package jp.groupsession.v2.cir.cir210;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 回覧板統計情報表示用のモデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class Cir210DspModel implements Serializable {

    /** 日付 */
    private String date__;
    /** 日付 (UDate) */
    private UDate uDate__;
    /** 表示用日付 */
    private String dspDate__;
    /** 受信件数 */
    private int jcirNum__;
    /** 受信件数（表示用） */
    private String strJcirNum__;
    /** 送信件数 */
    private int scirNum__;
    /** 送信件数（表示用） */
    private String strScirNum__;
    /**
     * <p>date を取得します。
     * @return date
     */
    public String getDate() {
        return date__;
    }
    /**
     * <p>date をセットします。
     * @param date date
     */
    public void setDate(String date) {
        date__ = date;
    }
    /**
     * <p>dspDate を取得します。
     * @return dspDate
     */
    public String getDspDate() {
        return dspDate__;
    }
    /**
     * <p>dspDate をセットします。
     * @param dspDate dspDate
     */
    public void setDspDate(String dspDate) {
        dspDate__ = dspDate;
    }
    /**
     * <p>jcirNum を取得します。
     * @return jcirNum
     */
    public int getJcirNum() {
        return jcirNum__;
    }
    /**
     * <p>jcirNum をセットします。
     * @param jcirNum jcirNum
     */
    public void setJcirNum(int jcirNum) {
        jcirNum__ = jcirNum;
    }
    /**
     * <p>strJcirNum を取得します。
     * @return strJcirNum
     */
    public String getStrJcirNum() {
        return strJcirNum__;
    }
    /**
     * <p>strJcirNum をセットします。
     * @param strJcirNum strJcirNum
     */
    public void setStrJcirNum(String strJcirNum) {
        strJcirNum__ = strJcirNum;
    }
    /**
     * <p>scirNum を取得します。
     * @return scirNum
     */
    public int getScirNum() {
        return scirNum__;
    }
    /**
     * <p>scirNum をセットします。
     * @param scirNum scirNum
     */
    public void setScirNum(int scirNum) {
        scirNum__ = scirNum;
    }
    /**
     * <p>strScirNum を取得します。
     * @return strScirNum
     */
    public String getStrScirNum() {
        return strScirNum__;
    }
    /**
     * <p>strScirNum をセットします。
     * @param strScirNum strScirNum
     */
    public void setStrScirNum(String strScirNum) {
        strScirNum__ = strScirNum;
    }
    /**
     * <p>uDate を取得します。
     * @return uDate
     */
    public UDate getuDate() {
        return uDate__;
    }
    /**
     * <p>uDate をセットします。
     * @param uDate uDate
     */
    public void setuDate(UDate uDate) {
        uDate__ = uDate;
    }

}