package jp.groupsession.v2.rss.rss060;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.rss.rss050.Rss050Form;

/**
 * <br>[機  能] RSSリーダー メイン画面表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss060Form extends Rss050Form {

    /** RSS情報一覧 */
    List<GSFeedList> rss060Flist__ = new ArrayList<GSFeedList>();
    /** RSSSID一覧(左) */
    String[] rss060SidLeft__ = null;
    /** RSSSID一覧(右) */
    String[] rss060SidRight__ = null;

    /** 更新用RSSID一覧 */
    List<String> rss060SidUpdate__ = null;

    /** RSSSID */
    int rssSid__ = 0;
    /** RSSタイトル */
    private String rssTitle__;
    /** 処理モード */
    int rssCmdMode__ = 0;

    /**
     * <p>rss060Flist を取得します。
     * @return rss060Flist
     */
    public List<GSFeedList> getRss060Flist() {
        return rss060Flist__;
    }

    /**
     * <p>rss060Flist をセットします。
     * @param rss060Flist rss060Flist
     */
    public void setRss060Flist(List<GSFeedList> rss060Flist) {
        rss060Flist__ = rss060Flist;
    }

    /**
     * <p>rss060SidLeft を取得します。
     * @return rss060SidLeft
     */
    public String[] getRss060SidLeft() {
        return rss060SidLeft__;
    }

    /**
     * <p>rss060SidLeft をセットします。
     * @param rss060SidLeft rss060SidLeft
     */
    public void setRss060SidLeft(String[] rss060SidLeft) {
        rss060SidLeft__ = rss060SidLeft;
    }

    /**
     * <p>rss060SidRight を取得します。
     * @return rss060SidRight
     */
    public String[] getRss060SidRight() {
        return rss060SidRight__;
    }

    /**
     * <p>rss060SidRight をセットします。
     * @param rss060SidRight rss060SidRight
     */
    public void setRss060SidRight(String[] rss060SidRight) {
        rss060SidRight__ = rss060SidRight;
    }

    /**
     * <p>rssCmdMode を取得します。
     * @return rssCmdMode
     */
    public int getRssCmdMode() {
        return rssCmdMode__;
    }

    /**
     * <p>rssCmdMode をセットします。
     * @param rssCmdMode rssCmdMode
     */
    public void setRssCmdMode(int rssCmdMode) {
        rssCmdMode__ = rssCmdMode;
    }

    /**
     * <p>rssSid を取得します。
     * @return rssSid
     */
    public int getRssSid() {
        return rssSid__;
    }

    /**
     * <p>rssSid をセットします。
     * @param rssSid rssSid
     */
    public void setRssSid(int rssSid) {
        rssSid__ = rssSid;
    }

    /**
     * <p>rss060SidUpdate を取得します。
     * @return rss060SidUpdate
     */
    public List<String> getRss060SidUpdate() {
        return rss060SidUpdate__;
    }

    /**
     * <p>rss060SidUpdate をセットします。
     * @param rss060SidUpdate rss060SidUpdate
     */
    public void setRss060SidUpdate(List<String> rss060SidUpdate) {
        rss060SidUpdate__ = rss060SidUpdate;
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