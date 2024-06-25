package jp.groupsession.v2.rsv;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 予約を日間画面表示用に編集したModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvDailyValueModel extends AbstractModel {

    /** スケジュール情報 */
    private RsvYoyakuModel yoyakuMdl__;
    /** 横幅(cols/rows) */
    private int cols__;
    /** インデックス(出力先index) */
    private int index__;

    /**
     * <p>cols__ を取得します。
     * @return cols
     */
    public int getCols() {
        return cols__;
    }
    /**
     * <p>cols__ をセットします。
     * @param cols cols__
     */
    public void setCols(int cols) {
        cols__ = cols;
    }
    /**
     * <p>index__ を取得します。
     * @return index
     */
    public int getIndex() {
        return index__;
    }
    /**
     * <p>index__ をセットします。
     * @param index index__
     */
    public void setIndex(int index) {
        index__ = index;
    }
    /**
     * <p>yoyakuMdl__ を取得します。
     * @return yoyakuMdl
     */
    public RsvYoyakuModel getYoyakuMdl() {
        return yoyakuMdl__;
    }
    /**
     * <p>yoyakuMdl__ をセットします。
     * @param yoyakuMdl yoyakuMdl__
     */
    public void setYoyakuMdl(RsvYoyakuModel yoyakuMdl) {
        yoyakuMdl__ = yoyakuMdl;
    }
}