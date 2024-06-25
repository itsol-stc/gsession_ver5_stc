package jp.groupsession.v2.cir.cir170;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir150.Cir150ParamModel;

/**
 * <br>[機  能] 回覧板 アカウントインポート画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir170ParamModel extends Cir150ParamModel {
    /** プラグインID */
    private String cir170pluginId__ = GSConstCircular.PLUGIN_ID_CIRCULAR;

    /** 初期表示フラグ*/
    private int cir170InitFlg__ = 0;

    /** 添付ファイル(コンボで選択中) */
    private String[] cir170selectFiles__ = null;
    /** ファイルコンボ */
    private  List<LabelValueBean> cir170FileLabelList__ = null;

    /**
     * <p>cir170FileLabelList を取得します。
     * @return cir170FileLabelList
     */
    public List<LabelValueBean> getCir170FileLabelList() {
        return cir170FileLabelList__;
    }
    /**
     * <p>cir170FileLabelList をセットします。
     * @param cir170FileLabelList cir170FileLabelList
     */
    public void setCir170FileLabelList(List<LabelValueBean> cir170FileLabelList) {
        cir170FileLabelList__ = cir170FileLabelList;
    }
    /**
     * <p>cir170pluginId を取得します。
     * @return cir170pluginId
     */
    public String getCir170pluginId() {
        return cir170pluginId__;
    }
    /**
     * <p>cir170pluginId をセットします。
     * @param cir170pluginId cir170pluginId
     */
    public void setCir170pluginId(String cir170pluginId) {
        cir170pluginId__ = cir170pluginId;
    }
    /**
     * <p>cir170selectFiles を取得します。
     * @return cir170selectFiles
     */
    public String[] getCir170selectFiles() {
        return cir170selectFiles__;
    }
    /**
     * <p>cir170selectFiles をセットします。
     * @param cir170selectFiles cir170selectFiles
     */
    public void setCir170selectFiles(String[] cir170selectFiles) {
        cir170selectFiles__ = cir170selectFiles;
    }
    /**
     * <p>cir170InitFlg を取得します。
     * @return cir170InitFlg
     * @see jp.groupsession.v2.cir.cir170.Cir170ParamModel#cir170InitFlg__
     */
    public int getCir170InitFlg() {
        return cir170InitFlg__;
    }
    /**
     * <p>cir170InitFlg をセットします。
     * @param cir170InitFlg cir170InitFlg
     * @see jp.groupsession.v2.cir.cir170.Cir170ParamModel#cir170InitFlg__
     */
    public void setCir170InitFlg(int cir170InitFlg) {
        cir170InitFlg__ = cir170InitFlg;
    }
}