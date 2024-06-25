package jp.groupsession.v2.ntp.ntp110;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpKtbunruiDao;
import jp.groupsession.v2.ntp.dao.NtpKthouhouDao;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.ntp.model.NtpKtbunruiModel;
import jp.groupsession.v2.ntp.model.NtpKthouhouModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 インポート画面の取込みファイルをチェックするクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp110ImportCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp110ImportCheck.class);
    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** コネクション */
    private Connection con__ = null;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** フォーマットエラーフラグ */
    private boolean formatError__ = false;
    /** 日報基本設定 */
    private NtpAdmConfModel conf__ = null;
    /** RequestModel */
    private RequestModel reqMdl__ = null;
    /**
     * <p>conf を取得します。
     * @return conf
     */
    public NtpAdmConfModel getConf() {
        return conf__;
    }
    /**
     * <p>conf をセットします。
     * @param conf conf
     */
    public void setConf(NtpAdmConfModel conf) {
        conf__ = conf;
    }
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
     * <p>formatError__ を取得します。
     * @return formatError
     */
    public boolean isFormatError() {
        return formatError__;
    }
    /**
     * <p>formatError__ をセットします。
     * @param formatError formatError__
     */
    public void setFormatError(boolean formatError) {
        formatError__ = formatError;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param error アクションエラー
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Ntp110ImportCheck(ActionErrors error, Connection con,
                                RequestModel reqMdl) {
        setErrors(error);
        setCon(con);
        setReqMdl(reqMdl);
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
     public boolean isCsvDataOk(String csvFile) throws Exception {

         boolean ret = false;
         //基本設定取得
         NtpCommonBiz biz = new NtpCommonBiz(con__, reqMdl__);
         setConf(biz.getAdminConfiModel(con__));

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
         readFile(new File(csvFile), Encoding.WINDOWS_31J);
         log__.debug("有効データ件数 " + getCount());

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

        if (num > 1) {

            try {

                int j = 0;
                String eprefix = "inputFile." + num + ".";;
                int ecnt = errors__.size();

                log__.debug("項目数===> " + stringTokenizer.length());
                if (stringTokenizer.length() != GSConstNippou.IMP_VALUE_SIZE) {
                    ActionMessage msg =
                        new ActionMessage("error.input.format.file", GSConstNippou.TEXT_SELECT_FILE,
                                           GSConstNippou.TEXT_CSV_VALUE_COUNT + "(" + num + "行目)");
                    StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
                } else {

                    boolean inputFrTime = false;
                    boolean inputToTime = false;
                    UDate frDate = new UDate();
                    UDate toDate = new UDate();

                    while (stringTokenizer.hasMoreTokens()) {
                        j++;
                        buff = stringTokenizer.nextToken();
                        log__.debug(num + "行目 " + j + ":" + buff);

                        //報告日
                        if (j == 1) {
                            __isOkDate(errors__, buff, GSConstNippou.TEXT_REPORT_DATE, num, frDate,
                                       true);
                            toDate.setDate(frDate.getDateString());
                        }
                        //開始時刻
                        if (j == 2) {
                            String errorMsg = num + "行目の開始時間";
                            if (__checkNoInput(errors__, buff, eprefix, errorMsg)) {
                                inputFrTime = true;
                                __isOkTime(errors__, buff, GSConstNippou.FROM_DATE_KBN, num,
                                           frDate);
                            } else {
                                inputFrTime = false;
                            }
                        }
                        //終了時刻
                        if (j == 3) {
                            String errorMsg = num + "行目の終了時間";
                            if (__checkNoInput(errors__, buff, eprefix, errorMsg)) {
                                inputToTime = true;
                                __isOkTime(errors__, buff, GSConstNippou.TO_DATE_KBN, num, toDate);
                            } else {
                                inputToTime = false;
                            }

                            //開始・終了大小チェック
                            //開始・終了にエラーがある場合はnull
                            log__.debug("frDate ===> " + frDate);
                            log__.debug("toDate ===> " + toDate);
                            if (frDate != null && toDate != null) {
                                if (!inputFrTime && !inputToTime) {
                                    frDate.setZeroHhMmSs();
                                    toDate.setMaxHhMmSs();
                                }
                                if (frDate.compare(frDate, toDate) != UDate.LARGE) {
                                    ActionMessage msg = new ActionMessage("error.input.comp.text",
                                            num + "行目の開始時間・終了時間", "開始 < 終了");
                                    StrutsUtil.addMessage(errors__, msg,
                                                          eprefix + "error.input.comp.text");
                                }
                            }
                        }

                        //案件コード
                        if (j == 4) {
                            __isOkAnken(errors__, buff, num);
                        }
                        //企業コード
                        if (j == 5) {
                            __isOkKaisya(errors__, buff, num);
                            //kaisyaSid = buff;
                        }

                        //タイトル
                        if (j == 6) {
                            __isOkTitle(errors__, buff, num);
                        }
                        //タイトル色
                        if (j == 7) {
                            __isOkColor(errors__, buff, num);
                        }

                        //活動分類SID
                        if (j == 8) {
                            __isOkKatsudoBunrui(errors__, buff, num);
                        }
                        //活動方法SID
                        if (j == 9) {
                            __isOkKatsudoHouhou(errors__, buff, num);
                        }
                        //活動内容詳細
                        if (j == 10) {
                            __isOkValue(errors__, buff, num);
                        }
                        //見込み度
                        if (j == 11) {
                            __isOkMikomi(errors__, buff, num);
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
     * <p>年月日の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param ymd 年月日
     * @param item 項目名
     * @param num 行数
     * @param udate チェックした日付
     * @param hisu 入力必須フラグ true:入力必須 false:未入力OK
     * @return ActionErrors
     */
    private ActionErrors __isOkDate(ActionErrors errors, String ymd, String item, long num,
                                     UDate udate, boolean hisu) {

        boolean errorFlg = false;
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String errorMsg = num + "行目の" + item;

        //未入力OKのとき
        if (!hisu && StringUtil.isNullZeroString(ymd)) {
            return errors;
        }

        if (__checkNoInput(errors, ymd, eprefix, errorMsg)) {
            //8桁入力
            if (ymd.length() != 8) {
                msg = new ActionMessage("error.input.comp.text", errorMsg, "半角数字8桁(yyyymmdd形式)");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
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
                    log__.debug("年月日CSV入力エラー");
                    msg = new ActionMessage("error.input.notfound.date", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.date");
                    errorFlg = true;
                }

                //論理チェック
                UDate date = new UDate();
                date.setDate(iBYear, iBMonth, iBDay);
                if (date.getYear() != iBYear || date.getMonth() != iBMonth
                        || date.getIntDay() != iBDay) {

                    msg = new ActionMessage("error.input.notfound.date", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.date");
                    errorFlg = true;
                }
            }
        } else {
            errorFlg = true;
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
     * @param frto 開始(0)・終了(1)
     * @param num 行数
     * @param udate チェック済み日付
     * @return ActionErrors
     */
    private ActionErrors __isOkTime(ActionErrors errors,
            String time, int frto, long num, UDate udate) {

        boolean errorFlg = false;
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + String.valueOf(frto) + "time.";
        String errorMsg = "";

        if (frto == 0) {
            errorMsg = num + "行目の" + GSConstNippou.TEXT_FR_TIME;
        } else {
            errorMsg = num + "行目の" + GSConstNippou.TEXT_TO_TIME;
        }

        int iBHour = 0;
        int iBMin = 0;
        if (!StringUtil.isNullZeroString(time)) {

            if (time.length() > 5) {
                msg = new ActionMessage("error.input.comp.text", errorMsg, "半角数字5桁(hh:mm形式)");
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
                errorFlg = true;
            } else {

                try {
                    ArrayList<String> list = StringUtil.split(":", time);
                    String hour = (String) list.get(0);
                    String min = (String) list.get(1);

                    iBHour = Integer.parseInt(hour);
                    iBMin = Integer.parseInt(min);
                } catch (Exception e) {
                    log__.debug("時刻CSV入力エラー");
                    msg = new ActionMessage("error.input.notvalidate.data", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notvalidate.data");
                    errorFlg = true;
                }

                //論理チェック
                if (iBHour < 0 || 33 < iBHour) {
                    msg = new ActionMessage("error.input.notvalidate.data", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notvalidate.data");
                    errorFlg = true;
                }
                if (iBMin < 0 || 59 < iBMin) {
                    msg = new ActionMessage("error.input.notvalidate.data", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notvalidate.data");
                    errorFlg = true;
                }
                //分単位チェック
                int hourDiv = getConf().getNacHourDiv();
                int ans = iBMin % hourDiv;
                if (ans != 0) {
                    msg = new ActionMessage("error.input.notvalidate.data", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notvalidate.data");
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
     * <br>[機  能] 案件コードのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param anken 案件SID
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkAnken(ActionErrors errors, String anken, long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "anken";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_ANKEN_CODE;

        //未入力チェック
        if (!StringUtil.isNullZeroString(anken)) {
            if (ValidateUtil.isSpaceOrKaigyou(anken)) {
                msg = new ActionMessage("error.input.spase.cl.only", errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + "value");
            } else {
                //存在チェック
                NtpAnkenModel model = new NtpAnkenModel();
                NtpAnkenDao dao = new NtpAnkenDao(con__);
                model = dao.select(anken);

                if (model == null) {
                    msg = new ActionMessage("search.notfound.tdfkcode", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 企業コードのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param kaisya 会社SID
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkKaisya(ActionErrors errors, String kaisya, long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "kaisya";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_COMPANY_CODE;

        if (!StringUtil.isNullZeroString(kaisya)) {
            if (ValidateUtil.isSpaceOrKaigyou(kaisya)) {
                msg = new ActionMessage("error.input.spase.cl.only", errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + "value");
            } else {
                //存在チェック
                AdrCompanyModel model = new AdrCompanyModel();
                //if (ValidateUtil.isNumber(kaisya)) {
                    AdrCompanyDao dao = new AdrCompanyDao(con__);
                    model = dao.select(kaisya);
                //}
                if (model == null) {
                    msg = new ActionMessage("search.notfound.tdfkcode", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] タイトルのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param title タイトル
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkTitle(ActionErrors errors, String title, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "title";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_TITLE;

        //未入力チェック
        if (__checkNoInput(errors, title, eprefix, errorMsg)) {
            //桁数チェック
            if (__checkRange(errors, title, eprefix, errorMsg, GSConstNippou.MAX_LENGTH_TITLE)) {
                //先頭スペースチェック
                if (ValidateUtil.isSpaceStart(title)) {
                    msg = new ActionMessage("error.input.spase.start", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }

                //タブ文字が含まれている
                if (ValidateUtil.isTab(title)) {
                    msg = new ActionMessage("error.input.tab.text", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix);
                }
                //第二水準チェック
                __checkJisString(errors, title, eprefix, errorMsg);
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] タイトル色のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param color 色区分
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkColor(ActionErrors errors, String color, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "color";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_TITLE_COLOR;

        if (__checkNoInput(errors, color, eprefix, errorMsg)) {
            if (!color.equals("1") && !color.equals("2") && !color.equals("3")
                    && !color.equals("4") && !color.equals("5")) {
                msg = new ActionMessage("error.input.comp.text", errorMsg, "1～5");
                StrutsUtil.addMessage(errors, msg, eprefix + "color");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 活動分類コードのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param katsudo 活動分類SID
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkKatsudoBunrui(ActionErrors errors, String katsudo, long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "gyosyu";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_KTBUNRUI_CODE;


        if (!StringUtil.isNullZeroString(katsudo)) {
            if (ValidateUtil.isSpaceOrKaigyou(katsudo)) {
                msg = new ActionMessage("error.input.spase.cl.only", errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + "value");
            } else {
            //存在チェック
                NtpKtbunruiModel model = new NtpKtbunruiModel();
                NtpKtbunruiDao dao = new NtpKtbunruiDao(con__);
                model = dao.select(katsudo);

                if (model == null) {
                    msg = new ActionMessage("search.notfound.tdfkcode", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
                }
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 活動方法コードのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param katsudo 活動方法SID
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkKatsudoHouhou(ActionErrors errors, String katsudo, long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "houhou";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_KTHOUHOU_CODE;

        if (!StringUtil.isNullZeroString(katsudo)) {
            if (ValidateUtil.isSpaceOrKaigyou(katsudo)) {
                msg = new ActionMessage("error.input.spase.cl.only", errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + "value");
            } else {
                //存在チェック
                NtpKthouhouModel model = new NtpKthouhouModel();
                NtpKthouhouDao dao = new NtpKthouhouDao(con__);
                model = dao.select(katsudo);

                if (model == null) {
                    msg = new ActionMessage("search.notfound.tdfkcode", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
                }
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 内容のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param value 内容
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkValue(ActionErrors errors, String value, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "value";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_VALUE;

        //未入力チェック
        if (!StringUtil.isNullZeroString(value)) {
            //桁数チェック
            if (__checkRange(errors, value, eprefix, errorMsg, GSConstNippou.MAX_LENGTH_VALUE)) {
                //スペース・改行のみチェック
                if (ValidateUtil.isSpaceOrKaigyou(value)) {
                    msg = new ActionMessage("error.input.spase.cl.only", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "value");
                } else {
                    //先頭スペースチェック
                    if (ValidateUtil.isSpaceStart(value)) {
                        msg = new ActionMessage("error.input.spase.start", errorMsg);
                        StrutsUtil.addMessage(errors, msg, eprefix);
                    }
                    //JIS第2水準チェック
                    __checkJisString(errors, value, eprefix, errorMsg);
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 見込み度のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param mikomi 見込み度
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkMikomi(ActionErrors errors, String mikomi, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "mikomi";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_MIKOMIDO;

        if (__checkNoInput(errors, mikomi, eprefix, errorMsg)) {
            if ((!mikomi.equals("0") && !mikomi.equals("1")
                && !mikomi.equals("2") && !mikomi.equals("3") && !mikomi.equals("4"))) {
                msg = new ActionMessage("error.input.comp.text", errorMsg, "0～4");
                StrutsUtil.addMessage(errors, msg, eprefix + "color");
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
            log__.debug("error:6");
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
            log__.debug("error:7");
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
            msg = new ActionMessage("error.input.njapan.text", elementName, nstr);
            errors.add(element + "error.input.njapan.text", msg);
            result = false;
        }
        return result;
    }
    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
}