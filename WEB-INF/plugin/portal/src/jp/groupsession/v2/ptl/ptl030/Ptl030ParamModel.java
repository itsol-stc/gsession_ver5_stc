package jp.groupsession.v2.ptl.ptl030;

import java.util.List;

import jp.groupsession.v2.ptl.ptl020.Ptl020ParamModel;
import jp.groupsession.v2.ptl.ptl030.model.Ptl030Model;

/**
 * <br>[機  能] ポータル ポータル管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl030ParamModel extends Ptl020ParamModel {
    /** 表示順ラジオボタン */
    private int ptl030sortPortal__ = -1;

    /** ポータル一覧 */
    private List<Ptl030Model> ptl030portalList__ = null;

    /**
     * @return ptl030sortPortal
     */
    public int getPtl030sortPortal() {
        return ptl030sortPortal__;
    }
    /**
     * @param ptl030sortPortal セットする ptl030sortPortal
     */
    public void setPtl030sortPortal(int ptl030sortPortal) {
        ptl030sortPortal__ = ptl030sortPortal;
    }
    /**
     * <p>ptl030portalList を取得します。
     * @return ptl030portalList
     */
    public List<Ptl030Model> getPtl030portalList() {
        return ptl030portalList__;
    }
    /**
     * <p>ptl030portalList をセットします。
     * @param ptl030portalList ptl030portalList
     */
    public void setPtl030portalList(List<Ptl030Model> ptl030portalList) {
        ptl030portalList__ = ptl030portalList;
    }
}