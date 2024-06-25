package jp.groupsession.v2.ptl.ptl140;

import java.util.ArrayList;

import jp.groupsession.v2.man.model.base.PtlPortalModel;
import jp.groupsession.v2.ptl.AbstractPtlForm;

/**
 * <br>[機  能] ポータル 個人設定 ポータル管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl140Form extends AbstractPtlForm {

    /** 並び替え対象のポータル */
    private String ptl140sortPortal__ = null;
    /** ポータルSID:ソート順 文字列 */
    private String[] arrayPtl140sortPortal__ = null;

    /** ポートレットJSP表示用モデル */
    ArrayList<PtlPortalModel> ptl140portalList__ = null;

    /** ポータルデフォルト表示区分 0=最初のタブ 1=最後に開いたタブ */
    private int ptlType__ = 0;
    /** ポータルデフォルト表示 0=管理者のみ設定可 1=制限なし */
    private int ptlDefPow__ = 0;
    /** ポータルソート設定 0=管理者のみ設定可 1=制限なし */
    private int ptlSortPow__ = 0;

    /**
     * @return ptl140sortPortal
     */
    public String getPtl140sortPortal() {
        return ptl140sortPortal__;
    }

    /**
     * @param ptl140sortPortal セットする ptl140sortPortal
     */
    public void setPtl140sortPortal(String ptl140sortPortal) {
        ptl140sortPortal__ = ptl140sortPortal;
    }

    /**
     * @return arrayPtl140sortPortal
     */
    public String[] getArrayPtl140sortPortal() {
        return arrayPtl140sortPortal__;
    }

    /**
     * @param arrayPtl140sortPortal セットする arrayPtl140sortPortal
     */
    public void setArrayPtl140sortPortal(String[] arrayPtl140sortPortal) {
        arrayPtl140sortPortal__ = arrayPtl140sortPortal;
    }

    /**
     * @return ptl140portalList
     */
    public ArrayList<PtlPortalModel> getPtl140portalList() {
        return ptl140portalList__;
    }

    /**
     * @param ptl140portalList セットする ptl140portalList
     */
    public void setPtl140portalList(ArrayList<PtlPortalModel> ptl140portalList) {
        ptl140portalList__ = ptl140portalList;
    }
    /**
     * @return ptlType
     */
    public int getPtlType() {
        return ptlType__;
    }

    /**
     * @param ptlType セットする ptlType
     */
    public void setPtlType(int ptlType) {
        ptlType__ = ptlType;
    }

    /**
     * @return ptlDefPow
     */
    public int getPtlDefPow() {
        return ptlDefPow__;
    }

    /**
     * @param ptlDefPow セットする ptlDefPow
     */
    public void setPtlDefPow(int ptlDefPow) {
        ptlDefPow__ = ptlDefPow;
    }

    /**
     * <p>ptlSortPow を取得します。
     * @return ptlSortPow
     */
    public int getPtlSortPow() {
        return ptlSortPow__;
    }

    /**
     * <p>ptlSortPow をセットします。
     * @param ptlSortPow ptlSortPow
     */
    public void setPtlSortPow(int ptlSortPow) {
        ptlSortPow__ = ptlSortPow;
    }

}