package jp.groupsession.v2.sch.restapi.entities.reminder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.restapi.entities.ITargetParamModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesQueryParamModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesValidateUtil;
@ParamModel
public class SchReminderParamModel implements ITargetParamModel {
    /** スケジュールSID                                        */
    @NotBlank
    private Integer scheduleSid__;
    /**通知対象:グループ                                         */
    @Selectable({"0", "1"})
    private Integer remindGrpFlg__;
    /**リマインダー時間                                          */
    @Selectable({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13"})
    private Integer remindTimingType__;
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
     * <p>remindGrpFlg を取得します。
     * @return remindGrpFlg
     * @see jp.groupsession.v2.sch.restapi.entities.reminder.SchReminderParamModel#remindGrpFlg__
     */
    public Integer getRemindGrpFlg() {
        return remindGrpFlg__;
    }
    /**
     * <p>remindGrpFlg をセットします。
     * @param remindGrpFlg remindGrpFlg
     * @see jp.groupsession.v2.sch.restapi.entities.reminder.SchReminderParamModel#remindGrpFlg__
     */
    public void setRemindGrpFlg(Integer remindGrpFlg) {
        remindGrpFlg__ = remindGrpFlg;
    }
    /**
     * <p>remindTimingType を取得します。
     * @return remindTimingType
     * @see remindTimingType__
     */
    public Integer getRemindTimingType() {
        return remindTimingType__;
    }
    /**
     * <p>remindTimingType をセットします。
     * @param remindTimingType remindTimingType
     * @see remindTimingType__
     */
    public void setRemindTimingType(Integer remindTimingType) {
        remindTimingType__ = remindTimingType;
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
        return SchEntitiesValidateUtil.MODE_EDIT_REMIND;
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
