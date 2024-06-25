package jp.groupsession.v2.cht.cht070;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] チャット統計情報表示用のモデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class Cht070DspModel implements Serializable {

    /** 日付 */
    private UDate date__;
    /** 日付 (UDate) */
    private String strDate__;
    /** 表示用日付 */
    private String dspDate__;
    /** 送信数 */
    private int sendNum__;
    /** 送信数（表示用） */
    private String strSendNum__;
    /** 合計 */
    private int sumNum__;
    /** 合計（表示用） */
    private String strSumNum__;
    /** ユーザ数 */
    private int userNum__;
    /** ユーザ数（表示用） */
    private String strUserNum__;
    /** グループ数 */
    private int groupNum__;
    /** グループ数（表示用） */
    private String strGroupNum__;


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
     * <p>sendNum を取得します。
     * @return sendNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#sendNum__
     */
    public int getSendNum() {
        return sendNum__;
    }
    /**
     * <p>sendNum をセットします。
     * @param sendNum sendNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#sendNum__
     */
    public void setSendNum(int sendNum) {
        sendNum__ = sendNum;
    }
    /**
     * <p>strSendNum を取得します。
     * @return strSendNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strSendNum__
     */
    public String getStrSendNum() {
        return strSendNum__;
    }
    /**
     * <p>strSendNum をセットします。
     * @param strSendNum strSendNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strSendNum__
     */
    public void setStrSendNum(String strSendNum) {
        strSendNum__ = strSendNum;
    }
    /**
     * <p>sumNum を取得します。
     * @return sumNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#sumNum__
     */
    public int getSumNum() {
        return sumNum__;
    }
    /**
     * <p>sumNum をセットします。
     * @param sumNum sumNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#sumNum__
     */
    public void setSumNum(int sumNum) {
        sumNum__ = sumNum;
    }
    /**
     * <p>strSumNum を取得します。
     * @return strSumNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strSumNum__
     */
    public String getStrSumNum() {
        return strSumNum__;
    }
    /**
     * <p>strSumNum をセットします。
     * @param strSumNum strSumNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strSumNum__
     */
    public void setStrSumNum(String strSumNum) {
        strSumNum__ = strSumNum;
    }
    /**
     * <p>userNum を取得します。
     * @return userNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#userNum__
     */
    public int getUserNum() {
        return userNum__;
    }
    /**
     * <p>userNum をセットします。
     * @param userNum userNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#userNum__
     */
    public void setUserNum(int userNum) {
        userNum__ = userNum;
    }
    /**
     * <p>strUserNum を取得します。
     * @return strUserNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strUserNum__
     */
    public String getStrUserNum() {
        return strUserNum__;
    }
    /**
     * <p>strUserNum をセットします。
     * @param strUserNum strUserNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strUserNum__
     */
    public void setStrUserNum(String strUserNum) {
        strUserNum__ = strUserNum;
    }
    /**
     * <p>groupNum を取得します。
     * @return groupNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#groupNum__
     */
    public int getGroupNum() {
        return groupNum__;
    }
    /**
     * <p>groupNum をセットします。
     * @param groupNum groupNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#groupNum__
     */
    public void setGroupNum(int groupNum) {
        groupNum__ = groupNum;
    }
    /**
     * <p>strGroupNum を取得します。
     * @return strGroupNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strGroupNum__
     */
    public String getStrGroupNum() {
        return strGroupNum__;
    }
    /**
     * <p>strGroupNum をセットします。
     * @param strGroupNum strGroupNum
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strGroupNum__
     */
    public void setStrGroupNum(String strGroupNum) {
        strGroupNum__ = strGroupNum;
    }
    /**
     * <p>date を取得します。
     * @return date
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#date__
     */
    public UDate getDate() {
        return date__;
    }
    /**
     * <p>date をセットします。
     * @param date date
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#date__
     */
    public void setDate(UDate date) {
        date__ = date;
    }
    /**
     * <p>strDate を取得します。
     * @return strDate
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strDate__
     */
    public String getStrDate() {
        return strDate__;
    }
    /**
     * <p>strDate をセットします。
     * @param strDate strDate
     * @see jp.groupsession.v2.cht.cht070.Cht070DspModel#strDate__
     */
    public void setStrDate(String strDate) {
        strDate__ = strDate;
    }

}