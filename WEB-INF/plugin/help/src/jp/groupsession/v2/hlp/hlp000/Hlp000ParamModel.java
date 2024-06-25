package jp.groupsession.v2.hlp.hlp000;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] ヘルプ サイドメニューの情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Hlp000ParamModel extends AbstractParamModel {
    /** メニュー情報 */
    private List<MenuInfo> menuInfoList__ = null;

    /**
     * <p>menuInfoList を取得します。
     * @return menuInfoList
     */
    public List<MenuInfo> getMenuInfoList() {
        return menuInfoList__;
    }

    /**
     * <p>menuInfoList をセットします。
     * @param menuInfoList menuInfoList
     */
    public void setMenuInfoList(List<MenuInfo> menuInfoList) {
        menuInfoList__ = menuInfoList;
    }
}