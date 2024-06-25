package jp.groupsession.v2.cir.pdf;

/**
 * <br>[機  能] 回覧板確認状況のPDF用Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirDtlPdfMemModel {

    /** アカウントSID */
    private int cacSid__;
    /** 社員/職員番号 */
    private String syainNo__;
    /** 氏名 */
    private String cacName__;
    /** 役職 */
    private String posName__;
    /** 確認フラグ */
    private int cvwConf__;
    /** 確認日時(文字列) */
    private String openTime__;
    /** メモ */
    private String cvwMemo__;
    /** 最終更新日時(文字列) */
    private String dspCvwEdate__;

    /**
     * <p>cacSid を取得します。
     * @return cacSid
     */
    public int getCacSid() {
        return cacSid__;
    }

    /**
     * <p>cacSid をセットします。
     * @param cacSid cacSid
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
    }

    /**
     * <p>syainNo を取得します。
     * @return syainNo
     */
    public String getSyainNo() {
        return syainNo__;
    }

    /**
     * <p>syainNo をセットします。
     * @param syainNo syainNo
     */
    public void setSyainNo(String syainNo) {
        syainNo__ = syainNo;
    }

    /**
     * <p>cacName を取得します。
     * @return cacName
     */
    public String getCacName() {
        return cacName__;
    }

    /**
     * <p>cacName をセットします。
     * @param cacName cacName
     */
    public void setCacName(String cacName) {
        cacName__ = cacName;
    }

    /**
     * <p>posName を取得します。
     * @return posName
     */
    public String getPosName() {
        return posName__;
    }

    /**
     * <p>posName をセットします。
     * @param posName posName
     */
    public void setPosName(String posName) {
        posName__ = posName;
    }

    /**
     * <p>cvwConf を取得します。
     * @return cvwConf
     */
    public int getCvwConf() {
        return cvwConf__;
    }

    /**
     * <p>cvwConf をセットします。
     * @param cvwConf cvwConf
     */
    public void setCvwConf(int cvwConf) {
        cvwConf__ = cvwConf;
    }

    /**
     * <p>openTime を取得します。
     * @return openTime
     */
    public String getOpenTime() {
        return openTime__;
    }

    /**
     * <p>openTime をセットします。
     * @param openTime openTime
     */
    public void setOpenTime(String openTime) {
        openTime__ = openTime;
    }

    /**
     * <p>cvwMemo を取得します。
     * @return cvwMemo
     */
    public String getCvwMemo() {
        return cvwMemo__;
    }

    /**
     * <p>cvwMemo をセットします。
     * @param cvwMemo cvwMemo
     */
    public void setCvwMemo(String cvwMemo) {
        cvwMemo__ = cvwMemo;
    }

    /**
     * <p>dspCvwEdate を取得します。
     * @return dspCvwEdate
     */
    public String getDspCvwEdate() {
        return dspCvwEdate__;
    }

    /**
     * <p>dspCvwEdate をセットします。
     * @param dspCvwEdate dspCvwEdate
     */
    public void setDspCvwEdate(String dspCvwEdate) {
        dspCvwEdate__ = dspCvwEdate;
    }

}
