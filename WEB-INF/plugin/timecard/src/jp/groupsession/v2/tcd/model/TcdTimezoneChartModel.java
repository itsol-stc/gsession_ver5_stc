package jp.groupsession.v2.tcd.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;

/**
 * <br>[機  能] タイムカード時間帯チャートの明細を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdTimezoneChartModel extends AbstractModel {

    /** タイムカード基本設定 */
    private TcdAdmConfModel tcAdmConf__ = null;

    /** タイムカード時間帯明細のリスト*/
    private ArrayList<TcdTimezoneMeiModel> chartList__ = null;

    /**
     * <p>tcAdmConf を取得します。
     * @return tcAdmConf
     */
    public TcdAdmConfModel getTcAdmConf() {
        return tcAdmConf__;
    }

    /**
     * <p>tcAdmConf をセットします。
     * @param tcAdmConf tcAdmConf
     */
    public void setTcAdmConf(TcdAdmConfModel tcAdmConf) {
        tcAdmConf__ = tcAdmConf;
    }

    /**
     * <p>chartList を取得します。
     * @return chartList
     */
    public ArrayList<TcdTimezoneMeiModel> getChartList() {
        return chartList__;
    }

    /**
     * <p>chartList をセットします。
     * @param chartList chartList
     */
    public void setChartList(ArrayList<TcdTimezoneMeiModel> chartList) {
        chartList__ = chartList;
    }

}
