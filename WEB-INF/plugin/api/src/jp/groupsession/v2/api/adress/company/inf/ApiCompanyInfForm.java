package jp.groupsession.v2.api.adress.company.inf;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;


/**
 * <br>[機  能] WEB API アドレス帳 会社情報取得フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "address-companyInfo",
plugin = "address", name = "会社詳細情報取得",
url = "/api/address/companyInfo.do",
reqtype = "GET")
public class ApiCompanyInfForm extends AbstractApiForm {
    /** 会社SID */
    @ApiParam(name = "acoSid", viewName = "会社SID")
    String[] acoSid__ = null;

    /**
     * <p>acoSid を取得します。
     * @return acoSid
     */
    public String[] getAcoSid() {
        return acoSid__;
    }

    /**
     * <p>acoSid をセットします。
     * @param acoSid acoSid
     */
    public void setAcoSid(String[] acoSid) {
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
        if (acoSid__ == null) {
            msg = new ActionMessage("error.input.required.text", GSConstAddress.TEXT_ACO_SID);
            StrutsUtil.addMessage(errors, msg, "acoSid");
            return errors;
        }
        for (String acoSid : acoSid__) {
            if (StringUtil.isNullZeroString(acoSid)) {
                msg = new ActionMessage("error.input.required.text", GSConstAddress.TEXT_ACO_SID);
                StrutsUtil.addMessage(errors, msg, "acoSid");
                return errors;
            }
            if (!GSValidateUtil.isNumber(acoSid)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstAddress.TEXT_ACO_SID);
                StrutsUtil.addMessage(errors, msg, "acoSid");
                return errors;
            }

        }
        return errors;
    }

}
