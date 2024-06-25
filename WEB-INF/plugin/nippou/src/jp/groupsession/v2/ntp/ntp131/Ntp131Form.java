package jp.groupsession.v2.ntp.ntp131;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.dao.NtpShohinDao;
import jp.groupsession.v2.ntp.ntp130.Ntp130Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 日報 商品登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp131Form extends Ntp130Form {

    /** 商品コード */
    private String ntp131ShohinCode__ = null;
    /** 商品名 */
    private String ntp131ShohinName__ = null;
    /** 販売価格 */
    private String ntp131PriceSale__ = null;
    /** 原価価格 */
    private String ntp131PriceCost__ = null;
    /** 補足事項 */
    private String ntp131Hosoku__ = null;
    /** 複写して登録フラグ 0:通常 1:複写して登録 */
    private int ntp131CopyFlg__ = 0;

    /**
     * @return ntp131ShohinCode
     */
    public String getNtp131ShohinCode() {
        return ntp131ShohinCode__;
    }
    /**
     * @param ntp131ShohinCode 設定する ntp131ShohinCode
     */
    public void setNtp131ShohinCode(String ntp131ShohinCode) {
        ntp131ShohinCode__ = ntp131ShohinCode;
    }
    /**
     * @return ntp131ShohinName
     */
    public String getNtp131ShohinName() {
        return ntp131ShohinName__;
    }
    /**
     * @param ntp131ShohinName 設定する ntp131ShohinName
     */
    public void setNtp131ShohinName(String ntp131ShohinName) {
        ntp131ShohinName__ = ntp131ShohinName;
    }
    /**
     * @return ntp131PriceCost
     */
    public String getNtp131PriceCost() {
        return ntp131PriceCost__;
    }
    /**
     * @param ntp131PriceCost 設定する ntp131PriceCost
     */
    public void setNtp131PriceCost(String ntp131PriceCost) {
        ntp131PriceCost__ = ntp131PriceCost;
    }
    /**
     * @return ntp131PriceSale
     */
    public String getNtp131PriceSale() {
        return ntp131PriceSale__;
    }
    /**
     * @param ntp131PriceSale 設定する ntp131PriceSale
     */
    public void setNtp131PriceSale(String ntp131PriceSale) {
        ntp131PriceSale__ = ntp131PriceSale;
    }
    /**
     * @return ntp131Hosoku
     */
    public String getNtp131Hosoku() {
        return ntp131Hosoku__;
    }
    /**
     * @param ntp131Hosoku 設定する ntp131Hosoku
     */
    public void setNtp131Hosoku(String ntp131Hosoku) {
        ntp131Hosoku__ = ntp131Hosoku;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        //商品コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_SHOHIN_CODE,
                ntp131ShohinCode__,
               "ntp131ShohinCode",
                GSConstNippou.MAX_LENGTH_SHOHIN_CODE,
                true);
        if (errors.isEmpty()) {
            //商品コードの重複チェック
            int nhnSid = getNtp130NhnSid();
            if (getNtp130ProcMode().equals(GSConstNippou.CMD_ADD)) {
                nhnSid = -1;
            }
            NtpShohinDao dao = new NtpShohinDao(con);
            if (dao.existShohin(nhnSid, ntp131ShohinCode__)) {
                String eprefix = "ntp131ShohinCode";
                String fieldfix = GSConstNippou.TEXT_SHOHIN_CODE + ".";
                msg = new ActionMessage("error.select.dup.list", GSConstNippou.TEXT_SHOHIN_CODE);
                StrutsUtil.addMessage(errors, msg, eprefix + fieldfix + "ntp131ShohinCode");
            }
        }
        //商品名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_SHOHIN_NAME,
                ntp131ShohinName__,
               "ntp131ShohinName",
                GSConstNippou.MAX_LENGTH_SHOHIN_NAME,
                true);

        //販売価格入力チェック
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                GSConstNippou.TEXT_PRICE_SALE,
                ntp131PriceSale__.replaceAll(",", ""),
               "ntp131PriceSale",
                GSConstNippou.MAX_LENGTH_PRICE_SALE,
                false);

        //原価価格入力チェック
        GSValidateNippou.validateCmnFieldTextNumber(errors,
                GSConstNippou.TEXT_PRICE_COST,
                ntp131PriceCost__.replaceAll(",", ""),
               "ntp131PriceCost",
                GSConstNippou.MAX_LENGTH_PRICE_COST,
                false);

        //補足事項入力チェック
        GSValidateNippou.validateFieldTextArea(errors,
                GSConstNippou.TEXT_HOSOKU,
                ntp131Hosoku__,
               "ntp131Hosoku",
                GSConstNippou.MAX_LENGTH_HOSOKU,
                false);
        return errors;
    }
    /**
     * <p>ntp131CopyFlg を取得します。
     * @return ntp131CopyFlg
     */
    public int getNtp131CopyFlg() {
        return ntp131CopyFlg__;
    }
    /**
     * <p>ntp131CopyFlg をセットします。
     * @param ntp131CopyFlg ntp131CopyFlg
     */
    public void setNtp131CopyFlg(int ntp131CopyFlg) {
        ntp131CopyFlg__ = ntp131CopyFlg;
    }
}
