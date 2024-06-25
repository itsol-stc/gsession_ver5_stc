package jp.groupsession.v2.sch.restapi.entities.files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.RestApiAttachementResponseWriter;

@Plugin(GSConst.PLUGINID_SCH)
public class SchEntitiesFilesAction extends AbstractRestApiAction {
    @Get
    public void doGet(HttpServletRequest req,
            HttpServletResponse res,
            RestApiContext ctx,
            SchEntitiesFilesParamModel param) {

        RestApiAttachementResponseWriter.execute(res, req, ctx, param.getBinSid());

    }
}
