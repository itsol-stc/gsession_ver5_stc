package jp.groupsession.v2.man.man480;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.man440.Man440ParamModel;
/**
 *
 * <br>[機  能] CybozuLive Todoリストインポート画面 param
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man480ParamModel extends Man440ParamModel {
    /** テンポラリファイル選択*/
    private String[] man480selectFiles__;

    /** 表示用ファイル名リスト*/
    private List<LabelValueBean> man480FileLabelList__;

    /** 対象プロジェクトモード  */
    private int man480mode__ = GSConst.CMDMODE_ADD;
    /** 新規プロジェクト作成 プロジェクトID */
    private String man480projectID__ = null;
    /** 新規プロジェクト作成 プロジェクト名 */
    private String man480projectName__ = null;
    /** 新規プロジェクト作成 プロジェクト略称 */
    private String man480projectShortName__ = null;
    /** グループ名 */
    private String man480grpName__ = null;
    /** 登録済みプロジェクト選択 */
    private int man480selectProject__ = -1;
    /** 登録済みプロジェクト コンボ */
    private ArrayList<LabelValueBean> man480projectLabelList__ = null;
    /** 登録済みプロジェクト 進捗リスト */
    private ArrayList<LabelValueBean> man480svStatusLabelList__ = null;

    /** ファイル読み込み済み */
    private boolean man480fileOk__ = false;
    /** 追加進捗名 リスト */
    private String[] man480statusNames__ = null;
    /** 追加進捗状態 Map */
    private Map<String, String> man480statusValueMap__ = new HashMap<>();

    /** インポート行数*/
    private int man480impCount__;

    /**
     * <p>man480selectFiles を取得します。
     * @return man480selectFiles
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480selectFiles__
     */
    public String[] getMan480selectFiles() {
        return man480selectFiles__;
    }
    /**
     * <p>man480selectFiles をセットします。
     * @param man480selectFiles man480selectFiles
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480selectFiles__
     */
    public void setMan480selectFiles(String[] man480selectFiles) {
        man480selectFiles__ = man480selectFiles;
    }
    /**
     * <p>man480FileLabelList を取得します。
     * @return man480FileLabelList
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480FileLabelList__
     */
    public List<LabelValueBean> getMan480FileLabelList() {
        return man480FileLabelList__;
    }
    /**
     * <p>man480FileLabelList をセットします。
     * @param man480FileLabelList man480FileLabelList
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480FileLabelList__
     */
    public void setMan480FileLabelList(List<LabelValueBean> man480FileLabelList) {
        man480FileLabelList__ = man480FileLabelList;
    }
    /**
     * <p>man480mode を取得します。
     * @return man480mode
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480mode__
     */
    public int getMan480mode() {
        return man480mode__;
    }
    /**
     * <p>man480mode をセットします。
     * @param man480mode man480mode
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480mode__
     */
    public void setMan480mode(int man480mode) {
        man480mode__ = man480mode;
    }
    /**
     * <p>man480projectID を取得します。
     * @return man480projectID
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480projectID__
     */
    public String getMan480projectID() {
        return man480projectID__;
    }
    /**
     * <p>man480projectID をセットします。
     * @param man480projectID man480projectID
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480projectID__
     */
    public void setMan480projectID(String man480projectID) {
        man480projectID__ = man480projectID;
    }
    /**
     * <p>man480projectName を取得します。
     * @return man480projectName
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480projectName__
     */
    public String getMan480projectName() {
        return man480projectName__;
    }
    /**
     * <p>man480projectName をセットします。
     * @param man480projectName man480projectName
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480projectName__
     */
    public void setMan480projectName(String man480projectName) {
        man480projectName__ = man480projectName;
    }
    /**
     * <p>man480projectShortName を取得します。
     * @return man480projectShortName
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480projectShortName__
     */
    public String getMan480projectShortName() {
        return man480projectShortName__;
    }
    /**
     * <p>man480projectShortName をセットします。
     * @param man480projectShortName man480projectShortName
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480projectShortName__
     */
    public void setMan480projectShortName(String man480projectShortName) {
        man480projectShortName__ = man480projectShortName;
    }
    /**
     * <p>man480grpName を取得します。
     * @return man480grpName
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480grpName__
     */
    public String getMan480grpName() {
        return man480grpName__;
    }
    /**
     * <p>man480grpName をセットします。
     * @param man480grpName man480grpName
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480grpName__
     */
    public void setMan480grpName(String man480grpName) {
        man480grpName__ = man480grpName;
    }
    /**
     * <p>man480selectProject を取得します。
     * @return man480selectProject
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480selectProject__
     */
    public int getMan480selectProject() {
        return man480selectProject__;
    }
    /**
     * <p>man480selectProject をセットします。
     * @param man480selectProject man480selectProject
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480selectProject__
     */
    public void setMan480selectProject(int man480selectProject) {
        man480selectProject__ = man480selectProject;
    }
    /**
     * <p>man480projectLabelList を取得します。
     * @return man480projectLabelList
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480projectLabelList__
     */
    public ArrayList<LabelValueBean> getMan480projectLabelList() {
        return man480projectLabelList__;
    }
    /**
     * <p>man480projectLabelList をセットします。
     * @param man480projectLabelList man480projectLabelList
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480projectLabelList__
     */
    public void setMan480projectLabelList(
            ArrayList<LabelValueBean> man480projectLabelList) {
        man480projectLabelList__ = man480projectLabelList;
    }
    /**
     * <p>man480svStatusLabelList を取得します。
     * @return man480svStatusLabelList
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480svStatusLabelList__
     */
    public ArrayList<LabelValueBean> getMan480svStatusLabelList() {
        return man480svStatusLabelList__;
    }
    /**
     * <p>man480svStatusLabelList をセットします。
     * @param man480svStatusLabelList man480svStatusLabelList
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480svStatusLabelList__
     */
    public void setMan480svStatusLabelList(ArrayList<LabelValueBean> man480svStatusLabelList) {
        man480svStatusLabelList__ = man480svStatusLabelList;
    }
    /**
     * <p>man480fileOk を取得します。
     * @return man480fileOk
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480fileOk__
     */
    public boolean isMan480fileOk() {
        return man480fileOk__;
    }
    /**
     * <p>man480fileOk をセットします。
     * @param man480fileOk man480fileOk
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480fileOk__
     */
    public void setMan480fileOk(boolean man480fileOk) {
        man480fileOk__ = man480fileOk;
    }
    /**
     * <p>man480statusNames を取得します。
     * @return man480statusNames
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480statusNames__
     */
    public String[] getMan480statusNames() {
        return man480statusNames__;
    }
    /**
     * <p>man480statusNames をセットします。
     * @param man480statusNames man480statusNames
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480statusNames__
     */
    public void setMan480statusNames(String[] man480statusNames) {
        man480statusNames__ = man480statusNames;
    }
    /**
     * <p>man480statusValueMap を取得します。
     * @return man480statusValueMap
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480statusValueMap__
     */
    public Map<String, String> getMan480statusValueMap() {
        return man480statusValueMap__;
    }
    /**
     * <p>man480statusValueMap をセットします。
     * @param man480statusValueMap man480statusValueMap
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480statusValueMap__
     */
    public void setMan480statusValueMap(Map<String, String> man480statusValueMap) {
        man480statusValueMap__ = man480statusValueMap;
    }
    /**
     * <p>man480impCount を取得します。
     * @return man480impCount
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480impCount__
     */
    public int getMan480impCount() {
        return man480impCount__;
    }
    /**
     * <p>man480impCount をセットします。
     * @param man480impCount man480impCount
     * @see jp.groupsession.v2.man.man480.Man480ParamModel#man480impCount__
     */
    public void setMan480impCount(int man480impCount) {
        man480impCount__ = man480impCount;
    }


}
