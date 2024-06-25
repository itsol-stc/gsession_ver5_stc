package jp.groupsession.v2.rsv.model;


import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RSV_SIS_MAIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RsvSisMainModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** RSG_SID mapping */
    private int rsgSid__;
    /** RSM_DSP_KBN mapping */
    private int rsmDspKbn__;
    /** RSM_AUID mapping */
    private int rsmAuid__;
    /** RSM_ADATE mapping */
    private UDate rsmAdate__;
    /** RSM_EUID mapping */
    private int rsmEuid__;
    /** RSM_EDATE mapping */
    private UDate rsmEdate__;

    /**
     * <p>Default Constructor
     */
    public RsvSisMainModel() {
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
     * <p>get RSG_SID value
     * @return RSG_SID value
     */
    public int getRsgSid() {
        return rsgSid__;
    }

    /**
     * <p>set RSG_SID value
     * @param rsgSid RSG_SID value
     */
    public void setRsgSid(int rsgSid) {
        rsgSid__ = rsgSid;
    }

    /**
     * <p>get RSM_AUID value
     * @return RSM_AUID value
     */
    public int getRsmAuid() {
        return rsmAuid__;
    }

    /**
     * <p>set RSM_AUID value
     * @param rsmAuid RSM_AUID value
     */
    public void setRsmAuid(int rsmAuid) {
        rsmAuid__ = rsmAuid;
    }

    /**
     * <p>get RSM_ADATE value
     * @return RSM_ADATE value
     */
    public UDate getRsmAdate() {
        return rsmAdate__;
    }

    /**
     * <p>set RSM_ADATE value
     * @param rsmAdate RSM_ADATE value
     */
    public void setRsmAdate(UDate rsmAdate) {
        rsmAdate__ = rsmAdate;
    }

    /**
     * <p>get RSM_EUID value
     * @return RSM_EUID value
     */
    public int getRsmEuid() {
        return rsmEuid__;
    }

    /**
     * <p>set RSM_EUID value
     * @param rsmEuid RSM_EUID value
     */
    public void setRsmEuid(int rsmEuid) {
        rsmEuid__ = rsmEuid;
    }

    /**
     * <p>get RSM_EDATE value
     * @return RSM_EDATE value
     */
    public UDate getRsmEdate() {
        return rsmEdate__;
    }

    /**
     * <p>set RSM_EDATE value
     * @param rsmEdate RSM_EDATE value
     */
    public void setRsmEdate(UDate rsmEdate) {
        rsmEdate__ = rsmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(rsgSid__);
        buf.append(",");
        buf.append(rsmDspKbn__);
        buf.append(",");
        buf.append(rsmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rsmAdate__, ""));
        buf.append(",");
        buf.append(rsmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rsmEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>rsmDspKbn を取得します。
     * @return rsmDspKbn
     */
    public int getRsmDspKbn() {
        return rsmDspKbn__;
    }

    /**
     * <p>rsmDspKbn をセットします。
     * @param rsmDspKbn rsmDspKbn
     */
    public void setRsmDspKbn(int rsmDspKbn) {
        rsmDspKbn__ = rsmDspKbn;
    }

}
