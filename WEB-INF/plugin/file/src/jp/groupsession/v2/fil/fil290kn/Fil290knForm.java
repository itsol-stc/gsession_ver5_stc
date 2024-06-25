package jp.groupsession.v2.fil.fil290kn;

import java.util.ArrayList;

import jp.groupsession.v2.fil.fil290.Fil290Form;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 管理者設定 個人キャビネット使用許可設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil290knForm extends Fil290Form {
    /** キャビネット使用許可 ユーザ・グループリスト(表示用) */
    private ArrayList<UsrLabelValueBean> fil290knCabinetAuthLabel__ = null;
    /**
     * <p>fil290knCabinetAuthLabel を取得します。
     * @return fil290knCabinetAuthLabel
     * @see jp.groupsession.v2.fil.fil290kn.Fil290knForm#fil290knCabinetAuthLabel__
     */
    public ArrayList<UsrLabelValueBean> getFil290knCabinetAuthLabel() {
        return fil290knCabinetAuthLabel__;
    }
    /**
     * <p>fil290knCabinetAuthLabel をセットします。
     * @param fil290knCabinetAuthLabel fil290knCabinetAuthLabel
     * @see jp.groupsession.v2.fil.fil290kn.Fil290knForm#fil290knCabinetAuthLabel__
     */
    public void setFil290knCabinetAuthLabel(
            ArrayList<UsrLabelValueBean> fil290knCabinetAuthLabel) {
        fil290knCabinetAuthLabel__ = fil290knCabinetAuthLabel;
    }
}
