package jp.groupsession.v2.cir.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CIR_LOG_COUNT_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class CirLogCountSumModel implements Serializable {

    /** CLS_KBN mapping */
    private int clsKbn__;
    /** CLS_CNT mapping */
    private long clsCnt__;
    /** CLS_CNT_SUM mapping */
    private long clsCntSum__;
    /** CLS_DATE mapping */
    private UDate clsDate__;
    /** CLS_MONTH mapping */
    private int clsMonth__;
    /** CLS_EDATE mapping */
    private UDate clsEdate__;

    /**
     * <p>Default Constructor
     */
    public CirLogCountSumModel() {
    }

    /**
     * <p>get CLS_KBN value
     * @return CLS_KBN value
     */
    public int getClsKbn() {
        return clsKbn__;
    }

    /**
     * <p>set CLS_KBN value
     * @param clsKbn CLS_KBN value
     */
    public void setClsKbn(int clsKbn) {
        clsKbn__ = clsKbn;
    }

    /**
     * <p>get CLS_CNT value
     * @return CLS_CNT value
     */
    public long getClsCnt() {
        return clsCnt__;
    }

    /**
     * <p>set CLS_CNT value
     * @param clsCnt CLS_CNT value
     */
    public void setClsCnt(long clsCnt) {
        clsCnt__ = clsCnt;
    }

    /**
     * <p>get CLS_CNT_SUM value
     * @return CLS_CNT_SUM value
     */
    public long getClsCntSum() {
        return clsCntSum__;
    }

    /**
     * <p>set CLS_CNT_SUM value
     * @param clsCntSum CLS_CNT_SUM value
     */
    public void setClsCntSum(long clsCntSum) {
        clsCntSum__ = clsCntSum;
    }

    /**
     * <p>get CLS_DATE value
     * @return CLS_DATE value
     */
    public UDate getClsDate() {
        return clsDate__;
    }

    /**
     * <p>set CLS_DATE value
     * @param clsDate CLS_DATE value
     */
    public void setClsDate(UDate clsDate) {
        clsDate__ = clsDate;
    }

    /**
     * <p>get CLS_MONTH value
     * @return CLS_MONTH value
     */
    public int getClsMonth() {
        return clsMonth__;
    }

    /**
     * <p>set CLS_MONTH value
     * @param clsMonth CLS_MONTH value
     */
    public void setClsMonth(int clsMonth) {
        clsMonth__ = clsMonth;
    }

    /**
     * <p>get CLS_EDATE value
     * @return CLS_EDATE value
     */
    public UDate getClsEdate() {
        return clsEdate__;
    }

    /**
     * <p>set CLS_EDATE value
     * @param clsEdate CLS_EDATE value
     */
    public void setClsEdate(UDate clsEdate) {
        clsEdate__ = clsEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(clsKbn__);
        buf.append(",");
        buf.append(clsCnt__);
        buf.append(",");
        buf.append(clsCntSum__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(clsDate__, ""));
        buf.append(",");
        buf.append(clsMonth__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(clsEdate__, ""));
        return buf.toString();
    }

}
