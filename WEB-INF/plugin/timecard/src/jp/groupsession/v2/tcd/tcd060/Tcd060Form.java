package jp.groupsession.v2.tcd.tcd060;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneMeiModel;
import jp.groupsession.v2.tcd.tcd120.Tcd120Form;


/**
 * <br>[機  能] タイムカード 管理者設定 時間帯設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd060Form extends Tcd120Form {

    /** 初期表示フラグ */
    private int tcd060initFlg__ = 0;
    /** デフォルト時間帯フラグ */
    private int tcd060defTimezoneFlg__ = 0;
    /** 追加する時間帯区分*/
    private String addTimezoneKbn__;
    /** 編集する時間帯SID*/
    private String editTimezoneSid__;
    /** 使用区分設定フラグ */
    private int ttiUseSetFlg__ = 0;

    /** 時間帯設定名称*/
    private String tcd060Name__;
    /** 略称*/
    private String tcd060Ryaku__;
    /** 使用区分 1:可能 0:不可*/
    private int tcd060UseFlg__ = GSConstTimecard.TIMEZONE_USE_KBN_OK;
    /** 休日区分 1:休日 0:平日*/
    private int tcd060Holiday__;

    /** ダイアログ用変数 */

    /** 時間帯識別用番号 */
    private int tcd060ZoneNo__;
    /** 時間帯区分 */
    private String tcd060ZoneKbn__;
    /** 開始 時 */
    private String tcd060FrH__;
    /** 開始 分 */
    private String tcd060FrM__;
    /** 終了 時 */
    private String tcd060ToH__;
    /** 終了 分 */
    private String tcd060ToM__;
    /** ラジオ */
    private String tcd060TimeKbn__;
    /** ラベル 区分 */
    private List < LabelValueBean > tcd060ZoneLabel__ = null;
    /** ラベル 時 */
    private List < LabelValueBean > tcd060HourLabel__ = null;
    /** ラベル 分 */
    private List < LabelValueBean > tcd060MinuteLabel__ = null;

    /** 時間最大値 */
    private static final int MAX_HOUR = 24;
    /** 時間最小値 */
    private static final int MIN_HOUR = 0;
    /** 分最小値 */
    private static final int MIN_MINUTE = 0;
    /** 24:00セット時間 */
    private static final int MAX_HOUR_VALUE = 23;
    /** 24:00セット分 */
    private static final int MAX_MINUTE_VALUE = 59;

    /** エラー：なし */
    private static final int ERR_NONE = 0;
    /** エラー：開始時間不正 */
    private static final int ERR_INVALID_FRTIME = 1;
    /** エラー：終了時間不正 */
    private static final int ERR_INVALID_TOTIME = 2;
    /** エラー：開始時間 > 終了時間 */
    private static final int ERR_LARGE_FRTIME = 3;

    /** 時間帯明細部 */
    private ArrayList<TcdTimezoneMeiModel> tcd060TimezoneMeiList__;

    /**
     * インスタンス生成
     */
    public Tcd060Form() {
        tcd060TimezoneMeiList__ = new ArrayList<TcdTimezoneMeiModel>();
    }

    /**
     * <p>Tcd060Model を取得します。
     * @param iIndex インデックス番号
     * @return Tcd060Model を戻す
     */
    public TcdTimezoneMeiModel getMeiList(int iIndex) {
        while (tcd060TimezoneMeiList__.size() <= iIndex) {
            tcd060TimezoneMeiList__.add(new TcdTimezoneMeiModel());
        }
        return tcd060TimezoneMeiList__.get(iIndex);
    }

    /**
     * <p>tcd060initFlg を取得します。
     * @return tcd060initFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060initFlg__
     */
    public int getTcd060initFlg() {
        return tcd060initFlg__;
    }
    /**
     * <p>tcd060initFlg をセットします。
     * @param tcd060initFlg tcd060initFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060initFlg__
     */
    public void setTcd060initFlg(int tcd060initFlg) {
        tcd060initFlg__ = tcd060initFlg;
    }
    /**
     * <p>tcd060defTimezoneFlg を取得します。
     * @return tcd060defTimezoneFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060defTimezoneFlg__
     */
    public int getTcd060defTimezoneFlg() {
        return tcd060defTimezoneFlg__;
    }
    /**
     * <p>tcd060defTimezoneFlg をセットします。
     * @param tcd060defTimezoneFlg tcd060defTimezoneFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060defTimezoneFlg__
     */
    public void setTcd060defTimezoneFlg(int tcd060defTimezoneFlg) {
        tcd060defTimezoneFlg__ = tcd060defTimezoneFlg;
    }
    /**
     * <p>addTimezoneKbn を取得します。
     * @return addTimezoneKbn
     */
    public String getAddTimezoneKbn() {
        return addTimezoneKbn__;
    }
    /**
     * <p>addTimezoneKbn をセットします。
     * @param addTimezoneKbn addTimezoneKbn
     */
    public void setAddTimezoneKbn(String addTimezoneKbn) {
        addTimezoneKbn__ = addTimezoneKbn;
    }
    /**
     * <p>editTimezoneSid を取得します。
     * @return editTimezoneSid
     */
    public String getEditTimezoneSid() {
        return editTimezoneSid__;
    }
    /**
     * <p>editTimezoneSid をセットします。
     * @param editTimezoneSid editTimezoneSid
     */
    public void setEditTimezoneSid(String editTimezoneSid) {
        editTimezoneSid__ = editTimezoneSid;
    }
    /**
     * <p>ttiUseSetFlg を取得します。
     * @return ttiUseSetFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#ttiUseSetFlg__
     */
    public int getTtiUseSetFlg() {
        return ttiUseSetFlg__;
    }
    /**
     * <p>ttiUseSetFlg をセットします。
     * @param ttiUseSetFlg ttiUseSetFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#ttiUseSetFlg__
     */
    public void setTtiUseSetFlg(int ttiUseSetFlg) {
        ttiUseSetFlg__ = ttiUseSetFlg;
    }
    /**
     * <p>tcd060TimezoneMeiList を取得します。
     * @return tcd060TimezoneMeiList
     */
    public ArrayList<TcdTimezoneMeiModel> getTcd060TimezoneMeiList() {
        return tcd060TimezoneMeiList__;
    }
    /**
     * <p>tcd060TimezoneMeiList をセットします。
     * @param tcd060TimezoneMeiList tcd060TimezoneMeiList
     */
    public void setTcd060TimezoneMeiList(
            ArrayList<TcdTimezoneMeiModel> tcd060TimezoneMeiList) {
        tcd060TimezoneMeiList__ = tcd060TimezoneMeiList;
    }
    /**
     * <p>tcd060Name を取得します。
     * @return tcd060Name
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060Name__
     */
    public String getTcd060Name() {
        return tcd060Name__;
    }
    /**
     * <p>tcd060Name をセットします。
     * @param tcd060Name tcd060Name
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060Name__
     */
    public void setTcd060Name(String tcd060Name) {
        tcd060Name__ = tcd060Name;
    }
    /**
     * <p>tcd060Ryaku を取得します。
     * @return tcd060Ryaku
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060Ryaku__
     */
    public String getTcd060Ryaku() {
        return tcd060Ryaku__;
    }
    /**
     * <p>tcd060Ryaku をセットします。
     * @param tcd060Ryaku tcd060Ryaku
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060Ryaku__
     */
    public void setTcd060Ryaku(String tcd060Ryaku) {
        tcd060Ryaku__ = tcd060Ryaku;
    }
    /**
     * <p>tcd060UseFlg を取得します。
     * @return tcd060UseFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060UseFlg__
     */
    public int getTcd060UseFlg() {
        return tcd060UseFlg__;
    }
    /**
     * <p>tcd060UseFlg をセットします。
     * @param tcd060UseFlg tcd060UseFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060UseFlg__
     */
    public void setTcd060UseFlg(int tcd060UseFlg) {
        tcd060UseFlg__ = tcd060UseFlg;
    }
    /**
     * <p>tcd060Holiday を取得します。
     * @return tcd060Holiday
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060Holiday__
     */
    public int getTcd060Holiday() {
        return tcd060Holiday__;
    }
    /**
     * <p>tcd060Holiday をセットします。
     * @param tcd060Holiday tcd060Holiday
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060Holiday__
     */
    public void setTcd060Holiday(int tcd060Holiday) {
        tcd060Holiday__ = tcd060Holiday;
    }
    /**
     * <p>tcd060ZoneKbn を取得します。
     * @return tcd060ZoneKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ZoneKbn__
     */
    public String getTcd060ZoneKbn() {
        return tcd060ZoneKbn__;
    }
    /**
     * <p>tcd060ZoneKbn をセットします。
     * @param tcd060ZoneKbn tcd060ZoneKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ZoneKbn__
     */
    public void setTcd060ZoneKbn(String tcd060ZoneKbn) {
        tcd060ZoneKbn__ = tcd060ZoneKbn;
    }
    /**
     * <p>tcd060FrH を取得します。
     * @return tcd060FrH
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060FrH__
     */
    public String getTcd060FrH() {
        return tcd060FrH__;
    }
    /**
     * <p>tcd060FrH をセットします。
     * @param tcd060FrH tcd060FrH
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060FrH__
     */
    public void setTcd060FrH(String tcd060FrH) {
        tcd060FrH__ = tcd060FrH;
    }
    /**
     * <p>tcd060FrM を取得します。
     * @return tcd060FrM
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060FrM__
     */
    public String getTcd060FrM() {
        return tcd060FrM__;
    }
    /**
     * <p>tcd060FrM をセットします。
     * @param tcd060FrM tcd060FrM
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060FrM__
     */
    public void setTcd060FrM(String tcd060FrM) {
        tcd060FrM__ = tcd060FrM;
    }
    /**
     * <p>tcd060ToH を取得します。
     * @return tcd060ToH
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ToH__
     */
    public String getTcd060ToH() {
        return tcd060ToH__;
    }
    /**
     * <p>tcd060ToH をセットします。
     * @param tcd060ToH tcd060ToH
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ToH__
     */
    public void setTcd060ToH(String tcd060ToH) {
        tcd060ToH__ = tcd060ToH;
    }
    /**
     * <p>tcd060ToM を取得します。
     * @return tcd060ToM
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ToM__
     */
    public String getTcd060ToM() {
        return tcd060ToM__;
    }
    /**
     * <p>tcd060ToM をセットします。
     * @param tcd060ToM tcd060ToM
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ToM__
     */
    public void setTcd060ToM(String tcd060ToM) {
        tcd060ToM__ = tcd060ToM;
    }
    /**
     * <p>tcd060ZoneNo を取得します。
     * @return tcd060ZoneNo
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ZoneNo__
     */
    public int getTcd060ZoneNo() {
        return tcd060ZoneNo__;
    }
    /**
     * <p>tcd060ZoneNo をセットします。
     * @param tcd060ZoneNo tcd060ZoneNo
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ZoneNo__
     */
    public void setTcd060ZoneNo(int tcd060ZoneNo) {
        tcd060ZoneNo__ = tcd060ZoneNo;
    }
    /**
     * <p>tcd060TimeKbn を取得します。
     * @return tcd060TimeKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060TimeKbn__
     */
    public String getTcd060TimeKbn() {
        return tcd060TimeKbn__;
    }
    /**
     * <p>tcd060TimeKbn をセットします。
     * @param tcd060TimeKbn tcd060TimeKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060TimeKbn__
     */
    public void setTcd060TimeKbn(String tcd060TimeKbn) {
        tcd060TimeKbn__ = tcd060TimeKbn;
    }
    /**
     * <p>tcd060ZoneLabel を取得します。
     * @return tcd060ZoneLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ZoneLabel__
     */
    public List<LabelValueBean> getTcd060ZoneLabel() {
        return tcd060ZoneLabel__;
    }
    /**
     * <p>tcd060ZoneLabel をセットします。
     * @param tcd060ZoneLabel tcd060ZoneLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060ZoneLabel__
     */
    public void setTcd060ZoneLabel(List<LabelValueBean> tcd060ZoneLabel) {
        tcd060ZoneLabel__ = tcd060ZoneLabel;
    }
    /**
     * <p>tcd060HourLabel を取得します。
     * @return tcd060HourLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060HourLabel__
     */
    public List<LabelValueBean> getTcd060HourLabel() {
        return tcd060HourLabel__;
    }
    /**
     * <p>tcd060HourLabel をセットします。
     * @param tcd060HourLabel tcd060HourLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060HourLabel__
     */
    public void setTcd060HourLabel(List<LabelValueBean> tcd060HourLabel) {
        tcd060HourLabel__ = tcd060HourLabel;
    }
    /**
     * <p>tcd060MinuteLabel を取得します。
     * @return tcd060MinuteLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060MinuteLabel__
     */
    public List<LabelValueBean> getTcd060MinuteLabel() {
        return tcd060MinuteLabel__;
    }
    /**
     * <p>tcd060MinuteLabel をセットします。
     * @param tcd060MinuteLabel tcd060MinuteLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060MinuteLabel__
     */
    public void setTcd060MinuteLabel(List<LabelValueBean> tcd060MinuteLabel) {
        tcd060MinuteLabel__ = tcd060MinuteLabel;
    }


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(
            RequestModel reqMdl,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);

        //時間帯名称
        errors = GSValidateCommon.validateTextField(
                errors,
                tcd060Name__,
                "tcd060Name__",
                gsMsg.getMessage("tcd.tcd120.01"),
                100,
                true);
        //略称
        errors = GSValidateCommon.validateTextField(
                errors,
                tcd060Ryaku__,
                "tcd060Ryaku__",
                gsMsg.getMessage("tcd.tcd120.02"),
                5,
                true);
        //使用区分
        errors = __useFlgCheck(
                errors,
                tcd060UseFlg__,
                "tcd060UseFlg__",
                gsMsg.getMessage("tcd.tcd060.06"),
                con,
                reqMdl);

        //休日区分
        errors = __holFlgCheck(
                errors,
                tcd060Holiday__,
                "tcd060Holiday__",
                gsMsg.getMessage("tcd.tcd060.07"),
                con,
                reqMdl);

        //略称重複チェック
        TcdTimezoneInfoDao ttiDao = new TcdTimezoneInfoDao(con);
        List<TcdTimezoneInfoModel> ttiMdlList = ttiDao.selectZyogai(getTimezoneSid());
        if (ttiMdlList != null) {
            for (TcdTimezoneInfoModel ttiMdl : ttiMdlList) {
                if (ttiMdl.getTtiRyaku().equals(tcd060Ryaku__)) {
                    msg = new ActionMessage("tcd060.ryaku.timezone");
                    errors.add("tcd060Ryaku__", msg);
                    return errors;
                }
            }
        }

        //時間帯重複チェック
        ArrayList<TcdTimezoneMeiModel> chList = tcd060TimezoneMeiList__;
        if (chList == null || chList.isEmpty()) {
            msg = new ActionMessage(
                    "tcd060.error.none.timezone");
            errors.add("tcd060TimezoneMeiList__", msg);
            return errors;
        }
        ArrayList<TcdTimezoneMeiModel> chList2 = new ArrayList<TcdTimezoneMeiModel>();
        for (TcdTimezoneMeiModel chMdl: chList) {
            TcdTimezoneMeiModel bean = new TcdTimezoneMeiModel();
            bean.setFrTime(chMdl.getFrTime());
            bean.setToTime(chMdl.getToTime());
            bean.setTimeZoneKbn(chMdl.getTimeZoneKbn());
            bean.setTimeZoneNo(chMdl.getTimeZoneNo());
            chList2.add(bean);
        }
        for (TcdTimezoneMeiModel chMdl: chList) {

            // 区分チェック
            int chkKbn = NullDefault.getInt(chMdl.getTimeZoneKbn(), -1);
            if (!__isChkTimeKbn(chkKbn)) {
                 msg =  new ActionMessage("error.input.format.file",
                        gsMsg.getMessage("tcd.tcd070.01"),
                        gsMsg.getMessage("main.man340.10"));
                String eprefix = "tcd060ZoneKbn__";
                StrutsUtil.addMessage(errors, msg, eprefix);
                return errors;
            }
            LocalTime frTime = chMdl.getFrTime().toLocalTime();
            LocalTime toTime = chMdl.getToTime().toLocalTime();
            // 時間チェック
            int frH = frTime.getHour();
            int frM = frTime.getMinute();
            int toH = toTime.getHour();
            int toM = toTime.getMinute();
            if (toH == MIN_HOUR && toM == MIN_MINUTE) {
                toH = MAX_HOUR;
            }
            UDate inDate = new UDate();
            inDate.setZeroHhMmSs();
            inDate.setHour(frH);
            inDate.setMinute(frM);
            UDate outDate = inDate.cloneUDate();
            if (toH == MAX_HOUR  && toM == MIN_MINUTE) {
                outDate.setHour(MAX_HOUR_VALUE);
                outDate.setMinute(MAX_MINUTE_VALUE);
            } else {
                outDate.setHour(toH);
                outDate.setMinute(toM);
            }
            int errChk = __isChkTime(frH, frM, toH, toM, inDate, outDate);
            // エラー確認
            if (errChk == ERR_INVALID_FRTIME) {
                msg = new ActionMessage("error.select3.required.text", gsMsg.getMessage("tcd.171"));
                errors.add("tcd060FrTime", msg);
                return errors;
            } else if (errChk ==  ERR_INVALID_TOTIME) {
                msg = new ActionMessage("error.select3.required.text", gsMsg.getMessage("tcd.173"));
                errors.add("tcd060toTime", msg);
                return errors;
            } else if (errChk ==  ERR_LARGE_FRTIME) {
                String gsmsg2 = gsMsg.getMessage("tcd.184");
                String gsmsg3 = gsMsg.getMessage("cmn.start.lessthan.end");
                msg = new ActionMessage("error.input.comp.text", gsmsg2, gsmsg3);
                errors.add("" + "error.input.comp.text", msg);
                return errors;
            }
            //時間帯が重複している場合はエラー
            if (__isRepetitionTimeZone(
                    chList2,
                    chkKbn,
                    chMdl.getTimeZoneNo(),
                    frTime,
                    toTime)) {
                msg = new ActionMessage(
                        "error.input.double.timezone",
                        gsMsg.getMessage("tcd.184"),
                        gsMsg.getMessage("tcd.184"));
                errors.add("error.input.double.timezone", msg);
                return errors;
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 使用区分のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param message エラーメッセージ表示テキスト
     * @param checkObject チェックするフィールドの数値
     * @param fieldName チェックするフィールド
     * @param con コネクション
     * @return ActionErrors
     */
    private ActionErrors __useFlgCheck(ActionErrors errors, int checkObject, String fieldName,
            String message, Connection con, RequestModel reqMdl) throws SQLException {
        ActionMessage msg = null;
        String fieldfix = checkObject + ".";
        //0か1か
        if (checkObject != GSConstTimecard.TIMEZONE_USE_KBN_OK
                && checkObject != GSConstTimecard.TIMEZONE_USE_KBN_NG) {

            msg = new ActionMessage("error.input.notvalidate.data", message);
            StrutsUtil.addMessage(errors, msg, fieldfix);
            return errors;
        }
        //編集時
        if (getTimezoneCmdMode() == 1) {
            //使用チェック
            //セッション情報を取得
            BaseUserModel usModel = reqMdl.getSmodel();
            int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

            //DBより設定情報を取得。なければデフォルト値とする。
            TimecardBiz tcdBiz = new TimecardBiz(reqMdl);
            TcdAdmConfModel admMdl = tcdBiz.getTcdAdmConfModel(sessionUsrSid, con);
            if (admMdl != null) {
                if (admMdl.getTacDefTimezone() == getTimezoneSid()
                        && checkObject == GSConstTimecard.TIMEZONE_USE_KBN_NG) {
                    msg = new ActionMessage("tcd060.cannot.change.useflg", message);
                    StrutsUtil.addMessage(errors, msg, fieldfix);
                    return errors;
                }
            }
            if (checkObject == GSConstTimecard.TIMEZONE_USE_KBN_NG) {
                TcdTimezonePriDao ttpDao = new TcdTimezonePriDao(con);
                int useUserCnt = ttpDao.selectUseUserCount(getTimezoneSid());
                if (useUserCnt != 0) {
                    msg = new ActionMessage("tcd120.notdelete.user");
                    StrutsUtil.addMessage(errors, msg, "fieldfix");
                    return errors;
                }
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 休日区分のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param message エラーメッセージ表示テキスト
     * @param checkObject チェックするフィールドの数値
     * @param fieldName チェックするフィールド
     * @param con コネクション
     * @return ActionErrors
     */
    private ActionErrors __holFlgCheck(ActionErrors errors, int checkObject, String fieldName,
            String message, Connection con, RequestModel reqMdl) throws SQLException {
        ActionMessage msg = null;
        String fieldfix = checkObject + ".";
        //0か1か
        if (checkObject != GSConstTimecard.HOLKBN_WEEKDAY
                && checkObject != GSConstTimecard.HOLKBN_HOLDAY) {

            msg = new ActionMessage("error.input.notvalidate.data", message);
            StrutsUtil.addMessage(errors, msg, fieldfix);
            return errors;
        }
        return errors;
    }

    /**
     * <br>[機  能] 入力した時間帯が登録済み時間帯と重複しているか判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param chList 時間帯一覧
     * @param zoneKbn 選択時間帯区分
     * @param zoneNo 選択時間帯No
     * @param frTime1 選択時間帯開始時間
     * @param toTime1 選択時間帯終了時間
     * @return true:重複している false:重複していない
     * @throws SQLException SQL実行時例外
     */
    private boolean __isRepetitionTimeZone(
            ArrayList<TcdTimezoneMeiModel> chList,
            int zoneKbn,
            int zoneNo,
            LocalTime frTime1,
            LocalTime toTime1
            ) {
        // 他に時間帯設定が存在しない場合は重複なし
        if (chList == null || chList.isEmpty()) {
            return false;
        }
        boolean ret = false;
        for (TcdTimezoneMeiModel chMdl: chList) {
            //時間帯区分がどちらも深夜時間帯の場合、あるいはどちらも深夜時間帯でない場合のみ確認。
            int zoneKbn2 = NullDefault.getInt(chMdl.getTimeZoneKbn(), -1);
            if ( (zoneKbn == GSConstTimecard.TIMEZONE_KBN_SINYA
                    &&  zoneKbn2 != GSConstTimecard.TIMEZONE_KBN_SINYA)
                    ||  ( zoneKbn != GSConstTimecard.TIMEZONE_KBN_SINYA
                            &&  zoneKbn2 == GSConstTimecard.TIMEZONE_KBN_SINYA)) {
                 continue;
            }
            LocalTime frTime2 = chMdl.getFrTime().toLocalTime();
            LocalTime toTime2 = chMdl.getToTime().toLocalTime();
            // 24:00 は0:00で格納されているため24:00に戻す
            if (frTime2.compareTo(toTime2) >= UDate.EQUAL) {
                UDate maxDate = new UDate();
                maxDate.setZeroHhMmSs();
                maxDate.setHour(MAX_HOUR_VALUE);
                maxDate.setMinute(MAX_MINUTE_VALUE);
                toTime2 = new Time(maxDate.getTime()).toLocalTime();
                if (toTime2.compareTo(toTime1) == UDate.EQUAL
                        && chMdl.getTimeZoneNo() != zoneNo) {
                    return true;
                }
            }
            // 比較
            if (__isOverTimeZone(frTime1, toTime1, frTime2, toTime2)) {
                // 編集の場合は編集対象を除外する
                if (chMdl.getTimeZoneNo() == zoneNo) {
                    ret = false;
                } else {
                    // 重複なのでエラー
                    ret =  true;
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] ダイアログの入力チェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return ActionErrors
     */
    public ActionErrors validateDialogCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        String gsmsg = "";

        // 区分チェック
        int chkKbn = NullDefault.getInt(tcd060ZoneKbn__, -1);
        if (!__isChkTimeKbn(chkKbn)) {
             msg =  new ActionMessage("error.input.format.file",
                    gsMsg.getMessage("tcd.tcd070.01"),
                    gsMsg.getMessage("tcd.tcd070.03"));
            String eprefix = "tcd060ZoneKbn__";
            StrutsUtil.addMessage(errors, msg, eprefix);
            return errors;
        }

        // 時間チェック
        int frH = NullDefault.getInt(tcd060FrH__, -1);
        int frM = NullDefault.getInt(tcd060FrM__, -1);
        int toH = NullDefault.getInt(tcd060ToH__, -1);
        int toM = NullDefault.getInt(tcd060ToM__, -1);

        UDate inDate = new UDate();
        inDate.setZeroHhMmSs();
        inDate.setHour(frH);
        inDate.setMinute(frM);
        UDate outDate = inDate.cloneUDate();

        if (toH == MAX_HOUR  && toM == MIN_MINUTE) {
            outDate.setHour(MAX_HOUR_VALUE);
            outDate.setMinute(MAX_MINUTE_VALUE);
        } else {
            outDate.setHour(toH);
            outDate.setMinute(toM);
        }
        int errChk = __isChkTime(frH, frM, toH, toM, inDate, outDate);

        // エラー確認
        if (errChk == ERR_INVALID_FRTIME) {
            gsmsg = gsMsg.getMessage("tcd.171");
            msg = new ActionMessage("error.select3.required.text", gsmsg);
            errors.add("tcd060FrTime", msg);
            return errors;
        } else if (errChk ==  ERR_INVALID_TOTIME) {
            gsmsg = gsMsg.getMessage("tcd.173");
            msg = new ActionMessage("error.select3.required.text", gsmsg);
            errors.add("tcd060toTime", msg);
            return errors;
        } else if (errChk ==  ERR_LARGE_FRTIME) {
            String gsmsg2 = gsMsg.getMessage("cmn.start.end");
            String gsmsg3 = gsMsg.getMessage("cmn.start.lessthan.end");
            msg = new ActionMessage("error.input.comp.text", gsmsg2, gsmsg3);
            errors.add("" + "error.input.comp.text", msg);
            return errors;
        }

        //時間帯が重複している場合はエラー
        if (__isRepetitionTimeZone(
                tcd060TimezoneMeiList__,
                chkKbn,
                tcd060ZoneNo__,
                new Time(inDate.getTime()).toLocalTime(),
                new Time(outDate.getTime()).toLocalTime())) {
            msg = new ActionMessage(
                    "error.input.double.timezone",
                    gsMsg.getMessage("tcd.184"),
                    gsMsg.getMessage("tcd.184"));
            errors.add("error.input.double.timezone", msg);
        }

        return errors;
    }

    /**
     * <br>[機  能] 時間帯チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param chkKbn 時間帯区分
     * @return boolean false エラー
     */
    private boolean __isChkTimeKbn(
            int chkKbn) {
        if (chkKbn != GSConstTimecard.TIMEZONE_KBN_NORMAL
                && chkKbn != GSConstTimecard.TIMEZONE_KBN_ZANGYO
                && chkKbn != GSConstTimecard.TIMEZONE_KBN_SINYA
                && chkKbn != GSConstTimecard.TIMEZONE_KBN_KYUKEI) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 範囲チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param frH 開始時
     * @param frM 開始分
     * @param toH 終了時
     * @param toM 終了分
     * @param frTime 開始時間
     * @param toTime 終了時間
     * @return boolean true=範囲内、false=範囲外
     */
    private int __isChkTime(
            int frH,
            int frM,
            int toH,
            int toM,
            UDate frTime,
            UDate toTime) {

        // 開始:0:00～24:00範囲チェック
        if (!__isTimeRange(frH, frM)) {
            return ERR_INVALID_FRTIME;
        }
        // 終了：0:00～24:00範囲チェック
        if (!__isTimeRange(toH, toM)) {
            return ERR_INVALID_TOTIME;
        }
        // 開始時間 >= 終了時間チェック
        if (frH == MAX_HOUR || (frH == toH && frM == toM)) {
            return ERR_LARGE_FRTIME;
        }
        if (frTime.compare(frTime, toTime) < UDate.EQUAL) {
            return ERR_LARGE_FRTIME;
        }

        return ERR_NONE;
    }

    /**
     * <br>[機  能] 0:00～24:00範囲チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param hour 時
     * @param minute 分
     * @return boolean true=範囲内、false=範囲外
     */
    private boolean __isTimeRange(int hour, int minute) {
        boolean rangeHour = (MIN_HOUR <= hour && hour <= MAX_HOUR);
        boolean rangeMinute = (MIN_MINUTE <= minute && minute <= MAX_MINUTE_VALUE);
        boolean is24 = !(hour == MAX_HOUR && minute != MIN_MINUTE);
        return rangeHour && rangeMinute && is24;
    }

    /**
     * <br>[機  能] 重複チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param fr1 LocalTime
     * @param to1 LocalTime
     * @param fr2 LocalTime
     * @param to2 LocalTime
     * @return boolean true=範囲内、false=範囲外
     */
    private boolean __isOverTimeZone(LocalTime fr1, LocalTime to1, LocalTime fr2, LocalTime to2) {

        return fr1.compareTo(to2) < UDate.EQUAL && to1.compareTo(fr2) > UDate.EQUAL;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParamTcd060(Cmn999Form msgForm) {
        msgForm.addHiddenParam("tcd060initFlg", tcd060initFlg__);
        msgForm.addHiddenParam("tcd060ZoneKbn", tcd060ZoneKbn__);
        msgForm.addHiddenParam("tcd060FrH", tcd060FrH__);
        msgForm.addHiddenParam("tcd060FrM", tcd060FrM__);
        msgForm.addHiddenParam("tcd060ToH", tcd060ToH__);
        msgForm.addHiddenParam("tcd060ToM", tcd060ToM__);
        msgForm.addHiddenParam("tcd060Name", tcd060Name__);
        msgForm.addHiddenParam("tcd060Ryaku", tcd060Ryaku__);
        msgForm.addHiddenParam("tcd060UseFlg", tcd060UseFlg__);
        msgForm.addHiddenParam("tcd060Holiday", tcd060Holiday__);
        if (tcd060TimezoneMeiList__ != null && !tcd060TimezoneMeiList__.isEmpty()) {
            int index = 0;
            for (TcdTimezoneMeiModel value : tcd060TimezoneMeiList__) {
                msgForm.addHiddenParam("meiList[" + index + "].frTime",
                        String.valueOf(value.getFrTime()));
                msgForm.addHiddenParam("meiList[" + index + "].toTime",
                        String.valueOf(value.getToTime()));
                msgForm.addHiddenParam("meiList[" + index + "].timeZoneKbn",
                        value.getTimeZoneKbn());
                msgForm.addHiddenParam("meiList[" + index + "].timeZoneNo",
                        value.getTimeZoneNo());
                msgForm.addHiddenParam("meiList[" + index + "].timeZoneStr",
                        value.getTimeZoneStr());
                index++;
            }
        }
    }
}
