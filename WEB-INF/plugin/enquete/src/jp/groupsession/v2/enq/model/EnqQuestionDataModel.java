package jp.groupsession.v2.enq.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アンケートの設問データモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqQuestionDataModel extends AbstractModel {

    // 設問_基本情報
    /** アンケートSID */
    private long emnSid__;
    /** EQM_SEQ mapping */
    private int eqmSeq__;
    /** EQM_DSP_SEC mapping */
    private int eqmDspSec__;
    /** EQM_QUE_SEC mapping */
    private String eqmQueSec__;
    /** EQM_QUESTION mapping */
    private String eqmQuestion__;
    /** EQM_QUE_KBN mapping */
    private int eqmQueKbn__;
    /** EQM_REQUIRE mapping */
    private int eqmRequire__;
    /** EQM_OTHER mapping */
    private int eqmOther__;
    /** EQM_DESC mapping */
    private String eqmDesc__;
    /** EQM_ATTACH_KBN mapping */
    private int eqmAttachKbn__;
    /** EQM_ATTACH_ID mapping */
    private String eqmAttachId__;
    /** EQM_ATTACH_NAME mapping */
    private String eqmAttachName__;
    /** EQM_ATTACH_POS mapping */
    private int eqmAttachPos__;
    /** EQM_LINE_KBN mapping */
    private int eqmLineKbn__;
    /** EQM_GRF_KBN mapping */
    private int eqmGrfKbn__;

    // 設問_サブ情報
    /** EQS_SEQ mapping */
    private int eqsSeq__;
    /** EQS_DSP_SEC mapping */
    private int eqsDspSec__;
    /** EQS_DSP_NAME mapping */
    private String eqsDspName__;
    /** EQS_DEF_TXT mapping */
    private String eqsDefTxt__;
    /** EQS_DEF_NUM mapping */
    private Long eqsDefNum__;
    /** EQS_DEF_DAT mapping */
    private UDate eqsDefDat__;
    /** EQS_DEF mapping */
    private String eqsDef__;
    /** EQS_RNG_STR_NUM mapping */
    private String eqsRngStrNum__;
    /** EQS_RNG_END_NUM mapping */
    private String eqsRngEndNum__;
    /** EQS_RNG_STR_DAT mapping */
    private UDate eqsRngStrDat__;
    /** EQS_RNG_END_DAT mapping */
    private UDate eqsRngEndDat__;
    /** EQS_UNIT_NUM mapping */
    private String eqsUnitNum__;

    // 回答_サブ
    /** EAS_ANS_TXT mapping */
    private String easAnsTxt__;
    /** EAS_ANS_NUM mapping */
    private long easAnsNum__;
    /** EAS_ANS_DAT mapping */
    private UDate easAnsDat__;
    /** EAS_ANS mapping */
    private String easAns__;

    /**
     * <p>easAnsTxt を取得します。
     * @return easAnsTxt
     */
    public String getEasAnsTxt() {
        return easAnsTxt__;
    }

    /**
     * <p>easAnsTxt をセットします。
     * @param easAnsTxt easAnsTxt
     */
    public void setEasAnsTxt(String easAnsTxt) {
        easAnsTxt__ = easAnsTxt;
    }

    /**
     * <p>easAnsNum を取得します。
     * @return easAnsNum
     */
    public Long getEasAnsNum() {
        return easAnsNum__;
    }

    /**
     * <p>easAnsNum をセットします。
     * @param easAnsNum easAnsNum
     */
    public void setEasAnsNum(Long easAnsNum) {
        easAnsNum__ = easAnsNum;
    }

    /**
     * <p>easAnsDat を取得します。
     * @return easAnsDat
     */
    public UDate getEasAnsDat() {
        return easAnsDat__;
    }

    /**
     * <p>easAnsDat をセットします。
     * @param easAnsDat easAnsDat
     */
    public void setEasAnsDat(UDate easAnsDat) {
        easAnsDat__ = easAnsDat;
    }

    /**
     * <p>easAns を取得します。
     * @return easAns
     */
    public String getEasAns() {
        return easAns__;
    }

    /**
     * <p>easAns をセットします。
     * @param easAns easAns
     */
    public void setEasAns(String easAns) {
        easAns__ = easAns;
    }

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
     * <p>eqmDspSec を取得します。
     * @return eqmDspSec
     */
    public int getEqmDspSec() {
        return eqmDspSec__;
    }

    /**
     * <p>eqmDspSec をセットします。
     * @param eqmDspSec eqmDspSec
     */
    public void setEqmDspSec(int eqmDspSec) {
        eqmDspSec__ = eqmDspSec;
    }

    /**
     * <p>eqmQueSec を取得します。
     * @return eqmQueSec
     */
    public String getEqmQueSec() {
        return eqmQueSec__;
    }

    /**
     * <p>eqmQueSec をセットします。
     * @param eqmQueSec eqmQueSec
     */
    public void setEqmQueSec(String eqmQueSec) {
        eqmQueSec__ = eqmQueSec;
    }

    /**
     * <p>eqmQuestion を取得します。
     * @return eqmQuestion
     */
    public String getEqmQuestion() {
        return eqmQuestion__;
    }

    /**
     * <p>eqmQuestion をセットします。
     * @param eqmQuestion eqmQuestion
     */
    public void setEqmQuestion(String eqmQuestion) {
        eqmQuestion__ = eqmQuestion;
    }

    /**
     * <p>eqmQueKbn を取得します。
     * @return eqmQueKbn
     */
    public int getEqmQueKbn() {
        return eqmQueKbn__;
    }

    /**
     * <p>eqmQueKbn をセットします。
     * @param eqmQueKbn eqmQueKbn
     */
    public void setEqmQueKbn(int eqmQueKbn) {
        eqmQueKbn__ = eqmQueKbn;
    }

    /**
     * <p>eqmRequire を取得します。
     * @return eqmRequire
     */
    public int getEqmRequire() {
        return eqmRequire__;
    }

    /**
     * <p>eqmRequire をセットします。
     * @param eqmRequire eqmRequire
     */
    public void setEqmRequire(int eqmRequire) {
        eqmRequire__ = eqmRequire;
    }

    /**
     * <p>eqmOther を取得します。
     * @return eqmOther
     */
    public int getEqmOther() {
        return eqmOther__;
    }

    /**
     * <p>eqmOther をセットします。
     * @param eqmOther eqmOther
     */
    public void setEqmOther(int eqmOther) {
        eqmOther__ = eqmOther;
    }

    /**
     * <p>eqmDesc を取得します。
     * @return eqmDesc
     */
    public String getEqmDesc() {
        return eqmDesc__;
    }

    /**
     * <p>eqmDesc をセットします。
     * @param eqmDesc eqmDesc
     */
    public void setEqmDesc(String eqmDesc) {
        eqmDesc__ = eqmDesc;
    }

    /**
     * <p>eqmAttachKbn を取得します。
     * @return eqmAttachKbn
     */
    public int getEqmAttachKbn() {
        return eqmAttachKbn__;
    }

    /**
     * <p>eqmAttachKbn をセットします。
     * @param eqmAttachKbn eqmAttachKbn
     */
    public void setEqmAttachKbn(int eqmAttachKbn) {
        eqmAttachKbn__ = eqmAttachKbn;
    }

    /**
     * <p>eqmAttachId を取得します。
     * @return eqmAttachId
     */
    public String getEqmAttachId() {
        return eqmAttachId__;
    }

    /**
     * <p>eqmAttachId をセットします。
     * @param eqmAttachId eqmAttachId
     */
    public void setEqmAttachId(String eqmAttachId) {
        eqmAttachId__ = eqmAttachId;
    }

    /**
     * <p>eqmAttachName を取得します。
     * @return eqmAttachName
     */
    public String getEqmAttachName() {
        return eqmAttachName__;
    }

    /**
     * <p>eqmAttachName をセットします。
     * @param eqmAttachName eqmAttachName
     */
    public void setEqmAttachName(String eqmAttachName) {
        eqmAttachName__ = eqmAttachName;
    }

    /**
     * <p>eqmAttachPos を取得します。
     * @return eqmAttachPos
     */
    public int getEqmAttachPos() {
        return eqmAttachPos__;
    }

    /**
     * <p>eqmAttachPos をセットします。
     * @param eqmAttachPos eqmAttachPos
     */
    public void setEqmAttachPos(int eqmAttachPos) {
        eqmAttachPos__ = eqmAttachPos;
    }

    /**
     * <p>eqmLineKbn を取得します。
     * @return eqmLineKbn
     */
    public int getEqmLineKbn() {
        return eqmLineKbn__;
    }

    /**
     * <p>eqmLineKbn をセットします。
     * @param eqmLineKbn eqmLineKbn
     */
    public void setEqmLineKbn(int eqmLineKbn) {
        eqmLineKbn__ = eqmLineKbn;
    }

    /**
     * <p>eqmGrfKbn を取得します。
     * @return eqmGrfKbn
     */
    public int getEqmGrfKbn() {
        return eqmGrfKbn__;
    }

    /**
     * <p>eqmGrfKbn をセットします。
     * @param eqmGrfKbn eqmGrfKbn
     */
    public void setEqmGrfKbn(int eqmGrfKbn) {
        eqmGrfKbn__ = eqmGrfKbn;
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
    public Long getEqsDefNum() {
        return eqsDefNum__;
    }

    /**
     * <p>eqsDefNum をセットします。
     * @param eqsDefNum eqsDefNum
     */
    public void setEqsDefNum(Long eqsDefNum) {
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

    /**
     * <p>eqsRngStrNum を取得します。
     * @return eqsRngStrNum
     */
    public String getEqsRngStrNum() {
        return eqsRngStrNum__;
    }

    /**
     * <p>eqsRngStrNum をセットします。
     * @param eqsRngStrNum eqsRngStrNum
     */
    public void setEqsRngStrNum(String eqsRngStrNum) {
        eqsRngStrNum__ = eqsRngStrNum;
    }

    /**
     * <p>eqsRngEndNum を取得します。
     * @return eqsRngEndNum
     */
    public String getEqsRngEndNum() {
        return eqsRngEndNum__;
    }

    /**
     * <p>eqsRngEndNum をセットします。
     * @param eqsRngEndNum eqsRngEndNum
     */
    public void setEqsRngEndNum(String eqsRngEndNum) {
        eqsRngEndNum__ = eqsRngEndNum;
    }

    /**
     * <p>eqsRngStrDat を取得します。
     * @return eqsRngStrDat
     */
    public UDate getEqsRngStrDat() {
        return eqsRngStrDat__;
    }

    /**
     * <p>eqsRngStrDat をセットします。
     * @param eqsRngStrDat eqsRngStrDat
     */
    public void setEqsRngStrDat(UDate eqsRngStrDat) {
        eqsRngStrDat__ = eqsRngStrDat;
    }

    /**
     * <p>eqsRngEndDat を取得します。
     * @return eqsRngEndDat
     */
    public UDate getEqsRngEndDat() {
        return eqsRngEndDat__;
    }

    /**
     * <p>eqsRngEndDat をセットします。
     * @param eqsRngEndDat eqsRngEndDat
     */
    public void setEqsRngEndDat(UDate eqsRngEndDat) {
        eqsRngEndDat__ = eqsRngEndDat;
    }

    /**
     * <p>eqsUnitNum を取得します。
     * @return eqsUnitNum
     */
    public String getEqsUnitNum() {
        return eqsUnitNum__;
    }

    /**
     * <p>eqsUnitNum をセットします。
     * @param eqsUnitNum eqsUnitNum
     */
    public void setEqsUnitNum(String eqsUnitNum) {
        eqsUnitNum__ = eqsUnitNum;
    }
}
