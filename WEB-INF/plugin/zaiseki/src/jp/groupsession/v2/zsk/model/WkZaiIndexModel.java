package jp.groupsession.v2.zsk.model;

import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>WK_ZAI_INDEX Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class WkZaiIndexModel implements Serializable {

    /** WZI_SESSION_SID mapping */
    private String wziSessionSid__;
    /** WZI_KEY mapping */
    private String wziKey__;
    /** WZI_SID mapping */
    private int wziSid__;
    /** WZI_LINKKBN mapping */
    private int wziLinkkbn__;
    /** WZI_LINKSID mapping */
    private int wziLinksid__;
    /** WZI_NAME mapping */
    private String wziName__;
    /** WZI_BGCOLOR mapping */
    private int wziBgcolor__;
    /** WZI_MSG mapping */
    private String wziMsg__;
    /** WZI_XINDEX mapping */
    private int wziXindex__;
    /** WZI_YINDEX mapping */
    private int wziYindex__;
    /** WZI_OTHER_VALUE mapping */
    private String wziOtherValue__;

    /**
     * <p>Default Constructor
     */
    public WkZaiIndexModel() {
    }

    /**
     * <p>get WZI_SESSION_SID value
     * @return WZI_SESSION_SID value
     */
    public String getWziSessionSid() {
        return wziSessionSid__;
    }

    /**
     * <p>set WZI_SESSION_SID value
     * @param wziSessionSid WZI_SESSION_SID value
     */
    public void setWziSessionSid(String wziSessionSid) {
        wziSessionSid__ = wziSessionSid;
    }

    /**
     * <p>get WZI_KEY value
     * @return WZI_KEY value
     */
    public String getWziKey() {
        return wziKey__;
    }

    /**
     * <p>set WZI_KEY value
     * @param wziKey WZI_KEY value
     */
    public void setWziKey(String wziKey) {
        wziKey__ = wziKey;
    }

    /**
     * <p>get WZI_SID value
     * @return WZI_SID value
     */
    public int getWziSid() {
        return wziSid__;
    }

    /**
     * <p>set WZI_SID value
     * @param wziSid WZI_SID value
     */
    public void setWziSid(int wziSid) {
        wziSid__ = wziSid;
    }

    /**
     * <p>get WZI_LINKKBN value
     * @return WZI_LINKKBN value
     */
    public int getWziLinkkbn() {
        return wziLinkkbn__;
    }

    /**
     * <p>set WZI_LINKKBN value
     * @param wziLinkkbn WZI_LINKKBN value
     */
    public void setWziLinkkbn(int wziLinkkbn) {
        wziLinkkbn__ = wziLinkkbn;
    }

    /**
     * <p>get WZI_LINKSID value
     * @return WZI_LINKSID value
     */
    public int getWziLinksid() {
        return wziLinksid__;
    }

    /**
     * <p>set WZI_LINKSID value
     * @param wziLinksid WZI_LINKSID value
     */
    public void setWziLinksid(int wziLinksid) {
        wziLinksid__ = wziLinksid;
    }

    /**
     * <p>get WZI_NAME value
     * @return WZI_NAME value
     */
    public String getWziName() {
        return wziName__;
    }

    /**
     * <p>set WZI_NAME value
     * @param wziName WZI_NAME value
     */
    public void setWziName(String wziName) {
        wziName__ = wziName;
    }

    /**
     * <p>get WZI_BGCOLOR value
     * @return WZI_BGCOLOR value
     */
    public int getWziBgcolor() {
        return wziBgcolor__;
    }

    /**
     * <p>set WZI_BGCOLOR value
     * @param wziBgcolor WZI_BGCOLOR value
     */
    public void setWziBgcolor(int wziBgcolor) {
        wziBgcolor__ = wziBgcolor;
    }

    /**
     * <p>get WZI_MSG value
     * @return WZI_MSG value
     */
    public String getWziMsg() {
        return wziMsg__;
    }

    /**
     * <p>set WZI_MSG value
     * @param wziMsg WZI_MSG value
     */
    public void setWziMsg(String wziMsg) {
        wziMsg__ = wziMsg;
    }

    /**
     * <p>get WZI_XINDEX value
     * @return WZI_XINDEX value
     */
    public int getWziXindex() {
        return wziXindex__;
    }

    /**
     * <p>set WZI_XINDEX value
     * @param wziXindex WZI_XINDEX value
     */
    public void setWziXindex(int wziXindex) {
        wziXindex__ = wziXindex;
    }

    /**
     * <p>get WZI_YINDEX value
     * @return WZI_YINDEX value
     */
    public int getWziYindex() {
        return wziYindex__;
    }

    /**
     * <p>set WZI_YINDEX value
     * @param wziYindex WZI_YINDEX value
     */
    public void setWziYindex(int wziYindex) {
        wziYindex__ = wziYindex;
    }

    /**
     * <p>get WZI_OTHER_VALUE value
     * @return WZI_OTHER_VALUE value
     */
    public String getWziOtherValue() {
        return wziOtherValue__;
    }

    /**
     * <p>set WZI_OTHER_VALUE value
     * @param wziOtherValue WZI_OTHER_VALUE value
     */
    public void setWziOtherValue(String wziOtherValue) {
        wziOtherValue__ = wziOtherValue;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(NullDefault.getString(wziSessionSid__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(wziKey__, ""));
        buf.append(",");
        buf.append(wziSid__);
        buf.append(",");
        buf.append(wziLinkkbn__);
        buf.append(",");
        buf.append(wziLinksid__);
        buf.append(",");
        buf.append(NullDefault.getString(wziName__, ""));
        buf.append(",");
        buf.append(wziBgcolor__);
        buf.append(",");
        buf.append(NullDefault.getString(wziMsg__, ""));
        buf.append(",");
        buf.append(wziXindex__);
        buf.append(",");
        buf.append(wziYindex__);
        buf.append(",");
        buf.append(NullDefault.getString(wziOtherValue__, ""));
        return buf.toString();
    }

}
