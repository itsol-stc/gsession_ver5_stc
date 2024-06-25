package jp.groupsession.v2.api.smail.account.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.smail.account.ApiSmlAccountDao;
import jp.groupsession.v2.sml.model.SmailUsrModel;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] ショートメールアカウント一覧を取得するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlAccountSelectAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlAccountSelectAction.class);

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

        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }

        ApiSmlAccountSelectForm form = (ApiSmlAccountSelectForm) aForm;

        int     grpSid  = -1;
        int     sacType = 0;
        int     userSid = getSessionUserSid(req);
        boolean isMyGrp = false;

        RequestModel reqMdl = getRequestModel(req);
        GsMessage    gsMsg  = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();

        if (form.getGrpSid() == null
         || form.getGrpSid().length() == 0
         || form.getGrpSid().contains("-1")) {
            // 代表アカウント
            sacType   = 1;
        } else {
            String grpSidStr = form.getGrpSid();
            if (grpSidStr.startsWith("M") && grpSidStr.length() >= 2) {
                // マイグループ
                grpSidStr = grpSidStr.substring(1); // [M]を除外して数値部分を取得
                isMyGrp   = true;
            }

            // グループSIDの数値チェック
            if (GSValidateUtil.isNumber(grpSidStr)) {
                // グループSIDを数値として取得
                grpSid = Integer.valueOf(grpSidStr).intValue();
            }
        }

        LinkedHashMap<Integer, String> list = new LinkedHashMap<Integer, String>();
        List<Integer> ukoList = new ArrayList<Integer>();  // 無効ユーザのアカウントSID一覧
        try {
            SmlAccountDao  smlAcnDao = new SmlAccountDao(con);

            SmlBanDestDao sbdDao = new SmlBanDestDao(con);

            if (sacType == 1) {
                // 代表アカウント
                List<SmailUsrModel> usrList = smlAcnDao.selectSmlAccount();
                if (usrList != null) {
                    for (SmailUsrModel data : usrList) {
                        list.put(Integer.parseInt(data.getSacSid()), data.getSacName());
                    }

                    // 除外リスト
                    List<Integer> banSidList = sbdDao.getBanDestAccSidList(userSid);
                    if (banSidList != null) {
                        for (Integer banSid : banSidList) {
                            list.remove(banSid);
                        }
                    }
                }
            } else if (grpSid >= 0) {
                // グループに所属するユーザSID一覧を取得
                UserBiz userBiz = new UserBiz();
                List<UsrLabelValueBean> userList = null;
                Map<Integer, Integer> sacSids = null;
                if (!isMyGrp) {
                    // ユーザグループ
                    userList = userBiz.getUserLabelList(con, grpSid, null);
                } else {
                    // マイグループ
                    userList = userBiz.getMyGroupUserLabelList(con, userSid, grpSid, null);
                }

                // グループに所属するユーザSID一覧からアカウント一覧取得
                if (userList != null && userList.size() > 0) {
                    // 除外リストにあるユーザSIDをユーザSID一覧から削除
                    List<Integer> banSidList = sbdDao.getBanDestUsrSidList(userSid);
                    for (int i = userList.size() - 1; i >= 0; i--) {
                        UsrLabelValueBean bean = userList.get(i);
                        if (bean.getValue() != null
                         && banSidList.contains(Integer.valueOf(bean.getValue()))) {
                            userList.remove(bean);
                        }
                    }

                    // 有効なユーザSID一覧のデータ形式変更(共通関数使用する為)
                    List<Integer> usrSids = new ArrayList<Integer>();
                    for (UsrLabelValueBean bean : userList) {
                        usrSids.add(Integer.valueOf(bean.getValue()));
                    }

                    ApiSmlAccountDao sacDao = new ApiSmlAccountDao(con);
                    sacSids = sacDao.selectFromUsrSids(usrSids);
                }

                // アカウント情報一覧から必要情報を取得
                if (sacSids != null && sacSids.size() > 0) {
                    for (UsrLabelValueBean mdl : userList) {
                        Integer usrSid = Integer.parseInt(mdl.getValue());
                        if (sacSids.containsKey(usrSid)) {
                            Integer sacSid = sacSids.get(usrSid);

                            // アカウント名を一覧へ追加
                            list.put(sacSid, mdl.getLabel());

                            // 無効ユーザの場合、無効ユーザリストへ追加
                            if (mdl.getUsrUkoFlg() == GSConst.YUKOMUKO_MUKO) {
                                ukoList.add(sacSid);
                            }
                        }
                    }
                }
            } else {
                // グループSIDが数値ではないのでエラー
                ActionMessage msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage("cmn.group.sid"));
                StrutsUtil.addMessage(errors, msg, "cmn.group.sid");
                addErrors(req, errors);
                return null;
            }
        } catch (SQLException e) {
            log__.error("ショートメール宛先アカウント一覧の取得に失敗", e);
        }

        //Result
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);
        Integer resultCnt = 0;
        if (list != null) {
            for (Integer key : list.keySet()) {

                int ukoFlg = GSConst.YUKOMUKO_YUKO;
                if (ukoList.contains(key)) {
                    ukoFlg = GSConst.YUKOMUKO_MUKO;
                }

                Element result = new Element("Result");
                resultSet.addContent(result);
                result.addContent(_createElement("sacSid",  key));
                result.addContent(_createElement("sacType", sacType));
                result.addContent(_createElement("sacName", list.get(key)));
                result.addContent(_createElement("sacUkoFlg", ukoFlg));
            }
            resultCnt = list.size();
        }
        resultSet.setAttribute("Count", Integer.toString(resultCnt));
        return doc;
    }

}
