package jp.groupsession.v2.rsv.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.rsv.model.RsvBinModel;
import jp.groupsession.v2.rsv.rsv090.model.Rsv090BinfModel;

/**
 * <p>RSV_BIN Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvBinDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvBinDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvBinDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvBinDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSV_BIN Data Bindding JavaBean
     * @param bean RSV_BIN Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_BIN(");
            sql.addSql("   RSD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   RSD_IMG_KBN,");
            sql.addSql("   RSD_DSP_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsdSid());
            sql.addLongValue(bean.getBinSid());
            sql.addIntValue(bean.getRsdImgKbn());
            sql.addIntValue(bean.getRsdDspKbn());
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
     * <p>施設添付情報の一括登録を行う
     * @param rsdSid 施設SID
     * @param binSidList バイナリSIDの一覧
     * @param imgKbn 画像区分
     * @param dspKbn 表示区分
     * @throws SQLException SQL実行例外
     */
    public void insertRsvBinData(int rsdSid,
                                   List<String> binSidList,
                                   int imgKbn,
                                   int dspKbn) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql(" RSV_BIN (");
            sql.addSql("   RSD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   RSD_IMG_KBN,");
            sql.addSql("   RSD_DSP_KBN");
            sql.addSql(" )");
            sql.addSql(" values (");
            sql.addSql("   " + rsdSid + ",");
            sql.addSql("   ?,");
            sql.addSql("   " + imgKbn + ",");
            sql.addSql("   " + dspKbn);
            sql.addSql(" )");

            String logString = sql.toLogString();
            pstmt = con.prepareStatement(sql.toSqlString());

            for (String binSid : binSidList) {
                log__.info(StringUtil.substitute(logString, "?", binSid));
                pstmt.setLong(1, Long.parseLong(binSid));
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Update RSV_BIN Data Bindding JavaBean
     * @param bean RSV_BIN Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RsvBinModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_BIN");
            sql.addSql(" set ");
            sql.addSql("   RSD_IMG_KBN=?,");
            sql.addSql("   RSD_DSP_KBN=?");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsdImgKbn());
            sql.addIntValue(bean.getRsdDspKbn());
            //where
            sql.addIntValue(bean.getRsdSid());
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
     * <br>[機  能] 指定された施設のバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param rsdSid 施設SID
     * @param imgKbn 画像区分
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinfForSisetu(int rsdSid, int imgKbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  update");
            sql.addSql("    CMN_BINF");
            sql.addSql("  set");
            sql.addSql("    BIN_JKBN = ?");
            sql.addSql("  where");
            sql.addSql("    BIN_SID in (");
            sql.addSql("      select BIN_SID from RSV_BIN");
            sql.addSql("      where RSD_SID = ?");
            sql.addSql("      and RSD_IMG_KBN = ?");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(rsdSid);
            sql.addIntValue(imgKbn);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
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
     * <p>Select RSV_BIN All Data
     * @return List in RSV_BINModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvBinModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvBinModel> ret = new ArrayList<RsvBinModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RSD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   RSD_IMG_KBN,");
            sql.addSql("   RSD_DSP_KBN");
            sql.addSql(" from ");
            sql.addSql("   RSV_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvBinFromRs(rs));
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
     * <p>Select RSV_BIN
     * @param rsdSid RSD_SID
     * @param binSid BIN_SID
     * @return RSV_BINModel
     * @throws SQLException SQL実行例外
     */
    public RsvBinModel select(int rsdSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvBinModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSD_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   RSD_IMG_KBN,");
            sql.addSql("   RSD_DSP_KBN");
            sql.addSql(" from");
            sql.addSql("   RSV_BIN");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsdSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvBinFromRs(rs);
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
     * <p>施設SIDとバイナリSIDの組み合わせが存在するかチェックする
     * @param rsdSid 施設SID
     * @param binSid バイナリSID
     * @return true:存在する  false:存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckRsvImage(int rsdSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int cnt = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   RSV_BIN");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsdSid);
            sql.addLongValue(binSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                cnt = rs.getInt("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return cnt > 0;
    }

    /**
     * <br>[機  能]施設の添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSid 施設SID
     * @param imgKbn 画像区分
     * @param weekDayDspHnt 週間・日間に表示する画像を含めて全表示するか false:非表示 true:全表示
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List<Rsv090BinfModel> getWriteTmpFileListData(int rsdSid,
                                                          int imgKbn,
                                                          boolean weekDayDspHnt)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Rsv090BinfModel> ret = new ArrayList<Rsv090BinfModel>();
        CommonBiz cmnBiz = new CommonBiz();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE,");
            sql.addSql("    RSV_BIN.RSD_DSP_KBN as RSD_DSP_KBN");
            sql.addSql("  from");
            sql.addSql("    RSV_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    RSV_BIN.RSD_SID = ?");
            sql.addSql("  and");
            sql.addSql("    RSV_BIN.RSD_IMG_KBN = ?");
            sql.addSql("  and");
            if (!weekDayDspHnt) {
                sql.addSql("    RSV_BIN.RSD_DSP_KBN = 0");
            }
            sql.addSql("    RSV_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = 0");
//            sql.addSql("  order by");
//            sql.addSql("   CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(rsdSid);
            sql.addIntValue(imgKbn);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            Rsv090BinfModel cbMdl = null;

            while (rs.next()) {
                cbMdl = new Rsv090BinfModel();
                cbMdl.setBinSid(rs.getLong("BIN_SID"));
                cbMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                cbMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                cbMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                cbMdl.setImgDspKbn(rs.getInt("RSD_DSP_KBN"));

                long lngSize = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(lngSize);
                cbMdl.setBinFileSizeDsp(strSize);
                ret.add(cbMdl);
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
     * <br>[機  能]週間・日間に表示する施設の添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSid 施設SID
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public Rsv090BinfModel getWriteTmpFile(int rsdSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Rsv090BinfModel ret = null;
        CommonBiz cmnBiz = new CommonBiz();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE,");
            sql.addSql("    RSV_BIN.RSD_DSP_KBN as RSD_DSP_KBN");
            sql.addSql("  from");
            sql.addSql("    RSV_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    RSV_BIN.RSD_SID = ?");
            sql.addSql("  and");
            sql.addSql("    RSV_BIN.RSD_IMG_KBN = 0");
            sql.addSql("  and");
            sql.addSql("    RSV_BIN.RSD_DSP_KBN = 1");
            sql.addSql("  and");
            sql.addSql("    RSV_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = 0");
            sql.addSql("  order by");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(rsdSid);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new Rsv090BinfModel();
                ret.setBinSid(rs.getLong("BIN_SID"));
                ret.setBinFileName(rs.getString("BIN_FILE_NAME"));
                ret.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                ret.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                ret.setImgDspKbn(rs.getInt("RSD_DSP_KBN"));

                long lngSize = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(lngSize);
                ret.setBinFileSizeDsp(strSize);
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
     * <br>[機  能] 指定された施設の添付ファイル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSid 施設SID
     * @param imgKbn 施設区分
     * @return 添付ファイル情報一覧
     * @throws SQLException SQL実行例外
     */
    public List <Rsv090BinfModel> getWriteTmpFileList(int rsdSid, int imgKbn)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Rsv090BinfModel> ret = new ArrayList<Rsv090BinfModel>();
        CommonBiz cmnBiz = new CommonBiz();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE,");
            sql.addSql("    RSV_BIN.RSD_DSP_KBN as RSD_DSP_KBN");
            sql.addSql("  from");
            sql.addSql("    RSV_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    RSV_BIN.RSD_SID = ?");
            sql.addSql("  and");
            sql.addSql("    RSV_BIN.RSD_IMG_KBN = ?");
            sql.addSql("  and");
            sql.addSql("    RSV_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = 0");
            sql.addSql("  order by");
            sql.addSql("   CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(rsdSid);
            sql.addIntValue(imgKbn);

            log__.info(sql.toLogString());

            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Rsv090BinfModel binMdl = new Rsv090BinfModel();
                binMdl.setBinSid(rs.getLong("BIN_SID"));
                binMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                binMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                binMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                binMdl.setImgDspKbn(rs.getInt("RSD_DSP_KBN"));

                long lngSize = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(lngSize);
                binMdl.setBinFileSizeDsp(strSize);
                ret.add(binMdl);
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
     * <br>[機  能] 指定した施設情報の添付ファイルサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsdSidList 施設SID
     * @return ファイルサイズ合計
     * @throws SQLException SQL実行例外
     */
    public long getTotalFileSize(List<Integer> rsdSidList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        long fileSize = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(CMN_BINF.BIN_FILE_SIZE) as FILE_SIZE");
            sql.addSql(" from");
            sql.addSql("   RSV_BIN,");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   RSV_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   RSV_BIN.RSD_SID in (");

            for (int idx = 0; idx < rsdSidList.size(); idx++) {
                if (idx == 0) {
                    sql.addSql("     ?");
                } else {
                    sql.addSql("     ,?");
                }
                sql.addIntValue(rsdSidList.get(idx));
            }

            sql.addSql("  )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>Delete RSV_BIN
     * @param rsdSid RSD_SID
     * @param binSid BIN_SID
     * @return 件数
     * @throws SQLException SQL実行例外
     */
    public int delete(int rsdSid, Long binSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_BIN");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");
            sql.addSql(" and");
            sql.addSql("   BIN_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsdSid);
            sql.addLongValue(binSid);

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
     * <p>指定した施設添付情報を全て削除する
     * @param rsdSid 施設SID
     * @param imgKbn 画像区分
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteSisetuBin(int rsdSid, int imgKbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_BIN");
            sql.addSql(" where ");
            sql.addSql("   RSD_SID=?");
            sql.addSql(" and ");
            sql.addSql("   RSD_IMG_KBN=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rsdSid);
            sql.addIntValue(imgKbn);

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
     * <p>Create RSV_BIN Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvBinModel
     * @throws SQLException SQL実行例外
     */
    private RsvBinModel __getRsvBinFromRs(ResultSet rs) throws SQLException {
        RsvBinModel bean = new RsvBinModel();
        bean.setRsdSid(rs.getInt("RSD_SID"));
        bean.setBinSid(rs.getLong("BIN_SID"));
        bean.setRsdImgKbn(rs.getInt("RSD_IMG_KBN"));
        bean.setRsdDspKbn(rs.getInt("RSD_DSP_KBN"));
        return bean;
    }
}
