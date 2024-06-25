package jp.groupsession.v2.cht.cht150kn;

import java.util.List;

import jp.groupsession.v2.cht.cht150.Cht150Form;

/**
 *
 * <br>[機  能]  カテゴリ作成編集確認のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cht150knForm extends Cht150Form {

    /** グループ名一覧 */
    private List<String> cht150knGrpNameList__ = null;

    /**
     * <p>cht150knGrpNameList を取得します。
     * @return cht150knGrpNameList
     * @see jp.groupsession.v2.cht.cht150kn.Cht150knForm#cht150knGrpNameList__
     */
    public List<String> getCht150knGrpNameList() {
        return cht150knGrpNameList__;
    }

    /**
     * <p>cht150knGrpNameList をセットします。
     * @param cht150knGrpNameList cht150knGrpNameList
     * @see jp.groupsession.v2.cht.cht150kn.Cht150knForm#cht150knGrpNameList__
     */
    public void setCht150knGrpNameList(List<String> cht150knGrpNameList) {
        cht150knGrpNameList__ = cht150knGrpNameList;
    }



}
