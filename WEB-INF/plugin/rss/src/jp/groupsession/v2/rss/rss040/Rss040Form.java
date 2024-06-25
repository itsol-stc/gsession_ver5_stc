package jp.groupsession.v2.rss.rss040;

import java.util.List;

import jp.groupsession.v2.rss.model.RssModel;
import jp.groupsession.v2.rss.rss010.Rss010Form;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] RSSリーダー 登録ランキング画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss040Form extends Rss010Form {
    /** ページコンボ上 */
    private int rss040page1__ = 0;
    /** ページコンボ下 */
    private int rss040page2__ = 0;
    /** フィード名称 */
    private String rss040feedTitle__ = null;

    /** 開始インデックス */
    private int startIndex__ = 0;
    /** ページラベル */
    private List<LabelValueBean> pageLabelList__;
    /** 登録ランキング一覧 */
    private List<RssModel> resultList__ = null;

    /**
     * <p>rss040feedTitle を取得します。
     * @return rss040feedTitle
     */
    public String getRss040feedTitle() {
        return rss040feedTitle__;
    }
    /**
     * <p>rss040feedTitle をセットします。
     * @param rss040feedTitle rss040feedTitle
     */
    public void setRss040feedTitle(String rss040feedTitle) {
        rss040feedTitle__ = rss040feedTitle;
    }
    /**
     * <p>rss040page1 を取得します。
     * @return rss040page1
     */
    public int getRss040page1() {
        return rss040page1__;
    }
    /**
     * <p>rss040page1 をセットします。
     * @param rss040page1 rss040page1
     */
    public void setRss040page1(int rss040page1) {
        rss040page1__ = rss040page1;
    }
    /**
     * <p>rss040page2 を取得します。
     * @return rss040page2
     */
    public int getRss040page2() {
        return rss040page2__;
    }
    /**
     * <p>rss040page2 をセットします。
     * @param rss040page2 rss040page2
     */
    public void setRss040page2(int rss040page2) {
        rss040page2__ = rss040page2;
    }
    /**
     * <p>pageLabelList を取得します。
     * @return pageLabelList
     */
    public List<LabelValueBean> getPageLabelList() {
        return pageLabelList__;
    }
    /**
     * <p>pageLabelList をセットします。
     * @param pageLabelList pageLabelList
     */
    public void setPageLabelList(List<LabelValueBean> pageLabelList) {
        pageLabelList__ = pageLabelList;
    }
    /**
     * <p>resultList を取得します。
     * @return resultList
     */
    public List<RssModel> getResultList() {
        return resultList__;
    }
    /**
     * <p>resultList をセットします。
     * @param resultList resultList
     */
    public void setResultList(List<RssModel> resultList) {
        resultList__ = resultList;
    }
    /**
     * <p>startIndex を取得します。
     * @return startIndex
     */
    public int getStartIndex() {
        return startIndex__;
    }
    /**
     * <p>startIndex をセットします。
     * @param startIndex startIndex
     */
    public void setStartIndex(int startIndex) {
        startIndex__ = startIndex;
    }

}
