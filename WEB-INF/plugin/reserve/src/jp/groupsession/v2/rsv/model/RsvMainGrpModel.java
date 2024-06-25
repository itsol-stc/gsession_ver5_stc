package jp.groupsession.v2.rsv.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100SisYrkModel;

/**
 * <br>[機  能] メイン用予約状況一覧施設グループ を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvMainGrpModel extends AbstractModel {

    /** グループ名称 */
    private String rsgName__ = null;
    /** 施設予約情報リスト */
    private ArrayList<Rsv100SisYrkModel> sisetuList__ = null;

    /**
     * <p>rsgName を取得します。
     * @return rsgName
     */
    public String getRsgName() {
        return rsgName__;
    }
    /**
     * <p>rsgName をセットします。
     * @param rsgName rsgName
     */
    public void setRsgName(String rsgName) {
        rsgName__ = rsgName;
    }
    /**
     * <p>sisetuList を取得します。
     * @return sisetuList
     */
    public ArrayList<Rsv100SisYrkModel> getSisetuList() {
        return sisetuList__;
    }
    /**
     * <p>sisetuList をセットします。
     * @param sisetuList sisetuList
     */
    public void setSisetuList(ArrayList<Rsv100SisYrkModel> sisetuList) {
        sisetuList__ = sisetuList;
    }

}