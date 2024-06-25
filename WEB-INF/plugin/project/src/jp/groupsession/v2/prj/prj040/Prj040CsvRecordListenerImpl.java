package jp.groupsession.v2.prj.prj040;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

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
 * <br>[機  能] プロジェクト情報のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj040CsvRecordListenerImpl implements CSVRecordListener {

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
     * @param pw PrintWriter
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Prj040CsvRecordListenerImpl(PrintWriter pw, Connection con, RequestModel reqMdl) {
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

        GsMessage gsMsg = new GsMessage(reqMdl__);

        ProjectItemModel csvModel = (ProjectItemModel) model;
        PrjCommonBiz prjCmnBiz = new PrjCommonBiz(con__, gsMsg, reqMdl__);
        //1行分出力
        StringBuilder sb = new StringBuilder();

        //プロジェクトID
        sb.append(CsvEncode.encString(csvModel.getProjectId()));
        sb.append(",");

        //プロジェクト名称
        sb.append(CsvEncode.encString(csvModel.getProjectName()));
        sb.append(",");

        //プロジェクト略称
        sb.append(CsvEncode.encString(csvModel.getProjectRyakuName()));
        sb.append(",");

        //予算
        sb.append(CsvEncode.encString(prjCmnBiz.getYosanStr(csvModel.getYosan())));
        sb.append(",");

        //公開区分
        sb.append(CsvEncode.encString(prjCmnBiz.getKoukaiKbnName(csvModel.getKoukaiKbn())));
        sb.append(",");

        //開始日付
        sb.append(CsvEncode.encString(UDateUtil.getSlashYYMD(csvModel.getStartDate())));
        sb.append(",");

        //終了日付
        sb.append(CsvEncode.encString(UDateUtil.getSlashYYMD(csvModel.getEndDate())));
        sb.append(",");

        //進捗率
        if (csvModel.getPrjMyKbn() == GSConstProject.KBN_MY_PRJ_DEF) {
            sb.append(CsvEncode.encString(String.valueOf(csvModel.getRate()) + "%"));
        } else {
            sb.append("");
        }
        sb.append(",");

        //状態
        if (csvModel.getPrjMyKbn() == GSConstProject.KBN_MY_PRJ_DEF) {
            sb.append(CsvEncode.encString(csvModel.getStatusName()));
        } else {
            sb.append("");
        }
        sb.append(",");

        //目標・目的
        sb.append(CsvEncode.encString(csvModel.getMokuhyou()));
        sb.append(",");

        //内容
        sb.append(CsvEncode.encString(csvModel.getNaiyou()));
        sb.append(",");

        List<UserModel> memberList = csvModel.getMemberList();
        List<UserModel> adminList = new ArrayList<UserModel>();
        //管理者
        for (UserModel uMdl : memberList) {
            if (uMdl.getAdminKbn() == GSConstProject.KBN_POWER_ADMIN) {
                adminList.add(uMdl);
            }
        }

        int count = 0;
        for (UserModel uMdl : adminList) {
            sb.append(CsvEncode.encString(uMdl.getSei() + " " + uMdl.getMei()));
            if (count != adminList.size() - 1) {
                sb.append(":");
            }
            count++;
        }
        sb.append(",");

        //所属メンバー
        count = 0;
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
