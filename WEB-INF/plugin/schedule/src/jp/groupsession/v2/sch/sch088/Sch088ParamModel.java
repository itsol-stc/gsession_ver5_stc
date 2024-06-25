package jp.groupsession.v2.sch.sch088;

import jp.groupsession.v2.sch.sch100.Sch100ParamModel;

/**
 * <br>[機  能] スケジュール ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch088ParamModel extends Sch100ParamModel {

    /** ショートメール通知区分 */
    private String sch088smlSendKbn__;
    /** ショートメール通知設定 */
    private String sch088Smail__;
    /** グループスケジュールショートメール通知設定 */
    private String sch088SmailGroup__;
    /** 出欠確認時ショートメール通知設定 */
    private String sch088SmailAttend__;
    /**
     * <p>sch088Smail を取得します。
     * @return sch088Smail
     */
    public String getSch088Smail() {
        return sch088Smail__;
    }
    /**
     * <p>sch088Smail をセットします。
     * @param sch088Smail sch088Smail
     */
    public void setSch088Smail(String sch088Smail) {
        sch088Smail__ = sch088Smail;
    }
    /**
     * <p>sch088SmailGroup を取得します。
     * @return sch088SmailGroup
     */
    public String getSch088SmailGroup() {
        return sch088SmailGroup__;
    }
    /**
     * <p>sch088SmailGroup をセットします。
     * @param sch088SmailGroup sch088SmailGroup
     */
    public void setSch088SmailGroup(String sch088SmailGroup) {
        sch088SmailGroup__ = sch088SmailGroup;
    }
    /**
     * <p>sch088smlSendKbn を取得します。
     * @return sch088smlSendKbn
     */
    public String getSch088smlSendKbn() {
        return sch088smlSendKbn__;
    }
    /**
     * <p>sch088smlSendKbn をセットします。
     * @param sch088smlSendKbn sch088smlSendKbn
     */
    public void setSch088smlSendKbn(String sch088smlSendKbn) {
        sch088smlSendKbn__ = sch088smlSendKbn;
    }
    /**
     * <p>sch088SmailAttend を取得します。
     * @return sch088SmailAttend
     */
    public String getSch088SmailAttend() {
        return sch088SmailAttend__;
    }
    /**
     * <p>sch088SmailAttend をセットします。
     * @param sch088SmailAttend sch088SmailAttend
     */
    public void setSch088SmailAttend(String sch088SmailAttend) {
        sch088SmailAttend__ = sch088SmailAttend;
    }

}
