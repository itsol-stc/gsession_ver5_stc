package jp.groupsession.v2.zsk.maingrp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnTdispDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.UserSearchModel;
import jp.groupsession.v2.cmn.model.base.CmnTdispModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchDataPubDao;
import jp.groupsession.v2.sch.dao.ScheduleSearchDao;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchLabelValueModel;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.zsk.GSConstZaiseki;
import jp.groupsession.v2.zsk.dao.ZaiGpriConfDao;
import jp.groupsession.v2.zsk.model.ZaiGpriConfModel;

/**
 * <br>[機  能] 在席管理(メイン画面表示用 メンバー)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ZskMaingrpBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ZskMaingrpBiz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <p>コンストラクタ
     * @param reqMdl RequestModel
     */
    public ZskMaingrpBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * 初期表示画面情報を取得します
     * @param paramMdl ZskMaingrpParamModel
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @param umodel ユーザ基本情報
     * @param appRoot アップルートパス
     * @return ZskMaingrpForm アクションフォーム
     * @throws Exception SQL実行時例外
     */
    public ZskMaingrpParamModel getInitData(ZskMaingrpParamModel paramMdl,
             Connection con, PluginConfig pconfig, BaseUserModel umodel,
             String appRoot) throws Exception {

        log__.debug("-- getInitData START --");

        int userSid = umodel.getUsrsid();
        //グループコンボセット
        SchCommonBiz scBiz = new SchCommonBiz(reqMdl__);
        List<SchLabelValueModel> groupLabel = scBiz.getGroupLabelCombo(
                userSid, con, false);
        paramMdl.setZaiGrpLabelList(groupLabel);

        //メイン画面に表示するグループSIDを取得
        String groupCodeStr = "";
        if (NullDefault.getString(paramMdl.getZaiGrpSid(), "").equals("-1")) {
            ZskMaingrpCommonBiz biz = new ZskMaingrpCommonBiz();
            groupCodeStr = biz.selectGroupSid(userSid, con);
            GroupBiz grpBiz = new GroupBiz();
            paramMdl.setZaiGrpSid(NullDefault.getString(groupCodeStr,
                    String.valueOf(grpBiz.getDefaultGroupSid(userSid, con))));
        } else {
            groupCodeStr = String.valueOf(paramMdl.getZaiGrpSid());
        }

        //グループSIDから所属ユーザ一覧を作成
        ArrayList<Integer> users = new ArrayList<Integer>();
        CmnBelongmDao cmnbDao = new CmnBelongmDao(con);
        if (SchCommonBiz.isMyGroupSid(groupCodeStr)) {
            //マイグループから作成
            CmnMyGroupMsDao mgmsDao = new CmnMyGroupMsDao(con);
            users = mgmsDao.selectMyGroupUsers(userSid,
                                               SchCommonBiz.getDspGroupSid(groupCodeStr));
        } else {
            //通常グループから作成
            users = cmnbDao.selectBelongUserSid(SchCommonBiz.getDspGroupSid(groupCodeStr));
        }
        //ショートメールプラグインを使用していないユーザを除外する。
        //送信制限されているユーザを除外する。
        CommonBiz cmnBiz = new CommonBiz();
        List<Integer> smlUsrs = (ArrayList<Integer>) cmnBiz.getCanUseSmailUser(con, users);
        SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl__);
        smlUsrs = smlCommonBiz.getValiableDestUsrSid(con, userSid, smlUsrs);


        //グループ表示情報を設定する。
        ZaiGpriConfDao gpcDao = new ZaiGpriConfDao(con);
        ZaiGpriConfModel gpcMdl = gpcDao.select(userSid);
        int sortName1 = GSConstUser.USER_SORT_NAME;
        int sortName2 = GSConstUser.USER_SORT_NAME;
        int orderKey1 = GSConst.ORDER_KEY_ASC;
        int orderKey2 = GSConst.ORDER_KEY_ASC;
        int schDsp = GSConstZaiseki.MAIN_SCH_DSP;
        if (gpcMdl != null) {
            sortName1 = gpcMdl.getZgcSortKey1();
            sortName2 = gpcMdl.getZgcSortKey2();
            orderKey1 = gpcMdl.getZgcSortOrder1();
            orderKey2 = gpcMdl.getZgcSortOrder2();
            schDsp = gpcMdl.getZgcSchViewDf();
        }

        //ユーザ情報一覧を作成
        UserSearchDao uDao = new UserSearchDao(con);
        ArrayList<UserSearchModel> uList = uDao.getUsersInfoJtkb(
                users,
                sortName1,
                orderKey1,
                sortName2,
                orderKey2);

        SchDao schDao = new SchDao(con);
        for (UserSearchModel um:uList) {
            //ショートメール有効無効設定
            if (!smlUsrs.contains(um.getUsrSid())) {
                um.setSmlAble(0);
            } else {
                um.setSmlAble(1);
            }

            //指定ユーザのスケジュールにアクセス可能か設定する
            if (schDao.canAccessUserSchedule(um.getUsrSid(), userSid)) {
                um.setSchAccessFlg(GSConstSchedule.SCH_ACCESS_YES);
            } else {
                um.setSchAccessFlg(GSConstSchedule.SCH_ACCESS_NO);
            }

            // 指定ユーザのスケジュールに追加変更可能か設定する
            um.setSchRegistFlg(
                    schDao.canRegistUserSchedule(um.getUsrSid(), userSid));
        }

        //スケジュール表示フラグ デフォルト値
        paramMdl.setZaiSchViewDf(schDsp);
        paramMdl.setUserList(uList);

        if (paramMdl.getSchUseOk() == GSConstSchedule.PLUGIN_NOT_USE) {
            return paramMdl;
        }

        //スケジュール取得範囲整形
        UDate nowDate = new UDate();
        UDate fromDate = nowDate.cloneUDate();
        fromDate.setHour(GSConstSchedule.DAY_START_HOUR);
        fromDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        fromDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        fromDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);
        UDate toDate = nowDate.cloneUDate();
        toDate.setHour(GSConstSchedule.DAY_END_HOUR);
        toDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        toDate.setSecond(GSConstSchedule.DAY_END_SECOND);
        toDate.setMilliSecond(GSConstSchedule.DAY_END_MILLISECOND);

        //スケジュール情報一覧を作成
        ScheduleSearchDao schSearchDao = new ScheduleSearchDao(con);
        ArrayList<SchDataModel> schDataList = schSearchDao.selectUsers(
                uList,
                GSConstSchedule.USER_KBN_USER,
                GSConstSchedule.DSP_ALL,
                fromDate,
                toDate,
                GSConstSchedule.DSP_MOD_DAY);

        //スケジュール情報一覧(表示用)を作成
        ArrayList<ZskMaingrpModel> zskDataList = new ArrayList<ZskMaingrpModel>();
        boolean grpBelongHnt = false;
        boolean publicUserMatch = false;

        for (SchDataModel schMdl : schDataList) {
            ZskMaingrpModel zskMdl = new ZskMaingrpModel();
            //スケジュールSID
            zskMdl.setScdSid(schMdl.getScdSid());
            //ユーザSID
            zskMdl.setScdUsrSid(schMdl.getScdUsrSid());

            GsMessage gsMsg = new GsMessage(reqMdl__);
            String message = gsMsg.getMessage("schedule.7");

            //開始～終了
            if (schMdl.getScdDaily() == GSConstSchedule.TIME_EXIST) {
                zskMdl.setScdFrToDateDsp(__createFromTo(schMdl.getScdFrDate(),
                                        schMdl.getScdToDate()));
            } else {
                zskMdl.setScdFrToDateDsp(message);
            }
            zskMdl.setScdPublic(schMdl.getScdPublic());

            int publicKbn = schMdl.getScdPublic();


            switch (publicKbn) {
            case GSConstSchedule.DSP_PUBLIC:
                //タイトル
                zskMdl.setScdTitle(schMdl.getScdTitle());
                //背景色区分
                zskMdl.setScdBgcolor(schMdl.getScdBgcolor());
                //内容
                zskMdl.setScdValue(schMdl.getScdValue());
                zskDataList.add(zskMdl);
                break;

            case GSConstSchedule.DSP_NOT_PUBLIC:
                if (schMdl.getScdUsrSid() == userSid
                || schMdl.getScdAuid() == userSid
                || schMdl.getScdEuid() == userSid) {
                    //タイトル
                    zskMdl.setScdTitle(schMdl.getScdTitle());
                    //背景色区分
                    zskMdl.setScdBgcolor(schMdl.getScdBgcolor());
                    //内容
                    zskMdl.setScdValue(schMdl.getScdValue());
                    zskMdl.setScdPublic(GSConstSchedule.DSP_PUBLIC);
                    zskDataList.add(zskMdl);
                }
                break;

            case GSConstSchedule.DSP_YOTEIARI:
                if (schMdl.getScdUsrSid() == userSid
                        || schMdl.getScdAuid() == userSid
                        || schMdl.getScdEuid() == userSid) {
                    //タイトル
                    zskMdl.setScdTitle(schMdl.getScdTitle());
                    //背景色区分
                    zskMdl.setScdBgcolor(schMdl.getScdBgcolor());
                    //内容
                    zskMdl.setScdValue(schMdl.getScdValue());
                    zskMdl.setScdPublic(GSConstSchedule.DSP_PUBLIC);
                } else {
                    //タイトル
                    zskMdl.setScdTitle(gsMsg.getMessage("schedule.src.9"));
                    //背景色区分
                    zskMdl.setScdBgcolor(5);
                    //内容
                    zskMdl.setScdValue("");
                }
                zskDataList.add(zskMdl);
                break;

            case GSConstSchedule.DSP_BELONG_GROUP:
                //セッションユーザがスケジュールユーザと同じグループに所属しているかを判定
                ArrayList<Integer> belongGrpList = new ArrayList<Integer>();
                belongGrpList = cmnbDao.selectUserBelongGroupSid(userSid);
                grpBelongHnt = getUsrBelongHnt(
                        schMdl.getScdUserBlongGpList(), belongGrpList);
                if (grpBelongHnt
                || schMdl.getScdAuid() == userSid
                || schMdl.getScdEuid() == userSid) {
                    //タイトル
                    zskMdl.setScdTitle(schMdl.getScdTitle());
                    //背景色区分
                    zskMdl.setScdBgcolor(schMdl.getScdBgcolor());
                    //内容
                    zskMdl.setScdValue(schMdl.getScdValue());
                    zskMdl.setScdPublic(GSConstSchedule.DSP_PUBLIC);
                    zskDataList.add(zskMdl);
                } else {
                    //タイトル
                    zskMdl.setScdTitle(gsMsg.getMessage("schedule.src.9"));
                    //背景色区分
                    zskMdl.setScdBgcolor(5);
                    //内容
                    zskMdl.setScdValue("");
                    zskMdl.setNotBelongGrpFlg(true);
                    zskDataList.add(zskMdl);
                }
                break;

            case GSConstSchedule.DSP_USRGRP:
                SchDataPubDao scdDao = new SchDataPubDao(con);
                boolean result = scdDao.select(zskMdl.getScdSid(), userSid);
                if (schMdl.getScdUsrSid() == userSid
                || result
                || schMdl.getScdAuid() == userSid
                || schMdl.getScdEuid() == userSid) {
                    //タイトル
                    zskMdl.setScdTitle(schMdl.getScdTitle());
                    //背景色区分
                    zskMdl.setScdBgcolor(schMdl.getScdBgcolor());
                    //内容
                    zskMdl.setScdValue(schMdl.getScdValue());
                    zskMdl.setScdPublic(GSConstSchedule.DSP_PUBLIC);
                    zskDataList.add(zskMdl);
                    publicUserMatch = true;
                } else {
                    //タイトル
                    zskMdl.setScdTitle(gsMsg.getMessage("schedule.src.9"));
                    //背景色区分
                    zskMdl.setScdBgcolor(5);
                    //内容
                    zskMdl.setScdValue("");
                    zskMdl.setScdPublic(GSConstSchedule.DSP_YOTEIARI);
                    zskMdl.setNotSelectUsrGrpFlg(true);
                    zskDataList.add(zskMdl);
                }
                break;

            case GSConstSchedule.DSP_TITLE:
                if (schMdl.getScdUsrSid() == userSid
                || schMdl.getScdAuid() == userSid
                || schMdl.getScdEuid() == userSid) {
                    //タイトル
                    zskMdl.setScdTitle(schMdl.getScdTitle());
                    //背景色区分
                    zskMdl.setScdBgcolor(schMdl.getScdBgcolor());
                    //内容
                    zskMdl.setScdValue(schMdl.getScdValue());
                    zskMdl.setScdPublic(GSConstSchedule.DSP_PUBLIC);
                    zskDataList.add(zskMdl);
                } else {
                    //タイトル
                    zskMdl.setScdTitle(schMdl.getScdTitle());
                    //背景色区分
                    zskMdl.setScdBgcolor(5);
                    //内容
                    zskMdl.setScdValue("");
                    zskDataList.add(zskMdl);
                }
                break;

            default:
                break;
            }

            zskMdl.setKeyFlg(
                    scBiz.getPublicIconFlg(schMdl, userSid, grpBelongHnt, publicUserMatch));

            publicUserMatch = false;
        }

        paramMdl.setDspUserSid(userSid);
        paramMdl.setScheduleList(zskDataList);

        return paramMdl;
    }

    /**
     * <br>[機  能] スケジュール開始～終了範囲を生成
     * <br>[解  説]
     * <br>[備  考] システム日付内の時は時間範囲、以外は日付範囲
     * @param frDate 開始
     * @param toDate 終了
     * @return 範囲
     */
    private static String __createFromTo(UDate frDate, UDate toDate) {

        UDate nowDate = new UDate();
        StringBuilder viewDate = new StringBuilder("");

        boolean retFr = false;
        boolean retTo = false;

        retFr = nowDate.equalsDate(frDate);
        retTo = nowDate.equalsDate(toDate);

        if (retFr == true && retTo == true) {
            //時間範囲
            viewDate.append(frDate.getStrHour());
            viewDate.append(":");
            viewDate.append(frDate.getStrMinute());
            viewDate.append("-");
            viewDate.append(toDate.getStrHour());
            viewDate.append(":");
            viewDate.append(toDate.getStrMinute());
        } else {
            //日付範囲
            viewDate.append(frDate.getStrMonth());
            viewDate.append("/");
            viewDate.append(frDate.getStrDay());
            viewDate.append("-");
            viewDate.append(toDate.getStrMonth());
            viewDate.append("/");
            viewDate.append(toDate.getStrDay());
        }

        return viewDate.toString();
    }

    /**
     * <br>[機  能] セッションユーザがスケジュールユーザと同じグループに所属しているか判定
     * <br>[解  説]
     * <br>[備  考]
     * @param belongSids スケジュールユーザ所属グループSID
     * @param belongSessionUsrSids セッションユーザ所属グループSID
     * @return 所属フラグ
     */
    public boolean getUsrBelongHnt(
            ArrayList<Integer> belongSids, ArrayList<Integer> belongSessionUsrSids) {
        boolean belongFlg = false;
        if (belongSids != null && !belongSids.isEmpty()) {
            for (int gpSid : belongSids) {
                if (belongSessionUsrSids != null) {
                    if (belongSessionUsrSids.indexOf(gpSid) > -1) {
                        belongFlg = true;
                    }
                }
            }
        }
        return belongFlg;
    }

    /**
     * <br>[機  能] メイン画面表示の有無を判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl ZskMaingrpParamModel
     * @param con コネクション
     * @param sessionUserSid ユーザSID
     * @return dspFlg true:表示、false:非表示
     * @throws SQLException SQL実行時例外
     */
    public boolean isMainGrpDsp(ZskMaingrpParamModel paramMdl, Connection con, int sessionUserSid)
    throws SQLException {

        boolean dspFlg = false;

        ZaiGpriConfDao dao = new ZaiGpriConfDao(con);
        ZaiGpriConfModel model = dao.select(sessionUserSid);
        if (model == null) {
            dspFlg = true;
            paramMdl.setZskGrpDspFlg(GSConstZaiseki.MAINGRP_DSP);
        } else if (model.getZgcViewKbn() == GSConstZaiseki.MAINGRP_DSP) {
            dspFlg = true;
            paramMdl.setZskGrpDspFlg(GSConstZaiseki.MAINGRP_DSP);
        }

        return dspFlg;
    }

    /**
     * <br>[機  能] プラグインがメニュー表示項目か判定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl ZskMaingrpParamModel
     * @param con コネクション
     * @param sessionUserSid ユーザSID
     * @param pluginId プラグインID
     * @return dspFlg true:メニュー表示項目、false:メニュー非表示項目
     * @throws SQLException SQL実行時例外
     */
    public boolean isDspMenuPlugin(ZskMaingrpParamModel paramMdl, Connection con,
            int sessionUserSid, String pluginId)
    throws SQLException {

        CmnTdispDao tdispDao = new CmnTdispDao(con);
        CmnTdispModel tdispModel = new CmnTdispModel();
        tdispModel.setUsrSid(sessionUserSid);
        tdispModel.setTdpPid(pluginId);
        CmnTdispModel ret = tdispDao.select(tdispModel);

        if (ret != null) {
            return true;
        }

        return false;
    }
}
