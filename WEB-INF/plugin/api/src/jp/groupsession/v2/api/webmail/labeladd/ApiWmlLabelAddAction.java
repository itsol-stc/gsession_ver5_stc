package jp.groupsession.v2.api.webmail.labeladd;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.GSValidateWebmail;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.wml120kn.Wml120knBiz;
import jp.groupsession.v2.wml.wml120kn.Wml120knParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] Webメールラベルを追加するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlLabelAddAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlLabelAddAction.class);

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

        ApiWmlLabelAddForm form = (ApiWmlLabelAddForm) aForm;

        int    wacSid    = form.getWacSid();
        String labelName = form.getWlbName();
        int    userSid   = getSessionUserSid(req);

        boolean commitFlg = false;

        RequestModel reqMdl = getRequestModel(req);
        GsMessage    gsMsg  = new GsMessage(reqMdl);

        try {
            ActionErrors errors = new ActionErrors();

            // アカウント使用可否チェック
            WmlDao wmlDao = new WmlDao(con);
            if (!wmlDao.canUseAccount(wacSid, userSid)) {
                // アカウントがない場合
                ActionMessage msg = new ActionMessage("search.data.notfound",
                                                      gsMsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "account");
                addErrors(req, errors);
                return null;
            }

            // 入力されたラベル名チェック
            GSValidateWebmail.validateTextBoxInput(errors, labelName,
                "wml120LabelName",
                getInterMessage(req, GSConstWebmail.TEXT_LABEL),
                GSConstWebmail.MAXLEN_SEARCH_KEYWORD, true);

            if (!errors.isEmpty()) {
                // ラベル名に問題
                addErrors(req, errors);
                return null;
            }

            //登録処理
            if (labelName != null && labelName.length() > 0) {
                MlCountMtController cntCon   = getCountMtController(req);
                Wml120knParamModel  paramMdl = new Wml120knParamModel();
                paramMdl.setWml110accountSid(wacSid);
                paramMdl.setWml120LabelName(labelName);
                paramMdl.setWmlLabelCmdMode(GSConstWebmail.CMDMODE_ADD); // 登録で固定
                Wml120knBiz biz = new Wml120knBiz(con);
                biz.doAddEdit(paramMdl, userSid, cntCon);

                commitFlg = true;

                //ログ出力
                WmlBiz wmlBiz = new WmlBiz();
                wmlBiz.outPutApiLog(reqMdl, con, userSid, getClass().getCanonicalName(),
                        getInterMessage(req, "cmn.entry"), GSConstLog.LEVEL_INFO,
                        "[name]" + NullDefault.getString(labelName, ""));
            }

        } catch (SQLException e) {
            log__.error("WEBメールラベルの追加に失敗", e);
        }

        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        if (commitFlg) {
            result.addContent("OK");
        } else {
            result.addContent("NG");
        }
        return doc;
    }

}
