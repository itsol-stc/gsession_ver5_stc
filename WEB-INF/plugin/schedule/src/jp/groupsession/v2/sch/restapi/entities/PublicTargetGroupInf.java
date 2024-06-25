package jp.groupsession.v2.sch.restapi.entities;

import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;

/** 個別公開対象 グループ                                                                    */
@ResponceModel(targetField = {
        "id",
        "name"
})
public class PublicTargetGroupInf {
    /** グループ情報*/
    private final CmnGroupmModel base__;
    public PublicTargetGroupInf(CmnGroupmModel cmnGroupmModel) {
        base__ = cmnGroupmModel;
    }
    /** @return ID                                       */
    public String getId() {
        return base__.getGrpId();
    }
    /** @return  名前                                       */
    public String getName() {
        return base__.getGrpName();
    }
}