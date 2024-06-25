package jp.groupsession.v2.fil.fil120;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.fil.fil110.Fil110Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 個人設定 表示設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil120Form extends Fil110Form {

    /** メイン画面表示 ショートカット */
    private String fil120MainSort__ = null;
    /** メイン画面表示 更新通知 */
    private String fil120MainCall__ = null;
    /** 履歴表示件数 */
    private String fil120RirekiCnt__ = null;
    /** 更新通知 */
    private String fil120Call__ = null;

    /** 履歴表示件数コンボ */
    private ArrayList<LabelValueBean> fil120RirekiCntLblList__ = new ArrayList<LabelValueBean>();
    /** 更新表示件数コンボ */
    private ArrayList<LabelValueBean> fil120CallLblList__ = new ArrayList<LabelValueBean>();
    /** 戻り先メインフラグ */
    private int backMainFlg__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        String textCall = gsMsg.getMessage(req, "fil.1");

        //ショートカット
        if (fil120MainSort__ == null) {
            String textShortcut = gsMsg.getMessage(req, "fil.2");
            msg = new ActionMessage("error.select.required.text", textShortcut);
            StrutsUtil.addMessage(errors, msg, "fil120MainSort");
        }

        //更新通知
        if (fil120MainCall__ == null) {
            msg = new ActionMessage("error.select.required.text", textCall);
            StrutsUtil.addMessage(errors, msg, "fil120MainCall");
        }

        //履歴表示件数
        if (fil120RirekiCnt__ == null) {
            String textRireki = gsMsg.getMessage(req, "fil.26");
            msg = new ActionMessage("error.select.required.text", textRireki);
            StrutsUtil.addMessage(errors, msg, "fil120RirekiCnt");
        }

        //更新通知
        if (fil120Call__ == null) {
            msg = new ActionMessage("error.select.required.text", textCall);
            StrutsUtil.addMessage(errors, msg, "fil120Call");
        }


        return errors;
    }

    /**
     * <p>fil120RirekiCntLblList を取得します。
     * @return fil120RirekiCntLblList
     */
    public ArrayList<LabelValueBean> getFil120RirekiCntLblList() {
        return fil120RirekiCntLblList__;
    }
    /**
     * <p>fil120RirekiCntLblList をセットします。
     * @param fil120RirekiCntLblList fil120RirekiCntLblList
     */
    public void setFil120RirekiCntLblList(
            ArrayList<LabelValueBean> fil120RirekiCntLblList) {
        fil120RirekiCntLblList__ = fil120RirekiCntLblList;
    }
    /**
     * <p>fil120MainCall を取得します。
     * @return fil120MainCall
     */
    public String getFil120MainCall() {
        return fil120MainCall__;
    }
    /**
     * <p>fil120MainCall をセットします。
     * @param fil120MainCall fil120MainCall
     */
    public void setFil120MainCall(String fil120MainCall) {
        fil120MainCall__ = fil120MainCall;
    }
    /**
     * <p>fil120MainSort を取得します。
     * @return fil120MainSort
     */
    public String getFil120MainSort() {
        return fil120MainSort__;
    }
    /**
     * <p>fil120MainSort をセットします。
     * @param fil120MainSort fil120MainSort
     */
    public void setFil120MainSort(String fil120MainSort) {
        fil120MainSort__ = fil120MainSort;
    }
    /**
     * <p>fil120RirekiCnt を取得します。
     * @return fil120RirekiCnt
     */
    public String getFil120RirekiCnt() {
        return fil120RirekiCnt__;
    }
    /**
     * <p>fil120RirekiCnt をセットします。
     * @param fil120RirekiCnt fil120RirekiCnt
     */
    public void setFil120RirekiCnt(String fil120RirekiCnt) {
        fil120RirekiCnt__ = fil120RirekiCnt;
    }

    /**
     * <p>fil120Call を取得します。
     * @return fil120Call
     */
    public String getFil120Call() {
        return fil120Call__;
    }

    /**
     * <p>fil120Call をセットします。
     * @param fil120Call fil120Call
     */
    public void setFil120Call(String fil120Call) {
        fil120Call__ = fil120Call;
    }

    /**
     * <p>fil120CallLblList を取得します。
     * @return fil120CallLblList
     */
    public ArrayList<LabelValueBean> getFil120CallLblList() {
        return fil120CallLblList__;
    }

    /**
     * <p>fil120CallLblList をセットします。
     * @param fil120CallLblList fil120CallLblList
     */
    public void setFil120CallLblList(ArrayList<LabelValueBean> fil120CallLblList) {
        fil120CallLblList__ = fil120CallLblList;
    }

    /**
     * <p>backMainFlg を取得します。
     * @return backMainFlg
     */
    public int getBackMainFlg() {
        return backMainFlg__;
    }

    /**
     * <p>backMainFlg をセットします。
     * @param backMainFlg backMainFlg
     */
    public void setBackMainFlg(int backMainFlg) {
        backMainFlg__ = backMainFlg;
    }

}