package jp.groupsession.v2.api.adress.companybase.belongaco;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] WEB API アドレス帳 会社拠点情報取得のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "address-companyBaseBelongAcoSid",
plugin = "address", name = "会社拠点情報取得",
url = "/api/address/companyBaseBelongAcoSid.do",
reqtype = "GET")
public class ApiCompanyBaseBelongAcoSidForm extends AbstractApiForm {
    /** 会社SID */
    String acoSid__ = null;
    /**
     * <p>acoSid を取得します。
     * @return acoSid
     */
    public String getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>acoSid をセットします。
     * @param acoSid acoSid
     */
    public void setAcoSid(String acoSid) {
        this.acoSid__ = acoSid;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return errors エラー
     */
    public ActionErrors validateCheck() {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        if (StringUtil.isNullZeroString(acoSid__)) {
            msg = new ActionMessage("error.input.required.text", GSConstAddress.TEXT_ACO_SID);
            StrutsUtil.addMessage(errors, msg, "acoSid");
            return errors;
        }
        if (!GSValidateUtil.isNumber(acoSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstAddress.TEXT_ACO_SID);
            StrutsUtil.addMessage(errors, msg, "acoSid");
            return errors;
        }
        return errors;
    }
}
