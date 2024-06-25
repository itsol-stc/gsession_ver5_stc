package jp.groupsession.v2.enq.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>ENQ_AUTODELETE Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class EnqAutodeleteModel implements Serializable {

    /** EAD_SEND_KBN mapping */
    private int eadSendKbn__;
    /** EAD_SEND_YEAR mapping */
    private int eadSendYear__;
    /** EAD_SEND_MONTH mapping */
    private int eadSendMonth__;
    /** EAD_DRAFT_KBN mapping */
    private int eadDraftKbn__;
    /** EAD_DRAFT_YEAR mapping */
    private int eadDraftYear__;
    /** EAD_DRAFT_MONTH mapping */
    private int eadDraftMonth__;
    /** EAD_AUID mapping */
    private int eadAuid__;
    /** EAD_ADATE mapping */
    private UDate eadAdate__;
    /** EAD_EUID mapping */
    private int eadEuid__;
    /** EAD_EDATE mapping */
    private UDate eadEdate__;

    /**
     * <p>Default Constructor
     */
    public EnqAutodeleteModel() {
    }

    /**
     * <p>get EAD_SEND_KBN value
     * @return EAD_SEND_KBN value
     */
    public int getEadSendKbn() {
        return eadSendKbn__;
    }

    /**
     * <p>set EAD_SEND_KBN value
     * @param eadSendKbn EAD_SEND_KBN value
     */
    public void setEadSendKbn(int eadSendKbn) {
        eadSendKbn__ = eadSendKbn;
    }

    /**
     * <p>get EAD_SEND_YEAR value
     * @return EAD_SEND_YEAR value
     */
    public int getEadSendYear() {
        return eadSendYear__;
    }

    /**
     * <p>set EAD_SEND_YEAR value
     * @param eadSendYear EAD_SEND_YEAR value
     */
    public void setEadSendYear(int eadSendYear) {
        eadSendYear__ = eadSendYear;
    }

    /**
     * <p>get EAD_SEND_MONTH value
     * @return EAD_SEND_MONTH value
     */
    public int getEadSendMonth() {
        return eadSendMonth__;
    }

    /**
     * <p>set EAD_SEND_MONTH value
     * @param eadSendMonth EAD_SEND_MONTH value
     */
    public void setEadSendMonth(int eadSendMonth) {
        eadSendMonth__ = eadSendMonth;
    }

    /**
     * <p>get EAD_DRAFT_KBN value
     * @return EAD_DRAFT_KBN value
     */
    public int getEadDraftKbn() {
        return eadDraftKbn__;
    }

    /**
     * <p>set EAD_DRAFT_KBN value
     * @param eadDraftKbn EAD_DRAFT_KBN value
     */
    public void setEadDraftKbn(int eadDraftKbn) {
        eadDraftKbn__ = eadDraftKbn;
    }

    /**
     * <p>get EAD_DRAFT_YEAR value
     * @return EAD_DRAFT_YEAR value
     */
    public int getEadDraftYear() {
        return eadDraftYear__;
    }

    /**
     * <p>set EAD_DRAFT_YEAR value
     * @param eadDraftYear EAD_DRAFT_YEAR value
     */
    public void setEadDraftYear(int eadDraftYear) {
        eadDraftYear__ = eadDraftYear;
    }

    /**
     * <p>get EAD_DRAFT_MONTH value
     * @return EAD_DRAFT_MONTH value
     */
    public int getEadDraftMonth() {
        return eadDraftMonth__;
    }

    /**
     * <p>set EAD_DRAFT_MONTH value
     * @param eadDraftMonth EAD_DRAFT_MONTH value
     */
    public void setEadDraftMonth(int eadDraftMonth) {
        eadDraftMonth__ = eadDraftMonth;
    }

    /**
     * <p>get EAD_AUID value
     * @return EAD_AUID value
     */
    public int getEadAuid() {
        return eadAuid__;
    }

    /**
     * <p>set EAD_AUID value
     * @param eadAuid EAD_AUID value
     */
    public void setEadAuid(int eadAuid) {
        eadAuid__ = eadAuid;
    }

    /**
     * <p>get EAD_ADATE value
     * @return EAD_ADATE value
     */
    public UDate getEadAdate() {
        return eadAdate__;
    }

    /**
     * <p>set EAD_ADATE value
     * @param eadAdate EAD_ADATE value
     */
    public void setEadAdate(UDate eadAdate) {
        eadAdate__ = eadAdate;
    }

    /**
     * <p>get EAD_EUID value
     * @return EAD_EUID value
     */
    public int getEadEuid() {
        return eadEuid__;
    }

    /**
     * <p>set EAD_EUID value
     * @param eadEuid EAD_EUID value
     */
    public void setEadEuid(int eadEuid) {
        eadEuid__ = eadEuid;
    }

    /**
     * <p>get EAD_EDATE value
     * @return EAD_EDATE value
     */
    public UDate getEadEdate() {
        return eadEdate__;
    }

    /**
     * <p>set EAD_EDATE value
     * @param eadEdate EAD_EDATE value
     */
    public void setEadEdate(UDate eadEdate) {
        eadEdate__ = eadEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(eadSendKbn__);
        buf.append(",");
        buf.append(eadSendYear__);
        buf.append(",");
        buf.append(eadSendMonth__);
        buf.append(",");
        buf.append(eadDraftKbn__);
        buf.append(",");
        buf.append(eadDraftYear__);
        buf.append(",");
        buf.append(eadDraftMonth__);
        buf.append(",");
        buf.append(eadAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eadAdate__, ""));
        buf.append(",");
        buf.append(eadEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(eadEdate__, ""));
        return buf.toString();
    }

}
