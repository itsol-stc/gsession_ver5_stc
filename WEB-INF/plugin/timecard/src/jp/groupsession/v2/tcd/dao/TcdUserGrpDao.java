package jp.groupsession.v2.tcd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.tcd.model.TcdUserGrpModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 所属グループ一覧情報を取得するためのDAOクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdUserGrpDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdUserGrpDao.class);

    /**
     * <p>Default Constructor
     */
    public TcdUserGrpDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public TcdUserGrpDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ユーザSIDから所属するグループ名、グループID、グループ階層を取得します
     * <br>[解  説] ユーザSIDを引数で与えると、グループ名とグループIDが取得できます
     * <br>[備  考]
     * @param usid ユーザSID
     * @return ret List Rsv110Model
     * @throws SQLException SQL実行時例外
     */
    public List<TcdUserGrpModel> selectGroupDataListOrderbyClass(int usid)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<TcdUserGrpModel> ret = new ArrayList<TcdUserGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  CL.SID as GROUPSID,");
            sql.addSql("  CL.GCNAME as GROUPNAME,");
            sql.addSql("  CL.GCL_SID1,");
            sql.addSql("  CL.GCL_SID2,");
            sql.addSql("  CL.GCL_SID3,");
            sql.addSql("  CL.GCL_SID4,");
            sql.addSql("  CL.GCL_SID5,");
            sql.addSql("  CL.GCL_SID6,");
            sql.addSql("  CL.GCL_SID7,");
            sql.addSql("  CL.GCL_SID8,");
            sql.addSql("  CL.GCL_SID9,");
            sql.addSql("  CL.GCL_SID10");
            sql.addSql(" from");
            sql.addSql(" (");
            sql.addSql(" select");
            sql.addSql("    GC.GCL_SID1 as GCL_SID1,");
            sql.addSql("    GC.GCL_SID2 as GCL_SID2,");
            sql.addSql("    GC.GCL_SID3 as GCL_SID3,");
            sql.addSql("    GC.GCL_SID4 as GCL_SID4,");
            sql.addSql("    GC.GCL_SID5 as GCL_SID5,");
            sql.addSql("    GC.GCL_SID6 as GCL_SID6,");
            sql.addSql("    GC.GCL_SID7 as GCL_SID7,");
            sql.addSql("    GC.GCL_SID8 as GCL_SID8,");
            sql.addSql("    GC.GCL_SID9 as GCL_SID9,");
            sql.addSql("    GC.GCL_SID10 as GCL_SID10,");
            sql.addSql("    GC.GCL_AUID,");
            sql.addSql("    GC.GCL_ADATE,");
            sql.addSql("    GC.GCL_EUID,");
            sql.addSql("    GC.GCL_EDATE,");
            sql.addSql("    GI1.GRP_NAME as GNAME1,");
            sql.addSql("    GI2.GRP_NAME as GNAME2,");
            sql.addSql("    GI3.GRP_NAME as GNAME3,");
            sql.addSql("    GI4.GRP_NAME as GNAME4,");
            sql.addSql("    GI5.GRP_NAME as GNAME5,");
            sql.addSql("    GI6.GRP_NAME as GNAME6,");
            sql.addSql("    GI7.GRP_NAME as GNAME7,");
            sql.addSql("    GI8.GRP_NAME as GNAME8,");
            sql.addSql("    GI9.GRP_NAME as GNAME9,");
            sql.addSql("    GI10.GRP_NAME as GNAME10,");
            sql.addSql("    case when GI1.GRP_NAME is null then '' else GI1.GRP_NAME end s1,");
            sql.addSql("    case when GI2.GRP_NAME is null then '' else GI2.GRP_NAME end s2,");
            sql.addSql("    case when GI3.GRP_NAME is null then '' else GI3.GRP_NAME end s3,");
            sql.addSql("    case when GI4.GRP_NAME is null then '' else GI4.GRP_NAME end s4,");
            sql.addSql("    case when GI5.GRP_NAME is null then '' else GI5.GRP_NAME end s5,");
            sql.addSql("    case when GI6.GRP_NAME is null then '' else GI6.GRP_NAME end s6,");
            sql.addSql("    case when GI7.GRP_NAME is null then '' else GI7.GRP_NAME end s7,");
            sql.addSql("    case when GI8.GRP_NAME is null then '' else GI8.GRP_NAME end s8,");
            sql.addSql("    case when GI9.GRP_NAME is null then '' else GI9.GRP_NAME end s9,");
            sql.addSql("    case when GI10.GRP_NAME is null then '' else GI10.GRP_NAME end s10,");
            sql.addSql("    (case when GC.GCL_SID10 > 0 then GC.GCL_SID10");
            sql.addSql("          when GC.GCL_SID9 > 0 then GC.GCL_SID9");
            sql.addSql("          when GC.GCL_SID8 > 0 then GC.GCL_SID8");
            sql.addSql("          when GC.GCL_SID7 > 0 then GC.GCL_SID7");
            sql.addSql("          when GC.GCL_SID6 > 0 then GC.GCL_SID6");
            sql.addSql("          when GC.GCL_SID5 > 0 then GC.GCL_SID5");
            sql.addSql("          when GC.GCL_SID4 > 0 then GC.GCL_SID4");
            sql.addSql("          when GC.GCL_SID3 > 0 then GC.GCL_SID3");
            sql.addSql("          when GC.GCL_SID2 > 0 then GC.GCL_SID2");
            sql.addSql("          when GC.GCL_SID1 > 0 then GC.GCL_SID1");
            sql.addSql("          else -1");
            sql.addSql("    end) SID,");
            sql.addSql("    (case when GI10.GRP_NAME is not null then GI10.GRP_NAME");
            sql.addSql("          when GI9.GRP_NAME is not null then GI9.GRP_NAME");
            sql.addSql("          when GI8.GRP_NAME is not null then GI8.GRP_NAME");
            sql.addSql("          when GI7.GRP_NAME is not null then GI7.GRP_NAME");
            sql.addSql("          when GI6.GRP_NAME is not null then GI6.GRP_NAME");
            sql.addSql("          when GI5.GRP_NAME is not null then GI5.GRP_NAME");
            sql.addSql("          when GI4.GRP_NAME is not null then GI4.GRP_NAME");
            sql.addSql("          when GI3.GRP_NAME is not null then GI3.GRP_NAME");
            sql.addSql("          when GI2.GRP_NAME is not null then GI2.GRP_NAME");
            sql.addSql("          when GI1.GRP_NAME is not null then GI1.GRP_NAME");
            sql.addSql("          else ''");
            sql.addSql("    end) as GCNAME");
            sql.addSql(" from");
            sql.addSql("    ((((((((((");
            sql.addSql("    CMN_GROUP_CLASS GC");
            sql.addSql("    left join CMN_GROUPM GI1 on GC.GCL_SID1 = GI1.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI2 on GC.GCL_SID2 = GI2.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI3 on GC.GCL_SID3 = GI3.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI4 on GC.GCL_SID4 = GI4.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI5 on GC.GCL_SID5 = GI5.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI6 on GC.GCL_SID6 = GI6.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI7 on GC.GCL_SID7 = GI7.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI8 on GC.GCL_SID8 = GI8.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI9 on GC.GCL_SID9 = GI9.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI10 on GC.GCL_SID10 = GI10.GRP_SID)");
            sql.addSql(" where");
            sql.addSql("    GI1.GRP_JKBN = 0");
            sql.addSql(" or    GI2.GRP_JKBN = 0");
            sql.addSql(" or    GI3.GRP_JKBN = 0");
            sql.addSql(" or    GI4.GRP_JKBN = 0");
            sql.addSql(" or    GI5.GRP_JKBN = 0");
            sql.addSql(" or    GI6.GRP_JKBN = 0");
            sql.addSql(" or    GI7.GRP_JKBN = 0");
            sql.addSql(" or    GI8.GRP_JKBN = 0");
            sql.addSql(" or    GI9.GRP_JKBN = 0");
            sql.addSql(" or    GI10.GRP_JKBN = 0");
            sql.addSql(" ) CL,");
            sql.addSql("  CMN_BELONGM");
            sql.addSql(" where");
            sql.addSql("   CMN_BELONGM.USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.GRP_SID=CL.SID");
            sql.addSql(" order by");
            sql.addSql("    CL.s1,");
            sql.addSql("    CL.s2,");
            sql.addSql("    CL.s3,");
            sql.addSql("    CL.s4,");
            sql.addSql("    CL.s5,");
            sql.addSql("    CL.s6,");
            sql.addSql("    CL.s7,");
            sql.addSql("    CL.s8,");
            sql.addSql("    CL.s9,");
            sql.addSql("    CL.s10,");
            sql.addSql("    CL.GCL_SID1,");
            sql.addSql("    CL.GCL_SID2,");
            sql.addSql("    CL.GCL_SID3,");
            sql.addSql("    CL.GCL_SID4,");
            sql.addSql("    CL.GCL_SID5,");
            sql.addSql("    CL.GCL_SID6,");
            sql.addSql("    CL.GCL_SID7,");
            sql.addSql("    CL.GCL_SID8,");
            sql.addSql("    CL.GCL_SID9,");
            sql.addSql("    CL.GCL_SID10");
            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);
            sql.setParameter(pstmt);

            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            TcdUserGrpModel model = null;
            while (rs.next()) {
                model = new TcdUserGrpModel();
                model.setGroupSid(rs.getInt("GROUPSID"));
                model.setGroupName(rs.getString("GROUPNAME"));
                model.setGclSid1(rs.getInt("GCL_SID1"));
                model.setGclSid2(rs.getInt("GCL_SID2"));
                model.setGclSid3(rs.getInt("GCL_SID3"));
                model.setGclSid4(rs.getInt("GCL_SID4"));
                model.setGclSid5(rs.getInt("GCL_SID5"));
                model.setGclSid6(rs.getInt("GCL_SID6"));
                model.setGclSid7(rs.getInt("GCL_SID7"));
                model.setGclSid8(rs.getInt("GCL_SID8"));
                model.setGclSid9(rs.getInt("GCL_SID9"));
                model.setGclSid10(rs.getInt("GCL_SID10"));
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
     * <br>[機  能] ユーザSIDから所属するグループ名とグループIDとグループ階層を取得します(階層順)
     * <br>[解  説] ユーザSIDを引数で与えると、グループ名とグループIDが取得できます
     * <br>[備  考]
     * @param usid ユーザSID
     * @return ret List TcdUserGrpModel
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<TcdUserGrpModel> selectGroupAdminOrderbyClass(int usid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<TcdUserGrpModel> ret = new ArrayList<TcdUserGrpModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("  CL.SID as GROUPSID,");
            sql.addSql("  CL.NAME as GROUPNAME,");
            sql.addSql("  CL.GCL_SID1,");
            sql.addSql("  CL.GCL_SID2,");
            sql.addSql("  CL.GCL_SID3,");
            sql.addSql("  CL.GCL_SID4,");
            sql.addSql("  CL.GCL_SID5,");
            sql.addSql("  CL.GCL_SID6,");
            sql.addSql("  CL.GCL_SID7,");
            sql.addSql("  CL.GCL_SID8,");
            sql.addSql("  CL.GCL_SID9,");
            sql.addSql("  CL.GCL_SID10");
            sql.addSql(" from");
            sql.addSql(" (");
            sql.addSql(" select");
            sql.addSql("    GC.GCL_SID1 as GCL_SID1,");
            sql.addSql("    GC.GCL_SID2 as GCL_SID2,");
            sql.addSql("    GC.GCL_SID3 as GCL_SID3,");
            sql.addSql("    GC.GCL_SID4 as GCL_SID4,");
            sql.addSql("    GC.GCL_SID5 as GCL_SID5,");
            sql.addSql("    GC.GCL_SID6 as GCL_SID6,");
            sql.addSql("    GC.GCL_SID7 as GCL_SID7,");
            sql.addSql("    GC.GCL_SID8 as GCL_SID8,");
            sql.addSql("    GC.GCL_SID9 as GCL_SID9,");
            sql.addSql("    GC.GCL_SID10 as GCL_SID10,");
            sql.addSql("    GC.GCL_AUID,");
            sql.addSql("    GC.GCL_ADATE,");
            sql.addSql("    GC.GCL_EUID,");
            sql.addSql("    GC.GCL_EDATE,");
            sql.addSql("    GI1.GRP_NAME as GNAME1,");
            sql.addSql("    GI2.GRP_NAME as GNAME2,");
            sql.addSql("    GI3.GRP_NAME as GNAME3,");
            sql.addSql("    GI4.GRP_NAME as GNAME4,");
            sql.addSql("    GI5.GRP_NAME as GNAME5,");
            sql.addSql("    GI6.GRP_NAME as GNAME6,");
            sql.addSql("    GI7.GRP_NAME as GNAME7,");
            sql.addSql("    GI8.GRP_NAME as GNAME8,");
            sql.addSql("    GI9.GRP_NAME as GNAME9,");
            sql.addSql("    GI10.GRP_NAME as GNAME10,");
            sql.addSql("    case when GI1.GRP_NAME is null then '' else GI1.GRP_NAME end s1,");
            sql.addSql("    case when GI2.GRP_NAME is null then '' else GI2.GRP_NAME end s2,");
            sql.addSql("    case when GI3.GRP_NAME is null then '' else GI3.GRP_NAME end s3,");
            sql.addSql("    case when GI4.GRP_NAME is null then '' else GI4.GRP_NAME end s4,");
            sql.addSql("    case when GI5.GRP_NAME is null then '' else GI5.GRP_NAME end s5,");
            sql.addSql("    case when GI6.GRP_NAME is null then '' else GI6.GRP_NAME end s6,");
            sql.addSql("    case when GI7.GRP_NAME is null then '' else GI7.GRP_NAME end s7,");
            sql.addSql("    case when GI8.GRP_NAME is null then '' else GI8.GRP_NAME end s8,");
            sql.addSql("    case when GI9.GRP_NAME is null then '' else GI9.GRP_NAME end s9,");
            sql.addSql("    case when GI10.GRP_NAME is null then '' else GI10.GRP_NAME end s10,");
            sql.addSql("    (case when GC.GCL_SID10 > 0 then GC.GCL_SID10");
            sql.addSql("          when GC.GCL_SID9 > 0 then GC.GCL_SID9");
            sql.addSql("          when GC.GCL_SID8 > 0 then GC.GCL_SID8");
            sql.addSql("          when GC.GCL_SID7 > 0 then GC.GCL_SID7");
            sql.addSql("          when GC.GCL_SID6 > 0 then GC.GCL_SID6");
            sql.addSql("          when GC.GCL_SID5 > 0 then GC.GCL_SID5");
            sql.addSql("          when GC.GCL_SID4 > 0 then GC.GCL_SID4");
            sql.addSql("          when GC.GCL_SID3 > 0 then GC.GCL_SID3");
            sql.addSql("          when GC.GCL_SID2 > 0 then GC.GCL_SID2");
            sql.addSql("          when GC.GCL_SID1 > 0 then GC.GCL_SID1");
            sql.addSql("          else -1");
            sql.addSql("    end) as SID,");
            sql.addSql("    (case when GI10.GRP_NAME is not null then GI10.GRP_NAME");
            sql.addSql("          when GI9.GRP_NAME is not null then GI9.GRP_NAME");
            sql.addSql("          when GI8.GRP_NAME is not null then GI8.GRP_NAME");
            sql.addSql("          when GI7.GRP_NAME is not null then GI7.GRP_NAME");
            sql.addSql("          when GI6.GRP_NAME is not null then GI6.GRP_NAME");
            sql.addSql("          when GI5.GRP_NAME is not null then GI5.GRP_NAME");
            sql.addSql("          when GI4.GRP_NAME is not null then GI4.GRP_NAME");
            sql.addSql("          when GI3.GRP_NAME is not null then GI3.GRP_NAME");
            sql.addSql("          when GI2.GRP_NAME is not null then GI2.GRP_NAME");
            sql.addSql("          when GI1.GRP_NAME is not null then GI1.GRP_NAME");
            sql.addSql("          else ''");
            sql.addSql("    end) as NAME");
            sql.addSql(" from");
            sql.addSql("    ((((((((((");
            sql.addSql("    CMN_GROUP_CLASS GC");
            sql.addSql("    left join CMN_GROUPM GI1 on GC.GCL_SID1 = GI1.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI2 on GC.GCL_SID2 = GI2.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI3 on GC.GCL_SID3 = GI3.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI4 on GC.GCL_SID4 = GI4.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI5 on GC.GCL_SID5 = GI5.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI6 on GC.GCL_SID6 = GI6.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI7 on GC.GCL_SID7 = GI7.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI8 on GC.GCL_SID8 = GI8.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI9 on GC.GCL_SID9 = GI9.GRP_SID)");
            sql.addSql("    left join CMN_GROUPM GI10 on GC.GCL_SID10 = GI10.GRP_SID)");
            sql.addSql(" where");
            sql.addSql("    GI1.GRP_JKBN = 0");
            sql.addSql(" or    GI2.GRP_JKBN = 0");
            sql.addSql(" or    GI3.GRP_JKBN = 0");
            sql.addSql(" or    GI4.GRP_JKBN = 0");
            sql.addSql(" or    GI5.GRP_JKBN = 0");
            sql.addSql(" or    GI6.GRP_JKBN = 0");
            sql.addSql(" or    GI7.GRP_JKBN = 0");
            sql.addSql(" or    GI8.GRP_JKBN = 0");
            sql.addSql(" or    GI9.GRP_JKBN = 0");
            sql.addSql(" or    GI10.GRP_JKBN = 0");
            sql.addSql(" ) CL,");
            sql.addSql("  CMN_BELONGM");
            sql.addSql(" where");
            sql.addSql("  CMN_BELONGM.USR_SID=?");
            sql.addSql(" and");
            sql.addSql("   CMN_BELONGM.BEG_GRPKBN=1");
            sql.addSql(" and");
            sql.addSql("  CMN_BELONGM.GRP_SID=CL.SID");
            sql.addSql(" order by");
            sql.addSql("    CL.s1,");
            sql.addSql("    CL.s2,");
            sql.addSql("    CL.s3,");
            sql.addSql("    CL.s4,");
            sql.addSql("    CL.s5,");
            sql.addSql("    CL.s6,");
            sql.addSql("    CL.s7,");
            sql.addSql("    CL.s8,");
            sql.addSql("    CL.s9,");
            sql.addSql("    CL.s10,");
            sql.addSql("    CL.GCL_SID1,");
            sql.addSql("    CL.GCL_SID2,");
            sql.addSql("    CL.GCL_SID3,");
            sql.addSql("    CL.GCL_SID4,");
            sql.addSql("    CL.GCL_SID5,");
            sql.addSql("    CL.GCL_SID6,");
            sql.addSql("    CL.GCL_SID7,");
            sql.addSql("    CL.GCL_SID8,");
            sql.addSql("    CL.GCL_SID9,");
            sql.addSql("    CL.GCL_SID10");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            TcdUserGrpModel gpMdl = null;
            while (rs.next()) {
                gpMdl = new TcdUserGrpModel();
                gpMdl.setGroupSid(rs.getInt("GROUPSID"));
                gpMdl.setGroupName(rs.getString("GROUPNAME"));
                gpMdl.setGclSid1(rs.getInt("GCL_SID1"));
                gpMdl.setGclSid2(rs.getInt("GCL_SID2"));
                gpMdl.setGclSid3(rs.getInt("GCL_SID3"));
                gpMdl.setGclSid4(rs.getInt("GCL_SID4"));
                gpMdl.setGclSid5(rs.getInt("GCL_SID5"));
                gpMdl.setGclSid6(rs.getInt("GCL_SID6"));
                gpMdl.setGclSid7(rs.getInt("GCL_SID7"));
                gpMdl.setGclSid8(rs.getInt("GCL_SID8"));
                gpMdl.setGclSid9(rs.getInt("GCL_SID9"));
                gpMdl.setGclSid10(rs.getInt("GCL_SID10"));

                ret.add(gpMdl);
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