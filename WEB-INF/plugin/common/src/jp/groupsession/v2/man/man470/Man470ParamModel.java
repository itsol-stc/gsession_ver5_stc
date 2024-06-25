package jp.groupsession.v2.man.man470;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.man440.Man440ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] サイボウズLiveデータ移行 掲示板インポート画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man470ParamModel extends Man440ParamModel {
    //非表示項目
    /** プラグインID */
    private String man470pluginId__ = GSConst.PLUGINID_MAIN;

    /** 添付ファイル(コンボで選択中) */
    private String[] man470selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man470FileLabelList__ = null;

    /** 新規フォーラム作成 フォーラム名 */
    private int man470mode__ = GSConst.CMDMODE_ADD;
    /** 新規フォーラム作成 フォーラム名 */
    private String man470forumName__ = null;
    /** グループ名 */
    private String man470grpName__ = null;
    /** 登録済みフォーラム フォーラム(コンボで選択中) */
    private String[] man470selectForum__ = null;
    /** 登録済みフォーラム フォーラムコンボ */
    private ArrayList<LabelValueBean> man470forumLabelList__ = null;

    /**
     * <p>man470FileLabelList を取得します。
     * @return man470FileLabelList
     */
    public ArrayList<LabelValueBean> getMan470FileLabelList() {
        return man470FileLabelList__;
    }

    /**
     * <p>man470FileLabelList をセットします。
     * @param man470FileLabelList man470FileLabelList
     */
    public void setMan470FileLabelList(ArrayList<LabelValueBean> man470FileLabelList) {
        man470FileLabelList__ = man470FileLabelList;
    }

    /**
     * <p>man470pluginId を取得します。
     * @return man470pluginId
     */
    public String getMan470pluginId() {
        return man470pluginId__;
    }

    /**
     * <p>man470pluginId をセットします。
     * @param man470pluginId man470pluginId
     */
    public void setMan470pluginId(String man470pluginId) {
        man470pluginId__ = man470pluginId;
    }

    /**
     * <p>man470selectFiles を取得します。
     * @return man470selectFiles
     */
    public String[] getMan470selectFiles() {
        return man470selectFiles__;
    }

    /**
     * <p>man470selectFiles をセットします。
     * @param man470selectFiles man470selectFiles
     */
    public void setMan470selectFiles(String[] man470selectFiles) {
        man470selectFiles__ = man470selectFiles;
    }

    /**
     * <p>man470mode を取得します。
     * @return man470mode
     */
    public int getMan470mode() {
        return man470mode__;
    }

    /**
     * <p>man470mode をセットします。
     * @param man470mode man470mode
     */
    public void setMan470mode(int man470mode) {
        man470mode__ = man470mode;
    }

    /**
     * <p>man470forumName を取得します。
     * @return man470forumName
     */
    public String getMan470forumName() {
        return man470forumName__;
    }

    /**
     * <p>man470forumName をセットします。
     * @param man470forumName man470forumName
     */
    public void setMan470forumName(String man470forumName) {
        man470forumName__ = man470forumName;
    }

    /**
     * <p>man470selectForum を取得します。
     * @return man470selectForum
     */
    public String[] getMan470selectForum() {
        return man470selectForum__;
    }

    /**
     * <p>man470selectForum をセットします。
     * @param man470selectForum man470selectForum
     */
    public void setMan470selectForum(String[] man470selectForum) {
        man470selectForum__ = man470selectForum;
    }

    /**
     * <p>man470forumLabelList を取得します。
     * @return man470forumLabelList
     */
    public ArrayList<LabelValueBean> getMan470forumLabelList() {
        return man470forumLabelList__;
    }

    /**
     * <p>man470forumLabelList をセットします。
     * @param man470forumLabelList man470forumLabelList
     */
    public void setMan470forumLabelList(ArrayList<LabelValueBean> man470forumLabelList) {
        man470forumLabelList__ = man470forumLabelList;
    }

    /**
     * <p>man470grpName を取得します。
     * @return man470grpName
     */
    public String getMan470grpName() {
        return man470grpName__;
    }

    /**
     * <p>man470grpName をセットします。
     * @param man470grpName man470grpName
     */
    public void setMan470grpName(String man470grpName) {
        man470grpName__ = man470grpName;
    }
}
