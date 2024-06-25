package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;

/**
 * <p>NTP_ANKEN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpAnkenDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpAnkenDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpAnkenDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpAnkenDao(Connection con) {
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
            sql.addSql("drop table NTP_ANKEN");

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
            sql.addSql(" create table NTP_ANKEN (");
            sql.addSql("   NAN_SID               integer           not null,");
            sql.addSql("   NAN_CODE              varchar(8)        not null,");
            sql.addSql("   NAN_NAME              varchar(100)      not null,");
            sql.addSql("   NAN_DETIAL            varchar(1000)     not null,");
            sql.addSql("   NAN_DATE              timestamp         not null,");
            sql.addSql("   ACO_SID               integer           not null,");
            sql.addSql("   ABA_SID               integer           not null,");
            sql.addSql("   NGP_SID               integer                   ,");
            sql.addSql("   NAN_MIKOMI            integer                   ,");
            sql.addSql("   NAN_KIN_MITUMORI      integer                   ,");
            sql.addSql("   NAN_KIN_JUTYU         integer                   ,");
            sql.addSql("   NAN_SYODAN            integer                   ,");
            sql.addSql("   NAN_STATE             integer                   ,");
            sql.addSql("   NCN_SID               integer                   ,");
            sql.addSql("   NAN_AUID              integer                   ,");
            sql.addSql("   NAN_ADATE             timestamp         not null,");
            sql.addSql("   NAN_EUID              integer                   ,");
            sql.addSql("   NAN_EDATE             timestamp         not null,");
            sql.addSql("   NAN_MITUMORI_DATE     timestamp         not null,");
            sql.addSql("   NAN_JUTYU_DATE        timestamp         not null,");
            sql.addSql("   NAN_PERMIT_VIEW       integer           not null,");
            sql.addSql("   NAN_PERMIT_EDIT       integer           not null,");
            sql.addSql("   primary key (NAN_SID)");
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
     * <p>Insert NTP_ANKEN Data Bindding JavaBean
     * @param bean NTP_ANKEN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpAnkenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_ANKEN(");
            sql.addSql("   NAN_SID,");
            sql.addSql("   NAN_CODE,");
            sql.addSql("   NAN_NAME,");
            sql.addSql("   NAN_DETIAL,");
            sql.addSql("   NAN_DATE,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NAN_MIKOMI,");
            sql.addSql("   NAN_KIN_MITUMORI,");
            sql.addSql("   NAN_KIN_JUTYU,");
            sql.addSql("   NAN_SYODAN,");
            sql.addSql("   NAN_STATE,");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NAN_AUID,");
            sql.addSql("   NAN_ADATE,");
            sql.addSql("   NAN_EUID,");
            sql.addSql("   NAN_EDATE,");
            sql.addSql("   NAN_MITUMORI_DATE,");
            sql.addSql("   NAN_JUTYU_DATE,");
            sql.addSql("   NAN_PERMIT_VIEW,");
            sql.addSql("   NAN_PERMIT_EDIT");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
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
            sql.addIntValue(bean.getNanSid());
            sql.addStrValue(bean.getNanCode());
            sql.addStrValue(bean.getNanName());
            sql.addStrValue(bean.getNanDetial());
            sql.addDateValue(bean.getNanDate());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addIntValue(bean.getNgpSid());
            sql.addIntValue(bean.getNanMikomi());
            sql.addIntValue(bean.getNanKinMitumori());
            sql.addIntValue(bean.getNanKinJutyu());
            sql.addIntValue(bean.getNanSyodan());
            sql.addIntValue(bean.getNanState());
            sql.addIntValue(bean.getNcnSid());
            sql.addIntValue(bean.getNanAuid());
            sql.addDateValue(bean.getNanAdate());
            sql.addIntValue(bean.getNanEuid());
            sql.addDateValue(bean.getNanEdate());
            sql.addDateValue(bean.getNanMitumoriDate());
            sql.addDateValue(bean.getNanJutyuDate());
            sql.addIntValue(bean.getNanPermitView());
            sql.addIntValue(bean.getNanPermitEdit());
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
     * <p>Update NTP_ANKEN Data Bindding JavaBean
     * @param bean NTP_ANKEN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpAnkenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_ANKEN");
            sql.addSql(" set ");
            sql.addSql("   NAN_CODE=?,");
            sql.addSql("   NAN_NAME=?,");
            sql.addSql("   NAN_DETIAL=?,");
            sql.addSql("   NAN_DATE=?,");
            sql.addSql("   ACO_SID=?,");
            sql.addSql("   ABA_SID=?,");
            sql.addSql("   NGP_SID=?,");
            sql.addSql("   NAN_MIKOMI=?,");
            sql.addSql("   NAN_KIN_MITUMORI=?,");
            sql.addSql("   NAN_KIN_JUTYU=?,");
            sql.addSql("   NAN_SYODAN=?,");
            sql.addSql("   NAN_STATE=?,");
            sql.addSql("   NCN_SID=?,");
            sql.addSql("   NAN_EUID=?,");
            sql.addSql("   NAN_EDATE=?,");
            sql.addSql("   NAN_MITUMORI_DATE=?,");
            sql.addSql("   NAN_JUTYU_DATE=?,");
            sql.addSql("   NAN_PERMIT_VIEW=?,");
            sql.addSql("   NAN_PERMIT_EDIT=?");

            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNanCode());
            sql.addStrValue(bean.getNanName());
            sql.addStrValue(bean.getNanDetial());
            sql.addDateValue(bean.getNanDate());
            sql.addIntValue(bean.getAcoSid());
            sql.addIntValue(bean.getAbaSid());
            sql.addIntValue(bean.getNgpSid());
            sql.addIntValue(bean.getNanMikomi());
            sql.addIntValue(bean.getNanKinMitumori());
            sql.addIntValue(bean.getNanKinJutyu());
            sql.addIntValue(bean.getNanSyodan());
            sql.addIntValue(bean.getNanState());
            sql.addIntValue(bean.getNcnSid());
            sql.addIntValue(bean.getNanEuid());
            sql.addDateValue(bean.getNanEdate());
            sql.addDateValue(bean.getNanMitumoriDate());
            sql.addDateValue(bean.getNanJutyuDate());
            sql.addIntValue(bean.getNanPermitView());
            sql.addIntValue(bean.getNanPermitEdit());
            //where
            sql.addIntValue(bean.getNanSid());

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
     * <p>案件の見込み度を更新
     * @param nanSid 案件SID
     * @param mikomido 見込み度
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateMikomido(int nanSid, int mikomido) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_ANKEN");
            sql.addSql(" set ");
            sql.addSql("   NAN_MIKOMI=?,");
            sql.addSql("   NAN_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(mikomido);
            UDate now = new UDate();
            sql.addDateValue(now);
            //where
            sql.addIntValue(nanSid);

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
     * <p>Select NTP_ANKEN All Data
     * @return List in NTP_ANKENModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpAnkenModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpAnkenModel> ret = new ArrayList<NtpAnkenModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NAN_SID,");
            sql.addSql("   NAN_CODE,");
            sql.addSql("   NAN_NAME,");
            sql.addSql("   NAN_DETIAL,");
            sql.addSql("   NAN_DATE,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NAN_MIKOMI,");
            sql.addSql("   NAN_KIN_MITUMORI,");
            sql.addSql("   NAN_KIN_JUTYU,");
            sql.addSql("   NAN_SYODAN,");
            sql.addSql("   NAN_STATE,");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NAN_AUID,");
            sql.addSql("   NAN_ADATE,");
            sql.addSql("   NAN_EUID,");
            sql.addSql("   NAN_EDATE,");
            sql.addSql("   NAN_MITUMORI_DATE,");
            sql.addSql("   NAN_JUTYU_DATE,");
            sql.addSql("   NAN_PERMIT_VIEW,");
            sql.addSql("   NAN_PERMIT_EDIT");
            sql.addSql(" from ");
            sql.addSql("   NTP_ANKEN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpAnkenFromRs(rs));
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
     * <p>Select NTP_ANKEN All Data
     * @param nanSids nan_sid
     * @return List in NTP_ANKENModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpAnkenModel> select(Collection<Integer> nanSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpAnkenModel> ret = new ArrayList<NtpAnkenModel>();
        con = getCon();
        if (nanSids == null || nanSids.size() <= 0) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NAN_SID,");
            sql.addSql("   NAN_CODE,");
            sql.addSql("   NAN_NAME,");
            sql.addSql("   NAN_DETIAL,");
            sql.addSql("   NAN_DATE,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NAN_MIKOMI,");
            sql.addSql("   NAN_KIN_MITUMORI,");
            sql.addSql("   NAN_KIN_JUTYU,");
            sql.addSql("   NAN_SYODAN,");
            sql.addSql("   NAN_STATE,");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NAN_AUID,");
            sql.addSql("   NAN_ADATE,");
            sql.addSql("   NAN_EUID,");
            sql.addSql("   NAN_EDATE,");
            sql.addSql("   NAN_MITUMORI_DATE,");
            sql.addSql("   NAN_JUTYU_DATE,");
            sql.addSql("   NAN_PERMIT_VIEW,");
            sql.addSql("   NAN_PERMIT_EDIT");
            sql.addSql(" from ");
            sql.addSql("   NTP_ANKEN");
            sql.addSql(" where ");
            int i = 0;
            sql.addSql("   (NAN_SID in (");
            for (Integer sid : nanSids) {
                if (sid == null) {
                    continue;
                }
                if (i > 0 && i % 1000 == 0) {
                    sql.addSql("  ) or NAN_SID in (");
                }
                if (i % 1000 != 0) {
                    sql.addSql("  ,");
                }
                sql.addSql(String.valueOf(sid));
                i++;
            }
            sql.addSql("  ))");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpAnkenFromRs(rs));
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
     * <p>Select NTP_ANKEN
     * @param nanSid NAN_SID
     * @return NTP_ANKENModel
     * @throws SQLException SQL実行例外
     */
    public NtpAnkenModel select(int nanSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpAnkenModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NAN_SID,");
            sql.addSql("   NAN_CODE,");
            sql.addSql("   NAN_NAME,");
            sql.addSql("   NAN_DETIAL,");
            sql.addSql("   NAN_DATE,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NAN_MIKOMI,");
            sql.addSql("   NAN_KIN_MITUMORI,");
            sql.addSql("   NAN_KIN_JUTYU,");
            sql.addSql("   NAN_SYODAN,");
            sql.addSql("   NAN_STATE,");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NAN_AUID,");
            sql.addSql("   NAN_ADATE,");
            sql.addSql("   NAN_EUID,");
            sql.addSql("   NAN_EDATE,");
            sql.addSql("   NAN_MITUMORI_DATE,");
            sql.addSql("   NAN_JUTYU_DATE,");
            sql.addSql("   NAN_PERMIT_VIEW,");
            sql.addSql("   NAN_PERMIT_EDIT");
            sql.addSql(" from");
            sql.addSql("   NTP_ANKEN");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nanSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpAnkenFromRs(rs);
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
     * <p>Select NTP_ANKEN
     * @param nanCode NAN_SID
     * @return NTP_ANKENModel
     * @throws SQLException SQL実行例外
     */
    public NtpAnkenModel select(String nanCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpAnkenModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NAN_SID,");
            sql.addSql("   NAN_CODE,");
            sql.addSql("   NAN_NAME,");
            sql.addSql("   NAN_DETIAL,");
            sql.addSql("   NAN_DATE,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NGP_SID,");
            sql.addSql("   NAN_MIKOMI,");
            sql.addSql("   NAN_KIN_MITUMORI,");
            sql.addSql("   NAN_KIN_JUTYU,");
            sql.addSql("   NAN_SYODAN,");
            sql.addSql("   NAN_STATE,");
            sql.addSql("   NCN_SID,");
            sql.addSql("   NAN_AUID,");
            sql.addSql("   NAN_ADATE,");
            sql.addSql("   NAN_EUID,");
            sql.addSql("   NAN_MITUMORI_DATE,");
            sql.addSql("   NAN_JUTYU_DATE,");
            sql.addSql("   NAN_EDATE,");
            sql.addSql("   NAN_PERMIT_VIEW,");
            sql.addSql("   NAN_PERMIT_EDIT");
            sql.addSql(" from");
            sql.addSql("   NTP_ANKEN");
            sql.addSql(" where ");
            sql.addSql("   NAN_CODE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(nanCode);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpAnkenFromRs(rs);
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
     * <br>[機  能] 指定した案件コードに該当する案件情報が存在するかをチェックする
     * <br>[解  説] 指定した案件SID > 0 の場合、指定した案件SID以外を存在チェックの条件とする
     * <br>[備  考]
     * @param nanSid 案件SID
     * @param nanCode 案件コード
     * @return 判定結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existAnken(int nanSid, String nanCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(NAN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_ANKEN");
            sql.addSql(" where ");
            sql.addSql("   NAN_CODE=?");
            sql.addStrValue(nanCode);

            if (nanSid > 0) {
                sql.addSql(" and ");
                sql.addSql("   NAN_SID != ?");
                sql.addIntValue(nanSid);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
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
     * <p>Delete NTP_ANKEN
     * @param nanSid NAN_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(int nanSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_ANKEN");
            sql.addSql(" where ");
            sql.addSql("   NAN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nanSid);

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
     * <p>Create NTP_ANKEN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpAnkenModel
     * @throws SQLException SQL実行例外
     */
    private NtpAnkenModel __getNtpAnkenFromRs(ResultSet rs) throws SQLException {
        NtpAnkenModel bean = new NtpAnkenModel();
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setNanCode(rs.getString("NAN_CODE"));
        bean.setNanName(rs.getString("NAN_NAME"));
        bean.setNanDetial(rs.getString("NAN_DETIAL"));
        bean.setNanDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_DATE")));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNgpSid(rs.getInt("NGP_SID"));
        bean.setNanMikomi(rs.getInt("NAN_MIKOMI"));
        bean.setNanKinMitumori(rs.getInt("NAN_KIN_MITUMORI"));
        bean.setNanKinJutyu(rs.getInt("NAN_KIN_JUTYU"));
        bean.setNanSyodan(rs.getInt("NAN_SYODAN"));
        bean.setNanState(rs.getInt("NAN_STATE"));
        bean.setNcnSid(rs.getInt("NCN_SID"));
        bean.setNanAuid(rs.getInt("NAN_AUID"));
        bean.setNanAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_ADATE")));
        bean.setNanEuid(rs.getInt("NAN_EUID"));
        bean.setNanEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_EDATE")));
        bean.setNanMitumoriDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_MITUMORI_DATE")));
        bean.setNanJutyuDate(UDate.getInstanceTimestamp(rs.getTimestamp("NAN_JUTYU_DATE")));
        bean.setNanPermitView(rs.getInt("NAN_PERMIT_VIEW"));
        bean.setNanPermitEdit(rs.getInt("NAN_PERMIT_EDIT"));
        return bean;
    }
}
