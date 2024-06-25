package jp.groupsession.v2.sch.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>SCH_MYVIEWLIST Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class SchMyviewlistModel implements Serializable {

    /** SMY_SID mapping */
    private int smySid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** SMY_NAME mapping */
    private String smyName__;
    /** SMY_BIKO mapping */
    private String smyBiko__;
    /** SMY_SORT mapping */
    private int smySort__;
    /** MGP_AUID mapping */
    private int mgpAuid__;
    /** MGP_ADATE mapping */
    private UDate mgpAdate__;
    /** MGP_EUID mapping */
    private int mgpEuid__;
    /** MGP_EDATE mapping */
    private UDate mgpEdate__;

    /**
     * <p>Default Constructor
     */
    public SchMyviewlistModel() {
    }

    /**
     * <p>get SMY_SID value
     * @return SMY_SID value
     */
    public int getSmySid() {
        return smySid__;
    }

    /**
     * <p>set SMY_SID value
     * @param smySid SMY_SID value
     */
    public void setSmySid(int smySid) {
        smySid__ = smySid;
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
     * <p>get SMY_NAME value
     * @return SMY_NAME value
     */
    public String getSmyName() {
        return smyName__;
    }

    /**
     * <p>set SMY_NAME value
     * @param smyName SMY_NAME value
     */
    public void setSmyName(String smyName) {
        smyName__ = smyName;
    }

    /**
     * <p>get SMY_BIKO value
     * @return SMY_BIKO value
     */
    public String getSmyBiko() {
        return smyBiko__;
    }

    /**
     * <p>set SMY_BIKO value
     * @param smyBiko SMY_BIKO value
     */
    public void setSmyBiko(String smyBiko) {
        smyBiko__ = smyBiko;
    }

    /**
     * <p>get SMY_SORT value
     * @return SMY_SORT value
     */
    public int getSmySort() {
        return smySort__;
    }

    /**
     * <p>set SMY_SORT value
     * @param smySort SMY_SORT value
     */
    public void setSmySort(int smySort) {
        smySort__ = smySort;
    }

    /**
     * <p>get MGP_AUID value
     * @return MGP_AUID value
     */
    public int getMgpAuid() {
        return mgpAuid__;
    }

    /**
     * <p>set MGP_AUID value
     * @param mgpAuid MGP_AUID value
     */
    public void setMgpAuid(int mgpAuid) {
        mgpAuid__ = mgpAuid;
    }

    /**
     * <p>get MGP_ADATE value
     * @return MGP_ADATE value
     */
    public UDate getMgpAdate() {
        return mgpAdate__;
    }

    /**
     * <p>set MGP_ADATE value
     * @param mgpAdate MGP_ADATE value
     */
    public void setMgpAdate(UDate mgpAdate) {
        mgpAdate__ = mgpAdate;
    }

    /**
     * <p>get MGP_EUID value
     * @return MGP_EUID value
     */
    public int getMgpEuid() {
        return mgpEuid__;
    }

    /**
     * <p>set MGP_EUID value
     * @param mgpEuid MGP_EUID value
     */
    public void setMgpEuid(int mgpEuid) {
        mgpEuid__ = mgpEuid;
    }

    /**
     * <p>get MGP_EDATE value
     * @return MGP_EDATE value
     */
    public UDate getMgpEdate() {
        return mgpEdate__;
    }

    /**
     * <p>set MGP_EDATE value
     * @param mgpEdate MGP_EDATE value
     */
    public void setMgpEdate(UDate mgpEdate) {
        mgpEdate__ = mgpEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(smySid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(smyName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(smyBiko__, ""));
        buf.append(",");
        buf.append(smySort__);
        buf.append(",");
        buf.append(mgpAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mgpAdate__, ""));
        buf.append(",");
        buf.append(mgpEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(mgpEdate__, ""));
        return buf.toString();
    }

}
