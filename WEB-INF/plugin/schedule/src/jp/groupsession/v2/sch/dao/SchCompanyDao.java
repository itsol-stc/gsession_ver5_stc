package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sch.model.SchCompanyModel;

/**
 * <p>SCH_COMPANY Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchCompanyDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchCompanyDao.class);

    /**
     * <p>Default Constructor
     */
    public SchCompanyDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchCompanyDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert SCH_COMPANY Data Bindding JavaBean
     * @param bean SCH_COMPANY Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchCompanyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_COMPANY(");
            sql.addSql("   SCD_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getScdSid());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addIntValue(bean.getSccAuid());
            sql.addDateValue(bean.getSccAdate());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
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
     * <p>Insert SCH_ADDRESS Data Bindding JavaBean
     * @param beanList SCH_ADDRESS DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<SchCompanyModel> beanList) throws SQLException {

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<SchCompanyModel> exeList = new ArrayList<>();
        Iterator<SchCompanyModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" SCH_COMPANY(");
        sb.append("   SCD_SID,");
        sb.append("   ACO_SID,");
        sb.append("   ABA_SID,");
        sb.append("   SCC_AUID,");
        sb.append("   SCC_ADATE,");
        sb.append("   SCC_EUID,");
        sb.append("   SCC_EDATE");
        sb.append(" )");
        sb.append(" values");


        Connection con = null;
        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件分インサート
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());

            Iterator<SchCompanyModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                SchCompanyModel bean = exeItr.next();
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getScdSid());
                sql.addIntValue(bean.getAcoSid());
                sql.addIntValue(bean.getAbaSid());
                sql.addIntValue(bean.getSccAuid());
                sql.addDateValue(bean.getSccAdate());
                sql.addIntValue(bean.getSccEuid());
                sql.addDateValue(bean.getSccEdate());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                pstmt.executeUpdate();

            }
            exeList.clear();
        }
    }

    /**
     * <p>Update SCH_COMPANY Data Bindding JavaBean
     * @param bean SCH_COMPANY Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchCompanyModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_COMPANY");
            sql.addSql(" set ");
            sql.addSql("   SCC_EUID=?,");
            sql.addSql("   SCC_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addIntValue(bean.getSccEuid());
            sql.addDateValue(bean.getSccEdate());
            //where
            sql.addIntValue(bean.getScdSid());

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
     * <p>Select SCH_COMPANY All Data
     * @return List in SCH_COMPANYModel
     * @throws SQLException SQL実行例外
     */
    public List<SchCompanyModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchCompanyModel> ret = new ArrayList<SchCompanyModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCD_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_COMPANY");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchCompanyFromRs(rs));
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
     * <p>Select SCH_COMPANY Data
     * @param scdSid SCD_SID
     * @return List in SCH_COMPANYModel
     * @throws SQLException SQL実行例外
     */
    public List<SchCompanyModel> select(int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchCompanyModel> ret = new ArrayList<SchCompanyModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCD_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID = ?");
            sql.addIntValue(scdSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchCompanyFromRs(rs));
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
     * <p>Select SCH_COMPANY Data
     * @param scdSids SCD_SID一覧
     * @return List in SCH_COMPANYModel
     * @throws SQLException SQL実行例外
     */
    public List<SchCompanyModel> select(List<Integer> scdSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchCompanyModel> ret = new ArrayList<SchCompanyModel>();
        con = getCon();

        try {
            if (scdSids != null && scdSids.size() > 0) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select ");
                sql.addSql("   SCD_SID,");
                sql.addSql("   ACO_SID,");
                sql.addSql("   ABA_SID,");
                sql.addSql("   SCC_AUID,");
                sql.addSql("   SCC_ADATE,");
                sql.addSql("   SCC_EUID,");
                sql.addSql("   SCC_EDATE");
                sql.addSql(" from ");
                sql.addSql("   SCH_COMPANY");
                sql.addSql(" where ");
                sql.addSql("   SCD_SID in (");
                for (int i = 0; i < scdSids.size(); i++) {
                    sql.addSql((i > 0 ? "   ," : "   ") + String.valueOf(scdSids.get(i)));
                }
                sql.addSql("   )");

                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    ret.add(__getSchCompanyFromRs(rs));
                }
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
     * <p>Select SCH_COMPANY
     * @param scdSid SCD_SID
     * @param acoSid ACO_SID
     * @param abaSid ABA_SID
     * @return SCH_COMPANYModel
     * @throws SQLException SQL実行例外
     */
    public SchCompanyModel select(int scdSid, int acoSid, int abaSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchCompanyModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ACO_SID=?");
            sql.addSql(" and ");
            sql.addSql("   ABA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            sql.addIntValue(acoSid);
            sql.addIntValue(abaSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchCompanyFromRs(rs);
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
     * <p>Delete SCH_COMPANY
     * @param scdSid SCD_SID
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

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
     * <p>Delete SCH_COMPANY
     * @param scdSidList List in SCD_SID
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(List<Integer> scdSidList) throws SQLException {

        if (scdSidList == null || scdSidList.isEmpty()) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID in (");

            sql.addSql("     ?");
            sql.addIntValue(scdSidList.get(0));

            for (int index = 1; index < scdSidList.size(); index++) {
                sql.addSql("     ,?");
                sql.addIntValue(scdSidList.get(index));
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 指定したスケジュール拡張情報に属するスケジュールの
     * <br>         スケジュール-会社情報Mapping を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param sceSid SCE_SID
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int deleteEx(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_COMPANY");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID in (");
            sql.addSql("     select SCD_SID from SCH_DATA");
            sql.addSql("     where SCE_SID = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

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
     * <p>Create SCH_COMPANY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchCompanyModel
     * @throws SQLException SQL実行例外
     */
    private SchCompanyModel __getSchCompanyFromRs(ResultSet rs) throws SQLException {
        SchCompanyModel bean = new SchCompanyModel();
        bean.setScdSid(rs.getInt("SCD_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setSccAuid(rs.getInt("SCC_AUID"));
        bean.setSccAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_ADATE")));
        bean.setSccEuid(rs.getInt("SCC_EUID"));
        bean.setSccEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_EDATE")));
        return bean;
    }
}
