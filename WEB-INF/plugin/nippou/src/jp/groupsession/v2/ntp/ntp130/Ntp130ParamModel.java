package jp.groupsession.v2.ntp.ntp130;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.model.NtpShohinCategoryModel;
import jp.groupsession.v2.ntp.ntp061.Ntp061ParamModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 日報 商品一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp130ParamModel extends Ntp061ParamModel {


    /** 画面モード 0:商品一覧 1:カテゴリ*/
    private int ntp130DspKbn__ = 0;
    /** カテゴリリスト一覧 */
    private List<LabelValueBean> ntp130CategoryList__ = null;
    /** 商品一覧 */
    private ArrayList<Ntp130ShohinModel> ntp130ShohinList__ = null;
    /** 商品内容SID */
    private int ntp130NhnSid__ = -1;
    /** 処理モード */
    private String ntp130ProcMode__ = GSConstNippou.CMD_ADD;
    /** ソートキーリスト一覧 */
    private List<LabelValueBean> ntp130SortList__ = null;

    /** 画面表示モード */
    private String ntp130DspMode__ = null;
    /** 遷移元 */
    private String ntp130ReturnPage__ = null;
    /** 初期化フラグ */
    private int ntp130InitFlg__ = 0;
    /** 商品コード */
    private String ntp130NhnCode__ = null;
    /** 商品名 */
    private String ntp130NhnName__ = null;
    /** 販売価格 */
    private String ntp130NhnPriceSale__ = null;
    /** 原価価格 */
    private String ntp130NhnPriceCost__ = null;
    /** 販売価格区分 */
    private int ntp130NhnPriceSaleKbn__ = Ntp130Biz.PRICE_MORE;
    /** 販売価格区分 */
    private int ntp130NhnPriceCostKbn__ = Ntp130Biz.PRICE_MORE;
    /** ソートキー１ */
    private int ntp130SortKey1__ = GSConstNippou.SORT_KEY_NHK_EDATE;
//    private int ntp130SortKey1__ = -1;
    /** オーダキー１ */
    private int ntp130OrderKey1__ = GSConstNippou.ORDER_KEY_DESC;
    /** ソートキー２ */
    private int ntp130SortKey2__ = -1;
    /** オーダキー２ */
    private int ntp130OrderKey2__ = GSConstNippou.ORDER_KEY_ASC;

    /** ページ */
    private int ntp130Page__ = 0;
    /** ページ上段 */
    private int ntp130PageTop__ = 0;
    /** ページ下段 */
    private int ntp130PageBottom__ = 0;
    /** ページコンボ */
    private List<LabelValueBean> ntp130PageCmbList__ = null;
    /** 商品チェック (現在ページ以外でチェックされている値) */
    private ArrayList<String> ntp130SelectedSid__ = null;
    /** 選択商品チェックボックスリスト */
    private String[] ntp130ChkShohinSidList__ = null;
    /** 選択商品チェックボックスリスト(保存用) */
    private String[] ntp130SvChkShohinSidList__ = null;



    /** カテゴリSID */
    private int ntp130CatSid__;
    /** カテゴリ情報List */
    private ArrayList<NtpShohinCategoryModel> ntp130CatList__  = null;
    /** チェックされている並び順 */
    private String ntp130SortRadio__;
    /** 表示順リスト */
    private String[] ntp130KeyList__;
    /** 編集カテゴリSID */
    private int ntp130EditSid__ = 0;
    /** 選択カテゴリSID */
    private int ntp130SelCategorySid__ = 1;


    /** ページ番号 */
    private int ntp130ShohinPageNum__ = 1;

    /**
     * @return ntp130SvChkShohinSidList
     */
    public String[] getNtp130SvChkShohinSidList() {
        return ntp130SvChkShohinSidList__;
    }
    /**
     * @param ntp130SvChkShohinSidList 設定する ntp130SvChkShohinSidList
     */
    public void setNtp130SvChkShohinSidList(String[] ntp130SvChkShohinSidList) {
        ntp130SvChkShohinSidList__ = ntp130SvChkShohinSidList;
    }
    /**
     * @return ntp130ChkShohinSidList
     */
    public String[] getNtp130ChkShohinSidList() {
        return ntp130ChkShohinSidList__;
    }
    /**
     * @param ntp130ChkShohinSidList 設定する ntp130ChkShohinSidList
     */
    public void setNtp130ChkShohinSidList(String[] ntp130ChkShohinSidList) {
        ntp130ChkShohinSidList__ = ntp130ChkShohinSidList;
    }
    /**
     * @return ntp130InitFlg
     */
    public int getNtp130InitFlg() {
        return ntp130InitFlg__;
    }
    /**
     * @param ntp130InitFlg 設定する ntp130InitFlg
     */
    public void setNtp130InitFlg(int ntp130InitFlg) {
        ntp130InitFlg__ = ntp130InitFlg;
    }
    /**
     * @return ntp130DspMode
     */
    public String getNtp130DspMode() {
        return ntp130DspMode__;
    }
    /**
     * @param ntp130DspMode 設定する ntp130DspMode
     */
    public void setNtp130DspMode(String ntp130DspMode) {
        ntp130DspMode__ = ntp130DspMode;
    }
    /**
     * @return ntp130ReturnPage
     */
    public String getNtp130ReturnPage() {
        return ntp130ReturnPage__;
    }
    /**
     * @param ntp130ReturnPage 設定する ntp130ReturnPage
     */
    public void setNtp130ReturnPage(String ntp130ReturnPage) {
        ntp130ReturnPage__ = ntp130ReturnPage;
    }
    /**
     * @return ntp130NhnSid
     */
    public int getNtp130NhnSid() {
        return ntp130NhnSid__;
    }
    /**
     * @param ntp130NhnSid 設定する ntp130NhnSid
     */
    public void setNtp130NhnSid(int ntp130NhnSid) {
        ntp130NhnSid__ = ntp130NhnSid;
    }
    /**
     * @return ntp130ProcMode
     */
    public String getNtp130ProcMode() {
        return ntp130ProcMode__;
    }
    /**
     * @param ntp130ProcMode 設定する ntp130ProcMode
     */
    public void setNtp130ProcMode(String ntp130ProcMode) {
        ntp130ProcMode__ = ntp130ProcMode;
    }

    /**
     * @return ntp130ShohinList
     */
    public ArrayList<Ntp130ShohinModel> getNtp130ShohinList() {
        return ntp130ShohinList__;
    }
    /**
     * @param ntp130ShohinList 設定する ntp130ShohinList
     */
    public void setNtp130ShohinList(ArrayList<Ntp130ShohinModel> ntp130ShohinList) {
        ntp130ShohinList__ = ntp130ShohinList;
    }
    /**
     * @return ntp130Sort1List
     */
    public List<LabelValueBean> getNtp130SortList() {
        return ntp130SortList__;
    }
    /**
     * @param ntp130SortList 設定する ntp130SortList
     */
    public void setNtp130SortList(List<LabelValueBean> ntp130SortList) {
        ntp130SortList__ = ntp130SortList;
    }
    /**
     * @return ntp130NhnCode
     */
    public String getNtp130NhnCode() {
        return ntp130NhnCode__;
    }
    /**
     * @param ntp130NhnCode 設定する ntp130NhnCode
     */
    public void setNtp130NhnCode(String ntp130NhnCode) {
        ntp130NhnCode__ = ntp130NhnCode;
    }
    /**
     * @return ntp130NhnName
     */
    public String getNtp130NhnName() {
        return ntp130NhnName__;
    }
    /**
     * @param ntp130NhnName 設定する ntp130NhnName
     */
    public void setNtp130NhnName(String ntp130NhnName) {
        ntp130NhnName__ = ntp130NhnName;
    }
    /**
     * @return ntp130NhnPriceCost
     */
    public String getNtp130NhnPriceCost() {
        return ntp130NhnPriceCost__;
    }
    /**
     * @param ntp130NhnPriceCost 設定する ntp130NhnPriceCost
     */
    public void setNtp130NhnPriceCost(String ntp130NhnPriceCost) {
        ntp130NhnPriceCost__ = ntp130NhnPriceCost;
    }
    /**
     * @return ntp130NhnPriceCostKbn
     */
    public int getNtp130NhnPriceCostKbn() {
        return ntp130NhnPriceCostKbn__;
    }
    /**
     * @param ntp130NhnPriceCostKbn 設定する ntp130NhnPriceCostKbn
     */
    public void setNtp130NhnPriceCostKbn(int ntp130NhnPriceCostKbn) {
        ntp130NhnPriceCostKbn__ = ntp130NhnPriceCostKbn;
    }
    /**
     * @return ntp130NhnPriceSale
     */
    public String getNtp130NhnPriceSale() {
        return ntp130NhnPriceSale__;
    }
    /**
     * @param ntp130NhnPriceSale 設定する ntp130NhnPriceSale
     */
    public void setNtp130NhnPriceSale(String ntp130NhnPriceSale) {
        ntp130NhnPriceSale__ = ntp130NhnPriceSale;
    }
    /**
     * @return ntp130NhnPriceSaleKbn
     */
    public int getNtp130NhnPriceSaleKbn() {
        return ntp130NhnPriceSaleKbn__;
    }
    /**
     * @param ntp130NhnPriceSaleKbn 設定する ntp130NhnPriceSaleKbn
     */
    public void setNtp130NhnPriceSaleKbn(int ntp130NhnPriceSaleKbn) {
        ntp130NhnPriceSaleKbn__ = ntp130NhnPriceSaleKbn;
    }
    /**
     * @return ntp130OrderKey1
     */
    public int getNtp130OrderKey1() {
        return ntp130OrderKey1__;
    }
    /**
     * @param ntp130OrderKey1 設定する ntp130OrderKey1
     */
    public void setNtp130OrderKey1(int ntp130OrderKey1) {
        ntp130OrderKey1__ = ntp130OrderKey1;
    }
    /**
     * @return ntp130OrderKey2
     */
    public int getNtp130OrderKey2() {
        return ntp130OrderKey2__;
    }
    /**
     * @param ntp130OrderKey2 設定する ntp130OrderKey2
     */
    public void setNtp130OrderKey2(int ntp130OrderKey2) {
        ntp130OrderKey2__ = ntp130OrderKey2;
    }
    /**
     * @return ntp130SortKey1
     */
    public int getNtp130SortKey1() {
        return ntp130SortKey1__;
    }
    /**
     * @param ntp130SortKey1 設定する ntp130SortKey1
     */
    public void setNtp130SortKey1(int ntp130SortKey1) {
        ntp130SortKey1__ = ntp130SortKey1;
    }
    /**
     * @return ntp130SortKey2
     */
    public int getNtp130SortKey2() {
        return ntp130SortKey2__;
    }
    /**
     * @param ntp130SortKey2 設定する ntp130SortKey2
     */
    public void setNtp130SortKey2(int ntp130SortKey2) {
        ntp130SortKey2__ = ntp130SortKey2;
    }
    /**
     * @return ntp130Page
     */
    public int getNtp130Page() {
        return ntp130Page__;
    }
    /**
     * @param ntp130Page 設定する ntp130Page
     */
    public void setNtp130Page(int ntp130Page) {
        ntp130Page__ = ntp130Page;
    }
    /**
     * @return ntp130PageBottom
     */
    public int getNtp130PageBottom() {
        return ntp130PageBottom__;
    }
    /**
     * @param ntp130PageBottom 設定する ntp130PageBottom
     */
    public void setNtp130PageBottom(int ntp130PageBottom) {
        ntp130PageBottom__ = ntp130PageBottom;
    }
    /**
     * @return ntp130PageCmbList
     */
    public List<LabelValueBean> getNtp130PageCmbList() {
        return ntp130PageCmbList__;
    }
    /**
     * @param ntp130PageCmbList 設定する ntp130PageCmbList
     */
    public void setNtp130PageCmbList(List<LabelValueBean> ntp130PageCmbList) {
        ntp130PageCmbList__ = ntp130PageCmbList;
    }
    /**
     * @return ntp130PageTop
     */
    public int getNtp130PageTop() {
        return ntp130PageTop__;
    }
    /**
     * @param ntp130PageTop 設定する ntp130PageTop
     */
    public void setNtp130PageTop(int ntp130PageTop) {
        ntp130PageTop__ = ntp130PageTop;
    }
    /**
     * @return ntp130SelectedSid
     */
    public ArrayList<String> getNtp130SelectedSid() {
        return ntp130SelectedSid__;
    }
    /**
     * @param ntp130SelectedSid 設定する ntp130SelectedSid
     */
    public void setNtp130SelectedSid(ArrayList<String> ntp130SelectedSid) {
        ntp130SelectedSid__ = ntp130SelectedSid;
    }

    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setNtp130HiddenParam(Cmn999Form msgForm) {
        msgForm.addHiddenParam("ntp130NhnCode", ntp130NhnCode__);
        msgForm.addHiddenParam("ntp130NhnName", ntp130NhnName__);
        msgForm.addHiddenParam("ntp130NhnPriceSale", ntp130NhnPriceSale__);
        msgForm.addHiddenParam("ntp130NhnPriceCost", ntp130NhnPriceCost__);
        msgForm.addHiddenParam("ntp130NhnPriceSaleKbn", ntp130NhnPriceSaleKbn__);
        msgForm.addHiddenParam("ntp130NhnPriceCostKbn ", ntp130NhnPriceCostKbn__);
        msgForm.addHiddenParam("ntp130SortKey1", ntp130SortKey1__);
        msgForm.addHiddenParam("ntp130OrderKey1", ntp130OrderKey1__);
        msgForm.addHiddenParam("ntp130SortKey2", ntp130SortKey2__);
        msgForm.addHiddenParam("ntp130OrderKey2", ntp130OrderKey2__);
        msgForm.addHiddenParam("ntp130Page", ntp130Page__);
        msgForm.addHiddenParam("ntp130PageTop", ntp130PageTop__);
        msgForm.addHiddenParam("ntp130PageBottom", ntp130PageBottom__);
        msgForm.addHiddenParam("ntp130ProcMode", ntp130ProcMode__);
        msgForm.addHiddenParam("ntp130ReturnPage", ntp130ReturnPage__);
        msgForm.addHiddenParam("ntp130DspMode", ntp130DspMode__);
        msgForm.addHiddenParam("ntp130InitFlg", ntp130InitFlg__);
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck()
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        //商品コード入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_SHOHIN_CODE,
                ntp130NhnCode__,
               "ntp130NhnCode",
                GSConstNippou.MAX_LENGTH_SHOHIN_CODE,
                false);

        //商品名入力チェック
        GSValidateNippou.validateCmnFieldText(errors,
                GSConstNippou.TEXT_SHOHIN_NAME,
                ntp130NhnName__,
               "ntp130NhnName",
                GSConstNippou.MAX_LENGTH_SHOHIN_NAME,
                false);

        //販売価格入力チェック
        if (!StringUtil.isNullZeroStringSpace(ntp130NhnPriceSale__)) {
            GSValidateNippou.validateCmnFieldTextNumber(errors,
                    GSConstNippou.TEXT_PRICE_SALE,
                    ntp130NhnPriceSale__.replaceAll(",", ""),
                   "ntp130NhnPriceSale",
                    GSConstNippou.MAX_LENGTH_PRICE_SALE,
                    false);
        }


        //原価価格入力チェック
        if (!StringUtil.isNullZeroStringSpace(ntp130NhnPriceCost__)) {
            GSValidateNippou.validateCmnFieldTextNumber(errors,
                    GSConstNippou.TEXT_PRICE_COST,
                    ntp130NhnPriceCost__.replaceAll(",", ""),
                   "ntp130NhnPriceCost",
                    GSConstNippou.MAX_LENGTH_PRICE_COST,
                    false);
        }

        return errors;
    }
    /**
     * <p>ntp130DspKbn を取得します。
     * @return ntp130DspKbn
     */
    public int getNtp130DspKbn() {
        return ntp130DspKbn__;
    }
    /**
     * <p>ntp130DspKbn をセットします。
     * @param ntp130DspKbn ntp130DspKbn
     */
    public void setNtp130DspKbn(int ntp130DspKbn) {
        ntp130DspKbn__ = ntp130DspKbn;
    }

    /**
     * <p>ntp130CatList を取得します。
     * @return ntp130CatList
     */
    public ArrayList<NtpShohinCategoryModel> getNtp130CatList() {
        return ntp130CatList__;
    }
    /**
     * <p>ntp130CatList をセットします。
     * @param ntp130CatList ntp130CatList
     */
    public void setNtp130CatList(ArrayList<NtpShohinCategoryModel> ntp130CatList) {
        ntp130CatList__ = ntp130CatList;
    }
    /**
     * <p>ntp130SortRadio を取得します。
     * @return ntp130SortRadio
     */
    public String getNtp130SortRadio() {
        return ntp130SortRadio__;
    }
    /**
     * <p>ntp130SortRadio をセットします。
     * @param ntp130SortRadio ntp130SortRadio
     */
    public void setNtp130SortRadio(String ntp130SortRadio) {
        ntp130SortRadio__ = ntp130SortRadio;
    }
    /**
     * <p>ntp130KeyList を取得します。
     * @return ntp130KeyList
     */
    public String[] getNtp130KeyList() {
        return ntp130KeyList__;
    }
    /**
     * <p>ntp130KeyList をセットします。
     * @param ntp130KeyList ntp130KeyList
     */
    public void setNtp130KeyList(String[] ntp130KeyList) {
        ntp130KeyList__ = ntp130KeyList;
    }
    /**
     * <p>ntp130EditSid を取得します。
     * @return ntp130EditSid
     */
    public int getNtp130EditSid() {
        return ntp130EditSid__;
    }
    /**
     * <p>ntp130EditSid をセットします。
     * @param ntp130EditSid ntp130EditSid
     */
    public void setNtp130EditSid(int ntp130EditSid) {
        ntp130EditSid__ = ntp130EditSid;
    }
    /**
     * <p>ntp130ShohinPageNum を取得します。
     * @return ntp130ShohinPageNum
     */
    public int getNtp130ShohinPageNum() {
        return ntp130ShohinPageNum__;
    }
    /**
     * <p>ntp130ShohinPageNum をセットします。
     * @param ntp130ShohinPageNum ntp130ShohinPageNum
     */
    public void setNtp130ShohinPageNum(int ntp130ShohinPageNum) {
        ntp130ShohinPageNum__ = ntp130ShohinPageNum;
    }
    /**
     * <p>ntp130CategoryList を取得します。
     * @return ntp130CategoryList
     */
    public List<LabelValueBean> getNtp130CategoryList() {
        return ntp130CategoryList__;
    }
    /**
     * <p>ntp130CategoryList をセットします。
     * @param ntp130CategoryList ntp130CategoryList
     */
    public void setNtp130CategoryList(List<LabelValueBean> ntp130CategoryList) {
        ntp130CategoryList__ = ntp130CategoryList;
    }
    /**
     * <p>ntp130CatSid を取得します。
     * @return ntp130CatSid
     */
    public int getNtp130CatSid() {
        return ntp130CatSid__;
    }
    /**
     * <p>ntp130CatSid をセットします。
     * @param ntp130CatSid ntp130CatSid
     */
    public void setNtp130CatSid(int ntp130CatSid) {
        ntp130CatSid__ = ntp130CatSid;
    }
    /**
     * <p>ntp130SelCategorySid を取得します。
     * @return ntp130SelCategorySid
     */
    public int getNtp130SelCategorySid() {
        return ntp130SelCategorySid__;
    }
    /**
     * <p>ntp130SelCategorySid をセットします。
     * @param ntp130SelCategorySid ntp130SelCategorySid
     */
    public void setNtp130SelCategorySid(int ntp130SelCategorySid) {
        ntp130SelCategorySid__ = ntp130SelCategorySid;
    }

}