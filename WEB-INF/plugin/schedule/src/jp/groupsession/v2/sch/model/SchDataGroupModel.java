package jp.groupsession.v2.sch.model;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] スケジュール同時登録処理に使用
 * <br>[解  説] 同時登録されるスケジュール共通情報を扱う
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchDataGroupModel {

    /** スケジュール同時登録SID*/
    private int scdGrpSid__;
    /** スケジュール指定日 繰り返し登録は登録対象日 通常登録はfromに一致 */
    private UDate targetDate__;
    /** スケジュール開始日次*/
    private UDate from__;
    /** スケジュール終了日時*/
    private UDate to__;
    /** スケジュール対象区分*/
    private int scdUsrKbn__;
    /** スケジュール対象SID*/
    private int scdUsrSid__;
    /** スケジュール 施設予約紐づけSID*/
    private int scdResSid__ = -1;

    /**
     *
     * コンストラクタ
     * @param usrKbn ユーザ区分 0:ユーザ 1:グループ
     * @param usrSid 対象SID
     */
    public SchDataGroupModel(int usrKbn, int usrSid) {
        scdUsrKbn__ = usrKbn;
        scdUsrSid__ = usrSid;
    }
    /**
     *
     * コンストラクタ
     * @param sch ベーススケジュール
     */
    public SchDataGroupModel(SchDataModel sch) {
        this(sch.getScdUsrKbn(), sch.getScdUsrSid());
        targetDate__ = sch.getScdFrDate().cloneUDate();
        from__ = sch.getScdFrDate().cloneUDate();
        to__ = sch.getScdToDate().cloneUDate();
        scdGrpSid__ = sch.getScdGrpSid();
        if (sch.getScdRsSid() > 0) {
            scdResSid__ = sch.getScdRsSid();
        }
    }


    /**
     * <p>scdGrpSid を取得します。
     * @return scdGrpSid
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#schGrpSid__
     */
    public int getScdGrpSid() {
        return scdGrpSid__;
    }
    /**
     * <p>schGrpSid をセットします。
     * @param scdGrpSid scdGrpSid
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#scdGrpSid__
     */
    public void setScdGrpSid(int scdGrpSid) {
        scdGrpSid__ = scdGrpSid;
    }
    /**
     * <p>targetDate を取得します。
     * @return targetDate
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#targetDate__
     */
    public UDate getTargetDate() {
        return targetDate__;
    }
    /**
     * <p>targetDate をセットします。
     * @param targetDate targetDate
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#targetDate__
     */
    public void setTargetDate(UDate targetDate) {
        targetDate__ = targetDate;
    }
    /**
     * <p>from を取得します。
     * @return from
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#from__
     */
    public UDate getFrom() {
        return from__;
    }
    /**
     * <p>from をセットします。
     * @param from from
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#from__
     */
    public void setFrom(UDate from) {
        from__ = from;
    }
    /**
     * <p>to を取得します。
     * @return to
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#to__
     */
    public UDate getTo() {
        return to__;
    }
    /**
     * <p>to をセットします。
     * @param to to
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#to__
     */
    public void setTo(UDate to) {
        to__ = to;
    }
    /**
     * <p>scdUsrKbn を取得します。
     * @return scdUsrKbn
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#scdUsrKbn__
     */
    public int getScdUsrKbn() {
        return scdUsrKbn__;
    }
    /**
     * <p>scdUsrKbn をセットします。
     * @param scdUsrKbn scdUsrKbn
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#scdUsrKbn__
     */
    public void setScdUsrKbn(int scdUsrKbn) {
        scdUsrKbn__ = scdUsrKbn;
    }
    /**
     * <p>scdUsrSid を取得します。
     * @return scdUsrSid
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#scdUsrSid__
     */
    public int getScdUsrSid() {
        return scdUsrSid__;
    }
    /**
     * <p>scdUsrSid をセットします。
     * @param scdUsrSid scdUsrSid
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#scdUsrSid__
     */
    public void setScdUsrSid(int scdUsrSid) {
        scdUsrSid__ = scdUsrSid;
    }
    /**
     * <p>scdResSid を取得します。
     * @return scdResSid
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#scdResSid__
     */
    public int getScdResSid() {
        return scdResSid__;
    }
    /**
     * <p>scdResSid をセットします。
     * @param scdResSid scdResSid
     * @see jp.groupsession.v2.sch.model.SchDataGroupModel#scdResSid__
     */
    public void setScdResSid(int scdResSid) {
        scdResSid__ = scdResSid;
    }

}
