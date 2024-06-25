package jp.groupsession.v2.enq;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.enq.biz.EnqCommonBiz;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] アンケートプラグインで共通的に使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractEnqueteAction extends AbstractGsAction {

    /**
     * プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return GSConstEnquete.PLUGIN_ID_ENQUETE;
    }

    /**
     * <br>[機  能] アンケート作成可能者かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return true: アンケート作成可能 false:アンケート作成不可
     * @throws SQLException SQL実行時例外
     */
    protected boolean _checkEnqCrtUser(Connection con, HttpServletRequest req) throws SQLException {
        EnqCommonBiz enqBiz = new EnqCommonBiz();
        return enqBiz.isEnqCrtUser(con, getRequestModel(req));
    }


    /**
     * <br>[機  能] ショートメールプラグインの使用可否を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return ショートメールプラグインの使用可否
     * @throws SQLException SQL実行時例外
     */
    protected int _getUseSmailPluginKbn(HttpServletRequest req, Connection con)
    throws SQLException {
    PluginConfig pconfig
        = getPluginConfigForMain(getPluginConfig(req), con, getSessionUserSid(req));
        int pluginUse = GSConst.PLUGIN_NOT_USE;
        if (pconfig.getPlugin(GSConst.PLUGINID_SML) != null) {
            pluginUse = GSConst.PLUGIN_USE;
        }
        return pluginUse;
    }
}
