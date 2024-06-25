package jp.groupsession.v2.cir.cir100;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir010.Cir010ParamModel;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] 回覧板 管理者設定メニュー画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir100ParamModel extends Cir010ParamModel {
    /** 自動削除区分 */
    private int cir100autoDelKbn__ = GSConstCircular.AUTO_DEL_ADM;
    /** ショートメール使用可否*/
    private int canSmlUse__ = GSConst.PLUGIN_NOT_USE;


    /**
     * <p>cir100autoDelKbn を取得します。
     * @return cir100autoDelKbn
     */
    public int getCir100autoDelKbn() {
        return cir100autoDelKbn__;
    }

    /**
     * <p>cir100autoDelKbn をセットします。
     * @param cir100autoDelKbn cir100autoDelKbn
     */
    public void setCir100autoDelKbn(int cir100autoDelKbn) {
        cir100autoDelKbn__ = cir100autoDelKbn;
    }

    /**
     * <p>canSmlUse を取得します。
     * @return canSmlUse
     * @see jp.groupsession.v2.cir.cir100.Cir100ParamModel#canSmlUse__
     */
    public int getCanSmlUse() {
        return canSmlUse__;
    }

    /**
     * <p>canSmlUse をセットします。
     * @param canSmlUse canSmlUse
     * @see jp.groupsession.v2.cir.cir100.Cir100ParamModel#canSmlUse__
     */
    public void setCanSmlUse(int canSmlUse) {
        canSmlUse__ = canSmlUse;
    }
}