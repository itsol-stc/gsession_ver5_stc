package jp.groupsession.v2.api.schedule.reminder;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] スケジュール リマインダー編集 WEBAPI パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchChangeReminderParamModel extends AbstractParamModel {
    // schSid スケジュールSID
    private String schSid__;

    // reminder リマインダー通知時間
    private String reminder__;

    /**
     * <p>schSid を取得します。
     * @return schSid
     * @see jp.groupsession.v2.api.schedule.reminder.ApiSchChangeReminderForm#schSid__
     */
    public String getSchSid() {
        return schSid__;
    }
    /**
     * <p>schSid をセットします。
     * @param schSid schSid
     * @see jp.groupsession.v2.api.schedule.reminder.ApiSchChangeReminderForm#schSid__
     */
    public void setSchSid(String schSid) {
        schSid__ = schSid;
    }
    /**
     * <p>reminder を取得します。
     * @return reminder
     * @see jp.groupsession.v2.api.schedule.reminder.ApiSchChangeReminderForm#reminder__
     */
    public String getReminder() {
        return reminder__;
    }
    /**
     * <p>reminder をセットします。
     * @param reminder reminder
     * @see jp.groupsession.v2.api.schedule.reminder.ApiSchChangeReminderForm#reminder__
     */
    public void setReminder(String reminder) {
        reminder__ = reminder;
    }
}
