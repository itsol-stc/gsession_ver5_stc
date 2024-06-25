package jp.groupsession.v2.cir.cir090kn;

import java.util.List;

import jp.groupsession.v2.cir.cir090.Cir090Form;
import jp.groupsession.v2.cir.cir150.Cir150AccountModel;

/**
 * <br>[機  能] 回覧板 個人設定 手動削除確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir090knForm extends Cir090Form {

    /** アカウント名リスト */
    private List<Cir150AccountModel> cir090knAccountList__ = null;

    /**
     * <p>cir090knAccountList を取得します。
     * @return cir090knAccountList
     */
    public List<Cir150AccountModel> getCir090knAccountList() {
        return cir090knAccountList__;
    }

    /**
     * <p>cir090knAccountList をセットします。
     * @param cir090knAccountList cir090knAccountList
     */
    public void setCir090knAccountList(List<Cir150AccountModel> cir090knAccountList) {
        cir090knAccountList__ = cir090knAccountList;
    }
}