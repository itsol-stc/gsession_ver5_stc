package jp.groupsession.v2.api.ntp.nippou.detail;

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
import jp.groupsession.v2.ntp.model.NtpDataModel;
/**
 * <br>[機  能] WEBAPI 日報詳細情報取得DAO
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouDetailDao extends AbstractDao {

    /** ロガーオブジェクト */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    /**
     * コンストラクタ
     * @param con コネクション
     */
    public  ApiNippouDetailDao(Connection con) {
        super(con);
    }

    /**
     * <p>
     * ユーザSID、ユーザ区分、公開区分、日付を指定し日報情報を取得する
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分
     * @param pub 公開区分 ※-1を指定すると条件から除外されます
     * @param from 日付from
     * @param notAccessUserList 特例閲覧不可ユーザ
     * @return ArrayList in NtpDataModel
     * @throws SQLException
     *             SQL実行例外
     */
    public ArrayList<NtpDataModel> select(int usrSid, int usrKbn, int pub,
            UDate from, List<Integer> notAccessUserList) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<NtpDataModel> ret = new ArrayList<NtpDataModel>();
        con = getCon();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   NIP_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   NIP_DATE,");
            sql.addSql("   NIP_FR_TIME,");
            sql.addSql("   NIP_TO_TIME,");
            sql.addSql("   NIP_KADO_HH,");
            sql.addSql("   NIP_KADO_MM,");
            sql.addSql("   NIP_MGY_SID,");
            sql.addSql("   NAN_SID,");
            sql.addSql("   ACO_SID,");
            sql.addSql("   ABA_SID,");
            sql.addSql("   NIP_TITLE,");
            sql.addSql("   NIP_TITLE_CLO,");
            sql.addSql("   MPR_SID,");
            sql.addSql("   MKB_SID,");
            sql.addSql("   MKH_SID,");
            sql.addSql("   NIP_TIEUP_SID,");
            sql.addSql("   NIP_KEIZOKU,");
            sql.addSql("   NIP_ACTEND,");
            sql.addSql("   NIP_DETAIL,");
            sql.addSql("   NIP_ASSIGN,");
            sql.addSql("   NIP_KINGAKU,");
            sql.addSql("   NIP_MIKOMI,");
            sql.addSql("   NIP_SYOKAN,");
            sql.addSql("   NIP_PUBLIC,");
            sql.addSql("   NIP_EDIT,");
            sql.addSql("   NEX_SID,");
            sql.addSql("   NIP_AUID,");
            sql.addSql("   NIP_ADATE,");
            sql.addSql("   NIP_EUID,");
            sql.addSql("   NIP_EDATE");
            sql.addSql(" from ");
            sql.addSql("   NTP_DATA");
            sql.addSql(" where ");
            sql.addSql("   USR_SID = ?");
            sql.addIntValue(usrSid);

            //特例のアクセス不可ユーザは取得しない
            if (notAccessUserList != null && notAccessUserList.size() != 0) {
                for (int notAccessUser:notAccessUserList) {
                    sql.addSql("   and");
                    sql.addSql("   USR_SID != ?");
                    sql.addIntValue(notAccessUser);
                }
            }

            String startDateFrStr = from.getDateStringForSql();

            sql.addSql(" and");
            sql.addSql("  NIP_DATE = cast(? as DATE)");
            sql.addStrValue(startDateFrStr);

            sql.addSql(" order by ");
            sql.addSql("   NIP_FR_TIME");



            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getNtpDataFromRs(rs));
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
     * <p>
     * Create NTP_DATA Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created SchDataModel
     * @throws SQLException
     *             SQL実行例外
     */
    private NtpDataModel __getNtpDataFromRs(ResultSet rs) throws SQLException {
        NtpDataModel bean = new NtpDataModel();
        bean.setNipSid(rs.getInt("NIP_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setNipDate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_DATE")));
        bean.setNipFrTime(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_FR_TIME")));
        bean.setNipToTime(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_TO_TIME")));
        bean.setNipMgySid(rs.getInt("NIP_MGY_SID"));
        bean.setNanSid(rs.getInt("NAN_SID"));
        bean.setAcoSid(rs.getInt("ACO_SID"));
        bean.setAbaSid(rs.getInt("ABA_SID"));
        bean.setNipTitle(rs.getString("NIP_TITLE"));
        bean.setNipTitleClo(rs.getInt("NIP_TITLE_CLO"));
        bean.setMprSid(rs.getInt("MPR_SID"));
        bean.setMkbSid(rs.getInt("MKB_SID"));
        bean.setMkhSid(rs.getInt("MKH_SID"));
        bean.setNipTieupSid(rs.getInt("NIP_TIEUP_SID"));
        bean.setNipKeizoku(rs.getInt("NIP_KEIZOKU"));
        bean.setNipActend(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_ACTEND")));
        bean.setNipDetail(rs.getString("NIP_DETAIL"));
        bean.setNipAssign(rs.getString("NIP_ASSIGN"));
        bean.setNipKingaku(rs.getInt("NIP_KINGAKU"));
        bean.setNipMikomi(rs.getInt("NIP_MIKOMI"));
        bean.setNipSyokan(rs.getString("NIP_SYOKAN"));
        bean.setNipPublic(rs.getInt("NIP_PUBLIC"));
        bean.setNipEdit(rs.getInt("NIP_EDIT"));
        bean.setNexSid(rs.getInt("NEX_SID"));
        bean.setNipAuid(rs.getInt("NIP_AUID"));
        bean.setNipAdate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_ADATE")));
        bean.setNipEuid(rs.getInt("NIP_EUID"));
        bean.setNipEdate(UDate.getInstanceTimestamp(rs
                .getTimestamp("NIP_EDATE")));
        return bean;
    }
}
