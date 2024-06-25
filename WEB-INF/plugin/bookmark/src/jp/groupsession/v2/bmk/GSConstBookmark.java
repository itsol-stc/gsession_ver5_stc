package jp.groupsession.v2.bmk;

/**
 * <br>[機  能] ブックマークの定数クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstBookmark {

    /** プラグインID ブックマーク */
    public static final String PLUGIN_ID_BOOKMARK = "bookmark";

    /** 採番ID ブックマーク */
    public static final String SBNSID_BOOKMARK = "bookmark";
    /** 採番IDサブ URLSID */
    public static final String SBNSID_SUB_URL = "url";
    /** 採番IDサブ ブックマークSID */
    public static final String SBNSID_SUB_BMK = "bmk";
    /** 採番IDサブ ラベルSID */
    public static final String SBNSID_SUB_LABEL = "label";

    /** ブックマーク処理モード 登録 */
    public static final int BMK_MODE_TOUROKU = 0;
    /** ブックマーク処理モード 編集 */
    public static final int BMK_MODE_EDIT = 1;

    /** ブックマーク区分 個人ブックマーク */
    public static final int BMK_KBN_KOJIN = 0;
    /** ブックマーク区分 グループブックマーク */
    public static final int BMK_KBN_GROUP = 1;
    /** ブックマーク区分 共有ブックマーク */
    public static final int BMK_KBN_KYOYU = 2;

    /** 公開区分 非公開 */
    public static final int KOKAI_NO = 0;
    /** 公開区分 公開 */
    public static final int KOKAI_YES = 1;

    /** 表示区分 非表示 */
    public static final int DSP_NO = 0;
    /** 表示区分 表示 */
    public static final int DSP_YES = 1;

    /** ラベル統合区分 しない */
    public static final int LABEL_TOGO_NO = 0;
    /** ラベル統合区分 する */
    public static final int LABEL_TOGO_YES = 1;

    /** ラベル処理モード 登録 */
    public static final int LABEL_MODE_TOUROKU = 0;
    /** ラベル処理モード 編集 */
    public static final int LABEL_MODE_EDIT = 1;

    /** 登録区分 未登録 */
    public static final int TOROKU_NO = 0;
    /** 登録区分 登録済 */
    public static final int TOROKU_YES = 1;

    /** 権限有無 権限なし */
    public static final int POW_NO = 0;
    /** 権限有無 権限あり */
    public static final int POW_YES = 1;

    /** 編集権限 管理者のみ */
    public static final int EDIT_POW_ADMIN = 0;
    /** 編集権限 グループ指定 */
    public static final int EDIT_POW_GROUP = 1;
    /** 編集権限 ユーザ指定 */
    public static final int EDIT_POW_USER = 2;
    /** 編集権限 権限設定なし */
    public static final int EDIT_POW_ALL = 3;

    /** グループブックマーク編集権限 管理者のみ */
    public static final int GROUP_EDIT_ADMIN = 0;
    /** グループブックマーク編集権限 グループ別に設定 */
    public static final int GROUP_EDIT_GROUP = 1;
    /** グループブックマーク編集権限 権限設定なし */
    public static final int GROUP_EDIT_ALL = 2;

    /** ブックマーク登録時情報取得制限 制限なし */
    public static final int LIMIT_NO = 0;
    /** ブックマーク登録時情報取得制限 制限あり(取得しない) */
    public static final int LIMIT_YES = 1;

    /** 並び順 昇順 */
    public static final int ORDERKEY_ASC = 0;
    /** 並び順 降順 */
    public static final int ORDERKEY_DESC = 1;

    /** ソートキー 登録 */
    public static final int SORTKEY_ADATE = 0;
    /** ソートキー 評価 */
    public static final int SORTKEY_SCORE = 1;
    /** ソートキー タイトル */
    public static final int SORTKEY_TITLE = 2;
    /** ソートキー 投稿者 */
    public static final int SORTKEY_AUID = 3;

    /** デフォルト表示件数 */
    public static final String DEFAULT_BMKCOUNT = "10";

    /** 新着・ランキング表示件数 */
    public static final int NEW_RANKING_COUNT = 5;
    /** 新着ブックマークデフォルト表示日数 */
    public static final int NEW_DEFO_DSP_COUNT = 3;

    /** ブックマーク ＵＲＬ MAX文字数 */
    public static final int MAX_LENGTH_URL = 1000;
    /** ブックマーク コメント MAX文字数 */
    public static final int MAX_LENGTH_CMT = 1000;
    /** タイムアウト */
    public static final int TIMEOUT = 3000;

    /** ブックマークポートレット グループブックマーク選択画面ID */
    public static final String SCREENID_BMKPTL020 = "bmkptl020";

}
