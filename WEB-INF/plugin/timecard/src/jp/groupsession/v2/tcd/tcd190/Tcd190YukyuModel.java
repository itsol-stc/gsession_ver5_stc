package jp.groupsession.v2.tcd.tcd190;

import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] タイムカード管理者設定 有休日数一覧画面の有休情報描画用クラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd190YukyuModel {

    /** 氏名 */
    private String name__ = null;
    
    /** 有休日数 */
    private double yukyuDays__;
    
    /** 有休使用日数 */
    private double yukyuUseDays__;
    
    /** 有休消化率 */
    private double yukyuUsePercent__;
    
    /** ユーザSID */
    private int usrSid__;
    
    /** ユーザ有効区分 */
    private int userUkoFlg__ = GSConst.YUKOMUKO_YUKO;
    
    /** 社員/職員番号*/
    private String employeeNumber__ = null;

    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#name__
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#name__
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     * <p>yukyuDays を取得します。
     * @return yukyuDays
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#yukyuDays__
     */
    public double getYukyuDays() {
        return yukyuDays__;
    }

    /**
     * <p>yukyuDays をセットします。
     * @param yukyuDays yukyuDays
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#yukyuDays__
     */
    public void setYukyuDays(double yukyuDays) {
        yukyuDays__ = yukyuDays;
    }

    /**
     * <p>yukyuUseDays を取得します。
     * @return yukyuUseDays
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#yukyuUseDays__
     */
    public double getYukyuUseDays() {
        return yukyuUseDays__;
    }

    /**
     * <p>yukyuUseDays をセットします。
     * @param yukyuUseDays yukyuUseDays
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#yukyuUseDays__
     */
    public void setYukyuUseDays(double yukyuUseDays) {
        yukyuUseDays__ = yukyuUseDays;
    }

    /**
     * <p>yukyuUsePercent を取得します。
     * @return yukyuUsePercent
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#yukyuUsePercent__
     */
    public double getYukyuUsePercent() {
        return yukyuUsePercent__;
    }

    /**
     * <p>yukyuUsePercent をセットします。
     * @param yukyuUsePercent yukyuUsePercent
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#yukyuUsePercent__
     */
    public void setYukyuUsePercent(double yukyuUsePercent) {
        yukyuUsePercent__ = yukyuUsePercent;
    }

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#usrSid__
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#usrSid__
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>userUkoFlg を取得します。
     * @return userUkoFlg
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#userUkoFlg__
     */
    public int getUserUkoFlg() {
        return userUkoFlg__;
    }

    /**
     * <p>userUkoFlg をセットします。
     * @param userUkoFlg userUkoFlg
     * @see jp.groupsession.v2.tcd.tcd190.Tcd190YukyuModel#userUkoFlg__
     */
    public void setUserUkoFlg(int userUkoFlg) {
        userUkoFlg__ = userUkoFlg;
    }

    public String getEmployeeNumber() {
        return employeeNumber__;
    }

    public void setEmployeeNumber(String employeeNumber) {
        employeeNumber__ = employeeNumber;
    }
    
}
