package jp.groupsession.v2.tcd.tcd150;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdHolidayInfDao;
import jp.groupsession.v2.tcd.dao.TcdTimezoneInfoDao;
import jp.groupsession.v2.tcd.dao.TcdTimezonePriDao;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.tcd.model.TcdTimezonePriModel;

/**
 * <br>[機  能] タイムカードインポート画面の取込みファイルをチェックするクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Tcd150ImportCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd150ImportCheck.class);
    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** コネクション */
    private Connection con__ = null;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /** インポート対象ユーザ*/
    private int usrSid__ = 0;

    /**
     * <p>con__ を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con__ をセットします。
     * @param con con__
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>count__ を取得します。
     * @return count
     */
    public int getCount() {
        return count__;
    }
    /**
     * <p>count__ をセットします。
     * @param count count__
     */
    public void setCount(int count) {
        count__ = count;
    }
    /**
     * <p>errorFlg__ を取得します。
     * @return errorFlg
     */
    public boolean isErrorFlg() {
        return errorFlg__;
    }
    /**
     * <p>errorFlg__ をセットします。
     * @param errorFlg errorFlg__
     */
    public void setErrorFlg(boolean errorFlg) {
        errorFlg__ = errorFlg;
    }
    /**
     * <p>errors__ を取得します。
     * @return errors
     */
    public ActionErrors getErrors() {
        return errors__;
    }
    /**
     * <p>errors__ をセットします。
     * @param errors errors__
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param error アクションエラー
     * @param con コネクション
     * @param reqMdl RequestModel
     * @param usrSid ユーザSID
     */
    public Tcd150ImportCheck(
            ActionErrors error,
            Connection con,
            RequestModel reqMdl,
            int usrSid) {
        setErrors(error);
        setCon(con);
        reqMdl__ = reqMdl;
        usrSid__ = usrSid;
    }

    /**
     * <br>[機　能] CSVファイルのチェックを行なう
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param csvFile 入力ファイル名
     * @return ture:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
     protected boolean _isCsvDataOk(String csvFile) throws Exception {

         boolean ret = false;
         File file = new File(csvFile);
         if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT)) {
             if (errors__ != null) {
                 GsMessage gsMsg = new GsMessage(reqMdl__);
                 String eprefix = "inputFile.";
                 String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
                 ActionMessage msg =
                     new ActionMessage("error.over.row.csvdata",
                             textCaptureFile,
                             String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT));
                 StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
             }
             ret = true;
             return ret;
         }

         //ファイル読込み
         readFile(file, Encoding.WINDOWS_31J);

         ret = isErrorFlg();

         //有効データ無し
         if (getCount() == 0) {
             ret = true;
         }
         return ret;
     }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {

        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //CSV項目数
        String textCsvNumberItems = gsMsg.getMessage("cmn.csv.number.items");
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        //エラーメッセージタイトル
        String title = null;

        //開始時刻
        String startTime = null;

        if (num > 1) {

            try {

                int j = 0;
                String eprefix = "inputFile." + num + ".";
                int ecnt = errors__.size();

                if (stringTokenizer.length() != GSConstTimecard.IMP_VALUE_SIZE) {
                    //行目
                    String textLine = gsMsg.getMessage("cmn.line",
                            new String[] {String.valueOf(num)});
                    ActionMessage msg =
                        new ActionMessage(
                                "error.input.format.file",
                                textCaptureFile,
                                textCsvNumberItems + "(" + textLine + ")");
                    StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
                } else {
                    UDate date = new UDate();
                    String absenceKbn = "";
                    TcdTimezonePriDao ttpDao = new TcdTimezonePriDao(con__);
                    ArrayList<TcdTimezonePriModel> timezoneList = ttpDao.selectCanUse(usrSid__);
                    ArrayList<String> ttiRyakuList = new ArrayList<String>();
                    if (timezoneList.size() == 0) {
                        TimecardBiz tcdBiz = new TimecardBiz(reqMdl__);
                        TcdAdmConfModel admConfModel = 
                                tcdBiz.getTcdAdmConfModel(reqMdl__.getSmodel().getUsrsid(), con__);
                        TcdTimezoneInfoDao ttiDao = new TcdTimezoneInfoDao(con__);
                        TcdTimezoneInfoModel ttiMdl = 
                                ttiDao.select(admConfModel.getTacDefTimezone());
                        ttiRyakuList.add(ttiMdl.getTtiRyaku());
                    } else {
                        for (TcdTimezonePriModel ttpMdl : timezoneList) {
                            ttiRyakuList.add(ttpMdl.getTtiRyaku());  
                        }
                    }
                    while (stringTokenizer.hasMoreTokens()) {
                        j++;
                        buff = stringTokenizer.nextToken();
                        //日付
                        if (j == 3) {
                            __isOkDate(errors__, buff, num, date);
                        }
                        //時間帯
                        if (j == 4) {
                            __isOkTimezone(errors__, ttiRyakuList, buff, num);
                        }

                        //打刻始業時刻
                        if (j == 5) {
                            //打刻終業時刻との比較があるため、後に入力チェックを行う
                            startTime = buff;
                        }

                        //打刻終業時刻
                        if (j == 6) {
                            //打刻始業時刻チェック
                            title = gsMsg.getMessage("tcd.133")
                                    + gsMsg.getMessage("tcd.tcdmain.03");
                            __isOkTime(errors__, startTime, buff, title,
                                    GSConstTimecard.FROM_DATE_KBN, num, date, false);

                            //打刻終業時刻チェック
                            title = gsMsg.getMessage("tcd.133")
                                    + gsMsg.getMessage("tcd.tcdmain.02");
                            __isOkTime(errors__, buff, startTime, title,
                                    GSConstTimecard.TO_DATE_KBN, num, date, false);

                            //打刻始業時刻 < 打刻終業時刻チェック
                            __isOkStartEnd(errors__, startTime, buff, true, num, date);
                        }

                        //始業時刻
                        if (j == 7) {
                            //終業時刻との比較があるため、後に入力チェックを行う
                            startTime = buff;
                        }

                        //終業時刻
                        if (j == 8) {
                            //始業時刻チェック
                            title = gsMsg.getMessage("tcd.tcdmain.03");
                            __isOkTime(errors__, startTime, buff, title,
                                    GSConstTimecard.FROM_DATE_KBN, num, date, true);
                            //終業時刻チェック
                            title = gsMsg.getMessage("tcd.tcdmain.02");
                            __isOkTime(errors__, buff, startTime, title,
                                    GSConstTimecard.TO_DATE_KBN, num, date, false);

                            //始業時刻 < 終業時刻チェック
                            __isOkStartEnd(errors__, startTime, buff, false, num, date);
                        }

                        //備考
                        if (j == 9) {
                            __isOkBiko(errors__, buff, num);
                        }

                        //遅刻
                        if (j == 10) {
                            __isOkTikoku(errors__, buff, num);
                        }
                        //早退
                        if (j == 11) {
                            //備考
                            __isOkSoutai(errors__, buff, num);
                        }
                        //休日区分
                        if (j == 12) {
                            absenceKbn = buff;
                            __isAbsenceReason(errors__, buff, num);
                        }
                        //休日内容
                        if (j == 13) {
                            __isOkAbsenceNaiyo(errors__, buff, num, absenceKbn);
                        }
                        //休日日数
                        if (j == 14) {
                            __isOkAbsenceDays(errors__, buff, num, absenceKbn);
                        }

                    }
                }

                //エラー有り
                if (ecnt < errors__.size()) {
                    //エラー存在フラグON
                    setErrorFlg(true);
                } else {
                    //明細データ以降
                    if (num > 1) {
                        //有効データ件数カウントアップ
                        int cnt = getCount();
                        cnt += 1;
                        setCount(cnt);
                    }
                }

            } catch (Exception e) {
                log__.error("CSVファイル読込み時例外");
                throw e;
            }

        }
    }

    /**
     * <br>[機  能]時間帯の入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param timezoneRyakuList 略称リスト
     * @param ttiRyaku
     * @param num 行番号
     * @return ActionErrors
     */
    private ActionErrors __isOkTimezone(ActionErrors errors,
            ArrayList<String> timezoneRyakuList,
            String ttiRyaku, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "timezone.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2",
                new String[] {String.valueOf(num)});

        //時間帯
        if (!StringUtil.isNullZeroString(ttiRyaku)) {
            boolean chk = false;
            for (String  ryaku : timezoneRyakuList) {
                if (ryaku.equals(ttiRyaku)) {
                    chk = true;
                    break;
                }
            }
            if (!chk) {
                String timezone = gsMsg.getMessage("tcd.tcd070.01");
                msg = new ActionMessage("tcd150.not.timezone", textLine);
                StrutsUtil.addMessage(errors, msg, eprefix + timezone);
            }
        }
        return errors;
    }

    /**
     * <p>年月日の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param ymd 年月日
     * @param num 行数
     * @param udate チェックした日付
     * @return ActionErrors
     */
    private ActionErrors __isOkDate(ActionErrors errors,
            String ymd, long num, UDate udate) {

        boolean errorFlg = false;
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        //タイトル
        String title = textLine + gsMsg.getMessage("cmn.date2");

        if (StringUtil.isNullZeroString(ymd)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", title);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
            errorFlg = true;
        } else {

            ArrayList<String> list = StringUtil.split("/", ymd);

            String sptYear = "";
            String sptMonth = "";
            String sptDay = "";
            boolean ymdFomatHnt = false;

            if (list.size() == 3) {
                sptYear = list.get(0);
                sptMonth = list.get(1);
                sptDay = list.get(2);
                try {
                    ymd = StringUtil.getStrYyyyMmDd(sptYear, sptMonth, sptDay);
                    ymdFomatHnt = true;
                } catch (NumberFormatException ne) {
                }
            }

            //行目の
            String textYyyyMmDd = gsMsg.getMessage("cmn.format.date2");
            //yyyy/mm/dd形式入力
            if (!ymdFomatHnt) {
                msg = new ActionMessage("error.input.comp.text",
                        title,
                        textYyyyMmDd);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.comp.text");
                errorFlg = true;

            } else {

                int iBYear = 0;
                int iBMonth = 0;
                int iBDay = 0;
                try {
                    String year = ymd.substring(0, 4);
                    String month = ymd.substring(4, 6);
                    String day = ymd.substring(6, 8);
                    iBYear = Integer.parseInt(year);
                    iBMonth = Integer.parseInt(month);
                    iBDay = Integer.parseInt(day);
                } catch (NumberFormatException e) {
                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                    errorFlg = true;
                }

                //論理チェック
                UDate date = new UDate();
                date.setDate(iBYear, iBMonth, iBDay);
                if (date.getYear() != iBYear
                || date.getMonth() != iBMonth
                || date.getIntDay() != iBDay) {

                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                    errorFlg = true;
                }
            }
        }
        if (!errorFlg) {
            udate.setDate(ymd);
        } else {
            udate = null;
        }
        return errors;
    }

    /**
     * <p>時刻の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param time 時刻(HH:MM)
     * @param time2 比較用時刻(HH:MM)
     * @param title タイトル
     * @param frto 開始(0)・終了(1)
     * @param num 行数
     * @param udate チェック済み日付
     * @param chkFlg チェックフラグ
     * @return ActionErrors
     */
    private ActionErrors __isOkTime(ActionErrors errors, String time, String time2,
            String title, int frto, long num, UDate udate, boolean chkFlg) {

        boolean errorFlg = false;
        ActionMessage msg = null;
        String eprefix = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //eprefixの調整
        if (title.contains(gsMsg.getMessage("tcd.133"))) {
            eprefix = String.valueOf(num) + String.valueOf(frto) + "dakoku." + "time.";
        } else {
            eprefix = String.valueOf(num) + String.valueOf(frto) + "time.";
        }
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        title = textLine + title;

        //開始，終了のどちらかが入力されていて、もう片方が入力されていない場合
        if (StringUtil.isNullZeroString(time) && !StringUtil.isNullZeroString(time2) && chkFlg) {
            msg = new ActionMessage("error.input.required.text", title);
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.required.text");
            errorFlg = true;
        }

        int iBHour = 0;
        int iBMin = 0;

        if (!StringUtil.isNullZeroString(time)) {

            if (!ValidateUtil.isTimeFormat(time)) {
                //半角数字(hh:mm形式)
                msg = new ActionMessage("error.input.number.hankaku", title);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.comp.text");
                errorFlg = true;
            } else {

                try {
                    ArrayList<String> list = StringUtil.split(":", time);
                    String hour = (String) list.get(0);
                    String min = (String) list.get(1);

                    iBHour = Integer.parseInt(hour);
                    iBMin = Integer.parseInt(min);

                } catch (Exception e) {
                    msg = new ActionMessage("error.input.notvalidate.data", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notvalidate.data");
                    errorFlg = true;
                }

                //論理チェック
                if (iBHour < 0 || GSConstTimecard.MAX_TIMECARD_HOUR < iBHour) {
                    msg = new ActionMessage("error.input.notvalidate.data", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notvalidate.data");
                    errorFlg = true;
                }
                if (iBMin < 0 || 59 < iBMin) {
                    msg = new ActionMessage("error.input.notvalidate.data", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notvalidate.data");
                    errorFlg = true;
                }
            }
            if (!errorFlg && udate != null) {
                udate.setZeroHhMmSs();
                udate.setHour(iBHour);
                udate.setMinute(iBMin);
            } else {
                udate = null;
            }
        } else if (udate != null) {
            udate.setZeroHhMmSs();
            udate.setHour(iBHour);
            udate.setMinute(iBMin);
        }
        return errors;
    }

    /**
     * <p>開始時刻と終業時刻大小関係をチェックする(CSV用)
     * @param errors ActionErrors
     * @param start 始業時刻(HH:MM)
     * @param end 終業時刻(HH:MM)
     * @param dakokuFlg 打刻時刻か時刻かを示すフラグ
     * @param num 行数
     * @param udate チェック済み日付
     * @return ActionErrors
     */
    private ActionErrors __isOkStartEnd(ActionErrors errors,
            String start, String end, boolean dakokuFlg, long num, UDate udate) {

        ActionMessage msg = null;
        String eprefix = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        String title = null;
        String startTitle = null;
        String endTitle = null;
        if (dakokuFlg) {
            startTitle = gsMsg.getMessage("tcd.133") + gsMsg.getMessage("tcd.tcdmain.03");
            endTitle = gsMsg.getMessage("tcd.133") + gsMsg.getMessage("tcd.tcdmain.02");
            eprefix = String.valueOf(num) + "dakoku.compare.";
        } else {
            startTitle = gsMsg.getMessage("tcd.tcdmain.03");
            endTitle = gsMsg.getMessage("tcd.tcdmain.02");
            eprefix = String.valueOf(num) + "compare.";
        }
        if (!StringUtil.isNullZeroString(start) && !StringUtil.isNullZeroString(end)
                && ValidateUtil.isTimeFormat(start) && ValidateUtil.isTimeFormat(end)) {
            int startHour;
            int endHour;
            int startMin;
            int endMin;

            try {
                ArrayList<String> list = StringUtil.split(":", start);
                String hour = (String) list.get(0);
                String min = (String) list.get(1);
                
                startHour = Integer.parseInt(hour);
                startMin = Integer.parseInt(min);

                list = StringUtil.split(":", end);
                hour = (String) list.get(0);
                min = (String) list.get(1);

                endHour = Integer.parseInt(hour);
                endMin = Integer.parseInt(min);
                
                if (startHour > endHour || (startHour == endHour && startMin > endMin)) {
                    String text = startTitle + " < " + endTitle;
                    title = textLine + startTitle + "・" + endTitle;
                    msg = new ActionMessage("error.input.comp.text", title, text);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
                } else if (endHour - startHour >= 24 && endMin >= startMin) {
                    title = textLine + startTitle + "～"  + endTitle;
                    String text = gsMsg.getMessage("tcd.161");
                    msg = new ActionMessage("error.input.comp.text", title, text);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text2");
                }
            } catch (Exception e) {
            }
        }

        return errors;
    }


    /**
     * <br>[機  能] 休日区分のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 休暇理由
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行例外
     */
    private ActionErrors __isAbsenceReason(
            ActionErrors errors, String value, long num) throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "reason.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2",
                new String[] {String.valueOf(num)});

        String textKbn = gsMsg.getMessage("tcd.203");
        String errorMsg = textLine + textKbn;

        //休暇理由
        if (!StringUtil.isNullZeroString(value)) {

            //休暇理由存在確認
            TcdHolidayInfDao thiDao = new TcdHolidayInfDao(con__);
            TcdHolidayInfModel tcdMdl = thiDao.getHolidayInf(value);
            if (tcdMdl == null) {
                msg = new ActionMessage("search.notfound.tdfkcode",
                        textLine + textKbn);
                StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
            }

            //スペースのみチェック
            if (ValidateUtil.isSpace(value)) {
                msg = new ActionMessage("error.input.spase.cl.only",
                        textLine + textKbn);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.cl.only");
            }

            //先頭スペースチェック
            if (ValidateUtil.isSpaceStart(value)) {
                msg = new ActionMessage("error.input.spase.start",
                        textLine + textKbn);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");
            }

            //タブスペースチェック
            if (ValidateUtil.isTab(value)) {
                msg = new ActionMessage("error.input.tab.text",
                        textLine + textKbn);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");
            }

            //最大桁数チェック
            __checkRange(errors, value, eprefix, errorMsg,
                    GSConstTimecard.MAX_LENGTH_ABSENCE_REASON);

            //内容 JIS第2水準チェック
            __checkJisString(errors, value, eprefix, errorMsg);
        }

        return errors;
    }

    /**
     * <br>[機  能] 休日内容のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 休日内容
     * @param num 行数
     * @param absenceKbn 休日区分
     * @return ActionErrors
     */
    private ActionErrors __isOkAbsenceNaiyo(
            ActionErrors errors,
            String value,
            long num,
            String absenceKbn) throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "naiyo.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2",
                new String[] {String.valueOf(num)});

        String textNaiyo = gsMsg.getMessage("tcd.200");
        String errorMsg = textLine + textNaiyo;
        
        TcdHolidayInfDao thiDao = new TcdHolidayInfDao(con__);
        TcdHolidayInfModel tcdMdl = thiDao.getHolidayInf(absenceKbn);
        if (tcdMdl != null && tcdMdl.getThiContentKbn() == GSConstTimecard.HOLIDAY_CONTENT_KBN_NG) {
            if (!StringUtil.isNullZeroString(value)) {
                msg = new ActionMessage("tcd150.not.permitted.input.naiyo", errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
            }
        }

        if (!StringUtil.isNullZeroString(value)) {
            //スペースのみの入力かチェック
            if (ValidateUtil.isSpace(value)) {
                msg = new ActionMessage("error.input.spase.only", textLine + textNaiyo);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");
            }

            //先頭スペースチェック
            if (ValidateUtil.isSpaceStart(value)) {
                msg = new ActionMessage("error.input.spase.start",
                        textLine + textNaiyo);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");
            }

            //タブスペースチェック
            if (ValidateUtil.isTab(value)) {
                msg = new ActionMessage("error.input.tab.text",
                        textLine + textNaiyo);
                StrutsUtil.addMessage(errors, msg, eprefix + "content");
            }

            //最大桁数チェック
            __checkRange(errors, value, eprefix, errorMsg, GSConstTimecard.MAX_LENGTH_SONOTA);

            //内容 JIS第2水準チェック
            __checkJisString(errors, value, eprefix, errorMsg);
        }

        //休日区分なし、休日内容のみの入力はNG
        if (StringUtil.isNullZeroString(absenceKbn)
                && !StringUtil.isNullZeroString(value)) {
            msg = new ActionMessage("tcd150.not.permitted.input.naiyo",
                    textLine + textNaiyo);
            StrutsUtil.addMessage(errors, msg, eprefix + "tcd150.not.permitted.input.naiyo");
        }

        return errors;
    }

    /**
     * <br>[機  能] 休日日数のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 休日日数
     * @param num 行数
     * @param absenceKbn 休日区分
     * @return ActionErrors
     */
    private ActionErrors __isOkAbsenceDays(
            ActionErrors errors,
            String value,
            long num,
            String absenceKbn) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "days.";
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //行目の
        String textLine = gsMsg.getMessage("cmn.line2",
                new String[] {String.valueOf(num)});

        String textDays = gsMsg.getMessage("tcd.201");
        String errorMsg = textLine + textDays;

        //未入力・未選択チェック
        if (!StringUtil.isNullZeroString(absenceKbn)) {
            __checkNoInput(errors, value, eprefix, errorMsg);
        }

        //休日区分なし、休日日数のみの入力はNG
        if (StringUtil.isNullZeroString(absenceKbn)
                && !StringUtil.isNullZeroString(value)) {
            msg = new ActionMessage("error.input.required.text",
                    textLine + gsMsg.getMessage("tcd.203"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        }

        //フォーマットチェック
        if (!StringUtil.isNullZeroString(value)
                && !ValidateUtil.isNumberDot(value)) {
            msg = new ActionMessage("error.input.number.hankaku",
                    textLine + textDays);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.number.hankaku");
        }
        return errors;
    }

    /**
     * <br>[機  能] 遅刻のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 遅刻
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkTikoku(ActionErrors errors, String value, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "tikoku.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2",
                new String[] {String.valueOf(num)});

        //遅刻
        if (!StringUtil.isNullZeroString(value)) {
            String tikoku = gsMsg.getMessage("tcd.18");
            if (!value.equals(tikoku)) {
                msg = new ActionMessage("error.input.format.text", textLine + tikoku);
                StrutsUtil.addMessage(errors, msg, eprefix + tikoku);
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 早退のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param value 早退
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkSoutai(ActionErrors errors, String value, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "soutai.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2",
                new String[] {String.valueOf(num)});

        //早退
        if (!StringUtil.isNullZeroString(value)) {
            String soutai = gsMsg.getMessage("tcd.22");
            if (!value.equals(soutai)) {
                msg = new ActionMessage("error.input.format.text", textLine + soutai);
                StrutsUtil.addMessage(errors, msg, eprefix + soutai);
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 備考のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param biko 備考
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkBiko(ActionErrors errors, String biko, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "biko.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        //備考
        if (!StringUtil.isNullZeroString(biko)) {
            //備考
            String textMemo = gsMsg.getMessage("cmn.memo");
            String errorMsg = textLine + textMemo;

            //備考 桁数チェック
            __checkRange(errors, biko, eprefix, errorMsg, GSConstTimecard.MAX_LENGTH_BIKO);
            //備考 スペースのみチェック
            if (ValidateUtil.isSpace(biko)) {
                msg = new ActionMessage("error.input.spase.only",
                        textLine + textMemo);
                StrutsUtil.addMessage(errors, msg, eprefix + "biko");
            }
            //備考 先頭スペースチェック
            if (ValidateUtil.isSpaceStart(biko)) {
                msg = new ActionMessage("error.input.spase.start",
                        textLine + textMemo);
                StrutsUtil.addMessage(errors, msg, eprefix + "biko");
            }
            //備考 タブスペースチェック
            if (ValidateUtil.isTab(biko)) {
                msg = new ActionMessage("error.input.tab.text",
                        textLine + textMemo);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");
            }
            //備考 JIS第2水準チェック
            __checkJisString(errors, biko, eprefix, errorMsg);
        }

        return errors;
    }

    /**
     * <br>[機  能] 指定された項目の未入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
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
            StrutsUtil.addMessage(errors, msg, element + "error.input.required.text");
            result = false;
        }

        return result;
    }
    /**
     * <br>[機  能] 指定された項目の桁数チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
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
     * @param errors ActionErrorsエラー
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
            msg = new ActionMessage("error.input.njapan.text", elementName, nstr);
            errors.add(element + "error.input.njapan.text", msg);
            result = false;
        }
        return result;
    }
}
