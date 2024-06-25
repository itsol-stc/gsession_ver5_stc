package jp.groupsession.v2.api.adress.companybase.search;


import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
/**
 * <br>[機  能] WEB API アドレス帳 企業拠点検索フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "address-searchCompanyBase",
plugin = "address", name = "会社詳細検索",
url = "/api/address/searchCompanyBase.do", reqtype = "GET")
public class ApiSearchCompanyBaseForm extends AbstractApiForm {
    /** 会社名*/
    private String name__;
    /** 会社名カナ*/
    private String ini__;
    /** 拠点名*/
    private String base__;
    /** 業種SID*/
    private String atiSid__;
    /** 都道府県SID*/
    private String tdfSid__;
    /** 備考*/
    private String biko__;

    /** 企業コード*/
    private String code__;

    /** 取得開始位置 */
    String  page__ = null;
    /** 取得件数 */
    String  count__ = null;

    /**
     * <p>code を取得します。
     * @return code
     */
    public String getCode() {
        return code__;
    }

    /**
     * <p>code をセットします。
     * @param code code
     */
    public void setCode(String code) {
        code__ = code;
    }

    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        this.name__ = name;
    }

    /**
     * <p>ini を取得します。
     * @return ini
     */
    public String getIni() {
        return ini__;
    }

    /**
     * <p>ini をセットします。
     * @param ini ini
     */
    public void setIni(String ini) {
        ini__ = ini;
    }

    /**
     * <p>base を取得します。
     * @return base
     */
    public String getBase() {
        return base__;
    }

    /**
     * <p>base をセットします。
     * @param base base
     */
    public void setBase(String base) {
        base__ = base;
    }

    /**
     * <p>atiSid を取得します。
     * @return atiSid
     */
    public String getAtiSid() {
        return atiSid__;
    }

    /**
     * <p>atiSid をセットします。
     * @param atiSid atiSid
     */
    public void setAtiSid(String atiSid) {
        atiSid__ = atiSid;
    }

    /**
     * <p>tdfSid を取得します。
     * @return tdfSid
     */
    public String getTdfSid() {
        return tdfSid__;
    }

    /**
     * <p>tdfSid をセットします。
     * @param tdfSid tdfSid
     */
    public void setTdfSid(String tdfSid) {
        tdfSid__ = tdfSid;
    }

    /**
     * <p>biko を取得します。
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }

    /**
     * <p>biko をセットします。
     * @param biko biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }


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
     * @return error
     */
    public ActionErrors validateCheck()  {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        atiSid__ = NullDefault.getString(atiSid__, "0");
        if (!GSValidateUtil.isNumberHaifun(atiSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "atiSid");
            StrutsUtil.addMessage(errors, msg, "atiSid");
        }

        tdfSid__ = NullDefault.getString(tdfSid__, "0");
        if (!GSValidateUtil.isNumberHaifun(tdfSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "tdfSid");
            StrutsUtil.addMessage(errors, msg, "tdfSid");
        }

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
