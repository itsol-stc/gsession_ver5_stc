package jp.groupsession.v2.rsv.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 選択済施設(非表示) グループ を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvHidGroupModel extends AbstractModel {

    /** グループ名称 */
    private String rsgName__ = null;
    /** 施設リスト */
    private ArrayList<RsvHidSisetuModel> sisetuList__ = null;

    /**
     * <p>rsgName__ を取得します。
     * @return rsgName
     */
    public String getRsgName() {
        return rsgName__;
    }
    /**
     * <p>rsgName__ をセットします。
     * @param rsgName rsgName__
     */
    public void setRsgName(String rsgName) {
        rsgName__ = rsgName;
    }
    /**
     * <p>sisetuList__ を取得します。
     * @return sisetuList
     */
    public ArrayList<RsvHidSisetuModel> getSisetuList() {
        return sisetuList__;
    }
    /**
     * <p>sisetuList__ をセットします。
     * @param sisetuList sisetuList__
     */
    public void setSisetuList(ArrayList<RsvHidSisetuModel> sisetuList) {
        sisetuList__ = sisetuList;
    }
}