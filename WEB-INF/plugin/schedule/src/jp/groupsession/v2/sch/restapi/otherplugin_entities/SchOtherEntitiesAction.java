package jp.groupsession.v2.sch.restapi.otherplugin_entities;

import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Parameter;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
/**
*
* <br>[機  能] スケジュール情報 API
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
@Plugin(GSConst.PLUGINID_SCH)
public class SchOtherEntitiesAction extends AbstractRestApiAction {
    @Post
    @Parameter("query")
    public void doQuery(
            HttpServletResponse res,
            SchOtherEntitiesQueryParamModel param,
            RestApiContext ctx) {



        SchOtherEntitiesQueryBiz biz = new SchOtherEntitiesQueryBiz(param, ctx);
        biz.execute();
        RestApiResponseWriter.builder(res, ctx)
            .setMax(biz.getMaxCount())
            .addResultList(biz.getResult())
            .build().execute();
    }
}
