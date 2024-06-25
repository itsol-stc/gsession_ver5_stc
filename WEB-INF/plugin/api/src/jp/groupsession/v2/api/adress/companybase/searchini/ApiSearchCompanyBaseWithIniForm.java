package jp.groupsession.v2.api.adress.companybase.searchini;



import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] WEB API アドレス帳 先頭一文字で絞り込んだ会社情報の一覧取得用フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "address-searchCompanyBaseWithAcoSini",
plugin = "address", name = "会社拠点検索（会社名カナ先頭１文字）",
url = "/api/address/searchCompanyBaseWithAcoSini.do",
reqtype = "GET")
public class ApiSearchCompanyBaseWithIniForm extends AbstractApiForm {
    /** 会社名カナ 先頭一文字  */
    String acoSini__;
    /** 結果を取得する件数 */
    String results__ = null;
    /** 取得開始位置 */
    String  page__ = null;
    /** アドレスなしのデータを含む*/
    String  flgNoAdress__ = "0";
    /**
     * <p>acoSini を取得します。
     * @return acoSini
     */
    public String getAcoSini() {
        return acoSini__;
    }
    /**
     * <p>acoSini をセットします。
     * @param acoSini acoSini
     */
    public void setAcoSini(String acoSini) {
        this.acoSini__ = acoSini;
    }
    /**
     * <p>results を取得します。
     * @return results
     */
    public String getResults() {
        return results__;
    }
    /**
     * <p>results をセットします。
     * @param results results
     */
    public void setResults(String results) {
        this.results__ = results;
    }
    /**
     * <p>page を取得します。
     * @return start
     */
    public String getPage() {
        return page__;
    }
    /**
     * <p>page をセットします。
     * @param page page
     */
    public void setPage(String page) {
        this.page__ = page;
    }

    /**
     * <p>flgNoAdress を取得します。
     * @return flgNoAdress
     */
    public String getFlgNoAdress() {
        return flgNoAdress__;
    }
    /**
     * <p>flgNoAdress をセットします。
     * @param flgNoAdress flgNoAdress
     */
    public void setFlgNoAdress(String flgNoAdress) {
        flgNoAdress__ = flgNoAdress;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @return エラー
     */
    public ActionErrors validateCheck() {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        page__
        = NullDefault.getStringZeroLength(page__, "1");
        if (!GSValidateUtil.isNumber(page__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "ページ");
            StrutsUtil.addMessage(errors, msg, "page");
        }
        results__
        = NullDefault.getStringZeroLength(results__, "50");
        if (!GSValidateUtil.isNumber(results__)
                || Integer.parseInt(results__) <= 0
                || Integer.parseInt(results__) > 100) {
            msg = new ActionMessage(
                    "error.input.addhani.text", "取得件数", 1, 100);
            StrutsUtil.addMessage(errors, msg, "results");
        }


        return errors;
    }
}
