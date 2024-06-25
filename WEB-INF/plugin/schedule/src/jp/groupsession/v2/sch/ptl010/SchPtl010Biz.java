package jp.groupsession.v2.sch.ptl010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.PortletBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.biz.SchEasyRegisterBiz;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.ptl020.SchPtl020Biz;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール ポートレット グループスケジュール週間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchPtl010Biz implements PortletBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchPtl010Biz.class);

    /**
     * 初期表示画面情報を取得します
     * @param paramMdl SchPtl010ParamModel
     * @param con コネクション
     * @param changeDateFlg 日付変更フラグ false:変更なし true:変更あり
     * @param usModel ユーザモデル
     * @param reqMdl RequestModel
     * @return Man001paramMdl アクションフォーム
     * @throws SQLException SQL実行時例外
     */
    public SchPtl010ParamModel getInitData(
            SchPtl010ParamModel paramMdl,
            Connection con,
            boolean changeDateFlg,
            BaseUserModel usModel,
            RequestModel reqMdl) throws SQLException {

        int grpSid = paramMdl.getSchDspGrpSid();

        //グループスケジュール閲覧権限を判定
        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        boolean dspOk = schBiz.isDspOkGroup(grpSid, usModel.getUsrsid(), con);
        if (!dspOk) {
            paramMdl.setSchPtlDspFlg(1);
            return paramMdl;
        }

        //グループ名を取得する。
        GroupDao grpDao = new GroupDao(con);
        CmnGroupmModel grpModel = grpDao.getGroup(grpSid);
        if (grpModel == null) {
            paramMdl.setSchPtlDspFlg(1);
            return paramMdl;
        }
        paramMdl.setSchPtl010GrpName(grpModel.getGrpName());

        //週間スケジュール
        __getWeekSchedule(paramMdl, reqMdl, con, changeDateFlg, usModel.getUsrsid());

        //管理者権限区分
        CommonBiz commonBiz = new CommonBiz();
        boolean isAdmin = commonBiz.isPluginAdmin(con, usModel, GSConstSchedule.PLUGIN_ID_SCHEDULE);
        if (isAdmin) {
            paramMdl.setAdminKbn(GSConst.USER_ADMIN);
        } else {
            paramMdl.setAdminKbn(GSConst.USER_NOT_ADMIN);
        }

        //指定グループのスケジュールに追加変更可能か設定する
        SchDao schDao = new SchDao(con);
        paramMdl.setSchRegistFlg(schDao.canRegistGroupSchedule(grpSid, usModel.getUsrsid()));

        SchCommonBiz cmnBiz = new SchCommonBiz(reqMdl);
        //個人設定取得&作成
        SchPriConfModel confMdl = cmnBiz.getSchPriConfModel(con, usModel.getUsrsid());
        //管理者設定を取得
        SchAdmConfModel adminConf = cmnBiz.getAdmConfModel(con);
        //簡易登録画面用値設定
        SchEasyRegisterBiz serBiz = new SchEasyRegisterBiz(con, reqMdl); 
        paramMdl.setEasyRegister(serBiz.setInitDisp(adminConf, confMdl));

        paramMdl.setSchPtlDspFlg(0);
        return paramMdl;
    }
    /**
     * 週間スケジュール初期表示画面情報を取得します
     * @param paramMdl SchPtl010ParamModel
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param changeDateFlg 日付変更フラグ false:変更なし true:変更あり
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __getWeekSchedule(
            SchPtl010ParamModel paramMdl,
            RequestModel reqMdl,
            Connection con,
            boolean changeDateFlg,
            int sessionUsrSid) throws SQLException {

        //表示開始日
        String strDspDate = NullDefault.getString(
                paramMdl.getSchWeekDate(), new UDate().getDateString());
        UDate dspDate = new UDate();
        dspDate.setDate(strDspDate);

        //個人設定取得&作成
        Sch010Biz biz = new Sch010Biz(reqMdl);
        SchPriConfModel confMdl = biz.getPrivateConf(sessionUsrSid, con);

        //各ユーザで設定した週スケジュールの開始曜日を取得
        int startWeek = confMdl.getSccIniWeek();

        //表示開始曜日に今日を設定した場合
        if (startWeek == 0 && paramMdl.getMoveKbn() == 0) {
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

        int grpSId = paramMdl.getSchDspGrpSid();

        //表示項目取得
        paramMdl.setSchWeekDate(dspDate.getDateString());
        paramMdl.setSchWeekMdl(
                __getWeekScheduleMdl(sessionUsrSid, dspDate, con, grpSId, reqMdl));
        paramMdl.setSchSelectUsrSid(String.valueOf(sessionUsrSid));
    }

    /**
     * 週間スケジュール表示用モデル一式を取得する
     * @param usrSid ユーザSID
     * @param dspDate 表示開始日付
     * @param con コネクション
     * @param grpSid グループSID
     * @param reqMdl RequestModel
     * @return ScheduleWeekModel
     * @throws SQLException SQL実行時例外
     */
    private SchPtl010Model __getWeekScheduleMdl(
                                        int usrSid,
                                        UDate dspDate,
                                        Connection con,
                                        int grpSid,
                                        RequestModel reqMdl)
    throws SQLException {

        log__.debug("メイン:週間スケジュール取得");
        GsMessage gsMsg = new GsMessage(reqMdl);
        //年
        String textYear = gsMsg.getMessage("cmn.year", new String[] {dspDate.getStrYear()});
        //月
        String textMonth = gsMsg.getMessage("cmn.month");
        SchPtl010Model ret = new SchPtl010Model();
        //週間情報取得
        Sch010Biz sch010Biz = new Sch010Biz(reqMdl);
        boolean myGroupFlg = false;
        boolean onlyGrpFlg = true;

        ret.setSchWeekCalendarList(sch010Biz.getWeekCalender(dspDate.cloneUDate(), con));
        ret.setSchWeekTopList(
                sch010Biz.getWeekScheduleTopList(
                        dspDate, grpSid, usrSid, myGroupFlg, onlyGrpFlg, con));
        ret.setSchWeekDspYm(textYear + dspDate.getStrMonth() + textMonth);

        return ret;
    }

    /**
     * <br>プラグインポートレットタイトルを取得する。
     * @param con コネクション
     * @param paramMap パラメータマップ
     * @return title ポートレットプラグインタイトル
     * @throws Exception 実行時例外
     */
    public String getPortletTitle(Connection con, HashMap<String, String> paramMap)
    throws Exception {

        String title = "";

        if (paramMap == null) {
            return title;
        }

        //マップからパラメータを取得
        String grpSidValue = paramMap.get(SchPtl020Biz.SCH_PORTLET_PARAM1);

        if (StringUtil.isNullZeroString(grpSidValue)) {
            return title;
        }
        int grpSid = NullDefault.getInt(grpSidValue, 0);

        //グループ名を設定する。
        GroupDao grpDao = new GroupDao(con);
        CmnGroupmModel grpModel = grpDao.getGroup(grpSid);
        if (grpModel != null) {
            title = grpModel.getGrpName();
        }

        return title;
    }
}
