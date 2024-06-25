package jp.groupsession.v2.prj.prj210;


/**
 * <p>プロジェクト管理 プロジェクト選択画面で使用する管理定数一覧
 * @author JTS
 */
public class Prj210Const {

    /** プロジェクト情報 存在する。 */
    public static final String EXIST = "0";
    /** プロジェクト情報 存在しない */
    public static final String NOT_EXIST = "1";
    /** プロジェクト情報 選択中 */
    public static final String INDEX_SELECT = "2";

    /** 検索条件 公開区分 全て */
    public static final String KOUKAI_KBN_ALL = "-1";
    /** 検索条件 完了区分 全て */
    public static final String PROGRESS_ALL = "-1";

    /** プロジェクト一覧 1ページの最大表示件数 */
    public static final int PROJECTSEARCH_MAXCOUNT = 10;

    /** 検索条件 参加プロジェクト */
    public static final int PROJECT_SANKA = 0;
    /** 検索条件 公開プロジェクト */
    public static final int PROJECT_KOUKAI = 1;

    /** 検索条件 未完 */
    public static final int PROGRESS_MIKAN = 0;
    /** 検索条件 完了 */
    public static final int PROGRESS_END = 1;

}
