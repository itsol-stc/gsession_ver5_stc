package jp.groupsession.v2.sch.sch041kn;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.sch.sch041.Sch041ParamModel;

/**
 * <br>[機  能] スケジュール繰り返し登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch041knParamModel extends Sch041ParamModel {

    /** 設定期間 from */
    private String sch041knFromDate__ = null;
    /** 設定期間 to */
    private String sch041knToDate__ = null;
    /** 設定時間 from */
    private String sch041knFromTime__ = null;
    /** 設定時間 to */
    private String sch041knToTime__ = null;
    /** 内容 */
    private String sch041knDspValue__ = null;
    /** 備考 */
    private String sch041knDspBiko__ = null;

    /** 更新前の登録日付リスト */
    private ArrayList<String> sch041knBefDateList__ = null;
    /** 更新後の登録日付リスト */
    private ArrayList<String> sch041knAftDateList__ = null;
    /** 同時登録ユーザリスト */
    private ArrayList < CmnUsrmInfModel > sch041knSelectUsrList__ = null;

    /** 削除用 振替設定 */
    private String sch041knDelTranKbn__ = null;
    /** 削除用 設定期間from */
    private String sch041knDelFrDate__ = null;
    /** 削除用 設定期間to */
    private String sch041knDelToDate__ = null;
    /** 削除用 開始時間 */
    private String sch041knDelFrTime__ = null;
    /** 削除用 終了時間 */
    private String sch041knDelToTime__ = null;
    /** 削除用 背景色 */
    private String sch041knDelBgcolor__ = null;
    /** 削除用 スケジュールタイトル */
    private String sch041knDelTitle__ = null;
    /** 削除用 スケジュール内容 */
    private String sch041knDelValue__ = null;
    /** 削除用 スケジュール備考 */
    private String sch041knDelBiko__ = null;
    /** 削除用 公開区分 */
    private String sch041knDelPublic__ = null;
    /** 削除用 編集権限設定 0=未設定 1=本人のみ 2=所属グループ */
    private String sch041knDelEdit__ = null;

    /** 同時登録施設リスト */
    private ArrayList <RsvSisDataModel> sch041knReserveList__ = null;

    /** リマインダー通知 通知時間テキスト */
    private String sch041knTimeText__ = null;
    /** リマインダー通知 対象テキスト */
    private String sch041knTargetText__ = null;
    
    /** 添付ファイルのバイナリSID(ダウンロード時) */
    private String sch041knBinSid__;
    /** 添付ファイルリスト */
    private List<LabelValueBean> sch041knFileList__ = null;

    /**
     * <p>sch041knReserveList を取得します。
     * @return sch041knReserveList
     */
    public ArrayList<RsvSisDataModel> getSch041knReserveList() {
        return sch041knReserveList__;
    }
    /**
     * <p>sch041knReserveList をセットします。
     * @param sch041knReserveList sch041knReserveList
     */
    public void setSch041knReserveList(
            ArrayList<RsvSisDataModel> sch041knReserveList) {
        sch041knReserveList__ = sch041knReserveList;
    }
    /**
     * <p>sch041knDelBgcolor を取得します。
     * @return sch041knDelBgcolor
     */
    public String getSch041knDelBgcolor() {
        return sch041knDelBgcolor__;
    }
    /**
     * <p>sch041knDelBgcolor をセットします。
     * @param sch041knDelBgcolor sch041knDelBgcolor
     */
    public void setSch041knDelBgcolor(String sch041knDelBgcolor) {
        sch041knDelBgcolor__ = sch041knDelBgcolor;
    }
    /**
     * <p>sch041knDelBiko を取得します。
     * @return sch041knDelBiko
     */
    public String getSch041knDelBiko() {
        return sch041knDelBiko__;
    }
    /**
     * <p>sch041knDelBiko をセットします。
     * @param sch041knDelBiko sch041knDelBiko
     */
    public void setSch041knDelBiko(String sch041knDelBiko) {
        sch041knDelBiko__ = sch041knDelBiko;
    }
    /**
     * <p>sch041knDelEdit を取得します。
     * @return sch041knDelEdit
     */
    public String getSch041knDelEdit() {
        return sch041knDelEdit__;
    }
    /**
     * <p>sch041knDelEdit をセットします。
     * @param sch041knDelEdit sch041knDelEdit
     */
    public void setSch041knDelEdit(String sch041knDelEdit) {
        sch041knDelEdit__ = sch041knDelEdit;
    }
    /**
     * <p>sch041knDelFrDate を取得します。
     * @return sch041knDelFrDate
     */
    public String getSch041knDelFrDate() {
        return sch041knDelFrDate__;
    }
    /**
     * <p>sch041knDelFrDate をセットします。
     * @param sch041knDelFrDate sch041knDelFrDate
     */
    public void setSch041knDelFrDate(String sch041knDelFrDate) {
        sch041knDelFrDate__ = sch041knDelFrDate;
    }
    /**
     * <p>sch041knDelFrTime を取得します。
     * @return sch041knDelFrTime
     */
    public String getSch041knDelFrTime() {
        return sch041knDelFrTime__;
    }
    /**
     * <p>sch041knDelFrTime をセットします。
     * @param sch041knDelFrTime sch041knDelFrTime
     */
    public void setSch041knDelFrTime(String sch041knDelFrTime) {
        sch041knDelFrTime__ = sch041knDelFrTime;
    }
    /**
     * <p>sch041knDelPublic を取得します。
     * @return sch041knDelPublic
     */
    public String getSch041knDelPublic() {
        return sch041knDelPublic__;
    }
    /**
     * <p>sch041knDelPublic をセットします。
     * @param sch041knDelPublic sch041knDelPublic
     */
    public void setSch041knDelPublic(String sch041knDelPublic) {
        sch041knDelPublic__ = sch041knDelPublic;
    }
    /**
     * <p>sch041knDelTitle を取得します。
     * @return sch041knDelTitle
     */
    public String getSch041knDelTitle() {
        return sch041knDelTitle__;
    }
    /**
     * <p>sch041knDelTitle をセットします。
     * @param sch041knDelTitle sch041knDelTitle
     */
    public void setSch041knDelTitle(String sch041knDelTitle) {
        sch041knDelTitle__ = sch041knDelTitle;
    }
    /**
     * <p>sch041knDelToDate を取得します。
     * @return sch041knDelToDate
     */
    public String getSch041knDelToDate() {
        return sch041knDelToDate__;
    }
    /**
     * <p>sch041knDelToDate をセットします。
     * @param sch041knDelToDate sch041knDelToDate
     */
    public void setSch041knDelToDate(String sch041knDelToDate) {
        sch041knDelToDate__ = sch041knDelToDate;
    }
    /**
     * <p>sch041knDelToTime を取得します。
     * @return sch041knDelToTime
     */
    public String getSch041knDelToTime() {
        return sch041knDelToTime__;
    }
    /**
     * <p>sch041knDelToTime をセットします。
     * @param sch041knDelToTime sch041knDelToTime
     */
    public void setSch041knDelToTime(String sch041knDelToTime) {
        sch041knDelToTime__ = sch041knDelToTime;
    }
    /**
     * <p>sch041knDelTranKbn を取得します。
     * @return sch041knDelTranKbn
     */
    public String getSch041knDelTranKbn() {
        return sch041knDelTranKbn__;
    }
    /**
     * <p>sch041knDelTranKbn をセットします。
     * @param sch041knDelTranKbn sch041knDelTranKbn
     */
    public void setSch041knDelTranKbn(String sch041knDelTranKbn) {
        sch041knDelTranKbn__ = sch041knDelTranKbn;
    }
    /**
     * <p>sch041knDelValue を取得します。
     * @return sch041knDelValue
     */
    public String getSch041knDelValue() {
        return sch041knDelValue__;
    }
    /**
     * <p>sch041knDelValue をセットします。
     * @param sch041knDelValue sch041knDelValue
     */
    public void setSch041knDelValue(String sch041knDelValue) {
        sch041knDelValue__ = sch041knDelValue;
    }
    /**
     * <p>sch041knDspBiko を取得します。
     * @return sch041knDspBiko
     */
    public String getSch041knDspBiko() {
        return sch041knDspBiko__;
    }
    /**
     * <p>sch041knDspBiko をセットします。
     * @param sch041knDspBiko sch041knDspBiko
     */
    public void setSch041knDspBiko(String sch041knDspBiko) {
        sch041knDspBiko__ = sch041knDspBiko;
    }
    /**
     * <p>sch041knDspValue を取得します。
     * @return sch041knDspValue
     */
    public String getSch041knDspValue() {
        return sch041knDspValue__;
    }
    /**
     * <p>sch041knDspValue をセットします。
     * @param sch041knDspValue sch041knDspValue
     */
    public void setSch041knDspValue(String sch041knDspValue) {
        sch041knDspValue__ = sch041knDspValue;
    }
    /**
     * <p>sch041knFromDate を取得します。
     * @return sch041knFromDate
     */
    public String getSch041knFromDate() {
        return sch041knFromDate__;
    }
    /**
     * <p>sch041knFromDate をセットします。
     * @param sch041knFromDate sch041knFromDate
     */
    public void setSch041knFromDate(String sch041knFromDate) {
        sch041knFromDate__ = sch041knFromDate;
    }
    /**
     * <p>sch041knFromTime を取得します。
     * @return sch041knFromTime
     */
    public String getSch041knFromTime() {
        return sch041knFromTime__;
    }
    /**
     * <p>sch041knFromTime をセットします。
     * @param sch041knFromTime sch041knFromTime
     */
    public void setSch041knFromTime(String sch041knFromTime) {
        sch041knFromTime__ = sch041knFromTime;
    }
    /**
     * <p>sch041knToDate を取得します。
     * @return sch041knToDate
     */
    public String getSch041knToDate() {
        return sch041knToDate__;
    }
    /**
     * <p>sch041knToDate をセットします。
     * @param sch041knToDate sch041knToDate
     */
    public void setSch041knToDate(String sch041knToDate) {
        sch041knToDate__ = sch041knToDate;
    }
    /**
     * <p>sch041knToTime を取得します。
     * @return sch041knToTime
     */
    public String getSch041knToTime() {
        return sch041knToTime__;
    }
    /**
     * <p>sch041knToTime をセットします。
     * @param sch041knToTime sch041knToTime
     */
    public void setSch041knToTime(String sch041knToTime) {
        sch041knToTime__ = sch041knToTime;
    }
    /**
     * <p>sch041knAftDateList を取得します。
     * @return sch041knAftDateList
     */
    public ArrayList<String> getSch041knAftDateList() {
        return sch041knAftDateList__;
    }
    /**
     * <p>sch041knAftDateList をセットします。
     * @param sch041knAftDateList sch041knAftDateList
     */
    public void setSch041knAftDateList(ArrayList<String> sch041knAftDateList) {
        sch041knAftDateList__ = sch041knAftDateList;
    }
    /**
     * <p>sch041knBefDateList を取得します。
     * @return sch041knBefDateList
     */
    public ArrayList<String> getSch041knBefDateList() {
        return sch041knBefDateList__;
    }
    /**
     * <p>sch041knBefDateList をセットします。
     * @param sch041knBefDateList sch041knBefDateList
     */
    public void setSch041knBefDateList(ArrayList<String> sch041knBefDateList) {
        sch041knBefDateList__ = sch041knBefDateList;
    }
    /**
     * <p>sch041knSelectUsrList を取得します。
     * @return sch041knSelectUsrList
     */
    public ArrayList<CmnUsrmInfModel> getSch041knSelectUsrList() {
        return sch041knSelectUsrList__;
    }
    /**
     * <p>sch041knSelectUsrList をセットします。
     * @param sch041knSelectUsrList sch041knSelectUsrList
     */
    public void setSch041knSelectUsrList(
            ArrayList<CmnUsrmInfModel> sch041knSelectUsrList) {
        sch041knSelectUsrList__ = sch041knSelectUsrList;
    }
    /**
     * <p>sch041knTimeText を取得します。
     * @return sch041knTimeText
     * @see jp.groupsession.v2.sch.sch041kn.Sch041knParamModel#sch041knTimeText__
     */
    public String getSch041knTimeText() {
        return sch041knTimeText__;
    }
    /**
     * <p>sch041knTimeText をセットします。
     * @param sch041knTimeText sch041knTimeText
     * @see jp.groupsession.v2.sch.sch041kn.Sch041knParamModel#sch041knTimeText__
     */
    public void setSch041knTimeText(String sch041knTimeText) {
        sch041knTimeText__ = sch041knTimeText;
    }
    /**
     * <p>sch041knTargetText を取得します。
     * @return sch041knTargetText
     * @see jp.groupsession.v2.sch.sch041kn.Sch041knParamModel#sch041knTargetText__
     */
    public String getSch041knTargetText() {
        return sch041knTargetText__;
    }
    /**
     * <p>sch041knTargetText をセットします。
     * @param sch041knTargetText sch041knTargetText
     * @see jp.groupsession.v2.sch.sch041kn.Sch041knParamModel#sch041knTargetText__
     */
    public void setSch041knTargetText(String sch041knTargetText) {
        sch041knTargetText__ = sch041knTargetText;
    }
    /**
     * <p>sch041knBinSid を取得します。
     * @return sch041knBinSid
     * @see jp.groupsession.v2.sch.sch041kn.Sch041knParamModel#sch041knBinSid__
     */
    public String getSch041knBinSid() {
        return sch041knBinSid__;
    }
    /**
     * <p>sch041knBinSid をセットします。
     * @param sch041knBinSid sch041knBinSid
     * @see jp.groupsession.v2.sch.sch041kn.Sch041knParamModel#sch041knBinSid__
     */
    public void setSch041knBinSid(String sch041knBinSid) {
        sch041knBinSid__ = sch041knBinSid;
    }
    /**
     * <p>sch041knFileList を取得します。
     * @return sch041knFileList
     * @see jp.groupsession.v2.sch.sch041kn.Sch041knParamModel#sch041knFileList__
     */
    public List<LabelValueBean> getSch041knFileList() {
        return sch041knFileList__;
    }
    /**
     * <p>sch041knFileList をセットします。
     * @param sch041knFileList sch041knFileList
     * @see jp.groupsession.v2.sch.sch041kn.Sch041knParamModel#sch041knFileList__
     */
    public void setSch041knFileList(List<LabelValueBean> sch041knFileList) {
        sch041knFileList__ = sch041knFileList;
    }

}
