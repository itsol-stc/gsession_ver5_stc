package jp.groupsession.v2.api.ntp.nippou.search;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] WEBAPI 日報検索フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouSearch",
plugin = "nippou", name = "日報検索",
url = "/api/ntp/nippouSearch.do", reqtype = "GET")
public class ApiNippouSearchForm extends AbstractApiForm {
    /** usrSid */
    private String usrSid__;
    /** grpSid */
    private String grpSid__;
    /** nanSid */
    private String nanSid__;
    /** abaSid */
    private String abaSid__;
    /** acoSid */
    private String acoSid__;
    /** mkbSid */
    private String mkbSid__;
    /** mkhSid */
    private String mkhSid__;
    /** mikomi */
    private String[] mikomi__;
    /** keyWord */
    private String keyWord__;
    /** kewRule */
    private String kewRule__;
    /** keyFlgTitle */
    private String keyFlgTitle__;
    /** keyFlgSyokan */
    private String keyFlgSyokan__;
    /** titleClr */
    private String[] titleClr__;
    /** fromDate */
    private String fromDate__;
    /** toDate */
    private String toDate__;
    /** sortKey1 */
    private String sortKey1__;
    /** order1 */
    private String order1__;
    /** sortKey2 */
    private String sortKey2__;
    /** order2 */
    private String order2__;
    /** 取得件数 */
    private String maxCnt__ = null;
    /** 取得位置 */
    private String page__ = null;


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
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public String getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(String grpSid) {
        grpSid__ = grpSid;
    }
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
        nanSid__ = nanSid;
    }
    /**
     * <p>mkbSid を取得します。
     * @return mkbSid
     */
    public String getMkbSid() {
        return mkbSid__;
    }
    /**
     * <p>mkbSid をセットします。
     * @param mkbSid mkbSid
     */
    public void setMkbSid(String mkbSid) {
        mkbSid__ = mkbSid;
    }
    /**
     * <p>mkhSid を取得します。
     * @return mkhSid
     */
    public String getMkhSid() {
        return mkhSid__;
    }
    /**
     * <p>mkhSid をセットします。
     * @param mkhSid mkhSid
     */
    public void setMkhSid(String mkhSid) {
        mkhSid__ = mkhSid;
    }
    /**
     * <p>mikomi を取得します。
     * @return mikomi
     */
    public String[] getMikomi() {
        return mikomi__;
    }
    /**
     * <p>mikomi をセットします。
     * @param mikomi mikomi
     */
    public void setMikomi(String[] mikomi) {
        mikomi__ = mikomi;
    }
    /**
     * <p>keyWord を取得します。
     * @return keyWord
     */
    public String getKeyWord() {
        return keyWord__;
    }
    /**
     * <p>keyWord をセットします。
     * @param keyWord keyWord
     */
    public void setKeyWord(String keyWord) {
        keyWord__ = keyWord;
    }
    /**
     * <p>kewRule を取得します。
     * @return kewRule
     */
    public String getKewRule() {
        return kewRule__;
    }
    /**
     * <p>kewRule をセットします。
     * @param kewRule kewRule
     */
    public void setKewRule(String kewRule) {
        kewRule__ = kewRule;
    }
    /**
     * <p>keyFlgTitle を取得します。
     * @return keyFlgTitle
     */
    public String getKeyFlgTitle() {
        return keyFlgTitle__;
    }
    /**
     * <p>keyFlgTitle をセットします。
     * @param keyFlgTitle keyFlgTitle
     */
    public void setKeyFlgTitle(String keyFlgTitle) {
        keyFlgTitle__ = keyFlgTitle;
    }
    /**
     * <p>keyFlgSyokan を取得します。
     * @return keyFlgSyokan
     */
    public String getKeyFlgSyokan() {
        return keyFlgSyokan__;
    }
    /**
     * <p>keyFlgSyokan をセットします。
     * @param keyFlgSyokan keyFlgSyokan
     */
    public void setKeyFlgSyokan(String keyFlgSyokan) {
        keyFlgSyokan__ = keyFlgSyokan;
    }
    /**
     * <p>sortKey1 を取得します。
     * @return sortKey1
     */
    public String getSortKey1() {
        return sortKey1__;
    }
    /**
     * <p>sortKey1 をセットします。
     * @param sortKey1 sortKey1
     */
    public void setSortKey1(String sortKey1) {
        this.sortKey1__ = sortKey1;
    }
    /**
     * <p>order1 を取得します。
     * @return order1
     */
    public String getOrder1() {
        return order1__;
    }
    /**
     * <p>order1 をセットします。
     * @param order1 order1
     */
    public void setOrder1(String order1) {
        this.order1__ = order1;
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
        this.sortKey2__ = sortKey2;
    }
    /**
     * <p>order2 を取得します。
     * @return order2
     */
    public String getOrder2() {
        return order2__;
    }
    /**
     * <p>order2 をセットします。
     * @param order2 order2
     */
    public void setOrder2(String order2) {
        this.order2__ = order2;
    }
    /**
     * <p>abaSid を取得します。
     * @return abaSid
     */
    public String getAbaSid() {
        return abaSid__;
    }
    /**
     * <p>abaSid をセットします。
     * @param abaSid abaSid
     */
    public void setAbaSid(String abaSid) {
        abaSid__ = abaSid;
    }
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
        acoSid__ = acoSid;
    }
    /**
     * <p>fromDate を取得します。
     * @return fromDate
     */
    public String getFromDate() {
        return fromDate__;
    }
    /**
     * <p>fromDate をセットします。
     * @param fromDate fromDate
     */
    public void setFromDate(String fromDate) {
        this.fromDate__ = fromDate;
    }
    /**
     * <p>toDate を取得します。
     * @return toDate
     */
    public String getToDate() {
        return toDate__;
    }
    /**
     * <p>toDate をセットします。
     * @param toDate toDate
     */
    public void setToDate(String toDate) {
        this.toDate__ = toDate;
    }
    /**
     * <p>titleClr を取得します。
     * @return titleClr
     */
    public String[] getTitleClr() {
        return titleClr__;
    }
    /**
     * <p>titleClr をセットします。
     * @param titleClr titleClr
     */
    public void setTitleClr(String[] titleClr) {
        this.titleClr__ = titleClr;
    }
    /**
     * <p>maxCnt を取得します。
     * @return maxCnt
     */
    public String getMaxCnt() {
        return maxCnt__;
    }
    /**
     * <p>maxCnt をセットします。
     * @param maxCnt maxCnt
     */
    public void setMaxCnt(String maxCnt) {
        maxCnt__ = maxCnt;
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
        GsMessage gsMsg = new GsMessage();

        usrSid__ = NullDefault.getString(usrSid__, "-1");
        if (!GSValidateUtil.isNumberHaifun(usrSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_USER_SID);
            StrutsUtil.addMessage(errors, msg, "usrSid");
            return errors;

        }


        /** NAN_SID mapping */
        nanSid__ = NullDefault.getString(nanSid__, "-1");
        if (!GSValidateUtil.isNumberHaifun(nanSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_ANKEN_SID);
            StrutsUtil.addMessage(errors, msg, "nanSid");
            return errors;
        }
        /** ACO_SID mapping */
        acoSid__ = NullDefault.getString(acoSid__, "-1");
        if (!GSValidateUtil.isNumberHaifun(acoSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_COMPANY_SID);
            StrutsUtil.addMessage(errors, msg, "acoSid");
            return errors;
        }
        /** ABA_SID mapping */
        abaSid__ = NullDefault.getString(abaSid__, "-1");
        if (!GSValidateUtil.isNumberHaifun(abaSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_COMPANY_SID);
            StrutsUtil.addMessage(errors, msg, "abaSid");
            return errors;
        }
        /** MKB_SID mapping */
        mkbSid__ = NullDefault.getString(mkbSid__, "-1");
        if (!GSValidateUtil.isNumberHaifun(mkbSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_COMPANY_SID);
            StrutsUtil.addMessage(errors, msg, "mkbSid");
            return errors;
        }
        /** MKH_SID mapping */
        mkhSid__ = NullDefault.getString(mkhSid__, "-1");
        if (!GSValidateUtil.isNumberHaifun(mkhSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_COMPANY_SID);
            StrutsUtil.addMessage(errors, msg, "mkhSid");
            return errors;
        }
        if (mikomi__ == null) {
            mikomi__ = new String[]{"0", "1", "2", "3", "4"};
        }
        for (String nipMikomi : mikomi__) {
            nipMikomi = NullDefault.getString(nipMikomi, "0");
            if (!GSValidateUtil.isNumber(nipMikomi)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_MIKOMI);
                StrutsUtil.addMessage(errors, msg, "nipMikomi");
                return errors;
            }
        }
        //キーワード
        GSValidateNippou.validateCmnFieldText(errors,
                gsMsg.getMessage(req, "cmn.keyword"),
                keyWord__,
               "keyWord",
                GSConstNippou.MAX_LENGTH_TITLE,
                false);


        kewRule__ = NullDefault.getString(kewRule__, "0");

        if (!GSValidateUtil.isNumber(kewRule__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "検索ルール");
            StrutsUtil.addMessage(errors, msg, "kewRule");
            return errors;
        }

        //ＫＥＹワードチェック
        keyFlgTitle__ = NullDefault.getString(keyFlgTitle__, "1");

        if (!GSValidateUtil.isNumber(keyFlgTitle__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "タイトル検索フラグ");
            StrutsUtil.addMessage(errors, msg, "keyFlgTitle");
            return errors;
        }

        keyFlgSyokan__ = NullDefault.getString(keyFlgSyokan__, "1");

        if (!GSValidateUtil.isNumber(keyFlgSyokan__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "内容検索フラグ");
            StrutsUtil.addMessage(errors, msg, "keyFlgTitle");
            return errors;
        }
        //キーワード未入力時はチェックなし
        if (NullDefault.getString(keyWord__, "").length() > 0) {
            if (keyFlgSyokan__.equals("0") && keyFlgTitle__.equals("0")) {
                //検索対象
                String textSort = gsMsg.getMessage(req, "cmn.search2");
                //未選択の場合エラー
                msg = new ActionMessage(
                        "error.select.required.text", textSort);
                StrutsUtil.addMessage(errors, msg, "target");
                return errors;
            }
        }

        //タイトルカラー
        if (titleClr__ == null || titleClr__.length <= 0) {
            titleClr__ = new String[]{"1", "2", "3", "4", "5"};
        }
        for (String titleClrStr : titleClr__) {
            if (!GSValidateUtil.isNumber(titleClrStr)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", "検索色");
                StrutsUtil.addMessage(errors, msg, "titleClr");
                return errors;
            }
        }
        String textTitileColor = gsMsg.getMessage(req, "schedule.128");
        if (titleClr__ == null || titleClr__.length <= 0) {
            msg = new ActionMessage("error.select.required.text", textTitileColor);
            errors.add("error.select.required.text", msg);
        }

        //日付論理チェック
        fromDate__ = NullDefault.getString(fromDate__, "1970/01/01");
        GSValidateCommon.validateDateFieldText(errors, fromDate__,
                "dateFrom", GSConstNippou.TEXT_ANKEN_DATE_FROM, false);
        //日付論理チェック
        toDate__ = NullDefault.getString(toDate__, "2036/02/05");
        GSValidateCommon.validateDateFieldText(errors, toDate__,
                "dateTo", GSConstNippou.TEXT_ANKEN_DATE_TO, false);

        UDate fDate = new UDate();
        fDate.setZeroHhMmSs();
        fDate.setDate(Integer.parseInt(fromDate__.substring(0, 4)),
                Integer.parseInt(fromDate__.substring(5, 7)),
                Integer.parseInt(fromDate__.substring(8, 10)));

        UDate tDate = new UDate();
        tDate.setZeroHhMmSs();
        tDate.setDate(Integer.parseInt(toDate__.substring(0, 4)),
                Integer.parseInt(toDate__.substring(5, 7)),
                Integer.parseInt(toDate__.substring(8, 10)));
        //個別チェックOKの場合

        //from～to大小チェック
        if (fDate.compare(fDate, tDate) != UDate.LARGE) {
            //開始 < 終了
            String textStartLessEnd = gsMsg.getMessage(req, "cmn.start.lessthan.end");
            //開始・終了
            String textStartEnd = gsMsg.getMessage(req, "cmn.start.end");
            msg = new ActionMessage("error.input.comp.text",
                    textStartEnd, textStartLessEnd);
            errors.add("" + "error.input.comp.text", msg);
        }

        sortKey1__ = NullDefault.getString(sortKey1__,
                String.valueOf(GSConstNippou.SORT_KEY_FRDATE));
        if (!GSValidateUtil.isNumber(sortKey1__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "第1ソートキー");
            StrutsUtil.addMessage(errors, msg, "sortKey1");
            return errors;
        }
        order1__ = NullDefault.getString(order1__, String.valueOf(GSConstNippou.ORDER_KEY_ASC));
        if (!GSValidateUtil.isNumber(order1__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "第1ソートキー");
            StrutsUtil.addMessage(errors, msg, "order1");
            return errors;
        }

        sortKey2__ = NullDefault.getString(sortKey2__, String.valueOf(GSConstNippou.SORT_KEY_NAME));
        if (!GSValidateUtil.isNumber(sortKey2__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "第2ソートキー");
            StrutsUtil.addMessage(errors, msg, "sortKey2");
            return errors;
        }
        order2__ = NullDefault.getString(order2__, String.valueOf(GSConstNippou.ORDER_KEY_ASC));
        if (!GSValidateUtil.isNumber(order2__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "第2ソート方向");
            StrutsUtil.addMessage(errors, msg, "order2");
            return errors;
        }

        page__
        = NullDefault.getStringZeroLength(page__, "1");
        if (!GSValidateUtil.isNumber(page__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "ページ");
            StrutsUtil.addMessage(errors, msg, "page");
            return errors;
        }
        maxCnt__
        = NullDefault.getStringZeroLength(maxCnt__, "10");
        if (!GSValidateUtil.isNumber(maxCnt__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "取得件数");
            StrutsUtil.addMessage(errors, msg, "maxCnt");
            return errors;
        }
        return errors;
    }

}
