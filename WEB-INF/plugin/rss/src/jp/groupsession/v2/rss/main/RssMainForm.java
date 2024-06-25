package jp.groupsession.v2.rss.main;

import java.util.List;

import jp.groupsession.v2.cmn.GSFeedList;
import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] RSSリーダー(メイン画面表示用)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RssMainForm extends AbstractGsForm {

    /** 更新用RSSID一覧 */
    List<String> sidUpdate__;
    /** RSS情報一覧 */
    List<GSFeedList> flist__;

    /**
     * <p>sidUpdate を取得します。
     * @return sidUpdate
     */
    public List<String> getSidUpdate() {
        return sidUpdate__;
    }
    /**
     * <p>sidUpdate をセットします。
     * @param sidUpdate sidUpdate
     */
    public void setSidUpdate(List<String> sidUpdate) {
        sidUpdate__ = sidUpdate;
    }
    /**
     * <p>flist を取得します。
     * @return flist
     */
    public List<GSFeedList> getFlist() {
        return flist__;
    }
    /**
     * <p>flist をセットします。
     * @param flist flist
     */
    public void setFlist(List<GSFeedList> flist) {
        flist__ = flist;
    }

}
