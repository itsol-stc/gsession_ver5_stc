package jp.groupsession.v2.enq.enq330;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 設問 選択肢を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq330QuestionSubModel extends AbstractModel {
    /** 設問サブ連番 */
    private int eqsSeq__ = 0;
    /** 表示名 */
    private String dspName__ = null;
    /** 対象人数 */
    private String answer__ = null;
    /** 対象人数 割合 全体 */
    private String answerAllPer__ = null;
    /** 対象人数 割合 回答人数 */
    private String answerArPer__ = null;
    /**
     * <p>dspName を取得します。
     * @return dspName
     */
    public String getDspName() {
        return dspName__;
    }
    /**
     * <p>dspName をセットします。
     * @param dspName dspName
     */
    public void setDspName(String dspName) {
        dspName__ = dspName;
    }
    /**
     * <p>answer を取得します。
     * @return answer
     */
    public String getAnswer() {
        return answer__;
    }
    /**
     * <p>answer をセットします。
     * @param answer answer
     */
    public void setAnswer(String answer) {
        answer__ = answer;
    }
    /**
     * <p>answerAllPer を取得します。
     * @return answerAllPer
     */
    public String getAnswerAllPer() {
        return answerAllPer__;
    }
    /**
     * <p>answerAllPer をセットします。
     * @param answerAllPer answerAllPer
     */
    public void setAnswerAllPer(String answerAllPer) {
        answerAllPer__ = answerAllPer;
    }
    /**
     * <p>answerArPer を取得します。
     * @return answerArPer
     */
    public String getAnswerArPer() {
        return answerArPer__;
    }
    /**
     * <p>answerArPer をセットします。
     * @param answerArPer answerArPer
     */
    public void setAnswerArPer(String answerArPer) {
        answerArPer__ = answerArPer;
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
}
