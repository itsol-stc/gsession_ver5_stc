package jp.groupsession.v2.mem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.mem010.Mem010SearchModel;
import jp.groupsession.v2.mem.model.MemoDataModel;

/**
 * <p>MEMO_DATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemoDataDao.class);

    /**
     * <p>Default Constructor
     */
    public MemoDataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public MemoDataDao(Connection con) {
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
            sql.addSql("drop table MEMO_DATA");

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
            sql.addSql(" create table MEMO_DATA (");
            sql.addSql("   MEM_SID bigint not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   MMD_CONTENT varchar(1000) not null,");
            sql.addSql("   MMD_DEL_CONF integer not null,");
            sql.addSql("   MMD_AUID integer not null,");
            sql.addSql("   MMD_ADATE timestamp not null,");
            sql.addSql("   MMD_EUID integer not null,");
            sql.addSql("   MMD_EDATE timestamp not null,");
            sql.addSql("   primary key (MEM_SID)");
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
     * <p>Insert MEMO_DATA Data Bindding JavaBean
     * @param bean MEMO_DATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(MemoDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" MEMO_DATA(");
            sql.addSql("   MEM_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   MMD_CONTENT,");
            sql.addSql("   MMD_DEL_CONF,");
            sql.addSql("   MMD_AUID,");
            sql.addSql("   MMD_ADATE,");
            sql.addSql("   MMD_EUID,");
            sql.addSql("   MMD_EDATE");
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
            sql.addLongValue(bean.getMemSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMmdContent());
            sql.addIntValue(bean.getMmdDelConf());
            sql.addIntValue(bean.getMmdAuid());
            sql.addDateValue(bean.getMmdAdate());
            sql.addIntValue(bean.getMmdEuid());
            sql.addDateValue(bean.getMmdEdate());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update MEMO_DATA Data Bindding JavaBean
     * @param bean MEMO_DATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(MemoDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   MMD_CONTENT=?,");
            sql.addSql("   MMD_DEL_CONF=?,");
            sql.addSql("   MMD_AUID=?,");
            sql.addSql("   MMD_ADATE=?,");
            sql.addSql("   MMD_EUID=?,");
            sql.addSql("   MMD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getMmdContent());
            sql.addIntValue(bean.getMmdDelConf());
            sql.addIntValue(bean.getMmdAuid());
            sql.addDateValue(bean.getMmdAdate());
            sql.addIntValue(bean.getMmdEuid());
            sql.addDateValue(bean.getMmdEdate());
            sql.addLongValue(bean.getMemSid());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select MEMO_DATA All Data
     * @return List in MEMO_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<MemoDataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<MemoDataModel> ret = new ArrayList<MemoDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MEM_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   MMD_CONTENT,");
            sql.addSql("   MMD_DEL_CONF,");
            sql.addSql("   MMD_AUID,");
            sql.addSql("   MMD_ADATE,");
            sql.addSql("   MMD_EUID,");
            sql.addSql("   MMD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   MEMO_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getMemoDataFromRs(rs));
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
     * <p>Select MEMO_DATA
     * @param memSid MEM_SID
     * @return MEMO_DATAModel
     * @throws SQLException SQL実行例外
     */
    public MemoDataModel select(long memSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        MemoDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MEM_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   MMD_CONTENT,");
            sql.addSql("   MMD_DEL_CONF,");
            sql.addSql("   MMD_AUID,");
            sql.addSql("   MMD_ADATE,");
            sql.addSql("   MMD_EUID,");
            sql.addSql("   MMD_EDATE");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getMemoDataFromRs(rs);
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
     * <br>[機  能] 更新日時 <= 基準日となるメモSIDリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param todate 日付
     * @return 削除対象のメモ一覧
     * @throws SQLException SQL実行例外
     */
    public List<Long> getDelMemoList(UDate todate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Long> ret  = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MEM_SID");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" where ");
            sql.addSql("   MMD_EDATE <= ?");
            sql.addDateValue(todate);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong("MEM_SID"));
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
     * <p>Delete MEMO_DATA
     * @param memSid MEM_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(long memSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] メモSIDを指定しメモ情報を削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param mems メモSIDリスト
     * @return 削除数
     * @throws SQLException SQL実行例外
     */
    public int delete(ArrayList<Long> mems) throws SQLException {

        int count = 0;
        if (mems.size() == 0) {
            return count;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID in(");
            if (mems.size() > 0) {
                sql.addSql("   ?");
                sql.addLongValue(mems.get(0));
            }
            for (int i = 1; i < mems.size(); i++) {
                sql.addSql("   ,?");
                sql.addLongValue(mems.get(i));
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したユーザのメモ件数を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl Mem010SearchModel
     * @return メモ件数
     * @throws SQLException SQL実行例外
     */
    public int getMemoCount(Mem010SearchModel searchMdl) throws SQLException {

        int count = 0;

         PreparedStatement pstmt = null;
         ResultSet rs = null;
         Connection con = null;
         con = getCon();

         try {
             //SQL文
             SqlBuffer sql = new SqlBuffer();
             sql.addSql(" select");
             sql.addSql("   count(MEM_SID) as COUNT");
             sql.addSql(" from");
             sql.addSql("   MEMO_DATA");
             sql.addSql(" where ");
             sql.addSql("   USR_SID = ?");

             sql.addIntValue(searchMdl.getUsrSid());

             String mmdContent = searchMdl.getMmdContent();
             UDate dateFr = searchMdl.getDateFr();
             UDate dateTo = searchMdl.getDateTo();
             int mmlSid = searchMdl.getMmlSid();
             int tenpuFlg = searchMdl.getTenpu();

             if (mmdContent != null) {
                 sql.addSql(" and");
                 sql.addSql("   MEMO_DATA.MMD_CONTENT like '%"
                         + JDBCUtil.escapeForLikeSearch(mmdContent)
                         + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
             }
             if (dateFr != null) {
                 sql.addSql(" and");
                 sql.addSql("   MEMO_DATA.MMD_EDATE >= ?");
                 sql.addDateValue(dateFr);
             }
             if (dateTo != null) {
                 sql.addSql(" and");
                 sql.addSql("   MEMO_DATA.MMD_EDATE <= ?");
                 sql.addDateValue(dateTo);
             }
             if (mmlSid != -1) {
                 sql.addSql(" and");
                 sql.addSql("   MEMO_DATA.MEM_SID in (");
                 sql.addSql("     select MEMO_BELONG_LABEL.MEM_SID from MEMO_BELONG_LABEL");
                 sql.addSql("     where MEMO_BELONG_LABEL.MML_SID = ?");
                 sql.addSql("   )");
                 sql.addIntValue(mmlSid);
             }
             if (tenpuFlg == GSConstMemo.TENPU_KBN_YES) {
                 sql.addSql(" and");
                 sql.addSql("   MEMO_DATA.MEM_SID in (");
                 sql.addSql("     select MEMO_BIN.MEM_SID from MEMO_BIN");
                 sql.addSql("   )");
             } else if (tenpuFlg == GSConstMemo.TENPU_KBN_NO) {
                 sql.addSql(" and");
                 sql.addSql("   MEMO_DATA.MEM_SID not in (");
                 sql.addSql("     select MEMO_BIN.MEM_SID from MEMO_BIN");
                 sql.addSql("   )");
             }

             pstmt = con.prepareStatement(sql.toSqlString());
             sql.setParameter(pstmt);
             log__.info(sql.toLogString());
             rs = pstmt.executeQuery();
             if (rs.next()) {
                 count = rs.getInt("COUNT");
             }
         } catch (SQLException e) {
             throw e;
         } finally {
             JDBCUtil.closeStatement(pstmt);
             JDBCUtil.closeResultSet(rs);
         }
         return count;
    }

    /**
     * <p>Create MEMO_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created MemoDataModel
     * @throws SQLException SQL実行例外
     */
    private MemoDataModel __getMemoDataFromRs(ResultSet rs) throws SQLException {
        MemoDataModel bean = new MemoDataModel();
        bean.setMemSid(rs.getLong("MEM_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setMmdContent(rs.getString("MMD_CONTENT"));
        bean.setMmdDelConf(rs.getInt("MMD_DEL_CONF"));
        bean.setMmdAuid(rs.getInt("MMD_AUID"));
        bean.setMmdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("MMD_ADATE")));
        bean.setMmdEuid(rs.getInt("MMD_EUID"));
        bean.setMmdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("MMD_EDATE")));
        return bean;
    }
    /**
     *
     * <br>[機  能] 指定SIDのデータサイズを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList 指定SID
     * @return データサイズ
     * @throws SQLException SQL実行時例外
     */
    public Long getMemSize(List<Long> sidList) throws SQLException {

        if (sidList == null || sidList.size() == 0) {
            return (long) 0;
        }
        String inSidStr = sidList.stream()
                .map(sid -> sid.toString())
                .collect(Collectors.joining(",", "(", ")"));
        long ret = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(octet_length(MMD_CONTENT)) as MMD_CONTENT");
            sql.addSql(" from ");
            sql.addSql("   MEMO_DATA ");
            sql.addSql(" where ");
            sql.addSql("   MEM_SID in ");
            sql.addSql(inSidStr);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret += rs.getLong("MMD_CONTENT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
}
