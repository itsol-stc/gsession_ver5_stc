package jp.groupsession.v2.rss.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RSS_UCONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RssUconfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** RUC_NEW_CNT mapping */
    private int rucNewCnt__;
    /** RUC_AUID mapping */
    private int rucAuid__;
    /** RUC_ADATE mapping */
    private UDate rucAdate__;
    /** RUC_EUID mapping */
    private int rucEuid__;
    /** RUC_EDATE mapping */
    private UDate rucEdate__;

    /**
     * <p>Default Constructor
     */
    public RssUconfModel() {
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
     * <p>get RUC_NEW_CNT value
     * @return RUC_NEW_CNT value
     */
    public int getRucNewCnt() {
        return rucNewCnt__;
    }

    /**
     * <p>set RUC_NEW_CNT value
     * @param rucNewCnt RUC_NEW_CNT value
     */
    public void setRucNewCnt(int rucNewCnt) {
        rucNewCnt__ = rucNewCnt;
    }

    /**
     * <p>get RUC_AUID value
     * @return RUC_AUID value
     */
    public int getRucAuid() {
        return rucAuid__;
    }

    /**
     * <p>set RUC_AUID value
     * @param rucAuid RUC_AUID value
     */
    public void setRucAuid(int rucAuid) {
        rucAuid__ = rucAuid;
    }

    /**
     * <p>get RUC_ADATE value
     * @return RUC_ADATE value
     */
    public UDate getRucAdate() {
        return rucAdate__;
    }

    /**
     * <p>set RUC_ADATE value
     * @param rucAdate RUC_ADATE value
     */
    public void setRucAdate(UDate rucAdate) {
        rucAdate__ = rucAdate;
    }

    /**
     * <p>get RUC_EUID value
     * @return RUC_EUID value
     */
    public int getRucEuid() {
        return rucEuid__;
    }

    /**
     * <p>set RUC_EUID value
     * @param rucEuid RUC_EUID value
     */
    public void setRucEuid(int rucEuid) {
        rucEuid__ = rucEuid;
    }

    /**
     * <p>get RUC_EDATE value
     * @return RUC_EDATE value
     */
    public UDate getRucEdate() {
        return rucEdate__;
    }

    /**
     * <p>set RUC_EDATE value
     * @param rucEdate RUC_EDATE value
     */
    public void setRucEdate(UDate rucEdate) {
        rucEdate__ = rucEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(rucNewCnt__);
        buf.append(",");
        buf.append(rucAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rucAdate__, ""));
        buf.append(",");
        buf.append(rucEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rucEdate__, ""));
        return buf.toString();
    }

}
