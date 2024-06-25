package jp.groupsession.v2.zsk.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ZAI_INDEX Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiIndexModel implements Serializable {

    /** ZIF_SID mapping */
    private int zifSid__;
    /** ZIN_LINKKBN mapping */
    private int zinLinkkbn__;
    /** ZIN_LINKSID mapping */
    private int zinLinksid__;
    /** ZIN_NAME mapping */
    private String zinName__;
    /** ZIN_BGCOLOR mapping */
    private int zinBgcolor__;
    /** ZIN_MSG mapping */
    private String zinMsg__;
    /** ZIN_XINDEX mapping */
    private int zinXindex__;
    /** ZIN_YINDEX mapping */
    private int zinYindex__;
    /** ZIN_OTHER_VALUE mapping */
    private String zinOtherValue__;
    /** ZIN_AUID mapping */
    private int zinAuid__;
    /** ZIN_ADATE mapping */
    private UDate zinAdate__;
    /** ZIN_EUID mapping */
    private int zinEuid__;
    /** ZIN_EDATE mapping */
    private UDate zinEdate__;

    /**
     * <p>Default Constructor
     */
    public ZaiIndexModel() {
    }

    /**
     * <p>get ZIF_SID value
     * @return ZIF_SID value
     */
    public int getZifSid() {
        return zifSid__;
    }

    /**
     * <p>set ZIF_SID value
     * @param zifSid ZIF_SID value
     */
    public void setZifSid(int zifSid) {
        zifSid__ = zifSid;
    }

    /**
     * <p>get ZIN_LINKKBN value
     * @return ZIN_LINKKBN value
     */
    public int getZinLinkkbn() {
        return zinLinkkbn__;
    }

    /**
     * <p>set ZIN_LINKKBN value
     * @param zinLinkkbn ZIN_LINKKBN value
     */
    public void setZinLinkkbn(int zinLinkkbn) {
        zinLinkkbn__ = zinLinkkbn;
    }

    /**
     * <p>get ZIN_LINKSID value
     * @return ZIN_LINKSID value
     */
    public int getZinLinksid() {
        return zinLinksid__;
    }

    /**
     * <p>set ZIN_LINKSID value
     * @param zinLinksid ZIN_LINKSID value
     */
    public void setZinLinksid(int zinLinksid) {
        zinLinksid__ = zinLinksid;
    }

    /**
     * <p>get ZIN_NAME value
     * @return ZIN_NAME value
     */
    public String getZinName() {
        return zinName__;
    }

    /**
     * <p>set ZIN_NAME value
     * @param zinName ZIN_NAME value
     */
    public void setZinName(String zinName) {
        zinName__ = zinName;
    }

    /**
     * <p>get ZIN_BGCOLOR value
     * @return ZIN_BGCOLOR value
     */
    public int getZinBgcolor() {
        return zinBgcolor__;
    }

    /**
     * <p>set ZIN_BGCOLOR value
     * @param zinBgcolor ZIN_BGCOLOR value
     */
    public void setZinBgcolor(int zinBgcolor) {
        zinBgcolor__ = zinBgcolor;
    }

    /**
     * <p>get ZIN_MSG value
     * @return ZIN_MSG value
     */
    public String getZinMsg() {
        return zinMsg__;
    }

    /**
     * <p>set ZIN_MSG value
     * @param zinMsg ZIN_MSG value
     */
    public void setZinMsg(String zinMsg) {
        zinMsg__ = zinMsg;
    }

    /**
     * <p>get ZIN_XINDEX value
     * @return ZIN_XINDEX value
     */
    public int getZinXindex() {
        return zinXindex__;
    }

    /**
     * <p>set ZIN_XINDEX value
     * @param zinXindex ZIN_XINDEX value
     */
    public void setZinXindex(int zinXindex) {
        zinXindex__ = zinXindex;
    }

    /**
     * <p>get ZIN_YINDEX value
     * @return ZIN_YINDEX value
     */
    public int getZinYindex() {
        return zinYindex__;
    }

    /**
     * <p>set ZIN_YINDEX value
     * @param zinYindex ZIN_YINDEX value
     */
    public void setZinYindex(int zinYindex) {
        zinYindex__ = zinYindex;
    }

    /**
     * <p>get ZIN_OTHER_VALUE value
     * @return ZIN_OTHER_VALUE value
     */
    public String getZinOtherValue() {
        return zinOtherValue__;
    }

    /**
     * <p>set ZIN_OTHER_VALUE value
     * @param zinOtherValue ZIN_OTHER_VALUE value
     */
    public void setZinOtherValue(String zinOtherValue) {
        zinOtherValue__ = zinOtherValue;
    }

    /**
     * <p>get ZIN_AUID value
     * @return ZIN_AUID value
     */
    public int getZinAuid() {
        return zinAuid__;
    }

    /**
     * <p>set ZIN_AUID value
     * @param zinAuid ZIN_AUID value
     */
    public void setZinAuid(int zinAuid) {
        zinAuid__ = zinAuid;
    }

    /**
     * <p>get ZIN_ADATE value
     * @return ZIN_ADATE value
     */
    public UDate getZinAdate() {
        return zinAdate__;
    }

    /**
     * <p>set ZIN_ADATE value
     * @param zinAdate ZIN_ADATE value
     */
    public void setZinAdate(UDate zinAdate) {
        zinAdate__ = zinAdate;
    }

    /**
     * <p>get ZIN_EUID value
     * @return ZIN_EUID value
     */
    public int getZinEuid() {
        return zinEuid__;
    }

    /**
     * <p>set ZIN_EUID value
     * @param zinEuid ZIN_EUID value
     */
    public void setZinEuid(int zinEuid) {
        zinEuid__ = zinEuid;
    }

    /**
     * <p>get ZIN_EDATE value
     * @return ZIN_EDATE value
     */
    public UDate getZinEdate() {
        return zinEdate__;
    }

    /**
     * <p>set ZIN_EDATE value
     * @param zinEdate ZIN_EDATE value
     */
    public void setZinEdate(UDate zinEdate) {
        zinEdate__ = zinEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(zifSid__);
        buf.append(",");
        buf.append(zinLinkkbn__);
        buf.append(",");
        buf.append(zinLinksid__);
        buf.append(",");
        buf.append(NullDefault.getString(zinName__, ""));
        buf.append(",");
        buf.append(zinBgcolor__);
        buf.append(",");
        buf.append(NullDefault.getString(zinMsg__, ""));
        buf.append(",");
        buf.append(zinXindex__);
        buf.append(",");
        buf.append(zinYindex__);
        buf.append(",");
        buf.append(NullDefault.getString(zinOtherValue__, ""));
        buf.append(",");
        buf.append(zinAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zinAdate__, ""));
        buf.append(",");
        buf.append(zinEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zinEdate__, ""));
        return buf.toString();
    }

}
