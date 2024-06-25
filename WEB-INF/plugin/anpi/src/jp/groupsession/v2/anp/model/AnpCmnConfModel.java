package jp.groupsession.v2.anp.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ANP_CMN_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpCmnConfModel implements Serializable {

    /** APC_URL_BASE mapping */
    private String apcUrlBase__;
    /** APC_URL_KBN mapping */
    private int apcUrlKbn__;
    /** APC_ADDRESS mapping */
    private String apcAddress__;
    /** APC_SEND_HOST mapping */
    private String apcSendHost__;
    /** APC_SEND_PORT mapping */
    private int apcSendPort__;
    /** APC_SEND_USER mapping */
    private String apcSendUser__;
    /** APC_SEND_PASS mapping */
    private String apcSendPass__;
    /** APC_SEND_SSL mapping */
    private int apcSendSsl__;
    /** APC_SMTP_AUTH mapping */
    private int apcSmtpAuth__;
    /** APC_AUID mapping */
    private int apcAuid__;
    /** APC_ADATE mapping */
    private UDate apcAdate__;
    /** APC_EUID mapping */
    private int apcEuid__;
    /** APC_EDATE mapping */
    private UDate apcEdate__;
    /** APC_AUTH_TYPE mapping */
    private int apcAuthType__;
    /** COT_SID mapping */
    private int cotSid__;

    /**
     * <p>Default Constructor
     */
    public AnpCmnConfModel() {
    }

    /**
     * <p>get APC_URL_BASE value
     * @return APC_URL_BASE value
     */
    public String getApcUrlBase() {
        return apcUrlBase__;
    }

    /**
     * <p>set APC_URL_BASE value
     * @param apcUrlBase APC_URL_BASE value
     */
    public void setApcUrlBase(String apcUrlBase) {
        apcUrlBase__ = apcUrlBase;
    }

    /**
     * <p>apcUrlKbn を取得します。
     * @return apcUrlKbn
     */
    public int getApcUrlKbn() {
        return apcUrlKbn__;
    }

    /**
     * <p>apcUrlKbn をセットします。
     * @param apcUrlKbn apcUrlKbn
     */
    public void setApcUrlKbn(int apcUrlKbn) {
        apcUrlKbn__ = apcUrlKbn;
    }

    /**
     * <p>get APC_ADDRESS value
     * @return APC_ADDRESS value
     */
    public String getApcAddress() {
        return apcAddress__;
    }

    /**
     * <p>set APC_ADDRESS value
     * @param apcAddress APC_ADDRESS value
     */
    public void setApcAddress(String apcAddress) {
        apcAddress__ = apcAddress;
    }

    /**
     * <p>get APC_SEND_HOST value
     * @return APC_SEND_HOST value
     */
    public String getApcSendHost() {
        return apcSendHost__;
    }

    /**
     * <p>set APC_SEND_HOST value
     * @param apcSendHost APC_SEND_HOST value
     */
    public void setApcSendHost(String apcSendHost) {
        apcSendHost__ = apcSendHost;
    }

    /**
     * <p>get APC_SEND_PORT value
     * @return APC_SEND_PORT value
     */
    public int getApcSendPort() {
        return apcSendPort__;
    }

    /**
     * <p>set APC_SEND_PORT value
     * @param apcSendPort APC_SEND_PORT value
     */
    public void setApcSendPort(int apcSendPort) {
        apcSendPort__ = apcSendPort;
    }

    /**
     * <p>get APC_SEND_USER value
     * @return APC_SEND_USER value
     */
    public String getApcSendUser() {
        return apcSendUser__;
    }

    /**
     * <p>set APC_SEND_USER value
     * @param apcSendUser APC_SEND_USER value
     */
    public void setApcSendUser(String apcSendUser) {
        apcSendUser__ = apcSendUser;
    }

    /**
     * <p>get APC_SEND_PASS value
     * @return APC_SEND_PASS value
     */
    public String getApcSendPass() {
        return apcSendPass__;
    }

    /**
     * <p>set APC_SEND_PASS value
     * @param apcSendPass APC_SEND_PASS value
     */
    public void setApcSendPass(String apcSendPass) {
        apcSendPass__ = apcSendPass;
    }

    /**
     * <p>get APC_SEND_SSL value
     * @return APC_SEND_SSL value
     */
    public int getApcSendSsl() {
        return apcSendSsl__;
    }

    /**
     * <p>set APC_SEND_SSL value
     * @param apcSendSsl APC_SEND_SSL value
     */
    public void setApcSendSsl(int apcSendSsl) {
        apcSendSsl__ = apcSendSsl;
    }

    /**
     * <p>get APC_SMTP_AUTH value
     * @return APC_SMTP_AUTH value
     */
    public int getApcSmtpAuth() {
        return apcSmtpAuth__;
    }

    /**
     * <p>set APC_SMTP_AUTH value
     * @param apcSmtpAuth APC_SMTP_AUTH value
     */
    public void setApcSmtpAuth(int apcSmtpAuth) {
        apcSmtpAuth__ = apcSmtpAuth;
    }

    /**
     * <p>get APC_AUID value
     * @return APC_AUID value
     */
    public int getApcAuid() {
        return apcAuid__;
    }

    /**
     * <p>set APC_AUID value
     * @param apcAuid APC_AUID value
     */
    public void setApcAuid(int apcAuid) {
        apcAuid__ = apcAuid;
    }

    /**
     * <p>get APC_ADATE value
     * @return APC_ADATE value
     */
    public UDate getApcAdate() {
        return apcAdate__;
    }

    /**
     * <p>set APC_ADATE value
     * @param apcAdate APC_ADATE value
     */
    public void setApcAdate(UDate apcAdate) {
        apcAdate__ = apcAdate;
    }

    /**
     * <p>get APC_EUID value
     * @return APC_EUID value
     */
    public int getApcEuid() {
        return apcEuid__;
    }

    /**
     * <p>set APC_EUID value
     * @param apcEuid APC_EUID value
     */
    public void setApcEuid(int apcEuid) {
        apcEuid__ = apcEuid;
    }

    /**
     * <p>get APC_EDATE value
     * @return APC_EDATE value
     */
    public UDate getApcEdate() {
        return apcEdate__;
    }

    /**
     * <p>set APC_EDATE value
     * @param apcEdate APC_EDATE value
     */
    public void setApcEdate(UDate apcEdate) {
        apcEdate__ = apcEdate;
    }
    
    /**
     * <p>get APC_AUTH_TYPE value
     * @return APC_AUTH_TYPE value
     */
    public int getApcAuthType() {
        return apcAuthType__;
    }

    /**
     * <p>set APC_AUTH_TYPE value
     * @param apcAuthType APC_AUTH_TYPE value
     */
    public void setApcAuthType(int apcAuthType) {
        apcAuthType__ = apcAuthType;
    }

    /**
     * <p>get COT_SID value
     * @return COT_SID value
     */
    public int getCotSid() {
        return cotSid__;
    }

    /**
     * <p>set COT_SID value
     * @param cotSid COT_SID value
     */
    public void setCotSid(int cotSid) {
        cotSid__ = cotSid;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(NullDefault.getString(apcUrlBase__, ""));
        buf.append(",");
        buf.append(apcUrlKbn__);
        buf.append(",");
        buf.append(NullDefault.getString(apcAddress__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(apcSendHost__, ""));
        buf.append(",");
        buf.append(apcSendPort__);
        buf.append(",");
        buf.append(NullDefault.getString(apcSendUser__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(apcSendPass__, ""));
        buf.append(",");
        buf.append(apcSendSsl__);
        buf.append(",");
        buf.append(apcSmtpAuth__);
        buf.append(",");
        buf.append(apcAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apcAdate__, ""));
        buf.append(",");
        buf.append(apcEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(apcEdate__, ""));
        buf.append(",");
        buf.append(apcAuthType__);
        buf.append(",");
        buf.append(cotSid__);
        return buf.toString();
    }
}
