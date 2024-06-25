package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>FILE_UPLOAD_LOG Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class FileUploadLogModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** GRP_SID mapping */
    private int grpSid__;
    /** FUL_REG_KBN mapping */
    private int fulRegKbn__;
    /** FCB_SID mapping */
    private int fcbSid__;
    /** BIN_SID mapping */
    private long binSid__;
    /** FUL_DATE mapping */
    private UDate fulDate__;

    /**
     * <p>Default Constructor
     */
    public FileUploadLogModel() {
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
     * <p>get GRP_SID value
     * @return GRP_SID value
     */
    public int getGrpSid() {
        return grpSid__;
    }

    /**
     * <p>set GRP_SID value
     * @param grpSid GRP_SID value
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>get FUL_REG_KBN value
     * @return FUL_REG_KBN value
     */
    public int getFulRegKbn() {
        return fulRegKbn__;
    }

    /**
     * <p>set FUL_REG_KBN value
     * @param fulRegKbn FUL_REG_KBN value
     */
    public void setFulRegKbn(int fulRegKbn) {
        fulRegKbn__ = fulRegKbn;
    }

    /**
     * <p>get FCB_SID value
     * @return FCB_SID value
     */
    public int getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>set FCB_SID value
     * @param fcbSid FCB_SID value
     */
    public void setFcbSid(int fcbSid) {
        fcbSid__ = fcbSid;
    }

    /**
     * <p>get BIN_SID value
     * @return BIN_SID value
     */
    public long getBinSid() {
        return binSid__;
    }

    /**
     * <p>set BIN_SID value
     * @param binSid BIN_SID value
     */
    public void setBinSid(long binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>get FUL_DATE value
     * @return FUL_DATE value
     */
    public UDate getFulDate() {
        return fulDate__;
    }

    /**
     * <p>set FUL_DATE value
     * @param fulDate FUL_DATE value
     */
    public void setFulDate(UDate fulDate) {
        fulDate__ = fulDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(grpSid__);
        buf.append(",");
        buf.append(fulRegKbn__);
        buf.append(",");
        buf.append(fcbSid__);
        buf.append(",");
        buf.append(binSid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(fulDate__, ""));
        return buf.toString();
    }

}
