package jp.groupsession.v2.api.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>API_TOKEN Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ApiTokenModel implements Serializable {

    /** APT_TOKEN mapping */
    private String aptToken__;
    /** APT_CLIENT mapping */
    private int aptClient__;
    /** APT_CLIENT_ID mapping */
    private String aptClientId__;
    /** USR_SID mapping */
    private int usrSid__;
    /** APT_IP mapping */
    private String aptIp__;
    /** APT_JKBN mapping */
    private int aptJkbn__;
    /** APT_LIMIT_DATE mapping */
    private UDate aptLimitDate__;
    /** APT_AUID mapping */
    private int aptAuid__;
    /** APT_ADATE mapping */
    private UDate aptAdate__;
    /** APT_EUID mapping */
    private int aptEuid__;
    /** APT_EDATE mapping */
    private UDate aptEdate__;

    /**
     * <p>Default Constructor
     */
    public ApiTokenModel() {
    }

    /**
     * <p>get APT_TOKEN value
     * @return APT_TOKEN value
     */
    public String getAptToken() {
        return aptToken__;
    }

    /**
     * <p>set APT_TOKEN value
     * @param aptToken APT_TOKEN value
     */
    public void setAptToken(String aptToken) {
        aptToken__ = aptToken;
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
     * <p>get APT_IP value
     * @return APT_IP value
     */
    public String getAptIp() {
        return aptIp__;
    }

    /**
     * <p>set APT_IP value
     * @param aptIp APT_IP value
     */
    public void setAptIp(String aptIp) {
        aptIp__ = aptIp;
    }

    /**
     * <p>get APT_JKBN value
     * @return APT_JKBN value
     */
    public int getAptJkbn() {
        return aptJkbn__;
    }

    /**
     * <p>set APT_JKBN value
     * @param aptJkbn APT_JKBN value
     */
    public void setAptJkbn(int aptJkbn) {
        aptJkbn__ = aptJkbn;
    }

    /**
     * <p>get APT_LIMIT_DATE value
     * @return APT_LIMIT_DATE value
     */
    public UDate getAptLimitDate() {
        return aptLimitDate__;
    }

    /**
     * <p>set APT_LIMIT_DATE value
     * @param aptLimitDate APT_LIMIT_DATE value
     */
    public void setAptLimitDate(UDate aptLimitDate) {
        aptLimitDate__ = aptLimitDate;
    }

    /**
     * <p>get APT_AUID value
     * @return APT_AUID value
     */
    public int getAptAuid() {
        return aptAuid__;
    }

    /**
     * <p>set APT_AUID value
     * @param aptAuid APT_AUID value
     */
    public void setAptAuid(int aptAuid) {
        aptAuid__ = aptAuid;
    }

    /**
     * <p>get APT_ADATE value
     * @return APT_ADATE value
     */
    public UDate getAptAdate() {
        return aptAdate__;
    }

    /**
     * <p>set APT_ADATE value
     * @param aptAdate APT_ADATE value
     */
    public void setAptAdate(UDate aptAdate) {
        aptAdate__ = aptAdate;
    }

    /**
     * <p>get APT_EUID value
     * @return APT_EUID value
     */
    public int getAptEuid() {
        return aptEuid__;
    }

    /**
     * <p>set APT_EUID value
     * @param aptEuid APT_EUID value
     */
    public void setAptEuid(int aptEuid) {
        aptEuid__ = aptEuid;
    }

    /**
     * <p>get APT_EDATE value
     * @return APT_EDATE value
     */
    public UDate getAptEdate() {
        return aptEdate__;
    }

    /**
     * <p>set APT_EDATE value
     * @param aptEdate APT_EDATE value
     */
    public void setAptEdate(UDate aptEdate) {
        aptEdate__ = aptEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(aptToken__, ""));
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(aptClient__);
        buf.append(",");
        buf.append(NullDefault.getString(aptClientId__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(aptIp__, ""));
        buf.append(",");
        buf.append(aptJkbn__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(aptLimitDate__, ""));
        buf.append(",");
        buf.append(aptAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(aptAdate__, ""));
        buf.append(",");
        buf.append(aptEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(aptEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>aptClientId を取得します。
     * @return aptClientId
     * @see jp.groupsession.v2.api.model.ApiTokenModel#aptClientId__
     */
    public String getAptClientId() {
        return aptClientId__;
    }

    /**
     * <p>aptClientId をセットします。
     * @param aptClientId aptClientId
     * @see jp.groupsession.v2.api.model.ApiTokenModel#aptClientId__
     */
    public void setAptClientId(String aptClientId) {
        aptClientId__ = aptClientId;
    }

    /**
     * <p>aptClient を取得します。
     * @return aptClient
     * @see jp.groupsession.v2.api.model.ApiTokenModel#aptClient__
     */
    public int getAptClient() {
        return aptClient__;
    }

    /**
     * <p>aptClient をセットします。
     * @param aptClient aptClient
     * @see jp.groupsession.v2.api.model.ApiTokenModel#aptClient__
     */
    public void setAptClient(int aptClient) {
        aptClient__ = aptClient;
    }

}
