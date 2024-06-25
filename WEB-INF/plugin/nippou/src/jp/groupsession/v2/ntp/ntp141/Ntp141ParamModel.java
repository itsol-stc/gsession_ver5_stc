package jp.groupsession.v2.ntp.ntp141;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.dao.NtpGyomuDao;
import jp.groupsession.v2.ntp.ntp140.Ntp140ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 業種登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp141ParamModel extends Ntp140ParamModel {

    /** 業務コード */
    private String ntp141GyomuCode__ = null;
    /** 業務名 */
    private String ntp141GyomuName__ = null;
    /**
     * @return ntp141GyomuCode
     */
    public String getNtp141GyomuCode() {
        return ntp141GyomuCode__;
    }

    /**
     * @param ntp141GyomuCode 設定する ntp141GyomuCode
     */
    public void setNtp141GyomuCode(String ntp141GyomuCode) {
        ntp141GyomuCode__ = ntp141GyomuCode;
    }

    /**
     * @return ntp141GyomuName
     */
    public String getNtp141GyomuName() {
        return ntp141GyomuName__;
    }

    /**
     * @param ntp141GyomuName 設定する ntp141GyomuName
     */
    public void setNtp141GyomuName(String ntp141GyomuName) {
        ntp141GyomuName__ = ntp141GyomuName;
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

        //業種コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_GYOMU_CODE,
                ntp141GyomuCode__,
               "ntp141GyomuCode",
                GSConstNippou.MAX_LENGTH_GYOMU_CODE,
                true);

        if (errors.isEmpty()) {
            //業種コードの重複チェック
            int ngySid = getNtp140NgySid();
            if (getNtp140ProcMode().equals(GSConstNippou.CMD_ADD)) {
                ngySid = -1;
            }
            NtpGyomuDao dao = new NtpGyomuDao(con);
            if (dao.existGyomu(ngySid, ntp141GyomuCode__)) {
                String eprefix = "ntp141GyomuCode";
                String fieldfix = GSConstNippou.TEXT_GYOMU_CODE + ".";
                msg = new ActionMessage("error.select.dup.list", GSConstNippou.TEXT_GYOMU_CODE);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "ntp141GyomuCode");
            }
        }

        //業種名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_GYOMU_NAME,
                ntp141GyomuName__,
               "ntp141GyomuName",
                GSConstNippou.MAX_LENGTH_GYOMU_NAME,
                true);
        return errors;
    }
}
