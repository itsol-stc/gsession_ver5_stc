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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sch.model.SchBinModel;

/**
 * <p>SCH_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchBinDao.class);

    /**
     * <p>Default Constructor
     */
    public SchBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public SchBinDao(Connection con) {
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
            sql.addSql("drop table SCH_BIN");

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
            sql.addSql(" create table SCH_BIN (");
            sql.addSql("   SCD_SID integer not null,");
            sql.addSql("   BIN_SID bigint not null,");
            sql.addSql("   primary key (SCD_SID,BIN_SID)");
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
     * <p>Insert SCH_BIN Data Bindding JavaBean
     * @param bean SCH_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(SchBinModel bean) throws SQLException {

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
     * <p>Insert SCH_BIN Data Bindding JavaBean
     * @param beanList SCH_BIN DataList
     * @throws SQLException SQL実行例外
     */
    public void insert(List<SchBinModel> beanList) throws SQLException {

        if (beanList == null || beanList.size() <= 0) {
            return;
        }
        List<SchBinModel> exeList = new ArrayList<>();
        Iterator<SchBinModel> itr = beanList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" insert ");
        sb.append(" into ");
        sb.append(" SCH_BIN(");
        sb.append("   SCD_SID,");
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

            Iterator<SchBinModel> exeItr = exeList.iterator();
            while (exeItr.hasNext()) {
                SchBinModel bean = exeItr.next();
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getScdSid());
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
     * <p>Update SCH_BIN Data Bindding JavaBean
     * @param bean SCH_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(SchBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   SCH_BIN");
            sql.addSql(" set ");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            //where
            sql.addIntValue(bean.getScdSid());
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
     * <p>Select SCH_BIN All Data
     * @return List in SCH_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<SchBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<SchBinModel> ret = new ArrayList<SchBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SCD_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from ");
            sql.addSql("   SCH_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getSchBinFromRs(rs));
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
     * <p>Select SCH_BIN
     * @param scdSid SCD_SID
     * @param binSid BIN_SID
     * @return SCH_BINModel
     * @throws SQLException SQL実行例外
     */
    public SchBinModel select(int scdSid, long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        SchBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SCD_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_BIN");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getSchBinFromRs(rs);
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
     * <br>[機  能] 指定したスケジュールの添付ファイル情報を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @return 添付ファイル情報リスト
     * @throws SQLException SQL実行時例外
     */
    public List<CmnBinfModel> getBinInfo(int scdSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("   CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("   CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE,");
            sql.addSql("   CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH");
            sql.addSql(" from");
            sql.addSql("   SCH_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   SCH_BIN.SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN=0");
            sql.addSql(" and");
            sql.addSql("   SCH_BIN.BIN_SID=CMN_BINF.BIN_SID");
            sql.addSql(" order by");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            CmnBinfModel bean = null;
            CommonBiz cmnBiz = new CommonBiz();
            while (rs.next()) {
                bean = new CmnBinfModel();
                bean.setBinSid(rs.getLong("BIN_SID"));
                bean.setBinFileExtension("BIN_FILE_EXTENSION");
                bean.setBinFileName(rs.getString("BIN_FILE_NAME"));
                bean.setBinFileSize(rs.getLong("BIN_FILE_SIZE"));
                bean.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                bean.setBinFileSizeDsp(cmnBiz.getByteSizeString(bean.getBinFileSize()));
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
     * <br>[機  能] バイナリSID Mapを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSids 拡張情報スケジュールSID
     * @return バイナリSID
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Set<Long>> getBinSidMap(Collection<Integer> scdSids) throws SQLException {

        Connection con = null;
        Map<Integer, Set<Long>> ret = new HashMap<>();
        if (scdSids == null || scdSids.isEmpty()) {
            return ret;
        }


        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = scdSids.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   SCD_SID,");
        sb.append("   BIN_SID");
        sb.append(" from");
        sb.append("   SCH_BIN");
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
            sql.addSql(" SCD_SID in (");

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
                        int sceSid = rs.getInt("SCD_SID");
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
     * <br>[機  能] 指定したスケジュールの添付ファイル情報を取得します
     * <br>[解  説] スケジュールSIDと添付ファイル情報(List)のMappingを取得します
     * <br>[備  考]
     * @param scdSidList スケジュールSID
     * @return スケジュールSIDと添付ファイル情報(List)のMapping
     * @throws SQLException SQL実行時例外
     */
    public Map<Integer, List<CmnBinfModel>> getBinInfoMap(List<Integer> scdSidList)
    throws SQLException {

        Map<Integer, List<CmnBinfModel>> binMap = new HashMap<Integer, List<CmnBinfModel>>();
        if (scdSidList == null || scdSidList.isEmpty()) {
            return binMap;
        }

        List<Integer> exeList = new ArrayList<>();
        Iterator<Integer> itr = scdSidList.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        sb.append("   SCH_BIN.SCD_SID as SCD_SID,");
        sb.append("   CMN_BINF.BIN_SID as BIN_SID,");
        sb.append("   CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
        sb.append("   CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
        sb.append("   CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE,");
        sb.append("   CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH");
        sb.append(" from");
        sb.append("   SCH_BIN,");
        sb.append("   CMN_BINF");
        sb.append(" where ");


        Connection con = null;
        con = getCon();

        CommonBiz cmnBiz = new CommonBiz();
        while (itr.hasNext()) {
            exeList.add(itr.next());
            if (exeList.size() < 1000
                    && itr.hasNext()) {
                continue;
            }

            //500件分インサート
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(sb.toString());

            Iterator<Integer> exeItr = exeList.iterator();
            sql.addSql("   SCH_BIN.SCD_SID in (");
            while (exeItr.hasNext()) {
                Integer sid = exeItr.next();
                sql.addSql("     ?");
                sql.addIntValue(sid);

                if (exeItr.hasNext()) {
                    sql.addSql(",");
                }
            }
            sql.addSql("   )");

            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SCH_BIN.BIN_SID=CMN_BINF.BIN_SID");
            sql.addSql(" order by");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME");
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            try (PreparedStatement pstmt = con.prepareStatement(sql.toSqlString());) {
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());

                try (ResultSet rs = pstmt.executeQuery();) {
                while (rs.next()) {
                    CmnBinfModel bean = new CmnBinfModel();
                    int scdSid = rs.getInt("SCD_SID");
                    bean.setBinSid(rs.getLong("BIN_SID"));
                    bean.setBinFileExtension("BIN_FILE_EXTENSION");
                    bean.setBinFileName(rs.getString("BIN_FILE_NAME"));
                    bean.setBinFileSize(rs.getLong("BIN_FILE_SIZE"));
                    bean.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                    bean.setBinFileSizeDsp(cmnBiz.getByteSizeString(bean.getBinFileSize()));

                    if (!binMap.containsKey(scdSid)) {
                        binMap.put(scdSid, new ArrayList<CmnBinfModel>());
                    }

                    binMap.get(scdSid).add(bean);
                }

                }
            }
            exeList.clear();
        }
        return binMap;
    }

    /**
     * <br>[機  能] 指定した添付ファイルの件数を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @param binSid 添付ファイルSID
     * @return 添付ファイルの件数
     * @throws SQLException SQL実行時例外
     */
    public int getFileCount(int scdSid, long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(BIN_SID) CNT");
            sql.addSql(" from");
            sql.addSql("   SCH_BIN");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(scdSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
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
     * <br>[機  能] バイナリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSid スケジュールSID
     * @return バイナリSID
     * @throws SQLException SQL実行例外
     */
    public String[] getBinSids(int scdSid) throws SQLException {

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
            sql.addSql("   SCD_SID,");
            sql.addSql("   BIN_SID");
            sql.addSql(" from");
            sql.addSql("   SCH_BIN");
            sql.addSql(" where");
            sql.addSql("   SCD_SID = ?");
            sql.addIntValue(scdSid);

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
     * <br>[機  能] スケジュール情報に関連する添付ファイルのファイルサイズ合計を取得する
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
            sql.addSql("   SCH_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where ");
            sql.addSql("   SCH_BIN.BIN_SID = CMN_BINF.BIN_SID");
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
     * <br>[機  能] 指定されたスケジュールの添付情報を削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSIDリスト
     * @throws SQLException SQL実行時例外
     * @return 削除件数
     */
    private int __delete(List<Integer> scdSidList) throws SQLException {

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
            sql.addSql("   SCH_BIN");
            sql.addSql(" where ");
            sql.addSql("   SCD_SID in (");
            for (int idx = 0; idx < scdSidList.size(); idx++) {
                sql.addSql("     ?");
                sql.addIntValue(scdSidList.get(idx));
                if (idx != scdSidList.size() - 1) {
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
     * <br>[機  能] 指定スケジュールに添付されているファイルを削除します
     * <br>[解  説] CMN_BINFの論理削除は行わない。
     * <br>[備  考]
     * @param scdSidList スケジュールSIDリスト
     * @throws SQLException SQL実行時例外
     */
    public void delete(List<Integer> scdSidList) throws SQLException {

        if (scdSidList == null || scdSidList.size() < 1) {
            return;
        }
        for (List<Integer> sids : ListUtils.partition(new ArrayList<>(scdSidList), 500)) {
            __delete(sids);
        }
    }

    /**
     * <br>[機  能] 指定スケジュールに添付されているファイルを削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param scdSidList スケジュールSIDリスト
     * @throws SQLException SQL実行時例外
     */
    public void deleteTempFile(Collection<Integer> scdSidList) throws SQLException {

        if (scdSidList == null || scdSidList.size() < 1) {
            return;
        }
        Set<Long> binSids = getBinSidMap(scdSidList).values().stream()
                    .flatMap(bins -> bins.stream())
                    .collect(Collectors.toSet());

        for (List<Integer> sids : ListUtils.partition(new ArrayList<>(scdSidList), 500)) {
            __delete(sids);
        }

        Connection con = null;
        con = getCon();

        SchExdataBinDao exBinDao = new SchExdataBinDao(con);
        CmnBinfDao cmnBinfDao = new CmnBinfDao(con);
        CmnBinfModel ronriDelMdl = new CmnBinfModel();
        ronriDelMdl.setBinUpdate(new UDate());
        ronriDelMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        binSids = notExistsBinSids(binSids);
        binSids = exBinDao.notExistsBinSids(binSids);

        for (List<Long> sids : ListUtils.partition(new ArrayList<>(binSids), 500)) {
            cmnBinfDao.updateJKbn(ronriDelMdl, sids);
        }


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
        sb.append("   SCD_SID,");
        sb.append("   BIN_SID");
        sb.append(" from");
        sb.append("   SCH_BIN");
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

    /**
     * <p>Create SCH_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchBinModel
     * @throws SQLException SQL実行例外
     */
    private SchBinModel __getSchBinFromRs(ResultSet rs) throws SQLException {
        SchBinModel bean = new SchBinModel();
        bean.setScdSid(rs.getInt("SCD_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        return bean;
    }
}
