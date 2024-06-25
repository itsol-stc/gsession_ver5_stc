package jp.groupsession.v2.tcd.model;

import java.sql.Time;

import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] タイムカード時間帯設定の明細を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdTimezoneMeiModel extends AbstractModel {

    /** 縦幅(cols/rows) */
    private int rows__;
    /** インデックス(出力先index) */
    private int index__;
    /** 時間帯SID */
    private int timeZoneSID__;
    /** 時間帯区分 */
    private String timeZoneKbn__ = null;
    /** 時間帯from */
    private Time frTime__ = null;
    /** 時間帯to */
    private Time toTime__ = null;
    //画面表示用
    /** 時間帯文字列 */
    private String timeZoneStr__ = null;
    /** 識別用番号 */
    private int timeZoneNo__ = -1;

    /**
     * コンストラクタ
     */
    public TcdTimezoneMeiModel() {

    }
    /**
     * コンストラクタ
     * @param tzMdl TcdTimezoneModel
     */
    public TcdTimezoneMeiModel(TcdTimezoneModel tzMdl) {
        if (tzMdl != null) {
            timeZoneSID__ = tzMdl.getTtzSid();
            timeZoneKbn__ = String.valueOf(tzMdl.getTtzKbn());
            frTime__ = tzMdl.getTtzFrtime();
            toTime__ = tzMdl.getTtzTotime();
        }

    }
    /**
     * <p>frTime を取得します。
     * @return frTime
     */
    public Time getFrTime() {
        return frTime__;
    }
    /**
     * <p>frTime をセットします。
     * @param frTime frTime
     */
    public void setFrTime(Time frTime) {
        frTime__ = frTime;
    }
    /**
     * <p>toTime を取得します。
     * @return toTime
     */
    public Time getToTime() {
        return toTime__;
    }
    /**
     * <p>toTime をセットします。
     * @param toTime toTime
     */
    public void setToTime(Time toTime) {
        toTime__ = toTime;
    }
    /**
     * <p>index を取得します。
     * @return index
     */
    public int getIndex() {
        return index__;
    }
    /**
     * <p>index をセットします。
     * @param index index
     */
    public void setIndex(int index) {
        index__ = index;
    }
    /**
     * <p>rows を取得します。
     * @return rows
     */
    public int getRows() {
        return rows__;
    }
    /**
     * <p>rows をセットします。
     * @param rows rows
     */
    public void setRows(int rows) {
        rows__ = rows;
    }
    /**
     * <p>timeZoneKbn を取得します。
     * @return timeZoneKbn
     */
    public String getTimeZoneKbn() {
        return timeZoneKbn__;
    }
    /**
     * <p>timeZoneKbn をセットします。
     * @param timeZoneKbn timeZoneKbn
     */
    public void setTimeZoneKbn(String timeZoneKbn) {
        timeZoneKbn__ = timeZoneKbn;
    }
    /**
     * <p>timeZoneSID を取得します。
     * @return timeZoneSID
     */
    public int getTimeZoneSID() {
        return timeZoneSID__;
    }
    /**
     * <p>timeZoneSID をセットします。
     * @param timeZoneSID timeZoneSID
     */
    public void setTimeZoneSID(int timeZoneSID) {
        timeZoneSID__ = timeZoneSID;
    }
    /**
     * <p>timeZoneStr を取得します。
     * @return timeZoneStr
     */
    public String getTimeZoneStr() {
        return timeZoneStr__;
    }
    /**
     * <p>timeZoneStr をセットします。
     * @param timeZoneStr timeZoneStr
     */
    public void setTimeZoneStr(String timeZoneStr) {
        timeZoneStr__ = timeZoneStr;
    }
    /**
     * <p>timeZoneNo を取得します。
     * @return timeZoneNo
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneMeiModel#timeZoneNo__
     */
    public int getTimeZoneNo() {
        return timeZoneNo__;
    }
    /**
     * <p>timeZoneNo をセットします。
     * @param timeZoneNo timeZoneNo
     * @see jp.groupsession.v2.tcd.model.TcdTimezoneMeiModel#timeZoneNo__
     */
    public void setTimeZoneNo(int timeZoneNo) {
        timeZoneNo__ = timeZoneNo;
    }

}
