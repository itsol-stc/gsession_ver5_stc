package jp.groupsession.v2.cht.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CHT_GROUP_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtGroupInfModel implements Serializable {

    /** CGI_SID mapping */
    private int cgiSid__;
    /** CGI_ID mapping */
    private String cgiId__;
    /** CGI_NAME mapping */
    private String cgiName__;
    /** CGI_CONTENT mapping */
    private String cgiContent__;
    /** CGI_COMP_FLG mapping */
    private int cgiCompFlg__;
    /** CGI_DEL_FLG mapping */
    private int cgiDelFlg__;
    /** CHC_SID mapping */
    private int chcSid__;
    /** CGI_AUID mapping */
    private int cgiAuid__;
    /** CGI_ADATE mapping */
    private UDate cgiAdate__;
    /** CGI_EUID mapping */
    private int cgiEuid__;
    /** CGI_EDATE mapping */
    private UDate cgiEdate__;

    /**
     * <p>Default Constructor
     */
    public ChtGroupInfModel() {
    }

    /**
     * <p>get CGI_SID value
     * @return CGI_SID value
     */
    public int getCgiSid() {
        return cgiSid__;
    }

    /**
     * <p>set CGI_SID value
     * @param cgiSid CGI_SID value
     */
    public void setCgiSid(int cgiSid) {
        cgiSid__ = cgiSid;
    }

    /**
     * <p>get CGI_ID value
     * @return CGI_ID value
     */
    public String getCgiId() {
        return cgiId__;
    }

    /**
     * <p>set CGI_ID value
     * @param cgiId CGI_ID value
     */
    public void setCgiId(String cgiId) {
        cgiId__ = cgiId;
    }

    /**
     * <p>get CGI_NAME value
     * @return CGI_NAME value
     */
    public String getCgiName() {
        return cgiName__;
    }

    /**
     * <p>set CGI_NAME value
     * @param cgiName CGI_NAME value
     */
    public void setCgiName(String cgiName) {
        cgiName__ = cgiName;
    }

    /**
     * <p>get CGI_CONTENT value
     * @return CGI_CONTENT value
     */
    public String getCgiContent() {
        return cgiContent__;
    }

    /**
     * <p>set CGI_CONTENT value
     * @param cgiContent CGI_CONTENT value
     */
    public void setCgiContent(String cgiContent) {
        cgiContent__ = cgiContent;
    }

    /**
     * <p>get CGI_COMP_FLG value
     * @return CGI_COMP_FLG value
     */
    public int getCgiCompFlg() {
        return cgiCompFlg__;
    }

    /**
     * <p>set CGI_COMP_FLG value
     * @param cgiCompFlg CGI_COMP_FLG value
     */
    public void setCgiCompFlg(int cgiCompFlg) {
        cgiCompFlg__ = cgiCompFlg;
    }

    /**
     * <p>get CGI_DEL_FLG value
     * @return CGI_DEL_FLG value
     */
    public int getCgiDelFlg() {
        return cgiDelFlg__;
    }

    /**
     * <p>set CGI_DEL_FLG value
     * @param cgiDelFlg CGI_DEL_FLG value
     */
    public void setCgiDelFlg(int cgiDelFlg) {
        cgiDelFlg__ = cgiDelFlg;
    }

    /**
     * <p>get CHC_SID value
     * @return CHC_SID value
     */
    public int getChcSid() {
        return chcSid__;
    }

    /**
     * <p>set CHC_SID value
     * @param chcSid CHC_SID value
     */
    public void setChcSid(int chcSid) {
        chcSid__ = chcSid;
    }

    /**
     * <p>get CGI_AUID value
     * @return CGI_AUID value
     */
    public int getCgiAuid() {
        return cgiAuid__;
    }

    /**
     * <p>set CGI_AUID value
     * @param cgiAuid CGI_AUID value
     */
    public void setCgiAuid(int cgiAuid) {
        cgiAuid__ = cgiAuid;
    }

    /**
     * <p>get CGI_ADATE value
     * @return CGI_ADATE value
     */
    public UDate getCgiAdate() {
        return cgiAdate__;
    }

    /**
     * <p>set CGI_ADATE value
     * @param cgiAdate CGI_ADATE value
     */
    public void setCgiAdate(UDate cgiAdate) {
        cgiAdate__ = cgiAdate;
    }

    /**
     * <p>get CGI_EUID value
     * @return CGI_EUID value
     */
    public int getCgiEuid() {
        return cgiEuid__;
    }

    /**
     * <p>set CGI_EUID value
     * @param cgiEuid CGI_EUID value
     */
    public void setCgiEuid(int cgiEuid) {
        cgiEuid__ = cgiEuid;
    }

    /**
     * <p>get CGI_EDATE value
     * @return CGI_EDATE value
     */
    public UDate getCgiEdate() {
        return cgiEdate__;
    }

    /**
     * <p>set CGI_EDATE value
     * @param cgiEdate CGI_EDATE value
     */
    public void setCgiEdate(UDate cgiEdate) {
        cgiEdate__ = cgiEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cgiSid__);
        buf.append(",");
        buf.append(NullDefault.getString(cgiId__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cgiName__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(cgiContent__, ""));
        buf.append(",");
        buf.append(cgiCompFlg__);
        buf.append(",");
        buf.append(cgiDelFlg__);
        buf.append(",");
        buf.append(chcSid__);
        buf.append(",");
        buf.append(cgiAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cgiAdate__, ""));
        buf.append(",");
        buf.append(cgiEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(cgiEdate__, ""));
        return buf.toString();
    }

}
