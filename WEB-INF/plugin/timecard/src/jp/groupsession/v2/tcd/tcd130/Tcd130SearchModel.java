package jp.groupsession.v2.tcd.tcd130;

import java.io.Serializable;


/**
 * <br>[機  能] 検索条件を格納するのModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd130SearchModel implements Serializable  {

    /** 検索対象グループ */
    private int groupSid__ = -1;
    /** 検索対象ユーザ */
    private int userSid__ = -1;
    /** 検索対象時間帯設定 */
    private int timeZone__ = 0;
    /** 検索対象デフォルト時間帯設定 */
    private int defaultTimeZone__ = 0;
    /** 検索対象デフォルト時間帯設定 */
    private int tacDefaultTimeZone__ = 0;

    /** ページ*/
    private int page__ = 0;
    /** ページごとの最大表示件数*/
    private int maxCnt__ = 0;


    /**
     * <p>groupSid を取得します。
     * @return groupSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#groupSid__
     */
    public int getGroupSid() {
        return groupSid__;
    }
    /**
     * <p>groupSid をセットします。
     * @param groupSid groupSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#groupSid__
     */
    public void setGroupSid(int groupSid) {
        groupSid__ = groupSid;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#userSid__
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#userSid__
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>timeZone を取得します。
     * @return timeZone
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#timeZone__
     */
    public int getTimeZone() {
        return timeZone__;
    }
    /**
     * <p>timeZone をセットします。
     * @param timeZone timeZone
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#timeZone__
     */
    public void setTimeZone(int timeZone) {
        timeZone__ = timeZone;
    }
    /**
     * <p>defaultTimeZone を取得します。
     * @return defaultTimeZone
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#defaultTimeZone__
     */
    public int getDefaultTimeZone() {
        return defaultTimeZone__;
    }
    /**
     * <p>defaultTimeZone をセットします。
     * @param defaultTimeZone defaultTimeZone
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#defaultTimeZone__
     */
    public void setDefaultTimeZone(int defaultTimeZone) {
        defaultTimeZone__ = defaultTimeZone;
    }
    /**
     * <p>tacDefaultTimeZone を取得します。
     * @return tacDefaultTimeZone
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#tacDefaultTimeZone__
     */
    public int getTacDefaultTimeZone() {
        return tacDefaultTimeZone__;
    }
    /**
     * <p>tacDefaultTimeZone をセットします。
     * @param tacDefaultTimeZone tacDefaultTimeZone
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#tacDefaultTimeZone__
     */
    public void setTacDefaultTimeZone(int tacDefaultTimeZone) {
        tacDefaultTimeZone__ = tacDefaultTimeZone;
    }
    /**
     * <p>page を取得します。
     * @return page
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#page__
     */
    public int getPage() {
        return page__;
    }
    /**
     * <p>page をセットします。
     * @param page page
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#page__
     */
    public void setPage(int page) {
        page__ = page;
    }
    /**
     * <p>maxCnt を取得します。
     * @return maxCnt
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#maxCnt__
     */
    public int getMaxCnt() {
        return maxCnt__;
    }
    /**
     * <p>maxCnt をセットします。
     * @param maxCnt maxCnt
     * @see jp.groupsession.v2.tcd.tcd130.Tcd130SearchModel#maxCnt__
     */
    public void setMaxCnt(int maxCnt) {
        maxCnt__ = maxCnt;
    }
}
