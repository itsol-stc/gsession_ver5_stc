package jp.groupsession.v2.rsv.rsv220;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;


import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.rsv040.Rsv040Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv220Form extends Rsv040Form {

    /** 施設予約単位分設定 */
    private String rsv220HourDiv__ = null;

    /** 午前開始時 */
    private String rsv220AmFrHour__ = null;
    /** 午前開始分 */
    private String rsv220AmFrMin__ = null;
    /** 午前終了時 */
    private String rsv220AmToHour__ = null;
    /** 午前終了分 */
    private String rsv220AmToMin__ = null;

    /** 午後開始時 */
    private String rsv220PmFrHour__ = null;
    /** 午後開始分 */
    private String rsv220PmFrMin__ = null;
    /** 午後終了時 */
    private String rsv220PmToHour__ = null;
    /** 午後終了分 */
    private String rsv220PmToMin__ = null;

    /** 終日開始時 */
    private String rsv220AllDayFrHour__ = null;
    /** 終日開始分 */
    private String rsv220AllDayFrMin__ = null;
    /** 終日終了時 */
    private String rsv220AllDayToHour__ = null;
    /** 終日終了分 */
    private String rsv220AllDayToMin__ = null;
    
    /** 午前開始時間 */
    private String rsv220AmFrTime__ = null;
    /** 午前終了時間 */
    private String rsv220AmToTime__ = null;
    /** 午後開始時間 */
    private String rsv220PmFrTime__ = null;
    /** 午後終了時間 */
    private String rsv220PmToTime__ = null;
    /** 午後開始時間 */
    private String rsv220AllDayFrTime__ = null;
    /** 午後終了時間 */
    private String rsv220AllDayToTime__ = null;

    /** 表示用午前マスタ */
    private String rsv220AmTime__ = null;
    /** 表示用午後マスタ */
    private String rsv220PmTime__ = null;
    /** 表示用終日マスタ */
    private String rsv220AllTime__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Rsv220Form() {
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl) throws SQLException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        
        int errorSize = errors.size();
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        String start = gsMsg.getMessage("cmn.start");
        String end = gsMsg.getMessage("cmn.end");
        errors.add(dateBiz.setHmParam(this, "rsv220AmFrTime",
                "rsv220AmFrHour", "rsv220AmFrMin", gsMsg.getMessage("cmn.am") + start));
        errors.add(dateBiz.setHmParam(this, "rsv220AmToTime",
                "rsv220AmToHour", "rsv220AmToMin", gsMsg.getMessage("cmn.am") + end));
        
        errors.add(dateBiz.setHmParam(this, "rsv220PmFrTime",
                "rsv220PmFrHour", "rsv220PmFrMin", gsMsg.getMessage("cmn.pm") + start));
        errors.add(dateBiz.setHmParam(this, "rsv220PmToTime",
                "rsv220PmToHour", "rsv220PmToMin", gsMsg.getMessage("cmn.pm") + end));
        
        errors.add(dateBiz.setHmParam(this, "rsv220AllDayFrTime",
                "rsv220AllDayFrHour", "rsv220AllDayFrMin", gsMsg.getMessage("cmn.allday") + start));
        errors.add(dateBiz.setHmParam(this, "rsv220AllDayToTime",
                "rsv220AllDayToHour", "rsv220AllDayToMin", gsMsg.getMessage("cmn.allday") + end));
        
        if (errorSize != errors.size()) {
            return errors;
        }
        
        if (Integer.parseInt(rsv220AmFrMin__) % Integer.parseInt(rsv220HourDiv__) != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", gsMsg.getMessage("cmn.am") + start,
                    gsMsg.getMessageVal0("cmn.minute.periods", rsv220HourDiv__));
            errors.add("error.input.comp.text", msg);
        }
        if (Integer.parseInt(rsv220AmToMin__) % Integer.parseInt(rsv220HourDiv__) != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", gsMsg.getMessage("cmn.am") + end,
                    gsMsg.getMessageVal0("cmn.minute.periods", rsv220HourDiv__));
            errors.add("error.input.comp.text", msg);
        }
        if (Integer.parseInt(rsv220PmFrMin__) % Integer.parseInt(rsv220HourDiv__) != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", gsMsg.getMessage("cmn.pm") + start,
                    gsMsg.getMessageVal0("cmn.minute.periods", rsv220HourDiv__));
            errors.add("error.input.comp.text", msg);
        }
        if (Integer.parseInt(rsv220PmToMin__) % Integer.parseInt(rsv220HourDiv__) != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", gsMsg.getMessage("cmn.pm") + end,
                    gsMsg.getMessageVal0("cmn.minute.periods", rsv220HourDiv__));
            errors.add("error.input.comp.text", msg);
        }
        if (Integer.parseInt(rsv220AllDayFrMin__) % Integer.parseInt(rsv220HourDiv__) != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", gsMsg.getMessage("cmn.allday") + start,
                    gsMsg.getMessageVal0("cmn.minute.periods", rsv220HourDiv__));
            errors.add("error.input.comp.text", msg);
        }
        if (Integer.parseInt(rsv220AllDayToMin__) % Integer.parseInt(rsv220HourDiv__) != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", gsMsg.getMessage("cmn.allday") + end,
                    gsMsg.getMessageVal0("cmn.minute.periods", rsv220HourDiv__));
            errors.add("error.input.comp.text", msg);
        }
        
        //午前 from～to大小チェック
        __checkInputTime(errors, rsv220AmFrHour__, rsv220AmToHour__,
                rsv220AmFrMin__, rsv220AmToMin__, gsMsg.getMessage("cmn.am"));

        //午後 from～to大小チェック
        __checkInputTime(errors, rsv220PmFrHour__, rsv220PmToHour__,
                rsv220PmFrMin__, rsv220PmToMin__, gsMsg.getMessage("cmn.pm"));

        //終日 from～to大小チェック
        __checkInputTime(errors, rsv220AllDayFrHour__, rsv220AllDayToHour__,
                rsv220AllDayFrMin__, rsv220AllDayToMin__, gsMsg.getMessage("cmn.allday"));

        return errors;
    }

    /**
     * <br>[機  能] 開始時間、終了時間の値が正常かチェックします
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param strFrTimeH 開始時
     * @param strToTimeH 終了時
     * @param strFrTimeM 開始分
     * @param strToTimeM 終了分
     * @param timeKbn 時間区分
     * @return errors
     */
    private ActionErrors __checkInputTime(
            ActionErrors errors,
            String strFrTimeH,
            String strToTimeH,
            String strFrTimeM,
            String strToTimeM,
            String timeKbn) {

        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        int frTimeH = Integer.valueOf(strFrTimeH);
        int toTimeH = Integer.valueOf(strToTimeH);

        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(timeKbn);
        errorMessage.append(":");

        //開始時間が0~24の間でない時はエラー
        if (frTimeH < 0 || frTimeH > 23) {
            //timeKbn:開始時間は0~23で入力してください。
            errorMessage.append(gsMsg.getMessage("cmn.start"));
            errorMessage.append(gsMsg.getMessage("cmn.time"));
            String errorPlace = errorMessage.toString();
            String errorCause = "0 ~ 23";

            msg = new ActionMessage("error.input.comp.text",
                    errorPlace, errorCause);
            errors.add("error.input.comp.text", msg);
            return errors;
        }

        //終了時間が0~24の間でない時はエラー
        if (toTimeH < 0 || toTimeH > 23) {
            //timeKbn:終了時間は0~23で入力してください。
            errorMessage.append(gsMsg.getMessage("cmn.end"));
            errorMessage.append(gsMsg.getMessage("cmn.time"));
            String errorPlace = errorMessage.toString();
            String errorCause = "0 ~ 23";
            msg = new ActionMessage("error.input.comp.text",
                    errorPlace, errorCause);

            errors.add("error.input.comp.text", msg);
            return errors;
        }

        int frTimeM = Integer.valueOf(strFrTimeM);
        int toTimeM = Integer.valueOf(strToTimeM);

        //開始分の範囲が0~59の外の場合エラー
        if (frTimeM < 0 || frTimeM > 59) {
            //timeKbn:開始時間は0~23で入力してください。
            errorMessage.append(gsMsg.getMessage("cmn.start"));
            errorMessage.append(gsMsg.getMessage("cmn.minute"));
            String errorPlace = errorMessage.toString();
            String errorCause = "0 ~ 59";

            msg = new ActionMessage("error.input.comp.text",
                    errorPlace, errorCause);
            errors.add("error.input.comp.text", msg);
            return errors;
        }

        //終了分の範囲が0~59の外の場合エラー
        if (toTimeM < 0 || toTimeM > 59) {
            //timeKbn:終了時間は0~23で入力してください。
            errorMessage.append(gsMsg.getMessage("cmn.end"));
            errorMessage.append(gsMsg.getMessage("cmn.minute"));
            String errorPlace = errorMessage.toString();
            String errorCause = "0 ~ 59";
            msg = new ActionMessage("error.input.comp.text",
                    errorPlace, errorCause);

            errors.add("error.input.comp.text", msg);
            return errors;
        }

        //時間の整合性チェック
        __compareTimeHour(frTimeH, toTimeH, frTimeM,
                toTimeM, timeKbn, errors);

        return errors;
    }

    /**
     * 
     * <br>[機  能] 開始時間と終了時間の整合性をチェックします。
     * <br>[解  説]
     * <br>[備  考]
     * @param frTimeH 開始時間
     * @param toTimeH 終了時間
     * @param frTimeM 開始分
     * @param toTimeM 終了分
     * @param timeKbn 午前、午後、終日区分
     * @param errors  アクションエラー
     * @return アクションエラー
     */
    private ActionErrors __compareTimeHour(
            int frTimeH, int toTimeH,
            int frTimeM, int toTimeM,
            String timeKbn, ActionErrors errors) {
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        //開始時間が終了時間より遅い場合エラー
        if (frTimeH > toTimeH) {
            //開始・終了
            String textStartEnd = gsMsg.getMessage("cmn.start.end");
            //開始<終了
            String textStartLessEnd = gsMsg.getMessage("cmn.start.lessthan.end");
            msg = new ActionMessage("error.input.comp.text",
                    timeKbn + "：" + textStartEnd, textStartLessEnd);
            errors.add("" + "error.input.comp.text", msg);
            return errors;
        }

        //もし時が同じならば、分で大小比較を行う。
        if (frTimeH == toTimeH) {
            __compareTimeMin(errors, frTimeM, toTimeM, timeKbn);
        }

        return errors;
    }
    
    /**
     * 
     * <br>[機  能]開始分と終了分の大小比較を行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param frTimeM 開始分
     * @param toTimeM 終了分
     * @param timeKbn 午前午後終日区分
     * @return アクションエラー
     */
    private ActionErrors __compareTimeMin(
            ActionErrors errors,
            int frTimeM,
            int toTimeM,
            String timeKbn) {
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;

        //開始分が終了分より遅いとエラー
        if (frTimeM >= toTimeM) {
            //開始・終了
            String textStartEnd = gsMsg.getMessage("cmn.start.end");
            //開始<終了
            String textStartLessEnd = gsMsg.getMessage("cmn.start.lessthan.end");
            msg = new ActionMessage("error.input.comp.text",
                    timeKbn + "：" + textStartEnd, textStartLessEnd);
            errors.add("" + "error.input.comp.text", msg);
            return errors;
        }

        return errors;
    }
    /**
     * <p>rsv220HourDiv を取得します。
     * @return rsv220HourDiv
     */
    public String getRsv220HourDiv() {
        return rsv220HourDiv__;
    }

    /**
     * <p>rsv220HourDiv をセットします。
     * @param rsv220HourDiv rsv220HourDiv
     */
    public void setRsv220HourDiv(String rsv220HourDiv) {
        rsv220HourDiv__ = rsv220HourDiv;
    }

    /**
     * <p>rsv220AmFrHour を取得します。
     * @return rsv220AmFrHour
     */
    public String getRsv220AmFrHour() {
        return rsv220AmFrHour__;
    }

    /**
     * <p>rsv220AmFrHour をセットします。
     * @param rsv220AmFrHour rsv220AmFrHour
     */
    public void setRsv220AmFrHour(String rsv220AmFrHour) {
        rsv220AmFrHour__ = rsv220AmFrHour;
    }

    /**
     * <p>rsv220AmFrMin を取得します。
     * @return rsv220AmFrMin
     */
    public String getRsv220AmFrMin() {
        return rsv220AmFrMin__;
    }

    /**
     * <p>rsv220AmFrMin をセットします。
     * @param rsv220AmFrMin rsv220AmFrMin
     */
    public void setRsv220AmFrMin(String rsv220AmFrMin) {
        rsv220AmFrMin__ = rsv220AmFrMin;
    }

    /**
     * <p>rsv220AmToHour を取得します。
     * @return rsv220AmToHour
     */
    public String getRsv220AmToHour() {
        return rsv220AmToHour__;
    }

    /**
     * <p>rsv220AmToHour をセットします。
     * @param rsv220AmToHour rsv220AmToHour
     */
    public void setRsv220AmToHour(String rsv220AmToHour) {
        rsv220AmToHour__ = rsv220AmToHour;
    }

    /**
     * <p>rsv220AmToMin を取得します。
     * @return rsv220AmToMin
     */
    public String getRsv220AmToMin() {
        return rsv220AmToMin__;
    }

    /**
     * <p>rsv220AmToMin をセットします。
     * @param rsv220AmToMin rsv220AmToMin
     */
    public void setRsv220AmToMin(String rsv220AmToMin) {
        rsv220AmToMin__ = rsv220AmToMin;
    }

    /**
     * <p>rsv220PmFrHour を取得します。
     * @return rsv220PmFrHour
     */
    public String getRsv220PmFrHour() {
        return rsv220PmFrHour__;
    }

    /**
     * <p>rsv220PmFrHour をセットします。
     * @param rsv220PmFrHour rsv220PmFrHour
     */
    public void setRsv220PmFrHour(String rsv220PmFrHour) {
        rsv220PmFrHour__ = rsv220PmFrHour;
    }

    /**
     * <p>rsv220PmFrMin を取得します。
     * @return rsv220PmFrMin
     */
    public String getRsv220PmFrMin() {
        return rsv220PmFrMin__;
    }

    /**
     * <p>rsv220PmFrMin をセットします。
     * @param rsv220PmFrMin rsv220PmFrMin
     */
    public void setRsv220PmFrMin(String rsv220PmFrMin) {
        rsv220PmFrMin__ = rsv220PmFrMin;
    }

    /**
     * <p>rsv220PmToHour を取得します。
     * @return rsv220PmToHour
     */
    public String getRsv220PmToHour() {
        return rsv220PmToHour__;
    }

    /**
     * <p>rsv220PmToHour をセットします。
     * @param rsv220PmToHour rsv220PmToHour
     */
    public void setRsv220PmToHour(String rsv220PmToHour) {
        rsv220PmToHour__ = rsv220PmToHour;
    }

    /**
     * <p>rsv220PmToMin を取得します。
     * @return rsv220PmToMin
     */
    public String getRsv220PmToMin() {
        return rsv220PmToMin__;
    }

    /**
     * <p>rsv220PmToMin をセットします。
     * @param rsv220PmToMin rsv220PmToMin
     */
    public void setRsv220PmToMin(String rsv220PmToMin) {
        rsv220PmToMin__ = rsv220PmToMin;
    }

    /**
     * <p>rsv220AllDayFrHour を取得します。
     * @return rsv220AllDayFrHour
     */
    public String getRsv220AllDayFrHour() {
        return rsv220AllDayFrHour__;
    }

    /**
     * <p>rsv220AllDayFrHour をセットします。
     * @param rsv220AllDayFrHour rsv220AllDayFrHour
     */
    public void setRsv220AllDayFrHour(String rsv220AllDayFrHour) {
        rsv220AllDayFrHour__ = rsv220AllDayFrHour;
    }

    /**
     * <p>rsv220AllDayFrMin を取得します。
     * @return rsv220AllDayFrMin
     */
    public String getRsv220AllDayFrMin() {
        return rsv220AllDayFrMin__;
    }

    /**
     * <p>rsv220AllDayFrMin をセットします。
     * @param rsv220AllDayFrMin rsv220AllDayFrMin
     */
    public void setRsv220AllDayFrMin(String rsv220AllDayFrMin) {
        rsv220AllDayFrMin__ = rsv220AllDayFrMin;
    }

    /**
     * <p>rsv220AllDayToHour を取得します。
     * @return rsv220AllDayToHour
     */
    public String getRsv220AllDayToHour() {
        return rsv220AllDayToHour__;
    }

    /**
     * <p>rsv220AllDayToHour をセットします。
     * @param rsv220AllDayToHour rsv220AllDayToHour
     */
    public void setRsv220AllDayToHour(String rsv220AllDayToHour) {
        rsv220AllDayToHour__ = rsv220AllDayToHour;
    }

    /**
     * <p>rsv220AllDayToMin を取得します。
     * @return rsv220AllDayToMin
     */
    public String getRsv220AllDayToMin() {
        return rsv220AllDayToMin__;
    }

    /**
     * <p>rsv220AllDayToMin をセットします。
     * @param rsv220AllDayToMin rsv220AllDayToMin
     */
    public void setRsv220AllDayToMin(String rsv220AllDayToMin) {
        rsv220AllDayToMin__ = rsv220AllDayToMin;
    }

    /**
     * <p>rsv220AmFrTime を取得します。
     * @return rsv220AmFrTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220AmFrTime__
     */
    public String getRsv220AmFrTime() {
        return rsv220AmFrTime__;
    }

    /**
     * <p>rsv220AmFrTime をセットします。
     * @param rsv220AmFrTime rsv220AmFrTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220AmFrTime__
     */
    public void setRsv220AmFrTime(String rsv220AmFrTime) {
        rsv220AmFrTime__ = rsv220AmFrTime;
    }

    /**
     * <p>rsv220AmToTime を取得します。
     * @return rsv220AmToTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220AmToTime__
     */
    public String getRsv220AmToTime() {
        return rsv220AmToTime__;
    }

    /**
     * <p>rsv220AmToTime をセットします。
     * @param rsv220AmToTime rsv220AmToTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220AmToTime__
     */
    public void setRsv220AmToTime(String rsv220AmToTime) {
        rsv220AmToTime__ = rsv220AmToTime;
    }

    /**
     * <p>rsv220PmFrTime を取得します。
     * @return rsv220PmFrTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220PmFrTime__
     */
    public String getRsv220PmFrTime() {
        return rsv220PmFrTime__;
    }

    /**
     * <p>rsv220PmFrTime をセットします。
     * @param rsv220PmFrTime rsv220PmFrTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220PmFrTime__
     */
    public void setRsv220PmFrTime(String rsv220PmFrTime) {
        rsv220PmFrTime__ = rsv220PmFrTime;
    }

    /**
     * <p>rsv220PmToTime を取得します。
     * @return rsv220PmToTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220PmToTime__
     */
    public String getRsv220PmToTime() {
        return rsv220PmToTime__;
    }

    /**
     * <p>rsv220PmToTime をセットします。
     * @param rsv220PmToTime rsv220PmToTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220PmToTime__
     */
    public void setRsv220PmToTime(String rsv220PmToTime) {
        rsv220PmToTime__ = rsv220PmToTime;
    }

    /**
     * <p>rsv220AllDayFrTime を取得します。
     * @return rsv220AllDayFrTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220AllDayFrTime__
     */
    public String getRsv220AllDayFrTime() {
        return rsv220AllDayFrTime__;
    }

    /**
     * <p>rsv220AllDayFrTime をセットします。
     * @param rsv220AllDayFrTime rsv220AllDayFrTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220AllDayFrTime__
     */
    public void setRsv220AllDayFrTime(String rsv220AllDayFrTime) {
        rsv220AllDayFrTime__ = rsv220AllDayFrTime;
    }

    /**
     * <p>rsv220AllDayToTime を取得します。
     * @return rsv220AllDayToTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220AllDayToTime__
     */
    public String getRsv220AllDayToTime() {
        return rsv220AllDayToTime__;
    }

    /**
     * <p>rsv220AllDayToTime をセットします。
     * @param rsv220AllDayToTime rsv220AllDayToTime
     * @see jp.groupsession.v2.rsv.rsv220.Rsv220Form#rsv220AllDayToTime__
     */
    public void setRsv220AllDayToTime(String rsv220AllDayToTime) {
        rsv220AllDayToTime__ = rsv220AllDayToTime;
    }

    /**
     * <p>rsv220AmTime を取得します。
     * @return rsv220AmTime
     */
    public String getRsv220AmTime() {
        return rsv220AmTime__;
    }

    /**
     * <p>rsv220AmTime をセットします。
     * @param rsv220AmTime rsv220AmTime
     */
    public void setRsv220AmTime(String rsv220AmTime) {
        rsv220AmTime__ = rsv220AmTime;
    }

    /**
     * <p>rsv220PmTime を取得します。
     * @return rsv220PmTime
     */
    public String getRsv220PmTime() {
        return rsv220PmTime__;
    }

    /**
     * <p>rsv220PmTime をセットします。
     * @param rsv220PmTime rsv220PmTime
     */
    public void setRsv220PmTime(String rsv220PmTime) {
        rsv220PmTime__ = rsv220PmTime;
    }

    /**
     * <p>rsv220AllTime を取得します。
     * @return rsv220AllTime
     */
    public String getRsv220AllTime() {
        return rsv220AllTime__;
    }

    /**
     * <p>rsv220AllTime をセットします。
     * @param rsv220AllTime rsv220AllTime
     */
    public void setRsv220AllTime(String rsv220AllTime) {
        rsv220AllTime__ = rsv220AllTime;
    }
}