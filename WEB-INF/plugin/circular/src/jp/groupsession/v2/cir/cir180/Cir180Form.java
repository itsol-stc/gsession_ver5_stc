package jp.groupsession.v2.cir.cir180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cir.biz.CirCommonBiz;
import jp.groupsession.v2.cir.cir070.Cir070Form;
import jp.groupsession.v2.struts.msg.GsMessage;



/**
 * <br>[機  能] 回覧板 アカウントの管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir180Form extends Cir070Form {

    /** アカウントリスト */
    private List<Cir180AccountDataDspModel> accountList__ = null;
    /** チェックされている並び順 */
    private String cir180sortAccount__ = null;
    /** 並び替え対象のラベル */
    private String[] cir180sortLabel__ = null;
    /** 非管理者のアカウント登録判定 */
    private int cir180MakeAcntHnt__ = GSConstCircular.ACCOUNT_ADD_OK;


    /**
     * <br>[機  能] ラベルボタンクリック時チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param userSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateLabelCheck(Connection con, int userSid)
        throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        CirCommonBiz cirBiz = new CirCommonBiz();
        if (!cirBiz.canUseAccount(
                con, userSid, getCirAccountSid())) {
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
    public List<Cir180AccountDataDspModel> getAccountList() {
        return accountList__;
    }

    /**
     * <p>accountList をセットします。
     * @param accountList accountList
     */
    public void setAccountList(List<Cir180AccountDataDspModel> accountList) {
        accountList__ = accountList;
    }

    /**
     * <p>cir180sortAccount を取得します。
     * @return cir180sortAccount
     */
    public String getCir180sortAccount() {
        return cir180sortAccount__;
    }

    /**
     * <p>cir180sortAccount をセットします。
     * @param cir180sortAccount cir180sortAccount
     */
    public void setCir180sortAccount(String cir180sortAccount) {
        cir180sortAccount__ = cir180sortAccount;
    }

    /**
     * <p>cir180sortLabel を取得します。
     * @return cir180sortLabel
     */
    public String[] getCir180sortLabel() {
        return cir180sortLabel__;
    }

    /**
     * <p>cir180sortLabel をセットします。
     * @param cir180sortLabel cir180sortLabel
     */
    public void setCir180sortLabel(String[] cir180sortLabel) {
        cir180sortLabel__ = cir180sortLabel;
    }

    /**
     * @return cir180MakeAcntHnt
     */
    public int getCir180MakeAcntHnt() {
        return cir180MakeAcntHnt__;
    }

    /**
     * @param cir180MakeAcntHnt 設定する cir180MakeAcntHnt
     */
    public void setCir180MakeAcntHnt(int cir180MakeAcntHnt) {
        cir180MakeAcntHnt__ = cir180MakeAcntHnt;
    }

}
