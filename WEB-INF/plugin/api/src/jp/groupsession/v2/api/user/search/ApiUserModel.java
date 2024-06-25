package jp.groupsession.v2.api.user.search;

import java.util.List;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
/**
 *
 * <br>[機  能] API ユーザ情報モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiUserModel extends CmnUsrmInfModel {
    /** ラベル一覧*/
    private List<CmnLabelUsrModel> labels__;
    /** グループ一覧*/
    private List<GroupModel> groups__;
    /**
     * <p>labels を取得します。
     * @return labels
     */
    public List<CmnLabelUsrModel> getLabels() {
        return labels__;
    }
    /**
     * <p>labels をセットします。
     * @param labels labels
     */
    public void setLabels(List<CmnLabelUsrModel> labels) {
        labels__ = labels;
    }
    /**
     * <p>groups を取得します。
     * @return groups
     */
    public List<GroupModel> getGroups() {
        return groups__;
    }
    /**
     * <p>groups をセットします。
     * @param groups groups
     */
    public void setGroups(List<GroupModel> groups) {
        this.groups__ = groups;
    }

}
