package jp.groupsession.v2.enq.enq310;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 設問情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq310QuestionModel extends AbstractModel {
    /** 設問SID */
    private long emnSid__ = 0;
    /** 設問連番 */
    private int queSeq__ = 0;
    /** 設問番号 */
    private String no__ = null;
    /** 必須 */
    private int require__ = 0;
    /** 設問種別 */
    private int queKbn__ = 0;
    /** 設問種別名称 */
    private String queKbnName__ = null;
    /** 設問 */
    private String question__ = null;
    /** 対象人数 回答人数 */
    private String answerCountAr__ = null;
    /** 対象人数 回答人数 割合 */
    private String answerCountArPer__ = null;
    /** 対象人数 未回答人数 */
    private String answerCountUn__ = null;
    /** 対象人数 未回答人数 割合 */
    private String answerCountUnPer__ = null;
    /** 選択肢 */
    List<Enq310QuestionSubModel> subList__ = null;
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
     * <p>no を取得します。
     * @return no
     */
    public String getNo() {
        return no__;
    }
    /**
     * <p>no をセットします。
     * @param no no
     */
    public void setNo(String no) {
        no__ = no;
    }
    /**
     * <p>require を取得します。
     * @return require
     */
    public int getRequire() {
        return require__;
    }
    /**
     * <p>require をセットします。
     * @param require require
     */
    public void setRequire(int require) {
        require__ = require;
    }
    /**
     * <p>queKbn を取得します。
     * @return queKbn
     */
    public int getQueKbn() {
        return queKbn__;
    }
    /**
     * <p>queKbn をセットします。
     * @param queKbn queKbn
     */
    public void setQueKbn(int queKbn) {
        queKbn__ = queKbn;
    }
    /**
     * <p>queKbnName を取得します。
     * @return queKbnName
     */
    public String getQueKbnName() {
        return queKbnName__;
    }
    /**
     * <p>queKbnName をセットします。
     * @param queKbnName queKbnName
     */
    public void setQueKbnName(String queKbnName) {
        queKbnName__ = queKbnName;
    }
    /**
     * <p>question を取得します。
     * @return question
     */
    public String getQuestion() {
        return question__;
    }
    /**
     * <p>question をセットします。
     * @param question question
     */
    public void setQuestion(String question) {
        question__ = question;
    }
    /**
     * <p>answerCountAr を取得します。
     * @return answerCountAr
     */
    public String getAnswerCountAr() {
        return answerCountAr__;
    }
    /**
     * <p>answerCountAr をセットします。
     * @param answerCountAr answerCountAr
     */
    public void setAnswerCountAr(String answerCountAr) {
        answerCountAr__ = answerCountAr;
    }
    /**
     * <p>answerCountArPer を取得します。
     * @return answerCountArPer
     */
    public String getAnswerCountArPer() {
        return answerCountArPer__;
    }
    /**
     * <p>answerCountArPer をセットします。
     * @param answerCountArPer answerCountArPer
     */
    public void setAnswerCountArPer(String answerCountArPer) {
        answerCountArPer__ = answerCountArPer;
    }
    /**
     * <p>answerCountUn を取得します。
     * @return answerCountUn
     */
    public String getAnswerCountUn() {
        return answerCountUn__;
    }
    /**
     * <p>answerCountUn をセットします。
     * @param answerCountUn answerCountUn
     */
    public void setAnswerCountUn(String answerCountUn) {
        answerCountUn__ = answerCountUn;
    }
    /**
     * <p>answerCountUnPer を取得します。
     * @return answerCountUnPer
     */
    public String getAnswerCountUnPer() {
        return answerCountUnPer__;
    }
    /**
     * <p>answerCountUnPer をセットします。
     * @param answerCountUnPer answerCountUnPer
     */
    public void setAnswerCountUnPer(String answerCountUnPer) {
        answerCountUnPer__ = answerCountUnPer;
    }
    /**
     * <p>subList を取得します。
     * @return subList
     */
    public List<Enq310QuestionSubModel> getSubList() {
        return subList__;
    }
    /**
     * <p>subList をセットします。
     * @param subList subList
     */
    public void setSubList(List<Enq310QuestionSubModel> subList) {
        subList__ = subList;
    }
}
