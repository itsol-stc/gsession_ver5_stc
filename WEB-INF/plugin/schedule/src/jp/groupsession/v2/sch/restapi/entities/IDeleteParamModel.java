package jp.groupsession.v2.sch.restapi.entities;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;

public interface IDeleteParamModel extends ITargetParamModel {
    /* (非 Javadoc)
     * @see jp.groupsession.v2.sch.restapi.entities.ITargetParamModel#getMode()
     */
    @Override
    default int getMode() {
        return SchEntitiesValidateUtil.MODE_DELETE;
    }

    /**
     * <p>sameSchededuledEditFlg をセットします。
     * @param sameSchededuledEditFlg sameSchededuledEditFlg
     * @see SchEntitiesPostParamModel#sameSchededuledEditFlg__
     */
    void setSameScheduledEditFlg(Integer sameSchededuledEditFlg);

    /**
     * <p>sameFacilityReserveEditFlg を取得します。
     * @return sameFacilityReserveEditFlg
     * @see SchEntitiesPostParamModel#sameFacilityReserveEditFlg__
     */
    Integer getSameFacilityReserveEditFlg();

    /**
     * <p>sameFacilityReserveEditFlg をセットします。
     * @param sameFacilityReserveEditFlg sameFacilityReserveEditFlg
     * @see SchEntitiesPostParamModel#sameFacilityReserveEditFlg__
     */
    void setSameFacilityReserveEditFlg(Integer sameFacilityReserveEditFlg);

    /**
     * <p>sameSchededuledEditFlg を取得します。
     * @return sameSchededuledEditFlg
     * @see SchEntitiesPostParamModel#sameSchededuledEditFlg__
     */
    Integer getSameScheduledEditFlg();

    /**
     * <p>rsvAdmin を取得します。
     * @param ctx
     * @return rsvAdmin
     * @see SchEntitiesPostParamModel#rsvAdmin__
     */
    boolean isRsvAdmin(RestApiContext ctx);

    SchPriConfModel getPconf(RestApiContext ctx) throws SQLException;

    default void defaultInit(RestApiContext ctx) throws SQLException {

        Connection con = ctx.getCon();
        RequestModel reqMdl = ctx.getRequestModel();

        SchCommonBiz biz = new SchCommonBiz(reqMdl);
        SchPriConfModel pconf = getPconf(ctx);
        //同時編集区分
        if (getSameScheduledEditFlg() == null) {
            setSameScheduledEditFlg(
                    biz.getInitSameAuth(con, pconf)
                    ); // pconf.getSccIniSame();
        }

    }

    @Override
    default void validate(RestApiContext ctx) throws SQLException {
        List<RestApiValidateException> valErr = new ArrayList<>();
        defaultInit(ctx);
        try {
            ITargetParamModel.super.validate(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }
        //スケジュールの編集権限チェック
        valErr.addAll(SchEntitiesValidateUtil.validateSchEditPowerCheck(
                this,
                ctx));

        //同時登録施設予約の編集権限チェック
        valErr.addAll(SchEntitiesValidateUtil.validateEditResPowerCheck(this, ctx));
        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }


    }

    SchAdmConfModel getAconf(RestApiContext ctx) throws SQLException;


}