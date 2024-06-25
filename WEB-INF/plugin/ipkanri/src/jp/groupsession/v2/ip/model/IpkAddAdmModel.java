package jp.groupsession.v2.ip.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] IP管理 IPアドレス管理者情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IpkAddAdmModel extends AbstractModel {

    /** IPアドレスSID */
    private int iadSid__;
    /**  採番IPアドレスSID */
    private int newIadSid__;
    /** ユーザSID */
    private int usrSid__;
    /** 登録者ID */
    private int iadAdmAuid__;
    /** 登録日時 */
    private UDate iadAdmAdate__;
    /** 更新ID */
    private int iadAdmEuid__;
    /** 更新日時 */
    private UDate iadAdmEdate__;
    /** ユーザ姓 */
    private String usiSei__;
    /** ユーザ名 */
    private String usiMei__;
    /** 状態区分 */
    private int usrJkbn__;
    /** ログイン停止区分 */
    private int usrUkoFlg__ = 0;

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
    /**
     * <p>usiMei を取得します。
     * @return usiMei
     */
    public String getUsiMei() {
        return usiMei__;
    }
    /**
     * <p>usiMei をセットします。
     * @param usiMei usiMei
     */
    public void setUsiMei(String usiMei) {
        usiMei__ = usiMei;
    }
    /**
     * <p>usiSei を取得します。
     * @return usiSei
     */
    public String getUsiSei() {
        return usiSei__;
    }
    /**
     * <p>usiSei をセットします。
     * @param usiSei usiSei
     */
    public void setUsiSei(String usiSei) {
        usiSei__ = usiSei;
    }
    /**
     * <p>iadAdmAuid を取得します。
     * @return iadAdmAuid
     */
    public int getIadAdmAuid() {
        return iadAdmAuid__;
    }
    /**
     * <p>iadAdmAuid をセットします。
     * @param iadAdmAuid iadAdmAuid
     */
    public void setIadAdmAuid(int iadAdmAuid) {
        iadAdmAuid__ = iadAdmAuid;
    }
    /**
     * <p>iadAdmAdate を取得します。
     * @return iadAdmAdate
     */
    public UDate getIadAdmAdate() {
        return iadAdmAdate__;
    }
    /**
     * <p>iadAdmAdate をセットします。
     * @param iadAdmAdate iadAdmAdate
     */
    public void setIadAdmAdate(UDate iadAdmAdate) {
        iadAdmAdate__ = iadAdmAdate;
    }
    /**
     * <p>iadAdmEdate を取得します。
     * @return iadAdmEdate
     */
    public UDate getIadAdmEdate() {
        return iadAdmEdate__;
    }
    /**
     * <p>iadAdmEdate をセットします。
     * @param iadAdmEdate iadAdmEdate
     */
    public void setIadAdmEdate(UDate iadAdmEdate) {
        iadAdmEdate__ = iadAdmEdate;
    }
    /**
     * <p>iadAdmEuid を取得します。
     * @return iadAdmEuid
     */
    public int getIadAdmEuid() {
        return iadAdmEuid__;
    }
    /**
     * <p>iadAdmEuid をセットします。
     * @param iadAdmEuid iadAdmEuid
     */
    public void setIadAdmEuid(int iadAdmEuid) {
        iadAdmEuid__ = iadAdmEuid;
    }
    /**
     * <p>iadSid を取得します。
     * @return iadSid
     */
    public int getIadSid() {
        return iadSid__;
    }
    /**
     * <p>iadSid をセットします。
     * @param iadSid iadSid
     */
    public void setIadSid(int iadSid) {
        iadSid__ = iadSid;
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
     * <p>newIadSid を取得します。
     * @return newIadSid
     */
    public int getNewIadSid() {
        return newIadSid__;
    }
    /**
     * <p>newIadSid をセットします。
     * @param newIadSid newIadSid
     */
    public void setNewIadSid(int newIadSid) {
        newIadSid__ = newIadSid;
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
}