package jp.groupsession.v2.anp.anp100kn;


import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.anp.AbstractAnpiAdminAction;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] 管理者設定・メールテンプレート編集確認画面のアクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp100knAction extends AbstractAnpiAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp100knAction.class);

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
        Anp100knForm uform = (Anp100knForm) form;

        //管理者権限確認
        if (!AnpiCommonBiz.isGsAnpiAdmin(getRequestModel(req), con)) {
            return getNotAdminSeniPage(map, req);
        }

        if (cmd.equals("anp100knback")) {
            //戻る
            forward = map.findForward("back");

        } else if (cmd.equals("anp100knexcute")) {
            //確定（完了メッセージ→一覧）
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
    private ActionForward __doInit(ActionMapping map, Anp100knForm form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        return __refresh(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 登録・更新処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doUpdate(ActionMapping map, Anp100knForm form,
                HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("登録・更新処理");

        //入力チェック
        ActionErrors errors = form.validateAnp100(getRequestModel(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __refresh(map, form, req, res, con);
        }

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //登録
        MlCountMtController cntCon = getCountMtController(req);
        Anp100knBiz biz = new Anp100knBiz();
        Anp100knParamModel paramModel = new Anp100knParamModel();
        paramModel.setParam(form);
        biz.doRegist(paramModel, getRequestModel(req), con, cntCon);
        paramModel.setFormData(form);

        //ログ
        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        AnpiCommonBiz anpBiz = new AnpiCommonBiz(con);
        String opCode;
        String value;
        if (StringUtil.isNullZeroStringSpace(form.getAnp090SelectSid())) {
            //登録
            opCode = gsMsg.getMessage("cmn.entry");
        } else {
            //編集
            opCode = gsMsg.getMessage("cmn.edit");
        }
        value = form.getAnp100Title();
        anpBiz.outPutLog(map, getRequestModel(req), opCode, GSConstLog.LEVEL_INFO, value);

        //完了画面を表示
        return __setUpdateDsp(map, form, req);
    }

    /**
     * <br>[機  能] 完了画面表示
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     * @return アクションフォーワード
     */
    private ActionForward __setUpdateDsp(ActionMapping map, Anp100knForm form,
                                    HttpServletRequest req)
                        throws SQLException {

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        MessageResources msgRes = getResources(req);
        String msg = msgRes.getMessage("touroku.kanryo.object", gsMsg.getMessage("anp.anp090.03"));

        return __setMsgDsp(map, form, req, msg);
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
    private ActionForward __refresh(ActionMapping map, Anp100knForm form,
                    HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //初期データ取得
        Anp100knBiz biz = new Anp100knBiz();
        Anp100knParamModel paramModel = new Anp100knParamModel();
        paramModel.setParam(form);
        biz.setInitData(paramModel, con);
        paramModel.setFormData(form);

        return map.getInputForward();
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
    private ActionForward __setMsgDsp(ActionMapping map, Anp100knForm form,
                                    HttpServletRequest req, String msg) {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("list");
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        cmn999Form.setMessage(msg);

        //画面値のセーブ
        form.setHiddenParamAnp010(cmn999Form);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }
}
