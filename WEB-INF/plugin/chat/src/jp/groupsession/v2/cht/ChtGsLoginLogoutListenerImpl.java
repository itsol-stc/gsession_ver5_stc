package jp.groupsession.v2.cht;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.ILoginLogoutListener;

/**
 * <br>[機  能] ログイン、ログアウト時に実行されるリスナーの実装
 * <br>[解  説] チャットについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChtGsLoginLogoutListenerImpl implements ILoginLogoutListener {

    /** Logging インスタンス */
    static Log log__ = LogFactory.getLog(ChtGsLoginLogoutListenerImpl.class);

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

    }
}
