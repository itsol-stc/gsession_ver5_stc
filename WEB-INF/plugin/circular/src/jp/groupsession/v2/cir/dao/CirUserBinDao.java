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
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.model.CirUserBinModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <p>CIR_USER_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirUserBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirUserBinDao.class);

    /**
     * <p>Default Constructor
     */
    public CirUserBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirUserBinDao(Connection con) {
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
            sql.addSql("drop table CIR_USER_BIN");

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
            sql.addSql(" create table CIR_USER_BIN (");
            sql.addSql("   CIF_SID integer not null,");
            sql.addSql("   CAC_SID integer not null,");
            sql.addSql("   CUB_BIN_SID bigint not null,");
            sql.addSql("   primary key (CIF_SID,CAC_SID,CUB_BIN_SID)");
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
     * <p>Insert CIR_USER_BIN Data Bindding JavaBean
     * @param bean CIR_USER_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirUserBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_USER_BIN(");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CUB_BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCifSid());
            sql.addIntValue(bean.getCacSid());
            sql.addLongValue(bean.getCubBinSid());
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
     * <br>[機  能] ユーザ確認時の添付情報を登録する
     * <br>[解  説] Listを渡し、複数登録する
     * <br>[備  考]
     * @param bean CIR_USER_BIN Data Bindding JavaBean
     * @param binList バイナリSIDリスト
     * @throws SQLException SQL実行例外
     */
    public void insertCubBinList(CirUserBinModel bean, List<String> binList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (binList.size() < 1) {
            return;
        }

        try {

            for (int i = 0; i < binList.size(); i++) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" CIR_USER_BIN(");
                sql.addSql("   CIF_SID,");
                sql.addSql("   CAC_SID,");
                sql.addSql("   CUB_BIN_SID");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.addIntValue(bean.getCifSid());
                sql.addIntValue(bean.getCacSid());
                sql.addLongValue(NullDefault.getLong(binList.get(i), 0));
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();

            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update CIR_USER_BIN Data Bindding JavaBean
     * @param bean CIR_USER_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirUserBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");
            sql.addSql(" and");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   CUB_BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getCifSid());
            sql.addIntValue(bean.getCacSid());
            sql.addLongValue(bean.getCubBinSid());

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
     * <p>Select CIR_USER_BIN All Data
     * @return List in CIR_USER_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<CirUserBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirUserBinModel> ret = new ArrayList<CirUserBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CUB_BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_USER_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirUserBinFromRs(rs));
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
     * <br>[機  能] 選択された回覧板に紐付いているユーザ添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return List in CirBinModel
     * @throws SQLException SQL実行例外
     */
    public List<CirUserBinModel> getBinList(String[] cirSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirUserBinModel> ret = new ArrayList<CirUserBinModel>();
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
            sql.addSql("   CAC_SID,");
            sql.addSql("   CUB_BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_USER_BIN");
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
                ret.add(__getCirUserBinFromRs(rs));
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
     * <p>指定した回覧板のユーザ添付ファイルのバイナリSIDを取得する
     * @param cifSid CIF_SID
     * @return List in CIR_USER_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> selectBinsSetCif(int cifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUB_BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("CUB_BIN_SID"));
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
     * <p>指定したユーザSIDのユーザ添付ファイルのバイナリSIDを取得する
     * @param cacSid CAC_SID
     * @return List in CIR_USER_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> selectBinsSetUsr(int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CUB_BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("CUB_BIN_SID"));
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
     * <br>[機  能] 指定されたバイナリSIDが送信先ユーザの添付ファイルかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSid 回覧板SID
     * @param binSid バイナリSID
     * @param cacSid ユーザ添付ファイルダウンロード時選択アカウントSID
     * @throws SQLException SQL実行時例外
     * @return true:参照可能  false:参照不可能
     */
    public boolean canViewCirUsrTempfile(int cifSid, long binSid, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF,");
            sql.addSql("   CIR_INF,");
            sql.addSql("   CIR_VIEW,");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" where");
            sql.addSql("   CIR_INF.CIF_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CIR_INF.CIF_JKBN <> ?");
            sql.addSql(" and");
            sql.addSql("   CIR_VIEW.CAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CIR_INF.CIF_SID = CIR_VIEW.CIF_SID");
            sql.addSql(" and");
            sql.addSql("   CIR_INF.CIF_SID = CIR_USER_BIN.CIF_SID");
            sql.addSql(" and");
            sql.addSql("   CIR_USER_BIN.CUB_BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CIR_VIEW.CAC_SID = CIR_USER_BIN.CAC_SID");

            sql.addIntValue(cifSid);
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_DEL);
            sql.addIntValue(cacSid);
            sql.addLongValue(binSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return cnt > 0;
    }



    /**
     * <p>Delete CIR_USER_BIN
     * @param cifSid CIF_SID
     * @param cacSid CAC_SID
     * @param cubBinSid CUB_BIN_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int cifSid, int cacSid, Long cubBinSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");
            sql.addSql(" and");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   CUB_BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cifSid);
            sql.addIntValue(cacSid);
            sql.addLongValue(cubBinSid);

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
     * <p>指定した回覧板・ユーザのユーザ添付ファイルのバイナリ情報を削除する
     * @param cifSid CIF_SID
     * @param cacSid CAC_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteBins(int cifSid, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");
            sql.addSql(" and");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cifSid);
            sql.addIntValue(cacSid);

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
     * <p>指定した回覧板の回覧先ユーザ添付ファイルのバイナリ情報を削除する
     * @param cifSid 回覧板SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteBinsSetCir(int cifSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cifSid);

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
     * <br>[機  能] 回覧板SID(複数)から回覧先ユーザ添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinsSetCir(String[] cirSid) throws SQLException {

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
            sql.addSql("   CIR_USER_BIN");
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
     * <p>指定したユーザSIDのユーザ添付ファイルのバイナリ情報を削除する
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteBinsSetUsr(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

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
     * <p>Create CIR_USER_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirUserBinModel
     * @throws SQLException SQL実行例外
     */
    private CirUserBinModel __getCirUserBinFromRs(ResultSet rs) throws SQLException {
        CirUserBinModel bean = new CirUserBinModel();
        bean.setCifSid(rs.getInt("CIF_SID"));
        bean.setCacSid(rs.getInt("CAC_SID"));
        bean.setCubBinSid(rs.getLong("CUB_BIN_SID"));
        return bean;
    }
    /**
     *
     * <br>[機  能] 回覧板送信先情報に紐づく確認時添付を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSid 回覧板SID
     * @param accSidList 対象アカウントSID
     * @return 回覧板添付情報
     * @throws SQLException  SQL実行例外
     */
    public List<CirUserBinModel> getBinList(int cifSid,
            List<Integer> accSidList) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirUserBinModel> ret = new ArrayList<CirUserBinModel>();
        con = getCon();

        if (accSidList == null) {
            return ret;
        }
        if (accSidList.size() < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CUB_BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID = ?");
            sql.addIntValue(cifSid);
            sql.addSql("   and CAC_SID in (");
            for (int i = 0; i < accSidList.size(); i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql(" " + accSidList.get(i));

            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCirUserBinFromRs(rs));
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
     * <br>[機  能] 指定した回覧先情報の添付ファイルサイズ合計を取得する
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
            sql.addSql("   CIR_USER_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   CIR_USER_BIN.CUB_BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   CIR_USER_BIN.CIF_SID in (");

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
     * <p>指定した回覧板・ユーザのユーザ添付ファイルのバイナリ情報を削除する
     * @param cifSid 回覧板SID
     * @param accSidList 対象アカウントSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteBins(int cifSid, List<Integer> accSidList) throws SQLException {
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        if (accSidList == null) {
            return count;
        }
        if (accSidList.size() < 1) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete ");
            sql.addSql(" from ");
            sql.addSql("   CIR_USER_BIN");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID = ?");
            sql.addIntValue(cifSid);
            sql.addSql("   and CAC_SID in (");
            for (int i = 0; i < accSidList.size(); i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql(" " + accSidList.get(i));

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
}
