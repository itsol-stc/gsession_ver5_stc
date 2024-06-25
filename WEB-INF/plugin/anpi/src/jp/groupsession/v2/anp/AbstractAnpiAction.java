package jp.groupsession.v2.anp;

import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] 安否確認プラグインで共通的に使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractAnpiAction extends AbstractGsAction {

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return GSConstAnpi.PLUGIN_ID;
    }
}