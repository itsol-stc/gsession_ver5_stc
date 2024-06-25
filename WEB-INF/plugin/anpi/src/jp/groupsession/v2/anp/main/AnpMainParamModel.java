package jp.groupsession.v2.anp.main;

import jp.groupsession.v2.anp.model.AnpStateModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] メイン画面に表示する安否状況画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AnpMainParamModel extends AbstractParamModel {

    /** 安否確認SID */
    private String anpMainAnpiSid__;
    /** 現在の状況 */
    private AnpStateModel anpMainState__ = null;
    /**
     * <p>anpMainAnpiSid を取得します。
     * @return anpMainAnpiSid
     */
    public String getAnpMainAnpiSid() {
        return anpMainAnpiSid__;
    }
    /**
     * <p>anpMainAnpiSid をセットします。
     * @param anpMainAnpiSid anpMainAnpiSid
     */
    public void setAnpMainAnpiSid(String anpMainAnpiSid) {
        anpMainAnpiSid__ = anpMainAnpiSid;
    }
    /**
     * <p>anpMainState を取得します。
     * @return anpMainState
     */
    public AnpStateModel getAnpMainState() {
        return anpMainState__;
    }
    /**
     * <p>anpMainState をセットします。
     * @param anpMainState anpMainState
     */
    public void setAnpMainState(AnpStateModel anpMainState) {
        anpMainState__ = anpMainState;
    }
}