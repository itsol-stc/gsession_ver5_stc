package jp.groupsession.v2.api.ntp.nippou.edit.template;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEBAPI 日報テンプレート取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouEditTemplate",
plugin = "nippou", name = "日報テンプレート取得",
url = "/api/ntp/nippouEditTemplate.do", reqtype = "GET")
public class ApiNippouEditTemplateForm extends AbstractApiForm {

    /** ユーザSID */
    String usrSid__;
    /** 取得日 */
    String date__;
    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>date を取得します。
     * @return date
     */
    public String getDate() {
        return date__;
    }
    /**
     * <p>date をセットします。
     * @param date date
     */
    public void setDate(String date) {
        date__ = date;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req) throws SQLException {
        ActionErrors errors = new ActionErrors();

        return errors;
    }
}
