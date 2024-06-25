package jp.groupsession.v2.enq.enq230;

import java.util.List;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.EnqValidateUtil;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq010.Enq010EnqueteModel;
import jp.groupsession.v2.enq.enq010.Enq010Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] テンプレート一覧画面のアクションフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq230Form extends Enq010Form {

    /** 初期表示フラグ */
    private int enq230initFlg__ = 0;
    /** 選択したアンケートSID */
    private String[] enq230selectEnqSid__ = null;
    /** 種類 */
    private int enq230type__ = 0;
    /** キーワード */
    private String enq230keyword__ = null;
    /** キーワード 種別 */
    private int enq230keywordType__ = 0;
    /** 重要度 */
    private int[] enq230priority__ = null;
    /** 匿名 */
    private int enq230anony__ = 0;

    /** 種類(検索条件保持) */
    private int enq230svType__ = 0;
    /** キーワード(検索条件保持) */
    private String enq230svKeyword__ = null;
    /** キーワード 種別(検索条件保持) */
    private int enq230svKeywordType__ = 0;
    /** 重要度(検索条件保持) */
    private int[] enq230svPriority__ = null;
    /** 状態(検索条件保持) */
    private int[] enq230svStatus__ = null;
    /** 匿名(検索条件保持) */
    private int enq230svAnony__ = 0;

    /** ページ(上段) */
    private int enq230pageTop__ = 0;
    /** ページ(下段) */
    private int enq230pageBottom__ = 0;
    /** ページリスト */
    private List<LabelValueBean> enq230pageList__ = null;
    /** アンケート情報一覧 */
    private List<Enq010EnqueteModel> enq230EnqueteList__ = null;
    /** アンケート種類リスト */
    private List<LabelValueBean> enq230TypeList__ = null;

    /**
     * <p>enq230initFlg を取得します。
     * @return enq230initFlg
     */
    public int getEnq230initFlg() {
        return enq230initFlg__;
    }

    /**
     * <p>enq230initFlg をセットします。
     * @param enq230initFlg enq230initFlg
     */
    public void setEnq230initFlg(int enq230initFlg) {
        enq230initFlg__ = enq230initFlg;
    }

    /**
     * <p>enq230selectEnqSid を取得します。
     * @return enq230selectEnqSid
     */
    public String[] getEnq230selectEnqSid() {
        return enq230selectEnqSid__;
    }

    /**
     * <p>enq230selectEnqSid をセットします。
     * @param enq230selectEnqSid enq230selectEnqSid
     */
    public void setEnq230selectEnqSid(String[] enq230selectEnqSid) {
        enq230selectEnqSid__ = enq230selectEnqSid;
    }

    /**
     * <p>enq230type を取得します。
     * @return enq230type
     */
    public int getEnq230type() {
        return enq230type__;
    }

    /**
     * <p>enq230type をセットします。
     * @param enq230type enq230type
     */
    public void setEnq230type(int enq230type) {
        enq230type__ = enq230type;
    }

    /**
     * <p>enq230keyword を取得します。
     * @return enq230keyword
     */
    public String getEnq230keyword() {
        return enq230keyword__;
    }

    /**
     * <p>enq230keyword をセットします。
     * @param enq230keyword enq230keyword
     */
    public void setEnq230keyword(String enq230keyword) {
        enq230keyword__ = enq230keyword;
    }

    /**
     * <p>enq230keywordType を取得します。
     * @return enq230keywordType
     */
    public int getEnq230keywordType() {
        return enq230keywordType__;
    }

    /**
     * <p>enq230keywordType をセットします。
     * @param enq230keywordType enq230keywordType
     */
    public void setEnq230keywordType(int enq230keywordType) {
        enq230keywordType__ = enq230keywordType;
    }

    /**
     * <p>enq230priority を取得します。
     * @return enq230priority
     */
    public int[] getEnq230priority() {
        return enq230priority__;
    }

    /**
     * <p>enq230priority をセットします。
     * @param enq230priority enq230priority
     */
    public void setEnq230priority(int[] enq230priority) {
        enq230priority__ = enq230priority;
    }

    /**
     * <p>enq230anony を取得します。
     * @return enq230anony
     */
    public int getEnq230anony() {
        return enq230anony__;
    }

    /**
     * <p>enq230anony をセットします。
     * @param enq230anony enq230anony
     */
    public void setEnq230anony(int enq230anony) {
        enq230anony__ = enq230anony;
    }

    /**
     * <p>enq230svType を取得します。
     * @return enq230svType
     */
    public int getEnq230svType() {
        return enq230svType__;
    }

    /**
     * <p>enq230svType をセットします。
     * @param enq230svType enq230svType
     */
    public void setEnq230svType(int enq230svType) {
        enq230svType__ = enq230svType;
    }

    /**
     * <p>enq230svKeyword を取得します。
     * @return enq230svKeyword
     */
    public String getEnq230svKeyword() {
        return enq230svKeyword__;
    }

    /**
     * <p>enq230svKeyword をセットします。
     * @param enq230svKeyword enq230svKeyword
     */
    public void setEnq230svKeyword(String enq230svKeyword) {
        enq230svKeyword__ = enq230svKeyword;
    }

    /**
     * <p>enq230svKeywordType を取得します。
     * @return enq230svKeywordType
     */
    public int getEnq230svKeywordType() {
        return enq230svKeywordType__;
    }

    /**
     * <p>enq230svKeywordType をセットします。
     * @param enq230svKeywordType enq230svKeywordType
     */
    public void setEnq230svKeywordType(int enq230svKeywordType) {
        enq230svKeywordType__ = enq230svKeywordType;
    }

    /**
     * <p>enq230svPriority を取得します。
     * @return enq230svPriority
     */
    public int[] getEnq230svPriority() {
        return enq230svPriority__;
    }

    /**
     * <p>enq230svPriority をセットします。
     * @param enq230svPriority enq230svPriority
     */
    public void setEnq230svPriority(int[] enq230svPriority) {
        enq230svPriority__ = enq230svPriority;
    }

    /**
     * <p>enq230svStatus を取得します。
     * @return enq230svStatus
     */
    public int[] getEnq230svStatus() {
        return enq230svStatus__;
    }

    /**
     * <p>enq230svStatus をセットします。
     * @param enq230svStatus enq230svStatus
     */
    public void setEnq230svStatus(int[] enq230svStatus) {
        enq230svStatus__ = enq230svStatus;
    }

    /**
     * <p>enq230svAnony を取得します。
     * @return enq230svAnony
     */
    public int getEnq230svAnony() {
        return enq230svAnony__;
    }

    /**
     * <p>enq230svAnony をセットします。
     * @param enq230svAnony enq230svAnony
     */
    public void setEnq230svAnony(int enq230svAnony) {
        enq230svAnony__ = enq230svAnony;
    }

    /**
     * <p>enq230pageTop を取得します。
     * @return enq230pageTop
     */
    public int getEnq230pageTop() {
        return enq230pageTop__;
    }

    /**
     * <p>enq230pageTop をセットします。
     * @param enq230pageTop enq230pageTop
     */
    public void setEnq230pageTop(int enq230pageTop) {
        enq230pageTop__ = enq230pageTop;
    }

    /**
     * <p>enq230pageBottom を取得します。
     * @return enq230pageBottom
     */
    public int getEnq230pageBottom() {
        return enq230pageBottom__;
    }

    /**
     * <p>enq230pageBottom をセットします。
     * @param enq230pageBottom enq230pageBottom
     */
    public void setEnq230pageBottom(int enq230pageBottom) {
        enq230pageBottom__ = enq230pageBottom;
    }

    /**
     * <p>enq230pageList を取得します。
     * @return enq230pageList
     */
    public List<LabelValueBean> getEnq230pageList() {
        return enq230pageList__;
    }

    /**
     * <p>enq230pageList をセットします。
     * @param enq230pageList enq230pageList
     */
    public void setEnq230pageList(List<LabelValueBean> enq230pageList) {
        enq230pageList__ = enq230pageList;
    }

    /**
     * <p>enq230EnqueteList を取得します。
     * @return enq230EnqueteList
     */
    public List<Enq010EnqueteModel> getEnq230EnqueteList() {
        return enq230EnqueteList__;
    }

    /**
     * <p>enq230EnqueteList をセットします。
     * @param enq230EnqueteList enq230EnqueteList
     */
    public void setEnq230EnqueteList(List<Enq010EnqueteModel> enq230EnqueteList) {
        enq230EnqueteList__ = enq230EnqueteList;
    }

    /**
     * <p>enq230TypeList を取得します。
     * @return enq230TypeList
     */
    public List<LabelValueBean> getEnq230TypeList() {
        return enq230TypeList__;
    }

    /**
     * <p>enq230TypeList をセットします。
     * @param enq230TypeList enq230TypeList
     */
    public void setEnq230TypeList(List<LabelValueBean> enq230TypeList) {
        enq230TypeList__ = enq230TypeList;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam230(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("enq230initFlg", enq230initFlg__);
        msgForm.addHiddenParam("enq230selectEnqSid", enq230selectEnqSid__);
        msgForm.addHiddenParam("enq230type", enq230type__);
        msgForm.addHiddenParam("enq230keyword", enq230keyword__);
        msgForm.addHiddenParam("enq230keywordType", enq230keywordType__);
        msgForm.addHiddenParam("enq230priority", enq230priority__);
        msgForm.addHiddenParam("enq230anony", enq230anony__);
        msgForm.addHiddenParam("enq230pageTop", enq230pageTop__);
        msgForm.addHiddenParam("enq230pageBottom", enq230pageBottom__);
        msgForm.addHiddenParam("enq230svType", enq230svType__);
        msgForm.addHiddenParam("enq230svKeyword", enq230svKeyword__);
        msgForm.addHiddenParam("enq230svKeywordType", enq230svKeywordType__);
        msgForm.addHiddenParam("enq230svPriority", enq230svPriority__);
        msgForm.addHiddenParam("enq230svStatus", enq230svStatus__);
        msgForm.addHiddenParam("enq230svAnony", enq230svAnony__);
    }

    /**
     * <br>[機  能] 検索条件の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ActionErrors
     */
    public ActionErrors validateSearch(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //キーワード
        EnqValidateUtil.validateTextBoxInput(errors, enq230keyword__,
                "enq230keyword",
                gsMsg.getMessage("cmn.keyword"),
                GSConstEnquete.MAX_LEN_EMN_TITLE, false);

        return errors;
    }
}
