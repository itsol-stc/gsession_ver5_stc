package jp.groupsession.v2.cir.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CIR_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class CirLabelModel implements Serializable {

    /** CLA_SID mapping */
    private int claSid__;
    /** CAC_SID mapping */
    private int cacSid__;
    /** CLA_NAME mapping */
    private String claName__;
    /** CLA_ORDER mapping */
    private int claOrder__;
    /** CLA_AUID mapping */
    private int claAuid__;
    /** CLA_ADATE mapping */
    private UDate claAdate__;
    /** CLA_EUID mapping */
    private int claEuid__;
    /** CLA_EDATE mapping */
    private UDate claEdate__;

    /**
     * <p>Default Constructor
     */
    public CirLabelModel() {
    }

    /**
     * <p>get CLA_SID value
     * @return CLA_SID value
     */
    public int getClaSid() {
        return claSid__;
    }

    /**
     * <p>set CLA_SID value
     * @param claSid CLA_SID value
     */
    public void setClaSid(int claSid) {
        claSid__ = claSid;
    }

    /**
     * <p>get CAC_SID value
     * @return CAC_SID value
     */
    public int getCacSid() {
        return cacSid__;
    }

    /**
     * <p>set CAC_SID value
     * @param cacSid CAC_SID value
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
    }

    /**
     * <p>get CLA_NAME value
     * @return CLA_NAME value
     */
    public String getClaName() {
        return claName__;
    }

    /**
     * <p>set CLA_NAME value
     * @param claName CLA_NAME value
     */
    public void setClaName(String claName) {
        claName__ = claName;
    }

    /**
     * <p>get CLA_ORDER value
     * @return CLA_ORDER value
     */
    public int getClaOrder() {
        return claOrder__;
    }

    /**
     * <p>set CLA_ORDER value
     * @param claOrder CLA_ORDER value
     */
    public void setClaOrder(int claOrder) {
        claOrder__ = claOrder;
    }

    /**
     * <p>get CLA_AUID value
     * @return CLA_AUID value
     */
    public int getClaAuid() {
        return claAuid__;
    }

    /**
     * <p>set CLA_AUID value
     * @param claAuid CLA_AUID value
     */
    public void setClaAuid(int claAuid) {
        claAuid__ = claAuid;
    }

    /**
     * <p>get CLA_ADATE value
     * @return CLA_ADATE value
     */
    public UDate getClaAdate() {
        return claAdate__;
    }

    /**
     * <p>set CLA_ADATE value
     * @param claAdate CLA_ADATE value
     */
    public void setClaAdate(UDate claAdate) {
        claAdate__ = claAdate;
    }

    /**
     * <p>get CLA_EUID value
     * @return CLA_EUID value
     */
    public int getClaEuid() {
        return claEuid__;
    }

    /**
     * <p>set CLA_EUID value
     * @param claEuid CLA_EUID value
     */
    public void setClaEuid(int claEuid) {
        claEuid__ = claEuid;
    }

    /**
     * <p>get CLA_EDATE value
     * @return CLA_EDATE value
     */
    public UDate getClaEdate() {
        return claEdate__;
    }

    /**
     * <p>set CLA_EDATE value
     * @param claEdate CLA_EDATE value
     */
    public void setClaEdate(UDate claEdate) {
        claEdate__ = claEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(claSid__);
        buf.append(",");
        buf.append(cacSid__);
        buf.append(",");
        buf.append(NullDefault.getString(claName__, ""));
        buf.append(",");
        buf.append(claOrder__);
        buf.append(",");
        buf.append(claAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(claAdate__, ""));
        buf.append(",");
        buf.append(claEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(claEdate__, ""));
        return buf.toString();
    }

}
