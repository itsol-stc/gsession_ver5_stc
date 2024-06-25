package jp.groupsession.v2.enq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.enq.model.EnqMenuListModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>ENQ_TYPE Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqMenuListDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqMenuListDao.class);

    /**
     * <p>Default Constructor
     */
    public EnqMenuListDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqMenuListDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定したデータ区分のアンケート一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dataKbn データ区分
     * @return ENQ_MAINModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqMenuListModel> selectMenuList(int dataKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        EnqMenuListModel mdl = null;
        List<EnqMenuListModel> ret = new ArrayList<EnqMenuListModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   EMN_SID,");
            sql.addSql("   EMN_DATA_KBN,");
            sql.addSql("   ENQ_MAIN.ETP_SID,");
            sql.addSql("   ETP_NAME,");
            sql.addSql("   EMN_TITLE,");
            sql.addSql("   EMN_PRI_KBN,");
            sql.addSql("   EMN_DESC,");
            sql.addSql("   EMN_DESC_PLAIN");
            sql.addSql(" from");
            sql.addSql("   ENQ_MAIN");
            sql.addSql(" left join ENQ_TYPE");
            sql.addSql("   on ENQ_MAIN.ETP_SID = ENQ_TYPE.ETP_SID");
            sql.addSql(" where");
            sql.addSql("   EMN_DATA_KBN=?");
            sql.addSql(" order by");
            sql.addSql("   case when ENQ_MAIN.ETP_SID < 1 then 1 else 0 end,");
            sql.addSql("   ETP_DSP_SEQ, EMN_TITLE");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(dataKbn);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                mdl = __getEnqMainFromRs(rs);
                ret.add(mdl);
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
     * <p>Create ENQ_MAIN Data Binding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqMainModel
     * @throws SQLException SQL実行例外
     */
    private EnqMenuListModel __getEnqMainFromRs(ResultSet rs) throws SQLException {
        EnqMenuListModel bean = new EnqMenuListModel();
        bean.setEmnSid(rs.getInt("EMN_SID"));
        bean.setEmnDataKbn(rs.getInt("EMN_DATA_KBN"));
        bean.setEtpSid(rs.getInt("ETP_SID"));
        bean.setEtpName(rs.getString("ETP_NAME"));
        bean.setEmnTitle(rs.getString("EMN_TITLE"));
        bean.setEmnPriKbn(rs.getInt("EMN_PRI_KBN"));
        bean.setEmnDesc(rs.getString("EMN_DESC"));
        bean.setEmnDescPlain(rs.getString("EMN_DESC_PLAIN"));
        return bean;
    }
}
