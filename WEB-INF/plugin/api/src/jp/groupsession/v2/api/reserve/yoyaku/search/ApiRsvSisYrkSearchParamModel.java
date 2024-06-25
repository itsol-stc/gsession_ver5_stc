package jp.groupsession.v2.api.reserve.yoyaku.search;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiRsvSisYrkSearchParamModel extends AbstractParamModel {
    /** 施設グループSID */
    private String rsgSid__;
    /** 施設SID */
    private String rsdSid__;
    /** 開始日 */
    private String startTime__;
    /** 終了日 */
    private String endTime__;
    /** 登録日FROM */
    private String addTimeFrom__;
    /** 登録日TO */
    private String addTimeTo__;
    /** 編集日FROM */
    private String editTimeFrom__;
    /** 編集日TO */
    private String editTimeTo__;
    /** 承認状況 */
    private String statusKbn__;

    /** キーワード */
    private String keyWord__;
    /** キーワードand or */
    private String keyWordKbn__;
    /** キーワード対象 利用目的 */
    private String keytitle__;
    /** キーワード対象 内容・備考 */
    private String keybody__;
    /** ソート1キー */
    private String sort1__ = "1";

    /** ソート1昇順降順 */
    private String order1__;
    /** ソート2キー */
    private String sort2__ = "2";

    /** ソート2昇順降順 */
    private String order2__ = "0";
    /** 結果を取得する件数 */
    private String results__ = "50";
    /** 取得開始位置 */
    private String start__ = "0";
    /**
     * <p>rsgSid を取得します。
     * @return rsgSid
     */
    public String getRsgSid() {
        return rsgSid__;
    }
    /**
     * <p>rsgSid をセットします。
     * @param rsgSid rsgSid
     */
    public void setRsgSid(String rsgSid) {
        rsgSid__ = rsgSid;
    }
    /**
     * <p>rsdSid を取得します。
     * @return rsdSid
     */
    public String getRsdSid() {
        return rsdSid__;
    }
    /**
     * <p>rsdSid をセットします。
     * @param rsdSid rsdSid
     */
    public void setRsdSid(String rsdSid) {
        rsdSid__ = rsdSid;
    }
    /**
     * <p>startTime を取得します。
     * @return startTime
     */
    public String getStartTime() {
        return startTime__;
    }
    /**
     * <p>startTime をセットします。
     * @param startTime startTime
     */
    public void setStartTime(String startTime) {
        startTime__ = startTime;
    }
    /**
     * <p>endTime を取得します。
     * @return endTime
     */
    public String getEndTime() {
        return endTime__;
    }
    /**
     * <p>endTime をセットします。
     * @param endTime endTime
     */
    public void setEndTime(String endTime) {
        endTime__ = endTime;
    }
    /**
     * <p>addTimeFrom を取得します。
     * @return addTimeFrom
     */
    public String getAddTimeFrom() {
        return addTimeFrom__;
    }
    /**
     * <p>addTimeFrom をセットします。
     * @param addTimeFrom addTimeFrom
     */
    public void setAddTimeFrom(String addTimeFrom) {
        addTimeFrom__ = addTimeFrom;
    }
    /**
     * <p>addTimeTo を取得します。
     * @return addTimeTo
     */
    public String getAddTimeTo() {
        return addTimeTo__;
    }
    /**
     * <p>addTimeTo をセットします。
     * @param addTimeTo addTimeTo
     */
    public void setAddTimeTo(String addTimeTo) {
        addTimeTo__ = addTimeTo;
    }
    /**
     * <p>editTimeFrom を取得します。
     * @return editTimeFrom
     */
    public String getEditTimeFrom() {
        return editTimeFrom__;
    }
    /**
     * <p>editTimeFrom をセットします。
     * @param editTimeFrom editTimeFrom
     */
    public void setEditTimeFrom(String editTimeFrom) {
        editTimeFrom__ = editTimeFrom;
    }
    /**
     * <p>editTimeTo を取得します。
     * @return editTimeTo
     */
    public String getEditTimeTo() {
        return editTimeTo__;
    }
    /**
     * <p>editTimeTo をセットします。
     * @param editTimeTo editTimeTo
     */
    public void setEditTimeTo(String editTimeTo) {
        editTimeTo__ = editTimeTo;
    }
    /**
     * <p>status を取得します。
     * @return status
     */
    public String getStatusKbn() {
        return statusKbn__;
    }
    /**
     * <p>status をセットします。
     * @param statusKbn status
     */
    public void setStatusKbn(String statusKbn) {
        statusKbn__ = statusKbn;
    }
    /**
     * <p>keyWord を取得します。
     * @return keyWord
     */
    public String getKeyWord() {
        return keyWord__;
    }
    /**
     * <p>keyWord をセットします。
     * @param keyWord keyWord
     */
    public void setKeyWord(String keyWord) {
        keyWord__ = keyWord;
    }
    /**
     * <p>keyWordKbn を取得します。
     * @return keyWordKbn
     */
    public String getKeyWordKbn() {
        return keyWordKbn__;
    }
    /**
     * <p>keyWordKbn をセットします。
     * @param keyWordKbn keyWordKbn
     */
    public void setKeyWordKbn(String keyWordKbn) {
        keyWordKbn__ = keyWordKbn;
    }
    /**
     * <p>keytitle を取得します。
     * @return keytitle
     */
    public String getKeytitle() {
        return keytitle__;
    }
    /**
     * <p>keytitle をセットします。
     * @param keytitle keytitle
     */
    public void setKeytitle(String keytitle) {
        keytitle__ = keytitle;
    }
    /**
     * <p>keybody を取得します。
     * @return keybody
     */
    public String getKeybody() {
        return keybody__;
    }
    /**
     * <p>keybody をセットします。
     * @param keybody keybody
     */
    public void setKeybody(String keybody) {
        keybody__ = keybody;
    }
    /**
     * <p>sort1 を取得します。
     * @return sort1
     */
    public String getSort1() {
        return sort1__;
    }
    /**
     * <p>sort1 をセットします。
     * @param sort1 sort1
     */
    public void setSort1(String sort1) {
        sort1__ = sort1;
    }
    /**
     * <p>order1 を取得します。
     * @return order1
     */
    public String getOrder1() {
        return order1__;
    }
    /**
     * <p>order1 をセットします。
     * @param order1 order1
     */
    public void setOrder1(String order1) {
        order1__ = order1;
    }
    /**
     * <p>sort2 を取得します。
     * @return sort2
     */
    public String getSort2() {
        return sort2__;
    }
    /**
     * <p>sort2 をセットします。
     * @param sort2 sort2
     */
    public void setSort2(String sort2) {
        sort2__ = sort2;
    }
    /**
     * <p>order2 を取得します。
     * @return order2
     */
    public String getOrder2() {
        return order2__;
    }
    /**
     * <p>order2 をセットします。
     * @param order2 order2
     */
    public void setOrder2(String order2) {
        order2__ = order2;
    }
    /**
     * <p>results を取得します。
     * @return results
     */
    public String getResults() {
        return results__;
    }
    /**
     * <p>results をセットします。
     * @param results results
     */
    public void setResults(String results) {
        results__ = results;
    }
    /**
     * <p>start を取得します。
     * @return start
     */
    public String getStart() {
        return start__;
    }
    /**
     * <p>start をセットします。
     * @param start start
     */
    public void setStart(String start) {
        start__ = start;
    }

}
