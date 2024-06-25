package jp.groupsession.v2.tcd.tcd140;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.tcd.tcd130.Tcd130ParamModel;

/**
 * <br>[機  能] タイムカード 管理者設定 ユーザ別時間帯設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd140ParamModel extends Tcd130ParamModel {

    /** 初期表示フラグ */
    private int tcd140initFlg__ = 0;
    /** 選択時間帯チェックボックス */
    private String[] tcd140SelectedTimeZone__ = null;
    /** デフォルト時間帯設定 */
    private int tcd140DefaultTimeZone__ = 0;
    /** 時間帯設定チェックボックス */
    private List<TcdTimezoneInfoModel> tcd140TimeZoneCheck__ = null;
    /** デフォルト設定セレクトボックス */
    private List<TcdTimezoneInfoModel> tcd140TimeZoneDefaultList__ = null;
    /** ユーザ一覧 */
    private List<CmnUsrmInfModel> tcd140SelectedUser__ = null;

    /**
     * <p>tcd140initFlg を取得します。
     * @return tcd140initFlg
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140initFlg__
     */
    public int getTcd140initFlg() {
        return tcd140initFlg__;
    }

    /**
     * <p>tcd140initFlg をセットします。
     * @param tcd140initFlg tcd140initFlg
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140initFlg__
     */
    public void setTcd140initFlg(int tcd140initFlg) {
        tcd140initFlg__ = tcd140initFlg;
    }

    /**
     * <p>tcd140SelectedTimeZone を取得します。
     * @return tcd140SelectedTimeZone
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140SelectedTimeZone__
     */
    public String[] getTcd140SelectedTimeZone() {
        return tcd140SelectedTimeZone__;
    }

    /**
     * <p>tcd140SelectedTimeZone をセットします。
     * @param tcd140SelectedTimeZone tcd140SelectedTimeZone
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140SelectedTimeZone__
     */
    public void setTcd140SelectedTimeZone(String[] tcd140SelectedTimeZone) {
        tcd140SelectedTimeZone__ = tcd140SelectedTimeZone;
    }

    /**
     * <p>tcd140DefaultTimeZone を取得します。
     * @return tcd140DefaultTimeZone
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140DefaultTimeZone__
     */
    public int getTcd140DefaultTimeZone() {
        return tcd140DefaultTimeZone__;
    }

    /**
     * <p>tcd140DefaultTimeZone をセットします。
     * @param tcd140DefaultTimeZone tcd140DefaultTimeZone
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140DefaultTimeZone__
     */
    public void setTcd140DefaultTimeZone(int tcd140DefaultTimeZone) {
        tcd140DefaultTimeZone__ = tcd140DefaultTimeZone;
    }

    /**
     * <p>tcd140TimeZoneCheck を取得します。
     * @return tcd140TimeZoneCheck
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140TimeZoneCheck__
     */
    public List<TcdTimezoneInfoModel> getTcd140TimeZoneCheck() {
        return tcd140TimeZoneCheck__;
    }

    /**
     * <p>tcd140TimeZoneCheck をセットします。
     * @param tcd140TimeZoneCheck tcd140TimeZoneCheck
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140TimeZoneCheck__
     */
    public void setTcd140TimeZoneCheck(
            List<TcdTimezoneInfoModel> tcd140TimeZoneCheck) {
        tcd140TimeZoneCheck__ = tcd140TimeZoneCheck;
    }

    /**
     * <p>tcd140TimeZoneDefaultList を取得します。
     * @return tcd140TimeZoneDefaultList
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140TimeZoneDefaultList__
     */
    public List<TcdTimezoneInfoModel> getTcd140TimeZoneDefaultList() {
        return tcd140TimeZoneDefaultList__;
    }

    /**
     * <p>tcd140TimeZoneDefaultList をセットします。
     * @param tcd140TimeZoneDefaultList tcd140TimeZoneDefaultList
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140TimeZoneDefaultList__
     */
    public void setTcd140TimeZoneDefaultList(
            List<TcdTimezoneInfoModel> tcd140TimeZoneDefaultList) {
        tcd140TimeZoneDefaultList__ = tcd140TimeZoneDefaultList;
    }

    /**
     * <p>tcd140SelectedUser を取得します。
     * @return tcd140SelectedUser
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140SelectedUser__
     */
    public List<CmnUsrmInfModel> getTcd140SelectedUser() {
        return tcd140SelectedUser__;
    }

    /**
     * <p>tcd140SelectedUser をセットします。
     * @param tcd140SelectedUser tcd140SelectedUser
     * @see jp.groupsession.v2.tcd.tcd140.Tcd140Form#tcd140SelectedUser__
     */
    public void setTcd140SelectedUser(List<CmnUsrmInfModel> tcd140SelectedUser) {
        tcd140SelectedUser__ = tcd140SelectedUser;
    }
}