package jp.groupsession.v2.sch.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;

/**
 * <br>[機  能] スケジュールプラグインのグループに関する共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchGroupBiz {
    /**
     * <br>[機  能] 指定したユーザがアクセス可能なグループの一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param sadCrange 共有範囲
     * @return グループ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getAccessGrpList(Connection con, int userSid, int sadCrange)
    throws SQLException {
        List<Integer> grpSidList = new ArrayList<Integer>();

        CmnGroupmDao grpDao = new CmnGroupmDao(con);
        List<CmnGroupmModel> grpDataList = grpDao.selectSuvivers();

        SchDao schDao = new SchDao(con);
        if (sadCrange == GSConstSchedule.CRANGE_SHARE_ALL) {
            //全員で共有
            List<Integer> notAccessGrpList = schDao.getNotAccessGrpList(userSid);
            int grpSid = 0;
            for (CmnGroupmModel grpData : grpDataList) {
                grpSid = grpData.getGrpSid();
                if (notAccessGrpList.indexOf(grpSid) < 0) {
                    grpSidList.add(grpSid);
                }
            }
        } else {
            //所属グループのみで共有
            List<CmnGroupmModel> belongGrpDataList = grpDao.selectBelongGroupList(userSid);
            List<Integer> belongGrpSidList = new ArrayList<Integer>();
            for (CmnGroupmModel belongGrpData : belongGrpDataList) {
                belongGrpSidList.add(belongGrpData.getGrpSid());
            }
            List<Integer> accessGrpList = schDao.getAccessGrpList(userSid);

            int grpSid = 0;
            for (CmnGroupmModel grpData : grpDataList) {
                grpSid = grpData.getGrpSid();
                if (belongGrpSidList.indexOf(grpSid) >= 0
                || accessGrpList.indexOf(grpSid) >= 0) {
                    grpSidList.add(grpSid);
                }
            }
        }
        return grpSidList;
    }

    /**
     * <br>[機  能] グループ情報一覧を取得する(コンボボックス用)
     * <br>[解  説] 指定したユーザが属するグループの一覧を取得する
     * <br>         ユーザSID < 0 の場合は全グループの一覧を取得する
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return グループ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<GroupModel> getGroupCombList(Connection con, int userSid)
    throws SQLException {

        int level = 1;
        String space = "";
        ArrayList<GroupModel> groupList = new ArrayList<GroupModel>();
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

        GroupDao dao = new GroupDao(con);
        ArrayList<GroupModel> gpList = null;
        if (sortMdl.getCscGroupSkbn() == GSConst.GROUPCMB_SKBN_SET) {
            gpList = dao.getGroupTree(sortMdl, false);
        } else {
            gpList = dao.getGroupList(sortMdl);
        }

        List<Integer> belongList = new ArrayList<Integer>();
        List<Integer> accessGrpList = new ArrayList<Integer>();
        if (userSid >= 0) {
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            belongList = belongDao.selectUserBelongGroupSid(userSid);

            SchDao schDao = new SchDao(con);
            accessGrpList = schDao.getAccessGrpList(userSid);
        }

        GroupBiz grpBiz = new GroupBiz();
        if (gpList != null) {
            for (GroupModel gpMdl : gpList) {
                if (belongList.isEmpty()
                || belongList.indexOf(gpMdl.getGroupSid()) >= 0
                || accessGrpList.contains(gpMdl.getGroupSid())) {
                    level = gpMdl.getClassLevel();
                    String gpName = gpMdl.getGroupName();
                    space = grpBiz.getCombSpace(level);
                    gpMdl.setGroupName(space + gpName);
                    groupList.add(gpMdl);
                }
            }
        }

        return groupList;
    }
}
