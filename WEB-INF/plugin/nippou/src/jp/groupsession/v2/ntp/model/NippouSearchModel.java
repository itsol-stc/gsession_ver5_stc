package jp.groupsession.v2.ntp.model;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 日報情報、ユーザ名を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class NippouSearchModel extends NtpDataModel {

    /** 日報ユーザ　姓*/
    private String nipUsrSei__;
    /** 日報ユーザ　名*/
    private String nipUsrMei__;
    /** 日報登録者　姓*/
    private String nipAuidSei__;
    /** 日報登録者　名*/
    private String nipAuidMei__;
    /** ユーザ状態区分 */
    private int nipUsrJkbn__;
    /** 登録者状態区分 */
    private int nipAuidJkbn__;
    /** 登録日時 */
    private UDate nipAdate__;

    /**
     * @return nipAuidJkbn を戻します。
     */
    public int getNipAuidJkbn() {
        return nipAuidJkbn__;
    }
    /**
     * @param nipAuidJkbn 設定する nipAuidJkbn。
     */
    public void setNipAuidJkbn(int nipAuidJkbn) {
        nipAuidJkbn__ = nipAuidJkbn;
    }
    /**
     * @return nipUsrJkbn を戻します。
     */
    public int getNipUsrJkbn() {
        return nipUsrJkbn__;
    }
    /**
     * @param nipUsrJkbn 設定する nipUsrJkbn。
     */
    public void setNipUsrJkbn(int nipUsrJkbn) {
        nipUsrJkbn__ = nipUsrJkbn;
    }
    /**
     * @return nipAuidMei を戻します。
     */
    public String getNipAuidMei() {
        return nipAuidMei__;
    }
    /**
     * @param nipAuidMei 設定する nipAuidMei。
     */
    public void setNipAuidMei(String nipAuidMei) {
        nipAuidMei__ = nipAuidMei;
    }
    /**
     * @return nipAuidSei を戻します。
     */
    public String getNipAuidSei() {
        return nipAuidSei__;
    }
    /**
     * @param nipAuidSei 設定する nipAuidSei。
     */
    public void setNipAuidSei(String nipAuidSei) {
        nipAuidSei__ = nipAuidSei;
    }
    /**
     * @return nipUsrMei を戻します。
     */
    public String getNipUsrMei() {
        return nipUsrMei__;
    }
    /**
     * @param nipUsrMei 設定する nipUsrMei。
     */
    public void setNipUsrMei(String nipUsrMei) {
        nipUsrMei__ = nipUsrMei;
    }
    /**
     * @return nipUsrSei を戻します。
     */
    public String getNipUsrSei() {
        return nipUsrSei__;
    }
    /**
     * @param nipUsrSei 設定する nipUsrSei。
     */
    public void setNipUsrSei(String nipUsrSei) {
        nipUsrSei__ = nipUsrSei;
    }
    /**
     * <p>nipAdate を取得します。
     * @return nipAdate
     */
    public UDate getNipAdate() {
        return nipAdate__;
    }
    /**
     * <p>nipAdate をセットします。
     * @param nipAdate nipAdate
     */
    public void setNipAdate(UDate nipAdate) {
        nipAdate__ = nipAdate;
    }
}