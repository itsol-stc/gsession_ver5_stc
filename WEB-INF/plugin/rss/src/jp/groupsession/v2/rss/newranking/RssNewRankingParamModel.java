package jp.groupsession.v2.rss.newranking;

import java.util.List;

import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.model.base.RssInfomModel;

/**
 * <br>[機  能] RSSリーダー 新着RSS(メイン画面表示用)の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssNewRankingParamModel extends AbstractParamModel {
    /** 新着ランキング一覧 */
    List<RssInfomModel> newRankingList__ = null;

    /** RSSSID */
    private String rssSid__ = "";
    /** RSSタイトル */
    private String rssTitle__;
    /** フィードリスト */
    List<GSFeedList> flist__;
    /** RSSトップ画面URL */
    private String  rssTopUrl__;

    /**
     * @return rssTopUrl__ を戻します。
     */
    public String getRssTopUrl() {
        return rssTopUrl__;
    }
    /**
     * @param rssTopUrl 設定する rssTopUrl__。
     */
    public void setRssTopUrl(String rssTopUrl) {
        rssTopUrl__ = rssTopUrl;
    }

    /**
     * <p>rssSid を取得します。
     * @return rssSid
     */
    public String getRssSid() {
        return rssSid__;
    }

    /**
     * <p>rssSid をセットします。
     * @param rssSid rssSid
     */
    public void setRssSid(String rssSid) {
        rssSid__ = rssSid;
    }

    /**
     * <p>newRankingList を取得します。
     * @return newRankingList
     */
    public List<RssInfomModel> getNewRankingList() {
        return newRankingList__;
    }

    /**
     * <p>newRankingList をセットします。
     * @param newRankingList newRankingList
     */
    public void setNewRankingList(List<RssInfomModel> newRankingList) {
        newRankingList__ = newRankingList;
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
}