package jp.groupsession.v2.cir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.model.CirLabelModel;
import jp.groupsession.v2.cir.model.LabelDataModel;

/**
 * <p>CIR_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CirLabelDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirLabelDao.class);

    /**
     * <p>Default Constructor
     */
    public CirLabelDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirLabelDao(Connection con) {
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
            sql.addSql("drop table CIR_LABEL");

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
            sql.addSql(" create table CIR_LABEL (");
            sql.addSql("   CLA_SID integer not null,");
            sql.addSql("   CAC_SID integer not null,");
            sql.addSql("   CLA_NAME varchar(100) not null,");
            sql.addSql("   CLA_ORDER integer not null,");
            sql.addSql("   CLA_AUID integer not null,");
            sql.addSql("   CLA_ADATE timestamp not null,");
            sql.addSql("   CLA_EUID integer not null,");
            sql.addSql("   CLA_EDATE timestamp not null,");
            sql.addSql("   primary key (CLA_SID)");
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
     * <p>Insert CIR_LABEL Data Bindding JavaBean
     * @param bean CIR_LABEL Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_LABEL(");
            sql.addSql("   CLA_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLA_NAME,");
            sql.addSql("   CLA_ORDER,");
            sql.addSql("   CLA_AUID,");
            sql.addSql("   CLA_ADATE,");
            sql.addSql("   CLA_EUID,");
            sql.addSql("   CLA_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getClaSid());
            sql.addIntValue(bean.getCacSid());
            sql.addStrValue(bean.getClaName());
            sql.addIntValue(bean.getClaOrder());
            sql.addIntValue(bean.getClaAuid());
            sql.addDateValue(bean.getClaAdate());
            sql.addIntValue(bean.getClaEuid());
            sql.addDateValue(bean.getClaEdate());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update CIR_LABEL Data Bindding JavaBean
     * @param bean CIR_LABEL Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirLabelModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_LABEL");
            sql.addSql(" set ");
            sql.addSql("   CAC_SID=?,");
            sql.addSql("   CLA_NAME=?,");
            sql.addSql("   CLA_EUID=?,");
            sql.addSql("   CLA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CLA_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addStrValue(bean.getClaName());
            sql.addIntValue(bean.getClaEuid());
            sql.addDateValue(bean.getClaEdate());
            //where
            sql.addIntValue(bean.getClaSid());
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
     * <p>Select CIR_LABEL All Data
     * @return List in CIR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<CirLabelModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirLabelModel> ret = new ArrayList<CirLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CLA_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLA_NAME,");
            sql.addSql("   CLA_ORDER,");
            sql.addSql("   CLA_AUID,");
            sql.addSql("   CLA_ADATE,");
            sql.addSql("   CLA_EUID,");
            sql.addSql("   CLA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_LABEL");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirLabelFromRs(rs));
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
     * <br>[機  能] 指定したユーザのラベル情報をソート順通りに取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in CIR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public List<CirLabelModel> select(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirLabelModel> ret = new ArrayList<CirLabelModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CLA_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLA_NAME,");
            sql.addSql("   CLA_ORDER,");
            sql.addSql("   CLA_AUID,");
            sql.addSql("   CLA_ADATE,");
            sql.addSql("   CLA_EUID,");
            sql.addSql("   CLA_EDATE");
            sql.addSql(" from ");
            sql.addSql("   CIR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");
            sql.addSql(" order by ");
            sql.addSql("   CLA_ORDER asc ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(userSid);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirLabelFromRs(rs));
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
     * <p>Select CIR_LABEL
     * @param claSid CLA_SID
     * @param cacSid CAC_SID
     * @return CIR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public CirLabelModel select(int claSid, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CLA_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLA_NAME,");
            sql.addSql("   CLA_ORDER,");
            sql.addSql("   CLA_AUID,");
            sql.addSql("   CLA_ADATE,");
            sql.addSql("   CLA_EUID,");
            sql.addSql("   CLA_EDATE");
            sql.addSql(" from");
            sql.addSql("   CIR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CLA_SID=?");
            sql.addSql(" and ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(claSid);
            sql.addIntValue(cacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCirLabelFromRs(rs);
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
     * <p>Delete CIR_LABEL
     * @param claSid CLA_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int claSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CLA_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] ラベル生存チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param claSid ラベルSID
     * @param cacSid アカウントSID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public boolean selectExistLabel(int cacSid, int claSid)
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean ret = false;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CLA_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_LABEL ");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CLA_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.addIntValue(claSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = true;
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
     * <br>[機  能] ラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<LabelDataModel> selectLabelList(int cacSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelDataModel> ret =
            new ArrayList<LabelDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CLA_SID, ");
            sql.addSql("   CLA_NAME ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LABEL ");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   CLA_ORDER asc ");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(cacSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                LabelDataModel model = new LabelDataModel();
                model.setLabelSid(rs.getInt("CLA_SID"));
                model.setLabelName(rs.getString("CLA_NAME"));
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
     * <br>[機  能] ラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<LabelDataModel> selectLabelWidthCountList(int cacSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelDataModel> ret =
            new ArrayList<LabelDataModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_LABEL.CLA_SID, ");
            sql.addSql("   CIR_LABEL.CLA_NAME, ");
            sql.addSql("   CIR_VIEW.CNT");
            sql.addSql(" from ");
            sql.addSql("   CIR_LABEL ");
            sql.addSql(" left join ");
            sql.addSql(" (");
            sql.addSql("    select ");
            sql.addSql("   CIR_VIEW_LABEL.CLA_SID,");
            sql.addSql("   COUNT(CIR_VIEW.CIF_SID) as CNT");
            sql.addSql("  from");
            sql.addSql("   CIR_VIEW_LABEL");
            sql.addSql(" left join ");
            sql.addSql("   CIR_VIEW");
            sql.addSql("   on");
            sql.addSql(" CIR_VIEW.CIF_SID= CIR_VIEW_LABEL.CIF_SID");
            sql.addSql("  where");
            sql.addSql("    CIR_VIEW.CVW_CONF = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_VIEW.CVW_DSP = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_VIEW.CAC_SID = ?");
            sql.addSql("  group by CIR_VIEW_LABEL.CLA_SID");
            sql.addSql("  ) as CIR_VIEW");
            sql.addSql("  on CIR_LABEL.CLA_SID = CIR_VIEW.CLA_SID");
            sql.addSql("  where");
            sql.addSql("  CIR_LABEL.CAC_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   CLA_ORDER asc ");

            sql.addIntValue(GSConstCircular.CONF_UNOPEN);
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
            sql.addIntValue(cacSid);
            sql.addIntValue(cacSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                LabelDataModel model = new LabelDataModel();
                model.setLabelSid(rs.getInt("CLA_SID"));
                model.setLabelName(rs.getString("CLA_NAME"));
                model.setMidokuCount(rs.getInt("CNT"));
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
     * <br>[機  能] ラベル名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @param claSids ラベルSID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> selectLabelNameList(int cacSid, List<Integer> claSids)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelValueBean> ret =
            new ArrayList<LabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CLA_SID, ");
            sql.addSql("   CLA_NAME, ");
            sql.addSql("   CLA_ORDER ");
            sql.addSql(" from ");
            sql.addSql("   CIR_LABEL ");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addIntValue(cacSid);
            sql.addSql(" and ");
            sql.addSql("   CLA_SID in (");
            //where
            for (int i = 0; i < claSids.size(); i++) {
                sql.addSql("     ? ");
                sql.addIntValue(claSids.get(i));

                if (i < claSids.size() - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");
            sql.addSql(" order by ");
            sql.addSql("   CLA_ORDER asc ");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                LabelValueBean model = new LabelValueBean();
                model.setValue(String.valueOf(rs.getInt("CLA_SID")));
                model.setLabel(rs.getString("CLA_NAME"));
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
     * <p>Select CIR_LABEL
     * @param order ソート番号
     * @param cacSid アカウントSID
     * @return CIR_LABELModel
     * @throws SQLException SQL実行例外
     */
    public CirLabelModel selectClaSidFromOrder(int order, int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirLabelModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CLA_SID,");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CLA_NAME,");
            sql.addSql("   CLA_ORDER,");
            sql.addSql("   CLA_AUID,");
            sql.addSql("   CLA_ADATE,");
            sql.addSql("   CLA_EUID,");
            sql.addSql("   CLA_EDATE");
            sql.addSql(" from");
            sql.addSql("   CIR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CLA_ORDER=?");
            sql.addSql(" and ");
            sql.addSql("   CAC_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(order);
            sql.addIntValue(cacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getCirLabelFromRs(rs);
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
     * <br>[機  能] ラベルの並び順の現在最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @return ラベル最大値
     * @throws SQLException SQL実行時例外
     */
    public int maxSortNumber(int sacSid)
    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int maxNumber = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(CLA_ORDER) as MAX_ORDER");
            sql.addSql(" from");
            sql.addSql("   CIR_LABEL");
            sql.addSql(" where");
            sql.addSql("   CAC_SID = ?");
            sql.addIntValue(sacSid);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxNumber = rs.getInt("MAX_ORDER");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return maxNumber;
    }


    /**
     * <p>Update CIR_LABEL Data Bindding JavaBean
     * @param order 順番
     * @param userSid ユーザSID
     * @param claSid ラベルSID
     * @param now UDate
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateSort(int order, int userSid, UDate now, int claSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_LABEL");
            sql.addSql(" set ");
            sql.addSql("   CLA_ORDER=?,");
            sql.addSql("   CLA_EUID=?,");
            sql.addSql("   CLA_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   CLA_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(order);
            sql.addIntValue(userSid);
            sql.addDateValue(now);
            //where
            sql.addIntValue(claSid);
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
     * <br>[機  能] 並び順を更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @param now 現在日付
     * @param userSid ユーザSID
     * @param sort 削除ソート番号
     * @throws SQLException SQL実行時例外
     */
    public void updateSortAll(int cacSid, int userSid, UDate now, int sort)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CLA_SID,");
            sql.addSql("   CLA_ORDER,");
            sql.addSql("   CLA_EUID,");
            sql.addSql("   CLA_EDATE");
            sql.addSql(" from");
            sql.addSql("   CIR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   CLA_ORDER > ?");
            sql.addSql(" order by");
            sql.addSql("   CLA_ORDER");
            sql.addIntValue(cacSid);
            sql.addIntValue(sort);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int claSid = rs.getInt("CLA_SID");
                int order = rs.getInt("CLA_ORDER") - 1;
                updateSort(order, userSid, now, claSid);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
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
            sql.addSql("   CIR_LABEL");
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
            sql.addSql("   CIR_LABEL");
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
     * <p>Create CIR_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirLabelModel
     * @throws SQLException SQL実行例外
     */
    private CirLabelModel __getCirLabelFromRs(ResultSet rs) throws SQLException {
        CirLabelModel bean = new CirLabelModel();
        bean.setClaSid(rs.getInt("CLA_SID"));
        bean.setCacSid(rs.getInt("CAC_SID"));
        bean.setClaName(rs.getString("CLA_NAME"));
        bean.setClaOrder(rs.getInt("CLA_ORDER"));
        bean.setClaAuid(rs.getInt("CLA_AUID"));
        bean.setClaAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CLA_ADATE")));
        bean.setClaEuid(rs.getInt("CLA_EUID"));
        bean.setClaEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CLA_EDATE")));
        return bean;
    }
}
