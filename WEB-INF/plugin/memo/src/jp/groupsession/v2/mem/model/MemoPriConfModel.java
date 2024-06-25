package jp.groupsession.v2.mem.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>MEMO_PRI_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoPriConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** MPC_DSP_MODE mapping */
    private int mpcDspMode__;
    /** MPC_AUID mapping */
    private int mpcAuid__;
    /** MPC_ADATE mapping */
    private UDate mpcAdate__;
    /** MPC_EUID mapping */
    private int mpcEuid__;
    /** MPC_EDATE mapping */
    private UDate mpcEdate__;

    /**
     * <p>Default Constructor
     */
    public MemoPriConfModel() {
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
     * <p>get MPC_DSP_MODE value
     * @return MPC_DSP_MODE value
     */
    public int getMpcDspMode() {
        return mpcDspMode__;
    }

    /**
     * <p>set MPC_DSP_MODE value
     * @param mpcDspMode MPC_DSP_MODE value
     */
    public void setMpcDspMode(int mpcDspMode) {
        mpcDspMode__ = mpcDspMode;
    }

    /**
     * <p>get MPC_AUID value
     * @return MPC_AUID value
     */
    public int getMpcAuid() {
        return mpcAuid__;
    }

    /**
     * <p>set MPC_AUID value
     * @param mpcAuid MPC_AUID value
     */
    public void setMpcAuid(int mpcAuid) {
        mpcAuid__ = mpcAuid;
    }

    /**
     * <p>get MPC_ADATE value
     * @return MPC_ADATE value
     */
    public UDate getMpcAdate() {
        return mpcAdate__;
    }

    /**
     * <p>set MPC_ADATE value
     * @param mpcAdate MPC_ADATE value
     */
    public void setMpcAdate(UDate mpcAdate) {
        mpcAdate__ = mpcAdate;
    }

    /**
     * <p>get MPC_EUID value
     * @return MPC_EUID value
     */
    public int getMpcEuid() {
        return mpcEuid__;
    }

    /**
     * <p>set MPC_EUID value
     * @param mpcEuid MPC_EUID value
     */
    public void setMpcEuid(int mpcEuid) {
        mpcEuid__ = mpcEuid;
    }

    /**
     * <p>get MPC_EDATE value
     * @return MPC_EDATE value
     */
    public UDate getMpcEdate() {
        return mpcEdate__;
    }

    /**
     * <p>set MPC_EDATE value
     * @param mpcEdate MPC_EDATE value
     */
    public void setMpcEdate(UDate mpcEdate) {
        mpcEdate__ = mpcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(mpcDspMode__);
        buf.append(",");
        buf.append(mpcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mpcAdate__, ""));
        buf.append(",");
        buf.append(mpcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mpcEdate__, ""));
        return buf.toString();
    }

}
