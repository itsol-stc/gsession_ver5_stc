package jp.groupsession.v2.cht.cht080;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] スケジュール 特例アクセス管理画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht080Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cht080Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Cht080Dao(Connection con) {
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
    public List<Cht080SpAccessModel> getAccessList(Cht080SearchModel searchMdl,
                                                                            RequestModel reqMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Cht080SpAccessModel> ret = new ArrayList<Cht080SpAccessModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CHT_SPACCESS.CHS_SID as CHS_SID,");
            sql.addSql("   CHT_SPACCESS.CHS_NAME as CHS_NAME,");
            sql.addSql("   CHT_SPACCESS.CHS_BIKO as CHS_BIKO");
            sql.addSql(" from ");
            sql.addSql("   CHT_SPACCESS");

            //検索条件
            sql = __setSqlWhere(sql, searchMdl);

            //ソート
            String order = "";
            if (searchMdl.getOrder() == Cht080Const.ORDER_DESC) {
                order = " desc";
            }
            sql.addSql(" order by");
            sql.addSql("   CHS_NAME" + order);
            sql.setPagingValue(searchMdl.getStart() - 1, searchMdl.getLimit());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                Cht080SpAccessModel model = new Cht080SpAccessModel();
                model.setSsaSid(rs.getInt("CHS_SID"));
                model.setName(rs.getString("CHS_NAME"));
                model.setBiko(rs.getString("CHS_BIKO"));
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
    public int recordCount(Cht080SearchModel searchMdl) throws SQLException {
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
            sql.addSql("   CHT_SPACCESS");

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
    private SqlBuffer __setSqlWhere(SqlBuffer sql, Cht080SearchModel searchMdl) {
        if (searchMdl.getUserSid() > 0) {
            sql.addSql(" where");
            sql.addSql("   CHT_SPACCESS.CHS_SID in (");
            sql.addSql("      select CHS_SID from CHT_SPACCESS_PERMIT");
            sql.addSql("      where");
            sql.addSql("        (");
            sql.addSql("          CHT_SPACCESS_PERMIT.CSP_TYPE = ?");
            sql.addSql("        and");
            sql.addSql("          CHT_SPACCESS_PERMIT.CSP_PSID = ?");
            sql.addSql("        )");
            sql.addSql("      or");
            sql.addSql("        (");
            sql.addSql("          CHT_SPACCESS_PERMIT.CSP_TYPE = ?");
            sql.addSql("        and");
            sql.addSql("          CHT_SPACCESS_PERMIT.CSP_PSID in (");
            sql.addSql("            select GRP_SID from CMN_BELONGM");
            sql.addSql("            where CMN_BELONGM.USR_SID = ?");
            sql.addSql("          )");
            sql.addSql("      or");
            sql.addSql("        (");
            sql.addSql("          CHT_SPACCESS_PERMIT.CSP_TYPE = ?");
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
            sql.addSql("                  CMN_POSITION.POS_SID = CHT_SPACCESS_PERMIT.CSP_PSID");
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
            sql.addSql("   CHT_SPACCESS.CHS_NAME like '%"
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
            sql.addSql("   CHS_NAME");
            sql.addSql(" from ");
            sql.addSql("   CHT_SPACCESS");
            sql.addSql(" where ");
            sql.addSql("   CHS_SID in (");
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
                ret.add(rs.getString("CHS_NAME"));
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
