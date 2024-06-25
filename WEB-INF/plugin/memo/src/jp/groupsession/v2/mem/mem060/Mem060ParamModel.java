package jp.groupsession.v2.mem.mem060;

import jp.groupsession.v2.mem.GSConstMemo;
import jp.groupsession.v2.mem.mem050.Mem050ParamModel;

/**
 * <br>[機  能] メモ帳 個人設定 ラベル登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Mem060ParamModel extends Mem050ParamModel {
    /** ラベル名 */
    private String mem060LabelName__ = null;
    /** 初期表示区分 */
    private int mem060initKbn__ = GSConstMemo.INIT_FLG;

    /**
     * <p>mem060LabelName を取得します。
     * @return mem060LabelName
     */
    public String getMem060LabelName() {
        return mem060LabelName__;
    }
    /**
     * <p>mem060LabelName をセットします。
     * @param mem060LabelName mem060LabelName
     */
    public void setMem060LabelName(String mem060LabelName) {
        mem060LabelName__ = mem060LabelName;
    }
    /**
     * <p>mem060initKbn を取得します。
     * @return mem060initKbn
     */
    public int getMem060initKbn() {
        return mem060initKbn__;
    }
    /**
     * <p>mem060initKbn をセットします。
     * @param mem060initKbn mem060initKbn
     */
    public void setMem060initKbn(int mem060initKbn) {
        mem060initKbn__ = mem060initKbn;
    }
}