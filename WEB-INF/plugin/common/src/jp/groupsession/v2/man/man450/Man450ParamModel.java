package jp.groupsession.v2.man.man450;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.man.man440.Man440ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 役職インポート画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man450ParamModel extends Man440ParamModel {
    //非表示項目
    /** プラグインID */
    private String man450pluginId__ = GSConst.PLUGINID_MAIN;

    /** 添付ファイル(コンボで選択中) */
    private String[] man450selectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man450FileLabelList__ = null;
    /** グループ名 */
    private String man450grpName__ = null;
    /** 仮パスワード */
    private String man450password__ = "";
    /** パスワード次回更新フラグ */
    private int man450passUpdateFlg__ = 0;

    /**
     * <p>man450FileLabelList を取得します。
     * @return man450FileLabelList
     */
    public ArrayList<LabelValueBean> getMan450FileLabelList() {
        return man450FileLabelList__;
    }

    /**
     * <p>man450FileLabelList をセットします。
     * @param man450FileLabelList man450FileLabelList
     */
    public void setMan450FileLabelList(ArrayList<LabelValueBean> man450FileLabelList) {
        man450FileLabelList__ = man450FileLabelList;
    }

    /**
     * <p>man450pluginId を取得します。
     * @return man450pluginId
     */
    public String getMan450pluginId() {
        return man450pluginId__;
    }

    /**
     * <p>man450pluginId をセットします。
     * @param man450pluginId man450pluginId
     */
    public void setMan450pluginId(String man450pluginId) {
        man450pluginId__ = man450pluginId;
    }

    /**
     * <p>man450selectFiles を取得します。
     * @return man450selectFiles
     */
    public String[] getMan450selectFiles() {
        return man450selectFiles__;
    }

    /**
     * <p>man450selectFiles をセットします。
     * @param man450selectFiles man450selectFiles
     */
    public void setMan450selectFiles(String[] man450selectFiles) {
        man450selectFiles__ = man450selectFiles;
    }

    /**
     * <p>man450grpName を取得します。
     * @return man450grpName
     */
    public String getMan450grpName() {
        return man450grpName__;
    }

    /**
     * <p>man450grpName をセットします。
     * @param man450grpName man450grpName
     */
    public void setMan450grpName(String man450grpName) {
        man450grpName__ = man450grpName;
    }

    /**
     * <p>man450password を取得します。
     * @return man450password
     */
    public String getMan450password() {
        return man450password__;
    }

    /**
     * <p>man450password をセットします。
     * @param man450password man450password
     */
    public void setMan450password(String man450password) {
        man450password__ = man450password;
    }

    /**
     * <p>man450passUpdateFlg を取得します。
     * @return man450passUpdateFlg
     */
    public int getMan450passUpdateFlg() {
        return man450passUpdateFlg__;
    }

    /**
     * <p>man450passUpdateFlg をセットします。
     * @param man450passUpdateFlg man450passUpdateFlg
     */
    public void setMan450passUpdateFlg(int man450passUpdateFlg) {
        man450passUpdateFlg__ = man450passUpdateFlg;
    }
}
