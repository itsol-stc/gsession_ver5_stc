package jp.groupsession.v2.rsv.rsv111kn;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.rsv.rsv111.Rsv111Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 施設予約 施設予約拡張登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv111knForm extends Rsv111Form {

    /** 変更前データリスト */
    private ArrayList<String> oldDay__ = null;
    /** 登録対象日付リスト */
    private ArrayList<String> targetDay__ = null;
    /** 予約開始時間(表示形式) */
    private String yoyakuTimeFrString__ = null;
    /** 予約終了時間(表示形式) */
    private String yoyakuTimeToString__ = null;
    /** 内容(確認用) */
    private String rsv111knRsrBiko__ = null;
    /** 削除処理フラグ */
    private boolean rsv111DeleteFlg__ = false;

    /** 施設予約拡張SID(ショートメールからの遷移時に使用) */
    private int extendSid__ = -1;

    /** 公開対象ユーザ・グループリスト */
    private List <UsrLabelValueBean> rsv111knPubUsrGrpList__ = null;

    /**
     * <p>rsv111DeleteFlg__ を取得します。
     * @return rsv111DeleteFlg
     */
    public boolean isRsv111DeleteFlg() {
        return rsv111DeleteFlg__;
    }
    /**
     * <p>rsv111DeleteFlg__ をセットします。
     * @param rsv111DeleteFlg rsv111DeleteFlg__
     */
    public void setRsv111DeleteFlg(boolean rsv111DeleteFlg) {
        rsv111DeleteFlg__ = rsv111DeleteFlg;
    }
    /**
     * <p>oldDay__ を取得します。
     * @return oldDay
     */
    public ArrayList<String> getOldDay() {
        return oldDay__;
    }
    /**
     * <p>oldDay__ をセットします。
     * @param oldDay oldDay__
     */
    public void setOldDay(ArrayList<String> oldDay) {
        oldDay__ = oldDay;
    }
    /**
     * <p>rsv111knRsrBiko__ を取得します。
     * @return rsv111knRsrBiko
     */
    public String getRsv111knRsrBiko() {
        return rsv111knRsrBiko__;
    }
    /**
     * <p>rsv111knRsrBiko__ をセットします。
     * @param rsv111knRsrBiko rsv111knRsrBiko__
     */
    public void setRsv111knRsrBiko(String rsv111knRsrBiko) {
        rsv111knRsrBiko__ = rsv111knRsrBiko;
    }
    /**
     * <p>targetDay__ を取得します。
     * @return targetDay
     */
    public ArrayList<String> getTargetDay() {
        return targetDay__;
    }
    /**
     * <p>targetDay__ をセットします。
     * @param targetDay targetDay__
     */
    public void setTargetDay(ArrayList<String> targetDay) {
        targetDay__ = targetDay;
    }
    /**
     * <p>yoyakuTimeFrString__ を取得します。
     * @return yoyakuTimeFrString
     */
    public String getYoyakuTimeFrString() {
        return yoyakuTimeFrString__;
    }
    /**
     * <p>yoyakuTimeFrString__ をセットします。
     * @param yoyakuTimeFrString yoyakuTimeFrString__
     */
    public void setYoyakuTimeFrString(String yoyakuTimeFrString) {
        yoyakuTimeFrString__ = yoyakuTimeFrString;
    }
    /**
     * <p>yoyakuTimeToString__ を取得します。
     * @return yoyakuTimeToString
     */
    public String getYoyakuTimeToString() {
        return yoyakuTimeToString__;
    }
    /**
     * <p>yoyakuTimeToString__ をセットします。
     * @param yoyakuTimeToString yoyakuTimeToString__
     */
    public void setYoyakuTimeToString(String yoyakuTimeToString) {
        yoyakuTimeToString__ = yoyakuTimeToString;
    }
    /**
     * @return extendSid
     */
    public int getExtendSid() {
        return extendSid__;
    }
    /**
     * @param extendSid セットする extendSid
     */
    public void setExtendSid(int extendSid) {
        extendSid__ = extendSid;
    }
    /**
     * <p>rsv111knPubUsrGrpList を取得します。
     * @return rsv111knPubUsrGrpList
     * @see jp.groupsession.v2.rsv.rsv111kn.Rsv111knForm#rsv111knPubUsrGrpList__
     */
    public List<UsrLabelValueBean> getRsv111knPubUsrGrpList() {
        return rsv111knPubUsrGrpList__;
    }
    /**
     * <p>rsv111knPubUsrGrpList をセットします。
     * @param rsv111knPubUsrGrpList rsv111knPubUsrGrpList
     * @see jp.groupsession.v2.rsv.rsv111kn.Rsv111knForm#rsv111knPubUsrGrpList__
     */
    public void setRsv111knPubUsrGrpList(
            List<UsrLabelValueBean> rsv111knPubUsrGrpList) {
        rsv111knPubUsrGrpList__ = rsv111knPubUsrGrpList;
    }
}