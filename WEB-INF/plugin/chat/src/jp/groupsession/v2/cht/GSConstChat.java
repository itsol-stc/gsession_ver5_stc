package jp.groupsession.v2.cht;

/**
 *
 * <br>[機  能] チャットプラグインで利用される定数
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstChat {

    //プラグインID
    /** プラグインID*/
    public static final String PLUGIN_ID_CHAT = "chat";

    /** テンポラリディレクトリ名 添付用プラグインID*/
    public static final String PLUGIN_ID_CHAT_DRAG = "chatDrag";
    /** テンポラリディレクトリ名一括ダウンロード用作業ディレクトリ */
    public static final String TEMP_DIR_ZIP = "chtAllExp";

    /** 採番ID チャット */
    public static final String SBNSID_CHAT = "chat";
    /** 採番IDサブ グループ */
    public static final String SBNSID_SUB_CHAT_GROUP = "group";
    /** 採番IDサブ カテゴリ */
    public static final String SBNSID_SUB_CHAT_CATEGORY = "category";
    /** 採番IDサブ ペアSID*/
    public static final String SBSID_SUB_CHAT_PAIR = "pairSid";
    //一覧
    //投稿状態区分
    /** 投稿状態 通常*/
    public static final int TOUKOU_STATUS_NORMAL = 0;
    /** 投稿状態 編集*/
    public static final int TOUKOU_STATUS_EDIT = 1;
    /** 投稿状態 削除済み*/
    public static final int TOUKOU_STATUS_DELETE = 9;


    // 管理者設定 機能制限設定
    // ユーザ間チャットの利用
    /** ユーザ間チャットの利用 制限する */
    public static final int LIMIT_BETWEEN_USERS = 1;
    /** ユーザ間チャットの利用 制限しない */
    public static final int PERMIT_BETWEEN_USERS = 0;
    // グループの作成
    /** グループ管理使用制限 */
    public static final String GROUP_USE_LIMIT = "limitList";
    /** グループの作成 制限する */
    public static final int LIMIT_CREATE_GROUP = 1;
    /** グループの作成 制限しない */
    public static final int PERMIT_CREATE_GROUP = 0;
    /** グループの作成 制限対象  制限するユーザ/グループを指定 */
    public static final int TARGET_LIMIT = 0;
    /** グループの作成 制限対象  許可するユーザ/グループを指定 */
    public static final int TARGET_PERMIT = 1;
    /** 制限対象  ユーザ */
    public static final int LIMIT_TARGET_TYPE_USR = 1;
    /** 制限対象　グループ */
    public static final int LIMIT_TARGETTYPE_GRP = 2;
    // 既読の表示
    /** 既読の表示 表示する */
    public static final int KIDOKU_DISP = 1;
    /** 既読の表示  表示しない */
    public static final int KIDOKU_NOT_DISP = 0;
    // 自動削除
    /** 自動削除する */
    public static final int AUTO_DELETE_YES = 1;
    /** 自動削除しない */
    public static final int AUTO_DELETE_NO = 0;
    /** 自動削除：年初期設定 */
    public static final int AUTO_DELETE_DEFAULT_YEAR = 0;
    /** 自動削除：月初期設定 */
    public static final int AUTO_DELETE_DEFAULT_MONTH = 0;
    // 管理者設定削除

    /** 削除 年 開始年 */
    public static final int[] DEL_YEAR_DATE = {0, 1, 2, 3, 4, 5, 10};
    /** 削除 月 開始月 */
    public static final int[] DEL_MONTH_DATE = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

    // 管理者設定検索
    /** 1ページ最大表示件数 */
    public static final int CHAT_MAX_VEIWCOUNT = 10;
    /** オーダーキー 昇順 */
    public static final int CHAT_ORDER_ASC = 0;
    /** オーダーキー 降順 */
    public static final int CHAT_ORDER_DESC = 1;

    /** ソートキー グループID */
    public static final int CHAT_SORT_GRPID = 0;
    /** ソートキー グループ名 */
    public static final int CHAT_SORT_GRPNAME = 1;
    /** ソートキー 作成日時 */
    public static final int CHAT_SORT_ADATE = 2;
    /** ソートキー 最終投稿日時 */
    public static final int CHAT_SORT_EDATE = 3;


    /** キーワードMAX文字数 */
    public static final int MAX_LENGTH_KEYWORD = 100;
    /** 検索方法 全てを含む*/
    public static final int KEYWORDKBN_AND = 1;
    /** 検索方法 いずれかを含む*/
    public static final int KEYWORDKBN_OR = 2;

    /** 検索条件 グループID　含む*/
    public static final int SEARCH_GROUPID_IN = 1;
    /** 検索条件 グループID　含まない*/
    public static final int SEARCH_GROUPID_OUT = 0;
    /** 検索条件 グループ名　含む*/
    public static final int SEARCH_GROUPNAME_IN = 1;
    /** 検索条件 グループ名　含まない*/
    public static final int SEARCH_GROUPNAME_OUT = 0;
    /** 検索条件 備考 含む*/
    public static final int SEARCH_GROUPINFO_IN = 1;
    /** 検索条件 備考 含まない*/
    public static final int SEARCH_GROUPINFO_OUT = 0;
    /** 状態区分 全て*/
    public static final int SEARCH_STATUSKBN_ALL = 1;
    /** 状態区分 アーカイブ以外*/
    public static final int SEARCH_STATUSKBN_NOT_ARCHIVE = 2;
    /** 状態区分 アーカイブのみ*/
    public static final int SEARCH_STATUSKBN_ARCHIVE_ONLY = 3;
    /** メンバー検索：グループ管理者 含む*/
    public static final int SEARCH_GROUPADMIN_IN = 1;
    /** メンバー検索：グループ管理者 含まない*/
    public static final int SEARCH_GROUPADMIN_OUT = 0;
    /** メンバー検索：一般ユーザ 含む*/
    public static final int SEARCH_GENERALUSER_IN = 1;
    /** メンバー検索：一般ユーザ 含まない*/
    public static final int SEARCH_GENERALUSER_OUT = 0;

    /** カテゴリ 全て */
    public static final int CHAT_CHC_SID_ALL = -2;
    /** カテゴリ なし */
    public static final int CHAT_CHC_SID_NO = -1;
    /** カテゴリ 一般グループ */
    public static final int CHAT_CHC_SID_GENERAL = 0;

    // グループ登録・編集
    /** 処理モード なし */
    public static final int CHAT_MODE_NONE = -1;
    /** 処理モード 登録 */
    public static final int CHAT_MODE_ADD = 0;
    /** 処理モード 更新 */
    public static final int CHAT_MODE_EDIT = 1;
    /** 処理モード 削除 */
    public static final int CHAT_MODE_DELETE = 9;
    /** 追加区分 管理者 */
    public static final int ADD_KBN_ADMIN = 0;
    /** 追加区分 一般 */
    public static final int ADD_KBN_GENERAL = 1;

    /** モード メンバー グループ */
    public static final int MODE_MEMBER_GROUP = 0;
    /** モード メンバー ユーザ */
    public static final int MODE_MEMBER_USER = 1;
    /** モードメンバー ユーザの所属グループ */
    public static final int MODE_MEMBER_USERS_GROUP = 2;
    /** モード メンバー ユーザ+グループに所属するユーザ */
    public static final int MODE_MEMBER_RELATIVE_USER = 3;

    /** グループチャット 論理削除しない*/
    public static final int CHAT_GROUP_LOGIC_NOT_DELETE = 0;
    /** グループチャット 論理削除する*/
    public static final int CHAT_GROUP_LOGIC_DELETE = 9;


    /** グループの頭文字 */
    public static final String GROUP_HEADSTR = "G";
    /** グループコンボに設定するテキスト グループ一覧のVALUE */
    public static final String GROUP_COMBO_VALUE = "-9";

    /** グループコンボに設定するテキスト ユーザ指定のVALUE */
    public static final String GROUP_COMBO_VALUE_USER = "-8";
    /** グループIDMAX文字数 */
    public static final int MAX_LENGTH_GROUPID = 20;
    /** グループ名MAX文字数 */
    public static final int MAX_LENGTH_GROUPNAME = 100;
    /** 備考MAX文字数 */
    public static final int MAX_LENGTH_BIKO = 500;
    /** カテゴリMAX文字数 */
    public static final int MAX_LENGTH_CATEGORY = 20;
    /** 投稿メッセージMAX文字数 */
    public static final int MAX_LENGTH_MESSAGE = 3000;

    // 個人設定
    /** 受信時プッシュ通知する */
    public static final int CHAT_PUSH_FLG_YES = 1;
    /** 受信時プッシュ通知しない */
    public static final int CHAT_PUSH_FLG_NO = 0;
    /** 通知表示時間 MAX */
    public static final int CHAT_PUSH_MAX_TIME = 10;
    /** 通知表示時間 初期値 */
    public static final int CHAT_PUSH_DEFAULT_TIME = 5;

    /** デフォルト表示 */
    public static final int CHAT_DSP_DEFAULT = 0;
    /** デフォルト表示:ユーザ */
    public static final int CHAT_DSP_USER = 1;
    /** デフォルト表示:グループ */
    public static final int CHAT_DSP_CHATGROUP = 2;

    // 画面の表示
    /** 初期表示 */
    public static final int DSP_FIRST = 0;
    /** 初期表示完了 */
    public static final int DSP_ALREADY = 1;
    /** 再読み込み*/
    public static final int DSP_RELOAD = 2;

    // チャット相手区分
    /** ユーザ*/
    public static final int CHAT_KBN_USER = 1;
    /** グループ*/
    public static final int CHAT_KBN_GROUP = 2;

    // プッシュ通知使用フラグ
    /** 使用しない*/
    public static final int CHAT_PUSH_NOT_USE = 0;
    /** 使用する*/
    public static final int CHAT_PUSH_USE = 1;

    // 左メニュー タブコード
    /** 全て*/
    public static final int CHAT_TAB_ALL = 0;
    /** タイムライン*/
    public static final int CHAT_TAB_TIMELINE = 1;



    // Enterキー送信フラグ
    /** 送信しない*/
    public static final int CHAT_ENTER_NOT_SEND = 0;
    /** 送信する*/
    public static final int CHAT_ENTER_SEND = 1;

    // アーカイブ（完了）
    /** アーカイブ状態ではない*/
    public static final int CHAT_ARCHIVE_NOT_MODE = 0;
    /** アーカイブ状態*/
    public static final int CHAT_ARCHIVE_MODE = 1;

    /** 一度に取得するメッセージ数*/
    public static final int CHAT_GET_MESSAGE_NUMBER = 30;

    // チャットグループ管理者ユーザフラグ
    /** 管理者ではない（一般ユーザ）*/
    public static final int CHAT_GROUP_NOT_ADMIN = 0;
    /** 管理者*/
    public static final int CHAT_GROUP_ADMIN = 1;

    //特例アクセス区分
    /** ユーザ*/
    public static final int CHAT_SPACCESS_TYPE_USER = 1;
    /** グループ*/
    public static final int CHAT_SPACCESS_TYPE_GROUP = 2;
    /** 役職*/
    public static final int CHAT_SPACCESS_TYPE_POSITION = 3;

    //メッセージ送信不可状態区分
    /** ユーザ間チャット制限*/
    public static final int NOT_SEND_USER_SEIGEN = 1;
    /** アーカイブ状態*/
    public static final int NOT_SEND_ARCHIVE = 2;
    /** 削除済みユーザ*/
    public static final int NOT_SEND_DELETE_USER = 3;
    /** 特例アクセス*/
    public static final int NOT_SEND_SPACCESS = 4;
    /** アクセス不可*/
    public static final int NOT_SEND_ACCESS = 5;

    /** 未読件数*/
    public static final int MIDOKU_COUNT = 10;

    //お気に入り登録
    /** お気に入り登録する*/
    public static final int CHAT_FAVORITE = 1;
    /** お気に入り登録しない*/
    public static final int CHAT_NOT_FAVORITE = 0;

    //統計情報
    /** 統計グラフ　メッセージ数 */
    public static final String CHAT_LOG_GRAPH_MESSAGE = "chat_graph_message";
    /** 統計グラフ　送信件数 */
    public static final String CHAT_LOG_GRAPH_SEND = "chat_graph_send";

    /** 表示項目ユーザ　表示する */
    public static final  int CHAT_LOG_DSPUSER_YES = 1;
    /** 表示項目ユーザ　表示しない */
    public static final  int CHAT_LOG_DSPUSER_NO = 0;
    /** 表示項目グループ　表示する */
    public static final  int CHAT_LOG_DSPGROUP_YES = 1;
    /** 表示項目グループ　表示しない */
    public static final  int CHAT_LOG_DSPGROUP_NO = 0;
    /** 表示項目合計　表示する */
    public static final  int CHAT_LOG_DSPSUM_YES = 1;
    /** 表示項目合計　表示しない */
    public static final  int CHAT_LOG_DSPSUM_NO = 0;
    /** 表示件数 */
    public static final  int CHAT_LOG_DEFAULT_DSP_COUNT = 40;

    //グループ管理
    /** グループリスト　全て表示する */
    public static final  int CHAT_CONF_DSP_ALL = 1;
    /** グループリスト　管理権限を持つグループのみ表示 */
    public static final  int CHAT_CONF_DSP_ADMIN_GROUP = 0;

    //スクロール方向
    /** スクロール　上へ*/
    public static final int CHAT_TOP_SCROLL = 1;
    /** スクロール　下へ*/
    public static final int CHAT_BOTTOM_SCROLL = 2;

    //リアルタイム通信実行フラグ
    /** リアルタイム通信 実行しない */
    public static final int REAL_TIME_NO = 0;
    /** リアルタイム通信 実行する */
    public static final int REAL_TIME_YES = 1;

    //個人設定デフォルト送信相手フラグ
    /** 前回最後に開いていたものを表示*/
    public static final int PRI_DEF_FLG_LAST = 0;
    /** ユーザ*/
    public static final int PRI_DEF_FLG_USER = 1;
    /** グループ*/
    public static final int PRI_DEF_FLG_GROUP = 2;

    /** グループ編集権限フラグ 編集可*/
    public static final int CHAT_GROUP_EDIT = 1;
    /** グループ編集権限フラグ　編集不可*/
    public static final int CHAT_GROUP_NOT_EDIT = 0;

    /** ユーザ間チャットペア　完了*/
    public static final int CHAT_USER_COMP = 1;
    /** ユーザ間チャットペア　未完了*/
    public static final int CHAT_USER_NOT_COMP = 0;

    /** メイン画面遷移フラグ メイン画面以外から遷移 */
    public static final int FROM_NOT_MAIN = 0;
    /** メイン画面遷移フラグ メイン画面から遷移 */
    public static final int FROM_MAIN = 1;


    /** テンポラリディレクトリID チャット一覧 */
    public static final String DIRID_CHT010 = "cht010";
}
