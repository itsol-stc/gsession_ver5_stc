package jp.groupsession.v2.ptl.ptl030.model;

import jp.groupsession.v2.man.model.base.PtlPortalModel;

/**
 * <br>[機  能] ポータル ポータル管理画面の画面表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl030Model extends PtlPortalModel {

    /** PTL_DESCRIPTION(表示用) mapping */
    private String ptlDescriptionView__;
    /** PTS_SORT mapping */
    private int ptsSort__;
    /**
     * <p>ptlDescriptionView を取得します。
     * @return ptlDescriptionView
     */
    public String getPtlDescriptionView() {
        return ptlDescriptionView__;
    }
    /**
     * <p>ptlDescriptionView をセットします。
     * @param ptlDescriptionView ptlDescriptionView
     */
    public void setPtlDescriptionView(String ptlDescriptionView) {
        ptlDescriptionView__ = ptlDescriptionView;
    }
    /**
     * <p>ptsSort を取得します。
     * @return ptsSort
     */
    public int getPtsSort() {
        return ptsSort__;
    }
    /**
     * <p>ptsSort をセットします。
     * @param ptsSort ptsSort
     */
    public void setPtsSort(int ptsSort) {
        ptsSort__ = ptsSort;
    }
}
