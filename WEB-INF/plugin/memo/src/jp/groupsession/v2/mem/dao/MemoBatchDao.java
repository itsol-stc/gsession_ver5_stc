package jp.groupsession.v2.mem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] メモプラグインのバッチ処理を行うDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class MemoBatchDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MemoBatchDao.class);

    /**
     * <p>デフォルトコンストラクタ
     */
    public MemoBatchDao() {
    }

    /**
     * <p>デフォルトコンストラクタ
     * @param con DBコネクション
     */
    public MemoBatchDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定されたメモSidのバイナリーファイル情報を論理削除する
     * <br>[解  説] 状態区分を9:削除に更新する
     * <br>[備  考]
     * @param mems メモSIDリスト
     * @return 削除(更新)件数
     * @throws SQLException SQL実行例外
     */
    public int deleteMemoBin(ArrayList<Long> mems) throws SQLException {

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
            sql.addSql("      select");
            sql.addSql("        BIN_SID");
            sql.addSql("      from");
            sql.addSql("        MEMO_BIN");
            sql.addSql("      where");
            sql.addSql("        MEM_SID in (");

            sql.addIntValue(GSConst.JTKBN_DELETE);

            if (mems.size() > 0) {
                sql.addSql("   ?");
                sql.addLongValue(mems.get(0));
            }
            for (int i = 1; i < mems.size(); i++) {
                sql.addSql("   ,?");
                sql.addLongValue(mems.get(i));
            }
            sql.addSql("        )");
            sql.addSql("    )");

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
}