package jp.groupsession.v2.bmk.ptl020;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

import org.apache.struts.util.LabelValueBean;


/**
 * <p>ポータル グループブックマーク管理画面のフォーム
 * @author JTS
 */
public class BmkPtl020ParamModel extends AbstractParamModel {
    /** グループ名一覧 */
    List<String> groupNameList__ = null;
    /** グループSID一覧 */
    List<String> groupSidList__ = null;
    /** グループモデル一覧 */
    ArrayList<GroupModel> tree__ = null;

    /** ポータルSID */
    private int ptlPortalSid__ = 0;
    /** 選択プラグインポートレット */
    private String ptl080PluginPortlet__ = "-1";
    /** プラグインポートレットコンボ */
    private List<LabelValueBean> portletTypeCombo__ = null;

    /** フォーラム選択フラグ */
    private boolean bmkptl020selectFlg__ = false;

    /** 選択グループ */
    private int bmkptl020GrpSid__ = -1;

    /**
     * @return groupNameList
     */
    public List<String> getGroupNameList() {
        return groupNameList__;
    }
    /**
     * @param groupNameList セットする groupNameList
     */
    public void setGroupNameList(List<String> groupNameList) {
        groupNameList__ = groupNameList;
    }
    /**
     * @return groupSidList
     */
    public List<String> getGroupSidList() {
        return groupSidList__;
    }
    /**
     * @param groupSidList セットする groupSidList
     */
    public void setGroupSidList(List<String> groupSidList) {
        groupSidList__ = groupSidList;
    }
    /**
     * @return tree
     */
    public ArrayList<GroupModel> getTree() {
        return tree__;
    }
    /**
     * @param tree セットする tree
     */
    public void setTree(ArrayList<GroupModel> tree) {
        tree__ = tree;
    }
    /**
     * @return ptlPortalSid
     */
    public int getPtlPortalSid() {
        return ptlPortalSid__;
    }
    /**
     * @param ptlPortalSid セットする ptlPortalSid
     */
    public void setPtlPortalSid(int ptlPortalSid) {
        ptlPortalSid__ = ptlPortalSid;
    }
    /**
     * @return ptl080PluginPortlet
     */
    public String getPtl080PluginPortlet() {
        return ptl080PluginPortlet__;
    }
    /**
     * @param ptl080PluginPortlet セットする ptl080PluginPortlet
     */
    public void setPtl080PluginPortlet(String ptl080PluginPortlet) {
        ptl080PluginPortlet__ = ptl080PluginPortlet;
    }
    /**
     * @return portletTypeCombo
     */
    public List<LabelValueBean> getPortletTypeCombo() {
        return portletTypeCombo__;
    }
    /**
     * @param portletTypeCombo セットする portletTypeCombo
     */
    public void setPortletTypeCombo(List<LabelValueBean> portletTypeCombo) {
        portletTypeCombo__ = portletTypeCombo;
    }
    /**
     * @return bmkptl020selectFlg
     */
    public boolean isBmkptl020selectFlg() {
        return bmkptl020selectFlg__;
    }
    /**
     * @param bmkptl020selectFlg セットする bmkptl020selectFlg
     */
    public void setBmkptl020selectFlg(boolean bmkptl020selectFlg) {
        bmkptl020selectFlg__ = bmkptl020selectFlg;
    }
    /**
     * @return bmkptl020GrpSid
     */
    public int getBmkptl020GrpSid() {
        return bmkptl020GrpSid__;
    }
    /**
     * @param bmkptl020GrpSid セットする bmkptl020GrpSid
     */
    public void setBmkptl020GrpSid(int bmkptl020GrpSid) {
        bmkptl020GrpSid__ = bmkptl020GrpSid;
    }
}
