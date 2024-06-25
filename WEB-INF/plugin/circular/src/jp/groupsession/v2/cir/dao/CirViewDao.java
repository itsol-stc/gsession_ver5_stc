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
import jp.groupsession.v2.cir.model.CirViewModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <p>CIR_VIEW Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirViewDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirViewDao.class);

    /**
     * <p>Default Constructor
     */
    public CirViewDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirViewDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CIR_VIEW Data Bindding JavaBean
     * @param bean CIR_VIEW Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_VIEW(");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CVW_MEMO,");
            sql.addSql("   CVW_CONF,");
            sql.addSql("   CVW_DSP,");
            sql.addSql("   CVW_AUID,");
            sql.addSql("   CVW_ADATE,");
            sql.addSql("   CVW_EUID,");
            sql.addSql("   CVW_EDATE,");
            sql.addSql("   CVW_CONF_DATE");
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
            sql.addIntValue(bean.getCifSid());
            sql.addIntValue(bean.getCacSid());
            sql.addStrValue(bean.getCvwMemo());
            sql.addIntValue(bean.getCvwConf());
            sql.addIntValue(bean.getCvwDsp());
            sql.addIntValue(bean.getCvwAuid());
            sql.addDateValue(bean.getCvwAdate());
            sql.addIntValue(bean.getCvwEuid());
            sql.addDateValue(bean.getCvwEdate());
            sql.addDateValue(bean.getCvwConfDate());
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
     * <p>Insert CIR_VIEW Data Bindding JavaBean
     * @param beanList CIR_VIEW DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<CirViewModel> beanList) throws SQLException {

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
            sql.addSql(" CIR_VIEW(");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CVW_MEMO,");
            sql.addSql("   CVW_CONF,");
            sql.addSql("   CVW_DSP,");
            sql.addSql("   CVW_AUID,");
            sql.addSql("   CVW_ADATE,");
            sql.addSql("   CVW_EUID,");
            sql.addSql("   CVW_EDATE,");
            sql.addSql("   CVW_CONF_DATE");
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
            for (CirViewModel bean : beanList) {
                sql.addIntValue(bean.getCifSid());
                sql.addIntValue(bean.getCacSid());
                sql.addStrValue(bean.getCvwMemo());
                sql.addIntValue(bean.getCvwConf());
                sql.addIntValue(bean.getCvwDsp());
                sql.addIntValue(bean.getCvwAuid());
                sql.addDateValue(bean.getCvwAdate());
                sql.addIntValue(bean.getCvwEuid());
                sql.addDateValue(bean.getCvwEdate());
                sql.addDateValue(bean.getCvwConfDate());
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
     * <br>[機  能] 状態区分を更新する(対象ユーザが受信したデータ全て)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CIR_VIEW Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateUserCir(CirViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CVW_DSP = ?,");
            sql.addSql("   CVW_EUID = ?,");
            sql.addSql("   CVW_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");

            sql.addIntValue(bean.getCvwDsp());
            sql.addIntValue(bean.getCvwEuid());
            sql.addDateValue(bean.getCvwEdate());
            //where
            sql.addIntValue(bean.getCacSid());

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
     * <br>[機  能] 選択された回覧板の状態区分を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CIR_VIEW Data Bindding JavaBean
     * @param cirSid 回覧板SID
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateDspFlg(CirViewModel bean, String[] cirSid) throws SQLException {

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
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CVW_DSP = ?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CIF_SID in (");

            sql.addIntValue(bean.getCvwDsp());
            //where
            sql.addIntValue(bean.getCacSid());

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
     * <br>[機  能] 受信回覧板情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CIR_VIEW Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateView(CirViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CVW_MEMO = ?,");
            sql.addSql("   CVW_CONF = ?,");
            sql.addSql("   CVW_CONF_DATE = ?,");
            sql.addSql("   CVW_EUID = ?,");
            sql.addSql("   CVW_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CIF_SID = ?");

            sql.addStrValue(bean.getCvwMemo());
            sql.addIntValue(bean.getCvwConf());
            sql.addDateValue(bean.getCvwConfDate());
            sql.addIntValue(bean.getCvwEuid());
            sql.addDateValue(bean.getCvwEdate());
            //where
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getCifSid());

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
     * <br>[機  能] 受信回覧板情報の更新(メモ欄のみ)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CIR_VIEW Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateMemo(CirViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CVW_MEMO = ?,");
            sql.addSql("   CVW_EUID = ?,");
            sql.addSql("   CVW_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CIF_SID = ?");

            sql.addStrValue(bean.getCvwMemo());
            sql.addIntValue(bean.getCvwEuid());
            sql.addDateValue(bean.getCvwEdate());
            //where
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getCifSid());

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
     * <br>[機  能] 受信回覧板情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CIR_VIEW Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateAllView(CirViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set ");
            sql.addSql("   CVW_DSP = ?,");
            sql.addSql("   CVW_EUID = ?,");
            sql.addSql("   CVW_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CVW_DSP = ?");

            sql.addIntValue(bean.getCvwDsp());
            sql.addIntValue(bean.getCvwEuid());
            sql.addDateValue(bean.getCvwEdate());
            //where
            sql.addIntValue(bean.getCacSid());
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
     * <p>Select CIR_VIEW All Data
     * @return List in CIR_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public List <CirViewModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirViewModel> ret = new ArrayList<CirViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CVW_MEMO,");
            sql.addSql("   CVW_CONF,");
            sql.addSql("   CVW_DSP,");
            sql.addSql("   CVW_AUID,");
            sql.addSql("   CVW_ADATE,");
            sql.addSql("   CVW_EUID,");
            sql.addSql("   CVW_EDATE,");
            sql.addSql("   CVW_CONF_DATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_VIEW");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirViewFromRs(rs));
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
     * <p>Select CIR_VIEW All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in CIR_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public List <CirViewModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirViewModel> ret = new ArrayList<CirViewModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CVW_MEMO,");
            sql.addSql("   CVW_CONF,");
            sql.addSql("   CVW_DSP,");
            sql.addSql("   CVW_AUID,");
            sql.addSql("   CVW_ADATE,");
            sql.addSql("   CVW_EUID,");
            sql.addSql("   CVW_EDATE,");
            sql.addSql("   CVW_CONF_DATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" order by ");
            sql.addSql("   CIF_SID asc,");
            sql.addSql("   CAC_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirViewFromRs(rs));
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
     * @return List in CIR_VIEWModel
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
            sql.addSql("   CIR_VIEW");

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
     * <p>Select CIR_VIEW
     * @param bean CIR_VIEW Model
     * @return CIR_VIEWModel
     * @throws SQLException SQL実行例外
     */
    public CirViewModel select(CirViewModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirViewModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CVW_MEMO,");
            sql.addSql("   CVW_CONF,");
            sql.addSql("   CVW_DSP,");
            sql.addSql("   CVW_AUID,");
            sql.addSql("   CVW_ADATE,");
            sql.addSql("   CVW_EUID,");
            sql.addSql("   CVW_EDATE,");
            sql.addSql("   CVW_CONF_DATE");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");
            sql.addSql(" and");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCifSid());
            sql.addIntValue(bean.getCacSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCirViewFromRs(rs);
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
     * <br>[機  能] 未確認の受信回覧板件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @return int 未確認の受信回覧板件数
     * @throws SQLException SQL実行例外
     */
    public int getUnopenedCirCnt(int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(*) as CNT");
            sql.addSql("  from");
            sql.addSql("    CIR_VIEW");
            sql.addSql("  where");
            sql.addSql("    CVW_CONF = ?");
            sql.addSql("  and");
            sql.addSql("    CVW_DSP = ?");
            sql.addSql("  and");
            sql.addSql("    CAC_SID = ?");

            sql.addIntValue(GSConstCircular.CONF_UNOPEN);
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
            sql.addIntValue(cacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] ゴミ箱の未確認の受信回覧板件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @return int 未確認の受信回覧板件数
     * @throws SQLException SQL実行例外
     */
    public int getUnopenedGomiCirCnt(int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    count(*) as CNT");
            sql.addSql("  from");
            sql.addSql("    CIR_VIEW");
            sql.addSql("  where");
            sql.addSql("    CVW_CONF = ?");
            sql.addSql("  and");
            sql.addSql("    CVW_DSP = ?");
            sql.addSql("  and");
            sql.addSql("    CAC_SID = ?");

            sql.addIntValue(GSConstCircular.CONF_UNOPEN);
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_NG);
            sql.addIntValue(cacSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 回覧板SIDを指定して、未確認の回覧板の件数をカウントする
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return int 未確認の回覧板の件数
     * @throws SQLException SQL実行例外
     */
    public int getUnopenedAllCnt(int cirSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CAC_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW ");
            sql.addSql(" where");
            sql.addSql("   CIF_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CVW_CONF = ?");

            sql.addIntValue(cirSid);
            sql.addIntValue(GSConstCircular.CONF_UNOPEN);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 受信タブ自動削除設定に従いデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param cifSids 回覧板SIDリスト
     * @return ret 削除対象回覧板リスト
     * @throws SQLException SQL実行例外
     */
    public List<CirViewModel> select(String[] cifSids)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<CirViewModel> ret = new ArrayList<CirViewModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" where ");

            for (int i = 0; i < cifSids.length; i++) {
                if (i > 0) {
                    sql.addSql(" or");
                }
                sql.addSql("   CIF_SID = ?");
                sql.addIntValue(NullDefault.getInt(cifSids[i], 0));
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CirViewModel bean = null;
            while (rs.next()) {
                bean = new CirViewModel();
                bean.setCifSid(rs.getInt("CIF_SID"));
                bean.setCacSid(rs.getInt("CAC_SID"));
                ret.add(bean);
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
     * <br>[機  能] 受信タブ自動削除設定に従いデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delMdl 自動削除設定モデル
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @return ret 削除対象回覧板リスト
     * @throws SQLException SQL実行例外
     */
    public List<CirViewModel> getDeleteCir(CirAdelModel delMdl, int kbn)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<CirViewModel> ret = new ArrayList<CirViewModel>();

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
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CVW_ADATE <= ?");
            sql.addSql(" and");
            sql.addSql("   CVW_DSP = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            int year;
            int month;
            if (kbn == 1) {
                year = delMdl.getCadJdelYear();
                month = delMdl.getCadJdelMonth();
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

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            CirViewModel bean = null;
            while (rs.next()) {
                bean = new CirViewModel();
                bean.setCifSid(rs.getInt("CIF_SID"));
                bean.setCacSid(rs.getInt("CAC_SID"));
                ret.add(bean);
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
     * <br>[機  能] 指定した回覧板の回覧先情報のデータサイズ合計を取得する
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
            sql.addSql("   sum(octet_length(CVW_MEMO)) as MEMO_SIZE");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW");
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
                dataSize = rs.getLong("MEMO_SIZE");
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
     * <br>[機  能] 回覧板SID(複数)から回覧先情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteCirView(String[] cirSid) throws SQLException {

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
            sql.addSql("   CIR_VIEW");
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
     * <br>[機  能] 受信タブ自動削除設定に従いデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param delList 削除する回覧板リスト
     * @return count 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteData(List<CirViewModel> delList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" where ");

            int i = 0;
            for (CirViewModel model : delList) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql(" (");
                sql.addSql(" CIF_SID=?");
                sql.addSql(" and");
                sql.addSql(" CAC_SID=?");
                sql.addSql(" )");

                sql.addIntValue(model.getCifSid());
                sql.addIntValue(model.getCacSid());
                i++;
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            count = pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    /**
     * <br>[機  能] 指定した送信先データを部瑠璃削除する
     * <br>[解  説] 送信先編集用
     * <br>[備  考]
     * @param delList 削除する回覧板リスト
     * @throws SQLException SQL実行例外
     */
    public void removeView(List<CirViewModel> delList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();


        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql("   from CIR_VIEW");
            sql.addSql(" where ");

            int i = 0;
            for (CirViewModel model : delList) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql(" (");
                sql.addSql(" CIF_SID=?");
                sql.addSql(" and");
                sql.addSql(" CAC_SID=?");
                sql.addSql(" )");

                sql.addIntValue(model.getCifSid());
                sql.addIntValue(model.getCacSid());
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
     * <br>[機  能] 受信タブ自動削除設定に従いデータを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param delList 削除する回覧板リスト
     * @throws SQLException SQL実行例外
     */
    public void delete(List<CirViewModel> delList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set");
            sql.addSql("   CVW_DSP = ?,");
            sql.addSql("   CVW_EUID = ?,");
            sql.addSql("   CVW_EDATE = ?");
            sql.addSql(" where ");

            sql.addIntValue(GSConstCircular.DSPKBN_DSP_DEL);
            sql.addIntValue(0);
            sql.addDateValue(now);

            int i = 0;
            for (CirViewModel model : delList) {
                if (i > 0) {
                    sql.addSql("  or");
                }
                sql.addSql(" (");
                sql.addSql(" CIF_SID=?");
                sql.addSql(" and");
                sql.addSql(" CAC_SID=?");
                sql.addSql(" )");

                sql.addIntValue(model.getCifSid());
                sql.addIntValue(model.getCacSid());
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
     * <br>[機  能] 受信タブ自動削除設定に従いデータを削除する
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
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set");
            sql.addSql("   CVW_DSP = ?,");
            sql.addSql("   CVW_EUID = ?,");
            sql.addSql("   CVW_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CVW_ADATE <= ?");
            sql.addSql(" and");
            sql.addSql("   CVW_DSP = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (CirAdelModel mdl : delList) {

                int year;
                int month;
                if (kbn == 1) {
                    year = mdl.getCadJdelYear();
                    month = mdl.getCadJdelMonth();
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
     * <br>[機  能] 指定した条件に当てはまる[送信済]回覧板の回覧先情報を全て論理削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delList 削除ユーザの個人設定リスト
     * @throws SQLException SQL実行例外
     */
    public void deleteSendView(ArrayList<CirAdelModel> delList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set");
            sql.addSql("   CVW_DSP = ?,");
            sql.addSql("   CVW_EUID = ?,");
            sql.addSql("   CVW_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID in (");
            sql.addSql("     select CIF_SID from CIR_INF");
            sql.addSql("     where ");
            sql.addSql("       CIF_AUID = ?");
            sql.addSql("     and");
            sql.addSql("       CIF_ADATE <= ?");
            sql.addSql("     and");
            sql.addSql("       CIF_JKBN = ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            for (CirAdelModel mdl : delList) {

                UDate delUd = now.cloneUDate();
                delUd.addYear(mdl.getCadSdelYear() * -1);
                delUd.addMonth((mdl.getCadSdelMonth() * -1));
                delUd.setMaxHhMmSs();

                sql.addIntValue(GSConstCircular.DSPKBN_DSP_DEL);
                sql.addIntValue(0);
                sql.addDateValue(now);
                sql.addIntValue(mdl.getCacSid());
                sql.addDateValue(delUd);
                sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);

                log__.info(sql.toLogString());

                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                pstmt.clearParameters();
                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 指定した回覧板の回覧先情報を全て論理削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSid 回覧板SID
     * @param cacSid アカウントSID
     * @param date 日時
     * @throws SQLException SQL実行例外
     */
    public void deleteAllView(int cifSid, int cacSid, UDate date)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" set");
            sql.addSql("   CVW_DSP = ?,");
            sql.addSql("   CVW_EUID = ?,");
            sql.addSql("   CVW_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");

            sql.addIntValue(GSConstCircular.DSPKBN_DSP_DEL);
            sql.addIntValue(cacSid);
            sql.addDateValue(date);
            sql.addIntValue(cifSid);

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
     * <br>[機  能] 指定した回覧板の回覧先ユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSid 回覧板SID
     * @return 回覧先ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getCirViewUser(int cifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> userList = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" where");
            sql.addSql("   CIF_SID = ?");
            sql.addIntValue(cifSid);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                userList.add(rs.getInt("CAC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return userList;
    }

    /**
     * <br>[機  能] 指定した回覧板の回覧先ユーザSIDを取得する
     * <br>[解  説] 複写して新規作成時に使用
     *                     削除アカウントは除外する
     * <br>[備  考]
     * @param cifSid 回覧板SID
     * @return 回覧先ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public List<String> getCirViewUserStr(int cifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> userList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_VIEW.CAC_SID as CAC_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_VIEW left join CIR_ACCOUNT ");
            sql.addSql("     on CIR_VIEW.CAC_SID = CIR_ACCOUNT.CAC_SID ");
            sql.addSql(" where");
            sql.addSql("   CIR_VIEW.CIF_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN = ?");
            sql.addIntValue(cifSid);
            sql.addIntValue(0);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                userList.add(String.valueOf(rs.getInt("CAC_SID")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return userList;
    }

    /**
     * <p>Create CIR_VIEW Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirViewModel
     * @throws SQLException SQL実行例外
     */
    private CirViewModel __getCirViewFromRs(ResultSet rs) throws SQLException {
        CirViewModel bean = new CirViewModel();
        bean.setCifSid(rs.getInt("CIF_SID"));
        bean.setCacSid(rs.getInt("CAC_SID"));
        bean.setCvwMemo(rs.getString("CVW_MEMO"));
        bean.setCvwConf(rs.getInt("CVW_CONF"));
        bean.setCvwDsp(rs.getInt("CVW_DSP"));
        bean.setCvwAuid(rs.getInt("CVW_AUID"));
        bean.setCvwAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CVW_ADATE")));
        bean.setCvwEuid(rs.getInt("CVW_EUID"));
        bean.setCvwEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CVW_EDATE")));
        bean.setCvwConfDate(UDate.getInstanceTimestamp(rs.getTimestamp("CVW_CONF_DATE")));
        return bean;
    }

}
