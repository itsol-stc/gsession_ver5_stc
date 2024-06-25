package jp.groupsession.v2.rsv.rsv100;

import jp.groupsession.v2.rsv.model.RsvSisYrkModel;

/**
 * <br>[機  能] RsvSisYrkModelを拡張したModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv100SisYrkModel extends RsvSisYrkModel {
    /** 最小年 */
    private String rsyMinYear__;
    /** 最大年 */
    private String rsyMaxYear__;

    //検索結果格納用
    /** 姓名 */
    private String rsySeiMei__;
    /** ユーザ削除区分*/
    private int usrJKbn__;
    /** ユーザ有効フラグ */
    private int usrUkoFlg__;
    /** 施設 */
    private String rsySisetu__;
    /** 開始日付 */
    private String rsyFrom__;
    /** 開始時刻 */
    private String rsyFromTime__;
    /** 終了日付 */
    private String rsyTo__;
    /** 終了時刻 */
    private String rsyToTime__;
    /** タイトル（利用目的） */
    private String rsyContent__;
    /** 施設予約情報SID */
    private int rsySisetuSid__;
    /** 内容・備考 */
    private String rsyBiko__;
    /** 施設ID */
    private String sisetuId__;
    /** ユーザID */
    private String userId__;
    /** 施設区分*/
    private int rskSid__;
    /** 担当部署*/
    private String rkyBusyo__;
    /** 担当・使用者名*/
    private String rkyName__;
    /** 人数*/
    private String rkyNum__;
    /** 利用区分*/
    private int rkyUseKbn__;
    /** 連絡先*/
    private String rkyContact__;
    /** 会議名案内*/
    private String rkyGuide__;
    /** 駐車場見込み台数*/
    private String rkyParkNum__;
    /** 行き先*/
    private String rkyDest__;
    /** 公開範囲 */
    private int public__;
    /** 閲覧可能フラグ */
    private boolean viewFlg__ = true;

    /**
     * <p>rsyContent を取得します。
     * @return rsyContent
     */
    public String getRsyContent() {
        return rsyContent__;
    }
    /**
     * <p>rsyContent をセットします。
     * @param rsyContent rsyContent
     */
    public void setRsyContent(String rsyContent) {
        rsyContent__ = rsyContent;
    }
    /**
     * <p>rsyFrom を取得します。
     * @return rsyFrom
     */
    public String getRsyFrom() {
        return rsyFrom__;
    }
    /**
     * <p>rsyFrom をセットします。
     * @param rsyFrom rsyFrom
     */
    public void setRsyFrom(String rsyFrom) {
        rsyFrom__ = rsyFrom;
    }
    /**
     * <p>rsyMaxYear を取得します。
     * @return rsyMaxYear
     */
    public String getRsyMaxYear() {
        return rsyMaxYear__;
    }
    /**
     * <p>rsyMaxYear をセットします。
     * @param rsyMaxYear rsyMaxYear
     */
    public void setRsyMaxYear(String rsyMaxYear) {
        rsyMaxYear__ = rsyMaxYear;
    }
    /**
     * <p>rsyMinYear を取得します。
     * @return rsyMinYear
     */
    public String getRsyMinYear() {
        return rsyMinYear__;
    }
    /**
     * <p>rsyMinYear をセットします。
     * @param rsyMinYear rsyMinYear
     */
    public void setRsyMinYear(String rsyMinYear) {
        rsyMinYear__ = rsyMinYear;
    }
    /**
     * <p>rsySeiMei を取得します。
     * @return rsySeiMei
     */
    public String getRsySeiMei() {
        return rsySeiMei__;
    }
    /**
     * <p>rsySeiMei をセットします。
     * @param rsySeiMei rsySeiMei
     */
    public void setRsySeiMei(String rsySeiMei) {
        rsySeiMei__ = rsySeiMei;
    }
    /**
     * <p>rsySisetuSid を取得します。
     * @return rsySisetuSid
     */
    public int getRsySisetuSid() {
        return rsySisetuSid__;
    }
    /**
     * <p>rsySisetuSid をセットします。
     * @param rsySisetuSid rsySisetuSid
     */
    public void setRsySisetuSid(int rsySisetuSid) {
        rsySisetuSid__ = rsySisetuSid;
    }
    /**
     * <p>rsyTo を取得します。
     * @return rsyTo
     */
    public String getRsyTo() {
        return rsyTo__;
    }
    /**
     * <p>rsyTo をセットします。
     * @param rsyTo rsyTo
     */
    public void setRsyTo(String rsyTo) {
        rsyTo__ = rsyTo;
    }
    /**
     * <p>rsySisetu を取得します。
     * @return rsySisetu
     */
    public String getRsySisetu() {
        return rsySisetu__;
    }
    /**
     * <p>rsySisetu をセットします。
     * @param rsySisetu rsySisetu
     */
    public void setRsySisetu(String rsySisetu) {
        rsySisetu__ = rsySisetu;
    }
    /**
     * <p>rsyBiko を取得します。
     * @return rsyBiko
     */
    public String getRsyBiko() {
        return rsyBiko__;
    }
    /**
     * <p>rsyBiko をセットします。
     * @param rsyBiko rsyBiko
     */
    public void setRsyBiko(String rsyBiko) {
        rsyBiko__ = rsyBiko;
    }
    /**
     * <p>sisetuId を取得します。
     * @return sisetuId
     */
    public String getSisetuId() {
        return sisetuId__;
    }
    /**
     * <p>sisetuId をセットします。
     * @param sisetuId sisetuId
     */
    public void setSisetuId(String sisetuId) {
        sisetuId__ = sisetuId;
    }
    /**
     * <p>userId を取得します。
     * @return userId
     */
    public String getUserId() {
        return userId__;
    }
    /**
     * <p>userId をセットします。
     * @param userId userId
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }
    /**
     * <p>rsyFromTime を取得します。
     * @return rsyFromTime
     */
    public String getRsyFromTime() {
        return rsyFromTime__;
    }
    /**
     * <p>rsyFromTime をセットします。
     * @param rsyFromTime rsyFromTime
     */
    public void setRsyFromTime(String rsyFromTime) {
        rsyFromTime__ = rsyFromTime;
    }
    /**
     * <p>rsyToTime を取得します。
     * @return rsyToTime
     */
    public String getRsyToTime() {
        return rsyToTime__;
    }
    /**
     * <p>rsyToTime をセットします。
     * @param rsyToTime rsyToTime
     */
    public void setRsyToTime(String rsyToTime) {
        rsyToTime__ = rsyToTime;
    }
    /**
     * <p>rskSid を取得します。
     * @return rskSid
     */
    public int getRskSid() {
        return rskSid__;
    }
    /**
     * <p>rskSid をセットします。
     * @param rskSid rskSid
     */
    public void setRskSid(int rskSid) {
        rskSid__ = rskSid;
    }
    /**
     * <p>rkyBusyo を取得します。
     * @return rkyBusyo
     */
    public String getRkyBusyo() {
        return rkyBusyo__;
    }
    /**
     * <p>rkyBusyo をセットします。
     * @param rkyBusyo rkyBusyo
     */
    public void setRkyBusyo(String rkyBusyo) {
        rkyBusyo__ = rkyBusyo;
    }
    /**
     * <p>rkyName を取得します。
     * @return rkyName
     */
    public String getRkyName() {
        return rkyName__;
    }
    /**
     * <p>rkyName をセットします。
     * @param rkyName rkyTanto
     */
    public void setRkyName(String rkyName) {
        rkyName__ = rkyName;
    }
    /**
     * <p>rkyNum を取得します。
     * @return rkyNum
     */
    public String getRkyNum() {
        return rkyNum__;
    }
    /**
     * <p>rkyNum をセットします。
     * @param rkyNum rkyNum
     */
    public void setRkyNum(String rkyNum) {
        rkyNum__ = rkyNum;
    }
    /**
     * <p>rkyUseKbn を取得します。
     * @return rkyUseKbn
     */
    public int getRkyUseKbn() {
        return rkyUseKbn__;
    }
    /**
     * <p>rkyUseKbn をセットします。
     * @param rkyUseKbn rkyUseKbn
     */
    public void setRkyUseKbn(int rkyUseKbn) {
        rkyUseKbn__ = rkyUseKbn;
    }
    /**
     * <p>rkyContact を取得します。
     * @return rkyContact
     */
    public String getRkyContact() {
        return rkyContact__;
    }
    /**
     * <p>rkyContact をセットします。
     * @param rkyContact rkyContact
     */
    public void setRkyContact(String rkyContact) {
        rkyContact__ = rkyContact;
    }
    /**
     * <p>rkyGuide を取得します。
     * @return rkyGuide
     */
    public String getRkyGuide() {
        return rkyGuide__;
    }
    /**
     * <p>rkyGuide をセットします。
     * @param rkyGuide rkyGuide
     */
    public void setRkyGuide(String rkyGuide) {
        rkyGuide__ = rkyGuide;
    }
    /**
     * <p>rkyParkNum を取得します。
     * @return rkyParkNum
     */
    public String getRkyParkNum() {
        return rkyParkNum__;
    }
    /**
     * <p>rkyParkNum をセットします。
     * @param rkyParkNum rkyParkNum
     */
    public void setRkyParkNum(String rkyParkNum) {
        rkyParkNum__ = rkyParkNum;
    }
    /**
     * <p>rkyDest を取得します。
     * @return rkyDest
     */
    public String getRkyDest() {
        return rkyDest__;
    }
    /**
     * <p>rkyDest をセットします。
     * @param rkyDest rkyDest
     */
    public void setRkyDest(String rkyDest) {
        rkyDest__ = rkyDest;
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
    /**
     * <p>viewFlg を取得します。
     * @return viewFlg
     * @see jp.groupsession.v2.rsv.rsv100.Rsv100SisYrkModel#viewFlg__
     */
    public boolean isViewFlg() {
        return viewFlg__;
    }
    /**
     * <p>viewFlg をセットします。
     * @param viewFlg viewFlg
     * @see jp.groupsession.v2.rsv.rsv100.Rsv100SisYrkModel#viewFlg__
     */
    public void setViewFlg(boolean viewFlg) {
        viewFlg__ = viewFlg;
    }

}
