package jp.groupsession.v2.rsv.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 選択済施設(非表示) 日付 を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvHidDayModel extends AbstractModel {

    /** 日付 */
    private String hidDayStr__ = null;
    /** グループリスト */
    private ArrayList<RsvHidGroupModel> grpList__ = null;

    /**
     * <p>grpList__ を取得します。
     * @return grpList
     */
    public ArrayList<RsvHidGroupModel> getGrpList() {
        return grpList__;
    }
    /**
     * <p>grpList__ をセットします。
     * @param grpList grpList__
     */
    public void setGrpList(ArrayList<RsvHidGroupModel> grpList) {
        grpList__ = grpList;
    }
    /**
     * <p>hidDayStr__ を取得します。
     * @return hidDayStr
     */
    public String getHidDayStr() {
        return hidDayStr__;
    }
    /**
     * <p>hidDayStr__ をセットします。
     * @param hidDayStr hidDayStr__
     */
    public void setHidDayStr(String hidDayStr) {
        hidDayStr__ = hidDayStr;
    }
}