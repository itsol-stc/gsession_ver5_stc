package jp.groupsession.v2.sch.restapi.entities.reminder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchPriPushModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesGetParamModel;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesQueryBiz;
import jp.groupsession.v2.sch.restapi.entities.SchEntitiesResultModel;

public class SchReminderBiz {
    /** RestApiコンテキスト*/
    private final RestApiContext ctx__;
    /** 実行結果*/
    private SchEntitiesResultModel result__ = null;
    /** 実行結果 スケジュールSID*/
    private Integer resultScheduleSid__;
    /** パラメータ*/
    private SchReminderParamModel param__;

    public SchReminderBiz(SchReminderParamModel param, RestApiContext ctx) {
        param__ = param;
        ctx__ = ctx;
    }

    public void execute() throws SQLException {
        boolean defAutoCommit = ctx__.getCon().getAutoCommit();

        ctx__.getCon().setAutoCommit(false);
        //登録処理
        __put();
        ctx__.getCon().commit();

        ctx__.getCon().setAutoCommit(defAutoCommit);


        //実行結果取得
        result__ = __createResult(Optional.ofNullable(resultScheduleSid__)
                .orElse(-1));

    }
    private void __put() throws SQLException {
        RequestModel reqMdl = ctx__.getRequestModel();
        Connection con = ctx__.getCon();

        //スケジュールSID
        int schSid = param__.getScheduleSid();
        resultScheduleSid__ = schSid;

        if (param__.getRemindTimingType() == null) {
            return;
        }

        SchDataDao dao  = new SchDataDao(con);
        SchDataModel updMdl = new SchDataModel();

        //スケジュール情報取得
        SchDataModel scdMdl = param__.getOldData(ctx__);

        //スケジュールSID
        updMdl.setScdSid(schSid);

        //リマインダー時間
        int usrKbn = scdMdl.getScdUsrKbn();
        int reminderStr = scdMdl.getScdReminder();
        int targetGrpFlg = scdMdl.getScdTargetGrp();

        if (usrKbn == GSConstSchedule.USER_KBN_USER) {
            reminderStr =
                Optional.ofNullable(
                        param__.getRemindTimingType()
                        )
                .orElse(scdMdl.getScdReminder());
            if (reminderStr < GSConstSchedule.REMINDER_TIME_NO
                    || reminderStr > GSConstSchedule.REMINDER_TIME_ONE_WEEK) {
                reminderStr =  scdMdl.getScdReminder();
            }
        } else if (usrKbn == GSConstSchedule.USER_KBN_GROUP) {
            targetGrpFlg =
                Optional.ofNullable(
                        param__.getRemindGrpFlg()
                        )
                .orElse(scdMdl.getScdTargetGrp());
            if (targetGrpFlg != GSConstSchedule.REMINDER_USE_YES
            && targetGrpFlg != GSConstSchedule.REMINDER_USE_NO) {
                targetGrpFlg =  scdMdl.getScdTargetGrp();
            }
        }

        updMdl.setScdReminder(reminderStr);
        updMdl.setScdTargetGrp(targetGrpFlg);

        //リマインダー情報の更新を行う
        dao.updateReminder(updMdl);

        //通知リスト更新
        SchCommonBiz biz = new SchCommonBiz(con, reqMdl);
        scdMdl.setScdReminder(reminderStr);
        scdMdl.setScdTargetGrp(targetGrpFlg);
        biz.deletePushList(scdMdl.getScdSid());
        if (scdMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
            if (usrKbn == GSConstSchedule.USER_KBN_USER) {
                biz.insertPushInfUser(scdMdl);
            } else if (usrKbn == GSConstSchedule.USER_KBN_GROUP
                    && targetGrpFlg
                    == GSConstSchedule.REMINDER_USE_YES) {
                Map<Integer, SchPriPushModel> reminderGroupMap =
                        biz.getGroupPriConf(scdMdl.getScdUsrSid());
                biz.insertPushInfGroup(scdMdl.getScdSid(),
                                    reminderGroupMap,
                                    scdMdl.getScdFrDate(),
                                    targetGrpFlg);
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
