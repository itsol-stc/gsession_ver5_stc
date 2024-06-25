package jp.groupsession.v2.sch.sch240kn;

import jp.groupsession.v2.sch.sch240.Sch240Form;

/**
 * <br>[機  能] スケジュール 特例アクセス登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch240knForm extends Sch240Form {
    /** 備考(表示用) */
    private String sch240knBiko__ = null;
    /** 役職名(表示用) */
    private String sch240knpositionName__ = null;
    /**
     * <p>sch240knBiko を取得します。
     * @return sch240knBiko
     */
    public String getSch240knBiko() {
        return sch240knBiko__;
    }
    /**
     * <p>sch240knBiko をセットします。
     * @param sch240knBiko sch240knBiko
     */
    public void setSch240knBiko(String sch240knBiko) {
        sch240knBiko__ = sch240knBiko;
    }
    /**
     * <p>sch240knpositionName を取得します。
     * @return sch240knpositionName
     */
    public String getSch240knpositionName() {
        return sch240knpositionName__;
    }
    /**
     * <p>sch240knpositionName をセットします。
     * @param sch240knpositionName sch240knpositionName
     */
    public void setSch240knpositionName(String sch240knpositionName) {
        sch240knpositionName__ = sch240knpositionName;
    }
}
