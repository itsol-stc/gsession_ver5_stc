package jp.groupsession.v2.anp;

/**
 * <br>[機  能] 安否確認定数一覧
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class GSConstAnpi {

    /** プラグインID */
    public static final String PLUGIN_ID = "anpi";

    /** 採番ID 安否確認 */
    public static final String SBNSID_ANPI = "anpi";
    /** 採番IDサブ 安否確認SID */
    public static final String SBNSID_SUB_ANPIHAISIN = "anpihaisin";
    /** 採番IDサブ テンプレートSID */
    public static final String SBNSID_SUB_MAILTEMPLATE = "mailtemplate";

    /** ユーザ返信URLの固定URL（PC） */
    public static final String URL_REPLY_PC = "anpi/anp020.do?";
    /** ユーザ返信URLの固定URL（携帯） */
    public static final String URL_REPLY_MB = "mobile/mh_anp021.do?";

    /** リスト表示画面での1ページ最大表示件数(規定値) */
    public static final int MAX_RECORD_COUNT = 30;

    /** マイグループSIDの先頭文字 */
    public static final String MY_GROUP_STRING = "M";
    /** 全ての送信者 の先頭文字 */
    public static final String ALL_GROUP_STRING = "ALL";
    /** 全てのグループ一覧 */
    public static final int GRP_SID_GRPLIST = -9;

    /** リモートモード */
    public static final String REMOTE_MODE = "1";
    /** リモートモード(携帯) */
    public static final String REMOTE_MODE_MOBILE = "2";

    /** 安否確認完了フラグ（完了） */
    public static final int ANPI_END_FLG = 1;

    /** 安否状況フラグ（未設定） */
    public static final int JOKYO_FLG_UNSET = 0;
    /** 安否状況フラグ（無事） */
    public static final int JOKYO_FLG_GOOD = 1;
    /** 安否状況フラグ（軽症） */
    public static final int JOKYO_FLG_KEISYO = 2;
    /** 安否状況フラグ（重症） */
    public static final int JOKYO_FLG_JUSYO = 3;

    /** 現在地フラグ（未設定） */
    public static final int PLACE_FLG_UNSET = 0;
    /** 現在地フラグ（自宅） */
    public static final int PLACE_FLG_HOUSE = 1;
    /** 現在地フラグ（会社） */
    public static final int PLACE_FLG_OFFICE = 2;
    /** 現在地フラグ（外出先） */
    public static final int PLACE_FLG_OUT = 3;

    /** 出社状況フラグ（未設定） */
    public static final int SYUSYA_FLG_UNSET = 0;
    /** 出社状況フラグ（不可） */
    public static final int SYUSYA_FLG_NO = 1;
    /** 出社状況フラグ（可能） */
    public static final int SYUSYA_FLG_OK = 2;
    /** 出社状況フラグ（出社済） */
    public static final int SYUSYA_FLG_OKD = 3;

    /** 全て選択フラグ（未選択） */
    public static final int ALL_GROUP_NOT_SELECT = 0;
    /** 全て選択フラグ（選択） */
    public static final int ALL_GROUP_SELECT = 1;

    /** 基本URL設定区分（自動取得） */
    public static final int URL_SETTING_AUTO = 0;
    /** 基本URL設定区分（手動） */
    public static final int URL_SETTING_MANUAL = 1;

    /** 送信サーバ SSLフラグ（SSLを使用しない） */
    public static final int SEND_SSL_NOUSE = 0;
    /** 送信サーバ SSLフラグ（SSLを使用する） */
    public static final int SEND_SSL_USE = 1;

    /** SMTP認証（認証しない） */
    public static final int SMTP_AUTH_NOT = 0;
    /** SMTP認証（認証する） */
    public static final int SMTP_AUTH_YES = 1;

    /** 表示フラグ（非表示） */
    public static final int DSP_FLG_NOT = 0;
    /** 表示フラグ（表示） */
    public static final int DSP_FLG_YES = 1;

    /** 配信フラグ（正常） */
    public static final int HAISIN_FLG_OK = 0;
    /** 配信フラグ（エラー） */
    public static final int HAISIN_FLG_ERROR = 1;
    /** 配信フラグ（途中） */
    public static final int HAISIN_FLG_UNSET = 2;

    /** フィルターフラグ（全て） */
    public static final int FILTER_FLG_ALL = 0;
    /** フィルターフラグ（登録済） */
    public static final int FILTER_FLG_REG = 1;
    /** フィルターフラグ（未登録） */
    public static final int FILTER_FLG_NONE = 2;

    /** メッセージ配信モード (新規) */
    public static final String MSG_HAISIN_MODE_NEW = "0";
    /** メッセージ配信モード (複写して新規作成) */
    public static final String MSG_HAISIN_MODE_COPY = "1";
    /** メッセージ配信モード (未返信者へ再送信) */
    public static final String MSG_HAISIN_MODE_MISAISOU = "2";
    /** メッセージ配信モード (全員へ再送信) */
    public static final String MSG_HAISIN_MODE_ZENSAISOU = "3";
    /** メッセージ配信モード (配信内容確認) */
    public static final String MSG_HAISIN_MODE_INFOCONF = "4";

    /** 安否確認完了フラグ（完了） */
    public static final int SENDMSG_SUCCESS = 0;
    /** メッセージ送信処理エラーフラグ（送信サーバへの接続エラー） */
    public static final int SENDMSG_ERROR_CONNECT = -1;
    /** メッセージ送信処理エラーフラグ（メール送信エラー） */
    public static final int SENDMSG_ERROR_SEND = -2;
    /** メッセージ送信処理エラーフラグ（エラー） */
    public static final int SENDMSG_ERROR = -9;

    /** 安否 送信先タイプ ユーザ*/
    public static final int SEND_TYPE_USER = 1;
    /** 安否 送信先タイプ グループ*/
    public static final int SEND_TYPE_GROUP = 2;

    /** 最大文字数・件名 */
    public static final int MAXLENGTH_SUBJECT = 20;
    /** 最大文字数・本文１ */
    public static final int MAXLENGTH_TEXT1 = 1000;
    /** 最大文字数・本文２ */
    public static final int MAXLENGTH_TEXT2 = 1000;
    /** 最大文字数・返信コメント */
    public static final int MAXLENGTH_REPLYCOMMENT = 1000;
    /** 最大文字数・安否確認基本URL */
    public static final int MAXLENGTH_BASE_URL = 200;
    /** 最大文字数・送信サーバー */
    public static final int MAXLENGTH_SEND_HOST = 100;
    /** 最大文字数・送信サーバー ポート番号 */
    public static final int MAXLENGTH_SEND_PORT = 5;
    /** 最大文字数・送信サーバ ユーザ */
    public static final int MAXLENGTH_SEND_USER = 100;
    /** 最大文字数・送信サーバ パスワード */
    public static final int MAXLENGTH_SEND_PASSWORD = 100;

    /** 緊急連絡先取り込み用CSVサンプルファイル名 */
    public static final String IMPORT_CSV_SAMPLE = "import_anpi.xls";
    /** 緊急連絡先取り込み用CSV保存フォルダ名（templete） */
    public static final String IMPORT_CSV_SAVEDIR = "templete";
    /** 緊急連絡先取り込み用CSV項目数 */
    public static final int IMPORT_CSV_ITEMCOUNT = 3;

    /** 訓練モード OFF */
    public static final int KNREN_MODE_OFF = 0;
    /** 訓練モード ON */
    public static final int KNREN_MODE_ON = 1;

    /** 安否確認配信データ メイン表示 全ユーザ  */
    public static final int APH_VIEW_MAIN_ALL = 0;
    /** 安否確認配信データ メイン表示 送信先ユーザのみ */
    public static final int APH_VIEW_MAIN_SENDTO = 1;

    /** 安否確認回答区分 未回答 */
    public static final int ANP_ANS_NO = 0;
    /** 安否確認回答区分 回答済 */
    public static final int ANP_ANS_YES = 1;

    /** 検索条件 配信状況 全て */
    public static final int SEARCH_SENDKBN_ALL = 0;
    /** 検索条件 配信状況 未配信 */
    public static final int SEARCH_SENDKBN_NO = 1;
    /** 検索条件 配信状況 配信済み */
    public static final int SEARCH_SENDKBN_OK = 2;

    /** 検索条件 回答状況 全て */
    public static final int SEARCH_ANSKBN_ALL = 0;
    /** 検索条件 回答状況 未回答 */
    public static final int SEARCH_ANSKBN_NO = 1;
    /** 検索条件 回答状況 回答済み */
    public static final int SEARCH_ANSKBN_OK = 2;

    /** 検索条件 安否状況 全て */
    public static final int SEARCH_ANPKBN_ALL = 0;
    /** 検索条件 安否状況 無事 */
    public static final int SEARCH_ANPKBN_GOOD = 1;
    /** 検索条件 安否状況 軽症 */
    public static final int SEARCH_ANPKBN_KEISYO = 2;
    /** 検索条件 安否状況 重症 */
    public static final int SEARCH_ANPKBN_JUSYO = 3;

    /** 検索条件 現在地 全て */
    public static final int SEARCH_PLACEKBN_ALL = 0;
    /** 検索条件 現在地 自宅 */
    public static final int SEARCH_PLACEKBN_HOUSE = 1;
    /** 検索条件 現在地 会社 */
    public static final int SEARCH_PLACEKBN_OFFICE = 2;
    /** 検索条件 現在地 外出先 */
    public static final int SEARCH_PLACEKBN_OUT = 3;

    /** 検索条件 出社状況 全て */
    public static final int SEARCH_SYUSYAKBN_ALL = 0;
    /** 検索条件 出社状況 不可 */
    public static final int SEARCH_SYUSYAKBN_NO = 1;
    /** 検索条件 出社状況 可能 */
    public static final int SEARCH_SYUSYAKBN_OK = 2;
    /** 検索条件 出社状況 出社済 */
    public static final int SEARCH_SYUSYAKBN_OKD = 3;

    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /** メイン画面表示 表示しない */
    public static final int MAIN_DSP_HIDE = 0;
    /** メイン画面表示 表示する */
    public static final int MAIN_DSP_SHOW = 1;

    /** 返信基本URL 自動設定 */
    public static final int RETURN_URL_AUTO = 0;
    /** 返信基本URL 手動設定 */
    public static final int RETURN_URL_MANUAL = 1;
    
    /** 送信サーバポート 初期値 */
    public static final int SERVER_DEFO_SEND = 25;
    
    /** 認証 未完了 */
    public static final int AUTH_YET = 0;
    /** 認証 完了 */
    public static final int AUTH_DONE = 1;
}