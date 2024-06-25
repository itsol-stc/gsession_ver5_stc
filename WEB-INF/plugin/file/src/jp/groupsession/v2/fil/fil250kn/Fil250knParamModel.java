package jp.groupsession.v2.fil.fil250kn;

import java.util.ArrayList;

import jp.groupsession.v2.fil.fil250.Fil250ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] 管理者設定 更新通知一括設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil250knParamModel extends Fil250ParamModel {
    /** 更新通知対象者 ユーザリスト */
    private ArrayList<UsrLabelValueBean> fil250knCallUserList__ = null;
    /**
     * <p>fil250knCallUserList を取得します。
     * @return fil250knCallUserList
     * @see jp.groupsession.v2.fil.fil250kn.Fil250knForm#fil250knCallUserList__
     */
    public ArrayList<UsrLabelValueBean> getFil250knCallUserList() {
        return fil250knCallUserList__;
    }
    /**
     * <p>fil250knCallUserList をセットします。
     * @param fil250knCallUserList fil250knCallUserList
     * @see jp.groupsession.v2.fil.fil250kn.Fil250knForm#fil250knCallUserList__
     */
    public void setFil250knCallUserList(
            ArrayList<UsrLabelValueBean> fil250knCallUserList) {
        fil250knCallUserList__ = fil250knCallUserList;
    }
}
