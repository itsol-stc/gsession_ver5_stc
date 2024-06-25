package jp.groupsession.v2.tcd.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] タイムカードCSV出力条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdCsvSearchModel extends AbstractModel {

    /** 開始日付 */
    private UDate tcdCsvFrDate__;
    /** 終了日付 */
    private UDate tcdCsvToDate__;
    /** ユーザSID */
    private int tcdCsvUserSid__;

    /**
     * <p>tcdCsvFrDate を取得します。
     * @return tcdCsvFrDate
     */
    public UDate getTcdCsvFrDate() {
        return tcdCsvFrDate__;
    }
    /**
     * <p>tcdCsvFrDate をセットします。
     * @param tcdCsvFrDate tcdCsvFrDate
     */
    public void setTcdCsvFrDate(UDate tcdCsvFrDate) {
        tcdCsvFrDate__ = tcdCsvFrDate;
    }
    /**
     * <p>tcdCsvToDate を取得します。
     * @return tcdCsvToDate
     */
    public UDate getTcdCsvToDate() {
        return tcdCsvToDate__;
    }
    /**
     * <p>tcdCsvToDate をセットします。
     * @param tcdCsvToDate tcdCsvToDate
     */
    public void setTcdCsvToDate(UDate tcdCsvToDate) {
        tcdCsvToDate__ = tcdCsvToDate;
    }
    /**
     * <p>tcdCsvUserSid を取得します。
     * @return tcdCsvUserSid
     */
    public int getTcdCsvUserSid() {
        return tcdCsvUserSid__;
    }
    /**
     * <p>tcdCsvUserSid をセットします。
     * @param tcdCsvUserSid tcdCsvUserSid
     */
    public void setTcdCsvUserSid(int tcdCsvUserSid) {
        tcdCsvUserSid__ = tcdCsvUserSid;
    }

}