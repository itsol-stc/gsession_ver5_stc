package jp.groupsession.v2.anp.anp150kn;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.sml.sml180.Sml180Action;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 安否確認 管理者設定 緊急連絡先一括設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp150knAction extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml180Action.class);

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォアード
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        Anp150knForm uform = (Anp150knForm) form;

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp150knback")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp150knexcute")) {
            //確定（完了メッセージ→管理者設定メニュー）
            forward = __doUpdate(map, uform, req, res, con);

        } else {
            //初期化
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __doInit(ActionMapping map, Anp150knForm form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return __refresh(map, form, req, res, con);
    }


    /**
     * <br>[機  能] 表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __refresh(ActionMapping map, Anp150knForm form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //初期データ取得
        Anp150knBiz biz = new Anp150knBiz();
        Anp150knParamModel paramModel = new Anp150knParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, con);
        paramModel.setFormData(form);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 更新処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 例外
     * @return アクションフォアード
     */
    private ActionForward __doUpdate(ActionMapping map, Anp150knForm form,
                HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("更新処理");

        //入力チェック
        ActionErrors errors = form.validateCheck(map, getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __refresh(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //セッション情報を取得
      HttpSession session = req.getSession();
      BaseUserModel usModel = (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
      int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID


       //登録
        Anp150knBiz biz = new Anp150knBiz();
        Anp150knParamModel paramModel = new Anp150knParamModel();
        paramModel.setParam(form);
        int count = biz.updatePriMail(con, paramModel, sessionUsrSid);
        paramModel.setFormData(form);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode = gsMsg.getMessage("cmn.edit");
        String value = count + gsMsg.getMessage("cmn.user");
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, value);


        //完了画面を表示
        return __setUpdateDsp(map, form, req);
    }

    /**
     * <br>[機  能] 完了画面
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __setUpdateDsp(ActionMapping map, Anp150knForm form,
                                    HttpServletRequest req)
                        throws Exception {

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        String msg = msgRes.getMessage("cmn.kanryo.object", gsMsg.getMessage("anp.anp150.01"));

        return __setMsgDsp(map, form, req, msg);
    }

    /**
     * <br>[機  能] メッセージ画面のパラメータセット
     * <br>[解  説] OKボタンのみのメッセージ画面を表示する
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param msg メッセージ文字列
     * @return ActionForward
     */
    private ActionForward __setMsgDsp(ActionMapping map, Anp150knForm form,
                                    HttpServletRequest req, String msg) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk;

        forwardOk = map.findForward("admtool");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        cmn999Form.setMessage(msg);

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}