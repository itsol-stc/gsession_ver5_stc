package jp.groupsession.v2.ntp.ntp140;

import java.io.Serializable;

/**
 * <br>[機  能] 日報 業種一覧画面で扱う情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp140GyomuDspModel implements Serializable {

    /** 業種SID */
    private int gymSid__ = 0;
    /** 業種コード */
    private String gymCode__ = null;
    /** 業種名 */
    private String gymName__ = null;

    /** 並び順 */
    private int gymSort__;
    /** 表示順(画面用) */
    private String gymValue__ = null;
    /**
     * <p>gymSid を取得します。
     * @return gymSid
     */
    public int getGymSid() {
        return gymSid__;
    }
    /**
     * <p>gymSid をセットします。
     * @param gymSid gymSid
     */
    public void setGymSid(int gymSid) {
        gymSid__ = gymSid;
    }
    /**
     * <p>gymCode を取得します。
     * @return gymCode
     */
    public String getGymCode() {
        return gymCode__;
    }
    /**
     * <p>gymCode をセットします。
     * @param gymCode gymCode
     */
    public void setGymCode(String gymCode) {
        gymCode__ = gymCode;
    }
    /**
     * <p>gymName を取得します。
     * @return gymName
     */
    public String getGymName() {
        return gymName__;
    }
    /**
     * <p>gymName をセットします。
     * @param gymName gymName
     */
    public void setGymName(String gymName) {
        gymName__ = gymName;
    }
    /**
     * <p>gymSort を取得します。
     * @return gymSort
     */
    public int getGymSort() {
        return gymSort__;
    }
    /**
     * <p>gymSort をセットします。
     * @param gymSort gymSort
     */
    public void setGymSort(int gymSort) {
        gymSort__ = gymSort;
    }
    /**
     * <p>gymValue を取得します。
     * @return gymValue
     */
    public String getGymValue() {
        return gymValue__;
    }
    /**
     * <p>gymValue をセットします。
     * @param gymValue gymValue
     */
    public void setGymValue(String gymValue) {
        gymValue__ = gymValue;
    }
}
