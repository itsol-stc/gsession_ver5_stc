package jp.groupsession.v2.rsv.main;

import java.util.ArrayList;

import jp.groupsession.v2.rsv.model.RsvMainGrpModel;
import jp.groupsession.v2.struts.AbstractGsForm;


/**
 * <br>[機  能] 施設予約 予約状況一覧(メイン画面表示用)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvMainForm extends AbstractGsForm {

    /** 表示日付 */
    private String dspDate__;
    /** 編集対象の予約SID */
    private String rsvmainSselectedYoyakuSid__;
    //表示用
    /** 予約状況一覧リスト */
    private ArrayList<RsvMainGrpModel> reservList__;
    /** 施設予約トップ画面URL */
    private String  rsvTopUrl__;
    /**
     * @return rsvTopUrl__ を戻します。
     */
    public String getRsvTopUrl() {
        return rsvTopUrl__;
    }
    /**
     * @param rsvTopUrl 設定する rsvTopUrl__。
     */
    public void setRsvTopUrl(String rsvTopUrl) {
        rsvTopUrl__ = rsvTopUrl;
    }
    /**
     * <p>rsvmainSselectedYoyakuSid を取得します。
     * @return rsvmainSselectedYoyakuSid
     */
    public String getRsvmainSselectedYoyakuSid() {
        return rsvmainSselectedYoyakuSid__;
    }

    /**
     * <p>rsvmainSselectedYoyakuSid をセットします。
     * @param rsvmainSselectedYoyakuSid rsvmainSselectedYoyakuSid
     */
    public void setRsvmainSselectedYoyakuSid(String rsvmainSselectedYoyakuSid) {
        rsvmainSselectedYoyakuSid__ = rsvmainSselectedYoyakuSid;
    }

    /**
     * <p>dspDate を取得します。
     * @return dspDate
     */
    public String getDspDate() {
        return dspDate__;
    }

    /**
     * <p>dspDate をセットします。
     * @param dspDate dspDate
     */
    public void setDspDate(String dspDate) {
        dspDate__ = dspDate;
    }

    /**
     * <p>reservList を取得します。
     * @return reservList
     */
    public ArrayList<RsvMainGrpModel> getReservList() {
        return reservList__;
    }

    /**
     * <p>reservList をセットします。
     * @param reservList reservList
     */
    public void setReservList(ArrayList<RsvMainGrpModel> reservList) {
        reservList__ = reservList;
    }

}