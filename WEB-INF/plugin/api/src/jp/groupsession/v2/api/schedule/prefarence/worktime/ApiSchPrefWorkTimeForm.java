package jp.groupsession.v2.api.schedule.prefarence.worktime;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] スケジュール日間表示時間帯設定取得WEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "schedule-prefarence/worktime",
plugin = "schedule", name = "スケジュール日間表示時間帯設定取得",
url = "/api/schedule/prefarence/worktime.do", reqtype = "GET")
public class ApiSchPrefWorkTimeForm extends AbstractApiForm {
    /** ユーザSID*/
    private String usrSid__;

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
    }
    /**
    *
    * <br>[機  能] 入力チェック
    * <br>[解  説]
    * <br>[備  考]
    * @param gsMsg GsMessage
    * @return errors
    */
   public ActionErrors validateCheck(
           GsMessage gsMsg) {
       ActionErrors errors = new ActionErrors();

       GSValidateApi.validateSid(errors, usrSid__, "usrSid",
               "usrSid", false);

       return errors;
   }
}
