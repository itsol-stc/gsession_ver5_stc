package jp.groupsession.v2.enq.enq810;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.AbstractEnqueteAction;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.enq.enq010.Enq010Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アンケート 個人設定 表示設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq810Action extends AbstractEnqueteAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Enq810Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    public ActionForward executeAction(ActionMapping map,
                                       ActionForm form,
                                       HttpServletRequest req,
                                       HttpServletResponse res,
                                       Connection con)
        throws Exception {

        log__.debug("Enq810Action_START");

        ActionForward forward = null;
        Enq810Form enq810Form = (Enq810Form) form;

        // アクセス権限チェック
        con.setAutoCommit(false);

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "").trim();
        log__.debug("CMD = " + cmd);

        // コマンドの判定
        if (cmd.equals("enq810commit")) {
            // 更新処理
            forward = __doCommit(map, enq810Form, req, res, con);

        } else if (cmd.equals("enq810back")) {
            // 戻る
            forward = map.findForward(cmd);

        } else {
            // 初期表示処理
            forward = __doInit(map, enq810Form, req, res, con);
        }

        log__.debug("Enq810Action_END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                   Enq810Form form,
                                   HttpServletRequest req,
                                   HttpServletResponse res,
                                   Connection con)
    throws SQLException {

        log__.debug("初期表示処理");

        con.setAutoCommit(true);
        try {
            Enq810Biz biz = new Enq810Biz();
            Enq810ParamModel paramModel = new Enq810ParamModel();
            paramModel.setParam(form);
            biz.setInitData(paramModel, getRequestModel(req), con);
            paramModel.setFormData(form);
        } finally {
            con.setAutoCommit(false);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 更新処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doCommit(ActionMapping map,
                                     Enq810Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
    throws Exception {

        log__.debug("更新処理");

        RequestModel reqMdl = getRequestModel(req);

        // 更新処理
        Enq810Biz biz = new Enq810Biz();
        Enq810ParamModel paramModel = new Enq810ParamModel();
        paramModel.setParam(form);
        biz.doCommit(paramModel, reqMdl, con);
        paramModel.setFormData(form);

        // オペレーションログ出力処理
        GsMessage gsMsg = new GsMessage(reqMdl);
        String textChange = gsMsg.getMessage("cmn.change");
        String pluginName = gsMsg.getMessage("enq.plugin");
        EnqCommonBiz enqBiz = new EnqCommonBiz(con);
        enqBiz.outPutLog(map, reqMdl, pluginName, textChange,
                GSConstLog.LEVEL_TRACE, __getLogText(reqMdl, form));

        return __setCommitDsp(map, form, req);
    }
    /**
     * <br>[機  能] 表示設定更新時のオペレーションログ内容を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param form アクションフォーム
     * @return [一覧表示件数]XXX
     */
    private String __getLogText(RequestModel reqMdl, Enq810Form form) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String ret = "";

        if (form.getEnq810DspUse() == GSConstEnquete.CONF_LIST_USE_EACH) {
            ret += "[" + gsMsg.getMessage("enq.enq810.01") + "]" + form.getEnq810SelectCnt();
        }


        if (ret.length() > 0) {
            ret += "\r\n";
        }
        if (form.getEnq810DspMainUse() == GSConstEnquete.CONF_MAIN_DSP_USE_EACH) {
            ret += "[" + gsMsg.getMessage("enq.enq820.01") + "]";
            if (form.getEnq810MainDsp() == GSConstEnquete.CONF_MAIN_DSP_OFF) {
                ret += gsMsg.getMessage("cmn.dont.show");
            } else {
                ret += gsMsg.getMessage("cmn.display.ok");
            }
        }




        return ret;
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @return アクションフォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __setCommitDsp(ActionMapping map,
                                         Enq810Form form,
                                         HttpServletRequest req) throws Exception {

        log__.debug("完了画面表示処理");

        ActionForward forward = null;
        MessageResources msgRes = getResources(req);
        ActionForward urlForward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        // OKボタンクリック時遷移先
        urlForward = map.findForward("enq810commit");
        cmn999Form.setUrlOK(urlForward.getPath());

        // メッセージのセット
        GsMessage gsMsg = new GsMessage();
        String textEnq = gsMsg.getMessage(req, "enq.enq800.01");
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object", textEnq));

        // 画面パラメータをセット
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        ((Enq010Form) form).setHiddenParam(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }
}
