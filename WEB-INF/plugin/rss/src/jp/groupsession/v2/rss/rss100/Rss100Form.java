package jp.groupsession.v2.rss.rss100;

import java.util.List;

import jp.groupsession.v2.rss.rss050.Rss050Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] RSSリーダー 新着RSS表示日数設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss100Form extends Rss050Form {
    /** 新着RSS表示日数の選択値 */
    public static final String[] NEWCNTVALUE
        = new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

    /** ページコンボ上 */
    private int rss100newCnt__ = 0;
    /** RSS登録ユーザリスト */
    private List<LabelValueBean> rss100newCntLabel__ = null;

    /**
     * コンストラクタ
     */
    public Rss100Form() {
    }

    /**
     * <p>rss100newCnt を取得します。
     * @return rss100newCnt
     */
    public int getRss100newCnt() {
        return rss100newCnt__;
    }
    /**
     * <p>rss100newCnt をセットします。
     * @param rss100newCnt rss100newCnt
     */
    public void setRss100newCnt(int rss100newCnt) {
        rss100newCnt__ = rss100newCnt;
    }
    /**
     * <p>rss100newCntLabel を取得します。
     * @return rss100newCntLabel
     */
    public List<LabelValueBean> getRss100newCntLabel() {
        return rss100newCntLabel__;
    }
    /**
     * <p>rss100newCntLabel をセットします。
     * @param rss100newCntLabel rss100newCntLabel
     */
    public void setRss100newCntLabel(List<LabelValueBean> rss100newCntLabel) {
        rss100newCntLabel__ = rss100newCntLabel;
    }
}