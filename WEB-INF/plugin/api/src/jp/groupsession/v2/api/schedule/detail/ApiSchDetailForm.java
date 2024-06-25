package jp.groupsession.v2.api.schedule.detail;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] スケジュール詳細情報取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "schedule-detail",
plugin = "schedule", name = "スケジュール詳細取得",
url = "/api/schedule/detail.do", reqtype = "GET")
public class ApiSchDetailForm extends AbstractApiForm {
    /** スケジュールSID */
    @ApiParam(name = "schSid", viewName = "スケジュールSID")
    private String schSid__;

    /**
     * <p>schSid を取得します。
     * @return schSid
     */
    public String getSchSid() {
        return schSid__;
    }

    /**
     * <p>schSid をセットします。
     * @param schSid schSid
     */
    public void setSchSid(String schSid) {
        schSid__ = schSid;
    }

    /**
    *
    * <br>[機  能]入力チェックを行う
    * <br>[解  説]
    * <br>[備  考]
    * @param gsMsg GsMessage
    * @return エラー
    */
   public ActionErrors validateCheck(
           GsMessage gsMsg) {
       ActionErrors errors = new ActionErrors();
       GSValidateApi.validateSid(errors, schSid__, "schSid", gsMsg.getMessage("api.sch.sid"), true);
       return errors;
   }
}
