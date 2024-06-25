package jp.groupsession.v2.cht.model;

import java.io.Serializable;

/**
 * <p>CHT_SPACCESS_TARGET Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtSpaccessTargetModel implements Serializable {

    /** CHS_SID mapping */
    private int chsSid__;
    /** CST_TYPE mapping */
    private int cstType__;
    /** CST_PSID mapping */
    private int cstPsid__;

    /**
     * <p>Default Constructor
     */
    public ChtSpaccessTargetModel() {
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
     * <p>get CST_TYPE value
     * @return CST_TYPE value
     */
    public int getCstType() {
        return cstType__;
    }

    /**
     * <p>set CST_TYPE value
     * @param cstType CST_TYPE value
     */
    public void setCstType(int cstType) {
        cstType__ = cstType;
    }

    /**
     * <p>get CST_PSID value
     * @return CST_PSID value
     */
    public int getCstPsid() {
        return cstPsid__;
    }

    /**
     * <p>set CST_PSID value
     * @param cstPsid CST_PSID value
     */
    public void setCstPsid(int cstPsid) {
        cstPsid__ = cstPsid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(chsSid__);
        buf.append(",");
        buf.append(cstType__);
        buf.append(",");
        buf.append(cstPsid__);
        return buf.toString();
    }

}
