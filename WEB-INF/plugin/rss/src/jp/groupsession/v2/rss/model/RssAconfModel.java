package jp.groupsession.v2.rss.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RSS_ACONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssAconfModel implements Serializable {

    /** RAC_READTIME mapping */
    private int racReadtime__;
    /** RAC_AUID mapping */
    private int racAuid__;
    /** RAC_ADATE mapping */
    private UDate racAdate__;
    /** RAC_EUID mapping */
    private int racEuid__;
    /** RAC_EDATE mapping */
    private UDate racEdate__;

    /**
     * <p>Default Constructor
     */
    public RssAconfModel() {
    }

    /**
     * <p>get RAC_READTIME value
     * @return RAC_READTIME value
     */
    public int getRacReadtime() {
        return racReadtime__;
    }

    /**
     * <p>set RAC_READTIME value
     * @param racReadtime RAC_READTIME value
     */
    public void setRacReadtime(int racReadtime) {
        racReadtime__ = racReadtime;
    }

    /**
     * <p>get RAC_AUID value
     * @return RAC_AUID value
     */
    public int getRacAuid() {
        return racAuid__;
    }

    /**
     * <p>set RAC_AUID value
     * @param racAuid RAC_AUID value
     */
    public void setRacAuid(int racAuid) {
        racAuid__ = racAuid;
    }

    /**
     * <p>get RAC_ADATE value
     * @return RAC_ADATE value
     */
    public UDate getRacAdate() {
        return racAdate__;
    }

    /**
     * <p>set RAC_ADATE value
     * @param racAdate RAC_ADATE value
     */
    public void setRacAdate(UDate racAdate) {
        racAdate__ = racAdate;
    }

    /**
     * <p>get RAC_EUID value
     * @return RAC_EUID value
     */
    public int getRacEuid() {
        return racEuid__;
    }

    /**
     * <p>set RAC_EUID value
     * @param racEuid RAC_EUID value
     */
    public void setRacEuid(int racEuid) {
        racEuid__ = racEuid;
    }

    /**
     * <p>get RAC_EDATE value
     * @return RAC_EDATE value
     */
    public UDate getRacEdate() {
        return racEdate__;
    }

    /**
     * <p>set RAC_EDATE value
     * @param racEdate RAC_EDATE value
     */
    public void setRacEdate(UDate racEdate) {
        racEdate__ = racEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(racReadtime__);
        buf.append(",");
        buf.append(racAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(racAdate__, ""));
        buf.append(",");
        buf.append(racEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(racEdate__, ""));
        return buf.toString();
    }

}
