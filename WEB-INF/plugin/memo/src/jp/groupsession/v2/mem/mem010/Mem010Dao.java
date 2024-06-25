package jp.groupsession.v2.mem.mem010;

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
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.model.MemoDataModel;
import jp.groupsession.v2.mem.model.MemoLabelModel;

/**
 * <br>[機  能] メモ メモ画面に関するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem010Dao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Mem010Dao.class);
    
    /**
     * コンストラクタ
     * @param con コネクション
     */
    public Mem010Dao (Connection con) {
        super(con);
    }
    
    /**
     * <br>[機 能]指定された条件に従ったメモを最大30件取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param searchMdl Mem010SearchModel
     * @param list メモSIDを格納する配列
     * @return Mem010DisplayModelリスト
     * @throws SQLException SQL実行例外
     */
    public List<Mem010DisplayModel> select(
            Mem010SearchModel searchMdl, List<Long> list) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        List<Mem010DisplayModel> ret = new ArrayList<Mem010DisplayModel>();
        con = getCon();
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MEM_SID,");
            sql.addSql("   MMD_CONTENT,");
            sql.addSql("   MMD_EDATE");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA");
            
            int sortOrder = searchMdl.getSortOrder();
            __setSearchSql(searchMdl, sql);
            
            sql.addSql(" order by");
            sql.addSql("   MMD_EDATE");
            if (sortOrder == GSConst.ORDER_KEY_ASC) {
                sql.addSql("    asc");
            } else {
                sql.addSql("    desc");
            }
            sql.addSql(" limit " + GSConstMemo.MAX_MEMO_SIZE);
            sql.addSql(" offset ?");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(searchMdl.getHyouziKensu());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ret.add(__getMem010DisplayDataFromRs(rs));
                list.add(rs.getLong("MEM_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        
        return ret;
    }
    
    /**
     * <br>[機 能]存在するメモのSIDリストを取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param targetSidList 対象メモのSIDのリスト
     * @return 存在するメモのSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<Long> getExistMemoSid(
            int usrSid, List<Long> targetSidList) throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        List<Long> ret = new ArrayList<Long>();
        int size = targetSidList.size();
        con = getCon();
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MEM_SID");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addIntValue(usrSid);
            if (size > 0) {
                sql.addSql(" and");
                sql.addSql("   MEM_SID in (");
                for (int i = 0; i < targetSidList.size(); i++) {
                    sql.addSql(" ?");
                    sql.addLongValue(targetSidList.get(i));
                    if (i != targetSidList.size() -1) {
                        sql.addSql(",");
                    }
                }
                sql.addSql("   )");    
            }
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                ret.add(rs.getLong("MEM_SID"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        
        return ret;
    }
    
    /**
     * <br>[機  能] 登録，変更されたメモが検索条件に当てはまっているかを確認する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl Mem010SearchModel
     * @param memSid メモSID
     * @return true:検索条件に当てはまっている false:検索条件に当てはまっていない
     * @throws SQLException SQL実行例外
     */
    public boolean isMatchRequirement(
            Mem010SearchModel searchMdl, long memSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        boolean ret = false;
        con = getCon();
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(MEM_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA");
            
            __setSearchSql(searchMdl, sql);
            
            sql.addSql(" and");
            sql.addSql("   MEM_SID = ?");
            sql.addLongValue(memSid);
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                if (rs.getInt("CNT") > 0) {
                    ret = true;
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        
        return ret;
    }
    
    /**
     * <br>[機 能]指定したメモの各項目を取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param memSid 対象メモのSID
     * @return Mem010DisplayModel
     * @throws SQLException SQL実行例外
     */
    public Mem010DisplayModel getMemoMeisai(int usrSid, long memSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        Mem010DisplayModel ret = new Mem010DisplayModel();
        con = getCon();
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MEMO_DATA.MEM_SID as MEM_SID,");
            sql.addSql("   MEMO_DATA.MMD_CONTENT as MMD_CONTENT,");
            sql.addSql("   MEMO_DATA_PERIOD.MDP_DEL_DATE as MDP_DEL_DATE");
            sql.addSql(" from");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" left join");
            sql.addSql("   MEMO_DATA_PERIOD");
            sql.addSql(" on");
            sql.addSql("   MEMO_DATA.MEM_SID = MEMO_DATA_PERIOD.MEM_SID");
            sql.addSql(" where");
            sql.addSql("   MEMO_DATA.USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   MEMO_DATA.MEM_SID = ?");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addLongValue(memSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret.setMemSid(rs.getLong("MEM_SID"));
                ret.setMmdContent(rs.getString("MMD_CONTENT"));
                if (rs.getTimestamp("MDP_DEL_DATE") != null) {
                    UDate udate = UDate.getInstanceDate(
                            rs.getTimestamp("MDP_DEL_DATE"));
                    String atdelDate = UDateUtil.getSlashYYMD(udate); 
                    ret.setAdtelDate(atdelDate);
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        return ret;
    }
    
    /**
     * <br>[機 能]指定したメモに添付されているファイル情報のリストを取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param memSid 対象メモのSID
     * @return CmnBinfModelリスト
     * @throws SQLException SQL実行例外
     */
    public List<CmnBinfModel> getMemoMeisaiTenpu(int usrSid, long memSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        List<CmnBinfModel> ret = new ArrayList<CmnBinfModel>();
        con = getCon();
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME");
            sql.addSql(" from");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       BIN_SID");
            sql.addSql("     from");
            sql.addSql("       MEMO_BIN");
            sql.addSql("     where");
            sql.addSql("       MEM_SID = ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   BIN_ADUSER = ?");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                CmnBinfModel model = new CmnBinfModel();
                model.setBinFileName(rs.getString("BIN_FILE_NAME"));
                model.setBinSid(rs.getLong("BIN_SID"));
                ret.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        return ret;
    }
    
    /**
     * <br>[機 能]指定したメモに添付されているラベル情報を取得する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param memSid 対象メモのSID
     * @return MemoLabelModelリスト
     * @throws SQLException SQL実行例外
     */
    public List<MemoLabelModel> getMemoMeisaiLabel(int usrSid, long memSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        List<MemoLabelModel> ret = new ArrayList<MemoLabelModel>();
        con = getCon();
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   MEMO_LABEL.MML_NAME as MML_NAME,");
            sql.addSql("   MEMO_BELONG_LABEL.MML_SID as MML_SID");
            sql.addSql(" from");
            sql.addSql("   MEMO_LABEL,");
            sql.addSql("   MEMO_BELONG_LABEL");
            sql.addSql(" where");
            sql.addSql("   MEMO_BELONG_LABEL.MEM_SID = ?");
            sql.addSql(" and");
            sql.addSql("   MEMO_LABEL.MML_SID = MEMO_BELONG_LABEL.MML_SID");
            sql.addSql(" and");
            sql.addSql("   MEMO_LABEL.USR_SID = ?");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(memSid);
            sql.addIntValue(usrSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                MemoLabelModel model = new MemoLabelModel();
                model.setMmlName(rs.getString("MML_NAME"));
                model.setMmlSid(rs.getInt("MML_SID"));
                ret.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        
        return ret;
    }
    
    /**
     * <br>[機 能]メモ情報を更新する
     * <br>[解 説]
     * <br>[備 考]
     * @param mmdMdl MemoDataModelオブジェクト
     * @return 更新されたメモ件数
     * @throws SQLException SQL実行例外
     */
    public int updateMemoData(MemoDataModel mmdMdl) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int ret= 0;
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" set");
            sql.addSql("   MMD_CONTENT = ?,");
            sql.addSql("   MMD_DEL_CONF = ?,");
            sql.addSql("   MMD_EUID = ?,");
            sql.addSql("   MMD_EDATE = ?");
            sql.addSql(" where");
            sql.addSql("   MEM_SID = ?");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addStrValue(mmdMdl.getMmdContent());
            sql.addIntValue(mmdMdl.getMmdDelConf());
            sql.addIntValue(mmdMdl.getUsrSid());
            sql.addDateValue(mmdMdl.getMmdEdate());
            sql.addLongValue(mmdMdl.getMemSid());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            ret = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        return ret;
    }
    
    /**
     * <br>[機 能]メモ情報を削除する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param targetMemoSid 削除対象のメモSID
     * @return 削除されたメモ件数
     * @throws SQLException SQL実行例外
     */
    public int deleteMemoData(int usrSid, long targetMemoSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int ret= 0;
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   MEMO_DATA");
            sql.addSql(" where");
            sql.addSql("   USR_SID = ?");
            sql.addSql(" and");
            sql.addSql("   MEM_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(usrSid);
            sql.addLongValue(targetMemoSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            ret = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        
        return ret;
    }
    
    /**
     * <br>[機 能]メモに添付されているファイルを論理削除する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param targetMemoSid 削除対象のメモSID
     * @return 論理削除したファイル件数
     * @throws SQLException SQL実行例外
     */
    public int deleteCmnBinData(int usrSid, long targetMemoSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int ret= 0;
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   CMN_BINF");
            sql.addSql(" set");
            sql.addSql("   BIN_JKBN = 9");
            sql.addSql(" where");
            sql.addSql("   BIN_SID in (");
            sql.addSql("     select");
            sql.addSql("       BIN_SID");
            sql.addSql("     from");
            sql.addSql("       MEMO_BIN");
            sql.addSql("     where");
            sql.addSql("       MEM_SID = ?");
            sql.addSql("   )");
            sql.addSql(" and");
            sql.addSql("   BIN_ADUSER = ?");
            
            sql.addLongValue(targetMemoSid);
            sql.addIntValue(usrSid);
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            ret = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }

        return ret;
    }
    
    /**
     * <br>[機 能]対象のメモに添付されているファイルを情報を削除する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param targetMemoSid 削除対象のメモSID
     * @return 削除したファイル情報件数
     * @throws SQLException SQL実行例外
     */
    public int deleteMemoBinData(int usrSid, long targetMemoSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int ret= 0;
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   MEMO_BIN");
            sql.addSql(" where");
            sql.addSql("   MEM_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(targetMemoSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            ret = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        return ret;
    }
    
    /**
     * <br>[機 能]対象のメモの自動削除日付を削除する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param targetMemoSid 削除対象のメモSID
     * @return 削除したメモの自動削除日付件数
     * @throws SQLException SQL実行例外
     */
    public int deleteMemoPeriodData(int usrSid, long targetMemoSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int ret= 0;
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   MEMO_DATA_PERIOD");
            sql.addSql(" where");
            sql.addSql("   MEM_SID = ?");
            
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(targetMemoSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            ret = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }

        return ret;
    }
        
    /**
     * <br>[機 能]対象のメモに設定されているラベル情報を削除する
     * <br>[解 説]
     * <br>[備 考]
     * @param usrSid セッションユーザSID
     * @param targetMemoSid 削除対象のメモSID
     * @return 削除したラベル情報件数
     * @throws SQLException SQL実行例外
     */
    public int deleteMemoLabelData(int usrSid, long targetMemoSid) throws SQLException {
        
        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;
        con = getCon();
        int ret= 0;
        
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete from");
            sql.addSql("   MEMO_BELONG_LABEL");
            sql.addSql(" where");
            sql.addSql("   MEM_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(targetMemoSid);
            sql.setParameter(pstmt);
            log__.info(sql.toLogString());
            ret = pstmt.executeUpdate();
            
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closePreparedStatement(pstmt);
            JDBCUtil.closeResultSet(rs);
        }
        
        return ret;
    }
    
    /**
     * <br>[機  能] メモ検索条件を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl Mem010SearchModel
     * @param sql SqlBuffer
     */
    private void __setSearchSql(Mem010SearchModel searchMdl, SqlBuffer sql) {
        
        String mmdContent = searchMdl.getMmdContent();
        UDate dateFr = searchMdl.getDateFr();
        UDate dateTo = searchMdl.getDateTo();
        int mmlSid = searchMdl.getMmlSid();
        int tenpuFlg = searchMdl.getTenpu();
        
        sql.addSql(" where");
        sql.addSql("   USR_SID = ?");
        sql.addIntValue(searchMdl.getUsrSid());
        
        if (mmdContent != null) {
            sql.addSql(" and");
            sql.addSql("   MEMO_DATA.MMD_CONTENT like '%" 
                + JDBCUtil.escapeForLikeSearch(mmdContent)
                + "%' ESCAPE '" + JDBCUtil.def_esc + "'");
        }
        if (dateFr != null) {
            sql.addSql(" and");
            sql.addSql("   MEMO_DATA.MMD_EDATE >= ?");
            sql.addDateValue(dateFr);
        }
        if (dateTo != null) {
            sql.addSql(" and");
            sql.addSql("   MEMO_DATA.MMD_EDATE <= ?");
            sql.addDateValue(dateTo);
        }
        if (mmlSid != -1) {
            sql.addSql(" and");
            sql.addSql("   MEMO_DATA.MEM_SID in (");
            sql.addSql("     select MEMO_BELONG_LABEL.MEM_SID from MEMO_BELONG_LABEL");
            sql.addSql("     where MEMO_BELONG_LABEL.MML_SID = ?");
            sql.addSql("   )");
            sql.addIntValue(mmlSid);
        }
        if (tenpuFlg == GSConstMemo.TENPU_KBN_YES) {
            sql.addSql(" and");
            sql.addSql("   MEMO_DATA.MEM_SID in (");
            sql.addSql("     select MEMO_BIN.MEM_SID from MEMO_BIN");
            sql.addSql("   )");
        } else if (tenpuFlg == GSConstMemo.TENPU_KBN_NO) {
            sql.addSql(" and");
            sql.addSql("   MEMO_DATA.MEM_SID not in (");
            sql.addSql("     select MEMO_BIN.MEM_SID from MEMO_BIN");
            sql.addSql("   )");
        }
    }
    
    /**
     * <br>[機 能]リザルトセットからMem010DisplayModelオブジェクトを生成し、返す
     * <br>[解 説]
     * <br>[備 考]
     * @param rs ResultSet
     * @return Mem010SearchModel
     * @throws SQLException SQL実行例外
     */
    private Mem010DisplayModel __getMem010DisplayDataFromRs(ResultSet rs) throws SQLException {
        Mem010DisplayModel bean = new Mem010DisplayModel();
        bean.setMemSid(rs.getLong("MEM_SID"));
        String mmdContent = rs.getString("MMD_CONTENT");
        String[] splitStr = mmdContent.split("\n");
        mmdContent = splitStr[0];
        mmdContent = mmdContent.replaceAll("[\n]+", "");
        bean.setMmdContent(mmdContent);
        UDate udate = UDate.getInstanceDate(rs.getTimestamp("MMD_EDATE"));
        UDate now = new UDate();
        String mmdEdate = udate.getStrMonth() + "/" + udate.getStrDay();
        if (!now.getStrYear().equals(udate.getStrYear())) {
            mmdEdate = udate.getStrYear() + "/" + mmdEdate;    
        }
        bean.setMmdEdate(mmdEdate);
        
        return bean;
    }
}
