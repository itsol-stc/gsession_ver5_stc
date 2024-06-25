package jp.groupsession.v2.cht.model;

import java.io.Serializable;

/**
 * <br>[機  能] チャット 自動/手動削除条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ChatDeleteModel implements Serializable {

    /** 年 */
    private int delYear__ = 0;
    /** 月 */
    private int delMonth__ = 0;
    /**
     * <p>delYear を取得します。
     * @return delYear
     */
    public int getDelYear() {
        return delYear__;
    }
    /**
     * <p>delYear をセットします。
     * @param delYear delYear
     */
    public void setDelYear(int delYear) {
        delYear__ = delYear;
    }
    /**
     * <p>delMonth を取得します。
     * @return delMonth
     */
    public int getDelMonth() {
        return delMonth__;
    }
    /**
     * <p>delMonth をセットします。
     * @param delMonth delMonth
     */
    public void setDelMonth(int delMonth) {
        delMonth__ = delMonth;
    }

}
