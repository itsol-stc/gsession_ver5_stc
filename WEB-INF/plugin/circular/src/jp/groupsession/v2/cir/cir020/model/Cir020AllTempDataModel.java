package jp.groupsession.v2.cir.cir020.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 回覧板の添付ファイル一括ダウンロード用のデータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir020AllTempDataModel extends AbstractModel {

    /** トップディレクトリ名(タイトル) */
    private String topTitle__ = null;

    /** 全ファイルデータリスト */
    private ArrayList<Cir020UserTempDataModel> allUserFileList__ = null;

    /**
     * <p>topTitle を取得します。
     * @return topTitle
     */
    public String getTopTitle() {
        return topTitle__;
    }

    /**
     * <p>topTitle をセットします。
     * @param topTitle topTitle
     */
    public void setTopTitle(String topTitle) {
        topTitle__ = topTitle;
    }

    /**
     * <p>allUserFileList を取得します。
     * @return allUserFileList
     */
    public ArrayList<Cir020UserTempDataModel> getAllUserFileList() {
        return allUserFileList__;
    }

    /**
     * <p>allUserFileList をセットします。
     * @param allUserFileList allUserFileList
     */
    public void setAllUserFileList(
            ArrayList<Cir020UserTempDataModel> allUserFileList) {
        allUserFileList__ = allUserFileList;
    }

}
