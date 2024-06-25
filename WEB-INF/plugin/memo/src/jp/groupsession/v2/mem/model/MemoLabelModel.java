package jp.groupsession.v2.mem.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>MEMO_LABEL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoLabelModel implements Serializable {

    /** MML_SID mapping */
    private int mmlSid__;
    /** MML_NAME mapping */
    private String mmlName__;
    /** USR_SID mapping */
    private int usrSid__;
    /** MML_SORT mapping */
    private int mmlSort__;
    /** MML_AUID mapping */
    private int mmlAuid__;
    /** MML_ADATE mapping */
    private UDate mmlAdate__;
    /** MML_EUID mapping */
    private int mmlEuid__;
    /** MML_EDATE mapping */
    private UDate mmlEdate__;

    /**
     * <p>Default Constructor
     */
    public MemoLabelModel() {
    }

    /**
     * <p>get MML_SID value
     * @return MML_SID value
     */
    public int getMmlSid() {
        return mmlSid__;
    }

    /**
     * <p>set MML_SID value
     * @param mmlSid MML_SID value
     */
    public void setMmlSid(int mmlSid) {
        mmlSid__ = mmlSid;
    }

    /**
     * <p>get MML_NAME value
     * @return MML_NAME value
     */
    public String getMmlName() {
        return mmlName__;
    }

    /**
     * <p>set MML_NAME value
     * @param mmlName MML_NAME value
     */
    public void setMmlName(String mmlName) {
        mmlName__ = mmlName;
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
     * <p>get MML_SORT value
     * @return MML_SORT value
     */
    public int getMmlSort() {
        return mmlSort__;
    }

    /**
     * <p>set MML_SORT value
     * @param mmlSort MML_SORT value
     */
    public void setMmlSort(int mmlSort) {
        mmlSort__ = mmlSort;
    }

    /**
     * <p>get MML_AUID value
     * @return MML_AUID value
     */
    public int getMmlAuid() {
        return mmlAuid__;
    }

    /**
     * <p>set MML_AUID value
     * @param mmlAuid MML_AUID value
     */
    public void setMmlAuid(int mmlAuid) {
        mmlAuid__ = mmlAuid;
    }

    /**
     * <p>get MML_ADATE value
     * @return MML_ADATE value
     */
    public UDate getMmlAdate() {
        return mmlAdate__;
    }

    /**
     * <p>set MML_ADATE value
     * @param mmlAdate MML_ADATE value
     */
    public void setMmlAdate(UDate mmlAdate) {
        mmlAdate__ = mmlAdate;
    }

    /**
     * <p>get MML_EUID value
     * @return MML_EUID value
     */
    public int getMmlEuid() {
        return mmlEuid__;
    }

    /**
     * <p>set MML_EUID value
     * @param mmlEuid MML_EUID value
     */
    public void setMmlEuid(int mmlEuid) {
        mmlEuid__ = mmlEuid;
    }

    /**
     * <p>get MML_EDATE value
     * @return MML_EDATE value
     */
    public UDate getMmlEdate() {
        return mmlEdate__;
    }

    /**
     * <p>set MML_EDATE value
     * @param mmlEdate MML_EDATE value
     */
    public void setMmlEdate(UDate mmlEdate) {
        mmlEdate__ = mmlEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(mmlSid__);
        buf.append(",");
        buf.append(NullDefault.getString(mmlName__, ""));
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(mmlSort__);
        buf.append(",");
        buf.append(mmlAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mmlAdate__, ""));
        buf.append(",");
        buf.append(mmlEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mmlEdate__, ""));
        return buf.toString();
    }

}
