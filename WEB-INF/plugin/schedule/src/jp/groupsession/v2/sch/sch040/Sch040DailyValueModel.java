package jp.groupsession.v2.sch.sch040;

import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;

/**
 * <br>[機  能] スケジュール情報、日間画面表示用情報を格納したModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch040DailyValueModel extends AbstractModel {

    /** スケジュール情報 */
    private SimpleScheduleModel scheduleMdl__;
    /** 横幅(cols/rows) */
    private int cols__;

    /** インデックス(出力先index) */
    private int index__;

    /**
     * @return index を戻します。
     */
    public int getIndex() {
        return index__;
    }
    /**
     * @param index 設定する index。
     */
    public void setIndex(int index) {
        index__ = index;
    }
    /**
     * @return cols を戻します。
     */
    public int getCols() {
        return cols__;
    }
    /**
     * @param cols 設定する cols。
     */
    public void setCols(int cols) {
        cols__ = cols;
    }
    /**
     * @return scheduleMdl を戻します。
     */
    public SimpleScheduleModel getScheduleMdl() {
        return scheduleMdl__;
    }
    /**
     * @param scheduleMdl 設定する scheduleMdl。
     */
    public void setScheduleMdl(SimpleScheduleModel scheduleMdl) {
        scheduleMdl__ = scheduleMdl;
    }
}
