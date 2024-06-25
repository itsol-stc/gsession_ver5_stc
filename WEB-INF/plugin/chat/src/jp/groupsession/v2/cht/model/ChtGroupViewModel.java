package jp.groupsession.v2.cht.model;

import java.io.Serializable;

/**
 * <p>CHT_GROUP_VIEW Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupViewModel implements Serializable {

    /** CGI_SID mapping */
    private int cgiSid__;
    /** CGV_UID mapping */
    private int cgvUid__;
    /** CGD_SID mapping */
    private long cgdSid__;
    /** CGV_VIEWCNT mapping */
    private int cgvViewcnt__;

    /**
     * <p>Default Constructor
     */
    public ChtGroupViewModel() {
    }

    /**
     * <p>get CGI_SID value
     * @return CGI_SID value
     */
    public int getCgiSid() {
        return cgiSid__;
    }

    /**
     * <p>set CGI_SID value
     * @param cgiSid CGI_SID value
     */
    public void setCgiSid(int cgiSid) {
        cgiSid__ = cgiSid;
    }

    /**
     * <p>get CGV_UID value
     * @return CGV_UID value
     */
    public int getCgvUid() {
        return cgvUid__;
    }

    /**
     * <p>set CGV_UID value
     * @param cgvUid CGV_UID value
     */
    public void setCgvUid(int cgvUid) {
        cgvUid__ = cgvUid;
    }

    /**
     * <p>get CGD_SID value
     * @return CGD_SID value
     */
    public long getCgdSid() {
        return cgdSid__;
    }

    /**
     * <p>set CGD_SID value
     * @param cgdSid CGD_SID value
     */
    public void setCgdSid(long cgdSid) {
        cgdSid__ = cgdSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cgiSid__);
        buf.append(",");
        buf.append(cgvUid__);
        buf.append(",");
        buf.append(cgdSid__);
        return buf.toString();
    }

    /**
     * <p>cgvViewcnt を取得します。
     * @return cgvViewcnt
     * @see jp.groupsession.v2.cht.model.ChtGroupViewModel#cgvViewcnt__
     */
    public int getCgvViewcnt() {
        return cgvViewcnt__;
    }

    /**
     * <p>cgvViewcnt をセットします。
     * @param cgvViewcnt cgvViewcnt
     * @see jp.groupsession.v2.cht.model.ChtGroupViewModel#cgvViewcnt__
     */
    public void setCgvViewcnt(int cgvViewcnt) {
        cgvViewcnt__ = cgvViewcnt;
    }

}
