package jp.groupsession.v2.api.smail.labeladd;

import jp.groupsession.v2.api.smail.labellist.ApiSmlLabelListForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] ショートメールラベルを追加するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "smail-labeladd",
plugin = "smail", name = "ラベル追加",
url = "/api/smail/labeladd.do", reqtype = "POST")
public class ApiSmlLabelAddForm extends ApiSmlLabelListForm {
    /** ラベル名*/
    @ApiParam(name = "slbName", viewName = "ラベル名")
    private String slbName__ = null;

    /**
     * <p>slbName を取得します。
     * @return labelName
     */
    public String getSlbName() {
        return slbName__;
    }
    /**
     * <p>slbName をセットします。
     * @param slbName 追加するラベル名
     */
    public void setSlbName(String slbName) {
        slbName__ = slbName;
    }
}
