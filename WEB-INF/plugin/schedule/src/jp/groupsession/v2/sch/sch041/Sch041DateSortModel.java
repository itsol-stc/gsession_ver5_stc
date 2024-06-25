package jp.groupsession.v2.sch.sch041;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] 日付を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch041DateSortModel implements Comparable<Sch041DateSortModel> {
    /** compareTo(Sch041DateSortModel o)メソッド実施時、oがthisより大きい場合に返されます */
    public static final int LARGE = 1;
    /** compareTo(Sch041DateSortModel o)メソッド実施時、oが等しい場合に返されます */
    public static final int EQUAL = 0;
    /** compareTo(Sch041DateSortModel o)メソッド実施時、oがthisより小さい場合に返されます */
    public static final int SMALL = -1;

    /** 日付 */
    private UDate udate__ = null;

    /**
     * <p>udate を取得します。
     * @return udate
     */
    public UDate getUdate() {
        return udate__;
    }

    /**
     * <p>udate をセットします。
     * @param udate udate
     */
    public void setUdate(UDate udate) {
        udate__ = udate;
    }

    /**
     * ソート用比較メソット
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param o 比較対象
     * @return int
     */
    public int compareTo(Sch041DateSortModel o) {
        return o.getUdate().compareDateYMD(getUdate());
    }

}
