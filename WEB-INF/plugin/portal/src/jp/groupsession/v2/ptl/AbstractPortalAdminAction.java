package jp.groupsession.v2.ptl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

/**
 * <br>[機  能] ポータルの管理者設定に使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractPortalAdminAction extends AbstractPortalAction {

    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    @Override
    protected boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {
        if (_isSystemAdmin(req, con)) {
            return true; //システム管理者の場合はプラグインチェックをしない
        }
        return super._isAccessPlugin(req, form, con);
    }
}
