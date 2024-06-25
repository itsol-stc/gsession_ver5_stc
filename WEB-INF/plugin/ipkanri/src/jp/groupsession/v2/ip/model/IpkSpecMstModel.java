package jp.groupsession.v2.ip.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] IP管理 スペック情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IpkSpecMstModel extends AbstractModel {

    /** 名前 */
    private String ipk100name__;
    /** 表示順 */
    private int ipk100level__;
    /** 備考 */
    private String ipk100biko__;
    /** スペックSID */
    private int ismSid__;
    /** スペック区分 */
    private int specKbn__;
    /** ユーザSID */
    private int usrSid__;
    /** 登録・更新時刻 */
    private UDate now__;

    /**
     * <p>ipk100biko を取得します。
     * @return ipk100biko
     */
    public String getIpk100biko() {
        return ipk100biko__;
    }
    /**
     * <p>ipk100biko をセットします。
     * @param ipk100biko ipk100biko
     */
    public void setIpk100biko(String ipk100biko) {
        ipk100biko__ = ipk100biko;
    }
    /**
     * <p>ipk100level を取得します。
     * @return ipk100level
     */
    public int getIpk100level() {
        return ipk100level__;
    }
    /**
     * <p>ipk100level をセットします。
     * @param ipk100level ipk100level
     */
    public void setIpk100level(int ipk100level) {
        ipk100level__ = ipk100level;
    }
    /**
     * <p>ipk100name を取得します。
     * @return ipk100name
     */
    public String getIpk100name() {
        return ipk100name__;
    }
    /**
     * <p>ipk100name をセットします。
     * @param ipk100name ipk100name
     */
    public void setIpk100name(String ipk100name) {
        ipk100name__ = ipk100name;
    }
    /**
     * <p>ismSid を取得します。
     * @return ismSid
     */
    public int getIsmSid() {
        return ismSid__;
    }
    /**
     * <p>ismSid をセットします。
     * @param ismSid ismSid
     */
    public void setIsmSid(int ismSid) {
        ismSid__ = ismSid;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>now を取得します。
     * @return now
     */
    public UDate getNow() {
        return now__;
    }
    /**
     * <p>now をセットします。
     * @param now now
     */
    public void setNow(UDate now) {
        now__ = now;
    }
    /**
     * <p>specKbn を取得します。
     * @return specKbn
     */
    public int getSpecKbn() {
        return specKbn__;
    }
    /**
     * <p>specKbn をセットします。
     * @param specKbn specKbn
     */
    public void setSpecKbn(int specKbn) {
        specKbn__ = specKbn;
    }
}