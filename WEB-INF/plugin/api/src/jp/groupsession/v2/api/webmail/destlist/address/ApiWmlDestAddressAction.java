package jp.groupsession.v2.api.webmail.destlist.address;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.wml.dao.WmlGetAddressBookDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAccessConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAddressDao;
import jp.groupsession.v2.wml.model.WmlAddressBookModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistAddressModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] WEBメール送信先リストのアドレス一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlDestAddressAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlDestAddressAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aForm フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        //WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        ApiWmlDestAddressForm form = (ApiWmlDestAddressForm) aForm;

        int wdlSid = form.getWdlSid();
        int usrSid = umodel.getUsrsid();
        List<WmlDestlistAddressModel> destAdrList = null;
        HashMap<Integer, CmnUsrmInfModel>        userDataMap =
                new HashMap<Integer, CmnUsrmInfModel>();
        HashMap<Integer, WmlAddressBookModel> adrDataMap  =
                new HashMap<Integer, WmlAddressBookModel>();

        try {
            WmlDestlistAccessConfDao destAcsDao = new WmlDestlistAccessConfDao(con);
            // 閲覧or編集権限チェック
            if (destAcsDao.checkDestlistAuth(wdlSid, usrSid, GSConstWebmail.WLA_AUTH_ALL)
             || destAcsDao.checkDestlistAuth(wdlSid, usrSid, GSConstWebmail.WLA_AUTH_READ)) {
                // 送信先を設定する
                WmlDestlistAddressDao destAdrDao = new WmlDestlistAddressDao(con);
                destAdrList = destAdrDao.getDestlistAddress(wdlSid, -1);
            }

            if (destAdrList != null) {
                HashSet<String> usrSidSet = new HashSet<String>();
                HashSet<String> adrSidSet = new HashSet<String>();
                for (WmlDestlistAddressModel mdl : destAdrList) {
                    if (mdl.getWdaType() == GSConstWebmail.WDA_TYPE_USER) {
                        usrSidSet.add(String.valueOf(mdl.getWdaSid()));
                    } else if (mdl.getWdaType() ==  GSConstWebmail.WDA_TYPE_ADDRESS) {
                        adrSidSet.add(String.valueOf(mdl.getWdaSid()));
                    }
                }

                // ユーザ情報
                if (usrSidSet.size() > 0) {
                    String[] usrSids = new String[usrSidSet.size()];
                    usrSidSet.toArray(usrSids);

                    CmnUsrmInfDao userInfDao = new CmnUsrmInfDao(con);
                    List<CmnUsrmInfModel> dataList = userInfDao.getUsersInfList(usrSids);
                    if (dataList != null) {
                        for (CmnUsrmInfModel data : dataList) {
                            userDataMap.put(data.getUsrSid(), data);
                        }
                    }
                }

                // アドレス情報
                if (adrSidSet.size() > 0) {
                    String[] adrSids = new String[adrSidSet.size()];
                    adrSidSet.toArray(adrSids);

                    WmlGetAddressBookDao adrDao = new WmlGetAddressBookDao(con);
                    List<WmlAddressBookModel>  dataList  = adrDao.getAddressBookData(adrSids);
                    if (dataList != null) {
                        for (WmlAddressBookModel data : dataList) {
                            adrDataMap.put(data.getAdrSid(), data);
                        }
                    }
                }
            }

        } catch (SQLException e) {
            log__.error("WEBメール送信先リストアドレス一覧の取得に失敗", e);
        }

        // Result
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Integer resultCnt = 0;
        if (destAdrList != null) {
            for (WmlDestlistAddressModel data : destAdrList) {
                Element result = new Element("Result");
                resultSet.addContent(result);
                String mailAdr = "";
                String nameSei = "";
                String nameMei = "";
                int    ukoFlg  = -1;

                if (data.getWdaType() == GSConstWebmail.WDA_TYPE_USER) {
                    CmnUsrmInfModel mdl = userDataMap.get(data.getWdaSid());
                    if (mdl != null) {
                        switch (data.getWdaAdrno()) {
                            case 1: mailAdr = mdl.getUsiMail1(); break;
                            case 2: mailAdr = mdl.getUsiMail2(); break;
                            case 3: mailAdr = mdl.getUsiMail3(); break;
                            default:                             break;
                        }
                        nameSei = mdl.getUsiSei();
                        nameMei = mdl.getUsiMei();
                        ukoFlg  = mdl.getUsrUkoFlg();
                    }
                } else if (data.getWdaType() == GSConstWebmail.WDA_TYPE_ADDRESS) {
                    WmlAddressBookModel mdl = adrDataMap.get(data.getWdaSid());
                    if (mdl != null) {
                        switch (data.getWdaAdrno()) {
                            case 1: mailAdr = mdl.getAdrMail1(); break;
                            case 2: mailAdr = mdl.getAdrMail2(); break;
                            case 3: mailAdr = mdl.getAdrMail3(); break;
                            default:                             break;
                        }
                        nameSei = mdl.getAdrSei();
                        nameMei = mdl.getAdrMei();
                    }
                }
                result.addContent(_createElement("wdaSid",   data.getWdaSid()));
                result.addContent(_createElement("wdaType",  data.getWdaType()));
                result.addContent(_createElement("wdaSei",   nameSei));
                result.addContent(_createElement("wdaMei",   nameMei));
                result.addContent(_createElement("mailAdr",  mailAdr));
                if (ukoFlg >= 0) {
                    result.addContent(_createElement("wdaUkoFlg",  ukoFlg));
                }
            }
            resultCnt = destAdrList.size();
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));

        return doc;
    }

}
