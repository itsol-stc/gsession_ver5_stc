package jp.groupsession.v2.rsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.RsvWeekModelBeforConv;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.rsv010.Rsv010SearchModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100Biz;
import jp.groupsession.v2.rsv.rsv100.Rsv100SisYrkModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100searchModel;
import jp.groupsession.v2.rsv.rsv110.Rsv110SisetuModel;

/**
 * <p>RSV_SIS_YRK Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvSisYrkDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSisYrkDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvSisYrkDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvSisYrkDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSV_SIS_YRK Data Bindding JavaBean
     * @param bean RSV_SIS_YRK Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvSisYrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_YRK(");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_YGRP_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN,");
            sql.addSql("   RSY_APPR_UID,");
            sql.addSql("   RSY_APPR_DATE,");
            sql.addSql("   RSY_PUBLIC ");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsySid());
            sql.addIntValue(bean.getRsdSid());
            sql.addIntValue(bean.getRsyYgrpSid());
            sql.addStrValue(bean.getRsyMok());
            sql.addDateValue(bean.getRsyFrDate());
            sql.addDateValue(bean.getRsyToDate());
            sql.addStrValue(bean.getRsyBiko());
            sql.addIntValue(bean.getRsyAuid());
            sql.addDateValue(bean.getRsyAdate());
            sql.addIntValue(bean.getRsyEuid());
            sql.addDateValue(bean.getRsyEdate());
            sql.addIntValue(bean.getScdRsSid());
            sql.addIntValue(bean.getRsyEdit());
            sql.addIntValue(bean.getRsrRsid());
            sql.addIntValue(bean.getRsyApprStatus());
            sql.addIntValue(bean.getRsyApprKbn());
            sql.addIntValue(bean.getRsyApprUid());
            sql.addDateValue(bean.getRsyApprDate());
            sql.addIntValue(bean.getRsyPublic());

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
     * <p>Insert RSV_SIS_YRK Data Bindding JavaBean
     * @param beanList RSV_SIS_YRK DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<RsvSisYrkModel> beanList) throws SQLException {

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<RsvSisYrkModel> exeList = new ArrayList<>();
        Iterator<RsvSisYrkModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" RSV_SIS_YRK(");
        sb.append("   RSY_SID,");
        sb.append("   RSD_SID,");
        sb.append("   RSY_YGRP_SID,");
        sb.append("   RSY_MOK,");
        sb.append("   RSY_FR_DATE,");
        sb.append("   RSY_TO_DATE,");
        sb.append("   RSY_BIKO,");
        sb.append("   RSY_AUID,");
        sb.append("   RSY_ADATE,");
        sb.append("   RSY_EUID,");
        sb.append("   RSY_EDATE,");
        sb.append("   SCD_RSSID,");
        sb.append("   RSY_EDIT,");
        sb.append("   RSR_RSID,");
        sb.append("   RSY_APPR_STATUS,");
        sb.append("   RSY_APPR_KBN,");
        sb.append("   RSY_APPR_UID,");
        sb.append("   RSY_APPR_DATE,");
        sb.append("   RSY_PUBLIC");
        sb.append(" )");
        sb.append(" values");


        Connection con = null;
        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件分インサート
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());

            Iterator<RsvSisYrkModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                RsvSisYrkModel bean = exeItr.next();
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
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getRsySid());
                sql.addIntValue(bean.getRsdSid());
                sql.addIntValue(bean.getRsyYgrpSid());
                sql.addStrValue(bean.getRsyMok());
                sql.addDateValue(bean.getRsyFrDate());
                sql.addDateValue(bean.getRsyToDate());
                sql.addStrValue(bean.getRsyBiko());
                sql.addIntValue(bean.getRsyAuid());
                sql.addDateValue(bean.getRsyAdate());
                sql.addIntValue(bean.getRsyEuid());
                sql.addDateValue(bean.getRsyEdate());
                sql.addIntValue(bean.getScdRsSid());
                sql.addIntValue(bean.getRsyEdit());
                sql.addIntValue(bean.getRsrRsid());
                sql.addIntValue(bean.getRsyApprStatus());
                sql.addIntValue(bean.getRsyApprKbn());
                sql.addIntValue(bean.getRsyApprUid());
                sql.addDateValue(bean.getRsyApprDate());
                sql.addIntValue(bean.getRsyPublic());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                pstmt.executeUpdate();

            }
            exeList.clear();
        }
    }

    /**
     * <br>[機  能] 新規予約情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 登録モデル
     * @throws SQLException SQL実行例外
     */
    @Deprecated
    public void insertNewYoyaku(RsvSisYrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_YRK(");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN,");
            sql.addSql("   RSY_PUBLIC ");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsySid());
            sql.addIntValue(bean.getRsdSid());
            sql.addStrValue(bean.getRsyMok());
            sql.addDateValue(bean.getRsyFrDate());
            sql.addDateValue(bean.getRsyToDate());
            sql.addStrValue(bean.getRsyBiko());
            sql.addIntValue(bean.getScdRsSid());
            sql.addIntValue(bean.getRsyAuid());
            sql.addDateValue(bean.getRsyAdate());
            sql.addIntValue(bean.getRsyEuid());
            sql.addDateValue(bean.getRsyEdate());
            sql.addIntValue(bean.getRsyEdit());
            sql.addIntValue(bean.getRsrRsid());
            sql.addIntValue(bean.getRsyApprStatus());
            sql.addIntValue(bean.getRsyApprKbn());
            sql.addIntValue(bean.getRsyPublic());
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
     * <br>         データを登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param updArray 更新リスト
     * @throws SQLException 例外
     */
    @Deprecated
    public void insertNewYoyakuPlural2(ArrayList<RsvSisYrkModel> updArray)
        throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_SIS_YRK(");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSY_PUBLIC,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (RsvSisYrkModel mdl : updArray) {
                pstmt.setInt(1, mdl.getRsySid());
                pstmt.setInt(2, mdl.getRsdSid());
                pstmt.setString(3, mdl.getRsyMok());
                pstmt.setTimestamp(4, mdl.getRsyFrDate().toSQLTimestamp());
                pstmt.setTimestamp(5, mdl.getRsyToDate().toSQLTimestamp());
                pstmt.setString(6, mdl.getRsyBiko());
                pstmt.setInt(7, mdl.getRsyAuid());
                pstmt.setTimestamp(8, mdl.getRsyAdate().toSQLTimestamp());
                pstmt.setInt(9, mdl.getRsyEuid());
                pstmt.setTimestamp(10, mdl.getRsyEdate().toSQLTimestamp());
                pstmt.setInt(11, mdl.getRsyEdit());
                pstmt.setInt(12, mdl.getRsyPublic());
                pstmt.setInt(13, mdl.getScdRsSid());
                pstmt.setInt(14, mdl.getRsrRsid());
                pstmt.setInt(15, mdl.getRsyApprStatus());
                pstmt.setInt(16, mdl.getRsyApprKbn());

                //ログ出力
                sql.addIntValue(mdl.getRsySid());
                sql.addIntValue(mdl.getRsdSid());
                sql.addStrValue(mdl.getRsyMok());
                sql.addDateValue(mdl.getRsyFrDate());
                sql.addDateValue(mdl.getRsyToDate());
                sql.addStrValue(mdl.getRsyBiko());
                sql.addIntValue(mdl.getRsyAuid());
                sql.addDateValue(mdl.getRsyAdate());
                sql.addIntValue(mdl.getRsyEuid());
                sql.addDateValue(mdl.getRsyEdate());
                sql.addIntValue(mdl.getRsyEdit());
                sql.addIntValue(mdl.getRsyPublic());
                sql.addIntValue(mdl.getScdRsSid());
                sql.addIntValue(mdl.getRsrRsid());
                sql.addIntValue(mdl.getRsyApprStatus());
                sql.addIntValue(mdl.getRsyApprKbn());

                log__.info(sql.toLogString());
                sql.clearValue();

                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update RSV_SIS_YRK Data Bindding JavaBean
     * @param bean RSV_SIS_YRK Data Bindding JavaBean
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int update(RsvSisYrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" set ");
            sql.addSql("   RSD_SID=?,");
            sql.addSql("   RSY_YGRP_SID=?,");
            sql.addSql("   RSY_MOK=?,");
            sql.addSql("   RSY_FR_DATE=?,");
            sql.addSql("   RSY_TO_DATE=?,");
            sql.addSql("   RSY_BIKO=?,");
            sql.addSql("   RSY_AUID=?,");
            sql.addSql("   RSY_ADATE=?,");
            sql.addSql("   RSY_EUID=?,");
            sql.addSql("   RSY_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsdSid());
            sql.addIntValue(bean.getRsyYgrpSid());
            sql.addStrValue(bean.getRsyMok());
            sql.addDateValue(bean.getRsyFrDate());
            sql.addDateValue(bean.getRsyToDate());
            sql.addStrValue(bean.getRsyBiko());
            sql.addIntValue(bean.getRsyAuid());
            sql.addDateValue(bean.getRsyAdate());
            sql.addIntValue(bean.getRsyEuid());
            sql.addDateValue(bean.getRsyEdate());
            //where
            sql.addIntValue(bean.getRsySid());

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
     * <br>[機  能] 予約情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param bean 更新モデル
     * @return count update count
     * @throws SQLException SQL実行例外
     */
    public int updateYoyakuData(RsvSisYrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" set ");
            sql.addSql("   RSY_MOK=?,");
            sql.addSql("   RSY_FR_DATE=?,");
            sql.addSql("   RSY_TO_DATE=?,");
            sql.addSql("   RSY_BIKO=?,");
            sql.addSql("   RSY_EUID=?,");
            sql.addSql("   RSY_EDATE=?,");
            sql.addSql("   SCD_RSSID=?,");
            sql.addSql("   RSY_EDIT=?,");
            sql.addSql("   RSY_APPR_STATUS=?,");
            sql.addSql("   RSY_APPR_KBN=?,");
            sql.addSql("   RSY_PUBLIC=?");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getRsyMok());
            sql.addDateValue(bean.getRsyFrDate());
            sql.addDateValue(bean.getRsyToDate());
            sql.addStrValue(bean.getRsyBiko());
            sql.addIntValue(bean.getRsyEuid());
            sql.addDateValue(bean.getRsyEdate());
            sql.addIntValue(bean.getScdRsSid());
            sql.addIntValue(bean.getRsyEdit());
            sql.addIntValue(bean.getRsyApprStatus());
            sql.addIntValue(bean.getRsyApprKbn());
            sql.addIntValue(bean.getRsyPublic());
            //where
            sql.addIntValue(bean.getRsySid());

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
     * <br>[機  能] 承認状況、承認区分を更新する
     * <br>[解  説]
     * <br>[備  考] 承認状況は[通常]に更新する
     *                     承認待ちに戻された場合は[承認待ち]になる
     * @param rsySid 施設予約情報SID
     * @param apprKbn 承認区分
     * @param apprUid 承認ユーザID
     * @param apprDate 承認日時
     * @throws SQLException SQL実行例外
     */
    public void updateYoyakuAppr(int rsySid, int apprKbn, int apprUid, UDate apprDate)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" set ");
            sql.addSql("   RSY_EUID=?,");
            sql.addSql("   RSY_EDATE=?,");
            sql.addSql("   RSY_APPR_STATUS=?,");
            sql.addSql("   RSY_APPR_KBN=?,");
            sql.addSql("   RSY_APPR_UID=?,");
            sql.addSql("   RSY_APPR_DATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(apprUid);
            sql.addDateValue(apprDate);
            if (apprKbn == 0) {
                sql.addIntValue(GSConstReserve.RSY_APPR_STATUS_NOAPPR);
            } else {
                sql.addIntValue(GSConstReserve.RSY_APPR_STATUS_NORMAL);
            }
            sql.addIntValue(apprKbn);
            sql.addIntValue(apprUid);
            sql.addDateValue(apprDate);
            //where
            sql.addIntValue(rsySid);

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
     * <p>Select RSV_SIS_YRK All Data
     * @return List in RSV_SIS_YRKModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisYrkModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisYrkModel> ret = new ArrayList<RsvSisYrkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_YGRP_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSY_PUBLIC,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN,");
            sql.addSql("   RSY_APPR_UID,");
            sql.addSql("   RSY_APPR_DATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_YRK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisYrkFromRs(rs));
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
     * <p>Select RSV_SIS_YRK
     * @param rsySid RSY_SID value
     * @return List in RSY_SID
     * @throws SQLException SQL実行例外
     */
    public RsvSisYrkModel select(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisYrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_YGRP_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSY_PUBLIC,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN,");
            sql.addSql("   RSY_APPR_UID,");
            sql.addSql("   RSY_APPR_DATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getRsvSisYrkFromRs(rs);
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
     * <p>Select RSV_SIS_YRK
     * @param rsySid RSY_SID list
     * @return List in RSY_SID
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisYrkModel> select(Collection<Integer> rsySids) throws SQLException {
        Connection con = null;
        List<RsvSisYrkModel> ret= new ArrayList<>();
        if (rsySids== null || rsySids.isEmpty()) {
            return ret;
        }

        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = rsySids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select ");
        sb.append("   RSY_SID,");
        sb.append("   RSD_SID,");
        sb.append("   RSY_YGRP_SID,");
        sb.append("   RSY_MOK,");
        sb.append("   RSY_FR_DATE,");
        sb.append("   RSY_TO_DATE,");
        sb.append("   RSY_BIKO,");
        sb.append("   RSY_AUID,");
        sb.append("   RSY_ADATE,");
        sb.append("   RSY_EUID,");
        sb.append("   RSY_EDATE,");
        sb.append("   SCD_RSSID,");
        sb.append("   RSY_EDIT,");
        sb.append("   RSY_PUBLIC,");
        sb.append("   RSR_RSID,");
        sb.append("   RSY_APPR_STATUS,");
        sb.append("   RSY_APPR_KBN,");
        sb.append("   RSY_APPR_UID,");
        sb.append("   RSY_APPR_DATE");
        sb.append(" from ");
        sb.append("   RSV_SIS_YRK");
        sb.append(" where");

        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" RSY_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addLongValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        RsvSisYrkModel mdl = __getRsvSisYrkFromRs(rs);
                        ret.add(mdl);
                    }

                }

            }
            exeList.clear();
        }
        return ret;
    }

    /**
     * <p>Select RSV_SIS_YRK All Data
     * @param offset レコードの読取開始行
     * @param limit 1ページの最大件数
     * @return List in RSV_SIS_YRKModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisYrkModel> selectLimit(
            int offset, int limit) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisYrkModel> ret = new ArrayList<RsvSisYrkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_YGRP_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSY_PUBLIC,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN,");
            sql.addSql("   RSY_APPR_UID,");
            sql.addSql("   RSY_APPR_DATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" order by ");
            sql.addSql("   RSY_SID asc");

            sql.setPagingValue(offset, limit);

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisYrkFromRs(rs));
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
     * <p>count RSV_SIS_YRK All Data
     * @return List in RSV_SIS_YRKModel
     * @throws SQLException SQL実行例外
     */
    public int count() throws SQLException {

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
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_YRK");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] データサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return データサイズ集計
     * @throws SQLException SQL実行時例外
     */
    public long getTotalDataSize() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long dataSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RSY_MOK)) as SIZE1,");
            sql.addSql("   sum(octet_length(RSY_BIKO)) as SIZE2");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dataSize = rs.getLong("SIZE1");
                dataSize += rs.getLong("SIZE2");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return dataSize;
    }

    /**
     * <p>スケジュールと同時登録している施設予約情報を取得する
     * @param scdRsSid スケジュール施設SID
     * @return ArrayList in RSV_SIS_YRKModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvSisYrkModel> getScheduleRserveData(int scdRsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvSisYrkModel> ret = new ArrayList<RsvSisYrkModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_YGRP_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSY_PUBLIC,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN,");
            sql.addSql("   RSY_APPR_UID,");
            sql.addSql("   RSY_APPR_DATE");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvSisYrkFromRs(rs));
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
     * <p>スケジュールと同時登録している施設予約SIDを取得する
     * @param scdRsSid スケジュール施設SID
     * @return ArrayList in rsySid
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getScheduleRserveSids(int scdRsSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSY_SID");
            sql.addSql(" from ");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <p>Select RSV_SIS_YRK
     * @param bean RSV_SIS_YRK Model
     * @return RSV_SIS_YRKModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisYrkModel select(RsvSisYrkModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisYrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_YGRP_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSY_PUBLIC,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN,");
            sql.addSql("   RSY_APPR_UID,");
            sql.addSql("   RSY_APPR_DATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsySid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisYrkFromRs(rs);
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
     * <p>施設SIDと同時登録スケジュールから施設予約情報を取得する。
     * @param rsdSid RSD_SID
     * @param scdRssid SCD_RSSID
     * @return RSV_SIS_YRKModel
     * @throws SQLException SQL実行例外
     */
    public RsvSisYrkModel select(int rsdSid, int scdRssid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvSisYrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID,");
            sql.addSql("   RSD_SID,");
            sql.addSql("   RSY_YGRP_SID,");
            sql.addSql("   RSY_MOK,");
            sql.addSql("   RSY_FR_DATE,");
            sql.addSql("   RSY_TO_DATE,");
            sql.addSql("   RSY_BIKO,");
            sql.addSql("   RSY_AUID,");
            sql.addSql("   RSY_ADATE,");
            sql.addSql("   RSY_EUID,");
            sql.addSql("   RSY_EDATE,");
            sql.addSql("   SCD_RSSID,");
            sql.addSql("   RSY_EDIT,");
            sql.addSql("   RSY_PUBLIC,");
            sql.addSql("   RSR_RSID,");
            sql.addSql("   RSY_APPR_STATUS,");
            sql.addSql("   RSY_APPR_KBN,");
            sql.addSql("   RSY_APPR_UID,");
            sql.addSql("   RSY_APPR_DATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");
            sql.addSql("   and");
            sql.addSql("   SCD_RSSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsdSid);
            sql.addIntValue(scdRssid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvSisYrkFromRs(rs);
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
     * <p>最小年、最大年を取得
     * @return RSV_SIS_YRKModel
     * @throws SQLException SQL実行例外
     */
    public Rsv100SisYrkModel selectMaxMin() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Rsv100SisYrkModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   min(year(RSV_SIS_YRK.RSY_FR_DATE)) as MIN_YEAR,");
            sql.addSql("   max(year(RSV_SIS_YRK.RSY_TO_DATE)) as MAX_YEAR");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                Rsv100SisYrkModel model = new Rsv100SisYrkModel();
                model.setRsyMinYear(rs.getString("MIN_YEAR"));
                model.setRsyMaxYear(rs.getString("MAX_YEAR"));
                ret = model;
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
     * <p>Delete RSV_SIS_YRK
     * @param bean RSV_SIS_YRK Model
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(RsvSisYrkModel bean) throws SQLException {

        if (bean == null || bean.getRsySid() <= 0) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsySid());

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
     * <br>[機  能] 指定された拡張予約SIDの予約データを削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 拡張予約SID
     * @throws SQLException 例外
     */
    public void deleteRyrkData(int rsrRsid) throws SQLException {
        if (rsrRsid <= 0) {
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
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);

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
     * <br>[機  能] 指定された拡張予約SIDの予約データを削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsidList 拡張予約SID配列
     * @throws SQLException 例外
     */
    public void deleteRyrkData(ArrayList<Integer> rsrRsidList)
        throws SQLException {
        if (rsrRsidList == null || rsrRsidList.size() <= 0) {
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
     * <br>[機  能] 指定された拡張予約SIDの予約データを削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 拡張予約SID
     * @throws SQLException 例外
     */
    public void deleteKakutyoData(int rsrRsid) throws SQLException {
        if (rsrRsid <= 0) {
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
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSR_RSID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);

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
     * <br>[機  能] 指定された年月を経過した施設予約SIDリストを取得する
     * <br>[解  説] 管理者手動データ削除で削除対象の施設予約SIDを取得
     * <br>[備  考]
     *
     * @param delDate 日付
     * @return 施設予約SIDリスト
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getRsySidsDeleteForAdmin(UDate delDate) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSY_TO_DATE <= ?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(delDate);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <p>Delete RSV_SIS_YRK
     * @param delDate 日付
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteForAdmin(UDate delDate) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSY_TO_DATE <= ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(delDate);

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
     * <br>[機  能] 指定された施設の施設予約SIDを全て取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisetuSid 施設SID
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getRsySidListSetRsdSid(int sisetuSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSD_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sisetuSid);

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <br>[機  能] 指定された施設の予約情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisetuSid 施設SID
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int sisetuSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sisetuSid);
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
     * <br>[機  能] 指定されたスケジュール施設予約SIDの予約情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRsSid スケジュール施設予約SID
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteScdRsSid(int scdRsSid) throws SQLException {
        if (scdRsSid <= 0) {
            return 0;
        }

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   SCD_RSSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdRsSid);
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
     * <br>[機  能] 指定された施設の施設予約SIDを全て取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisetuSid 施設SID
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getRsySidListSetSisSids(ArrayList<Integer> sisetuSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSD_SID in (");

            for (int i = 0; i < sisetuSid.size(); i++) {

                int rsgSid = sisetuSid.get(i);
                if (i > 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(rsgSid);
            }

            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <br>[機  能] 指定された施設の予約情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisetuSid 施設SID
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(ArrayList<Integer> sisetuSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID in (");

            for (int i = 0; i < sisetuSid.size(); i++) {

                int rsgSid = sisetuSid.get(i);
                if (i > 0) {
                    sql.addSql(", ");
                }
                sql.addSql("?");
                sql.addIntValue(rsgSid);
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
     * <br>[機  能] 指定された施設予約SIDの予約情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySidList 施設予約SID
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteRsySid(ArrayList<Integer> rsySidList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSY_SID in (-1");

            for (int i = 0; i < rsySidList.size(); i++) {

                int rsgSid = rsySidList.get(i);

                sql.addSql(", ");
                sql.addSql("?");
                sql.addIntValue(rsgSid);
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
     * <br>[機  能] 施設ごとに指定された期間の予約情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param groupSid 条件 施設グループSID
     * @param sisetuSid 条件 施設SID
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret ArrayList in Rsv010WeekModelBeforConv
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvWeekModelBeforConv> getYrkList(int groupSid,
                                                        int sisetuSid,
                                                        UDate frDate,
                                                        UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvWeekModelBeforConv> ret =
            new ArrayList<RsvWeekModelBeforConv>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   grp.RSG_SID,");
            sql.addSql("   grp.RSK_SID,");
            sql.addSql("   sisData.RSD_SID,");
            sql.addSql("   sisData.RSD_NAME,");
            //施設情報
            sql.addSql("   sisData.RSD_ID,");
            sql.addSql("   sisData.RSD_SNUM,");
            sql.addSql("   sisData.RSD_PROP_1,");
            sql.addSql("   sisData.RSD_PROP_2,");
            sql.addSql("   sisData.RSD_PROP_3,");
            sql.addSql("   sisData.RSD_PROP_4,");
            sql.addSql("   sisData.RSD_PROP_5,");
            sql.addSql("   sisData.RSD_PROP_6,");
            sql.addSql("   sisData.RSD_PROP_7,");
            sql.addSql("   sisData.RSD_IMG_CMT1,");
            sql.addSql("   sisData.RSD_IMG_CMT2,");
            sql.addSql("   sisData.RSD_IMG_CMT3,");
            sql.addSql("   sisData.RSD_IMG_CMT4,");
            sql.addSql("   sisData.RSD_IMG_CMT5,");
            sql.addSql("   sisData.RSD_IMG_CMT6,");
            sql.addSql("   sisData.RSD_IMG_CMT7,");
            sql.addSql("   sisData.RSD_IMG_CMT8,");
            sql.addSql("   sisData.RSD_IMG_CMT9,");
            sql.addSql("   sisData.RSD_IMG_CMT10,");
            sql.addSql("   sisData.RSD_BIKO,");
            sql.addSql("   sisData.RSD_PLA_CMT,");
            sql.addSql("   sisData.RSD_APPR_KBN,");

            //施設表示区分
            sql.addSql("   sisData.RSD_ID_DF,");
            sql.addSql("   sisData.RSD_SNUM_DF,");
            sql.addSql("   sisData.RSD_PROP_1_DF,");
            sql.addSql("   sisData.RSD_PROP_2_DF,");
            sql.addSql("   sisData.RSD_PROP_3_DF,");
            sql.addSql("   sisData.RSD_PROP_4_DF,");
            sql.addSql("   sisData.RSD_PROP_5_DF,");
            sql.addSql("   sisData.RSD_PROP_6_DF,");
            sql.addSql("   sisData.RSD_PROP_7_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT1_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT2_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT3_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT4_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT5_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT6_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT7_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT8_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT9_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT10_DF,");
            sql.addSql("   sisData.RSD_BIKO_DF,");
            sql.addSql("   sisData.RSD_PLA_CMT_DF,");
            sql.addSql("   sisData.RSD_IMG_DF,");
            sql.addSql("   sisData.RSD_APPR_KBN_DF,");

            sql.addSql("   sisYrk.RSY_SID,");
            sql.addSql("   sisYrk.RSY_MOK,");
            sql.addSql("   sisYrk.RSY_BIKO,");
            sql.addSql("   sisYrk.RSY_FR_DATE,");
            sql.addSql("   sisYrk.RSY_TO_DATE,");
            sql.addSql("   sisYrk.RSY_APPR_STATUS,");
            sql.addSql("   sisYrk.RSY_APPR_KBN,");
            sql.addSql("   sisYrk.RSY_PUBLIC,");
            sql.addSql("   inf.USI_SEI,");
            sql.addSql("   inf.USI_MEI,");
            sql.addSql("   cusrm.USR_JKBN,");
            sql.addSql("   cusrm.USR_UKO_FLG");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     (RSV_SIS_DATA sisData left join RSV_SIS_GRP grp");
            sql.addSql("      on");
            sql.addSql("      sisData.RSG_SID = grp.RSG_SID");
            sql.addSql("     ) left join RSV_SIS_YRK sisYrk");
            sql.addSql("     on sisData.RSD_SID = sisYrk.RSD_SID");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_FR_DATE between ?");
            sql.addSql("           and ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_TO_DATE between ?");
            sql.addSql("           and ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("     ) left join CMN_USRM_INF inf");
            sql.addSql("     on sisYrk.RSY_AUID = inf.USR_SID");
            sql.addSql("     left join CMN_USRM cusrm");
            sql.addSql("     on sisYrk.RSY_AUID = cusrm.USR_SID");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            UDate frDatePlus = UDate.getInstance(frDate.getTimeMillis());
            frDatePlus.setMilliSecond(1);
            sql.addDateValue(frDatePlus);
            sql.addDateValue(toDate);

            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);

            boolean whereFlg = false;
            if (groupSid > 0) {
                sql.addSql(" where");
                sql.addSql("   grp.RSG_SID = ?");
                sql.addIntValue(groupSid);
                whereFlg = true;
            }

            if (sisetuSid > -1) {
                if (whereFlg) {
                    sql.addSql(" and");
                } else {
                    sql.addSql(" where");
                }
                sql.addSql("   sisYrk.RSD_SID = ?");
                sql.addIntValue(sisetuSid);
            }

            sql.addSql(" order by");
            sql.addSql("   grp.RSG_SORT asc,");
            sql.addSql("   sisData.RSD_SORT asc,");
            sql.addSql("   sisYrk.RSY_FR_DATE asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(getDataYrkAnalysis(rs));
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
     * <br>[機  能] 施設ごとに指定された期間の予約情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sMdl 条件
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret ArrayList in Rsv010WeekModelBeforConv
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvWeekModelBeforConv> getYrkList(Rsv010SearchModel sMdl,
                                                        UDate frDate,
                                                        UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int groupSid = sMdl.getGrpNarrowDown();
        ArrayList<RsvWeekModelBeforConv> ret =
            new ArrayList<RsvWeekModelBeforConv>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   grp.RSG_SID,");
            sql.addSql("   grp.RSK_SID,");
            sql.addSql("   sisData.RSD_SID,");
            sql.addSql("   sisData.RSD_NAME,");
            //施設情報
            sql.addSql("   sisData.RSD_ID,");
            sql.addSql("   sisData.RSD_SNUM,");
            sql.addSql("   sisData.RSD_PROP_1,");
            sql.addSql("   sisData.RSD_PROP_2,");
            sql.addSql("   sisData.RSD_PROP_3,");
            sql.addSql("   sisData.RSD_PROP_4,");
            sql.addSql("   sisData.RSD_PROP_5,");
            sql.addSql("   sisData.RSD_PROP_6,");
            sql.addSql("   sisData.RSD_PROP_7,");
            sql.addSql("   sisData.RSD_IMG_CMT1,");
            sql.addSql("   sisData.RSD_IMG_CMT2,");
            sql.addSql("   sisData.RSD_IMG_CMT3,");
            sql.addSql("   sisData.RSD_IMG_CMT4,");
            sql.addSql("   sisData.RSD_IMG_CMT5,");
            sql.addSql("   sisData.RSD_IMG_CMT6,");
            sql.addSql("   sisData.RSD_IMG_CMT7,");
            sql.addSql("   sisData.RSD_IMG_CMT8,");
            sql.addSql("   sisData.RSD_IMG_CMT9,");
            sql.addSql("   sisData.RSD_IMG_CMT10,");
            sql.addSql("   sisData.RSD_BIKO,");
            sql.addSql("   sisData.RSD_PLA_CMT,");
            sql.addSql("   sisData.RSD_APPR_KBN,");

            //施設表示区分
            sql.addSql("   sisData.RSD_ID_DF,");
            sql.addSql("   sisData.RSD_SNUM_DF,");
            sql.addSql("   sisData.RSD_PROP_1_DF,");
            sql.addSql("   sisData.RSD_PROP_2_DF,");
            sql.addSql("   sisData.RSD_PROP_3_DF,");
            sql.addSql("   sisData.RSD_PROP_4_DF,");
            sql.addSql("   sisData.RSD_PROP_5_DF,");
            sql.addSql("   sisData.RSD_PROP_6_DF,");
            sql.addSql("   sisData.RSD_PROP_7_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT1_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT2_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT3_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT4_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT5_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT6_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT7_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT8_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT9_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT10_DF,");
            sql.addSql("   sisData.RSD_BIKO_DF,");
            sql.addSql("   sisData.RSD_PLA_CMT_DF,");
            sql.addSql("   sisData.RSD_IMG_DF,");
            sql.addSql("   sisData.RSD_APPR_KBN_DF,");

            sql.addSql("   sisYrk.RSY_SID,");
            sql.addSql("   sisYrk.RSY_MOK,");
            sql.addSql("   sisYrk.RSY_BIKO,");
            sql.addSql("   sisYrk.RSY_FR_DATE,");
            sql.addSql("   sisYrk.RSY_TO_DATE,");
            sql.addSql("   sisYrk.RSY_APPR_STATUS,");
            sql.addSql("   sisYrk.RSY_APPR_KBN,");
            sql.addSql("   sisYrk.RSY_PUBLIC,");
            sql.addSql("   inf.USI_SEI,");
            sql.addSql("   inf.USI_MEI,");
            sql.addSql("   cusrm.USR_JKBN,");
            sql.addSql("   cusrm.USR_UKO_FLG");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     (RSV_SIS_DATA sisData left join RSV_SIS_GRP grp");
            sql.addSql("      on");
            sql.addSql("      sisData.RSG_SID = grp.RSG_SID");
            sql.addSql("     ) left join RSV_SIS_YRK sisYrk");
            sql.addSql("     on sisData.RSD_SID = sisYrk.RSD_SID");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_FR_DATE between ?");
            sql.addSql("           and ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_TO_DATE between ?");
            sql.addSql("           and ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("     ) left join CMN_USRM_INF inf");
            sql.addSql("     on sisYrk.RSY_AUID = inf.USR_SID");
            sql.addSql("     left join CMN_USRM cusrm");
            sql.addSql("     on sisYrk.RSY_AUID = cusrm.USR_SID");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            UDate frDatePlus = UDate.getInstance(frDate.getTimeMillis());
            frDatePlus.setMilliSecond(1);
            sql.addDateValue(frDatePlus);
            sql.addDateValue(toDate);

            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);
            sql.addSql(" where");
            if (groupSid > 0) {
                sql.addSql("   grp.RSG_SID = ?");
                sql.addIntValue(groupSid);
            } else {
                sql.addSql(" 1 = 1");
            }

            //キーワード検索
            keywordSiborikomi(sMdl, sql);

            //空き状況
            if (sMdl.getSisetuFree() == 1) {
                freeSiborikomi(sMdl, sql);
            }

            //喫煙
            if ((sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_HEYA
                    || sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_CAR)
                    && sMdl.getSisetuSmoky() != 0) {
                sql.addSql(" and");
                sql.addSql("   sisData.RSD_PROP_2 = ?");
                if (sMdl.getSisetuSmoky() == 1) {
                    sql.addStrValue("0");
                } else {
                    sql.addStrValue("1");
                }
            }

            //社外持ち出し
            if ((sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_BUPPIN
                    || sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_BOOK)
                    && sMdl.getSisetuTakeout() != 0) {
                sql.addSql(" and");
                sql.addSql("   sisData.RSD_PROP_3 = ?");
                if (sMdl.getSisetuTakeout() == 1) {
                    sql.addStrValue("0");
                } else {
                    sql.addStrValue("1");
                }
            }

            sql.addSql(" order by");
            sql.addSql("   grp.RSG_SORT asc,");
            sql.addSql("   sisData.RSD_SORT asc,");
            sql.addSql("   sisYrk.RSY_FR_DATE asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                //座席数
                if ((sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_HEYA
                        || sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_CAR)
                        && !StringUtil.isNullZeroString(sMdl.getSisetuChere())) {
                    String chere = rs.getString("RSD_PROP_1");
                    if (Long.parseLong(chere) < Long.parseLong(sMdl.getSisetuChere())) {
                        continue;
                    }
                }
                ret.add(getDataYrkAnalysis(rs));
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
     * <br>[機  能]絞り込み検索のキーワード部
     * <br>[解  説]
     * <br>[備  考]
     * @param sMdl Rsv010SearchModel
     * @param sql SqlBuffer
     * @return SqlBuffer
     */
    public SqlBuffer keywordSiborikomi(Rsv010SearchModel sMdl, SqlBuffer sql) {

        //キーワード
        List<String> keywordList = null;
        if (!StringUtil.isNullZeroString(sMdl.getSisetuKeyword())) {
            keywordList = new ArrayList<String>();
            String searchKey =
                StringUtil.substitute(sMdl.getSisetuKeyword(), "　", " ");
            StringTokenizer st = new StringTokenizer(searchKey, " ");
            while (st.hasMoreTokens()) {
                String str = st.nextToken();
                if (!StringUtil.isNullZeroString(str)) {
                    keywordList.add(str);
                }
            }
        }

        int targetSisan = sMdl.getSisetuKeywordSisan();
        int targetName = sMdl.getSisetuKeywordSisetu();
        int targetBiko = sMdl.getSisetuKeywordBiko();
        int targetNo = 0;
        if (sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_CAR) {
            targetNo = sMdl.getSisetuKeywordNo();
        }
        int targetIsbn = 0;
        if (sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_BOOK) {
            targetIsbn = sMdl.getSisetuKeywordIsbn();
        }
        int condition = sMdl.getKeyWordkbn();

        //キーワード・検索対象
        if ((targetSisan == 1 || targetName == 1
                || targetBiko == 1 || targetNo == 1 || targetIsbn == 1)
            && (keywordList != null && !keywordList.isEmpty())) {

            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");
            //資産管理番号
            if (targetSisan == 1) {
                for (int i = 0; i < keywordList.size(); i++) {
                    String key = keywordList.get(i);
                    sql.addSql("       (");
                    sql.addSql("         sisData.RSD_SNUM like '%"
                            + JDBCUtil.escapeForLikeSearch(key)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("       )");
                    if (keywordList.size() > 1) {
                        if (i != keywordList.size() - 1 && condition == 0) {
                            sql.addSql("       and");
                        } else if (i != keywordList.size() - 1 && condition == 1) {
                            sql.addSql("       or");
                        }
                    }
                }
            }
            //施設名
            if (targetName == 1) {
                if (targetSisan == 1) {
                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                }
                for (int i = 0; i < keywordList.size(); i++) {
                    String key = keywordList.get(i);
                    sql.addSql("       (");
                    sql.addSql("         sisData.RSD_NAME like '%"
                            + JDBCUtil.escapeForLikeSearch(key)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("     )");
                    if (keywordList.size() > 1) {
                        if (i != keywordList.size() - 1 && condition == 0) {
                            sql.addSql("       and");
                        } else if (i != keywordList.size() - 1 && condition == 1) {
                            sql.addSql("       or");
                        }
                    }
                }
            }
            //備考
            if (targetBiko == 1) {
                if (targetSisan == 1 || targetName == 1) {
                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                }
                for (int i = 0; i < keywordList.size(); i++) {
                    String key = keywordList.get(i);
                    sql.addSql("       (");
                    sql.addSql("         sisData.RSD_BIKO like '%"
                            + JDBCUtil.escapeForLikeSearch(key)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("     )");
                    if (keywordList.size() > 1) {
                        if (i != keywordList.size() - 1 && condition == 0) {
                            sql.addSql("       and");
                        } else if (i != keywordList.size() - 1 && condition == 1) {
                            sql.addSql("       or");
                        }
                    }
                }
            }
            //ナンバー
            if (targetNo == 1) {
                if (targetSisan == 1 || targetName == 1 || targetBiko == 1) {
                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                }
                for (int i = 0; i < keywordList.size(); i++) {
                    String key = keywordList.get(i);
                    sql.addSql("       (");
                    sql.addSql("         sisData.RSD_PROP_4 like '%"
                            + JDBCUtil.escapeForLikeSearch(key)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("     )");
                    if (keywordList.size() > 1) {
                        if (i != keywordList.size() - 1 && condition == 0) {
                            sql.addSql("       and");
                        } else if (i != keywordList.size() - 1 && condition == 1) {
                            sql.addSql("       or");
                        }
                    }
                }
            }
            //ISDN
            if (targetIsbn == 1) {
                if (targetSisan == 1 || targetName == 1 || targetBiko == 1 || targetNo == 1) {
                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                }
                for (int i = 0; i < keywordList.size(); i++) {
                    String key = keywordList.get(i);
                    sql.addSql("       (");
                    sql.addSql("         sisData.RSD_PROP_5 like '%"
                            + JDBCUtil.escapeForLikeSearch(key)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                    sql.addSql("     )");
                    if (keywordList.size() > 1) {
                        if (i != keywordList.size() - 1 && condition == 0) {
                            sql.addSql("       and");
                        } else if (i != keywordList.size() - 1 && condition == 1) {
                            sql.addSql("       or");
                        }
                    }
                }
            }
            sql.addSql("     )");
            sql.addSql("   )");
        }
        return sql;
    }

    /**
     * <br>[機  能]絞り込み検索の空き情報部
     * <br>[解  説]
     * <br>[備  考]
     * @param sMdl Rsv010SearchModel
     * @param sql SqlBuffer
     * @return SqlBuffer
     */
    public SqlBuffer freeSiborikomi(Rsv010SearchModel sMdl, SqlBuffer sql) {

        //空き情報
        UDate frAkiDate = new UDate();
        frAkiDate.setZeroHhMmSs();
        frAkiDate.setDay(sMdl.getSisetuFreeFromD());
        frAkiDate.setMonth(sMdl.getSisetuFreeFromMo());
        frAkiDate.setYear(sMdl.getSisetuFreeFromY());
        frAkiDate.setHour(sMdl.getSisetuFreeFromH());
        frAkiDate.setMinute(sMdl.getSisetuFreeFromMi());

        UDate toAkiDate = new UDate();
        toAkiDate.setZeroHhMmSs();
        toAkiDate.setDay(sMdl.getSisetuFreeToD());
        toAkiDate.setMonth(sMdl.getSisetuFreeToMo());
        toAkiDate.setYear(sMdl.getSisetuFreeToY());
        toAkiDate.setHour(sMdl.getSisetuFreeToH());
        toAkiDate.setMinute(sMdl.getSisetuFreeToMi());

        sql.addSql(" and");
        sql.addSql("   sisData.RSD_SID not in");
        sql.addSql("     (");
        sql.addSql("      select");
        sql.addSql("        RSD_SID");
        sql.addSql("      from");
        sql.addSql("        RSV_SIS_YRK");
        sql.addSql("      where");
        sql.addSql("        RSY_FR_DATE < ?");
        sql.addDateValue(toAkiDate);
        sql.addSql("      and");
        sql.addSql("        RSY_TO_DATE> ?");
        sql.addDateValue(frAkiDate);
        sql.addSql("      group by");
        sql.addSql("        RSD_SID");
        sql.addSql("     )");

        return sql;
    }

    /**
     * <br>[機  能]施設情報取得データ解析
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return RsvWeekModelBeforConv
     * @throws SQLException SQLException
     */
    public RsvWeekModelBeforConv getDataYrkAnalysis(ResultSet rs)
            throws SQLException {

        RsvWeekModelBeforConv mdl = new RsvWeekModelBeforConv();
        mdl.setRsgSid(rs.getInt("RSG_SID"));
        mdl.setRsdInfoSisetuKbnSid(rs.getInt("RSK_SID"));
        mdl.setRsdSid(rs.getInt("RSD_SID"));
        mdl.setRsdName(rs.getString("RSD_NAME"));

        //施設情報
        mdl.setRsdInfoSisetuId(rs.getString("RSD_ID"));
        mdl.setRsdInfoSisanKanri(rs.getString("RSD_SNUM"));
        mdl.setRsdInfoProp1Value(rs.getString("RSD_PROP_1"));
        mdl.setRsdInfoProp2Value(rs.getString("RSD_PROP_2"));
        mdl.setRsdInfoProp3Value(rs.getString("RSD_PROP_3"));
        mdl.setRsdInfoProp4Value(rs.getString("RSD_PROP_4"));
        mdl.setRsdInfoProp5Value(rs.getString("RSD_PROP_5"));
        mdl.setRsdInfoProp6Value(rs.getString("RSD_PROP_6"));
        mdl.setRsdInfoProp7Value(rs.getString("RSD_PROP_7"));
        mdl.setRsdInfoPlaceImgCom1(rs.getString("RSD_IMG_CMT1"));
        mdl.setRsdInfoPlaceImgCom2(rs.getString("RSD_IMG_CMT2"));
        mdl.setRsdInfoPlaceImgCom3(rs.getString("RSD_IMG_CMT3"));
        mdl.setRsdInfoPlaceImgCom4(rs.getString("RSD_IMG_CMT4"));
        mdl.setRsdInfoPlaceImgCom5(rs.getString("RSD_IMG_CMT5"));
        mdl.setRsdInfoPlaceImgCom6(rs.getString("RSD_IMG_CMT6"));
        mdl.setRsdInfoPlaceImgCom7(rs.getString("RSD_IMG_CMT7"));
        mdl.setRsdInfoPlaceImgCom8(rs.getString("RSD_IMG_CMT8"));
        mdl.setRsdInfoPlaceImgCom9(rs.getString("RSD_IMG_CMT9"));
        mdl.setRsdInfoPlaceImgCom10(rs.getString("RSD_IMG_CMT10"));
        mdl.setRsdInfoBiko(rs.getString("RSD_BIKO"));
        mdl.setRsdInfoPlaCom(rs.getString("RSD_PLA_CMT"));
        mdl.setRsdApprKbn(rs.getInt("RSD_APPR_KBN"));

        //施設表示区分
        mdl.setRsdInfoSisetuIdDspKbn(rs.getInt("RSD_ID_DF"));
        mdl.setRsdInfoSisanKanriDspKbn(rs.getInt("RSD_SNUM_DF"));
        mdl.setRsdInfoProp1ValueDspKbn(rs.getInt("RSD_PROP_1_DF"));
        mdl.setRsdInfoProp2ValueDspKbn(rs.getInt("RSD_PROP_2_DF"));
        mdl.setRsdInfoProp3ValueDspKbn(rs.getInt("RSD_PROP_3_DF"));
        mdl.setRsdInfoProp4ValueDspKbn(rs.getInt("RSD_PROP_4_DF"));
        mdl.setRsdInfoProp5ValueDspKbn(rs.getInt("RSD_PROP_5_DF"));
        mdl.setRsdInfoProp6ValueDspKbn(rs.getInt("RSD_PROP_6_DF"));
        mdl.setRsdInfoProp7ValueDspKbn(rs.getInt("RSD_PROP_7_DF"));
        mdl.setRsdInfoPlaceImgCom1DspKbn(rs.getInt("RSD_IMG_CMT1_DF"));
        mdl.setRsdInfoPlaceImgCom2DspKbn(rs.getInt("RSD_IMG_CMT2_DF"));
        mdl.setRsdInfoPlaceImgCom3DspKbn(rs.getInt("RSD_IMG_CMT3_DF"));
        mdl.setRsdInfoPlaceImgCom4DspKbn(rs.getInt("RSD_IMG_CMT4_DF"));
        mdl.setRsdInfoPlaceImgCom5DspKbn(rs.getInt("RSD_IMG_CMT5_DF"));
        mdl.setRsdInfoPlaceImgCom6DspKbn(rs.getInt("RSD_IMG_CMT6_DF"));
        mdl.setRsdInfoPlaceImgCom7DspKbn(rs.getInt("RSD_IMG_CMT7_DF"));
        mdl.setRsdInfoPlaceImgCom8DspKbn(rs.getInt("RSD_IMG_CMT8_DF"));
        mdl.setRsdInfoPlaceImgCom9DspKbn(rs.getInt("RSD_IMG_CMT9_DF"));
        mdl.setRsdInfoPlaceImgCom10DspKbn(rs.getInt("RSD_IMG_CMT10_DF"));
        mdl.setRsdInfoBikoDspKbn(rs.getInt("RSD_BIKO_DF"));
        mdl.setRsdInfoPlaComDspKbn(rs.getInt("RSD_PLA_CMT_DF"));
        mdl.setRsdInfoSisetuImgDspKbn(rs.getInt("RSD_IMG_DF"));
        mdl.setRsdApprDspKbn(rs.getInt("RSD_APPR_KBN_DF"));

        mdl.setRsySid(rs.getInt("RSY_SID"));
        mdl.setRsyMok(rs.getString("RSY_MOK"));
        mdl.setRsyNaiyo(rs.getString("RSY_BIKO"));
        mdl.setRsyFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE")));
        mdl.setRsyToDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_TO_DATE")));
        mdl.setRsyApprStatus(rs.getInt("RSY_APPR_STATUS"));
        mdl.setRsyApprKbn(rs.getInt("RSY_APPR_KBN"));
        mdl.setRsyPublic(rs.getInt("RSY_PUBLIC"));

        mdl.setUsiSei(rs.getString("USI_SEI"));
        mdl.setUsiMei(rs.getString("USI_MEI"));
        mdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        mdl.setUsrJkbn(rs.getInt("USR_JKBN"));

        return mdl;
    }

    /**
     * <br>[機  能] 施設ごとに指定された期間の予約情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSidList 条件 施設グループSIDリスト
     * @param sisetuSid 条件 施設SID
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret ArrayList in Rsv010WeekModelBeforConv
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvWeekModelBeforConv> getYrkList(List<Integer> grpSidList,
                                                        int sisetuSid,
                                                        UDate frDate,
                                                        UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvWeekModelBeforConv> ret =
            new ArrayList<RsvWeekModelBeforConv>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   grp.RSG_SID,");
            sql.addSql("   grp.RSK_SID,");
            sql.addSql("   sisData.RSD_SID,");
            sql.addSql("   sisData.RSD_NAME,");

            //施設情報
            sql.addSql("   sisData.RSD_ID,");
            sql.addSql("   sisData.RSD_SNUM,");
            sql.addSql("   sisData.RSD_PROP_1,");
            sql.addSql("   sisData.RSD_PROP_2,");
            sql.addSql("   sisData.RSD_PROP_3,");
            sql.addSql("   sisData.RSD_PROP_4,");
            sql.addSql("   sisData.RSD_PROP_5,");
            sql.addSql("   sisData.RSD_PROP_6,");
            sql.addSql("   sisData.RSD_PROP_7,");
            sql.addSql("   sisData.RSD_IMG_CMT1,");
            sql.addSql("   sisData.RSD_IMG_CMT2,");
            sql.addSql("   sisData.RSD_IMG_CMT3,");
            sql.addSql("   sisData.RSD_IMG_CMT4,");
            sql.addSql("   sisData.RSD_IMG_CMT5,");
            sql.addSql("   sisData.RSD_IMG_CMT6,");
            sql.addSql("   sisData.RSD_IMG_CMT7,");
            sql.addSql("   sisData.RSD_IMG_CMT8,");
            sql.addSql("   sisData.RSD_IMG_CMT9,");
            sql.addSql("   sisData.RSD_IMG_CMT10,");
            sql.addSql("   sisData.RSD_BIKO,");
            sql.addSql("   sisData.RSD_PLA_CMT,");

            //施設表示区分
            sql.addSql("   sisData.RSD_ID_DF,");
            sql.addSql("   sisData.RSD_SNUM_DF,");
            sql.addSql("   sisData.RSD_PROP_1_DF,");
            sql.addSql("   sisData.RSD_PROP_2_DF,");
            sql.addSql("   sisData.RSD_PROP_3_DF,");
            sql.addSql("   sisData.RSD_PROP_4_DF,");
            sql.addSql("   sisData.RSD_PROP_5_DF,");
            sql.addSql("   sisData.RSD_PROP_6_DF,");
            sql.addSql("   sisData.RSD_PROP_7_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT1_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT2_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT3_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT4_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT5_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT6_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT7_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT8_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT9_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT10_DF,");
            sql.addSql("   sisData.RSD_BIKO_DF,");
            sql.addSql("   sisData.RSD_PLA_CMT_DF,");
            sql.addSql("   sisData.RSD_IMG_DF,");
            sql.addSql("   sisData.RSD_APPR_KBN_DF,");

            sql.addSql("   sisYrk.RSY_SID,");
            sql.addSql("   sisYrk.RSY_MOK,");
            sql.addSql("   sisYrk.RSY_BIKO,");
            sql.addSql("   sisYrk.RSY_FR_DATE,");
            sql.addSql("   sisYrk.RSY_TO_DATE,");
            sql.addSql("   sisYrk.RSY_APPR_STATUS,");
            sql.addSql("   sisYrk.RSY_APPR_KBN,");
            sql.addSql("   sisYrk.RSY_PUBLIC,");
            sql.addSql("   inf.USI_SEI,");
            sql.addSql("   inf.USI_MEI,");
            sql.addSql("   curm.USR_JKBN,");
            sql.addSql("   curm.USR_UKO_FLG");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     (RSV_SIS_DATA sisData left join RSV_SIS_GRP grp");
            sql.addSql("      on");
            sql.addSql("      sisData.RSG_SID = grp.RSG_SID");
            sql.addSql("     ) left join RSV_SIS_YRK sisYrk");
            sql.addSql("     on sisData.RSD_SID = sisYrk.RSD_SID");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_FR_DATE between ?");
            sql.addSql("           and ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_TO_DATE between ?");
            sql.addSql("           and ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("     ) left join CMN_USRM_INF inf");
            sql.addSql("     on sisYrk.RSY_AUID = inf.USR_SID");
            sql.addSql("     left join CMN_USRM curm");
            sql.addSql("     on sisYrk.RSY_AUID = curm.USR_SID");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            UDate frDatePlus = UDate.getInstance(frDate.getTimeMillis());
            frDatePlus.setMilliSecond(1);
            sql.addDateValue(frDatePlus);
            sql.addDateValue(toDate);

            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);

            boolean whereFlg = false;
            if (grpSidList != null && grpSidList.size() > 0) {
                sql.addSql(" where");
                whereFlg = true;
                int i = 0;
                sql.addSql(" (");
                for (Integer grpSid : grpSidList) {
                    if (i > 0) {
                        sql.addSql(" or");
                    }
                    sql.addSql("   grp.RSG_SID = ?");
                    sql.addIntValue(grpSid);
                    i++;
                }
                sql.addSql(" )");
            }

            if (sisetuSid > -1) {
                if (whereFlg) {
                    sql.addSql(" and");
                } else {
                    sql.addSql(" where");
                }
                sql.addSql("   sisYrk.RSD_SID = ?");
                sql.addIntValue(sisetuSid);
            }

            sql.addSql(" order by");
            sql.addSql("   grp.RSG_SORT asc,");
            sql.addSql("   sisData.RSD_SORT asc,");
            sql.addSql("   sisYrk.RSY_FR_DATE asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getDataYrkAnalysisAll(rs));
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
     * <br>[機  能] 施設ごとに指定された期間の予約情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param grpSidList 条件 施設グループSIDリスト
     * @param sMdl Rsv010SearchModel
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret ArrayList in Rsv010WeekModelBeforConv
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvWeekModelBeforConv> getYrkList(List<Integer> grpSidList,
            Rsv010SearchModel sMdl, UDate frDate, UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvWeekModelBeforConv> ret =
            new ArrayList<RsvWeekModelBeforConv>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   grp.RSG_SID,");
            sql.addSql("   grp.RSK_SID,");
            sql.addSql("   sisData.RSD_SID,");
            sql.addSql("   sisData.RSD_NAME,");

            //施設情報
            sql.addSql("   sisData.RSD_ID,");
            sql.addSql("   sisData.RSD_SNUM,");
            sql.addSql("   sisData.RSD_PROP_1,");
            sql.addSql("   sisData.RSD_PROP_2,");
            sql.addSql("   sisData.RSD_PROP_3,");
            sql.addSql("   sisData.RSD_PROP_4,");
            sql.addSql("   sisData.RSD_PROP_5,");
            sql.addSql("   sisData.RSD_PROP_6,");
            sql.addSql("   sisData.RSD_PROP_7,");
            sql.addSql("   sisData.RSD_IMG_CMT1,");
            sql.addSql("   sisData.RSD_IMG_CMT2,");
            sql.addSql("   sisData.RSD_IMG_CMT3,");
            sql.addSql("   sisData.RSD_IMG_CMT4,");
            sql.addSql("   sisData.RSD_IMG_CMT5,");
            sql.addSql("   sisData.RSD_IMG_CMT6,");
            sql.addSql("   sisData.RSD_IMG_CMT7,");
            sql.addSql("   sisData.RSD_IMG_CMT8,");
            sql.addSql("   sisData.RSD_IMG_CMT9,");
            sql.addSql("   sisData.RSD_IMG_CMT10,");
            sql.addSql("   sisData.RSD_BIKO,");
            sql.addSql("   sisData.RSD_PLA_CMT,");

            //施設表示区分
            sql.addSql("   sisData.RSD_ID_DF,");
            sql.addSql("   sisData.RSD_SNUM_DF,");
            sql.addSql("   sisData.RSD_PROP_1_DF,");
            sql.addSql("   sisData.RSD_PROP_2_DF,");
            sql.addSql("   sisData.RSD_PROP_3_DF,");
            sql.addSql("   sisData.RSD_PROP_4_DF,");
            sql.addSql("   sisData.RSD_PROP_5_DF,");
            sql.addSql("   sisData.RSD_PROP_6_DF,");
            sql.addSql("   sisData.RSD_PROP_7_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT1_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT2_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT3_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT4_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT5_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT6_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT7_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT8_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT9_DF,");
            sql.addSql("   sisData.RSD_IMG_CMT10_DF,");
            sql.addSql("   sisData.RSD_BIKO_DF,");
            sql.addSql("   sisData.RSD_PLA_CMT_DF,");
            sql.addSql("   sisData.RSD_IMG_DF,");
            sql.addSql("   sisData.RSD_APPR_KBN_DF,");

            sql.addSql("   sisYrk.RSY_SID,");
            sql.addSql("   sisYrk.RSY_MOK,");
            sql.addSql("   sisYrk.RSY_BIKO,");
            sql.addSql("   sisYrk.RSY_FR_DATE,");
            sql.addSql("   sisYrk.RSY_TO_DATE,");
            sql.addSql("   sisYrk.RSY_PUBLIC,");
            sql.addSql("   sisYrk.RSY_APPR_STATUS,");
            sql.addSql("   sisYrk.RSY_APPR_KBN,");
            sql.addSql("   inf.USI_SEI,");
            sql.addSql("   inf.USI_MEI,");
            sql.addSql("   curm.USR_JKBN,");
            sql.addSql("   curm.USR_UKO_FLG");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     (RSV_SIS_DATA sisData left join RSV_SIS_GRP grp");
            sql.addSql("      on");
            sql.addSql("      sisData.RSG_SID = grp.RSG_SID");
            sql.addSql("     ) left join RSV_SIS_YRK sisYrk");
            sql.addSql("     on sisData.RSD_SID = sisYrk.RSD_SID");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_FR_DATE between ?");
            sql.addSql("           and ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_TO_DATE between ?");
            sql.addSql("           and ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("     ) left join CMN_USRM_INF inf");
            sql.addSql("     on sisYrk.RSY_AUID = inf.USR_SID");
            sql.addSql("     left join CMN_USRM curm");
            sql.addSql("     on sisYrk.RSY_AUID = curm.USR_SID");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);

            UDate frDatePlus = UDate.getInstance(frDate.getTimeMillis());
            frDatePlus.setMilliSecond(1);
            sql.addDateValue(frDatePlus);
            sql.addDateValue(toDate);

            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);

            boolean whereFlg = false;
            if (grpSidList != null && grpSidList.size() > 0) {
                sql.addSql(" where");
                whereFlg = true;
                int i = 0;
                sql.addSql(" (");
                for (Integer grpSid : grpSidList) {
                    if (i > 0) {
                        sql.addSql(" or");
                    }
                    sql.addSql("   grp.RSG_SID = ?");
                    sql.addIntValue(grpSid);
                    i++;
                }
                sql.addSql(" )");
            }
            if (!whereFlg) {
                sql.addSql(" where");
                sql.addSql("   1 = 1");
            }

            //キーワード検索
            keywordSiborikomi(sMdl, sql);

            //空き状況
            if (sMdl.getSisetuFree() == 1) {
                freeSiborikomi(sMdl, sql);
            }

            //喫煙
            if ((sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_HEYA
                    || sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_CAR)
                    && sMdl.getSisetuSmoky() != 0) {
                sql.addSql(" and");
                sql.addSql("   sisData.RSD_PROP_2 = ?");
                if (sMdl.getSisetuSmoky() == 1) {
                    sql.addStrValue("0");
                } else {
                    sql.addStrValue("1");
                }
            }

            //社外持ち出し
            if ((sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_BUPPIN
                    || sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_BOOK)
                    && sMdl.getSisetuTakeout() != 0) {
                sql.addSql(" and");
                sql.addSql("   sisData.RSD_PROP_3 = ?");
                if (sMdl.getSisetuTakeout() == 1) {
                    sql.addStrValue("0");
                } else {
                    sql.addStrValue("1");
                }
            }

            //施設区分
            if (sMdl.getSisetuKbn() != 0) {
                sql.addSql(" and");
                sql.addSql("   grp.RSK_SID = ?");
                sql.addIntValue(sMdl.getSisetuKbn());
            }

            sql.addSql(" order by");
            sql.addSql("   grp.RSG_SORT asc,");
            sql.addSql("   sisData.RSD_SORT asc,");
            sql.addSql("   sisYrk.RSY_FR_DATE asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {

                //座席数
                if ((sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_HEYA
                        || sMdl.getSisetuKbn() == GSConstReserve.RSK_KBN_CAR)
                        && !StringUtil.isNullZeroString(sMdl.getSisetuChere())) {
                    String chere = rs.getString("RSD_PROP_1");
                    if (Long.parseLong(chere) < Long.parseLong(sMdl.getSisetuChere())) {
                        continue;
                    }
                }
                ret.add(__getDataYrkAnalysisAll(rs));
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
     * <br>[機  能]施設情報取得データ解析
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return RsvWeekModelBeforConv
     * @throws SQLException SQLException
     */
    private RsvWeekModelBeforConv __getDataYrkAnalysisAll(ResultSet rs)
            throws SQLException {
        RsvWeekModelBeforConv mdl = new RsvWeekModelBeforConv();
        mdl.setRsgSid(rs.getInt("RSG_SID"));
        mdl.setRsdInfoSisetuKbnSid(rs.getInt("RSK_SID"));
        mdl.setRsdSid(rs.getInt("RSD_SID"));
        mdl.setRsdName(rs.getString("RSD_NAME"));

        //施設情報
        mdl.setRsdInfoSisetuId(rs.getString("RSD_ID"));
        mdl.setRsdInfoSisanKanri(rs.getString("RSD_SNUM"));
        mdl.setRsdInfoProp1Value(rs.getString("RSD_PROP_1"));
        mdl.setRsdInfoProp2Value(rs.getString("RSD_PROP_2"));
        mdl.setRsdInfoProp3Value(rs.getString("RSD_PROP_3"));
        mdl.setRsdInfoProp4Value(rs.getString("RSD_PROP_4"));
        mdl.setRsdInfoProp5Value(rs.getString("RSD_PROP_5"));
        mdl.setRsdInfoProp6Value(rs.getString("RSD_PROP_6"));
        mdl.setRsdInfoProp7Value(rs.getString("RSD_PROP_7"));
        mdl.setRsdInfoPlaceImgCom1(rs.getString("RSD_IMG_CMT1"));
        mdl.setRsdInfoPlaceImgCom2(rs.getString("RSD_IMG_CMT2"));
        mdl.setRsdInfoPlaceImgCom3(rs.getString("RSD_IMG_CMT3"));
        mdl.setRsdInfoPlaceImgCom4(rs.getString("RSD_IMG_CMT4"));
        mdl.setRsdInfoPlaceImgCom5(rs.getString("RSD_IMG_CMT5"));
        mdl.setRsdInfoPlaceImgCom6(rs.getString("RSD_IMG_CMT6"));
        mdl.setRsdInfoPlaceImgCom7(rs.getString("RSD_IMG_CMT7"));
        mdl.setRsdInfoPlaceImgCom8(rs.getString("RSD_IMG_CMT8"));
        mdl.setRsdInfoPlaceImgCom9(rs.getString("RSD_IMG_CMT9"));
        mdl.setRsdInfoPlaceImgCom10(rs.getString("RSD_IMG_CMT10"));
        mdl.setRsdInfoBiko(rs.getString("RSD_BIKO"));
        mdl.setRsdInfoPlaCom(rs.getString("RSD_PLA_CMT"));

        //施設表示区分
        mdl.setRsdInfoSisetuIdDspKbn(rs.getInt("RSD_ID_DF"));
        mdl.setRsdInfoSisanKanriDspKbn(rs.getInt("RSD_SNUM_DF"));
        mdl.setRsdInfoProp1ValueDspKbn(rs.getInt("RSD_PROP_1_DF"));
        mdl.setRsdInfoProp2ValueDspKbn(rs.getInt("RSD_PROP_2_DF"));
        mdl.setRsdInfoProp3ValueDspKbn(rs.getInt("RSD_PROP_3_DF"));
        mdl.setRsdInfoProp4ValueDspKbn(rs.getInt("RSD_PROP_4_DF"));
        mdl.setRsdInfoProp5ValueDspKbn(rs.getInt("RSD_PROP_5_DF"));
        mdl.setRsdInfoProp6ValueDspKbn(rs.getInt("RSD_PROP_6_DF"));
        mdl.setRsdInfoProp7ValueDspKbn(rs.getInt("RSD_PROP_7_DF"));
        mdl.setRsdInfoPlaceImgCom1DspKbn(rs.getInt("RSD_IMG_CMT1_DF"));
        mdl.setRsdInfoPlaceImgCom2DspKbn(rs.getInt("RSD_IMG_CMT2_DF"));
        mdl.setRsdInfoPlaceImgCom3DspKbn(rs.getInt("RSD_IMG_CMT3_DF"));
        mdl.setRsdInfoPlaceImgCom4DspKbn(rs.getInt("RSD_IMG_CMT4_DF"));
        mdl.setRsdInfoPlaceImgCom5DspKbn(rs.getInt("RSD_IMG_CMT5_DF"));
        mdl.setRsdInfoPlaceImgCom6DspKbn(rs.getInt("RSD_IMG_CMT6_DF"));
        mdl.setRsdInfoPlaceImgCom7DspKbn(rs.getInt("RSD_IMG_CMT7_DF"));
        mdl.setRsdInfoPlaceImgCom8DspKbn(rs.getInt("RSD_IMG_CMT8_DF"));
        mdl.setRsdInfoPlaceImgCom9DspKbn(rs.getInt("RSD_IMG_CMT9_DF"));
        mdl.setRsdInfoPlaceImgCom10DspKbn(rs.getInt("RSD_IMG_CMT10_DF"));
        mdl.setRsdInfoBikoDspKbn(rs.getInt("RSD_BIKO_DF"));
        mdl.setRsdInfoPlaComDspKbn(rs.getInt("RSD_PLA_CMT_DF"));
        mdl.setRsdInfoSisetuImgDspKbn(rs.getInt("RSD_IMG_DF"));
        mdl.setRsdApprDspKbn(rs.getInt("RSD_APPR_KBN_DF"));
        mdl.setRsySid(rs.getInt("RSY_SID"));
        mdl.setRsyMok(rs.getString("RSY_MOK"));
        mdl.setRsyNaiyo(rs.getString("RSY_BIKO"));
        mdl.setRsyFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE")));
        mdl.setRsyToDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_TO_DATE")));
        mdl.setRsyPublic(rs.getInt("RSY_PUBLIC"));
        mdl.setRsyApprStatus(rs.getInt("RSY_APPR_STATUS"));
        mdl.setRsyApprKbn(rs.getInt("RSY_APPR_KBN"));
        mdl.setUsiSei(rs.getString("USI_SEI"));
        mdl.setUsiMei(rs.getString("USI_MEI"));
        mdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        mdl.setUsrJkbn(rs.getInt("USR_JKBN"));
        return mdl;
    }


    /**
     * <br>[機  能] 指定した施設ごとに指定された期間の予約情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sisetuSids 条件 施設SID
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret ArrayList in Rsv010WeekModelBeforConv
     * @throws SQLException SQL実行例外
     */
    public ArrayList<RsvWeekModelBeforConv> getYrkList(ArrayList<Integer> sisetuSids,
                                                        UDate frDate,
                                                        UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<RsvWeekModelBeforConv> ret =
            new ArrayList<RsvWeekModelBeforConv>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   grp.RSG_SID,");
            sql.addSql("   sisData.RSD_SID,");
            sql.addSql("   sisData.RSD_NAME,");
            sql.addSql("   sisYrk.RSY_SID,");
            sql.addSql("   sisYrk.RSY_MOK,");
            sql.addSql("   sisYrk.RSY_BIKO,");
            sql.addSql("   sisYrk.RSY_FR_DATE,");
            sql.addSql("   sisYrk.RSY_TO_DATE,");
            sql.addSql("   sisYrk.RSY_APPR_STATUS,");
            sql.addSql("   sisYrk.RSY_APPR_KBN,");
            sql.addSql("   sisYrk.RSY_PUBLIC,");
            sql.addSql("   inf.USI_SEI,");
            sql.addSql("   inf.USI_MEI,");
            sql.addSql("   infm.USR_JKBN,");
            sql.addSql("   infm.USR_UKO_FLG");
            sql.addSql(" from");
            sql.addSql("   (");
            sql.addSql("     (RSV_SIS_DATA sisData left join RSV_SIS_GRP grp");
            sql.addSql("      on");
            sql.addSql("      sisData.RSG_SID = grp.RSG_SID");
            sql.addSql("     ) left join RSV_SIS_YRK sisYrk");
            sql.addSql("     on sisData.RSD_SID = sisYrk.RSD_SID");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_FR_DATE >= ?");
            sql.addSql("           and");
            sql.addSql("           sisYrk.RSY_FR_DATE <= ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           sisYrk.RSY_TO_DATE > ?");
            sql.addSql("           and");
            sql.addSql("           sisYrk.RSY_TO_DATE <= ?");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("         or");
            sql.addSql("         (");
            sql.addSql("           ? >= sisYrk.RSY_FR_DATE");
            sql.addSql("           and");
            sql.addSql("           ? < sisYrk.RSY_TO_DATE");
            sql.addSql("         )");
            sql.addSql("       )");
            sql.addSql("     ) left join CMN_USRM_INF inf");
            sql.addSql("     on sisYrk.RSY_AUID = inf.USR_SID");
            sql.addSql("       left join CMN_USRM infm");
            sql.addSql("     on sisYrk.RSY_AUID = infm.USR_SID");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);

            if (sisetuSids.size() > 0) {
                sql.addSql(" where");
                sql.addSql("   sisData.RSD_SID in(");
                for (int i = 0; i < sisetuSids.size(); i++) {
                    if (i > 0) {
                        sql.addSql(",");
                    }
                    sql.addSql("   ?");
                    sql.addIntValue(sisetuSids.get(i));
                }
                sql.addSql("   )");
            }

            sql.addSql(" order by");
            sql.addSql("   grp.RSG_SORT asc,");
            sql.addSql("   sisData.RSD_SORT asc,");
            sql.addSql("   sisYrk.RSY_FR_DATE asc");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RsvWeekModelBeforConv mdl = new RsvWeekModelBeforConv();
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                mdl.setRsdSid(rs.getInt("RSD_SID"));
                mdl.setRsdName(rs.getString("RSD_NAME"));
                mdl.setRsySid(rs.getInt("RSY_SID"));
                mdl.setRsyMok(rs.getString("RSY_MOK"));
                mdl.setRsyNaiyo(rs.getString("RSY_BIKO"));
                mdl.setRsyFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE")));
                mdl.setRsyToDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_TO_DATE")));
                mdl.setRsyApprStatus(rs.getInt("RSY_APPR_STATUS"));
                mdl.setRsyApprKbn(rs.getInt("RSY_APPR_KBN"));
                mdl.setRsyPublic(rs.getInt("RSY_PUBLIC"));
                mdl.setUsiSei(rs.getString("USI_SEI"));
                mdl.setUsiMei(rs.getString("USI_MEI"));
                mdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                mdl.setUsrJkbn(rs.getInt("USR_JKBN"));
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
     * <br>[機  能] 施設利用状況一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件格納モデル
     * @param adminKbn システム管理者フラグ
     * @param reqMdl リクエストモデル
     * @return 検索結果 ArrayList
     * @throws SQLException 例外
     */
    public ArrayList<Rsv100SisYrkModel> getYrkReferenceList(
            Rsv100searchModel model, boolean adminKbn, RequestModel reqMdl) throws SQLException {
        int offset = 0;
        if (model.getRsvPageTop() > 1) {
            offset = (model.getRsvPageTop() - 1) * model.getRsvMaxPage();
        }
        return getYrkReferenceList(model, adminKbn, offset, model.getRsvMaxPage(), reqMdl);
    }
    /**
     * <br>[機  能] 施設利用状況一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件格納モデル
     * @param adminKbn システム管理者フラグ
     * @param offset 取得開始インデックス
     * @param results 取得件数
     * @param reqMdl リクエストモデル
     * @return 検索結果 ArrayList
     * @throws SQLException 例外
     */
    public ArrayList<Rsv100SisYrkModel> getYrkReferenceList(
            Rsv100searchModel model, boolean adminKbn,
            int offset, int results, RequestModel reqMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Rsv100SisYrkModel> ret = new ArrayList<Rsv100SisYrkModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("  CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("  CMN_USRM.USR_JKBN as USR_JKBN,");
            sql.addSql("  CMN_USRM.USR_UKO_FLG as USR_UKO_FLG,");
            sql.addSql("  RSV_SIS_YRK.RSY_FR_DATE as RSY_FR_DATE,");
            sql.addSql("  RSV_SIS_YRK.RSY_TO_DATE as RSY_TO_DATE,");
            sql.addSql("  RSV_SIS_YRK.RSY_MOK as RSY_MOK,");
            sql.addSql("  RSV_SIS_YRK.RSY_BIKO as RSY_BIKO,");
            sql.addSql("  RSV_SIS_YRK.RSY_SID as RSY_SID,");
            sql.addSql("  RSV_SIS_YRK.RSY_APPR_STATUS as RSY_APPR_STATUS,");
            sql.addSql("  RSV_SIS_YRK.RSY_APPR_KBN as RSY_APPR_KBN,");
            sql.addSql("  RSV_SIS_YRK.SCD_RSSID as SCD_RSSID,");
            sql.addSql("  RSV_SIS_YRK.RSY_EDATE as RSY_EDATE,");
            sql.addSql("  RSV_SIS_YRK.RSY_ADATE as RSY_ADATE,");
            sql.addSql("  RSV_SIS_YRK.RSY_EDIT as RSY_EDIT,");
            sql.addSql("  RSV_SIS_YRK.RSY_PUBLIC as  RSY_PUBLIC,");
            sql.addSql("  RSV_SIS_YRK.RSY_AUID as RSY_AUID,");
            sql.addSql("  RSV_SIS_DATA.RSG_SID as RSG_SID,");
            sql.addSql("  RSV_SIS_DATA.RSD_SID as RSD_SID,");
            sql.addSql("  RSV_SIS_DATA.RSD_NAME as RSD_NAME,");
            sql.addSql("  RSV_SIS_GRP.RSK_SID as RSK_SID,");
            sql.addSql("  RSV_SIS_KYRK.RKY_BUSYO as RKY_BUSYO,");
            sql.addSql("  RSV_SIS_KYRK.RKY_NAME as RKY_NAME,");
            sql.addSql("  RSV_SIS_KYRK.RKY_NUM as RKY_NUM,");
            sql.addSql("  RSV_SIS_KYRK.RKY_USE_KBN as RKY_USE_KBN,");
            sql.addSql("  RSV_SIS_KYRK.RKY_CONTACT as RKY_CONTACT,");
            sql.addSql("  RSV_SIS_KYRK.RKY_GUIDE as RKY_GUIDE,");
            sql.addSql("  RSV_SIS_KYRK.RKY_PARK_NUM as RKY_PARK_NUM,");
            sql.addSql("  RSV_SIS_KYRK.RKY_DEST as RKY_DEST");
            __setYrkReferenceListSql(sql, model, adminKbn);

            sql.addSql(" order by");
            StringBuilder sortString = new StringBuilder("");

            switch (model.getRsvSelectedKey1()) {
                case 1:
                    sortString.append("   CMN_USRM_INF.USI_SEI_KN");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    sortString.append("   CMN_USRM_INF.USI_MEI_KN");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                case 2:
                    sortString.append("   RSV_SIS_DATA.RSD_NAME");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                case 3:
                    sortString.append("   RSV_SIS_YRK.RSY_FR_DATE");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                case 4:
                    sortString.append("   RSV_SIS_YRK.RSY_TO_DATE");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                case 5:
                    sortString.append("   RSV_SIS_YRK.RSY_MOK");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                default:
                    break;
            }

            switch (model.getRsvSelectedKey2()) {
                case 1:
                    sortString.append("   CMN_USRM_INF.USI_SEI_KN");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    sortString.append("   CMN_USRM_INF.USI_MEI_KN");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                case 2:
                    sortString.append("   RSV_SIS_DATA.RSD_NAME");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                case 3:
                    sortString.append("   RSV_SIS_YRK.RSY_FR_DATE");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                case 4:
                    sortString.append("   RSV_SIS_YRK.RSY_TO_DATE");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                case 5:
                    sortString.append("   RSV_SIS_YRK.RSY_MOK");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                default:
                    break;
            }

            sql.addSql(sortString.toString());

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (offset > 0) {
                rs.absolute(offset);
            }
            for (int i = 0; rs.next() && i < results; i++) {
                Rsv100SisYrkModel mdl = new Rsv100SisYrkModel();
                String sei = rs.getString("USI_SEI");
                String mei = rs.getString("USI_MEI");
                mdl.setRsySeiMei(sei + " " + mei);
                mdl.setUsrJKbn(rs.getInt("USR_JKBN"));
                mdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                mdl.setRsySisetu(rs.getString("RSD_NAME"));
                mdl.setRsdSid(rs.getInt("RSD_SID"));
                mdl.setRsgSid(rs.getInt("RSG_SID"));
                UDate frm = UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE"));
                mdl.setRsyFrDate(frm);
                mdl.setRsyFrom(frm.getStrYear()  + "/"
                             + frm.getStrMonth() + "/"
                             + frm.getStrDay()   + "  "
                             + frm.getStrHour()  + ":"
                             + frm.getStrMinute());
                UDate to = UDate.getInstanceTimestamp(rs.getTimestamp("RSY_TO_DATE"));
                mdl.setRsyToDate(to);
                mdl.setRsyTo(to.getStrYear()  + "/"
                           + to.getStrMonth() + "/"
                           + to.getStrDay()   + "  "
                           + to.getStrHour()  + ":"
                           + to.getStrMinute());

                mdl.setRsySisetuSid(rs.getInt("RSY_SID"));
                mdl.setRsyEdit(rs.getInt("RSY_EDIT"));
                int rsyPublic = rs.getInt("RSY_PUBLIC");
                mdl.setRsyPublic(rsyPublic);
                //閲覧可能な施設予約情報のみ、利用目的をセット。それ以外は「予定あり」
                RsvCommonBiz rsvBiz = new RsvCommonBiz();
                int publicFlg = rsvBiz.isWithinPubilicRange(
                        con, mdl.getRsySisetuSid(), reqMdl.getSmodel().getUsrsid());

                if (publicFlg == GSConstReserve.POWRANGE_DEL) {
                    continue;
                }

                Rsv100Biz rsv100Biz = new Rsv100Biz(con, reqMdl);
                rsv100Biz.setPublicArea(mdl, publicFlg);
                if (mdl.getPublic() != GSConstReserve.PUBLIC_KBN_PLANS
                || rsyPublic == GSConstReserve.PUBLIC_KBN_TITLE) {
                    mdl.setRsyContent(rs.getString("RSY_MOK"));
                }
                mdl.setViewFlg(publicFlg == GSConstReserve.POWRANGE_IN);

                mdl.setRsyBiko(rs.getString("RSY_BIKO"));
                mdl.setRskSid(rs.getInt("RSK_SID"));
                mdl.setRkyBusyo(rs.getString("RKY_BUSYO"));
                mdl.setRkyName(rs.getString("RKY_NAME"));
                mdl.setRkyNum(rs.getString("RKY_NUM"));
                mdl.setRkyUseKbn(rs.getInt("RKY_USE_KBN"));
                mdl.setRkyContact(rs.getString("RKY_CONTACT"));
                mdl.setRkyGuide(rs.getString("RKY_GUIDE"));
                mdl.setRkyParkNum(rs.getString("RKY_PARK_NUM"));
                mdl.setRkyDest(rs.getString("RKY_DEST"));

                mdl.setScdRsSid(rs.getInt("SCD_RSSID"));
                mdl.setRsyApprStatus(rs.getInt("RSY_APPR_STATUS"));
                mdl.setRsyApprKbn(rs.getInt("RSY_APPR_KBN"));
                mdl.setRsyAuid(rs.getInt("RSY_AUID"));
                mdl.setRsyAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_ADATE")));
                mdl.setRsyEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_EDATE")));

                //キーワード検索時、閲覧できない施設予約情報は表示しない
                if (model != null && model.getRsvKeyWord() != null
                        && !(model.getRsvKeyWord().isEmpty())
                        && mdl.getPublic() == GSConstReserve.PUBLIC_KBN_PLANS) {
                    continue;
                }
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
     * <br>[機  能] 施設利用状況一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件格納モデル
     * @param adminKbn システム管理者フラグ
     * @param reqMdl リクエストモデル
     * @return 検索結果 ArrayList
     * @throws SQLException 例外
     */
    public ArrayList<Rsv100SisYrkModel> getAllYrkReferenceList(
            Rsv100searchModel model, boolean adminKbn,
            RequestModel reqMdl) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Rsv100SisYrkModel> ret = new ArrayList<Rsv100SisYrkModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("  CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("  RSV_SIS_YRK.RSY_FR_DATE as RSY_FR_DATE,");
            sql.addSql("  RSV_SIS_YRK.RSY_TO_DATE as RSY_TO_DATE,");
            sql.addSql("  RSV_SIS_YRK.RSY_MOK as RSY_MOK,");
            sql.addSql("  RSV_SIS_YRK.RSY_SID as RSY_SID,");
            sql.addSql("  RSV_SIS_YRK.RSY_BIKO as RSY_BIKO,");
            sql.addSql("  RSV_SIS_YRK.RSY_EDIT as RSY_EDIT,");
            sql.addSql("  RSV_SIS_YRK.RSY_PUBLIC as RSY_PUBLIC,");
            sql.addSql("  RSV_SIS_YRK.RSY_APPR_STATUS as RSY_APPR_STATUS,");
            sql.addSql("  RSV_SIS_YRK.RSY_APPR_KBN as RSY_APPR_KBN,");
            sql.addSql("  RSV_SIS_DATA.RSD_NAME as RSD_NAME,");
            sql.addSql("  RSV_SIS_DATA.RSD_ID as RSD_ID,");
            sql.addSql("  CMN_USRM.USR_LGID as USR_LGID,");

            sql.addSql("  RSV_SIS_KYRK.RKY_BUSYO as RKY_BUSYO,");
            sql.addSql("  RSV_SIS_KYRK.RKY_NAME as RKY_NAME,");
            sql.addSql("  RSV_SIS_KYRK.RKY_NUM as RKY_NUM,");
            sql.addSql("  RSV_SIS_KYRK.RKY_USE_KBN as RKY_USE_KBN,");
            sql.addSql("  RSV_SIS_KYRK.RKY_CONTACT as RKY_CONTACT,");
            sql.addSql("  RSV_SIS_KYRK.RKY_GUIDE as RKY_GUIDE,");
            sql.addSql("  RSV_SIS_KYRK.RKY_PARK_NUM as RKY_PARK_NUM,");
            sql.addSql("  RSV_SIS_KYRK.RKY_DEST as RKY_DEST");

            __setYrkReferenceListSql(sql, model, adminKbn);

            sql.addSql(" order by");
            StringBuilder sortString = new StringBuilder("");

            switch (model.getRsvSelectedKey1()) {
                case 1:
                    sortString.append("   CMN_USRM_INF.USI_SEI_KN");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    sortString.append("   CMN_USRM_INF.USI_MEI_KN");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                case 2:
                    sortString.append("   RSV_SIS_DATA.RSD_NAME");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                case 3:
                    sortString.append("   RSV_SIS_YRK.RSY_FR_DATE");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                case 4:
                    sortString.append("   RSV_SIS_YRK.RSY_TO_DATE");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                case 5:
                    sortString.append("   RSV_SIS_YRK.RSY_MOK");
                    if (model.getRsvSelectedKey1Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey1Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    break;
                default:
                    break;
            }

            switch (model.getRsvSelectedKey2()) {
                case 1:
                    sortString.append("   CMN_USRM_INF.USI_SEI_KN");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc,");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc,");
                    }
                    sortString.append("   CMN_USRM_INF.USI_MEI_KN");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                case 2:
                    sortString.append("   RSV_SIS_DATA.RSD_NAME");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                case 3:
                    sortString.append("   RSV_SIS_YRK.RSY_FR_DATE");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                case 4:
                    sortString.append("   RSV_SIS_YRK.RSY_TO_DATE");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                case 5:
                    sortString.append("   RSV_SIS_YRK.RSY_MOK");
                    if (model.getRsvSelectedKey2Sort() == 0) {
                        sortString.append(" asc");
                    } else if (model.getRsvSelectedKey2Sort() == 1) {
                        sortString.append(" desc");
                    }
                    break;
                default:
                    break;
            }

            sql.addSql(sortString.toString());

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Rsv100SisYrkModel mdl = new Rsv100SisYrkModel();
                String sei = rs.getString("USI_SEI");
                String mei = rs.getString("USI_MEI");
                mdl.setRsySeiMei(sei + " " + mei);
                mdl.setRsySisetu(rs.getString("RSD_NAME"));
                UDate frm = UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE"));
                mdl.setRsyFrom(frm.getStrYear()  + "/"
                             + frm.getStrMonth()  + "/"
                             + frm.getStrDay());
                mdl.setRsyFromTime(frm.getStrHour()  + ":"
                                  + frm.getStrMinute());
                UDate to = UDate.getInstanceTimestamp(rs.getTimestamp("RSY_TO_DATE"));
                mdl.setRsyTo(to.getStrYear()  + "/"
                           + to.getStrMonth()  + "/"
                           + to.getStrDay());
                mdl.setRsyToTime(to.getStrHour()  + ":"
                                 + to.getStrMinute());

                mdl.setRsySisetuSid(rs.getInt("RSY_SID"));
                mdl.setSisetuId(rs.getString("RSD_ID"));
                mdl.setUserId(rs.getString("USR_LGID"));
                mdl.setRsyBiko(rs.getString("RSY_BIKO"));
                mdl.setRsyEdit(rs.getInt("RSY_EDIT"));
                mdl.setRsyPublic(rs.getInt("RSY_PUBLIC"));
              //閲覧可能な施設予約情報のみ、利用目的をセット
                RsvCommonBiz rsvBiz = new RsvCommonBiz();
                int publicFlg = rsvBiz.isWithinPubilicRange(
                        con, mdl.getRsySisetuSid(), reqMdl.getSmodel().getUsrsid());

                if (publicFlg == GSConstReserve.POWRANGE_DEL) {
                    continue;
                }

                Rsv100Biz rsv100Biz = new Rsv100Biz(con, reqMdl);
                rsv100Biz.setPublicArea(mdl, publicFlg);
                if (mdl.getPublic() != GSConstReserve.PUBLIC_KBN_PLANS
                || mdl.getRsyPublic() == GSConstReserve.PUBLIC_KBN_TITLE) {
                    mdl.setRsyContent(rs.getString("RSY_MOK"));
                }
                mdl.setViewFlg(publicFlg == GSConstReserve.POWRANGE_IN);

                mdl.setRsyApprStatus(rs.getInt("RSY_APPR_STATUS"));
                mdl.setRsyApprKbn(rs.getInt("RSY_APPR_KBN"));

                //区分別
                mdl.setRkyBusyo(rs.getString("RKY_BUSYO"));
                mdl.setRkyName(rs.getString("RKY_NAME"));
                mdl.setRkyNum(rs.getString("RKY_NUM"));
                mdl.setRkyUseKbn(rs.getInt("RKY_USE_KBN"));
                mdl.setRkyContact(rs.getString("RKY_CONTACT"));
                mdl.setRkyGuide(rs.getString("RKY_GUIDE"));
                mdl.setRkyParkNum(rs.getString("RKY_PARK_NUM"));
                mdl.setRkyDest(rs.getString("RKY_DEST"));

              //キーワード検索時、閲覧できない施設予約情報は表示しない
                if (model != null && model.getRsvKeyWord() != null
                        && !(model.getRsvKeyWord().isEmpty())
                        && mdl.getPublic() == GSConstReserve.PUBLIC_KBN_PLANS) {
                    continue;
                }
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
     * <br>[機  能] 施設利用状況一覧の件数を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param model 検索条件格納モデル
     * @param adminKbn システム管理者フラグ
     * @return 検索結果数
     * @throws SQLException 例外
     */
    public int getYrkReferenceCount(
            Rsv100searchModel model, boolean adminKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  count(RSV_SIS_YRK.RSY_SID) as RSVCOUNT");
            __setYrkReferenceListSql(sql, model, adminKbn);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("RSVCOUNT");
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
     * <br>[機  能] 施設利用状況照会の検索SQLを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQLバッファ
     * @param model Rsv100searchModel
     * @param adminKbn システム管理者フラグ
     */
    private void __setYrkReferenceListSql(SqlBuffer sql,
            Rsv100searchModel model, boolean adminKbn) {
        //SQL文
        sql.addSql(" from");
        sql.addSql("   (RSV_SIS_YRK left join RSV_SIS_KYRK ");
        sql.addSql("      on RSV_SIS_YRK.RSY_SID = RSV_SIS_KYRK.RSY_SID),");
        sql.addSql("   RSV_SIS_DATA,");
        sql.addSql("   RSV_SIS_GRP,");
        sql.addSql("   CMN_USRM,");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql(" where");
        sql.addSql("   RSV_SIS_YRK.RSD_SID = RSV_SIS_DATA.RSD_SID");
        sql.addSql(" and");
        sql.addSql("   RSV_SIS_DATA.RSG_SID = RSV_SIS_GRP.RSG_SID");
        sql.addSql(" and");
        sql.addSql("   RSV_SIS_YRK.RSY_AUID = CMN_USRM.USR_SID");
        sql.addSql(" and");
        sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

        List<String> keyList = model.getRsvKeyWord();
        int targetMok = model.getRsvTargetMok();
        int targetNiyo = model.getRsvTargetNiyo();
        int condition = model.getRsvSearchCondition();

        //キーワード・検索対象
        if ((targetMok == 1 || targetNiyo == 1)
            && (keyList != null && !keyList.isEmpty())) {

            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");

            if (targetMok == 1) {
                for (int i = 0; i < keyList.size(); i++) {

                    String key = keyList.get(i);
                    sql.addSql("       (");
                    sql.addSql("         RSV_SIS_YRK.RSY_MOK like '%"
                            + JDBCUtil.escapeForLikeSearch(key)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");

                    sql.addSql("       )");

                    if (keyList.size() > 1) {
                        if (i != keyList.size() - 1 && condition == 0) {
                            sql.addSql("       and");
                        } else if (i != keyList.size() - 1 && condition == 1) {
                            sql.addSql("       or");
                        }
                    }
                }
            }

            if (targetNiyo == 1) {
                if (targetMok == 1) {
                    sql.addSql("     )");
                    sql.addSql("     or");
                    sql.addSql("     (");
                }

                for (int i = 0; i < keyList.size(); i++) {

                    String key = keyList.get(i);
                    sql.addSql("       (");
                    sql.addSql("         RSV_SIS_YRK.RSY_BIKO like '%"
                            + JDBCUtil.escapeForLikeSearch(key)
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");

                    sql.addSql("     )");

                    if (keyList.size() > 1) {
                        if (i != keyList.size() - 1 && condition == 0) {
                            sql.addSql("       and");
                        } else if (i != keyList.size() - 1 && condition == 1) {
                            sql.addSql("       or");
                        }
                    }
                }
            }

            sql.addSql("     )");
            sql.addSql("   )");
        }

        if (model.isRsvDateKbn()) {
            sql.addSql(" and ");
            sql.addSql("   (");
            //条件1：From～To内で予約開始～予約終了が完結するもの
            sql.addSql("     (");
            sql.addSql("        RSV_SIS_YRK.RSY_FR_DATE <= ? ");
            sql.addSql("       and");
            sql.addSql("        ? < RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addDateValue(model.getRsvFrom());
            sql.addDateValue(model.getRsvFrom());
            //条件2：予約開始はFrom以前だが、予約終了がFrom以降(またがっている)のもの
            sql.addSql("     (");
            sql.addSql("        RSV_SIS_YRK.RSY_FR_DATE <= ?");
            sql.addSql("       and");
            sql.addSql("        ? <= RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addDateValue(model.getRsvTo());
            sql.addDateValue(model.getRsvTo());
            //条件3：予約終了はTo以降だが、予約の開始がTo以前(またがっている)のもの
            sql.addSql("     (");
            sql.addSql("       (");
            sql.addSql("         RSV_SIS_YRK.RSY_FR_DATE between ? ");
            sql.addSql("                                 and ? ");
            sql.addSql("       )");
            sql.addSql("       and");
            sql.addSql("       (");
            sql.addSql("         RSV_SIS_YRK.RSY_TO_DATE between ? ");
            sql.addSql("                                 and ? ");
            sql.addDateValue(model.getRsvFrom());
            sql.addDateValue(model.getRsvTo());
            sql.addDateValue(model.getRsvFrom());
            sql.addDateValue(model.getRsvTo());
            sql.addSql("       )");
            sql.addSql("     )");
            sql.addSql("   )");
        }

        //グループが選択されていれば検索条件に含める
        if (model.getRsvGrp1() > GSConstReserve.COMBO_DEFAULT_VALUE) {
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_DATA.RSG_SID = ?");
            sql.addIntValue(model.getRsvGrp1());
        }
        if (model.getUsrSid() > 0 && !adminKbn) {
            sql.addSql(" and");
            //グループが選択されていない場合、アクセス権限のないものを省く
            sql.addSql("(");
            sql.addSql("  (");
            sql.addSql("   RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  )");
            sql.addSql(" or");
            sql.addSql("   ( ");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql(" or");
            sql.addSql("  (");
            sql.addSql("    RSV_SIS_GRP.RSG_ACS_LIMIT_KBN=?");
            sql.addSql("  and ");
            sql.addSql("    not exists");
            sql.addSql("    (");
            sql.addSql("      select");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID");
            sql.addSql("      from");
            sql.addSql("        RSV_ACCESS_CONF");
            sql.addSql("        left join");
            sql.addSql("          CMN_BELONGM");
            sql.addSql("        on");
            sql.addSql("          RSV_ACCESS_CONF.GRP_SID = CMN_BELONGM.GRP_SID");
            sql.addSql("      where");
            sql.addSql("        RSV_ACCESS_CONF.RSG_SID = RSV_SIS_GRP.RSG_SID");
            sql.addSql("      and");
            sql.addSql("        (");
            sql.addSql("          RSV_ACCESS_CONF.USR_SID = ?");
            sql.addSql("        or");
            sql.addSql("          CMN_BELONGM.USR_SID = ?");
            sql.addSql("        )");
            sql.addSql("      and");
            sql.addSql("        RSV_ACCESS_CONF.RAC_AUTH=?");
            sql.addSql("    )");
            sql.addSql("  )");
            sql.addSql(")");

            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_FREE);
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_LIMIT);
            sql.addIntValue(model.getUsrSid());
            sql.addIntValue(model.getUsrSid());
            sql.addIntValue(GSConstReserve.RSV_ACCESS_MODE_PERMIT);
            sql.addIntValue(model.getUsrSid());
            sql.addIntValue(model.getUsrSid());
            sql.addIntValue(GSConstReserve.RSV_ACCESS_KBN_READ);
        }


        //施設が選択されていれば検索条件に含める
        if (model.getRsvGrp2() > GSConstReserve.COMBO_DEFAULT_VALUE) {
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_DATA.RSD_SID = ?");
            sql.addIntValue(model.getRsvGrp2());
        }

        //承認状況
        if (model.getRsvApprStatus() != GSConstReserve.SRH_APPRSTATUS_ALL) {
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_STATUS = ?");

            switch (model.getRsvApprStatus()) {
                case GSConstReserve.SRH_APPRSTATUS_NORMAL:
                    sql.addIntValue(GSConstReserve.RSY_APPR_STATUS_NORMAL);
                    break;
                case GSConstReserve.SRH_APPRSTATUS_NOAPPR:
                case GSConstReserve.SRH_APPRSTATUS_APPRONLY:
                    sql.addIntValue(GSConstReserve.RSY_APPR_STATUS_NOAPPR);
                    break;
                default:
                    sql.addIntValue(GSConstReserve.RSY_APPR_STATUS_NORMAL);
            }

            //管理者として指定されている施設グループ内の
            //施設のみを対象とする
            if (model.getRsvApprStatus() == GSConstReserve.SRH_APPRSTATUS_APPRONLY) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_DATA.RSG_SID in (");
                sql.addSql("     select");
                sql.addSql("       RSV_SIS_GRP.RSG_SID");
                sql.addSql("     from");
                sql.addSql("       RSV_SIS_GRP,");
                sql.addSql("       RSV_SIS_ADM");
                sql.addSql("     where");
                sql.addSql("       RSV_SIS_GRP.RSG_ADM_KBN = ?");
                sql.addSql("     and");
                sql.addSql("       (");
                sql.addSql("        RSV_SIS_ADM.USR_SID = ?");
                sql.addSql("       or ");
                sql.addSql("        RSV_SIS_ADM.GRP_SID in (");
                sql.addSql("           select");
                sql.addSql("             GRP_SID");
                sql.addSql("           from");
                sql.addSql("             CMN_BELONGM");
                sql.addSql("           where ");
                sql.addSql("            USR_SID=?");
                sql.addSql("        )");
                sql.addSql("       )");
                sql.addSql("     and");
                sql.addSql("       RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
                sql.addSql("   )");

                sql.addIntValue(GSConstReserve.RSG_ADM_KBN_OK);
                sql.addIntValue(model.getUsrSid());
                sql.addIntValue(model.getUsrSid());
            }
        }
        //登録日
        if (model.getRsvADateFrom() != null) {
            sql.addSql(" and RSV_SIS_YRK.RSY_ADATE >= ?");
            sql.addDateValue(model.getRsvADateFrom());
        }
        if (model.getRsvADateTo() != null) {
            sql.addSql(" and RSV_SIS_YRK.RSY_ADATE < ?");
            sql.addDateValue(model.getRsvADateTo());
        }
        //編集日
        if (model.getRsvEDateFrom() != null) {
            sql.addSql(" and RSV_SIS_YRK.RSY_EDATE >= ?");
            sql.addDateValue(model.getRsvEDateFrom());
        }
        if (model.getRsvEDateTo() != null) {
            sql.addSql(" and RSV_SIS_YRK.RSY_EDATE < ?");
            sql.addDateValue(model.getRsvEDateTo());
        }
    }
    /**
     * <br>[機  能] 施設予約画面に表示する予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]更新者IDと等しいユーザIDを持つレコードを取得
     *
     * @param rsySid 予約SID
     * @return ret 取得結果
     * @throws SQLException SQL実行例外
     */
    public Rsv110SisetuModel selectYoyakuEditData(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        Rsv110SisetuModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSD_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_MOK,");
            sql.addSql("   RSV_SIS_YRK.RSY_FR_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_TO_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_BIKO,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_ADATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_EUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT,");
            sql.addSql("   RSV_SIS_YRK.RSY_PUBLIC,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_STATUS,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_KBN as RSY_APPR_KBN,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   RSV_SIS_KYRK.RKY_BUSYO,");
            sql.addSql("   RSV_SIS_KYRK.RKY_NAME,");
            sql.addSql("   RSV_SIS_KYRK.RKY_NUM,");
            sql.addSql("   RSV_SIS_KYRK.RKY_USE_KBN,");
            sql.addSql("   RSV_SIS_KYRK.RKY_CONTACT,");
            sql.addSql("   RSV_SIS_KYRK.RKY_GUIDE,");
            sql.addSql("   RSV_SIS_KYRK.RKY_PARK_NUM,");
            sql.addSql("   RSV_SIS_KYRK.RKY_PRINT_KBN,");
            sql.addSql("   RSV_SIS_KYRK.RKY_DEST");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK left join RSV_SIS_KYRK");
            sql.addSql("     on RSV_SIS_YRK.RSY_SID = RSV_SIS_KYRK.RSY_SID,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_YRK.RSY_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSY_EUID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new Rsv110SisetuModel();
                ret.setRsdSid(rs.getInt("RSD_SID"));
                ret.setRsyMok(rs.getString("RSY_MOK"));
                ret.setRsyFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE")));
                ret.setRsyToDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_TO_DATE")));
                ret.setRsyBiko(rs.getString("RSY_BIKO"));
                ret.setRsyAuid(rs.getInt("RSY_AUID"));
                ret.setRsyEuid(rs.getInt("RSY_EUID"));
                ret.setRsyAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_ADATE")));
                ret.setRsyEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_EDATE")));
                ret.setRsyEdit(rs.getInt("RSY_EDIT"));
                ret.setRsyPublic(rs.getInt("RSY_PUBLIC"));
                ret.setScdRsSid(rs.getInt("SCD_RSSID"));
                ret.setRsrRsid(rs.getInt("RSR_RSID"));
                ret.setRsyApprStatus(rs.getInt("RSY_APPR_STATUS"));
                ret.setRsyApprKbn(rs.getInt("RSY_APPR_KBN"));
                ret.setUsiSei(rs.getString("USI_SEI"));
                ret.setUsiMei(rs.getString("USI_MEI"));
                ret.setUsrJkbn(rs.getInt("USR_JKBN"));
                ret.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                ret.setRkyBusyo(rs.getString("RKY_BUSYO"));
                ret.setRkyName(rs.getString("RKY_NAME"));
                ret.setRkyNum(rs.getString("RKY_NUM"));
                ret.setRkyUseKbn(rs.getInt("RKY_USE_KBN"));
                ret.setRkyContact(rs.getString("RKY_CONTACT"));
                ret.setRkyGuide(rs.getString("RKY_GUIDE"));
                ret.setRkyParkNum(rs.getString("RKY_PARK_NUM"));
                ret.setRkyPrintKbn(rs.getInt("RKY_PRINT_KBN"));
                ret.setRkyDest(rs.getString("RKY_DEST"));

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
     * <br>[機  能] 施設予約画面に表示する予約情報を取得する
     * <br>[解  説]
     * <br>[備  考]登録者IDと等しいユーザIDを持つレコードを取得
     *
     * @param rsySid 予約SID
     * @return ret 取得結果
     * @throws SQLException SQL実行例外
     */
    public Rsv110SisetuModel selectYoyakuAddData(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        Rsv110SisetuModel ret = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSD_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_MOK,");
            sql.addSql("   RSV_SIS_YRK.RSY_FR_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_TO_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_BIKO,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_ADATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_EUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT,");
            sql.addSql("   RSV_SIS_YRK.RSY_PUBLIC,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_STATUS,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_KBN as RSY_APPR_KBN,");
            sql.addSql("   CMN_USRM_INF.USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI,");
            sql.addSql("   CMN_USRM.USR_JKBN,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG,");
            sql.addSql("   RSV_SIS_KYRK.RKY_BUSYO,");
            sql.addSql("   RSV_SIS_KYRK.RKY_NAME,");
            sql.addSql("   RSV_SIS_KYRK.RKY_NUM,");
            sql.addSql("   RSV_SIS_KYRK.RKY_USE_KBN,");
            sql.addSql("   RSV_SIS_KYRK.RKY_CONTACT,");
            sql.addSql("   RSV_SIS_KYRK.RKY_GUIDE,");
            sql.addSql("   RSV_SIS_KYRK.RKY_PARK_NUM,");
            sql.addSql("   RSV_SIS_KYRK.RKY_PRINT_KBN,");
            sql.addSql("   RSV_SIS_KYRK.RKY_DEST");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK left join RSV_SIS_KYRK");
            sql.addSql("     on RSV_SIS_YRK.RSY_SID = RSV_SIS_KYRK.RSY_SID,");
            sql.addSql("   CMN_USRM,");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" where ");
            sql.addSql("   RSV_SIS_YRK.RSY_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID = CMN_USRM.USR_SID");
            sql.addSql(" and");
            sql.addSql("   CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new Rsv110SisetuModel();
                ret.setRsdSid(rs.getInt("RSD_SID"));
                ret.setRsyMok(rs.getString("RSY_MOK"));
                ret.setRsyFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE")));
                ret.setRsyToDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_TO_DATE")));
                ret.setRsyBiko(rs.getString("RSY_BIKO"));
                ret.setRsyAuid(rs.getInt("RSY_AUID"));
                ret.setRsyEuid(rs.getInt("RSY_EUID"));
                ret.setRsyAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_ADATE")));
                ret.setRsyEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_EDATE")));
                ret.setRsyEdit(rs.getInt("RSY_EDIT"));
                ret.setRsyPublic(rs.getInt("RSY_PUBLIC"));
                ret.setScdRsSid(rs.getInt("SCD_RSSID"));
                ret.setRsrRsid(rs.getInt("RSR_RSID"));
                ret.setRsyApprStatus(rs.getInt("RSY_APPR_STATUS"));
                ret.setRsyApprKbn(rs.getInt("RSY_APPR_KBN"));
                ret.setUsiSei(rs.getString("USI_SEI"));
                ret.setUsiMei(rs.getString("USI_MEI"));
                ret.setUsrJkbn(rs.getInt("USR_JKBN"));
                ret.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                ret.setRkyBusyo(rs.getString("RKY_BUSYO"));
                ret.setRkyName(rs.getString("RKY_NAME"));
                ret.setRkyNum(rs.getString("RKY_NUM"));
                ret.setRkyUseKbn(rs.getInt("RKY_USE_KBN"));
                ret.setRkyContact(rs.getString("RKY_CONTACT"));
                ret.setRkyGuide(rs.getString("RKY_GUIDE"));
                ret.setRkyParkNum(rs.getString("RKY_PARK_NUM"));
                ret.setRkyPrintKbn(rs.getInt("RKY_PRINT_KBN"));
                ret.setRkyDest(rs.getString("RKY_DEST"));

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
     * <br>[機  能] 施設ごとに指定された日時の予約状況を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsdSid 施設SID
     * @param date 判定 日時
     * @return ret true:予約済 false:予約なし
     * @throws SQLException SQL実行例外
     */
    public boolean isYrk(int rsdSid,
                             UDate date)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        boolean ret = false;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(RSY_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   ? between RSY_FR_DATE  and RSY_TO_DATE");
            sql.addSql(" and");
            sql.addSql("   RSD_SID = ?");
            sql.addDateValue(date);
            sql.addIntValue(rsdSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 施設ごとに指定された期間の予約情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param yrkSid 予約SID(新規時は-1)
     * @param rsdSid 施設SID
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret true:予約可能 false:予約不可能
     * @throws SQLException SQL実行例外
     */
    public boolean isYrkOk(int yrkSid,
                             int rsdSid,
                             UDate frDate,
                             UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        boolean ret = true;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(RSY_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("      RSY_FR_DATE >= ?");
            sql.addSql("      and");
            sql.addSql("      RSY_FR_DATE < ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      RSY_TO_DATE > ?");
            sql.addSql("      and");
            sql.addSql("      RSY_TO_DATE <= ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? >= RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? < RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? > RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? <= RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RSD_SID = ?");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);
            sql.addIntValue(rsdSid);

            if (yrkSid != -1) {
                sql.addSql(" and");
                sql.addSql("   RSY_SID <> ?");
                sql.addIntValue(yrkSid);
            }

            //却下された施設予約は重複チェック対象から除外する
            sql.addSql(" and");
            sql.addSql("   RSY_APPR_KBN <> ?");
            sql.addIntValue(GSConstReserve.RSY_APPR_KBN_REJECTION);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = false;
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
     * <br>[機  能] 施設ごとに指定された期間の予約情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param yrkSid 予約SID(新規時は-1)
     * @param rsdSid 施設SID
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret 予約重複リスト
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisYrkModel> getYrkNgList(int yrkSid,
                             int rsdSid,
                             UDate frDate,
                             UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<RsvSisYrkModel> ret = new ArrayList<RsvSisYrkModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSY_SID,");
            sql.addSql("   RSV_SIS_YRK.RSD_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_YGRP_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_MOK,");
            sql.addSql("   RSV_SIS_YRK.RSY_FR_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_TO_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_BIKO,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_ADATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_EUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDATE,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT,");
            sql.addSql("   RSV_SIS_YRK.RSY_PUBLIC,");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_STATUS,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_KBN as RSY_APPR_KBN,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_UID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_DATE,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_YRK.RSD_SID = RSV_SIS_DATA.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("      RSV_SIS_YRK.RSY_FR_DATE >= ?");
            sql.addSql("      and");
            sql.addSql("      RSV_SIS_YRK.RSY_FR_DATE < ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      RSV_SIS_YRK.RSY_TO_DATE > ?");
            sql.addSql("      and");
            sql.addSql("      RSV_SIS_YRK.RSY_TO_DATE <= ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? >= RSV_SIS_YRK.RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? < RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? > RSV_SIS_YRK.RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? <= RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSD_SID = ?");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);
            sql.addIntValue(rsdSid);

            if (yrkSid != -1) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_YRK.RSY_SID <> ?");
                sql.addIntValue(yrkSid);
            }

            //却下された施設予約は重複チェック対象から除外する
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_KBN <> ?");
            sql.addIntValue(GSConstReserve.RSY_APPR_KBN_REJECTION);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RsvSisYrkModel model = __getRsvSisYrkFromRs(rs);
                model.setRsdName(rs.getString("RSD_NAME"));
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
     * <br>[機  能] 施設ごとに指定された期間の予約情報一覧を取得する
     * <br>[解  説]指定された拡張予約情報リレーションを除く
     * <br>[備  考]
     *
     * @param rsrSid 拡張予約情報リレーション
     * @param yrkSid 予約SID(新規時は-1)
     * @param rsdSid 施設SID
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret 予約重複リスト
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisYrkModel> getSchYrkNgList(int rsrSid, int yrkSid,
                             int rsdSid,
                             UDate frDate,
                             UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<RsvSisYrkModel> ret = new ArrayList<RsvSisYrkModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSY_SID,");
            sql.addSql("   RSV_SIS_YRK.RSD_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_YGRP_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_MOK,");
            sql.addSql("   RSV_SIS_YRK.RSY_FR_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_TO_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_BIKO,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_ADATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_EUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDATE,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT,");
            sql.addSql("   RSV_SIS_YRK.RSY_PUBLIC,");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_STATUS,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_KBN as RSY_APPR_KBN,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_UID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_DATE,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_YRK.RSD_SID = RSV_SIS_DATA.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("      RSV_SIS_YRK.RSY_FR_DATE >= ?");
            sql.addSql("      and");
            sql.addSql("      RSV_SIS_YRK.RSY_FR_DATE < ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      RSV_SIS_YRK.RSY_TO_DATE > ?");
            sql.addSql("      and");
            sql.addSql("      RSV_SIS_YRK.RSY_TO_DATE <= ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? >= RSV_SIS_YRK.RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? < RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? > RSV_SIS_YRK.RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? <= RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSD_SID = ?");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);
            sql.addIntValue(rsdSid);

            if (yrkSid != -1) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_YRK.RSY_SID <> ?");
                sql.addIntValue(yrkSid);
            }

            if (rsrSid != -1) {
                sql.addSql(" and");
                sql.addSql("   RSV_SIS_YRK.RSR_RSID <> ?");
                sql.addIntValue(rsrSid);
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RsvSisYrkModel model = __getRsvSisYrkFromRs(rs);
                model.setRsdName(rs.getString("RSD_NAME"));
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
     * <br>[機  能] 拡張予約時、予約重複チェックを行う
     * <br>[解  説]
     * <br>[備  考] 変更前の自データの予約はチェック対象としない
     *
     * @param rsrRsid 拡張予約予約SID
     * @param rsdSid 施設SID
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret true:予約可能 false:予約不可能
     * @throws SQLException SQL実行例外
     */
    public boolean isKakutyoYrkOk(int rsrRsid,
                                    int rsdSid,
                                    UDate frDate,
                                    UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        boolean ret = true;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(RSY_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("      RSY_FR_DATE >= ?");
            sql.addSql("      and");
            sql.addSql("      RSY_FR_DATE < ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      RSY_TO_DATE > ?");
            sql.addSql("      and");
            sql.addSql("      RSY_TO_DATE <= ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? >= RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? < RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? > RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? <= RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RSD_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSR_RSID <> ?");


            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);
            sql.addIntValue(rsdSid);
            sql.addIntValue(rsrRsid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                if (rs.getInt("cnt") > 0) {
                    ret = false;
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
     * <br>[機  能] 施設ごとに指定された期間の拡張予約情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考] 変更前の自データの予約は取得としない
     *
     * @param rsrRsid 拡張予約予約SID
     * @param rsdSid 施設SID
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret 予約重複リスト
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisYrkModel> getKakutyoYrkNgList(int rsrRsid,
                                    int rsdSid,
                                    UDate frDate,
                                    UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<RsvSisYrkModel> ret = new ArrayList<RsvSisYrkModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSY_SID,");
            sql.addSql("   RSV_SIS_YRK.RSD_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_YGRP_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_MOK,");
            sql.addSql("   RSV_SIS_YRK.RSY_FR_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_TO_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_BIKO,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_ADATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_EUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDATE,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT,");
            sql.addSql("   RSV_SIS_YRK.RSY_PUBLIC,");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_STATUS,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_KBN as RSY_APPR_KBN,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_UID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_DATE,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("      RSV_SIS_YRK.RSY_FR_DATE >= ?");
            sql.addSql("      and");
            sql.addSql("      RSV_SIS_YRK.RSY_FR_DATE < ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      RSV_SIS_YRK.RSY_TO_DATE > ?");
            sql.addSql("      and");
            sql.addSql("      RSV_SIS_YRK.RSY_TO_DATE <= ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? >= RSV_SIS_YRK.RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? < RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? > RSV_SIS_YRK.RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? <= RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSD_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID <> ?");


            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);
            sql.addIntValue(rsdSid);
            sql.addIntValue(rsrRsid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RsvSisYrkModel model = __getRsvSisYrkFromRs(rs);
                model.setRsdName(rs.getString("RSD_NAME"));
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
     * <br>[機  能] 施設ごとに指定された期間の拡張予約情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考] 変更前の自データの予約は取得としない、指定したスケジュールを除く
     *
     * @param rsrRsid 拡張予約予約SID
     * @param rsdSid 施設SID
     * @param frDate 条件 予約開始
     * @param toDate 条件 予約終了
     * @return ret 予約重複リスト
     * @throws SQLException SQL実行例外
     */
    public List<RsvSisYrkModel> getSchKakutyoYrkNgList(int rsrRsid,
                                    int rsdSid,
                                    UDate frDate,
                                    UDate toDate)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<RsvSisYrkModel> ret = new ArrayList<RsvSisYrkModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSV_SIS_YRK.RSY_SID,");
            sql.addSql("   RSV_SIS_YRK.RSD_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_YGRP_SID,");
            sql.addSql("   RSV_SIS_YRK.RSY_MOK,");
            sql.addSql("   RSV_SIS_YRK.RSY_FR_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_TO_DATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_BIKO,");
            sql.addSql("   RSV_SIS_YRK.RSY_AUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_ADATE,");
            sql.addSql("   RSV_SIS_YRK.RSY_EUID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDATE,");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_EDIT,");
            sql.addSql("   RSV_SIS_YRK.RSY_PUBLIC,");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_STATUS,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_KBN as RSY_APPR_KBN,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_UID,");
            sql.addSql("   RSV_SIS_YRK.RSY_APPR_DATE,");
            sql.addSql("   RSV_SIS_DATA.RSD_NAME");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK,");
            sql.addSql("   RSV_SIS_DATA");
            sql.addSql(" where");
            sql.addSql("   (");
            sql.addSql("     (");
            sql.addSql("      RSV_SIS_YRK.RSY_FR_DATE >= ?");
            sql.addSql("      and");
            sql.addSql("      RSV_SIS_YRK.RSY_FR_DATE < ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      RSV_SIS_YRK.RSY_TO_DATE > ?");
            sql.addSql("      and");
            sql.addSql("      RSV_SIS_YRK.RSY_TO_DATE <= ?");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? >= RSV_SIS_YRK.RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? < RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("     or");
            sql.addSql("     (");
            sql.addSql("      ? > RSV_SIS_YRK.RSY_FR_DATE");
            sql.addSql("      and");
            sql.addSql("      ? <= RSV_SIS_YRK.RSY_TO_DATE");
            sql.addSql("     )");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSD_SID = RSV_SIS_DATA.RSD_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSD_SID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID <> ?");

            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(frDate);
            sql.addDateValue(frDate);
            sql.addDateValue(toDate);
            sql.addDateValue(toDate);
            sql.addIntValue(rsdSid);
            sql.addIntValue(rsrRsid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                RsvSisYrkModel model = __getRsvSisYrkFromRs(rs);
                model.setRsdName(rs.getString("RSD_NAME"));
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
     * <br>[機  能] 指定された施設拡張SIDの登録日付リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 施設予約拡張SID
     * @return ret ArrayList in String
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getKurikaeshiDataList(int rsrRsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<String> ret = new ArrayList<String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_FR_DATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSR_RSID = ?");
            sql.addSql(" order by");
            sql.addSql("   RSY_FR_DATE asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(UDateUtil.getSlashYYMD(
                        UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE"))));
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
     * <br>[機  能] 指定された施設SIDの登録日付リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySid 施設予約SID
     * @return ret ArrayList in String
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getTanituDataList(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<String> ret = new ArrayList<String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_FR_DATE");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSY_SID = ?");
            sql.addSql(" order by");
            sql.addSql("   RSY_FR_DATE asc");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(UDateUtil.getSlashYYMD(
                        UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE"))));
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
     * <br>[機  能] 指定されたスケジュールリレーションSIDを持つ
     * <br>         施設SIDを全て取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param scdRsSidArray スケジュールリレーションSID配列
     * @return ret 登録対象施設SID配列
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getKakutyoAllRsdSid(ArrayList<Integer> scdRsSidArray)
        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSD_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   SCD_RSSID in (");

            for (int i = 0; i < scdRsSidArray.size(); i++) {
                sql.addSql("?");
                sql.addIntValue(scdRsSidArray.get(i));

                if (i != scdRsSidArray.size() - 1) {
                    sql.addSql(", ");
                }
            }

            sql.addSql("   )");
            sql.addSql(" group by");
            sql.addSql("   RSD_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getInt("RSD_SID"));
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
     * <br>[機  能] 指定された施設拡張SIDの施設予約件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 施設予約拡張SID
     * @return int データ件数
     * @throws SQLException SQL実行例外
     */
    public int getYrkDataCnt(int rsrRsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSR_RSID = ?");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);
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
     * <br>[機  能] 指定された施設拡張SIDの施設予約件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsids 施設予約拡張SID
     * @return 施設予約拡張SID データ件数 Map
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Integer> getYrkDataCntMap(Collection<Integer> rsrRsids) throws SQLException {

        Connection con = null;
        Map<Integer, Integer> ret= new HashMap<>();
        if (rsrRsids == null || rsrRsids.isEmpty()) {
            return ret;
        }
        ret.putAll(rsrRsids.stream().collect(Collectors.toMap(sid -> sid, sid -> 0)));

        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = rsrRsids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   RSR_RSID,");
        sb.append("   count(*) as CNT");
        sb.append(" from");
        sb.append("   RSV_SIS_YRK");
        sb.append(" where");

        con = getCon();

        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 500
                    && itr.hasNext()) {
                continue;
            }

            //500件毎に実行
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());
            sql.addSql(" RSR_RSID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addIntValue(exeItr.next());

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql(" )");
            sql.addSql(" group by RSR_RSID");

            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                try (ResultSet rs = pstmt.executeQuery();) {

                    while (rs.next()) {
                        ret.put(
                                rs.getInt("RSR_RSID"),
                                rs.getInt("CNT")
                                );
                    }

                }

            }
            exeList.clear();
        }
        return ret;

    }
    /**
     *
     * <br>[機  能] 指定された施設拡張SIDから紐づく施設予約が空のものを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param rsrRsids
     * @return 施設拡張SID
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> emptyRsid(Collection<Integer> rsrRsids) throws SQLException {
        return getYrkDataCntMap(rsrRsids).entrySet().stream()
        .filter(entry -> (entry.getValue() > 0))
        .map(Entry::getKey)
        .collect(Collectors.toList());
    }
    /**
     * <br>[機  能] 指定された施設拡張SIDの施設予約件数を取得する
     * <br>[解  説]
     * <br>[備  考] 指定したスケジュールに紐づいている施設予約はカウントしない
     *
     * @param rsrRsid 施設予約拡張SID
     * @param scdSid スケジュールSID
     * @return int データ件数
     * @throws SQLException SQL実行例外
     */
    public int getYrkDataCnt(int rsrRsid, int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(RSV_SIS_YRK.RSY_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_YRK.RSR_RSID = ?");
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID in (");
            sql.addSql("     select");
            sql.addSql("       SCH_DATA.SCD_RSSID");
            sql.addSql("     from");
            sql.addSql("       SCH_DATA");
            sql.addSql("    where");
            sql.addSql("      SCH_DATA.SCD_SID != ?");
            sql.addSql("   )");


            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);
            sql.addIntValue(scdSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("CNT");
                log__.debug("カウント：" + ret);
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
     * <br>[機  能] 指定された施設拡張SIDの施設SIDを取得する
     * <br>[解  説] 拡張登録されている施設予約の最初のSIDを取得する
     * <br>[備  考]
     *
     * @param rsrRsid 施設予約拡張SID
     * @return int 施設SID
     * @throws SQLException SQL実行例外
     */
    public int getYrkDataSid(int rsrRsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSR_RSID = ?");
            sql.addSql(" order by");
            sql.addSql("   RSD_SID");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("RSY_SID");
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
     * <br>[機  能] 指定された施設拡張SIDの施設SIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 施設予約拡張SID
     * @return int 施設SID
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getYrkDataSidList(int rsrRsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        ArrayList<Integer> ret = new ArrayList<Integer>();;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSY_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSR_RSID = ?");


            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <br>[機  能] 指定された施設拡張SIDの施設予約SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsrRsid 施設予約拡張SID
     * @return int データ件数
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getScdRsSid(int rsrRsid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_RSSID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSR_RSID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsrRsid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
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
     * <br>[機  能] 指定された施設予約SIDの施設SIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param rsySid 施設予約SID
     * @return 施設SID
     * @throws SQLException SQL実行例外
     */
    public int getSisDataSid(int rsySid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSD_SID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSY_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsySid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("RSD_SID");
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
     * <p>未承認の施設予約情報件数を取得する
     * @param userSid ユーザSID
     * @return 施設予約情報件数
     * @throws SQLException SQL実行例外
     */
    public int getNotApprDataCount(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(RSY_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where ");
            sql.addSql("   RSY_APPR_STATUS=?");
            sql.addIntValue(GSConstReserve.RSY_APPR_STATUS_NOAPPR);

            //管理者として指定されている施設グループ内の
            //施設のみを対象とする
            sql.addSql(" and");
            sql.addSql("   RSV_SIS_YRK.RSD_SID in (");
            sql.addSql("     select");
            sql.addSql("       RSV_SIS_DATA.RSD_SID");
            sql.addSql("     from");
            sql.addSql("       RSV_SIS_GRP,");
            sql.addSql("       RSV_SIS_ADM,");
            sql.addSql("       RSV_SIS_DATA");
            sql.addSql("     where");
            sql.addSql("       RSV_SIS_GRP.RSG_ADM_KBN = ?");
            sql.addSql("     and");
            sql.addSql("       (");
            sql.addSql("        RSV_SIS_ADM.USR_SID = ?");
            sql.addSql("       or ");
            sql.addSql("        RSV_SIS_ADM.GRP_SID in (");
            sql.addSql("           select");
            sql.addSql("             GRP_SID");
            sql.addSql("           from");
            sql.addSql("             CMN_BELONGM");
            sql.addSql("           where ");
            sql.addSql("            USR_SID=?");
            sql.addSql("        )");
            sql.addSql("       )");
            sql.addSql("     and");
            sql.addSql("       RSV_SIS_GRP.RSG_SID = RSV_SIS_ADM.RSG_SID");
            sql.addSql("     and");
            sql.addSql("       RSV_SIS_GRP.RSG_SID = RSV_SIS_DATA.RSG_SID");
            sql.addSql("   )");
            sql.addIntValue(GSConstReserve.RSG_ADM_KBN_OK);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

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
     * <br>[機  能] 指定したスケジュール予約SIDから施設予約情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdRssid スケジュール予約SID
     * @return 施設予約情報の件数
     * @throws SQLException SQL実行例外
     */
    public int getSisYrkCountFromScdRs(int scdRssid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_YRK");
            sql.addSql(" where");
            sql.addSql("   RSV_SIS_YRK.SCD_RSSID = ?");

            sql.addIntValue(scdRssid);

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

    /**
     * <p>Create RSV_SIS_YRK Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvSisYrkModel
     * @throws SQLException SQL実行例外
     */
    private RsvSisYrkModel __getRsvSisYrkFromRs(ResultSet rs) throws SQLException {
        RsvSisYrkModel bean = new RsvSisYrkModel();
        bean.setRsySid(rs.getInt("RSY_SID"));
        bean.setRsdSid(rs.getInt("RSD_SID"));
        bean.setRsyYgrpSid(rs.getInt("RSY_YGRP_SID"));
        bean.setRsyMok(rs.getString("RSY_MOK"));
        bean.setRsyFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_FR_DATE")));
        bean.setRsyToDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_TO_DATE")));
        bean.setRsyBiko(rs.getString("RSY_BIKO"));
        bean.setRsyAuid(rs.getInt("RSY_AUID"));
        bean.setRsyAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_ADATE")));
        bean.setRsyEuid(rs.getInt("RSY_EUID"));
        bean.setRsyEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_EDATE")));
        bean.setScdRsSid(rs.getInt("SCD_RSSID"));
        bean.setRsyEdit(rs.getInt("RSY_EDIT"));
        bean.setRsyPublic(rs.getInt("RSY_PUBLIC"));
        bean.setRsrRsid(rs.getInt("RSR_RSID"));
        bean.setRsyApprStatus(rs.getInt("RSY_APPR_STATUS"));
        bean.setRsyApprKbn(rs.getInt("RSY_APPR_KBN"));
        bean.setRsyApprUid(rs.getInt("RSY_APPR_UID"));
        bean.setRsyApprDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSY_APPR_DATE")));
        return bean;
    }
}