package jp.groupsession.v2.tcd.main;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] タイムカード(メイン画面表示用)の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdMainParamModel extends AbstractParamModel {
    //表示用項目
    /** ステータス */
    private String tcdStatus__;
    /** 始業時刻 */
    private String tcdStartTime__;
    /** 終業時刻 */
    private String tcdStopTime__;
    /** 個人設定(メイン表示設定)**/
    private int dspFlg__;
    /** 在席管理連動**/
    private String zaisekiSts__;
    /** タイムカードトップ画面URL */
    private String  tcdTopUrl__;

    /**
     * @return tcdTopUrl__ を戻します。
     */
    public String getTcdTopUrl() {
        return tcdTopUrl__;
    }
    /**
     * @param tcdTopUrl 設定する tcdTopUrl__。
     */
    public void setTcdTopUrl(String tcdTopUrl) {
        tcdTopUrl__ = tcdTopUrl;
    }

    /**
     * <p>zaisekiSts を取得します。
     * @return zaisekiSts
     */
    public String getZaisekiSts() {
        return zaisekiSts__;
    }

    /**
     * <p>zaisekiSts をセットします。
     * @param zaisekiSts zaisekiSts
     */
    public void setZaisekiSts(String zaisekiSts) {
        zaisekiSts__ = zaisekiSts;
    }

    /**
     * <p>dspFlg を取得します。
     * @return dspFlg
     */
    public int getDspFlg() {
        return dspFlg__;
    }

    /**
     * <p>dspFlg をセットします。
     * @param dspFlg dspFlg
     */
    public void setDspFlg(int dspFlg) {
        dspFlg__ = dspFlg;
    }

    /**
     * <p>tcdStartTime を取得します。
     * @return tcdStartTime
     */
    public String getTcdStartTime() {
        return tcdStartTime__;
    }

    /**
     * <p>tcdStartTime をセットします。
     * @param tcdStartTime tcdStartTime
     */
    public void setTcdStartTime(String tcdStartTime) {
        tcdStartTime__ = tcdStartTime;
    }

    /**
     * <p>tcdStatus を取得します。
     * @return tcdStatus
     */
    public String getTcdStatus() {
        return tcdStatus__;
    }

    /**
     * <p>tcdStatus をセットします。
     * @param tcdStatus tcdStatus
     */
    public void setTcdStatus(String tcdStatus) {
        tcdStatus__ = tcdStatus;
    }

    /**
     * <p>tcdStopTime を取得します。
     * @return tcdStopTime
     */
    public String getTcdStopTime() {
        return tcdStopTime__;
    }

    /**
     * <p>tcdStopTime をセットします。
     * @param tcdStopTime tcdStopTime
     */
    public void setTcdStopTime(String tcdStopTime) {
        tcdStopTime__ = tcdStopTime;
    }
}