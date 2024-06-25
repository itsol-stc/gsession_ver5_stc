package jp.groupsession.v2.cht;

import jp.groupsession.v2.struts.AbstractGsAction;

public abstract class AbstractChatAction extends AbstractGsAction {

    /** プラグインID */
    private static final String PLUGIN_ID = GSConstChat.PLUGIN_ID_CHAT;

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }


}
