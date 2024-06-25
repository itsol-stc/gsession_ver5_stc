package jp.groupsession.v2.api.user.belongname;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;

/**
 *
 * <br>[機  能] グループ所属ユーザ名一覧取得WEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "user-belongnamelist",
plugin = "user", name = "グループに所属するユーザ名一覧取得",
url = "/api/user/belongnamelist.do", reqtype = "GET")
public class ApiBelongUserNameForm extends AbstractApiForm {
    /** グループSID*/
    @ApiParam(name = "grpSid", viewName = "グループSID")
    private String grpSid__;

    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public String getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(String grpSid) {
        grpSid__ = grpSid;
    }
}
