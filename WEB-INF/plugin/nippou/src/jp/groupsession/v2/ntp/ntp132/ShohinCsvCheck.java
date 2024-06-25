package jp.groupsession.v2.ntp.ntp132;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 商品インポート画面の取込みファイルをチェックするクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ShohinCsvCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ShohinCsvCheck.class);

    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** コネクション */
    private Connection con__ = null;
    /** リクエスト */
    private HttpServletRequest req__ = null;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** グループID重複チェック用MAP */
    private HashMap<String, String> groupIdMap__;

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
     * <p>groupIdMap を取得します。
     * @return groupIdMap
     */
    public HashMap<String, String> getGroupIdMap() {
        return groupIdMap__;
    }

    /**
     * <p>groupIdMap をセットします。
     * @param groupIdMap groupIdMap
     */
    public void setGroupIdMap(HashMap<String, String> groupIdMap) {
        groupIdMap__ = groupIdMap;
    }

    /**
     * コンストラクタ
     * @param error アクションエラー
     * @param con コネクション
     * @param req リクエスト
     */
    public ShohinCsvCheck(
                  ActionErrors error,
                  Connection con,
                  HttpServletRequest req) {
        setErrors(error);
        setCon(con);
        setGroupIdMap(new HashMap<String, String>());
        setReq(req);
    }

    /**
     * <br>[機　能] CSVファイルのチェックを行なう
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param csvFile 入力ファイル名
     * @param req リクエスト
     * @return ture:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
     public boolean isCsvDataOk(String csvFile, HttpServletRequest req) throws Exception {

         boolean ret = false;
         GsMessage gsMsg = new GsMessage(req__);
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

         //ファイル読込み
         readFile(file, Encoding.WINDOWS_31J);
         log__.debug("有効データ件数==" + getCount());

         ret = isErrorFlg();

         //有効データ無し
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
    *
    * @param num 行番号
    * @param lineStr 行データ
    * @throws Exception csv読込時例外
    * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
    */
    protected void processedLine(long num, String lineStr) throws Exception {
        GsMessage gsMsg = new GsMessage();
        //取込みファイル
        String textCaptureFile = gsMsg.getMessage(req__, "cmn.capture.file");
        //CSV項目数
        String textCsvitems = gsMsg.getMessage(req__, "cmn.csv.number.items");
        //行目の
        String textLine = gsMsg.getMessage(req__, "cmn.line2", String.valueOf(num));
        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        try {

            int j = 0;
            String buff;
            String eprefix = "inputFile.";
            int ecnt = errors__.size();
            CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

            log__.debug("項目数=" + stringTokenizer.length());
            //4項目である必要がある
            if (stringTokenizer.length() < 2 || stringTokenizer.length() > 5) {
                ActionMessage msg =
                    new ActionMessage(
                            "error.input.format.file",
                            textCaptureFile,
                            textCsvitems + "(" + textLine + ")");
                StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
            } else {

                //グループID
                String textShohinCode = GSConstNippou.TEXT_SHOHIN_CODE;
                while (stringTokenizer.hasMoreTokens()) {
                    j++;
                    buff = stringTokenizer.nextToken();
                    //商品コード
                    if (j == 1) {
                        GSValidateNippou.validateCmnFieldText(errors__,
                                textLine + GSConstNippou.TEXT_SHOHIN_CODE,
                                buff,
                               "ntp130NhnCode",
                                GSConstNippou.MAX_LENGTH_SHOHIN_CODE,
                                true);
                        //CSVファイル内重複チェック
                        if (!StringUtil.isNullZeroString(buff)) {
                            if (groupIdMap__.containsKey(buff)) {
                                String dupIndex = (String) groupIdMap__.get(buff);
                                //行目の
                                String dupLine = gsMsg.getMessage(req__, "cmn.line2", dupIndex);

                                ActionMessage msg = new ActionMessage(
                                    "error.select.dup.list2",
                                    textLine + textShohinCode,
                                    dupLine + textShohinCode);
                                StrutsUtil.addMessage(
                                    errors__,
                                    msg,
                                    "shohincode." + num + "error.select.dup.list2");
                            } else {
                                groupIdMap__.put(buff, String.valueOf(num));
                            }
                        }
                    }
                    //商品名
                    if (j == 2) {
                        GSValidateNippou.validateCmnFieldText(errors__,
                                textLine + GSConstNippou.TEXT_SHOHIN_NAME,
                                buff,
                               "ntp130NhnName",
                                GSConstNippou.MAX_LENGTH_SHOHIN_NAME,
                                true);
                    }
                    //販売価格
                    if (j == 3) {
                        GSValidateNippou.validateCmnFieldTextNumber(errors__,
                                textLine + GSConstNippou.TEXT_PRICE_SALE,
                                buff.trim(),
                               "ntp130NhnPriceSale",
                                GSConstNippou.MAX_LENGTH_PRICE_SALE,
                                false);
                    }
                    //原価価格
                    if (j == 4) {
                        GSValidateNippou.validateCmnFieldTextNumber(errors__,
                                textLine + GSConstNippou.TEXT_PRICE_COST,
                                buff.trim(),
                               "ntp130NhnPriceCost",
                                GSConstNippou.MAX_LENGTH_PRICE_COST,
                                false);
                    }
                  //補足事項
                    if (j == 5) {
                        GSValidateNippou.validateCmnFieldText(errors__,
                                textLine + GSConstNippou.TEXT_HOSOKU,
                                buff,
                               "ntp130NhnHosoku",
                                GSConstNippou.MAX_LENGTH_HOSOKU,
                                false);
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
 * <p>req を取得します。
 * @return req
 */
public HttpServletRequest getReq() {
    return req__;
}

/**
 * <p>req をセットします。
 * @param req req
 */
public void setReq(HttpServletRequest req) {
    req__ = req;
}
}