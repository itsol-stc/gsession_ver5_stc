package jp.groupsession.v2.ptl.ptl040;

import java.util.List;

import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.ptl.ptl030.Ptl030ParamModel;
import jp.groupsession.v2.ptl.ptl040.model.Ptl040Model;

/**
 * <br>[機  能] ポータル ポータル詳細画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl040ParamModel extends Ptl030ParamModel {
    /** ポータル名 */
    private String ptl040portalName__ = null;
    /** ポータル表示リスト 上 */
    private List<Ptl040Model> ptl040topList__ = null;
    /** ポータル表示リスト 下 */
    private List<Ptl040Model> ptl040bottomList__ = null;
    /** ポータル表示リスト 左 */
    private List<Ptl040Model> ptl040leftList__ = null;
    /** ポータル表示リスト 中 */
    private List<Ptl040Model> ptl040centerList__ = null;
    /** ポータル表示リスト 右 */
    private List<Ptl040Model> ptl040rightList__ = null;

    /** レイアウト表示区分 上 */
    private int ptl040areaTop__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 下 */
    private int ptl040areaBottom__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 左 */
    private int ptl040areaLeft__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 中 */
    private int ptl040areaCenter__ = GSConstPortal.LAYOUT_VIEW_OFF;
    /** レイアウト表示区分 右 */
    private int ptl040areaRight__ = GSConstPortal.LAYOUT_VIEW_OFF;

    /** アイテムリスト一覧(上) */
    private String[] ptl040PortalItemHead__ = null;
    /** アイテムリスト一覧(下) */
    private String[] ptl040PortalItemBottom__ = null;
    /** アイテムリスト一覧(左) */
    private String[] ptl040PortalItemLeft__ = null;
    /** アイテムリスト一覧(中) */
    private String[] ptl040PortalItemCenter__ = null;
    /** アイテムリスト一覧(右) */
    private String[] ptl040PortalItemRight__ = null;

    /** 選択されたアイテムID */
    private String ptl040PortalItemId__ = null;
    /** 選択された表示区分 */
    private int ptl040view__ = GSConstPortal.LAYOUT_VIEW_ON;

    /**
     * <p>ptl040portalName を取得します。
     * @return ptl040portalName
     */
    public String getPtl040portalName() {
        return ptl040portalName__;
    }
    /**
     * <p>ptl040portalName をセットします。
     * @param ptl040portalName ptl040portalName
     */
    public void setPtl040portalName(String ptl040portalName) {
        ptl040portalName__ = ptl040portalName;
    }
    /**
     * <p>ptl040topList を取得します。
     * @return ptl040topList
     */
    public List<Ptl040Model> getPtl040topList() {
        return ptl040topList__;
    }
    /**
     * <p>ptl040topList をセットします。
     * @param ptl040topList ptl040topList
     */
    public void setPtl040topList(List<Ptl040Model> ptl040topList) {
        ptl040topList__ = ptl040topList;
    }
    /**
     * <p>ptl040bottomList を取得します。
     * @return ptl040bottomList
     */
    public List<Ptl040Model> getPtl040bottomList() {
        return ptl040bottomList__;
    }
    /**
     * <p>ptl040bottomList をセットします。
     * @param ptl040bottomList ptl040bottomList
     */
    public void setPtl040bottomList(List<Ptl040Model> ptl040bottomList) {
        ptl040bottomList__ = ptl040bottomList;
    }
    /**
     * <p>ptl040leftList を取得します。
     * @return ptl040leftList
     */
    public List<Ptl040Model> getPtl040leftList() {
        return ptl040leftList__;
    }
    /**
     * <p>ptl040leftList をセットします。
     * @param ptl040leftList ptl040leftList
     */
    public void setPtl040leftList(List<Ptl040Model> ptl040leftList) {
        ptl040leftList__ = ptl040leftList;
    }
    /**
     * <p>ptl040centerList を取得します。
     * @return ptl040centerList
     */
    public List<Ptl040Model> getPtl040centerList() {
        return ptl040centerList__;
    }
    /**
     * <p>ptl040centerList をセットします。
     * @param ptl040centerList ptl040centerList
     */
    public void setPtl040centerList(List<Ptl040Model> ptl040centerList) {
        ptl040centerList__ = ptl040centerList;
    }
    /**
     * <p>ptl040rightList を取得します。
     * @return ptl040rightList
     */
    public List<Ptl040Model> getPtl040rightList() {
        return ptl040rightList__;
    }
    /**
     * <p>ptl040rightList をセットします。
     * @param ptl040rightList ptl040rightList
     */
    public void setPtl040rightList(List<Ptl040Model> ptl040rightList) {
        ptl040rightList__ = ptl040rightList;
    }
    /**
     * <p>ptl040areaTop を取得します。
     * @return ptl040areaTop
     */
    public int getPtl040areaTop() {
        return ptl040areaTop__;
    }
    /**
     * <p>ptl040areaTop をセットします。
     * @param ptl040areaTop ptl040areaTop
     */
    public void setPtl040areaTop(int ptl040areaTop) {
        ptl040areaTop__ = ptl040areaTop;
    }
    /**
     * <p>ptl040areaBottom を取得します。
     * @return ptl040areaBottom
     */
    public int getPtl040areaBottom() {
        return ptl040areaBottom__;
    }
    /**
     * <p>ptl040areaBottom をセットします。
     * @param ptl040areaBottom ptl040areaBottom
     */
    public void setPtl040areaBottom(int ptl040areaBottom) {
        ptl040areaBottom__ = ptl040areaBottom;
    }
    /**
     * <p>ptl040areaLeft を取得します。
     * @return ptl040areaLeft
     */
    public int getPtl040areaLeft() {
        return ptl040areaLeft__;
    }
    /**
     * <p>ptl040areaLeft をセットします。
     * @param ptl040areaLeft ptl040areaLeft
     */
    public void setPtl040areaLeft(int ptl040areaLeft) {
        ptl040areaLeft__ = ptl040areaLeft;
    }
    /**
     * <p>ptl040areaCenter を取得します。
     * @return ptl040areaCenter
     */
    public int getPtl040areaCenter() {
        return ptl040areaCenter__;
    }
    /**
     * <p>ptl040areaCenter をセットします。
     * @param ptl040areaCenter ptl040areaCenter
     */
    public void setPtl040areaCenter(int ptl040areaCenter) {
        ptl040areaCenter__ = ptl040areaCenter;
    }
    /**
     * <p>ptl040areaRight を取得します。
     * @return ptl040areaRight
     */
    public int getPtl040areaRight() {
        return ptl040areaRight__;
    }
    /**
     * <p>ptl040areaRight をセットします。
     * @param ptl040areaRight ptl040areaRight
     */
    public void setPtl040areaRight(int ptl040areaRight) {
        ptl040areaRight__ = ptl040areaRight;
    }
    /**
     * <p>ptl040PortalItemHead を取得します。
     * @return ptl040PortalItemHead
     */
    public String[] getPtl040PortalItemHead() {
        return ptl040PortalItemHead__;
    }
    /**
     * <p>ptl040PortalItemHead をセットします。
     * @param ptl040PortalItemHead ptl040PortalItemHead
     */
    public void setPtl040PortalItemHead(String[] ptl040PortalItemHead) {
        ptl040PortalItemHead__ = ptl040PortalItemHead;
    }
    /**
     * <p>ptl040PortalItemLeft を取得します。
     * @return ptl040PortalItemLeft
     */
    public String[] getPtl040PortalItemLeft() {
        return ptl040PortalItemLeft__;
    }
    /**
     * <p>ptl040PortalItemLeft をセットします。
     * @param ptl040PortalItemLeft ptl040PortalItemLeft
     */
    public void setPtl040PortalItemLeft(String[] ptl040PortalItemLeft) {
        ptl040PortalItemLeft__ = ptl040PortalItemLeft;
    }
    /**
     * <p>ptl040PortalItemCenter を取得します。
     * @return ptl040PortalItemCenter
     */
    public String[] getPtl040PortalItemCenter() {
        return ptl040PortalItemCenter__;
    }
    /**
     * <p>ptl040PortalItemCenter をセットします。
     * @param ptl040PortalItemCenter ptl040PortalItemCenter
     */
    public void setPtl040PortalItemCenter(String[] ptl040PortalItemCenter) {
        ptl040PortalItemCenter__ = ptl040PortalItemCenter;
    }
    /**
     * <p>ptl040PortalItemRight を取得します。
     * @return ptl040PortalItemRight
     */
    public String[] getPtl040PortalItemRight() {
        return ptl040PortalItemRight__;
    }
    /**
     * <p>ptl040PortalItemRight をセットします。
     * @param ptl040PortalItemRight ptl040PortalItemRight
     */
    public void setPtl040PortalItemRight(String[] ptl040PortalItemRight) {
        ptl040PortalItemRight__ = ptl040PortalItemRight;
    }
    /**
     * <p>ptl040PortalItemBottom を取得します。
     * @return ptl040PortalItemBottom
     */
    public String[] getPtl040PortalItemBottom() {
        return ptl040PortalItemBottom__;
    }
    /**
     * <p>ptl040PortalItemBottom をセットします。
     * @param ptl040PortalItemBottom ptl040PortalItemBottom
     */
    public void setPtl040PortalItemBottom(String[] ptl040PortalItemBottom) {
        ptl040PortalItemBottom__ = ptl040PortalItemBottom;
    }
    /**
     * <p>ptl040PortalItemId を取得します。
     * @return ptl040PortalItemId
     */
    public String getPtl040PortalItemId() {
        return ptl040PortalItemId__;
    }
    /**
     * <p>ptl040PortalItemId をセットします。
     * @param ptl040PortalItemId ptl040PortalItemId
     */
    public void setPtl040PortalItemId(String ptl040PortalItemId) {
        ptl040PortalItemId__ = ptl040PortalItemId;
    }
    /**
     * <p>ptl040view を取得します。
     * @return ptl040view
     */
    public int getPtl040view() {
        return ptl040view__;
    }
    /**
     * <p>ptl040view をセットします。
     * @param ptl040view ptl040view
     */
    public void setPtl040view(int ptl040view) {
        ptl040view__ = ptl040view;
    }
}