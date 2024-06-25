package jp.groupsession.v2.cir;

/**
 * <br>[機  能] 回覧板管理定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstCircular {

    /** プラグインID */
    public static final String PLUGIN_ID_CIRCULAR = "circular";

    /** 採番区分 SID 回覧板 */
    public static final String SBNSID_CIRCULAR = "circular";
    /** 採番区分 SID SUB 回覧板SID */
    public static final String SBNSID_SUB_CIRCULAR = "cir";
    /** 採番SIDサブ(アカウントSID) */
    public static final String SBNSID_SUB_ACCOUNT = "account";
    /** 採番IDサブ ラベルSID */
    public static final String SBNSID_SUB_LABEL = "label";

    /** テンポラリディレクトリ名（新規回覧作成時添付） */
    public static final String TEMP_DIR_NEW = "cirTempNew";
    /** テンポラリディレクトリ名（確認時添付） */
    public static final String TEMP_DIR_KN = "cirTempKn";
    /** テンポラリディレクトリ名（一括ダウンロード用作業ディレクトリ） */
    public static final String TEMP_DIR_ZIP = "cirAllExp";
    /** テンポラリディレクトリ名（PDF受信ダウンロード用作業ディレクトリ）*/
    public static final String TEMP_DIR_PDF = "cirrecvpdf";
    /** テンポラリディレクトリ名（PDF送信ダウンロード用作業ディレクトリ）*/
    public static final String TEMP_DIR_PDF_SEND = "cirsendpdf";
    /** 受信か送信かを判定するキーのSIDフォーマット */
    public static final String CIR_KEY_FORMAT = "000";

    /** タイトルMAX文字数 */
    public static final int MAX_LENGTH_TITLE = 70;
    /** メモMAX文字数 */
    public static final int MAX_LENGTH_MEMO = 1000;
    /** 検索キーワードMAX文字数 */
    public static final int MAX_LENGTH_KEYWORD = 100;

    /** 管理者設定 ショートメール通知設定 各ユーザが設定 */
    public static final int CAF_SML_NTF_USER = 0;
    /** 管理者設定 ショートメール通知設定  管理者が設定 */
    public static final int CAF_SML_NTF_ADMIN = 1;
    /** 管理者設定 ショートメール通知設定区分 通知する */
    public static final int CAF_SML_NTF_KBN_YES = 0;
    /** 管理者設定 ショートメール通知設定区分 通知しない */
    public static final int CAF_SML_NTF_KBN_NO = 1;

    /** 状態区分 (登録) */
    public static final int DSPKBN_DSP_OK = 0;
    /** 状態区分 (ゴミ箱) */
    public static final int DSPKBN_DSP_NG = 1;
    /** 状態区分 (削除) */
    public static final int DSPKBN_DSP_DEL = 9;

    /** 確認区分 (未確認) */
    public static final int CONF_UNOPEN = 0;
    /** 確認区分 (確認済み) */
    public static final int CONF_OPEN = 1;

    /** 受信モード */
    public static final String MODE_JUSIN = "0";
    /** 送信済みモード */
    public static final String MODE_SOUSIN = "1";
    /** 受信(メイン)モード */
    public static final String MODE_JUSIN_MAIN = "2";
    /** ゴミ箱モード */
    public static final String MODE_GOMI = "3";
    /** ラベルモード */
    public static final String MODE_LABEL = "4";

    /** ソートキー(タイトル) */
    public static final int SORT_TITLE = 0;
    /** ソートキー(登録日時) */
    public static final int SORT_DATE = 1;
    /** ソートキー(グループSID) */
    public static final int SORT_GROUP = 2;
    /** ソートキー(登録者ID) */
    public static final int SORT_USER = 3;

    /** 回覧先ソートキー(社員/職員番号) */
    public static final int SAKI_SORT_SNO = 0;
    /** 回覧先ソートキー(氏名) */
    public static final int SAKI_SORT_NAME = 1;
    /** 回覧先ソートキー(役職) */
    public static final int SAKI_SORT_POST = 2;
    /** 回覧先ソートキー(メモ) */
    public static final int SAKI_SORT_MEMO = 3;
    /** 回覧先ソートキー(確認(状態区分)) */
    public static final int SAKI_SORT_KAKU = 4;
    /** 回覧先ソートキー(最終更新日時) */
    public static final int SAKI_SORT_SAISYU = 5;

    /** 回覧板閲覧 前へボタンモード */
    public static final int VIEW_PREV = 0;
    /** 回覧板閲覧 次へボタンモード */
    public static final int VIEW_NEXT = 1;

    /** 回覧板通知(通知する) */
    public static final int SMAIL_TSUUCHI = 0;
    /** 回覧板通知(通知しない) */
    public static final int SMAIL_NOT_TSUUCHI = 1;
    /** URL表示(確認画面) */
    public static final int SMAIL_DSP_KAKUNIN = 0;
    /** URL表示（状況確認画面) */
    public static final int SMAIL_DSP_JYOUKYOU = 1;

    /** 自動リロード時間 10分 */
    public static final int AUTO_RELOAD_10MIN = 600000;

    /** キーワード検索区分 (and) */
    public static final int KEY_WORD_KBN_AND = 0;
    /** キーワード検索区分 (or) */
    public static final int KEY_WORD_KBN_OR = 1;

    /** 検索対象 件名 */
    public static final int SEARCH_TARGET_TITLE = 1;
    /** 検索対象 内容 */
    public static final int SEARCH_TARGET_BODY = 2;

    /** 回覧板削除設定 0:削除しない */
    public static final int CIR_AUTO_DEL_NO = 0;
    /** 回覧板削除設定 1:一定期間が経過した回覧板を削除する */
    public static final int CIR_AUTO_DEL_LIMIT = 1;
    /** 回覧板自動削除設定 2:ログアウト時に全て削除する */
    public static final int CIR_AUTO_DEL_LOGOUT = 2;

    /** 回覧板自動削除設定 設定ユーザ区分 0:管理者 */
    public static final int CIR_ADEL_USR_KBN_ADM = 0;
    /** 回覧板自動削除設定 設定ユーザ区分 1:通常ユーザ */
    public static final int CIR_ADEL_USR_KBN_USER = 1;

    /** 回覧板自動削除区分 0:管理者が設定する */
    public static final int CIR_DEL_KBN_ADM_SETTING = 0;
    /** 回覧板自動削除区分 0:各ユーザが設定する */
    public static final int CIR_DEL_KBN_USER_SETTING = 1;

    /** グループフィルタ 全て */
    public static final int GRPFILTER_ALL = -1;

    /** 日次バッチで一度に削除する回覧板件数 */
    public static final int CIR_BATCH_DELETE_COUNT = 100;

    /** 初期値 編集権限設定 設定種別 0:管理者強制 */
    public static final int CIR_INIEDIT_STYPE_ADM = 0;
    /** 初期値 編集権限設定 設定種別 1:ユーザ単位で設定 */
    public static final int CIR_INIEDIT_STYPE_USER = 1;

    /** 初期値 メモ欄修正 0:不可能 */
    public static final int CIR_INIT_MEMO_CHANGE_NO = 0;
    /** 初期値 メモ欄修正 1:可能 */
    public static final int CIR_INIT_MEMO_CHANGE_YES = 1;

    /** 初期値 メモ欄の修正期限区分 0:1週間 */
    public static final int CIR_INIT_MEMO_ONEWEEK = 0;
    /** 初期値 メモ欄の修正期限区分 1:当日 */
    public static final int CIR_INIT_MEMO_TODAY = 1;
    /** 初期値 メモ欄の修正期限区分 2:2週間 */
    public static final int CIR_INIT_MEMO_TWOWEEKS = 2;
    /** 初期値 メモ欄の修正期限区分 3:1ヶ月 */
    public static final int CIR_INIT_MEMO_ONEMONTH = 3;

    /** 初期値 回覧先確認状況 0:公開 */
    public static final int CIR_INIT_SAKI_PUBLIC = 0;
    /** 初期値 回覧先確認状況 1:非公開 */
    public static final int CIR_INIT_SAKI_PRIVATE = 1;

    /** 初期値 初期設定区分 0:初期設定 */
    public static final int CIR_INIT_KBN_NOSET = 0;
    /** 初期値 初期設定区分 1:通常設定 */
    public static final int CIR_INIT_KBN_SET = 1;

    /** 初期値 設定画面表示区分 0:表示 */
    public static final int CIR_INIT_SET_DIS = 0;
    /** 初期値 設定画面表示区分 1:非表示 */
    public static final int CIR_INIT_SET_NODIS = 1;

    /** 登録モード 新規作成 */
    public static final int CIR_ENTRYMODE_NEW = 0;
    /** 登録モード 複写して作成 */
    public static final int CIR_ENTRYMODE_COPY = 1;
    /** 登録モード 編集 */
    public static final int CIR_ENTRYMODE_EDIT = 2;


    /** アカウント 状態区分 通常 */
    public static final int CAC_JKBN_NORMAL = 0;
    /** アカウント 状態区分 削除 */
    public static final int CAC_JKBN_DELETE = 1;

    /** アカウント処理モード 個人(通常) */
    public static final int ACCOUNTMODE_NORMAL = 0;
    /** アカウント処理モード 個人(個人設定) */
    public static final int ACCOUNTMODE_PSNLSETTING = 1;
    /** アカウント処理モード 個人(共通) */
    public static final int ACCOUNTMODE_COMMON = 2;

    /** アカウント情報 アカウント種別 通常 */
    public static final int CAC_TYPE_NORMAL = 0;
    /** アカウント情報 アカウント種別 共通(グループ) */
    public static final int CAC_TYPE_GROUP = 1;
    /** アカウント情報 アカウント種別 共通(ユーザ) */
    public static final int CAC_TYPE_USER = 2;

    /** アカウント識別文字列 */
    public static final String CIR_ACCOUNT_STR = "cac";

    /** ソートキー アカウント名 */
    public static final int SKEY_ACCOUNTNAME = 0;

    /** ソートキー 使用者 */
    public static final int SKEY_USER = 2;
    /** ソートキー ディスク使用量 */
    public static final int SKEY_DISKSIZE = 3;
    /** ソートキー 受信日時 */
    public static final int SKEY_RECEIVEDATE = 4;

    /** 並び順 昇順 */
    public static final int ORDER_ASC = 0;
    /** 並び順 降順 */
    public static final int ORDER_DESC = 1;

    /** メッセージに表示するテキスト 検索キーワード */
    public static final String TEXT_SEARCH_KEYWORD = "cmn.keyword";
    /** メッセージに表示するテキスト ラベル名 */
    public static final String TEXT_LABEL = "cmn.label.name";
    /** メッセージに表示するテキスト フィルター名 */
    public static final String TEXT_FILTER = "wml.84";
    /** メッセージに表示するテキスト 条件１ */
    public static final String TEXT_CONDITION1 = "wml.wml140kn.05";
    /** メッセージに表示するテキスト 条件２ */
    public static final String TEXT_CONDITION2 = "wml.wml140kn.04";
    /** メッセージに表示するテキスト 条件３ */
    public static final String TEXT_CONDITION3 = "wml.wml140kn.03";
    /** メッセージに表示するテキスト 条件４ */
    public static final String TEXT_CONDITION4 = "wml.wml140kn.02";
    /** メッセージに表示するテキスト 条件５ */
    public static final String TEXT_CONDITION5 = "wml.wml140kn.01";
    /** メッセージに表示するテキスト ディスク容量 */
    public static final String TEXT_DISK = "wml.87";
    /** メッセージに表示するテキスト 備考 */
    public static final String TEXT_BIKO = "cmn.memo";
    /** メッセージに表示するテキスト 使用者 グループ */
    public static final String TEXT_GROUP = "wml.188";
    /** メッセージに表示するテキスト 使用者ユーザ */
    public static final String TEXT_USER = "wml.189";
    /** メッセージに表示するテキスト 日付 */
    public static final String TEXT_DATE = "cmn.date2";
    /** メッセージに表示するテキスト 件名 */
    public static final String TEXT_NAME = "cmn.subject";
    /** メッセージに表示するテキスト ラベル */
    public static final String TEXT_SEL_LABEL = "cmn.label";
    /** メッセージに表示するテキスト ラベル */
    public static final String TEXT_MAIL_AUTO_RSV = "wml.152";
    /** メッセージに表示するテキスト 送信文字コード */
    public static final String TEXT_SEND_WORDCODE = "wml.wml040kn.01";
    /** メッセージに表示するテキスト 送信メール形式 */
    public static final String TEXT_SEND_TYPE = "cmn.format.";
    /** メッセージに表示するテキスト 使用者ユーザ */
    public static final String TEXT_USE_USER = "cmn.employer.user";
    /** メッセージに表示するテキスト 使用者グループ */
    public static final String TEXT_USE_GROUP = "cmn.employer.group";
    /** メッセージに表示するテキスト APOP */
    public static final String TEXT_APOP = "APOP";

    /** 検索キーワード MAX文字数 */
    public static final int MAXLEN_SEARCH_KEYWORD = 100;
    /** 条件 MAX文字数 */
    public static final int MAXLEN_CONDITION_KEYWORD = 256;
    /** アカウント MAX文字数 */
    public static final int MAXLEN_ACCOUNT = 100;
    /** アカウント アドレス　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_ADDRESS = 256;
    /** アカウント ディスク容量　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_DISK = 6;
    /** アカウント 備考　MAX文字数 */
    public static final int MAXLEN_ACCOUNT_BIKO = 1000;
    /** 件名 MAX文字数 */
    public static final int MAXLEN_NAME = 100;
    /** ユーザIDMAX文字数 */
    public static final int MAXLEN_USERID = 20;
    /** ユーザIDMIN文字数 */
    public static final int MINLEN_USERID = 2;

    /** ラベル MAX文字数 */
    public static final int MAXLEN_LABEL_NAME = 100;

    /** アカウントインポート用サンプルCSVファイル名 */
    public static final String SAMPLE_CSV_FILE_NAME = "import_account.xls";

    /** 1ページ表示件数 10件 */
    public static final int LIMIT_DSP = 10;
    /** アカウント 1ページ表示件数 30件 */
    public static final int LIMIT_DSP_ACCOUNT = 30;
    /** 送受信ログ管理 1ページ表示件数 50件 */
    public static final int LIMIT_DSP_MAILLOG = 50;

    /** 処理区分 新規追加 */
    public static final int CMDMODE_ADD = 0;
    /** 処理区分 編集 */
    public static final int CMDMODE_EDIT = 1;

    /** アカウント登録・編集 選択タブ  基本 */
    public static final int SEL_TAB_NORMAL = 0;
    /** アカウント登録・編集 選択タブ 自動削除 */
    public static final int SEL_TAB_AUTODEL = 1;

    /** アカウント  デフォルトアカウント */
    public static final int ACNT_DEF = 0;
    /** アカウント  作成アカウント */
    public static final int ACNT_MAKE_ACNT = 1;

    /** 自動削除  管理者が設定 */
    public static final int AUTO_DEL_ADM = 0;
    /** 自動削除  各アカウントが設定 */
    public static final int AUTO_DEL_ACCOUNT = 1;

    /** アカウント作成区分 管理者のみ作成可能 */
    public static final int KANRI_USER_ONLY = 0;
    /** アカウント作成区分 制限なし */
    public static final int KANRI_USER_NO = 1;

    /** アカウント作成区分 作成可 */
    public static final int ACCOUNT_ADD_OK = 0;
    /** アカウント作成区分 作成不可 */
    public static final int ACCOUNT_ADD_NG = 1;

    /** アカウント削除区分 削除可 */
    public static final int ACCOUNT_DELETE_OK = 0;
    /** アカウント削除区分 削除不可 */
    public static final int ACCOUNT_DELETE_NG = 1;

    /** 処理区分 初期表示 */
    public static final int DSP_FIRST = 0;
    /** 処理区分 初期表示完了 */
    public static final int DSP_ALREADY = 1;

    /** 画面区分 個人 */
    public static final int DSP_KOJIN = 0;
    /** 画面区分 管理者 */
    public static final int DSP_KANRI = 1;

    /** メッセージに表示するテキスト アカウント名 */
    public static final String TEXT_ACCOUNT = "wml.96";

    /** アカウント設定区分 指定 */
    public static final int ACCOUNT_SEL = 0;
    /** アカウント設定区分 すべて */
    public static final int ACCOUNT_ALL = 1;

    /** 画面遷移 ショートメールから*/
    public static final String CMD_SMAIL = "cmdSmail";

    /** アカウント テーマ 未設定 */
    public static final int CAC_THEME_NOSET = 0;

    /** 回覧板 編集なし */
    public static final int CIR_NO_EDIT = 0;
    /** 回覧板 編集あり */
    public static final int CIR_EDIT = 1;

    /** アカウント使用者設定制限 制限なし */
    public static final int CIN_ACNT_USER_NO = 0;
    /** アカウント使用者設定制限 管理者のみ設定可能 */
    public static final int CIN_ACNT_USER_ONLY = 1;

    /** 集計データ ログ区分 送信 */
    public static final int LOG_COUNT_KBN_SCIR = 0;
    /** 集計データ ログ区分 受信 */
    public static final int LOG_COUNT_KBN_JCIR = 1;

    /** 統計グラフ　受信回覧板数 */
    public static final String CIR_LOG_GRAPH_JMAIL = "cir_graph_jcir";
    /** 統計グラフ　送信回覧板数 */
    public static final String CIR_LOG_GRAPH_SMAIL = "cir_graph_scir";

    /** 回覧板管理者設定 作成権限者制限区分 全てのユーザが使用可能 */
    public static final int CAF_AREST_KBN_ALL = 0;
    /** 回覧板管理者設定 作成権限者制限区分 許可するユーザを選択 */
    public static final int CAF_AREST_KBN_SELECT = 1;

    /** ログ出力種別判別フラグ なし */
    public static final int CIR_LOG_FLG_NONE = -1;
    /** ログ出力種別判別フラグ 添付ファイル */
    public static final int CIR_LOG_FLG_DOWNLOAD = 0;
    /** ログ出力種別判別フラグ PDFファイル */
    public static final int CIR_LOG_FLG_PDF = 1;
    /** ログ出力種別判別フラグ 管理者設定 */
    public static final int CIR_LOG_FLG_ADMIN = 2;
    /** ログ出力種別判別フラグ 個人設定 */
    public static final int CIR_LOG_FLG_PREF = 3;

    /** コンボボックス グループ一覧 */
    public static final int ANSWER_GROUP_GRPLIST = -9;

    /** ページ表示区分 管理者設定 */
    public static final int DSPKBN_ADMIN = 1;
    /** ページ表示区分 個人設定 */
    public static final int DSPKBN_PREF = 0;
    /** 通知メール種別 受信・完了 */
    public static final int NOTIFY_NTF = 0;
    /** 通知メール種別 メモ・確認時添付編集時 */
    public static final int NOTIFY_MEMO = 1;
    /** 通知メール種別 回覧板編集時 */
    public static final int NOTIFY_EDIT = 2;
    /** 回覧板添付ファイル変更判定用 一時ファイル */
    public static final String CIR_EDITCHK_FILENAME = "editFlg";



    /** ラベル追加種別 既存のラベルを追加 */
    public static final int ADDLABEL_NORMAL = 0;
    /** ラベル追加種別 新規登録したラベルを追加 */
    public static final int ADDLABEL_NEW = 1;

    /** 回覧板に対するラベル付与 ラベル追加 */
    public static final int CIR_LABEL_ADD = 0;
    /** 回覧板に対するラベル付与 ラベル削除 */
    public static final int CIR_LABEL_DEL = 1;

    /** 自動リロード時間 1分 */
    public static final String AUTORELOAD_1 = "60000";
    /** 自動リロード時間 3分 */
    public static final String AUTORELOAD_3 = "180000";
    /** 自動リロード時間 5分 */
    public static final String AUTORELOAD_5 = "300000";
    /** 自動リロード時間 10分 */
    public static final String AUTORELOAD_10 = "600000";
    /** 自動リロード時間 20分 */
    public static final String AUTORELOAD_20 = "1200000";
    /** 自動リロード時間 30分 */
    public static final String AUTORELOAD_30 = "1800000";
    /** 自動リロード時間 40分 */
    public static final String AUTORELOAD_40 = "2400000";
    /** 自動リロード時間 50分 */
    public static final String AUTORELOAD_50 = "3000000";
    /** 自動リロード時間 60分 */
    public static final String AUTORELOAD_60 = "3600000";
    /** 自動リロード時間 リロードしない */
    public static final String AUTORELOAD_0 = "0";

    /** 自動リロード時間 1分 */
    public static final String AUTORELOAD_TEXT_1 = "1分";
    /** 自動リロード時間 3分 */
    public static final String AUTORELOAD_TEXT_3 = "3分";
    /** 自動リロード時間 5分 */
    public static final String AUTORELOAD_TEXT_5 = "5分";
    /** 自動リロード時間 10分 */
    public static final String AUTORELOAD_TEXT_10 = "10分";
    /** 自動リロード時間 20分 */
    public static final String AUTORELOAD_TEXT_20 = "20分";
    /** 自動リロード時間 30分 */
    public static final String AUTORELOAD_TEXT_30 = "30分";
    /** 自動リロード時間 40分 */
    public static final String AUTORELOAD_TEXT_40 = "40分";
    /** 自動リロード時間 50分 */
    public static final String AUTORELOAD_TEXT_50 = "50分";
    /** 自動リロード時間 60分 */
    public static final String AUTORELOAD_TEXT_60 = "60分";
    /** 自動リロード時間 リロードしない */
    public static final String AUTORELOAD_TEXT_0 = "リロードしない";

    /** 編集権限 0:管理者が設定する */
    public static final int CIR_DEFAULT_EDIT_ADMIN = 0;
    /** 編集権限 1:各アカウントが設定する */
    public static final int CIR_DEFAULT_EDIT_ALL = 1;

    /** 内容MAX文字数 */
    public static final int MAX_LENGTH_VALUE = 30000;

    /** 回覧板設定ファイル 内容文字数変数名*/
    public static final String CIRCONF_MAX_LENGTH = "CIRCULARBODY_LIMIT";

}