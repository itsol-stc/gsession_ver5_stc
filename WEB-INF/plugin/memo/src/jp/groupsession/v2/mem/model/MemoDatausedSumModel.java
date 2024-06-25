package jp.groupsession.v2.mem.model;

import java.io.Serializable;

/**
 * <p>MEMO_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class MemoDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** MEMO_DATA_SIZE mapping */
    private long memoDataSize__;

    /**
     * <p>Default Constructor
     */
    public MemoDatausedSumModel() {
    }

    /**
     * <p>get SUM_TYPE value
     * @return SUM_TYPE value
     */
    public int getSumType() {
        return sumType__;
    }

    /**
     * <p>set SUM_TYPE value
     * @param sumType SUM_TYPE value
     */
    public void setSumType(int sumType) {
        sumType__ = sumType;
    }

    /**
     * <p>get MEMO_DATA_SIZE value
     * @return MEMO_DATA_SIZE value
     */
    public long getMemoDataSize() {
        return memoDataSize__;
    }

    /**
     * <p>set MEMO_DATA_SIZE value
     * @param memoDataSize MEMO_DATA_SIZE value
     */
    public void setMemoDataSize(long memoDataSize) {
        memoDataSize__ = memoDataSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(memoDataSize__);
        return buf.toString();
    }

}
