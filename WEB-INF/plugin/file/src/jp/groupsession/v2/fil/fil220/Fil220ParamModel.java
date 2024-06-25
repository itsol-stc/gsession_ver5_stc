package jp.groupsession.v2.fil.fil220;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.fil.fil200.Fil200ParamModel;
import jp.groupsession.v2.fil.model.FileCabinetModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 管理者設定 キャビネット管理設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil220ParamModel extends Fil200ParamModel {

    /** キャビネット単一選択 */
    private String fil220sltRadio__ = null;
    /** キャビネット複数選択 */
    private String[] fil220sltCheck__ = null;
    /** 全て選択or解除用 */
    private String fil220allCheck__ = null;
    /** キャビネット区分 */
    private int fil220cabinetKbn__ = GSConstFile.CABINET_KBN_PUBLIC;
    /** キャビネット情報リスト */
    private List<FileCabinetModel> fil220cabinetList__ = new ArrayList<FileCabinetModel>();

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheckPackageEdit(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //キャビネット複数選択
        if (fil220sltCheck__ == null || fil220sltCheck__.length < 1) {

            GsMessage gsMsg = new GsMessage();
            String textCabinetSelect = gsMsg.getMessage(req, "fil.70");

            msg = new ActionMessage("error.select.required.text", textCabinetSelect);
            StrutsUtil.addMessage(errors, msg, "fil220sltCheck");
        }

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheckSort(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        String textCabinet = gsMsg.getMessage(req, "fil.23");

        //キャビネット複数選択
        if (StringUtil.isNullZeroString(fil220sltRadio__)) {
            msg = new ActionMessage("error.select.required.text",
                    textCabinet);
            StrutsUtil.addMessage(errors, msg, "fil220sltRadio");
        }

        return errors;
    }

    /**
     * <p>fil220cabinetList を取得します。
     * @return fil220cabinetList
     */
    public List<FileCabinetModel> getFil220cabinetList() {
        return fil220cabinetList__;
    }
    /**
     * <p>fil220cabinetList をセットします。
     * @param fil220cabinetList fil220cabinetList
     */
    public void setFil220cabinetList(List<FileCabinetModel> fil220cabinetList) {
        fil220cabinetList__ = fil220cabinetList;
    }
    /**
     * <p>fil220cabinetKbn を取得します。
     * @return fil220cabinetKbn
     * @see jp.groupsession.v2.fil.fil220.Fil220Form#fil220cabinetKbn__
     */
    public int getFil220cabinetKbn() {
        return fil220cabinetKbn__;
    }
    /**
     * <p>fil220cabinetKbn をセットします。
     * @param fil220cabinetKbn fil220cabinetKbn
     * @see jp.groupsession.v2.fil.fil220.Fil220Form#fil220cabinetKbn__
     */
    public void setFil220cabinetKbn(int fil220cabinetKbn) {
        fil220cabinetKbn__ = fil220cabinetKbn;
    }
    /**
     * <p>fil220allCheck を取得します。
     * @return fil220allCheck
     */
    public String getFil220allCheck() {
        return fil220allCheck__;
    }
    /**
     * <p>fil220allCheck をセットします。
     * @param fil220allCheck fil220allCheck
     */
    public void setFil220allCheck(String fil220allCheck) {
        fil220allCheck__ = fil220allCheck;
    }
    /**
     * <p>fil220sltRadio を取得します。
     * @return fil220sltRadio
     */
    public String getFil220sltRadio() {
        return fil220sltRadio__;
    }
    /**
     * <p>fil220sltRadio をセットします。
     * @param fil220sltRadio fil220sltRadio
     */
    public void setFil220sltRadio(String fil220sltRadio) {
        fil220sltRadio__ = fil220sltRadio;
    }

    /**
     * <p>fil220sltCheck をセットします。
     * @param fil220sltCheck fil220sltCheck
     */
    public void setFil220sltCheck(String[] fil220sltCheck) {
        fil220sltCheck__ = fil220sltCheck;
    }

    /**
     * <p>fil220sltCheck を取得します。
     * @return fil220sltCheck
     */
    public String[] getFil220sltCheck() {
        return fil220sltCheck__;
    }

}
