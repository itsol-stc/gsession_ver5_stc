package jp.groupsession.v2.rss.rss090;

import java.util.List;

import jp.groupsession.v2.rss.model.RssModel;
import jp.groupsession.v2.rss.rss080.Rss080Form;

/**
 * <br>[機  能] RSSリーダー RSS購読メンバー一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss090Form extends Rss080Form {

    /** ページコンボ上 */
    private int rss090page1__ = 0;
    /** ページコンボ下 */
    private int rss090page2__ = 0;
    /** RSS登録ユーザリスト */
    private List<RssModel> userDataList__ = null;

    /**
     * @return userDataList
     */
    public List<RssModel> getUserDataList() {
        return userDataList__;
    }
    /**
     * @param userDataList セットする userDataList
     */
    public void setUserDataList(List<RssModel> userDataList) {
        userDataList__ = userDataList;
    }
    /**
     * @return rss090page1
     */
    public int getRss090page1() {
        return rss090page1__;
    }
    /**
     * @param rss090page1 設定する rss090page1
     */
    public void setRss090page1(int rss090page1) {
        rss090page1__ = rss090page1;
    }
    /**
     * @return rss090page2
     */
    public int getRss090page2() {
        return rss090page2__;
    }
    /**
     * @param rss090page2 設定する rss090page2
     */
    public void setRss090page2(int rss090page2) {
        rss090page2__ = rss090page2;
    }
}