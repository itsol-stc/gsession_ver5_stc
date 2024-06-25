package jp.groupsession.v2.anp.anp170;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 状況内容確認 結果状況ポップアップ画面のDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp170Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp170Dao.class);

    /**
     * <p>Default Constructor
     */
    public Anp170Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Anp170Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 安否確認データ件数チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param anpiSid 安否確認SID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int getHDataCount(int anpiSid)
                      throws SQLException {

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
            __setHListFromWhereSQL(sql, anpiSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

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
     * <br>[機  能]  結果状況 送信者一覧取得のSqlBufferにfrom, where句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param anpiSid 安否確認SID
     * @return SqlBuffer
     */
    private SqlBuffer __setHListFromWhereSQL(SqlBuffer sql, int anpiSid) {

        sql.addSql(" from");
        sql.addSql("   ANP_HDATA");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM");
        sql.addSql("   on ANP_HDATA.APH_HUID = CMN_USRM.USR_SID");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql("   on ANP_HDATA.APH_HUID = CMN_USRM_INF.USR_SID");

        sql.addSql(" where");
        sql.addSql("   ANP_HDATA.APH_SID = ?");
        sql.addIntValue(anpiSid);

        return sql;
    }

    /**
     * <br>[機  能] 指定したグループの結果状況一覧の表示数を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param anpiSid 安否確認SID
     * @param grpSid グループSID
     * @return 総表示数
     * @throws SQLException SQL実行例外
     */
    public int getJListCount(int anpiSid, int grpSid) throws SQLException {
        log__.debug("結果状況一覧表示数を取得");
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
            __setJListFromWhereSQL(sql, anpiSid, grpSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

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
     * <br>[機  能] 結果状況一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param anpiSid 安否確認SID
     * @param grpSid グループSID
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @param offset   レコードの読取開始行
     * @param limit    1ページの最大件数
     * @return 安否確認データ
     * @throws SQLException SQL実行例外
     */
    public List<Anp170DspModel> getJyokyoList(
            int anpiSid, int grpSid, int sortKey,
            int orderKey, int offset, int limit)
                                              throws SQLException {

        log__.debug("結果状況一覧を取得  anpiSid = " + anpiSid);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Anp170DspModel> ret = new ArrayList<Anp170DspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   ANP_PRI_CONF.APP_MAILADR,");
            sql.addSql("   ANP_JDATA.APD_RDATE,");
            sql.addSql("   ANP_JDATA.APD_JOKYO_FLG,");
            sql.addSql("   CMN_USRM.USR_JKBN");

            __setJListFromWhereSQL(sql, anpiSid, grpSid);

            //GSに設定された規定の並び順
            sql = __setListOrderSQL(sql, sortKey, orderKey);
            sql = AnpiCommonBiz.setUserOrderSQL(sql, con);

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();

            //表示開始行を設定
            if (offset > 1) {
                rs.absolute(offset - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                //取得データをモデルに設定
                ret.add(__getJyokyoDataFromRs(rs));
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
     * <br>[機  能] 結果状況一覧取得のSqlBufferにfrom, where句を設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sql     SqlBuffer
     * @param anpiSid 安否確認SID
     * @param grpSid グループSID
     * @return SqlBuffer
     */
    private SqlBuffer __setJListFromWhereSQL(SqlBuffer sql, int anpiSid, int grpSid) {
        sql.addSql(" from");
        sql.addSql("   CMN_USRM");
        sql.addSql(" left join");
        sql.addSql("   (select");
        sql.addSql("      CMN_USRM_INF.*,");
        sql.addSql("      (case");
        sql.addSql("         when CMN_USRM_INF.POS_SID = 0 then 1");
        sql.addSql("         else 0");
        sql.addSql("       end) as YAKUSYOKU_EXIST,");
        sql.addSql("      (case");
        sql.addSql("         when CMN_USRM_INF.POS_SID = 0 then 0");
        sql.addSql("         else CMN_POSITION.POS_SORT");
        sql.addSql("       end) as YAKUSYOKU_SORT");
        sql.addSql("    from");
        sql.addSql("       CMN_USRM_INF");
        sql.addSql("    left join");
        sql.addSql("       CMN_POSITION");
        sql.addSql("       on CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
        sql.addSql("    ) as CMN_USRM_INF");
        sql.addSql("   on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql(" inner join");
        sql.addSql("   ANP_JDATA");
        sql.addSql("   on ANP_JDATA.USR_SID = CMN_USRM_INF.USR_SID");

        sql.addSql(" left join");
        sql.addSql("   ANP_PRI_CONF");
        sql.addSql("   on ANP_PRI_CONF.USR_SID = ANP_JDATA.USR_SID");

        //グループ指定
        sql.addSql(" inner join");
        sql.addSql("   CMN_BELONGM");
        sql.addSql("   on CMN_USRM.USR_SID = CMN_BELONGM.USR_SID");
        sql.addSql("   and CMN_BELONGM.GRP_SID = ?");
        sql.addIntValue(grpSid);

        sql.addSql(" where");
        sql.addSql("   ANP_JDATA.APH_SID = ?");
        sql.addIntValue(anpiSid);

        return sql;
    }

    /**
     * <br>[機  能] 結果状況リスト内容を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rs ResultSet
     * @return created Anp010SenderModel
     * @throws SQLException SQL実行例外
     */
    private Anp170DspModel __getJyokyoDataFromRs(
            ResultSet rs) throws SQLException {
        Anp170DspModel bean = new Anp170DspModel();

        //ユーザSID
        bean.setAnp170UsrSid(rs.getInt("USR_SID"));
        //送信先
        bean.setAnp170NameTo(NullDefault.getString(rs.getString("USI_SEI"), "") + " "
                     + NullDefault.getString(rs.getString("USI_MEI"), ""));
        //メールアドレス
        bean.setAnp170MailAddress(NullDefault.getString(rs.getString("APP_MAILADR"), ""));
        //返信日時
        bean.setAnp170HensinDate(__getListDspDate(rs, "APD_RDATE"));
        //状態
        bean.setAnp170Jyokyo(rs.getInt("APD_JOKYO_FLG"));
        //ユーザ状態区分（非表示）
        bean.setAnp170JyotaiKbn(rs.getInt("USR_JKBN"));

        return bean;
    }

    /**
     * <br>[機  能] 結果状況一覧取得のSqlBufferにorder句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @return SqlBuffer
     */
    private SqlBuffer __setListOrderSQL(SqlBuffer sql, int sortKey, int orderKey) {

        //列クリックによるソート
        sql.addSql(" order by ");

        String order = "asc";
        if (orderKey == GSConst.ORDER_KEY_DESC) {
            order = "desc";
        }
        switch (sortKey) {
            case Anp170ParamModel.SORT_KEY_NAME:
                sql.addSql("   CMN_USRM_INF.USI_SEI " + order + ",");
                sql.addSql("   CMN_USRM_INF.USI_MEI " + order + ",");
                break;
            case Anp170ParamModel.SORT_KEY_ADDRESS:
                sql.addSql("   ANP_PRI_CONF.APP_MAILADR " + order + ",");
                break;
            case Anp170ParamModel.SORT_KEY_DATE:
                if (orderKey == GSConst.ORDER_KEY_DESC) {
                    sql.addSql("   ANP_JDATA.APD_RDATE is null,");
                } else {
                    sql.addSql("   ANP_JDATA.APD_RDATE is not null,");
                }
                sql.addSql("   ANP_JDATA.APD_RDATE " + order + ",");
                break;
            case Anp170ParamModel.SORT_KEY_JOKYO:
                sql.addSql("   ANP_JDATA.APD_JOKYO_FLG " + order + ",");
                break;
            default:
                sql.addSql("   ANP_JDATA.APD_RDATE is null,");
                sql.addSql("   ANP_JDATA.APD_RDATE desc,");
                break;
        }

        return sql;
    }

    /**
     * <br>[機  能] 一覧に表示する日付の書式を整えて戻す
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @param fieldName 列名
     * @return 表示日付
     * @throws SQLException SQL実行例外
     */
    private String __getListDspDate(ResultSet rs, String fieldName) throws SQLException {
        SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd HH:mm");

        if (rs.getDate(fieldName) == null) {
            return "-";
        } else {
            return dateformat.format(rs.getTimestamp(fieldName));
        }
    }
}