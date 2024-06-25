package jp.groupsession.v2.man.man460;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.man440.Man440ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] サイボウズLiveデータ移行 イベントインポート画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man460ParamModel extends Man440ParamModel {
    //非表示項目
    /** プラグインID */
    private String man460pluginId__ = GSConst.PLUGINID_MAIN;

    /** 添付ファイル(コンボで選択中) */
    private String[] man460selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man460FileLabelList__ = null;
    /** グループ名 */
    private String man460grpName__ = null;

    /**
     * <p>man460FileLabelList を取得します。
     * @return man460FileLabelList
     */
    public ArrayList<LabelValueBean> getMan460FileLabelList() {
        return man460FileLabelList__;
    }

    /**
     * <p>man460FileLabelList をセットします。
     * @param man460FileLabelList man460FileLabelList
     */
    public void setMan460FileLabelList(ArrayList<LabelValueBean> man460FileLabelList) {
        man460FileLabelList__ = man460FileLabelList;
    }

    /**
     * <p>man460pluginId を取得します。
     * @return man460pluginId
     */
    public String getMan460pluginId() {
        return man460pluginId__;
    }

    /**
     * <p>man460pluginId をセットします。
     * @param man460pluginId man460pluginId
     */
    public void setMan460pluginId(String man460pluginId) {
        man460pluginId__ = man460pluginId;
    }

    /**
     * <p>man460selectFiles を取得します。
     * @return man460selectFiles
     */
    public String[] getMan460selectFiles() {
        return man460selectFiles__;
    }

    /**
     * <p>man460selectFiles をセットします。
     * @param man460selectFiles man460selectFiles
     */
    public void setMan460selectFiles(String[] man460selectFiles) {
        man460selectFiles__ = man460selectFiles;
    }
    /**
     * <p>man460grpName を取得します。
     * @return man460grpName
     */
    public String getMan460grpName() {
        return man460grpName__;
    }

    /**
     * <p>man460grpName をセットします。
     * @param man460grpName man460grpName
     */
    public void setMan460grpName(String man460grpName) {
        man460grpName__ = man460grpName;
    }
}
