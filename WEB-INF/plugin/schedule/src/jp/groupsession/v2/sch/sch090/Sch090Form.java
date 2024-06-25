package jp.groupsession.v2.sch.sch090;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.sch100.Sch100Form;

/**
 * <br>[機  能] スケジュール 個人設定メニュー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch090Form extends Sch100Form {
    /** 重複登録設定可能か */
    private boolean sch090canEditRepertKbn__ = false;
    
    /** 個人設定 グループメンバー表示フラグ */
    private int sch090dspEditGroupUser__ = GSConstSchedule.SCC_USER_SHOW;

    /**
     * <p>sch090canEditRepertKbn を取得します。
     * @return sch090canEditRepertKbn
     */
    public boolean isSch090canEditRepertKbn() {
        return sch090canEditRepertKbn__;
    }

    /**
     * <p>sch090canEditRepertKbn をセットします。
     * @param sch090canEditRepertKbn sch090canEditRepertKbn
     */
    public void setSch090canEditRepertKbn(boolean sch090canEditRepertKbn) {
        sch090canEditRepertKbn__ = sch090canEditRepertKbn;
    }

    /**
     * <p>sch090dspEditGroupUser を取得します。
     * @return sch090dspEditGroupUser
     */
    public int getSch090dspEditGroupUser() {
        return sch090dspEditGroupUser__;
    }

    /**
     * <p>sch090dspEditGroupUser をセットします。
     * @param sch090dspEditGroupUser sch090dspEditGroupUser
     */
    public void setSch090dspEditGroupUser(int sch090dspEditGroupUser) {
        sch090dspEditGroupUser__ = sch090dspEditGroupUser;
    }
}
