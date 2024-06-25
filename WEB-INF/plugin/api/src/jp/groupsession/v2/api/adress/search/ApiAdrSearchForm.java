package jp.groupsession.v2.api.adress.search;


import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
*
* <br>[機  能] アドレス検索 WEBAPI
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
@ApiClass(id = "address-search",
plugin = "address", name = "アドレス情報検索",
url = "/api/address/search.do",
reqtype = "GET")
public class ApiAdrSearchForm extends AbstractApiForm {
    /** 並び順 */
    private String orderKey__ = null;

    /** 企業コード */
    private String coCode__ = null;
    /** 会社名 */
    private String coName__ = null;
    /** 会社名カナ */
    private String coNameKn__ = null;
    /** 支店・営業所名 */
    private String coBaseName__ = null;
    /** 業種 */
    private String atiSid__ = "-1";
    /** 都道府県 */
    private String tdfk__ = "-1";
    /** 備考 */
    private String biko__ = null;


    /** 氏名カナ 頭文字 */
    private String unameKnHead__ = null;

    /** 会社名 頭文字 */
    private String cnameKnHead__ = null;

    /** グループ */
    private String group__ = "-1";
    /** ユーザ */
    private String user__ = "-1";

    /** 氏名 姓 */
    private String unameSei__ = null;
    /** 氏名 名 */
    private String unameMei__ = null;
    /** 氏名カナ 姓 */
    private String unameSeiKn__ = null;
    /** 氏名カナ 名 */
    private String unameMeiKn__ = null;
    /** 役職 */
    private String position__ = "-1";
    /** E-MAIL */
    private String mail__ = null;

    /** ラベル */
    private String[] label__ = null;

    /** 会社SID */
    private String companySid__ = null;
    /** 会社拠点SID */
    private String companyBaseSid__ = null;

    /** ページ */
    private String page__ = "1";
    /** 1ページの最大表示件数 */
    private String results__ = "50";


    /** ソート項目 */
    private String sortKey__ = null;


    /**
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public String getOrderKey() {
        return orderKey__;
    }


    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(String orderKey) {
        orderKey__ = orderKey;
    }


    /**
     * <p>coCode を取得します。
     * @return coCode
     */
    public String getCoCode() {
        return coCode__;
    }


    /**
     * <p>coCode をセットします。
     * @param coCode coCode
     */
    public void setCoCode(String coCode) {
        coCode__ = coCode;
    }


    /**
     * <p>coName を取得します。
     * @return coName
     */
    public String getCoName() {
        return coName__;
    }


    /**
     * <p>coName をセットします。
     * @param coName coName
     */
    public void setCoName(String coName) {
        coName__ = coName;
    }


    /**
     * <p>coNameKn を取得します。
     * @return coNameKn
     */
    public String getCoNameKn() {
        return coNameKn__;
    }


    /**
     * <p>coNameKn をセットします。
     * @param coNameKn coNameKn
     */
    public void setCoNameKn(String coNameKn) {
        coNameKn__ = coNameKn;
    }


    /**
     * <p>coBaseName を取得します。
     * @return coBaseName
     */
    public String getCoBaseName() {
        return coBaseName__;
    }


