package jp.groupsession.v2.cir.cir140kn;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cir.AbstractCircularAdminAction;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 回覧板 管理者設定 初期値設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir140knAction extends AbstractCircularAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cir140knAction.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    public ActionForward executeAction(
        ActionMapping map,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        ActionForward forward = null;
        Cir140knForm thisForm = (Cir140knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        log__.debug("Cir140Action CMD==>" + cmd);

        if (cmd.equals("backToKtool")) {
            log__.debug("設定完了");
            forward = __doOk(map, thisForm, req, res, con);

        } else if (cmd.equals("back_init_change")) {
            log__.debug("戻る");
            forward = map.findForward("back_init_change");

        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        log__.debug("END_Cir140kn");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        Cir140knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 回覧板 送信ボタンクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doOk(
        ActionMapping map,
        Cir140knForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("トランザクショントークンエラー");
            return getSubmitErrorPage(map, req);
        }

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        con.setAutoCommit(true);
        Cir140knParamModel paramMdl = new Cir140knParamModel();
        paramMdl.setParam(form);
        Cir140knBiz biz = new Cir140knBiz();
        biz.setInitAdmDataDB(con, paramMdl, userSid);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        CirCommonBiz cirBiz = new CirCommonBiz(con);
        String opCode = gsMsg.getMessage("cmn.change");
        String msg = __getLogMessage(form, req);
        cirBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, msg);

        //完了画面を表示
        return __doCompDsp(map, form, req, res);
    }


    /**
     * <br>[機  能] 完了画面設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @return ActionForward
     */
    private ActionForward __doCompDsp(ActionMapping map,
                                       Cir140knForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res) {

        ActionForward forward = null;
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //完了画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        GsMessage gsMsg = new GsMessage();
        String textCir = gsMsg.getMessage(req, "cir.cir130kn.1");

        urlForward = map.findForward("backToKtool");
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(
                msgRes.getMessage("hensyu.henkou.kanryo.object", textCir));

        //hiddenパラメータ
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        form.setHiddenParam(cmn999Form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     * @throws Exception エラー
     */
    private String __getLogMessage(
        Cir140knForm form,
        HttpServletRequest req) throws Exception {

        GsMessage gsMsg = new GsMessage(req);

        String msg = "";
        //編集権限
        msg += "[" + gsMsg.getMessage("cmn.edit.permissions") + "]";
        if (form.getCir140KenKbn() == GSConstCircular.CIR_DEFAULT_EDIT_ADMIN) {
            msg += gsMsg.getMessage("cmn.set.the.admin");
        } else if (form.getCir140KenKbn() == GSConstCircular.CIR_DEFAULT_EDIT_ALL) {
            msg += gsMsg.getMessage("cmn.set.eachaccount");
        }
        //メモ欄の修正
        msg += "\r\n[" + gsMsg.getMessage("cir.cir040.2") + "]";
        msg += gsMsg.getMessage("cir.cir040.3") + " : ";
        if (form.getCir140memoKbn() == GSConstCircular.CIR_INIT_MEMO_CHANGE_NO) {
            msg += gsMsg.getMessage("cmn.not");
        } else if (form.getCir140memoKbn() == GSConstCircular.CIR_INIT_MEMO_CHANGE_YES) {
            msg += gsMsg.getMessage("cmn.accepted");
            msg += "\r\n" + gsMsg.getMessage("cir.54") + " : ";
            if (form.getCir140memoPeriod() == GSConstCircular.CIR_INIT_MEMO_TODAY) {
                msg += gsMsg.getMessage("cmn.today");
            } else if (form.getCir140memoPeriod() == GSConstCircular.CIR_INIT_MEMO_ONEWEEK) {
                msg += "1" + gsMsg.getMessage("cmn.weeks");
            } else if (form.getCir140memoPeriod() == GSConstCircular.CIR_INIT_MEMO_TWOWEEKS) {
                msg += "2" + gsMsg.getMessage("cmn.weeks");
            } else if (form.getCir140memoPeriod() == GSConstCircular.CIR_INIT_MEMO_ONEMONTH) {
                msg += gsMsg.getMessage("schedule.src.55");
            }
        }
        //回覧先確認状況
        msg += "\r\n[" + gsMsg.getMessage("cir.cir030.3") + "]";
        if (form.getCir140show() == GSConstCircular.CIR_INIT_SAKI_PUBLIC) {
            msg += gsMsg.getMessage("cmn.public");
        } else if (form.getCir140show() == GSConstCircular.CIR_INIT_SAKI_PRIVATE) {
            msg += gsMsg.getMessage("cmn.private");
        }
        return msg;
    }
}
