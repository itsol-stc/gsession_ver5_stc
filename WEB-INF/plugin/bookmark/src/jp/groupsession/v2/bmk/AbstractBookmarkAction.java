package jp.groupsession.v2.bmk;

import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] ブックマークプラグインで
 * <br>         共通使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractBookmarkAction extends AbstractGsAction {

    /** プラグインID */
    private static final String PLUGIN_ID = GSConstBookmark.PLUGIN_ID_BOOKMARK;

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

}