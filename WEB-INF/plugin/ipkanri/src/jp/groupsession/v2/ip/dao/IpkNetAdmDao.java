package jp.groupsession.v2.ip.dao;

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
import jp.groupsession.v2.ip.model.IpkNetAdmModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ネットワーク管理者情報に関するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkNetAdmDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkNetAdmDao.class);

    /**
     * <p>Default Constructor
     */
    public IpkNetAdmDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public IpkNetAdmDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ネットワーク管理者のユーザSIDのリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid 数値型ののネットワークSIDの
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> selectUsrSid(int netSid)
           throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
//        String[] admSids = null;
//        int i = 0;
//        IpkNetModel ipkModel = new IpkNetModel();
        ArrayList<String> ret = new ArrayList<String>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   INC_AUID,");
            sql.addSql("   INC_ADATE,");
            sql.addSql("   INC_EUID,");
            sql.addSql("   INC_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_NET_ADM");
            sql.addSql(" where");
            sql.addSql("   INT_SID= ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(netSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("USR_SID"));
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
     * <br>[機  能] 全ネットワーク管理者のユーザSIDのリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> selectAdminUsrSid() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
//        String[] admSids = null;
//        int i = 0;
//        IpkNetModel ipkModel = new IpkNetModel();
        ArrayList<String> ret = new ArrayList<String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   INC_AUID,");
            sql.addSql("   INC_ADATE,");
            sql.addSql("   INC_EUID,");
            sql.addSql("   INC_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_NET_ADM");
            sql.addSql(" where");
            sql.addSql("   INT_SID=-1");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("USR_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

//    /**
//     * <br>[機  能] ユーザ情報を取得する。
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param admSids 管理者ユーザSIDの配列
//     * @return ret 管理者ユーザ情報
//     * @throws SQLException SQL実行例外
//     */
//    public List<IpkNetModel> select(String[] admSids)
//    throws SQLException {
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        ArrayList<IpkNetModel> ret = new ArrayList<IpkNetModel>();
//        con = getCon();
//        if (admSids == null) {
//            return ret;
//        }
//        if (admSids.length < 1) {
//            return ret;
//        }
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//            sql.addSql(" select ");
//            sql.addSql("   UINF.USR_SID,");
//            sql.addSql("   UINF.USI_SEI,");
//            sql.addSql("   UINF.USI_MEI,");
//            sql.addSql("   CPOS.POS_SORT,");
//            sql.addSql("   CPOS.POS_SORT,");
//            sql.addSql("  (case");
//            sql.addSql("   when UINF.POS_SID = 0 then 1 else 0 end) as YAKUSYOKU_EXIST,");
//            sql.addSql("   UINF.USI_AUID,");
//            sql.addSql("   UINF.USI_ADATE,");
//            sql.addSql("   UINF.USI_EUID,");
//            sql.addSql("   UINF.USI_EDATE");
//            sql.addSql(" from ");
//            sql.addSql("   CMN_USRM_INF as UINF, CMN_POSITION as CPOS");
//            sql.addSql(" where ");
//            sql.addSql("   UINF.POS_SID = CPOS.POS_SID");
//            for (int i = 0; i < admSids.length; i++) {
//                if (i == 0) {
//                    sql.addSql(" and");
//                    sql.addSql(" USR_SID in(");
//                } else if (i > 0) {
//                    sql.addSql(",");
//                }
//                sql.addSql(admSids[i]);
//            }
//            sql.addSql(")");
//            sql.addSql("   order by");
//            sql.addSql("     YAKUSYOKU_EXIST asc, POS_SORT asc, USI_SEI asc, USI_MEI asc");
//            pstmt = con.prepareStatement(sql.toSqlString());
//            log__.info(sql.toLogString());
//            rs = pstmt.executeQuery();
//            while (rs.next()) {
//                ret.add(__getIpkNetModelFromRs(rs));
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return ret;
//    }

//    /**
//     * <br>[機  能] ユーザ情報を格納したモデルを取得する
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param rs ResultSet
//     * @return model IpkNetModel
//     * @throws SQLException SQL実行例外
//     */
//    private IpkNetModel __getIpkNetModelFromRs(ResultSet rs) throws SQLException {
//
//        IpkNetModel model = new IpkNetModel();
//        model.setUsrSid(rs.getInt("USR_SID"));
//        model.setUsiSei(rs.getString("USI_SEI"));
//        model.setUsiMei(rs.getString("USI_MEI"));
//        return model;
//    }

    /**
     * <br>[機  能] ネットワーク管理者情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  netSid int
     * @throws SQLException SQL実行例外
     */
    public void deleteAdmin(int netSid) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   IPK_NET_ADM");
            sql.addSql(" where");
            sql.addSql("   INT_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(netSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] ネットワーク管理者情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteAdminUser(int usrSid) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   IPK_NET_ADM");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] ネットワーク管理者情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkNetAdmModel
     * @throws SQLException SQL実行例外
     */
     public void insertAdmin(IpkNetAdmModel model) throws SQLException {
         PreparedStatement pstmt = null;
         Connection con = null;
         con = getCon();
         try {
         //SQL文
             SqlBuffer sql = new SqlBuffer();
             sql.addSql(" insert into");
             sql.addSql("   IPK_NET_ADM (");
             sql.addSql("   INT_SID,");
             sql.addSql("   USR_SID,");
             sql.addSql("   INC_AUID,");
             sql.addSql("   INC_ADATE,");
             sql.addSql("   INC_EUID,");
             sql.addSql("   INC_EDATE");
             sql.addSql("  ) values (");
             sql.addSql(" ?,");
             sql.addSql(" ?,");
             sql.addSql(" ?,");
             sql.addSql(" ?,");
             sql.addSql(" ?,");
             sql.addSql(" ?);");
             pstmt = con.prepareStatement(sql.toSqlString());
             sql.addIntValue(model.getNetSid());
             sql.addIntValue(model.getUsrSid());
             sql.addIntValue(model.getNetAuid());
             sql.addDateValue(model.getNetAdate());
             sql.addIntValue(model.getNetEuid());
             sql.addDateValue(model.getNetEdate());
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
     * <br>[機  能] セッションユーザがネットワーク管理者になっているネットワークSIDリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionSid セッションユーザSID
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getUsrAdminNetworkSidData(int sessionSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<String> ret = new ArrayList<String>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   INT_SID");
            sql.addSql(" from ");
            sql.addSql("   IPK_NET_ADM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sessionSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("INT_SID"));
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
     * <br>[機  能] ユーザがネットワーク管理者になっているか判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param usrSid ユーザSID
     * @return netCount ユーザがネットワーク管理者になっているネットワーク数
     * @throws SQLException SQL実行例外
     */
    public int countUsrAdmNet(int netSid, int usrSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int netCount = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   IPK_NET_ADM");
            sql.addSql(" where");
            sql.addSql("   INT_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(netSid);
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            rs = pstmt.executeQuery();
            while (rs.next()) {
                netCount = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return netCount;
    }

    /**
     * <br>[機  能] ユーザが管理者になっているネットワーク数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return iadCount ユーザが管理者になっているネットワーク数
     * @throws SQLException SQL実行例外
     */
    public String countUsrAdm(int usrSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        String iadCount = "0";
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   IPK_NET_ADM");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            rs = pstmt.executeQuery();
            while (rs.next()) {
                iadCount = rs.getString("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return iadCount;
    }

    /**
     * <br>[機  能] セッションユーザが全ネットワーク管理者になっている数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionSid セッションユーザSID
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public Integer countUsrAdmAllNet(int sessionSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   IPK_NET_ADM");
            sql.addSql(" where ");
            sql.addSql("   INT_SID = -1 ");
            sql.addSql("   and ");
            sql.addSql("   USR_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sessionSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <br>[機  能] ユーザがネットワーク管理者になっている数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionSid セッションユーザSID
     * @return List in CMN_USRM_INFModel
     * @throws SQLException SQL実行例外
     */
    public Integer countUsrAdmNet(int sessionSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   IPK_NET_ADM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sessionSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <p>Select IPK_NET_ADM All Data
     * @return List in IPK_NET_ADMModel
     * @throws SQLException SQL実行例外
     */
    public List<IpkNetAdmModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkNetAdmModel> ret = new ArrayList<IpkNetAdmModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   INT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   INC_AUID,");
            sql.addSql("   INC_ADATE,");
            sql.addSql("   INC_EUID,");
            sql.addSql("   INC_EDATE");
            sql.addSql(" from ");
            sql.addSql("   IPK_NET_ADM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkNetAdmFromRs(rs));
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
     * <p>Insert IPK_NET_ADM Data Bindding JavaBean
     * @param bean IPK_NET_ADM Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(IpkNetAdmModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" IPK_NET_ADM(");
            sql.addSql("   INT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   INC_AUID,");
            sql.addSql("   INC_ADATE,");
            sql.addSql("   INC_EUID,");
            sql.addSql("   INC_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNetSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getNetAuid());
            sql.addDateValue(bean.getNetAdate());
            sql.addIntValue(bean.getNetEuid());
            sql.addDateValue(bean.getNetEdate());
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
     * <p>Create IPK_NET_ADM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created IpkNetAdmModel
     * @throws SQLException SQL実行例外
     */
    private IpkNetAdmModel __getIpkNetAdmFromRs(ResultSet rs) throws SQLException {
        IpkNetAdmModel bean = new IpkNetAdmModel();
        bean.setNetSid(rs.getInt("INT_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNetAuid(rs.getInt("INC_AUID"));
        bean.setNetAdate(UDate.getInstanceTimestamp(rs.getTimestamp("INC_ADATE")));
        bean.setNetEuid(rs.getInt("INC_EUID"));
        bean.setNetEdate(UDate.getInstanceTimestamp(rs.getTimestamp("INC_EDATE")));
        return bean;
    }
}