package jp.groupsession.v2.cir.cir130;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir070.Cir070Form;

/**
 * <br>[機  能] 回覧板 個人設定 初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir130Form extends Cir070Form {

    /** 設定アカウント区分 */
    private int cir130SelKbn__ = GSConstCircular.ACCOUNT_SEL;
    /** 手動削除設定のアカウントSID */
    private int cir130AccountSid__;
    /** メ手動削除設定のアカウント名 */
    private String cir130AccountName__;

    /** 初期表示フラグ */
    private int cir130init__ = 0;
    /** メモ欄修正区分 */
    private int cir130memoKbn__ = 0;
    /** メモ欄修正期限(期間指定) */
    private int cir130memoPeriod__ = 0;
    /**回覧先 公開／非公開 */
    private int cir130show__ = 0;


    /**
     * <p>cir130init を取得します。
     * @return cir130init
     */
    public int getCir130init() {
        return cir130init__;
    }
    /**
     * <p>cir130init をセットします。
     * @param cir130init cir130init
     */
    public void setCir130init(int cir130init) {
        cir130init__ = cir130init;
    }
    /**
     * <p>cir130memoKbn を取得します。
     * @return cir130memoKbn
     */
    public int getCir130memoKbn() {
        return cir130memoKbn__;
    }
    /**
     * <p>cir130memoKbn をセットします。
     * @param cir130memoKbn cir130memoKbn
     */
    public void setCir130memoKbn(int cir130memoKbn) {
        cir130memoKbn__ = cir130memoKbn;
    }
    /**
     * <p>cir130memoPeriod を取得します。
     * @return cir130memoPeriod
     */
    public int getCir130memoPeriod() {
        return cir130memoPeriod__;
    }
    /**
     * <p>cir130memoPeriod をセットします。
     * @param cir130memoPeriod cir130memoPeriod
     */
    public void setCir130memoPeriod(int cir130memoPeriod) {
        cir130memoPeriod__ = cir130memoPeriod;
    }

    /**
     * <p>cir130show を取得します。
     * @return cir130show
     */
    public int getCir130show() {
        return cir130show__;
    }
    /**
     * <p>cir0130show をセットします。
     * @param cir130show cir130show
     */
    public void setCir130show(int cir130show) {
        cir130show__ = cir130show;
    }
    /**
     * <p>cir130SelKbn を取得します。
     * @return cir130SelKbn
     */
    public int getCir130SelKbn() {
        return cir130SelKbn__;
    }
    /**
     * <p>cir130SelKbn をセットします。
     * @param cir130SelKbn cir130SelKbn
     */
    public void setCir130SelKbn(int cir130SelKbn) {
        cir130SelKbn__ = cir130SelKbn;
    }
    /**
     * <p>cir130AccountSid を取得します。
     * @return cir130AccountSid
     */
    public int getCir130AccountSid() {
        return cir130AccountSid__;
    }
    /**
     * <p>cir130AccountSid をセットします。
     * @param cir130AccountSid cir130AccountSid
     */
    public void setCir130AccountSid(int cir130AccountSid) {
        cir130AccountSid__ = cir130AccountSid;
    }
    /**
     * <p>cir130AccountName を取得します。
     * @return cir130AccountName
     */
    public String getCir130AccountName() {
        return cir130AccountName__;
    }
    /**
     * <p>cir130AccountName をセットします。
     * @param cir130AccountName cir130AccountName
     */
    public void setCir130AccountName(String cir130AccountName) {
        cir130AccountName__ = cir130AccountName;
    }
}
