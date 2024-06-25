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
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.model.IpkSpecMstModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] スペック情報に関するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkSpecMstDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkSpecMstDao.class);

    /**
     * <p>Default Constructor
     */
    public IpkSpecMstDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public IpkSpecMstDao(Connection con) {
        super(con);
    }
    /**
     * <p>Select IPK_SPECM All Data
     * @return List in IPK_SPECMModel
     * @throws SQLException SQL実行例外
     */
    public List<IpkSpecMstModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkSpecMstModel> ret = new ArrayList<IpkSpecMstModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ISM_SID,");
            sql.addSql("   ISM_KBN,");
            sql.addSql("   ISM_NAME,");
            sql.addSql("   ISM_LEVEL,");
            sql.addSql("   ISM_BIKO,");
            sql.addSql("   ISM_AUID,");
            sql.addSql("   ISM_ADATE,");
            sql.addSql("   ISM_EUID,");
            sql.addSql("   ISM_EDATE");
            sql.addSql(" from ");
            sql.addSql("   IPK_SPECM");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkSpecmFromRs(rs));
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
     * <br>[機  能] スペック情報一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param specKbn スペック区分
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkSpecMstModel> select(int specKbn)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkSpecMstModel> ret = new ArrayList<IpkSpecMstModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ISM_SID,");
            sql.addSql("   ISM_KBN,");
            sql.addSql("   ISM_NAME,");
            sql.addSql("   ISM_LEVEL,");
            sql.addSql("   ISM_BIKO,");
            sql.addSql("   ISM_AUID,");
            sql.addSql("   ISM_ADATE,");
            sql.addSql("   ISM_EUID,");
            sql.addSql("   ISM_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_SPECM");
            sql.addSql(" where");
            sql.addSql("   ISM_KBN=?");
            sql.addSql(" order by ISM_LEVEL DESC, ISM_NAME");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(specKbn);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkSpecMstModelFromRs(rs));
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
     * <br>[機  能] スペック情報一覧を取得する。
     * <br>[解  説] 表示順を指定してスペック情報を取得する。
     * <br>[備  考]
     * @param ismLevel 表示順
     * @param ismKbn スペック区分
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public IpkSpecMstModel selectLevel(int ismLevel, int ismKbn)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        IpkSpecMstModel ret = new IpkSpecMstModel();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ISM_SID,");
            sql.addSql("   ISM_KBN,");
            sql.addSql("   ISM_NAME,");
            sql.addSql("   ISM_LEVEL,");
            sql.addSql("   ISM_BIKO,");
            sql.addSql("   ISM_AUID,");
            sql.addSql("   ISM_ADATE,");
            sql.addSql("   ISM_EUID,");
            sql.addSql("   ISM_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_SPECM");
            sql.addSql(" where");
            sql.addSql("   ISM_LEVEL=?");
            sql.addSql("   and");
            sql.addSql("   ISM_KBN=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ismLevel);
            sql.addIntValue(ismKbn);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getIpkSpecMstModelFromRs(rs);
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
     * <br>[機  能] スペック情報一覧を取得する。
     * <br>[解  説] 引数のISMSID以外の情報を取得する。
     * <br>[備  考]
     * @param specKbn スペック区分
     * @param ismSid スペックSID
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkSpecMstModel> select(int specKbn, int ismSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkSpecMstModel> ret = new ArrayList<IpkSpecMstModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ISM_SID,");
            sql.addSql("   ISM_KBN,");
            sql.addSql("   ISM_NAME,");
            sql.addSql("   ISM_LEVEL,");
            sql.addSql("   ISM_BIKO,");
            sql.addSql("   ISM_AUID,");
            sql.addSql("   ISM_ADATE,");
            sql.addSql("   ISM_EUID,");
            sql.addSql("   ISM_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_SPECM");
            sql.addSql(" where");
            sql.addSql("   ISM_KBN=?");
            sql.addSql("   and");
            sql.addSql("   ISM_SID<>?");
            sql.addSql(" order by ISM_LEVEL DESC, ISM_NAME");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(specKbn);
            sql.addIntValue(ismSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkSpecMstModelFromRs(rs));
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
     * <br>[機  能] スペック情報を取得する。
     * <br>[解  説] 一件のスペック情報を取得する。
     * <br>[備  考]
     * @param ismSid スペックSID
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkSpecMstModel> selectSpecInf(int ismSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkSpecMstModel> ret = new ArrayList<IpkSpecMstModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ISM_SID,");
            sql.addSql("   ISM_KBN,");
            sql.addSql("   ISM_NAME,");
            sql.addSql("   ISM_LEVEL,");
            sql.addSql("   ISM_BIKO,");
            sql.addSql("   ISM_AUID,");
            sql.addSql("   ISM_ADATE,");
            sql.addSql("   ISM_EUID,");
            sql.addSql("   ISM_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_SPECM");
            sql.addSql(" where");
            sql.addSql("   ISM_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ismSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkSpecMstModelFromRs(rs));
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
     * <br>[機  能] スペックSIDから名前を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ismSid スペックSID
     * @return name 名前
     * @throws SQLException SQL実行例外
     */
    public String selectName(int ismSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String name = "";
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   ISM_SID,");
            sql.addSql("   ISM_NAME");
             sql.addSql(" from");
            sql.addSql("   IPK_SPECM");
            sql.addSql(" where");
            sql.addSql("   ISM_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ismSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("ISM_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return name;
    }

    /**
     * <br>[機  能] IPアドレスSIDから名前を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param iadSid IPアドレスSID
     * @param specKbn スペック区分
     * @return name 名前
     * @throws SQLException SQL実行例外
     */
    public String selectNameIpad(int iadSid, int specKbn)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String name = "";
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IPK_SPECM.ISM_SID,");
            sql.addSql("   IPK_SPECM.ISM_NAME,");
            sql.addSql("   IPK_ADD.IAD_SID");
             sql.addSql(" from");
            sql.addSql("   IPK_SPECM, IPK_ADD");
            sql.addSql(" where");
            sql.addSql("   IPK_SPECM.ISM_SID = ");
            if (specKbn == IpkConst.IPK_SPECKBN_CPU) {
                sql.addSql("   IPK_ADD.IAD_CPU");
            } else if (specKbn == IpkConst.IPK_SPECKBN_MEMORY) {
                sql.addSql("   IPK_ADD.IAD_MEMORY");
            } else if (specKbn == IpkConst.IPK_SPECKBN_HD) {
                sql.addSql("   IPK_ADD.IAD_HD");
            }
            sql.addSql(" and");
            sql.addSql("   IPK_ADD.IAD_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(iadSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                name = rs.getString("ISM_NAME");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return name;
    }

    /**
     * <br>[機  能] スペック一覧情報を格納したモデルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return bean IpkModel
     * @throws SQLException SQL実行例外
     */
    private IpkSpecMstModel __getIpkSpecMstModelFromRs(ResultSet rs)
    throws SQLException {

        IpkSpecMstModel model = new IpkSpecMstModel();
        model.setIsmSid(rs.getInt("ISM_SID"));
        model.setIpk100name(rs.getString("ISM_NAME"));
        model.setIpk100level(rs.getInt("ISM_LEVEL"));
        model.setIpk100biko(rs.getString("ISM_BIKO"));
        model.setSpecKbn(rs.getInt("ISM_KBN"));
        return model;
    }

    /**
     * <br>[機  能] スペックマスタ情報を変更する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkSpecMstModel
     * @throws SQLException SQL実行例外
     */
    public void update(IpkSpecMstModel model) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   IPK_SPECM");
            sql.addSql(" set");
            sql.addSql("   ISM_KBN = ?,");
            sql.addSql("   ISM_NAME = ?,");
            sql.addSql("   ISM_LEVEL = ?,");
            sql.addSql("   ISM_BIKO = ?,");
            sql.addSql("   ISM_EUID = ?,");
            sql.addSql("   ISM_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   ISM_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getSpecKbn());
            sql.addStrValue(model.getIpk100name());
            sql.addIntValue(model.getIpk100level());
            sql.addStrValue(model.getIpk100biko());
            sql.addIntValue(model.getUsrSid());
            sql.addDateValue(model.getNow());
            sql.addIntValue(model.getIsmSid());
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
     * <br>[機  能] スペックマスタ情報を変更する。（表示順）
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkSpecMstModel
     * @throws SQLException SQL実行例外
     */
    public void updateLevel(IpkSpecMstModel model) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   IPK_SPECM");
            sql.addSql(" set");
            sql.addSql("   ISM_LEVEL = ?");
            sql.addSql(" where ");
            sql.addSql("   ISM_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getIpk100level());
            sql.addIntValue(model.getIsmSid());
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
     * <br>[機  能] スペック情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkSpecMstModel
     * @throws SQLException SQL実行例外
     */
    public void insert(IpkSpecMstModel model) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql("  IPK_SPECM (");
            sql.addSql("   ISM_SID,");
            sql.addSql("   ISM_KBN,");
            sql.addSql("   ISM_NAME,");
            sql.addSql("   ISM_LEVEL,");
            sql.addSql("   ISM_BIKO,");
            sql.addSql("   ISM_AUID,");
            sql.addSql("   ISM_ADATE,");
            sql.addSql("   ISM_EUID,");
            sql.addSql("   ISM_EDATE");
            sql.addSql(" ) values ( ");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?);");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getIsmSid());
            sql.addIntValue(model.getSpecKbn());
            sql.addStrValue(model.getIpk100name());
            sql.addIntValue(model.getIpk100level());
            sql.addStrValue(model.getIpk100biko());
            sql.addIntValue(model.getUsrSid());
            sql.addDateValue(model.getNow());
            sql.addIntValue(model.getUsrSid());
            sql.addDateValue(model.getNow());
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
    * <br>[機  能] スペックマスタ情報を削除する。
    * <br>[解  説]
    * <br>[備  考]
    * @param ismSid スペックSID
    * @throws SQLException SQL実行例外
    */
   public void delete(int ismSid) throws SQLException {

       PreparedStatement pstmt = null;
       Connection con = null;
       con = getCon();
       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" delete from");
           sql.addSql("   IPK_SPECM");
           sql.addSql(" where ");
           sql.addSql("   ISM_SID = ?");
           pstmt = con.prepareStatement(sql.toSqlString());
           sql.addIntValue(ismSid);
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
    * <p>Create IPK_SPECM Data Bindding JavaBean From ResultSet
    * @param rs ResultSet
    * @return created IpkSpecmModel
    * @throws SQLException SQL実行例外
    */
   private IpkSpecMstModel __getIpkSpecmFromRs(ResultSet rs) throws SQLException {
       IpkSpecMstModel bean = new IpkSpecMstModel();
       bean.setIsmSid(rs.getInt("ISM_SID"));
       bean.setSpecKbn(rs.getInt("ISM_KBN"));
       bean.setIpk100name(rs.getString("ISM_NAME"));
       bean.setIpk100level(rs.getInt("ISM_LEVEL"));
       bean.setIpk100biko(rs.getString("ISM_BIKO"));
       bean.setUsrSid(rs.getInt("ISM_AUID"));
       bean.setNow(UDate.getInstanceTimestamp(rs.getTimestamp("ISM_ADATE")));
       return bean;
   }
}