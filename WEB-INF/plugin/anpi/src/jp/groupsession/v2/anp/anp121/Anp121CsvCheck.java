package jp.groupsession.v2.anp.anp121;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSValidateUserCsv;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 管理者設定・緊急連絡先インポートでのCSVファイルをチェッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class Anp121CsvCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp121CsvCheck.class);

    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト */
    private RequestModel reqMdl__ = null;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** モード */
    private int mode__ = 0;
    /** 有効データカウント */
    private int count__ = 0;
    /** ログインID重複チェック用MAP */
    private HashMap<String, String> loginIdMap__;

    /**
     * <p>エラー行存在フラグを取得します
     * @return errorFlg を戻します。
     */
    public boolean isErrorFlg() {
        return errorFlg__;
    }

    /**
     * <p>エラー行存在フラグを設定します
     * @param errorFlg 設定する errorFlg。
     */
    public void setErrorFlg(boolean errorFlg) {
        errorFlg__ = errorFlg;
    }

    /**
     * <p>コネクションを取得します
     * @return con を戻します。
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>コネクションを設定します
     * @param con 設定する con。
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <p>アクションエラーを取得します
     * @return errors を戻します。
     */
    public ActionErrors getErrors() {
        return errors__;
    }

    /**
     * <p>アクションエラーを設定します
     * @param errors 設定する errors。
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
    }

    /**
     * <p>モードを取得します
     * @return mode を戻します。
     */
    public int getMode() {
        return mode__;
    }

    /**
     * <p>モードを設定します
     * @param mode 設定する mode。
     */
    public void setMode(int mode) {
        mode__ = mode;
    }

    /**
     * <p>有効データカウントを取得します
     * @return count を戻します。
     */
    public int getCount() {
        return count__;
    }

    /**
     * <p>有効データカウントを設定します
     * @param count 設定する count。
     */
    public void setCount(int count) {
        count__ = count;
    }

    /**
     * <p>ログインID重複チェック用MAPを取得します。
     * @return loginIdMap
     */
    public HashMap<String, String> getLoginIdMap() {
        return loginIdMap__;
    }

    /**
     * <p>ログインID重複チェック用MAPを設定します。
     * @param loginIdMap loginIdMap
     */
    public void setLoginIdMap(HashMap<String, String> loginIdMap) {
        loginIdMap__ = loginIdMap;
    }


    /**
     * コンストラクタ
     * @param error アクションエラー
     * @param con   コネクション
     * @param req   リクエスト
     * @param reqMdl リクエストモデル
     */
     public Anp121CsvCheck(ActionErrors error,
                           Connection con,
                           HttpServletRequest req,
                           RequestModel reqMdl) {

        setErrors(error);
        setCon(con);
//        setReq(req);
        reqMdl__ = reqMdl;
        setLoginIdMap(new HashMap<String, String>());
    }

    /**
     * <br>[機  能] CSVファイルの有効データチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  csvFile 入力ファイル名
     * @return ture:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
     public boolean isCsvDataOk(String csvFile) throws Exception {

         boolean ret = false;
         GsMessage gsMsg = new GsMessage(reqMdl__);

         //取込みファイル
         String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
         File file = new File(csvFile);
         if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT_X5)) {
             String eprefix = "inputFile.";
             ActionMessage msg =
                 new ActionMessage("error.over.row.csvdata",
                         textCaptureFile,
                         String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT_X5));
             StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
             ret = true;
             return ret;
         }

         //ファイル読込み
         readFile(file, Encoding.WINDOWS_31J);
         log__.debug("有効データ件数 = " + getCount());

         //エラー行存在フラグ
         ret = isErrorFlg();

         //有効データ数
         if (getCount() == 0) {
             String eprefix = "inputFile.";
             ActionMessage msg =
                 new ActionMessage("search.notfound.data", textCaptureFile);
             StrutsUtil.addMessage(errors__, msg, eprefix + "search.notfound.data");
             ret = true;
         }

         return ret;
     }

   /**
    * <br>[機  能] csvファイル一行の処理
    * <br>[解  説]
    * <br>[備  考]
    * @param num        行番号
    * @param lineStr    行データ
    * @throws Exception csv読込時例外
    * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
    */
    protected void processedLine(long num, String lineStr) throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //取込みファイル
        String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
        //CSV項目数
        String textCsvitems = gsMsg.getMessage("cmn.csv.number.items");
        //行目
        String textLine = gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)});

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        try {
            int item = 0;
            String buff;
            String eprefix = "inputFile.";
            int ecnt = errors__.size();
            CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

            //項目数チェック
            log__.debug("項目数=" + stringTokenizer.length());
            if (stringTokenizer.length() != GSConstAnpi.IMPORT_CSV_ITEMCOUNT) {
                ActionMessage msg = new ActionMessage("error.input.format.file",
                                                       textCaptureFile,
                                                       textCsvitems + "(" + textLine + ")");
                StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");

            } else {

                //各データのチェック
                while (stringTokenizer.hasMoreTokens()) {
                    item++;
                    buff = stringTokenizer.nextToken();

                    //ユーザログインID
                    if (item == 1) {
                        __validateCsvLoginId(errors__, buff, num);
                    }

                    //緊急連絡先：メールアドレス
                    if (item == 2) {
                        __validateCsvMail(errors__, buff, num);
                    }

                    //緊急連絡先：電話番号
                    if (item == 3) {
                        __validateCsvTel(errors__, buff, num);
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
     * <p>ログインIDの入力チェックを行う(CSV用)
     * @param errors  ActionErrors
     * @param loginID ログインID
     * @param line    行数
     * @throws SQLException SQL実行例外
     */
    private void __validateCsvLoginId(ActionErrors errors,
                                      String loginID,
                                      long line) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        CmnUsrmDao usrmDao = new CmnUsrmDao(con__);
        GSValidateUserCsv gsValidateUserCsv = new GSValidateUserCsv(reqMdl__);

        //ユーザID
        String textUserId = gsMsg.getMessage("cmn.user.id");
        //「行目の」
        String textLine = gsMsg.getMessage("cmn.line2", new String[]{String.valueOf(line)});

        //ログインIDの文字列チェック
        gsValidateUserCsv.validateCsvUserId(errors__, loginID, line);

        if (!loginID.equals("")) {
          //ログインＩＤが存在するかチェック
            if (!usrmDao.existLoginidEdit(-1, loginID)) {
                ActionMessage msg = new ActionMessage("search.notfound.tdfkcode",
                                                      textLine + textUserId);
                StrutsUtil.addMessage(errors__, msg,
                                      "userid." + line + "search.notfound.tdfkcode");
           }

            //CSVファイル内重複チェック
            if (!StringUtil.isNullZeroString(loginID)) {
                if (loginIdMap__.containsKey(loginID)) {
                    String dupIndex = (String) loginIdMap__.get(loginID);
                    //行目の
                    String dupLine = gsMsg.getMessage("cmn.line2", new String[]{dupIndex});
                    ActionMessage msg = new ActionMessage("error.select.dup.list2",
                                                           textLine + textUserId,
                                                           dupLine + textUserId);
                    StrutsUtil.addMessage(errors__, msg,
                                         "userid." + line + "error.select.dup.list2");

                } else {
                    loginIdMap__.put(loginID, String.valueOf(line));
                }
            }
        }
    }

    /**
     * <p>メールアドレスの入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param mail   メールアドレス
     * @param line   行数
     */
    private void __validateCsvMail(ActionErrors errors,
                                   String mail,
                                   long line) {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //エラーメッセージ用文字列を作成
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[]{ String.valueOf(line)});
        String eprefix = line + "mail.";
        String text = textLine + gsMsg.getMessage("cmn.mailaddress");

        if (!StringUtil.isNullZeroString(mail)) {

            if (mail.length() > GSConstUser.MAX_LENGTH_MAIL) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                                        text,
                                        GSConstUser.MAX_LENGTH_MAIL);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else {

                //メールフォーマットチェック
                if (!GSValidateUtil.isMailFormat(mail)) {
                    msg = new ActionMessage("error.input.format.text", text);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
                }
            }
        }
    }

    /**
     * <p>電話番号の入力チェックを行う(CSV用)
     * @param errors ActionErrors
     * @param tel 電話番号
     * @param line 行数
     */
    private void __validateCsvTel(ActionErrors errors,
                                  String tel,
                                  long line) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //エラーメッセージ用文字列を作成
        //行目の
        String textLine = gsMsg.getMessage("cmn.line2", new String[]{String.valueOf(line)});
        String eprefix = line + "tel.";
        String text = textLine + gsMsg.getMessage("cmn.tel");;

        if (!StringUtil.isNullZeroString(tel)) {

            if (tel.length() > GSConstUser.MAX_LENGTH_TEL) {
                //MAX桁チェック
                msg = new ActionMessage("error.input.length.text", text,
                                        GSConstUser.MAX_LENGTH_TEL);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

            } else {
                //電話番号フォーマットチェック
                if (!GSValidateUtil.isTel(tel)) {
                    msg = new ActionMessage("error.input.format.text", text);
                    StrutsUtil.addMessage(errors, msg, eprefix + "error.input.format.text");
                }
            }
        }
    }
}