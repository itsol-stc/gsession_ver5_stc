package jp.groupsession.v2.api.ntp.aco.history;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能]  日報で利用した企業拠点履歴を返すWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-companyBaseHistory",
plugin = "nippou", name = "会社拠点一覧取得",
url = "/api/ntp/companyBaseHistory.do", reqtype = "GET")
public class ApiNtpCompanyBaseHistoryForm extends AbstractApiForm {
    /** 取得開始位置 */
    String  page__ = "1";
    /** 取得件数 */
    String  count__ = "10";
    /**
     * <p>page を取得します。
     * @return page
     */
    public String getPage() {
        return page__;
    }

    /**
     * <p>page をセットします。
     * @param page page
     */
    public void setPage(String page) {
        page__ = page;
    }

    /**
     * <p>count を取得します。
     * @return count
     */
    public String getCount() {
        return count__;
    }

    /**
     * <p>count をセットします。
     * @param count count
     */
    public void setCount(String count) {
        count__ = count;
    }

    /**
     * 入力チェック
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @return errors
     */
    public ActionErrors validateCheck() {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;


       page__ = NullDefault.getString(page__, "1");
        if (!GSValidateUtil.isNumber(page__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "page");
            StrutsUtil.addMessage(errors, msg, "page");
        }

        count__ = NullDefault.getString(count__, "10");
        if (!GSValidateUtil.isNumber(count__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "count");
            StrutsUtil.addMessage(errors, msg, "count");
        }
        return errors;
    }

}
