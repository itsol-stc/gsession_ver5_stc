package jp.groupsession.v2.enq.enq110;

/**
 * <br>[機  能] アンケート回答画面で使用する定数一覧
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq110Const {

    // アンケート回答チェックに使用するフラグ
    /** 未回答＆回答期限内 */
    public static final int ANS_KBN_UNANS_CURRENT = 0;
    /** 未回答＆回答期限外 */
    public static final int ANS_KBN_UNANS_NOT_CURRENT = 1;
    /** 回答済＆回答期限内 */
    public static final int ANS_KBN_ANSED_CURRENT = 10;
    /** 回答済＆回答期限外 */
    public static final int ANS_KBN_ANSED_NOT_CURRENT = 11;
    /** 回答権限無し */
    public static final int ANS_KBN_WITHOUT_AUTHORITY = -9;

    // 画面モード
    /** アンケート回答モード */
    public static final int DSP_MODE_ANSWER = 0;
    /** プレビューモード */
    public static final int DSP_MODE_PREVIEW = 1;

    /** アンケート設問確認結果 通常 */
    public static final int ENQ_QUE_OK = 0;
    /** アンケート設問確認結果 回答中に変更 */
    public static final int ENQ_QUE_CHANGED = 1;
    /** アンケート設問確認結果 回答中に削除 */
    public static final int ENQ_QUE_NODATA = 2;

    /** 発信者区分 ユーザ */
    public static final int SENDER_KBN_USER = 0;
    /** 発信者区分 グループ */
    public static final int SENDER_KBN_GROUP = 1;
}
