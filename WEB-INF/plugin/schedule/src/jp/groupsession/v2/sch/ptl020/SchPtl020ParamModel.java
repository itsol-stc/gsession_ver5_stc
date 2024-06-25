package jp.groupsession.v2.sch.ptl020;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.sch.ptl010.SchPtl010ParamModel;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] スケジュール ポートレット グループスケジュール選択画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchPtl020ParamModel extends SchPtl010ParamModel {

    /** メイン表示一覧 */
    private ArrayList<GroupModel> schptl020dspList__ = null;

    /** 選択グループSID */
    private int schptl020selectGrpSid__ = 0;
    /** グループ選択済フラグ */
    private boolean schptl020selectFlg__ = false;

    /** プラグインポートレットリスト */
    private List<LabelValueBean> schPtl020PluginPortletList__ = null;
    /** 選択プラグインポートレット */
    private String ptl080PluginPortlet__ = "-1";

    /**
     * <p>schptl020dspList を取得します。
     * @return schptl020dspList
     */
    public ArrayList<GroupModel> getSchptl020dspList() {
        return schptl020dspList__;
    }
    /**
     * <p>schptl020dspList をセットします。
     * @param schptl020dspList schptl020dspList
     */
    public void setSchptl020dspList(ArrayList<GroupModel> schptl020dspList) {
        schptl020dspList__ = schptl020dspList;
    }
    /**
     * <p>schptl020selectGrpSid を取得します。
     * @return schptl020selectGrpSid
     */
    public int getSchptl020selectGrpSid() {
        return schptl020selectGrpSid__;
    }
    /**
     * <p>schptl020selectGrpSid をセットします。
     * @param schptl020selectGrpSid schptl020selectGrpSid
     */
    public void setSchptl020selectGrpSid(int schptl020selectGrpSid) {
        schptl020selectGrpSid__ = schptl020selectGrpSid;
    }
    /**
     * <p>schptl020selectFlg を取得します。
     * @return schptl020selectFlg
     */
    public boolean isSchptl020selectFlg() {
        return schptl020selectFlg__;
    }
    /**
     * <p>schptl020selectFlg をセットします。
     * @param schptl020selectFlg schptl020selectFlg
     */
    public void setSchptl020selectFlg(boolean schptl020selectFlg) {
        schptl020selectFlg__ = schptl020selectFlg;
    }
    /**
     * <p>schPtl020PluginPortletList を取得します。
     * @return schPtl020PluginPortletList
     */
    public List<LabelValueBean> getSchPtl020PluginPortletList() {
        return schPtl020PluginPortletList__;
    }
    /**
     * <p>schPtl020PluginPortletList をセットします。
     * @param schPtl020PluginPortletList schPtl020PluginPortletList
     */
    public void setSchPtl020PluginPortletList(
            List<LabelValueBean> schPtl020PluginPortletList) {
        schPtl020PluginPortletList__ = schPtl020PluginPortletList;
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
