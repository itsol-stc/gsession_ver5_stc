package jp.groupsession.v2.cir.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>CIR_LOG_COUNT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirLogCountModel implements Serializable {

    /** CAC_SID mapping */
    private int cacSid__;
    /** CLC_KBN mapping */
    private int clcKbn__;
    /** CLC_CNT mapping */
    private int clcCnt__;
    /** CLC_DATE mapping */
    private UDate clcDate__;

    /**
     * <p>Default Constructor
     */
    public CirLogCountModel() {
    }

    /**
     * <p>get CAC_SID value
     * @return CAC_SID value
     */
    public int getCacSid() {
        return cacSid__;
    }

    /**
     * <p>set CAC_SID value
     * @param cacSid CAC_SID value
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
    }

    /**
     * <p>get CLC_KBN value
     * @return CLC_KBN value
     */
    public int getClcKbn() {
        return clcKbn__;
    }

    /**
     * <p>set CLC_KBN value
     * @param clcKbn CLC_KBN value
     */
    public void setClcKbn(int clcKbn) {
        clcKbn__ = clcKbn;
    }

    /**
     * <p>get CLC_CNT value
     * @return CLC_CNT value
     */
    public int getClcCnt() {
        return clcCnt__;
    }

    /**
     * <p>set CLC_CNT value
     * @param clcCnt CLC_CNT value
     */
    public void setClcCnt(int clcCnt) {
        clcCnt__ = clcCnt;
    }

    /**
     * <p>get CLC_DATE value
     * @return CLC_DATE value
     */
    public UDate getClcDate() {
        return clcDate__;
    }

    /**
     * <p>set CLC_DATE value
     * @param clcDate CLC_DATE value
     */
    public void setClcDate(UDate clcDate) {
        clcDate__ = clcDate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(cacSid__);
        buf.append(",");
        buf.append(clcKbn__);
        buf.append(",");
        buf.append(clcCnt__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(clcDate__, ""));
        return buf.toString();
    }

}
