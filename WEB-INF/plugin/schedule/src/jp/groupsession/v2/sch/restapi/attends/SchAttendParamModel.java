package jp.groupsession.v2.sch.restapi.attends;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.restapi.entities.ITargetParamModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesQueryParamModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesValidateUtil;
@ParamModel
public class SchAttendParamModel implements ITargetParamModel {
    /** スケジュールSID                                        */
    @NotBlank
    private Integer scheduleSid__;
    /** 出席確認応答                                        */
    @NotBlank
    @Selectable({"0", "1", "2"})
    private Integer ansType__;
    /** 出席確認応答 コメント                                        */
    @MaxLength(50)
    @TextField
    private String commentText__;
    /**編集前データ*/
    private ScheduleSearchModel oldData__;

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
     * <p>attendAnsType を取得します。
     * @return attendAnsType
     * @see jp.groupsession.v2.sch.restapi.attends.SchAttendParamModel#ansType__
     */
    public Integer getAnsType() {
        return ansType__;
    }
    /**
     * <p>attendAnsType をセットします。
     * @param attendAnsType attendAnsType
     * @see jp.groupsession.v2.sch.restapi.attends.SchAttendParamModel#ansType__
     */
    public void setAnsType(Integer attendAnsType) {
        ansType__ = attendAnsType;
    }
    /**
     * <p>attendAnsCommentText を取得します。
     * @return attendAnsCommentText
     * @see jp.groupsession.v2.sch.restapi.attends.SchAttendParamModel#commentText__
     */
    public String getCommentText() {
        return commentText__;
    }
    /**
     * <p>attendAnsCommentText をセットします。
     * @param attendAnsCommentText attendAnsCommentText
     * @see jp.groupsession.v2.sch.restapi.attends.SchAttendParamModel#commentText__
     */
    public void setCommentText(String attendAnsCommentText) {
        commentText__ = attendAnsCommentText;
    }
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {
        List<RestApiValidateException> valErr = new ArrayList<>();
        try {
            ITargetParamModel.super.validate(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }
        //スケジュールの編集権限チェック
        valErr.addAll(SchEntitiesValidateUtil.validateSchEditPowerCheck(
                this,
                ctx));
        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }
    }
    @Override
    public int getMode() {
        return SchEntitiesValidateUtil.MODE_ANSER;
    }
    /**
    *
    * <br>[機  能] 編集前スケジュール情報を取得する
    * <br>[解  説] ２回目以降はキャッシュを返す
    * <br>[備  考]
    * @param ctx
    * @return defConf
    * @throws SQLException
    */
   @Override
   public ScheduleSearchModel getOldData(RestApiContext ctx) throws SQLException {
       if (oldData__ != null) {
           return oldData__;
       }
       if (getScheduleSid() == null) {
           return null;
       }
       ScheduleSearchDao searchDao = new ScheduleSearchDao(ctx.getCon());
       oldData__ = searchDao.getScheduleData(
               getScheduleSid(),
               0, ctx.getRequestUserSid());
       return oldData__;
   }



}
