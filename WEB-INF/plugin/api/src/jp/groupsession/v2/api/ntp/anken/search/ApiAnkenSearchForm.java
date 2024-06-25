package jp.groupsession.v2.api.ntp.anken.search;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] 日報 案件検索するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-ankenSearch",
plugin = "nippou", name = "案件一覧取得",
url = "/api/ntp/ankenSearch.do", reqtype = "GET")
public class ApiAnkenSearchForm extends AbstractApiForm {
    /** 案件コード */
    private String nanCode__ = null;
    /** 案件名 */
    private String nanName__ = null;
    /** 会社コード */
    private String acoCode__ = null;
    /** 会社名 */
    private String acoName__ = null;
    /** 会社名カナ */
    private String acoNameIni__ = null;
    /** 拠点名 */
    private String abaName__ = null;
    /** 商品名 */
    private String nhnName__ = null;
    /** 登録日開始 */
    private String dateFrom__ = "0000/01/01";
    /** 登録日終了 */
    private String dateTo__ = "9999/12/31";
    /** プロセスSID */
    private String ngpSid__ = "-1";
    /** 見込み度 */
    private String[] nanMikomi__ = null;
    /** 見積金額 */
    private String mitumoriKingaku__ = null;
    /** 見積範囲選択 */
    private String mitumorHiOrRow__ = "0";
    /** 受注金額 */
    private String jutyuuKingaku__ = null;
    /** 受注範囲選択 */
    private String jutyuuHiOrRow__ = "0";
    /** 商談結果 */
    private String[] syodan__ = null;
    /** コンタクト */
    private String ncnSid__ = "-1";

    /** ソートキー */
    private String sortKey__ = null;

    /** オーダー */
    private String order__ = null;

    /** 取得件数 */
    private String maxCnt__ = "10";
    /** 取得位置 */
    private String page__ = "1";

    /**
     * <p>nanCode を取得します。
     * @return nanCode
     */
    public String getNanCode() {
        return nanCode__;
    }
    /**
     * <p>nanCode をセットします。
     * @param nanCode nanCode
     */
    public void setNanCode(String nanCode) {
        this.nanCode__ = nanCode;
    }
    /**
     * <p>nanName を取得します。
     * @return nanName
     */
    public String getNanName() {
        return nanName__;
    }
    /**
     * <p>nanName をセットします。
     * @param nanName nanName
     */
    public void setNanName(String nanName) {
        this.nanName__ = nanName;
    }
    /**
     * <p>acoName を取得します。
     * @return acoName
     */
    public String getAcoName() {
        return acoName__;
    }
    /**
     * <p>acoName をセットします。
     * @param acoName acoName
     */
    public void setAcoName(String acoName) {
        this.acoName__ = acoName;
    }
    /**
     * <p>acoNameIni を取得します。
     * @return acoNameIni
     */
    public String getAcoNameIni() {
        return acoNameIni__;
    }
    /**
     * <p>acoNameIni をセットします。
     * @param acoNameIni acoNameIni
     */
    public void setAcoNameIni(String acoNameIni) {
        this.acoNameIni__ = acoNameIni;
    }
    /**
     * <p>abaName を取得します。
     * @return abaName
     */
    public String getAbaName() {
        return abaName__;
    }
    /**
     * <p>abaName をセットします。
     * @param abaName abaName
     */
    public void setAbaName(String abaName) {
        this.abaName__ = abaName;
    }
    /**
     * <p>nhnName を取得します。
     * @return nhnName
     */
    public String getNhnName() {
        return nhnName__;
    }
    /**
     * <p>nhnName をセットします。
     * @param nhnName nhnName
     */
    public void setNhnName(String nhnName) {
        this.nhnName__ = nhnName;
    }
    /**
     * <p>dateFrom を取得します。
     * @return dateFrom
     */
    public String getDateFrom() {
        return dateFrom__;
    }
    /**
     * <p>dateFrom をセットします。
     * @param dateFrom dateFrom
     */
    public void setDateFrom(String dateFrom) {
        this.dateFrom__ = dateFrom;
    }
    /**
     * <p>dateTo を取得します。
     * @return dateTo
     */
    public String getDateTo() {
        return dateTo__;
    }
    /**
     * <p>dateTo をセットします。
     * @param dateTo dateTo
     */
    public void setDateTo(String dateTo) {
        this.dateTo__ = dateTo;
    }

