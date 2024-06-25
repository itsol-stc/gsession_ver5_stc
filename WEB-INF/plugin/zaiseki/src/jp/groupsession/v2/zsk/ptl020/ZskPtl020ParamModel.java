package jp.groupsession.v2.zsk.ptl020;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.zsk.ptl010.ZskPtl010ParamModel;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] 在席管理 ポートレット グループメンバー選択のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskPtl020ParamModel extends ZskPtl010ParamModel {

    /** メイン表示一覧 */
    private ArrayList<GroupModel> zskptl020dspList__ = null;

    /** 選択グループSID */
    private int zskptl020selectGrpSid__ = 0;
    /** グループ選択済フラグ */
    private boolean zskptl020selectFlg__ = false;

    /** プラグインポートレットリスト */
    private List<LabelValueBean> zskPtl020PluginPortletList__ = null;
    /** 選択プラグインポートレット */
    private String ptl080PluginPortlet__ = "-1";

    /**
     * <p>zskptl020selectGrpSid を取得します。
     * @return zskptl020selectGrpSid
     */
    public int getZskptl020selectGrpSid() {
        return zskptl020selectGrpSid__;
    }
    /**
     * <p>zskptl020selectGrpSid をセットします。
     * @param zskptl020selectGrpSid zskptl020selectGrpSid
     */
    public void setZskptl020selectGrpSid(int zskptl020selectGrpSid) {
        zskptl020selectGrpSid__ = zskptl020selectGrpSid;
    }
    /**
     * <p>zskptl020selectFlg を取得します。
     * @return zskptl020selectFlg
     */
    public boolean isZskptl020selectFlg() {
        return zskptl020selectFlg__;
    }
    /**
     * <p>zskptl020selectFlg をセットします。
     * @param zskptl020selectFlg zskptl020selectFlg
     */
    public void setZskptl020selectFlg(boolean zskptl020selectFlg) {
        zskptl020selectFlg__ = zskptl020selectFlg;
    }
    /**
     * <p>zskPtl020PluginPortletList を取得します。
     * @return zskPtl020PluginPortletList
     */
    public List<LabelValueBean> getZskPtl020PluginPortletList() {
        return zskPtl020PluginPortletList__;
    }
    /**
     * <p>zskPtl020PluginPortletList をセットします。
     * @param zskPtl020PluginPortletList zskPtl020PluginPortletList
     */
    public void setZskPtl020PluginPortletList(
            List<LabelValueBean> zskPtl020PluginPortletList) {
        zskPtl020PluginPortletList__ = zskPtl020PluginPortletList;
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
    /**
     * <p>zskptl020dspList を取得します。
     * @return zskptl020dspList
     */
    public ArrayList<GroupModel> getZskptl020dspList() {
        return zskptl020dspList__;
    }
    /**
     * <p>zskptl020dspList をセットします。
     * @param zskptl020dspList zskptl020dspList
     */
    public void setZskptl020dspList(ArrayList<GroupModel> zskptl020dspList) {
        zskptl020dspList__ = zskptl020dspList;
    }

}
