package jp.groupsession.v2.sch.restapi.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;

public interface ITargetParamModel {

    /**
     * <p>mode を取得します。
     * @return mode
     * @see SchEntitiesPostParamModel#cmn360mode__
     */
    int getMode();

    /**
     * <p>scheduleSid をセットします。
     * @param scheduleSid scheduleSid
     * @see SchEntitiesPostParamModel#scheduleSid__
     */
    void setScheduleSid(Integer scheduleSid);

    /**
     * <p>scheduleSid を取得します。
     * @return scheduleSid
     * @see SchEntitiesPostParamModel#scheduleSid__
     */
    Integer getScheduleSid();

    /**
     *
     * <br>[機  能] 編集前スケジュール情報を取得する
     * <br>[解  説] ２回目以降はキャッシュを返す
     * <br>[備  考]
     * @param ctx
     * @return defConf
     * @throws SQLException
     */
    ScheduleSearchModel getOldData(RestApiContext ctx) throws SQLException;

    default void validate(RestApiContext ctx) throws SQLException {
        List<RestApiValidateException> valErr = new ArrayList<>();

        valErr.addAll(SchEntitiesValidateUtil.validateExistData(this, ctx));

    }

}