package jp.groupsession.v2.cir.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.biz.CirUsedDataBiz;
import jp.groupsession.v2.cir.cir020.Cir020SearchModel;
import jp.groupsession.v2.cir.cir020.CirHashControlModel;
import jp.groupsession.v2.cir.cir020.model.Cir020KnDataSearchModel;
import jp.groupsession.v2.cir.model.CirBinModel;
import jp.groupsession.v2.cir.model.CirInfModel;
import jp.groupsession.v2.cir.model.CirSearchModel;
import jp.groupsession.v2.cir.model.CirUserBinModel;
import jp.groupsession.v2.cir.model.CirViewModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cir.model.CircularUsrModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginControlModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] 回覧板プラグインで共通利用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CircularDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CircularDao.class);

    /** 取得区分(カウント) */
    public static final int GET_COUNT = 0;
    /** 取得区分(一覧表示) */
    public static final int GET_LIST = 1;
    /** 取得区分(未確認・受信一覧) */
    public static final int GET_UNOPEN_LIST = 2;

    /**
     * <p>デフォルトコンストラクタ
     */
    public CircularDao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public CircularDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 回覧板情報(受信)の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @return int 回覧板情報(受信)の件数
     * @throws SQLException SQL実行例外
     */
    public int getJusinCount(CirSearchModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __createJusinSql(bean, GET_COUNT, 0, 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 回覧板情報(受信)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getJusinList(CirSearchModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __createJusinSql(
                    bean, GET_LIST, bean.getLimit(), (bean.getStart() - 1));

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setJsFlg(GSConstCircular.MODE_JUSIN);
                retMdl.setCvwConf(rs.getInt("CVW_CONF"));
                retMdl.setCifSid(rs.getInt("CIF_SID"));
                retMdl.setCifTitle(rs.getString("CIF_TITLE"));
                retMdl.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
                retMdl.setCifShow(rs.getInt("CIF_SHOW"));
                retMdl.setCacJkbn(rs.getInt("CAC_JKBN"));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    retMdl.setCacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    retMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                } else {
                    retMdl.setCacName(rs.getString("CAC_NAME"));
                }

                retMdl.setOpenCount(rs.getInt("OPEN_CNT"));
                retMdl.setAllCount(rs.getInt("ALL_CNT"));
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板情報(受信)の回覧板SIDを全て取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean Cir010SearchModel
     * @param cirSid 現在表示中の回覧板SID
     * @return CirHashControlModel
     * @throws SQLException SQL実行例外
     */
    public CirHashControlModel getJusinAllList(CirSearchModel bean, int cirSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirHashControlModel ret = new CirHashControlModel();
        HashMap<Integer, CircularDspModel> retMap = new HashMap<Integer, CircularDspModel>();
        con = getCon();

        int rowCount = 0;
        int selectRow = 0;

        try {
            //SQL文
            SqlBuffer sql = __createJusinSql(bean, GET_LIST, 0, 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CircularDspModel cdMdl = null;
            while (rs.next()) {
                rowCount++;
                cdMdl = new CircularDspModel();
                cdMdl.setCifSid(rs.getInt("CIF_SID"));
                retMap.put(rowCount, cdMdl);
                if (cirSid == rs.getInt("CIF_SID")) {
                    selectRow = rowCount;
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        ret.setRowNum(selectRow);
        ret.setMap(retMap);

        return ret;
    }

    /**
     * <br>[機  能] 回覧板情報(未確認・受信)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param limit 最大表示件数
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public List < CircularDspModel > getUnopenJusinList(CirSearchModel bean, int limit)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __createJusinSql(bean, GET_UNOPEN_LIST, limit, 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setCvwConf(rs.getInt("CVW_CONF"));
                retMdl.setCifSid(rs.getInt("CIF_SID"));
                retMdl.setCifTitle(rs.getString("CIF_TITLE"));
                retMdl.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
                retMdl.setCacSid(rs.getInt("CAC_SID"));
                retMdl.setCacJkbn(rs.getInt("CAC_JKBN"));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    retMdl.setCacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    retMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                } else {
                    retMdl.setCacName(rs.getString("CAC_NAME"));
                }

                if (!StringUtil.isNullZeroStringSpace(rs.getString("VIEW_USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("VIEW_USI_MEI"))) {
                    retMdl.setPosName(
                            rs.getString("VIEW_USI_SEI") + " " + rs.getString("VIEW_USI_MEI"));
                    retMdl.setViewUkoFlg(rs.getInt("VIEW_UKO_FLG"));
                } else {
                    retMdl.setPosName(rs.getString("ACCOUNT_NAME"));
                }

                ret.add(retMdl);
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
     * <br>[機  能] 回覧板情報(受信・削除確認)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param cirSid 回覧板SID
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getJusinConfList(CirSearchModel bean, String[] cirSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        if (cirSid == null) {
            return ret;
        }
        if (cirSid.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = __createJusinConfSql(bean, cirSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setCvwConf(rs.getInt("CVW_CONF"));
                retMdl.setCifSid(rs.getInt("CIF_SID"));
                retMdl.setCifTitle(rs.getString("CIF_TITLE"));
                retMdl.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
                ret.add(retMdl);
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
     * <br>[機  能] SQLを作成する(回覧板情報(受信・削除確認)取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param cirSid 回覧板SID
     * @return SqlBuffer
     */
    private SqlBuffer __createJusinConfSql(CirSearchModel bean, String[] cirSid) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");
        sql.addSql("    CIR_VIEW.CVW_CONF,");
        sql.addSql("    CIR_INF.CIF_SID,");
        sql.addSql("    CIR_INF.CIF_TITLE,");
        sql.addSql("    CIR_INF.CIF_ADATE");
        sql.addSql("  from");
        sql.addSql("    CIR_VIEW,");
        sql.addSql("    CIR_INF");
        sql.addSql("  where");
        sql.addSql("    CIR_INF.CIF_SID in (");

        for (int i = 0; i < cirSid.length; i++) {
            sql.addSql("     ? ");
            sql.addIntValue(NullDefault.getInt(cirSid[i], 0));

            if (i < cirSid.length - 1) {
                sql.addSql("     , ");
            }
        }

        sql.addSql("   )");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CAC_SID = ?");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CVW_DSP = ?");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CIF_SID = CIR_INF.CIF_SID");
        sql.addSql("  order by");
        sql.addSql("    CIR_INF.CIF_ADATE desc ");

        sql.addIntValue(bean.getCacSid());
        sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);

        return sql;
    }

    /**
     * <br>[機  能] SQLを作成する(回覧板情報(受信)件数)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @return SqlBuffer
     */
    private SqlBuffer __createJusinCountSql(CirSearchModel bean) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");
        sql.addSql("    count(*) as CNT");
        sql.addSql("  from");
        sql.addSql("    CIR_VIEW");
        sql.addSql("  where");
        sql.addSql("    CIR_VIEW.CVW_DSP = ?");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CAC_SID = ?");

        sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
        sql.addIntValue(bean.getCacSid());

        return sql;
    }

    /**
     * <br>[機  能] SQLを作成する(回覧板情報(受信)取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param limit 最大表示件数
     * @param getKbn 0=件数、1=一覧表示、2=未確認・受信一覧
     * @param offset 取得開始位置
     * @return SqlBuffer
     */
    private SqlBuffer __createJusinSql(CirSearchModel bean, int getKbn, int limit, int offset) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("    count(*) as CNT");

        } else {
            //一覧表示
            sql.addSql("    CIR_VIEW.CVW_CONF as CVW_CONF, ");
            sql.addSql("    VIEW_ACCOUNT.CAC_SID as CAC_SID, ");
            sql.addSql("    VIEW_ACCOUNT.CAC_NAME as ACCOUNT_NAME, ");
            sql.addSql("    VIEW_USR_INF.USI_SEI as VIEW_USI_SEI,  ");
            sql.addSql("    VIEW_USR_INF.USI_MEI as VIEW_USI_MEI, ");
            sql.addSql("    VIEW_USR.USR_UKO_FLG as VIEW_UKO_FLG, ");
            sql.addSql("    INF.CIF_SID as CIF_SID, ");
            sql.addSql("    INF.CIF_TITLE as CIF_TITLE, ");
            sql.addSql("    INF.CIF_ADATE as CIF_ADATE, ");
            sql.addSql("    INF.CIF_SHOW as CIF_SHOW, ");
            sql.addSql("    SEND_ACCOUNT.CAC_JKBN as CAC_JKBN, ");
            sql.addSql("    SEND_ACCOUNT.CAC_NAME as CAC_NAME, ");
            sql.addSql("    ALL_CT.OPEN_CNT as OPEN_CNT, ");
            sql.addSql("    ALL_CT.ALL_CNT as ALL_CNT, ");
            sql.addSql("    SEND_USR_INF.USI_SEI as USI_SEI,  ");
            sql.addSql("    SEND_USR_INF.USI_MEI as USI_MEI,  ");
            sql.addSql("    SEND_USR.USR_UKO_FLG as USR_UKO_FLG  ");

        }

        sql.addSql("  from");
        sql.addSql("  CIR_VIEW");
        sql.addSql("  left join");
        sql.addSql("    CIR_ACCOUNT VIEW_ACCOUNT");
        sql.addSql("     on CIR_VIEW.CAC_SID = VIEW_ACCOUNT.CAC_SID");
        sql.addSql("       left join");
        sql.addSql("         CMN_USRM_INF VIEW_USR_INF");
        sql.addSql("       on");
        sql.addSql("         VIEW_ACCOUNT.USR_SID = VIEW_USR_INF.USR_SID");
        sql.addSql("       left join");
        sql.addSql("         CMN_USRM VIEW_USR");
        sql.addSql("       on");
        sql.addSql("         VIEW_ACCOUNT.USR_SID = VIEW_USR.USR_SID,");
        sql.addSql("  CIR_ACCOUNT SEND_ACCOUNT");
        sql.addSql("  left join");
        sql.addSql("     CMN_USRM_INF SEND_USR_INF");
        sql.addSql("   on");
        sql.addSql("     SEND_ACCOUNT.USR_SID = SEND_USR_INF.USR_SID");
        sql.addSql("  left join");
        sql.addSql("     CMN_USRM SEND_USR");
        sql.addSql("   on");
        sql.addSql("     SEND_ACCOUNT.USR_SID = SEND_USR.USR_SID,");
        sql.addSql("  CIR_INF INF");

        if (getKbn != GET_COUNT) {
            sql.addSql("    left join");
            sql.addSql("    (");
            sql.addSql("    select");
            sql.addSql("      CIF_SID as ALL_CIF_SID,");
            sql.addSql("      sum(case when CVW_CONF = ? then 1 else 0 end) as OPEN_CNT,");
            sql.addSql("      count(CAC_SID) as ALL_CNT");
            sql.addSql("    from");
            sql.addSql("      CIR_VIEW");
            sql.addSql("    where");
            sql.addSql("      CIF_SID in (");
            sql.addSql("        select CIF_SID from CIR_VIEW");
            sql.addSql("        where CAC_SID = ?");
            sql.addSql("        and CVW_DSP = ?");
            sql.addSql("      )");
            sql.addSql("    group by");
            sql.addSql("      CIF_SID");
            sql.addSql("    ) ALL_CT");
            sql.addSql("    on INF.CIF_SID = ALL_CT.ALL_CIF_SID");
            sql.addIntValue(GSConstCircular.CONF_OPEN);
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
        }

        sql.addSql("  where");
        sql.addSql("    CIR_VIEW.CAC_SID = ?");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CVW_DSP = ?");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CIF_SID = INF.CIF_SID");
        sql.addSql("  and");
        sql.addSql("    INF.CIF_AUID = SEND_ACCOUNT.CAC_SID");

        sql.addIntValue(bean.getCacSid());
        sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);

        if (getKbn == GET_UNOPEN_LIST) {
            //未確認・受信一覧
            sql.addSql("  and");
            sql.addSql("    CIR_VIEW.CVW_CONF = ?");
            sql.addIntValue(GSConstCircular.CONF_UNOPEN);
        }

        //検索時条件
        if (ValidateUtil.isNumber(bean.getGroupSid())
                && Integer.valueOf(bean.getGroupSid()) > GSConstCommon.NUM_INIT
                && bean.getHassinSid() == GSConstCommon.NUM_INIT) {
            //発信者グループのみ選択時(通常のグループ)
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CMN_BELONGM");
            sql.addSql("                left join");
            sql.addSql("                  CIR_ACCOUNT");
            sql.addSql("                   on CMN_BELONGM.USR_SID = CIR_ACCOUNT.USR_SID");
            sql.addSql("            where");
            sql.addSql("              CMN_BELONGM.GRP_SID = ?");
            sql.addSql("            and CIR_ACCOUNT.CAC_SID = INF.CIF_AUID)");
            sql.addIntValue(Integer.valueOf(bean.getGroupSid()));
        } else if (__isMyGroupSid(bean.getGroupSid())
                && Integer.parseInt(bean.getGroupSid().substring(1)) > GSConstCommon.NUM_INIT
                && bean.getHassinSid() == GSConstCommon.NUM_INIT) {
            //発信者グループのみ選択時(マイグループ)
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CMN_MY_GROUP_MS");
            sql.addSql("                left join");
            sql.addSql("                  CIR_ACCOUNT");
            sql.addSql("                   on CMN_MY_GROUP_MS.MGM_SID = CIR_ACCOUNT.USR_SID");
            sql.addSql("            where");
            sql.addSql("              CMN_MY_GROUP_MS.MGP_SID = ?");
            sql.addIntValue(Integer.valueOf(bean.getGroupSid().substring(1)));
            sql.addSql("            and ");
            sql.addSql("              ( ");
            sql.addSql("              CMN_MY_GROUP_MS.USR_SID=? or ");
            sql.addIntValue(bean.getSessionUserSid());
            sql.addSql("              CMN_MY_GROUP_MS.MGP_SID in ( ");
            sql.addSql("                select MGP_SID ");
            sql.addSql("                from CMN_MY_GROUP_SHARE ");
            sql.addSql("                where  ");
            sql.addSql("                  MGS_USR_SID = ? ");
            sql.addIntValue(bean.getSessionUserSid());
            sql.addSql("                  or MGS_GRP_SID in ( ");
            sql.addSql("                    select GRP_SID from CMN_BELONGM ");
            sql.addSql("                    where USR_SID=? ");
            sql.addIntValue(bean.getSessionUserSid());
            sql.addSql("                 ) ");
            sql.addSql("               ) ");
            sql.addSql("              ) ");
            sql.addSql("            and CIR_ACCOUNT.CAC_SID = INF.CIF_AUID)");
        } else if (__isCirAccount(bean.getGroupSid())
                && bean.getHassinSid() == GSConstCommon.NUM_INIT) {
            //発信者グループのみ選択時(代表アカウント)
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CIR_ACCOUNT");
            sql.addSql("            where");
            sql.addSql("              CIR_ACCOUNT.USR_SID IS NULL");
            sql.addSql("            and CIR_ACCOUNT.CAC_SID = INF.CIF_AUID)");
        }

        //発信者選択時
        if (bean.getHassinSid() > GSConstCommon.NUM_INIT) {
            sql.addSql("  and");
            sql.addSql("    INF.CIF_AUID = ?");
            sql.addIntValue(bean.getHassinSid());
        }

        //キーワード入力時の検索条件
        __createKeyWord(bean, sql, Integer.valueOf(GSConstCircular.MODE_JUSIN));

        if (getKbn == GET_LIST) {
            //一覧表示
            //ソート
            sql.addSql(" order by ");
            __createOder(sql, bean.getSortKey(), bean.getOrderKey(),
                    Integer.parseInt(GSConstCircular.MODE_JUSIN));

            if (bean.getSortKey2() > GSConstCommon.NUM_INIT
            && bean.getOrderKey2() > GSConstCommon.NUM_INIT) {
                sql.addSql(" , ");
                __createOder(sql, bean.getSortKey2(), bean.getOrderKey2(),
                        Integer.parseInt(GSConstCircular.MODE_JUSIN));
            }
            sql.addSql("    , INF.CIF_SID ASC");

        } else if (getKbn == GET_UNOPEN_LIST) {
            //未確認・受信一覧
            //ソート
            sql.addSql("  order by");
            sql.addSql("    INF.CIF_ADATE desc ");
        }

        int setOffset = 0;
        int setLimit = 0;
        if (offset > 0) {
            setOffset = offset;
        }
        if (limit > 0) {
            setLimit = limit;
        }
        sql.setPagingValue(setOffset, setLimit);

        return sql;
    }

    /**
     * <br>[機  能] 回覧板情報(送信済み)の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @return int 回覧板情報(送信済み)の件数
     * @throws SQLException SQL実行例外
     */
    public int getSousinCount(CirSearchModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __createSousinSql(bean, GET_COUNT, 0, 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 回覧板情報(送信済み)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getSousinList(CirSearchModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __createSousinSql(
                    bean, GET_LIST, bean.getLimit(), (bean.getStart() - 1));

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setJsFlg(GSConstCircular.MODE_SOUSIN);
                retMdl.setCifSid(rs.getInt("CIF_SID"));
                retMdl.setCifTitle(rs.getString("CIF_TITLE"));
                retMdl.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    retMdl.setCacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    retMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                } else {
                    retMdl.setCacName(rs.getString("CAC_NAME"));
                }

                retMdl.setOpenCount(rs.getInt("OPEN_CNT"));
                retMdl.setAllCount(rs.getInt("ALL_CNT"));
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板情報(送信済み)の回覧板SIDを全て取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean Cir010SearchModel
     * @param cirSid 現在表示中の回覧板SID
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public CirHashControlModel getSousinAllList(CirSearchModel bean, int cirSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirHashControlModel ret = new CirHashControlModel();
        HashMap<Integer, CircularDspModel> retMap = new HashMap <Integer, CircularDspModel>();
        con = getCon();

        int rowCount = 0;
        int selectRow = 0;

        try {
            //SQL文
            SqlBuffer sql = __createSousinSql(bean, GET_LIST, 0, 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CircularDspModel cdMdl = null;
            while (rs.next()) {
                rowCount++;
                cdMdl = new CircularDspModel();
                cdMdl.setCifSid(rs.getInt("CIF_SID"));
                retMap.put(rowCount, cdMdl);

                if (cirSid == rs.getInt("CIF_SID")) {
                    selectRow = rowCount;
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        ret.setRowNum(selectRow);
        ret.setMap(retMap);

        return ret;
    }

    /**
     * <br>[機  能] SQLを作成する(回覧板情報(送信済み)取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param getKbn 0=カウント、1=一覧表示
     * @param limit 最大件数
     * @param offset 取得開始位置
     * @return SqlBuffer
     */
    private SqlBuffer __createSousinSql(CirSearchModel bean, int getKbn, int limit, int offset) {

        boolean keyWordSearch = false;
        List<String>  keywordList = bean.getKeyWord();
        if (keywordList != null && keywordList.size() > 0) {
            keyWordSearch = true;
        }
        //回覧先選択時
        String[] kairansakiSid = bean.getKairansakiSid();
        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from");
            sql.addSql("   CIR_INF INF ");
            sql.addSql("  where");
            sql.addSql("    INF.CIF_JKBN = ?");
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
            sql.addSql("  and");
            sql.addSql("    INF.CIF_AUID = ?");
            sql.addIntValue(bean.getCacSid());

            //キーワード用クエリ、閲覧先クエリに備えてand配置
            if (keyWordSearch || (kairansakiSid != null && kairansakiSid.length > 0)) {
                sql.addSql("  and");
            }

        } else if (getKbn == GET_LIST) {
            //一覧表示
            sql.addSql("  INF.CIF_SID,");
            sql.addSql("  INF.CIF_TITLE,");
            sql.addSql("  INF.CIF_ADATE,");
            sql.addSql("  INF.USI_SEI,");
            sql.addSql("  INF.USI_MEI,");
            sql.addSql("  INF.USI_SEI_KN, ");
            sql.addSql("  INF.USI_MEI_KN, ");
            sql.addSql("  INF.CAC_NAME,");
            sql.addSql("  INF.USR_UKO_FLG,");
            sql.addSql("  sum(case");
            sql.addSql("      when CIR_VIEW.CVW_CONF = 1 then 1");
            sql.addSql("          else 0");
            sql.addSql("          end) as OPEN_CNT,");
            sql.addSql("  count(CIR_VIEW.CAC_SID) as ALL_CNT");
            sql.addSql(" from");
            sql.addSql("    (");
            sql.addSql("    select");
            sql.addSql("      CIR_INF.CIF_SID,");
            sql.addSql("      CIR_INF.CIF_TITLE,");
            sql.addSql("      CIR_INF.CIF_VALUE,");
            sql.addSql("      CIR_INF.CIF_ADATE,");
            sql.addSql("      CIR_INF.GRP_SID,");
            sql.addSql("      CIR_INF.CIF_AUID,");
            sql.addSql("      CIR_INF.CIF_JKBN,");
            sql.addSql("      CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("      CIR_ACCOUNT.CAC_SID,");
            sql.addSql("      CMN_USRM_INF.USI_SEI,");
            sql.addSql("      CMN_USRM_INF.USI_MEI,");
            sql.addSql("      CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("      CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("      CMN_USRM.USR_UKO_FLG");
            sql.addSql("    from");
            sql.addSql("      CIR_INF,");
            sql.addSql("      CIR_ACCOUNT");
            sql.addSql("        left join");
            sql.addSql("          CMN_USRM_INF");
            sql.addSql("        on");
            sql.addSql("          CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("        left join");
            sql.addSql("          CMN_USRM");
            sql.addSql("        on");
            sql.addSql("          CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  where");
            sql.addSql("    CIR_INF.CIF_AUID = CIR_ACCOUNT.CAC_SID");
            sql.addSql("  and");
            sql.addSql("    CIR_INF.CIF_JKBN = ?");
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
            sql.addSql("  and");
            sql.addSql("    CIR_INF.CIF_AUID = ?");
            sql.addIntValue(bean.getCacSid());
            sql.addSql("  order by CIR_INF.CIF_SID");
            sql.addSql("    ) INF");
            sql.addSql("    left join");
            sql.addSql("      CIR_VIEW");
            sql.addSql("    on");
            sql.addSql("      CIR_VIEW.CIF_SID = INF.CIF_SID");
            if (keyWordSearch || (kairansakiSid != null && kairansakiSid.length > 0)) {
                sql.addSql("  where");
            }
        }

        //キーワード入力時の検索条件
        __createKeyWord(bean, sql, Integer.valueOf(GSConstCircular.MODE_SOUSIN));


        if (kairansakiSid != null && kairansakiSid.length > 0) {
            //キーワード検索している場合は条件追記のためandを配置
            if (keyWordSearch) {
                sql.addSql("  and");
            }
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CIR_VIEW");
            sql.addSql("            where");
            sql.addSql("              CAC_SID in (");
            for (int i = 0; i < kairansakiSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(kairansakiSid[i], 0));

                if (i < kairansakiSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("              )");
            sql.addSql("              and CIR_VIEW.CIF_SID = INF.CIF_SID)");
        }

        if (getKbn == GET_LIST) {
            //キーワード検索済み or 回覧先指定済みの場合先の条件に追加するのでandを配置

            sql.addSql(" group by");
            sql.addSql("        INF.CIF_SID,");
            sql.addSql("        INF.CIF_TITLE,");
            sql.addSql("        INF.CIF_ADATE,");
            sql.addSql("        INF.USI_SEI,");
            sql.addSql("        INF.USI_MEI,");
            sql.addSql("        INF.USI_SEI_KN, ");
            sql.addSql("        INF.USI_MEI_KN, ");
            sql.addSql("        INF.CAC_NAME,");
            sql.addSql("        INF.USR_UKO_FLG");

            //ソート
            sql.addSql(" order by ");
            __createOder(sql, bean.getSortKey(), bean.getOrderKey(),
                    Integer.parseInt(GSConstCircular.MODE_SOUSIN));

            if (bean.getSortKey2() > GSConstCommon.NUM_INIT
            && bean.getOrderKey2() > GSConstCommon.NUM_INIT) {
                sql.addSql(" , ");
                __createOder(sql, bean.getSortKey2(), bean.getOrderKey2(),
                        Integer.parseInt(GSConstCircular.MODE_SOUSIN));
            }
            sql.addSql("    , INF.CIF_SID ASC");
        }

        int setOffset = 0;
        int setLimit = 0;
        if (offset > 0) {
            setOffset = offset;
        }
        if (limit > 0) {
            setLimit = limit;
        }
        sql.setPagingValue(setOffset, setLimit);
        return sql;
    }

    /**
     * <br>[機  能] SQL(キーワード入力時の検索条件)を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CirSearchModel
     * @param sql SqlBuffer
     * @param mode 回覧板モード
     */
    private void __createKeyWord(CirSearchModel bean, SqlBuffer sql, int mode) {

        //キーワード入力時
        List<String>  keywordList = bean.getKeyWord();
        if (keywordList != null && keywordList.size() > 0) {

            String keywordJoin = "  and";
            if (bean.getWordKbn() == GSConstCircular.KEY_WORD_KBN_OR) {
                keywordJoin = "   or";
            }

            //検索対象の「タイトル」がチェック済みの場合
            if (bean.isTargetTitle()) {
                //送信済みはSQLの関係上'and'が不要
                if (mode != Integer.valueOf(GSConstCircular.MODE_SOUSIN)) {
                    sql.addSql("  and");
                }

                if (bean.isTargetBody()) {
                    sql.addSql("    (");
                }
                sql.addSql("      (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       INF.CIF_TITLE like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("      )");
            }

            if (bean.isTargetBody()) {
                if (bean.isTargetTitle()) {
                    sql.addSql("      or");
                } else if (mode !=  Integer.valueOf(GSConstCircular.MODE_SOUSIN)) {
                    //送信済みはSQLの関係上'and'が不要
                    sql.addSql("      and");
                }

                sql.addSql("      (");
                for (int i = 0; i < keywordList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(keywordJoin);
                    }
                    sql.addSql("       INF.CIF_VALUE like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList.get(i))
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }
                sql.addSql("      )");

                if (bean.isTargetTitle()) {
                    sql.addSql("    )");
                }
            }
        }
    }

    /**
     * <br>[機  能] order by 以下のSQLを作成する(回覧板情報取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sort ソートキー
     * @param order オーダーキー
     * @param kbn フォルダ区分
     */
    private void __createOder(SqlBuffer sql, int sort, int order, int kbn) {

        //昇順,降順指定
        String strOrder = "DESC";
        if (order != 1) {
            strOrder = "ASC";
        }

        switch (sort) {
            case GSConstCircular.SORT_TITLE:
                //タイトル
                sql.addSql("     INF.CIF_TITLE " + strOrder);
                break;

            case GSConstCircular.SORT_DATE:
                //登録日時
                sql.addSql("     INF.CIF_ADATE " + strOrder);
                break;

            case GSConstCircular.SORT_USER:
                //登録者名
                if (GSConstCircular.MODE_JUSIN.equals(String.valueOf(kbn))) {
                  sql.addSql(" (case when SEND_USR_INF.USR_SID is null then 1 else 0 end) ");
                  sql.addSql(strOrder + ",");
                  sql.addSql("     SEND_USR_INF.USI_SEI_KN " + strOrder + ",");
                  sql.addSql("     SEND_USR_INF.USI_MEI_KN " + strOrder + ",");
                  sql.addSql("     CAC_NAME " + strOrder);

                } else if (GSConstCircular.MODE_SOUSIN.equals(String.valueOf(kbn))) {
                  sql.addSql(" (case when INF.USI_SEI is null then 1 else 0 end) ");
                    sql.addSql(strOrder + ",");
                  sql.addSql("     INF.USI_SEI_KN " + strOrder + ",");
                  sql.addSql("     INF.USI_MEI_KN " + strOrder + ",");
                  sql.addSql("     CAC_NAME " + strOrder);
                }
               break;

            default:
                break;
        }
    }

    /**
     * <br>[機  能] 回覧板情報(ゴミ箱)の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @return int 回覧板情報(ゴミ箱)の件数
     * @throws SQLException SQL実行例外
     */
    public int getGomiCount(CirSearchModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __createGomiSql(bean, GET_COUNT, 0, 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 回覧板情報(ラベル)の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @return int 回覧板情報(ゴミ箱)の件数
     * @throws SQLException SQL実行例外
     */
    public int getEncLabelCount(CirSearchModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __createLabelSql(bean, GET_COUNT, 0, 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 回覧板情報(ゴミ箱)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getGomiList(CirSearchModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __createGomiSql(
                    bean, GET_LIST, bean.getLimit(), (bean.getStart() - 1));



            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);

            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setJsFlg(rs.getString("JS_FLG"));
                retMdl.setCvwConf(rs.getInt("CVW_CONF"));
                retMdl.setCifSid(rs.getInt("CIF_SID"));
                retMdl.setCifTitle(rs.getString("CIF_TITLE"));
                retMdl.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    retMdl.setCacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    retMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                } else {
                    retMdl.setCacName(rs.getString("CAC_NAME"));
                }
                retMdl.setCacJkbn(rs.getInt("CAC_JKBN"));
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板情報(ラベル)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getEncLabelList(CirSearchModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = __createLabelSql(
                    bean, GET_LIST, bean.getLimit(), (bean.getStart() - 1));
            pstmt = con.prepareStatement(sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            sql.setParameter(pstmt);

            log__.info(sql.toLogString());

            rs = pstmt.executeQuery();
            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setJsFlg(rs.getString("JS_FLG"));
                retMdl.setCvwConf(rs.getInt("CVW_CONF"));
                retMdl.setCifSid(rs.getInt("CIF_SID"));
                retMdl.setCifTitle(rs.getString("CIF_TITLE"));
                retMdl.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    retMdl.setCacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    retMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                } else {
                    retMdl.setCacName(rs.getString("CAC_NAME"));
                }
                retMdl.setCacJkbn(rs.getInt("CAC_JKBN"));
                retMdl.setOpenCount(rs.getInt("OPEN_CNT"));
                retMdl.setAllCount(rs.getInt("ALL_CNT"));
                retMdl.setCifShow(rs.getInt("CIF_SHOW"));
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板情報(ゴミ箱)の回覧板SIDを全て取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean Cir010SearchModel
     * @param cirSid 現在表示中の回覧板SID
     * @param jsFlg 受信送信フラグ
     * @return CirHashControlModel
     * @throws SQLException SQL実行例外
     */
    public CirHashControlModel getGomiAllList(
        CirSearchModel bean,
        int cirSid,
        String jsFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirHashControlModel ret = new CirHashControlModel();
        HashMap<Integer, CircularDspModel> retMap = new HashMap<Integer, CircularDspModel>();
        con = getCon();

        int rowCount = 0;
        int selectRow = 0;

        try {
            //SQL文
            SqlBuffer sql = __createGomiSql(bean, GET_LIST, 0, 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CircularDspModel cdMdl = null;
            while (rs.next()) {
                rowCount++;
                cdMdl = new CircularDspModel();
                cdMdl.setCifSid(rs.getInt("CIF_SID"));
                cdMdl.setJsFlg(rs.getString("JS_FLG"));
                retMap.put(rowCount, cdMdl);

                if (cirSid == rs.getInt("CIF_SID") && jsFlg.equals(rs.getString("JS_FLG"))) {
                    selectRow = rowCount;
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        ret.setRowNum(selectRow);
        ret.setMap(retMap);

        return ret;
    }

    /**
     * <br>[機  能] 回覧板情報(ラベル)の回覧板SIDを全て取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean Cir010SearchModel
     * @param cirSid 現在表示中の回覧板SID
     * @param jsFlg 受信送信フラグ
     * @return CirHashControlModel
     * @throws SQLException SQL実行例外
     */
    public CirHashControlModel getLabelAllList(
        CirSearchModel bean,
        int cirSid,
        String jsFlg) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CirHashControlModel ret = new CirHashControlModel();
        HashMap<Integer, CircularDspModel> retMap = new HashMap<Integer, CircularDspModel>();
        con = getCon();

        int rowCount = 0;
        int selectRow = 0;

        try {
            //SQL文
            SqlBuffer sql = __createLabelSql(bean, GET_LIST, 0, 0);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            CircularDspModel cdMdl = null;
            while (rs.next()) {
                rowCount++;
                cdMdl = new CircularDspModel();
                cdMdl.setCifSid(rs.getInt("CIF_SID"));
                cdMdl.setJsFlg(rs.getString("JS_FLG"));
                retMap.put(rowCount, cdMdl);

                if (cirSid == rs.getInt("CIF_SID") && jsFlg.equals(rs.getString("JS_FLG"))) {
                    selectRow = rowCount;
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        ret.setRowNum(selectRow);
        ret.setMap(retMap);

        return ret;
    }


    /**
     * <br>[機  能] SQLを作成する(回覧板情報(ゴミ箱)取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param getKbn 0=カウント、1=一覧表示
     * @param limit 上限数
     * @param offset 取得開始位置
     * @return SqlBuffer
     */
    private SqlBuffer __createGomiSql(CirSearchModel bean, int getKbn, int limit, int offset) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウントの合計
            sql.addSql("    sum(C_VIEW.CNT) as CNT");

        } else {
            //一覧表示
            sql.addSql("    C_VIEW.JS_FLG,");
            sql.addSql("    C_VIEW.CVW_CONF,");
            sql.addSql("    C_VIEW.CIF_SID,");
            sql.addSql("    C_VIEW.CIF_TITLE,");
            sql.addSql("    C_VIEW.CIF_ADATE,");
            sql.addSql("    C_VIEW.USI_SEI,");
            sql.addSql("    C_VIEW.USI_MEI,");
            sql.addSql("    C_VIEW.USR_UKO_FLG,");
            sql.addSql("    C_VIEW.CAC_JKBN,");
            sql.addSql("    C_VIEW.CAC_NAME,");
            sql.addSql("    C_VIEW.USI_SEI,");
            sql.addSql("    C_VIEW.USI_MEI");
        }

        sql.addSql(" from");
        sql.addSql("   (");

        //受信//////////////////////////////////////////////////////////////
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("    count(INF.CIF_SID) as CNT");

        } else {
            //一覧表示
            sql.addSql("    '" + GSConstCircular.MODE_JUSIN + "' as JS_FLG,");
            sql.addSql("    CIR_VIEW.CVW_CONF,");
            sql.addSql("    INF.CIF_SID,");
            sql.addSql("    INF.CIF_TITLE,");
            sql.addSql("    INF.CIF_ADATE,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("    CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("    CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("    CIR_ACCOUNT.CAC_NAME");
        }

        sql.addSql("  from");
        sql.addSql("    CIR_VIEW,");
        sql.addSql("    CIR_ACCOUNT");

        sql.addSql("      left join");
        sql.addSql("        CMN_USRM_INF");
        sql.addSql("      on");
        sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("      left join");
        sql.addSql("        CMN_USRM");
        sql.addSql("      on");
        sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID,");

        sql.addSql("    CIR_INF INF");
        sql.addSql("  where");
        sql.addSql("    CIR_VIEW.CAC_SID = ?");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CVW_DSP = ?");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CIF_SID = INF.CIF_SID");
        sql.addSql("  and");
        sql.addSql("    INF.CIF_AUID = CIR_ACCOUNT.CAC_SID");

        sql.addIntValue(bean.getCacSid());
        sql.addIntValue(GSConstCircular.DSPKBN_DSP_NG);

        //検索時条件
        if (ValidateUtil.isNumber(bean.getGroupSid())
                && Integer.valueOf(bean.getGroupSid()) > GSConstCommon.NUM_INIT
                && bean.getHassinSid() == GSConstCommon.NUM_INIT) {
            //発信者グループのみ選択時(通常のグループ)
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CMN_BELONGM");
            sql.addSql("                left join");
            sql.addSql("                  CIR_ACCOUNT");
            sql.addSql("                   on CMN_BELONGM.USR_SID = CIR_ACCOUNT.USR_SID");
            sql.addSql("            where");
            sql.addSql("              CMN_BELONGM.GRP_SID = ?");
            sql.addSql("            and CIR_ACCOUNT.CAC_SID = INF.CIF_AUID)");
            sql.addIntValue(Integer.valueOf(bean.getGroupSid()));
        } else if (__isMyGroupSid(bean.getGroupSid())
                && Integer.parseInt(bean.getGroupSid().substring(1)) > GSConstCommon.NUM_INIT
                && bean.getHassinSid() == GSConstCommon.NUM_INIT) {
            //発信者グループのみ選択時(マイグループ)
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CMN_MY_GROUP_MS");
            sql.addSql("                left join");
            sql.addSql("                  CIR_ACCOUNT");
            sql.addSql("                   on CMN_MY_GROUP_MS.MGM_SID = CIR_ACCOUNT.USR_SID");
            sql.addSql("            where");
            sql.addSql("              CMN_MY_GROUP_MS.MGP_SID = ?");
            sql.addIntValue(Integer.valueOf(bean.getGroupSid().substring(1)));
            sql.addSql("            and ");
            sql.addSql("              ( ");
            sql.addSql("              CMN_MY_GROUP_MS.USR_SID=? or ");
            sql.addIntValue(bean.getSessionUserSid());
            sql.addSql("              CMN_MY_GROUP_MS.MGP_SID in ( ");
            sql.addSql("                select MGP_SID ");
            sql.addSql("                from CMN_MY_GROUP_SHARE ");
            sql.addSql("                where  ");
            sql.addSql("                  MGS_USR_SID = ? ");
            sql.addIntValue(bean.getSessionUserSid());
            sql.addSql("                  or MGS_GRP_SID in ( ");
            sql.addSql("                    select GRP_SID from CMN_BELONGM ");
            sql.addSql("                    where USR_SID=? ");
            sql.addIntValue(bean.getSessionUserSid());
            sql.addSql("                 ) ");
            sql.addSql("               ) ");
            sql.addSql("              ) ");
            sql.addSql("            and CIR_ACCOUNT.CAC_SID = INF.CIF_AUID)");
        } else if (__isCirAccount(bean.getGroupSid())
                && bean.getHassinSid() == GSConstCommon.NUM_INIT) {
            //発信者グループのみ選択時(代表アカウント)
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CIR_ACCOUNT");
            sql.addSql("            where");
            sql.addSql("              CIR_ACCOUNT.USR_SID IS NULL");
            sql.addSql("            and CIR_ACCOUNT.CAC_SID = INF.CIF_AUID)");
        }

        //発信者選択時
        if (bean.getHassinSid() > GSConstCommon.NUM_INIT) {
            sql.addSql("  and");
            sql.addSql("    INF.CIF_AUID = ?");
            sql.addIntValue(bean.getHassinSid());
        }

        //キーワード入力時の検索条件
        __createKeyWord(bean, sql, Integer.valueOf(GSConstCircular.MODE_GOMI));

        //回覧先選択時
        String[] kairansakiSid = bean.getKairansakiSid();
        if (kairansakiSid != null && kairansakiSid.length > 0) {
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CIR_VIEW");
            sql.addSql("            where");
            sql.addSql("              CAC_SID in (");
            for (int i = 0; i < kairansakiSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(kairansakiSid[i], 0));

                if (i < kairansakiSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("              )");
            sql.addSql("              and CIR_VIEW.CIF_SID = INF.CIF_SID)");
        }

        sql.addSql(" union all");


        //送信//////////////////////////////////////////////////////////////

        boolean keyWordSearch = false;
        List<String>  keywordList = bean.getKeyWord();
        if (keywordList != null && keywordList.size() > 0) {
            keyWordSearch = true;
        }
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from");
            sql.addSql("   CIR_INF INF ");
            sql.addSql("  where");
            sql.addSql("    INF.CIF_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    INF.CIF_AUID = ?");
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_NG);
            sql.addIntValue(bean.getCacSid());

            //キーワード用クエリ、閲覧先クエリに備えてand配置
            if (keyWordSearch || (kairansakiSid != null && kairansakiSid.length > 0)) {
                sql.addSql("  and");
            }

        } else if (getKbn == GET_LIST) {
            //一覧表示
            sql.addSql("    '" + GSConstCircular.MODE_SOUSIN + "' as JS_FLG,");
            sql.addSql("    '" + GSConstCircular.CONF_OPEN + "' as CVW_CONF,");
            sql.addSql("    INF.CIF_SID,");
            sql.addSql("    INF.CIF_TITLE,");
            sql.addSql("    INF.CIF_ADATE,");
            sql.addSql("    INF.USI_SEI,");
            sql.addSql("    INF.USI_MEI,");
            sql.addSql("    INF.USI_SEI_KN,");
            sql.addSql("    INF.USI_MEI_KN,");
            sql.addSql("    INF.USR_UKO_FLG,");
            sql.addSql("    INF.CAC_JKBN,");
            sql.addSql("    INF.CAC_NAME");
            sql.addSql(" from");
            sql.addSql("    (");
            sql.addSql("    select");
            sql.addSql("      CIR_INF.CIF_SID,");
            sql.addSql("      CIR_INF.CIF_TITLE,");
            sql.addSql("      CIR_INF.CIF_VALUE,");
            sql.addSql("      CIR_INF.CIF_ADATE,");
            sql.addSql("      CIR_INF.GRP_SID,");
            sql.addSql("      CIR_INF.CIF_AUID,");
            sql.addSql("      CIR_INF.CIF_JKBN,");
            sql.addSql("      CMN_USRM_INF.USI_SEI,");
            sql.addSql("      CMN_USRM_INF.USI_MEI,");
            sql.addSql("      CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("      CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("      CMN_USRM.USR_UKO_FLG,");
            sql.addSql("      CIR_ACCOUNT.CAC_SID as USID,");
            sql.addSql("      CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("      CIR_ACCOUNT.CAC_JKBN");
            sql.addSql("    from");
            sql.addSql("      CIR_INF,");
            sql.addSql("      CIR_ACCOUNT");
            sql.addSql("        left join");
            sql.addSql("          CMN_USRM_INF");
            sql.addSql("        on");
            sql.addSql("          CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("        left join");
            sql.addSql("          CMN_USRM");
            sql.addSql("        on");
            sql.addSql("          CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  where");
            sql.addSql("    CIR_INF.CIF_AUID = CIR_ACCOUNT.CAC_SID");
            sql.addSql("  and");
            sql.addSql("    CIR_INF.CIF_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_INF.CIF_AUID = ?");
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_NG);
            sql.addIntValue(bean.getCacSid());
            sql.addSql("  order by CIR_INF.CIF_SID");
            sql.addSql("    ) INF");

            //キーワード用クエリ、閲覧先クエリに備えてand配置
            if (keyWordSearch || (kairansakiSid != null && kairansakiSid.length > 0)) {
                sql.addSql("  where");
            }
        }

        //キーワード入力時の検索条件
        __createKeyWord(bean, sql, Integer.valueOf(GSConstCircular.MODE_SOUSIN));


        if (kairansakiSid != null && kairansakiSid.length > 0) {
            //キーワード検索している場合は条件追記のためandを配置
            if (keyWordSearch) {
                sql.addSql("  and");
            }
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CIR_VIEW");
            sql.addSql("            where");
            sql.addSql("              CAC_SID in (");
            for (int i = 0; i < kairansakiSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(kairansakiSid[i], 0));

                if (i < kairansakiSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("              )");
            sql.addSql("              and CIR_VIEW.CIF_SID = INF.CIF_SID)");
        }


        sql.addSql("   ) C_VIEW");

        if (getKbn == GET_LIST) {
            //一覧表示
            //ソート
            sql.addSql(" order by ");
            __createGomiOder(sql, bean.getSortKey(), bean.getOrderKey());

            if (bean.getSortKey2() > GSConstCommon.NUM_INIT
            && bean.getOrderKey2() > GSConstCommon.NUM_INIT) {
                sql.addSql(" , ");
                __createGomiOder(sql, bean.getSortKey2(), bean.getOrderKey2());
            }
            sql.addSql("    , C_VIEW.CIF_SID ASC");
        }

        int setOffset = 0;
        int setLimit = 0;
        if (offset > 0) {
            setOffset = offset;
        }
        if (limit > 0) {
            setLimit = limit;
        }
        sql.setPagingValue(setOffset, setLimit);
        return sql;
    }


    /**
     * <br>[機  能] SQLを作成する(回覧板情報(ラベル)取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param getKbn 0=カウント、1=一覧表示
     * @param limit 取得件数
     * @param offset 取得開始位置
     * @return SqlBuffer
     */
    private SqlBuffer __createLabelSql(CirSearchModel bean, int getKbn, int limit, int offset) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウントの合計
            sql.addSql("    sum(C_VIEW.CNT) as CNT");

        } else {
            //一覧表示
            sql.addSql("    C_VIEW.JS_FLG,");
            sql.addSql("    C_VIEW.CVW_CONF,");
            sql.addSql("    C_VIEW.CIF_SHOW,");
            sql.addSql("    C_VIEW.CIF_SID,");
            sql.addSql("    C_VIEW.CIF_TITLE,");
            sql.addSql("    C_VIEW.CIF_ADATE,");
            sql.addSql("    C_VIEW.USI_SEI,");
            sql.addSql("    C_VIEW.USI_MEI,");
            sql.addSql("    C_VIEW.USR_UKO_FLG,");
            sql.addSql("    C_VIEW.CAC_JKBN,");
            sql.addSql("    C_VIEW.CAC_NAME,");
            sql.addSql("    C_VIEW.USI_SEI,");
            sql.addSql("    C_VIEW.USI_MEI,");
            sql.addSql("    C_VIEW.OPEN_CNT,");
            sql.addSql("    C_VIEW.ALL_CNT");
        }

        sql.addSql(" from");
        sql.addSql("   (");

        //受信//////////////////////////////////////////////////////////////
        sql.addSql("  (select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("    count(INF.CIF_SID) as CNT");

        } else {
            //一覧表示
            sql.addSql("    '" + GSConstCircular.MODE_JUSIN + "' as JS_FLG,");
            sql.addSql("    CIR_VIEW.CVW_CONF,");
            sql.addSql("    INF.CIF_SHOW,");
            sql.addSql("    INF.CIF_SID,");
            sql.addSql("    INF.CIF_TITLE,");
            sql.addSql("    INF.CIF_ADATE,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("    CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("    CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("    CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("    ALL_CT.OPEN_CNT,");
            sql.addSql("    ALL_CT.ALL_CNT");
        }

        sql.addSql("  from");
        sql.addSql("    CIR_VIEW,");
        sql.addSql("    CIR_ACCOUNT");

        sql.addSql("      left join");
        sql.addSql("        CMN_USRM_INF");
        sql.addSql("      on");
        sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
        sql.addSql("      left join");
        sql.addSql("        CMN_USRM");
        sql.addSql("      on");
        sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID,");
        sql.addSql("    CIR_INF INF");
        sql.addSql("    left join");
        sql.addSql("    (");
        sql.addSql("    select");
        sql.addSql("      CIF_SID as ALL_CIF_SID,");
        sql.addSql("      sum(case when CVW_CONF = ? then 1 else 0 end) as OPEN_CNT,");
        sql.addSql("      count(CAC_SID) as ALL_CNT");
        sql.addSql("    from");
        sql.addSql("      CIR_VIEW");
        sql.addSql("    where");
        sql.addSql("      CIF_SID in (");
        sql.addSql("        select CIF_SID from CIR_VIEW");
        sql.addSql("        where CAC_SID = ?");
        sql.addSql("        and CVW_DSP = ?");
        sql.addSql("      )");
        sql.addSql("    group by");
        sql.addSql("      CIF_SID");
        sql.addSql("    ) ALL_CT");
        sql.addSql("    on INF.CIF_SID = ALL_CT.ALL_CIF_SID");
        sql.addIntValue(GSConstCircular.CONF_OPEN);
        sql.addIntValue(bean.getCacSid());
        sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
        sql.addSql("  where");
        sql.addSql("    CIR_VIEW.CAC_SID = ?");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CVW_DSP = ?");
        sql.addSql("  and");
        sql.addSql("    CIR_VIEW.CIF_SID = INF.CIF_SID");
        sql.addSql("  and");
        sql.addSql("    INF.CIF_AUID = CIR_ACCOUNT.CAC_SID");
        sql.addSql("  and");
        sql.addSql("    INF.CIF_SID in (select CIF_SID from CIR_VIEW_LABEL where CLA_SID = ?)");

        sql.addIntValue(bean.getCacSid());
        sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
        sql.addIntValue(bean.getLabelSid());

        //検索時条件
        if (ValidateUtil.isNumber(bean.getGroupSid())
                && Integer.valueOf(bean.getGroupSid()) > GSConstCommon.NUM_INIT
                && bean.getHassinSid() == GSConstCommon.NUM_INIT) {
            //発信者グループのみ選択時(通常のグループ)
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CMN_BELONGM");
            sql.addSql("                left join");
            sql.addSql("                  CIR_ACCOUNT");
            sql.addSql("                   on CMN_BELONGM.USR_SID = CIR_ACCOUNT.USR_SID");
            sql.addSql("            where");
            sql.addSql("              CMN_BELONGM.GRP_SID = ?");
            sql.addSql("            and CIR_ACCOUNT.CAC_SID = INF.CIF_AUID)");
            sql.addIntValue(Integer.valueOf(bean.getGroupSid()));
        } else if (__isMyGroupSid(bean.getGroupSid())
                && Integer.parseInt(bean.getGroupSid().substring(1)) > GSConstCommon.NUM_INIT
                && bean.getHassinSid() == GSConstCommon.NUM_INIT) {
            //発信者グループのみ選択時(マイグループ)
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CMN_MY_GROUP_MS");
            sql.addSql("                left join");
            sql.addSql("                  CIR_ACCOUNT");
            sql.addSql("                   on CMN_MY_GROUP_MS.MGM_SID = CIR_ACCOUNT.USR_SID");
            sql.addSql("            where");
            sql.addSql("              CMN_MY_GROUP_MS.MGP_SID = ?");
            sql.addIntValue(Integer.valueOf(bean.getGroupSid().substring(1)));
            sql.addSql("            and ");
            sql.addSql("              ( ");
            sql.addSql("              CMN_MY_GROUP_MS.USR_SID=? or ");
            sql.addIntValue(bean.getSessionUserSid());
            sql.addSql("              CMN_MY_GROUP_MS.MGP_SID in ( ");
            sql.addSql("                select MGP_SID ");
            sql.addSql("                from CMN_MY_GROUP_SHARE ");
            sql.addSql("                where  ");
            sql.addSql("                  MGS_USR_SID = ? ");
            sql.addIntValue(bean.getSessionUserSid());
            sql.addSql("                  or MGS_GRP_SID in ( ");
            sql.addSql("                    select GRP_SID from CMN_BELONGM ");
            sql.addSql("                    where USR_SID=? ");
            sql.addIntValue(bean.getSessionUserSid());
            sql.addSql("                 ) ");
            sql.addSql("               ) ");
            sql.addSql("              ) ");
            sql.addSql("            and CIR_ACCOUNT.CAC_SID = INF.CIF_AUID)");
        } else if (__isCirAccount(bean.getGroupSid())
                && bean.getHassinSid() == GSConstCommon.NUM_INIT) {
            //発信者グループのみ選択時(代表アカウント)
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CIR_ACCOUNT");
            sql.addSql("            where");
            sql.addSql("              CIR_ACCOUNT.USR_SID IS NULL");
            sql.addSql("            and CIR_ACCOUNT.CAC_SID = INF.CIF_AUID)");
        }

        //発信者選択時
        if (bean.getHassinSid() > GSConstCommon.NUM_INIT) {
            sql.addSql("  and");
            sql.addSql("    INF.CIF_AUID = ?");
            sql.addIntValue(bean.getHassinSid());
        }

        //キーワード入力時の検索条件
        __createKeyWord(bean, sql, Integer.valueOf(GSConstCircular.MODE_LABEL));

        //回覧先選択時
        String[] kairansakiSid = bean.getKairansakiSid();
        if (kairansakiSid != null && kairansakiSid.length > 0) {
            sql.addSql("  and");
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CIR_VIEW");
            sql.addSql("            where");
            sql.addSql("              CAC_SID in (");
            for (int i = 0; i < kairansakiSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(kairansakiSid[i], 0));

                if (i < kairansakiSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("              )");
            sql.addSql("              and CIR_VIEW.CIF_SID = INF.CIF_SID)");
        }

        sql.addSql(" ) union all (");

        //送信//////////////////////////////////////////////////////////////

        boolean keyWordSearch = false;
        List<String>  keywordList = bean.getKeyWord();
        if (keywordList != null && keywordList.size() > 0) {
            keyWordSearch = true;
        }
        sql.addSql("  select");

        if (getKbn == GET_COUNT) {
            //カウント
            sql.addSql("   count(*) as CNT ");
            sql.addSql(" from");
            sql.addSql("   CIR_INF INF ");
            sql.addSql("  where");
            sql.addSql("    INF.CIF_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    INF.CIF_AUID = ?");
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
            sql.addIntValue(bean.getCacSid());
            sql.addSql("  and");
            sql.addSql("    INF.CIF_SID in (select CIF_SID from CIR_INF_LABEL where CLA_SID = ?)");
            sql.addIntValue(bean.getLabelSid());

            //キーワード用クエリ、閲覧先クエリに備えてand配置
            if (keyWordSearch || (kairansakiSid != null && kairansakiSid.length > 0)) {
                sql.addSql("  and");
            }

        } else if (getKbn == GET_LIST) {
            //一覧表示
            sql.addSql("    '" + GSConstCircular.MODE_SOUSIN + "' as JS_FLG,");
            sql.addSql("    '" + GSConstCircular.CONF_OPEN + "' as CVW_CONF,");
            sql.addSql("    '" + GSConstCircular.CIR_INIT_SAKI_PUBLIC + "' as CIF_SHOW,");
            sql.addSql("    INF.CIF_SID,");
            sql.addSql("    INF.CIF_TITLE,");
            sql.addSql("    INF.CIF_ADATE,");
            sql.addSql("    INF.USI_SEI,");
            sql.addSql("    INF.USI_MEI,");
            sql.addSql("    INF.USI_SEI_KN,");
            sql.addSql("    INF.USI_MEI_KN,");
            sql.addSql("    INF.USR_UKO_FLG,");
            sql.addSql("    INF.CAC_JKBN,");
            sql.addSql("    INF.CAC_NAME,");
            sql.addSql("  sum(case");
            sql.addSql("      when CIR_VIEW.CVW_CONF = 1 then 1");
            sql.addSql("          else 0");
            sql.addSql("          end) as OPEN_CNT,");
            sql.addSql("  count(CIR_VIEW.CAC_SID) as ALL_CNT");
            sql.addSql(" from");
            sql.addSql("    (");
            sql.addSql("    select");
            sql.addSql("      CIR_INF.CIF_SID,");
            sql.addSql("      CIR_INF.CIF_TITLE,");
            sql.addSql("      CIR_INF.CIF_VALUE,");
            sql.addSql("      CIR_INF.CIF_ADATE,");
            sql.addSql("      CIR_INF.GRP_SID,");
            sql.addSql("      CIR_INF.CIF_AUID,");
            sql.addSql("      CIR_INF.CIF_JKBN,");
            sql.addSql("      CMN_USRM_INF.USI_SEI,");
            sql.addSql("      CMN_USRM_INF.USI_MEI,");
            sql.addSql("      CMN_USRM_INF.USI_SEI_KN,");
            sql.addSql("      CMN_USRM_INF.USI_MEI_KN,");
            sql.addSql("      CMN_USRM.USR_UKO_FLG,");
            sql.addSql("      CIR_ACCOUNT.CAC_SID as USID,");
            sql.addSql("      CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("      CIR_ACCOUNT.CAC_JKBN");
            sql.addSql("    from");
            sql.addSql("      CIR_INF,");
            sql.addSql("      CIR_ACCOUNT");
            sql.addSql("        left join");
            sql.addSql("          CMN_USRM_INF");
            sql.addSql("        on");
            sql.addSql("          CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("        left join");
            sql.addSql("          CMN_USRM");
            sql.addSql("        on");
            sql.addSql("          CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("  where");
            sql.addSql("    CIR_INF.CIF_AUID = CIR_ACCOUNT.CAC_SID");
            sql.addSql("  and");
            sql.addSql("    CIR_INF.CIF_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_INF.CIF_AUID = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_INF.CIF_SID in ("
                    + "select CIF_SID from CIR_INF_LABEL where CLA_SID = ?)");
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(bean.getLabelSid());
            sql.addSql("    ) INF");
            sql.addSql("    left join");
            sql.addSql("      CIR_VIEW");
            sql.addSql("    on");
            sql.addSql("      CIR_VIEW.CIF_SID = INF.CIF_SID");

            //キーワード用クエリ、閲覧先クエリに備えてand配置
            if (keyWordSearch || (kairansakiSid != null && kairansakiSid.length > 0)) {
                sql.addSql("  where");
            }
        }

        //キーワード入力時の検索条件
        __createKeyWord(bean, sql, Integer.valueOf(GSConstCircular.MODE_SOUSIN));


        if (kairansakiSid != null && kairansakiSid.length > 0) {
            //キーワード検索している場合は条件追記のためandを配置
            if (keyWordSearch) {
                sql.addSql("  and");
            }
            sql.addSql("    exists (");
            sql.addSql("            select");
            sql.addSql("              1");
            sql.addSql("            from");
            sql.addSql("              CIR_VIEW");
            sql.addSql("            where");
            sql.addSql("              CAC_SID in (");
            for (int i = 0; i < kairansakiSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(kairansakiSid[i], 0));

                if (i < kairansakiSid.length - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("              )");
            sql.addSql("              and CIR_VIEW.CIF_SID = INF.CIF_SID)");
        }

        if (getKbn == GET_LIST) {
          sql.addSql(" group by");
          sql.addSql("        INF.CIF_SID,");
          sql.addSql("        INF.CIF_TITLE,");
          sql.addSql("        INF.CIF_ADATE,");
          sql.addSql("        INF.USI_SEI,");
          sql.addSql("        INF.USI_MEI,");
          sql.addSql("        INF.USI_SEI_KN, ");
          sql.addSql("        INF.USI_MEI_KN, ");
          sql.addSql("        INF.CAC_NAME,");
          sql.addSql("        INF.CAC_JKBN,");
          sql.addSql("        INF.USR_UKO_FLG");
          sql.addSql("  order by INF.CIF_SID");
        }
        sql.addSql("   )");

        sql.addSql("   ) C_VIEW");

        if (getKbn == GET_LIST) {
            //一覧表示
            //ソート
            sql.addSql(" order by ");
            __createGomiOder(sql, bean.getSortKey(), bean.getOrderKey());

            if (bean.getSortKey2() > GSConstCommon.NUM_INIT
            && bean.getOrderKey2() > GSConstCommon.NUM_INIT) {
                sql.addSql(" , ");
                __createGomiOder(sql, bean.getSortKey2(), bean.getOrderKey2());
            }
            sql.addSql("    , C_VIEW.CIF_SID ASC");
        }

        int setOffset = 0;
        int setLimit = 0;
        if (offset > 0) {
            setOffset = offset;
        }
        if (limit > 0) {
            setLimit = limit;
        }
        sql.setPagingValue(setOffset, setLimit);

        return sql;
    }


    /**
     * <br>[機  能] SQLを作成する(回覧板情報(ゴミ箱・削除確認)取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param juSid 受信回覧板SID
     * @param soSid 送信回覧板SID
     * @return SqlBuffer
     */
    private SqlBuffer __createGomiConfSql(
        CirSearchModel bean,
        String[] juSid,
        String[] soSid) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");
        sql.addSql("    C_VIEW.JS_FLG,");
        sql.addSql("    C_VIEW.CIF_SID,");
        sql.addSql("    C_VIEW.CIF_TITLE,");
        sql.addSql("    C_VIEW.CIF_ADATE");
        sql.addSql(" from");
        sql.addSql("   (");

        //受信//////////////////////////////////////////////////////////////
        if (juSid != null && juSid.length > 0) {
            sql.addSql("  select");
            sql.addSql("    '" + GSConstCircular.MODE_JUSIN + "' as JS_FLG,");
            sql.addSql("    CIR_INF.CIF_SID,");
            sql.addSql("    CIR_INF.CIF_TITLE,");
            sql.addSql("    CIR_INF.CIF_ADATE");
            sql.addSql("  from");
            sql.addSql("    CIR_VIEW,");
            sql.addSql("    CIR_INF");
            sql.addSql("  where");
            sql.addSql("    CIR_VIEW.CAC_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_VIEW.CVW_DSP = ?");

            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_NG);

            sql.addSql("  and");
            sql.addSql("    CIR_VIEW.CIF_SID = CIR_INF.CIF_SID");
            sql.addSql("  and");
            sql.addSql("    CIR_INF.CIF_SID in (");

            for (int i = 0; i < juSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(juSid[i], 0));

                if (i < juSid.length - 1) {
                    sql.addSql("     , ");
                }
            }

            sql.addSql("   )");
        }

        if (juSid != null && juSid.length > 0
        &&  soSid != null && soSid.length > 0) {
            sql.addSql(" union all");
        }


        //送信//////////////////////////////////////////////////////////////
        if (soSid != null && soSid.length > 0) {
            sql.addSql("  select");
            sql.addSql("    '" + GSConstCircular.MODE_SOUSIN + "' as JS_FLG,");
            sql.addSql("    INF.CIF_SID,");
            sql.addSql("    INF.CIF_TITLE,");
            sql.addSql("    INF.CIF_ADATE");
            sql.addSql("  from");
            sql.addSql("    CIR_INF INF");
            sql.addSql("  where");
            sql.addSql("    INF.CIF_AUID = ?");
            sql.addSql("  and");
            sql.addSql("    INF.CIF_JKBN = ?");

            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_NG);

            sql.addSql("  and");
            sql.addSql("    INF.CIF_SID in (");

            for (int i = 0; i < soSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(soSid[i], 0));

                if (i < soSid.length - 1) {
                    sql.addSql("     , ");
                }
            }

            sql.addSql("   )");
        }

        sql.addSql("   ) C_VIEW");
        sql.addSql("  order by");
        sql.addSql("    C_VIEW.CIF_ADATE desc ");

        return sql;
    }


    /**
     * <br>[機  能] SQLを作成する(回覧板情報(ラベル)取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param juSid 受信回覧板SID
     * @param soSid 送信回覧板SID
     * @return SqlBuffer
     */
    private SqlBuffer __createLabelConfSql(
        CirSearchModel bean,
        String[] juSid,
        String[] soSid) {

        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  select");
        sql.addSql("    C_VIEW.JS_FLG,");
        sql.addSql("    C_VIEW.CIF_SID,");
        sql.addSql("    C_VIEW.CIF_TITLE,");
        sql.addSql("    C_VIEW.CIF_ADATE");
        sql.addSql(" from");
        sql.addSql("   (");

        //受信//////////////////////////////////////////////////////////////
        if (juSid != null && juSid.length > 0) {
            sql.addSql("  select");
            sql.addSql("    '" + GSConstCircular.MODE_JUSIN + "' as JS_FLG,");
            sql.addSql("    CIR_INF.CIF_SID,");
            sql.addSql("    CIR_INF.CIF_TITLE,");
            sql.addSql("    CIR_INF.CIF_ADATE");
            sql.addSql("  from");
            sql.addSql("    CIR_VIEW,");
            sql.addSql("    CIR_INF");
            sql.addSql("  where");
            sql.addSql("    CIR_VIEW.CAC_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_VIEW.CVW_DSP = ?");

            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);

            sql.addSql("  and");
            sql.addSql("    CIR_VIEW.CIF_SID = CIR_INF.CIF_SID");
            sql.addSql("  and");
            sql.addSql("    CIR_INF.CIF_SID in (");

            for (int i = 0; i < juSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(juSid[i], 0));

                if (i < juSid.length - 1) {
                    sql.addSql("     , ");
                }
            }

            sql.addSql("   )");
        }

        if (juSid != null && juSid.length > 0
        &&  soSid != null && soSid.length > 0) {
            sql.addSql(" union all");
        }


        //送信//////////////////////////////////////////////////////////////
        if (soSid != null && soSid.length > 0) {
            sql.addSql("  select");
            sql.addSql("    '" + GSConstCircular.MODE_SOUSIN + "' as JS_FLG,");
            sql.addSql("    INF.CIF_SID,");
            sql.addSql("    INF.CIF_TITLE,");
            sql.addSql("    INF.CIF_ADATE");
            sql.addSql("  from");
            sql.addSql("    CIR_INF INF");
            sql.addSql("  where");
            sql.addSql("    INF.CIF_AUID = ?");
            sql.addSql("  and");
            sql.addSql("    INF.CIF_JKBN = ?");

            sql.addIntValue(bean.getCacSid());
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);

            sql.addSql("  and");
            sql.addSql("    INF.CIF_SID in (");

            for (int i = 0; i < soSid.length; i++) {
                sql.addSql("     ? ");
                sql.addIntValue(NullDefault.getInt(soSid[i], 0));

                if (i < soSid.length - 1) {
                    sql.addSql("     , ");
                }
            }

            sql.addSql("   )");
        }

        sql.addSql("   ) C_VIEW");
        sql.addSql("  order by");
        sql.addSql("    C_VIEW.CIF_ADATE desc ");

        return sql;
    }


    /**
     * <br>[機  能] order by 以下のSQLを作成する(回覧板情報(ゴミ箱)取得時)
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param sort ソートキー
     * @param order オーダーキー
     */
    private void __createGomiOder(SqlBuffer sql, int sort, int order) {

        //昇順,降順指定
        String strOrder = "DESC";
        if (order != 1) {
            strOrder = "ASC";
        }

        switch (sort) {
            case GSConstCircular.SORT_TITLE:
                //タイトル
                sql.addSql("     C_VIEW.CIF_TITLE " + strOrder);
                break;

            case GSConstCircular.SORT_DATE:
                //登録日時
                sql.addSql("     C_VIEW.CIF_ADATE " + strOrder);
                break;

            case GSConstCircular.SORT_USER:
                //登録者名
              sql.addSql("     (case when C_VIEW.USI_SEI_KN is null then 1 else 0 end) ");
              sql.addSql(strOrder + ",");
              sql.addSql("     C_VIEW.USI_SEI_KN " + strOrder + ",");
              sql.addSql("     C_VIEW.USI_MEI_KN " + strOrder + ",");
              sql.addSql("     C_VIEW.CAC_NAME " + strOrder);
              break;

            default:
                break;
        }
    }

    /**
     * <br>[機  能] 回覧板情報(ゴミ箱・削除確認)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param juSid 受信回覧板SID
     * @param soSid 送信回覧板SID
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getGomiConfList(
        CirSearchModel bean,
        String[] juSid,
        String[] soSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        if ((juSid == null || juSid.length < 1)
        && (soSid == null || soSid.length < 1)) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = __createGomiConfSql(bean, juSid, soSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setJsFlg(rs.getString("JS_FLG"));
                retMdl.setCifSid(rs.getInt("CIF_SID"));
                retMdl.setCifTitle(rs.getString("CIF_TITLE"));
                retMdl.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板情報(ラベル)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CircularSearchModel
     * @param juSid 受信回覧板SID
     * @param soSid 送信回覧板SID
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getLabelConfList(
        CirSearchModel bean,
        String[] juSid,
        String[] soSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        if ((juSid == null || juSid.length < 1)
        && (soSid == null || soSid.length < 1)) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = __createLabelConfSql(bean, juSid, soSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setJsFlg(rs.getString("JS_FLG"));
                retMdl.setCifSid(rs.getInt("CIF_SID"));
                retMdl.setCifTitle(rs.getString("CIF_TITLE"));
                retMdl.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板情報(受信)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean Cir020SearchModel
     * @return CircularDspModel
     * @throws SQLException SQL実行例外
     */
    public CircularDspModel getJusinView(Cir020SearchModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CircularDspModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CIR_VIEW.CVW_MEMO,");
            sql.addSql("    CIR_VIEW.CVW_CONF,");
            sql.addSql("    CIR_VIEW.CVW_DSP,");
            sql.addSql("    CIR_VIEW.CVW_EDATE,");
            sql.addSql("    INF.CIF_TITLE,");
            sql.addSql("    INF.CIF_ADATE,");
            sql.addSql("    INF.CIF_EDATE,");
            sql.addSql("    INF.CIF_EKBN,");
            sql.addSql("    INF.CIF_EDIT_DATE,");
            sql.addSql("    INF.CIF_VALUE,");
            sql.addSql("    INF.CIF_SHOW,");
            sql.addSql("    INF.CIF_MEMO_FLG,");
            sql.addSql("    INF.CIF_MEMO_DATE,");
            sql.addSql("    INF.GRP_NAME,");
            sql.addSql("    INF.GRP_JKBN,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("    CIR_ACCOUNT.CAC_NAME");
            sql.addSql("  from");
            sql.addSql("    CIR_VIEW,");
            sql.addSql("    CIR_ACCOUNT");
            sql.addSql("      left join");
            sql.addSql("        CMN_USRM_INF");
            sql.addSql("      on");
            sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            sql.addSql("      left join");
            sql.addSql("        CMN_USRM");
            sql.addSql("      on");
            sql.addSql("        CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID,");
            sql.addSql("    (select");
            sql.addSql("       CIR_INF.CIF_SID,");
            sql.addSql("       CIR_INF.CIF_TITLE,");
            sql.addSql("       CIR_INF.CIF_VALUE,");
            sql.addSql("       CIR_INF.CIF_SHOW,");
            sql.addSql("       CIR_INF.CIF_ADATE,");
            sql.addSql("       CIR_INF.CIF_EDATE,");
            sql.addSql("       CIR_INF.GRP_SID,");
            sql.addSql("       CIR_INF.CIF_AUID,");
            sql.addSql("       CIR_INF.CIF_MEMO_FLG,");
            sql.addSql("       CIR_INF.CIF_MEMO_DATE,");
            sql.addSql("       CIR_INF.CIF_EKBN,");
            sql.addSql("       CIR_INF.CIF_EDIT_DATE,");
            sql.addSql("       CMN_GROUPM.GRP_NAME,");
            sql.addSql("       CMN_GROUPM.GRP_JKBN");
            sql.addSql("     from");
            sql.addSql("       CIR_INF");
            sql.addSql("     left join");
            sql.addSql("       CMN_GROUPM");
            sql.addSql("      on CIR_INF.GRP_SID = CMN_GROUPM.GRP_SID");
            sql.addSql("    ) INF");
            sql.addSql("  where");
            sql.addSql("    CIR_VIEW.CIF_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_VIEW.CAC_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_VIEW.CIF_SID = INF.CIF_SID");
            sql.addSql("  and");
            sql.addSql("    INF.CIF_AUID = CIR_ACCOUNT.CAC_SID");

            sql.addIntValue(bean.getCirSid());
            sql.addIntValue(bean.getCacSid());

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new CircularDspModel();
                ret.setCvwMemo(rs.getString("CVW_MEMO"));
                ret.setCvwConf(rs.getInt("CVW_CONF"));
                ret.setCvwDsp(rs.getInt("CVW_DSP"));
                ret.setCvwEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CVW_EDATE")));
                ret.setCifTitle(rs.getString("CIF_TITLE"));
                ret.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
                ret.setCifEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_EDATE")));
                ret.setCifEkbn(rs.getInt("CIF_EKBN"));
                ret.setCifValue(rs.getString("CIF_VALUE"));
                ret.setCifShow(rs.getInt("CIF_SHOW"));
                ret.setCifMemoFlg(rs.getInt("CIF_MEMO_FLG"));
                if (rs.getTimestamp("CIF_MEMO_DATE") != null) {
                    ret.setCifMemoDate(UDate.getInstanceTimestamp(
                            rs.getTimestamp("CIF_MEMO_DATE")));
                } else {
                    ret.setCifMemoDate(null);
                }

                if (ret.getCifEkbn() == GSConstCircular.CIR_EDIT) {
                    ret.setCifEditDate(
                            UDate.getInstanceTimestamp(rs.getTimestamp("CIF_EDIT_DATE")));
                }

                ret.setGrpName(rs.getString("GRP_NAME"));
                ret.setGrpJkbn(rs.getInt("GRP_JKBN"));
                ret.setCacJkbn(rs.getInt("CAC_JKBN"));
                ret.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    ret.setCacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                } else {
                    ret.setCacName(rs.getString("CAC_NAME"));
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
     * <br>[機  能] 回覧板情報(送信済み)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return CircularDspModel
     * @throws SQLException SQL実行例外
     */
    public CircularDspModel getSousinView(int cirSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CircularDspModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CIR_INF.CIF_TITLE,");
            sql.addSql("    CIR_INF.CIF_ADATE,");
            sql.addSql("    CIR_INF.CIF_EDATE,");
            sql.addSql("    CIR_INF.CIF_EKBN,");
            sql.addSql("    CIR_INF.CIF_EDIT_DATE,");
            sql.addSql("    CIR_INF.CIF_VALUE,");
            sql.addSql("    CIR_INF.CIF_SHOW,");
            sql.addSql("    CMN_GROUPM.GRP_NAME,");
            sql.addSql("    CMN_GROUPM.GRP_JKBN");
            sql.addSql("  from");
            sql.addSql("    CIR_INF");
            sql.addSql("      left join CMN_GROUPM");
            sql.addSql("      on CIR_INF.GRP_SID = CMN_GROUPM.GRP_SID");
            sql.addSql("  where");
            sql.addSql("    CIR_INF.CIF_SID = ?");

            sql.addIntValue(cirSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = new CircularDspModel();
                ret.setCifTitle(rs.getString("CIF_TITLE"));
                ret.setCifAdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_ADATE")));
                ret.setCifEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CIF_EDATE")));
                ret.setCifEkbn(rs.getInt("CIF_EKBN"));
                ret.setCifValue(rs.getString("CIF_VALUE"));
                ret.setCifShow(rs.getInt("CIF_SHOW"));
                ret.setGrpName(rs.getString("GRP_NAME"));
                ret.setGrpJkbn(rs.getInt("GRP_JKBN"));

                if (ret.getCifEkbn() == GSConstCircular.CIR_EDIT) {
                    ret.setCifEditDate(UDate.getInstanceTimestamp(
                            rs.getTimestamp("CIF_EDIT_DATE")));
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
     * <br>[機  能] 回覧板SIDから添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return List in CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getFileInfo(int cirSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    CIR_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    CIR_BIN.CIF_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  order by");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME");

            sql.addIntValue(cirSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel retMdl = new CmnBinfModel();
                retMdl.setBinSid(rs.getLong("BIN_SID"));
                retMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                retMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                retMdl.setBinFileSizeDsp(strSize);
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板SIDとユーザSIDから回覧先ユーザの添付ファイル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索モデル
     * @return List in CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getUserTempFileInfo(
            Cir020SearchModel searchMdl)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_EXTENSION,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_PATH,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE");
            sql.addSql("  from");
            sql.addSql("    CIR_USER_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    CIR_USER_BIN.CIF_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_USER_BIN.CAC_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_USER_BIN.CUB_BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  order by");
            sql.addSql("    CIR_USER_BIN.CUB_BIN_SID");

            sql.addIntValue(searchMdl.getCirSid());
            sql.addIntValue(searchMdl.getCacSid());
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CmnBinfModel retMdl = new CmnBinfModel();
                retMdl.setBinSid(rs.getLong("BIN_SID"));
                retMdl.setBinFileExtension(rs.getString("BIN_FILE_EXTENSION"));
                retMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                retMdl.setBinFilePath(rs.getString("BIN_FILE_PATH"));
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                retMdl.setBinFileSizeDsp(strSize);
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板SIDから回覧先ユーザの添付ファイルのバイナリSIDリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return List in CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    public HashMap <Integer, ArrayList<String>> getUserTempFileHash(
            int cirSid)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap <Integer, ArrayList<String>> retHash =
                new HashMap <Integer, ArrayList<String>>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID,");
            sql.addSql("    CIR_USER_BIN.CAC_SID");
            sql.addSql("  from");
            sql.addSql("    CIR_USER_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    CIR_USER_BIN.CIF_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_USER_BIN.CUB_BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  order by");
            sql.addSql("    CIR_USER_BIN.CUB_BIN_SID");

            sql.addIntValue(cirSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                int cacSid = rs.getInt("CAC_SID");
                ArrayList<String> uBinList =  retHash.get(cacSid);
                if (uBinList == null || uBinList.size() <= 0) {
                    uBinList = new ArrayList<String>();
                }

                uBinList.add(String.valueOf(rs.getLong("BIN_SID")));
                retHash.put(cacSid, uBinList);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return retHash;
    }

    /**
     * <br>[機  能] 回覧板SIDから回覧先ユーザ添付ファイル情報(バイナリSID,ファイル名、サイズのみ)を取得する。
     * <br>[解  説] ユーザSID：添付ファイル情報(バイナリSID,ファイル名、サイズのみ)のHash
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @return List in CmnBinfModel
     * @throws SQLException SQL実行例外
     */
    public HashMap <Integer, ArrayList<CmnBinfModel>> getUserTempFileNameHash(
            int cirSid)
                    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap <Integer, ArrayList<CmnBinfModel>> retHash =
                new HashMap <Integer, ArrayList<CmnBinfModel>>();
        CommonBiz cmnBiz = new CommonBiz();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CMN_BINF.BIN_SID,");
            sql.addSql("    CMN_BINF.BIN_FILE_NAME,");
            sql.addSql("    CMN_BINF.BIN_FILE_SIZE,");
            sql.addSql("    CIR_USER_BIN.CAC_SID");
            sql.addSql("  from");
            sql.addSql("    CIR_USER_BIN,");
            sql.addSql("    CMN_BINF");
            sql.addSql("  where");
            sql.addSql("    CIR_USER_BIN.CIF_SID = ?");
            sql.addSql("  and");
            sql.addSql("    CMN_BINF.BIN_JKBN = ?");
            sql.addSql("  and");
            sql.addSql("    CIR_USER_BIN.CUB_BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql("  order by");
            sql.addSql("    CIR_USER_BIN.CUB_BIN_SID");

            sql.addIntValue(cirSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                int cacSid = rs.getInt("CAC_SID");
                ArrayList<CmnBinfModel> uBinList =  retHash.get(cacSid);
                if (uBinList == null || uBinList.size() <= 0) {
                    uBinList = new ArrayList<CmnBinfModel>();
                }

                CmnBinfModel retMdl = new CmnBinfModel();
                retMdl.setBinSid(rs.getLong("BIN_SID"));
                retMdl.setBinFileName(rs.getString("BIN_FILE_NAME"));
                long size = rs.getInt("BIN_FILE_SIZE");
                String strSize = cmnBiz.getByteSizeString(size);
                retMdl.setBinFileSizeDsp(strSize);
                uBinList.add(retMdl);

                retHash.put(cacSid, uBinList);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return retHash;
    }
    /**
     * <br>[機  能] 指定されたユーザ添付ファイルのバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param cirSid 回覧板SID
     * @param cacSid アカウントSID
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int deleteBinfUsrTmp(int cirSid, int cacSid) throws SQLException {

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
            sql.addSql("      select CUB_BIN_SID from CIR_USER_BIN");
            sql.addSql("      where CIF_SID = ?");
            sql.addSql("      and CAC_SID = ?");
            sql.addSql("    )");

            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addIntValue(cirSid);
            sql.addIntValue(cacSid);

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
     * <br>[機  能] 回覧板SIDから回覧先情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl パラメータ情報
     * @return List in CircularDspModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getMemberInfo(Cir020KnDataSearchModel searchMdl)
    throws SQLException {

        boolean cirControl = false;
        int pctType = 0;
        CmnPluginControlDao pcontrolDao = new CmnPluginControlDao(getCon());
        CmnPluginControlModel pcontrolMdl = pcontrolDao.select(GSConstCircular.PLUGIN_ID_CIRCULAR);
        if (pcontrolMdl != null) {
            if (pcontrolMdl.getPctKbn() == GSConstMain.PCT_KBN_MEMBER) {
                cirControl = true;
                pctType = pcontrolMdl.getPctType();
            }
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        //回覧板SID
        int cirSid = searchMdl.getCirSid();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CIR_VIEW.CVW_CONF,");
            sql.addSql("    CIR_VIEW.CVW_CONF_DATE,");
            sql.addSql("    CIR_VIEW.CVW_MEMO,");
            sql.addSql("    CIR_VIEW.CVW_EDATE,");
            sql.addSql("    CIR_ACCOUNT.CAC_SID,");
            sql.addSql("    CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("    CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("    CMN_USRM.USR_JKBN,");
            sql.addSql("    CMN_USRM.USR_UKO_FLG,");
            sql.addSql("    CMN_USRM_INF.USR_SID,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("    CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");

            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");

            if (cirControl) {
                sql.addSql("    ,coalesce(CIR_CONTROL_MEMBER.MEMBER_SID, -1) as MEMBER_SID");
            }
            sql.addSql("  from");
            sql.addSql("  CIR_VIEW");
            sql.addSql("  left join");
            sql.addSql("    CIR_ACCOUNT");
            sql.addSql("      on CIR_VIEW.CAC_SID = CIR_ACCOUNT.CAC_SID");
            sql.addSql("        left join");
            sql.addSql("         CMN_USRM");
            sql.addSql("           on CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("             left join");
            sql.addSql("               CMN_USRM_INF");
            sql.addSql("                 on CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            if (cirControl) {
                sql.addSql("    left join");
                sql.addSql("      (");
                sql.addSql("        select");
                sql.addSql("          case CMN_PLUGIN_CONTROL_MEMBER.USR_SID");
                sql.addSql("          when -1 then CMN_BELONGM.USR_SID");
                sql.addSql("          else CMN_PLUGIN_CONTROL_MEMBER.USR_SID");
                sql.addSql("          end as MEMBER_SID");
                sql.addSql("        from");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER");
                sql.addSql("          left join");
                sql.addSql("            CMN_BELONGM");
                sql.addSql("          on");
                sql.addSql("            CMN_PLUGIN_CONTROL_MEMBER.GRP_SID = CMN_BELONGM.GRP_SID");
                sql.addSql("        where PCT_PID = ?");
                sql.addSql("        group by MEMBER_SID");
                sql.addSql("      ) CIR_CONTROL_MEMBER");
                sql.addSql("    on");
                sql.addSql("      CMN_USRM_INF.USR_SID = CIR_CONTROL_MEMBER.MEMBER_SID");
                sql.addStrValue(GSConstCircular.PLUGIN_ID_CIRCULAR);
            }
            sql.addSql("  where");
            sql.addSql("    CIR_VIEW.CIF_SID = ?");
            sql.addIntValue(cirSid);

            if (searchMdl.getSelectGrp() != GSConstCircular.GRPFILTER_ALL) {
                sql.addSql("  and");
                sql.addSql("    CMN_USRM_INF.USR_SID in (");
                sql.addSql("      select USR_SID from CMN_BELONGM");
                sql.addSql("      where GRP_SID = ?");
                sql.addSql("    )");
                sql.addIntValue(searchMdl.getSelectGrp());
            }

            //昇順,降順指定
            String strOrder = "DESC";
            String timeOrder = "ASC";
            if (searchMdl.getOrderKey() != 1) {
                strOrder = "ASC";
                timeOrder = "DESC";
            }

            sql.addSql(" order by ");

            switch (searchMdl.getSortKey()) {
                case GSConstCircular.SAKI_SORT_SNO:

                    //社員/職員番号
                    sql.addSql("     case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                    sql.addSql("     else CMN_USRM_INF.USI_SYAIN_NO end " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder + ",");
                    sql.addSql("     CIR_ACCOUNT.CAC_NAME " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_NAME:

                    //氏名
                    sql.addSql("     (case when CMN_USRM_INF.USI_SEI_KN is null then 1");
                    sql.addSql("      else 0 end) " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder + ",");
                    sql.addSql("     CIR_ACCOUNT.CAC_NAME " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_POST:

                    //役職
                    sql.addSql("     YAKUSYOKU_EXIST " + strOrder + " ,");
                    sql.addSql("     YAKUSYOKU_SORT " + strOrder + " ,");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder + ",");
                    sql.addSql("     CIR_ACCOUNT.CAC_NAME " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_MEMO:
                    //メモ
                    sql.addSql("     CIR_VIEW.CVW_MEMO " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_KAKU:
                    //確認
                    sql.addSql("     CIR_VIEW.CVW_CONF " + timeOrder + ",");
                    sql.addSql("     CIR_VIEW.CVW_CONF_DATE " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_SAISYU:
                    //最終更新日時
                    sql.addSql("     CIR_VIEW.CVW_EDATE " + timeOrder + ",");
                    sql.addSql("     CIR_VIEW.CVW_CONF " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;

                default:
                    //確認
                    sql.addSql("     CIR_VIEW.CVW_CONF " + timeOrder + ",");
                    sql.addSql("     CIR_VIEW.CVW_CONF_DATE " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setCvwConf(rs.getInt("CVW_CONF"));
                retMdl.setCvwConfDate(UDate.getInstanceTimestamp(
                        rs.getTimestamp("CVW_CONF_DATE")));
                retMdl.setCvwMemo(rs.getString("CVW_MEMO"));
                retMdl.setCvwEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CVW_EDATE")));
                retMdl.setCacSid(rs.getInt("CAC_SID"));
                retMdl.setCacJkbn(rs.getInt("CAC_JKBN"));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    retMdl.setCacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                    retMdl.setUsrUkoFlg(rs.getInt("USR_UKO_FLG"));
                } else {
                    retMdl.setCacName(rs.getString("CAC_NAME"));
                }

                retMdl.setSyainNo(rs.getString("USI_SYAIN_NO"));
                retMdl.setPosName(rs.getString("USI_YAKUSYOKU"));
                if (cirControl) {
                    if (pctType == GSConstMain.PCT_TYPE_LIMIT) {
                        retMdl.setCircularUse(rs.getInt("MEMBER_SID") >= 0);
                    } else {
                        retMdl.setCircularUse(rs.getInt("MEMBER_SID") < 0);
                    }
                } else {
                    retMdl.setCircularUse(true);
                }
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板SIDから回覧先情報を取得する(マイグループ指定)
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl パラメータ情報
     * @return List in CircularDspModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getMemberInfoMyGrp(Cir020KnDataSearchModel searchMdl)
    throws SQLException {

        boolean cirControl = false;
        int pctType = 0;
        CmnPluginControlDao pcontrolDao = new CmnPluginControlDao(getCon());
        CmnPluginControlModel pcontrolMdl = pcontrolDao.select(GSConstCircular.PLUGIN_ID_CIRCULAR);
        if (pcontrolMdl != null) {
            if (pcontrolMdl.getPctKbn() == GSConstMain.PCT_KBN_MEMBER) {
                cirControl = true;
                pctType = pcontrolMdl.getPctType();
            }
        }

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        //回覧板SID
        int cirSid = searchMdl.getCirSid();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CIR_VIEW.CVW_CONF,");
            sql.addSql("    CIR_VIEW.CVW_CONF_DATE,");
            sql.addSql("    CIR_VIEW.CVW_MEMO,");
            sql.addSql("    CIR_VIEW.CVW_EDATE,");
            sql.addSql("    CIR_ACCOUNT.CAC_SID,");
            sql.addSql("    CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("    CIR_ACCOUNT.CAC_NAME,");
            sql.addSql("    CMN_USRM.USR_JKBN,");
            sql.addSql("    CMN_USRM_INF.USR_SID,");
            sql.addSql("    CMN_USRM_INF.USI_SEI,");
            sql.addSql("    CMN_USRM_INF.USI_MEI,");
            sql.addSql("    CMN_USRM_INF.USI_SYAIN_NO,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then ''");
            sql.addSql("      else (select");
            sql.addSql("            POS_NAME");
            sql.addSql("          from");
            sql.addSql("            CMN_POSITION");
            sql.addSql("          where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as USI_YAKUSYOKU,");

            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 1");
            sql.addSql("      else 0");
            sql.addSql("    end) as YAKUSYOKU_EXIST,");
            sql.addSql("   (case");
            sql.addSql("      when CMN_USRM_INF.POS_SID = 0 then 0");
            sql.addSql("      else (select");
            sql.addSql("              POS_SORT");
            sql.addSql("            from");
            sql.addSql("              CMN_POSITION");
            sql.addSql("            where CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID)");
            sql.addSql("    end) as YAKUSYOKU_SORT");

            if (cirControl) {
                sql.addSql("    ,coalesce(CIR_CONTROL_MEMBER.MEMBER_SID, -1) as MEMBER_SID");
            }
            sql.addSql("  from");
            sql.addSql("  CIR_VIEW");
            sql.addSql("  left join");
            sql.addSql("    CIR_ACCOUNT");
            sql.addSql("      on CIR_VIEW.CAC_SID = CIR_ACCOUNT.CAC_SID");
            sql.addSql("        left join");
            sql.addSql("         CMN_USRM");
            sql.addSql("           on CIR_ACCOUNT.USR_SID = CMN_USRM.USR_SID");
            sql.addSql("             left join");
            sql.addSql("               CMN_USRM_INF");
            sql.addSql("                 on CIR_ACCOUNT.USR_SID = CMN_USRM_INF.USR_SID");
            if (cirControl) {
                sql.addSql("    left join");
                sql.addSql("      (");
                sql.addSql("        select");
                sql.addSql("          case CMN_PLUGIN_CONTROL_MEMBER.USR_SID");
                sql.addSql("          when -1 then CMN_MY_GROUP_MS.MGM_SID");
                sql.addSql("          else CMN_PLUGIN_CONTROL_MEMBER.USR_SID");
                sql.addSql("          end as MEMBER_SID");
                sql.addSql("        from");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER");
                sql.addSql("          left join");
                sql.addSql("            CMN_MY_GROUP_MS");
                sql.addSql("          on");
                sql.addSql("          CMN_PLUGIN_CONTROL_MEMBER.GRP_SID = CMN_MY_GROUP_MS.MGP_SID");
                sql.addSql("        where PCT_PID = ?");
                sql.addSql("        group by MEMBER_SID");
                sql.addSql("      ) CIR_CONTROL_MEMBER");
                sql.addSql("    on");
                sql.addSql("      CMN_USRM_INF.USR_SID = CIR_CONTROL_MEMBER.MEMBER_SID");
                sql.addStrValue(GSConstCircular.PLUGIN_ID_CIRCULAR);
            }
            sql.addSql("  where");
            sql.addSql("    CIR_VIEW.CIF_SID = ?");
            sql.addIntValue(cirSid);

            if (searchMdl.getSelectGrp() != GSConstCircular.GRPFILTER_ALL) {
                sql.addSql("  and");
                sql.addSql("    CMN_USRM_INF.USR_SID in (");
                sql.addSql("      select MGM_SID from CMN_MY_GROUP_MS");
                sql.addSql("      where MGP_SID = ?");
                sql.addSql("    )");
                sql.addIntValue(searchMdl.getSelectGrp());
            }

            //昇順,降順指定
            String strOrder = "DESC";
            String timeOrder = "ASC";
            if (searchMdl.getOrderKey() != 1) {
                strOrder = "ASC";
                timeOrder = "DESC";
            }

            sql.addSql(" order by ");

            switch (searchMdl.getSortKey()) {
                case GSConstCircular.SAKI_SORT_SNO:

                    //社員/職員番号
                    sql.addSql("     case when CMN_USRM_INF.USI_SYAIN_NO is null then ''");
                    sql.addSql("     else CMN_USRM_INF.USI_SYAIN_NO end " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_NAME:

                    //氏名
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_POST:

                    //役職
                    sql.addSql("     YAKUSYOKU_EXIST " + strOrder + " ,");
                    sql.addSql("     YAKUSYOKU_SORT " + strOrder + " ,");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_MEMO:
                    //メモ
                    sql.addSql("     CIR_VIEW.CVW_MEMO " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_KAKU:
                    //確認
                    sql.addSql("     CIR_VIEW.CVW_CONF " + timeOrder + ",");
                    sql.addSql("     CIR_VIEW.CVW_CONF_DATE " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_SAISYU:
                    //最終更新日時
                    sql.addSql("     CIR_VIEW.CVW_EDATE " + timeOrder + ",");
                    sql.addSql("     CIR_VIEW.CVW_CONF " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;

                default:
                    //確認
                    sql.addSql("     CIR_VIEW.CVW_CONF " + timeOrder + ",");
                    sql.addSql("     CIR_VIEW.CVW_CONF_DATE " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_SEI_KN " + strOrder + ",");
                    sql.addSql("     CMN_USRM_INF.USI_MEI_KN " + strOrder);
                    break;
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setCvwConf(rs.getInt("CVW_CONF"));
                retMdl.setCvwConfDate(UDate.getInstanceTimestamp(
                        rs.getTimestamp("CVW_CONF_DATE")));
                retMdl.setCvwMemo(rs.getString("CVW_MEMO"));
                retMdl.setCvwEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CVW_EDATE")));
                retMdl.setCacSid(rs.getInt("CAC_SID"));
                retMdl.setCacJkbn(rs.getInt("CAC_JKBN"));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                        && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    retMdl.setCacName(
                            rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                } else {
                    retMdl.setCacName(rs.getString("CAC_NAME"));
                }

                retMdl.setSyainNo(rs.getString("USI_SYAIN_NO"));
                retMdl.setPosName(rs.getString("USI_YAKUSYOKU"));
                if (cirControl) {
                    if (pctType == GSConstMain.PCT_TYPE_LIMIT) {
                        retMdl.setCircularUse(rs.getInt("MEMBER_SID") >= 0);
                    } else {
                        retMdl.setCircularUse(rs.getInt("MEMBER_SID") < 0);
                    }
                } else {
                    retMdl.setCircularUse(true);
                }
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板SIDから回覧先情報を取得する(代表アカウント指定)
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl パラメータ情報
     * @return List in CircularDspModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularDspModel> getMemberInfoAccount(Cir020KnDataSearchModel searchMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<CircularDspModel> ret = new ArrayList<CircularDspModel>();
        con = getCon();

        //回覧板SID
        int cirSid = searchMdl.getCirSid();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("  select");
            sql.addSql("    CIR_VIEW.CVW_CONF,");
            sql.addSql("    CIR_VIEW.CVW_CONF_DATE,");
            sql.addSql("    CIR_VIEW.CVW_MEMO,");
            sql.addSql("    CIR_VIEW.CVW_EDATE,");
            sql.addSql("    CIR_ACCOUNT.CAC_SID,");
            sql.addSql("    CIR_ACCOUNT.CAC_JKBN,");
            sql.addSql("    CIR_ACCOUNT.CAC_NAME");
            sql.addSql("  from");
            sql.addSql("  CIR_VIEW");
            sql.addSql("  left join");
            sql.addSql("    CIR_ACCOUNT");
            sql.addSql("      on CIR_VIEW.CAC_SID = CIR_ACCOUNT.CAC_SID");
            sql.addSql("  where");
            sql.addSql("    CIR_VIEW.CIF_SID = ?");
            sql.addSql("  and ");
            sql.addSql("    CIR_ACCOUNT.USR_SID IS NULL");
            sql.addIntValue(cirSid);



            //昇順,降順指定
            String strOrder = "DESC";
            String timeOrder = "ASC";
            if (searchMdl.getOrderKey() != 1) {
                strOrder = "ASC";
                timeOrder = "DESC";
            }

            sql.addSql(" order by ");

            switch (searchMdl.getSortKey()) {

                case GSConstCircular.SAKI_SORT_NAME:
                    //氏名
                    sql.addSql("     CIR_ACCOUNT.CAC_NAME " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_MEMO:
                    //メモ
                    sql.addSql("     CIR_VIEW.CVW_MEMO " + strOrder + ",");
                    sql.addSql("     CIR_ACCOUNT.CAC_NAME " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_KAKU:
                    //確認
                    sql.addSql("     CIR_VIEW.CVW_CONF " + timeOrder + ",");
                    sql.addSql("     CIR_VIEW.CVW_CONF_DATE " + strOrder + ",");
                    sql.addSql("     CIR_ACCOUNT.CAC_NAME " + strOrder);
                    break;

                case GSConstCircular.SAKI_SORT_SAISYU:
                    //最終更新日時
                    sql.addSql("     CIR_VIEW.CVW_EDATE " + timeOrder + ",");
                    sql.addSql("     CIR_VIEW.CVW_CONF " + strOrder + ",");
                    sql.addSql("     CIR_ACCOUNT.CAC_NAME " + strOrder);
                    break;

                default:
                    //確認
                    sql.addSql("     CIR_VIEW.CVW_CONF " + timeOrder + ",");
                    sql.addSql("     CIR_VIEW.CVW_CONF_DATE " + strOrder + ",");
                    sql.addSql("     CIR_ACCOUNT.CAC_NAME " + strOrder);
                    break;
            }

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CircularDspModel retMdl = new CircularDspModel();
                retMdl.setCvwConf(rs.getInt("CVW_CONF"));
                retMdl.setCvwConfDate(UDate.getInstanceTimestamp(
                        rs.getTimestamp("CVW_CONF_DATE")));
                retMdl.setCvwMemo(rs.getString("CVW_MEMO"));
                retMdl.setCvwEdate(UDate.getInstanceTimestamp(rs.getTimestamp("CVW_EDATE")));
                retMdl.setCacSid(rs.getInt("CAC_SID"));
                retMdl.setCacJkbn(rs.getInt("CAC_JKBN"));
                retMdl.setCacName(rs.getString("CAC_NAME"));
                retMdl.setSyainNo("");
                retMdl.setPosName("");
                retMdl.setCircularUse(true);
                ret.add(retMdl);
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
     * <br>[機  能] 回覧板SID(複数)を指定して回覧板情報を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param cirSids 回覧板SID
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void deleteCir(String[] cirSids, int userSid) throws SQLException {

        Connection con = getCon();
        UDate now = new UDate();
        CirInfDao ciDao = new CirInfDao(con);
        CirCommonBiz cirBiz = new CirCommonBiz();

        //回覧板情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        List<Integer> cifSidList
            = Arrays.asList(cirSids).stream()
                .filter(Objects::nonNull)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        CirUsedDataBiz usedDataBiz = new CirUsedDataBiz(con);
        usedDataBiz.insertCirDataSize(cifSidList, false);

        //添付ファイル情報を取得
        CirBinDao cbDao = new CirBinDao(con);
        List < CirBinModel > cbList = cbDao.getBinList(cirSids);
        //ユーザ添付ファイル情報を取得
        CirUserBinDao cubDao = new CirUserBinDao(con);
        List<CirUserBinModel> cubList = cubDao.getBinList(cirSids);

        List<Long> binList = __getBinSids(cbList, cubList);

        //バイナリ情報を論理削除
        CmnBinfModel cbMdl = new CmnBinfModel();
        cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
        cbMdl.setBinUpuser(userSid);
        cbMdl.setBinUpdate(now);
        updateJKbn(cbMdl, binList);


        int i = 0;
        int delCount = GSConstCircular.CIR_BATCH_DELETE_COUNT;
        List<String> updateList = new ArrayList<String>();
        String[] delList = null;

        for (String cirSid : cirSids) {

            updateList.add(cirSid);
            i++;

            if (i > delCount) {
                delList = updateList.toArray(new String[updateList.size()]);

                //回覧板情報(CIR_INF)を物理削除
                ciDao.deleteCirInf(delList);

                //回覧板添付情報(CIR_BIN)を物理削除
                cbDao.deleteCriBin(delList);

                //回覧先ユーザ添付情報(CIR_USER_BIN)を物理削除
                cubDao.deleteBinsSetCir(delList);

                //回覧先情報(CIR_VIEW)を物理削除
                cirBiz.deleteView(con, delList);

                updateList = new ArrayList<String>();
                i = 0;

                // 回覧板送信ラベル削除
                CirInfLabelDao labelInfDao = new CirInfLabelDao(con);
                labelInfDao.deleteCircularLabel(delList);
                // 回覧板受信ラベル削除
                CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
                labelViewDao.deleteCircularLabel(delList);
            }
        }

        if (updateList != null && updateList.size() > 0) {

            delList = updateList.toArray(new String[updateList.size()]);

            //回覧板情報(CIR_INF)物理を削除
            ciDao.deleteCirInf(delList);

            //回覧板添付情報(CIR_BIN)を物理削除
            cbDao.deleteCriBin(delList);

            //回覧先ユーザ添付情報(CIR_USER_BIN)を物理削除
            cubDao.deleteBinsSetCir(delList);

            //回覧先情報(CIR_VIEW)を物理削除
            cirBiz.deleteView(con, delList);


            // 回覧板送信ラベル削除
            CirInfLabelDao labelInfDao = new CirInfLabelDao(con);
            labelInfDao.deleteCircularLabel(delList);
            // 回覧板受信ラベル削除
            CirViewLabelDao labelViewDao = new CirViewLabelDao(con);
            labelViewDao.deleteCircularLabel(delList);
        }

    }

    /**
     * <br>[機  能] 添付ファイル、回覧先ユーザ添付ファイルの情報からバイナリSIDを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cbList 添付ファイル情報を取得
     * @param cubList 回覧先ユーザ添付ファイル情報を取得
     * @return バイナリーSIDリスト
     */
    private List<Long> __getBinSids(List < CirBinModel > cbList, List<CirUserBinModel> cubList) {
        List<Long> ret = new ArrayList<Long>();


        for (CirBinModel cbMdl : cbList) {
            ret.add(cbMdl.getBinSid());
        }

        for (CirUserBinModel cubMdl : cubList) {
            ret.add(cubMdl.getCubBinSid());
        }

        return ret;
    }

    /**
     * <br>[機  能] バイナリSID(複数)を指定して状態区分を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CMN_BINF Data Bindding JavaBean
     * @param binList 削除対象バイナリSIDリスト
     * @return int 更新件数
     * @throws SQLException SQL実行例外
     */
    public int updateJKbn(CmnBinfModel bean, List <Long> binList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        if (binList == null) {
            return count;
        }
        if (binList.size() < 1) {
            return count;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set ");
            sql.addSql("   BIN_JKBN = ?,");
            sql.addSql("   BIN_UPUSER = ?,");
            sql.addSql("   BIN_UPDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   BIN_SID in (");

            sql.addIntValue(bean.getBinJkbn());
            sql.addIntValue(bean.getBinUpuser());
            sql.addDateValue(bean.getBinUpdate());

            for (int i = 0; i < binList.size(); i++) {

                sql.addSql("     ? ");
                sql.addLongValue(binList.get(i));

                if (i < binList.size() - 1) {
                    sql.addSql("     , ");
                }
            }
            sql.addSql("   )");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <br>[機  能] 回覧板情報を登録する
     * <br>[解  説] 回覧板情報(CIR_INF)、回覧板添付情報(CIR_BIN)、回覧先情報(CIR_VIEW)
     *              を登録する
     * <br>[備  考]
     * @param ciMdl CirInfModel
     * @param binList List in String(回覧板添付情報登録用)
     * @param userSid ユーザSID(回覧先登録用)
     * @throws SQLException SQL実行例外
     */
    public void insertCir(CirInfModel ciMdl, List < String > binList, String[] userSid)
    throws SQLException {

        Connection con = getCon();

        //回覧板情報(CIR_INF)を登録
        CirInfDao ciDao = new CirInfDao(con);
        ciDao.insertCirInf(ciMdl);

        //回覧板添付情報(CIR_BIN)を登録
        insertCirBin(ciMdl, binList);

        //回覧先情報(CIR_VIEW)を登録
        CirCommonBiz cirBiz = new CirCommonBiz();
        insertCirView(ciMdl, cirBiz.getAccountSidFromUsr(con, userSid));

        //送信回覧板の集計データを登録する。
        cirBiz.regScirLogCnt(con, ciMdl.getCifAuid(),
                cirBiz.getAccountSidFromUsr(con, userSid).length, ciMdl.getCifAdate());

        //受信回覧板の集計データを登録する
        for (String str : cirBiz.getAccountSidFromUsr(con, userSid)) {
            cirBiz.regJcirLogCnt(con, Integer.parseInt(str), ciMdl.getCifAdate());
        }

        //回覧板情報のデータ使用量を登録
        CirUsedDataBiz usedDataBiz = new CirUsedDataBiz(con);
        usedDataBiz.insertCirDataSize(ciMdl.getCifSid(), true);

    }

    /**
     * <br>[機  能] 回覧板情報を編集する
     * <br>[解  説] 回覧板情報(CIR_INF)、回覧板添付情報(CIR_BIN)、回覧先情報(CIR_VIEW)
     *              を登録する
     * <br>[備  考]
     * @param ciMdl CirInfModel
     * @param binList List in String(回覧板添付情報登録用)
     * @param addAcc 追加アカウントSID
     * @param rmAcc 削除アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void updateCir(CirInfModel ciMdl,
            List < String > binList,
            List<Integer> addAcc, List<Integer> rmAcc)
    throws SQLException {

        Connection con = getCon();

        //回覧板情報(CIR_INF)を登録
        CirInfDao ciDao = new CirInfDao(con);
        ciDao.updateCirInf(ciMdl);

        //回覧板添付情報(CIR_BIN)を登録
        insertCirBin(ciMdl, binList);

        String[] accArr = new String[addAcc.size()];
        for (int i = 0; i < accArr.length; i++) {
            accArr[i] = String.valueOf(addAcc.get(i));
        }


        //回覧先情報(CIR_VIEW)を登録
        CirCommonBiz cirBiz = new CirCommonBiz();
        insertCirView(ciMdl, accArr);

        //送信回覧板の集計データを登録する。
        cirBiz.regScirLogCnt(con, ciMdl.getCifAuid(),
                accArr.length, ciMdl.getCifAdate());

        //受信回覧板の集計データを登録する
        for (int sid : addAcc) {
            cirBiz.regJcirLogCnt(con, sid, ciMdl.getCifAdate());
        }

        //送信回覧板削除
        if (rmAcc != null && rmAcc.size() > 0) {
            CirViewDao civDao = new CirViewDao(con);
            List<CirViewModel> rmMdlList = new ArrayList<CirViewModel>();
            List<Integer> accSidList = new ArrayList<Integer>();
            for (int accSid : rmAcc) {
                CirViewModel rmMdl = new CirViewModel();
                rmMdl.setCifSid(ciMdl.getCifSid());
                rmMdl.setCacSid(accSid);
                rmMdlList.add(rmMdl);
                accSidList.add(accSid);
            }

            civDao.removeView(rmMdlList);

            //バイナリ情報を論理削除
            CirUserBinDao cubDao = new CirUserBinDao(con);
            List<CirUserBinModel> cubList = cubDao.getBinList(ciMdl.getCifSid(), accSidList);

            List<Long> delBinList = new ArrayList<Long>();
            for (CirUserBinModel cubMdl : cubList) {
                delBinList.add(cubMdl.getCubBinSid());
            }

            CmnBinfModel cbMdl = new CmnBinfModel();
            cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            cbMdl.setBinUpuser(ciMdl.getCifEuid());
            cbMdl.setBinUpdate(ciMdl.getCifEdate());
            updateJKbn(cbMdl, delBinList);


            cubDao.deleteBins(ciMdl.getCifSid(), accSidList);

            //送信回覧板ラベル削除
            CirViewLabelDao cvlDao = new CirViewLabelDao(con);
            cvlDao.deleteCircularLabel(ciMdl.getCifSid(), accSidList);

        }
    }

    /**
     * <br>[機  能] 回覧板添付情報を登録する
     * <br>[解  説] Listを渡し、複数登録する
     * <br>[備  考]
     * @param bean CirInfModel
     * @param binList List in String
     * @throws SQLException SQL実行例外
     */
    public void insertCirBin(CirInfModel bean, List < String > binList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (binList.size() < 1) {
            return;
        }

        try {

            for (int i = 0; i < binList.size(); i++) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" CIR_BIN(");
                sql.addSql("   CIF_SID,");
                sql.addSql("   BIN_SID");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   ?,");
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getCifSid());
                sql.addLongValue(NullDefault.getLong(binList.get(i), 0));

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 回覧先情報を登録する
     * <br>[解  説] 配列を渡し、複数登録する
     * <br>[備  考]
     * @param bean CmnMyGroupModel
     * @param cacSid アカウントSID
     * @throws SQLException SQL実行例外
     */
    public void insertCirView(CirInfModel bean, String[] cacSid) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        if (cacSid == null) {
            return;
        }
        if (cacSid.length < 1) {
            return;
        }

        try {

            for (int i = 0; i < cacSid.length; i++) {
                //SQL文
                SqlBuffer sql = new SqlBuffer();
                sql.addSql(" insert ");
                sql.addSql(" into ");
                sql.addSql(" CIR_VIEW(");
                sql.addSql("   CIF_SID,");
                sql.addSql("   CAC_SID,");
                sql.addSql("   CVW_MEMO,");
                sql.addSql("   CVW_CONF,");
                sql.addSql("   CVW_DSP,");
                sql.addSql("   CVW_AUID,");
                sql.addSql("   CVW_ADATE,");
                sql.addSql("   CVW_EUID,");
                sql.addSql("   CVW_EDATE");
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
                sql.addSql("   ?");
                sql.addSql(" )");

                sql.addIntValue(bean.getCifSid());
                sql.addIntValue(NullDefault.getInt(cacSid[i], 0));
                String nullStr = null;
                sql.addStrValue(nullStr);
                sql.addIntValue(GSConstCircular.CONF_UNOPEN);
                sql.addIntValue(GSConstCircular.DSPKBN_DSP_OK);
                sql.addIntValue(bean.getCifAuid());
                sql.addDateValue(bean.getCifAdate());
                sql.addIntValue(bean.getCifEuid());
                sql.addDateValue(bean.getCifEdate());

                log__.info(sql.toLogString());

                pstmt = con.prepareStatement(sql.toSqlString());
                sql.setParameter(pstmt);
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 回覧板情報(バッチ処理で削除するもの)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return List in CircularListModel
     * @throws SQLException SQL実行例外
     */
    public String[] getDelList() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIR_INF.CIF_SID");
            sql.addSql(" from");
            sql.addSql("   CIR_INF");
            sql.addSql(" where");
            sql.addSql("   CIR_INF.CIF_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   not exists");
            sql.addSql("    (");
            sql.addSql("     select");
            sql.addSql("       CIR_VIEW.CIF_SID");
            sql.addSql("     from");
            sql.addSql("       CIR_VIEW");
            sql.addSql("     where");
            sql.addSql("       CIR_VIEW.CIF_SID = CIR_INF.CIF_SID");
            sql.addSql("     and");
            sql.addSql("       CIR_VIEW.CVW_DSP != ?");
            sql.addSql("    )");

            sql.addIntValue(GSConstCircular.DSPKBN_DSP_DEL);
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_DEL);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.add(rs.getString("CIF_SID"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret.toArray(new String[ret.size()]);
    }

    /**
     * <br>[機  能] 使用可能なアカウントかを調べる
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param cacSid アカウントSID
     * @return true:可能  false:不可能
     * @throws SQLException SQL実行時例外
     */
    public boolean canUseCheckAccount(int userSid, int cacSid)
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
            sql.addSql("   CIR_ACCOUNT.CAC_SID as CAC_SID, ");
            sql.addSql("   CIR_ACCOUNT.CAC_TYPE as CAC_TYPE, ");
            sql.addSql("   CIR_ACCOUNT.CAC_NAME as CAC_NAME, ");
            sql.addSql("   CIR_ACCOUNT.CAC_BIKO as CAC_BIKO, ");
            sql.addSql("   ACCOUNT_SORT.CAS_SORT as CAS_SORT ");
            sql.addSql(" from ");
            sql.addSql("   CIR_ACCOUNT");
            sql.addSql("   left join");
            sql.addSql("     (");
            sql.addSql("      select CAC_SID, CAS_SORT");
            sql.addSql("      from CIR_ACCOUNT_SORT ");
            sql.addSql("      where USR_SID = ? ");
            sql.addSql("     ) ACCOUNT_SORT");
            sql.addSql("   on");
            sql.addSql("     CIR_ACCOUNT.CAC_SID = ACCOUNT_SORT.CAC_SID ");
            sql.addSql(" where ");
            sql.addSql("   CIR_ACCOUNT.CAC_JKBN = ?");
            sql.addSql(" and ");
            sql.addSql("   (");
            sql.addSql("      (");
            sql.addSql("         CIR_ACCOUNT.CAC_TYPE = ? ");
            sql.addSql("       and ");
            sql.addSql("         CIR_ACCOUNT.USR_SID = ? ");
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
            sql.addSql(" and ");
            sql.addSql("   CIR_ACCOUNT.CAC_SID = ?");
            sql.addSql(" order by ");
            sql.addSql("   ACCOUNT_SORT.CAS_SORT asc ");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(userSid);
            sql.addIntValue(GSConstCircular.CAC_JKBN_NORMAL);
            sql.addIntValue(GSConstCircular.CAC_TYPE_NORMAL);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);
            sql.addIntValue(cacSid);

            log__.info(sql.toLogString());
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

    /**
     * <br>[機  能] アカウント情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param cirSid 回覧板SID
     * @return アカウントリスト
     * @throws SQLException SQL実行時例外
     */
    public int getViewAccountSid(int userSid, int cirSid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        int ret = 0;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   distinct ");
            sql.addSql("   CIR_VIEW.CAC_SID as CAC_SID, ");
            sql.addSql("   CIR_VIEW.CVW_CONF as  CVW_CONF");
            sql.addSql(" from ");
            sql.addSql("    CIR_VIEW ");
            sql.addSql("     left join CIR_ACCOUNT_USER ");
            sql.addSql("       on CIR_VIEW.CAC_SID = CIR_ACCOUNT_USER.CAC_SID ");
            sql.addSql(" where ");
            sql.addSql("   CIR_VIEW.CIF_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   (CIR_ACCOUNT_USER.USR_SID = ? ");
            sql.addSql("      or ");
            sql.addSql("    CIR_ACCOUNT_USER.GRP_SID in ( ");
            sql.addSql("     select  ");
            sql.addSql("       CMN_BELONGM.GRP_SID  ");
            sql.addSql("     from  ");
            sql.addSql("       CMN_BELONGM ");
            sql.addSql("     where ");
            sql.addSql("       CMN_BELONGM.USR_SID = ?) ");
            sql.addSql("    ) ");
            sql.addSql("  order by  ");
            sql.addSql("    CIR_VIEW.CVW_CONF");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addIntValue(cirSid);
            sql.addIntValue(userSid);
            sql.addIntValue(userSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                ret = rs.getInt("CAC_SID");
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
     * @return List in SML_ACCOUNTModel
     * @throws SQLException SQL実行例外
     */
    public List<CircularUsrModel> selectCirAccount() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        CircularUsrModel cirUsrMdl = null;
        ArrayList<CircularUsrModel> ret = new ArrayList<CircularUsrModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   CAC_SID,");
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
                cirUsrMdl = new CircularUsrModel();
                cirUsrMdl.setCacName(rs.getString("CAC_NAME"));
                cirUsrMdl.setCacSid(String.valueOf(rs.getInt("CAC_SID")));
                ret.add(cirUsrMdl);
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
     * パラメータ.グループコンボ値がグループSIDかマイグループSIDかを判定する
     * <br>[機  能]先頭文字に"M"が有る場合、マイグループSID
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    private static boolean __isMyGroupSid(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf("M");

        // 先頭文字に"M"が有る場合はマイグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * パラメータ.グループコンボ値が代表アカウントかを判定する
     * <br>[機  能]先頭文字に"sac"が有る場合は代表アカウント
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    private static boolean __isCirAccount(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf(GSConstCircular.CIR_ACCOUNT_STR);

        // 先頭文字に"cac"が有る場合は代表アカウント
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * <br>[機  能] アカウントが回覧板の添付ファイルを参照可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSid 回覧板SID
     * @param binSid バイナリ―SID
     * @param accountSid アカウントSID
     * @return true: 参照可能 false: 参照不可
     * @throws SQLException SQL実行例外
     */
    public boolean canViewCirTempfile(int cifSid, long binSid, int accountSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        boolean result = false;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIR_INF.CIF_SID");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF,");
            sql.addSql("   CIR_INF,");
            sql.addSql("   CIR_BIN");
            sql.addSql(" where");
            sql.addSql("   CIR_INF.CIF_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CIR_INF.CIF_JKBN <> ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_SID = ?");
            sql.addSql(" and");
            sql.addSql("   CMN_BINF.BIN_JKBN = ?");
            sql.addSql(" and");
            sql.addSql("   CIR_INF.CIF_SID = CIR_BIN.CIF_SID");
            sql.addSql(" and");
            sql.addSql("   CIR_BIN.BIN_SID = CMN_BINF.BIN_SID");
            sql.addSql(" and");
            sql.addSql("   (");
            sql.addSql("      CIR_INF.CIF_AUID = ?");
            sql.addSql("   or");
            sql.addSql("      CIR_INF.CIF_SID in (");
            sql.addSql("        select CIF_SID from CIR_VIEW");
            sql.addSql("        where CIF_SID = ?");
            sql.addSql("        and CAC_SID = ?");
            sql.addSql("      )");
            sql.addSql("   )");
            sql.setPagingValue(0, 1);

            sql.addIntValue(cifSid);
            sql.addIntValue(GSConstCircular.DSPKBN_DSP_DEL);
            sql.addLongValue(binSid);
            sql.addIntValue(GSConst.JTKBN_TOROKU);
            sql.addIntValue(accountSid);
            sql.addIntValue(cifSid);
            sql.addIntValue(accountSid);

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
     * <br>[機  能] 送信済みのメールが論理削除されていないか確認
     * <br>[解  説]
     * <br>[備  考]
     * @param cifSid 回覧板SID
     * @param accountSid アカウントSID
     * @return true: 存在する false: 存在しない
     * @throws SQLException SQL実行例外
     */
    public CirInfModel checkSendCirExist(int cifSid, int accountSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        CirInfModel dspMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   CIF_JKBN");
            sql.addSql(" from");
            sql.addSql("   CIR_INF");
            sql.addSql(" where");
            sql.addSql("   CIF_SID = ?");
            sql.addSql("   and CIF_AUID = ?");

            sql.addIntValue(cifSid);
            sql.addIntValue(accountSid);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                dspMdl = new CirInfModel();
                dspMdl.setCifJkbn(rs.getInt("CIF_JKBN"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        return dspMdl;
    }


}
