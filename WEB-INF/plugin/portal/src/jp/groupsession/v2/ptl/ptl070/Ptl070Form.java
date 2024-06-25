package jp.groupsession.v2.ptl.ptl070;

import java.util.List;

import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.ptl.AbstractPtlForm;
import jp.groupsession.v2.ptl.ptl070.model.Ptl070Model;

/**
 * <br>[機  能] ポータル プレビュー(ポップアップ)画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl070Form extends AbstractPtlForm {

    /** ポータル名 */
    private String ptl070portalName__ = null;
    /** ポータル表示リスト 上 */
    private List<Ptl070Model> ptl070topList__ = null;
    /** ポータル表示リスト 下 */
    private List<Ptl070Model> ptl070bottomList__ = null;
    /** ポータル表示リスト 左 */
    private List<Ptl070Model> ptl070leftList__ = null;
    /** ポータル表示リスト 中 */
    private List<Ptl070Model> ptl070centerList__ = null;
    /** ポータル表示リスト 右 */
    private List<Ptl070Model> ptl070rightList__ = null;

    /** レイアウト表示区分 上 */
    private int ptl070areaTop__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 下 */
    private int ptl070areaBottom__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 左 */
    private int ptl070areaLeft__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 中 */
    private int ptl070areaCenter__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 右 */
    private int ptl070areaRight__ = GSConstPortal.LAYOUT_VIEW_OFF;

    /** アイテムリスト一覧(上) */
    private String[] ptl070PortalItemHead__ = null;
    /** アイテムリスト一覧(下) */
    private String[] ptl070PortalItemBottom__ = null;
    /** アイテムリスト一覧(左) */
    private String[] ptl070PortalItemLeft__ = null;
    /** アイテムリスト一覧(中) */
    private String[] ptl070PortalItemCenter__ = null;
    /** アイテムリスト一覧(右) */
    private String[] ptl070PortalItemRight__ = null;

    /** インフォーメーションのリスト */
    private List<MainInfoMessageModel> infoMsgs__ = null;

    /** js格納list */
    private List<String> jsList__ = null;
    /** css格納list */
    private List<String> cssList__ = null;
    /** url格納list */
    private List<Ptl070Model> urlList__ = null;

    /**
     * @return ptl070portalName
     */
    public String getPtl070portalName() {
        return ptl070portalName__;
    }
    /**
     * @param ptl070portalName セットする ptl070portalName
     */
    public void setPtl070portalName(String ptl070portalName) {
        ptl070portalName__ = ptl070portalName;
    }
    /**
     * @return ptl070topList
     */
    public List<Ptl070Model> getPtl070topList() {
        return ptl070topList__;
    }
    /**
     * @param ptl070topList セットする ptl070topList
     */
    public void setPtl070topList(List<Ptl070Model> ptl070topList) {
        ptl070topList__ = ptl070topList;
    }
    /**
     * @return ptl070bottomList
     */
    public List<Ptl070Model> getPtl070bottomList() {
        return ptl070bottomList__;
    }
    /**
     * @param ptl070bottomList セットする ptl070bottomList
     */
    public void setPtl070bottomList(List<Ptl070Model> ptl070bottomList) {
        ptl070bottomList__ = ptl070bottomList;
    }
    /**
     * @return ptl070leftList
     */
    public List<Ptl070Model> getPtl070leftList() {
        return ptl070leftList__;
    }
    /**
     * @param ptl070leftList セットする ptl070leftList
     */
    public void setPtl070leftList(List<Ptl070Model> ptl070leftList) {
        ptl070leftList__ = ptl070leftList;
    }
    /**
     * @return ptl070centerList
     */
    public List<Ptl070Model> getPtl070centerList() {
        return ptl070centerList__;
    }
    /**
     * @param ptl070centerList セットする ptl070centerList
     */
    public void setPtl070centerList(List<Ptl070Model> ptl070centerList) {
        ptl070centerList__ = ptl070centerList;
    }
    /**
     * @return ptl070rightList
     */
    public List<Ptl070Model> getPtl070rightList() {
        return ptl070rightList__;
    }
    /**
     * @param ptl070rightList セットする ptl070rightList
     */
    public void setPtl070rightList(List<Ptl070Model> ptl070rightList) {
        ptl070rightList__ = ptl070rightList;
    }
    /**
     * @return ptl070areaTop
     */
    public int getPtl070areaTop() {
        return ptl070areaTop__;
    }
    /**
     * @param ptl070areaTop セットする ptl070areaTop
     */
    public void setPtl070areaTop(int ptl070areaTop) {
        ptl070areaTop__ = ptl070areaTop;
    }
    /**
     * @return ptl070areaBottom
     */
    public int getPtl070areaBottom() {
        return ptl070areaBottom__;
    }
    /**
     * @param ptl070areaBottom セットする ptl070areaBottom
     */
    public void setPtl070areaBottom(int ptl070areaBottom) {
        ptl070areaBottom__ = ptl070areaBottom;
    }
    /**
     * @return ptl070areaLeft
     */
    public int getPtl070areaLeft() {
        return ptl070areaLeft__;
    }
    /**
     * @param ptl070areaLeft セットする ptl070areaLeft
     */
    public void setPtl070areaLeft(int ptl070areaLeft) {
        ptl070areaLeft__ = ptl070areaLeft;
    }
    /**
     * @return ptl070areaCenter
     */
    public int getPtl070areaCenter() {
        return ptl070areaCenter__;
    }
    /**
     * @param ptl070areaCenter セットする ptl070areaCenter
     */
    public void setPtl070areaCenter(int ptl070areaCenter) {
        ptl070areaCenter__ = ptl070areaCenter;
    }
    /**
     * @return ptl070areaRight
     */
    public int getPtl070areaRight() {
        return ptl070areaRight__;
    }
    /**
     * @param ptl070areaRight セットする ptl070areaRight
     */
    public void setPtl070areaRight(int ptl070areaRight) {
        ptl070areaRight__ = ptl070areaRight;
    }
    /**
     * @return ptl070PortalItemHead
     */
    public String[] getPtl070PortalItemHead() {
        return ptl070PortalItemHead__;
    }
    /**
     * @param ptl070PortalItemHead セットする ptl070PortalItemHead
     */
    public void setPtl070PortalItemHead(String[] ptl070PortalItemHead) {
        ptl070PortalItemHead__ = ptl070PortalItemHead;
    }
    /**
     * @return ptl070PortalItemBottom
     */
    public String[] getPtl070PortalItemBottom() {
        return ptl070PortalItemBottom__;
    }
    /**
     * @param ptl070PortalItemBottom セットする ptl070PortalItemBottom
     */
    public void setPtl070PortalItemBottom(String[] ptl070PortalItemBottom) {
        ptl070PortalItemBottom__ = ptl070PortalItemBottom;
    }
    /**
     * @return ptl070PortalItemLeft
     */
    public String[] getPtl070PortalItemLeft() {
        return ptl070PortalItemLeft__;
    }
    /**
     * @param ptl070PortalItemLeft セットする ptl070PortalItemLeft
     */
    public void setPtl070PortalItemLeft(String[] ptl070PortalItemLeft) {
        ptl070PortalItemLeft__ = ptl070PortalItemLeft;
    }
    /**
     * @return ptl070PortalItemCenter
     */
    public String[] getPtl070PortalItemCenter() {
        return ptl070PortalItemCenter__;
    }
    /**
     * @param ptl070PortalItemCenter セットする ptl070PortalItemCenter
     */
    public void setPtl070PortalItemCenter(String[] ptl070PortalItemCenter) {
        ptl070PortalItemCenter__ = ptl070PortalItemCenter;
    }
    /**
     * @return ptl070PortalItemRight
     */
    public String[] getPtl070PortalItemRight() {
        return ptl070PortalItemRight__;
    }
    /**
     * @param ptl070PortalItemRight セットする ptl070PortalItemRight
     */
    public void setPtl070PortalItemRight(String[] ptl070PortalItemRight) {
        ptl070PortalItemRight__ = ptl070PortalItemRight;
    }
    /**
     * @return infoMsgs
     */
    public List<MainInfoMessageModel> getInfoMsgs() {
        return infoMsgs__;
    }
    /**
     * @param infoMsgs セットする infoMsgs
     */
    public void setInfoMsgs(List<MainInfoMessageModel> infoMsgs) {
        infoMsgs__ = infoMsgs;
    }
    /**
     * @return jsList
     */
    public List<String> getJsList() {
        return jsList__;
    }
    /**
     * @param jsList セットする jsList
     */
    public void setJsList(List<String> jsList) {
        jsList__ = jsList;
    }
    /**
     * @return cssList
     */
    public List<String> getCssList() {
        return cssList__;
    }
    /**
     * @param cssList セットする cssList
     */
    public void setCssList(List<String> cssList) {
        cssList__ = cssList;
    }
    /**
     * @return urlList
     */
    public List<Ptl070Model> getUrlList() {
        return urlList__;
    }
    /**
     * @param urlList セットする urlList
     */
    public void setUrlList(List<Ptl070Model> urlList) {
        urlList__ = urlList;
    }
}
