package jp.groupsession.v2.cht.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.model.ChatDeleteModel;
import jp.groupsession.v2.cht.model.ChatMessageModel;
import jp.groupsession.v2.cht.model.ChtGroupDataModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>CHT_GROUP_DATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtGroupDataDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtGroupDataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtGroupDataDao(Connection con) {
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
            sql.addSql("drop table CHT_GROUP_DATA");

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
            sql.addSql(" create table CHT_GROUP_DATA (");
            sql.addSql("   CGD_SID bigint not null,");
            sql.addSql("   CGI_SID integer not null,");
            sql.addSql("   CGD_TEXT varchar(5000),");
            sql.addSql("   BIN_SID bigint,");
            sql.addSql("   CGD_SEND_UID integer not null,");
            sql.addSql("   CGD_STATE_KBN integer not null,");
            sql.addSql("   CGD_AUID integer not null,");
            sql.addSql("   CGD_ADATE timestamp not null,");
            sql.addSql("   CGD_EUID integer not null,");
            sql.addSql("   CGD_EDATE timestamp not null,");
            sql.addSql("   primary key (CGD_SID)");
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
     * <p>全件数を取得する
     * @return List in ChtGroupDataModel
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
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
     * <p>CUP_SID毎の指定SIDまでの件数を取得する
     * @param cgiSid ペアSID
     * @param target 指定チャットSID
     * @return List in ChtUserPairModel
     * @throws SQLException SQL実行例外
     */
    public int countTarget(int cgiSid, long target) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID = ?");
            sql.addSql("   and CGD_SID <= ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addLongValue(target);
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
     * <p>Insert CHT_GROUP_DATA Data Bindding JavaBean
     * @param bean CHT_GROUP_DATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtGroupDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_GROUP_DATA(");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CGD_SEND_UID,");
            sql.addSql("   CGD_STATE_KBN,");
            sql.addSql("   CGD_AUID,");
            sql.addSql("   CGD_ADATE,");
            sql.addSql("   CGD_EUID,");
            sql.addSql("   CGD_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getCgdSid());
            sql.addIntValue(bean.getCgiSid());
            sql.addStrValue(bean.getCgdText());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getCgdSendUid());
            sql.addIntValue(bean.getCgdStateKbn());
            sql.addIntValue(bean.getCgdAuid());
            sql.addDateValue(bean.getCgdAdate());
            sql.addIntValue(bean.getCgdEuid());
            sql.addDateValue(bean.getCgdEdate());
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
     * <br>[機  能] チャットグループ投稿情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList ChtGroupUserModel DataList
     * @throws SQLException SQL実行例外
     */
    public void insertData(List<ChtGroupDataModel> beanList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (beanList == null || beanList.size() <= 0) {
            return;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_GROUP_DATA(");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CGD_SEND_UID,");
            sql.addSql("   CGD_STATE_KBN,");
            sql.addSql("   CGD_AUID,");
            sql.addSql("   CGD_ADATE,");
            sql.addSql("   CGD_EUID,");
            sql.addSql("   CGD_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (ChtGroupDataModel bean : beanList) {
                sql.addLongValue(bean.getCgdSid());
                sql.addIntValue(bean.getCgiSid());
                sql.addStrValue(bean.getCgdText());
                sql.addLongValue(bean.getBinSid());
                sql.addIntValue(bean.getCgdSendUid());
                sql.addIntValue(bean.getCgdStateKbn());
                sql.addIntValue(bean.getCgdAuid());
                sql.addDateValue(bean.getCgdAdate());
                sql.addIntValue(bean.getCgdEuid());
                sql.addDateValue(bean.getCgdEdate());
                log__.info(sql.toLogString());

                sql.setParameter(pstmt);
                pstmt.executeUpdate();
                sql.clearValue();
            }


        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update CHT_GROUP_DATA Data Bindding JavaBean
     * @param bean CHT_GROUP_DATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtGroupDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" set ");
            sql.addSql("   CGI_SID=?,");
            sql.addSql("   CGD_TEXT=?,");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   CGD_SEND_UID=?,");
            sql.addSql("   CGD_STATE_KBN=?,");
            sql.addSql("   CGD_AUID=?,");
            sql.addSql("   CGD_ADATE=?,");
            sql.addSql("   CGD_EUID=?,");
            sql.addSql("   CGD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CGD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCgiSid());
            sql.addStrValue(bean.getCgdText());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getCgdSendUid());
            sql.addIntValue(bean.getCgdStateKbn());
            sql.addIntValue(bean.getCgdAuid());
            sql.addDateValue(bean.getCgdAdate());
            sql.addIntValue(bean.getCgdEuid());
            sql.addDateValue(bean.getCgdEdate());
            //where
            sql.addLongValue(bean.getCgdSid());

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
     * <p>Select CHT_GROUP_DATA All Data
     * @return List in CHT_GROUP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChtGroupDataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupDataModel> ret = new ArrayList<ChtGroupDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CGD_SEND_UID,");
            sql.addSql("   CGD_STATE_KBN,");
            sql.addSql("   CGD_AUID,");
            sql.addSql("   CGD_ADATE,");
            sql.addSql("   CGD_EUID,");
            sql.addSql("   CGD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupDataFromRs(rs));
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
     * <p>Select CHT_GROUP_USER All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in ChtGroupDataModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtGroupDataModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupDataModel> ret = new ArrayList<ChtGroupDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CGD_SEND_UID,");
            sql.addSql("   CGD_STATE_KBN,");
            sql.addSql("   CGD_AUID,");
            sql.addSql("   CGD_ADATE,");
            sql.addSql("   CGD_EUID,");
            sql.addSql("   CGD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" order by ");
            sql.addSql("   CGD_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupDataFromRs(rs));
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
     * <p>Select CHT_GROUP_DATA
     * @param cgdSid CGD_SID
     * @return CHT_GROUP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ChtGroupDataModel select(long cgdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtGroupDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CGD_SEND_UID,");
            sql.addSql("   CGD_STATE_KBN,");
            sql.addSql("   CGD_AUID,");
            sql.addSql("   CGD_ADATE,");
            sql.addSql("   CGD_EUID,");
            sql.addSql("   CGD_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("   CGD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(cgdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtGroupDataFromRs(rs);
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
     * <p>Select CHT_GROUP_DATA
     * @param cgiSid CGI_SID
     * @return CHT_GROUP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<ChtGroupDataModel> select(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtGroupDataModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGI_SID,");
            sql.addSql("   CGD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CGD_SEND_UID,");
            sql.addSql("   CGD_STATE_KBN,");
            sql.addSql("   CGD_AUID,");
            sql.addSql("   CGD_ADATE,");
            sql.addSql("   CGD_EUID,");
            sql.addSql("   CGD_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtGroupDataFromRs(rs));
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
     * 指定したグループに投稿されたメッセージの内、削除されていないメッセージの件数を取得
     * @param cgiSid チャットグループSID
     * @return メッセージ件数
     * @throws SQLException SQL実行例外
     * */
    public int getExistMessageCount(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CGD_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CGD_STATE_KBN != ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cgiSid);
            sql.addIntValue(GSConstChat.TOUKOU_STATUS_DELETE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
     * <p>Delete CHT_GROUP_DATA
     * @param cgdSid CGD_SID
     * @throws SQLException SQL実行例外
     * @return count 削除件数
     */
    public int delete(long cgdSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("   CGD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(cgdSid);

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
     * <p>Delete CHT_GROUP_DATA
     * @param sidList cgiSidList
     * @throws SQLException SQL実行例外
     * @return count 削除件数
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
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID IN (");
            for (int idx = 0; idx < sidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(" , ");
                }
                sql.addSql(" ? ");
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
     * <p>Create CHT_GROUP_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtGroupDataModel
     * @throws SQLException SQL実行例外
     */
    private ChtGroupDataModel __getChtGroupDataFromRs(ResultSet rs) throws SQLException {
        ChtGroupDataModel bean = new ChtGroupDataModel();
        bean.setCgdSid(rs.getLong("CGD_SID"));
        bean.setCgiSid(rs.getInt("CGI_SID"));
        bean.setCgdText(rs.getString("CGD_TEXT"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setCgdSendUid(rs.getInt("CGD_SEND_UID"));
        bean.setCgdStateKbn(rs.getInt("CGD_STATE_KBN"));
        bean.setCgdAuid(rs.getInt("CGD_AUID"));
        bean.setCgdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CGD_ADATE")));
        bean.setCgdEuid(rs.getInt("CGD_EUID"));
        bean.setCgdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CGD_EDATE")));
        return bean;
    }

    /**
     * <br>[機  能] 削除対象のグループ投稿SID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param delMdl 手動削除設定モデル
     * @return チャットグループ毎投稿SIDMap
     * @throws SQLException SQL実行時例外
     */
    public Map<Integer, List<Long>>  getDelGroupChatSidList(
            ChatDeleteModel delMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        HashMap<Integer, List<Long>> ret = new HashMap<Integer, List<Long>>();

        SqlBuffer sql = null;

        try {
            UDate delDate = new UDate();
            delDate.setMaxHhMmSs();
            delDate.addYear((delMdl.getDelYear() * -1));
            delDate.addMonth((delMdl.getDelMonth() * -1));

            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGD_SID,");
            sql.addSql("   CGI_SID");
            sql.addSql(" from CHT_GROUP_DATA");
            sql.addSql(" where");
            sql.addSql("  CGD_ADATE <= ? ");
            sql.addDateValue(delDate);


            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int cgiSid = rs.getInt("CGI_SID");
                long cgdSid = rs.getLong("CGD_SID");

                List<Long> chtGrpSidList;
                if (ret.containsKey(cgiSid)) {
                    chtGrpSidList = ret.get(cgiSid);
                } else {
                    chtGrpSidList = new ArrayList<Long>();
                    ret.put(cgiSid, chtGrpSidList);
                }

                chtGrpSidList.add(cgdSid);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return ret;
    }

    /**
     * <p>Delete CHT_GROUP_DATA
     * @param delGrpChtList 削除対象投稿SID一覧
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int deleteGroup(List<Long> delGrpChtList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        Iterator<Long> it = delGrpChtList.iterator();
        int cursor = 0;
        while (it.hasNext()) {

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   CHT_GROUP_DATA");
                sql.addSql(" where ");
                sql.addSql("   CGD_SID in ( ");
                boolean first = true;
                while ((first || cursor % 1000 != 0)
                        && it.hasNext()) {
                    if (first) {
                        sql.addSql(" ? ");
                    } else {
                        sql.addSql(" ,? ");
                    }
                    sql.addLongValue(it.next());

                    first = false;
                    cursor++;
                }

                sql.addSql(" )");
                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeStatement(pstmt);
            }
        }
        return count;
    }

    /**
     * <p>update
     * @param sid メッセージSID
     * @param kbn 状態区分 編集・削除
     * @param userSid ユーザSID
     * @param text メッセージ内容
     * @throws SQLException SQL実行例外
     * @return count
     */
    public ChatMessageModel updateStateKbn(long sid,
            int kbn, int userSid, String text) throws SQLException {

        PreparedStatement pstmt = null;
        ChatMessageModel mdl = new ChatMessageModel();
        Connection con = null;
        UDate now = new UDate();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" set");
            sql.addSql("   CGD_STATE_KBN = ?,");
            sql.addIntValue(kbn);
            if (kbn == GSConstChat.TOUKOU_STATUS_EDIT) {
                sql.addSql("   CGD_TEXT = ?,");
                sql.addStrValue(text);
            }
            sql.addSql("   CGD_EUID = ?,");
            sql.addIntValue(userSid);
            sql.addSql("   CGD_EDATE = ?");
            sql.addDateValue(now);
            sql.addSql(" where ");
            sql.addSql("   CGD_SID = ? ");
            sql.addLongValue(sid);
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
            mdl.setUpdateDay(now.getStrYear() + "/" + now.getStrMonth() + "/" + now.getStrDay());
            mdl.setUpdateTime(now.getStrHour() + ":" + now.getStrMinute());
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return mdl;
    }

    /**
     * <br>[機  能] CHT_GROUP_DATAに紐づくバイナリー情報の論理削除
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList 削除対象投稿SID一覧
     * @param userSid ユーザSID
     * @param usid 更新者SID
     * @param date 現在日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeBinData(
            List<Long> sidList, int userSid, int usid, UDate date) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        Iterator<Long> it = sidList.iterator();
        int cursor = 0;
        while (it.hasNext()) {

            try {

                //バイナリー情報の論理削除

                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" update");
                sql.addSql("   CMN_BINF");
                sql.addSql(" set ");
                sql.addSql("   BIN_UPUSER = ?,");
                sql.addSql("   BIN_UPDATE = ?,");
                sql.addSql("   BIN_JKBN = ?");
                sql.addIntValue(usid);
                sql.addDateValue(date);
                sql.addIntValue(GSConst.JTKBN_DELETE);
                sql.addSql(" where");
                sql.addSql("   BIN_SID in (");
                sql.addSql(" select");
                sql.addSql("   BIN_SID");
                sql.addSql(" from");
                sql.addSql("   CHT_GROUP_DATA");
                sql.addSql(" where ");
                sql.addSql("   CGD_SID in ( ");
                if (sidList.size() == cursor + 1) {
                    sql.addSql(" ? ");
                    sql.addLongValue(it.next());
                    cursor++;
                } else {
                    boolean first = true;
                    while ((first || cursor % 1000 != 0)
                            && it.hasNext()) {
                        if (first) {
                            sql.addSql(" ? ");
                        } else {
                            sql.addSql(" ,? ");
                        }
                        sql.addLongValue(it.next());

                        first = false;
                        cursor++;
                    }
                }
                sql.addSql(" ))");

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();

            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeStatement(pstmt);
            }
        }
        return count;
    }

    /**
     * <p>Select BIN_SID
     * @param cgdSid CGD_SIDLIST
     * @return CHT_GROUP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getBinSid(String[] cgdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("   CGD_SID IN (");
            for (int idx = 0; idx < cgdSid.length; idx++) {
                if (idx != 0) {
                    sql.addSql(" , ");
                }
                sql.addSql(" ? ");
                sql.addIntValue(Integer.parseInt(cgdSid[idx]));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("BIN_SID"));
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
     * グループ内の最新のメッセージ送信日時を取得します。
     * @param cgiSid チャットグループSID
     * @return 最新送信日時
     * @throws SQLException SQL実行例外
     * */
    public UDate selectLastEntryTime(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        UDate ret = null;
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CGD_ADATE) CNT,");
            sql.addSql("   max(CGD_ADATE) as LAST");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where");
            sql.addSql("  CGI_SID = ?");

            sql.addIntValue(cgiSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("CNT") > 0) {
                    ret = UDate.getInstanceTimestamp(rs.getTimestamp("LAST"));
                } else {
                    return null;
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
     * <p>Select 初回メッセージ日付
     * @param cgiSid チャットグループSID
     * @return String
     * @throws SQLException SQL実行例外
     */
    public String getFirstDate(int cgiSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = "";
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   MIN(CGD_ADATE) as CGD_ADATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID = ?");

            sql.addIntValue(cgiSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                UDate date = UDate.getInstanceTimestamp(rs.getTimestamp("CGD_ADATE"));
                if (date != null) {
                    ret = date.getStrYear() + "/" + date.getStrMonth() + "/" + date.getStrDay();
                } else {
                    UDate now  = new UDate();
                    ret = now.getStrYear() + "/" + now.getStrMonth() + "/" + now.getStrDay();
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
     * <p>Select 最大投稿SID
     * @param groupSid チャットグループSID
     * @return String
     * @throws SQLException SQL実行例外
     */
    public Long getMaxSid(int groupSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Long ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MAX(CGD_SID) as CGD_SID");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("   CGI_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(groupSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CGD_SID");
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
     * <p>バイナリSIDを取得する
     * @param sidList グループSIDリスト
     * @return List in binSidList
     * @throws SQLException SQL実行例外
     */
    public List<Integer> selectBinList(List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where");
            sql.addSql("   CGI_SID IN (");
            for (int idx = 0; idx < sidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql("  , ");
                }
                sql.addSql("  ? ");
                sql.addIntValue(sidList.get(idx));
            }
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   BIN_SID > 0");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("BIN_SID"));
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
     * <p>投稿SIDを取得する
     * @param sidList グループSIDリスト
     * @return 投稿SID
     * @throws SQLException SQL実行例外
     */
    public List<Long> selectCgdSidList(List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Long> ret = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CGD_SID");
            sql.addSql(" from ");
            sql.addSql("   CHT_GROUP_DATA");
            sql.addSql(" where");
            sql.addSql("   CGI_SID IN (");
            for (int idx = 0; idx < sidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql("  , ");
                }
                sql.addSql("  ? ");
                sql.addIntValue(sidList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong("CGD_SID"));
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
     *
     * <br>[機  能] 指定したメッセージSIDの添付ファイルを含むものと論理削除フラグをマップして返す
     * <br>[解  説]
     * <br>[備  考]
     * @param cgdSidList メッセージSID
     * @param selectSid チャットペアSID
     * @return 添付ファイル付きメッセージのSIDと論理削除状態マップ
     * @throws SQLException SQL実行時例外
     */
    public Map<Long, Integer> selectBinMessageStateMap(List<Long> cgdSidList, int selectSid)
           throws SQLException {
       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       Map<Long, Integer> ret = new HashMap<>();

       con = getCon();

       try {
           // SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select");
           sql.addSql("  CGD_SID,");
           sql.addSql("  CGD_STATE_KBN");
           sql.addSql(" from");
           sql.addSql("  CHT_GROUP_DATA ");
           sql.addSql(" where");
           sql.addSql("   CGD_SID IN (");
           for (int idx = 0; idx < cgdSidList.size(); idx++) {
               if (idx != 0) {
                   sql.addSql(" , ");
               }
               sql.addSql(String.valueOf(cgdSidList.get(idx)));
           }
           sql.addSql("   )");
           sql.addSql(" and");
           sql.addSql(" CGI_SID = ? ");
           sql.addIntValue(selectSid);
           sql.addSql(" and");
           sql.addSql("   BIN_SID > 0");
           log__.info(sql.toLogString());
           pstmt = con.prepareStatement(sql.toSqlString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           while (rs.next()) {
               ret.put(rs.getLong("CGD_SID"),
                       rs.getInt("CGD_STATE_KBN"));
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
    *
    * <br>[機  能] 指定SIDのデータサイズを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param sidList 指定SID
    * @return データサイズ
    * @throws SQLException SQL実行時例外
    */
   public long getDiskSize(List<Long> sidList) throws SQLException {

       if (sidList == null || sidList.size() == 0) {
           return (long) 0;
       }
       String inSidStr = sidList.stream()
               .map(sid -> sid.toString())
               .collect(Collectors.joining(",", "(", ")"));
       long ret = 0;

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       con = getCon();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select  ");
           sql.addSql("   sum(octet_length(CGD_TEXT)) as CGD_TEXT");
           sql.addSql(" from ");
           sql.addSql("   CHT_GROUP_DATA ");
           sql.addSql(" where CGD_SID in ");
           sql.addSql(inSidStr);

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           if (rs.next()) {
               ret = rs.getLong("CGD_TEXT");
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
     * <br>[機  能] 指定したチャット投稿情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cgdSidList 投稿SID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<Long> cgdSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   coalesce(CHT_GROUP_DATA.BIN_SID, 0) > 0");
            sql.addSql(" and");
            sql.addSql("   CHT_GROUP_DATA.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CHT_GROUP_DATA.CGD_SID in (");

            for (int idx = 0; idx < cgdSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(cgdSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }
 }
