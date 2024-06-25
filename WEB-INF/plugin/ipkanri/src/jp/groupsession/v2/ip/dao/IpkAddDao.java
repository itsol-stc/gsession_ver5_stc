package jp.groupsession.v2.ip.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.ip.IpkConst;
import jp.groupsession.v2.ip.model.IpkAddModel;
import jp.groupsession.v2.ip.model.IpkSearchModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] IPアドレス情報に関するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkAddDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(IpkAddDao.class);

    /**
     * <p>Default Constructor
     */
    public IpkAddDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public IpkAddDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] IPアドレス情報を変更する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkAddModel
     * @throws SQLException SQL実行例外
     */
    public void update(IpkAddModel model) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   IPK_ADD");
            sql.addSql(" set");
            sql.addSql("   INT_SID = ?,");
            sql.addSql("   IAD_SID = ?,");
            sql.addSql("   IAD_NAME = ?,");
            sql.addSql("   IAD_IPAD = ?,");
            sql.addSql("   IAD_USED_KBN = ?,");
            sql.addSql("   IAD_MSG = ?,");
            sql.addSql("   IAD_PRTMNG_NUM =? ,");
            sql.addSql("   IAD_CPU= ? ,");
            sql.addSql("   IAD_MEMORY = ?,");
            sql.addSql("   IAD_HD = ?,");
            sql.addSql("   IAD_EUID = ?,");
            sql.addSql("   IAD_EDATE = ?");
            sql.addSql(" where ");
            sql.addSql("   IAD_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getNetSid());
            sql.addIntValue(model.getNewIadSid());
            sql.addStrValue(model.getIadName());
            sql.addLongValue(model.getIadIpad());
            sql.addIntValue(NullDefault.getInt(model.getIadUseKbn(), 0));
            sql.addStrValue(model.getIadMsg());
            sql.addStrValue(model.getIadPrtMngNum());
            sql.addIntValue(model.getIadCpu());
            sql.addIntValue(model.getIadMemory());
            sql.addIntValue(model.getIadHd());
            sql.addIntValue(model.getIadEuid());
            sql.addDateValue(model.getIadEdate());
            sql.addIntValue(model.getIadSid());
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
     * <br>[機  能] IPアドレス情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkAddModel
     * @throws SQLException SQL実行例外
     */
    public void insert(IpkAddModel model) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql("  IPK_ADD(");
            sql.addSql("   IAD_SID,");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_NAME,");
            sql.addSql("   IAD_IPAD,");
            sql.addSql("   IAD_USED_KBN,");
            sql.addSql("   IAD_MSG,");
            sql.addSql("   IAD_PRTMNG_NUM,");
            sql.addSql("   IAD_CPU,");
            sql.addSql("   IAD_MEMORY,");
            sql.addSql("   IAD_HD,");
            sql.addSql("   IAD_AUID,");
            sql.addSql("   IAD_ADATE,");
            sql.addSql("   IAD_EUID,");
            sql.addSql("   IAD_EDATE");
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
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?);");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getNewIadSid());
            sql.addIntValue(model.getNetSid());
            sql.addStrValue(model.getIadName());
            sql.addLongValue(model.getIadIpad());
            sql.addIntValue(NullDefault.getInt(model.getIadUseKbn(), 0));
            sql.addStrValue(model.getIadMsg());
            sql.addStrValue(model.getIadPrtMngNum());
            sql.addIntValue(model.getIadCpu());
            sql.addIntValue(model.getIadMemory());
            sql.addIntValue(model.getIadHd());
            sql.addIntValue(model.getIadAuid());
            sql.addDateValue(model.getIadAdate());
            sql.addIntValue(model.getIadEuid());
            sql.addDateValue(model.getIadEdate());
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
     * <br>[機  能] IPアドレス情報を登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  model IpkAddModel
     * @throws SQLException SQL実行例外
     */
    public void insertConv(IpkAddModel model) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into ");
            sql.addSql("  IPK_ADD(");
            sql.addSql("   IAD_SID,");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_NAME,");
            sql.addSql("   IAD_IPAD,");
            sql.addSql("   IAD_USED_KBN,");
            sql.addSql("   IAD_MSG,");
            sql.addSql("   IAD_PRTMNG_NUM,");
            sql.addSql("   IAD_CPU,");
            sql.addSql("   IAD_MEMORY,");
            sql.addSql("   IAD_HD,");
            sql.addSql("   IAD_AUID,");
            sql.addSql("   IAD_ADATE,");
            sql.addSql("   IAD_EUID,");
            sql.addSql("   IAD_EDATE");
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
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?,");
            sql.addSql("    ?);");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getIadSid());
            sql.addIntValue(model.getNetSid());
            sql.addStrValue(model.getIadName());
            sql.addLongValue(model.getIadIpad());
            sql.addIntValue(NullDefault.getInt(model.getIadUseKbn(), 0));
            sql.addStrValue(model.getIadMsg());
            sql.addStrValue(model.getIadPrtMngNum());
            sql.addIntValue(model.getIadCpu());
            sql.addIntValue(model.getIadMemory());
            sql.addIntValue(model.getIadHd());
            sql.addIntValue(model.getIadAuid());
            sql.addDateValue(model.getIadAdate());
            sql.addIntValue(model.getIadEuid());
            sql.addDateValue(model.getIadEdate());
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
     * <br>[機  能] 一つのIPアドレス情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  iadSid int
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkAddModel> selectIad(int iadSid)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkAddModel> ret = new ArrayList<IpkAddModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_NAME,");
            sql.addSql("   IAD_USED_KBN,");
            sql.addSql("   IAD_IPAD,");
            sql.addSql("   IAD_MSG,");
            sql.addSql("   IAD_PRTMNG_NUM, ");
            sql.addSql("   IAD_CPU,");
            sql.addSql("   IAD_MEMORY,");
            sql.addSql("   IAD_HD,");
            sql.addSql("   IAD_AUID,");
            sql.addSql("   IAD_ADATE,");
            sql.addSql("   IAD_EUID,");
            sql.addSql("   IAD_EDATE ");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD");
            sql.addSql(" where");
            sql.addSql("   IAD_SID= ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(iadSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkAddModelFromRs2(rs));
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
     * <br>[機  能] IPアドレスを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param iadSid IPアドレスSID
     * @throws SQLException SQL実行例外
     */
    public void delete(int iadSid) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   IPK_ADD");
            sql.addSql(" where ");
            sql.addSql("     IAD_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(iadSid);
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
     * <br>[機  能] リスト内のIPアドレスを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param deleteIadSids IPアドレスSIDリスト
     * @throws SQLException SQL実行例外
     */
    public void deleteArrayIpad(ArrayList<Integer> deleteIadSids) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   IPK_ADD");
            sql.addSql(" where ");
            for (int i = 0; i < deleteIadSids.size(); i++) {
                if (i == 0) {
                    sql.addSql("   IAD_SID in(" + deleteIadSids.get(i));
                } else if (i > 0) {
                    sql.addSql("," + deleteIadSids.get(i));
                }
            }
            sql.addSql(")");
            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 1つのネットワークのIPアドレスを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSid
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
            sql.addSql("   IPK_ADD");
            sql.addSql(" where ");
            sql.addSql(" INT_SID=?");
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
     * <br>[機  能] 使用中のIPアドレス登録台数情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @return iadCountUse 使用中のIPアドレスの登録台数
     * @throws SQLException SQL実行例外
     */
    public String countIadUse(int netSid) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        String iadCountUse = "";
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD");
            sql.addSql(" where");
            sql.addSql("   INT_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   IAD_USED_KBN=1");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(netSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            rs = pstmt.executeQuery();
            while (rs.next()) {
                iadCountUse = rs.getString("CNT");
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return iadCountUse;
    }

    /**
     * <br>[機  能] 未使用のIPアドレス登録台数情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param netSid ネットワークSID
     * @return iadCountUse 使用中のIPアドレスの登録台数
     * @throws SQLException SQL実行例外
     */
    public String countIadNotUse(int netSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        String iadCountNotUse = "";
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD");
            sql.addSql(" where");
            sql.addSql("   INT_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   IAD_USED_KBN=0");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(netSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            rs = pstmt.executeQuery();
            while (rs.next()) {
                iadCountNotUse = rs.getString("CNT");
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return iadCountNotUse;
    }

    /**
     * <br>[機  能] IPアドレス一覧表示のためのリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ipkAddModel IPアドレスモデル
     * @param offset オフセット
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkAddModel> select(
            IpkAddModel ipkAddModel, boolean offset)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkAddModel> ret = new ArrayList<IpkAddModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IAD_SID,");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_NAME,");
            sql.addSql("   IAD_IPAD,");
            sql.addSql("   IAD_USED_KBN,");
            sql.addSql("   IAD_MSG,");
            sql.addSql("   IAD_PRTMNG_NUM,");
            sql.addSql("   IAD_CPU,");
            sql.addSql("   IAD_MEMORY,");
            sql.addSql("   IAD_HD,");
            sql.addSql("   IAD_AUID,");
            sql.addSql("   IAD_ADATE,");
            sql.addSql("   IAD_EUID,");
            sql.addSql("   IAD_EDATE,");
            sql.addSql("   ISM_SID,");
            sql.addSql("   ISM_LEVEL");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD left join IPK_SPECM on ");
            if (ipkAddModel.getSortKey() == IpkConst.IPK_SORT_MEMORY) {
                sql.addSql("   IPK_ADD.IAD_MEMORY = IPK_SPECM.ISM_SID ");
            } else if (ipkAddModel.getSortKey() == IpkConst.IPK_SORT_HD) {
                sql.addSql("   IPK_ADD.IAD_HD = IPK_SPECM.ISM_SID ");
            } else {
                sql.addSql("   IPK_ADD.IAD_CPU = IPK_SPECM.ISM_SID ");
            }

            sql.addSql(" where ");
            sql.addSql("   INT_SID = ?");

            if (ipkAddModel.getIadUseKbn().equals(IpkConst.USEDKBN_SIYOU)) {
                sql.addSql(" and  IAD_USED_KBN=1");
            } else if (ipkAddModel.getIadUseKbn().equals(IpkConst.USEDKBN_MISIYOU)) {
                sql.addSql(" and  IAD_USED_KBN=0");
            }
            sql.addSql(" order by ");
            //ソート順指定
            switch (ipkAddModel.getSortKey()) {
            case IpkConst.IPK_SORT_IPAD:
                sql.addSql("   IAD_IPAD ");
                break;
            case IpkConst.IPK_SORT_NAME:
                sql.addSql("   IAD_NAME");
                break;
            case IpkConst.IPK_SORT_USEDKBN:
                sql.addSql("   IAD_USED_KBN ");
                break;
            case IpkConst.IPK_SORT_MSG:
                sql.addSql("   IAD_MSG ");
                break;
            case IpkConst.IPK_SORT_CPU:
                sql.addSql("   ISM_LEVEL ");
                break;
            case IpkConst.IPK_SORT_MEMORY:
                sql.addSql("   ISM_LEVEL ");
                break;
            case IpkConst.IPK_SORT_HD:
                sql.addSql("   ISM_LEVEL ");
                break;
            default:
                sql.addSql("   IAD_IPAD ");
            }
            //ソートが昇順か降順かを指定
            if (ipkAddModel.getOrderKey() == IpkConst.IPK_ORDER_ASC) {
                sql.addSql(" asc ");
            } else if (ipkAddModel.getOrderKey() == IpkConst.IPK_ORDER_DESC) {
                sql.addSql(" desc ");
            }

            pstmt = con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
                    );
            sql.addIntValue(ipkAddModel.getNetSid());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();

            //結果セットを何行目から取得するのか
            if (ipkAddModel.getIadOffset() > 1 && offset) {
                rs.absolute(ipkAddModel.getIadOffset() - 1);
            }

            if (offset) {
                for (int i = 0; rs.next() && i < ipkAddModel.getIadLimit(); i++) {
                    ret.add(__getIpkAddModelFromRs(rs));
                }
            } else {
                while (rs.next()) {
                    ret.add(__getIpkAddModelFromRs(rs));
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
     * <br>[機  能] IPアドレス一覧エクスポートのためのリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ipkAddModel IPアドレスモデル
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkAddModel> selectExport(
            IpkAddModel ipkAddModel)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkAddModel> ret = new ArrayList<IpkAddModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IAD_SID,");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_NAME,");
            sql.addSql("   IAD_IPAD,");
            sql.addSql("   IAD_USED_KBN,");
            sql.addSql("   IAD_MSG,");
            sql.addSql("   IAD_PRTMNG_NUM,");
            sql.addSql("   IAD_CPU,");
            sql.addSql("   IAD_MEMORY,");
            sql.addSql("   IAD_HD,");
            sql.addSql("   IAD_AUID,");
            sql.addSql("   IAD_ADATE,");
            sql.addSql("   IAD_EUID,");
            sql.addSql("   IAD_EDATE");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD");
            sql.addSql(" where ");
            sql.addSql("   INT_SID = ?");

            if (ipkAddModel.getIadUseKbn().equals(IpkConst.USEDKBN_SIYOU)) {
                sql.addSql(" and  IAD_USED_KBN=1");
            } else if (ipkAddModel.getIadUseKbn().equals(IpkConst.USEDKBN_MISIYOU)) {
                sql.addSql(" and  IAD_USED_KBN=0");
            }
            //ソート順指定
            switch (ipkAddModel.getSortKey()) {
            case IpkConst.IPK_SORT_IPAD:
                sql.addSql(" order by ");
                sql.addSql("   IAD_IPAD ");
                break;
            case IpkConst.IPK_SORT_NAME:
                sql.addSql(" order by ");
                sql.addSql("   IAD_NAME");
                break;
            case IpkConst.IPK_SORT_USEDKBN:
                sql.addSql(" order by ");
                sql.addSql("   IAD_USED_KBN ");
                break;
            case IpkConst.IPK_SORT_MSG:
                sql.addSql(" order by ");
                sql.addSql("   IAD_MSG ");
                break;
            default:
                sql.addSql(" order by ");
                sql.addSql("   IAD_IPAD ");
            }
            //ソートが昇順か降順かを指定
            if (ipkAddModel.getOrderKey() == IpkConst.IPK_ORDER_ASC) {
                sql.addSql(" asc ");
            } else if (ipkAddModel.getOrderKey() == IpkConst.IPK_ORDER_DESC) {
                sql.addSql(" desc ");
            }

            pstmt = con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
                    );
            sql.addIntValue(ipkAddModel.getNetSid());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkAddModelFromRs(rs));
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
     * <br>[機  能] IPアドレスSIDリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  netSid int
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> selectIadSid(int netSid)
            throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<String> ret = new ArrayList<String>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IAD_SID,");
            sql.addSql("   INT_SID");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD");
            sql.addSql(" where");
            sql.addSql("   INT_SID= ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(netSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("IAD_SID"));
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
     * <p>Select IPK_ADD All Data
     * @return List in IPK_ADDModel
     * @throws SQLException SQL実行例外
     */
    public List<IpkAddModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkAddModel> ret = new ArrayList<IpkAddModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   IAD_SID,");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_NAME,");
            sql.addSql("   IAD_IPAD,");
            sql.addSql("   IAD_USED_KBN,");
            sql.addSql("   IAD_MSG,");
            sql.addSql("   IAD_AUID,");
            sql.addSql("   IAD_ADATE,");
            sql.addSql("   IAD_EUID,");
            sql.addSql("   IAD_EDATE,");
            sql.addSql("   IAD_PRTMNG_NUM,");
            sql.addSql("   IAD_CPU,");
            sql.addSql("   IAD_MEMORY,");
            sql.addSql("   IAD_HD");
            sql.addSql(" from ");
            sql.addSql("   IPK_ADD");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getIpkAddFromRs(rs));
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
     * <br>[機  能] 同じIPアドレスの数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param model IpkAddModel
     * @return ret  同じIPアドレスの数
     * @throws SQLException SQL実行例外
     */
    public int countIpad(IpkAddModel model) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int ret = 10;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("   IPK_ADD");
            sql.addSql(" where");
            sql.addSql("   INT_SID = ?");
            sql.addSql(" and ");
            sql.addSql("   IAD_IPAD = ?");
            if (model.getCmd().equals("iadEdit")) {
                sql.addSql(" and ");
                sql.addSql("   IAD_IPAD != ?");
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getNetSid());
            sql.addLongValue(model.getIadIpad());
            if (model.getCmd().equals("iadEdit")) {
                sql.addLongValue(model.getBeforeIpad());
            }
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            //SQL実行
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
     * <br>[機  能] 検索結果一覧表示のためのリストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ipkSearchModel IP検索モデル
     * @param offset オフセット
     * @return ret ArrayList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<IpkAddModel> selectSearchResult(
            IpkSearchModel ipkSearchModel, boolean offset)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<IpkAddModel> ret = new ArrayList<IpkAddModel>();
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   IAD_SID,");
            sql.addSql("   INT_SID,");
            sql.addSql("   IAD_NAME,");
            sql.addSql("   IAD_IPAD,");
            sql.addSql("   IAD_USED_KBN,");
            sql.addSql("   IAD_MSG,");
            sql.addSql("   IAD_PRTMNG_NUM,");
            sql.addSql("   IAD_CPU,");
            sql.addSql("   IAD_MEMORY,");
            sql.addSql("   IAD_HD,");
            sql.addSql("   IAD_AUID,");
            sql.addSql("   IAD_ADATE,");
            sql.addSql("   IAD_EUID,");
            sql.addSql("   IAD_EDATE,");
            sql.addSql("   IPAD, ");
            sql.addSql("   INT_NETAD,");
            sql.addSql("   INT_SABNET,");
            sql.addSql("   INT_DSP");
            sql.addSql("  from ");
            sql.addSql("  ( ");

            sql.addSql(" select");
            sql.addSql("   IPK_ADD.IAD_SID,");
            sql.addSql("   IPK_ADD.IAD_NAME,");
            sql.addSql("   IPK_ADD.IAD_IPAD,");
            sql.addSql("   IPK_ADD.IAD_USED_KBN,");
            sql.addSql("   IPK_ADD.IAD_MSG,");
            sql.addSql("   IPK_ADD.IAD_PRTMNG_NUM,");
            sql.addSql("   IPK_ADD.IAD_CPU,");
            sql.addSql("   IPK_ADD.IAD_MEMORY,");
            sql.addSql("   IPK_ADD.IAD_HD,");
            sql.addSql("   IPK_ADD.IAD_AUID,");
            sql.addSql("   IPK_ADD.IAD_ADATE,");
            sql.addSql("   IPK_ADD.IAD_EUID,");
            sql.addSql("   IPK_ADD.IAD_EDATE,");
            sql.addSql("   IPK_NET.INT_DSP,");
            sql.addSql("   ltrim(substr (cast(IPK_ADD.IAD_IPAD as char), 1, 3), '0') || '.' ");
            sql.addSql("   || ltrim(substr(cast(IPK_ADD.IAD_IPAD as char), 4, 3), '0')  || '.' ");
            sql.addSql("   || ltrim(substr (cast(IPK_ADD.IAD_IPAD as char), 7, 3), '0') || '.' ");
            sql.addSql("   || ltrim(substr (cast(IPK_ADD.IAD_IPAD as char), 10, 3), '0') as IPAD,");
            sql.addSql("   IPK_NET.INT_NETAD,");
            sql.addSql("   IPK_NET.INT_SABNET,");
            sql.addSql("   IPK_NET.INT_SID,");
            sql.addSql("   (select ISM_LEVEL");
            sql.addSql("       from IPK_SPECM where ISM_SID=IAD_CPU) as CPU,");
            sql.addSql("   (select ISM_LEVEL");
            sql.addSql("       from IPK_SPECM where ISM_SID=IAD_MEMORY) as MEMORY,");
            sql.addSql("   (select ISM_LEVEL");
            sql.addSql("       from IPK_SPECM where ISM_SID=IAD_HD) as HD");

            sql.addSql(" from");
            sql.addSql("   (IPK_ADD left join IPK_NET on IPK_ADD.INT_SID=IPK_NET.INT_SID)");
            sql.addSql("   ) as IPKADD ");

            sql.addSql(" where ");
            if (ipkSearchModel.getNetSid() == 0) {
                sql.addSql("   INT_SID > 0");
            } else {
                sql.addSql("   INT_SID = ?");
                sql.addIntValue(ipkSearchModel.getNetSid());
            }

            //非公開ネットワーク
            if (ipkSearchModel.getNotDspNetSidList() != null) {
                for (int notDspNetSid : ipkSearchModel.getNotDspNetSidList()) {
                    sql.addSql("   and");
                    sql.addSql("   INT_SID <> " + notDspNetSid);
                }
            }
            //検索キーワード
            __sqlSetUpForKeyword(sql, ipkSearchModel);

            //使用者指定
            if (ipkSearchModel.getAdmGrpSid() != -1) {
                sql.addSql(" and ");
                sql.addSql("   IAD_SID in");
                if (ipkSearchModel.getAdmUsrSid() != -1) {
                    sql.addSql("   (select IAD_SID from IPK_ADD_ADM where USR_SID = ? )");
                    sql.addIntValue(ipkSearchModel.getAdmUsrSid());
                } else {
                    //使用者が指定無しの場合、選択されているグループの全ユーザで検索する。
                    sql.addSql("   (select IAD_SID from IPK_ADD_ADM where USR_SID in ");
                    sql.addSql(" (select USR_SID from CMN_BELONGM where GRP_SID = ?))");
                    sql.addIntValue(ipkSearchModel.getAdmGrpSid());
                }
            }

            //ソート順指定
            sql.addSql(" order by ");
            switch (ipkSearchModel.getSortKey1()) {
            case IpkConst.IPK_SORT_IPAD:
                sql.addSql("   IAD_IPAD ");
                break;
            case IpkConst.IPK_SORT_NAME:
                sql.addSql("   IAD_NAME");
                break;
            case IpkConst.IPK_SORT_USEDKBN:
                sql.addSql("   IAD_USED_KBN ");
                break;
            case IpkConst.IPK_SORT_MSG:
                sql.addSql("   IAD_MSG ");
                break;
            case IpkConst.IPK_SORT_NETAD:
                sql.addSql("   INT_NETAD ");
                break;
            case IpkConst.IPK_SORT_SUBNET:
                sql.addSql("   INT_SABNET ");
                break;
            case IpkConst.IPK_SORT_CPU:
                sql.addSql("   CPU ");
                break;
            case IpkConst.IPK_SORT_MEMORY:
                sql.addSql("   MEMORY ");
                break;
            case IpkConst.IPK_SORT_HD:
                sql.addSql("   HD ");
                break;
            default:
                sql.addSql("   IAD_IPAD ");
            }

            //昇順か降順かを指定
            if (ipkSearchModel.getOrderKey1() == IpkConst.IPK_ORDER_ASC) {
                sql.addSql(" asc ");
            } else if (ipkSearchModel.getOrderKey1() == IpkConst.IPK_ORDER_DESC) {
                sql.addSql(" desc ");
            }

            switch (ipkSearchModel.getSortKey2()) {
            case IpkConst.IPK_SORT_IPAD:
                sql.addSql(" , IAD_IPAD ");
                break;
            case IpkConst.IPK_SORT_NAME:
                sql.addSql(" , IAD_NAME");
                break;
            case IpkConst.IPK_SORT_USEDKBN:
                sql.addSql(" , IAD_USED_KBN ");
                break;
            case IpkConst.IPK_SORT_MSG:
                sql.addSql(" , IAD_MSG ");
                break;
            case IpkConst.IPK_SORT_NETAD:
                sql.addSql(" , INT_NETAD ");
                break;
            case IpkConst.IPK_SORT_SUBNET:
                sql.addSql(" , INT_SABNET ");
                break;
            case IpkConst.IPK_SORT_CPU:
                sql.addSql(" , CPU ");
                break;
            case IpkConst.IPK_SORT_MEMORY:
                sql.addSql(" , MEMORY ");
                break;
            case IpkConst.IPK_SORT_HD:
                sql.addSql(" , HD ");
                break;
            default:
                sql.addSql(" , IAD_IPAD ");
            }
            //昇順か降順かを指定
            if (ipkSearchModel.getOrderKey2() == IpkConst.IPK_ORDER_ASC) {
                sql.addSql(" asc ");
            } else if (ipkSearchModel.getOrderKey2() == IpkConst.IPK_ORDER_DESC) {
                sql.addSql(" desc ");
            }

            pstmt = con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
                    );
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            //結果セットを何行目から取得するのか
            if (ipkSearchModel.getOffset() > 1 && offset) {
                rs.absolute(ipkSearchModel.getOffset() - 1);
            }

            if (offset) {
                for (int i = 0; rs.next() && i < ipkSearchModel.getLimit(); i++) {
                    ret.add(__getIpkAddSearchModelFromRs(rs));
                }
            } else {
                while (rs.next()) {
                    ret.add(__getIpkAddSearchModelFromRs(rs));
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
     * <br>[機  能] 検索結果一覧の件数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ipkSearchModel IP検索モデル
     * @return int count
     * @throws SQLException SQL実行例外
     */
    public int countSearchResult(
            IpkSearchModel ipkSearchModel)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        int count = 0;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select count(*) as CNT");
            sql.addSql(" from");
            sql.addSql("  ( select");
            sql.addSql("     IAD_SID,");
            sql.addSql("     INT_SID,");
            sql.addSql("     IAD_NAME,");
            sql.addSql("     IAD_IPAD,");
            sql.addSql("     IAD_MSG,");
            sql.addSql("     IAD_PRTMNG_NUM,");
            sql.addSql("     ltrim(SUBSTR (CAST(IAD_IPAD as char), 1, 3), '0') || '.' ");
            sql.addSql("     || ltrim(SUBSTR (CAST(IAD_IPAD as char), 4, 3), '0')  || '.' ");
            sql.addSql("     || ltrim(SUBSTR (CAST(IAD_IPAD as char), 7, 3), '0') || '.' ");
            sql.addSql("     || ltrim(SUBSTR (CAST(IAD_IPAD as char), 10, 3), '0') as IPAD ");
            sql.addSql("    from");
            sql.addSql("     IPK_ADD ");
            sql.addSql("    ) as IPKADD");
            sql.addSql("    where ");
            if (ipkSearchModel.getNetSid() == 0) {
                sql.addSql("   IPKADD.INT_SID > 0");
            } else {
                sql.addSql("   IPKADD.INT_SID = ?");
                sql.addIntValue(ipkSearchModel.getNetSid());
            }

            //非公開ネットワーク
            if (ipkSearchModel.getNotDspNetSidList() != null) {
                for (int notDspNetSid : ipkSearchModel.getNotDspNetSidList()) {
                    sql.addSql("   and");
                    sql.addSql("   IPKADD.INT_SID <> " + notDspNetSid);
                }
            }

            //検索キーワード
            __sqlSetUpForKeyword(sql, ipkSearchModel);

            //使用者指定
            if (ipkSearchModel.getAdmGrpSid() != -1) {
                sql.addSql(" and ");
                sql.addSql("   IAD_SID in");
                if (ipkSearchModel.getAdmUsrSid() != -1) {
                    sql.addSql("   (select IAD_SID from IPK_ADD_ADM where USR_SID = ? )");
                    sql.addIntValue(ipkSearchModel.getAdmUsrSid());
                } else {
                    //使用者が指定無しの場合、選択されているグループの全ユーザで検索する。
                    sql.addSql("   (select IAD_SID from IPK_ADD_ADM where USR_SID in ");
                    sql.addSql(" (select USR_SID from CMN_BELONGM where GRP_SID = ?))");
                    sql.addIntValue(ipkSearchModel.getAdmGrpSid());
                }
            }
            pstmt = con.prepareStatement(
                    sql.toSqlString(),
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
                    );
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
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
     * <br>[機  能] キーワード部SQL条件構築
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param model IpkSearchModel
     */
    private void __sqlSetUpForKeyword(SqlBuffer sql, IpkSearchModel model) {
        //キーワード
        String[] keywordList = model.getKeywordList();
//        //キーワード区分
//        String keywordKbn = model.getKeywordKbn();
        //検索対象
        boolean targetIpad = false;
        boolean targetUseKbn = false;
        boolean targetMsg = false;
        String[] targets = model.getSearchTarget();
        if (targets == null || targets.length == 0) {
            return;
        }

        boolean targetFlg = false;
        for (String target : targets) {
            if (String.valueOf(IpkConst.SEARCH_TARGET_IPAD).equals(target)) {
                targetFlg = true;
                targetIpad = true;
            }
            if (String.valueOf(IpkConst.SEARCH_TARGET_USEKBN).equals(target)) {
                targetFlg = true;
                targetUseKbn = true;
            }
            if (String.valueOf(IpkConst.SEARCH_TARGET_MSG).equals(target)) {
                targetFlg = true;
                targetMsg = true;
            }
        }

        if ((keywordList != null || keywordList.length != 0) && targetFlg == true) {

            String keywordJoin = "  and";
            if (model.getKeywordKbn().equals(IpkConst.SEARCH_KEYWORD_OR)) {
                keywordJoin = "   or";
            }
            for (int i = 0; i < keywordList.length; i++) {
                if (i > 0) {
                    sql.addSql(keywordJoin + "(");
                } else {
                    sql.addSql(" and (");
                }

                if (targetIpad) {
                     sql.addSql("    IPAD like '%"
                            + JDBCUtil.escapeForLikeSearch(keywordList[i])
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }

                if (targetUseKbn) {
                    if (targetIpad) {
                        sql.addSql("      or ");
                    }
                    sql.addSql("      upper(IAD_NAME) like '%"
                           + JDBCUtil.escapeForLikeSearch(keywordList[i].toUpperCase())
                           + "%' ESCAPE '"
                           + JDBCUtil.def_esc
                           + "'");
                }

                if (targetMsg) {
                    if (targetIpad || targetUseKbn) {
                        sql.addSql("      or ");
                    }
                    sql.addSql("  upper(IAD_MSG) like '%"
                        + JDBCUtil.escapeForLikeSearch(keywordList[i].toUpperCase())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
                }
                sql.addSql("    )");
            }
        }
    }

    /**
     * <br>[機  能] スペックマスタ情報削除時にIPアドレス情報を変更する。
     * <br>[解  説]
     * <br>[備  考]
     * @param  ismSid スペックSID
     * @param  specKbn スペック区分
     * @param  model IpkAddModel
     * @throws SQLException SQL実行例外
     */
    public void updateSpec(int ismSid, int specKbn, IpkAddModel model) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update ");
            sql.addSql("   IPK_ADD");
            sql.addSql(" set");
            if (specKbn == IpkConst.IPK_SPECKBN_CPU) {
                sql.addSql("   IAD_CPU= 0");
            } else if (specKbn == IpkConst.IPK_SPECKBN_MEMORY) {
                sql.addSql("   IAD_MEMORY = 0");
            } else if (specKbn == IpkConst.IPK_SPECKBN_HD) {
                sql.addSql("   IAD_HD = 0");
            }
            sql.addSql(" ,  IAD_EUID = ?");
            sql.addSql(" ,  IAD_EDATE = ?");
            sql.addSql(" where ");
            if (specKbn == IpkConst.IPK_SPECKBN_CPU) {
                sql.addSql("   IAD_CPU = ?");
            } else if (specKbn == IpkConst.IPK_SPECKBN_MEMORY) {
                sql.addSql("   IAD_MEMORY = ?");
            } else if (specKbn == IpkConst.IPK_SPECKBN_HD) {
                sql.addSql("   IAD_HD = ?");
            }
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(model.getIadEuid());
            sql.addDateValue(model.getIadEdate());
            sql.addIntValue(ismSid);
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
     * <br>[機  能] IPアドレス一覧情報をモデルにセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return bean IpkModel
     * @throws SQLException SQL実行例外
     */
    private IpkAddModel __getIpkAddModelFromRs(ResultSet rs) throws SQLException {
        IpkAddModel model = new IpkAddModel();
        model.setIadSid(rs.getInt("IAD_SID"));
        model.setNetSid(rs.getInt("INT_SID"));
        model.setIadName(rs.getString("IAD_NAME"));
        model.setIadIpad(rs.getLong("IAD_IPAD"));
        model.setIadUseKbn(rs.getString("IAD_USED_KBN"));
        model.setIadMsg(rs.getString("IAD_MSG"));
        model.setIadPrtMngNum(rs.getString("IAD_PRTMNG_NUM"));
        model.setIadCpu(rs.getInt("IAD_CPU"));
        model.setIadMemory(rs.getInt("IAD_MEMORY"));
        model.setIadHd(rs.getInt("IAD_HD"));
        model.setIadAuid(rs.getInt("IAD_AUID"));
        model.setIadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAD_ADATE")));
        model.setIadEuid(rs.getInt("IAD_EUID"));
        model.setIadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAD_EDATE")));

        return model;
    }

    /**
     * <br>[機  能] IPアドレス一覧情報をモデルにセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return bean IpkModel
     * @throws SQLException SQL実行例外
     */
    private IpkAddModel __getIpkAddModelFromRs2(ResultSet rs)
    throws SQLException {

        IpkAddModel model = new IpkAddModel();
        model.setNetSid(rs.getInt("INT_SID"));
        model.setIadName(rs.getString("IAD_NAME"));
        model.setIadIpad(rs.getLong("IAD_IPAD"));
        model.setIadUseKbn(rs.getString("IAD_USED_KBN"));
        model.setIadMsg(rs.getString("IAD_MSG"));
        model.setIadPrtMngNum(rs.getString("IAD_PRTMNG_NUM"));
        model.setIadCpu(rs.getInt("IAD_CPU"));
        model.setIadMemory(rs.getInt("IAD_MEMORY"));
        model.setIadHd(rs.getInt("IAD_HD"));
        model.setIadAuid(rs.getInt("IAD_AUID"));
        model.setIadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAD_ADATE")));
        model.setIadEuid(rs.getInt("IAD_EUID"));
        model.setIadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAD_EDATE")));

        return model;
    }
    /**
     * <br>[機  能] 検索結果一覧情報をモデルにセットする。
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @return bean IpkModel
     * @throws SQLException SQL実行例外
     */
    private IpkAddModel __getIpkAddSearchModelFromRs(ResultSet rs) throws SQLException {
        IpkAddModel model = new IpkAddModel();
        model.setIadSid(rs.getInt("IAD_SID"));
        model.setNetSid(rs.getInt("INT_SID"));
        model.setIadName(rs.getString("IAD_NAME"));
        model.setIadIpad(rs.getLong("IAD_IPAD"));
        model.setIadUseKbn(rs.getString("IAD_USED_KBN"));
        model.setIadMsg(rs.getString("IAD_MSG"));
        model.setIadPrtMngNum(rs.getString("IAD_PRTMNG_NUM"));
        model.setIadCpu(rs.getInt("IAD_CPU"));
        model.setIadMemory(rs.getInt("IAD_MEMORY"));
        model.setIadHd(rs.getInt("IAD_HD"));
        model.setIadAuid(rs.getInt("IAD_AUID"));
        model.setIadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAD_ADATE")));
        model.setIadEuid(rs.getInt("IAD_EUID"));
        model.setIadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAD_EDATE")));
        model.setNetNetad(rs.getString("INT_NETAD"));
        model.setNetSabnet(rs.getString("INT_SABNET"));
        model.setNetDsp(rs.getString("INT_DSP"));
        return model;
    }
    /**
     * <p>Create IPK_ADD Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created IpkAddModel
     * @throws SQLException SQL実行例外
     */
    private IpkAddModel __getIpkAddFromRs(ResultSet rs) throws SQLException {
        IpkAddModel bean = new IpkAddModel();
        bean.setIadSid(rs.getInt("IAD_SID"));
        bean.setNetSid(rs.getInt("INT_SID"));
        bean.setIadName(rs.getString("IAD_NAME"));
        bean.setIadIpad(rs.getLong("IAD_IPAD"));
        bean.setIadUseKbn(rs.getString("IAD_USED_KBN"));
        bean.setIadMsg(rs.getString("IAD_MSG"));
        bean.setIadAuid(rs.getInt("IAD_AUID"));
        bean.setIadAdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAD_ADATE")));
        bean.setIadEuid(rs.getInt("IAD_EUID"));
        bean.setIadEdate(UDate.getInstanceTimestamp(rs.getTimestamp("IAD_EDATE")));
        bean.setIadPrtMngNum(rs.getString("IAD_PRTMNG_NUM"));
        bean.setIadCpu(rs.getInt("IAD_CPU"));
        bean.setIadMemory(rs.getInt("IAD_MEMORY"));
        bean.setIadHd(rs.getInt("IAD_HD"));
        return bean;
    }
}