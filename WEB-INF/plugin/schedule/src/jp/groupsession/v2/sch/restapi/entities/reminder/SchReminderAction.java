package jp.groupsession.v2.sch.restapi.entities.reminder;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Put;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

@Plugin(GSConst.PLUGINID_SCH)
public class SchReminderAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] 出席確認編集API
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     * @param res
     * @param param
     * @param ctx
     * @throws SQLException
     */
    @Put
    public void doPut(
            HttpServletRequest req,
            HttpServletResponse res,
            SchReminderParamModel param,
            RestApiContext ctx) throws SQLException {
        SchReminderBiz biz = new SchReminderBiz(param,
                ctx);
        biz.execute();
        RestApiResponseWriter.builder(res, ctx)
        .addResult(biz.getResult())
        .build().execute();

    }
}
