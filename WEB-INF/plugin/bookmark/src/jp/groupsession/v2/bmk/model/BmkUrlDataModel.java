package jp.groupsession.v2.bmk.model;

import java.util.List;



/**
 * <br>[機  能] URLデータ情報を保持するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BmkUrlDataModel extends BmkUrlModel {
    /** 平均評価 */
    private int scoreAvg__ = 0;
    /** 順位 */
    private String ranking__ = "-";
    /** ユーザ登録件数 */
    private int userCount__ = 0;
    /** 登録の有無 */
    private int tourokuCount__ = 0;
    /** タイトル(表示用) */
    private List<String> bmkTitleDspList__;
    /** URL(表示用) */
    private List<String> bmkUrlDspList__;
    /**
     * <p>scoreAvg を取得します。
     * @return scoreAvg
     */
    public int getScoreAvg() {
        return scoreAvg__;
    }
    /**
     * <p>scoreAvg をセットします。
     * @param scoreAvg scoreAvg
     */
    public void setScoreAvg(int scoreAvg) {
        scoreAvg__ = scoreAvg;
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
     * <p>tourokuCount を取得します。
     * @return tourokuCount
     */
    public int getTourokuCount() {
        return tourokuCount__;
    }
    /**
     * <p>tourokuCount をセットします。
     * @param tourokuCount tourokuCount
     */
    public void setTourokuCount(int tourokuCount) {
        tourokuCount__ = tourokuCount;
    }
    /**
     * <p>bmkTitleDspList を取得します。
     * @return bmkTitleDspList
     */
    public List<String> getBmkTitleDspList() {
        return bmkTitleDspList__;
    }
    /**
     * <p>bmkTitleDspList をセットします。
     * @param bmkTitleDspList bmkTitleDspList
     */
    public void setBmkTitleDspList(List<String> bmkTitleDspList) {
        bmkTitleDspList__ = bmkTitleDspList;
    }
    /**
     * <p>bmkUrlDspList を取得します。
     * @return bmkUrlDspList
     */
    public List<String> getBmkUrlDspList() {
        return bmkUrlDspList__;
    }
    /**
     * <p>bmkUrlDspList をセットします。
     * @param bmkUrlDspList bmkUrlDspList
     */
    public void setBmkUrlDspList(List<String> bmkUrlDspList) {
        bmkUrlDspList__ = bmkUrlDspList;
    }
}
