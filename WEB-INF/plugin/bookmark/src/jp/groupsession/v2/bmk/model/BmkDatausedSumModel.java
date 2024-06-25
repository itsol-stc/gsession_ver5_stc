package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

/**
 * <p>BMK_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class BmkDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** BMK_BOOKMARK_SIZE mapping */
    private long bmkBookmarkSize__;

    /**
     * <p>Default Constructor
     */
    public BmkDatausedSumModel() {
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
     * <p>get BMK_BOOKMARK_SIZE value
     * @return BMK_BOOKMARK_SIZE value
     */
    public long getBmkBookmarkSize() {
        return bmkBookmarkSize__;
    }

    /**
     * <p>set BMK_BOOKMARK_SIZE value
     * @param bmkBookmarkSize BMK_BOOKMARK_SIZE value
     */
    public void setBmkBookmarkSize(long bmkBookmarkSize) {
        bmkBookmarkSize__ = bmkBookmarkSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(bmkBookmarkSize__);
        return buf.toString();
    }

}
