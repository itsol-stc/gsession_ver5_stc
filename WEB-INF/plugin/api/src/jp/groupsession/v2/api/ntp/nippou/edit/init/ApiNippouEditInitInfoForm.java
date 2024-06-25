package jp.groupsession.v2.api.ntp.nippou.edit.init;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEBAPI 日報編集初期基本情報取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouEditInfo",
plugin = "nippou", name = "日報編集初期基本情報取得",
url = "/api/ntp/nippouEditInfo.do", reqtype = "GET")
public class ApiNippouEditInitInfoForm extends AbstractApiForm {

}
