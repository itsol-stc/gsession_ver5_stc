package jp.groupsession.v2.enq.model;

import jp.groupsession.v2.cmn.model.AbstractModel;


/**
 * <br>[機  能] アンケート種類一覧モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqTypeListModel extends AbstractModel {

    /** アンケート種類SID */
    private long etpSid__ = -1;
    /** 表示順 */
    private int etpDspSeq__ = -1;
    /** 種類名 */
    private String etpName__ = null;
    /** 件数 */
    private int emnCnt__ = -1;
    /** 直近の回答期限 */
    private String emnResEnd__ = null;
    /** 直近の公開期限 */
    private String emnOpenEnd__ = null;

    /**
     * <p>アンケート種類SID を取得します。
     * @return アンケート種類SID
     */
    public long getEtpSid() {
        return etpSid__;
    }
    /**
     * <p>アンケート種類SID をセットします。
     * @param etpSid アンケート種類SID
     */
    public void setEtpSid(long etpSid) {
        etpSid__ = etpSid;
    }
    /**
     * <p>表示順 を取得します。
     * @return 表示順
     */
    public int getEtpDspSeq() {
        return etpDspSeq__;
    }
    /**
     * <p>表示順 をセットします。
     * @param etpDspSeq 表示順
     */
    public void setEtpDspSeq(int etpDspSeq) {
        etpDspSeq__ = etpDspSeq;
    }
    /**
     * <p>種類名 を取得します。
     * @return 種類名
     */
    public String getEtpName() {
        return etpName__;
    }
    /**
     * <p>種類名 をセットします。
     * @param etpName 種類名
     */
    public void setEtpName(String etpName) {
        etpName__ = etpName;
    }
    /**
     * <p>件数 を取得します。
     * @return 件数
     */
    public int getEmnCnt() {
        return emnCnt__;
    }
    /**
     * <p>件数 をセットします。
     * @param emnCnt 件数
     */
    public void setEmnCnt(int emnCnt) {
        emnCnt__ = emnCnt;
    }
    /**
     * <p>直近の回答期限 を取得します。
     * @return 直近の回答期限
     */
    public String getEmnResEnd() {
        return emnResEnd__;
    }
    /**
     * <p>直近の回答期限 をセットします。
     * @param emnResEnd 直近の回答期限
     */
    public void setEmnResEnd(String emnResEnd) {
        emnResEnd__ = emnResEnd;
    }
    /**
     * <p>直近の公開期限 を取得します。
     * @return 直近の公開期限
     */
    public String getEmnOpenEnd() {
        return emnOpenEnd__;
    }
    /**
     * <p>直近の公開期限 をセットします。
     * @param emnOpenEnd 直近の公開期限
     */
    public void setEmnOpenEnd(String emnOpenEnd) {
        emnOpenEnd__ = emnOpenEnd;
    }
}
