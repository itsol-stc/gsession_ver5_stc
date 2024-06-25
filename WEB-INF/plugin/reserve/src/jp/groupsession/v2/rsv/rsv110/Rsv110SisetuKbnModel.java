package jp.groupsession.v2.rsv.rsv110;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] 施設予約 施設予約登録画面の施設区分別情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv110SisetuKbnModel extends AbstractModel {

    /** 担当部署 */
    private String rkyBusyo__ = null;
    /** 担当・使用者名 */
    private String rkyName__ = null;
    /** 人数 */
    private String rkyNum__ = null;
    /** 利用区分 */
    private int rkyUseKbn__ = -1;
    /** 連絡先 */
    private String rkyContact__ = null;
    /** 会議名案内 */
    private String rkyGuide__ = null;
    /** 駐車場見込み台数 */
    private String rkyParkNum__ = null;
    /** 印刷区分 */
    private int rkyPrintKbn__ = -1;
    /** 行き先 */
    private String rkyDest__ = null;
    /** 登録者ID */
    private int rkyAuid__ = -1;
    /** 登録日時 */
    private UDate rkyAdate__ = null;
    /** 更新者ID */
    private int rkyEuid__ = -1;
    /** 更新日時 */
    private UDate rkyEdate__ = null;
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
     * @param rkyName rkyName
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
     * <p>rkyAuid を取得します。
     * @return rkyAuid
     */
    public int getRkyAuid() {
        return rkyAuid__;
    }
    /**
     * <p>rkyAuid をセットします。
     * @param rkyAuid rkyAuid
     */
    public void setRkyAuid(int rkyAuid) {
        rkyAuid__ = rkyAuid;
    }
    /**
     * <p>rkyAdate を取得します。
     * @return rkyAdate
     */
    public UDate getRkyAdate() {
        return rkyAdate__;
    }
    /**
     * <p>rkyAdate をセットします。
     * @param rkyAdate rkyAdate
     */
    public void setRkyAdate(UDate rkyAdate) {
        rkyAdate__ = rkyAdate;
    }
    /**
     * <p>rkyEuid を取得します。
     * @return rkyEuid
     */
    public int getRkyEuid() {
        return rkyEuid__;
    }
    /**
     * <p>rkyEuid をセットします。
     * @param rkyEuid rkyEuid
     */
    public void setRkyEuid(int rkyEuid) {
        rkyEuid__ = rkyEuid;
    }
    /**
     * <p>rkyEdate を取得します。
     * @return rkyEdate
     */
    public UDate getRkyEdate() {
        return rkyEdate__;
    }
    /**
     * <p>rkyEdate をセットします。
     * @param rkyEdate rkyEdate
     */
    public void setRkyEdate(UDate rkyEdate) {
        rkyEdate__ = rkyEdate;
    }
    /**
     * <p>rkyPrintKbn を取得します。
     * @return rkyPrintKbn
     */
    public int getRkyPrintKbn() {
        return rkyPrintKbn__;
    }
    /**
     * <p>rkyPrintKbn をセットします。
     * @param rkyPrintKbn rkyPrintKbn
     */
    public void setRkyPrintKbn(int rkyPrintKbn) {
        rkyPrintKbn__ = rkyPrintKbn;
    }

}