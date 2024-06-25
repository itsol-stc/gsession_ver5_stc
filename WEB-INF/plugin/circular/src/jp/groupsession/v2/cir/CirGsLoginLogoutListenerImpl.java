package jp.groupsession.v2.cir;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.ILoginLogoutListener;

/**
 * <br>[機  能] ログイン、ログアウト時に実行されるリスナーの実装
 * <br>[解  説] 回覧板についての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirGsLoginLogoutListenerImpl implements ILoginLogoutListener {

    /** Logging インスタンス */
    static Log log__ = LogFactory.getLog(CirGsLoginLogoutListenerImpl.class);

    /**
     * <br>[機  能] ログイン時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param usid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doLogin(Connection con, int usid) throws SQLException {
    }

    /**
     * <br>[機  能] ログアウト時に実行される
     * <br>[解  説]
     * <br>[備  考]
     * @param session セッション
     * @param con DBコネクション
     * @param usid ユーザSID
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     */
    public void doLogout(HttpSession session,
                 Connection con, int usid, String domain) throws SQLException {

        //一時保管ファイルを削除
        /*String tempDir = GroupSession.getResourceManager().getTempPath(domain)
                       + File.separator + GSConstCircular.PLUGIN_ID_CIRCULAR
                       + File.separator + session.getId();
        IOTools.deleteDir(tempDir);*/
    }
}