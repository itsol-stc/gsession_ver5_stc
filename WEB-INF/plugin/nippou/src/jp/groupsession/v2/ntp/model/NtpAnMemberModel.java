package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;
import java.io.Serializable;

/**
 * <p>NTP_AN_SHOHIN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class NtpAnMemberModel implements Serializable {

    /** NAN_SID mapping */
    private int nanSid__;
    /** NHN_SID mapping */
    private int usrSid__;
    /** NAS_AUID mapping */
    private int namAuid__;
    /** NAS_ADATE mapping */
    private UDate namAdate__;
    /** NAS_EUID mapping */
    private int namEuid__;
    /** NAS_EDATE mapping */
    private UDate namEdate__;
    /**
     * <p>nanSid を取得します。
     * @return nanSid
     */
    public int getNanSid() {
        return nanSid__;
    }
    /**
     * <p>nanSid をセットします。
     * @param nanSid nanSid
     */
    public void setNanSid(int nanSid) {
        nanSid__ = nanSid;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>namAuid を取得します。
     * @return namAuid
     */
    public int getNamAuid() {
        return namAuid__;
    }
    /**
     * <p>namAuid をセットします。
     * @param namAuid namAuid
     */
    public void setNamAuid(int namAuid) {
        namAuid__ = namAuid;
    }
    /**
     * <p>namAdate を取得します。
     * @return namAdate
     */
    public UDate getNamAdate() {
        return namAdate__;
    }
    /**
     * <p>namAdate をセットします。
     * @param namAdate namAdate
     */
    public void setNamAdate(UDate namAdate) {
        namAdate__ = namAdate;
    }
    /**
     * <p>namEuid を取得します。
     * @return namEuid
     */
    public int getNamEuid() {
        return namEuid__;
    }
    /**
     * <p>namEuid をセットします。
     * @param namEuid namEuid
     */
    public void setNamEuid(int namEuid) {
        namEuid__ = namEuid;
    }
    /**
     * <p>namEdate を取得します。
     * @return namEdate
     */
    public UDate getNamEdate() {
        return namEdate__;
    }
    /**
     * <p>namEdate をセットします。
     * @param namEdate namEdate
     */
    public void setNamEdate(UDate namEdate) {
        namEdate__ = namEdate;
    }

}
