package jp.groupsession.v2.cht.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CHT_SPACCESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtSpaccessModel implements Serializable {

    /** CHS_SID mapping */
    private int chsSid__;
    /** CHS_NAME mapping */
    private String chsName__;
    /** CHS_BIKO mapping */
    private String chsBiko__;
    /** CHS_AUID mapping */
    private int chsAuid__;
    /** CHS_ADATE mapping */
    private UDate chsAdate__;
    /** CHS_EUID mapping */
    private int chsEuid__;
    /** CHS_EDATE mapping */
    private UDate chsEdate__;

    /**
     * <p>Default Constructor
     */
    public ChtSpaccessModel() {
    }

    /**
     * <p>get CHS_SID value
     * @return CHS_SID value
     */
    public int getChsSid() {
        return chsSid__;
    }

    /**
     * <p>set CHS_SID value
     * @param chsSid CHS_SID value
     */
    public void setChsSid(int chsSid) {
        chsSid__ = chsSid;
    }

    /**
     * <p>get CHS_NAME value
     * @return CHS_NAME value
     */
    public String getChsName() {
        return chsName__;
    }

    /**
     * <p>set CHS_NAME value
     * @param chsName CHS_NAME value
     */
    public void setChsName(String chsName) {
        chsName__ = chsName;
    }

    /**
     * <p>get CHS_BIKO value
     * @return CHS_BIKO value
     */
    public String getChsBiko() {
        return chsBiko__;
    }

    /**
     * <p>set CHS_BIKO value
     * @param chsBiko CHS_BIKO value
     */
    public void setChsBiko(String chsBiko) {
        chsBiko__ = chsBiko;
    }

    /**
     * <p>get CHS_AUID value
     * @return CHS_AUID value
     */
    public int getChsAuid() {
        return chsAuid__;
    }

    /**
     * <p>set CHS_AUID value
     * @param chsAuid CHS_AUID value
     */
    public void setChsAuid(int chsAuid) {
        chsAuid__ = chsAuid;
    }

    /**
     * <p>get CHS_ADATE value
     * @return CHS_ADATE value
     */
    public UDate getChsAdate() {
        return chsAdate__;
    }

    /**
     * <p>set CHS_ADATE value
     * @param chsAdate CHS_ADATE value
     */
    public void setChsAdate(UDate chsAdate) {
        chsAdate__ = chsAdate;
    }

    /**
     * <p>get CHS_EUID value
     * @return CHS_EUID value
     */
    public int getChsEuid() {
        return chsEuid__;
    }

    /**
     * <p>set CHS_EUID value
     * @param chsEuid CHS_EUID value
     */
    public void setChsEuid(int chsEuid) {
        chsEuid__ = chsEuid;
    }

    /**
     * <p>get CHS_EDATE value
     * @return CHS_EDATE value
     */
    public UDate getChsEdate() {
        return chsEdate__;
    }

    /**
     * <p>set CHS_EDATE value
     * @param chsEdate CHS_EDATE value
     */
    public void setChsEdate(UDate chsEdate) {
        chsEdate__ = chsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(chsSid__);
        buf.append(",");
        buf.append(NullDefault.getString(chsName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(chsBiko__, ""));
        buf.append(",");
        buf.append(chsAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(chsAdate__, ""));
        buf.append(",");
        buf.append(chsEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(chsEdate__, ""));
        return buf.toString();
    }

}
