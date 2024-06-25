package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>NTP_ANKEN_PERMIT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class NtpAnkenPermitModel implements Serializable {

    /** NAN_SID mapping */
    private int nanSid__;
    /** USR_SID mapping */
    private int usrSid__ = -1;
    /** GRP_SID mapping */
    private int grpSid__ = -1;
    /** NAP_KBN mapping */
    private int napKbn__;

    /**
     * <p>Default Constructor
     */
    public NtpAnkenPermitModel() {
    }

    /**
     * <p>get NAN_SID value
     * @return NAN_SID value
     */
    public int getNanSid() {
        return nanSid__;
    }

    /**
     * <p>set NAN_SID value
     * @param nanSid NAN_SID value
     */
    public void setNanSid(int nanSid) {
        nanSid__ = nanSid;
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
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get NAP_KBN value
     * @return NAP_KBN value
     */
    public int getNapKbn() {
        return napKbn__;
    }

    /**
     * <p>set NAP_KBN value
     * @param napKbn NAP_KBN value
     */
    public void setNapKbn(int napKbn) {
        napKbn__ = napKbn;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(nanSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(napKbn__);
        return buf.toString();
    }

}
