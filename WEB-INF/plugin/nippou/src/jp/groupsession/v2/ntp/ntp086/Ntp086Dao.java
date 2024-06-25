package jp.groupsession.v2.ntp.ntp086;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ntp.model.NtpTemplateModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 日報 日報テンプレート一覧画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp086Dao  extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp086Dao.class);

    /**
     *<p>Default Constructor
     * */
    public Ntp086Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Ntp086Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] テンプレート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return テンプレートリスト
     * @throws SQLException SQL実行時例外
     */
    public List<NtpTemplateModel> getTemplateList()
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<NtpTemplateModel> ret = new ArrayList<NtpTemplateModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_TEMPLATE.NTT_SID AS NTT_SID, ");
            sql.addSql("   NTP_TEMPLATE.NTT_NAME AS NTT_NAME, ");
            sql.addSql("   NTP_TEMPLATE.NTT_DETAIL AS NTT_DETAIL, ");
            sql.addSql("   TEMPLATE_SORT.NPS_SORT AS NPS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   NTP_TEMPLATE");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select NTT_SID, NPS_SORT");
            sql.addSql("      from NTP_TEMPLATE_SORT");
            sql.addSql("     ) TEMPLATE_SORT");
            sql.addSql("   on");
            sql.addSql("     NTP_TEMPLATE.NTT_SID = TEMPLATE_SORT.NTT_SID ");
            sql.addSql(" order by ");
            sql.addSql("   TEMPLATE_SORT.NPS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                NtpTemplateModel model = new NtpTemplateModel();
                model.setNttSid(rs.getInt("NTT_SID"));
                model.setNttName(rs.getString("NTT_NAME"));
                model.setNttDetail(rs.getString("NTT_DETAIL"));
                model.setNttSort(rs.getInt("NPS_SORT"));
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
