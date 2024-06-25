package jp.groupsession.v2.fil.fil050.dao;

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
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.dao.FileFileBinDao;
import jp.groupsession.v2.fil.fil050.model.Fil050Model;
import jp.groupsession.v2.fil.util.FilStringUtil;

/**
 * <p>フォルダ詳細画面で使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Fil050Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil050Dao.class);

    /**
     * <p>Default Constructor
     */
    public Fil050Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil050Dao(Connection con) {
        super(con);
    }

    /**
     * <p>更新履歴一覧を取得する。
     * @param fdrSid ディレクトリSID
     * @param authUsrSid アクセス制限ユーザSID（特権ユーザの場合は、-1で指定）
     * @param orderKey オーダーキー
     * @param sortKey ソートキー
     * @param start 検索開始位置
     * @param limit 最大表示件数
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public List<Fil050Model> getRekiList(
            int fdrSid, int authUsrSid, int orderKey, int sortKey, int start, int limit)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Fil050Model> ret = new ArrayList<Fil050Model>();
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
            sql.addSql("   REKI.FDR_TRADE_DATE,");
            sql.addSql("   REKI.FDR_TRADE_TARGET,");
            sql.addSql("   REKI.FDR_TRADE_MONEYKBN,");
            sql.addSql("   REKI.FDR_TRADE_MONEY,");
            sql.addSql("   MONEY.FMM_NAME,");
            sql.addSql("   DIR.FDR_JTKBN,");
            sql.addSql("   DIR_MAXVERSION.MAXVERSION,");
            if (authUsrSid != -1) {
                sql.addSql("   coalesce(DACCESS.FDA_AUTH, ?) as ACKBN");
            } else {
                sql.addSql("   ? as ACKBN");
            }
            sql.addIntValue(Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));

            sql.addSql(" from");
            sql.addSql("   FILE_FILE_REKI REKI");
            sql.addSql(" inner join");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_FILE_REKI group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" on REKI.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" left join");
            sql.addSql("   FILE_DIRECTORY DIR");
            sql.addSql(" on DIR.FDR_SID = REKI.FDR_SID");
            sql.addSql(" and DIR.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");

            sql.addSql("   left join CMN_GROUPM");
            sql.addSql("     on REKI.FFR_EGID = CMN_GROUPM.GRP_SID");
            sql.addSql("   left join");
            sql.addSql("     FILE_MONEY_MASTER MONEY");
            sql.addSql("     on MONEY.FMM_SID = REKI.EMT_SID,");

            //閲覧が許可されていない場合は対象外とする
              if (authUsrSid != -1) {
                  sql.addSql("   ( ");
                  sql.addSql("   select");
                  sql.addSql("     ? as FDR_SID,");
                  sql.addSql("     ? as FDA_AUTH");
                  sql.addSql("   union all");
                  sql.addSql("   select");
                  sql.addSql("     A.FDR_SID,");
                  sql.addSql("     max(A.FDA_AUTH) FDA_AUTH");
                  sql.addSql("   from");
                  sql.addSql("     FILE_DACCESS_CONF A");
                  sql.addSql("   where");
                  sql.addSql("     exists (");
                  sql.addSql("       select *");
                  sql.addSql("       from");
                  sql.addSql("         FILE_DIRECTORY D,");
                  sql.addSql("         FILE_FILE_REKI R");
                  sql.addSql("       where");
                  sql.addSql("         D.FDR_ACCESS_SID = A.FDR_SID");
                  sql.addSql("       and");
                  sql.addSql("         D.FDR_SID = R.FDR_SID");
                  sql.addSql("       and");
                  sql.addSql("         R.FFR_PARENT_SID = ?)");
                  sql.addSql("   and (");
                  sql.addSql("     (A.USR_KBN = ? and");
                  sql.addSql("      A.USR_SID = ?) or");
                  sql.addSql("     (A.USR_KBN = ? and");
                  sql.addSql("      exists");
                  sql.addSql("      (select *");
                  sql.addSql("         from CMN_BELONGM B");
                  sql.addSql("        where B.GRP_SID = A.USR_SID");
                  sql.addSql("          and B.USR_SID = ?");
                  sql.addSql("      )))");
                  sql.addSql("   group by");
                  sql.addSql("     A.FDR_SID");
                  sql.addSql("   ) DACCESS,");
                  sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
                  sql.addIntValue(Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE));
                  sql.addIntValue(fdrSid);
                  sql.addIntValue(GSConstFile.USER_KBN_USER);
                  sql.addIntValue(authUsrSid);
                  sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                  sql.addIntValue(authUsrSid);
              }

            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");

            sql.addSql(" where ");
            sql.addSql("   REKI.FFR_PARENT_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   REKI.FFR_EUID = CMN_USRM.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addIntValue(fdrSid);

            if (authUsrSid != -1) {
                sql.addSql(" and");
                sql.addSql("   DIR.FDR_ACCESS_SID = DACCESS.FDR_SID");
            }

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

            sql.setPagingValue(start - 1, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                int dirSid = rs.getInt("FDR_SID");
                int version = rs.getInt("FDR_VERSION");

                Fil050Model bean = new Fil050Model();
                bean.setUsrSid(rs.getInt("USR_SID"));
                if (rs.getInt("FFR_EGID") > 0) {
                    bean.setUsrSeiMei(rs.getString("GRP_NAME"));
                    bean.setUsrJkbn(rs.getInt("GRP_JKBN"));
                } else {
                    bean.setUsrSeiMei(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    bean.setUsrJkbn(rs.getInt("USR_JKBN"));
                    bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                }
                bean.setFdrSid(dirSid);
                bean.setFfrVersion(version);
                bean.setFfrName(rs.getString("FFR_FNAME"));
                bean.setFfrKbn(rs.getInt("FFR_KBN"));
                bean.setFfrEuid(rs.getInt("FFR_EUID"));
                UDate edate = UDate.getInstanceTimestamp(rs.getTimestamp("FFR_EDATE"));
                bean.setFfrEdate(UDateUtil.getSlashYYMD(edate)
                        + " " + UDateUtil.getSeparateHM(edate));
                bean.setFdrJtkbn(rs.getInt("FDR_JTKBN"));
                bean.setFfrUpCmt(
                        StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("FFR_UP_CMT")));
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

                //復旧ボタンの表示フラグを設定
                if (rs.getInt("ACKBN") == Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE)) {
                    int maxversion = rs.getInt("MAXVERSION");

                    if (bean.getFdrJtkbn() == GSConstFile.JTKBN_NORMAL) {
                        if (version == maxversion
                                && bean.getFfrKbn() != GSConstFile.REKI_KBN_DELETE) {
                            //最新バージョン
                            bean.setRepairBtnDspFlg(false);
                        } else {
                            bean.setRepairBtnDspFlg(true);
                        }
                    } else {
                        if (version == maxversion
                                && bean.getFfrKbn() == GSConstFile.REKI_KBN_DELETE) {
                            //最新バージョン
                            bean.setRepairBtnDspFlg(true);
                        } else {
                            bean.setRepairBtnDspFlg(false);
                        }
                    }
                } else {
                    //編集許可がされていない場合は、復旧ボタンは表示しない
                    bean.setRepairBtnDspFlg(false);
                }

                ret.add(bean);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }


        //バイナリSIDを取得
        FileFileBinDao binDao = new FileFileBinDao(con);
        if (ret != null && ret.size() > 0) {
            for (Fil050Model mdl : ret) {
                mdl.setBinSid(binDao.getCmnBinSid(mdl.getFdrSid(), mdl.getFfrVersion()));

                //ファイルの実体がない場合は、復旧ボタンは表示しない
                if (mdl.getBinSid() < 1) {
                    mdl.setRepairBtnDspFlg(false);
                }
            }

        }

        return ret;
    }

    /**
     * <p>更新履歴一覧の件数を取得する。
     * @param fdrSid ディレクトリSID
     * @param authUsrSid アクセス制限ユーザSID（特権ユーザの場合は、-1で指定）
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public int countRekiList(int fdrSid, int authUsrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_FILE_REKI REKI");
            sql.addSql(" inner join");
            sql.addSql("   (select FDR_SID, max(FDR_VERSION) as MAXVERSION");
            sql.addSql("    from FILE_FILE_REKI group by FDR_SID) DIR_MAXVERSION");
            sql.addSql(" on REKI.FDR_SID = DIR_MAXVERSION.FDR_SID");
            sql.addSql(" left join");
            sql.addSql("   FILE_DIRECTORY DIR");
            sql.addSql(" on DIR.FDR_SID = REKI.FDR_SID");
            sql.addSql(" and DIR.FDR_VERSION = DIR_MAXVERSION.MAXVERSION");

            sql.addSql(" where");
            sql.addSql("   REKI.FFR_PARENT_SID = ?");
            sql.addIntValue(fdrSid);

            //閲覧が許可されていない場合は対象外とする
            if (authUsrSid != -1) {
                sql.addSql(" and (");
                sql.addSql("   DIR.FDR_ACCESS_SID = ?");
                sql.addSql(" or exists");
                sql.addSql("   (select *");
                sql.addSql("    from");
                sql.addSql("      FILE_DACCESS_CONF A");
                sql.addSql("    where");
                sql.addSql("      A.FDR_SID = DIR.FDR_ACCESS_SID");
                sql.addSql("    and (");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       A.USR_SID = ?) or");
                sql.addSql("      (A.USR_KBN = ? and");
                sql.addSql("       exists");
                sql.addSql("         (select *");
                sql.addSql("          from");
                sql.addSql("            CMN_BELONGM B");
                sql.addSql("          where");
                sql.addSql("            B.GRP_SID = A.USR_SID");
                sql.addSql("          and");
                sql.addSql("            B.USR_SID = ?");
                sql.addSql("         )))");
                sql.addSql("   ))");
                sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
                sql.addIntValue(GSConstFile.USER_KBN_USER);
                sql.addIntValue(authUsrSid);
                sql.addIntValue(GSConstFile.USER_KBN_GROUP);
                sql.addIntValue(authUsrSid);
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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

}
