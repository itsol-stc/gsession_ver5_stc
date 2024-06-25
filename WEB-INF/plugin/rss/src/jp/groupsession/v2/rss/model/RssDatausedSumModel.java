package jp.groupsession.v2.rss.model;

import java.io.Serializable;

/**
 * <p>RSS_DATAUSED_SUM Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class RssDatausedSumModel implements Serializable {

    /** SUM_TYPE mapping */
    private int sumType__;
    /** RSS_DATA_SIZE mapping */
    private long rssDataSize__;

    /**
     * <p>Default Constructor
     */
    public RssDatausedSumModel() {
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
     * <p>get RSS_DATA_SIZE value
     * @return RSS_DATA_SIZE value
     */
    public long getRssDataSize() {
        return rssDataSize__;
    }

    /**
     * <p>set RSS_DATA_SIZE value
     * @param rssDataSize RSS_DATA_SIZE value
     */
    public void setRssDataSize(long rssDataSize) {
        rssDataSize__ = rssDataSize;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(sumType__);
        buf.append(",");
        buf.append(rssDataSize__);
        return buf.toString();
    }

}
