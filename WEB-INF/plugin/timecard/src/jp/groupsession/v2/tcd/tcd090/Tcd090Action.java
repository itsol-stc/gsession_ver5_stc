package jp.groupsession.v2.tcd.tcd090;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.tcd.AbstractTimecardAdminAction;


/**
 * <br>[機  能] タイムカード 管理者設定 編集権限設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd090Action extends AbstractTimecardAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd090Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
        HttpServletRequest req, HttpServletResponse res, Connection con)
        throws Exception {

        ActionForward forward = null;
        //管理者権限チェック
        CommonBiz commonBiz = new CommonBiz();
        RequestModel reqMdl = getRequestModel(req);
        boolean isAdmin =
                commonBiz.isPluginAdmin(con,
                        reqMdl.getSmodel(),
                        GSConstTimecard.PLUGIN_ID_TIMECARD);
        if (!isAdmin) {
            return getNotAdminSeniPage(map, req);
        }

        //コマンド取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        cmd = cmd.trim();

        Tcd090Form tcForm = (Tcd090Form) form;

        if (cmd.equals("tcd090_back")) {
            //戻る
            forward = map.findForward("tcd090back");
        } else if (cmd.equals("tcd090_submit")) {
            //OKボタン
            forward = __doSubmit(map, tcForm, req, res, con);
        } else if (cmd.equals("tcd090kn_back")) {
            //再表示
            forward = map.getInputForward();
        } else {
            //初期表示
            forward = __doInit(map, tcForm, req, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Tcd090Form form,
            HttpServletRequest req, Connection con)
            throws Exception {

        con.setAutoCommit(true);
        Tcd090ParamModel paramMdl = new Tcd090ParamModel();
        paramMdl.setParam(form);
        Tcd090Biz biz = new Tcd090Biz();
        biz.setInitData(paramMdl, getRequestModel(req), con);
        paramMdl.setFormData(form);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] OKボタンクリック時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doSubmit(ActionMapping map, Tcd090Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws Exception {

        //トランザクショントークン設定
        saveToken(req);

        return map.findForward("tcd090commit");
    }
}
