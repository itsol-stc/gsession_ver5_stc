package jp.groupsession.v2.rsv;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 予約情報(1件)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvYoyakuModel extends AbstractModel {

    /** 予約SID */
    private int rsySid__ = -1;
    /** 利用日 */
    private String yrkRiyoDateStr__ = null;
    /** 利用開始日 */
    private UDate rsyFrDate__ = null;
    /** 利用終了日 */
    private UDate rsyToDate__ = null;
    /** 利用目的 */
    private String rsyMok__ = null;
    /** 登録者 */
    private String yrkName__ = null;
    /** 内容 */
    private String rsyNaiyo__ = null;
    /** 承認状況 */
    private int rsyApprStatus__ = GSConstReserve.RSY_APPR_STATUS_NORMAL;
    /** 承認区分 */
    private int rsyApprKbn__ = GSConstReserve.RSY_APPR_KBN_NOSET;
    /** 削除ユーザ区分*/
    private int usrJKbn__;
    /** 登録ユーザ有効区分 */
    private int usrUkoFlg__;
    /** 施設予約公開状況 */
    private int public__ = GSConstReserve.PUBLIC_KBN_ALL;

    /**
     * <p>rsyMok__ を取得します。
     * @return rsyMok
     */
    public String getRsyMok() {
        return rsyMok__;
    }
    /**
     * <p>rsyMok__ をセットします。
     * @param rsyMok rsyMok__
     */
    public void setRsyMok(String rsyMok) {
        rsyMok__ = rsyMok;
    }
    /**
     * <p>rsySid__ を取得します。
     * @return rsySid
     */
    public int getRsySid() {
        return rsySid__;
    }
    /**
     * <p>rsySid__ をセットします。
     * @param rsySid rsySid__
     */
    public void setRsySid(int rsySid) {
        rsySid__ = rsySid;
    }
    /**
     * <p>yrkName__ を取得します。
     * @return yrkName
     */
    public String getYrkName() {
        return yrkName__;
    }
    /**
     * <p>yrkName__ をセットします。
     * @param yrkName yrkName__
     */
    public void setYrkName(String yrkName) {
        yrkName__ = yrkName;
    }
    /**
     * <p>yrkRiyoDateStr__ を取得します。
     * @return yrkRiyoDateStr
     */
    public String getYrkRiyoDateStr() {
        return yrkRiyoDateStr__;
    }
    /**
     * <p>yrkRiyoDateStr__ をセットします。
     * @param yrkRiyoDateStr yrkRiyoDateStr__
     */
    public void setYrkRiyoDateStr(String yrkRiyoDateStr) {
        yrkRiyoDateStr__ = yrkRiyoDateStr;
    }
    /**
     * <p>rsyFrDate__ を取得します。
     * @return rsyFrDate
     */
    public UDate getRsyFrDate() {
        return rsyFrDate__;
    }
    /**
     * <p>rsyFrDate__ をセットします。
     * @param rsyFrDate rsyFrDate__
     */
    public void setRsyFrDate(UDate rsyFrDate) {
        rsyFrDate__ = rsyFrDate;
    }
    /**
     * <p>rsyToDate__ を取得します。
     * @return rsyToDate
     */
    public UDate getRsyToDate() {
        return rsyToDate__;
    }
    /**
     * <p>rsyToDate__ をセットします。
     * @param rsyToDate rsyToDate__
     */
    public void setRsyToDate(UDate rsyToDate) {
        rsyToDate__ = rsyToDate;
    }
    /**
     * <p>rsyNaiyo を取得します。
     * @return rsyNaiyo
     */
    public String getRsyNaiyo() {
        return rsyNaiyo__;
    }
    /**
     * <p>rsyNaiyo をセットします。
     * @param rsyNaiyo rsyNaiyo
     */
    public void setRsyNaiyo(String rsyNaiyo) {
        rsyNaiyo__ = rsyNaiyo;
    }
    /**
     * <p>rsyApprStatus を取得します。
     * @return rsyApprStatus
     */
    public int getRsyApprStatus() {
        return rsyApprStatus__;
    }
    /**
     * <p>rsyApprStatus をセットします。
     * @param rsyApprStatus rsyApprStatus
     */
    public void setRsyApprStatus(int rsyApprStatus) {
        rsyApprStatus__ = rsyApprStatus;
    }
    /**
     * <p>rsyApprKbn を取得します。
     * @return rsyApprKbn
     */
    public int getRsyApprKbn() {
        return rsyApprKbn__;
    }
    /**
     * <p>rsyApprKbn をセットします。
     * @param rsyApprKbn rsyApprKbn
     */
    public void setRsyApprKbn(int rsyApprKbn) {
        rsyApprKbn__ = rsyApprKbn;
    }
    /**
     * <br>[機  能] 承認待ちの施設予約情報かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true:承認待ち false:通常(承認済み)
     */
    public boolean isNotAppr() {
        return rsyApprStatus__ == GSConstReserve.RSY_APPR_STATUS_NOAPPR;
    }
    /**
     * <br>[機  能] 却下されたの施設予約情報かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return true:却下された予約情報
     */
    public boolean isRejection() {
        return rsyApprKbn__ == GSConstReserve.RSY_APPR_KBN_REJECTION;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
    /**
     * <p>usrJKbn を取得します。
     * @return usrJKbn
     */
    public int getUsrJKbn() {
        return usrJKbn__;
    }
    /**
     * <p>usrJKbn をセットします。
     * @param usrJKbn usrJKbn
     */
    public void setUsrJKbn(int usrJKbn) {
        usrJKbn__ = usrJKbn;
    }
    /**
     * <p> publicを取得します。
     * @return public
     * */
    public int getPublic() {
        return public__;
    }
    /**
     * <p> publicをセットします。
     * @param public1 public1
     * */
    public void setPublic(int public1) {
        public__ = public1;
    }
}