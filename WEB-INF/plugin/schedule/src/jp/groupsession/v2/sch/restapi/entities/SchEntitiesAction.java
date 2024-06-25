package jp.groupsession.v2.sch.restapi.entities;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.AbstractRestApiAction;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.controller.annotation.Delete;
import jp.groupsession.v2.restapi.controller.annotation.Get;
import jp.groupsession.v2.restapi.controller.annotation.Parameter;
import jp.groupsession.v2.restapi.controller.annotation.Plugin;
import jp.groupsession.v2.restapi.controller.annotation.Post;
import jp.groupsession.v2.restapi.controller.annotation.Put;
import jp.groupsession.v2.restapi.exception.RestApiException;
import jp.groupsession.v2.restapi.response.RestApiResponseWriter;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] スケジュール情報 API
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Plugin(GSConst.PLUGINID_SCH)
public class SchEntitiesAction extends AbstractRestApiAction {
    /**
     *
     * <br>[機  能] 検索処理
     * <br>[解  説]
     * <br>[備  考]
     * @param res
     * @param param
     * @param ctx
     */
    @Post
    @Parameter("query")
    public void doQuery(
            HttpServletResponse res,
            SchEntitiesQueryParamModel param,
            RestApiContext ctx) {
        SchEntitiesQueryBiz biz = new SchEntitiesQueryBiz(param, ctx);
        biz.execute();
        RestApiResponseWriter.builder(res, ctx)
            .setMax(biz.getMaxCount())
            .addResultList(biz.getResult())
            .build().execute();
    }
    /**
     *
     * <br>[機  能] 登録処理を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     * @param res
     * @param param
     * @param ctx
     * @param tempPath
     * @throws SQLException
     * @throws IOToolsException
     */
    @Post
    @Parameter("normal")
    public void doPost(
            HttpServletRequest req,
            HttpServletResponse res,
            SchEntitiesPostParamModel param,
            RestApiContext ctx,
            GSTemporaryPathModel tempPath) throws SQLException, IOToolsException {
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.createTempDir(tempPath);

        if (param.getWarnCommitFlg() != 1) {
            //スケジュール重複登録チェック
            List<RestApiException> warns =
                    new ArrayList<>(
                            SchEntitiesValidateUtil.validateSchRepeatCheck(
                                param,
                                ctx,
                                GSConstSchedule.SCH_REPEAT_KBN_WARNING)
                            );
            if (warns.size() > 0) {
                RestApiResponseWriter.WarnResponceBuilder builder =
                        RestApiResponseWriter.builderWarn(res)
                        .setRestApiContext(ctx);

                warns.stream()
                    .flatMap(warn -> warn.createElement(ctx).stream())
                    .forEach(elm -> builder.addResult(elm));

                builder.build().execute();
                return;

            }
        }

        SchEntitiesPostBiz biz = new SchEntitiesPostBiz(param,
                ctx,
                tempPath.getTempPath());
        biz.execute();
        //ログ出力処理
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        String entry = gsMsg.getMessage("cmn.entry");
        SchCommonBiz schBiz = new SchCommonBiz(ctx.getCon(), ctx.getRequestModel());
        String opCode = "";
        opCode = entry;
        String outOpLog = __getOpLog(ctx,
                biz.getResult(),
                -1,
                0);
        schBiz.outPutApiLog(
                ctx.getRequestModel(),
                ctx.getCon(),
                ctx.getRequestUserSid(),
                getClass().getName(),
                opCode, GSConstLog.LEVEL_TRACE,
                outOpLog);

        //実行後添付フォルダ破棄
        tempUtil.clearTempPath(tempPath);

        RestApiResponseWriter.builder(res, ctx)
            .addResult(biz.getResult())
            .build().execute();


    }
    /**
     *
     * <br>[機  能] 編集処理を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     * @param res
     * @param param
     * @param ctx
     * @param tempPath
     * @throws SQLException
     * @throws IOToolsException
     */
    @Put
    @Parameter("normal")
    public void doPut(
            HttpServletRequest req,
            HttpServletResponse res,
            SchEntitiesPutParamModel param,
            RestApiContext ctx,
            GSTemporaryPathModel tempPath) throws SQLException, IOToolsException {
        GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();
        tempUtil.createTempDir(tempPath);

        if (param.getWarnCommitFlg() != 1) {
            //スケジュール重複登録チェック
            List<RestApiException> warns =
                    new ArrayList<>(
                            SchEntitiesValidateUtil.validateSchRepeatCheck(
                                param,
                                ctx,
                                GSConstSchedule.SCH_REPEAT_KBN_WARNING)
                            );
            if (warns.size() > 0) {
                RestApiResponseWriter.WarnResponceBuilder builder =
                        RestApiResponseWriter.builderWarn(res)
                .setRestApiContext(ctx);

                warns.stream()
                    .flatMap(warn -> warn.createElement(ctx).stream())
                    .forEach(elm -> builder.addResult(elm));

                builder.build().execute();
                return;

            }
        }

        SchEntitiesPutBiz biz = new SchEntitiesPutBiz(param,
                ctx,
                tempPath.getTempPath());
        biz.execute();
        //ログ出力処理
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        String entry = gsMsg.getMessage("cmn.edit");
        SchCommonBiz schBiz = new SchCommonBiz(ctx.getCon(), ctx.getRequestModel());
        String opCode = "";
        opCode = entry;

        int mode = 0;
        if (param.getSameScheduledEditFlg() == GSConstSchedule.SAME_EDIT_ON) {
            mode = 1;
        }
        String outPreOpLog = __getOpLog(ctx,
                biz.getPrev(),
                mode,
                1);
        String outOpLog = __getOpLog(ctx,
                biz.getResult(),
                mode,
                2);
        schBiz.outPutApiLog(
                ctx.getRequestModel(),
                ctx.getCon(),
                ctx.getRequestUserSid(),
                getClass().getName(),
                opCode, GSConstLog.LEVEL_TRACE,
                outPreOpLog + outOpLog);


        //実行後添付フォルダ破棄
        tempUtil.clearTempPath(tempPath);

        RestApiResponseWriter.builder(res, ctx)
            .addResult(biz.getResult())
            .build().execute();


    }
    /**
     *
     * <br>[機  能] 削除処理を実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     * @param res
     * @param param
     * @param ctx
     * @throws SQLException
     */
    @Delete
    @Parameter("normal")
    public void doDelete(
            HttpServletRequest req,
            HttpServletResponse res,
            SchEntitiesDeleteParamModel param,
            RestApiContext ctx) throws SQLException {


        SchEntitiesDeleteBiz biz = new SchEntitiesDeleteBiz(param,
                ctx);
        biz.execute();
        //ログ出力処理
        GsMessage gsMsg = new GsMessage(ctx.getRequestModel());
        String entry = gsMsg.getMessage("cmn.delete");
        SchCommonBiz schBiz = new SchCommonBiz(ctx.getCon(), ctx.getRequestModel());
        String opCode = "";
        opCode = entry;

        int mode = -1;
        String outPreOpLog = __getOpLog(ctx,
                biz.getPrev(),
                mode,
                1);
        schBiz.outPutApiLog(
                ctx.getRequestModel(),
                ctx.getCon(),
                ctx.getRequestUserSid(),
                getClass().getName(),
                opCode, GSConstLog.LEVEL_TRACE,
                outPreOpLog);


        RestApiResponseWriter.builder(res, ctx)
            .addResult("OK")
            .build().execute();


    }
    /**
     *
     * <br>[機  能] 詳細取得
     * <br>[解  説]
     * <br>[備  考]
     * @param req
     * @param res
     * @param param
     * @param ctx
     */
    @Get
    @Parameter("normal")
    public void doGet(
            HttpServletRequest req,
            HttpServletResponse res,
            SchEntitiesGetParamModel param,
            RestApiContext ctx) {
        SchEntitiesQueryBiz biz = new SchEntitiesQueryBiz(param, ctx);
        biz.execute();
        RestApiResponseWriter.builder(res, ctx)
            .addResultList(biz.getResult())
            .build().execute();
    }

