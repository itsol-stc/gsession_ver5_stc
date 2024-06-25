package jp.groupsession.v2.api.smail.mail.edit;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.api.smail.mail.ApiSmlMailBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.sml010.Sml010Biz;
import jp.groupsession.v2.sml.sml010.Sml010ParamModel;
import jp.groupsession.v2.sml.sml030.Sml030Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメールを更新するWEBAPIアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSmlMailEditAction extends AbstractApiAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlMailEditAction.class);

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
        //ショートメールプラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConstSmail.PLUGIN_ID_SMAIL, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConstSmail.PLUGIN_ID_SMAIL));
            return null;
        }

        //CommonBiz cmnBiz = new CommonBiz();
        ApiSmlMailEditForm form = (ApiSmlMailEditForm) aForm;
        GsMessage gsMsg = new GsMessage(req);
        RequestModel reqMdl = getRequestModel(req);

        int    sacSid  = form.getSacSid();
        int    userSid = getSessionUserSid(req);
        String sacName = "";

        // アカウントチェック
        ApiSmlMailBiz biz = new ApiSmlMailBiz();
        ActionErrors  err = biz.validateCheckSmlAccount(con, gsMsg, userSid, sacSid);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        boolean commitFlg = false;

        ActionMessage errMsg = null;

        try {
            //入力内容チェック
            form.validateCheckSmlSend(err, con, gsMsg);
            if (!err.isEmpty()) {
                addErrors(req, err);
                return null;
            }

            int      cmdID   = form.getSmlCmd();
            String   mode    = String.valueOf(form.getSmlKbn());
            String[] sidSids = form.getSmlSids();

            Sml010Biz sml010biz  = new Sml010Biz();
            Sml010ParamModel paramMdl = new Sml010ParamModel();
            paramMdl.setSml010ProcMode(mode);
            paramMdl.setSmlViewAccount(sacSid);

            String opCode = null;
            String logVal = "";

            //  0:既読 / 1:未読
            // 10:ラベル追加 / 11:ラベル削除
            // 20:元に戻す / 21:削除
            if (cmdID == 0) {
                log__.info("LIST UPDATE COMMAND:  READ COMP");
                // 既読
                paramMdl.setSml010DelSid(sidSids);
                paramMdl.setSmlViewAccount(sacSid);

                sml010biz.selsRead(paramMdl, reqMdl, con, 0);
            } else if (cmdID == 1) {
                log__.info("LIST UPDATE COMMAND:  NOT READ");
                // 未読
                Sml030Biz sml030biz  = new Sml030Biz();
                if (sidSids != null && sidSids.length > 0) {
                    for (String strSidSids:sidSids) {
                        int smjsid = NullDefault.getInt(strSidSids, -1);
                        sml030biz.updateMidokuMsg(con, sacSid, userSid, smjsid);
                    }
                }
            } else if (cmdID == 10) {
                log__.info("LIST UPDATE COMMAND:  LABEL ADD => " + form.getSlbSid());
                // ラベル追加
                if (!mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) { // ゴミ箱以外で実行可
                    // ここではショートメールに対するラベル付与のみ(※ラベルの新規登録は行わない)
                    paramMdl.setSml010DelSid(sidSids);
                    paramMdl.setSmlViewAccount(sacSid);
                    paramMdl.setSml010addLabel(form.getSlbSid());
                    paramMdl.setSml010ProcMode(mode);
                    paramMdl.setSml010addLabelType(GSConstSmail.ADDLABEL_NORMAL);
                    sml010biz.setLabelForMessage(null, reqMdl, res, con, paramMdl, null, userSid);
                    // ※ ActionMapping map, MlCountMtController mtCon はラベル付与では使わないので null を渡す

                    opCode = getInterMessage(req, "cmn.entry");
                    logVal = getInterMessage(req, GSConstSmail.LOG_VALUE_LABEL);
                } else {
                    errMsg = new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("cmn.mode"));
                }
            } else if (cmdID == 11) {
                log__.info("LIST UPDATE COMMAND:  LABEL DEL => " + form.getSlbSid());
                // ラベル削除
                if (!mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) { // ゴミ箱以外で実行可
                    String[] smlNums = form.getSmlNums();

                    //処理モード = ラベルモード
                    paramMdl.setSml010DelSid(sidSids);
                    paramMdl.setSmlViewAccount(sacSid);
                    paramMdl.setSml010delLabel(form.getSlbSid());
                    paramMdl.setSml010ProcMode(mode);
                    paramMdl.setSml010LabelDelSid(smlNums); // セミコロン付きのメールSIDを渡す
                    sml010biz.deleteLabelForMessage(con, paramMdl, reqMdl);

                    opCode = getInterMessage(req, "cmn.delete");
                    logVal = getInterMessage(req, GSConstSmail.LOG_VALUE_LABEL);
                } else {
                    errMsg = new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("cmn.mode"));
                }
            } else if (cmdID == 20) {
                log__.info("LIST UPDATE COMMAND:  RESTORE");
                // 元に戻す
                if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) { // ゴミ箱以外で実行可
                    paramMdl.setSml010DelSid(sidSids);
                    paramMdl.setSmlViewAccount(sacSid);
                    sml010biz.revivedMessage(paramMdl, reqMdl, con);

                    opCode = gsMsg.getMessage("cmn.edit");
                    logVal = gsMsg.getMessage("cmn.undo");
                } else {
                    errMsg = new ActionMessage("error.input.notvalidate.data",
                            gsMsg.getMessage("cmn.mode"));
                }
            } else if (cmdID == 21) {
                log__.info("LIST UPDATE COMMAND:  DELETE");
                // 削除
                paramMdl.setSml010DelSid(sidSids);
                sml010biz.deleteMessage(paramMdl, reqMdl, con);

                opCode = gsMsg.getMessage("cmn.delete");
                if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                    logVal = gsMsg.getMessage("cmn.empty.trash");
                }
            } else {
                // コマンドエラー
                errMsg = new ActionMessage("error.input.notvalidate.data",
                        gsMsg.getMessage("cmn.action"));
            }

            if (errMsg != null) {
                // エラーメッセージあり
            } else if (paramMdl.getErrorsList() != null
                    && paramMdl.getErrorsList().size() > 0
                    && paramMdl.getErrorsList().get(0).length() > 0) {
                // 実行処理によるエラーメッセージあり
                log__.info("LIST UPDATE ERROR END: " + paramMdl.getErrorsList().get(0));
                errMsg = new ActionMessage("errors.free.msg",
                        paramMdl.getErrorsList().get(0));
            } else {
                // エラーなし
                commitFlg = true;

                if (opCode != null) {
                    //ログ出力処理
                    SmlAccountDao sacDao = new SmlAccountDao(con);
                    SmlAccountModel sacMdl = sacDao.select(sacSid);
                    if (sacMdl != null) {
                        sacName = sacMdl.getSacName();
                    }

                    SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
                    smlBiz.outPutApiLog(req, con, umodel.getUsrsid(),
                            this.getClass().getCanonicalName(),
                            opCode, GSConstLog.LEVEL_TRACE, "アカウント:" + sacName + "\n" + logVal);
                }

                log__.info("LIST UPDATE COMPLETE");
            }
        } catch (SQLException e) {
            log__.error("メッセージの送信に失敗", e);
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }

        // エラーあり
        if (errMsg != null) {
            StrutsUtil.addMessage(err, errMsg, "smlEditOther");
            addErrors(req, err);
            return null;
        }

        //Result
        Element result = new Element("Result");
        Document doc = new Document(result);

        if (commitFlg) {
            result.addContent("OK");
        } else {
            result.addContent("NG");
        }
        log__.debug("createXml end");
        return doc;
    }

}
