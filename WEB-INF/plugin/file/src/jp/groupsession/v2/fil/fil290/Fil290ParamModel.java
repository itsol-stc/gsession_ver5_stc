package jp.groupsession.v2.fil.fil290;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil200.Fil200ParamModel;

/**
 * <br>[機  能] 管理者設定 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil290ParamModel extends Fil200ParamModel {

    /** 初期表示区分 */
    private int fil290initFlg__ = 0;

    /** 個人キャビネット使用区分*/
    private String fil290CabinetUseKbn__ = null;

    /** キャビネット一括使用許可区分*/
    private String fil290CabinetAuthKbn__ = null;


    /** キャビネット使用許可 ユーザ・グループリスト */
    private String[] fil290CabinetSv__ = null;
    /** キャビネット使用許可 候補グループ*/
    private String fil290CabinetSltGroup__ = null;
    /** キャビネット使用許可 UI */
    private UserGroupSelector fil290CabinetUserUI__ = null;

    /** 容量制限区分*/
    private String fil290CapaKbn__ = null;
    /** 容量サイズ*/
    private String fil290CapaSize__ = null;
    /** 容量警告パーセント*/
    private String fil290CapaWarn__ = null;

    /** バージョン管理 表示区分*/
    private String fil290VerVisible__ = GSConstFile.DSP_KBN_OFF;
    /** バージョン管理区分*/
    private String fil290VerKbn__ = null;

    /** キャビネット使用率コンボ用リスト */
    private ArrayList< LabelValueBean > fil290CapaWarnLavel__ = null;
    /** キャビネット世代管理数コンボ用リスト */
    private ArrayList< LabelValueBean > fil290VerKbnLavel__ = null;

    /**
     * <p>fil290initFlg を取得します。
     * @return fil290initFlg
     */
    public int getFil290initFlg() {
        return fil290initFlg__;
    }
    /**
     * <p>fil290initFlg をセットします。
     * @param fil290initFlg fil290initFlg
     */
    public void setFil290initFlg(int fil290initFlg) {
        fil290initFlg__ = fil290initFlg;
    }

    /**
     * @return fil290CabinetUseKbn
     */
    public String getFil290CabinetUseKbn() {
        return fil290CabinetUseKbn__;
    }

    /**
     * @param fil290CabinetUseKbn 設定する fil290CabinetUseKbn
     */
    public void setFil290CabinetUseKbn(String fil290CabinetUseKbn) {
        fil290CabinetUseKbn__ = fil290CabinetUseKbn;
    }

    /**
     * @return fil290CabinetAuthKbn
     */
    public String getFil290CabinetAuthKbn() {
        return fil290CabinetAuthKbn__;
    }

    /**
     * @param fil290CabinetAuthKbn 設定する fil290CabinetAuthKbn
     */
    public void setFil290CabinetAuthKbn(String fil290CabinetAuthKbn) {
        fil290CabinetAuthKbn__ = fil290CabinetAuthKbn;
    }

    /**
     * @return fil290CabinetSv
     */
    public String[] getFil290CabinetSv() {
        return fil290CabinetSv__;
    }

    /**
     * @param fil290CabinetSv 設定する fil290CabinetSv
     */
    public void setFil290CabinetSv(String[] fil290CabinetSv) {
        fil290CabinetSv__ = fil290CabinetSv;
    }

    /**
     * <p>fil290CabinetUserUI を取得します。
     * @return fil290CabinetUserUI
     * @see jp.groupsession.v2.fil.fil290.Fil290ParamModel#fil290CabinetUserUI__
     */
    public UserGroupSelector getFil290CabinetUserUI() {
        return fil290CabinetUserUI__;
    }
    /**
     * <p>fil290CabinetUserUI をセットします。
     * @param fil290CabinetUserUI fil290CabinetUserUI
     * @see jp.groupsession.v2.fil.fil290.Fil290ParamModel#fil290CabinetUserUI__
     */
    public void setFil290CabinetUserUI(UserGroupSelector fil290CabinetUserUI) {
        fil290CabinetUserUI__ = fil290CabinetUserUI;
    }
    /**
     * @return fil290CapaKbn
     */
    public String getFil290CapaKbn() {
        return fil290CapaKbn__;
    }

    /**
     * @param fil290CapaKbn 設定する fil290CapaKbn
     */
    public void setFil290CapaKbn(String fil290CapaKbn) {
        fil290CapaKbn__ = fil290CapaKbn;
    }

    /**
     * @return fil290CapaSize
     */
    public String getFil290CapaSize() {
        return fil290CapaSize__;
    }

    /**
     * @param fil290CapaSize 設定する fil290CapaSize
     */
    public void setFil290CapaSize(String fil290CapaSize) {
        fil290CapaSize__ = fil290CapaSize;
    }

    /**
     * @return fil290CapaWarn
     */
    public String getFil290CapaWarn() {
        return fil290CapaWarn__;
    }

    /**
     * @param fil290CapaWarn 設定する fil290CapaWarn
     */
    public void setFil290CapaWarn(String fil290CapaWarn) {
        fil290CapaWarn__ = fil290CapaWarn;
    }

    /**
     * @return fil290VerVisible
     */
    public String getFil290VerVisible() {
        return fil290VerVisible__;
    }

    /**
     * @param fil290VerVisible 設定する fil290VerVisible
     */
    public void setFil290VerVisible(String fil290VerVisible) {
        fil290VerVisible__ = fil290VerVisible;
    }

    /**
     * @return fil290VerKbn
     */
    public String getFil290VerKbn() {
        return fil290VerKbn__;
    }

    /**
     * @param fil290VerKbn 設定する fil290VerKbn
     */
    public void setFil290VerKbn(String fil290VerKbn) {
        fil290VerKbn__ = fil290VerKbn;
    }

    /**
     * @return fil290CapaWarnLavel
     */
    public ArrayList<LabelValueBean> getFil290CapaWarnLavel() {
        return fil290CapaWarnLavel__;
    }

    /**
     * @param fil290CapaWarnLavel 設定する fil290CapaWarnLavel
     */
    public void setFil290CapaWarnLavel(ArrayList<LabelValueBean> fil290CapaWarnLavel) {
        fil290CapaWarnLavel__ = fil290CapaWarnLavel;
    }

    /**
     * @return fil290CabinetSltGroup
     */
    public String getFil290CabinetSltGroup() {
        return fil290CabinetSltGroup__;
    }

    /**
     * @param fil290CabinetSltGroup 設定する fil290CabinetSltGroup
     */
    public void setFil290CabinetSltGroup(String fil290CabinetSltGroup) {
        fil290CabinetSltGroup__ = fil290CabinetSltGroup;
    }

    /**
     * @return fil290VerKbnLavel
     */
    public ArrayList<LabelValueBean> getFil290VerKbnLavel() {
        return fil290VerKbnLavel__;
    }

    /**
     * @param fil290VerKbnLavel 設定する fil290VerKbnLavel
     */
    public void setFil290VerKbnLavel(ArrayList<LabelValueBean> fil290VerKbnLavel) {
        fil290VerKbnLavel__ = fil290VerKbnLavel;
    }
}
