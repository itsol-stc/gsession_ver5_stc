package jp.groupsession.v2.cht.cht080;

/**
 * <br>[機  能] チャットの特例アクセスで使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht080Const {
    /** 処理区分 初期表示 */
    public static final int DSP_FIRST = 0;
    /** 処理区分 初期表示完了 */
    public static final int DSP_ALREADY = 1;

    /** 処理区分 新規追加 */
    public static final int EDITMODE_ADD = 0;
    /** 処理区分 編集 */
    public static final int EDITMODE_EDIT = 1;

    /** 並び順 昇順 */
    public static final int ORDER_ASC = 0;
    /** 並び順 降順 */
    public static final int ORDER_DESC = 1;

    /** 特例アクセス 1ページ表示件数 30件 */
    public static final int LIMIT_DSP_SPACCESS = 30;

    /** 特例アクセス採番*/
    public static final String SBNSID_SUB_CHT_SPACCESS = "spaccess";

    /** 特例アクセス種別 ユーザ */
    public static final int SP_TYPE_USER = 1;
    /** 特例アクセス 種別 グループ */
    public static final int SP_TYPE_GROUP = 2;
    /** 特例アクセス 種別 役職 */
    public static final int SP_TYPE_POSITION = 3;
}