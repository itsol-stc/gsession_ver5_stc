package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_TODOCOMMENT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodocommentModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** PTD_SID mapping */
    private int ptdSid__;
    /** PCM_SID mapping */
    private int pcmSid__;
    /** PCM_COMMENT mapping */
    private String pcmComment__;
    /** PCM_AUID mapping */
    private int pcmAuid__;
    /** PCM_ADATE mapping */
    private UDate pcmAdate__;
    /** PCM_EUID mapping */
    private int pcmEuid__;
    /** PCM_EDATE mapping */
    private UDate pcmEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjTodocommentModel() {
    }

    /**
     * <p>get PRJ_SID value
     * @return PRJ_SID value
     */
    public int getPrjSid() {
        return prjSid__;
    }

    /**
     * <p>set PRJ_SID value
     * @param prjSid PRJ_SID value
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
    }

    /**
     * <p>get PTD_SID value
     * @return PTD_SID value
     */
    public int getPtdSid() {
        return ptdSid__;
    }

    /**
     * <p>set PTD_SID value
     * @param ptdSid PTD_SID value
     */
    public void setPtdSid(int ptdSid) {
        ptdSid__ = ptdSid;
    }

    /**
     * <p>get PCM_SID value
     * @return PCM_SID value
     */
    public int getPcmSid() {
        return pcmSid__;
    }

    /**
     * <p>set PCM_SID value
     * @param pcmSid PCM_SID value
     */
    public void setPcmSid(int pcmSid) {
        pcmSid__ = pcmSid;
    }

    /**
     * <p>get PCM_COMMENT value
     * @return PCM_COMMENT value
     */
    public String getPcmComment() {
        return pcmComment__;
    }

    /**
     * <p>set PCM_COMMENT value
     * @param pcmComment PCM_COMMENT value
     */
    public void setPcmComment(String pcmComment) {
        pcmComment__ = pcmComment;
    }

    /**
     * <p>get PCM_AUID value
     * @return PCM_AUID value
     */
    public int getPcmAuid() {
        return pcmAuid__;
    }

    /**
     * <p>set PCM_AUID value
     * @param pcmAuid PCM_AUID value
     */
    public void setPcmAuid(int pcmAuid) {
        pcmAuid__ = pcmAuid;
    }

    /**
     * <p>get PCM_ADATE value
     * @return PCM_ADATE value
     */
    public UDate getPcmAdate() {
        return pcmAdate__;
    }

    /**
     * <p>set PCM_ADATE value
     * @param pcmAdate PCM_ADATE value
     */
    public void setPcmAdate(UDate pcmAdate) {
        pcmAdate__ = pcmAdate;
    }

    /**
     * <p>get PCM_EUID value
     * @return PCM_EUID value
     */
    public int getPcmEuid() {
        return pcmEuid__;
    }

    /**
     * <p>set PCM_EUID value
     * @param pcmEuid PCM_EUID value
     */
    public void setPcmEuid(int pcmEuid) {
        pcmEuid__ = pcmEuid;
    }

    /**
     * <p>get PCM_EDATE value
     * @return PCM_EDATE value
     */
    public UDate getPcmEdate() {
        return pcmEdate__;
    }

    /**
     * <p>set PCM_EDATE value
     * @param pcmEdate PCM_EDATE value
     */
    public void setPcmEdate(UDate pcmEdate) {
        pcmEdate__ = pcmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(ptdSid__);
        buf.append(",");
        buf.append(pcmSid__);
        buf.append(",");
        buf.append(NullDefault.getString(pcmComment__, ""));
        buf.append(",");
        buf.append(pcmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(pcmAdate__, ""));
        buf.append(",");
        buf.append(pcmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(pcmEdate__, ""));
        return buf.toString();
    }

}
