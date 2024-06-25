package jp.groupsession.v2.ptl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.struts.AdminAction;

/**
 * <br>[機  能] ポータルで共通的に使用するアクションクラスです
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractPortalAction extends AdminAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(AbstractPortalAction.class);

    /** プラグインID */
    private static final String PLUGIN_ID = GSConstPortal.PLUGIN_ID;

    /**プラグインIDを取得します
     * @return String プラグインID
     * @see jp.groupsession.v2.struts.AbstractGsAction#getPluginId()
     */
    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

}
