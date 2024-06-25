package jp.groupsession.v2.enq.enq320;

import java.util.List;

import jp.groupsession.v2.enq.enq310.Enq310Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アンケート 結果確認（一覧）のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq320Form extends Enq310Form {

    /** グループ */
    private int enq320group__ = -1;
    /** ユーザ */
    private int enq320user__ = -1;
    /** 状態 回答 */
    private int enq320stsAns__ = 0;
    /** 状態 未回答 */
    private int enq320stsNon__ = 0;
    /** グループ(検索条件保持) */
    private int enq320svGroup__ = -1;
    /** ユーザ(検索条件保持) */
    private int enq320svUser__ = -1;
    /** 状態 回答(検索条件保持) */
    private int enq320svStsAns__ = 0;
    /** 状態 未回答(検索条件保持) */
    private int enq320svStsNon__ = 0;

    /** 一覧初期表示区分 */
    private int enq320viewMode__ = 0;

    /** 表示位置移動フラグ */
    private int enq320scrollQuestonFlg__ = 0;
    /** ソートキー */
    private int enq320sortKey__ = Enq320Const.SORTKEY_ANSDATE;
    /** 並び順 */
    private int enq320order__ = Enq320Const.ORDER_DESC;
    /** ページ(上段) */
    private int enq320pageTop__ = 0;
    /** ページ(下段) */
    private int enq320pageBottom__ = 0;

    /** グループコンボ */
    private List<LabelValueBean> groupCombo__ = null;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> userCombo__ = null;
    /** 回答一覧 */
    private List<Enq320AnswerModel> ansList__ = null;
    /**
     * <p>enq320group を取得します。
     * @return enq320group
     */
    public int getEnq320group() {
        return enq320group__;
    }
    /**
     * <p>enq320group をセットします。
     * @param enq320group enq320group
     */
    public void setEnq320group(int enq320group) {
        enq320group__ = enq320group;
    }
    /**
     * <p>enq320user を取得します。
     * @return enq320user
     */
    public int getEnq320user() {
        return enq320user__;
    }
    /**
     * <p>enq320user をセットします。
     * @param enq320user enq320user
     */
    public void setEnq320user(int enq320user) {
        enq320user__ = enq320user;
    }
    /**
     * <p>enq320stsAns を取得します。
     * @return enq320stsAns
     */
    public int getEnq320stsAns() {
        return enq320stsAns__;
    }
    /**
     * <p>enq320stsAns をセットします。
     * @param enq320stsAns enq320stsAns
     */
    public void setEnq320stsAns(int enq320stsAns) {
        enq320stsAns__ = enq320stsAns;
    }
    /**
     * <p>enq320stsNon を取得します。
     * @return enq320stsNon
     */
    public int getEnq320stsNon() {
        return enq320stsNon__;
    }
    /**
     * <p>enq320stsNon をセットします。
     * @param enq320stsNon enq320stsNon
     */
    public void setEnq320stsNon(int enq320stsNon) {
        enq320stsNon__ = enq320stsNon;
    }
    /**
     * <p>enq320svGroup を取得します。
     * @return enq320svGroup
     */
    public int getEnq320svGroup() {
        return enq320svGroup__;
    }
    /**
     * <p>enq320svGroup をセットします。
     * @param enq320svGroup enq320svGroup
     */
    public void setEnq320svGroup(int enq320svGroup) {
        enq320svGroup__ = enq320svGroup;
    }
    /**
     * <p>enq320svUser を取得します。
     * @return enq320svUser
     */
    public int getEnq320svUser() {
        return enq320svUser__;
    }
    /**
     * <p>enq320svUser をセットします。
     * @param enq320svUser enq320svUser
     */
    public void setEnq320svUser(int enq320svUser) {
        enq320svUser__ = enq320svUser;
    }
    /**
     * <p>enq320svStsAns を取得します。
     * @return enq320svStsAns
     */
    public int getEnq320svStsAns() {
        return enq320svStsAns__;
    }
    /**
     * <p>enq320svStsAns をセットします。
     * @param enq320svStsAns enq320svStsAns
     */
    public void setEnq320svStsAns(int enq320svStsAns) {
        enq320svStsAns__ = enq320svStsAns;
    }
    /**
     * <p>enq320svStsNon を取得します。
     * @return enq320svStsNon
     */
    public int getEnq320svStsNon() {
        return enq320svStsNon__;
    }
    /**
     * <p>enq320svStsNon をセットします。
     * @param enq320svStsNon enq320svStsNon
     */
    public void setEnq320svStsNon(int enq320svStsNon) {
        enq320svStsNon__ = enq320svStsNon;
    }
    /**
     * <p>enq320viewMode を取得します。
     * @return enq320viewMode
     */
    public int getEnq320viewMode() {
        return enq320viewMode__;
    }
    /**
     * <p>enq320viewMode をセットします。
     * @param enq320viewMode enq320viewMode
     */
    public void setEnq320viewMode(int enq320viewMode) {
        enq320viewMode__ = enq320viewMode;
    }
    /**
     * <p>enq320scrollQuestonFlg を取得します。
     * @return enq320scrollQuestonFlg
     */
    public int getEnq320scrollQuestonFlg() {
        return enq320scrollQuestonFlg__;
    }
    /**
     * <p>enq320scrollQuestonFlg をセットします。
     * @param enq320scrollQuestonFlg enq320scrollQuestonFlg
     */
    public void setEnq320scrollQuestonFlg(int enq320scrollQuestonFlg) {
        enq320scrollQuestonFlg__ = enq320scrollQuestonFlg;
    }
    /**
     * <p>enq320sortKey を取得します。
     * @return enq320sortKey
     */
    public int getEnq320sortKey() {
        return enq320sortKey__;
    }
    /**
     * <p>enq320sortKey をセットします。
     * @param enq320sortKey enq320sortKey
     */
    public void setEnq320sortKey(int enq320sortKey) {
        enq320sortKey__ = enq320sortKey;
    }
    /**
     * <p>enq320order を取得します。
     * @return enq320order
     */
    public int getEnq320order() {
        return enq320order__;
    }
    /**
     * <p>enq320order をセットします。
     * @param enq320order enq320order
     */
    public void setEnq320order(int enq320order) {
        enq320order__ = enq320order;
    }
    /**
     * <p>enq320pageTop を取得します。
     * @return enq320pageTop
     */
    public int getEnq320pageTop() {
        return enq320pageTop__;
    }
    /**
     * <p>enq320pageTop をセットします。
     * @param enq320pageTop enq320pageTop
     */
    public void setEnq320pageTop(int enq320pageTop) {
        enq320pageTop__ = enq320pageTop;
    }
    /**
     * <p>enq320pageBottom を取得します。
     * @return enq320pageBottom
     */
    public int getEnq320pageBottom() {
        return enq320pageBottom__;
    }
    /**
     * <p>enq320pageBottom をセットします。
     * @param enq320pageBottom enq320pageBottom
     */
    public void setEnq320pageBottom(int enq320pageBottom) {
        enq320pageBottom__ = enq320pageBottom;
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
    public List<UsrLabelValueBean> getUserCombo() {
        return userCombo__;
    }
    /**
     * <p>userCombo をセットします。
     * @param userCombo userCombo
     */
    public void setUserCombo(List<UsrLabelValueBean> userCombo) {
        userCombo__ = userCombo;
    }
    /**
     * <p>ansList を取得します。
     * @return ansList
     */
    public List<Enq320AnswerModel> getAnsList() {
        return ansList__;
    }
    /**
     * <p>ansList をセットします。
     * @param ansList ansList
     */
    public void setAnsList(List<Enq320AnswerModel> ansList) {
        ansList__ = ansList;
    }
}
