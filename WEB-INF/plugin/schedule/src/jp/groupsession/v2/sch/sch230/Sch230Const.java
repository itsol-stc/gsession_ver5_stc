package jp.groupsession.v2.sch.sch230;

/**
 * <br>[機  能] WEBメールプラグインで使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch230Const {
    /** 処理区分 初期表示 */
    public static final int DSP_FIRST = 0;
    /** 処理区分 初期表示完了 */
    public static final int DSP_ALREADY = 1;

    /** 処理区分 新規追加 */
    public static final int EDITMODE_ADD = 0;
    /** 処理区分 編集 */
    public static final int EDITMODE_EDIT = 1;

    /** ソートキー アカウント名 */
    public static final int SKEY_ACCOUNTNAME = 0;
    /** ソートキー メールアドレス */
    public static final int SKEY_MAIL = 1;
    /** ソートキー 使用者 */
    public static final int SKEY_USER = 2;
    /** ソートキー ディスク使用量 */
    public static final int SKEY_DISKSIZE = 3;
    /** ソートキー 受信日時 */
    public static final int SKEY_RECEIVEDATE = 4;

    /** 並び順 昇順 */
    public static final int ORDER_ASC = 0;
    /** 並び順 降順 */
    public static final int ORDER_DESC = 1;

    /** 特例アクセス 1ページ表示件数 30件 */
    public static final int LIMIT_DSP_SPACCESS = 30;
}