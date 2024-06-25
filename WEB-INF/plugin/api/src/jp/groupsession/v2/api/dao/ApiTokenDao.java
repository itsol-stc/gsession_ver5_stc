package jp.groupsession.v2.api.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.api.model.ApiTokenModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>API_TOKEN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiTokenDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiTokenDao.class);

    /**
     * <p>Default Constructor
     */
    public ApiTokenDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ApiTokenDao(Connection con) {
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
            sql.addSql("drop table API_TOKEN");

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
            sql.addSql(" create table API_TOKEN (");
            sql.addSql("   APT_TOKEN varchar(256) not null,");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   APT_CLIENT integer not null,");
            sql.addSql("   APT_CLIENT_ID varchar(256),");
            sql.addSql("   APT_IP varchar(40),");
            sql.addSql("   APT_JKBN integer not null,");
            sql.addSql("   APT_LIMIT_DATE timestamp not null,");
            sql.addSql("   APT_AUID integer not null,");
            sql.addSql("   APT_ADATE timestamp not null,");
            sql.addSql("   APT_EUID integer not null,");
            sql.addSql("   APT_EDATE timestamp not null,");
            sql.addSql("   primary key (APT_TOKEN)");
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
     * <p>Insert API_TOKEN Data Bindding JavaBean
     * @param bean API_TOKEN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(ApiTokenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" API_TOKEN(");
            sql.addSql("   APT_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APT_CLIENT,");
            sql.addSql("   APT_CLIENT_ID,");
            sql.addSql("   APT_IP,");
            sql.addSql("   APT_JKBN,");
            sql.addSql("   APT_LIMIT_DATE,");
            sql.addSql("   APT_AUID,");
            sql.addSql("   APT_ADATE,");
            sql.addSql("   APT_EUID,");
            sql.addSql("   APT_EDATE");
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
            sql.addStrValue(bean.getAptToken());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getAptClient());
            sql.addStrValue(bean.getAptClientId());
            sql.addStrValue(bean.getAptIp());
            sql.addIntValue(bean.getAptJkbn());
            sql.addDateValue(bean.getAptLimitDate());
            sql.addIntValue(bean.getAptAuid());
            sql.addDateValue(bean.getAptAdate());
            sql.addIntValue(bean.getAptEuid());
            sql.addDateValue(bean.getAptEdate());
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
     * <p>Update API_TOKEN Data Bindding JavaBean
     * @param bean API_TOKEN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(ApiTokenModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   API_TOKEN");
            sql.addSql(" set ");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   APT_CLIENT=?,");
            sql.addSql("   APT_CLIENT_ID=?,");
            sql.addSql("   APT_IP=?,");
            sql.addSql("   APT_JKBN=?,");
            sql.addSql("   APT_LIMIT_DATE=?,");
            sql.addSql("   APT_AUID=?,");
            sql.addSql("   APT_ADATE=?,");
            sql.addSql("   APT_EUID=?,");
            sql.addSql("   APT_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   APT_TOKEN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getAptClient());
            sql.addStrValue(bean.getAptClientId());
            sql.addStrValue(bean.getAptIp());
            sql.addIntValue(bean.getAptJkbn());
            sql.addDateValue(bean.getAptLimitDate());
            sql.addIntValue(bean.getAptAuid());
            sql.addDateValue(bean.getAptAdate());
            sql.addIntValue(bean.getAptEuid());
            sql.addDateValue(bean.getAptEdate());
            //where
            sql.addStrValue(bean.getAptToken());

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
     * <p>Select API_TOKEN All Data
     * @return List in API_TOKENModel
     * @throws SQLException SQL実行例外
     */
    public List<ApiTokenModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<ApiTokenModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   APT_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APT_CLIENT,");
            sql.addSql("   APT_CLIENT_ID,");
            sql.addSql("   APT_IP,");
            sql.addSql("   APT_JKBN,");
            sql.addSql("   APT_LIMIT_DATE,");
            sql.addSql("   APT_AUID,");
            sql.addSql("   APT_ADATE,");
            sql.addSql("   APT_EUID,");
            sql.addSql("   APT_EDATE");
            sql.addSql(" from ");
            sql.addSql("   API_TOKEN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getApiTokenFromRs(rs));
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
     * <p>Select API_TOKEN
     * @param aptToken APT_TOKEN
     * @return API_TOKENModel
     * @throws SQLException SQL実行例外
     */
    public ApiTokenModel select(String aptToken) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ApiTokenModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   APT_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APT_CLIENT,");
            sql.addSql("   APT_CLIENT_ID,");
            sql.addSql("   APT_IP,");
            sql.addSql("   APT_JKBN,");
            sql.addSql("   APT_LIMIT_DATE,");
            sql.addSql("   APT_AUID,");
            sql.addSql("   APT_ADATE,");
            sql.addSql("   APT_EUID,");
            sql.addSql("   APT_EDATE");
            sql.addSql(" from");
            sql.addSql("   API_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   APT_TOKEN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(aptToken);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getApiTokenFromRs(rs);
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
     * <br>[機  能] 指定したユーザのWEBAPIトークンを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSidList ユーザSID
     * @return API_TOKENModel
     * @throws SQLException SQL実行例外
     */
    public List<ApiTokenModel> select(List<Integer> usrSidList) throws SQLException {

        List<ApiTokenModel> ret = new ArrayList<ApiTokenModel>();
        if (usrSidList == null || usrSidList.isEmpty()) {
            return ret;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   APT_TOKEN,");
            sql.addSql("   USR_SID,");
            sql.addSql("   APT_CLIENT,");
            sql.addSql("   APT_CLIENT_ID,");
            sql.addSql("   APT_IP,");
            sql.addSql("   APT_JKBN,");
            sql.addSql("   APT_LIMIT_DATE,");
            sql.addSql("   APT_AUID,");
            sql.addSql("   APT_ADATE,");
            sql.addSql("   APT_EUID,");
            sql.addSql("   APT_EDATE");
            sql.addSql(" from");
            sql.addSql("   API_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   USR_SID in (");
            for (int idx = 0; idx < usrSidList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(usrSidList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getApiTokenFromRs(rs));
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
     * <p>Delete API_TOKEN
     * @param aptToken APT_TOKEN
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(String aptToken) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   API_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   APT_TOKEN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(aptToken);

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
     * <p>Select API_TOKEN
     * @return API_TOKENModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> selectDelTarget() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("            select");
            sql.addSql("              APT_TOKEN");
            sql.addSql("            from");
            sql.addSql("              (");
            sql.addSql("               select");
            sql.addSql("                 COUNT(*) as ID_CNT,");
            sql.addSql("                 USR_SID,");
            sql.addSql("                 APT_CLIENT_ID,");
            sql.addSql("                 MAX(APT_ADATE) as ADATE");
            sql.addSql("               from");
            sql.addSql("                 API_TOKEN");
            sql.addSql("               where");
            sql.addSql("                 APT_CLIENT_ID <> ''");
            sql.addSql("               GROUP BY");
            sql.addSql("                 USR_SID,");
            sql.addSql("                 APT_CLIENT_ID");
            sql.addSql("              ) as CNT_TABLE");
            sql.addSql("            inner join");
            sql.addSql("              API_TOKEN");
            sql.addSql("            on");
            sql.addSql("              CNT_TABLE.USR_SID = API_TOKEN.USR_SID");
            sql.addSql("            and");
            sql.addSql("              CNT_TABLE.APT_CLIENT_ID = API_TOKEN.APT_CLIENT_ID");
            sql.addSql("            and");
            sql.addSql("              CNT_TABLE.ADATE <> API_TOKEN.APT_ADATE");
            sql.addSql("            where");
            sql.addSql("              CNT_TABLE.ID_CNT > 1");
            sql.addSql("            UNION");
            sql.addSql("            select");
            sql.addSql("              APT_TOKEN");
            sql.addSql("            from");
            sql.addSql("              (");
            sql.addSql("               select");
            sql.addSql("                 COUNT(*) as ID_CNT2,");
            sql.addSql("                 USR_SID,");
            sql.addSql("                 APT_IP,");
            sql.addSql("                 MAX(API_TOKEN.APT_ADATE) as ADATE2");
            sql.addSql("               from");
            sql.addSql("                 API_TOKEN");
            sql.addSql("               where");
            sql.addSql("                 APT_CLIENT_ID = '' ");
            sql.addSql("               GROUP BY");
            sql.addSql("                 USR_SID,");
            sql.addSql("                 APT_IP");
            sql.addSql("              ) as CNT_TABLE2");
            sql.addSql("            inner join");
            sql.addSql("              API_TOKEN");
            sql.addSql("            on");
            sql.addSql("              CNT_TABLE2.USR_SID = API_TOKEN.USR_SID");
            sql.addSql("            and");
            sql.addSql("              CNT_TABLE2.APT_IP = API_TOKEN.APT_IP");
            sql.addSql("            and");
            sql.addSql("              CNT_TABLE2.ADATE2 <> API_TOKEN.APT_ADATE");
            sql.addSql("            where");
            sql.addSql("              CNT_TABLE2.ID_CNT2 > 1");


            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("APT_TOKEN"));
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
     * <p>Create API_TOKEN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created ApiTokenModel
     * @throws SQLException SQL実行例外
     */
    private ApiTokenModel __getApiTokenFromRs(ResultSet rs) throws SQLException {
        ApiTokenModel bean = new ApiTokenModel();
        bean.setAptToken(rs.getString("APT_TOKEN"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setAptClient(rs.getInt("APT_CLIENT"));
        bean.setAptClientId(rs.getString("APT_CLIENT_ID"));
        bean.setAptIp(rs.getString("APT_IP"));
        bean.setAptJkbn(rs.getInt("APT_JKBN"));
        bean.setAptLimitDate(UDate.getInstanceTimestamp(rs.getTimestamp("APT_LIMIT_DATE")));
        bean.setAptAuid(rs.getInt("APT_AUID"));
        bean.setAptAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APT_ADATE")));
        bean.setAptEuid(rs.getInt("APT_EUID"));
        bean.setAptEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APT_EDATE")));
        return bean;
    }
    /**
     *
     * <br>[機  能] トークン無効化処理
     * <br>[解  説]
     * <br>[備  考]
     * @param mukoList 対象トークン
     * @return 実行件数
     * @throws SQLException 実行時例外
     */
    public int doMukou(List<String> mukoList) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        if (mukoList == null || mukoList.size() == 0) {
            return count;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   API_TOKEN");
            sql.addSql(" set ");
            sql.addSql("   APT_JKBN=?");
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where ");
            sql.addSql("   APT_TOKEN in (");
            sql.addSql(" ?");
            sql.addStrValue("");
            for (String token : mukoList) {
                sql.addSql(" ,?");
                sql.addStrValue(token);
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
    *
    * <br>[機  能] 有効期限切れのTokenの削除
    * <br>[解  説]
    * <br>[備  考]
    * @param delDate 有効期限
    * @return 削除件数
    * @throws SQLException SQL実行時例外
    */
    public int deleteLimitOver(UDate delDate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   API_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   APT_LIMIT_DATE<?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(delDate);

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
    *
    * <br>[機  能] トークンの自動削除実行
    * <br>[解  説]
    * <br>[備  考]
    * @param aptToken 削除対象トークン
    * @return 削除件数
    * @throws SQLException SQL実行時例外
    */
    public int deleteAuto(ArrayList<String> aptToken) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            for (int delCnt = 0; delCnt < aptToken.size(); delCnt += 1000) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" delete");
                sql.addSql(" from");
                sql.addSql("   API_TOKEN");
                sql.addSql(" where ");
                sql.addSql("   APT_TOKEN IN (");
                for (int idx = delCnt; idx < aptToken.size(); idx++) {
                    if (idx != 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql(" ?");
                    sql.addStrValue(aptToken.get(idx));
                    if (idx == delCnt + 1000) {
                        break;
                    }
                }
                sql.addSql("   )");
                pstmt = con.prepareStatement(sql.toSqlString());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count = pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    
    /**
    *
    * <br>[機  能] 無効化トークンの削除実行
    * <br>[解  説]
    * <br>[備  考]
    * @return 削除件数
    * @throws SQLException SQL実行時例外
    */
    public int deleteMuko() throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   API_TOKEN");
            sql.addSql(" where ");
            sql.addSql("   APT_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_DELETE);
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
}
