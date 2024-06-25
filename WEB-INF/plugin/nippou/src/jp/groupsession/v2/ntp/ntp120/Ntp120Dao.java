package jp.groupsession.v2.ntp.ntp120;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 マスタメンテナンス画面で使用するマスタデータを取得する際使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp120Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp120Dao.class);

    /**
     * <p>Default Constructor
     */
    public Ntp120Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp120Dao(Connection con) {
        super(con);
    }

    /**
     * <p>Select NTP_SHOHIN All Data
     * @return 商品件数
     * @throws SQLException SQL実行例外
     */
    public Ntp120DataModel getShohinData() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Ntp120DataModel dataMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTPSHO1.NHN_EDATE as SHEDATE,");
            sql.addSql("   NTPSHO2.NTP_SHCNT as SHCOUNT");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN as NTPSHO1,");
            sql.addSql("   (select");
            sql.addSql("      count(NTPSHO3.NHN_SID) as NTP_SHCNT");
            sql.addSql("    from");
            sql.addSql("      NTP_SHOHIN as NTPSHO3");
            sql.addSql("    where");
            sql.addSql("      NTPSHO3.NHN_SID != -1");
            sql.addSql("    ) as NTPSHO2");
            sql.addSql(" where");
            sql.addSql("   NTPSHO1.NHN_SID != -1");
            sql.addSql(" order by (NTPSHO1.NHN_EDATE) DESC");
            sql.addSql(" limit 1;");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataMdl = new Ntp120DataModel();
                dataMdl.setCount(rs.getInt("SHCOUNT"));
                dataMdl.setLastEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SHEDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataMdl;
    }

    /**
     * <p>Select NTP_GYOMU All Data
     * @return 業種件数
     * @throws SQLException SQL実行例外
     */
    public Ntp120DataModel getGyoushuData() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Ntp120DataModel dataMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTPGYO1.NGY_EDATE  as NGYEDATE,");
            sql.addSql("   NTPGYO2.NTP_GYOCNT as NGYCOUNT");
            sql.addSql(" from");
            sql.addSql("   NTP_GYOMU as NTPGYO1,");
            sql.addSql("   (select");
            sql.addSql("      count(NTPGYO3.NGY_SID) as NTP_GYOCNT");
            sql.addSql("    from");
            sql.addSql("      NTP_GYOMU as NTPGYO3");
            sql.addSql("    where");
            sql.addSql("      NTPGYO3.NGY_SID != -1");
            sql.addSql("    ) as NTPGYO2");
            sql.addSql(" where");
            sql.addSql("   NTPGYO1.NGY_SID != -1");
            sql.addSql(" order by (NTPGYO1.NGY_EDATE) DESC");
            sql.addSql(" limit 1;");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataMdl = new Ntp120DataModel();
                dataMdl.setCount(rs.getInt("NGYCOUNT"));
                dataMdl.setLastEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NGYEDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataMdl;
    }

    /**
     * <p>Select NTP_PROCESS All Data
     * @return プロセス件数
     * @throws SQLException SQL実行例外
     */
    public Ntp120DataModel getProcessData() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Ntp120DataModel dataMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTPPCS1.NGP_EDATE  as NPCEDATE,");
            sql.addSql("   NTPGYO2.NTP_PCSCNT as NPCCOUNT");
            sql.addSql(" from");
            sql.addSql("   NTP_PROCESS as NTPPCS1,");
            sql.addSql("   NTP_GYOMU as NTPGYO1,");
            sql.addSql("   (select");
            sql.addSql("      count(NTPPCS3.NGP_SID) as NTP_PCSCNT");
            sql.addSql("    from");
            sql.addSql("      NTP_PROCESS as NTPPCS3,");
            sql.addSql("      NTP_GYOMU as NTPGYO2");
            sql.addSql("    where");
            sql.addSql("      NTPPCS3.NGY_SID = NTPGYO2.NGY_SID");
            sql.addSql("    ) as NTPGYO2");
            sql.addSql(" where");
            sql.addSql("   NTPPCS1.NGY_SID = NTPGYO1.NGY_SID");
            sql.addSql(" order by (NTPPCS1.NGP_EDATE) DESC");
            sql.addSql(" limit 1;");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataMdl = new Ntp120DataModel();
                dataMdl.setCount(rs.getInt("NPCCOUNT"));
                dataMdl.setLastEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NPCEDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataMdl;
    }

    /**
     * <p>Select NTP_KTBUNRUI All Data
     * @return 活動分類件数
     * @throws SQLException SQL実行例外
     */
    public Ntp120DataModel getKtBunruiData() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Ntp120DataModel dataMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NKB1.NKB_EDATE  as NGYEDATE,");
            sql.addSql("   NKB2.NKBCNT as NKBCOUNT");
            sql.addSql(" from");
            sql.addSql("   NTP_KTBUNRUI as NKB1,");
            sql.addSql("   (select");
            sql.addSql("      count(NKB3.NKB_SID) as NKBCNT");
            sql.addSql("    from");
            sql.addSql("      NTP_KTBUNRUI as NKB3");
            sql.addSql("    ) as NKB2");
            sql.addSql(" order by (NKB1.NKB_EDATE) DESC");
            sql.addSql(" limit 1;");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataMdl = new Ntp120DataModel();
                dataMdl.setCount(rs.getInt("NKBCOUNT"));
                dataMdl.setLastEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NGYEDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataMdl;
    }

    /**
     * <p>Select NTP_KTHOUHOU All Data
     * @return 活動方法件数
     * @throws SQLException SQL実行例外
     */
    public Ntp120DataModel getKtHouhouData() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Ntp120DataModel dataMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NKH1.NKH_EDATE as NKHEDATE,");
            sql.addSql("   NKH2.NKHCNT as NKHCOUNT");
            sql.addSql(" from");
            sql.addSql("   NTP_KTHOUHOU as NKH1,");
            sql.addSql("   (select");
            sql.addSql("      count(NKH3.NKH_SID) as NKHCNT");
            sql.addSql("    from");
            sql.addSql("      NTP_KTHOUHOU as NKH3");
            sql.addSql("    ) as NKH2");
            sql.addSql(" order by (NKH1.NKH_EDATE) DESC");
            sql.addSql(" limit 1;");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataMdl = new Ntp120DataModel();
                dataMdl.setCount(rs.getInt("NKHCOUNT"));
                dataMdl.setLastEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NKHEDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataMdl;
    }

    /**
     * <p>Select NTP_CONTACT All Data
     * @return 活動方法件数
     * @throws SQLException SQL実行例外
     */
    public Ntp120DataModel getContactData() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Ntp120DataModel dataMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NCN1.NCN_EDATE as NCNEDATE,");
            sql.addSql("   NCN2.NCNCNT as NCNCOUNT");
            sql.addSql(" from");
            sql.addSql("   NTP_CONTACT as NCN1,");
            sql.addSql("   (select");
            sql.addSql("      count(NCN3.NCN_SID) as NCNCNT");
            sql.addSql("    from");
            sql.addSql("      NTP_CONTACT as NCN3");
            sql.addSql("    ) as NCN2");
            sql.addSql(" order by (NCN1.NCN_EDATE) DESC");
            sql.addSql(" limit 1;");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataMdl = new Ntp120DataModel();
                dataMdl.setCount(rs.getInt("NCNCOUNT"));
                dataMdl.setLastEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NCNEDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataMdl;
    }

    /**
     * <p>Select NTP_TARGET All Data
     * @return 目標件数
     * @throws SQLException SQL実行例外
     */
    public Ntp120DataModel getTargetData() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Ntp120DataModel dataMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTG1.NTG_EDATE as NTGEDATE,");
            sql.addSql("   NTG2.NTGCNT as NTGCOUNT");
            sql.addSql(" from");
            sql.addSql("   NTP_TARGET as NTG1,");
            sql.addSql("   (select");
            sql.addSql("      count(NTG3.NTG_SID) as NTGCNT");
            sql.addSql("    from");
            sql.addSql("      NTP_TARGET as NTG3");
            sql.addSql("    ) as NTG2");
            sql.addSql(" order by (NTG1.NTG_EDATE) DESC");
            sql.addSql(" limit 1;");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataMdl = new Ntp120DataModel();
                dataMdl.setCount(rs.getInt("NTGCOUNT"));
                dataMdl.setLastEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NTGEDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataMdl;
    }
}
