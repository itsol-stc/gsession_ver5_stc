package jp.groupsession.v2.rsv.rsv150kn;

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
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 個人設定 表示設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv150knAction extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv150knAction.class);

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
    */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con) throws Exception {

        ActionForward forward = null;
        Rsv150knForm rsvform = (Rsv150knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();

        //確定ボタン押下
        if (cmd.equals("hyozi_settei_kakutei")) {
            log__.debug("確定ボタン押下");
            forward = __doKakutei(map, rsvform, req, res, con);
        //戻るボタン押下
        } else if (cmd.equals("back_to_hyozi_settei_input")) {
            log__.debug("戻るボタン押下");
            forward = map.findForward("back_to_hyozi_settei_input");
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
                                    Rsv150knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con) throws Exception {
        con.setAutoCommit(true);
        Rsv150knBiz biz = new Rsv150knBiz(con, getRequestModel(req));

        Rsv150knParamModel paramMdl = new Rsv150knParamModel();
        paramMdl.setParam(form);
        biz.initDsp(paramMdl);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] 確定ボタンクリック時
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form Rsv150knForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     */
    public ActionForward __doKakutei(ActionMapping map, Rsv150knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        //2重投稿
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors = new ActionErrors();
        errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        ActionForward forward = null;
        boolean commit = false;
        try {
            Rsv150knBiz biz = new Rsv150knBiz(con, getRequestModel(req));
            //個人設定の登録
            Rsv150knParamModel paramMdl = new Rsv150knParamModel();
            paramMdl.setParam(form);
            biz.registPrivate(paramMdl, getSessionUserModel(req).getUsrsid());
            paramMdl.setFormData(form);

            forward = __setCompPageParam(map, req, form);
            con.commit();
            commit = true;
        } catch (Exception e) {
            log__.error("個人設定の登録に失敗", e);
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        String msg = __getLogMessage(form, req, con);
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        rsvBiz.outPutLog(map, req, res,
                gsMsg.getMessage(req, "cmn.change"),
                GSConstLog.LEVEL_INFO, msg);

        return forward;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @return ActionForward
     */
    private ActionForward __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Rsv150knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("back_to_kojn_menu");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        GsMessage gsMsg = new GsMessage();
        cmn999Form.setMessage(msgRes.getMessage("touroku.kanryo.object",
                gsMsg.getMessage(req, "cmn.display.settings")));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
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
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @param con コネクション
     * @return ActionForward
     * @throws SQLException SQLエラー
     */
    private String __getLogMessage(
        Rsv150knForm form,
        HttpServletRequest req,
        Connection con) throws SQLException {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";
        //初期表示画面
        msg += "[" + gsMsg.getMessage("cmn.initial.display") + "]";
        if (form.getRsv150DefDsp() == GSConstReserve.INIT_DISP_WEEK) {
            msg += gsMsg.getMessage("cmn.weeks");
        } else if (form.getRsv150DefDsp() == GSConstReserve.INIT_DISP_DAY) {
            msg += gsMsg.getMessage("cmn.days2");
        }
        //初期表示グループ
        msg += "\r\n[" + gsMsg.getMessage("reserve.99") + "]";
        if (form.getRsv150SelectedGrpSid() == GSConstReserve.INIT_GROUP_ALL) {
            msg += gsMsg.getMessage("reserve.rsv100.appr.status1");
        } else if (form.getRsv150SelectedGrpSid() != GSConstReserve.INIT_GROUP_NONE) {
            Rsv150knBiz biz = new Rsv150knBiz(con, getRequestModel(req));
            String groupName = biz.getGroupName(form.getRsv150SelectedGrpSid(), con);
            msg += groupName;
        }
        //表示項目
        msg += "\r\n[" + gsMsg.getMessage("reserve.100") + "]";
        boolean spaceFlg = false;
        if (form.getRsv150DispItem1().equals(GSConstReserve.SHOW_TARGET)) {
            msg += gsMsg.getMessage("reserve.72");
            spaceFlg = true;
        }
        if (form.getRsv150DispItem2().equals(GSConstReserve.SHOW_ADDUSER)) {
            if (spaceFlg) {
                msg += " ";
            }
            msg += gsMsg.getMessage("reserve.137");
        }
        //自動リロード時間
        msg += "\r\n[" + gsMsg.getMessage("cmn.auto.reload.time") + "]";
        if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_1)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_1;
        } else if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_3)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_3;
        } else if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_5)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_5;
        } else if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_10)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_10;
        } else if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_20)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_20;
        } else if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_30)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_30;
        } else if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_40)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_40;
        } else if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_50)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_50;
        } else if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_60)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_60;
        } else if (form.getRsv150ReloadTime().equals(GSConstReserve.AUTORELOAD_0)) {
            msg += GSConstReserve.AUTORELOAD_TEXT_0;
        }
        //施設画像表示
        msg += "\r\n[" + gsMsg.getMessage("reserve.102") + "]";
        if (form.getRsv150ImgDspKbn() == GSConstReserve.SISETU_IMG_ON) {
            msg += gsMsg.getMessage("cmn.display.ok");
        } else if (form.getRsv150ImgDspKbn() == GSConstReserve.SISETU_IMG_OFF) {
            msg += gsMsg.getMessage("cmn.dont.show");
        }
        //日間表示時間帯設定
        if (form.getRsv150DateKbn() == GSConstReserve.AUTH_ALL_USER) {
            msg += "\r\n[" + gsMsg.getMessage("cmn.show.timezone.days.setting") + "]";
            msg += gsMsg.getMessage("cmn.starttime") + " : ";
            switch (form.getRsv150SelectedFromSid()) {
              case GSConstReserve.START_TIME_0 :
                  msg += GSConstReserve.START_TIME_0 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_1 :
                  msg += GSConstReserve.START_TIME_1 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_2 :
                  msg += GSConstReserve.START_TIME_2 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_3 :
                  msg += GSConstReserve.START_TIME_3 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_4 :
                  msg += GSConstReserve.START_TIME_4 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_5 :
                  msg += GSConstReserve.START_TIME_5 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_6 :
                  msg += GSConstReserve.START_TIME_6 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_7 :
                  msg += GSConstReserve.START_TIME_7 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_8 :
                  msg += GSConstReserve.START_TIME_8 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_9 :
                  msg += GSConstReserve.START_TIME_9 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_10 :
                  msg += GSConstReserve.START_TIME_10 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_11 :
                  msg += GSConstReserve.START_TIME_11 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_12 :
                  msg += GSConstReserve.START_TIME_12 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_13 :
                  msg += GSConstReserve.START_TIME_13 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_14 :
                  msg += GSConstReserve.START_TIME_14 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_15 :
                  msg += GSConstReserve.START_TIME_15 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_16 :
                  msg += GSConstReserve.START_TIME_16 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_17 :
                  msg += GSConstReserve.START_TIME_17 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_18 :
                  msg += GSConstReserve.START_TIME_18 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_19 :
                  msg += GSConstReserve.START_TIME_19 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_20 :
                  msg += GSConstReserve.START_TIME_20 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_21 :
                  msg += GSConstReserve.START_TIME_21 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_22 :
                  msg += GSConstReserve.START_TIME_22 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_23 :
                  msg += GSConstReserve.START_TIME_23 + gsMsg.getMessage("cmn.hour");
                  break;
              default :
                  break;
            }
            msg += "\r\n" + gsMsg.getMessage("cmn.endtime") + " : ";
            switch (form.getRsv150SelectedToSid()) {
              case GSConstReserve.START_TIME_0 :
                  msg += GSConstReserve.START_TIME_0 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_1 :
                  msg += GSConstReserve.START_TIME_1 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_2 :
                  msg += GSConstReserve.START_TIME_2 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_3 :
                  msg += GSConstReserve.START_TIME_3 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_4 :
                  msg += GSConstReserve.START_TIME_4 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_5 :
                  msg += GSConstReserve.START_TIME_5 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_6 :
                  msg += GSConstReserve.START_TIME_6 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_7 :
                  msg += GSConstReserve.START_TIME_7 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_8 :
                  msg += GSConstReserve.START_TIME_8 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_9 :
                  msg += GSConstReserve.START_TIME_9 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_10 :
                  msg += GSConstReserve.START_TIME_10 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_11 :
                  msg += GSConstReserve.START_TIME_11 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_12 :
                  msg += GSConstReserve.START_TIME_12 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_13 :
                  msg += GSConstReserve.START_TIME_13 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_14 :
                  msg += GSConstReserve.START_TIME_14 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_15 :
                  msg += GSConstReserve.START_TIME_15 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_16 :
                  msg += GSConstReserve.START_TIME_16 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_17 :
                  msg += GSConstReserve.START_TIME_17 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_18 :
                  msg += GSConstReserve.START_TIME_18 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_19 :
                  msg += GSConstReserve.START_TIME_19 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_20 :
                  msg += GSConstReserve.START_TIME_20 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_21 :
                  msg += GSConstReserve.START_TIME_21 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_22 :
                  msg += GSConstReserve.START_TIME_22 + gsMsg.getMessage("cmn.hour");
                  break;
              case GSConstReserve.START_TIME_23 :
                  msg += GSConstReserve.START_TIME_23 + gsMsg.getMessage("cmn.hour");
                  break;
              default :
                  break;
            }
        }
        //表示件数
        msg += "\r\n[" + gsMsg.getMessage("cmn.number.display") + "]";
        if (form.getRsv150ViewCnt() == GSConstReserve.SHOW_COUNT_10) {
            msg += String.valueOf(GSConstReserve.SHOW_COUNT_10) + gsMsg.getMessage("cmn.number");
        } else if (form.getRsv150ViewCnt() == GSConstReserve.SHOW_COUNT_20) {
            msg += String.valueOf(GSConstReserve.SHOW_COUNT_20) + gsMsg.getMessage("cmn.number");
        } else if (form.getRsv150ViewCnt() == GSConstReserve.SHOW_COUNT_30) {
            msg += String.valueOf(GSConstReserve.SHOW_COUNT_30) + gsMsg.getMessage("cmn.number");
        } else if (form.getRsv150ViewCnt() == GSConstReserve.SHOW_COUNT_40) {
            msg += String.valueOf(GSConstReserve.SHOW_COUNT_40) + gsMsg.getMessage("cmn.number");
        } else if (form.getRsv150ViewCnt() == GSConstReserve.SHOW_COUNT_50) {
            msg += String.valueOf(GSConstReserve.SHOW_COUNT_50) + gsMsg.getMessage("cmn.number");
        }
        return msg;
    }
}