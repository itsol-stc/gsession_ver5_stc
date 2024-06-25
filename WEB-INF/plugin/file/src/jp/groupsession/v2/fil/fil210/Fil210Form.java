package jp.groupsession.v2.fil.fil210;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil200.Fil200Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil210Form extends Fil200Form implements ISelectorUseForm {

    /** キャビネット作成区分 */
    private String fil210CrtKbn__ = null;

    /** 選択グループ */
    private String fil210SltGroup__ = "0";
    /** 作成可能に選ばれたユーザSID保持用 */
    private String[] fil210SvUsers__ = null;
    /** 作成可能ユーザ UI */
    private UserGroupSelector fil210UserUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("fil.28", null))
            .chainType(EnumSelectType.USER)
            .chainSelect(Select.builder()
                    .chainLabel(new GsMessageReq("fil.fil210.2", null))
                    .chainParameterName(
                            "fil210SvUsers")
                    )
            .chainGroupSelectionParamName("fil210SltGroup")
            .build();

    /** 作成可能に選ばれたグループSID保持用 */
    private String[] fil210SvGroups__ = null;
    /** 作成可能グループ UI */
    private UserGroupSelector fil210GroupUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("fil.28", null))
            .chainType(EnumSelectType.GROUP)
            .chainSelect(Select.builder()
                    .chainLabel(new GsMessageReq("fil.fil210.2", null))
                    .chainParameterName(
                            "fil210SvGroups")
                    )
            .build();

    /** 選択（全て選択用） */
    private String fil210AllSelect__ = null;
    /** ファイルサイズ制限 */
    private String fil210FileSize__ = null;
    /** ファイル保存期間 */
    private String fil210SaveDays__ = null;
    /** ロック機能有無 */
    private String fil210LockKbn__ = null;
    /** バージョン管理有無 */
    private String fil210VerKbn__ = null;

    /** ファイルのサイズ制限コンボ */
    private ArrayList<LabelValueBean> fil210FileSizeList__ = new ArrayList<LabelValueBean>();
    /** 削除ファイル保存期間コンボ */
    private ArrayList<LabelValueBean> fil210SaveDaysList__ = new ArrayList<LabelValueBean>();

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();

        //キャビネット作成区分
        if (fil210CrtKbn__ == null) {
            String textCabinetPermit = gsMsg.getMessage(req, "fil.28");
            msg = new ActionMessage("error.select.required.text",
                    textCabinetPermit);
            StrutsUtil.addMessage(errors, msg, "fil210CrtKbn");
        }

        if (errors.size() < 1) {
            __checkCrtKbn(errors, req);
        }

        //ファイルサイズ制限
        if (fil210FileSize__ == null) {
            String textFileSize = gsMsg.getMessage(req, "fil.66");
            msg = new ActionMessage("error.select.required.text", textFileSize);
            StrutsUtil.addMessage(errors, msg, "fil210FileSize");
        }

        //削除ファイル保存期間
        if (fil210SaveDays__ == null) {
            String textSaveDays = gsMsg.getMessage(req, "fil.67");
            msg = new ActionMessage("error.select.required.text", textSaveDays);
            StrutsUtil.addMessage(errors, msg, "fil210SaveDays");
        }

        //ロック機能
        if (fil210LockKbn__ == null) {
            String textLock = gsMsg.getMessage(req, "fil.34");
            msg = new ActionMessage("error.select.required.text", textLock);
            StrutsUtil.addMessage(errors, msg, "fil210LockKbn");
        }

        //バージョン管理
        if (fil210VerKbn__ == null) {
            String textVersion = gsMsg.getMessage(req, "fil.69");
            msg = new ActionMessage("error.select.required.text", textVersion);
            StrutsUtil.addMessage(errors, msg, "fil210VerKbn");
        }

        return errors;
    }

    /**
     * <br>[機  能] キャビネット作成権限の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors エラー
     * @param req リクエスト
     * @return エラー
     */
    private ActionErrors __checkCrtKbn(ActionErrors errors, HttpServletRequest req) {
        ActionMessage msg = null;

        int crtLbn = NullDefault.getInt(fil210CrtKbn__, 0);

        GsMessage gsMsg = new GsMessage();
        String textCabinetPermit = gsMsg.getMessage(req, "fil.28");

        if (crtLbn == GSConstFile.CREATE_CABINET_PERMIT_GROUP) {
            if (fil210SvGroups__ == null || fil210SvGroups__.length < 1) {
                msg = new ActionMessage("error.select.required.text",
                                         textCabinetPermit + " "
                                         + gsMsg.getMessage(req, "cmn.group"));
                StrutsUtil.addMessage(errors, msg, "fil210SvGroups");
            }
        } else if (crtLbn == GSConstFile.CREATE_CABINET_PERMIT_USER) {
            if (fil210SvUsers__ == null || fil210SvUsers__.length < 1) {
                msg = new ActionMessage("error.select.required.text",
                                         textCabinetPermit + " "
                                         + gsMsg.getMessage(req, "cmn.user"));
                StrutsUtil.addMessage(errors, msg, "fil210SvUsers");
            }
        }

        return errors;
    }

    /**
     * <p>fil210AllSelect を取得します。
     * @return fil210AllSelect
     */
    public String getFil210AllSelect() {
        return fil210AllSelect__;
    }
    /**
     * <p>fil210AllSelect をセットします。
     * @param fil210AllSelect fil210AllSelect
     */
    public void setFil210AllSelect(String fil210AllSelect) {
        fil210AllSelect__ = fil210AllSelect;
    }
    /**
     * <p>fil210CrtKbn を取得します。
     * @return fil210CrtKbn
     */
    public String getFil210CrtKbn() {
        return fil210CrtKbn__;
    }
    /**
     * <p>fil210CrtKbn をセットします。
     * @param fil210CrtKbn fil210CrtKbn
     */
    public void setFil210CrtKbn(String fil210CrtKbn) {
        fil210CrtKbn__ = fil210CrtKbn;
    }
    /**
     * <p>fil210FileSize を取得します。
     * @return fil210FileSize
     */
    public String getFil210FileSize() {
        return fil210FileSize__;
    }
    /**
     * <p>fil210FileSize をセットします。
     * @param fil210FileSize fil210FileSize
     */
    public void setFil210FileSize(String fil210FileSize) {
        fil210FileSize__ = fil210FileSize;
    }
    /**
     * <p>fil210FileSizeList を取得します。
     * @return fil210FileSizeList
     */
    public ArrayList<LabelValueBean> getFil210FileSizeList() {
        return fil210FileSizeList__;
    }
    /**
     * <p>fil210FileSizeList をセットします。
     * @param fil210FileSizeList fil210FileSizeList
     */
    public void setFil210FileSizeList(ArrayList<LabelValueBean> fil210FileSizeList) {
        fil210FileSizeList__ = fil210FileSizeList;
    }
    /**
     * <p>fil210LockKbn を取得します。
     * @return fil210LockKbn
     */
    public String getFil210LockKbn() {
        return fil210LockKbn__;
    }
    /**
     * <p>fil210LockKbn をセットします。
     * @param fil210LockKbn fil210LockKbn
     */
    public void setFil210LockKbn(String fil210LockKbn) {
        fil210LockKbn__ = fil210LockKbn;
    }
    /**
     * <p>fil210SaveDays を取得します。
     * @return fil210SaveDays
     */
    public String getFil210SaveDays() {
        return fil210SaveDays__;
    }
    /**
     * <p>fil210SaveDays をセットします。
     * @param fil210SaveDays fil210SaveDays
     */
    public void setFil210SaveDays(String fil210SaveDays) {
        fil210SaveDays__ = fil210SaveDays;
    }
    /**
     * <p>fil210SaveDaysList を取得します。
     * @return fil210SaveDaysList
     */
    public ArrayList<LabelValueBean> getFil210SaveDaysList() {
        return fil210SaveDaysList__;
    }
    /**
     * <p>fil210SaveDaysList をセットします。
     * @param fil210SaveDaysList fil210SaveDaysList
     */
    public void setFil210SaveDaysList(ArrayList<LabelValueBean> fil210SaveDaysList) {
        fil210SaveDaysList__ = fil210SaveDaysList;
    }
    /**
     * <p>fil210SltGroup を取得します。
     * @return fil210SltGroup
     */
    public String getFil210SltGroup() {
        return fil210SltGroup__;
    }
    /**
     * <p>fil210SltGroup をセットします。
     * @param fil210SltGroup fil210SltGroup
     */
    public void setFil210SltGroup(String fil210SltGroup) {
        fil210SltGroup__ = fil210SltGroup;
    }
    /**
     * <p>fil210SvGroups を取得します。
     * @return fil210SvGroups
     */
    public String[] getFil210SvGroups() {
        return fil210SvGroups__;
    }
    /**
     * <p>fil210SvGroups をセットします。
     * @param fil210SvGroups fil210SvGroups
     */
    public void setFil210SvGroups(String[] fil210SvGroups) {
        fil210SvGroups__ = fil210SvGroups;
    }
    /**
     * <p>fil210SvUsers を取得します。
     * @return fil210SvUsers
     */
    public String[] getFil210SvUsers() {
        return fil210SvUsers__;
    }
    /**
     * <p>fil210SvUsers をセットします。
     * @param fil210SvUsers fil210SvUsers
     */
    public void setFil210SvUsers(String[] fil210SvUsers) {
        fil210SvUsers__ = fil210SvUsers;
    }
    /**
     * <p>fil210VerKbn を取得します。
     * @return fil210VerKbn
     */
    public String getFil210VerKbn() {
        return fil210VerKbn__;
    }
    /**
     * <p>fil210VerKbn をセットします。
     * @param fil210VerKbn fil210VerKbn
     */
    public void setFil210VerKbn(String fil210VerKbn) {
        fil210VerKbn__ = fil210VerKbn;
    }
    /**
     * <p>fil210UserUI を取得します。
     * @return fil210UserUI
     * @see jp.groupsession.v2.fil.fil210.Fil210Form#fil210UserUI__
     */
    public UserGroupSelector getFil210UserUI() {
        return fil210UserUI__;
    }
    /**
     * <p>fil210UserUI をセットします。
     * @param fil210UserUI fil210UserUI
     * @see jp.groupsession.v2.fil.fil210.Fil210Form#fil210UserUI__
     */
    public void setFil210UserUI(UserGroupSelector fil210UserUI) {
        fil210UserUI__ = fil210UserUI;
    }
    /**
     * <p>fil210GroupUI を取得します。
     * @return fil210GroupUI
     * @see jp.groupsession.v2.fil.fil210.Fil210Form#fil210GroupUI__
     */
    public UserGroupSelector getFil210GroupUI() {
        return fil210GroupUI__;
    }
    /**
     * <p>fil210GroupUI をセットします。
     * @param fil210GroupUI fil210GroupUI
     * @see jp.groupsession.v2.fil.fil210.Fil210Form#fil210GroupUI__
     */
    public void setFil210GroupUI(UserGroupSelector fil210GroupUI) {
        fil210GroupUI__ = fil210GroupUI;
    }
}
