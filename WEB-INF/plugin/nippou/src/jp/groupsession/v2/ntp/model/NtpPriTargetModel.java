package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import java.io.Serializable;

/**
 * <p>NTP_TEMPLATE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpPriTargetModel implements Serializable {

    /** NTG_SID mapping */
    private int ntgSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** NPG_YEAR mapping */
    private int npgYear__;
    /** NPG_MONTH mapping */
    private int npgMonth__;
    /** NPG_DATE mapping */
    private UDate npgDate__;
    /** NPG_TARGET mapping */
    private Long npgTarget__;
    /** NPG_RECORD mapping */
    private Long npgRecord__;
    /** NPG_AUID mapping */
    private int npgAuid__;
    /** NPG_ADATE mapping */
    private UDate npgAdate__;
    /** NPG_EUID mapping */
    private int npgEuid__;
    /** NPG_EDATE mapping */
    private UDate npgEdate__;

    /** 目標名 */
    private String npgTargetName__;
    /** 単位 */
    private String npgTargetUnit__;
    /** 達成率 文字列 */
    private String npgTargetRatioStr__;
    /** 達成率 */
    private double npgTargetRatio__;
    /** 未達成率 */
    private double npgTargetUnRatio__;
    /** 目標区分 0:未達成 1:達成 */
    private int npgTargetKbn__ = 0;

    /**
     * <p>ntgSid を取得します。
     * @return ntgSid
     */
    public int getNtgSid() {
        return ntgSid__;
    }
    /**
     * <p>ntgSid をセットします。
     * @param ntgSid ntgSid
     */
    public void setNtgSid(int ntgSid) {
        ntgSid__ = ntgSid;
    }
    /**
     * <p>npgYear を取得します。
     * @return npgYear
     */
    public int getNpgYear() {
        return npgYear__;
    }
    /**
     * <p>npgYear をセットします。
     * @param npgYear npgYear
     */
    public void setNpgYear(int npgYear) {
        npgYear__ = npgYear;
    }
    /**
     * <p>npgMonth を取得します。
     * @return npgMonth
     */
    public int getNpgMonth() {
        return npgMonth__;
    }
    /**
     * <p>npgMonth をセットします。
     * @param npgMonth npgMonth
     */
    public void setNpgMonth(int npgMonth) {
        npgMonth__ = npgMonth;
    }
    /**
     * <p>npgAuid を取得します。
     * @return npgAuid
     */
    public int getNpgAuid() {
        return npgAuid__;
    }
    /**
     * <p>npgAuid をセットします。
     * @param npgAuid npgAuid
     */
    public void setNpgAuid(int npgAuid) {
        npgAuid__ = npgAuid;
    }
    /**
     * <p>npgAdate を取得します。
     * @return npgAdate
     */
    public UDate getNpgAdate() {
        return npgAdate__;
    }
    /**
     * <p>npgAdate をセットします。
     * @param npgAdate npgAdate
     */
    public void setNpgAdate(UDate npgAdate) {
        npgAdate__ = npgAdate;
    }
    /**
     * <p>npgEuid を取得します。
     * @return npgEuid
     */
    public int getNpgEuid() {
        return npgEuid__;
    }
    /**
     * <p>npgEuid をセットします。
     * @param npgEuid npgEuid
     */
    public void setNpgEuid(int npgEuid) {
        npgEuid__ = npgEuid;
    }
    /**
     * <p>npgEdate を取得します。
     * @return npgEdate
     */
    public UDate getNpgEdate() {
        return npgEdate__;
    }
    /**
     * <p>npgEdate をセットします。
     * @param npgEdate npgEdate
     */
    public void setNpgEdate(UDate npgEdate) {
        npgEdate__ = npgEdate;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>npgDate を取得します。
     * @return npgDate
     */
    public UDate getNpgDate() {
        return npgDate__;
    }
    /**
     * <p>npgDate をセットします。
     * @param npgDate npgDate
     */
    public void setNpgDate(UDate npgDate) {
        npgDate__ = npgDate;
    }
    /**
     * <p>npgTargetName を取得します。
     * @return npgTargetName
     */
    public String getNpgTargetName() {
        return npgTargetName__;
    }
    /**
     * <p>npgTargetName をセットします。
     * @param npgTargetName npgTargetName
     */
    public void setNpgTargetName(String npgTargetName) {
        npgTargetName__ = npgTargetName;
    }
    /**
     * <p>npgTargetUnit を取得します。
     * @return npgTargetUnit
     */
    public String getNpgTargetUnit() {
        return npgTargetUnit__;
    }
    /**
     * <p>npgTargetUnit をセットします。
     * @param npgTargetUnit npgTargetUnit
     */
    public void setNpgTargetUnit(String npgTargetUnit) {
        npgTargetUnit__ = npgTargetUnit;
    }
    /**
     * <p>npgTarget を取得します。
     * @return npgTarget
     */
    public Long getNpgTarget() {
        return npgTarget__;
    }
    /**
     * <p>npgTarget をセットします。
     * @param npgTarget npgTarget
     */
    public void setNpgTarget(Long npgTarget) {
        npgTarget__ = npgTarget;
    }
    /**
     * <p>npgRecord を取得します。
     * @return npgRecord
     */
    public Long getNpgRecord() {
        return npgRecord__;
    }
    /**
     * <p>npgRecord をセットします。
     * @param npgRecord npgRecord
     */
    public void setNpgRecord(Long npgRecord) {
        npgRecord__ = npgRecord;
    }
    /**
     * <p>npgTargetRatio を取得します。
     * @return npgTargetRatio
     */
    public double getNpgTargetRatio() {
        return npgTargetRatio__;
    }
    /**
     * <p>npgTargetRatio をセットします。
     * @param npgTargetRatio npgTargetRatio
     */
    public void setNpgTargetRatio(double npgTargetRatio) {
        npgTargetRatio__ = npgTargetRatio;
    }
    /**
     * <p>npgTargetKbn を取得します。
     * @return npgTargetKbn
     */
    public int getNpgTargetKbn() {
        return npgTargetKbn__;
    }
    /**
     * <p>npgTargetKbn をセットします。
     * @param npgTargetKbn npgTargetKbn
     */
    public void setNpgTargetKbn(int npgTargetKbn) {
        npgTargetKbn__ = npgTargetKbn;
    }
    /**
     * <p>npgTargetUnRatio を取得します。
     * @return npgTargetUnRatio
     */
    public double getNpgTargetUnRatio() {
        return npgTargetUnRatio__;
    }
    /**
     * <p>npgTargetUnRatio をセットします。
     * @param npgTargetUnRatio npgTargetUnRatio
     */
    public void setNpgTargetUnRatio(double npgTargetUnRatio) {
        npgTargetUnRatio__ = npgTargetUnRatio;
    }
    /**
     * <p>npgTargetRatioStr を取得します。
     * @return npgTargetRatioStr
     */
    public String getNpgTargetRatioStr() {
        return npgTargetRatioStr__;
    }
    /**
     * <p>npgTargetRatioStr をセットします。
     * @param npgTargetRatioStr npgTargetRatioStr
     */
    public void setNpgTargetRatioStr(String npgTargetRatioStr) {
        npgTargetRatioStr__ = npgTargetRatioStr;
    }
}