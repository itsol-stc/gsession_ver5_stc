package jp.groupsession.v2.api.webmail.mail.edit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.wml010.Wml010Biz;
import jp.groupsession.v2.wml.wml010.Wml010Const;
import jp.groupsession.v2.wml.wml010.Wml010ParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] WEBメールを編集するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlMailEditAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlMailEditAction.class);

    /**
     * <br>[機  能] レスポンスXML情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param aForm  フォーム
     * @param req    リクエスト
     * @param res    レスポンス
     * @param con    DBコネクション
     * @param umodel ユーザ情報
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public Document createXml(ActionForm aForm, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {

        log__.debug("createXml start");
        // WEBメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstWebmail.PLUGIN_ID_WEBMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstWebmail.PLUGIN_ID_WEBMAIL));
            return null;
        }

        ApiWmlMailEditForm form = (ApiWmlMailEditForm) aForm;
        GsMessage gsMsg = new GsMessage(req);
        RequestModel reqMdl = getRequestModel(req);
        int userSid = getSessionUserSid(req);
        ActionErrors errors = new ActionErrors();

        int    wacSid  = form.getWacSid();
        int    cmdID   = form.getWmlCmd();
        long[] wmlSids = form.getWmlSids();
        String errMsg  = null;

        boolean commitFlg = false;
        try {
            WmlDao wmlDao = new WmlDao(con);
            if (!wmlDao.canUseAccount(wacSid, userSid)) {
                // アカウント使用不可
                //retStr = gsMsg.getMessage("wml.191");
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "account");
                addErrors(req, errors);
                return null;
            }

            Wml010Biz biz = new Wml010Biz();
            Wml010ParamModel paramMdl = new Wml010ParamModel();
            String  retStr = null;

            paramMdl.setWmlViewAccount(String.valueOf(wacSid));
            paramMdl.setWml010selectMessageNum(wmlSids);
            if (cmdID == 20) {
                // 移動
                log__.info("MAIL UPDATE COMMAND:  RESTORE");
                paramMdl.setWml010moveFolder(form.getWdrSid());
                retStr = biz.changeFolderInMail(con, paramMdl, reqMdl);
            } else if (cmdID == 21) {
                // 削除
                log__.info("MAIL UPDATE COMMAND:  DELETE");
                // メッセージ番号からディレクトリSID一覧取得
                ApiWmlMailEditDao editDao = new ApiWmlMailEditDao(con);
                HashMap<Long, Integer> wdrMap = editDao.getMailDirectoryList(wmlSids);
                if (wdrMap != null && wdrMap.size() > 0) {
                    int[] wdrSids = new int[wdrMap.size()];
                    for (int idx = 0; idx < wmlSids.length; idx++) {
                        Long key = Long.valueOf(wmlSids[idx]);
                        if (wdrMap.containsKey(key) && wdrSids.length > idx) {
                            wdrSids[idx] = wdrMap.get(key).intValue();
                        }
                    }
                    paramMdl.setWml010selectMessageDirId(wdrSids);
                }
                retStr = biz.dustMail(con, paramMdl, userSid, reqMdl);

            } else if (cmdID == 10) {
                // ラベル追加
                paramMdl.setWml010addLabelType(Wml010Const.ADDLABEL_NORMAL);
                paramMdl.setWml010addLabel(form.getWlbSid());
                retStr = biz.setLabelForMessage(null, reqMdl, res, con, paramMdl,
                        getCountMtController(req), userSid);

                // 応答がJSON形式なので解析
                if (retStr != null && retStr.length() > 0) {
                    JSONObject jsonData = new JSONObject();
                    jsonData = JSONObject.fromObject(retStr);
                    if (jsonData != null && jsonData.containsKey("message")) {
                        retStr = jsonData.optString("message");
                    }
                }
            } else if (cmdID == 11) {
                // ラベル削除
                paramMdl.setWml010delLabel(form.getWlbSid());
                retStr = biz.deleteLabelForMessage(con, paramMdl, reqMdl);

                // 応答がJSON形式なので解析
                if (retStr != null && retStr.length() > 0) {
                    JSONObject jsonData = new JSONObject();
                    jsonData = JSONObject.fromObject(retStr);
                    if (jsonData != null && jsonData.containsKey("message")) {
                        retStr = jsonData.optString("message");
                    }
                }
            } else if (cmdID == 0) {
                // 既読
                retStr = biz.changeMailReaded(con, paramMdl, reqMdl,
                                                             Wml010Const.MAIL_READTYPE_READED);
            } else if (cmdID == 1) {
                // 未読
                retStr = biz.changeMailReaded(con, paramMdl, reqMdl,
                                                             Wml010Const.MAIL_READTYPE_NOREAD);
            }

            if (retStr != null && retStr.contentEquals("success")) {
                // エラーなし
                commitFlg = true;

                if (cmdID == 10 || cmdID == 11) {
                    // ログ出力処理(ラベル追加・ラベル削除 のみ)
                    WmlBiz wmlBiz = new WmlBiz();
                    wmlBiz.outPutApiLog(reqMdl, con, userSid, getClass().getCanonicalName(),
                            getInterMessage(req, GSConstWebmail.LOG_VALUE_LABEL),
                            GSConstLog.LEVEL_TRACE, "");
                }
            } else {
                errMsg = retStr;
            }
        } catch (SQLException e) {
            log__.error("メッセージの更新に失敗", e);
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        if (commitFlg) {
            result.addContent("OK");
        } else if (errMsg != null && errMsg.length() > 0) {
            // エラーメッセージがある場合、そちらを優先
            result.addContent(_createElement("Message", errMsg));
        } else {
            result.addContent("NG");
        }
        log__.debug("createXml end");
        return doc;
    }
}
