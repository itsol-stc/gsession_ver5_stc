package jp.groupsession.v2.ptl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ptl.model.PtlPortletImageModel;

/**
 * <p>PTL_PORTLET_IMAGE Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortletImageDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortletImageDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortletImageDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortletImageDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTLET_IMAGE");

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
            sql.addSql(" create table PTL_PORTLET_IMAGE (");
            sql.addSql("   PLT_SID integer not null,");
            sql.addSql("   PLI_SID bigint not null,");
            sql.addSql("   BIN_SID bigint not null,");
            sql.addSql("   PLI_NAME varchar(10) not null,");
            sql.addSql("   primary key (PLT_SID,PLI_SID)");
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
     * <p>Insert PTL_PORTLET_IMAGE Data Bindding JavaBean
     * @param bean PTL_PORTLET_IMAGE Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortletImageModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTLET_IMAGE(");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLI_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   PLI_NAME");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPltSid());
            sql.addLongValue(bean.getPliSid());
            sql.addLongValue(bean.getBinSid());
            sql.addStrValue(bean.getPliName());
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
     * <p>Update PTL_PORTLET_IMAGE Data Bindding JavaBean
     * @param bean PTL_PORTLET_IMAGE Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortletImageModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTLET_IMAGE");
            sql.addSql(" set ");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   PLI_NAME=?");
            sql.addSql(" where ");
            sql.addSql("   PLT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PLI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getBinSid());
            sql.addStrValue(bean.getPliName());
            //where
            sql.addIntValue(bean.getPltSid());
            sql.addLongValue(bean.getPliSid());

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
     * <p>Select PTL_PORTLET_IMAGE All Data
     * @return List in PTL_PORTLET_IMAGEModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortletImageModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortletImageModel> ret = new ArrayList<PtlPortletImageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLI_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   PLI_NAME");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTLET_IMAGE");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortletImageFromRs(rs));
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
     * <p>Select PTL_PORTLET_IMAGE
     * @param pltSid PLT_SID
     * @param pliSid PLI_SID
     * @return PTL_PORTLET_IMAGEModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortletImageModel select(int pltSid, long pliSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortletImageModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLI_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   PLI_NAME");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_IMAGE");
            sql.addSql(" where ");
            sql.addSql("   PLT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PLI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pltSid);
            sql.addLongValue(pliSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortletImageFromRs(rs);
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
     * <p>画像一覧を取得する。
     * @param pltSid PLT_SID
     * @return PTL_PORTLET_IMAGEModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortletImageModel> getImageList(int pltSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<PtlPortletImageModel> ret = new ArrayList<PtlPortletImageModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_PORTLET_IMAGE.PLT_SID,");
            sql.addSql("   PTL_PORTLET_IMAGE.PLI_SID,");
            sql.addSql("   PTL_PORTLET_IMAGE.BIN_SID,");
            sql.addSql("   PTL_PORTLET_IMAGE.PLI_NAME");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_IMAGE,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   PTL_PORTLET_IMAGE.PLT_SID=?");
            sql.addSql(" and");
            sql.addSql("   PTL_PORTLET_IMAGE.BIN_SID=CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pltSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortletImageFromRs(rs));
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
     * <p>Create PTL_PORTLET_IMAGE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortletImageModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortletImageModel __getPtlPortletImageFromRs(ResultSet rs) throws SQLException {
        PtlPortletImageModel bean = new PtlPortletImageModel();
        bean.setPltSid(rs.getInt("PLT_SID"));
        bean.setPliSid(rs.getInt("PLI_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setPliName(rs.getString("PLI_NAME"));
        return bean;
    }

    /**
     * 画像を論理削除する。
     * @param pltSid ポートレットSID
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateImgFlg(int pltSid)  throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (pltSid == 0) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("  CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = ?");
            sql.addSql(" where");
            sql.addSql("   CMN_BINF.BIN_SID in (");
            sql.addSql("   select");
            sql.addSql("     PTL_PORTLET_IMAGE.BIN_SID");
            sql.addSql("   from");
            sql.addSql("     PTL_PORTLET_IMAGE");
            sql.addSql("   where");
            sql.addSql("     PTL_PORTLET_IMAGE.PLT_SID = ?");
            sql.addSql("   )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(pltSid);

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
     * <br>[機  能] 指定したポートレットに関連するポートレット_画像情報を全て削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param pltSid ポートレットSID
     * @return int 削除件数
     * @throws SQLException
     */
    public int deletePortletImage(int pltSid)  throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (pltSid <= 0) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET_IMAGE");
            sql.addSql(" where");
            sql.addSql("   PLT_SID = ?");

            sql.addIntValue(pltSid);

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
}
