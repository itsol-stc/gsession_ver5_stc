package jp.groupsession.v2.prj.prj110kn;

import jp.groupsession.v2.prj.prj110.Prj110Form;

/**
 * <br>[機  能] プロジェクト管理 管理者設定 登録権限設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Prj110knForm extends Prj110Form {

    //表示項目
    /** プロジェクト登録権限 */
    private String strEdit__;

    /**
     * <p>strEdit を取得します。
     * @return strEdit
     */
    public String getStrEdit() {
        return strEdit__;
    }

    /**
     * <p>strEdit をセットします。
     * @param strEdit strEdit
     */
    public void setStrEdit(String strEdit) {
        strEdit__ = strEdit;
    }
}
