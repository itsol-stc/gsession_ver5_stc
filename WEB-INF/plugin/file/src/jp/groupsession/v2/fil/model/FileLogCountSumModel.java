package jp.groupsession.v2.fil.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>FILE_LOG_COUNT_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class FileLogCountSumModel implements Serializable {

    /** FLS_KBN mapping */
    private int flsKbn__;
    /** FLS_DATE mapping */
    private UDate flsDate__;
    /** FLS_MONTH mapping */
    private int flsMonth__;
    /** FLS_CNT mapping */
    private long flsCnt__;
    /** FLS_EDATE mapping */
    private UDate flsEdate__;

    /**
     * <p>Default Constructor
     */
    public FileLogCountSumModel() {
    }

    /**
     * <p>get FLS_KBN value
     * @return FLS_KBN value
     */
    public int getFlsKbn() {
        return flsKbn__;
    }

    /**
     * <p>set FLS_KBN value
     * @param flsKbn FLS_KBN value
     */
    public void setFlsKbn(int flsKbn) {
        flsKbn__ = flsKbn;
    }

    /**
     * <p>get FLS_DATE value
     * @return FLS_DATE value
     */
    public UDate getFlsDate() {
        return flsDate__;
    }

    /**
     * <p>set FLS_DATE value
     * @param flsDate FLS_DATE value
     */
    public void setFlsDate(UDate flsDate) {
        flsDate__ = flsDate;
    }

    /**
     * <p>get FLS_MONTH value
     * @return FLS_MONTH value
     */
    public int getFlsMonth() {
        return flsMonth__;
    }

    /**
     * <p>set FLS_MONTH value
     * @param flsMonth FLS_MONTH value
     */
    public void setFlsMonth(int flsMonth) {
        flsMonth__ = flsMonth;
    }

    /**
     * <p>get FLS_CNT value
     * @return FLS_CNT value
     */
    public long getFlsCnt() {
        return flsCnt__;
    }

    /**
     * <p>set FLS_CNT value
     * @param flsCnt FLS_CNT value
     */
    public void setFlsCnt(long flsCnt) {
        flsCnt__ = flsCnt;
    }

    /**
     * <p>get FLS_EDATE value
     * @return FLS_EDATE value
     */
    public UDate getFlsEdate() {
        return flsEdate__;
    }

    /**
     * <p>set FLS_EDATE value
     * @param flsEdate FLS_EDATE value
     */
    public void setFlsEdate(UDate flsEdate) {
        flsEdate__ = flsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(flsKbn__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(flsDate__, ""));
        buf.append(",");
        buf.append(flsMonth__);
        buf.append(",");
        buf.append(flsCnt__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(flsEdate__, ""));
        return buf.toString();
    }

}
