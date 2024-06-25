package jp.groupsession.v2.rsv.rsv190;

import java.util.ArrayList;

import jp.groupsession.v2.rsv.AbstractReserveParamModel;
import jp.groupsession.v2.rsv.RsvCalenderModel;
import jp.groupsession.v2.rsv.RsvSisetuModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 施設予約 週間ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv190ParamModel extends AbstractReserveParamModel {

    /** 施設グループコンボリスト */
    private ArrayList<LabelValueBean> rsvGrpLabelList__ = null;
    /** 一覧のヘッダに表示する表示日付 */
    private String rsvDispYm__ = null;
    /** 週間カレンダー */
    private ArrayList<RsvCalenderModel> rsv010CalendarList__ = null;
    /** 施設毎の予約情報リスト */
    private ArrayList<RsvSisetuModel> rsv010SisetuList__ = null;

    /**
     * <p>rsvGrpLabelList__ を取得します。
     * @return rsvGrpLabelList
     */
    public ArrayList<LabelValueBean> getRsvGrpLabelList() {
        return rsvGrpLabelList__;
    }
    /**
     * <p>rsvGrpLabelList__ をセットします。
     * @param rsvGrpLabelList rsvGrpLabelList__
     */
    public void setRsvGrpLabelList(ArrayList<LabelValueBean> rsvGrpLabelList) {
        rsvGrpLabelList__ = rsvGrpLabelList;
    }
    /**
     * <p>rsvDispYm__ を取得します。
     * @return rsvDispYm
     */
    public String getRsvDispYm() {
        return rsvDispYm__;
    }
    /**
     * <p>rsvDispYm__ をセットします。
     * @param rsvDispYm rsvDispYm__
     */
    public void setRsvDispYm(String rsvDispYm) {
        rsvDispYm__ = rsvDispYm;
    }
    /**
     * <p>rsv010CalendarList__ を取得します。
     * @return rsv010CalendarList
     */
    public ArrayList<RsvCalenderModel> getRsv010CalendarList() {
        return rsv010CalendarList__;
    }
    /**
     * <p>rsv010CalendarList__ をセットします。
     * @param rsv010CalendarList rsv010CalendarList__
     */
    public void setRsv010CalendarList(ArrayList<RsvCalenderModel> rsv010CalendarList) {
        rsv010CalendarList__ = rsv010CalendarList;
    }
    /**
     * <p>rsv010SisetuList__ を取得します。
     * @return rsv010SisetuList
     */
    public ArrayList<RsvSisetuModel> getRsv010SisetuList() {
        return rsv010SisetuList__;
    }
    /**
     * <p>rsv010SisetuList__ をセットします。
     * @param rsv010SisetuList rsv010SisetuList__
     */
    public void setRsv010SisetuList(ArrayList<RsvSisetuModel> rsv010SisetuList) {
        rsv010SisetuList__ = rsv010SisetuList;
    }
}