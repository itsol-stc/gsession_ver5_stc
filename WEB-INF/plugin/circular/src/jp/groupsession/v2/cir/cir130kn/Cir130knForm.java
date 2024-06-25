package jp.groupsession.v2.cir.cir130kn;

import java.util.List;

import jp.groupsession.v2.cir.cir130.Cir130Form;
import jp.groupsession.v2.cir.cir150.Cir150AccountModel;

/**
 * <br>[機  能] 回覧板 個人設定 初期値設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir130knForm extends Cir130Form {
    /** アカウント名リスト */
    private List<Cir150AccountModel> cir130knAccountList__ = null;

    /**
     * <p>cir130knAccountList を取得します。
     * @return cir130knAccountList
     */
    public List<Cir150AccountModel> getCir130knAccountList() {
        return cir130knAccountList__;
    }

    /**
     * <p>cir130knAccountList をセットします。
     * @param cir130knAccountList cir130knAccountList
     */
    public void setCir130knAccountList(List<Cir150AccountModel> cir130knAccountList) {
        cir130knAccountList__ = cir130knAccountList;
    }
}
