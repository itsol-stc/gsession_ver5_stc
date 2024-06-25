package jp.groupsession.v2.tcd.tcd060;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.tcd.model.TcdTimezoneMeiModel;
import jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel;

/**
 * <br>[機  能] タイムカード 管理者設定 時間帯設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd060ParamModel extends Tcd120ParamModel {

    /** 初期表示フラグ */
    private int tcd060initFlg__ = 0;
    /** デフォルト時間帯フラグ */
    private int tcd060defTimezoneFlg__ = 0;
    /** 追加する時間帯区分*/
    private String addTimezoneKbn__;
    /** 編集する時間帯SID*/
    private String editTimezoneSid__;
    /** 使用区分設定フラグ */
    private int ttiUseSetFlg__ = GSConstTimecard.TIMEZONE_USE_KBN_OK;

    /** 時間帯設定名称*/
    private String tcd060Name__;
    /** 略称*/
    private String tcd060Ryaku__;
    /** 使用区分 1:可能 0:不可*/
    private int tcd060UseFlg__;
    /** 休日区分 1:休日 0:平日*/
    private int tcd060Holiday__;

    /** 時間帯明細部 */
    private ArrayList<TcdTimezoneMeiModel> tcd060TimezoneMeiList__;

    /** ダイアログ用変数 */

    /** 時間帯識別用番号 */
    private int tcd060ZoneNo__;
    /** 時間帯区分 */
    private String tcd060ZoneKbn__;
    /** 開始 時 */
    private String tcd060FrH__;
    /** 開始 分 */
    private String tcd060FrM__;
    /** 終了 時 */
    private String tcd060ToH__;
    /** 終了 分 */
    private String tcd060ToM__;
    /** ラジオ */
    private String tcd060TimeKbn__;
    /** ラベル 区分 */
    private List < LabelValueBean > tcd060ZoneLabel__ = null;
    /** ラベル 時 */
    private List < LabelValueBean > tcd060HourLabel__ = null;
    /** ラベル 分 */
    private List < LabelValueBean > tcd060MinuteLabel__ = null;

    /**
     * <p>tcd060initFlg を取得します。
     * @return tcd060initFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060initFlg__
     */
    public int getTcd060initFlg() {
        return tcd060initFlg__;
    }
    /**
     * <p>tcd060initFlg をセットします。
     * @param tcd060initFlg tcd060initFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060initFlg__
     */
    public void setTcd060initFlg(int tcd060initFlg) {
        tcd060initFlg__ = tcd060initFlg;
    }/**
     * <p>tcd060defTimezoneFlg を取得します。
     * @return tcd060defTimezoneFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060defTimezoneFlg__
     */
    public int getTcd060defTimezoneFlg() {
        return tcd060defTimezoneFlg__;
    }
    /**
     * <p>tcd060defTimezoneFlg をセットします。
     * @param tcd060defTimezoneFlg tcd060defTimezoneFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060Form#tcd060defTimezoneFlg__
     */
    public void setTcd060defTimezoneFlg(int tcd060defTimezoneFlg) {
        tcd060defTimezoneFlg__ = tcd060defTimezoneFlg;
    }
    /**
     * <p>addTimezoneKbn を取得します。
     * @return addTimezoneKbn
     */
    public String getAddTimezoneKbn() {
        return addTimezoneKbn__;
    }
    /**
     * <p>addTimezoneKbn をセットします。
     * @param addTimezoneKbn addTimezoneKbn
     */
    public void setAddTimezoneKbn(String addTimezoneKbn) {
        addTimezoneKbn__ = addTimezoneKbn;
    }
    /**
     * <p>editTimezoneSid を取得します。
     * @return editTimezoneSid
     */
    public String getEditTimezoneSid() {
        return editTimezoneSid__;
    }
    /**
     * <p>editTimezoneSid をセットします。
     * @param editTimezoneSid editTimezoneSid
     */
    public void setEditTimezoneSid(String editTimezoneSid) {
        editTimezoneSid__ = editTimezoneSid;
    }
    /**
     * <p>ttiUseSetFlg を取得します。
     * @return ttiUseSetFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#ttiUseSetFlg__
     */
    public int getTtiUseSetFlg() {
        return ttiUseSetFlg__;
    }
    /**
     * <p>ttiUseSetFlg をセットします。
     * @param ttiUseSetFlg ttiUseSetFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#ttiUseSetFlg__
     */
    public void setTtiUseSetFlg(int ttiUseSetFlg) {
        ttiUseSetFlg__ = ttiUseSetFlg;
    }
    /**
     * <p>tcd060TimezoneMeiList を取得します。
     * @return tcd060TimezoneMeiList
     */
    public ArrayList<TcdTimezoneMeiModel> getTcd060TimezoneMeiList() {
        return tcd060TimezoneMeiList__;
    }
    /**
     * <p>tcd060TimezoneMeiList をセットします。
     * @param tcd060TimezoneMeiList tcd060TimezoneMeiList
     */
    public void setTcd060TimezoneMeiList(
            ArrayList<TcdTimezoneMeiModel> tcd060TimezoneMeiList) {
        tcd060TimezoneMeiList__ = tcd060TimezoneMeiList;
    }
    /**
     * <p>tcd060Name を取得します。
     * @return tcd060Name
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060Name__
     */
    public String getTcd060Name() {
        return tcd060Name__;
    }
    /**
     * <p>tcd060Name をセットします。
     * @param tcd060Name tcd060Name
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060Name__
     */
    public void setTcd060Name(String tcd060Name) {
        tcd060Name__ = tcd060Name;
    }
    /**
     * <p>tcd060Ryaku を取得します。
     * @return tcd060Ryaku
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060Ryaku__
     */
    public String getTcd060Ryaku() {
        return tcd060Ryaku__;
    }
    /**
     * <p>tcd060Ryaku をセットします。
     * @param tcd060Ryaku tcd060Ryaku
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060Ryaku__
     */
    public void setTcd060Ryaku(String tcd060Ryaku) {
        tcd060Ryaku__ = tcd060Ryaku;
    }
    /**
     * <p>tcd060UseFlg を取得します。
     * @return tcd060UseFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060UseFlg__
     */
    public int getTcd060UseFlg() {
        return tcd060UseFlg__;
    }
    /**
     * <p>tcd060UseFlg をセットします。
     * @param tcd060UseFlg tcd060UseFlg
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060UseFlg__
     */
    public void setTcd060UseFlg(int tcd060UseFlg) {
        tcd060UseFlg__ = tcd060UseFlg;
    }
    /**
     * <p>tcd060Holiday を取得します。
     * @return tcd060Holiday
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060Holiday__
     */
    public int getTcd060Holiday() {
        return tcd060Holiday__;
    }
    /**
     * <p>tcd060Holiday をセットします。
     * @param tcd060Holiday tcd060Holiday
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060Holiday__
     */
    public void setTcd060Holiday(int tcd060Holiday) {
        tcd060Holiday__ = tcd060Holiday;
    }
    /**
     * <p>tcd060ZoneKbn を取得します。
     * @return tcd060ZoneKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ZoneKbn__
     */
    public String getTcd060ZoneKbn() {
        return tcd060ZoneKbn__;
    }
    /**
     * <p>tcd060ZoneKbn をセットします。
     * @param tcd060ZoneKbn tcd060ZoneKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ZoneKbn__
     */
    public void setTcd060ZoneKbn(String tcd060ZoneKbn) {
        tcd060ZoneKbn__ = tcd060ZoneKbn;
    }
    /**
     * <p>tcd060FrH を取得します。
     * @return tcd060FrH
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060FrH__
     */
    public String getTcd060FrH() {
        return tcd060FrH__;
    }
    /**
     * <p>tcd060FrH をセットします。
     * @param tcd060FrH tcd060FrH
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060FrH__
     */
    public void setTcd060FrH(String tcd060FrH) {
        tcd060FrH__ = tcd060FrH;
    }
    /**
     * <p>tcd060FrM を取得します。
     * @return tcd060FrM
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060FrM__
     */
    public String getTcd060FrM() {
        return tcd060FrM__;
    }
    /**
     * <p>tcd060FrM をセットします。
     * @param tcd060FrM tcd060FrM
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060FrM__
     */
    public void setTcd060FrM(String tcd060FrM) {
        tcd060FrM__ = tcd060FrM;
    }
    /**
     * <p>tcd060ToH を取得します。
     * @return tcd060ToH
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ToH__
     */
    public String getTcd060ToH() {
        return tcd060ToH__;
    }
    /**
     * <p>tcd060ToH をセットします。
     * @param tcd060ToH tcd060ToH
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ToH__
     */
    public void setTcd060ToH(String tcd060ToH) {
        tcd060ToH__ = tcd060ToH;
    }
    /**
     * <p>tcd060ToM を取得します。
     * @return tcd060ToM
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ToM__
     */
    public String getTcd060ToM() {
        return tcd060ToM__;
    }
    /**
     * <p>tcd060ToM をセットします。
     * @param tcd060ToM tcd060ToM
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ToM__
     */
    public void setTcd060ToM(String tcd060ToM) {
        tcd060ToM__ = tcd060ToM;
    }
    /**
     * <p>tcd060ZoneNo を取得します。
     * @return tcd060ZoneNo
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ZoneNo__
     */
    public int getTcd060ZoneNo() {
        return tcd060ZoneNo__;
    }
    /**
     * <p>tcd060ZoneNo をセットします。
     * @param tcd060ZoneNo tcd060ZoneNo
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ZoneNo__
     */
    public void setTcd060ZoneNo(int tcd060ZoneNo) {
        tcd060ZoneNo__ = tcd060ZoneNo;
    }
    /**
     * <p>tcd060TimeKbn を取得します。
     * @return tcd060TimeKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060TimeKbn__
     */
    public String getTcd060TimeKbn() {
        return tcd060TimeKbn__;
    }
    /**
     * <p>tcd060TimeKbn をセットします。
     * @param tcd060TimeKbn tcd060TimeKbn
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060TimeKbn__
     */
    public void setTcd060TimeKbn(String tcd060TimeKbn) {
        tcd060TimeKbn__ = tcd060TimeKbn;
    }
    /**
     * <p>tcd060ZoneLabel を取得します。
     * @return tcd060ZoneLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ZoneLabel__
     */
    public List<LabelValueBean> getTcd060ZoneLabel() {
        return tcd060ZoneLabel__;
    }
    /**
     * <p>tcd060ZoneLabel をセットします。
     * @param tcd060ZoneLabel tcd060ZoneLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060ZoneLabel__
     */
    public void setTcd060ZoneLabel(List<LabelValueBean> tcd060ZoneLabel) {
        tcd060ZoneLabel__ = tcd060ZoneLabel;
    }
    /**
     * <p>tcd060HourLabel を取得します。
     * @return tcd060HourLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060HourLabel__
     */
    public List<LabelValueBean> getTcd060HourLabel() {
        return tcd060HourLabel__;
    }
    /**
     * <p>tcd060HourLabel をセットします。
     * @param tcd060HourLabel tcd060HourLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060HourLabel__
     */
    public void setTcd060HourLabel(List<LabelValueBean> tcd060HourLabel) {
        tcd060HourLabel__ = tcd060HourLabel;
    }
    /**
     * <p>tcd060MinuteLabel を取得します。
     * @return tcd060MinuteLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060MinuteLabel__
     */
    public List<LabelValueBean> getTcd060MinuteLabel() {
        return tcd060MinuteLabel__;
    }
    /**
     * <p>tcd060MinuteLabel をセットします。
     * @param tcd060MinuteLabel tcd060MinuteLabel
     * @see jp.groupsession.v2.tcd.tcd060.Tcd060ParamModel#tcd060MinuteLabel__
     */
    public void setTcd060MinuteLabel(List<LabelValueBean> tcd060MinuteLabel) {
        tcd060MinuteLabel__ = tcd060MinuteLabel;
    }
}