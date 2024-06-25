package jp.groupsession.v2.ntp.ntp220.model;

import java.util.ArrayList;

/**
 * <br>[機  能] メニューに表示する情報を格納するモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp220ContentDataModel {
    /** contentDataList */
    private ArrayList<Object> contentDataList__ = null;

    /**
     * <p>contentDataList を取得します。
     * @return contentDataList
     */
    public ArrayList<Object> getContentDataList() {
        return contentDataList__;
    }

    /**
     * <p>contentDataList をセットします。
     * @param contentDataList contentDataList
     */
    public void setContentDataList(ArrayList<Object> contentDataList) {
        contentDataList__ = contentDataList;
    }

}
