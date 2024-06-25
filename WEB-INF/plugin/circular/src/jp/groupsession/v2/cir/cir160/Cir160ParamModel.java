package jp.groupsession.v2.cir.cir160;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir150.Cir150ParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 回覧板 アカウント登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir160ParamModel extends Cir150ParamModel {

    /** グループSID: グループ一覧 */
    public static final int GRP_SID_GRPLIST = -9;
    /** 使用者 グループを指定 */
    public static final int USERKBN_GROUP = 0;
    /** 使用者 ユーザを指定 */
    public static final int USERKBN_USER = 1;


    /** 選択タブ  */
    private int cir160SelTab__ = GSConstCircular.SEL_TAB_NORMAL;

    /** アカウント区分  */
    private int cir160AccountKbn__ = GSConstCircular.ACNT_DEF;

    /** アカウント名 */
    private String cir160name__ = null;
    /** デフォルトアカウント ユーザSID  */
    private int cir160DefActUsrSid__ = 0;

    /** 削除可能フラグ  */
    private int cir160CanDelFlg__ = GSConstCircular.ACCOUNT_DELETE_OK;

    /** 備考 */
    private String cir160biko__ = null;

    /** 使用者 ユーザ */
    private String[] cir160userKbnUser__ = null;


    /** 初期表示フラグ */
    private int cir160initFlg__ = 0;

    /** 使用者 設定フラグ */
    private boolean cir160acntUserFlg__ = false;

    /** 使用者 ユーザ グループ */
    private String cir160userKbnUserGroup__ = null;
    /** 使用者 ユーザ(選択用) コンボ 確認表示用 */
    private List<UsrLabelValueBean> userKbnUserSelectCombo__  = null;
    /** 使用者 ユーザ(選択用) コンボ */
    private UserGroupSelector userKbnUserSelector__
        = null;


    //--------- アカウントマネージャー画面のパラメータ
    /** 検索キーワード */
    private String cir150keyword__ = null;
    /** グループ */
    private int cir150group__ = -1;
    /** ユーザ */
    private int cir150user__ = -1;
    /** ページ上段 */
    private int cir150pageTop__ = 0;
    /** ページ下段 */
    private int cir150pageBottom__ = 0;

    /** 検索キーワード(検索条件保持) */
    private String cir150svKeyword__ = null;
    /** グループ(検索条件保持) */
    private int cir150svGroup__ = -1;
    /** ユーザ(検索条件保持) */
    private int cir150svUser__ = -1;

    /** ソートキー */
    private int cir150sortKey__ = GSConstCircular.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int cir150order__ = GSConstCircular.ORDER_ASC;

    /** 検索実行フラグ */
    private int cir150searchFlg__ = 0;

    /** 表示判定 */
    private int cir160elementKbn__ = 0;


    /** 自動削除区分 */
    private int cir160autoDelKbn__ = GSConstCircular.AUTO_DEL_ADM;
    /** 受信タブ 処理区分 */
    private String cir160JdelKbn__ = null;
    /** 送信タブ 処理区分 */
    private String cir160SdelKbn__ = null;
    /** ゴミ箱タブ 処理区分 */
    private String cir160DdelKbn__ = null;

    /**初期値設定区分 */
    private int cir160cirInitKbn__ = GSConstCircular.CIR_INIEDIT_STYPE_ADM;

    /** 年リスト */
    private ArrayList<LabelValueBean> cir160YearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> cir160MonthLabelList__ = null;
    /** 受信タブ 年選択 */
    private String cir160JYear__ = null;
    /** 受信タブ 月選択 */
    private String cir160JMonth__ = null;
    /** 送信タブ 年選択 */
    private String cir160SYear__ = null;
    /** 送信タブ 月選択 */
    private String cir160SMonth__ = null;
    /** ゴミ箱タブ 年選択 */
    private String cir160DYear__ = null;
    /** ゴミ箱タブ 月選択 */
    private String cir160DMonth__ = null;

    /** テーマ */
    private int cir160theme__ = 0;
    /** テーマ 一覧 */
    private List<LabelValueBean> cir160themeList__ = null;

    //--------- 初期値設定
    /** メモ欄修正区分 */
    private int cir160memoKbn__ = 0;
    /** メモ欄修正期限(期間指定) */
    private int cir160memoPeriod__ = 0;
    /**回覧先 公開／非公開 */
    private int cir160show__ = 0;

    /** ショートメール通知 受信・完了*/
    private int cir160smlNtf__ = GSConstCircular.SMAIL_TSUUCHI;
    /** ショートメール通知 メモ・確認時添付*/
    private int cir160smlMemo__ = GSConstCircular.SMAIL_TSUUCHI;
    /** ショートメール通知 編集*/
    private int cir160smlEdt__ = GSConstCircular.SMAIL_TSUUCHI;
    /** ショートメール通知区分 */
    private int cir160SmlNtfKbn__ = GSConstCircular.CAF_SML_NTF_USER;

    /**
     * <p>cir160biko を取得します。
     * @return cir160biko
     */
    public String getCir160biko() {
        return cir160biko__;
    }
    /**
     * <p>cir160biko をセットします。
     * @param cir160biko cir160biko
     */
    public void setCir160biko(String cir160biko) {
        cir160biko__ = cir160biko;
    }

    /**
     * <p>cir160name を取得します。
     * @return cir160name
     */
    public String getCir160name() {
        return cir160name__;
    }
    /**
     * <p>cir160name をセットします。
     * @param cir160name cir160name
     */
    public void setCir160name(String cir160name) {
        cir160name__ = cir160name;
    }
//
//    /**
//     * <p>cir160userKbn を取得します。
//     * @return cir160userKbn
//     */
//    public int getCir160userKbn() {
//        return cir160userKbn__;
//    }
//    /**
//     * <p>cir160userKbn をセットします。
//     * @param cir160userKbn cir160userKbn
//     */
//    public void setCir160userKbn(int cir160userKbn) {
//        cir160userKbn__ = cir160userKbn;
//    }
//    /**
//     * <p>cir160userKbnGroup を取得します。
//     * @return cir160userKbnGroup
//     */
//    public String[] getCir160userKbnGroup() {
//        return cir160userKbnGroup__;
//    }
//    /**
//     * <p>cir160userKbnGroup をセットします。
//     * @param cir160userKbnGroup cir160userKbnGroup
//     */
//    public void setCir160userKbnGroup(String[] cir160userKbnGroup) {
//        cir160userKbnGroup__ = cir160userKbnGroup;
//    }
    /**
     * <p>cir160userKbnUser を取得します。
     * @return cir160userKbnUser
     */
    public String[] getCir160userKbnUser() {
        return cir160userKbnUser__;
    }
    /**
     * <p>cir160userKbnUser をセットします。
     * @param cir160userKbnUser cir160userKbnUser
     */
    public void setCir160userKbnUser(String[] cir160userKbnUser) {
        cir160userKbnUser__ = cir160userKbnUser;
    }
    /**
     * <p>cir160initFlg を取得します。
     * @return cir160initFlg
     */
    public int getCir160initFlg() {
        return cir160initFlg__;
    }
    /**
     * <p>cir160initFlg をセットします。
     * @param cir160initFlg cir160initFlg
     */
    public void setCir160initFlg(int cir160initFlg) {
        cir160initFlg__ = cir160initFlg;
    }
    /**
     * <p>userKbnUserSelectCombo を取得します。
     * @return userKbnUserSelectCombo
     */
    public List<UsrLabelValueBean> getUserKbnUserSelectCombo() {
        return userKbnUserSelectCombo__;
    }
    /**
     * <p>userKbnUserSelectCombo をセットします。
     * @param userKbnUserSelectCombo userKbnUserSelectCombo
     */
    public void setUserKbnUserSelectCombo(
            List<UsrLabelValueBean> userKbnUserSelectCombo) {
        userKbnUserSelectCombo__ = userKbnUserSelectCombo;
    }
    /**
     * <p>userKbnUserSelector を取得します。
     * @return userKbnUserSelector
     * @see jp.groupsession.v2.cir.cir160.Cir160ParamModel#userKbnUserSelector__
     */
    public UserGroupSelector getUserKbnUserSelector() {
        return userKbnUserSelector__;
    }
    /**
     * <p>userKbnUserSelector をセットします。
     * @param userKbnUserSelector userKbnUserSelector
     * @see jp.groupsession.v2.cir.cir160.Cir160ParamModel#userKbnUserSelector__
     */
    public void setUserKbnUserSelector(UserGroupSelector userKbnUserSelector) {
        userKbnUserSelector__ = userKbnUserSelector;
    }
    /**
     * <p>cir160userKbnUserGroup を取得します。
     * @return cir160userKbnUserGroup
     */
    public String getCir160userKbnUserGroup() {
        return cir160userKbnUserGroup__;
    }
    /**
     * <p>cir160userKbnUserGroup をセットします。
     * @param cir160userKbnUserGroup cir160userKbnUserGroup
     */
    public void setCir160userKbnUserGroup(String cir160userKbnUserGroup) {
        cir160userKbnUserGroup__ = cir160userKbnUserGroup;
    }
    /**
     * <p>cir150group を取得します。
     * @return cir150group
     */
    public int getCir150group() {
        return cir150group__;
    }
    /**
     * <p>cir150group をセットします。
     * @param cir150group cir150group
     */
    public void setCir150group(int cir150group) {
        cir150group__ = cir150group;
    }
    /**
     * <p>cir150keyword を取得します。
     * @return cir150keyword
     */
    public String getCir150keyword() {
        return cir150keyword__;
    }
    /**
     * <p>cir150keyword をセットします。
     * @param cir150keyword cir150keyword
     */
    public void setCir150keyword(String cir150keyword) {
        cir150keyword__ = cir150keyword;
    }
    /**
     * <p>cir150order を取得します。
     * @return cir150order
     */
    public int getCir150order() {
        return cir150order__;
    }
    /**
     * <p>cir150order をセットします。
     * @param cir150order cir150order
     */
    public void setCir150order(int cir150order) {
        cir150order__ = cir150order;
    }
    /**
     * <p>cir150pageBottom を取得します。
     * @return cir150pageBottom
     */
    public int getCir150pageBottom() {
        return cir150pageBottom__;
    }
    /**
     * <p>cir150pageBottom をセットします。
     * @param cir150pageBottom cir150pageBottom
     */
    public void setCir150pageBottom(int cir150pageBottom) {
        cir150pageBottom__ = cir150pageBottom;
    }
    /**
     * <p>cir150pageTop を取得します。
     * @return cir150pageTop
     */
    public int getCir150pageTop() {
        return cir150pageTop__;
    }
    /**
     * <p>cir150pageTop をセットします。
     * @param cir150pageTop cir150pageTop
     */
    public void setCir150pageTop(int cir150pageTop) {
        cir150pageTop__ = cir150pageTop;
    }
    /**
     * <p>cir150searchFlg を取得します。
     * @return cir150searchFlg
     */
    public int getCir150searchFlg() {
        return cir150searchFlg__;
    }
    /**
     * <p>cir150searchFlg をセットします。
     * @param cir150searchFlg cir150searchFlg
     */
    public void setCir150searchFlg(int cir150searchFlg) {
        cir150searchFlg__ = cir150searchFlg;
    }
    /**
     * <p>cir150sortKey を取得します。
     * @return cir150sortKey
     */
    public int getCir150sortKey() {
        return cir150sortKey__;
    }
    /**
     * <p>cir150sortKey をセットします。
     * @param cir150sortKey cir150sortKey
     */
    public void setCir150sortKey(int cir150sortKey) {
        cir150sortKey__ = cir150sortKey;
    }
    /**
     * <p>cir150svGroup を取得します。
     * @return cir150svGroup
     */
    public int getCir150svGroup() {
        return cir150svGroup__;
    }
    /**
     * <p>cir150svGroup をセットします。
     * @param cir150svGroup cir150svGroup
     */
    public void setCir150svGroup(int cir150svGroup) {
        cir150svGroup__ = cir150svGroup;
    }
    /**
     * <p>cir150svKeyword を取得します。
     * @return cir150svKeyword
     */
    public String getCir150svKeyword() {
        return cir150svKeyword__;
    }
    /**
     * <p>cir150svKeyword をセットします。
     * @param cir150svKeyword cir150svKeyword
     */
    public void setCir150svKeyword(String cir150svKeyword) {
        cir150svKeyword__ = cir150svKeyword;
    }
    /**
     * <p>cir150svUser を取得します。
     * @return cir150svUser
     */
    public int getCir150svUser() {
        return cir150svUser__;
    }
    /**
     * <p>cir150svUser をセットします。
     * @param cir150svUser cir150svUser
     */
    public void setCir150svUser(int cir150svUser) {
        cir150svUser__ = cir150svUser;
    }
    /**
     * <p>cir150user を取得します。
     * @return cir150user
     */
    public int getCir150user() {
        return cir150user__;
    }
    /**
     * <p>cir150user をセットします。
     * @param cir150user cir150user
     */
    public void setCir150user(int cir150user) {
        cir150user__ = cir150user;
    }
    /**
     * <p>cir160elementKbn を取得します。
     * @return cir160elementKbn
     */
    public int getCir160elementKbn() {
        return cir160elementKbn__;
    }

    /**
     * <p>cir160elementKbn をセットします。
     * @param cir160elementKbn cir160elementKbn
     */
    public void setCir160elementKbn(int cir160elementKbn) {
        cir160elementKbn__ = cir160elementKbn;
    }

    /**
     * <p>cir160AccountKbn を取得します。
     * @return cir160AccountKbn
     */
    public int getCir160AccountKbn() {
        return cir160AccountKbn__;
    }
    /**
     * <p>cir160AccountKbn をセットします。
     * @param cir160AccountKbn cir160AccountKbn
     */
    public void setCir160AccountKbn(int cir160AccountKbn) {
        cir160AccountKbn__ = cir160AccountKbn;
    }
    /**
     * <p>cir160DefActUsrSid を取得します。
     * @return cir160DefActUsrSid
     */
    public int getCir160DefActUsrSid() {
        return cir160DefActUsrSid__;
    }
    /**
     * <p>cir160DefActUsrSid をセットします。
     * @param cir160DefActUsrSid cir160DefActUsrSid
     */
    public void setCir160DefActUsrSid(int cir160DefActUsrSid) {
        cir160DefActUsrSid__ = cir160DefActUsrSid;
    }
    /**
     * <p>cir160JdelKbn を取得します。
     * @return cir160JdelKbn
     */
    public String getCir160JdelKbn() {
        return cir160JdelKbn__;
    }
    /**
     * <p>cir160JdelKbn をセットします。
     * @param cir160JdelKbn cir160JdelKbn
     */
    public void setCir160JdelKbn(String cir160JdelKbn) {
        cir160JdelKbn__ = cir160JdelKbn;
    }
    /**
     * <p>cir160SdelKbn を取得します。
     * @return cir160SdelKbn
     */
    public String getCir160SdelKbn() {
        return cir160SdelKbn__;
    }
    /**
     * <p>cir160SdelKbn をセットします。
     * @param cir160SdelKbn cir160SdelKbn
     */
    public void setCir160SdelKbn(String cir160SdelKbn) {
        cir160SdelKbn__ = cir160SdelKbn;
    }
    /**
     * <p>cir160DdelKbn を取得します。
     * @return cir160DdelKbn
     */
    public String getCir160DdelKbn() {
        return cir160DdelKbn__;
    }
    /**
     * <p>cir160DdelKbn をセットします。
     * @param cir160DdelKbn cir160DdelKbn
     */
    public void setCir160DdelKbn(String cir160DdelKbn) {
        cir160DdelKbn__ = cir160DdelKbn;
    }
    /**
     * <p>cir160YearLabelList を取得します。
     * @return cir160YearLabelList
     */
    public ArrayList<LabelValueBean> getCir160YearLabelList() {
        return cir160YearLabelList__;
    }
    /**
     * <p>cir160YearLabelList をセットします。
     * @param cir160YearLabelList cir160YearLabelList
     */
    public void setCir160YearLabelList(ArrayList<LabelValueBean> cir160YearLabelList) {
        cir160YearLabelList__ = cir160YearLabelList;
    }
    /**
     * <p>cir160MonthLabelList を取得します。
     * @return cir160MonthLabelList
     */
    public ArrayList<LabelValueBean> getCir160MonthLabelList() {
        return cir160MonthLabelList__;
    }
    /**
     * <p>cir160MonthLabelList をセットします。
     * @param cir160MonthLabelList cir160MonthLabelList
     */
    public void setCir160MonthLabelList(
            ArrayList<LabelValueBean> cir160MonthLabelList) {
        cir160MonthLabelList__ = cir160MonthLabelList;
    }
    /**
     * <p>cir160JYear を取得します。
     * @return cir160JYear
     */
    public String getCir160JYear() {
        return cir160JYear__;
    }
    /**
     * <p>cir160JYear をセットします。
     * @param cir160jYear cir160JYear
     */
    public void setCir160JYear(String cir160jYear) {
        cir160JYear__ = cir160jYear;
    }
    /**
     * <p>cir160JMonth を取得します。
     * @return cir160JMonth
     */
    public String getCir160JMonth() {
        return cir160JMonth__;
    }
    /**
     * <p>cir160JMonth をセットします。
     * @param cir160jMonth cir160JMonth
     */
    public void setCir160JMonth(String cir160jMonth) {
        cir160JMonth__ = cir160jMonth;
    }
    /**
     * <p>cir160SYear を取得します。
     * @return cir160SYear
     */
    public String getCir160SYear() {
        return cir160SYear__;
    }
    /**
     * <p>cir160SYear をセットします。
     * @param cir160sYear cir160SYear
     */
    public void setCir160SYear(String cir160sYear) {
        cir160SYear__ = cir160sYear;
    }
    /**
     * <p>cir160SMonth を取得します。
     * @return cir160SMonth
     */
    public String getCir160SMonth() {
        return cir160SMonth__;
    }
    /**
     * <p>cir160SMonth をセットします。
     * @param cir160sMonth cir160SMonth
     */
    public void setCir160SMonth(String cir160sMonth) {
        cir160SMonth__ = cir160sMonth;
    }
    /**
     * <p>cir160DYear を取得します。
     * @return cir160DYear
     */
    public String getCir160DYear() {
        return cir160DYear__;
    }
    /**
     * <p>cir160DYear をセットします。
     * @param cir160dYear cir160DYear
     */
    public void setCir160DYear(String cir160dYear) {
        cir160DYear__ = cir160dYear;
    }
    /**
     * <p>cir160DMonth を取得します。
     * @return cir160DMonth
     */
    public String getCir160DMonth() {
        return cir160DMonth__;
    }
    /**
     * <p>cir160DMonth をセットします。
     * @param cir160dMonth cir160DMonth
     */
    public void setCir160DMonth(String cir160dMonth) {
        cir160DMonth__ = cir160dMonth;
    }
    /**
     * <p>cir160autoDelKbn を取得します。
     * @return cir160autoDelKbn
     */
    public int getCir160autoDelKbn() {
        return cir160autoDelKbn__;
    }
    /**
     * <p>cir160autoDelKbn をセットします。
     * @param cir160autoDelKbn cir160autoDelKbn
     */
    public void setCir160autoDelKbn(int cir160autoDelKbn) {
        cir160autoDelKbn__ = cir160autoDelKbn;
    }

    /**
     * <p>cir160SelTab を取得します。
     * @return cir160SelTab
     */
    public int getCir160SelTab() {
        return cir160SelTab__;
    }
    /**
     * <p>cir160SelTab をセットします。
     * @param cir160SelTab cir160SelTab
     */
    public void setCir160SelTab(int cir160SelTab) {
        cir160SelTab__ = cir160SelTab;
    }
    /**
     * <p>cir160CanDelFlg を取得します。
     * @return cir160CanDelFlg
     */
    public int getCir160CanDelFlg() {
        return cir160CanDelFlg__;
    }
    /**
     * <p>cir160CanDelFlg をセットします。
     * @param cir160CanDelFlg cir160CanDelFlg
     */
    public void setCir160CanDelFlg(int cir160CanDelFlg) {
        cir160CanDelFlg__ = cir160CanDelFlg;
    }
    /**
     * <p>cir160theme を取得します。
     * @return cir160theme
     */
    public int getCir160theme() {
        return cir160theme__;
    }
    /**
     * <p>cir160theme をセットします。
     * @param cir160theme cir160theme
     */
    public void setCir160theme(int cir160theme) {
        cir160theme__ = cir160theme;
    }
    /**
     * <p>cir160themeList を取得します。
     * @return cir160themeList
     */
    public List<LabelValueBean> getCir160themeList() {
        return cir160themeList__;
    }
    /**
     * <p>cir160themeList をセットします。
     * @param cir160themeList cir160themeList
     */
    public void setCir160themeList(List<LabelValueBean> cir160themeList) {
        cir160themeList__ = cir160themeList;
    }
    /**
     * <p>cir160cirInitKbn を取得します。
     * @return cir160cirInitKbn
     */
    public int getCir160cirInitKbn() {
        return cir160cirInitKbn__;
    }
    /**
     * <p>cir160cirInitKbn をセットします。
     * @param cir160cirInitKbn cir160cirInitKbn
     */
    public void setCir160cirInitKbn(int cir160cirInitKbn) {
        cir160cirInitKbn__ = cir160cirInitKbn;
    }
    /**
     * <p>cir160memoKbn を取得します。
     * @return cir160memoKbn
     */
    public int getCir160memoKbn() {
        return cir160memoKbn__;
    }
    /**
     * <p>cir160memoKbn をセットします。
     * @param cir160memoKbn cir160memoKbn
     */
    public void setCir160memoKbn(int cir160memoKbn) {
        cir160memoKbn__ = cir160memoKbn;
    }
    /**
     * <p>cir160memoPeriod を取得します。
     * @return cir160memoPeriod
     */
    public int getCir160memoPeriod() {
        return cir160memoPeriod__;
    }
    /**
     * <p>cir160memoPeriod をセットします。
     * @param cir160memoPeriod cir160memoPeriod
     */
    public void setCir160memoPeriod(int cir160memoPeriod) {
        cir160memoPeriod__ = cir160memoPeriod;
    }
    /**
     * <p>cir160show を取得します。
     * @return cir160show
     */
    public int getCir160show() {
        return cir160show__;
    }
    /**
     * <p>cir160show をセットします。
     * @param cir160show cir160show
     */
    public void setCir160show(int cir160show) {
        cir160show__ = cir160show;
    }
    /**
     * <p>cir160smlNtf を取得します。
     * @return cir160smlNtf
     */
    public int getCir160smlNtf() {
        return cir160smlNtf__;
    }
    /**
     * <p>cir160smlNtf をセットします。
     * @param cir160smlNtf cir160smlNtf
     */
    public void setCir160smlNtf(int cir160smlNtf) {
        cir160smlNtf__ = cir160smlNtf;
    }
    /**
     * <p>cir160SmlNtfKbn を取得します。
     * @return cir160SmlNtfKbn
     */
    public int getCir160SmlNtfKbn() {
        return cir160SmlNtfKbn__;
    }
    /**
     * <p>cir160SmlNtfKbn をセットします。
     * @param cir160SmlNtfKbn cir160SmlNtfKbn
     */
    public void setCir160SmlNtfKbn(int cir160SmlNtfKbn) {
        cir160SmlNtfKbn__ = cir160SmlNtfKbn;
    }

    /**
     * <p>cir160acntUserFlg を取得します。
     * @return cir160acntUserFlg
     */
    public boolean isCir160acntUserFlg() {
        return cir160acntUserFlg__;
    }

    /**
     * <p>cir160acntUserFlg をセットします。
     * @param cir160acntUserFlg cir160acntUserFlg
     */
    public void setCir160acntUserFlg(boolean cir160acntUserFlg) {
        cir160acntUserFlg__ = cir160acntUserFlg;
    }
    /**
     * <p>cir160smlMemo を取得します。
     * @return cir160smlMemo
     * @see jp.groupsession.v2.cir.cir160.Cir160ParamModel#cir160smlMemo__
     */
    public int getCir160smlMemo() {
        return cir160smlMemo__;
    }
    /**
     * <p>cir160smlMemo をセットします。
     * @param cir160smlMemo cir160smlMemo
     * @see jp.groupsession.v2.cir.cir160.Cir160ParamModel#cir160smlMemo__
     */
    public void setCir160smlMemo(int cir160smlMemo) {
        cir160smlMemo__ = cir160smlMemo;
    }
    /**
     * <p>cir160smlEdt を取得します。
     * @return cir160smlEdt
     * @see jp.groupsession.v2.cir.cir160.Cir160ParamModel#cir160smlEdt__
     */
    public int getCir160smlEdt() {
        return cir160smlEdt__;
    }
    /**
     * <p>cir160smlEdt をセットします。
     * @param cir160smlEdt cir160smlEdt
     * @see jp.groupsession.v2.cir.cir160.Cir160ParamModel#cir160smlEdt__
     */
    public void setCir160smlEdt(int cir160smlEdt) {
        cir160smlEdt__ = cir160smlEdt;
    }

}