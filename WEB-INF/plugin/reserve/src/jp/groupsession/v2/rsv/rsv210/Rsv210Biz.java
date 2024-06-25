package jp.groupsession.v2.rsv.rsv210;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rsv.AbstractReserveBiz;
import jp.groupsession.v2.rsv.biz.IRsvYoyakuRegister;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.biz.RsvScheduleBiz;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.dao.RsvInitPubDao;
import jp.groupsession.v2.rsv.dao.RsvScdOperationDao;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvUserDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.model.RsvDataPubModel;
import jp.groupsession.v2.rsv.model.RsvHidDayModel;
import jp.groupsession.v2.rsv.model.RsvHidGroupModel;
import jp.groupsession.v2.rsv.model.RsvHidModel;
import jp.groupsession.v2.rsv.model.RsvHidSisetuModel;
import jp.groupsession.v2.rsv.model.RsvInitPubModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.model.RsvUserModel;
import jp.groupsession.v2.rsv.model.other.ExtendedLabelValueModel;
import jp.groupsession.v2.rsv.model.other.RsvSchAdmConfModel;
import jp.groupsession.v2.rsv.model.other.RsvSchDataPubModel;
import jp.groupsession.v2.rsv.model.other.ScheduleRsvModel;
import jp.groupsession.v2.sch.biz.ISchRegister;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchDataPubModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 施設予約 施設予約一括登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
  *
 * @author JTS