    /**
     * <br>[機  能]スケジュール登録・編集・削除時ログ出力内容を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param ctx RESTAPIコンテキスト
     * @param result 結果
     * @param editFlg 編集フラグ -1:編集以外 0:編集かつ同時修正しない 1:編集かつ同時修正する
     * @param beforeFlg 0:変更文字不要 1:変更前 2:変更後
     * @return オペレーションログ表示内容
     */
    private String __getOpLog(RestApiContext
            ctx,
            SchEntitiesResultModel result,
            int editFlg,
            int beforeFlg) {
        RequestModel reqMdl = ctx.getRequestModel();
        GsMessage gsMsg = new GsMessage(reqMdl);
        StringBuilder sbValue = new StringBuilder();
        //開始日時
        String startDate
           = gsMsg.getMessage("cmn.view.date", new String[] {
                   String.valueOf(result.getStartDateTime().getYear()),
                   String.valueOf(result.getStartDateTime().getMonth()),
                   String.valueOf(result.getStartDateTime().getIntDay()),
                   result.getStartDateTime().getStrHour(),
                   result.getStartDateTime().getStrMinute()
            });
        //終了日時
        String endDate
           = gsMsg.getMessage("cmn.view.date", new String[] {
                   String.valueOf(result.getEndDateTime().getYear()),
                   String.valueOf(result.getEndDateTime().getMonth()),
                   String.valueOf(result.getEndDateTime().getIntDay()),
                   result.getEndDateTime().getStrHour(),
                   result.getEndDateTime().getStrMinute()
             });

        if (beforeFlg == 1) {
            sbValue.append(gsMsg.getMessage("schedule.sch040.1"));
            sbValue.append("\n");
        } else if (beforeFlg == 2) {
            sbValue.append("\n");
            sbValue.append(gsMsg.getMessage("schedule.sch040.2"));
            sbValue.append("\n");
        }
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("sml.155"));
        sbValue.append("]");

