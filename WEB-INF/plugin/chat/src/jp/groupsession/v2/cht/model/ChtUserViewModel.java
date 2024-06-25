package jp.groupsession.v2.cht.model;

import java.io.Serializable;

/**
 * <p>CHT_USER_VIEW Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtUserViewModel implements Serializable {

    /** CUP_SID mapping */
    private int cupSid__;
    /** CUV_UID mapping */
    private int cuvUid__;
    /** CUD_SID mapping */
    private long cudSid__;
    /** CUV_VIEWCNT mapping */
    private int cuvViewcnt__;

    /**
     * <p>Default Constructor
     */
    public ChtUserViewModel() {
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
     * <p>get CUV_UID value
     * @return CUV_UID value
     */
    public int getCuvUid() {
        return cuvUid__;
    }

    /**
     * <p>set CUV_UID value
     * @param cuvUid CUV_UID value
     */
    public void setCuvUid(int cuvUid) {
        cuvUid__ = cuvUid;
    }

    /**
     * <p>get CUD_SID value
     * @return CUD_SID value
     */
    public long getCudSid() {
        return cudSid__;
    }

    /**
     * <p>set CUD_SID value
     * @param cudSid CUD_SID value
     */
    public void setCudSid(long cudSid) {
        cudSid__ = cudSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cupSid__);
        buf.append(",");
        buf.append(cuvUid__);
        buf.append(",");
        buf.append(cudSid__);
        return buf.toString();
    }

    /**
     * <p>cuvViewcnt を取得します。
     * @return cuvViewcnt
     * @see jp.groupsession.v2.cht.model.ChtUserViewModel#cuvViewcnt__
     */
    public int getCuvViewcnt() {
        return cuvViewcnt__;
    }

    /**
     * <p>cuvViewcnt をセットします。
     * @param cuvViewcnt cuvViewcnt
     * @see jp.groupsession.v2.cht.model.ChtUserViewModel#cuvViewcnt__
     */
    public void setCuvViewcnt(int cuvViewcnt) {
        cuvViewcnt__ = cuvViewcnt;
    }

}
