package jp.groupsession.v2.ip.ipk060;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ip.IpkValidateCsv;
import jp.groupsession.v2.ip.model.ValidateCheckModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] IPアドレスインポート 取込みファイルのチェックを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkCsvCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkCsvCheck.class);

    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** コネクション */
    private Connection con__ = null;
    /** ネットワークSID */
    private int netSid__;
    /** インポートモード */
    private String mode__;
    /** CSVファイル内のIPアドレスリスト */
    private ArrayList<String> ipadListCsv__;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** サブネットマスク */
    private String subnetMask__;
    /** リクエスト情報 */
    private RequestModel reqMdl__;
    /** リクエスト */
    private GsMessage gsMsg__;

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
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param error アクションエラー
     * @param con コネクション
     * @param netSid ネットワークSID
     * @param mode インポートモード
     * @param ipadListCsv CSVファイル内のIPアドレスリスト
     * @param reqMdl リクエスト情報
     */
    public IpkCsvCheck(ActionErrors error, Connection con, int netSid,
            String mode, ArrayList<String> ipadListCsv,
            RequestModel reqMdl) {
        setErrors(error);
        setCon(con);
        setNetSid(netSid);
        setMode(mode);
        setIpadListCsv(ipadListCsv);
        setReqMdl(reqMdl);
        gsMsg__ = new GsMessage(reqMdl__);

    }

    /**
     * <br>[機  能] CSVファイルのチェックを行なう
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param csvFile 入力ファイル名
     * @return ture:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
     public boolean isCsvDataOk(String csvFile) throws Exception {

         boolean ret = false;
         File file = new File(csvFile);
         if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT_X5)) {
             String eprefix = "inputFile.";
             String textCaptureFile = gsMsg__.getMessage("main.src.34");
             ActionMessage msg =
                 new ActionMessage("error.over.row.csvdata",
                         textCaptureFile,
                         String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT_X5));
             StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
             ret = true;
             return ret;
         }

         //ファイル読込み
         readFile(new File(csvFile), Encoding.WINDOWS_31J);
         log__.debug("有効データ件数==" + getCount());

         ret = isErrorFlg();

         //有効データ無し
         if (getCount() == 0) {
             String eprefix = "inputFile.";
             String msgText = gsMsg__.getMessage("main.src.34");
             ActionMessage msg =
                 new ActionMessage("search.notfound.data", msgText);
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
    protected void processedLine(long num, String lineStr)
    throws Exception {

        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }
        try {

            String textSelectFile = gsMsg__.getMessage("cmn.capture.file");
            String textKoumokusu = gsMsg__.getMessage("cmn.csv.number.items");

            int j = 0;
            String buff;
            String eprefix = "inputFile.";
            int ecnt = errors__.size();
            CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");
            ValidateCheckModel model = new ValidateCheckModel();
            log__.debug("項目数=" + stringTokenizer.length());
            if (stringTokenizer.length() != 9) {
                ActionMessage msg =
                    new ActionMessage(
                            "error.input.format.file",
                            textSelectFile,
                            textKoumokusu
                            + "(" + gsMsg__.getMessage("cmn.line",
                                                    new String[] {String.valueOf(num)})
                            + ")");
                StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
            } else {

                while (stringTokenizer.hasMoreTokens()) {
                    j++;
                    buff = stringTokenizer.nextToken();

                    //ネットワーク名
                    if (j == 1) {
                        errors__ = IpkValidateCsv.validateCsvNetName(
                                errors__, reqMdl__, buff, num);
                    }
                    //ネットワークアドレス
                    if (j == 2) {
                        model.setNetworkAddress(buff);
                        errors__ = IpkValidateCsv.validateCsvNetad(
                                errors__, reqMdl__, buff, num);
                    }
                    //サブネットマスク
                    if (j == 3) {
                        model.setSubnetMask(buff);
                        errors__ = IpkValidateCsv.validateCsvNetMask(
                                errors__, reqMdl__, buff, num);
                        setSubnetMask(buff);
                    }
                    //IPアドレス
                    if (j == 4) {
                        model.setSubnetMask(getSubnetMask());
                        model.setIpAddress(buff);
                        model.setMode(mode__);
                        model.setIpadListCsv(ipadListCsv__);
                        model.setNetSid(netSid__);
                        errors__ = IpkValidateCsv.validateCsvIpad(
                                errors__, reqMdl__, buff, num, con__, model);

                        ArrayList<String> ipadListCsv = new ArrayList<String>(ipadListCsv__);
                        ipadListCsv.add(buff);
                        setIpadListCsv(ipadListCsv);
                    }
                    //マシン名
                    if (j == 5) {
                        errors__ = IpkValidateCsv.validateCsvIadName(
                                errors__, reqMdl__, buff, num);
                    }
                    //使用区分
                    if (j == 6) {
                        errors__ = IpkValidateCsv.validateCsvUsekbn(
                                errors__, reqMdl__, buff, num);
                    }
                    //コメント
                    if (j == 8) {
                        errors__ = IpkValidateCsv.validateCsvIadMsg(
                                errors__, reqMdl__, buff, num);
                    }
                    //資産管理番号
                    if (j == 9) {
                        errors__ = IpkValidateCsv.validateCsvIadPrtMngNum(
                                errors__, reqMdl__, buff, num);
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
     * <p>netSid を取得します。
     * @return netSid
     */
    public int getNetSid() {
        return netSid__;
    }

    /**
     * <p>netSid をセットします。
     * @param netSid netSid
     */
    public void setNetSid(int netSid) {
        netSid__ = netSid;
    }

    /**
     * <p>mode を取得します。
     * @return mode
     */
    public String getMode() {
        return mode__;
    }

    /**
     * <p>mode をセットします。
     * @param mode mode
     */
    public void setMode(String mode) {
        mode__ = mode;
    }

    /**
     * <p>ipadListCsv を取得します。
     * @return ipadListCsv
     */
    public ArrayList<String> getIpadListCsv() {
        return ipadListCsv__;
    }

    /**
     * <p>ipadListCsv をセットします。
     * @param ipadListCsv ipadListCsv
     */
    public void setIpadListCsv(ArrayList<String> ipadListCsv) {
        ipadListCsv__ = ipadListCsv;
    }

    /**
     * <p>subnetMask を取得します。
     * @return subnetMask
     */
    public String getSubnetMask() {
        return subnetMask__;
    }

    /**
     * <p>subnetMask をセットします。
     * @param subnetMask subnetMask
     */
    public void setSubnetMask(String subnetMask) {
        subnetMask__ = subnetMask;
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
     * <p>gsMsg を取得します。
     * @return gsMsg
     */
    public GsMessage getGsMsg() {
        return gsMsg__;
    }

    /**
     * <p>gsMsg をセットします。
     * @param gsMsg gsMsg
     */
    public void setGsMsg(GsMessage gsMsg) {
        gsMsg__ = gsMsg;
    }
}