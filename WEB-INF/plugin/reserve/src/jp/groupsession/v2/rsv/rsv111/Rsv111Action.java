package jp.groupsession.v2.rsv.rsv111;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.rsv110.Rsv110Biz;
import jp.groupsession.v2.rsv.rsv110.Rsv110SisetuModel;

/**
 * <br>[機  能] 施設予約 施設予約拡張登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv111Action extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv111Action.class);

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
        Rsv111Form rsvform = (Rsv111Form) form;

        //登録対象となる施設のチェックを行う
        String procMode = NullDefault.getString(rsvform.getRsv110ProcMode(), "");
        if (!procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
            String sisetsuErrMessage = _checkTargetFactory(con, rsvform.getRsv110RsdSid(), req);
            if (sisetsuErrMessage != null) {
                return __doNotSelectSisetsu(map, rsvform, req, sisetsuErrMessage);
            }
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //一般登録ボタン押下
        if (cmd.equals("ippan")) {
            log__.debug("一般登録ボタン押下");
            forward = map.findForward("ippan");
        //OKボタン押下
        } else if (cmd.equals("kurikaesi_toroku_kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doConfirmation(map, rsvform, req, res, con);
        //削除ボタン押下
        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタン押下");
            forward = __doYoyakuDelete(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_menu")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, rsvform, req, res, con);
        } else if (cmd.equals("copytouroku")) {
        //複写して登録ボタン押下
            log__.debug("複写して登録ボタン押下");
            forward = __doCopy(map, rsvform, req, res, con);
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
                                    Rsv111Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        try {
            con.setAutoCommit(true);

            //管理者設定を反映したプラグイン設定情報を取得
            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

            Rsv111Biz biz = new Rsv111Biz(getRequestModel(req), con);

            Rsv111ParamModel paramMdl = new Rsv111ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl, pconfig, getAppRootPath());
            paramMdl.setFormData(form);

            con.setAutoCommit(false);
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        return __checkViewError(req, map, con,
                map.getInputForward(), form);
    }

    /**
     * <br>[機  能] OKボタン押下処理
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
    private ActionForward __doConfirmation(ActionMapping map,
                                            Rsv111Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        //入力チェック
        ActionErrors errors
            = form.validateRsv111All(req, getRequestModel(req), con, getSessionUserSid(req));
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);

        return map.findForward("kurikaesi_toroku_kakunin");
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
                                    Rsv111Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) {

        ActionForward forward = null;

        String backPgId =
            NullDefault.getStringZeroLength(
                    form.getRsvBackPgId(), GSConstReserve.DSP_ID_RSV010);

        //予約一覧[週間]画面へ戻る
        if (backPgId.equals(GSConstReserve.DSP_ID_RSV010)) {
            forward = map.findForward("back_to_syukan");
        //予約一覧[日間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV020)) {
            forward = map.findForward("back_to_nikkan");
        //予約一覧[月間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV030)) {
            forward = map.findForward("back_to_gekkan");
        //予約状況一覧 メイン画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSVMAIN)) {
            forward = map.findForward("back_to_main");
        }

        return forward;
    }

    /**
     * <br>[機  能] 削除ボタン押下処理
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
    private ActionForward __doYoyakuDelete(ActionMapping map,
                                            Rsv111Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        //入力チェック
        ActionErrors errors = form.validateRsv111Scd(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        saveToken(req);

        return __checkViewError(req, map, con,
                map.findForward("kurikaesi_delete_kakunin"), form);
    }

    /**
     * <br>[機  能] 複写して登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doCopy(ActionMapping map,
            Rsv111Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {

        //複写して登録モードに変更
        form.setRsv110ProcMode(GSConstReserve.PROC_MODE_COPY_ADD);

        //施設SIDをセット
        Rsv110Biz biz = new Rsv110Biz(getRequestModel(req), con);

        Rsv111ParamModel paramMdl = new Rsv111ParamModel();
        paramMdl.setParam(form);
        Rsv110SisetuModel yrkMdl = biz.getYoyakuEditData(paramMdl);
        paramMdl.setFormData(form);

        int sisetuSid = -1;
        if (yrkMdl != null) {
            sisetuSid = yrkMdl.getRsdSid();
        }
        form.setRsv110RsdSid(sisetuSid);

        return __doInit(map, form, req, res, con);

    }

    /**
    *
    * <br>[機  能] 施設予約が閲覧できるかを判定し、エラーメッセージをセットする。
    * <br>[解  説]
    * <br>[備  考]
    * @param req リクエストモデル
    * @param map アクションマッピング
    * @param con コネクション
    * @param forward アクションフォワード
    * @param form フォーム
    * @return foraward アクションフォワード
    * @throws SQLException SQL実行例外
    */
   private ActionForward __checkViewError(HttpServletRequest req,
                                                              ActionMapping map,
                                                              Connection con,
                                                              ActionForward forward,
                                                              Rsv111Form form
                                                              ) throws SQLException {

       //処理モード
       String procMode = form.getRsv110ProcMode();

       if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {

           //セッションユーザSIDを取得
           RequestModel reqMdl = getRequestModel(req);
           BaseUserModel usModel = reqMdl.getSmodel();
           int sessionUsrSid = usModel.getUsrsid();

           //エラーメッセージ
           MessageResources msgRes = getResources(req);
           String errorMessage = null;

           RsvCommonBiz rsvBiz = new RsvCommonBiz();

           int powrange = rsvBiz.isWithinPubilicRange(
                   con, form.getRsv110RsySid(), sessionUsrSid);

           //データが存在しない場合
           if (powrange == GSConstReserve.POWRANGE_DEL) {
               errorMessage = msgRes.getMessage("error.none.edit.reservedata");
               return __doTransitionErrorPage(map, form, req, errorMessage);
           }
           //施設予約の公開区分範囲外である場合
           if (powrange == GSConstReserve.POWRANGE_OUT) {
               errorMessage = msgRes.getMessage("error.not.view.reservedata");
               return __doTransitionErrorPage(map, form, req, errorMessage);
           }
       }

       return forward;
   }

   /**
    * <br>エラー画面設定
    * @param map アクションマッピング
    * @param form アクションフォーム
    * @param req リクエスト
    * @param errMessage エラーメッセージ
    * @return ActionForward
    */
   private ActionForward __doTransitionErrorPage(ActionMapping map, Rsv111Form form,
           HttpServletRequest req, String errMessage) {
       ActionForward forward = null;

       Cmn999Form cmn999Form = new Cmn999Form();
       ActionForward urlForward = __getBackPageForward(map, form);
       //パラメータの設定
       cmn999Form.setType(Cmn999Form.TYPE_OK);
       cmn999Form.setIcon(Cmn999Form.ICON_WARN);
       cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
       cmn999Form.setUrlOK(urlForward.getPath());
       cmn999Form.setMessage(errMessage);

       //画面パラメータをセット
       __setMsgFormParam(cmn999Form, form);
       req.setAttribute("cmn999Form", cmn999Form);

       forward = map.findForward("gf_msg");
       return forward;
   }

    /**
     * <br>予約対象の施設が選択されていない場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param errMessage エラーメッセージ
     * @return ActionForward
     */
    private ActionForward __doNotSelectSisetsu(ActionMapping map, Rsv111Form form,
            HttpServletRequest req, String errMessage) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = __getBackPageForward(map, form);
        //パラメータの設定
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(urlForward.getPath());
        cmn999Form.setMessage(errMessage);

        //画面パラメータをセット
        __setMsgFormParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 戻り先画面を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @return 戻り先画面
     */
    private ActionForward __getBackPageForward(ActionMapping map, Rsv111Form form) {
        ActionForward urlForward = null;
        String backPgId =
                NullDefault.getStringZeroLength(
                        form.getRsvBackPgId(), GSConstReserve.DSP_ID_RSV010);

        //予約一覧[週間]画面へ戻る
        if (backPgId.equals(GSConstReserve.DSP_ID_RSV010)) {
            urlForward = map.findForward("back_to_syukan");
        //予約一覧[日間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV020)) {
            urlForward = map.findForward("back_to_nikkan");
        //予約一覧[月間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV030)) {
            urlForward = map.findForward("back_to_gekkan");
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSVMAIN)) {
            urlForward = map.findForward("back_to_main");
        }

        return urlForward;
    }

    /**
     * <br>[機  能] 共通メッセージ画面Formへパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージ画面Form
     * @param form フォーム
     */
    private void __setMsgFormParam(Cmn999Form cmn999Form, Rsv111Form form) {
        cmn999Form.addHiddenParam("rsvBackPgId", form.getRsvBackPgId());
        cmn999Form.addHiddenParam("rsvDspFrom", form.getRsvDspFrom());
        cmn999Form.addHiddenParam("rsvSelectedGrpSid", form.getRsvSelectedGrpSid());
        cmn999Form.addHiddenParam("rsvSelectedSisetuSid", form.getRsvSelectedSisetuSid());
        cmn999Form.addHiddenParam("rsv100InitFlg",
                String.valueOf(form.isRsv100InitFlg()));
        cmn999Form.addHiddenParam("rsv100SearchFlg",
                String.valueOf(form.isRsv100SearchFlg()));
        cmn999Form.addHiddenParam("rsv100SortKey", form.getRsv100SortKey());
        cmn999Form.addHiddenParam("rsv100OrderKey", form.getRsv100OrderKey());
        cmn999Form.addHiddenParam("rsv100PageTop", form.getRsv100PageTop());
        cmn999Form.addHiddenParam("rsv100PageBottom", form.getRsv100PageBottom());
        cmn999Form.addHiddenParam("rsv100selectedFromYear", form.getRsv100selectedFromYear());
        cmn999Form.addHiddenParam("rsv100selectedFromMonth", form.getRsv100selectedFromMonth());
        cmn999Form.addHiddenParam("rsv100selectedFromDay", form.getRsv100selectedFromDay());
        cmn999Form.addHiddenParam("rsv100selectedToYear", form.getRsv100selectedToYear());
        cmn999Form.addHiddenParam("rsv100selectedToMonth", form.getRsv100selectedToMonth());
        cmn999Form.addHiddenParam("rsv100selectedToDay", form.getRsv100selectedToDay());
        cmn999Form.addHiddenParam("rsv100KeyWord", form.getRsv100KeyWord());
        cmn999Form.addHiddenParam("rsv100SearchCondition", form.getRsv100SearchCondition());
        cmn999Form.addHiddenParam("rsv100TargetMok", form.getRsv100TargetMok());
        cmn999Form.addHiddenParam("rsv100TargetNiyo", form.getRsv100TargetNiyo());
        cmn999Form.addHiddenParam("rsv100CsvOutField", form.getRsv100CsvOutField());
        cmn999Form.addHiddenParam("rsv100SelectedKey1", form.getRsv100SelectedKey1());
        cmn999Form.addHiddenParam("rsv100SelectedKey2", form.getRsv100SelectedKey2());
        cmn999Form.addHiddenParam("rsv100SelectedKey1Sort", form.getRsv100SelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100SelectedKey2Sort", form.getRsv100SelectedKey2Sort());
        cmn999Form.addHiddenParam("rsvIkkatuTorokuKey", form.getRsvIkkatuTorokuKey());
        cmn999Form.addHiddenParam("rsv100svFromYear", form.getRsv100svFromYear());
        cmn999Form.addHiddenParam("rsv100svFromMonth", form.getRsv100svFromMonth());
        cmn999Form.addHiddenParam("rsv100svFromDay", form.getRsv100svFromDay());
        cmn999Form.addHiddenParam("rsv100svToYear", form.getRsv100svToYear());
        cmn999Form.addHiddenParam("rsv100svToMonth", form.getRsv100svToMonth());
        cmn999Form.addHiddenParam("rsv100svToDay", form.getRsv100svToDay());
        cmn999Form.addHiddenParam("rsv100svGrp1", form.getRsv100svGrp1());
        cmn999Form.addHiddenParam("rsv100svGrp2", form.getRsv100svGrp2());
        cmn999Form.addHiddenParam("rsv100svKeyWord", form.getRsv100svKeyWord());
        cmn999Form.addHiddenParam("rsv100svSearchCondition", form.getRsv100svSearchCondition());
        cmn999Form.addHiddenParam("rsv100svTargetMok", form.getRsv100svTargetMok());
        cmn999Form.addHiddenParam("rsv100svTargetNiyo", form.getRsv100svTargetNiyo());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1", form.getRsv100svSelectedKey1());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2", form.getRsv100svSelectedKey2());
        cmn999Form.addHiddenParam("rsv100svSelectedKey1Sort", form.getRsv100svSelectedKey1Sort());
        cmn999Form.addHiddenParam("rsv100svSelectedKey2Sort", form.getRsv100svSelectedKey2Sort());
        cmn999Form.addHiddenParam("rsv100SearchSvFlg",
                String.valueOf(form.isRsv100SearchSvFlg()));

        cmn999Form.addHiddenParam("rsv100dateKbn", form.getRsv100dateKbn());
        cmn999Form.addHiddenParam("rsv100apprStatus", form.getRsv100apprStatus());
        cmn999Form.addHiddenParam("rsv100svDateKbn", form.getRsv100svDateKbn());
        cmn999Form.addHiddenParam("rsv100svApprStatus", form.getRsv100svApprStatus());
    }
}