package jp.groupsession.v2.prj.prj110;

import jp.groupsession.v2.prj.prj100.Prj100ParamModel;

/**
 * <br>[機  能] プロジェクト管理 管理者設定 登録権限設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj110ParamModel extends Prj100ParamModel {

    //入力項目
    /** プロジェクト登録権限 */
    private int prj110edit__;

    /**
     * <p>prj110edit を取得します。
     * @return prj110edit
     */
    public int getPrj110edit() {
        return prj110edit__;
    }

    /**
     * <p>prj110edit をセットします。
     * @param prj110edit prj110edit
     */
    public void setPrj110edit(int prj110edit) {
        prj110edit__ = prj110edit;
    }

}
