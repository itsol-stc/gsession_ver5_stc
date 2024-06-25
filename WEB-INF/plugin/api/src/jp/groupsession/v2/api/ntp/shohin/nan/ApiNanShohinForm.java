package jp.groupsession.v2.api.ntp.shohin.nan;

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
 * <br>[機  能] WEBAPI 日報商品一覧取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-ankenshohin",
plugin = "nippou", name = "案件の商品一覧取得",
url = "/api/ntp/ankenshohin.do")
public class ApiNanShohinForm extends AbstractApiForm {
    /** 案件SID */
    @ApiParam(name = "nanSid", viewName = "案件SID")
    private String nanSid__ = null;

    /**
     * <p>nanSid を取得します。
     * @return nanSid
     */
    public String getNanSid() {
        return nanSid__;
    }

    /**
     * <p>nanSid をセットします。
     * @param nanSid nanSid
     */
    public void setNanSid(String nanSid) {
        this.nanSid__ = nanSid;
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
        if (StringUtil.isNullZeroString(nanSid__)) {
            msg = new ActionMessage("error.input.required.text", GSConstNippou.TEXT_ANKEN_SID);
            StrutsUtil.addMessage(errors, msg, "nanSid");
            return errors;
        }
        if (!GSValidateUtil.isNumber(nanSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_ANKEN_SID);
            StrutsUtil.addMessage(errors, msg, "nanSid");
            return errors;
        }
        return errors;
    }
}
