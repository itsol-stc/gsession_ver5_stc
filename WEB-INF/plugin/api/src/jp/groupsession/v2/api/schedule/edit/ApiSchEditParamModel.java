package jp.groupsession.v2.api.schedule.edit;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
/**
 *
 * <br>[機  能] スケジュール編集WEBAPI パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchEditParamModel extends AbstractParamModel {
    /**Schsid スケジュールSID*/
    private String schSid__;
    /**SchKf スケジュール公開フラグ*/
    private String schKf__;
    /**SchKfTarget スケジュール公開対象 */
    private String[] schKfTarget__;
    /**SchEf スケジュール編集フラグ*/
    private String schEf__;
    /**Title*/
    private String title__;
    /**Naiyo*/
    private String naiyo__;
    /** frDate*/
    private UDate frDate__;
    /** toDate*/
    private UDate toDate__;
    /**TimeKbn*/
    private String timeKbn__;
    /**UserKbn*/
    private String userKbn__ = "0";
    /**UserSid*/
    private String usrSid__;
    /**targetId*/
    private String targetId__;
    /**ColorKbn*/
    private String colorKbn__;
    /**Biko*/
    private String biko__;
    /**同時登録ユーザ*/
    private String[] sameScheduledUser__;
    /**施設予約*/
    private String[] reserves__;
    /**企業SID*/
    private String[] acoSid__;
    /**企業拠点SID*/
    private String[] abaSid__;
    /**アドレスSid*/
    private String[] adress__;
    /** 同時登録スケジュール編集*/
    private String batchRef__;
    /** 施設予約編集*/
    private String batchResRef__;
    /** コピーフラグ*/
    private String copyFlg__;
    /** 重複警告無視フラグ*/
    private String wornCommit__;
    /**コンタクト履歴編集フラグ*/
    private String contactFlg__;
    /** 出欠確認*/
    private String attendKbn__;
    /** 出欠確認メール再通知*/
    private String attendMailResendKbn__;
    /** リマインダー通知 グループ通知 */
    private String targetGrp__;
    /** リマインダー通知 通知時間 */
    private String reminder__;

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
     * <p>schKf を取得します。
     * @return schKf
     */
    public String getSchKf() {
        return schKf__;
    }
    /**
     * <p>schKf をセットします。
     * @param schKf schKf
     */
    public void setSchKf(String schKf) {
        schKf__ = schKf;
    }
    /**
     * <p>schKfTarget を取得します。
     * @return schKfTarget
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#schKfTarget__
     */
    public String[] getSchKfTarget() {
        return schKfTarget__;
    }
    /**
     * <p>schKfTarget をセットします。
     * @param schKfTarget schKfTarget
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#schKfTarget__
     */
    public void setSchKfTarget(String[] schKfTarget) {
        schKfTarget__ = schKfTarget;
    }
    /**
     * <p>schEf を取得します。
     * @return schEf
     */
    public String getSchEf() {
        return schEf__;
    }
    /**
     * <p>schEf をセットします。
     * @param schEf schEf
     */
    public void setSchEf(String schEf) {
        schEf__ = schEf;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>naiyo を取得します。
     * @return naiyo
     */
    public String getNaiyo() {
        return naiyo__;
    }
    /**
     * <p>naiyo をセットします。
     * @param naiyo naiyo
     */
    public void setNaiyo(String naiyo) {
        naiyo__ = naiyo;
    }
    /**
     * <p>from を取得します。
     * @return from
     */
    public String getFrom() {
        return frDate__.getDateString("/") + " " + UDateUtil.getSeparateHM(frDate__);
    }
    /**
     * <p>from をセットします。
     * @param from from
     */
    public void setFrom(String from) {
        UDate frdate = UDateUtil.getUDate(from.substring(0, 4),
                from.substring(5, 7),
                from.substring(8, 10));
        frdate.setHour(Integer.valueOf(from.substring(11, 13)));
        frdate.setMinute(Integer.valueOf(from.substring(14, 16)));

        frDate__ = frdate;
    }
    /**
     * <p>to を取得します。
     * @return to
     */
    public String getTo() {
        return toDate__.getDateString("/") + " " + UDateUtil.getSeparateHM(toDate__);
    }
    /**
     * <p>to をセットします。
     * @param to to
     */
    public void setTo(String to) {
        UDate todate = UDateUtil.getUDate(to.substring(0, 4),
                to.substring(5, 7),
                to.substring(8, 10));
        todate.setHour(Integer.valueOf(to.substring(11, 13)));
        todate.setMinute(Integer.valueOf(to.substring(14, 16)));

        toDate__ = todate;
    }
    /**
     * <p>frDate を取得します。
     * @return frDate
     */
    public UDate getFrDate() {
        return frDate__;
    }
    /**
     * <p>frDate をセットします。
     * @param frDate frDate
     */
    public void setFrDate(UDate frDate) {
        frDate__  = frDate;
    }
    /**
     * <p>toDate を取得します。
     * @return toDate
     */
    public UDate getToDate() {
        return toDate__;
    }
    /**
     * <p>toDate をセットします。
     * @param toDate toDate
     */
    public void setToDate(UDate toDate) {
        toDate__ = toDate;
    }

    /**
     * <p>timeKbn を取得します。
     * @return timeKbn
     */
    public String getTimeKbn() {
        return timeKbn__;
    }
    /**
     * <p>timeKbn をセットします。
     * @param timeKbn timeKbn
     */
    public void setTimeKbn(String timeKbn) {
        timeKbn__ = timeKbn;
    }
    /**
     * <p>userKbn を取得します。
     * @return userKbn
     */
    public String getUserKbn() {
        return userKbn__;
    }
    /**
     * <p>userKbn をセットします。
     * @param userKbn userKbn
     */
    public void setUserKbn(String userKbn) {
        userKbn__ = userKbn;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>targetId を取得します。
     * @return targetId
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#targetId__
     */
    public String getTargetId() {
        return targetId__;
    }
    /**
     * <p>targetId をセットします。
     * @param targetId targetId
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#targetId__
     */
    public void setTargetId(String targetId) {
        targetId__ = targetId;
    }
    /**
     * <p>colorKbn を取得します。
     * @return colorKbn
     */
    public String getColorKbn() {
        return colorKbn__;
    }
    /**
     * <p>colorKbn をセットします。
     * @param colorKbn colorKbn
     */
    public void setColorKbn(String colorKbn) {
        colorKbn__ = colorKbn;
    }
    /**
     * <p>biko を取得します。
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }
    /**
     * <p>biko をセットします。
     * @param biko biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }
    /**
     * <p>sameScheduledUser を取得します。
     * @return sameScheduledUser
     */
    public String[] getSameScheduledUser() {
        return sameScheduledUser__;
    }
    /**
     * <p>sameScheduledUser をセットします。
     * @param sameScheduledUser sameScheduledUser
     */
    public void setSameScheduledUser(String[] sameScheduledUser) {
        sameScheduledUser__ = sameScheduledUser;
    }
    /**
     * <p>reserves を取得します。
     * @return reserves
     */
    public String[] getReserves() {
        return reserves__;
    }
    /**
     * <p>reserves をセットします。
     * @param reserves reserves
     */
    public void setReserves(String[] reserves) {
        reserves__ = reserves;
    }
    /**
     * <p>acoSid を取得します。
     * @return acoSid
     */
    public String[] getAcoSid() {
        return acoSid__;
    }
    /**
     * <p>acoSid をセットします。
     * @param acoSid acoSid
     */
    public void setAcoSid(String[] acoSid) {
        acoSid__ = acoSid;
    }
    /**
     * <p>abaSid を取得します。
     * @return abaSid
     */
    public String[] getAbaSid() {
        return abaSid__;
    }
    /**
     * <p>abaSid をセットします。
     * @param abaSid abaSid
     */
    public void setAbaSid(String[] abaSid) {
        abaSid__ = abaSid;
    }
    /**
     * <p>adress を取得します。
     * @return adress
     */
    public String[] getAdress() {
        return adress__;
    }
    /**
     * <p>adress をセットします。
     * @param adress adress
     */
    public void setAdress(String[] adress) {
        adress__ = adress;
    }

    /**
     * <p>batchRef を取得します。
     * @return batchRef
     */
    public String getBatchRef() {
        return batchRef__;
    }
    /**
     * <p>batchRef をセットします。
     * @param batchRef batchRef
     */
    public void setBatchRef(String batchRef) {
        batchRef__ = batchRef;
    }
    /**
     * <p>batchResRef を取得します。
     * @return batchResRef
     */
    public String getBatchResRef() {
        return batchResRef__;
    }
    /**
     * <p>batchResRef をセットします。
     * @param batchResRef batchResRef
     */
    public void setBatchResRef(String batchResRef) {
        batchResRef__ = batchResRef;
    }
    /**
     * <p>copyFlg を取得します。
     * @return copyFlg
     */
    public String getCopyFlg() {
        return copyFlg__;
    }
    /**
     * <p>copyFlg をセットします。
     * @param copyFlg copyFlg
     */
    public void setCopyFlg(String copyFlg) {
        copyFlg__ = copyFlg;
    }
    /**
     * <p>wornCommit を取得します。
     * @return wornCommit
     */
    public String getWornCommit() {
        return wornCommit__;
    }
    /**
     * <p>wornCommit をセットします。
     * @param wornCommit wornCommit
     */
    public void setWornCommit(String wornCommit) {
        wornCommit__ = wornCommit;
    }
    /**
     * <p>contact を取得します。
     * @return contact
     */
    public String getContactFlg() {
        return contactFlg__;
    }
    /**
     * <p>contact をセットします。
     * @param contactFlg contactFlg
     */
    public void setContactFlg(String contactFlg) {
        contactFlg__ = contactFlg;
    }
    /**
     * <p>attendKbn を取得します。
     * @return attendKbn
     */
    public String getAttendKbn() {
        return attendKbn__;
    }
    /**
     * <p>attendKbn をセットします。
     * @param attendKbn attendKbn
     */
    public void setAttendKbn(String attendKbn) {
        attendKbn__ = attendKbn;
    }
    /**
     * <p>attendMailResendKbn を取得します。
     * @return attendMailResendKbn
     */
    public String getAttendMailResendKbn() {
        return attendMailResendKbn__;
    }
    /**
     * <p>attendMailResendKbn をセットします。
     * @param attendMailResendKbn attendMailResendKbn
     */
    public void setAttendMailResendKbn(String attendMailResendKbn) {
        attendMailResendKbn__ = attendMailResendKbn;
    }
    /**
     * <p>targetGrp を取得します。
     * @return targetGrp
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#targetGrp__
     */
    public String getTargetGrp() {
        return targetGrp__;
    }
    /**
     * <p>targetGrp をセットします。
     * @param targetGrp targetGrp
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#targetGrp__
     */
    public void setTargetGrp(String targetGrp) {
        targetGrp__ = targetGrp;
    }
    /**
     * <p>reminder を取得します。
     * @return reminder
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#reminder__
     */
    public String getReminder() {
        return reminder__;
    }
    /**
     * <p>reminder をセットします。
     * @param reminder reminder
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#reminder__
     */
    public void setReminder(String reminder) {
        reminder__ = reminder;
    }
}
