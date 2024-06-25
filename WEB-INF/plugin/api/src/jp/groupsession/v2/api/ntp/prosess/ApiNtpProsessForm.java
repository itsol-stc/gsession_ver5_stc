package jp.groupsession.v2.api.ntp.prosess;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.ntp.GSConstNippou;
/**
 * <br>[機  能] WEBAPI 日報プロセス一覧取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-gyoumuprosess",
plugin = "nippou", name = "業務別プロセス一覧取得",
url = "/api/ntp/gyoumuprosess.do", reqtype = "GET")
public class ApiNtpProsessForm extends AbstractApiForm {
    /** 業務SID*/
    @ApiParam(name = "ngySid", viewName = "業務SID")
    private String ngySid__;

    /**
     * <p>ngySid を取得します。
     * @return ngySid
     */
    public String getNgySid() {
        return ngySid__;
    }

    /**
     * <p>ngySid をセットします。
     * @param ngySid ngySid
     */
    public void setNgySid(String ngySid) {
        this.ngySid__ = ngySid;
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
        ActionMessage msg = null;
        if (StringUtil.isNullZeroString(ngySid__)) {
            msg = new ActionMessage("error.input.required.text", GSConstNippou.TEXT_GYOMU_SID);
            StrutsUtil.addMessage(errors, msg, "ngySid");
            return errors;
        }
        if (!GSValidateUtil.isNumber(ngySid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_GYOMU_SID);
            StrutsUtil.addMessage(errors, msg, "ngySid");
            return errors;
        }
        return errors;
    }
}
