package jp.groupsession.v2.tcd.tcd080;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.tcd.tcd100.Tcd100ParamModel;

/**
 * <br>[機  能] タイムカード 個人設定 基本設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd080ParamModel extends Tcd100ParamModel {
    /** 開始 時 */
    private int tcd080DefFrH__ = -1;
    /** 開始 分 */
    private int tcd080DefFrM__ = -1;
    /** 終了 時 */
    private int tcd080DefToH__ = -1;
    /** 終了 分 */
    private int tcd080DefToM__ = -1;
    /** メインへの表示 */
    private int tcd080mainDsp__ = -1;
    /** 勤務表出力種類 0=Excel 1=PDF */
    private int tcd080kinmuOutput__ = -1;
    /** ラベル 時 */
    private List < LabelValueBean > tcd080HourLabel__ = null;
    /** ラベル 分 */
    private List < LabelValueBean > tcd080MinuteLabel__ = null;
    /** メイン画面初期値 在席管理と連動 */
    private String tcd080zaisekiSts__;
    /** 勤務表形式表示フラグ */
    private int tcd080KinpDispFlg__ = 0;

    /**
     * @return the tcd080kinmuOutput
     */
    public int getTcd080kinmuOutput() {
        return tcd080kinmuOutput__;
    }

    /**
     * @param tcd080kinmuOutput the tcd080kinmuOutput to set
     */
    public void setTcd080kinmuOutput(int tcd080kinmuOutput) {
        tcd080kinmuOutput__ = tcd080kinmuOutput;
    }

    /**
     * <p>tcd080zaisekiSts を取得します。
     * @return tcd080zaisekiSts
     */
    public String getTcd080zaisekiSts() {
        return tcd080zaisekiSts__;
    }

    /**
     * <p>tcd080zaisekiSts をセットします。
     * @param tcd080zaisekiSts tcd080zaisekiSts
     */
    public void setTcd080zaisekiSts(String tcd080zaisekiSts) {
        tcd080zaisekiSts__ = tcd080zaisekiSts;
    }

    /**
     * <p>tcd080mainDsp を取得します。
     * @return tcd080mainDsp
     */
    public int getTcd080mainDsp() {
        return tcd080mainDsp__;
    }

    /**
     * <p>tcd080mainDsp をセットします。
     * @param tcd080mainDsp tcd080mainDsp
     */
    public void setTcd080mainDsp(int tcd080mainDsp) {
        tcd080mainDsp__ = tcd080mainDsp;
    }

    /**
     * <p>tcd080DefFrH を取得します。
     * @return tcd080DefFrH
     */
    public int getTcd080DefFrH() {
        return tcd080DefFrH__;
    }

    /**
     * <p>tcd080DefFrH をセットします。
     * @param tcd080DefFrH tcd080DefFrH
     */
    public void setTcd080DefFrH(int tcd080DefFrH) {
        tcd080DefFrH__ = tcd080DefFrH;
    }

    /**
     * <p>tcd080DefFrM を取得します。
     * @return tcd080DefFrM
     */
    public int getTcd080DefFrM() {
        return tcd080DefFrM__;
    }

    /**
     * <p>tcd080DefFrM をセットします。
     * @param tcd080DefFrM tcd080DefFrM
     */
    public void setTcd080DefFrM(int tcd080DefFrM) {
        tcd080DefFrM__ = tcd080DefFrM;
    }

    /**
     * <p>tcd080DefToH を取得します。
     * @return tcd080DefToH
     */
    public int getTcd080DefToH() {
        return tcd080DefToH__;
    }

    /**
     * <p>tcd080DefToH をセットします。
     * @param tcd080DefToH tcd080DefToH
     */
    public void setTcd080DefToH(int tcd080DefToH) {
        tcd080DefToH__ = tcd080DefToH;
    }

    /**
     * <p>tcd080DefToM を取得します。
     * @return tcd080DefToM
     */
    public int getTcd080DefToM() {
        return tcd080DefToM__;
    }

    /**
     * <p>tcd080DefToM をセットします。
     * @param tcd080DefToM tcd080DefToM
     */
    public void setTcd080DefToM(int tcd080DefToM) {
        tcd080DefToM__ = tcd080DefToM;
    }

    /**
     * <p>tcd080HourLabel を取得します。
     * @return tcd080HourLabel
     */
    public List<LabelValueBean> getTcd080HourLabel() {
        return tcd080HourLabel__;
    }

    /**
     * <p>tcd080HourLabel をセットします。
     * @param tcd080HourLabel tcd080HourLabel
     */
    public void setTcd080HourLabel(List<LabelValueBean> tcd080HourLabel) {
        tcd080HourLabel__ = tcd080HourLabel;
    }

    /**
     * <p>tcd080MinuteLabel を取得します。
     * @return tcd080MinuteLabel
     */
    public List<LabelValueBean> getTcd080MinuteLabel() {
        return tcd080MinuteLabel__;
    }

    /**
     * <p>tcd080MinuteLabel をセットします。
     * @param tcd080MinuteLabel tcd080MinuteLabel
     */
    public void setTcd080MinuteLabel(List<LabelValueBean> tcd080MinuteLabel) {
        tcd080MinuteLabel__ = tcd080MinuteLabel;
    }

    /**
     * <p>tcd080KinpDispFlg を取得します。
     * @return tcd080KinpDispFlg
     * @see jp.groupsession.v2.tcd.tcd080.Tcd080ParamModel#tcd080KinpDispFlg__
     */
    public int getTcd080KinpDispFlg() {
        return tcd080KinpDispFlg__;
    }

    /**
     * <p>tcd080KinpDispFlg をセットします。
     * @param tcd080KinpDispFlg tcd080KinpDispFlg
     * @see jp.groupsession.v2.tcd.tcd080.Tcd080ParamModel#tcd080KinpDispFlg__
     */
    public void setTcd080KinpDispFlg(int tcd080KinpDispFlg) {
        tcd080KinpDispFlg__ = tcd080KinpDispFlg;
    }
}