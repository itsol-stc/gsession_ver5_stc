package jp.groupsession.v2.ip.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.model.IpkNetModel;

/**
 * <br>[機  能] ネットワーク情報に関するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkNetDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkNetDao.class);

    /**
     * <p>Default Constructor
     */
    public IpkNetDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public IpkNetDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] ネットワークの一覧のリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkNetModel> select()
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkNetModel> ret = new ArrayList<IpkNetModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   INT_SID,");
            sql.addSql("   INT_NAME,");
            sql.addSql("   INT_NETAD,");
            sql.addSql("   INT_SABNET,");
            sql.addSql("   INT_DSP,");
            sql.addSql("   INT_MSG,");
            sql.addSql("   INT_SORT,");
            sql.addSql("   INT_AUID,");
            sql.addSql("   INT_ADATE,");
            sql.addSql("   INT_EUID,");
            sql.addSql("   INT_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_NET");
            sql.addSql(" order by INT_SORT, INT_NAME, INT_SID");
            pstmt = con.prepareStatement(sql.toSqlString());

            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkNetModelFromRs(rs));
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
     * <br>[機  能] ネットワークの一覧のリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param notDspNetSidList 非公開ネットワークSIDリスト
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkNetModel> getDspNetInf(ArrayList<Integer> notDspNetSidList)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkNetModel> ret = new ArrayList<IpkNetModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   INT_SID,");
            sql.addSql("   INT_NAME,");
            sql.addSql("   INT_NETAD,");
            sql.addSql("   INT_SABNET,");
            sql.addSql("   INT_DSP,");
            sql.addSql("   INT_MSG,");
            sql.addSql("   INT_SORT,");
            sql.addSql("   INT_AUID,");
            sql.addSql("   INT_ADATE,");
            sql.addSql("   INT_EUID,");
            sql.addSql("   INT_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_NET");

            if (notDspNetSidList != null && notDspNetSidList.size() > 0) {
                sql.addSql("   where");
                for (int i = 0; notDspNetSidList.size() > i; i++) {
                    if (i != 0) {
                        sql.addSql("   and");
                    }
                    sql.addSql("   INT_SID <> " + notDspNetSidList.get(i));
                }
            }
            sql.addSql(" order by INT_SORT, INT_NAME, INT_SID");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkNetModelFromRs(rs));
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
     * <br>[機  能] 一つのネットワークのネットワーク情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  netSid int
     * @return IpkNetModel
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkNetModel> selectNetwork(int netSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkNetModel> ret = new ArrayList<IpkNetModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   INT_SID,");
            sql.addSql("   INT_NAME,");
            sql.addSql("   INT_NETAD,");
            sql.addSql("   INT_SABNET,");
            sql.addSql("   INT_DSP,");
            sql.addSql("   INT_MSG,");
            sql.addSql("   INT_SORT,");
            sql.addSql("   INT_AUID,");
            sql.addSql("   INT_ADATE,");
            sql.addSql("   INT_EUID,");
            sql.addSql("   INT_EDATE ");
            sql.addSql(" from");
            sql.addSql("   IPK_NET");
            sql.addSql(" where");
            sql.addSql("   INT_SID= ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(netSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkNetModelFromRs(rs));
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
     * <br>[機  能] ネットワーク一覧情報を格納したモデルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return bean IpkModel
     * @throws SQLException SQL実行例外
     */
    private IpkNetModel __getIpkNetModelFromRs(ResultSet rs)
    throws SQLException {

        IpkNetModel model = new IpkNetModel();
        model.setNetSid(rs.getInt("INT_SID"));
        model.setNetName(rs.getString("INT_NAME"));
        model.setNetNetad(rs.getString("INT_NETAD"));
        model.setNetSabnet(rs.getString("INT_SABNET"));
        model.setNetDsp(rs.getString("INT_DSP"));
        model.setNetMsg(rs.getString("INT_MSG"));
        model.setNetSort(rs.getInt("INT_SORT"));
        model.setNetAuid(rs.getInt("INT_AUID"));
        model.setNetAdate(UDate.getInstanceTimestamp(rs.getTimestamp("INT_ADATE")));
        model.setNetEuid(rs.getInt("INT_EUID"));
        model.setNetEdate(UDate.getInstanceTimestamp(rs.getTimestamp("INT_EDATE")));
        return model;
    }

    /**
     * <br>[機  能] ネットワーク情報を変更する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkModel
     * @throws SQLException SQL実行例外
     */
    public void updateNetworkData(IpkNetModel model) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   IPK_NET");
            sql.addSql(" set");
            sql.addSql("   INT_SID = ?,");
            sql.addSql("   INT_NAME = ?,");
            sql.addSql("   INT_NETAD = ?,");
            sql.addSql("   INT_SABNET = ?,");
            sql.addSql("   INT_DSP = ?,");
            sql.addSql("   INT_MSG = ?,");
            sql.addSql("   INT_SORT = ? ,");
            sql.addSql("   INT_EUID = ?,");
            sql.addSql("   INT_EDATE= ?");
            sql.addSql(" where ");
            sql.addSql("   INT_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getNetSid());
            sql.addStrValue(model.getNetName());
            sql.addStrValue(model.getNetNetad());
            sql.addStrValue(model.getNetSabnet());
            sql.addIntValue(NullDefault.getInt(model.getNetDsp(), 0));
            sql.addStrValue(model.getNetMsg());
            sql.addIntValue(model.getNetSort());
            sql.addIntValue(model.getNetEuid());
            sql.addDateValue(model.getNetEdate());
            sql.addIntValue(model.getNetSid());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 非公開ネットワークのネットワークSIDリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<Integer> getNotDspNetSid(int usrSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Integer> ret = new ArrayList<Integer>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   IPK_NET.INT_SID,");
            sql.addSql("   IPK_NET.INT_NAME,");
            sql.addSql("   IPK_NET.INT_NETAD,");
            sql.addSql("   IPK_NET.INT_SABNET,");
            sql.addSql("   IPK_NET.INT_DSP,");
            sql.addSql("   IPK_NET.INT_MSG,");
            sql.addSql("   IPK_NET.INT_SORT,");
            sql.addSql("   IPK_NET.INT_AUID,");
            sql.addSql("   IPK_NET.INT_ADATE,");
            sql.addSql("   IPK_NET.INT_EUID,");
            sql.addSql("   IPK_NET.INT_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_NET left join IPK_NET_ADM on");
            sql.addSql("   IPK_NET.INT_SID = IPK_NET_ADM.INT_SID");

            sql.addSql(" where");
            sql.addSql("   IPK_NET.INT_SID not in (");
            sql.addSql("     select IPK_NET.INT_SID from IPK_NET, IPK_NET_ADM");
            sql.addSql("     where IPK_NET.INT_SID = IPK_NET_ADM.INT_SID");
            sql.addSql("     and IPK_NET_ADM.USR_SID = ?)");
            sql.addSql(" and");
            sql.addSql("   IPK_NET.INT_DSP = ?");
            sql.addSql("   order by INT_SORT, INT_NAME, INT_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addIntValue(Integer.parseInt(IpkConst.IPK_NET_NOT_DSP));
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getInt("INT_SID"));
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
     * <br>[機  能] ネットワーク情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkModel
     * @throws SQLException SQL実行例外
     */
    public void insertConv(IpkNetModel model) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql("  IPK_NET(");
            sql.addSql("   INT_SID,");
            sql.addSql("   INT_NAME,");
            sql.addSql("   INT_NETAD,");
            sql.addSql("   INT_SABNET,");
            sql.addSql("   INT_DSP,");
            sql.addSql("   INT_MSG,");
            sql.addSql("   INT_SORT,");
            sql.addSql("   INT_AUID,");
            sql.addSql("   INT_ADATE,");
            sql.addSql("   INT_EUID,");
            sql.addSql("   INT_EDATE");
            sql.addSql(" ) values ( ");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?);");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getNetSid());
            sql.addStrValue(model.getNetName());
            sql.addStrValue(model.getNetNetad());
            sql.addStrValue(model.getNetSabnet());
            sql.addIntValue(NullDefault.getInt(model.getNetDsp(), 0));
            sql.addStrValue(model.getNetMsg());
            sql.addIntValue(model.getNetSort());
            sql.addIntValue(model.getUsrSid());
            sql.addDateValue(model.getNetAdate());
            sql.addIntValue(model.getUsrSid());
            sql.addDateValue(model.getNetEdate());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
    /**
     * <br>[機  能] ネットワーク情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkModel
     * @throws SQLException SQL実行例外
     */
    public void insert(IpkNetModel model) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql("  IPK_NET(");
            sql.addSql("   INT_SID,");
            sql.addSql("   INT_NAME,");
            sql.addSql("   INT_NETAD,");
            sql.addSql("   INT_SABNET,");
            sql.addSql("   INT_DSP,");
            sql.addSql("   INT_MSG,");
            sql.addSql("   INT_SORT,");
            sql.addSql("   INT_AUID,");
            sql.addSql("   INT_ADATE,");
            sql.addSql("   INT_EUID,");
            sql.addSql("   INT_EDATE");
            sql.addSql(" ) values ( ");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?);");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getNewNetSid());
            sql.addStrValue(model.getNetName());
            sql.addStrValue(model.getNetNetad());
            sql.addStrValue(model.getNetSabnet());
            sql.addIntValue(NullDefault.getInt(model.getNetDsp(), 0));
            sql.addStrValue(model.getNetMsg());
            sql.addIntValue(model.getNetSort());
            sql.addIntValue(model.getUsrSid());
            sql.addDateValue(model.getNetAdate());
            sql.addIntValue(model.getUsrSid());
            sql.addDateValue(model.getNetEdate());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

   /**
    * <br>[機  能] ネットワーク情報を削除する。
    * <br>[解  説]
    * <br>[備  考]
    * @param netSid ネットワークSID
    * @throws SQLException SQL実行例外
    */
   public void deleteNetwork(int netSid) throws SQLException {

       PreparedStatement pstmt = null;
       Connection con = null;
       con = getCon();
       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" delete from");
           sql.addSql("   IPK_NET");
           sql.addSql(" where ");
           sql.addSql("   INT_SID= ?");
           pstmt = con.prepareStatement(sql.toSqlString());
           sql.addIntValue(netSid);
           sql.setParameter(pstmt);
           log__.info(sql.toLogString());
           pstmt.executeUpdate();
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * <br>[機  能] ネットワークアドレスリストを取得する。
    * <br>[解  説]
    * <br>[備  考]
    * @return ret ArrayList ネットワークアドレスリスト
    * @throws SQLException SQL実行例外
    */
   public ArrayList<String> selectNetad() throws SQLException {

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       ArrayList<String> ret = new ArrayList<String>();
       con = getCon();
       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select");
           sql.addSql("   INT_NETAD");
           sql.addSql(" from");
           sql.addSql("   IPK_NET");
           pstmt = con.prepareStatement(sql.toSqlString());
           log__.info(sql.toLogString());
           rs = pstmt.executeQuery();
           while (rs.next()) {
               ret.add(rs.getString("INT_NETAD"));
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
    * <br>[機  能] ネットワーク情報のデータサイズを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @param netSid INT_SID
    * @return ネットワーク情報のデータサイズ
    * @throws SQLException SQL実行例外
    */
   public long getNetworkDataSize(int netSid) throws SQLException {

       long dataSize = 0;

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       Connection con = null;
       con = getCon();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select");
           sql.addSql("   sum(octet_length(INT_NAME)) as NAME_SIZE,");
           sql.addSql("   sum(octet_length(INT_MSG)) as MSG_SIZE");
           sql.addSql(" from");
           sql.addSql("   IPK_NET");
           sql.addSql(" where ");
           sql.addSql("   INT_SID = ?");
           sql.addIntValue(netSid);

           log__.info(sql.toLogString());

           pstmt = con.prepareStatement(sql.toSqlString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();

           if (rs.next()) {
               dataSize = rs.getLong("NAME_SIZE");
               dataSize += rs.getLong("MSG_SIZE");
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closeStatement(pstmt);
       }
       return dataSize;
   }
}