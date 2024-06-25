package jp.groupsession.v2.rss.rss100;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rss.rss050.Rss050ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] RSSリーダー 新着RSS表示日数設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rss100ParamModel extends Rss050ParamModel {
    /** ページコンボ上 */
    private int rss100newCnt__ = 0;
    /** RSS登録ユーザリスト */
    private List<LabelValueBean> rss100newCntLabel__ = null;
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

    /**
     * <br>[機  能] 新着RSS表示日数コンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void setNewCntLabel(RequestModel reqMdl) {
        rss100newCntLabel__ = new ArrayList < LabelValueBean >();
        GsMessage gsMsg = new GsMessage(reqMdl);
        for (String label : Rss100Form.NEWCNTVALUE) {
            rss100newCntLabel__.add(
                    new LabelValueBean(
                            gsMsg.getMessage("cmn.less.than.day", new String[] {label}), label));
        }
    }
}