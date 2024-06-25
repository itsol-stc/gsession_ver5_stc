package jp.groupsession.v2.api.bulletin.countm;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] /bulletin/countmのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "bulletin-countm",
plugin = "bulletin", name = "掲示板未確認件数取得",
url = "/api/bulletin/countm.do",
reqtype = "GET")
public class ApiCountMForm extends AbstractApiForm {
}
