package jp.groupsession.v2.cht.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>CHT_LOG_COUNT_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class ChtLogCountSumModel implements Serializable {

    /** CLS_SEND_CNT mapping */
    private int clsSendCnt__;
    /** CLS_SEND_UCNT mapping */
    private int clsSendUcnt__;
    /** CLS_SEND_GCNT mapping */
    private int clsSendGcnt__;
    /** CLS_USER_CNT mapping */
    private int clsUserCnt__;
    /** CLS_DATE mapping */
    private UDate clsDate__;
    /** CLS_YEAR_MONTH mapping */
    private int clsYearMonth__;
    /** CLS_EDATE mapping*/
    private UDate clsEdate__;

    /**
     * <p>Default Constructor
     */
    public ChtLogCountSumModel() {
    }

    /**
     * <p>get CLS_SEND_CNT value
     * @return CLS_SEND_CNT value
     */
    public int getClsSendCnt() {
        return clsSendCnt__;
    }

    /**
     * <p>set CLS_SEND_CNT value
     * @param clsSendCnt CLS_SEND_CNT value
     */
    public void setClsSendCnt(int clsSendCnt) {
        clsSendCnt__ = clsSendCnt;
    }

    /**
     * <p>get CLS_SEND_UCNT value
     * @return CLS_SEND_UCNT value
     */
    public int getClsSendUcnt() {
        return clsSendUcnt__;
    }

    /**
     * <p>set CLS_SEND_UCNT value
     * @param clsSendUcnt CLS_SEND_UCNT value
     */
    public void setClsSendUcnt(int clsSendUcnt) {
        clsSendUcnt__ = clsSendUcnt;
    }

    /**
     * <p>get CLS_SEND_GCNT value
     * @return CLS_SEND_GCNT value
     */
    public int getClsSendGcnt() {
        return clsSendGcnt__;
    }

    /**
     * <p>set CLS_SEND_GCNT value
     * @param clsSendGcnt CLS_SEND_GCNT value
     */
    public void setClsSendGcnt(int clsSendGcnt) {
        clsSendGcnt__ = clsSendGcnt;
    }

    /**
     * <p>get CLS_USER_CNT value
     * @return CLS_USER_CNT value
     */
    public int getClsUserCnt() {
        return clsUserCnt__;
    }

    /**
     * <p>set CLS_USER_CNT value
     * @param clsUserCnt CLS_USER_CNT value
     */
    public void setClsUserCnt(int clsUserCnt) {
        clsUserCnt__ = clsUserCnt;
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
     * <p>get CLS_YEAR_MONTH value
     * @return CLS_YEAR_MONTH value
     */
    public int getClsYearMonth() {
        return clsYearMonth__;
    }

    /**
     * <p>set CLS_YEAR_MONTH value
     * @param clsYearMonth CLS_YEAR_MONTH value
     */
    public void setClsYearMonth(int clsYearMonth) {
        clsYearMonth__ = clsYearMonth;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(clsSendCnt__);
        buf.append(",");
        buf.append(clsSendUcnt__);
        buf.append(",");
        buf.append(clsSendGcnt__);
        buf.append(",");
        buf.append(clsUserCnt__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(clsDate__, ""));
        buf.append(",");
        buf.append(clsYearMonth__);
        return buf.toString();
    }

    /**
     * <p>clsEdate を取得します。
     * @return clsEdate
     * @see jp.groupsession.v2.cht.model.ChtLogCountSumModel#clsEdate__
     */
    public UDate getClsEdate() {
        return clsEdate__;
    }

    /**
     * <p>clsEdate をセットします。
     * @param clsEdate clsEdate
     * @see jp.groupsession.v2.cht.model.ChtLogCountSumModel#clsEdate__
     */
    public void setClsEdate(UDate clsEdate) {
        clsEdate__ = clsEdate;
    }

}
