package jp.groupsession.v2.rsv.rsv280;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rsv.dao.RsvAdmConfDao;
import jp.groupsession.v2.rsv.model.RsvAdmConfModel;
import jp.groupsession.v2.rsv.rsv040.Rsv040Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 施設予約 管理者設定 施設予約初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv280Form extends Rsv040Form implements ISelectorUseForm {

    /** 期間 区分 */
    private int rsv280PeriodKbn__ = GSConstReserve.RAC_INIPERIODKBN_USER;
    /** 開始 時 */
    private int rsv280DefFrH__ = 9;
    /** 開始 分 */
    private int rsv280DefFrM__ = 0;
    /** 終了 時 */
    private int rsv280DefToH__ = 18;
    /** 終了 分 */
    private int rsv280DefToM__ = 0;
    /** 開始時間 */
    private String rsv280DefFrTime__ = null;
    /** 開始時間 */
    private String rsv280DefToTime__ = null;
    /** 分単位 */
    private int rsv280HourDiv__ = 0;
    
    /** 編集権限 区分 */
    private int rsv280EditKbn__ = GSConstReserve.RAC_INIEDITKBN_USER;
    /** 編集権限 */
    private int rsv280Edit__ = GSConstReserve.EDIT_AUTH_NONE;
    /** 公開区分 区分 */
    private int rsv280PublicKbn__ = GSConstReserve.RAC_INIEDITKBN_USER;
    /** 公開区分 */
    private int rsv280Public__ = GSConstReserve.PUBLIC_KBN_ALL;
    /** 初期表示フラグ */
    private int rsv280initFlg__ = 0;

    /** デフォルトユーザSID  */
    private int rsv280PubDefUsrSid__ = 0;
    /** 公開対象 ユーザ グループ */
    private String rsv280PubUserGroup__ =
            String.valueOf(GSConst.GROUP_COMBO_VALUE);
    /** 公開対象ユーザ・グループSID */
    private String[] rsv280PubUsrGrpSid__ = new String[0];
    /** 公開対象ユーザ・グループ UI */
    private UserGroupSelector rsv280PubUsrGrpUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("main.exposed", null))
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(
                    Select.builder()
                    .chainLabel(new GsMessageReq("main.exposed", null))
                    .chainParameterName(
                            "rsv280PubUsrGrpSid")
                )
            .chainGroupSelectionParamName("rsv280PubUserGroup")
            .build();

    /**
     * <br>[機  能] 時間のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public ActionErrors validateTimeRsv280(Connection con, HttpServletRequest req)
            throws SQLException, IllegalAccessException,
                InvocationTargetException, NoSuchMethodException {
        
        ActionErrors errors = new ActionErrors();
        if (rsv280PeriodKbn__ == GSConstReserve.RAC_INIPERIODKBN_USER) {
            return errors;
        }
        
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        
        int errorSize = errors.size();
        String startTime = gsMsg.getMessage("cmn.starttime");
        String endTime = gsMsg.getMessage("cmn.endtime");
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        errors.add(dateBiz.setHmParam(this, "rsv280DefFrTime",
                "rsv280DefFrH", "rsv280DefFrM", startTime));
        errors.add(dateBiz.setHmParam(this, "rsv280DefToTime",
                "rsv280DefToH", "rsv280DefToM", endTime));
        
        if (errorSize != errors.size()) {
            return errors;
        }
        
        RsvAdmConfDao aconfDao = new RsvAdmConfDao(con);
        RsvAdmConfModel aconfModel = aconfDao.select();
        int hourDiv = GSConstReserve.DF_HOUR_DIVISION;
        if (aconfModel != null) {
            hourDiv = aconfModel.getRacHourDiv();
        }
        
        if (rsv280DefFrM__ % hourDiv != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", startTime,
                    gsMsg.getMessageVal0("cmn.minute.periods", String.valueOf(hourDiv)));
            errors.add("error.input.comp.text", msg);
        }
        
        if (rsv280DefToM__ % hourDiv != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", endTime,
                    gsMsg.getMessageVal0("cmn.minute.periods", String.valueOf(hourDiv)));
            errors.add("error.input.comp.text", msg);
        }

        UDate startDate = new UDate();
        startDate.setZeroDdHhMmSs();
        startDate.setHour(rsv280DefFrH__);
        startDate.setMinute(rsv280DefFrM__);

        UDate endDate = new UDate();
        endDate.setZeroDdHhMmSs();
        endDate.setHour(rsv280DefToH__);
        endDate.setMinute(rsv280DefToM__);
        
        if (startDate.compareDateYMDHM(endDate) == UDate.SMALL) {
            msg = new ActionMessage("error.input.comp.text",
                    gsMsg.getMessage("cmn.period"),
                    startTime + " < " + endTime);
            errors.add("error.input.comp.text", msg);
        }

        return errors;
    }
    

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con DBコネクション
     * @param req リクエスト
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateRsv280(Connection con, HttpServletRequest req) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        if (rsv280PublicKbn__ == GSConstReserve.RAC_INIEDITKBN_ADM
        && rsv280Public__ == GSConstReserve.PUBLIC_KBN_USRGRP) {

            //存在しないユーザを除外する
            ArrayList<GroupModel> glist = new ArrayList<GroupModel>();
            ArrayList<BaseUserModel> ulist = new ArrayList<BaseUserModel>();
            if (rsv280PubUsrGrpSid__ != null && rsv280PubUsrGrpSid__.length > 0) {
                ArrayList<Integer> grpSids = new ArrayList<Integer>();
                List<String> usrSids = new ArrayList<String>();
                for (String target : rsv280PubUsrGrpSid__) {
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

                if (!grpSids.isEmpty()) {
                    UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
                    glist = gdao.selectGroupNmListOrderbyClass(grpSids);
                }
                if (!usrSids.isEmpty()) {
                    UserBiz userBiz = new UserBiz();
                    ulist = userBiz.getBaseUserList(con,
                                                    usrSids.toArray(new String[usrSids.size()]));
                }
            }

            if (glist.isEmpty() && ulist.isEmpty()) {
                String taisyou = gsMsg.getMessage("reserve.190");
                msg = new ActionMessage(
                        "error.select.required.text", taisyou);
                errors.add("error.select.required.text", msg);
            }
        }

        return errors;
    }

    /**
     * rsv280PeriodKbnを取得します。
     * @return rsv280PeriodKbn
     * */
    public int getRsv280PeriodKbn() {
        return rsv280PeriodKbn__;
    }
    /**
     * rsv280PeriodKbnをセットします。
     * @param rsv280PeriodKbn rsv280PeriodKbn
     */
    public void setRsv280PeriodKbn(int rsv280PeriodKbn) {
        rsv280PeriodKbn__ = rsv280PeriodKbn;
    }
    /**
     * <p>rsv280DefFrH を取得します。
     * @return rsv280DefFrH
     */
    public int getRsv280DefFrH() {
        return rsv280DefFrH__;
    }
    /**
     * <p>rsv280DefFrH をセットします。
     * @param rsv280DefFrH rsv280DefFrH
     */
    public void setRsv280DefFrH(int rsv280DefFrH) {
        rsv280DefFrH__ = rsv280DefFrH;
    }
    /**
     * <p>rsv280DefFrM を取得します。
     * @return rsv280DefFrM
     */
    public int getRsv280DefFrM() {
        return rsv280DefFrM__;
    }
    /**
     * <p>rsv280DefFrM をセットします。
     * @param rsv280DefFrM rsv280DefFrM
     */
    public void setRsv280DefFrM(int rsv280DefFrM) {
        rsv280DefFrM__ = rsv280DefFrM;
    }
    /**
     * <p>rsv280DefToH を取得します。
     * @return rsv280DefToH
     */
    public int getRsv280DefToH() {
        return rsv280DefToH__;
    }
    /**
     * <p>rsv280DefToH をセットします。
     * @param rsv280DefToH rsv280DefToH
     */
    public void setRsv280DefToH(int rsv280DefToH) {
        rsv280DefToH__ = rsv280DefToH;
    }
    /**
     * <p>rsv280DefToM を取得します。
     * @return rsv280DefToM
     */
    public int getRsv280DefToM() {
        return rsv280DefToM__;
    }
    /**
     * <p>rsv280DefToM をセットします。
     * @param rsv280DefToM rsv280DefToM
     */
    public void setRsv280DefToM(int rsv280DefToM) {
        rsv280DefToM__ = rsv280DefToM;
    }
    
    /**
     * <p>rsv280DefFrTime を取得します。
     * @return rsv280DefFrTime
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280DefFrTime__
     */
    public String getRsv280DefFrTime() {
        return rsv280DefFrTime__;
    }

    /**
     * <p>rsv280DefFrTime をセットします。
     * @param rsv280DefFrTime rsv280DefFrTime
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280DefFrTime__
     */
    public void setRsv280DefFrTime(String rsv280DefFrTime) {
        rsv280DefFrTime__ = rsv280DefFrTime;
    }

    /**
     * <p>rsv280DefToTime を取得します。
     * @return rsv280DefToTime
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280DefToTime__
     */
    public String getRsv280DefToTime() {
        return rsv280DefToTime__;
    }

    /**
     * <p>rsv280DefToTime をセットします。
     * @param rsv280DefToTime rsv280DefToTime
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280DefToTime__
     */
    public void setRsv280DefToTime(String rsv280DefToTime) {
        rsv280DefToTime__ = rsv280DefToTime;
    }
    
    /**
     * <p>rsv280HourDiv を取得します。
     * @return rsv280HourDiv
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280HourDiv__
     */
    public int getRsv280HourDiv() {
        return rsv280HourDiv__;
    }

    /**
     * <p>rsv280HourDiv をセットします。
     * @param rsv280HourDiv rsv280HourDiv
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280HourDiv__
     */
    public void setRsv280HourDiv(int rsv280HourDiv) {
        rsv280HourDiv__ = rsv280HourDiv;
    }

    /**
     * <p>rsv280EditKbn を取得します。
     * @return rsv280EditKbn
     */
    /**
     * <p>rsv280EditKbn を取得します。
     * @return rsv280EditKbn
     */
    public int getRsv280EditKbn() {
        return rsv280EditKbn__;
    }
    /**
     * <p>rsv280EditKbn をセットします。
     * @param rsv280EditKbn rsv280EditKbn
     */
    public void setRsv280EditKbn(int rsv280EditKbn) {
        rsv280EditKbn__ = rsv280EditKbn;
    }
    /**
     * <p>rsv280Edit を取得します。
     * @return rsv280Edit
     */
    public int getRsv280Edit() {
        return rsv280Edit__;
    }
    /**
     * <p>rsv280Edit をセットします。
     * @param rsv280Edit rsv280Edit
     */
    public void setRsv280Edit(int rsv280Edit) {
        rsv280Edit__ = rsv280Edit;
    }
    /**
     * <p>rsv280PublicKbnを取得します。
     * @return rsv280PublicKbn
     * */
    public int getRsv280PublicKbn() {
        return rsv280PublicKbn__;
    }
    /**
     * rsv280PublicKbnをセットします。
     * @param rsv280PublicKbn rsv280PublicKbn
     * */
    public void setRsv280PublicKbn(int rsv280PublicKbn) {
        rsv280PublicKbn__ = rsv280PublicKbn;
    }
    /**
     * <p>rsv280Publicを取得します。
     * @return rsv280Public
     * */
    public int getRsv280Public() {
        return rsv280Public__;
    }
    /**
     * rsv280Publicをセットします。
     * @param rsv280Public rsv280Public
     * */
    public void setRsv280Public(int rsv280Public) {
        rsv280Public__ = rsv280Public;
    }
    /**
     * <p>rsv280initFlg を取得します。
     * @return rsv280initFlg
     */
    public int getRsv280initFlg() {
        return rsv280initFlg__;
    }
    /**
     * <p>rsv280initFlg をセットします。
     * @param rsv280initFlg rsv280initFlg
     */
    public void setRsv280initFlg(int rsv280initFlg) {
        rsv280initFlg__ = rsv280initFlg;
    }
    /**
     * <p>rsv280PubDefUsrSid を取得します。
     * @return rsv280PubDefUsrSid
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280PubDefUsrSid__
     */
    public int getRsv280PubDefUsrSid() {
        return rsv280PubDefUsrSid__;
    }
    /**
     * <p>rsv280PubDefUsrSid をセットします。
     * @param rsv280PubDefUsrSid rsv280PubDefUsrSid
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280PubDefUsrSid__
     */
    public void setRsv280PubDefUsrSid(int rsv280PubDefUsrSid) {
        rsv280PubDefUsrSid__ = rsv280PubDefUsrSid;
    }
    /**
     * <p>rsv280PubUserGroup を取得します。
     * @return rsv280PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280PubUserGroup__
     */
    public String getRsv280PubUserGroup() {
        return rsv280PubUserGroup__;
    }
    /**
     * <p>rsv280PubUserGroup をセットします。
     * @param rsv280PubUserGroup rsv280PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280PubUserGroup__
     */
    public void setRsv280PubUserGroup(String rsv280PubUserGroup) {
        rsv280PubUserGroup__ = rsv280PubUserGroup;
    }
    /**
     * <p>rsv280PubUsrGrpSid を取得します。
     * @return rsv280PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280PubUsrGrpSid__
     */
    public String[] getRsv280PubUsrGrpSid() {
        return rsv280PubUsrGrpSid__;
    }
    /**
     * <p>rsv280PubUsrGrpSid をセットします。
     * @param rsv280PubUsrGrpSid rsv280PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280PubUsrGrpSid__
     */
    public void setRsv280PubUsrGrpSid(String[] rsv280PubUsrGrpSid) {
        rsv280PubUsrGrpSid__ = rsv280PubUsrGrpSid;
    }
    /**
     * <p>rsv280PubUsrGrpUI を取得します。
     * @return rsv280PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280PubUsrGrpUI__
     */
    public UserGroupSelector getRsv280PubUsrGrpUI() {
        return rsv280PubUsrGrpUI__;
    }
    /**
     * <p>rsv280PubUsrGrpUI をセットします。
     * @param rsv280PubUsrGrpUI rsv280PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv280.Rsv280Form#rsv280PubUsrGrpUI__
     */
    public void setRsv280PubUsrGrpUI(UserGroupSelector rsv280PubUsrGrpUI) {
        rsv280PubUsrGrpUI__ = rsv280PubUsrGrpUI;
    }
}
