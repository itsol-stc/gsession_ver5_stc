package jp.groupsession.v2.sch.sch260;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.cmn140.MenuSelector;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sch.GSValidateSchedule;
import jp.groupsession.v2.sch.sch250.Sch250Form;
import jp.groupsession.v2.sch.ui.parts.schedulelist.ScheduleListSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 表示リスト登録画面のアクションフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch260Form extends Sch250Form {

    /** マイスケジュール名 MAX文字数 */
    public static final int MAXLEN_NAME = 50;
    /** 備考 MAX文字数 */
    public static final int MAXLEN_BIKO = 1000;

    /** マイスケジュール名 */
    private String sch260scheduleName__ = null;
    /** 対象 */
    private String[] sch260subject__ = null;
    /** 対象 グループ */
    private int sch260subjectGroup__  = -1;
    /** 対象(選択用) */
    private String[] sch260subjectSelect__  = null;
    /** 対象(未選択用) */
    private String[] sch260subjectNoSelect__ = null;
    /** 備考 */
    private String sch260biko__ = null;
    /** 初期表示フラグ */
    private int sch260initFlg__ = 0;
    /** グループコンボ */
    private List<LabelValueBean> groupCombo__ = null;
    /** 対象並び順 */
    private String[] sch260orderList__ = null;
    /** 対象 選択UI*/
    private ScheduleListSelector sch260subjectUI__ =
            ScheduleListSelector.builder()
                //ユーザ選択 日本語名（入力チェック時に使用）
                .chainLabel(new GsMessageReq("schedule.117", null))
                //選択対象設定
                .chainSelect(Select.builder()
                        //ユーザSID保管パラメータ名
                        .chainParameterName(
                                "sch260subject")
                        )
                //グループ選択保管パラメータ名
                .chainGroupSelectionParamName("sch260subjectGroup")
                .build();
    /**
     * <p>sch260scheduleName を取得します。
     * @return sch260scheduleName
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260scheduleName__
     */
    public String getSch260scheduleName() {
        return sch260scheduleName__;
    }
    /**
     * <p>sch260scheduleName をセットします。
     * @param sch260scheduleName sch260scheduleName
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260scheduleName__
     */
    public void setSch260scheduleName(String sch260scheduleName) {
        sch260scheduleName__ = sch260scheduleName;
    }
    /**
     * <p>sch260subject を取得します。
     * @return sch260subject
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subject__
     */
    public String[] getSch260subject() {
        return sch260subject__;
    }
    /**
     * <p>sch260subject をセットします。
     * @param sch260subject sch260subject
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subject__
     */
    public void setSch260subject(String[] sch260subject) {
        sch260subject__ = sch260subject;
    }
    /**
     * <p>sch260subjectGroup を取得します。
     * @return sch260subjectGroup
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectGroup__
     */
    public int getSch260subjectGroup() {
        return sch260subjectGroup__;
    }
    /**
     * <p>sch260subjectGroup をセットします。
     * @param sch260subjectGroup sch260subjectGroup
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectGroup__
     */
    public void setSch260subjectGroup(int sch260subjectGroup) {
        sch260subjectGroup__ = sch260subjectGroup;
    }
    /**
     * <p>sch260subjectSelect を取得します。
     * @return sch260subjectSelect
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectSelect__
     */
    public String[] getSch260subjectSelect() {
        return sch260subjectSelect__;
    }
    /**
     * <p>sch260subjectSelect をセットします。
     * @param sch260subjectSelect sch260subjectSelect
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectSelect__
     */
    public void setSch260subjectSelect(String[] sch260subjectSelect) {
        sch260subjectSelect__ = sch260subjectSelect;
    }
    /**
     * <p>sch260subjectNoSelect を取得します。
     * @return sch260subjectNoSelect
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectNoSelect__
     */
    public String[] getSch260subjectNoSelect() {
        return sch260subjectNoSelect__;
    }
    /**
     * <p>sch260subjectNoSelect をセットします。
     * @param sch260subjectNoSelect sch260subjectNoSelect
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectNoSelect__
     */
    public void setSch260subjectNoSelect(String[] sch260subjectNoSelect) {
        sch260subjectNoSelect__ = sch260subjectNoSelect;
    }
    /**
     * <p>sch260biko を取得します。
     * @return sch260biko
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260biko__
     */
    public String getSch260biko() {
        return sch260biko__;
    }
    /**
     * <p>sch260biko をセットします。
     * @param sch260biko sch260biko
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260biko__
     */
    public void setSch260biko(String sch260biko) {
        sch260biko__ = sch260biko;
    }
    /**
     * <p>sch260initFlg を取得します。
     * @return sch260initFlg
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260initFlg__
     */
    public int getSch260initFlg() {
        return sch260initFlg__;
    }
    /**
     * <p>sch260initFlg をセットします。
     * @param sch260initFlg sch260initFlg
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260initFlg__
     */
    public void setSch260initFlg(int sch260initFlg) {
        sch260initFlg__ = sch260initFlg;
    }
    /**
     * <p>groupCombo を取得します。
     * @return groupCombo
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#groupCombo__
     */
    public List<LabelValueBean> getGroupCombo() {
        return groupCombo__;
    }
    /**
     * <p>groupCombo をセットします。
     * @param groupCombo groupCombo
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#groupCombo__
     */
    public void setGroupCombo(List<LabelValueBean> groupCombo) {
        groupCombo__ = groupCombo;
    }
    /**
     * <p>sch260orderList を取得します。
     * @return sch260orderList
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260orderList__
     */
    public String[] getSch260orderList() {
        return sch260orderList__;
    }
    /**
     * <p>sch260orderList をセットします。
     * @param sch260orderList sch260orderList
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260orderList__
     */
    public void setSch260orderList(String[] sch260orderList) {
        sch260orderList__ = sch260orderList;
    }
    /**
     * <p>sch260subjectUI を取得します。
     * @return sch260subjectUI
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectUI__
     */
    public ScheduleListSelector getSch260subjectUI() {
        return sch260subjectUI__;
    }
    /**
     * <p>sch260subjectUI をセットします。
     * @param sch260subjectUI sch260subjectUI
     * @see jp.groupsession.v2.sch.sch260.Sch260Form#sch260subjectUI__
     */
    public void setSch260subjectUI(ScheduleListSelector sch260subjectUI) {
        sch260subjectUI__ = sch260subjectUI;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(RequestModel reqMdl, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //名前入力チェック
        GSValidateSchedule.validateTextBoxInput(errors, sch260scheduleName__,
                "sch260scheduleName",
                gsMsg.getMessage("cmn.name5"),
                MAXLEN_NAME, true);

        //備考入力チェック
        __validateBikoCheck(errors, con, reqMdl);

        return errors;
    }

   /**
    * <br>[機  能] 備考入力チェック
    * <br>[解  説]
    * <br>[備  考]
    * @param errors アクションエラー
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @throws SQLException SQL実行時例外
    */
    private void __validateBikoCheck(
            ActionErrors errors, Connection con, RequestModel reqMdl) throws SQLException {

        if (StringUtil.isNullZeroString(sch260biko__)) {
            return;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;

        String textBiko = gsMsg.getMessage("cmn.memo");
        if (ValidateUtil.isSpaceOrKaigyou(sch260biko__)) {
            //スペース・改行のみチェック
            msg = new ActionMessage("error.input.spase.cl.only", textBiko);
            StrutsUtil.addMessage(errors, msg, "sch260biko.error.input.spase.cl.only");
        } else if (ValidateUtil.isSpaceStart(sch260biko__)) {
            //先頭スペースチェック
            msg = new ActionMessage("error.input.spase.start", textBiko);
            StrutsUtil.addMessage(errors, msg, "sch260biko.error.input.spase.start");
        } else if (ValidateUtil.isTab(sch260biko__)) {
            //タブ文字チェック
            msg = new ActionMessage("error.input.tab.text", textBiko);
            StrutsUtil.addMessage(errors, msg, "sch260biko.error.input.tab.text");
        } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(sch260biko__)) {
            //JIS第2水準チェック
            String nstr = GSValidateUtil.getNotGsJapaneaseStringTextArea(sch260biko__);
            msg = new ActionMessage("error.input.njapan.text", textBiko, nstr);
            StrutsUtil.addMessage(errors, msg, "sch260biko.error.input.njapan.text");
        } else if (sch260biko__.length() > MAXLEN_BIKO) {
            //MAX桁チェック
            msg = new ActionMessage("error.input.length.text", textBiko, MAXLEN_BIKO);
            StrutsUtil.addMessage(
                    errors, msg, "sch260biko.error.input.length.text");
        }
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {

        msgForm.addHiddenParam("sch250editMode", getSch250editMode());
        msgForm.addHiddenParam("sch250editData", getSch250editData());
        msgForm.addHiddenParam("sch260orderList", getSch260orderList());
        msgForm.addHiddenParam("sch260scheduleName", sch260scheduleName__);
        msgForm.addHiddenParam("sch260subject", sch260subject__);
        msgForm.addHiddenParam("sch260subjectGroup", sch260subjectGroup__);
        msgForm.addHiddenParam("sch260subjectSelect", sch260subjectSelect__);
        msgForm.addHiddenParam("sch260subjectNoSelect", sch260subjectNoSelect__);
        msgForm.addHiddenParam("sch260biko", sch260biko__);
        msgForm.addHiddenParam("sch260initFlg", sch260initFlg__);

    }

}
