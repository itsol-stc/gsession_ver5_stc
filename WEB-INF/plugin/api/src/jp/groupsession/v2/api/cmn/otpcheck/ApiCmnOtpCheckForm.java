package jp.groupsession.v2.api.cmn.otpcheck;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
/**
 *
 * <br>[機  能] ワンタイムパスワード使用確認API フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "common-otpcheck",
plugin = "common", name = "ワンタイムパスワード使用確認",
url = "/api/cmn/otpcheck.do",
reqtype = "GET")
public class ApiCmnOtpCheckForm extends AbstractApiForm {
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
