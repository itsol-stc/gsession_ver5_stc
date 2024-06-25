package jp.groupsession.v2.fil.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.fil310.Fil310DisplayModel;
import jp.groupsession.v2.fil.model.FileMoneyMasterModel;

/**
 * <p>FILE_MONEY_MASTER Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class FileMoneyMasterDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileMoneyMasterDao.class);

    /**
     * <p>Default Constructor
     */
    public FileMoneyMasterDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public FileMoneyMasterDao(Connection con) {
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
            sql.addSql("drop table FILE_MONEY_MASTER");

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
            sql.addSql(" create table FILE_MONEY_MASTER (");
            sql.addSql("   FMM_SID integer not null,");
            sql.addSql("   FMM_NAME varchar(15) not null,");
            sql.addSql("   FMM_SORT integer not null,");
            sql.addSql("   primary key (FMM_SID)");
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
     * <p>Insert FILE_MONEY_MASTER Data Bindding JavaBean
     * @param bean FILE_MONEY_MASTER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(FileMoneyMasterModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" FILE_MONEY_MASTER(");
            sql.addSql("   FMM_SID,");
            sql.addSql("   FMM_NAME,");
            sql.addSql("   FMM_SORT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getFmmSid());
            sql.addStrValue(bean.getFmmName());
            sql.addIntValue(bean.getFmmSort());
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
     * <p>Update FILE_MONEY_MASTER Data Bindding JavaBean
     * @param bean FILE_MONEY_MASTER Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(FileMoneyMasterModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" set ");
            sql.addSql("   FMM_NAME=?,");
            sql.addSql("   FMM_SORT=?");
            sql.addSql(" where ");
            sql.addSql("   FMM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getFmmName());
            sql.addIntValue(bean.getFmmSort());
            //where
            sql.addIntValue(bean.getFmmSid());

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
     * <br>[機  能] 表示順序を入れ替える
     * <br>[解  説]
     * <br>[備  考]
     * @param fmmSid 外貨SID
     * @param fmmSort ソート順
     * @throws SQLException SQL実行例外
     */
    public void updateSort(int fmmSid, int fmmSort)
            throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" set");
            sql.addSql("   FMM_SORT = ?");
            sql.addSql(" where");
            sql.addSql("   FMM_SID = ?");

            sql.addIntValue(fmmSort);
            sql.addIntValue(fmmSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Select FILE_MONEY_MASTER All Data
     * @return List in FILE_MONEY_MASTERModel
     * @throws SQLException SQL実行例外
     */
    public List<FileMoneyMasterModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<FileMoneyMasterModel> ret = new ArrayList<FileMoneyMasterModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FMM_SID,");
            sql.addSql("   FMM_NAME,");
            sql.addSql("   FMM_SORT");
            sql.addSql(" from ");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" order by ");
            sql.addSql("   FMM_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getFileMoneyMasterFromRs(rs));
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
     * <p>Select FILE_MONEY_MASTER
     * @param fmmSid FMM_SID
     * @return FILE_MONEY_MASTERModel
     * @throws SQLException SQL実行例外
     */
    public FileMoneyMasterModel select(int fmmSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileMoneyMasterModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FMM_SID,");
            sql.addSql("   FMM_NAME,");
            sql.addSql("   FMM_SORT");
            sql.addSql(" from");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" where ");
            sql.addSql("   FMM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fmmSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileMoneyMasterFromRs(rs);
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
     * <p>Select FILE_MONEY_MASTER
     * @param fmmName FMM_NAME
     * @return FILE_MONEY_MASTERModel
     * @throws SQLException SQL実行例外
     */
    public FileMoneyMasterModel select(String fmmName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        FileMoneyMasterModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   FMM_SID,");
            sql.addSql("   FMM_NAME,");
            sql.addSql("   FMM_SORT");
            sql.addSql(" from");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" where ");
            sql.addSql("   FMM_NAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(fmmName);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getFileMoneyMasterFromRs(rs);
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
     * <br>[機  能] 指定された外貨名に対する外貨SIDを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param nameList 外貨名リスト
     * @return 外貨名に対する外貨SID
     * @throws SQLException SQL実行例外
     */
    public Map<String, Integer> getGaikaSidMap(List<String> nameList) throws SQLException {

        Map<String, Integer> ret = new HashMap<String, Integer>();
        if (nameList == null || nameList.size() < 1) {
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
            sql.addSql("   FMM_SID,");
            sql.addSql("   FMM_NAME");
            sql.addSql(" from");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" where ");
            sql.addSql("   FMM_NAME in (");
            for (int idx = 0; idx < nameList.size(); idx++) {
                if (idx != 0) {
                    sql.addSql(" ,");
                }
                sql.addSql(" ?");
                sql.addStrValue(nameList.get(idx));
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(rs.getString("FMM_NAME"), rs.getInt("FMM_SID"));
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
     * <br>[機  能] 外貨情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in FILE_MONEY_MASTERModel
     * @throws SQLException SQL実行例外
     */
    public List<Fil310DisplayModel> getGaikaList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Fil310DisplayModel> ret = new ArrayList<Fil310DisplayModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FMM_SID,");
            sql.addSql("   FMM_NAME,");
            sql.addSql("   FMM_SORT");
            sql.addSql(" from ");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" order by ");
            sql.addSql("   FMM_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Fil310DisplayModel model = new Fil310DisplayModel();
                model.setFmmSid(rs.getInt("FMM_SID"));
                model.setFmmName(rs.getString("FMM_NAME"));
                model.setFmmSort(rs.getInt("FMM_SORT"));
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
     * <br>[機  能] 指定の外貨が存在するか判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fmmSid 削除対象SID
     * @return true:存在する/false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existGaika(int fmmSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(FMM_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" where ");
            sql.addSql("   FMM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fmmSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <br>[機  能] 指定の外貨名が存在するか判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fmmName 外貨名
     * @return true:存在する/false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existGaikaName(String fmmName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(FMM_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" where ");
            sql.addSql("   FMM_NAME=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(fmmName);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <br>[機  能] 指定の外貨がファイル管理で使用されているか判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param fmmSid 削除対象SID
     * @return true:使用されている/false:使用されていない
     * @throws SQLException SQL実行例外
     */
    public boolean existUsedGaika(int fmmSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(FMM_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" where exists (");
            sql.addSql("   select");
            sql.addSql("     EMT_SID");
            sql.addSql("   from");
            sql.addSql("     FILE_DIRECTORY");
            sql.addSql("   where");
            sql.addSql("     FILE_DIRECTORY.EMT_SID = ?");
            sql.addSql(" )");
            sql.addSql(" or exists (");
            sql.addSql("   select");
            sql.addSql("     EMT_SID");
            sql.addSql("   from");
            sql.addSql("     FILE_FILE_REKI");
            sql.addSql("   where");
            sql.addSql("     FILE_FILE_REKI.EMT_SID = ?");
            sql.addSql(" );");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fmmSid);
            sql.addIntValue(fmmSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getInt("CNT") > 0;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return result;
    }

    /**
     * <br>[機  能] 外貨の並び順の現在最大値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ソート番号最大値
     * @throws SQLException SQL実行時例外
     */
    public int getMaxSort()
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
            sql.addSql("   max(FMM_SORT) as MAX_SORT");
            sql.addSql(" from");
            sql.addSql("   FILE_MONEY_MASTER");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                maxNumber = rs.getInt("MAX_SORT");
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
     * <p>Delete FILE_MONEY_MASTER
     * @param fmmSid FMM_SID
     * @throws SQLException SQL実行例外
     * @return 削除件数
     */
    public int delete(int fmmSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   FILE_MONEY_MASTER");
            sql.addSql(" where ");
            sql.addSql("   FMM_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(fmmSid);

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
     * <p>Create FILE_MONEY_MASTER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created FileMoneyMasterModel
     * @throws SQLException SQL実行例外
     */
    private FileMoneyMasterModel __getFileMoneyMasterFromRs(ResultSet rs) throws SQLException {
        FileMoneyMasterModel bean = new FileMoneyMasterModel();
        bean.setFmmSid(rs.getInt("FMM_SID"));
        bean.setFmmName(rs.getString("FMM_NAME"));
        bean.setFmmSort(rs.getInt("FMM_SORT"));
        return bean;
    }
}
