package jp.groupsession.v2.api.man.plugin;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] /main/pluginのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "main-plugin",
plugin = "main", name = "プラグイン情報取得",
url = "/api/main/plugin.do", reqtype = "GET")
public class ApiManPluginForm extends AbstractApiForm {
}
