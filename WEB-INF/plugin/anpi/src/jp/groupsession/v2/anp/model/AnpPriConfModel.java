package jp.groupsession.v2.anp.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ANP_PRI_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class AnpPriConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** APP_MAIN_KBN mapping */
    private int appMainKbn__;
    /** APP_LIST_COUNT mapping */
    private int appListCount__;
    /** APP_DSP_GROUP mapping */
    private int appDspGroup__;
    /** APP_DSP_MYGROUP mapping */
    private int appDspMygroup__;
    /** APP_DSP_MYGROUP mapping */
    private int appAllGroupFlg__;
    /** APP_MAILADR mapping */
    private String appMailadr__;
    /** APP_TELNO mapping */
    private String appTelno__;
    /** APP_AUID mapping */
    private int appAuid__;
    /** APP_ADATE mapping */
    private UDate appAdate__;
    /** APP_EUID mapping */
    private int appEuid__;
    /** APP_EDATE mapping */
    private UDate appEdate__;

    /**
     * <p>Default Constructor
     */
    public AnpPriConfModel() {
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
     * <p>get APP_MAIN_KBN value
     * @return APP_MAIN_KBN value
     */
    public int getAppMainKbn() {
        return appMainKbn__;
    }

    /**
     * <p>set APP_MAIN_KBN value
     * @param appMainKbn APP_MAIN_KBN value
     */
    public void setAppMainKbn(int appMainKbn) {
        appMainKbn__ = appMainKbn;
    }

    /**
     * <p>get APP_LIST_COUNT value
     * @return APP_LIST_COUNT value
     */
    public int getAppListCount() {
        return appListCount__;
    }

    /**
     * <p>set APP_LIST_COUNT value
     * @param appListCount APP_LIST_COUNT value
     */
    public void setAppListCount(int appListCount) {
        appListCount__ = appListCount;
    }

    /**
     * <p>get APP_DSP_GROUP value
     * @return APP_DSP_GROUP value
     */
    public int getAppDspGroup() {
        return appDspGroup__;
    }

    /**
     * <p>set APP_DSP_GROUP value
     * @param appDspGroup APP_DSP_GROUP value
     */
    public void setAppDspGroup(int appDspGroup) {
        appDspGroup__ = appDspGroup;
    }

    /**
     * <p>get APP_DSP_MYGROUP value
     * @return APP_DSP_MYGROUP value
     */
    public int getAppDspMygroup() {
        return appDspMygroup__;
    }

    /**
     * <p>set APP_DSP_MYGROUP value
     * @param appDspMygroup APP_DSP_MYGROUP value
     */
    public void setAppDspMygroup(int appDspMygroup) {
        appDspMygroup__ = appDspMygroup;
    }

    /**
     * <p>appAllGroupFlg を取得します。
     * @return appAllGroupFlg
     */
    public int getAppAllGroupFlg() {
        return appAllGroupFlg__;
    }

    /**
     * <p>appAllGroupFlg をセットします。
     * @param appAllGroupFlg appAllGroupFlg
     */
    public void setAppAllGroupFlg(int appAllGroupFlg) {
        appAllGroupFlg__ = appAllGroupFlg;
    }

    /**
     * <p>get APP_MAILADR value
     * @return APP_MAILADR value
     */
    public String getAppMailadr() {
        return appMailadr__;
    }

    /**
     * <p>set APP_MAILADR value
     * @param appMailadr APP_MAILADR value
     */
    public void setAppMailadr(String appMailadr) {
        appMailadr__ = appMailadr;
    }

    /**
     * <p>get APP_TELNO value
     * @return APP_TELNO value
     */
    public String getAppTelno() {
        return appTelno__;
    }

    /**
     * <p>set APP_TELNO value
     * @param appTelno APP_TELNO value
     */
    public void setAppTelno(String appTelno) {
        appTelno__ = appTelno;
    }

    /**
     * <p>get APP_AUID value
     * @return APP_AUID value
     */
    public int getAppAuid() {
        return appAuid__;
    }

    /**
     * <p>set APP_AUID value
     * @param appAuid APP_AUID value
     */
    public void setAppAuid(int appAuid) {
        appAuid__ = appAuid;
    }

    /**
     * <p>get APP_ADATE value
     * @return APP_ADATE value
     */
    public UDate getAppAdate() {
        return appAdate__;
    }

    /**
     * <p>set APP_ADATE value
     * @param appAdate APP_ADATE value
     */
    public void setAppAdate(UDate appAdate) {
        appAdate__ = appAdate;
    }

    /**
     * <p>get APP_EUID value
     * @return APP_EUID value
     */
    public int getAppEuid() {
        return appEuid__;
    }

    /**
     * <p>set APP_EUID value
     * @param appEuid APP_EUID value
     */
    public void setAppEuid(int appEuid) {
        appEuid__ = appEuid;
    }

    /**
     * <p>get APP_EDATE value
     * @return APP_EDATE value
     */
    public UDate getAppEdate() {
        return appEdate__;
    }

    /**
     * <p>set APP_EDATE value
     * @param appEdate APP_EDATE value
     */
    public void setAppEdate(UDate appEdate) {
        appEdate__ = appEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(appMainKbn__);
        buf.append(",");
        buf.append(appListCount__);
        buf.append(",");
        buf.append(appDspGroup__);
        buf.append(",");
        buf.append(appDspMygroup__);
        buf.append(",");
        buf.append(appAllGroupFlg__);
        buf.append(",");
        buf.append(NullDefault.getString(appMailadr__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(appTelno__, ""));
        buf.append(",");
        buf.append(appAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(appAdate__, ""));
        buf.append(",");
        buf.append(appEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(appEdate__, ""));
        return buf.toString();
    }

}
