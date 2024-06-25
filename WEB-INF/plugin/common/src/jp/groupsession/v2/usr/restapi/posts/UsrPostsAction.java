package jp.groupsession.v2.usr.restapi.posts;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.response.ResultElement;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
/**
 *
 * <br>[機  能] 役職情報API アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin("user")
public class UsrPostsAction extends AbstractRestApiAction {
    /**
    *
    * <br>[機  能] 役職情報API
    * <br>[解  説] GET
    * <br>[備  考] 役職情報を取得する
    * @param req サーブレットリクエスト
    * @param res サーブレットレスポンス
    * @param ctx RestApiコンテキスト
     * @throws SQLException
    */
   @Get
   public void doGet(HttpServletRequest req,
           HttpServletResponse res,
           RestApiContext ctx) throws SQLException {

       CmnPositionDao dao = new CmnPositionDao(ctx.getCon());
       List<CmnPositionModel> list = dao.getPosList(false);
       RestApiResponseWriter.Builder builder = RestApiResponseWriter.builder(res, ctx);
       for (CmnPositionModel posModel : list) {
           ResultElement result = new ResultElement("result");
           result.addContent(new ResultElement(
                   "sid").addContent(String.valueOf(posModel.getPosSid())));
           result.addContent(new ResultElement("name").addContent(posModel.getPosName()));
           result.addContent(new ResultElement("bikoText").addContent(posModel.getPosBiko()));
           result.addContent(new ResultElement("sortNum").addContent(
                   String.valueOf(posModel.getPosSort())));
           builder.addResult(result);
       }
       builder.build().execute();
   }
}