package jp.groupsession.v2.rsv.restapi.facility_groups.facility;

import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
/**
 *
 * <br>[機  能] 施設一覧情報API パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class RsvFacilityGroupsFacilitiesGetParamModel {
    /** グループSID */
    private String groupId__;

    /**
     * <p>groupId を取得します。
     * @return groupId
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.facility.
     *       RsvFacilityGroupsFacilitiesGetParamModel#groupId__
     */
    public String getGroupId() {
        return groupId__;
    }

    /**
     * <p>groupId をセットします。
     * @param groupId groupId
     * @see jp.groupsession.v2.rsv.restapi.facility_groups.facility.
     *       RsvFacilityGroupsFacilitiesGetParamModel#groupId__
     */
    public void setGroupId(String groupId) {
        groupId__ = groupId;
    }
}
