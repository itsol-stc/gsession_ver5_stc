package jp.groupsession.v2.ip.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] IP管理 ネットワーク管理者情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class IpkNetAdmModel extends AbstractModel {

    /** ネットワークSID */
    private int netSid__;
    /** 登録者ID */
    private int netAuid__;
    /** 登録日時 */
    private UDate netAdate__;
    /** 更新ID */
    private int netEuid__;
    /** 更新日時 */
    private UDate netEdate__;
    /** ユーザSID */
    private int usrSid__;

    /**
     * <p>netAuid を取得します。
     * @return netAuid
     */
    public int getNetAuid() {
        return netAuid__;
    }
    /**
     * <p>netAuid をセットします。
     * @param netAuid netAuid
     */
    public void setNetAuid(int netAuid) {
        netAuid__ = netAuid;
    }

    /**
     * <p>netAdate を取得します。
     * @return netAdate
     */
    public UDate getNetAdate() {
        return netAdate__;
    }
    /**
     * <p>netAdate をセットします。
     * @param netAdate netAdate
     */
    public void setNetAdate(UDate netAdate) {
        netAdate__ = netAdate;
    }
    /**
     * <p>netEdate を取得します。
     * @return netEdate
     */
    public UDate getNetEdate() {
        return netEdate__;
    }
    /**
     * <p>netEdate をセットします。
     * @param netEdate netEdate
     */
    public void setNetEdate(UDate netEdate) {
        netEdate__ = netEdate;
    }
    /**
     * <p>netEuid を取得します。
     * @return netEuid
     */
    public int getNetEuid() {
        return netEuid__;
    }
    /**
     * <p>netEuid をセットします。
     * @param netEuid netEuid
     */
    public void setNetEuid(int netEuid) {
        netEuid__ = netEuid;
    }
    /**
     * <p>netSid を取得します。
     * @return netSid
     */
    public int getNetSid() {
        return netSid__;
    }
    /**
     * <p>netSid をセットします。
     * @param netSid netSid
     */
    public void setNetSid(int netSid) {
        netSid__ = netSid;
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
}