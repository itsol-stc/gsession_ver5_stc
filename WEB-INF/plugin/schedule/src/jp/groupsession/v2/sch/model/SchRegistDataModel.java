package jp.groupsession.v2.sch.model;

import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] スケジュール登録用モデル
 * <br>[解  説] 複数のスケジュールの共通データのbaseSchと
 * <br>         SchDataGroupModel、SchDataPushModelの参照で構成
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchRegistDataModel extends SchDataModel {

    /** SCD_SID mapping */
    private final int scdSid__;
    /** SCD_USR_SID mapping */
    private final int scdUsrSid__;
    /** 再利用用ベースモデル*/
    private final SchDataModel baseSch__;
    /** 同時登録管理モデル*/
    private final SchDataGroupModel groupData__;
    /** プッシュ通知設定モデル*/
    private final SchDataPushModel pushData__;
    /** 添付バイナリSIDリスト*/
    private final List<Long> binSidList__;

    /**
     * コンストラクタ
     * @param scdSid
     * @param scdUsrSid
     * @param baseSch
     * @param groupData
     * @param pushData
     * @param binSidList
     */
    public SchRegistDataModel(int scdSid, int scdUsrSid,
            SchDataModel baseSch, SchDataGroupModel groupData,
            SchDataPushModel pushData, List<Long> binSidList) {
        super();
        scdSid__ = scdSid;
        scdUsrSid__ = scdUsrSid;
        baseSch__ = baseSch;
        groupData__ = groupData;
        pushData__ = pushData;
        binSidList__ = binSidList;
    }

    /**
     * <p>scdSid を取得します。
     * @return scdSid
     * @see jp.groupsession.v2.sch.model.SchRegistDataModel#scdSid__
     */
    public int getScdSid() {
        return scdSid__;
    }

    /**
     * <p>scdUsrSid を取得します。
     * @return scdUsrSid
     * @see jp.groupsession.v2.sch.model.SchRegistDataModel#scdUsrSid__
     */
    public int getScdUsrSid() {
        return scdUsrSid__;
    }

    /**
     * <p>baseSch を取得します。
     * @return baseSch
     * @see jp.groupsession.v2.sch.model.SchRegistDataModel#baseSch__
     */
    public SchDataModel getBaseSch() {
        return baseSch__;
    }

    /**
     * <p>groupData を取得します。
     * @return groupData
     * @see jp.groupsession.v2.sch.model.SchRegistDataModel#groupData__
     */
    public SchDataGroupModel getGroupData() {
        return groupData__;
    }

    /**
     * <p>pushData を取得します。
     * @return pushData
     * @see jp.groupsession.v2.sch.model.SchRegistDataModel#pushData__
     */
    public SchDataPushModel getPushData() {
        return pushData__;
    }

    /**
     * <p>binSidList を取得します。
     * @return binSidList
     * @see jp.groupsession.v2.sch.model.SchRegistDataModel#binSidList__
     */
    public List<Long> getBinSidList() {
        return binSidList__;
    }



    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdAppendUrl()
     */
    @Override
    public String getScdAppendUrl() {
        return baseSch__.getScdAppendUrl();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdAppendId()
     */
    @Override
    public String getScdAppendId() {
        return baseSch__.getScdAppendId();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdUserName()
     */
    @Override
    public String getScdUserName() {
        return baseSch__.getScdUserName();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdRsSid()
     */
    @Override
    public int getScdRsSid() {
        return baseSch__.getScdRsSid();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdEdit()
     */
    @Override
    public int getScdEdit() {
        return baseSch__.getScdEdit();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getSceSid()
     */
    @Override
    public int getSceSid() {
        return baseSch__.getSceSid();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdGrpSid()
     */
    @Override
    public int getScdGrpSid() {
        return baseSch__.getScdGrpSid();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdUsrKbn()
     */
    @Override
    public int getScdUsrKbn() {
        return baseSch__.getScdUsrKbn();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdFrDate()
     */
    @Override
    public UDate getScdFrDate() {
        return groupData__.getFrom();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdToDate()
     */
    @Override
    public UDate getScdToDate() {
        return groupData__.getTo();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdDaily()
     */
    @Override
    public int getScdDaily() {
        return baseSch__.getScdDaily();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdBgcolor()
     */
    @Override
    public int getScdBgcolor() {
        return baseSch__.getScdBgcolor();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdTitle()
     */
    @Override
    public String getScdTitle() {
        return baseSch__.getScdTitle();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdValue()
     */
    @Override
    public String getScdValue() {
        return baseSch__.getScdValue();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdBiko()
     */
    @Override
    public String getScdBiko() {
        return baseSch__.getScdBiko();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdPublic()
     */
    @Override
    public int getScdPublic() {
        return baseSch__.getScdPublic();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdAuid()
     */
    @Override
    public int getScdAuid() {
        return baseSch__.getScdAuid();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdAdate()
     */
    @Override
    public UDate getScdAdate() {
        return baseSch__.getScdAdate();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdEuid()
     */
    @Override
    public int getScdEuid() {
        return baseSch__.getScdEuid();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdEdate()
     */
    @Override
    public UDate getScdEdate() {
        return baseSch__.getScdEdate();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdUserBlongGpList()
     */
    @Override
    public ArrayList<Integer> getScdUserBlongGpList() {
        return baseSch__.getScdUserBlongGpList();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdAttendKbn()
     */
    @Override
    public int getScdAttendKbn() {
        return baseSch__.getScdAttendKbn();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdAttendAns()
     */
    @Override
    public int getScdAttendAns() {
        return baseSch__.getScdAttendAns();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdAttendAuKbn()
     */
    @Override
    public int getScdAttendAuKbn() {
        return baseSch__.getScdAttendAuKbn();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdAppendDspName()
     */
    @Override
    public String getScdAppendDspName() {
        return baseSch__.getScdAppendDspName();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdUserUkoFlg()
     */
    @Override
    public int getScdUserUkoFlg() {
        return baseSch__.getScdUserUkoFlg();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdAttendComment()
     */
    @Override
    public String getScdAttendComment() {
        return baseSch__.getScdAttendComment();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdTargetGrp()
     */
    @Override
    public int getScdTargetGrp() {
        return pushData__.getScdTargetGrp();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#getScdReminder()
     */
    @Override
    public int getScdReminder() {
        return pushData__.getScdReminder();
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdAppendUrl(java.lang.String)
     */
    @Override
    public void setScdAppendUrl(String scdAppendUrl) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdAppendId(java.lang.String)
     */
    @Override
    public void setScdAppendId(String scdAppendId) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdUserName(java.lang.String)
     */
    @Override
    public void setScdUserName(String scdUserName) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdRsSid(int)
     */
    @Override
    public void setScdRsSid(int scdRsSid) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdEdit(int)
     */
    @Override
    public void setScdEdit(int scdEdit) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setSceSid(int)
     */
    @Override
    public void setSceSid(int sceSid) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdSid(int)
     */
    @Override
    public void setScdSid(int scdSid) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdGrpSid(int)
     */
    @Override
    public void setScdGrpSid(int scdGrpSid) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdUsrKbn(int)
     */
    @Override
    public void setScdUsrKbn(int scdUsrKbn) {
    }


    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdUsrSid(int)
     */
    @Override
    public void setScdUsrSid(int scdUsrSid) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdFrDate(jp.co.sjts.util.date.UDate)
     */
    @Override
    public void setScdFrDate(UDate scdFrDate) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdToDate(jp.co.sjts.util.date.UDate)
     */
    @Override
    public void setScdToDate(UDate scdToDate) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdDaily(int)
     */
    @Override
    public void setScdDaily(int scdDaily) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdBgcolor(int)
     */
    @Override
    public void setScdBgcolor(int scdBgcolor) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdTitle(java.lang.String)
     */
    @Override
    public void setScdTitle(String scdTitle) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdValue(java.lang.String)
     */
    @Override
    public void setScdValue(String scdValue) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdBiko(java.lang.String)
     */
    @Override
    public void setScdBiko(String scdBiko) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdPublic(int)
     */
    @Override
    public void setScdPublic(int scdPublic) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdAuid(int)
     */
    @Override
    public void setScdAuid(int scdAuid) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdAdate(jp.co.sjts.util.date.UDate)
     */
    @Override
    public void setScdAdate(UDate scdAdate) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdEuid(int)
     */
    @Override
    public void setScdEuid(int scdEuid) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdEdate(jp.co.sjts.util.date.UDate)
     */
    @Override
    public void setScdEdate(UDate scdEdate) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdUserBlongGpList(java.util.ArrayList)
     */
    @Override
    public void setScdUserBlongGpList(ArrayList<Integer> scdUserBlongGpList) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdAttendKbn(int)
     */
    @Override
    public void setScdAttendKbn(int scdAttendKbn) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdAttendAns(int)
     */
    @Override
    public void setScdAttendAns(int scdAttendAns) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdAttendAuKbn(int)
     */
    @Override
    public void setScdAttendAuKbn(int scdAttendAuKbn) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdAppendDspName(java.lang.String)
     */
    @Override
    public void setScdAppendDspName(String scdAppendDspName) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdUserUkoFlg(int)
     */
    @Override
    public void setScdUserUkoFlg(int scdUserUkoFlg) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdAttendComment(java.lang.String)
     */
    @Override
    public void setScdAttendComment(String scdAttendComment) {
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdTargetGrp(int)
     */
    @Override
    public void setScdTargetGrp(int scdTargetGrp) {
        pushData__.setScdTargetGrp(scdTargetGrp);
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.model.SchDataModel#setScdReminder(int)
     */
    @Override
    public void setScdReminder(int scdReminder) {
        pushData__.setScdReminder(scdReminder);
    }

}
