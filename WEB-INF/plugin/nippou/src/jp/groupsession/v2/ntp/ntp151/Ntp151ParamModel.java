package jp.groupsession.v2.ntp.ntp151;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.dao.NtpProcessDao;
import jp.groupsession.v2.ntp.ntp150.Ntp150ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 プロセス登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp151ParamModel extends Ntp150ParamModel {

    /** プロセスコード */
    private String ntp151ProcessCode__ = null;
    /** プロセス名 */
    private String ntp151ProcessName__ = null;
    /** 内容 */
    private String ntp151Naiyo__ = null;
    /** 業務一覧 */
    private List<LabelValueBean> ntp151GyomuList__ = null;

    /**
     * @return ntp151ProcessCode
     */
    public String getNtp151ProcessCode() {
        return ntp151ProcessCode__;
    }
    /**
     * @param ntp151ProcessCode 設定する ntp151ProcessCode
     */
    public void setNtp151ProcessCode(String ntp151ProcessCode) {
        ntp151ProcessCode__ = ntp151ProcessCode;
    }
    /**
     * @return ntp151ProcessName
     */
    public String getNtp151ProcessName() {
        return ntp151ProcessName__;
    }
    /**
     * @param ntp151ProcessName 設定する ntp151ProcessName
     */
    public void setNtp151ProcessName(String ntp151ProcessName) {
        ntp151ProcessName__ = ntp151ProcessName;
    }

    /**
     * @return ntp151Naiyo
     */
    public String getNtp151Naiyo() {
        return ntp151Naiyo__;
    }
    /**
     * @param ntp151Naiyo 設定する ntp151Naiyo
     */
    public void setNtp151Naiyo(String ntp151Naiyo) {
        ntp151Naiyo__ = ntp151Naiyo;
    }

    /**
     * @return ntp150GyomuList
     */
    public List<LabelValueBean> getNtp151GyomuList() {
        return ntp151GyomuList__;
    }
    /**
     * @param ntp151GyomuList 設定する ntp151GyomuList
     */
    public void setNtp151GyomuList(List<LabelValueBean> ntp151GyomuList) {
        ntp151GyomuList__ = ntp151GyomuList;
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

        //業種
        if (getNtp150NgySid() == -1) {
            //未入力はOK
            msg = new ActionMessage("error.select.required.text", GSConstNippou.TEXT_GYOMU);
            StrutsUtil.addMessage(
                      errors, msg, "ntp150NgySid__");
        }

        //プロセスコード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_PROCESS_CODE,
                ntp151ProcessCode__,
               "ntp151ProcessCode",
                GSConstNippou.MAX_LENGTH_PROCESS_CODE,
                true);

        if (errors.isEmpty()) {
            //プロセスコードの重複チェック
            int ngySid = getNtp150NgySid();
            int ngpSid = getNtp150NgpSid();
            if (getNtp150ProcMode().equals(GSConstNippou.CMD_ADD)) {
                ngySid = -1;
                ngpSid = -1;
            }
            NtpProcessDao dao = new NtpProcessDao(con);
            if (dao.existProcess(ngySid, ngpSid, ntp151ProcessCode__)) {
                String eprefix = "ntp151ProcessCode";
                String fieldfix = GSConstNippou.TEXT_PROCESS_CODE + ".";
                msg = new ActionMessage("error.select.dup.list", GSConstNippou.TEXT_PROCESS_CODE);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "ntp151ProcessCode");
            }
        }

        //プロセス名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_PROCESS_NAME,
                ntp151ProcessName__,
               "ntp151ProcessName",
                GSConstNippou.MAX_LENGTH_PROCESS_NAME,
                true);

        //内容入力チェック
        GSValidateNippou.validateFieldTextArea(errors,
                GSConstNippou.TEXT_NAIYO,
                ntp151Naiyo__,
               "ntp151Naiyo",
                GSConstNippou.MAX_LENGTH_NAIYO,
                false);
        return errors;
    }
}
