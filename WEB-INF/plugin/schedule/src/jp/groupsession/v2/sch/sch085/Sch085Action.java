package jp.groupsession.v2.sch.sch085;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 管理者設定 メンバー表示順設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch085Action extends AbstractScheduleAdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch085Action.class);

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
        log__.debug("AA form = " + form);
        Sch085Form schForm = (Sch085Form) form;
        if (form == null) {
            log__.debug(" form is null ");
        }

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        //ソートキーラベル
        GsMessage gsMsg = new GsMessage();
        //氏名
        String textName = gsMsg.getMessage(req, "cmn.name");
        //社員/職員番号
        String textStaffNumber = gsMsg.getMessage(req, "cmn.employee.staff.number");
        //役職
        String textPost = gsMsg.getMessage(req, "cmn.post");
        //生年月日
        String textBirthDay = gsMsg.getMessage(req, "cmn.birthday");
        //ソートキー1
        String textSortkey1 = gsMsg.getMessage(req, "cmn.sortkey") + 1;
        //ソートキー2
        String textSortkey2 = gsMsg.getMessage(req, "cmn.sortkey") + 2;

        String[] sortKeyAllText = new String[] { textName,
                textStaffNumber, textPost, textBirthDay,  textSortkey1, textSortkey2};
        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();
        for (int i = 0; i < GSConstSchedule.SORT_KEY_ALL.length; i++) {
            String label = sortKeyAllText[i];
            String value = Integer.toString(GSConstSchedule.SORT_KEY_ALL[i]);
            sortLabel.add(new LabelValueBean(label, value));
        }
        schForm.setSch085SortKeyLabel(sortLabel);

        if (cmd.equals("sch085kakunin")) {
            //確認
            forward = __doKakunin(map, schForm, req, res, con);
        } else if (cmd.equals("sch085commit")) {
            //登録 実行
            forward = __doCommit(map, schForm, req, res, con);
        } else if (cmd.equals("sch085back")) {
            //戻る
            forward = __doBack(map, schForm, req, res, con);
        } else if (cmd.equals("sch085reload")) {
            forward = __doReload(map, schForm, req, res, con);
        } else {
            //デフォルト
            forward = __doInit(map, schForm, req, res, con);
        }
        return forward;
    }

    /**
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(
        ActionMapping map,
        Sch085Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        log__.debug("確認");

        ActionErrors errors = form.validateCheck(getRequestModel(req));

        if (!errors.isEmpty() && form.getSch085MemDspKbn() == 0) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面を表示
        __setKakuninPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] 確認メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setKakuninPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Sch085Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
//        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //メッセージセット
        String msgState = "edit.kakunin.once";
        GsMessage gsMsg = new GsMessage();
        //グループメンバー表示設定
        String mkey1 = gsMsg.getMessage(req, "cmn.display.settings");
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());

        cmn999Form.addHiddenParam("sch085AdSortKey1", form.getSch085AdSortKey1());
        cmn999Form.addHiddenParam("sch085AdSortOrder1", form.getSch085AdSortOrder1());
        cmn999Form.addHiddenParam("sch085AdSortKey2", form.getSch085AdSortKey2());
        cmn999Form.addHiddenParam("sch085AdSortOrder2", form.getSch085AdSortOrder2());

        cmn999Form.addHiddenParam("sch085MemDspKbn", form.getSch085MemDspKbn());
        cmn999Form.addHiddenParam("sch085DefGroup", form.getSch085DefGroup());

        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
        cmn999Form.addHiddenParam("sch020SelectUsrSid", form.getSch020SelectUsrSid());
        cmn999Form.addHiddenParam("sch030FromHour", form.getSch030FromHour());
        //一覧画面用
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());

        cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());
        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());

        cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
        cmn999Form.addHiddenParam("sch100SvSearchTarget", form.getSch100SvSearchTarget());
        cmn999Form.addHiddenParam("sch100SearchTarget", form.getSch100SearchTarget());
        cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());
        cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());

        req.setAttribute("cmn999Form", cmn999Form);
    }

    /**
     * <br>登録処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(
        ActionMapping map,
        Sch085Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws SQLException {

        log__.debug("登録");
        ActionForward forward = null;

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        RequestModel reqMdl = getRequestModel(req);
        Sch085Biz biz = new Sch085Biz(reqMdl);

        ActionErrors errors = form.validateCheck(reqMdl);
        if (!errors.isEmpty() && form.getSch085MemDspKbn() == 0) {
            addErrors(req, errors);
            return map.getInputForward();
        }

        //DB更新
        Sch085ParamModel paramMdl = new Sch085ParamModel();
        paramMdl.setParam(form);
        biz.setAdmconfSetting(paramMdl, getSessionUserSid(req), con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 変更 **/
        String change = gsMsg.getMessage(req, "cmn.change");

        //ログ出力処理
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        String value = "";
        // メンバー表示順
        value += "[" + gsMsg.getMessage("schedule.sch085.1") + "] ";
        String[] orderSetting = {
                gsMsg.getMessage("cmn.set.the.admin"),
                gsMsg.getMessage("cmn.set.eachuser")
        };
        value += orderSetting[paramMdl.getSch085MemDspKbn()] + "\r\n";
        // メンバー表示順 ソートキー
        Map<Integer, String> sortKey = new HashMap<Integer, String>();
        sortKey.put(GSConstSchedule.SCH_MEM_ORDER_SORT_NAME,
                gsMsg.getMessage("cmn.name"));
        sortKey.put(GSConstSchedule.SCH_MEM_ORDER_SORT_SYAIN,
                gsMsg.getMessage("cmn.employee.staff.number"));
        sortKey.put(GSConstSchedule.SCH_MEM_ORDER_SORT_POST,
                gsMsg.getMessage("cmn.post"));
        sortKey.put(GSConstSchedule.SCH_MEM_ORDER_SORT_BIRTHDAY,
                gsMsg.getMessage("cmn.birthday"));
        sortKey.put(GSConstSchedule.SCH_MEM_ORDER_SORT_SORT1,
                gsMsg.getMessage("cmn.sortkey") + "1");
        sortKey.put(GSConstSchedule.SCH_MEM_ORDER_SORT_SORT2,
                gsMsg.getMessage("cmn.sortkey") + "2");
        // メンバー表示順 ソート順
        String[] order = {
                gsMsg.getMessage("cmn.order.asc"),
                gsMsg.getMessage("cmn.order.desc")
        };
        value += gsMsg.getMessage("cmn.first.key") + ":"
                 + sortKey.get(paramMdl.getSch085AdSortKey1()) + " "
                 + order[paramMdl.getSch085AdSortOrder1()];
        value += "\r\n";
        value += gsMsg.getMessage("cmn.second.key") + ":"
                + sortKey.get(paramMdl.getSch085AdSortKey2()) + " "
                + order[paramMdl.getSch085AdSortOrder2()];
        // デフォルト表示グループ
        value += "\r\n" + "[" + gsMsg.getMessage("schedule.sch085.3") + "] ";
        String[] defDspGrp = {
                gsMsg.getMessage("cmn.set.eachuser"),
                gsMsg.getMessage("schedule.sch085.2")
        };
        value += defDspGrp[paramMdl.getSch085DefGroup()];
        schBiz.outPutLog(
                map, req, res,
                change, GSConstLog.LEVEL_INFO, value);

        //共通メッセージ画面(OK)を表示
        __setCompPageParam(map, req, form);
        forward = map.findForward("gf_msg");
        return forward;
    }

    /**
     * <br>[機  能] 完了メッセージ画面遷移時のパラメータを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param req リクエスト
     * @param form アクションフォーム
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Sch085Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("sch085back");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        GsMessage gsMsg = new GsMessage();
        //グループメンバー表示設定
        String key1 = gsMsg.getMessage(req, "cmn.display.settings");
        cmn999Form.setMessage(msgRes.getMessage(msgState, key1));

        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("dspMod", form.getDspMod());
        cmn999Form.addHiddenParam("listMod", form.getListMod());
        cmn999Form.addHiddenParam("sch010DspDate", form.getSch010DspDate());
        cmn999Form.addHiddenParam("changeDateFlg", form.getChangeDateFlg());
        cmn999Form.addHiddenParam("sch010DspGpSid", form.getSch010DspGpSid());
        cmn999Form.addHiddenParam("sch010SelectUsrSid", form.getSch010SelectUsrSid());
        cmn999Form.addHiddenParam("sch010SelectUsrKbn", form.getSch010SelectUsrKbn());
        cmn999Form.addHiddenParam("sch010SelectDate", form.getSch010SelectDate());
        cmn999Form.addHiddenParam("sch020SelectUsrSid", form.getSch020SelectUsrSid());
        cmn999Form.addHiddenParam("sch030FromHour", form.getSch030FromHour());
        //一覧画面用
        cmn999Form.addHiddenParam("sch100PageNum", form.getSch100PageNum());
        cmn999Form.addHiddenParam("sch100Slt_page1", form.getSch100Slt_page1());
        cmn999Form.addHiddenParam("sch100Slt_page2", form.getSch100Slt_page2());
        cmn999Form.addHiddenParam("sch100OrderKey1", form.getSch100OrderKey1());
        cmn999Form.addHiddenParam("sch100SortKey1", form.getSch100SortKey1());
        cmn999Form.addHiddenParam("sch100OrderKey2", form.getSch100OrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SortKey2());

        cmn999Form.addHiddenParam("sch100SvSltGroup", form.getSch100SvSltGroup());
        cmn999Form.addHiddenParam("sch100SvSltUser", form.getSch100SvSltUser());
        cmn999Form.addHiddenParam("sch100SvSltStartYearFr", form.getSch100SvSltStartYearFr());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthFr", form.getSch100SvSltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltStartDayFr", form.getSch100SvSltStartDayFr());
        cmn999Form.addHiddenParam("sch100SvSltStartYearTo", form.getSch100SvSltStartYearTo());
        cmn999Form.addHiddenParam("sch100SvSltStartMonthTo", form.getSch100SvSltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltStartDayTo", form.getSch100SvSltStartDayTo());
        cmn999Form.addHiddenParam("sch100SvSltEndYearFr", form.getSch100SvSltEndYearFr());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthFr", form.getSch100SvSltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SvSltEndDayFr", form.getSch100SvSltEndDayFr());
        cmn999Form.addHiddenParam("sch100SvSltEndYearTo", form.getSch100SvSltEndYearTo());
        cmn999Form.addHiddenParam("sch100SvSltEndMonthTo", form.getSch100SvSltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SvSltEndDayTo", form.getSch100SvSltEndDayTo());
        cmn999Form.addHiddenParam("sch100SvKeyWordkbn", form.getSch100SvKeyWordkbn());
        cmn999Form.addHiddenParam("sch100SvKeyValue", form.getSch100SvKeyValue());
        cmn999Form.addHiddenParam("sch100SvOrderKey1", form.getSch100SvOrderKey1());
        cmn999Form.addHiddenParam("sch100SvSortKey1", form.getSch100SvSortKey1());
        cmn999Form.addHiddenParam("sch100SvOrderKey2", form.getSch100SvOrderKey2());
        cmn999Form.addHiddenParam("sch100SortKey2", form.getSch100SvSortKey2());

        cmn999Form.addHiddenParam("sch100SltGroup", form.getSch100SltGroup());
        cmn999Form.addHiddenParam("sch100SltUser", form.getSch100SltUser());
        cmn999Form.addHiddenParam("sch100SltStartYearFr", form.getSch100SltStartYearFr());
        cmn999Form.addHiddenParam("sch100SltStartMonthFr", form.getSch100SltStartMonthFr());
        cmn999Form.addHiddenParam("sch100SltStartDayFr", form.getSch100SltStartDayFr());
        cmn999Form.addHiddenParam("sch100SltStartYearTo", form.getSch100SltStartYearTo());
        cmn999Form.addHiddenParam("sch100SltStartMonthTo", form.getSch100SltStartMonthTo());
        cmn999Form.addHiddenParam("sch100SltStartDayTo", form.getSch100SltStartDayTo());
        cmn999Form.addHiddenParam("sch100SltEndYearFr", form.getSch100SltEndYearFr());
        cmn999Form.addHiddenParam("sch100SltEndMonthFr", form.getSch100SltEndMonthFr());
        cmn999Form.addHiddenParam("sch100SltEndDayFr", form.getSch100SltEndDayFr());
        cmn999Form.addHiddenParam("sch100SltEndYearTo", form.getSch100SltEndYearTo());
        cmn999Form.addHiddenParam("sch100SltEndMonthTo", form.getSch100SltEndMonthTo());
        cmn999Form.addHiddenParam("sch100SltEndDayTo", form.getSch100SltEndDayTo());
        cmn999Form.addHiddenParam("sch100KeyWordkbn", form.getSch100KeyWordkbn());
        cmn999Form.addHiddenParam("sch010searchWord", form.getSch010searchWord());
        cmn999Form.addHiddenParam("sch100SvSearchTarget", form.getSch100SvSearchTarget());
        cmn999Form.addHiddenParam("sch100SearchTarget", form.getSch100SearchTarget());
        cmn999Form.addHiddenParam("sch100SvBgcolor", form.getSch100SvBgcolor());
        cmn999Form.addHiddenParam("sch100Bgcolor", form.getSch100Bgcolor());
        cmn999Form.addHiddenParam("sch100CsvOutField", form.getSch100CsvOutField());

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
    private ActionForward __doBack(ActionMapping map, Sch085Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("戻る");
        ActionForward forward = null;
        forward = map.findForward("sch085back");
        return forward;
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
    private ActionForward __doInit(ActionMapping map, Sch085Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");
        log__.debug("form = " + form);

        ActionForward forward = null;

        con.setAutoCommit(true);
        Sch085Biz biz = new Sch085Biz(getRequestModel(req));
        Sch085ParamModel paramMdl = new Sch085ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        forward = map.getInputForward();
        con.setAutoCommit(false);
        return forward;
    }
    /**
     * <br>再表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doReload(ActionMapping map, Sch085Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("再表示");
        ActionForward forward = null;

        forward = map.getInputForward();
        return forward;
    }
}
