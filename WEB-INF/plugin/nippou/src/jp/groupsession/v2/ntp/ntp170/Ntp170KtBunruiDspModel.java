package jp.groupsession.v2.ntp.ntp170;

import java.io.Serializable;

/**
 * <br>[機  能] 活動分類情報(表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Ntp170KtBunruiDspModel implements Serializable {

    /** 活動分類SID */
    private int ktbSid__ = 0;
    /**活動分類コード */
    private String ktbCode__ = null;
    /** 活動分類名 */
    private String ktbName__ = null;

    /** 並び順 */
    private int ktbSort__;
    /** 表示順(画面用) */
    private String ktbValue__ = null;
    /**
     * <p>ktbSid を取得します。
     * @return ktbSid
     */
    public int getKtbSid() {
        return ktbSid__;
    }
    /**
     * <p>ktbSid をセットします。
     * @param ktbSid ktbSid
     */
    public void setKtbSid(int ktbSid) {
        ktbSid__ = ktbSid;
    }
    /**
     * <p>ktbCode を取得します。
     * @return ktbCode
     */
    public String getKtbCode() {
        return ktbCode__;
    }
    /**
     * <p>ktbCode をセットします。
     * @param ktbCode ktbCode
     */
    public void setKtbCode(String ktbCode) {
        ktbCode__ = ktbCode;
    }
    /**
     * <p>ktbName を取得します。
     * @return ktbName
     */
    public String getKtbName() {
        return ktbName__;
    }
    /**
     * <p>ktbName をセットします。
     * @param ktbName ktbName
     */
    public void setKtbName(String ktbName) {
        ktbName__ = ktbName;
    }
    /**
     * <p>ktbSort を取得します。
     * @return ktbSort
     */
    public int getKtbSort() {
        return ktbSort__;
    }
    /**
     * <p>ktbSort をセットします。
     * @param ktbSort ktbSort
     */
    public void setKtbSort(int ktbSort) {
        ktbSort__ = ktbSort;
    }
    /**
     * <p>ktbValue を取得します。
     * @return ktbValue
     */
    public String getKtbValue() {
        return ktbValue__;
    }
    /**
     * <p>ktbValue をセットします。
     * @param ktbValue ktbValue
     */
    public void setKtbValue(String ktbValue) {
        ktbValue__ = ktbValue;
    }


}
