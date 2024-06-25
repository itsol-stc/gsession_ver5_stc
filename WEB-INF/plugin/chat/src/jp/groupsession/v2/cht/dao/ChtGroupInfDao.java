package jp.groupsession.v2.cht.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.model.ChatGroupInfModel;
import jp.groupsession.v2.cht.model.ChatInformationModel;
import jp.groupsession.v2.cht.model.ChtGroupInfModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>CHT_GROUP_INF Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupInfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtGroupInfDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtGroupInfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtGroupInfDao(Connection con) {
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
            sql.addSql("drop table CHT_GROUP_INF");

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
            sql.addSql(" create table CHT_GROUP_INF (");
            sql.addSql("   CGI_SID integer not null,");
            sql.addSql("   CGI_ID varchar(20) not null,");
            sql.addSql("   CGI_NAME varchar(100) not null,");
            sql.addSql("   CGI_CONTENT varchar(500),");
            sql.addSql("   CGI_COMP_FLG integer not null,");
            sql.addSql("   CGI_DEL_FLG integer not null,");
            sql.addSql("   CHC_SID integer,");
            sql.addSql("   CGI_AUID integer not null,");
            sql.addSql("   CGI_ADATE timestamp not null,");
            sql.addSql("   CGI_EUID integer not null,");
            sql.addSql("   CGI_EDATE timestamp not null,");
            sql.addSql("   primary key (CGI_SID)");
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
     * <p>Insert CHT_GROUP_INF Data Bindding JavaBean
     * @param bean CHT_GROUP_INF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtGroupInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_GROUP_INF(");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGI_ID,");
            sql.addSql("   CGI_NAME,");
            sql.addSql("   CGI_CONTENT,");
            sql.addSql("   CGI_COMP_FLG,");
            sql.addSql("   CGI_DEL_FLG,");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CGI_AUID,");
            sql.addSql("   CGI_ADATE,");
            sql.addSql("   CGI_EUID,");
            sql.addSql("   CGI_EDATE");
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
            sql.addIntValue(bean.getCgiSid());
            sql.addStrValue(bean.getCgiId());
            sql.addStrValue(bean.getCgiName());
            sql.addStrValue(bean.getCgiContent());
            sql.addIntValue(bean.getCgiCompFlg());
            sql.addIntValue(bean.getCgiDelFlg());
            sql.addIntValue(bean.getChcSid());
            sql.addIntValue(bean.getCgiAuid());
            sql.addDateValue(bean.getCgiAdate());
            sql.addIntValue(bean.getCgiEuid());
            sql.addDateValue(bean.getCgiEdate());
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
     * <p>Update CHT_GROUP_INF Data Bindding JavaBean
     * @param bean CHT_GROUP_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtGroupInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" set ");
            sql.addSql("   CGI_ID=?,");
            sql.addSql("   CGI_NAME=?,");
            sql.addSql("   CGI_CONTENT=?,");
            sql.addSql("   CGI_COMP_FLG=?,");
            sql.addSql("   CGI_DEL_FLG=?,");
            sql.addSql("   CHC_SID=?,");
            sql.addSql("   CGI_AUID=?,");
            sql.addSql("   CGI_ADATE=?,");
            sql.addSql("   CGI_EUID=?,");
            sql.addSql("   CGI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCgiId());
            sql.addStrValue(bean.getCgiName());
            sql.addStrValue(bean.getCgiContent());
            sql.addIntValue(bean.getCgiCompFlg());
            sql.addIntValue(bean.getCgiDelFlg());
            sql.addIntValue(bean.getChcSid());
            sql.addIntValue(bean.getCgiAuid());
            sql.addDateValue(bean.getCgiAdate());
            sql.addIntValue(bean.getCgiEuid());
            sql.addDateValue(bean.getCgiEdate());
            //where
            sql.addIntValue(bean.getCgiSid());

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
     * <p>Update CHT_GROUP_INF Data Bindding JavaBean
     * @param bean CHT_GROUP_INF Data Bindding JavaBean
     * @param adminFlg システム・プラグイン管理者判定
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateGrp(ChtGroupInfModel bean, boolean adminFlg) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" set ");
            sql.addSql("   CGI_ID=?,");
            sql.addSql("   CGI_NAME=?,");
            sql.addSql("   CGI_CONTENT=?,");
            sql.addSql("   CGI_COMP_FLG=?,");
            sql.addSql("   CGI_DEL_FLG=?,");
            if (adminFlg) {
                sql.addSql("   CHC_SID=?,");
            }
            sql.addSql("   CGI_EUID=?,");
            sql.addSql("   CGI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCgiId());
            sql.addStrValue(bean.getCgiName());
            sql.addStrValue(bean.getCgiContent());
            sql.addIntValue(bean.getCgiCompFlg());
            sql.addIntValue(bean.getCgiDelFlg());
            if (adminFlg) {
                sql.addIntValue(bean.getChcSid());
            }
            sql.addIntValue(bean.getCgiEuid());
            sql.addDateValue(bean.getCgiEdate());
            //where
            sql.addIntValue(bean.getCgiSid());

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
     * <p>Update CHT_GROUP_INF Data Bindding JavaBean
     * @param bean CHT_GROUP_INF Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateGrp(ChtGroupInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" set ");
            sql.addSql("   CGI_ID=?,");
            sql.addSql("   CGI_NAME=?,");
            sql.addSql("   CGI_CONTENT=?,");
            sql.addSql("   CGI_COMP_FLG=?,");
            sql.addSql("   CGI_DEL_FLG=?,");
            sql.addSql("   CHC_SID=?,");
            sql.addSql("   CGI_EUID=?,");
            sql.addSql("   CGI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCgiId());
            sql.addStrValue(bean.getCgiName());
            sql.addStrValue(bean.getCgiContent());
            sql.addIntValue(bean.getCgiCompFlg());
            sql.addIntValue(bean.getCgiDelFlg());
            sql.addIntValue(bean.getChcSid());
            sql.addIntValue(bean.getCgiEuid());
            sql.addDateValue(bean.getCgiEdate());
            //where
            sql.addIntValue(bean.getCgiSid());

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
     * <p>Update CHT_GROUP_INF Data Bindding JavaBean
     * @param bean CHT_GROUP_INF Data Bindding JavaBean
     * @param cgiSid グループSID
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCategory(ChtGroupInfModel bean, String[] cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" set ");
            sql.addSql("   CHC_SID=?,");
            sql.addSql("   CGI_EUID=?,");
            sql.addSql("   CGI_EDATE=?");
            sql.addIntValue(bean.getChcSid());
            sql.addIntValue(bean.getCgiEuid());
            sql.addDateValue(bean.getCgiEdate());
            sql.addSql(" where ");
            sql.addSql("   CGI_SID ");
            sql.addSql(" in");
            sql.addSql(" (");
            for (int i = 0; i < cgiSid.length; i++) {
                sql.addSql(
                        (i > 0 ? "   ," : "   "));
                sql.addSql(" ?");
                sql.addIntValue(Integer.parseInt(cgiSid[i]));
            }
            sql.addSql("   )");
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
     * <p>Select CHT_GROUP_INF All Data
     * @return List in CHT_GROUP_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtGroupInfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupInfModel> ret = new ArrayList<ChtGroupInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGI_ID,");
            sql.addSql("   CGI_NAME,");
            sql.addSql("   CGI_CONTENT,");
            sql.addSql("   CGI_COMP_FLG,");
            sql.addSql("   CGI_DEL_FLG,");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CGI_AUID,");
            sql.addSql("   CGI_ADATE,");
            sql.addSql("   CGI_EUID,");
            sql.addSql("   CGI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupInfFromRs(rs));
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
     * <p>Select CHT_GROUP_INF
     * @param cgiSid CGI_SID
     * @return CHT_GROUP_INFModel
     * @throws SQLException SQL実行例外
     */
    public ChtGroupInfModel select(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtGroupInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGI_ID,");
            sql.addSql("   CGI_NAME,");
            sql.addSql("   CGI_CONTENT,");
            sql.addSql("   CGI_COMP_FLG,");
            sql.addSql("   CGI_DEL_FLG,");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CGI_AUID,");
            sql.addSql("   CGI_ADATE,");
            sql.addSql("   CGI_EUID,");
            sql.addSql("   CGI_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtGroupInfFromRs(rs);
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
     * <p>Select CHT_GROUP_INF
     * groupIDからチャット情報を取得
     * @param groupId groupID
     * @return CHT_GROUP_INFModel
     * @throws SQLException SQL実行例外
     */
    public ChtGroupInfModel selectWhereCgiId(String groupId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtGroupInfModel ret = new ChtGroupInfModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGI_ID,");
            sql.addSql("   CGI_NAME,");
            sql.addSql("   CGI_CONTENT,");
            sql.addSql("   CGI_COMP_FLG,");
            sql.addSql("   CGI_DEL_FLG,");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CGI_AUID,");
            sql.addSql("   CGI_ADATE,");
            sql.addSql("   CGI_EUID,");
            sql.addSql("   CGI_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where");
            sql.addSql("       CGI_ID like '"
                    + JDBCUtil.escapeForLikeSearch(groupId)
                    + "'");
            sql.addSql(" and");
            sql.addSql("   CGI_DEL_FLG = ? ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtGroupInfFromRs(rs);
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
     * <p>Select CHT_GROUP_INF
     * @param cgiSid CGI_SID
     * @return CHT_GROUP_INFModel
     * @throws SQLException SQL実行例外
     */
    public ChatInformationModel selectChatGroupInf(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChatInformationModel ret = new ChatInformationModel();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_ID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME, ");
            sql.addSql("   CHT_GROUP_INF.CGI_CONTENT, ");
            sql.addSql("   CHT_GROUP_INF.CGI_COMP_FLG, ");
            sql.addSql("   CHT_GROUP_INF.CHC_SID, ");
            sql.addSql("   CHT_GROUP_INF.CGI_ADATE, ");
            sql.addSql("   CHT_GROUP_INF.CGI_EDATE,");
            sql.addSql("   LAST_DAY.CGD_ADATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" left join ");
            sql.addSql("   (");
            sql.addSql("   select");
            sql.addSql("     CGI_SID,");
            sql.addSql("     MAX(CGD_ADATE) as CGD_ADATE");
            sql.addSql("   from");
            sql.addSql("     CHT_GROUP_DATA");
            sql.addSql("   group by");
            sql.addSql("     CGI_SID");
            sql.addSql("   ) as LAST_DAY");
            sql.addSql(" on ");
            sql.addSql("    CHT_GROUP_INF.CGI_SID = LAST_DAY.CGI_SID");
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret.setChatSid(rs.getInt("CGI_SID"));
                ret.setChatName(rs.getString("CGI_NAME"));
                ret.setChatArchive(rs.getInt("CGI_COMP_FLG"));
                ret.setCategorySid(rs.getInt("CHC_SID"));
                ret.setChatId(rs.getString("CGI_ID"));
                ret.setChatBiko(rs.getString("CGI_CONTENT"));
                ret.setInsertDate(UDate.getInstanceTimestamp(rs.getTimestamp("CGI_ADATE")));
                ret.setStrInsertDate(__createDateStr(ret.getInsertDate()));
                ret.setLastSendDate(UDate.getInstanceTimestamp(rs.getTimestamp("CGD_ADATE")));
                ret.setStrLastSendDate(__createDateStr(ret.getLastSendDate()));
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
     * <p>Delete CHT_GROUP_INF
     * @param cgiSid CGI_SID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);

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
     * <p>Delete CHT_GROUP_INF
     * @param sidList cgiSidList
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID IN (");
            for (int idx = 0; idx < sidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(" , ");
                }
                sql.addSql("   ?");
                sql.addIntValue(sidList.get(idx));
            }
            sql.addSql("   )");
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
     * <br>[機  能]自分が所属するチャットグループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return チャットグループリスト
     * @throws SQLException SQLException
     */
    public ArrayList<ChatGroupInfModel> selectGroup(int usrSid)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChatGroupInfModel> ret = new ArrayList<ChatGroupInfModel>();
        con = getCon();
        ChatDao chtDao = new ChatDao(con);
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ID,");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME,");
            sql.addSql("   CHT_GROUP_INF.CGI_CONTENT,");
            sql.addSql("   CHT_GROUP_INF.CGI_COMP_FLG,");
            sql.addSql("   CHT_GROUP_INF.CGI_DEL_FLG,");
            sql.addSql("   CHT_GROUP_INF.CHC_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_AUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ADATE,");
            sql.addSql("   CHT_GROUP_INF.CGI_EUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_EDATE,");
            sql.addSql("   COUNT_DATA.CNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" left join ");
            sql.addSql("   (");
            chtDao.writeQueryGroupTimeline(sql, usrSid);
            sql.addSql("   ) COUNT_DATA");
            sql.addSql(" on");
            sql.addSql("   CHT_GROUP_INF.CGI_SID = COUNT_DATA.GROUP_SID");
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID IN (");
            sql.addSql("     select");
            sql.addSql("       CGI_SID");
            sql.addSql("     from");
            sql.addSql("       CHT_GROUP_USER");
            sql.addSql("     where");
            sql.addSql("       (");
            sql.addSql("          CGU_SELECT_SID = ?");
            sql.addSql("        and");
            sql.addSql("          CGU_KBN = ?");
            sql.addSql("       )");
            sql.addSql("       or");
            sql.addSql("       (");
            sql.addSql("          CGU_KBN = ?");
            sql.addSql("        and");
            sql.addSql("          CGU_SELECT_SID IN (");
            sql.addSql("            select");
            sql.addSql("              GRP_SID");
            sql.addSql("            from");
            sql.addSql("              CMN_BELONGM");
            sql.addSql("            where");
            sql.addSql("              CMN_BELONGM.USR_SID = ?");
            sql.addSql("          )");
            sql.addSql("       )");
            sql.addSql("    )");
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ?");
            sql.addSql(" order by ");
            sql.addSql("   CGI_SID ");

            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChatGroupInfFromRs(rs));
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
     * <br>[機  能]カテゴリSIDからチャットグループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param chcSid カテゴリSID
     * @return チャットグループリスト
     * @throws SQLException SQLException
     */
    public ArrayList<ChtGroupInfModel> selectGroupWhereChcSid(int chcSid)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupInfModel> ret = new ArrayList<ChtGroupInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ID,");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME,");
            sql.addSql("   CHT_GROUP_INF.CGI_CONTENT,");
            sql.addSql("   CHT_GROUP_INF.CGI_COMP_FLG,");
            sql.addSql("   CHT_GROUP_INF.CGI_DEL_FLG,");
            sql.addSql("   CHT_GROUP_INF.CHC_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_AUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ADATE,");
            sql.addSql("   CHT_GROUP_INF.CGI_EUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CHC_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ? ");
            sql.addSql(" order by ");
            sql.addSql("   CGI_SID ");
            sql.addIntValue(chcSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupInfFromRs(rs));
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
     * <br>[機  能]カテゴリSIDからチャットグループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param chcSid カテゴリSID
     * @return チャットグループリスト
     * @throws SQLException SQLException
     */
    public ArrayList<ChtGroupInfModel> selectGroupWhereChcSid(List<Integer> chcSid)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupInfModel> ret = new ArrayList<ChtGroupInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ID,");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME,");
            sql.addSql("   CHT_GROUP_INF.CGI_CONTENT,");
            sql.addSql("   CHT_GROUP_INF.CGI_COMP_FLG,");
            sql.addSql("   CHT_GROUP_INF.CGI_DEL_FLG,");
            sql.addSql("   CHT_GROUP_INF.CHC_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_AUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ADATE,");
            sql.addSql("   CHT_GROUP_INF.CGI_EUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CHC_SID ");
            sql.addSql(" in");
            sql.addSql(" (");
            for (int i = 0; i < chcSid.size(); i++) {
                sql.addSql(
                        (i > 0 ? "   ," : "   "));
                sql.addSql("?");
                sql.addIntValue(chcSid.get(i));
            }
            sql.addSql("   )");
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ? ");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql(" order by ");
            sql.addSql("   CGI_SID ");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupInfFromRs(rs));
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
     * <br>[機  能]グループSIDからチャットグループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cgiSid カテゴリSID
     * @return チャットグループリスト
     * @throws SQLException SQLException
     */
    public ArrayList<ChtGroupInfModel> selectGroupWhereCgiSid(String[] cgiSid)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupInfModel> ret = new ArrayList<ChtGroupInfModel>();
        if (cgiSid == null || cgiSid.length <= 0) {
            return ret;
        }

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ID,");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME,");
            sql.addSql("   CHT_GROUP_INF.CGI_CONTENT,");
            sql.addSql("   CHT_GROUP_INF.CGI_COMP_FLG,");
            sql.addSql("   CHT_GROUP_INF.CGI_DEL_FLG,");
            sql.addSql("   CHT_GROUP_INF.CHC_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_AUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ADATE,");
            sql.addSql("   CHT_GROUP_INF.CGI_EUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            if (cgiSid != null && cgiSid.length > 0) {
              sql.addSql("   CGI_SID ");
              sql.addSql(" in");
              sql.addSql(" (");
              for (int i = 0; i < cgiSid.length; i++) {
                if (i != 0) {
                    sql.addSql("   ,");
                }
                sql.addSql("?");
                sql.addIntValue(Integer.parseInt(cgiSid[i]));
              }
              sql.addSql("   )");
              sql.addSql(" and ");
            }
            sql.addSql("   CGI_DEL_FLG = ? ");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql(" order by ");
            sql.addSql("   CGI_SID ");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupInfFromRs(rs));
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
     * <br>[機  能] 所属チャット判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param cgiSid チャットSID
     * @return ret true:所属 false:所属していない
     * @throws SQLException SQL実行時例外
     */
    public boolean isJoinGroup(int cgiSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CHT_GROUP_INF.CGI_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID IN (");
            sql.addSql("     select");
            sql.addSql("       CGI_SID");
            sql.addSql("     from");
            sql.addSql("       CHT_GROUP_USER");
            sql.addSql("     where");
            sql.addSql("       CGI_SID = ?");
            sql.addSql("        and");
            sql.addSql("       ((");
            sql.addSql("          CGU_SELECT_SID = ?");
            sql.addSql("        and");
            sql.addSql("          CGU_KBN = ?");
            sql.addSql("       )");
            sql.addSql("       or");
            sql.addSql("       (");
            sql.addSql("          CGU_KBN = ?");
            sql.addSql("        and");
            sql.addSql("          CGU_SELECT_SID IN (");
            sql.addSql("            select");
            sql.addSql("              GRP_SID");
            sql.addSql("            from");
            sql.addSql("              CMN_BELONGM");
            sql.addSql("            where");
            sql.addSql("              CMN_BELONGM.USR_SID = ?");
            sql.addSql("          )");
            sql.addSql("       )");
            sql.addSql("    ))");
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ? ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
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
     * <br>[機  能] チャット編集権限判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @param cgiSid チャットSID
     * @return ret true:権限あり false:権限なし
     * @throws SQLException SQL実行時例外
     */
    public boolean isLimitEditGroup(int cgiSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CHT_GROUP_INF.CGI_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID IN (");
            sql.addSql("     select");
            sql.addSql("       CGI_SID");
            sql.addSql("     from");
            sql.addSql("       CHT_GROUP_USER");
            sql.addSql("     where");
            sql.addSql("       CGI_SID = ?");
            sql.addSql("        and");
            sql.addSql("       CGU_ADMIN_FLG = ?");
            sql.addSql("        and");
            sql.addSql("       ((");
            sql.addSql("          CGU_SELECT_SID = ?");
            sql.addSql("        and");
            sql.addSql("          CGU_KBN = ?");
            sql.addSql("       )");
            sql.addSql("       or");
            sql.addSql("       (");
            sql.addSql("          CGU_KBN = ?");
            sql.addSql("        and");
            sql.addSql("          CGU_SELECT_SID IN (");
            sql.addSql("            select");
            sql.addSql("              GRP_SID");
            sql.addSql("            from");
            sql.addSql("              CMN_BELONGM");
            sql.addSql("            where");
            sql.addSql("              CMN_BELONGM.USR_SID = ?");
            sql.addSql("          )");
            sql.addSql("       )");
            sql.addSql("    ))");
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ? ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(GSConstChat.CHAT_GROUP_ADMIN);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstChat.CHAT_KBN_USER);
            sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
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
     * <br>[機  能] グループ存在判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret true:グループが存在 false:グループがない
     * @throws SQLException SQL実行時例外
     */
    public boolean isExitGroup() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CHT_GROUP_INF.CGI_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CGI_DEL_FLG = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
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
     * <p>Create CHT_GROUP_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtGroupInfModel
     * @throws SQLException SQL実行例外
     */
    private ChtGroupInfModel __getChtGroupInfFromRs(ResultSet rs) throws SQLException {
        ChtGroupInfModel bean = new ChtGroupInfModel();
        bean.setCgiSid(rs.getInt("CGI_SID"));
        bean.setCgiId(rs.getString("CGI_ID"));
        bean.setCgiName(rs.getString("CGI_NAME"));
        bean.setCgiContent(rs.getString("CGI_CONTENT"));
        bean.setCgiCompFlg(rs.getInt("CGI_COMP_FLG"));
        bean.setCgiDelFlg(rs.getInt("CGI_DEL_FLG"));
        bean.setChcSid(rs.getInt("CHC_SID"));
        bean.setCgiAuid(rs.getInt("CGI_AUID"));
        bean.setCgiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CGI_ADATE")));
        bean.setCgiEuid(rs.getInt("CGI_EUID"));
        bean.setCgiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CGI_EDATE")));
        return bean;
    }

    /**
     * <p>Create CHT_GROUP_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtGroupInfModel
     * @throws SQLException SQL実行例外
     */
    private ChatGroupInfModel __getChatGroupInfFromRs(ResultSet rs) throws SQLException {
        ChatGroupInfModel bean = new ChatGroupInfModel();
        bean.setCgiSid(rs.getInt("CGI_SID"));
        bean.setCgiId(rs.getString("CGI_ID"));
        bean.setCgiName(rs.getString("CGI_NAME"));
        bean.setCgiContent(rs.getString("CGI_CONTENT"));
        bean.setCgiCompFlg(rs.getInt("CGI_COMP_FLG"));
        bean.setCgiDelFlg(rs.getInt("CGI_DEL_FLG"));
        bean.setChcSid(rs.getInt("CHC_SID"));
        bean.setCgiAuid(rs.getInt("CGI_AUID"));
        bean.setCgiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CGI_ADATE")));
        bean.setCgiEuid(rs.getInt("CGI_EUID"));
        bean.setCgiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CGI_EDATE")));
        bean.setChtGroupCount(rs.getInt("CNT"));
        return bean;
    }

    /**
     * 日付文字列を取得する
     * @param date 日付
     * @return 日付文字列
     */
    private String __createDateStr(UDate date) {
        if (date == null) {
            return null;
        }

        StringBuilder strDate = new StringBuilder("");
        strDate.append(UDateUtil.getSlashYYMD(date));
        strDate.append(" ");
        strDate.append(UDateUtil.getSeparateHMS(date));

        return strDate.toString();
    }

    /**
     * <p>Update CHT_GROUP_INF Data Bindding JavaBean
     * @param cgiSid cgiSid
     * @param now UDate
     * @param usrSid usrSid
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateGroupLocgicDelete(int cgiSid, int usrSid, UDate now) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" set ");
            sql.addSql("   CGI_DEL_FLG=?,");
            sql.addSql("   CGI_EUID=?,");
            sql.addSql("   CGI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstChat.CHAT_GROUP_LOGIC_DELETE);
            sql.addIntValue(usrSid);
            sql.addDateValue(now);
            //where
            sql.addIntValue(cgiSid);

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
     * <p>Update CHT_GROUP_INF Data Bindding JavaBean
     * @param chcSid chcSid
     * @param now UDate
     * @param usrSid usrSid
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCategoryNone(int chcSid, int usrSid, UDate now) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" set ");
            sql.addSql("   CHC_SID=?,");
            sql.addSql("   CGI_EUID=?,");
            sql.addSql("   CGI_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CHC_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstChat.CHAT_CHC_SID_NO);
            sql.addIntValue(usrSid);
            sql.addDateValue(now);
            //where
            sql.addIntValue(chcSid);

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
     * GroupId存在チェック
     * @param groupId GroupId
     * @param cgiSid cgiSid
     * @return カテゴリ有(true)無(false)
     * @throws SQLException SQL実行例外
     */
    public boolean isExitGroupId(String groupId, int cgiSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGI_SID");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where");
            sql.addSql("       CGI_ID like '"
                    + JDBCUtil.escapeForLikeSearch(groupId)
                    + "'");
            sql.addSql(" and");
            sql.addSql("  CGI_SID <> ?");
            sql.addIntValue(cgiSid);
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ? ");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <br>[機  能] グループチャット存在判定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param cgiSid グループチャットSID
     * @return ret true:ある false:ない
     * @throws SQLException SQL実行時例外
     */
    public boolean isExitGroup(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CHT_GROUP_INF.CGI_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ? ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
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
     * <br>[機  能]　削除チャットグループSID取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ret true:ある false:ない
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getDelGrpSid() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGI_SID");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CGI_DEL_FLG = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_DELETE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("CGI_SID"));
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
     * <br>[機  能]　管理権限を持つグループ情報一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param allDspFlg 全てのグループを検索するか
     * @param usrSid ユーザSID
     * @param archiveFlg アーカイブ状態
     * @param keyword 検索キーワード
     * @return グループ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<ChtGroupInfModel> getGroupConfList(
            boolean allDspFlg,
            int usrSid,
            int archiveFlg,
            String keyword) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<ChtGroupInfModel> ret = new ArrayList<ChtGroupInfModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ID,");
            sql.addSql("   CHT_GROUP_INF.CGI_NAME,");
            sql.addSql("   CHT_GROUP_INF.CGI_CONTENT,");
            sql.addSql("   CHT_GROUP_INF.CGI_COMP_FLG,");
            sql.addSql("   CHT_GROUP_INF.CGI_DEL_FLG,");
            sql.addSql("   CHT_GROUP_INF.CHC_SID,");
            sql.addSql("   CHT_GROUP_INF.CGI_AUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_ADATE,");
            sql.addSql("   CHT_GROUP_INF.CGI_EUID,");
            sql.addSql("   CHT_GROUP_INF.CGI_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            // 全てのチャットグループを表示するかしないか
            if (!allDspFlg) {
                sql.addSql(" right join ");
                sql.addSql("   (");
                sql.addSql("   select");
                sql.addSql("     CGI_SID");
                sql.addSql("   from");
                sql.addSql("     CHT_GROUP_USER");
                sql.addSql("   where");
                sql.addSql("       ((");
                sql.addSql("          CGU_SELECT_SID = ?");
                sql.addIntValue(usrSid);
                sql.addSql("        and");
                sql.addSql("          CGU_KBN = ?");
                sql.addIntValue(GSConstChat.CHAT_KBN_USER);
                sql.addSql("       )");
                sql.addSql("       or");
                sql.addSql("       (");
                sql.addSql("          CGU_KBN = ?");
                sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
                sql.addSql("        and");
                sql.addSql("          CGU_SELECT_SID IN (");
                sql.addSql("            select");
                sql.addSql("              GRP_SID");
                sql.addSql("            from");
                sql.addSql("              CMN_BELONGM");
                sql.addSql("            where");
                sql.addSql("              CMN_BELONGM.USR_SID = ?");
                sql.addIntValue(usrSid);
                sql.addSql("          )");
                sql.addSql("       )");
                sql.addSql("    )");
                sql.addSql("   and");
                sql.addSql("     CGU_ADMIN_FLG = ?");
                sql.addIntValue(GSConstChat.CHAT_GROUP_ADMIN);
                sql.addSql("     group by CGI_SID");
                sql.addSql("   ) as CHT_GROUP_USER");
                sql.addSql(" on ");
                sql.addSql("    CHT_GROUP_INF.CGI_SID = CHT_GROUP_USER.CGI_SID");
            }
            sql.addSql(" where ");
            sql.addSql(" 1 = 1 ");
            if (archiveFlg == GSConstChat.CHAT_ARCHIVE_NOT_MODE) {
                sql.addSql(" and ");
                sql.addSql("   CGI_COMP_FLG = ?");
                sql.addIntValue(archiveFlg);
            }
            // 検索
            if (!StringUtil.isNullZeroStringSpace(keyword)) {
                sql.addSql(" and ");
                sql.addSql(" CHT_GROUP_INF.CGI_NAME  like '%"
                        + JDBCUtil.escapeForLikeSearch(keyword)
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
            }
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupInfFromRs(rs));
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
     * グループIDからチャットグループ情報を取得する
     * @param cgiId グループID
     * @return チャットグループ情報モデル
     * @throws SQLException SQL実行例外
     * */
    public ChtGroupInfModel selectById(String cgiId) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtGroupInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGI_ID,");
            sql.addSql("   CGI_NAME,");
            sql.addSql("   CGI_CONTENT,");
            sql.addSql("   CGI_COMP_FLG,");
            sql.addSql("   CGI_DEL_FLG,");
            sql.addSql("   CHC_SID,");
            sql.addSql("   CGI_AUID,");
            sql.addSql("   CGI_ADATE,");
            sql.addSql("   CGI_EUID,");
            sql.addSql("   CGI_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CGI_ID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(cgiId);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtGroupInfFromRs(rs);
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
     * <br>[機  能]　グループ管理グループ表示判定
     * <br>[解  説]　グループ一覧にグループ名を表示するか判定
     * <br>[備  考]
     * @param allDspFlg 全てのグループを検索するか
     * @param usrSid ユーザSID
     * @param archiveFlg アーカイブ状態
     * @param keyword 検索キーワード
     * @param cgiSid グループＳＩＤ
     * @return グループ情報一覧
     * @throws SQLException SQL実行時例外
     */
    public boolean chkGrpConfGroupDsp(
            boolean allDspFlg,
            int usrSid,
            int archiveFlg,
            String keyword,
            int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_GROUP_INF.CGI_SID");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF");
            // 全てのチャットグループを表示するかしないか
            if (!allDspFlg) {
                sql.addSql(" right join ");
                sql.addSql("   (");
                sql.addSql("   select");
                sql.addSql("     CGI_SID");
                sql.addSql("   from");
                sql.addSql("     CHT_GROUP_USER");
                sql.addSql("   where");
                sql.addSql("       ((");
                sql.addSql("          CGU_SELECT_SID = ?");
                sql.addIntValue(usrSid);
                sql.addSql("        and");
                sql.addSql("          CGU_KBN = ?");
                sql.addIntValue(GSConstChat.CHAT_KBN_USER);
                sql.addSql("       )");
                sql.addSql("       or");
                sql.addSql("       (");
                sql.addSql("          CGU_KBN = ?");
                sql.addIntValue(GSConstChat.CHAT_KBN_GROUP);
                sql.addSql("        and");
                sql.addSql("          CGU_SELECT_SID IN (");
                sql.addSql("            select");
                sql.addSql("              GRP_SID");
                sql.addSql("            from");
                sql.addSql("              CMN_BELONGM");
                sql.addSql("            where");
                sql.addSql("              CMN_BELONGM.USR_SID = ?");
                sql.addIntValue(usrSid);
                sql.addSql("          )");
                sql.addSql("       )");
                sql.addSql("    )");
                sql.addSql("   and");
                sql.addSql("     CGU_ADMIN_FLG = ?");
                sql.addIntValue(GSConstChat.CHAT_GROUP_ADMIN);
                sql.addSql("     group by CGI_SID");
                sql.addSql("   ) as CHT_GROUP_USER");
                sql.addSql(" on ");
                sql.addSql("    CHT_GROUP_INF.CGI_SID = CHT_GROUP_USER.CGI_SID");
            }
            sql.addSql(" where ");
            sql.addSql(" CHT_GROUP_INF.CGI_SID = ? ");
            sql.addIntValue(cgiSid);
            if (archiveFlg == GSConstChat.CHAT_ARCHIVE_NOT_MODE) {
                sql.addSql(" and ");
                sql.addSql("   CGI_COMP_FLG = ?");
                sql.addIntValue(archiveFlg);
            }
            // 検索
            if (!StringUtil.isNullZeroStringSpace(keyword)) {
                sql.addSql(" and ");
                sql.addSql(" CHT_GROUP_INF.CGI_NAME  like '%"
                        + JDBCUtil.escapeForLikeSearch(keyword)
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
            }
            sql.addSql(" and ");
            sql.addSql("   CGI_DEL_FLG = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                return true;
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
     * グループ件数を取得する
     * @return グループ件数
     * @throws SQLException SQL実行例外
     * */
    public int getGroupCount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   count(CGI_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_INF");
            sql.addSql(" where ");
            sql.addSql("   CGI_DEL_FLG = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] グループ情報が最も古い日時を取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 最古日時
     * @throws SQLException SQL実行例外
     */
    public UDate getMinDate() throws SQLException {
        UDate ret = new UDate();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   min(CGI_ADATE) as MIN_DATE ");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_INF ");
            sql.addSql(" where ");
            sql.addSql("   CGI_DEL_FLG = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = UDate.getInstanceTimestamp(rs.getTimestamp("MIN_DATE"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }




}
