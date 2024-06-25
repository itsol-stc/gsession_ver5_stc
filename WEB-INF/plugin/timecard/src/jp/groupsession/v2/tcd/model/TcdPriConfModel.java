package jp.groupsession.v2.tcd.model;

import java.io.Serializable;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstTimecard;

/**
 * <p>TCD_PRI_CONF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class TcdPriConfModel implements Serializable {

    /** USR_SID mapping */
    private int usrSid__;
    /** TPC_IN_HOUR mapping */
    private int tpcInHour__;
    /** TPC_IN_MIN mapping */
    private int tpcInMin__;
    /** TPC_OUT_HOUR mapping */
    private int tpcOutHour__;
    /** TPC_OUT_MIN mapping */
    private int tpcOutMin__;
    /** TPC_MAIN_DSP mapping */
    private int tpcMainDsp__;
    /** TPC_ZAISEKI_STS mapping */
    private int tpcZaisekiSts__;

    /** TPC_KINMU_OUT mapping */
    private int tpcKinmuOut__;

    /**
     * @return the tpcKinmuOut
     */
    public int getTpcKinmuOut() {
        return tpcKinmuOut__;
    }

    /**
     * @param tpcKinmuOut the tpcKinmuOut to set
     */
    public void setTpcKinmuOut(int tpcKinmuOut) {
        tpcKinmuOut__ = tpcKinmuOut;
    }

    /**
     * <p>tpcZaisekiSts を取得します。
     * @return tpcZaisekiSts
     */
    public int getTpcZaisekiSts() {
        return tpcZaisekiSts__;
    }

    /**
     * <p>tpcZaisekiSts をセットします。
     * @param tpcZaisekiSts tpcZaisekiSts
     */
    public void setTpcZaisekiSts(int tpcZaisekiSts) {
        tpcZaisekiSts__ = tpcZaisekiSts;
    }

    /**
     * <p>tpcMainDsp を取得します。
     * @return tpcMainDsp
     */
    public int getTpcMainDsp() {
        return tpcMainDsp__;
    }

    /**
     * <p>tpcMainDsp をセットします。
     * @param tpcMainDsp tpcMainDsp
     */
    public void setTpcMainDsp(int tpcMainDsp) {
        tpcMainDsp__ = tpcMainDsp;
    }

    /**
     * <p>Default Constructor
     */
    public TcdPriConfModel() {
    }

    /**
     * <p>初期値でインスタンス作成
     * @param usrSid ユーザSID
     */
    public TcdPriConfModel(int usrSid) {
        usrSid__ = usrSid;
        tpcInHour__ = GSConstTimecard.DF_IN_TIME;
        tpcInMin__ = GSConstTimecard.DF_IN_MIN;
        tpcOutHour__ = GSConstTimecard.DF_OUT_TIME;
        tpcOutMin__ = GSConstTimecard.DF_OUT_MIN;
        tpcMainDsp__ = GSConstCommon.MAIN_DSP;
        tpcZaisekiSts__ = GSConstTimecard.ZAISEKI_OFF;
    }

    /**
     * <p>get USR_SID value
     * @return USR_SID value
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>set USR_SID value
     * @param usrSid USR_SID value
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>get TPC_IN_HOUR value
     * @return TPC_IN_HOUR value
     */
    public int getTpcInHour() {
        return tpcInHour__;
    }

    /**
     * <p>set TPC_IN_HOUR value
     * @param tpcInHour TPC_IN_HOUR value
     */
    public void setTpcInHour(int tpcInHour) {
        tpcInHour__ = tpcInHour;
    }

    /**
     * <p>get TPC_IN_MIN value
     * @return TPC_IN_MIN value
     */
    public int getTpcInMin() {
        return tpcInMin__;
    }

    /**
     * <p>set TPC_IN_MIN value
     * @param tpcInMin TPC_IN_MIN value
     */
    public void setTpcInMin(int tpcInMin) {
        tpcInMin__ = tpcInMin;
    }

    /**
     * <p>get TPC_OUT_HOUR value
     * @return TPC_OUT_HOUR value
     */
    public int getTpcOutHour() {
        return tpcOutHour__;
    }

    /**
     * <p>set TPC_OUT_HOUR value
     * @param tpcOutHour TPC_OUT_HOUR value
     */
    public void setTpcOutHour(int tpcOutHour) {
        tpcOutHour__ = tpcOutHour;
    }

    /**
     * <p>get TPC_OUT_MIN value
     * @return TPC_OUT_MIN value
     */
    public int getTpcOutMin() {
        return tpcOutMin__;
    }

    /**
     * <p>set TPC_OUT_MIN value
     * @param tpcOutMin TPC_OUT_MIN value
     */
    public void setTpcOutMin(int tpcOutMin) {
        tpcOutMin__ = tpcOutMin;
    }

    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(usrSid__);
        buf.append(",");
        buf.append(tpcInHour__);
        buf.append(",");
        buf.append(tpcInMin__);
        buf.append(",");
        buf.append(tpcOutHour__);
        buf.append(",");
        buf.append(tpcOutMin__);
        return buf.toString();
    }

}
