package jp.groupsession.v2.ntp.ntp171;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.dao.NtpKtbunruiDao;
import jp.groupsession.v2.ntp.ntp170.Ntp170ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 活動分類登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp171ParamModel extends Ntp170ParamModel {

    /** 活動分類コード */
    private String ntp171KtbunruiCode__ = null;
    /** 活動分類名 */
    private String ntp171KtbunruiName__ = null;
    /** 関連活動設定 */
    private int ntp171TieupKbn__ = GSConstNippou.TIEUP_NO;

    /**
     * @return ntp171KtbunruiCode
     */
    public String getNtp171KtbunruiCode() {
        return ntp171KtbunruiCode__;
    }
    /**
     * @param ntp171KtbunruiCode 設定する ntp171KtbunruiCode
     */
    public void setNtp171KtbunruiCode(String ntp171KtbunruiCode) {
        ntp171KtbunruiCode__ = ntp171KtbunruiCode;
    }
    /**
     * @return ntp171KtbunruiName
     */
    public String getNtp171KtbunruiName() {
        return ntp171KtbunruiName__;
    }
    /**
     * @param ntp171KtbunruiName 設定する ntp171KtbunruiName
     */
    public void setNtp171KtbunruiName(String ntp171KtbunruiName) {
        ntp171KtbunruiName__ = ntp171KtbunruiName;
    }

    /**
     * @return ntp171TieupKbn
     */
    public int getNtp171TieupKbn() {
        return ntp171TieupKbn__;
    }
    /**
     * @param ntp171TieupKbn 設定する ntp171TieupKbn
     */
    public void setNtp171TieupKbn(int ntp171TieupKbn) {
        ntp171TieupKbn__ = ntp171TieupKbn;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //活動分類コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_KTBUNRUI_CODE,
                ntp171KtbunruiCode__,
               "ntp171KtbunruiCode",
                GSConstNippou.MAX_LENGTH_KTBUNRUI_CODE,
                true);

        if (errors.isEmpty()) {
            //活動分類コードの重複チェック
            int nkbSid = getNtp170NkbSid();
            if (getNtp170ProcMode().equals(GSConstNippou.CMD_ADD)) {
                nkbSid = -1;
            }
            NtpKtbunruiDao dao = new NtpKtbunruiDao(con);
            if (dao.existKtbunrui(nkbSid, ntp171KtbunruiCode__)) {
                String eprefix = "ntp171KtbunruiCode";
                String fieldfix = GSConstNippou.TEXT_KTBUNRUI_CODE + ".";
                msg = new ActionMessage("error.select.dup.list", GSConstNippou.TEXT_KTBUNRUI_CODE);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "ntp171KtbunruiCode");
            }
        }

        //活動分類名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_KTBUNRUI_NAME,
                ntp171KtbunruiName__,
               "ntp171KtbunruiName",
                GSConstNippou.MAX_LENGTH_KTBUNRUI_NAME,
                true);

        return errors;
    }
}
