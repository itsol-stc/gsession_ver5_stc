package jp.groupsession.v2.sch.restapi.entities;
/**
 *
 * <br>[機  能] パラメータモデル 削除処理
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */

import java.sql.SQLException;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
@ParamModel
public class SchEntitiesDeleteParamModel implements IDeleteParamModel {

    /** スケジュールSID */
    @NotBlank
    private Integer scheduleSid__;
    /**同時スケジュール編集                                        */
    @Selectable({"0", "1"})
    private Integer sameScheduledEditFlg__;
    /**同時施設予約編集                                          */
    @Selectable({"0", "1"})
    private Integer sameFacilityReserveEditFlg__ = GSConstSchedule.SAME_EDIT_ON;

    /** 施設管理者権限*/
    private Boolean rsvAdmin__;
    /** スケジュール個人設定*/
    private SchPriConfModel pconf__;
    /** スケジュール管理者設定*/
    private SchAdmConfModel aconf__;
    /**編集前 スケジュール情報*/
    private ScheduleSearchModel oldData__;

    /**
     * <p>scheduleSid を取得します。
     * @return scheduleSid
     * @see SchEntitiesPostParamModel#scheduleSid__
     */
    @Override
    public Integer getScheduleSid() {
        return scheduleSid__;
    }
    /**
     * <p>scheduleSid をセットします。
     * @param scheduleSid scheduleSid
     * @see SchEntitiesPostParamModel#scheduleSid__
     */
    @Override
    public void setScheduleSid(Integer scheduleSid) {
        scheduleSid__ = scheduleSid;
    }
    /**
     * <p>sameSchededuledEditFlg を取得します。
     * @return sameSchededuledEditFlg
     * @see SchEntitiesPostParamModel#sameScheduledEditFlg__
     */
    @Override
    public Integer getSameScheduledEditFlg() {
        return sameScheduledEditFlg__;
    }
    /**
     * <p>sameSchededuledEditFlg をセットします。
     * @param sameSchededuledEditFlg sameSchededuledEditFlg
     * @see SchEntitiesPostParamModel#sameScheduledEditFlg__
     */
    @Override
    public void setSameScheduledEditFlg(Integer sameSchededuledEditFlg) {
        sameScheduledEditFlg__ = sameSchededuledEditFlg;
    }
    /**
     * <p>sameFacilityReserveEditFlg を取得します。
     * @return sameFacilityReserveEditFlg
     * @see SchEntitiesPostParamModel#sameFacilityReserveEditFlg__
     */
    @Override
    public Integer getSameFacilityReserveEditFlg() {
        return sameFacilityReserveEditFlg__;
    }
    /**
     * <p>sameFacilityReserveEditFlg をセットします。
     * @param sameFacilityReserveEditFlg sameFacilityReserveEditFlg
     * @see SchEntitiesPostParamModel#sameFacilityReserveEditFlg__
     */
    @Override
    public void setSameFacilityReserveEditFlg(Integer sameFacilityReserveEditFlg) {
        sameFacilityReserveEditFlg__ = sameFacilityReserveEditFlg;
    }

    /* (非 Javadoc)
     * @see IParamModel#isRsvAdmin(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public boolean isRsvAdmin(RestApiContext ctx) {
        if (rsvAdmin__ != null) {
            return rsvAdmin__;
        }
        try {
            CommonBiz cmnBiz = new CommonBiz();

            rsvAdmin__ = cmnBiz.isPluginAdmin(ctx.getCon(),
                    ctx.getRequestUserSid(),
                    GSConstSchedule.PLUGIN_ID_RESERVE);
            return rsvAdmin__;
        } catch (SQLException e) {
            throw new RuntimeException("SQL実行時例外", e);
        }

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
   /* (非 Javadoc)
    * @see IParamModel#getAconf(jp.groupsession.v2.restapi.controller.RestApiContext)
    */
   @Override
   public SchAdmConfModel getAconf(RestApiContext ctx) throws SQLException {
       if (aconf__ != null) {
           return aconf__;
       }
       SchCommonBiz schBiz = new SchCommonBiz(ctx.getCon(), ctx.getRequestModel());
       aconf__ = schBiz.getAdmConfModel(ctx.getCon());
       return aconf__;
   }

    /* (非 Javadoc)
     * @see IDeleteParamModel#getPconf(jp.groupsession.v2.restapi.controller.RestApiContext)
     */
    @Override
    public SchPriConfModel getPconf(RestApiContext ctx) throws SQLException {
        if (pconf__ != null) {
            return pconf__;
        }
        SchCommonBiz schBiz = new SchCommonBiz(ctx.getCon(), ctx.getRequestModel());
        pconf__ = schBiz.getSchPriConfModel(ctx.getCon(), ctx.getRequestUserSid());
        return pconf__;
    }

    @Override
    @Validator
    public void validate(RestApiContext ctx) throws SQLException {
        defaultInit(ctx);

        IDeleteParamModel.super.validate(ctx);
    }


}
