package jp.groupsession.v2.rss.rss020;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.RssValidate;
import jp.groupsession.v2.rss.dao.RssDataDao;
import jp.groupsession.v2.rss.model.RssDataModel;
import jp.groupsession.v2.rss.rss010.Rss010Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


/**
 * <p>RSSリーダー フィードURL入力画面のフォーム
 * @author JTS
 */
public class Rss020Form extends Rss010Form {

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

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param errors アクションエラー
     * @param reqMdl リクエスト情報
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(
                                  Connection con,
                                  int userSid,
                                  ActionErrors errors,
                                  RequestModel reqMdl)
    throws SQLException {
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(reqMdl);
        String textRssFeedUrl = gsMsg.getMessage("rss.feedurl");

        int errCnt = errors.size();
        //-- フィードURLチェック --
        errors = RssValidate.validateCmnFieldText(
                                                errors,
                                                textRssFeedUrl,
                                                rssFeedUrl__,
                                                "rssFeedUrl",
                                                GSConstRss.MAX_LENGTH_FEEDURL,
                                                true);

        if (errors.size() == errCnt
        && !NullDefault.getString(rssBeforeFeedUrl__, "").equals(rssFeedUrl__)) {
            //フィードURL重複チェック
            RssDataDao rssDao = new RssDataDao(con);
            RssDataModel rssMdl = rssDao.getRssDataToFeedUrl(userSid, rssFeedUrl__);
            if (rssMdl != null) {

                String textRssFeed = gsMsg.getMessage("rss.src.3");

                msg = new ActionMessage("error.input.exist.data", textRssFeed);
                StrutsUtil.addMessage(
                        errors, msg, "rssFeedUrl.error.input.exist.data");
            }
        }

        return errors;
    }

}
