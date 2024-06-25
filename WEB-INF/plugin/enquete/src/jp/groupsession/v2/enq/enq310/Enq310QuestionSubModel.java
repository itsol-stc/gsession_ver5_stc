package jp.groupsession.v2.enq.enq310;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 設問 選択肢を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq310QuestionSubModel extends AbstractModel {
    /** 設問SID */
    private long emnSid__ = 0;
    /** 設問連番 */
    private int queSeq__ = 0;
    /** 設問サブ連番 */
    private int queSubSeq__ = 0;
    /** 表示名 */
    private String dspName__ = null;
    /** 対象人数 */
    private String answer__ = null;
    /** 対象人数(数値 */
    private String answerNum__ = null;
    /** 対象人数 割合 全体 */
    private String answerAllPer__ = null;
    /** 対象人数 割合 回答人数 */
    private String answerArPer__ = null;
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
     * <p>queSeq を取得します。
     * @return queSeq
     */
    public int getQueSeq() {
        return queSeq__;
    }
    /**
     * <p>queSeq をセットします。
     * @param queSeq queSeq
     */
    public void setQueSeq(int queSeq) {
        queSeq__ = queSeq;
    }
    /**
     * <p>queSubSeq を取得します。
     * @return queSubSeq
     */
    public int getQueSubSeq() {
        return queSubSeq__;
    }
    /**
     * <p>queSubSeq をセットします。
     * @param queSubSeq queSubSeq
     */
    public void setQueSubSeq(int queSubSeq) {
        queSubSeq__ = queSubSeq;
    }
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
     * <p>answerNum を取得します。
     * @return answerNum
     */
    public String getAnswerNum() {
        return answerNum__;
    }
    /**
     * <p>answerNum をセットします。
     * @param answerNum answerNum
     */
    public void setAnswerNum(String answerNum) {
        answerNum__ = answerNum;
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
     * <br>[機  能] HTMLで表示可能な表示名を取得します
     * <br>[解  説]
     * <br>[備  考]
     * @return ansDspName
     */
    public String getAnsDspName() {
        String ansDspName = "";
        ansDspName = StringUtilHtml.transToHTmlPlusAmparsant(NullDefault.getString(dspName__, ""));
        return ansDspName;
    }
}
