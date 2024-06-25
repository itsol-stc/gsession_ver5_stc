package jp.groupsession.v2.mem.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>MEMO_DATA_PERIOD Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoDataPeriodModel implements Serializable {

    /** MEM_SID mapping */
    private long memSid__;
    /** MDP_DEL_DATE mapping */
    private UDate mdpDelDate__;

    /**
     * <p>Default Constructor
     */
    public MemoDataPeriodModel() {
    }

    /**
     * <p>get MEM_SID value
     * @return MEM_SID value
     */
    public long getMemSid() {
        return memSid__;
    }

    /**
     * <p>set MEM_SID value
     * @param memSid MEM_SID value
     */
    public void setMemSid(long memSid) {
        memSid__ = memSid;
    }

    /**
     * <p>get MDP_DEL_DATE value
     * @return MDP_DEL_DATE value
     */
    public UDate getMdpDelDate() {
        return mdpDelDate__;
    }

    /**
     * <p>set MDP_DEL_DATE value
     * @param mdpDelDate MDP_DEL_DATE value
     */
    public void setMdpDelDate(UDate mdpDelDate) {
        mdpDelDate__ = mdpDelDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(memSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mdpDelDate__, ""));
        return buf.toString();
    }

}
