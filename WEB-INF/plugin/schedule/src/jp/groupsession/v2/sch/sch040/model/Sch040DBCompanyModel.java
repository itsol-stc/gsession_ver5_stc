package jp.groupsession.v2.sch.sch040.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 会社情報(アドレス帳)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040DBCompanyModel implements Serializable {

    /** ACO_SID mapping */
    private int acoSid__;
    /** ACO_CODE mapping */
    private String acoCode__;
    /** ACO_NAME mapping */
    private String acoName__;
    /** ACO_NAME_KN mapping */
    private String acoNameKn__;
    /** ACO_SINI mapping */
    private String acoSini__;
    /** ACO_URL mapping */
    private String acoUrl__;
    /** ACO_BIKO mapping */
    private String acoBiko__;
    /** ACO_AUID mapping */
    private int acoAuid__;
    /** ACO_ADATE mapping */
    private UDate acoAdate__;
    /** ACO_EUID mapping */
    private int acoEuid__;
    /** ACO_EDATE mapping */
    private UDate acoEdate__;

    /**
     * <p>Default Constructor
     */
    public Sch040DBCompanyModel() {
    }

    /**
     * <p>get ACO_SID value
     * @return ACO_SID value
     */
    public int getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>set ACO_SID value
     * @param acoSid ACO_SID value
     */
    public void setAcoSid(int acoSid) {
        acoSid__ = acoSid;
    }

    /**
     * <p>get ACO_CODE value
     * @return ACO_CODE value
     */
    public String getAcoCode() {
        return acoCode__;
    }

    /**
     * <p>set ACO_CODE value
     * @param acoCode ACO_CODE value
     */
    public void setAcoCode(String acoCode) {
        acoCode__ = acoCode;
    }

    /**
     * <p>get ACO_NAME value
     * @return ACO_NAME value
     */
    public String getAcoName() {
        return acoName__;
    }

    /**
     * <p>set ACO_NAME value
     * @param acoName ACO_NAME value
     */
    public void setAcoName(String acoName) {
        acoName__ = acoName;
    }

    /**
     * <p>get ACO_NAME_KN value
     * @return ACO_NAME_KN value
     */
    public String getAcoNameKn() {
        return acoNameKn__;
    }

    /**
     * <p>set ACO_NAME_KN value
     * @param acoNameKn ACO_NAME_KN value
     */
    public void setAcoNameKn(String acoNameKn) {
        acoNameKn__ = acoNameKn;
    }

    /**
     * <p>get ACO_URL value
     * @return ACO_URL value
     */
    public String getAcoUrl() {
        return acoUrl__;
    }

    /**
     * <p>set ACO_URL value
     * @param acoUrl ACO_URL value
     */
    public void setAcoUrl(String acoUrl) {
        acoUrl__ = acoUrl;
    }

    /**
     * <p>get ACO_BIKO value
     * @return ACO_BIKO value
     */
    public String getAcoBiko() {
        return acoBiko__;
    }

    /**
     * <p>set ACO_BIKO value
     * @param acoBiko ACO_BIKO value
     */
    public void setAcoBiko(String acoBiko) {
        acoBiko__ = acoBiko;
    }

    /**
     * <p>get ACO_AUID value
     * @return ACO_AUID value
     */
    public int getAcoAuid() {
        return acoAuid__;
    }

    /**
     * <p>set ACO_AUID value
     * @param acoAuid ACO_AUID value
     */
    public void setAcoAuid(int acoAuid) {
        acoAuid__ = acoAuid;
    }

    /**
     * <p>get ACO_ADATE value
     * @return ACO_ADATE value
     */
    public UDate getAcoAdate() {
        return acoAdate__;
    }

    /**
     * <p>set ACO_ADATE value
     * @param acoAdate ACO_ADATE value
     */
    public void setAcoAdate(UDate acoAdate) {
        acoAdate__ = acoAdate;
    }

    /**
     * <p>get ACO_EUID value
     * @return ACO_EUID value
     */
    public int getAcoEuid() {
        return acoEuid__;
    }

    /**
     * <p>set ACO_EUID value
     * @param acoEuid ACO_EUID value
     */
    public void setAcoEuid(int acoEuid) {
        acoEuid__ = acoEuid;
    }

    /**
     * <p>get ACO_EDATE value
     * @return ACO_EDATE value
     */
    public UDate getAcoEdate() {
        return acoEdate__;
    }

    /**
     * <p>set ACO_EDATE value
     * @param acoEdate ACO_EDATE value
     */
    public void setAcoEdate(UDate acoEdate) {
        acoEdate__ = acoEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(acoSid__);
        buf.append(",");
        buf.append(NullDefault.getString(acoCode__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(acoName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(acoNameKn__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(acoUrl__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(acoBiko__, ""));
        buf.append(",");
        buf.append(acoAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(acoAdate__, ""));
        buf.append(",");
        buf.append(acoEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(acoEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>acoSini を取得します。
     * @return acoSini
     */
    public String getAcoSini() {
        return acoSini__;
    }

    /**
     * <p>acoSini をセットします。
     * @param acoSini acoSini
     */
    public void setAcoSini(String acoSini) {
        acoSini__ = acoSini;
    }

}
