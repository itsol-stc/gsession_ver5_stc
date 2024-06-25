package jp.groupsession.v2.api.user.inf;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.ApiDataTypeUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] ユーザ情報を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiInfAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiInfAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");

        ApiInfForm thisForm = (ApiInfForm) form;
        GsMessage gsMsg = new GsMessage(req);
        //入力チェック
        ActionErrors errors = thisForm.validateUsrInf(gsMsg);
        if (!errors.isEmpty()) {
            log__.debug("エラーあり");
            addErrors(req, errors);
            return null;
        }

        int usid = NullDefault.getInt(thisForm.getUsrSid(), -1);
        CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel usrInfMdl = null;
        int ukoFlg = GSConst.YUKOMUKO_YUKO;
        try {
            //ユーザ情報を取得する。
            usrInfMdl = usrInfDao.select(usid);

            CmnUsrmInfModel jkbninfMdl = usrInfDao.selectUserNameAndJtkbn(usid);
            if (jkbninfMdl != null) {
                ukoFlg = jkbninfMdl.getUsrUkoFlg();
            }
        } catch (SQLException e) {
            log__.error("ユーザ情報の取得に失敗", e);
        }

        //ResultSet
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //Result
        Element result = new Element("Result");
        resultSet.addContent(result);

        //管理者権限の有無を判定
        boolean adminFlg = umodel.getAdminFlg();
        if (umodel.getUsrsid() == usid) {
            adminFlg = true;
        }
        //XMLデータ作成
        if (usrInfMdl != null) {

            //Usrsid ユーザSid
            result.addContent(_createElement("Usrsid", usrInfMdl.getUsrSid()));

            //Usisei ユーザ姓
            result.addContent(_createElement("Usisei", usrInfMdl.getUsiSei()));

            //Usimei ユーザ名
            result.addContent(_createElement("Usimei", usrInfMdl.getUsiMei()));

            //Usisei ユーザ姓カナ
            result.addContent(_createElement("Usiseikn", usrInfMdl.getUsiSeiKn()));

            //Usimeikn ユーザ名カナ
            result.addContent(_createElement("Usimeikn", usrInfMdl.getUsiMeiKn()));

            //UsrUkoFlg ユーザ無効フラグ
            result.addContent(_createElement("UsrUkoFlg", ukoFlg));

            //SyainNo
            result.addContent(_createElement("SyainNo", usrInfMdl.getUsiSyainNo()));
            //Syozoku
            result.addContent(_createElement("Syozoku", usrInfMdl.getUsiSyozoku()));
            //YakusyokuSid
            result.addContent(_createElement("YakusyokuSid", usrInfMdl.getPosSid()));
            //YakusyokuName
            //役職名称を取得
            String posName = "";
            if (usrInfMdl.getPosSid() > GSConst.POS_DEFAULT) {
                PosBiz pBiz = new PosBiz();
                posName = NullDefault.getString(pBiz.getPosName(con, usrInfMdl.getPosSid()), "");
            }
            result.addContent(_createElement("YakusyokuName", posName));

            if (!adminFlg && usrInfMdl.getUsiBdateKf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiBdate(null);
            }
            //Birthday
            Element birthday = new Element("Birthday");
            birthday.addContent(ApiDataTypeUtil.getDate(usrInfMdl.getUsiBdate()));
            result.addContent(birthday);
            //BirthdayKf
            result.addContent(_createElement("BirthdayKf", usrInfMdl.getUsiBdateKf()));

            if (!adminFlg && usrInfMdl.getUsiMail1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiMail1(null);
                usrInfMdl.setUsiMailCmt1(null);
            }
            //Mail1
            result.addContent(_createElement("Mail1", usrInfMdl.getUsiMail1()));
            //Mail1Comment
            result.addContent(_createElement("Mail1Comment", usrInfMdl.getUsiMailCmt1()));
            //Mail1Kf
            result.addContent(_createElement("Mail1Kf", usrInfMdl.getUsiMail1Kf()));

            if (!adminFlg && usrInfMdl.getUsiMail2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiMail2(null);
                usrInfMdl.setUsiMailCmt2(null);
            }
            //Mail2
            result.addContent(_createElement("Mail2", usrInfMdl.getUsiMail2()));
            //Mail2Comment
            result.addContent(_createElement("Mail2Comment", usrInfMdl.getUsiMailCmt2()));
            //Mail2Kf
            result.addContent(_createElement("Mail2Kf", usrInfMdl.getUsiMail2Kf()));

            if (!adminFlg && usrInfMdl.getUsiMail3Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiMail3(null);
                usrInfMdl.setUsiMailCmt3(null);
            }
            //Mail3
            result.addContent(_createElement("Mail3", usrInfMdl.getUsiMail3()));
            //Mail3Comment
            result.addContent(_createElement("Mail3Comment", usrInfMdl.getUsiMailCmt3()));
            //Mail3Kf
            result.addContent(_createElement("Mail3Kf", usrInfMdl.getUsiMail3Kf()));

            if (!adminFlg && usrInfMdl.getUsiZipKf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiZip1(null);
                usrInfMdl.setUsiZip2(null);
            }
            //Zip1
            result.addContent(_createElement("Zip1", usrInfMdl.getUsiZip1()));
            //Zip2
            result.addContent(_createElement("Zip2", usrInfMdl.getUsiZip2()));
            //ZipKf
            result.addContent(_createElement("ZipKf", usrInfMdl.getUsiZipKf()));

            if (adminFlg || usrInfMdl.getUsiTdfKf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
                //TodofukenSid
                result.addContent(_createElement("TodofukenSid", usrInfMdl.getTdfSid()));
                //TodofukenName
                int tdfkCode = usrInfMdl.getTdfSid();
                String tdfkName = "";
                if (tdfkCode > 0) {
                    CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
                    CmnTdfkModel param = new CmnTdfkModel();
                    param.setTdfSid(tdfkCode);
                    CmnTdfkModel ret = tdfkDao.select(param);
                    if (ret != null) {
                        tdfkName = ret.getTdfName();
                    }
                }
                result.addContent(_createElement("TodofukenName", tdfkName));
            } else {
                Element todofukenSid = new Element("TodofukenSid");
                String tdfkSid = null;
                todofukenSid.addContent(tdfkSid);
                result.addContent(todofukenSid);
                Element todofukenName = new Element("TodofukenName");
                String tdfkName = null;
                todofukenName.addContent(tdfkName);
                result.addContent(todofukenName);
            }
            //TodofukenKf
            result.addContent(_createElement("TodofukenKf", usrInfMdl.getUsiTdfKf()));

            if (!adminFlg && usrInfMdl.getUsiAddr1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiAddr1(null);
            }
            //Address1
            result.addContent(_createElement("Address1", usrInfMdl.getUsiAddr1()));
            //Address1Kf
            result.addContent(_createElement("Address1Kf", usrInfMdl.getUsiAddr1Kf()));

            if (!adminFlg && usrInfMdl.getUsiAddr2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiAddr2(null);
            }
            //Address2
            result.addContent(_createElement("Address2", usrInfMdl.getUsiAddr2()));
            //Address2Kf
            result.addContent(_createElement("Address2Kf", usrInfMdl.getUsiAddr2Kf()));

            if (!adminFlg && usrInfMdl.getUsiTel1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiTel1(null);
                usrInfMdl.setUsiTelNai1(null);
                usrInfMdl.setUsiTelCmt1(null);
            }
            //Tel1
            result.addContent(_createElement("Tel1", usrInfMdl.getUsiTel1()));
            //Tel1Naisen
            result.addContent(_createElement("Tel1Naisen", usrInfMdl.getUsiTelNai1()));
            //Tel1Comment
            result.addContent(_createElement("Tel1Comment", usrInfMdl.getUsiTelCmt1()));
            //Tel1Kf
            result.addContent(_createElement("Tel1Kf", usrInfMdl.getUsiTel1Kf()));

            if (!adminFlg && usrInfMdl.getUsiTel2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiTel2(null);
                usrInfMdl.setUsiTelNai2(null);
                usrInfMdl.setUsiTelCmt2(null);
            }
            //Tel2
            result.addContent(_createElement("Tel2", usrInfMdl.getUsiTel2()));
            //Tel2Naisen
            result.addContent(_createElement("Tel2Naisen", usrInfMdl.getUsiTelNai2()));
            //Tel2Comment
            result.addContent(_createElement("Tel2Comment", usrInfMdl.getUsiTelCmt2()));
            //Tel2Kf
            result.addContent(_createElement("Tel2Kf", usrInfMdl.getUsiTel2Kf()));

            if (!adminFlg && usrInfMdl.getUsiTel3Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiTel3(null);
                usrInfMdl.setUsiTelNai3(null);
                usrInfMdl.setUsiTelCmt3(null);
            }
            //Tel3
            result.addContent(_createElement("Tel3", usrInfMdl.getUsiTel3()));
            //Tel3Naisen
            result.addContent(_createElement("Tel3Naisen", usrInfMdl.getUsiTelNai3()));
            //Tel3Comment
            result.addContent(_createElement("Tel3Comment", usrInfMdl.getUsiTelCmt3()));
            //Tel3Kf
            result.addContent(_createElement("Tel3Kf", usrInfMdl.getUsiTel3Kf()));

            if (!adminFlg && usrInfMdl.getUsiFax1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiFax1(null);
                usrInfMdl.setUsiFaxCmt1(null);
            }
            //Fax1
            result.addContent(_createElement("Fax1", usrInfMdl.getUsiFax1()));
            //Fax1Comment
            result.addContent(_createElement("Fax1Comment", usrInfMdl.getUsiFaxCmt1()));
            //Fax1Kf
            result.addContent(_createElement("Fax1Kf", usrInfMdl.getUsiFax1Kf()));

            if (!adminFlg && usrInfMdl.getUsiFax2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiFax2(null);
                usrInfMdl.setUsiFaxCmt2(null);
            }
            //Fax2
            result.addContent(_createElement("Fax2", usrInfMdl.getUsiFax2()));
            //Fax2Comment
            result.addContent(_createElement("Fax2Comment", usrInfMdl.getUsiFaxCmt2()));
            //Fax2Kf
            result.addContent(_createElement("Fax2Kf", usrInfMdl.getUsiFax2Kf()));

            if (!adminFlg && usrInfMdl.getUsiFax3Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
                usrInfMdl.setUsiFax3(null);
                usrInfMdl.setUsiFaxCmt3(null);
            }
            //Fax3
            result.addContent(_createElement("Fax3", usrInfMdl.getUsiFax3()));
            //Fax3Comment
            result.addContent(_createElement("Fax3Comment", usrInfMdl.getUsiFaxCmt3()));
            //Fax3Kf
            result.addContent(_createElement("Fax3Kf", usrInfMdl.getUsiFax3Kf()));

            //Bikou
            result.addContent(_createElement("Bikou", usrInfMdl.getUsiBiko()));
            //AddDate
            Element addDate = new Element("AddDateTime");
            addDate.addContent(ApiDataTypeUtil.getDateTime(usrInfMdl.getUsiAdate()));
            result.addContent(addDate);
            //EditDate
            Element editDate = new Element("EditDateTime");
            editDate.addContent(ApiDataTypeUtil.getDateTime(usrInfMdl.getUsiEdate()));
            result.addContent(editDate);

        }

        log__.debug("createXml end");
        return doc;
    }

}
