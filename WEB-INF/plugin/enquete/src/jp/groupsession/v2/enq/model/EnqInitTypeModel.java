package jp.groupsession.v2.enq.model;

import java.io.Serializable;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] アンケート種類の取得処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqInitTypeModel implements Serializable {

    /** アンケート種類SID */
    private long etpSid__ = -1;
    /** 表示順 */
    private int etpDspSeq__ = -1;
    /** 種類名 */
    private String etpName__ = null;
    /** 件数 */
    private int emnCnt__ = -1;
    /** 直近の回答期限 */
    private UDate emnResEnd__ = null;
    /** 直近の公開期限 */
    private UDate emnOpenEnd__ = null;

    /**
     * <p>etpSid を取得します。
     * @return etpSid
     */
    public long getEtpSid() {
        return etpSid__;
    }
    /**
     * <p>etpSid をセットします。
     * @param etpSid etpSid
     */
    public void setEtpSid(long etpSid) {
        etpSid__ = etpSid;
    }
    /**
     * <p>etpDspSeq を取得します。
     * @return etpDspSeq
     */
    public int getEtpDspSeq() {
        return etpDspSeq__;
    }
    /**
     * <p>etpDspSeq をセットします。
     * @param etpDspSeq etpDspSeq
     */
    public void setEtpDspSeq(int etpDspSeq) {
        etpDspSeq__ = etpDspSeq;
    }
    /**
     * <p>etpName を取得します。
     * @return etpName
     */
    public String getEtpName() {
        return etpName__;
    }
    /**
     * <p>etpName をセットします。
     * @param etpName etpName
     */
    public void setEtpName(String etpName) {
        etpName__ = etpName;
    }
    /**
     * <p>emnCnt を取得します。
     * @return emnCnt
     */
    public int getEmnCnt() {
        return emnCnt__;
    }
    /**
     * <p>emnCnt をセットします。
     * @param emnCnt emnCnt
     */
    public void setEmnCnt(int emnCnt) {
        emnCnt__ = emnCnt;
    }
    /**
     * <p>emnResEnd を取得します。
     * @return emnResEnd
     */
    public UDate getEmnResEnd() {
        return emnResEnd__;
    }
    /**
     * <p>emnResEnd をセットします。
     * @param emnResEnd emnResEnd
     */
    public void setEmnResEnd(UDate emnResEnd) {
        emnResEnd__ = emnResEnd;
    }
    /**
     * <p>emnOpenEnd を取得します。
     * @return emnOpenEnd
     */
    public UDate getEmnOpenEnd() {
        return emnOpenEnd__;
    }
    /**
     * <p>emnOpenEnd をセットします。
     * @param emnOpenEnd emnOpenEnd
     */
    public void setEmnOpenEnd(UDate emnOpenEnd) {
        emnOpenEnd__ = emnOpenEnd;
    }
}
