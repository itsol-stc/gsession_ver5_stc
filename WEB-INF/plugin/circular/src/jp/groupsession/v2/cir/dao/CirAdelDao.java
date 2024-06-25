package jp.groupsession.v2.cir.dao;

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
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.model.CirAdelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>CIR_ADATE Data Access Object
 *
 * @author JTS DaoGenerator
 */
public class CirAdelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirAdelDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirAdelDao(Connection con) {
        super(con);
    }
    /**
     * <br>[機  能] 回覧板自動削除設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return ret 回覧板自動削除設定モデル
     * @throws SQLException SQL実行例外
     */
    public List<CirAdelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<CirAdelModel> ret = new ArrayList<CirAdelModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAD_USR_KBN,");
            sql.addSql("   CAD_DEL_KBN,");
            sql.addSql("   CAD_JDEL_KBN,");
            sql.addSql("   CAD_JDEL_YEAR,");
            sql.addSql("   CAD_JDEL_MONTH,");
            sql.addSql("   CAD_SDEL_KBN,");
            sql.addSql("   CAD_SDEL_YEAR,");
            sql.addSql("   CAD_SDEL_MONTH,");
            sql.addSql("   CAD_DDEL_KBN,");
            sql.addSql("   CAD_DDEL_YEAR,");
            sql.addSql("   CAD_DDEL_MONTH,");
            sql.addSql("   CAD_AUID,");
            sql.addSql("   CAD_ADATE,");
            sql.addSql("   CAD_EUID,");
            sql.addSql("   CAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_ADEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCirAdelFromRs(rs));
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
     * <br>[機  能] 回覧板自動削除設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param cacSid アカウントSID
     * @return ret 回覧板自動削除設定モデル
     * @throws SQLException SQL実行例外
     */
    public CirAdelModel select(int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        CirAdelModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAD_USR_KBN,");
            sql.addSql("   CAD_DEL_KBN,");
            sql.addSql("   CAD_JDEL_KBN,");
            sql.addSql("   CAD_JDEL_YEAR,");
            sql.addSql("   CAD_JDEL_MONTH,");
            sql.addSql("   CAD_SDEL_KBN,");
            sql.addSql("   CAD_SDEL_YEAR,");
            sql.addSql("   CAD_SDEL_MONTH,");
            sql.addSql("   CAD_DDEL_KBN,");
            sql.addSql("   CAD_DDEL_YEAR,");
            sql.addSql("   CAD_DDEL_MONTH,");
            sql.addSql("   CAD_AUID,");
            sql.addSql("   CAD_ADATE,");
            sql.addSql("   CAD_EUID,");
            sql.addSql("   CAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_ADEL");
            sql.addSql(" where");
            sql.addSql("   CAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            log__.info(sql.toLogString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCirAdelFromRs(rs);
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
     * <br>[機  能] 回覧板自動削除設定(全ユーザ)を取得する
     * <br>[解  説]
     * <br>[備  考] [自動削除する] に設定されているユーザが対象
     *
     * @param kbn 1:受信 2:送信 3:ゴミ箱
     * @return ret 回覧板自動削除設定モデルリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CirAdelModel> selectAutoDelUserData(int kbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<CirAdelModel> ret = new ArrayList<CirAdelModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAD_USR_KBN,");
            sql.addSql("   CAD_DEL_KBN,");
            sql.addSql("   CAD_JDEL_KBN,");
            sql.addSql("   CAD_JDEL_YEAR,");
            sql.addSql("   CAD_JDEL_MONTH,");
            sql.addSql("   CAD_SDEL_KBN,");
            sql.addSql("   CAD_SDEL_YEAR,");
            sql.addSql("   CAD_SDEL_MONTH,");
            sql.addSql("   CAD_DDEL_KBN,");
            sql.addSql("   CAD_DDEL_YEAR,");
            sql.addSql("   CAD_DDEL_MONTH,");
            sql.addSql("   CAD_AUID,");
            sql.addSql("   CAD_ADATE,");
            sql.addSql("   CAD_EUID,");
            sql.addSql("   CAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_ADEL");
            sql.addSql(" where");

            if (kbn == 1) {
                sql.addSql("   CAD_JDEL_KBN = ?");
            } else if (kbn == 2) {
                sql.addSql("   CAD_SDEL_KBN = ?");
            } else if (kbn == 3) {
                sql.addSql("   CAD_DDEL_KBN = ?");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstCircular.CIR_AUTO_DEL_LIMIT);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                CirAdelModel mdl = __getCirAdelFromRs(rs);
                ret.add(mdl);
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
     * <br>[機  能] 回覧板自動削除設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean CIR_ADEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirAdelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ADEL(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAD_USR_KBN,");
            sql.addSql("   CAD_DEL_KBN,");
            sql.addSql("   CAD_JDEL_KBN,");
            sql.addSql("   CAD_JDEL_YEAR,");
            sql.addSql("   CAD_JDEL_MONTH,");
            sql.addSql("   CAD_SDEL_KBN,");
            sql.addSql("   CAD_SDEL_YEAR,");
            sql.addSql("   CAD_SDEL_MONTH,");
            sql.addSql("   CAD_DDEL_KBN,");
            sql.addSql("   CAD_DDEL_YEAR,");
            sql.addSql("   CAD_DDEL_MONTH,");
            sql.addSql("   CAD_AUID,");
            sql.addSql("   CAD_ADATE,");
            sql.addSql("   CAD_EUID,");
            sql.addSql("   CAD_EDATE");
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
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getCadUsrKbn());
            sql.addIntValue(bean.getCadDelKbn());
            sql.addIntValue(bean.getCadJdelKbn());
            sql.addIntValue(bean.getCadJdelYear());
            sql.addIntValue(bean.getCadJdelMonth());
            sql.addIntValue(bean.getCadSdelKbn());
            sql.addIntValue(bean.getCadSdelYear());
            sql.addIntValue(bean.getCadSdelMonth());
            sql.addIntValue(bean.getCadDdelKbn());
            sql.addIntValue(bean.getCadDdelYear());
            sql.addIntValue(bean.getCadDdelMonth());
            sql.addIntValue(bean.getCadAuid());
            sql.addDateValue(bean.getCadAdate());
            sql.addIntValue(bean.getCadEuid());
            sql.addDateValue(bean.getCadEdate());

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
     * <br>[機  能] 回覧板自動削除設定を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean CIR_ADEL Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirAdelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ADEL");
            sql.addSql(" set");
            sql.addSql("   CAD_DEL_KBN = ?,");
            sql.addSql("   CAD_JDEL_KBN = ?,");
            sql.addSql("   CAD_JDEL_YEAR = ?,");
            sql.addSql("   CAD_JDEL_MONTH = ?,");
            sql.addSql("   CAD_SDEL_KBN = ?,");
            sql.addSql("   CAD_SDEL_YEAR = ?,");
            sql.addSql("   CAD_SDEL_MONTH = ?,");
            sql.addSql("   CAD_DDEL_KBN = ?,");
            sql.addSql("   CAD_DDEL_YEAR = ?,");
            sql.addSql("   CAD_DDEL_MONTH = ?,");
            sql.addSql("   CAD_EUID = ?,");
            sql.addSql("   CAD_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCadDelKbn());
            sql.addIntValue(bean.getCadJdelKbn());
            sql.addIntValue(bean.getCadJdelYear());
            sql.addIntValue(bean.getCadJdelMonth());
            sql.addIntValue(bean.getCadSdelKbn());
            sql.addIntValue(bean.getCadSdelYear());
            sql.addIntValue(bean.getCadSdelMonth());
            sql.addIntValue(bean.getCadDdelKbn());
            sql.addIntValue(bean.getCadDdelYear());
            sql.addIntValue(bean.getCadDdelMonth());
            sql.addIntValue(bean.getCadEuid());
            sql.addDateValue(bean.getCadEdate());
            //where
            sql.addIntValue(bean.getCacSid());

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
     * <p>Create CIR_ADEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirAdelModel
     * @throws SQLException SQL実行例外
     */
    private CirAdelModel __getCirAdelFromRs(ResultSet rs) throws SQLException {
        CirAdelModel bean = new CirAdelModel();
        bean.setCacSid(rs.getInt("CAC_SID"));
        bean.setCadUsrKbn(rs.getInt("CAD_USR_KBN"));
        bean.setCadDelKbn(rs.getInt("CAD_DEL_KBN"));
        bean.setCadJdelKbn(rs.getInt("CAD_JDEL_KBN"));
        bean.setCadJdelYear(rs.getInt("CAD_JDEL_YEAR"));
        bean.setCadJdelMonth(rs.getInt("CAD_JDEL_MONTH"));
        bean.setCadSdelKbn(rs.getInt("CAD_SDEL_KBN"));
        bean.setCadSdelYear(rs.getInt("CAD_SDEL_YEAR"));
        bean.setCadSdelMonth(rs.getInt("CAD_SDEL_MONTH"));
        bean.setCadDdelKbn(rs.getInt("CAD_DDEL_KBN"));
        bean.setCadDdelYear(rs.getInt("CAD_DDEL_YEAR"));
        bean.setCadDdelMonth(rs.getInt("CAD_DDEL_MONTH"));
        bean.setCadAuid(rs.getInt("CAD_AUID"));
        bean.setCadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CAD_ADATE")));
        bean.setCadEuid(rs.getInt("CAD_EUID"));
        bean.setCadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CAD_EDATE")));
        return bean;
    }
}