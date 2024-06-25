package jp.groupsession.v2.ptl.ptl120;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ptl.GSValidatePortal;
import jp.groupsession.v2.ptl.ptl110.Ptl110Form;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] ポータル ポートレットカテゴリ登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl120Form extends Ptl110Form {

    /** 初期表示フラグ */
    private int ptl120init__ = 0;
    /** カテゴリ名 */
    private String ptl120name__ = "";
    /** 備考 */
    private String ptl120biko__ = "";
    /** ポートレットカテゴリSID */
    private int ptlPtlCategorytSid__ = -1;
    /** 遷移元画面 0=ポートレット管理 1=ポートレットカテゴリ一覧 */
    private int ptlPlcBack__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validatePtl120(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.category.name");
        String biko = gsMsg.getMessage("cmn.memo");

        //カテゴリ名
        errors = GSValidatePortal.validateCmnFieldText(errors,
                                                  msg,
                                                  ptl120name__,
                                                  "ptl120name__",
                                                  100,
                                                  true);
        //備考
        errors = GSValidatePortal.validateFieldTextArea(errors,
                                                  biko,
                                                  ptl120biko__,
                                                  "ptl120biko__",
                                                  500,
                                                  false);
        return errors;
    }

    /**
     * @return ptl120init
     */
    public int getPtl120init() {
        return ptl120init__;
    }
    /**
     * @param ptl120init セットする ptl120init
     */
    public void setPtl120init(int ptl120init) {
        ptl120init__ = ptl120init;
    }
    /**
     * @return ptl120name
     */
    public String getPtl120name() {
        return ptl120name__;
    }
    /**
     * @param ptl120name セットする ptl120name
     */
    public void setPtl120name(String ptl120name) {
        ptl120name__ = ptl120name;
    }
    /**
     * @return ptl120biko
     */
    public String getPtl120biko() {
        return ptl120biko__;
    }
    /**
     * @param ptl120biko セットする ptl120biko
     */
    public void setPtl120biko(String ptl120biko) {
        ptl120biko__ = ptl120biko;
    }
    /**
     * @return ptlPtlCategorytSid
     */
    public int getPtlPtlCategorytSid() {
        return ptlPtlCategorytSid__;
    }
    /**
     * @param ptlPtlCategorytSid セットする ptlPtlCategorytSid
     */
    public void setPtlPtlCategorytSid(int ptlPtlCategorytSid) {
        ptlPtlCategorytSid__ = ptlPtlCategorytSid;
    }
    /**
     * @return ptlPlcBack
     */
    public int getPtlPlcBack() {
        return ptlPlcBack__;
    }
    /**
     * @param ptlPlcBack セットする ptlPlcBack
     */
    public void setPtlPlcBack(int ptlPlcBack) {
        ptlPlcBack__ = ptlPlcBack;
    }
}
