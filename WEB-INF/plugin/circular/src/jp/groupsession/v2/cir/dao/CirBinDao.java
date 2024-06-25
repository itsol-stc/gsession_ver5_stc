package jp.groupsession.v2.cir.dao;

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
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.model.CirBinModel;

/**
 * <p>CIR_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirBinDao.class);

    /**
     * <p>Default Constructor
     */
    public CirBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirBinDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CIR_BIN Data Bindding JavaBean
     * @param bean CIR_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     * @return 更新件数
     */
    public int insert(CirBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_BIN(");
            sql.addSql("   CIF_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCifSid());
            sql.addLongValue(bean.getBinSid());
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
     * <p>Update CIR_BIN Data Bindding JavaBean
     * @param bean CIR_BIN Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getCifSid());
            sql.addLongValue(bean.getBinSid());

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
     * <br>[機  能] 選択された回覧板に紐付いている添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return List in CirBinModel
     * @throws SQLException SQL実行例外
     */
    public List<CirBinModel> getBinList(String[] cirSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirBinModel> ret = new ArrayList<CirBinModel>();
        con = getCon();

        if (cirSid == null) {
            return ret;
        }
        if (cirSid.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_BIN");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID in (");

            for (int i = 0; i < cirSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(cirSid[i], 0));

                if (i < cirSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCirBinFromRs(rs));
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
     * <p>Select CIR_BIN
     * @return CIR_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<CirBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirBinModel> ret = new ArrayList<CirBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIF_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirBinFromRs(rs));
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
     * <p>Select CIR_BIN
     * @param bean CIR_BIN Model
     * @return CIR_BINModel
     * @throws SQLException SQL実行例外
     */
    public CirBinModel select(CirBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIF_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_BIN");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCifSid());
            sql.addLongValue(bean.getBinSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCirBinFromRs(rs);
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
     * <br>[機  能] 指定した回覧板情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSidList 回覧板SID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<Integer> cifSidList) throws SQLException {

        if (cifSidList == null || cifSidList.isEmpty()) {
            return 0;
        }

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
            sql.addSql("   CIR_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   CIR_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CIR_BIN.CIF_SID in (");

            for (int idx = 0; idx < cifSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addLongValue(cifSidList.get(idx));
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

    /**
     * <br>[機  能] 回覧板SID(複数)から回覧板添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteCriBin(String[] cirSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (cirSid == null) {
            return count;
        }
        if (cirSid.length < 1) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_BIN");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID in (");

            for (int i = 0; i < cirSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(cirSid[i], 0));

                if (i < cirSid.length - 1) {
                    sql.addSql("     , ");
                }
            }

            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Create CIR_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirBinModel
     * @throws SQLException SQL実行例外
     */
    private CirBinModel __getCirBinFromRs(ResultSet rs) throws SQLException {
        CirBinModel bean = new CirBinModel();
        bean.setCifSid(rs.getInt("CIF_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}
