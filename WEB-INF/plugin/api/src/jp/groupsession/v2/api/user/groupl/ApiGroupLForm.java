package jp.groupsession.v2.api.user.groupl;

import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;

/**
 * <br>[機  能] グループ一覧を取得するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "user-groupl",
plugin = "user", name = "グループ一覧取得",
url = "/api/user/groupl.do", reqtype = "GET")
public class ApiGroupLForm extends AbstractApiForm {
    /** マイグループフラグ*/
    private int mygroup__  = 0; // 0:マイグループ含まない / 1:マイグループ含む

    /** デフォルトフラグ*/
    private int defgroup__ = 0; // 0:デフォルトグループ情報なし / 1:デフォルトグループ情報あり

    /**
     * <p>mygroup を取得します。
     * @return mygroup
     */
    public int getMygroup() {
        return mygroup__;
    }
    /**
     * <p>mygroup をセットします。
     * @param mygroup マイグループフラグ
     */
    public void setMygroup(int mygroup) {
        mygroup__ = mygroup;
    }

    /**
     * <p>defgroup を取得します。
     * @return defgroup
     */
    public int getDefgroup() {
        return defgroup__;
    }
    /**
     * <p>defgroup をセットします。
     * @param defgroup デフォルトグループフラグ
     */
    public void setDefgroup(int defgroup) {
        defgroup__ = defgroup;
    }
}
