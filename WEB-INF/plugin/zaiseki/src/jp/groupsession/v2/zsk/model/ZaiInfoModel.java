package jp.groupsession.v2.zsk.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ZAI_INFO Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiInfoModel implements Serializable {

    /** ZIF_SID mapping */
    private int zifSid__;
    /** ZIF_NAME mapping */
    private String zifName__;
    /** BIN_SID mapping */
    private Long binSid__ = new Long(0);
    /** ZIF_SORT mapping */
    private int zifSort__;
    /** ZIF_MSG mapping */
    private String zifMsg__;
    /** ZIF_AUID mapping */
    private int zifAuid__;
    /** ZIF_ADATE mapping */
    private UDate zifAdate__;
    /** ZIF_EUID mapping */
    private int zifEuid__;
    /** ZIF_EDATE mapping */
    private UDate zifEdate__;

    /**
     * <p>Default Constructor
     */
    public ZaiInfoModel() {
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
     * <p>get ZIF_NAME value
     * @return ZIF_NAME value
     */
    public String getZifName() {
        return zifName__;
    }

    /**
     * <p>set ZIF_NAME value
     * @param zifName ZIF_NAME value
     */
    public void setZifName(String zifName) {
        zifName__ = zifName;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>get ZIF_SORT value
     * @return ZIF_SORT value
     */
    public int getZifSort() {
        return zifSort__;
    }

    /**
     * <p>set ZIF_SORT value
     * @param zifSort ZIF_SORT value
     */
    public void setZifSort(int zifSort) {
        zifSort__ = zifSort;
    }

    /**
     * <p>get ZIF_MSG value
     * @return ZIF_MSG value
     */
    public String getZifMsg() {
        return zifMsg__;
    }

    /**
     * <p>set ZIF_MSG value
     * @param zifMsg ZIF_MSG value
     */
    public void setZifMsg(String zifMsg) {
        zifMsg__ = zifMsg;
    }

    /**
     * <p>get ZIF_AUID value
     * @return ZIF_AUID value
     */
    public int getZifAuid() {
        return zifAuid__;
    }

    /**
     * <p>set ZIF_AUID value
     * @param zifAuid ZIF_AUID value
     */
    public void setZifAuid(int zifAuid) {
        zifAuid__ = zifAuid;
    }

    /**
     * <p>get ZIF_ADATE value
     * @return ZIF_ADATE value
     */
    public UDate getZifAdate() {
        return zifAdate__;
    }

    /**
     * <p>set ZIF_ADATE value
     * @param zifAdate ZIF_ADATE value
     */
    public void setZifAdate(UDate zifAdate) {
        zifAdate__ = zifAdate;
    }

    /**
     * <p>get ZIF_EUID value
     * @return ZIF_EUID value
     */
    public int getZifEuid() {
        return zifEuid__;
    }

    /**
     * <p>set ZIF_EUID value
     * @param zifEuid ZIF_EUID value
     */
    public void setZifEuid(int zifEuid) {
        zifEuid__ = zifEuid;
    }

    /**
     * <p>get ZIF_EDATE value
     * @return ZIF_EDATE value
     */
    public UDate getZifEdate() {
        return zifEdate__;
    }

    /**
     * <p>set ZIF_EDATE value
     * @param zifEdate ZIF_EDATE value
     */
    public void setZifEdate(UDate zifEdate) {
        zifEdate__ = zifEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(zifSid__);
        buf.append(",");
        buf.append(NullDefault.getString(zifName__, ""));
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(zifSort__);
        buf.append(",");
        buf.append(NullDefault.getString(zifMsg__, ""));
        buf.append(",");
        buf.append(zifAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zifAdate__, ""));
        buf.append(",");
        buf.append(zifEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zifEdate__, ""));
        return buf.toString();
    }

}
