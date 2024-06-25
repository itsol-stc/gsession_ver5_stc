package jp.groupsession.v2.ntp.ntp170;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpKtbunruiModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 活動分類一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp170Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp170Dao.class);

    /**
     * <p>Default Constructor
     */
    public Ntp170Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp170Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 活動分類情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 活動分類リスト
     * @throws SQLException SQL実行時例外
     */
    public List<NtpKtbunruiModel> getKtBunruiList()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpKtbunruiModel> ret = new ArrayList<NtpKtbunruiModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_KTBUNRUI.NKB_SID as NKB_SID, ");
            sql.addSql("   NTP_KTBUNRUI.NKB_CODE as NKB_CODE, ");
            sql.addSql("   NTP_KTBUNRUI.NKB_NAME as NKB_NAME, ");
            sql.addSql("   NTP_KTBUNRUI.NKB_TIEUP_KBN as NKB_TIEUP_KBN, ");
            sql.addSql("   KTBUNRUI_SORT.NKS_SORT as NKS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_KTBUNRUI");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NKB_SID, NKS_SORT");
            sql.addSql("      from NTP_KTBUNRUI_SORT ");
            sql.addSql("     ) KTBUNRUI_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_KTBUNRUI.NKB_SID = KTBUNRUI_SORT.NKB_SID ");
            sql.addSql(" order by ");
            sql.addSql("   KTBUNRUI_SORT.NKS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NtpKtbunruiModel model = new NtpKtbunruiModel();
                model.setNkbSid(rs.getInt("NKB_SID"));
                model.setNkbCode(rs.getString("NKB_CODE"));
                model.setNkbName(rs.getString("NKB_NAME"));
                model.setNkbTieupKbn(rs.getInt("NKB_TIEUP_KBN"));
                model.setNksSort(rs.getInt("NKS_SORT"));
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
