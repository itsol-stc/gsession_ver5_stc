package jp.groupsession.v2.sch.model;

/**
 * <br>[機  能] スケジュール同時登録処理に使用
 * <br>[解  説] SchDataModelの同名のフィールドに対応する
 * <br>         SchDataModelの同時登録で共通化できない情報を切り出している
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchDataPushModel implements ISchDataPushInf {
    /** SCD_SID mapping */
    private int scdSid__;
    /** SCD_TARGET_GRP mapping */
    private int scdTargetGrp__;
    /** SCD_REMINDER mapping */
    private int scdReminder__;

    /**
     *
     * コンストラクタ
     * @param base パラメータコピー元
     */
    public SchDataPushModel(ISchDataPushInf base) {
        scdSid__ = base.getScdSid();
        scdTargetGrp__ = base.getScdTargetGrp();
        scdReminder__ = base.getScdReminder();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.ISchDataPushInf#getScdSid()
     */
    @Override
    public int getScdSid() {
        return scdSid__;
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.ISchDataPushInf#setScdSid(int)
     */
    @Override
    public void setScdSid(int scdSid) {
        scdSid__ = scdSid;
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.ISchDataPushInf#getScdTargetGrp()
     */
    @Override
    public int getScdTargetGrp() {
        return scdTargetGrp__;
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.ISchDataPushInf#setScdTargetGrp(int)
     */
    @Override
    public void setScdTargetGrp(int scdTargetGrp) {
        scdTargetGrp__ = scdTargetGrp;
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.ISchDataPushInf#getScdReminder()
     */
    @Override
    public int getScdReminder() {
        return scdReminder__;
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.ISchDataPushInf#setScdReminder(int)
     */
    @Override
    public void setScdReminder(int scdReminder) {
        scdReminder__ = scdReminder;
    }



}