*/
public class Rsv210Biz extends AbstractReserveBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rsv210Biz.class);
    /** リクエスト情報 */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    protected Connection con_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param con コネクション
     */
    public Rsv210Biz(RequestModel reqMdl, Connection con) {
        reqMdl_ = reqMdl;
        con_ = con;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv210ParamModel
     * @param pconfig プラグイン情報
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public void setInitData(Rsv210ParamModel paramMdl, PluginConfig pconfig)
            throws SQLException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        //登録者名をセット
        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        paramMdl.setRsv210Torokusya(usrMdl.getUsisei() + "  " + usrMdl.getUsimei());

        //時間表示間隔を設定
        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        paramMdl.setRsv210HourDiv(rsvBiz.getHourDivision(con_));

        RsvCommonBiz biz = new RsvCommonBiz();
        //管理者設定の取得
        RsvAdmConfDao confDao = new RsvAdmConfDao(con_);
        RsvAdmConfModel amdConfMdl = confDao.select();
        if (amdConfMdl == null) {
            amdConfMdl = biz.setInitAdminnConfModel();
        }
        // 個人設定の取得
        int usrSid = usrMdl.getUsrsid();
        RsvUserDao uConfDao = new RsvUserDao(con_);
        RsvUserModel uConfMdl = uConfDao.select(usrSid);
        if (uConfMdl == null) {
            uConfMdl = biz.getUnconfInitModel();
        }

        //午前
        paramMdl.setRsv210AmFrHour(amdConfMdl.getRacAmTimeFrH());
        paramMdl.setRsv210AmFrMin(amdConfMdl.getRacAmTimeFrM());
        paramMdl.setRsv210AmToHour(amdConfMdl.getRacAmTimeToH());
        paramMdl.setRsv210AmToMin(amdConfMdl.getRacAmTimeToM());

        //午後
        paramMdl.setRsv210PmFrHour(amdConfMdl.getRacPmTimeFrH());
        paramMdl.setRsv210PmFrMin(amdConfMdl.getRacPmTimeFrM());
        paramMdl.setRsv210PmToHour(amdConfMdl.getRacPmTimeToH());
        paramMdl.setRsv210PmToMin(amdConfMdl.getRacPmTimeToM());

        //終日
        paramMdl.setRsv210AllDayFrHour(amdConfMdl.getRacAllDayTimeFrH());
        paramMdl.setRsv210AllDayFrMin(amdConfMdl.getRacAllDayTimeFrM());
        paramMdl.setRsv210AllDayToHour(amdConfMdl.getRacAllDayTimeToH());
        paramMdl.setRsv210AllDayToMin(amdConfMdl.getRacAllDayTimeToM());

        //初期表示時はコンボ選択値にデフォルト値を設定
        if (paramMdl.isRsv210InitFlg()) {
            __setDefHourMin(paramMdl, amdConfMdl, uConfMdl, usrSid);
        }
        GsMessage gsMsg = new GsMessage();
        DateTimePickerBiz picker = new DateTimePickerBiz();
        String timeNameJp = gsMsg.getMessage("reserve.159");

        //日付時間設定
        if (paramMdl.getRsv210TimeFr() == null) {
            picker.setTimeParam(
                    paramMdl,
                    "rsv210TimeFr",
                    "rsv210SelectedHourFr",
                    "rsv210SelectedMinuteFr",
                    timeNameJp);
        }
        
        if (paramMdl.getRsv210TimeTo() == null) {
            picker.setTimeParam(
                    paramMdl,
                    "rsv210TimeTo",
                    "rsv210SelectedHourTo",
                    "rsv210SelectedMinuteTo",
                    timeNameJp);
        }

        //使用者 グループコンボ、ユーザコンボを設定
        _setUserCombo(con_, paramMdl, reqMdl_);

        //登録対象の施設情報を取得
        __setTargetSisetuList(paramMdl);

        //スケジュール登録情報を取得
        __setUserList(paramMdl);

        paramMdl.setRsv210InitFlg(false);

        //スケジュール使用有無
        if (pconfig.getPlugin(GSConstReserve.PLUGIN_ID_SCHEDULE) != null) {
            paramMdl.setSchedulePluginKbn(GSConst.PLUGIN_USE);

            //スケジュール管理者設定 共有範囲を取得する
            SchDao schDao = new SchDao(con_);
            paramMdl.setRsv210SchCrangeKbn(schDao.getSadCrange());

            //閲覧不可のグループ、ユーザを設定
            int sessionUserSid = reqMdl_.getSmodel().getUsrsid();
            paramMdl.setRsv210SchNotAccessGroupList(schDao.getNotRegistGrpList(sessionUserSid));
        } else {
            paramMdl.setSchedulePluginKbn(GSConst.PLUGIN_NOT_USE);
        }

    }

    /**
     * <br>[機  能] 選択施設情報リストをセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv210ParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __setTargetSisetuList(Rsv210ParamModel paramMdl) throws SQLException {

        ArrayList<String> hiddArray = new ArrayList<String>();
        String[] hiddStr = paramMdl.getRsvIkkatuTorokuKey();
        if (hiddStr != null && hiddStr.length > 0) {
            for (String key : hiddStr) {
                hiddArray.add(key);
            }
        }

        Collections.sort(hiddArray);
        String saveDay = null;
        String saveHiddDayKey = null;
        RsvSisDataDao dao = new RsvSisDataDao(con_);
        ArrayList<String> searchArray = new ArrayList<String>();
        ArrayList<RsvHidDayModel> hiddList = new ArrayList<RsvHidDayModel>();

        //画面に表示しきれていないキーがあれば処理
        String yrkFrom = null;
        if (!hiddArray.isEmpty()) {

            for (String hiddKey : hiddArray) {

                //キーから日付部分を取得
                String hiddDayKey = hiddKey.substring(0, hiddKey.indexOf("-"));

                if (saveDay == null) {
                    saveDay = hiddDayKey;
                    saveHiddDayKey = hiddDayKey;
                    yrkFrom = hiddDayKey;
                } else if (!saveDay.equals(hiddDayKey)) {

                    ArrayList<RsvHidModel> hiddDayList = dao.selectHidSisetuList(searchArray);
                    hiddList.add(__getDaylySisetuList(hiddDayList, saveDay, saveHiddDayKey));

                    //配列とキーを初期化
                    searchArray = new ArrayList<String>();
                    saveDay = hiddDayKey;
                    saveHiddDayKey = hiddDayKey;

                    //一括予約開始日付を取得
                    if (__formatYrkDayStr(yrkFrom) > __formatYrkDayStr(hiddDayKey)) {
                        yrkFrom = hiddDayKey;
                    }
                }

                //キーの施設SID部分を追加
                String hiddSidKey = hiddKey.substring(hiddKey.indexOf("-") + 1);
                searchArray.add(hiddSidKey);
            }

            if (!searchArray.isEmpty()) {
                //リスト末尾
                ArrayList<RsvHidModel> hiddDayList = dao.selectHidSisetuList(searchArray);
                hiddList.add(__getDaylySisetuList(hiddDayList, saveDay, saveHiddDayKey));
            }
        }

        //一括予約開始日付を設定
        if (yrkFrom != null) {
            paramMdl.setRsv210YrkFrom(yrkFrom);
        } else {
            paramMdl.setRsv210YrkFrom(paramMdl.getRsvDspFrom());
        }

        paramMdl.setRsvIkkatuTorokuHiddenList(hiddList);
    }

    /**
     * <br>[機  能] DB取得結果を画面表示用に変換する
     * <br>[解  説]
     * <br>
     * <br>   施設グループ    施設
     * <br>       A            1
     * <br>       A            2
     * <br>       A            3
     * <br>       B            4
     * <br>       B            5
     * <br>       B            6
     * <br>
     * <br>   DBから上記の形式で取得したリストを
     * <br>
     * <br>   施設グループ    施設
     * <br>       A            1 2 3
     * <br>       B            4 5 6
     * <br>
     * <br>   の形式へ変換する
     * <br>
     * <br>[備  考]
     *
     * @param hiddDayList DB取得結果リスト
     * @param saveDay キー
     * @param saveHiddDayKey 日付文字列yyyyMMdd
     * @return ret 変換後モデル
     */
    private RsvHidDayModel __getDaylySisetuList(ArrayList<RsvHidModel> hiddDayList,
                                                 String saveDay,
                                                 String saveHiddDayKey) {

        //画面表示用に変換
        int saveRsgSid = -1;
        String saveRsgName = null;
        ArrayList<RsvHidSisetuModel> hidSisetuList =
            new ArrayList<RsvHidSisetuModel>();
        ArrayList<RsvHidGroupModel> hidGroupList =
            new ArrayList<RsvHidGroupModel>();

        RsvHidDayModel day = new RsvHidDayModel();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        UDate udDay = new UDate();
        udDay.setDate(saveDay);
        day.setHidDayStr(
                gsMsg.getMessage("cmn.year", new String[] {udDay.getStrYear()})
                + udDay.getStrMonth()
                + gsMsg.getMessage("cmn.month")
                + udDay.getStrDay()
                + gsMsg.getMessage("cmn.day") + "（"
                + UDateUtil.getStrWeek(udDay.getWeek(), reqMdl_)
                + "）");

        for (RsvHidModel dbMdl : hiddDayList) {

            if (saveRsgSid == -1) {
                saveRsgSid = dbMdl.getRsgSid();
                saveRsgName = dbMdl.getRsgName();

                RsvHidSisetuModel sisetu = new RsvHidSisetuModel();
                sisetu.setRsdSid(dbMdl.getRsdSid());
                sisetu.setRsdName(dbMdl.getRsdName());
                sisetu.setRsvIkkatuTorokuKey(saveHiddDayKey + "-" + dbMdl.getRsdSid());
                hidSisetuList.add(sisetu);
                continue;

            //同じグループに所属する施設をまとめる
            } else if (saveRsgSid == dbMdl.getRsgSid()) {

                RsvHidSisetuModel sisetu = new RsvHidSisetuModel();
                sisetu.setRsdSid(dbMdl.getRsdSid());
                sisetu.setRsdName(dbMdl.getRsdName());
                sisetu.setRsvIkkatuTorokuKey(saveHiddDayKey + "-" + dbMdl.getRsdSid());
                hidSisetuList.add(sisetu);
                continue;

            //グループブレイク
            } else if (saveRsgSid != dbMdl.getRsgSid()) {

                RsvHidGroupModel group = new RsvHidGroupModel();
                group.setRsgName(saveRsgName);
                group.setSisetuList(hidSisetuList);
                hidGroupList.add(group);

                saveRsgSid = dbMdl.getRsgSid();
                saveRsgName = dbMdl.getRsgName();

                hidSisetuList = new ArrayList<RsvHidSisetuModel>();
                RsvHidSisetuModel sisetu = new RsvHidSisetuModel();
                sisetu.setRsdSid(dbMdl.getRsdSid());
                sisetu.setRsdName(dbMdl.getRsdName());
                sisetu.setRsvIkkatuTorokuKey(saveHiddDayKey + "-" + dbMdl.getRsdSid());
                hidSisetuList.add(sisetu);
            }

            day.setGrpList(hidGroupList);
        }
        RsvHidGroupModel group = new RsvHidGroupModel();
        group.setRsgName(saveRsgName);
        group.setSisetuList(hidSisetuList);
        hidGroupList.add(group);
        day.setGrpList(hidGroupList);

        return day;
    }

    /**
     * <br>[機  能] 期間の時・分・編集権限・公開区分の初期値を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rsv210ParamModel
     * @param admConf 管理者設定
     * @param uConf 個人設定
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    private void __setDefHourMin(Rsv210ParamModel paramMdl, RsvAdmConfModel admConf,
            RsvUserModel uConf, int usrSid) throws SQLException {
        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        // 開始期間
        UDate frDate = rsvBiz.getFrDateAuth(admConf, uConf);
        paramMdl.setRsv210SelectedHourFr(frDate.getIntHour());
        paramMdl.setRsv210SelectedMinuteFr(frDate.getIntMinute());
        // 終了期間
        UDate toDate = rsvBiz.getToDateAuth(admConf, uConf);
        paramMdl.setRsv210SelectedHourTo(toDate.getIntHour());
        paramMdl.setRsv210SelectedMinuteTo(toDate.getIntMinute());
        //編集権限の初期値を設定
        paramMdl.setRsv210RsyEdit(rsvBiz.getInitEditAuth(admConf, uConf));
        //公開区分の初期値を設定
        paramMdl.setRsv210RsyPublic(rsvBiz.getInitPublicAuth(admConf, uConf));

        if (paramMdl.getRsv210RsyPublic() == GSConstReserve.PUBLIC_KBN_USRGRP) {
            int userSid = GSConst.SYSTEM_USER_ADMIN;
            if (admConf.getRacIniPublicKbn() == GSConstReserve.AUTH_ALL_USER) {
                userSid = usrSid;
            }
            RsvInitPubDao ripDao = new RsvInitPubDao(con_);
            ArrayList<String> usrGrpList = new ArrayList<String>();
            //指定ユーザグループのみ公開の場合
            List<RsvInitPubModel> ripList =  ripDao.select(userSid);
            for (RsvInitPubModel ripMdl : ripList) {
                if (ripMdl.getRipType() == GSConst.TYPE_GROUP) {
                    usrGrpList.add("G" + String.valueOf(ripMdl.getRipPsid()));
                } else {
                    usrGrpList.add(String.valueOf(ripMdl.getRipPsid()));
                }
            }
            String[] sidList = usrGrpList.toArray(new String[0]);
            paramMdl.setRsv210PubUsrGrpSid(sidList);
        }
    }

    /**
     * <br>[機  能] 同時登録ユーザ情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv210ParamModel
     * @throws SQLException SQL実行時例外
     */
    private void __setUserList(Rsv210ParamModel paramMdl)
        throws SQLException {

        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();

        //デフォルト表示グループ
        RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();
        String dfGpSidStr = rsvSchBiz.getDispDefaultGroupSidStr(con_, usrSid);

        //表示グループ
        String dspGpSidStr = NullDefault.getString(paramMdl.getRsv210GroupSid(), dfGpSidStr);
        paramMdl.setRsv210GroupSid(dspGpSidStr);

        //同時登録スケジュールグループリスト
        paramMdl.setRsv210SchGroupLabel(rsvSchBiz.getSchGroupCombo(con_, reqMdl_, usrSid));

        //スケジュールを登録するユーザがいる場合、登録するユーザの名称をセット
        if (paramMdl.getSv_users() != null && paramMdl.getSv_users().length > 0) {
            setUserName(paramMdl, paramMdl.getSv_users());
        }
    }

    /**
     * <br>[機  能] スケジュールSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rsSid スケジュールリレーションSID
     * @return ret スケジュールSID
     * @throws SQLException SQL実行時例外
     */
    public int getScdSid(int rsSid) throws SQLException {

        RsvScdOperationDao rsvSchDao = new RsvScdOperationDao(con_);
        int scdSid = rsvSchDao.getScdSidFromRsSid(rsSid);

        return scdSid;
    }

    /**
     * <br>[機  能] 表示グループ用のグループリストを取得する
     * <br>[解  説] 管理者設定の共有範囲が「ユーザ全員で共有」の場合有効な全てのグループを取得する。
     * <br>         「所属グループ内のみ共有可」の場合、ユーザが所属するグループのみを返す。
     * <br>[備  考]
     *
     * @param usrSid ユーザSID
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public List<ExtendedLabelValueModel> getGroupLabelList(int usrSid) throws SQLException {

        List<ExtendedLabelValueModel> labelList = null;

        RsvScheduleBiz rsvSchBiz = new RsvScheduleBiz();
        labelList =
            rsvSchBiz.getGroupLabelForSchedule(
                con_, reqMdl_, usrSid, false);

        return labelList;
    }

    /**
     * <br>スケジュールSIDからスケジュール情報を取得する
     * @param req リクエスト
     * @param scdSid スケジュールSID
     * @param adminConf 管理者設定
     * @param con コネクション
     * @return ScheduleSearchModel
     * @throws SQLException SQL実行時例外
     */
    public ScheduleRsvModel getSchData(HttpServletRequest req,
                                        int scdSid,
                                        RsvSchAdmConfModel adminConf,
                                        Connection con)
        throws SQLException {

        ScheduleRsvModel scdMdl = null;
        CmnUsrmInfModel uMdl = null;

        try {

            RsvScdOperationDao rsvSchDao = new RsvScdOperationDao(con_);
            scdMdl = rsvSchDao.getSchData(scdSid, reqMdl_.getSmodel().getUsrsid());

            if (scdMdl == null || (scdMdl != null && scdMdl.getScdSid() < 1)) {
                return null;
            }
            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmDao cuDao = new CmnUsrmDao(con);

            //登録者
            uMdl = uDao.getUserInfoJtkb(scdMdl.getScdAuid(), -1);
            if (uMdl != null) {
                scdMdl.setScdAuidSei(uMdl.getUsiSei());
                scdMdl.setScdAuidMei(uMdl.getUsiMei());
                scdMdl.setScdAuidJkbn(cuDao.getUserJkbn(scdMdl.getScdAuid()));
            }

            //対象ユーザ
            if (scdMdl.getScdUsrKbn() == GSConstSchedule.USER_KBN_USER) {
                uMdl = uDao.getUserInfoJtkb(scdMdl.getScdUsrSid(), -1);
                if (uMdl != null) {
                    scdMdl.setScdUsrSei(uMdl.getUsiSei());
                    scdMdl.setScdUsrMei(uMdl.getUsiMei());
                    scdMdl.setScdUsrJkbn(cuDao.getUserJkbn(scdMdl.getScdUsrSid()));
                }
            } else {
                scdMdl.setScdUsrSei(
                        getUsrName(
                                req,
                                scdMdl.getScdUsrSid(),
                                scdMdl.getScdUsrKbn(),
                                con));
            }
        } catch (SQLException e) {
            log__.error("スケジュール情報の取得に失敗" + e);
            throw e;
        }

        return scdMdl;
    }

    /**
     * <br>ユーザSIDとユーザ区分からユーザ氏名を取得する
     * @param req リクエスト
     * @param usrSid ユーザSID
     * @param usrKbn ユーザ区分
     * @param con コネクション
     * @return String ユーザ氏名
     * @throws SQLException SQL実行時例外
     */
    public String getUsrName(HttpServletRequest req, int usrSid, int usrKbn, Connection con)
    throws SQLException {
        String ret = "";
        if (usrKbn == GSConstSchedule.USER_KBN_GROUP) {

            if (usrSid == GSConstSchedule.SCHEDULE_GROUP) {
                GsMessage gsMsg = new GsMessage();
                ret = gsMsg.getMessage(req, "cmn.group");
            } else {
                GroupDao grpDao = new GroupDao(con);
                ret = grpDao.getGroup(usrSid).getGrpName();
            }

        } else {
            UserSearchDao uDao = new UserSearchDao(con);
            CmnUsrmInfModel uMdl = uDao.getUserInfoJtkb(usrSid, GSConstUser.USER_JTKBN_ACTIVE);
            ret = uMdl.getUsiSei() + " " + uMdl.getUsiMei();
        }
        return ret;
    }

    /**
     * <br>[機  能] スケジュール登録ユーザ名一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv210ParamModel
     * @param users 選択されているユーザのユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setUserName(Rsv210ParamModel paramMdl, String[] users) throws SQLException {

        UserBiz userBiz = new UserBiz();
        ArrayList<UsrLabelValueBean> usrArray
            = userBiz.getUserLabelList(con_, users);

        paramMdl.setUserNameArray(usrArray);
    }

    /**
     * <br>[機  能] 施設予約 予約日付 をint型へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param dayStr 日付文字列
     * @return int型へ変換した日付文字列
     */
    private int __formatYrkDayStr(String dayStr) {
        if (StringUtil.isNullZeroString(dayStr)
        || !ValidateUtil.isNumber(dayStr)
        || dayStr.length() != 8) {
            return 99999999;
        }

        return Integer.parseInt(dayStr);
    }

    /**
     * <br>[機  能] 選択施設情報リストを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv210ParamModel
     * @return hiddList 選択施設リスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<RsvHidDayModel> getTargetSisetuList(Rsv210ParamModel paramMdl)
        throws SQLException {

        ArrayList<String> hiddArray = new ArrayList<String>();
        String[] hiddStr = paramMdl.getRsvIkkatuTorokuKey();
        if (hiddStr != null && hiddStr.length > 0) {
            for (String key : hiddStr) {
                hiddArray.add(key);
            }
        }

        Collections.sort(hiddArray);
        String saveDay = null;
        String saveHiddDayKey = null;
        RsvSisDataDao dao = new RsvSisDataDao(con_);
        ArrayList<String> searchArray = new ArrayList<String>();
        ArrayList<RsvHidDayModel> hiddList = new ArrayList<RsvHidDayModel>();

        //画面に表示しきれていないキーがあれば処理
        if (!hiddArray.isEmpty()) {

            for (String hiddKey : hiddArray) {

                //キーから日付部分を取得
                String hiddDayKey = hiddKey.substring(0, hiddKey.indexOf("-"));

                if (saveDay == null) {
                    saveDay = hiddDayKey;
                    saveHiddDayKey = hiddDayKey;
                } else if (!saveDay.equals(hiddDayKey)) {

                    ArrayList<RsvHidModel> hiddDayList = dao.selectHidSisetuList(searchArray);
                    hiddList.add(__getDaylySisetuList(hiddDayList, saveDay, saveHiddDayKey));

                    //配列とキーを初期化
                    searchArray = new ArrayList<String>();
                    saveDay = hiddDayKey;
                    saveHiddDayKey = hiddDayKey;
                }

                //キーの施設SID部分を追加
                String hiddSidKey = hiddKey.substring(hiddKey.indexOf("-") + 1);
                searchArray.add(hiddSidKey);
            }

            if (!searchArray.isEmpty()) {
                //リスト末尾
                ArrayList<RsvHidModel> hiddDayList = dao.selectHidSisetuList(searchArray);
                hiddList.add(__getDaylySisetuList(hiddDayList, saveDay, saveHiddDayKey));
            }
        }

        return hiddList;
    }

    /**
     * <br>[機  能] 入力内容をDBに反映する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Rsv210ParamModel
     * @param ctrl 採番用コネクション
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @return sidDataList 予約SID、施設SIDリスト
     * @throws SQLException SQL実行時例外
     */
    public List<int []> updateYoyakuData(
            Rsv210ParamModel paramMdl, MlCountMtController ctrl, int userSid, String appRootPath)
        throws SQLException {

        BaseUserModel usrMdl = _getSessionUserModel(reqMdl_);
        int usrSid = usrMdl.getUsrsid();

        UDate now = new UDate();
        //予約開始
        UDate frDate = new UDate();
        frDate.setHour(paramMdl.getRsv210SelectedHourFr());
        frDate.setMinute(paramMdl.getRsv210SelectedMinuteFr());
        frDate.setSecond(GSConstReserve.DAY_START_SECOND);
        frDate.setMilliSecond(GSConstReserve.DAY_START_MILLISECOND);

        //予約終了
        UDate toDate = new UDate();
        toDate.setHour(paramMdl.getRsv210SelectedHourTo());
        toDate.setMinute(paramMdl.getRsv210SelectedMinuteTo());
        toDate.setSecond(GSConstReserve.DAY_START_SECOND);

        RsvSisYrkModel yrkParam = new RsvSisYrkModel();
        yrkParam.setRsyMok(NullDefault.getString(paramMdl.getRsv210Mokuteki(), ""));
        yrkParam.setRsyFrDate(frDate);
        yrkParam.setRsyToDate(toDate);
        yrkParam.setRsyBiko(NullDefault.getString(paramMdl.getRsv210Naiyo(), ""));
        yrkParam.setRsyAuid(usrSid);
        yrkParam.setRsyAdate(now);
        yrkParam.setRsyEuid(usrSid);
        yrkParam.setRsyEdate(now);
        yrkParam.setRsyEdit(paramMdl.getRsv210RsyEdit());
        yrkParam.setRsyPublic(paramMdl.getRsv210RsyPublic());

        int schKbn = paramMdl.getRsv210SchKbn();
        int rsvSchGrpSid = NullDefault.getInt(paramMdl.getRsv210SchGroupSid(), -1);
        String[] users = paramMdl.getSv_users();

        boolean schInsertFlg = (schKbn == GSConstReserve.RSV_SCHKBN_USER
                && users != null && users.length > 0)
        || (schKbn == GSConstReserve.RSV_SCHKBN_GROUP
                && rsvSchGrpSid >= 0);

        IRsvYoyakuRegister.Builder regBld = IRsvYoyakuRegister.ikkatuRegistBuilder(
                con_, reqMdl_, ctrl, appRootPath, yrkParam,
                Optional.ofNullable(paramMdl.getRsvIkkatuTorokuKey())
                    .stream()
                    .flatMap(arr -> Stream.of(arr))
                    .collect(Collectors.toList())
                );

        //公開区分が対象ユーザ・グループのみ公開の場合
        if (paramMdl.getRsv210RsyPublic() == GSConstReserve.PUBLIC_KBN_USRGRP) {
            //削除済みユーザは設定出来ないのでリストから除外する。
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con_);
            UserBiz userBiz = new UserBiz();

            ArrayList<Integer> grpSids = new ArrayList<Integer>();
            List<String> usrSids = new ArrayList<String>();
            for (String target : paramMdl.getRsv210PubUsrGrpSid()) {
                if (target.startsWith("G")) {
                    grpSids.add(NullDefault.getInt(
                            target.substring(1), -1));
                } else {
                    if (NullDefault.getInt(
                            target, -1) > GSConstUser.USER_RESERV_SID) {
                        usrSids.add(target);
                    }
                }
            }
            ArrayList<GroupModel> glist = new ArrayList<GroupModel>();
            ArrayList<BaseUserModel> ulist = new ArrayList<BaseUserModel>();
            //グループ存在チェック
            if (!grpSids.isEmpty()) {
                glist = gdao.selectGroupNmListOrderbyClass(grpSids);
            }
            //ユーザ存在チェック
            if (!usrSids.isEmpty()) {
                ulist = userBiz.getBaseUserList(con_,
                                                usrSids.toArray(new String[usrSids.size()]));
            }
            String[] checkPubUsrGrpSid = new String[glist.size() + ulist.size()];
            int i = 0;
            for (GroupModel gMdl : glist) {
                checkPubUsrGrpSid[i] = "G" + gMdl.getGroupSid();
                i++;
            }
            for (BaseUserModel uMdl : ulist) {
                checkPubUsrGrpSid[i] = String.valueOf(uMdl.getUsrsid());
                i++;
            }
            paramMdl.setRsv210PubUsrGrpSid(checkPubUsrGrpSid);
            regBld.setPubList(
                    Stream.of(paramMdl.getRsv210PubUsrGrpSid())
                    .map(targetSid -> {
                        RsvDataPubModel rdpMdl = new RsvDataPubModel();
                        if (targetSid.startsWith("G")) {
                            rdpMdl.setRdpType(GSConst.TYPE_GROUP);
                            rdpMdl.setRdpPsid(Integer.parseInt(targetSid.substring(1)));
                        } else {
                            rdpMdl.setRdpType(GSConst.TYPE_USER);
                            rdpMdl.setRdpPsid(Integer.parseInt(targetSid));
                        }
                        return rdpMdl;
                    })
                    .collect(Collectors.toList())
                );
        }


        regBld.setUseSch(schInsertFlg);

        //施設予約を登録
        IRsvYoyakuRegister regRsv = regBld.build();
        regRsv.regist();

        List<int []> sidDataList =
            regRsv.getRsySidMap().values().stream()
                    .flatMap(map -> map.entrySet().stream())
                    .map(ent -> new int[] {ent.getValue(), ent.getKey()})
                    .collect(Collectors.toList());



        if (schInsertFlg) {
            SchDataModel baseSch = __createSchData(frDate, toDate, paramMdl, usrSid, now);
            Set<Integer> userSidSet = new HashSet<>();
            String mainTargetSid;
            if (paramMdl.getRsv210SchKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                baseSch.setScdUsrKbn(GSConstSchedule.USER_KBN_GROUP);
                baseSch.setScdUsrSid(rsvSchGrpSid);
                mainTargetSid = String.format("G%d", rsvSchGrpSid);
                baseSch.setScdTargetGrp(GSConstSchedule.REMINDER_USE_YES);
            } else {
                int target = Stream.of(users)
                            .findFirst()
                            .map(str -> Integer.parseInt(str))
                            .orElse(-1);
                mainTargetSid = String.valueOf(target);
                userSidSet.addAll(Stream.of(users)
                    .map(Integer::parseInt)
                    .filter(sid -> sid != target)
                    .collect(Collectors.toSet()));
                baseSch.setScdUsrKbn(GSConstSchedule.USER_KBN_USER);
                baseSch.setScdUsrSid(target);
            }
            ISchRegister.Builder regSchBld = ISchRegister.ikkatuRegistBuilder(
                    con_, reqMdl_, ctrl,
                    baseSch,
                    Optional.ofNullable(paramMdl.getRsvIkkatuTorokuKey())
                    .stream()
                    .flatMap(arr -> Stream.of(arr))
                    .map(key -> key.substring(0, key.indexOf("-")))
                    .collect(Collectors.toSet())
                    .stream()
                    .map(ymd -> String.format("%s-%s", ymd, mainTargetSid))
                    .collect(Collectors.toList())
                    );
            regSchBld.setSchResSidMap(regRsv.getRsyGrpsList().stream()
                    .collect(Collectors.toMap(
                            grp -> grp.getTargetDate().getDateString("/"),
                            grp -> grp.getScdRsSid())));
            if (paramMdl.getRsv210RsyPublic() == GSConstReserve.PUBLIC_KBN_USRGRP) {
                regSchBld.setPubList(
                        Stream.of(paramMdl.getRsv210PubUsrGrpSid())
                        .map(targetSid -> {
                            SchDataPubModel sdpMdl = new SchDataPubModel();
                            if (targetSid.startsWith("G")) {
                                sdpMdl.setSdpType(GSConst.TYPE_GROUP);
                                sdpMdl.setSdpPsid(Integer.parseInt(targetSid.substring(1)));
                            } else {
                                sdpMdl.setSdpType(GSConst.TYPE_USER);
                                sdpMdl.setSdpPsid(Integer.parseInt(targetSid));
                            }
                            return sdpMdl;
                        })
                        .collect(Collectors.toList())
                    );
            }
            regSchBld.setUseRsv(true);
            regSchBld.setUsers(userSidSet);
            ISchRegister regSch = regSchBld.build();
            regSch.regist();
        }


        return sidDataList;
    }
    /**
     * <br>[機  能] 登録モデルを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param frDate 開始日時
     * @param toDate 終了日時
     * @param paramMdl Rsv210ParamModel
     * @param usrSid セッションユーザSID
     * @param now 現在日時
     * @return 登録モデル
     * @throws SQLException
     */
    private SchDataModel __createSchData(
            UDate frDate, UDate toDate, Rsv210ParamModel paramMdl,
                                            int usrSid, UDate now
                                            ) throws SQLException {


        SchDataModel schMdl = new SchDataModel();
        schMdl.setScdDaily(GSConstSchedule.TIME_EXIST);
        schMdl.setScdFrDate(frDate);
        schMdl.setScdToDate(toDate);
        schMdl.setScdBgcolor(GSConstSchedule.DF_BG_COLOR);
        schMdl.setScdTitle(NullDefault.getString(paramMdl.getRsv210Mokuteki(), ""));
        schMdl.setScdValue(NullDefault.getString(paramMdl.getRsv210Naiyo(), ""));
        schMdl.setScdBiko("");
        schMdl.setScdPublic(GSConstSchedule.DSP_PUBLIC);
        schMdl.setScdAuid(usrSid);
        schMdl.setScdAdate(now);
        schMdl.setScdEuid(usrSid);
        schMdl.setScdEdate(now);
        schMdl.setScdEdit(RsvScheduleBiz.getScdEditKbn(paramMdl.getRsv210RsyEdit()));
        schMdl.setScdPublic(RsvScheduleBiz.getScdPublicKbn(
                paramMdl.getRsv210RsyPublic(), paramMdl.getRsv210SchKbn()));

        //拡張登録SID
        schMdl.setSceSid(-1);
        schMdl.setScdGrpSid(-1);
        schMdl.setScdRsSid(-1);
        SchCommonBiz schCmnBiz = new SchCommonBiz(con_, reqMdl_);
        schCmnBiz.getUserPriConf(new String[] {String.valueOf(usrSid)})
            .values().stream()
            .findAny()
            .ifPresent(push -> {
                schMdl.setScdReminder(push.getSccReminder());
            });
        return schMdl;
    }

    /**
     * <br>[機  能] ユーザコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setUserCombo(
            Connection con,
            Rsv210ParamModel paramMdl,
            RequestModel reqMdl) throws SQLException {

        String[] selectUserSid = paramMdl.getRsv210PubUsrGrpSid();
        if (selectUserSid == null) {
            selectUserSid = new String[0];
        }

        //デフォルトユーザを設定
        if (paramMdl.getRsv210PubDefUsrSid() > 0) {
            boolean defFlg = false;
            ArrayList<String> usrSidList = new ArrayList<String>();
            for (String usid : selectUserSid) {
                usrSidList.add(usid);
                if (usid.equals(String.valueOf(paramMdl.getRsv210PubDefUsrSid()))) {
                    defFlg = true;
                }
            }
            if (!defFlg) {
                usrSidList.add(String.valueOf(paramMdl.getRsv210PubDefUsrSid()));
            }
            paramMdl.setRsv210PubUsrGrpSid(
                    (String[]) usrSidList.toArray(new String[usrSidList.size()]));
        }

    }

    /**
     * <br>[機  能] スケジュールの公開対象を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param scdSid スケジュールSID
     * @throws SQLException SQL実行時例外
     */
    protected void __setSdpMdl(Rsv210ParamModel paramMdl, int scdSid)
            throws SQLException {

        RsvScdOperationDao rsoDao = new RsvScdOperationDao(con_);
        RsvSchDataPubModel rdpMdl = new RsvSchDataPubModel();
        String[] sidList = paramMdl.getRsv210PubUsrGrpSid();

        for (int i = 0; i < sidList.length; i++) {
            if (sidList[i].substring(0, 1).equals("G")) {
                rdpMdl.setSdpPsid(Integer.parseInt(sidList[i].substring(1)));
                rdpMdl.setSdpType(GSConst.TYPE_GROUP);
            } else {
                rdpMdl.setSdpPsid(Integer.parseInt(sidList[i]));
                rdpMdl.setSdpType(GSConst.TYPE_USER);
            }
            rdpMdl.setScdSid(scdSid);
            rsoDao.insert(rdpMdl);
        }
    }
}