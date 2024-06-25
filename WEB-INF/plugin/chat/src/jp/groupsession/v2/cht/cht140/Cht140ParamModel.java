package jp.groupsession.v2.cht.cht140;

import java.util.List;

import jp.groupsession.v2.cht.GSConstChat;
import jp.groupsession.v2.cht.cht020.Cht020ParamModel;
import jp.groupsession.v2.cht.model.ChtCategoryModel;

/**
 *
 * <br>[機  能] チャット カテゴリ管理のパラメータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht140ParamModel extends Cht020ParamModel {

    /** カテゴリ */
    private int cht140CategoryLink__ = -1;
    /** カテゴリ登録編集区分 */
    private int cht140ProcMode__ = GSConstChat.CHAT_MODE_ADD;
    /** カテゴリリスト */
    private List<ChtCategoryModel> cht140CategoryList__ = null;

    /**
     * <p>cht140CategoryList を取得します。
     * @return cht140CategoryList
     * @see jp.groupsession.v2.cht.cht140.Cht140ParamModel#cht140CategoryList__
     */
    public List<ChtCategoryModel> getCht140CategoryList() {
        return cht140CategoryList__;
    }

    /**
     * <p>cht140CategoryList をセットします。
     * @param cht140CategoryList cht140CategoryList
     * @see jp.groupsession.v2.cht.cht140.Cht140ParamModel#cht140CategoryList__
     */
    public void setCht140CategoryList(List<ChtCategoryModel> cht140CategoryList) {
        cht140CategoryList__ = cht140CategoryList;
    }

    /**
     * <p>cht140CategoryLink を取得します。
     * @return cht140CategoryLink
     * @see jp.groupsession.v2.cht.cht140.Cht140ParamModel#cht140CategoryLink__
     */
    public int getCht140CategoryLink() {
        return cht140CategoryLink__;
    }

    /**
     * <p>cht140CategoryLink をセットします。
     * @param cht140CategoryLink cht140CategoryLink
     * @see jp.groupsession.v2.cht.cht140.Cht140ParamModel#cht140CategoryLink__
     */
    public void setCht140CategoryLink(int cht140CategoryLink) {
        cht140CategoryLink__ = cht140CategoryLink;
    }

    /**
     * <p>cht140ProcMode を取得します。
     * @return cht140ProcMode
     * @see jp.groupsession.v2.cht.cht140.Cht140ParamModel#cht140ProcMode__
     */
    public int getCht140ProcMode() {
        return cht140ProcMode__;
    }

    /**
     * <p>cht140ProcMode をセットします。
     * @param cht140ProcMode cht140ProcMode
     * @see jp.groupsession.v2.cht.cht140.Cht140ParamModel#cht140ProcMode__
     */
    public void setCht140ProcMode(int cht140ProcMode) {
        cht140ProcMode__ = cht140ProcMode;
    }

}
