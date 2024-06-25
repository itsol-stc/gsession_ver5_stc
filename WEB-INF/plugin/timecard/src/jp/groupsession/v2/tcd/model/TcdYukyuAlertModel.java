package jp.groupsession.v2.tcd.model;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <p>TCD_YUKYU_ALERT Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.5
 */
public class TcdYukyuAlertModel implements Serializable {

    /** TYA_NO mapping */
    private int tyaNo__;
    /** TYA_MONTH mapping */
    private int tyaMonth__;
    /** TYA_BASEDAYS mapping */
    private int tyaBasedays__;
    /** TYA_MESSAGE mapping */
    private String tyaMessage__;

    /**
     * <p>Default Constructor
     */
    public TcdYukyuAlertModel() {
    }

    /**
     * <p>get TYA_NO value
     * @return TYA_NO value
     */
    public int getTyaNo() {
        return tyaNo__;
    }

    /**
     * <p>set TYA_NO value
     * @param tyaNo TYA_NO value
     */
    public void setTyaNo(int tyaNo) {
        tyaNo__ = tyaNo;
    }

    /**
     * <p>get TYA_MONTH value
     * @return TYA_MONTH value
     */
    public int getTyaMonth() {
        return tyaMonth__;
    }

    /**
     * <p>set TYA_MONTH value
     * @param tyaMonth TYA_MONTH value
     */
    public void setTyaMonth(int tyaMonth) {
        tyaMonth__ = tyaMonth;
    }

    /**
     * <p>get TYA_BASEDAYS value
     * @return TYA_BASEDAYS value
     */
    public int getTyaBasedays() {
        return tyaBasedays__;
    }

    /**
     * <p>set TYA_BASEDAYS value
     * @param tyaBasedays TYA_BASEDAYS value
     */
    public void setTyaBasedays(int tyaBasedays) {
        tyaBasedays__ = tyaBasedays;
    }

    /**
     * <p>get TYA_MESSAGE value
     * @return TYA_MESSAGE value
     */
    public String getTyaMessage() {
        return tyaMessage__;
    }

    /**
     * <p>set TYA_MESSAGE value
     * @param tyaMessage TYA_MESSAGE value
     */
    public void setTyaMessage(String tyaMessage) {
        tyaMessage__ = tyaMessage;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuffer buf = new StringBuffer();
        buf.append(tyaNo__);
        buf.append(",");
        buf.append(tyaMonth__);
        buf.append(",");
        buf.append(tyaBasedays__);
        buf.append(",");
        buf.append(NullDefault.getString(tyaMessage__, ""));
        return buf.toString();
    }

}
