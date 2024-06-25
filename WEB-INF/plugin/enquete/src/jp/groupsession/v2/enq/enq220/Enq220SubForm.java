package jp.groupsession.v2.enq.enq220;

import jp.co.sjts.util.struts.AbstractForm;

/**
 * <br>[機  能] アンケート 設問詳細登録画面の選択肢情報を保持するフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq220SubForm extends AbstractForm {
    /** インデックス */
    private int enqIndex__ = 0;
    /** アンケートSID */
    private long enqSid__ = -1;
    /** 設問連番 */
    private int enqSeq__ = -1;
    /** 設問サブ連番 */
    private int enqSubSeq__ = -1;
    /** 表示順 */
    private int enqDspSec__ = 0;
    /** 表示名 */
    private String enqDspName__ = null;
    /** 初期値フラグ */
    private int enqDefFlg__ = 0;
    /**
     * <p>enqIndex を取得します。
     * @return enqIndex
     */
    public int getEnqIndex() {
        return enqIndex__;
    }
    /**
     * <p>enqIndex をセットします。
     * @param enqIndex enqIndex
     */
    public void setEnqIndex(int enqIndex) {
        enqIndex__ = enqIndex;
    }
    /**
     * <p>enqSid を取得します。
     * @return enqSid
     */
    public long getEnqSid() {
        return enqSid__;
    }
    /**
     * <p>enqSid をセットします。
     * @param enqSid enqSid
     */
    public void setEnqSid(long enqSid) {
        enqSid__ = enqSid;
    }
    /**
     * <p>enqSeq を取得します。
     * @return enqSeq
     */
    public int getEnqSeq() {
        return enqSeq__;
    }
    /**
     * <p>enqSeq をセットします。
     * @param enqSeq enqSeq
     */
    public void setEnqSeq(int enqSeq) {
        enqSeq__ = enqSeq;
    }
    /**
     * <p>enqSubSeq を取得します。
     * @return enqSubSeq
     */
    public int getEnqSubSeq() {
        return enqSubSeq__;
    }
    /**
     * <p>enqSubSeq をセットします。
     * @param enqSubSeq enqSubSeq
     */
    public void setEnqSubSeq(int enqSubSeq) {
        enqSubSeq__ = enqSubSeq;
    }
    /**
     * <p>enqDspSec を取得します。
     * @return enqDspSec
     */
    public int getEnqDspSec() {
        return enqDspSec__;
    }
    /**
     * <p>enqDspSec をセットします。
     * @param enqDspSec enqDspSec
     */
    public void setEnqDspSec(int enqDspSec) {
        enqDspSec__ = enqDspSec;
    }
    /**
     * <p>enqDspName を取得します。
     * @return enqDspName
     */
    public String getEnqDspName() {
        return enqDspName__;
    }
    /**
     * <p>enqDspName をセットします。
     * @param enqDspName enqDspName
     */
    public void setEnqDspName(String enqDspName) {
        enqDspName__ = enqDspName;
    }
    /**
     * <p>enqDefFlg を取得します。
     * @return enqDefFlg
     */
    public int getEnqDefFlg() {
        return enqDefFlg__;
    }
    /**
     * <p>enqDefFlg をセットします。
     * @param enqDefFlg enqDefFlg
     */
    public void setEnqDefFlg(int enqDefFlg) {
        enqDefFlg__ = enqDefFlg;
    }
}
