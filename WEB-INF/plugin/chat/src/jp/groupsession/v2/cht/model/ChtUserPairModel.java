package jp.groupsession.v2.cht.model;

import java.io.Serializable;

/**
 * <p>CHT_USER_PAIR Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtUserPairModel implements Serializable {

    /** CUP_SID mapping */
    private int cupSid__;
    /** CUP_UID_F mapping */
    private int cupUidF__;
    /** CUP_UID_S mapping */
    private int cupUidS__;
    /** CUP_COMP_FLG mapping */
    private int cupCompFlg__;

    /**
     * <p>Default Constructor
     */
    public ChtUserPairModel() {
    }

    /**
     * <p>get CUP_SID value
     * @return CUP_SID value
     */
    public int getCupSid() {
        return cupSid__;
    }

    /**
     * <p>set CUP_SID value
     * @param cupSid CUP_SID value
     */
    public void setCupSid(int cupSid) {
        cupSid__ = cupSid;
    }

    /**
     * <p>get CUP_UID_F value
     * @return CUP_UID_F value
     */
    public int getCupUidF() {
        return cupUidF__;
    }

    /**
     * <p>set CUP_UID_F value
     * @param cupUidF CUP_UID_F value
     */
    public void setCupUidF(int cupUidF) {
        cupUidF__ = cupUidF;
    }

    /**
     * <p>get CUP_UID_S value
     * @return CUP_UID_S value
     */
    public int getCupUidS() {
        return cupUidS__;
    }

    /**
     * <p>set CUP_UID_S value
     * @param cupUidS CUP_UID_S value
     */
    public void setCupUidS(int cupUidS) {
        cupUidS__ = cupUidS;
    }

    /**
     * <p>get CUP_COMP_FLG value
     * @return CUP_COMP_FLG value
     */
    public int getCupCompFlg() {
        return cupCompFlg__;
    }

    /**
     * <p>set CUP_COMP_FLG value
     * @param cupCompFlg CUP_COMP_FLG value
     */
    public void setCupCompFlg(int cupCompFlg) {
        cupCompFlg__ = cupCompFlg;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cupSid__);
        buf.append(",");
        buf.append(cupUidF__);
        buf.append(",");
        buf.append(cupUidS__);
        buf.append(",");
        buf.append(cupCompFlg__);
        return buf.toString();
    }

}
