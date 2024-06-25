package jp.groupsession.v2.cir.cir190;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir100.Cir100ParamModel;


/**
 * <br>[機  能] 回覧板 アカウント基本設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir190ParamModel extends Cir100ParamModel {

    /** 初期表示区分 */
    private int cir190initFlg__ = GSConstCircular.DSP_FIRST;
    /** アカウントの作成区分 */
    private int cir190acntMakeKbn__ = GSConstCircular.KANRI_USER_NO;
    /** 自動削除区分 */
    private int cir190autoDelKbn__ = GSConstCircular.AUTO_DEL_ADM;
    /** 使用者 */
    private int cir190acntUser__ = GSConstCircular.CIN_ACNT_USER_NO;
    /**
     * <p>cir190initFlg を取得します。
     * @return cir190initFlg
     */
    public int getCir190initFlg() {
        return cir190initFlg__;
    }
    /**
     * <p>cir190initFlg をセットします。
     * @param cir190initFlg cir190initFlg
     */
    public void setCir190initFlg(int cir190initFlg) {
        cir190initFlg__ = cir190initFlg;
    }
    /**
     * <p>cir190acntMakeKbn を取得します。
     * @return cir190acntMakeKbn
     */
    public int getCir190acntMakeKbn() {
        return cir190acntMakeKbn__;
    }
    /**
     * <p>cir190acntMakeKbn をセットします。
     * @param cir190acntMakeKbn cir190acntMakeKbn
     */
    public void setCir190acntMakeKbn(int cir190acntMakeKbn) {
        cir190acntMakeKbn__ = cir190acntMakeKbn;
    }
    /**
     * <p>cir190autoDelKbn を取得します。
     * @return cir190autoDelKbn
     */
    public int getCir190autoDelKbn() {
        return cir190autoDelKbn__;
    }
    /**
     * <p>cir190autoDelKbn をセットします。
     * @param cir190autoDelKbn cir190autoDelKbn
     */
    public void setCir190autoDelKbn(int cir190autoDelKbn) {
        cir190autoDelKbn__ = cir190autoDelKbn;
    }
    /**
     * <p>cir190acntUser を取得します。
     * @return cir190acntUser
     */
    public int getCir190acntUser() {
        return cir190acntUser__;
    }
    /**
     * <p>cir190acntUser をセットします。
     * @param cir190acntUser cir190acntUser
     */
    public void setCir190acntUser(int cir190acntUser) {
        cir190acntUser__ = cir190acntUser;
    }
}