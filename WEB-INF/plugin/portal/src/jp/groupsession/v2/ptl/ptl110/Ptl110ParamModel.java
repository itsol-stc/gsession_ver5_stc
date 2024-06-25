package jp.groupsession.v2.ptl.ptl110;

import java.util.List;

import jp.groupsession.v2.ptl.model.PtlPortletCategoryModel;
import jp.groupsession.v2.ptl.ptl090.Ptl090ParamModel;

/**
 * <br>[機  能] ポータル ポートレットカテゴリ一覧画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl110ParamModel extends Ptl090ParamModel {
    /** ポートレットカテゴリ一覧 */
    private List<PtlPortletCategoryModel> ptl110categoryList__;
    /** チェックされているカテゴリ */
    private String ptl110sortPltCategory__;
    /** 表示順リスト */
    private String[] ptl110categorySort__;

    /**
     * @return ptl110categoryList
     */
    public List<PtlPortletCategoryModel> getPtl110categoryList() {
        return ptl110categoryList__;
    }
    /**
     * @param ptl110categoryList セットする ptl110categoryList
     */
    public void setPtl110categoryList(
            List<PtlPortletCategoryModel> ptl110categoryList) {
        ptl110categoryList__ = ptl110categoryList;
    }
    /**
     * @return ptl110sortPltCategory
     */
    public String getPtl110sortPltCategory() {
        return ptl110sortPltCategory__;
    }
    /**
     * @param ptl110sortPltCategory セットする ptl110sortPltCategory
     */
    public void setPtl110sortPltCategory(String ptl110sortPltCategory) {
        ptl110sortPltCategory__ = ptl110sortPltCategory;
    }
    /**
     * @return ptl110categorySort
     */
    public String[] getPtl110categorySort() {
        return ptl110categorySort__;
    }
    /**
     * @param ptl110categorySort セットする ptl110categorySort
     */
    public void setPtl110categorySort(String[] ptl110categorySort) {
        ptl110categorySort__ = ptl110categorySort;
    }
}