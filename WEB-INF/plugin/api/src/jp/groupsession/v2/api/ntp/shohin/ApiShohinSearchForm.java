package jp.groupsession.v2.api.ntp.shohin;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
/**
 * <br>[機  能] WEBAPI 日報商品一覧検索フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-shohinSearch",
plugin = "nippou", name = "商品一覧取得",
url = "/api/ntp/shohinSearch.do", reqtype = "GET")
public class ApiShohinSearchForm extends AbstractApiForm {
    /** 商品コード */
    private String nhnCode__;
    /** 商品名 */
    private String nhnName__;
    /** 販売金額 */
    private String nhnPriceSale__;
    /** 販売金額範囲選択 */
    private String priceHiOrRow__;
    /** 原価金額 */
    private String nhnPriceCost__;
    /** 原価金額範囲選択 */
    private String costHiOrRow__;
    /** 第一ソートキー */
    private String sortKey1__;
    /** 第一ソート順 */
    private String orderKey1__;
    /** 第二ソートキー */
    private String sortKey2__;
    /** 第二ソート順 */
    private String orderKey2__;
    /** 取得ページ位置 */
    private String page__;
    /** 取得数 */
    private String maxCnt__;
    /**
     * <p>nhnCode を取得します。
     * @return nhnCode
     */
    public String getNhnCode() {
        return nhnCode__;
    }
    /**
     * <p>nhnCode をセットします。
     * @param nhnCode nhnCode
     */
    public void setNhnCode(String nhnCode) {
        nhnCode__ = nhnCode;
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
        nhnName__ = nhnName;
    }
    /**
     * <p>nhnPriceSale を取得します。
     * @return nhnPriceSale
     */
    public String getNhnPriceSale() {
        return nhnPriceSale__;
    }
    /**
     * <p>nhnPriceSale をセットします。
     * @param nhnPriceSale nhnPriceSale
     */
    public void setNhnPriceSale(String nhnPriceSale) {
        nhnPriceSale__ = nhnPriceSale;
    }
    /**
     * <p>priceHiOrRow を取得します。
     * @return priceHiOrRow
     */
    public String getPriceHiOrRow() {
        return priceHiOrRow__;
    }
    /**
     * <p>priceHiOrRow をセットします。
     * @param priceHiOrRow priceHiOrRow
     */
    public void setPriceHiOrRow(String priceHiOrRow) {
        priceHiOrRow__ = priceHiOrRow;
    }
    /**
     * <p>nhnPriceCost を取得します。
     * @return nhnPriceCost
     */
    public String getNhnPriceCost() {
        return nhnPriceCost__;
    }
    /**
     * <p>nhnPriceCost をセットします。
     * @param nhnPriceCost nhnPriceCost
     */
    public void setNhnPriceCost(String nhnPriceCost) {
        nhnPriceCost__ = nhnPriceCost;
    }
    /**
     * <p>costHiOrRow を取得します。
     * @return costHiOrRow
     */
    public String getCostHiOrRow() {
        return costHiOrRow__;
    }
    /**
     * <p>costHiOrRow をセットします。
     * @param costHiOrRow costHiOrRow
     */
    public void setCostHiOrRow(String costHiOrRow) {
        costHiOrRow__ = costHiOrRow;
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
        sortKey1__ = sortKey1;
    }
    /**
     * <p>orderKey1 を取得します。
     * @return orderKey1
     */
    public String getOrderKey1() {
        return orderKey1__;
    }
    /**
     * <p>orderKey1 をセットします。
     * @param orderKey1 orderKey1
     */
    public void setOrderKey1(String orderKey1) {
        orderKey1__ = orderKey1;
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
     * <p>orderKey2 を取得します。
     * @return orderKey2
     */
    public String getOrderKey2() {
        return orderKey2__;
    }
    /**
     * <p>orderKey2 をセットします。
     * @param orderKey2 orderKey2
     */
    public void setOrderKey2(String orderKey2) {
        orderKey2__ = orderKey2;
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

        //商品コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_SHOHIN_CODE,
                nhnCode__,
               "nhnCode",
                GSConstNippou.MAX_LENGTH_SHOHIN_CODE,
                false);

        //商品名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_SHOHIN_NAME,
                nhnName__,
               "nhnName",
                GSConstNippou.MAX_LENGTH_SHOHIN_NAME,
                false);

        //販売価格入力チェック
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                GSConstNippou.TEXT_PRICE_SALE,
                nhnPriceSale__,
               "nhnPriceSale",
                GSConstNippou.MAX_LENGTH_PRICE_SALE,
                false);
        priceHiOrRow__ =  NullDefault.getStringZeroLength(priceHiOrRow__, "0");
        if (!GSValidateUtil.isNumber(priceHiOrRow__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "販売価格範囲");
            StrutsUtil.addMessage(errors, msg, "priceHiOrRow");
            return errors;
        }

        //原価価格入力チェック
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                GSConstNippou.TEXT_PRICE_COST,
                nhnPriceCost__,
               "nhnPriceCost",
                GSConstNippou.MAX_LENGTH_PRICE_COST,
                false);
        costHiOrRow__ =  NullDefault.getStringZeroLength(costHiOrRow__, "0");
        if (!GSValidateUtil.isNumber(costHiOrRow__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "原価金額範囲");
            StrutsUtil.addMessage(errors, msg, "costHiOrRow");
            return errors;
        }

        sortKey1__ =  NullDefault.getStringZeroLength(sortKey1__, "0");
        if (!GSValidateUtil.isNumber(sortKey1__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "第１ソートキー");
            StrutsUtil.addMessage(errors, msg, "sortKey1");
            return errors;
        }
        orderKey1__ =  NullDefault.getStringZeroLength(orderKey1__, "0");
        if (!GSValidateUtil.isNumber(orderKey1__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "第１ソート順");
            StrutsUtil.addMessage(errors, msg, "orderKey1");
            return errors;
        }

        sortKey2__ =  NullDefault.getStringZeroLength(sortKey2__, "0");
        if (!GSValidateUtil.isNumber(sortKey2__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "第2ソートキー");
            StrutsUtil.addMessage(errors, msg, "sortKey2");
            return errors;
        }
        orderKey2__ =  NullDefault.getStringZeroLength(orderKey2__, "0");
        if (!GSValidateUtil.isNumber(orderKey2__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "第2ソート順");
            StrutsUtil.addMessage(errors, msg, "orderKey2");
            return errors;
        }
        maxCnt__ =  NullDefault.getStringZeroLength(maxCnt__, "10");
        if (!GSValidateUtil.isNumber(maxCnt__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "取得件数");
            StrutsUtil.addMessage(errors, msg, "maxCnt");
            return errors;
        }
        page__ =  NullDefault.getStringZeroLength(page__, "1");
        if (!GSValidateUtil.isNumber(page__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "ページ番号");
            StrutsUtil.addMessage(errors, msg, "page");
            return errors;
        }

        return errors;
    }
}
