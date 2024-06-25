package jp.groupsession.v2.api.ntp.nippou.monthly;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.jdom2.Document;
import org.jdom2.Element;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.api.AbstractApiAction;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouSearchDao;
import jp.groupsession.v2.ntp.dao.NtpCommentDao;
import jp.groupsession.v2.ntp.model.AnkenPermitCheckModel;
import jp.groupsession.v2.ntp.model.NtpDataModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010Biz;
import jp.groupsession.v2.ntp.ntp010.Ntp010UsrModel;
import jp.groupsession.v2.ntp.ntp010.SimpleNippouModel;
import jp.groupsession.v2.ntp.ntp020.Ntp020DayOfModel;
import jp.groupsession.v2.ntp.ntp020.Ntp020MonthOfModel;
import jp.groupsession.v2.usr.GSConstUser;
/**
 * <br>[機  能] WEBAPI 日報月次取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouMonthlyAction extends AbstractApiAction {
    /** ロガーオブジェクト */
    private static Log log__ =
            LogFactory.getLog(new Throwable().getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {
        log__.debug("createXml start");
        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //セッション情報を取得
        BaseUserModel usModel = umodel;
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        ApiNippouMonthlyForm thisForm = (ApiNippouMonthlyForm) form;
        ActionErrors err = thisForm.validateCheck(con, req, sessionUsrSid);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }

        int usrSid = NullDefault.getInt(thisForm.getUsrSid(), sessionUsrSid);

        UDate toDate;
        UDate frDate = new UDate();
        //取得範囲が含まれている場合
        if (!StringUtil.isNullZeroString(thisForm.getStart())
                && !StringUtil.isNullZeroString(thisForm.getEnd())) {
            frDate.setZeroHhMmSs();
            String dateStr = thisForm.getStart();
            frDate.setYear(Integer.parseInt(dateStr.substring(0, 4)));
            frDate.setMonth(Integer.parseInt(dateStr.substring(5, 7)));
            frDate.setDay(Integer.parseInt(dateStr.substring(8, 10)));
            dateStr = thisForm.getEnd();
            toDate = new UDate();
            toDate.setZeroDdHhMmSs();
            toDate.setYear(Integer.parseInt(dateStr.substring(0, 4)));
            toDate.setMonth(Integer.parseInt(dateStr.substring(5, 7)));
            toDate.setDay(Integer.parseInt(dateStr.substring(8, 10)));

        } else {
            frDate.setZeroHhMmSs();
            if (!StringUtil.isNullZeroString(thisForm.getMonth())) {
                frDate.setYear(Integer.parseInt(thisForm.getMonth().substring(0, 4)));
                frDate.setMonth(Integer.parseInt(thisForm.getMonth().substring(5, 7)));
            }
            frDate.setDay(1);
            toDate = frDate.cloneUDate();
            toDate.setDay(toDate.getMaxDayOfMonth());
        }


        resultSet.setAttribute("usrSid", Integer.toString(usrSid));
        resultSet.setAttribute("baseDate", UDateUtil.getSlashYYMD(frDate));

        Ntp020MonthOfModel monthBlock =
                __getMonthNippouList(frDate, toDate, usrSid, sessionUsrSid, req, con);

        for (Ntp020DayOfModel ntp020DayOfModel__
                : (List<Ntp020DayOfModel>) monthBlock.getNtp020NtpList()) {
            Element day = new Element("Result");
            resultSet.addContent(day);
            day.addContent(_createElement("NptDate", ntp020DayOfModel__.getNtpDate()));
            String dspDateStr = ntp020DayOfModel__.getNtpDate();

            String slsDateStr = dspDateStr.substring(0, 4)
                    + "/" + dspDateStr.substring(4, 6)
                    + "/" + dspDateStr.substring(6, 8);

            day.addContent(_createElement("NipDate", slsDateStr));
            day.addContent(_createElement("WeekKbn", ntp020DayOfModel__.getWeekKbn()));
            day.addContent(_createElement("HolidayKbn", ntp020DayOfModel__.getHolidayKbn()));
            Element reportSet = new Element("ReportSet");
            day.addContent(reportSet);
            reportSet.setAttribute("TotalCount",
                    Integer.toString(ntp020DayOfModel__.getNtpDataList().size()));

            for (SimpleNippouModel simpleNippouModel__ : ntp020DayOfModel__.getNtpDataList()) {
                Element report = new Element("Report");
                reportSet.addContent(report);
                report.addContent(_createElement("NipSid", simpleNippouModel__.getNtpSid()));
                report.addContent(_createElement("NipTime", simpleNippouModel__.getTime()));
                report.addContent(_createElement("NipTitle", simpleNippouModel__.getTitle()));
                report.addContent(_createElement("NipTitleClo",
                        simpleNippouModel__.getTitleColor()));
                report.addContent(_createElement("NipCommentKbn",
                        simpleNippouModel__.getNtp_cmtkbn()));
                //コメント件数取得
                report.addContent(_createElement("NipCommentCount",
                        simpleNippouModel__.getNtp_cmtCnt()));
                //確認区分取得
                report.addContent(_createElement("NipCheckKbn",
                        simpleNippouModel__.getNtp_chkKbn()));
                //いいね件数取得
                report.addContent(_createElement("NipIineCount",
                        simpleNippouModel__.getNtp_goodCnt()));
                if (simpleNippouModel__.isAnkenViewable()) {
                    report.addContent(_createElement("AnkenViewable", 1)); //案件公開
                } else {
                    report.addContent(_createElement("AnkenViewable", 0)); //案件非公開
                }

            }
        }





        return doc;
    }

    /**
     * <br>指定ユーザの月間日報を取得します
     * @param frDate 開始日付
     * @param toDate 終了日付
     * @param usrSid ユーザSID
     * @param sessionUsrSid セッションユーザSID
     * @param req リクエスト
     * @param con コネクション
     * @return ArrayList グループ>指定ユーザの順に格納
     * @throws SQLException SQL実行時例外
     */
    private Ntp020MonthOfModel __getMonthNippouList(
            UDate frDate,
            UDate toDate,
            int usrSid,
            int sessionUsrSid,
            HttpServletRequest req,
            Connection con) throws SQLException {

        frDate.setHour(GSConstNippou.DAY_START_HOUR);
        frDate.setMinute(GSConstNippou.DAY_START_MINUTES);
        frDate.setSecond(GSConstNippou.DAY_START_SECOND);
        toDate.setHour(GSConstNippou.DAY_END_HOUR);
        toDate.setMinute(GSConstNippou.DAY_END_MINUTES);
        toDate.setSecond(GSConstNippou.DAY_END_SECOND);
        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        HashMap < String, CmnHolidayModel > holMap = holDao.getHoliDayList(frDate, toDate);
        CmnHolidayModel holMdl = null;

        //グループ・指定ユーザのcolListを保持
        ArrayList<Ntp020MonthOfModel> rowList = new ArrayList<Ntp020MonthOfModel>();
        //ユーザ情報を保持
        Ntp010UsrModel usMdl = null;
        ArrayList < Ntp020DayOfModel > colList = null;
        //DB日報情報
        ArrayList < NtpDataModel > ntpDataList = null;
        //ユーザ別、１ヶ月間分の日報
        Ntp020MonthOfModel monthMdl = null;

        //指定ユーザ日報
        monthMdl = new Ntp020MonthOfModel();
        colList = new ArrayList<Ntp020DayOfModel>();
        usMdl = new Ntp010UsrModel();
        UserSearchDao usrDao = new UserSearchDao(con);

        //ユーザ日報
        CmnUsrmInfModel usrInfMdl = usrDao.getUserInfoJtkb(
                usrSid, GSConstUser.USER_JTKBN_ACTIVE);
        if (usrInfMdl != null) {
            usMdl.setUsrName(usrInfMdl.getUsiSei() + " " + usrInfMdl.getUsiMei());
            usMdl.setUsrUkoFlg(usrInfMdl.getUsrUkoFlg());
        }

        usMdl.setUsrSid(usrSid);
        usMdl.setUsrKbn(GSConstNippou.USER_KBN_USER);
        monthMdl.setNtp020UsrMdl(usMdl);
        RequestModel reqMdl = getRequestModel(req);

        NtpCommonBiz ntpCmnBiz = new NtpCommonBiz(con, reqMdl);
        AnkenPermitCheckModel napMdl = new AnkenPermitCheckModel(reqMdl,
                ntpCmnBiz.isNippouAdmin(con, reqMdl));

        //日報情報を取得(指定ユーザ)
        NippouSearchDao ntpDao = new NippouSearchDao(con);
            //グループ又はユーザの日報を取得
            ntpDataList = ntpDao.select(
                    usrSid,
                    0,
                    -1,
                    frDate,
                    toDate,
                    GSConstNippou.DSP_MOD_MONTH,
                    napMdl);
            UserSearchModel usrMdl = new UserSearchModel();
            usrMdl.setUsrSid(usrSid);
            ArrayList<UserSearchModel> belongList = new ArrayList<UserSearchModel>();
            belongList.add(usrMdl);
            Map<Integer, Integer> chkMap = ntpDao.getCheckCntMap(
                    belongList,
                    sessionUsrSid,
                    GSConstNippou.USER_KBN_USER,
                    -1,
                    frDate,
                    toDate,
                    GSConstNippou.DSP_MOD_WEEK
                    );
            Map<Integer, Integer> iinMap = ntpDao.getGoodCntMap(
                    belongList,
                    GSConstNippou.USER_KBN_USER,
                    -1,
                    frDate,
                    toDate,
                    GSConstNippou.DSP_MOD_WEEK
                    );
            Map<Integer, Integer> cmmMap = ntpDao.getCommentCntMap(
                    belongList,
                    GSConstNippou.USER_KBN_USER,
                    -1,
                    frDate,
                    toDate,
                    GSConstNippou.DSP_MOD_WEEK
                    );

        Ntp020DayOfModel dayMdl = null;
        ArrayList<SimpleNippouModel> dayMdlList = null;
        SimpleNippouModel dspNtpMdl = null;
        while (frDate.compareDateYMD(toDate) != UDate.SMALL) {
            //１日分の日報
            dayMdlList = new ArrayList<SimpleNippouModel>();
            dayMdl = new Ntp020DayOfModel();
            // 休日名称
            holMdl = holMap.get(frDate.getDateString());
            if (holMdl != null) {
              dayMdl.setHolidayName(holMdl.getHolName());
              dayMdl.setHolidayKbn(GSConstNippou.HOLIDAY_TRUE);
            } else {
              dayMdl.setHolidayName(null);
              dayMdl.setHolidayKbn(GSConstNippou.HOLIDAY_FALSE);
            }
            dayMdl.setDspDay(String.valueOf(frDate.getIntDay()));
            dayMdl.setNtpDate(frDate.getDateString());
            dayMdl.setUsrSid(usrSid);
            dayMdl.setUsrKbn(0);
            dayMdl.setWeekKbn(frDate.getWeek());

            NtpDataModel ntpMdl = null;
            for (int j = 0; j < ntpDataList.size(); j++) {
                //日報１個
                ntpMdl = ntpDataList.get(j);
                //本日の日報か判定
                if (Ntp010Biz.isTodayNippou(ntpMdl, frDate)) {
                    dspNtpMdl = new SimpleNippouModel();
                    dspNtpMdl.setNtpSid(ntpMdl.getNipSid());
                    dspNtpMdl.setUserSid(String.valueOf(ntpMdl.getUsrSid()));
                    dspNtpMdl.setTitle(ntpMdl.getNipTitle());
                    dspNtpMdl.setTime(Ntp010Biz.getTimeString(ntpMdl, frDate));
                    dspNtpMdl.setTitleColor(ntpMdl.getNipTitleClo());
                    dspNtpMdl.setDetail(ntpMdl.getNipDetail());
                    dspNtpMdl.setUserName(ntpMdl.getntpUserName());

                    if (ntpMdl.getUsrSid() == sessionUsrSid) {
                    } else {
                        //他ユーザ
                        if (ntpMdl.getNipPublic() == GSConstNippou.DSP_YOTEIARI) {
                            //予定あり
                            dspNtpMdl.setTitle(GSConstNippou.DSP_YOTEIARI_STRING);
                        } else if (ntpMdl.getNipPublic() == GSConstNippou.DSP_NOT_PUBLIC) {
                            //非公開
                            continue;
                        }
                    }
                    // 追加： SimpleNippouModelに追加した区分・人数に値をセットする
                    Ntp010Biz biz = new Ntp010Biz(con, getRequestModel(req));
                    //確認区分取得
                    dspNtpMdl.setNtp_chkKbn(biz.getCheckKbn(ntpMdl.getNipSid(), chkMap));
                    //コメント件数取得
                    dspNtpMdl.setNtp_cmtCnt(biz.existComment(ntpMdl.getNipSid(), cmmMap));
                    dspNtpMdl.setNtp_cmtkbn(biz.getCommentKbn(dspNtpMdl.getNtp_cmtCnt()));
                    //いいね件数取得
                    dspNtpMdl.setNtp_goodCnt(biz.existGood(ntpMdl.getNipSid(), iinMap));
                    dspNtpMdl.setNtp_goodkbn(biz.getCommentKbn(dspNtpMdl.getNtp_goodCnt()));
                    dspNtpMdl.setAnkenViewable(ntpMdl.isAnkenViewable());
                    dayMdlList.add(dspNtpMdl);
                }
            }
            dayMdl.setNtpDataList(dayMdlList);
            colList.add(dayMdl);
            //日付を進める
            frDate.addDay(1);
        }
        monthMdl.setNtp020NtpList(colList);
        rowList.add(monthMdl);

        return monthMdl;
    }
    /**
     * コメントがあるか判定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param nipSid 日報SID
     * @param con コネクション
     * @return int 確認件数
     * @throws SQLException sql実行例外
     */
    public boolean existComment(int nipSid, Connection con) throws SQLException {

        boolean ret = false;
        int cnt = 0;

        NtpCommentDao cmtDao = new NtpCommentDao(con);
        cnt = cmtDao.getNpcCount(nipSid);
        if (cnt > 0) {
            ret = true;
        }

        return ret;
    }


}
