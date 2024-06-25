package jp.groupsession.v2.sch.sch010;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] ユーザ毎のスケジュール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch010PeriodModel extends AbstractModel {

    /** 開始位置 */
    private int schPeriodStart__ = 0;
    /** 日数 */
    private int schPeriodCnt__ = 0;
    /**
     * <p>schPeriodStart を取得します。
     * @return schPeriodStart
     */
    public int getSchPeriodStart() {
        return schPeriodStart__;
    }
    /**
     * <p>schPeriodStart をセットします。
     * @param schPeriodStart schPeriodStart
     */
    public void setSchPeriodStart(int schPeriodStart) {
        schPeriodStart__ = schPeriodStart;
    }
    /**
     * <p>schPeriodCnt を取得します。
     * @return schPeriodCnt
     */
    public int getSchPeriodCnt() {
        return schPeriodCnt__;
    }
    /**
     * <p>schPeriodCnt をセットします。
     * @param schPeriodCnt schPeriodCnt
     */
    public void setSchPeriodCnt(int schPeriodCnt) {
        schPeriodCnt__ = schPeriodCnt;
    }

}
