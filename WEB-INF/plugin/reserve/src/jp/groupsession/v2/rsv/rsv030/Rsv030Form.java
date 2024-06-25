package jp.groupsession.v2.rsv.rsv030;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.RsvWeekModel;
import jp.groupsession.v2.rsv.rsv010.Rsv010Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約一覧 月間画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv030Form extends Rsv010Form {

    /** 施設コンボリスト */
    private ArrayList<LabelValueBean> rsvSisetuLabelList__ = null;
    /** 施設毎の予約情報リスト */
    private ArrayList<RsvWeekModel> rsv030MonthList__ = null;
    /** 取消し対象施設キー */
    private String rsv030ClearTargetKey__ = null;
    /** 自動リロード時間 */
    private int rsv030Reload__ = GSConstReserve.AUTO_RELOAD_10MIN;
    /** アクセス権限区分 */
    private int rsv030AccessAuth__ = GSConstReserve.RSV_ACCESS_KBN_WRITE;

    /**
     * <p>rsv030ClearTargetKey__ を取得します。
     * @return rsv030ClearTargetKey
     */
    public String getRsv030ClearTargetKey() {
        return rsv030ClearTargetKey__;
    }
    /**
     * <p>rsv030ClearTargetKey__ をセットします。
     * @param rsv030ClearTargetKey rsv030ClearTargetKey__
     */
    public void setRsv030ClearTargetKey(String rsv030ClearTargetKey) {
        rsv030ClearTargetKey__ = rsv030ClearTargetKey;
    }
    /**
     * <p>rsv030MonthList__ を取得します。
     * @return rsv030MonthList
     */
    public ArrayList<RsvWeekModel> getRsv030MonthList() {
        return rsv030MonthList__;
    }
    /**
     * <p>rsv030MonthList__ をセットします。
     * @param rsv030MonthList rsv030MonthList__
     */
    public void setRsv030MonthList(ArrayList<RsvWeekModel> rsv030MonthList) {
        rsv030MonthList__ = rsv030MonthList;
    }
    /**
     * <p>rsvSisetuLabelList__ を取得します。
     * @return rsvSisetuLabelList
     */
    public ArrayList<LabelValueBean> getRsvSisetuLabelList() {
        return rsvSisetuLabelList__;
    }
    /**
     * <p>rsvSisetuLabelList__ をセットします。
     * @param rsvSisetuLabelList rsvSisetuLabelList__
     */
    public void setRsvSisetuLabelList(ArrayList<LabelValueBean> rsvSisetuLabelList) {
        rsvSisetuLabelList__ = rsvSisetuLabelList;
    }
    /**
     * <p>rsv030Reload を取得します。
     * @return rsv030Reload
     */
    public int getRsv030Reload() {
        return rsv030Reload__;
    }
    /**
     * <p>rsv030Reload をセットします。
     * @param rsv030Reload rsv030Reload
     */
    public void setRsv030Reload(int rsv030Reload) {
        rsv030Reload__ = rsv030Reload;
    }
    /**
     * <p>rsv030AccessAuth__ を取得します。
     * @return rsv030AccessAuth__
     */
    public int getRsv030AccessAuth() {
        return rsv030AccessAuth__;
    }
    /**
     * <p>rsv030AccessAuth__ をセットします。
     * @param rsv030AccessAuth rsv030AccessAuth__
     */
    public void setRsv030AccessAuth(int rsv030AccessAuth) {
        rsv030AccessAuth__ = rsv030AccessAuth;
    }
}