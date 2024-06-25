package jp.groupsession.v2.prj.prj160;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.model.PrjMembersModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] プロジェクト管理 TODOインポート画面の取り込みファイル内容をチェックするクラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class TodoImportCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TodoImportCheck.class);
    /** コネクション */
    private Connection con__ = null;
    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** 1行の項目数 */
    private static final int ROW_ITEM_CNT = 14;
    /** 全て未入力の場合は文字列 */
    private static final String NO_INPUT_STRING = ",,,,,,,,,,,,,";
    /** プロジェクトメンバーマッピング */
    private HashMap<String, PrjMembersModel> memberMap__;
    /** マイプロジェクト区分 */
    private int prjMyKbn__;

    /**
     * <p>prjMyKbn を取得します。
     * @return prjMyKbn
     */
    public int getPrjMyKbn() {
        return prjMyKbn__;
    }
    /**
     * <p>prjMyKbn をセットします。
     * @param prjMyKbn prjMyKbn
     */
    public void setPrjMyKbn(int prjMyKbn) {
        prjMyKbn__ = prjMyKbn;
    }
    /**
     * <p>memberMap を取得します。
     * @return memberMap
     */
    public HashMap<String, PrjMembersModel> getMemberMap() {
        return memberMap__;
    }
    /**
     * <p>memberMap をセットします。
     * @param memberMap memberMap
     */
    public void setMemberMap(HashMap<String, PrjMembersModel> memberMap) {
        memberMap__ = memberMap;
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
    /** リクエスト */
    protected HttpServletRequest req__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param error アクションエラー
     * @param con コネクション
     * @param memberMap メンバーマッピング
     * @param prjMyKbn マイプロジェクト区分
     * @param req リクエスト
     */
    public TodoImportCheck(ActionErrors error,
                            Connection con,
                            HashMap<String, PrjMembersModel> memberMap,
                            int prjMyKbn,
                            HttpServletRequest req
                            ) {
        setErrors(error);
        setCon(con);
        setMemberMap(memberMap);
        setPrjMyKbn(prjMyKbn);
        req__ = req;
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

         File file = new File(csvFile);
         if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT)) {
             if (errors__ != null) {
                 GsMessage gsMsg = new GsMessage(req__);
                 String eprefix = "inputFile.";
                 String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
                 ActionMessage msg =
                     new ActionMessage("error.over.row.csvdata",
                             textCaptureFile,
                             String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT));
                 StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
             }
             return true;
         }
         //ファイル読込み
         readFile(file, Encoding.WINDOWS_31J);
         log__.debug("有効データ件数 " + getCount());

         boolean ret = isErrorFlg();

         //有効データ無し
         if (!ret && getCount() == 0) {
             GsMessage gsMsg = new GsMessage();
             ret = true;

             ActionMessage msg =
                 new ActionMessage(
                         "search.notfound.tdfkcode",
                         gsMsg.getMessage(req__, "project.import.data"));
             StrutsUtil.addMessage(
                     errors__, msg, "search.notfound.tdfkcode");

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
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader
     * @see #processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {
        GsMessage gsMsg = new GsMessage();
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        //1行目はヘッダなのでチェック無し
        if (num > 1) {

            try {

                int i = 0;
                int ecnt = errors__.size();
                String eprefix = "inputFile.";

                if (lineStr.equals(NO_INPUT_STRING)) {
                    return;
                }

                log__.debug("項目数=" + stringTokenizer.length());

                //項目数不正
                if (stringTokenizer.length() < ROW_ITEM_CNT) {

                    ActionMessage msg =
                        new ActionMessage(
                                "error.item.num.min.prj",
                                gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)));
                    StrutsUtil.addMessage(
                            errors__, msg, eprefix + num + "error.input.format.file");

                } else {

                    while (stringTokenizer.hasMoreTokens()) {

                        String yoteiFrDate = null;
                        String yoteiToDate = null;
                        String zissekiFrDate = null;
                        String zissekiToDate = null;
                        boolean yoteiDateErrFlg = false;
                        boolean zissekiDateErrFlg = false;

                        i++;
                        buff = stringTokenizer.nextToken();

                        //タイトル
                        if (i == 1) {
                            __checkTitle(errors__, buff, num);
                        }

                        //カテゴリ
                        if (i == 2) {
                            __checkCategory(errors__, buff, num);
                        }

                        int bkErrCnt = errors__.size();
                        //予定 開始
                        String textStartYotei = gsMsg.getMessage(req__, "project.src.39");
                        //予定 開始
                        if (i == 3) {
                            __checkDate(errors__, buff, num, textStartYotei);
                            yoteiFrDate = buff;

                            if (bkErrCnt < errors__.size()) {
                                yoteiDateErrFlg = true;
                            }
                        }

                        bkErrCnt = errors__.size();
                        //予定 終了
                        String textKigen = gsMsg.getMessage(req__, "project.src.26");
                        //予定 終了
                        if (i == 4) {
                            __checkDate(errors__, buff, num, textKigen);
                            yoteiToDate = buff;

                            if (bkErrCnt < errors__.size()) {
                                yoteiDateErrFlg = true;
                            }
                        }

                        //予定 開始～終了
                        if (!yoteiDateErrFlg
                                && yoteiFrDate != null
                                && yoteiToDate != null) {

                            __checkDateRange(
                                    errors__,
                                    num,
                                    gsMsg.getMessage(req__, "project.src.39"),
                                    textKigen,
                                    yoteiFrDate,
                                    yoteiToDate);
                        }
                        //予定工数
                        String textYoteiKosu = gsMsg.getMessage(req__, "project.src.49");
                        //予定 工数
                        if (i == 5) {
                            __checkKosu(errors__, buff, num, textYoteiKosu);
                        }

                        bkErrCnt = errors__.size();
                        //実績 開始
                        String textJisseki = gsMsg.getMessage(req__, "project.src.37");
                        //実績 開始
                        if (i == 6) {
                            __checkDate(errors__, buff, num, textJisseki);
                            zissekiFrDate = buff;

                            if (bkErrCnt < errors__.size()) {
                                zissekiDateErrFlg = true;
                            }
                        }

                        bkErrCnt = errors__.size();
                        //実績 終了
                        String textEndJisseki = gsMsg.getMessage(req__, "project.src.20");
                        //実績 終了
                        if (i == 7) {
                            __checkDate(errors__, buff, num, textEndJisseki);
                            zissekiToDate = buff;

                            if (bkErrCnt < errors__.size()) {
                                zissekiDateErrFlg = true;
                            }
                        }

                        //実績 開始～終了
                        if (!zissekiDateErrFlg
                                && zissekiFrDate != null
                                && zissekiToDate != null) {

                            __checkDateRange(
                                    errors__,
                                    num,
                                    textJisseki,
                                    textEndJisseki,
                                    zissekiFrDate,
                                    zissekiToDate);
                        }
                        //実績 工数
                        String textJissekiKosu = gsMsg.getMessage(req__, "project.src.24");
                        //実績 工数
                        if (i == 8) {
                            __checkKosu(errors__, buff, num, textJissekiKosu);
                        }
                        //内容
                        String textContent = gsMsg.getMessage(req__, "cmn.content");
                        //内容
                        if (i == 9) {
                            __checkTextArea(
                                    errors__,
                                    buff,
                                    num,
                                    textContent,
                                    GSConstProject.MAX_LENGTH_CONTENT);
                        }

                        //担当
                        if (i == 10) {
                            __checkMember(errors__, buff, num);
                        }
                        //状態変更理由
                        String textComment = gsMsg.getMessage(req__, "project.36");
                        //状態変更理由
                        if (i == 14) {
                            __checkTextArea(
                                    errors__,
                                    buff,
                                    num,
                                    textComment,
                                    GSConstProject.MAX_LENGTH_STATUS_REASON);
                        }
                    }
                }

                //エラー有り
                if (ecnt < errors__.size()) {

                    //エラー存在フラグON
                    setErrorFlg(true);

                } else {

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
     * <br>[機  能] タイトルのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param buff タイトル
     * @param num 行数
     */
    private void __checkTitle(ActionErrors errors, String buff, long num) {
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + ".title.";
        //タイトル
        String textTitle = gsMsg.getMessage(req__, "cmn.title");
        //未入力チェック
        if (StringUtil.isNullZeroString(buff)) {

            msg =
                new ActionMessage(
                        "error.input.required.text.prj",
                        gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                        textTitle);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.required.text.prj");

        //スペースのみチェック
        } else if (ValidateUtil.isSpace(buff)) {

            msg =
                new ActionMessage(
                        "error.input.spase.only.prj",
                        gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                        textTitle);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.spase.only.prj");

        //先頭スペースチェック
        } else if (ValidateUtil.isSpaceStart(buff)) {
            msg =
                new ActionMessage(
                        "error.input.spase.start.prj",
                        gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                        textTitle);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.spase.start.prj");
        //タブスペースチェック
        } else if (ValidateUtil.isTab(buff)) {
            //行目の
            String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
            String errorMsg = textLine + textTitle;
            msg = new ActionMessage("error.input.tab.text",
                    errorMsg);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");

        //MAX桁チェック
        } else if (buff.length() > GSConstProject.MAX_LENGTH_TODO_TITLE) {

            msg =
                new ActionMessage(
                        "error.input.length.text.prj",
                        gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                        textTitle,
                        GSConstProject.MAX_LENGTH_TODO_TITLE);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.length.text.prj");

        //利用不可能な文字チェック
        } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(buff)) {

            //利用不可能な文字を入力した場合
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(buff);

            msg =
                new ActionMessage(
                        "error.input.njapan.text.prj",
                        gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                        textTitle,
                        nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.njapan.text.prj");
        }
    }

    /**
     * <br>[機  能] カテゴリのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param buff カテゴリ
     * @param num 行数
     */
    private void __checkCategory(ActionErrors errors, String buff, long num) {
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + ".category.";
        //カテゴリ
        String textCategory = gsMsg.getMessage(req__, "cmn.label");
        if (!StringUtil.isNullZeroString(buff)) {

            //スペースのみチェック
            if (ValidateUtil.isSpace(buff)) {

                msg =
                    new ActionMessage(
                            "error.input.spase.only.prj",
                            gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                            textCategory);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.spase.only.prj");

            //先頭スペースチェック
            } else if (ValidateUtil.isSpaceStart(buff)) {

                msg =
                    new ActionMessage(
                            "error.input.spase.start.prj",
                            gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                            textCategory);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.spase.start.prj");
            //タブスペースチェック
            } else if (ValidateUtil.isTab(buff)) {
                //行目の
                String textLine = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});
                String errorMsg = textLine + textCategory;

                msg = new ActionMessage("error.input.tab.text",
                        errorMsg);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.tab.text");

            //JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseString(buff)) {

                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(buff);
                msg =
                    new ActionMessage(
                            "error.input.njapan.text.prj",
                            gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                            textCategory,
                            nstr);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.njapan.text.prj");

            //MAX桁チェック
            } else if (buff.length() > GSConstProject.MAX_LENGTH_CATE_NAME) {

                msg =
                    new ActionMessage(
                            "error.input.length.text.prj",
                            gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                            textCategory,
                            GSConstProject.MAX_LENGTH_CATE_NAME);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.length.text.prj");

            }
        }
    }

    /**
     * <br>[機  能] 日付のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param buff 日付
     * @param num 行数
     * @param fieldName チェックするフィールド名称
     */
    private void __checkDate(ActionErrors errors, String buff, long num, String fieldName) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "." + fieldName + ".date.";

        if (!StringUtil.isNullZeroString(buff)) {
            GsMessage gsMsg = new GsMessage();

            //日付フォーマットチェック
            if (!GSValidateUtil.isSlashDateFormat(buff)) {
                msg =
                    new ActionMessage(
                            "error.date.format.prj",
                            gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                            fieldName);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.date.format.prj");

            } else {

                //日付存在チェック
                UDate date = new UDate();
                int month =
                    Integer.parseInt(
                            buff.substring(5, buff.lastIndexOf("/")));

                date.setDate(
                        Integer.parseInt(buff.substring(0, 4)),
                        month,
                        Integer.parseInt(buff.substring(buff.lastIndexOf("/") + 1)));

                if (date.getMonth() != month) {

                    msg =
                        new ActionMessage(
                                "error.input.notfound.date.prj",
                                gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                                fieldName);
                    StrutsUtil.addMessage(
                            errors, msg, eprefix + "error.input.notfound.date.prj");

                }
            }
        }
    }

    /**
     * <br>[機  能] 年月日大小チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param num 行数
     * @param fieldName1 チェック対象(開始日付)
     * @param fieldName2 チェック対象(終了日付)
     * @param startBuff 開始日付
     * @param endBuff 終了日付
     */
    private void __checkDateRange(ActionErrors errors,
                                   long num,
                                   String fieldName1,
                                   String fieldName2,
                                   String startBuff,
                                   String endBuff) {

        ActionMessage msg = null;
        String eprefix =
            String.valueOf(num)
            + "."
            + fieldName1
            + fieldName2
            + ".date.range";

        UDate dateStart = new UDate();
        dateStart.setDate(
            Integer.parseInt(startBuff.substring(0, 4)),
            Integer.parseInt(startBuff.substring(5, startBuff.lastIndexOf("/"))),
            Integer.parseInt(startBuff.substring(startBuff.lastIndexOf("/") + 1)));

        UDate dateEnd = new UDate();
        dateEnd.setDate(
            Integer.parseInt(endBuff.substring(0, 4)),
            Integer.parseInt(endBuff.substring(5, endBuff.lastIndexOf("/"))),
            Integer.parseInt(endBuff.substring(endBuff.lastIndexOf("/") + 1)));

        if (dateEnd.compareDateYMD(dateStart) == UDate.LARGE) {
            GsMessage gsMsg = new GsMessage();

            msg =
                new ActionMessage(
                        "error.input.comp.text.prj",
                        gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                        fieldName1 + " ～ " + fieldName2,
                        fieldName1 + " < " + fieldName2);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.comp.text.prj");
        }
    }

    /**
     * <br>[機  能] 工数のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param buff 工数
     * @param num 行数
     * @param fieldName チェックするフィールド名称
     */
    private void __checkKosu(ActionErrors errors, String buff, long num, String fieldName) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "." + fieldName + ".kosu.";

        if (!StringUtil.isNullZeroString(buff)) {
            if (!ValidateUtil.isNumberDot(
                    buff,
                    GSConstProject.MAX_LENGTH_KOSU_SEISU,
                    GSConstProject.MAX_LENGTH_KOSU_SYOSU)) {

                GsMessage gsMsg = new GsMessage();

                msg =
                    new ActionMessage(
                            "error.input.length.num.prj",
                            gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                            fieldName,
                            gsMsg.getMessage(req__, "project.kosu.keta.check"));

                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.length.num.prj");
            }
        }
    }

    /**
     * <br>[機  能] 内容や状態変更理由等、テキストエリアのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param buff 文字列
     * @param num 行数
     * @param fieldName チェックするフィールド名称
     * @param maxLenth 最大文字長
     */
    private void __checkTextArea(ActionErrors errors,
                                  String buff,
                                  long num,
                                  String fieldName,
                                  int maxLenth) {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "." + fieldName + ".textArea.";

        if (!StringUtil.isNullZeroString(buff)) {

            GsMessage gsMsg = new GsMessage();
            //スペース・改行のみチェック
            if (ValidateUtil.isSpaceOrKaigyou(buff)) {

                msg =
                    new ActionMessage(
                            "error.input.spase.cl.only.prj",
                            gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                            fieldName);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.spase.cl.only.prj");

            //JIS第2水準チェック
            } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(buff)) {

                //利用不可能な文字を入力した場合
                String nstr = GSValidateUtil.getNotGsJapaneaseString(buff);
                msg =
                    new ActionMessage(
                            "error.input.njapan.text.prj",
                            gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                            fieldName,
                            nstr);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.njapan.text.prj");

            //MAX桁チェック
            } else if (buff.length() > maxLenth) {

                msg =
                    new ActionMessage(
                            "error.input.length.text.prj",
                            gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                            fieldName,
                            maxLenth);
                StrutsUtil.addMessage(
                        errors, msg, eprefix + "error.input.length.text.prj");

            }
        }
    }

    /**
     * <br>[機  能] メンバーのチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param buff メンバー
     * @param num 行数
     */
    private void __checkMember(ActionErrors errors, String buff, long num) {
        GsMessage gsMsg = new GsMessage();
        //担当者
        String textStaff = gsMsg.getMessage(req__, "cmn.staff");
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + ".member.";
        if (prjMyKbn__ == GSConstProject.KBN_MY_PRJ_MY && !StringUtil.isNullZeroString(buff)) {
            msg =
                new ActionMessage(
                        "error.input.tanto.myprj",
                        gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                        textStaff);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + "error.input.tanto.myprj");
        } else if (!StringUtil.isNullZeroString(buff)) {

            int idx = 0;
            String[] splitMember = buff.split("\n");

            for (String member : splitMember) {

                if (!memberMap__.containsKey(member)) {

                    msg =
                        new ActionMessage(
                                "error.input.member.notfound.prj",
                                gsMsg.getMessage(req__, "cmn.line", String.valueOf(num)),
                                member);
                    StrutsUtil.addMessage(
                            errors,
                            msg,
                            eprefix
                            + member
                            + idx
                            + ".error.input.member.notfound.prj");

                }

                idx += 1;
            }
        }
    }

