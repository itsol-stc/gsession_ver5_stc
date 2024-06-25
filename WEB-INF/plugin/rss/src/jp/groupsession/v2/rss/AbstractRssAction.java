package jp.groupsession.v2.rss;

import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] RSSリーダープラグインで共通的に使用するアクションクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractRssAction extends AbstractGsAction {

    /** プラグインID */
    private static final String PLUGIN_ID = GSConstRss.PLUGIN_ID_RSS;

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }
}