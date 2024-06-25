package jp.groupsession.v2.sch.sch082;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.AbstractScheduleAdminAction;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 自動データ削除設定画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch082Action extends AbstractScheduleAdminAction {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch082Action.class);

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
        con.setAutoCommit(true);
        Sch082Form schForm = (Sch082Form) form;

        GsMessage gsMsg = new GsMessage(getRequestModel(req));
        //年ラベル作成
        ArrayList<LabelValueBean> sch082AtdelYearLabel__ = new ArrayList<LabelValueBean>();
        for (String label : Sch082Form.YEAR_VALUE) {
            sch082AtdelYearLabel__.add(
                    new LabelValueBean(gsMsg.getMessage(req, "cmn.year", label), label));
        }
        schForm.setSch082AtdelYearLabel(sch082AtdelYearLabel__);
        //月ラベル作成
        ArrayList<LabelValueBean> sch082AtdelMonthLabel__ = new ArrayList<LabelValueBean>();
        for (String label : Sch082Form.MONTH_VALUE) {
            sch082AtdelMonthLabel__.add(
                    new LabelValueBean(gsMsg.getMessage(req, "cmn.months", label), label));
        }
        schForm.setSch082AtdelMonthLabel(sch082AtdelMonthLabel__);

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        if (cmd.equals("sch082kakunin")) {
            //確認
            forward = __doKakunin(map, schForm, req, res, con);
        } else if (cmd.equals("sch082commit")) {
            //登録
            con.setAutoCommit(false);
            forward = __doCommit(map, schForm, req, res, con);
        } else if (cmd.equals("sch082cancel")) {
            //キャンセル
            forward = __doRedraw(map, schForm, req, res, con);
        } else if (cmd.equals("sch082back")) {
            //戻るボタンクリック
            forward = __doBack(map, schForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, schForm, req, res, con);
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
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doCommit(ActionMapping map, Sch082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("登録処理");

        //不正な画面遷移
        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //DB更新
        RequestModel reqMdl = getRequestModel(req);
        BaseUserModel umodel = getSessionUserModel(req);
        Sch082Biz biz = new Sch082Biz(getRequestModel(req));
        Sch082ParamModel paramMdl = new Sch082ParamModel();
        paramMdl.setParam(form);
        SchAdmConfModel conf = biz.setAutoDeleteSetting(paramMdl, umodel, con);
        paramMdl.setFormData(form);

        GsMessage gsMsg = new GsMessage();
        /** メッセージ 変更 **/
        String change = gsMsg.getMessage(req, "cmn.change");

        //ログ出力処理
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        StringBuilder values = new StringBuilder();
        values.append("[auto]" + conf.getSadAtdelFlg());
        values.append(" [year]" + conf.getSadAtdelY());
        values.append(" [month]" + conf.getSadAtdelM());
        schBiz.outPutLog(
                map, req, res,
                change, GSConstLog.LEVEL_INFO, values.toString());

        //共通メッセージ画面(OK)を表示
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
     */
    private void __setCompPageParam(
        ActionMapping map,
        HttpServletRequest req,
        Sch082Form form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OK);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("ktool");
        cmn999Form.setUrlOK(urlForward.getPath());

        //メッセージセット
        String msgState = "touroku.kanryo.object";
        GsMessage gsMsg = new GsMessage();
        //自動データ削除設定
        String textAutoDelete = gsMsg.getMessage(req, "cmn.automatic.delete.setting");
        String key1 = textAutoDelete;
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
     * <br>確認処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doKakunin(ActionMapping map, Sch082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("確認処理");

        ActionErrors errors = form.validateCheck(req);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doSetShowData(map, form, req, res, con);
        }

        //トランザクショントークン設定
        saveToken(req);

        //共通メッセージ画面(OK キャンセル)を表示
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
        Sch082Form form) {
        GsMessage gsMsg = new GsMessage();

        Cmn999Form cmn999Form = new Cmn999Form();
//        ActionForward urlForward = null;

        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        MessageResources msgRes = getResources(req);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        cmn999Form.setUrlOK(map.findForward("changeOk").getPath());
        cmn999Form.setUrlCancel(map.findForward("changeCancel").getPath());

        //年
        String textYear
        = gsMsg.getMessage(req, "cmn.year", String.valueOf(form.getSch082AtdelYear()));
        //設定しない
        String textNoset = gsMsg.getMessage(req, "cmn.noset");
        //自動で削除する
        String textAutomatically = gsMsg.getMessage(req, "cmn.automatically.delete");
        //メッセージセット
        String msgState = "setting.kakunin.data";
        String mkey1 = gsMsg.getMessage(req, "schedule.108")
                     + " " +  gsMsg.getMessage(req, "cmn.autodelete");
        String mkey2 = null;
        if (form.getSch082AtdelFlg() ==  GSConstSchedule.AUTO_DELETE_OFF) {
            mkey2 = textNoset;
        } else {
            mkey2 = textAutomatically + "("
                    + textYear
                    + gsMsg.getMessage(
                            req, "cmn.months", String.valueOf(form.getSch082AtdelMonth())) + ")";
        }
        cmn999Form.setMessage(msgRes.getMessage(msgState, mkey1, mkey2));

        cmn999Form.addHiddenParam("cmd", "ok");
        cmn999Form.addHiddenParam("backScreen", form.getBackScreen());
        cmn999Form.addHiddenParam("sch082AtdelFlg", form.getSch082AtdelFlg());
        cmn999Form.addHiddenParam("sch082AtdelYear", form.getSch082AtdelYear());
        cmn999Form.addHiddenParam("sch082AtdelMonth", form.getSch082AtdelMonth());

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
    private ActionForward __doBack(ActionMapping map, Sch082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {
        log__.debug("戻る");
        return map.findForward("sch082back");
    }

    /**
     * <br>初期表処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doInit(ActionMapping map, Sch082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("初期表示");

        //初期値をセット
        Sch082Biz biz = new Sch082Biz(getRequestModel(req));
        Sch082ParamModel paramMdl = new Sch082ParamModel();
        paramMdl.setParam(form);
        biz.setInitData(paramMdl, con);
        paramMdl.setFormData(form);

        return __doSetShowData(map, form, req, res, con);
    }

    /**
     * <br>確認画面でキャンセル時再描画処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doRedraw(ActionMapping map, Sch082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        log__.debug("再表示");

        return __doSetShowData(map, form, req, res, con);
    }

    /**
     * <br>表示用データを取得、表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws SQLException SQL実行時例外
     */
    private ActionForward __doSetShowData(ActionMapping map, Sch082Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws SQLException {

        //表示用データを取得する
        Sch082Biz biz = new Sch082Biz(getRequestModel(req));
        Sch082ParamModel paramMdl = new Sch082ParamModel();
        paramMdl.setParam(form);
        biz.setShowData(paramMdl, con);
        paramMdl.setFormData(form);

        return map.getInputForward();
    }
}