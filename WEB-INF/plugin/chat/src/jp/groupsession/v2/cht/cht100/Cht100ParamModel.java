package jp.groupsession.v2.cht.cht100;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht020.Cht020ParamModel;
import jp.groupsession.v2.cht.model.ChatInformationModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] チャットグループ管理のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht100ParamModel extends Cht020ParamModel {

    /** 初期表示区分 */
    private int cht100InitFlg__ = GSConstChat.DSP_FIRST;
    /** チャットグループSID */
    private int cgiSid__ = -1;
    /** チャットグループ登録編集区分 */
    private int cht100ProcMode__ = GSConstChat.CHAT_MODE_ADD;
    /** キーワード */
    private String cht100Keyword__ = null;
    /** キーワード検索方法 */
    private int cht100AndOr__ = GSConstChat.KEYWORDKBN_AND;
    /** 検索条件:グループID */
    private int cht100GroupId__ = GSConstChat.SEARCH_GROUPID_OUT;
    /** 検索条件:グループ名 */
    private int cht100GroupName__ = GSConstChat.SEARCH_GROUPNAME_OUT;
    /** 検索条件:備考 */
    private int cht100GroupInfo__ = GSConstChat.SEARCH_GROUPINFO_OUT;
    /** カテゴリ */
    private int cht100Category__ = GSConstChat.CHAT_CHC_SID_ALL;
    /** 状態区分 */
    private int cht100StatusKbn__ = GSConstChat.SEARCH_STATUSKBN_ALL;
    /** グループ */
    private int cht100SelectGroup__ = -1;
    /** ユーザ */
    private int cht100SelectUser__ = -1;
    /** メンバー検索条件：管理者 */
    private int cht100AdminMember__ = GSConstChat.SEARCH_GROUPADMIN_OUT;
    /** メンバー検索条件：一般ユーザ */
    private int cht100GeneralMember__ = GSConstChat.SEARCH_GENERALUSER_OUT;
    /** 作成日時 年 From */
    private int cht100CreateYearFr__ = -1;
    /** 作成日時 月 From */
    private int cht100CreateMonthFr__ = -1;
    /** 作成日時 日 From */
    private int cht100CreateDayFr__ = -1;
    /** 作成日時 年 To */
    private int cht100CreateYearTo__ = -1;
    /** 作成日時 月 To */
    private int cht100CreateMonthTo__ = -1;
    /** 作成日時 日 To */
    private int cht100CreateDayTo__ = -1;
    /** 作成日時 年月日From */
    private String cht100CreateDateFr__ = null;
    /** 作成日時 年月日To */
    private String cht100CreateDateTo__ = null;
    /** 最終投稿日時 年 From */
    private int cht100UpdateYearFr__ = -1;
    /** 最終投稿日時 月 From */
    private int cht100UpdateMonthFr__ = -1;
    /** 最終投稿日時 日 From */
    private int cht100UpdateDayFr__ = -1;
    /** 最終投稿日時 年 To */
    private int cht100UpdateYearTo__ = -1;
    /** 最終投稿日時 月 To */
    private int cht100UpdateMonthTo__ = -1;
    /** 最終投稿日時 日 To */
    private int cht100UpdateDayTo__ = -1;
    /** 最終投稿日時 年月日From */
    private String cht100UpdateDateFr__ = null;
    /** 最終投稿日時 年月日To */
    private String cht100UpdateDateTo__ = null;
    /** ソートキー */
    private int cht100SortKey__ = GSConstChat.CHAT_SORT_GRPNAME;
    /** オーダーキー */
    private int cht100OrderKey__ = GSConstChat.CHAT_ORDER_ASC;

    /** キーワード(検索条件保存用)*/
    private String svCht100Keyword__ = null;
    /** キーワード検索方法(検索条件保存用)*/
    private int svCht100AndOr__ = GSConstChat.KEYWORDKBN_AND;
    /** 検索条件:グループID(検索条件保存用)*/
    private int svCht100GroupId__ = GSConstChat.SEARCH_GROUPID_IN;
    /** 検索条件:グループ名(検索条件保存用)*/
    private int svCht100GroupName__ = GSConstChat.SEARCH_GROUPNAME_IN;
    /** 検索条件:備考(検索条件保存用)*/
    private int svCht100GroupInfo__ = GSConstChat.SEARCH_GROUPINFO_IN;
    /** カテゴリ(検索条件保存用)*/
    private int svCht100Category__ = GSConstChat.CHAT_CHC_SID_ALL;
    /** 状態区分(検索条件保存用)*/
    private int svCht100StatusKbn__ = GSConstChat.SEARCH_STATUSKBN_ALL;
    /** グループ(検索条件保存用)*/
    private int svCht100SelectGroup__ = -1;
    /** ユーザ(検索条件保存用)*/
    private int svCht100SelectUser__ = -1;
    /** メンバー検索条件：管理者(検索条件保存用)*/
    private int svCht100AdminMember__ = GSConstChat.SEARCH_GROUPADMIN_IN;
    /** メンバー検索条件：一般ユーザ(検索条件保存用)*/
    private int svCht100GeneralMember__ = GSConstChat.SEARCH_GENERALUSER_IN;
    /** 作成日時 年 From(検索条件保存用)*/
    private int svCht100CreateYearFr__ = -1;
    /** 作成日時 月 From(検索条件保存用)*/
    private int svCht100CreateMonthFr__ = -1;
    /** 作成日時 日 From(検索条件保存用)*/
    private int svCht100CreateDayFr__ = -1;
    /** 作成日時 年 To(検索条件保存用)*/
    private int svCht100CreateYearTo__ = -1;
    /** 作成日時 月 To(検索条件保存用)*/
    private int svCht100CreateMonthTo__ = -1;
    /** 作成日時 日 To(検索条件保存用)*/
    private int svCht100CreateDayTo__ = -1;
    /** 作成日時 年月日From(検索条件保存用) */
    private String svCht100CreateDateFr__ = null;
    /** 作成日時 年月日To(検索条件保存用) */
    private String svCht100CreateDateTo__ = null;
    /** 最終投稿日時 年 From(検索条件保存用)*/
    private int svCht100UpdateYearFr__ = -1;
    /** 最終投稿日時 月 From(検索条件保存用)*/
    private int svCht100UpdateMonthFr__ = -1;
    /** 最終投稿日時 日 From(検索条件保存用)*/
    private int svCht100UpdateDayFr__ = -1;
    /** 最終投稿日時 年 To(検索条件保存用)*/
    private int svCht100UpdateYearTo__ = -1;
    /** 最終投稿日時 月 To(検索条件保存用)*/
    private int svCht100UpdateMonthTo__ = -1;
    /** 最終投稿日時 日 To(検索条件保存用)*/
    private int svCht100UpdateDayTo__ = -1;
    /** 最終投稿日時 年月日From(検索条件保存用) */
    private String svCht100UpdateDateFr__ = null;
    /** 最終投稿日時 年月日To(検索条件保存用) */
    private String svCht100UpdateDateTo__ = null;
    /** ソートキー */
    private int svCht100SortKey__ = GSConstChat.CHAT_SORT_GRPID;
    /** オーダーキー */
    private int svCht100OrderKey__ = GSConstChat.CHAT_ORDER_ASC;

    /** ページコンボ上段 */
    private int cht100PageTop__ = 1;
    /** ページコンボ下段 */
    private int cht100PageBottom__ = 1;
    /** カテゴリリスト*/
    private List <LabelValueBean> cht100CategoryList__ = null;
    /** グループリスト*/
    private List <LabelValueBean> cht100GroupList__ = null;
    /** ユーザリスト*/
    private List <UsrLabelValueBean> cht100UserList__ = null;
    /** ページコンボリスト */
    private List < LabelValueBean > pageList__ = null;
    /** 検索結果一覧 */
    List<ChatInformationModel> cht100GrpDataList__ = null;

    /** 検索フラグ */
    private int cht100SearchFlg__ = 1;
    /** デートピッカー　年度コンボ 設定 */
    private int cht100OldYear__ = 0;

    /**
     * <p>cht100InitFlg を取得します。
     * @return cht100InitFlg
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100InitFlg__
     */
    public int getCht100InitFlg() {
        return cht100InitFlg__;
    }
    /**
     * <p>cht100InitFlg をセットします。
     * @param cht100InitFlg cht100InitFlg
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100InitFlg__
     */
    public void setCht100InitFlg(int cht100InitFlg) {
        cht100InitFlg__ = cht100InitFlg;
    }
    /**
     * <p>cht100Keyword を取得します。
     * @return cht100Keyword
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100Keyword__
     */
    public String getCht100Keyword() {
        return cht100Keyword__;
    }
    /**
     * <p>cht100SearchFlg を取得します。
     * @return cht100SearchFlg
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100SearchFlg__
     */
    public int getCht100SearchFlg() {
        return cht100SearchFlg__;
    }
    /**
     * <p>cht100SearchFlg をセットします。
     * @param cht100SearchFlg cht100SearchFlg
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100SearchFlg__
     */
    public void setCht100SearchFlg(int cht100SearchFlg) {
        cht100SearchFlg__ = cht100SearchFlg;
    }
    /**
     * <p>cht100Keyword をセットします。
     * @param cht100Keyword cht100Keyword
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100Keyword__
     */
    public void setCht100Keyword(String cht100Keyword) {
        cht100Keyword__ = cht100Keyword;
    }
    /**
     * <p>cht100AndOr を取得します。
     * @return cht100AndOr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100AndOr__
     */
    public int getCht100AndOr() {
        return cht100AndOr__;
    }
    /**
     * <p>cht100AndOr をセットします。
     * @param cht100AndOr cht100AndOr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100AndOr__
     */
    public void setCht100AndOr(int cht100AndOr) {
        cht100AndOr__ = cht100AndOr;
    }
    /**
     * <p>cht100GroupId を取得します。
     * @return cht100GroupId
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GroupId__
     */
    public int getCht100GroupId() {
        return cht100GroupId__;
    }
    /**
     * <p>cht100GroupId をセットします。
     * @param cht100GroupId cht100GroupId
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GroupId__
     */
    public void setCht100GroupId(int cht100GroupId) {
        cht100GroupId__ = cht100GroupId;
    }
    /**
     * <p>cht100GroupName を取得します。
     * @return cht100GroupName
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GroupName__
     */
    public int getCht100GroupName() {
        return cht100GroupName__;
    }
    /**
     * <p>cht100GroupName をセットします。
     * @param cht100GroupName cht100GroupName
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GroupName__
     */
    public void setCht100GroupName(int cht100GroupName) {
        cht100GroupName__ = cht100GroupName;
    }
    /**
     * <p>cht100GroupInfo を取得します。
     * @return cht100GroupInfo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GroupInfo__
     */
    public int getCht100GroupInfo() {
        return cht100GroupInfo__;
    }
    /**
     * <p>cht100GroupInfo をセットします。
     * @param cht100GroupInfo cht100GroupInfo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GroupInfo__
     */
    public void setCht100GroupInfo(int cht100GroupInfo) {
        cht100GroupInfo__ = cht100GroupInfo;
    }
    /**
     * <p>cht100Category を取得します。
     * @return cht100Category
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100Category__
     */
    public int getCht100Category() {
        return cht100Category__;
    }
    /**
     * <p>cht100Category をセットします。
     * @param cht100Category cht100Category
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100Category__
     */
    public void setCht100Category(int cht100Category) {
        cht100Category__ = cht100Category;
    }
    /**
     * <p>cht100StatusKbn を取得します。
     * @return cht100StatusKbn
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100StatusKbn__
     */
    public int getCht100StatusKbn() {
        return cht100StatusKbn__;
    }
    /**
     * <p>cht100StatusKbn をセットします。
     * @param cht100StatusKbn cht100StatusKbn
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100StatusKbn__
     */
    public void setCht100StatusKbn(int cht100StatusKbn) {
        cht100StatusKbn__ = cht100StatusKbn;
    }
    /**
     * <p>cht100SelectGroup を取得します。
     * @return cht100SelectGroup
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100SelectGroup__
     */
    public int getCht100SelectGroup() {
        return cht100SelectGroup__;
    }
    /**
     * <p>cht100SelectGroup をセットします。
     * @param cht100SelectGroup cht100SelectGroup
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100SelectGroup__
     */
    public void setCht100SelectGroup(int cht100SelectGroup) {
        cht100SelectGroup__ = cht100SelectGroup;
    }
    /**
     * <p>cht100SelectUser を取得します。
     * @return cht100SelectUser
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100SelectUser__
     */
    public int getCht100SelectUser() {
        return cht100SelectUser__;
    }
    /**
     * <p>cht100SelectUser をセットします。
     * @param cht100SelectUser cht100SelectUser
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100SelectUser__
     */
    public void setCht100SelectUser(int cht100SelectUser) {
        cht100SelectUser__ = cht100SelectUser;
    }
    /**
     * <p>cht100AdminMember を取得します。
     * @return cht100AdminMember
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100AdminMember__
     */
    public int getCht100AdminMember() {
        return cht100AdminMember__;
    }
    /**
     * <p>cht100AdminMember をセットします。
     * @param cht100AdminMember cht100AdminMember
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100AdminMember__
     */
    public void setCht100AdminMember(int cht100AdminMember) {
        cht100AdminMember__ = cht100AdminMember;
    }
    /**
     * <p>cht100GeneralMember を取得します。
     * @return cht100GeneralMember
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GeneralMember__
     */
    public int getCht100GeneralMember() {
        return cht100GeneralMember__;
    }
    /**
     * <p>cht100GeneralMember をセットします。
     * @param cht100GeneralMember cht100GeneralMember
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GeneralMember__
     */
    public void setCht100GeneralMember(int cht100GeneralMember) {
        cht100GeneralMember__ = cht100GeneralMember;
    }
    /**
     * <p>cht100CreateYearFr を取得します。
     * @return cht100CreateYearFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateYearFr__
     */
    public int getCht100CreateYearFr() {
        return cht100CreateYearFr__;
    }
    /**
     * <p>cht100CreateYearFr をセットします。
     * @param cht100CreateYearFr cht100CreateYearFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateYearFr__
     */
    public void setCht100CreateYearFr(int cht100CreateYearFr) {
        cht100CreateYearFr__ = cht100CreateYearFr;
    }
    /**
     * <p>cht100CreateMonthFr を取得します。
     * @return cht100CreateMonthFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateMonthFr__
     */
    public int getCht100CreateMonthFr() {
        return cht100CreateMonthFr__;
    }
    /**
     * <p>cht100CreateMonthFr をセットします。
     * @param cht100CreateMonthFr cht100CreateMonthFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateMonthFr__
     */
    public void setCht100CreateMonthFr(int cht100CreateMonthFr) {
        cht100CreateMonthFr__ = cht100CreateMonthFr;
    }
    /**
     * <p>cht100CreateDayFr を取得します。
     * @return cht100CreateDayFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateDayFr__
     */
    public int getCht100CreateDayFr() {
        return cht100CreateDayFr__;
    }
    /**
     * <p>cht100CreateDayFr をセットします。
     * @param cht100CreateDayFr cht100CreateDayFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateDayFr__
     */
    public void setCht100CreateDayFr(int cht100CreateDayFr) {
        cht100CreateDayFr__ = cht100CreateDayFr;
    }
    /**
     * <p>cht100CreateYearTo を取得します。
     * @return cht100CreateYearTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateYearTo__
     */
    public int getCht100CreateYearTo() {
        return cht100CreateYearTo__;
    }
    /**
     * <p>cht100CreateYearTo をセットします。
     * @param cht100CreateYearTo cht100CreateYearTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateYearTo__
     */
    public void setCht100CreateYearTo(int cht100CreateYearTo) {
        cht100CreateYearTo__ = cht100CreateYearTo;
    }
    /**
     * <p>cht100CreateMonthTo を取得します。
     * @return cht100CreateMonthTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateMonthTo__
     */
    public int getCht100CreateMonthTo() {
        return cht100CreateMonthTo__;
    }
    /**
     * <p>cht100CreateMonthTo をセットします。
     * @param cht100CreateMonthTo cht100CreateMonthTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateMonthTo__
     */
    public void setCht100CreateMonthTo(int cht100CreateMonthTo) {
        cht100CreateMonthTo__ = cht100CreateMonthTo;
    }
    /**
     * <p>cht100CreateDayTo を取得します。
     * @return cht100CreateDayTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateDayTo__
     */
    public int getCht100CreateDayTo() {
        return cht100CreateDayTo__;
    }
    /**
     * <p>cht100CreateDayTo をセットします。
     * @param cht100CreateDayTo cht100CreateDayTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CreateDayTo__
     */
    public void setCht100CreateDayTo(int cht100CreateDayTo) {
        cht100CreateDayTo__ = cht100CreateDayTo;
    }
    /**
     * <p>cht100CreateDateFr を取得します。
     * @return cht100CreateDateFr
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#cht100CreateDateFr__
     */
    public String getCht100CreateDateFr() {
        return cht100CreateDateFr__;
    }
    /**
     * <p>cht100CreateDateFr をセットします。
     * @param cht100CreateDateFr cht100CreateDateFr
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#cht100CreateDateFr__
     */
    public void setCht100CreateDateFr(String cht100CreateDateFr) {
        cht100CreateDateFr__ = cht100CreateDateFr;
    }
    /**
     * <p>cht100CreateDateTo を取得します。
     * @return cht100CreateDateTo
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#cht100CreateDateTo__
     */
    public String getCht100CreateDateTo() {
        return cht100CreateDateTo__;
    }
    /**
     * <p>cht100CreateDateTo をセットします。
     * @param cht100CreateDateTo cht100CreateDateTo
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#cht100CreateDateTo__
     */
    public void setCht100CreateDateTo(String cht100CreateDateTo) {
        cht100CreateDateTo__ = cht100CreateDateTo;
    }
    /**
     * <p>cht100UpdateYearFr を取得します。
     * @return cht100UpdateYearFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateYearFr__
     */
    public int getCht100UpdateYearFr() {
        return cht100UpdateYearFr__;
    }
    /**
     * <p>cht100UpdateYearFr をセットします。
     * @param cht100UpdateYearFr cht100UpdateYearFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateYearFr__
     */
    public void setCht100UpdateYearFr(int cht100UpdateYearFr) {
        cht100UpdateYearFr__ = cht100UpdateYearFr;
    }
    /**
     * <p>cht100UpdateMonthFr を取得します。
     * @return cht100UpdateMonthFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateMonthFr__
     */
    public int getCht100UpdateMonthFr() {
        return cht100UpdateMonthFr__;
    }
    /**
     * <p>cht100UpdateMonthFr をセットします。
     * @param cht100UpdateMonthFr cht100UpdateMonthFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateMonthFr__
     */
    public void setCht100UpdateMonthFr(int cht100UpdateMonthFr) {
        cht100UpdateMonthFr__ = cht100UpdateMonthFr;
    }
    /**
     * <p>cht100UpdateDayFr を取得します。
     * @return cht100UpdateDayFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateDayFr__
     */
    public int getCht100UpdateDayFr() {
        return cht100UpdateDayFr__;
    }
    /**
     * <p>cht100UpdateDayFr をセットします。
     * @param cht100UpdateDayFr cht100UpdateDayFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateDayFr__
     */
    public void setCht100UpdateDayFr(int cht100UpdateDayFr) {
        cht100UpdateDayFr__ = cht100UpdateDayFr;
    }
    /**
     * <p>cht100UpdateYearTo を取得します。
     * @return cht100UpdateYearTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateYearTo__
     */
    public int getCht100UpdateYearTo() {
        return cht100UpdateYearTo__;
    }
    /**
     * <p>cht100UpdateYearTo をセットします。
     * @param cht100UpdateYearTo cht100UpdateYearTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateYearTo__
     */
    public void setCht100UpdateYearTo(int cht100UpdateYearTo) {
        cht100UpdateYearTo__ = cht100UpdateYearTo;
    }
    /**
     * <p>cht100UpdateMonthTo を取得します。
     * @return cht100UpdateMonthTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateMonthTo__
     */
    public int getCht100UpdateMonthTo() {
        return cht100UpdateMonthTo__;
    }
    /**
     * <p>cht100UpdateMonthTo をセットします。
     * @param cht100UpdateMonthTo cht100UpdateMonthTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateMonthTo__
     */
    public void setCht100UpdateMonthTo(int cht100UpdateMonthTo) {
        cht100UpdateMonthTo__ = cht100UpdateMonthTo;
    }
    /**
     * <p>cht100UpdateDayTo を取得します。
     * @return cht100UpdateDayTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateDayTo__
     */
    public int getCht100UpdateDayTo() {
        return cht100UpdateDayTo__;
    }
    /**
     * <p>cht100UpdateDayTo をセットします。
     * @param cht100UpdateDayTo cht100UpdateDayTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UpdateDayTo__
     */
    public void setCht100UpdateDayTo(int cht100UpdateDayTo) {
        cht100UpdateDayTo__ = cht100UpdateDayTo;
    }
    /**
     * <p>cht100UpdateDateFr を取得します。
     * @return cht100UpdateDateFr
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#cht100UpdateDateFr__
     */
    public String getCht100UpdateDateFr() {
        return cht100UpdateDateFr__;
    }
    /**
     * <p>cht100UpdateDateFr をセットします。
     * @param cht100UpdateDateFr cht100UpdateDateFr
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#cht100UpdateDateFr__
     */
    public void setCht100UpdateDateFr(String cht100UpdateDateFr) {
        cht100UpdateDateFr__ = cht100UpdateDateFr;
    }
    /**
     * <p>cht100UpdateDateTo を取得します。
     * @return cht100UpdateDateTo
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#cht100UpdateDateTo__
     */
    public String getCht100UpdateDateTo() {
        return cht100UpdateDateTo__;
    }
    /**
     * <p>cht100UpdateDateTo をセットします。
     * @param cht100UpdateDateTo cht100UpdateDateTo
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#cht100UpdateDateTo__
     */
    public void setCht100UpdateDateTo(String cht100UpdateDateTo) {
        cht100UpdateDateTo__ = cht100UpdateDateTo;
    }
    /**
     * <p>cht100SortKey を取得します。
     * @return cht100SortKey
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100SortKey__
     */
    public int getCht100SortKey() {
        return cht100SortKey__;
    }
    /**
     * <p>cht100SortKey をセットします。
     * @param cht100SortKey cht100SortKey
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100SortKey__
     */
    public void setCht100SortKey(int cht100SortKey) {
        cht100SortKey__ = cht100SortKey;
    }
    /**
     * <p>cht100OrderKey を取得します。
     * @return cht100OrderKey
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100OrderKey__
     */
    public int getCht100OrderKey() {
        return cht100OrderKey__;
    }
    /**
     * <p>cht100OrderKey をセットします。
     * @param cht100OrderKey cht100OrderKey
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100OrderKey__
     */
    public void setCht100OrderKey(int cht100OrderKey) {
        cht100OrderKey__ = cht100OrderKey;
    }
    /**
     * <p>svCht100Keyword を取得します。
     * @return svCht100Keyword
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100Keyword__
     */
    public String getSvCht100Keyword() {
        return svCht100Keyword__;
    }
    /**
     * <p>svCht100Keyword をセットします。
     * @param svCht100Keyword svCht100Keyword
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100Keyword__
     */
    public void setSvCht100Keyword(String svCht100Keyword) {
        svCht100Keyword__ = svCht100Keyword;
    }
    /**
     * <p>svCht100AndOr を取得します。
     * @return svCht100AndOr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100AndOr__
     */
    public int getSvCht100AndOr() {
        return svCht100AndOr__;
    }
    /**
     * <p>svCht100AndOr をセットします。
     * @param svCht100AndOr svCht100AndOr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100AndOr__
     */
    public void setSvCht100AndOr(int svCht100AndOr) {
        svCht100AndOr__ = svCht100AndOr;
    }
    /**
     * <p>svCht100GroupId を取得します。
     * @return svCht100GroupId
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100GroupId__
     */
    public int getSvCht100GroupId() {
        return svCht100GroupId__;
    }
    /**
     * <p>svCht100GroupId をセットします。
     * @param svCht100GroupId svCht100GroupId
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100GroupId__
     */
    public void setSvCht100GroupId(int svCht100GroupId) {
        svCht100GroupId__ = svCht100GroupId;
    }
    /**
     * <p>svCht100GroupName を取得します。
     * @return svCht100GroupName
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100GroupName__
     */
    public int getSvCht100GroupName() {
        return svCht100GroupName__;
    }
    /**
     * <p>svCht100GroupName をセットします。
     * @param svCht100GroupName svCht100GroupName
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100GroupName__
     */
    public void setSvCht100GroupName(int svCht100GroupName) {
        svCht100GroupName__ = svCht100GroupName;
    }
    /**
     * <p>svCht100GroupInfo を取得します。
     * @return svCht100GroupInfo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100GroupInfo__
     */
    public int getSvCht100GroupInfo() {
        return svCht100GroupInfo__;
    }
    /**
     * <p>svCht100GroupInfo をセットします。
     * @param svCht100GroupInfo svCht100GroupInfo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100GroupInfo__
     */
    public void setSvCht100GroupInfo(int svCht100GroupInfo) {
        svCht100GroupInfo__ = svCht100GroupInfo;
    }
    /**
     * <p>svCht100Category を取得します。
     * @return svCht100Category
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100Category__
     */
    public int getSvCht100Category() {
        return svCht100Category__;
    }
    /**
     * <p>svCht100Category をセットします。
     * @param svCht100Category svCht100Category
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100Category__
     */
    public void setSvCht100Category(int svCht100Category) {
        svCht100Category__ = svCht100Category;
    }
    /**
     * <p>svCht100StatusKbn を取得します。
     * @return svCht100StatusKbn
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100StatusKbn__
     */
    public int getSvCht100StatusKbn() {
        return svCht100StatusKbn__;
    }
    /**
     * <p>svCht100StatusKbn をセットします。
     * @param svCht100StatusKbn svCht100StatusKbn
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100StatusKbn__
     */
    public void setSvCht100StatusKbn(int svCht100StatusKbn) {
        svCht100StatusKbn__ = svCht100StatusKbn;
    }
    /**
     * <p>svCht100SelectGroup を取得します。
     * @return svCht100SelectGroup
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100SelectGroup__
     */
    public int getSvCht100SelectGroup() {
        return svCht100SelectGroup__;
    }
    /**
     * <p>svCht100SelectGroup をセットします。
     * @param svCht100SelectGroup svCht100SelectGroup
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100SelectGroup__
     */
    public void setSvCht100SelectGroup(int svCht100SelectGroup) {
        svCht100SelectGroup__ = svCht100SelectGroup;
    }
    /**
     * <p>svCht100SelectUser を取得します。
     * @return svCht100SelectUser
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100SelectUser__
     */
    public int getSvCht100SelectUser() {
        return svCht100SelectUser__;
    }
    /**
     * <p>svCht100SelectUser をセットします。
     * @param svCht100SelectUser svCht100SelectUser
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100SelectUser__
     */
    public void setSvCht100SelectUser(int svCht100SelectUser) {
        svCht100SelectUser__ = svCht100SelectUser;
    }
    /**
     * <p>svCht100AdminMember を取得します。
     * @return svCht100AdminMember
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100AdminMember__
     */
    public int getSvCht100AdminMember() {
        return svCht100AdminMember__;
    }
    /**
     * <p>svCht100AdminMember をセットします。
     * @param svCht100AdminMember svCht100AdminMember
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100AdminMember__
     */
    public void setSvCht100AdminMember(int svCht100AdminMember) {
        svCht100AdminMember__ = svCht100AdminMember;
    }
    /**
     * <p>svCht100GeneralMember を取得します。
     * @return svCht100GeneralMember
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100GeneralMember__
     */
    public int getSvCht100GeneralMember() {
        return svCht100GeneralMember__;
    }
    /**
     * <p>svCht100GeneralMember をセットします。
     * @param svCht100GeneralMember svCht100GeneralMember
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100GeneralMember__
     */
    public void setSvCht100GeneralMember(int svCht100GeneralMember) {
        svCht100GeneralMember__ = svCht100GeneralMember;
    }
    /**
     * <p>svCht100CreateYearFr を取得します。
     * @return svCht100CreateYearFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateYearFr__
     */
    public int getSvCht100CreateYearFr() {
        return svCht100CreateYearFr__;
    }
    /**
     * <p>svCht100CreateYearFr をセットします。
     * @param svCht100CreateYearFr svCht100CreateYearFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateYearFr__
     */
    public void setSvCht100CreateYearFr(int svCht100CreateYearFr) {
        svCht100CreateYearFr__ = svCht100CreateYearFr;
    }
    /**
     * <p>svCht100CreateMonthFr を取得します。
     * @return svCht100CreateMonthFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateMonthFr__
     */
    public int getSvCht100CreateMonthFr() {
        return svCht100CreateMonthFr__;
    }
    /**
     * <p>svCht100CreateMonthFr をセットします。
     * @param svCht100CreateMonthFr svCht100CreateMonthFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateMonthFr__
     */
    public void setSvCht100CreateMonthFr(int svCht100CreateMonthFr) {
        svCht100CreateMonthFr__ = svCht100CreateMonthFr;
    }
    /**
     * <p>svCht100CreateDayFr を取得します。
     * @return svCht100CreateDayFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateDayFr__
     */
    public int getSvCht100CreateDayFr() {
        return svCht100CreateDayFr__;
    }
    /**
     * <p>svCht100CreateDayFr をセットします。
     * @param svCht100CreateDayFr svCht100CreateDayFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateDayFr__
     */
    public void setSvCht100CreateDayFr(int svCht100CreateDayFr) {
        svCht100CreateDayFr__ = svCht100CreateDayFr;
    }
    /**
     * <p>svCht100CreateYearTo を取得します。
     * @return svCht100CreateYearTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateYearTo__
     */
    public int getSvCht100CreateYearTo() {
        return svCht100CreateYearTo__;
    }
    /**
     * <p>svCht100CreateYearTo をセットします。
     * @param svCht100CreateYearTo svCht100CreateYearTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateYearTo__
     */
    public void setSvCht100CreateYearTo(int svCht100CreateYearTo) {
        svCht100CreateYearTo__ = svCht100CreateYearTo;
    }
    /**
     * <p>svCht100CreateMonthTo を取得します。
     * @return svCht100CreateMonthTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateMonthTo__
     */
    public int getSvCht100CreateMonthTo() {
        return svCht100CreateMonthTo__;
    }
    /**
     * <p>svCht100CreateMonthTo をセットします。
     * @param svCht100CreateMonthTo svCht100CreateMonthTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateMonthTo__
     */
    public void setSvCht100CreateMonthTo(int svCht100CreateMonthTo) {
        svCht100CreateMonthTo__ = svCht100CreateMonthTo;
    }
    /**
     * <p>svCht100CreateDayTo を取得します。
     * @return svCht100CreateDayTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateDayTo__
     */
    public int getSvCht100CreateDayTo() {
        return svCht100CreateDayTo__;
    }
    /**
     * <p>svCht100CreateDayTo をセットします。
     * @param svCht100CreateDayTo svCht100CreateDayTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100CreateDayTo__
     */
    public void setSvCht100CreateDayTo(int svCht100CreateDayTo) {
        svCht100CreateDayTo__ = svCht100CreateDayTo;
    }
    /**
     * <p>svCht100CreateDateFr を取得します。
     * @return svCht100CreateDateFr
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#svCht100CreateDateFr__
     */
    public String getSvCht100CreateDateFr() {
        return svCht100CreateDateFr__;
    }
    /**
     * <p>svCht100CreateDateFr をセットします。
     * @param svCht100CreateDateFr svCht100CreateDateFr
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#svCht100CreateDateFr__
     */
    public void setSvCht100CreateDateFr(String svCht100CreateDateFr) {
        svCht100CreateDateFr__ = svCht100CreateDateFr;
    }
    /**
     * <p>svCht100CreateDateTo を取得します。
     * @return svCht100CreateDateTo
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#svCht100CreateDateTo__
     */
    public String getSvCht100CreateDateTo() {
        return svCht100CreateDateTo__;
    }
    /**
     * <p>svCht100CreateDateTo をセットします。
     * @param svCht100CreateDateTo svCht100CreateDateTo
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#svCht100CreateDateTo__
     */
    public void setSvCht100CreateDateTo(String svCht100CreateDateTo) {
        svCht100CreateDateTo__ = svCht100CreateDateTo;
    }
    /**
     * <p>svCht100UpdateYearFr を取得します。
     * @return svCht100UpdateYearFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateYearFr__
     */
    public int getSvCht100UpdateYearFr() {
        return svCht100UpdateYearFr__;
    }
    /**
     * <p>svCht100UpdateYearFr をセットします。
     * @param svCht100UpdateYearFr svCht100UpdateYearFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateYearFr__
     */
    public void setSvCht100UpdateYearFr(int svCht100UpdateYearFr) {
        svCht100UpdateYearFr__ = svCht100UpdateYearFr;
    }
    /**
     * <p>svCht100UpdateMonthFr を取得します。
     * @return svCht100UpdateMonthFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateMonthFr__
     */
    public int getSvCht100UpdateMonthFr() {
        return svCht100UpdateMonthFr__;
    }
    /**
     * <p>svCht100UpdateMonthFr をセットします。
     * @param svCht100UpdateMonthFr svCht100UpdateMonthFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateMonthFr__
     */
    public void setSvCht100UpdateMonthFr(int svCht100UpdateMonthFr) {
        svCht100UpdateMonthFr__ = svCht100UpdateMonthFr;
    }
    /**
     * <p>svCht100UpdateDayFr を取得します。
     * @return svCht100UpdateDayFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateDayFr__
     */
    public int getSvCht100UpdateDayFr() {
        return svCht100UpdateDayFr__;
    }
    /**
     * <p>svCht100UpdateDayFr をセットします。
     * @param svCht100UpdateDayFr svCht100UpdateDayFr
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateDayFr__
     */
    public void setSvCht100UpdateDayFr(int svCht100UpdateDayFr) {
        svCht100UpdateDayFr__ = svCht100UpdateDayFr;
    }
    /**
     * <p>svCht100UpdateYearTo を取得します。
     * @return svCht100UpdateYearTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateYearTo__
     */
    public int getSvCht100UpdateYearTo() {
        return svCht100UpdateYearTo__;
    }
    /**
     * <p>svCht100UpdateYearTo をセットします。
     * @param svCht100UpdateYearTo svCht100UpdateYearTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateYearTo__
     */
    public void setSvCht100UpdateYearTo(int svCht100UpdateYearTo) {
        svCht100UpdateYearTo__ = svCht100UpdateYearTo;
    }
    /**
     * <p>svCht100UpdateMonthTo を取得します。
     * @return svCht100UpdateMonthTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateMonthTo__
     */
    public int getSvCht100UpdateMonthTo() {
        return svCht100UpdateMonthTo__;
    }
    /**
     * <p>svCht100UpdateMonthTo をセットします。
     * @param svCht100UpdateMonthTo svCht100UpdateMonthTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateMonthTo__
     */
    public void setSvCht100UpdateMonthTo(int svCht100UpdateMonthTo) {
        svCht100UpdateMonthTo__ = svCht100UpdateMonthTo;
    }
    /**
     * <p>svCht100UpdateDayTo を取得します。
     * @return svCht100UpdateDayTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateDayTo__
     */
    public int getSvCht100UpdateDayTo() {
        return svCht100UpdateDayTo__;
    }
    /**
     * <p>svCht100UpdateDayTo をセットします。
     * @param svCht100UpdateDayTo svCht100UpdateDayTo
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100UpdateDayTo__
     */
    public void setSvCht100UpdateDayTo(int svCht100UpdateDayTo) {
        svCht100UpdateDayTo__ = svCht100UpdateDayTo;
    }
    /**
     * <p>svCht100UpdateDateFr を取得します。
     * @return svCht100UpdateDateFr
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#svCht100UpdateDateFr__
     */
    public String getSvCht100UpdateDateFr() {
        return svCht100UpdateDateFr__;
    }
    /**
     * <p>svCht100UpdateDateFr をセットします。
     * @param svCht100UpdateDateFr svCht100UpdateDateFr
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#svCht100UpdateDateFr__
     */
    public void setSvCht100UpdateDateFr(String svCht100UpdateDateFr) {
        svCht100UpdateDateFr__ = svCht100UpdateDateFr;
    }
    /**
     * <p>svCht100UpdateDateTo を取得します。
     * @return svCht100UpdateDateTo
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#svCht100UpdateDateTo__
     */
    public String getSvCht100UpdateDateTo() {
        return svCht100UpdateDateTo__;
    }
    /**
     * <p>svCht100UpdateDateTo をセットします。
     * @param svCht100UpdateDateTo svCht100UpdateDateTo
     * @see jp.groupsession.v2.cht.cht100.Cht100Form#svCht100UpdateDateTo__
     */
    public void setSvCht100UpdateDateTo(String svCht100UpdateDateTo) {
        svCht100UpdateDateTo__ = svCht100UpdateDateTo;
    }
    /**
     * <p>cht100PageTop を取得します。
     * @return cht100PageTop
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100PageTop__
     */
    public int getCht100PageTop() {
        return cht100PageTop__;
    }
    /**
     * <p>cht100PageTop をセットします。
     * @param cht100PageTop cht100PageTop
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100PageTop__
     */
    public void setCht100PageTop(int cht100PageTop) {
        cht100PageTop__ = cht100PageTop;
    }
    /**
     * <p>cht100PageBottom を取得します。
     * @return cht100PageBottom
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100PageBottom__
     */
    public int getCht100PageBottom() {
        return cht100PageBottom__;
    }
    /**
     * <p>cht100PageBottom をセットします。
     * @param cht100PageBottom cht100PageBottom
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100PageBottom__
     */
    public void setCht100PageBottom(int cht100PageBottom) {
        cht100PageBottom__ = cht100PageBottom;
    }
    /**
     * <p>cht100CategoryList を取得します。
     * @return cht100CategoryList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CategoryList__
     */
    public List<LabelValueBean> getCht100CategoryList() {
        return cht100CategoryList__;
    }
    /**
     * <p>cht100CategoryList をセットします。
     * @param cht100CategoryList cht100CategoryList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100CategoryList__
     */
    public void setCht100CategoryList(List<LabelValueBean> cht100CategoryList) {
        cht100CategoryList__ = cht100CategoryList;
    }
    /**
     * <p>cht100GroupList を取得します。
     * @return cht100GroupList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GroupList__
     */
    public List<LabelValueBean> getCht100GroupList() {
        return cht100GroupList__;
    }
    /**
     * <p>cht100GroupList をセットします。
     * @param cht100GroupList cht100GroupList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GroupList__
     */
    public void setCht100GroupList(List<LabelValueBean> cht100GroupList) {
        cht100GroupList__ = cht100GroupList;
    }
    /**
     * <p>cht100UserList を取得します。
     * @return cht100UserList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UserList__
     */
    public List<UsrLabelValueBean> getCht100UserList() {
        return cht100UserList__;
    }
    /**
     * <p>cht100UserList をセットします。
     * @param cht100UserList cht100UserList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100UserList__
     */
    public void setCht100UserList(List<UsrLabelValueBean> cht100UserList) {
        cht100UserList__ = cht100UserList;
    }
    /**
     * <p>cht100GrpDataList を取得します。
     * @return cht100GrpDataList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GrpDataList__
     */
    public List<ChatInformationModel> getCht100GrpDataList() {
        return cht100GrpDataList__;
    }
    /**
     * <p>cht100GrpDataList をセットします。
     * @param cht100GrpDataList cht100GrpDataList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100GrpDataList__
     */
    public void setCht100GrpDataList(List<ChatInformationModel> cht100GrpDataList) {
        cht100GrpDataList__ = cht100GrpDataList;
    }
    /**
     * <p>pageList を取得します。
     * @return pageList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#pageList__
     */
    public List<LabelValueBean> getPageList() {
        return pageList__;
    }
    /**
     * <p>pageList をセットします。
     * @param pageList pageList
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#pageList__
     */
    public void setPageList(List<LabelValueBean> pageList) {
        pageList__ = pageList;
    }
    /**
     * <p>svCht100SortKey を取得します。
     * @return svCht100SortKey
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100SortKey__
     */
    public int getSvCht100SortKey() {
        return svCht100SortKey__;
    }
    /**
     * <p>svCht100SortKey をセットします。
     * @param svCht100SortKey svCht100SortKey
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100SortKey__
     */
    public void setSvCht100SortKey(int svCht100SortKey) {
        svCht100SortKey__ = svCht100SortKey;
    }
    /**
     * <p>svCht100OrderKey を取得します。
     * @return svCht100OrderKey
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100OrderKey__
     */
    public int getSvCht100OrderKey() {
        return svCht100OrderKey__;
    }
    /**
     * <p>svCht100OrderKey をセットします。
     * @param svCht100OrderKey svCht100OrderKey
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#svCht100OrderKey__
     */
    public void setSvCht100OrderKey(int svCht100OrderKey) {
        svCht100OrderKey__ = svCht100OrderKey;
    }
    /**
     * <p>cgiSid を取得します。
     * @return cgiSid
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cgiSid__
     */
    public int getCgiSid() {
        return cgiSid__;
    }
    /**
     * <p>cgiSid をセットします。
     * @param cgiSid cgiSid
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cgiSid__
     */
    public void setCgiSid(int cgiSid) {
        cgiSid__ = cgiSid;
    }
    /**
     * <p>cht100ProcMode を取得します。
     * @return cht100ProcMode
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100ProcMode__
     */
    public int getCht100ProcMode() {
        return cht100ProcMode__;
    }
    /**
     * <p>cht100ProcMode をセットします。
     * @param cht100ProcMode cht100ProcMode
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100ProcMode__
     */
    public void setCht100ProcMode(int cht100ProcMode) {
        cht100ProcMode__ = cht100ProcMode;
    }
    /**
     * <p>cht100OldYear を取得します。
     * @return cht100OldYear
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100OldYear__
     */
    public int getCht100OldYear() {
        return cht100OldYear__;
    }
    /**
     * <p>cht100OldYear をセットします。
     * @param cht100OldYear cht100OldYear
     * @see jp.groupsession.v2.cht.cht100.Cht100ParamModel#cht100OldYear__
     */
    public void setCht100OldYear(int cht100OldYear) {
        cht100OldYear__ = cht100OldYear;
    }
}
