package jp.groupsession.v2.rsv.rsv090.model;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 施設の画像情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv090BinfModel extends CmnBinfModel {

    /** 週間・日間表示画像区分 */
    private int imgDspKbn__;

    /**
     * <p>imgDspKbn を取得します。
     * @return imgDspKbn
     */
    public int getImgDspKbn() {
        return imgDspKbn__;
    }

    /**
     * <p>imgDspKbn をセットします。
     * @param imgDspKbn imgDspKbn
     */
    public void setImgDspKbn(int imgDspKbn) {
        imgDspKbn__ = imgDspKbn;
    }
}
