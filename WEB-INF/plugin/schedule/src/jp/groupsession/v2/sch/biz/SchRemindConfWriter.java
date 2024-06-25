package jp.groupsession.v2.sch.biz;

import java.util.Objects;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.SchEnumRemindMode;
import jp.groupsession.v2.cmn.model.SchEnumReminderTime;
import jp.groupsession.v2.sch.model.ISchDataPushInf;
import jp.groupsession.v2.sch.model.SchPriPushModel;

public class SchRemindConfWriter {
    /** TARGET_GRP mapping */
    private int targetGrp__ = GSConstSchedule.REMINDER_USE_NO;
    /** REMINDER mapping */
    private int reminder__ = GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES;
    /** TIME_KBN mapping */
    private int timeKbn__ = GSConstSchedule.TIME_EXIST;
    /** PushService useable*/
    private boolean pushUseable__ = false;

    /** リマインダー通知欄の設定モード*/
    private SchEnumRemindMode remindMode__;
    /** 旧スケジュールの通知設定*/
    private SchPriPushModel oldConf__;
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
    public void write(ISchDataPushInf scdMdl) {


        //時間指定無しの場合
        if (Objects.equals(timeKbn__,
                GSConstSchedule.TIME_NOT_EXIST)) {

            scdMdl.setScdTargetGrp(GSConstSchedule.REMINDER_USE_NO);
            return;
        }

        if (remindMode__ == SchEnumRemindMode.MINE) {

            scdMdl.setScdTargetGrp(GSConstSchedule.REMINDER_USE_NO);
            scdMdl.setScdReminder(reminder__);
            return;
        }

        if (remindMode__ == SchEnumRemindMode.OTHERS) {

            if (defConf__ != null) {
                scdMdl.setScdReminder(defConf__.getSccReminder());
            }

            if (oldConf__ != null) {
                scdMdl.setScdReminder(oldConf__.getSccReminder());
            }

            if (!pushUseable__) {
                if (SchEnumReminderTime.valueOf(scdMdl.getScdReminder())
                        == SchEnumReminderTime.REMINDER_TIME_NO) {
                    scdMdl.setScdReminder(SchEnumReminderTime.REMINDER_TIME_NO.getValue());
                }

            }
            return;
        }

        if (remindMode__ == SchEnumRemindMode.GROUP) {

            scdMdl.setScdTargetGrp(targetGrp__);
            scdMdl.setScdReminder(GSConstSchedule.REMINDER_TIME_NO);
            return;
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
        /** TARGET_GRP mapping */
        private int targetGrp__ = GSConstSchedule.REMINDER_USE_NO;
        /** REMINDER mapping */
        private int reminder__ = GSConstSchedule.REMINDER_TIME_FIFTEEN_MINUTES;
        /** TIME_KBN mapping */
        private int timeKbn__ = GSConstSchedule.TIME_EXIST;
        /** PushService useable*/
        private boolean pushUseable__ = false;
        /** リマインダー通知欄の設定モード*/
        private SchEnumRemindMode remindMode__;
        /** 旧スケジュールの通知設定*/
        private SchPriPushModel oldConf__;
        /** デフォルト通知設定*/
        private SchPriPushModel defConf__;

        /**
         *
         * コンストラクタ
         */
        private Builder() {

        }

        /**
         * <p>targetGrp をセットします。
         * @param targetGrp targetGrp
         * @return this
         * @see jp.groupsession.v2.sch.biz.SchRemindConfWriter.Builder#targetGrp__
         */
        public Builder setTargetGrp(int targetGrp) {
            targetGrp__ = targetGrp;
            return this;
        }
        /**
         * <p>reminder をセットします。
         * @param reminder reminder
         * @see jp.groupsession.v2.sch.biz.SchRemindConfWriter.Builder#reminder__
         * @return this
         */
        public Builder setReminder(int reminder) {
            reminder__ = reminder;
            return this;
        }
        /**
         * <p>timeKbn をセットします。
         * @param timeKbn timeKbn
         * @see jp.groupsession.v2.sch.biz.SchRemindConfWriter.Builder#timeKbn__
         * @return this
         */
        public Builder setTimeKbn(int timeKbn) {
            timeKbn__ = timeKbn;
            return this;
        }
        /**
         * <p>remindMode をセットします。
         * @param remindMode remindMode
         * @see jp.groupsession.v2.sch.biz.SchRemindConfWriter.Builder#remindMode__
         * @return this
         */
        public Builder setRemindMode(SchEnumRemindMode remindMode) {
            remindMode__ = remindMode;
            return this;
        }

        /**
         * <p>oldConf をセットします。
         * @param oldConf oldConf
         * @see jp.groupsession.v2.sch.biz.SchRemindConfWriter.Builder#oldConf__
         * @return this
         */
        public Builder setOldConf(SchPriPushModel oldConf) {
            oldConf__ = oldConf;
            return this;
        }

        /**
         * <p>defConf をセットします。
         * @param defConf defConf
         * @see jp.groupsession.v2.sch.biz.SchRemindConfWriter.Builder#defConf__
         * @return this
         */
        public Builder setDefConf(SchPriPushModel defConf) {
            defConf__ = defConf;
            return this;
        }

        /**
         * <p>pushUseable をセットします。
         * @param pushUseable pushUseable
         * @see jp.groupsession.v2.sch.biz.SchRemindConfWriter.Builder#pushUseable__
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
            ret.reminder__ = this.reminder__;
            ret.remindMode__ = this.remindMode__;
            ret.targetGrp__ = this.targetGrp__;
            ret.timeKbn__ = this.timeKbn__;
            ret.pushUseable__ = this.pushUseable__;
            return ret;
        }


    }




}
