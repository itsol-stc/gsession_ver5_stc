package jp.groupsession.v2.ntp.ntp060;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 案件情報(CSV)を出力する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp060CsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp060CsvWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** 案件情報一覧ファイル名 */
    public static final String FILE_NAME = "ankenList.csv";

    /** 検索条件 */
    Ntp060SearchModel searchModel__ = null;

    /** 実行者SID */
    private int sessionUserSid__;

    /** リクエストモデル */
    private RequestModel reqMdl__;

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
        String strHeader =
            "案件コード,案件名,案件詳細,企業コード,業種コード,プロセスコード,見込度,見積金額,提出日,受注金額,受注日,商談結果,顧客源泉コード,状態";
        pw.println(strHeader);
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        Ntp060CsvRecordListenerIppanImpl rl = new Ntp060CsvRecordListenerIppanImpl(pw);
        Connection con = getCon();

        try {
            Ntp060AnkenDao ankenDao = new Ntp060AnkenDao(con);
            //詳細
            ankenDao.createAnkenForCsv(searchModel__, sessionUserSid__, rl, reqMdl__);
        } catch (SQLException e) {
            log__.error("SQLの実行に失敗", e);
            throw new CSVException("SQLの実行に失敗", e);
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
     * @return searchModel
     */
    public Ntp060SearchModel getSearchModel() {
        return searchModel__;
    }

    /**
     * @param searchModel 設定する searchModel
     */
    public void setSearchModel(Ntp060SearchModel searchModel) {
        searchModel__ = searchModel;
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