//    /**
//     * <br>[機  能] 優先度のチェックを行う
//     * <br>[解  説]
//     * <br>[備  考]
//     *
//     * @param errors ActionErrors
//     * @param buff 優先度
//     * @param num 行数
//     */
//    private void __checkPriority(ActionErrors errors, String buff, long num) {
//
//        ActionMessage msg = null;
//        String eprefix = String.valueOf(num) + ".priority.";
//
//        if (!StringUtil.isNullZeroString(buff)) {
//
//            if (!buff.equals(GSConstProject.WEIGHT_NAME_LOW)
//                    && !buff.equals(GSConstProject.WEIGHT_NAME_MIDDLE)
//                    && !buff.equals(GSConstProject.WEIGHT_NAME_HIGH)) {
//
//            }
//        }
//    }
//
//   /**
//    * <br>[機  能] 状態のチェックを行う
//    * <br>[解  説]
//    * <br>[備  考]
//    *
//    * @param errors ActionErrors
//    * @param buff 状態
//    * @param num 行数
//    */
//    private void __checkStatus(ActionErrors errors, String buff, long num) {
//
//        ActionMessage msg = null;
//        String eprefix = String.valueOf(num) + ".status.";
//
//        if (!StringUtil.isNullZeroString(buff)) {
//
//        }
//    }
}