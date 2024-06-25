package jp.groupsession.v2.ntp.ntp270;

import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 日報分析画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp270Biz {
    /** DBコネクション */
    public Connection con__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     */
    public Ntp270Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param res レスポンス
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            ActionMapping map,
            HttpServletResponse res,
            int sessionUserSid,
            Connection con) throws SQLException {

    }
}
