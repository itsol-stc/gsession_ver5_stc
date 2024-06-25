package jp.groupsession.v2.tcd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.tcd.model.TcdYukyudataModel;
import jp.groupsession.v2.tcd.tcd190.Tcd190SearchModel;
import jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel;

/**
 * <p>TCD_YUKYUDATA Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdYukyudataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdYukyudataDao.class);

    /**
     * <p>Default Constructor
     */
    public TcdYukyudataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdYukyudataDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table TCD_YUKYUDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table TCD_YUKYUDATA (");
            sql.addSql("   USR_SID integer not null,");
            sql.addSql("   TCY_NENDO integer not null,");
            sql.addSql("   TCY_YUKYU integer not null,");
            sql.addSql("   TCY_AUID integer not null,");
            sql.addSql("   TCY_ADATE timestamp not null,");
            sql.addSql("   TCY_EUID integer not null,");
            sql.addSql("   TCY_EDATE timestamp not null,");
            sql.addSql("   primary key (USR_SID)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert TCD_YUKYUDATA Data Bindding JavaBean
     * @param bean TCD_YUKYUDATA Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(TcdYukyudataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" TCD_YUKYUDATA(");
            sql.addSql("   USR_SID,");
            sql.addSql("   TCY_NENDO,");
            sql.addSql("   TCY_YUKYU,");
            sql.addSql("   TCY_AUID,");
            sql.addSql("   TCY_ADATE,");
            sql.addSql("   TCY_EUID,");
            sql.addSql("   TCY_EDATE");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getTcyNendo());
            sql.addDecimalValue(bean.getTcyYukyu());
            sql.addIntValue(bean.getTcyAuid());
            sql.addDateValue(bean.getTcyAdate());
            sql.addIntValue(bean.getTcyEuid());
            sql.addDateValue(bean.getTcyEdate());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update TCD_YUKYUDATA Data Bindding JavaBean
     * @param bean TCD_YUKYUDATA Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(TcdYukyudataModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   TCD_YUKYUDATA");
            sql.addSql(" set ");
            sql.addSql("   TCY_YUKYU=?,");
            sql.addSql("   TCY_EUID=?,");
            sql.addSql("   TCY_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   TCY_NENDO = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDecimalValue(bean.getTcyYukyu());
            sql.addIntValue(bean.getTcyEuid());
            sql.addDateValue(bean.getTcyEdate());
            //where
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getTcyNendo());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Select TCD_YUKYUDATA All Data
     * @return List in TCD_YUKYUDATAModel
     * @throws SQLException SQL実行例外
     */
    public List<TcdYukyudataModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdYukyudataModel> ret = new ArrayList<TcdYukyudataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   TCY_NENDO,");
            sql.addSql("   TCY_YUKYU,");
            sql.addSql("   TCY_AUID,");
            sql.addSql("   TCY_ADATE,");
            sql.addSql("   TCY_EUID,");
            sql.addSql("   TCY_EDATE");
            sql.addSql(" from ");
            sql.addSql("   TCD_YUKYUDATA");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getTcdYukyudataFromRs(rs));
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
     * <p>Select TCD_YUKYUDATA
     * @param usrSid USR_SID
     * @return TCD_YUKYUDATAModel
     * @throws SQLException SQL実行例外
     */
    public TcdYukyudataModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        TcdYukyudataModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   TCY_NENDO,");
            sql.addSql("   TCY_YUKYU,");
            sql.addSql("   TCY_AUID,");
            sql.addSql("   TCY_ADATE,");
            sql.addSql("   TCY_EUID,");
            sql.addSql("   TCY_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_YUKYUDATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdYukyudataFromRs(rs);
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
     * <br>[機  能] ユーザSIDと年度から有休データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param nendo 年度
     * @return 有休データ
     * @throws SQLException SQL実行例外
     */
    public TcdYukyudataModel getYukyuData(int usrSid, int nendo) throws SQLException {
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        TcdYukyudataModel ret = null;
        Connection con = null;
        con = getCon();
        
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   TCY_NENDO,");
            sql.addSql("   TCY_YUKYU,");
            sql.addSql("   TCY_AUID,");
            sql.addSql("   TCY_ADATE,");
            sql.addSql("   TCY_EUID,");
            sql.addSql("   TCY_EDATE");
            sql.addSql(" from");
            sql.addSql("   TCD_YUKYUDATA");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   TCY_NENDO = ?");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(nendo);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getTcdYukyudataFromRs(rs);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        
        return ret;
    }
    
    /**
     * <br>[機  能] 最小の年度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 最小の年度
     * @throws SQLException SQL実行例外
     */
    public String getMinNendo() throws SQLException {
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String ret = null;
        Connection con = null;
        con = getCon();
        
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   min(TCY_NENDO) as NENDO_MIN");
            sql.addSql(" from");
            sql.addSql("   TCD_YUKYUDATA");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("NENDO_MIN") != 0) {
                    ret = String.valueOf(rs.getInt("NENDO_MIN"));    
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        
        return ret;
    }
    
    /**
     * <br>[機  能] 最小の年度を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 最小の年度
     * @throws SQLException SQL実行例外
     */
    public String getMaxNendo() throws SQLException {
        
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String ret = null;
        Connection con = null;
        con = getCon();
        
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(TCY_NENDO) as NENDO_MAX");
            sql.addSql(" from");
            sql.addSql("   TCD_YUKYUDATA");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("NENDO_MAX") != 0) {
                    ret = String.valueOf(rs.getInt("NENDO_MAX"));    
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        
        return ret;
    }
    
    /**
     * <br>[機  能] 有休データ一覧の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl Tcd190SearchModel
     * @return 有休データ一覧の件数
     * @throws SQLException SQL実行例外
     */
    public int getYukyuDataCount(Tcd190SearchModel searchMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int ret = 0;
        Connection con = null;
        con = getCon();
        
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM");
            sql.addSql(" left join(");
            sql.addSql("   select");
            sql.addSql("     TCD_YUKYUDATA.TCY_YUKYU,");
            sql.addSql("     TCD_YUKYUDATA.USR_SID");
            sql.addSql("   from");
            sql.addSql("     TCD_YUKYUDATA");
            sql.addSql("   where");
            sql.addSql("     TCD_YUKYUDATA.TCY_NENDO = ?");
            sql.addSql(" ) YUKYU_DATA");
            sql.addSql(" on");
            sql.addSql("   CMN_USRM.USR_SID = YUKYU_DATA.USR_SID");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM.USR_SID in (");
            sql.addSql("     select USR_SID from CMN_BELONGM");
            sql.addSql("     where GRP_SID = ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(searchMdl.getNendo());
            sql.addIntValue(searchMdl.getGroupSid());
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
            JDBCUtil.closePreparedStatement(pstmt);
        }
        
        return ret;
        
    }
    
    /**
     * <br>[機  能] 条件に沿った有休日数情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @return 有休日数情報リスト
     * @throws SQLException SQL実行例外
     */
    public List<Tcd190YukyuModel> getYukyuDataList(Tcd190SearchModel searchMdl)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Tcd190YukyuModel> ret = new ArrayList<Tcd190YukyuModel>();
        Connection con = null;
        con = getCon();
        
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_USRM.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("   CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   YUKYU_DATA.TCY_YUKYU as TCY_YUKYU,");
            sql.addSql("   YUKYU_SUM.CNT as YUKYU_USE_COUNT,");
            sql.addSql("   round (");
            sql.addSql("     (YUKYU_SUM.CNT * 100.0) / YUKYU_DATA.TCY_YUKYU, 1) as YUKYU_RITSU");
            sql.addSql(" from");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" left join(");
            sql.addSql("   select");
            sql.addSql("     TCD_YUKYUDATA.TCY_YUKYU,");
            sql.addSql("     TCD_YUKYUDATA.USR_SID");
            sql.addSql("   from");
            sql.addSql("     TCD_YUKYUDATA");
            sql.addSql("   where");
            sql.addSql("     TCD_YUKYUDATA.TCY_NENDO = ?");
            sql.addSql(" ) YUKYU_DATA");
            sql.addSql(" on");
            sql.addSql("   CMN_USRM_INF.USR_SID = YUKYU_DATA.USR_SID");
            sql.addSql(" left join (");
            sql.addSql("   select");
            sql.addSql("       TCD_TCDATA.USR_SID as USR_SID,");
            sql.addSql("       sum (TCD_TCDATA.TCD_HOLCNT) as CNT");
            sql.addSql("     from");
            sql.addSql("       TCD_TCDATA,");
            sql.addSql("       TCD_HOLIDAY_INF");
            sql.addSql("     where");
            sql.addSql("       TCD_TCDATA.TCD_DATE >= ?");
            sql.addSql("     and");
            sql.addSql("       TCD_TCDATA.TCD_DATE <= ?");
            sql.addSql("     and");
            sql.addSql("       TCD_HOLIDAY_INF.THI_HOLTOTAL_KBN = " 
            + GSConstTimecard.HOL_KBN_YUUKYUU);
            sql.addSql("     and");
            sql.addSql("       TCD_TCDATA.THI_SID = TCD_HOLIDAY_INF.THI_SID");
            sql.addSql("     group by");
            sql.addSql("       TCD_TCDATA.USR_SID");
            sql.addSql("   ) YUKYU_SUM");
            sql.addSql(" on");
            sql.addSql("   CMN_USRM_INF.USR_SID = YUKYU_SUM.USR_SID");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM_INF.USR_SID in (");
            sql.addSql("     select USR_SID from CMN_BELONGM");
            sql.addSql("     where GRP_SID = ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_JKBN = 0");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            
            String order = null;
            int sortOrder = searchMdl.getSortOrder();
            int sortKey = searchMdl.getSortKey();
            
            //ソートキーを設定
            if (sortKey == GSConstTimecard.SORT_SIMEI) {
                if (sortOrder != GSConstTimecard.ORDER_KEY_ASC) {
                    order = "CMN_USRM_INF.USI_SEI_KN desc, CMN_USRM_INF.USI_MEI_KN";
                } else {
                    order = "CMN_USRM_INF.USI_SEI_KN, CMN_USRM_INF.USI_MEI_KN";
                }
            } else if (sortKey == GSConstTimecard.SORT_SYAINNO) {
                order = "CMN_USRM_INF.USI_SYAIN_NO";
            } else if (sortKey == GSConstTimecard.SORT_YUKYUDAYS) {
                order = "YUKYU_DATA.TCY_YUKYU";
            } else if (sortKey == GSConstTimecard.SORT_YUKYU_USEDAYS) {
                order = "YUKYU_SUM.CNT";
            } else if (sortKey == GSConstTimecard.SORT_YUKYU_USEPERCENT) {
                order = "YUKYU_RITSU";
            }
            //ソート順を設定
            if (sortOrder != GSConstTimecard.ORDER_KEY_ASC) {
                order += " desc";
            }
            
            //ソートキーが氏名以外の場合、第2ソート順に氏名を設定
            if (sortKey != GSConstTimecard.SORT_SIMEI) {
                if (sortOrder != GSConst.ORDER_KEY_ASC) {
                    order += " ,CMN_USRM_INF.USI_SEI_KN desc, CMN_USRM_INF.USI_MEI_KN desc";
                } else {
                    order += " ,CMN_USRM_INF.USI_SEI_KN, CMN_USRM_INF.USI_MEI_KN";
                }
            }
            
            if (order != null) {
                sql.addSql(" order by " + order);                
            }
            sql.addSql(" limit " + GSConstTimecard.MAX_YUKYU_GET_COUNT);
            sql.addSql(" offset ?");

            sql.addIntValue(searchMdl.getNendo());
            pstmt = con.prepareStatement(sql.toSqlString());
            UDate fromDate = new UDate();
            fromDate.setYear(searchMdl.getNendo());
            fromDate.setMonth(searchMdl.getKishuMonth());
            fromDate.setDay(1);
            fromDate.setZeroHhMmSs();
            sql.addDateValue(fromDate);
            
            UDate toDate = new UDate();
            toDate.setYear(searchMdl.getNendo() + 1);
            toDate.setMonth(searchMdl.getKimatuMonth());
            toDate.setDay(toDate.getMaxDayOfMonth());
            toDate.setMaxHhMmSs();
            sql.addDateValue(toDate);
            
            sql.addIntValue(searchMdl.getGroupSid());
            int offset = (searchMdl.getPage() - 1) * GSConstTimecard.MAX_YUKYU_GET_COUNT;
            sql.addIntValue(offset);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Tcd190YukyuModel bean = __getTcd190YukyuModelFromRs(rs);
                ret.add(bean);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
        
        return ret;
    }

    /**
     * <p>Delete TCD_YUKYUDATA
     * @param usrSid USR_SID
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_YUKYUDATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
    
    /**
     * <br>[機  能] ユーザSIDと年度から有休情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param nendo 年度
     * @return 削除件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int usrSid, int nendo) throws SQLException {
        
        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   TCD_YUKYUDATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   TCY_NENDO = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(nendo);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Create TCD_YUKYUDATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created TcdYukyudataModel
     * @throws SQLException SQL実行例外
     */
    private TcdYukyudataModel __getTcdYukyudataFromRs(ResultSet rs) throws SQLException {
        TcdYukyudataModel bean = new TcdYukyudataModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setTcyNendo(rs.getInt("TCY_NENDO"));
        bean.setTcyYukyu(rs.getBigDecimal("TCY_YUKYU"));
        bean.setTcyAuid(rs.getInt("TCY_AUID"));
        bean.setTcyAdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCY_ADATE")));
        bean.setTcyEuid(rs.getInt("TCY_EUID"));
        bean.setTcyEdate(UDate.getInstanceTimestamp(rs.getTimestamp("TCY_EDATE")));
        return bean;
    }
    
    /**
     * <br>[機  能] SQL実行結果から表示用モデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return Tcd190YukyuModel
     * @throws SQLException SQL実行例外
     */
    private Tcd190YukyuModel __getTcd190YukyuModelFromRs(ResultSet rs) throws SQLException {
        
        Tcd190YukyuModel yukyuMdl = new Tcd190YukyuModel();
        yukyuMdl.setEmployeeNumber(rs.getString("USI_SYAIN_NO")); 
        String name = rs.getString("USI_SEI") + " " + rs.getString("USI_MEI");
        yukyuMdl.setName(name);
        yukyuMdl.setYukyuDays(rs.getDouble("TCY_YUKYU"));
        yukyuMdl.setYukyuUseDays(rs.getDouble("YUKYU_USE_COUNT"));
        yukyuMdl.setYukyuUsePercent(rs.getDouble("YUKYU_RITSU"));
        yukyuMdl.setUsrSid(rs.getInt("USR_SID"));
        yukyuMdl.setUserUkoFlg(rs.getInt("USR_UKO_FLG"));
        return yukyuMdl;
    }
}
