package jp.groupsession.v2.rss.rss010;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.model.base.RssInfomModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rss.model.RssModel;

/**
 * <br>[機  能] RSSリーダー一覧画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss010ParamModel extends AbstractParamModel {
    /** RSS情報一覧 */
    List<GSFeedList> rss010Flist__ = new ArrayList<GSFeedList>();
    /** RSSSID一覧(左) */
    String[] rss010SidLeft__ = null;
    /** RSSSID一覧(右) */
    String[] rss010SidRight__ = null;

    /** 登録ランキング一覧 */
    private List<RssModel> rankingList__ = null;
    /** 新着ランキング一覧 */
    List<RssInfomModel> newRankingList__ = null;

    /** 更新用RSSID一覧 */
    List<String> rss010SidUpdate__ = null;

    /** RSSSID */
    int rssSid__ = 0;
    /** RSSタイトル */
    private String rssTitle__;
    /** 処理モード */
    int rssCmdMode__ = 0;

    /** 遷移元 メイン個人設定メニュー:2 メイン管理者設定メニュー:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** 管理者ボタン表示フラグ */
    private int rss010viewAdminBtn__ = 0;

    /**
     * <p>rss010Flist を取得します。
     * @return rss010Flist
     */
    public List<GSFeedList> getRss010Flist() {
        return rss010Flist__;
    }

    /**
     * <p>rss010Flist をセットします。
     * @param rss010Flist rss010Flist
     */
    public void setRss010Flist(List<GSFeedList> rss010Flist) {
        rss010Flist__ = rss010Flist;
    }

    /**
     * <p>rss010SidLeft を取得します。
     * @return rss010SidLeft
     */
    public String[] getRss010SidLeft() {
        return rss010SidLeft__;
    }

    /**
     * <p>rss010SidLeft をセットします。
     * @param rss010SidLeft rss010SidLeft
     */
    public void setRss010SidLeft(String[] rss010SidLeft) {
        rss010SidLeft__ = rss010SidLeft;
    }

    /**
     * <p>rss010SidRight を取得します。
     * @return rss010SidRight
     */
    public String[] getRss010SidRight() {
        return rss010SidRight__;
    }

    /**
     * <p>rss010SidRight をセットします。
     * @param rss010SidRight rss010SidRight
     */
    public void setRss010SidRight(String[] rss010SidRight) {
        rss010SidRight__ = rss010SidRight;
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
     * <p>rss010SidUpdate を取得します。
     * @return rss010SidUpdate
     */
    public List<String> getRss010SidUpdate() {
        return rss010SidUpdate__;
    }

    /**
     * <p>rss010SidUpdate をセットします。
     * @param rss010SidUpdate rss010SidUpdate
     */
    public void setRss010SidUpdate(List<String> rss010SidUpdate) {
        rss010SidUpdate__ = rss010SidUpdate;
    }

    /**
     * <p>rankingList を取得します。
     * @return rankingList
     */
    public List<RssModel> getRankingList() {
        return rankingList__;
    }

    /**
     * <p>rankingList をセットします。
     * @param rankingList rankingList
     */
    public void setRankingList(List<RssModel> rankingList) {
        rankingList__ = rankingList;
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

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }

    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }

    /**
     * @return rss010viewAdminBtn
     */
    public int getRss010viewAdminBtn() {
        return rss010viewAdminBtn__;
    }

    /**
     * @param rss010viewAdminBtn 設定する rss010viewAdminBtn
     */
    public void setRss010viewAdminBtn(int rss010viewAdminBtn) {
        rss010viewAdminBtn__ = rss010viewAdminBtn;
    }
}