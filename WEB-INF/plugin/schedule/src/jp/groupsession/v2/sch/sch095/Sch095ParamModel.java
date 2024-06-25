package jp.groupsession.v2.sch.sch095;

import jp.groupsession.v2.sch.sch100.Sch100ParamModel;

/**
 * <br>[機  能] スケジュール ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch095ParamModel extends Sch100ParamModel {

    /** ショートメール通知設定 */
    private String sch095Smail__;
    /** グループスケジュールショートメール通知設定 */
    private String sch095SmailGroup__;
    /** 出欠確認時ショートメール通知設定 */
    private String sch095SmailAttend__;
    /**
     * <p>sch095Smail を取得します。
     * @return sch095Smail
     */
    public String getSch095Smail() {
        return sch095Smail__;
    }
    /**
     * <p>sch095Smail をセットします。
     * @param sch095Smail sch095Smail
     */
    public void setSch095Smail(String sch095Smail) {
        sch095Smail__ = sch095Smail;
    }
    /**
     * <p>sch095SmailGroup を取得します。
     * @return sch095SmailGroup
     */
    public String getSch095SmailGroup() {
        return sch095SmailGroup__;
    }
    /**
     * <p>sch095SmailGroup をセットします。
     * @param sch095SmailGroup sch095SmailGroup
     */
    public void setSch095SmailGroup(String sch095SmailGroup) {
        sch095SmailGroup__ = sch095SmailGroup;
    }
    /**
     * <p>sch095SmailAttend を取得します。
     * @return sch095SmailAttend
     */
    public String getSch095SmailAttend() {
        return sch095SmailAttend__;
    }
    /**
     * <p>sch095SmailAttend をセットします。
     * @param sch095SmailAttend sch095SmailAttend
     */
    public void setSch095SmailAttend(String sch095SmailAttend) {
        sch095SmailAttend__ = sch095SmailAttend;
    }

}
