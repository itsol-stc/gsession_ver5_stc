package jp.groupsession.v2.mem.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>MEMO_DATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoDataModel implements Serializable {

    /** MEM_SID mapping */
    private long memSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** MMD_CONTENT mapping */
    private String mmdContent__;
    /** MMD_DEL_CONF mapping */
    private int mmdDelConf__;
    /** MMD_AUID mapping */
    private int mmdAuid__;
    /** MMD_ADATE mapping */
    private UDate mmdAdate__;
    /** MMD_EUID mapping */
    private int mmdEuid__;
    /** MMD_EDATE mapping */
    private UDate mmdEdate__;

    /**
     * <p>Default Constructor
     */
    public MemoDataModel() {
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
     * <p>get MMD_CONTENT value
     * @return MMD_CONTENT value
     */
    public String getMmdContent() {
        return mmdContent__;
    }

    /**
     * <p>set MMD_CONTENT value
     * @param mmdContent MMD_CONTENT value
     */
    public void setMmdContent(String mmdContent) {
        mmdContent__ = mmdContent;
    }

    /**
     * <p>get MMD_DEL_CONF value
     * @return MMD_DEL_CONF value
     */
    public int getMmdDelConf() {
        return mmdDelConf__;
    }

    /**
     * <p>set MMD_DEL_CONF value
     * @param mmdDelConf MMD_DEL_CONF value
     */
    public void setMmdDelConf(int mmdDelConf) {
        mmdDelConf__ = mmdDelConf;
    }

    /**
     * <p>get MMD_AUID value
     * @return MMD_AUID value
     */
    public int getMmdAuid() {
        return mmdAuid__;
    }

    /**
     * <p>set MMD_AUID value
     * @param mmdAuid MMD_AUID value
     */
    public void setMmdAuid(int mmdAuid) {
        mmdAuid__ = mmdAuid;
    }

    /**
     * <p>get MMD_ADATE value
     * @return MMD_ADATE value
     */
    public UDate getMmdAdate() {
        return mmdAdate__;
    }

    /**
     * <p>set MMD_ADATE value
     * @param mmdAdate MMD_ADATE value
     */
    public void setMmdAdate(UDate mmdAdate) {
        mmdAdate__ = mmdAdate;
    }

    /**
     * <p>get MMD_EUID value
     * @return MMD_EUID value
     */
    public int getMmdEuid() {
        return mmdEuid__;
    }

    /**
     * <p>set MMD_EUID value
     * @param mmdEuid MMD_EUID value
     */
    public void setMmdEuid(int mmdEuid) {
        mmdEuid__ = mmdEuid;
    }

    /**
     * <p>get MMD_EDATE value
     * @return MMD_EDATE value
     */
    public UDate getMmdEdate() {
        return mmdEdate__;
    }

    /**
     * <p>set MMD_EDATE value
     * @param mmdEdate MMD_EDATE value
     */
    public void setMmdEdate(UDate mmdEdate) {
        mmdEdate__ = mmdEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(memSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(mmdContent__, ""));
        buf.append(",");
        buf.append(mmdDelConf__);
        buf.append(",");
        buf.append(mmdAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mmdAdate__, ""));
        buf.append(",");
        buf.append(mmdEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mmdEdate__, ""));
        return buf.toString();
    }

}
