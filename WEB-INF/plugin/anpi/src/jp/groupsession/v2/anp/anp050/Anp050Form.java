package jp.groupsession.v2.anp.anp050;

import jp.groupsession.v2.anp.AnpiValidateUtil;
import jp.groupsession.v2.anp.anp030.Anp030Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.struts.action.ActionErrors;


/**
 * <br>[機  能] 個人設定・連絡先設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Anp050Form extends Anp030Form {

    /** 表示中のユーザSID */
    private int anp050UserSid__ = 0;
    /** メールアドレス */
    private String anp050Mail__ = null;
    /** 電話番号 */
    private String anp050TelNo__ = null;


    /**
     * <p>ユーザSIDを取得する
     * @return anp050AnpiSid
     */
    public int getAnp050UserSid() {
        return anp050UserSid__;
    }

    /**
     * <p>ユーザSIDを設定する
     * @param anp050UserSid セットする anp050UserSid
     */
    public void setAnp050UserSid(int anp050UserSid) {
        anp050UserSid__ = anp050UserSid;
    }

    /**
     * <p>メールアドレスを取得する
     * @return anp050Mail
     */
    public String getAnp050Mail() {
        return anp050Mail__;
    }

    /**
     * <p>メールアドレスを設定する
     * @param anp050Mail セットする anp050Mail
     */
    public void setAnp050Mail(String anp050Mail) {
        anp050Mail__ = anp050Mail;
    }

    /**
     * <p>電話番号を取得する
     * @return anp050TelNo
     */
    public String getAnp050TelNo() {
        return anp050TelNo__;
    }

    /**
     * <p>電話番号を設定する
     * @param anp050TelNo セットする anp050TelNo
     */
    public void setAnp050TelNo(String anp050TelNo) {
        anp050TelNo__ = anp050TelNo;
    }


    /**
     * <br>[機  能] 登録前の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return エラー
     */
    public ActionErrors validateAnp050(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //メールアドレス
        AnpiValidateUtil.validateMail(errors,
                anp050Mail__, "anp050Mail", gsMsg.getMessage("cmn.mailaddress"), false);

        //電話番号
        AnpiValidateUtil.validateTel(errors,
                anp050TelNo__, "anp050TelNo",
                gsMsg.getMessage("cmn.tel"), GSConstUser.MAX_LENGTH_TEL, false);

        return errors;
    }
}