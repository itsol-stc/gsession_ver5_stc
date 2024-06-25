package jp.groupsession.v2.enq.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.enq.model.EnqTypeListModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アンケート種類の取得処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqTypeListDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqTypeListDao.class);

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public EnqTypeListDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アンケート種類の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in ENQ_TYPEModel
     * @throws SQLException SQL実行例外
     */
    public List<EnqTypeListModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<EnqTypeListModel> ret = new ArrayList<EnqTypeListModel>();
        con = getCon();

        UDate now = new UDate();
        now.setZeroHhMmSs();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ETP.ETP_SID,");
            sql.addSql("        ETP_DSP_SEQ,");
            sql.addSql("        ETP_NAME,");
            sql.addSql("        case when SUB1.CNT >= 0 then SUB1.CNT");
            sql.addSql("             else 0");
            sql.addSql("         end as CNT,");
            sql.addSql("        case when OPN0 is null and OPN1 is null");
            sql.addSql("                  then ''");
            sql.addSql("             when OPN0 = cast('9999-12-31' as date)");
            sql.addSql("              and OPN1 = cast('0001-01-01' as date)");
            sql.addSql("                  then ''");
            sql.addSql("             when OPN0 = cast('9999-12-31' as date)");
            sql.addSql("                  then replace(cast(OPN1 as varchar), '-', '/')");
            sql.addSql("             else replace(cast(OPN0 as varchar), '-', '/')");
            sql.addSql("         end as OPN,");
            sql.addSql("        case when RES0 is null and RES1 is null");
            sql.addSql("                  then ''");
            sql.addSql("             when RES0 = cast('9999-12-31' as date)");
            sql.addSql("              and RES1 = cast('0001-01-01' as date)");
            sql.addSql("                  then ''");
            sql.addSql("             when RES0 = cast('9999-12-31' as date)");
            sql.addSql("                  then replace(cast(RES1 as varchar), '-', '/')");
            sql.addSql("             else replace(cast(RES0 as varchar), '-', '/')");
            sql.addSql("         end as RES");
            sql.addSql("   from ENQ_TYPE ETP");
            sql.addSql("   left join (select EMN.ETP_SID as SID,");
            sql.addSql("                     count(*) CNT,");
            sql.addSql("                     min(");
            sql.addSql("                     case when EMN_OPEN_END >= ?");
            sql.addSql("                               then EMN_OPEN_END");
            sql.addSql("                          else cast('9999-12-31' as date) end");
            sql.addSql("                     ) as OPN0,");
            sql.addSql("                     min(");
            sql.addSql("                     case when EMN_RES_END >= ?");
            sql.addSql("                               then EMN_RES_END");
            sql.addSql("                          else cast('9999-12-31' as date) end");
            sql.addSql("                     ) as RES0,");
            sql.addSql("                     max(");
            sql.addSql("                     case when EMN_OPEN_END < ?");
            sql.addSql("                               then EMN_OPEN_END");
            sql.addSql("                          else cast('0001-01-01' as date) end");
            sql.addSql("                     ) as OPN1,");
            sql.addSql("                     max(");
            sql.addSql("                     case when EMN_RES_END < ?");
            sql.addSql("                               then EMN_RES_END");
            sql.addSql("                          else cast('0001-01-01' as date) end");
            sql.addSql("                     ) as RES1");
            sql.addSql("                from ENQ_MAIN EMN");
            sql.addSql("               where EMN.EMN_DATA_KBN in(0, 3)");
            sql.addSql("               group by SID");
            sql.addSql("             ) SUB1 ");
            sql.addSql("          on ETP.ETP_SID = SUB1.SID");
            sql.addSql(" order by ETP.ETP_DSP_SEQ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(now);
            sql.addDateValue(now);
            sql.addDateValue(now);
            sql.addDateValue(now);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getEnqInitTypeFromRs(rs));
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
     * <p>Create ENQ_TYPE Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created EnqTypeModel
     * @throws SQLException SQL実行例外
     */
    private EnqTypeListModel __getEnqInitTypeFromRs(ResultSet rs) throws SQLException {
        EnqTypeListModel bean = new EnqTypeListModel();
        bean.setEtpSid(rs.getInt("ETP_SID"));
        bean.setEtpDspSeq(rs.getInt("ETP_DSP_SEQ"));
        bean.setEtpName(rs.getString("ETP_NAME"));
        bean.setEmnCnt(rs.getInt("CNT"));
        bean.setEmnOpenEnd(rs.getString("OPN"));
        bean.setEmnResEnd(rs.getString("RES"));
        return bean;
    }
}
