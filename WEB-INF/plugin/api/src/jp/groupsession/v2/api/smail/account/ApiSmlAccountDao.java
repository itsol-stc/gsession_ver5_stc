package jp.groupsession.v2.api.smail.account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.GSConstSmail;

/**
 * <p> ショートメール アカウント情報を取得する際に使用するDAOクラス
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiSmlAccountDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ApiSmlAccountDao.class);

    /**
     * <p>Default Constructor
     */
    public ApiSmlAccountDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ApiSmlAccountDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アカウントSID一覧から自動送信先情報一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param  sacSidList アカウントSID一覧
     * @return アカウントSIDをキーにした自動送信先情報(SmlAccountAutoDestModel)一覧
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, List<ApiSmlAccountModel>> getAutoDestListMap(List<Integer> sacSidList)
                            throws SQLException {


        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<Integer, List<ApiSmlAccountModel>> ret
                                = new HashMap<Integer, List<ApiSmlAccountModel>>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   SAC_SID,");
            sql.addSql("   SAA_TYPE,");
            sql.addSql("   SAA_SID");
            sql.addSql(" from ");
            sql.addSql("   SML_ACCOUNT_AUTODEST");
            sql.addSql(" where ");
            sql.addSql("   exists (");
            sql.addSql("     select ");
            sql.addSql("       1");
            sql.addSql("     from ");
            sql.addSql("       SML_ACCOUNT");
            sql.addSql("     where ");
            sql.addSql("       SML_ACCOUNT_AUTODEST.SAA_SID = SML_ACCOUNT.SAC_SID ");
            sql.addSql("     and ");
            sql.addSql("       SML_ACCOUNT.SAC_JKBN= ? ");
            sql.addSql("   )");
            sql.addIntValue(GSConstSmail.SAC_JKBN_NORMAL);

            if (sacSidList != null) {
            sql.addSql(" and ");
            sql.addSql("   SAC_SID in (");
                for (int i = 0; i < sacSidList.size(); i++) {
                    if (i > 0) {
                        sql.addSql(" ,");
                    }
                    sql.addSql(" ?");
                    sql.addIntValue(sacSidList.get(i));
                }
                sql.addSql("   )");
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Integer key = Integer.valueOf(rs.getInt("SAC_SID"));
                List<ApiSmlAccountModel> list = ret.get(key);
                if (list == null) {
                    list = new ArrayList<ApiSmlAccountModel>();
                    ret.put(key, list);
                }
                ApiSmlAccountModel bean = new ApiSmlAccountModel();
                bean.setSacSid(rs.getInt("SAC_SID"));
                bean.setSaaType(rs.getInt("SAA_TYPE"));
                bean.setSaaSid(rs.getInt("SAA_SID"));
                list.add(bean);
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
     * <br>[機  能] ユーザSIDからアカウントSIDを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSids USR_SID
     * @return ユーザSIDをキーにしたアカウントSID配列
     * @throws SQLException SQL実行例外
     */
    public Map<Integer, Integer> selectFromUsrSids(List<Integer> usrSids) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        Map<Integer, Integer> ret = new HashMap<Integer, Integer>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   SAC_SID,");
            sql.addSql("   USR_SID");
            sql.addSql(" from");
            sql.addSql("   SML_ACCOUNT");
            sql.addSql(" where ");
            sql.addSql("   USR_SID in (");
            for (int i = 0; i < usrSids.size(); i++) {
                if (i > 0) {
                    sql.addSql(", ");
                }
                sql.addSql(usrSids.get(i).toString());
            }
            sql.addSql("   )");
            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.put(Integer.valueOf(rs.getInt("USR_SID")),
                        Integer.valueOf(rs.getInt("SAC_SID")));
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
    *
    * <br>[機  能] 無効ユーザのアカウントSIDを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param sacSid SAC_SID
    * @return 無効ユーザのSID一覧
    * @throws SQLException SQL実行例外
    */
   public HashMap<Integer, Integer> getUkoFlgMap(List<Integer> sacSid) throws SQLException {

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       con = getCon();
       HashMap<Integer, Integer> ret = new HashMap<Integer, Integer>();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select");
           sql.addSql("   SML_ACCOUNT.SAC_SID as SAC_SID,");
           sql.addSql("   case ");
           sql.addSql("     when CMN_USRM.USR_UKO_FLG IS NULL ");
           sql.addSql("     then " + GSConst.YUKOMUKO_YUKO);
           sql.addSql("     else CMN_USRM.USR_UKO_FLG");
           sql.addSql("   end USR_UKO_FLG ");
           sql.addSql(" from");
           sql.addSql("   SML_ACCOUNT");
           sql.addSql(" left join CMN_USRM");
           sql.addSql(" on CMN_USRM.USR_SID = SML_ACCOUNT.USR_SID");

           if (sacSid != null) {
               // アカウントSIDの条件がある場合
               sql.addSql(" where ");
               sql.addSql("   SML_ACCOUNT.SAC_SID in (");
               for (int i = 0; i < sacSid.size(); i++) {
                   sql.addSql((i > 0 ? ", ?" : "?"));
                   sql.addIntValue(sacSid.get(i));
               }
               sql.addSql("   )");
           }
           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           while (rs.next()) {
               ret.put(Integer.valueOf(rs.getInt("SAC_SID")),
                       Integer.valueOf(rs.getInt("USR_UKO_FLG")));
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closePreparedStatement(pstmt);
       }

       return ret;
   }
}
