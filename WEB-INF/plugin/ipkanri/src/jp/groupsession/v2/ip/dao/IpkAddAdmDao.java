package jp.groupsession.v2.ip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ip.model.IpkAddAdmModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] IPアドレス管理者情報に関するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkAddAdmDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkAddAdmDao.class);

    /**
     * <p>Default Constructor
     */
    public IpkAddAdmDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public IpkAddAdmDao(Connection con) {
        super(con);
    }
    /**
     * <br>[機  能] IPアドレス使用者のリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkAddAdmModel> select()
           throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<IpkAddAdmModel> ret = new ArrayList<IpkAddAdmModel>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IPK_ADD_ADM.IAD_SID,");
            sql.addSql("   IPK_ADD_ADM.USR_SID,");
            sql.addSql("   IPK_ADD_ADM.IAC_AUID,");
            sql.addSql("   IPK_ADD_ADM.IAC_ADATE,");
            sql.addSql("   IPK_ADD_ADM.IAC_EUID,");
            sql.addSql("   IPK_ADD_ADM.IAC_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD_ADM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkAddAdmFromRs(rs));
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
     * <br>[機  能] IPアドレス使用者のユーザSIDのリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param iadSid 数値型のIPアドレスSID
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> select(int iadSid)
           throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        ArrayList<String> ret = new ArrayList<String>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IPK_ADD_ADM.USR_SID,");
            sql.addSql("   IPK_ADD_ADM.IAC_AUID,");
            sql.addSql("   IPK_ADD_ADM.IAC_ADATE,");
            sql.addSql("   IPK_ADD_ADM.IAC_EUID,");
            sql.addSql("   IPK_ADD_ADM.IAC_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD_ADM,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where");
            sql.addSql("   IPK_ADD_ADM.IAD_SID=?");
            sql.addSql(" and");
            sql.addSql("   IPK_ADD_ADM.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(iadSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
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
     * <br>[機  能] IPアドレス使用者情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkAddAdmModel
     * @throws SQLException SQL実行例外
     */
    public void insert(IpkAddAdmModel model) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into");
            sql.addSql("   IPK_ADD_ADM (");
            sql.addSql("   IAD_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   IAC_AUID,");
            sql.addSql("   IAC_ADATE,");
            sql.addSql("   IAC_EUID,");
            sql.addSql("   IAC_EDATE");
            sql.addSql("  ) values (");
            sql.addSql(" ?,");
            sql.addSql(" ?,");
            sql.addSql(" ?,");
            sql.addSql(" ?,");
            sql.addSql(" ?,");
            sql.addSql(" ?);");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getIadSid());
            sql.addIntValue(model.getUsrSid());
            sql.addIntValue(model.getIadAdmAuid());
            sql.addDateValue(model.getIadAdmAdate());
            sql.addIntValue(model.getIadAdmEuid());
            sql.addDateValue(model.getIadAdmEdate());
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
     * <br>[機  能] IPアドレス使用者情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param iadSid IPアドレスSID
     * @throws SQLException SQL実行例外
     */
    public void delete(int iadSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   IPK_ADD_ADM");
            sql.addSql(" where ");
            sql.addSql(" IAD_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(iadSid);
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
     * <br>[機  能] IPアドレス使用者情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param iadSidList IPアドレスSID
     * @throws SQLException SQL実行例外
     */
    public void deleteHukusuu(ArrayList<Integer> iadSidList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   IPK_ADD_ADM");
            sql.addSql(" where ");
            for (int i = 0; i < iadSidList.size(); i++) {
                if (i == 0) {
                    sql.addSql("   IAD_SID in (" + iadSidList.get(i));
                } else if (i > 0) {
                    sql.addSql(" ," + iadSidList.get(i));
                }
            }
            sql.addSql(" ) ");
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
     * <br>[機  能] ユーザが使用者なっているIPアドレスSIDを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  usrSid int ユーザSid
     * @return ret ArrayList ユーザが使用者になっているIPアドレスSIDリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> selectUsrAdminIadSid(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<String> ret = new ArrayList<String>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   IAD_SID");
            sql.addSql(" from ");
            sql.addSql("   IPK_ADD_ADM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("IAD_SID"));
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
     * <br>[機  能] IPアドレス使用者のユーザ情報のリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param iadSidList String[] IPアドレス使用者のリスト
     * @return ret ArrayList ユーザ姓名のリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkAddAdmModel> selectIadAdminUsr(ArrayList<Integer> iadSidList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkAddAdmModel> ret = new ArrayList<IpkAddAdmModel>();
        con = getCon();
        if (iadSidList == null) {
            return ret;
        }
        if (iadSidList.size() < 1) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   IPK_ADD_ADM.IAD_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.POS_SID,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   CPOS.POS_SORT,");
            sql.addSql("  (case");
            sql.addSql("   when CMN_USRM_INF.POS_SID = 0 then 1 else 0 end) as YAKUSYOKU_EXIST");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD_ADM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_POSITION as CPOS");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM_INF.POS_SID = CPOS.POS_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = IPK_ADD_ADM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM_INF.USR_SID = IPK_ADD_ADM.USR_SID");

            for (int i = 0; i < iadSidList.size(); i++) {
                if (i == 0) {
                    sql.addSql(" and ");
                    sql.addSql("   IPK_ADD_ADM.IAD_SID in(" + iadSidList.get(i));
                }
                sql.addSql("," + iadSidList.get(i));
            }
            sql.addSql(")");
            sql.addSql(" order by");
            sql.addSql("   YAKUSYOKU_EXIST asc, CPOS.POS_SORT asc,");
            sql.addSql("   CMN_USRM_INF.USI_SEI asc, CMN_USRM_INF.USI_MEI asc");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                IpkAddAdmModel model = __getIpkAddAdmModelFromRs(rs);
                ret.add(model);
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
     * <br>[機  能] IPアドレス使用者のユーザ姓名を結果セットから取り出し、モデルに格納する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet 結果セット
     * @return model IpkNetModel ユーザ姓名のリスト
     * @throws SQLException SQL実行例外
     */
    private IpkAddAdmModel __getIpkAddAdmModelFromRs(ResultSet rs) throws SQLException {

        IpkAddAdmModel model = new IpkAddAdmModel();
        model.setIadSid(rs.getInt("IAD_SID"));
        model.setUsiSei(rs.getString("USI_SEI"));
        model.setUsiMei(rs.getString("USI_MEI"));
        model.setUsrJkbn(rs.getInt("USR_JKBN"));
        model.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        return model;
    }
    /**
     * <p>Create IPK_ADD_ADM Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created IpkAddAdmModel
     * @throws SQLException SQL実行例外
     */
    private IpkAddAdmModel __getIpkAddAdmFromRs(ResultSet rs) throws SQLException {
        IpkAddAdmModel bean = new IpkAddAdmModel();
        bean.setIadSid(rs.getInt("IAD_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setIadAdmAuid(rs.getInt("IAC_AUID"));
        bean.setIadAdmAdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAC_ADATE")));
        bean.setIadAdmEuid(rs.getInt("IAC_EUID"));
        bean.setIadAdmEdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAC_EDATE")));
        return bean;
    }
    /**
     * <br>[機  能] ユーザが使用者になっているIPアドレス数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param iadSid ネットワークSID
     * @param usrSid ユーザSID
     * @return iadCount ユーザが使用者になっているIPアドレス数
     * @throws SQLException SQL実行例外
     */
    public int countUsrAdmIad(int iadSid, int usrSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int iadCount = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD_ADM");
            sql.addSql(" where");
            sql.addSql("   IAD_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   USR_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(iadSid);
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            rs = pstmt.executeQuery();
            while (rs.next()) {
                iadCount = rs.getInt("CNT");
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
     * <br>[機  能] ユーザが使用者になっているIPアドレス数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param usrSid ユーザSID
     * @return iadCount ユーザが使用者になっているIPアドレス数
     * @throws SQLException SQL実行例外
     */
    public String countUsrAdmNet(int netSid, int usrSid) throws SQLException {
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
            sql.addSql("   IPK_ADD, IPK_ADD_ADM");
            sql.addSql(" where");
            sql.addSql("   IPK_ADD.IAD_SID = IPK_ADD_ADM.IAD_SID");
            sql.addSql(" and ");
            sql.addSql("   IPK_ADD.INT_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   IPK_ADD_ADM.USR_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(netSid);
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
}