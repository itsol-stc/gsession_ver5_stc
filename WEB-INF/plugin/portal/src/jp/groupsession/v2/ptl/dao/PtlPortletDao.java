package jp.groupsession.v2.ptl.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <p>PTL_PORTLET Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PtlPortletDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PtlPortletDao.class);

    /**
     * <p>Default Constructor
     */
    public PtlPortletDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public PtlPortletDao(Connection con) {
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
            sql.addSql("drop table PTL_PORTLET");

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
            sql.addSql(" create table PTL_PORTLET (");
            sql.addSql("   PLT_SID integer not null,");
            sql.addSql("   PLT_NAME varchar(100) not null,");
            sql.addSql("   PLT_CONTENT clob not null,");
            sql.addSql("   PLT_CONTENT_TYPE smallint not null,");
            sql.addSql("   PLT_CONTENT_URL varchar(1000),");
            sql.addSql("   PLT_DESCRIPTION varchar(1000),");
            sql.addSql("   PLC_SID varchar(8) not null,");
            sql.addSql("   PLT_BORDER smallint not null,");
            sql.addSql("   PLT_AUID integer not null,");
            sql.addSql("   PLT_ADATE timestamp not null,");
            sql.addSql("   PLT_EUID integer not null,");
            sql.addSql("   PLT_EDATE timestamp not null,");
            sql.addSql("   primary key (PLT_SID)");
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
     * <p>Insert PTL_PORTLET Data Bindding JavaBean
     * @param bean PTL_PORTLET Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(PtlPortletModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" PTL_PORTLET(");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLT_NAME,");
            sql.addSql("   PLT_CONTENT,");
            sql.addSql("   PLT_CONTENT_TYPE,");
            sql.addSql("   PLT_CONTENT_URL,");
            sql.addSql("   PLT_DESCRIPTION,");
            sql.addSql("   PLC_SID,");
            sql.addSql("   PLT_BORDER,");
            sql.addSql("   PLT_AUID,");
            sql.addSql("   PLT_ADATE,");
            sql.addSql("   PLT_EUID,");
            sql.addSql("   PLT_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getPltSid());
            sql.addStrValue(bean.getPltName());
            sql.addStrValue(bean.getPltContent());
            sql.addIntValue(bean.getPltContentType());
            sql.addStrValue(bean.getPltContentUrl());
            sql.addStrValue(bean.getPltDescription());
            sql.addIntValue(bean.getPlcSid());
            sql.addIntValue(bean.getPltBorder());
            sql.addIntValue(bean.getPltAuid());
            sql.addDateValue(bean.getPltAdate());
            sql.addIntValue(bean.getPltEuid());
            sql.addDateValue(bean.getPltEdate());
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
     * <p>Update PTL_PORTLET Data Bindding JavaBean
     * @param bean PTL_PORTLET Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(PtlPortletModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTLET");
            sql.addSql(" set ");
            sql.addSql("   PLT_NAME=?,");
            sql.addSql("   PLT_CONTENT=?,");
            sql.addSql("   PLT_CONTENT_TYPE=?,");
            sql.addSql("   PLT_CONTENT_URL=?,");
            sql.addSql("   PLT_DESCRIPTION=?,");
            sql.addSql("   PLC_SID=?,");
            sql.addSql("   PLT_BORDER=?,");
            sql.addSql("   PLT_EUID=?,");
            sql.addSql("   PLT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   PLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getPltName());
            sql.addStrValue(bean.getPltContent());
            sql.addIntValue(bean.getPltContentType());
            sql.addStrValue(bean.getPltContentUrl());
            sql.addStrValue(bean.getPltDescription());
            sql.addIntValue(bean.getPlcSid());
            sql.addIntValue(bean.getPltBorder());
            sql.addIntValue(bean.getPltEuid());
            sql.addDateValue(bean.getPltEdate());
            //where
            sql.addIntValue(bean.getPltSid());

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
     * <br>[機  能] ポートレット更新
     * <br>[解  説] 指定カテゴリ内のポートレットを「カテゴリ未設定」に更新
     * <br>[備  考] 所属カテゴリが削除された場合に使用
     * @param plcSid カテゴリSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateInCategory(int plcSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   PTL_PORTLET");
            sql.addSql(" set ");
            sql.addSql("   PLC_SID=0");
            sql.addSql(" where ");
            sql.addSql("   PLC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(plcSid);

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
     * <p>Select PTL_PORTLET All Data
     * @return List in PTL_PORTLETModel
     * @throws SQLException SQL実行例外
     */
    public List<PtlPortletModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortletModel> ret = new ArrayList<PtlPortletModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLT_NAME,");
            sql.addSql("   PLT_CONTENT,");
            sql.addSql("   PLT_CONTENT_TYPE,");
            sql.addSql("   PLT_CONTENT_URL,");
            sql.addSql("   PLT_DESCRIPTION,");
            sql.addSql("   PLC_SID,");
            sql.addSql("   PLT_BORDER,");
            sql.addSql("   PLT_AUID,");
            sql.addSql("   PLT_ADATE,");
            sql.addSql("   PLT_EUID,");
            sql.addSql("   PLT_EDATE");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTLET");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getPtlPortletFromRs(rs));
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
     * <p>Select PTL_PORTLET
     * @param pltSid PLT_SID
     * @return PTL_PORTLETModel
     * @throws SQLException SQL実行例外
     */
    public PtlPortletModel select(int pltSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        PtlPortletModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PLT_SID,");
            sql.addSql("   PLT_NAME,");
            sql.addSql("   PLT_CONTENT,");
            sql.addSql("   PLT_CONTENT_TYPE,");
            sql.addSql("   PLT_CONTENT_URL,");
            sql.addSql("   PLT_DESCRIPTION,");
            sql.addSql("   PLC_SID,");
            sql.addSql("   PLT_BORDER,");
            sql.addSql("   PLT_AUID,");
            sql.addSql("   PLT_ADATE,");
            sql.addSql("   PLT_EUID,");
            sql.addSql("   PLT_EDATE");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET");
            sql.addSql(" where ");
            sql.addSql("   PLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pltSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getPtlPortletFromRs(rs);
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
     * <p>Delete PTL_PORTLET
     * @param pltSid PLT_SID
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int delete(int pltSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET");
            sql.addSql(" where ");
            sql.addSql("   PLT_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pltSid);

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
     * <p>Select PTL_PORTLET
     * @param plcSid カテゴリSID
     * @param reqMdl リクエスト情報
     * @return PTL_PORTLETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<PtlPortletModel> selectInCategory(
                      int plcSid, RequestModel reqMdl) throws SQLException {

        return selectInCategory(plcSid, 0, reqMdl);
    }

    /**
     * <p>Select PTL_PORTLET
     * @param plcSid カテゴリSID
     * @param sortKey ソートキー
     * @param reqMdl リクエスト情報
     * @return PTL_PORTLETModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<PtlPortletModel> selectInCategory(
            int plcSid, int sortKey, RequestModel reqMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<PtlPortletModel> ret = new ArrayList<PtlPortletModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_PORTLET.PLT_SID,");
            sql.addSql("   PTL_PORTLET.PLT_NAME,");
            sql.addSql("   PTL_PORTLET.PLT_CONTENT,");
            sql.addSql("   PTL_PORTLET.PLT_DESCRIPTION,");
            sql.addSql("   PTL_PORTLET.PLC_SID,");
            sql.addSql("   PTL_PORTLET.PLT_BORDER,");
            sql.addSql("   PTL_PORTLET.PLT_AUID,");
            sql.addSql("   PTL_PORTLET.PLT_ADATE,");
            sql.addSql("   PTL_PORTLET.PLT_EUID,");
            sql.addSql("   PTL_PORTLET.PLT_EDATE,");
            sql.addSql("   PTL_PORTLET_SORT.PLS_SORT,");
            sql.addSql("   PTL_PORTLET_CATEGORY.PLC_NAME");
            sql.addSql(" from");
            sql.addSql("   PTL_PORTLET");
            sql.addSql("   left join");
            sql.addSql("     PTL_PORTLET_SORT");
            sql.addSql("   on");
            sql.addSql("     PTL_PORTLET.PLT_SID = PTL_PORTLET_SORT.PLT_SID");
            sql.addSql("   left join");
            sql.addSql("     PTL_PORTLET_CATEGORY");
            sql.addSql("   on");
            sql.addSql("     PTL_PORTLET.PLC_SID = PTL_PORTLET_CATEGORY.PLC_SID");
            if (plcSid != -1) {
                sql.addSql(" where ");
                sql.addSql("   PTL_PORTLET.PLC_SID=" + plcSid);
            }

            sql.addSql(" order by ");
            if (sortKey == 1) {
                if (plcSid == -1) {
                    sql.addSql("   PTL_PORTLET_CATEGORY.PLC_NAME asc,");
                }
                sql.addSql("   PTL_PORTLET.PLT_NAME asc,");
            }
            sql.addSql("   PLS_SORT asc");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                PtlPortletModel bean = new PtlPortletModel();
                bean.setPltSid(rs.getInt("PLT_SID"));
                bean.setPltName(rs.getString("PLT_NAME"));
                bean.setPltDescription(rs.getString("PLT_DESCRIPTION"));
                bean.setPlcSid(rs.getInt("PLC_SID"));
                bean.setPltSort(rs.getInt("PLS_SORT"));
                bean.setPlcName(NullDefault.getString(
                        rs.getString("PLC_NAME"), gsMsg.getMessage("cmn.category.no")));
                ret.add(bean);
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
     * <p>Create PTL_PORTLET Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created PtlPortletModel
     * @throws SQLException SQL実行例外
     */
    private PtlPortletModel __getPtlPortletFromRs(ResultSet rs) throws SQLException {
        PtlPortletModel bean = new PtlPortletModel();
        bean.setPltSid(rs.getInt("PLT_SID"));
        bean.setPltName(rs.getString("PLT_NAME"));
        bean.setPltContent(rs.getString("PLT_CONTENT"));
        bean.setPltContentType(rs.getInt("PLT_CONTENT_TYPE"));
        bean.setPltContentUrl(rs.getString("PLT_CONTENT_URL"));
        bean.setPltDescription(rs.getString("PLT_DESCRIPTION"));
        bean.setPlcSid(rs.getInt("PLC_SID"));
        bean.setPltBorder(rs.getInt("PLT_BORDER"));
        bean.setPltAuid(rs.getInt("PLT_AUID"));
        bean.setPltAdate(UDate.getInstanceTimestamp(rs.getTimestamp("PLT_ADATE")));
        bean.setPltEuid(rs.getInt("PLT_EUID"));
        bean.setPltEdate(UDate.getInstanceTimestamp(rs.getTimestamp("PLT_EDATE")));
        return bean;
    }
}
