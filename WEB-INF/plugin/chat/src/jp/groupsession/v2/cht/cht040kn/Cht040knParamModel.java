package jp.groupsession.v2.cht.cht040kn;

import jp.groupsession.v2.cht.cht040.Cht040ParamModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;

/**
 *
 * <br>[機  能] チャット 基本設定確認のパラメータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht040knParamModel extends Cht040ParamModel {
    /** 使用制限ユーザ(表示用) */
    private UserGroupSelectModel cht040knTargetUser__ = new UserGroupSelectModel();
    /**
     * <p>cht040knTargetUser を取得します。
     * @return cht040knTargetUser
     * @see jp.groupsession.v2.cht.cht040kn.Cht040knForm#cht040knTargetUser__
     */
    public UserGroupSelectModel getCht040knTargetUser() {
        return cht040knTargetUser__;
    }
    /**
     * <p>cht040knTargetUser をセットします。
     * @param cht040knTargetUser cht040knTargetUser
     * @see jp.groupsession.v2.cht.cht040kn.Cht040knForm#cht040knTargetUser__
     */
    public void setCht040knTargetUser(UserGroupSelectModel cht040knTargetUser) {
        cht040knTargetUser__ = cht040knTargetUser;
    }
}
