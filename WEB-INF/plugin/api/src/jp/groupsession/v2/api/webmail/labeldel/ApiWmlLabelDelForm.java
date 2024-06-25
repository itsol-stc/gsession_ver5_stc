package jp.groupsession.v2.api.webmail.labeldel;

import jp.groupsession.v2.api.webmail.labellist.ApiWmlLabelListForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 * <br>[機  能] WEBメールラベルを追加するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-labeldel",
plugin = "webmail", name = "ラベル削除",
url = "/api/webmail/labeldel.do", reqtype = "DELETE")
public class ApiWmlLabelDelForm extends ApiWmlLabelListForm {
    /** ラベルSID*/
    @ApiParam(name = "wlbSid", viewName = "ラベルSID")
    private int wlbSid__ = -1;

    /**
     * <p>wlbSid を取得します。
     * @return wlbSid
     */
    public int getWlbSid() {
        return wlbSid__;
    }
    /**
     * <p>wlbSid をセットします。
     * @param wlbSid ラベルSID
     */
    public void setWlbSid(int wlbSid) {
        wlbSid__ = wlbSid;
    }
}
