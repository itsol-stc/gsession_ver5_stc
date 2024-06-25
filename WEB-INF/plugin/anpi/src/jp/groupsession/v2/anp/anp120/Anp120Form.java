package jp.groupsession.v2.anp.anp120;

import jp.groupsession.v2.anp.AnpiValidateUtil;
import jp.groupsession.v2.anp.anp110.Anp110Form;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] 管理者設定・緊急連絡先編集画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp120Form extends Anp110Form {

    /** メールアドレス */
    private String anp120MailAdr__ = null;
    /** 電話番号 */
    private String anp120TelNo__ = null;

    /**
     * <p>メールアドレスを取得します
     * @return anp120MailAdr
     */
    public String getAnp120MailAdr() {
        return anp120MailAdr__;
    }

    /**
     * <p>メールアドレスを設定します
     * @param anp120MailAdr セットする anp120MailAdr
     */
    public void setAnp120MailAdr(String anp120MailAdr) {
        anp120MailAdr__ = anp120MailAdr;
    }

    /**
     * <p>電話番号を取得します
     * @return anp120TelNo
     */
    public String getAnp120TelNo() {
        return anp120TelNo__;
    }

    /**
     * <p>電話番号を設定します
     * @param anp120TelNo セットする anp120TelNo
     */
    public void setAnp120TelNo(String anp120TelNo) {
        anp120TelNo__ = anp120TelNo;
    }

    /**
     * <br>[機  能] 登録前の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return エラー
     */
    public ActionErrors validateAnp120(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //メールアドレス
        AnpiValidateUtil.validateMail(errors,
                anp120MailAdr__, "anp120MailAdr", gsMsg.getMessage("cmn.mailaddress"), false);

        //電話番号
        AnpiValidateUtil.validateTel(errors,
                anp120TelNo__, "anp120TelNo",
                gsMsg.getMessage("cmn.tel"), GSConstUser.MAX_LENGTH_TEL, false);

        return errors;
    }

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param cmn999Form 共通メッセージフォーム
     */
    public void setHiddenParamAnp120(Cmn999Form cmn999Form) {

        cmn999Form.addHiddenParam("anp120MailAdr", anp120MailAdr__);
        cmn999Form.addHiddenParam("anp120TelNo", anp120TelNo__);
    }
}