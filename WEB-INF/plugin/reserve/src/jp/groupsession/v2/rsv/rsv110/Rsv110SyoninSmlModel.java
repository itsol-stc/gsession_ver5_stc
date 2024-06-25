package jp.groupsession.v2.rsv.rsv110;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 承認・却下メール内容を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv110SyoninSmlModel extends AbstractModel {

    /** 予約情報SID */
    private int rsvSid__ = 0;
    /** 利用目的 */
    private String rsvMokuteki__  = null;
    /** 内容 */
    private String rsvNaiyou__ = null;
    /** 宛先(登録者) */
    private int sendUserSid__ = 0;
    /** 開始日時 */
    private UDate rsvFrDate__ = null;
    /** 終了日時 */
    private UDate rsvToDate__ = null;
    /** 登録日時 */
    private UDate rsvAdate__ = null;
    /** URL */
    private String rsvUrl__ = null;

    /**
     * <p>rsvSid を取得します。
     * @return rsvSid
     */
    public int getRsvSid() {
        return rsvSid__;
    }

    /**
     * <p>rsvSid をセットします。
     * @param rsvSid rsvSid
     */
    public void setRsvSid(int rsvSid) {
        rsvSid__ = rsvSid;
    }

    /**
     * <p>rsvMokuteki を取得します。
     * @return rsvMokuteki
     */
    public String getRsvMokuteki() {
        return rsvMokuteki__;
    }

    /**
     * <p>rsvMokuteki をセットします。
     * @param rsvMokuteki rsvMokuteki
     */
    public void setRsvMokuteki(String rsvMokuteki) {
        rsvMokuteki__ = rsvMokuteki;
    }

    /**
     * <p>rsvNaiyou を取得します。
     * @return rsvNaiyou
     */
    public String getRsvNaiyou() {
        return rsvNaiyou__;
    }

    /**
     * <p>rsvNaiyou をセットします。
     * @param rsvNaiyou rsvNaiyou
     */
    public void setRsvNaiyou(String rsvNaiyou) {
        rsvNaiyou__ = rsvNaiyou;
    }

    /**
     * <p>sendUserSid を取得します。
     * @return sendUserSid
     */
    public int getSendUserSid() {
        return sendUserSid__;
    }

    /**
     * <p>sendUserSid をセットします。
     * @param sendUserSid sendUserSid
     */
    public void setSendUserSid(int sendUserSid) {
        sendUserSid__ = sendUserSid;
    }

    /**
     * <p>rsvFrDate を取得します。
     * @return rsvFrDate
     */
    public UDate getRsvFrDate() {
        return rsvFrDate__;
    }

    /**
     * <p>rsvFrDate をセットします。
     * @param rsvFrDate rsvFrDate
     */
    public void setRsvFrDate(UDate rsvFrDate) {
        rsvFrDate__ = rsvFrDate;
    }

    /**
     * <p>rsvToDate を取得します。
     * @return rsvToDate
     */
    public UDate getRsvToDate() {
        return rsvToDate__;
    }

    /**
     * <p>rsvToDate をセットします。
     * @param rsvToDate rsvToDate
     */
    public void setRsvToDate(UDate rsvToDate) {
        rsvToDate__ = rsvToDate;
    }

    /**
     * <p>rsvAdate を取得します。
     * @return rsvAdate
     */
    public UDate getRsvAdate() {
        return rsvAdate__;
    }

    /**
     * <p>rsvAdate をセットします。
     * @param rsvAdate rsvAdate
     */
    public void setRsvAdate(UDate rsvAdate) {
        rsvAdate__ = rsvAdate;
    }

    /**
     * <p>rsvUrl を取得します。
     * @return rsvUrl
     */
    public String getRsvUrl() {
        return rsvUrl__;
    }

    /**
     * <p>rsvUrl をセットします。
     * @param rsvUrl rsvUrl
     */
    public void setRsvUrl(String rsvUrl) {
        rsvUrl__ = rsvUrl;
    }
}
