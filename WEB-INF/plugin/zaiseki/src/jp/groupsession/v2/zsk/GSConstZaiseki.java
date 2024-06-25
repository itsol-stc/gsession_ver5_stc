package jp.groupsession.v2.zsk;

import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>在席管理 定数一覧
 * @author JTS
 */
public class GSConstZaiseki {
    /** プラグインID */
    public static final String PLUGIN_ID_ZAISEKI = "zaiseki";
    /** 採番ID 在席管理 */
    public static final String SBNSID_ZAISEKI = "zaiseki";
    /** 採番IDサブ 座席表情報SID */
    public static final String SBNSID_SUB_ZAISEKIINFO = "info";

    /** エレメントKEY文字 */
    public static final String ELEMENT_KEY = "elKey";
    /** エレメントKEY区切り文字 */
    public static final String ELEMENT_SEPARATOR = "-";
    /** エレメント区分(ユーザ) */
    public static final int ELEMENT_KBN_USR = 0;
    /** エレメント区分(施設) */
    public static final int ELEMENT_KBN_RSV = 1;
    /** エレメント区分(その他) */
    public static final int ELEMENT_KBN_ETC = 2;

    /** 施設予約状態(未使用) */
    public static final int RSV_NOT_USED = 0;
    /** 施設予約状態(使用中) */
    public static final int RSV_USED = 1;

    /** オブジェクトファイル名 エレメント詳細情報 */
    public static final String FNAME_ELEMENT_INFO = "zaisekiElementobjfile";
    /** JSONファイル名 エレメント詳細情報 */
    public static final String FNAME_ELEMENT_JSON = "zaisekiElementjsonfile";

    /** 初期表示フラグ 初期表示 */
    public static final String INIT_FLG_ON = "0";
    /** 初期表示フラグ 初期表示以外 */
    public static final String INIT_FLG_OFF = "1";

    /** 座席表SID 未設定 */
    public static final int ZASEKI_NOT_SELECT = -1;
    /** 自動リロード時間 10分 */
    public static final int AUTO_RELOAD_10MIN = 600000;

    /** 表タイトル最大桁数 */
    public static final int MAX_LENGTH_MAPTITLE = 20;
    /** 表示順最大桁数 */
    public static final int MAX_LENGTH_SORTNUM = 3;
    /** 未選択値 */
    public static final int NONE_SELECT = -1;

    /** メイン画面表示のグループ区分(通常グループ) */
    public static final int DSP_GROUP = 0;
    /** メイン画面表示のグループ区分(マイグループ) */
    public static final int DSP_MYGROUP = 1;

    /** メイン画面:リロードを行わない */
    public static final int MAIN_RELOAD_OFF = 0;
    /** メイン画面:リロードを行う */
    public static final int MAIN_RELOAD_ON = 1;

    /** 投稿者画像(表示する) */
    public static final int USR_IMAGE_DSP = 1;
    /** 投稿者画像(表示しない) */
    public static final int USR_IMAGE_NOT_DSP = 0;

    /** 画面遷移モード 在席管理 */
    public static final int MODE_ZAISEKI = 0;
    /** 画面遷移モード メイン */
    public static final int MODE_MAIN = 1;

    /** 遷移先:ショートメール作成へ */
    public static final String SCR_SML_NEW = "gf_smlNew";

    /** ソート 名前 */
    public static final int SORT_KEY_NAME = GSConstUser.USER_SORT_NAME;
    /** ソート 社員/職員番号 */
    public static final int SORT_KEY_SNO = GSConstUser.USER_SORT_SNO;
    /** ソート 役職 */
    public static final int SORT_KEY_YKSK = GSConstUser.USER_SORT_YKSK;
    /** ソート 生年月日 */
    public static final int SORT_KEY_BDATE = GSConstUser.USER_SORT_BDATE;
    /** ソート 在席状況 */
    public static final int SORT_KEY_ZAKSTS = GSConstUser.USER_SORT_UIO;
    /** ソート 在席コメント */
    public static final int SORT_KEY_COMM = GSConstUser.USER_SORT_COMM;
    /** ソート ソートキー1 */
    public static final int SORT_KEY_SORTKEY1 = GSConstUser.USER_SORT_SORTKEY1;
    /** ソート ソートキー2 */
    public static final int SORT_KEY_SORTKEY2 = GSConstUser.USER_SORT_SORTKEY2;

    /** ソートキーALL */
    public static final int[] SORT_KEY_ALL = new int[] { SORT_KEY_NAME,
            SORT_KEY_SNO, SORT_KEY_YKSK, SORT_KEY_BDATE,
            SORT_KEY_SORTKEY1, SORT_KEY_SORTKEY2 };

    /** ソートキー 在席表 */
    public static final int[] SORT_KEY_ZSK =
        new int[] {SORT_KEY_NAME, SORT_KEY_SNO, SORT_KEY_YKSK,
        SORT_KEY_BDATE, SORT_KEY_ZAKSTS, SORT_KEY_COMM,
        SORT_KEY_SORTKEY1, SORT_KEY_SORTKEY2};

    /** メイン画面スケジュール表示区分 表示する */
    public static final int MAIN_SCH_DSP = 0;
    /** メイン画面スケジュール表示区分 表示しない */
    public static final int MAIN_SCH_NOT_DSP = 1;

    /** メイン画面在席グループ表示区分 表示する */
    public static final int MAINGRP_DSP = 0;
    /** メイン画面在席グループ表示区分 表示しない */
    public static final int MAINGRP_NOT_DSP = 1;

