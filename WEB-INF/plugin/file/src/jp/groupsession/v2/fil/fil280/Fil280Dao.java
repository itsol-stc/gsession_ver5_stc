package jp.groupsession.v2.fil.fil280;

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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.usr.GSConstUser;


/**
 * <br>[機  能] 管理者設定 キャビネット管理設定画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil280Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Fil280Dao.class);

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Fil280Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 個人キャビネット情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索条件
     * @return アカウント情報の一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Fil280DspModel> getCabinetList(Fil280SearchModel searchMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Fil280DspModel> ret = new ArrayList<Fil280DspModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   FILE_CABINET.FCB_SID as FCB_SID,");
            sql.addSql("   FILE_CABINET.FCB_NAME as FCB_NAME,");
            sql.addSql("   FILE_CABINET.FCB_ACCESS_KBN as FCB_ACCESS_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_KBN as FCB_CAPA_KBN,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_SIZE as FCB_CAPA_SIZE,");
            sql.addSql("   FILE_CABINET.FCB_CAPA_WARN as FCB_CAPA_WARN,");
            sql.addSql("   FILE_CABINET.FCB_VER_KBN as FCB_VER_KBN,");
            sql.addSql("   FILE_CABINET.FCB_VERALL_KBN as FCB_VERALL_KBN,");
            sql.addSql("   FILE_CABINET.FCB_BIKO as FCB_BIKO,");
            sql.addSql("   FILE_CABINET.FCB_SORT as FCB_SORT,");
            sql.addSql("   FILE_CABINET.FCB_MARK as FCB_MARK,");
            sql.addSql("   FILE_CABINET.FCB_AUID as FCB_AUID,");
            sql.addSql("   FILE_CABINET.FCB_ADATE as FCB_ADATE,");
            sql.addSql("   FILE_CABINET.FCB_EUID as FCB_EUID,");
            sql.addSql("   FILE_CABINET.FCB_EDATE as FCB_EDATE,");
            sql.addSql("   FILE_CABINET.FCB_PERSONAL_FLG as FCB_PERSONAL_FLG,");
            sql.addSql("   FILE_CABINET.FCB_OWNER_SID as FCB_OWNER_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI");
            sql.addSql(" from (");
            sql.addSql("   select");
            sql.addSql("     FCB_SID,");
            sql.addSql("     FCB_NAME,");
            sql.addSql("     FCB_ACCESS_KBN,");
            sql.addSql("     FCB_CAPA_KBN,");
            sql.addSql("     FCB_CAPA_SIZE,");
            sql.addSql("     FCB_CAPA_WARN,");
            sql.addSql("     FCB_VER_KBN,");
            sql.addSql("     FCB_VERALL_KBN,");
            sql.addSql("     FCB_BIKO,");
            sql.addSql("     FCB_SORT,");
            sql.addSql("     FCB_MARK,");
            sql.addSql("     FCB_AUID,");
            sql.addSql("     FCB_ADATE,");
            sql.addSql("     FCB_EUID,");
            sql.addSql("     FCB_EDATE,");
            sql.addSql("     FCB_PERSONAL_FLG,");
            sql.addSql("     FCB_OWNER_SID,");
            sql.addSql("     FCB_JKBN");
            sql.addSql("   from");
            sql.addSql("     FILE_CABINET");
            sql.addSql("   where ");
            sql.addSql("     FCB_PERSONAL_FLG=?");
            sql.addSql(" ) as FILE_CABINET");
            sql.addSql(" left join");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" on CMN_USRM_INF.USR_SID = FILE_CABINET.FCB_OWNER_SID");

            sql.addIntValue(GSConstFile.CABINET_KBN_PRIVATE);

            //検索条件
            if (searchMdl != null) {
                sql = __setSqlWhere(sql, searchMdl);
            }

            //並び順
            sql.addSql(" order by");
            sql.addSql("   FILE_CABINET.FCB_SORT");

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString(),
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Fil280DspModel model = new Fil280DspModel();
                model.setFcbSid(rs.getInt("FCB_SID"));
                model.setFcbAccessKbn(rs.getInt("FCB_ACCESS_KBN"));
                model.setFcbCapaKbn(rs.getInt("FCB_CAPA_KBN"));
                model.setFcbCapaSize(rs.getInt("FCB_CAPA_SIZE"));
                model.setFcbCapaWarn(rs.getInt("FCB_CAPA_WARN"));
                model.setFcbVerKbn(rs.getInt("FCB_VER_KBN"));
                model.setFcbVerallKbn(rs.getInt("FCB_VERALL_KBN"));
                model.setFcbBiko(rs.getString("FCB_BIKO"));
                model.setFcbSort(rs.getInt("FCB_SORT"));
                model.setFcbMark(rs.getLong("FCB_MARK"));
                model.setFcbAuid(rs.getInt("FCB_AUID"));
                model.setFcbAdate(UDate.getInstanceTimestamp(rs.getTimestamp("FCB_ADATE")));
                model.setFcbEuid(rs.getInt("FCB_EUID"));
                model.setFcbEdate(UDate.getInstanceTimestamp(rs.getTimestamp("FCB_EDATE")));
                model.setFcbPersonalFlg(rs.getInt("FCB_PERSONAL_FLG"));
                model.setFcbOwnerSid(rs.getInt("FCB_OWNER_SID"));

                if (!StringUtil.isNullZeroStringSpace(rs.getString("USI_SEI"))
                 && !StringUtil.isNullZeroStringSpace(rs.getString("USI_MEI"))) {
                    model.setFcbName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                } else {
                    model.setFcbName(rs.getString("FCB_NAME"));
                }
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
     * <br>[機  能] SqlBufferに検索条件を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索条件Model
     * @return SqlBuffer
     */
    private SqlBuffer __setSqlWhere(SqlBuffer sql, Fil280SearchModel searchMdl) {
        sql.addSql(" where");
        sql.addSql("   CMN_USRM_INF.USR_SID > ?");
        sql.addIntValue(GSConstUser.SID_SYSTEM_MAIL);

        //キーワード
        if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {
            sql.addSql(" and ");
            sql.addSql("   (CMN_USRM_INF.USI_SEI || CMN_USRM_INF.USI_MEI) like '%"
                    + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                    + "%' ESCAPE '" + JDBCUtil.def_esc + "' ");
        }

        //グループSID
        if (searchMdl.getGrpSid() >= 0) {
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_OWNER_SID in (");
            sql.addSql("     select USR_SID from CMN_BELONGM");
            sql.addSql("     where GRP_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(searchMdl.getGrpSid());
        }

        //ユーザSID
        if (searchMdl.getUserSid() > 0) {
            sql.addSql(" and");
            sql.addSql("   FILE_CABINET.FCB_OWNER_SID = ?");
            sql.addIntValue(searchMdl.getUserSid());
        }

        // 許可ユーザSID一覧
        if (searchMdl.getPermitUsrSids() != null && searchMdl.getPermitUsrSids().size() > 0) {
            sql.addSql(" and (");

            int maxCnt  = searchMdl.getPermitUsrSids().size(); // 許可ユーザ件数
            int roopCnt = 200; // or句による区切り件数(固定)
            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < maxCnt; i += roopCnt) {
                // or句による分割
                if (i > 0) {
                    strBuf.append("   or");
                }
                strBuf.append("   FILE_CABINET.FCB_OWNER_SID in (");

                // in句による条件指定
                int readCnt = (roopCnt > maxCnt - i ? maxCnt - i : roopCnt);
                for (int j = 0; j < readCnt; j++) {
                    searchMdl.getPermitUsrSids();

                    Integer usrSid = searchMdl.getPermitUsrSids().get(i + j);
                    if (j > 0) {
                        strBuf.append(",");
                    }
                    strBuf.append(usrSid);
                }
                strBuf.append("   )");

                sql.addSql("   " + strBuf.toString());
            }
            sql.addSql("   )");
        }

        sql.addSql(" and ");
        sql.addSql("   FILE_CABINET.FCB_JKBN = ?");
        sql.addIntValue(GSConstFile.JTKBN_NORMAL);
        return sql;
    }
}
