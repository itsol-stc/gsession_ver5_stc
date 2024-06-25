package jp.groupsession.v2.enq.enq970;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.enq.enq010.Enq010SearchModel;

/**
 * <br>[機  能] アンケート一覧取得時に使用する検索条件を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq970SearchModel extends Enq010SearchModel {
    /** セッションユーザSID */
    private int sessionUserSid__ = 0;
    /** ページ */
    private int page__ = 0;
    /** 1ページの最大件数 */
    private int maxCount__ = 0;
    /** ソートキー */
    private int sortKey__ = 0;
    /** 並び順 */
    private int order__ = 0;
    /** フォルダ */
    private int folder__ = 0;
    /** 種類 */
    private int enqType__ = 0;
    /** キーワード 種別 */
    private int keywordType__ = 0;
    /** 発信者 グループ */
    private int senderGroup__ = 0;
    /** 発信者 ユーザ */
    private int senderUser__ = 0;
    /** 発信者 入力 */
    private String senderInput__ = null;
    /** 作成日 開始 */
    private UDate makeDateFrom__ = null;
    /** 作成日 終了 */
    private UDate makeDateTo__ = null;
    /** 公開期限 開始 */
    private UDate pubLimitDateFrom__ = null;
    /** 公開期限 終了 */
    private UDate pubLimitDateTo__ = null;
    /** 回答期限 開始 */
    private UDate ansLimitDateFrom__ = null;
    /** 回答期限 終了 */
    private UDate ansLimitDateTo__ = null;
    /** 重要度 */
    private int[] priority__ = null;
    /** 状態  */
    private int[] status__ = null;
    /** 匿名 */
    private int anony__ = 0;
    /** 管理者フラグ */
    private boolean enqAdminFlg__ = false;
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
     * <p>folder を取得します。
     * @return folder
     */
    public int getFolder() {
        return folder__;
    }
    /**
     * <p>folder をセットします。
     * @param folder folder
     */
    public void setFolder(int folder) {
        folder__ = folder;
    }
    /**
     * <p>enqType を取得します。
     * @return enqType
     */
    public int getEnqType() {
        return enqType__;
    }
    /**
     * <p>enqType をセットします。
     * @param enqType enqType
     */
    public void setEnqType(int enqType) {
        enqType__ = enqType;
    }
    /**
     * <p>keywordType を取得します。
     * @return keywordType
     */
    public int getKeywordType() {
        return keywordType__;
    }
    /**
     * <p>keywordType をセットします。
     * @param keywordType keywordType
     */
    public void setKeywordType(int keywordType) {
        keywordType__ = keywordType;
    }
    /**
     * <p>senderGroup を取得します。
     * @return senderGroup
     */
    public int getSenderGroup() {
        return senderGroup__;
    }
    /**
     * <p>senderGroup をセットします。
     * @param senderGroup senderGroup
     */
    public void setSenderGroup(int senderGroup) {
        senderGroup__ = senderGroup;
    }
    /**
     * <p>senderUser を取得します。
     * @return senderUser
     */
    public int getSenderUser() {
        return senderUser__;
    }
    /**
     * <p>senderUser をセットします。
     * @param senderUser senderUser
     */
    public void setSenderUser(int senderUser) {
        senderUser__ = senderUser;
    }
    /**
     * <p>senderInput を取得します。
     * @return senderInput
     */
    public String getSenderInput() {
        return senderInput__;
    }
    /**
     * <p>senderInput をセットします。
     * @param senderInput senderInput
     */
    public void setSenderInput(String senderInput) {
        senderInput__ = senderInput;
    }
    /**
     * <p>makeDateFrom を取得します。
     * @return makeDateFrom
     */
    public UDate getMakeDateFrom() {
        return makeDateFrom__;
    }
    /**
     * <p>makeDateFrom をセットします。
     * @param makeDateFrom makeDateFrom
     */
    public void setMakeDateFrom(UDate makeDateFrom) {
        makeDateFrom__ = makeDateFrom;
    }
    /**
     * <p>makeDateTo を取得します。
     * @return makeDateTo
     */
    public UDate getMakeDateTo() {
        return makeDateTo__;
    }
    /**
     * <p>makeDateTo をセットします。
     * @param makeDateTo makeDateTo
     */
    public void setMakeDateTo(UDate makeDateTo) {
        makeDateTo__ = makeDateTo;
    }
    /**
     * <p>pubLimitDateFrom を取得します。
     * @return pubLimitDateFrom
     */
    public UDate getPubLimitDateFrom() {
        return pubLimitDateFrom__;
    }
    /**
     * <p>pubLimitDateFrom をセットします。
     * @param pubLimitDateFrom pubLimitDateFrom
     */
    public void setPubLimitDateFrom(UDate pubLimitDateFrom) {
        pubLimitDateFrom__ = pubLimitDateFrom;
    }
    /**
     * <p>pubLimitDateTo を取得します。
     * @return pubLimitDateTo
     */
    public UDate getPubLimitDateTo() {
        return pubLimitDateTo__;
    }
    /**
     * <p>pubLimitDateTo をセットします。
     * @param pubLimitDateTo pubLimitDateTo
     */
    public void setPubLimitDateTo(UDate pubLimitDateTo) {
        pubLimitDateTo__ = pubLimitDateTo;
    }
    /**
     * <p>ansLimitDateFrom を取得します。
     * @return ansLimitDateFrom
     */
    public UDate getAnsLimitDateFrom() {
        return ansLimitDateFrom__;
    }
    /**
     * <p>ansLimitDateFrom をセットします。
     * @param ansLimitDateFrom ansLimitDateFrom
     */
    public void setAnsLimitDateFrom(UDate ansLimitDateFrom) {
        ansLimitDateFrom__ = ansLimitDateFrom;
    }
    /**
     * <p>ansLimitDateTo を取得します。
     * @return ansLimitDateTo
     */
    public UDate getAnsLimitDateTo() {
        return ansLimitDateTo__;
    }
    /**
     * <p>ansLimitDateTo をセットします。
     * @param ansLimitDateTo ansLimitDateTo
     */
    public void setAnsLimitDateTo(UDate ansLimitDateTo) {
        ansLimitDateTo__ = ansLimitDateTo;
    }
    /**
     * <p>priority を取得します。
     * @return priority
     */
    public int[] getPriority() {
        return priority__;
    }
    /**
     * <p>priority をセットします。
     * @param priority priority
     */
    public void setPriority(int[] priority) {
        priority__ = priority;
    }
    /**
     * <p>status を取得します。
     * @return status
     */
    public int[] getStatus() {
        return status__;
    }
    /**
     * <p>status をセットします。
     * @param status status
     */
    public void setStatus(int[] status) {
        status__ = status;
    }
    /**
     * <p>anony を取得します。
     * @return anony
     */
    public int getAnony() {
        return anony__;
    }
    /**
     * <p>anony をセットします。
     * @param anony anony
     */
    public void setAnony(int anony) {
        anony__ = anony;
    }
    /**
     * <p>enqAdminFlg を取得します。
     * @return enqAdminFlg
     */
    public boolean isEnqAdminFlg() {
        return enqAdminFlg__;
    }
    /**
     * <p>enqAdminFlg をセットします。
     * @param enqAdminFlg enqAdminFlg
     */
    public void setEnqAdminFlg(boolean enqAdminFlg) {
        enqAdminFlg__ = enqAdminFlg;
    }
}
