package jp.groupsession.v2.ntp.ntp191;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.dao.NtpContactDao;
import jp.groupsession.v2.ntp.ntp190.Ntp190Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 顧客源泉登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp191Form extends Ntp190Form {

    /** コンタクトコード */
    private String ntp191ContactCode__ = null;
    /** コンタクト名 */
    private String ntp191ContactName__ = null;

    /**
     * @return ntp191ContactCode
     */
    public String getNtp191ContactCode() {
        return ntp191ContactCode__;
    }
    /**
     * @param ntp191ContactCode 設定する ntp191ContactCode
     */
    public void setNtp191ContactCode(String ntp191ContactCode) {
        ntp191ContactCode__ = ntp191ContactCode;
    }
    /**
     * @return ntp191ContactName
     */
    public String getNtp191ContactName() {
        return ntp191ContactName__;
    }
    /**
     * @param ntp191ContactName 設定する ntp191ContactName
     */
    public void setNtp191ContactName(String ntp191ContactName) {
        ntp191ContactName__ = ntp191ContactName;
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

        //コンタクトコード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_CONTACT_CODE,
                ntp191ContactCode__,
               "ntp191ContactCode",
                GSConstNippou.MAX_LENGTH_CONTACT_CODE,
                true);

        if (errors.isEmpty()) {
            //コンタクトコードの重複チェック
            int ncnSid = getNtp190NcnSid();
            if (getNtp190ProcMode().equals(GSConstNippou.CMD_ADD)) {
                ncnSid = -1;
            }
            NtpContactDao dao = new NtpContactDao(con);
            if (dao.existContact(ncnSid, ntp191ContactCode__)) {
                String eprefix = "ntp191ContactCode";
                String fieldfix = GSConstNippou.TEXT_CONTACT_CODE + ".";
                msg = new ActionMessage("error.select.dup.list", GSConstNippou.TEXT_CONTACT_CODE);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "ntp191ContactCode");
            }
        }

        //コンタクト名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_CONTACT_NAME,
                ntp191ContactName__,
               "ntp191ContactName",
                GSConstNippou.MAX_LENGTH_CONTACT_NAME,
                true);
        return errors;
    }
}
