package jp.groupsession.v2.prj.ptl011;

import jp.groupsession.v2.prj.ptl010.PrjPtl010ParamModel;

/**
 * <br>[機  能] プロジェクト管理 ポートレット TODO状態内訳のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjPtl011ParamModel extends PrjPtl010ParamModel {
    /** ブラウザ判定 */
    private int prj010IeFlg__ = 0;

    /** プロジェクトトップ画面URL */
    private String prjTopUrl__ = null;

    /**
     * <p>prj010IeFlg を取得します。
     * @return prj010IeFlg
     */
    public int getPrj010IeFlg() {
        return prj010IeFlg__;
    }

    /**
     * <p>prj010IeFlg をセットします。
     * @param prj010IeFlg prj010IeFlg
     */
    public void setPrj010IeFlg(int prj010IeFlg) {
        prj010IeFlg__ = prj010IeFlg;
    }

    /**
     * @return prjTopUrl
     */
    public String getPrjTopUrl() {
        return prjTopUrl__;
    }

    /**
     * @param prjTopUrl セットする prjTopUrl
     */
    public void setPrjTopUrl(String prjTopUrl) {
        prjTopUrl__ = prjTopUrl;
    }
}