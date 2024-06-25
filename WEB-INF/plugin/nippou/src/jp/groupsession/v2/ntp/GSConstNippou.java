package jp.groupsession.v2.ntp;

import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 日報管理定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstNippou {
    /** プラグインID */
    public static final String PLUGIN_ID_NIPPOU = "nippou";
    /** 在席管理のプラグインID */
    public static final String PLUGIN_ID_ZAISEKI = "zaiseki";
    /** ショートメールのプラグインID */
    public static final String PLUGIN_ID_SMAIL = "smail";
    /** スケジュールのプラグインID */
    public static final String PLUGIN_ID_SCHEDULE = "schedule";
    /** プロジェクトのプラグインID */
    public static final String PLUGIN_ID_PROJECT = "project";

    /** 採番ID 日報 */
    public static final String SBNSID_NIPPOU = "nippou";
    /** 採番IDサブ 日報 */
    public static final String SBNSID_SUB_NIPPOU = "ntp";
    /** 採番ID 日報コメント */
    public static final String SBNSID_NIPPOU_COMMENT = "nipcomment";
    /** 採番IDサブ 日報コメント */
    public static final String SBNSID_SUB_NIPPOU_COMMENT = "npc";
    /** 採番IDサブ 案件情報 */
    public static final String SBNSID_SUB_ANKEN = "anken";
    /** 採番IDサブ 案件履歴情報 */
    public static final String SBNSID_SUB_ANKEN_HISTORY = "ankenhistory";
    /** 採番IDサブ 商品 */
    public static final String SBNSID_SUB_SHOHIN = "shohin";
    /** 採番IDサブ 業務 */
    public static final String SBNSID_SUB_GYOMU = "gyomu";
    /** 採番IDサブ プロセス */
    public static final String SBNSID_SUB_PROCESS = "process";
    /** 採番IDサブ 活動分類 */
    public static final String SBNSID_SUB_KTBUNRUI = "ktbunrui";
    /** 採番IDサブ 活動方法 */
    public static final String SBNSID_SUB_KTHOUHOU = "kthouhou";
    /** 採番IDサブ コンタクト */
    public static final String SBNSID_SUB_CONTACT = "contact";
    /** 採番IDサブ 目標 */
    public static final String SBNSID_SUB_TARGET = "target";
    /** 採番IDサブ テンプレート */
    public static final String SBNSID_SUB_TEMPLATE = "template";
    /** 採番IDサブ商品カテゴリ*/
    public static final String SBNSID_SUB_SHOHIN_CATEGORY = "shohincategory";
    /** 採番IDサブ商品カテゴリ*/
    public static final String SBNSID_SUB_SPACCESS = "spaccess";

    /** メイン：日間画面でのタイトル非表示スケジュール時間(45分以下非表示) */
    public static final int MAIN_DSP_HOURS = 3;
    /** デフォルト時間分割単位 */
    public static final int DF_HOUR_DIVISION = 10;
    /** デフォルト時間分割単位 */
    public static final int HOUR_DIVISION5 = 5;
    /** デフォルト時間分割単位 */
    public static final int HOUR_DIVISION15 = 15;

    /** 日間画面での時間分割単位(15分単位) */
    public static final int HOUR_DIVISION_COUNT_15 = 4;
    /** 日間画面での時間分割単位(10分単位) */
    public static final int HOUR_DIVISION_COUNT_10 = 6;
    /** 日間画面での時間分割単位(5分単位) */
    public static final int HOUR_DIVISION_COUNT_5 = 12;

    /** 日間画面での表示時間数 */
    public static final int DAILY_HOUR_COUNT = 9;
    /** 日間画面でのCOLS */
//    public static final int DAILY_COLS_COUNT = 40;
//    public static final int DAILY_COLS_COUNT = (DAILY_HOUR_COUNT + 1) * HOUR_DIVISION_COUNT;

    /** メイン日間画面でのROWS */
    public static final int DAILY_ROWS_COUNT = 96;
    /** 週間画面での表示日数 */
    public static final int WEEK_DAY_COUNT = 7;
    /** 日報グループ */
    public static final int NIPPOU_GROUP = -1;
    /** 日報グループ+メンバー */
    public static final int NIPPOU_GROUP_MEMBER = -2;

    /** 年コンボの表示件数 */
    public static final int YEAR_LIST_CNT = 5;
    /** 自動リロード時間 10分 */
    public static final int AUTO_RELOAD_10MIN = 600000;

    /** 開始　時 */
    public static final int DAY_START_HOUR = 0;
    /** 開始　分 */
    public static final int DAY_START_MINUTES = 0;
    /** 開始　秒 */
    public static final int DAY_START_SECOND = 0;
    /** 開始　ミリ秒 */
    public static final int DAY_START_MILLISECOND
    = java.util.Calendar.getInstance().getMinimum(java.util.Calendar.MILLISECOND);
    /** 終了　時 */
    public static final int DAY_END_HOUR = 23;
    /** 終了　分 */
    public static final int DAY_END_MINUTES = 59;
    /** 終了　秒 */
    public static final int DAY_END_SECOND = 59;
    /** 終了　ミリ秒 */
    public static final int DAY_END_MILLISECOND
    = java.util.Calendar.getInstance().getMaximum(java.util.Calendar.MILLISECOND);

    /** 設定可能な最大　分 */
    public static final int DAY_SYSMAX_MINUTES = 55;

    /** デフォルト時間指定：from時 */
    public static final int DF_FROM_HOUR = 9;
    /** デフォルト時間指定：from分 */
    public static final int DF_FROM_MINUTES = 0;

    /** デフォルト時間指定：to時 */
    public static final int DF_TO_HOUR = 18;
    /** デフォルト時間指定：to分 */
    public static final int DF_TO_MINUTES = 0;

    /** デフォルト見込み度 */
    public static final int DF_MIKOMIDO = 0;

    /** デフォルト背景色 */
    public static final int DF_BG_COLOR = 1;

    /** 週間画面 当日表示位置 左 */
    public static final int DAY_POSITION_LEFT = 0;
    /** 週間画面 当日表示位置 右 */
    public static final int DAY_POSITION_RIGHT = 1;

    /** 週間画面 当日表示位置 右  パラメータ */
    public static final int DAY_POSITION_RIGHT_PARAM = -6;

    /** 日報検索 初期時  開始日数*/
    public static final int SEARCH_FR_DAYS = -6;

    /** デフォルトスケジュールグループID */
    public static final int DF_SCHGP_ID = -1;

    /** ユーザ区分 ユーザスケジュール */
    public static final int USER_KBN_USER = 0;
    /** ユーザ区分 グループスケジュール */
    public static final int USER_KBN_GROUP = 1;

    /** グループに所属 */
    public static final int GROUP_BELONG = 1;
    /** グループに未所属 */
    public static final int GROUP_NOT_BELONG = 0;

    /** 時間指定区分 有り */
    public static final int TIME_EXIST = 0;
    /** 時間指定区分 無し */
    public static final int TIME_NOT_EXIST = 1;

    /** 公開区分 する */
    public static final int DSP_PUBLIC = 0;
    /** 公開区分 しない */
    public static final int DSP_NOT_PUBLIC = 1;
    /** 公開区分 予定あり */
    public static final int DSP_YOTEIARI = 2;
    /** 公開区分 指定無し */
    public static final int DSP_ALL = -1;

    /** 公開 する */
    public static final String DSP_PUBLIC_STRING = "公開する";
    /** 公開 しない */
    public static final String DSP_NOT_PUBLIC_STRING = "公開しない";
    /** 公開 予定あり */
    public static final String DSP_YOTEIARI_STRING = "予定あり";

    /** 休日区分 休日 */
    public static final int HOLIDAY_TRUE = 1;
    /** 休日区分 平日 */
    public static final int HOLIDAY_FALSE = 0;

    /** 今日区分 今日 */
    public static final int TODAY_TRUE = 1;
    /** 今日区分 今日以外 */
    public static final int TODAY_FALSE = 0;

    /** 編集 可能 */
    public static final int CAN_EDIT_TRUE = 1;
    /** 編集 不可 */
    public static final int CAN_EDIT_FALSE = 0;

    /** 画面固定表示項目 グループ */
    public static final String STATIC_DSP_GROUP = "グループ";
    /** 画面固定表示項目 グループ + メンバー*/
    public static final String STATIC_DSP_GROUP_MEMBER = "グループ + メンバー";
    /** 時間指定　未設定 */
    public static final String TIME_NOT_SELECT = "-1";
    /** 完了メッセージ */
    public static final String NIPPOU_MSG = "日報";
    /** 個人設定完了メッセージ */
    public static final String NIPPOU_CONF_MSG = "日報個人設定";

    /** エラーメッセージに表示するテキスト 日報SID */
    public static final String TEXT_NIPPOU_SID = "日報SID";
    /** エラーメッセージに表示するテキスト ユーザSID */
    public static final String TEXT_USER_SID = "ユーザSID";
    /** エラーメッセージに表示するテキスト 開始 */
    public static final String TEXT_SELECT_FROM = "開始";
    /** エラーメッセージに表示するテキスト 終了 */
    public static final String TEXT_SELECT_TO = "終了";
    /** エラーメッセージに表示するテキスト 期間 */
    public static final String TEXT_SELECT_PERIOD = "期間";
    /** エラーメッセージに表示するテキスト 時間 */
    public static final String TEXT_SELECT_TIME = "時間";
    /** エラーメッセージに表示するテキスト タイトル */
    public static final String TEXT_TITLE = "タイトル";
    /** エラーメッセージに表示するテキスト 内容 */
    public static final String TEXT_VALUE = "内容";
    /** エラーメッセージに表示するテキスト 備考 */
    public static final String TEXT_BIKO = "備考";
    /** メッセージに表示するテキスト ソート順 */
    public static final String TEXT_SORT = "ソート順";
    /** メッセージに表示するテキスト (検索対象) */
    public static final String MSG_TXT_TARGET = "検索対象";
    /** エラーメッセージに表示するテキスト カラーコメント */
    public static final String TEXT_COLOR_COMMENT = "カラーコメント";
    /** エラーメッセージに表示するテキスト カラーコメント */
    public static final String TEXT_BGCOLOR = "タイトル色";
    /** メッセージに表示するテキスト コンタクトコード */
    public static final String TEXT_CONTACT_CODE = "コンタクトコード";
    /** メッセージに表示するテキスト コンタクト名 */
    public static final String TEXT_CONTACT_NAME = "コンタクト名";
    /** メッセージに表示するテキスト 活動方法SID */
    public static final String TEXT_KTHOUHOU_SID = "活動方法SID";
    /** メッセージに表示するテキスト 活動方法コード */
    public static final String TEXT_KTHOUHOU_CODE = "活動方法コード";
    /** メッセージに表示するテキスト 活動方法名 */
    public static final String TEXT_KTHOUHOU_NAME = "活動方法名";
    /** メッセージに表示するテキスト 活動分類SID */
    public static final String TEXT_KTBUNRUI_SID = "活動分類SID";
    /** メッセージに表示するテキスト 活動分類コード */
    public static final String TEXT_KTBUNRUI_CODE = "活動分類コード";
    /** メッセージに表示するテキスト 活動分類名 */
    public static final String TEXT_KTBUNRUI_NAME = "活動分類名";

    /** メッセージに表示するテキスト 目標名 */
    public static final String TEXT_TARGET_NAME = "目標名";
    /** メッセージに表示するテキスト 単位 */
    public static final String TEXT_TARGET_UNIT = "単位";
    /** メッセージに表示するテキスト 内容 */
    public static final String TEXT_TARGET_DETAIL = "内容";
    /** メッセージに表示するテキスト 初期値 */
    public static final String TEXT_TARGET_DEF = "初期値";

    /** メッセージに表示するテキスト テンプレート名 */
    public static final String TEXT_TEMPLATE_NAME = " テンプレート名";

    /** メッセージに表示するテキスト 業種 */
    public static final String TEXT_GYOMU = "業種";
    /** メッセージに表示するテキスト プロセスSID */
    public static final String TEXT_PROCESS_SID = "プロセスSID";
    /** メッセージに表示するテキスト プロセスコード */
    public static final String TEXT_PROCESS_CODE = "プロセスコード";
    /** メッセージに表示するテキスト プロセス名 */
    public static final String TEXT_PROCESS_NAME = "プロセス名";
    /** メッセージに表示するテキスト 内容 */
    public static final String TEXT_NAIYO = "内容";
    /** メッセージに表示するテキスト 内容 */
    public static final String TEXT_MIKOMI = "見込み度";
    /** メッセージに表示するテキスト 業種SID */
    public static final String TEXT_GYOMU_SID = "業種SID";
    /** メッセージに表示するテキスト 業種コード */
    public static final String TEXT_GYOMU_CODE = "業種コード";
    /** メッセージに表示するテキスト 業種名 */
    public static final String TEXT_GYOMU_NAME = "業種名";
    /** メッセージに表示するテキスト 商品コード */
    public static final String TEXT_SHOHIN_CODE = "商品コード";
    /** メッセージに表示するテキスト 商品名 */
    public static final String TEXT_SHOHIN_NAME = "商品名";
    /** メッセージに表示するテキスト 販売価格 */
    public static final String TEXT_PRICE_SALE = "販売価格";
    /** メッセージに表示するテキスト 原価価格 */
    public static final String TEXT_PRICE_COST = "原価価格";
    /** メッセージに表示するテキスト 補足事項 */
    public static final String TEXT_HOSOKU = "補足事項";
    /** 表示テキスト 報告日 */
    public static final String TEXT_REPORT_DATE = "報告日";
    /** 表示テキスト 稼動時間（時） */
    public static final String TEXT_WORK_HOUR = "稼動時間（時）";
    /** 表示テキスト 稼動時間（分） */
    public static final String TEXT_WORK_MINUTE = "稼動時間（分）";
    /** 表示テキスト 案件SID */
    public static final String TEXT_ANKEN_SID = "案件SID";
    /** 表示テキスト 会社SID */
    public static final String TEXT_COMPANY_SID = "会社SID";
    /** 表示テキスト 会社拠点SID */
    public static final String TEXT_KYOTEN_SID = "会社拠点SID";
    /** 表示テキスト 関連活動日報SID */
    public static final String TEXT_KANREN_SID = "関連活動日報SID";
    /** 表示テキスト 活動継続状態区分 */
    public static final String TEXT_KEIZOKU_KBN = "活動継続状態区分";
    /** 表示テキスト 活動完了予定日 */
    public static final String TEXT_KANRYO_DATE = "活動完了予定日";
    /** 表示テキスト 課題／宿題 */
    public static final String TEXT_KADAI_SYUKUDAI = "課題／宿題";
    /** 表示テキスト 金額 */
    public static final String TEXT_KINGAKU = "金額";
    /** 表示テキスト 所感 */
    public static final String TEXT_SYOKAN = "所感";
    /** メッセージに表示するテキスト 案件コード */
    public static final String TEXT_ANKEN_CODE = "案件コード";
    /** メッセージに表示するテキスト 案件名 */
    public static final String TEXT_ANKEN_NAME = "案件名";
    /** メッセージに表示するテキスト 案件名 */
    public static final String TEXT_ANKEN_SYOSAI = "案件詳細";
    /** メッセージに表示するテキスト 見積金額 */
    public static final String TEXT_ANKEN_MITUMORI = "見積金額";
    /** メッセージに表示するテキスト 提出日付*/
    public static final String TEXT_ANKEN_MITUMORI_DATE = "提出日付";
    /** メッセージに表示するテキスト 受注金額 */
    public static final String TEXT_ANKEN_JUCYU = "受注金額";
    /** メッセージに表示するテキスト 受注日付*/
    public static final String TEXT_ANKEN_JUTYU_DATE = "受注日付";
    /** メッセージに表示するテキスト 登録日付 */
    public static final String TEXT_ANKEN_DATE = "登録日付";
    /** メッセージに表示するテキスト 登録日付 From */
    public static final String TEXT_ANKEN_DATE_FROM = "登録日付 From";
    /** メッセージに表示するテキスト 登録日付 To */
    public static final String TEXT_ANKEN_DATE_TO = "登録日付 To";
    /** メッセージに表示するテキスト 投稿日時 From-投稿日時 To */
    public static final String TEXT_ANKEN_DATE_FROM_TO = "登録日付 From-登録日付 To";
    /** 表示テキスト 投稿日時 From < 投稿日時 To */
    public static final String TEXT_ANKEN_DATE_MIN = "登録日付 From < 登録日付 To";
    /** メッセージに表示するテキスト 企業コード */
    public static final String TEXT_COMPANY_CODE = "企業コード";
    /** メッセージに表示するテキスト 会社名 */
    public static final String TEXT_COMPANY_NAME = "会社名";
    /** メッセージに表示するテキスト 会社名カナ */
    public static final String TEXT_COMPANY_NAME_KN = "会社名カナ";
    /** メッセージに表示するテキスト 支店・営業所名 */
    public static final String TEXT_COMPANY_BASE_NAME = "拠点名";
    /** メッセージに表示するテキスト 目標 */
    public static final String TEXT_MOKUHYOU = "目標";
    /** メッセージに表示するテキスト 実績 */
    public static final String TEXT_JISSEKI = "実績";
    /** メッセージに表示するテキスト業種コード */
    public static final String TEXT_GYOUSHU_CODE = "業種コード";
    /** メッセージに表示するテキスト顧客源泉コード */
    public static final String TEXT_KOKYAKUGENSEN_CODE = "顧客源泉コード";
    /** メッセージに表示するテキスト状態 */
    public static final String TEXT_STATE = "状態";

    /** 会社拠点名 MAX文字数 */
    public static final int MAX_LENGTH_BASE_NAME = 100;
    /** 企業コード MAX文字数 */
    public static final int MAX_LENGTH_COMPANY_CODE = 20;
    /** 会社名 MAX文字数 */
    public static final int MAX_LENGTH_COMPANY_NAME = 50;
    /** 会社名(カナ) MAX文字数 */
    public static final int MAX_LENGTH_COMPANY_NAME_KN = 100;
    /** 案件コード MAX文字数 */
    public static final int MAX_LENGTH_ANKEN_CODE = 8;
    /** 案件名 MAX文字数 */
    public static final int MAX_LENGTH_ANKEN_NAME = 100;
    /** 案件詳細 MAX文字数 */
    public static final int MAX_LENGTH_ANKEN_SYOSAI = 1000;
    /** 見積金額 MAX文字数 */
    public static final int MAX_LENGTH_ANKEN_MITUMORI = 9;
    /** 受注金額 MAX文字数 */
    public static final int MAX_LENGTH_ANKEN_JUCYU = 9;
    /** タイトルMAX文字数 */
    public static final int MAX_LENGTH_TITLE = 100;
    /** 内容MAX文字数 */
    public static final int MAX_LENGTH_VALUE = 1000;
    /** 備考MAX文字数 */
    public static final int MAX_LENGTH_BIKO = 1000;
    /** 備考MAX文字数 */
    public static final int MAX_LENGTH_BIKO2 = 300;
    /** コンタクトコード MAX文字数 */
    public static final int MAX_LENGTH_CONTACT_CODE = 5;
    /** コンタクト名 MAX文字数 */
    public static final int MAX_LENGTH_CONTACT_NAME = 50;
    /** 活動方法コード MAX文字数 */
    public static final int MAX_LENGTH_KTHOUHOU_CODE = 5;
    /** 活動方法名 MAX文字数 */
    public static final int MAX_LENGTH_KTHOUHOU_NAME = 50;
    /** 活動分類コード MAX文字数 */
    public static final int MAX_LENGTH_KTBUNRUI_CODE = 5;
    /** 活動分類名 MAX文字数 */
    public static final int MAX_LENGTH_KTBUNRUI_NAME = 50;
    /** プロセスコード MAX文字数 */
    public static final int MAX_LENGTH_PROCESS_CODE = 8;
    /** プロセス名 MAX文字数 */
    public static final int MAX_LENGTH_PROCESS_NAME = 50;
    /** 内容 MAX文字数 */
    public static final int MAX_LENGTH_NAIYO = 1000;
    /** 業務コード MAX文字数 */
    public static final int MAX_LENGTH_GYOMU_CODE = 5;
    /** 業務名 MAX文字数 */
    public static final int MAX_LENGTH_GYOMU_NAME = 50;
    /** 商品コード MAX文字数 */
    public static final int MAX_LENGTH_SHOHIN_CODE = 13;
    /** 商品名 MAX文字数 */
    public static final int MAX_LENGTH_SHOHIN_NAME = 50;
    /** 販売価格 MAX文字数 */
    public static final int MAX_LENGTH_PRICE_SALE = 9;
    /** 原価価格 MAX文字数 */
    public static final int MAX_LENGTH_PRICE_COST = 9;
    /** 補足事項 MAX文字数 */
    public static final int MAX_LENGTH_HOSOKU = 1000;
    /** 目標 名MAX文字数 */
    public static final int MAX_LENGTH_TARGET_NAME = 50;
    /** 目標 単位 MAX文字数 */
    public static final int MAX_LENGTH_TARGET_UNIT = 50;
    /** 目標 内容 MAX文字数 */
    public static final int MAX_LENGTH_TARGET_DETAIL = 1000;
    /** 目標  MAX桁数 */
    public static final int MAX_LENGTH_TARGET_DEF = 15;
    /** 目標 実績 MAX桁数 */
    public static final int MAX_LENGTH_RECORD = 15;

    /** テンプレート 名MAX文字数 */
    public static final int MAX_LENGTH_TEMPLATE_NAME = 50;

    /** 処理モード　登録 */
    public static final String CMD_ADD = "add";
    /** 処理モード　修正 */
    public static final String CMD_EDIT = "edit";
    /** 処理モード　確認 */
    public static final String CMD_KAKUNIN = "kakunin";
    /** 処理モード　コピー */
    public static final String CMD_COPY = "copy";
    /** 処理モード　削除 */
    public static final String CMD_DELETE = "del";
    /** 処理モード　閲覧 */
    public static final String CMD_DSP = "dsp";
    /** 処理モード　pdf出力 */
    public static final String CMD_PDF = "pdf";

    /** 画面遷移元　週間 */
    public static final String DSP_MOD_WEEK = "1";
    /** 画面遷移元　月間 */
    public static final String DSP_MOD_MONTH = "2";
    /** 画面遷移元　グループ日間(モバイル) */
    public static final String DSP_MOD_GROUP_DAY = "2";
    /** 画面遷移元　日間 */
    public static final String DSP_MOD_DAY = "3";
    /** 画面遷移元　メイン */
    public static final String DSP_MOD_MAIN = "4";
    /** 画面遷移元　一覧 */
    public static final String DSP_MOD_LIST = "5";
    /** 画面遷移元　マスタメンテナンス */
    public static final String DSP_MOD_MASTA = "6";
    /** 画面遷移元　案件 */
    public static final String DSP_MOD_ANKEN = "7";
    /** 画面遷移元　目標 */
    public static final String DSP_MOD_TARGET = "8";
    /** 画面遷移元　分析 */
    public static final String DSP_MOD_BUNSEKI = "9";

    /** 一覧 初期表示件数 */
    public static final int DEFAULT_LIST_CNT = 10;

    /** ソート 名前 */
    public static final int SORT_KEY_NAME = GSConstUser.USER_SORT_NAME;
    /** ソート 社員/職員番号 */
    public static final int SORT_KEY_SNO = GSConstUser.USER_SORT_SNO;
    /** ソート 役職 */
    public static final int SORT_KEY_YKSK = GSConstUser.USER_SORT_YKSK;
    /** ソート 生年月日 */
    public static final int SORT_KEY_BDATE = GSConstUser.USER_SORT_BDATE;
    /** ソート ソートキー1*/
    public static final int SORT_KEY_SORTKEY1 = GSConstUser.USER_SORT_SORTKEY1;
    /** ソート ソートキー2*/
    public static final int SORT_KEY_SORTKEY2 = GSConstUser.USER_SORT_SORTKEY2;

    /** ソート 開始日 */
    public static final int SORT_KEY_FRDATE = 2;
    /** ソート 終了日 */
    public static final int SORT_KEY_TODATE = 3;
    /** ソート タイトル */
    public static final int SORT_KEY_TITLE = 4;
    /** ソートキーALL */
    public static final int[] LIST_SORT_KEY_ALL = new int[] { SORT_KEY_NAME,
        SORT_KEY_FRDATE, SORT_KEY_TITLE };
    /** ソートキーALLテキスト */
    public static final String[] LIST_SORT_KEY_ALL_TEXT = new String[] { "名前",
        "日報日付", "タイトル/内容" };

    /** ソート 商品コード */
    public static final int SORT_KEY_NHK_CODE = 0;
    /** ソート 商品名 */
    public static final int SORT_KEY_NHK_NAME = 1;
    /** ソート 販売価格 */
    public static final int SORT_KEY_NHK_PRICE_SALE = 2;
    /** ソート 原価価格 */
    public static final int SORT_KEY_NHK_PRICE_COST = 3;
    /** ソート 商品登録日 */
    public static final int SORT_KEY_NHK_ADATE = 4;
    /** ソート 商品更新日 */
    public static final int SORT_KEY_NHK_EDATE = 5;

    /** ソートキーALL */
    public static final int[] SORT_KEY_NHK_ALL = new int[] { SORT_KEY_NHK_CODE,
    SORT_KEY_NHK_NAME, SORT_KEY_NHK_PRICE_SALE, SORT_KEY_NHK_PRICE_COST,
    SORT_KEY_NHK_ADATE, SORT_KEY_NHK_EDATE};

    /** ソートキーALLテキスト */
    public static final String[] SORT_KEY_NHK_ALL_TEXT = new String[] { "商品コード",
        "商品名", "販売金額", "原価金額", "登録日", "更新日"};

    /** ソート 案件 */
    public static final int SORT_KEY_NAN_ANKEN = 0;
    /** ソート 企業 */
    public static final int SORT_KEY_NAN_COMPANY = 1;
    /** ソート 商品 */
    public static final int SORT_KEY_NAN_SHOHIN = 2;
    /** ソート 業務 */
    public static final int SORT_KEY_NAN_GYOMU = 3;
    /** ソート 現行プロセス */
    public static final int SORT_KEY_NAN_PROCESS = 4;
    /** ソート 見込度 */
    public static final int SORT_KEY_NAN_MIKOMI = 5;
    /** ソート 登録日 */
    public static final int SORT_KEY_NAN_TOUROKU = 6;
    /** ソート 見積金額 */
    public static final int SORT_KEY_NAN_MITUMORI = 7;
    /** ソート 受注金額 */
    public static final int SORT_KEY_NAN_JUTYU = 8;
    /** ソート コンタクト */
    public static final int SORT_KEY_NAN_CONTACT = 9;
    /** ソート 商談結果 */
    public static final int SORT_KEY_NAN_SYODAN = 10;
    /** ソート 更新日 */
    public static final int SORT_KEY_NAN_KOUSHIN = 11;
    /** ソート 案件名 */
    public static final int SORT_KEY_NAN_ANKEN_NAME = 12;
    /** ソート 登録日 */
    public static final int SORT_KEY_NAN_ADATE = 13;

    /** ソートキーALL */
    public static final int[] SORT_KEY_NAN_ALL = new int[] { SORT_KEY_NAN_ANKEN,
    SORT_KEY_NAN_COMPANY, SORT_KEY_NAN_SHOHIN, SORT_KEY_NAN_GYOMU,
    SORT_KEY_NAN_PROCESS, SORT_KEY_NAN_MIKOMI, SORT_KEY_NAN_TOUROKU,
    SORT_KEY_NAN_MITUMORI, SORT_KEY_NAN_JUTYU, SORT_KEY_NAN_CONTACT, SORT_KEY_NAN_SYODAN};

    /** ソートキーALLテキスト */
    public static final String[] SORT_KEY_NAN_ALL_TEXT = new String[] { "案件",
        "企業", "商品", "業種", "現行プロセス", "見込度", "登録日",
        "見積金額", "受注金額", "顧客源泉", "商談結果" };


    /** ソートキーALL 案件検索用 */
    public static final int[] SORT_KEY_NAN_SEARCH_ALL = new int[] { SORT_KEY_NAN_KOUSHIN,
    SORT_KEY_NAN_ANKEN, SORT_KEY_NAN_ANKEN_NAME, SORT_KEY_NAN_COMPANY, SORT_KEY_NAN_GYOMU,
    SORT_KEY_NAN_PROCESS, SORT_KEY_NAN_MIKOMI, SORT_KEY_NAN_TOUROKU,
    SORT_KEY_NAN_MITUMORI, SORT_KEY_NAN_JUTYU, SORT_KEY_NAN_CONTACT, SORT_KEY_NAN_SYODAN};

    /** ソートキーALLテキスト 案件検索用 */
    public static final String[] SORT_KEY_NAN_SEARCH_ALL_TEXT = new String[] { "更新日",
        "案件コード", "案件名", "企業", "業種", "現行プロセス", "見込度", "登録日",
        "見積金額", "受注金額", "顧客源泉", "商談結果" };

    /** ソート 社員 */
    public static final int SORT_KEY_NTP_USER = 0;
    /** ソート 会社名 */
    public static final int SORT_KEY_NTP_COMPANY = 1;
    /** ソート 案件名 */
    public static final int SORT_KEY_NTP_ANKEN = 2;
    /** ソート プロセス */
    public static final int SORT_KEY_NTP_PROCESS = 3;
    /** ソート 活動分類 */
    public static final int SORT_KEY_NTP_KTBUNRUI = 4;
    /** ソート 完了予定日 */
    public static final int SORT_KEY_NTP_ACEDATE = 5;
    /** ソート 完了日 */
    public static final int SORT_KEY_NTP_ACDATE = 6;

    /** ソートキーALL */
    public static final int[] SORT_KEY_NTP_ALL = new int[] {
        SORT_KEY_NTP_USER, SORT_KEY_NTP_COMPANY, SORT_KEY_NTP_ANKEN, SORT_KEY_NTP_PROCESS,
        SORT_KEY_NTP_KTBUNRUI, SORT_KEY_NTP_ACEDATE, SORT_KEY_NTP_ACDATE};

    /** ソートキーALLテキスト */
    public static final String[] SORT_KEY_NTP_ALL_TEXT = new String[] {
        "社員", "会社名", "案件名", "プロセス",
        "活動分類", "完了予定日", "完了日"};

    /** 検索対象 件名 */
    public static final int SEARCH_TARGET_TITLE = 1;
    /** 検索対象 本文 */
    public static final int SEARCH_TARGET_HONBUN = 2;
    /** キーワード検索区分 (and) */
    public static final int KEY_WORD_KBN_AND = 0;
    /** キーワード検索区分 (or) */
    public static final int KEY_WORD_KBN_OR = 1;

    /** ソート 昇順 */
    public static final int ORDER_KEY_ASC = 0;
    /** ソート 降順 */
    public static final int ORDER_KEY_DESC = 1;

    /** ソートキーALL */
    public static final int[] SORT_KEY_ALL = new int[] { SORT_KEY_NAME,
            SORT_KEY_SNO, SORT_KEY_YKSK, SORT_KEY_BDATE,
                               SORT_KEY_SORTKEY1, SORT_KEY_SORTKEY2 };
    /** ソートキーALLテキスト */
    public static final String[] SORT_KEY_ALL_TEXT = new String[] { "氏名",
        "社員/職員番号", "役職", "生年月日", "ソートキー1", "ソートキー2" };
    /** 一覧表示件数 */
    public static final int[] LIST_LINE_COUNTER = new int[] { 10,
            20, 30, 40, 50 };


    /** タイムラインソート順  登録日時▲更新日時▲ */
    public static final int DATE_DESC_EDATE_DESC = 0;
    /** タイムラインソート順   登録日時▲更新日時▼ */
    public static final int DATE_DESC_EDATE_ASC = 1;
    /** タイムラインソート順   登録日時▼更新日時▲ */
    public static final int DATE_ASC_EDATE_DESC = 2;
    /** タイムラインソート順   登録日時▼更新日時▼ */
    public static final int DATE_ASC_EDATE_ASC = 3;


    /** 案件検索ソートキー  案件 */
    public static final int ANKEN_SEARCH_SORT_ANKEN = 0;
    /** 案件検索ソートキー  企業 */
    public static final int ANKEN_SEARCH_SORT_COMPANY = 1;
    /** 案件検索ソートキー  更新日 */
    public static final int ANKEN_SEARCH_SORT_KOUSHIN = 2;
    /** 案件検索ソートキー  商談結果 */
    public static final int ANKEN_SEARCH_SORT_SYODAN = 3;


    //共有範囲設定
    /** 共有設定 (全員で共有する) */
    public static final int CRANGE_SHARE_ALL = 0;
    /** 共有設定 テキスト (全員で共有する) */
    public static final String CRANGE_SHARE_ALL_TEXT = "共有範囲を設定しない(ユーザ全員で共有)";
    /** 共有設定 (グループメンバ内で共有する) */
    public static final int CRANGE_SHARE_GROUP = 1;
    /** 共有設定 テキスト(グループメンバ内で共有する) */
    public static final String CRANGE_SHARE_GROUP_TEXT = "所属グループ内のみ共有可";

    //自動データ削除設定
    /** 自動データ削除 設定しない */
    public static final int AUTO_DELETE_OFF = 0;
    /** 自動データ削除テキスト 設定しない */
    public static final String AUTO_DELETE_OFF_TEXT = "設定しない";
    /** 自動データ削除 自動削除する */
    public static final int AUTO_DELETE_ON = 1;
    /** 自動データ削除テキスト 自動削除する */
    public static final String AUTO_DELETE_ON_TEXT = "自動で削除する";

    //日報確定者設定
    /** 日報確定者設定 (入力必須) */
    public static final int KAKUTEI_INPUT_REQUIRED = 0;
    /** 日報確定者設定 (入力任意) */
    public static final int KAKUTEI_INPUT_FREE = 1;

    //編集権限設定
    /** 編集権限 設定しない */
    public static final int EDIT_CONF_NONE = 0;
    /** 編集権限 本人 */
    public static final int EDIT_CONF_OWN = 1;
    /** 編集権限 グループ */
    public static final int EDIT_CONF_GROUP = 2;

    //拡張登録
    /** 拡張区分 毎日 */
    public static final int EXTEND_KBN_DAY = 1;
    /** 拡張区分 毎週 */
    public static final int EXTEND_KBN_WEEK = 2;
    /** 拡張区分 毎月 */
    public static final int EXTEND_KBN_MONTH = 3;
    /** 振替区分 しない */
    public static final int FURIKAE_KBN_NONE = 0;
    /** 振替区分 前営業日 */
    public static final int FURIKAE_KBN_BEF = 1;
    /** 振替区分 翌営業日 */
    public static final int FURIKAE_KBN_AFT = 2;
    /** 振替区分 登録をしない */
    public static final int FURIKAE_KBN_NOADD = 9;
    /** 設定なし */
    public static final int SETTING_NONE = 0;

    /** 拡張SID 初期値 */
    public static final int DEF_SCE_SID = -1;

    /** 施設予約プラグイン使用する */
    public static final int RESERVE_PLUGIN_USE = 0;
    /** 施設予約プラグイン使用しない */
    public static final int RESERVE_PLUGIN_NOT_USE = 1;
    /** プラグイン使用する */
    public static final int PLUGIN_USE = 0;
    /** プラグイン使用しない */
    public static final int PLUGIN_NOT_USE = 1;
    /** マイグループSIDの先頭文字*/
    public static final String MY_GROUP_STRING = "M";
    /** ショートメール通知を使用しない */
    public static final int SMAIL_NOT_USE = 0;
    /** ショートメール通知を使用する */
    public static final int SMAIL_USE = 1;

    /** タイトルカラー 青 */
    public static final int BGCOLOR_BLUE = 1;
    /** タイトルカラー 赤 */
    public static final int BGCOLOR_RED = 2;
    /** タイトルカラー 緑 */
    public static final int BGCOLOR_GREEN = 3;
    /** タイトルカラー 橙 */
    public static final int BGCOLOR_ORANGE = 4;
    /** タイトルカラー 黒 */
    public static final int BGCOLOR_BLACK = 5;
    /** タイトルカラー ALL */
    public static final int[] BGCOLOR_ALL = new int[] {
            BGCOLOR_BLUE,
            BGCOLOR_RED,
            BGCOLOR_GREEN,
            BGCOLOR_ORANGE,
            BGCOLOR_BLACK
    };

    /** インポートサンプルCSVファイル名(管理者向け) */
    public static final String SAMPLE_NTP_CSV_NAME_ADM = "sample_admin.xls";
    /** インポートサンプルCSVファイル名(一般向け) */
    public static final String SAMPLE_NTP_CSV_NAME = "sample.xls";
    /** インポートサンプルCSVファイル名(一般向け) */
    public static final String SAMPLE_ANKEN_NTP_CSV_NAME = "sampleAnkenImport.xls";
    /** インポート項目数(管理者向け) */
    public static final int IMP_VALUE_SIZE_ADM = 12;
    /** インポート項目数(一般向け) */
    public static final int IMP_VALUE_SIZE = 11;
    /** インポート項目数(案件) */
    public static final int IMP_ANKEN_SIZE = 14;
    /** インポートサンプルCSVファイル名(商品) */
    public static final String SAMPLE_NTP_CSV_SHOHIN = "sampleShohinImport.xls";

    /** 開始日付区分 */
    public static final int FROM_DATE_KBN = 0;
    /** 終了日付区分 */
    public static final int TO_DATE_KBN = 1;
    /** ユーザ指定　未設定 */
    public static final String USER_NOT_SELECT = "-1";
    /** 初期表示フラグ 初期 */
    public static final int INIT_FLG = 0;
    /** 初期表示フラグ 初期済 */
    public static final int NOT_INIT_FLG = 1;

    /** 同時登録選択区分 選択解除 */
    public static final int SELECT_OFF = 0;
    /** 同時登録選択区分 選択 */
    public static final int SELECT_ON = 1;

    /** 空き状況遷移元画面　通常 */
    public static final int MOVE_NO = 0;
    /** 空き状況遷移元画面　拡張 */
    public static final int MOVE_EX = 1;

    /** 表示テキスト CSVファイル */
    public static final String TEXT_CSV_FILE = "CSV形式のファイル";
    /** 表示テキスト 取込みファイル */
    public static final String TEXT_SELECT_FILE = "取込みファイル";
    /** 表示テキスト マイグループ指定 */
    public static final String TEXT_SELECT_MYGROUP = "マイグループ";
    /** 表示テキスト 登録対象 */
    public static final String TEXT_IMPORT_TARGET = "登録対象";
    /** 表示テキスト CSVファイル */
    public static final String TEXT_CSV_VALUE_COUNT = "CSV項目数";
    /** 表示テキスト ログインID */
    public static final String TEXT_LOGIN_ID = "ログインID";
    /** 表示テキスト グループID */
    public static final String TEXT_GROUP_ID = "グループID";
    /** 表示テキスト 開始日付 */
    public static final String TEXT_FR_DATE = "開始日付";
    /** 表示テキスト 終了日付 */
    public static final String TEXT_TO_DATE = "終了日付";
    /** 表示テキスト 開始時刻 */
    public static final String TEXT_FR_TIME = "開始時刻";
    /** 表示テキスト 終了時刻 */
    public static final String TEXT_TO_TIME = "終了時刻";
    /** 表示テキスト タイトル */
    public static final String TEXT_TITLE_NAME = "タイトル";
    /** 表示テキスト タイトル色 */
    public static final String TEXT_TITLE_COLOR = "タイトル色";
    /** 表示テキスト 見込み度 */
    public static final String TEXT_MIKOMIDO = "見込み度";
    /** 表示テキスト 商談結果 */
    public static final String TEXT_SHODAN = "商談結果";
    /** 表示テキスト 編集権限 */
    public static final String TEXT_EDIT_KBN = "編集権限";
    /** 表示テキスト 公開区分 */
    public static final String TEXT_PUBLIC_KBN = "公開区分";

    /** 表示テキスト 一括登録する施設 */
    public static final String TEXT_SELECT_NIPPOU = "一括確認する日報";

    //日報確認設定
    /** 日報確認 未確認 */
    public static final int KAKUNIN_NO = 0;
    /** 日報確認　確認済 */
    public static final int KAKUNIN_YES = 1;

    //日報一括確認ボタン使用可能設定
    /** 日報一括確認ボタン　使用不能 */
    public static final int IKKATU_ENABLE_OFF = 0;
    /** 日報一括確認ボタン　使用可能 */
    public static final int IKKATU_ENABLE_ON = 1;

    /** 関連活動設定 しない */
    public static final int TIEUP_NO = 0;
    /** 関連活動設定 する */
    public static final int TIEUP_YES = 1;

//    /** 見込度 大 */
//    public static final int MIKOMI_DAI = 0;
//    /** 見込度 中 */
//    public static final int MIKOMI_CHU = 1;
//    /** 見込度 小 */
//    public static final int MIKOMI_SYO = 2;

    /** 見込度 10% */
    public static final int MIKOMI_10 = 0;
    /** 見込度 30% */
    public static final int MIKOMI_30 = 1;
    /** 見込度 50% */
    public static final int MIKOMI_50 = 2;
    /** 見込度 70% */
    public static final int MIKOMI_70 = 3;
    /** 見込度 100% */
    public static final int MIKOMI_100 = 4;

    /** 見込度ALL */
    public static final String[] MIKOMI_ALL = new String[] {
        String.valueOf(MIKOMI_10),
        String.valueOf(MIKOMI_30),
        String.valueOf(MIKOMI_50),
        String.valueOf(MIKOMI_70),
        String.valueOf(MIKOMI_100)};

    /** 見込み度MAX文字数 */
    public static final int MAX_LENGTH_MIKOMIDO = 1000;

    /** 活動継続状態区分 未完了 */
    public static final int KEIZOKU_KBN_NO = 0;
    /** 活動継続状態区分 完了 */
    public static final int KEIZOKU_KBN_YES = 1;

    /** 商談結果 商談中 */
    public static final int SYODAN_CHU = 0;
    /** 商談結果 受注 */
    public static final int SYODAN_JYUCHU = 1;
    /** 商談結果 失注 */
    public static final int SYODAN_SICHU = 2;

    /** 状態 未完了 */
    public static final int STATE_UNCOMPLETE = 0;
    /** 状態  完了 */
    public static final int STATE_COMPLETE = 1;

    /** 見込度ALL */
    public static final String[] SYODAN_ALL = new String[] {
        String.valueOf(SYODAN_CHU),
        String.valueOf(SYODAN_JYUCHU),
        String.valueOf(SYODAN_SICHU)};

    /** ポップアップ画面パラメータ */
    public static final String POP_UP = "popup";

    /** 登録・編集権限 あり*/
    public static final int AUTH_ADD_EDIT = 0;
    /** 登録・編集権限 なし*/
    public static final int AUTH_NOT_ADD_EDIT = 1;

    /** ショートメール通知設定区分  管理者が設定*/
    public static final int SML_NOTICE_ADM = 0;
    /** ショートメール通知設定区分  個人が設定*/
    public static final int SML_NOTICE_USR = 1;

    /** ショートメール通知区分  通知しない*/
    public static final int SML_NOTICE_NO = 0;
    /** ショートメール通知区分  通知する*/
    public static final int SML_NOTICE_YES = 1;

    /** 次のアクション スケジュール表示  表示する*/
    public static final int SCH_DSP_YES = 0;
    /** 次のアクション スケジュール表示  表示しない*/
    public static final int SCH_DSP_NO = 1;

    /** ショートメール通知先区分  所属グループ*/
    public static final int SML_NOTICE_GROUP = 0;
    /** ショートメール通知先区分  所属グループの管理者*/
    public static final int SML_NOTICE_GROUP_ADM = 1;

    /** 表示項目 案件 */
    public static final int ITEM_ANKEN = 0;
    /** 表示項目 企業・顧客  */
    public static final int ITEM_COMPANY = 1;
    /** 表示項目 活動分類/方法   */
    public static final int ITEM_KATUDO = 2;
    /** 表示項目 見込み度    */
    public static final int ITEM_MIKOMIDO = 3;
    /** 表示項目 添付ファイル    */
    public static final int ITEM_TEMP = 4;
    /** 表示項目 次のアクション    */
    public static final int ITEM_ACTION = 5;

    /** 設定項目ALL */
    public static final String[] ITEM_ALL = new String[] {
        String.valueOf(ITEM_ANKEN),
        String.valueOf(ITEM_COMPANY),
        String.valueOf(ITEM_KATUDO),
        String.valueOf(ITEM_MIKOMIDO),
        String.valueOf(ITEM_ACTION),
        String.valueOf(ITEM_TEMP)};

    /** 項目 使用する    */
    public static final int ITEM_USE = 0;
    /** 項目 使用しない    */
    public static final int ITEM_NOT_USE = 1;

    /** 項目 両方使用する    */
    public static final int ITEM_BOTH = 0;
    /** 項目 案件 使用する    */
    public static final int ITEM_ANKEN_USE = 1;
    /** 項目 企業・顧客 使用する    */
    public static final int ITEM_COMPANY_USE = 2;
    /** 項目 両方使用しない    */
    public static final int ITEM_BOTH_NOT_USE = 3;

    /** 目標画面表示月数    */
    public static final int DSP_MONTH_CNT = 6;

    /** 企業    */
    public static final String STR_KIGYOU = "kigyou";
    /** 商品    */
    public static final String STR_SHOHIN = "shohin";
    /** 業種    */
    public static final String STR_GYOUSHU = "gyoushu";
    /** プロセス    */
    public static final String STR_PROCESS = "process";
    /** 見込み度    */
    public static final String STR_MIKOMIDO = "mikomido";
    /** 顧客源泉    */
    public static final String STR_KOKYAKUGENSEN = "kokyakugensen";
    /** 稼働時間   */
    public static final String STR_KADOU = "kadou";

    /** カテゴリ内の商品の有無 0 = 存在しない*/
    public static final int CATEGORY_EXIST_NO = 0;
    /** カテゴリ内の商品の有無 1 = 存在する*/
    public static final int CATEGORY_EXIST_YES = 1;

    /** 商品カテゴリ 「未設定」 */
    public static final int SHOHIN_CATEGORY_NOSET = 1;

    /** CSV出力対象ユーザSID */
    public static final String CSV_OUT_USR_ID = "1";
    /** CSV出力対象ユーザSID */
    public static final String CSV_OUT_USR_NAME = "2";
    /** CSV出力対象 報告日 */
    public static final String CSV_OUT_NTP_DATE = "3";
    /** CSV出力対象 開始時刻 */
    public static final String CSV_OUT_NTP_FR_TIME = "4";
    /** CSV出力対象 終了時刻 */
    public static final String CSV_OUT_NTP_TO_TIME = "5";
    /** CSV出力対象 案件コード */
    public static final String CSV_OUT_NAN_CODE = "6";
    /** CSV出力対象 企業コード */
    public static final String CSV_OUT_ACO_CODE = "7";
    /** CSV出力対象 タイトル */
    public static final String CSV_OUT_TITLE = "8";
    /** CSV出力対象 タイトル色 */
    public static final String CSV_OUT_TITLE_COLOR = "9";
    /** CSV出力対象 活動分類コード */
    public static final String CSV_OUT_KTB_CODE = "10";
    /** CSV出力対象 活動方法コード */
    public static final String CSV_OUT_KTH_CODE = "11";
    /** CSV出力対象 内容 */
    public static final String CSV_OUT_VALUE = "12";
    /** CSV出力対象 見込み度 */
    public static final String CSV_OUT_MIKOMIDO = "13";

    /** ログ出力種別判別フラグ なし */
    public static final int NTP_LOG_FLG_NONE = -1;
    /** ログ出力種別判別フラグ 添付ファイル */
    public static final int NTP_LOG_FLG_DOWNLOAD = 0;
    /** ログ出力種別判別フラグ PDFファイル */
    public static final int NTP_LOG_FLG_PDF = 1;

    /** 案件権限制限設定区分 制限なし */
    public static final int NAP_KBN_ALL = 0;
    /** 案件権限制限設定区分 担当者のみ */
    public static final int NAP_KBN_TANTO = 1;
    /** 案件権限制限設定区分 ユーザグループ指定 */
    public static final int NAP_KBN_USERGROUP = 2;



}