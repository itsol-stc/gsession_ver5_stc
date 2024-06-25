package jp.groupsession.v2.rsv.main;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.rsv110.Rsv110Form;

/**
 * <br>[機  能] 施設予約 予約状況一覧(メイン画面表示用)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvMainAction extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvMainAction.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /**
     * <br>[機  能] 施設予約アクション
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map,
                                                 ActionForm form,
                                                 HttpServletRequest req,
                                                 HttpServletResponse res,
                                                 Connection con)
        throws Exception {

        log__.debug("START");
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);

        ActionForward forward = null;
        RsvMainForm rsvForm = (RsvMainForm) form;

        if (cmd.equals("sisetu_edit")) {
            forward = __doMoveYoyaku(map, rsvForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, rsvForm, req, res, con);
        }


        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    RsvMainForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws SQLException {

        RsvMainBiz biz = new RsvMainBiz();

        con.setAutoCommit(true);
        RequestModel reqMdl = getRequestModel(req);
        //初期表示データセット
        biz.setInitData(form, req, con, reqMdl);
        form.setRsvTopUrl(getPluginConfig(req).getPlugin(
                GSConstReserve.PLUGIN_ID_RESERVE).getTopMenuInfo().getUrl());
        con.setAutoCommit(false);

        return map.getInputForward();
    }
    /**
     * <br>[機  能] 施設予約編集画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     */
    private ActionForward __doMoveYoyaku(ActionMapping map,
                                          RsvMainForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) {

        Rsv110Form nextForm = new Rsv110Form();
        nextForm.setRsvBackPgId(GSConstReserve.DSP_ID_RSVMAIN);
        nextForm.setRsvDspFrom(form.getDspDate());
        nextForm.setRsv110ProcMode(GSConstReserve.PROC_MODE_EDIT);
        nextForm.setRsv110RsySid(NullDefault.getInt(form.getRsvmainSselectedYoyakuSid(), -1));
//        nextForm.setRsv110RsdSid(form.getRsvSelectedSisetuSid());
        req.setAttribute("rsv110Form", nextForm);

        return map.findForward("yoyaku");
    }
}
