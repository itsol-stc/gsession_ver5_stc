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
import jp.groupsession.v2.cht.model.ChtUserDataModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>CHT_USER_DATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtUserDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ChtUserDataDao.class);

    /**
     * <p>Default Constructor
     */
    public ChtUserDataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ChtUserDataDao(Connection con) {
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
            sql.addSql("drop table CHT_USER_DATA");

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
            sql.addSql(" create table CHT_USER_DATA (");
            sql.addSql("   CUD_SID bigint not null,");
            sql.addSql("   CUP_SID integer not null,");
            sql.addSql("   CUD_TEXT varchar(5000),");
            sql.addSql("   BIN_SID integer,");
            sql.addSql("   CUD_SEND_UID integer not null,");
            sql.addSql("   CUD_STATE_KBN integer not null,");
            sql.addSql("   CUD_AUID integer not null,");
            sql.addSql("   CUD_ADATE timestamp not null,");
            sql.addSql("   CUD_EUID integer not null,");
            sql.addSql("   CUD_EDATE timestamp not null,");
            sql.addSql("   primary key (CUD_SID)");
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
     * @return List in ChtUserPairModel
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
            sql.addSql("   CHT_USER_DATA");

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
     * @param cupSid ペアSID
     * @param target 指定チャットSID
     * @return List in ChtUserPairModel
     * @throws SQLException SQL実行例外
     */
    public int countTarget(int cupSid, long target) throws SQLException {

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
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID = ?");
            sql.addSql("   and CUD_SID <= ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cupSid);
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
     * <p>Insert CHT_USER_DATA Data Bindding JavaBean
     * @param bean CHT_USER_DATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ChtUserDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CHT_USER_DATA(");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CUD_SEND_UID,");
            sql.addSql("   CUD_STATE_KBN,");
            sql.addSql("   CUD_AUID,");
            sql.addSql("   CUD_ADATE,");
            sql.addSql("   CUD_EUID,");
            sql.addSql("   CUD_EDATE");
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
            sql.addLongValue(bean.getCudSid());
            sql.addIntValue(bean.getCupSid());
            sql.addStrValue(bean.getCudText());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getCudSendUid());
            sql.addIntValue(bean.getCudStateKbn());
            sql.addIntValue(bean.getCudAuid());
            sql.addDateValue(bean.getCudAdate());
            sql.addIntValue(bean.getCudEuid());
            sql.addDateValue(bean.getCudEdate());
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
     * <br>[機  能] チャットユーザ投稿情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList ChtGroupUserModel DataList
     * @throws SQLException SQL実行例外
     */
    public void insertData(List<ChtUserDataModel> beanList) throws SQLException {

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
            sql.addSql(" CHT_USER_DATA(");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CUD_SEND_UID,");
            sql.addSql("   CUD_STATE_KBN,");
            sql.addSql("   CUD_AUID,");
            sql.addSql("   CUD_ADATE,");
            sql.addSql("   CUD_EUID,");
            sql.addSql("   CUD_EDATE");
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
            for (ChtUserDataModel bean : beanList) {
                sql.addLongValue(bean.getCudSid());
                sql.addIntValue(bean.getCupSid());
                sql.addStrValue(bean.getCudText());
                sql.addLongValue(bean.getBinSid());
                sql.addIntValue(bean.getCudSendUid());
                sql.addIntValue(bean.getCudStateKbn());
                sql.addIntValue(bean.getCudAuid());
                sql.addDateValue(bean.getCudAdate());
                sql.addIntValue(bean.getCudEuid());
                sql.addDateValue(bean.getCudEdate());
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
     * <p>Update CHT_USER_DATA Data Bindding JavaBean
     * @param bean CHT_USER_DATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ChtUserDataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" set ");
            sql.addSql("   CUP_SID=?,");
            sql.addSql("   CUD_TEXT=?,");
            sql.addSql("   BIN_SID=?,");
            sql.addSql("   CUD_SEND_UID=?,");
            sql.addSql("   CUD_STATE_KBN=?,");
            sql.addSql("   CUD_AUID=?,");
            sql.addSql("   CUD_ADATE=?,");
            sql.addSql("   CUD_EUID=?,");
            sql.addSql("   CUD_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CUD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCupSid());
            sql.addStrValue(bean.getCudText());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getCudSendUid());
            sql.addIntValue(bean.getCudStateKbn());
            sql.addIntValue(bean.getCudAuid());
            sql.addDateValue(bean.getCudAdate());
            sql.addIntValue(bean.getCudEuid());
            sql.addDateValue(bean.getCudEdate());
            //where
            sql.addLongValue(bean.getCudSid());

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
     * <p>Select CHT_USER_DATA All Data
     * @return List in CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtUserDataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtUserDataModel> ret = new ArrayList<ChtUserDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CUD_SEND_UID,");
            sql.addSql("   CUD_STATE_KBN,");
            sql.addSql("   CUD_AUID,");
            sql.addSql("   CUD_ADATE,");
            sql.addSql("   CUD_EUID,");
            sql.addSql("   CUD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_USER_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtUserDataFromRs(rs));
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
     * <p>Select CHT_USER_DATA All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<ChtUserDataModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ChtUserDataModel> ret = new ArrayList<ChtUserDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CUD_SEND_UID,");
            sql.addSql("   CUD_STATE_KBN,");
            sql.addSql("   CUD_AUID,");
            sql.addSql("   CUD_ADATE,");
            sql.addSql("   CUD_EUID,");
            sql.addSql("   CUD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" order by ");
            sql.addSql("   CUD_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getChtUserDataFromRs(rs));
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
     * <p>Select CHT_USER_DATA
     * @param cudSid CUD_SID
     * @return CHT_USER_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ChtUserDataModel select(long cudSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ChtUserDataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUP_SID,");
            sql.addSql("   CUD_TEXT,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CUD_SEND_UID,");
            sql.addSql("   CUD_STATE_KBN,");
            sql.addSql("   CUD_AUID,");
            sql.addSql("   CUD_ADATE,");
            sql.addSql("   CUD_EUID,");
            sql.addSql("   CUD_EDATE");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" where ");
            sql.addSql("   CUD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(cudSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getChtUserDataFromRs(rs);
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
     * <p>Delete CHT_USER_DATA
     * @param cudSid CUD_SID
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int delete(long cudSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" where ");
            sql.addSql("   CUD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(cudSid);

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
     * <p>Delete CHT_USER_DATA
     * @param sidList CUPSIDList
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
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID IN (");
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
     * <p>Update
     * @param sid メッセージSID
     * @param kbn 区分 編集・削除
     * @param usrSid ユーザSID
     * @param text メッセージ内容
     * @throws SQLException SQL実行例外
     * @return count
     */
    public ChatMessageModel updateStateKbn(long sid,
            int kbn, int usrSid, String text) throws SQLException {

        PreparedStatement pstmt = null;
        ChatMessageModel mdl = new ChatMessageModel();
        UDate now = new UDate();
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" set");
            sql.addSql("   CUD_STATE_KBN = ?,");
            sql.addIntValue(kbn);
            if (kbn != GSConstChat.TOUKOU_STATUS_DELETE) {
                sql.addSql("   CUD_TEXT = ?,");
                sql.addStrValue(text);
            }
            sql.addSql("   CUD_EUID = ?,");
            sql.addIntValue(usrSid);
            sql.addSql("   CUD_EDATE = ?");
            sql.addDateValue(now);
            sql.addSql(" where ");
            sql.addSql("   CUD_SID = ?");
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
     * <p>Create CHT_USER_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ChtUserDataModel
     * @throws SQLException SQL実行例外
     */
    private ChtUserDataModel __getChtUserDataFromRs(ResultSet rs) throws SQLException {
        ChtUserDataModel bean = new ChtUserDataModel();
        bean.setCudSid(rs.getLong("CUD_SID"));
        bean.setCupSid(rs.getInt("CUP_SID"));
        bean.setCudText(rs.getString("CUD_TEXT"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setCudSendUid(rs.getInt("CUD_SEND_UID"));
        bean.setCudStateKbn(rs.getInt("CUD_STATE_KBN"));
        bean.setCudAuid(rs.getInt("CUD_AUID"));
        bean.setCudAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CUD_ADATE")));
        bean.setCudEuid(rs.getInt("CUD_EUID"));
        bean.setCudEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CUD_EDATE")));
        return bean;
    }

    /**
     * <br>[機  能] 削除対象のユーザ投稿SID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param delMdl 手動削除設定モデル
     * @return チャットペア毎投稿SIDMap
     * @throws SQLException SQL実行時例外
     */
    public Map<Integer, List<Long>> getDelUserChatSidList(
            ChatDeleteModel delMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        SqlBuffer sql = null;
        HashMap<Integer, List<Long>> ret = new HashMap<Integer, List<Long>>();
        try {
            UDate delDate = new UDate();
            delDate.setMaxHhMmSs();
            delDate.addYear((delMdl.getDelYear() * -1));
            delDate.addMonth((delMdl.getDelMonth() * -1));

            sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUD_SID,");
            sql.addSql("   CUP_SID");
            sql.addSql(" from CHT_USER_DATA");
            sql.addSql(" where");
            sql.addSql("  CUD_ADATE <= ? ");
            sql.addDateValue(delDate);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int cupSid = rs.getInt("CUP_SID");
                long cudSid = rs.getLong("CUD_SID");

                List<Long> chtUsrSidList;
                if (ret.containsKey(cupSid)) {
                    chtUsrSidList = ret.get(cupSid);
                } else {
                    chtUsrSidList = new ArrayList<Long>();
                    ret.put(cupSid, chtUsrSidList);
                }

                chtUsrSidList.add(cudSid);
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
     * <p>Delete CHT_USER_DATA
     * @param sidList 削除対象投稿SID一覧
     * @throws SQLException SQL実行例外
     * @return count
     */
    public int deleteUser(List<Long> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        Iterator<Long> it = sidList.iterator();
        int cursor = 0;
        while (it.hasNext()) {
            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   CHT_USER_DATA");
                sql.addSql(" where ");
                sql.addSql("   CUD_SID in ( ");
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
     * <br>[機  能] CHT_USER_DATAに紐づくバイナリー情報の論理削除
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
                sql.addSql("   CHT_USER_DATA");
                sql.addSql(" where ");
                sql.addSql("   CUD_SID in ( ");
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
     * <p>Select 一回目の送信日付
     * @param sessionUsrSid USR_SID
     * @param partnerSid USR_SID
     * @return String
     * @throws SQLException SQL実行例外
     */
    public String getFirstDate(int sessionUsrSid, int partnerSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = "";
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MIN(CUD_ADATE) as CUD_ADATE");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=");
            sql.addSql("     (select");
            sql.addSql("        CUP_SID");
            sql.addSql("      from");
            sql.addSql("        CHT_USER_PAIR");
            sql.addSql("      where");
            sql.addSql("        (CUP_UID_F = ?");
            sql.addSql("        and");
            sql.addSql("        CUP_UID_S = ?)");
            sql.addSql("      or");
            sql.addSql("        (CUP_UID_F = ?");
            sql.addSql("        and");
            sql.addSql("        CUP_UID_S = ?)");
            sql.addSql("     )");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sessionUsrSid);
            sql.addIntValue(partnerSid);
            sql.addIntValue(partnerSid);
            sql.addIntValue(sessionUsrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                UDate date = UDate.getInstanceTimestamp(rs.getTimestamp("CUD_ADATE"));
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
     * @param pairSid ペアSID
     * @return String
     * @throws SQLException SQL実行例外
     */
    public Long getMaxSid(int pairSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Long ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MAX(CUD_SID) as CUD_SID");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" where ");
            sql.addSql("   CUP_SID=?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(pairSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getLong("CUD_SID");
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
     * <p>Select BIN_SID
     * @param cudSid CUD_SIDLIST
     * @return CHT_GROUP_DATAModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getBinSid(String[] cudSid) throws SQLException {

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
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" where ");
            sql.addSql("   CUD_SID IN (");
            for (int idx = 0; idx < cudSid.length; idx++) {
                if (idx != 0) {
                    sql.addSql(" , ");
                }
                sql.addSql(" ? ");
                sql.addLongValue(Long.parseLong(cudSid[idx]));
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
     * <p>バイナリSIDを取得する
     * @param sidList ペアSIDリスト
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
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" where");
            sql.addSql("   CUP_SID IN (");
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
     * @param sidList ペアSIDリスト
     * @return 投稿SID
     * @throws SQLException SQL実行例外
     */
    public List<Long> selectCudSidList(List<Integer> sidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Long> ret = new ArrayList<Long>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUD_SID");
            sql.addSql(" from ");
            sql.addSql("   CHT_USER_DATA");
            sql.addSql(" where");
            sql.addSql("   CUP_SID IN (");
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
                ret.add(rs.getLong("CUD_SID"));
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
           sql.addSql("  CUD_SID,");
           sql.addSql("  CUD_STATE_KBN");
           sql.addSql(" from");
           sql.addSql("  CHT_USER_DATA ");
           sql.addSql(" where");
           sql.addSql("   CUD_SID IN (");
           for (int idx = 0; idx < cgdSidList.size(); idx++) {
               if (idx != 0) {
                   sql.addSql(" , ");
               }
               sql.addSql(String.valueOf(cgdSidList.get(idx)));
           }
           sql.addSql("   )");
           sql.addSql(" and");
           sql.addSql(" CUP_SID = ? ");
           sql.addIntValue(selectSid);
           sql.addSql(" and");
           sql.addSql("   BIN_SID > 0");
           log__.info(sql.toLogString());
           pstmt = con.prepareStatement(sql.toSqlString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           while (rs.next()) {
               ret.put(rs.getLong("CUD_SID"),
                       rs.getInt("CUD_STATE_KBN"));
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
           sql.addSql("   sum(octet_length(CUD_TEXT)) as CUD_TEXT");
           sql.addSql(" from ");
           sql.addSql("   CHT_USER_DATA ");
           sql.addSql(" where CUD_SID in ");
           sql.addSql(inSidStr);

           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           if (rs.next()) {
               ret = rs.getLong("CUD_TEXT");
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
     * @param cudSidList 投稿SID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<Long> cudSidList) throws SQLException {

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
            sql.addSql("   CHT_USER_DATA,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   coalesce(CHT_USER_DATA.BIN_SID, 0) > 0");
            sql.addSql(" and");
            sql.addSql("   CHT_USER_DATA.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CHT_USER_DATA.CUD_SID in (");

            for (int idx = 0; idx < cudSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(cudSidList.get(idx));
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