        sbValue.append(
                Stream.concat(
                        Stream.of(result.getTargetName()),
                        result.getSameScheduledArray().stream()
                            .map(usr -> usr.getTargetName())
                            )
                .collect(Collectors.joining(","))
                );
        sbValue.append("\n");
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("schedule.sch100.11"));
        sbValue.append("]");
        sbValue.append(startDate);
        sbValue.append("\n");
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("schedule.sch100.16"));
        sbValue.append("]");
        sbValue.append(endDate);
        sbValue.append("\n");
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("cmn.title"));
        sbValue.append("]");
        sbValue.append(result.getTitleText());
        sbValue.append("\n");
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("cmn.content"));
        sbValue.append("]");
        sbValue.append(NullDefault.getString(result.getBodyText(), ""));
        if (editFlg == 0 || editFlg == 1) {
            sbValue.append("\n");
            sbValue.append("[");
            sbValue.append(gsMsg.getMessage("schedule.32"));
            sbValue.append("]");
            if (editFlg == 0) {
                sbValue.append(gsMsg.getMessage("schedule.33"));
            } else {
                sbValue.append(gsMsg.getMessage("schedule.34"));
            }
        }
        if (result.getFacilityArray() != null  && result.getFacilityArray().size() > 0) {
            sbValue.append("\n");
            sbValue.append("[");
            sbValue.append(gsMsg.getMessage("cmn.facility.name"));
            sbValue.append("]");
            sbValue.append(
                    result.getFacilityArray()
                        .stream()
                        .map(fac -> fac.getName())
                        .collect(Collectors.joining(","))
                    );
        } else {
            sbValue.append("");
        }
        return sbValue.toString();
    }

}
