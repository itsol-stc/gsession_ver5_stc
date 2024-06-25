package jp.groupsession.v2.rss.rss080;

import java.util.List;

import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.rss.model.RssModel;
import jp.groupsession.v2.rss.rss070.Rss070Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] RSSリーダー メンテナンス画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss080Form extends Rss070Form {

    /** ページコンボ上 */
    private int rss080page1__ = 0;
    /** ページコンボ下 */
    private int rss080page2__ = 0;

    /** 並び順 */
    private int rss080orderKey__ = GSConstRss.ORDER_KEY_ASC;
    /** ソートキー */
    private int rss080sortKey__ = GSConstRss.RSS_SORT_LAST_UPDATE;

    /** 開始インデックス */
    private int startIndex__ = 0;
    /** ページラベル */
    private List<LabelValueBean> pageLabelList__;
    /** 登録ランキング一覧 */
    private List<RssModel> resultList__ = null;

    /**
     * @return pageLabelList
     */
    public List<LabelValueBean> getPageLabelList() {
        return pageLabelList__;
    }
    /**
     * @param pageLabelList 設定する pageLabelList
     */
    public void setPageLabelList(List<LabelValueBean> pageLabelList) {
        pageLabelList__ = pageLabelList;
    }
    /**
     * @return resultList
     */
    public List<RssModel> getResultList() {
        return resultList__;
    }
    /**
     * @param resultList 設定する resultList
     */
    public void setResultList(List<RssModel> resultList) {
        resultList__ = resultList;
    }
    /**
     * @return rss080page1
     */
    public int getRss080page1() {
        return rss080page1__;
    }
    /**
     * @param rss080page1 設定する rss080page1
     */
    public void setRss080page1(int rss080page1) {
        rss080page1__ = rss080page1;
    }
    /**
     * @return rss080page2
     */
    public int getRss080page2() {
        return rss080page2__;
    }
    /**
     * @param rss080page2 設定する rss080page2
     */
    public void setRss080page2(int rss080page2) {
        rss080page2__ = rss080page2;
    }
    /**
     * @return startIndex
     */
    public int getStartIndex() {
        return startIndex__;
    }
    /**
     * @param startIndex 設定する startIndex
     */
    public void setStartIndex(int startIndex) {
        startIndex__ = startIndex;
    }
    /**
     * @return rss080orderKey
     */
    public int getRss080orderKey() {
        return rss080orderKey__;
    }
    /**
     * @param rss080orderKey 設定する rss080orderKey
     */
    public void setRss080orderKey(int rss080orderKey) {
        rss080orderKey__ = rss080orderKey;
    }
    /**
     * @return rss080sortKey
     */
    public int getRss080sortKey() {
        return rss080sortKey__;
    }
    /**
     * @param rss080sortKey 設定する rss080sortKey
     */
    public void setRss080sortKey(int rss080sortKey) {
        rss080sortKey__ = rss080sortKey;
    }
}