package jp.groupsession.v2.ntp.ntp062;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

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
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.dao.NtpContactDao;
import jp.groupsession.v2.ntp.dao.NtpGyomuDao;
import jp.groupsession.v2.ntp.dao.NtpProcessDao;
import jp.groupsession.v2.ntp.model.NtpAdmConfModel;
import jp.groupsession.v2.ntp.model.NtpGyomuModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 案件インポート画面の取込みファイルをチェックするクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp062ImportCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp062ImportCheck.class);
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
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;
    /** グループID重複チェック用MAP */
    private HashMap<String, String> ankenCodeMap__;
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
     * <p>req を取得します。
     * @return reqMdl__
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>req をセットします。
     * @param reqMdl reqMdl
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>ankenCode を取得します。
     * @return ankenCode
     */
    public HashMap<String, String> getAnkenCodeMap() {
        return ankenCodeMap__;
    }

    /**
     * <p>ankenCode をセットします。
     * @param ankenCodeMap ankenCodeMap
     */
    public void setAnkenCodeMap(HashMap<String, String> ankenCodeMap) {
        ankenCodeMap__ = ankenCodeMap;
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
    public Ntp062ImportCheck(ActionErrors error, Connection con,
                                          RequestModel reqMdl) {
        setErrors(error);
        setCon(con);
        setReqMdl(reqMdl);
        setAnkenCodeMap(new HashMap<String, String>());
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
         readFile(file, Encoding.WINDOWS_31J);
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
        String gyoushuCode = "";

        if (num > 1) {

            try {

                int j = 0;
                String eprefix = "inputFile." + num + ".";;
                int ecnt = errors__.size();

                log__.debug("項目数===> " + stringTokenizer.length());
                if (stringTokenizer.length() != GSConstNippou.IMP_ANKEN_SIZE) {
                    ActionMessage msg =
                        new ActionMessage("error.input.format.file", GSConstNippou.TEXT_SELECT_FILE,
                                           GSConstNippou.TEXT_CSV_VALUE_COUNT + "(" + num + "行目)");
                    StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
                } else {

                    while (stringTokenizer.hasMoreTokens()) {
                        j++;
                        buff = stringTokenizer.nextToken();
                        log__.debug(num + "行目 " + j + ":" + buff);

                        //案件コード
                        if (j == 1) {
                            __isOkAnken(errors__, buff, num, con__);
                        }

                        //案件名
                        if (j == 2) {
                            __isOkAnkenName(errors__, buff, num);
                        }

                        //案件詳細
                        if (j == 3) {
                            __isOkValue(errors__, buff, num);
                        }

                        //企業コード
                        if (j == 4) {
                            __isOkKaisya(errors__, buff, num);
                        }

                        //業種コード
                        if (j == 5) {
                            gyoushuCode = buff;
                            __isOkGyoushu(errors__, buff, num);
                        }

                        //プロセスコード
                        if (j == 6) {
                            __isOkProcess(errors__, gyoushuCode, buff, num);
                            gyoushuCode = "";
                        }

                        //見込み度
                        if (j == 7) {
                            __isOkMikomi(errors__, buff, num);
                        }

                        //見積もり金額
                        if (j == 8) {
                            __isOkMitumori(errors__, buff, num);
                        }

                        //提出日
                        if (j == 9) {
                          __isOkDate(errors__, buff,
                          GSConstNippou.TEXT_ANKEN_MITUMORI_DATE, num,
                                         false);
                        }

                        //受注金額
                        if (j == 10) {
                            __isOkJutyu(errors__, buff, num);
                        }

                        //受注日
                        if (j == 11) {
                          __isOkDate(errors__, buff,
                          GSConstNippou.TEXT_ANKEN_JUTYU_DATE, num,
                                         false);
                        }

                        //商談結果
                        if (j == 12) {
                            __isOkShodan(errors__, buff, num);
                        }

                        //顧客源泉コード
                        if (j == 13) {
                            __isOkKokyakugensen(errors__, buff, num);
                        }

                        //状態
                        if (j == 14) {
                            __isOkState(errors__, buff, num);
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
     * @param hisu 入力必須フラグ true:入力必須 false:未入力OK
     * @return ActionErrors
     */
    private ActionErrors __isOkDate(ActionErrors errors, String ymd, String item, long num,
                                     boolean hisu) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String errorMsg = num + "行目の" + item;

        //未入力OKのとき
        if (!hisu && StringUtil.isNullZeroString(ymd)) {
            return errors;
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (StringUtil.isNullZeroString(ymd)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", errorMsg);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
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
                        errorMsg,
                        textYyyyMmDd);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.comp.text");

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
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                }

                //論理チェック
                UDate date = new UDate();
                date.setDate(iBYear, iBMonth, iBDay);
                if (date.getYear() != iBYear
                || date.getMonth() != iBMonth
                || date.getIntDay() != iBDay) {

                    msg = new ActionMessage("error.input.notfound.date", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                }
            }
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
     * @param con コネクション
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkAnken(ActionErrors errors, String anken, long num, Connection con)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "anken";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_ANKEN_CODE;

        //案件コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                errorMsg,
                anken,
               "ntp061NanCode",
                GSConstNippou.MAX_LENGTH_ANKEN_CODE,
                true);

        if (errors.isEmpty()) {
            //案件コードの重複チェック
            int ncnSid = -1;
            NtpAnkenDao dao = new NtpAnkenDao(con);
            if (dao.existAnken(ncnSid, anken)) {
                String fieldfix = GSConstNippou.TEXT_ANKEN_CODE + ".";
                msg = new ActionMessage("error.input.timecard.exist", errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix);
            }
        }


        //CSVファイル内重複チェック
        if (!StringUtil.isNullZeroString(anken)) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            if (ankenCodeMap__.containsKey(anken)) {
                String dupIndex = (String) ankenCodeMap__.get(anken);
                //行目の
                String dupLine = gsMsg.getMessage("cmn.line2", new String[] {dupIndex});

                msg = new ActionMessage(
                    "error.select.dup.list2",
                    errorMsg,
                    dupLine + GSConstNippou.TEXT_ANKEN_CODE);
                StrutsUtil.addMessage(
                    errors__,
                    msg,
                    "groupid." + num + "error.select.dup.list2");
            } else {
                ankenCodeMap__.put(anken, String.valueOf(num));
            }
        }



        return errors;
    }

    /**
     * <br>[機  能] 案件名のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param anken 案件SID
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkAnkenName(ActionErrors errors, String anken, long num)
    throws SQLException {

        String eprefix = String.valueOf(num) + "anken";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_ANKEN_NAME;

        //案件名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                errorMsg,
                anken,
                eprefix,
                GSConstNippou.MAX_LENGTH_ANKEN_NAME,
                true);

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
     * <br>[機  能] 業種コードのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param gyoushu 業種コード
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkGyoushu(ActionErrors errors, String gyoushu, long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "gyoushu";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_GYOUSHU_CODE;

        if (!StringUtil.isNullZeroString(gyoushu)) {
            if (ValidateUtil.isSpaceOrKaigyou(gyoushu)) {
                msg = new ActionMessage("error.input.spase.cl.only", errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + "value");
            } else {
                //存在チェック
                NtpGyomuModel model = new NtpGyomuModel();
                //if (ValidateUtil.isNumber(kaisya)) {
                    NtpGyomuDao dao = new NtpGyomuDao(con__);
                    model = dao.selectCode(gyoushu);
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
     * <br>[機  能] プロセスコードのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param gyoushu 業種コード
     * @param process プロセス
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkProcess(
                             ActionErrors errors, String gyoushu, String process, long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "gyoushu";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_PROCESS_CODE;

        //業種入力チェック
        if (StringUtil.isNullZeroString(gyoushu) && !StringUtil.isNullZeroString(process)) {
            msg = new ActionMessage("error.select.required.text",
                                     num + "行目の" + GSConstNippou.TEXT_GYOUSHU_CODE);
            StrutsUtil.addMessage(
                      errors, msg, eprefix);
        }

        //プロセス入力チェック
        if (!StringUtil.isNullZeroString(gyoushu) && StringUtil.isNullZeroString(process)) {
            msg = new ActionMessage("error.select.required.text", errorMsg);
            StrutsUtil.addMessage(
                      errors, msg, eprefix);
        }

        if (!StringUtil.isNullZeroString(process)) {
            if (ValidateUtil.isSpaceOrKaigyou(process)) {
                msg = new ActionMessage("error.input.spase.cl.only", errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + "value");
            } else {
                //存在チェック
                boolean exist = false;
                NtpProcessDao dao = new NtpProcessDao(con__);
                exist = dao.existProcess(gyoushu, process);

                if (!exist) {
                    msg = new ActionMessage("search.notfound.tdfkcode", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
                }
            }
        }
        return errors;
    }


    /**
     * <br>[機  能] 顧客源泉コードのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param kokyakugensen 顧客源泉コード
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkKokyakugensen(
                             ActionErrors errors, String kokyakugensen, long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "kokyakugensen";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_KOKYAKUGENSEN_CODE;

        if (!StringUtil.isNullZeroString(kokyakugensen)) {
            if (ValidateUtil.isSpaceOrKaigyou(kokyakugensen)) {
                msg = new ActionMessage("error.input.spase.cl.only", errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + "value");
            } else {
                //存在チェック
                boolean exist = false;
                NtpContactDao dao = new NtpContactDao(con__);
                exist = dao.existContact(-1, kokyakugensen);

                if (!exist) {
                    msg = new ActionMessage("search.notfound.tdfkcode", errorMsg);
                    StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
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

        if (!StringUtil.isNullZeroStringSpace(mikomi)) {
            if ((!mikomi.equals("10") && !mikomi.equals("30")
                && !mikomi.equals("50") && !mikomi.equals("70") && !mikomi.equals("100"))) {
                msg = new ActionMessage("error.input.comp.text", errorMsg, "10,30,50,70,100");
                StrutsUtil.addMessage(errors, msg, eprefix + "color");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 商談結果のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param mikomi 見込み度
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkShodan(ActionErrors errors, String mikomi, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "mikomi";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_SHODAN;

        if (!StringUtil.isNullZeroStringSpace(mikomi)) {
            if ((!mikomi.equals("0") && !mikomi.equals("1")
                && !mikomi.equals("2"))) {
                msg = new ActionMessage("error.input.comp.text", errorMsg, "0～2");
                StrutsUtil.addMessage(errors, msg, eprefix + "color");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 状態のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param state 見込み度
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkState(ActionErrors errors, String state, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "state";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_STATE;

        if (!StringUtil.isNullZeroStringSpace(state)) {
            if ((!state.equals("0") && !state.equals("1"))) {
                msg = new ActionMessage("error.input.comp.text", errorMsg, "0～1");
                StrutsUtil.addMessage(errors, msg, eprefix + "color");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 見積金額入力チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param mitumori 見積もり金額
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkMitumori(ActionErrors errors, String mitumori, long num) {

        String eprefix = String.valueOf(num) + "mikomi";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_ANKEN_MITUMORI;

        GSValidateNippou.validateCmnFieldTextNum(errors,
                errorMsg,
                mitumori,
                eprefix,
                GSConstNippou.MAX_LENGTH_ANKEN_MITUMORI,
                false);

        return errors;
    }


    /**
     * <br>[機  能] 金額入力チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param jutyu 受注金額
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkJutyu(ActionErrors errors, String jutyu, long num) {

        String eprefix = String.valueOf(num) + "jutyu";
        String errorMsg = num + "行目の" + GSConstNippou.TEXT_ANKEN_JUCYU;

        GSValidateNippou.validateCmnFieldTextNum(errors,
                errorMsg,
                jutyu,
                eprefix,
                GSConstNippou.MAX_LENGTH_ANKEN_MITUMORI,
                false);

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
//
//    /**
//     * <br>[機  能] 指定された項目の未入力チェックを行う
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param errors アクションエラー
//     * @param value 項目の値
//     * @param element 項目名
//     * @param elementName 項目名(日本語)
//     * @return チェック結果 true : 正常, false : 異常
//     */
//    private boolean __checkNoInput(ActionErrors errors,
//                                    String value,
//                                    String element,
//                                    String elementName) {
//        boolean result = true;
//        ActionMessage msg = null;
//
//        if (StringUtil.isNullZeroString(value)) {
//            msg = new ActionMessage("error.input.required.text", elementName);
//            errors.add(element + "error.input.required.text", msg);
//            result = false;
//            log__.debug("error:6");
//        } else {
//            //スペースのみの入力かチェック
//            if (ValidateUtil.isSpace(value)) {
//                msg = new ActionMessage("error.input.spase.only", elementName);
//                errors.add(element + "error.input.spase.only", msg);
//                result = false;
//            }
//
//        }
//
//        return result;
//    }
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
}