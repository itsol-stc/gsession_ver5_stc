package jp.groupsession.v2.sch.model;

public interface ISchDataPushInf {

    /**
     * <p>scdSid を取得します。
     * @return scdSid
     * @see jp.groupsession.v2.sch.model.SchDataPushModel#scdSid__
     */
    int getScdSid();

    /**
     * <p>scdSid をセットします。
     * @param scdSid scdSid
     * @see jp.groupsession.v2.sch.model.SchDataPushModel#scdSid__
     */
    void setScdSid(int scdSid);

    /**
     * <p>scdTargetGrp を取得します。
     * @return scdTargetGrp
     * @see jp.groupsession.v2.sch.model.SchDataPushModel#scdTargetGrp__
     */
    int getScdTargetGrp();

    /**
     * <p>scdTargetGrp をセットします。
     * @param scdTargetGrp scdTargetGrp
     * @see jp.groupsession.v2.sch.model.SchDataPushModel#scdTargetGrp__
     */
    void setScdTargetGrp(int scdTargetGrp);

    /**
     * <p>scdReminder を取得します。
     * @return scdReminder
     * @see jp.groupsession.v2.sch.model.SchDataPushModel#scdReminder__
     */
    int getScdReminder();

    /**
     * <p>scdReminder をセットします。
     * @param scdReminder scdReminder
     * @see jp.groupsession.v2.sch.model.SchDataPushModel#scdReminder__
     */
    void setScdReminder(int scdReminder);

}