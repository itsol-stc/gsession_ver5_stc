package jp.groupsession.v2.ntp.ntp088;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSValidateNippou;
import jp.groupsession.v2.ntp.ntp080.Ntp080Form;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] 日報 特例アクセス一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp088Form extends Ntp080Form {

    /** 検索キーワード 最大文字数 */
    private static final int MAXLEN_SEARCH_KEYWORD__ = 50;

    /** ユーザ情報 or アクセス制御コンボ(選択用)のグループコンボに設定する値 グループ一覧のVALUE */
    public static final int GROUP_COMBO_VALUE = -9;
    /** 特例アクセス名 MAX文字数 */
    public static final int MAXLEN_NAME = 50;
    /** 備考 MAX文字数 */
    public static final int MAXLEN_BIKO = 1000;
    /** 役職 権限区分 追加・変更・削除 */
    public static final int POTION_AUTH_EDIT = 0;
    /** 役職 権限区分 閲覧 */
    public static final int POTION_AUTH_VIEW = 1;

    /** 検索キーワード */
    private String ntp088keyword__ = null;
    /** ページ上段 */
    private int ntp088pageTop__ = 0;
    /** ページ下段 */
    private int ntp088pageBottom__ = 0;
    /** ページ表示フラグ */
    private boolean ntp088pageDspFlg__ = false;

    /** 検索キーワード(検索条件保持) */
    private String ntp088svKeyword__ = null;

    /** ソートキー */
    private int ntp088sortKey__ = GSConstWebmail.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int ntp088order__ = GSConstWebmail.ORDER_ASC;

    /** 送信先リスト(編集対象) */
    private int ntp088editData__ = 0;
    /** 検索実行フラグ */
    private int ntp088searchFlg__ = 0;
    /** 編集モード */
    private int ntp088editMode__ = 0;

    /** ページコンボ */
    private List<LabelValueBean> pageCombo__ = null;

    /** 検索結果一覧 */
    private List<Ntp088SpAccessModel> spAccessList__ = null;
    /** 選択された送信先リスト */
    private String[] ntp088selectSpAccessList__;

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("ntp088keyword", ntp088keyword__);
        msgForm.addHiddenParam("ntp088pageTop", ntp088pageTop__);
        msgForm.addHiddenParam("ntp088pageBottom", ntp088pageBottom__);
        msgForm.addHiddenParam("ntp088pageDspFlg", Boolean.toString(ntp088pageDspFlg__));
        msgForm.addHiddenParam("ntp088svKeyword", ntp088svKeyword__);
        msgForm.addHiddenParam("ntp088sortKey", ntp088sortKey__);
        msgForm.addHiddenParam("ntp088order", ntp088order__);
        msgForm.addHiddenParam("ntp088editData", ntp088editData__);
        msgForm.addHiddenParam("ntp088searchFlg", ntp088searchFlg__);
        msgForm.addHiddenParam("ntp088editMode", ntp088editMode__);
        msgForm.addHiddenParam("ntp088selectSpAccessList", ntp088selectSpAccessList__);
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCheck(Connection con, RequestModel reqMdl)
    throws SQLException {

        ActionErrors errors = new ActionErrors();

        //検索キーワード
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateNippou.validateCmnFieldText(errors, ntp088keyword__,
                                        "ntp088keyword",
                                        gsMsg.getMessage("cmn.keyword"),
                                        MAXLEN_SEARCH_KEYWORD__, false);

        //検索結果件数チェック
        if (errors.isEmpty()) {

            Ntp088ParamModel paramMdl = new Ntp088ParamModel();
            paramMdl.setParam(this);
            paramMdl.setNtp088svKeyword(getNtp088keyword());
            paramMdl.setNtp088searchFlg(0);

            Ntp088Biz biz = new Ntp088Biz();
            if (biz.getRecordCount(con, paramMdl, reqMdl) <= 0) {
                ActionMessage msg
                    = new ActionMessage("search.data.notfound",
                                                    gsMsg.getMessage("schedule.sch230.02"));
                StrutsUtil.addMessage(errors, msg, "wml270keyword");
            }
        }

        return errors;
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
        if (ntp088selectSpAccessList__ == null || ntp088selectSpAccessList__.length < 1) {
            msg = new ActionMessage("error.select.required.text",
                                                    gsMsg.getMessage("schedule.Ntp088.02"));
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
        }

        return errors;
    }

    /**
     * <p>Ntp088keyword を取得します。
     * @return Ntp088keyword
     */
    public String getNtp088keyword() {
        return ntp088keyword__;
    }

    /**
     * <p>Ntp088keyword をセットします。
     * @param ntp088keyword Ntp088keyword
     */
    public void setNtp088keyword(String ntp088keyword) {
        ntp088keyword__ = ntp088keyword;
    }

    /**
     * <p>Ntp088pageTop を取得します。
     * @return Ntp088pageTop
     */
    public int getNtp088pageTop() {
        return ntp088pageTop__;
    }

    /**
     * <p>Ntp088pageTop をセットします。
     * @param ntp088pageTop ntp088pageTop
     */
    public void setNtp088pageTop(int ntp088pageTop) {
        ntp088pageTop__ = ntp088pageTop;
    }

    /**
     * <p>Ntp088pageBottom を取得します。
     * @return ntp088pageBottom
     */
    public int getNtp088pageBottom() {
        return ntp088pageBottom__;
    }

    /**
     * <p>Ntp088pageBottom をセットします。
     * @param ntp088pageBottom ntp088pageBottom
     */
    public void setNtp088pageBottom(int ntp088pageBottom) {
        ntp088pageBottom__ = ntp088pageBottom;
    }

    /**
     * <p>Ntp088pageDspFlg を取得します。
     * @return ntp088pageDspFlg
     */
    public boolean isNtp088pageDspFlg() {
        return ntp088pageDspFlg__;
    }

    /**
     * <p>Ntp088pageDspFlg をセットします。
     * @param ntp088pageDspFlg ntp088pageDspFlg
     */
    public void setNtp088pageDspFlg(boolean ntp088pageDspFlg) {
        ntp088pageDspFlg__ = ntp088pageDspFlg;
    }

    /**
     * <p>Ntp088svKeyword を取得します。
     * @return ntp088svKeyword
     */
    public String getNtp088svKeyword() {
        return ntp088svKeyword__;
    }

    /**
     * <p>Ntp088svKeyword をセットします。
     * @param ntp088svKeyword ntp088svKeyword
     */
    public void setNtp088svKeyword(String ntp088svKeyword) {
        ntp088svKeyword__ = ntp088svKeyword;
    }

    /**
     * <p>Ntp088sortKey を取得します。
     * @return ntp088sortKey
     */
    public int getNtp088sortKey() {
        return ntp088sortKey__;
    }

    /**
     * <p>Ntp088sortKey をセットします。
     * @param ntp088sortKey ntp088sortKey
     */
    public void setNtp088sortKey(int ntp088sortKey) {
        ntp088sortKey__ = ntp088sortKey;
    }

    /**
     * <p>Ntp088order を取得します。
     * @return ntp088order
     */
    public int getNtp088order() {
        return ntp088order__;
    }

    /**
     * <p>Ntp088order をセットします。
     * @param ntp088order ntp088order
     */
    public void setNtp088order(int ntp088order) {
        ntp088order__ = ntp088order;
    }

    /**
     * <p>Ntp088editData を取得します。
     * @return Ntp088editData
     */
    public int getNtp088editData() {
        return ntp088editData__;
    }

    /**
     * <p>Ntp088editData をセットします。
     * @param ntp088editData ntp088editData
     */
    public void setNtp088editData(int ntp088editData) {
        ntp088editData__ = ntp088editData;
    }

    /**
     * <p>Ntp088searchFlg を取得します。
     * @return ntp088searchFlg
     */
    public int getNtp088searchFlg() {
        return ntp088searchFlg__;
    }

    /**
     * <p>Ntp088searchFlg をセットします。
     * @param ntp088searchFlg ntp088searchFlg
     */
    public void setNtp088searchFlg(int ntp088searchFlg) {
        ntp088searchFlg__ = ntp088searchFlg;
    }

    /**
     * <p>Ntp088editMode を取得します。
     * @return ntp088editMode
     */
    public int getNtp088editMode() {
        return ntp088editMode__;
    }

    /**
     * <p>Ntp088editMode をセットします。
     * @param ntp088editMode ntp088editMode
     */
    public void setNtp088editMode(int ntp088editMode) {
        ntp088editMode__ = ntp088editMode;
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
    public List<Ntp088SpAccessModel> getSpAccessList() {
        return spAccessList__;
    }


    /**
     * <p>spAccessList をセットします。
     * @param spAccessList spAccessList
     */
    public void setSpAccessList(List<Ntp088SpAccessModel> spAccessList) {
        spAccessList__ = spAccessList;
    }


    /**
     * <p>Ntp088selectSpAccessList を取得します。
     * @return ntp088selectSpAccessList
     */
    public String[] getNtp088selectSpAccessList() {
        return ntp088selectSpAccessList__;
    }

    /**
     * <p>Ntp088selectSpAccessList をセットします。
     * @param ntp088selectSpAccessList ntp088selectSpAccessList
     */
    public void setNtp088selectSpAccessList(String[] ntp088selectSpAccessList) {
        ntp088selectSpAccessList__ = ntp088selectSpAccessList;
    }
}
