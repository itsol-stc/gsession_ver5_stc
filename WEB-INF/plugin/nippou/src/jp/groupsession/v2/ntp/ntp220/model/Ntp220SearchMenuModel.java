package jp.groupsession.v2.ntp.ntp220.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>[機  能] メニュー項目の検索結果を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp220SearchMenuModel implements Serializable {

    /** 最大ページサイズ */
    private int maxPageSize__ = 0;
    /** ページ番号 */
    private int pageNum__ = 0;
    /** ユーザ情報 */
    private List<Ntp220MenuParam> menuParamList__ = new ArrayList<Ntp220MenuParam>();


    /**
     * <p>maxPageSize を取得します。
     * @return maxPageSize
     */
    public int getMaxPageSize() {
        return maxPageSize__;
    }
    /**
     * <p>maxPageSize をセットします。
     * @param maxPageSize maxPageSize
     */
    public void setMaxPageSize(int maxPageSize) {
        maxPageSize__ = maxPageSize;
    }
    /**
     * <p>pageNum を取得します。
     * @return pageNum
     */
    public int getPageNum() {
        return pageNum__;
    }
    /**
     * <p>pageNum をセットします。
     * @param pageNum pageNum
     */
    public void setPageNum(int pageNum) {
        pageNum__ = pageNum;
    }
    /**
     * <p>menuParamList を取得します。
     * @return menuParamList
     */
    public List<Ntp220MenuParam> getMenuParamList() {
        return menuParamList__;
    }
    /**
     * <p>menuParamList をセットします。
     * @param menuParamList menuParamList
     */
    public void setMenuParamList(List<Ntp220MenuParam> menuParamList) {
        menuParamList__ = menuParamList;
    }
}
