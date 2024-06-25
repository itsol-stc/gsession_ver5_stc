package jp.groupsession.v2.sch.biz;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchEasyRegisterModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.sch010.Sch010Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュールプラグインの簡易登録に関するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchEasyRegisterBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchEasyRegisterBiz.class);

    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト */
    private RequestModel reqMdl__ = null;
    /** 採番コントローラ */
    MlCountMtController cntCon__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public SchEasyRegisterBiz() {
    }
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public SchEasyRegisterBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param cntCon 採番
     */
    public SchEasyRegisterBiz(Connection con, RequestModel reqMdl, MlCountMtController cntCon) {
        con__ = con;
        reqMdl__ = reqMdl;
        cntCon__ = cntCon;
    }


    /**
     * <br>[機  能] 簡易登録画面の初期値を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param admConf 管理者設定
     * @param priConf 個人設定
     * @return 簡易登録モデルを返す
     */
    public SchEasyRegisterModel setInitDisp(SchAdmConfModel admConf, SchPriConfModel priConf) {

        SchEasyRegisterModel serMdl = new SchEasyRegisterModel();

        serMdl.setTimeUnits(admConf.getSadHourDiv());
        if (admConf.getSadInitPublicStype() == GSConstSchedule.SAD_INIPUBLIC_STYPE_ADM) {
            serMdl.setInitPubKbn(admConf.getSadIniPublic());
        } else {
            serMdl.setInitPubKbn(priConf.getSccIniPublic());
        }
        serMdl.setInitTitleColor(priConf.getSccIniFcolor());
        serMdl.setTitleColorKbn(admConf.getSadMsgColorKbn());
        if (admConf.getSadIniTimeStype() == GSConstSchedule.SAD_INITIME_STYPE_ADM) {
            String frTime = String.format("%02d", admConf.getSadIniFrH())
                    + ":" + String.format("%02d", admConf.getSadIniFrM());
            serMdl.setFrTime(frTime);
            serMdl.setInitFrTime(frTime);
            String toTime = String.format("%02d", admConf.getSadIniToH())
                    + ":" + String.format("%02d", admConf.getSadIniToM());
            serMdl.setToTime(toTime);
            serMdl.setInitToTime(toTime);
        } else {
            String frTime = String.format("%02d", priConf.getSccIniFrDate().getIntHour())
                    + ":" + String.format("%02d", priConf.getSccIniFrDate().getIntMinute());
            serMdl.setFrTime(frTime);
            serMdl.setInitFrTime(frTime);
            String toTime = String.format("%02d", priConf.getSccIniToDate().getIntHour())
                    + ":" + String.format("%02d", priConf.getSccIniToDate().getIntMinute());
            serMdl.setToTime(toTime);
            serMdl.setInitToTime(toTime);
        }
        serMdl.setAmFrHour(admConf.getSadAmFrH());
        serMdl.setAmFrMin(admConf.getSadAmFrM());
        serMdl.setAmToHour(admConf.getSadAmToH());
        serMdl.setAmToMin(admConf.getSadAmToM());

        serMdl.setPmFrHour(admConf.getSadPmFrH());
        serMdl.setPmFrMin(admConf.getSadPmFrM());
        serMdl.setPmToHour(admConf.getSadPmToH());
        serMdl.setPmToMin(admConf.getSadPmToM());

        serMdl.setAllDayFrHour(admConf.getSadAllFrH());
        serMdl.setAllDayFrMin(admConf.getSadAllFrM());
        serMdl.setAllDayToHour(admConf.getSadAllToH());
        serMdl.setAllDayToMin(admConf.getSadAllToM());

        return serMdl;
    }

    /**
     * <br>[機  能] チェック用に簡易登録モデルを生成して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return serForm 簡易登録モデル
     */
    public SchEasyRegisterModel createChkMdl(HttpServletRequest req) {

        SchEasyRegisterModel serForm = new SchEasyRegisterModel();
        serForm.setFrDate(req.getParameter("easyRegister.frDate"));
        serForm.setFrTime(req.getParameter("easyRegister.frTime"));
        serForm.setToDate(req.getParameter("easyRegister.toDate"));
        serForm.setToTime(req.getParameter("easyRegister.toTime"));
        serForm.setSelectSid(Integer.parseInt(
                NullDefault.getString(req.getParameter("selectUser"), "0")));
        serForm.setSelectDate(req.getParameter("selectDate"));
        serForm.setSelectKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("selectKbn"), "0")));
        serForm.setTimeFree(Integer.parseInt(
                NullDefault.getString(req.getParameter("easyRegister.timeFree"), "0")));
        serForm.setTitleColor(Integer.parseInt(
                NullDefault.getString(req.getParameter("easyRegister.titleColor"), "0")));
        serForm.setTitle(req.getParameter("easyRegister.title"));
        serForm.setContent(req.getParameter("easyRegister.content"));
        serForm.setPublicKbn(Integer.parseInt(
                NullDefault.getString(req.getParameter("easyRegister.publicKbn"), "1")));

        return serForm;
    }

    /**
     * <br>[機  能] スケジュールを新規登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl アクションフォーム
     * @param appRootPath アプリケーションRoot
     * @param plconf プラグイン設定
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param dspDate 表示開始日付
     * @param grpSid 表示グループSID
     * @param dspMod 表示モード
     * @return sidDataList 予約SID、施設SIDリスト
     * @throws Exception SQL実行時例外
     */
    public ArrayList<int []> insertScheduleDate(
            RequestModel reqMdl,
            SchEasyRegisterModel paramMdl,
            String appRootPath,
            PluginConfig plconf,
            boolean smailPluginUseFlg,
            String dspDate,
            String grpSid,
            String dspMod) throws Exception {

        SchDataModel schMdl = null;
        SchCommonBiz cmnBiz = new SchCommonBiz(con__, reqMdl);

        int usrKbn = paramMdl.getSelectKbn();
        int selectUsrSid = paramMdl.getSelectSid();
        BaseUserModel usMdl = reqMdl.getSmodel();
        int sessionUsrSid = usMdl.getUsrsid();

        //登録モデルを作成
        schMdl = new SchDataModel();
        UDate frDate = new UDate();
        UDate toDate = frDate.cloneUDate();
        UDate now = new UDate();

        ArrayList<int []> sidDataList = new ArrayList<int []>();

        int frHour = GSConstSchedule.DAY_START_HOUR;
        int frMin = GSConstSchedule.DAY_START_MINUTES;
        int toHour = GSConstSchedule.DAY_END_HOUR;
        int toMin = GSConstSchedule.DAY_END_MINUTES;
        if (paramMdl.getTimeFree() == GSConstSchedule.TIME_EXIST) {
            frHour = Integer.parseInt(paramMdl.getFrTime().substring(0, 2));
            frMin = Integer.parseInt(paramMdl.getFrTime().substring(3, 5));
            toHour = Integer.parseInt(paramMdl.getToTime().substring(0, 2));
            toMin = Integer.parseInt(paramMdl.getToTime().substring(3, 5));
            schMdl.setScdDaily(GSConstSchedule.TIME_EXIST);
        } else {
            schMdl.setScdDaily(GSConstSchedule.TIME_NOT_EXIST);
        }
        frDate.setDate(paramMdl.getFrDate().replace("/", ""));
        if (frHour != -1 && frMin != -1) {
            frDate.setHour(frHour);
            frDate.setMinute(frMin);
            frDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            frDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        }

        toDate.setDate(paramMdl.getToDate().replace("/", ""));
        if (toHour != -1 && toMin != -1) {
            toDate.setHour(toHour);
            toDate.setMinute(toMin);
            toDate.setSecond(GSConstSchedule.DAY_START_SECOND);
            toDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        }

        schMdl.setScdFrDate(frDate);
        schMdl.setScdToDate(toDate);
        schMdl.setScdBgcolor(paramMdl.getTitleColor());
        schMdl.setScdTitle(paramMdl.getTitle());
        schMdl.setScdValue(paramMdl.getContent());
        schMdl.setScdBiko("");
        schMdl.setScdPublic(paramMdl.getPublicKbn());

        schMdl.setScdAuid(sessionUsrSid);
        schMdl.setScdAdate(now);
        schMdl.setScdEuid(sessionUsrSid);
        schMdl.setScdEdate(now);
        //編集区分
        schMdl.setScdEdit(GSConstSchedule.EDIT_CONF_NONE);
        //拡張登録SID
        int extSid = -1;
        schMdl.setSceSid(extSid);
        //リマインダー通知設定
        if (usrKbn == GSConstSchedule.USER_KBN_USER) {
            //ユーザのリマインダー通知時間初期値を設定
            Sch010Biz biz = new Sch010Biz(reqMdl);
            SchPriConfModel spcMdl = biz.getPrivateConf(selectUsrSid, con__);
            schMdl.setScdReminder(spcMdl.getSccReminder());
        } else {
            //グループスケジュールを"通知する"に設定
            schMdl.setScdTargetGrp(GSConstSchedule.REMINDER_USE_YES);
        }

        int scdSid = -1;
        int scdGpSid = GSConstSchedule.DF_SCHGP_ID;
        int scdResSid = GSConstSchedule.DF_SCHGP_ID;
        //SID採番
        scdSid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_SCHEDULE,
                SaibanModel.SBNSID_SUB_SCH, sessionUsrSid);
        schMdl.setScdSid(scdSid);
        schMdl.setScdUsrSid(selectUsrSid);
        schMdl.setScdUsrKbn(usrKbn);
        schMdl.setScdGrpSid(scdGpSid);
        schMdl.setScdRsSid(scdResSid);

        schMdl.setScdAttendKbn(GSConstSchedule.ATTEND_KBN_NO);
        schMdl.setScdAttendAns(GSConstSchedule.ATTEND_ANS_NONE);
        schMdl.setScdAttendAuKbn(GSConstSchedule.ATTEND_REGIST_USER_NO);

        ISchRegister.Builder regBld = ISchRegister.simpleRegistBuilder(con__, reqMdl__, cntCon__, schMdl);
        regBld.setUseRsv(false);

        //スケジュール登録ロジッククラス設定完了
        ISchRegister reg = regBld.build();

        //スケジュール・関連情報登録実行
        reg.regist();

        //ユーザSID
        String usrSid = String.valueOf(selectUsrSid);
        //URL取得
        String url = __createScheduleUrlDefo(GSConstSchedule.CMD_EDIT,
                String.valueOf(scdSid), usrSid,
                paramMdl, dspDate, grpSid, dspMod);
        cmnBiz.sendPlgSmail(con__, cntCon__, schMdl, appRootPath, plconf, smailPluginUseFlg, url);

        return sidDataList;
    }

    /**
     * <br>[機  能] スケジュール一般登録確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd 処理モード
     * @param sch010SchSid スケジュールSID
     * @param usrSid ユーザーSID
     * @param paramMdl SchEasyRegisterModel
     * @param dspDate 画面表示開始日付
     * @param grpSid 表示グループSID
     * @param dspMod 表示モード
     * @return スケジュール一般登録確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    private String __createScheduleUrlDefo(String cmd,
            String sch010SchSid, String usrSid,
            SchEasyRegisterModel paramMdl, String dspDate,
            String grpSid, String dspMod)
                    throws UnsupportedEncodingException {
        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl__);
            paramUrl +=  "/" + GSConstSchedule.PLUGIN_ID_SCHEDULE;

            paramUrl += "/sch040.do";
            paramUrl += "?sch010SelectDate=" + paramMdl.getSelectDate();
            paramUrl += "&cmd=" + cmd;
            paramUrl += "&sch010SchSid=" + sch010SchSid;
            paramUrl += "&sch010SelectUsrSid=" + usrSid;
            paramUrl += "&sch010SelectUsrKbn=" + paramMdl.getSelectKbn();
            paramUrl += "&sch010DspDate=" + dspDate;
            paramUrl += "&dspMod=" + dspMod;
            paramUrl += "&sch010DspGpSid=" + grpSid;

            return urlBiz.getAccessUrl(reqMdl__, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }
    }

    /**
     * <br>[機  能]ログ用メッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param serMdl 簡易登録モデル
     * @return log ログ内容
     * @throws Exception Exception
     */
    public String logMessage(SchEasyRegisterModel serMdl) throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        StringBuilder sbValue = new StringBuilder();

        String target = "";
        if (serMdl.getSelectKbn() == 0) {
            UserSearchDao usDao = new UserSearchDao(con__);
            UserSearchModel usMdl = usDao.getUserInfoJtkb(serMdl.getSelectSid(), 0);
            target = usMdl.getUsiSei() + " " + usMdl.getUsiMei();
        } else {
            GroupDao gDao = new GroupDao(con__);
            CmnGroupmModel cgMdl = gDao.getGroup(serMdl.getSelectSid());
            target = cgMdl.getGrpName();

        }
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("sml.155"));
        sbValue.append("]");
        sbValue.append(target);
        sbValue.append("\n");
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("schedule.sch100.11"));
        sbValue.append("]");
        sbValue.append(serMdl.getFrDate());
        sbValue.append(" ");
        sbValue.append(serMdl.getFrTime());
        sbValue.append("\n");
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("schedule.sch100.16"));
        sbValue.append("]");
        sbValue.append(serMdl.getToDate());
        sbValue.append(" ");
        sbValue.append(serMdl.getToTime());
        sbValue.append("\n");
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("cmn.title"));
        sbValue.append("]");
        sbValue.append(serMdl.getTitle());
        sbValue.append("\n");
        sbValue.append("[");
        sbValue.append(gsMsg.getMessage("cmn.content"));
        sbValue.append("]");
        sbValue.append(serMdl.getContent());

        return sbValue.toString();
    }
} 
