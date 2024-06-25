package jp.groupsession.v2.mem;

/**
 * <br>[機  能] メモ管理定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GSConstMemo {
    
    /** プラグインID */
    public static final String PLUGIN_ID_MEMO = "memo";
    
    /** プラグイン名 */
    public static final String PLUGIN_NAME_MEMO = "memo.01";
    
    /** 自動削除区分 削除しない */
    public static final int DELETE_KBN_OFF = 0;
    
    /** 自動削除区分 削除する */
    public static final int DELETE_KBN_ON = 1;
    
    /** 内容最大桁数 */
    public static final int MAX_CONTENT_LENGTH = 1000;
    
    /** ラベル名の最大文字数 */
    public static final int MAX_LABEL_LENGTH = 20;
    
    /** 添付区分 指定なし */
    public static final int TENPU_KBN_NONE = 0;
    
    /** 添付区分 添付有り */
    public static final int TENPU_KBN_YES = 1;
    
    /**  添付区分 添付無し */
    public static final int TENPU_KBN_NO = 2;
    
    /** テンポラリディレクトリID チャット一覧 */
    public static final String DIRID_MEM010 = "mem010";
    
    /** ラベル追加モード:選択追加 */
    public static final int MODE_SELECT = 0;
    /** ラベル追加モード:新規追加 */
    public static final int MODE_ADD = 1;
    
    /** 採番ID メモ */
    public static final String SBN_SID = "memo";

    /** 採番IDサブ ラベルSID */
    public static final String SBN_SID_SUB = "label";

    /** 初期表示フラグ 初期 */
    public static final int INIT_FLG = 0;
    /** 初期表示フラグ 初期済 */
    public static final int NOT_INIT_FLG = 1;

    /** 処理区分 新規追加 */
    public static final int CMDMODE_ADD = 0;
    /** 処理区分 編集 */
    public static final int CMDMODE_EDIT = 1;

    /** 並び順 昇順 */
    public static final int ORDER_ASC = 0;
    /** 並び順 降順 */
    public static final int ORDER_DESC = 1;
    
    /** インデックス 降順  */
    public static final int INDEX_DESC = 0;
    /** インデックス 昇順  */
    public static final int INDEX_ASC = 1;

    //メモ自身の設定 自動削除設定
    /** 削除期限 設定しない */
    public static final int AUTO_DELETE_FLG_OFF = 0;
    /** 削除期限 設定する */
    public static final int AUTO_DELETE_FLG_ON = 1;

    //管理者設定 自動削除設定
    /** 自動削除 設定しない */
    public static final int AUTO_DELETE_KBN_OFF = 0;
    /** 自動削除 自動で削除する */
    public static final int AUTO_DELETE_KBN_ON = 1;

    /** 日次バッチで一度に削除する メモ件数 */
    public static final int MEM_BATCH_DELETE_COUNT = 100;
    
    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;
    
    /** メッセージに表示するテキスト ラベル名 */
    public static final String TEXT_LABEL = "cmn.label.name";
    
    /** 添付ファイル 無し */
    public static final int TENPU_FILE_NO = 0;
    /** 添付ファイル 有り */
    public static final int TENPU_FILE_YES = 1;
    
    /** メモ最大取得件数 */
    public static final int MAX_MEMO_SIZE = 30;
    
    /** メモ一覧 最大メモ内容文字数 */
    public static final int LIST_MAX_CONTENT = 15;
}
