package jp.groupsession.v2.sch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sch.model.SchExdataBinModel;

/**
 * <p>SCH_EXDATA_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchExdataBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchExdataBinDao.class);

    /**
     * <p>Default Constructor
     */
    public SchExdataBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchExdataBinDao(Connection con) {
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
            sql.addSql("drop table SCH_EXDATA_BIN");

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
            sql.addSql(" create table SCH_EXDATA_BIN (");
            sql.addSql("   SCE_SID integer not null,");
            sql.addSql("   BIN_SID bigint not null,");
            sql.addSql("   primary key (SCE_SID,BIN_SID)");
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
     * <p>Insert SCH_EXDATA_BIN Data Bindding JavaBean
     * @param bean SCH_EXDATA_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchExdataBinModel bean) throws SQLException {

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
     * <p>Insert SCH_BIN Data Bindding JavaBean
     * @param beanList SCH_BIN DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<SchExdataBinModel> beanList) throws SQLException {

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<SchExdataBinModel> exeList = new ArrayList<>();
        Iterator<SchExdataBinModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" SCH_EXDATA_BIN(");
        sb.append("   SCE_SID,");
        sb.append("   BIN_SID");
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

            Iterator<SchExdataBinModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                SchExdataBinModel bean = exeItr.next();
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getSceSid());
                sql.addLongValue(bean.getBinSid());

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
     * <p>Update SCH_EXDATA_BIN Data Bindding JavaBean
     * @param bean SCH_EXDATA_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchExdataBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_EXDATA_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getSceSid());
            sql.addLongValue(bean.getBinSid());

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
     * <p>Select SCH_EXDATA_BIN All Data
     * @return List in SCH_EXDATA_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<SchExdataBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<SchExdataBinModel> ret = new ArrayList<SchExdataBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   SCH_EXDATA_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchExdataBinFromRs(rs));
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
     * <p>Select SCH_EXDATA_BIN
     * @param sceSid SCE_SID
     * @param binSid BIN_SID
     * @return SCH_EXDATA_BINModel
     * @throws SQLException SQL実行例外
     */
    public SchExdataBinModel select(int sceSid, long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchExdataBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCE_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA_BIN");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sceSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchExdataBinFromRs(rs);
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
     * <br>[機  能] バイナリSID Mapを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdExSids 拡張情報スケジュールSID
     * @return バイナリSID
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Set<Long>> getBinSidMap(Collection<Integer> scdExSids) throws SQLException {

        Connection con = null;
        Map<Integer, Set<Long>> ret = new HashMap<>();
        if (scdExSids == null || scdExSids.isEmpty()) {
            return ret;
        }


        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = scdExSids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   SCE_SID,");
        sb.append("   BIN_SID");
        sb.append(" from");
        sb.append("   SCH_EXDATA_BIN");
        sb.append(" where");

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
            sql.addSql(" SCE_SID in (");

            Iterator<Integer> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                sql.addSql("   ?");
                sql.addIntValue(exeItr.next());

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
                        int sceSid = rs.getInt("SCE_SID");
                        long binSid = rs.getLong("BIN_SID");
                        if (!ret.containsKey(sceSid)) {
                            ret.put(sceSid, new HashSet<>());
                        }
                        ret.get(sceSid).add(binSid);
                    }

                }

            }
            exeList.clear();
        }


        return ret;
    }
    /**
     * <br>[機  能] スケジュール拡張情報情報に関連する添付ファイルのファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行時例外
     */
    public long getTotalFileSize() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = getCon();
        long fileSize = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   SCH_EXDATA_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   SCH_EXDATA_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and ");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                fileSize = rs.getLong("FILE_SIZE");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return fileSize;
    }


    /**
     * <br>[機  能] 指定スケジュールに添付されているファイルを削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdExSidList スケジュール拡張情報SID
     * @throws SQLException SQL実行時例外
     */
    public void deleteTempFile(Collection<Integer> scdExSidList) throws SQLException {

        Set<Long> binSids = getBinSidMap(scdExSidList).values().stream()
                    .flatMap(bins -> bins.stream())
                    .collect(Collectors.toSet());

        for (List<Integer> sids : ListUtils.partition(new ArrayList<>(scdExSidList), 500)) {
            __delete(sids);
        }

        Connection con = null;
        con = getCon();

        SchBinDao schBinDao = new SchBinDao(con);
        CmnBinfDao cmnBinfDao = new CmnBinfDao(con);
        CmnBinfModel ronriDelMdl = new CmnBinfModel();
        ronriDelMdl.setBinUpdate(new UDate());
        ronriDelMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        binSids = notExistsBinSids(binSids);
        binSids = schBinDao.notExistsBinSids(binSids);

        for (List<Long> sids : ListUtils.partition(new ArrayList<>(binSids), 500)) {
            cmnBinfDao.updateJKbn(ronriDelMdl, sids);
        }
    }

    /**
     * <br>[機  能] 指定されたスケジュールの拡張添付情報を削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdExSidList スケジュール拡張SIDリスト
     * @throws scdExSidList SQL実行時例外
     * @return 削除件数
     */
    private int __delete(List<Integer> scdExSidList) throws SQLException {

        int count = 0;
        if (scdExSidList == null || scdExSidList.size() < 1) {
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
            sql.addSql("   SCH_EXDATA_BIN");
            sql.addSql(" where ");
            sql.addSql("   SCE_SID in (");
            for (int idx = 0; idx < scdExSidList.size(); idx++) {
                sql.addSql("     ?");
                sql.addIntValue(scdExSidList.get(idx));
                if (idx != scdExSidList.size() - 1) {
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
     * <p>Create SCH_EXDATA_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchExdataBinModel
     * @throws SQLException SQL実行例外
     */
    private SchExdataBinModel __getSchExdataBinFromRs(ResultSet rs) throws SQLException {
        SchExdataBinModel bean = new SchExdataBinModel();
        bean.setSceSid(rs.getInt("SCE_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
    /**
     *
     * <br>[機  能] SCH_BIN上に参照のないバイナリSIDを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param binSids 確認するバイナリSID
     * @return SCH_BIN上に参照のないバイナリSID
     * @throws SQLException SQL実行例外
     */
    public Set<Long> notExistsBinSids(Set<Long> binSids) throws SQLException {
        Connection con = null;
        Set<Long> exist = new HashSet<>();
        if (binSids == null || binSids.isEmpty()) {
            return binSids;
        }


        List<Long> exeList = new ArrayList<>();
        Iterator<Long> itr = binSids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   SCE_SID,");
        sb.append("   BIN_SID");
        sb.append(" from");
        sb.append("   SCH_EXDATA_BIN");
        sb.append(" where");

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
            sql.addSql(" BIN_SID in (");

            Iterator<Long> exeItr = exeList.iterator();
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
                        long binSid = rs.getLong("BIN_SID");
                        exist.add(binSid);
                    }

                }

            }
            exeList.clear();
        }
        return CollectionUtils.subtract(binSids, exist).stream().collect(Collectors.toSet());
    }
}
