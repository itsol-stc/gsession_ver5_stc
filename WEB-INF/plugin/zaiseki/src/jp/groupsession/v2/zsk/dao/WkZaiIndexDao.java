package jp.groupsession.v2.zsk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.model.WkZaiIndexModel;
import jp.groupsession.v2.zsk.model.ZaiIndexModel;
import jp.groupsession.v2.zsk.zsk050.Zsk050ElementModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>WK_ZAI_INDEX Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WkZaiIndexDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WkZaiIndexDao.class);

    /**
     * <p>Default Constructor
     */
    public WkZaiIndexDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public WkZaiIndexDao(Connection con) {
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
            sql.addSql("drop table WK_ZAI_INDEX");

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
            sql.addSql(" create table WK_ZAI_INDEX (");
            sql.addSql("   WZI_SESSION_SID varchar(50) not null,");
            sql.addSql("   WZI_KEY varchar(50) not null,");
            sql.addSql("   WZI_SID NUMBER(10,0) not null,");
            sql.addSql("   WZI_LINKKBN NUMBER(10,0) not null,");
            sql.addSql("   WZI_LINKSID NUMBER(10,0) not null,");
            sql.addSql("   WZI_NAME varchar(50) not null,");
            sql.addSql("   WZI_BGCOLOR NUMBER(10,0) not null,");
            sql.addSql("   WZI_MSG varchar(1000),");
            sql.addSql("   WZI_XINDEX NUMBER(10,0) not null,");
            sql.addSql("   WZI_YINDEX NUMBER(10,0) not null,");
            sql.addSql("   WZI_OTHER_VALUE varchar(20) not null");
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
     * <p>Insert WK_ZAI_INDEX Data Bindding JavaBean
     * @param bean WK_ZAI_INDEX Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(WkZaiIndexModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WK_ZAI_INDEX(");
            sql.addSql("   WZI_SESSION_SID,");
            sql.addSql("   WZI_KEY,");
            sql.addSql("   WZI_SID,");
            sql.addSql("   WZI_LINKKBN,");
            sql.addSql("   WZI_LINKSID,");
            sql.addSql("   WZI_NAME,");
            sql.addSql("   WZI_BGCOLOR,");
            sql.addSql("   WZI_MSG,");
            sql.addSql("   WZI_XINDEX,");
            sql.addSql("   WZI_YINDEX,");
            sql.addSql("   WZI_OTHER_VALUE");
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
            sql.addStrValue(bean.getWziSessionSid());
            sql.addStrValue(bean.getWziKey());
            sql.addIntValue(bean.getWziSid());
            sql.addIntValue(bean.getWziLinkkbn());
            sql.addIntValue(bean.getWziLinksid());
            sql.addStrValue(bean.getWziName());
            sql.addIntValue(bean.getWziBgcolor());
            sql.addStrValue(bean.getWziMsg());
            sql.addIntValue(bean.getWziXindex());
            sql.addIntValue(bean.getWziYindex());
            sql.addStrValue(bean.getWziOtherValue());
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
     * <p>Update WK_ZAI_INDEX Data Bindding JavaBean
     * @param bean WK_ZAI_INDEX Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int 更新件数
     */
    public int update(WkZaiIndexModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WK_ZAI_INDEX");
            sql.addSql(" set ");
            sql.addSql("   WZI_SESSION_SID=?,");
            sql.addSql("   WZI_KEY=?,");
            sql.addSql("   WZI_SID=?,");
            sql.addSql("   WZI_LINKKBN=?,");
            sql.addSql("   WZI_LINKSID=?,");
            sql.addSql("   WZI_NAME=?,");
            sql.addSql("   WZI_BGCOLOR=?,");
            sql.addSql("   WZI_MSG=?,");
            sql.addSql("   WZI_XINDEX=?,");
            sql.addSql("   WZI_YINDEX=?,");
            sql.addSql("   WZI_OTHER_VALUE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getWziSessionSid());
            sql.addStrValue(bean.getWziKey());
            sql.addIntValue(bean.getWziSid());
            sql.addIntValue(bean.getWziLinkkbn());
            sql.addIntValue(bean.getWziLinksid());
            sql.addStrValue(bean.getWziName());
            sql.addIntValue(bean.getWziBgcolor());
            sql.addStrValue(bean.getWziMsg());
            sql.addIntValue(bean.getWziXindex());
            sql.addIntValue(bean.getWziYindex());
            sql.addStrValue(bean.getWziOtherValue());

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
     * <p>セッションIDとエレメントKEYを指定し一時保存情報の座標部分を更新する
     * @param sessionId セッションID
     * @param zifSid 座席表SID
     * @param elKey エレメントKEY
     * @param bean 更新内容
     * @throws SQLException SQL実行例外
     * @return int 更新件数
     */
    public int updateWkZaisekiIndex(
            String sessionId,
            int zifSid,
            String elKey,
            WkZaiIndexModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   WK_ZAI_INDEX");
            sql.addSql(" set ");
            sql.addSql("   WZI_XINDEX=?,");
            sql.addSql("   WZI_YINDEX=?");
            sql.addSql(" where ");
            sql.addSql("   WZI_SESSION_SID=?");
            sql.addSql(" and ");
            sql.addSql("   WZI_SID=?");
            sql.addSql(" and ");
            sql.addSql("   WZI_KEY=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //更新内容
            sql.addIntValue(bean.getWziXindex());
            sql.addIntValue(bean.getWziYindex());
            //検索条件
            sql.addStrValue(sessionId);
            sql.addIntValue(zifSid);
            sql.addStrValue(elKey);

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
     * <p>Select WK_ZAI_INDEX All Data
     * @return List in WK_ZAI_INDEXModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<WkZaiIndexModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<WkZaiIndexModel> ret = new ArrayList<WkZaiIndexModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WZI_SESSION_SID,");
            sql.addSql("   WZI_KEY,");
            sql.addSql("   WZI_SID,");
            sql.addSql("   WZI_LINKKBN,");
            sql.addSql("   WZI_LINKSID,");
            sql.addSql("   WZI_NAME,");
            sql.addSql("   WZI_BGCOLOR,");
            sql.addSql("   WZI_MSG,");
            sql.addSql("   WZI_XINDEX,");
            sql.addSql("   WZI_YINDEX,");
            sql.addSql("   WZI_OTHER_VALUE");
            sql.addSql(" from ");
            sql.addSql("   WK_ZAI_INDEX");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getWkZaiIndexFromRs(rs));
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
     * <p>セッションIDと座席表SIDを指定し画面項目情報を取得する
     * @param sessionId セッションID
     * @param zifSid 編集中の座席表SID
     * @return List in WK_ZAI_INDEXModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Zsk050ElementModel> getEdittingIndex(String sessionId, int zifSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Zsk050ElementModel> ret = new ArrayList<Zsk050ElementModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WZI_SESSION_SID,");
            sql.addSql("   WZI_KEY,");
            sql.addSql("   WZI_SID,");
            sql.addSql("   WZI_LINKKBN,");
            sql.addSql("   WZI_LINKSID,");
            sql.addSql("   WZI_NAME,");
            sql.addSql("   WZI_BGCOLOR,");
            sql.addSql("   WZI_MSG,");
            sql.addSql("   WZI_XINDEX,");
            sql.addSql("   WZI_YINDEX,");
            sql.addSql("   WZI_OTHER_VALUE,");
            sql.addSql("   USR_JKBN,");
            sql.addSql("   USR_UKO_FLG");
            sql.addSql(" from ");
            sql.addSql("   WK_ZAI_INDEX");
            sql.addSql("   left join CMN_USRM");
            sql.addSql("   on (WK_ZAI_INDEX.WZI_LINKSID = CMN_USRM.USR_SID");
            sql.addSql("      and WK_ZAI_INDEX.WZI_LINKKBN = ?)");
            sql.addSql(" where ");
            sql.addSql("   WZI_SESSION_SID=?");
            sql.addSql(" and ");
            sql.addSql("   WZI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstZaiseki.ELEMENT_KBN_USR);
            sql.addStrValue(sessionId);
            sql.addIntValue(zifSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getZsk050ElementFromRs(rs));
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
     * <p>セッションIDと座席表SIDを指定し画面項目情報を取得する
     * @param sessionId セッションID
     * @param zifSid 編集中の座席表SID
     * @return List in WK_ZAI_INDEXModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ZaiIndexModel> getZaiIndexModelList(String sessionId, int zifSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ZaiIndexModel> ret = new ArrayList<ZaiIndexModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WZI_SESSION_SID,");
            sql.addSql("   WZI_KEY,");
            sql.addSql("   WZI_SID,");
            sql.addSql("   WZI_LINKKBN,");
            sql.addSql("   WZI_LINKSID,");
            sql.addSql("   WZI_NAME,");
            sql.addSql("   WZI_BGCOLOR,");
            sql.addSql("   WZI_MSG,");
            sql.addSql("   WZI_XINDEX,");
            sql.addSql("   WZI_YINDEX,");
            sql.addSql("   WZI_OTHER_VALUE");
            sql.addSql(" from ");
            sql.addSql("   WK_ZAI_INDEX");
            sql.addSql(" where ");
            sql.addSql("   WZI_SESSION_SID=?");
            sql.addSql(" and ");
            sql.addSql("   WZI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(sessionId);
            sql.addIntValue(zifSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getZaiIndexFromRs(rs));
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
     * <p>Select WK_ZAI_INDEX
     * @param sessionId セッションID
     * @param zaiSid 座席表SID
     * @param elKey エレメントKey
     * @return WK_ZAI_INDEXModel
     * @throws SQLException SQL実行例外
     */
    public Zsk050ElementModel getWkZasekiIndex(String sessionId, int zaiSid, String elKey)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Zsk050ElementModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WZI_SESSION_SID,");
            sql.addSql("   WZI_KEY,");
            sql.addSql("   WZI_SID,");
            sql.addSql("   WZI_LINKKBN,");
            sql.addSql("   WZI_LINKSID,");
            sql.addSql("   WZI_NAME,");
            sql.addSql("   WZI_BGCOLOR,");
            sql.addSql("   WZI_MSG,");
            sql.addSql("   WZI_XINDEX,");
            sql.addSql("   WZI_YINDEX,");
            sql.addSql("   WZI_OTHER_VALUE,");
            sql.addSql("   USR_JKBN,");
            sql.addSql("   USR_UKO_FLG");
            sql.addSql(" from");
            sql.addSql("   WK_ZAI_INDEX");
            sql.addSql("   left join CMN_USRM");
            sql.addSql("   on (WK_ZAI_INDEX.WZI_LINKSID = CMN_USRM.USR_SID");
            sql.addSql("       and WK_ZAI_INDEX.WZI_LINKKBN = ?)");
            sql.addSql(" where");
            sql.addSql("   WZI_SESSION_SID=?");
            sql.addSql(" and");
            sql.addSql("   WZI_SID=?");
            sql.addSql(" and");
            sql.addSql("   WZI_KEY=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstZaiseki.ELEMENT_KBN_USR);
            sql.addStrValue(sessionId);
            sql.addIntValue(zaiSid);
            sql.addStrValue(elKey);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getZsk050ElementFromRs(rs);
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
     * <p>Delete WK_ZAI_INDEX
     * @param bean WK_ZAI_INDEX Model
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int delete(WkZaiIndexModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WK_ZAI_INDEX");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <p>Delete WK_ZAI_INDEX
     * @param sessionId セッッション
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int deleteSessionWk(String sessionId) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WK_ZAI_INDEX");
            sql.addSql(" where");
            sql.addSql("   WZI_SESSION_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(sessionId);

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
     * <p>Delete WK_ZAI_INDEX
     * @param sessionId セッションID
     * @param elKey エレメントKEY
     * @param zifSid 座席表SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteSessionWk(String sessionId, int zifSid, String elKey)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   WK_ZAI_INDEX");
            sql.addSql(" where");
            sql.addSql("   WZI_SESSION_SID=?");
            sql.addSql(" and");
            sql.addSql("   WZI_SID=?");
            sql.addSql(" and");
            sql.addSql("   WZI_KEY=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(sessionId);
            sql.addIntValue(zifSid);
            sql.addStrValue(elKey);
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
     * <p>Create WK_ZAI_INDEX Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created Zsk050ElementModel
     * @throws SQLException SQL実行例外
     */
    private Zsk050ElementModel __getZsk050ElementFromRs(ResultSet rs) throws SQLException {
        Zsk050ElementModel bean = new Zsk050ElementModel();
        __setWkZaiIndexFromRs(rs, bean);
        if (bean.getWziLinkkbn() == GSConstZaiseki.ELEMENT_KBN_USR) {
            bean.setUser(
                    new UsrLabelValueBean(bean.getWziName(),
                            String.valueOf(bean.getWziLinksid()),
                            rs.getInt("USR_UKO_FLG")
                            )
                    );
        }
        return bean;
    }

    /**
     * <p>Create WK_ZAI_INDEX Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created WkZaiIndexModel
     * @throws SQLException SQL実行例外
     */
    private WkZaiIndexModel __getWkZaiIndexFromRs(ResultSet rs) throws SQLException {
        WkZaiIndexModel bean = new WkZaiIndexModel();
        __setWkZaiIndexFromRs(rs, bean);
        return bean;
    }
    /**
     * <p>Create WK_ZAI_INDEX Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @param bean WK_ZAI_INDEXモデル
     * @return created WkZaiIndexModel
     * @throws SQLException SQL実行例外
     */
    private WkZaiIndexModel __setWkZaiIndexFromRs(ResultSet rs,
            WkZaiIndexModel bean) throws SQLException {

        bean.setWziSessionSid(rs.getString("WZI_SESSION_SID"));
        bean.setWziKey(rs.getString("WZI_KEY"));
        bean.setWziSid(rs.getInt("WZI_SID"));
        bean.setWziLinkkbn(rs.getInt("WZI_LINKKBN"));
        bean.setWziLinksid(rs.getInt("WZI_LINKSID"));
        bean.setWziName(rs.getString("WZI_NAME"));
        bean.setWziBgcolor(rs.getInt("WZI_BGCOLOR"));
        bean.setWziMsg(rs.getString("WZI_MSG"));
        bean.setWziXindex(rs.getInt("WZI_XINDEX"));
        bean.setWziYindex(rs.getInt("WZI_YINDEX"));
        bean.setWziOtherValue(rs.getString("WZI_OTHER_VALUE"));
        return bean;
    }
    /**
     * <p>Create WK_ZAI_INDEX Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ZaiIndexModel
     * @throws SQLException SQL実行例外
     */
    private ZaiIndexModel __getZaiIndexFromRs(ResultSet rs) throws SQLException {
        ZaiIndexModel bean = new ZaiIndexModel();
        bean.setZifSid(rs.getInt("WZI_SID"));
        bean.setZinLinkkbn(rs.getInt("WZI_LINKKBN"));
        bean.setZinLinksid(rs.getInt("WZI_LINKSID"));
        bean.setZinName(rs.getString("WZI_NAME"));
        bean.setZinBgcolor(rs.getInt("WZI_BGCOLOR"));
        bean.setZinMsg(rs.getString("WZI_MSG"));
        bean.setZinXindex(rs.getInt("WZI_XINDEX"));
        bean.setZinYindex(rs.getInt("WZI_YINDEX"));
        bean.setZinOtherValue(rs.getString("WZI_OTHER_VALUE"));
        return bean;
    }
}
