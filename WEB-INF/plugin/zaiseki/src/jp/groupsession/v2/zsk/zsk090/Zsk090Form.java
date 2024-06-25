package jp.groupsession.v2.zsk.zsk090;

import java.util.List;

import jp.groupsession.v2.zsk.zsk070.Zsk070Form;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 在席管理 個人設定 自動リロード時間設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk090Form extends Zsk070Form {

    /** 自動リロード時間コンボ */
    private List < LabelValueBean > zsk090TimeLabelList__ = null;
    /** 自動リロード時間の選択値 */
    private String zsk090ReloadTime__ = null;

    /**
     * <p>zsk090ReloadTime を取得します。
     * @return zsk090ReloadTime
     */
    public String getZsk090ReloadTime() {
        return zsk090ReloadTime__;
    }
    /**
     * <p>zsk090ReloadTime をセットします。
     * @param zsk090ReloadTime zsk090ReloadTime
     */
    public void setZsk090ReloadTime(String zsk090ReloadTime) {
        zsk090ReloadTime__ = zsk090ReloadTime;
    }
    /**
     * <p>zsk090TimeLabelList を取得します。
     * @return zsk090TimeLabelList
     */
    public List<LabelValueBean> getZsk090TimeLabelList() {
        return zsk090TimeLabelList__;
    }
    /**
     * <p>zsk090TimeLabelList をセットします。
     * @param zsk090TimeLabelList zsk090TimeLabelList
     */
    public void setZsk090TimeLabelList(List<LabelValueBean> zsk090TimeLabelList) {
        zsk090TimeLabelList__ = zsk090TimeLabelList;
    }
}
