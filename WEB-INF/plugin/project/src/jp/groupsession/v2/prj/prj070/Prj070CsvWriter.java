package jp.groupsession.v2.prj.prj070;

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
 * <br>[機  能] TODO情報の一覧(CSV)を出力する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj070CsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Prj070CsvWriter.class);

    /** プロジェクト情報一覧ファイル名 */
    public static final String FILE_NAME = "todoList.csv";

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
    public Prj070CsvWriter(RequestModel reqMdl) {
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
        //プロジェクトID
        String textPrjId = gsMsg.getMessage("project.31");
        //プロジェクト名称
        String textPrjName = gsMsg.getMessage("project.40");
        //管理番号
        //String textEdit = gsMsg.getMessage(req, "cmn.edit");
        //カテゴリ
        String textCatagory = gsMsg.getMessage("cmn.label");
        //タイトル
        String textTitle = gsMsg.getMessage("cmn.title");
        //開始予定日付
        //String textEdit = gsMsg.getMessage(req, "cmn.edit");
        //期限日付
        //String textEdit = gsMsg.getMessage(req, "cmn.edit");
        //開始日付
        String textStartDate = gsMsg.getMessage("cmn.startdate");
        //終了日付
        String textEndDate = gsMsg.getMessage("cmn.end.date");
        //重要度
        String textImportance = gsMsg.getMessage("project.prj050.4");
        //進捗率
        String textRate = gsMsg.getMessage("project.src.34");
        //状態
        String textStatue = gsMsg.getMessage("cmn.status");
        //内容
        String textContent = gsMsg.getMessage("cmn.content");
        //担当
        String textCharge = gsMsg.getMessage("project.113");
        //管理番号
        String textManageNum = gsMsg.getMessage("project.127");
        //開始予定日付
        String textStartSchDate = gsMsg.getMessage("project.128");
        //期限日付
        String textLimitDate = gsMsg.getMessage("project.129");

        String strHeader =
            textPrjId + "," + textPrjName + "," + textManageNum + "," + textCatagory + ","
            + textTitle + "," + textStartSchDate + "," + textLimitDate + ","
            + textStartDate + "," + textEndDate + "," + textImportance
            + "," + textRate + "," + textStatue + "," + textContent + "," + textCharge;

        pw.println(strHeader);
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        Prj070CsvRecordListenerImpl rl = new Prj070CsvRecordListenerImpl(
                pw, con__, reqMdl__);
        Connection con = getCon();

        try {

            //プロジェクト情報リストを取得、CSVを生成する
            GsMessage gsMsg = new GsMessage(reqMdl__);
            ProjectSearchDao sDao = new ProjectSearchDao(con, gsMsg);
            sDao.createTodoList(searchModel__, rl);

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
