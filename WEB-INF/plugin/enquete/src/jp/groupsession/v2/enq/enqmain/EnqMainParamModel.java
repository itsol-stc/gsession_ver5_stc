package jp.groupsession.v2.enq.enqmain;

import jp.groupsession.v2.enq.enq010.Enq010ParamModel;

/**
 *
 * <br>[機  能] アンケート メイン画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqMainParamModel extends Enq010ParamModel {

    /** アンケートトップ画面URL */
    private String enqTopUrl__;

    /**
     * <p>enqTopUrl を取得します。
     * @return enqTopUrl
     */
    public String getEnqTopUrl() {
        return enqTopUrl__;
    }

    /**
     * <p>enqTopUrl をセットします。
     * @param enqTopUrl enqTopUrl
     */
    public void setEnqTopUrl(String enqTopUrl) {
        enqTopUrl__ = enqTopUrl;
    }


}
