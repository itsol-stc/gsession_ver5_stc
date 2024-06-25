package jp.groupsession.v2.sch.restapi.attends;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesGetParamModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesQueryBiz;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesResultModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesUtil;

public class SchAttendBiz {
    /** RestApiコンテキスト*/
    private final RestApiContext ctx__;
    /** 実行結果*/
    private SchEntitiesResultModel result__ = null;
    /** 実行結果 スケジュールSID*/
    private Integer resultScheduleSid__;
    /** パラメータ*/
    private SchAttendParamModel param__;
    /** 採番コントローラ*/
    private MlCountMtController mlCnt__;

    public SchAttendBiz(SchAttendParamModel param, RestApiContext ctx) {
        param__ = param;
        ctx__ = ctx;
        try {
            mlCnt__
            = GroupSession.getResourceManager().getCountController(
                    ctx.getRequestModel());
        } catch (Exception e) {
            throw new RuntimeException("採番コントローラ取得失敗", e);
        }
    }

    public void execute() throws SQLException {
        boolean defAutoCommit = ctx__.getCon().getAutoCommit();

        ctx__.getCon().setAutoCommit(false);
        //登録処理
        __put();
        ctx__.getCon().commit();
        ctx__.getCon().setAutoCommit(defAutoCommit);


        //実行結果取得
        result__ = __createResult(resultScheduleSid__);

    }
    private void __put() throws SQLException {
        RequestModel reqMdl = ctx__.getRequestModel();
        Connection con = ctx__.getCon();
        CommonBiz cmnBiz = new CommonBiz();
        boolean smailPluginUseFlg = cmnBiz.isCanUsePlugin(
                GSConstMain.PLUGIN_ID_SMAIL,
                ctx__.getPluginConfig());
        SchDataDao dao  = new SchDataDao(con);
        SchDataModel scdMdl = param__.getOldData(ctx__);

        //スケジュールSID
        int schSid = param__.getScheduleSid();

        //更新前 未回答数取得
        int cntBefore = dao.countAnsNone(schSid);

        UDate date = new UDate();

        //更新モデルの作成
        scdMdl.setScdSid(schSid);
        scdMdl.setScdAttendAns(
                Optional.ofNullable(
                        param__.getAnsType()
                        )
                .orElse(GSConstSchedule.ATTEND_ANS_NONE));
        scdMdl.setScdAttendComment(param__.getCommentText());
        scdMdl.setScdEuid(ctx__.getRequestUserSid());
        scdMdl.setScdEdate(date);

        dao.updateAnsKbn(scdMdl);
        resultScheduleSid__ = schSid;
        //更新後 未回答数取得
        int cntAfter = dao.countAnsNone(schSid);
        //出欠確認 依頼者に更新完了通知を行う
        //未回答件数が無い場合、且つカウンタが1→0になった場合
        if (cntAfter == 0 && cntAfter != cntBefore) {
            boolean delFlg = dao.isCheckAttendAuSchDelete(schSid);
            //依頼者のスケジュールが削除された場合、完了通知メールを送信しない
            if (!delFlg) {
                //回答者のスケジュールSIDより紐付いている出欠確認 依頼者のスケジュールデータを取得する
                SchDataModel parSchMdl = dao.getAttendRegistSch(schSid);
                if (parSchMdl != null) {
                    //URL取得
                    String url = SchEntitiesUtil.createScheduleUrlDefo(
                            ctx__,
                            GSConstSchedule.CMD_EDIT,
                            String.valueOf(parSchMdl.getScdSid()),
                            String.valueOf(parSchMdl.getScdUsrSid()),
                            scdMdl.getScdFrDate().getDateString(),
                            String.valueOf(parSchMdl.getScdUsrKbn()));
                    try {
                        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl);
                        schCmnBiz.sendAttendCompSmail(
                                con, mlCnt__, parSchMdl, ctx__.getAppRootPath(),
                                ctx__.getPluginConfig(), smailPluginUseFlg, url, date);
                    } catch (Exception e) {
                        throw new RuntimeException("メール通知実行時例外", e);
                    }

                }
            }
        }

    }

    /**
    *
    * <br>[機  能] 実行結果モデルの生成
    * <br>[解  説]
    * <br>[備  考]
    * @param resultScheduleSid 対象SID
    * @return 実行結果モデル
    */
   private SchEntitiesResultModel __createResult(int resultScheduleSid) {
       SchEntitiesGetParamModel qParam = new SchEntitiesGetParamModel();
       qParam.setScheduleSid(resultScheduleSid);
       SchEntitiesQueryBiz qBiz = new SchEntitiesQueryBiz(
               qParam,
               ctx__);
       qBiz.execute();
       List<SchEntitiesResultModel> result = qBiz.getResult();
       if (result.size() > 0) {
           return result.get(0);
       }
       return null;
   }

    /**
     * <p>result を取得します。
     * @return result
     * @see jp.groupsession.v2.sch.restapi.attends.SchAttendBiz#result__
     */
    public SchEntitiesResultModel getResult() {
        return result__;
    }
}
