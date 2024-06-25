package jp.groupsession.v2.ntp.ntp088;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] スケジュール 特例アクセス管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp088Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp088Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Ntp088Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 特例アクセス情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @param reqMdl リクエスト情報
     * @return 特例アクセス情報の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Ntp088SpAccessModel> getAccessList(Ntp088SearchModel searchMdl,
                                                                            RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Ntp088SpAccessModel> ret = new ArrayList<Ntp088SpAccessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NTP_SPACCESS.NSA_SID as NSA_SID,");
            sql.addSql("   NTP_SPACCESS.NSA_NAME as NSA_NAME,");
            sql.addSql("   NTP_SPACCESS.NSA_BIKO as NSA_BIKO,");
            sql.addSql("   PERMIT_USER_COUNT.USR_COUNT as USR_COUNT,");
            sql.addSql("   PERMIT_POSITION.POS_SID as POS_SID,");
            sql.addSql("   PERMIT_POSITION.POS_NAME as POS_NAME");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         PERMIT_USER.NSA_SID,");
            sql.addSql("         count(PERMIT_USER.USR_SID) as USR_COUNT");
            sql.addSql("       from");
            sql.addSql("         (");
            sql.addSql("           select");
            sql.addSql("             NTP_SPACCESS_PERMIT.NSA_SID as NSA_SID,");
            sql.addSql("             NTP_SPACCESS_PERMIT.NSP_PSID as USR_SID");
            sql.addSql("           from");
            sql.addSql("             CMN_USRM,");
            sql.addSql("             NTP_SPACCESS_PERMIT");
            sql.addSql("           where");
            sql.addSql("             CMN_USRM.USR_JKBN = ?");
            sql.addSql("           and");
            sql.addSql("             NTP_SPACCESS_PERMIT.NSP_TYPE = ?");
            sql.addSql("           and");
            sql.addSql("             CMN_USRM.USR_SID = NTP_SPACCESS_PERMIT.NSP_PSID");
            sql.addSql("         union");
            sql.addSql("           select");
            sql.addSql("             NTP_SPACCESS_PERMIT.NSA_SID as NSA_SID,");
            sql.addSql("             CMN_BELONGM.USR_SID as USR_SID");
            sql.addSql("           from");
            sql.addSql("             CMN_USRM,");
            sql.addSql("             CMN_GROUPM,");
            sql.addSql("             CMN_BELONGM,");
            sql.addSql("             NTP_SPACCESS_PERMIT");
            sql.addSql("           where");
            sql.addSql("             CMN_USRM.USR_JKBN = ?");
            sql.addSql("           and");
            sql.addSql("             CMN_GROUPM.GRP_JKBN = ?");
            sql.addSql("           and");
            sql.addSql("             NTP_SPACCESS_PERMIT.NSP_TYPE = ?");
            sql.addSql("           and");
            sql.addSql("             CMN_GROUPM.GRP_SID = NTP_SPACCESS_PERMIT.NSP_PSID");
            sql.addSql("           and");
            sql.addSql("             CMN_GROUPM.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("           and");
            sql.addSql("             CMN_BELONGM.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("        ) PERMIT_USER");
            sql.addSql("      group by");
            sql.addSql("        PERMIT_USER.NSA_SID");
            sql.addSql("     ) PERMIT_USER_COUNT");
            sql.addSql("   on");
            sql.addSql("     NTP_SPACCESS.NSA_SID = PERMIT_USER_COUNT.NSA_SID");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("       select");
            sql.addSql("         NTP_SPACCESS_PERMIT.NSA_SID as NSA_SID,");
            sql.addSql("         CMN_POSITION.POS_SID as POS_SID,");
            sql.addSql("         CMN_POSITION.POS_NAME as POS_NAME,");
            sql.addSql("         CMN_POSITION.POS_SORT as POS_SORT");
            sql.addSql("       from");
            sql.addSql("         NTP_SPACCESS_PERMIT,");
            sql.addSql("         CMN_POSITION");
            sql.addSql("       where");
            sql.addSql("         NTP_SPACCESS_PERMIT.NSP_TYPE = ?");
            sql.addSql("       and");
            sql.addSql("         NTP_SPACCESS_PERMIT.NSP_PSID = CMN_POSITION.POS_SID");
            sql.addSql("     ) PERMIT_POSITION");
            sql.addSql("   on");
            sql.addSql("     NTP_SPACCESS.NSA_SID = PERMIT_POSITION.NSA_SID");

            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConst.SP_TYPE_USER);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addIntValue(GSConst.SP_TYPE_GROUP);
            sql.addIntValue(GSConst.SP_TYPE_POSITION);

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

            //ソート
            String order = "";
            if (searchMdl.getOrder() == Ntp088Const.ORDER_DESC) {
                order = " desc";
            }
            sql.addSql(" order by");
            if (searchMdl.getSortKey() == Ntp088Const.SKEY_USER) {
                sql.addSql("   USR_COUNT" + order);
            } else {
                sql.addSql("   NSA_NAME" + order);
            }
            sql.setPagingValue(searchMdl.getStart() - 1, searchMdl.getLimit());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Ntp088SpAccessModel model = new Ntp088SpAccessModel();
                model.setSsaSid(rs.getInt("NSA_SID"));
                model.setName(rs.getString("NSA_NAME"));
                model.setBiko(rs.getString("NSA_BIKO"));
                model.setPosSid(rs.getInt("POS_SID"));
                model.setPosName(rs.getString("POS_NAME"));
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
     * <br>[機  能] レコード件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  searchMdl 検索パラメータモデル
     * @throws SQLException SQL実行例外
     * @return count 件数
     */
    public int recordCount(Ntp088SearchModel searchMdl) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql(" count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS");

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <br>[機  能] SqlBufferに検索条件を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件Model
     * @return SqlBuffer
     */
    private SqlBuffer __setSqlWhere(SqlBuffer sql, Ntp088SearchModel searchMdl) {
        if (searchMdl.getUserSid() > 0) {
            sql.addSql(" where");
            sql.addSql("   NTP_SPACCESS.NSA_SID in (");
            sql.addSql("      select NSA_SID from NTP_SPACCESS_PERMIT");
            sql.addSql("      where");
            sql.addSql("        (");
            sql.addSql("          NTP_SPACCESS_PERMIT.NSP_TYPE = ?");
            sql.addSql("        and");
            sql.addSql("          NTP_SPACCESS_PERMIT.NSP_PSID = ?");
            sql.addSql("        )");
            sql.addSql("      or");
            sql.addSql("        (");
            sql.addSql("          NTP_SPACCESS_PERMIT.NSP_TYPE = ?");
            sql.addSql("        and");
            sql.addSql("          NTP_SPACCESS_PERMIT.NSP_PSID in (");
            sql.addSql("            select GRP_SID from CMN_BELONGM");
            sql.addSql("            where CMN_BELONGM.USR_SID = ?");
            sql.addSql("          )");
            sql.addSql("      or");
            sql.addSql("        (");
            sql.addSql("          NTP_SPACCESS_PERMIT.NSP_TYPE = ?");
            sql.addSql("        and");
            sql.addSql("          exists (");
            sql.addSql("            select 1 from");
            sql.addSql("              CMN_USRM,");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where");
            sql.addSql("              CMN_USRM.USR_SID = ?");
            sql.addSql("            and");
            sql.addSql("              CMN_USRM.POS_SID = CMN_POSITION.POS_SID");
            sql.addSql("            and");
            sql.addSql("              CMN_POSITION.POS_SORT >= (");
            sql.addSql("                select CMN_POSITION.POS_SORT");
            sql.addSql("                from");
            sql.addSql("                  CMN_POSITION");
            sql.addSql("                where");
            sql.addSql("                  CMN_POSITION.POS_SID = NTP_SPACCESS_PERMIT.NSP_PSID");
            sql.addSql("          )");
            sql.addSql("        )");
            sql.addSql("      )");
            sql.addSql("    )");
            sql.addIntValue(GSConst.SP_TYPE_USER);
            sql.addIntValue(searchMdl.getUserSid());
            sql.addIntValue(GSConst.SP_TYPE_GROUP);
            sql.addIntValue(searchMdl.getUserSid());
            sql.addIntValue(GSConst.SP_TYPE_POSITION);
            sql.addIntValue(searchMdl.getUserSid());
        }

        if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {
            if (searchMdl.getUserSid() > 0) {
                sql.addSql(" and");
            } else {
                sql.addSql(" where");
            }
            sql.addSql("   NTP_SPACCESS.NSA_NAME like '%"
                    + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        return sql;
    }

    /**
     * <br>[機  能] 特例アクセス名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param ssaSidList 特例アクセスSIDリスト
     * @return 特例アクセス名の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<String> getAccessNameList(String[] ssaSidList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NSA_NAME");
            sql.addSql(" from ");
            sql.addSql("   NTP_SPACCESS");
            sql.addSql(" where ");
            sql.addSql("   NSA_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(ssaSidList[0]));
            for (String ssaSid : ssaSidList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(ssaSid));
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("NSA_NAME"));
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
