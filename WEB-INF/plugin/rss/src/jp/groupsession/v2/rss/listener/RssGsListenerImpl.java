package jp.groupsession.v2.rss.listener;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.IGsListener;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.TempFileUtilFactory;
import jp.groupsession.v2.rss.RssBiz;

/**
 * <br>[機  能] Servlet init() 又はdestroy()実行時に実行されるリスナーを実装
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssGsListenerImpl implements IGsListener {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(RssGsListenerImpl.class);
    /** リクエスト */
    public HttpServletRequest req__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RssGsListenerImpl() {

    }
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    public RssGsListenerImpl(HttpServletRequest req) {
        req__ = req;
    }
    /** Servlet destroy()時に実行される
     * @param gscontext 基本情報
     * @param con コネクション
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @see jp.groupsession.v2.cmn.IGsListener
     * #gsDestroy(jp.groupsession.v2.cmn.GSContext, java.sql.Connection)
     */
    public void gsDestroy(GSContext gscontext, Connection con, String domain) throws SQLException {
        RssBiz.setStopRssUpdateFlg(domain, true);

        try {
            int waitCount = 0;
            while (RssBiz.getRssUpdateCount(domain) > 0) {
                Thread.sleep(500);

                waitCount++;
                if (waitCount > 10) {
                    break;
                }
            }
        } catch (Exception e) {
            log__.error("RSSリーダー停止処理に失敗");
        }
    }

    /**
     * <p>ログイン実行時に実行される
     * @param gscontext GS共通情報
     * @param con DBコネクション
     * @param session セッション
     * @throws Exception 実行例外
     */
    public void gsLogin(GSContext gscontext, Connection con, HttpSession session)
    throws Exception {
    }

    /**
     * <p>ログアウト実行時に実行される
     * @param gscontext GS共通情報
     * @param con DBコネクション
     * @param session セッション
     * @throws Exception 実行例外
     */
    public void gsLogout(GSContext gscontext, Connection con, HttpSession session)
    throws Exception {
    }

    /** Servlet init()時に実行される
     * @param gscontext 基本情報
     * @param con コネクション
     * @param domain ドメイン
     * @throws Exception 実行時例外
     * @see jp.groupsession.v2.cmn.IGsListener
     * #gsInit(jp.groupsession.v2.cmn.GSContext, java.sql.Connection)
     */
    public void gsInit(GSContext gscontext, Connection con, String domain) throws Exception {

        String batchId = "gsinit";
        RssBiz rssBiz = new RssBiz();
        String tempPath = rssBiz.getBatchTempDir(domain, batchId);

        boolean commit = false;
        try {
            con.setAutoCommit(false);

            //RSSフィード情報をクリアする
            //(バージョンアップ時、フィード情報格納Modelが更新される場合があるため)
            ITempFileUtil tempfileUtil = TempFileUtilFactory.getInstance(tempPath);
            tempfileUtil.clearFeedData(con, rssBiz.getUpdateTime(con), 0, new UDate());

            con.commit();
            commit = true;

        } catch (SQLException e) {
            log__.error("RSSフィード情報の更新に失敗", e);
            throw e;
        } catch (Exception e) {
            log__.error("RSSフィード情報の更新に失敗", e);
            throw new SQLException(e.getMessage());
        } finally {
            if (!commit) {
                JDBCUtil.rollback(con);
            }

            //テンポラリディレクトリが存在する場合、削除する
            if (IOTools.isDirCheck(tempPath, false)) {
                rssBiz.deleteBatchTempDir(domain, batchId);
            }
        }
    }
}