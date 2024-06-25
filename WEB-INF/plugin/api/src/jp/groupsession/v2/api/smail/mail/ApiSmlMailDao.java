package jp.groupsession.v2.api.smail.mail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <p> ショートメール一覧情報を取得する際に使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiSmlMailDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlMailDao.class);

    /**
     * <p>Default Constructor
     */
    public ApiSmlMailDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ApiSmlMailDao(Connection con) {
        super(con);
    }


    /**
     * <br>[機  能] ショートメールの本文情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailSidList メールSID一覧
     * @return メールSIDをキーにした本文情報一覧
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, ApiSmlMailBodyModel> getMailBodyMap(List<Integer> mailSidList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet  rs  = null;
        Connection con = getCon();

        HashMap<Integer, ApiSmlMailBodyModel> ret = new HashMap<Integer, ApiSmlMailBodyModel>();

        try {
            // -------------------------------------------------
            //  メール本文情報
            // -------------------------------------------------
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   unionView.mailSid  as mailSid,");
            sql.addSql("   unionView.mailType as mailType,");
            sql.addSql("   unionView.mailBody as body");
            sql.addSql(" from");
            sql.addSql("   (");
            // 受信 or 送信
            sql.addSql("    select ");
            sql.addSql("      SMS_SID  as mailSid,");
            sql.addSql("      SMS_TYPE as mailType,");
            sql.addSql("      SMS_BODY as mailBody");
            sql.addSql("    from");
            sql.addSql("      SML_SMEIS");
            sql.addSql("    where");
            sql.addSql("      SMS_SID in (");
            for (int i = 0; i < mailSidList.size(); i++) {
                sql.addSql(i > 0 ? "    ,?" : "    ?");
                sql.addIntValue(mailSidList.get(i));
            }
            sql.addSql("      )");

            sql.addSql(" union all");
            //草稿
            sql.addSql("    select ");
            sql.addSql("      SMW_SID  as mailSid,");
            sql.addSql("      SMW_TYPE as mailType,");
            sql.addSql("      SMW_BODY as mailBody");
            sql.addSql("    from");
            sql.addSql("      SML_WMEIS");
            sql.addSql("    where");
            sql.addSql("      SMW_SID in (");
            for (int i = 0; i < mailSidList.size(); i++) {
                sql.addSql(i > 0 ? "    ,?" : "    ?");
                sql.addIntValue(mailSidList.get(i));
            }
            sql.addSql("      )");
            sql.addSql("   ) unionView");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer key   = Integer.valueOf(rs.getInt("mailSid"));
                Integer type  = Integer.valueOf(rs.getInt("mailType"));
                String  body  = rs.getString("body");
                ApiSmlMailBodyModel mdl = new ApiSmlMailBodyModel();

                if (body != null) {
                    mdl.setMailType(type);
                    mdl.setMailBody(body);
                    ret.put(key, mdl);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        try {
            // -------------------------------------------------
            //  添付ファイル一覧情報
            // -------------------------------------------------
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    SML_BIN.SML_SID  as SML_SID,");
            sql.addSql("    CMN_BINF.BIN_SID as BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION as BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME as BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH as BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE as BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    SML_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    SML_BIN.SML_SID in (");
            for (int i = 0; i < mailSidList.size(); i++) {
                sql.addSql(i > 0 ? "    ,?" : "    ?");
                sql.addIntValue(mailSidList.get(i));
            }
            sql.addSql("    )");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    SML_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  order by");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CommonBiz cmnBiz = new CommonBiz();
            while (rs.next()) {
                Integer key = Integer.valueOf(rs.getInt("SML_SID"));
                ApiSmlMailBodyModel mdl = ret.get(key);
                if (mdl == null) {
                    mdl = new ApiSmlMailBodyModel();
                    ret.put(key, mdl);
                }

                CmnBinfModel retMdl = new CmnBinfModel();
                retMdl.setBinSid(rs.getLong("BIN_SID"));
                retMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                retMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                retMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                retMdl.setBinFileSize(size);
                retMdl.setBinFileSizeDsp(strSize);
                mdl.addBinModel(retMdl);
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
     * <br>[機  能] ショートメールの宛先情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailSidList メールSID一覧
     * @param smlKbn      メールボックス区分
     * @return メールSIDをキーにした宛先情報一覧
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, ArrayList<AtesakiModel>> getMailAtesakiMap(List<Integer> mailSidList,
                           String smlKbn) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        if (mailSidList == null || mailSidList.size() == 0) {
            return null;
        }

        HashMap<Integer, ArrayList<AtesakiModel>> ret
                                  = new HashMap<Integer, ArrayList<AtesakiModel>>();
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SML_ACCOUNT.SAC_SID   as sacSid,");
            sql.addSql("   SML_ACCOUNT.SAC_TYPE  as sacType,");
            sql.addSql("   SML_ACCOUNT.SAC_NAME  as sacName,");
            sql.addSql("   SML_ACCOUNT.SAC_JKBN  as sacJkbn,");
            sql.addSql("   CMN_USRM_INF.USI_SEI  as usiSei,");
            sql.addSql("   CMN_USRM_INF.USI_MEI  as usiMei,");
            sql.addSql("   CMN_USRM.USR_JKBN     as usrJkbn,");
            sql.addSql("   CMN_USRM.USR_UKO_FLG as usrUkoFlg, ");
            sql.addSql("   unionView.smjSid      as smjSid,");
            sql.addSql("   unionView.smjOpdate   as smjOpdate,");
            sql.addSql("   unionView.smjSendkbn  as smjSendkbn");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" left join CMN_USRM_INF");
            sql.addSql(" on SML_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql(" left join CMN_USRM");
            sql.addSql(" on SML_ACCOUNT.USR_SID = CMN_USRM.USR_SID,");
            sql.addSql("   (");
            if (!smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                sql.addSql("     select");
                sql.addSql("       SAC_SID      as sacSid,");
                sql.addSql("       SMJ_SID      as smjSid,");
                sql.addSql("       SMJ_OPDATE   as smjOpdate,");
                sql.addSql("       SMJ_SENDKBN  as smjSendkbn");
                sql.addSql("     from");
                sql.addSql("       SML_JMEIS");
                sql.addSql("     where");
                sql.addSql("       SMJ_SID in (");
                for (int i = 0; i < mailSidList.size(); i++) {
                    Integer mailSid = mailSidList.get(i);
                    sql.addSql((i > 0 ? "   ,?" : "   ?"));
                    sql.addIntValue(mailSid.intValue());
                }
                sql.addSql("       )");
            }
            if (!smlKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)
             && !smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                if (!smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                    sql.addSql("   union all");
                }
                sql.addSql("     select");
                sql.addSql("       SAC_SID      as sacSid,");
                sql.addSql("       SMS_SID      as smjSid,");
                sql.addSql("       NULL         as smjOpdate,");
                sql.addSql("       SMJ_SENDKBN  as smjSendkbn");
                sql.addSql("     from");
                sql.addSql("       SML_ASAK");
                sql.addSql("     where");
                sql.addSql("       SMS_SID in (");
                for (int i = 0; i < mailSidList.size(); i++) {
                    Integer mailSid = mailSidList.get(i);
                    sql.addSql((i > 0 ? "   ,?" : "   ?"));
                    sql.addIntValue(mailSid.intValue());
                }
                sql.addSql("       )");
            }
            sql.addSql("   ) as unionView");
            sql.addSql(" where ");
            sql.addSql("   unionView.sacSid = SML_ACCOUNT.SAC_SID");

            // 送信済み、草稿以外の場合は、宛先が含まれないのでここで条件セット
            if (smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
             || smlKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                sql.addSql(" and");
                sql.addSql("   unionView.smjSendkbn <> ?");
                sql.addIntValue(GSConstSmail.SML_SEND_KBN_ATESAKI);
            }

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer key = Integer.valueOf(rs.getInt("smjSid"));
                ArrayList<AtesakiModel> list = ret.get(key);
                if (list == null) {
                    list = new ArrayList<AtesakiModel>();
                    ret.put(key, list);
                }
                String sacName = "";
                int    sacJkbn = GSConstSmail.SAC_JKBN_NORMAL;
                if (!StringUtil.isNullZeroStringSpace(rs.getString("usiSei"))
                 && !StringUtil.isNullZeroStringSpace(rs.getString("usiMei"))) {
                    // 通常アカウントの場合 ⇒ ユーザ名(姓＋名)
                    sacName = rs.getString("usiSei") + " " + rs.getString("usiMei");
                    if (rs.getInt("usrJkbn") == GSConstUser.USER_JTKBN_DELETE) {
                        sacJkbn = GSConstSmail.SAC_JKBN_DELETE;
                    }
                } else {
                    // 代表者アカウント or ユーザ情報が見つからなかった場合 ⇒ アカウント名
                    sacName = rs.getString("sacName");
                    sacJkbn = rs.getInt("sacJkbn");
                }

                AtesakiModel mdl = new AtesakiModel();
                mdl.setAccountSid(rs.getInt("sacSid"));
                mdl.setAccountName(sacName);
                mdl.setAccountJkbn(sacJkbn);
                mdl.setSmjOpdate(UDate.getInstanceTimestamp(rs.getTimestamp("smjOpdate")));
                mdl.setSmjSendkbn(rs.getInt("smjSendkbn"));
                mdl.setUsrUkoFlg(rs.getInt("usrUkoFlg"));
                list.add(mdl);
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
     * <br>[機  能] ショートメールのラベル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param mailSidList メールSID一覧
     * @return メールSIDをキーにした本文情報一覧
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, List<SmlLabelModel>> getMailLabelMap(List<Integer> mailSidList)
                                                        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet  rs  = null;

        if (mailSidList == null || mailSidList.size() == 0) {
            return null;
        }

        HashMap<Integer, List<SmlLabelModel>> ret = new HashMap<Integer, List<SmlLabelModel>>();
        Connection con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   unionView.mailSid   as mailSid,");
            sql.addSql("   SML_LABEL.SLB_SID   as labelSid,");
            sql.addSql("   SML_LABEL.SLB_NAME  as labelName,");
            sql.addSql("   SML_LABEL.SLB_TYPE  as labelType,");
            sql.addSql("   SML_LABEL.SLB_ORDER as labelOrder");
            sql.addSql(" from");
            sql.addSql("   SML_LABEL,");
            sql.addSql("   (");
            // 受信
            sql.addSql("    select ");
            sql.addSql("      SMJ_SID as mailSid,");
            sql.addSql("      SLB_SID as labelSid,");
            sql.addSql("      SAC_SID as sacSid");
            sql.addSql("    from");
            sql.addSql("      SML_JMEIS_LABEL");
            sql.addSql("    where");
            sql.addSql("      SMJ_SID in (");
            for (int i = 0; i < mailSidList.size(); i++) {
                sql.addSql(i > 0 ? "    ,?" : "    ?");
                sql.addIntValue(mailSidList.get(i));
            }
            // 送信
            sql.addSql("      )");
            sql.addSql("    select ");
            sql.addSql("      SMS_SID as mailSid,");
            sql.addSql("      SLB_SID as labelSid,");
            sql.addSql("      SAC_SID as sacSid");
            sql.addSql("    from");
            sql.addSql("      SML_SMEIS_LABEL");
            sql.addSql("    where");
            sql.addSql("      SMS_SID in (");
            for (int i = 0; i < mailSidList.size(); i++) {
                sql.addSql(i > 0 ? "    ,?" : "    ?");
                sql.addIntValue(mailSidList.get(i));
            }
            sql.addSql("      )");
            sql.addSql(" union all");
            //草稿
            sql.addSql("    select ");
            sql.addSql("      SMW_SID as mailSid,");
            sql.addSql("      SLB_SID as labelSid,");
            sql.addSql("      SAC_SID as sacSid");
            sql.addSql("    from");
            sql.addSql("      SML_WMEIS_LABEL");
            sql.addSql("    where");
            sql.addSql("      SMW_SID in (");
            for (int i = 0; i < mailSidList.size(); i++) {
                sql.addSql(i > 0 ? "    ,?" : "    ?");
                sql.addIntValue(mailSidList.get(i));
            }
            sql.addSql("      )");
            sql.addSql("   ) unionView");
            sql.addSql(" where");
            sql.addSql("   unionView.labelSid = SML_LABEL.SML_SID");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer key = Integer.valueOf(rs.getInt("mailSid"));
                List<SmlLabelModel> list = ret.get(key);
                if (list == null) {
                    list = new ArrayList<SmlLabelModel>();
                    ret.put(key, list);
                }
                SmlLabelModel mdl = new SmlLabelModel();
                mdl.setSacSid(rs.getInt("sacSid"));
                mdl.setSlbSid(rs.getInt("labelSid"));
                mdl.setSlbName(rs.getString("labelName"));
                mdl.setSlbType(rs.getInt("labelType"));
                mdl.setSlbOrder(rs.getInt("labelOrder"));
                list.add(mdl);
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
     * <p>SIDで指定したアカウントの内、存在するアカウントSIDを返す
     * @param sacSids 指定SID
     * @return 実在するアカウントSID
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, Integer> getExistSacSidList(HashSet<Integer> sacSids)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<Integer, Integer> ret = new HashMap<Integer, Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   SAC_JKBN = ?");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);
            sql.addSql(" and SAC_SID in (");
            int cnt = 0;
            for (Integer sid : sacSids) {
                sql.addSql((cnt > 0 ? "     ,?" : "     ?"));
                sql.addIntValue(sid);
                cnt++;
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer key   = Integer.valueOf(rs.getString("SAC_SID"));
                String  value = rs.getString("USR_SID");
                if (value != null) {
                    // 通常アカウント
                    ret.put(key, Integer.valueOf(value));
                } else {
                    // 代表者アカウント
                    ret.put(key, -1);
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
     * <br>[機  能] ラベル情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @return ラベル情報一覧
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getLabelCount(int sacSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<LabelValueBean> ret = new ArrayList<LabelValueBean>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SML_JMEIS_LABEL.SLB_SID as SLB_SID,");
            sql.addSql("   count(SML_JMEIS_LABEL.SMJ_SID) as LABEL_COUNT");
            sql.addSql(" from");
            sql.addSql("   SML_JMEIS_LABEL");
            sql.addSql(" left join");
            sql.addSql("   SML_JMEIS");
            sql.addSql(" on ");
            sql.addSql("   SML_JMEIS_LABEL.SMJ_SID = SML_JMEIS.SMJ_SID");
            sql.addSql(" where");
            sql.addSql("   SML_JMEIS_LABEL.SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SAC_SID = ?");
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SMJ_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   SML_JMEIS.SMJ_OPKBN = ?");
            sql.addSql(" group by");
            sql.addSql("   SML_JMEIS_LABEL.SLB_SID");

            sql.addIntValue(sacSid);
            sql.addIntValue(sacSid);
            sql.addIntValue(GSConstSmail.SML_JTKBN_TOROKU);
            sql.addIntValue(GSConstSmail.OPKBN_UNOPENED);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                LabelValueBean bean = new LabelValueBean();
                bean.setLabel(rs.getString("SLB_SID"));
                bean.setValue(rs.getString("LABEL_COUNT"));
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

}
