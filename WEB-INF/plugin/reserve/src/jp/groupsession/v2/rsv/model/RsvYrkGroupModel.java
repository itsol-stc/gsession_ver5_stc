package jp.groupsession.v2.rsv.model;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 施設予約グループ モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvYrkGroupModel {
    /** 指定日 繰り返し登録は登録対象日 通常登録はfromに一致 */
    private UDate targetDate__;
    /** 指定施設SID */
    private int rsdSid__;

    /** 開始日次*/
    private UDate from__;
    /** 終了日時*/
    private UDate to__;
    /** スケジュール 施設予約紐づけSID*/
    private int scdRsSid__ = -1;
    /**
     * コンストラクタ
     */
    public RsvYrkGroupModel() {
    }
    /**
     * コンストラクタ
     * @param baseMdl ベース施設予約モデル
     */
    public RsvYrkGroupModel(RsvSisYrkModel baseMdl) {
        this();
        targetDate__ = baseMdl.getRsyFrDate().cloneUDate();
        from__ = baseMdl.getRsyFrDate().cloneUDate();
        to__ = baseMdl.getRsyToDate().cloneUDate();
        rsdSid__ = baseMdl.getRsdSid();
        if (baseMdl.getScdRsSid() > 0) {
            scdRsSid__ = baseMdl.getScdRsSid();
        }
    }
    /**
     * <p>targetDate を取得します。
     * @return targetDate
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#targetDate__
     */
    public UDate getTargetDate() {
        return targetDate__;
    }
    /**
     * <p>targetDate をセットします。
     * @param targetDate targetDate
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#targetDate__
     */
    public void setTargetDate(UDate targetDate) {
        targetDate__ = targetDate;
    }
    /**
     * <p>rsdSid を取得します。
     * @return rsdSid
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#rsdSid__
     */
    public int getRsdSid() {
        return rsdSid__;
    }
    /**
     * <p>rsdSid をセットします。
     * @param rsdSid rsdSid
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#rsdSid__
     */
    public void setRsdSid(int rsdSid) {
        rsdSid__ = rsdSid;
    }
    /**
     * <p>from を取得します。
     * @return from
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#from__
     */
    public UDate getFrom() {
        return from__;
    }
    /**
     * <p>from をセットします。
     * @param from from
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#from__
     */
    public void setFrom(UDate from) {
        from__ = from;
    }
    /**
     * <p>to を取得します。
     * @return to
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#to__
     */
    public UDate getTo() {
        return to__;
    }
    /**
     * <p>to をセットします。
     * @param to to
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#to__
     */
    public void setTo(UDate to) {
        to__ = to;
    }
    /**
     * <p>scdRsSid を取得します。
     * @return scdRsSid
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#scdRsSid__
     */
    public int getScdRsSid() {
        return scdRsSid__;
    }
    /**
     * <p>scdRsSid をセットします。
     * @param scdRsSid scdRsSid
     * @see jp.groupsession.v2.rsv.model.RsvYrkGroupModel#scdRsSid__
     */
    public void setScdRsSid(int scdRsSid) {
        scdRsSid__ = scdRsSid;
    }


}
