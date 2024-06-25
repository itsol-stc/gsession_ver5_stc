package jp.groupsession.v2.rsv.rsv050;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.AbstractReserveSubAction;
import jp.groupsession.v2.rsv.rsv060.Rsv060Form;
import jp.groupsession.v2.rsv.rsv080.Rsv080Form;
import jp.groupsession.v2.rsv.rsv260.Rsv260Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 施設予約 施設グループ情報設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv050Action extends AbstractReserveSubAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv050Action.class);

    /**
    * <br>[機  能] アクションを実行する
    * <br>[解  説]
    * <br>[備  考]
    * @param map ActionMapping
    * @param form ActionForm
    * @param req HttpServletRequest
    * @param res HttpServletResponse
    * @param con DB Connection
    * @return ActionForward
    * @throws Exception 実行時例外
    * @see jp.co.sjts.util.struts.AbstractAction
    * @see #executeAction(org.apache.struts.action.ActionMapping,
    *                      org.apache.struts.action.ActionForm,
    *                      javax.servlet.http.HttpServletRequest,
    *                      javax.servlet.http.HttpServletResponse,
    *                      java.sql.Connection)
    */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Rsv050Form rsvform = (Rsv050Form) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //追加ボタン押下
        if (cmd.equals("sisetu_group_tuika")) {
            log__.debug("追加ボタン押下");
            forward =
                __doMoveGrpEdit(map,
                                rsvform,
                                req,
                                res,
                                con,
                                GSConstReserve.PROC_MODE_SINKI);
        //インポートボタン押下
        } else if (cmd.equals("sisetu_group_all_tuika")) {
            log__.debug("インポートボタン押下");
            forward = __doImportGrpEdit(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_menu")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, rsvform, req, res, con);
        //上へボタン押下
        } else if (cmd.equals("ue_e")) {
            log__.debug("上へボタン押下");
            forward = __doSortChange(map, rsvform, req, res, con, GSConst.SORT_UP);
        //下へボタン押下
        } else if (cmd.equals("sita_e")) {
            log__.debug("下へボタン押下");
            forward = __doSortChange(map, rsvform, req, res, con, GSConst.SORT_DOWN);
        //グループ名リンククリック
        } else if (cmd.equals("sisetu_group_hensyu")) {
            log__.debug("グループ名リンククリック");
            forward =
                __doMoveGrpEdit(map,
                                rsvform,
                                req,
                                res,
                                con,
                                GSConstReserve.PROC_MODE_EDIT);
        //施設情報設定ボタン押下
        } else if (cmd.equals("sisetu_zyoho_settei")) {
            log__.debug("施設情報設定ボタン押下");
            forward = __doMoveSisetuEdit(map, rsvform, req, res, con);

        //施設グループ・施設情報一括エクスポート
        } else if (cmd.equals("sisetu_group_all_export")) {
            log__.debug("施設グループ・施設情報一括エクスポートボタン押下");
            forward = map.findForward("sisetu_group_all_export");

        //初期表示処理
        } else {
            log__.debug("初期表示処理");
            forward = __doInit(map, rsvform, req, res, con);
        }

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
     * @throws Exception 実行時例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Rsv050Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        try {
            con.setAutoCommit(true);
            Rsv050Biz biz = new Rsv050Biz(getRequestModel(req), con);

            //管理者フラグを設定する
            Rsv050ParamModel paramMdl = new Rsv050ParamModel();
            paramMdl.setParam(form);
            biz.setAdmFlg(paramMdl);
            paramMdl.setFormData(form);

            //処理権限判定
            if (!biz.isPossibleToProcess()) {
                //処理権限無し
                return getSubmitErrorPage(map, req);
            }

            //施設グループ一覧をセット
            paramMdl = new Rsv050ParamModel();
            paramMdl.setParam(form);
            biz.setGroupList(paramMdl);
            paramMdl.setFormData(form);

            con.setAutoCommit(false);
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }
        
        if (!isTokenValid(req, false)) {
            saveToken(req);
        }

        return map.getInputForward();
    }

    /**
     * <br>[機  能] ソート順変更処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doSortChange(ActionMapping map,
                                          Rsv050Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con,
                                          int changeKbn) throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;

        //権限チェック
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel buMdl = getRequestModel(req).getSmodel();
        if (!cmnBiz.isPluginAdmin(con, buMdl, getPluginId())) {
            return getSubmitErrorPage(map, req);
        }
        
        //トークンチェック
        if (!isTokenValid(req, false)) {
            return getSubmitErrorPage(map, req);
        }

        try {

            Rsv050Biz biz = new Rsv050Biz(getRequestModel(req), con);
            Rsv050ParamModel paramMdl = new Rsv050ParamModel();
            paramMdl.setParam(form);
            biz.updateSort(paramMdl, changeKbn);
            paramMdl.setFormData(form);

            //ログ出力処理
            AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
            GsMessage gsMsg = new GsMessage();
            rsvBiz.outPutLog(
                    map, req, res, gsMsg.getMessage(req, "cmn.change"),
                    GSConstLog.LEVEL_TRACE, gsMsg.getMessage(req, "reserve.src.15"));

            commitFlg = true;

            return __doInit(map, form, req, res, con);

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 戻るボタン処理
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
    private ActionForward __doBack(ActionMapping map,
                                    Rsv050Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) {

        ActionForward forward = null;

        String backPgId =
            NullDefault.getStringZeroLength(
                    form.getRsvBackPgId(), GSConstReserve.DSP_ID_RSV010);

      //管理者設定画面から遷移してきた場合、管理者設定画面へ戻る
        if (form.getRsvBackToAdminSetting() == 1) {
            forward = map.findForward("kanrisya_settei");

        //予約一覧[週間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV010)) {
            forward = map.findForward("back_to_syukan");

        //予約一覧[日間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV020)) {
            forward = map.findForward("back_to_nikkan");

        //予約一覧[月間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV030)) {
            forward = map.findForward("back_to_gekkan");

        //施設利用状況照会画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV100)) {
            forward = map.findForward("back_to_itiran");
        }

        return forward;
    }

    /**
     * <br>[機  能] 施設グループ登録画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param procMode 処理モード 0=新規 1=編集
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doMoveGrpEdit(ActionMapping map,
                                           Rsv050Form form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con,
                                           String procMode) throws Exception {

        Rsv060Form nextForm = new Rsv060Form();

        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvBackPgId(form.getRsvBackPgId());
        nextForm.setRsv060ProcMode(procMode);
        nextForm.setRsv060EditGrpSid(form.getRsv050EditGrpSid());
        req.setAttribute("rsv060Form", nextForm);

        return map.findForward("sisetu_group");
    }

    /**
     * <br>[機  能] 施設・グループ一括インポート画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doImportGrpEdit(ActionMapping map,
                                           Rsv050Form form,
                                           HttpServletRequest req,
                                           HttpServletResponse res,
                                           Connection con) throws Exception {

        Rsv260Form nextForm = new Rsv260Form();

        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvBackPgId(form.getRsvBackPgId());

        req.setAttribute("rsv260Form", nextForm);

        return map.findForward("sisetu_group_all_tuika");
    }

    /**
     * <br>[機  能] 施設登録画面へ移動
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doMoveSisetuEdit(ActionMapping map,
                                              Rsv050Form form,
                                              HttpServletRequest req,
                                              HttpServletResponse res,
                                              Connection con) throws Exception {

        Rsv080Form nextForm = new Rsv080Form();

        nextForm.setRsvDspFrom(form.getRsvDspFrom());
        nextForm.setRsvSelectedGrpSid(form.getRsvSelectedGrpSid());
        nextForm.setRsvSelectedSisetuSid(form.getRsvSelectedSisetuSid());
        nextForm.setRsvBackPgId(form.getRsvBackPgId());
        nextForm.setRsv080EditGrpSid(form.getRsv050EditGrpSid());
        req.setAttribute("rsv080Form", nextForm);

        return map.findForward("sisetu_zyoho_settei");
    }
}