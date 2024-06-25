package jp.groupsession.v2.rss.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstRss;
import jp.groupsession.v2.cmn.GSFeedList;


/**
 * <p>RSS_DATA Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RssModel extends RssDataModel {

    /** フィード情報 */
    private GSFeedList feedData__ = null;
    /** フィード情報更新時間 */
    private UDate feedUpdateTime__ = null;
    /** 順位 */
    private String ranking__ = "-";
    /** ユーザ登録件数 */
    private int userCount__ = 0;
    /** 説明 */
    private String description__ = null;
    /** RSS購読の有無 */
    private int koudokuCount__ = GSConstRss.RSS_KOUDOKU_FLG_NO;

    /** RSP_POSITION mapping */
    private int rspPosition__;
    /** フィード情報更新時間(表示用) */
    private String dspFeedUpdateTime__ = null;
    /** 登録者姓 */
    private String registSei__ = null;
    /** 登録者名 */
    private String registMei__ = null;

    /**
     * @return registMei
     */
    public String getRegistMei() {
        return registMei__;
    }

    /**
     * @param registMei 設定する registMei
     */
    public void setRegistMei(String registMei) {
        registMei__ = registMei;
    }

    /**
     * @return registSei
     */
    public String getRegistSei() {
        return registSei__;
    }

    /**
     * @param registSei 設定する registSei
     */
    public void setRegistSei(String registSei) {
        registSei__ = registSei;
    }

    /**
     * <p>ranking を取得します。
     * @return ranking
     */
    public String getRanking() {
        return ranking__;
    }

    /**
     * <p>ranking をセットします。
     * @param ranking ranking
     */
    public void setRanking(String ranking) {
        ranking__ = ranking;
    }

    /**
     * <p>rspPosition を取得します。
     * @return rspPosition
     */
    public int getRspPosition() {
        return rspPosition__;
    }

    /**
     * <p>rspPosition をセットします。
     * @param rspPosition rspPosition
     */
    public void setRspPosition(int rspPosition) {
        rspPosition__ = rspPosition;
    }

    /**
     * <p>userCount を取得します。
     * @return userCount
     */
    public int getUserCount() {
        return userCount__;
    }
    /**
     * <p>userCount をセットします。
     * @param userCount userCount
     */
    public void setUserCount(int userCount) {
        userCount__ = userCount;
    }
    /**
     * <p>description を取得します。
     * @return description
     */
    public String getDescription() {
        return description__;
    }
    /**
     * <p>description をセットします。
     * @param description description
     */
    public void setDescription(String description) {
        description__ = description;
    }

    /**
     * <p>feedData を取得します。
     * @return feedData
     */
    public GSFeedList getFeedData() {
        return feedData__;
    }

    /**
     * <p>feedData をセットします。
     * @param feedData feedData
     */
    public void setFeedData(GSFeedList feedData) {
        feedData__ = feedData;
    }

    /**
     * <p>feedUpdateTime を取得します。
     * @return feedUpdateTime
     */
    public UDate getFeedUpdateTime() {
        return feedUpdateTime__;
    }

    /**
     * <p>feedUpdateTime をセットします。
     * @param feedUpdateTime feedUpdateTime
     */
    public void setFeedUpdateTime(UDate feedUpdateTime) {
        feedUpdateTime__ = feedUpdateTime;
    }

    /**
     * <p>koudokuCount を取得します。
     * @return koudokuCount
     */
    public int getKoudokuCount() {
        return koudokuCount__;
    }

    /**
     * <p>koudokuCount をセットします。
     * @param koudokuCount koudokuCount
     */
    public void setKoudokuCount(int koudokuCount) {
        koudokuCount__ = koudokuCount;
    }

    /**
     * @return dspFeedUpdateTime
     */
    public String getDspFeedUpdateTime() {
        return dspFeedUpdateTime__;
    }

    /**
     * @param dspFeedUpdateTime 設定する dspFeedUpdateTime
     */
    public void setDspFeedUpdateTime(String dspFeedUpdateTime) {
        dspFeedUpdateTime__ = dspFeedUpdateTime;
    }
}