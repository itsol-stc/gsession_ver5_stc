package jp.groupsession.v2.man.man440;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] サイボウズLive インポートファイルのチェックを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractCybCsvImport extends AbstractCsvRecordReader {

    /** エラー行存在フラグ */
    protected boolean errorFlg__ = false;
    /** コネクション */
    protected Connection con__ = null;
    /** アクションエラー */
    protected ActionErrors errors__ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl__ = null;
    /** 有効データカウント */
    protected int count__ = 0;

    /** 選択グループSID */
    protected int grpSid__ = -1;

    /** 実行モード CSVデータチェック */
    public static final int MODE_CHECK = 0;
    /** 実行モード CSVデータインポート */
    public static final int MODE_IMPORT = 1;

    // -------------------------------
    //  クラス内でのみ使用する変数
    // -------------------------------
    /** 実行モード*/
    protected int mode__;
    /** 採番コントローラ */
    protected MlCountMtController cntCon__ = null;
    /** セッションユーザ */
    protected int sessionUser__;
    /** システム日付 */
    protected UDate sysUd__;

    /**
     * @return errors を戻します。
     */
    public ActionErrors getErrors() {
        return errors__;
    }

    /**
     * @param errors 設定する errors。
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
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

    /**
     * @return con を戻します。
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * @param con 設定する con。
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * @return errorFlg を戻します。
     */
    public boolean isErrorFlg() {
        return errorFlg__;
    }

    /**
     * @param errorFlg 設定する errorFlg。
     */
    public void setErrorFlg(boolean errorFlg) {
        errorFlg__ = errorFlg;
    }

    /**
     * @return count を戻します。
     */
    public int getCount() {
        return count__;
    }

    /**
     * @param count 設定する count。
     */
    public void setCount(int count) {
        count__ = count;
    }


    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>grpSid__ をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }


    /**
     * <br>[機 能] コンストラクタ
     * <br>[解 説]
     * <br>[備 考]
     * @param error アクションエラー
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public AbstractCybCsvImport(ActionErrors error, RequestModel reqMdl,
                             Connection con) {
        setErrors(error);
        setReqMdl(reqMdl);
        setCon(con);
    }

    /**
     * <br>[機 能] CSVファイルのチェックを行なう
     * <br>[解 説]
     * <br>[備 考]
     * @param csvFile 入力ファイル名
     * @return true:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
    public boolean isCsvDataOk(String csvFile) throws Exception {
        boolean ret = false;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        // モード指定
        mode__ = MODE_CHECK;

        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        File file = new File(csvFile);

        if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT)) {
            String eprefix = "inputFile.";
            ActionMessage msg =
                new ActionMessage("error.over.row.csvdata",
                        textCaptureFile,
                        String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT));
            StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
            ret = true;
            return ret;
        }

        // インポート個別にチェックする
        __validateImportData();

        // エラーが無い場合のみファイル解析チェック
        if (errors__.size() == 0) {
            // ファイル読込み
            readFile(file, Encoding.WINDOWS_31J);
            //log__.debug("有効データ件数==" + getCount());

            ret = isErrorFlg();

            //有効データ無し
            if (getCount() == 0) {

                String eprefix = "inputFile.";
                ActionMessage msg =
                    new ActionMessage("search.notfound.data", textCaptureFile);
                StrutsUtil.addMessage(errors__, msg, eprefix + "search.notfound.data");
                ret = true;
            }
        }

        return ret;
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @return 取得件数
     * @throws  Exception   実行時例外
     */
    public long importCsv(String tmpFileDir)
        throws Exception {

        // モード指定
        mode__ = MODE_IMPORT;

        //採番コントローラー取得
        cntCon__ = GroupSession.getResourceManager().getCountController(reqMdl__);

        // 更新日付(現在日時)取得
        sysUd__ = new UDate();

        // ログインユーザSID取得
        BaseUserModel buMdl = reqMdl__.getSmodel();
        if (buMdl != null) {
            sessionUser__ = buMdl.getUsrsid();
        }

        //テンポラリディレクトリにあるファイル名称を取得
        String csvFile = this.getCsvFilePath(tmpFileDir);

        //ファイル取込
        long num = readFile(new File(csvFile), Encoding.WINDOWS_31J);
        if (num > 0) {
            num--; // ヘッダ行分を除外
        }

        //登録・登録予定内容を設定
        return num;
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @return userInfList__
     * @throws  Exception   実行時例外
     */
    public String getCsvFilePath(String tmpFileDir)
        throws Exception {

        //テンポラリディレクトリにあるファイル名称を取得
        String saveFileName = "";

        List<String> fileList = IOTools.getFileNames(tmpFileDir);
        for (int i = 0; i < fileList.size(); i++) {
            //ファイル名を取得
            String fileName = fileList.get(i);
            if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                continue;
            }

            //オブジェクトファイルを取得
            ObjectFile objFile = new ObjectFile(tmpFileDir, fileName);
            Object fObj = objFile.load();
            if (fObj == null) {
                continue;
            }
            Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
            saveFileName = fMdl.getSaveFileName();
        }

        return tmpFileDir + saveFileName;
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

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        //処理モード
        if (mode__ == MODE_CHECK) {
            // データチェック
            __check(num, lineStr);
        } else if (mode__ == MODE_IMPORT) {
            // データインポート
            __import(num, lineStr);
        }
    }

    /**
     * <br>[機  能] インポート画面毎に設定するチェック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @throws Exception csv読込時例外
     */
    protected abstract void __validateImportData() throws Exception;

    /**
     * <br>[機  能] csvファイル一行のチェック処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    protected abstract void __check(long num, String lineStr) throws Exception;

    /**
     * <br>[機  能] csvファイル一行のインポート処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    protected abstract void __import(long num, String lineStr) throws Exception;

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

    /**
     * <p>年月日の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param ymdhm 年月日時分
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     */
    protected void __isOkDateTime(ActionErrors errors, String ymdhm,
                                    String targetName, String targetJp) {
        ActionMessage msg;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (StringUtil.isNullZeroString(ymdhm)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(errors__, msg, targetJp + "error.input.required.text");
        } else {
            ArrayList<String> list = StringUtil.split(" ", ymdhm);

            //行目の
            String textYyyyMmDd = gsMsg.getMessage("cmn.format.date2");
            //yyyy/mm/dd形式入力
            if (list.size() < 2) {
                msg = new ActionMessage("error.input.comp.text",
                        targetName,
                        textYyyyMmDd);
                StrutsUtil.addMessage(errors__, msg, targetJp
                                      + "error.input.comp.text");
            } else {
                // 日時形式のチェック
                ActionErrors checkErrors = new ActionErrors();
                UDate udate = __isOkDate(checkErrors, list.get(0),
                        targetName, targetJp);
                if (udate != null) {
                    __isOkTime(checkErrors, list.get(1), null,
                            targetName, targetJp);
                }
                // 日付、時刻でエラーがあった場合、フォーマットエラーとして出力
                if (checkErrors.size() > 0) {
                    msg = new ActionMessage("error.input.format.text", targetName);
                    StrutsUtil.addMessage(errors__, msg, targetJp + "error.input.format.text");
                }
            }
        }
    }

    /**
     * <p>年月日の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param ymd 年月日
     * @param targetName チェック対象名
     * @param targetJp チェック対象名(日本語)
     * @return ActionErrors
     */
    protected UDate __isOkDate(ActionErrors errors, String ymd,
                              String targetName, String targetJp) {

        ActionMessage msg;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        UDate udate = null;

        if (StringUtil.isNullZeroString(ymd)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(errors, msg, targetJp + "error.input.required.text");
        } else {
            ArrayList<String> list = StringUtil.split("/", ymd);

            //行目の
            String textYyyyMmDd = gsMsg.getMessage("cmn.format.date2");
            //yyyy/mm/dd形式入力
            if (list.size() != 3) {
                msg = new ActionMessage("error.input.comp.text",
                        targetName,
                        textYyyyMmDd);
                StrutsUtil.addMessage(errors, msg, targetJp
                        + "error.input.comp.text");

            } else {
                try {
                    int iBYear  = Integer.parseInt(list.get(0));
                    int iBMonth = Integer.parseInt(list.get(1));
                    int iBDay   = Integer.parseInt(list.get(2));

                    //論理チェック
                    UDate date = new UDate();
                    date.setDate(iBYear, iBMonth, iBDay);
                    if (date.getYear() != iBYear
                     || date.getMonth() != iBMonth
                     || date.getIntDay() != iBDay) {
                        msg = new ActionMessage("error.input.notfound.date", targetName);
                        StrutsUtil.addMessage(errors, msg, targetJp
                                + "error.input.notfound.date");
                    } else {
                        date.setZeroHhMmSs(); // この時点では時間指定なし
                        udate = date;
                    }
                } catch (NumberFormatException e) {
                    //log__.debug("年月日CSV入力エラー");
                    msg = new ActionMessage("error.input.notfound.date", targetName);
                    StrutsUtil.addMessage(errors, msg, targetJp
                            + "error.input.notfound.date");
                }
            }
        }

        return udate;
    }

    /**
     * <p>時刻の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param time 時刻(HH:MM)
     * @param udate チェック済み日付
     * @param targetName チェック対象名(日本語)
     * @param targetJp チェック対象名
     * @return true:時間指定あり / false:時間指定なし
     */
    protected boolean __isOkTime(ActionErrors errors,
            String time, UDate udate, String targetName, String targetJp) {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        boolean errorFlg = false;

        if (StringUtil.isNullZeroString(time)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", targetName);
            StrutsUtil.addMessage(errors, msg, targetJp + "error.input.required.text");
        } else {
            ArrayList<String> list = StringUtil.split(":", time);
            if (list.size() < 2) {
                //hh:mm形式 かチェック(実際のサイボウズLiveのエクスポートデータは hh:mm:ss形式)
                //※メッセージはサイボウズLive移行用のものを使用
                String textFormatTime = gsMsg.getMessage("main.man460.4");
                msg = new ActionMessage("error.input.comp.text",
                        targetName,
                        textFormatTime);
                StrutsUtil.addMessage(errors, msg, targetJp
                        + "error.input.comp.text");
            } else {
                try {
                    int iBHour = Integer.parseInt(list.get(0));
                    int iBMin  = Integer.parseInt(list.get(1));

                    //論理チェック
                    if (iBHour < 0 || 23 < iBHour) {
                        msg = new ActionMessage("error.input.notvalidate.data", targetName);
                        StrutsUtil.addMessage(errors, msg, targetJp
                                + "error.input.notvalidate.data");
                        errorFlg = true;
                    }
                    if (iBMin < 0 || 59 < iBMin) {
                        msg = new ActionMessage("error.input.notvalidate.data", targetName);
                        StrutsUtil.addMessage(errors, msg, targetJp
                                + "error.input.notvalidate.data");
                        errorFlg = true;
                    }


                    if (!errorFlg && udate != null) {
                        udate.setZeroHhMmSs();
                        udate.setHour(iBHour);
                        udate.setMinute(iBMin);
                    }

                } catch (Exception e) {
                    //log__.debug("時刻CSV入力エラー");
                    msg = new ActionMessage("error.input.notvalidate.data", targetName);
                    StrutsUtil.addMessage(errors, msg, targetJp
                            + "error.input.notvalidate.data");
                }
            }
        }
        return errorFlg;
    }


    /**
     * <br>[機  能] 文字列から日時情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param buff 日時文字列(Y/M/D h:m)
     * @return 日時情報
     */
    protected UDate __getDateFromStr(String buff) {
        UDate date = new UDate();

        if (!StringUtil.isNullZeroString(buff)) {
            ArrayList<String> list = StringUtil.split(" ", buff);

            if (list.size() >= 2) {
                String ymd = list.get(0);
                String hs  = list.get(1);

                ArrayList<String> dlist = StringUtil.split("/", ymd);
                ArrayList<String> tlist = StringUtil.split(":", hs);
                if (dlist.size() >= 3 && tlist.size() >= 2) {
                    int year   = new Integer(((String) dlist.get(0))).intValue();
                    int month  = new Integer(((String) dlist.get(1))).intValue();
                    int day    = new Integer(((String) dlist.get(2))).intValue();
                    int hour   = new Integer(((String) tlist.get(0))).intValue();
                    int minute = new Integer(((String) tlist.get(1))).intValue();

                    date.setDate(StringUtil.getStrYyyyMmDd(year, month, day));
                    date.setHour(hour);
                    date.setMinute(minute);
                    date.setSecond(0);
                }
            }
        }

        return date;
    }

    /**
     * <br>[機  能] 文字列からコメント一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param buff データ文字列
     * @return コメント一覧
     */
    protected ArrayList<CybCsvCommentModel> __getCommentFromStr(String buff)  {

        ArrayList<CybCsvCommentModel> ret = new ArrayList<CybCsvCommentModel>();

        // 区切り文字で分割
        ArrayList<String> dataList = StringUtil.split(
                "--------------------------------------------------", buff);
        if (dataList != null) {
            // 以下のパターンで記載されたフォーマット形式を抽出
            // 「[番号]: [ユーザ名] [投稿日付(Y/M/D)] ([曜日]) [投稿時間(h:m)]」
            String regex =
               "^[0-9]+: (.+) ([0-9]{4}/[0-9]{1,2}/[0-9]{1,2}) \\(.\\) ([0-9]{1,2}:[0-9]{1,2})";
            Pattern p = Pattern.compile(regex);
            for (String data: dataList) {
                if (!StringUtil.isNullZeroStringSpace(data)) {
                    int offset = "\r\n".length(); // 先頭の改行分をずらす
                    int index = data.indexOf("\r\n", offset);
                    String datHead = null;
                    String datBody = null;
                    if (index >= offset) { // 1行目(タイトル行)を取得
                        datHead = data.substring(offset, index);
                        datBody = data.substring(index + "\r\n".length());
                    }

                    if (!StringUtil.isNullZeroStringSpace(datHead)) {
                        Matcher m = p.matcher(datHead);
                        int mCnt = m.groupCount();
                        if (mCnt >= 3 && m.find()) {
                            String name    = m.group(1);
                            String dateStr = m.group(2);
                            String timeStr = m.group(3);
                            CybCsvCommentModel mdl = new CybCsvCommentModel();
                            UDate date = __getDateFromStr(dateStr + " " + timeStr);
                            mdl.setName(name);
                            mdl.setDate(date);
                            mdl.setValue(datBody);
                            ret.add(mdl);
                        }
                    }
                }
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 文字列をユーザ名として変換
     * <br>[解  説]
     * <br>[備  考]
     * @param buff データ文字列
     * @return ユーザ名
     */
    protected String _getUserNameFromBuffer(String buff)  {
        if (ValidateUtil.isTab(buff)) {
            buff = buff.replaceAll("\t", " "); //タブ文字が含まれている場合、半角スペースに変換
        }
        return buff.trim(); // 先頭 or 末尾の半角スペースは除去
    }
}
