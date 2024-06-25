package jp.groupsession.v2.prj.prj070;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.prj.GSConstProject;
import jp.groupsession.v2.prj.PrjCommonBiz;
import jp.groupsession.v2.prj.model.ProjectItemModel;
import jp.groupsession.v2.prj.model.UserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] TODO情報のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj070CsvRecordListenerImpl implements CSVRecordListener {

    /** PrintWriter */
    private PrintWriter pw__ = null;
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     * @param pw PrintWriter
     */
    public Prj070CsvRecordListenerImpl(PrintWriter pw, Connection con, RequestModel reqMdl) {
        pw__ = pw;
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model) throws CSVException {

        ProjectItemModel csvModel = (ProjectItemModel) model;

        //1行分出力
        StringBuilder sb = new StringBuilder();

        //プロジェクトID
        sb.append(CsvEncode.encString(csvModel.getProjectId()));
        sb.append(",");

        //プロジェクト名称
        sb.append(CsvEncode.encString(csvModel.getProjectName()));
        sb.append(",");

        //管理番号
        sb.append(CsvEncode.encString(
                StringUtil.toDecFormat(csvModel.getKanriNo(), GSConstProject.KANRI_NO_FORMAT)));
        sb.append(",");

        //カテゴリ
        sb.append(CsvEncode.encString(csvModel.getCategory()));
        sb.append(",");

        //タイトル
        sb.append(CsvEncode.encString(csvModel.getTodoTitle()));
        sb.append(",");

        //開始予定日付
        sb.append(CsvEncode.encString(UDateUtil.getSlashYYMD(csvModel.getStartDate())));
        sb.append(",");

        //期限日付
        sb.append(CsvEncode.encString(UDateUtil.getSlashYYMD(csvModel.getEndDate())));
        sb.append(",");

        //開始日付
        sb.append(CsvEncode.encString(UDateUtil.getSlashYYMD(csvModel.getStartJissekiDate())));
        sb.append(",");

        //終了日付
        sb.append(CsvEncode.encString(UDateUtil.getSlashYYMD(csvModel.getEndJissekiDate())));
        sb.append(",");
        GsMessage gsMsg = new GsMessage(reqMdl__);
        PrjCommonBiz prjCmnBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        //重要度
        sb.append(CsvEncode.encString(prjCmnBiz.getWeightName(csvModel.getJuyo())));
        sb.append(",");

        //進捗率
        sb.append(CsvEncode.encString(String.valueOf(csvModel.getRate()) + "%"));
        sb.append(",");

        //状態
        sb.append(CsvEncode.encString(csvModel.getStatusName()));
        sb.append(",");

        //内容
        sb.append(CsvEncode.encString(csvModel.getNaiyou()));
        sb.append(",");

        List<UserModel> memberList = csvModel.getMemberList();
        //担当メンバー
        int count = 0;
        for (UserModel uMdl : memberList) {
            sb.append(CsvEncode.encString(uMdl.getSei() + " " + uMdl.getMei()));
            if (count != memberList.size() - 1) {
                sb.append(":");
            }
            count++;
        }

        pw__.println(sb.toString());
    }

}
