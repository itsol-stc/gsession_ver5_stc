package jp.groupsession.v2.sch.restapi.groups.member;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

@Plugin(GSConst.PLUGINID_SCH)
public class SchGroupMemberAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] GETメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx コンテキスト
     * @param res レスポンス
     * @param param パラメータ
     * @throws SQLException
     */
    @Get
    public void doGet(RestApiContext ctx,
            HttpServletResponse res,
            SchGroupMemberGetParamModel param) throws SQLException {
        SchGroupMemberGetBiz biz = new SchGroupMemberGetBiz(param, ctx);
        biz.execute();
        RestApiResponseWriter.builder(res, ctx)
        .addResultList(biz.getResult())
        .setMax(biz.getSearchCnt())
        .build().execute();
    }
}
