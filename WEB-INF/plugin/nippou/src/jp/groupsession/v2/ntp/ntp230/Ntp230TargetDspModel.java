package jp.groupsession.v2.ntp.ntp230;

import java.io.Serializable;

/**
 * <br>[機  能] 日報 目標一覧画面の画面情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp230TargetDspModel implements Serializable {

    /** 目標SID */
    private int targetSid__ = 0;
    /** コンタクト名 */
    private String targetName__ = null;

    /** 並び順 */
    private int targetSort__;
    /** 表示順(画面用) */
    private String targetValue__ = null;
    /**
     * <p>targetSid を取得します。
     * @return targetSid
     */
    public int getTargetSid() {
        return targetSid__;
    }
    /**
     * <p>targetSid をセットします。
     * @param targetSid targetSid
     */
    public void setTargetSid(int targetSid) {
        targetSid__ = targetSid;
    }
    /**
     * <p>targetName を取得します。
     * @return targetName
     */
    public String getTargetName() {
        return targetName__;
    }
    /**
     * <p>targetName をセットします。
     * @param targetName targetName
     */
    public void setTargetName(String targetName) {
        targetName__ = targetName;
    }
    /**
     * <p>targetSort を取得します。
     * @return targetSort
     */
    public int getTargetSort() {
        return targetSort__;
    }
    /**
     * <p>targetSort をセットします。
     * @param targetSort targetSort
     */
    public void setTargetSort(int targetSort) {
        targetSort__ = targetSort;
    }
    /**
     * <p>targetValue を取得します。
     * @return targetValue
     */
    public String getTargetValue() {
        return targetValue__;
    }
    /**
     * <p>targetValue をセットします。
     * @param targetValue targetValue
     */
    public void setTargetValue(String targetValue) {
        targetValue__ = targetValue;
    }
}
