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
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <p>CIR_ACCOUNT Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CirAccountDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirAccountDao.class);

    /**
     * <p>Default Constructor
     */
    public CirAccountDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CirAccountDao(Connection con) {
        super(con);
    }

    /**
     * <p>Insert CIR_ACCOUNT Data Bindding JavaBean
     * @param bean CIR_ACCOUNT Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(CirAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACCOUNT(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAC_NAME,");
            sql.addSql("   CAC_BIKO,");
            sql.addSql("   CAC_JKBN,");
            sql.addSql("   CAC_THEME,");
            sql.addSql("   CAC_SML_NTF,");
            sql.addSql("   CAC_MEMO_KBN,");
            sql.addSql("   CAC_MEMO_DAY,");
            sql.addSql("   CAC_KOU_KBN,");
            sql.addSql("   CAC_INIT_KBN,");
            sql.addSql("   CAC_SML_MEMO,");
            sql.addSql("   CAC_SML_EDIT");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getCacType());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getCacName());
            sql.addStrValue(bean.getCacBiko());
            sql.addIntValue(bean.getCacJkbn());
            sql.addIntValue(bean.getCacTheme());
            sql.addIntValue(bean.getCacSmlNtf());
            sql.addIntValue(bean.getCacMemoKbn());
            sql.addIntValue(bean.getCacMemoDay());
            sql.addIntValue(bean.getCacKouKbn());
            sql.addIntValue(bean.getCacInitKbn());
            sql.addIntValue(bean.getCacSmlMemo());
            sql.addIntValue(bean.getCacSmlEdit());
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
     * <p>Update CIR_ACCOUNT Data Bindding JavaBean
     * @param bean CIR_ACCOUNT Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(CirAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   CAC_TYPE=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   CAC_NAME=?,");
            sql.addSql("   CAC_BIKO=?,");
            sql.addSql("   CAC_THEME=?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacType());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getCacName());
            sql.addStrValue(bean.getCacBiko());
            sql.addIntValue(bean.getCacTheme());

            //where
            sql.addIntValue(bean.getCacSid());

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
     * <p>Select CIR_ACCOUNT All Data
     * @return List in CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirAccountModel> ret = new ArrayList<CirAccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAC_NAME,");
            sql.addSql("   CAC_BIKO,");
            sql.addSql("   CAC_JKBN,");
            sql.addSql("   CAC_THEME,");
            sql.addSql("   CAC_SML_NTF,");
            sql.addSql("   CAC_MEMO_KBN,");
            sql.addSql("   CAC_MEMO_DAY,");
            sql.addSql("   CAC_KOU_KBN,");
            sql.addSql("   CAC_INIT_KBN,");
            sql.addSql("   CAC_SML_MEMO,");
            sql.addSql("   CAC_SML_EDIT");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CirAccountModel bean = new CirAccountModel();
                bean.setCacSid(rs.getInt("CAC_SID"));
                bean.setCacType(rs.getInt("CAC_TYPE"));
                bean.setUsrSid(rs.getInt("USR_SID"));
                bean.setCacName(rs.getString("CAC_NAME"));
                bean.setCacBiko(rs.getString("CAC_BIKO"));
                bean.setCacJkbn(rs.getInt("CAC_JKBN"));
                bean.setCacTheme(rs.getInt("CAC_THEME"));
                bean.setCacSmlNtf(rs.getInt("CAC_SML_NTF"));
                bean.setCacMemoKbn(rs.getInt("CAC_MEMO_KBN"));
                bean.setCacMemoDay(rs.getInt("CAC_MEMO_DAY"));
                bean.setCacKouKbn(rs.getInt("CAC_KOU_KBN"));
                bean.setCacInitKbn(rs.getInt("CAC_INIT_KBN"));
                bean.setCacSmlMemo(rs.getInt("CAC_SML_MEMO"));
                bean.setCacSmlEdit(rs.getInt("CAC_SML_EDIT"));
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
     * <p>代表アカウント(作成アカウント)をすべて取得する
     * @return List in CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> selectCirAccountLv() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        LabelValueBean smlUsrMdl = null;
        ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAC_NAME,");
            sql.addSql("   CAC_BIKO,");
            sql.addSql("   CAC_JKBN,");
            sql.addSql("   CAC_THEME");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID is null");
            sql.addSql(" and ");
            sql.addSql("   CAC_JKBN = ?");
            sql.addIntValue(GSConstCircular.CAC_JKBN_NORMAL);
            sql.addSql(" order by CAC_NAME, CAC_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                smlUsrMdl = new LabelValueBean();
                smlUsrMdl.setLabel(rs.getString("CAC_NAME"));
                smlUsrMdl.setValue(String.valueOf(rs.getInt("CAC_SID")));
                ret.add(smlUsrMdl);
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
     * <br>[機  能] アカウントのアカウントデータを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 自動受信対象アカウントのアカウントSID一覧
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountModel> getAccountSidList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CirAccountModel> ret = new ArrayList<CirAccountModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE,");
            sql.addSql("   CIR_ACCOUNT.USR_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO,");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_THEME,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_NTF,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_DAY,");
            sql.addSql("   CIR_ACCOUNT.CAC_KOU_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_INIT_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_MEMO,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_EDIT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CAC_JKBN = ?");
            sql.addIntValue(GSConstCircular.CAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirAccountFromRs(rs));
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
     * <br>[機  能] 指定したユーザが利用可能なアカウント情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public int getAccountCount(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int count = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(*) as CNT");
            sql = __setAccountSearchSql(sql, userSid);

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
     * <br>[機  能] 指定したユーザが利用可能なアカウント情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return List in CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountModel> getAccountList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirAccountModel> ret = new ArrayList<CirAccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID as CAC_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE as CAC_TYPE,");
            sql.addSql("   CIR_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME as CAC_NAME,");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO as CAC_BIKO,");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN as CAC_JKBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_THEME as CAC_THEME,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_NTF as CAC_SML_NTF,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_KBN as CAC_MEMO_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_DAY as CAC_MEMO_DAY,");
            sql.addSql("   CIR_ACCOUNT.CAC_KOU_KBN as CAC_KOU_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_INIT_KBN as CAC_INIT_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_MEMO as CAC_SML_MEMO,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_EDIT as CAC_SML_EDIT,");
            sql.addSql("    CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");

            sql = __setAccountSearchSql(sql, userSid);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.CAS_SORT, CIR_ACCOUNT.CAC_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirAccountFromRs(rs));
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
     * <br>[機  能] 指定したアカウントSIDのアカウント情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param accountSids ユーザSID
     * @return List in CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountModel> getAccountList(String[] accountSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CirAccountModel> ret = new ArrayList<CirAccountModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID as CAC_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE as CAC_TYPE,");
            sql.addSql("   CIR_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME as CAC_NAME,");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO as CAC_BIKO,");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN as CAC_JKBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_THEME as CAC_THEME,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_NTF as CAC_SML_NTF,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_KBN as CAC_MEMO_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_DAY as CAC_MEMO_DAY,");
            sql.addSql("   CIR_ACCOUNT.CAC_KOU_KBN as CAC_KOU_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_INIT_KBN as CAC_INIT_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_MEMO as CAC_SML_MEMO,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_EDIT as CAC_SML_EDIT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql("      left join");
            sql.addSql("        CMN_USRM_INF");
            sql.addSql("      on");
            sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("      left join");
            sql.addSql("        CMN_USRM");
            sql.addSql("      on");
            sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            if (accountSids != null && accountSids.length > 0) {
                sql.addSql(" where ");
                sql.addSql("   CIR_ACCOUNT.CAC_SID in (");
                for (int i = 0; i < accountSids.length; i++) {
                    if (i != 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(accountSids[i]);
                }
                sql.addSql(")");
            }
            sql.addSql(" order by CIR_ACCOUNT.CAC_NAME, CIR_ACCOUNT.CAC_SID");
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirAccountFromRs(rs));
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
     * <br>[機  能] ユーザが指定したアカウントを利用可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param userSid ユーザSID
     * @return true:使用可能 false:使用不可
     * @throws SQLException SQL実行例外
     */
    public boolean canUseAccount(int sacSid, int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");

            sql = setAccountSearchSql(sql, userSid);

            sql.addSql(" and CIR_ACCOUNT.CAC_SID = ?");
            sql.addIntValue(sacSid);
            sql.setPagingValue(0, 1);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return result;
    }

    /**
     * <br>[機  能] 指定したユーザが利用可能なアカウントのアカウントSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @return アカウントSID一覧
     * @throws SQLException SQL実行例外
     */
    public int[] getAccountSidList(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int[] sacSidArray = new int[0];
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID as CAC_SID");

            sql = __setAccountSearchSql(sql, userSid);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.SAS_SORT");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            List<Integer> accountSidList = new ArrayList<Integer>();
            while (rs.next()) {
                accountSidList.add(rs.getInt("CAC_SID"));
            }

            sacSidArray = new int[accountSidList.size()];
            for (int i = 0; i < accountSidList.size(); i++) {
                sacSidArray[i] = accountSidList.get(i).intValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return sacSidArray;
    }

    /**
     * <br>[機  能] 指定したユーザのデフォルトアカウントSIDを取得する
     * <br>[解  説] 利用可能なアカウントのうち、並び順 = 1のものをデフォルトアカウントとする
     * <br>[備  考] 利用可能なアカウントが存在しない場合、0を返す
     * @param userSid ユーザSID
     * @return List in CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public int getDefaultAccountSid(int userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int defAccountSid = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID as CAC_SID");

            sql = __setAccountSearchSql(sql, userSid);

            sql.addSql(" order by");
            sql.addSql("   ACCOUNT_SORT.WAS_SORT, CIR_ACCOUNT.CAC_NAME");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                defAccountSid = rs.getInt("CAC_SID");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return defAccountSid;
    }

    /**
     * <p>Select CIR_ACCOUNT
     * @param sacSid CAC_SID
     * @return CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public CirAccountModel select(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirAccountModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIR_ACCOUNT.CAC_SID as CAC_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE as CAC_TYPE,");
            sql.addSql("   CIR_ACCOUNT.USR_SID as USR_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME as CAC_NAME,");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO as CAC_BIKO,");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN as CAC_JKBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_THEME as CAC_THEME,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_NTF as CAC_SML_NTF,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_KBN as CAC_MEMO_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_DAY as CAC_MEMO_DAY,");
            sql.addSql("   CIR_ACCOUNT.CAC_KOU_KBN as CAC_KOU_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_INIT_KBN as CAC_INIT_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_MEMO as CAC_SML_MEMO,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_EDIT as CAC_SML_EDIT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID ");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID ");

            sql.addSql(" where ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = new CirAccountModel();
                ret.setCacSid(rs.getInt("CAC_SID"));
                ret.setCacType(rs.getInt("CAC_TYPE"));
                ret.setUsrSid(rs.getInt("USR_SID"));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    ret.setCacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    ret.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                } else {
                    ret.setCacName(rs.getString("CAC_NAME"));
                }

                ret.setCacBiko(rs.getString("CAC_BIKO"));
                ret.setCacJkbn(rs.getInt("CAC_JKBN"));
                ret.setCacTheme(rs.getInt("CAC_THEME"));
                ret.setCacSmlNtf(rs.getInt("CAC_SML_NTF"));
                ret.setCacMemoKbn(rs.getInt("CAC_MEMO_KBN"));
                ret.setCacMemoDay(rs.getInt("CAC_MEMO_DAY"));
                ret.setCacKouKbn(rs.getInt("CAC_KOU_KBN"));
                ret.setCacInitKbn(rs.getInt("CAC_INIT_KBN"));
                ret.setCacSmlMemo(rs.getInt("CAC_SML_MEMO"));
                ret.setCacSmlEdit(rs.getInt("CAC_SML_EDIT"));
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
     * <p>Select CIR_ACCOUNT
     * @param usrSid USR_SID
     * @return CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public CirAccountModel selectFromUsrSid(int usrSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirAccountModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIR_ACCOUNT.CAC_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE,");
            sql.addSql("   CIR_ACCOUNT.USR_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO,");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_THEME,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_NTF,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_DAY,");
            sql.addSql("   CIR_ACCOUNT.CAC_KOU_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_INIT_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_MEMO,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_EDIT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CIR_ACCOUNT.USR_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret = __getCirAccountFromRs(rs);
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
     * <p>ユーザSIDからアカウントSIDを取得
     * @param usrSids USR_SID
     * @return CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<String> getAccountSidFromUsrSids(String[] usrSids) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAC_NAME,");
            sql.addSql("   CAC_BIKO,");
            sql.addSql("   CAC_JKBN,");
            sql.addSql("   CAC_THEME");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID in (");
            for (int i = 0; i < usrSids.length; i++) {
                if (i > 0) {
                    sql.addSql(", ");
                }
                sql.addSql(usrSids[i]);
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(String.valueOf(rs.getInt("CAC_SID")));
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
     * <p>ユーザSIDからアカウントSIDを取得
     * @param usrSids USR_SID
     * @return CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountModel> selectFromUsrSids(String[] usrSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CirAccountModel> ret = new ArrayList<CirAccountModel>();

        if (usrSids == null || usrSids.length <= 0) {
            return ret;
        }

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIR_ACCOUNT.CAC_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE,");
            sql.addSql("   CIR_ACCOUNT.USR_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO,");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_THEME,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_NTF,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_DAY,");
            sql.addSql("   CIR_ACCOUNT.CAC_KOU_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_INIT_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_MEMO,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_EDIT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CIR_ACCOUNT.USR_SID in (");
            for (int i = 0; i < usrSids.length; i++) {
                if (i > 0) {
                    sql.addSql(", ");
                }
                sql.addSql(usrSids[i]);
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CirAccountModel mdl = __getCirAccountFromRs(rs);
                ret.add(mdl);
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
     * <p>アカウントSIDからアカウント情報を取得
     * @param accountSids CAC_SID
     * @return CIR_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<LabelValueBean> selectSacSids(String[] accountSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        LabelValueBean mdl = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();

        if (accountSids != null && accountSids.length > 0) {

            try {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   CAC_SID,");
                sql.addSql("   CAC_TYPE,");
                sql.addSql("   USR_SID,");
                sql.addSql("   CAC_NAME,");
                sql.addSql("   CAC_BIKO,");
                sql.addSql("   CAC_JKBN,");
                sql.addSql("   CAC_THEME");
                sql.addSql(" from");
                sql.addSql("   CIR_ACCOUNT");
                sql.addSql(" where ");
                sql.addSql("   CAC_SID in (");
                for (int i = 0; i < accountSids.length; i++) {
                    if (i > 0) {
                        sql.addSql(", ");
                    }
                    sql.addSql(accountSids[i]);
                }
                sql.addSql("   )");
                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    mdl = new LabelValueBean();
                    mdl.setValue(String.valueOf(rs.getInt("CAC_SID")));
                    mdl.setLabel(rs.getString("CAC_NAME"));
                    ret.add(mdl);
                }
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closePreparedStatement(pstmt);
            }

        }
        return ret;
    }


    /**
     * <br>[機  能] 利用可能なアカウント情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントSID
     * @throws SQLException SQL実行エラー
     */
    public List<CirAccountModel> getExistAccountData() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CirAccountModel> ret = new ArrayList<CirAccountModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE,");
            sql.addSql("   CIR_ACCOUNT.USR_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO,");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_THEME,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_NTF,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_DAY,");
            sql.addSql("   CIR_ACCOUNT.CAC_KOU_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_INIT_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_MEMO,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_EDIT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI, ");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM");
            sql.addSql(" on");
            sql.addSql("   CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CAC_JKBN = ?");

            sql.addIntValue(GSConstCircular.CAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getCirAccountFromRs(rs));
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
     * <br>[機  能] 指定されたアカウントSIDリストの中からショートメール通知対象を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param accountList アカウントSID
     * @param notifyType 通知種別
     * @return List in CIR_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<Integer> getMailSendAccount(List<Integer> accountList, int notifyType)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<Integer> ret = new ArrayList<Integer>();
        con = getCon();
        if (accountList == null || accountList.size() < 1) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID in (");
            for (int i = 0; i < accountList.size(); i++) {
                sql.addSql("     ? ");
                sql.addIntValue(accountList.get(i));
                if (i < accountList.size() - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   ) ");
            //アカウント毎のショートメール通知設定
            switch (notifyType) {
            case GSConstCircular.NOTIFY_NTF:
                sql.addSql(" and ");
                sql.addSql("   CAC_SML_NTF = ?");
                sql.addIntValue(GSConstCircular.SMAIL_TSUUCHI);
                break;
            case GSConstCircular.NOTIFY_MEMO:
                sql.addSql(" and ");
                sql.addSql("   CAC_SML_MEMO = ?");
                sql.addIntValue(GSConstCircular.SMAIL_TSUUCHI);
                break;
            case GSConstCircular.NOTIFY_EDIT:
                sql.addSql(" and ");
                sql.addSql("   CAC_SML_EDIT = ?");
                sql.addIntValue(GSConstCircular.SMAIL_TSUUCHI);
                break;
            default:
            }
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("CAC_SID"));
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
     * <br>[機  能] 指定されたユーザSIDリストの中から
     *              ショートメール通知対象の受信者情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSids アカウントSID
     * @param smlFlg アカウント毎メール通知設定反映フラグ  true : 反映する  false:反映させない
     * @param notifyType 通知種別
     * @return List in CIR_USERModel
     * @throws SQLException SQL実行例外
     */
    public List<CirAccountModel> getMailSendUser(String[] cacSids, boolean smlFlg, int notifyType)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<CirAccountModel> ret = new ArrayList<CirAccountModel>();
        con = getCon();

        if (cacSids == null) {
            return ret;
        }
        if (cacSids.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE,");
            sql.addSql("   CIR_ACCOUNT.USR_SID,");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO,");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_THEME,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_NTF,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_MEMO_DAY,");
            sql.addSql("   CIR_ACCOUNT.CAC_KOU_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_INIT_KBN,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_MEMO,");
            sql.addSql("   CIR_ACCOUNT.CAC_SML_EDIT,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI, ");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as USR_UKO_FLG ");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" left join");
            sql.addSql("    CMN_USRM_INF");
            sql.addSql(" on");
            sql.addSql("    CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" left join");
            sql.addSql("    CMN_USRM");
            sql.addSql(" on");
            sql.addSql("    CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");

            sql.addSql(" where ");
            sql.addSql("   CAC_SID in (");

            for (int i = 0; i < cacSids.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(cacSids[i], 0));

                if (i < cacSids.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   ) ");

            //アカウント毎のショートメール通知設定
            if (smlFlg) {
                switch (notifyType) {
                case GSConstCircular.NOTIFY_NTF:
                    sql.addSql(" and ");
                    sql.addSql("   CAC_SML_NTF = ?");
                    sql.addIntValue(GSConstCircular.SMAIL_TSUUCHI);
                    break;
                case GSConstCircular.NOTIFY_MEMO:
                    sql.addSql(" and ");
                    sql.addSql("   CAC_SML_MEMO = ?");
                    sql.addIntValue(GSConstCircular.SMAIL_TSUUCHI);
                    break;
                case GSConstCircular.NOTIFY_EDIT:
                    sql.addSql(" and ");
                    sql.addSql("   CAC_SML_EDIT = ?");
                    sql.addIntValue(GSConstCircular.SMAIL_TSUUCHI);
                    break;
                default:
                }
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(__getCirAccountFromRs(rs));
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
     * <br>[機  能] 利用可能なアカウントのアカウントSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return アカウントSID
     * @throws SQLException SQL実行エラー
     */
    public List<Integer> getExistAccountSidList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Integer> ret = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   CAC_JKBN = ?");

            sql.addIntValue(GSConstCircular.CAC_JKBN_NORMAL);

            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("CAC_SID"));
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
     * <p>Delete CIR_ACCOUNT
     * @param sacSid CAC_SID
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int delete(int sacSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(sacSid);

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
     * <br>[機  能] アカウント情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CirAccountModel
     * @param accountMode アカウント種別
     * @throws SQLException SQL実行例外
     */
    public void insertAccount(CirAccountModel bean, int accountMode) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACCOUNT(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAC_NAME,");
            sql.addSql("   CAC_BIKO,");
            sql.addSql("   CAC_JKBN,");
            sql.addSql("   CAC_THEME,");
            sql.addSql("   CAC_SML_NTF,");
            sql.addSql("   CAC_MEMO_KBN,");
            sql.addSql("   CAC_MEMO_DAY,");
            sql.addSql("   CAC_KOU_KBN,");
            sql.addSql("   CAC_INIT_KBN,");
            sql.addSql("   CAC_SML_MEMO,");
            sql.addSql("   CAC_SML_EDIT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
//            if (accountMode == GSConstSmail.CAC_TYPE_USER) {
//                sql.addSql("   ?,");
//            } else {
                sql.addSql("   null,");
//            }
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
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getCacType());
//            if (accountMode != GSConstSmail.CAC_TYPE_USER) {
//                sql.addValue(bean.getUsrSid());
//            }
            sql.addStrValue(bean.getCacName());
            sql.addStrValue(bean.getCacBiko());
            sql.addIntValue(bean.getCacJkbn());
            sql.addIntValue(bean.getCacTheme());
            sql.addIntValue(bean.getCacSmlNtf());
            sql.addIntValue(bean.getCacMemoKbn());
            sql.addIntValue(bean.getCacMemoDay());
            sql.addIntValue(bean.getCacKouKbn());
            sql.addIntValue(bean.getCacInitKbn());
            sql.addIntValue(bean.getCacSmlMemo());
            sql.addIntValue(bean.getCacSmlEdit());
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
     * <br>[機  能] アカウント情報の登録を行う(ユーザ登録時)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CirAccountModel
     * @throws SQLException SQL実行例外
     */
    public void insertAccountDef(CirAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" CIR_ACCOUNT(");
            sql.addSql("   CAC_SID,");
            sql.addSql("   CAC_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   CAC_NAME,");
            sql.addSql("   CAC_BIKO,");
            sql.addSql("   CAC_JKBN,");
            sql.addSql("   CAC_THEME,");
            sql.addSql("   CAC_SML_NTF,");
            sql.addSql("   CAC_MEMO_KBN,");
            sql.addSql("   CAC_MEMO_DAY,");
            sql.addSql("   CAC_KOU_KBN,");
            sql.addSql("   CAC_INIT_KBN,");
            sql.addSql("   CAC_SML_MEMO,");
            sql.addSql("   CAC_SML_EDIT");
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
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getCacType());
            sql.addIntValue(bean.getUsrSid());
            sql.addStrValue(bean.getCacName());
            sql.addStrValue(bean.getCacBiko());
            sql.addIntValue(bean.getCacJkbn());
            sql.addIntValue(bean.getCacTheme());
            sql.addIntValue(bean.getCacSmlNtf());
            sql.addIntValue(bean.getCacMemoKbn());
            sql.addIntValue(bean.getCacMemoDay());
            sql.addIntValue(bean.getCacKouKbn());
            sql.addIntValue(bean.getCacInitKbn());
            sql.addIntValue(bean.getCacSmlMemo());
            sql.addIntValue(bean.getCacSmlEdit());
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
     * <br>[機  能] アカウント情報の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CirAccountModel
     * @param accountMode アカウントモード
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int updateAccount(CirAccountModel bean, int accountMode) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   CAC_NAME=?,");
            //アカウント処理モード 共通
            if (accountMode == GSConstCircular.ACCOUNTMODE_COMMON) {
                sql.addSql("   CAC_TYPE=?,");
            }
            sql.addSql("   CAC_BIKO=?,");
            sql.addSql("   CAC_THEME=?,");
            sql.addSql("   CAC_SML_NTF=?,");
            sql.addSql("   CAC_MEMO_KBN=?,");
            sql.addSql("   CAC_MEMO_DAY=?,");
            sql.addSql("   CAC_KOU_KBN=?,");
            sql.addSql("   CAC_INIT_KBN=?,");
            sql.addSql("   CAC_SML_MEMO=?,");
            sql.addSql("   CAC_SML_EDIT=?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(bean.getCacName());
            //アカウント処理モード 共通
            if (accountMode == GSConstCircular.ACCOUNTMODE_COMMON) {
                sql.addIntValue(bean.getCacType());
            }
            sql.addStrValue(bean.getCacBiko());
            sql.addIntValue(bean.getCacTheme());
            sql.addIntValue(bean.getCacSmlNtf());
            sql.addIntValue(bean.getCacMemoKbn());
            sql.addIntValue(bean.getCacMemoDay());
            sql.addIntValue(bean.getCacKouKbn());
            sql.addIntValue(bean.getCacInitKbn());
            sql.addIntValue(bean.getCacSmlMemo());
            sql.addIntValue(bean.getCacSmlEdit());

            //where
            sql.addIntValue(bean.getCacSid());

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
     * <br>[機  能] アカウント情報のショートメール通知部分の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CirAccountModel
     * @param accountMode アカウントモード
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int updateAccountSml(CirAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   CAC_SML_NTF=?,");
            sql.addSql("   CAC_SML_MEMO=?,");
            sql.addSql("   CAC_SML_EDIT=?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacSmlNtf());
            sql.addIntValue(bean.getCacSmlMemo());
            sql.addIntValue(bean.getCacSmlEdit());

            //where
            sql.addIntValue(bean.getCacSid());

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
     * <br>[機  能] 状態区分の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param jkbn 状態区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbn(int sacSid, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   CAC_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID=?");

            sql.addIntValue(jkbn);
            sql.addIntValue(sacSid);

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
     * <br>[機  能] 状態区分の変更を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSidList アカウントSID
     * @param jkbn 状態区分
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJkbn(String[] sacSidList, int jkbn) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   CAC_JKBN=?");
            sql.addIntValue(jkbn);
            sql.addSql(" where ");
            sql.addSql("   CAC_SID in (");
            sql.addSql("     ?");
            sql.addIntValue(Integer.parseInt(sacSidList[0]));
            for (String accountSid : sacSidList) {
                sql.addSql("     ,?");
                sql.addIntValue(Integer.parseInt(accountSid));
            }
            sql.addSql("   )");

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
     * <br>[機  能] アカウント情報取得SQLの検索条件部分を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     * @return SqlBuffer
     */
    public SqlBuffer setAccountSearchSql(SqlBuffer sql, int userSid) {
        sql.addSql(" where");
        sql.addSql("   CIR_ACCOUNT.CAC_JKBN = ?");
        sql.addSql(" and");
        sql.addSql("   (");
        sql.addSql("      (");
        sql.addSql("         CIR_ACCOUNT.CAC_TYPE = ?");
        sql.addSql("       and");
        sql.addSql("         CIR_ACCOUNT.USR_SID = ?");
        sql.addSql("      )");
        sql.addSql("      or ");
        sql.addSql("      (");
        sql.addSql("         exists ( ");
        sql.addSql("           select CAC_SID from CIR_ACCOUNT_USER ");
        sql.addSql("           where");
        sql.addSql("           GRP_SID in ( ");
        sql.addSql("             select GRP_SID from CMN_BELONGM ");
        sql.addSql("             where USR_SID = ? ");
        sql.addSql("           )");
        sql.addSql("         and ");
        sql.addSql("           CIR_ACCOUNT.CAC_SID = CIR_ACCOUNT_USER.CAC_SID ");
        sql.addSql("         and ");
        sql.addSql("           CIR_ACCOUNT_USER.USR_SID < 0 ");
        sql.addSql("         )");
        sql.addSql("      )");
        sql.addSql("      or ");
        sql.addSql("      (");
        sql.addSql("         exists ( ");
        sql.addSql("           select CAC_SID from CIR_ACCOUNT_USER ");
        sql.addSql("           where");
        sql.addSql("             USR_SID = ? ");
        sql.addSql("           and ");
        sql.addSql("             CIR_ACCOUNT.CAC_SID = CIR_ACCOUNT_USER.CAC_SID ");
        sql.addSql("           and ");
        sql.addSql("             CIR_ACCOUNT_USER.GRP_SID < 0 ");
        sql.addSql("         )");
        sql.addSql("      )");
        sql.addSql("   )");

        sql.addIntValue(GSConstCircular.CAC_JKBN_NORMAL);
        sql.addIntValue(GSConstCircular.CAC_TYPE_NORMAL);
        sql.addIntValue(userSid);
        sql.addIntValue(userSid);
        sql.addIntValue(userSid);

        return sql;
    }

    /**
     * <br>[機  能] アカウント情報取得SQLの検索条件部分を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param userSid ユーザSID
     * @return SqlBuffer
     */
    private SqlBuffer __setAccountSearchSql(SqlBuffer sql, int userSid) {
        sql.addSql(" from ");
        sql.addSql("   CIR_ACCOUNT");
        sql.addSql("   left join");
        sql.addSql("     (");
        sql.addSql("       select CAC_SID, CAS_SORT");
        sql.addSql("       from CIR_ACCOUNT_SORT");
        sql.addSql("       where USR_SID = ?");
        sql.addSql("     ) ACCOUNT_SORT");
        sql.addSql("   on");
        sql.addSql("     CIR_ACCOUNT.CAC_SID = ACCOUNT_SORT.CAC_SID");
        sql.addSql("      left join");
        sql.addSql("        CMN_USRM_INF");
        sql.addSql("      on");
        sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("      left join");
        sql.addSql("        CMN_USRM");
        sql.addSql("      on");
        sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");

        sql.addIntValue(userSid);

        sql = setAccountSearchSql(sql, userSid);

        return sql;
    }

    /**
     * <br>[機  能] アカウントSID配列から削除済みのユーザ数をカウントする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID配列
     * @return ret カウント件数
     * @throws SQLException SQL実行例外
     */
    public int getCountDeleteAccount(List<String> userSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        int count = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CIR_ACCOUNT.CAC_SID) as cnt");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID in (");

            for (int i = 0; i < userSid.size(); i++) {
                if (i != 0) {
                    sql.addSql(", ");
                }
                sql.addSql(userSid.get(i));
            }
            sql.addSql(")");

            sql.addSql(" and");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstCircular.CAC_JKBN_DELETE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("cnt");
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
     * <br>[機  能] 指定したアカウント名に該当するアカウントのSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param accountName アカウント名
     * @return アカウントSID
     * @throws SQLException SQL実行例外
     */
    public List<Integer> searchByNameAccount(String accountName) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        List<Integer> accountList = new ArrayList<Integer>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIR_ACCOUNT.CAC_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select CMN_USRM.USR_SID, CMN_USRM_INF.USI_SEI"
                            + " || ' ' || CMN_USRM_INF.USI_MEI as USR_ACCOUNT_NAME");
            sql.addSql("      from CMN_USRM, CMN_USRM_INF");
            sql.addSql("      where CMN_USRM.USR_JKBN = ?");
            sql.addSql("      and CMN_USRM.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("     ) USER_ACCOUNT");
            sql.addSql("   on");
            sql.addSql("     CIR_ACCOUNT.USR_SID = USER_ACCOUNT.USR_SID");
            sql.addSql(" where ");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN = ?");
            sql.addIntValue(GSConstCircular.CAC_JKBN_NORMAL);

            sql.addSql(" and ");
            sql.addSql("   (");
            //共通アカウント
            sql.addSql("     (");
            sql.addSql("       coalesce(CIR_ACCOUNT.USR_SID, 0) <= 0");
            sql.addSql("     and ");
            sql.addSql("       CIR_ACCOUNT.CAC_NAME = ?");
            sql.addSql("     )");
            sql.addSql("   or ");
            //ユーザアカウント
            sql.addSql("     (");
            sql.addSql("       coalesce(CIR_ACCOUNT.USR_SID, 0) > 0");
            sql.addSql("     and ");
            sql.addSql("       USER_ACCOUNT.USR_ACCOUNT_NAME = ?");
            sql.addSql("     )");
            sql.addSql("   )");

            sql.addIntValue(GSConstUser.USER_JTKBN_ACTIVE);
            sql.addStrValue(accountName);
            sql.addStrValue(accountName);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                accountList.add(rs.getInt("CAC_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return accountList;
    }

    /**
     * 初期値 設定値を登録する。
     * @param bean CIR_USER Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void updateInitSet(CirAccountModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" set ");
            sql.addSql("   CAC_MEMO_KBN = ?,");
            sql.addSql("   CAC_MEMO_DAY = ?,");
            sql.addSql("   CAC_KOU_KBN = ?,");
            sql.addSql("   CAC_INIT_KBN = ?");
            sql.addSql(" where ");
            sql.addSql("   CAC_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getCacMemoKbn());
            sql.addIntValue(bean.getCacMemoDay());
            sql.addIntValue(bean.getCacKouKbn());
            sql.addIntValue(bean.getCacInitKbn());
            //where
            sql.addIntValue(bean.getCacSid());

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
     * <p>Create CIR_ACCOUNT Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created CirAccountModel
     * @throws SQLException SQL実行例外
     */
    private CirAccountModel __getCirAccountFromRs(ResultSet rs) throws SQLException {
        CirAccountModel bean = new CirAccountModel();
        bean.setCacSid(rs.getInt("CAC_SID"));
        bean.setCacType(rs.getInt("CAC_TYPE"));
        bean.setUsrSid(rs.getInt("USR_SID"));

        if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
            bean.setCacName(
                    rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
            bean.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
        } else {
            bean.setCacName(rs.getString("CAC_NAME"));
        }

        bean.setCacBiko(rs.getString("CAC_BIKO"));
        bean.setCacJkbn(rs.getInt("CAC_JKBN"));
        bean.setCacTheme(rs.getInt("CAC_THEME"));
        bean.setCacSmlNtf(rs.getInt("CAC_SML_NTF"));
        bean.setCacMemoKbn(rs.getInt("CAC_MEMO_KBN"));
        bean.setCacMemoDay(rs.getInt("CAC_MEMO_DAY"));
        bean.setCacKouKbn(rs.getInt("CAC_KOU_KBN"));
        bean.setCacInitKbn(rs.getInt("CAC_INIT_KBN"));
        bean.setCacSmlMemo(rs.getInt("CAC_SML_MEMO"));
        bean.setCacSmlEdit(rs.getInt("CAC_SML_EDIT"));
        return bean;
    }

    /**
     * <br>[機  能] アカウント名を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param acSid アカウントSID
     * @return アカウント名
     * @throws SQLException SQL実行例外
     */
    public String getCirAccountName(int acSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        String ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_NAME");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" where");
            sql.addSql("   CAC_SID = ?");
            sql.addIntValue(acSid);

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getString("CAC_NAME");
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
     * <br>[機  能] アカウント情報が存在するか
     * <br>[解  説]
     * <br>[備  考]
     * @param cacSid アカウントSID
     * @return true:存在する
     * @throws SQLException SQL実行エラー
     */
    public boolean selectExistAccount(int cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean ret = false;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   CAC_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   CAC_SID = ?");
            sql.addIntValue(GSConstCircular.CAC_JKBN_NORMAL);
            sql.addIntValue(cacSid);
            log__.info(sql.toLogString());
            pstmt = getCon().prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
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
}
