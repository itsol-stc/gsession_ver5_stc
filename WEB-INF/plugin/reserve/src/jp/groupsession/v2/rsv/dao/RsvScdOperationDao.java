package jp.groupsession.v2.rsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.model.RsvScdOperationModel;
import jp.groupsession.v2.rsv.model.other.RsvSchAdmConfModel;
import jp.groupsession.v2.rsv.model.other.RsvSchBinModel;
import jp.groupsession.v2.rsv.model.other.RsvSchDataPubModel;
import jp.groupsession.v2.rsv.model.other.RsvSchExdataBinModel;
import jp.groupsession.v2.rsv.model.other.RsvSchExdataPubModel;
import jp.groupsession.v2.rsv.model.other.RsvSchPriConfModel;
import jp.groupsession.v2.rsv.model.other.ScheduleRsvModel;

/**
 * <br>[機  能] 施設予約プラグインからスケジュール情報を操作する際使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 */
public class RsvScdOperationDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvScdOperationDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvScdOperationDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvScdOperationDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] スケジュールを登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録モデル
     * @throws SQLException 例外
     */
    public void insertSchData(RsvScdOperationModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_DATA(");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SCD_USR_SID,");
            sql.addSql("   SCD_GRP_SID,");
            sql.addSql("   SCD_USR_KBN,");
            sql.addSql("   SCD_FR_DATE,");
            sql.addSql("   SCD_TO_DATE,");
            sql.addSql("   SCD_DAILY,");
            sql.addSql("   SCD_BGCOLOR,");
            sql.addSql("   SCD_TITLE,");
            sql.addSql("   SCD_VALUE,");
            sql.addSql("   SCD_BIKO,");
            sql.addSql("   SCD_PUBLIC,");
            sql.addSql("   SCD_AUID,");
            sql.addSql("   SCD_ADATE,");
            sql.addSql("   SCD_EUID,");
            sql.addSql("   SCD_EDATE,");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   SCD_EDIT,");
            sql.addSql("   SCD_TARGET_GRP,");
            sql.addSql("   SCD_REMINDER");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getScdSid());
            sql.addIntValue(bean.getScdUsrSid());
            sql.addIntValue(bean.getScdGrpSid());
            sql.addIntValue(bean.getScdUsrKbn());
            sql.addDateValue(bean.getScdFrDate());
            sql.addDateValue(bean.getScdToDate());
            sql.addIntValue(bean.getScdDaily());
            sql.addIntValue(bean.getScdBgcolor());
            sql.addStrValue(bean.getScdTitle());
            sql.addStrValue(bean.getScdValue());
            sql.addStrValue(bean.getScdBiko());
            sql.addIntValue(bean.getScdPublic());
            sql.addIntValue(bean.getScdAuid());
            sql.addDateValue(bean.getScdAdate());
            sql.addIntValue(bean.getScdEuid());
            sql.addDateValue(bean.getScdEdate());
            sql.addIntValue(bean.getSceSid());
            sql.addIntValue(bean.getScdRsSid());
            sql.addIntValue(bean.getScdEdit());
            sql.addIntValue(bean.getScdTargetGrp());
            sql.addIntValue(bean.getScdReminder());
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
     * <br>[機  能] スケジュール公開対象を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 登録モデル
     * @throws SQLException 例外
     */
    public void insert(RsvSchDataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_DATA_PUB(");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SDP_TYPE,");
            sql.addSql("   SDP_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getScdSid());
            sql.addIntValue(bean.getSdpType());
            sql.addIntValue(bean.getSdpPsid());
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
     * <br>[機  能] スケジュール添付情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean SCH_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSchBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_BIN(");
            sql.addSql("   SCD_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getScdSid());
            sql.addLongValue(bean.getBinSid());
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
     * <br>[機  能] スケジュール添付拡張情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean SCH_EXDATA_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSchExdataBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_EXDATA_BIN(");
            sql.addSql("   SCE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSceSid());
            sql.addLongValue(bean.getBinSid());
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRssid スケジュールリレーションSID
     * @throws SQLException 例外
     */
    public void deleteScdTi(int scdRssid) throws SQLException {
        if (scdRssid <= 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRssid);

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
     * <br>[機  能] 指定されたスケジュールリレーションSID、ユーザの
     * <br>         スケジュール情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdRssid スケジュールリレーションSID
     * @param schUsrSid ユーザ・グループSID
     * @throws SQLException 例外
     */
    public void deleteScdTiWithUsers(int scdRssid, String[] schUsrSid) throws SQLException {

        if (scdRssid <= 0 || schUsrSid == null || schUsrSid.length == 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");
            sql.addIntValue(scdRssid);

            sql.addSql(" and ");
            sql.addSql("   SCD_USR_SID in (");
            for (int idx = 0; idx < schUsrSid.length; idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(Integer.parseInt(schUsrSid[idx]));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] バイナリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sceSid 拡張情報スケジュールSID
     * @return バイナリSID
     * @throws SQLException SQL実行例外
     */
    public String[] getBinSids(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String[] ret = null;
        List<String> binList = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA_BIN");
            sql.addSql(" where");
            sql.addSql("   SCE_SID = ?");
            sql.addIntValue(sceSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                binList.add(String.valueOf(rs.getLong("BIN_SID")));
            }
            ret = binList.toArray(new String[binList.size()]);
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール拡張SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRssid スケジュールリレーションSID
     * @return sceSid スケジュール拡張SID
     * @throws SQLException 例外
     */
    public int selectSceSid(int scdRssid) throws SQLException {

        if (scdRssid <= 0) {
            return -1;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int sceSid = -1;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCE_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRssid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                sceSid = rs.getInt("SCE_SID");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return sceSid;
    }

    /**
     * <br>[機  能] ユーザのスケジュールが登録されているかチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRsSid スケジュールリレーションSID
     * @param usrSid ユーザSID
     * @return ret true:登録されている false 登録されていない
     * @throws SQLException 例外
     */
    public boolean isUsingUserFromRsSid(int scdRsSid, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SCD_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where");
            sql.addSql("   SCD_RSSID = ?");
            sql.addSql(" and");
            sql.addSql("   SCD_USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
                }
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
     * <br>[機  能] ユーザのスケジュールが登録されているかチェックする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @param usrSid ユーザSID
     * @return ret true:登録されている false 登録されていない
     * @throws SQLException 例外
     */
    public boolean isUsingUserFromSceSid(int sceSid, int usrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SCD_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where");
            sql.addSql("   SCE_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SCD_USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
                }
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
     * <br>[機  能] 指定されたスケジュール拡張SIDに
     * <br>         関連付いた施設リレーションSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @return ret 施設リレーションSID
     * @throws SQLException 例外
     */
    public ArrayList<Integer> selectKakutyoAllScdRsSid(int sceSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");
            sql.addSql(" group by");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SCD_RSSID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SCD_RSSID"));
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDに
     * <br>         関連付いた施設予約SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsidList スケジュールリレーションSID
     * @return ret 施設拡張SID
     * @throws SQLException 例外
     */
    public ArrayList<Integer> selectKakutyoAllRsysid(ArrayList<Integer> rsrRsidList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID in (");

            for (int i = 0; i < rsrRsidList.size(); i++) {

                sql.addSql("?");
                sql.addIntValue(rsrRsidList.get(i));

                if (i != rsrRsidList.size() - 1) {
                    sql.addSql(", ");
                }
            }

            sql.addSql("   )");
            sql.addSql(" group by");
            sql.addSql("   RSY_SID");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("RSY_SID"));
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDに
     * <br>         関連付いた施設拡張SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsidList スケジュールリレーションSID
     * @return ret 施設拡張SID
     * @throws SQLException 例外
     */
    public ArrayList<Integer> selectKakutyoAllRsrRsid(ArrayList<Integer> rsrRsidList)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSR_RSID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID in (");

            for (int i = 0; i < rsrRsidList.size(); i++) {

                sql.addSql("?");
                sql.addIntValue(rsrRsidList.get(i));

                if (i != rsrRsidList.size() - 1) {
                    sql.addSql(", ");
                }
            }

            sql.addSql("   )");
            sql.addSql(" group by");
            sql.addSql("   RSR_RSID");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("RSR_RSID"));
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
     * <br>[機  能] 指定されたスケジュール拡張SIDに
     * <br>         関連付いた施設リレーションSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @return ret 施設リレーションSID
     * @throws SQLException 例外
     */
    public RsvScdOperationModel selectOldScdData(int sceSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        RsvScdOperationModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCE_BGCOLOR,");
            sql.addSql("   SCE_TITLE,");
            sql.addSql("   SCE_VALUE,");
            sql.addSql("   SCE_BIKO,");
            sql.addSql("   SCE_PUBLIC,");
            sql.addSql("   SCE_EDIT,");
            sql.addSql("   SCE_AUID,");
            sql.addSql("   SCE_ADATE, ");
            sql.addSql("   SCE_EUID,");
            sql.addSql("   SCE_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new RsvScdOperationModel();
                ret.setSceBgcolor(rs.getInt("SCE_BGCOLOR"));
                ret.setSceTitle(rs.getString("SCE_TITLE"));
                ret.setSceValue(rs.getString("SCE_VALUE"));
                ret.setSceBiko(rs.getString("SCE_BIKO"));
                ret.setScePublic(rs.getInt("SCE_PUBLIC"));
                ret.setSceEdit(rs.getInt("SCE_EDIT"));
                ret.setSceAuid(rs.getInt("SCE_AUID"));
                ret.setSceAdate(
                        UDate.getInstanceTimestamp(
                                rs.getTimestamp("SCE_ADATE")));
                ret.setSceEuid(rs.getInt("SCE_EUID"));
                ret.setSceEdate(
                        UDate.getInstanceTimestamp(
                                rs.getTimestamp("SCE_EDATE")));
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDに
     * <br>         関連付いたユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRsSid スケジュールリレーションSID
     * @return ret ユーザSID
     * @throws SQLException 例外
     */
    public ArrayList<Integer> selectScdUsrSid(int scdRsSid)
        throws SQLException {

        ArrayList<Integer> ret = new ArrayList<Integer>();
        if (scdRsSid <= 0) {
            return ret;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_USR_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");
            sql.addSql(" group by");
            sql.addSql("   SCD_USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SCD_USR_SID"));
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
     * <br>[機  能] 指定されたスケジュールのリレーションSIDに
     * <br>         関連付いたユーザ区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRsSid スケジュールリレーションSID
     * @return ユーザ区分
     * @throws SQLException 例外
     */
    public int getSchUsrKbn(int scdRsSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int scdUsrKbn = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_USR_KBN");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");
            sql.addSql(" group by");
            sql.addSql("   SCD_USR_KBN");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                scdUsrKbn = rs.getInt("SCD_USR_KBN");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return scdUsrKbn;
    }

    /**
     * <br>[機  能] 指定されたスケジュール拡張SIDに
     * <br>         関連付いたユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @return ret ユーザSID
     * @throws SQLException 例外
     */
    public ArrayList<Integer> selectKakutyoAllUsrSid(int sceSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_USR_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");
            sql.addSql(" group by");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SCD_USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("SCD_USR_SID"));
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
     * <br>[機  能] 指定されたスケジュール拡張SIDに
     * <br>         関連付いたユーザ区分を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @return ユーザ区分
     * @throws SQLException 例外
     */
    public int selectKakutyoUsrKbn(int sceSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int scdUsrKbn = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_USR_KBN");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");
            sql.addSql(" group by");
            sql.addSql("   SCD_USR_KBN");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                scdUsrKbn = rs.getInt("SCD_USR_KBN");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return scdUsrKbn;
    }

    /**
     * <br>[機  能] 指定されたスケジュールリレーション拡張SIDを持つ
     * <br>         スケジュールデータ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @return cnt 件数
     * @throws SQLException 例外
     */
    public int selectExDataCnt(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int cnt = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(SCD_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("cnt");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt;
    }

    /**
     * <br>[機  能] 指定されたスケジュール拡張SIDの
     * <br>         データを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @throws SQLException 例外
     */
    public void deleteKakutyoData(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

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
     * <br>[機  能] 指定されたスケジュール拡張SIDに関連するスケジュール情報SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @param schUsrSid ユーザ・グループSID
     * @return スケジュール情報SID
     * @throws SQLException 例外
     */
    public List<Integer> getKakutyoDataWithUsers(int sceSid, String[] schUsrSid)
    throws SQLException {

        List<Integer> scdSidList = new ArrayList<Integer>();
        if (sceSid <= 0 || schUsrSid == null || schUsrSid.length == 0) {
            return scdSidList;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");
            sql.addIntValue(sceSid);

            sql.addSql(" and ");
            sql.addSql("   SCD_USR_SID in (");
            for (int idx = 0; idx < schUsrSid.length; idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(Integer.parseInt(schUsrSid[idx]));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                scdSidList.add(rs.getInt("SCD_SID"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return scdSidList;
    }

    /**
     * <br>[機  能] 指定されたスケジュール拡張SIDの
     * <br>         データを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @param schUsrSid ユーザ・グループSID
     * @throws SQLException 例外
     */
    public void deleteKakutyoDataWithUsers(int sceSid, String[] schUsrSid) throws SQLException {

        if (sceSid <= 0 || schUsrSid == null || schUsrSid.length == 0) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");
            sql.addIntValue(sceSid);

            sql.addSql(" and ");
            sql.addSql("   SCD_USR_SID in (");
            for (int idx = 0; idx < schUsrSid.length; idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(Integer.parseInt(schUsrSid[idx]));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] 指定されたスケジュール拡張SIDの
     * <br>         データを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @throws SQLException 例外
     */
    public void deleteExData(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

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
     * <br>[機  能] スケジュール公開対象を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean 登録モデル
     * @throws SQLException 例外
     */
    public void insertSchExPub(RsvSchExdataPubModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" SCH_EXDATA_PUB(");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SEP_TYPE,");
            sql.addSql("   SEP_PSID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getSceSid());
            sql.addIntValue(bean.getSepType());
            sql.addIntValue(bean.getSepPsid());
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
     * <br>[機  能] 指定されたスケジュール拡張SIDの
     * <br>         データを削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @throws SQLException 例外
     */
    public void deleteExPubData(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);

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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRssid スケジュールリレーションSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectSchList(int scdRssid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret =
            new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SCD_USR_SID,");
            sql.addSql("   SCD_GRP_SID,");
            sql.addSql("   SCD_USR_KBN,");
            sql.addSql("   SCD_FR_DATE,");
            sql.addSql("   SCD_TO_DATE,");
            sql.addSql("   SCD_DAILY,");
            sql.addSql("   SCD_BGCOLOR,");
            sql.addSql("   SCD_TITLE,");
            sql.addSql("   SCD_VALUE,");
            sql.addSql("   SCD_BIKO,");
            sql.addSql("   SCD_PUBLIC,");
            sql.addSql("   SCD_AUID,");
            sql.addSql("   SCD_ADATE,");
            sql.addSql("   SCD_EUID,");
            sql.addSql("   SCD_EDATE,");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SCD_EDIT,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   SCD_TARGET_GRP,");
            sql.addSql("   SCD_REMINDER");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRssid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = __getSchDataFromRs(rs);
                ret.add(mdl);
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRssid スケジュールリレーションSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectSchListGrpSceSid(int scdRssid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret =
            new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCH_DATA.SCD_SID,");
            sql.addSql("   SCH_DATA.SCD_USR_SID,");
            sql.addSql("   SCH_DATA.SCD_GRP_SID,");
            sql.addSql("   SCH_DATA.SCD_USR_KBN,");
            sql.addSql("   SCH_DATA.SCD_FR_DATE,");
            sql.addSql("   SCH_DATA.SCD_TO_DATE,");
            sql.addSql("   SCH_DATA.SCD_DAILY,");
            sql.addSql("   SCH_DATA.SCD_BGCOLOR,");
            sql.addSql("   SCH_DATA.SCD_TITLE,");
            sql.addSql("   SCH_DATA.SCD_VALUE,");
            sql.addSql("   SCH_DATA.SCD_BIKO,");
            sql.addSql("   SCH_DATA.SCD_PUBLIC,");
            sql.addSql("   SCH_DATA.SCD_AUID,");
            sql.addSql("   SCH_DATA.SCD_ADATE,");
            sql.addSql("   SCH_DATA.SCD_EUID,");
            sql.addSql("   SCH_DATA.SCD_EDATE,");
            sql.addSql("   SCH_DATA.SCE_SID,");
            sql.addSql("   SCH_DATA.SCD_EDIT,");
            sql.addSql("   SCH_DATA.SCD_RSSID,");
            sql.addSql("   SCH_DATA.SCD_TARGET_GRP,");
            sql.addSql("   SCH_DATA.SCD_REMINDER");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   (select");
            sql.addSql("      max(SCH_DATA.SCE_SID) as SCE_SID");
            sql.addSql("    from");
            sql.addSql("      SCH_DATA");
            sql.addSql("    where ");
            sql.addSql("      SCH_DATA.SCD_RSSID = ?");
            sql.addSql("    group by");
            sql.addSql("      SCH_DATA.SCD_RSSID");
            sql.addSql("   ) relVw");
            sql.addSql(" where");
            sql.addSql("   SCH_DATA.SCE_SID = relVw.SCE_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRssid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = __getSchDataFromRs(rs);
                ret.add(mdl);
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
     * <br>[機  能] 指定されたスケジュールSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdSid スケジュールSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public RsvScdOperationModel selectSchMdl(int scdSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        RsvScdOperationModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SCD_USR_SID,");
            sql.addSql("   SCD_GRP_SID,");
            sql.addSql("   SCD_USR_KBN,");
            sql.addSql("   SCD_FR_DATE,");
            sql.addSql("   SCD_TO_DATE,");
            sql.addSql("   SCD_DAILY,");
            sql.addSql("   SCD_BGCOLOR,");
            sql.addSql("   SCD_TITLE,");
            sql.addSql("   SCD_VALUE,");
            sql.addSql("   SCD_BIKO,");
            sql.addSql("   SCD_PUBLIC,");
            sql.addSql("   SCD_AUID,");
            sql.addSql("   SCD_ADATE,");
            sql.addSql("   SCD_EUID,");
            sql.addSql("   SCD_EDATE,");
            sql.addSql("   SCE_SID,");
            sql.addSql("   SCD_EDIT,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   SCD_TARGET_GRP,");
            sql.addSql("   SCD_REMINDER");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchDataFromRs(rs);
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考] 単一スケジュール編集の場合
     *
     * @param scdRsSid スケジュールリレーションSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public RsvScdOperationModel selectSchMdlGrpRssid(int scdRsSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        RsvScdOperationModel ret = null;
        HashMap<String, String> usrMap = new HashMap<String, String>();
        HashMap<Integer, Integer> rssidMap = new HashMap<Integer, Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_USR_SID,");
            sql.addSql("   SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            boolean exist = false;
            while (rs.next()) {
                int rsSid = rs.getInt("SCD_RSSID");
                int usrSid = rs.getInt("SCD_USR_SID");
                String key = rsSid + "-" + usrSid;
                usrMap.put(key, key);
                rssidMap.put(rsSid, rsSid);
                exist = true;
            }

            if (exist) {
                ret = new RsvScdOperationModel();
                ret.setUsrMap(usrMap);
                ret.setRssidMap(rssidMap);
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考] 単一スケジュール編集の場合
     *
     * @param sessionUsrSid セッションユーザSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public HashMap<String, String> selectRsvGrpAdmMap(int sessionUsrSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        HashMap<String, String> grpAdmMap = new HashMap<String, String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_ADM");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sessionUsrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                int rsgSid = rs.getInt("RSG_SID");
                int usrSid = rs.getInt("USR_SID");
                String key = rsgSid + "-" + usrSid;
                grpAdmMap.put(key, key);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return grpAdmMap;
    }

    /**
     * <br>[機  能] 指定されたスケジュールSIDの
     * <br>         スケジュール情報を取得する
     * <br>[解  説]
     * <br>[備  考] 繰り返しスケジュール編集の場合
     *
     * @param scdSid スケジュールSID
     * @return ret スケジュール情報
     * @throws SQLException 例外
     */
    public RsvScdOperationModel selectSchMdlGrpSceSid(int scdSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        RsvScdOperationModel ret = null;
        HashMap<String, String> usrMap = new HashMap<String, String>();
        HashMap<Integer, Integer> rssidMap = new HashMap<Integer, Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCH_DATA.SCD_USR_SID,");
            sql.addSql("   SCH_DATA.SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   (select");
            sql.addSql("      SCH_DATA.SCE_SID");
            sql.addSql("    from");
            sql.addSql("      SCH_DATA");
            sql.addSql("    where");
            sql.addSql("      SCH_DATA.SCD_SID = ?");
            sql.addSql("    and");
            sql.addSql("      SCH_DATA.SCE_SID > 0");
            sql.addSql("   ) sceGrpVw");
            sql.addSql(" where");
            sql.addSql("   SCH_DATA.SCD_RSSID > 0");
            sql.addSql("   and SCH_DATA.SCE_SID = sceGrpVw.SCE_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            boolean exist = false;
            while (rs.next()) {
                int rsSid = rs.getInt("SCD_RSSID");
                int usrSid = rs.getInt("SCD_USR_SID");
                String key = rsSid + "-" + usrSid;
                usrMap.put(key, key);
                rssidMap.put(rsSid, rsSid);
                exist = true;
            }

            if (exist) {
                ret = new RsvScdOperationModel();
                ret.setUsrMap(usrMap);
                ret.setRssidMap(rssidMap);
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
     * <br>[機  能] 指定されたユーザSID_Aのユーザが所属している
     * <br>         グループに、ユーザSID_Bのユーザが所属しているか
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSidA チェック元ユーザ
     * @param usrSidB チェック対象ユーザ
     * @return ret true:所属している false:所属していない
     * @throws SQLException 例外
     */
    public boolean isScdEditTi(int usrSidA, int usrSidB) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CMN_BELONGM.USR_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CMN_BELONGM,");
            sql.addSql("   (select");
            sql.addSql("      GRP_SID");
            sql.addSql("    from");
            sql.addSql("      CMN_BELONGM");
            sql.addSql("    where");
            sql.addSql("      USR_SID = ?");
            sql.addSql("   ) belongmVw");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.GRP_SID = belongmVw.GRP_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSidA);
            sql.addIntValue(usrSidB);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = true;
                }
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDの
     * <br>         施設情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rssidArray スケジュールリレーションSID
     * @return ret 施設情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectRsvList(ArrayList<Integer> rssidArray)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret = new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID = RSV_SIS_YRK.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID in (");

            for (int i = 0; i < rssidArray.size(); i++) {
                int rssid = rssidArray.get(i);
                sql.addSql("?");
                sql.addIntValue(rssid);
                if (i != rssidArray.size() - 1) {
                    sql.addSql(", ");
                }
            }
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = new RsvScdOperationModel();
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                mdl.setScdRsSid(rs.getInt("SCD_RSSID"));
                mdl.setRsySid(rs.getInt("RSY_SID"));
                mdl.setRsyAuid(rs.getInt("RSY_AUID"));
                mdl.setRsyEdit(rs.getInt("RSY_EDIT"));
                ret.add(mdl);
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
     * <br>[機  能] 指定された施設予約拡張SIDより施設情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 施設予約拡張SID
     * @return ret 施設情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectRsvList(int rsrRsid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret = new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID = RSV_SIS_YRK.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = new RsvScdOperationModel();
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                mdl.setScdRsSid(rs.getInt("SCD_RSSID"));
                mdl.setRsyAuid(rs.getInt("RSY_AUID"));
                mdl.setRsyEdit(rs.getInt("RSY_EDIT"));
                ret.add(mdl);
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
     * <br>[機  能] 指定された施設予約SIDより施設情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsyRsid 施設予約SID
     * @return ret 施設情報
     * @throws SQLException 例外
     */
    public ArrayList<RsvScdOperationModel> selectRsvMdl(int rsyRsid)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvScdOperationModel> ret = new ArrayList<RsvScdOperationModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_DATA.RSG_SID,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_DATA,");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_DATA.RSD_SID = RSV_SIS_YRK.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSY_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsyRsid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel mdl = new RsvScdOperationModel();
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                mdl.setScdRsSid(rs.getInt("SCD_RSSID"));
                mdl.setRsyAuid(rs.getInt("RSY_AUID"));
                mdl.setRsyEdit(rs.getInt("RSY_EDIT"));
                ret.add(mdl);
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
     * <br>[機  能] スケジュールを更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param updArray 更新リスト
     * @param scdGrpSid スケジュールグループSID
     * @throws SQLException 例外
     */
    public void updateRsvToScdTi(ArrayList<RsvScdOperationModel> updArray,
                                int scdGrpSid)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_DATA");
            sql.addSql(" set");
            sql.addSql("   SCD_TITLE = ?,");
            sql.addSql("   SCD_FR_DATE = ?,");
            sql.addSql("   SCD_TO_DATE = ?,");
            sql.addSql("   SCD_VALUE = ?,");
            sql.addSql("   SCD_EUID = ?,");
            sql.addSql("   SCD_EDATE = ?,");
            sql.addSql("   SCD_EDIT = ?,");
            sql.addSql("   SCD_PUBLIC = ?,");
            sql.addSql("   SCD_REMINDER = ?,");
            sql.addSql("   SCD_TARGET_GRP = ?");

            boolean updateScdGrp = scdGrpSid > 0;
            if (updateScdGrp) {
                sql.addSql("   ,SCD_GRP_SID = ?");
            }

            sql.addSql(" where");
            sql.addSql("   SCD_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (RsvScdOperationModel mdl : updArray) {

                sql.addStrValue(mdl.getScdTitle());
                sql.addDateValue(mdl.getScdFrDate());
                sql.addDateValue(mdl.getScdToDate());
                sql.addStrValue(mdl.getScdValue());
                sql.addIntValue(mdl.getScdEuid());
                sql.addDateValue(mdl.getScdEdate());
                sql.addIntValue(mdl.getScdEdit());
                sql.addIntValue(mdl.getScdPublic());
                sql.addIntValue(mdl.getScdReminder());
                sql.addIntValue(mdl.getScdTargetGrp());

                if (updateScdGrp) {
                    sql.addIntValue(scdGrpSid);
                }
                sql.addIntValue(mdl.getScdSid());

                //ログ出力
                log__.info(sql.toLogString());

                sql.setParameter(pstmt);
                pstmt.executeUpdate();

                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>スケジュールリレーションSIDからスケジュールSIDを取得する
     *
     * @param rsSid スケジュールリレーションSID
     * @return scdSid スケジュールSID
     * @throws SQLException SQL実行例外
     */
    public int getScdSidFromRsSid(int rsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int scdSid = -1;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   SCD_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                scdSid = rs.getInt("SCD_SID");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return scdSid;
    }

    /**
     * <p>Select SCH_DATA
     * @param scdSid スケジュールSID
     * @param sessionUserSid セッションユーザSID
     * @return スケジュール情報
     * @throws SQLException SQL実行例外
     */
    public ScheduleRsvModel getSchData(int scdSid, int sessionUserSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ScheduleRsvModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   SCD_USR_SID,");
            sql.addSql("   SCD_GRP_SID,");
            sql.addSql("   SCD_USR_KBN,");
            sql.addSql("   SCD_FR_DATE,");
            sql.addSql("   SCD_TO_DATE,");
            sql.addSql("   SCD_DAILY,");
            sql.addSql("   SCD_BGCOLOR,");
            sql.addSql("   SCD_TITLE,");
            sql.addSql("   SCD_VALUE,");
            sql.addSql("   SCD_BIKO,");
            sql.addSql("   SCD_PUBLIC,");
            sql.addSql("   SCD_AUID,");
            sql.addSql("   SCD_ADATE,");
            sql.addSql("   SCD_EUID,");
            sql.addSql("   SCD_EDATE");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new ScheduleRsvModel();
                ret.setScdSid(rs.getInt("SCD_SID"));
                ret.setScdUsrSid(rs.getInt("SCD_USR_SID"));
                ret.setScdGrpSid(rs.getInt("SCD_GRP_SID"));
                ret.setScdUsrKbn(rs.getInt("SCD_USR_KBN"));
                ret.setScdFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_FR_DATE")));
                ret.setScdToDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_TO_DATE")));
                ret.setScdDaily(rs.getInt("SCD_DAILY"));
                ret.setScdBgcolor(rs.getInt("SCD_BGCOLOR"));
                ret.setScdTitle(rs.getString("SCD_TITLE"));
                ret.setScdValue(rs.getString("SCD_VALUE"));
                ret.setScdBiko(rs.getString("SCD_BIKO"));
                ret.setScdPublic(rs.getInt("SCD_PUBLIC"));
                ret.setScdAuid(rs.getInt("SCD_AUID"));
                ret.setScdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_ADATE")));
                ret.setScdEuid(rs.getInt("SCD_EUID"));
                ret.setScdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_EDATE")));
            }

            if (ret.getScdGrpSid() != GSConstSchedule.DF_SCHGP_ID) {
                //同時登録有りスケジュールの場合ユーザ氏名を取得
                ArrayList<CmnUsrmInfModel> usrList = __getScheduleUsrList(
                        scdSid, ret.getScdUsrSid(), ret.getScdUsrKbn(), sessionUserSid);
                ret.setUsrInfList(usrList);
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
     * スケジュールSIDを指定し同時登録しているユーザ情報を取得します
     * @param scdSid スケジュールSID
     * @param usrSid 除外するユーザ
     * @param usrKbn 除外するユーザの区分
     * @param sessionUserSid セッションユーザSID
     * @return ArrayLis in CmnUsrmInfModel
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<CmnUsrmInfModel> __getScheduleUsrList(
            int scdSid,
            int usrSid,
            int usrKbn,
            int sessionUserSid)
    throws SQLException {

        //スケジュールアクセス不可ユーザ or グループを取得
        SchDao schDao = new SchDao(getCon());
        List<Integer> schNotAccessUserList = null;
        if (usrKbn == GSConstSchedule.USER_KBN_GROUP) {
            schNotAccessUserList = schDao.getNotAccessGrpList(sessionUserSid);
        } else {
            schNotAccessUserList = schDao.getNotAccessUserList(sessionUserSid);
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnUsrmInfModel> ret = new ArrayList<CmnUsrmInfModel>();
        CmnUsrmInfModel usrMdl = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   USR_INFO.SCD_USR_SID, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG, ");
            sql.addSql("   CMN_USRM_INF.USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI ");
            sql.addSql(" from ");
            sql.addSql(" ( ");
            sql.addSql(" select ");
            sql.addSql("   SCH_DATA.SCD_USR_SID ");
            sql.addSql(" from ");
            sql.addSql("   ( ");
            sql.addSql("   select ");
            sql.addSql("    SCH_DATA.SCD_GRP_SID ");
            sql.addSql("   from ");
            sql.addSql("    SCH_DATA ");
            sql.addSql("   where ");
            sql.addSql("    SCH_DATA.SCD_SID=? ");
            sql.addSql("   ) GP, ");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   CMN_USRM_INF ");
            sql.addSql(" where ");
            sql.addSql("   SCH_DATA.SCD_USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" and");
            sql.addSql("   GP.SCD_GRP_SID = SCH_DATA.SCD_GRP_SID ");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_USR_KBN =? ");
            sql.addSql(" group by SCH_DATA.SCD_USR_SID ");
            sql.addSql(" ) USR_INFO, ");
            sql.addSql("   CMN_USRM_INF, ");
            sql.addSql("   CMN_USRM ");
            sql.addSql(" where ");
            sql.addSql("   USR_INFO.SCD_USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" and ");
            sql.addSql("   USR_INFO.SCD_USR_SID = CMN_USRM.USR_SID ");
            sql.addIntValue(scdSid);
            sql.addIntValue(GSConstSchedule.USER_KBN_USER);

            sql.addSql(" order by");
            sql.addSql("   CMN_USRM_INF.USI_SEI_KN, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI_KN ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            log__.debug("同時登録ユーザ取得：開始");
            int scdUsrSid = 0;
            while (rs.next()) {
                scdUsrSid = rs.getInt("SCD_USR_SID");
                if (schNotAccessUserList.indexOf(scdUsrSid) < 0) {
                    usrMdl = new CmnUsrmInfModel();
                    usrMdl.setUsrSid(scdUsrSid);
                    usrMdl.setUsiSei(rs.getString("USI_SEI"));
                    usrMdl.setUsiMei(rs.getString("USI_MEI"));
                    usrMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                    log__.debug("同時登録ユーザ氏名==>" + usrMdl.getUsiMei() + usrMdl.getUsiSei());
                    ret.add(usrMdl);
                }
            }
            log__.debug("同時登録ユーザ取得：終了");
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p>Select SCH_ADM_CONF All Data
     * @return SCH_ADM_CONFModel
     * @throws SQLException SQL実行例外
     */
    public RsvSchAdmConfModel getAdmConf() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSchAdmConfModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAD_CRANGE,");
            sql.addSql("   SAD_ATDEL_FLG,");
            sql.addSql("   SAD_ATDEL_Y,");
            sql.addSql("   SAD_ATDEL_M,");
            sql.addSql("   SAD_HOUR_DIV,");
            sql.addSql("   SAD_SORT_KBN,");
            sql.addSql("   SAD_SORT_KEY1,");
            sql.addSql("   SAD_SORT_ORDER1,");
            sql.addSql("   SAD_SORT_KEY2,");
            sql.addSql("   SAD_SORT_ORDER2,");
            sql.addSql("   SAD_INI_EDIT_STYPE,");
            sql.addSql("   SAD_INI_EDIT,");
            sql.addSql("   SAD_REPEAT_STYPE,");
            sql.addSql("   SAD_REPEAT_KBN,");
            sql.addSql("   SAD_REPEAT_MY_KBN,");
            sql.addSql("   SAD_AUID,");
            sql.addSql("   SAD_ADATE,");
            sql.addSql("   SAD_EUID,");
            sql.addSql("   SAD_EDATE");
            sql.addSql(" from ");
            sql.addSql("   SCH_ADM_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = new RsvSchAdmConfModel();
                bean.setSadCrange(rs.getInt("SAD_CRANGE"));
                bean.setSadAtdelFlg(rs.getInt("SAD_ATDEL_FLG"));
                bean.setSadAtdelY(rs.getInt("SAD_ATDEL_Y"));
                bean.setSadAtdelM(rs.getInt("SAD_ATDEL_M"));
                bean.setSadHourDiv(rs.getInt("SAD_HOUR_DIV"));
                bean.setSadSortKbn(rs.getInt("SAD_SORT_KBN"));
                bean.setSadSortKey1(rs.getInt("SAD_SORT_KEY1"));
                bean.setSadSortOrder1(rs.getInt("SAD_SORT_ORDER1"));
                bean.setSadSortKey2(rs.getInt("SAD_SORT_KEY2"));
                bean.setSadSortOrder2(rs.getInt("SAD_SORT_ORDER2"));
                bean.setSadIniEditStype(rs.getInt("SAD_INI_EDIT_STYPE"));
                bean.setSadIniEdit(rs.getInt("SAD_INI_EDIT"));
                bean.setSadRepeatStype(rs.getInt("SAD_REPEAT_STYPE"));
                bean.setSadRepeatKbn(rs.getInt("SAD_REPEAT_KBN"));
                bean.setSadRepeatMyKbn(rs.getInt("SAD_REPEAT_MY_KBN"));
                bean.setSadAuid(rs.getInt("SAD_AUID"));
                bean.setSadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SAD_ADATE")));
                bean.setSadEuid(rs.getInt("SAD_EUID"));
                bean.setSadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SAD_EDATE")));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return bean;
    }

    /**
     * <p>Select SCH_PRI_CONF
     * @param usid ユーザSID
     * @return SCH_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public RsvSchPriConfModel getPriConf(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSchPriConfModel bean = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   SCC_FR_DATE,");
            sql.addSql("   SCC_TO_DATE,");
            sql.addSql("   SCC_DSP_GROUP,");
            sql.addSql("   SCC_SORT_KEY1,");
            sql.addSql("   SCC_SORT_ORDER1,");
            sql.addSql("   SCC_SORT_KEY2,");
            sql.addSql("   SCC_SORT_ORDER2,");
            sql.addSql("   SCC_INI_FR_DATE,");
            sql.addSql("   SCC_INI_TO_DATE,");
            sql.addSql("   SCC_INI_PUBLIC,");
            sql.addSql("   SCC_INI_FCOLOR,");
            sql.addSql("   SCC_INI_EDIT,");
            sql.addSql("   SCC_DSP_LIST,");
            sql.addSql("   SCC_SMAIL,");
            sql.addSql("   SCC_SMAIL_GROUP,");
            sql.addSql("   SCC_RELOAD,");
            sql.addSql("   SCC_AUID,");
            sql.addSql("   SCC_ADATE,");
            sql.addSql("   SCC_EUID,");
            sql.addSql("   SCC_EDATE,");
            sql.addSql("   SCC_DSP_MYGROUP,");
            sql.addSql("   SCC_INI_WEEK,");
            sql.addSql("   SCC_SORT_EDIT,");
            sql.addSql("   SCC_REPEAT_MY_KBN,");
            sql.addSql("   SCC_REPEAT_KBN,");
            sql.addSql("   SCC_DEF_DSP");
            sql.addSql(" from");
            sql.addSql("   SCH_PRI_CONF");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                bean = new RsvSchPriConfModel();
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setSccFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_FR_DATE")));
                bean.setSccToDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_TO_DATE")));
                bean.setSccDspGroup(rs.getInt("SCC_DSP_GROUP"));
                bean.setSccSortKey1(rs.getInt("SCC_SORT_KEY1"));
                bean.setSccSortOrder1(rs.getInt("SCC_SORT_ORDER1"));
                bean.setSccSortKey2(rs.getInt("SCC_SORT_KEY2"));
                bean.setSccSortOrder2(rs.getInt("SCC_SORT_ORDER2"));
                bean.setSccIniFrDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("SCC_INI_FR_DATE")));
                bean.setSccIniToDate(
                        UDate.getInstanceTimestamp(rs.getTimestamp("SCC_INI_TO_DATE")));
                bean.setSccIniPublic(rs.getInt("SCC_INI_PUBLIC"));
                bean.setSccIniFcolor(rs.getInt("SCC_INI_FCOLOR"));
                bean.setSccIniEdit(rs.getInt("SCC_INI_EDIT"));
                bean.setSccDspList(rs.getInt("SCC_DSP_LIST"));
                bean.setSccSmail(rs.getInt("SCC_SMAIL"));
                bean.setSccSmailGroup(rs.getInt("SCC_SMAIL_GROUP"));
                bean.setSccReload(rs.getInt("SCC_RELOAD"));
                bean.setSccAuid(rs.getInt("SCC_AUID"));
                bean.setSccAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_ADATE")));
                bean.setSccEuid(rs.getInt("SCC_EUID"));
                bean.setSccEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCC_EDATE")));
                bean.setSccDspMygroup(rs.getInt("SCC_DSP_MYGROUP"));
                bean.setSccIniWeek(rs.getInt("SCC_INI_WEEK"));
                bean.setSccSortEdit(rs.getInt("SCC_SORT_EDIT"));
                bean.setSccRepeatKbn(rs.getInt("SCC_REPEAT_KBN"));
                bean.setSccRepeatMyKbn(rs.getInt("SCC_REPEAT_MY_KBN"));
                bean.setSccDefDsp(rs.getInt("SCC_DEF_DSP"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return bean;
    }

    /**
     * <p> 指定した期間のスケジュールを取得する
     * <p> 非公開スケジュールを除く
     * <p> 指定したスケジュールSIDを除く
     * <p> 指定した同時登録グループSIDを除く
     * @param usrSidList ユーザリスト
     * @param scdRsSid スケジュール予約SID 新規登録の場合:0
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @param copyFlg 複写フラグ
     * @return SCH_DATAModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvScdOperationModel> getSchData(List<Integer> usrSidList,
            int[] scdRsSid,
            UDate frDate,
            UDate toDate,
            boolean copyFlg) throws SQLException {

        if (usrSidList == null || usrSidList.size() < 1) {
            return null;
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RsvScdOperationModel> ret = new ArrayList<RsvScdOperationModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCH_DATA.SCD_SID as SCD_SID,");
            sql.addSql("   SCH_DATA.SCD_USR_SID as SCD_USR_SID,");
            sql.addSql("   SCH_DATA.SCD_GRP_SID as SCD_GRP_SID,");
            sql.addSql("   SCH_DATA.SCD_USR_KBN as SCD_USR_KBN,");
            sql.addSql("   SCH_DATA.SCD_FR_DATE as SCD_FR_DATE,");
            sql.addSql("   SCH_DATA.SCD_TO_DATE as SCD_TO_DATE,");
            sql.addSql("   SCH_DATA.SCD_DAILY as SCD_DAILY,");
            sql.addSql("   SCH_DATA.SCD_BGCOLOR as SCD_BGCOLOR,");
            sql.addSql("   SCH_DATA.SCD_TITLE as SCD_TITLE,");
            sql.addSql("   SCH_DATA.SCD_VALUE as SCD_VALUE,");
            sql.addSql("   SCH_DATA.SCD_BIKO as SCD_BIKO,");
            sql.addSql("   SCH_DATA.SCD_PUBLIC as SCD_PUBLIC,");
            sql.addSql("   SCH_DATA.SCD_AUID as SCD_AUID,");
            sql.addSql("   SCH_DATA.SCD_ADATE as SCD_ADATE,");
            sql.addSql("   SCH_DATA.SCD_EUID as SCD_EUID,");
            sql.addSql("   SCH_DATA.SCD_EDATE as SCD_EDATE,");
            sql.addSql("   SCH_DATA.SCE_SID as SCE_SID,");
            sql.addSql("   SCH_DATA.SCD_EDIT as SCD_EDIT,");
            sql.addSql("   SCH_DATA.SCD_RSSID as SCD_RSSID,");
            sql.addSql("   SCH_DATA.SCD_TARGET_GRP as SCD_TARGET_GRP,");
            sql.addSql("   SCH_DATA.SCD_REMINDER as SCD_REMINDER,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("      SCD_FR_DATE >= ?");
            sql.addSql("      and");
            sql.addSql("      SCD_FR_DATE < ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      SCD_TO_DATE > ?");
            sql.addSql("      and");
            sql.addSql("      SCD_TO_DATE <= ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? >= SCD_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? < SCD_TO_DATE");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? > SCD_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? <= SCD_TO_DATE");
            sql.addSql("     )");
            sql.addSql("   )");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);

            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_USR_SID=CMN_USRM_INF.USR_SID");
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_USR_KBN = ?");
            sql.addIntValue(GSConstSchedule.USER_KBN_USER);
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_PUBLIC <> ?");
            sql.addIntValue(GSConstSchedule.DSP_NOT_PUBLIC);
            sql.addSql(" and");
            sql.addSql("   SCH_DATA.SCD_DAILY <> ?");
            sql.addIntValue(GSConstSchedule.TIME_NOT_EXIST);

            sql.addSql(" and");
            sql.addSql("    (");
            int i = 0;
            for (Integer usrSid : usrSidList) {
                if (i > 0) {
                    sql.addSql(" or");
                }
                sql.addSql("   SCH_DATA.SCD_USR_SID=?");
                sql.addIntValue(usrSid);
                i++;
            }
            sql.addSql("    )");

            if (scdRsSid != null && scdRsSid.length > 0 && !copyFlg) {
                sql.addSql(" and");
                sql.addSql("   SCH_DATA.SCD_RSSID not in (");
                for (int idx = 0; idx < scdRsSid.length; idx++) {
                    if (idx > 0) {
                        sql.addSql("   ,?");
                    } else {
                        sql.addSql("   ?");
                    }
                    sql.addIntValue(scdRsSid[idx]);
                }
                sql.addSql("   )");
            }

            sql.addSql(" order by");
            sql.addSql("   SCH_DATA.SCD_FR_DATE");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RsvScdOperationModel bean = __getSchDataFromRs(rs);
                bean.setScdUserName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                ret.add(bean);
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
     * <p>指定したユーザから指定した重複区分を設定しているユーザSIDリストを取得します。
     * @param usidList ユーザSIDリスト
     * @param repeatKbn リピート区分
     * @return SCH_PRI_CONFModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getUsrSidListRepeatKbn(List<Integer> usidList, int repeatKbn)
    throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        if (usidList == null || usidList.size() < 1) {
            return ret;
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_PRI_CONF");

            sql.addSql(" where ");
            sql.addSql("   SCC_REPEAT_KBN=?");
            sql.addIntValue(repeatKbn);
            sql.addSql(" and (");
            int i = 0;
            for (Integer usrSid : usidList) {
                if (i > 0) {
                    sql.addSql(" or");
                }
                sql.addSql("   USR_SID=?");
                sql.addIntValue(usrSid);
                i++;
            }
            sql.addSql(" )");


            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("USR_SID"));
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
     * <br>[機  能] 指定されたスケジュール拡張SIDの
     * <br>         スケジュールSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sceSid スケジュール拡張SID
     * @return  スケジュールSID
     * @throws SQLException 例外
     */
    public List<Integer> getScdSid(int sceSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        List<Integer> scdList = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                scdList.add(rs.getInt("SCD_SID"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return scdList;
    }

    /**
     * <br>[機  能] スケジュールSIDに対応する公開対象を削除
     * <br>[解  説] 
     * <br>[備  考] 
     * @param scdSid 施設予約SID
     * @return 公開対象リスト
     * @throws SQLException SQL実行例外
     */
    public int deleteSdpData(int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

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
     * <br>[機  能] スケジュールSIDに対応する公開対象を削除
     * <br>[解  説] 
     * <br>[備  考] 
     * @param scdSidList 施設予約SID
     * @return 公開対象リスト
     * @throws SQLException SQL実行例外
     */
    public int deleteSdpData(List<Integer> scdSidList) throws SQLException {

        int count = 0;
        if (scdSidList == null || scdSidList.size() < 1) {
            return count;
        }
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   SCH_DATA_PUB");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID in (");
            for (int idx = 0; idx < scdSidList.size(); idx++) {
                sql.addSql("     ?");
                sql.addIntValue(scdSidList.get(idx));
                if (idx < scdSidList.size() - 1) {
                    sql.addSql("  ,");
                }
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

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
     * <br>[機  能] リザルトセットの値をモデルにセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rs リザルトセット
     * @return bean
     * @throws SQLException SQL実行例外
     */
    private RsvScdOperationModel __getSchDataFromRs(ResultSet rs) throws SQLException {
        RsvScdOperationModel bean = new RsvScdOperationModel();
        bean.setScdSid(rs.getInt("SCD_SID"));
        bean.setScdUsrSid(rs.getInt("SCD_USR_SID"));
        bean.setScdGrpSid(rs.getInt("SCD_GRP_SID"));
        bean.setScdUsrKbn(rs.getInt("SCD_USR_KBN"));
        bean.setScdFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_FR_DATE")));
        bean.setScdToDate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_TO_DATE")));
        bean.setScdDaily(rs.getInt("SCD_DAILY"));
        bean.setScdBgcolor(rs.getInt("SCD_BGCOLOR"));
        bean.setScdTitle(rs.getString("SCD_TITLE"));
        bean.setScdValue(rs.getString("SCD_VALUE"));
        bean.setScdBiko(rs.getString("SCD_BIKO"));
        bean.setScdPublic(rs.getInt("SCD_PUBLIC"));
        bean.setScdAuid(rs.getInt("SCD_AUID"));
        bean.setScdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_ADATE")));
        bean.setScdEuid(rs.getInt("SCE_SID"));
        bean.setScdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("SCD_EDATE")));
        bean.setScdEdit(rs.getInt("SCD_EDIT"));
        bean.setScdRsSid(rs.getInt("SCD_RSSID"));
        bean.setScdTargetGrp(rs.getInt("SCD_TARGET_GRP"));
        bean.setScdReminder(rs.getInt("SCD_REMINDER"));
        return bean;
    }
}