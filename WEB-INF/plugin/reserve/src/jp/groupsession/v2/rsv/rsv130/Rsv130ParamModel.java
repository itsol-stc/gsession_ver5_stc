package jp.groupsession.v2.rsv.rsv130;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.rsv040.Rsv040ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約 管理者設定 手動データ削除画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv130ParamModel extends Rsv040ParamModel {
    /** 年 */
    private int rsv130year__ = 3;
    /** 月 */
    private int rsv130month__ = GSConstReserve.COMBO_DEFAULT_VALUE;
    /** 年リスト */
    private ArrayList<LabelValueBean> rsv130yearLabelList__ = null;
    /** 月リスト */
    private ArrayList<LabelValueBean> rsv130monthLabelList__ = null;

    /**
     * <p>rsv130month を取得します。
     * @return rsv130month
     */
    public int getRsv130month() {
        return rsv130month__;
    }
    /**
     * <p>rsv130month をセットします。
     * @param rsv130month rsv130month
     */
    public void setRsv130month(int rsv130month) {
        rsv130month__ = rsv130month;
    }
    /**
     * <p>rsv130monthLabelList を取得します。
     * @return rsv130monthLabelList
     */
    public ArrayList<LabelValueBean> getRsv130monthLabelList() {
        return rsv130monthLabelList__;
    }
    /**
     * <p>rsv130monthLabelList をセットします。
     * @param rsv130monthLabelList rsv130monthLabelList
     */
    public void setRsv130monthLabelList(
            ArrayList<LabelValueBean> rsv130monthLabelList) {
        rsv130monthLabelList__ = rsv130monthLabelList;
    }
    /**
     * <p>rsv130year を取得します。
     * @return rsv130year
     */
    public int getRsv130year() {
        return rsv130year__;
    }
    /**
     * <p>rsv130year をセットします。
     * @param rsv130year rsv130year
     */
    public void setRsv130year(int rsv130year) {
        rsv130year__ = rsv130year;
    }
    /**
     * <p>rsv130yearLabelList を取得します。
     * @return rsv130yearLabelList
     */
    public ArrayList<LabelValueBean> getRsv130yearLabelList() {
        return rsv130yearLabelList__;
    }
    /**
     * <p>rsv130yearLabelList をセットします。
     * @param rsv130yearLabelList rsv130yearLabelList
     */
    public void setRsv130yearLabelList(ArrayList<LabelValueBean> rsv130yearLabelList) {
        rsv130yearLabelList__ = rsv130yearLabelList;
    }
}