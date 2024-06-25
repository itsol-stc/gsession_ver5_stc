package jp.groupsession.v2.bmk.bmk080;

import java.util.List;

import jp.groupsession.v2.bmk.bmk010.Bmk010Form;
import jp.groupsession.v2.bmk.model.BmkUrlDataModel;

import org.apache.struts.util.LabelValueBean;


/**
 * <p>登録ランキング画面のフォーム
 * @author JTS
 */
public class Bmk080Form extends Bmk010Form {
    /** ページ */
    private int bmk080Page__;
    /** ページ上段 */
    private int bmk080PageTop__;
    /** ページ下段 */
    private int bmk080PageBottom__;
    /** ページラベル */
    private List<LabelValueBean> bmk080PageLabelList__;
    /** 登録ランキング一覧 */
    private List<BmkUrlDataModel> bmk080ResultList__ = null;

    /**
     * <p>bmk080Page を取得します。
     * @return bmk080Page
     */
    public int getBmk080Page() {
        return bmk080Page__;
    }
    /**
     * <p>bmk080Page をセットします。
     * @param bmk080Page bmk080Page
     */
    public void setBmk080Page(int bmk080Page) {
        bmk080Page__ = bmk080Page;
    }
    /**
     * <p>bmk080PageBottom を取得します。
     * @return bmk080PageBottom
     */
    public int getBmk080PageBottom() {
        return bmk080PageBottom__;
    }
    /**
     * <p>bmk080PageBottom をセットします。
     * @param bmk080PageBottom bmk080PageBottom
     */
    public void setBmk080PageBottom(int bmk080PageBottom) {
        bmk080PageBottom__ = bmk080PageBottom;
    }
    /**
     * <p>bmk080PageTop を取得します。
     * @return bmk080PageTop
     */
    public int getBmk080PageTop() {
        return bmk080PageTop__;
    }
    /**
     * <p>bmk080PageTop をセットします。
     * @param bmk080PageTop bmk080PageTop
     */
    public void setBmk080PageTop(int bmk080PageTop) {
        bmk080PageTop__ = bmk080PageTop;
    }
    /**
     * <p>bmk080PageLabelList を取得します。
     * @return bmk080PageLabelList
     */
    public List<LabelValueBean> getBmk080PageLabelList() {
        return bmk080PageLabelList__;
    }
    /**
     * <p>bmk080PageLabelList をセットします。
     * @param bmk080PageLabelList bmk080PageLabelList
     */
    public void setBmk080PageLabelList(List<LabelValueBean> bmk080PageLabelList) {
        bmk080PageLabelList__ = bmk080PageLabelList;
    }
    /**
     * <p>bmk080ResultList を取得します。
     * @return bmk080ResultList
     */
    public List<BmkUrlDataModel> getBmk080ResultList() {
        return bmk080ResultList__;
    }
    /**
     * <p>bmk080ResultList をセットします。
     * @param bmk080ResultList bmk080ResultList
     */
    public void setBmk080ResultList(List<BmkUrlDataModel> bmk080ResultList) {
        bmk080ResultList__ = bmk080ResultList;
    }
}
