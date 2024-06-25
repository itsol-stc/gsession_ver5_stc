package jp.groupsession.v2.sch.sch097;

import jp.groupsession.v2.sch.sch100.Sch100ParamModel;

/**
 * <br>[機  能] スケジュール 個人設定 重複登録設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch097ParamModel extends Sch100ParamModel {

    /** 重複登録 */
    private String sch097RepeatKbn__ = null;
    /** 自身による登録を許可 */
    private String sch097RepeatMyKbn__ = null;

    /**
     * <p>sch097RepeatKbn を取得します。
     * @return sch097RepeatKbn
     */
    public String getSch097RepeatKbn() {
        return sch097RepeatKbn__;
    }

    /**
     * <p>sch097RepeatKbn をセットします。
     * @param sch097RepeatKbn sch097RepeatKbn
     */
    public void setSch097RepeatKbn(String sch097RepeatKbn) {
        sch097RepeatKbn__ = sch097RepeatKbn;
    }

    /**
     * <p>sch097RepeatMyKbn を取得します。
     * @return sch097RepeatMyKbn
     */
    public String getSch097RepeatMyKbn() {
        return sch097RepeatMyKbn__;
    }

    /**
     * <p>sch097RepeatMyKbn をセットします。
     * @param sch097RepeatMyKbn sch097RepeatMyKbn
     */
    public void setSch097RepeatMyKbn(String sch097RepeatMyKbn) {
        sch097RepeatMyKbn__ = sch097RepeatMyKbn;
    }


}
