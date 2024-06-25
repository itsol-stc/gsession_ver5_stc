package jp.groupsession.v2.rss.rss030;

import java.util.List;

import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.rss.rss020.Rss020ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] RSSリーダー RSS登録画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss030ParamModel extends Rss020ParamModel {
    /** 表示件数 */
    private int rss030ViewCnt__ = GSConstRss.RSS_DEFAULT_VIEWCNT;
    /** メイン表示 */
    private int rss030mainView__ = GSConstRss.RSS_MAIN_VIEWFLG_SHOW;

    /** 表示件数コンボ */
    private List<LabelValueBean> viewCntList__ = null;
    /** ヘルプモード */
    private String helpMode__ = GSConstRss.RSSHELPMODE_ADD;
    /**
     * <p>rss030ViewCnt を取得します。
     * @return rss030ViewCnt
     */
    public int getRss030ViewCnt() {
        return rss030ViewCnt__;
    }

    /**
     * <p>rss030ViewCnt をセットします。
     * @param rss030ViewCnt rss030ViewCnt
     */
    public void setRss030ViewCnt(int rss030ViewCnt) {
        rss030ViewCnt__ = rss030ViewCnt;
    }

    /**
     * <p>viewCntList を取得します。
     * @return viewCntList
     */
    public List<LabelValueBean> getViewCntList() {
        return viewCntList__;
    }

    /**
     * <p>viewCntList をセットします。
     * @param viewCntList viewCntList
     */
    public void setViewCntList(List<LabelValueBean> viewCntList) {
        viewCntList__ = viewCntList;
    }

    /**
     * <p>rss030mainView を取得します。
     * @return rss030mainView
     */
    public int getRss030mainView() {
        return rss030mainView__;
    }

    /**
     * <p>rss030mainView をセットします。
     * @param rss030mainView rss030mainView
     */
    public void setRss030mainView(int rss030mainView) {
        rss030mainView__ = rss030mainView;
    }

    /**
     * <p>helpMode を取得します。
     * @return helpMode
     */
    public String getHelpMode() {
        return helpMode__;
    }

    /**
     * <p>helpMode をセットします。
     * @param helpMode helpMode
     */
    public void setHelpMode(String helpMode) {
        helpMode__ = helpMode;
    }
}