    /**
     * <p>ngpSid を取得します。
     * @return ngpSid
     */
    public String getNgpSid() {
        return ngpSid__;
    }
    /**
     * <p>ngpSid をセットします。
     * @param ngpSid ngpSid
     */
    public void setNgpSid(String ngpSid) {
        this.ngpSid__ = ngpSid;
    }
    /**
     * <p>nanMikomi を取得します。
     * @return nanMikomi
     */
    public String[] getNanMikomi() {
        return nanMikomi__;
    }
    /**
     * <p>nanMikomi をセットします。
     * @param nanMikomi nanMikomi
     */
    public void setNanMikomi(String[] nanMikomi) {
        this.nanMikomi__ = nanMikomi;
    }
    /**
     * <p>mitumoriKingaku を取得します。
     * @return mitumoriKingaku
     */
    public String getMitumoriKingaku() {
        return mitumoriKingaku__;
    }
    /**
     * <p>mitumoriKingaku をセットします。
     * @param mitumoriKingaku mitumoriKingaku
     */
    public void setMitumoriKingaku(String mitumoriKingaku) {
        this.mitumoriKingaku__ = mitumoriKingaku;
    }
    /**
     * <p>mitumorHiOrRow を取得します。
     * @return mitumorHiOrRow
     */
    public String getMitumorHiOrRow() {
        return mitumorHiOrRow__;
    }
    /**
     * <p>mitumorHiOrRow をセットします。
     * @param mitumorHiOrRow mitumorHiOrRow
     */
    public void setMitumorHiOrRow(String mitumorHiOrRow) {
        this.mitumorHiOrRow__ = mitumorHiOrRow;
    }
    /**
     * <p>jutyuuKingaku を取得します。
     * @return jutyuuKingaku
     */
    public String getJutyuuKingaku() {
        return jutyuuKingaku__;
    }
    /**
     * <p>jutyuuKingaku をセットします。
     * @param jutyuuKingaku jutyuuKingaku
     */
    public void setJutyuuKingaku(String jutyuuKingaku) {
        this.jutyuuKingaku__ = jutyuuKingaku;
    }
    /**
     * <p>jutyuuHiOrRow を取得します。
     * @return jutyuuHiOrRow
     */
    public String getJutyuuHiOrRow() {
        return jutyuuHiOrRow__;
    }
    /**
     * <p>jutyuuHiOrRow をセットします。
     * @param jutyuuHiOrRow jutyuuHiOrRow
     */
    public void setJutyuuHiOrRow(String jutyuuHiOrRow) {
        this.jutyuuHiOrRow__ = jutyuuHiOrRow;
    }
    /**
     * <p>acoCode を取得します。
     * @return acoCode
     */
    public String getAcoCode() {
        return acoCode__;
    }
    /**
     * <p>acoCode をセットします。
     * @param acoCode acoCode
     */
    public void setAcoCode(String acoCode) {
        this.acoCode__ = acoCode;
    }
    /**
     * <p>syodan を取得します。
     * @return syodan
     */
    public String[] getSyodan() {
        return syodan__;
    }
    /**
     * <p>syodan をセットします。
     * @param syodan syodan
     */
    public void setSyodan(String[] syodan) {
        syodan__ = syodan;
    }
    /**
     * <p>ncnSid を取得します。
     * @return ncnSid
     */
    public String getNcnSid() {
        return ncnSid__;
    }
    /**
     * <p>ncnSid をセットします。
     * @param ncnSid ncnSid
     */
    public void setNcnSid(String ncnSid) {
        ncnSid__ = ncnSid;
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
        this.sortKey__ = sortKey;
    }
    /**
     * <p>order を取得します。
     * @return order
     */
    public String getOrder() {
        return order__;
    }
    /**
     * <p>order をセットします。
     * @param order order
     */
    public void setOrder(String order) {
        this.order__ = order;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param gsMsg GsMessage
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, GsMessage gsMsg) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //案件コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_ANKEN_CODE,
                nanCode__,
               "nanCode",
                GSConstNippou.MAX_LENGTH_ANKEN_CODE,
                false);

        //案件名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_ANKEN_NAME,
                nanName__,
               "nanName",
                GSConstNippou.MAX_LENGTH_ANKEN_NAME,
                false);

        //見積金額入力チェック
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                GSConstNippou.TEXT_ANKEN_MITUMORI,
                mitumoriKingaku__,
               "mitumoriKingaku__",
                GSConstNippou.MAX_LENGTH_ANKEN_MITUMORI,
                false);

        //受注金額入力チェック
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                GSConstNippou.TEXT_ANKEN_JUCYU,
                jutyuuKingaku__,
               "jutyuuKingaku",
                GSConstNippou.MAX_LENGTH_ANKEN_JUCYU,
                false);

        //企業コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_CODE,
                acoCode__,
               "acoCode",
                GSConstNippou.MAX_LENGTH_COMPANY_CODE,
                false);

        //会社名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_NAME,
                acoName__,
               "acoName",
                GSConstNippou.MAX_LENGTH_COMPANY_NAME,
                false);

        //会社名カナ入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_NAME_KN,
                acoNameIni__,
               "acoNameIni",
                GSConstNippou.MAX_LENGTH_COMPANY_NAME_KN,
                false);

        //拠点名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_COMPANY_BASE_NAME,
                abaName__,
               "abaName",
                GSConstNippou.MAX_LENGTH_BASE_NAME,
                false);

        //商品名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_SHOHIN_NAME,
                nhnName__,
               "nhnName",
                GSConstNippou.MAX_LENGTH_SHOHIN_NAME,
                false);

        //日付論理チェック
        GSValidateCommon.validateDateFieldText(errors,
                dateFrom__,
                "dateFrom",
                GSConstNippou.TEXT_ANKEN_DATE_FROM,
                false);


        //日付論理チェック
        GSValidateCommon.validateDateFieldText(errors,
                dateTo__,
                "dateTo",
                GSConstNippou.TEXT_ANKEN_DATE_TO,
                false);
        //
        //開始年月日チェックフラグ(true=入力OK、false=NG)
        //終了年月日チェックフラグ(true=入力OK、false=NG)
        UDate fDate = new UDate();
        fDate.setZeroHhMmSs();
        fDate.setDate(Integer.parseInt(dateFrom__.substring(0, 4)),
                Integer.parseInt(dateFrom__.substring(5, 7)),
                Integer.parseInt(dateFrom__.substring(8, 10)));

        UDate tDate = new UDate();
        tDate.setZeroHhMmSs();
        tDate.setDate(Integer.parseInt(dateTo__.substring(0, 4)),
                Integer.parseInt(dateTo__.substring(5, 7)),
                Integer.parseInt(dateTo__.substring(8, 10)));
        //個別チェックOKの場合

        //from～to大小チェック
        if (fDate.compare(fDate, tDate) != UDate.LARGE) {
            //開始 < 終了
            String textStartLessEnd = gsMsg.getMessage("cmn.start.lessthan.end");
            //開始・終了
            String textStartEnd = gsMsg.getMessage("cmn.start.end");
            msg = new ActionMessage("error.input.comp.text",
                    textStartEnd, textStartLessEnd);
            errors.add("" + "error.input.comp.text", msg);
        }

        //プロセス入力チェック
        GSValidateApi.validateSid(errors, ngpSid__,
                "ngpSid", GSConstNippou.TEXT_PROCESS_SID,
                false, true);

        GSValidateApi.validateSid(errors, ncnSid__,
                "ngpSid", "コンタクトSID",
                false, true);

        if (!GSValidateUtil.isNumber(page__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "ページ");
            StrutsUtil.addMessage(errors, msg, "page");
        }
        if (!GSValidateUtil.isNumber(maxCnt__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "取得件数");
            StrutsUtil.addMessage(errors, msg, "maxCnt");
        }
        return errors;
    }
}
