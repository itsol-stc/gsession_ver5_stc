package jp.groupsession.v2.rsv.rsv210;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
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
import jp.groupsession.v2.rsv.model.RsvHidDayModel;
import jp.groupsession.v2.rsv.model.RsvHidGroupModel;
import jp.groupsession.v2.rsv.model.RsvHidSisetuModel;
import jp.groupsession.v2.rsv.model.RsvRegSmailModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 施設予約一括登録画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv210Action extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv210Action.class);
    /** テンポラリディレクトリID*/
    private static final String TEMP_DIRECTORY_ID = "rsv210";

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
        Rsv210Form rsvform = (Rsv210Form) form;

        //登録対象となる施設のチェックを行う
        String sisetsuErrMessage = __checkTargetFactory(con, req, rsvform);
        if (sisetsuErrMessage != null) {
            return __doNotSelectSisetsu(map, rsvform, req, sisetsuErrMessage);
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //OKボタン押下
        if (cmd.equals("ikkatu_toroku")) {
            log__.debug("OKボタン押下");
            forward = __doConfirmation(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_menu")) {
            log__.debug("戻るボタン押下");
            forward = __doBack(map, rsvform, req, res, con);
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
                                    Rsv210Form form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {

        try {
            con.setAutoCommit(true);
            //管理者設定を反映したプラグイン設定情報を取得
            PluginConfig pconfig
                = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));

            Rsv210Biz biz = new Rsv210Biz(getRequestModel(req), con);

            Rsv210ParamModel paramMdl = new Rsv210ParamModel();
            paramMdl.setParam(form);
            biz.setInitData(paramMdl, pconfig);
            paramMdl.setFormData(form);

            con.setAutoCommit(false);
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        }
        saveToken(req);

        return map.getInputForward();
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
                                            Rsv210Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {

        boolean commitFlg = false;

        BaseUserModel usModel = getSessionUserModel(req);
        if (usModel == null) {
            throw new GSAuthenticateException("ユーザ情報の取得に失敗");
        }
        int userSid = usModel.getUsrsid();
        GSTemporaryPathUtil temp = GSTemporaryPathUtil.getInstance();
        String tempDir = temp.getTempPath(getRequestModel(req),
                GSConstReserve.PLUGIN_ID_RESERVE, TEMP_DIRECTORY_ID);
        ArrayList<RsvHidDayModel> rsvHidDayList = new ArrayList<RsvHidDayModel>();

        try {

            //2重投稿
            if (!isTokenValid(req, true)) {
                log__.info("２重投稿");
                return getSubmitErrorPage(map, req);
            }

            //入力チェック
            ActionErrors errors = form.validateCheck(con, getRequestModel(req),
                                                    getSessionUserSid(req));
            if (!errors.isEmpty()) {
                addErrors(req, errors);
                return __doInit(map, form, req, res, con);
            }

            RequestModel reqMdl = getRequestModel(req);

            //DB更新
            MlCountMtController cntCon = getCountMtController(req);
            Rsv210Biz biz = new Rsv210Biz(reqMdl, con);

            Rsv210ParamModel paramMdl = new Rsv210ParamModel();
            paramMdl.setParam(form);
            List<int[]> sidDataList =
                    biz.updateYoyakuData(paramMdl, cntCon, userSid, getAppRootPath());
            rsvHidDayList = biz.getTargetSisetuList(paramMdl);
            paramMdl.setFormData(form);

            CommonBiz cmnBiz = new CommonBiz();

            //申請通知メール
            for (int[] sidData : sidDataList) {
                int yoyakuSid = sidData[0];
                int sisetsuSid = sidData[1];
                RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();
                //選択した施設に承認設定がされている場合
                if (rsvCmnBiz.isApprSis(con, sisetsuSid, userSid)) {
                    //ショートメールで通知
                    PluginConfig pconfig = getPluginConfigForMain(getPluginConfig(req), con);
                    if (cmnBiz.isCanUsePlugin(GSConstMain.PLUGIN_ID_SMAIL, pconfig)) {
                        RsvRegSmailModel regMdl = new RsvRegSmailModel();
                        regMdl.setCon(con);
                        regMdl.setReqMdl(reqMdl);
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
        ArrayList<RsvHidGroupModel> rsvGrpList = new ArrayList<RsvHidGroupModel>();
        ArrayList<RsvHidSisetuModel> rsvSisList = new ArrayList<RsvHidSisetuModel>();
        String sisName = null;
        String date = null;
        for (RsvHidDayModel rsvdayMdl : rsvHidDayList) {
            date = rsvdayMdl.getHidDayStr();
            rsvGrpList = rsvdayMdl.getGrpList();

            for (RsvHidGroupModel rsvGrpMdl : rsvGrpList) {
                rsvSisList = rsvGrpMdl.getSisetuList();

                for (RsvHidSisetuModel rsvSisMdl : rsvSisList) {
                    sisName = rsvSisMdl.getRsdName();
                    //ログ出力処理
                    GsMessage gsMsg = new GsMessage();
                    AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
                    String timeFr =
                            StringUtil.toDecFormat(form.getRsv210SelectedHourFr(), "00")
                            + ":"
                            + StringUtil.toDecFormat(form.getRsv210SelectedMinuteFr(), "00");
                    StringBuilder opLog = new StringBuilder();
                    opLog.append("[");
                    opLog.append(gsMsg.getMessage("reserve.122"));
                    opLog.append("]");
                    opLog.append(sisName);
                    opLog.append("\n");
                    opLog.append("[");
                    opLog.append(gsMsg.getMessage("schedule.sch100.11"));
                    opLog.append("]");
                    opLog.append(date);
                    opLog.append(timeFr);
                    opLog.append("\n");
                    opLog.append("[");
                    opLog.append(gsMsg.getMessage("reserve.72"));
                    opLog.append("]");
                    opLog.append(form.getRsv210Mokuteki());
                    opLog.append("\n");
                    opLog.append("[");
                    opLog.append(gsMsg.getMessage("cmn.content"));
                    opLog.append("]");
                    opLog.append(form.getRsv210Naiyo());
                    opLog.append("\n");

                    rsvBiz.outPutLog(map, req, res,
                            gsMsg.getMessage(req, "cmn.entry"), GSConstLog.LEVEL_TRACE,
                            opLog.toString());
                }
            }
        }
        return __doUpdateComp(map, form, req, res, con);
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
                                    Rsv210Form form,
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
        //施設利用状況照会画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV100)) {
            forward = map.findForward("back_to_itiran");
        }

        return forward;
    }

    /**
     * <br>[機  能] 施設予約の対象となる施設のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @param form フォーム
     * @return エラーメッセージ | 指定された施設が正常の場合、nullを返す
     * @throws SQLException SQL実行時例外
     */
    private String __checkTargetFactory(Connection con, HttpServletRequest req, Rsv210Form form)
    throws SQLException {
        int[] rsdSidList = null;
        String[] torokuKeyList = form.getRsvIkkatuTorokuKey();

        if (torokuKeyList != null && torokuKeyList.length > 0) {
            List<Integer> intTorokuKeyList = new ArrayList<Integer>();
            for (String torokuKey : torokuKeyList) {
                String rsdSid = null;
                if (torokuKey.indexOf("-") > 0) {
                    rsdSid = torokuKey.substring(torokuKey.indexOf("-") + 1);
                }

                //指定された施設が不正な場合、エラーメッセージを返す
                if (rsdSid == null
                || !ValidateUtil.isNumber(rsdSid)) {
                    return (new GsMessage(getRequestModel(req))).getMessage("reserve.174");
                }

                if (!intTorokuKeyList.contains(Integer.parseInt(rsdSid))) {
                    intTorokuKeyList.add(Integer.parseInt(rsdSid));
                }
            }

            if (!intTorokuKeyList.isEmpty()) {
                rsdSidList = new int[intTorokuKeyList.size()];
                for (int idx = 0; idx < intTorokuKeyList.size(); idx++) {
                    rsdSidList[idx] = intTorokuKeyList.get(idx);
                }
            }
        }

        return _checkTargetFactory(con, rsdSidList, req);
    }

    /**
     * <br>予約対象の施設が選択されていない場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param errMessage エラーメッセージ
     * @return ActionForward
     */
    private ActionForward __doNotSelectSisetsu(ActionMapping map, Rsv210Form form,
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
    private ActionForward __getBackPageForward(ActionMapping map, Rsv210Form form) {
        ActionForward urlForward = null;
        String backPgId =
                NullDefault.getStringZeroLength(
                        form.getRsvBackPgId(), GSConstReserve.DSP_ID_RSV010);

        //週間画面へ戻る
        if (backPgId.equals(GSConstReserve.DSP_ID_RSV010)) {
            urlForward = map.findForward("back_to_syukan");
        //日間画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV020)) {
            urlForward = map.findForward("back_to_nikkan");
        //月間画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV030)) {
            urlForward = map.findForward("back_to_gekkan");
        //一覧画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV100)) {
            urlForward = map.findForward("back_to_itiran");
        }

        return urlForward;
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
                                          Rsv210Form form,
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

        //週間画面へ戻る
        if (backPgId.equals(GSConstReserve.DSP_ID_RSV010)) {
            forwardOk = map.findForward("back_to_syukan_finish");
        //日間画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV020)) {
            forwardOk = map.findForward("back_to_nikkan_finish");
        //月間画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV030)) {
            forwardOk = map.findForward("back_to_gekkan_finish");
        //一覧画面へ戻る
        } else if (backPgId.equals(GSConstReserve.DSP_ID_RSV100)) {
            forwardOk = map.findForward("back_to_itiran");
        }
        cmn999Form.setUrlOK(forwardOk.getPath());

        //メッセージ
        MessageResources msgRes = getResources(req);
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                gsMsg.getMessage(req, "reserve.src.22")));

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
        //予約完了後はクリアする
        //cmn999Form.addHiddenParam("rsvIkkatuTorokuKey", form.getRsvIkkatuTorokuKey());

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
     * <br>[機  能] 共通メッセージ画面Formへパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージ画面Form
     * @param form フォーム
     */
    private void __setMsgFormParam(Cmn999Form cmn999Form, Rsv210Form form) {
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
        //予約完了後はクリアする
        //cmn999Form.addHiddenParam("rsvIkkatuTorokuKey", form.getRsvIkkatuTorokuKey());

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