package jp.groupsession.v2.zsk.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>ZAI_PRI_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class ZaiPriConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** ZIF_SID mapping */
    private int zifSid__;
    /** ZPC_RELOAD mapping */
    private int zpcReload__;
    /** ZPC_AID mapping */
    private int zpcAid__;
    /** ZPC_ADATE mapping */
    private UDate zpcAdate__;
    /** ZPC_EID mapping */
    private int zpcEid__;
    /** ZPC_EDATE mapping */
    private UDate zpcEdate__;

    /** ZPC_SORT_KEY1 mapping */
    private int zpcSortKey1__;
    /** ZPC_SORT_ORDER1 mapping */
    private int zpcSortOrder1__;
    /** ZPC_SORT_KEY2 mapping */
    private int zpcSortKey2__;
    /** ZPC_SORT_ORDER2 mapping */
    private int zpcSortOrder2__;

    /**
     * <p>Default Constructor
     */
    public ZaiPriConfModel() {
    }


    /**
     * <p>zpcSortKey1 を取得します。
     * @return zpcSortKey1
     */
    public int getZpcSortKey1() {
        return zpcSortKey1__;
    }


    /**
     * <p>zpcSortKey1 をセットします。
     * @param zpcSortKey1 zpcSortKey1
     */
    public void setZpcSortKey1(int zpcSortKey1) {
        zpcSortKey1__ = zpcSortKey1;
    }


    /**
     * <p>zpcSortOrder1 を取得します。
     * @return zpcSortOrder1
     */
    public int getZpcSortOrder1() {
        return zpcSortOrder1__;
    }


    /**
     * <p>zpcSortOrder1 をセットします。
     * @param zpcSortOrder1 zpcSortOrder1
     */
    public void setZpcSortOrder1(int zpcSortOrder1) {
        zpcSortOrder1__ = zpcSortOrder1;
    }


    /**
     * <p>zpcSortKey2 を取得します。
     * @return zpcSortKey2
     */
    public int getZpcSortKey2() {
        return zpcSortKey2__;
    }


    /**
     * <p>zpcSortKey2 をセットします。
     * @param zpcSortKey2 zpcSortKey2
     */
    public void setZpcSortKey2(int zpcSortKey2) {
        zpcSortKey2__ = zpcSortKey2;
    }


    /**
     * <p>zpcSortOrder2 を取得します。
     * @return zpcSortOrder2
     */
    public int getZpcSortOrder2() {
        return zpcSortOrder2__;
    }


    /**
     * <p>zpcSortOrder2 をセットします。
     * @param zpcSortOrder2 zpcSortOrder2
     */
    public void setZpcSortOrder2(int zpcSortOrder2) {
        zpcSortOrder2__ = zpcSortOrder2;
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
     * <p>get ZPC_AID value
     * @return ZPC_AID value
     */
    public int getZpcAid() {
        return zpcAid__;
    }

    /**
     * <p>set ZPC_AID value
     * @param zpcAid ZPC_AID value
     */
    public void setZpcAid(int zpcAid) {
        zpcAid__ = zpcAid;
    }

    /**
     * <p>get ZPC_ADATE value
     * @return ZPC_ADATE value
     */
    public UDate getZpcAdate() {
        return zpcAdate__;
    }

    /**
     * <p>set ZPC_ADATE value
     * @param zpcAdate ZPC_ADATE value
     */
    public void setZpcAdate(UDate zpcAdate) {
        zpcAdate__ = zpcAdate;
    }

    /**
     * <p>get ZPC_EID value
     * @return ZPC_EID value
     */
    public int getZpcEid() {
        return zpcEid__;
    }

    /**
     * <p>set ZPC_EID value
     * @param zpcEid ZPC_EID value
     */
    public void setZpcEid(int zpcEid) {
        zpcEid__ = zpcEid;
    }

    /**
     * <p>get ZPC_EDATE value
     * @return ZPC_EDATE value
     */
    public UDate getZpcEdate() {
        return zpcEdate__;
    }

    /**
     * <p>set ZPC_EDATE value
     * @param zpcEdate ZPC_EDATE value
     */
    public void setZpcEdate(UDate zpcEdate) {
        zpcEdate__ = zpcEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(zifSid__);
        buf.append(",");
        buf.append(zpcAid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zpcAdate__, ""));
        buf.append(",");
        buf.append(zpcEid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(zpcEdate__, ""));
        return buf.toString();
    }

    /**
     * <p>zpcReload を取得します。
     * @return zpcReload
     */
    public int getZpcReload() {
        return zpcReload__;
    }

    /**
     * <p>zpcReload をセットします。
     * @param zpcReload zpcReload
     */
    public void setZpcReload(int zpcReload) {
        zpcReload__ = zpcReload;
    }

}
