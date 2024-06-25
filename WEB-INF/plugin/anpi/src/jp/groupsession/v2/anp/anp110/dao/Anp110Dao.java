package jp.groupsession.v2.anp.anp110.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp110.Anp110ParamModel;
import jp.groupsession.v2.anp.anp110.model.Anp110SearchModel;
import jp.groupsession.v2.anp.anp110.model.Anp110SenderModel;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 管理者設定・緊急連絡先設定状況画面 Data Access
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp110Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp110Dao.class);

    /**
     * <p>Default Constructor
     */
    public Anp110Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Anp110Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 緊急連絡先一覧の総表示数を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param findKey 検索条件MODEL
     * @return 総表示数
     * @throws SQLException SQL実行例外
     */
    public int getListCount(Anp110SearchModel findKey) throws SQLException {

        log__.debug("一覧総表示数を取得");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            __setListFromWhereSQL(sql, findKey);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("CNT");
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
     * <br>[機  能] 緊急連絡先リストを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param findKey  検索条件MODEL
     * @param sortKey  ソートキー
     * @param orderKey オーダーキー
     * @param offset   レコードの読取開始行
     * @param limit    1ページの最大件数
     * @return 緊急連絡先リスト
     * @throws SQLException SQL実行例外
     */
    public List<Anp110SenderModel> getListInfo(Anp110SearchModel findKey,
                                               int sortKey,
                                               int orderKey,
                                               int offset,
                                               int limit)
                                               throws SQLException {

        log__.debug("緊急連絡先リストを取得");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Anp110SenderModel> ret = new ArrayList<Anp110SenderModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM_INF.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM_INF.USR_UKO_FLG,");
            sql.addSql("   ANP_PRI_CONF.APP_MAILADR,");
            sql.addSql("   ANP_PRI_CONF.APP_TELNO,");

            sql.addSql("   (case");
            sql.addSql("     when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("     else (select");
            sql.addSql("               POS_NAME");
            sql.addSql("             from");
            sql.addSql("               CMN_POSITION");
            sql.addSql("             where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("     end) as USI_YAKUSYOKU,");
            sql.addSql("   (case");
            sql.addSql("     when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("     else 0");
            sql.addSql("     end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("     when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("     else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("   end) as YAKUSYOKU_SORT");

            sql = __setListFromWhereSQL(sql, findKey);
            sql = __setListOrderSQL(sql, sortKey, orderKey);
            //GSに設定された規定の並び順
            sql = AnpiCommonBiz.setUserOrderSQL(sql, con);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            //表示開始行を設定
            if (offset > 1) {
                rs.absolute(offset - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                ret.add(__getSenderFromRs(rs));
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
     * <br>[機  能]  送信者一覧取得のSqlBufferにfrom, where句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param findKey 検索条件MODEL
     * @return SqlBuffer
     */
    private SqlBuffer __setListFromWhereSQL(SqlBuffer sql, Anp110SearchModel findKey) {

        sql.addSql(" from");
        sql.addSql("   (select");
        sql.addSql("      CMN_USRM_INF.*,");
        sql.addSql("      CMN_USRM.USR_UKO_FLG,");
        sql.addSql("      (case");
        sql.addSql("         when CMN_USRM_INF.POS_SID = 0 then 1");
        sql.addSql("         else 0");
        sql.addSql("       end) as YAKUSYOKU_EXIST,");
        sql.addSql("      (case");
        sql.addSql("         when CMN_USRM_INF.POS_SID = 0 then 0");
        sql.addSql("         else CMN_POSITION.POS_SORT");
        sql.addSql("       end) as YAKUSYOKU_SORT");
        sql.addSql("    from");
        sql.addSql("       CMN_USRM");
        sql.addSql("    left join");
        sql.addSql("       CMN_USRM_INF");
        sql.addSql("       on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("    left join");
        sql.addSql("       CMN_POSITION");
        sql.addSql("       on CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
        sql.addSql("    where");
        sql.addSql("       CMN_USRM.USR_JKBN = ? ");
        sql.addSql("    and");
        sql.addSql("       CMN_USRM.USR_SID > 100 ");
        sql.addSql("    ) as CMN_USRM_INF");
        sql.addSql(" left join");
        sql.addSql("   ANP_PRI_CONF");
        sql.addSql("   on ANP_PRI_CONF.USR_SID = CMN_USRM_INF.USR_SID");

        //状態フラグ（有効なユーザのみ取得）
        sql.addIntValue(GSConst.JTKBN_TOROKU);

        //表示グループ
        if (!AnpiCommonBiz.isChackAllGrp(findKey.getGroupSid())) {
            //全てではない場合
            if (AnpiCommonBiz.isMyGroupSidforDisp(String.valueOf(findKey.getGroupSid()))) {
                //マイグループ
                sql.addSql(" where");
                sql.addSql("   CMN_USRM_INF.USR_SID in (");
                sql.addSql("select");
                sql.addSql("   CMN_MY_GROUP_MS.MGM_SID");
                sql.addSql(" from");
                sql.addSql("   CMN_MY_GROUP_MS");
                sql.addSql(" where");
                sql.addSql("   CMN_MY_GROUP_MS.MGP_SID = ?");
                sql.addIntValue(AnpiCommonBiz.getGroupSidfromDisp(findKey.getGroupSid()));
                sql.addSql(")");
            } else {
                //ユーザ所属グループ
                sql.addSql(" ,");
                sql.addSql("   CMN_BELONGM");
                sql.addSql(" where");
                sql.addSql("   CMN_BELONGM.GRP_SID = ?");
                sql.addSql(" and");
                sql.addSql("   CMN_USRM_INF.USR_SID = CMN_BELONGM.USR_SID");
                sql.addIntValue(AnpiCommonBiz.getGroupSidfromDisp(findKey.getGroupSid()));
            }
        } else {
            //全ての場合

            //フィルター メールアドレス または 電場番号が設定されている場合
            if ((findKey.getMailFlg() != GSConstAnpi.FILTER_FLG_ALL)
                    || (findKey.getTellFlg() != GSConstAnpi.FILTER_FLG_ALL)) {
                sql.addSql(" where");
            }
        }

        //グループ・マイグループ指定 且つ メールアドレスフィルタが全てでない場合
        if (!AnpiCommonBiz.isChackAllGrp(findKey.getGroupSid())
                && (findKey.getMailFlg() != GSConstAnpi.FILTER_FLG_ALL)) {
            sql.addSql(" and");
        }

        //フィルター メールアドレス
        if (findKey.getMailFlg() == GSConstAnpi.FILTER_FLG_REG) {
            sql.addSql("   (ANP_PRI_CONF.APP_MAILADR is not null");
            sql.addSql("   and");
            sql.addSql("   ANP_PRI_CONF.APP_MAILADR  <> '')");
        } else if (findKey.getMailFlg() == GSConstAnpi.FILTER_FLG_NONE) {
            sql.addSql("   (ANP_PRI_CONF.APP_MAILADR is null");
            sql.addSql("   or");
            sql.addSql("   ANP_PRI_CONF.APP_MAILADR = '')");
        }

        //グループ・マイグループ指定の場合
        if (!AnpiCommonBiz.isChackAllGrp(findKey.getGroupSid())) {
            //電話番号フィルターが全て出ない場合
            if (findKey.getTellFlg() != GSConstAnpi.FILTER_FLG_ALL) {
                sql.addSql(" and");
            }
        } else {
            //全てのグループ場合
            //メールアドレスフィルターが存在する場合 且つ 電話フィルターが存在する場合
            if ((findKey.getMailFlg() != GSConstAnpi.FILTER_FLG_ALL)
                    && (findKey.getTellFlg() != GSConstAnpi.FILTER_FLG_ALL)) {
                sql.addSql(" and");
            }
        }

        //フィルター 電話番号
        if (findKey.getTellFlg() == GSConstAnpi.FILTER_FLG_REG) {
            sql.addSql("   (ANP_PRI_CONF.APP_TELNO is not null");
            sql.addSql("   and");
            sql.addSql("   ANP_PRI_CONF.APP_TELNO <> '')");
        } else if (findKey.getTellFlg() == GSConstAnpi.FILTER_FLG_NONE) {
            sql.addSql("   (ANP_PRI_CONF.APP_TELNO is null");
            sql.addSql("   or");
            sql.addSql("   ANP_PRI_CONF.APP_TELNO ='')");
        }

        return sql;
    }

    /**
     * <br>[機  能] 緊急時連絡先一覧取得のSqlBufferにorder句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql      SqlBuffer
     * @param sortKey  ソートキー
     * @param orderKey オーダーキー
     * @return SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __setListOrderSQL(SqlBuffer sql,
                                        int sortKey,
                                        int orderKey)
                                 throws SQLException {

        String order = null;
        if (orderKey == GSConst.ORDER_KEY_ASC) {
            order = "asc";
        } else {
            order = "desc";
        }
        log__.debug("orderKey = " + order);

        //列クリックによるソート
        if (sortKey >= 0) {
            sql.addSql(" order by");

            switch (sortKey) {
            case Anp110ParamModel.SORT_KEY_SYAIN:
                sql.addSql("   case when USI_SYAIN_NO is null then ''");
                sql.addSql("   else USI_SYAIN_NO end " + order + ",");
                break;

            case Anp110ParamModel.SORT_KEY_NAME:
                sql.addSql("   USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order + ",");
                break;

            case Anp110ParamModel.SORT_KEY_POST:
                sql.addSql("   YAKUSYOKU_EXIST " + order + ",");
                sql.addSql("   YAKUSYOKU_SORT " + order + ",");
                break;

            case Anp110ParamModel.SORT_KEY_MAIL:
                sql.addSql("   APP_MAILADR " + order + ",");
                break;

            case Anp110ParamModel.SORT_KEY_TEL:
                sql.addSql("   APP_TELNO " + order + ",");
                break;

            default:
                break;
            }
        }

        return sql;
    }

    /**
     * <br>[機  能] 緊急時連絡先リスト内容を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rs ResultSet
     * @return created Anp010SenderModel
     * @throws SQLException SQL実行例外
     */
    private Anp110SenderModel __getSenderFromRs(ResultSet rs) throws SQLException {
        Anp110SenderModel bean = new Anp110SenderModel();

        //ユーザSID（非表示）
        bean.setUsrSid(rs.getInt("USR_SID"));
        //社員/職員番号
        bean.setSyainNo(rs.getString("USI_SYAIN_NO"));
        //氏名
        bean.setName(NullDefault.getString(rs.getString("USI_SEI"), "") + " "
                    + NullDefault.getString(rs.getString("USI_MEI"), ""));
        //役職
        bean.setPost(NullDefault.getString(rs.getString("USI_YAKUSYOKU"), ""));
        //メールアドレス
        bean.setMailAdr(NullDefault.getString(rs.getString("APP_MAILADR"), ""));
        //電話番号
        bean.setTelNo(NullDefault.getString(rs.getString("APP_TELNO"), ""));
        //ログイン停止フラグ
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        return bean;
    }
}
