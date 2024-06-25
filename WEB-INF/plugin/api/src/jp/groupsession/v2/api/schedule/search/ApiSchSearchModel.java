package jp.groupsession.v2.api.schedule.search;

import jp.groupsession.v2.sch.model.ScheduleSearchModel;
/**
 *
 * <br>[機  能] Apiスケジュール検索用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchSearchModel extends ScheduleSearchModel {
    /**予定ありフラグ*/
    private boolean seacret__;



    /**
     * <p>seacret を取得します。
     * @return seacret
     */
    public boolean isSeacret() {
        return seacret__;
    }

    /**
     * <p>seacret をセットします。
     * @param seacret seacret
     */
    public void setSeacret(boolean seacret) {
        seacret__ = seacret;
    }

}
