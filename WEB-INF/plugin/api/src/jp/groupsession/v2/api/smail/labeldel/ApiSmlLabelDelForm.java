package jp.groupsession.v2.api.smail.labeldel;

import jp.groupsession.v2.api.smail.labellist.ApiSmlLabelListForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] ショートメールラベルを削除するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "smail-labeldel",
plugin = "smail", name = "ラベル削除",
url = "/api/smail/labeldel.do", reqtype = "DELETE")
public class ApiSmlLabelDelForm extends ApiSmlLabelListForm {
    /** ラベルSID */
    @ApiParam(name = "slbSid", viewName = "ラベルSID")
    private int slbSid__ = -1;

    /**
     * <p>slbSid を取得します。
     * @return slbSid
     */
    public int getSlbSid() {
        return slbSid__;
    }
    /**
     * <p>slbSid をセットします。
     * @param slbSid 削除するラベルSID
     */
    public void setSlbSid(int slbSid) {
        slbSid__ = slbSid;
    }
}
