package jp.groupsession.v2.zsk;

import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] 在席管理プラグインで共通使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractZaisekiAction extends AbstractGsAction {
    /** プラグインID */
    private static final String PLUGIN_ID = "zaiseki";

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }
}