package jp.groupsession.v2.enq.enq210kn;

import java.util.List;

import jp.groupsession.v2.enq.enq010.Enq010Const;
import jp.groupsession.v2.enq.enq210.Enq210ParamModel;

/**
 * <br>[機  能] アンケート 設問登録確認画面のパラメータ情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq210knParamModel extends Enq210ParamModel {
    /** アンケート種類(表示用) */
    private String enq210knViewSyurui__ = null;
    /** 発信者名称 */
    private String enq210knSenderName__ = null;
    /** 発信者 削除フラグ */
    private boolean enq210knSenderDelFlg__ = false;
    /** 発信者 ログイン停止フラグ */
    private int enq210knSenderUkoFlg__ = 0;
    /** 説明(表示用) */
    private String enq210knViewDesc__ = null;
    /** 削除された発信者 */
    private List<String> enq210knDelAnswerList__ = null;

    //発信アンケート管理画面からの遷移
    /** 初期表示フラグ */
    private int enq970initFlg__ = 0;
    /** 詳細検索フラグ */
    private int enq970searchDetailFlg__ = 0;
    /** 選択したアンケートSID */
    private String[] enq970selectEnqSid__ = null;
    /** 遷移元ページ */
    private int enq970BackPage__ = 0;

    /** ソートキー */
    private int enq970sortKey__ = Enq010Const.SORTKEY_OPEN;
    /** 並び順 */
    private int enq970order__ = Enq010Const.ORDER_DESC;

    /** 種類 */
    private int enq970type__ = 0;
    /** キーワード(簡易検索) */
    private String enq970keywordSimple__ = null;
    /** キーワード */
    private String enq970keyword__ = null;
    /** キーワード 種別 */
    private int enq970keywordType__ = 0;
    /** 発信者 グループ */
    private int enq970sendGroup__ = -1;
    /** 発信者 ユーザ */
    private int enq970sendUser__ = -1;
    /** 発信者 入力 */
    private int enq970sendInput__ = 0;
    /** 発信者 テキスト */
    private String enq970sendInputText__ = null;
    /** 作成日 指定なし */
    private int enq970makeDateKbn__ = 0;
    /** 作成日 開始 年 */
    private int enq970makeDateFromYear__ = 0;
    /** 作成日 開始 月 */
    private int enq970makeDateFromMonth__ = 0;
    /** 作成日 開始 日 */
    private int enq970makeDateFromDay__ = 0;
    /** 作成日 終了 年 */
    private int enq970makeDateToYear__ = 0;
    /** 作成日 終了 月 */
    private int enq970makeDateToMonth__ = 0;
    /** 作成日 終了 日 */
    private int enq970makeDateToDay__ = 0;
    /** 公開期間 指定なし */
    private int enq970pubDateKbn__ = 0;
    /** 公開期間 開始 年 */
    private int enq970pubDateFromYear__ = 0;
    /** 公開期間 開始 月 */
    private int enq970pubDateFromMonth__ = 0;
    /** 公開期間 開始 日 */
    private int enq970pubDateFromDay__ = 0;
    /** 公開期間 終了 年 */
    private int enq970pubDateToYear__ = 0;
    /** 公開期間 終了 月 */
    private int enq970pubDateToMonth__ = 0;
    /** 公開期間 終了 日 */
    private int enq970pubDateToDay__ = 0;
    /** 回答期限 指定なし */
    private int enq970ansDateKbn__ = 0;
    /** 回答期限 開始 年 */
    private int enq970ansDateFromYear__ = 0;
    /** 回答期限 開始 月 */
    private int enq970ansDateFromMonth__ = 0;
    /** 回答期限 開始 日 */
    private int enq970ansDateFromDay__ = 0;
    /** 回答期限 終了 年 */
    private int enq970ansDateToYear__ = 0;
    /** 回答期限 終了 月 */
    private int enq970ansDateToMonth__ = 0;
    /** 回答期限 終了 日 */
    private int enq970ansDateToDay__ = 0;
    /** 重要度 */
    private int[] enq970priority__ = null;
    /** 状態 */
    private int[] enq970status__ = null;
    /** 匿名 匿名 */
    private int enq970anony__ = 0;
    /** 種類(検索条件保持) */
    private int enq970svType__ = 0;
    /** キーワード(簡易検索)(検索条件保持) */
    private String enq970svKeywordSimple__ = null;
    /** キーワード(検索条件保持) */
    private String enq970svKeyword__ = null;
    /** キーワード 種別(検索条件保持) */
    private int enq970svKeywordType__ = 0;
    /** 発信者 グループ(検索条件保持) */
    private int enq970svSendGroup__ = 0;
    /** 発信者 ユーザ(検索条件保持) */
    private int enq970svSendUser__ = 0;
    /** 発信者 入力(検索条件保持) */
    private int enq970svSendInput__ = 0;
    /** 発信者 テキスト(検索条件保持) */
    private String enq970svSendInputText__ = null;
    /** 作成日 指定なし(検索条件保持) */
    private int enq970svMakeDateKbn__ = 0;
    /** 作成日 開始 年(検索条件保持) */
    private int enq970svMakeDateFromYear__ = 0;
    /** 作成日 開始 月(検索条件保持) */
    private int enq970svMakeDateFromMonth__ = 0;
    /** 作成日 開始 日(検索条件保持) */
    private int enq970svMakeDateFromDay__ = 0;
    /** 作成日 終了 年(検索条件保持) */
    private int enq970svMakeDateToYear__ = 0;
    /** 作成日 終了 月(検索条件保持) */
    private int enq970svMakeDateToMonth__ = 0;
    /** 作成日 終了 日(検索条件保持) */
    private int enq970svMakeDateToDay__ = 0;
    /** 公開期間 指定なし(検索条件保持) */
    private int enq970svPubDateKbn__ = 0;
    /** 公開期間 開始 年(検索条件保持) */
    private int enq970svPubDateFromYear__ = 0;
    /** 公開期間 開始 月(検索条件保持) */
    private int enq970svPubDateFromMonth__ = 0;
    /** 公開期間 開始 日(検索条件保持) */
    private int enq970svPubDateFromDay__ = 0;
    /** 公開期間 終了 年(検索条件保持) */
    private int enq970svPubDateToYear__ = 0;
    /** 公開期間 終了 月(検索条件保持) */
    private int enq970svPubDateToMonth__ = 0;
    /** 公開期間 終了 日(検索条件保持) */
    private int enq970svPubDateToDay__ = 0;
    /** 回答期限 指定なし(検索条件保持) */
    private int enq970svAnsDateKbn__ = 0;
    /** 回答期限 開始 年(検索条件保持) */
    private int enq970svAnsDateFromYear__ = 0;
    /** 回答期限 開始 月(検索条件保持) */
    private int enq970svAnsDateFromMonth__ = 0;
    /** 回答期限 開始 日(検索条件保持) */
    private int enq970svAnsDateFromDay__ = 0;
    /** 回答期限 終了 年(検索条件保持) */
    private int enq970svAnsDateToYear__ = 0;
    /** 回答期限 終了 月(検索条件保持) */
    private int enq970svAnsDateToMonth__ = 0;
    /** 回答期限 終了 日(検索条件保持) */
    private int enq970svAnsDateToDay__ = 0;
    /** 重要度(検索条件保持) */
    private int[] enq970svPriority__ = null;
    /** 状態(検索条件保持) */
    private int[] enq970svStatus__ = null;
    /** 匿名 匿名(検索条件保持) */
    private int enq970svAnony__ = 0;
    /** ページ(上段) */
    private int enq970pageTop__ = 0;
    /** ページ(下段) */
    private int enq970pageBottom__ = 0;

    /**
     * <p>enq210knSenderName を取得します。
     * @return enq210knSenderName
     */
    public String getEnq210knSenderName() {
        return enq210knSenderName__;
    }
    /**
     * <p>enq210knSenderName をセットします。
     * @param enq210knSenderName enq210knSenderName
     */
    public void setEnq210knSenderName(String enq210knSenderName) {
        enq210knSenderName__ = enq210knSenderName;
    }
    /**
     * <p>enq210knSenderDelFlg を取得します。
     * @return enq210knSenderDelFlg
     */
    public boolean isEnq210knSenderDelFlg() {
        return enq210knSenderDelFlg__;
    }
    /**
     * <p>enq210knSenderDelFlg をセットします。
     * @param enq210knSenderDelFlg enq210knSenderDelFlg
     */
    public void setEnq210knSenderDelFlg(boolean enq210knSenderDelFlg) {
        enq210knSenderDelFlg__ = enq210knSenderDelFlg;
    }
    /**
     * <p>enq210knViewSyurui を取得します。
     * @return enq210knViewSyurui
     */
    public String getEnq210knViewSyurui() {
        return enq210knViewSyurui__;
    }
    /**
     * <p>enq210knViewSyurui をセットします。
     * @param enq210knViewSyurui enq210knViewSyurui
     */
    public void setEnq210knViewSyurui(String enq210knViewSyurui) {
        enq210knViewSyurui__ = enq210knViewSyurui;
    }
    /**
     * <p>enq210knViewDesc を取得します。
     * @return enq210knViewDesc
     */
    public String getEnq210knViewDesc() {
        return enq210knViewDesc__;
    }
    /**
     * <p>enq210knViewDesc をセットします。
     * @param enq210knViewDesc enq210knViewDesc
     */
    public void setEnq210knViewDesc(String enq210knViewDesc) {
        enq210knViewDesc__ = enq210knViewDesc;
    }
    /**
     * <p>enq210knDelAnswerList を取得します。
     * @return enq210knDelAnswerList
     */
    public List<String> getEnq210knDelAnswerList() {
        return enq210knDelAnswerList__;
    }
    /**
     * <p>enq210knDelAnswerList をセットします。
     * @param enq210knDelAnswerList enq210knDelAnswerList
     */
    public void setEnq210knDelAnswerList(List<String> enq210knDelAnswerList) {
        enq210knDelAnswerList__ = enq210knDelAnswerList;
    }
    /**
     * <p>enq970initFlg を取得します。
     * @return enq970initFlg
     */
    public int getEnq970initFlg() {
        return enq970initFlg__;
    }
    /**
     * <p>enq970initFlg をセットします。
     * @param enq970initFlg enq970initFlg
     */
    public void setEnq970initFlg(int enq970initFlg) {
        enq970initFlg__ = enq970initFlg;
    }
    /**
     * <p>enq970searchDetailFlg を取得します。
     * @return enq970searchDetailFlg
     */
    public int getEnq970searchDetailFlg() {
        return enq970searchDetailFlg__;
    }
    /**
     * <p>enq970searchDetailFlg をセットします。
     * @param enq970searchDetailFlg enq970searchDetailFlg
     */
    public void setEnq970searchDetailFlg(int enq970searchDetailFlg) {
        enq970searchDetailFlg__ = enq970searchDetailFlg;
    }
    /**
     * <p>enq970selectEnqSid を取得します。
     * @return enq970selectEnqSid
     */
    public String[] getEnq970selectEnqSid() {
        return enq970selectEnqSid__;
    }
    /**
     * <p>enq970selectEnqSid をセットします。
     * @param enq970selectEnqSid enq970selectEnqSid
     */
    public void setEnq970selectEnqSid(String[] enq970selectEnqSid) {
        enq970selectEnqSid__ = enq970selectEnqSid;
    }
    /**
     * <p>enq970BackPage を取得します。
     * @return enq970BackPage
     */
    public int getEnq970BackPage() {
        return enq970BackPage__;
    }
    /**
     * <p>enq970BackPage をセットします。
     * @param enq970BackPage enq970BackPage
     */
    public void setEnq970BackPage(int enq970BackPage) {
        enq970BackPage__ = enq970BackPage;
    }
    /**
     * <p>enq970sortKey を取得します。
     * @return enq970sortKey
     */
    public int getEnq970sortKey() {
        return enq970sortKey__;
    }
    /**
     * <p>enq970sortKey をセットします。
     * @param enq970sortKey enq970sortKey
     */
    public void setEnq970sortKey(int enq970sortKey) {
        enq970sortKey__ = enq970sortKey;
    }
    /**
     * <p>enq970order を取得します。
     * @return enq970order
     */
    public int getEnq970order() {
        return enq970order__;
    }
    /**
     * <p>enq970order をセットします。
     * @param enq970order enq970order
     */
    public void setEnq970order(int enq970order) {
        enq970order__ = enq970order;
    }
    /**
     * <p>enq970type を取得します。
     * @return enq970type
     */
    public int getEnq970type() {
        return enq970type__;
    }
    /**
     * <p>enq970type をセットします。
     * @param enq970type enq970type
     */
    public void setEnq970type(int enq970type) {
        enq970type__ = enq970type;
    }
    /**
     * <p>enq970keywordSimple を取得します。
     * @return enq970keywordSimple
     */
    public String getEnq970keywordSimple() {
        return enq970keywordSimple__;
    }
    /**
     * <p>enq970keywordSimple をセットします。
     * @param enq970keywordSimple enq970keywordSimple
     */
    public void setEnq970keywordSimple(String enq970keywordSimple) {
        enq970keywordSimple__ = enq970keywordSimple;
    }
    /**
     * <p>enq970keyword を取得します。
     * @return enq970keyword
     */
    public String getEnq970keyword() {
        return enq970keyword__;
    }
    /**
     * <p>enq970keyword をセットします。
     * @param enq970keyword enq970keyword
     */
    public void setEnq970keyword(String enq970keyword) {
        enq970keyword__ = enq970keyword;
    }
    /**
     * <p>enq970keywordType を取得します。
     * @return enq970keywordType
     */
    public int getEnq970keywordType() {
        return enq970keywordType__;
    }
    /**
     * <p>enq970keywordType をセットします。
     * @param enq970keywordType enq970keywordType
     */
    public void setEnq970keywordType(int enq970keywordType) {
        enq970keywordType__ = enq970keywordType;
    }
    /**
     * <p>enq970sendGroup を取得します。
     * @return enq970sendGroup
     */
    public int getEnq970sendGroup() {
        return enq970sendGroup__;
    }
    /**
     * <p>enq970sendGroup をセットします。
     * @param enq970sendGroup enq970sendGroup
     */
    public void setEnq970sendGroup(int enq970sendGroup) {
        enq970sendGroup__ = enq970sendGroup;
    }
    /**
     * <p>enq970sendUser を取得します。
     * @return enq970sendUser
     */
    public int getEnq970sendUser() {
        return enq970sendUser__;
    }
    /**
     * <p>enq970sendUser をセットします。
     * @param enq970sendUser enq970sendUser
     */
    public void setEnq970sendUser(int enq970sendUser) {
        enq970sendUser__ = enq970sendUser;
    }
    /**
     * <p>enq970sendInput を取得します。
     * @return enq970sendInput
     */
    public int getEnq970sendInput() {
        return enq970sendInput__;
    }
    /**
     * <p>enq970sendInput をセットします。
     * @param enq970sendInput enq970sendInput
     */
    public void setEnq970sendInput(int enq970sendInput) {
        enq970sendInput__ = enq970sendInput;
    }
    /**
     * <p>enq970sendInputText を取得します。
     * @return enq970sendInputText
     */
    public String getEnq970sendInputText() {
        return enq970sendInputText__;
    }
    /**
     * <p>enq970sendInputText をセットします。
     * @param enq970sendInputText enq970sendInputText
     */
    public void setEnq970sendInputText(String enq970sendInputText) {
        enq970sendInputText__ = enq970sendInputText;
    }
    /**
     * <p>enq970makeDateKbn を取得します。
     * @return enq970makeDateKbn
     */
    public int getEnq970makeDateKbn() {
        return enq970makeDateKbn__;
    }
    /**
     * <p>enq970makeDateKbn をセットします。
     * @param enq970makeDateKbn enq970makeDateKbn
     */
    public void setEnq970makeDateKbn(int enq970makeDateKbn) {
        enq970makeDateKbn__ = enq970makeDateKbn;
    }
    /**
     * <p>enq970makeDateFromYear を取得します。
     * @return enq970makeDateFromYear
     */
    public int getEnq970makeDateFromYear() {
        return enq970makeDateFromYear__;
    }
    /**
     * <p>enq970makeDateFromYear をセットします。
     * @param enq970makeDateFromYear enq970makeDateFromYear
     */
    public void setEnq970makeDateFromYear(int enq970makeDateFromYear) {
        enq970makeDateFromYear__ = enq970makeDateFromYear;
    }
    /**
     * <p>enq970makeDateFromMonth を取得します。
     * @return enq970makeDateFromMonth
     */
    public int getEnq970makeDateFromMonth() {
        return enq970makeDateFromMonth__;
    }
    /**
     * <p>enq970makeDateFromMonth をセットします。
     * @param enq970makeDateFromMonth enq970makeDateFromMonth
     */
    public void setEnq970makeDateFromMonth(int enq970makeDateFromMonth) {
        enq970makeDateFromMonth__ = enq970makeDateFromMonth;
    }
    /**
     * <p>enq970makeDateFromDay を取得します。
     * @return enq970makeDateFromDay
     */
    public int getEnq970makeDateFromDay() {
        return enq970makeDateFromDay__;
    }
    /**
     * <p>enq970makeDateFromDay をセットします。
     * @param enq970makeDateFromDay enq970makeDateFromDay
     */
    public void setEnq970makeDateFromDay(int enq970makeDateFromDay) {
        enq970makeDateFromDay__ = enq970makeDateFromDay;
    }
    /**
     * <p>enq970makeDateToYear を取得します。
     * @return enq970makeDateToYear
     */
    public int getEnq970makeDateToYear() {
        return enq970makeDateToYear__;
    }
    /**
     * <p>enq970makeDateToYear をセットします。
     * @param enq970makeDateToYear enq970makeDateToYear
     */
    public void setEnq970makeDateToYear(int enq970makeDateToYear) {
        enq970makeDateToYear__ = enq970makeDateToYear;
    }
    /**
     * <p>enq970makeDateToMonth を取得します。
     * @return enq970makeDateToMonth
     */
    public int getEnq970makeDateToMonth() {
        return enq970makeDateToMonth__;
    }
    /**
     * <p>enq970makeDateToMonth をセットします。
     * @param enq970makeDateToMonth enq970makeDateToMonth
     */
    public void setEnq970makeDateToMonth(int enq970makeDateToMonth) {
        enq970makeDateToMonth__ = enq970makeDateToMonth;
    }
    /**
     * <p>enq970makeDateToDay を取得します。
     * @return enq970makeDateToDay
     */
    public int getEnq970makeDateToDay() {
        return enq970makeDateToDay__;
    }
    /**
     * <p>enq970makeDateToDay をセットします。
     * @param enq970makeDateToDay enq970makeDateToDay
     */
    public void setEnq970makeDateToDay(int enq970makeDateToDay) {
        enq970makeDateToDay__ = enq970makeDateToDay;
    }
    /**
     * <p>enq970pubDateKbn を取得します。
     * @return enq970pubDateKbn
     */
    public int getEnq970pubDateKbn() {
        return enq970pubDateKbn__;
    }
    /**
     * <p>enq970pubDateKbn をセットします。
     * @param enq970pubDateKbn enq970pubDateKbn
     */
    public void setEnq970pubDateKbn(int enq970pubDateKbn) {
        enq970pubDateKbn__ = enq970pubDateKbn;
    }
    /**
     * <p>enq970pubDateFromYear を取得します。
     * @return enq970pubDateFromYear
     */
    public int getEnq970pubDateFromYear() {
        return enq970pubDateFromYear__;
    }
    /**
     * <p>enq970pubDateFromYear をセットします。
     * @param enq970pubDateFromYear enq970pubDateFromYear
     */
    public void setEnq970pubDateFromYear(int enq970pubDateFromYear) {
        enq970pubDateFromYear__ = enq970pubDateFromYear;
    }
    /**
     * <p>enq970pubDateFromMonth を取得します。
     * @return enq970pubDateFromMonth
     */
    public int getEnq970pubDateFromMonth() {
        return enq970pubDateFromMonth__;
    }
    /**
     * <p>enq970pubDateFromMonth をセットします。
     * @param enq970pubDateFromMonth enq970pubDateFromMonth
     */
    public void setEnq970pubDateFromMonth(int enq970pubDateFromMonth) {
        enq970pubDateFromMonth__ = enq970pubDateFromMonth;
    }
    /**
     * <p>enq970pubDateFromDay を取得します。
     * @return enq970pubDateFromDay
     */
    public int getEnq970pubDateFromDay() {
        return enq970pubDateFromDay__;
    }
    /**
     * <p>enq970pubDateFromDay をセットします。
     * @param enq970pubDateFromDay enq970pubDateFromDay
     */
    public void setEnq970pubDateFromDay(int enq970pubDateFromDay) {
        enq970pubDateFromDay__ = enq970pubDateFromDay;
    }
    /**
     * <p>enq970pubDateToYear を取得します。
     * @return enq970pubDateToYear
     */
    public int getEnq970pubDateToYear() {
        return enq970pubDateToYear__;
    }
    /**
     * <p>enq970pubDateToYear をセットします。
     * @param enq970pubDateToYear enq970pubDateToYear
     */
    public void setEnq970pubDateToYear(int enq970pubDateToYear) {
        enq970pubDateToYear__ = enq970pubDateToYear;
    }
    /**
     * <p>enq970pubDateToMonth を取得します。
     * @return enq970pubDateToMonth
     */
    public int getEnq970pubDateToMonth() {
        return enq970pubDateToMonth__;
    }
    /**
     * <p>enq970pubDateToMonth をセットします。
     * @param enq970pubDateToMonth enq970pubDateToMonth
     */
    public void setEnq970pubDateToMonth(int enq970pubDateToMonth) {
        enq970pubDateToMonth__ = enq970pubDateToMonth;
    }
    /**
     * <p>enq970pubDateToDay を取得します。
     * @return enq970pubDateToDay
     */
    public int getEnq970pubDateToDay() {
        return enq970pubDateToDay__;
    }
    /**
     * <p>enq970pubDateToDay をセットします。
     * @param enq970pubDateToDay enq970pubDateToDay
     */
    public void setEnq970pubDateToDay(int enq970pubDateToDay) {
        enq970pubDateToDay__ = enq970pubDateToDay;
    }
    /**
     * <p>enq970ansDateKbn を取得します。
     * @return enq970ansDateKbn
     */
    public int getEnq970ansDateKbn() {
        return enq970ansDateKbn__;
    }
    /**
     * <p>enq970ansDateKbn をセットします。
     * @param enq970ansDateKbn enq970ansDateKbn
     */
    public void setEnq970ansDateKbn(int enq970ansDateKbn) {
        enq970ansDateKbn__ = enq970ansDateKbn;
    }
    /**
     * <p>enq970ansDateFromYear を取得します。
     * @return enq970ansDateFromYear
     */
    public int getEnq970ansDateFromYear() {
        return enq970ansDateFromYear__;
    }
    /**
     * <p>enq970ansDateFromYear をセットします。
     * @param enq970ansDateFromYear enq970ansDateFromYear
     */
    public void setEnq970ansDateFromYear(int enq970ansDateFromYear) {
        enq970ansDateFromYear__ = enq970ansDateFromYear;
    }
    /**
     * <p>enq970ansDateFromMonth を取得します。
     * @return enq970ansDateFromMonth
     */
    public int getEnq970ansDateFromMonth() {
        return enq970ansDateFromMonth__;
    }
    /**
     * <p>enq970ansDateFromMonth をセットします。
     * @param enq970ansDateFromMonth enq970ansDateFromMonth
     */
    public void setEnq970ansDateFromMonth(int enq970ansDateFromMonth) {
        enq970ansDateFromMonth__ = enq970ansDateFromMonth;
    }
    /**
     * <p>enq970ansDateFromDay を取得します。
     * @return enq970ansDateFromDay
     */
    public int getEnq970ansDateFromDay() {
        return enq970ansDateFromDay__;
    }
    /**
     * <p>enq970ansDateFromDay をセットします。
     * @param enq970ansDateFromDay enq970ansDateFromDay
     */
    public void setEnq970ansDateFromDay(int enq970ansDateFromDay) {
        enq970ansDateFromDay__ = enq970ansDateFromDay;
    }
    /**
     * <p>enq970ansDateToYear を取得します。
     * @return enq970ansDateToYear
     */
    public int getEnq970ansDateToYear() {
        return enq970ansDateToYear__;
    }
    /**
     * <p>enq970ansDateToYear をセットします。
     * @param enq970ansDateToYear enq970ansDateToYear
     */
    public void setEnq970ansDateToYear(int enq970ansDateToYear) {
        enq970ansDateToYear__ = enq970ansDateToYear;
    }
    /**
     * <p>enq970ansDateToMonth を取得します。
     * @return enq970ansDateToMonth
     */
    public int getEnq970ansDateToMonth() {
        return enq970ansDateToMonth__;
    }
    /**
     * <p>enq970ansDateToMonth をセットします。
     * @param enq970ansDateToMonth enq970ansDateToMonth
     */
    public void setEnq970ansDateToMonth(int enq970ansDateToMonth) {
        enq970ansDateToMonth__ = enq970ansDateToMonth;
    }
    /**
     * <p>enq970ansDateToDay を取得します。
     * @return enq970ansDateToDay
     */
    public int getEnq970ansDateToDay() {
        return enq970ansDateToDay__;
    }
    /**
     * <p>enq970ansDateToDay をセットします。
     * @param enq970ansDateToDay enq970ansDateToDay
     */
    public void setEnq970ansDateToDay(int enq970ansDateToDay) {
        enq970ansDateToDay__ = enq970ansDateToDay;
    }
    /**
     * <p>enq970priority を取得します。
     * @return enq970priority
     */
    public int[] getEnq970priority() {
        return enq970priority__;
    }
    /**
     * <p>enq970priority をセットします。
     * @param enq970priority enq970priority
     */
    public void setEnq970priority(int[] enq970priority) {
        enq970priority__ = enq970priority;
    }
    /**
     * <p>enq970status を取得します。
     * @return enq970status
     */
    public int[] getEnq970status() {
        return enq970status__;
    }
    /**
     * <p>enq970status をセットします。
     * @param enq970status enq970status
     */
    public void setEnq970status(int[] enq970status) {
        enq970status__ = enq970status;
    }
    /**
     * <p>enq970anony を取得します。
     * @return enq970anony
     */
    public int getEnq970anony() {
        return enq970anony__;
    }
    /**
     * <p>enq970anony をセットします。
     * @param enq970anony enq970anony
     */
    public void setEnq970anony(int enq970anony) {
        enq970anony__ = enq970anony;
    }
    /**
     * <p>enq970svType を取得します。
     * @return enq970svType
     */
    public int getEnq970svType() {
        return enq970svType__;
    }
    /**
     * <p>enq970svType をセットします。
     * @param enq970svType enq970svType
     */
    public void setEnq970svType(int enq970svType) {
        enq970svType__ = enq970svType;
    }
    /**
     * <p>enq970svKeywordSimple を取得します。
     * @return enq970svKeywordSimple
     */
    public String getEnq970svKeywordSimple() {
        return enq970svKeywordSimple__;
    }
    /**
     * <p>enq970svKeywordSimple をセットします。
     * @param enq970svKeywordSimple enq970svKeywordSimple
     */
    public void setEnq970svKeywordSimple(String enq970svKeywordSimple) {
        enq970svKeywordSimple__ = enq970svKeywordSimple;
    }
    /**
     * <p>enq970svKeyword を取得します。
     * @return enq970svKeyword
     */
    public String getEnq970svKeyword() {
        return enq970svKeyword__;
    }
    /**
     * <p>enq970svKeyword をセットします。
     * @param enq970svKeyword enq970svKeyword
     */
    public void setEnq970svKeyword(String enq970svKeyword) {
        enq970svKeyword__ = enq970svKeyword;
    }
    /**
     * <p>enq970svKeywordType を取得します。
     * @return enq970svKeywordType
     */
    public int getEnq970svKeywordType() {
        return enq970svKeywordType__;
    }
    /**
     * <p>enq970svKeywordType をセットします。
     * @param enq970svKeywordType enq970svKeywordType
     */
    public void setEnq970svKeywordType(int enq970svKeywordType) {
        enq970svKeywordType__ = enq970svKeywordType;
    }
    /**
     * <p>enq970svSendGroup を取得します。
     * @return enq970svSendGroup
     */
    public int getEnq970svSendGroup() {
        return enq970svSendGroup__;
    }
    /**
     * <p>enq970svSendGroup をセットします。
     * @param enq970svSendGroup enq970svSendGroup
     */
    public void setEnq970svSendGroup(int enq970svSendGroup) {
        enq970svSendGroup__ = enq970svSendGroup;
    }
    /**
     * <p>enq970svSendUser を取得します。
     * @return enq970svSendUser
     */
    public int getEnq970svSendUser() {
        return enq970svSendUser__;
    }
    /**
     * <p>enq970svSendUser をセットします。
     * @param enq970svSendUser enq970svSendUser
     */
    public void setEnq970svSendUser(int enq970svSendUser) {
        enq970svSendUser__ = enq970svSendUser;
    }
    /**
     * <p>enq970svSendInput を取得します。
     * @return enq970svSendInput
     */
    public int getEnq970svSendInput() {
        return enq970svSendInput__;
    }
    /**
     * <p>enq970svSendInput をセットします。
     * @param enq970svSendInput enq970svSendInput
     */
    public void setEnq970svSendInput(int enq970svSendInput) {
        enq970svSendInput__ = enq970svSendInput;
    }
    /**
     * <p>enq970svSendInputText を取得します。
     * @return enq970svSendInputText
     */
    public String getEnq970svSendInputText() {
        return enq970svSendInputText__;
    }
    /**
     * <p>enq970svSendInputText をセットします。
     * @param enq970svSendInputText enq970svSendInputText
     */
    public void setEnq970svSendInputText(String enq970svSendInputText) {
        enq970svSendInputText__ = enq970svSendInputText;
    }
    /**
     * <p>enq970svMakeDateKbn を取得します。
     * @return enq970svMakeDateKbn
     */
    public int getEnq970svMakeDateKbn() {
        return enq970svMakeDateKbn__;
    }
    /**
     * <p>enq970svMakeDateKbn をセットします。
     * @param enq970svMakeDateKbn enq970svMakeDateKbn
     */
    public void setEnq970svMakeDateKbn(int enq970svMakeDateKbn) {
        enq970svMakeDateKbn__ = enq970svMakeDateKbn;
    }
    /**
     * <p>enq970svMakeDateFromYear を取得します。
     * @return enq970svMakeDateFromYear
     */
    public int getEnq970svMakeDateFromYear() {
        return enq970svMakeDateFromYear__;
    }
    /**
     * <p>enq970svMakeDateFromYear をセットします。
     * @param enq970svMakeDateFromYear enq970svMakeDateFromYear
     */
    public void setEnq970svMakeDateFromYear(int enq970svMakeDateFromYear) {
        enq970svMakeDateFromYear__ = enq970svMakeDateFromYear;
    }
    /**
     * <p>enq970svMakeDateFromMonth を取得します。
     * @return enq970svMakeDateFromMonth
     */
    public int getEnq970svMakeDateFromMonth() {
        return enq970svMakeDateFromMonth__;
    }
    /**
     * <p>enq970svMakeDateFromMonth をセットします。
     * @param enq970svMakeDateFromMonth enq970svMakeDateFromMonth
     */
    public void setEnq970svMakeDateFromMonth(int enq970svMakeDateFromMonth) {
        enq970svMakeDateFromMonth__ = enq970svMakeDateFromMonth;
    }
    /**
     * <p>enq970svMakeDateFromDay を取得します。
     * @return enq970svMakeDateFromDay
     */
    public int getEnq970svMakeDateFromDay() {
        return enq970svMakeDateFromDay__;
    }
    /**
     * <p>enq970svMakeDateFromDay をセットします。
     * @param enq970svMakeDateFromDay enq970svMakeDateFromDay
     */
    public void setEnq970svMakeDateFromDay(int enq970svMakeDateFromDay) {
        enq970svMakeDateFromDay__ = enq970svMakeDateFromDay;
    }
    /**
     * <p>enq970svMakeDateToYear を取得します。
     * @return enq970svMakeDateToYear
     */
    public int getEnq970svMakeDateToYear() {
        return enq970svMakeDateToYear__;
    }
    /**
     * <p>enq970svMakeDateToYear をセットします。
     * @param enq970svMakeDateToYear enq970svMakeDateToYear
     */
    public void setEnq970svMakeDateToYear(int enq970svMakeDateToYear) {
        enq970svMakeDateToYear__ = enq970svMakeDateToYear;
    }
    /**
     * <p>enq970svMakeDateToMonth を取得します。
     * @return enq970svMakeDateToMonth
     */
    public int getEnq970svMakeDateToMonth() {
        return enq970svMakeDateToMonth__;
    }
    /**
     * <p>enq970svMakeDateToMonth をセットします。
     * @param enq970svMakeDateToMonth enq970svMakeDateToMonth
     */
    public void setEnq970svMakeDateToMonth(int enq970svMakeDateToMonth) {
        enq970svMakeDateToMonth__ = enq970svMakeDateToMonth;
    }
    /**
     * <p>enq970svMakeDateToDay を取得します。
     * @return enq970svMakeDateToDay
     */
    public int getEnq970svMakeDateToDay() {
        return enq970svMakeDateToDay__;
    }
    /**
     * <p>enq970svMakeDateToDay をセットします。
     * @param enq970svMakeDateToDay enq970svMakeDateToDay
     */
    public void setEnq970svMakeDateToDay(int enq970svMakeDateToDay) {
        enq970svMakeDateToDay__ = enq970svMakeDateToDay;
    }
    /**
     * <p>enq970svPubDateKbn を取得します。
     * @return enq970svPubDateKbn
     */
    public int getEnq970svPubDateKbn() {
        return enq970svPubDateKbn__;
    }
    /**
     * <p>enq970svPubDateKbn をセットします。
     * @param enq970svPubDateKbn enq970svPubDateKbn
     */
    public void setEnq970svPubDateKbn(int enq970svPubDateKbn) {
        enq970svPubDateKbn__ = enq970svPubDateKbn;
    }
    /**
     * <p>enq970svPubDateFromYear を取得します。
     * @return enq970svPubDateFromYear
     */
    public int getEnq970svPubDateFromYear() {
        return enq970svPubDateFromYear__;
    }
    /**
     * <p>enq970svPubDateFromYear をセットします。
     * @param enq970svPubDateFromYear enq970svPubDateFromYear
     */
    public void setEnq970svPubDateFromYear(int enq970svPubDateFromYear) {
        enq970svPubDateFromYear__ = enq970svPubDateFromYear;
    }
    /**
     * <p>enq970svPubDateFromMonth を取得します。
     * @return enq970svPubDateFromMonth
     */
    public int getEnq970svPubDateFromMonth() {
        return enq970svPubDateFromMonth__;
    }
    /**
     * <p>enq970svPubDateFromMonth をセットします。
     * @param enq970svPubDateFromMonth enq970svPubDateFromMonth
     */
    public void setEnq970svPubDateFromMonth(int enq970svPubDateFromMonth) {
        enq970svPubDateFromMonth__ = enq970svPubDateFromMonth;
    }
    /**
     * <p>enq970svPubDateFromDay を取得します。
     * @return enq970svPubDateFromDay
     */
    public int getEnq970svPubDateFromDay() {
        return enq970svPubDateFromDay__;
    }
    /**
     * <p>enq970svPubDateFromDay をセットします。
     * @param enq970svPubDateFromDay enq970svPubDateFromDay
     */
    public void setEnq970svPubDateFromDay(int enq970svPubDateFromDay) {
        enq970svPubDateFromDay__ = enq970svPubDateFromDay;
    }
    /**
     * <p>enq970svPubDateToYear を取得します。
     * @return enq970svPubDateToYear
     */
    public int getEnq970svPubDateToYear() {
        return enq970svPubDateToYear__;
    }
    /**
     * <p>enq970svPubDateToYear をセットします。
     * @param enq970svPubDateToYear enq970svPubDateToYear
     */
    public void setEnq970svPubDateToYear(int enq970svPubDateToYear) {
        enq970svPubDateToYear__ = enq970svPubDateToYear;
    }
    /**
     * <p>enq970svPubDateToMonth を取得します。
     * @return enq970svPubDateToMonth
     */
    public int getEnq970svPubDateToMonth() {
        return enq970svPubDateToMonth__;
    }
    /**
     * <p>enq970svPubDateToMonth をセットします。
     * @param enq970svPubDateToMonth enq970svPubDateToMonth
     */
    public void setEnq970svPubDateToMonth(int enq970svPubDateToMonth) {
        enq970svPubDateToMonth__ = enq970svPubDateToMonth;
    }
    /**
     * <p>enq970svPubDateToDay を取得します。
     * @return enq970svPubDateToDay
     */
    public int getEnq970svPubDateToDay() {
        return enq970svPubDateToDay__;
    }
    /**
     * <p>enq970svPubDateToDay をセットします。
     * @param enq970svPubDateToDay enq970svPubDateToDay
     */
    public void setEnq970svPubDateToDay(int enq970svPubDateToDay) {
        enq970svPubDateToDay__ = enq970svPubDateToDay;
    }
    /**
     * <p>enq970svAnsDateKbn を取得します。
     * @return enq970svAnsDateKbn
     */
    public int getEnq970svAnsDateKbn() {
        return enq970svAnsDateKbn__;
    }
    /**
     * <p>enq970svAnsDateKbn をセットします。
     * @param enq970svAnsDateKbn enq970svAnsDateKbn
     */
    public void setEnq970svAnsDateKbn(int enq970svAnsDateKbn) {
        enq970svAnsDateKbn__ = enq970svAnsDateKbn;
    }
    /**
     * <p>enq970svAnsDateFromYear を取得します。
     * @return enq970svAnsDateFromYear
     */
    public int getEnq970svAnsDateFromYear() {
        return enq970svAnsDateFromYear__;
    }
    /**
     * <p>enq970svAnsDateFromYear をセットします。
     * @param enq970svAnsDateFromYear enq970svAnsDateFromYear
     */
    public void setEnq970svAnsDateFromYear(int enq970svAnsDateFromYear) {
        enq970svAnsDateFromYear__ = enq970svAnsDateFromYear;
    }
    /**
     * <p>enq970svAnsDateFromMonth を取得します。
     * @return enq970svAnsDateFromMonth
     */
    public int getEnq970svAnsDateFromMonth() {
        return enq970svAnsDateFromMonth__;
    }
    /**
     * <p>enq970svAnsDateFromMonth をセットします。
     * @param enq970svAnsDateFromMonth enq970svAnsDateFromMonth
     */
    public void setEnq970svAnsDateFromMonth(int enq970svAnsDateFromMonth) {
        enq970svAnsDateFromMonth__ = enq970svAnsDateFromMonth;
    }
    /**
     * <p>enq970svAnsDateFromDay を取得します。
     * @return enq970svAnsDateFromDay
     */
    public int getEnq970svAnsDateFromDay() {
        return enq970svAnsDateFromDay__;
    }
    /**
     * <p>enq970svAnsDateFromDay をセットします。
     * @param enq970svAnsDateFromDay enq970svAnsDateFromDay
     */
    public void setEnq970svAnsDateFromDay(int enq970svAnsDateFromDay) {
        enq970svAnsDateFromDay__ = enq970svAnsDateFromDay;
    }
    /**
     * <p>enq970svAnsDateToYear を取得します。
     * @return enq970svAnsDateToYear
     */
    public int getEnq970svAnsDateToYear() {
        return enq970svAnsDateToYear__;
    }
    /**
     * <p>enq970svAnsDateToYear をセットします。
     * @param enq970svAnsDateToYear enq970svAnsDateToYear
     */
    public void setEnq970svAnsDateToYear(int enq970svAnsDateToYear) {
        enq970svAnsDateToYear__ = enq970svAnsDateToYear;
    }
    /**
     * <p>enq970svAnsDateToMonth を取得します。
     * @return enq970svAnsDateToMonth
     */
    public int getEnq970svAnsDateToMonth() {
        return enq970svAnsDateToMonth__;
    }
    /**
     * <p>enq970svAnsDateToMonth をセットします。
     * @param enq970svAnsDateToMonth enq970svAnsDateToMonth
     */
    public void setEnq970svAnsDateToMonth(int enq970svAnsDateToMonth) {
        enq970svAnsDateToMonth__ = enq970svAnsDateToMonth;
    }
    /**
     * <p>enq970svAnsDateToDay を取得します。
     * @return enq970svAnsDateToDay
     */
    public int getEnq970svAnsDateToDay() {
        return enq970svAnsDateToDay__;
    }
    /**
     * <p>enq970svAnsDateToDay をセットします。
     * @param enq970svAnsDateToDay enq970svAnsDateToDay
     */
    public void setEnq970svAnsDateToDay(int enq970svAnsDateToDay) {
        enq970svAnsDateToDay__ = enq970svAnsDateToDay;
    }
    /**
     * <p>enq970svPriority を取得します。
     * @return enq970svPriority
     */
    public int[] getEnq970svPriority() {
        return enq970svPriority__;
    }
    /**
     * <p>enq970svPriority をセットします。
     * @param enq970svPriority enq970svPriority
     */
    public void setEnq970svPriority(int[] enq970svPriority) {
        enq970svPriority__ = enq970svPriority;
    }
    /**
     * <p>enq970svStatus を取得します。
     * @return enq970svStatus
     */
    public int[] getEnq970svStatus() {
        return enq970svStatus__;
    }
    /**
     * <p>enq970svStatus をセットします。
     * @param enq970svStatus enq970svStatus
     */
    public void setEnq970svStatus(int[] enq970svStatus) {
        enq970svStatus__ = enq970svStatus;
    }
    /**
     * <p>enq970svAnony を取得します。
     * @return enq970svAnony
     */
    public int getEnq970svAnony() {
        return enq970svAnony__;
    }
    /**
     * <p>enq970svAnony をセットします。
     * @param enq970svAnony enq970svAnony
     */
    public void setEnq970svAnony(int enq970svAnony) {
        enq970svAnony__ = enq970svAnony;
    }
    /**
     * <p>enq970pageTop を取得します。
     * @return enq970pageTop
     */
    public int getEnq970pageTop() {
        return enq970pageTop__;
    }
    /**
     * <p>enq970pageTop をセットします。
     * @param enq970pageTop enq970pageTop
     */
    public void setEnq970pageTop(int enq970pageTop) {
        enq970pageTop__ = enq970pageTop;
    }
    /**
     * <p>enq970pageBottom を取得します。
     * @return enq970pageBottom
     */
    public int getEnq970pageBottom() {
        return enq970pageBottom__;
    }
    /**
     * <p>enq970pageBottom をセットします。
     * @param enq970pageBottom enq970pageBottom
     */
    public void setEnq970pageBottom(int enq970pageBottom) {
        enq970pageBottom__ = enq970pageBottom;
    }
    /**
     * <p>enq210knSenderUkoFlg を取得します。
     * @return enq210knSenderUkoFlg
     */
    public int getEnq210knSenderUkoFlg() {
        return enq210knSenderUkoFlg__;
    }
    /**
     * <p>enq210knSenderUkoFlg をセットします。
     * @param enq210knSenderUkoFlg enq210knSenderUkoFlg
     */
    public void setEnq210knSenderUkoFlg(int enq210knSenderUkoFlg) {
        enq210knSenderUkoFlg__ = enq210knSenderUkoFlg;
    }
}
