package jp.groupsession.v2.api.schedule.user.list;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEBAPI スケジュール用ユーザ一覧取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "schedule-userlist",
plugin = "schedule", name = "グループ所属ユーザ一覧取得",
url = "/api/schedule/userlist.do", reqtype = "GET")
public class ApiSchSharedUserForm extends AbstractApiForm {
    /** グループSID */
    private String grpSid__ = null;

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
