package jp.groupsession.v2.fil.fil290;

import java.sql.Connection;
import java.sql.SQLException;
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
import jp.groupsession.v2.fil.dao.FileAconfDao;
import jp.groupsession.v2.fil.fil200.Fil200Form;
import jp.groupsession.v2.fil.model.FileAconfModel;
import jp.groupsession.v2.fil.util.FilValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 管理者設定 個人キャビネット使用許可設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil290Form extends Fil200Form implements ISelectorUseForm {

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
    private UserGroupSelector fil290CabinetUserUI__ =
            UserGroupSelector.builder()
            .chainLabel(new GsMessageReq("fil.fil290.5", null))
            .chainType(EnumSelectType.USERGROUP)
            .chainSelect(Select.builder()
                    .chainParameterName(
                            "fil290CabinetSv")
                    )
            .chainGroupSelectionParamName("fil290CabinetSltGroup")
            .build();

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
     * @see jp.groupsession.v2.fil.fil290.Fil290Form#fil290CabinetUserUI__
     */
    public UserGroupSelector getFil290CabinetUserUI() {
        return fil290CabinetUserUI__;
    }
    /**
     * <p>fil290CabinetUserUI をセットします。
     * @param fil290CabinetUserUI fil290CabinetUserUI
     * @see jp.groupsession.v2.fil.fil290.Fil290Form#fil290CabinetUserUI__
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

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req, Connection con) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();

        // 管理者情報を取得
        FileAconfDao aconfDao = new FileAconfDao(con);
        FileAconfModel aconf = null;
        try {
            aconf = aconfDao.select();
        } catch (SQLException e) {
        }
        if (aconf == null) {
            aconf = new FileAconfModel();
            aconf.init();
        }

        // 個人キャビネット使用フラグ
        int cabinetUseKbn = NullDefault.getInt(fil290CabinetUseKbn__, -1);
        if (cabinetUseKbn == GSConstFile.CABINET_PRIVATE_NOT_USE) {
            // 個人キャビネット未使用時にはここでチェック終了
            return errors;
        } else if (cabinetUseKbn == GSConstFile.CABINET_PRIVATE_USE) {
            // 個人キャビネット使用時
        } else {
            // 不正な値の場合、エラーメッセージを出してチェック終了
            String textCabinetPermit = gsMsg.getMessage(req, "fil.fil290.1");
            msg = new ActionMessage("error.select.required.text",
                    textCabinetPermit);
            StrutsUtil.addMessage(errors, msg, "fil290CabinetUseKbn");
            return errors;
        }

        // キャビネット使用許可ユーザ
        int cabinetAuthKbn = NullDefault.getInt(fil290CabinetAuthKbn__, -1);
        if (cabinetAuthKbn == GSConstFile.CABINET_AUTH_ALL) {
            // 全ユーザに許可した場合
        } else if (cabinetAuthKbn == GSConstFile.CABINET_AUTH_USER) {
            // 指定ユーザに許可した場合、ここでチェック
            String textCabinetPermit = gsMsg.getMessage(req, "fil.fil290.5");

            if (fil290CabinetSv__ == null || fil290CabinetSv__.length < 1) {
                msg = new ActionMessage("error.select.required.text",
                                         textCabinetPermit + " "
                                         + gsMsg.getMessage(req, "fil.fil290.8"));
                StrutsUtil.addMessage(errors, msg, "fil290CabinetSv");
            }
        } else {
            // 不正な値の場合、エラーメッセージ
            String textFileSize = gsMsg.getMessage(req, "fil.fil290.5");
            msg = new ActionMessage("error.select.required.text", textFileSize);
            StrutsUtil.addMessage(errors, msg, "fil210FileSize");
        }

        // 容量制限
        int capaKbn = NullDefault.getInt(fil290CapaKbn__, -1);
        if (capaKbn == GSConstFile.CAPA_KBN_ON) {
            // 容量制限がある場合、ここで詳細チェック

            //キャビネット容量上限
            String textCabinetCapaSize = gsMsg.getMessage(req, "fil.4");
            FilValidateUtil.validateTextFieldOfNumber(errors, fil290CapaSize__, "fil290CapaSize",
                    textCabinetCapaSize, GSConstFile.MAX_LENGTH_CAPA, true);

            // キャビネット警告値の不正値チェック
            int warnKbn = NullDefault.getInt(fil290CapaWarn__, -1);
            if (warnKbn < 0 || warnKbn > 100) {
                String textCabinetAcFull = gsMsg.getMessage(req, "fil.fil030kn.1");
                msg = new ActionMessage(
                                "error.input.format.text", textCabinetAcFull);
                StrutsUtil.addMessage(errors, msg, "fil030CapaWarn");
            }
        } else if (capaKbn == GSConstFile.CAPA_KBN_OFF) {
            // 容量制限がない場合
        } else {
            // 不正な値の場合、エラーメッセージ
            String textSaveDays = gsMsg.getMessage(req, "fil.3");
            msg = new ActionMessage("error.select.required.text", textSaveDays);
            StrutsUtil.addMessage(errors, msg, "fil290CapaKbn");
        }

        // バージョン管理
        if (aconf.getFacVerKbn() == GSConstFile.VERSION_KBN_ON) {
            // バージョン管理数
            int verLvKbn = NullDefault.getInt(fil290VerKbn__, -1);
            if (verLvKbn < 0 || verLvKbn > GSConstFile.MAX_LEVEL) {
                String textCabinetAcFull = gsMsg.getMessage(req, "fil.68");
                msg = new ActionMessage(
                                "error.input.format.text", textCabinetAcFull);
                StrutsUtil.addMessage(errors, msg, "fil290VerKbn");
            }
        }
        return errors;
    }


    /**
     * <br>[機  能] 出力するログメッセージを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return 出力するログメッセージ
     * @throws SQLException SQL実行例外
     */
    public String getOutputLog(HttpServletRequest req) {
        StringBuffer logMsg = new StringBuffer();
        GsMessage gsMsg = new GsMessage();

        // 個人キャビネット使用フラグ
        logMsg.append("[");
        logMsg.append(gsMsg.getMessage(req, "fil.fil290.1"));
        logMsg.append("]");
        if (fil290CabinetUseKbn__.equals(
                String.valueOf(GSConstFile.CABINET_PRIVATE_USE))) {
            logMsg.append(gsMsg.getMessage(req, "fil.fil290.2"));
        } else if (fil290CabinetUseKbn__.equals(
                String.valueOf(GSConstFile.CABINET_PRIVATE_NOT_USE))) {
            // 個人キャビネット未使用時にはここでチェック終了
            logMsg.append(gsMsg.getMessage(req, "fil.fil290.3"));
            return logMsg.toString();
        } else {
            // 不明な値の為、そのままの値を表示
            logMsg.append(fil290CabinetUseKbn__);
        }
        logMsg.append("\r\n");

        // キャビネット使用許可ユーザ
        logMsg.append("[");
        logMsg.append(gsMsg.getMessage(req, "fil.fil290.5"));
        logMsg.append("]");
        if (fil290CabinetAuthKbn__.equals(String.valueOf(GSConstFile.CABINET_AUTH_ALL))) {
            logMsg.append(gsMsg.getMessage(req, "fil.fil290.6"));
        } else if (fil290CabinetAuthKbn__.equals(String.valueOf(GSConstFile.CABINET_AUTH_USER))) {
            logMsg.append(gsMsg.getMessage(req, "fil.fil290.7"));
        } else {
            // 不明な値の為、そのままの値を表示
            logMsg.append(fil290CabinetAuthKbn__);
        }
        logMsg.append("\r\n");

        // 容量制限
        logMsg.append("[");
        logMsg.append(gsMsg.getMessage(req, "fil.3"));
        logMsg.append("]");
        if (fil290CapaKbn__.equals(String.valueOf(GSConstFile.CAPA_KBN_OFF))) {
            logMsg.append(gsMsg.getMessage(req, "cmn.noset"));
        } else if (fil290CapaKbn__.equals(String.valueOf(GSConstFile.CAPA_KBN_ON))) {
            logMsg.append(gsMsg.getMessage(req, "cmn.setting.do"));
            logMsg.append("\r\n");

            logMsg.append("[");
            logMsg.append(gsMsg.getMessage(req, "fil.4"));
            logMsg.append("]");
            logMsg.append(fil290CapaSize__);
            logMsg.append("MB");
            logMsg.append("\r\n");

            logMsg.append("[");
            logMsg.append(gsMsg.getMessage(req, "fil.fil030kn.1"));
            logMsg.append("]");
            if (fil290CapaWarn__ != null && !fil290CapaWarn__.equals(String.valueOf(0))) {
                logMsg.append(fil290CapaWarn__);
                logMsg.append("%");
            } else {
                logMsg.append(gsMsg.getMessage(req, "cmn.specified.no")); // 指定なし
            }
        }
        logMsg.append("\r\n");

        //バージョン管理
        logMsg.append("[");
        logMsg.append(gsMsg.getMessage(req, "fil.5"));
        logMsg.append("]");
        logMsg.append(fil290VerKbn__); // 指定なし
        logMsg.append(gsMsg.getMessage(req, "fil.6"));

        return logMsg.toString();
    }
}
