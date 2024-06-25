package jp.groupsession.v2.fil.ptl060;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.fil.fil040.FileDirectoryDspModel;
import jp.groupsession.v2.fil.model.FileDirectoryModel;
import jp.groupsession.v2.fil.ptl050.FilPtl050ParamModel;

/**
 * <p>ポータル ファイル一覧管理 フォルダ情報画面Model
 *
 * @author JTS
 */
public class FilPtl060ParamModel extends FilPtl050ParamModel {

    /** ディレクトリ選択済フラグ */
    private boolean filptl060selectFlg__ = false;

    //表示用
    /** キャビネット名称 */
    private String filptl060CabinetName__ = null;
    /** ディレクトリパス情報(タイトル表示用) */
    private ArrayList<FileDirectoryModel> filptl060DirectoryPathList__ = null;

    /** カレントディレクトリ情報一覧 */
    private List<FileDirectoryDspModel> filptl060DirectoryList__ = null;

    /** プラグインポートレットリスト */
    private List<LabelValueBean> filptl060PluginPortletList__ = null;
    /** 選択プラグインポートレット */
    private String ptl080PluginPortlet__ = "-1";

    /**
     * <p>filptl060selectFlg を取得します。
     * @return filptl060selectFlg
     */
    public boolean isFilptl060selectFlg() {
        return filptl060selectFlg__;
    }
    /**
     * <p>filptl060selectFlg をセットします。
     * @param filptl060selectFlg filptl060selectFlg
     */
    public void setFilptl060selectFlg(boolean filptl060selectFlg) {
        filptl060selectFlg__ = filptl060selectFlg;
    }

    /**
     * <p>filptl060CabinetName を取得します。
     * @return filptl060CabinetName
     */
    public String getFilptl060CabinetName() {
        return filptl060CabinetName__;
    }
    /**
     * <p>filptl060CabinetName をセットします。
     * @param filptl060CabinetName filptl060CabinetName
     */
    public void setFilptl060CabinetName(String filptl060CabinetName) {
        filptl060CabinetName__ = filptl060CabinetName;
    }
    /**
     * <p>filptl060DirectoryPathList を取得します。
     * @return filptl060DirectoryPathList
     */
    public ArrayList<FileDirectoryModel> getFilptl060DirectoryPathList() {
        return filptl060DirectoryPathList__;
    }
    /**
     * <p>filptl060DirectoryPathList をセットします。
     * @param filptl060DirectoryPathList filptl060DirectoryPathList
     */
    public void setFilptl060DirectoryPathList(
            ArrayList<FileDirectoryModel> filptl060DirectoryPathList) {
        filptl060DirectoryPathList__ = filptl060DirectoryPathList;
    }

    /**
     * <p>filptl060DirectoryList を取得します。
     * @return filptl060DirectoryList
     */
    public List<FileDirectoryDspModel> getFilptl060DirectoryList() {
        return filptl060DirectoryList__;
    }
    /**
     * <p>filptl060DirectoryList をセットします。
     * @param filptl060DirectoryList filptl060DirectoryList
     */
    public void setFilptl060DirectoryList(
            List<FileDirectoryDspModel> filptl060DirectoryList) {
        filptl060DirectoryList__ = filptl060DirectoryList;
    }
    /**
     * <p>filptl060PluginPortletList を取得します。
     * @return filptl060PluginPortletList
     */
    public List<LabelValueBean> getFilptl060PluginPortletList() {
        return filptl060PluginPortletList__;
    }
    /**
     * <p>filptl060PluginPortletList をセットします。
     * @param filptl060PluginPortletList filptl060PluginPortletList
     */
    public void setFilptl060PluginPortletList(
            List<LabelValueBean> filptl060PluginPortletList) {
        filptl060PluginPortletList__ = filptl060PluginPortletList;
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