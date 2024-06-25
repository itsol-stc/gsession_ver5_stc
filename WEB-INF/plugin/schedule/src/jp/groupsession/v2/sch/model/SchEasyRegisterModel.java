package jp.groupsession.v2.sch.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.GSValidateSchedule;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 簡易登録の吹き出し情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchEasyRegisterModel extends AbstractModel {

    /** 選択ユーザSID*/
    private int selectSid__;
    /** 選択区分 0:ユーザ 1:グループ*/
    private int selectKbn__;
    /** 選択日付*/
    private String selectDate__;

    /** 開始日付*/
    private String frDate__;
    /** 開始時間*/
    private String frTime__;
    /** 開始時間 初期値*/
    private String initFrTime__;

    /** 終了日付*/
    private String toDate__;
    /** 終了時間*/
    private String toTime__;
    /** 終了時間 初期値*/
    private String initToTime__;

    /** 午前開始時 */
    private int amFrHour__;
    /** 午前開始分 */
    private int amFrMin__;
    /** 午前終了時 */
    private int amToHour__;
    /** 午前終了分 */
    private int amToMin__;

    /** 午後開始時 */
    private int pmFrHour__;
    /** 午後開始分 */
    private int pmFrMin__;
    /** 午後終了時 */
    private int pmToHour__;
    /** 午後終了分 */
    private int pmToMin__;

    /** 終日開始時 */
    private int allDayFrHour__;
    /** 終日開始分 */
    private int allDayFrMin__;
    /** 終日終了時 */
    private int allDayToHour__;
    /** 終日終了分 */
    private int allDayToMin__;

    /** 時間指定無し区分*/
    private int timeFree__ = GSConstSchedule.TIME_EXIST;

    /** タイトルカラー表示区分*/
    private int titleColorKbn__ = GSConstSchedule.SAD_MSG_NO_ADD;
    /** タイトルカラー*/
    private int titleColor__ = GSConstSchedule.DF_BG_COLOR;
    /** タイトルカラー 初期値 */
    private int initTitleColor__ = GSConstSchedule.DF_BG_COLOR;
    /** タイトル*/
    private String title__;

    /** 内容*/
    private String content__;

    /** 公開区分*/
    private int publicKbn__ = GSConstSchedule.DSP_PUBLIC;
    /** 公開区分 初期値 */
    private int initPubKbn__ = GSConstSchedule.DSP_PUBLIC;

    /** 時間単位*/
    private int timeUnits__ = GSConstSchedule.DF_HOUR_DIVISION;


    /**
     * <br>[機  能]簡易登録用validateチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return errors エラー
     * @throws SQLException
     */
    public ActionErrors validateCheck(RequestModel reqMdl,
            Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        HttpSession session = reqMdl.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
        if (selectSid__ >= 0) {
            SchDao schDao = new SchDao(con);
            if (selectKbn__ == GSConstSchedule.USER_KBN_GROUP) {
                //グループスケジュール登録権限チェック
                if (!schDao.canRegistGroupSchedule(selectSid__, sessionUsrSid)) {
                    msg = new ActionMessage("error.edit.power.user",
                            gsMsg.getMessage("cmn.edit"),
                            gsMsg.getMessage("cmn.entry"));
                    errors.add("error.edit.power.user", msg);
                    return errors;
                }
            } else {
                //ユーザスケジュール登録権限チェック
                if (!schDao.canRegistUserSchedule(selectSid__, sessionUsrSid)) {
                    msg = new ActionMessage("error.edit.power.user",
                            gsMsg.getMessage("cmn.edit"),
                            gsMsg.getMessage("cmn.entry"));
                    errors.add("error.edit.power.user", msg);
                    return errors;
                }
            }
        }


        //開始年月日 空チェック
        if (frDate__ == null) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.10"));
            errors.add("error.input.notfound.date", msg);
            return errors;
        }
        //開始年月日 長さチェック
        if (frDate__.length() != 10) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.10"));
            errors.add("error.input.notfound.date", msg);
            return errors;
        }

        UDate frDate = new UDate();
        frDate.setDate(frDate__.replace("/", ""));
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        String fDate = frDate__.replace("/", "");
        int nFYear = Integer.parseInt(fDate.substring(0, 4));
        int nFMonth = Integer.parseInt(fDate.substring(4, 6));
        int nFDay = Integer.parseInt(fDate.substring(6, 8));

        //開始年月日 フォーマットチェック
        if (frDate.getYear() != nFYear
                || frDate.getMonth() != nFMonth
                || frDate.getIntDay() != nFDay) {
                    msg = new ActionMessage("error.input.notfound.date",
                            gsMsg.getMessage("schedule.sch100.10"));
                    errors.add("error.input.notfound.date", msg);
                    return errors;
        }
        //終了年月日 空チェック
        if (toDate__ == null) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.15"));
            errors.add("error.input.notfound.date", msg);
            return errors;
        }
        //終了年月日 長さチェック
        if (toDate__.length() != 10) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.15"));
            errors.add("error.input.notfound.date", msg);
            return errors;
        }
        UDate toDate = new UDate();
        toDate.setDate(toDate__.replace("/", ""));
        toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        String tDate = toDate__.replace("/", "");
        int nTYear = Integer.parseInt(tDate.substring(0, 4));
        int nTMonth = Integer.parseInt(tDate.substring(4, 6));
        int nTDay = Integer.parseInt(tDate.substring(6, 8));

        //終了年月日 フォーマットチェック
        if (toDate.getYear() != nTYear
                || toDate.getMonth() != nTMonth
                || toDate.getIntDay() != nTDay) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.15"));
            errors.add("error.input.notfound.date", msg);
                    return errors;
        }

        if (timeFree__ == GSConstSchedule.TIME_EXIST) {
            //開始時間 空チェック
            if (frTime__ == null) {
                String textHourMinute = gsMsg.getMessage("schedule.src.36");
                msg = new ActionMessage("error.input.required.text", textHourMinute);
                errors.add("" + "error.input.required.text", msg);
                return errors;
            }
            //開始時間 長さチェック
            if (frTime__.length() != 5) {
                String textHourMinute = gsMsg.getMessage("schedule.src.36");
                msg = new ActionMessage("error.input.required.text", textHourMinute);
                errors.add("" + "error.input.required.text", msg);
                return errors;
            }
            //終了時間 空チェック
            if (toTime__ == null) {
                String textHourMinute = gsMsg.getMessage("schedule.src.36");
                msg = new ActionMessage("error.input.required.text", textHourMinute);
                errors.add("" + "error.input.required.text", msg);
                return errors;
            }
            //終了時間 長さチェック
            if (toTime__.length() != 5) {
                String textHourMinute = gsMsg.getMessage("schedule.src.36");
                msg = new ActionMessage("error.input.required.text", textHourMinute);
                errors.add("" + "error.input.required.text", msg);
                return errors;
            }
            //時間単位チェック
            String frMinute = frTime__.substring(3, 5);
            String toMinute = toTime__.substring(3, 5);
            int frTime = Integer.parseInt(frMinute);
            int toTime = Integer.parseInt(toMinute);
            errors = GSValidateSchedule.validateTimeUnit(
                    con, errors, gsMsg, frTime, toTime);
            if (errors.size() > 0) {
                return errors;
            }
        }

        //個別チェックOKの場合
        if (timeFree__ == GSConstSchedule.TIME_EXIST) {
            int frHour = Integer.parseInt(frTime__.substring(0, 2));
            int frMin = Integer.parseInt(frTime__.substring(3, 5));
            int toHour = Integer.parseInt(toTime__.substring(0, 2));
            int toMin = Integer.parseInt(toTime__.substring(3, 5));
            frDate.setHour(frHour);
            frDate.setMinute(frMin);
            toDate.setHour(toHour);
            toDate.setMinute(toMin);
        } else {
            frDate.setZeroHhMmSs();
            toDate.setMaxHhMmSs();
        }

        //from～to大小チェック
        if (frDate.compare(frDate, toDate) != UDate.LARGE) {
            //開始 < 終了
            String textStartLessEnd = gsMsg.getMessage("cmn.start.lessthan.end");
            //開始・終了
            String textStartEnd = gsMsg.getMessage("cmn.start.end");
            msg = new ActionMessage("error.input.comp.text", textStartEnd, textStartLessEnd);
            errors.add("" + "error.input.comp.text", msg);
            return errors;
        }

        //タイトルのチェック
        if (__checkNoInput(errors, title__, "title",
                gsMsg.getMessage("cmn.title"))) {
            if (__checkRange(
                    errors,
                    title__,
                    "title",
                    gsMsg.getMessage("cmn.title"),
                    GSConstSchedule.MAX_LENGTH_TITLE)) {
                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(title__)) {
                    //タイトル
                    String textTitle = gsMsg.getMessage("cmn.title");
                    msg = new ActionMessage("error.input.spase.start",
                            textTitle);
                    StrutsUtil.addMessage(errors, msg, "title");
                //タブスペースチェック
                } else if (ValidateUtil.isTab(title__)) {
                    //タイトル
                    String textTitle = gsMsg.getMessage("cmn.title");
                    msg = new ActionMessage("error.input.tab.text",
                            textTitle);
                    StrutsUtil.addMessage(errors, msg, "title");

                } else {
                    __checkJisString(
                            errors,
                            title__,
                            "title",
                            gsMsg.getMessage("cmn.title"));
                }
            }
        }
        if (errors.size() > 0) {
            return errors;
        }
        boolean valueError = false;
        //内容のチェック
        if (__checkRangeTextarea(
                errors,
                content__,
                "content",
                gsMsg.getMessage("cmn.content"),
                GSConstSchedule.MAX_LENGTH_VALUE)) {
            if (!StringUtil.isNullZeroString(content__)) {
                //スペースのみチェック
                if (!valueError && ValidateUtil.isSpaceOrKaigyou(content__)) {
                    msg = new ActionMessage("error.input.spase.cl.only",
                            gsMsg.getMessage("cmn.content"));
                    StrutsUtil.addMessage(errors, msg, "content");
                    valueError = true;
                }

                if (!valueError) {
                    //JIS
                    __checkJisString(
                            errors,
                            content__,
                            "content",
                            gsMsg.getMessage("cmn.content"));
                }
            }
        }
        //スケジュール重複登録チェック
        if (timeFree__ == GSConstSchedule.TIME_EXIST
                && selectKbn__ != GSConstSchedule.USER_KBN_GROUP) {
            SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);

            //重複登録 NGスケジュール一覧を取得する。
            List<SchDataModel> rptSchList
            = schBiz.getSchWarningList(sessionUsrSid, selectSid__, selectKbn__,
                    frDate__, frTime__, toDate__, toTime__, GSConstSchedule.SCH_REPEAT_KBN_NG);
            if (rptSchList != null && rptSchList.size() > 0) {
                msg = new ActionMessage("error.input.dup2");
                errors.add("error.input.dup2", msg);
                return errors;
            }
        }
        return errors;
    }

    /**
     * <br>[機  能]簡易登録 重複validateチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return errors エラー
     * @throws SQLException
     */
    public ActionErrors validateDupCheck(RequestModel reqMdl,
            Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        HttpSession session = reqMdl.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
        if (selectSid__ >= 0) {
            SchDao schDao = new SchDao(con);
            if (selectKbn__ == GSConstSchedule.USER_KBN_GROUP) {
                //グループスケジュール登録権限チェック
                if (!schDao.canRegistGroupSchedule(selectSid__, sessionUsrSid)) {
                    msg = new ActionMessage("error.edit.power.user",
                            gsMsg.getMessage("cmn.edit"),
                            gsMsg.getMessage("cmn.entry"));
                    errors.add("error.edit.power.user", msg);
                    return errors;
                }
            } else {
                //ユーザスケジュール登録権限チェック
                if (!schDao.canRegistUserSchedule(selectSid__, sessionUsrSid)) {
                    msg = new ActionMessage("error.edit.power.user",
                            gsMsg.getMessage("cmn.edit"),
                            gsMsg.getMessage("cmn.entry"));
                    errors.add("error.edit.power.user", msg);
                    return errors;
                }
            }
        }

        //開始年月日 空チェック
        if (frDate__ == null) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.10"));
            errors.add("error.input.notfound.date", msg);
            return errors;
        }
        //開始年月日 長さチェック
        if (frDate__.length() != 10) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.10"));
            errors.add("error.input.notfound.date", msg);
            return errors;
        }

        UDate frDate = new UDate();
        frDate.setDate(frDate__.replace("/", ""));
        frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        String fDate = frDate__.replace("/", "");
        int nFYear = Integer.parseInt(fDate.substring(0, 4));
        int nFMonth = Integer.parseInt(fDate.substring(4, 6));
        int nFDay = Integer.parseInt(fDate.substring(6, 8));

        //開始年月日 フォーマットチェック
        if (frDate.getYear() != nFYear
                || frDate.getMonth() != nFMonth
                || frDate.getIntDay() != nFDay) {
                    msg = new ActionMessage("error.input.notfound.date",
                            gsMsg.getMessage("schedule.sch100.10"));
                    errors.add("error.input.notfound.date", msg);
                    return errors;
        }
        //終了年月日 空チェック
        if (toDate__ == null) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.15"));
            errors.add("error.input.notfound.date", msg);
            return errors;
        }
        //終了年月日 長さチェック
        if (toDate__.length() != 10) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.15"));
            errors.add("error.input.notfound.date", msg);
            return errors;
        }
        UDate toDate = new UDate();
        toDate.setDate(toDate__.replace("/", ""));
        toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        String tDate = toDate__.replace("/", "");
        int nTYear = Integer.parseInt(tDate.substring(0, 4));
        int nTMonth = Integer.parseInt(tDate.substring(4, 6));
        int nTDay = Integer.parseInt(tDate.substring(6, 8));

        //終了年月日 フォーマットチェック
        if (toDate.getYear() != nTYear
                || toDate.getMonth() != nTMonth
                || toDate.getIntDay() != nTDay) {
            msg = new ActionMessage("error.input.notfound.date",
                    gsMsg.getMessage("schedule.sch100.15"));
            errors.add("error.input.notfound.date", msg);
                    return errors;
        }

        if (timeFree__ == GSConstSchedule.TIME_EXIST) {
            //開始時間 空チェック
            if (frTime__ == null) {
                String textHourMinute = gsMsg.getMessage("schedule.src.36");
                msg = new ActionMessage("error.input.required.text", textHourMinute);
                errors.add("" + "error.input.required.text", msg);
                return errors;
            }
            //開始時間 長さチェック
            if (frTime__.length() != 5) {
                String textHourMinute = gsMsg.getMessage("schedule.src.36");
                msg = new ActionMessage("error.input.required.text", textHourMinute);
                errors.add("" + "error.input.required.text", msg);
                return errors;
            }
            //終了時間 空チェック
            if (toTime__ == null) {
                String textHourMinute = gsMsg.getMessage("schedule.src.36");
                msg = new ActionMessage("error.input.required.text", textHourMinute);
                errors.add("" + "error.input.required.text", msg);
                return errors;
            }
            //終了時間 長さチェック
            if (toTime__.length() != 5) {
                String textHourMinute = gsMsg.getMessage("schedule.src.36");
                msg = new ActionMessage("error.input.required.text", textHourMinute);
                errors.add("" + "error.input.required.text", msg);
                return errors;
            }
        }

        //個別チェックOKの場合
        if (timeFree__ == GSConstSchedule.TIME_EXIST) {
            int frHour = Integer.parseInt(frTime__.substring(0, 2));
            int frMin = Integer.parseInt(frTime__.substring(3, 5));
            int toHour = Integer.parseInt(toTime__.substring(0, 2));
            int toMin = Integer.parseInt(toTime__.substring(3, 5));
            frDate.setHour(frHour);
            frDate.setMinute(frMin);
            toDate.setHour(toHour);
            toDate.setMinute(toMin);
        } else {
            frDate.setZeroHhMmSs();
            toDate.setMaxHhMmSs();
        }

        //スケジュール重複登録チェック
        if (timeFree__ == GSConstSchedule.TIME_EXIST
                && selectKbn__ != GSConstSchedule.USER_KBN_GROUP) {
            SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);

            //重複登録 NGスケジュール一覧を取得する。
            List<SchDataModel> rptSchList
            = schBiz.getSchWarningList(sessionUsrSid, selectSid__, selectKbn__,
                    frDate__, frTime__, toDate__, toTime__, GSConstSchedule.SCH_REPEAT_KBN_NG);
            if (rptSchList != null && rptSchList.size() > 0) {
                msg = new ActionMessage("error.input.dup2");
                errors.add("error.input.dup2", msg);
                return errors;
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 指定された項目の未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkNoInput(ActionErrors errors,
                                    String value,
                                    String element,
                                    String elementName) {
        boolean result = true;
        ActionMessage msg = null;

        if (StringUtil.isNullZeroString(value)) {
            msg = new ActionMessage("error.input.required.text", elementName);
            errors.add(element + "error.input.required.text", msg);
            result = false;
        } else {
            //スペースのみの入力かチェック
            if (ValidateUtil.isSpace(value)) {
                msg = new ActionMessage("error.input.spase.only", elementName);
                errors.add(element + "error.input.spase.only", msg);
                result = false;
            }
        }
        return result;
    }

    /**
     * <br>[機  能] 指定された項目の桁数チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @param range 桁数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkRange(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.text", elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.text", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能] 指定された項目がJIS第2水準文字かチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkJisString(ActionErrors errors,
                                String value,
                                String element,
                                String elementName) {
        boolean result = true;
        ActionMessage msg = null;
        //JIS第2水準文字か
        if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(value);
            msg = new ActionMessage("error.input.njapan.text2", elementName, nstr);
            errors.add(element + "error.input.njapan.text2", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能] 指定された項目の桁数チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param value 項目の値
     * @param element 項目名
     * @param elementName 項目名(日本語)
     * @param range 桁数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkRangeTextarea(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.textarea", elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.textarea", msg);
            result = false;
        }
        return result;
    }

    /**
     * <br>[機  能]簡易登録用重複警告チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return boolean チェック結果
     * @throws SQLException
     */
    public boolean warningCheck(RequestModel reqMdl, Connection con)
            throws SQLException {

        if (timeFree__ == GSConstSchedule.TIME_EXIST
                && selectKbn__ != GSConstSchedule.USER_KBN_GROUP) {

            int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
            SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);

            //重複警告 NGスケジュール一覧を取得する。
            List<SchDataModel> rptSchList
            = schBiz.getSchWarningList(sessionUsrSid, selectSid__, selectKbn__,
                    frDate__, frTime__, toDate__, toTime__, GSConstSchedule.SCH_REPEAT_KBN_WARNING);

            if (rptSchList != null && rptSchList.size() > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>selectSid を取得します。
     * @return selectSid
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#selectSid__
     */
    public int getSelectSid() {
        return selectSid__;
    }

    /**
     * <p>selectSid をセットします。
     * @param selectSid selectSid
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#selectSid__
     */
    public void setSelectSid(int selectSid) {
        selectSid__ = selectSid;
    }

    /**
     * <p>selectKbn を取得します。
     * @return selectKbn
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#selectKbn__
     */
    public int getSelectKbn() {
        return selectKbn__;
    }

    /**
     * <p>selectKbn をセットします。
     * @param selectKbn selectKbn
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#selectKbn__
     */
    public void setSelectKbn(int selectKbn) {
        selectKbn__ = selectKbn;
    }

    /**
     * <p>selectDate を取得します。
     * @return selectDate
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#selectDate__
     */
    public String getSelectDate() {
        return selectDate__;
    }

    /**
     * <p>selectDate をセットします。
     * @param selectDate selectDate
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#selectDate__
     */
    public void setSelectDate(String selectDate) {
        selectDate__ = selectDate;
    }

    /**
     * <p>frDate を取得します。
     * @return frDate
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#frDate__
     */
    public String getFrDate() {
        return frDate__;
    }

    /**
     * <p>frDate をセットします。
     * @param frDate frDate
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#frDate__
     */
    public void setFrDate(String frDate) {
        frDate__ = frDate;
    }

    /**
     * <p>frTime を取得します。
     * @return frTime
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#frTime__
     */
    public String getFrTime() {
        return frTime__;
    }

    /**
     * <p>frTime をセットします。
     * @param frTime frTime
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#frTime__
     */
    public void setFrTime(String frTime) {
        frTime__ = frTime;
    }

    /**
     * <p>toDate を取得します。
     * @return toDate
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#toDate__
     */
    public String getToDate() {
        return toDate__;
    }

    /**
     * <p>toDate をセットします。
     * @param toDate toDate
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#toDate__
     */
    public void setToDate(String toDate) {
        toDate__ = toDate;
    }

    /**
     * <p>toTime を取得します。
     * @return toTime
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#toTime__
     */
    public String getToTime() {
        return toTime__;
    }

    /**
     * <p>toTime をセットします。
     * @param toTime toTime
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#toTime__
     */
    public void setToTime(String toTime) {
        toTime__ = toTime;
    }

    /**
     * <p>amFrHour を取得します。
     * @return amFrHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#amFrHour__
     */
    public int getAmFrHour() {
        return amFrHour__;
    }

    /**
     * <p>amFrHour をセットします。
     * @param amFrHour amFrHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#amFrHour__
     */
    public void setAmFrHour(int amFrHour) {
        amFrHour__ = amFrHour;
    }

    /**
     * <p>amFrMin を取得します。
     * @return amFrMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#amFrMin__
     */
    public int getAmFrMin() {
        return amFrMin__;
    }

    /**
     * <p>amFrMin をセットします。
     * @param amFrMin amFrMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#amFrMin__
     */
    public void setAmFrMin(int amFrMin) {
        amFrMin__ = amFrMin;
    }

    /**
     * <p>amToHour を取得します。
     * @return amToHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#amToHour__
     */
    public int getAmToHour() {
        return amToHour__;
    }

    /**
     * <p>amToHour をセットします。
     * @param amToHour amToHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#amToHour__
     */
    public void setAmToHour(int amToHour) {
        amToHour__ = amToHour;
    }

    /**
     * <p>amToMin を取得します。
     * @return amToMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#amToMin__
     */
    public int getAmToMin() {
        return amToMin__;
    }

    /**
     * <p>amToMin をセットします。
     * @param amToMin amToMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#amToMin__
     */
    public void setAmToMin(int amToMin) {
        amToMin__ = amToMin;
    }

    /**
     * <p>pmFrHour を取得します。
     * @return pmFrHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#pmFrHour__
     */
    public int getPmFrHour() {
        return pmFrHour__;
    }

    /**
     * <p>pmFrHour をセットします。
     * @param pmFrHour pmFrHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#pmFrHour__
     */
    public void setPmFrHour(int pmFrHour) {
        pmFrHour__ = pmFrHour;
    }

    /**
     * <p>pmFrMin を取得します。
     * @return pmFrMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#pmFrMin__
     */
    public int getPmFrMin() {
        return pmFrMin__;
    }

    /**
     * <p>pmFrMin をセットします。
     * @param pmFrMin pmFrMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#pmFrMin__
     */
    public void setPmFrMin(int pmFrMin) {
        pmFrMin__ = pmFrMin;
    }

    /**
     * <p>pmToHour を取得します。
     * @return pmToHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#pmToHour__
     */
    public int getPmToHour() {
        return pmToHour__;
    }

    /**
     * <p>pmToHour をセットします。
     * @param pmToHour pmToHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#pmToHour__
     */
    public void setPmToHour(int pmToHour) {
        pmToHour__ = pmToHour;
    }

    /**
     * <p>pmToMin を取得します。
     * @return pmToMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#pmToMin__
     */
    public int getPmToMin() {
        return pmToMin__;
    }

    /**
     * <p>pmToMin をセットします。
     * @param pmToMin pmToMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#pmToMin__
     */
    public void setPmToMin(int pmToMin) {
        pmToMin__ = pmToMin;
    }

    /**
     * <p>allDayFrHour を取得します。
     * @return allDayFrHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#allDayFrHour__
     */
    public int getAllDayFrHour() {
        return allDayFrHour__;
    }

    /**
     * <p>allDayFrHour をセットします。
     * @param allDayFrHour allDayFrHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#allDayFrHour__
     */
    public void setAllDayFrHour(int allDayFrHour) {
        allDayFrHour__ = allDayFrHour;
    }

    /**
     * <p>allDayFrMin を取得します。
     * @return allDayFrMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#allDayFrMin__
     */
    public int getAllDayFrMin() {
        return allDayFrMin__;
    }

    /**
     * <p>allDayFrMin をセットします。
     * @param allDayFrMin allDayFrMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#allDayFrMin__
     */
    public void setAllDayFrMin(int allDayFrMin) {
        allDayFrMin__ = allDayFrMin;
    }

    /**
     * <p>allDayToHour を取得します。
     * @return allDayToHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#allDayToHour__
     */
    public int getAllDayToHour() {
        return allDayToHour__;
    }

    /**
     * <p>allDayToHour をセットします。
     * @param allDayToHour allDayToHour
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#allDayToHour__
     */
    public void setAllDayToHour(int allDayToHour) {
        allDayToHour__ = allDayToHour;
    }

    /**
     * <p>allDayToMin を取得します。
     * @return allDayToMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#allDayToMin__
     */
    public int getAllDayToMin() {
        return allDayToMin__;
    }

    /**
     * <p>allDayToMin をセットします。
     * @param allDayToMin allDayToMin
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#allDayToMin__
     */
    public void setAllDayToMin(int allDayToMin) {
        allDayToMin__ = allDayToMin;
    }

    /**
     * <p>timeFree を取得します。
     * @return timeFree
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#timeFree__
     */
    public int getTimeFree() {
        return timeFree__;
    }

    /**
     * <p>timeFree をセットします。
     * @param timeFree timeFree
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#timeFree__
     */
    public void setTimeFree(int timeFree) {
        timeFree__ = timeFree;
    }

    /**
     * <p>titleColorKbn を取得します。
     * @return titleColorKbn
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#titleColorKbn__
     */
    public int getTitleColorKbn() {
        return titleColorKbn__;
    }

    /**
     * <p>titleColorKbn をセットします。
     * @param titleColorKbn titleColorKbn
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#titleColorKbn__
     */
    public void setTitleColorKbn(int titleColorKbn) {
        titleColorKbn__ = titleColorKbn;
    }

    /**
     * <p>titleColor を取得します。
     * @return titleColor
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#titleColor__
     */
    public int getTitleColor() {
        return titleColor__;
    }

    /**
     * <p>titleColor をセットします。
     * @param titleColor titleColor
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#titleColor__
     */
    public void setTitleColor(int titleColor) {
        titleColor__ = titleColor;
    }

    /**
     * <p>initTitleColor を取得します。
     * @return initTitleColor
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#initTitleColor__
     */
    public int getInitTitleColor() {
        return initTitleColor__;
    }

    /**
     * <p>initTitleColor をセットします。
     * @param initTitleColor initTitleColor
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#initTitleColor__
     */
    public void setInitTitleColor(int initTitleColor) {
        initTitleColor__ = initTitleColor;
    }

    /**
     * <p>title を取得します。
     * @return title
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#title__
     */
    public String getTitle() {
        return title__;
    }

    /**
     * <p>title をセットします。
     * @param title title
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#title__
     */
    public void setTitle(String title) {
        title__ = title;
    }

    /**
     * <p>content を取得します。
     * @return content
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#content__
     */
    public String getContent() {
        return content__;
    }

    /**
     * <p>content をセットします。
     * @param content content
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#content__
     */
    public void setContent(String content) {
        content__ = content;
    }

    /**
     * <p>publicKbn を取得します。
     * @return publicKbn
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#publicKbn__
     */
    public int getPublicKbn() {
        return publicKbn__;
    }

    /**
     * <p>publicKbn をセットします。
     * @param publicKbn publicKbn
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#publicKbn__
     */
    public void setPublicKbn(int publicKbn) {
        publicKbn__ = publicKbn;
    }

    /**
     * <p>initPubKbn を取得します。
     * @return initPubKbn
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#initPubKbn__
     */
    public int getInitPubKbn() {
        return initPubKbn__;
    }

    /**
     * <p>initPubKbn をセットします。
     * @param initPubKbn initPubKbn
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#initPubKbn__
     */
    public void setInitPubKbn(int initPubKbn) {
        initPubKbn__ = initPubKbn;
    }

    /**
     * <p>timeUnits を取得します。
     * @return timeUnits
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#timeUnits__
     */
    public int getTimeUnits() {
        return timeUnits__;
    }

    /**
     * <p>timeUnits をセットします。
     * @param timeUnits timeUnits
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#timeUnits__
     */
    public void setTimeUnits(int timeUnits) {
        timeUnits__ = timeUnits;
    }
    /**
     * <p>initFrTime を取得します。
     * @return initFrTime
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#initFrTime__
     */
    public String getInitFrTime() {
        return initFrTime__;
    }

    /**
     * <p>initFrTime をセットします。
     * @param initFrTime initFrTime
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#initFrTime__
     */
    public void setInitFrTime(String initFrTime) {
        initFrTime__ = initFrTime;
    }

    /**
     * <p>initToTime を取得します。
     * @return initToTime
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#initToTime__
     */
    public String getInitToTime() {
        return initToTime__;
    }

    /**
     * <p>initToTime をセットします。
     * @param initToTime initToTime
     * @see jp.groupsession.v2.sch.model.SchEasyRegisterModel#initToTime__
     */
    public void setInitToTime(String initToTime) {
        initToTime__ = initToTime;
    }



}
