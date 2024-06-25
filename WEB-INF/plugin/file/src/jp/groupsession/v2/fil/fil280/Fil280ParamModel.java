package jp.groupsession.v2.fil.fil280;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.fil.fil200.Fil200ParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 キャビネット管理設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil280ParamModel extends Fil200ParamModel {

    /** キャビネット単一選択 */
    private String fil280sltRadio__ = null;
    /** キャビネット複数選択 */
    private String[] fil220sltCheck__ = null;
    /** 全て選択or解除用 */
    private String fil280allCheck__ = null;

    /** キャビネット情報リスト */
    private List<Fil280DspModel> fil280cabinetList__ = new ArrayList<Fil280DspModel>();

    /** 検索キーワード */
    private String fil280keyword__ = null;
    /** グループ */
    private int fil280group__ = -1;
    /** ユーザ */
    private int fil280user__ = -1;

    /** 検索キーワード(検索条件保持) */
    private String fil280svKeyword__ = null;
    /** グループ(検索条件保持) */
    private int fil280svGroup__ = -1;
    /** ユーザ(検索条件保持) */
    private int fil280svUser__ = -1;

    /** 検索実行フラグ */
    private int fil280searchFlg__ = 0;

    /** グループコンボ */
    private List<LabelValueBean> groupCombo__ = null;
    /** ユーザコンボ */
    private List<LabelValueBean> userCombo__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheckPackageEdit(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //キャビネット複数選択
        if (fil220sltCheck__ == null || fil220sltCheck__.length < 1) {

            GsMessage gsMsg = new GsMessage();
            String textCabinetSelect = gsMsg.getMessage(req, "fil.70");

            msg = new ActionMessage("error.select.required.text", textCabinetSelect);
            StrutsUtil.addMessage(errors, msg, "fil220sltCheck");
        }

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheckSort(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        String textCabinet = gsMsg.getMessage(req, "fil.23");

        //キャビネット複数選択
        if (StringUtil.isNullZeroString(fil280sltRadio__)) {
            msg = new ActionMessage("error.select.required.text",
                    textCabinet);
            StrutsUtil.addMessage(errors, msg, "fil280sltRadio");
        }

        return errors;
    }

    /**
     * <p>fil280cabinetList を取得します。
     * @return fil280cabinetList
     */
    public List<Fil280DspModel> getFil280cabinetList() {
        return fil280cabinetList__;
    }
    /**
     * <p>fil280cabinetList をセットします。
     * @param fil280cabinetList fil280cabinetList
     */
    public void setFil280cabinetList(List<Fil280DspModel> fil280cabinetList) {
        fil280cabinetList__ = fil280cabinetList;
    }
    /**
     * <p>fil280allCheck を取得します。
     * @return fil280allCheck
     */
    public String getFil280allCheck() {
        return fil280allCheck__;
    }
    /**
     * <p>fil280allCheck をセットします。
     * @param fil280allCheck fil280allCheck
     */
    public void setFil280allCheck(String fil280allCheck) {
        fil280allCheck__ = fil280allCheck;
    }
    /**
     * <p>fil280sltRadio を取得します。
     * @return fil280sltRadio
     */
    public String getFil280sltRadio() {
        return fil280sltRadio__;
    }
    /**
     * <p>fil280sltRadio をセットします。
     * @param fil280sltRadio fil280sltRadio
     */
    public void setFil280sltRadio(String fil280sltRadio) {
        fil280sltRadio__ = fil280sltRadio;
    }

    /**
     * <p>fil220sltCheck をセットします。
     * @param fil220sltCheck fil220sltCheck
     */
    public void setFil220sltCheck(String[] fil220sltCheck) {
        fil220sltCheck__ = fil220sltCheck;
    }

    /**
     * <p>fil220sltCheck を取得します。
     * @return fil220sltCheck
     */
    public String[] getFil220sltCheck() {
        return fil220sltCheck__;
    }

    /**
     * <p>fil280group を取得します。
     * @return fil280group
     */
    public int getFil280group() {
        return fil280group__;
    }
    /**
     * <p>fil280group をセットします。
     * @param fil280group fil280group
     */
    public void setFil280group(int fil280group) {
        fil280group__ = fil280group;
    }
    /**
     * <p>fil280keyword を取得します。
     * @return fil280keyword
     */
    public String getFil280keyword() {
        return fil280keyword__;
    }
    /**
     * <p>fil280keyword をセットします。
     * @param fil280keyword fil280keyword
     */
    public void setFil280keyword(String fil280keyword) {
        fil280keyword__ = fil280keyword;
    }
    /**
     * <p>fil280user を取得します。
     * @return fil280user
     */
    public int getFil280user() {
        return fil280user__;
    }
    /**
     * <p>fil280user をセットします。
     * @param fil280user fil280user
     */
    public void setFil280user(int fil280user) {
        fil280user__ = fil280user;
    }
    /**
     * <p>fil280searchFlg を取得します。
     * @return fil280searchFlg
     */
    public int getFil280searchFlg() {
        return fil280searchFlg__;
    }
    /**
     * <p>fil280searchFlg をセットします。
     * @param fil280searchFlg fil280searchFlg
     */
    public void setFil280searchFlg(int fil280searchFlg) {
        fil280searchFlg__ = fil280searchFlg;
    }
    /**
     * <p>fil280svGroup を取得します。
     * @return fil280svGroup
     */
    public int getFil280svGroup() {
        return fil280svGroup__;
    }
    /**
     * <p>fil280svGroup をセットします。
     * @param fil280svGroup fil280svGroup
     */
    public void setFil280svGroup(int fil280svGroup) {
        fil280svGroup__ = fil280svGroup;
    }
    /**
     * <p>fil280svKeyword を取得します。
     * @return fil280svKeyword
     */
    public String getFil280svKeyword() {
        return fil280svKeyword__;
    }
    /**
     * <p>fil280svKeyword をセットします。
     * @param fil280svKeyword fil280svKeyword
     */
    public void setFil280svKeyword(String fil280svKeyword) {
        fil280svKeyword__ = fil280svKeyword;
    }
    /**
     * <p>fil280svUser を取得します。
     * @return fil280svUser
     */
    public int getFil280svUser() {
        return fil280svUser__;
    }
    /**
     * <p>fil280svUser をセットします。
     * @param fil280svUser fil280svUser
     */
    public void setFil280svUser(int fil280svUser) {
        fil280svUser__ = fil280svUser;
    }
    /**
     * <p>groupCombo を取得します。
     * @return groupCombo
     */
    public List<LabelValueBean> getGroupCombo() {
        return groupCombo__;
    }
    /**
     * <p>groupCombo をセットします。
     * @param groupCombo groupCombo
     */
    public void setGroupCombo(List<LabelValueBean> groupCombo) {
        groupCombo__ = groupCombo;
    }
    /**
     * <p>userCombo を取得します。
     * @return userCombo
     */
    public List<LabelValueBean> getUserCombo() {
        return userCombo__;
    }
    /**
     * <p>userCombo をセットします。
     * @param userCombo userCombo
     */
    public void setUserCombo(List<LabelValueBean> userCombo) {
        userCombo__ = userCombo;
    }
}
