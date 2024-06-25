package jp.groupsession.v2.api.webmail.labeldel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;
import jp.groupsession.v2.wml.wml110.Wml110Biz;
import jp.groupsession.v2.wml.wml110.Wml110ParamModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

/**
 * <br>[機  能] Webメールラベルを削除するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiWmlLabelDelAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiWmlLabelDelAction.class);

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

        ApiWmlLabelDelForm form = (ApiWmlLabelDelForm) aForm;

        int wacSid   = form.getWacSid();
        int labelSid = form.getWlbSid();
        int userSid  = getSessionUserSid(req);

        boolean commitFlg = false;

        RequestModel reqMdl = getRequestModel(req);
        GsMessage    gsMsg  = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();

        try {
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

            // ラベル使用可否チェック
            if (labelSid < 0) {
                ActionMessage msg = new ActionMessage("error.input.required.text",
                        gsMsg.getMessage("cmn.label"));
                StrutsUtil.addMessage(errors, msg, "label");
                addErrors(req, errors);
                return null;
            }

            // 使用可能なラベル一覧の中からラベルSIDが一致するデータを検索
            WmlLabelDao wlbDao = new WmlLabelDao(con);
            List<WmlLabelModel> labelList = wlbDao.getLabelList(wacSid);
            WmlLabelModel labelMdl = null;
            if (labelList != null) {
                for (WmlLabelModel mdl : labelList) {
                    if (mdl.getWlbSid() == labelSid) {
                        labelMdl = mdl;
                        break;
                    }
                }
            }

            // 一致するデータがない為、削除権限なし
            if (labelMdl == null) {
                ActionMessage msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage("cmn.label"));
                StrutsUtil.addMessage(errors, msg, "label");
                addErrors(req, errors);
                return null;
            }

            //ラベルを削除する
            Wml110ParamModel paramMdl = new Wml110ParamModel();
            paramMdl.setWmlEditLabelId(labelSid);
            Wml110Biz biz = new Wml110Biz();
            biz.deleteLabel(con, paramMdl);

            commitFlg = true;

            //ログ出力
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.outPutApiLog(reqMdl, con, userSid, getClass().getCanonicalName(),
                    getInterMessage(req, "cmn.delete"), GSConstLog.LEVEL_INFO, "");

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
