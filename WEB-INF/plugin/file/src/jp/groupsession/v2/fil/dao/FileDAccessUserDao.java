package jp.groupsession.v2.fil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.model.FileDAccessUserModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>ディレクトリアクセス設定ユーザDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileDAccessUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileDAccessUserDao.class);

    /**
     * <p>Default Constructor
     */
    public FileDAccessUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileDAccessUserDao(Connection con) {
        super(con);
    }

    /**
     * <p>キャビネット詳細で使用する、アクセス制御情報を取得する。
     * @param fcbMdl キャビネットデータ
     * @param fdrSid ディレクトリSID
     * @param sortKey ソート項目
     * @param orderKey ソートオーダー
     * @param start 取得開始位置
     * @param limit 取得件数(上限値)
     * @param accessUserSids アクセス可能ユーザSID
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileDAccessUserModel> getAccessList(FileCabinetModel fcbMdl,
            int fdrSid, int sortKey, int orderKey, int start, int limit,
            List<Integer> accessUserSids) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileDAccessUserModel> ret = new ArrayList<FileDAccessUserModel>();
        con = getCon();

        if (fcbMdl == null) {
            return ret;
        }

        try {
            //キャビネットSIDからアクセス制御一覧取得
            SqlBuffer sql = __getUserAccessSql(fcbMdl, fdrSid, sortKey, orderKey, false,
                                                accessUserSids);
            sql.addSql(" limit " + limit + " offset " + (start - 1));
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getFileAccessUserModelFromRs(rs));
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
     * <p>キャビネット詳細で使用する、アクセス制御情報の件数を取得する。
     * @param fcbMdl キャビネットデータ
     * @param fdrSid ディレクトリSID
     * @param accessUserSids アクセス可能ユーザSID
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public int getAccessListCount(FileCabinetModel fcbMdl, int fdrSid,
            List<Integer> accessUserSids)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        if (fcbMdl == null) {
            return ret;
        }
        try {
            SqlBuffer sql = __getUserAccessSql(fcbMdl, fdrSid, 0, 0, true, accessUserSids);
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
     * <p>ディレクトリ、ファイルに対するアクセス権限制御用一覧を取得するSQLを取得する。
     * @param fcbMdl キャビネットデータ
     * @param fdrSid ディレクトリSID
     * @param sortKey ソート項目
     * @param orderKey ソートオーダー
     * @param countFlg カウント用フラグ
     * @param accessUserSids アクセス可能ユーザSID
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __getUserAccessSql(FileCabinetModel fcbMdl, int fdrSid,
            int sortKey, int orderKey, boolean countFlg,
            List<Integer> accessUserSids) throws SQLException {

        int personalFlg = fcbMdl.getFcbPersonalFlg();

        //SQL文
        SqlBuffer sql = new SqlBuffer();
        if (countFlg) {
            sql.addSql(" select count(*) CNT from (");
        } else {
            sql.addSql(" select * from (");
        }
        sql.addSql(" select");
        sql.addSql("   USRM.USR_UKO_FLG,");
        sql.addSql("   CMN_USRM_INF.USR_SID,");
        sql.addSql("   CMN_USRM_INF.USI_SEI,");
        sql.addSql("   CMN_USRM_INF.USI_MEI,");
        sql.addSql("   CMN_USRM_INF.USI_SEI_KN,");
        sql.addSql("   CMN_USRM_INF.USI_MEI_KN,");
        /*
        sql.addSql("   CMN_USRM_INF.USI_SINI,");
        sql.addSql("   CMN_USRM_INF.USI_BDATE,");
        sql.addSql("   CMN_USRM_INF.USI_ZIP1,");
        sql.addSql("   CMN_USRM_INF.USI_ZIP2,");
        sql.addSql("   CMN_USRM_INF.TDF_SID,");
        sql.addSql("   CMN_USRM_INF.USI_ADDR1,");
        sql.addSql("   CMN_USRM_INF.USI_ADDR2,");
        sql.addSql("   CMN_USRM_INF.USI_TEL1,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_NAI1,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_CMT1,");
        sql.addSql("   CMN_USRM_INF.USI_TEL2,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_NAI2,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_CMT2,");
        sql.addSql("   CMN_USRM_INF.USI_TEL3,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_NAI3,");
        sql.addSql("   CMN_USRM_INF.USI_TEL_CMT3,");
        sql.addSql("   CMN_USRM_INF.USI_FAX1,");
        sql.addSql("   CMN_USRM_INF.USI_FAX_CMT1,");
        sql.addSql("   CMN_USRM_INF.USI_FAX2,");
        sql.addSql("   CMN_USRM_INF.USI_FAX_CMT2,");
        sql.addSql("   CMN_USRM_INF.USI_FAX3,");
        sql.addSql("   CMN_USRM_INF.USI_FAX_CMT3,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL1,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT1,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL2,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT2,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL3,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL_CMT3,");
        sql.addSql("   CMN_USRM_INF.USI_SYOZOKU,");
        */
        sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
        sql.addSql("   (case");
        sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
        sql.addSql("      else (select");
        sql.addSql("            POS_NAME");
        sql.addSql("          from");
        sql.addSql("            CMN_POSITION");
        sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
        sql.addSql("    end) as USI_YAKUSYOKU,");
        /*
        sql.addSql("   CMN_USRM_INF.USI_BIKO,");
        sql.addSql("   CMN_USRM_INF.BIN_SID,");
        sql.addSql("   CMN_USRM_INF.USI_PICT_KF,");
        sql.addSql("   CMN_USRM_INF.USI_BDATE_KF,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL1_KF,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL2_KF,");
        sql.addSql("   CMN_USRM_INF.USI_MAIL3_KF,");
        sql.addSql("   CMN_USRM_INF.USI_ZIP_KF,");
        sql.addSql("   CMN_USRM_INF.USI_TDF_KF,");
        sql.addSql("   CMN_USRM_INF.USI_ADDR1_KF,");
        sql.addSql("   CMN_USRM_INF.USI_ADDR2_KF,");
        sql.addSql("   CMN_USRM_INF.USI_TEL1_KF,");
        sql.addSql("   CMN_USRM_INF.USI_TEL2_KF,");
        sql.addSql("   CMN_USRM_INF.USI_TEL3_KF,");
        sql.addSql("   CMN_USRM_INF.USI_FAX1_KF,");
        sql.addSql("   CMN_USRM_INF.USI_FAX2_KF,");
        sql.addSql("   CMN_USRM_INF.USI_FAX3_KF,");
        sql.addSql("   CMN_USRM_INF.USI_LTLGIN,");
        sql.addSql("   CMN_USRM_INF.USI_AUID,");
        sql.addSql("   CMN_USRM_INF.USI_ADATE,");
        sql.addSql("   CMN_USRM_INF.USI_EUID,");
        sql.addSql("   CMN_USRM_INF.USI_EDATE,");
        */
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
        sql.addSql("    end) as YAKUSYOKU_SORT,");

        if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.USR_SID = ? then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as ACKBN");
            sql.addIntValue(fcbMdl.getFcbOwnerSid());
        } else {
            sql.addSql("   coalesce(DACCESS.FDA_AUTH, 1) as ACKBN");
        }

        sql.addSql(" from");
        sql.addSql("   CMN_USRM USRM");
        sql.addSql(" inner join");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql("   on USRM.USR_SID=CMN_USRM_INF.USR_SID");

        //ディレクトリのアクセス設定で参照ファイルを制限する
        sql.addSql(" inner join");
        sql.addSql("   FILE_DIRECTORY DIR");
        sql.addSql("   on DIR.FDR_SID = ?");
        sql.addSql(" inner join");
        sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
        sql.addSql("    from FILE_DIRECTORY");
        sql.addSql("    where FDR_SID = ? group by FDR_SID) DIR_MAXVERSION");
        sql.addSql("   on DIR.FDR_SID = DIR_MAXVERSION.FDR_SID");
        sql.addSql("   and DIR.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
        sql.addIntValue(fdrSid);
        sql.addIntValue(fdrSid);

        sql.addSql(" left join");
        sql.addSql("   ( ");
        sql.addSql("   select");
        sql.addSql("     A.FDR_SID,");
        sql.addSql("     A.USR_SID,");
        sql.addSql("     max(A.FDA_AUTH) FDA_AUTH");
        sql.addSql("   from (");
        sql.addSql("   select");
        sql.addSql("     A.FDR_SID,");
        sql.addSql("     case when A.USR_KBN = ?");
        sql.addSql("          then A.USR_SID");
        sql.addSql("          else B.USR_SID");
        sql.addSql("          end as USR_SID,");
        sql.addSql("     A.FDA_AUTH");
        sql.addSql("   from");
        sql.addSql("     FILE_DIRECTORY D,");
        sql.addSql("     FILE_DACCESS_CONF A");
        sql.addSql("   left join");
        sql.addSql("     CMN_BELONGM B on B.GRP_SID = A.USR_SID");
        sql.addSql("   where");
        if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
            sql.addSql("     D.FDR_SID = A.FDR_SID");
        } else {
            sql.addSql("     D.FDR_ACCESS_SID = A.FDR_SID");
        }
        sql.addSql("   and");
        sql.addSql("     D.FDR_SID = ?");
        sql.addSql("   ) A");
        sql.addSql("   group by");
        sql.addSql("     A.FDR_SID, A.USR_SID");
        sql.addSql("   ) DACCESS");
        sql.addSql("   on DIR.FDR_ACCESS_SID = DACCESS.FDR_SID");
        sql.addSql("   and USRM.USR_SID = DACCESS.USR_SID");
        sql.addIntValue(GSConstFile.USER_KBN_USER);
        sql.addIntValue(fdrSid);

        sql.addSql(" where");
        sql.addSql("   USRM.USR_JKBN<>?");
        sql.addIntValue(GSConstUser.USER_JTKBN_DELETE);
        //ユーザSID < 100は除外
        sql.addSql(" and");
        sql.addSql("   USRM.USR_SID>?");
        sql.addIntValue(GSConstUser.USER_RESERV_SID);

        sql.addSql(" and (");
        sql.addSql("   DACCESS.FDR_SID is not null");
        if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
            sql.addSql(" or");
            sql.addSql("   CMN_USRM_INF.USR_SID = ?");
            sql.addIntValue(fcbMdl.getFcbOwnerSid());
            if (accessUserSids != null && accessUserSids.size() > 0) {
                sql.addSql(" or");
                FileDao.getLoopListSql(sql, "USRM.USR_SID", accessUserSids);
            }
        } else {
            sql.addSql(" or");
            sql.addSql("   DIR.FDR_ACCESS_SID = ?");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
        }
        sql.addSql(" )");
        sql.addSql(") ACCESS");

        String orderStr = "";
        //オーダー
        if (orderKey == GSConst.ORDER_KEY_ASC) {
            orderStr = "  asc";
        } else {
            orderStr = "  desc";
        }

        if (!countFlg) {
            log__.info("sortkey = " + sortKey);
            //ソートカラム
            switch (sortKey) {
                //氏名
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql(" order by");
                    sql.addSql("   ACCESS.USI_SEI_KN");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   ACCESS.USI_MEI_KN");
                    sql.addSql(orderStr);
                    break;
                //社員/職員番号
                case GSConstUser.USER_SORT_SNO:
                    sql.addSql(" order by");
                    sql.addSql("   case when ACCESS.USI_SYAIN_NO is null then ''");
                    sql.addSql("   else ACCESS.USI_SYAIN_NO end ");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   ACCESS.USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   ACCESS.USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                //役職
                case GSConstUser.USER_SORT_YKSK:
                    sql.addSql(" order by");
                    sql.addSql("   ACCESS.YAKUSYOKU_EXIST");
                    sql.addSql(orderStr);
                    sql.addSql("  ,");
                    sql.addSql("   ACCESS.YAKUSYOKU_SORT");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   ACCESS.USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   ACCESS.USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                //キャビネット管理者
                case GSConstFile.USER_SORT_ADMIN:
                    sql.addSql(" order by");
                    sql.addSql("   ACCESS.ADMKBN");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   ACCESS.USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   ACCESS.USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                    //キャビネットアクセス
                case GSConstFile.USER_SORT_ACCESS:
                    sql.addSql(" order by");
                    sql.addSql("   ACCESS.ACKBN");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   ACCESS.USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   ACCESS.USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                default:
                    break;
            }
        }

        return sql;
    }

    /**
     * <p>キャビネット詳細で使用する、アクセス制御情報の件数を取得する。
     * @param dirSidList ディレクトリSID一覧
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getAccessUserSidList(List<Integer> dirSidList) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        if (dirSidList == null || dirSidList.size() == 0) {
            return ret;
        }

        try {
            //キャビネットSIDからアクセス制御一覧取得
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   (case");
            sql.addSql("     when FILE_DACCESS_CONF.USR_KBN = ? then FILE_DACCESS_CONF.USR_SID");
            sql.addSql("     when FILE_DACCESS_CONF.USR_KBN = ? then CMN_BELONGM.USR_SID");
            sql.addSql("     else -1");
            sql.addSql("    end) as USID");
            sql.addSql(" from ");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" left join");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" on CMN_BELONGM.GRP_SID = FILE_DACCESS_CONF.USR_SID");
            sql.addSql(" where");

            // 指定ディレクトリ以下の階層ディレクトリをチェック
            FileDao.getLoopListSql(sql, "FDR_SID", dirSidList);

            sql.addSql(" group by USID");
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                int userSid = rs.getInt("USID");
                if (userSid >= 0) {
                    ret.add(Integer.valueOf(userSid));
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
     * <p>キャビネット詳細で使用する、アクセス制御情報の件数を取得する。
     * @param dirSidList ディレクトリSID一覧
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getDirectoryAccessList(List<Integer> dirSidList)
            throws SQLException {
        if (dirSidList == null || dirSidList.size() == 0) {
            return null;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //キャビネットSIDからアクセス制御一覧取得
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   case when FILE_DACCESS_CONF.USR_KBN = ?");
            sql.addSql("        then FILE_DACCESS_CONF.USR_SID,");
            sql.addSql("        else CMN_BELONGM.USR_SID");
            sql.addSql("        end as USR_SID");
            sql.addSql(" from");
            sql.addSql("   FILE_DACCESS_CONF");
            sql.addSql(" left join");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" on CMN_BELONGM.GRP_SID = FILE_DACCESS_CONF.USR_SID");
            sql.addSql(" where");
            sql.addSql("   FILE_DACCESS_CONF.FDA_AUTH = ?");
            sql.addSql(" and (");

            // 指定ディレクトリ以下の階層ディレクトリをチェック
            FileDao.getLoopListSql(sql, "FDR_SID", dirSidList);

            sql.addSql(" )");
            sql.addSql(" group by USR_SID");
            sql.addIntValue(GSConstFile.USER_KBN_USER);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(Integer.valueOf(rs.getInt("USR_SID")));
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
     * <p>Create CMN_USRM_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    private FileDAccessUserModel __getFileAccessUserModelFromRs(ResultSet rs) throws SQLException {
        FileDAccessUserModel bean = new FileDAccessUserModel();
        bean.setAccessKbn(rs.getInt("ACKBN"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSeiKn(rs.getString("USI_SEI_KN"));
        bean.setUsiMeiKn(rs.getString("USI_MEI_KN"));
        /*
        bean.setUsiSini(rs.getString("USI_SINI"));
        bean.setUsiBdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_BDATE")));
        bean.setUsiZip1(rs.getString("USI_ZIP1"));
        bean.setUsiZip2(rs.getString("USI_ZIP2"));
        bean.setTdfSid(rs.getInt("TDF_SID"));
        bean.setUsiAddr1(rs.getString("USI_ADDR1"));
        bean.setUsiAddr2(rs.getString("USI_ADDR2"));
        bean.setUsiTel1(rs.getString("USI_TEL1"));
        bean.setUsiTelNai1(rs.getString("USI_TEL_NAI1"));
        bean.setUsiTelCmt1(rs.getString("USI_TEL_CMT1"));
        bean.setUsiTel2(rs.getString("USI_TEL2"));
        bean.setUsiTelNai2(rs.getString("USI_TEL_NAI2"));
        bean.setUsiTelCmt2(rs.getString("USI_TEL_CMT2"));
        bean.setUsiTel3(rs.getString("USI_TEL3"));
        bean.setUsiTelNai3(rs.getString("USI_TEL_NAI3"));
        bean.setUsiTelCmt3(rs.getString("USI_TEL_CMT3"));
        bean.setUsiFax1(rs.getString("USI_FAX1"));
        bean.setUsiFaxCmt1(rs.getString("USI_FAX_CMT1"));
        bean.setUsiFax2(rs.getString("USI_FAX2"));
        bean.setUsiFaxCmt2(rs.getString("USI_FAX_CMT2"));
        bean.setUsiFax3(rs.getString("USI_FAX3"));
        bean.setUsiFaxCmt3(rs.getString("USI_FAX_CMT3"));
        bean.setUsiMail1(rs.getString("USI_MAIL1"));
        bean.setUsiMailCmt1(rs.getString("USI_MAIL_CMT1"));
        bean.setUsiMail2(rs.getString("USI_MAIL2"));
        bean.setUsiMailCmt2(rs.getString("USI_MAIL_CMT2"));
        bean.setUsiMail3(rs.getString("USI_MAIL3"));
        bean.setUsiMailCmt3(rs.getString("USI_MAIL_CMT3"));
        bean.setUsiSyozoku(rs.getString("USI_SYOZOKU"));
        */
        bean.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
        bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));
        /*
        bean.setUsiBiko(rs.getString("USI_BIKO"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setUsiPictKf(rs.getInt("USI_PICT_KF"));
        bean.setUsiBdateKf(rs.getInt("USI_BDATE_KF"));
        bean.setUsiMail1Kf(rs.getInt("USI_MAIL1_KF"));
        bean.setUsiMail2Kf(rs.getInt("USI_MAIL2_KF"));
        bean.setUsiMail3Kf(rs.getInt("USI_MAIL3_KF"));
        bean.setUsiZipKf(rs.getInt("USI_ZIP_KF"));
        bean.setUsiTdfKf(rs.getInt("USI_TDF_KF"));
        bean.setUsiAddr1Kf(rs.getInt("USI_ADDR1_KF"));
        bean.setUsiAddr2Kf(rs.getInt("USI_ADDR2_KF"));
        bean.setUsiTel1Kf(rs.getInt("USI_TEL1_KF"));
        bean.setUsiTel2Kf(rs.getInt("USI_TEL2_KF"));
        bean.setUsiTel3Kf(rs.getInt("USI_TEL3_KF"));
        bean.setUsiFax1Kf(rs.getInt("USI_FAX1_KF"));
        bean.setUsiFax2Kf(rs.getInt("USI_FAX2_KF"));
        bean.setUsiFax3Kf(rs.getInt("USI_FAX3_KF"));
        bean.setUsiLtlgin(UDate.getInstanceTimestamp(rs.getTimestamp("USI_LTLGIN")));
        bean.setUsiAuid(rs.getInt("USI_AUID"));
        bean.setUsiAdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_ADATE")));
        bean.setUsiEuid(rs.getInt("USI_EUID"));
        bean.setUsiEdate(UDate.getInstanceTimestamp(rs.getTimestamp("USI_EDATE")));

        //生年月日
        if (bean.getUsiBdateKf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiBdate(null);
        }
        //メールアドレス1
        if (bean.getUsiMail1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiMail1(null);
            bean.setUsiMailCmt1(null);
        }
        //メールアドレス2
        if (bean.getUsiMail2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiMail2(null);
            bean.setUsiMailCmt2(null);
        }
        //メールアドレス3
        if (bean.getUsiMail3Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiMail3(null);
            bean.setUsiMailCmt3(null);
        }
        //郵便番号
        if (bean.getUsiZipKf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiZip1(null);
            bean.setUsiZip2(null);
        }
        //都道府県
        if (bean.getUsiTdfKf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setTdfSid(-1);
        }
        //住所1
        if (bean.getUsiAddr1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiAddr1(null);
        }
        //住所2
        if (bean.getUsiAddr2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiAddr2(null);
        }
        //TEL1
        if (bean.getUsiTel1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiTel1(null);
            bean.setUsiTelNai1(null);
            bean.setUsiTelCmt1(null);
        }
        //TEL2
        if (bean.getUsiTel2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiTel2(null);
            bean.setUsiTelNai2(null);
            bean.setUsiTelCmt2(null);
        }
        //TEL3
        if (bean.getUsiTel3Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiTel3(null);
            bean.setUsiTelNai3(null);
            bean.setUsiTelCmt3(null);
        }
        //FAX1
        if (bean.getUsiFax1Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiFax1(null);
            bean.setUsiFaxCmt1(null);
        }
        //FAX2
        if (bean.getUsiFax2Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiFax2(null);
            bean.setUsiFaxCmt2(null);
        }
        //FAX3
        if (bean.getUsiFax3Kf() == GSConstUser.INDIVIDUAL_INFO_CLOSE) {
            bean.setUsiFax3(null);
            bean.setUsiFaxCmt3(null);
        }
        */
        return bean;
    }
}
