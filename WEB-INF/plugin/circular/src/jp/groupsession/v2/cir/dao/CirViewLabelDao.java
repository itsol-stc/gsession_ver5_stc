package jp.groupsession.v2.cir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.model.CirAdelModel;
import jp.groupsession.v2.cir.model.CirViewLabelModel;
import jp.groupsession.v2.cir.model.CirViewModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <p>CIR_VIEW_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CirViewLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirViewLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public CirViewLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirViewLabelDao(Connection con) {
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
            sql.addSql("drop table CIR_VIEW_LABEL");

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
            sql.addSql(" create table CIR_VIEW_LABEL (");
            sql.addSql("   CIF_SID integer not null,");
            sql.addSql("   CAC_SID integer not null,");
            sql.addSql("   CLA_SID integer not null,");
            sql.addSql("   primary key (CIF_SID,CAC_SID,CLA_SID)");
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
     * <p>Insert CIR_VIEW_LABEL Data Bindding JavaBean
     * @param bean CIR_VIEW_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirViewLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_VIEW_LABEL(");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLA_SID");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCifSid());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getClaSid());
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
     * <p>Update CIR_VIEW_LABEL Data Bindding JavaBean
     * @param bean CIR_VIEW_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirViewLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" set ");
            sql.addSql("   CIF_SID=?,");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   CLA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCifSid());
            //where
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getClaSid());

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
     * <p>Select CIR_VIEW_LABEL All Data
     * @return List in CIR_VIEW_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<CirViewLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirViewLabelModel> ret = new ArrayList<CirViewLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLA_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_VIEW_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirViewLabelFromRs(rs));
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
     * <p>Select CIR_VIEW_LABEL
     * @param cacSid CAC_SID
     * @param cifSid CIF_SID
     * @return CIR_VIEW_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<CirViewLabelModel>  selectLabelList(int cacSid, int cifSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CirViewLabelModel> ret = new ArrayList<CirViewLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLA_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   CIF_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.addIntValue(cifSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirViewLabelFromRs(rs));
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
     * <p>Select CIR_VIEW_LABEL
     * @param cacSid CAC_SID
     * @param claSid CLA_SID
     * @return CIR_VIEW_LABELModel
     * @throws SQLException SQL実行例外
     */
    public CirViewLabelModel select(int cacSid, int claSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirViewLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIF_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLA_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   CLA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.addIntValue(claSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCirViewLabelFromRs(rs);
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
     * <p>Delete CIR_VIEW_LABEL
     * @param cacSid CAC_SID
     * @param claSid CLA_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(int claSid, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   CLA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.addIntValue(claSid);

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
     * <p>Delete CIR_VIEW_LABEL
     * @param bean CirViewLabelModel
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(CirViewLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" and");
            sql.addSql("   CLA_SID=?");
            sql.addSql(" and");
            sql.addSql("   CIF_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getClaSid());
            sql.addIntValue(bean.getCifSid());

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
     * <p>アカウント削除時におけるラベル削除
     * @param cacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteAccountLabel(String[] cacSid) throws SQLException {

        if (cacSid == null || cacSid.length < 1) {
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
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID in (");
            //where
            for (int i = 0; i < cacSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(cacSid[i], 0));

                if (i < cacSid.length - 1) {
                    sql.addSql("     , ");
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
     * <p>アカウント削除時におけるラベル削除
     * @param cacSid アカウントSID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteAccountLabel(int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addIntValue(cacSid);
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
     * <p>回覧板削除時におけるラベル削除
     * @param cifSid ラベルSID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteCircularLabel(String[] cifSid) throws SQLException {

        if (cifSid == null || cifSid.length < 1) {
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
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID in (");
            //where
            for (int i = 0; i < cifSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(cifSid[i], 0));

                if (i < cifSid.length - 1) {
                    sql.addSql("     , ");
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
     * <p>回覧板削除時におけるラベル削除
     * @param cifSid ラベルSID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteCircularLabel(int cifSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cifSid);
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
     * <p>ゴミ箱を空にするにおけるラベル削除
     * @param juBean CirViewModel
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteGomiCircularLabel(CirViewModel juBean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID in (");
            sql.addSql(" select");
            sql.addSql("   CIF_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CVW_DSP = ?");
            sql.addSql(" )");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(juBean.getCacSid());
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_NG);
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
     * <br>[機  能] 手動削除・自動削除時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delList 削除ユーザの個人設定リスト
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @throws SQLException SQL実行例外
     */
    public void deleteLabel(ArrayList<CirAdelModel> delList, int kbn)
        throws SQLException {
        if (delList == null || delList.size() < 1) {
            return;
        }
        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();
        int jkbn = -1;

        if (kbn == 1) {
            jkbn = GSConstCircular.DSPKBN_DSP_OK;
        } else if (kbn == 2) {
            jkbn = GSConstCircular.DSPKBN_DSP_NG;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CIF_SID in (");
            sql.addSql(" select");
            sql.addSql("   CIF_SID ");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CVW_ADATE <= ?");
            sql.addSql(" and");
            sql.addSql("   CVW_DSP = ?");
            sql.addSql(" )");


            pstmt = con.prepareStatement(sql.toSqlString());
            for (CirAdelModel mdl : delList) {

                int year;
                int month;
                if (kbn == 1) {
                    year = mdl.getCadJdelYear();
                    month = mdl.getCadJdelMonth();
                } else {
                    year = mdl.getCadDdelYear();
                    month = mdl.getCadDdelMonth();
                }
                UDate delUd = now.cloneUDate();

                delUd.addYear((year * -1));
                delUd.addMonth((month * -1));
                delUd.setHour(GSConstMain.DAY_END_HOUR);
                delUd.setMinute(GSConstMain.DAY_END_MINUTES);
                delUd.setSecond(GSConstMain.DAY_END_SECOND);
                delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

                pstmt.setInt(1, mdl.getCacSid());
                pstmt.setInt(2, mdl.getCacSid());
                pstmt.setTimestamp(3, delUd.toSQLTimestamp());
                pstmt.setInt(4, jkbn);


                //ログ出力
                sql.addIntValue(mdl.getCacSid());
                sql.addIntValue(mdl.getCacSid());
                sql.addDateValue(delUd);
                sql.addIntValue(jkbn);

                log__.info(sql.toLogString());

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
     * <br>[機  能] 手動削除・自動削除時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param delList 削除ユーザの個人設定リスト
     * @param kbn 1:通常データ 2:ゴミ箱データ
     * @param delMdl CirAdelModel
     * @throws SQLException SQL実行例外
     */
    public void deleteLabel(List<CirViewModel> delList, CirAdelModel delMdl, int kbn)
        throws SQLException {
        if (delList == null || delList.size() < 1) {
            return;
        }
        PreparedStatement pstmt = null;
        Connection con = getCon();

        UDate now = new UDate();
        int jkbn = -1;

        if (kbn == 1) {
            jkbn = GSConstCircular.DSPKBN_DSP_OK;
        } else if (kbn == 2) {
            jkbn = GSConstCircular.DSPKBN_DSP_NG;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CIF_SID in (");
            sql.addSql(" select");
            sql.addSql("   CIF_SID ");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CVW_ADATE <= ?");
            sql.addSql(" and");
            sql.addSql("   CVW_DSP = ?");
            sql.addSql(" )");


            pstmt = con.prepareStatement(sql.toSqlString());
            for (CirViewModel mdl : delList) {

                int year;
                int month;
                if (kbn == 1) {
                    year = delMdl.getCadJdelYear();
                    month = delMdl.getCadJdelMonth();
                } else {
                    year = delMdl.getCadDdelYear();
                    month = delMdl.getCadDdelMonth();
                }
                UDate delUd = now.cloneUDate();

                delUd.addYear((year * -1));
                delUd.addMonth((month * -1));
                delUd.setHour(GSConstMain.DAY_END_HOUR);
                delUd.setMinute(GSConstMain.DAY_END_MINUTES);
                delUd.setSecond(GSConstMain.DAY_END_SECOND);
                delUd.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

                pstmt.setInt(1, mdl.getCacSid());
                pstmt.setInt(2, mdl.getCacSid());
                pstmt.setTimestamp(3, delUd.toSQLTimestamp());
                pstmt.setInt(4, jkbn);


                //ログ出力
                sql.addIntValue(mdl.getCacSid());
                sql.addIntValue(mdl.getCacSid());
                sql.addDateValue(delUd);
                sql.addIntValue(jkbn);

                log__.info(sql.toLogString());

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
     * <p>Create CIR_VIEW_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirViewLabelModel
     * @throws SQLException SQL実行例外
     */
    private CirViewLabelModel __getCirViewLabelFromRs(ResultSet rs) throws SQLException {
        CirViewLabelModel bean = new CirViewLabelModel();
        bean.setCifSid(rs.getInt("CIF_SID"));
        bean.setCacSid(rs.getInt("CAC_SID"));
        bean.setClaSid(rs.getInt("CLA_SID"));
        return bean;
    }

    /**
     * <p>回覧板送信先削除時におけるラベル削除
     * @param cifSid 回覧板SID
     * @param accSidList 対象アカウントSID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int deleteCircularLabel(int cifSid, List<Integer> accSidList) throws SQLException {


        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();
        if (accSidList == null) {
            return count;
        }
        if (accSidList.size() < 1) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CIF_SID = ?");
            sql.addIntValue(cifSid);
            sql.addSql("   and CAC_SID in (");
            for (int i = 0; i < accSidList.size(); i++) {
                if (i > 0) {
                    sql.addSql("     , ");
                }
                sql.addSql(" " + accSidList.get(i));

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
}
