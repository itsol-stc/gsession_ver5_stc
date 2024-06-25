package jp.groupsession.v2.ptl.ptl090;

import java.util.ArrayList;

import jp.groupsession.v2.ptl.model.PtlPortletModel;
import jp.groupsession.v2.ptl.ptl020.Ptl020ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ポータル ポートレット管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl090ParamModel extends Ptl020ParamModel {
    /** 選択したポートレットSID */
    private int ptlPortletSid__ = -1;
    /** 並び替え対象のポートレット */
    private String ptl090sortPortlet__ = null;
    /** カテゴリ */
    private int ptl090category__ = -1;

    /** カテゴリ(検索条件保持) */
    private int ptl090svCategory__ = -1;
    /** ポートレットSID:ソート順 文字列 */
    private String[] arrayPtl090sortPortlet__ = null;

    /** ポートレットJSP表示用モデル */
    ArrayList<PtlPortletModel> ptl090portletlist__ = null;

    /** カテゴリコンボボックス */
    ArrayList<LabelValueBean> ptl090CategoryList__ = null;

    /**
     * @return ptlPortletSid
     */
    public int getPtlPortletSid() {
        return ptlPortletSid__;
    }

    /**
     * @param ptlPortletSid セットする ptlPortletSid
     */
    public void setPtlPortletSid(int ptlPortletSid) {
        ptlPortletSid__ = ptlPortletSid;
    }

    /**
     * @return ptl090sortPortlet
     */
    public String getPtl090sortPortlet() {
        return ptl090sortPortlet__;
    }

    /**
     * @param ptl090sortPortlet セットする ptl090sortPortlet
     */
    public void setPtl090sortPortlet(String ptl090sortPortlet) {
        ptl090sortPortlet__ = ptl090sortPortlet;
    }

    /**
     * @return ptl090portletlist
     */
    public ArrayList<PtlPortletModel> getPtl090portletlist() {
        return ptl090portletlist__;
    }

    /**
     * @param ptl090portletlist セットする ptl090portletlist
     */
    public void setPtl090portletlist(ArrayList<PtlPortletModel> ptl090portletlist) {
        ptl090portletlist__ = ptl090portletlist;
    }

    /**
     * @return ptl090CategoryList
     */
    public ArrayList<LabelValueBean> getPtl090CategoryList() {
        return ptl090CategoryList__;
    }

    /**
     * @param ptl090CategoryList セットする ptl090CategoryList
     */
    public void setPtl090CategoryList(ArrayList<LabelValueBean> ptl090CategoryList) {
        ptl090CategoryList__ = ptl090CategoryList;
    }

    /**
     * @return ptl090category
     */
    public int getPtl090category() {
        return ptl090category__;
    }

    /**
     * @param ptl090category セットする ptl090category
     */
    public void setPtl090category(int ptl090category) {
        ptl090category__ = ptl090category;
    }

    /**
     * @return ptl090svCategory
     */
    public int getPtl090svCategory() {
        return ptl090svCategory__;
    }

    /**
     * @param ptl090svCategory セットする ptl090svCategory
     */
    public void setPtl090svCategory(int ptl090svCategory) {
        ptl090svCategory__ = ptl090svCategory;
    }

    /**
     * 検索条件パラメータをSAVEフィールドへ移行します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     */
    public void saveSearchParm() {
        ptl090svCategory__ = ptl090category__;
    }

    /**
     * @return arrayPtl090sortPortlet
     */
    public String[] getArrayPtl090sortPortlet() {
        return arrayPtl090sortPortlet__;
    }

    /**
     * @param arrayPtl090sortPortlet セットする arrayPtl090sortPortlet
     */
    public void setArrayPtl090sortPortlet(String[] arrayPtl090sortPortlet) {
        arrayPtl090sortPortlet__ = arrayPtl090sortPortlet;
    }
}