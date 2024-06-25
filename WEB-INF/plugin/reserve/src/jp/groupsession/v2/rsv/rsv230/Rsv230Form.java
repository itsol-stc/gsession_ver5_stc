package jp.groupsession.v2.rsv.rsv230;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.rsv.rsv140.Rsv140Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約 初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv230Form extends Rsv140Form implements ISelectorUseForm {

    /** 開始 時 */
    private int rsv230DefFrH__ = 9;
    /** 開始 分 */
    private int rsv230DefFrM__ = 0;
    /** 終了 時 */
    private int rsv230DefToH__ = 18;
    /** 終了 分 */
    private int rsv230DefToM__ = 0;
    /** 開始時間 */
    private String rsv230DefFrTime__ = null;
    /** 開始時間 */
    private String rsv230DefToTime__ = null;
    /** 分単位 */
    private int rsv230HourDiv__ = 0;
    
    /** 期間 編集許可 */
    private boolean rsv230PeriodFlg__ = false;
    /** 編集権限 */
    private int rsv230Edit__ = GSConstReserve.EDIT_AUTH_NONE;
    /** 編集権限 編集許可 */
    private boolean rsv230EditFlg__ = false;
    /** 公開区分 */
    private int rsv230Public__ = GSConstReserve.PUBLIC_KBN_ALL;
    /** 公開区分 編集許可 */
    private boolean rsv230PublicFlg__ = false;

    /** 期間 編集許可(保存） */
    private boolean rsv230SvPeriodFlg__ = false;
    /** 編集権限 編集許可(保存） */
    private boolean rsv230SvEditFlg__ = false;
    /** 公開区分 編集許可(保存） */
    private boolean rsv230SvPublicFlg__ = false;
    /** 初期表示フラグ */
    private int rsv230initFlg__ = 0;

    /** デフォルトユーザSID  */
    private int rsv230PubDefUsrSid__ = 0;
    /** 公開対象 ユーザ グループ */
    private String rsv230PubUserGroup__ =
            String.valueOf(GSConst.GROUP_COMBO_VALUE);
    /** 公開対象ユーザ・グループSID */
    private String[] rsv230PubUsrGrpSid__ = new String[0];
    /** 公開対象ユーザ・グループ UI */
    private UserGroupSelector rsv230PubUsrGrpUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("main.exposed", null))
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(
                    Select.builder()
                    .chainLabel(new GsMessageReq("main.exposed", null))
                    .chainParameterName(
                            "rsv230PubUsrGrpSid")
                )
            .chainGroupSelectionParamName("rsv230PubUserGroup")
            .build();
    
    /**
     * <br>[機  能] 時間の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 時間設定時例外
     * @throws InvocationTargetException 時間設定時例外
     * @throws IllegalAccessException 時間設定時例外
     */
    public ActionErrors validateTimeRsv230(Connection con, RequestModel reqMdl) throws
        SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        ActionErrors errors = new ActionErrors();
        //管理者が設定する場合入力チェックを行わない
        RsvCommonBiz rsvBiz = new RsvCommonBiz();
        boolean periodUserFlg = rsvBiz.canPeriodInitConf(con);
        if (!periodUserFlg) {
            return errors;
        }
        
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        int errorSize = errors.size();
        String startTime = gsMsg.getMessage("cmn.starttime");
        String endTime = gsMsg.getMessage("cmn.endtime");
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        errors.add(dateBiz.setHmParam(this, "rsv230DefFrTime",
                "rsv230DefFrH", "rsv230DefFrM", startTime));
        errors.add(dateBiz.setHmParam(this, "rsv230DefToTime",
                "rsv230DefToH", "rsv230DefToM", endTime));
        
        if (errorSize != errors.size()) {
            return errors;
        }
        
        int hourDivision = rsvBiz.getHourDivision(con);
        if (rsv230DefFrM__ % hourDivision != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", startTime,
                    gsMsg.getMessageVal0("cmn.minute.periods", String.valueOf(hourDivision)));
            errors.add("error.input.comp.text", msg);
        }
        
        if (rsv230DefToM__ % hourDivision != 0) {
            msg = new ActionMessage(
                    "error.input.comp.text", endTime,
                    gsMsg.getMessageVal0("cmn.minute.periods", String.valueOf(hourDivision)));
            errors.add("error.input.comp.text", msg);
        }

        UDate startDate = new UDate();
        startDate.setZeroDdHhMmSs();
        startDate.setHour(rsv230DefFrH__);
        startDate.setMinute(rsv230DefFrM__);

        UDate endDate = new UDate();
        endDate.setZeroDdHhMmSs();
        endDate.setHour(rsv230DefToH__);
        endDate.setMinute(rsv230DefToM__);
        
        if (startDate.compareDateYMDHM(endDate) == UDate.SMALL) {
            msg = new ActionMessage("error.input.comp.text",
                    gsMsg.getMessage("cmn.time"),
                    startTime + " < " + endTime);
            errors.add("error.input.comp.text", msg);
        }

        return errors;
    }


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateRsv230(HttpServletRequest req)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        if (rsv230Public__ == GSConstReserve.PUBLIC_KBN_USRGRP
                && rsv230PubUsrGrpSid__.length == 0) {
            String taisyou = gsMsg.getMessage("reserve.190");
            msg = new ActionMessage(
                    "error.select.required.text", taisyou);
            errors.add("error.select.required.text", msg);
        }
        

        return errors;
    }

    /**
     * <p>rsv230DefFrH を取得します。
     * @return rsv230DefFrH
     */
    public int getRsv230DefFrH() {
        return rsv230DefFrH__;
    }
    /**
     * <p>rsv230DefFrH をセットします。
     * @param rsv230DefFrH rsv230DefFrH
     */
    public void setRsv230DefFrH(int rsv230DefFrH) {
        rsv230DefFrH__ = rsv230DefFrH;
    }
    /**
     * <p>rsv230DefFrM を取得します。
     * @return rsv230DefFrM
     */
    public int getRsv230DefFrM() {
        return rsv230DefFrM__;
    }
    /**
     * <p>rsv230DefFrM をセットします。
     * @param rsv230DefFrM rsv230DefFrM
     */
    public void setRsv230DefFrM(int rsv230DefFrM) {
        rsv230DefFrM__ = rsv230DefFrM;
    }
    /**
     * <p>rsv230DefToH を取得します。
     * @return rsv230DefToH
     */
    public int getRsv230DefToH() {
        return rsv230DefToH__;
    }
    /**
     * <p>rsv230DefToH をセットします。
     * @param rsv230DefToH rsv230DefToH
     */
    public void setRsv230DefToH(int rsv230DefToH) {
        rsv230DefToH__ = rsv230DefToH;
    }
    /**
     * <p>rsv230DefToM を取得します。
     * @return rsv230DefToM
     */
    public int getRsv230DefToM() {
        return rsv230DefToM__;
    }
    /**
     * <p>rsv230DefToM をセットします。
     * @param rsv230DefToM rsv230DefToM
     */
    public void setRsv230DefToM(int rsv230DefToM) {
        rsv230DefToM__ = rsv230DefToM;
    }
    /**
     * <p>rsv230DefFrTime を取得します。
     * @return rsv230DefFrTime
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230DefFrTime__
     */
    public String getRsv230DefFrTime() {
        return rsv230DefFrTime__;
    }
    /**
     * <p>rsv230DefFrTime をセットします。
     * @param rsv230DefFrTime rsv230DefFrTime
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230DefFrTime__
     */
    public void setRsv230DefFrTime(String rsv230DefFrTime) {
        rsv230DefFrTime__ = rsv230DefFrTime;
    }
    /**
     * <p>rsv230DefToTime を取得します。
     * @return rsv230DefToTime
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230DefToTime__
     */
    public String getRsv230DefToTime() {
        return rsv230DefToTime__;
    }
    /**
     * <p>rsv230DefToTime をセットします。
     * @param rsv230DefToTime rsv230DefToTime
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230DefToTime__
     */
    public void setRsv230DefToTime(String rsv230DefToTime) {
        rsv230DefToTime__ = rsv230DefToTime;
    }
    /**
     * <p>rsv230HourDiv を取得します。
     * @return rsv230HourDiv
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230HourDiv__
     */
    public int getRsv230HourDiv() {
        return rsv230HourDiv__;
    }
    /**
     * <p>rsv230HourDiv をセットします。
     * @param rsv230HourDiv rsv230HourDiv
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230HourDiv__
     */
    public void setRsv230HourDiv(int rsv230HourDiv) {
        rsv230HourDiv__ = rsv230HourDiv;
    }
    /**
     * <p>rsv230Edit を取得します。
     * @return rsv230Edit
     */
    public int getRsv230Edit() {
        return rsv230Edit__;
    }
    /**
     * <p>rsv230Edit をセットします。
     * @param rsv230Edit rsv230Edit
     */
    public void setRsv230Edit(int rsv230Edit) {
        rsv230Edit__ = rsv230Edit;
    }
    /**
     * <p>rsv230PeriodFlg を取得します。
     * @return rsv230PeriodFlg
     */
    public boolean isRsv230PeriodFlg() {
        return rsv230PeriodFlg__;
    }
    /**
     * <p>rsv230PeriodFlg をセットします。
     * @param rsv230PeriodFlg rsv230PeriodFlg
     */
    public void setRsv230PeriodFlg(boolean rsv230PeriodFlg) {
        rsv230PeriodFlg__ = rsv230PeriodFlg;
    }
    /**
     * <p>rsv230EditFlg を取得します。
     * @return rsv230EditFlg
     */
    public boolean isRsv230EditFlg() {
        return rsv230EditFlg__;
    }
    /**
     * <p>rsv230EditFlg をセットします。
     * @param rsv230EditFlg rsv230EditFlg
     */
    public void setRsv230EditFlg(boolean rsv230EditFlg) {
        rsv230EditFlg__ = rsv230EditFlg;
    }
    /**
     * rsv230Publicを取得します。
     * @return rsv230Public
     * */
    public int getRsv230Public() {
        return rsv230Public__;
    }
    /**
     * rsv230Publicをセットします。
     * @param rsv230Public rsv230Public
     * */
    public void setRsv230Public(int rsv230Public) {
        rsv230Public__ = rsv230Public;
    }
    /**
     * rsv230PublicFlgを取得します。
     * @return rsv230PublicFlg
     * */
    public boolean getRsv230PublicFlg() {
        return rsv230PublicFlg__;
    }
    /**
     * rsv230PublicFlgをセットします。
     * @param rsv230PublicFlg rsv230PublicFlg
     * */
    public void setRsv230PublicFlg(boolean rsv230PublicFlg) {
        rsv230PublicFlg__ = rsv230PublicFlg;
    }
    /**
     * <p>rsv230SvPeriodFlg を取得します。
     * @return rsv230SvPeriodFlg
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230SvPeriodFlg__
     */
    public boolean isRsv230SvPeriodFlg() {
        return rsv230SvPeriodFlg__;
    }
    /**
     * <p>rsv230SvPeriodFlg をセットします。
     * @param rsv230SvPeriodFlg rsv230SvPeriodFlg
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230SvPeriodFlg__
     */
    public void setRsv230SvPeriodFlg(boolean rsv230SvPeriodFlg) {
        rsv230SvPeriodFlg__ = rsv230SvPeriodFlg;
    }
    /**
     * <p>rsv230SvEditFlg を取得します。
     * @return rsv230SvEditFlg
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230SvEditFlg__
     */
    public boolean isRsv230SvEditFlg() {
        return rsv230SvEditFlg__;
    }
    /**
     * <p>rsv230SvEditFlg をセットします。
     * @param rsv230SvEditFlg rsv230SvEditFlg
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230SvEditFlg__
     */
    public void setRsv230SvEditFlg(boolean rsv230SvEditFlg) {
        rsv230SvEditFlg__ = rsv230SvEditFlg;
    }
    /**
     * <p>rsv230SvPublicFlg を取得します。
     * @return rsv230SvPublicFlg
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230SvPublicFlg__
     */
    public boolean isRsv230SvPublicFlg() {
        return rsv230SvPublicFlg__;
    }
    /**
     * <p>rsv230SvPublicFlg をセットします。
     * @param rsv230SvPublicFlg rsv230SvPublicFlg
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230SvPublicFlg__
     */
    public void setRsv230SvPublicFlg(boolean rsv230SvPublicFlg) {
        rsv230SvPublicFlg__ = rsv230SvPublicFlg;
    }
    /**
     * <p>rsv230initFlg を取得します。
     * @return rsv230initFlg
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230initFlg__
     */
    public int getRsv230initFlg() {
        return rsv230initFlg__;
    }
    /**
     * <p>rsv230initFlg をセットします。
     * @param rsv230initFlg rsv230initFlg
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230initFlg__
     */
    public void setRsv230initFlg(int rsv230initFlg) {
        rsv230initFlg__ = rsv230initFlg;
    }
    /**
     * <p>rsv230PubDefUsrSid を取得します。
     * @return rsv230PubDefUsrSid
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230PubDefUsrSid__
     */
    public int getRsv230PubDefUsrSid() {
        return rsv230PubDefUsrSid__;
    }
    /**
     * <p>rsv230PubDefUsrSid をセットします。
     * @param rsv230PubDefUsrSid rsv230PubDefUsrSid
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230PubDefUsrSid__
     */
    public void setRsv230PubDefUsrSid(int rsv230PubDefUsrSid) {
        rsv230PubDefUsrSid__ = rsv230PubDefUsrSid;
    }
    /**
     * <p>rsv230PubUserGroup を取得します。
     * @return rsv230PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230PubUserGroup__
     */
    public String getRsv230PubUserGroup() {
        return rsv230PubUserGroup__;
    }
    /**
     * <p>rsv230PubUserGroup をセットします。
     * @param rsv230PubUserGroup rsv230PubUserGroup
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230PubUserGroup__
     */
    public void setRsv230PubUserGroup(String rsv230PubUserGroup) {
        rsv230PubUserGroup__ = rsv230PubUserGroup;
    }
    /**
     * <p>rsv230PubUsrGrpSid を取得します。
     * @return rsv230PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230PubUsrGrpSid__
     */
    public String[] getRsv230PubUsrGrpSid() {
        return rsv230PubUsrGrpSid__;
    }
    /**
     * <p>rsv230PubUsrGrpSid をセットします。
     * @param rsv230PubUsrGrpSid rsv230PubUsrGrpSid
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230PubUsrGrpSid__
     */
    public void setRsv230PubUsrGrpSid(String[] rsv230PubUsrGrpSid) {
        rsv230PubUsrGrpSid__ = rsv230PubUsrGrpSid;
    }
    /**
     * <p>rsv230PubUsrGrpUI を取得します。
     * @return rsv230PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230PubUsrGrpUI__
     */
    public UserGroupSelector getRsv230PubUsrGrpUI() {
        return rsv230PubUsrGrpUI__;
    }
    /**
     * <p>rsv230PubUsrGrpUI をセットします。
     * @param rsv230PubUsrGrpUI rsv230PubUsrGrpUI
     * @see jp.groupsession.v2.rsv.rsv230.Rsv230Form#rsv230PubUsrGrpUI__
     */
    public void setRsv230PubUsrGrpUI(UserGroupSelector rsv230PubUsrGrpUI) {
        rsv230PubUsrGrpUI__ = rsv230PubUsrGrpUI;
    }
}