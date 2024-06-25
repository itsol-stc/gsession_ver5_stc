package jp.groupsession.v2.mem;

import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] メモプラグインで共通的に使用するアクションクラスです(管理者用)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractMemoAction extends AbstractGsAction {

    /** 
     * プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return GSConstMemo.PLUGIN_ID_MEMO;
    }
}
