package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sch.model.MyViewListBelongModel;
import jp.groupsession.v2.sch.model.SchMyviewlistBelongModel;

/**
 * <p>SCH_MYVIEWLIST_BELONG Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchMyviewlistBelongDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchMyviewlistBelongDao.class);

    /**
     * <p>Default Constructor
     */
    public SchMyviewlistBelongDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchMyviewlistBelongDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table SCH_MYVIEWLIST_BELONG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table SCH_MYVIEWLIST_BELONG (");
            sql.addSql("   SMY_SID integer not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   GRP_SID integer not null,");
            sql.addSql("   SMV_ORDER integer not null,");
            sql.addSql("   primary key (SMY_SID,USR_SID,GRP_SID,SMV_ORDER)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert SCH_MYVIEWLIST_BELONG Data Bindding JavaBean
     * @param bean SCH_MYVIEWLIST_BELONG Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchMyviewlistBelongModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_MYVIEWLIST_BELONG(");
            sql.addSql("   SMY_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   SMV_ORDER");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSmySid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getSmvOrder());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update SCH_MYVIEWLIST_BELONG Data Bindding JavaBean
     * @param bean SCH_MYVIEWLIST_BELONG Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchMyviewlistBelongModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_MYVIEWLIST_BELONG");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   SMY_SID=?");
            sql.addSql(" and");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   GRP_SID=?");
            sql.addSql(" and");
            sql.addSql("   SMV_ORDER=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getSmySid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getGrpSid());
            sql.addIntValue(bean.getSmvOrder());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    
    /**
     * <p>Select SCH_MYVIEWLIST_BELONG All Data
     * @return List in SCH_MYVIEWLIST_BELONGModel
     * @throws SQLException SQL実行例外
     */
    public List<SchMyviewlistBelongModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchMyviewlistBelongModel> ret = new ArrayList<SchMyviewlistBelongModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SMY_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   SMV_ORDER");
            sql.addSql(" from ");
            sql.addSql("   SCH_MYVIEWLIST_BELONG");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchMyviewlistBelongFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した表示リストの所属ユーザ，所属グループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smySid 表示設定SID
     * @return 所属ユーザ，所属グループリスト
     * @throws SQLException SQL実行時例外
     */
    public List<SchMyviewlistBelongModel> select(int smySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchMyviewlistBelongModel> ret = new ArrayList<SchMyviewlistBelongModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SMY_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   SMV_ORDER");
            sql.addSql(" from ");
            sql.addSql("   SCH_MYVIEWLIST_BELONG");
            sql.addSql(" where ");
            sql.addSql("   SMY_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   SMV_ORDER");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smySid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchMyviewlistBelongFromRs(rs));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した表示リストの所属ユーザ，所属グループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smySid 表示設定SID
     * @return 所属ユーザ，所属グループ情報
     * @throws SQLException SQL実行時例外
     */
    public List<MyViewListBelongModel> getBelongDataList(int smySid) throws SQLException {
        return getBelongDataList(smySid, null, null, -1, -1);
    }

    /**
     * <br>[機  能] 指定した表示リストの所属ユーザ，所属グループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smySid 表示設定SID
     * @param List<Integer> 閲覧不可グループSIDリスト
     * @param List<Integer> 閲覧不可ユーザSIDリスト
     * @return 所属ユーザ，所属グループ情報
     * @throws SQLException SQL実行時例外
     */
    public List<MyViewListBelongModel> getBelongDataList(
        int smySid,
        List<Integer> notAccessUserList,
        List<Integer> notAccessGrpList) throws SQLException {
        return getBelongDataList(smySid, notAccessUserList, notAccessGrpList, -1, -1);
    }

    /**
     * <br>[機  能] 指定した表示リストの所属ユーザ，所属グループ情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smySid 表示設定SID
     * @param List<Integer> 閲覧不可ユーザSIDリスト
     * @param List<Integer> 閲覧不可グループSIDリスト
     * @param limit リミット
     * @param offset オフセット
     * @return 所属ユーザ，所属グループ情報
     * @throws SQLException SQL実行時例外
     */
    public List<MyViewListBelongModel> getBelongDataList(
        int smySid,
        List<Integer> notAccessUserList,
        List<Integer> notAccessGrpList,
        int limit,
        int offset) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<MyViewListBelongModel> ret = new ArrayList<MyViewListBelongModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   coalesce(SCH_MYVIEWLIST_BELONG.USR_SID, -1) as USR_SID,");
            sql.addSql("   coalesce(SCH_MYVIEWLIST_BELONG.GRP_SID, -1) as GRP_SID");
            sql.addSql(" from");
            __createSql(sql, notAccessUserList, notAccessGrpList);
            sql.addSql(" order by");
            sql.addSql("   SMV_ORDER");

            if (offset >= 0 && limit >= 1) {
                sql.setPagingValue(offset, limit);
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smySid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MyViewListBelongModel model = new MyViewListBelongModel();
                model.setSmySid(smySid);
                model.setGrpSid(rs.getInt("GRP_SID"));
                model.setGrpName(rs.getString("GRP_NAME"));
                model.setUsrSid(rs.getInt("USR_SID"));
                model.setUsrNameSei(rs.getString("USI_SEI"));
                model.setUsrNameMei(rs.getString("USI_MEI"));
                ret.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した表示リストの所属ユーザ，所属グループ情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smySid 表示設定SID
     * @param List<Integer> 閲覧不可グループSIDリスト
     * @param List<Integer> 閲覧不可ユーザSIDリスト
     * @return 所属ユーザ，所属グループ情報の件数
     * @throws SQLException SQL実行時例外
     */
    public int countBelongDataList(
        int smySid,
        List<Integer> notAccessUserList,
        List<Integer> notAccessGrpList) throws SQLException {
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("select count(*) as CNT from");
            __createSql(sql, notAccessUserList, notAccessGrpList);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smySid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;

    }

    /**
     * <br>[機  能] 指定した表示リストの所属情報を削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param smySid 表示設定SID
     * @return delete count
     * @throws SQLException SQL実行時例外
     */
    public int delete(int smySid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_MYVIEWLIST_BELONG");
            sql.addSql(" where ");
            sql.addSql("   SMY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smySid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定した表示リストの所属ユーザ，所属グループ情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param smySid 表示設定SID
     * @param List<Integer> 閲覧不可グループSIDリスト
     * @param List<Integer> 閲覧不可ユーザSIDリスト
     * @return 所属ユーザ，所属グループ情報の件数
     * @throws SQLException SQL実行時例外
     */
    private void __createSql(
        SqlBuffer sql,
        List<Integer> notAccessUserList,
        List<Integer> notAccessGrpList) {

        sql.addSql("   SCH_MYVIEWLIST_BELONG");
        sql.addSql("   left join");
        sql.addSql("     CMN_GROUPM");
        sql.addSql("   on");
        sql.addSql("     SCH_MYVIEWLIST_BELONG.GRP_SID = CMN_GROUPM.GRP_SID");
        sql.addSql("   left join");
        sql.addSql("     CMN_USRM");
        sql.addSql("   on");
        sql.addSql("     SCH_MYVIEWLIST_BELONG.USR_SID = CMN_USRM.USR_SID");
        sql.addSql("   left join");
        sql.addSql("     CMN_USRM_INF");
        sql.addSql("   on");
        sql.addSql("     CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql(" where");
        sql.addSql("   SMY_SID = ?");
        sql.addSql(" and");
        sql.addSql("   (");
        sql.addSql("     CMN_GROUPM.GRP_JKBN = ?");
        sql.addSql("   or");
        sql.addSql("     CMN_USRM.USR_JKBN = ?");
        sql.addSql("   )");
        
        //アクセス不可ユーザを除く
        if (notAccessUserList != null && notAccessUserList.size() > 0) {
            List<Integer> exeList = new ArrayList<Integer>();
            Iterator<Integer> itr = notAccessUserList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500 && itr.hasNext()) {
                    continue;
                }
                sql.addSql(" and");
                sql.addSql("   SCH_MYVIEWLIST_BELONG.USR_SID not in");
                sql.addSql("     (");
                String notAccessUsers = String.join(",", exeList.stream()
                                                            .map(u -> u.toString())
                                                            .collect(Collectors.toList()));
                sql.addSql(notAccessUsers);
                sql.addSql("     )");
                exeList.clear();
            }
        }

        //アクセス不可グループを除く
        if (notAccessGrpList != null && notAccessGrpList.size() > 0) {
            List<Integer> exeList = new ArrayList<Integer>();
            Iterator<Integer> itr = notAccessGrpList.iterator();
            while (itr.hasNext()) {
                exeList.add(itr.next());
                if (exeList.size() < 500 && itr.hasNext()) {
                    continue;
                }
                sql.addSql(" and");
                sql.addSql("   SCH_MYVIEWLIST_BELONG.GRP_SID not in");
                sql.addSql("     (");
                String notAccessGrps = String.join(",", exeList.stream()
                                                            .map(g -> g.toString())
                                                            .collect(Collectors.toList()));
                sql.addSql(notAccessGrps);
                sql.addSql("     )");
                exeList.clear();
            }
        }
    }

    /**
     * <p>Create SCH_MYVIEWLIST_BELONG Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchMyviewlistBelongModel
     * @throws SQLException SQL実行例外
     */
    private SchMyviewlistBelongModel __getSchMyviewlistBelongFromRs(ResultSet rs)
    throws SQLException {
        SchMyviewlistBelongModel bean = new SchMyviewlistBelongModel();
        bean.setSmySid(rs.getInt("SMY_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setSmvOrder(rs.getInt("SMV_ORDER"));
        return bean;
    }
}
