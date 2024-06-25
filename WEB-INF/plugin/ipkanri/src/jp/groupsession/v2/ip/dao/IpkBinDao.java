package jp.groupsession.v2.ip.dao;

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
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.model.IpkBinModel;

/**
 * <br>[機  能] ネットワーク添付ファイル情報に関するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkBinDao.class);

    /**
     * <p>Default Constructor
     */
    public IpkBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public IpkBinDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定されたIPアドレスの添付ファイル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param iadSid IPアドレスSID
     * @param tempDsp 添付ファイル公開・非公開フラグ
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List < IpkBinModel > getIpkanriTmpFileList(int netSid, int iadSid, int tempDsp)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List < IpkBinModel > ret = new ArrayList < IpkBinModel >();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IPK_BIN.INT_SID,");
            sql.addSql("   IPK_BIN.IAD_SID,");
            sql.addSql("   IPK_BIN.BIN_SID,");
            sql.addSql("   IPK_BIN.BIN_DSP");
            sql.addSql(" from");
            sql.addSql("   IPK_BIN");
            sql.addSql(" where");
            sql.addSql("   IPK_BIN.INT_SID = ?");
            sql.addIntValue(netSid);
            if (iadSid == IpkConst.NETWORK_TOUROKU) {
                sql.addSql(" and  IPK_BIN.IAD_SID = ?");
                sql.addIntValue(IpkConst.NETWORK_TOUROKU);
            } else {
                sql.addSql(" and  IPK_BIN.IAD_SID = ?");
                sql.addIntValue(iadSid);
            }
            if (tempDsp != IpkConst.IPK_TEMP_DSP) {
                sql.addSql(" and");
                sql.addSql("   IPK_BIN.BIN_DSP = ?");
                sql.addIntValue(tempDsp);
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                IpkBinModel binMdl = new IpkBinModel();
                binMdl.setNetSid(rs.getInt("INT_SID"));
                binMdl.setIadSid(rs.getInt("IAD_SID"));
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setTempDsp(rs.getInt("BIN_DSP"));
                ret.add(binMdl);
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
     * <br>[機  能] 指定したバイナリSID、ネットワークSIDのファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param binSid バイナリSID
     * @return 添付ファイル情報
     * @throws SQLException SQL実行例外
     */
    public IpkBinModel getNetworkTmpFile(int netSid, Long binSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        IpkBinModel binMdl = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IPK_BIN.INT_SID,");
            sql.addSql("   IPK_BIN.IAD_SID,");
            sql.addSql("   IPK_BIN.BIN_SID,");
            sql.addSql("   IPK_BIN.BIN_DSP");
            sql.addSql(" from");
            sql.addSql("   IPK_BIN");
            sql.addSql(" where");
            sql.addSql("   IPK_BIN.INT_SID = ?");
            sql.addIntValue(netSid);
            sql.addSql(" and");
            sql.addSql("   IPK_BIN.IAD_SID = ?");
            sql.addIntValue(IpkConst.NETWORK_TOUROKU);
            sql.addSql(" and");
            sql.addSql("   IPK_BIN.BIN_SID = ?");
            sql.addLongValue(binSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                binMdl = __getIpkBinFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return binMdl;
    }

    /**
     * <br>[機  能] 指定されたネットワークの添付ファイルかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param iadSid IPアドレスSID
     * @param tempDsp 添付ファイル公開・非公開フラグ
     * @param binSid バイナリSID
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckIpKanriTmpFile(int netSid, int iadSid, int tempDsp, Long binSid)
    throws SQLException {

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
            sql.addSql("   IPK_BIN");
            sql.addSql(" where");
            sql.addSql("   IPK_BIN.INT_SID = ?");
            sql.addIntValue(netSid);
            sql.addSql(" and");
            sql.addSql("   IPK_BIN.IAD_SID = ?");
            sql.addIntValue(iadSid);
            sql.addSql(" and");
            sql.addSql("   IPK_BIN.BIN_DSP = ?");
            sql.addIntValue(tempDsp);
            sql.addSql(" and");
            sql.addSql("   IPK_BIN.BIN_SID = ?");
            sql.addLongValue(binSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <br>[機  能] 添付ファイル情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param model バイナリデータ登録用モデル
     * @throws SQLException SQL実行例外
     */
    public void insert(IpkBinModel model) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" IPK_BIN(");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_DSP");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getNewNetSid());
            sql.addIntValue(model.getNewIadSid());
            sql.addLongValue(model.getBinSid());
            sql.addIntValue(model.getTempDsp());
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
     * <br>[機  能] バイナリー情報の論理削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param iadSid IPアドレスSID
     * @param usrSid 更新者SID
     * @param date 現在日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeIpkBinData(
            int netSid, int iadSid, int usrSid, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //バイナリー情報の論理削除
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(usrSid);
            sql.addDateValue(date);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from IPK_BIN");
            sql.addSql("     where ");
            sql.addSql("  INT_SID = ? ");
            sql.addIntValue(netSid);
            if (iadSid == -1) {
                sql.addSql(" and IAD_SID = ? )");
                sql.addIntValue(IpkConst.NETWORK_TOUROKU);
            } else {
                sql.addSql(" and  IAD_SID = ? )");
                sql.addIntValue(iadSid);
            }
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
     * <br>[機  能] バイナリー情報の論理削除を行う(ネットワーク削除時)
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param usrSid 更新者SID
     * @param date 現在日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeIpkBinData(
            int netSid, int usrSid, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //バイナリー情報の論理削除
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(usrSid);
            sql.addDateValue(date);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from IPK_BIN");
            sql.addSql("     where ");
            sql.addSql("  INT_SID = ? )");
            sql.addIntValue(netSid);

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
     * <br>[機  能] バイナリー情報の論理削除を行う(IPアドレス削除時)
     * <br>[解  説]
     * <br>[備  考]
     * @param iadSid IPアドレスSID
     * @param usrSid 更新者SID
     * @param date 現在日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeIadBinData(
            int iadSid, int usrSid, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //バイナリー情報の論理削除
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(usrSid);
            sql.addDateValue(date);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from IPK_BIN");
            sql.addSql("     where ");
            sql.addSql("  IAD_SID = ? )");
            sql.addIntValue(iadSid);

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
     * <br>[機  能] バイナリー情報の論理削除を行う(IPアドレス削除時)
     * <br>[解  説]
     * <br>[備  考]
     * @param deleteIadSids IPアドレスSIDリスト
     * @param usrSid 更新者SID
     * @param date 現在日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeIadBinDataList(ArrayList<Integer> deleteIadSids, int usrSid, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //バイナリー情報の論理削除
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(usrSid);
            sql.addDateValue(date);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from IPK_BIN");
            sql.addSql(" where ");
            for (int i = 0; i < deleteIadSids.size(); i++) {
                if (i == 0) {
                    sql.addSql("   IAD_SID in(" + deleteIadSids.get(i));
                } else if (i > 0) {
                    sql.addSql("," + deleteIadSids.get(i));
                }
            }
            sql.addSql("))");
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
     * <br>[機  能] ネットワークのIPアドレスのバイナリー情報の論理削除を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param usrSid 更新者SID
     * @param date 現在日時
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int removeBinData(int netSid, int usrSid, UDate date)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //バイナリー情報の論理削除
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?,");
            sql.addSql("   BIN_JKBN = ?");
            sql.addIntValue(usrSid);
            sql.addDateValue(date);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select BIN_SID from IPK_BIN");
            sql.addSql("     where ");
            sql.addSql("     INT_SID = ?");
            sql.addSql("     and ");
            sql.addSql("     IAD_SID > -1)");
            sql.addIntValue(netSid);
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
     * <br>[機  能] IPアドレス添付情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param iadSid IPアドレスSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int netSid, int iadSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   IPK_BIN");
            sql.addSql(" where ");
            sql.addSql("   INT_SID=?");
            sql.addIntValue(netSid);
            if (iadSid == -1) {
                sql.addSql(" and  IAD_SID=?");
                sql.addIntValue(IpkConst.NETWORK_TOUROKU);
            } else {
                sql.addSql(" and  IAD_SID=?");
                sql.addIntValue(iadSid);
            }

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
     * <br>[機  能] 1つのネットワークの添付情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteNetworkTemp(int netSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   IPK_BIN");
            sql.addSql(" where ");
            sql.addSql("   INT_SID=?");
            sql.addIntValue(netSid);

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
     * <br>[機  能] 1つのIPアドレスの添付情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param iadSid IPアドレスSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteIadTemp(int netSid, int iadSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   IPK_BIN");
            sql.addSql(" where ");
            sql.addSql("   INT_SID=?");
            sql.addSql(" and");
            sql.addSql("   IAD_SID=?");
            sql.addIntValue(netSid);
            sql.addIntValue(iadSid);

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
     * <br>[機  能] IPアドレスの添付情報の削除を行う(一括削除時）
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @param deleteIadSids IPアドレスSIDリスト
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteIadTempList(int netSid, ArrayList<Integer> deleteIadSids)
    throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   IPK_BIN");
            sql.addSql(" where ");
            for (int i = 0; i < deleteIadSids.size(); i++) {
                if (i == 0) {
                    sql.addSql("   IAD_SID in(" + deleteIadSids.get(i));
                } else if (i > 0) {
                    sql.addSql("," + deleteIadSids.get(i));
                }
            }
            sql.addSql(")");
            sql.addSql(" and");
            sql.addSql("   INT_SID=?");
            sql.addIntValue(netSid);

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
     * <br>[機  能] 1ネットワークのIPアドレスの添付情報の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int deleteIadTemp(int netSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   IPK_BIN");
            sql.addSql(" where ");
            sql.addSql("   INT_SID= ?");
            sql.addSql(" and");
            sql.addSql("   IAD_SID > -1");
            sql.addIntValue(netSid);

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
     * <p>Insert IPK_BIN Data Bindding JavaBean
     * @param bean IPK_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insertConvert(IpkBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" IPK_BIN(");
            sql.addSql("   BIN_SID,");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_SID,");
            sql.addSql("   BIN_DSP");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getNetSid());
            sql.addIntValue(bean.getIadSid());
            sql.addIntValue(bean.getTempDsp());
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
     * <p>Select IPK_BIN All Data
     * @return List in IPK_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<IpkBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkBinModel> ret = new ArrayList<IpkBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   BIN_SID,");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_SID,");
            sql.addSql("   BIN_DSP");
            sql.addSql(" from ");
            sql.addSql("   IPK_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkBinFromRs(rs));
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
     * <br>[機  能] 指定したネットワーク情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param intSidList ネットワークSID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalNetworkFileSize(List<Integer> intSidList) throws SQLException {

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
            sql.addSql("   IPK_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   IPK_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   IPK_BIN.INT_SID in (");

            for (int idx = 0; idx < intSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(intSidList.get(idx));
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
     * <p>Create IPK_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created IpkBinModel
     * @throws SQLException SQL実行例外
     */
    private IpkBinModel __getIpkBinFromRs(ResultSet rs) throws SQLException {
        IpkBinModel bean = new IpkBinModel();
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setNetSid(rs.getInt("INT_SID"));
        bean.setIadSid(rs.getInt("IAD_SID"));
        bean.setTempDsp(rs.getInt("BIN_DSP"));
        return bean;
    }
}