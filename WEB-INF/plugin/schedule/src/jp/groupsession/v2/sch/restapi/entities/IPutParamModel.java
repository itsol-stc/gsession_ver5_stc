package jp.groupsession.v2.sch.restapi.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiException;
import jp.groupsession.v2.restapi.exception.RestApiExceptionNest;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.sch.dao.SchBinDao;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 編集時 パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IPutParamModel extends IPostParamModel, IDeleteParamModel {
    @Override
    default void defaultInit(RestApiContext ctx) throws SQLException {
        IPostParamModel.super.defaultInit(ctx);
        IDeleteParamModel.super.defaultInit(ctx);


    }

    @Override
    default void validate(RestApiContext ctx) throws SQLException {
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());

        ScheduleSearchModel old = getOldData(ctx);
        if (old != null && old.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
            setTargetType(GSConstSchedule.USER_KBN_USER);
            setTargetId(
                getUserMap(ctx).values().stream()
                .filter(usr -> usr.getUsrSid() == old.getScdUsrSid())
                .findAny()
                .map(usr -> usr.getUsrLgid())
                .orElseGet(null)
                    );
        }
        if (old != null && old.getScdUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {
            setTargetType(GSConstSchedule.USER_KBN_GROUP);
            setTargetId(
                    getGroupMap(ctx).values().stream()
                    .filter(grp -> grp.getGrpSid() == old.getScdUsrSid())
                    .findAny()
                    .map(grp -> grp.getGrpId())
                    .orElseGet(null)
                        );

        }


        List<RestApiValidateException> valErr = new ArrayList<>();
        try {
            IDeleteParamModel.super.validate(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
            if (valErr.size() > 0) {
                throw new RestApiValidateExceptionNest(valErr);
            }
        }


        try {
            IPostParamModel.super.validate(ctx);
        } catch (RestApiValidateException e) {
            valErr.add(e);
        }
        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }
        //出欠確認スケジュールは単体編集不可
        if (getUseAttendFlg()
                != GSConstSchedule.ATTEND_KBN_NO
                && Optional.ofNullable(getSameScheduledEditFlg())
                .map(flg -> flg == GSConstSchedule.SAME_EDIT_OFF)
                .orElse(false)
                ) {
            valErr.add(new RestApiValidateException(
                    EnumError.PARAM_COLLABORATION,
                    "error.cant.edit.single.attend.schedule")
                    .setParamName(
                            "useAttendFlg",
                            "sameSchededuledEditFlg"
                            )
                    );

        }

        if (valErr.size() > 0) {
            throw new RestApiValidateExceptionNest(valErr);
        }

        List<RestApiException> dupErr = new ArrayList<>();
        dupErr.addAll(SchEntitiesValidateUtil.validateSchRepeatCheck(
                this,
                ctx,
                GSConstSchedule.SCH_REPEAT_KBN_NG));
        //同時登録その他施設予約エラーチェック
        dupErr.addAll(SchEntitiesValidateUtil.validateReserve(this, ctx));


        if (dupErr.size() > 0) {
            throw new RestApiExceptionNest(403, dupErr);
        }
        //binSID権限
        if (getBinSidArray() != null && getBinSidArray().length > 0) {
            List<RestApiException> binErr = new ArrayList<>();
            for (int i = 0; i < getBinSidArray().length; i++) {
                long binSid = getBinSidArray()[i];
                SchBinDao binDao = new SchBinDao(ctx.getCon());
                if (binDao.select(getScheduleSid(), binSid) == null) {
                    binErr.add(new RestApiValidateException(
                            ReasonCode.EnumError.IMPERMISSIBLE,
                            "error.none.edit.data",
                            gsMsg.getMessage("cmn.file"),
                            gsMsg.getMessage("cmn.edit")
                            )
                            .setParamName(
                                    String.format("binSidArray[%d]", i)
                                    )
                            );
                }

            }
            if (binErr.size() > 0) {
                throw new RestApiExceptionNest(403, binErr);
            }
        }

    }
    @Override
    default int getMode() {
        return SchEntitiesValidateUtil.MODE_EDIT;
    }

    /**
     * <p>binSidArray を取得します。
     * @return binSidArray
     * @see binSidArray__
     */
    long[] getBinSidArray();

    /**
     * <p>binSidArray をセットします。
     * @param binSidArray binSidArray
     * @see binSidArray__
     */
    void setBinSidArray(long[] binSidArray);
}

