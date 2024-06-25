package jp.groupsession.v2.api.user.search;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.usr.GSConstUser;
/**
 *
 * <br>[機  能] API ユーザ情報検索
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "user-search",
plugin = "user", name = "ユーザの個人情報を検索",
url = "/api/user/search.do", reqtype = "GET")
public class ApiUserSearchForm extends AbstractApiForm {
    /** 選択グループSID */
    private String gsid__ = null;
    /** ユーザが選択した検索用カナ */
    private String searchKana__ = null;
    /** ユーザID(Save) */
    private String userId__ = null;
    /** 社員/職員番号(Save) */
    private String shainNo__ = null;
    /** ユーザ名 姓(Save) */
    private String sei__ = null;
    /** ユーザ名 名(Save) */
    private String mei__ = null;
    /** ユーザ名 姓カナ(Save) */
    private String seiKn__ = null;
    /** ユーザ名 名カナ(Save) */
    private String meiKn__ = null;
    /** 年齢From(Save) */
    private String ageFrom__ = null;
    /** 年齢To(Save) */
    private String ageTo__ = null;
    /** 役職(Save) */
    private String yakushoku__ = null;
    /** メール(Save) */
    private String mail__ = null;
    /** 都道府県コード(Save) */
    private String tdfkCd__ = null;


    /** ページ */
    private String page__ = "1";
    /** 1ページの最大表示件数 */
    private String results__ = "50";

    /** 第一ソートキー */
    private String sortKey__ = String.valueOf(GSConstUser.USER_SORT_NAME);
    /** 第一ソートオーダー */
    private String sortOrder__ = String.valueOf(GSConst.ORDER_KEY_ASC);
    /** 第二ソートキー */
    private String sortKey2__ = String.valueOf(GSConstUser.USER_SORT_SNO);
    /** 第二ソートオーダー */
    private String sortOrder2__ = String.valueOf(GSConst.ORDER_KEY_ASC);

    /** ラベルSID */
    private String[] labelSid__ = null;

    /**
     * <p>selectgsid を取得します。
     * @return selectgsid
     */
    public String getGsid() {
        return gsid__;
    }

    /**
     * <p>selectgsid をセットします。
     * @param selectgsid selectgsid
     */
    public void setGsid(String selectgsid) {
        gsid__ = selectgsid;
    }

    /**
     * <p>searchKana を取得します。
     * @return searchKana
     */
    public String getSearchKana() {
        return searchKana__;
    }

    /**
     * <p>searchKana をセットします。
     * @param searchKana searchKana
     */
    public void setSearchKana(String searchKana) {
        searchKana__ = searchKana;
    }

    /**
     * <p>userId を取得します。
     * @return userId
     */
    public String getUserId() {
        return userId__;
    }

    /**
     * <p>userId をセットします。
     * @param userId userId
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }

    /**
     * <p>shainno を取得します。
     * @return shainno
     */
    public String getShainNo() {
        return shainNo__;
    }

    /**
     * <p>shainno をセットします。
     * @param shainno shainno
     */
    public void setShainNo(String shainno) {
        shainNo__ = shainno;
    }

    /**
     * <p>sei を取得します。
     * @return sei
     */
    public String getSei() {
        return sei__;
    }

    /**
     * <p>sei をセットします。
     * @param sei sei
     */
    public void setSei(String sei) {
        sei__ = sei;
    }

    /**
     * <p>mei を取得します。
     * @return mei
     */
    public String getMei() {
        return mei__;
    }

    /**
     * <p>mei をセットします。
     * @param mei mei
     */
    public void setMei(String mei) {
        mei__ = mei;
    }

    /**
     * <p>seikn を取得します。
     * @return seikn
     */
    public String getSeiKn() {
        return seiKn__;
    }

    /**
     * <p>seikn をセットします。
     * @param seikn seikn
     */
    public void setSeiKn(String seikn) {
        seiKn__ = seikn;
    }

    /**
     * <p>meikn を取得します。
     * @return meikn
     */
    public String getMeiKn() {
        return meiKn__;
    }

    /**
     * <p>meikn をセットします。
     * @param meikn meikn
     */
    public void setMeiKn(String meikn) {
        meiKn__ = meikn;
    }

    /**
     * <p>agefrom を取得します。
     * @return agefrom
     */
    public String getAgeFrom() {
        return ageFrom__;
    }

    /**
     * <p>agefrom をセットします。
     * @param agefrom agefrom
     */
    public void setAgeFrom(String agefrom) {
        ageFrom__ = agefrom;
    }

    /**
     * <p>ageto を取得します。
     * @return ageto
     */
    public String getAgeTo() {
        return ageTo__;
    }

    /**
     * <p>ageto をセットします。
     * @param ageto ageto
     */
    public void setAgeTo(String ageto) {
        ageTo__ = ageto;
    }

    /**
     * <p>yakushoku を取得します。
     * @return yakushoku
     */
    public String getYakushoku() {
        return yakushoku__;
    }

    /**
     * <p>yakushoku をセットします。
     * @param yakushoku yakushoku
     */
    public void setYakushoku(String yakushoku) {
        yakushoku__ = yakushoku;
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
     * <p>tdfkCd を取得します。
     * @return tdfkCd
     */
    public String getTdfkCd() {
        return tdfkCd__;
    }

    /**
     * <p>tdfkCd をセットします。
     * @param tdfkCd tdfkCd
     */
    public void setTdfkCd(String tdfkCd) {
        tdfkCd__ = tdfkCd;
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
     * <p>sortOrder を取得します。
     * @return sortOrder
     */
    public String getSortOrder() {
        return sortOrder__;
    }

    /**
     * <p>sortOrder をセットします。
     * @param sortOrder sortOrder
     */
    public void setSortOrder(String sortOrder) {
        sortOrder__ = sortOrder;
    }

    /**
     * <p>sortKey2 を取得します。
     * @return sortKey2
     */
    public String getSortKey2() {
        return sortKey2__;
    }

    /**
     * <p>sortKey2 をセットします。
     * @param sortKey2 sortKey2
     */
    public void setSortKey2(String sortKey2) {
        sortKey2__ = sortKey2;
    }

    /**
     * <p>sortOrder2 を取得します。
     * @return sortOrder2
     */
    public String getSortOrder2() {
        return sortOrder2__;
    }

    /**
     * <p>sortOrder2 をセットします。
     * @param sortOrder2 sortOrder2
     */
    public void setSortOrder2(String sortOrder2) {
        sortOrder2__ = sortOrder2;
    }

    /**
     * <p>labelSid を取得します。
     * @return labelSid
     */
    public String[] getLabelSid() {
        return labelSid__;
    }

    /**
     * <p>labelSid をセットします。
     * @param labelSid labelSid
     */
    public void setLabelSid(String[] labelSid) {
        labelSid__ = labelSid;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
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
