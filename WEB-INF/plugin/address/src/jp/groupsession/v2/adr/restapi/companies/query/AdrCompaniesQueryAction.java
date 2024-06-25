package jp.groupsession.v2.adr.restapi.companies.query;

import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.adr.restapi.companies.AdrCompaniesAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;

/**
 * <br>[機  能] アドレス帳 会社情報一覧取得API アクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin(GSConst.PLUGINID_ADDRESS)
public class AdrCompaniesQueryAction extends AbstractRestApiAction {

    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(AdrCompaniesAction.class);

    /**
     *
     * <br>[機  能] GETメソッド
     * <br>[解  説]
     * <br>[備  考]
     * 
     * @param res レスポンス
     * @param param パラメータ
     * @param ctx コンテキスト
     * @throws SQLException
     */
    @Post
    public void doPost(
        HttpServletResponse res,
        AdrCompaniesQueryPostParamModel param,
        RestApiContext ctx) throws SQLException {

        log__.info("アドレス帳 会社情報一覧取得RESTAPIの開始");
                
        AdrCompaniesQueryPostBiz biz = new AdrCompaniesQueryPostBiz(ctx);
        biz.execute(param);
        log__.info("アドレス帳 会社情報一覧取得取得完了");

        RestApiResponseWriter.builder(res, ctx)
        .addResultList(biz.getResult())
        .setMax(biz.getMaxCount())
        .build().execute();
        log__.info("アドレス帳 会社情報一覧取得RESTAPIのレスポンス完了");
    }
}
