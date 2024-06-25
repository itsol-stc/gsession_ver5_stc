package jp.groupsession.v2.cir.model;

import java.io.Serializable;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstCommon;

/**
 * <br>[機  能] 回覧板一覧検索時に必要な情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirSearchModel implements Serializable {

    /** ユーザSID */
    private int cacSid__;
//    /** ユーザSID */
//    private int userSid__;
    /** カーソルスタート位置 */
    private int start__;
    /** 1ページの件数 */
    private int limit__;
    /** オーダーキー1 */
    private int orderKey__;
    /** ソートキー1 */
    private int sortKey__;
    /** オーダーキー2 */
    private int orderKey2__ = GSConstCommon.NUM_INIT;
    /** ソートキー2 */
    private int sortKey2__ = GSConstCommon.NUM_INIT;
    /** グループSID */
    private String groupSid__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** 発信者SID */
    private int hassinSid__ = GSConstCommon.NUM_INIT;
    /** 回覧先 */
    private String[] kairansakiSid__;
    /** キーワード */
    private List<String> keyWord__;
    /** キーワード検索区分(AND OR) */
    private int wordKbn__ = GSConstCommon.NUM_INIT;
    /** 検索対象(タイトル) */
    private boolean targetTitle__ = false;
    /** 検索対象(本文) */
    private boolean targetBody__ = false;
    /** 検索者ユーザSID*/
    private int sessionUserSid__ = GSConstCommon.NUM_INIT;
    /** ラベルSID*/
    private int labelSid__ = -1;

    /**
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public int getOrderKey() {
        return orderKey__;
    }
    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(int orderKey) {
        orderKey__ = orderKey;
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
     * <p>limit を取得します。
     * @return limit
     */
    public int getLimit() {
        return limit__;
    }
    /**
     * <p>limit をセットします。
     * @param limit limit
     */
    public void setLimit(int limit) {
        limit__ = limit;
    }
    /**
     * <p>start を取得します。
     * @return start
     */
    public int getStart() {
        return start__;
    }
    /**
     * <p>start をセットします。
     * @param start start
     */
    public void setStart(int start) {
        start__ = start;
    }

    /**
     * <p>hassinSid を取得します。
     * @return hassinSid
     */
    public int getHassinSid() {
        return hassinSid__;
    }
    /**
     * <p>hassinSid をセットします。
     * @param hassinSid hassinSid
     */
    public void setHassinSid(int hassinSid) {
        hassinSid__ = hassinSid;
    }
    /**
     * <p>orderKey2 を取得します。
     * @return orderKey2
     */
    public int getOrderKey2() {
        return orderKey2__;
    }
    /**
     * <p>orderKey2 をセットします。
     * @param orderKey2 orderKey2
     */
    public void setOrderKey2(int orderKey2) {
        orderKey2__ = orderKey2;
    }
    /**
     * <p>sortKey2 を取得します。
     * @return sortKey2
     */
    public int getSortKey2() {
        return sortKey2__;
    }
    /**
     * <p>sortKey2 をセットします。
     * @param sortKey2 sortKey2
     */
    public void setSortKey2(int sortKey2) {
        sortKey2__ = sortKey2;
    }
    /**
     * <p>wordKbn を取得します。
     * @return wordKbn
     */
    public int getWordKbn() {
        return wordKbn__;
    }
    /**
     * <p>wordKbn をセットします。
     * @param wordKbn wordKbn
     */
    public void setWordKbn(int wordKbn) {
        wordKbn__ = wordKbn;
    }
    /**
     * <p>keyWord を取得します。
     * @return keyWord
     */
    public List<String> getKeyWord() {
        return keyWord__;
    }
    /**
     * <p>keyWord をセットします。
     * @param keyWord keyWord
     */
    public void setKeyWord(List<String> keyWord) {
        keyWord__ = keyWord;
    }
    /**
     * <p>targetBody を取得します。
     * @return targetBody
     */
    public boolean isTargetBody() {
        return targetBody__;
    }
    /**
     * <p>targetBody をセットします。
     * @param targetBody targetBody
     */
    public void setTargetBody(boolean targetBody) {
        targetBody__ = targetBody;
    }
    /**
     * <p>targetTitle を取得します。
     * @return targetTitle
     */
    public boolean isTargetTitle() {
        return targetTitle__;
    }
    /**
     * <p>targetTitle をセットします。
     * @param targetTitle targetTitle
     */
    public void setTargetTitle(boolean targetTitle) {
        targetTitle__ = targetTitle;
    }
    /**
     * <p>kairansakiSid を取得します。
     * @return kairansakiSid
     */
    public String[] getKairansakiSid() {
        return kairansakiSid__;
    }
    /**
     * <p>kairansakiSid をセットします。
     * @param kairansakiSid kairansakiSid
     */
    public void setKairansakiSid(String[] kairansakiSid) {
        kairansakiSid__ = kairansakiSid;
    }
    /**
     * <p>cacSid を取得します。
     * @return cacSid
     */
    public int getCacSid() {
        return cacSid__;
    }
    /**
     * <p>cacSid をセットします。
     * @param cacSid cacSid
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
    }
    /**
     * <p>groupSid を取得します。
     * @return groupSid
     */
    public String getGroupSid() {
        return groupSid__;
    }
    /**
     * <p>groupSid をセットします。
     * @param groupSid groupSid
     */
    public void setGroupSid(String groupSid) {
        groupSid__ = groupSid;
    }
    /**
     * <p>sessionUserSid を取得します。
     * @return sessionUserSid
     */
    public int getSessionUserSid() {
        return sessionUserSid__;
    }
    /**
     * <p>sessionUserSid をセットします。
     * @param sessionUserSid sessionUserSid
     */
    public void setSessionUserSid(int sessionUserSid) {
        sessionUserSid__ = sessionUserSid;
    }
    /**
     * <p>labelSid を取得します。
     * @return labelSid
     * @see jp.groupsession.v2.cir.model.CirSearchModel#labelSid__
     */
    public int getLabelSid() {
        return labelSid__;
    }
    /**
     * <p>labelSid をセットします。
     * @param labelSid labelSid
     * @see jp.groupsession.v2.cir.model.CirSearchModel#labelSid__
     */
    public void setLabelSid(int labelSid) {
        labelSid__ = labelSid;
    }

}
