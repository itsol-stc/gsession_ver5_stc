package jp.groupsession.v2.tcd.tcd120;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.tcd.model.TcdTimezoneInfoModel;
import jp.groupsession.v2.tcd.tcd030.Tcd030ParamModel;

/**
 * <br>[機  能] タイムカード 管理者設定 時間設定一覧画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Tcd120ParamModel extends Tcd030ParamModel {

    /**  時間帯情報編集モード */
    private int timezoneCmdMode__ = GSConst.CMDMODE_ADD;
    /** 編集対象 時間帯情報SID */
    private int timezoneSid__ = -1;

    /** 時間帯リスト */
    private List<TcdTimezoneInfoModel> tcd120TimezoneList__;
    /** チェックされている並び順 */
    private String tcd120SortRadio__ = null;
    /** 並び替え対象の時間帯名称 */
    private String[] tcd120sortLabel__ = null;

    /**
     * <p>timezoneCmdMode を取得します。
     * @return timezoneCmdMode
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#timezoneCmdMode__
     */
    public int getTimezoneCmdMode() {
        return timezoneCmdMode__;
    }
    /**
     * <p>timezoneCmdMode をセットします。
     * @param timezoneCmdMode timezoneCmdMode
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#timezoneCmdMode__
     */
    public void setTimezoneCmdMode(int timezoneCmdMode) {
        timezoneCmdMode__ = timezoneCmdMode;
    }
    /**
     * <p>tcd120TimezoneSid を取得します。
     * @return tcd120TimezoneSid
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#timezoneSid__
     */
    public int getTimezoneSid() {
        return timezoneSid__;
    }
    /**
     * <p>tcd120TimezoneSid をセットします。
     * @param tcd120TimezoneSid tcd120TimezoneSid
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#timezoneSid__
     */
    public void setTimezoneSid(int timezoneSid) {
        timezoneSid__ = timezoneSid;
    }
    /**
     * <p>tcd120TimezoneList を取得します。
     * @return tcd120TimezoneList
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#tcd120TimezoneList__
     */
    public List<TcdTimezoneInfoModel> getTcd120TimezoneList() {
        return tcd120TimezoneList__;
    }
    /**
     * <p>tcd120TimezoneList をセットします。
     * @param tcd120TimezoneList tcd120TimezoneList
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#tcd120TimezoneList__
     */
    public void setTcd120TimezoneList(
            List<TcdTimezoneInfoModel> tcd120TimezoneList) {
        tcd120TimezoneList__ = tcd120TimezoneList;
    }
    /**
     * <p>tcd120SortRadio を取得します。
     * @return tcd120SortRadio
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#tcd120SortRadio__
     */
    public String getTcd120SortRadio() {
        return tcd120SortRadio__;
    }
    /**
     * <p>tcd120SortRadio をセットします。
     * @param tcd120SortRadio tcd120SortRadio
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#tcd120SortRadio__
     */
    public void setTcd120SortRadio(String tcd120SortRadio) {
        tcd120SortRadio__ = tcd120SortRadio;
    }
    /**
     * <p>tcd120sortLabel を取得します。
     * @return tcd120sortLabel
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#tcd120sortLabel__
     */
    public String[] getTcd120sortLabel() {
        return tcd120sortLabel__;
    }
    /**
     * <p>tcd120sortLabel をセットします。
     * @param tcd120sortLabel tcd120sortLabel
     * @see jp.groupsession.v2.tcd.tcd120.Tcd120ParamModel#tcd120sortLabel__
     */
    public void setTcd120sortLabel(String[] tcd120sortLabel) {
        tcd120sortLabel__ = tcd120sortLabel;
    }
}