package jp.groupsession.v2.api.schedule.edit;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.SchDao;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.dao.RsvSisDataDao;
import jp.groupsession.v2.rsv.dao.RsvSisYrkDao;
import jp.groupsession.v2.rsv.model.RsvSisDataModel;
import jp.groupsession.v2.rsv.model.RsvSisYrkModel;
import jp.groupsession.v2.rsv.rsv110.Rsv110Biz;
import jp.groupsession.v2.rsv.rsv210.Rsv210Model;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchAddressDao;
import jp.groupsession.v2.sch.dao.SchDataDao;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.dao.ScheduleReserveDao;
import jp.groupsession.v2.sch.model.SchAddressModel;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchDataModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.sch.model.SchRepeatKbnModel;
import jp.groupsession.v2.sch.model.ScheduleSearchModel;
import jp.groupsession.v2.sch.sch040.Sch040Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
/**
 *
 * <br>[機  能] スケジュール編集WEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "schedule-edit",
plugin = "schedule", name = "スケジュール登録・編集",
url = "/api/schedule/edit.do", reqtype = "POST")
public class ApiSchEditForm extends AbstractApiForm {

    /**Schsid スケジュールSID*/
    private String schSid__;
    /**SchKf スケジュール公開フラグ*/
    private String schKf__;
    /**SchKfTarget スケジュール公開対象 */
    private String[] schKfTarget__;
    /**SchEf スケジュール編集フラグ*/
    private String schEf__;
    /**Title*/
    @ApiParam(name = "title", viewName = "タイトル")
    private String title__;
    /**Naiyo*/
    @ApiParam(name = "naiyo", viewName = "内容", required = false, confRequired = true)
    private String naiyo__;
    /**StartDateTime*/
    @ApiParam(name = "from", viewName = "開始日時")
    private String from__;
    /** frDate （内部利用）*/
    private UDate frDate__;
    /**EndDateTime*/
    @ApiParam(name = "to", viewName = "終了日時")
    private String to__;
    /** toDate （内部利用）*/
    private UDate toDate__;
    /**TimeKbn*/
    private String timeKbn__;
    /**UserKbn*/
    @ApiParam(name = "userKbn", viewName = "ユーザ/グループ", required = false, confRequired = true)
    private String userKbn__ = "0";
    /**UserSid*/
    private String usrSid__;
    /**targetId*/
    @ApiParam(name = "targetId",
            viewName = "登録対象ユーザID/グループID", required = false, confRequired = true)
    private String targetId__;
    /**ColorKbn*/
    private String colorKbn__;
    /**Biko*/
    private String biko__;
    /**同時登録ユーザ*/
    private String[] sameScheduledUser__;
    /**施設予約*/
    private String[] reserves__;
    /**企業SID*/
    private String[] acoSid__;
    /**企業拠点SID*/
    private String[] abaSid__;
    /**アドレスSid*/
    private String[] adress__;
    /** 同時登録スケジュール編集*/
    private String batchRef__;
    /** 施設予約編集*/
    private String batchResRef__;
    /** コピーフラグ*/
    private String copyFlg__;
    /** 重複警告無視フラグ*/
    private String wornCommit__;
    /**コンタクト履歴編集フラグ*/
    private String contactFlg__;
    /** 出欠確認*/
    private String attendKbn__;
    /** 出欠確認メール再通知*/
    private String attendMailResendKbn__;
    /** リマインダー通知 グループ通知 */
    private String targetGrp__;
    /** リマインダー通知 通知時間 */
    private String reminder__;

    /**
     * <p>schSid を取得します。
     * @return schSid
     */
    public String getSchSid() {
        return schSid__;
    }
    /**
     * <p>schSid をセットします。
     * @param schSid schSid
     */
    public void setSchSid(String schSid) {
        schSid__ = schSid;
    }
    /**
     * <p>schKf を取得します。
     * @return schKf
     */
    public String getSchKf() {
        return schKf__;
    }
    /**
     * <p>schKf をセットします。
     * @param schKf schKf
     */
    public void setSchKf(String schKf) {
        schKf__ = schKf;
    }
    /**
     * <p>schKfTarget を取得します。
     * @return schKfTarget
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#schKfTarget__
     */
    public String[] getSchKfTarget() {
        return schKfTarget__;
    }
    /**
     * <p>schKfTarget をセットします。
     * @param schKfTarget schKfTarget
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditParamModel#schKfTarget__
     */
    public void setSchKfTarget(String[] schKfTarget) {
        schKfTarget__ = schKfTarget;
    }
    /**
     * <p>schEf を取得します。
     * @return schEf
     */
    public String getSchEf() {
        return schEf__;
    }
    /**
     * <p>schEf をセットします。
     * @param schEf schEf
     */
    public void setSchEf(String schEf) {
        schEf__ = schEf;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>naiyo を取得します。
     * @return naiyo
     */
    public String getNaiyo() {
        return naiyo__;
    }
    /**
     * <p>naiyo をセットします。
     * @param naiyo naiyo
     */
    public void setNaiyo(String naiyo) {
        naiyo__ = naiyo;
    }
    /**
     * <p>from を取得します。
     * @return from
     */
    public String getFrom() {
        return from__;
    }
    /**
     * <p>from をセットします。
     * @param from from
     */
    public void setFrom(String from) {
        from__ = from;
    }
    /**
     * <p>to を取得します。
     * @return to
     */
    public String getTo() {
        return to__;
    }
    /**
     * <p>to をセットします。
     * @param to to
     */
    public void setTo(String to) {
        to__ = to;
    }
    /**
     * <p>timeKbn を取得します。
     * @return timeKbn
     */
    public String getTimeKbn() {
        return timeKbn__;
    }
    /**
     * <p>timeKbn をセットします。
     * @param timeKbn timeKbn
     */
    public void setTimeKbn(String timeKbn) {
        timeKbn__ = timeKbn;
    }
    /**
     * <p>userKbn を取得します。
     * @return userKbn
     */
    public String getUserKbn() {
        return userKbn__;
    }
    /**
     * <p>userKbn をセットします。
     * @param userKbn userKbn
     */
    public void setUserKbn(String userKbn) {
        userKbn__ = userKbn;
    }
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>targetId を取得します。
     * @return targetId
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditForm#targetId__
     */
    public String getTargetId() {
        return targetId__;
    }
    /**
     * <p>targetId をセットします。
     * @param targetId targetId
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditForm#targetId__
     */
    public void setTargetId(String targetId) {
        targetId__ = targetId;
    }
    /**
     * <p>colorKbn を取得します。
     * @return colorKbn
     */
    public String getColorKbn() {
        return colorKbn__;
    }
    /**
     * <p>colorKbn をセットします。
     * @param colorKbn colorKbn
     */
    public void setColorKbn(String colorKbn) {
        colorKbn__ = colorKbn;
    }
    /**
     * <p>biko を取得します。
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }
    /**
     * <p>biko をセットします。
     * @param biko biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }
    /**
     * <p>sameScheduledUser を取得します。
     * @return sameScheduledUser
     */
    public String[] getSameScheduledUser() {
        return sameScheduledUser__;
    }
    /**
     * <p>sameScheduledUser をセットします。
     * @param sameScheduledUser sameScheduledUser
     */
    public void setSameScheduledUser(String[] sameScheduledUser) {
        sameScheduledUser__ = sameScheduledUser;
    }
    /**
     * <p>reserves を取得します。
     * @return reserves
     */
    public String[] getReserves() {
        return reserves__;
    }
    /**
     * <p>reserves をセットします。
     * @param reserves reserves
     */
    public void setReserves(String[] reserves) {
        reserves__ = reserves;
    }
    /**
     * <p>acoSid を取得します。
     * @return acoSid
     */
    public String[] getAcoSid() {
        return acoSid__;
    }
    /**
     * <p>acoSid をセットします。
     * @param acoSid acoSid
     */
    public void setAcoSid(String[] acoSid) {
        acoSid__ = acoSid;
    }
    /**
     * <p>abaSid を取得します。
     * @return abaSid
     */
    public String[] getAbaSid() {
        return abaSid__;
    }
    /**
     * <p>abaSid をセットします。
     * @param abaSid abaSid
     */
    public void setAbaSid(String[] abaSid) {
        abaSid__ = abaSid;
    }
    /**
     * <p>adress を取得します。
     * @return adress
     */
    public String[] getAdress() {
        return adress__;
    }
    /**
     * <p>adress をセットします。
     * @param adress adress
     */
    public void setAdress(String[] adress) {
        adress__ = adress;
    }

    /**
     * <p>batchRef を取得します。
     * @return batchRef
     */
    public String getBatchRef() {
        return batchRef__;
    }
    /**
     * <p>batchRef をセットします。
     * @param batchRef batchRef
     */
    public void setBatchRef(String batchRef) {
        batchRef__ = batchRef;
    }
    /**
     * <p>batchResRef を取得します。
     * @return batchResRef
     */
    public String getBatchResRef() {
        return batchResRef__;
    }
    /**
     * <p>batchResRef をセットします。
     * @param batchResRef batchResRef
     */
    public void setBatchResRef(String batchResRef) {
        batchResRef__ = batchResRef;
    }
    /**
     * <p>copyFlg を取得します。
     * @return copyFlg
     */
    public String getCopyFlg() {
        return copyFlg__;
    }
    /**
     * <p>copyFlg をセットします。
     * @param copyFlg copyFlg
     */
    public void setCopyFlg(String copyFlg) {
        copyFlg__ = copyFlg;
    }
    /**
     * <p>wornCommit を取得します。
     * @return wornCommit
     */
    public String getWornCommit() {
        return wornCommit__;
    }
    /**
     * <p>wornCommit をセットします。
     * @param wornCommit wornCommit
     */
    public void setWornCommit(String wornCommit) {
        wornCommit__ = wornCommit;
    }
    /**
     * <p>contact を取得します。
     * @return contact
     */
    public String getContactFlg() {
        return contactFlg__;
    }
    /**
     * <p>contact をセットします。
     * @param contactFlg contactFlg
     */
    public void setContactFlg(String contactFlg) {
        contactFlg__ = contactFlg;
    }
    /**
    *
    * <br>[機  能] コンタクト履歴編集フラグ初期化
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @throws SQLException SQL実行時例外
    */
    private void __initContact(Connection con) throws SQLException {
        int schSid = NullDefault.getInt(getSchSid(), -1);
        if (schSid == -1) {
            contactFlg__ = "0";
        }
        SchAddressDao addressDao = new SchAddressDao(con);
        List<SchAddressModel> addressList = addressDao.select(schSid);
        if (addressList != null) {
            String[] addressId = new String[addressList.size()];
            for (int index = 0; index < addressList.size(); index++) {
                addressId[index] = String.valueOf(addressList.get(index).getAdrSid());
                if (addressList.get(index).getAdcSid() > 0) {
                    contactFlg__ = "1";
                    return;
                }
            }

        }
        contactFlg__ = "0";
    }
    /**
     * <br>[機  能] 初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void init(Connection con,
            int sessionUsrSid) throws SQLException {
        if (contactFlg__ == null) {
            //コンタクト履歴編集フラグ設定
            __initContact(con);
        }
        if (usrSid__ == null) {
            usrSid__ = String.valueOf(sessionUsrSid);
        }

    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param sessionSid セッションユーザSID
     * @param oldMdl 編集前データ
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con,
            RequestModel reqMdl,
            int sessionSid,
            ScheduleSearchModel oldMdl) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();

        //登録対象者ID/登録対象者SIDの存在チェック
        int targetSid = __getTargetSid(con, sessionUsrSid);
        if (targetSid < 0) {
            if (NullDefault.getInt(userKbn__, 0) == GSConstSchedule.USER_KBN_GROUP) {
                msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage("cmn.group"));
            } else {
                msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage("cmn.user"));
            }
            errors.add("targetId." + "error.search.notfound", msg);
        }

        ArrayList<String> usrSidList = new ArrayList<String>();
        usrSidList.add(String.valueOf(targetSid));

        if (sameScheduledUser__ != null) {
            usrSidList.addAll(Arrays.asList(sameScheduledUser__));
        }
        boolean first = true;
        List<String> errorUserList = new ArrayList<String>();
        for (String usrSidStr : usrSidList) {
            //アクセス不可グループ、またはユーザに対してのスケジュール登録を許可しない
            int selectUserSid = NullDefault.getInt(usrSidStr, -1);
            if (selectUserSid >= 0) {
                SchDao schDao = new SchDao(con);
                if (first && NullDefault.getInt(userKbn__, 0) == GSConstSchedule.USER_KBN_GROUP) {
                    CmnGroupmDao grpDao = new CmnGroupmDao(con);
                    CmnGroupmModel gmdl = grpDao.select(selectUserSid);
                    if (gmdl == null) {
                        msg = new ActionMessage("search.data.notfound",
                                gsMsg.getMessage("cmn.group"));
                        errors.add("" + "error.search.notfound", msg);
                    } else if (!schDao.canRegistGroupSchedule(selectUserSid, sessionUsrSid)) {
                        //グループスケジュール登録権限チェック

                        msg = new ActionMessage("error.cant.entry.schedule", gmdl.getGrpName());
                        errors.add("" + "error.cant.entry.schedule", msg);
                    }
                } else {

                    //ユーザスケジュール登録権限チェック
                    if (!schDao.canRegistUserSchedule(selectUserSid, sessionUsrSid)) {
                        errorUserList.add(usrSidStr);
                    }
                }
            }
            first = false;
        }
        //エラーユーザ情報取得
        if (!errorUserList.isEmpty()) {

            UserBiz userBiz = new UserBiz();
            ArrayList<BaseUserModel> ulist
                    = userBiz.getBaseUserList(con,
                                    (String[]) errorUserList.toArray(
                                                new String[errorUserList.size()]));
            for (BaseUserModel umodel : ulist) {
                String usrName = umodel.getUsisei() + " " + umodel.getUsimei();
                msg = new ActionMessage("error.cant.entry.schedule",
                        usrName);
                errors.add(String.valueOf(umodel.getUsrsid())
                        + ".error.cant.entry.userschedule", msg);

            }
        }

        //スケジュール公開フラグチェック
        int publicKbn = NullDefault.getInt(schKf__, -1);
        if (NullDefault.getInt(userKbn__, -1) == GSConstSchedule.USER_KBN_GROUP) {
            if (publicKbn != GSConstSchedule.DSP_PUBLIC
            && publicKbn != GSConstSchedule.DSP_NOT_PUBLIC) {
                msg = new ActionMessage("error.input.notvalidate.data",
                        gsMsg.getMessage("cmn.public"));
            }
        } else {
            if (publicKbn != GSConstSchedule.DSP_PUBLIC
            && publicKbn != GSConstSchedule.DSP_NOT_PUBLIC
            && publicKbn != GSConstSchedule.DSP_YOTEIARI
            && publicKbn != GSConstSchedule.DSP_BELONG_GROUP
            && publicKbn != GSConstSchedule.DSP_USRGRP
            && publicKbn != GSConstSchedule.DSP_TITLE
            ) {
                msg = new ActionMessage("error.input.notvalidate.data",
                        gsMsg.getMessage("cmn.public"));
            }
        }

        //公開対象チェック
        if (NullDefault.getInt(schKf__, 0) == GSConstSchedule.DSP_USRGRP) {
            //未選択チェック
            if (schKfTarget__ == null || schKfTarget__.length == 0) {
                msg = new ActionMessage("error.select.required.text",
                                        gsMsg.getMessage("main.exposed"));
                StrutsUtil.addMessage(
                        errors, msg, "sv_publicTarget");
            } else {
                //存在チェック
                ArrayList<Integer> grpSids = new ArrayList<Integer>();
                List<String> usrSids = new ArrayList<String>();
                for (String target : schKfTarget__) {
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

                //グループ存在チェック
                if (!grpSids.isEmpty()) {
                    UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                    ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyClass(grpSids);
                    if (glist == null || glist.size() != grpSids.size()) {
                        msg = new ActionMessage("error.input.notvalidate.data",
                                gsMsg.getMessage("main.exposed"));
                        StrutsUtil.addMessage(
                                errors, msg, "sv_publicTarget");
                    }
                }
                //ユーザ存在チェック
                if (!usrSids.isEmpty()) {
                    UserBiz userBiz = new UserBiz();
                    ArrayList<BaseUserModel> ulist
                            = userBiz.getBaseUserList(con,
                                                    usrSids.toArray(new String[usrSids.size()]));
                    if (ulist == null || ulist.size() != usrSids.size()) {
                        msg = new ActionMessage("error.input.notvalidate.data",
                                gsMsg.getMessage("main.exposed"));
                        StrutsUtil.addMessage(
                                errors, msg, "sv_publicTarget");
                    }
                }
            }
        }

        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl);
        SchAdmConfModel adminConf = schCmnBiz.getAdmConfModel(con);

        CommonBiz cmnBiz = new CommonBiz();
        if (reserves__ != null) {
            RsvSisDataDao sisDataDao = new RsvSisDataDao(con);
            //施設予約の管理者
            boolean rsvAdmin = cmnBiz.isPluginAdmin(con,
                    reqMdl.getSmodel(),
                    GSConstSchedule.PLUGIN_ID_RESERVE);

            ArrayList<RsvSisDataModel> selectResList = new ArrayList<RsvSisDataModel>();
            ArrayList<Integer> resList = new ArrayList<Integer>();
            for (int i = 0; i < reserves__.length; i++) {
                resList.add(Integer.valueOf(reserves__[i]));
            }
            if (rsvAdmin) {
                //全施設
                selectResList =
                        sisDataDao.selectGrpSisetuList(resList);
            } else {
                //閲覧権限のある施設
                selectResList =
                        sisDataDao.selectGrpSisetuCanReadList(resList, sessionUsrSid);
            }
            if (selectResList.size() < resList.size()) {

                msg = new ActionMessage("error.edit.power.notfound",
                        gsMsg.getMessage("cmn.institution"),
                        gsMsg.getMessage("schedule.151"));
                errors.add("" + "error.edit.power.notfound.institution", msg);

            }
        }


        //時間利用フラグ
        int timeKbn = NullDefault.getInt(timeKbn__, 0);

        GSValidateCommon.validateDateTimeFieldText(errors,
                from__,
                "from",
                gsMsg.getMessage("tcd.171"),
                true);
        GSValidateCommon.validateDateTimeFieldText(errors,
                to__,
                "to",
                gsMsg.getMessage("tcd.173"),
                true);
        if (errors.isEmpty()) {
            UDate frdate = UDateUtil.getUDate(from__.substring(0, 4),
                    from__.substring(5, 7),
                    from__.substring(8, 10));
            frdate.setZeroHhMmSs();
            frdate.setHour(Integer.valueOf(from__.substring(11, 13)));
            frdate.setMinute(Integer.valueOf(from__.substring(14, 16)));

            UDate todate = UDateUtil.getUDate(to__.substring(0, 4),
                    to__.substring(5, 7),
                    to__.substring(8, 10));
            todate.setZeroHhMmSs();
            todate.setHour(Integer.valueOf(to__.substring(11, 13)));
            todate.setMinute(Integer.valueOf(to__.substring(14, 16)));

            if (timeKbn == GSConstSchedule.TIME_NOT_EXIST) {
                frdate.setZeroHhMmSs();
                todate.setMaxHhMmSs();
            }
            frDate__ = frdate;
            toDate__ = todate;
            if (frdate.compareDateYMDHM(todate) != UDate.LARGE) {
                //開始 < 終了
                String textStartLessEnd = gsMsg.getMessage("cmn.start.lessthan.end");
                //開始・終了
                String textStartEnd = gsMsg.getMessage("cmn.start.end");
                msg = new ActionMessage("error.input.comp.text", textStartEnd, textStartLessEnd);
                errors.add("" + "error.input.comp.text", msg);
            }
        }


        //タイトルのチェック
        GSValidateCommon.validateTextField(errors,
                title__,
                "title",
                gsMsg.getMessage("cmn.title"),
                GSConstSchedule.MAX_LENGTH_TITLE,
                true);
        //内容のチェック
        GSValidateCommon.validateTextAreaField(errors,
                naiyo__,
                "naiyo",
                gsMsg.getMessage("cmn.content"),
                GSConstSchedule.MAX_LENGTH_VALUE, false);


        //備考のチェック
        GSValidateCommon.validateTextAreaField(errors,
                biko__,
                "biko",
                gsMsg.getMessage("cmn.memo"),
                GSConstSchedule.MAX_LENGTH_BIKO, false);



        ApiSchEditBiz eBiz = new ApiSchEditBiz(con, reqMdl, null);

        int scdSid = NullDefault.getInt(schSid__, -1);
        if (scdSid != -1) {
            errors = eBiz.validateExistData(errors,
                    gsMsg.getMessage("cmn.change"), oldMdl);
            if (!errors.isEmpty()) {
                return errors;
            }
        }

        //出欠確認スケジュールは単体編集不可
        if (scdSid != -1
                && NullDefault.getInt(attendKbn__, GSConstSchedule.ATTEND_KBN_NO)
                    != GSConstSchedule.ATTEND_KBN_NO
                && NullDefault.getInt(batchRef__, 1) == GSConstSchedule.SAME_EDIT_OFF) {
            msg = new ActionMessage("error.cant.edit.single.attend.schedule");
            StrutsUtil.addMessage(errors, msg, "attend");
            return errors;
        }

        //同時登録スケジュールの編集権限チェック
        errors = eBiz.validateSchPowerCheck(errors,
                scdSid,
                NullDefault.getInt(batchRef__, -1), 0);

        if (errors.isEmpty()) {
            //スケジュール重複登録チェック
            errors = validateSchRepeatCheck(
                    reqMdl,
                    errors,
                    con,
                    reqMdl.getSmodel().getUsrsid(),
                    GSConstSchedule.SCH_REPEAT_KBN_NG);

            //同時登録施設予約の編集権限チェック
            errors = eBiz.validateResPowerCheck(errors,
                    NullDefault.getInt(schSid__, -1),
                    NullDefault.getInt(batchResRef__, -1));

            //施設予約エラーチェック

            Sch040Biz biz = new Sch040Biz(con, reqMdl);

            RsvSisYrkDao yrkDao = new RsvSisYrkDao(con);
            boolean errorFlg = false;
            String[] rsdSids = null;

            if (timeKbn == GSConstSchedule.TIME_EXIST) {
                rsdSids = getReserves();
            }

            Rsv210Model dataMdl = null;
            if (rsdSids != null) {
                Rsv110Biz rsv110biz = new Rsv110Biz(reqMdl, con);
                for (String rsdSid : rsdSids) {
                    dataMdl = rsv110biz.getGroupData(Integer.parseInt(rsdSid));
                    if (dataMdl != null) {

                        //予約可能期限チェック(期限が設定されていればチェックする)
                        String kigen = dataMdl.getRsdProp6();
                        if (!StringUtil.isNullZeroString(kigen)) {

                            //施設グループ管理者の場合は予約可能期限チェックをパスする
                            RsvCommonBiz rsvBiz = new RsvCommonBiz();
                            if (!rsvBiz.isGroupAdmin(con, Integer.parseInt(rsdSid),
                                    reqMdl.getSmodel().getUsrsid())) {
                                UDate now = new UDate();
                                UDate udKigen = now.cloneUDate();
                                udKigen.addDay(Integer.parseInt(kigen));

                                String kigenYmd = udKigen.getDateString();
                                String chkYmd = toDate__.getDateString();

                                if (Integer.parseInt(chkYmd) > Integer.parseInt(kigenYmd)) {
                                    //開始・終了
                                    String textDayAfter = gsMsg.getMessage("cmn.days.after");
                                    String kigenStr =
                                            "※"
                                                    + dataMdl.getRsdProp6()
                                                    + textDayAfter;

                                    msg = new ActionMessage("error.kigen.over2.sisetu", kigenStr);
                                    StrutsUtil.addMessage(errors, msg, "sisetu");
                                    errorFlg = true;
                                }
                            }

                        }
                    }

                    //重複のチェック(重複登録 = 不可の場合にチェック)
                    String tyohuku = dataMdl.getRsdProp7();
                    if (!errorFlg
                            && !StringUtil.isNullZeroString(tyohuku)
                            && Integer.parseInt(tyohuku) == GSConstReserve.PROP_KBN_HUKA) {

                        List<RsvSisYrkModel> ngList = new ArrayList<RsvSisYrkModel>();
                        //施設予約重複チェック
                        //新規モード
                        if (NullDefault.getInt(schSid__, -1) == -1) {

                            ngList = yrkDao.getYrkNgList(-1, Integer.parseInt(rsdSid),
                                    frDate__, toDate__);

                            //編集モード
                        } else  {
                            ArrayList<RsvSisYrkModel> yrkList = null;
                            if (oldMdl.getScdRsSid() != -1) {
                                yrkList = yrkDao.getScheduleRserveData(
                                        oldMdl.getScdRsSid()
                                        );
                            }
                            RsvSisYrkModel yrkMdl = null;

                            yrkMdl = getReserveData(yrkList, Integer.parseInt(rsdSid));

                            if (yrkMdl == null) {
                                ngList = yrkDao.getYrkNgList(
                                        -1, Integer.parseInt(rsdSid), frDate__, toDate__);

                            } else {
                                ngList = yrkDao.getYrkNgList(
                                        yrkMdl.getRsySid(), yrkMdl.getRsdSid(), frDate__, toDate__);
                            }

                        }

                        //重複チェック
                        if (ngList != null && ngList.size() > 0) {

                            String textSchedule = gsMsg.getMessage("cmn.reserve");
                            msg = new ActionMessage("error.input.dup", textSchedule);
                            StrutsUtil.addMessage(errors, msg, "rsv110YrkEr");

                            for (RsvSisYrkModel yrkModel : ngList) {

                                String schTime = UDateUtil.getYymdJ(yrkModel.getRsyFrDate(),
                                        reqMdl);
                                schTime += UDateUtil.getSeparateHMJ(yrkModel.getRsyFrDate(),
                                        reqMdl);
                                schTime += "～";
                                schTime += UDateUtil.getYymdJ(yrkModel.getRsyToDate(), reqMdl);
                                schTime += UDateUtil.getSeparateHMJ(yrkModel.getRsyToDate(),
                                        reqMdl);


                                msg = new ActionMessage("error.input.dup.sch",
                                        schTime,
                                        yrkModel.getRsdName(),
                                        yrkModel.getRsyMok());

                                StrutsUtil.addMessage(errors, msg,
                                        "rsv110YrkErr" + String.valueOf(yrkModel.getRsySid()));
                            }
                        }
                    }
                    if (errorFlg) {
                        break;
                    }
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 同時登録スケジュールの重複登録チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param errors アクションエラー
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @param mode 1:NG 2:警告を表示
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateSchRepeatCheck(
            RequestModel reqMdl,
            ActionErrors errors,
            Connection con,
            int sessionUsrSid,
            int mode
            ) throws SQLException {

        //グループスケジュールの場合はチェックを行わない
        if (NullDefault.getInt(userKbn__, 0) == GSConstSchedule.USER_KBN_GROUP) {
            return errors;
        }

        ActionMessage msg = null;

        SchCommonBiz schBiz = new SchCommonBiz(con, reqMdl);
        GsMessage gsMsg = new GsMessage(reqMdl);

        //重複登録 NGスケジュール一覧を取得する。
        List<SchDataModel> rptSchList
                 = __getSchWarningList(
                          sessionUsrSid, con, mode, reqMdl);
        if (rptSchList != null && rptSchList.size() > 0) {
            int i = 1;

            String textSchedule = gsMsg.getMessage("schedule.108");
            msg = new ActionMessage("error.input.dup", textSchedule);
            StrutsUtil.addMessage(errors, msg, "error.input.dup");
            String title = "";
            for (SchDataModel ngMdl : rptSchList) {

                //公開区分で判定してタイトルを取得
                title = schBiz.getDspTitle(ngMdl, sessionUsrSid);

                String schTime = UDateUtil.getYymdJ(ngMdl.getScdFrDate(), reqMdl);
                schTime += UDateUtil.getSeparateHMJ(ngMdl.getScdFrDate(), reqMdl);
                schTime += "～";
                schTime += UDateUtil.getYymdJ(ngMdl.getScdToDate(), reqMdl);
                schTime += UDateUtil.getSeparateHMJ(ngMdl.getScdToDate(), reqMdl);

                msg = new ActionMessage("error.input.dup.sch",
                        schTime,
                        title,
                        ngMdl.getScdUserName());
                StrutsUtil.addMessage(errors, msg, "error.input.dup.sch" + i);
                i++;
            }
        }

        return errors;
    }


    /**
     * <br>[機  能] 重複登録の警告スケジュール一覧を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid ユーザSID
     * @param con コネクション
     * @param mode 1:NG 2:警告を表示
     * @param reqMdl RequestModel
     * @return 警告スケジュールリスト
     * @throws SQLException SQLExceptionm
     */
    private List<SchDataModel> __getSchWarningList(
            int sessionUsrSid,
            Connection con,
            int mode,
            RequestModel reqMdl
            ) throws SQLException {
        List<SchDataModel> rptSchList = new ArrayList<SchDataModel>();

        if (NullDefault.getInt(timeKbn__, GSConstSchedule.TIME_EXIST)
                == GSConstSchedule.TIME_NOT_EXIST) {
            return rptSchList;
        }
        //同時登録メンバー
        String[] sv_users = sameScheduledUser__;

        //個人設定を取得する。
        SchPriConfDao priConfDao = new SchPriConfDao(con);
        SchPriConfModel priModel = priConfDao.select(sessionUsrSid);

        //自分の予定の場合は編集可能フラグ
        SchCommonBiz schBiz = new SchCommonBiz(reqMdl);
        SchRepeatKbnModel repertMdl = schBiz.getRepertKbn(con, priModel, sessionUsrSid);
        boolean mySchOkFlg = repertMdl.getRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG
                            && repertMdl.getRepeatMyKbn() == GSConstSchedule.SCH_REPEAT_MY_KBN_OK;
        //ユーザリストを作成
        List<Integer> usrList = new ArrayList<Integer>();
        if (sv_users != null && sv_users.length > 0) {
            for (int i = 0; i < sv_users.length; i++) {
                if (mySchOkFlg && sessionUsrSid == Integer.parseInt(sv_users[i])) {
                    continue;
                }
                usrList.add(Integer.parseInt(sv_users[i]));
            }
        }

        //複写フラグ
        String copyFlg
        = NullDefault.getString(copyFlg__, GSConstSchedule.NOT_COPY_FLG);

        //ユーザリストに被登録者を含める
        int targetSid = __getTargetSid(con, sessionUsrSid);
        if (!mySchOkFlg || sessionUsrSid != targetSid) {
            usrList.add(targetSid);
        }

        int frYear = frDate__.getYear();
        int frMonth = frDate__.getMonth();
        int frDay = frDate__.getIntDay();

        int frHour = frDate__.getIntHour();
        int frMin = frDate__.getIntMinute();

        int toYear = toDate__.getYear();
        int toMonth = toDate__.getMonth();
        int toDay = toDate__.getIntDay();


        int toHour = toDate__.getIntHour();
        int toMin = toDate__.getIntMinute();
        int toSec = GSConstSchedule.DAY_START_SECOND;
        int toMiliSec = GSConstSchedule.DAY_START_MILLISECOND;


        //予約開始
        UDate chkFrDate = new UDate();
        chkFrDate.setDate(frYear, frMonth, frDay);
        chkFrDate.setHour(frHour);
        chkFrDate.setMinute(frMin);
        chkFrDate.setSecond(GSConstSchedule.DAY_START_SECOND);
        chkFrDate.setMilliSecond(GSConstSchedule.DAY_START_MILLISECOND);

        //予約終了
        UDate chkToDate = new UDate();
        chkToDate.setDate(toYear, toMonth, toDay);
        chkToDate.setHour(toHour);
        chkToDate.setMinute(toMin);
        chkToDate.setSecond(toSec);
        chkToDate.setMilliSecond(toMiliSec);


        //編集スケジュールSID
        int schSid = NullDefault.getInt(schSid__, 0);

        SchDataDao schDao = new SchDataDao(con);
        int schGrpSid = -1;
        int batchRef = NullDefault.getInt(batchRef__, 1);

        if (batchRef == 1) {
            //同時修正する場合

            SchDataModel bean = new SchDataModel();
            bean.setScdSid(schSid);
            SchDataModel schModel = schDao.select(bean);

            if (schModel != null) {
                schGrpSid = schModel.getScdGrpSid();
            }
        }

        SchAdmConfModel admConf = schBiz.getAdmConfModel(con);
        boolean canEditRepeatKbn = schBiz.canEditRepertKbn(admConf);
        if (mode == GSConstSchedule.SCH_REPEAT_KBN_NG) {
            List<Integer> ngUsrList = null;
            if (canEditRepeatKbn) {
                //重複登録不可にしているユーザリストを取得
                ngUsrList = priConfDao.getUsrSidListRepeatKbn(usrList,
                                                            GSConstSchedule.SCH_REPEAT_KBN_NG);
            } else {
                if (admConf.getSadRepeatKbn() == GSConstSchedule.SCH_REPEAT_KBN_NG) {
                    ngUsrList = new ArrayList<Integer>();
                    ngUsrList.addAll(usrList);
                }
            }
            if (ngUsrList != null && !ngUsrList.isEmpty()) {
                //重複登録しているスケジュール一覧を取得する。
                rptSchList =
                    schDao.getSchData(ngUsrList, schSid, chkFrDate, chkToDate, schGrpSid, copyFlg);
            }

        } else if (mode == GSConstSchedule.SCH_REPEAT_KBN_WARNING) {

            //重複登録警告にしているユーザリストを取得
            List<Integer> warningUsrList = null;
            if (canEditRepeatKbn) {
                warningUsrList = priConfDao.getUsrSidListRepeatKbn(usrList,
                                                            GSConstSchedule.SCH_REPEAT_KBN_WARNING);
            } else {
                warningUsrList = new ArrayList<Integer>();
                if (admConf.getSadRepeatKbn() != GSConstSchedule.SCH_REPEAT_KBN_OK) {
                    warningUsrList.addAll(usrList);
                }
            }

            //セッションユーザをチェックに含める
            if (mySchOkFlg
                    && sessionUsrSid == targetSid) {
                warningUsrList.add(targetSid);
            }

            if (warningUsrList != null && !warningUsrList.isEmpty()) {
                //重複登録しているスケジュール一覧を取得する。
                rptSchList = schDao.getSchData(
                        warningUsrList, schSid, chkFrDate, chkToDate, schGrpSid, copyFlg);
            }
        }

        return rptSchList;
    }
    /**
     * 指定した施設SIDを配列から検索します。
     * <br>[機  能]配列に存在する場合はRsvSisYrkModelを返します。
     * <br>[解  説]配列に存在しない場合はnullを返します。
     * <br>[備  考]
     * @param yrkList 本スケジュールろ同時登録された施設予約情報リスト
     * @param rsdSid 検索される施設SID
     * @return RsvSisYrkModel
     */
    public RsvSisYrkModel getReserveData(ArrayList<RsvSisYrkModel> yrkList, int rsdSid) {
        RsvSisYrkModel ret = null;
        if (yrkList == null) {
            return ret;
        }

        for (RsvSisYrkModel yrkMdl : yrkList) {
            if (yrkMdl.getRsdSid() == rsdSid) {
                return yrkMdl;
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] アクセス権限のない施設数を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param exSid 拡張SID
     * @param sessionUsrSid ユーザSID
     * @param con コネクション
     * @param rsvAdmin 施設予約管理者
     * @return count 施設数
     * @throws SQLException SQLExceptionm
     */
    public int getCanNotEditRsvCountEx(
            int exSid,
            int sessionUsrSid,
            Connection con,
            boolean rsvAdmin
            ) throws SQLException {
        int count = 0;

        if (rsvAdmin) {
            return count;
        }

        ScheduleReserveDao schRsvDao = new ScheduleReserveDao(con);

        ArrayList<Integer> allRsdList = schRsvDao.getScheduleReserveDataFromExSid(exSid);
        if (allRsdList == null || allRsdList.size() == 0) {
            return count;
        }

        //施設SIDリストを取得
        ArrayList<Integer> rsdList
            = schRsvDao.getCanEditScheduleReserveDataFromExSid(exSid, sessionUsrSid);

        if (rsdList.size() == allRsdList.size()) {
            return count;
        }

        for (Integer rsdSid : allRsdList) {
            if (!rsdList.contains(rsdSid)) {
                count++;
            }
        }

        return count;
    }

    /**
     * <br>[機  能] 登録対象者のSIDを取得する
     * <br>[解  説]
     * <br>[備  考] 登録対象者ID(targetId)が設定されている、かつ該当するユーザ/グループが存在しない場合は-1を返す
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @return 登録対象者のSID
     * @throws SQLExcpetion SQL実行時例外
     */
    private int __getTargetSid(Connection con, int sessionUsrSid) throws SQLException {
        ApiSchEditBiz biz = new ApiSchEditBiz();
        return biz.getTargetSid(con, userKbn__, usrSid__, targetId__, sessionUsrSid);
    }

    /**
     * <p>attendKbn を取得します。
     * @return attendKbn
     */
    public String getAttendKbn() {
        return attendKbn__;
    }
    /**
     * <p>attendKbn をセットします。
     * @param attendKbn attendKbn
     */
    public void setAttendKbn(String attendKbn) {
        attendKbn__ = attendKbn;
    }
    /**
     * <p>attendMailResendKbn を取得します。
     * @return attendMailResendKbn
     */
    public String getAttendMailResendKbn() {
        return attendMailResendKbn__;
    }
    /**
     * <p>attendMailResendKbn をセットします。
     * @param attendMailResendKbn attendMailResendKbn
     */
    public void setAttendMailResendKbn(String attendMailResendKbn) {
        attendMailResendKbn__ = attendMailResendKbn;
    }
    /**
     * <p>targetGrp を取得します。
     * @return targetGrp
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditForm#targetGrp__
     */
    public String getTargetGrp() {
        return targetGrp__;
    }
    /**
     * <p>targetGrp をセットします。
     * @param targetGrp targetGrp
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditForm#targetGrp__
     */
    public void setTargetGrp(String targetGrp) {
        targetGrp__ = targetGrp;
    }
    /**
     * <p>reminder を取得します。
     * @return reminder
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditForm#reminder__
     */
    public String getReminder() {
        return reminder__;
    }
    /**
     * <p>reminder をセットします。
     * @param reminder reminder
     * @see jp.groupsession.v2.api.schedule.edit.ApiSchEditForm#reminder__
     */
    public void setReminder(String reminder) {
        reminder__ = reminder;
    }

}
