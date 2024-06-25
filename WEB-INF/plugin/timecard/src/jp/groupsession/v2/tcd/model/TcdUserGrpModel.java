package jp.groupsession.v2.tcd.model;

import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;


/**
 * <br>[機  能] グループ情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdUserGrpModel extends CmnGroupClassModel {

    /** GROUP SID */
    private int groupSid__ = -1;
    /** グループ名 */
    private String groupName__ = null;

    /**
     * <p>groupName を取得します。
     * @return groupName
     */
    public String getGroupName() {
        return groupName__;
    }
    /**
     * <p>groupName をセットします。
     * @param groupName groupName
     */
    public void setGroupName(String groupName) {
        groupName__ = groupName;
    }
    /**
     * <p>groupSid を取得します。
     * @return groupSid
     */
    public int getGroupSid() {
        return groupSid__;
    }
    /**
     * <p>groupSid をセットします。
     * @param groupSid groupSid
     */
    public void setGroupSid(int groupSid) {
        groupSid__ = groupSid;
    }
}
