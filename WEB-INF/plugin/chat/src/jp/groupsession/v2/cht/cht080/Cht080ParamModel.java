package jp.groupsession.v2.cht.cht080;


import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cht.cht020.Cht020ParamModel;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;




/**
 * <br>[機  能] チャット 特例アクセス一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht080ParamModel extends Cht020ParamModel {

    /** 検索キーワード */
    private String cht080keyword__ = null;
    /** ページ上段 */
    private int cht080pageTop__ = 0;
    /** ページ下段 */
    private int cht080pageBottom__ = 0;
    /** ページ表示フラグ */
    private boolean cht080pageDspFlg__ = false;

    /** 検索キーワード(検索条件保持) */
    private String cht080svKeyword__ = null;

    /** ソートキー */
    private int cht080sortKey__ = GSConstWebmail.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int cht080order__ = GSConstWebmail.ORDER_ASC;

    /** 送信先リスト(編集対象) */
    private int cht080editData__ = 0;
    /** 検索実行フラグ */
    private int cht080searchFlg__ = 0;
    /** 編集モード */
    private int cht080editMode__ = 0;

    /** ページコンボ */
    private List<LabelValueBean> pageCombo__ = null;

    /** 検索結果一覧 */
    private List<Cht080SpAccessModel> spAccessList__ = null;
    /** 選択された送信先リスト */
    private String[] cht080selectSpAccessList__;

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
//        super.setHiddenParam(msgForm);

        msgForm.addHiddenParam("cht080keyword", cht080keyword__);
        msgForm.addHiddenParam("cht080pageTop", cht080pageTop__);
        msgForm.addHiddenParam("cht080pageBottom", cht080pageBottom__);
        msgForm.addHiddenParam("cht080pageDspFlg", Boolean.toString(cht080pageDspFlg__));
        msgForm.addHiddenParam("cht080svKeyword", cht080svKeyword__);
        msgForm.addHiddenParam("cht080sortKey", cht080sortKey__);
        msgForm.addHiddenParam("cht080order", cht080order__);
        msgForm.addHiddenParam("cht080editData", cht080editData__);
        msgForm.addHiddenParam("cht080searchFlg", cht080searchFlg__);
        msgForm.addHiddenParam("cht080editMode", cht080editMode__);
        msgForm.addHiddenParam("cht080selectSpAccessList", cht080selectSpAccessList__);
    }


    /**
     * <br>[機  能] 削除ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateDelete(RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);

        //選択された特例アクセス
        if (cht080selectSpAccessList__ == null || cht080selectSpAccessList__.length < 1) {
            msg = new ActionMessage("error.select.required.text",
                                                    gsMsg.getMessage("schedule.Cht080.02"));
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
        }

        return errors;
    }

    /**
     * <p>Cht080keyword を取得します。
     * @return Cht080keyword
     */
    public String getCht080keyword() {
        return cht080keyword__;
    }

    /**
     * <p>Cht080keyword をセットします。
     * @param cht080keyword Cht080keyword
     */
    public void setCht080keyword(String cht080keyword) {
        cht080keyword__ = cht080keyword;
    }

    /**
     * <p>Cht080pageTop を取得します。
     * @return Cht080pageTop
     */
    public int getCht080pageTop() {
        return cht080pageTop__;
    }

    /**
     * <p>Cht080pageTop をセットします。
     * @param cht080pageTop cht080pageTop
     */
    public void setCht080pageTop(int cht080pageTop) {
        cht080pageTop__ = cht080pageTop;
    }

    /**
     * <p>Cht080pageBottom を取得します。
     * @return cht080pageBottom
     */
    public int getCht080pageBottom() {
        return cht080pageBottom__;
    }

    /**
     * <p>Cht080pageBottom をセットします。
     * @param cht080pageBottom cht080pageBottom
     */
    public void setCht080pageBottom(int cht080pageBottom) {
        cht080pageBottom__ = cht080pageBottom;
    }

    /**
     * <p>Cht080pageDspFlg を取得します。
     * @return cht080pageDspFlg
     */
    public boolean isCht080pageDspFlg() {
        return cht080pageDspFlg__;
    }

    /**
     * <p>Cht080pageDspFlg をセットします。
     * @param cht080pageDspFlg cht080pageDspFlg
     */
    public void setCht080pageDspFlg(boolean cht080pageDspFlg) {
        cht080pageDspFlg__ = cht080pageDspFlg;
    }

    /**
     * <p>Cht080svKeyword を取得します。
     * @return cht080svKeyword
     */
    public String getCht080svKeyword() {
        return cht080svKeyword__;
    }

    /**
     * <p>Cht080svKeyword をセットします。
     * @param cht080svKeyword cht080svKeyword
     */
    public void setCht080svKeyword(String cht080svKeyword) {
        cht080svKeyword__ = cht080svKeyword;
    }

    /**
     * <p>Cht080sortKey を取得します。
     * @return cht080sortKey
     */
    public int getCht080sortKey() {
        return cht080sortKey__;
    }

    /**
     * <p>Cht080sortKey をセットします。
     * @param cht080sortKey cht080sortKey
     */
    public void setCht080sortKey(int cht080sortKey) {
        cht080sortKey__ = cht080sortKey;
    }

    /**
     * <p>Cht080order を取得します。
     * @return cht080order
     */
    public int getCht080order() {
        return cht080order__;
    }

    /**
     * <p>Cht080order をセットします。
     * @param cht080order cht080order
     */
    public void setCht080order(int cht080order) {
        cht080order__ = cht080order;
    }

    /**
     * <p>Cht080editData を取得します。
     * @return Cht080editData
     */
    public int getCht080editData() {
        return cht080editData__;
    }

    /**
     * <p>Cht080editData をセットします。
     * @param cht080editData cht080editData
     */
    public void setCht080editData(int cht080editData) {
        cht080editData__ = cht080editData;
    }

    /**
     * <p>Cht080searchFlg を取得します。
     * @return cht080searchFlg
     */
    public int getCht080searchFlg() {
        return cht080searchFlg__;
    }

    /**
     * <p>Cht080searchFlg をセットします。
     * @param cht080searchFlg cht080searchFlg
     */
    public void setCht080searchFlg(int cht080searchFlg) {
        cht080searchFlg__ = cht080searchFlg;
    }

    /**
     * <p>Cht080editMode を取得します。
     * @return cht080editMode
     */
    public int getCht080editMode() {
        return cht080editMode__;
    }

    /**
     * <p>Cht080editMode をセットします。
     * @param cht080editMode cht080editMode
     */
    public void setCht080editMode(int cht080editMode) {
        cht080editMode__ = cht080editMode;
    }

    /**
     * <p>pageCombo を取得します。
     * @return pageCombo
     */
    public List<LabelValueBean> getPageCombo() {
        return pageCombo__;
    }

    /**
     * <p>pageCombo をセットします。
     * @param pageCombo pageCombo
     */
    public void setPageCombo(List<LabelValueBean> pageCombo) {
        pageCombo__ = pageCombo;
    }

    /**
     * <p>spAccessList を取得します。
     * @return spAccessList
     */
    public List<Cht080SpAccessModel> getSpAccessList() {
        return spAccessList__;
    }


    /**
     * <p>spAccessList をセットします。
     * @param spAccessList spAccessList
     */
    public void setSpAccessList(List<Cht080SpAccessModel> spAccessList) {
        spAccessList__ = spAccessList;
    }


    /**
     * <p>Cht080selectSpAccessList を取得します。
     * @return cht080selectSpAccessList
     */
    public String[] getCht080selectSpAccessList() {
        return cht080selectSpAccessList__;
    }

    /**
     * <p>Cht080selectSpAccessList をセットします。
     * @param cht080selectSpAccessList cht080selectSpAccessList
     */
    public void setCht080selectSpAccessList(String[] cht080selectSpAccessList) {
        cht080selectSpAccessList__ = cht080selectSpAccessList;
    }
}
