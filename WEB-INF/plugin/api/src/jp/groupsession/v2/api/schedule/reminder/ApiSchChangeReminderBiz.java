package jp.groupsession.v2.api.schedule.reminder;

import java.sql.Connection;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.model.SchDataModel;

/**
 * <br>[機  能] スケジュール リマインダー編集 WEBAPI biz
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiSchChangeReminderBiz {
    /** コネクション */
    public Connection con__ = null;
    /** リクエストモデル*/
    public RequestModel reqMdl__ = null;

    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public ApiSchChangeReminderBiz(Connection con,
            RequestModel reqMdl) {
        super();
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] リマインダー情報を更新する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param sessionUsrSid ユーザSID
     * @throws Exception SQL実行時例外
     */
    public void updateScheduleReminder(ApiSchChangeReminderParamModel paramMdl,
            int sessionUsrSid) throws Exception {
        if (paramMdl.getReminder() != null) {
            SchDataDao dao  = new SchDataDao(con__);
            SchDataModel updMdl = new SchDataModel();

            //スケジュール情報取得
            SchDataModel scdMdl = dao.getSchData(Integer.valueOf(paramMdl.getSchSid()));

            int reminderStr = NullDefault.getInt(
                    paramMdl.getReminder(), scdMdl.getScdReminder());
            if (reminderStr < GSConstSchedule.REMINDER_TIME_NO
                    || reminderStr > GSConstSchedule.REMINDER_TIME_ONE_WEEK) {
                reminderStr =  scdMdl.getScdReminder();
            }

            //スケジュールSID
            updMdl.setScdSid(Integer.valueOf(paramMdl.getSchSid()));

            //リマインダー時間
            updMdl.setScdReminder(reminderStr);
            updMdl.setScdTargetGrp(scdMdl.getScdTargetGrp());

            //リマインダー情報の更新を行う
            dao.updateReminder(updMdl);

            //通知リスト更新
            SchCommonBiz biz = new SchCommonBiz(con__, reqMdl__);
            scdMdl.setScdReminder(reminderStr);
            biz.deletePushList(scdMdl.getScdSid());
            biz.insertPushInfUser(scdMdl);
        }
    }
}
