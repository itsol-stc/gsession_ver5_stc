package jp.groupsession.v2.ntp.ntp150;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpProcessModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 プロセス一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp150ProcessDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp150ProcessDao.class);

    /**
     * <p>Default Constructor
     */
    public Ntp150ProcessDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp150ProcessDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 業務内容毎のソートの最大値+1を返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param ngySid 業務内容SID
     * @return ソートの最大値
     * @throws SQLException SQL実行例外
     */
    public int getMaxSort(int ngySid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int sort = 1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MAX(NTP_PROCESS.NGP_SORT) as MAX_SORT");
            sql.addSql(" from ");
            sql.addSql("   NTP_PROCESS");
            sql.addSql(" where ");
            sql.addSql("   NTP_PROCESS.NGY_SID = ?");
            sql.addIntValue(ngySid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                sort = rs.getInt("MAX_SORT") + 1;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return sort;
    }
    /**
     * <p>Select NTP_PROCESS All Data
     * @return List in NTP_PROCESSModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpProcessModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpProcessModel> ret = new ArrayList<NtpProcessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_PROCESS.NGP_SID as NGP_SID,");
            sql.addSql("   NTP_PROCESS.NGY_SID as NGY_SID,");
            sql.addSql("   NTP_PROCESS.NGP_CODE as NGP_CODE,");
            sql.addSql("   NTP_PROCESS.NGP_NAME as NGP_NAME,");
            sql.addSql("   PROCESS_SORT.NPS_SORT as NPS_SORT,");
            sql.addSql("   NTP_GYOMU_SORT.NGS_SORT as NGS_SORT,");
            sql.addSql("   NTP_GYOMU.NGY_NAME as NGY_NAME,");
            sql.addSql("   NTP_GYOMU.NGY_CODE as NGY_CODE");
            sql.addSql(" from ");
            sql.addSql("   NTP_GYOMU_SORT,");
            sql.addSql("   NTP_GYOMU,");
            sql.addSql("   NTP_PROCESS");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NGP_SID, NGY_SID, NPS_SORT");
            sql.addSql("      from NTP_PROCESS_SORT");
            sql.addSql("     ) PROCESS_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_PROCESS.NGP_SID = PROCESS_SORT.NGP_SID");
            sql.addSql("   and");
            sql.addSql("     NTP_PROCESS.NGY_SID = PROCESS_SORT.NGY_SID");
            sql.addSql("   where");
            sql.addSql("     NTP_PROCESS.NGY_SID = NTP_GYOMU.NGY_SID");
            sql.addSql("   and");
            sql.addSql("     NTP_PROCESS.NGY_SID = NTP_GYOMU_SORT.NGY_SID");
            sql.addSql(" order by");
            sql.addSql("   NTP_GYOMU_SORT.NGS_SORT asc,");
            sql.addSql("   PROCESS_SORT.NPS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpProcessFromRs(rs));
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
     * <p>Select NTP_PROCESS All Data
     * @param ngySid 業務SID
     * @return List in NTP_PROCESSModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpProcessModel> select(int ngySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpProcessModel> ret = new ArrayList<NtpProcessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_PROCESS.NGP_SID as NGP_SID,");
            sql.addSql("   NTP_PROCESS.NGY_SID as NGY_SID,");
            sql.addSql("   NTP_PROCESS.NGP_CODE as NGP_CODE,");
            sql.addSql("   NTP_PROCESS.NGP_NAME as NGP_NAME,");
            sql.addSql("   PROCESS_SORT.NPS_SORT as NPS_SORT,");
            sql.addSql("   NTP_GYOMU.NGY_NAME as NGY_NAME,");
            sql.addSql("   NTP_GYOMU.NGY_CODE as NGY_CODE");
            sql.addSql(" from ");
            sql.addSql("   NTP_GYOMU,");
            sql.addSql("   NTP_PROCESS");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NGP_SID, NGY_SID, NPS_SORT");
            sql.addSql("      from NTP_PROCESS_SORT");
            sql.addSql("     ) PROCESS_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_PROCESS.NGP_SID = PROCESS_SORT.NGP_SID");
            sql.addSql("   and");
            sql.addSql("     NTP_PROCESS.NGY_SID = PROCESS_SORT.NGY_SID");
            sql.addSql("   where");
            sql.addSql("     NTP_PROCESS.NGY_SID = NTP_GYOMU.NGY_SID");
            sql.addSql("   and");
            sql.addSql("     NTP_PROCESS.NGY_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   PROCESS_SORT.NPS_SORT asc ");
            sql.addIntValue(ngySid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpProcessFromRs(rs));
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
     * <p>Create NTP_PROCESS Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpProcessModel
     * @throws SQLException SQL実行例外
     */
    private NtpProcessModel __getNtpProcessFromRs(ResultSet rs) throws SQLException {
        NtpProcessModel bean = new NtpProcessModel();
        bean.setNgpSid(rs.getInt("NGP_SID"));
        bean.setNgySid(rs.getInt("NGY_SID"));
        bean.setNgpCode(rs.getString("NGP_CODE"));
        bean.setNgpName(rs.getString("NGP_NAME"));
        bean.setNgyName(rs.getString("NGY_NAME"));
        bean.setNgyCode(rs.getString("NGY_CODE"));
        return bean;
    }
}
