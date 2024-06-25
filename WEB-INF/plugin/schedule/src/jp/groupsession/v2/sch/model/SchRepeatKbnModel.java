package jp.groupsession.v2.sch.model;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] スケジュール 重複登録区分情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchRepeatKbnModel extends AbstractModel {
    /** 重複登録区分 */
    private int repeatKbn__ = GSConstSchedule.SCH_REPEAT_KBN_OK;
    /** 自スケジュール重複登録許可区分 */
    private int repeatMyKbn__ = GSConstSchedule.SCH_REPEAT_MY_KBN_NG;
    /**
     * <p>repeatKbn を取得します。
     * @return repeatKbn
     */
    public int getRepeatKbn() {
        return repeatKbn__;
    }
    /**
     * <p>repeatKbn をセットします。
     * @param repeatKbn repeatKbn
     */
    public void setRepeatKbn(int repeatKbn) {
        repeatKbn__ = repeatKbn;
    }
    /**
     * <p>repeatMyKbn を取得します。
     * @return repeatMyKbn
     */
    public int getRepeatMyKbn() {
        return repeatMyKbn__;
    }
    /**
     * <p>repeatMyKbn をセットします。
     * @param repeatMyKbn repeatMyKbn
     */
    public void setRepeatMyKbn(int repeatMyKbn) {
        repeatMyKbn__ = repeatMyKbn;
    }
}