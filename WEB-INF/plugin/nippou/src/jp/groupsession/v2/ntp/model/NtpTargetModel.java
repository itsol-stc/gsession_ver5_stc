package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import java.io.Serializable;

/**
 * <p>NTP_Target Data Bindding JavaBean
 */
public class NtpTargetModel implements Serializable {

    /** NTG_SID mapping */
    private int ntgSid__;
    /** NTG_NAME mapping */
    private String ntgName__;
    /** NTG_DETAIL mapping */
    private String ntgDetail__;
    /** NTG_UNIT mapping */
    private String ntgUnit__;
    /** NTG_DEF mapping */
    private long ntgDef__;
    /** NTG_AUID mapping */
    private int ntgAuid__;
    /** NTG_ADATE mapping */
    private UDate ntgAdate__;
    /** NTG_EUID mapping */
    private int ntgEuid__;
    /** NTG_EDATE mapping */
    private UDate ntgEdate__;

    /** ソート順 */
    private int ntgSort__;

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
     * <p>ntgName を取得します。
     * @return ntgName
     */
    public String getNtgName() {
        return ntgName__;
    }
    /**
     * <p>ntgName をセットします。
     * @param ntgName ntgName
     */
    public void setNtgName(String ntgName) {
        ntgName__ = ntgName;
    }
    /**
     * <p>ntgDetail を取得します。
     * @return ntgDetail
     */
    public String getNtgDetail() {
        return ntgDetail__;
    }
    /**
     * <p>ntgDetail をセットします。
     * @param ntgDetail ntgDetail
     */
    public void setNtgDetail(String ntgDetail) {
        ntgDetail__ = ntgDetail;
    }
    /**
     * <p>ntgDef を取得します。
     * @return ntgDef
     */
    public long getNtgDef() {
        return ntgDef__;
    }
    /**
     * <p>ntgDef をセットします。
     * @param ntgDef ntgDef
     */
    public void setNtgDef(long ntgDef) {
        ntgDef__ = ntgDef;
    }
    /**
     * <p>ntgAuid を取得します。
     * @return ntgAuid
     */
    public int getNtgAuid() {
        return ntgAuid__;
    }
    /**
     * <p>ntgAuid をセットします。
     * @param ntgAuid ntgAuid
     */
    public void setNtgAuid(int ntgAuid) {
        ntgAuid__ = ntgAuid;
    }
    /**
     * <p>ntgAdate を取得します。
     * @return ntgAdate
     */
    public UDate getNtgAdate() {
        return ntgAdate__;
    }
    /**
     * <p>ntgAdate をセットします。
     * @param ntgAdate ntgAdate
     */
    public void setNtgAdate(UDate ntgAdate) {
        ntgAdate__ = ntgAdate;
    }
    /**
     * <p>ntgEuid を取得します。
     * @return ntgEuid
     */
    public int getNtgEuid() {
        return ntgEuid__;
    }
    /**
     * <p>ntgEuid をセットします。
     * @param ntgEuid ntgEuid
     */
    public void setNtgEuid(int ntgEuid) {
        ntgEuid__ = ntgEuid;
    }
    /**
     * <p>ntgEdate を取得します。
     * @return ntgEdate
     */
    public UDate getNtgEdate() {
        return ntgEdate__;
    }
    /**
     * <p>ntgEdate をセットします。
     * @param ntgEdate ntgEdate
     */
    public void setNtgEdate(UDate ntgEdate) {
        ntgEdate__ = ntgEdate;
    }
    /**
     * <p>ntgUnit を取得します。
     * @return ntgUnit
     */
    public String getNtgUnit() {
        return ntgUnit__;
    }
    /**
     * <p>ntgUnit をセットします。
     * @param ntgUnit ntgUnit
     */
    public void setNtgUnit(String ntgUnit) {
        ntgUnit__ = ntgUnit;
    }
    /**
     * <p>ntgSort を取得します。
     * @return ntgSort
     */
    public int getNtgSort() {
        return ntgSort__;
    }
    /**
     * <p>ntgSort をセットします。
     * @param ntgSort ntgSort
     */
    public void setNtgSort(int ntgSort) {
        ntgSort__ = ntgSort;
    }

}
