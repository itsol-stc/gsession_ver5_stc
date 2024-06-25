package jp.groupsession.v2.fil.fil020.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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
import jp.groupsession.v2.fil.fil020.model.FileAccessUserModel;
import jp.groupsession.v2.fil.fil020.model.FileHistoryModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.util.FilStringUtil;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>キャビネット詳細画面で使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class Fil020Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil020Dao.class);

    /**
     * <p>Default Constructor
     */
    public Fil020Dao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Fil020Dao(Connection con) {
        super(con);
    }

    /**
     * <p>更新履歴一覧の件数を取得する。
     * @param fdrSid ディレクトリSID
     * @param authUsrSid アクセス制限ユーザSID（特権ユーザの場合は、-1で指定）
     * @return 履歴件数
     * @throws SQLException SQL実行例外
     */
    public int getRekiCount(int fdrSid, int authUsrSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

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
     * <p>更新履歴一覧を取得する。
     * @param fdrSid ディレクトリSID
     * @param orderKey オーダーキー
     * @param sortKey ソートキー
     * @param start 取得開始位置
     * @param limit 取得件数(上限値)
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileHistoryModel> getRekiList(int fdrSid, int orderKey, int sortKey,
            int start, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileHistoryModel> ret = new ArrayList<FileHistoryModel>();
        HashMap<Integer, Integer> newVerMap = new HashMap<Integer, Integer>();
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
            sql.addSql("   FILE_FILE_REKI.FDR_SID,");
            sql.addSql("   FILE_FILE_REKI.FDR_VERSION,");
            sql.addSql("   FILE_FILE_REKI.FFR_FNAME,");
            sql.addSql("   FILE_FILE_REKI.FFR_KBN,");
            sql.addSql("   FILE_FILE_REKI.FFR_EUID,");
            sql.addSql("   FILE_FILE_REKI.FFR_EDATE,");
            sql.addSql("   FILE_FILE_REKI.FFR_PARENT_SID,");
            sql.addSql("   FILE_FILE_BIN.BIN_SID");

            sql.addSql(" from ");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql("   left join");
            sql.addSql("     FILE_FILE_BIN");
            sql.addSql("   on");
            sql.addSql("     FILE_DIRECTORY.FDR_SID = FILE_FILE_BIN.FDR_SID");
            sql.addSql("   and");
            sql.addSql("     FILE_DIRECTORY.FDR_VERSION = FILE_FILE_BIN.FDR_VERSION,");

            sql.addSql("   FILE_FILE_REKI,");
            sql.addSql("   CMN_USRM_INF,");
            sql.addSql("   CMN_USRM");

            sql.addSql(" where ");
            sql.addSql("   FILE_FILE_REKI.FFR_PARENT_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   FILE_FILE_REKI.FDR_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql(" and ");
            sql.addSql("   FILE_FILE_REKI.FDR_VERSION = FILE_DIRECTORY.FDR_VERSION");
            sql.addSql(" and ");
            sql.addSql("   FILE_FILE_REKI.FFR_EUID = CMN_USRM.USR_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            //オーダーキー
            String order = "ASC";
            if (orderKey == GSConst.ORDER_KEY_DESC) {
                order = "DESC";
            }

            sql.addSql(" order by ");
            if (sortKey == 1) {
                //更新日時
                sql.addSql(" FILE_FILE_REKI.FFR_EDATE　" + order);
                sql.addSql(" , FILE_FILE_REKI.FFR_FNAME ");
            } else if (sortKey == 2) {
                //更新者
                sql.addSql(" CMN_USRM_INF.USI_SEI_KN " + order);
                sql.addSql(" , CMN_USRM_INF.USI_MEI_KN, ");
                sql.addSql(" FILE_FILE_REKI.FFR_EDATE ");
            } else if (sortKey == 3) {
                //ファイル名
                sql.addSql(" FILE_FILE_REKI.FFR_FNAME " + order);
                sql.addSql(", FILE_FILE_REKI.FFR_EDATE ");
            } else if (sortKey == 4) {
                //操作
                sql.addSql(" FILE_FILE_REKI.FFR_KBN " + order);
                sql.addSql(", FILE_FILE_REKI.FFR_EDATE ");
            }

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            sql.addIntValue(fdrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (start > 1) {
                rs.absolute(start - 1);
            }

            int newVersion = 0;
            for (int i = 0; rs.next() && i < limit; i++) {

                int dirSid = rs.getInt("FDR_SID");
                int version = rs.getInt("FDR_VERSION");

                FileHistoryModel bean = new FileHistoryModel();
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setUsrJkbn(rs.getInt("USR_JKBN"));
                bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                bean.setUsrSeiMei(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                bean.setFdrSid(rs.getInt("FDR_SID"));
                bean.setFfrVersion(rs.getInt("FDR_VERSION"));
                bean.setFfrName(rs.getString("FFR_FNAME"));
                bean.setFfrKbn(rs.getInt("FFR_KBN"));
                bean.setFfrEuid(rs.getInt("FFR_EUID"));
                UDate edate = UDate.getInstanceTimestamp(rs.getTimestamp("FFR_EDATE"));
                bean.setFfrEdate(UDateUtil.getSlashYYMD(edate)
                        + " " + UDateUtil.getSeparateHM(edate));
                bean.setBinSid(rs.getLong("BIN_SID"));

                if (newVerMap.containsKey(dirSid)) {
                    newVersion = newVerMap.get(dirSid);
                    if (newVersion < version) {
                        newVerMap.remove(dirSid);
                        newVerMap.put(dirSid, version);
                    }
                } else {
                    newVerMap.put(dirSid, version);
                }

                ret.add(bean);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        //復旧ボタンの表示フラグを設定する
        ret = __setRepairBtnDsp(ret, newVerMap);

        return ret;
    }

    /**
     * <p>更新履歴一覧を取得する。(ファイルの実体がないものも取得する。)
     * @param fdrSid ディレクトリSID
     * @param authUsrSid アクセス制限ユーザSID（特権ユーザの場合は、-1で指定）
     * @param orderKey オーダーキー
     * @param sortKey ソートキー
     * @param start 取得開始位置
     * @param limit 取得件数(上限値)
     * @return List in FILE_DIRECTORYModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileHistoryModel> getRekiListAll(int fdrSid, int authUsrSid,
            int orderKey, int sortKey, int start, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileHistoryModel> ret = new ArrayList<FileHistoryModel>();
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
            sql.addSql("   CMN_GROUPM.GRP_NAME,");
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
            sql.addSql(" left join");
            sql.addSql("   FILE_MONEY_MASTER MONEY");
            sql.addSql(" on MONEY.FMM_SID = REKI.EMT_SID,");

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
            if (sortKey == 1) {
                //更新日時
                sql.addSql(" REKI.FFR_EDATE " + order);
                sql.addSql(" , REKI.FFR_FNAME ");
            } else if (sortKey == 2) {
                //更新者
                sql.addSql(" CMN_USRM_INF.USI_SEI_KN " + order);
                sql.addSql(" , CMN_USRM_INF.USI_MEI_KN, ");
                sql.addSql(" REKI.FFR_EDATE ");
            } else if (sortKey == 3) {
                //ファイル名
                sql.addSql(" REKI.FFR_FNAME " + order);
                sql.addSql(", REKI.FFR_EDATE ");
            } else if (sortKey == 4) {
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

            //ページング
            sql.setPagingValue(start - 1, limit);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                FileHistoryModel bean = new FileHistoryModel();
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setUsrJkbn(rs.getInt("USR_JKBN"));
                if (rs.getInt("FFR_EGID") > 0) {
                    bean.setUsrSeiMei(rs.getString("GRP_NAME"));
                } else {
                    bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                    bean.setUsrSeiMei(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                }
                bean.setFdrSid(rs.getInt("FDR_SID"));
                bean.setFfrVersion(rs.getInt("FDR_VERSION"));
                bean.setFfrName(rs.getString("FFR_FNAME"));
                bean.setFfrKbn(rs.getInt("FFR_KBN"));
                bean.setFfrUpCmt(
                        StringUtilHtml.transToHTmlPlusAmparsant(rs.getString("FFR_UP_CMT")));
                bean.setFfrEuid(rs.getInt("FFR_EUID"));
                bean.setFfrEgid(rs.getInt("FFR_EGID"));
                UDate edate = UDate.getInstanceTimestamp(rs.getTimestamp("FFR_EDATE"));
                bean.setFfrEdate(UDateUtil.getSlashYYMD(edate)
                        + " " + UDateUtil.getSeparateHM(edate));
                bean.setFdrJtkbn(rs.getInt("FDR_JTKBN"));
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

                //編集許可がされていない場合は、復旧ボタンの表示しない
                if (rs.getInt("ACKBN") == Integer.parseInt(GSConstFile.ACCESS_KBN_WRITE)) {
                    int maxversion = rs.getInt("MAXVERSION");
                    bean.setRepairBtnDspFlg(__getRepairBtnDsp(bean, maxversion));
                } else {
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

        //バイナリSIDを取得する。
        FileFileBinDao binDao = new FileFileBinDao(con);
        if (ret != null && ret.size() > 0) {
            for (FileHistoryModel model : ret) {
                model.setBinSid(binDao.getCmnBinSid(model.getFdrSid(), model.getFfrVersion()));

                //ファイルの実体がない場合は、復旧ボタンは表示しない
                if (model.getBinSid() < 1) {
                    model.setRepairBtnDspFlg(false);
                }
            }
        }

        return ret;
    }

    /**
     * <p>更新履歴一覧の復旧ボタンの表示フラグを設定する。
     * @param rekiList 更新履歴一覧
     * @param newVerMap 最新バージョンマップ
     * @return List in Fil050Model
     * @throws SQLException SQL実行例外
     */
    private ArrayList<FileHistoryModel> __setRepairBtnDsp(
            ArrayList<FileHistoryModel> rekiList, HashMap<Integer, Integer> newVerMap)
    throws SQLException {

        int version = -1;
        for (FileHistoryModel model : rekiList) {
            if (model.getBinSid() < 1) {
                model.setRepairBtnDspFlg(false);
                continue;
            }

            version = newVerMap.get(model.getFdrSid());
            model.setRepairBtnDspFlg(__getRepairBtnDsp(model, version));
        }

        return rekiList;
    }

    /**
     * <p>更新履歴の復旧ボタンの表示フラグを取得する。
     * @param rekibean 更新履歴情報
     * @param newversion 最新バージョン
     * @return List in Fil050Model
     * @throws SQLException SQL実行例外
     */
    private boolean __getRepairBtnDsp(
            FileHistoryModel rekibean, int newversion)
    throws SQLException {

        if (rekibean.getFdrJtkbn() == GSConstFile.JTKBN_NORMAL) {

            if (newversion == rekibean.getFfrVersion()
                    && rekibean.getFfrKbn() != GSConstFile.REKI_KBN_DELETE) {
                //最新バージョン
                return false;
            } else {
                return true;
            }

        } else {
            if (newversion == rekibean.getFfrVersion()
                    && rekibean.getFfrKbn() == GSConstFile.REKI_KBN_DELETE) {
                //最新バージョン
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * <p>キャビネット詳細で使用する、アクセス制御情報の件数を取得する。
     * @param cabMdl キャビネットデータ
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getDirectoryAccessList(FileCabinetModel cabMdl) throws SQLException {
        if (cabMdl == null) {
            return null;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //キャビネットSIDからアクセス制御一覧取得
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   case when FILE_DACCESS_CONF.USR_KBN = ?");
            sql.addSql("        then FILE_DACCESS_CONF.USR_SID");
            sql.addSql("        else CMN_BELONGM.USR_SID");
            sql.addSql("        end as USID");
            sql.addSql(" from (");
            sql.addSql("   select");
            sql.addSql("     FILE_DACCESS_CONF.USR_SID,");
            sql.addSql("     FILE_DACCESS_CONF.USR_KBN,");
            sql.addSql("     FILE_DACCESS_CONF.FDR_SID,");
            sql.addSql("     FILE_DACCESS_CONF.FDA_AUTH");
            sql.addSql("   from");
            sql.addSql("     FILE_DACCESS_CONF");
            sql.addSql("   left join");
            sql.addSql("     FILE_DIRECTORY");
            sql.addSql("   on FILE_DACCESS_CONF.FDR_SID = FILE_DIRECTORY.FDR_SID");
            sql.addSql("   where");
            sql.addSql("     FILE_DIRECTORY.FCB_SID = ?");
            sql.addSql(" ) as FILE_DACCESS_CONF");
            sql.addSql(" left join");
            sql.addSql("   CMN_BELONGM");
            sql.addSql(" on CMN_BELONGM.GRP_SID = FILE_DACCESS_CONF.USR_SID");
            sql.addSql(" group by USID");
            sql.addIntValue(GSConstFile.USER_KBN_USER);
            sql.addIntValue(cabMdl.getFcbSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(Integer.valueOf(rs.getInt("USID")));
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
     * <p>キャビネット詳細で使用する、アクセス制御情報を取得する。
     * @param cabMdl キャビネットデータ
     * @param sortKey ソート項目
     * @param orderKey ソートオーダー
     * @param start 取得開始位置
     * @param limit 取得件数(上限値)
     * @param addList 追加リスト(個人キャビネットのサブディレクトリ用)
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<FileAccessUserModel> getAccessList(FileCabinetModel cabMdl, int sortKey,
            int orderKey, int start, int limit, List<Integer> addList) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileAccessUserModel> ret = new ArrayList<FileAccessUserModel>();
        con = getCon();

        try {

            //キャビネットSIDからアクセス制御一覧取得
            SqlBuffer sql = __getUserAccessSql(cabMdl, sortKey, orderKey, false, addList);
            sql.addSql(" limit " + limit + " offset " + (start - 1));
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getFileAccessUserModelFromRs(rs));
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
     * <p>キャビネット詳細で使用する、アクセス制御情報の件数を取得する。
     * @param cabMdl キャビネットデータ
     * @param addList 追加リスト(個人キャビネットのサブディレクトリ用)
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    public int getAccessListCount(FileCabinetModel cabMdl, List<Integer> addList)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            SqlBuffer sql = __getUserAccessSql(cabMdl, 0, 0, true, addList);
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("USR_COUNT");
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
     * <p>キャビネットに対するアクセス権限制御用一覧を取得するSQLを取得する。
     * @param cabMdl キャビネットデータ
     * @param sortKey ソート項目
     * @param orderKey ソートオーダー
     * @param countFlg カウント用フラグ
     * @param addList 追加リスト(個人キャビネットのサブディレクトリ用)
     * @return 検索にヒットしたユーザデータ CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __getUserAccessSql(FileCabinetModel cabMdl,
            int sortKey, int orderKey, boolean countFlg, List<Integer> addList)
                    throws SQLException {

        int fcbSid      = -1;
        int personalFlg = GSConstFile.CABINET_KBN_PUBLIC;
        if (cabMdl != null) {
            fcbSid      = cabMdl.getFcbSid();
            personalFlg = cabMdl.getFcbPersonalFlg();
        }

        //SQL文
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        if (countFlg) {
            sql.addSql("   count(ACCESS.USR_SID) as USR_COUNT");
        } else {
            sql.addSql("   ACCESS.USR_SID         as USR_SID,");
            sql.addSql("   ACCESS.USI_SEI         as USI_SEI,");
            sql.addSql("   ACCESS.USI_MEI         as USI_MEI,");
            sql.addSql("   ACCESS.USI_SEI_KN      as USI_SEI_KN,");
            sql.addSql("   ACCESS.USI_MEI_KN      as USI_MEI_KN,");
            sql.addSql("   ACCESS.USI_SYAIN_NO    as USI_SYAIN_NO,");
            sql.addSql("   ACCESS.USI_YAKUSYOKU   as USI_YAKUSYOKU,");
            sql.addSql("   ACCESS.USR_UKO_FLG     as USR_UKO_FLG,");
            sql.addSql("   ACCESS.YAKUSYOKU_EXIST as YAKUSYOKU_EXIST,");
            sql.addSql("   ACCESS.YAKUSYOKU_SORT  as YAKUSYOKU_SORT,");
            sql.addSql("   (case");
            sql.addSql("      when ACCESS.ACKBN <> -9");
            sql.addSql("      then ACCESS.ACKBN");
            sql.addSql("      else 0");
            sql.addSql("    end) as ACKBN,");
            sql.addSql("   ACCESS.ADMKBN          as ADMKBN");
        }
        sql.addSql(" from (");
        sql.addSql(" select");
        sql.addSql("   CMN_USRM_INF.USR_SID       as USR_SID,");
        sql.addSql("   CMN_USRM_INF.USI_SEI       as USI_SEI,");
        sql.addSql("   CMN_USRM_INF.USI_MEI       as USI_MEI,");
        sql.addSql("   CMN_USRM_INF.USI_SEI_KN    as USI_SEI_KN,");
        sql.addSql("   CMN_USRM_INF.USI_MEI_KN    as USI_MEI_KN,");
        sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO  as USI_SYAIN_NO,");
        sql.addSql("   (case");
        sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
        sql.addSql("      else CMN_POSITION.POS_NAME");
        sql.addSql("    end) as USI_YAKUSYOKU,");
        sql.addSql("   USRM.USR_UKO_FLG          as USR_UKO_FLG,");
        sql.addSql("   (case");
        sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
        sql.addSql("      else 0");
        sql.addSql("    end) as YAKUSYOKU_EXIST,");
        sql.addSql("   (case");
        sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
        sql.addSql("      else CMN_POSITION.POS_SORT");
        sql.addSql("    end) as YAKUSYOKU_SORT,");

        if (personalFlg == GSConstFile.CABINET_KBN_PRIVATE) {
            sql.addSql("   (case");
            sql.addSql("      when FILE_CABINET.FCB_OWNER_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("      then 1");
            sql.addSql("      when FILE_CABINET.FCB_ACCESS_KBN <> 0");
            sql.addSql("      and FILE_CABINET.FCB_PERSONAL_FLG = 1");
            sql.addSql("      and FILE_ACCESS_CONF.FCB_SID is not null");
            sql.addSql("      and FILE_ACCESS_CONF.FAA_AUTH = 0");
            sql.addSql("      then 0");
            sql.addSql("      else -9");
            sql.addSql("    end) as ACKBN,");
            sql.addSql("   (case");
            sql.addSql("      when FILE_CABINET.FCB_OWNER_SID = CMN_USRM_INF.USR_SID then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as ADMKBN");
        } else {
            sql.addSql("   (case");
            sql.addSql("      when FILE_CABINET.FCB_ACCESS_KBN = 0");
            sql.addSql("      and FILE_CABINET.FCB_PERSONAL_FLG = 0");
            sql.addSql("      then 1");
            sql.addSql("      when FILE_CABINET_ADMIN.FCB_SID is not null");
            sql.addSql("      then 1");
            sql.addSql("      when FILE_ACCESS_CONF.FCB_SID is not null");
            sql.addSql("      then FILE_ACCESS_CONF.FAA_AUTH");
            sql.addSql("      else -9");
            sql.addSql("    end) as ACKBN,");
            sql.addSql("   (case");
            sql.addSql("      when FILE_CABINET_ADMIN.FCB_SID is not null then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as ADMKBN");
        }

        sql.addSql(" from");
        sql.addSql("   CMN_USRM USRM");
        sql.addSql(" inner join");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql(" on");
        sql.addSql("   USRM.USR_SID=CMN_USRM_INF.USR_SID");
        sql.addSql(" left join");
        sql.addSql("   CMN_POSITION");
        sql.addSql(" on");
        sql.addSql("   CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID");

        sql.addSql(" inner join");
        sql.addSql("   FILE_CABINET");
        sql.addSql(" on");
        sql.addSql("   FILE_CABINET.FCB_SID = ?");
        sql.addIntValue(fcbSid);

        if (personalFlg != GSConstFile.CABINET_KBN_PRIVATE) {
            sql.addSql(" left join");
            sql.addSql("   FILE_CABINET_ADMIN");
            sql.addSql(" on");
            sql.addSql("   FILE_CABINET.FCB_SID = FILE_CABINET_ADMIN.FCB_SID");
            sql.addSql(" and");
            sql.addSql("   USRM.USR_SID = FILE_CABINET_ADMIN.USR_SID");
        }

        sql.addSql(" left join");
        sql.addSql("   (select");
        sql.addSql("      A.FCB_SID,");
        sql.addSql("      A.USR_SID,");
        sql.addSql("      max(A.FAA_AUTH) FAA_AUTH");
        sql.addSql("    from");
        sql.addSql("      (select");
        sql.addSql("         A.FCB_SID,");
        sql.addSql("         case when A.USR_KBN = ?");
        sql.addSql("              then A.USR_SID");
        sql.addSql("              else B.USR_SID");
        sql.addSql("              end as USR_SID,");
        sql.addSql("         A.FAA_AUTH");
        sql.addSql("       from");
        sql.addSql("         FILE_ACCESS_CONF A");
        sql.addSql("       left join");
        sql.addSql("         CMN_BELONGM B on B.GRP_SID = A.USR_SID");
        sql.addSql("       where");
        sql.addSql("         A.FCB_SID = ?");
        sql.addSql("      ) A");
        sql.addSql("    group by");
        sql.addSql("      A.FCB_SID,");
        sql.addSql("      A.USR_SID");
        sql.addSql("   ) FILE_ACCESS_CONF");
        sql.addSql(" on");
        sql.addSql("   FILE_CABINET.FCB_SID = FILE_ACCESS_CONF.FCB_SID");
        sql.addSql(" and");
        sql.addSql("   USRM.USR_SID = FILE_ACCESS_CONF.USR_SID");
        sql.addIntValue(GSConstFile.USER_KBN_USER);
        sql.addIntValue(fcbSid);

        sql.addSql(" where");

        sql.addSql("   USRM.USR_JKBN<>?");
        //ユーザSID < 100は除外
        sql.addSql(" and");
        sql.addSql("   USRM.USR_SID>?");

        sql.addSql(" ) ACCESS ");
        sql.addSql(" where");
        sql.addSql("   ACCESS.ACKBN<>-9");

        if (addList != null && addList.size() > 0) {
            sql.addSql(" or ");
            sql.addSql("   ACCESS.USR_SID in (");
            StringBuffer strBuf = new StringBuffer();
            for (Integer userSid : addList) {
                if (strBuf.length() > 0) {
                    strBuf.append(",");
                }
                strBuf.append(userSid);
            }
            sql.addSql(strBuf.toString());
            sql.addSql("   )");
        }

        String orderStr = "";
        //オーダー
        if (orderKey == GSConst.ORDER_KEY_ASC) {
            orderStr = "  asc";
        } else {
            orderStr = "  desc";
        }

        if (!countFlg) {
            log__.info("sortkey = " + sortKey);
            //ソートカラム
            switch (sortKey) {
                //氏名
                case GSConstUser.USER_SORT_NAME:
                    sql.addSql(" order by");
                    sql.addSql("   USI_SEI_KN");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   USI_MEI_KN");
                    sql.addSql(orderStr);
                    break;
                //社員/職員番号
                case GSConstUser.USER_SORT_SNO:
                    sql.addSql(" order by");
                    sql.addSql("   case when USI_SYAIN_NO is null then ''");
                    sql.addSql("   else USI_SYAIN_NO end ");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                //役職
                case GSConstUser.USER_SORT_YKSK:
                    sql.addSql(" order by");
                    sql.addSql("   YAKUSYOKU_EXIST");
                    sql.addSql(orderStr);
                    sql.addSql("  ,");
                    sql.addSql("   YAKUSYOKU_SORT");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                //キャビネット管理者
                case GSConstFile.USER_SORT_ADMIN:
                    sql.addSql(" order by");
                    sql.addSql("   ADMKBN");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                    //キャビネットアクセス
                case GSConstFile.USER_SORT_ACCESS:
                    sql.addSql(" order by");
                    sql.addSql("   ACKBN");
                    sql.addSql(orderStr);
                    sql.addSql("   ,");
                    sql.addSql("   USI_SEI_KN");
                    sql.addSql("    asc");
                    sql.addSql("   ,");
                    sql.addSql("   USI_MEI_KN");
                    sql.addSql("    asc");
                    break;
                default:
                    break;
            }
        }
        sql.addIntValue(GSConstUser.USER_JTKBN_DELETE);
        sql.addIntValue(GSConstUser.USER_RESERV_SID);
        return sql;
    }

    /**
     * <p>Create CMN_USRM_INF Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CmnUsrmInfModel
     * @throws SQLException SQL実行例外
     */
    private FileAccessUserModel __getFileAccessUserModelFromRs(ResultSet rs) throws SQLException {
        FileAccessUserModel bean = new FileAccessUserModel();
        bean.setCabinetAdminKbn(rs.getInt("ADMKBN"));
        bean.setCabinetAccessKbn(rs.getInt("ACKBN"));

        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setUsiSei(rs.getString("USI_SEI"));
        bean.setUsiMei(rs.getString("USI_MEI"));
        bean.setUsiSyainNo(rs.getString("USI_SYAIN_NO"));
        bean.setUsiYakusyoku(rs.getString("USI_YAKUSYOKU"));

        //ユーザログイン停止
        bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        return bean;
    }
}
