package jp.groupsession.v2.sch.sch091;

import java.lang.reflect.InvocationTargetException;
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
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.SchEnumReminderTime;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchColMsgDao;
import jp.groupsession.v2.sch.dao.SchInitPubDao;
import jp.groupsession.v2.sch.dao.SchPriConfDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchInitPubModel;
import jp.groupsession.v2.sch.model.SchPriConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] スケジュール 個人設定 初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch091Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sch091Biz.class);
    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch091Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch091ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param con コネクション
     * @throws Exception 実行エラー
     */
    public void setInitData(Sch091ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws Exception {

        SchCommonBiz biz = new SchCommonBiz(reqMdl__);

        //管理者設定を取得
        SchAdmConfModel admMdl = new SchAdmConfModel();
        admMdl = biz.getAdmConfModel(con);
        paramMdl.setSch091colorKbn(admMdl.getSadMsgColorKbn());

        //DBより設定情報を取得。なければデフォルト値とする。
        if (paramMdl.getSch091initFlg() != 1) {
            SchPriConfModel pconf = biz.getSchPriConfModel(con, umodel.getUsrsid());
            UDate ifr = pconf.getSccIniFrDate();
            UDate ito = pconf.getSccIniToDate();

            //開始 時
            log__.debug("開始 時 = " + ifr.getIntHour());
            paramMdl.setSch091DefFrH(ifr.getIntHour());
            //開始 分
            log__.debug("開始 分 = " + ifr.getIntMinute());
            paramMdl.setSch091DefFrM(ifr.getIntMinute());
            //終了 時
            log__.debug("終了 時 = " + ito.getIntHour());
            paramMdl.setSch091DefToH(ito.getIntHour());
            //終了 分
            log__.debug("終了 分 = " + ito.getIntMinute());
            paramMdl.setSch091DefToM(ito.getIntMinute());

            //公開フラグ
            paramMdl.setSch091PubFlg(pconf.getSccIniPublic());

            if (paramMdl.getSch091PubFlg() == GSConstSchedule.DSP_USRGRP) {
                SchInitPubDao sipDao = new SchInitPubDao(con);
                ArrayList<String> usrGrpList = new ArrayList<String>();
                List<SchInitPubModel> sipList = sipDao.select(umodel.getUsrsid());
                for (SchInitPubModel sipMdl : sipList) {
                    if (sipMdl.getSipType() == GSConst.TYPE_GROUP) {
                        usrGrpList.add("G" + String.valueOf(sipMdl.getSipPsid()));
                    } else {
                        usrGrpList.add(String.valueOf(sipMdl.getSipPsid()));
                    }
                }
                String[] sidList = usrGrpList.toArray(new String[0]);
                paramMdl.setSch091PubUsrGrpSid(sidList);
            }

            //編集権限
            paramMdl.setSch091Edit(pconf.getSccIniEdit());
            //タイトルカラー
            paramMdl.setSch091Fcolor(String.valueOf(
                    biz.getUserColor(pconf.getSccIniFcolor(), con)));
            //同時修正
            paramMdl.setSch091Same(pconf.getSccIniSame());

            //リマインダー通知
            int usrSid = umodel.getUsrsid();
            SchPriConfModel spcMdl = biz.getSchPriConfModel(con, usrSid);
            paramMdl.setSch091ReminderTime(String.valueOf(spcMdl.getSccReminder()));

            //トップメニュー設定
            paramMdl.setSch091DefDsp(pconf.getSccDefDsp());

            paramMdl.setSch091initFlg(1);
        }

        //画面表示情報を設定
        setDspData(paramMdl, con);

        _setUserCombo(con, paramMdl);

        //タイトルカラーコメント
        setTitleColorMsg(paramMdl, con);

        //通知時間リストを設定
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setReminderTimeList(
                SchEnumReminderTime.labelList(gsMsg));
    }

    /**
     * <br>[機  能] 画面表示に必要な情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public void setDspData(Sch091ParamModel paramMdl, Connection con) throws
        SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        //管理者設定を取得
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel admMdl = new SchAdmConfModel();
        admMdl = biz.getAdmConfModel(con);
        paramMdl.setSch091colorKbn(admMdl.getSadMsgColorKbn());

        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl__);
        int hourMemCount = schCmnBiz.getDayScheduleHourMemoriMin(con);
        paramMdl.setSch091HourDiv(hourMemCount);

        //開始・終了時間を設定
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getSch091DefFrTime() == null) {
            dateBiz.setTimeParam(paramMdl, "sch091DefFrTime", "sch091DefFrH", "sch091DefFrM", null);
        }
        if (paramMdl.getSch091DefToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "sch091DefToTime", "sch091DefToH", "sch091DefToM", null);
        }

        //グループコンボを設定
        SchCommonBiz schBiz = new SchCommonBiz();
        paramMdl.setSch091PubGroupCombo(schBiz.getGroupLabelList(con, reqMdl__));
        //使用者 グループコンボ、ユーザコンボを設定
        _setUserCombo(con, paramMdl);

        //時間、編集権限、公開区分 編集許可を設定
        paramMdl.setSch091TimeFlg(schCmnBiz.canTimeInitConf(con));
        paramMdl.setSch091EditFlg(schCmnBiz.canEditInitConf(con));
        paramMdl.setSch091PublicFlg(schCmnBiz.canPubInitConf(con));
        paramMdl.setSch091SameFlg(schCmnBiz.canSameInitConf(con));
    }

    /**
     * タイトルカラーコメントを設定します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl フォーム
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setTitleColorMsg(Sch091ParamModel paramMdl, Connection con) throws SQLException {

        SchCommonBiz biz = new SchCommonBiz(reqMdl__);

        //管理者設定を取得
        SchAdmConfModel admMdl = new SchAdmConfModel();
        admMdl = biz.getAdmConfModel(con);
        //タイトル色区分の設定
        paramMdl.setSch091colorKbn(admMdl.getSadMsgColorKbn());

        //タイトルカラーコメント
        SchColMsgDao msgDao = new SchColMsgDao(con);
        ArrayList<String> msgList = msgDao.selectMsg();
        paramMdl.setSch091ColorMsgList(msgList);
    }

    /**
     * <br>[機  能] 設定された個人設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sch091ParamModel
     * @param umodel ユーザ基本情報モデル
     * @param sacMdl スケジュール管理者設定情報モデル
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setPconfSetting(Sch091ParamModel paramMdl, BaseUserModel umodel,
            SchAdmConfModel sacMdl, Connection con) throws Exception {

        //DBより設定情報を取得。なければデフォルト値とする。
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        int userSid = umodel.getUsrsid();
        SchPriConfModel pconf = biz.getSchPriConfModel(con, userSid);

        if (sacMdl.getSadIniTimeStype() == GSConstSchedule.SAD_INITIME_STYPE_USER) {
            //開始時刻
            UDate fromUd = new UDate();
            fromUd.setHour(paramMdl.getSch091DefFrH());
            fromUd.setMinute(paramMdl.getSch091DefFrM());
            //終了時刻
            UDate toUd = new UDate();
            toUd.setHour(paramMdl.getSch091DefToH());
            toUd.setMinute(paramMdl.getSch091DefToM());

            pconf.setSccIniFrDate(fromUd);
            pconf.setSccIniToDate(toUd);
        }
        if (sacMdl.getSadInitPublicStype() == GSConstSchedule.SAD_INIPUBLIC_STYPE_USER) {
            switch (paramMdl.getSch091PubFlg()) {
            case GSConstSchedule.DSP_PUBLIC:
            case GSConstSchedule.DSP_NOT_PUBLIC:
            case GSConstSchedule.DSP_YOTEIARI:
            case GSConstSchedule.DSP_BELONG_GROUP:
            case GSConstSchedule.DSP_USRGRP:
            case GSConstSchedule.DSP_TITLE:
                pconf.setSccIniPublic(paramMdl.getSch091PubFlg());
                break;
            default:
                pconf.setSccIniPublic(GSConstSchedule.DSP_PUBLIC);
            }
        }
        if (sacMdl.getSadIniEditStype() == GSConstSchedule.SAD_INIEDIT_STYPE_USER) {
            pconf.setSccIniEdit(paramMdl.getSch091Edit());
        }
        if (sacMdl.getSadIniSameStype() == GSConstSchedule.SAD_INISAME_STYPE_USER) {
            pconf.setSccIniSame(paramMdl.getSch091Same());
        }
        pconf.setSccEuid(userSid);
        pconf.setSccEdate(new UDate());
        pconf.setSccIniFcolor(Integer.parseInt(paramMdl.getSch091Fcolor()));
        pconf.setSccReminder(Integer.parseInt(paramMdl.getSch091ReminderTime()));
        pconf.setSccDefDsp(paramMdl.getSch091DefDsp());

        //編集前の公開対象ユーザ・グループを削除
        SchInitPubDao sipDao = new SchInitPubDao(con);
        if (sacMdl.getSadInitPublicStype() == GSConstSchedule.SAD_INIPUBLIC_STYPE_USER) {
            sipDao.delete(userSid);

            //公開区分が指定ユーザグループのみ公開の場合
            if (paramMdl.getSch091PubFlg() == GSConstSchedule.DSP_USRGRP) {
                UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                UserBiz userBiz = new UserBiz();

                ArrayList<Integer> grpSids = new ArrayList<Integer>();
                List<String> usrSids = new ArrayList<String>();
                for (String target : paramMdl.getSch091PubUsrGrpSid()) {
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
                    ulist = userBiz.getBaseUserList(con,
                                                    usrSids.toArray(new String[usrSids.size()]));
                }
                SchInitPubModel sipMdl = new SchInitPubModel();
                for (GroupModel gMdl : glist) {
                    sipMdl.setSipPsid(gMdl.getGroupSid());
                    sipMdl.setSipType(GSConst.TYPE_GROUP);
                    sipMdl.setUsrSid(userSid);
                    sipDao.insert(sipMdl);
                }
                for (BaseUserModel uMdl : ulist) {
                    sipMdl.setSipPsid(uMdl.getUsrsid());
                    sipMdl.setSipType(GSConst.TYPE_USER);
                    sipMdl.setUsrSid(userSid);
                    sipDao.insert(sipMdl);
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
                paramMdl.setSch091PubUsrGrpSid(checkPubUsrGrpSid);
            }
        }

        boolean commitFlg = false;
        try {
            SchPriConfDao dao = new SchPriConfDao(con);
            int count = dao.updateInitData(pconf);
            if (count <= 0) {
                //レコードがない場合は作成
                dao.insert(pconf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }

    /**
     * <br>[機  能] ユーザコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setUserCombo(Connection con, Sch091ParamModel paramMdl)
            throws SQLException {
        String[] selectUserSid = paramMdl.getSch091PubUsrGrpSid();
        if (selectUserSid == null) {
            selectUserSid = new String[0];
        }
        //デフォルトユーザを設定
        if (paramMdl.getSch091PubDefUsrSid() > 0) {
            boolean defFlg = false;
            ArrayList<String> usrSidList = new ArrayList<String>();
            for (String usid : selectUserSid) {
                usrSidList.add(usid);
                if (usid.equals(String.valueOf(paramMdl.getSch091PubDefUsrSid()))) {
                    defFlg = true;
                }
            }
            if (!defFlg) {
                usrSidList.add(String.valueOf(paramMdl.getSch091PubDefUsrSid()));
            }
            paramMdl.setSch091PubUsrGrpSid(
                    (String[]) usrSidList.toArray(new String[usrSidList.size()]));
        }
    }
}