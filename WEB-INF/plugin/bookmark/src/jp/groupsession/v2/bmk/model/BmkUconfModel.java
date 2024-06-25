package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BMK_UCONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkUconfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** BUC_COUNT mapping */
    private int bucCount__;
    /** BUC_MAIN_MY mapping */
    private int bucMainMy__;
    /** BUC_MAIN_NEW mapping */
    private int bucMainNew__;
    /** BUC_NEW_CNT mapping */
    private int bucNewCnt__;
    /** BUC_AUID mapping */
    private int bucAuid__;
    /** BUC_ADATE mapping */
    private UDate bucAdate__;
    /** BUC_EUID mapping */
    private int bucEuid__;
    /** BUC_EDATE mapping */
    private UDate bucEdate__;

    /**
     * <p>Default Constructor
     */
    public BmkUconfModel() {
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
     * <p>get BUC_COUNT value
     * @return BUC_COUNT value
     */
    public int getBucCount() {
        return bucCount__;
    }

    /**
     * <p>set BUC_COUNT value
     * @param bucCount BUC_COUNT value
     */
    public void setBucCount(int bucCount) {
        bucCount__ = bucCount;
    }

    /**
     * <p>get BUC_MAIN_MY value
     * @return BUC_MAIN_MY value
     */
    public int getBucMainMy() {
        return bucMainMy__;
    }

    /**
     * <p>set BUC_MAIN_MY value
     * @param bucMainMy BUC_MAIN_MY value
     */
    public void setBucMainMy(int bucMainMy) {
        bucMainMy__ = bucMainMy;
    }

    /**
     * <p>get BUC_MAIN_NEW value
     * @return BUC_MAIN_NEW value
     */
    public int getBucMainNew() {
        return bucMainNew__;
    }

    /**
     * <p>set BUC_MAIN_NEW value
     * @param bucMainNew BUC_MAIN_NEW value
     */
    public void setBucMainNew(int bucMainNew) {
        bucMainNew__ = bucMainNew;
    }

    /**
     * <p>get BUC_NEW_CNT value
     * @return BUC_NEW_CNT value
     */
    public int getBucNewCnt() {
        return bucNewCnt__;
    }

    /**
     * <p>set BUC_NEW_CNT value
     * @param bucNewCnt BUC_NEW_CNT value
     */
    public void setBucNewCnt(int bucNewCnt) {
        bucNewCnt__ = bucNewCnt;
    }

    /**
     * <p>get BUC_AUID value
     * @return BUC_AUID value
     */
    public int getBucAuid() {
        return bucAuid__;
    }

    /**
     * <p>set BUC_AUID value
     * @param bucAuid BUC_AUID value
     */
    public void setBucAuid(int bucAuid) {
        bucAuid__ = bucAuid;
    }

    /**
     * <p>get BUC_ADATE value
     * @return BUC_ADATE value
     */
    public UDate getBucAdate() {
        return bucAdate__;
    }

    /**
     * <p>set BUC_ADATE value
     * @param bucAdate BUC_ADATE value
     */
    public void setBucAdate(UDate bucAdate) {
        bucAdate__ = bucAdate;
    }

    /**
     * <p>get BUC_EUID value
     * @return BUC_EUID value
     */
    public int getBucEuid() {
        return bucEuid__;
    }

    /**
     * <p>set BUC_EUID value
     * @param bucEuid BUC_EUID value
     */
    public void setBucEuid(int bucEuid) {
        bucEuid__ = bucEuid;
    }

    /**
     * <p>get BUC_EDATE value
     * @return BUC_EDATE value
     */
    public UDate getBucEdate() {
        return bucEdate__;
    }

    /**
     * <p>set BUC_EDATE value
     * @param bucEdate BUC_EDATE value
     */
    public void setBucEdate(UDate bucEdate) {
        bucEdate__ = bucEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(bucCount__);
        buf.append(",");
        buf.append(bucMainMy__);
        buf.append(",");
        buf.append(bucMainNew__);
        buf.append(",");
        buf.append(bucAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bucAdate__, ""));
        buf.append(",");
        buf.append(bucEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bucEdate__, ""));
        return buf.toString();
    }

}
