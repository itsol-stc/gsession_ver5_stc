package jp.groupsession.v2.api.ntp.nippou.weekly;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.biz.NtpCommonBiz;
import jp.groupsession.v2.ntp.dao.NippouDao;
import jp.groupsession.v2.ntp.model.NtpPriConfModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010Biz;
import jp.groupsession.v2.ntp.ntp010.Ntp010DayOfModel;
import jp.groupsession.v2.ntp.ntp010.Ntp010WeekOfModel;
import jp.groupsession.v2.ntp.ntp010.SimpleNippouModel;
import jp.groupsession.v2.usr.GSConstUser;
/**
 * <br>[機  能] WEBAPI 日報週間データ取得アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiNippouWeeklyAction extends AbstractApiAction {
    /** ログ*/
    private static Log log__ = LogFactory.getLog(new Throwable()
        .getStackTrace()[0].getClassName());
    @Override
    public Document createXml(ActionForm form, HttpServletRequest req,
            HttpServletResponse res, Connection con, BaseUserModel umodel)
            throws Exception {


        log__.debug("createXml start");
        Element resultSet = new Element("ResultSet");
        Document doc = new Document(resultSet);

        //日報プラグインアクセス権限確認
        if (!canAccsessSelectPlugin(GSConst.PLUGIN_ID_NIPPOU, req)) {
            addErrors(req, addCantAccsessPluginError(req, null, GSConst.PLUGIN_ID_NIPPOU));
            return null;
        }
        ApiNippouWeeklyForm thisForm = (ApiNippouWeeklyForm) form;
        ActionErrors err = thisForm.validateCheck(con, req);
        if (!err.isEmpty()) {
            addErrors(req, err);
            return null;
        }


        UDate dspDate = new UDate();
        dspDate.setZeroHhMmSs();

        if (!StringUtil.isNullZeroString(thisForm.getBaseDay())) {
            dspDate.setDate(Integer.parseInt(thisForm.getBaseDay().substring(0, 4)),
                    Integer.parseInt(thisForm.getBaseDay().substring(5, 7)),
                    Integer.parseInt(thisForm.getBaseDay().substring(8, 10)));

        }
        int sessionUsrSid = umodel.getUsrsid(); //セッションユーザSID

        //個人設定取得&作成
        NtpPriConfModel confMdl = null;

        NtpCommonBiz cbiz = new NtpCommonBiz(con, getRequestModel(req));
        confMdl = cbiz.getNtpPriConfModel(con, sessionUsrSid);

        //リクエストパラメータを取得
        //表示開始日

        //表示項目取得

        if (confMdl.getNprDspPosition() == GSConstNippou.DAY_POSITION_RIGHT) {
            //選択日付を右端へ移動
            dspDate.addDay(GSConstNippou.DAY_POSITION_RIGHT_PARAM);
        }

        //デフォルト表示グループ
        NtpCommonBiz sBiz = new NtpCommonBiz(con, getRequestModel(req));

        String dfGpSidStr = sBiz.getDispDefaultGroupSidStr(con, sessionUsrSid);
        int dfGpSid = NtpCommonBiz.getDspGroupSid(dfGpSidStr);
        boolean myGroupFlg = false;
        String gpSidStr = NullDefault.getString(thisForm.getGrpSid(), dfGpSidStr);
        int gpSid = 0;
        if (NtpCommonBiz.isMyGroupSid(gpSidStr)) {
            myGroupFlg = true;
        }
        gpSid = NtpCommonBiz.getDspGroupSid(gpSidStr);
        //休日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        UDate toDate = dspDate.cloneUDate();
        toDate.addDay(7);
        HashMap < String, CmnHolidayModel > holMap = holDao.getHoliDayList(dspDate, toDate);
        CmnHolidayModel holMdl = null;

        log__.debug("getList start");

        ArrayList<Ntp010WeekOfModel> topList =
                __getWeekNippouList(dspDate.cloneUDate(), gpSid, sessionUsrSid,
                        (!thisForm.getSelfDataOnTop().equals("0")), myGroupFlg, con, req);

        log__.debug("getList end");

        resultSet.setAttribute("TotalCount", Integer.toString(topList.size()));

        resultSet.setAttribute("grpSid", Integer.toString(gpSid));
        GroupBiz gbiz = new GroupBiz();
        log__.debug("getGroup start");

        List<GroupModel> list = gbiz.getGroupList(con, sessionUsrSid);
        for (GroupModel grp:list) {
            if (grp.getGroupSid() == dfGpSid) {
                resultSet.setAttribute("grpName", grp.getGroupName());
            }
        }
        log__.debug("getGroup end");

        String slsDateStr = UDateUtil.getSlashYYMD(dspDate);

        resultSet.setAttribute("startDay", slsDateStr);
        resultSet.setAttribute("TotalCount", Integer.toString(topList.size()));


        for (Ntp010WeekOfModel ntp010WeekOfModel__ : topList) {
            Element result = new Element("Result");
            resultSet.addContent(result);
            result.addContent(_createElement("UsrSid",
                    ntp010WeekOfModel__.getNtp010UsrMdl().getUsrSid()));
            result.addContent(_createElement("UsrName",
                    ntp010WeekOfModel__.getNtp010UsrMdl().getUsrName()));
            result.addContent(_createElement("UsrUkoFlg",
                    ntp010WeekOfModel__.getNtp010UsrMdl().getUsrUkoFlg()));
            Element week = new Element("WeekSet");
            result.addContent(week);
            for (Ntp010DayOfModel ntp010DayOfModel__
                    : ntp010WeekOfModel__.getNtp010NtpList()) {
                Element day = new Element("Week");
                week.addContent(day);
                String dspDateStr = ntp010DayOfModel__.getNtpDate();

                // 休日名称
                holMdl = holMap.get(dspDateStr);
                if (holMdl != null) {
                    day.addContent(_createElement("HolidayKbn", "1"));
                } else {
                    day.addContent(_createElement("HolidayKbn", "0"));
                }

                day.addContent(_createElement("WeekKbn", ntp010DayOfModel__.getWeekKbn()));
                slsDateStr = dspDateStr.substring(0, 4)
                        + "/" + dspDateStr.substring(4, 6)
                        + "/" + dspDateStr.substring(6, 8);

                day.addContent(_createElement("NipDate", slsDateStr));
                Element reportSet = new Element("ReportSet");
                day.addContent(reportSet);
                reportSet.setAttribute("TotalCount",
                        Integer.toString(ntp010DayOfModel__.getNtpDataList().size()));
                for (SimpleNippouModel simpleNippouModel__: ntp010DayOfModel__.getNtpDataList()) {
                    Element report = new Element("Report");
                    reportSet.addContent(report);
                    report.addContent(_createElement("NipSid", simpleNippouModel__.getNtpSid()));
                    report.addContent(_createElement("NipTime", simpleNippouModel__.getTime()));
                    report.addContent(_createElement("NipFrTime",
                            simpleNippouModel__.getFromDateStr()));
                    report.addContent(_createElement("NipToTime",
                            simpleNippouModel__.getToDateStr()));
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
        }

        log__.debug("createXml end");
        return doc;

    }
    /**
     * <br>表示グループに所属するユーザの日報情報を取得します
     * @param dspDate 開始日付
     * @param gpSid 表示グループSID
     * @param usrSid セッションユーザSID
     * @param selfDataOnTop 自身を最上位に追加するかどうか
     * @param myGroupFlg 自身のグループか否か
     * @param con コネクション
     * @param req リクエスト
     * @return ArrayList 役職>姓カナ>名カナの順に格納
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<Ntp010WeekOfModel> __getWeekNippouList(
            UDate dspDate,
            int gpSid,
            int usrSid,
            boolean selfDataOnTop,
            boolean myGroupFlg,
            Connection con,
            HttpServletRequest req) throws SQLException {

        //所属ユーザを取得
        UserSearchDao usDao = new UserSearchDao(con);

        //除外するユーザSIDを設定
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        usrSids.add(new Integer(GSConstUser.SID_ADMIN));
        usrSids.add(new Integer(GSConstUser.SID_SYSTEM_MAIL));

        if (selfDataOnTop) {
            usrSids.add(new Integer(usrSid)); //本人も表示しない
        }
        //日報個人設定で取得した表示順を取得する。
        NtpCommonBiz sBiz = new NtpCommonBiz(con, getRequestModel(req));
        NtpPriConfModel pconf = sBiz.getNtpPriConfModel(con, usrSid);
        int sortKey1 = pconf.getNprSortKey1();
        int orderKey1 = pconf.getNprSortOrder1();
        int sortKey2 = pconf.getNprSortKey2();
        int orderKey2 = pconf.getNprSortOrder2();

        //表示するグループメンバーを取得
        ArrayList<UserSearchModel> belongList = null;
        NippouDao ntpDao = new NippouDao(con);
        List<Integer> notAccessUserList = null;
        if (!myGroupFlg) {
            belongList = usDao.getBelongUserInfoJtkb(gpSid,
                    usrSids, sortKey1, orderKey1, sortKey2, orderKey2);
            notAccessUserList = ntpDao.getSpAccessUserList(gpSid, usrSid, false, -1);
        } else {
            belongList = usDao.getMyGroupBelongUserInfoJtkb(gpSid, usrSid,
                    usrSids, sortKey1, orderKey1, sortKey2, orderKey2);
            notAccessUserList = ntpDao.getSpAccessUserList(-1, usrSid, false, -1);
            ArrayList<Integer> usrSidArr = new ArrayList<Integer>();
            usrSidArr.add(new Integer(usrSid));
            //自分のスケジュールを取得
            belongList.addAll(0,
                    usDao.getUsersInfoJtkb(usrSidArr, sortKey1, orderKey1, sortKey2, orderKey2));
        }

        ArrayList<UserSearchModel> delList = new ArrayList<UserSearchModel>();
        for (UserSearchModel userData:belongList) {
            if (notAccessUserList.indexOf(userData.getUsrSid()) >= 0) {
                delList.add(userData);
            }
        }
        for (UserSearchModel delUser:delList) {
            belongList.remove(delUser);
        }

        if (selfDataOnTop) {
            UserSearchModel usrInfMdl = usDao.getUserInfoJtkb(
                    usrSid, GSConstUser.USER_JTKBN_ACTIVE);
            belongList.add(0, usrInfMdl);
        }
        //一括で生成する様に変更
        Ntp010Biz biz = new Ntp010Biz(con, getRequestModel(req));
        ArrayList<Ntp010WeekOfModel> rowList = biz.getWeekUserNippouNew(
                belongList, dspDate.cloneUDate(), con, usrSid);
        return rowList;
    }
}
