package jp.groupsession.v2.rsv.rsv110;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstReserve;

/**
 * <br>[機  能] 施設予約 施設予約登録画面の施設情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv110SisetuModel extends Rsv110SisetuKbnModel {

    /** 施設SID */
    private int rsdSid__ = -1;
    /** 利用目的 */
    private String rsyMok__ = null;
    /** 予約開始 */
    private UDate rsyFrDate__ = null;
    /** 予約終了 */
    private UDate rsyToDate__ = null;
    /** 備考 */
    private String rsyBiko__ = null;
    /** 氏名 姓 */
    private String usiSei__ = null;
    /** 氏名 名 */
    private String usiMei__ = null;
    /** 登録者 状態区分 */
    private int usrJkbn__ = 0;
    /** 登録者 有効フラグ */
    private int usrUkoFlg__ = 0;
    /** 公開区分 */
    private int rsyPublic__ = 0;
    /** 編集権限 */
    private int rsyEdit__;
    /** 施設予約拡張SID */
    private int rsrRsid__ = -1;
    /** 登録者 */
    private int rsyAuid__ = -1;
    /** 更新者 */
    private int rsyEuid__ = -1;
    /** 登録日時 */
    private UDate rsyAdate__ = null;
    /** 編集日時 */
    private UDate rsyEdate__ = null;
    /** スケジュールリレーションSID */
    private int scdRsSid__ = -1;
    /** 承認状況 */
    private int rsyApprStatus__ = GSConstReserve.RSY_APPR_STATUS_NORMAL;
    /** 承認区分 */
    private int rsyApprKbn__ = GSConstReserve.RSY_APPR_KBN_NOSET;

    /**
     * <p>rsyAdate__ を取得します。
     * @return rsyAdate
     */
    public UDate getRsyAdate() {
        return rsyAdate__;
    }
    /**
     * <p>rsyAdate__ をセットします。
     * @param rsyAdate rsyAdate__
     */
    public void setRsyAdate(UDate rsyAdate) {
        rsyAdate__ = rsyAdate;
    }
    /**
     * <p>scdRsSid__ を取得します。
     * @return scdRsSid
     */
    public int getScdRsSid() {
        return scdRsSid__;
    }
    /**
     * <p>scdRsSid__ をセットします。
     * @param scdRsSid scdRsSid__
     */
    public void setScdRsSid(int scdRsSid) {
        scdRsSid__ = scdRsSid;
    }
    /**
     * <p>rsyAuid__ を取得します。
     * @return rsyAuid
     */
    public int getRsyAuid() {
        return rsyAuid__;
    }
    /**
     * <p>rsyAuid をセットします。
     * @param rsyAuid rsyAuid__
     */
    public void setRsyAuid(int rsyAuid) {
        rsyAuid__ = rsyAuid;
    }
    /**
     * <p>rsyEuid__ を取得します。
     * @return rsyEuid
     */
    public int getRsyEuid() {
        return rsyEuid__;
    }
    /**
     * <p>rsyEuid をセットします。
     * @param rsyEuid rsyEuid__
     */
    public void setRsyEuid(int rsyEuid) {
        rsyEuid__ = rsyEuid;
    }
    /**
     * <p>rsrRsid__ を取得します。
     * @return rsrRsid
     */
    public int getRsrRsid() {
        return rsrRsid__;
    }
    /**
     * <p>rsrRsid__ をセットします。
     * @param rsrRsid rsrRsid__
     */
    public void setRsrRsid(int rsrRsid) {
        rsrRsid__ = rsrRsid;
    }
    /**
     * <p>rsyEdit__ を取得します。
     * @return rsyEdit
     */
    public int getRsyEdit() {
        return rsyEdit__;
    }
    /**
     * <p>rsyEdit__ をセットします。
     * @param rsyEdit rsyEdit__
     */
    public void setRsyEdit(int rsyEdit) {
        rsyEdit__ = rsyEdit;
    }
    /**
     * <p>rsdSid__ を取得します。
     * @return rsdSid
     */
    public int getRsdSid() {
        return rsdSid__;
    }
    /**
     * <p>rsdSid__ をセットします。
     * @param rsdSid rsdSid__
     */
    public void setRsdSid(int rsdSid) {
        rsdSid__ = rsdSid;
    }
    /**
     * <p>rsyBiko__ を取得します。
     * @return rsyBiko
     */
    public String getRsyBiko() {
        return rsyBiko__;
    }
    /**
     * <p>rsyBiko__ をセットします。
     * @param rsyBiko rsyBiko__
     */
    public void setRsyBiko(String rsyBiko) {
        rsyBiko__ = rsyBiko;
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
     * <p>usiMei__ を取得します。
     * @return usiMei
     */
    public String getUsiMei() {
        return usiMei__;
    }
    /**
     * <p>usiMei__ をセットします。
     * @param usiMei usiMei__
     */
    public void setUsiMei(String usiMei) {
        usiMei__ = usiMei;
    }
    /**
     * <p>usiSei__ を取得します。
     * @return usiSei
     */
    public String getUsiSei() {
        return usiSei__;
    }
    /**
     * <p>usiSei__ をセットします。
     * @param usiSei usiSei__
     */
    public void setUsiSei(String usiSei) {
        usiSei__ = usiSei;
    }
    /**
     * <p>rsyEdate を取得します。
     * @return rsyEdate
     */
    public UDate getRsyEdate() {
        return rsyEdate__;
    }
    /**
     * <p>rsyEdate をセットします。
     * @param rsyEdate rsyEdate
     */
    public void setRsyEdate(UDate rsyEdate) {
        rsyEdate__ = rsyEdate;
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
     * rsyPublicを取得します。
     * @return rsyPublic
     * */
    public int getRsyPublic() {
        return rsyPublic__;
    }
    /**
     * rsyPublicをセットします。
     * @param rsyPublic rsyPublic
     * */
    public void setRsyPublic(int rsyPublic) {
        rsyPublic__ = rsyPublic;
    }
    /**
     * <p>usrJkbn を取得します。
     * @return usrJkbn
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }
    /**
     * <p>usrJkbn をセットします。
     * @param usrJkbn usrJkbn
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
    }
}