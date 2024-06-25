package jp.groupsession.v2.cir.cir120kn;

import java.util.List;

import jp.groupsession.v2.cir.cir120.Cir120Form;
import jp.groupsession.v2.cir.cir150.Cir150AccountModel;

/**
 * <br>[機  能] 回覧板 管理者設定 手動削除確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir120knForm extends Cir120Form {
    /** アカウント名リスト */
    private List<Cir150AccountModel> cir120knAccountList__ = null;

    /**
     * <p>cir120knAccountList を取得します。
     * @return cir120knAccountList
     */
    public List<Cir150AccountModel> getCir120knAccountList() {
        return cir120knAccountList__;
    }

    /**
     * <p>cir120knAccountList をセットします。
     * @param cir120knAccountList cir120knAccountList
     */
    public void setCir120knAccountList(List<Cir150AccountModel> cir120knAccountList) {
        cir120knAccountList__ = cir120knAccountList;
    }
}