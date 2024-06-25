package jp.groupsession.v2.ntp.ntp085;

import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp080.Ntp080ParamModel;

/**
 * <br>[機  能] 日報 ショートメール通知設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp085ParamModel extends Ntp080ParamModel {

    /** 日報通知設定区分 0:管理者 1:個人 */
    private int ntp085NoticeKbn__ = GSConstNippou.SML_NOTICE_ADM;
    /** 日報通知区分 0:通知しない 1:通知する */
    private int ntp085SmlNoticeKbn__ = GSConstNippou.SML_NOTICE_YES;
    /** 日報通知先区分 0:所属グループ全員 1:所属グループの管理者 */
    private int ntp085SmlNoticePlace__ = GSConstNippou.SML_NOTICE_GROUP;
    /** コメント通知設定区分 0:管理者 1:個人 */
    private int ntp085CmtNoticeKbn__ = GSConstNippou.SML_NOTICE_ADM;
    /** コメント通知区分 0:通知しない 1:通知する */
    private int ntp085CmtSmlNoticeKbn__ = GSConstNippou.SML_NOTICE_YES;
    /** いいね通知設定区分 0:管理者 1:個人 */
    private int ntp085GoodNoticeKbn__ = GSConstNippou.SML_NOTICE_ADM;
    /** いいね通知区分 0:通知しない 1:通知する */
    private int ntp085GoodSmlNoticeKbn__ = GSConstNippou.SML_NOTICE_YES;

    /**
     * <p>ntp085NoticeKbn を取得します。
     * @return ntp085NoticeKbn
     */
    public int getNtp085NoticeKbn() {
        return ntp085NoticeKbn__;
    }
    /**
     * <p>ntp085NoticeKbn をセットします。
     * @param ntp085NoticeKbn ntp085NoticeKbn
     */
    public void setNtp085NoticeKbn(int ntp085NoticeKbn) {
        ntp085NoticeKbn__ = ntp085NoticeKbn;
    }
    /**
     * <p>ntp085SmlNoticeKbn を取得します。
     * @return ntp085SmlNoticeKbn
     */
    public int getNtp085SmlNoticeKbn() {
        return ntp085SmlNoticeKbn__;
    }
    /**
     * <p>ntp085SmlNoticeKbn をセットします。
     * @param ntp085SmlNoticeKbn ntp085SmlNoticeKbn
     */
    public void setNtp085SmlNoticeKbn(int ntp085SmlNoticeKbn) {
        ntp085SmlNoticeKbn__ = ntp085SmlNoticeKbn;
    }
    /**
     * <p>ntp085SmlNoticePlace を取得します。
     * @return ntp085SmlNoticePlace
     */
    public int getNtp085SmlNoticePlace() {
        return ntp085SmlNoticePlace__;
    }
    /**
     * <p>ntp085SmlNoticePlace をセットします。
     * @param ntp085SmlNoticePlace ntp085SmlNoticePlace
     */
    public void setNtp085SmlNoticePlace(int ntp085SmlNoticePlace) {
        ntp085SmlNoticePlace__ = ntp085SmlNoticePlace;
    }
    /**
     * <p>ntp085CmtNoticeKbn を取得します。
     * @return ntp085CmtNoticeKbn
     */
    public int getNtp085CmtNoticeKbn() {
        return ntp085CmtNoticeKbn__;
    }
    /**
     * <p>ntp085CmtNoticeKbn をセットします。
     * @param ntp085CmtNoticeKbn ntp085CmtNoticeKbn
     */
    public void setNtp085CmtNoticeKbn(int ntp085CmtNoticeKbn) {
        ntp085CmtNoticeKbn__ = ntp085CmtNoticeKbn;
    }
    /**
     * <p>ntp085CmtSmlNoticeKbn を取得します。
     * @return ntp085CmtSmlNoticeKbn
     */
    public int getNtp085CmtSmlNoticeKbn() {
        return ntp085CmtSmlNoticeKbn__;
    }
    /**
     * <p>ntp085CmtSmlNoticeKbn をセットします。
     * @param ntp085CmtSmlNoticeKbn ntp085CmtSmlNoticeKbn
     */
    public void setNtp085CmtSmlNoticeKbn(int ntp085CmtSmlNoticeKbn) {
        ntp085CmtSmlNoticeKbn__ = ntp085CmtSmlNoticeKbn;
    }
    /**
     * <p>ntp085GoodNoticeKbn を取得します。
     * @return ntp085GoodNoticeKbn
     */
    public int getNtp085GoodNoticeKbn() {
        return ntp085GoodNoticeKbn__;
    }
    /**
     * <p>ntp085GoodNoticeKbn をセットします。
     * @param ntp085GoodNoticeKbn ntp085GoodNoticeKbn
     */
    public void setNtp085GoodNoticeKbn(int ntp085GoodNoticeKbn) {
        ntp085GoodNoticeKbn__ = ntp085GoodNoticeKbn;
    }
    /**
     * <p>ntp085GoodSmlNoticeKbn を取得します。
     * @return ntp085GoodSmlNoticeKbn
     */
    public int getNtp085GoodSmlNoticeKbn() {
        return ntp085GoodSmlNoticeKbn__;
    }
    /**
     * <p>ntp085GoodSmlNoticeKbn をセットします。
     * @param ntp085GoodSmlNoticeKbn ntp085GoodSmlNoticeKbn
     */
    public void setNtp085GoodSmlNoticeKbn(int ntp085GoodSmlNoticeKbn) {
        ntp085GoodSmlNoticeKbn__ = ntp085GoodSmlNoticeKbn;
    }
}
