package jp.groupsession.v2.ntp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.ntp.model.NtpBinModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;

/**
 * <p>NtpBinDao Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(NtpBinDao.class);

    /**
     * <p>Default Constructor
     */
    public NtpBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public NtpBinDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert NTP_BIN Data Bindding JavaBean
     * @param bean NTP_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(NtpBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" NTP_BIN(");
            sql.addSql("   NTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNtpSid());
            sql.addLongValue(bean.getBinSid());
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
     * <br>[機  能] 日報添付情報を登録する
     * <br>[解  説] Listを渡し複数登録する
     * <br>[備  考]
     * @param bean NtpSmeisModel
     * @param binList List in String
     * @throws SQLException SQL実行例外
     */
    public void insertNtpBin(NtpDataModel bean, List<String> binList) throws SQLException {

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
                sql.addSql(" NTP_BIN(");
                sql.addSql("   NTP_SID,");
                sql.addSql("   BIN_SID");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getNipSid());
                sql.addLongValue(NullDefault.getLong(binList.get(i), 0));

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Update NTP_BIN Data Bindding JavaBean
     * @param bean NTP_BIN Data Bindding JavaBean
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(NtpBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   NTP_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   NTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getNtpSid());
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
     * <br>[機  能] 選択された日報紐付いている添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ntpSid 日報SID
     * @return List in NtpBinModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpBinModel> getBinList(int ntpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpBinModel> ret = new ArrayList<NtpBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_BIN");
            sql.addSql(" where ");
            sql.addSql("   NTP_SID = ?");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getNtpBinFromRs(rs));
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
     * <br>[機  能] 選択された日報紐付いている添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ntpSid 日報SID
     * @return List in NtpBinModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpBinModel> getBinList(String[] ntpSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpBinModel> ret = new ArrayList<NtpBinModel>();
        con = getCon();

        if (ntpSid == null) {
            return ret;
        }
        if (ntpSid.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_BIN");
            sql.addSql(" where ");
            sql.addSql("   NTP_SID in (");

            for (int i = 0; i < ntpSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(ntpSid[i], 0));

                if (i < ntpSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getNtpBinFromRs(rs));
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
     * <br>[機  能] 添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return List in NtpBinModel
     * @throws SQLException SQL実行例外
     */
    public List<NtpBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpBinModel> ret = new ArrayList<NtpBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   NTP_BIN");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getNtpBinFromRs(rs));
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
     * <p>Select NTP_BIN
     * @param bean NTP_BIN Model
     * @return NTP_BINModel
     * @throws SQLException SQL実行例外
     */
    public NtpBinModel select(NtpBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        NtpBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   NTP_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_BIN");
            sql.addSql(" where ");
            sql.addSql("   NTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getNtpSid());
            sql.addLongValue(bean.getBinSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getNtpBinFromRs(rs);
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
     * <br>[機  能] 日報SIDから添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smsSid 日報SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteNtpBin(int smsSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_BIN");
            sql.addSql(" where ");
            sql.addSql("   NTP_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(smsSid);
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
     * <br>[機  能] 日報SID(複数)から添付情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smsSidList 日報SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteNtpBin(List<String> smsSidList) throws SQLException {

        if (smsSidList == null || smsSidList.size() <= 0) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   NTP_BIN");
            sql.addSql(" where ");
            if (smsSidList.size() == 1) {
                sql.addSql("   NTP_SID = ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(0)));
            } else {
                sql.addSql("   NTP_SID in (");
                for (int idx = 0; idx < smsSidList.size() - 1; idx++) {
                    sql.addSql("     ?,");
                    sql.addIntValue(Integer.parseInt(smsSidList.get(idx)));
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(smsSidList.size() - 1)));
                sql.addSql("   )");
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
     * <br>[機  能] 日報SID(複数)からバイナリSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smsSidList 日報SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public List<Long> selectBinSidList(List<String> smsSidList) throws SQLException {

        if (smsSidList == null || smsSidList.size() <= 0) {
            return null;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        List<Long> ret = new ArrayList<Long>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_BIN");
            sql.addSql(" where ");
            if (smsSidList.size() == 1) {
                sql.addSql("   NTP_SID = ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(0)));
            } else {
                sql.addSql("   NTP_SID in (");
                for (int idx = 0; idx < smsSidList.size() - 1; idx++) {
                    sql.addSql("     ?,");
                    sql.addIntValue(Integer.parseInt(smsSidList.get(idx)));
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(smsSidList.get(smsSidList.size() - 1)));
                sql.addSql("   )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong("BIN_SID"));
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
     * <br>[機  能] 日報SID(複数)からバイナリSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smsSidList 日報SID
     * @return int 削除件数
     * @throws SQLException SQL実行例外
     */
    public List<Long> selectBinSidList(String[] smsSidList) throws SQLException {

        if (smsSidList == null || smsSidList.length <= 0) {
            return null;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        List<Long> ret = new ArrayList<Long>();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   NTP_BIN");
            sql.addSql(" where ");
            if (smsSidList.length == 1) {
                sql.addSql("   NTP_SID = ?");
                sql.addIntValue(Integer.parseInt(smsSidList[0]));
            } else {
                sql.addSql("   NTP_SID in (");
                for (int idx = 0; idx < smsSidList.length - 1; idx++) {
                    sql.addSql("     ?,");
                    sql.addIntValue(Integer.parseInt(smsSidList[idx]));
                }
                sql.addSql("     ?");
                sql.addIntValue(Integer.parseInt(smsSidList[smsSidList.length - 1]));
                sql.addSql("   )");
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getLong("BIN_SID"));
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
     * <br>[機  能] 日報SIDから添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid 日報SID
     * @return ArrayList in CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnBinfModel> getFileList(int mailSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    NTP_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    NTP_BIN.NTP_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    NTP_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  order by");
            sql.addSql("    NTP_BIN.BIN_SID");

            sql.addIntValue(mailSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel retMdl = new CmnBinfModel();
                retMdl.setBinSid(rs.getLong("BIN_SID"));
                retMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                retMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                retMdl.setBinFileSizeDsp(strSize);
                ret.add(retMdl);
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
     * <br>[機  能] 選択された日報に紐付いている添付ファイル名を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid 日報SID
     * @return ret 紐付いている添付ファイルの名称リスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getBinFileNameList(int mailSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        ArrayList<String> ret = new ArrayList<String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME as fileName");
            sql.addSql(" from ");
            sql.addSql("   NTP_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   NTP_BIN.NTP_SID = ?");
            sql.addSql(" and");
            sql.addSql("   NTP_BIN.BIN_SID = CMN_BINF.BIN_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(mailSid);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("fileName"));
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
     * <br>[機  能] 日報添付ファイルの存在の有無を判定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param ntpSid 日報SID
     * @param usrSid ユーザSID
     * @param binSid バイナリSID
     * @return count 0:存在しない 1以上:存在する
     * @throws SQLException SQL実行例外
     */
    public int getBinFileCnt(int ntpSid, int usrSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) CNT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_BIN, ");
            sql.addSql("   CMN_BINF, ");
            sql.addSql("   (select count(*) as J_CNT from NTP_JMEIS ");
            sql.addSql("      where SMJ_SID = ? ");
            sql.addSql("      and USR_SID=?) as JCNT, ");
            sql.addSql("   (select count(*) as S_CNT from NTP_SMEIS ");
            sql.addSql("      where SMS_SID = ? ");
            sql.addSql("      and USR_SID=?) as SCNT, ");
            sql.addSql("   (select count(*) as W_CNT from NTP_WMEIS ");
            sql.addSql("      where SMW_SID = ? ");
            sql.addSql("      and USR_SID=?) as WCNT ");
            sql.addSql(" where ");
            sql.addSql("   NTP_BIN.BIN_SID = CMN_BINF.BIN_SID ");
            sql.addSql(" and ");
            sql.addSql("   NTP_BIN.BIN_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   NTP_BIN.NTP_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   ( ");
            sql.addSql("   JCNT.J_CNT > 0 ");
            sql.addSql("   or ");
            sql.addSql("   SCNT.S_CNT > 0 ");
            sql.addSql("   or ");
            sql.addSql("   WCNT.W_CNT > 0 ");
            sql.addSql("   ) ");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(ntpSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(ntpSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(ntpSid);
            sql.addIntValue(usrSid);
            sql.addLongValue(binSid);
            sql.addIntValue(ntpSid);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] 日報添付ファイルかチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param nipSid 日報SID
     * @param binSid バイナリSID
     * @return count 0:存在しない 1以上:存在する
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckNtpFile(int nipSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int cnt = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_BIN ");
            sql.addSql(" where ");
            sql.addSql("   NTP_BIN.NTP_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   NTP_BIN.BIN_SID = ? ");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(nipSid);
            sql.addLongValue(binSid);

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
     * <p>Create NTP_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created NtpBinModel
     * @throws SQLException SQL実行例外
     */
    private NtpBinModel __getNtpBinFromRs(ResultSet rs) throws SQLException {
        NtpBinModel bean = new NtpBinModel();
        bean.setNtpSid(rs.getInt("NTP_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }

    /**
     * <br>[機  能] 日報SIDから添付ファイル情報を取得する
     * <br>[解  説] 日報SIDをキーのMapを返す
     * <br>[備  考]
     *
     * @param nipSidSet 日報SID
     * @return ArrayList in CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, List<CmnBinfModel>> getFileMap(
            Collection<Integer> nipSidSet) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<Integer, List<CmnBinfModel>> ret = new HashMap<>();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();
        if (nipSidSet == null || nipSidSet.size() <= 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    NTP_BIN.NTP_SID,");
            sql.addSql("    CMN_BINF.BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    NTP_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            int i = 0;
            sql.addSql("   (NTP_BIN.NTP_SID in (");
            for (Integer sid : nipSidSet) {
                if (sid == null) {
                    continue;
                }
                if (i > 0 && i % 1000 == 0) {
                    sql.addSql("  ) or NTP_BIN.NTP_SID in (");
                }
                if (i % 1000 != 0) {
                    sql.addSql("  ,");
                }
                sql.addSql(String.valueOf(sid));
                i++;
            }
            sql.addSql("  ))");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addSql("  and");
            sql.addSql("    NTP_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  order by");
            sql.addSql("    NTP_BIN.BIN_SID");


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel retMdl = new CmnBinfModel();
                retMdl.setBinSid(rs.getLong("BIN_SID"));
                retMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                retMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                retMdl.setBinFileSizeDsp(strSize);
                int nipSid = rs.getInt("NTP_SID");
                List<CmnBinfModel> list;
                if (ret.containsKey(nipSid)) {
                    list = ret.get(nipSid);
                } else {
                    list = new ArrayList<>();
                    ret.put(nipSid, list);
                }
                list.add(retMdl);
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
     * <br>[機  能] 指定した日報情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSidList 日報SID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalNtpFileSize(List<Integer> nipSidList) throws SQLException {

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
            sql.addSql("   NTP_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   NTP_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   NTP_BIN.NTP_SID in (");

            for (int idx = 0; idx < nipSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(nipSidList.get(idx));
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