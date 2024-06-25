package jp.groupsession.v2.fil.ptl050;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.fil.ptl010.FilPtl010ParamModel;


/**
 * <p>ポータル ファイル一覧管理 キャビネット選択画面Model
 * @author JTS
 */
public class FilPtl050ParamModel extends FilPtl010ParamModel {

    /** メイン(キャビネット)表示一覧 */
    private List<FileCabinetModel> filptl050dspList__ = null;

    /** ディレクトリ情報一覧（追加分） */
    private List<FileCabinetInfoModel> filptlCabinetInfoList__ = null;

    /** 選択キャビネットSID */
    private String filptl050selectFcbSid__;
    /** 選択ディレクトリSID */
    private String filptl050selectDirSid__;

    /** プラグインポートレットリスト */
    private List<LabelValueBean> filptl050PluginPortletList__ = null;
    /** 選択プラグインポートレット */
    private String ptl080PluginPortlet__ = "-1";

    /**
     * <p>filptl050dspList を取得します。
     * @return filptl050dspList
     */
    public List<FileCabinetModel> getFilptl050dspList() {
        return filptl050dspList__;
    }
    /**
     * <p>filptl050dspList をセットします。
     * @param filptl050dspList filptl050dspList
     */
    public void setFilptl050dspList(List<FileCabinetModel> filptl050dspList) {
        filptl050dspList__ = filptl050dspList;
    }
/**
     * <p>filptlCabinetInfoList を取得します。
     * @return filptlCabinetInfoList
     */
    public List<FileCabinetInfoModel> getFilptlCabinetInfoList() {
        return filptlCabinetInfoList__;
    }
    /**
     * <p>filptlCabinetInfoList をセットします。
     * @param filptlCabinetInfoList filptlCabinetInfoList
     */
    public void setFilptlCabinetInfoList(
            List<FileCabinetInfoModel> filptlCabinetInfoList) {
        filptlCabinetInfoList__ = filptlCabinetInfoList;
    }
    /**
     * <p>filptl050selectFcbSid を取得します。
     * @return filptl050selectFcbSid
     */
    public String getFilptl050selectFcbSid() {
        return filptl050selectFcbSid__;
    }
    /**
     * <p>filptl050selectFcbSid をセットします。
     * @param filptl050selectFcbSid filptl050selectFcbSid
     */
    public void setFilptl050selectFcbSid(String filptl050selectFcbSid) {
        filptl050selectFcbSid__ = filptl050selectFcbSid;
    }
    /**
     * <p>filptl050selectDirSid を取得します。
     * @return filptl050selectDirSid
     */
    public String getFilptl050selectDirSid() {
        return filptl050selectDirSid__;
    }
    /**
     * <p>filptl050selectDirSid をセットします。
     * @param filptl050selectDirSid filptl050selectDirSid
     */
    public void setFilptl050selectDirSid(String filptl050selectDirSid) {
        filptl050selectDirSid__ = filptl050selectDirSid;
    }
    /**
     * <p>filptl050PluginPortletList を取得します。
     * @return filptl050PluginPortletList
     */
    public List<LabelValueBean> getFilptl050PluginPortletList() {
        return filptl050PluginPortletList__;
    }
    /**
     * <p>filptl050PluginPortletList をセットします。
     * @param filptl050PluginPortletList filptl050PluginPortletList
     */
    public void setFilptl050PluginPortletList(
            List<LabelValueBean> filptl050PluginPortletList) {
        filptl050PluginPortletList__ = filptl050PluginPortletList;
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