    /**
     * <p>coBaseName をセットします。
     * @param coBaseName coBaseName
     */
    public void setCoBaseName(String coBaseName) {
        coBaseName__ = coBaseName;
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
     * <p>tdfk を取得します。
     * @return tdfk
     */
    public String getTdfk() {
        return tdfk__;
    }


    /**
     * <p>tdfk をセットします。
     * @param tdfk tdfk
     */
    public void setTdfk(String tdfk) {
        tdfk__ = tdfk;
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
     * <p>unameKnHead を取得します。
     * @return unameKnHead
     */
    public String getUnameKnHead() {
        return unameKnHead__;
    }


    /**
     * <p>unameKnHead をセットします。
     * @param unameKnHead unameKnHead
     */
    public void setUnameKnHead(String unameKnHead) {
        unameKnHead__ = unameKnHead;
    }


    /**
     * <p>cnameKnHead を取得します。
     * @return cnameKnHead
     */
    public String getCnameKnHead() {
        return cnameKnHead__;
    }


    /**
     * <p>cnameKnHead をセットします。
     * @param cnameKnHead cnameKnHead
     */
    public void setCnameKnHead(String cnameKnHead) {
        cnameKnHead__ = cnameKnHead;
    }


    /**
     * <p>group を取得します。
     * @return group
     */
    public String getGroup() {
        return group__;
    }


    /**
     * <p>group をセットします。
     * @param group group
     */
    public void setGroup(String group) {
        group__ = group;
    }


    /**
     * <p>user を取得します。
     * @return user
     */
    public String getUser() {
        return user__;
    }


    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(String user) {
        user__ = user;
    }


    /**
     * <p>unameSei を取得します。
     * @return unameSei
     */
    public String getUnameSei() {
        return unameSei__;
    }


    /**
     * <p>unameSei をセットします。
     * @param unameSei unameSei
     */
    public void setUnameSei(String unameSei) {
        unameSei__ = unameSei;
    }


    /**
     * <p>unameMei を取得します。
     * @return unameMei
     */
    public String getUnameMei() {
        return unameMei__;
    }


    /**
     * <p>unameMei をセットします。
     * @param unameMei unameMei
     */
    public void setUnameMei(String unameMei) {
        unameMei__ = unameMei;
    }


    /**
     * <p>unameSeiKn を取得します。
     * @return unameSeiKn
     */
    public String getUnameSeiKn() {
        return unameSeiKn__;
    }


    /**
     * <p>unameSeiKn をセットします。
     * @param unameSeiKn unameSeiKn
     */
    public void setUnameSeiKn(String unameSeiKn) {
        unameSeiKn__ = unameSeiKn;
    }


    /**
     * <p>unameMeiKn を取得します。
     * @return unameMeiKn
     */
    public String getUnameMeiKn() {
        return unameMeiKn__;
    }


    /**
     * <p>unameMeiKn をセットします。
     * @param unameMeiKn unameMeiKn
     */
    public void setUnameMeiKn(String unameMeiKn) {
        unameMeiKn__ = unameMeiKn;
    }


    /**
     * <p>position を取得します。
     * @return position
     */
    public String getPosition() {
        return position__;
    }


    /**
     * <p>position をセットします。
     * @param position position
     */
    public void setPosition(String position) {
        position__ = position;
    }


    /**
     * <p>mail を取得します。
     * @return mail
     */
    public String getMail() {
        return mail__;
    }


    /**
     * <p>mail をセットします。
     * @param mail mail
     */
    public void setMail(String mail) {
        mail__ = mail;
    }


    /**
     * <p>label を取得します。
     * @return label
     */
    public String[] getLabel() {
        return label__;
    }


    /**
     * <p>label をセットします。
     * @param label label
     */
    public void setLabel(String[] label) {
        label__ = label;
    }


    /**
     * <p>companySid を取得します。
     * @return companySid
     */
    public String getCompanySid() {
        return companySid__;
    }


    /**
     * <p>companySid をセットします。
     * @param companySid companySid
     */
    public void setCompanySid(String companySid) {
        companySid__ = companySid;
    }


    /**
     * <p>companyBaseSid を取得します。
     * @return companyBaseSid
     */
    public String getCompanyBaseSid() {
        return companyBaseSid__;
    }


    /**
     * <p>companyBaseSid をセットします。
     * @param companyBaseSid companyBaseSid
     */
    public void setCompanyBaseSid(String companyBaseSid) {
        companyBaseSid__ = companyBaseSid;
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
        results__ = results;
    }


    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public String getSortKey() {
        return sortKey__;
    }


    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(String sortKey) {
        sortKey__ = sortKey;
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
