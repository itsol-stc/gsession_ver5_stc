package jp.groupsession.v2.ntp.ntp190;

import java.io.Serializable;

/**
 * <br>[機  能] 日報 顧客源泉一覧画面で使用する情報を格納したModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp190ContactDspModel implements Serializable {

    /** コンタクトSID */
    private int contSid__ = 0;
    /** コンタクトコード */
    private String contCode__ = null;
    /** コンタクト名 */
    private String contName__ = null;

    /** 並び順 */
    private int contSort__;
    /** 表示順(画面用) */
    private String contValue__ = null;
    /**
     * <p>contSid を取得します。
     * @return contSid
     */
    public int getContSid() {
        return contSid__;
    }
    /**
     * <p>contSid をセットします。
     * @param contSid contSid
     */
    public void setContSid(int contSid) {
        contSid__ = contSid;
    }
    /**
     * <p>contCode を取得します。
     * @return contCode
     */
    public String getContCode() {
        return contCode__;
    }
    /**
     * <p>contCode をセットします。
     * @param contCode contCode
     */
    public void setContCode(String contCode) {
        contCode__ = contCode;
    }
    /**
     * <p>contName を取得します。
     * @return contName
     */
    public String getContName() {
        return contName__;
    }
    /**
     * <p>contName をセットします。
     * @param contName contName
     */
    public void setContName(String contName) {
        contName__ = contName;
    }
    /**
     * <p>contSort を取得します。
     * @return contSort
     */
    public int getContSort() {
        return contSort__;
    }
    /**
     * <p>contSort をセットします。
     * @param contSort contSort
     */
    public void setContSort(int contSort) {
        contSort__ = contSort;
    }
    /**
     * <p>contValue を取得します。
     * @return contValue
     */
    public String getContValue() {
        return contValue__;
    }
    /**
     * <p>contValue をセットします。
     * @param contValue contValue
     */
    public void setContValue(String contValue) {
        contValue__ = contValue;
    }
}
