package jp.groupsession.v2.ptl.ptl990;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.GSConstPortal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポータル ポートレット画像表示で使用するDAOクラス
 * <br>[解  説] 指定したユーザがポートレットを閲覧可能かの判定、画像情報の取得などを行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl990Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ptl990Dao.class);

    /**
     * <p>Default Constructor
     */
    public Ptl990Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ptl990Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ユーザが指定されたポートレットを閲覧可能かを判定する
     * <br>[解  説] 指定されたポートレットを使用しているポータルを取得し、
     * <br>         そのポータルをユーザが閲覧可能か否かで判定を行う
     * <br>[備  考]
     * @param pltSid ポートレットSID
     * @param userSid ユーザSID
     * @return true:閲覧可能 false:閲覧不可
     * @throws SQLException SQL実行例外
     */
    public boolean canViewPortlet(int pltSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean result = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   PTL_PORTAL.PTL_SID");
            sql.addSql(" from ");
            sql.addSql("   PTL_PORTAL,");
            sql.addSql("   PTL_PORTAL_POSITION");
            sql.addSql(" where");
            sql.addSql("   PTL_PORTAL_POSITION.PTP_TYPE = ?");
            sql.addSql(" and");
            sql.addSql("   PTL_PORTAL_POSITION.PLT_SID = ?");
            sql.addIntValue(GSConstPortal.PTP_TYPE_PORTLET);
            sql.addIntValue(pltSid);

            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      PTL_PORTAL.PTL_ACCESS = ?");
            sql.addSql("    or");
            sql.addSql("      (");
            sql.addSql("         PTL_PORTAL.PTL_ACCESS = ?");
            sql.addSql("       and");
            sql.addSql("         PTL_PORTAL.PTL_SID in (");
            sql.addSql("           select");
            sql.addSql("             PTL_PORTAL_CONF_READ.PTL_SID");
            sql.addSql("           from");
            sql.addSql("             PTL_PORTAL_CONF_READ");
            sql.addSql("             left join");
            sql.addSql("               CMN_BELONGM");
            sql.addSql("             on");
            sql.addSql("                PTL_PORTAL_CONF_READ.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("           where");
            sql.addSql("             PTL_PORTAL_CONF_READ.USR_SID = ?");
            sql.addSql("           or");
            sql.addSql("             CMN_BELONGM.USR_SID = ?");
            sql.addSql("         )");
            sql.addSql("      )");
            sql.addSql("   )");
            sql.addIntValue(GSConstPortal.PTL_ACCESS_OFF);
            sql.addIntValue(GSConstPortal.PTL_ACCESS_ON);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            sql.addSql(" and");
            sql.addSql("   PTL_PORTAL.PTL_SID = PTL_PORTAL_POSITION.PTL_SID");
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
     * <br>[機  能] 指定したポートレット画像のバイナリSIDを取得する
     * <br>[解  説] レコードが存在しない または バイナリー情報が論理削除されている場合
     * <br>         -1を返す
     * <br>[備  考]
     * @param pltSid ポートレットSID
     * @param pliSid ポートレット画像SID
     * @return バイナリSID
     * @throws SQLException SQL実行例外
     */
    public long getPortletBinSid(int pltSid, long pliSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long binSid = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   PTL_PORTLET_IMAGE.BIN_SID as BIN_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF,");
            sql.addSql("   PTL_PORTLET_IMAGE");
            sql.addSql(" where ");
            sql.addSql("   PTL_PORTLET_IMAGE.PLT_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   PTL_PORTLET_IMAGE.PLI_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CMN_BINF.BIN_SID = PTL_PORTLET_IMAGE.BIN_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");

            sql.addIntValue(pltSid);
            sql.addLongValue(pliSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                binSid = rs.getLong("BIN_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return binSid;
    }
}
