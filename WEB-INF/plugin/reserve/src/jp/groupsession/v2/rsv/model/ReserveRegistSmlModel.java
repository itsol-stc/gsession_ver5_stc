package jp.groupsession.v2.rsv.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 登録通知用ショートメール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ReserveRegistSmlModel {

    /** 予約情報SID */
    private int rsvSid__ = 0;
    /** 利用目的 */
    private String rsvMokuteki__  = null;
    /** 内容 */
    private String rsvNaiyou__ = null;
    /** ユーザSID(登録者) */
    private int userSid__ = 0;
    /** 開始日時 */
    private UDate rsvFrDate__ = null;
    /** 終了日時 */
    private UDate rsvToDate__ = null;
    /** 登録日時 */
    private UDate rsvAdate__ = null;
    /** URL */
    private String rsvUrl__ = null;
    /** 宛先リスト */
    private List<Integer>  sidList__ = null;
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
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
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
    /**
     * <p>sidList を取得します。
     * @return sidList
     */
    public List<Integer> getSidList() {
        return sidList__;
    }
    /**
     * <p>sidList をセットします。
     * @param sidList sidList
     */
    public void setSidList(List<Integer> sidList) {
        sidList__ = sidList;
    }
}