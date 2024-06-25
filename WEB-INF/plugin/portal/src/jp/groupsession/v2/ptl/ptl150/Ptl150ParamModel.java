package jp.groupsession.v2.ptl.ptl150;

import jp.groupsession.v2.ptl.ptl020.Ptl020ParamModel;

/**
 * <br>[機  能] ポータル 管理者設定 初期値設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl150ParamModel extends Ptl020ParamModel {
    /** ポータルの初期表示区分 */
    private String ptl150ptlInitKbn__ = null;
    /** ポータルの初期表示 設定方法 */
    private String ptl150ptlInitType__ = null;
    /** ポータルの初期表示フラグ */
    private int ptl150init__ = 0;
    /**
     * @return ptl150init
     */
    public int getPtl150init() {
        return ptl150init__;
    }
    /**
     * @param ptl150init セットする ptl150init
     */
    public void setPtl150init(int ptl150init) {
        ptl150init__ = ptl150init;
    }
    /**
     * @return ptl150ptlInitKbn
     */
    public String getPtl150ptlInitKbn() {
        return ptl150ptlInitKbn__;
    }
    /**
     * @param ptl150ptlInitKbn セットする ptl150ptlInitKbn
     */
    public void setPtl150ptlInitKbn(String ptl150ptlInitKbn) {
        ptl150ptlInitKbn__ = ptl150ptlInitKbn;
    }

    /**
     * @return ptl150ptlInitType
     */
    public String getPtl150ptlInitType() {
        return ptl150ptlInitType__;
    }
    /**
     * @param ptl150ptlInitType セットする ptl150ptlInitType
     */
    public void setPtl150ptlInitType(String ptl150ptlInitType) {
        ptl150ptlInitType__ = ptl150ptlInitType;
    }
}