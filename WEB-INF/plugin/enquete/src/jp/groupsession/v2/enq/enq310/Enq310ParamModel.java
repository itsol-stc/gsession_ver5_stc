package jp.groupsession.v2.enq.enq310;

import java.util.List;

import jp.groupsession.v2.enq.enq970.Enq970ParamModel;

/**
 * <br>[機  能] アンケート 結果確認画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq310ParamModel extends Enq970ParamModel {

    /** 設問 */
    private int enq310selectQue__ = 0;
    /** 選択肢 */
    private int enq310selectQueSub__ = 0;
    /** 重要度 */
    private int enq310priority__ = 0;
    /** 発信者 */
    private String enq310sender__ = null;
    /** 発信者 削除フラグ */
    private boolean enq310senderDelFlg__ = false;
    /** 発信者 ログイン停止フラグ */
    private int enq310senderUkoFlg__ = 0;
    /** アンケートタイトル */
    private String enq310enqTitle__ = null;
    /** アンケート内容 */
    private String enq310enqContent__ = null;
    /** 回答期限 */
    private String enq310ansLimitDate__ = null;
    /** 回答期限 曜日 */
    private String enq310ansDayOfWeek__ = null;
    /** 結果公開期限 */
    private String enq310pubLimitDate__ = null;
    /** 結果公開期限 曜日 */
    private String enq310pubDayOfWeek__ = null;
    /** 結果公開期限 開始 */
    private String enq310ansPubFrDate__ = null;
    /** 結果公開期限 開始 曜日 */
    private String enq310ansPubFrDayOfWeek__ = null;
    /** 対象人数 全体 */
    private String enq310answerCountAll__ = null;
    /** 対象人数 回答人数 */
    private String enq310answerCountAr__ = null;
    /** 対象人数 回答人数 割合 */
    private String enq310answerCountArPer__ = null;
    /** 対象人数 未回答人数 */
    private String enq310answerCountUn__ = null;
    /** 対象人数 未回答人数(数値) */
    private String enq310answerCountUnNum__ = null;
    /** 対象人数 回答人数 割合 */
    private String enq310answerCountUnPer__ = null;
    /** 注意事項 */
    private String enq310notes__ = null;
    /** 匿名 */
    private int enq310anony__ = 0;
    /** 回答公開 */
    private int enq310ansOpen__ = 0;
    /** 設問情報 */
    private List<Enq310QuestionModel> queList__ = null;

    /** 一覧初期表示区分 */
    private int enq310viewMode__ = 0;


    /**
     * <p>enq310selectQue を取得します。
     * @return enq310selectQue
     */
    public int getEnq310selectQue() {
        return enq310selectQue__;
    }
    /**
     * <p>enq310selectQue をセットします。
     * @param enq310selectQue enq310selectQue
     */
    public void setEnq310selectQue(int enq310selectQue) {
        enq310selectQue__ = enq310selectQue;
    }
    /**
     * <p>enq310selectQueSub を取得します。
     * @return enq310selectQueSub
     */
    public int getEnq310selectQueSub() {
        return enq310selectQueSub__;
    }
    /**
     * <p>enq310selectQueSub をセットします。
     * @param enq310selectQueSub enq310selectQueSub
     */
    public void setEnq310selectQueSub(int enq310selectQueSub) {
        enq310selectQueSub__ = enq310selectQueSub;
    }
    /**
     * <p>enq310priority を取得します。
     * @return enq310priority
     */
    public int getEnq310priority() {
        return enq310priority__;
    }
    /**
     * <p>enq310priority をセットします。
     * @param enq310priority enq310priority
     */
    public void setEnq310priority(int enq310priority) {
        enq310priority__ = enq310priority;
    }
    /**
     * <p>enq310sender を取得します。
     * @return enq310sender
     */
    public String getEnq310sender() {
        return enq310sender__;
    }
    /**
     * <p>enq310sender をセットします。
     * @param enq310sender enq310sender
     */
    public void setEnq310sender(String enq310sender) {
        enq310sender__ = enq310sender;
    }
    /**
     * <p>enq310senderDelFlg を取得します。
     * @return enq310senderDelFlg
     */
    public boolean isEnq310senderDelFlg() {
        return enq310senderDelFlg__;
    }
    /**
     * <p>enq310senderDelFlg をセットします。
     * @param enq310senderDelFlg enq310senderDelFlg
     */
    public void setEnq310senderDelFlg(boolean enq310senderDelFlg) {
        enq310senderDelFlg__ = enq310senderDelFlg;
    }
    /**
     * <p>enq310enqContent を取得します。
     * @return enq310enqContent
     */
    public String getEnq310enqContent() {
        return enq310enqContent__;
    }
    /**
     * <p>enq310enqContent をセットします。
     * @param enq310enqContent enq310enqContent
     */
    public void setEnq310enqContent(String enq310enqContent) {
        enq310enqContent__ = enq310enqContent;
    }
    /**
     * <p>enq310ansLimitDate を取得します。
     * @return enq310ansLimitDate
     */
    public String getEnq310ansLimitDate() {
        return enq310ansLimitDate__;
    }
    /**
     * <p>enq310ansLimitDate をセットします。
     * @param enq310ansLimitDate enq310ansLimitDate
     */
    public void setEnq310ansLimitDate(String enq310ansLimitDate) {
        enq310ansLimitDate__ = enq310ansLimitDate;
    }
    /**
     * <p>enq310ansDayOfWeek を取得します。
     * @return enq310ansDayOfWeek
     */
    public String getEnq310ansDayOfWeek() {
        return enq310ansDayOfWeek__;
    }
    /**
     * <p>enq310ansDayOfWeek をセットします。
     * @param enq310ansDayOfWeek enq310ansDayOfWeek
     */
    public void setEnq310ansDayOfWeek(String enq310ansDayOfWeek) {
        enq310ansDayOfWeek__ = enq310ansDayOfWeek;
    }
    /**
     * <p>enq310pubLimitDate を取得します。
     * @return enq310pubLimitDate
     */
    public String getEnq310pubLimitDate() {
        return enq310pubLimitDate__;
    }
    /**
     * <p>enq310pubLimitDate をセットします。
     * @param enq310pubLimitDate enq310pubLimitDate
     */
    public void setEnq310pubLimitDate(String enq310pubLimitDate) {
        enq310pubLimitDate__ = enq310pubLimitDate;
    }
    /**
     * <p>enq310pubDayOfWeek を取得します。
     * @return enq310pubDayOfWeek
     */
    public String getEnq310pubDayOfWeek() {
        return enq310pubDayOfWeek__;
    }
    /**
     * <p>enq310pubDayOfWeek をセットします。
     * @param enq310pubDayOfWeek enq310pubDayOfWeek
     */
    public void setEnq310pubDayOfWeek(String enq310pubDayOfWeek) {
        enq310pubDayOfWeek__ = enq310pubDayOfWeek;
    }
    /**
     * <p>enq310ansPubFrDate を取得します。
     * @return enq310ansPubFrDate
     */
    public String getEnq310ansPubFrDate() {
        return enq310ansPubFrDate__;
    }

    /**
     * <p>enq310ansPubFrDate をセットします。
     * @param enq310ansPubFrDate enq310ansPubFrDate
     */
    public void setEnq310ansPubFrDate(String enq310ansPubFrDate) {
        enq310ansPubFrDate__ = enq310ansPubFrDate;
    }
    /**
     * <p>enq310ansPubFrDayOfWeek を取得します。
     * @return enq310ansPubFrDayOfWeek
     */
    public String getEnq310ansPubFrDayOfWeek() {
        return enq310ansPubFrDayOfWeek__;
    }
    /**
     * <p>enq310ansPubFrDayOfWeek をセットします。
     * @param enq310ansPubFrDayOfWeek enq310ansPubFrDayOfWeek
     */
    public void setEnq310ansPubFrDayOfWeek(String enq310ansPubFrDayOfWeek) {
        enq310ansPubFrDayOfWeek__ = enq310ansPubFrDayOfWeek;
    }
    /**
     * <p>enq310answerCountAll を取得します。
     * @return enq310answerCountAll
     */
    public String getEnq310answerCountAll() {
        return enq310answerCountAll__;
    }
    /**
     * <p>enq310answerCountAll をセットします。
     * @param enq310answerCountAll enq310answerCountAll
     */
    public void setEnq310answerCountAll(String enq310answerCountAll) {
        enq310answerCountAll__ = enq310answerCountAll;
    }
    /**
     * <p>enq310answerCountAr を取得します。
     * @return enq310answerCountAr
     */
    public String getEnq310answerCountAr() {
        return enq310answerCountAr__;
    }
    /**
     * <p>enq310answerCountAr をセットします。
     * @param enq310answerCountAr enq310answerCountAr
     */
    public void setEnq310answerCountAr(String enq310answerCountAr) {
        enq310answerCountAr__ = enq310answerCountAr;
    }
    /**
     * <p>enq310answerCountArPer を取得します。
     * @return enq310answerCountArPer
     */
    public String getEnq310answerCountArPer() {
        return enq310answerCountArPer__;
    }
    /**
     * <p>enq310answerCountArPer をセットします。
     * @param enq310answerCountArPer enq310answerCountArPer
     */
    public void setEnq310answerCountArPer(String enq310answerCountArPer) {
        enq310answerCountArPer__ = enq310answerCountArPer;
    }
    /**
     * <p>enq310answerCountUn を取得します。
     * @return enq310answerCountUn
     */
    public String getEnq310answerCountUn() {
        return enq310answerCountUn__;
    }
    /**
     * <p>enq310answerCountUn をセットします。
     * @param enq310answerCountUn enq310answerCountUn
     */
    public void setEnq310answerCountUn(String enq310answerCountUn) {
        enq310answerCountUn__ = enq310answerCountUn;
    }
    /**
     * <p>enq310answerCountUnNum を取得します。
     * @return enq310answerCountUnNum
     */
    public String getEnq310answerCountUnNum() {
        return enq310answerCountUnNum__;
    }
    /**
     * <p>enq310answerCountUnNum をセットします。
     * @param enq310answerCountUnNum enq310answerCountUnNum
     */
    public void setEnq310answerCountUnNum(String enq310answerCountUnNum) {
        enq310answerCountUnNum__ = enq310answerCountUnNum;
    }
    /**
     * <p>enq310answerCountUnPer を取得します。
     * @return enq310answerCountUnPer
     */
    public String getEnq310answerCountUnPer() {
        return enq310answerCountUnPer__;
    }
    /**
     * <p>enq310answerCountUnPer をセットします。
     * @param enq310answerCountUnPer enq310answerCountUnPer
     */
    public void setEnq310answerCountUnPer(String enq310answerCountUnPer) {
        enq310answerCountUnPer__ = enq310answerCountUnPer;
    }
    /**
     * <p>enq310notes を取得します。
     * @return enq310notes
     */
    public String getEnq310notes() {
        return enq310notes__;
    }
    /**
     * <p>enq310notes をセットします。
     * @param enq310notes enq310notes
     */
    public void setEnq310notes(String enq310notes) {
        enq310notes__ = enq310notes;
    }
    /**
     * <p>enq310enqTitle を取得します。
     * @return enq310enqTitle
     */
    public String getEnq310enqTitle() {
        return enq310enqTitle__;
    }
    /**
     * <p>enq310enqTitle をセットします。
     * @param enq310enqTitle enq310enqTitle
     */
    public void setEnq310enqTitle(String enq310enqTitle) {
        enq310enqTitle__ = enq310enqTitle;
    }
    /**
     * <p>enq310anony を取得します。
     * @return enq310anony
     */
    public int getEnq310anony() {
        return enq310anony__;
    }
    /**
     * <p>enq310anony をセットします。
     * @param enq310anony enq310anony
     */
    public void setEnq310anony(int enq310anony) {
        enq310anony__ = enq310anony;
    }
    /**
     * <p>enq310ansOpen を取得します。
     * @return enq310ansOpen
     */
    public int getEnq310ansOpen() {
        return enq310ansOpen__;
    }
    /**
     * <p>enq310ansOpen をセットします。
     * @param enq310ansOpen enq310ansOpen
     */
    public void setEnq310ansOpen(int enq310ansOpen) {
        enq310ansOpen__ = enq310ansOpen;
    }
    /**
     * <p>queList を取得します。
     * @return queList
     */
    public List<Enq310QuestionModel> getQueList() {
        return queList__;
    }
    /**
     * <p>queList をセットします。
     * @param queList queList
     */
    public void setQueList(List<Enq310QuestionModel> queList) {
        queList__ = queList;
    }
    /**
     * <p>enq310viewMode を取得します。
     * @return enq310viewMode
     */
    public int getEnq310viewMode() {
        return enq310viewMode__;
    }
    /**
     * <p>enq310viewMode をセットします。
     * @param enq310viewMode enq310viewMode
     */
    public void setEnq310viewMode(int enq310viewMode) {
        enq310viewMode__ = enq310viewMode;
    }
    /**
     * <p>enq310senderUkoFlg を取得します。
     * @return enq310senderUkoFlg
     */
    public int getEnq310senderUkoFlg() {
        return enq310senderUkoFlg__;
    }
    /**
     * <p>enq310senderUkoFlg をセットします。
     * @param enq310senderUkoFlg enq310senderUkoFlg
     */
    public void setEnq310senderUkoFlg(int enq310senderUkoFlg) {
        enq310senderUkoFlg__ = enq310senderUkoFlg;
    }

}
