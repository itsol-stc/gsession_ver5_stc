package jp.groupsession.v2.ptl.ptl120;

import jp.groupsession.v2.ptl.ptl110.Ptl110ParamModel;

/**
 * <br>[機  能] ポータル ポートレットカテゴリ登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl120ParamModel extends Ptl110ParamModel {
    /** 初期表示フラグ */
    private int ptl120init__ = 0;
    /** カテゴリ名 */
    private String ptl120name__ = "";
    /** 備考 */
    private String ptl120biko__ = "";
    /** ポートレットカテゴリSID */
    private int ptlPtlCategorytSid__ = -1;
    /** 遷移元画面 0=ポートレット管理 1=ポートレットカテゴリ一覧 */
    private int ptlPlcBack__ = 0;
    /**
     * @return ptl120init
     */
    public int getPtl120init() {
        return ptl120init__;
    }
    /**
     * @param ptl120init セットする ptl120init
     */
    public void setPtl120init(int ptl120init) {
        ptl120init__ = ptl120init;
    }
    /**
     * @return ptl120name
     */
    public String getPtl120name() {
        return ptl120name__;
    }
    /**
     * @param ptl120name セットする ptl120name
     */
    public void setPtl120name(String ptl120name) {
        ptl120name__ = ptl120name;
    }
    /**
     * @return ptl120biko
     */
    public String getPtl120biko() {
        return ptl120biko__;
    }
    /**
     * @param ptl120biko セットする ptl120biko
     */
    public void setPtl120biko(String ptl120biko) {
        ptl120biko__ = ptl120biko;
    }
    /**
     * @return ptlPtlCategorytSid
     */
    public int getPtlPtlCategorytSid() {
        return ptlPtlCategorytSid__;
    }
    /**
     * @param ptlPtlCategorytSid セットする ptlPtlCategorytSid
     */
    public void setPtlPtlCategorytSid(int ptlPtlCategorytSid) {
        ptlPtlCategorytSid__ = ptlPtlCategorytSid;
    }
    /**
     * @return ptlPlcBack
     */
    public int getPtlPlcBack() {
        return ptlPlcBack__;
    }
    /**
     * @param ptlPlcBack セットする ptlPlcBack
     */
    public void setPtlPlcBack(int ptlPlcBack) {
        ptlPlcBack__ = ptlPlcBack;
    }
}