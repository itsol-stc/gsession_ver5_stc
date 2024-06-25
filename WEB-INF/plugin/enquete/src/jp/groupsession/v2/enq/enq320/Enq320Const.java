package jp.groupsession.v2.enq.enq320;

/**
 * <br>[機  能] アンケート 結果確認（一覧）画面で使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq320Const {

    /** ソートキー 状態 */
    public static final int SORTKEY_JOUTAI = 0;
    /** ソートキー グループ */
    public static final int SORTKEY_GROUP = 1;
    /** ソートキー ユーザ */
    public static final int SORTKEY_USER = 2;
    /** ソートキー 回答日 */
    public static final int SORTKEY_ANSDATE = 3;

    /** 並び順 昇順 */
    public static final int ORDER_ASC = 0;
    /** 並び順 降順 */
    public static final int ORDER_DESC = 1;

    /** 初期表示時 全体 */
    public static final int INIT_DSP_ALL = 0;
    /** 初期表示時 回答 */
    public static final int INIT_DSP_ANS = 1;
    /** 初期表示時 未回答 */
    public static final int INIT_DSP_NON = 2;
}
