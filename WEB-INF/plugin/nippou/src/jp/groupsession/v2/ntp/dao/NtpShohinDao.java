package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.model.NtpShohinModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>NTP_SHOHIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpShohinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpShohinDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpShohinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpShohinDao(Connection con) {
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
            sql.addSql("drop table NTP_SHOHIN");

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
            sql.addSql(" create table NTP_SHOHIN (");
            sql.addSql("   NHN_SID NUMBER(10,0) not null,");
            sql.addSql("   NHN_CODE varchar(5) not null,");
            sql.addSql("   NHN_NAME varchar(50) not null,");
            sql.addSql("   NHN_PRICE_SALE NUMBER(10,0),");
            sql.addSql("   NHN_PRICE_COST NUMBER(10,0),");
            sql.addSql("   NHN_HOSOKU varchar(1000),");
            sql.addSql("   NHN_AUID NUMBER(10,0),");
            sql.addSql("   NHN_ADATE varchar(23),");
            sql.addSql("   NHN_EUID NUMBER(10,0),");
            sql.addSql("   NHN_EDATE varchar(23),");
            sql.addSql("   NSC_SID NUMBER(10,0) not null,");
            sql.addSql("   primary key (NHN_SID)");
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
     * <p>Insert NTP_SHOHIN Data Bindding JavaBean
     * @param bean NTP_SHOHIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpShohinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_SHOHIN(");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NHN_CODE,");
            sql.addSql("   NHN_NAME,");
            sql.addSql("   NHN_PRICE_SALE,");
            sql.addSql("   NHN_PRICE_COST,");
            sql.addSql("   NHN_HOSOKU,");
            sql.addSql("   NHN_AUID,");
            sql.addSql("   NHN_ADATE,");
            sql.addSql("   NHN_EUID,");
            sql.addSql("   NHN_EDATE,");
            sql.addSql("   NSC_SID");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNhnSid());
            sql.addStrValue(bean.getNhnCode());
            sql.addStrValue(bean.getNhnName());
            sql.addIntValue(bean.getNhnPriceSale());
            sql.addIntValue(bean.getNhnPriceCost());
            sql.addStrValue(bean.getNhnHosoku());
            sql.addIntValue(bean.getNhnAuid());
            sql.addDateValue(bean.getNhnAdate());
            sql.addIntValue(bean.getNhnEuid());
            sql.addDateValue(bean.getNhnEdate());
            sql.addIntValue(bean.getNscSid());
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
     * <p>Update NTP_SHOHIN Data Bindding JavaBean
     * @param bean NTP_SHOHIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpShohinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" set ");
            sql.addSql("   NHN_CODE=?,");
            sql.addSql("   NHN_NAME=?,");
            sql.addSql("   NHN_PRICE_SALE=?,");
            sql.addSql("   NHN_PRICE_COST=?,");
            sql.addSql("   NHN_HOSOKU=?,");
            sql.addSql("   NHN_EUID=?,");
            sql.addSql("   NHN_EDATE=?,");
            sql.addSql("   NSC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getNhnCode());
            sql.addStrValue(bean.getNhnName());
            sql.addIntValue(bean.getNhnPriceSale());
            sql.addIntValue(bean.getNhnPriceCost());
            sql.addStrValue(bean.getNhnHosoku());
            sql.addIntValue(bean.getNhnEuid());
            sql.addDateValue(bean.getNhnEdate());
            sql.addIntValue(bean.getNscSid());
            //where
            sql.addIntValue(bean.getNhnSid());

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
     * <p>Select NTP_SHOHIN All Data
     * @return List in NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpShohinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpShohinModel> ret = new ArrayList<NtpShohinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NHN_CODE,");
            sql.addSql("   NHN_NAME,");
            sql.addSql("   NHN_PRICE_SALE,");
            sql.addSql("   NHN_PRICE_COST,");
            sql.addSql("   NHN_HOSOKU,");
            sql.addSql("   NHN_AUID,");
            sql.addSql("   NHN_ADATE,");
            sql.addSql("   NHN_EUID,");
            sql.addSql("   NHN_EDATE,");
            sql.addSql("   NSC_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_SHOHIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpShohinFromRs(rs));
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
     * <p>Select NTP_SHOHIN
     * @param nhnSid NHN_SID
     * @return NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public NtpShohinModel select(int nhnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpShohinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NHN_CODE,");
            sql.addSql("   NHN_NAME,");
            sql.addSql("   NHN_PRICE_SALE,");
            sql.addSql("   NHN_PRICE_COST,");
            sql.addSql("   NHN_HOSOKU,");
            sql.addSql("   NHN_AUID,");
            sql.addSql("   NHN_ADATE,");
            sql.addSql("   NHN_EUID,");
            sql.addSql("   NHN_EDATE,");
            sql.addSql("   NSC_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nhnSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpShohinFromRs(rs);
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
     * <p>Select NTP_SHOHIN
     * @param nscSid NSC_SID
     * @return NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpShohinModel> selectCategory(int nscSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpShohinModel> ret = new ArrayList<NtpShohinModel>();
        NtpShohinModel mdl = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NHN_CODE,");
            sql.addSql("   NHN_NAME,");
            sql.addSql("   NHN_PRICE_SALE,");
            sql.addSql("   NHN_PRICE_COST,");
            sql.addSql("   NHN_HOSOKU,");
            sql.addSql("   NHN_AUID,");
            sql.addSql("   NHN_ADATE,");
            sql.addSql("   NHN_EUID,");
            sql.addSql("   NHN_EDATE,");
            sql.addSql("   NSC_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NSC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nscSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mdl = new NtpShohinModel();
                mdl = __getNtpShohinFromRs(rs);
                ret.add(mdl);
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
     * <p>Select NTP_SHOHIN
     * @param nhnSid NHN_SID
     * @return NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpShohinModel> select(String[] nhnSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpShohinModel> ret = new ArrayList<NtpShohinModel>();
        con = getCon();

        if (nhnSid.length > 0) {
            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   NHN_SID,");
                sql.addSql("   NHN_CODE,");
                sql.addSql("   NHN_NAME,");
                sql.addSql("   NHN_PRICE_SALE,");
                sql.addSql("   NHN_PRICE_COST,");
                sql.addSql("   NHN_HOSOKU,");
                sql.addSql("   NHN_AUID,");
                sql.addSql("   NHN_ADATE,");
                sql.addSql("   NHN_EUID,");
                sql.addSql("   NHN_EDATE,");
                sql.addSql("   NSC_SID");
                sql.addSql(" from");
                sql.addSql("   NTP_SHOHIN");
                sql.addSql(" where ");
                sql.addSql("   NHN_SID in (");
                for (int i = 0; i < nhnSid.length; i++) {
                    if (i > 0) {
                        sql.addSql("     , ");
                    }
                    sql.addSql("     ?");
                    sql.addIntValue(Integer.parseInt(nhnSid[i]));
                }
                sql.addSql("   )");

                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    ret.add(__getNtpShohinFromRs(rs));
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定した商品コードに該当する商品情報が存在するかをチェックする
     * <br>[解  説] 指定した商品SID > 0 の場合、指定した商品SID以外を存在チェックの条件とする
     * <br>[備  考]
     * @param nhnSid 商品SID
     * @param nhnCode 商品コード
     * @return 判定結果 true:存在する false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existShohin(int nhnSid, String nhnCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(NHN_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NHN_CODE=?");
            sql.addStrValue(nhnCode);

            if (nhnSid > 0) {
                sql.addSql(" and ");
                sql.addSql("   NHN_SID != ?");
                sql.addIntValue(nhnSid);
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
     * <p>商品コードより商品情報を取得する。
     * @param shohinCode 商品コード
     * @return NTP_SHOHINModel
     * @throws SQLException SQL実行例外
     */
    public NtpShohinModel getShohinInf(String shohinCode) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpShohinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NHN_CODE,");
            sql.addSql("   NHN_NAME,");
            sql.addSql("   NHN_PRICE_SALE,");
            sql.addSql("   NHN_PRICE_COST,");
            sql.addSql("   NHN_HOSOKU,");
            sql.addSql("   NHN_AUID,");
            sql.addSql("   NHN_ADATE,");
            sql.addSql("   NHN_EUID,");
            sql.addSql("   NHN_EDATE,");
            sql.addSql("   NSC_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NHN_CODE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(shohinCode);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpShohinFromRs(rs);
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
     * <p>カテゴリ内の商品一覧を取得
     * @param nscSid 商品カテゴリSID
     * @return List in ADR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpShohinModel> getShohinInCategory(int nscSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpShohinModel> ret = new ArrayList<NtpShohinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NHN_SID,");
            sql.addSql("   NHN_CODE,");
            sql.addSql("   NHN_NAME,");
            sql.addSql("   NHN_PRICE_SALE,");
            sql.addSql("   NHN_PRICE_COST,");
            sql.addSql("   NHN_HOSOKU,");
            sql.addSql("   NHN_AUID,");
            sql.addSql("   NHN_ADATE,");
            sql.addSql("   NHN_EUID,");
            sql.addSql("   NHN_EDATE,");
            sql.addSql("   NSC_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NSC_SID=?");

            sql.addIntValue(nscSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpShohinFromRs(rs));
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
     * <p>Delete NTP_SHOHIN
     * @param nhnSid NHN_SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int delete(int nhnSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nhnSid);

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
     * <p>削除されたカテゴリ内の商品を「未選択」カテゴリに移動します。
     * @param shnMdl 商品モデル
     * @throws SQLException SQL実行例外
     */
    public void deleteCatAndShohin(NtpShohinModel shnMdl) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //カテゴリを「未設定」のカテゴリに変更
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" update");
            sql.addSql("   NTP_SHOHIN");
            sql.addSql(" set ");
            sql.addSql("   NSC_SID=?");
            sql.addSql(" where ");
            sql.addSql("   NHN_SID=?");

            sql.addIntValue(GSConstNippou.SHOHIN_CATEGORY_NOSET);
            sql.addIntValue(shnMdl.getNhnSid());

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
     * <p>Create NTP_SHOHIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpShohinModel
     * @throws SQLException SQL実行例外
     */
    private NtpShohinModel __getNtpShohinFromRs(ResultSet rs) throws SQLException {
        NtpShohinModel bean = new NtpShohinModel();
        bean.setNhnSid(rs.getInt("NHN_SID"));
        bean.setNhnCode(rs.getString("NHN_CODE"));
        bean.setNhnName(rs.getString("NHN_NAME"));
        bean.setNhnPriceSale(rs.getInt("NHN_PRICE_SALE"));
        bean.setNhnPriceCost(rs.getInt("NHN_PRICE_COST"));
        bean.setNhnHosoku(rs.getString("NHN_HOSOKU"));
        bean.setNhnAuid(rs.getInt("NHN_AUID"));
        bean.setNhnAdate(UDate.getInstanceTimestamp(rs.getTimestamp("NHN_ADATE")));
        bean.setNhnEuid(rs.getInt("NHN_EUID"));
        bean.setNhnEdate(UDate.getInstanceTimestamp(rs.getTimestamp("NHN_EDATE")));
        bean.setNscSid(rs.getInt("NSC_SID"));
        return bean;
    }

}
