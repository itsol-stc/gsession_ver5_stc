package jp.groupsession.v2.enq;


/**
 * <br>[機  能] アンケートの定数定義クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstEnquete {

    /** プラグインID */
    public static final String PLUGIN_ID_ENQUETE = "enquete";

    /** 採番ID アンケート */
    public static final String SBNSID_ENQUETE = "enquete";
    /** 採番ID アンケートSID */
    public static final String SBNSID_SUB_ENQUETE_ID = "enq";
    /** 採番ID アンケート種類SID */
    public static final String SBNSID_SUB_ENQUETE_TYPE = "type";

    /** アンケート 添付ファイル */
    public static final String ENQ_FILE = "enqfile";
    /** アンケート 設問 */
    public static final String ENQ_QUESTION = "enqquestion";
    /** アンケート 設問 編集フォルダ */
    public static final String ENQ_QUESTION_EDIT = "edit";

    /** 年コンボ 範囲 */
    public static final int YEARCOMBO_RANGE = 20;

    /** テンプレートメニューの文字列の長さ */
    public static final int MENU_RANGE_TEMPLAGE_NAME = 12;

    /** コンボボックス グループ一覧 */
    public static final int ANSWER_GROUP_GRPLIST = -9;

    /** アンケートのデータ区分 テンプレート */
    public static final int DATA_KBN_TEMPLATE = 0;
    /** アンケートのデータ区分 草稿 */
    public static final int DATA_KBN_DRAFT = 1;
    /** アンケートのデータ区分 発信 */
    public static final int DATA_KBN_SEND = 3;

    /** 画面ID 個人設定メイン */
    public static final int DSP_ID_800 = 800;
    /** 画面ID 個人設定 表示設定 */
    public static final int DSP_ID_810 = 810;
    /** 画面ID 個人設定 メイン表示設定 */
    public static final int DSP_ID_820 = 820;

    /** 重要度 低 */
    public static final int JUUYOU_0 = 0;
    /** 重要度 中 */
    public static final int JUUYOU_1 = 1;
    /** 重要度 高 */
    public static final int JUUYOU_2 = 2;

    /** 匿名フラグ 非匿名 */
    public static final int ANONYMUS_OFF = 0;
    /** 匿名フラグ 匿名 */
    public static final int ANONYMUS_ON = 1;
    /** 回答公開フラグ 非公開 */
    public static final int KOUKAI_OFF = 0;
    /** 回答公開フラグ 公開 */
    public static final int KOUKAI_ON = 1;

    /** 設問 登録モード */
    public static final String CMD_ADD = "add";
    /** 設問 編集モード */
    public static final String CMD_EDIT = "edit";

    /** 編集モード 新規登録 */
    public static final int EDITMODE_ADD = 0;
    /** 編集モード 編集 */
    public static final int EDITMODE_EDIT = 1;
    /** 編集モード 草稿 */
    public static final int EDITMODE_DRAFT = 2;

    /** 必須フラグ 任意 */
    public static final int REQUIRE_OFF = 0;
    /** 必須フラグ 必須 */
    public static final int REQUIRE_ON = 1;

    /** 添付 無し */
    public static final int TEMP_OFF = 0;
    /** 添付 画像 */
    public static final int TEMP_IMAGE = 1;
    /** 添付 ファイル */
    public static final int TEMP_FILE = 2;
    /** 添付 URL */
    public static final int TEMP_URL = 3;
    /** 添付位置 上 */
    public static final int TEMP_POS_TOP = 0;
    /** 添付位置 下 */
    public static final int TEMP_POS_BOTTOM = 1;

    /** 横線位置 無し */
    public static final int COMMENT_LINE_NONE = 0;
    /** 横線位置 上 */
    public static final int COMMENT_LINE_TOP = 1;
    /** 横線位置 下 */
    public static final int COMMENT_LINE_BOTTOM = 2;
    /** 横線位置 上下 */
    public static final int COMMENT_LINE_UPDOWN = 3;

    /** 初期値 無し */
    public static final int INIT_OFF = 0;
    /** 初期値 有り */
    public static final int INIT_ON = 1;

    /** 入力範囲 無し */
    public static final int RNG_OFF = 0;
    /** 入力範囲 有り */
    public static final int RNG_ON = 1;

    /** その他 無し */
    public static final int OTHER_OFF = 0;
    /** その他 有り（1行） */
    public static final int OTHER_TEXT = 1;
    /** その他 有り（複数行） */
    public static final int OTHER_TEXTAREA = 2;

    /** 選択区分 その他 */
    public static final int CHOICE_KBN_OTHER = -1;

    /** 選択項目 未選択 */
    public static final int CHOICE_INIT_OFF = 0;
    /** 選択項目 選択 */
    public static final int CHOICE_INIT_ON = 1;

    /** 設問種類 コメント */
    public static final int SYURUI_COMMENT = 0;
    /** 設問種類 単一選択 */
    public static final int SYURUI_SINGLE = 1;
    /** 設問種類 複数選択 */
    public static final int SYURUI_MULTIPLE = 2;
    /** 設問種類  文字入力（一行）*/
    public static final int SYURUI_TEXT = 3;
    /** 設問種類 文字入力（複数行） */
    public static final int SYURUI_TEXTAREA = 4;
    /** 設問種類 数値入力 */
    public static final int SYURUI_INTEGER = 5;
    /** 設問種類 日付入力 */
    public static final int SYURUI_DAY = 6;

    /** 表示モード コメント */
    public static final String DSP_MODE_COMMENT = "0";
    /** 表示モード テキスト */
    public static final String DSP_MODE_TEXT = "1";
    /** 表示モード 選択*/
    public static final String DSP_MODE_CHOICE = "2";
    /** 表示モード 数値 */
    public static final String DSP_MODE_INTEGER = "3";
    /** 表示モード 日付 */
    public static final String DSP_MODE_DAY = "4";

    /** アンケート作成可能対象者区分 ユーザ */
    public static final int TAISYO_KBN_USER = 1;
    /** アンケート作成可能対象者区分 グループ */
    public static final int TAISYO_KBN_GROUP = 2;

    /** アンケート作成可能対象者 制限する */
    public static final int CONF_TAISYO_LIMIT = 0;
    /** アンケート作成可能対象者 制限なし */
    public static final int CONF_TAISYO_ALL = 1;

    /** メイン表示フラグ区分 管理者が設定する */
    public static final int CONF_MAIN_DSP_USE_LIMIT = 0;
    /** メイン表示フラグ区分 各ユーザが設定する */
    public static final int CONF_MAIN_DSP_USE_EACH = 1;

    /** メイン表示フラグ 表示しない */
    public static final int CONF_MAIN_DSP_OFF = 0;
    /** メイン表示フラグ 表示する */
    public static final int CONF_MAIN_DSP_ON = 1;

    /** 一覧表示件数区分 管理者が設定する */
    public static final int CONF_LIST_USE_LIMIT = 0;
    /** 一覧表示件数区分 各ユーザが設定する */
    public static final int CONF_LIST_USE_EACH = 1;

    /** 一覧表示件数 */
    public static final int[] CONF_LIST_CNT = {10, 20, 30, 40, 50};

    /** フォルダ表示項目 */
    public static final String[] CONF_LIST_FOLDER = {"受信", "　未回答", "　回答済", "発信", "　未公開", "　回答受付中", "　回答受付終了", "　結果公開終了", "　草稿"};
    public static final int[] CONF_LIST_FOLDER_NO = {0, 1, 2, 10, 11, 12, 13, 14, 20};

    /** 状態区分 未回答 */
    public static final int ANS_KBN_UNANSWERED = 0;
    /** 状態区分 回答済 */
    public static final int ANS_KBN_ANSWERED = 1;

    /** 種類名 最大桁数 */
    public static final int TYPE_NAME_MAX_LENGTH = 100;

    /** 手動・自動削除区分 削除しない */
    public static final int DELETE_KBN_OFF = 0;
    /** 手動・自動削除区分 削除する */
    public static final int DELETE_KBN_ON = 1;

    /** アンケート区分 テンプレート */
    public static final int EMN_DATA_KBN_TEMPLATE = 0;
    /** アンケート区分 草稿 */
    public static final int EMN_DATA_KBN_DRAFT = 1;
    /** アンケート区分 発信 */
    public static final int EMN_DATA_KBN_SEND = 3;
    /** アンケート基本情報 添付区分 無し */
    public static final int EMN_ATTACH_KBN_NONE = 0;
    /** アンケート基本情報 添付区分 画像 */
    public static final int EMN_ATTACH_KBN_IMAGE = 1;
    /** アンケート基本情報 添付区分 ファイル */
    public static final int EMN_ATTACH_KBN_FILE = 2;
    /** アンケート基本情報 添付区分 URL */
    public static final int EMN_ATTACH_KBN_URL = 3;
    /** アンケート基本情報 公開期間_終了日 指定 指定あり */
    public static final int EMN_OPEN_END_KBN_SPECIFIED = 0;
    /** アンケート基本情報 公開期間_終了日 指定 指定なし */
    public static final int EMN_OPEN_END_KBN_NON = 1;
    /** アンケート基本情報 匿名フラグ 非匿名 */
    public static final int EMN_ANONNY_NON = 0;
    /** アンケート基本情報 匿名フラグ 匿名 */
    public static final int EMN_ANONNY_ANONNY = 1;
    /** アンケート基本情報 回答公開フラグ 非公開 */
    public static final int EMN_ANS_OPEN_PRIVATE = 0;
    /** アンケート基本情報 回答公開フラグ 公開 */
    public static final int EMN_ANS_OPEN_PUBLIC = 1;
    /** アンケート基本情報 設問番号_種別 自動 */
    public static final int EMN_QUESEC_TYPE_AUTO = 0;
    /** アンケート基本情報 設問番号_種別 手動 */
    public static final int EMN_QUESEC_TYPE_MANUAL = 1;

    /** アンケート種類 未設定 */
    public static final int ETP_SID_NOTSET = 0;

    /** アンケート_対象者 未設定 */
    public static final int ENQ_SUBJECT_NOTSET = -1;

    /** 設問_基本情報 添付区分 無し */
    public static final int EQM_ATTACH_KBN_NONE = 0;
    /** 設問_基本情報 添付区分 画像 */
    public static final int EQM_ATTACH_KBN_IMAGE = 1;
    /** 設問_基本情報 添付区分 ファイル */
    public static final int EQM_ATTACH_KBN_FILE = 2;
    /** 設問_基本情報 添付区分 URL */
    public static final int EQM_ATTACH_KBN_URL = 3;

    /** 設問_基本情報 横線位置（コメント行） 無し */
    public static final int EQM_LINE_KBN_NONE = 0;
    /** 設問_基本情報 横線位置（コメント行） 上 */
    public static final int EQM_LINE_KBN_TOP = 1;
    /** 設問_基本情報 横線位置（コメント行） 下 */
    public static final int EQM_LINE_KBN_BOTTOM = 2;
    /** 設問_基本情報 横線位置（コメント行） 上下 */
    public static final int EQM_LINE_KBN_UPDOWN = 3;

    /** 回答_基本情報 状態区分 未回答 */
    public static final int EAM_STS_KBN_NO = 0;
    /** 回答_基本情報 状態区分 回答済 */
    public static final int EAM_STS_KBN_YES = 1;

    /** 回答_サブ情報 設問サブ連番 その他 */
    public static final int EQS_SEQ_OTHER = -1;
    /** 回答_サブ情報 設問サブ連番 通常 */
    public static final int EQS_SEQ_NORMAL = 0;

    /** 回答_サブ情報 表示順 未設定 */
    public static final int EQS_DSP_SEC_NONE = 0;

    /** 回答_サブ情報 初期値 有り */
    public static final int EAS_DEF_REG = 1;
    /** 回答_サブ情報 範囲指定 有り */
    public static final int EAS_RNG_REG = 1;

    /** 回答_サブ情報 回答数値 未選択 */
    public static final int EAS_ANS_NUM_NOSELECT = 0;
    /** 回答_サブ情報 回答数値 選択 */
    public static final int EAS_ANS_NUM_SELECT = 1;

    /** 年リスト */
    public static final int[] YEAR_LABEL = {0, 1, 2, 3, 4, 5, 10};
    /** 月リスト */
    public static final int[] MONTH_LABEL = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

    /** アンケート_基本情報 タイトル MAX桁数 */
    public static final int MAX_LEN_EMN_TITLE = 100;
    /** アンケート_基本情報 説明 MAX桁数 */
    public static final int MAX_LEN_EMN_DESC = 1000;
    /** アンケート_基本情報 URL MAX桁数 */
    public static final int MAX_LEN_ATTACH_ID = 100;
    /** アンケート_基本情報 表示名 MAX桁数 */
    public static final int MAX_LEN_ATTACH_NAME = 100;

    /** 設問_基本情報 設問番号 MAX桁数 */
    public static final int MAX_LEN_EQM_QUE_SEC = 10;
    /** 設問_基本情報 設問 MAX桁数 */
    public static final int MAX_LEN_EQM_QUESTION = 100;
    /** 設問_基本情報 説明 MAX桁数 */
    public static final int MAX_LEN_EQM_DESC = 1000;
    /** 設問_基本情報 表示名 MAX桁数 */
    public static final int MAX_LEN_EQM_ATTACH_NAME = 100;

    /** 設問_サブ情報 表示名 MAX桁数 */
    public static final int MAX_LEN_EQS_DSP_NAME = 30;

    /** 回答_サブ情報 数値 */
    public static final int MAX_LEN_EAS_ANS_NUM = 16;
    /** 回答_サブ情報 数値(整数部) */
    public static final int MAX_LEN_EAS_ANS_NUM_INT = 10;
    /** 回答_サブ情報 数値(小数部) */
    public static final int MAX_LEN_EAS_ANS_NUM_DEC = 5;
    /** 回答_サブ情報 テキスト MAX桁数 */
    public static final int MAX_LEN_EAS_ANS_TEXT = 100;
    /** 回答_サブ情報 テキストエリア MAX桁数 */
    public static final int MAX_LEN_EAS_ANS_TEXTAREA = 1000;

    /** ログ出力種別判別フラグ なし */
    public static final int ENQ_LOG_FLG_NONE = -1;
    /** ログ出力種別判別フラグ 添付ファイル */
    public static final int ENQ_LOG_FLG_DOWNLOAD = 0;
    /** ログ出力種別判別フラグ PDF */
    public static final int ENQ_LOG_FLG_PDF = 1;

    /** 回答済み又は設問情報変更時フラグ なし */
    public static final int ENQ_QUS_CHG_NO = 0;
    /** 回答済み又は設問情報変更時フラグ あり */
    public static final int ENQ_QUS_CHG_YES = 1;

    /** 管理者設定_表示設定 管理者が設定する*/
    public static final int ENQ_DISP_ADMIN = 0;
    /** 管理者設定_表示設定 ユーザが設定する*/
    public static final int ENQ_DISP_USER = 1;

    /** 受信一覧 メイン画面 回答済みは表示しない */
    public static final int DSP_CHECKED_NONE = 0;
    /** 受信一覧 メイン画面 回答済みは表示しない */
    public static final int DSP_CHECKED_OK = 1;

    /** 個人設定 メイン表示件数 5 */
    public static final int MAIN_DSPCNT_5 = 5;

    /** 戻り先 メイン画面以外 */
    public static final int BACK_TO_ENQUETE = 0;
    /** 戻り先 メイン画面 */
    public static final int BACK_TO_MAIN = 1;
}
