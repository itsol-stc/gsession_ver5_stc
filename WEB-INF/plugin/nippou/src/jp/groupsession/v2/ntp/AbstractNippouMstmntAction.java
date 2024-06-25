package jp.groupsession.v2.ntp;

import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] 日報プラグインで共通的に使用するアクションクラスです(マスタメンテ用)
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractNippouMstmntAction extends AdminAction {
    /** プラグインID */
    private static final String PLUGIN_ID = GSConstNippou.PLUGIN_ID_NIPPOU;

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }
}