package jp.groupsession.v2.api.api030;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] トークン管理 DAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Api030Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Api030Dao.class);
    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Api030Dao(Connection con) {
        super(con);
    }
    /**
     *
     * <br>[機  能] 一覧件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public int selectCount(Api030ParamModel param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from");
            sql.addSql("   API_TOKEN");
            __writeWhereSql(sql, param);
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return 0;
    }
    /**
     *
     * <br>[機  能] 検索条件部の書き込みを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sql sql
     * @param param パラメータ
     */
    private void __writeWhereSql(SqlBuffer sql, Api030ParamModel param) {
        sql.addSql(" where ");
        UDate now = new UDate();
        sql.addSql(" API_TOKEN.APT_LIMIT_DATE > ?");
        sql.addDateValue(now);
        //クライアントタイプ
        sql.addSql("and API_TOKEN.APT_CLIENT in (");
        sql.addSql("     ?,");
        sql.addIntValue(param.getApi030cliantCRSv());
        sql.addSql("     ?,");
        sql.addIntValue(param.getApi030cliantAppSv());
        sql.addSql("     ?)");
        sql.addIntValue(param.getApi030cliantOtherSv());
        //ユーザ・グループ
        if (param.getApi030userSv() > 0) {
            sql.addSql(" and API_TOKEN.USR_SID = ?");
            sql.addIntValue(param.getApi030userSv());
        } else if (param.getApi030groupSv() > 0) {
            sql.addSql(" and API_TOKEN.USR_SID in (");
            sql.addSql("    select USR_SID from CMN_BELONGM");
            sql.addSql("    where GRP_SID = ?)");
            sql.addIntValue(param.getApi030groupSv());
        }
        if (param.getApi030targetDisabledSv() == Api030Biz.SEARCH_TARGET_DISABLED_ON) {
            sql.addSql("and API_TOKEN.APT_JKBN = ?");
            sql.addIntValue(GSConstApi.TOKEN_ENABLED);
        }
    }
    /**
     *
     * <br>[機  能] トークン一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータ
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Api030TokenModel> select(Api030ParamModel param) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Api030TokenModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   API_TOKEN.APT_TOKEN as APT_TOKEN,");
            sql.addSql("   API_TOKEN.APT_CLIENT as APT_CLIENT,");
            sql.addSql("   API_TOKEN.APT_CLIENT_ID as APT_CLIENT_ID,");
            sql.addSql("   API_TOKEN.APT_IP as APT_IP,");
            sql.addSql("   API_TOKEN.APT_JKBN as APT_JKBN,");
            sql.addSql("   API_TOKEN.APT_LIMIT_DATE as APT_LIMIT_DATE,");
            sql.addSql("   API_TOKEN.APT_AUID as APT_AUID,");
            sql.addSql("   API_TOKEN.APT_ADATE as APT_ADATE,");
            sql.addSql("   API_TOKEN.APT_EUID as APT_EUID,");
            sql.addSql("   API_TOKEN.APT_EDATE as APT_EDATE, ");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID, ");
            sql.addSql("   CMN_USRM.USR_JKBN as USR_JKBN, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI ");
            sql.addSql(" from");
            sql.addSql("   API_TOKEN");
            sql.addSql("    left join CMN_USRM ");
            sql.addSql("      on API_TOKEN.USR_SID=CMN_USRM.USR_SID");
            sql.addSql("    left join CMN_USRM_INF");
            sql.addSql("      on API_TOKEN.USR_SID=CMN_USRM_INF.USR_SID");
            __writeWhereSql(sql, param);
            sql.addSql(" order by");

            String orderStr = "";
            //オーダー
            if (param.getApi030orderKeySv() == GSConstSmail.ORDER_KEY_ASC) {
                orderStr = "  asc";
            } else if (param.getApi030orderKeySv() == GSConstSmail.ORDER_KEY_DESC) {
                orderStr = "  desc";
            }

            //ソートカラム
            switch (param.getApi030sortKeySv()) {
                //ユーザ
                case Api030Biz.TOKEN_SORTKEY_USER:
                    sql.addSql("  (case when CMN_USRM_INF.USR_SID is null then 1");
                    sql.addSql("      else 0 end) " + orderStr + ",");
                    sql.addSql("  CMN_USRM_INF.USI_SEI_KN");
                    sql.addSql(orderStr);
                    sql.addSql(",");
                    sql.addSql("  CMN_USRM_INF.USI_MEI_KN");
                    sql.addSql(orderStr);
                    sql.addSql(",");
                    sql.addSql("  API_TOKEN.APT_ADATE");
                    sql.addSql(orderStr);
                    break;
                case Api030Biz.TOKEN_SORTKEY_CLIENT:
                    sql.addSql("  API_TOKEN.APT_CLIENT");
                    sql.addSql(orderStr);
                    sql.addSql(",");
                    sql.addSql("  API_TOKEN.APT_ADATE");
                    sql.addSql(orderStr);
                    break;
                case Api030Biz.TOKEN_SORTKEY_LDATE:
                    sql.addSql("  API_TOKEN.APT_LIMIT_DATE");
                    sql.addSql(orderStr);
                    sql.addSql(",");
                    sql.addSql("  API_TOKEN.APT_ADATE");
                    sql.addSql(orderStr);
                    break;
                case Api030Biz.TOKEN_SORTKEY_ADATE:
                default:
                    sql.addSql("  API_TOKEN.APT_ADATE");
                    sql.addSql(orderStr);
                    break;
            }
            int page = param.getApi030page();
            if (page <= 0) {
                page = 1;
            }
            int offset = PageUtil.getRowNumber(page, Api030Biz.MAX_ROWCOUNT);
            if (offset > 0) {
                offset--;
            }
            sql.setPagingValue(offset, Api030Biz.MAX_ROWCOUNT);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Api030TokenModel bean = new Api030TokenModel();
                bean.setAptToken(rs.getString("APT_TOKEN"));
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setAptClient(rs.getInt("APT_CLIENT"));
                bean.setAptClientId(rs.getString("APT_CLIENT_ID"));
                bean.setAptIp(rs.getString("APT_IP"));
                bean.setAptJkbn(rs.getInt("APT_JKBN"));
                bean.setAptLimitDate(UDate.getInstanceTimestamp(rs.getTimestamp("APT_LIMIT_DATE")));
                bean.setAptAuid(rs.getInt("APT_AUID"));
                bean.setAptAdate(UDate.getInstanceTimestamp(rs.getTimestamp("APT_ADATE")));
                bean.setAptEuid(rs.getInt("APT_EUID"));
                bean.setAptEdate(UDate.getInstanceTimestamp(rs.getTimestamp("APT_EDATE")));
                bean.setYukoYear(bean.getAptLimitDate().getYear());

                //ユーザ情報
                UsrLabelValueBean user = new UsrLabelValueBean(
                        rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"),
                        String.valueOf(rs.getString("USR_SID")),
                        rs.getInt("USR_UKO_FLG"));
                user.setJkbn(rs.getInt("USR_JKBN"));
                bean.setUser(user);
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
