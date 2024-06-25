package jp.groupsession.v2.fil.fil240;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.GSConstFile;

/**
 * <p>更新通知一覧画面で使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Fil240Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil240Dao.class);

    /**
     * <p>Default Constructor
     */
    public Fil240Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil240Dao(Connection con) {
        super(con);
    }

    /**
     * <p>ユーザSIDを指定し未確認な更新通知情報のディレクトリ情報を取得
     * @param usrSid USR_SID
     * @param isAdmin true:システム or プラグイン管理者 false:一般ユーザ
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return FILE_SHORTCUT_CONFModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Fil240Model> getUpdateCallData(int usrSid, boolean isAdmin,
                                                    int offset, int limit)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Fil240Model> ret = new ArrayList<Fil240Model>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FILE_DIRECTORY.FDR_SID as FDR_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID as FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION as FDR_VERSION,");
            sql.addSql("   FILE_DIRECTORY.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_KBN as FDR_KBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_VER_KBN as FDR_VER_KBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_LEVEL as FDR_LEVEL,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME as FDR_NAME,");
            sql.addSql("   FILE_DIRECTORY.FDR_BIKO as FDR_BIKO,");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN as FDR_JTKBN,");
            sql.addSql("   FILE_DIRECTORY.FDR_AUID as FDR_AUID,");
            sql.addSql("   FILE_DIRECTORY.FDR_ADATE as FDR_ADATE,");
            sql.addSql("   FILE_DIRECTORY.FDR_EUID as FDR_EUID,");
            sql.addSql("   FILE_DIRECTORY.FDR_EGID as FDR_EGID,");
            sql.addSql("   FILE_DIRECTORY.FDR_EDATE as FDR_EDATE,");
            sql.addSql("   FILE_FILE_BIN.BIN_SID as BIN_SID,");
            sql.addSql("   FILE_FILE_REKI.FFR_UP_CMT as FFR_UP_CMT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("   FILE_CABINET.FCB_MARK as FCB_MARK");

            sql.addSql(" from");
            sql.addSql("   FILE_CALL_DATA,");
//            sql.addSql("   FILE_DIRECTORY,");

            sql.addSql("   FILE_DIRECTORY");
            sql.addSql("   left join");
            sql.addSql("     FILE_FILE_BIN");
            sql.addSql("   on");
            sql.addSql("     FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID ");
            sql.addSql("   and");
            sql.addSql("     FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION");
            sql.addSql("   left join");
            sql.addSql("     FILE_FILE_REKI");
            sql.addSql("   on");
            sql.addSql("     FILE_DIRECTORY.FDR_SID = FILE_FILE_REKI.FDR_SID");
            sql.addSql("   and");
            sql.addSql("     FILE_DIRECTORY.FDR_VERSION = FILE_FILE_REKI.FDR_VERSION");
            sql.addSql("   left join CMN_GROUPM");
            sql.addSql("       on FILE_DIRECTORY.FDR_EGID = CMN_GROUPM.GRP_SID,");
//            sql.addSql("     (FILE_DIRECTORY left join FILE_FILE_BIN ");
//            sql.addSql("     on FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID ");
//            sql.addSql("     and FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION");
//            sql.addSql("   ) as FILE_DIRECTORY, ");

            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_DIRECTORY group by FDR_SID) DIR_MAXVERSION,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   FILE_CABINET");
            sql.addSql(" where");
            sql.addSql("   FILE_CALL_DATA.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_CALL_DATA.FDR_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_EUID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FDR_JTKBN = ?");
            sql.addIntValue(usrSid);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);
            sql.addIntValue(GSConstFile.JTKBN_NORMAL);

            //閲覧が許可されていない場合は対象外とする
            /*sql.addSql(" and");
            sql.addSql("   FILE_DIRECTORY.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     FILE_CABINET.FCB_ACCESS_KBN = ?");
            sql.addSql("   or");
            sql.addSql("     (");
            sql.addSql("       FILE_CABINET.FCB_ACCESS_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         exists (");
            sql.addSql("           select 1 from FILE_ACCESS_CONF");
            sql.addSql("           where FILE_CALL_DATA.USR_SID = FILE_ACCESS_CONF.USR_SID");
            sql.addSql("           and FILE_ACCESS_CONF.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql("           and FILE_ACCESS_CONF.USR_KBN = ?");
            sql.addSql("         )");
            sql.addSql("       or");
            sql.addSql("         exists (");
            sql.addSql("           select");
            sql.addSql("             1");
            sql.addSql("           from");
            sql.addSql("             CMN_BELONGM,");
            sql.addSql("             FILE_ACCESS_CONF");
            sql.addSql("           where FILE_CALL_DATA.USR_SID = CMN_BELONGM.USR_SID");
            sql.addSql("           and FILE_ACCESS_CONF.FCB_SID = FILE_CABINET.FCB_SID");
            sql.addSql("           and FILE_ACCESS_CONF.USR_KBN = ?");
            sql.addSql("           and CMN_BELONGM.GRP_SID = FILE_ACCESS_CONF.USR_SID");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addIntValue(GSConstFile.ACCESS_KBN_OFF);
            sql.addIntValue(GSConstFile.ACCESS_KBN_ON);
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(GSConstFile.USER_KBN_GROUP);*/

            //管理者ユーザ以外の場合、ディレクトリのアクセス設定で判別する
            if (!isAdmin) {
                sql.addSql(" and (");
                sql.addSql("   FILE_DIRECTORY.FDR_ACCESS_SID = ?");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);

                //キャビネット管理者
                sql.addSql(" or exists");
                sql.addSql("   (select 1 from FILE_CABINET_ADMIN");
                sql.addSql("    where");
                sql.addSql("      FILE_CABINET_ADMIN.FCB_SID = FILE_DIRECTORY.FCB_SID");
                sql.addSql("    and ");
                sql.addSql("      FILE_CABINET_ADMIN.USR_SID = ?");
                sql.addSql("   )");
                sql.addIntValue(usrSid);

                //個別アクセス制限
                sql.addSql(" or exists");
                sql.addSql("   (select *");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = FILE_DIRECTORY.FDR_ACCESS_SID");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = FILE_CALL_DATA.USR_SID) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select *");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = FILE_CALL_DATA.USR_SID");
                sql.addSql("         )))");
                sql.addSql("   ))");
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
            }

            sql.addSql(" order by");
            sql.addSql("   FILE_DIRECTORY.FDR_EDATE DESC,");
            sql.addSql("   FILE_DIRECTORY.FDR_PARENT_SID,");
            sql.addSql("   FILE_DIRECTORY.FDR_NAME");

            pstmt = con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);


            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (offset > 1) {
                rs.absolute(offset - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                ret.add(__get240ModelFromRs(rs));
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
     * <p>Create FILE_DIRECTORY Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileDirectoryModel
     * @throws SQLException SQL実行例外
     */
    private Fil240Model __get240ModelFromRs(ResultSet rs)
    throws SQLException {
        Fil240Model bean = new Fil240Model();
        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFdrVersion(rs.getInt("FDR_VERSION"));
        bean.setFcbSid(rs.getInt("FCB_SID"));
        bean.setFdrParentSid(rs.getInt("FDR_PARENT_SID"));
        bean.setFdrKbn(rs.getInt("FDR_KBN"));
        bean.setFdrVerKbn(rs.getInt("FDR_VER_KBN"));
        bean.setFdrLevel(rs.getInt("FDR_LEVEL"));
        bean.setFdrName(rs.getString("FDR_NAME"));
        bean.setFdrBiko(rs.getString("FDR_BIKO"));
        bean.setFdrJtkbn(rs.getInt("FDR_JTKBN"));
        bean.setFdrAuid(rs.getInt("FDR_AUID"));
        bean.setFdrAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_ADATE")));
        bean.setFdrEuid(rs.getInt("FDR_EUID"));
        bean.setFdrEgid(rs.getInt("FDR_EGID"));
        bean.setFdrEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FDR_EDATE")));

        if (bean.getFdrEgid() > 0) {
            bean.setEditUsrName(rs.getString("GRP_NAME"));
        } else {
            bean.setEditUsrName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
            bean.setEditUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        }

        bean.setEditUsrJkbn(rs.getString("USR_JKBN"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setFfrUpCmt(rs.getString("FFR_UP_CMT"));
        bean.setFcbMark(rs.getLong("FCB_MARK"));
        return bean;
    }

}
