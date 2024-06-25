package jp.groupsession.v2.anp.anp100;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.anp.AnpiValidateUtil;
import jp.groupsession.v2.anp.GSConstAnpi;
import jp.groupsession.v2.anp.anp090.Anp090Form;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 管理者設定・メールテンプレート編集画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp100Form extends Anp090Form {

    /** テンプレート名 */
    private String anp100Title__;
    /** 件名 */
    private String anp100Subject__;
    /** 本文１ */
    private String anp100Text1__;
    /** 本文２ */
    private String anp100Text2__;
    /**
     * <p>anp100Title を取得します。
     * @return anp100Title
     */
    public String getAnp100Title() {
        return anp100Title__;
    }
    /**
     * <p>anp100Title をセットします。
     * @param anp100Title anp100Title
     */
    public void setAnp100Title(String anp100Title) {
        anp100Title__ = anp100Title;
    }
    /**
     * <p>anp100Subject を取得します。
     * @return anp100Subject
     */
    public String getAnp100Subject() {
        return anp100Subject__;
    }
    /**
     * <p>anp100Subject をセットします。
     * @param anp100Subject anp100Subject
     */
    public void setAnp100Subject(String anp100Subject) {
        anp100Subject__ = anp100Subject;
    }
    /**
     * <p>anp100Text1 を取得します。
     * @return anp100Text1
     */
    public String getAnp100Text1() {
        return anp100Text1__;
    }
    /**
     * <p>anp100Text1 をセットします。
     * @param anp100Text1 anp100Text1
     */
    public void setAnp100Text1(String anp100Text1) {
        anp100Text1__ = anp100Text1;
    }
    /**
     * <p>anp100Text2 を取得します。
     * @return anp100Text2
     */
    public String getAnp100Text2() {
        return anp100Text2__;
    }
    /**
     * <p>anp100Text2 をセットします。
     * @param anp100Text2 anp100Text2
     */
    public void setAnp100Text2(String anp100Text2) {
        anp100Text2__ = anp100Text2;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return エラー
     */
    public ActionErrors validateAnp100(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //テンプレート名
        AnpiValidateUtil.validateTextField(errors,
                anp100Title__, "anp100Title",
                gsMsg.getMessage("anp.anp100.02"), GSConstAnpi.MAXLENGTH_SUBJECT, true);

        //件名
        AnpiValidateUtil.validateTextField(errors,
                anp100Subject__, "anp100Subject",
                gsMsg.getMessage("cmn.subject"), GSConstAnpi.MAXLENGTH_SUBJECT, true);

        //本文
        AnpiValidateUtil.validateTextAreaField(errors,
                anp100Text1__, "anp100Text1",
                gsMsg.getMessage("anp.body1"), GSConstAnpi.MAXLENGTH_TEXT1, true);

        //本文
        AnpiValidateUtil.validateTextAreaField(errors,
                anp100Text2__, "anp100Text2",
                gsMsg.getMessage("anp.body2"), GSConstAnpi.MAXLENGTH_TEXT2, false);

        return errors;
    }

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     */
    public void setHiddenParamAnp100(Cmn999Form cmn999Form) {

        cmn999Form.addHiddenParam("anp090SelectSid", this.getAnp090SelectSid());
        cmn999Form.addHiddenParam("anp100Title", anp100Title__);
        cmn999Form.addHiddenParam("anp100Subject", anp100Subject__);
        cmn999Form.addHiddenParam("anp100Text1", anp100Text1__);
        cmn999Form.addHiddenParam("anp100Text2", anp100Text2__);
    }
}