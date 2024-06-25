package jp.groupsession.v2.cir.cir140;

import jp.groupsession.v2.cir.cir100.Cir100ParamModel;

/**
 * <br>[機  能] 回覧板 管理者設定 初期値設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir140ParamModel extends Cir100ParamModel {
    /** 修正権限区分  管理者/ユーザ */
    private int cir140KenKbn__ = 0;
    /** メモ欄修正区分 */
    private int cir140memoKbn__ = 0;
    /** メモ欄修正期限(期間指定) */
    private int cir140memoPeriod__ = 0;
    /**回覧先 公開／非公開 */
    private int cir140show__ = 0;

    /**
     * <p>cir140KenKbn を取得します。
     * @return cir140KenKbn
     */
    public int getCir140KenKbn() {
        return cir140KenKbn__;
    }
    /**
     * <p>cir140KenKbn をセットします。
     * @param cir140KenKbn cir140KenKbn
     */
    public void setCir140KenKbn(int cir140KenKbn) {
        cir140KenKbn__ = cir140KenKbn;
    }
    /**
     * <p>cir140memoKbn を取得します。
     * @return cir140memoKbn
     */
    public int getCir140memoKbn() {
        return cir140memoKbn__;
    }
    /**
     * <p>cir140memoKbn をセットします。
     * @param cir140memoKbn cir140memoKbn
     */
    public void setCir140memoKbn(int cir140memoKbn) {
        cir140memoKbn__ = cir140memoKbn;
    }
    /**
     * <p>cir140memoPeriod を取得します。
     * @return cir140memoPeriod
     */
    public int getCir140memoPeriod() {
        return cir140memoPeriod__;
    }
    /**
     * <p>cir140memoPeriod をセットします。
     * @param cir140memoPeriod cir140memoPeriod
     */
    public void setCir140memoPeriod(int cir140memoPeriod) {
        cir140memoPeriod__ = cir140memoPeriod;
    }
    /**
     * <p>cir140show を取得します。
     * @return cir140show
     */
    public int getCir140show() {
        return cir140show__;
    }
    /**
     * <p>cir0140show をセットします。
     * @param cir140show cir140show
     */
    public void setCir140show(int cir140show) {
        cir140show__ = cir140show;
    }
}