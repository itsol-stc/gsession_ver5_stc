package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CHT_GROUP_CORRECT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupDataSumModel implements Serializable {

    /** CGI_SID mapping */
    private int cgiSid__;
    /** CGS_CNT mapping */
    private int cgsCnt__;
    /** CGS_LASTSID mapping */
    private long cgsLastsid__;
    /** CGS_LASTDATE mapping */
    private UDate cgsLastdate__;

    /**
     * <p>Default Constructor
     */
    public ChtGroupDataSumModel() {
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
     * <p>get CGS_CNT value
     * @return CGS_CNT value
     */
    public int getCgsCnt() {
        return cgsCnt__;
    }

    /**
     * <p>set CGS_CNT value
     * @param cgcCnt CGS_CNT value
     */
    public void setCgsCnt(int cgcCnt) {
        cgsCnt__ = cgcCnt;
    }

    /**
     * <p>get CGS_LASTSID value
     * @return CGS_LASTSID value
     */
    public long getCgsLastsid() {
        return cgsLastsid__;
    }

    /**
     * <p>set CGS_LASTSID value
     * @param cgcLastsid CGS_LASTSID value
     */
    public void setCgsLastsid(long cgcLastsid) {
        cgsLastsid__ = cgcLastsid;
    }

    /**
     * <p>get CGS_LASTDATE value
     * @return CGS_LASTDATE value
     */
    public UDate getCgsLastdate() {
        return cgsLastdate__;
    }

    /**
     * <p>set CGS_LASTDATE value
     * @param cgcLastdate CGS_LASTDATE value
     */
    public void setCgsLastdate(UDate cgcLastdate) {
        cgsLastdate__ = cgcLastdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cgiSid__);
        buf.append(",");
        buf.append(cgsCnt__);
        buf.append(",");
        buf.append(cgsLastsid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cgsLastdate__, ""));
        return buf.toString();
    }

}
