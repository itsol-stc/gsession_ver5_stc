package jp.groupsession.v2.man.man460;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.man.man440.AbstractCybCsvImport;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSValidateUser;

/**
 * <br>[機  能] サイボウズLive イベント情報インポートファイルのチェックを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CybEventCsvImport extends AbstractCybCsvImport {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CybEventCsvImport.class);

    /** CSV列定義    */
    public enum COLNO {
      /** 開始日付*/
      FROM_DATE,
      /** 開始時刻*/
      FROM_TIME,
      /** 終了日付*/
      TO_DATE,
      /** 終了時刻*/
      TO_TIME,
      /** 予定メニュー*/
      PLAN_MENU,
      /** タイトル*/
      TITLE,
      /** メモ*/
      MEMO,
      /** 作成者*/
      CREATE_USER,
      /** コメント*/
      COMMENT
    }

    /** csv列定義実態 モードごとの列の違いが反映された配列*/
    private COLNO[] colArr__;

    /**
     * <br>[機 能] コンストラクタ
     * <br>[解 説]
     * <br>[備 考]
     * @param error アクションエラー
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param grpSid グループSID
     */
    public CybEventCsvImport(ActionErrors error, RequestModel reqMdl,
                             Connection con, int grpSid) {
        super(error, reqMdl, con);
        setGrpSid(grpSid);
        EnumSet<COLNO> eset = EnumSet.allOf(COLNO.class);
        colArr__ = eset.toArray(new COLNO[eset.size()]);
    }

    /**
     * <br>[機  能] インポート画面毎に設定するチェック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws Exception csv読込時例外
     */
    @Override
    protected void __validateImportData() throws Exception {
        //グループSID存在チェック
        if (this.getGrpSid() >= 0 && !GSValidateUser.existGroup(this.getGrpSid(), con__)) {
            GsMessage gsMsg = new GsMessage(reqMdl__);
            String eprefix = "group.";
            String group = gsMsg.getMessage("cmn.group"); //グループ
            ActionMessage msg = new ActionMessage("search.data.notfound", group);
            StrutsUtil.addMessage(errors__, msg, eprefix + "search.data.notfound");
        }
    }

    /**
     * <br>[機  能] csvファイル一行のチェック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    @Override
    protected void __check(long num, String lineStr) throws Exception {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        //CSV項目数
        String textCsvitems = gsMsg.getMessage("cmn.csv.number.items");
        //行目
        String textLine = gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)});
        //行目の
        String textLine2 = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)});

        try {

            int j = 0;

            String buff = "";
            String eprefix = "inputFile.";
            int ecnt = errors__.size();

            CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

            log__.debug("項目数=" + stringTokenizer.length());
            COLNO[] colArr = (COLNO[]) colArr__;
            if (stringTokenizer.length() != colArr.length) {
                ActionMessage msg =
                new ActionMessage(
                        "error.input.format.file",
                        textCaptureFile,
                        textCsvitems + "(" + textLine + ")");
                StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
            } else {
                boolean inputFrTime = false;
                boolean inputToTime = false;
                UDate frDate = null;
                UDate toDate = null;

                while (stringTokenizer.hasMoreTokens()) {
                    buff = stringTokenizer.nextToken();

                    if (buff != null && buff.equals("\"")) {
                        buff = ""; // 空データの場合、ダブルクォーテーションが残ってしまうのでその対策
                    }

                    switch (colArr[j]) {
                        //開始日付
                        case FROM_DATE:
                            String textFrDate = textLine2 + gsMsg.getMessage("cmn.startdate");
                            frDate = __isOkDate(errors__, buff,
                                                textFrDate, String.valueOf(num) + "frdate.");
                            break;

                        //開始時刻
                        case FROM_TIME:
                            String textFrTime = textLine2 + gsMsg.getMessage("cmn.starttime");
                            inputFrTime = false;
                            if (frDate != null && !StringUtil.isNullZeroStringSpace(buff)) {
                                __isOkTime(errors__, buff, frDate, textFrTime,
                                           String.valueOf(num) + "frtime.");
                                inputFrTime = true;
                            }
                            break;

                        //終了日付
                        case TO_DATE:
                            String textToDate = textLine2 + gsMsg.getMessage("cmn.end.date");
                            toDate = __isOkDate(errors__, buff,
                                                textToDate, String.valueOf(num) + "todate.");
                            break;

                        //終了時刻
                        case TO_TIME:
                            String textToTime = textLine2 + gsMsg.getMessage("cmn.endtime");
                            inputToTime = false;
                            if (toDate != null && !StringUtil.isNullZeroStringSpace(buff)) {
                                __isOkTime(errors__, buff, toDate, textToTime,
                                           String.valueOf(num) + "totime.");
                                inputToTime = true;
                            }
                            break;

                        //予定メニュー
                        case PLAN_MENU:
                            if (!StringUtil.isNullZeroString(buff)) {
                                String textPlan = textLine2 + gsMsg.getMessage("main.man460.5");
                                String fieldFix = String.valueOf(num) + "planmenu.";
                                // タブ文字については、スペースに変換する
                                //JIS第2水準チェック
                                if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                                    //利用不可能な文字を入力した場合
                                    String nstr = GSValidateUtil.getNotGsJapaneaseString(buff);
                                    ActionMessage msg = new ActionMessage(
                                                                        "error.input.njapan.text",
                                                                        textPlan, nstr);
                                    StrutsUtil.addMessage(errors__, msg,
                                                            fieldFix + "error.input.njapan.text");
                                }
                            }
                            break;

                        //タイトル
                        case TITLE:
                            if (!StringUtil.isNullZeroString(buff)) {
                                String textTitle = textLine2 + gsMsg.getMessage("cmn.title");
                                String fieldFix = String.valueOf(num) + "title.";
                                // ※タブ文字については、スペースに変換する
                                //JIS第2水準チェック
                                if (!GSValidateUtil.isGsJapaneaseString(buff)) {
                                    //利用不可能な文字を入力した場合
                                    String nstr = GSValidateUtil.getNotGsJapaneaseString(buff);
                                    ActionMessage msg = new ActionMessage(
                                                                        "error.input.njapan.text",
                                                                        textTitle, nstr);
                                    StrutsUtil.addMessage(errors__, msg,
                                                            fieldFix + "error.input.njapan.text");
                                }
                            }
                            break;

                        //内容
                        // ・文字数制限なし(文字数オーバーした場合、カットする)
                        case MEMO:
                            if (!StringUtil.isNullZeroString(buff)) {
                                String textContent = textLine2 + gsMsg.getMessage("cmn.content");
                                String fieldFix    = String.valueOf(num) + "value.";
                                //JIS第2水準チェック
                                if (!GSValidateUtil.isGsJapaneaseStringTextArea(buff)) {
                                    //利用不可能な文字を入力した場合
                                    String nstr =
                                            GSValidateUtil.getNotGsJapaneaseStringTextArea(buff);
                                    ActionMessage msg = new ActionMessage(
                                                    "error.input.njapan.text", textContent, nstr);
                                    StrutsUtil.addMessage(errors__,
                                                       msg, fieldFix + "error.input.njapan.text");
                                }
                            }
                            break;

                        //コメント(備考)
                        // ・文字数制限なし(文字数オーバーした場合、カットする)
                        case COMMENT:
                            if (!StringUtil.isNullZeroString(buff)) {
                                String textMemo = textLine2 + gsMsg.getMessage("cmn.memo");
                                String fieldFix = String.valueOf(num) + "biko.";
                                //JIS第2水準チェック
                                if (!GSValidateUtil.isGsJapaneaseStringTextArea(buff)) {
                                    //利用不可能な文字を入力した場合
                                    String nstr =
                                            GSValidateUtil.getNotGsJapaneaseStringTextArea(buff);
                                    ActionMessage msg = new ActionMessage(
                                                    "error.input.njapan.text", textMemo, nstr);
                                    StrutsUtil.addMessage(errors__,
                                                       msg, fieldFix + "error.input.njapan.text");
                                }
                            }
                            break;

                        default:
                            break;
                    }

                    j++;
                }

                //開始日時・終了日時チェック
                if (frDate != null && toDate != null) {
                    // サイボウズLiveデータ移行ではどちらか一方が時間指定なしの場合、両方とも時間指定なしとする
                    if (!inputFrTime || !inputToTime) {
                        // 時間指定なしの為、00:00:00 を指定
                        frDate.setZeroHhMmSs();
                        toDate.setMaxHhMmSs();
                    }

                    //開始・終了大小チェック
                    if (frDate.compare(frDate, toDate) == UDate.SMALL) {
                        String keyLineStartEnd = String.valueOf(num) + "start.end.";
                        //行目の開始・終了
                        String textLineStartEnd = String.valueOf(num)
                                                  + gsMsg.getMessage("cmn.start.end.of.line");
                        //開始 < 終了
                        String textStartLessEnd = gsMsg.getMessage("cmn.start.lessthan.end");
                        ActionMessage msg = new ActionMessage("error.input.comp.text",
                                                        textLineStartEnd, textStartLessEnd);
                        StrutsUtil.addMessage(errors__, msg,
                                              keyLineStartEnd + "error.input.comp.text");
                    }
                }
            }

            //エラー有り
            if (ecnt < errors__.size()) {
                //エラー存在フラグON
                setErrorFlg(true);
            } else {
                //明細データ以降
                if (num >= 2) {
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

    /**
     * <br>[機  能] csvファイル一行のインポート処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    @Override
    protected void __import(long num, String lineStr) throws Exception {
        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        COLNO[] colArr = colArr__;

        //スケジュールSID採番
        int scdSid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                SaibanModel.SBNSID_SUB_SCH, sessionUser__);

        SchDataModel impMdl = new SchDataModel();
        impMdl.setScdSid(scdSid);
        impMdl.setScdGrpSid(GSConstSchedule.DF_SCHGP_ID);
        impMdl.setSceSid(GSConstSchedule.DF_SCHGP_ID);
        impMdl.setScdRsSid(GSConstSchedule.DF_SCHGP_ID);

        impMdl.setScdUsrSid(grpSid__);
        impMdl.setScdUsrKbn(GSConstSchedule.USER_KBN_GROUP);

        impMdl.setScdBgcolor(GSConstSchedule.DF_BG_COLOR); // タイトル色 = デフォルト色
        impMdl.setScdEdit(GSConstSchedule.EDIT_CONF_GROUP); // 編集権限 = 所属グループのみ
        impMdl.setScdPublic(GSConstSchedule.DSP_NOT_PUBLIC); // 公開区分 = 非公開

        impMdl.setScdAuid(sessionUser__);
        impMdl.setScdAdate(sysUd__);
        impMdl.setScdEuid(sessionUser__);
        impMdl.setScdEdate(sysUd__);

        // 行データ解析
        while (stringTokenizer.hasMoreTokens()) {

            COLNO col = colArr[j];
            buff = stringTokenizer.nextToken();

            if (buff != null && buff.equals("\"")) {
                buff = ""; // 空データの場合、ダブルクォーテーションが残ってしまうのでその対策
            }
            __setEventModel(col, impMdl, buff);

            j++;
        }

        if (impMdl.getScdTitle().length() > GSConstSchedule.MAX_LENGTH_TITLE) {
            // タイトルの文字数オーバー時にはカットして、内容にも記載する
            String title = impMdl.getScdTitle().substring(0, GSConstSchedule.MAX_LENGTH_TITLE);
            String memo  = impMdl.getScdValue();
            if (!StringUtil.isNullZeroStringSpace(memo)) {
                impMdl.setScdValue(impMdl.getScdTitle() + "\r\n" + memo);
            } else {
                impMdl.setScdValue(impMdl.getScdTitle());
            }
            impMdl.setScdTitle(title);
        }

        // 内容の文字数オーバー時にはカットする
        if (!StringUtil.isNullZeroStringSpace(impMdl.getScdValue())
         && impMdl.getScdValue().length() > GSConstSchedule.MAX_LENGTH_VALUE) {
            String memo = impMdl.getScdValue().substring(0, GSConstSchedule.MAX_LENGTH_VALUE);
            impMdl.setScdValue(memo);
        }

        // 備考の文字数オーバー時にはカットする
        if (!StringUtil.isNullZeroStringSpace(impMdl.getScdBiko())
         && impMdl.getScdBiko().length() > GSConstSchedule.MAX_LENGTH_BIKO) {
            String biko = impMdl.getScdBiko().substring(0, GSConstSchedule.MAX_LENGTH_BIKO);
            impMdl.setScdBiko(biko);
        }
        //時間指定ありかつ日時が同じ場合は翌日0時までとする
        if (impMdl.getScdDaily() == GSConstSchedule.TIME_EXIST
                && impMdl.getScdFrDate().compare(impMdl.getScdFrDate()
                        , impMdl.getScdToDate()) == UDate.EQUAL) {
            UDate date = impMdl.getScdToDate();
            date.addDay(1);
            date.setZeroHhMmSs();
            impMdl.setScdToDate(date);
        }
        //登録処理
        SchDataDao dao = new SchDataDao(getCon());
        dao.insert(impMdl);
    }

    /**
     * <br>[機  能] イベント情報をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param col 項目クラス
     * @param impMdl SchDataModel
     * @param buff 読込文字列
     * @throws SQLException SQL実行時例外
     * @throws EncryptionException パスワード変換時例外
     */
    private void __setEventModel(COLNO col,
            SchDataModel impMdl,
            String buff
            ) throws SQLException, EncryptionException {

        ArrayList<String> list;
        UDate date;

        switch (col) {
        //開始日付
        case FROM_DATE:
            list = StringUtil.split("/", buff);

            int frYear  = new Integer(((String) list.get(0))).intValue();
            int frMonth = new Integer(((String) list.get(1))).intValue();
            int frDay   = new Integer(((String) list.get(2))).intValue();

            date = new UDate();
            date.setDate(StringUtil.getStrYyyyMmDd(frYear, frMonth, frDay));
            date.setZeroHhMmSs();
            impMdl.setScdFrDate(date);
            break;

        //開始時刻
        case FROM_TIME:
            if (!StringUtil.isNullZeroStringSpace(buff)) {
                list = StringUtil.split(":",  buff);

                int hour   = new Integer(((String) list.get(0))).intValue();
                int minute = new Integer(((String) list.get(1))).intValue();
                date = impMdl.getScdFrDate();
                date.setZeroHhMmSs();
                date.setHour(hour);
                date.setMinute(minute);
                impMdl.setScdFrDate(date);
            }
            break;

        //終了日付
        case TO_DATE:
            list = StringUtil.split("/", buff);

            int toYear  = new Integer(((String) list.get(0))).intValue();
            int toMonth = new Integer(((String) list.get(1))).intValue();
            int toDay   = new Integer(((String) list.get(2))).intValue();

            date = new UDate();
            date.setDate(StringUtil.getStrYyyyMmDd(toYear, toMonth, toDay));
            date.setMaxHhMmSs();
            impMdl.setScdToDate(date);
            break;

        //終了時刻
        case TO_TIME:
            if (!StringUtil.isNullZeroStringSpace(buff)) {
                list = StringUtil.split(":", buff);

                int hour   = new Integer(((String) list.get(0))).intValue();
                int minute = new Integer(((String) list.get(1))).intValue();
                date = impMdl.getScdToDate();
                date.setZeroHhMmSs();
                date.setHour(hour);
                date.setMinute(minute);
                impMdl.setScdToDate(date);
                impMdl.setScdDaily(GSConstSchedule.TIME_EXIST);
            } else {
                impMdl.setScdDaily(GSConstSchedule.TIME_NOT_EXIST);
            }
            break;

        //予定メニュー
        case PLAN_MENU:
            if (!StringUtil.isNullZeroStringSpace(buff)) {
                if (ValidateUtil.isTab(buff)) {
                    //タブ文字が含まれている場合、スペースに変換する
                    buff = buff.replaceAll("\t", " ");
                }
                impMdl.setScdTitle("[" + buff + "]");

            }
            break;

        //タイトル
        case TITLE:
            if (buff != null && ValidateUtil.isTab(buff)) {
                //タブ文字が含まれている場合、スペースに変換する
                buff = buff.replaceAll("\t", " ");
            }

            if (StringUtil.isNullZeroStringSpace(buff)) {
                // タイトルが無い場合、「予定あり」を入力
                GsMessage gsMsg = new GsMessage(reqMdl__);
                buff = gsMsg.getMessage("schedule.src.9"); // 予定あり
            }

            if (!StringUtil.isNullZeroString(impMdl.getScdTitle())) {
                impMdl.setScdTitle(impMdl.getScdTitle() + buff);
            } else {
                impMdl.setScdTitle(buff);
            }
            break;

        //メモ(内容)
        case MEMO:
            impMdl.setScdValue(buff);
            break;

        //コメント(備考)
        case COMMENT:
            impMdl.setScdBiko(buff);
            break;

        default:
            break;
        }
    }

    /**
     * [機 能] ファイルの読み込みを実行します。<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param file ファイル
     * @param encode 読み込み時の文字コード
     * @throws Exception 読込時例外
     */
    @Override
    public long readFile(File file, String encode) throws Exception {
        FileInputStream fStream = null;
        InputStreamReader isReader = null;
        BufferedReader bufferedreader = null;

        String nText = null;
        //行数
        long num = 0;
        try {
            //CSVファイルの読み込み
            fStream = new FileInputStream(file);
            isReader = new InputStreamReader(fStream, encode);
            bufferedreader = new BufferedReader(isReader);
            char[] cbuf = new char[1024];
            String tmp;
            int rcnt = 0;
            while (true) {
                rcnt = bufferedreader.read(cbuf, 0, 1024); // 1kb毎にファイル読み込み
                if (rcnt == -1) {
                    // 余った部分の読み込み
                    if (nText.length() > 0) {
                        num++;
                        processedLine(num, nText);
                    }
                    break; // 最後まで読み込み終わったので終了
                }

                // 読み込んだデータを前の文字列に連結
                StringBuilder sb = new StringBuilder();
                sb.append(cbuf, 0, rcnt);
                tmp = NullDefault.getString(nText, "").concat(sb.toString());

                int idxCrlf = 0;
                int offset  = 0;
                // 改行コードが無くなるまで読み込む
                while ((idxCrlf = tmp.indexOf(RCODE, offset)) != -1) {

                    String line = tmp.substring(0, idxCrlf); // 改行コードまで取得
                    offset = idxCrlf + RCODE.length();

                    // 改行コードまでの「"」の出現回数を取得
                    int ccnt = 0;
                    for (char c : line.toCharArray()) {
                        if (c == '\"') {
                            ccnt++;
                        }
                    }

                    if (ccnt % 2 == 0) {
                        // 「"」の出現回数が偶数の場合 → 行の終端
                        num++;
                        processedLine(num, line);
                        tmp = tmp.substring(offset); // 残り部分を読み込む
                        offset = 0;
                    }
                }
                nText = tmp;
            }
        } finally {
            if (fStream != null) {
                fStream.close();
            }
            if (isReader != null) {
                isReader.close();
            }
            if (bufferedreader != null) {
                bufferedreader.close();
            }
        }
        return num;
    }
}
