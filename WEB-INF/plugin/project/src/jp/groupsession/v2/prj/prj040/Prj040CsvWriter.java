package jp.groupsession.v2.prj.prj040;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.dao.ProjectSearchDao;
import jp.groupsession.v2.prj.model.ProjectSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] プロジェクト情報一覧(CSV)を出力する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj040CsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj040CsvWriter.class);

    /** プロジェクト情報一覧ファイル名 */
    public static final String FILE_NAME = "projectList.csv";

    /** 検索条件 */
    private ProjectSearchModel searchModel__ = null;
    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <p>Set Connection
     * @param reqMdl RequestModel
     */
    public Prj040CsvWriter(RequestModel reqMdl) {
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

//        String strHeader =
//            "プロジェクトID,プロジェクト名称,プロジェクト略称,予算,公開区分,開始日付,終了日付,"
//          + "進捗率,状態,目標・目的,内容,管理者,所属メンバー";

        StringBuilder sb = new StringBuilder();
        GsMessage gsMsg = new GsMessage(reqMdl__);

        //プロジェクトID
        sb.append(gsMsg.getMessage("project.31"));
        sb.append(",");
        //プロジェクト名称
        sb.append(gsMsg.getMessage("project.40"));
        sb.append(",");
        //プロジェクト略称
        sb.append(gsMsg.getMessage("project.41"));
        sb.append(",");
        //予算
        sb.append(gsMsg.getMessage("project.10"));
        sb.append(",");
        //公開区分
        sb.append(gsMsg.getMessage("cmn.public.kbn"));
        sb.append(",");
        //開始日付
        sb.append(gsMsg.getMessage("cmn.startdate"));
        sb.append(",");
        //終了日付
        sb.append(gsMsg.getMessage("cmn.end.date"));
        sb.append(",");
        //進捗率
        sb.append(gsMsg.getMessage("project.src.34"));
        sb.append(",");
        //状態
        sb.append(gsMsg.getMessage("cmn.status"));
        sb.append(",");
        //目標・目的
        sb.append(gsMsg.getMessage("project.21"));
        sb.append(",");
        //内容
        sb.append(gsMsg.getMessage("cmn.content"));
        sb.append(",");
        //管理者
        sb.append(gsMsg.getMessage("cmn.admin"));
        sb.append(",");
        //所属メンバー
        sb.append(gsMsg.getMessage("cmn.squad"));

        pw.println(sb.toString());
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        Prj040CsvRecordListenerImpl rl =
                         new Prj040CsvRecordListenerImpl(pw, con__, reqMdl__);
        Connection con = getCon();

        try {

            //プロジェクト情報リストを取得、CSVを生成する
            GsMessage gsMsg = new GsMessage(reqMdl__);
            ProjectSearchDao sDao = new ProjectSearchDao(con, gsMsg);
            sDao.createProjectList(searchModel__, rl);

        } catch (SQLException e) {
            log__.error("SQLの実行に失敗", e);
            throw new CSVException("SQLの実行に失敗", e);
        }
    }

    /**
     * <p>searchModel を取得します。
     * @return searchModel
     */
    public ProjectSearchModel getSearchModel() {
        return searchModel__;
    }

    /**
     * <p>searchModel をセットします。
     * @param searchModel searchModel
     */
    public void setSearchModel(ProjectSearchModel searchModel) {
        searchModel__ = searchModel;
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

}
