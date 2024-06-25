package jp.groupsession.v2.enq.csv;

import java.util.ArrayList;

/**
*
* <br>[機  能]アンケート CSVエクスポート用SubModelクラス
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class EnqCsvSubModel {

    /** アンケートSID */
    private long enqCsvSid__ = -1;
    /** アンケートタイトル */
    private String enqTitle__ = null;
    /** ヘッダー 設問 */
    private ArrayList<String> question__ = null;
    /** 匿名フラグ */
    private int anonyFlg__ = 0;

    /**
     * <p>enqCsvSid を取得します。
     * @return enqCsvSid
     */
    public long getEnqCsvSid() {
        return enqCsvSid__;
    }

    /**
     * <p>enqCsvSid をセットします。
     * @param enqCsvSid enqCsvSid
     */
    public void setEnqCsvSid(long enqCsvSid) {
        enqCsvSid__ = enqCsvSid;
    }
    /**
     * <p>enqTitle を取得します。
     * @return enqTitle
     */
    public String getEnqTitle() {
        return enqTitle__;
    }

    /**
     * <p>enqTitle をセットします。
     * @param enqTitle enqTitle
     */
    public void setEnqTitle(String enqTitle) {
        enqTitle__ = enqTitle;
    }

    /**
     * <p>question を取得します。
     * @return question
     */
    public ArrayList<String> getQuestion() {
        return question__;
    }

    /**
     * <p>question をセットします。
     * @param question question
     */
    public void setQuestion(ArrayList<String> question) {
        question__ = question;
    }

    /**
     * <p>anonyFlg を取得します。
     * @return anonyFlg
     */
    public int getAnonyFlg() {
        return anonyFlg__;
    }

    /**
     * <p>anonyFlg をセットします。
     * @param anonyFlg anonyFlg
     */
    public void setAnonyFlg(int anonyFlg) {
        anonyFlg__ = anonyFlg;
    }

}
