package jp.groupsession.v2.sch.sch100;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] スケジュール情報一覧のCSV出力を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchCsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchCsvWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** スケジュール情報一覧ファイル名 */
    public static final String FILE_NAME = "scheduleList.csv";

    /** 検索条件 */
    private ScheduleListSearchModel searchModel__ = null;

    /** 実行者SID */
    private int sessionUserSid__;
    /** リクエスト */
    private HttpServletRequest req__ = null;
    /** フォーム */
    private Sch100Form form__ = null;

    /**
     * <p>Set HttpServletRequest
     * @param req HttpServletRequest
     * @param form フォーム
     */
    public SchCsvWriter(HttpServletRequest req, Sch100Form form) {
        req__ = req;
        form__ = form;
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
//            "SCD_SID,ユーザSID,氏名,グループ区分,ユーザ区分,開始日付,終了日付,背景区分,タイトル,"
//            + "内容,備考,公開フラグ,登録者ID,登録者氏名,更新者ID,更新者氏名";

        if (form__.getSch100CsvOutField() == null) {
            return;
        }

        GsMessage gsMsg = new GsMessage();

        //1行分出力
        StringBuilder sb = new StringBuilder();
        int fieldCnt = form__.getSch100CsvOutField().length;
        log__.debug("***ヘッダ出力フィールド数:" + fieldCnt);

        for (int i = 0; i < form__.getSch100CsvOutField().length; i++) {

            switch(Integer.parseInt(form__.getSch100CsvOutField()[i])) {

            case 1:
                //ユーザID
                sb.append(gsMsg.getMessage(req__, "cmn.user.id"));
                break;
            case 2:
                //グループID
                sb.append(gsMsg.getMessage(req__, "cmn.group.id"));
                break;
            case 3:
                //氏名
                sb.append(gsMsg.getMessage(req__, "cmn.name"));
                break;
            case 4:
                //開始日付
                sb.append(gsMsg.getMessage(req__, "cmn.startdate"));
                break;
            case 5:
                //開始時刻
                sb.append(gsMsg.getMessage(req__, "cmn.starttime"));
                break;
            case 6:
                //終了日付
                sb.append(gsMsg.getMessage(req__, "cmn.end.date"));
                break;
            case 7:
                //終了時刻
                sb.append(gsMsg.getMessage(req__, "cmn.endtime"));
                break;
            case 8:
                //タイトル
                sb.append(gsMsg.getMessage(req__, "cmn.title"));
                break;
            case 9:
                //タイトル色
                sb.append(gsMsg.getMessage(req__, "schedule.10"));
                break;
            case 10:
                //内容
                sb.append(gsMsg.getMessage(req__, "cmn.content"));
                break;
            case 11:
                //備考
                sb.append(gsMsg.getMessage(req__, "cmn.memo"));
                break;
            case 12:
                //編集権限
                sb.append(gsMsg.getMessage(req__, "cmn.edit.permissions"));
                break;
            case 13:
                //公開区分
                sb.append(gsMsg.getMessage(req__, "cmn.public.kbn"));
                break;
            case 14:
                //時間指定区分
                sb.append(gsMsg.getMessage(req__, "schedule.timed.segments"));
                break;
            case 15:
                //登録者氏名
                sb.append(gsMsg.getMessage(req__, "schedule.132"));
                break;
            case 16:
                //更新者氏名
                sb.append(gsMsg.getMessage(req__, "schedule.133"));
                break;
            default:
                break;
            }

            if (i < form__.getSch100CsvOutField().length - 1) {
                sb.append(",");
            }
        }

        pw.println(sb.toString());
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        SchCsvRecordListenerIppanImpl rl = new SchCsvRecordListenerIppanImpl(pw, form__);
        Connection con = getCon();

        try {
            ScheduleSearchDao sDao = new ScheduleSearchDao(con);
            //詳細
            sDao.createSchduleForCsv(searchModel__, sessionUserSid__, rl, req__);
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
    public ScheduleListSearchModel getSearchModel() {
        return searchModel__;
    }

    /**
     * <p>searchModel をセットします。
     * @param searchModel searchModel
     */
    public void setSearchModel(ScheduleListSearchModel searchModel) {
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

}