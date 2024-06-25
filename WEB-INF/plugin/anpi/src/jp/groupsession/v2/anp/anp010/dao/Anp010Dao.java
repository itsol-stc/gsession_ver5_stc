package jp.groupsession.v2.anp.anp010.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.anp.AnpiCommonBiz;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp010.Anp010ParamModel;
import jp.groupsession.v2.anp.anp010.model.Anp010SearchModel;
import jp.groupsession.v2.anp.anp010.model.Anp010SenderModel;
import jp.groupsession.v2.cmn.GSConst;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 安否状況一覧画面 DAOクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Anp010Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp010Dao.class);

    /**
     * <p>Default Constructor
     */
    public Anp010Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Anp010Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 送信者一覧の総表示数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件MODEL
     * @return 総表示数
     * @throws SQLException SQL実行例外
     */
    public int getListCount(Anp010SearchModel searchMdl) throws SQLException {

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
            __setListFromWhereSQL(sql, searchMdl);

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
     * <br>[機  能] 送信者リストを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param findKey 検索条件MODEL
     * @param sortKey ソートキー
     * @param orderKey オーダーキー
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return 送信者リスト
     * @throws SQLException SQL実行例外
     */
    public List<Anp010SenderModel> getListInfo(Anp010SearchModel findKey,
                                               int sortKey, int orderKey,
                                               int offset, int limit)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Anp010SenderModel> ret = new ArrayList<Anp010SenderModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ANP_JDATA.APH_SID,");
            sql.addSql("   ANP_JDATA.USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   ANP_JDATA.APD_HDATE,");
            sql.addSql("   ANP_JDATA.APD_RDATE,");
            sql.addSql("   ANP_JDATA.APD_JOKYO_FLG,");
            sql.addSql("   ANP_JDATA.APD_PLACE_FLG,");
            sql.addSql("   ANP_JDATA.APD_SYUSYA_FLG,");
            sql.addSql("   ANP_JDATA.APD_COMMENT,");
            sql.addSql("   ANP_JDATA.APD_HAISIN_FLG");

            sql = __setListFromWhereSQL(sql, findKey);
            sql = __setListOrderSQL(sql, sortKey, orderKey);
            sql = AnpiCommonBiz.setUserOrderSQL(sql, con);

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

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
     * <br>[機  能] 送信者リスト内容を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return created Anp010SenderModel
     * @throws SQLException SQL実行例外
     */
    private Anp010SenderModel __getSenderFromRs(ResultSet rs) throws SQLException {
        Anp010SenderModel bean = new Anp010SenderModel();

        bean.setUsrSid(rs.getString("USR_SID"));
        bean.setName(NullDefault.getString(rs.getString("USI_SEI"), "") + " "
                   + NullDefault.getString(rs.getString("USI_MEI"), ""));
        bean.setUsrJkbn(rs.getInt("USR_JKBN"));
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        bean.setHaisinDate(__getDspDate(UDate.getInstanceTimestamp(rs.getTimestamp("APD_HDATE"))));
        bean.setReplyDate(__getDspDate(UDate.getInstanceTimestamp(rs.getTimestamp("APD_RDATE"))));
        bean.setHaisinflg(rs.getInt("APD_HAISIN_FLG"));
        bean.setJokyoflg(rs.getString("APD_JOKYO_FLG"));
        bean.setPlaceflg(rs.getString("APD_PLACE_FLG"));
        bean.setSyusyaflg(rs.getString("APD_SYUSYA_FLG"));
        bean.setComment(StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(rs.getString("APD_COMMENT"), "")));

        return bean;
    }
    /**
     * <br>[機  能] 表示する日付の書式を整えて戻す
     * <br>[解  説]
     * <br>[備  考]
     * @param date 対象の日付
     * @return 表示日付
     * @throws SQLException SQL実行例外
     */
    private String __getDspDate(UDate date) throws SQLException {
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        if (date == null) {
            return "";
        }
        return dateformat.format(date.toJavaUtilDate());
    }

    /**
     * <br>[機  能] 送信者一覧取得のSqlBufferにfrom, order句を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件MODEL
     * @return SqlBuffer
     */
    private SqlBuffer __setListFromWhereSQL(SqlBuffer sql, Anp010SearchModel searchMdl) {

        sql.addSql(" from");
        sql.addSql("   ANP_JDATA");

        sql.addSql(" left join (");
        sql.addSql("   select CMN_USRM_INF.*,");
        sql.addSql("   (case");
        sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
        sql.addSql("      else 0");
        sql.addSql("    end) as YAKUSYOKU_EXIST,");
        sql.addSql("   (case");
        sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
        sql.addSql("      else CMN_POSITION.POS_SORT");
        sql.addSql("    end) as YAKUSYOKU_SORT");
        sql.addSql(" from");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql(" left join");
        sql.addSql("   CMN_POSITION on CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");
        sql.addSql(" ) as CMN_USRM_INF");
        sql.addSql("  on ANP_JDATA.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql(" left join CMN_USRM on CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID ");
        sql.addSql(" where");
        sql.addSql("   ANP_JDATA.APH_SID=?");
        sql.addIntValue(searchMdl.getAnpiSid());

        //詳細検索
        __createDetailSql(sql, searchMdl);

        //全て以外
        if (!AnpiCommonBiz.isChackAllGrp(searchMdl.getGpSid())) {

            //表示グループ
            sql.addSql(" and exists (");
            sql.addSql("select *");
            sql.addSql(" from");

            if (AnpiCommonBiz.isMyGroupSidforDisp(searchMdl.getGpSid())) {
                //マイグループ
                sql.addSql("   CMN_MY_GROUP_MS");
                sql.addSql(" where");
                sql.addSql("   CMN_MY_GROUP_MS.MGP_SID = ?");
                sql.addSql(" and");
                sql.addSql("   CMN_MY_GROUP_MS.MGM_SID = ANP_JDATA.USR_SID");
                sql.addIntValue(AnpiCommonBiz.getGroupSidfromDisp(searchMdl.getGpSid()));
            } else {
                //ユーザ所属グループ
                sql.addSql("   CMN_BELONGM");
                sql.addSql(" where");
                sql.addSql("   CMN_BELONGM.GRP_SID = ?");
                sql.addSql(" and");
                sql.addSql("   CMN_BELONGM.USR_SID = ANP_JDATA.USR_SID");
                sql.addIntValue(AnpiCommonBiz.getGroupSidfromDisp(searchMdl.getGpSid()));
            }

            sql.addSql(")");
        }


        return sql;
    }

    /**
     * <br>[機  能] 詳細検索の場合、検索条件からSQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sql SqlBuffer
     * @param searchMdl 検索条件
     * @return sql SqlBuffer
     */
    private SqlBuffer __createDetailSql(SqlBuffer sql, Anp010SearchModel searchMdl) {

        //配信状況
        if (searchMdl.getSendKbn() != GSConstAnpi.SEARCH_SENDKBN_ALL) {
            int value;
            sql.addSql(" and");
            sql.addSql("   ANP_JDATA.APD_HAISIN_FLG = ?");
            //エラー
            if (searchMdl.getSendKbn() == GSConstAnpi.SEARCH_SENDKBN_NO) {
                value = GSConstAnpi.HAISIN_FLG_ERROR;
            } else {
                //正常
                value = GSConstAnpi.HAISIN_FLG_OK;
            }
            sql.addIntValue(value);
        }

        //回答状況 未回答
        if (searchMdl.getAnswerKbn() == GSConstAnpi.SEARCH_ANSKBN_NO) {
            sql.addSql(" and");
            sql.addSql("   (ANP_JDATA.APD_JOKYO_FLG = ?");
            sql.addSql("    or");
            sql.addSql("   ANP_JDATA.APD_PLACE_FLG = ?");
            sql.addSql("    or");
            sql.addSql("   ANP_JDATA.APD_SYUSYA_FLG = ?");
            sql.addSql("   )");
            sql.addIntValue(GSConstAnpi.JOKYO_FLG_UNSET);
            sql.addIntValue(GSConstAnpi.PLACE_FLG_UNSET);
            sql.addIntValue(GSConstAnpi.SYUSYA_FLG_UNSET);
        } else if (searchMdl.getAnswerKbn() == GSConstAnpi.SEARCH_ANSKBN_OK) {
            //回答済み
            sql.addSql(" and");
            sql.addSql("   (ANP_JDATA.APD_JOKYO_FLG <> ?");
            sql.addSql("    and");
            sql.addSql("   ANP_JDATA.APD_PLACE_FLG <> ?");
            sql.addSql("    and");
            sql.addSql("   ANP_JDATA.APD_SYUSYA_FLG <> ?");
            sql.addSql("   )");
            sql.addIntValue(GSConstAnpi.JOKYO_FLG_UNSET);
            sql.addIntValue(GSConstAnpi.PLACE_FLG_UNSET);
            sql.addIntValue(GSConstAnpi.SYUSYA_FLG_UNSET);
        }

        //回答状況 未回答以外
        if (searchMdl.getAnswerKbn() != GSConstAnpi.SEARCH_ANSKBN_NO) {

            //安否状況
            if (searchMdl.getAnpKbn() != GSConstAnpi.SEARCH_ANPKBN_ALL) {
                int value;
                //無事
                if (searchMdl.getAnpKbn() == GSConstAnpi.SEARCH_ANPKBN_GOOD) {
                    value = GSConstAnpi.JOKYO_FLG_GOOD;
                } else if (searchMdl.getAnpKbn() == GSConstAnpi.SEARCH_ANPKBN_KEISYO) {
                    //軽症
                    value = GSConstAnpi.JOKYO_FLG_KEISYO;
                } else {
                    //重症
                    value = GSConstAnpi.JOKYO_FLG_JUSYO;
                }

                sql.addSql(" and");
                sql.addSql("   ANP_JDATA.APD_JOKYO_FLG = ?");
                sql.addIntValue(value);
            }

            //現在地
            if (searchMdl.getPlaceKbn() != GSConstAnpi.SEARCH_PLACEKBN_ALL) {
                int value;
                //自宅
                if (searchMdl.getPlaceKbn() == GSConstAnpi.SEARCH_PLACEKBN_HOUSE) {
                    value = GSConstAnpi.PLACE_FLG_HOUSE;
                } else if (searchMdl.getPlaceKbn() == GSConstAnpi.SEARCH_PLACEKBN_OFFICE) {
                    //会社
                    value = GSConstAnpi.PLACE_FLG_OFFICE;
                } else {
                    //外出先
                    value = GSConstAnpi.PLACE_FLG_OUT;
                }

                sql.addSql(" and");
                sql.addSql("   ANP_JDATA.APD_PLACE_FLG = ?");
                sql.addIntValue(value);
            }

            //出社状況
            if (searchMdl.getSyusyaKbn() != GSConstAnpi.SEARCH_SYUSYAKBN_ALL) {
                int value;
                //不可
                if (searchMdl.getSyusyaKbn() == GSConstAnpi.SEARCH_SYUSYAKBN_NO) {
                    value = GSConstAnpi.SYUSYA_FLG_NO;
                } else if (searchMdl.getSyusyaKbn() == GSConstAnpi.SEARCH_SYUSYAKBN_OK) {
                    //可能
                    value = GSConstAnpi.SYUSYA_FLG_OK;
                } else {
                    //出社済
                    value = GSConstAnpi.SYUSYA_FLG_OKD;
                }

                sql.addSql(" and");
                sql.addSql("   ANP_JDATA.APD_SYUSYA_FLG = ?");
                sql.addIntValue(value);
            }
        }
        return sql;
    }

    /**
     * <br>[機  能] 送信者一覧取得のSqlBufferにorder句を設定する
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
            case Anp010ParamModel.SORT_KEY_NAME:
                sql.addSql("   USI_SEI_KN " + order + ",");
                sql.addSql("   USI_MEI_KN " + order + ",");
                break;
            case Anp010ParamModel.SORT_KEY_HAISIN:
                sql.addSql("   APD_HAISIN_FLG " + order + ",");
                break;
            case Anp010ParamModel.SORT_KEY_REPLY:
                if (orderKey == GSConst.ORDER_KEY_DESC) {
                    sql.addSql("   ANP_JDATA.APD_RDATE is null,");
                } else {
                    sql.addSql("   ANP_JDATA.APD_RDATE is not null,");
                }
                sql.addSql("   ANP_JDATA.APD_RDATE " + order + ",");
                break;
            case Anp010ParamModel.SORT_KEY_JOKYO:
                sql.addSql("   APD_JOKYO_FLG " + order + ",");
                break;
            case Anp010ParamModel.SORT_KEY_PLACE:
                sql.addSql("   APD_PLACE_FLG " + order + ",");
                break;
            case Anp010ParamModel.SORT_KEY_SYUSYA:
                sql.addSql("   APD_SYUSYA_FLG " + order + ",");
                break;
            default:
                sql.addSql("   ANP_JDATA.APD_RDATE is null,");
                sql.addSql("   ANP_JDATA.APD_RDATE desc,");
                break;
        }

        return sql;
    }

    /**
     * <br>[機  能] 配信先ユーザが全て削除されているか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param aphSid 安否SID
     * @return 1:全て削除されている  0:されていない
     * @throws SQLException SQL実行時例外
     */
    public int checkAllDellFlg(int aphSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("   select ");
            sql.addSql("    case CNT1");
            sql.addSql("    when CNT2 then 1");
            sql.addSql("    else 0");
            sql.addSql("    end as ALL_DELL_FLG");
            sql.addSql("   from");
            sql.addSql("    (select");
            sql.addSql("      count(*) as CNT1");
            sql.addSql("    from");
            sql.addSql("      ANP_JDATA,");
            sql.addSql("      CMN_USRM");
            sql.addSql("    where");
            sql.addSql("      APH_SID = ?");
            sql.addSql("    and");
            sql.addSql("      ANP_JDATA.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("    and");
            sql.addSql("      CMN_USRM.USR_JKBN = ?) as CNT1,");
            sql.addSql("    (select");
            sql.addSql("     count(*) as CNT2");
            sql.addSql("    from");
            sql.addSql("      ANP_JDATA");
            sql.addSql("    where");
            sql.addSql("      APH_SID = ?) as CNT2");

            sql.addIntValue(aphSid);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(aphSid);


            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("ALL_DELL_FLG");
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
