package jp.groupsession.v2.man.man420;

import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man420Form extends AbstractGsForm {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man420Form.class);

    /**
     *
     * <br>[機  能]入力データのチェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return アクションエラー
     */
    public ActionErrors validateForm(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsmsg = new GsMessage(reqMdl);

        //ユーザ自動インポートフラグは0か1のみ許可
        if (man420UsrImpFlg__ != 0 && man420UsrImpFlg__ != 1) {
            ActionMessage msg =
                    new ActionMessage("error.input.notvalidate.data",
                            gsmsg.getMessage("main.man420.1"));
            StrutsUtil.addMessage(errors, msg, "error.input.notvalidate.data");
        }

        //ユーザインポート時間フラグは0,1,2のみ許可
        if (man420UsrImpTimeSelect__ != 0
                && man420UsrImpTimeSelect__ != 1
                && man420UsrImpTimeSelect__ != 2) {
            ActionMessage msg =
                    new ActionMessage("error.input.notvalidate.data",
                            gsmsg.getMessage("main.man420.1"));
            StrutsUtil.addMessage(errors, msg, "error.input.notvalidate.data");
        }

        //開始時刻は0~23時のみ許可
        if (man420UsrImpFlg__ == 1) {
            int usrFrHour = NullDefault.getInt(man420UsrFrHour__, -1);
            if (usrFrHour == -1) {
                ActionMessage msg =
                        new ActionMessage("error.input.notvalidate.data",
                                gsmsg.getMessage("cmn.starttime"));
                StrutsUtil.addMessage(errors, msg, "error.input.notvalidate.data");
            } else if (usrFrHour < 0 || usrFrHour > 23) {
                ActionMessage msg =
                        new ActionMessage("error.input.lenge",
                                gsmsg.getMessage("cmn.starttime"),
                                0 + gsmsg.getMessage("cmn.hour.input"),
                                23 + gsmsg.getMessage("cmn.hour.input"));
                StrutsUtil.addMessage(errors, msg, "error.input.notvalidate.data");
            }
        }

        return errors;

    }

    /** 自動ユーザインポートフラグ */
    private int man420UsrImpFlg__ = 0;
    /** ユーザインポート時間フラグ */
    private int man420UsrImpTimeSelect__ = 0;
    /** ユーザインポート開始時 */
    private String man420UsrFrHour__ = null;
    /** ユーザインポート時リスト */
    private ArrayList < LabelValueBean > man420UsrHourLabel__ = null;
    /** 初期表示フラグ */
    private int man420InitFlg__ = 1;
    /** インポート対象フォルダ */
    private String man420ImportFolder__ = "";
    /** インポート成功フォルダ */
    private String man420ImpSuccessFolder__ = "";
    /** インポート失敗フォルダ */
    private String man420ImpFailedFolder__ = "";
    /**
     * <p>man420UsrImpAble を取得します。
     * @return man420UsrImpAble
     */
    public int getMan420UsrImpFlg() {
        return man420UsrImpFlg__;
    }

    /**
     * <p>man420UsrImpFlg__ をセットします。
     * @param man420UsrImpFlg man420UsrImpFlg
     */
    public void setMan420UsrImpFlg(int man420UsrImpFlg) {
        man420UsrImpFlg__ = man420UsrImpFlg;
    }

    /**
     * <p>man420UsrImpTimeFlg を取得します。
     * @return man420UsrImpTimeFlg
     */
    public int getMan420UsrImpTimeSelect() {
        return man420UsrImpTimeSelect__;
    }

    /**
     * <p>man420UsrImpTimeFlg をセットします。
     * @param man420UsrImpTimeSelect man420UsrImpTimeSelect
     */
    public void setMan420UsrImpTimeSelect(int man420UsrImpTimeSelect) {
        man420UsrImpTimeSelect__ = man420UsrImpTimeSelect;
    }

    /**
     * <p>man420UsrHourLabel を取得します。
     * @return man420UsrHourLabel
     */
    public ArrayList<LabelValueBean> getMan420UsrHourLabel() {
        return man420UsrHourLabel__;
    }

    /**
     * <p>man420UsrHourLabel をセットします。
     * @param man420UsrHourLabel man420UsrHourLabel
     */
    public void setMan420UsrHourLabel(
            ArrayList<LabelValueBean> man420UsrHourLabel) {
        man420UsrHourLabel__ = man420UsrHourLabel;
    }

    /**
     * <p>man420UsrFrHour を取得します。
     * @return man420UsrFrHour
     */
    public String getMan420UsrFrHour() {
        return man420UsrFrHour__;
    }

    /**
     * <p>man420UsrFrHour をセットします。
     * @param man420UsrFrHour man420UsrFrHour
     */
    public void setMan420UsrFrHour(String man420UsrFrHour) {
        man420UsrFrHour__ = man420UsrFrHour;
    }

    /**
     * <p>man420InitFlg を取得します。
     * @return man420InitFlg
     */
    public int getMan420InitFlg() {
        return man420InitFlg__;
    }

    /**
     * <p>man420InitFlg をセットします。
     * @param man420InitFlg man420InitFlg
     */
    public void setMan420InitFlg(int man420InitFlg) {
        man420InitFlg__ = man420InitFlg;
    }

    /**
     * <p>man420ImportFolder を取得します。
     * @return man420ImportFolder
     */
    public String getMan420ImportFolder() {
        return man420ImportFolder__;
    }

    /**
     * <p>man420ImportFolder をセットします。
     * @param man420ImportFolder man420ImportFolder
     */
    public void setMan420ImportFolder(String man420ImportFolder) {
        man420ImportFolder__ = man420ImportFolder;
    }
    /**
     * <p>man420ImpSuccessFolder を取得します。
     * @return man420ImpSuccessFolder
     */
    public String getMan420ImpSuccessFolder() {
        return man420ImpSuccessFolder__;
    }

    /**
     * <p>man420ImpSuccessFolder をセットします。
     * @param man420ImpSuccessFolder man420ImpSuccessFolder
     */
    public void setMan420ImpSuccessFolder(String man420ImpSuccessFolder) {
        man420ImpSuccessFolder__ = man420ImpSuccessFolder;
    }

    /**
     * <p>man420ImpFailedFolder を取得します。
     * @return man420ImpFailedFolder
     */
    public String getMan420ImpFailedFolder() {
        return man420ImpFailedFolder__;
    }

    /**
     * <p>man420ImpFailedFolder をセットします。
     * @param man420ImpFailedFolder man420ImpFailedFolder
     */
    public void setMan420ImpFailedFolder(String man420ImpFailedFolder) {
        man420ImpFailedFolder__ = man420ImpFailedFolder;
    }
}
