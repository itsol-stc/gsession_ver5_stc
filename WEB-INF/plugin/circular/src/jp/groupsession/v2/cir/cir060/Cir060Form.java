package jp.groupsession.v2.cir.cir060;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.GSValidateCircular;
import jp.groupsession.v2.cir.cir010.Cir010Form;
import jp.groupsession.v2.cir.model.CirAccountModel;
import jp.groupsession.v2.cir.model.CircularDspModel;
import jp.groupsession.v2.cir.model.CircularUsrModel;
import jp.groupsession.v2.cir.ui.parts.account.AccountSelector;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parts.select.Select;

/**
 * <br>[機  能] 回覧板 詳細検索画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir060Form extends Cir010Form {

    //入力項目
    /** 回覧板種別 */
    private int cir060syubetsu__ = Integer.parseInt(GSConstCircular.MODE_JUSIN);
    /** 発信者グループ */
    private String cir060groupSid__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 発信者 */
    private int cir060userSid__ = GSConstCommon.NUM_INIT;
    /** キーワード検索区分 */
    private int cir060wordKbn__ = GSConstCircular.KEY_WORD_KBN_AND;
    /** 回覧先SID */
    private String[] cir060selUserSid__ = null;
    /** 回覧先選択グループSID */
    private String cir060selGroupSid__ = null;
    /** 回覧先選択UI */
    private AccountSelector cir060MemberSelector__ =
                AccountSelector.builder()
                .chainSelect(Select.builder()
                        .chainParameterName("cir060selUserSid")
                        )
                .chainGroupSelectionParamName("cir060selGroupSid")
                .build();

    /** 検索対象 */
    private String[] cir060searchTarget__ = null;
    /** ソートキー1 */
    private int cir060sort1__ = GSConstCircular.SORT_DATE;
    /** オーダーキー1 */
    private int cir060order1__ = GSConst.ORDER_KEY_DESC;
    /** ソートキー2 */
    private int cir060sort2__ = GSConstCircular.SORT_TITLE;
    /** オーダーキー2 */
    private int cir060order2__ = GSConst.ORDER_KEY_ASC;

    /** ページ1 */
    private int cir060pageNum1__;
    /** ページ2 */
    private int cir060pageNum2__;

    //セーブ
    /** セーブ 検索キーワード */
    private String cir010svSearchWord__;
    /** セーブ 回覧板種別 */
    private int cir060svSyubetsu__ = Integer.parseInt(GSConstCircular.MODE_JUSIN);
    /** セーブ 発信者グループ */
    private String cir060svGroupSid__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** セーブ 発信者 */
    private int cir060svUserSid__ = GSConstCommon.NUM_INIT;
    /** セーブ キーワード検索区分 */
    private int cir060svWordKbn__ = GSConstCircular.KEY_WORD_KBN_AND;
    /** セーブ 回覧先SID */
    private String[] cir060svSelUserSid__ = null;
    /** セーブ 検索対象 */
    private String[] cir060svSearchTarget__ = null;
    /** セーブ ソートキー1 */
    private int cir060svSort1__ = GSConstCircular.SORT_DATE;
    /** セーブ オーダーキー1 */
    private int cir060svOrder1__ = GSConst.ORDER_KEY_DESC;
    /** セーブ ソートキー2 */
    private int cir060svSort2__ = GSConstCircular.SORT_TITLE;
    /** セーブ オーダーキー2 */
    private int cir060svOrder2__ = GSConst.ORDER_KEY_ASC;

    //フラグ
    /** 検索フラグ */
    private boolean cir060searchFlg__ = false;

    /** WEB検索プラグイン使用可否 0=使用 1=未使用 */
    private int cir060searchUse__ = GSConst.PLUGIN_USE;

    //表示項目
    /** グループラベルリスト */
    private List<CmnLabelValueModel> groupLabel__ = null;
    /** ユーザラベルリスト */
    private List<CircularUsrModel> userLabel__ = null;
    /** ソートキーラベルリスト */
    private List<LabelValueBean> sortLabel__ = null;
    /** ページラベル */
    ArrayList < LabelValueBean > pageLabel__;
    /** 回覧板リスト */
    private List<CircularDspModel> circularList__ = null;
    /** 回覧先リスト */
    private List<CirAccountModel> memberList__ = null;

    /** WEB検索ワード */
    private String cir060WebSearchWord__ = "";
    /** HTML表示用検索ワード */
    private String cir060HtmlSearchWord__ = "";

    /**
     * <br>[機  能] 入力チェックを行う(検索)
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateSearchCir(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();

        //検索キーワード
        GSValidateCircular.validateSearchKeyword(errors, getCir010searchWord(), reqMdl);

        //検索対象
        GSValidateCircular.validateSearchTarget(
                errors, reqMdl, getCir010searchWord(), cir060searchTarget__);

        //ソート順1、2
        GSValidateCircular.validateSearchSortOrder(errors, reqMdl, cir060sort1__, cir060sort2__);

        return errors;
    }

    /**
     * <br>[機  能] 検索条件パラメータをセーブフィールドへ移行
     * <br>[解  説]
     * <br>[備  考]
     */
    public void saveSearchParm() {
        //セーブ 検索キーワード
        cir010svSearchWord__ = getCir010searchWord();
        //セーブ 回覧板種別
        cir060svSyubetsu__ = cir060syubetsu__;
        //セーブ 発信者グループ
        cir060svGroupSid__ = cir060groupSid__;
        //セーブ 発信者
        cir060svUserSid__ = cir060userSid__;
        //セーブ キーワード検索区分
        cir060svWordKbn__ = cir060wordKbn__;
        //セーブ 回覧先SID
        cir060svSelUserSid__ = cir060selUserSid__;
        //セーブ 検索対象
        cir060svSearchTarget__ = cir060searchTarget__;
        //セーブ ソートキー1
        cir060svSort1__ = cir060sort1__;
        //セーブ オーダーキー1
        cir060svOrder1__ = cir060order1__;
        //セーブ ソートキー2
        cir060svSort2__ = cir060sort2__;
        //セーブ オーダーキー2
        cir060svOrder2__ = cir060order2__;
    }

    /**
     * <p>cir010svSearchWord を取得します。
     * @return cir010svSearchWord
     */
    public String getCir010svSearchWord() {
        return cir010svSearchWord__;
    }

    /**
     * <p>cir010svSearchWord をセットします。
     * @param cir010svSearchWord cir010svSearchWord
     */
    public void setCir010svSearchWord(String cir010svSearchWord) {
        cir010svSearchWord__ = cir010svSearchWord;
    }

    /**
     * <p>cir060order1 を取得します。
     * @return cir060order1
     */
    public int getCir060order1() {
        return cir060order1__;
    }

    /**
     * <p>cir060order1 をセットします。
     * @param cir060order1 cir060order1
     */
    public void setCir060order1(int cir060order1) {
        cir060order1__ = cir060order1;
    }

    /**
     * <p>cir060order2 を取得します。
     * @return cir060order2
     */
    public int getCir060order2() {
        return cir060order2__;
    }

    /**
     * <p>cir060order2 をセットします。
     * @param cir060order2 cir060order2
     */
    public void setCir060order2(int cir060order2) {
        cir060order2__ = cir060order2;
    }

    /**
     * <p>cir060pageNum1 を取得します。
     * @return cir060pageNum1
     */
    public int getCir060pageNum1() {
        return cir060pageNum1__;
    }

    /**
     * <p>cir060pageNum1 をセットします。
     * @param cir060pageNum1 cir060pageNum1
     */
    public void setCir060pageNum1(int cir060pageNum1) {
        cir060pageNum1__ = cir060pageNum1;
    }

    /**
     * <p>cir060pageNum2 を取得します。
     * @return cir060pageNum2
     */
    public int getCir060pageNum2() {
        return cir060pageNum2__;
    }

    /**
     * <p>cir060pageNum2 をセットします。
     * @param cir060pageNum2 cir060pageNum2
     */
    public void setCir060pageNum2(int cir060pageNum2) {
        cir060pageNum2__ = cir060pageNum2;
    }

    /**
     * <p>cir060searchFlg を取得します。
     * @return cir060searchFlg
     */
    public boolean isCir060searchFlg() {
        return cir060searchFlg__;
    }

    /**
     * <p>cir060searchFlg をセットします。
     * @param cir060searchFlg cir060searchFlg
     */
    public void setCir060searchFlg(boolean cir060searchFlg) {
        cir060searchFlg__ = cir060searchFlg;
    }

    /**
     * <p>cir060searchTarget を取得します。
     * @return cir060searchTarget
     */
    public String[] getCir060searchTarget() {
        return cir060searchTarget__;
    }

    /**
     * <p>cir060searchTarget をセットします。
     * @param cir060searchTarget cir060searchTarget
     */
    public void setCir060searchTarget(String[] cir060searchTarget) {
        cir060searchTarget__ = cir060searchTarget;
    }

    /**
     * <p>cir060sort1 を取得します。
     * @return cir060sort1
     */
    public int getCir060sort1() {
        return cir060sort1__;
    }

    /**
     * <p>cir060sort1 をセットします。
     * @param cir060sort1 cir060sort1
     */
    public void setCir060sort1(int cir060sort1) {
        cir060sort1__ = cir060sort1;
    }

    /**
     * <p>cir060sort2 を取得します。
     * @return cir060sort2
     */
    public int getCir060sort2() {
        return cir060sort2__;
    }

    /**
     * <p>cir060sort2 をセットします。
     * @param cir060sort2 cir060sort2
     */
    public void setCir060sort2(int cir060sort2) {
        cir060sort2__ = cir060sort2;
    }
    /**
     * <p>cir060svOrder1 を取得します。
     * @return cir060svOrder1
     */
    public int getCir060svOrder1() {
        return cir060svOrder1__;
    }

    /**
     * <p>cir060svOrder1 をセットします。
     * @param cir060svOrder1 cir060svOrder1
     */
    public void setCir060svOrder1(int cir060svOrder1) {
        cir060svOrder1__ = cir060svOrder1;
    }

    /**
     * <p>cir060svOrder2 を取得します。
     * @return cir060svOrder2
     */
    public int getCir060svOrder2() {
        return cir060svOrder2__;
    }

    /**
     * <p>cir060svOrder2 をセットします。
     * @param cir060svOrder2 cir060svOrder2
     */
    public void setCir060svOrder2(int cir060svOrder2) {
        cir060svOrder2__ = cir060svOrder2;
    }

    /**
     * <p>cir060svSearchTarget を取得します。
     * @return cir060svSearchTarget
     */
    public String[] getCir060svSearchTarget() {
        return cir060svSearchTarget__;
    }

    /**
     * <p>cir060svSearchTarget をセットします。
     * @param cir060svSearchTarget cir060svSearchTarget
     */
    public void setCir060svSearchTarget(String[] cir060svSearchTarget) {
        cir060svSearchTarget__ = cir060svSearchTarget;
    }

    /**
     * <p>cir060svSort1 を取得します。
     * @return cir060svSort1
     */
    public int getCir060svSort1() {
        return cir060svSort1__;
    }

    /**
     * <p>cir060svSort1 をセットします。
     * @param cir060svSort1 cir060svSort1
     */
    public void setCir060svSort1(int cir060svSort1) {
        cir060svSort1__ = cir060svSort1;
    }

    /**
     * <p>cir060svSort2 を取得します。
     * @return cir060svSort2
     */
    public int getCir060svSort2() {
        return cir060svSort2__;
    }

    /**
     * <p>cir060svSort2 をセットします。
     * @param cir060svSort2 cir060svSort2
     */
    public void setCir060svSort2(int cir060svSort2) {
        cir060svSort2__ = cir060svSort2;
    }

    /**
     * <p>cir060svSyubetsu を取得します。
     * @return cir060svSyubetsu
     */
    public int getCir060svSyubetsu() {
        return cir060svSyubetsu__;
    }

    /**
     * <p>cir060svSyubetsu をセットします。
     * @param cir060svSyubetsu cir060svSyubetsu
     */
    public void setCir060svSyubetsu(int cir060svSyubetsu) {
        cir060svSyubetsu__ = cir060svSyubetsu;
    }

    /**
     * <p>cir060svUserSid を取得します。
     * @return cir060svUserSid
     */
    public int getCir060svUserSid() {
        return cir060svUserSid__;
    }

    /**
     * <p>cir060svUserSid をセットします。
     * @param cir060svUserSid cir060svUserSid
     */
    public void setCir060svUserSid(int cir060svUserSid) {
        cir060svUserSid__ = cir060svUserSid;
    }

    /**
     * <p>cir060svWordKbn を取得します。
     * @return cir060svWordKbn
     */
    public int getCir060svWordKbn() {
        return cir060svWordKbn__;
    }

    /**
     * <p>cir060svWordKbn をセットします。
     * @param cir060svWordKbn cir060svWordKbn
     */
    public void setCir060svWordKbn(int cir060svWordKbn) {
        cir060svWordKbn__ = cir060svWordKbn;
    }

    /**
     * <p>cir060syubetsu を取得します。
     * @return cir060syubetsu
     */
    public int getCir060syubetsu() {
        return cir060syubetsu__;
    }

    /**
     * <p>cir060syubetsu をセットします。
     * @param cir060syubetsu cir060syubetsu
     */
    public void setCir060syubetsu(int cir060syubetsu) {
        cir060syubetsu__ = cir060syubetsu;
    }

    /**
     * <p>cir060userSid を取得します。
     * @return cir060userSid
     */
    public int getCir060userSid() {
        return cir060userSid__;
    }

    /**
     * <p>cir060userSid をセットします。
     * @param cir060userSid cir060userSid
     */
    public void setCir060userSid(int cir060userSid) {
        cir060userSid__ = cir060userSid;
    }

    /**
     * <p>cir060wordKbn を取得します。
     * @return cir060wordKbn
     */
    public int getCir060wordKbn() {
        return cir060wordKbn__;
    }

    /**
     * <p>cir060wordKbn をセットします。
     * @param cir060wordKbn cir060wordKbn
     */
    public void setCir060wordKbn(int cir060wordKbn) {
        cir060wordKbn__ = cir060wordKbn;
    }

    /**
     * <p>sortLabel を取得します。
     * @return sortLabel
     */
    public List<LabelValueBean> getSortLabel() {
        return sortLabel__;
    }

    /**
     * <p>sortLabel をセットします。
     * @param sortLabel sortLabel
     */
    public void setSortLabel(List<LabelValueBean> sortLabel) {
        sortLabel__ = sortLabel;
    }

    /**
     * <p>pageLabel を取得します。
     * @return pageLabel
     */
    public ArrayList<LabelValueBean> getPageLabel() {
        return pageLabel__;
    }

    /**
     * <p>pageLabel をセットします。
     * @param pageLabel pageLabel
     */
    public void setPageLabel(ArrayList<LabelValueBean> pageLabel) {
        pageLabel__ = pageLabel;
    }

    /**
     * <p>circularList を取得します。
     * @return circularList
     */
    public List<CircularDspModel> getCircularList() {
        return circularList__;
    }

    /**
     * <p>circularList をセットします。
     * @param circularList circularList
     */
    public void setCircularList(List<CircularDspModel> circularList) {
        circularList__ = circularList;
    }

    /**
     * <p>cir060searchUse を取得します。
     * @return cir060searchUse
     */
    public int getCir060searchUse() {
        return cir060searchUse__;
    }

    /**
     * <p>cir060searchUse をセットします。
     * @param cir060searchUse cir060searchUse
     */
    public void setCir060searchUse(int cir060searchUse) {
        cir060searchUse__ = cir060searchUse;
    }

    /**
     * <p>cir060WebSearchWord を取得します。
     * @return cir060WebSearchWord
     */
    public String getCir060WebSearchWord() {
        return cir060WebSearchWord__;
    }

    /**
     * <p>cir060WebSearchWord をセットします。
     * @param cir060WebSearchWord cir060WebSearchWord
     */
    public void setCir060WebSearchWord(String cir060WebSearchWord) {
        cir060WebSearchWord__ = cir060WebSearchWord;
    }

    /**
     * <p>cir060HtmlSearchWord を取得します。
     * @return cir060HtmlSearchWord
     */
    public String getCir060HtmlSearchWord() {
        return cir060HtmlSearchWord__;
    }

    /**
     * <p>cir060HtmlSearchWord をセットします。
     * @param cir060HtmlSearchWord cir060HtmlSearchWord
     */
    public void setCir060HtmlSearchWord(String cir060HtmlSearchWord) {
        cir060HtmlSearchWord__ = cir060HtmlSearchWord;
    }

    /**
     * <p>cir060selUserSid を取得します。
     * @return cir060selUserSid
     */
    public String[] getCir060selUserSid() {
        return cir060selUserSid__;
    }

    /**
     * <p>cir060selUserSid をセットします。
     * @param cir060selUserSid cir060selUserSid
     */
    public void setCir060selUserSid(String[] cir060selUserSid) {
        cir060selUserSid__ = cir060selUserSid;
    }

    /**
     * <p>cir060svSelUserSid を取得します。
     * @return cir060svSelUserSid
     */
    public String[] getCir060svSelUserSid() {
        return cir060svSelUserSid__;
    }

    /**
     * <p>cir060svSelUserSid をセットします。
     * @param cir060svSelUserSid cir060svSelUserSid
     */
    public void setCir060svSelUserSid(String[] cir060svSelUserSid) {
        cir060svSelUserSid__ = cir060svSelUserSid;
    }

    /**
     * <p>memberList を取得します。
     * @return memberList
     */
    public List<CirAccountModel> getMemberList() {
        return memberList__;
    }

    /**
     * <p>memberList をセットします。
     * @param memberList memberList
     */
    public void setMemberList(List<CirAccountModel> memberList) {
        memberList__ = memberList;
    }

    /**
     * <p>groupLabel を取得します。
     * @return groupLabel
     */
    public List<CmnLabelValueModel> getGroupLabel() {
        return groupLabel__;
    }

    /**
     * <p>groupLabel をセットします。
     * @param groupLabel groupLabel
     */
    public void setGroupLabel(List<CmnLabelValueModel> groupLabel) {
        groupLabel__ = groupLabel;
    }

    /**
     * <p>cir060groupSid を取得します。
     * @return cir060groupSid
     */
    public String getCir060groupSid() {
        return cir060groupSid__;
    }

    /**
     * <p>cir060groupSid をセットします。
     * @param cir060groupSid cir060groupSid
     */
    public void setCir060groupSid(String cir060groupSid) {
        cir060groupSid__ = cir060groupSid;
    }

    /**
     * <p>cir060svGroupSid を取得します。
     * @return cir060svGroupSid
     */
    public String getCir060svGroupSid() {
        return cir060svGroupSid__;
    }

    /**
     * <p>cir060svGroupSid をセットします。
     * @param cir060svGroupSid cir060svGroupSid
     */
    public void setCir060svGroupSid(String cir060svGroupSid) {
        cir060svGroupSid__ = cir060svGroupSid;
    }

    /**
     * <p>userLabel を取得します。
     * @return userLabel
     */
    public List<CircularUsrModel> getUserLabel() {
        return userLabel__;
    }

    /**
     * <p>userLabel をセットします。
     * @param userLabel userLabel
     */
    public void setUserLabel(List<CircularUsrModel> userLabel) {
        userLabel__ = userLabel;
    }

    /**
     * <p>cir060selGroupSid を取得します。
     * @return cir060selGroupSid
     * @see jp.groupsession.v2.cir.cir060.Cir060Form#cir060selGroupSid__
     */
    public String getCir060selGroupSid() {
        return cir060selGroupSid__;
    }

    /**
     * <p>cir060selGroupSid をセットします。
     * @param cir060selGroupSid cir060selGroupSid
     * @see jp.groupsession.v2.cir.cir060.Cir060Form#cir060selGroupSid__
     */
    public void setCir060selGroupSid(String cir060selGroupSid) {
        cir060selGroupSid__ = cir060selGroupSid;
    }

    /**
     * <p>cir060MemberSelector を取得します。
     * @return cir060MemberSelector
     * @see jp.groupsession.v2.cir.cir060.Cir060Form#cir060MemberSelector__
     */
    public AccountSelector getCir060MemberSelector() {
        return cir060MemberSelector__;
    }

    /**
     * <p>cir060MemberSelector をセットします。
     * @param cir060MemberSelector cir060MemberSelector
     * @see jp.groupsession.v2.cir.cir060.Cir060Form#cir060MemberSelector__
     */
    public void setCir060MemberSelector(AccountSelector cir060MemberSelector) {
        cir060MemberSelector__ = cir060MemberSelector;
    }

}
