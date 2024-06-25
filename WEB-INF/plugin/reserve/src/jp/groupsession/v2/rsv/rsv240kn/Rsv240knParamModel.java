package jp.groupsession.v2.rsv.rsv240kn;

import java.util.List;

import jp.groupsession.v2.rsv.model.RsvSisGrpModel;
import jp.groupsession.v2.rsv.rsv240.Rsv240ParamModel;

/**
 * <br>[機  能] 施設予約 メイン表示設定確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv240knParamModel extends Rsv240ParamModel {

    /** グループ一覧（確認用） */
    private List<RsvSisGrpModel> rsv240knGrpList__ = null;

    /**
     * <p>rsv240knGrpList を取得します。
     * @return rsv240knGrpList
     */
    public List<RsvSisGrpModel> getRsv240knGrpList() {
        return rsv240knGrpList__;
    }

    /**
     * <p>rsv240knGrpList をセットします。
     * @param rsv240knGrpList rsv240knGrpList
     */
    public void setRsv240knGrpList(List<RsvSisGrpModel> rsv240knGrpList) {
        rsv240knGrpList__ = rsv240knGrpList;
    }

}
