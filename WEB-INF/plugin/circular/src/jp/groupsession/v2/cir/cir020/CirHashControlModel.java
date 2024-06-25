package jp.groupsession.v2.cir.cir020;

import java.util.HashMap;

import jp.groupsession.v2.cir.model.CircularDspModel;

/**
 * <br>[機  能] Hashステータスを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirHashControlModel {

    /** 該当行番号 */
    private int rowNum__;
    /** 全データハッシュ */
    private HashMap < Integer, CircularDspModel > map__;

    /**
     * <p>map を取得します。
     * @return map
     */
    public HashMap < Integer, CircularDspModel > getMap() {
        return map__;
    }
    /**
     * <p>map をセットします。
     * @param map map
     */
    public void setMap(HashMap < Integer, CircularDspModel > map) {
        map__ = map;
    }
    /**
     * <p>rowNum を取得します。
     * @return rowNum
     */
    public int getRowNum() {
        return rowNum__;
    }
    /**
     * <p>rowNum をセットします。
     * @param rowNum rowNum
     */
    public void setRowNum(int rowNum) {
        rowNum__ = rowNum;
    }

}
