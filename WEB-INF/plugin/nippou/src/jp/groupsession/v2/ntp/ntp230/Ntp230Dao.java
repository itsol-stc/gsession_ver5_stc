package jp.groupsession.v2.ntp.ntp230;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpTargetModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 目標一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp230Dao  extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp230Dao.class);

    /**
     *<p>Default Constructor
     * */
    public Ntp230Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp230Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 目標情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 目標リスト
     * @throws SQLException SQL実行時例外
     */
    public List<NtpTargetModel> getTargetList()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpTargetModel> ret = new ArrayList<NtpTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_TARGET.NTG_SID AS NTG_SID, ");
            sql.addSql("   NTP_TARGET.NTG_NAME AS NTG_NAME, ");
            sql.addSql("   NTP_TARGET.NTG_DETAIL AS NTG_DETAIL, ");
            sql.addSql("   NTP_TARGET.NTG_UNIT AS NTG_UNIT, ");
            sql.addSql("   NTP_TARGET.NTG_DEF AS NTG_DEF, ");
            sql.addSql("   TARGET_SORT.NTS_SORT AS NTS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_TARGET");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NTG_SID, NTS_SORT");
            sql.addSql("      from NTP_TARGET_SORT");
            sql.addSql("     ) TARGET_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_TARGET.NTG_SID = TARGET_SORT.NTG_SID ");
            sql.addSql(" order by ");
            sql.addSql("   TARGET_SORT.NTS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NtpTargetModel model = new NtpTargetModel();
                model.setNtgSid(rs.getInt("NTG_SID"));
                model.setNtgName(rs.getString("NTG_NAME"));
                model.setNtgDetail(rs.getString("NTG_DETAIL"));
                model.setNtgDef(rs.getLong("NTG_DEF"));
                model.setNtgUnit(rs.getString("NTG_UNIT"));
                model.setNtgSort(rs.getInt("NTS_SORT"));
                ret.add(model);
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
     * <br>[機  能] 指定した目標SIDの目標情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ntgSids 目標SID
     * @return 目標リスト
     * @throws SQLException SQL実行時例外
     */
    public List<NtpTargetModel> getTargetList(ArrayList<Integer> ntgSids)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpTargetModel> ret = new ArrayList<NtpTargetModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_TARGET.NTG_SID AS NTG_SID, ");
            sql.addSql("   NTP_TARGET.NTG_NAME AS NTG_NAME, ");
            sql.addSql("   NTP_TARGET.NTG_DETAIL AS NTG_DETAIL, ");
            sql.addSql("   NTP_TARGET.NTG_UNIT AS NTG_UNIT, ");
            sql.addSql("   NTP_TARGET.NTG_DEF AS NTG_DEF, ");
            sql.addSql("   TARGET_SORT.NTS_SORT AS NTS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_TARGET");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NTG_SID, NTS_SORT");
            sql.addSql("      from NTP_TARGET_SORT");
            sql.addSql("     ) TARGET_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_TARGET.NTG_SID = TARGET_SORT.NTG_SID ");

            if (!ntgSids.isEmpty()) {
                sql.addSql("   where NTP_TARGET.NTG_SID in (");
                for (int i = 0; i < ntgSids.size(); i++) {
                    if (i == ntgSids.size() - 1) {
                        sql.addSql(String.valueOf(ntgSids.get(i)));
                    } else {
                        sql.addSql(String.valueOf(ntgSids.get(i)) + ",");
                    }
                }
                sql.addSql("   )");
            }

            sql.addSql(" order by ");
            sql.addSql("   TARGET_SORT.NTS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NtpTargetModel model = new NtpTargetModel();
                model.setNtgSid(rs.getInt("NTG_SID"));
                model.setNtgName(rs.getString("NTG_NAME"));
                model.setNtgDetail(rs.getString("NTG_DETAIL"));
                model.setNtgDef(rs.getLong("NTG_DEF"));
                model.setNtgUnit(rs.getString("NTG_UNIT"));
                model.setNtgSort(rs.getInt("NTS_SORT"));
                ret.add(model);
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
