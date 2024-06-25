package jp.groupsession.v2.tcd.csv;

import jp.groupsession.v2.tcd.tcd010.Tcd010Model;

/**
 * <br>[機  能] タイムカード一覧CSV用の１日分のデータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdCsvModel extends Tcd010Model {

    /** ユーザ氏名*/
    private String userName__;
    /** 社員/職員番号 */
    private String syainNo__;
    /** 休日区分名称 */
    private String thiName__ = null;
    /** 休日集計区分 */
    private int thiHoltotalKbn__ = 0;
    /** 時間帯略称*/
    private String ttiRyaku__;

    /**
     * <p>userName を取得します。
     * @return userName
     */
    public String getUserName() {
        return userName__;
    }

    /**
     * <p>userName をセットします。
     * @param userName userName
     */
    public void setUserName(String userName) {
        userName__ = userName;
    }

    /**
     * <p>syainNo を取得します。
     * @return syainNo
     */
    public String getSyainNo() {
        return syainNo__;
    }

    /**
     * <p>syainNo をセットします。
     * @param syainNo syainNo
     */
    public void setSyainNo(String syainNo) {
        syainNo__ = syainNo;
    }

    /**
     * <p>thiName を取得します。
     * @return thiName
     * @see jp.groupsession.v2.tcd.csv.TcdCsvModel#thiName__
     */
    public String getThiName() {
        return thiName__;
    }

    /**
     * <p>thiName をセットします。
     * @param thiName thiName
     * @see jp.groupsession.v2.tcd.csv.TcdCsvModel#thiName__
     */
    public void setThiName(String thiName) {
        thiName__ = thiName;
    }

    /**
     * <p>thiHoltotalKbn を取得します。
     * @return thiHoltotalKbn
     * @see jp.groupsession.v2.tcd.csv.TcdCsvModel#thiHoltotalKbn__
     */
    public int getThiHoltotalKbn() {
        return thiHoltotalKbn__;
    }

    /**
     * <p>thiHoltotalKbn をセットします。
     * @param thiHoltotalKbn thiHoltotalKbn
     * @see jp.groupsession.v2.tcd.csv.TcdCsvModel#thiHoltotalKbn__
     */
    public void setThiHoltotalKbn(int thiHoltotalKbn) {
        thiHoltotalKbn__ = thiHoltotalKbn;
    }

    /**
     * <p>ttiRyaku を取得します。
     * @return ttiRyaku
     * @see jp.groupsession.v2.tcd.csv.TcdCsvModel#ttiRyaku__
     */
    public String getTtiRyaku() {
        return ttiRyaku__;
    }

    /**
     * <p>ttiRyaku をセットします。
     * @param ttiRyaku ttiRyaku
     * @see jp.groupsession.v2.tcd.csv.TcdCsvModel#ttiRyaku__
     */
    public void setTtiRyaku(String ttiRyaku) {
        ttiRyaku__ = ttiRyaku;
    }

}
