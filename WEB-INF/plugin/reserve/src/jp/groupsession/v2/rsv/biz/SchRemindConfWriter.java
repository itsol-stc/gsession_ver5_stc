package jp.groupsession.v2.rsv.biz;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.SchEnumReminderTime;
import jp.groupsession.v2.rsv.model.RsvScdOperationModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
/**
 *
 * <br>[機  能] スケジュール リマインダー設定編集クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchRemindConfWriter {
    /** PushService useable*/
    private boolean pushUseable__ = false;
    /** リマインダー通知欄の設定モード*/
    private SchEnumRemindMode remindMode__;
    /** 旧スケジュールの通知設定*/
    private RsvScdOperationModel oldConf__;
    /** デフォルト通知設定*/
    private SchPriPushModel defConf__;

    /**
     *
     * コンストラクタ
     */
    private SchRemindConfWriter() {

    }
    /**
     *
     * <br>[機  能] ビルダーインスタンスを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダー
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     *
     * <br>[機  能] スケジュールモデルにリマインダー通知設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param scdMdl 設定対象スケジュール
     */
    public void write(RsvScdOperationModel scdMdl) {

        if (remindMode__ == SchEnumRemindMode.GROUP) {

            scdMdl.setScdReminder(GSConstSchedule.REMINDER_TIME_NO);

            if (oldConf__ != null) {
                scdMdl.setScdTargetGrp(oldConf__.getScdTargetGrp());
            } else {
                scdMdl.setScdTargetGrp(GSConstSchedule.REMINDER_USE_YES);
            }

            return;
        }

        scdMdl.setScdTargetGrp(GSConstSchedule.REMINDER_USE_NO);
        SchPriPushModel oldRemConf = null;
        if (oldConf__ != null) {
            oldRemConf = new SchPriPushModel();
            oldRemConf.setSccReminder(oldConf__.getScdReminder());
        }

        if (defConf__ != null) {
            scdMdl.setScdReminder(defConf__.getSccReminder());
        }

        if (oldRemConf != null) {
            scdMdl.setScdReminder(oldRemConf.getSccReminder());
        }

        if (!pushUseable__) {
            if (SchEnumReminderTime.valueOf(scdMdl.getScdReminder())
                    == SchEnumReminderTime.REMINDER_TIME_NO) {
                scdMdl.setScdReminder(SchEnumReminderTime.REMINDER_TIME_NO.getValue());
            }

        }

    }

    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class Builder {
        /** PushService useable*/
        private boolean pushUseable__ = false;
        /** リマインダー通知欄の設定モード*/
        private SchEnumRemindMode remindMode__;
        /** 旧スケジュールの通知設定*/
        private RsvScdOperationModel oldConf__;
        /** デフォルト通知設定*/
        private SchPriPushModel defConf__;

        /**
         *
         * コンストラクタ
         */
        private Builder() {

        }

        /**
         * <p>remindMode をセットします。
         * @param remindMode remindMode
         * @see jp.groupsession.v2.rsv.biz.SchRemindConfWriter.Builder#remindMode__
         * @return this
         */
        public Builder setRemindMode(SchEnumRemindMode remindMode) {
            remindMode__ = remindMode;
            return this;
        }

        /**
         * <p>oldConf をセットします。
         * @param oldConf oldConf
         * @see jp.groupsession.v2.rsv.biz.SchRemindConfWriter.Builder#oldConf__
         * @return this
         */
        public Builder setOldConf(RsvScdOperationModel oldConf) {
            oldConf__ = oldConf;
            return this;
        }

        /**
         * <p>defConf をセットします。
         * @param defConf defConf
         * @see jp.groupsession.v2.rsv.biz.SchRemindConfWriter.Builder#defConf__
         * @return this
         */
        public Builder setDefConf(SchPriPushModel defConf) {
            defConf__ = defConf;
            return this;
        }

        /**
         * <p>pushUseable をセットします。
         * @param pushUseable pushUseable
         * @see jp.groupsession.v2.rsv.biz.SchRemindConfWriter.Builder#pushUseable__
         * @return this
         */
        public Builder setPushUseable(boolean pushUseable) {
            pushUseable__ = pushUseable;
            return this;
        }

        /**
         *
         * <br>[機  能] SchRemindConfWriterインスタンス生成
         * <br>[解  説]
         * <br>[備  考]
         * @return SchRemindConfWriter
         */
        public SchRemindConfWriter build() {
            SchRemindConfWriter ret = new SchRemindConfWriter();
            ret.defConf__ = this.defConf__;
            ret.oldConf__ = this.oldConf__;
            ret.remindMode__ = this.remindMode__;
            ret.pushUseable__ = this.pushUseable__;
            return ret;
        }

    }

}
