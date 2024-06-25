package jp.groupsession.v2.ntp.ntp120;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 日報 マスタメンテナンス画面で使用するマスタデータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp120DataModel {
    /** 登録件数 */
    private int count__ = 0;
    /** 最終更新日 */
    private UDate lastEdate__ = null;
    /**
     * <p>count を取得します。
     * @return count
     */
    public int getCount() {
        return count__;
    }
    /**
     * <p>count をセットします。
     * @param count count
     */
    public void setCount(int count) {
        count__ = count;
    }
    /**
     * <p>lastEdate を取得します。
     * @return lastEdate
     */
    public UDate getLastEdate() {
        return lastEdate__;
    }
    /**
     * <p>lastEdate をセットします。
     * @param lastEdate lastEdate
     */
    public void setLastEdate(UDate lastEdate) {
        lastEdate__ = lastEdate;
    }
}
