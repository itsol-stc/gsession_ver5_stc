package jp.groupsession.v2.api.schedule.otherplugin.schlist;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] 他のプラグインのスケジュールを取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "schedule-otherplgin/list",
plugin = "schedule", name = "他プラグイン上のスケジュール取得",
url = "/api/schedule/otherplgin/list.do", reqtype = "GET")
public class ApiSchOtherPlginSchListForm extends AbstractApiForm {
    /** グループ */
    private String gsid__ = null;
    /** ユーザ */
    private String usid__ = null;
    /** from */
    private String from__ = null;
    /** to */
    private String to__ = null;
    /**
     * <p>gsid を取得します。
     * @return gsid
     */
    public String getGsid() {
        return gsid__;
    }
    /**
     * <p>gsid をセットします。
     * @param gsid gsid
     */
    public void setGsid(String gsid) {
        gsid__ = gsid;
    }
    /**
     * <p>usid を取得します。
     * @return usid
     */
    public String getUsid() {
        return usid__;
    }
    /**
     * <p>usid をセットします。
     * @param usid usid
     */
    public void setUsid(String usid) {
        usid__ = usid;
    }
    /**
     * <p>from を取得します。
     * @return from
     */
    public String getFrom() {
        return from__;
    }
    /**
     * <p>from をセットします。
     * @param from from
     */
    public void setFrom(String from) {
        from__ = from;
    }
    /**
     * <p>to を取得します。
     * @return to
     */
    public String getTo() {
        return to__;
    }
    /**
     * <p>to をセットします。
     * @param to to
     */
    public void setTo(String to) {
        to__ = to;
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

        GSValidateApi.validateSid(errors, usid__, "usid",
                "usid", false);
        GSValidateCommon.validateDateFieldText(errors, from__, "from", "from", false);
        GSValidateCommon.validateDateFieldText(errors, to__, "to", "to", false);

        return errors;
    }

}
