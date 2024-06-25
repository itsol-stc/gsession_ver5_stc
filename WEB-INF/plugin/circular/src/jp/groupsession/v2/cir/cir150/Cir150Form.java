package jp.groupsession.v2.cir.cir150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.CirValidateUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.cir100.Cir100Form;
import jp.groupsession.v2.cir.dao.CirAccountDao;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;


/**
 * <br>[機  能] 回覧板 アカウントマネージャー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir150Form extends Cir100Form {

    /** 検索キーワード */
    private String cir150keyword__ = null;
    /** グループ */
    private int cir150group__ = -1;
    /** ユーザ */
    private int cir150user__ = -1;
    /** ページ上段 */
    private int cir150pageTop__ = 0;
    /** ページ下段 */
    private int cir150pageBottom__ = 0;
    /** ページ表示フラグ */
    private boolean cir150pageDspFlg__ = false;

    /** 検索キーワード(検索条件保持) */
    private String cir150svKeyword__ = null;
    /** グループ(検索条件保持) */
    private int cir150svGroup__ = -1;
    /** ユーザ(検索条件保持) */
    private int cir150svUser__ = -1;

    /** ソートキー */
    private int cir150sortKey__ = GSConstCircular.SKEY_ACCOUNTNAME;
    /** 並び順 */
    private int cir150order__ = GSConstCircular.ORDER_ASC;

    /** 検索実行フラグ */
    private int cir150searchFlg__ = 0;

    /** 検索結果一覧 */
    private List<Cir150AccountModel> accountList__ = null;
    /** ページコンボ */
    private List<LabelValueBean> pageCombo__ = null;

    /** グループコンボ */
    private List<LabelValueBean> groupCombo__ = null;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> userCombo__ = null;

    /** 選択されたアカウント */
    private String[] cir150selectAcount__;

    /** ポップアップ区分 */
    private int cir150popKbn__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行例外
     * @throws IOToolsException SQL実行例外
     * @throws Exception 実行例外
     */
    public ActionErrors validateCheck(HttpServletRequest req)
        throws IOToolsException, SQLException, Exception {

        ActionErrors errors = new ActionErrors();

        //検索キーワード
        CirValidateUtil.validateTextField(errors, cir150keyword__,
                                        "cir150keyword",
                                        getInterMessage(req, GSConstCircular.TEXT_SEARCH_KEYWORD),
                                        GSConstCircular.MAXLEN_SEARCH_KEYWORD, false);
        return errors;
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateAccount(Connection con, HttpServletRequest req)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        //選択されたアカウント
        if (cir150selectAcount__ == null || cir150selectAcount__.length < 1) {
            msg = new ActionMessage("error.select.required.text", gsMsg.getMessage(req, "wml.102"));
            StrutsUtil.addMessage(errors, msg, "error.select.required.text");
        }

        return errors;
    }

    /**
     * <br>[機  能] ラベルボタンクリック時チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateLabelCheck(Connection con)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        // アカウント存在確認
        CirAccountDao dao = new CirAccountDao(con);
        boolean chk = dao.selectExistAccount(this.getCirAccountSid());
        if (!chk) {
            String msgKey = "search.data.notfound";
            ActionMessage msg = new ActionMessage(msgKey,
                    gsMsg.getMessage("wml.102"));
            StrutsUtil.addMessage(errors, msg, msgKey);
        }
        return errors;
    }

    /**
     * <p>accountList を取得します。
     * @return accountList
     */
    public List<Cir150AccountModel> getAccountList() {
        return accountList__;
    }
    /**
     * <p>accountList をセットします。
     * @param accountList accountList
     */
    public void setAccountList(List<Cir150AccountModel> accountList) {
        accountList__ = accountList;
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
     * <p>cir150group を取得します。
     * @return cir150group
     */
    public int getCir150group() {
        return cir150group__;
    }
    /**
     * <p>cir150group をセットします。
     * @param cir150group cir150group
     */
    public void setCir150group(int cir150group) {
        cir150group__ = cir150group;
    }
    /**
     * <p>cir150keyword を取得します。
     * @return cir150keyword
     */
    public String getCir150keyword() {
        return cir150keyword__;
    }
    /**
     * <p>cir150keyword をセットします。
     * @param cir150keyword cir150keyword
     */
    public void setCir150keyword(String cir150keyword) {
        cir150keyword__ = cir150keyword;
    }
    /**
     * <p>cir150order を取得します。
     * @return cir150order
     */
    public int getCir150order() {
        return cir150order__;
    }
    /**
     * <p>cir150order をセットします。
     * @param cir150order cir150order
     */
    public void setCir150order(int cir150order) {
        cir150order__ = cir150order;
    }
    /**
     * <p>cir150pageBottom を取得します。
     * @return cir150pageBottom
     */
    public int getCir150pageBottom() {
        return cir150pageBottom__;
    }
    /**
     * <p>cir150pageBottom をセットします。
     * @param cir150pageBottom cir150pageBottom
     */
    public void setCir150pageBottom(int cir150pageBottom) {
        cir150pageBottom__ = cir150pageBottom;
    }
    /**
     * <p>cir150pageTop を取得します。
     * @return cir150pageTop
     */
    public int getCir150pageTop() {
        return cir150pageTop__;
    }
    /**
     * <p>cir150pageTop をセットします。
     * @param cir150pageTop cir150pageTop
     */
    public void setCir150pageTop(int cir150pageTop) {
        cir150pageTop__ = cir150pageTop;
    }
    /**
     * <p>cir150sortKey を取得します。
     * @return cir150sortKey
     */
    public int getCir150sortKey() {
        return cir150sortKey__;
    }
    /**
     * <p>cir150sortKey をセットします。
     * @param cir150sortKey cir150sortKey
     */
    public void setCir150sortKey(int cir150sortKey) {
        cir150sortKey__ = cir150sortKey;
    }
    /**
     * <p>cir150user を取得します。
     * @return cir150user
     */
    public int getCir150user() {
        return cir150user__;
    }
    /**
     * <p>cir150user をセットします。
     * @param cir150user cir150user
     */
    public void setCir150user(int cir150user) {
        cir150user__ = cir150user;
    }
    /**
     * <p>cir150searchFlg を取得します。
     * @return cir150searchFlg
     */
    public int getCir150searchFlg() {
        return cir150searchFlg__;
    }
    /**
     * <p>cir150searchFlg をセットします。
     * @param cir150searchFlg cir150searchFlg
     */
    public void setCir150searchFlg(int cir150searchFlg) {
        cir150searchFlg__ = cir150searchFlg;
    }
    /**
     * <p>cir150svGroup を取得します。
     * @return cir150svGroup
     */
    public int getCir150svGroup() {
        return cir150svGroup__;
    }
    /**
     * <p>cir150svGroup をセットします。
     * @param cir150svGroup cir150svGroup
     */
    public void setCir150svGroup(int cir150svGroup) {
        cir150svGroup__ = cir150svGroup;
    }
    /**
     * <p>cir150svKeyword を取得します。
     * @return cir150svKeyword
     */
    public String getCir150svKeyword() {
        return cir150svKeyword__;
    }
    /**
     * <p>cir150svKeyword をセットします。
     * @param cir150svKeyword cir150svKeyword
     */
    public void setCir150svKeyword(String cir150svKeyword) {
        cir150svKeyword__ = cir150svKeyword;
    }
    /**
     * <p>cir150svUser を取得します。
     * @return cir150svUser
     */
    public int getCir150svUser() {
        return cir150svUser__;
    }
    /**
     * <p>cir150svUser をセットします。
     * @param cir150svUser cir150svUser
     */
    public void setCir150svUser(int cir150svUser) {
        cir150svUser__ = cir150svUser;
    }
    /**
     * <p>groupCombo を取得します。
     * @return groupCombo
     */
    public List<LabelValueBean> getGroupCombo() {
        return groupCombo__;
    }
    /**
     * <p>groupCombo をセットします。
     * @param groupCombo groupCombo
     */
    public void setGroupCombo(List<LabelValueBean> groupCombo) {
        groupCombo__ = groupCombo;
    }
    /**
     * <p>userCombo を取得します。
     * @return userCombo
     */
    public List<UsrLabelValueBean> getUserCombo() {
        return userCombo__;
    }
    /**
     * <p>userCombo をセットします。
     * @param userCombo userCombo
     */
    public void setUserCombo(List<UsrLabelValueBean> userCombo) {
        userCombo__ = userCombo;
    }

    /**
     * <p>cir150pageDspFlg を取得します。
     * @return cir150pageDspFlg
     */
    public boolean isCir150pageDspFlg() {
        return cir150pageDspFlg__;
    }

    /**
     * <p>cir150pageDspFlg をセットします。
     * @param cir150pageDspFlg cir150pageDspFlg
     */
    public void setCir150pageDspFlg(boolean cir150pageDspFlg) {
        cir150pageDspFlg__ = cir150pageDspFlg;
    }

    /**
     * <p>cir150selectAcount を取得します。
     * @return cir150selectAcount
     */
    public String[] getCir150selectAcount() {
        return cir150selectAcount__;
    }

    /**
     * <p>cir150selectAcount をセットします。
     * @param cir150selectAcount cir150selectAcount
     */
    public void setCir150selectAcount(String[] cir150selectAcount) {
        cir150selectAcount__ = cir150selectAcount;
    }

    /**
     * <p>cir150popKbn を取得します。
     * @return cir150popKbn
     */
    public int getCir150popKbn() {
        return cir150popKbn__;
    }

    /**
     * <p>cir150popKbn をセットします。
     * @param cir150popKbn cir150popKbn
     */
    public void setCir150popKbn(int cir150popKbn) {
        cir150popKbn__ = cir150popKbn;
    }
}
