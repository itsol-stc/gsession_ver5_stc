package jp.groupsession.v2.sch.sch010;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 1週間毎のスケジュール情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch010WeekOfModel extends AbstractModel {

    /** ユーザ情報 */
    private Sch010UsrModel sch010UsrMdl__ = null;
    /** スケジュール情報リスト */
    private ArrayList < Sch010DayOfModel > sch010SchList__ = null;
    /** スケジュール登録可能フラグ */
    private boolean schRegistFlg__ = false;

    /** 期間スケジュール存在フラグ */
    private int sch010PeriodRow__ = GSConstSchedule.PERIOD_NOT_EXSIST;
    /** スケジュール情報リスト(期間) */
    private ArrayList<ArrayList<SimpleScheduleModel>> sch010NoTimeSchList__ = null;

    //その他プラグインの利用可能状況
    /** 在席管理プラグイン利用可:0・不可:1*/
    private int zaisekiUseOk__ = GSConstSchedule.PLUGIN_USE;
    /** ショートメールプラグイン利用可:0・不可:1*/
    private int smailUseOk__ = GSConstSchedule.PLUGIN_USE;

    /**
     * <p>smailUseOk を取得します。
     * @return smailUseOk
     */
    public int getSmailUseOk() {
        return smailUseOk__;
    }
    /**
     * <p>smailUseOk をセットします。
     * @param smailUseOk smailUseOk
     */
    public void setSmailUseOk(int smailUseOk) {
        smailUseOk__ = smailUseOk;
    }
    /**
     * <p>zaisekiUseOk を取得します。
     * @return zaisekiUseOk
     */
    public int getZaisekiUseOk() {
        return zaisekiUseOk__;
    }
    /**
     * <p>zaisekiUseOk をセットします。
     * @param zaisekiUseOk zaisekiUseOk
     */
    public void setZaisekiUseOk(int zaisekiUseOk) {
        zaisekiUseOk__ = zaisekiUseOk;
    }
    /**
     * @return sch010SchList を戻します。
     */
    public ArrayList < Sch010DayOfModel > getSch010SchList() {
        return sch010SchList__;
    }
    /**
     * @param sch010SchList 設定する sch010SchList。
     */
    public void setSch010SchList(ArrayList < Sch010DayOfModel > sch010SchList) {
        sch010SchList__ = sch010SchList;
    }
    /**
     * @return sch010UsrMdl を戻します。
     */
    public Sch010UsrModel getSch010UsrMdl() {
        return sch010UsrMdl__;
    }
    /**
     * @param sch010UsrMdl 設定する sch010UsrMdl。
     */
    public void setSch010UsrMdl(Sch010UsrModel sch010UsrMdl) {
        sch010UsrMdl__ = sch010UsrMdl;
    }
    /**
     * <p>sch010NoTimeSchList を取得します。
     * @return sch010NoTimeSchList
     */
    public ArrayList<ArrayList<SimpleScheduleModel>> getSch010NoTimeSchList() {
        return sch010NoTimeSchList__;
    }
    /**
     * <p>sch010NoTimeSchList をセットします。
     * @param sch010NoTimeSchList sch010NoTimeSchList
     */
    public void setSch010NoTimeSchList(
            ArrayList<ArrayList<SimpleScheduleModel>> sch010NoTimeSchList) {
        sch010NoTimeSchList__ = sch010NoTimeSchList;
    }
    /**
     * <p>sch010PeriodRow を取得します。
     * @return sch010PeriodRow
     */
    public int getSch010PeriodRow() {
        return sch010PeriodRow__;
    }
    /**
     * <p>sch010PeriodRow をセットします。
     * @param sch010PeriodRow sch010PeriodRow
     */
    public void setSch010PeriodRow(int sch010PeriodRow) {
        sch010PeriodRow__ = sch010PeriodRow;
    }
    /**
     * <p>schRegistFlg を取得します。
     * @return schRegistFlg
     */
    public boolean isSchRegistFlg() {
        return schRegistFlg__;
    }
    /**
     * <p>schRegistFlg をセットします。
     * @param schRegistFlg schRegistFlg
     */
    public void setSchRegistFlg(boolean schRegistFlg) {
        schRegistFlg__ = schRegistFlg;
    }
}
