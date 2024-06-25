package jp.groupsession.v2.rsv.rsv230kn;

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
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.rsv.AbstractReserveAction;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 初期値設定確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv230knAction extends AbstractReserveAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv230knAction.class);

    /**
     * <br>アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        Rsv230knForm rsvForm = (Rsv230knForm) form;
        // アクセスチェック
        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        RsvAdmConfModel adminMdl = rsvBiz.getRsvAdminData(con, getSessionUserSid(req));
        if (!rsvBiz.isAccessUnconfInitSetting(adminMdl)) {
            return getSubmitErrorPage(map, req);
        }

        // 前画面から設定項目が変更されていないか確認
        if ((rsvForm.isRsv230SvEditFlg() == false
                && adminMdl.getRacIniEditKbn() == GSConstReserve.RAC_INIEDITKBN_USER)
            || (rsvForm.isRsv230SvEditFlg() == true
                && adminMdl.getRacIniEditKbn() != GSConstReserve.RAC_INIEDITKBN_USER)
            || (rsvForm.isRsv230SvPeriodFlg() == false
                && adminMdl.getRacIniPeriodKbn() == GSConstReserve.RAC_INIPERIODKBN_USER)
            || (rsvForm.isRsv230SvPeriodFlg() == true
                && adminMdl.getRacIniPeriodKbn() != GSConstReserve.RAC_INIPERIODKBN_USER)
            || (rsvForm.isRsv230SvPublicFlg() == false
                && adminMdl.getRacIniPublicKbn() == GSConstReserve.RAC_INIPUBLICKBN_USER)
            || (rsvForm.isRsv230SvPublicFlg() == true
                && adminMdl.getRacIniPublicKbn() != GSConstReserve.RAC_INIPUBLICKBN_USER)) {
            return getSubmitErrorPage(map, req);
        }

        // コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("rsv230knkakutei")) {
            //登録 戻る
            forward = __doCommit(map, rsvForm, req, res, con);
        } else if (cmd.equals("rsv230knback")) {
            //戻る
            forward = __doBack(map, rsvForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, rsvForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Rsv230knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("登録");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //入力チェック
        ActionErrors errors
            = form.validateRsv230(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        //DB更新
        Rsv230knBiz biz = new Rsv230knBiz();
        BaseUserModel umodel = getSessionUserModel(req);
        biz.setPconfSetting(form, umodel, con);

        //ログ出力処理
        GsMessage gsMsg = new GsMessage();
        String msg = __getLogMessage(form, req);
        AbstractReserveBiz rsvBiz = new AbstractReserveBiz(con);
        rsvBiz.outPutLog(map, req, res,
                gsMsg.getMessage(req, "cmn.change"), GSConstLog.LEVEL_INFO, msg);

        //完了画面のパラメータを設定。
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     * @throws Exception 実行例外
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Rsv230knForm form) throws Exception {

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
        String msgState = "touroku.kanryo.object";
        //施設予約初期値設定
        String key1 = gsMsg.getMessage(req, "reserve.src.39");
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("Rsv230DefFrH", form.getRsv230DefFrH());
        cmn999Form.addHiddenParam("Rsv230DefFrM", form.getRsv230DefFrM());
        cmn999Form.addHiddenParam("Rsv230DefToH", form.getRsv230DefToH());
        cmn999Form.addHiddenParam("Rsv230DefToM", form.getRsv230DefToM());
        cmn999Form.addHiddenParam("Rsv230Edit", form.getRsv230Edit());
        cmn999Form.addHiddenParam("Rsv230Edit", form.getRsv230Public());

        cmn999Form.addHiddenParam("rsvBackPgId", form.getRsvBackPgId());
        cmn999Form.addHiddenParam("rsvDspFrom", form.getRsvDspFrom());
        cmn999Form.addHiddenParam("rsvSelectedGrpSid", form.getRsvSelectedGrpSid());
        cmn999Form.addHiddenParam("rsvSelectedSisetuSid", form.getRsvSelectedSisetuSid());
        cmn999Form.addHiddenParam("rsv100InitFlg", String.valueOf(form.isRsv100InitFlg()));
        cmn999Form.addHiddenParam("rsv100SearchFlg", String.valueOf(form.isRsv100SearchFlg()));
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

    }

    /**
     * <br>戻る処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doBack(ActionMapping map, Rsv230knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");

        return map.findForward("back_to_syokiti_settei");
    }

    /**
     * <br>初期表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Rsv230knForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");
        con.setAutoCommit(true);
        Rsv230knBiz biz = new Rsv230knBiz();
        biz.setInitData(form, con);
        con.setAutoCommit(false);
        return map.getInputForward();
    }

    /**
     * <br>[機  能] ログ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param form アクションフォーム
     * @param req リクエスト
     * @return ActionForward
     * @throws SQLException SQLエラー
     */
    private String __getLogMessage(
        Rsv230knForm form,
        HttpServletRequest req) throws SQLException {

        GsMessage gsMsg = new GsMessage(req);
        String msg = "";

        //期間
        msg += "[" + gsMsg.getMessage("cmn.period") + "]";
        msg += gsMsg.getMessage("cmn.am") + " : ";
        msg += form.getRsv230DefFrH() + gsMsg.getMessage("cmn.hour");
        String minute = String.valueOf(form.getRsv230DefFrM());
        if (minute.equals("0")) {
            minute = "00";
        }
        msg += minute + gsMsg.getMessage("cmn.minute");

        msg += "\r\n" + gsMsg.getMessage("cmn.pm") + " : ";
        msg += form.getRsv230DefToH() + gsMsg.getMessage("cmn.hour");
        minute = String.valueOf(form.getRsv230DefToM());
        if (minute.equals("0")) {
            minute = "00";
        }
        msg += minute + gsMsg.getMessage("cmn.minute");
        //編集権限
        msg += "\r\n[" + gsMsg.getMessage("cmn.edit.permissions") + "]";
        if (form.getRsv230Edit() == GSConstReserve.EDIT_AUTH_NONE) {
            msg += gsMsg.getMessage("cmn.nolimit");
        } else if (form.getRsv230Edit() == GSConstReserve.EDIT_AUTH_PER_AND_ADU) {
            msg += gsMsg.getMessage("cmn.only.principal.or.registant");
        } else if (form.getRsv230Edit() == GSConstReserve.EDIT_AUTH_GRP_AND_ADU) {
            msg += gsMsg.getMessage("cmn.only.affiliation.group.membership");
        }
        //公開区分
        msg += "\r\n[" + gsMsg.getMessage("cmn.public.kbn") + "]";
        if (form.getRsv230Public() == GSConstReserve.PUBLIC_KBN_ALL) {
            msg += gsMsg.getMessage("cmn.public");
        } else if (form.getRsv230Public() == GSConstReserve.PUBLIC_KBN_PLANS) {
            msg += gsMsg.getMessage("reserve.175");
        } else if (form.getRsv230Public() == GSConstReserve.PUBLIC_KBN_GROUP) {
            msg += gsMsg.getMessage("reserve.176");
        }

        return msg;
    }
}
