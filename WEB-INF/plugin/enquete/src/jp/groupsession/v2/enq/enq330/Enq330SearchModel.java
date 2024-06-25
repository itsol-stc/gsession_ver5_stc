package jp.groupsession.v2.enq.enq330;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能]  アンケート 結果確認（詳細）画面の検索条件を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq330SearchModel extends AbstractModel {
    /** ページ */
    private int page__ = 0;
    /** 1ページの最大件数 */
    private int maxCount__ = 0;
    /** ソートキー */
    private int sortKey__ = 0;
    /** 並び順 */
    private int order__ = 0;
    /** アンケートSID */
    private long emnSid__ = 0;
    /** 設問連番 */
    private int eqmSeq__ = 0;
    /** アンケート 種別 */
    private int queKbn__ = 0;
    /** グループ */
    private int group__ = -1;
    /** ユーザ */
    private int user__ = -1;
    /** 回答(テキスト) */
    private String ansText__ = null;
    /** 回答(数値・日付) 区分 */
    private int ansNumKbn__ = 0;
    /** 回答(数値) From */
    private String ansNumFrom__ = null;
    /** 回答(数値) To */
    private String ansNumTo__ = null;
    /** 回答(日付) From */
    private UDate ansDateFrom__ = null;
    /** 回答(日付) To */
    private UDate ansDateTo__ = null;
    /** 回答(選択肢) */
    private int[] ans__ = null;
    /**
     * <p>page を取得します。
     * @return page
     */
    public int getPage() {
        return page__;
    }
    /**
     * <p>page をセットします。
     * @param page page
     */
    public void setPage(int page) {
        page__ = page;
    }
    /**
     * <p>maxCount を取得します。
     * @return maxCount
     */
    public int getMaxCount() {
        return maxCount__;
    }
    /**
     * <p>maxCount をセットします。
     * @param maxCount maxCount
     */
    public void setMaxCount(int maxCount) {
        maxCount__ = maxCount;
    }
    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public int getSortKey() {
        return sortKey__;
    }
    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }
    /**
     * <p>order を取得します。
     * @return order
     */
    public int getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(int order) {
        order__ = order;
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
     * <p>group を取得します。
     * @return group
     */
    public int getGroup() {
        return group__;
    }
    /**
     * <p>group をセットします。
     * @param group group
     */
    public void setGroup(int group) {
        group__ = group;
    }
    /**
     * <p>user を取得します。
     * @return user
     */
    public int getUser() {
        return user__;
    }
    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(int user) {
        user__ = user;
    }
    /**
     * <p>ansText を取得します。
     * @return ansText
     */
    public String getAnsText() {
        return ansText__;
    }
    /**
     * <p>ansText をセットします。
     * @param ansText ansText
     */
    public void setAnsText(String ansText) {
        ansText__ = ansText;
    }
    /**
     * <p>ansNumKbn を取得します。
     * @return ansNumKbn
     */
    public int getAnsNumKbn() {
        return ansNumKbn__;
    }
    /**
     * <p>ansNumKbn をセットします。
     * @param ansNumKbn ansNumKbn
     */
    public void setAnsNumKbn(int ansNumKbn) {
        ansNumKbn__ = ansNumKbn;
    }
    /**
     * <p>ansNumFrom を取得します。
     * @return ansNumFrom
     */
    public String getAnsNumFrom() {
        return ansNumFrom__;
    }
    /**
     * <p>ansNumFrom をセットします。
     * @param ansNumFrom ansNumFrom
     */
    public void setAnsNumFrom(String ansNumFrom) {
        ansNumFrom__ = ansNumFrom;
    }
    /**
     * <p>ansNumTo を取得します。
     * @return ansNumTo
     */
    public String getAnsNumTo() {
        return ansNumTo__;
    }
    /**
     * <p>ansNumTo をセットします。
     * @param ansNumTo ansNumTo
     */
    public void setAnsNumTo(String ansNumTo) {
        ansNumTo__ = ansNumTo;
    }
    /**
     * <p>ansDateFrom を取得します。
     * @return ansDateFrom
     */
    public UDate getAnsDateFrom() {
        return ansDateFrom__;
    }
    /**
     * <p>ansDateFrom をセットします。
     * @param ansDateFrom ansDateFrom
     */
    public void setAnsDateFrom(UDate ansDateFrom) {
        ansDateFrom__ = ansDateFrom;
    }
    /**
     * <p>ansDateTo を取得します。
     * @return ansDateTo
     */
    public UDate getAnsDateTo() {
        return ansDateTo__;
    }
    /**
     * <p>ansDateTo をセットします。
     * @param ansDateTo ansDateTo
     */
    public void setAnsDateTo(UDate ansDateTo) {
        ansDateTo__ = ansDateTo;
    }
    /**
     * <p>ans を取得します。
     * @return ans
     */
    public int[] getAns() {
        return ans__;
    }
    /**
     * <p>ans をセットします。
     * @param ans ans
     */
    public void setAns(int[] ans) {
        ans__ = ans;
    }
}
