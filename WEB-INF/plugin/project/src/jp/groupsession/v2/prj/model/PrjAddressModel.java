package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_ADDRESS Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class PrjAddressModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** ADR_SID mapping */
    private int adrSid__;
    /** PRA_AUID mapping */
    private int praAuid__;
    /** PRA_ADATE mapping */
    private UDate praAdate__;
    /** PRA_EUID mapping */
    private int praEuid__;
    /** PRA_EDATE mapping */
    private UDate praEdate__;
    /** PRA_SORT mapping */
    private int praSort__;

    /**
     * <p>praSort を取得します。
     * @return praSort
     */
    public int getPraSort() {
        return praSort__;
    }

    /**
     * <p>praSort をセットします。
     * @param praSort praSort
     */
    public void setPraSort(int praSort) {
        praSort__ = praSort;
    }

    /**
     * <p>Default Constructor
     */
    public PrjAddressModel() {
    }

    /**
     * <p>get PRJ_SID value
     * @return PRJ_SID value
     */
    public int getPrjSid() {
        return prjSid__;
    }

    /**
     * <p>set PRJ_SID value
     * @param prjSid PRJ_SID value
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
    }

    /**
     * <p>get ADR_SID value
     * @return ADR_SID value
     */
    public int getAdrSid() {
        return adrSid__;
    }

    /**
     * <p>set ADR_SID value
     * @param adrSid ADR_SID value
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
    }

    /**
     * <p>get PRA_AUID value
     * @return PRA_AUID value
     */
    public int getPraAuid() {
        return praAuid__;
    }

    /**
     * <p>set PRA_AUID value
     * @param praAuid PRA_AUID value
     */
    public void setPraAuid(int praAuid) {
        praAuid__ = praAuid;
    }

    /**
     * <p>get PRA_ADATE value
     * @return PRA_ADATE value
     */
    public UDate getPraAdate() {
        return praAdate__;
    }

    /**
     * <p>set PRA_ADATE value
     * @param praAdate PRA_ADATE value
     */
    public void setPraAdate(UDate praAdate) {
        praAdate__ = praAdate;
    }

    /**
     * <p>get PRA_EUID value
     * @return PRA_EUID value
     */
    public int getPraEuid() {
        return praEuid__;
    }

    /**
     * <p>set PRA_EUID value
     * @param praEuid PRA_EUID value
     */
    public void setPraEuid(int praEuid) {
        praEuid__ = praEuid;
    }

    /**
     * <p>get PRA_EDATE value
     * @return PRA_EDATE value
     */
    public UDate getPraEdate() {
        return praEdate__;
    }

    /**
     * <p>set PRA_EDATE value
     * @param praEdate PRA_EDATE value
     */
    public void setPraEdate(UDate praEdate) {
        praEdate__ = praEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(adrSid__);
        buf.append(",");
        buf.append(praAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(praAdate__, ""));
        buf.append(",");
        buf.append(praEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(praEdate__, ""));
        return buf.toString();
    }

}
