package jp.groupsession.v2.fil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.fil.model.FileModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>ファイル管理 Data Access Object
 *
 * @author JTS
 */
public class FileDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileDao.class);

    /**
     * <p>Default Constructor
     */
    public FileDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定したグループに所属するユーザの一覧を取得する
     * <br>[解  説] 削除されたユーザ、予約済みユーザは除く
     * <br>[備  考]
     * @param grpSid グループSID
     * @param sortMdl ソート情報
     * @return ユーザ一覧
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> getUserListBelongGroup(int grpSid, CmnCmbsortConfModel sortMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_BELONGM, ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_BELONGM.GRP_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID > ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   CMN_BELONGM.USR_SID = CMN_USRM_INF.USR_SID ");

            sql = __setOrderSQL(sql, sortMdl);

            sql.addIntValue(grpSid);
            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("USI_SEI")
                                    + " " + rs.getString("USI_MEI");
                LabelValueBean label = new LabelValueBean(userName,
                                                        String.valueOf(rs.getInt("USR_SID")));
                ret.add(label);
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
     * <br>[機  能] ユーザの一覧を取得する
     * <br>[解  説] 削除されたユーザ、予約済みユーザは除く
     * <br>[備  考]
     * @param sortMdl ソート情報
     * @return ユーザ一覧
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> getAllUserList(CmnCmbsortConfModel sortMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM_INF.USI_BDATE as USI_BDATE,");
            sql.addSql("   CMN_USRM_INF.USI_YAKUSYOKU as USI_YAKUSYOKU,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM, ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   CMN_USRM.USR_SID > ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_JKBN = ? ");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");

            sql = __setOrderSQL(sql, sortMdl);

            sql.addIntValue(GSConstUser.USER_RESERV_SID);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String userName = rs.getString("USI_SEI")
                                    + " " + rs.getString("USI_MEI");
                LabelValueBean label = new LabelValueBean(userName,
                                                        String.valueOf(rs.getInt("USR_SID")));
                ret.add(label);
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
     * <br>[機  能] フォルダ詳細情報を取得する(最新バージョン)
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @param usrSid ユーザSID
     * @return FileModel　フォルダ詳細情報モデル
     * @throws SQLException SQL実行例外
     */
    public FileModel getFolderInf(int fdrSid, int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   DIRECTORY.FDR_SID, ");
            sql.addSql("   DIRECTORY.FDR_VERSION, ");
            sql.addSql("   DIRECTORY.FCB_SID, ");
            sql.addSql("   DIRECTORY.FDR_PARENT_SID, ");
            sql.addSql("   DIRECTORY.FDR_KBN, ");
            sql.addSql("   DIRECTORY.FDR_VER_KBN, ");
            sql.addSql("   DIRECTORY.FDR_LEVEL, ");
            sql.addSql("   DIRECTORY.FDR_NAME, ");
            sql.addSql("   DIRECTORY.FDR_BIKO, ");
            sql.addSql("   DIRECTORY.FDR_JTKBN, ");
            sql.addSql("   DIRECTORY.BIN_SID, ");
            sql.addSql("   CALL_CONF.CALL_CNT as CALL_COUNT, ");
            sql.addSql("   SHORTCUT_CONF.SC_CNT as SHORTCUT_COUNT ");
            sql.addSql(" from ");
            sql.addSql("   (");
            sql.addSql("   select");
            sql.addSql("     FILE_DIRECTORY.FDR_SID, ");
            sql.addSql("     FILE_DIRECTORY.FDR_VERSION, ");
            sql.addSql("     FILE_DIRECTORY.FCB_SID, ");
            sql.addSql("     FILE_DIRECTORY.FDR_PARENT_SID, ");
            sql.addSql("     FILE_DIRECTORY.FDR_KBN, ");
            sql.addSql("     FILE_DIRECTORY.FDR_VER_KBN, ");
            sql.addSql("     FILE_DIRECTORY.FDR_LEVEL, ");
            sql.addSql("     FILE_DIRECTORY.FDR_NAME, ");
            sql.addSql("     FILE_DIRECTORY.FDR_BIKO, ");
            sql.addSql("     FILE_DIRECTORY.FDR_JTKBN, ");
            sql.addSql("     FILE_DIRECTORY_BIN.BIN_SID");
            sql.addSql("   from");
            sql.addSql("    (FILE_DIRECTORY left join FILE_DIRECTORY_BIN ");
            sql.addSql("     on FILE_DIRECTORY.FDR_SID = FILE_DIRECTORY_BIN.FDR_SID ");
            sql.addSql("     and FILE_DIRECTORY.FDR_VERSION = FILE_DIRECTORY_BIN.FDR_VERSION)");
            sql.addSql("  ) as DIRECTORY, ");

            sql.addSql(" (select FDR_SID, max(FDR_VERSION) as MAXVERSION ");
            sql.addSql("   from FILE_DIRECTORY group by FDR_SID) as DIR_MAXVERSION, ");

            sql.addSql(" (select COUNT(*) as CALL_CNT ");
            sql.addSql("   from FILE_CALL_CONF, FILE_DIRECTORY ");
            sql.addSql("   where  ");
            sql.addSql("     FILE_DIRECTORY.FDR_SID = FILE_CALL_CONF.FDR_SID ");
            sql.addSql("   and ");
            sql.addSql("     FILE_CALL_CONF.FDR_SID = ?");
            sql.addSql("   and ");
            sql.addSql("     FILE_CALL_CONF.USR_SID = ?) as CALL_CONF, ");

            sql.addSql(" (select COUNT(*) as SC_CNT from FILE_SHORTCUT_CONF, FILE_DIRECTORY ");
            sql.addSql("   where  ");
            sql.addSql("     FILE_DIRECTORY.FDR_SID = FILE_SHORTCUT_CONF.FDR_SID ");
            sql.addSql("   and ");
            sql.addSql("     FILE_SHORTCUT_CONF.FDR_SID = ?");
            sql.addSql("   and ");
            sql.addSql("    FILE_SHORTCUT_CONF.USR_SID = ?) as SHORTCUT_CONF ");

            sql.addSql(" where ");
            sql.addSql("   DIRECTORY.FDR_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID ");
            sql.addSql(" and ");
            sql.addSql("   DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION ");

            sql.addIntValue(fdrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(fdrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(fdrSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = new FileModel();
                ret.setFdrSid(rs.getInt("FDR_SID"));
                ret.setFdrVersion(rs.getInt("FDR_VERSION"));
                ret.setFcbSid(rs.getInt("FCB_SID"));
                ret.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
                ret.setFdrKbn(rs.getInt("FDR_KBN"));
                ret.setFdrVerKbn(rs.getInt("FDR_VER_KBN"));
                ret.setFdrLevel(rs.getInt("FDR_LEVEL"));
                ret.setFdrName(rs.getString("FDR_NAME"));
                ret.setFdrBiko(rs.getString("FDR_BIKO"));
                ret.setFdrJtkbn(rs.getInt("FDR_JTKBN"));
                ret.setBinSid(rs.getLong("BIN_SID"));
                ret.setCallCount(rs.getInt("CALL_COUNT"));
                ret.setShortcutCount(rs.getInt("SHORTCUT_COUNT"));

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
     * <br>[機  能] ファイル詳細情報を取得する(最新バージョン)
     * <br>[解  説]
     * <br>[備  考]
     * @param fdrSid ディレクトリSID
     * @param usrSid ユーザSID
     * @param getDelFlg 論理削除ファイルの取得フラグ
     * @return FileModel　フォルダ詳細情報モデル
     * @throws SQLException SQL実行例外
     */
    public FileModel getFileInf(int fdrSid, int usrSid, boolean getDelFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   DIRECTORY.FDR_SID, ");
            sql.addSql("   DIRECTORY.FDR_VERSION, ");
            sql.addSql("   DIRECTORY.FCB_SID, ");
            sql.addSql("   DIRECTORY.FDR_PARENT_SID, ");
            sql.addSql("   DIRECTORY.FDR_KBN, ");
            sql.addSql("   DIRECTORY.FDR_VER_KBN, ");
            sql.addSql("   DIRECTORY.FDR_LEVEL, ");
            sql.addSql("   DIRECTORY.FDR_NAME, ");
            sql.addSql("   DIRECTORY.FDR_BIKO, ");
            sql.addSql("   DIRECTORY.FDR_JTKBN, ");
            sql.addSql("   DIRECTORY.FDR_EUID, ");
            sql.addSql("   DIRECTORY.FDR_EGID, ");
            sql.addSql("   DIRECTORY.FDR_SID, ");
            sql.addSql("   DIRECTORY.FDR_VERSION, ");
            sql.addSql("   DIRECTORY.FDR_TRADE_DATE,");
            sql.addSql("   DIRECTORY.FDR_TRADE_TARGET,");
            sql.addSql("   DIRECTORY.FDR_TRADE_MONEYKBN,");
            sql.addSql("   DIRECTORY.FDR_TRADE_MONEY,");
            sql.addSql("   DIRECTORY.EMT_SID,");
            sql.addSql("   DIRECTORY.BIN_SID, ");
            sql.addSql("   DIRECTORY.FFL_EXT, ");
            sql.addSql("   DIRECTORY.FFL_FILE_SIZE, ");
            sql.addSql("   DIRECTORY.FFL_LOCK_KBN, ");
            sql.addSql("   DIRECTORY.FFL_LOCK_USER, ");
            sql.addSql("   CALL_CONF.CALL_CNT as CALL_COUNT, ");
            sql.addSql("   SHORTCUT_CONF.SC_CNT as SHORTCUT_COUNT ");
            sql.addSql(" from ");
            sql.addSql("   (");
            sql.addSql("   select");
            sql.addSql("     FILE_DIRECTORY.FDR_SID, ");
            sql.addSql("     FILE_DIRECTORY.FDR_VERSION, ");
            sql.addSql("     FILE_DIRECTORY.FCB_SID, ");
            sql.addSql("     FILE_DIRECTORY.FDR_PARENT_SID, ");
            sql.addSql("     FILE_DIRECTORY.FDR_KBN, ");
            sql.addSql("     FILE_DIRECTORY.FDR_VER_KBN, ");
            sql.addSql("     FILE_DIRECTORY.FDR_LEVEL, ");
            sql.addSql("     FILE_DIRECTORY.FDR_NAME, ");
            sql.addSql("     FILE_DIRECTORY.FDR_BIKO, ");
            sql.addSql("     FILE_DIRECTORY.FDR_JTKBN, ");
            sql.addSql("     FILE_DIRECTORY.FDR_EUID, ");
            sql.addSql("     FILE_DIRECTORY.FDR_EGID, ");
            sql.addSql("     FILE_DIRECTORY.FDR_TRADE_DATE, ");
            sql.addSql("     FILE_DIRECTORY.FDR_TRADE_TARGET, ");
            sql.addSql("     FILE_DIRECTORY.FDR_TRADE_MONEYKBN, ");
            sql.addSql("     FILE_DIRECTORY.FDR_TRADE_MONEY, ");
            sql.addSql("     FILE_DIRECTORY.EMT_SID, ");
            sql.addSql("     FILE_FILE_BIN.BIN_SID, ");
            sql.addSql("     FILE_FILE_BIN.FFL_EXT, ");
            sql.addSql("     FILE_FILE_BIN.FFL_FILE_SIZE, ");
            sql.addSql("     FILE_FILE_BIN.FFL_LOCK_KBN, ");
            sql.addSql("     FILE_FILE_BIN.FFL_LOCK_USER");

            sql.addSql("   from");
            sql.addSql("     (FILE_DIRECTORY left join FILE_FILE_BIN ");
            sql.addSql("     on FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID ");
            sql.addSql("     and FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION)");
            sql.addSql("   ) as DIRECTORY, ");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION ");
            sql.addSql("     from FILE_DIRECTORY group by FDR_SID) as DIR_MAXVERSION, ");
            sql.addSql("   (select COUNT(*) as CALL_CNT ");
            sql.addSql("     from FILE_CALL_CONF, FILE_DIRECTORY ");
            sql.addSql("   where  ");
            sql.addSql("     FILE_DIRECTORY.FDR_SID = FILE_CALL_CONF.FDR_SID ");
            sql.addSql("   and ");
            sql.addSql("     FILE_CALL_CONF.FDR_SID = ?");
            sql.addSql("   and ");
            sql.addSql("     FILE_CALL_CONF.USR_SID = ?) as CALL_CONF, ");
            sql.addSql("   (select COUNT(*) as SC_CNT from FILE_SHORTCUT_CONF, FILE_DIRECTORY ");
            sql.addSql("   where  ");
            sql.addSql("     FILE_DIRECTORY.FDR_SID = FILE_SHORTCUT_CONF.FDR_SID ");
            sql.addSql("   and ");
            sql.addSql("     FILE_SHORTCUT_CONF.FDR_SID = ?");
            sql.addSql("   and ");
            sql.addSql("     FILE_SHORTCUT_CONF.USR_SID = ?) as SHORTCUT_CONF ");
            sql.addSql(" where ");
            sql.addSql("   DIRECTORY.FDR_SID = ? ");
            sql.addIntValue(fdrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(fdrSid);
            sql.addIntValue(usrSid);
            sql.addIntValue(fdrSid);
            if (!getDelFlg) {
                sql.addSql(" and ");
                sql.addSql("   DIRECTORY.FDR_JTKBN = ? ");
                sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            }
            sql.addSql(" and ");
            sql.addSql("   DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID ");
            sql.addSql(" and ");
            sql.addSql("   DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION ");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret = new FileModel();
                ret.setFdrSid(rs.getInt("FDR_SID"));
                ret.setFdrVersion(rs.getInt("FDR_VERSION"));
                ret.setFcbSid(rs.getInt("FCB_SID"));
                ret.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
                ret.setFdrKbn(rs.getInt("FDR_KBN"));
                ret.setFdrVerKbn(rs.getInt("FDR_VER_KBN"));
                ret.setFdrLevel(rs.getInt("FDR_LEVEL"));
                ret.setFdrName(rs.getString("FDR_NAME"));
                ret.setFdrBiko(rs.getString("FDR_BIKO"));
                ret.setFdrJtkbn(rs.getInt("FDR_JTKBN"));
                ret.setFdrEuid(rs.getInt("FDR_EUID"));
                ret.setFdrEgid(rs.getInt("FDR_EGID"));
                ret.setFdrTradeDate(UDate.getInstanceTimestamp(
                        rs.getTimestamp("FDR_TRADE_DATE")));
                ret.setFdrTradeTarget(rs.getString("FDR_TRADE_TARGET"));
                ret.setFdrTradeMoneyKbn(rs.getInt("FDR_TRADE_MONEYKBN"));
                ret.setFdrTradeMoney(rs.getBigDecimal("FDR_TRADE_MONEY"));
                ret.setEmtSid(rs.getInt("EMT_SID"));

                ret.setBinSid(rs.getLong("BIN_SID"));
                ret.setFflExt(rs.getString("FFL_EXT"));
                ret.setFflFileSize(rs.getInt("FFL_FILE_SIZE"));
                ret.setFflLockKbn(rs.getInt("FFL_LOCK_KBN"));
                ret.setFflLockUser(rs.getInt("FFL_LOCK_USER"));
                ret.setCallCount(rs.getInt("CALL_COUNT"));
                ret.setShortcutCount(rs.getInt("SHORTCUT_COUNT"));
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
//     * <br>[機  能] ファイル詳細情報を取得する(最新バージョン)
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param fcbSid キャビネットSID
//     * @param fdrSid ディレクトリSID
//     * @return FileModel　フォルダ詳細情報モデル
//     * @throws SQLException SQL実行例外
//     */
//    public FileModel getFileInf(int fcbSid, int fdrSid) throws SQLException {
//
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//        Connection con = null;
//        FileModel ret = null;
//        con = getCon();
//
//        try {
//            //SQL文
//            SqlBuffer sql = new SqlBuffer();
//
//            sql.addSql(" select ");
//            sql.addSql("   FILE_DIRECTORY.FDR_SID, ");
//            sql.addSql("   FILE_DIRECTORY.FDR_VERSION, ");
//            sql.addSql("   FILE_DIRECTORY.FCB_SID, ");
//            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID, ");
//            sql.addSql("   FILE_DIRECTORY.FDR_KBN, ");
//            sql.addSql("   FILE_DIRECTORY.FDR_VER_KBN, ");
//            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL, ");
//            sql.addSql("   FILE_DIRECTORY.FDR_NAME, ");
//            sql.addSql("   FILE_DIRECTORY.FDR_BIKO, ");
//            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN, ");
//            sql.addSql("   FILE_FILE_BIN.FDR_SID, ");
//            sql.addSql("   FILE_FILE_BIN.FDR_VERSION, ");
//            sql.addSql("   FILE_FILE_BIN.BIN_SID, ");
//            sql.addSql("   FILE_FILE_BIN.FFL_EXT, ");
//            sql.addSql("   FILE_FILE_BIN.FFL_FILE_SIZE, ");
//            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_KBN, ");
//            sql.addSql("   FILE_FILE_BIN.FFL_LOCK_USER ");
//
//            sql.addSql(" from ");
//            sql.addSql("   FILE_DIRECTORY, ");
//            sql.addSql("   FILE_FILE_BIN, ");
//            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION ");
//            sql.addSql("     from FILE_DIRECTORY group by FDR_SID) as DIR_MAXVERSION     ");
//
//            sql.addSql(" where ");
//            sql.addSql("   FILE_DIRECTORY.FCB_SID = ? ");
//            sql.addSql(" and ");
//            sql.addSql("   FILE_DIRECTORY.FDR_SID = ? ");
//            sql.addSql(" and ");
//            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ? ");
//            sql.addSql(" and ");
//            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID ");
//            sql.addSql(" and ");
//            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION ");
//            sql.addSql(" and ");
//            sql.addSql("   FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID ");
//            sql.addSql(" and ");
//            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION ");
//
//            sql.addValue(fcbSid);
//            sql.addValue(fdrSid);
//            sql.addValue(GSConstFile.JTKBN_NORMAL);
//
//            log__.info(sql.toLogString());
//
//            pstmt = con.prepareStatement(sql.toSqlString());
//            sql.setParameter(pstmt);
//            rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                ret = new FileModel();
//                ret.setFdrSid(rs.getInt("FDR_SID"));
//                ret.setFdrVersion(rs.getInt("FDR_VERSION"));
//                ret.setFcbSid(rs.getInt("FCB_SID"));
//                ret.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
//                ret.setFdrKbn(rs.getInt("FDR_KBN"));
//                ret.setFdrVerKbn(rs.getInt("FDR_VER_KBN"));
//                ret.setFdrLevel(rs.getInt("FDR_LEVEL"));
//                ret.setFdrName(rs.getString("FDR_NAME"));
//                ret.setFdrBiko(rs.getString("FDR_BIKO"));
//                ret.setFdrJtkbn(rs.getInt("FDR_JTKBN"));
//
//                ret.setFdrVersion(rs.getInt("FDR_VERSION"));
//                ret.setBinSid(rs.getInt("BIN_SID"));
//                ret.setFflExt(rs.getString("FFL_EXT"));
//                ret.setFflFileSize(rs.getInt("FFL_FILE_SIZE"));
//                ret.setFflLockKbn(rs.getInt("FFL_LOCK_KBN"));
//                ret.setFflLockUser(rs.getInt("FFL_LOCK_USER"));
//            }
//        } catch (SQLException e) {
//            throw e;
//        } finally {
//            JDBCUtil.closeResultSet(rs);
//            JDBCUtil.closeStatement(pstmt);
//        }
//        return ret;
//    }

    /**
     * <br>[機  能] SqlBufferにorder句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sortMdl ソート情報
     * @return SqlBuffer
     */
    private SqlBuffer __setOrderSQL(SqlBuffer sql, CmnCmbsortConfModel sortMdl) {

        sql.addSql(" order by ");

        String order = "asc";
        if (sortMdl.getCscUserOrder1() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey1()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   USI_SORTKEY2 " + order);
                break;
            default:
                sql.addSql("   YAKUSYOKU_EXIST asc,");
                sql.addSql("   YAKUSYOKU_SORT asc,");
                sql.addSql("   USI_SEI_KN asc,");
                sql.addSql("   USI_MEI_KN asc");
                break;
        }

        order = "asc";
        if (sortMdl.getCscUserOrder2() == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortMdl.getCscUserSkey2()) {
            case GSConst.USERCMB_SKEY_NAME:
                sql.addSql("   ,USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order);
                break;
            case GSConst.USERCMB_SKEY_SNO:
                sql.addSql("   ,case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order);
                break;
            case GSConst.USERCMB_SKEY_POSITION:
                sql.addSql("   ,YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order);
                break;
            case GSConst.USERCMB_SKEY_BDATE:
                sql.addSql("   ,USI_BDATE " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY1:
                sql.addSql("   ,USI_SORTKEY1 " + order);
                break;
            case GSConst.USERCMB_SKEY_SORTKEY2:
                sql.addSql("   ,USI_SORTKEY2 " + order);
                break;
            default:
                break;
        }

        return sql;
    }

    /**
     * <br>[機  能] ファイルが属するキャビネットSIDを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param binSid バイナリSID
     * @return fcbSid キャビネットSID
     * @throws SQLException SQL実行例外
     */
    public int getCabinetSid(Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   FILE_DIRECTORY.FCB_SID ");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY, ");
            sql.addSql("   (select FDR_SID, BIN_SID, max(FDR_VERSION) as MAXVERSION ");
            sql.addSql("    from FILE_FILE_BIN group by FDR_SID, BIN_SID) as DIR_MAXVERSION ");
            sql.addSql(" where ");
            sql.addSql("   DIR_MAXVERSION.BIN_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID ");
            sql.addSql(" and ");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION ");

            sql.addLongValue(binSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("FCB_SID");
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
     * <br>[機  能] 指定したユーザSIDのユーザ名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSidList ユーザSID一覧
     * @return ユーザ名一覧
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, String> getUserNameMap(List<Integer> usrSidList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<Integer, String> ret = new HashMap<Integer, String>();
        con = getCon();

        if (usrSidList == null || usrSidList.size() < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID, ");
            sql.addSql("   USI_SEI, ");
            sql.addSql("   USI_MEI ");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");

            int orSize   = 200;
            int listSize = usrSidList.size();
            for (int i = 0; i < listSize; i += orSize) {
                StringBuffer strBuf = new StringBuffer();
                if (i > 0) {
                    strBuf.append("   or");
                }
                strBuf.append(" USR_SID in (");

                int max = (orSize < listSize - i ? orSize : listSize - i);
                for (int j = 0; j < max; j++) {
                    if (j > 0) {
                        strBuf.append(",");
                    }
                    strBuf.append(usrSidList.get(i + j));
                }
                strBuf.append(")");
                sql.addSql(strBuf.toString());
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int userSid = rs.getInt("USR_SID");
                if (userSid > GSConst.SYSTEM_USER_MAIL) { // システムユーザは除外
                    String userName = rs.getString("USI_SEI")
                                        + " " + rs.getString("USI_MEI");
                    ret.put(Integer.valueOf(userSid), userName);
                }
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
     * ループSQL作成
     * @param sql SQL
     * @param clumName カラム名
     * @param list リスト
     * @throws SQLException SQL実行例外
     */
    public static void getLoopListSql(SqlBuffer sql, String clumName, List<Integer> list)
            throws SQLException {
        int blockSize = 200;
        int listCnt   = list.size();
        sql.addSql(" (");
        for (int i = 0; i < listCnt; i += blockSize) {
            if (i > 0) {
                sql.addSql(" or");
            }
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("   " + clumName + " in (");

            int maxCnt = (i + blockSize > listCnt ? listCnt - i : blockSize);
            for (int j = 0; j < maxCnt; j++) {
                if (j > 0) {
                    strBuf.append(",");
                }
                strBuf.append(list.get(i + j));
            }
            strBuf.append(")");
            sql.addSql(strBuf.toString());
        }
        sql.addSql(" )");
    }

    /**
     * <br>[機  能] 指定したディレクトリを持つキャビネットにて個人/共有キャビネット、電帳法キャビネットが混在しているか判定
     * <br>[解  説]
     * <br>[備  考]
     * @param dirSids ディレクトリSIDリスト
     * @return 判定結果 true=混在している false=混在していない
     * @throws SQLException SQL実行例外
     */
    public boolean isNotSameCabinetType(ArrayList<Integer> dirSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        if (dirSids == null
                || dirSids.size() == 0) {
            return true;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   FILE_CABINET.FCB_ERRL ");
            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY ");
            sql.addSql("   left join FILE_CABINET ");
            sql.addSql("   on FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID ");
            sql.addSql(" where ");
            sql.addSql("   FILE_DIRECTORY.FDR_SID in (");
            for (int i = 0; i < dirSids.size(); i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql("     ? ");
                sql.addLongValue(dirSids.get(i));
            }
            sql.addSql("   ) ");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            boolean errlCabinet = false;
            boolean noErrlCabinet = false;
            while (rs.next()) {
                if (rs.getInt("FCB_ERRL") == 0) {
                    noErrlCabinet = true;
                } else if (rs.getInt("FCB_ERRL") == 1) {
                    errlCabinet = true;
                }
            }
            if (errlCabinet && noErrlCabinet) {
                ret = true;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }
}