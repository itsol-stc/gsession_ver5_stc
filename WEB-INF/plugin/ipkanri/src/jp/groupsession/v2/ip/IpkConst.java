package jp.groupsession.v2.ip;

/**
 * <br>[機  能] IP管理で使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkConst {

    /** プラグインID */
    public static final String PLUGIN_ID_IPKANRI = "ipkanri";

    /** ヘルプモード 登録 */
    public static final String IPK_HELP_TOUROKU = "0";
    /** ヘルプモード 編集 */
    public static final String IPK_HELP_HENSYUU = "1";
    /** ヘルプモード 詳細 */
    public static final String IPK_HELP_SYOUSAI = "2";
    /** ヘルプモード CPU */
    public static final String IPK_HELP_CPU = "0";
    /** ヘルプモード メモリ */
    public static final String IPK_HELP_MEMORY = "1";
    /** ヘルプモード HD */
    public static final String IPK_HELP_HD = "2";
    /** ヘルプモード CPU編集 */
    public static final String IPK_HELP_CPU_HENSYUU = "3";
    /** ヘルプモード メモリ編集 */
    public static final String IPK_HELP_MEMORY_HENSYUU = "4";
    /** ヘルプモード HD編集 */
    public static final String IPK_HELP_HD_HENSYUU = "5";

    /** オーダーキー 昇順 */
    public static final int IPK_ORDER_ASC = 0;
    /** オーダーキー 降順 */
    public static final int IPK_ORDER_DESC = 1;
    /** ソートキー IPアドレス */
    public static final int IPK_SORT_IPAD = 0;
    /** ソートキー マシン名 */
    public static final int IPK_SORT_NAME = 1;
    /** ソートキー 使用区分 */
    public static final int IPK_SORT_USEDKBN = 2;
    /** ソートキー コメント */
    public static final int IPK_SORT_MSG = 3;
    /** ソートキー ネットワークアドレス */
    public static final int IPK_SORT_NETAD = 4;
    /** ソートキー サブネットマスク */
    public static final int IPK_SORT_SUBNET = 5;
    /** ソートキー CPU */
    public static final int IPK_SORT_CPU = 6;
    /** ソートキー メモリ */
    public static final int IPK_SORT_MEMORY = 7;
    /** ソートキー HD */
    public static final int IPK_SORT_HD = 8;

    /** 追加モード */
    public static final int IPK_TUIKA = 0;
    /** 編集モード */
    public static final int IPK_HENSYU = 1;

    /** 管理者権限 有 */
    public static final boolean KANRI_KENGEN_ARI = true;
    /** 管理者権限 無 */
    public static final boolean KANRI_KENGEN_NASI = false;
    /** 管理者権限 IP管理者 */
    public static final String KANRI_KENGEN_IP = "-1";
    /** 変更ボタン 表示 */
    public static final String BUTTON_DSP = "1";
    /** 削除チェックボックス 表示 */
    public static final boolean CHECKBOX_DSP = true;
    /** ネットワーク添付ファイル 登録時 */
    public static final boolean NETWORKTEMP_DSP = true;
    /** 添付ファイル 表示 */
    public static final int IPK_TEMP_DSP_KOUKAI = 1;
    /** 添付ファイル 非表示 */
    public static final int IPK_TEMP_DSP_HIKOUKAI = 0;
    /** 添付ファイル 表示非表示 */
    public static final int IPK_TEMP_DSP = 2;

    /** ネットワーク公開 */
    public static final String IPK_NET_DSP = "0";
    /** ネットワーク非公開 */
    public static final String IPK_NET_NOT_DSP = "9";

    /** メッセージに表示するテキスト CPU */
    public static final String TEXT_CPU = "CPU";
    /** メッセージに表示するテキスト HD */
    public static final String TEXT_HD = "HD";
    /** ネットワークアドレス */
    public static final String NETWORK = "network";
    /** IPアドレス */
    public static final String IPADDRESS = "ipAddress";
    /** スペック */
    public static final String SPEC = "spec";

    /** IPアドレスのテキストボックス数フラグ */
    public static final String TEXT_NUMFLG1 = "1";
    /** IPアドレスのテキストボックス数フラグ */
    public static final String TEXT_NUMFLG2 = "2";
    /** IPアドレスのテキストボックス数フラグ */
    public static final String TEXT_NUMFLG3 = "3";
    /** IPアドレスのテキストボックス数フラグ */
    public static final String TEXT_NUMFLG4 = "4";

    /** ネットワーク名MAX文字数 */
    public static final int MAX_LENGTH_NAME = 50;
    /** ネットワークアドレスMAX文字数 */
    public static final int MAX_LENGTH_NETAD = 3;
    /** サブネットマスクMAX文字数 */
    public static final int MAX_LENGTH_SABNET = 3;
    /** サブネットマスク全体MAX文字数 */
    public static final int MAX_LENGTH_ALLSABNET = 12;
    /** 表示順MAX文字数 */
    public static final int MAX_LENGTH_SORT = 3;
    /** コメントMAX文字数 */
    public static final int MAX_LENGTH_MSG = 1000;
    /** IPアドレス */
    public static final int MAX_LENGTH_IPAD = 3;
    /** マシン名 */
    public static final int MAX_LENGTH_MACHINE = 50;
    /** 資産管理番号 */
    public static final int MAX_LENGTH_PRTMNG_NUM = 50;
    /** スペックマスタ 名前 */
    public static final int MAX_LENGTH_SPECM_NAME = 50;
    /** スペックマスタ 性能 */
    public static final int MAX_LENGTH_SPECM_LEVEL = 9;
    /** スペックマスタ 備考 */
    public static final int MAX_LENGTH_SPECM_BIKO = 1000;

    /** ユーザ使用区分 全件*/
    public static final String USEDKBN_ALL = "2";
    /** ユーザ使用区分 使用*/
    public static final String USEDKBN_SIYOU = "1";
    /** ユーザ使用区分 未使用*/
    public static final String USEDKBN_MISIYOU = "0";

    /** 1ページ最大表示件数 全件表示 */
    public static final String IPAD_LIMIT_ALL = "0";
    /** 1ページ最大表示件数 全件表示 */
    public static final String IPAD_PAGE_NUM = "1";
    /** 1ページ最大表示件数 検索画面 */
    public static final int SEARCH_LIMIT = 10;
    /** IPアドレスのフォーマット 全体 */
    public static final String IPAD_FORMAT = "000000000000";
    /** IPアドレスのフォーマット 単体*/
    public static final String IPAD_FORMAT_PART = "000";
    /** IPアドレスのテキストボックス数フラグ */
    public static final String IPAD_TOUROKU_COUNT0 = "0";
    /** サブネットマスクに入力可能な数値 */
    public static final int[] SUBNETMASK_OK_NUM = {0, 128, 192, 224, 240, 248, 252, 254, 255};

    /** 不正アクセスフラグ エラー無し（正常） */
    public static final String ACCESS_ERROR_OK = "0";
    /** 不正アクセスフラグ 管理者権限エラー */
    public static final String ACCESS_ERROR_ADMIN = "1";
    /** 不正アクセスフラグ 不正アクセスエラー */
    public static final String ACCESS_ERROR_NO_PARAM = "2";

    /** 添付ファイル登録モード　ネットワーク登録 */
    public static final int NETWORK_TOUROKU = -1;
    /** ネットワーク登録時のIPアドレスSID */
    public static final int IADSID_NETWORK_TOUROKU = 0;

    /** インポートモード　追加 */
    public static final String IMPORT_MODE_TUIKA = "0";
    /** インポートモード　上書き */
    public static final String IMPORT_MODE_UWAGAKI = "1";

    /** 検索キーワード条件 全てを含む*/
    public static final String SEARCH_KEYWORD_AND = "0";
    /** 検索キーワード条件 いずれかを含む*/
    public static final String SEARCH_KEYWORD_OR = "1";
    /** 検索対象 IPアドレス*/
    public static final String SEARCH_TARGET_IPAD = "1";
    /** 検索対象 使用状況*/
    public static final String SEARCH_TARGET_USEKBN = "2";
    /** 検索対象 コメント*/
    public static final String SEARCH_TARGET_MSG = "3";

    /** ページ遷移モード IPアドレス一覧*/
    public static final int IPK_PAGEMODE_IPAD = 0;
    /** ページ遷移モード 詳細検索*/
    public static final int IPK_PAGEMODE_SEARCH = 1;

    /** 添付ファイル 公開 */
    public static final String IPK_TEMP_KOUKAI = "koukai";
    /** 添付ファイル 非公開 */
    public static final String IPK_TEMP_HIKOUKAI = "hikoukai";
    /** 添付ファイル 存在する */
    public static final boolean IPK_TEMP_EXIST = true;
    /** 添付ファイル 存在しない */
    public static final boolean IPK_TEMP_NOT_EXIST = false;

    /** ネットワーク情報 表示 */
    public static final String IPK_NETINF_HYOUJI = "hyouji";
    /** ネットワーク情報 非表示 */
    public static final String IPK_NETINF_HIHYOUJI = "hihyouji";

    /** スペックマスタ 追加モード */
    public static final String IPK_SPECM_TUIKA = "1";
    /** スペックマスタ 変更モード */
    public static final String IPK_SPECM_HENKOU = "2";
    /** スペック区分 CPU */
    public static final int IPK_SPECKBN_CPU = 1;
    /** スペック区分 メモリ */
    public static final int IPK_SPECKBN_MEMORY = 2;
    /** スペック区分 HD */
    public static final int IPK_SPECKBN_HD = 3;
    /** スペックSID 未登録 */
    public static final int SPEC_MITOUROKU = 0;
    /** 表示順開始番号 */
    public static final int IPK_MAX_LEVEL = 100000;
    /** スペック存在する */
    public static final boolean IPK_SPEC_EXIST = true;
    /** スペック存在しない */
    public static final boolean IPK_SPEC_NOT_EXIST = false;

    /** コメント表示文字数 検索結果一覧*/
    public static final int IPK_MSG_NUM_SEARCH = 15;
    /** コメント表示文字数 IPアドレス結果一覧 */
    public static final int IPK_MSG_NUM_IPAD = 20;

    /** IPアドレスデータインポートcsv サンプルファイル名 */
    public static final String SAMPLE_IP_CSV = "sample_ip.xls";
}