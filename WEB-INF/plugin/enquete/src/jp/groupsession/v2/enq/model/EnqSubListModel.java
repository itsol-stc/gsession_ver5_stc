package jp.groupsession.v2.enq.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 設問サブ情報モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqSubListModel extends AbstractModel {

    /** アンケートSID */
    private long emnSid__ = -1;
    /** 設問連番 */
    private int eqmSeq__ = -1;
    /** 設問サブ連番 */
    private int eqsSeq__ = -1;
    /** 表示順 */
    private int eqsDspSec__ = -1;
    /** 表示名 */
    private String eqsDspName__ = null;
    /** 初期値_文字 */
    private String eqsDefTxt__ = null;
    /** 初期値_数値 */
    private String eqsDefNum__ = null;
    /** 初期値_日付 */
    private UDate eqsDefDat__ = null;
    /** 初期値 */
    private String eqsDef__ = null;

    /**
     * <p>emnSid を取得します。
     * @return emnSid
     */
    public long getEmnSid() {
        return emnSid__;
    }
    /**
     * <p>emnSid をセットします。
     * @param emnSid emnSid
     */
    public void setEmnSid(long emnSid) {
        emnSid__ = emnSid;
    }
    /**
     * <p>eqmSeq を取得します。
     * @return eqmSeq
     */
    public int getEqmSeq() {
        return eqmSeq__;
    }
    /**
     * <p>eqmSeq をセットします。
     * @param eqmSeq eqmSeq
     */
    public void setEqmSeq(int eqmSeq) {
        eqmSeq__ = eqmSeq;
    }
    /**
     * <p>eqsSeq を取得します。
     * @return eqsSeq
     */
    public int getEqsSeq() {
        return eqsSeq__;
    }
    /**
     * <p>eqsSeq をセットします。
     * @param eqsSeq eqsSeq
     */
    public void setEqsSeq(int eqsSeq) {
        eqsSeq__ = eqsSeq;
    }
    /**
     * <p>eqsDspSec を取得します。
     * @return eqsDspSec
     */
    public int getEqsDspSec() {
        return eqsDspSec__;
    }
    /**
     * <p>eqsDspSec をセットします。
     * @param eqsDspSec eqsDspSec
     */
    public void setEqsDspSec(int eqsDspSec) {
        eqsDspSec__ = eqsDspSec;
    }
    /**
     * <p>eqsDspName を取得します。
     * @return eqsDspName
     */
    public String getEqsDspName() {
        return eqsDspName__;
    }
    /**
     * <p>eqsDspName をセットします。
     * @param eqsDspName eqsDspName
     */
    public void setEqsDspName(String eqsDspName) {
        eqsDspName__ = eqsDspName;
    }
    /**
     * <p>eqsDefTxt を取得します。
     * @return eqsDefTxt
     */
    public String getEqsDefTxt() {
        return eqsDefTxt__;
    }
    /**
     * <p>eqsDefTxt をセットします。
     * @param eqsDefTxt eqsDefTxt
     */
    public void setEqsDefTxt(String eqsDefTxt) {
        eqsDefTxt__ = eqsDefTxt;
    }
    /**
     * <p>eqsDefNum を取得します。
     * @return eqsDefNum
     */
    public String getEqsDefNum() {
        return eqsDefNum__;
    }
    /**
     * <p>eqsDefNum をセットします。
     * @param eqsDefNum eqsDefNum
     */
    public void setEqsDefNum(String eqsDefNum) {
        eqsDefNum__ = eqsDefNum;
    }
    /**
     * <p>eqsDefDat を取得します。
     * @return eqsDefDat
     */
    public UDate getEqsDefDat() {
        return eqsDefDat__;
    }
    /**
     * <p>eqsDefDat をセットします。
     * @param eqsDefDat eqsDefDat
     */
    public void setEqsDefDat(UDate eqsDefDat) {
        eqsDefDat__ = eqsDefDat;
    }
    /**
     * <p>eqsDef を取得します。
     * @return eqsDef
     */
    public String getEqsDef() {
        return eqsDef__;
    }
    /**
     * <p>eqsDef をセットします。
     * @param eqsDef eqsDef
     */
    public void setEqsDef(String eqsDef) {
        eqsDef__ = eqsDef;
    }
}
