package jp.groupsession.v2.sch.sch210;

import jp.groupsession.v2.sch.sch040kn.Sch040knParamModel;

/**
 * <br>[機  能] スケジュール確認ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch210ParamModel extends Sch040knParamModel {

    /** リマインダー通知時間 */
    private String sch210ReminderTimeText__ = null;

    /**
     * <p>sch210ReminderTimeText を取得します。
     * @return sch210ReminderTimeText
     * @see jp.groupsession.v2.sch.sch210.Sch210Form#sch210ReminderTimeText__
     */
    public String getSch210ReminderTimeText() {
        return sch210ReminderTimeText__;
    }

    /**
     * <p>sch210ReminderTimeText をセットします。
     * @param sch210ReminderTimeText sch210ReminderTimeText
     * @see jp.groupsession.v2.sch.sch210.Sch210Form#sch210ReminderTimeText__
     */
    public void setSch210ReminderTimeText(String sch210ReminderTimeText) {
        sch210ReminderTimeText__ = sch210ReminderTimeText;
    }

}
