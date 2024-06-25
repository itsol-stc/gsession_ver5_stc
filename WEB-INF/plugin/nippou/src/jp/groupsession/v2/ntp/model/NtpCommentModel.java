package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_COMMENT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpCommentModel implements Serializable {

    /** NPC_SID mapping */
    private int npcSid__;
    /** NIP_SID mapping */
    private int nipSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** NPC_COMMENT mapping */
    private String npcComment__;
    /** NPC_VIEW_KBN mapping */
    private int npcViewKbn__;
    /** NPC_AUID mapping */
    private int npcAuid__;
    /** NPC_ADATE mapping */
    private UDate npcAdate__;
    /** NPC_EUID mapping */
    private int npcEuid__;
    /** NPC_EDATE mapping */
    private UDate npcEdate__;

    /**
     * <p>Default Constructor
     */
    public NtpCommentModel() {
    }

    /**
     * <p>get NPC_SID value
     * @return NPC_SID value
     */
    public int getNpcSid() {
        return npcSid__;
    }

    /**
     * <p>set NPC_SID value
     * @param npcSid NPC_SID value
     */
    public void setNpcSid(int npcSid) {
        npcSid__ = npcSid;
    }

    /**
     * <p>get NIP_SID value
     * @return NIP_SID value
     */
    public int getNipSid() {
        return nipSid__;
    }

    /**
     * <p>set NIP_SID value
     * @param nipSid NIP_SID value
     */
    public void setNipSid(int nipSid) {
        nipSid__ = nipSid;
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get NPC_COMMENT value
     * @return NPC_COMMENT value
     */
    public String getNpcComment() {
        return npcComment__;
    }

    /**
     * <p>set NPC_COMMENT value
     * @param npcComment NPC_COMMENT value
     */
    public void setNpcComment(String npcComment) {
        npcComment__ = npcComment;
    }

    /**
     * <p>get NPC_VIEW_KBN value
     * @return NPC_VIEW_KBN value
     */
    public int getNpcViewKbn() {
        return npcViewKbn__;
    }

    /**
     * <p>set NPC_VIEW_KBN value
     * @param npcViewKbn NPC_VIEW_KBN value
     */
    public void setNpcViewKbn(int npcViewKbn) {
        npcViewKbn__ = npcViewKbn;
    }

    /**
     * <p>get NPC_AUID value
     * @return NPC_AUID value
     */
    public int getNpcAuid() {
        return npcAuid__;
    }

    /**
     * <p>set NPC_AUID value
     * @param npcAuid NPC_AUID value
     */
    public void setNpcAuid(int npcAuid) {
        npcAuid__ = npcAuid;
    }

    /**
     * <p>get NPC_ADATE value
     * @return NPC_ADATE value
     */
    public UDate getNpcAdate() {
        return npcAdate__;
    }

    /**
     * <p>set NPC_ADATE value
     * @param npcAdate NPC_ADATE value
     */
    public void setNpcAdate(UDate npcAdate) {
        npcAdate__ = npcAdate;
    }

    /**
     * <p>get NPC_EUID value
     * @return NPC_EUID value
     */
    public int getNpcEuid() {
        return npcEuid__;
    }

    /**
     * <p>set NPC_EUID value
     * @param npcEuid NPC_EUID value
     */
    public void setNpcEuid(int npcEuid) {
        npcEuid__ = npcEuid;
    }

    /**
     * <p>get NPC_EDATE value
     * @return NPC_EDATE value
     */
    public UDate getNpcEdate() {
        return npcEdate__;
    }

    /**
     * <p>set NPC_EDATE value
     * @param npcEdate NPC_EDATE value
     */
    public void setNpcEdate(UDate npcEdate) {
        npcEdate__ = npcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(npcSid__);
        buf.append(",");
        buf.append(nipSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(npcComment__, ""));
        buf.append(",");
        buf.append(npcViewKbn__);
        buf.append(",");
        buf.append(npcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(npcAdate__, ""));
        buf.append(",");
        buf.append(npcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(npcEdate__, ""));
        return buf.toString();
    }

}
