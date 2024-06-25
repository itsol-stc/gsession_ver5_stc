package jp.groupsession.v2.ntp.ntp150;

import java.io.Serializable;

/**
 * <br>[機  能] 業務内容毎のプロセス情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp150ProcessModel implements Serializable {
    /** 業務名 */
    private String ngyName__ = null;

    /** プロセスSID */
    private int ngpSid__ = 0;
    /** 業務SID */
    private int ngySid__;
    /** プロセスコード */
    private String ngpCode__ = null;
    /** プロセス名 */
    private String ngpName__ = null;

    /** 並び順 */
    private int ngpSort__;
    /** 表示順(画面用) */
    private String ngpValue__ = null;

    /**
     * @return ngyName
     */
    public String getNgyName() {
        return ngyName__;
    }
    /**
     * @param ngyName 設定する ngyName
     */
    public void setNgyName(String ngyName) {
        ngyName__ = ngyName;
    }
    /**
     * <p>ngpSid を取得します。
     * @return ngpSid
     */
    public int getNgpSid() {
        return ngpSid__;
    }
    /**
     * <p>ngpSid をセットします。
     * @param ngpSid ngpSid
     */
    public void setNgpSid(int ngpSid) {
        ngpSid__ = ngpSid;
    }
    /**
     * <p>ngySid を取得します。
     * @return ngySid
     */
    public int getNgySid() {
        return ngySid__;
    }
    /**
     * <p>ngySid をセットします。
     * @param ngySid ngySid
     */
    public void setNgySid(int ngySid) {
        ngySid__ = ngySid;
    }
    /**
     * <p>ngpCode を取得します。
     * @return ngpCode
     */
    public String getNgpCode() {
        return ngpCode__;
    }
    /**
     * <p>ngpCode をセットします。
     * @param ngpCode ngpCode
     */
    public void setNgpCode(String ngpCode) {
        ngpCode__ = ngpCode;
    }
    /**
     * <p>ngpName を取得します。
     * @return ngpName
     */
    public String getNgpName() {
        return ngpName__;
    }
    /**
     * <p>ngpName をセットします。
     * @param ngpName ngpName
     */
    public void setNgpName(String ngpName) {
        ngpName__ = ngpName;
    }
    /**
     * <p>ngpSort を取得します。
     * @return ngpSort
     */
    public int getNgpSort() {
        return ngpSort__;
    }
    /**
     * <p>ngpSort をセットします。
     * @param ngpSort ngpSort
     */
    public void setNgpSort(int ngpSort) {
        ngpSort__ = ngpSort;
    }
    /**
     * <p>ngpValue を取得します。
     * @return ngpValue
     */
    public String getNgpValue() {
        return ngpValue__;
    }
    /**
     * <p>ngpValue をセットします。
     * @param ngpValue ngpValue
     */
    public void setNgpValue(String ngpValue) {
        ngpValue__ = ngpValue;
    }
}
