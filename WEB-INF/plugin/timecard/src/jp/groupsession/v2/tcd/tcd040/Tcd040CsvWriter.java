package jp.groupsession.v2.tcd.tcd040;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CsvEncode;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;
import jp.groupsession.v2.tcd.model.TcdManagerModel;
import jp.groupsession.v2.tcd.model.TcdTotalValueModel;

/**
 * <br>[機  能] タイムカード集計情報(CSV)を出力するクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd040CsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Tcd040CsvWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** タイムカード集計情報一覧ファイル名 */
    public static final String FILE_NAME = "timecardManager.csv";

    /** 実行者SID */
    private int sessionUserSid__;

    /** タイムカードデータ一覧 */
    private ArrayList<TcdManagerModel> tcdMngList__;

    /** 合計 */
    private TcdTotalValueModel tcdTotalData__ = null;

    /** 平均 */
    private TcdTotalValueModel tcdAverageData__ = null;

    /** 休日区分 */
    private List<TcdHolidayInfModel> holDataList__;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Tcd040CsvWriter(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(Connection con, String csvPath)
    throws CSVException {

        setCon(con);

        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
        String fileName = FILE_NAME;
        String fileFullPath = IOTools.replaceFileSep(csvPath + File.separator + fileName);
        log__.debug("CSVファイルのパス = " + fileFullPath);

        //出力初期セット
        setCsvPath(fileFullPath);

        log__.debug("開始");
        write();
        log__.debug("終了");
    }

    /**
     * <br>[機  能] CSV生成 値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    public void create(PrintWriter pw) throws CSVException {

        //ヘッダ
        __writeHeader(pw);

        //明細
        __writeItem(pw);
    }

    /**
     * <p>ヘッダ部分を生成します。
     * @param pw PrintWriter
     */
    private void __writeHeader(PrintWriter pw) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("tcd.145");
        String strHeader = msg;

        for (TcdHolidayInfModel holData : holDataList__ ) {
            strHeader += "," + holData.getThiName();
        }
        pw.println(strHeader);
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {
        //1行分出力
        StringBuilder sb = null;
        for (TcdManagerModel tcdMngMdl : tcdMngList__) {
            //1行分
            sb = new StringBuilder();
            //氏名
            sb.append(CsvEncode.encString(tcdMngMdl.getUserName()));
            sb.append(",");
            //社員/職員番号
            sb.append(CsvEncode.encString(tcdMngMdl.getUserSyainNo()));
            sb.append(",");
            //集計開始日付
            UDate startDate = tcdMngMdl.getStartDate();
            sb.append(CsvEncode.encString(UDateUtil.getSlashYYMD(startDate)));
            sb.append(",");
            //集計終了日付
            UDate endDate = tcdMngMdl.getEndDate();
            sb.append(CsvEncode.encString(UDateUtil.getSlashYYMD(endDate)));
            sb.append(",");

            //日数部分の出力
            __setTcdTime(tcdMngMdl, sb);

            //出力
            pw.println(sb.toString());
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String heikin = gsMsg.getMessage("tcd.tcd040.02");
        String goukei = gsMsg.getMessage("tcd.tcd040.11");

        //平均の出力
        if (tcdAverageData__ != null) {
            sb = new StringBuilder();
            sb.append(heikin);
            sb.append(",,,,");
            __setTcdTime(tcdAverageData__, sb);
            pw.println(sb.toString());
        }

        //合計の出力
        if (tcdTotalData__ != null) {
            sb = new StringBuilder();
            sb.append(goukei);
            sb.append(",,,,");
            __setTcdTime(tcdTotalData__, sb);
            pw.println(sb.toString());
        }

    }

    /**
     * <br>[機  能] 明細部の時間、日数部分を設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param model タイムカードデータ
     * @param sb StringBuffer
     */
    private void __setTcdTime(TcdTotalValueModel model, StringBuilder sb) {
        //稼動日数
        sb.append(CsvEncode.encString(model.getKadoDaysStr()));
        sb.append(",");
        //稼働時間
        //稼動日数
        sb.append(CsvEncode.encString(model.getKadoHoursStr()));
        sb.append(",");
        //残業日数
        sb.append(CsvEncode.encString(model.getZangyoDaysStr()));
        sb.append(",");
        //残業時間
        sb.append(CsvEncode.encString(model.getZangyoHoursStr()));
        sb.append(",");
        //深夜日数
        sb.append(CsvEncode.encString(model.getSinyaDaysStr()));
        sb.append(",");
        //深夜時間
        sb.append(CsvEncode.encString(model.getSinyaHoursStr()));
        sb.append(",");
        //休出日数
        sb.append(CsvEncode.encString(model.getKyusyutuDaysStr()));
        sb.append(",");
        //休出時間
        sb.append(CsvEncode.encString(model.getKyusyutuHoursStr()));
        sb.append(",");
        //遅刻
        sb.append(CsvEncode.encString(model.getChikokuTimesStr()));
        sb.append(",");
        //早退
        sb.append(CsvEncode.encString(model.getSoutaiTimesStr()));

        for (TcdHolidayInfModel holData : holDataList__ ) {
            sb.append(",");
            sb.append(CsvEncode.encString(model.getHolDaysStr(holData.getThiSid())));
        }
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <p>sessionUserSid を取得します。
     * @return sessionUserSid
     */
    public int getSessionUserSid() {
        return sessionUserSid__;
    }

    /**
     * <p>sessionUserSid をセットします。
     * @param sessionUserSid sessionUserSid
     */
    public void setSessionUserSid(int sessionUserSid) {
        sessionUserSid__ = sessionUserSid;
    }

    /**
     * <p>tcdMngList を取得します。
     * @return tcdMngList
     */
    public ArrayList<TcdManagerModel> getTcdMngList() {
        return tcdMngList__;
    }
    /**
     * <p>tcdMngList をセットします。
     * @param tcdMngList tcdMngList
     */
    public void setTcdMngList(ArrayList<TcdManagerModel> tcdMngList) {
        tcdMngList__ = tcdMngList;
    }

    /**
     * <p>tcdAverageData を取得します。
     * @return tcdAverageData
     */
    public TcdTotalValueModel getTcdAverageData() {
        return tcdAverageData__;
    }
    /**
     * <p>tcdAverageData をセットします。
     * @param tcdAverageData tcdAverageData
     */
    public void setTcdAverageData(TcdTotalValueModel tcdAverageData) {
        tcdAverageData__ = tcdAverageData;
    }
    /**
     * <p>holDataList を取得します。
     * @return holDataList
     * @see jp.groupsession.v2.tcd.tcd040.Tcd040CsvWriter#holDataList__
     */
    public List<TcdHolidayInfModel> getHolDataList() {
        return holDataList__;
    }

    /**
     * <p>holDataList をセットします。
     * @param holDataList holDataList
     * @see jp.groupsession.v2.tcd.tcd040.Tcd040CsvWriter#holDataList__
     */
    public void setHolDataList(List<TcdHolidayInfModel> holDataList) {
        holDataList__ = holDataList;
    }

    /**
     * <p>tcdTotalData を取得します。
     * @return tcdTotalData
     */
    public TcdTotalValueModel getTcdTotalData() {
        return tcdTotalData__;
    }
    /**
     * <p>tcdTotalData をセットします。
     * @param tcdTotalData tcdTotalData
     */
    public void setTcdTotalData(TcdTotalValueModel tcdTotalData) {
        tcdTotalData__ = tcdTotalData;
    }
}