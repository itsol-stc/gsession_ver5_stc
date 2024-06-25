package jp.groupsession.v2.fil.fil070.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.fil.fil070.model.Fil070Model;
import jp.groupsession.v2.fil.util.FilStringUtil;

/**
 * <p>フォルダ詳細画面で使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Fil070Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil070Dao.class);

    /**
     * <p>Default Constructor
     */
    public Fil070Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil070Dao(Connection con) {
        super(con);
    }

    /**
     * <p>更新履歴一覧を取得する。
     * @param fdrSid ディレクトリSID
     * @param orderKey オーダーキー
     * @param sortKey ソートキー
     * @param start 検索開始位置
     * @param limit 最大表示件数
     * @param newVersion 最新バージョン数
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<Fil070Model> getRekiList(
            int fdrSid, int orderKey, int sortKey, int start, int limit, int newVersion)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Fil070Model> ret = new ArrayList<Fil070Model>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CMN_USRM.USR_SID,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME,");
            sql.addSql("   CMN_GROUPM.GRP_JKBN as GRP_JKBN,");
            sql.addSql("   REKI.FDR_SID,");
            sql.addSql("   REKI.FDR_VERSION,");
            sql.addSql("   REKI.FFR_FNAME,");
            sql.addSql("   REKI.FFR_KBN,");
            sql.addSql("   REKI.FFR_EUID,");
            sql.addSql("   REKI.FFR_EGID,");
            sql.addSql("   REKI.FFR_EDATE,");
            sql.addSql("   REKI.FFR_PARENT_SID,");
            sql.addSql("   REKI.FFR_UP_CMT,");
            sql.addSql("   REKI.BIN_SID,");
            sql.addSql("   REKI.FDR_TRADE_DATE,");
            sql.addSql("   REKI.FDR_TRADE_TARGET,");
            sql.addSql("   REKI.FDR_TRADE_MONEYKBN,");
            sql.addSql("   REKI.FDR_TRADE_MONEY,");
            sql.addSql("   MONEY.FMM_NAME");
            sql.addSql(" from ");
            sql.addSql("   (");
            sql.addSql("   select");
            sql.addSql("     FILE_FILE_REKI.FDR_SID,");
            sql.addSql("     FILE_FILE_REKI.FDR_VERSION,");
            sql.addSql("     FILE_FILE_REKI.FFR_FNAME,");
            sql.addSql("     FILE_FILE_REKI.FFR_KBN,");
            sql.addSql("     FILE_FILE_REKI.FFR_EUID,");
            sql.addSql("     FILE_FILE_REKI.FFR_EGID,");
            sql.addSql("     FILE_FILE_REKI.FFR_EDATE,");
            sql.addSql("     FILE_FILE_REKI.FFR_PARENT_SID,");
            sql.addSql("     FILE_FILE_REKI.FFR_UP_CMT,");
            sql.addSql("     CMN_BINF.BIN_SID,");
            sql.addSql("     FILE_FILE_REKI.FDR_TRADE_DATE,");
            sql.addSql("     FILE_FILE_REKI.FDR_TRADE_TARGET,");
            sql.addSql("     FILE_FILE_REKI.FDR_TRADE_MONEYKBN,");
            sql.addSql("     FILE_FILE_REKI.FDR_TRADE_MONEY,");
            sql.addSql("     FILE_FILE_REKI.EMT_SID");
            sql.addSql("   from");
            sql.addSql("     ((FILE_FILE_REKI left join FILE_FILE_BIN");
            sql.addSql("     on FILE_FILE_REKI.FDR_SID = FILE_FILE_BIN.FDR_SID");
            sql.addSql("     and FILE_FILE_REKI.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION)");
            sql.addSql("     left join CMN_BINF");
            sql.addSql("     on FILE_FILE_BIN.BIN_SID = CMN_BINF.BIN_SID)");
            sql.addSql("   ) as REKI");
            sql.addSql("   left join CMN_GROUPM");
            sql.addSql("     on REKI.FFR_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql(" left join");
            sql.addSql("   FILE_MONEY_MASTER MONEY");
            sql.addSql(" on MONEY.FMM_SID = REKI.EMT_SID,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   REKI.FDR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   REKI.FFR_EUID = CMN_USRM.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            //オーダーキー
            String order = "ASC";
            if (orderKey == GSConst.ORDER_KEY_DESC) {
                order = "DESC";
            }

            sql.addSql(" order by ");
            if (sortKey == 0) {
                //更新日時
                sql.addSql(" REKI.FFR_EDATE " + order);
                sql.addSql(" , REKI.FFR_FNAME ");
            } else if (sortKey == 1) {
                //更新者
                sql.addSql(" CMN_USRM_INF.USI_SEI_KN " + order);
                sql.addSql(" , CMN_USRM_INF.USI_MEI_KN, ");
                sql.addSql(" REKI.FFR_EDATE ");
            } else if (sortKey == 2) {
                //ファイル名
                sql.addSql(" REKI.FFR_FNAME " + order);
                sql.addSql(", REKI.FFR_EDATE ");
            } else if (sortKey == 3) {
                //操作
                sql.addSql(" REKI.FFR_KBN " + order);
                sql.addSql(", REKI.FFR_EDATE ");
            } else if (sortKey == 5) {
                //取引年月日
                sql.addSql(" REKI.FDR_TRADE_DATE " + order);
                sql.addSql(", REKI.FFR_EDATE ");
            } else if (sortKey == 6) {
                //取引先
                sql.addSql(" REKI.FDR_TRADE_TARGET " + order);
                sql.addSql(", REKI.FFR_EDATE ");
            } else if (sortKey == 7) {
                //取引金額
                sql.addSql(" REKI.FDR_TRADE_MONEY " + order);
                sql.addSql(", REKI.FDR_TRADE_MONEYKBN " + order);
                sql.addSql(", REKI.FFR_EDATE ");
            }

            sql.addIntValue(fdrSid);
            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (start > 1) {
                rs.absolute(start - 1);
            }

            for (int i = 0; rs.next() && i < limit; i++) {
                ret.add(__getFil070ModelFromRs(rs, newVersion));
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
     * <p>更新履歴一覧の件数を取得する。
     * @param fdrSid ディレクトリSID
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public int countRekiList(int fdrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from ");
            sql.addSql("   (");
            sql.addSql("   select");
            sql.addSql("   FILE_FILE_REKI.FDR_SID,");
            sql.addSql("   FILE_FILE_REKI.FFR_EUID");
            sql.addSql("   from");
            sql.addSql("     ((FILE_FILE_REKI left join FILE_FILE_BIN");
            sql.addSql("     on FILE_FILE_REKI.FDR_SID = FILE_FILE_BIN.FDR_SID");
            sql.addSql("     and FILE_FILE_REKI.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION)");
            sql.addSql("     left join CMN_BINF");
            sql.addSql("     on FILE_FILE_BIN.BIN_SID = CMN_BINF.BIN_SID)");
            sql.addSql("   ) as REKI,");

            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");
            sql.addSql(" where ");
            sql.addSql("   REKI.FDR_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   REKI.FFR_EUID = CMN_USRM.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");


            sql.addIntValue(fdrSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
     * <p>ResultSetをモデルに格納する。
     * @param rs ResultSet
     * @param newVersion 最新バージョン
     * @return created FileDirectoryModel
     * @throws SQLException SQL実行例外
     */
    private Fil070Model __getFil070ModelFromRs(ResultSet rs, int newVersion) throws SQLException {
        Fil070Model bean = new Fil070Model();
        bean.setUsrSid(rs.getInt("USR_SID"));

        if (rs.getInt("FFR_EGID") > 0) {
            bean.setUsrSeiMei(rs.getString("GRP_NAME"));
            bean.setUsrJkbn(rs.getInt("GRP_JKBN"));
        } else {
            bean.setUsrSeiMei(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
            bean.setUsrJkbn(rs.getInt("USR_JKBN"));
            bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        }

        bean.setFdrSid(rs.getInt("FDR_SID"));
        bean.setFfrVersion(rs.getInt("FDR_VERSION"));
        bean.setFfrName(rs.getString("FFR_FNAME"));
        bean.setFfrKbn(rs.getInt("FFR_KBN"));
        bean.setFfrEuid(rs.getInt("FFR_EUID"));
        bean.setFfrEgid(rs.getInt("FFR_EGID"));
        UDate edate = UDate.getInstanceTimestamp(rs.getTimestamp("FFR_EDATE"));
        bean.setFfrEdate(UDateUtil.getSlashYYMD(edate) + " " + UDateUtil.getSeparateHM(edate));
        bean.setFfrUpCmt(StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("FFR_UP_CMT")));
        bean.setBinSid(rs.getLong("BIN_SID"));
        String tradeDate = "";
        if (rs.getString("FDR_TRADE_DATE") != null) {
            tradeDate = rs.getString("FDR_TRADE_DATE").substring(0, 4)
                    + "/" + rs.getString("FDR_TRADE_DATE").substring(5, 7)
                    + "/" + rs.getString("FDR_TRADE_DATE").substring(8, 10);
        }
        bean.setFdrTradeDate(tradeDate);
        bean.setFdrTradeTarget(rs.getString("FDR_TRADE_TARGET"));
        bean.setFdrTradeMoneykbn(rs.getInt("FDR_TRADE_MONEYKBN"));
        String tradeMoney = "";
        if (rs.getString("FDR_TRADE_MONEY") != null) {
            tradeMoney = FilStringUtil.getDspErrlTradeMoney(
                    rs.getString("FDR_TRADE_MONEY")
                    );
        }
        bean.setFdrTradeMoney(tradeMoney);
        bean.setFmmName(rs.getString("FMM_NAME"));

        if (newVersion == rs.getInt("FDR_VERSION")) {
            bean.setNewVersionFlg(true);
        }
        return bean;
    }
}
