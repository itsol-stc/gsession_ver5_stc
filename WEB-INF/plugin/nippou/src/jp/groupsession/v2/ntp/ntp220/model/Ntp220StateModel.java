package jp.groupsession.v2.ntp.ntp220.model;

/**
 * <br>[機  能] 状態の件数を格納するモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp220StateModel {
    /** 受注件数 */
    private int jutyuCnt__;
    /** 失注件数 */
    private int sityuCnt__;
    /** 商談中件数 */
    private int syodanCnt__;

    /** 項目SID */
    private int contentSid__;
    /** 項目名 */
    private String contentName__;
    /** 案件数 */
    private int ankenCnt__;

    /**
     * <p>jutyuCnt を取得します。
     * @return jutyuCnt
     */
    public int getJutyuCnt() {
        return jutyuCnt__;
    }
    /**
     * <p>jutyuCnt をセットします。
     * @param jutyuCnt jutyuCnt
     */
    public void setJutyuCnt(int jutyuCnt) {
        jutyuCnt__ = jutyuCnt;
    }
    /**
     * <p>sityuCnt を取得します。
     * @return sityuCnt
     */
    public int getSityuCnt() {
        return sityuCnt__;
    }
    /**
     * <p>sityuCnt をセットします。
     * @param sityuCnt sityuCnt
     */
    public void setSityuCnt(int sityuCnt) {
        sityuCnt__ = sityuCnt;
    }
    /**
     * <p>syodanCnt を取得します。
     * @return syodanCnt
     */
    public int getSyodanCnt() {
        return syodanCnt__;
    }
    /**
     * <p>syodanCnt をセットします。
     * @param syodanCnt syodanCnt
     */
    public void setSyodanCnt(int syodanCnt) {
        syodanCnt__ = syodanCnt;
    }
    /**
     * <p>contentSid を取得します。
     * @return contentSid
     */
    public int getContentSid() {
        return contentSid__;
    }
    /**
     * <p>contentSid をセットします。
     * @param contentSid contentSid
     */
    public void setContentSid(int contentSid) {
        contentSid__ = contentSid;
    }
    /**
     * <p>contentName を取得します。
     * @return contentName
     */
    public String getContentName() {
        return contentName__;
    }
    /**
     * <p>contentName をセットします。
     * @param contentName contentName
     */
    public void setContentName(String contentName) {
        contentName__ = contentName;
    }
    /**
     * <p>ankenCnt を取得します。
     * @return ankenCnt
     */
    public int getAnkenCnt() {
        return ankenCnt__;
    }
    /**
     * <p>ankenCnt をセットします。
     * @param ankenCnt ankenCnt
     */
    public void setAnkenCnt(int ankenCnt) {
        ankenCnt__ = ankenCnt;
    }
}
