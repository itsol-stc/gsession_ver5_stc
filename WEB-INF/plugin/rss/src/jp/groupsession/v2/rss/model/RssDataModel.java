package jp.groupsession.v2.rss.model;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.NullDefault;
import java.io.Serializable;

/**
 * <p>RSS_DATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssDataModel implements Serializable {

    /** RSS_SID mapping */
    private int rssSid__;
    /** USR_SID mapping */
    private int usrSid__;
    /** RSD_TITLE mapping */
    private String rsdTitle__;
    /** RSD_URL mapping */
    private String rsdUrl__;
    /** RSD_VIEW mapping */
    private int rsdView__;
    /** RSD_AUID mapping */
    private int rsdAuid__;
    /** RSD_ADATE mapping */
    private UDate rsdAdate__;
    /** RSD_EUID mapping */
    private int rsdEuid__;
    /** RSD_EDATE mapping */
    private UDate rsdEdate__;
    /** RSD_URL_FEED mapping */
    private String rsdUrlFeed__;
    /** RSD_FEED_COUNT mapping */
    private int rsdFeedCount__;
    /** RSD_MAIN_VIEW mapping */
    private int rsdMainView__;
    /** RSD_AUTH mapping */
    private int rsdAuth__;
    /** RSD_AUTH_ID mapping */
    private String rsdAuthId__;
    /** RSD_AUTH_PAWD mapping */
    private String rsdAuthPswd__;

    /**
     * <p>Default Constructor
     */
    public RssDataModel() {
    }

    /**
     * <p>get RSS_SID value
     * @return RSS_SID value
     */
    public int getRssSid() {
        return rssSid__;
    }

    /**
     * <p>set RSS_SID value
     * @param rssSid RSS_SID value
     */
    public void setRssSid(int rssSid) {
        rssSid__ = rssSid;
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get RSD_TITLE value
     * @return RSD_TITLE value
     */
    public String getRsdTitle() {
        return rsdTitle__;
    }

    /**
     * <p>set RSD_TITLE value
     * @param rsdTitle RSD_TITLE value
     */
    public void setRsdTitle(String rsdTitle) {
        rsdTitle__ = rsdTitle;
    }

    /**
     * <p>get RSD_URL value
     * @return RSD_URL value
     */
    public String getRsdUrl() {
        return rsdUrl__;
    }

    /**
     * <p>set RSD_URL value
     * @param rsdUrl RSD_URL value
     */
    public void setRsdUrl(String rsdUrl) {
        rsdUrl__ = rsdUrl;
    }

    /**
     * <p>get RSD_VIEW value
     * @return RSD_VIEW value
     */
    public int getRsdView() {
        return rsdView__;
    }

    /**
     * <p>set RSD_VIEW value
     * @param rsdView RSD_VIEW value
     */
    public void setRsdView(int rsdView) {
        rsdView__ = rsdView;
    }

    /**
     * <p>get RSD_AUID value
     * @return RSD_AUID value
     */
    public int getRsdAuid() {
        return rsdAuid__;
    }

    /**
     * <p>set RSD_AUID value
     * @param rsdAuid RSD_AUID value
     */
    public void setRsdAuid(int rsdAuid) {
        rsdAuid__ = rsdAuid;
    }

    /**
     * <p>get RSD_ADATE value
     * @return RSD_ADATE value
     */
    public UDate getRsdAdate() {
        return rsdAdate__;
    }

    /**
     * <p>set RSD_ADATE value
     * @param rsdAdate RSD_ADATE value
     */
    public void setRsdAdate(UDate rsdAdate) {
        rsdAdate__ = rsdAdate;
    }

    /**
     * <p>get RSD_EUID value
     * @return RSD_EUID value
     */
    public int getRsdEuid() {
        return rsdEuid__;
    }

    /**
     * <p>set RSD_EUID value
     * @param rsdEuid RSD_EUID value
     */
    public void setRsdEuid(int rsdEuid) {
        rsdEuid__ = rsdEuid;
    }

    /**
     * <p>get RSD_EDATE value
     * @return RSD_EDATE value
     */
    public UDate getRsdEdate() {
        return rsdEdate__;
    }

    /**
     * <p>set RSD_EDATE value
     * @param rsdEdate RSD_EDATE value
     */
    public void setRsdEdate(UDate rsdEdate) {
        rsdEdate__ = rsdEdate;
    }

    /**
     * <p>get RSD_URL_FEED value
     * @return RSD_URL_FEED value
     */
    public String getRsdUrlFeed() {
        return rsdUrlFeed__;
    }

    /**
     * <p>set RSD_URL_FEED value
     * @param rsdUrlFeed RSD_URL_FEED value
     */
    public void setRsdUrlFeed(String rsdUrlFeed) {
        rsdUrlFeed__ = rsdUrlFeed;
    }

    /**
     * <p>get RSD_FEED_COUNT value
     * @return RSD_FEED_COUNT value
     */
    public int getRsdFeedCount() {
        return rsdFeedCount__;
    }

    /**
     * <p>set RSD_FEED_COUNT value
     * @param rsdFeedCount RSD_FEED_COUNT value
     */
    public void setRsdFeedCount(int rsdFeedCount) {
        rsdFeedCount__ = rsdFeedCount;
    }

    /**
     * <p>rsdMainView を取得します。
     * @return rsdMainView
     */
    public int getRsdMainView() {
        return rsdMainView__;
    }

    /**
     * <p>rsdMainView をセットします。
     * @param rsdMainView rsdMainView
     */
    public void setRsdMainView(int rsdMainView) {
        rsdMainView__ = rsdMainView;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(rssSid__);
        buf.append(",");
        buf.append(usrSid__);
        buf.append(",");
        buf.append(NullDefault.getString(rsdTitle__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(rsdUrl__, ""));
        buf.append(",");
        buf.append(rsdView__);
        buf.append(",");
        buf.append(rsdAuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rsdAdate__, ""));
        buf.append(",");
        buf.append(rsdEuid__);
        buf.append(",");
        buf.append(NullDefault.getStringFmObj(rsdEdate__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(rsdUrlFeed__, ""));
        buf.append(",");
        buf.append(rsdFeedCount__);
        return buf.toString();
    }

    /**
     * <p>rsdAuth を取得します。
     * @return rsdAuth
     */
    public int getRsdAuth() {
        return rsdAuth__;
    }

    /**
     * <p>rsdAuth をセットします。
     * @param rsdAuth rsdAuth
     */
    public void setRsdAuth(int rsdAuth) {
        rsdAuth__ = rsdAuth;
    }

    /**
     * <p>rsdAuthId を取得します。
     * @return rsdAuthId
     */
    public String getRsdAuthId() {
        return rsdAuthId__;
    }

    /**
     * <p>rsdAuthId をセットします。
     * @param rsdAuthId rsdAuthId
     */
    public void setRsdAuthId(String rsdAuthId) {
        rsdAuthId__ = rsdAuthId;
    }

    /**
     * <p>rsdAuthPswd を取得します。
     * @return rsdAuthPswd
     */
    public String getRsdAuthPswd() {
        return rsdAuthPswd__;
    }

    /**
     * <p>rsdAuthPswd をセットします。
     * @param rsdAuthPswd rsdAuthPswd
     */
    public void setRsdAuthPswd(String rsdAuthPswd) {
        rsdAuthPswd__ = rsdAuthPswd;
    }

}
