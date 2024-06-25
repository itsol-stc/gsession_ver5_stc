package jp.groupsession.v2.cht.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 回覧板の添付ファイル一括ダウンロード用のデータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChatAllTempDataModel extends AbstractModel {

    /** トップディレクトリ名(タイトル) */
    private String topTitle__ = null;

    /** 全ファイルデータリスト */
    private List<CmnBinfModel> allFileList__ = null;

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
     * <p>allFileList を取得します。
     * @return allFileList
     */
    public List<CmnBinfModel> getAllFileList() {
        return allFileList__;
    }

    /**
     * <p>allFileList をセットします。
     * @param allFileList allFileList
     */
    public void setAllFileList(
            List<CmnBinfModel> allFileList) {
        allFileList__ = allFileList;
    }

}
