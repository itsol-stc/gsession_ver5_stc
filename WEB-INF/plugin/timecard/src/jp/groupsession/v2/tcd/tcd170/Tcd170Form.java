package jp.groupsession.v2.tcd.tcd170;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.dao.TcdTcdataDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.tcd160.Tcd160Form;


/**
 * <br>[機  能] タイムカード 管理者設定 休日区分登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd170Form extends Tcd160Form {

    /** 休日区分名 */
    private String tcd170HolidayName__ = null;
    /** 休日集計区分 */
    private int tcd170HoliTotalKbn__ = GSConstTimecard.HOL_KBN_YUUKYUU;
    /** 休日内容区分 */
    private int tcd170HoliContentKbn__ = GSConstTimecard.HOLIDAY_CONTENT_KBN_NG;
    /** 使用制限 */
    private int tcd170Limit__ = GSConstTimecard.USE_KBN_OK;
    /** 初期表示Flg */
    private int tcd170initFlg__ = 0;
    /** 使用チェックFlg */
    private int tcd170useHolFlg__ = 0;

    /**
     * <p>tcd170HolidayName を取得します。
     * @return tcd170HolidayName
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HolidayName__
     */
    public String getTcd170HolidayName() {
        return tcd170HolidayName__;
    }

    /**
     * <p>tcd170HolidayName をセットします。
     * @param tcd170HolidayName tcd170HolidayName
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HolidayName__
     */
    public void setTcd170HolidayName(String tcd170HolidayName) {
        tcd170HolidayName__ = tcd170HolidayName;
    }

    /**
     * <p>tcd170HoliTotalKbn を取得します。
     * @return tcd170HoliTotalKbn
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HoliTotalKbn__
     */
    public int getTcd170HoliTotalKbn() {
        return tcd170HoliTotalKbn__;
    }

    /**
     * <p>tcd170HoliTotalKbn をセットします。
     * @param tcd170HoliTotalKbn tcd170HoliTotalKbn
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HoliTotalKbn__
     */
    public void setTcd170HoliTotalKbn(int tcd170HoliTotalKbn) {
        tcd170HoliTotalKbn__ = tcd170HoliTotalKbn;
    }

    /**
     * <p>tcd170HoliContentKbn を取得します。
     * @return tcd170HoliContentKbn
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HoliContentKbn__
     */
    public int getTcd170HoliContentKbn() {
        return tcd170HoliContentKbn__;
    }

    /**
     * <p>tcd170HoliContentKbn をセットします。
     * @param tcd170HoliContentKbn tcd170HoliContentKbn
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170HoliContentKbn__
     */
    public void setTcd170HoliContentKbn(int tcd170HoliContentKbn) {
        tcd170HoliContentKbn__ = tcd170HoliContentKbn;
    }

    /**
     * <p>tcd170Limit を取得します。
     * @return tcd170Limit
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170Limit__
     */
    public int getTcd170Limit() {
        return tcd170Limit__;
    }

    /**
     * <p>tcd170Limit をセットします。
     * @param tcd170Limit tcd170Limit
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170Limit__
     */
    public void setTcd170Limit(int tcd170Limit) {
        tcd170Limit__ = tcd170Limit;
    }

    /**
     * <p>tcd170initFlg を取得します。
     * @return tcd170initFlg
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170initFlg__
     */
    public int getTcd170initFlg() {
        return tcd170initFlg__;
    }

    /**
     * <p>tcd170initFlg をセットします。
     * @param tcd170initFlg tcd170initFlg
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170initFlg__
     */
    public void setTcd170initFlg(int tcd170initFlg) {
        tcd170initFlg__ = tcd170initFlg;
    }

    /**
     * <p>tcd170useHolFlg を取得します。
     * @return tcd170useHolFlg
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170useHolFlg__
     */
    public int getTcd170useHolFlg() {
        return tcd170useHolFlg__;
    }

    /**
     * <p>tcd170useHolFlg をセットします。
     * @param tcd170useHolFlg tcd170useHolFlg
     * @see jp.groupsession.v2.tcd.tcd170.Tcd170Form#tcd170useHolFlg__
     */
    public void setTcd170useHolFlg(int tcd170useHolFlg) {
        tcd170useHolFlg__ = tcd170useHolFlg;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return errors エラー
     * @throws SQLException 例外
     */
    public ActionErrors validateCheck(
            HttpServletRequest req, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        //休日区分名
        errors = GSValidateCommon.validateTextField(
                errors,
                tcd170HolidayName__,
                "tcd170HolidayName__",
                gsMsg.getMessage(req, "tcd.tcd160.02"),
                10,
                true);
        //休日集計区分
        errors = __holFlgCheck(
                errors,
                tcd170HoliTotalKbn__,
                "tcd170HoliTotalKbn__",
                gsMsg.getMessage(req, "tcd.tcd170.01"));
        //使用制限
        errors = __limitCheck(
                errors,
                tcd170Limit__,
                "tcd170Limit__",
                gsMsg.getMessage(req, "tcd.tcd170.03"));
        //休日内容区分
        errors = __limitCheck(
                errors,
                tcd170HoliContentKbn__,
                "tcd170HoliContentKbn__",
                gsMsg.getMessage(req, "tcd.tcd170.07"));

        //休日名重複チェック
        errors = __nameCheck(
                errors,
                tcd170HolidayName__,
                "tcd170HolidayName__",
                gsMsg.getMessage(req, "tcd.tcd160.02"),
                con);


       return errors;
    }

    /**
     * <br>[機  能] 休日名重複チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors リクエスト
     * @param chkObj チェックオブジェクト
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @param con コネクション
     * @return errors エラー
     * @throws SQLException
     */
    private ActionErrors __nameCheck(ActionErrors errors,  String chkObj,
            String paramName, String paramNameJpn, Connection con)
                    throws SQLException {

        ActionMessage msg = null;
        String fieldfix = paramName + ".";
        TcdHolidayInfDao thiDao = new TcdHolidayInfDao(con);
        TcdHolidayInfModel thiMdl =  thiDao.getHolidayDataWithName(chkObj);
        if (thiMdl != null
                && thiMdl.getThiSid() != getTcd170EditSid()) {

            msg = new ActionMessage("tcd170.tyoufuku.holiday.name", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, fieldfix);
            return errors;

        }
        return errors;
    }

    /**
     * 休日区分の存在チェックを行います
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return ActionErrors
     * @throws SQLException
     */
    public ActionErrors existsHoliday(RequestModel reqMdl, Connection con)
            throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        TcdHolidayInfDao dao = new TcdHolidayInfDao(con);
        GsMessage gsMsg = new GsMessage(reqMdl);
        int cnt = dao.existsHoliday(getTcd170EditSid(), false);
        if (cnt == 0) {
            String msg2 = gsMsg.getMessage("tcd.tcd140.04");
            msg = new ActionMessage(
                    "error.nothing.selected", msg2);
            StrutsUtil.addMessage(errors, msg, "tcd170EditSid__");
        }
        return errors;
    }

    /**
     * <br>[機  能] デフォルト休日の削除チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return errors エラー
     */
    protected ActionErrors defaultHoliday(RequestModel reqMdl) {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (getTcd170EditSid() <= GSConstTimecard.DEF_HOLIDAY_MAX_SID) {
            //デフォルトで作成された休日のため削除不可
            String msg2 = gsMsg.getMessage("tcd.tcd140.04");
            msg = new ActionMessage(
                    "error.nothing.selected", msg2);
            StrutsUtil.addMessage(errors, msg, "tcd170EditSid__");
        }
        return errors;
    }

    /**
     * <br>[機  能] 使用制限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors リクエスト
     * @param checkObject チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return errors エラー
     */
    private ActionErrors __limitCheck(ActionErrors errors, int checkObject,
            String paramName, String paramNameJpn) {
        ActionMessage msg = null;
        String fieldfix = paramName + ".";
        //0か1か
        if (checkObject != GSConstTimecard.USE_KBN_OK
                && checkObject != GSConstTimecard.USE_KBN_NG) {

            msg = new ActionMessage("error.input.notvalidate.data", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, fieldfix);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] 休日集計区分チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors リクエスト
     * @param checkObject チェックを行う値
     * @param paramName パラメータ名 (xxxDate, wtbxxxText 等)
     * @param paramNameJpn パラメータの日本語名 (日付, 時刻 等)
     * @return errors エラー
     */
    private ActionErrors __holFlgCheck(ActionErrors errors, int checkObject,
            String paramName, String paramNameJpn) {
        ActionMessage msg = null;
        String fieldfix = paramName + ".";
        //0か1か2か
        if (checkObject != GSConstTimecard.HOL_KBN_UNSELECT
                && checkObject != GSConstTimecard.HOL_KBN_KEKKIN
                && checkObject != GSConstTimecard.HOL_KBN_YUUKYUU) {

            msg = new ActionMessage("error.input.notvalidate.data", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, fieldfix);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] 勤務表出力時使用チェック後、タイムカード使用チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @return errors エラー
     * @throws SQLException 例外
     */
    public ActionErrors outputCheck(
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        //勤務表出力時使用チェックを行う
        if (getTcd170EditSid() == GSConstTimecard.THI_SID_KEKKIN
                || getTcd170EditSid() == GSConstTimecard.THI_SID_YUUKYU
                || getTcd170EditSid() == GSConstTimecard.THI_SID_DAIKYU
                || getTcd170EditSid() == GSConstTimecard.THI_SID_HURIKYU
                || getTcd170EditSid() == GSConstTimecard.THI_SID_KEITYO
                || getTcd170EditSid() == GSConstTimecard.THI_SID_SONOTA) {
            msg = new ActionMessage(
                    "tcd160.cannot.delete.holiday.output");
            errors.add("tcd160.cannot.delete.holiday.output", msg);
        }
        if (errors.size() == 0) {
            errors = __useCheck(con);
        }

       return errors;
    }

    /**
     * <br>[機  能] 休日区分使用チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @return errors エラー
     * @throws SQLException 例外
     */
    private ActionErrors __useCheck(Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        TcdTcdataDao dao = new TcdTcdataDao(con);
        int cnt = dao.selectUseData(getTcd170EditSid());
        if (cnt > 0) {
            msg = new ActionMessage(
                    "tcd160.cannot.delete.holiday.use");
            errors.add("tcd160.cannot.delete.holiday.use", msg);
        }

       return errors;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParamTcd170(Cmn999Form msgForm) {
        msgForm.addHiddenParam("tcd170EditSid", getTcd170EditSid());
        msgForm.addHiddenParam("tcd170Mode", getTcd170Mode());
    }
}
