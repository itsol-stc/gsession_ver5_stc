package jp.groupsession.v2.ptl.ptl080;

import java.util.List;

import jp.groupsession.v2.ptl.ptl040.Ptl040Form;
import jp.groupsession.v2.ptl.ptl080.model.Ptl080Model;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ポータル プラグイン選択画面(ポップアップ)のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl080Form extends Ptl040Form {

    /** メイン表示一覧 */
    private List<Ptl080Model> ptl080dspList__ = null;
    /** 選択プラグインID */
    private String plt080pluginId__ = null;
    /** 選択画面ID */
    private String ptl080dspId__ = null;
    /** プラグイン選択済フラグ */
    private boolean ptl080selectFlg__ = false;

    /** プラグインポートレットリスト */
    private List<LabelValueBean> ptl080PluginPortletList__ = null;
    /** 選択プラグインポートレット */
    private String ptl080PluginPortlet__ = "-1";

    /**
     * <p>ptl080dspList を取得します。
     * @return ptl080dspList
     */
    public List<Ptl080Model> getPtl080dspList() {
        return ptl080dspList__;
    }

    /**
     * <p>ptl080dspList をセットします。
     * @param ptl080dspList ptl080dspList
     */
    public void setPtl080dspList(List<Ptl080Model> ptl080dspList) {
        ptl080dspList__ = ptl080dspList;
    }

    /**
     * <p>plt080pluginId を取得します。
     * @return plt080pluginId
     */
    public String getPlt080pluginId() {
        return plt080pluginId__;
    }

    /**
     * <p>plt080pluginId をセットします。
     * @param plt080pluginId plt080pluginId
     */
    public void setPlt080pluginId(String plt080pluginId) {
        plt080pluginId__ = plt080pluginId;
    }

    /**
     * <p>ptl080dspId を取得します。
     * @return ptl080dspId
     */
    public String getPtl080dspId() {
        return ptl080dspId__;
    }

    /**
     * <p>ptl080dspId をセットします。
     * @param ptl080dspId ptl080dspId
     */
    public void setPtl080dspId(String ptl080dspId) {
        ptl080dspId__ = ptl080dspId;
    }

    /**
     * <p>ptl080selectFlg を取得します。
     * @return ptl080selectFlg
     */
    public boolean isPtl080selectFlg() {
        return ptl080selectFlg__;
    }

    /**
     * <p>ptl080selectFlg をセットします。
     * @param ptl080selectFlg ptl080selectFlg
     */
    public void setPtl080selectFlg(boolean ptl080selectFlg) {
        ptl080selectFlg__ = ptl080selectFlg;
    }

    /**
     * <p>ptl080PluginPortletList を取得します。
     * @return ptl080PluginPortletList
     */
    public List<LabelValueBean> getPtl080PluginPortletList() {
        return ptl080PluginPortletList__;
    }

    /**
     * <p>ptl080PluginPortletList をセットします。
     * @param ptl080PluginPortletList ptl080PluginPortletList
     */
    public void setPtl080PluginPortletList(
            List<LabelValueBean> ptl080PluginPortletList) {
        ptl080PluginPortletList__ = ptl080PluginPortletList;
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
