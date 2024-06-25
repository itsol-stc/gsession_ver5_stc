package jp.groupsession.v2.rsv.rsv111kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.http.GSAuthenticateException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.model.RsvRegSmailModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設予約拡張登録確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv111knAction extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv111knAction.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "rsv111";

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
        Rsv111knForm rsvform = (Rsv111knForm) form;

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

        //削除確認処理時
        if (cmd.equals("delete")) {
            rsvform.setRsv111DeleteFlg(true);
        }

        //戻るボタン押下
        if (cmd.equals("back_to_kurikaeshi_inp")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_to_kurikaeshi_inp");
        //削除ボタン押下
        } else if (cmd.equals("kurikaesi_delete_kakutei")) {
            log__.debug("削除ボタン押下");
            forward = __doDelete(map, rsvform, req, res, con);
        //確定ボタン押下
        } else if (cmd.equals("kurikaesi_toroku_kakutei")) {
            log__.debug("確定ボタン押下");
            forward = __doUpdate(map, rsvform, req, res, con);
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
                                    Rsv111knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        try {
            con.setAutoCommit(true);
            Rsv111knBiz biz = new Rsv111knBiz(getRequestModel(req), con);

            Rsv111knParamModel paramMdl = new Rsv111knParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl);
            paramMdl.setFormData(form);

            con.setAutoCommit(false);
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }

        //権限チェック
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

            //セッションユーザが施設予約の公開範囲内にあるかを判定
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

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 削除ボタン押下時処理
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
    private ActionForward __doDelete(ActionMapping map,
                                      Rsv111knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;
        Rsv111knBiz biz = new Rsv111knBiz(getRequestModel(req), con);

        try {

            //2重投稿
            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            //入力チェック
            ActionErrors errors = form.validateRsv111Scd(getRequestModel(req), con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            //権限チェック
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

                //セッションユーザが施設予約の公開範囲内にあるかを判定
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

            //DB更新

            Rsv111knParamModel paramMdl = new Rsv111knParamModel();
            paramMdl.setParam(form);
            biz.deleteYoyakuData(paramMdl);
            paramMdl.setFormData(form);

            commitFlg = true;

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

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        rsvBiz.outPutLog(map, req, res, gsMsg.getMessage(req, "cmn.delete"), GSConstLog.LEVEL_TRACE,
                biz.getDelOpLog());

        return __doUpdateComp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 確定ボタン押下時処理
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
    private ActionForward __doUpdate(ActionMapping map,
                                      Rsv111knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con) throws Exception {

        con.setAutoCommit(false);
        boolean commitFlg = false;

        BaseUserModel usModel = getSessionUserModel(req);
        if (usModel == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }
        int userSid = usModel.getUsrsid();
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
        int yoyakuSid = 0;
        int sisetsuSid = 0;

        RequestModel reqMdl = getRequestModel(req);
        try {

            //2重投稿
            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            //入力チェック
            ActionErrors errors
                = form.validateRsv111All(req, getRequestModel(req), con, getSessionUserSid(req));
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            Rsv111knBiz biz = new Rsv111knBiz(getRequestModel(req), con);
            int entryUserSid = -1;
            //編集時、登録ユーザSID取得(DB更新前に)
            if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {

                Rsv111knParamModel paramMdl = new Rsv111knParamModel();
                paramMdl.setParam(form);
                entryUserSid = biz.getEntryUserSid(paramMdl);
                paramMdl.setFormData(form);

            }

            //権限チェック
            String procMode = form.getRsv110ProcMode();
            if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)) {
              //セッションユーザSIDを取得
                int sessionUsrSid = usModel.getUsrsid();

                //エラーメッセージ
                MessageResources msgRes = getResources(req);
                String errorMessage = null;
                RsvCommonBiz rsvBiz = new RsvCommonBiz();

                //セッションユーザが施設予約の公開範囲内にあるかを判定
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

            //DB更新
            MlCountMtController cntCon = getCountMtController(req);

            Rsv111knParamModel paramMdl = new Rsv111knParamModel();
            paramMdl.setParam(form);
            List<int[]> sidDataList =
                    biz.updateYoyakuData(paramMdl, cntCon, userSid,
                                        getAppRootPath(), tempDir);
            paramMdl.setFormData(form);
            IOTools.deleteInDir(tempDir);

            CommonBiz cmnBiz = new CommonBiz();
            //編集時のみ
            if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
                //登録者と編集者が別、かつ登録者が更新通知設定
                if (rsvCmnBiz.checkSendSmail(userSid, entryUserSid, con)) {
                    //ショートメールで通知
                    PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con);
                    if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
                        paramMdl = new Rsv111knParamModel();
                        paramMdl.setParam(form);
                        biz.sendSmail(paramMdl, cntCon, userSid, getAppRootPath(),
                                tempDir, getPluginConfig(req), entryUserSid);
                        paramMdl.setFormData(form);
                    }
                }
            }

            //申請通知メール
            for (int[] sidData : sidDataList) {
                yoyakuSid = sidData[0];
                sisetsuSid = sidData[1];
                //選択した施設に承認設定がされている場合
                if (rsvCmnBiz.isApprSis(con, sisetsuSid, userSid)) {
                    //ショートメールで通知
                    PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con);
                    if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
                        RsvRegSmailModel regMdl = new RsvRegSmailModel();
                        regMdl.setCon(con);
                        regMdl.setReqMdl(getRequestModel(req));
                        regMdl.setRsySid(yoyakuSid);
                        regMdl.setRsdSid(sisetsuSid);
                        regMdl.setCntCon(cntCon);
                        regMdl.setUserSid(userSid);
                        regMdl.setAppRootPath(getAppRootPath());
                        regMdl.setTempDir(tempDir);
                        regMdl.setPluginConfig(getPluginConfig(req));

                        rsvCmnBiz.sendRegSmail(regMdl);
                    }
                }
            }

            commitFlg = true;

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

        //テンポラリディレクトリを削除
        IOTools.deleteDir(tempDir);

        //ログ出力処理
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        Rsv111knBiz biz = new Rsv111knBiz(reqMdl, con);
        Rsv111knParamModel paramMdl = new Rsv111knParamModel();
        paramMdl.setParam(form);
        String outOpLog = biz.getOpLog(sisetsuSid, paramMdl);
        paramMdl.setFormData(form);
        String opCode  = "";
        GsMessage gsMsg = new GsMessage();
        if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)
                || form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
            opCode = gsMsg.getMessage(req, "cmn.entry");
        } else if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
            opCode = gsMsg.getMessage(req, "cmn.change");
        }
        rsvBiz.outPutLog(map, req, res, opCode, GSConstLog.LEVEL_TRACE, outOpLog);

        return __doUpdateComp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 予約更新完了後の画面遷移設定
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
    private ActionForward __doUpdateComp(ActionMapping map,
                                          Rsv111knForm form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forwardOk = null;

        //OKボタンクリック時遷移先
        forwardOk = __getBackPageForward(map, form);

        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        String msgId = "";
        if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)
                || form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
            msgId = "touroku.kanryo.object";
        } else if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
            if (form.isRsv111DeleteFlg()) {
                msgId = "sakujo.kanryo.object";
            } else {
                msgId = "hensyu.kanryo.object";
            }
        }

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(msgRes.getMessage(msgId, gsMsg.getMessage(req, "reserve.src.21")));

        //画面パラメータをセット
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

        cmn999Form.addHiddenParam("rsv010LearnMoreFlg", form.getRsv010LearnMoreFlg());
        cmn999Form.addHiddenParam("rsv010sisetuKeyword", form.getRsv010sisetuKeyword());
        cmn999Form.addHiddenParam("rsv010KeyWordkbn", form.getRsv010KeyWordkbn());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisan", form.getRsv010sisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu",
                form.getRsv010sisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordBiko", form.getRsv010sisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordNo", form.getRsv010sisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordIsbn", form.getRsv010sisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010sisetuFree", form.getRsv010sisetuFree());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromY", form.getRsv010sisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMo", form.getRsv010sisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromD", form.getRsv010sisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromH", form.getRsv010sisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMi", form.getRsv010sisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToY", form.getRsv010sisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMo", form.getRsv010sisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToD", form.getRsv010sisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToH", form.getRsv010sisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMi", form.getRsv010sisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010sisetuKbn", form.getRsv010sisetuKbn());
        cmn999Form.addHiddenParam("rsv010grpNarrowDown", form.getRsv010grpNarrowDown());
        cmn999Form.addHiddenParam("rsv010sisetuSmoky", form.getRsv010sisetuSmoky());
        cmn999Form.addHiddenParam("rsv010sisetuChere", form.getRsv010sisetuChere());
        cmn999Form.addHiddenParam("rsv010sisetuTakeout", form.getRsv010sisetuTakeout());
        cmn999Form.addHiddenParam("rsv010svSisetuKeyword", form.getRsv010svSisetuKeyword());
        cmn999Form.addHiddenParam("rsv010svKeyWordkbn", form.getRsv010svKeyWordkbn());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisan",
                form.getRsv010svSisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisetu",
                form.getRsv010svSisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko",
                form.getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn",
                form.getRsv010svSisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010svSisetuFree", form.getRsv010svSisetuFree());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromY", form.getRsv010svSisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMo", form.getRsv010svSisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromD", form.getRsv010svSisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromH", form.getRsv010svSisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMi", form.getRsv010svSisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToY", form.getRsv010svSisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMo", form.getRsv010svSisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToD", form.getRsv010svSisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToH", form.getRsv010svSisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMi", form.getRsv010svSisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010svSisetuKbn", form.getRsv010svSisetuKbn());
        cmn999Form.addHiddenParam("rsv010svGrpNarrowDown", form.getRsv010svGrpNarrowDown());
        cmn999Form.addHiddenParam("rsv010svSisetuSmoky", form.getRsv010svSisetuSmoky());
        cmn999Form.addHiddenParam("rsv010svSisetuChere", form.getRsv010svSisetuChere());
        cmn999Form.addHiddenParam("rsv010svSisetuTakeout", form.getRsv010svSisetuTakeout());
        cmn999Form.addHiddenParam("rsv010InitFlg", form.getRsv010InitFlg());
        cmn999Form.addHiddenParam("rsv010SiborikomiFlg", form.getRsv010SiborikomiFlg());
        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>予約対象の施設が選択されていない場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param errMessage エラーメッセージ
     * @return ActionForward
     */
    private ActionForward __doNotSelectSisetsu(ActionMapping map, Rsv111knForm form,
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
    * <br>エラー画面設定
    * @param map アクションマッピング
    * @param form アクションフォーム
    * @param req リクエスト
    * @param errMessage エラーメッセージ
    * @return ActionForward
    */
   private ActionForward __doTransitionErrorPage(ActionMapping map, Rsv111knForm form,
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
    private ActionForward __getBackPageForward(ActionMapping map, Rsv111knForm form) {
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
    private void __setMsgFormParam(Cmn999Form cmn999Form, Rsv111knForm form) {
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

        cmn999Form.addHiddenParam("rsv010LearnMoreFlg", form.getRsv010LearnMoreFlg());
        cmn999Form.addHiddenParam("rsv010sisetuKeyword", form.getRsv010sisetuKeyword());
        cmn999Form.addHiddenParam("rsv010KeyWordkbn", form.getRsv010KeyWordkbn());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisan", form.getRsv010sisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu",
                form.getRsv010sisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordBiko", form.getRsv010sisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordNo", form.getRsv010sisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordIsbn", form.getRsv010sisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010sisetuFree", form.getRsv010sisetuFree());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromY", form.getRsv010sisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMo", form.getRsv010sisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromD", form.getRsv010sisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromH", form.getRsv010sisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeFromMi", form.getRsv010sisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToY", form.getRsv010sisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMo", form.getRsv010sisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToD", form.getRsv010sisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToH", form.getRsv010sisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010sisetuFreeToMi", form.getRsv010sisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010sisetuKbn", form.getRsv010sisetuKbn());
        cmn999Form.addHiddenParam("rsv010grpNarrowDown", form.getRsv010grpNarrowDown());
        cmn999Form.addHiddenParam("rsv010sisetuSmoky", form.getRsv010sisetuSmoky());
        cmn999Form.addHiddenParam("rsv010sisetuChere", form.getRsv010sisetuChere());
        cmn999Form.addHiddenParam("rsv010sisetuTakeout", form.getRsv010sisetuTakeout());
        cmn999Form.addHiddenParam("rsv010svSisetuKeyword", form.getRsv010svSisetuKeyword());
        cmn999Form.addHiddenParam("rsv010svKeyWordkbn", form.getRsv010svKeyWordkbn());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisan",
                form.getRsv010svSisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordSisetu",
                form.getRsv010svSisetuKeywordSisetu());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko",
                form.getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn",
                form.getRsv010svSisetuKeywordIsbn());
        cmn999Form.addHiddenParam("rsv010svSisetuFree", form.getRsv010svSisetuFree());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromY", form.getRsv010svSisetuFreeFromY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMo", form.getRsv010svSisetuFreeFromMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromD", form.getRsv010svSisetuFreeFromD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromH", form.getRsv010svSisetuFreeFromH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeFromMi", form.getRsv010svSisetuFreeFromMi());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToY", form.getRsv010svSisetuFreeToY());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMo", form.getRsv010svSisetuFreeToMo());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToD", form.getRsv010svSisetuFreeToD());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToH", form.getRsv010svSisetuFreeToH());
        cmn999Form.addHiddenParam("rsv010svSisetuFreeToMi", form.getRsv010svSisetuFreeToMi());
        cmn999Form.addHiddenParam("rsv010svSisetuKbn", form.getRsv010svSisetuKbn());
        cmn999Form.addHiddenParam("rsv010svGrpNarrowDown", form.getRsv010svGrpNarrowDown());
        cmn999Form.addHiddenParam("rsv010svSisetuSmoky", form.getRsv010svSisetuSmoky());
        cmn999Form.addHiddenParam("rsv010svSisetuChere", form.getRsv010svSisetuChere());
        cmn999Form.addHiddenParam("rsv010svSisetuTakeout", form.getRsv010svSisetuTakeout());
        cmn999Form.addHiddenParam("rsv010InitFlg", form.getRsv010InitFlg());
        cmn999Form.addHiddenParam("rsv010SiborikomiFlg", form.getRsv010SiborikomiFlg());
    }
}