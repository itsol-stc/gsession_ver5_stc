package jp.groupsession.v2.enq.enq970;

/**
 * <p>管理者設定 発信アンケート管理画面で使用する定数一覧
 * @author JTS
 */
public class Enq970Const {
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
    /** 公開 公開完了 */
    public static final int PUBLIC_END = 2;

    /** 匿名 匿名以外 */
    public static final int ANONY_NON = 1;
    /** 匿名 匿名 */
    public static final int ANONY_ON = 2;

    /** 発信者 入力 */
    public static final int SENDERINPUT_INPUT = 1;
    /** 日付 指定しない */
    public static final int DATE_NON = 0;
    /** 日付 指定する */
    public static final int DATE_USE = 1;

    /** 発信者 入力 最大文字数 */
    public static final int MAXLEN_SENDER_INPUT = 20;
}
