package jp.groupsession.v2.api.cmn.logintype;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
/**
 *
 * <br>[機  能] ログイン方法取得API フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "common-logintype",
plugin = "common", name = "ログイン方法取得",
url = "/api/cmn/logintype.do",
reqtype = "GET")
public class ApiCmnLogintypeForm extends AbstractApiForm {
    /**ログインID*/
    @ApiParam(name = "userid", viewName = "ユーザID")
    private String userid__;

    /**
     * <p>userid を取得します。
     * @return userid
     * @see jp.groupsession.v2.api.cmn.otplogin.ApiCmnOtploginForm#Userid__
     */
    public String getUserid() {
        return userid__;
    }


    /**
     * <p>userid をセットします。
     * @param userid userid
     * @see jp.groupsession.v2.api.cmn.otplogin.ApiCmnOtploginForm#Userid__
     */
    public void setUserid(String userid) {
        userid__ = userid;
    }


}
