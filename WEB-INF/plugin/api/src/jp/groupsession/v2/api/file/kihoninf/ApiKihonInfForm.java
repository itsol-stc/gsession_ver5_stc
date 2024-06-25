package jp.groupsession.v2.api.file.kihoninf;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] /file/kihoninfのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-kihoninf",
plugin = "file", name = "ファイル管理基本設定取得",
url = "/api/file/kihoninf.do",
reqtype = "GET")
public class ApiKihonInfForm extends AbstractApiForm {

}
