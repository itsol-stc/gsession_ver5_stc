package jp.groupsession.v2.cir.cir180;

import java.util.List;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir070.Cir070ParamModel;

/**
 * <br>[機  能] 回覧板 アカウントの管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir180ParamModel extends Cir070ParamModel {
    /** アカウントリスト */
    private List<Cir180AccountDataDspModel> accountList__ = null;
    /** チェックされている並び順 */
    private String cir180sortAccount__ = null;
    /** 並び替え対象のラベル */
    private String[] cir180sortLabel__ = null;
    /** アカウント作成判定 */
    private int cir180MakeAcntHnt__ = GSConstCircular.ACCOUNT_ADD_OK;

    /**
     * <p>accountList を取得します。
     * @return accountList
     */
    public List<Cir180AccountDataDspModel> getAccountList() {
        return accountList__;
    }

    /**
     * <p>accountList をセットします。
     * @param accountList accountList
     */
    public void setAccountList(List<Cir180AccountDataDspModel> accountList) {
        accountList__ = accountList;
    }

    /**
     * <p>cir180sortAccount を取得します。
     * @return cir180sortAccount
     */
    public String getCir180sortAccount() {
        return cir180sortAccount__;
    }

    /**
     * <p>cir180sortAccount をセットします。
     * @param cir180sortAccount cir180sortAccount
     */
    public void setCir180sortAccount(String cir180sortAccount) {
        cir180sortAccount__ = cir180sortAccount;
    }

    /**
     * <p>cir180sortLabel を取得します。
     * @return cir180sortLabel
     */
    public String[] getCir180sortLabel() {
        return cir180sortLabel__;
    }

    /**
     * <p>cir180sortLabel をセットします。
     * @param cir180sortLabel cir180sortLabel
     */
    public void setCir180sortLabel(String[] cir180sortLabel) {
        cir180sortLabel__ = cir180sortLabel;
    }

    /**
     * @return cir180MakeAcntHnt
     */
    public int getCir180MakeAcntHnt() {
        return cir180MakeAcntHnt__;
    }

    /**
     * @param cir180MakeAcntHnt 設定する cir180MakeAcntHnt
     */
    public void setCir180MakeAcntHnt(int cir180MakeAcntHnt) {
        cir180MakeAcntHnt__ = cir180MakeAcntHnt;
    }


}