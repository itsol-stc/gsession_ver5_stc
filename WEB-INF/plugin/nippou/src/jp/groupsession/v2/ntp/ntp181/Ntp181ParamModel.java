package jp.groupsession.v2.ntp.ntp181;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.dao.NtpKthouhouDao;
import jp.groupsession.v2.ntp.ntp180.Ntp180ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 活動方法登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp181ParamModel extends Ntp180ParamModel {

    /** 活動方法コード */
    private String ntp181KthouhouCode__ = null;
    /** 活動方法名 */
    private String ntp181KthouhouName__ = null;

    /**
     * @return ntp181KthouhouCode
     */
    public String getNtp181KthouhouCode() {
        return ntp181KthouhouCode__;
    }
    /**
     * @param ntp181KthouhouCode 設定する ntp181KthouhouCode
     */
    public void setNtp181KthouhouCode(String ntp181KthouhouCode) {
        ntp181KthouhouCode__ = ntp181KthouhouCode;
    }
    /**
     * @return ntp181KthouhouName
     */
    public String getNtp181KthouhouName() {
        return ntp181KthouhouName__;
    }
    /**
     * @param ntp181KthouhouName 設定する ntp181KthouhouName
     */
    public void setNtp181KthouhouName(String ntp181KthouhouName) {
        ntp181KthouhouName__ = ntp181KthouhouName;
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

        //活動方法コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_KTHOUHOU_CODE,
                ntp181KthouhouCode__,
               "ntp181KthouhouCode",
                GSConstNippou.MAX_LENGTH_KTHOUHOU_CODE,
                true);

        if (errors.isEmpty()) {
            //活動方法コードの重複チェック
            int nkhSid = getNtp180NkhSid();
            if (getNtp180ProcMode().equals(GSConstNippou.CMD_ADD)) {
                nkhSid = -1;
            }
            NtpKthouhouDao dao = new NtpKthouhouDao(con);
            if (dao.existKthouhou(nkhSid, ntp181KthouhouCode__)) {
                String eprefix = "ntp181KthouhouCode";
                String fieldfix = GSConstNippou.TEXT_KTHOUHOU_CODE + ".";
                msg = new ActionMessage("error.select.dup.list", GSConstNippou.TEXT_KTHOUHOU_CODE);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "ntp181KthouhouCode");
            }
        }

        //活動方法名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_KTHOUHOU_NAME,
                ntp181KthouhouName__,
               "ntp181KthouhouName",
                GSConstNippou.MAX_LENGTH_KTHOUHOU_NAME,
                true);
        return errors;
    }
}
