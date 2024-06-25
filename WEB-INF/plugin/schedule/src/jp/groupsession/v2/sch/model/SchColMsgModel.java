package jp.groupsession.v2.sch.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>SCH_COL_MSG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SchColMsgModel implements Serializable {

    /** SCM_ID mapping */
    private int scmId__;
    /** SCM_MSG mapping */
    private String scmMsg__;
    /** SCM_AUID mapping */
    private int scmAuid__;
    /** SCM_ADATE mapping */
    private UDate scmAdate__;
    /** SCM_EUID mapping */
    private int scmEuid__;
    /** SCM_EDATE mapping */
    private UDate scmEdate__;

    /**
     * <p>Default Constructor
     */
    public SchColMsgModel() {
    }

    /**
     * <p>get SCM_ID value
     * @return SCM_ID value
     */
    public int getScmId() {
        return scmId__;
    }

    /**
     * <p>set SCM_ID value
     * @param scmId SCM_ID value
     */
    public void setScmId(int scmId) {
        scmId__ = scmId;
    }

    /**
     * <p>get SCM_MSG value
     * @return SCM_MSG value
     */
    public String getScmMsg() {
        return scmMsg__;
    }

    /**
     * <p>set SCM_MSG value
     * @param scmMsg SCM_MSG value
     */
    public void setScmMsg(String scmMsg) {
        scmMsg__ = scmMsg;
    }

    /**
     * <p>get SCM_AUID value
     * @return SCM_AUID value
     */
    public int getScmAuid() {
        return scmAuid__;
    }

    /**
     * <p>set SCM_AUID value
     * @param scmAuid SCM_AUID value
     */
    public void setScmAuid(int scmAuid) {
        scmAuid__ = scmAuid;
    }

    /**
     * <p>get SCM_ADATE value
     * @return SCM_ADATE value
     */
    public UDate getScmAdate() {
        return scmAdate__;
    }

    /**
     * <p>set SCM_ADATE value
     * @param scmAdate SCM_ADATE value
     */
    public void setScmAdate(UDate scmAdate) {
        scmAdate__ = scmAdate;
    }

    /**
     * <p>get SCM_EUID value
     * @return SCM_EUID value
     */
    public int getScmEuid() {
        return scmEuid__;
    }

    /**
     * <p>set SCM_EUID value
     * @param scmEuid SCM_EUID value
     */
    public void setScmEuid(int scmEuid) {
        scmEuid__ = scmEuid;
    }

    /**
     * <p>get SCM_EDATE value
     * @return SCM_EDATE value
     */
    public UDate getScmEdate() {
        return scmEdate__;
    }

    /**
     * <p>set SCM_EDATE value
     * @param scmEdate SCM_EDATE value
     */
    public void setScmEdate(UDate scmEdate) {
        scmEdate__ = scmEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(scmId__);
        buf.append(",");
        buf.append(NullDefault.getString(scmMsg__, ""));
        buf.append(",");
        buf.append(scmAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(scmAdate__, ""));
        buf.append(",");
        buf.append(scmEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(scmEdate__, ""));
        return buf.toString();
    }

}
