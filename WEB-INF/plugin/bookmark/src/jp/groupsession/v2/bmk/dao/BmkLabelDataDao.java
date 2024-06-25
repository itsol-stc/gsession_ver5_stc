package jp.groupsession.v2.bmk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.bmk.GSConstBookmark;
import jp.groupsession.v2.bmk.model.BmkLabelDataModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>BMK_LABEL Data Access Object
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkLabelDataDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BmkLabelDataDao.class);

    /**
     * <p>Default Constructor
     */
    public BmkLabelDataDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public BmkLabelDataDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定ラベル区分のラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param blbKbn ラベル区分
     * @param uSid ユーザSID
     * @param gSid グループSID
     * @return ラベル一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BmkLabelDataModel> select(int blbKbn, int uSid, int gSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkLabelDataModel> ret = new ArrayList<BmkLabelDataModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_LABEL.BLB_SID,");
            sql.addSql("   BMK_LABEL.BLB_KBN,");
            sql.addSql("   BMK_LABEL.USR_SID,");
            sql.addSql("   BMK_LABEL.GRP_SID,");
            sql.addSql("   BMK_LABEL.BLB_NAME,");
            sql.addSql("   BMK_LABEL.BLB_AUID,");
            sql.addSql("   BMK_LABEL.BLB_ADATE,");
            sql.addSql("   BMK_LABEL.BLB_EUID,");
            sql.addSql("   BMK_LABEL.BLB_EDATE,");
            sql.addSql("   count(BMK_BELONG_LABEL.BMK_SID) as COUNT");
            sql.addSql(" from");
            sql.addSql("   BMK_LABEL ");
            sql.addSql("   left join ");
            sql.addSql("     BMK_BELONG_LABEL");
            sql.addSql("   on ");
            sql.addSql("     BMK_BELONG_LABEL.BLB_SID = BMK_LABEL.BLB_SID");
            sql.addSql(" where ");
            sql.addSql("   BLB_KBN=?");
            sql.addIntValue(blbKbn);

            if (blbKbn == GSConstBookmark.BMK_KBN_KOJIN) {
                //ラベル区分：個人
                sql.addSql("   and");
                sql.addSql("   USR_SID=?");
                sql.addIntValue(uSid);
            } else if (blbKbn == GSConstBookmark.BMK_KBN_GROUP) {
                //ラベル区分：グループ
                sql.addSql("   and");
                sql.addSql("   GRP_SID=?");
                sql.addIntValue(gSid);
            }
            sql.addSql(" group by ");
            sql.addSql("   BMK_LABEL.BLB_SID,");
            sql.addSql("   BMK_LABEL.BLB_KBN,");
            sql.addSql("   BMK_LABEL.USR_SID,");
            sql.addSql("   BMK_LABEL.GRP_SID,");
            sql.addSql("   BMK_LABEL.BLB_NAME,");
            sql.addSql("   BMK_LABEL.BLB_AUID,");
            sql.addSql("   BMK_LABEL.BLB_ADATE,");
            sql.addSql("   BMK_LABEL.BLB_EUID,");
            sql.addSql("   BMK_LABEL.BLB_EDATE");
            sql.addSql(" order by ");
            sql.addSql("   COUNT desc, ");
            sql.addSql("   BMK_LABEL.BLB_SID asc");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkLabelDataFromRs(rs));
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
     * <br>[機  能] 指定ラベル区分のラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param blbSid ラベルSID
     * @return ラベル一覧
     * @throws SQLException SQL実行例外
     */
    public ArrayList<BmkLabelDataModel> select(String[] blbSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<BmkLabelDataModel> ret = new ArrayList<BmkLabelDataModel>();
        con = getCon();

        if (blbSid == null || blbSid.length < 1) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BMK_LABEL.BLB_SID,");
            sql.addSql("   BMK_LABEL.BLB_KBN,");
            sql.addSql("   BMK_LABEL.USR_SID,");
            sql.addSql("   BMK_LABEL.GRP_SID,");
            sql.addSql("   BMK_LABEL.BLB_NAME,");
            sql.addSql("   BMK_LABEL.BLB_AUID,");
            sql.addSql("   BMK_LABEL.BLB_ADATE,");
            sql.addSql("   BMK_LABEL.BLB_EUID,");
            sql.addSql("   BMK_LABEL.BLB_EDATE,");
            sql.addSql("   count(BMK_BELONG_LABEL.BMK_SID) as COUNT");
            sql.addSql(" from");
            sql.addSql("   BMK_LABEL ");
            sql.addSql("   left join ");
            sql.addSql("     BMK_BELONG_LABEL");
            sql.addSql("   on ");
            sql.addSql("     BMK_BELONG_LABEL.BLB_SID = BMK_LABEL.BLB_SID");

            sql.addSql(" where ");
            sql.addSql("   BMK_LABEL.BLB_SID in (");
            for (int i = 0; i < blbSid.length; i++) {
                sql.addSql("  ?");
                sql.addIntValue(NullDefault.getInt(blbSid[i], 0));
                if (i != blbSid.length - 1) {
                    sql.addSql("  ,");
                }
            }
            sql.addSql("   )");

            sql.addSql(" group by ");
            sql.addSql("   BMK_LABEL.BLB_SID,");
            sql.addSql("   BMK_LABEL.BLB_KBN,");
            sql.addSql("   BMK_LABEL.USR_SID,");
            sql.addSql("   BMK_LABEL.GRP_SID,");
            sql.addSql("   BMK_LABEL.BLB_NAME,");
            sql.addSql("   BMK_LABEL.BLB_AUID,");
            sql.addSql("   BMK_LABEL.BLB_ADATE,");
            sql.addSql("   BMK_LABEL.BLB_EUID,");
            sql.addSql("   BMK_LABEL.BLB_EDATE");
            sql.addSql(" order by ");
            sql.addSql("   COUNT desc, ");
            sql.addSql("   BMK_LABEL.BLB_SID asc");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getBmkLabelDataFromRs(rs));
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
     * <p>Create BMK_LABEL Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created BmkLabelModel
     * @throws SQLException SQL実行例外
     */
    private BmkLabelDataModel __getBmkLabelDataFromRs(ResultSet rs) throws SQLException {
        BmkLabelDataModel bean = new BmkLabelDataModel();
        bean.setBlbSid(rs.getInt("BLB_SID"));
        bean.setBlbKbn(rs.getInt("BLB_KBN"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setGrpSid(rs.getInt("GRP_SID"));
        bean.setBlbName(rs.getString("BLB_NAME"));
        bean.setBlbCnt(rs.getInt("COUNT"));
        bean.setBlbAuid(rs.getInt("BLB_AUID"));
        bean.setBlbAdate(UDate.getInstanceTimestamp(rs.getTimestamp("BLB_ADATE")));
        bean.setBlbEuid(rs.getInt("BLB_EUID"));
        bean.setBlbEdate(UDate.getInstanceTimestamp(rs.getTimestamp("BLB_EDATE")));
        return bean;
    }
}
