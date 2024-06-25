package jp.groupsession.v2.enq.enq010;

/**
 * <p>アンケート一覧画面で使用する定数一覧
 * @author JTS
 */
public class Enq010Const {
    /** フォルダー 受信 */
    public static final int FOLDER_RECEIVE = 0;
    /** フォルダー 送信 */
    public static final int FOLDER_SEND = 1;
    /** フォルダー 草稿 */
    public static final int FOLDER_DRAFT = 2;
    /** フォルダー テンプレート */
    public static final int FOLDER_TEMPLATE = 3;

    /** サブフォルダー 未設定 */
    public static final int SUBFOLDER_NONE = 0;
    /** サブフォルダー 未回答 */
    public static final int SUBFOLDER_UNANS = 1;
    /** サブフォルダー 回答済 */
    public static final int SUBFOLDER_REPLIED = 2;

    /** サブフォルダー 未公開 */
    public static final int SUBFOLDER_NOT_PUBLIC = 1;
    /** サブフォルダー 公開 */
    public static final int SUBFOLDER_PUBLIC = 2;
    /** サブフォルダー 回答完了 */
    public static final int SUBFOLDER_COMP_ANS = 3;
    /** サブフォルダー 公開完了 */
    public static final int SUBFOLDER_COMP_PUB = 4;

    /** 状態 未回答 */
    public static final int STATUS_NOTANS = 1;
    /** 状態 回答済 */
    public static final int STATUS_ANS = 2;
    /** 状態 未公開 */
    public static final int STATUS_NOTPUB = 1;
    /** 状態 公開中 */
    public static final int STATUS_PUB = 2;
    /** 状態 回答完了 */
    public static final int STATUS_ANSEXIT = 3;
    /** 状態 公開中 */
    public static final int STATUS_PUBEXIT = 4;

    /** 公開 未公開 */
    public static final int PUBLIC_NO = 0;
    /** 公開 公開 */
    public static final int PUBLIC_YES = 1;
    /** 公開 回答完了 */
    public static final int PUBLIC_ANSED = 2;
    /** 公開 公開完了 */
    public static final int PUBLIC_END = 3;

    /** 未回答アンケート 回答期限切れフラグ 回答期限内 */
    public static final int PUBLIC_ANSFLG_OK = 0;
    /** 未回答アンケート 回答期限切れフラグ 回答期限切れ */
    public static final int PUBLIC_ANSFLG_NG = 1;

    /** 結果確認 公開 */
    public static final int RESULT_DISCLOSURE = 0;
    /** 結果確認 非公開 */
    public static final int RESULT_NON_DISCLOSURE = 1;

    /** 匿名 匿名以外 */
    public static final int ANONY_NON = 1;
    /** 匿名 匿名 */
    public static final int ANONY_ON = 2;

    /** キーワード 全てを含む(AND) */
    public static final int KEYWORDKBN_AND = 0;
    /** いずれかを含む(OR) */
    public static final int KEYWORDKBN_OR = 1;

    /** 発信者 入力 */
    public static final int SENDERINPUT_INPUT = 1;
    /** 日付 指定しない */
    public static final int DATE_NON = 0;
    /** 日付 指定する */
    public static final int DATE_USE = 1;

    /** 発信者 入力 最大文字数 */
    public static final int MAXLEN_SENDER_INPUT = 20;

    /** 簡易検索 回答不能を含む */
    public static final int SEARCH_ANSFLGOK_NOTONLY = 0;
    /** 簡易検索 回答可能のみ */
    public static final int SEARCH_ANSFLGOK_ONLY = 1;


    /** ソートキー 状態 */
    public static final int SORTKEY_STATUS = 1;
    /** ソートキー 重要度 */
    public static final int SORTKEY_PRIORITY = 2;
    /** ソートキー 発信者 */
    public static final int SORTKEY_SENDER = 3;
    /** ソートキー タイトル */
    public static final int SORTKEY_TITLE = 4;
    /** ソートキー 回答期限 */
    public static final int SORTKEY_ANSLIMIT = 5;
    /** ソートキー 公開期限 */
    public static final int SORTKEY_OPEN = 0;
    /** ソートキー 作成日 */
    public static final int SORTKEY_MAKEDATE = 6;
    /** ソートキー 結果公開期限 */
    public static final int SORTKEY_ANS_OPEN = 7;

    /** 並び順 昇順 */
    public static final int ORDER_ASC = 0;
    /** 並び順 降順 */
    public static final int ORDER_DESC = 1;

    /** 日付 指定しない */
    public static final int SEARCH_DETAIL_ON = 1;
    /** 日付 指定する */
    public static final int SEARCH_DETAIL_OFF = 0;

}
