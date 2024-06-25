package jp.groupsession.v2.sch.restapi.entities.files;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesQueryParamModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

@ParamModel
public class SchEntitiesFilesParamModel {
    /** スケジュールSID                                        */
    @NotBlank
    private Integer scheduleSid__;
    /** binSID                                        */
    @NotBlank
    private Long binSid__;

    /**
     * <p>scheduleSid を取得します。
     * @return scheduleSid
     * @see SchEntitiesQueryParamModel#scheduleSid__
     */
    public Integer getScheduleSid() {
        return scheduleSid__;
    }
    /**
     * <p>scheduleSid をセットします。
     * @param scheduleSid scheduleSid
     * @see SchEntitiesQueryParamModel#scheduleSid__
     */
    public void setScheduleSid(Integer scheduleSid) {
        scheduleSid__ = scheduleSid;
    }

    /**
     * <p>binSid を取得します。
     * @return binSid
     * @see jp.groupsession.v2.sch.restapi.entities.files.SchEntitiesFilesParamModel#binSid__
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     * @see jp.groupsession.v2.sch.restapi.entities.files.SchEntitiesFilesParamModel#binSid__
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        Connection con = ctx.getCon();
        RequestModel reqMdl = ctx.getRequestModel();

        Sch010Biz schBiz = new Sch010Biz(reqMdl);
        if (scheduleSid__ != null) {
            SchDataDao scdDao = new SchDataDao(con);
            SchDataModel scdMdl = scdDao.getEditCheckData(scheduleSid__);
            if (!schBiz.checkViewOk(scdMdl, reqMdl.getSmodel().getUsrsid(), con)) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "error.none.edit.data",
                        gsMsg.getMessage("schedule.108"),
                        gsMsg.getMessage("cmn.reading")
                        )
                        .setParamName(
                                "scheduleSid"
                                );

            }
            SchBinDao binDao = new SchBinDao(con);
            if (binDao.select(getScheduleSid(), getBinSid()) == null) {
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "error.none.edit.data",
                        gsMsg.getMessage("cmn.file"),
                        gsMsg.getMessage("cmn.download")
                        )
                        .setParamName(
                                "scheduleSid"
                                );
            }

        }

    }


}
