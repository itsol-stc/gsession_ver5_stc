package jp.groupsession.v2.anp.anp060;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 安否確認メッセージ配信画面DAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp060Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp060Dao.class);

    /**
     *<p>Default Constructor
     * */
    public Anp060Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Anp060Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] テンプレート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return テンプレートリスト
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getTemplateList()
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
            sql.addSql("   ANP_MTEMP.APM_SID AS APM_SID, ");
            sql.addSql("   ANP_MTEMP.APM_TITLE AS APM_TITLE, ");
            sql.addSql("   TEMPLATE_SORT.AMS_SORT AS AMS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   ANP_MTEMP");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select APM_SID, AMS_SORT");
            sql.addSql("      from ANP_MTEMP_SORT");
            sql.addSql("     ) TEMPLATE_SORT");
            sql.addSql("   on");
            sql.addSql("     ANP_MTEMP.APM_SID = TEMPLATE_SORT.APM_SID ");
            sql.addSql(" order by ");
            sql.addSql("   TEMPLATE_SORT.AMS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                LabelValueBean bean = new LabelValueBean();
                bean.setValue(String.valueOf(rs.getInt("APM_SID")));
                bean.setLabel(rs.getString("APM_TITLE"));
                ret.add(bean);
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
