package jp.groupsession.v2.enq.enq920kn;

import java.util.ArrayList;

import jp.groupsession.v2.enq.enq920.Enq920ParamModel;
import jp.groupsession.v2.enq.model.EnqTypeListModel;

/**
 * <br>[機  能] 管理者設定 アンケート種類設定確認画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq920knParamModel extends Enq920ParamModel {

    /** アンケート種類リスト */
    private ArrayList<EnqTypeListModel> enq920knTypeList__ = null;

    /**
     * <p>enq920knTypeList を取得します。
     * @return enq920knTypeList
     */
    public ArrayList<EnqTypeListModel> getEnq920knTypeList() {
        return enq920knTypeList__;
    }

    /**
     * <p>enq920knTypeList をセットします。
     * @param enq920knTypeList enq920knTypeList
     */
    public void setEnq920knTypeList(ArrayList<EnqTypeListModel> enq920knTypeList) {
        enq920knTypeList__ = enq920knTypeList;
    }
}
