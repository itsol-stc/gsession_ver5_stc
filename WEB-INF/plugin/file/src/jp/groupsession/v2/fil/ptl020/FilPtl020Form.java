package jp.groupsession.v2.fil.ptl020;

import java.util.List;

import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.ptl010.FilPtl010Form;

import org.apache.struts.util.LabelValueBean;


/**
 * <p>ポータル キャビネットツリー管理画面フォーム
 * @author JTS
 */
public class FilPtl020Form extends FilPtl010Form {

    /** メイン表示一覧 */
    private List<FileCabinetModel> filptl020dspList__ = null;

    /** 選択キャビネットSID */
    private int filptl020selectFcbSid__ = 0;
    /** キャビネット選択済フラグ */
    private boolean filptl020selectFlg__ = false;

    /** プラグインポートレットリスト */
    private List<LabelValueBean> filPtl020PluginPortletList__ = null;
    /** 選択プラグインポートレット */
    private String ptl080PluginPortlet__ = "-1";

    /**
     * <p>filptl020dspList を取得します。
     * @return filptl020dspList
     */
    public List<FileCabinetModel> getFilptl020dspList() {
        return filptl020dspList__;
    }
    /**
     * <p>filptl020dspList をセットします。
     * @param filptl020dspList filptl020dspList
     */
    public void setFilptl020dspList(List<FileCabinetModel> filptl020dspList) {
        filptl020dspList__ = filptl020dspList;
    }
    /**
     * <p>filptl020selectFcbSid を取得します。
     * @return filptl020selectFcbSid
     */
    public int getFilptl020selectFcbSid() {
        return filptl020selectFcbSid__;
    }
    /**
     * <p>filptl020selectFcbSid をセットします。
     * @param filptl020selectFcbSid filptl020selectFcbSid
     */
    public void setFilptl020selectFcbSid(int filptl020selectFcbSid) {
        filptl020selectFcbSid__ = filptl020selectFcbSid;
    }
    /**
     * <p>filptl020selectFlg を取得します。
     * @return filptl020selectFlg
     */
    public boolean isFilptl020selectFlg() {
        return filptl020selectFlg__;
    }
    /**
     * <p>filptl020selectFlg をセットします。
     * @param filptl020selectFlg filptl020selectFlg
     */
    public void setFilptl020selectFlg(boolean filptl020selectFlg) {
        filptl020selectFlg__ = filptl020selectFlg;
    }
    /**
     * <p>filPtl020PluginPortletList を取得します。
     * @return filPtl020PluginPortletList
     */
    public List<LabelValueBean> getFilPtl020PluginPortletList() {
        return filPtl020PluginPortletList__;
    }
    /**
     * <p>filPtl020PluginPortletList をセットします。
     * @param filPtl020PluginPortletList filPtl020PluginPortletList
     */
    public void setFilPtl020PluginPortletList(
            List<LabelValueBean> filPtl020PluginPortletList) {
        filPtl020PluginPortletList__ = filPtl020PluginPortletList;
    }
    /**
     * <p>ptl080PluginPortlet を取得します。
     * @return ptl080PluginPortlet
     */
    public String getPtl080PluginPortlet() {
        return ptl080PluginPortlet__;
    }
    /**
     * <p>ptl080PluginPortlet をセットします。
     * @param ptl080PluginPortlet ptl080PluginPortlet
     */
    public void setPtl080PluginPortlet(String ptl080PluginPortlet) {
        ptl080PluginPortlet__ = ptl080PluginPortlet;
    }
}
