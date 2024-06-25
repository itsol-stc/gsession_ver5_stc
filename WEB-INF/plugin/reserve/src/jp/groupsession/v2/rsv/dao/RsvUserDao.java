package jp.groupsession.v2.rsv.dao;

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
import jp.groupsession.v2.rsv.model.RsvUserModel;

/**
 * <p>RSV_USER Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RsvUserDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvUserDao.class);

    /**
     * <p>Default Constructor
     */
    public RsvUserDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RsvUserDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert RSV_USER Data Bindding JavaBean
     * @param bean RSV_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RsvUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_USER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSU_DIT_1,");
            sql.addSql("   RSU_DIT_2,");
            sql.addSql("   RSU_DIT_3,");
            sql.addSql("   RSU_DIT_4,");
            sql.addSql("   RSU_DIT_5,");
            sql.addSql("   RSU_DTM_FR,");
            sql.addSql("   RSU_DTM_TO,");
            sql.addSql("   RSU_MAX_DSP,");
            sql.addSql("   RSU_RELOAD,");
            sql.addSql("   RSU_INI_FR_DATE,");
            sql.addSql("   RSU_INI_TO_DATE,");
            sql.addSql("   RSU_INI_EDIT,");
            sql.addSql("   RSU_INI_PUBLIC,");
            sql.addSql("   RSU_IMG_DSP,");
            sql.addSql("   RSU_INI_DSP,");
            sql.addSql("   RSU_AUID,");
            sql.addSql("   RSU_ADATE,");
            sql.addSql("   RSU_EUID,");
            sql.addSql("   RSU_EDATE,");
            sql.addSql("   RSU_SMAIL_KBN");
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
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRsuDit1());
            sql.addIntValue(bean.getRsuDit2());
            sql.addIntValue(bean.getRsuDit3());
            sql.addIntValue(bean.getRsuDit4());
            sql.addIntValue(bean.getRsuDit5());
            sql.addIntValue(bean.getRsuDtmFr());
            sql.addIntValue(bean.getRsuDtmTo());
            sql.addIntValue(bean.getRsuMaxDsp());
            sql.addIntValue(bean.getRsuReload());
            sql.addDateValue(bean.getRsuIniFrDate());
            sql.addDateValue(bean.getRsuIniToDate());
            sql.addIntValue(bean.getRsuIniEdit());
            sql.addIntValue(bean.getRsuIniPublic());
            sql.addIntValue(bean.getRsuImgDsp());
            sql.addIntValue(bean.getRsuIniDsp());
            sql.addIntValue(bean.getRsuAuid());
            sql.addDateValue(bean.getRsuAdate());
            sql.addIntValue(bean.getRsuEuid());
            sql.addDateValue(bean.getRsuEdate());
            sql.addIntValue(bean.getRsuSmailKbn());
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
     * <br>[機  能] 施設予約 個人設定情報を更新します
     * <br>[解  説]
     * <br>[備  考] 表示件数更新画面より
     *
     * @param bean RsvUserModel
     * @throws SQLException SQL実行時例外
     */
    public void insertMaxDsp(RsvUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RSV_USER(");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSU_DIT_1,");
            sql.addSql("   RSU_DIT_2,");
            sql.addSql("   RSU_DTM_FR,");
            sql.addSql("   RSU_DTM_TO,");
            sql.addSql("   RSU_MAX_DSP,");
            sql.addSql("   RSU_RELOAD,");
            sql.addSql("   RSU_INI_FR_DATE,");
            sql.addSql("   RSU_INI_TO_DATE,");
            sql.addSql("   RSU_INI_EDIT,");
            sql.addSql("   RSU_IMG_DSP,");
            sql.addSql("   RSU_INI_DSP,");
            sql.addSql("   RSU_AUID,");
            sql.addSql("   RSU_ADATE,");
            sql.addSql("   RSU_EUID,");
            sql.addSql("   RSU_EDATE");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRsuDit1());
            sql.addIntValue(bean.getRsuDit2());
            sql.addIntValue(bean.getRsuDtmFr());
            sql.addIntValue(bean.getRsuDtmTo());
            sql.addIntValue(bean.getRsuMaxDsp());
            sql.addIntValue(bean.getRsuReload());
            sql.addDateValue(bean.getRsuIniFrDate());
            sql.addDateValue(bean.getRsuIniToDate());
            sql.addIntValue(bean.getRsuIniEdit());
            sql.addIntValue(bean.getRsuImgDsp());
            sql.addIntValue(bean.getRsuIniDsp());
            sql.addIntValue(bean.getRsuAuid());
            sql.addDateValue(bean.getRsuAdate());
            sql.addIntValue(bean.getRsuEuid());
            sql.addDateValue(bean.getRsuEdate());
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
     * <br>[機  能] 施設予約 個人設定情報を更新します
     * @param bean RsvUserModel
     * @return int 更新件数
     * @throws SQLException 例外
     */
    public int updatePre(RsvUserModel bean, boolean dateUpdFlg) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_USER");
            sql.addSql(" set ");
            sql.addSql("   RSG_SID=?,");
            sql.addSql("   RSU_DIT_1=?,");
            sql.addSql("   RSU_DIT_2=?,");
            sql.addSql("   RSU_DIT_3=?,");
            sql.addSql("   RSU_DIT_4=?,");
            sql.addSql("   RSU_DIT_5=?,");
            sql.addSql("   RSU_RELOAD = ?,");
            sql.addSql("   RSU_IMG_DSP = ?,");
            sql.addSql("   RSU_INI_DSP = ?,");
            if (dateUpdFlg) {
                sql.addSql("   RSU_DTM_FR=?,");
                sql.addSql("   RSU_DTM_TO=?,");
            }
            sql.addSql("   RSU_MAX_DSP=?,");
            sql.addSql("   RSU_EUID=?,");
            sql.addSql("   RSU_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsgSid());
            sql.addIntValue(bean.getRsuDit1());
            sql.addIntValue(bean.getRsuDit2());
            sql.addIntValue(bean.getRsuDit3());
            sql.addIntValue(bean.getRsuDit4());
            sql.addIntValue(bean.getRsuDit5());
            sql.addIntValue(bean.getRsuReload());
            sql.addIntValue(bean.getRsuImgDsp());
            sql.addIntValue(bean.getRsuIniDsp());
            if (dateUpdFlg) {
                sql.addIntValue(bean.getRsuDtmFr());
                sql.addIntValue(bean.getRsuDtmTo());
            }
            sql.addIntValue(bean.getRsuMaxDsp());
            sql.addIntValue(bean.getRsuEuid());
            sql.addDateValue(bean.getRsuEdate());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * <br>[機  能] 施設予約 個人設定情報を更新します
     * <br>[解  説] 表示件数を更新
     * <br>[備  考]
     *
     * @param bean RsvUserModel
     * @return count 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateDspMax(RsvUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_USER");
            sql.addSql(" set ");
            sql.addSql("   RSU_MAX_DSP = ?,");
            sql.addSql("   RSU_EUID = ?,");
            sql.addSql("   RSU_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsuMaxDsp());
            sql.addIntValue(bean.getRsuEuid());
            sql.addDateValue(bean.getRsuEdate());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * <br>[機  能] 施設予約 個人設定[ショートメール通知]を更新します
     * <br>[解  説] 表示件数を更新
     * <br>[備  考]
     *
     * @param bean RsvUserModel
     * @return count 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateSmailKbn(RsvUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_USER");
            sql.addSql(" set ");
            sql.addSql("   RSU_SMAIL_KBN = ?,");
            sql.addSql("   RSU_EUID = ?,");
            sql.addSql("   RSU_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRsuSmailKbn());
            sql.addIntValue(bean.getRsuEuid());
            sql.addDateValue(bean.getRsuEdate());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * <br>[機  能] 施設予約 個人設定情報を更新します
     * <br>[解  説] 初期値設定を更新
     * <br>[備  考]
     *
     * @param bean RsvUserModel
     * @return count 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int updateInitData(RsvUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_USER");
            sql.addSql(" set ");
            sql.addSql("   RSU_INI_FR_DATE = ?,");
            sql.addSql("   RSU_INI_TO_DATE = ?,");
            sql.addSql("   RSU_INI_EDIT = ?,");
            sql.addSql("   RSU_INI_PUBLIC = ?,");
            sql.addSql("   RSU_EUID = ?,");
            sql.addSql("   RSU_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addDateValue(bean.getRsuIniFrDate());
            sql.addDateValue(bean.getRsuIniToDate());
            sql.addIntValue(bean.getRsuIniEdit());
            sql.addIntValue(bean.getRsuIniPublic());
            sql.addIntValue(bean.getRsuEuid());
            sql.addDateValue(bean.getRsuEdate());
            //where
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Select RSV_USER All Data
     * @return List in RSV_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<RsvUserModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RsvUserModel> ret = new ArrayList<RsvUserModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSU_DIT_1,");
            sql.addSql("   RSU_DIT_2,");
            sql.addSql("   RSU_DIT_3,");
            sql.addSql("   RSU_DIT_4,");
            sql.addSql("   RSU_DIT_5,");
            sql.addSql("   RSU_DTM_FR,");
            sql.addSql("   RSU_DTM_TO,");
            sql.addSql("   RSU_MAX_DSP,");
            sql.addSql("   RSU_RELOAD,");
            sql.addSql("   RSU_INI_FR_DATE,");
            sql.addSql("   RSU_INI_TO_DATE,");
            sql.addSql("   RSU_INI_EDIT,");
            sql.addSql("   RSU_INI_PUBLIC,");
            sql.addSql("   RSU_IMG_DSP,");
            sql.addSql("   RSU_INI_DSP,");
            sql.addSql("   RSU_AUID,");
            sql.addSql("   RSU_ADATE,");
            sql.addSql("   RSU_EUID,");
            sql.addSql("   RSU_EDATE,");
            sql.addSql("   RSU_SMAIL_KBN");
            sql.addSql(" from ");
            sql.addSql("   RSV_USER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRsvUserFromRs(rs));
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
     * <p>Select RSV_USER
     * @param bean RSV_USER Model
     * @return RSV_USERModel
     * @throws SQLException SQL実行例外
     */
    public RsvUserModel select(RsvUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSU_DIT_1,");
            sql.addSql("   RSU_DIT_2,");
            sql.addSql("   RSU_DIT_3,");
            sql.addSql("   RSU_DIT_4,");
            sql.addSql("   RSU_DIT_5,");
            sql.addSql("   RSU_DTM_FR,");
            sql.addSql("   RSU_DTM_TO,");
            sql.addSql("   RSU_MAX_DSP,");
            sql.addSql("   RSU_RELOAD,");
            sql.addSql("   RSU_INI_FR_DATE,");
            sql.addSql("   RSU_INI_TO_DATE,");
            sql.addSql("   RSU_INI_EDIT,");
            sql.addSql("   RSU_INI_PUBLIC,");
            sql.addSql("   RSU_IMG_DSP,");
            sql.addSql("   RSU_INI_DSP,");
            sql.addSql("   RSU_AUID,");
            sql.addSql("   RSU_ADATE,");
            sql.addSql("   RSU_EUID,");
            sql.addSql("   RSU_EDATE,");
            sql.addSql("   RSU_SMAIL_KBN");
            sql.addSql(" from");
            sql.addSql("   RSV_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvUserFromRs(rs);
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
     * <br>[機  能] 施設予約個人設定を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return ret RsvUserModel
     * @throws SQLException SQL実行例外
     */
    public RsvUserModel select(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RsvUserModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   USR_SID,");
            sql.addSql("   RSG_SID,");
            sql.addSql("   RSU_DIT_1,");
            sql.addSql("   RSU_DIT_2,");
            sql.addSql("   RSU_DIT_3,");
            sql.addSql("   RSU_DIT_4,");
            sql.addSql("   RSU_DIT_5,");
            sql.addSql("   RSU_DTM_FR,");
            sql.addSql("   RSU_DTM_TO,");
            sql.addSql("   RSU_MAX_DSP,");
            sql.addSql("   RSU_RELOAD,");
            sql.addSql("   RSU_INI_FR_DATE,");
            sql.addSql("   RSU_INI_TO_DATE,");
            sql.addSql("   RSU_INI_EDIT,");
            sql.addSql("   RSU_INI_PUBLIC,");
            sql.addSql("   RSU_IMG_DSP,");
            sql.addSql("   RSU_INI_DSP,");
            sql.addSql("   RSU_AUID,");
            sql.addSql("   RSU_ADATE,");
            sql.addSql("   RSU_EUID,");
            sql.addSql("   RSU_EDATE,");
            sql.addSql("   RSU_SMAIL_KBN");
            sql.addSql(" from");
            sql.addSql("   RSV_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRsvUserFromRs(rs);
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
     * <p>Delete RSV_USER
     * @param bean RSV_USER Model
     * @return count delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(RsvUserModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RSV_USER");
            sql.addSql(" where ");
            sql.addSql("   USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getUsrSid());

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
     * <p>Create RSV_USER Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RsvUserModel
     * @throws SQLException SQL実行例外
     */
    private RsvUserModel __getRsvUserFromRs(ResultSet rs) throws SQLException {
        RsvUserModel bean = new RsvUserModel();
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRsgSid(rs.getInt("RSG_SID"));
        bean.setRsuDit1(rs.getInt("RSU_DIT_1"));
        bean.setRsuDit2(rs.getInt("RSU_DIT_2"));
        bean.setRsuDit3(rs.getInt("RSU_DIT_3"));
        bean.setRsuDit4(rs.getInt("RSU_DIT_4"));
        bean.setRsuDit5(rs.getInt("RSU_DIT_5"));
        bean.setRsuDtmFr(rs.getInt("RSU_DTM_FR"));
        bean.setRsuDtmTo(rs.getInt("RSU_DTM_TO"));
        bean.setRsuMaxDsp(rs.getInt("RSU_MAX_DSP"));
        bean.setRsuReload(rs.getInt("RSU_RELOAD"));
        bean.setRsuIniFrDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSU_INI_FR_DATE")));
        bean.setRsuIniToDate(UDate.getInstanceTimestamp(rs.getTimestamp("RSU_INI_TO_DATE")));
        bean.setRsuIniEdit(rs.getInt("RSU_INI_EDIT"));
        bean.setRsuIniPublic(rs.getInt("RSU_INI_PUBLIC"));
        bean.setRsuImgDsp(rs.getInt("RSU_IMG_DSP"));
        bean.setRsuIniDsp(rs.getInt("RSU_INI_DSP"));
        bean.setRsuAuid(rs.getInt("RSU_AUID"));
        bean.setRsuAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSU_ADATE")));
        bean.setRsuEuid(rs.getInt("RSU_EUID"));
        bean.setRsuEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RSU_EDATE")));
        bean.setRsuSmailKbn(rs.getInt("RSU_SMAIL_KBN"));
        return bean;
    }
}