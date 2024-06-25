package jp.groupsession.v2.api.man.plugin.useable;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 *
 * <br>[機  能] 複数指定したプラグインIDの中から利用可能なプラグイン情報を返すWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "main-plugin/useable",
plugin = "main", name = "利用可能プラグイン確認",
url = "/api/main/plugin/useable.do", reqtype = "GET")
public class ApiManPluginUseableForm extends AbstractApiForm {
    /** プラグインID*/
    @ApiParam(name = "pluginId", viewName = "pluginId")
    private String[] pluginId__;

    /**
     * <p>pluginId を取得します。
     * @return pluginId
     */
    public String[] getPluginId() {
        return pluginId__;
    }

    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     */
    public void setPluginId(String[] pluginId) {
        pluginId__ = pluginId;
    }
}
