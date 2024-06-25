package jp.groupsession.v2.tcd.csv;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.dao.TcdTcdataDao;
import jp.groupsession.v2.tcd.model.TcdCsvSearchModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] タイムカード情報一覧のCSV出力を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdCsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdCsvWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** タイムカード情報一覧ファイル名 */
    public static final String FILE_NAME = "timecardList.csv";

    /** 検索条件 */
    private TcdCsvSearchModel searchModel__ = null;

    /** 実行者SID */
    private int sessionUserSid__;

    /** ファイル名付加情報 */
    private String fileYm__ = "";

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public TcdCsvWriter(RequestModel reqMdl) {
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
        String fileName = fileYm__ + FILE_NAME;
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
        String header = gsMsg.getMessage("tcd.122");

        String strHeader = header;
        pw.println(strHeader);
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        Connection con = getCon();

        try {
            TimecardBiz tcBiz = new TimecardBiz(reqMdl__);
            TcdAdmConfModel admConf = tcBiz.getTcdAdmConfModel(sessionUserSid__, con);
            TcdCsvRecordListenerImpl rl
                = new TcdCsvRecordListenerImpl(pw, admConf.getTacInterval(), reqMdl__);

            TcdTcdataDao tcDao = new TcdTcdataDao(con);
            //詳細
            tcDao.createTimecardForCsv(searchModel__, sessionUserSid__, rl);
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
     * <p>searchModel を取得します。
     * @return searchModel
     */
    public TcdCsvSearchModel getSearchModel() {
        return searchModel__;
    }

    /**
     * <p>searchModel をセットします。
     * @param searchModel searchModel
     */
    public void setSearchModel(TcdCsvSearchModel searchModel) {
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
     * @return fileYm
     */
    public String getFileYm() {
        return fileYm__;
    }

    /**
     * @param fileYm セットする fileYm
     */
    public void setFileYm(String fileYm) {
        fileYm__ = fileYm;
    }

}