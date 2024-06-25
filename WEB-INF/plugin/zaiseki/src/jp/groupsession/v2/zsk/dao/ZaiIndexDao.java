package jp.groupsession.v2.zsk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.biz.ZsjCommonBiz;
import jp.groupsession.v2.zsk.model.ZaiIndexModel;
import jp.groupsession.v2.zsk.model.ZaiIndexPlusModel;

/**
 * <p>ZAI_INDEX Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiIndexDao extends AbstractDao {

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     */
    public ZaiIndexDao(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
   }

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZaiIndexDao.class);

    /**
     * <p>Default Constructor
     */
    public ZaiIndexDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ZaiIndexDao(Connection con) {
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
            sql.addSql("drop table ZAI_INDEX");

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
            sql.addSql(" create table ZAI_INDEX (");
            sql.addSql("   ZIF_SID NUMBER(10,0) not null,");
            sql.addSql("   ZIN_LINKKBN NUMBER(10,0) not null,");
            sql.addSql("   ZIN_LINKSID NUMBER(10,0) not null,");
            sql.addSql("   ZIN_NAME varchar(50) not null,");
            sql.addSql("   ZIN_BGCOLOR NUMBER(10,0) not null,");
            sql.addSql("   ZIN_MSG varchar(1000),");
            sql.addSql("   ZIN_XINDEX NUMBER(10,0) not null,");
            sql.addSql("   ZIN_YINDEX NUMBER(10,0) not null,");
            sql.addSql("   ZIN_OTHER_VALUE varchar(20) not null,");
            sql.addSql("   ZIN_AUID NUMBER(10,0) not null,");
            sql.addSql("   ZIN_ADATE varchar(23) not null,");
            sql.addSql("   ZIN_EUID NUMBER(10,0) not null,");
            sql.addSql("   ZIN_EDATE varchar(23) not null");
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
     * <p>Insert ZAI_INDEX Data Bindding JavaBean
     * @param bean ZAI_INDEX Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ZaiIndexModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ZAI_INDEX(");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID,");
            sql.addSql("   ZIN_NAME,");
            sql.addSql("   ZIN_BGCOLOR,");
            sql.addSql("   ZIN_MSG,");
            sql.addSql("   ZIN_XINDEX,");
            sql.addSql("   ZIN_YINDEX,");
            sql.addSql("   ZIN_OTHER_VALUE,");
            sql.addSql("   ZIN_AUID,");
            sql.addSql("   ZIN_ADATE,");
            sql.addSql("   ZIN_EUID,");
            sql.addSql("   ZIN_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZifSid());
            sql.addIntValue(bean.getZinLinkkbn());
            sql.addIntValue(bean.getZinLinksid());
            sql.addStrValue(bean.getZinName());
            sql.addIntValue(bean.getZinBgcolor());
            sql.addStrValue(bean.getZinMsg());
            sql.addIntValue(bean.getZinXindex());
            sql.addIntValue(bean.getZinYindex());
            sql.addStrValue(bean.getZinOtherValue());
            sql.addIntValue(bean.getZinAuid());
            sql.addDateValue(bean.getZinAdate());
            sql.addIntValue(bean.getZinEuid());
            sql.addDateValue(bean.getZinEdate());
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
     * <p>Insert ZAI_INDEX Data Bindding JavaBean
     * @param sessionId セッションSID
     * @param zifSid 座席表SID
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void selectInsert(String sessionId, int zifSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        UDate now = new UDate();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" ZAI_INDEX(");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID,");
            sql.addSql("   ZIN_NAME,");
            sql.addSql("   ZIN_BGCOLOR,");
            sql.addSql("   ZIN_MSG,");
            sql.addSql("   ZIN_XINDEX,");
            sql.addSql("   ZIN_YINDEX,");
            sql.addSql("   ZIN_OTHER_VALUE,");
            sql.addSql("   ZIN_AUID,");
            sql.addSql("   ZIN_ADATE,");
            sql.addSql("   ZIN_EUID,");
            sql.addSql("   ZIN_EDATE");
            sql.addSql(" )");
            sql.addSql(" ");
            sql.addSql(" (");
            sql.addSql(" select ");
            sql.addSql("   WZI_SID,");
            sql.addSql("   WZI_LINKKBN,");
            sql.addSql("   WZI_LINKSID,");
            sql.addSql("   WZI_NAME,");
            sql.addSql("   WZI_BGCOLOR,");
            sql.addSql("   WZI_MSG,");
            sql.addSql("   WZI_XINDEX,");
            sql.addSql("   WZI_YINDEX,");
            sql.addSql("   WZI_OTHER_VALUE,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" from ");
            sql.addSql("   WK_ZAI_INDEX");
            sql.addSql(" where ");
            sql.addSql("   WZI_SESSION_SID=?");
            sql.addSql(" and ");
            sql.addSql("   WZI_SID=?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            sql.addDateValue(now);
            sql.addIntValue(userSid);
            sql.addDateValue(now);
            sql.addStrValue(sessionId);
            sql.addIntValue(zifSid);
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
     * <p>Update ZAI_INDEX Data Bindding JavaBean
     * @param bean ZAI_INDEX Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return int 更新件数
     */
    public int update(ZaiIndexModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ZAI_INDEX");
            sql.addSql(" set ");
            sql.addSql("   ZIF_SID=?,");
            sql.addSql("   ZIN_LINKKBN=?,");
            sql.addSql("   ZIN_LINKSID=?,");
            sql.addSql("   ZIN_NAME=?,");
            sql.addSql("   ZIN_BGCOLOR=?,");
            sql.addSql("   ZIN_MSG=?,");
            sql.addSql("   ZIN_XINDEX=?,");
            sql.addSql("   ZIN_YINDEX=?,");
            sql.addSql("   ZIN_OTHER_VALUE=?,");
            sql.addSql("   ZIN_AUID=?,");
            sql.addSql("   ZIN_ADATE=?,");
            sql.addSql("   ZIN_EUID=?,");
            sql.addSql("   ZIN_EDATE=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getZifSid());
            sql.addIntValue(bean.getZinLinkkbn());
            sql.addIntValue(bean.getZinLinksid());
            sql.addStrValue(bean.getZinName());
            sql.addIntValue(bean.getZinBgcolor());
            sql.addStrValue(bean.getZinMsg());
            sql.addIntValue(bean.getZinXindex());
            sql.addIntValue(bean.getZinYindex());
            sql.addStrValue(bean.getZinOtherValue());
            sql.addIntValue(bean.getZinAuid());
            sql.addDateValue(bean.getZinAdate());
            sql.addIntValue(bean.getZinEuid());
            sql.addDateValue(bean.getZinEdate());

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
     * <p>Select ZAI_INDEX
     * @return List in ZAI_INDEXModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ZaiIndexModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ZaiIndexModel> ret = new ArrayList<ZaiIndexModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID,");
            sql.addSql("   ZIN_NAME,");
            sql.addSql("   ZIN_BGCOLOR,");
            sql.addSql("   ZIN_MSG,");
            sql.addSql("   ZIN_XINDEX,");
            sql.addSql("   ZIN_YINDEX,");
            sql.addSql("   ZIN_OTHER_VALUE,");
            sql.addSql("   ZIN_AUID,");
            sql.addSql("   ZIN_ADATE,");
            sql.addSql("   ZIN_EUID,");
            sql.addSql("   ZIN_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ZAI_INDEX");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select ZAI_INDEX
     * @param zifSid 座席表SID
     * @return List in ZAI_INDEXModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ZaiIndexModel> select(int zifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ZaiIndexModel> ret = new ArrayList<ZaiIndexModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID,");
            sql.addSql("   ZIN_NAME,");
            sql.addSql("   ZIN_BGCOLOR,");
            sql.addSql("   ZIN_MSG,");
            sql.addSql("   ZIN_XINDEX,");
            sql.addSql("   ZIN_YINDEX,");
            sql.addSql("   ZIN_OTHER_VALUE,");
            sql.addSql("   ZIN_AUID,");
            sql.addSql("   ZIN_ADATE,");
            sql.addSql("   ZIN_EUID,");
            sql.addSql("   ZIN_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ZAI_INDEX");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID");
            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Select ZAI_INDEX + 在席ステータス
     * @param zifSid 座席表SID
     * @return List in ZAI_INDEXModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ZaiIndexPlusModel> selectUioIndexModel(int zifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ZaiIndexPlusModel> ret = new ArrayList<ZaiIndexPlusModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID,");
            sql.addSql("   ZIN_NAME,");
            sql.addSql("   ZIN_BGCOLOR,");
            sql.addSql("   ZIN_MSG,");
            sql.addSql("   ZIN_XINDEX,");
            sql.addSql("   ZIN_YINDEX,");
            sql.addSql("   ZIN_OTHER_VALUE,");
            sql.addSql("   ZIN_AUID,");
            sql.addSql("   ZIN_ADATE,");
            sql.addSql("   ZIN_EUID,");
            sql.addSql("   ZIN_EDATE,");
            sql.addSql("   USR_JKBN,");
            sql.addSql("   USR_UKO_FLG,");
            sql.addSql("   UIO_STATUS");
            sql.addSql(" from ");
            sql.addSql("   ZAI_INDEX left join CMN_USR_INOUT");
            sql.addSql("   on (ZAI_INDEX.ZIN_LINKSID = CMN_USR_INOUT.UIO_SID)");
            sql.addSql("   left join CMN_USRM");
            sql.addSql("   on (ZAI_INDEX.ZIN_LINKSID = CMN_USRM.USR_SID)");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID");
            sql.addSql("");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(zifSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getZaiIndexPlusFromRs(rs));
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
     * <p>座席表SIDを指定しHashMapを取得します。
     * @param zifSid 座席表SID
     * @return List in ZAI_INDEXModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, ZaiIndexModel> getZaiIndexMap2(int zifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiIndexModel model = null;
        String key = null;
        HashMap<String, ZaiIndexModel> ret = new HashMap<String, ZaiIndexModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID,");
            sql.addSql("   ZIN_NAME,");
            sql.addSql("   ZIN_BGCOLOR,");
            sql.addSql("   ZIN_MSG,");
            sql.addSql("   ZIN_XINDEX,");
            sql.addSql("   ZIN_YINDEX,");
            sql.addSql("   ZIN_OTHER_VALUE,");
            sql.addSql("   ZIN_AUID,");
            sql.addSql("   ZIN_ADATE,");
            sql.addSql("   ZIN_EUID,");
            sql.addSql("   ZIN_EDATE");
            sql.addSql(" from ");
            sql.addSql("   ZAI_INDEX");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(zifSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            int cnt = 0;
            while (rs.next()) {
                cnt++;
                model = __getZaiIndexFromRs(rs);
                key = __createElementKey(cnt);
                ret.put(key, model);
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
     * <p>Select ZAI_INDEX
     * @param bean ZAI_INDEX Model
     * @return ZAI_INDEXModel
     * @throws SQLException SQL実行例外
     */
    public ZaiIndexModel select(ZaiIndexModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ZaiIndexModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ZIF_SID,");
            sql.addSql("   ZIN_LINKKBN,");
            sql.addSql("   ZIN_LINKSID,");
            sql.addSql("   ZIN_NAME,");
            sql.addSql("   ZIN_BGCOLOR,");
            sql.addSql("   ZIN_MSG,");
            sql.addSql("   ZIN_XINDEX,");
            sql.addSql("   ZIN_YINDEX,");
            sql.addSql("   ZIN_OTHER_VALUE,");
            sql.addSql("   ZIN_AUID,");
            sql.addSql("   ZIN_ADATE,");
            sql.addSql("   ZIN_EUID,");
            sql.addSql("   ZIN_EDATE");
            sql.addSql(" from");
            sql.addSql("   ZAI_INDEX");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getZaiIndexFromRs(rs);
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
     * <p>指定した座席表に関連するデータサイズを取得します。
     * @param zifSid 座席表SID
     * @return データサイズ
     * @throws SQLException SQL実行例外
     */
    public long getZaiIndexDataSize(int zifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        long dataSize = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   sum(octet_length(ZIN_NAME)) as NAME_SIZE,");
            sql.addSql("   sum(octet_length(ZIN_MSG)) as MSG_SIZE,");
            sql.addSql("   sum(octet_length(ZIN_OTHER_VALUE)) as OTHER_SIZE");
            sql.addSql(" from ");
            sql.addSql("   ZAI_INDEX");
            sql.addSql(" where ");
            sql.addSql("   ZIF_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(zifSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize = rs.getLong("NAME_SIZE");
                dataSize += rs.getLong("MSG_SIZE");
                dataSize += rs.getLong("OTHER_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataSize;
    }

    /**
     * <p>Delete ZAI_INDEX
     * @param bean ZAI_INDEX Model
     * @throws SQLException SQL実行例外
     * @return int
     */
    public int delete(ZaiIndexModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ZAI_INDEX");

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
     * <p>座席表SIDを指定し削除する
     * @param zifSid 座席表SID
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int delete(int zifSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ZAI_INDEX");
            sql.addSql(" where");
            sql.addSql("   ZIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(zifSid);
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
     * <p>指定されたユーザSIDのデータを削除する
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return int 削除件数
     */
    public int deleteUsrData(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   ZAI_INDEX");
            sql.addSql(" where");
            sql.addSql("   ZIN_LINKKBN = ?");
            sql.addSql(" and");
            sql.addSql("   ZIN_LINKSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstZaiseki.ELEMENT_KBN_USR);
            sql.addIntValue(usrSid);
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
     * <p>Create ZAI_INDEX Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ZaiIndexModel
     * @throws SQLException SQL実行例外
     */
    private ZaiIndexModel __getZaiIndexFromRs(ResultSet rs) throws SQLException {
        ZaiIndexModel bean = new ZaiIndexModel();
        bean.setZifSid(rs.getInt("ZIF_SID"));
        bean.setZinLinkkbn(rs.getInt("ZIN_LINKKBN"));
        bean.setZinLinksid(rs.getInt("ZIN_LINKSID"));
        bean.setZinName(rs.getString("ZIN_NAME"));
        bean.setZinBgcolor(rs.getInt("ZIN_BGCOLOR"));
        bean.setZinMsg(rs.getString("ZIN_MSG"));
        bean.setZinXindex(rs.getInt("ZIN_XINDEX"));
        bean.setZinYindex(rs.getInt("ZIN_YINDEX"));
        bean.setZinOtherValue(rs.getString("ZIN_OTHER_VALUE"));
        bean.setZinAuid(rs.getInt("ZIN_AUID"));
        bean.setZinAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIN_ADATE")));
        bean.setZinEuid(rs.getInt("ZIN_EUID"));
        bean.setZinEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIN_EDATE")));
        return bean;
    }
    /**
     * <p>Create ZAI_INDEX Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ZaiIndexPlusModel
     * @throws SQLException SQL実行例外
     */
    private ZaiIndexPlusModel __getZaiIndexPlusFromRs(ResultSet rs) throws SQLException {
        ZaiIndexPlusModel bean = new ZaiIndexPlusModel();
        bean.setZifSid(rs.getInt("ZIF_SID"));
        bean.setZinLinkkbn(rs.getInt("ZIN_LINKKBN"));
        bean.setZinLinksid(rs.getInt("ZIN_LINKSID"));
        bean.setZinName(rs.getString("ZIN_NAME"));
        bean.setZinBgcolor(rs.getInt("ZIN_BGCOLOR"));
        bean.setZinMsg(rs.getString("ZIN_MSG"));
        bean.setZinXindex(rs.getInt("ZIN_XINDEX"));
        bean.setZinYindex(rs.getInt("ZIN_YINDEX"));
        bean.setZinOtherValue(rs.getString("ZIN_OTHER_VALUE"));
        bean.setZinAuid(rs.getInt("ZIN_AUID"));
        bean.setZinAdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIN_ADATE")));
        bean.setZinEuid(rs.getInt("ZIN_EUID"));
        bean.setZinEdate(UDate.getInstanceTimestamp(rs.getTimestamp("ZIN_EDATE")));
        bean.setUioStatus(rs.getInt("UIO_STATUS"));
        bean.setUsrJkbn(rs.getInt("USR_JKBN"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        return bean;
    }
    /**
     * HashMapのKEYを生成します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param cnt インデックス
     * @return String KEY
     */
    private String __createElementKey(int cnt) {
        String ret = null;
        ZsjCommonBiz biz = new ZsjCommonBiz(reqMdl__);
        ret = biz.createElementKey(cnt);
        return ret;
    }
}