    /** 定時一括更新 設定する */
    public static final int FIXED_UPDATE_ON = 0;
    /** 定時一括更新 設定しない */
    public static final int FIXED_UPDATE_OFF = 1;

    /** 座席表表示画像DIR */
    public static final String DSP_IMAGE_DIR = "dspimage";

    /** 管理者設定 在席管理表示区分 0:ユーザ単位で設定 */
    public static final int ADM_SORTKBN_PRI = 0;
    /** 管理者設定 在席管理表示区分 1:管理者強制 */
    public static final int ADM_SORTKBN_ADM = 1;

    /** 在席管理ポートレット グループメンバー選択画面ID */
    public static final String SCREENID_ZSKPTL020 = "zskptl020";

    /** ログ出力種別判別フラグ なし */
    public static final int ZSK_LOG_FLG_NONE = -1;
    /** ログ出力種別判別フラグ 添付ファイル */
    public static final int ZSK_LOG_FLG_DOWNLOAD = 0;

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

    /** 表示順 氏名 */
    public static final int SHOW_SORT_NAME = 1;
    /** 表示順 社員/職員番号 */
    public static final int SHOW_SORT_NUMBER = 2;
    /** 表示順 役職 */
    public static final int SHOW_SORT_POSITION = 3;
    /** 表示順 生年月日 */
    public static final int SHOW_SORT_DATE = 5;
    /** 表示順 在席状況 */
    public static final int SHOW_SORT_STATUS = 6;
    /** 表示順 在席コメント */
    public static final int SHOW_SORT_COMMENT = 7;
    /** 表示順 ソートキー1 */
    public static final int SHOW_SORT_KEY1 = 11;
    /** 表示順 ソートキー2 */
    public static final int SHOW_SORT_KEY2 = 12;

    /** 表示順 氏名 */
    public static final String SHOW_SORT_TEXT_NAME = "cmn.name";
    /** 表示順 社員/職員番号 */
    public static final String SHOW_SORT_TEXT_NUMBER = "cmn.employee.staff.number";
    /** 表示順 役職 */
    public static final String SHOW_SORT_TEXT_POSITION = "cmn.post";
    /** 表示順 生年月日 */
    public static final String SHOW_SORT_TEXT_DATE = "cmn.birthday";
    /** 表示順 在席状況 */
    public static final String SHOW_SORT_TEXT_STATUS = "zsk.20";
    /** 表示順 在席コメント */
    public static final String SHOW_SORT_TEXT_COMMENT = "zsk.23";
    /** 表示順 ソートキー1 */
    public static final String SHOW_SORT_TEXT_KEY1 = "cmn.sortkey";
    /** 表示順 ソートキー2 */
    public static final String SHOW_SORT_TEXT_KEY2 = "cmn.sortkey";

    /** 表示順 昇順 */
    public static final int SHOW_SORT_ORDER_ASC = 0;
    /** 表示順 降順 */
    public static final int SHOW_SORT_ORDER_DESC = 1;

    /** メイン画面スケジュール初期表示区分 表示する */
    public static final int MAIN_INI_SCH_DSP = 0;
    /** メイン画面スケジュール初期表示区分 表示しない */
    public static final int MAIN_INI_SCH_NOT_DSP = 1;

    /** 開始時刻 0時 */
    public static final int START_TIME_0 = 0;
    /** 開始時刻 1時 */
    public static final int START_TIME_1 = 1;
    /** 開始時刻 2時 */
    public static final int START_TIME_2 = 2;
    /** 開始時刻 3時 */
    public static final int START_TIME_3 = 3;
    /** 開始時刻 4時 */
    public static final int START_TIME_4 = 4;
    /** 開始時刻 5時 */
    public static final int START_TIME_5 = 5;
    /** 開始時刻 6時 */
    public static final int START_TIME_6 = 6;
    /** 開始時刻 7時 */
    public static final int START_TIME_7 = 7;
    /** 開始時刻 8時 */
    public static final int START_TIME_8 = 8;
    /** 開始時刻 9時 */
    public static final int START_TIME_9 = 9;
    /** 開始時刻 10時 */
    public static final int START_TIME_10 = 10;
    /** 開始時刻 11時 */
    public static final int START_TIME_11 = 11;
    /** 開始時刻 12時 */
    public static final int START_TIME_12 = 12;
    /** 開始時刻 13時 */
    public static final int START_TIME_13 = 13;
    /** 開始時刻 14時 */
    public static final int START_TIME_14 = 14;
    /** 開始時刻 15時 */
    public static final int START_TIME_15 = 15;
    /** 開始時刻 16時 */
    public static final int START_TIME_16 = 16;
    /** 開始時刻 17時 */
    public static final int START_TIME_17 = 17;
    /** 開始時刻 18時 */
    public static final int START_TIME_18 = 18;
    /** 開始時刻 19時 */
    public static final int START_TIME_19 = 19;
    /** 開始時刻 20時 */
    public static final int START_TIME_20 = 20;
    /** 開始時刻 21時 */
    public static final int START_TIME_21 = 21;
    /** 開始時刻 22時 */
    public static final int START_TIME_22 = 22;
    /** 開始時刻 23時 */
    public static final int START_TIME_23 = 23;

    /** 在席状況 その他 */
    public static final int STATUS_OTHER = 0;
    /** 在席状況 在席 */
    public static final int STATUS_PRESENCE = 1;
    /** 在席状況 不在 */
    public static final int STATUS_ABSENCE = 2;

    /** 表示順 各ユーザが設定する */
    public static final int ORDER_ALL_USER = 0;
    /** 表示順 管理者が設定する */
    public static final int ORDER_ADMIN_USER = 1;
}