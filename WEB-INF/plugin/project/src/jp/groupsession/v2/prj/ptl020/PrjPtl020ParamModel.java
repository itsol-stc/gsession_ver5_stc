package jp.groupsession.v2.prj.ptl020;

import java.util.List;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.prj.model.PrjPrjdataModel;

import org.apache.struts.util.LabelValueBean;


/**
 * <br>[機  能] プロジェクト管理 プラグインポートレット TODO状態内訳のォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjPtl020ParamModel extends AbstractParamModel {

    /** ポータルSID */
    private int ptlPortalSid__ = 0;
    /** 選択プラグインポートレット */
    private String ptl080PluginPortlet__ = "-1";
    /** プラグインポートレットコンボ */
    private List<LabelValueBean> portletTypeCombo__ = null;

    /** プロジェクト一覧 */
    private List<PrjPrjdataModel> prjptl020dspList__ = null;
    /** 選択プロジェクトSID */
    private int prjptl020selectPrjSid__ = 0;

    /** ページ(上段) */
    private int prjptl020pageTop__ = 0;
    /** ページ(下段) */
    private int prjptl020pageBottom__ = 0;
    /** ページコンボ */
    private List<LabelValueBean> pageCombo__ = null;

    /** TODO状態内訳選択フラグ */
    private boolean prjptl020selectFlg__ = false;

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
     * @return prjptl020selectFlg
     */
    public boolean isPrjptl020selectFlg() {
        return prjptl020selectFlg__;
    }

    /**
     * @param prjptl020selectFlg セットする prjptl020selectFlg
     */
    public void setPrjptl020selectFlg(boolean prjptl020selectFlg) {
        prjptl020selectFlg__ = prjptl020selectFlg;
    }

    /**
     * <p>prjptl020dspList を取得します。
     * @return prjptl020dspList
     */
    public List<PrjPrjdataModel> getPrjptl020dspList() {
        return prjptl020dspList__;
    }

    /**
     * <p>prjptl020dspList をセットします。
     * @param prjptl020dspList prjptl020dspList
     */
    public void setPrjptl020dspList(List<PrjPrjdataModel> prjptl020dspList) {
        prjptl020dspList__ = prjptl020dspList;
    }

    /**
     * <p>prjptl020selectPrjSid を取得します。
     * @return prjptl020selectPrjSid
     */
    public int getPrjptl020selectPrjSid() {
        return prjptl020selectPrjSid__;
    }

    /**
     * <p>prjptl020selectPrjSid をセットします。
     * @param prjptl020selectPrjSid prjptl020selectPrjSid
     */
    public void setPrjptl020selectPrjSid(int prjptl020selectPrjSid) {
        prjptl020selectPrjSid__ = prjptl020selectPrjSid;
    }
    /**
     * <p>pageCombo を取得します。
     * @return pageCombo
     */
    public List<LabelValueBean> getPageCombo() {
        return pageCombo__;
    }

    /**
     * <p>pageCombo をセットします。
     * @param pageCombo pageCombo
     */
    public void setPageCombo(List<LabelValueBean> pageCombo) {
        pageCombo__ = pageCombo;
    }

    /**
     * <p>prjptl020pageBottom を取得します。
     * @return prjptl020pageBottom
     */
    public int getPrjptl020pageBottom() {
        return prjptl020pageBottom__;
    }

    /**
     * <p>prjptl020pageBottom をセットします。
     * @param prjptl020pageBottom prjptl020pageBottom
     */
    public void setPrjptl020pageBottom(int prjptl020pageBottom) {
        prjptl020pageBottom__ = prjptl020pageBottom;
    }

    /**
     * <p>prjptl020pageTop を取得します。
     * @return prjptl020pageTop
     */
    public int getPrjptl020pageTop() {
        return prjptl020pageTop__;
    }

    /**
     * <p>prjptl020pageTop をセットします。
     * @param prjptl020pageTop prjptl020pageTop
     */
    public void setPrjptl020pageTop(int prjptl020pageTop) {
        prjptl020pageTop__ = prjptl020pageTop;
    }
}

