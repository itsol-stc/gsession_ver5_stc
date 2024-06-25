package jp.groupsession.v2.sch.main;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchEasyRegisterBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール(メイン画面表示用)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchMainBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchMainBiz.class);

    /**
     * 初期表示画面情報を取得します
     * @param form アクションフォーム
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param changeDateFlg 日付変更フラグ false:変更なし true:変更あり
     * @param pconfig プラグインコンフィグ
     * @return アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public SchMainForm getInitData(
            SchMainForm form,
            RequestModel reqMdl,
            Connection con,
            boolean changeDateFlg,
            PluginConfig pconfig) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();

        //リクエストパラメータを取得
        //週間スケジュール
        __getWeekSchedule(form, reqMdl, con, changeDateFlg, pconfig);

        //管理者権限区分
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);
        if (isAdmin) {
            form.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            form.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }
        return form;
    }
    /**
     * 週間スケジュール初期表示画面情報を取得します
     * @param form アクションフォーム
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param changeDateFlg 日付変更フラグ false:変更なし true:変更あり
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行時例外
     */
    private void __getWeekSchedule(
            SchMainForm form,
            RequestModel reqMdl,
            Connection con,
            boolean changeDateFlg,
            PluginConfig pconfig) throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        //表示開始日
        String strDspDate = NullDefault.getString(
                form.getSchWeekDate(), new UDate().getDateString());
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);

        //管理者設定を取得
        SchCommonBiz cmnBiz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = cmnBiz.getAdmConfModel(con);

        //個人設定取得&作成
        Sch010Biz biz = new Sch010Biz(reqMdl, pconfig);
        SchPriConfModel confMdl = biz.getPrivateConf(sessionUsrSid, con);

        //各ユーザで設定した週スケジュールの開始曜日を取得
        int startWeek = confMdl.getSccIniWeek();

        //表示開始曜日に今日を設定した場合
        if (startWeek == 0 && form.getMoveKbn() == 0) {
            changeDateFlg = true;
            dspDate.setDate(new UDate().getDateString());
        }

        if (!changeDateFlg) {
            int nowWeek = dspDate.getWeek();
            log__.debug("***対象の日付は" + dspDate.getDateString());
            //開始日付を取得
            int difWeek = startWeek - nowWeek;
            if (difWeek > 0) {
                dspDate.addDay(-7 + difWeek);

            } else {
                dspDate.addDay(difWeek);
            }
            log__.debug("***変更した日付は" + dspDate.getDateString());
        }

        //表示項目取得
        form.setSchWeekDate(dspDate.getDateString());
        form.setSchWeekMdl(__getWeekScheduleMdl(sessionUsrSid, dspDate, con, reqMdl, pconfig));
        form.setSchSelectUsrSid(String.valueOf(sessionUsrSid));
        form.setSchSelectUsrKbn(String.valueOf(GSConstSchedule.USER_KBN_USER));

        //簡易登録画面用値設定
        SchEasyRegisterBiz serBiz = new SchEasyRegisterBiz(con, reqMdl); 
        form.setEasyRegister(serBiz.setInitDisp(adminConf, confMdl));
    }

    /**
     * 週間スケジュール表示用モデル一式を取得する
     * @param usrSid ユーザSID
     * @param dspDate 表示開始日付
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param pconfig プラグインコンフィグ
     * @return ScheduleWeekModel
     * @throws SQLException SQL実行時例外
     */
    private ScheduleWeekModel __getWeekScheduleMdl(
                                        int usrSid,
                                        UDate dspDate,
                                        Connection con,
                                        RequestModel reqMdl,
                                        PluginConfig pconfig)
    throws SQLException {

        log__.debug("メイン:週間スケジュール取得");
        GsMessage gsMsg = new GsMessage(reqMdl);
        //年
        String textYear = gsMsg.getMessage("cmn.year", new String[] {dspDate.getStrYear()});
        //月
        String textMonth = gsMsg.getMessage("cmn.month");
        ScheduleWeekModel ret = new ScheduleWeekModel();
        //週間情報取得
        Sch010Biz sch010Biz = new Sch010Biz(reqMdl, pconfig);
        boolean myGroupFlg = false;
        ret.setSchWeekCalendarList(sch010Biz.getWeekCalender(dspDate.cloneUDate(), con));
        ret.setSchWeekTopList(sch010Biz.getWeekScheduleTopListWithBelongGroup(dspDate.cloneUDate(),
                usrSid, myGroupFlg, true, con));
        ret.setSchWeekDspYm(textYear + dspDate.getStrMonth() + textMonth);

        return ret;
    }
}
