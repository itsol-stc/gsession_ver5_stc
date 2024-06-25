package jp.groupsession.v2.rsv.rsv250;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約インポート画面の取込みファイルをチェックします
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvImportCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvImportCheck.class);
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
    /** 施設予約基本設定 */
    private RsvAdmConfModel conf__ = null;
    /** 登録済み施設IDリスト */
    private List<String> rsdIdDbList__ = null;
    /** 施設情報マップ */
    private HashMap<String, RsvSisDataModel> rsdMap__ = null;
    /** 施設予約情報DAO インスタンス */
    private RsvSisYrkDao yrkDao__ = null;
    /** 予約時間保持リストと施設IDのマッピング */
    private HashMap<String, ArrayList<ArrayList<Long>>> svTimeMap__
            = new HashMap<String, ArrayList<ArrayList<Long>>>();
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>conf を取得します。
     * @return conf
     */
    public RsvAdmConfModel getConf() {
        return conf__;
    }
    /**
     * <p>conf をセットします。
     * @param conf conf
     */
    public void setConf(RsvAdmConfModel conf) {
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
     * @param reqMdl リクエストモデル
     */
    public RsvImportCheck(ActionErrors error, Connection con, RequestModel reqMdl) {
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
         yrkDao__ = new RsvSisYrkDao(con__);

         //基本設定取得
         RsvAdmConfDao aconfDao = new RsvAdmConfDao(con__);
         RsvAdmConfModel aconfModel = aconfDao.select();
         setConf(aconfModel);

         //登録済み施設IDリストを取得
         RsvSisDataDao rsdDao = new RsvSisDataDao(con__);
         rsdIdDbList__ = rsdDao.getRsdSidList();

         //施設情報マップ
         List<RsvSisDataModel> allRsdList = rsdDao.select();
         rsdMap__ = new HashMap<String, RsvSisDataModel>();
         if (allRsdList != null) {
             for (RsvSisDataModel model : allRsdList) {
                 rsdMap__.put(model.getRsdId(), model);
             }
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
                String eprefix = "inputFile." + num + ".";
                int ecnt = errors__.size();
                GsMessage gsMsg = new GsMessage(getReqMdl());
                log__.debug("項目数=" + stringTokenizer.length());
                if (stringTokenizer.length() != GSConstReserve.IMP_VALUE_SIZE_ADM) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.input.format.file",
                                gsMsg.getMessage("cmn.capture.file"),
                                gsMsg.getMessage("cmn.csv.number.items")
                                + "(" + gsMsg.getMessage(
                                        "cmn.line", new String[] {String.valueOf(num)})
                                + ")");
                    StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
                } else {

                    boolean inputFlg = false;
                    boolean inputFrTime = false;
                    boolean inputToTime = false;
                    UDate frDate = new UDate();
                    UDate toDate = new UDate();
                    String sisetuId = null;
                    int errorSize = 0;

                    while (stringTokenizer.hasMoreTokens()) {
                        j++;
                        buff = stringTokenizer.nextToken();

                        //施設ID
                        if (j == 1) {
                            __isOkSisetuId(errors__, buff, num);
                            sisetuId = buff;
                        }
                        //ユーザID
                        if (j == 2) {
                            __isOkUserId(errors__, buff, num);
                        }
                        //利用目的
                        if (j == 3) {
                            __isOkMokuteki(errors__, buff, inputFlg, num);
                        }
                        //開始日付
                        if (j == 4) {
                            errorSize = errors__.size();
                            __isOkDate(errors__, buff, GSConstReserve.FROM_DATE_KBN, num, frDate);
                        }
                        //開始時刻
                        if (j == 5) {
                            inputFrTime = __isOkTime(
                                    errors__, buff, GSConstReserve.FROM_DATE_KBN, num, frDate);
                        }
                        //終了日付
                        if (j == 6) {
                            __isOkDate(errors__, buff, GSConstReserve.TO_DATE_KBN, num, toDate);
                        }
                        //終了時刻
                        if (j == 7) {

                            inputToTime = __isOkTime(
                                    errors__, buff, GSConstReserve.TO_DATE_KBN, num, toDate);

                            if (inputFrTime == true && inputToTime == true) {
                                //開始・終了大小チェック
                                if (frDate.compare(frDate, toDate) <= UDate.EQUAL) {
                                    ActionMessage msg = new ActionMessage("error.input.comp.text",
                                          num + gsMsg.getMessage("cmn.start.end.of.line"),
                                          gsMsg.getMessage("cmn.start.lessthan.end"));
                                    StrutsUtil.addMessage(errors__,
                                            msg, eprefix + "error.input.comp.text");
                                }
                            }

                            if (errorSize == errors__.size()) {
                                __isOkYoyaku(errors__, sisetuId, num, frDate, toDate);
                            }

                        }

                        //備考
                        if (j == 8) {
                            __isOkBiko(errors__, buff, num);
                        }
                        //編集権限
                        if (j == 9) {
                            __isOkEditAuth(errors__, buff, num);
                        }
                        //公開区分
                        if (j == 10) {
                            __isOkPublic(errors__, buff, num);
                        }
                        //担当部署
                        if (j == 11) {
                            __isOkBusyo(errors__, buff, num);
                        }
                        //担当・使用者名
                        if (j == 12) {
                            __isOkName(errors__, buff, num);
                        }
                        //人数
                        if (j == 13) {
                            __isOkNum(errors__, buff, num);
                        }
                        //利用区分
                        if (j == 14) {
                            __isOkUseKbn(errors__, buff, num);
                        }
                        //連絡先
                        if (j == 15) {
                            __isOkContact(errors__, buff, num);
                        }
                        //会議名案内
                        if (j == 16) {
                            __isOkGuide(errors__, buff, num);
                        }
                        //駐車場見込み台数
                        if (j == 17) {
                            __isOkParkNum(errors__, buff, num);
                        }
                        //印刷区分
                        if (j == 18) {
                            __isOkPrintKbn(errors__, buff, num);
                        }
                        //行き先
                        if (j == 19) {
                            __isOkDest(errors__, buff, num);
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
     * <br>[機  能] 施設IDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param sisetuId 施設ID
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkSisetuId(ActionErrors errors, String sisetuId, long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "sisetuId.";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        if (StringUtil.isNullZeroString(sisetuId)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage("reserve.55"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        } else if (sisetuId.length() > GSConstReserve.MAX_LENGTH_SISETU_ID) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage("reserve.55"),
                    GSConstReserve.MAX_LENGTH_SISETU_ID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

        } else if (!GSValidateUtil.isOtheridFormat(sisetuId)) {
            //施設ＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.text",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage("reserve.55"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
        } else if (!rsdIdDbList__.contains(sisetuId)) {
                //存在チェック
                msg = new ActionMessage("search.notfound.tdfkcode",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + gsMsg.getMessage("reserve.55"));
                StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
        }
        return errors;
    }

    /**
     * <br>[機  能] ユーザIDのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param userId ユーザID
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkUserId(ActionErrors errors, String userId, long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "userId.";
        GsMessage gsMsg = new GsMessage(getReqMdl());

        if (StringUtil.isNullZeroString(userId)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage("cmn.line2", new String[] { String.valueOf(num)})
                    + gsMsg.getMessage("cmn.user.id"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");

        } else if (userId.length() > GSConstReserve.MAX_LENGTH_USER_ID) {
            // MAX桁チェック
            msg = new ActionMessage("error.input.length.text",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage("cmn.user.id"),
                    GSConstReserve.MAX_LENGTH_USER_ID);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

        } else if (!GSValidateUtil.isUseridFormat(userId)) {
            //ユーザＩＤフォーマットチェック
            msg = new ActionMessage("error.input.format.text",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage("cmn.user.id"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
        } else if (userId.equals(GSConstReserve.TEXT_GS_KANRI)
                || userId.equals(GSConstReserve.TEXT_SYSTEM_MAIL)) {
            //GS管理者、システムメールチェック
            msg = new ActionMessage("error.input.format.file",
                    gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)}),
                    gsMsg.getMessage("cmn.user.id"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.file");
        } else {
            //存在チェック
            CmnUsrmDao dao = new CmnUsrmDao(con__);
            boolean ret = dao.existLoginidEdit(-1, userId);
            if (!ret) {
                msg = new ActionMessage("error.edit.no.userid",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)}));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.edit.no.userid");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 利用目的のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param mokuteki 利用目的
     * @param idinput ログインID入力有無
     * @param num 行数
     * @throws SQLException SQL実行時例外
     * @return ActionErrors
     */
    private ActionErrors __isOkMokuteki(
            ActionErrors errors,
            String mokuteki,
            boolean idinput,
            long num)
    throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "mokuteki.";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        //利用目的 未入力チェック
        if (StringUtil.isNullZeroString(mokuteki)) {
            msg =
                new ActionMessage("error.input.required.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //利用目的 桁数チェック
        } else if (mokuteki.length() > GSConstReserve.MAX_LENGTH_MOKUTEKI) {
            msg = new ActionMessage("error.input.length.text",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage("reserve.72"),
                    String.valueOf(GSConstReserve.MAX_LENGTH_MOKUTEKI));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

        //利用目的 スペースのみチェック
        } else if (ValidateUtil.isSpace(mokuteki)) {
            msg = new ActionMessage("error.input.spase.only",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");

        //利用目的 先頭スペースチェック
        } else if (ValidateUtil.isSpaceStart(mokuteki)) {
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");
        //利用目的 タブスペースチェック
        } else if (ValidateUtil.isTab(mokuteki)) {
            msg = new ActionMessage("error.input.tab.text",
                    gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                    + gsMsg.getMessage("reserve.72"));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");

        //利用目的 JIS第2水準チェック
        } else if (!GSValidateUtil.isGsJapaneaseString(mokuteki)) {
            String nstr = GSValidateUtil.getNotGsJapaneaseString(mokuteki);
            msg =
                new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + gsMsg.getMessage("reserve.72"),
                        nstr);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
        }
        return errors;
    }


    /**
     * <p>年月日の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param ymd 年月日
     * @param frto 開始(0)・終了(1)
     * @param num 行数
     * @param udate チェックした日付
     * @return ActionErrors
     */
    private ActionErrors __isOkDate(ActionErrors errors,
            String ymd, int frto, long num, UDate udate) {

        boolean errorFlg = false;
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String title = "";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        if (frto == 0) {
            title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
            + gsMsg.getMessage("cmn.startdate");
        } else {
            title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
            + gsMsg.getMessage("cmn.end.date");
        }

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
                sptYear = list.get(0).trim();
                sptMonth = list.get(1).trim();
                sptDay = list.get(2).trim();
                try {
                    ymd = StringUtil.getStrYyyyMmDd(sptYear, sptMonth, sptDay);
                    ymdFomatHnt = true;
                } catch (NumberFormatException e) {
                }
            }

            //yyyy/mm/dd形式入力
            if (!ymdFomatHnt) {
                msg = new ActionMessage("error.input.comp.text",
                        title,
                        gsMsg.getMessage("cmn.format.date2"));
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
                    log__.debug("年月日CSV入力エラー");
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
     * @param frto 開始(0)・終了(1)
     * @param num 行数
     * @param udate チェック済み日付
     * @return inputFlg 入力:true 未入力:false
     */
    private boolean __isOkTime(ActionErrors errors,
            String time, int frto, long num, UDate udate) {

        boolean inputFlg = false;
        boolean errorFlg = false;
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + String.valueOf(frto) + "time.";
        String title = "";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        if (frto == 0) {
            title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
            + gsMsg.getMessage("cmn.starttime");
        } else {
            title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
            + gsMsg.getMessage("cmn.endtime");
        }

        int iBHour = 0;
        int iBMin = 0;
        if (!StringUtil.isNullZeroString(time)) {
            inputFlg = true;
            if (time.length() > 5) {
                msg = new ActionMessage("error.input.comp.text",
                        title,
                        gsMsg.getMessage("cmn.format.time"));
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.comp.text");
                errorFlg = true;
            } else {

                try {
                    List<String> list = StringUtil.split(":", time);
                    String hour = (String) list.get(0);
                    String min = (String) list.get(1);

                    iBHour = Integer.parseInt(hour);
                    iBMin = Integer.parseInt(min);
                } catch (Exception e) {
                    log__.debug("時刻CSV入力エラー");
                    msg = new ActionMessage("error.input.notvalidate.data", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notvalidate.data");
                    errorFlg = true;
                }

                //論理チェック
                if (iBHour < 0 || 23 < iBHour) {
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
                //分単位チェック
                int hourDiv = GSConstReserve.DF_HOUR_DIVISION;
                if (getConf() != null) {
                    hourDiv = getConf().getRacHourDiv();
                }
                int ans = iBMin % hourDiv;
                if (ans != 0) {
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
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", title);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        }
        return inputFlg;
    }

    /**
     * <p>予約時間の期限チェックと重複チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param sisetuId 施設ID
     * @param num 行数
     * @param frDate 開始日時
     * @param toDate 終了日時
     * @return errors アクションエラー
     * @throws Exception 実行時例外
     */
    private ActionErrors __isOkYoyaku(
            ActionErrors errors, String sisetuId,
            long num, UDate frDate, UDate toDate) throws Exception {

        if (StringUtil.isNullZeroString(sisetuId)) {
            return errors;
        }
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "yoyaku.";
        boolean errorFlg = false;
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage("cmn.period");

        //施設情報を取得
        RsvSisDataModel model = rsdMap__.get(sisetuId);

        //施設の情報を取得
        if (model != null) {

            //予約可能期限チェック(期限が設定されていればチェックする)
            String kigen = model.getRsdProp6();
            if (!StringUtil.isNullZeroString(kigen)) {

                //施設グループ管理者の場合は予約可能期限チェックをパスする
                RsvCommonBiz rsvBiz = new RsvCommonBiz();
                if (!rsvBiz.isGroupAdmin(con__, model.getRsdSid(),
                        reqMdl__.getSmodel().getUsrsid())) {
                    UDate now = new UDate();
                    UDate udKigen = now.cloneUDate();
                    udKigen.addDay(Integer.parseInt(kigen));

                    String kigenYmd = udKigen.getDateString();
                    String chkYmd = toDate.getDateString();

                    if (Integer.parseInt(chkYmd) > Integer.parseInt(kigenYmd)) {

                        String kigenStr = gsMsg.getMessage("cmn.comments")
                                + model.getRsdProp6()
                                + gsMsg.getMessage("cmn.days.after");

                        msg = new ActionMessage("error.kigen.over.sisetu", title, kigenStr);
                        StrutsUtil.addMessage(errors, msg, eprefix + "error.kigen.over.sisetu");
                        errorFlg = true;
                    }
                }
            }

            //重複のチェック(重複登録 = 不可の場合にチェック)
            String tyohuku = model.getRsdProp7();
            if (!errorFlg
                    && !StringUtil.isNullZeroString(tyohuku)
                    && Integer.parseInt(tyohuku) == GSConstReserve.PROP_KBN_HUKA) {


                boolean yrkOkFlg = true;

                //新規モード
                yrkOkFlg = yrkDao__.isYrkOk(-1, model.getRsdSid(), frDate, toDate);

                if (!yrkOkFlg) {
                    msg = new ActionMessage("error.yrk.exist.reserve", title);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.yrk.exist.reserve");
                }
                //ファイル内重複チェック
                __isOkYoyakuJuhuku(errors, num, sisetuId, frDate, toDate);
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 編集権限のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param editAuth 編集権限区分
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkEditAuth(ActionErrors errors, String editAuth, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "editAuth";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String errorMsg = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage("cmn.edit.permissions");

        if (StringUtil.isNullZeroString(editAuth)) {
            msg =
                new ActionMessage("error.input.required.text",
                        errorMsg);
            StrutsUtil.addMessage(errors, msg, eprefix);

        } else if (!editAuth.equals("0")
         && !editAuth.equals("1")
         && !editAuth.equals("2")) {
            msg =
                new ActionMessage(
                        "error.input.comp.text",
                        errorMsg,
                        "0～2");
            StrutsUtil.addMessage(errors, msg, eprefix + "editAuth");
        }

        return errors;
    }

    /**
     * <br>[機  能] 公開区分のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param public1 編集権限区分
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkPublic(ActionErrors errors, String public1, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "public";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String errorMsg = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage("cmn.public.kbn");

        if (StringUtil.isNullZeroString(public1)) {
            msg =
                new ActionMessage("error.input.required.text",
                        errorMsg);
            StrutsUtil.addMessage(errors, msg, eprefix);

        } else if (!public1.equals("0")
         && !public1.equals("1")
         && !public1.equals("2")
         && !public1.equals("4")) {
            msg =
                new ActionMessage(
                        "error.input.notvalidate.data",
                        errorMsg);
            StrutsUtil.addMessage(errors, msg, eprefix + "public");
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
        String eprefix = String.valueOf(num) + "biko";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        //備考
        if (!StringUtil.isNullZeroString(biko)) {
            //備考 桁数チェック
            if (biko.length() > GSConstReserve.MAX_LENGTH_BIKO) {
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + gsMsg.getMessage("cmn.content"),
                            String.valueOf(GSConstReserve.MAX_LENGTH_BIKO));
                StrutsUtil.addMessage(errors, msg, eprefix + "biko");
            }
            //備考 スペース・改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(biko)) {
                msg = new ActionMessage("error.input.spase.cl.only",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + gsMsg.getMessage("cmn.content"));
                StrutsUtil.addMessage(errors, msg, eprefix + "biko");
            }
            //備考 JIS第2水準チェック
            if (!GSValidateUtil.isGsJapaneaseStringTextArea(biko)) {
                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(biko);
                msg = new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + gsMsg.getMessage("cmn.content"),
                        nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "biko");
            }
        }

        return errors;
    }

    /**
     * <p>施設予約を登録する時間の取り込みファイル内重複チェック
     * @param errors ActionErrors
     * @param num 行数
     * @param sisetuId 施設ID
     * @param frDate 開始日時
     * @param toDate 終了日時
     * @return errors アクションエラー
     * @throws Exception 実行時例外
     */
    private ActionErrors __isOkYoyakuJuhuku(ActionErrors errors, long num,
            String sisetuId, UDate frDate, UDate toDate) throws Exception {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "juhuku";

        Long frTime = frDate.getTimeMillis();
        Long toTime = toDate.getTimeMillis();

        /** 予約時間保持リスト（ファイル内予約重複チェック用） */
        ArrayList<ArrayList<Long>> svTimeList = null;
        if (svTimeMap__.containsKey(sisetuId)) {
            svTimeList = svTimeMap__.get(sisetuId);

            GsMessage gsMsg = new GsMessage(getReqMdl());
            //取り込みファイル内の予約
            String message = gsMsg.getMessage("reserve.src.44");
            for (ArrayList<Long> list : svTimeList) {
                if ((frTime <= list.get(0) && toTime > list.get(0))
                        || (frTime < list.get(1) && toTime >= list.get(1))
                        || (frTime >= list.get(0) && toTime <= list.get(1))) {

                    //ファイル内予約時間重複エラー
                    msg = new ActionMessage("error.select.dup.list2",
                            gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                            + gsMsg.getMessage("cmn.period"), message);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.select.dup.list2");
                    break;
                }
            }

        } else {
            svTimeList = new ArrayList<ArrayList<Long>>();
        }

        //予約時間を保持する。
        ArrayList<Long> svTime = new ArrayList<Long>();
        svTime.add(frTime);
        svTime.add(toTime);
        svTimeList.add(svTime);
        svTimeMap__.put(sisetuId, svTimeList);
        return errors;
    }

    /**
     * <br>[機  能] 担当部署のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param busyo 担当部署
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkBusyo(ActionErrors errors, String busyo, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "busyo";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = "担当部署";

        if (!StringUtil.isNullZeroString(busyo)) {

            //桁数チェック
            if (busyo.length() > GSConstReserve.MAX_LENGTH_TBUSYO) {
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title,
                        String.valueOf(GSConstReserve.MAX_LENGTH_TBUSYO));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            //スペースのみチェック
            } else if (ValidateUtil.isSpace(busyo)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");

            //先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(busyo)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");

            //JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(busyo)) {
                String nstr = GSValidateUtil.getNotGsJapaneaseString(busyo);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                            + title,
                            nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 担当・使用者名のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param name 担当・使用者名
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkName(ActionErrors errors, String name, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "name";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = "担当・使用者名";

        if (!StringUtil.isNullZeroString(name)) {

            //桁数チェック
            if (name.length() > GSConstReserve.MAX_LENGTH_TNAME) {
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title,
                        String.valueOf(GSConstReserve.MAX_LENGTH_TNAME));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            //スペースのみチェック
            } else if (ValidateUtil.isSpace(name)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");

            //先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(name)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");

            //JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(name)) {
                String nstr = GSValidateUtil.getNotGsJapaneaseString(name);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                            + title,
                            nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }

        return errors;
    }
    /**
     * <br>[機  能] 人数のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param tNum 人数
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkNum(ActionErrors errors, String tNum, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "num";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = "人数";
        if (!StringUtil.isNullZeroString(tNum)) {

            // 数字以外の文字を入力した場合
            if (!GSValidateUtil.isNumber(tNum)) {
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title, gsMsg.getMessage("cmn.numbers"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");

            } else if (tNum.length() > GSConstReserve.MAX_LENGTH_TNUM) {
                //桁数チェック
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title,
                        String.valueOf(GSConstReserve.MAX_LENGTH_TNUM));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 利用区分のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param useKbn 利用区分
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkUseKbn(ActionErrors errors, String useKbn, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "useKbn";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = "利用区分";
        String min = "0";
        String max = "3";
        if (!StringUtil.isNullZeroString(useKbn)) {
            // 数字以外の文字を入力した場合
            if (!GSValidateUtil.isNumber(useKbn)) {
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title, gsMsg.getMessage("cmn.numbers"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
            } else if (!__isCheckUseKbn(useKbn)) {
                //選択肢チェック
                msg = new ActionMessage("error.input.lenge",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title, min, max);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 連絡先のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param contact 連絡先
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkContact(ActionErrors errors, String contact, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "contact";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = "連絡先";
        if (!StringUtil.isNullZeroString(contact)) {
            //桁数チェック
            if (contact.length() > GSConstReserve.MAX_LENGTH_CONTACT) {
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title,
                        String.valueOf(GSConstReserve.MAX_LENGTH_CONTACT));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            //スペースのみチェック
            } else if (ValidateUtil.isSpace(contact)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");

            //先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(contact)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");

            //JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(contact)) {
                String nstr = GSValidateUtil.getNotGsJapaneaseString(contact);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                            + title,
                            nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 会議名案内のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param guide 会議名案内
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkGuide(ActionErrors errors, String guide, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "guide";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = "会議名案内";
        if (!StringUtil.isNullZeroString(guide)) {
            //桁数チェック
            if (guide.length() > GSConstReserve.MAX_LENGTH_GUIDE) {
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title,
                        String.valueOf(GSConstReserve.MAX_LENGTH_GUIDE));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            //スペースのみチェック
            } else if (ValidateUtil.isSpace(guide)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");

            //先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(guide)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");

            //JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(guide)) {
                String nstr = GSValidateUtil.getNotGsJapaneaseString(guide);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                            + title,
                            nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 駐車場見込み台数のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param parkNum 駐車場見込み台数
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkParkNum(ActionErrors errors, String parkNum, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "parkNum";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = "駐車場見込み台数";
        if (!StringUtil.isNullZeroString(parkNum)) {
            // 数字以外の文字を入力した場合
            if (!GSValidateUtil.isNumber(parkNum)) {
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title, gsMsg.getMessage("cmn.numbers"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
            } else if (parkNum.length() > GSConstReserve.MAX_LENGTH_PARKNUM) {
                //桁数チェック
                msg = new ActionMessage("error.input.length.number",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title, String.valueOf(GSConstReserve.MAX_LENGTH_PARKNUM));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");
            }
        }
        return errors;
    }



    /**
     * <br>[機  能] 印刷区分のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param printKbn 印刷区分
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkPrintKbn(ActionErrors errors, String printKbn, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "printKbn";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = "印刷区分";
        String min = "0";
        String max = "1";
        if (!StringUtil.isNullZeroString(printKbn)) {
            // 数字以外の文字を入力した場合
            if (!GSValidateUtil.isNumber(printKbn)) {
                msg = new ActionMessage("error.input.comp.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title, gsMsg.getMessage("cmn.numbers"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.comp.text");
            } else if (!__isCheckPrintKbn(printKbn)) {
                //選択肢チェック
                msg = new ActionMessage("error.input.lenge",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title, min, max);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.lenge");
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 行き先のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param dest 行き先
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkDest(ActionErrors errors, String dest, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "dest";
        GsMessage gsMsg = new GsMessage(getReqMdl());
        String title = "行き先";
        if (!StringUtil.isNullZeroString(dest)) {
            //桁数チェック
            if (dest.length() > GSConstReserve.MAX_LENGTH_DEST) {
                msg = new ActionMessage("error.input.length.text",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title,
                        String.valueOf(GSConstReserve.MAX_LENGTH_DEST));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            //スペースのみチェック
            } else if (ValidateUtil.isSpace(dest)) {
                msg = new ActionMessage("error.input.spase.only",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");

            //先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(dest)) {
                msg = new ActionMessage("error.input.spase.start",
                        gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");

            //JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(dest)) {
                String nstr = GSValidateUtil.getNotGsJapaneaseString(dest);
                msg =
                    new ActionMessage("error.input.njapan.text",
                            gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                            + title,
                            nstr);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
            }
        }
        return errors;
    }


    /**
     * @return rsdIdDbList
     */
    public List<String> getRsdIdDbList() {
        return rsdIdDbList__;
    }
    /**
     * @param rsdIdDbList 設定する rsdIdDbList
     */
    public void setRsdIdDbList(List<String> rsdIdDbList) {
        rsdIdDbList__ = rsdIdDbList;
    }
    /**
     * @return rsdMap
     */
    public HashMap<String, RsvSisDataModel> getRsdMap() {
        return rsdMap__;
    }
    /**
     * @param rsdMap 設定する rsdMap
     */
    public void setRsdMap(HashMap<String, RsvSisDataModel> rsdMap) {
        rsdMap__ = rsdMap;
    }
    /**
     * @return svTimeMap
     */
    public HashMap<String, ArrayList<ArrayList<Long>>> getSvTimeMap() {
        return svTimeMap__;
    }
    /**
     * @param svTimeMap 設定する svTimeMap
     */
    public void setSvTimeMap(HashMap<String, ArrayList<ArrayList<Long>>> svTimeMap) {
        svTimeMap__ = svTimeMap;
    }
    /**
     * @return yrkDao
     */
    public RsvSisYrkDao getYrkDao() {
        return yrkDao__;
    }
    /**
     * @param yrkDao 設定する yrkDao
     */
    public void setYrkDao(RsvSisYrkDao yrkDao) {
        yrkDao__ = yrkDao;
    }


    /**
     * <br>[機  能] 指定した数字が利用区分に当てはまるかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param useKbn 利用区分
     * @return true：OK  false：範囲外
     */
    private boolean __isCheckUseKbn(String useKbn) {

        int kbn = Integer.valueOf(useKbn);
        if (kbn == GSConstReserve.RSY_USE_KBN_NOSET
                || kbn == GSConstReserve.RSY_USE_KBN_KAIGI
                || kbn == GSConstReserve.RSY_USE_KBN_KENSYU
                || kbn == GSConstReserve.RSY_USE_KBN_OTHER) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 指定した数字が印刷区分に当てはまるかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param printKbn 利用区分
     * @return true：OK  false：範囲外
     */
    private boolean __isCheckPrintKbn(String printKbn) {

        int kbn = Integer.valueOf(printKbn);
        if (kbn == GSConstReserve.RSY_PRINT_KBN_NO
                || kbn == GSConstReserve.RSY_PRINT_KBN_YES) {
            return true;
        }
        return false;
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