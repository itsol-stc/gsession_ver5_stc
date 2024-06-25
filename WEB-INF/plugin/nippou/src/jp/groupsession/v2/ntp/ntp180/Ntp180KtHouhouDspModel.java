package jp.groupsession.v2.ntp.ntp180;

import java.io.Serializable;

/**
 * <br>[機  能] 活動方法情報(表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ntp180KtHouhouDspModel implements Serializable {

    /** 活動方法SID */
    private int kthSid__ = 0;
    /** 活動方法コード */
    private String kthCode__ = null;
    /** 活動方法名 */
    private String kthName__ = null;

    /** 並び順 */
    private int kthSort__;
    /** 表示順(画面用) */
    private String kthValue__ = null;
    /**
     * <p>kthSid を取得します。
     * @return kthSid
     */
    public int getKthSid() {
        return kthSid__;
    }
    /**
     * <p>kthSid をセットします。
     * @param kthSid kthSid
     */
    public void setKthSid(int kthSid) {
        kthSid__ = kthSid;
    }
    /**
     * <p>kthCode を取得します。
     * @return kthCode
     */
    public String getKthCode() {
        return kthCode__;
    }
    /**
     * <p>kthCode をセットします。
     * @param kthCode kthCode
     */
    public void setKthCode(String kthCode) {
        kthCode__ = kthCode;
    }
    /**
     * <p>kthName を取得します。
     * @return kthName
     */
    public String getKthName() {
        return kthName__;
    }
    /**
     * <p>kthName をセットします。
     * @param kthName kthName
     */
    public void setKthName(String kthName) {
        kthName__ = kthName;
    }
    /**
     * <p>kthSort を取得します。
     * @return kthSort
     */
    public int getKthSort() {
        return kthSort__;
    }
    /**
     * <p>kthSort をセットします。
     * @param kthSort kthSort
     */
    public void setKthSort(int kthSort) {
        kthSort__ = kthSort;
    }
    /**
     * <p>kthValue を取得します。
     * @return kthValue
     */
    public String getKthValue() {
        return kthValue__;
    }
    /**
     * <p>kthValue をセットします。
     * @param kthValue kthValue
     */
    public void setKthValue(String kthValue) {
        kthValue__ = kthValue;
    }

}
