package jp.groupsession.v2.prj.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>PRJ_TODOMEMBER Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class PrjTodomemberModel implements Serializable {

    /** PRJ_SID mapping */
    private int prjSid__;
    /** PTD_SID mapping */
    private int ptdSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** PTM_EMPLOYEE_KBN mapping */
    private int ptmEmployeeKbn__;
    /** PTM_AUID mapping */
    private int ptmAuid__;
    /** PTM_ADATE mapping */
    private UDate ptmAdate__;
    /** PTM_EUID mapping */
    private int ptmEuid__;
    /** PTM_EDATE mapping */
    private UDate ptmEdate__;

    /**
     * <p>Default Constructor
     */
    public PrjTodomemberModel() {
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
     * <p>get PTD_SID value
     * @return PTD_SID value
     */
    public int getPtdSid() {
        return ptdSid__;
    }

    /**
     * <p>set PTD_SID value
     * @param ptdSid PTD_SID value
     */
    public void setPtdSid(int ptdSid) {
        ptdSid__ = ptdSid;
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
     * <p>get PTM_EMPLOYEE_KBN value
     * @return PTM_EMPLOYEE_KBN value
     */
    public int getPtmEmployeeKbn() {
        return ptmEmployeeKbn__;
    }

    /**
     * <p>set PTM_EMPLOYEE_KBN value
     * @param ptmEmployeeKbn PTM_EMPLOYEE_KBN value
     */
    public void setPtmEmployeeKbn(int ptmEmployeeKbn) {
        ptmEmployeeKbn__ = ptmEmployeeKbn;
    }

    /**
     * <p>get PTM_AUID value
     * @return PTM_AUID value
     */
    public int getPtmAuid() {
        return ptmAuid__;
    }

    /**
     * <p>set PTM_AUID value
     * @param ptmAuid PTM_AUID value
     */
    public void setPtmAuid(int ptmAuid) {
        ptmAuid__ = ptmAuid;
    }

    /**
     * <p>get PTM_ADATE value
     * @return PTM_ADATE value
     */
    public UDate getPtmAdate() {
        return ptmAdate__;
    }

    /**
     * <p>set PTM_ADATE value
     * @param ptmAdate PTM_ADATE value
     */
    public void setPtmAdate(UDate ptmAdate) {
        ptmAdate__ = ptmAdate;
    }

    /**
     * <p>get PTM_EUID value
     * @return PTM_EUID value
     */
    public int getPtmEuid() {
        return ptmEuid__;
    }

    /**
     * <p>set PTM_EUID value
     * @param ptmEuid PTM_EUID value
     */
    public void setPtmEuid(int ptmEuid) {
        ptmEuid__ = ptmEuid;
    }

    /**
     * <p>get PTM_EDATE value
     * @return PTM_EDATE value
     */
    public UDate getPtmEdate() {
        return ptmEdate__;
    }

    /**
     * <p>set PTM_EDATE value
     * @param ptmEdate PTM_EDATE value
     */
    public void setPtmEdate(UDate ptmEdate) {
        ptmEdate__ = ptmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(prjSid__);
        buf.append(",");
        buf.append(ptdSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(ptmEmployeeKbn__);
        buf.append(",");
        buf.append(ptmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptmAdate__, ""));
        buf.append(",");
        buf.append(ptmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(ptmEdate__, ""));
        return buf.toString();
    }

}
