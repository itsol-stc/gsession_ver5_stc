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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <p>CIR_INF Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirInfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirInfDao.class);

    /**
     * <p>Default Constructor
     */
    public CirInfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirInfDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 回覧板情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CIR_INF Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertCirInf(CirInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_INF(");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CIF_TITLE,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   CIF_VALUE,");
            sql.addSql("   CIF_AUID,");
            sql.addSql("   CIF_ADATE,");
            sql.addSql("   CIF_EUID,");
            sql.addSql("   CIF_EDATE,");
            sql.addSql("   CIF_JKBN,");
            sql.addSql("   CIF_EKBN,");
            sql.addSql("   CIF_SHOW,");
            sql.addSql("   CIF_MEMO_FLG,");
            sql.addSql("   CIF_MEMO_DATE,");
            sql.addSql("   CIF_EDIT_DATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            sql.addIntValue(bean.getCifSid());
            sql.addStrValue(bean.getCifTitle());
            sql.addIntValue(bean.getGrpSid());
            sql.addStrValue(bean.getCifValue());
            sql.addIntValue(bean.getCifAuid());
            sql.addDateValue(bean.getCifAdate());
            sql.addIntValue(bean.getCifEuid());
            sql.addDateValue(bean.getCifEdate());
            sql.addIntValue(bean.getCifJkbn());
            sql.addIntValue(bean.getCifEkbn());
            sql.addIntValue(bean.getCifShow());
            sql.addIntValue(bean.getCifMemoFlg());
            sql.addDateValue(bean.getCifMemoDate());
            sql.addDateValue(bean.getCifEditDate());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 回覧板情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param beanList CIR_INF DataList
     * @throws SQLException SQL実行例外
     */
    public void insertCirInf(List<CirInfModel> beanList) throws SQLException {

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
            sql.addSql(" CIR_INF(");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CIF_TITLE,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   CIF_VALUE,");
            sql.addSql("   CIF_AUID,");
            sql.addSql("   CIF_ADATE,");
            sql.addSql("   CIF_EUID,");
            sql.addSql("   CIF_EDATE,");
            sql.addSql("   CIF_JKBN,");
            sql.addSql("   CIF_EKBN,");
            sql.addSql("   CIF_SHOW,");
            sql.addSql("   CIF_MEMO_FLG,");
            sql.addSql("   CIF_MEMO_DATE,");
            sql.addSql("   CIF_EDIT_DATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (CirInfModel bean : beanList) {
                sql.addIntValue(bean.getCifSid());
                sql.addStrValue(bean.getCifTitle());
                sql.addIntValue(bean.getGrpSid());
                sql.addStrValue(bean.getCifValue());
                sql.addIntValue(bean.getCifAuid());
                sql.addDateValue(bean.getCifAdate());
                sql.addIntValue(bean.getCifEuid());
                sql.addDateValue(bean.getCifEdate());
                sql.addIntValue(bean.getCifJkbn());
                sql.addIntValue(bean.getCifEkbn());
                sql.addIntValue(bean.getCifShow());
                sql.addIntValue(bean.getCifMemoFlg());
                sql.addDateValue(bean.getCifMemoDate());
                sql.addDateValue(bean.getCifEditDate());
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
     * <p>Update CIR_INF Data Bindding JavaBean
     * @param bean CIR_INF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateCirInf(CirInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INF");
            sql.addSql(" set ");
            sql.addSql("   CIF_SID=?,");
            sql.addSql("   CIF_TITLE=?,");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   CIF_VALUE=?,");
            sql.addSql("   CIF_EUID=?,");
            sql.addSql("   CIF_EDATE=?,");
            sql.addSql("   CIF_JKBN=?,");
            sql.addSql("   CIF_EKBN=?,");
            sql.addSql("   CIF_SHOW=?,");
            sql.addSql("   CIF_MEMO_FLG=?,");
            sql.addSql("   CIF_MEMO_DATE=?,");
            sql.addSql("   CIF_EDIT_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCifSid());
            sql.addStrValue(bean.getCifTitle());
            sql.addIntValue(bean.getGrpSid());
            sql.addStrValue(bean.getCifValue());
            sql.addIntValue(bean.getCifEuid());
            sql.addDateValue(bean.getCifEdate());
            sql.addIntValue(bean.getCifJkbn());
            sql.addIntValue(bean.getCifEkbn());
            sql.addIntValue(bean.getCifShow());
            sql.addIntValue(bean.getCifMemoFlg());
            sql.addDateValue(bean.getCifMemoDate());
            sql.addDateValue(bean.getCifEditDate());
            //where
            sql.addIntValue(bean.getCifSid());

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
     * <p>Update CIR_INF Data Bindding JavaBean
     * @param bean CIR_INF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INF");
            sql.addSql(" set ");
            sql.addSql("   CIF_TITLE=?,");
            sql.addSql("   GRP_SID=?,");
            sql.addSql("   CIF_VALUE=?,");
            sql.addSql("   CIF_AUID=?,");
            sql.addSql("   CIF_ADATE=?,");
            sql.addSql("   CIF_EUID=?,");
            sql.addSql("   CIF_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCifTitle());
            sql.addIntValue(bean.getGrpSid());
            sql.addStrValue(bean.getCifValue());
            sql.addIntValue(bean.getCifAuid());
            sql.addDateValue(bean.getCifAdate());
            sql.addIntValue(bean.getCifEuid());
            sql.addDateValue(bean.getCifEdate());
            //where
            sql.addIntValue(bean.getCifSid());

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
     * <p>状態区分を更新する(対象ユーザが投稿したデータ全て)
     * @param bean CIR_INF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateUserCir(CirInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INF");
            sql.addSql(" set ");
            sql.addSql("   CIF_JKBN=?,");
            sql.addSql("   CIF_EUID=?,");
            sql.addSql("   CIF_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CIF_AUID=?");

            sql.addIntValue(bean.getCifJkbn());
            sql.addIntValue(bean.getCifEuid());
            sql.addDateValue(bean.getCifEdate());
            //where
            sql.addIntValue(bean.getCifAuid());

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
     * <br>[機  能] 状態区分を更新する(対象ユーザのゴミ箱内全て)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CIR_INF Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAllUserCir(CirInfModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INF");
            sql.addSql(" set ");
            sql.addSql("   CIF_JKBN=?,");
            sql.addSql("   CIF_EUID=?,");
            sql.addSql("   CIF_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CIF_AUID=?");
            sql.addSql(" and ");
            sql.addSql("   CIF_JKBN=?");

            sql.addIntValue(bean.getCifJkbn());
            sql.addIntValue(bean.getCifEuid());
            sql.addDateValue(bean.getCifEdate());
            //where
            sql.addIntValue(bean.getCifAuid());
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_NG);

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
     * <p>状態区分を更新する
     * @param bean CIR_INF Data Bindding JavaBean
     * @param cirSid 回覧板SID
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbn(CirInfModel bean, String[] cirSid) throws SQLException {

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
            sql.addSql(" update");
            sql.addSql("   CIR_INF");
            sql.addSql(" set ");
            sql.addSql("   CIF_JKBN=?,");
            sql.addSql("   CIF_EUID=?,");
            sql.addSql("   CIF_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID in (");

            sql.addIntValue(bean.getCifJkbn());
            sql.addIntValue(bean.getCifEuid());
            sql.addDateValue(bean.getCifEdate());

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
     * <br>[機  能] 回覧板SID(複数)から回覧板情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return List in CirInfModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getCirListFromCirSid(String[] cirSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
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
            sql.addSql("   CIF_TITLE,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   CIF_VALUE,");
            sql.addSql("   CIF_AUID,");
            sql.addSql("   CIF_ADATE,");
            sql.addSql("   CIF_EUID,");
            sql.addSql("   CIF_EDATE,");
            sql.addSql("   CIF_SHOW,");
            sql.addSql("   CIF_MEMO_FLG,");
            sql.addSql("   CIF_MEMO_DATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_INF");
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
            sql.addSql(" order by ");
            sql.addSql("   CIF_ADATE desc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCirDspFromRs(rs));
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
     * <br>[機  能] 回覧板SID(複数)から回覧板情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSidList 回覧板SID
     * @return List in CirInfModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getCirListFromCirSid(
            ArrayList<String> cirSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        if (cirSidList == null) {
            return ret;
        }
        if (cirSidList.size() < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CIF_TITLE,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   CIF_VALUE,");
            sql.addSql("   CIF_AUID,");
            sql.addSql("   CIF_ADATE,");
            sql.addSql("   CIF_EUID,");
            sql.addSql("   CIF_EDATE,");
            sql.addSql("   CIF_SHOW,");
            sql.addSql("   CIF_MEMO_FLG,");
            sql.addSql("   CIF_MEMO_DATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_INF");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID in (");

            boolean sepFlg = false;
            for (String sid : cirSidList) {
                if (sepFlg) {
                    sql.addSql("     , ");
                } else {
                    sepFlg = true;
                }
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(sid, 0));
            }

            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   CIF_ADATE desc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCirDspFromRs(rs));
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
     * <p>Select CIR_INF All Data
     * @return List in CIR_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<CirInfModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirInfModel> ret = new ArrayList<CirInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CIF_TITLE,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   CIF_VALUE,");
            sql.addSql("   CIF_AUID,");
            sql.addSql("   CIF_ADATE,");
            sql.addSql("   CIF_EUID,");
            sql.addSql("   CIF_EDATE,");
            sql.addSql("   CIF_JKBN,");
            sql.addSql("   CIF_EKBN,");
            sql.addSql("   CIF_SHOW,");
            sql.addSql("   CIF_MEMO_FLG,");
            sql.addSql("   CIF_MEMO_DATE,");
            sql.addSql("   CIF_EDIT_DATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_INF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirInfFromRs(rs));
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
     * <p>Select CIR_INF All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in CIR_INFModel
     * @throws SQLException SQL実行例外
     */
    public List<CirInfModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirInfModel> ret = new ArrayList<CirInfModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CIF_TITLE,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   CIF_VALUE,");
            sql.addSql("   CIF_AUID,");
            sql.addSql("   CIF_ADATE,");
            sql.addSql("   CIF_EUID,");
            sql.addSql("   CIF_EDATE,");
            sql.addSql("   CIF_JKBN,");
            sql.addSql("   CIF_EKBN,");
            sql.addSql("   CIF_SHOW,");
            sql.addSql("   CIF_MEMO_FLG,");
            sql.addSql("   CIF_MEMO_DATE,");
            sql.addSql("   CIF_EDIT_DATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_INF");
            sql.addSql(" order by ");
            sql.addSql("   CIF_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirInfFromRs(rs));
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
     * <p>全件数を取得する
     * @return List in CIR_INFModel
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
            sql.addSql("   CIR_INF");

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
     * <br>[機  能] 回覧板情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return CIR_INFModel
     * @throws SQLException SQL実行例外
     */
    public CirInfModel getCirInfo(int cirSid) throws SQLException {
        return getCirInfo(cirSid, -1);
    }

    /**
     * <br>[機  能] 回覧板情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @param jkbn 状態区分
     * @return CIR_INFModel
     * @throws SQLException SQL実行例外
     */
    public CirInfModel getCirInfo(int cirSid, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirInfModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CIF_TITLE,");
            sql.addSql("   GRP_SID,");
            sql.addSql("   CIF_VALUE,");
            sql.addSql("   CIF_AUID,");
            sql.addSql("   CIF_ADATE,");
            sql.addSql("   CIF_EUID,");
            sql.addSql("   CIF_EDATE,");
            sql.addSql("   CIF_JKBN,");
            sql.addSql("   CIF_EKBN,");
            sql.addSql("   CIF_SHOW,");
            sql.addSql("   CIF_MEMO_FLG,");
            sql.addSql("   CIF_MEMO_DATE,");
            sql.addSql("   CIF_EDIT_DATE");
            sql.addSql(" from");
            sql.addSql("   CIR_INF");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");
            sql.addIntValue(cirSid);

            if (jkbn >= 0) {
                sql.addSql(" and");
                sql.addSql("   CIF_JKBN = ?");
                sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = __getCirInfFromRs(rs);
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
     * <br>[機  能] 送信タブ自動削除設定に従いデータを取得する(全ユーザ対象)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delMdl 自動削除設定モデル
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @return ret 削除回覧板リスト
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getDeleteCir(CirAdelModel delMdl, int kbn)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<Integer> ret = new ArrayList<Integer>();

        UDate now = new UDate();
        int jkbn = -1;

        if (kbn == 1) {
            jkbn = GSConstCircular.DSPKBN_DSP_OK;
        } else if (kbn == 2) {
            jkbn = GSConstCircular.DSPKBN_DSP_NG;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIF_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_INF");
            sql.addSql(" where ");
            sql.addSql("   CIF_ADATE <= ?");
            sql.addSql(" and");
            sql.addSql("   CIF_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            int year;
            int month;
            if (kbn == 1) {
                year = delMdl.getCadSdelYear();
                month = delMdl.getCadSdelMonth();
            } else {
                year = delMdl.getCadDdelYear();
                month = delMdl.getCadDdelMonth();
            }

            UDate delUd = now.cloneUDate();

            delUd.addYear((year * -1));
            delUd.addMonth((month * -1));
            delUd.setHour(GSConstMain.DAY_END_HOUR);
            delUd.setMinute(GSConstMain.DAY_END_MINUTES);
            delUd.setSecond(GSConstMain.DAY_END_SECOND);
            delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

            sql.addDateValue(delUd);
            sql.addIntValue(jkbn);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("CIF_SID"));
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
     * <br>[機  能] 回覧板SID(複数)から回覧板情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteCirInf(String[] cirSid) throws SQLException {

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
            sql.addSql("   CIR_INF");
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
     * <br>[機  能] 送信タブ自動削除設定に従いデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delList 削除ユーザの個人設定リスト
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行例外
     */
    public void delete(ArrayList<CirAdelModel> delList, int kbn)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();
        int jkbn = -1;

        if (kbn == 1) {
            jkbn = GSConstCircular.DSPKBN_DSP_OK;
        } else if (kbn == 2) {
            jkbn = GSConstCircular.DSPKBN_DSP_NG;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INF");
            sql.addSql(" set");
            sql.addSql("   CIF_JKBN = ?,");
            sql.addSql("   CIF_EUID = ?,");
            sql.addSql("   CIF_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   CIF_AUID = ?");
            sql.addSql(" and");
            sql.addSql("   CIF_ADATE <= ?");
            sql.addSql(" and");
            sql.addSql("   CIF_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (CirAdelModel mdl : delList) {

                int year;
                int month;

                if (kbn == 1) {
                    year = mdl.getCadSdelYear();
                    month = mdl.getCadSdelMonth();
                } else {
                    year = mdl.getCadDdelYear();
                    month = mdl.getCadDdelMonth();
                }
                UDate delUd = now.cloneUDate();

                delUd.addYear((year * -1));
                delUd.addMonth((month * -1));
                delUd.setHour(GSConstMain.DAY_END_HOUR);
                delUd.setMinute(GSConstMain.DAY_END_MINUTES);
                delUd.setSecond(GSConstMain.DAY_END_SECOND);
                delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

                pstmt.setInt(1, GSConstCircular.DSPKBN_DSP_DEL);
                pstmt.setInt(2, 0);
                pstmt.setTimestamp(3, now.toSQLTimestamp());
                pstmt.setInt(4, mdl.getCacSid());
                pstmt.setTimestamp(5, delUd.toSQLTimestamp());
                pstmt.setInt(6, jkbn);

                //ログ出力
                sql.addIntValue(GSConstCircular.DSPKBN_DSP_DEL);
                sql.addIntValue(0);
                sql.addDateValue(now);
                sql.addIntValue(mdl.getCacSid());
                sql.addDateValue(delUd);
                sql.addIntValue(jkbn);

                log__.info(sql.toLogString());
                sql.clearValue();

                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 受信タブ自動削除設定に従いデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param delList 削除する回覧板リスト
     * @throws SQLException SQL実行例外
     */
    public void delete(List<Integer> delList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_INF");
            sql.addSql(" set");
            sql.addSql("   CIF_JKBN = ?,");
            sql.addSql("   CIF_EUID = ?,");
            sql.addSql("   CIF_EDATE = ?");
            sql.addSql(" where ");

            sql.addIntValue(GSConstCircular.DSPKBN_DSP_DEL);
            sql.addIntValue(0);
            sql.addDateValue(now);

            int i = 0;
            for (Integer cifSid : delList) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql(" CIF_SID=?");

                sql.addIntValue(cifSid);
                i++;
            }

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 対象の回覧板が"送信済"のものかを判定する
     * <br>[解  説]
     * <br>[備  考] "ゴミ箱" 内の回覧板は除外する
     * @param cifSid 回覧板SID
     * @param userSid ユーザSID
     * @return true: "送信済"回覧板 false: "送信済"以外の回覧板 or 存在しない(削除済み)
     * @throws SQLException SQL実行例外
     */
    public boolean isSendCircular(int cifSid, int userSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        boolean result = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select 1 from CIR_INF");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CIF_AUID = ?");
            sql.addSql(" and");
            sql.addSql("   (CIF_JKBN = ?");
            sql.addSql("    or");
            sql.addSql("    CIF_JKBN = ? )");
            sql.addIntValue(cifSid);
            sql.addIntValue(userSid);
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_NG);
            sql.setPagingValue(0, 1);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <br>[機  能] 指定した回覧板情報のデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSidList 回覧板SID
     * @return データサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalDataSize(List<Integer> cifSidList) throws SQLException {

        if (cifSidList == null || cifSidList.isEmpty()) {
            return 0;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(CIF_TITLE)) as TITLE_SIZE,");
            sql.addSql("   sum(octet_length(CIF_VALUE)) as CONTENT_SIZE");
            sql.addSql(" from");
            sql.addSql("   CIR_INF");
            sql.addSql(" where");
            sql.addSql("   CIF_SID in (");

            for (int idx = 0; idx < cifSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(cifSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                dataSize = rs.getLong("TITLE_SIZE");
                dataSize += rs.getLong("CONTENT_SIZE");
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
     * <p>Create CIR_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirInfModel
     * @throws SQLException SQL実行例外
     */
    private CirInfModel __getCirInfFromRs(ResultSet rs) throws SQLException {
        CirInfModel bean = new CirInfModel();
        bean.setCifSid(rs.getInt("CIF_SID"));
        bean.setCifTitle(rs.getString("CIF_TITLE"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setCifValue(rs.getString("CIF_VALUE"));
        bean.setCifAuid(rs.getInt("CIF_AUID"));
        bean.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
        bean.setCifEuid(rs.getInt("CIF_EUID"));
        bean.setCifEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_EDATE")));
        bean.setCifEkbn(rs.getInt("CIF_EKBN"));
        bean.setCifShow(rs.getInt("CIF_SHOW"));
        bean.setCifMemoFlg(rs.getInt("CIF_MEMO_FLG"));
        bean.setCifMemoDate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_MEMO_DATE")));

        if (bean.getCifEkbn() == GSConstCircular.CIR_EDIT) {
            bean.setCifEditDate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_EDIT_DATE")));
        }

        return bean;
    }

    /**
     * <p>Create CIR_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirInfModel
     * @throws SQLException SQL実行例外
     */
    private CircularDspModel __getCirDspFromRs(ResultSet rs) throws SQLException {
        CircularDspModel bean = new CircularDspModel();
        bean.setCifSid(rs.getInt("CIF_SID"));
        bean.setCifTitle(rs.getString("CIF_TITLE"));
        bean.setCifValue(rs.getString("CIF_VALUE"));
        bean.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
        bean.setCifShow(rs.getInt("CIF_SHOW"));
        bean.setCifMemoFlg(rs.getInt("CIF_MEMO_FLG"));
        bean.setCifMemoDate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_MEMO_DATE")));
        return bean;
    }

}
