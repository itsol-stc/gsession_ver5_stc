package jp.groupsession.v2.sch.sch200;

/**
 * <br>[機  能] スケジュール 個人週間JSONデータ定義を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200Header {
    /** left */
    private String left__ = null;
    /** center */
    private String center__ = null;
    /** right */
    private String right__ = null;
    /**
     * <p>center を取得します。
     * @return center
     */
    public String getCenter() {
        return center__;
    }
    /**
     * <p>center をセットします。
     * @param center center
     */
    public void setCenter(String center) {
        center__ = center;
    }
    /**
     * <p>left を取得します。
     * @return left
     */
    public String getLeft() {
        return left__;
    }
    /**
     * <p>left をセットします。
     * @param left left
     */
    public void setLeft(String left) {
        left__ = left;
    }
    /**
     * <p>right を取得します。
     * @return right
     */
    public String getRight() {
        return right__;
    }
    /**
     * <p>right をセットします。
     * @param right right
     */
    public void setRight(String right) {
        right__ = right;
    }
}
