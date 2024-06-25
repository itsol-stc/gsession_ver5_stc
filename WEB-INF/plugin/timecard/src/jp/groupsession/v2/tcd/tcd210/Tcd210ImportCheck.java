package jp.groupsession.v2.tcd.tcd210;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.TimecardYukyuConfBiz;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] タイムカード 有休日数インポート画面の取込みファイルをチェックするクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd210ImportCheck extends AbstractCsvRecordReader {

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
    /** 重複登録確認用マップ */
    private Map<String, Long> repeatCheckMap__ = null;

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
     * <p>reqMdl を取得します。
     * @return reqMdl
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210ImportCheck#reqMdl__
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     * @see jp.groupsession.v2.tcd.tcd210.Tcd210ImportCheck#reqMdl__
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
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
    public Tcd210ImportCheck(
            ActionErrors error,
            Connection con,
            RequestModel reqMdl,
            int usrSid) {
        setErrors(error);
        setCon(con);
        reqMdl__ = reqMdl;
        repeatCheckMap__ = new HashMap<String, Long>();
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

        if (num > 1) {
            try {
                int j = 0;
                String eprefix = "inputFile." + num + ".";
                int ecnt = errors__.size();

                if (stringTokenizer.length() != GSConstTimecard.IMP_YUKYU_SIZE) {
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

                    String saveUserId = null;
                    String saveNendo = null;

                    while (stringTokenizer.hasMoreTokens()) {
                        j++;
                        buff = stringTokenizer.nextToken();

                        //ユーザID
                        if (j == 1) {
                            __isOkUserId(errors__, buff, num);
                            saveUserId = buff;
                        }

                        //登録年度
                        if (j == 2) {
                            saveNendo = buff;
                        }

                        //有休日数
                        if (j == 3) {
                            int ecntNendoBefore = errors__.size();
                            __isOkNendo(errors__, saveNendo, num).size();
                            int ecntNendoAfter = errors__.size();

                            //登録年度, ユーザIDでエラーが発生していない場合
                            if (ecnt == errors__.size()) {
                                String key = saveUserId + "," + saveNendo;
                                Long line = repeatCheckMap__.get(key);
                                //重複エラーチェック
                                if (line != null) {
                                    //登録年度、ユーザID
                                    String title = gsMsg.getMessage("tcd.tcd210.02");
                                    //行目の(前)
                                    String textLineFrom = gsMsg.getMessage("cmn.line2",
                                            new String[] {String.valueOf(line)});
                                    //行目の(後)
                                    String textLineTo = gsMsg.getMessage("cmn.line2",
                                            new String[] {String.valueOf(num)});

                                    //エラーの追加
                                    ActionMessage msg = new ActionMessage("error.select.dup.list2",
                                            textLineFrom + title, textLineTo + title);
                                    StrutsUtil.addMessage(errors__,
                                            msg, eprefix + "error.select.dup.list2");
                                } else {
                                    repeatCheckMap__.put(key, num);
                                }

                            }

                            String nendo = null;

                            //登録年度のチェックでエラーが発生していない場合、年度を設定する
                            if (ecntNendoBefore - ecntNendoAfter == 0) {
                                nendo = saveNendo;
                                __isOkYukyuDays(errors__, buff, num, nendo);
                            }
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
                throw e;
            }

        }
    }

    /**
     * <p>登録年度の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param nendo 年度
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkNendo(ActionErrors errors,
            String nendo, long num) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "nendo.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        //タイトル
        String title = textLine + gsMsg.getMessage("tcd.tcd210.01");

        if (StringUtil.isNullZeroString(nendo)) {
            //未入力チェック
            __checkNoInput(errors, nendo, eprefix, title, num);
        } else if (!ValidateUtil.isNumber(nendo)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.hankaku.prj", String.valueOf(num), title);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.number.hankaku.prj");
        } else if (!ValidateUtil.isExistDateYyyyMMdd(nendo + "0101")) {
            //フォーマットチェック
            msg = new ActionMessage("error.date.format.prj", String.valueOf(num), title);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.date.format.prj");
        }

        return errors;
    }

    /**
     * <p>ユーザIDの入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param id ユーザID
     * @param num 行数
     * @return ActionErrors
     * @throws SQLException
     */
    private ActionErrors __isOkUserId(ActionErrors errors,
            String id, long num) throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "userId.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        //タイトル
        String title = textLine + gsMsg.getMessage("cmn.user.id");

        //未入力チェック
        //桁数チェック
        if (__checkNoInput(errors, id, eprefix, title, num)
                && __checkRange(errors, id, eprefix, title, GSConstUser.MAX_LENGTH_USERID, num)) {
            //存在チェック
            CmnUsrmDao cuDao = new CmnUsrmDao(getCon());
            int usrSid = cuDao.selectLoginId(id);
            if (usrSid < 100) {
                msg = new ActionMessage("search.notfound.tdfkcode", title);
                StrutsUtil.addMessage(errors, msg, eprefix + "search.notfound.tdfkcode");
            } else {
                TimecardBiz tcdBiz = new TimecardBiz(getReqMdl());
                BaseUserModel usMdl = getReqMdl().getSmodel();
                int usrKbn = tcdBiz.getUserKbn(getCon(), usMdl);
                //グループ管理者の場合、対象ユーザの所属チェック
                if (usrKbn == GSConstTimecard.USER_KBN_GRPADM) {
                    GroupBiz gpBiz = new GroupBiz();
                    boolean belongFlg = false;
                    int sessionUsrSid = getReqMdl().getSmodel().getUsrsid();
                    List<LabelValueBean> groupList =
                            gpBiz.getAdminGroupLabelList(sessionUsrSid, getCon(), false, gsMsg);
                    for (LabelValueBean groupBean : groupList) {
                        int groupId = Integer.parseInt(groupBean.getValue());
                        if (gpBiz.isBelongGroup(usrSid, groupId, getCon())) {
                            belongFlg = true;
                            break;
                        }
                    }
                    if (!belongFlg) {
                        //所属チェック
                        msg = new ActionMessage("error.input.notvalidate.data", title);
                        StrutsUtil.addMessage(errors, msg, eprefix
                                + "error.input.notvalidate.data");
                    }
                }
            }
        }
        return errors;
    }

    /**
     * <p>有休日数の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param days 有休日数
     * @param num 行数
     * @param nendo 年度
     * @return ActionErrors
     */
    private ActionErrors __isOkYukyuDays(ActionErrors errors,
            String days, long num, String nendo) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "nendo.";
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
        //タイトル
        String title = textLine + gsMsg.getMessage("tcd.210");

        //未入力チェック
        if (!__checkNoInput(errors, days, eprefix, title, num)) {
        } else if (!ValidateUtil.isNumberDot(days, 3, 3)) {
            //半角数字チェック
            msg = new ActionMessage("error.input.number.hankaku.prj", String.valueOf(num), title);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.number.hankaku.prj");
        } else {
            //フォーマットチェック
            int maxDay = 365;
            BigDecimal dYukyu = new BigDecimal(days);

            TimecardYukyuConfBiz tycBiz = new TimecardYukyuConfBiz();
            if (tycBiz.isUruYear(Integer.parseInt(nendo))) {
                maxDay++;
            }
            BigDecimal bOne = BigDecimal.valueOf(1);
            BigDecimal bMaxDay = BigDecimal.valueOf(maxDay);
            if (dYukyu.compareTo(bOne) == -1 || dYukyu.compareTo(bMaxDay) == 1) {
                msg = new ActionMessage("error.date.format.prj", String.valueOf(num), title);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.date.format.prj");
            }
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
     * @param num 行数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkNoInput(ActionErrors errors,
                                    String value,
                                    String element,
                                    String elementName,
                                    long num) {
        boolean result = true;
        ActionMessage msg = null;

        if (StringUtil.isNullZeroString(value)) {
            msg = new ActionMessage("error.input.required.text.prj",
                    String.valueOf(num), elementName);
            StrutsUtil.addMessage(errors, msg, element + "error.input.required.text.prj");
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
     * @param num 行数
     * @return チェック結果 true : 正常, false : 異常
     */
    private boolean __checkRange(ActionErrors errors,
                                String value,
                                String element,
                                String elementName,
                                int range,
                                long num) {
        boolean result = true;
        ActionMessage msg = null;

        //MAX値を超えていないか
        if (value.length() > range) {
            msg = new ActionMessage("error.input.length.text.prj", String.valueOf(num), elementName,
                    String.valueOf(range));
            errors.add(element + "error.input.length.text.prj", msg);
            result = false;
        }
        return result;
    }
}
