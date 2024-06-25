package jp.groupsession.v2.cht.cht140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cht.ChatValidate;
import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht020.Cht020Form;
import jp.groupsession.v2.cht.model.ChtCategoryModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] チャット カテゴリ管理のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht140Form extends Cht020Form {

    /** カテゴリ */
    private int cht140CategoryLink__ = -1;
    /** カテゴリ登録編集区分 */
    private int cht140ProcMode__ = GSConstChat.CHAT_MODE_ADD;
    /** カテゴリリスト */
    private List<ChtCategoryModel> cht140CategoryList__ = null;


    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param form フォーム
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCht140(
            RequestModel reqMdl, Connection con, Cht140Form form) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        // カテゴリ
        if (cht140ProcMode__ == GSConstChat.CHAT_MODE_EDIT) {
            errors = ChatValidate.validateIsExitCategory(
                    errors,
                    gsMsg.getMessage("user.47"),
                    cht140CategoryLink__,
                    "category",
                    con,
                    false);
        }
        return errors;
    }


    /**
     * <p>cht140CategoryList を取得します。
     * @return cht140CategoryList
     * @see jp.groupsession.v2.cht.cht140.Cht140Form#cht140CategoryList__
     */
    public List<ChtCategoryModel> getCht140CategoryList() {
        return cht140CategoryList__;
    }


    /**
     * <p>cht140CategoryList をセットします。
     * @param cht140CategoryList cht140CategoryList
     * @see jp.groupsession.v2.cht.cht140.Cht140Form#cht140CategoryList__
     */
    public void setCht140CategoryList(List<ChtCategoryModel> cht140CategoryList) {
        cht140CategoryList__ = cht140CategoryList;
    }


    /**
     * <p>cht140CategoryLink を取得します。
     * @return cht140CategoryLink
     * @see jp.groupsession.v2.cht.cht140.Cht140Form#cht140CategoryLink__
     */
    public int getCht140CategoryLink() {
        return cht140CategoryLink__;
    }


    /**
     * <p>cht140CategoryLink をセットします。
     * @param cht140CategoryLink cht140CategoryLink
     * @see jp.groupsession.v2.cht.cht140.Cht140Form#cht140CategoryLink__
     */
    public void setCht140CategoryLink(int cht140CategoryLink) {
        cht140CategoryLink__ = cht140CategoryLink;
    }


    /**
     * <p>cht140ProcMode を取得します。
     * @return cht140ProcMode
     * @see jp.groupsession.v2.cht.cht140.Cht140Form#cht140ProcMode__
     */
    public int getCht140ProcMode() {
        return cht140ProcMode__;
    }


    /**
     * <p>cht140ProcMode をセットします。
     * @param cht140ProcMode cht140ProcMode
     * @see jp.groupsession.v2.cht.cht140.Cht140Form#cht140ProcMode__
     */
    public void setCht140ProcMode(int cht140ProcMode) {
        cht140ProcMode__ = cht140ProcMode;
    }





}
