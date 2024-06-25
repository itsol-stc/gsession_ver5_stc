package jp.groupsession.v2.anp.anp130.dao;

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
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp130.model.Anp130SenderModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <br>[機  能] 管理者設定・配信履歴画面 Data Access
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp130Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Anp130Dao.class);

    /**
     * <p>Default Constructor
     */
    public Anp130Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Anp130Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 緊急連絡先一覧の総表示数を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return 総表示数
     * @throws SQLException SQL実行例外
     */
    public int getListCount() throws SQLException {

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
            __setListFromWhereSQL(sql);

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
     * <br>[機  能] 緊急連絡先リストを取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param offset   レコードの読取開始行
     * @param limit    1ページの最大件数
     * @return 配信履歴リスト
     * @throws SQLException SQL実行例外
     */
    public List<Anp130SenderModel> getListInfo(int offset,
                                               int limit)
                                               throws SQLException {

        log__.debug("緊急連絡先リストを取得");
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Anp130SenderModel> ret = new ArrayList<Anp130SenderModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ANP_HDATA.APH_SID,");
            sql.addSql("   ANP_HDATA.APH_SUBJECT,");
            sql.addSql("   ANP_HDATA.APH_KNREN_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   ANP_HDATA.APH_HDATE,");
            sql.addSql("   ANP_HDATA.APH_EDATE,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   CMN_USRM.USR_JKBN");

            __setListFromWhereSQL(sql);

            sql.addSql(" order by");
            sql.addSql("   ANP_HDATA.APH_HDATE desc,");
            sql.addSql("   ANP_HDATA.APH_EDATE desc");

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
     * @return SqlBuffer
     */
    private SqlBuffer __setListFromWhereSQL(SqlBuffer sql) {

        sql.addSql(" from");
        sql.addSql("   ANP_HDATA");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM");
        sql.addSql("   on ANP_HDATA.APH_HUID = CMN_USRM.USR_SID");
        sql.addSql(" left join");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql("   on ANP_HDATA.APH_HUID = CMN_USRM_INF.USR_SID");
        sql.addSql(" where");
        sql.addSql("   ANP_HDATA.APH_END_FLG = ?");

        //安否確認完了データのみ取得
        sql.addIntValue(GSConstAnpi.ANPI_END_FLG);

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
    private Anp130SenderModel __getSenderFromRs(ResultSet rs) throws SQLException {
        Anp130SenderModel bean = new Anp130SenderModel();

        //安否確認SID（非表示）
        bean.setAnpiSid(rs.getInt("APH_SID"));
        //件名
        bean.setSubject(rs.getString("APH_SUBJECT"));
        //訓練モードフラグ
        bean.setKnrenFlg(rs.getInt("APH_KNREN_FLG"));
        //氏名
        bean.setName(NullDefault.getString(rs.getString("USI_SEI"), "") + " "
                    + NullDefault.getString(rs.getString("USI_MEI"), ""));
        //配信日時
        bean.setHaisinDate(__getListDspDate(rs, "APH_HDATE"));
        //完了日時
        bean.setKanryoDate(__getListDspDate(rs, "APH_EDATE"));
        //状態区分（非表示）
        bean.setJyotaiKbn(rs.getInt("USR_JKBN"));
        //ログイン停止フラグ
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));

        return bean;
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
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        if (rs.getDate(fieldName) == null) {
            return "-";
        } else {
            return dateformat.format(rs.getTimestamp(fieldName));
        }
    }
}
