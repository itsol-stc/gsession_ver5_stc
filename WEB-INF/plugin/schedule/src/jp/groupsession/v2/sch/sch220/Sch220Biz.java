package jp.groupsession.v2.sch.sch220;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.sch040.model.Sch040AttendModel;

/**
 * <br>[機  能] スケジュール 出欠確認一覧POPUPのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch220Biz {

    /** リクエスト情報 */
    public RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch220Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>初期表示画面情報を取得します
     * @param paramMdl Sch210ParamModel
     * @param con コネクション
     * @param schSid スケジュールSID
     * @return Sch010Form アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public Sch220ParamModel getInitData(
            Sch220ParamModel paramMdl, Connection con, String schSid)
    throws SQLException {

        ArrayList<Sch040AttendModel> ansList =
                getAttendAnsListAll(NullDefault.getInt(schSid, 0), con);
        paramMdl.setSch040AttendAnsList(ansList);

        return paramMdl;
    }

    /**
     * <br>[機  能] 指定したスケジュールグループSIDの出欠確認回答一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param schSid スケジュールSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return 出欠確認回答一覧
     */
    public ArrayList<Sch040AttendModel> getAttendAnsListAll(int schSid, Connection con)
            throws SQLException {

        ArrayList<Sch040AttendModel> ret = new ArrayList<Sch040AttendModel>();
        Sch040AttendModel attendMdl = null;
        SchDataDao dao = new SchDataDao(con);
        ArrayList<SchDataModel> schList = dao.selectAttendAnsSch(schSid);

        for (SchDataModel schMdl : schList) {
            attendMdl = new Sch040AttendModel();
            String strEditDate =
                    UDateUtil.getSlashYYMD(schMdl.getScdEdate())
                    + " "
                    + UDateUtil.getSeparateHM(schMdl.getScdEdate());
            attendMdl.setAttendAnsDate(strEditDate);
            attendMdl.setAttendAnsUsrSid(schMdl.getScdUsrSid());
            attendMdl.setAttendAnsUsrName(schMdl.getScdUserName());
            attendMdl.setAttendAnsKbn(schMdl.getScdAttendAns());
            attendMdl.setAttendAnsComment(schMdl.getScdAttendComment());
            ret.add(attendMdl);
        }
        return ret;
    }
}
