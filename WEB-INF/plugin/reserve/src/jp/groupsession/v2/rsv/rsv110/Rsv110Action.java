package jp.groupsession.v2.rsv.rsv110;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisGrpDao;
import jp.groupsession.v2.rsv.model.RsvRegSmailModel;
import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.pdf.RsvTanPdfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設予約登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv110Action extends AbstractReserveAction {

    /** 承認区分 承認 */
    private static final int APPR_TYPE_APPROVAL__ = 0;
    /** 承認区分 否認 */
    private static final int APPR_TYPE_REJECTION__ = 1;
    /** 承認区分 承認待ち */
    private static final int APPR_TYPE_WAIT__ = 2;
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "rsv110";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv110Action.class);

    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();

        if (cmd.equals("pdf")) {
            log__.debug("PDFファイルダウンロード");
            return true;
        }

        return false;
    }

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
        Rsv110Form rsvform = (Rsv110Form) form;

        //登録対象となる施設のチェックを行う
        String procMode = NullDefault.getString(rsvform.getRsv110ProcMode(), "");
        if (!procMode.equals(GSConstReserve.PROC_MODE_EDIT)
        && !procMode.equals(GSConstReserve.PROC_MODE_POPUP)) {
            String sisetsuErrMessage = _checkTargetFactory(con, rsvform.getRsv110RsdSid(), req);
            if (sisetsuErrMessage != null) {
                return __doTransitionErrorPage(map, rsvform, req, sisetsuErrMessage, procMode);
            }
        }


        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //OKボタン押下
        if (cmd.equals("sisetu_yoyaku_kakunin")) {
            log__.debug("OKボタン押下");
            forward = __doConfirmation(map, rsvform, req, res, con);
        //削除ボタン押下
        } else if (cmd.equals("delete")) {
            log__.debug("削除ボタン押下");
            forward = __doYoyakuDelete(map, rsvform, req, res, con);
        //削除確認画面でOKボタン押下
        } else if (cmd.equals("deleteOk")) {
            log__.debug("削除確認画面でOKボタン押下");
            forward = __doYoyakuDeleteOk(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_menu")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, rsvform, req, res, con);
        //繰り返し登録ボタン押下
        } else if (cmd.equals("kurikaeshi")) {
            log__.debug("繰り返し登録ボタン押下");
            forward = map.findForward("kurikaeshi");
        } else if (cmd.equals("copytouroku")) {
            //複写して登録ボタン押下
            log__.debug("複写して登録ボタン押下");
            forward = __doCopy(map, rsvform, req, res, con);
        //承認確認画面でOKボタン押下
        } else if (cmd.equals("rsvApprovalOk")) {
            log__.debug("承認確認画面でOKボタン押下");
            forward = __doYoyakuApprOk(map, rsvform, req, res, con, APPR_TYPE_APPROVAL__);
        //却下確認画面でOKボタン押下
        } else if (cmd.equals("rsvRejectionOk")) {
            log__.debug("却下確認画面でOKボタン押下");
            forward = __doYoyakuApprOk(map, rsvform, req, res, con, APPR_TYPE_REJECTION__);
        } else if (cmd.equals("rsvWaitOk")) {
            log__.debug("承認待ち確認画面でOKボタン押下");
            forward = __doYoyakuApprOk(map, rsvform, req, res, con, APPR_TYPE_WAIT__);

            //PDF出力
        } else if (cmd.equals("pdf")) {
            log__.debug("ＰＤＦファイルダウンロード");
            forward = __doDownLoadPdf(map, rsvform, req, res, con);

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
                                    Rsv110Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        try {
            con.setAutoCommit(true);

            //管理者設定を反映したプラグイン設定情報を取得
            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

            Rsv110Biz biz = new Rsv110Biz(getRequestModel(req), con);

            Rsv110ParamModel paramMdl = new Rsv110ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl, pconfig, getSessionUserSid(req), getAppRootPath());
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
                                            Rsv110Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        boolean commitFlg = false;

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }
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

            //セッション情報を取得
            HttpSession session = req.getSession();
            BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

            //システム管理者グループ所属
            CommonBiz cmnBiz = new CommonBiz();
            boolean admFlg = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);
            //編集権限チェック
            if (!__isEditRsvGrp(con, Integer.valueOf(form.getRsvSelectedGrpSid()),
                                                               getSessionUserSid(req), admFlg)) {
                return __doCantUpdate(map, form, req, res, con);
            }

            //入力チェック
            ActionErrors errors
                = form.validateRsv110All(getRequestModel(req), con, getSessionUserSid(req));
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            Rsv110Biz biz = new Rsv110Biz(getRequestModel(req), con);
            int entryUserSid = -1;
            //編集時、登録ユーザSID取得(DB更新前に)
            if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
                Rsv110ParamModel paramMdl = new Rsv110ParamModel();
                paramMdl.setParam(form);
                entryUserSid = biz.getEntryUserSid(paramMdl);
                paramMdl.setFormData(form);
            }

            //DB更新
            MlCountMtController cntCon = getCountMtController(req);

            Rsv110ParamModel paramMdl = new Rsv110ParamModel();
            paramMdl.setParam(form);
            int[] sidData = biz.updateYoyakuData(paramMdl, cntCon, userSid, getAppRootPath());
            paramMdl.setFormData(form);

            //編集時のみ
            if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
                //登録者と編集者が別、かつ登録者が更新通知設定
                if (rsvCmnBiz.checkSendSmail(userSid, entryUserSid, con)) {
                    //ショートメールで通知
                    PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con);
                    if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {

                        paramMdl = new Rsv110ParamModel();
                        paramMdl.setParam(form);
                        biz.sendSmail(paramMdl, cntCon, userSid, getAppRootPath(),
                                tempDir, getPluginConfig(req), entryUserSid);
                        paramMdl.setFormData(form);

                    }
                }
            }

            //申請通知メール
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

        //ログ出力準備
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        Rsv110Biz biz = new Rsv110Biz(reqMdl, con);
        StringBuilder opCode = new StringBuilder();
        Rsv110ParamModel paramMdl = new Rsv110ParamModel();
        paramMdl.setParam(form);
        String outOpLog = biz.getTourokuOpLog(sisetsuSid, paramMdl);
        paramMdl.setFormData(form);

        //登録or編集
        if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)
                || form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
            opCode.append(gsMsg.getMessage("cmn.entry"));
        } else if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
            opCode.append(gsMsg.getMessage("cmn.change"));
        }

        //ログ出力処理
        rsvBiz.outPutLog(map, req, res, opCode.toString(),
                GSConstLog.LEVEL_TRACE, outOpLog);

        return __checkViewError(req, map, con,
                __doUpdateComp(map, form, req, res, con), form);
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
                                          Rsv110Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forwardOk = null;

        //OKボタンクリック時遷移先
        String backPgId =
            NullDefault.getStringZeroLength(
                    form.getRsvBackPgId(), GSConstReserve.DSP_ID_RSV010);

        //予約一覧[週間]画面へ戻る
        if (backPgId.equals(GSConstReserve.DSP_ID_RSV010)) {
            forwardOk = map.findForward("back_to_syukan");
        //予約一覧[日間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV020)) {
            forwardOk = map.findForward("back_to_nikkan");
        //予約一覧[月間]画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV030)) {
            forwardOk = map.findForward("back_to_gekkan");
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSVMAIN)) {
            forwardOk = map.findForward("back_to_main");
        }
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        String msgId = "";
        if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)
                || form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
            msgId = "touroku.kanryo.object";
        } else if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
            msgId = "hensyu.kanryo.object";
        }

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(msgRes.getMessage(msgId, gsMsg.getMessage(req, "reserve.src.22")));

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
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu", form.getRsv010sisetuKeywordSisetu());
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
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko", form.getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn", form.getRsv010svSisetuKeywordIsbn());
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
                                            Rsv110Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        //入力チェック
        ActionErrors errors = form.validateRsv110Scd(getRequestModel(req), con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return __checkViewError(req, map, con,
                __doYoyakuDeleteCheckDsp(map, form, req, res, con), form);
    }

    /**
     * <br>[機  能] 削除確認画面でOKボタン押下時処理
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
    private ActionForward __doYoyakuDeleteOk(ActionMapping map,
                                              Rsv110Form form,
                                              HttpServletRequest req,
                                              HttpServletResponse res,
                                              Connection con) throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;
        //編集権限チェック
        if (!__isEditRsvGrp(con, req, Integer.valueOf(form.getRsvSelectedGrpSid()))) {
            return __doCantDelete(map, form, req, res, con);
        }

        RequestModel reqMdl = getRequestModel(req);
        try {
            Rsv110Biz biz = new Rsv110Biz(reqMdl, con);
            //削除処理実行
            Rsv110ParamModel paramMdl = new Rsv110ParamModel();
            paramMdl.setParam(form);
            String outOpLog = biz.getOpLog(paramMdl);
            biz.doYoyakuDelete(paramMdl);
            paramMdl.setFormData(form);
            GsMessage gsMsg = new GsMessage(reqMdl);
            //ログ出力処理
            AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
            rsvBiz.outPutLog(map, req, res, gsMsg.getMessage("cmn.delete"),
                    GSConstLog.LEVEL_TRACE, outOpLog);

            commitFlg = true;
        } catch (Exception e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
        return __doDeleteComp(map, form, req, res, con);
    }

    /**
     * <br>[機  能] 承認/却下確認画面でOKボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param apprType 0:承認 1:否認
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doYoyakuApprOk(ActionMapping map,
                                              Rsv110Form form,
                                              HttpServletRequest req,
                                              HttpServletResponse res,
                                              Connection con,
                                              int apprType) throws Exception {

        //施設グループ管理者以外の場合、不正なアクセスとする
        Rsv110Biz biz = new Rsv110Biz(getRequestModel(req), con);
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();

        boolean sisGrpAdmFlg = false;

        Rsv110ParamModel paramMdl = new Rsv110ParamModel();
        paramMdl.setParam(form);
        sisGrpAdmFlg = rsvCmnBiz.isSisGrpAdmin(
                con, paramMdl.getRsv110RsySid(), getSessionUserSid(req));
        paramMdl.setFormData(form);

        if (!sisGrpAdmFlg) {
            return getSubmitErrorPage(map, req);
        }

        if (apprType == APPR_TYPE_REJECTION__
        && form.getRsv110rejectDel() == 1) {
            //却下 かつ 却下時に施設予約情報を削除する場合
            //削除可能な施設予約かを確認する
            ActionErrors errors = form.validateRsv110Scd(getRequestModel(req), con);
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }
        }

        con.setAutoCommit(false);
        boolean commitFlg = false;

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        String msgKey = "";
        int procMode = 0;
        int delMode = 0;
        try {
            if (apprType == APPR_TYPE_REJECTION__) {
                msgKey = "cmn.rejection";
                procMode = GSConstReserve.RSY_APPR_KBN_REJECTION;

                if (form.getRsv110rejectDel() == 1) {

                    //却下時に施設予約情報を削除
                    if (!__isEditRsvGrp(con, req, Integer.valueOf(form.getRsvSelectedGrpSid()))) {
                        return __doCantDelete(map, form, req, res, con);
                    }
                    delMode = 1;
                    //決議メールの送信
                    __sendSmail(form, req, con, usModel, procMode, delMode);

                    paramMdl = new Rsv110ParamModel();
                    paramMdl.setParam(form);
                    biz.doYoyakuDelete(paramMdl);
                    paramMdl.setFormData(form);

                } else {
                    //却下
                    paramMdl = new Rsv110ParamModel();
                    paramMdl.setParam(form);
                    biz.updateYoyakuAppr(paramMdl,
                            GSConstReserve.RSY_APPR_KBN_REJECTION, usModel.getUsrsid());
                    paramMdl.setFormData(form);

                    //決議メールの送信
                    __sendSmail(form, req, con, usModel, procMode, delMode);
                }
            } else if (apprType == APPR_TYPE_APPROVAL__) {
                //承認
                msgKey = "cmn.approval";
                procMode = GSConstReserve.RSY_APPR_KBN_APPROVAL;

                paramMdl = new Rsv110ParamModel();
                paramMdl.setParam(form);
                biz.updateYoyakuAppr(paramMdl,
                        GSConstReserve.RSY_APPR_KBN_APPROVAL, usModel.getUsrsid());
                paramMdl.setFormData(form);

                //決議メールの送信
                __sendSmail(form, req, con, usModel, procMode, delMode);

            } else if (apprType == APPR_TYPE_WAIT__) {
                //承認待ち
                msgKey = "reserve.appr.st1";
                procMode = GSConstReserve.RSY_APPR_KBN_NOSET;

                paramMdl = new Rsv110ParamModel();
                paramMdl.setParam(form);
                biz.updateYoyakuAppr(paramMdl,
                        GSConstReserve.RSY_APPR_KBN_NOSET, usModel.getUsrsid());
                paramMdl.setFormData(form);

                //決議メールの送信
                __sendSmail(form, req, con, usModel, procMode, delMode);
            }

            commitFlg = true;

        } catch (Exception e) {
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
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        GsMessage gsMsg = new GsMessage();
        rsvBiz.outPutLog(map, req, res, gsMsg.getMessage(req, msgKey),
                        GSConstLog.LEVEL_TRACE,
                        "[mokuteki]" + form.getRsv110Mokuteki());

        return __doApprComp(map, form, req, res, con, apprType);
    }

    /**
     * <br>[機  能] 決裁メールの送信する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form Rsv110Form
     * @param req リクエスト
     * @param con コネクション
     * @param usModel usModel
     * @param procMode 処理モード
     * @param delMode 削除モード 0:非削除 1:削除
     * @throws  Exception 実行例外
     * @throws  SQLException SQL実行例外
     */
    private void __sendSmail(Rsv110Form form, HttpServletRequest req,
            Connection con, BaseUserModel usModel, int procMode, int delMode)
            throws Exception, SQLException {

        RequestModel reqMdl = getRequestModel(req);
        Rsv110Biz biz = new Rsv110Biz(reqMdl, con);
        //登録ユーザSID取得
        int entryUserSid = -1;
        entryUserSid = biz.getEntryUserSid(form.getRsv110RsySid());
        if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)
                || form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_POPUP)) {
            GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
            String tempDir = temp.getTempPath(getRequestModel(req),
                    GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);
            MlCountMtController cntCon = getCountMtController(req);

            CommonBiz cmnBiz = new CommonBiz();

            //登録者と承認・却下実行者が別の場合
            if (usModel.getUsrsid() != entryUserSid) {
                //ショートメールで通知
                PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con);
                if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
                    Rsv110SMailModel smailMdl = new Rsv110SMailModel();
                    smailMdl.setCon(con);
                    smailMdl.setReqMdl(reqMdl);
                    smailMdl.setRsySid(form.getRsv110RsySid());
                    smailMdl.setCntCon(cntCon);
                    smailMdl.setUserSid(usModel.getUsrsid());
                    smailMdl.setAppRootPath(getAppRootPath());
                    smailMdl.setTempDir(tempDir);
                    smailMdl.setPluginConfig(getPluginConfig(req));
                    smailMdl.setProcMode(procMode);
                    smailMdl.setDelMode(delMode);
                    biz.sendSmail(smailMdl);
                }
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
                                    Rsv110Form form,
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
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSVMAIN)) {
            forward = map.findForward("back_to_main");
        }

        return forward;
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
            Rsv110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con
            )
            throws Exception {

        //複写して登録モードに変更
        form.setRsv110ProcMode(GSConstReserve.PROC_MODE_COPY_ADD);

        //施設SIDをセット
        Rsv110Biz biz = new Rsv110Biz(getRequestModel(req), con);

        Rsv110ParamModel paramMdl = new Rsv110ParamModel();
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
     * <br>[機  能] 削除ボタン押下時確認画面表示処理
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
    private ActionForward __doYoyakuDeleteCheckDsp(ActionMapping map,
                                                    Rsv110Form form,
                                                    HttpServletRequest req,
                                                    HttpServletResponse res,
                                                    Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = map.findForward("redraw");
        cmn999Form.setUrlOK(forwardOk.getPath() + "?" + GSConst.P_CMD + "=deleteOk");

        //キャンセルボタンクリック時遷移先
        cmn999Form.setUrlCancel(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
             msgRes.getMessage("delete.deleteclick.yoyaku"));

        //画面パラメータをセット
        __setMsgFormParamToConfirm(cmn999Form, form);

        cmn999Form.addHiddenParam("rsv110Contact", form.getRsv110Contact());
        cmn999Form.addHiddenParam("rsv110Guide", form.getRsv110Guide());
        cmn999Form.addHiddenParam("rsv110ParkNum", form.getRsv110ParkNum());
        cmn999Form.addHiddenParam("rsv110Busyo", form.getRsv110Busyo());
        cmn999Form.addHiddenParam("rsv110UseName", form.getRsv110UseName());
        cmn999Form.addHiddenParam("rsv110UseNum", form.getRsv110UseNum());
        cmn999Form.addHiddenParam("rsv110Dest", form.getRsv110Dest());
        cmn999Form.addHiddenParam("rsv110PrintKbn", form.getRsv110PrintKbn());
        cmn999Form.addHiddenParam("rsv110UseKbn", form.getRsv110UseKbn());
        cmn999Form.addHiddenParam("rsv110SchGroupSid", form.getRsv110SchGroupSid());
        cmn999Form.addHiddenParam("rsv110SchKbn", form.getRsv110SchKbn());
        cmn999Form.addHiddenParam("rsv110Public", form.getRsv110Public());
        cmn999Form.addHiddenParam("rsv110PubUserGroup", form.getRsv110PubUserGroup());
        cmn999Form.addHiddenParam("rsv110PubUsrGrpSid", form.getRsv110PubUsrGrpSid());
        cmn999Form.addHiddenParam("rsv111SchGroupSid", form.getRsv111SchGroupSid());
        cmn999Form.addHiddenParam("rsv111SchKbn", form.getRsv111SchKbn());
        cmn999Form.addHiddenParam("rsv111RsrPublic", form.getRsv111RsrPublic());
        cmn999Form.addHiddenParam("rsv111PubUserGroup", form.getRsv111PubUserGroup());
        cmn999Form.addHiddenParam("rsv111PubUsrGrpSid", form.getRsv111PubUsrGrpSid());


        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 削除処理完了後の画面遷移設定
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
    private ActionForward __doDeleteComp(ActionMapping map,
                                          Rsv110Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = __doBack(map, form, req, res, con);
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
             msgRes.getMessage("sakujo.kanryo.object", gsMsg.getMessage(req, "reserve.src.21")));

        //画面パラメータをセット
        __setMsgFormParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    /**
     * <br>[機  能] 削除処理完了後の画面遷移設定
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
    private ActionForward __doCantDelete(ActionMapping map,
                                          Rsv110Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        //OKボタンクリック時遷移先
        ActionForward forwardOk = __doBack(map, form, req, res, con);
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(
             msgRes.getMessage("error.myself.auth", gsMsg.getMessage(req, "reserve.src.21")));

        //画面パラメータをセット
        __setMsgFormParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 承認/削除処理完了後の画面遷移設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @param apprType 0:承認 1:否認
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doApprComp(ActionMapping map,
                                          Rsv110Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con,
                                          int apprType) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        if (NullDefault.getString(form.getRsv110ProcMode(), "").equals(
                GSConstReserve.PROC_MODE_POPUP)) {
            cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
        } else {
            //OKボタンクリック時遷移先
            ActionForward forwardOk = __doBack(map, form, req, res, con);
            cmn999Form.setUrlOK(forwardOk.getPath());
        }
        
        String mokuteki = StringUtilHtml.transToHTmlPlusAmparsant(form.getRsv110Mokuteki());

        //メッセージ
        MessageResources msgRes = getResources(req);
        if (apprType == APPR_TYPE_REJECTION__) {
            cmn999Form.setMessage(
                    msgRes.getMessage("denial.kanryo.ringi2", mokuteki));
        } else {
            cmn999Form.setMessage(
                    msgRes.getMessage("approval.kanryo.ringi2", mokuteki));
        }


        //画面パラメータをセット
        __setMsgFormParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
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
                                                               Rsv110Form form
                                                               ) throws SQLException {

        //処理モード
        String procMode = form.getRsv110ProcMode();

        if (procMode.equals(GSConstReserve.PROC_MODE_EDIT)
                || procMode.equals(GSConstReserve.PROC_MODE_COPY_ADD)
                       || procMode.equals(GSConstReserve.PROC_MODE_POPUP)) {

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
                return __doTransitionErrorPage(map, form, req, errorMessage, procMode);
            }
            //施設予約の公開区分範囲外である場合
            if (powrange == GSConstReserve.POWRANGE_OUT) {
                errorMessage = msgRes.getMessage("error.not.view.reservedata");
                return __doTransitionErrorPage(map, form, req, errorMessage, procMode);
            }

        }
        
        saveToken(req);

        return forward;
    }

    /**
     * <br>エラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param errMessage エラーメッセージ
     * @param procMode 処理モード
     * @return ActionForward
     */
    private ActionForward __doTransitionErrorPage(ActionMapping map, Rsv110Form form,
            HttpServletRequest req, String errMessage, String procMode) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = null;
        ActionForward urlForward = __getBackPageForward(map, form);
        //パラメータの設定
        if (procMode.equals(GSConstReserve.PROC_MODE_POPUP)) {
            cmn999Form = new Cmn999Form("javascript:window.parent.callYokyakuWindowClose();");
            cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
            cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);
        } else {
            cmn999Form = new Cmn999Form();
            cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
            cmn999Form.setUrlOK(urlForward.getPath());
        }
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
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
    private ActionForward __getBackPageForward(ActionMapping map, Rsv110Form form) {
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
     * <br>[機  能] 予約可能な施設グループか判定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @param rsgSid 施設グループSID
     * @return ret 予約可能:true 予約不可:false
     * @throws SQLException SQL実行時例外
     */
    private boolean __isEditRsvGrp(Connection con, HttpServletRequest req, int rsgSid)
        throws SQLException {

        //セッション情報を取得
        HttpSession session = req.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);

        //システム管理者グループ所属
        CommonBiz cmnBiz = new CommonBiz();
        boolean admFlg = cmnBiz.isPluginAdmin(con, usModel, GSConstReserve.PLUGIN_ID_RESERVE);

        if (admFlg) {
            return true;
        }

        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con);
        RsvSisGrpModel grpModel = rsgDao.select(rsgSid);

        //制限方法
        int limitKbn = GSConstReserve.RSV_ACCESS_MODE_FREE;
        if (grpModel != null) {
            limitKbn = grpModel.getRsgAcsLimitKbn();
        }
        if (limitKbn == GSConstReserve.RSV_ACCESS_MODE_FREE) {
            return true;
        }

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> accessList
            = dao.getCanEditData(getSessionUserSid(req), rsgSid);
        if (accessList != null && accessList.size() > 0) {
            return true;
        }

        return false;
    }


    /**
     * <br>[機  能] PDFファイルダウンロード処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws SQLException SQL実行例外
     * @throws Exception 実行時例外
     */
    private ActionForward __doDownLoadPdf(ActionMapping map,
            Rsv110Form form,
            HttpServletRequest req,
            HttpServletResponse res,
            Connection con)
        throws SQLException, Exception {

        log__.debug("ファイルダウンロード処理(施設予約単票PDF)");

        RequestModel reqMdl = getRequestModel(req);

        //管理者設定を反映したプラグイン設定情報を取得
        PluginConfig pconfig
            = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

        //アプリケーションルートパス取得
        String appRootPath = getAppRootPath();
        //プラグイン固有のテンポラリパス取得
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String outTempDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        Rsv110Biz biz = new Rsv110Biz(reqMdl, con);
        //PDF生成
        Rsv110ParamModel paramMdl = new Rsv110ParamModel();
        paramMdl.setParam(form);
        RsvTanPdfModel pdfMdl =
                biz.createRsvTanPdf(
                        paramMdl, appRootPath, outTempDir, pconfig, getSessionUserSid(req));
        paramMdl.setFormData(form);

        String outBookName = pdfMdl.getFileName();
        String saveFileName = pdfMdl.getSaveFileName();
        String outFilePath = IOTools.setEndPathChar(outTempDir) + saveFileName;
        //ログ出力処理
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        GsMessage gsMsg = new GsMessage(req);
        String logCode =  "PDF出力 rsvSid：" + form.getRsvSelectedSisetuSid();
        rsvBiz.outPutLog(map, req, res, gsMsg.getMessage("cmn.pdf"),
                        GSConstLog.LEVEL_TRACE,
                        outBookName, logCode);
        TempFileUtil.downloadAtachment(req, res, outFilePath, outBookName, Encoding.UTF_8);
        //TEMPディレクトリ削除
        temp.deleteTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);

        return null;
    }

    /**
     * <br>[機  能] 共通メッセージ画面Formへパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージ画面Form
     * @param form フォーム
     */
    private void __setMsgFormParam(Cmn999Form cmn999Form, Rsv110Form form) {
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
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu", form.getRsv010sisetuKeywordSisetu());
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
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko", form.getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn", form.getRsv010svSisetuKeywordIsbn());
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

    /**
     * <br>[機  能] 共通メッセージ画面Formへパラメータを設定する(確認画面用)
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージ画面Form
     * @param form フォーム
     */
    private void __setMsgFormParamToConfirm(Cmn999Form cmn999Form, Rsv110Form form) {
        __setMsgFormParam(cmn999Form, form);

        cmn999Form.addHiddenParam("rsv110ProcMode", form.getRsv110ProcMode());
        cmn999Form.addHiddenParam("rsv110InitFlg", String.valueOf(form.isRsv110InitFlg()));
        cmn999Form.addHiddenParam("rsv110RsySid", form.getRsv110RsySid());
        cmn999Form.addHiddenParam("rsv110RsdSid", form.getRsv110RsdSid());
        cmn999Form.addHiddenParam("rsv110SinkiDefaultDate", form.getRsv110SinkiDefaultDate());
        cmn999Form.addHiddenParam("rsv110Mokuteki", form.getRsv110Mokuteki());
        cmn999Form.addHiddenParam("rsv110SelectedYearFr", form.getRsv110SelectedYearFr());
        cmn999Form.addHiddenParam("rsv110SelectedMonthFr", form.getRsv110SelectedMonthFr());
        cmn999Form.addHiddenParam("rsv110SelectedDayFr", form.getRsv110SelectedDayFr());
        cmn999Form.addHiddenParam("rsv110SelectedHourFr", form.getRsv110SelectedHourFr());
        cmn999Form.addHiddenParam("rsv110SelectedMinuteFr", form.getRsv110SelectedMinuteFr());
        cmn999Form.addHiddenParam("rsv110SelectedYearTo", form.getRsv110SelectedYearTo());
        cmn999Form.addHiddenParam("rsv110SelectedMonthTo", form.getRsv110SelectedMonthTo());
        cmn999Form.addHiddenParam("rsv110SelectedDayTo", form.getRsv110SelectedDayTo());
        cmn999Form.addHiddenParam("rsv110SelectedHourTo", form.getRsv110SelectedHourTo());
        cmn999Form.addHiddenParam("rsv110SelectedMinuteTo", form.getRsv110SelectedMinuteTo());
        cmn999Form.addHiddenParam("rsv110Naiyo", form.getRsv110Naiyo());
        cmn999Form.addHiddenParam("rsv110ScdRsSid", form.getRsv110ScdRsSid());
        cmn999Form.addHiddenParam("rsv110ScdReflection", form.getRsv110ScdReflection());
        cmn999Form.addHiddenParam("rsv110RsyEdit", form.getRsv110RsyEdit());
        cmn999Form.addHiddenParam("rsv110EditAuth", String.valueOf(form.isRsv110EditAuth()));
        cmn999Form.addHiddenParam("rsv110rejectDel", form.getRsv110rejectDel());
        cmn999Form.addHiddenParam("rsv110ApprBtnFlg", form.getRsv110ApprBtnFlg());
        cmn999Form.addHiddenParam("rsv110Public", form.getRsv110Public());

        cmn999Form.addHiddenParam("sv_users", form.getSv_users());
        cmn999Form.addHiddenParam("rsv111SvUsers", form.getRsv111SvUsers());
        cmn999Form.addHiddenParam("rsv110GroupSid", form.getRsv110GroupSid());
        cmn999Form.addHiddenParam("rsv111GroupSid", form.getRsv111GroupSid());

        cmn999Form.addHiddenParam("rsv111InitFlg", String.valueOf(form.isRsv111InitFlg()));
        cmn999Form.addHiddenParam("rsv111RsrRsid", form.getRsv111RsrRsid());
        cmn999Form.addHiddenParam("rsv111RsrKbn", form.getRsv111RsrKbn());
        cmn999Form.addHiddenParam("rsv111RsrDweek1", form.getRsv111RsrDweek1());
        cmn999Form.addHiddenParam("rsv111RsrDweek2", form.getRsv111RsrDweek2());
        cmn999Form.addHiddenParam("rsv111RsrDweek3", form.getRsv111RsrDweek3());
        cmn999Form.addHiddenParam("rsv111RsrDweek4", form.getRsv111RsrDweek4());
        cmn999Form.addHiddenParam("rsv111RsrDweek5", form.getRsv111RsrDweek5());
        cmn999Form.addHiddenParam("rsv111RsrDweek6", form.getRsv111RsrDweek6());
        cmn999Form.addHiddenParam("rsv111RsrDweek7", form.getRsv111RsrDweek7());
        cmn999Form.addHiddenParam("rsv111RsrWeek", form.getRsv111RsrWeek());
        cmn999Form.addHiddenParam("rsv111RsrDay", form.getRsv111RsrDay());
        cmn999Form.addHiddenParam("rsv111RsrTranKbn", form.getRsv111RsrTranKbn());
        cmn999Form.addHiddenParam("rsv111RsrDateYearFr", form.getRsv111RsrDateYearFr());
        cmn999Form.addHiddenParam("rsv111RsrDateMonthFr", form.getRsv111RsrDateMonthFr());
        cmn999Form.addHiddenParam("rsv111RsrDateDayFr", form.getRsv111RsrDateDayFr());
        cmn999Form.addHiddenParam("rsv111RsrDateYearTo", form.getRsv111RsrDateYearTo());
        cmn999Form.addHiddenParam("rsv111RsrDateMonthTo", form.getRsv111RsrDateMonthTo());
        cmn999Form.addHiddenParam("rsv111RsrDateDayTo", form.getRsv111RsrDateDayTo());
        cmn999Form.addHiddenParam("rsv111RsrTimeHourFr", form.getRsv111RsrTimeHourFr());
        cmn999Form.addHiddenParam("rsv111RsrTimeMinuteFr", form.getRsv111RsrTimeMinuteFr());
        cmn999Form.addHiddenParam("rsv111RsrTimeHourTo", form.getRsv111RsrTimeHourTo());
        cmn999Form.addHiddenParam("rsv111RsrTimeMinuteTo", form.getRsv111RsrTimeMinuteTo());
        cmn999Form.addHiddenParam("rsv111RsrMok", form.getRsv111RsrMok());
        cmn999Form.addHiddenParam("rsv111RsrBiko", form.getRsv111RsrBiko());
        cmn999Form.addHiddenParam("rsv111RsrEdit", form.getRsv111RsrEdit());
        cmn999Form.addHiddenParam("rsv111ScdReflection", form.getRsv111ScdReflection());

        cmn999Form.addHiddenParam("rsv010LearnMoreFlg", form.getRsv010LearnMoreFlg());
        cmn999Form.addHiddenParam("rsv010sisetuKeyword", form.getRsv010sisetuKeyword());
        cmn999Form.addHiddenParam("rsv010KeyWordkbn", form.getRsv010KeyWordkbn());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisan", form.getRsv010sisetuKeywordSisan());
        cmn999Form.addHiddenParam("rsv010sisetuKeywordSisetu", form.getRsv010sisetuKeywordSisetu());
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
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordBiko", form.getRsv010svSisetuKeywordBiko());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordNo", form.getRsv010svSisetuKeywordNo());
        cmn999Form.addHiddenParam("rsv010svSisetuKeywordIsbn", form.getRsv010svSisetuKeywordIsbn());
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
    private ActionForward __doCantUpdate(ActionMapping map,
                                          Rsv110Form form,
                                          HttpServletRequest req,
                                          HttpServletResponse res,
                                          Connection con) throws Exception {

        Cmn999Form cmn999Form = new Cmn999Form();
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        ActionForward forwardOk = __getBackPageForward(map, form);
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        String msgId = "";
        if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_SINKI)
                || form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_COPY_ADD)) {
            msgId = "error.myself.auth";
        } else if (form.getRsv110ProcMode().equals(GSConstReserve.PROC_MODE_EDIT)) {
            msgId = "error.myself.auth";
        }

        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(msgRes.getMessage(msgId, gsMsg.getMessage(req, "reserve.src.22")));

        //画面パラメータをセット
        __setMsgFormParam(cmn999Form, form);
        req.setAttribute("cmn999Form", cmn999Form);
        return map.findForward("gf_msg");
    }
    
    /**
     * <br>[機  能] 予約可能な施設グループか判定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param rsgSid 施設グループSID
     * @param sessionUsrSid セッションユーザSID
     * @param admFlg 管理者フラグ
     * @return ret 予約可能:true 予約不可:false
     * @throws SQLException SQL実行時例外
     */
    private boolean __isEditRsvGrp(Connection con,
            int rsgSid, int sessionUsrSid, boolean admFlg)
        throws SQLException {

        if (admFlg) {
            return true;
        }

        RsvSisGrpDao rsgDao = new RsvSisGrpDao(con);
        RsvSisGrpModel grpModel = rsgDao.select(rsgSid);

        //制限方法
        int limitKbn = GSConstReserve.RSV_ACCESS_MODE_FREE;
        if (grpModel != null) {
            limitKbn = grpModel.getRsgAcsLimitKbn();
        }
        if (limitKbn == GSConstReserve.RSV_ACCESS_MODE_FREE) {
            return true;
        }

        RsvSisGrpDao dao = new RsvSisGrpDao(con);
        ArrayList<RsvSisGrpModel> accessList = dao.getCanEditData(sessionUsrSid, rsgSid);
        if (accessList != null && accessList.size() > 0) {
            return true;
        }
        return false;
    }
}