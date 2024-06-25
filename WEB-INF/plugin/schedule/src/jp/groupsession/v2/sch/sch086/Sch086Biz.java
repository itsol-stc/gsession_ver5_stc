package jp.groupsession.v2.sch.sch086;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.sch.biz.SchCommonBiz;
import jp.groupsession.v2.sch.dao.SchAdmConfDao;
import jp.groupsession.v2.sch.dao.SchInitPubDao;
import jp.groupsession.v2.sch.model.SchAdmConfModel;
import jp.groupsession.v2.sch.model.SchInitPubModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール 管理者設定 スケジュール初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class Sch086Biz {

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Sch086Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Sch086ParamModel
     * @throws SQLException SQL実行例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public void setInitData(Connection con, Sch086ParamModel paramMdl) throws SQLException,
        IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        SchCommonBiz schCmnBiz = new SchCommonBiz(reqMdl__);
        int hourMemCount = schCmnBiz.getDayScheduleHourMemoriMin(con);
        paramMdl.setSch086HourDiv(hourMemCount);

        //グループコンボを設定
        SchCommonBiz schBiz = new SchCommonBiz();
        paramMdl.setSch086PubGroupCombo(schBiz.getGroupLabelList(con, reqMdl__));
        //使用者 グループコンボ、ユーザコンボを設定
        _setUserCombo(con, paramMdl);

        if (paramMdl.getSch086init() == 0) {
            //DBより現在の設定を取得する。(なければデフォルト)
            SchCommonBiz biz = new SchCommonBiz(reqMdl__);
            SchAdmConfModel conf = biz.getAdmConfModel(con);
            paramMdl.setSch086TimeType(conf.getSadIniTimeStype());
            paramMdl.setSch086DefFrH(conf.getSadIniFrH());
            paramMdl.setSch086DefFrM(conf.getSadIniFrM());
            paramMdl.setSch086DefToH(conf.getSadIniToH());
            paramMdl.setSch086DefToM(conf.getSadIniToM());
            paramMdl.setSch086EditType(conf.getSadIniEditStype());
            paramMdl.setSch086Edit(conf.getSadIniEdit());
            paramMdl.setSch086PublicType(conf.getSadInitPublicStype());
            paramMdl.setSch086Public(conf.getSadIniPublic());
            paramMdl.setSch086SameType(conf.getSadIniSameStype());
            paramMdl.setSch086Same(conf.getSadIniSame());

            if (paramMdl.getSch086Public() == GSConstSchedule.DSP_USRGRP) {
                SchInitPubDao sipDao = new SchInitPubDao(con);
                ArrayList<String> usrGrpList = new ArrayList<String>();
                List<SchInitPubModel> sipList = sipDao.select(GSConst.SYSTEM_USER_ADMIN);
                for (SchInitPubModel sipMdl : sipList) {
                    if (sipMdl.getSipType() == GSConst.TYPE_GROUP) {
                        usrGrpList.add("G" + String.valueOf(sipMdl.getSipPsid()));
                    } else {
                        usrGrpList.add(String.valueOf(sipMdl.getSipPsid()));
                    }
                }
                String[] sidList = usrGrpList.toArray(new String[0]);
                paramMdl.setSch086PubUsrGrpSid(sidList);
                _setUserCombo(con, paramMdl);
            }
            paramMdl.setSch086init(1);
        }

        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getSch086DefFrTime() == null) {
            dateBiz.setTimeParam(paramMdl, "sch086DefFrTime", "sch086DefFrH", "sch086DefFrM", null);
        }

        if (paramMdl.getSch086DefToTime() == null) {
            dateBiz.setTimeParam(paramMdl, "sch086DefToTime", "sch086DefToH", "sch086DefToM", null);
        }
    }

    /**
     * <br>[機  能] 時間、編集権限、公開区分初期値設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Sch086ParamModel
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void entryKbn(Connection con, Sch086ParamModel paramMdl,
                            int sessionUserSid)
        throws SQLException {
        SchCommonBiz biz = new SchCommonBiz(reqMdl__);
        SchAdmConfModel conf = biz.getAdmConfModel(con);

        //時間区分
        if (paramMdl.getSch086TimeType() == GSConstSchedule.SAD_INITIME_STYPE_ADM) {
            conf.setSadIniTimeStype(paramMdl.getSch086TimeType());
            conf.setSadIniFrH(paramMdl.getSch086DefFrH());
            conf.setSadIniFrM(paramMdl.getSch086DefFrM());
            conf.setSadIniToH(paramMdl.getSch086DefToH());
            conf.setSadIniToM(paramMdl.getSch086DefToM());
        } else {
            conf.setSadIniTimeStype(GSConstSchedule.SAD_INITIME_STYPE_USER);
        }

        //編集区分
        if (paramMdl.getSch086EditType() == GSConstSchedule.SAD_INIEDIT_STYPE_ADM) {
            conf.setSadIniEditStype(paramMdl.getSch086EditType());
        } else {
            conf.setSadIniEditStype(GSConstSchedule.SAD_INIEDIT_STYPE_USER);
        }

        switch (paramMdl.getSch086Edit()) {
            case GSConstSchedule.EDIT_CONF_NONE:
            case GSConstSchedule.EDIT_CONF_OWN:
            case GSConstSchedule.EDIT_CONF_GROUP:
                conf.setSadIniEdit(paramMdl.getSch086Edit());
                break;
            default:
                conf.setSadIniEdit(GSConstSchedule.EDIT_CONF_NONE);
        }

        //公開区分
        if (paramMdl.getSch086PublicType() == GSConstSchedule.SAD_INIPUBLIC_STYPE_ADM) {
            conf.setSadInitPublicStype(paramMdl.getSch086PublicType());
        } else {
            conf.setSadInitPublicStype(GSConstSchedule.SAD_INIPUBLIC_STYPE_USER);
        }

        switch (paramMdl.getSch086Public()) {
            case GSConstSchedule.DSP_PUBLIC:
            case GSConstSchedule.DSP_NOT_PUBLIC:
            case GSConstSchedule.DSP_YOTEIARI:
            case GSConstSchedule.DSP_BELONG_GROUP:
            case GSConstSchedule.DSP_USRGRP:
            case GSConstSchedule.DSP_TITLE:
                conf.setSadIniPublic(paramMdl.getSch086Public());
                break;
            default:
                conf.setSadIniPublic(GSConstSchedule.DSP_PUBLIC);
        }

        //編集前の公開対象ユーザ・グループを削除
        SchInitPubDao sipDao = new SchInitPubDao(con);
        sipDao.delete(GSConst.SYSTEM_USER_ADMIN);

        //公開区分が指定ユーザグループのみ公開の場合
        if (paramMdl.getSch086Public() == GSConstSchedule.DSP_USRGRP) {
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
            UserBiz userBiz = new UserBiz();

            ArrayList<Integer> grpSids = new ArrayList<Integer>();
            List<String> usrSids = new ArrayList<String>();
            for (String target : paramMdl.getSch086PubUsrGrpSid()) {
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
                sipMdl.setUsrSid(GSConst.SYSTEM_USER_ADMIN);
                sipDao.insert(sipMdl);
            }
            for (BaseUserModel uMdl : ulist) {
                sipMdl.setSipPsid(uMdl.getUsrsid());
                sipMdl.setSipType(GSConst.TYPE_USER);
                sipMdl.setUsrSid(GSConst.SYSTEM_USER_ADMIN);
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
            paramMdl.setSch086PubUsrGrpSid(checkPubUsrGrpSid);
        }

        //同時編集区分
        if (paramMdl.getSch086SameType() == GSConstSchedule.SAD_INISAME_STYPE_ADM) {
            conf.setSadIniSameStype(paramMdl.getSch086SameType());
        } else {
            conf.setSadIniSameStype(GSConstSchedule.SAD_INISAME_STYPE_USER);
        }

        switch (paramMdl.getSch086Same()) {
            case GSConstSchedule.SAME_EDIT_ON:
            case GSConstSchedule.SAME_EDIT_OFF:
                conf.setSadIniSame(paramMdl.getSch086Same());
                break;
            default:
                conf.setSadIniSame(GSConstSchedule.SAME_EDIT_ON);
        }

        //スケジュール 初期値を登録
        conf.setSadEuid(sessionUserSid);
        conf.setSadEdate(new UDate());
        SchAdmConfDao dao = new SchAdmConfDao(con);
        if (dao.updateInitSetting(conf) == 0) {
            conf.setSadAuid(sessionUserSid);
            conf.setSadAdate(conf.getSadEdate());
            dao.insert(conf);
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
    protected void _setUserCombo(Connection con, Sch086ParamModel paramMdl)
            throws SQLException {

        String[] selectUserSid = paramMdl.getSch086PubUsrGrpSid();
        if (selectUserSid == null) {
            selectUserSid = new String[0];
        }

        //デフォルトユーザを設定
        if (paramMdl.getSch086PubDefUsrSid() > 0) {
            boolean defFlg = false;
            ArrayList<String> usrSidList = new ArrayList<String>();
            for (String usid : selectUserSid) {
                usrSidList.add(usid);
                if (usid.equals(String.valueOf(paramMdl.getSch086PubDefUsrSid()))) {
                    defFlg = true;
                }
            }
            if (!defFlg) {
                usrSidList.add(String.valueOf(paramMdl.getSch086PubDefUsrSid()));
            }
            paramMdl.setSch086PubUsrGrpSid(
                    (String[]) usrSidList.toArray(new String[usrSidList.size()]));
        }
    }
}