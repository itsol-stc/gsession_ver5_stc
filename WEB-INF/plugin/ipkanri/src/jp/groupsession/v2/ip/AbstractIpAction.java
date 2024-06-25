package jp.groupsession.v2.ip;

import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] IP管理プラグインで共通的に使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractIpAction extends AbstractGsAction {

    /** プラグインID */
    private static final String PLUGIN_ID = IpkConst.PLUGIN_ID_IPKANRI;

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }
}