package jp.groupsession.v2.ptl.ptl081;

import java.util.List;

import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.ptl.ptl080.Ptl080Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ポータル ポートレット選択画面(ポップアップ)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl081Form extends Ptl080Form {

    /** 選択カテゴリID */
    private int ptl081Category__ = -1;
    /** カテゴリリスト */
    private List<LabelValueBean> ptl081CategoryList__ = null;

    /** 画面表示一覧 */
    private List<PtlPortletModel> ptl081dspList__ = null;
    /** ポートレットSID */
    private int ptl081selectPortletSid__ = -1;
    /** プラグイン選択済フラグ */
    private boolean ptl081selectFlg__ = false;

    /**
     * <p>ptl081Category を取得します。
     * @return ptl081Category
     */
    public int getPtl081Category() {
        return ptl081Category__;
    }
    /**
     * <p>ptl081Category をセットします。
     * @param ptl081Category ptl081Category
     */
    public void setPtl081Category(int ptl081Category) {
        ptl081Category__ = ptl081Category;
    }
    /**
     * <p>ptl081CategoryList を取得します。
     * @return ptl081CategoryList
     */
    public List<LabelValueBean> getPtl081CategoryList() {
        return ptl081CategoryList__;
    }
    /**
     * <p>ptl081CategoryList をセットします。
     * @param ptl081CategoryList ptl081CategoryList
     */
    public void setPtl081CategoryList(List<LabelValueBean> ptl081CategoryList) {
        ptl081CategoryList__ = ptl081CategoryList;
    }
    /**
     * <p>ptl081dspList を取得します。
     * @return ptl081dspList
     */
    public List<PtlPortletModel> getPtl081dspList() {
        return ptl081dspList__;
    }
    /**
     * <p>ptl081dspList をセットします。
     * @param ptl081dspList ptl081dspList
     */
    public void setPtl081dspList(List<PtlPortletModel> ptl081dspList) {
        ptl081dspList__ = ptl081dspList;
    }
    /**
     * <p>ptl081selectFlg を取得します。
     * @return ptl081selectFlg
     */
    public boolean isPtl081selectFlg() {
        return ptl081selectFlg__;
    }
    /**
     * <p>ptl081selectFlg をセットします。
     * @param ptl081selectFlg ptl081selectFlg
     */
    public void setPtl081selectFlg(boolean ptl081selectFlg) {
        ptl081selectFlg__ = ptl081selectFlg;
    }
    /**
     * <p>ptl081selectPortletSid を取得します。
     * @return ptl081selectPortletSid
     */
    public int getPtl081selectPortletSid() {
        return ptl081selectPortletSid__;
    }
    /**
     * <p>ptl081selectPortletSid をセットします。
     * @param ptl081selectPortletSid ptl081selectPortletSid
     */
    public void setPtl081selectPortletSid(int ptl081selectPortletSid) {
        ptl081selectPortletSid__ = ptl081selectPortletSid;
    }

}
