package jp.groupsession.v2.mem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.mem.mem050.Mem050DisplayModel;
import jp.groupsession.v2.mem.model.MemoLabelModel;

/**
 * <p>MEMO_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemoLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public MemoLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public MemoLabelDao(Connection con) {
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
            sql.addSql("drop table MEMO_LABEL");

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
            sql.addSql(" create table MEMO_LABEL (");
            sql.addSql("   MML_SID integer not null,");
            sql.addSql("   MML_NAME varchar(20) not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   MML_SORT integer not null,");
            sql.addSql("   MML_AUID integer not null,");
            sql.addSql("   MML_ADATE timestamp not null,");
            sql.addSql("   MML_EUID integer not null,");
            sql.addSql("   MML_EDATE timestamp not null,");
            sql.addSql("   primary key (MML_SID)");
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
     * <p>Insert MEMO_LABEL Data Bindding JavaBean
     * @param bean MEMO_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(MemoLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" MEMO_LABEL(");
            sql.addSql("   MML_SID,");
            sql.addSql("   MML_NAME,");
            sql.addSql("   USR_SID,");
            sql.addSql("   MML_SORT,");
            sql.addSql("   MML_AUID,");
            sql.addSql("   MML_ADATE,");
            sql.addSql("   MML_EUID,");
            sql.addSql("   MML_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getMmlSid());
            sql.addStrValue(bean.getMmlName());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getMmlSort());
            sql.addIntValue(bean.getMmlAuid());
            sql.addDateValue(bean.getMmlAdate());
            sql.addIntValue(bean.getMmlEuid());
            sql.addDateValue(bean.getMmlEdate());
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
     * <p>Update MEMO_LABEL Data Bindding JavaBean
     * @param bean MEMO_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(MemoLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_LABEL");
            sql.addSql(" set ");
            sql.addSql("   MML_NAME=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   MML_SORT=?,");
            sql.addSql("   MML_AUID=?,");
            sql.addSql("   MML_ADATE=?,");
            sql.addSql("   MML_EUID=?,");
            sql.addSql("   MML_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   MML_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getMmlName());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getMmlSort());
            sql.addIntValue(bean.getMmlAuid());
            sql.addDateValue(bean.getMmlAdate());
            sql.addIntValue(bean.getMmlEuid());
            sql.addDateValue(bean.getMmlEdate());
            //where
            sql.addIntValue(bean.getMmlSid());

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
     * <br>[機  能] ラベルの更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model MemoLabelModel
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateLabel(MemoLabelModel model) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_LABEL");
            sql.addSql(" set ");
            sql.addSql("   MML_NAME=?,");
            sql.addSql("   MML_EUID=?,");
            sql.addSql("   MML_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   MML_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(model.getMmlName());
            sql.addIntValue(model.getMmlEuid());
            sql.addDateValue(model.getMmlEdate());
            //where
            sql.addIntValue(model.getMmlSid());

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
     * <br>[機  能] 表示順序を入れ替える
     * <br>[解  説]
     * <br>[備  考]
     * @param mmlSid ラベルSID
     * @param mmlSort ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int mmlSid, int mmlSort)
            throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_LABEL");
            sql.addSql(" set");
            sql.addSql("   MML_SORT = ?");
            sql.addSql(" where");
            sql.addSql("   MML_SID = ?");

            sql.addIntValue(mmlSort);
            sql.addIntValue(mmlSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Select MEMO_LABEL All Data
     * @return List in MEMO_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<MemoLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<MemoLabelModel> ret = new ArrayList<MemoLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MML_SID,");
            sql.addSql("   MML_NAME,");
            sql.addSql("   USR_SID,");
            sql.addSql("   MML_SORT,");
            sql.addSql("   MML_AUID,");
            sql.addSql("   MML_ADATE,");
            sql.addSql("   MML_EUID,");
            sql.addSql("   MML_EDATE");
            sql.addSql(" from ");
            sql.addSql("   MEMO_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getMemoLabelFromRs(rs));
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
     * <p>Select MEMO_LABEL
     * @param mmlSid MML_SID
     * @return MEMO_LABELModel
     * @throws SQLException SQL実行例外
     */
    public MemoLabelModel select(int mmlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MemoLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MML_SID,");
            sql.addSql("   MML_NAME,");
            sql.addSql("   USR_SID,");
            sql.addSql("   MML_SORT,");
            sql.addSql("   MML_AUID,");
            sql.addSql("   MML_ADATE,");
            sql.addSql("   MML_EUID,");
            sql.addSql("   MML_EDATE");
            sql.addSql(" from");
            sql.addSql("   MEMO_LABEL");
            sql.addSql(" where ");
            sql.addSql("   MML_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(mmlSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getMemoLabelFromRs(rs);
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
     * <br>[機  能] ラベルの並び順の現在最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return ラベル最大値
     * @throws SQLException SQL実行時例外
     */
    public int getMaxSort(int userSid)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxNumber = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(MML_SORT) as MAX_SORT");
            sql.addSql(" from");
            sql.addSql("   MEMO_LABEL");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addIntValue(userSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxNumber = rs.getInt("MAX_SORT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return maxNumber;
    }

    /**
     * <br>[機 能]メモ一覧に設定するラベル情報を取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param targetSidList メモ一覧に表示するメモSIDのリスト
     * @return ラベル情報一覧
     * @throws SQLException SQL実行例外
     */
    public Map<Long,String> getMemoLabelList(int usrSid, List<Long>targetSidList)
                                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Long,String> map = new HashMap<Long, String>();
        int size = targetSidList.size();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MEMO_DATA.MEM_SID as MEM_SID,");
            sql.addSql("   MEMO_LABEL.MML_NAME as MML_NAME");
            sql.addSql(" from");
            sql.addSql("   MEMO_LABEL,");
            sql.addSql("   MEMO_BELONG_LABEL,");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" where");
            sql.addSql("   MEMO_LABEL.MML_SID = MEMO_BELONG_LABEL.MML_SID");
            if (size > 0) {
                sql.addSql(" and");
                sql.addSql("   MEMO_BELONG_LABEL.MEM_SID in (");
                for (int i = 0; i < size; i++) {
                    sql.addSql(" ?");
                    sql.addLongValue(targetSidList.get(i));
                    if (i != size -1) {
                        sql.addSql(",");
                    }
                }
                sql.addSql("   )");
            }
            sql.addSql(" and");
            sql.addSql("   MEMO_BELONG_LABEL.MEM_SID = MEMO_DATA.MEM_SID");
            sql.addSql(" and");
            sql.addSql("   MEMO_LABEL.USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while(rs.next()) {
                long key = rs.getLong("MEM_SID"); 
                String value = rs.getString("MML_NAME");
                if (map.get(key) != null ) {
                    map.put(key, map.get(key).concat(",").concat(value));
                } else {
                    map.put(key, value);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }

        return map;
    }

    /**
     * <br>[機  能] ラベル一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return ラベル一覧
     * @throws SQLException SQL実行例外
     */
    public List<MemoLabelModel> getLabelList(int usrSid) throws SQLException {

        List<MemoLabelModel> labelList = new ArrayList<MemoLabelModel>();
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MML_SID, MML_NAME");
            sql.addSql(" from");
            sql.addSql("   MEMO_LABEL");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   MML_SORT asc");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MemoLabelModel model = new MemoLabelModel();
                model.setMmlSid(rs.getInt("MML_SID"));
                model.setMmlName(rs.getString("MML_NAME"));
                labelList.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }

        return labelList;
    }

    /**
     * <br>[機  能] ラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return ラベル情報
     * @throws SQLException SQL実行時例外
     */
    public List<Mem050DisplayModel> getLabelNameList(int usrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Mem050DisplayModel> ret =
                new ArrayList<Mem050DisplayModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MML_SID, ");
            sql.addSql("   MML_NAME, ");
            sql.addSql("   MML_SORT ");
            sql.addSql(" from ");
            sql.addSql("   MEMO_LABEL ");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   MML_SORT ");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Mem050DisplayModel model = new Mem050DisplayModel();
                model.setMmlSid(rs.getInt("MML_SID"));
                model.setMmlName(rs.getString("MML_NAME"));
                model.setMmlSort(rs.getInt("MML_SORT"));
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
     * <br>[機  能] 指定されたラベルの情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid セッションユーザSID
     * @param mmlSid ラベルSID
     * @return MemoLabelModel
     * @throws SQLException SQL実行例外
     */
    public MemoLabelModel selectLabelData(int usrSid, int mmlSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        MemoLabelModel bean = new MemoLabelModel();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MML_NAME,");
            sql.addSql("   MML_SID");
            sql.addSql(" from");
            sql.addSql("   MEMO_LABEL");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   MML_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(mmlSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while(rs.next()) {
                bean.setMmlName(rs.getString("MML_NAME"));
                bean.setMmlSid(rs.getInt("MML_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }

        return bean;
    }

    /**
     * <br>[機  能] 対象ラベルの存在チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param mmlSid メモラベルSID
     * @param usrSid ユーザSID
     * @return 存在の有無
     * @throws SQLException SQL実行時例外
     */
    public boolean existLabel(int mmlSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(MML_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   MEMO_LABEL");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   MML_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(usrSid);
            sql.addIntValue(mmlSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <p>Delete MEMO_LABEL
     * @param mmlSid MML_SID
     * @throws SQLException SQL実行例外
     */
    public int delete(int mmlSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   MEMO_LABEL");
            sql.addSql(" where ");
            sql.addSql("   MML_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(mmlSid);

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
     * <p>Create MEMO_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created MemoLabelModel
     * @throws SQLException SQL実行例外
     */
    private MemoLabelModel __getMemoLabelFromRs(ResultSet rs) throws SQLException {
        MemoLabelModel bean = new MemoLabelModel();
        bean.setMmlSid(rs.getInt("MML_SID"));
        bean.setMmlName(rs.getString("MML_NAME"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMmlSort(rs.getInt("MML_SORT"));
        bean.setMmlAuid(rs.getInt("MML_AUID"));
        bean.setMmlAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MML_ADATE")));
        bean.setMmlEuid(rs.getInt("MML_EUID"));
        bean.setMmlEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MML_EDATE")));
        return bean;
    }

}
