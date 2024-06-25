package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.sch.model.SchAddressModel;

/**
 * <p>SCH_ADDRESS Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchAddressDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchAddressDao.class);

    /**
     * <p>Default Constructor
     */
    public SchAddressDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchAddressDao(Connection con) {
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
            sql.addSql("drop table SCH_ADDRESS");

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
            sql.addSql(" create table SCH_ADDRESS (");
            sql.addSql("   SCD_SID NUMBER(10,0) not null,");
            sql.addSql("   ADR_SID NUMBER(10,0) not null,");
            sql.addSql("   ADC_SID NUMBER(10,0),");
            sql.addSql("   SCA_AUID NUMBER(10,0) not null,");
            sql.addSql("   SCA_ADATE varchar(23) not null,");
            sql.addSql("   SCA_EUID NUMBER(10,0) not null,");
            sql.addSql("   SCA_EDATE varchar(23) not null,");
            sql.addSql("   primary key (SCD_SID,ADR_SID)");
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
     * <p>Insert SCH_ADDRESS Data Bindding JavaBean
     * @param bean SCH_ADDRESS Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_ADDRESS(");
            sql.addSql("   SCD_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADC_SID,");
            sql.addSql("   SCA_AUID,");
            sql.addSql("   SCA_ADATE,");
            sql.addSql("   SCA_EUID,");
            sql.addSql("   SCA_EDATE");
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
            sql.addIntValue(bean.getAdrSid());
            sql.addIntValue(bean.getAdcSid());
            sql.addIntValue(bean.getScaAuid());
            sql.addDateValue(bean.getScaAdate());
            sql.addIntValue(bean.getScaEuid());
            sql.addDateValue(bean.getScaEdate());
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
    public void insert(List<SchAddressModel> beanList) throws SQLException {

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<SchAddressModel> exeList = new ArrayList<>();
        Iterator<SchAddressModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" SCH_ADDRESS(");
        sb.append("   SCD_SID,");
        sb.append("   ADR_SID,");
        sb.append("   ADC_SID,");
        sb.append("   SCA_AUID,");
        sb.append("   SCA_ADATE,");
        sb.append("   SCA_EUID,");
        sb.append("   SCA_EDATE");
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

            Iterator<SchAddressModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                SchAddressModel bean = exeItr.next();
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
                sql.addIntValue(bean.getAdrSid());
                sql.addIntValue(bean.getAdcSid());
                sql.addIntValue(bean.getScaAuid());
                sql.addDateValue(bean.getScaAdate());
                sql.addIntValue(bean.getScaEuid());
                sql.addDateValue(bean.getScaEdate());

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
     * <p>Update SCH_ADDRESS Data Bindding JavaBean
     * @param bean SCH_ADDRESS Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchAddressModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_ADDRESS");
            sql.addSql(" set ");
            sql.addSql("   ADC_SID=?,");
            sql.addSql("   SCA_AUID=?,");
            sql.addSql("   SCA_ADATE=?,");
            sql.addSql("   SCA_EUID=?,");
            sql.addSql("   SCA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getAdcSid());
            sql.addIntValue(bean.getScaAuid());
            sql.addDateValue(bean.getScaAdate());
            sql.addIntValue(bean.getScaEuid());
            sql.addDateValue(bean.getScaEdate());
            //where
            sql.addIntValue(bean.getScdSid());
            sql.addIntValue(bean.getAdrSid());

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
     * <p>Select SCH_ADDRESS All Data
     * @return List in SCH_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<SchAddressModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchAddressModel> ret = new ArrayList<SchAddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCD_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADC_SID,");
            sql.addSql("   SCA_AUID,");
            sql.addSql("   SCA_ADATE,");
            sql.addSql("   SCA_EUID,");
            sql.addSql("   SCA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_ADDRESS");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchAddressFromRs(rs));
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
     * <p>Select SCH_ADDRESS Data
     * @param scdSid SCD_SID
     * @return List in SCH_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<SchAddressModel> select(int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchAddressModel> ret = new ArrayList<SchAddressModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCD_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADC_SID,");
            sql.addSql("   SCA_AUID,");
            sql.addSql("   SCA_ADATE,");
            sql.addSql("   SCA_EUID,");
            sql.addSql("   SCA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID = ?");
            sql.addIntValue(scdSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchAddressFromRs(rs));
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
     * <p>Select SCH_ADDRESS Data
     * @param scdSids SCD_SID一覧
     * @return List in SCH_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public List<SchAddressModel> select(List<Integer> scdSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchAddressModel> ret = new ArrayList<SchAddressModel>();
        con = getCon();

        try {
            if (scdSids != null && scdSids.size() > 0) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select ");
                sql.addSql("   SCD_SID,");
                sql.addSql("   ADR_SID,");
                sql.addSql("   ADC_SID,");
                sql.addSql("   SCA_AUID,");
                sql.addSql("   SCA_ADATE,");
                sql.addSql("   SCA_EUID,");
                sql.addSql("   SCA_EDATE");
                sql.addSql(" from ");
                sql.addSql("   SCH_ADDRESS");
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
                    ret.add(__getSchAddressFromRs(rs));
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
     * <p>Select SCH_ADDRESS
     * @param scdSid SCD_SID
     * @param adrSid ADR_SID
     * @return SCH_ADDRESSModel
     * @throws SQLException SQL実行例外
     */
    public SchAddressModel select(int scdSid, int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchAddressModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   ADR_SID,");
            sql.addSql("   ADC_SID,");
            sql.addSql("   SCA_AUID,");
            sql.addSql("   SCA_ADATE,");
            sql.addSql("   SCA_EUID,");
            sql.addSql("   SCA_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            sql.addIntValue(adrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchAddressFromRs(rs);
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
     * <p>Delete SCH_ADDRESS
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
            sql.addSql("   SCH_ADDRESS");
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
     * <p>Delete SCH_ADDRESS
     * @param scdSid SCD_SID list
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(Collection<Integer> scdSidList) throws SQLException {
        int count = 0;

        if (scdSidList == null || scdSidList.size() <= 0) {
            return count;
        }
        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = scdSidList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" delete");
        sb.append(" from");
        sb.append("   SCH_ADDRESS");
        sb.append(" where ");

        Connection con = null;
        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" SCD_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addLongValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                count += pstmt.executeUpdate();

            }
            exeList.clear();
        }
        return count;

    }

    /**
     * <p>Delete SCH_ADDRESS
     * @param scdSid SCD_SID
     * @param adrSid ADR_SID
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(int scdSid, int adrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_ADDRESS");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   ADR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            sql.addIntValue(adrSid);

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
     * <p>Create SCH_ADDRESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchAddressModel
     * @throws SQLException SQL実行例外
     */
    private SchAddressModel __getSchAddressFromRs(ResultSet rs) throws SQLException {
        SchAddressModel bean = new SchAddressModel();
        bean.setScdSid(rs.getInt("SCD_SID"));
        bean.setAdrSid(rs.getInt("ADR_SID"));
        bean.setAdcSid(rs.getInt("ADC_SID"));
        bean.setScaAuid(rs.getInt("SCA_AUID"));
        bean.setScaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCA_ADATE")));
        bean.setScaEuid(rs.getInt("SCA_EUID"));
        bean.setScaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCA_EDATE")));
        return bean;
    }

}
