package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;

/**
 * <p>BMK_URL Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class BmkUrlModel implements Serializable {

    /** BMU_SID mapping */
    private int bmuSid__;
    /** BMU_URL mapping */
    private String bmuUrl__;
    /** BMU_TITLE mapping */
    private String bmuTitle__;
    /** BMU_PUB_DATE mapping */
    private UDate bmuPubDate__;
    /** BMU_AUID mapping */
    private int bmuAuid__;
    /** BMU_ADATE mapping */
    private UDate bmuAdate__;
    /** BMU_EUID mapping */
    private int bmuEuid__;
    /** BMU_EDATE mapping */
    private UDate bmuEdate__;

    /**
     * <p>Default Constructor
     */
    public BmkUrlModel() {
    }

    /**
     * <p>get BMU_SID value
     * @return BMU_SID value
     */
    public int getBmuSid() {
        return bmuSid__;
    }

    /**
     * <p>set BMU_SID value
     * @param bmuSid BMU_SID value
     */
    public void setBmuSid(int bmuSid) {
        bmuSid__ = bmuSid;
    }

    /**
     * <p>get BMU_URL value
     * @return BMU_URL value
     */
    public String getBmuUrl() {
        return bmuUrl__;
    }

    /**
     * <p>set BMU_URL value
     * @param bmuUrl BMU_URL value
     */
    public void setBmuUrl(String bmuUrl) {
        bmuUrl__ = bmuUrl;
    }

    /**
     * <p>get BMU_TITLE value
     * @return BMU_TITLE value
     */
    public String getBmuTitle() {
        return bmuTitle__;
    }

    /**
     * <p>set BMU_TITLE value
     * @param bmuTitle BMU_TITLE value
     */
    public void setBmuTitle(String bmuTitle) {
        bmuTitle__ = bmuTitle;
    }

    /**
     * <p>get BMU_PUB_DATE value
     * @return BMU_PUB_DATE value
     */
    public UDate getBmuPubDate() {
        return bmuPubDate__;
    }

    /**
     * <p>set BMU_PUB_DATE value
     * @param bmuPubDate BMU_PUB_DATE value
     */
    public void setBmuPubDate(UDate bmuPubDate) {
        bmuPubDate__ = bmuPubDate;
    }

    /**
     * <p>get BMU_AUID value
     * @return BMU_AUID value
     */
    public int getBmuAuid() {
        return bmuAuid__;
    }

    /**
     * <p>set BMU_AUID value
     * @param bmuAuid BMU_AUID value
     */
    public void setBmuAuid(int bmuAuid) {
        bmuAuid__ = bmuAuid;
    }

    /**
     * <p>get BMU_ADATE value
     * @return BMU_ADATE value
     */
    public UDate getBmuAdate() {
        return bmuAdate__;
    }

    /**
     * <p>set BMU_ADATE value
     * @param bmuAdate BMU_ADATE value
     */
    public void setBmuAdate(UDate bmuAdate) {
        bmuAdate__ = bmuAdate;
    }

    /**
     * <p>get BMU_EUID value
     * @return BMU_EUID value
     */
    public int getBmuEuid() {
        return bmuEuid__;
    }

    /**
     * <p>set BMU_EUID value
     * @param bmuEuid BMU_EUID value
     */
    public void setBmuEuid(int bmuEuid) {
        bmuEuid__ = bmuEuid;
    }

    /**
     * <p>get BMU_EDATE value
     * @return BMU_EDATE value
     */
    public UDate getBmuEdate() {
        return bmuEdate__;
    }

    /**
     * <p>set BMU_EDATE value
     * @param bmuEdate BMU_EDATE value
     */
    public void setBmuEdate(UDate bmuEdate) {
        bmuEdate__ = bmuEdate;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(bmuSid__);
        buf.append(",");
        buf.append(NullDefault.getString(bmuUrl__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(bmuTitle__, ""));
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bmuPubDate__, ""));
        buf.append(",");
        buf.append(bmuAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bmuAdate__, ""));
        buf.append(",");
        buf.append(bmuEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(bmuEdate__, ""));
        return buf.toString();
    }
}
