package jp.groupsession.v2.rss.rss020;

import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.rss.rss010.Rss010ParamModel;

/**
 * <br>[機  能] RSSリーダー フィードURL入力画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss020ParamModel extends Rss010ParamModel {
    /** フィードURL */
    private String rssFeedUrl__ = null;
    /** フィードURL(前回登録時) */
    private String rssBeforeFeedUrl__ = null;
    /** RSS名称 */
    private String rssTitle__ = null;
    /** URL */
    private String rssUrl__ = null;
    /** BASIC認証 */
    private int rssAuth__ = GSConstRss.RSS_BASIC_AUTH_NOT_USE;
    /** BASIC認証ID */
    private String rssAuthId__ = null;
    /** BASIC認証パスワード */
    private String rssAuthPswd__ = null;
    /**
     * <p>rssAuth を取得します。
     * @return rssAuth
     */
    public int getRssAuth() {
        return rssAuth__;
    }

    /**
     * <p>rssAuth をセットします。
     * @param rssAuth rssAuth
     */
    public void setRssAuth(int rssAuth) {
        rssAuth__ = rssAuth;
    }

    /**
     * <p>rssAuthId を取得します。
     * @return rssAuthId
     */
    public String getRssAuthId() {
        return rssAuthId__;
    }

    /**
     * <p>rssAuthId をセットします。
     * @param rssAuthId rssAuthId
     */
    public void setRssAuthId(String rssAuthId) {
        rssAuthId__ = rssAuthId;
    }

    /**
     * <p>rssAuthPswd を取得します。
     * @return rssAuthPswd
     */
    public String getRssAuthPswd() {
        return rssAuthPswd__;
    }

    /**
     * <p>rssAuthPswd をセットします。
     * @param rssAuthPswd rssAuthPswd
     */
    public void setRssAuthPswd(String rssAuthPswd) {
        rssAuthPswd__ = rssAuthPswd;
    }

    /**
     * <p>rssTitle を取得します。
     * @return rssTitle
     */
    public String getRssTitle() {
        return rssTitle__;
    }

    /**
     * <p>rssTitle をセットします。
     * @param rssTitle rssTitle
     */
    public void setRssTitle(String rssTitle) {
        rssTitle__ = rssTitle;
    }

    /**
     * <p>rssUrl を取得します。
     * @return rssUrl
     */
    public String getRssUrl() {
        return rssUrl__;
    }

    /**
     * <p>rssUrl をセットします。
     * @param rssUrl rssUrl
     */
    public void setRssUrl(String rssUrl) {
        rssUrl__ = rssUrl;
    }

    /**
     * <p>rssFeedUrl を取得します。
     * @return rssFeedUrl
     */
    public String getRssFeedUrl() {
        return rssFeedUrl__;
    }

    /**
     * <p>rssFeedUrl をセットします。
     * @param rssFeedUrl rssFeedUrl
     */
    public void setRssFeedUrl(String rssFeedUrl) {
        rssFeedUrl__ = rssFeedUrl;
    }

    /**
     * <p>rssBeforeFeedUrl を取得します。
     * @return rssBeforeFeedUrl
     */
    public String getRssBeforeFeedUrl() {
        return rssBeforeFeedUrl__;
    }

    /**
     * <p>rssBeforeFeedUrl をセットします。
     * @param rssBeforeFeedUrl rssBeforeFeedUrl
     */
    public void setRssBeforeFeedUrl(String rssBeforeFeedUrl) {
        rssBeforeFeedUrl__ = rssBeforeFeedUrl;
    }
}