package jp.groupsession.v2.api.cmn.otplogin;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] ワンタイムパスワード認証API フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "common-otplogin",
plugin = "common", name = "ワンタイムパスワード認証",
url = "/api/cmn/otplogin.do",
reqtype = "POST")
public class ApiCmnOtploginForm extends AbstractApiForm {
    /**ログインID*/
    @ApiParam(name = "userid", viewName = "ユーザID")
    private String userid__;
    /**ワンタイムパスワード*/
    @ApiParam(name = "otPassword", viewName = "ワンタイムパスワード")
    private String otPassword__;

    /**
     *
     * <br>[機  能] 入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマップ
     * @param reqMdl リクエストモデル
     * @return アクションエラー
     */
    public ActionErrors validateLogin(ActionMapping map,
            RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // パスワード
        GSValidateCommon.validateNumberInt(errors,
                otPassword__,
                gsMsg.getMessage("cmn.onetimepassword"),
                GSConstCommon.OTP_LENGTH);
        return errors;
    }


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


    /**
     * <p>otPassword を取得します。
     * @return otPassword
     * @see jp.groupsession.v2.api.cmn.otplogin.ApiCmnOtploginForm#otPassword__
     */
    public String getOtPassword() {
        return otPassword__;
    }


    /**
     * <p>otPassword をセットします。
     * @param otPassword otPassword
     * @see jp.groupsession.v2.api.cmn.otplogin.ApiCmnOtploginForm#otPassword__
     */
    public void setOtPassword(String otPassword) {
        otPassword__ = otPassword;
    }

}
