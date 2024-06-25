package jp.groupsession.v2.tcd.tcd080;

import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.tcd100.Tcd100Form;

/**
 * <br>[機  能] タイムカード 個人設定 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd080Form extends Tcd100Form {

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
     * @see jp.groupsession.v2.tcd.tcd080.Tcd080Form#tcd080KinpDispFlg__
     */
    public int getTcd080KinpDispFlg() {
        return tcd080KinpDispFlg__;
    }

    /**
     * <p>tcd080KinpDispFlg をセットします。
     * @param tcd080KinpDispFlg tcd080KinpDispFlg
     * @see jp.groupsession.v2.tcd.tcd080.Tcd080Form#tcd080KinpDispFlg__
     */
    public void setTcd080KinpDispFlg(int tcd080KinpDispFlg) {
        tcd080KinpDispFlg__ = tcd080KinpDispFlg;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        UDate inDate = new UDate();
        inDate.setZeroHhMmSs();
        UDate outDate = inDate.cloneUDate();
        inDate.setHour(tcd080DefFrH__);
        inDate.setMinute(tcd080DefFrM__);
        outDate.setHour(tcd080DefToH__);
        outDate.setMinute(tcd080DefToM__);
        GsMessage gsMsg = new GsMessage(reqMdl);
        /** メッセージ 開始時刻 時間 **/
        String sTimeHour = gsMsg.getMessage("tcd.174");
        /** メッセージ 開始時刻 分 **/
        String sTimeMin = gsMsg.getMessage("tcd.175");
        /** メッセージ 終了時刻 時間 **/
        String eTimeHour = gsMsg.getMessage("tcd.176");
        /** メッセージ 終了時刻 分 **/
        String eTimeMin = gsMsg.getMessage("tcd.177");

        //未入力チェック
        boolean errorFlg = false;
        if (tcd080DefFrH__ == -1) {
            msg = new ActionMessage("error.select.required.text", sTimeHour);
            errors.add("tcd080DefFrH__" + "error.select.required.text", msg);
            errorFlg = true;
        }
        //未入力チェック
        if (tcd080DefFrM__ == -1) {
            msg = new ActionMessage("error.select.required.text", sTimeMin);
            errors.add("tcd080DefFrM__" + "error.select.required.text", msg);
            errorFlg = true;
        }
        //未入力チェック
        if (tcd080DefToH__ == -1) {
            msg = new ActionMessage("error.select.required.text", eTimeHour);
            errors.add("tcd080DefToH__" + "error.select.required.text", msg);
            errorFlg = true;
        }
        //未入力チェック
        if (tcd080DefToM__ == -1) {
            msg = new ActionMessage("error.select.required.text", eTimeMin);
            errors.add("tcd080DefToM__" + "error.select.required.text", msg);
            errorFlg = true;
        }

        if (!errorFlg) {
            //大小チェックcmn.starttime
            if (inDate.compare(inDate, outDate) != UDate.LARGE) {
                msg = new ActionMessage("error.input.comp.text", gsMsg.getMessage("cmn.time"),
                        gsMsg.getMessage("tcd.178"));
                errors.add("" + "error.input.comp.text", msg);
            }
        }

        return errors;
    }
}
