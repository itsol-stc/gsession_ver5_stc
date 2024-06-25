package jp.groupsession.v2.sch.sch200;

import java.util.ArrayList;

import jp.groupsession.v2.sch.sch010.Sch010DayOfModel;

/**
 * <br>[機  能] ユーザ別の1日分スケジュール情報と当月区分、休日区分を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200DayOfModel extends Sch010DayOfModel {

    /** 表示用日付 */
    private String dspDay__;
    /** 休日区分 */
    private int holidayKbn__;
    /** 当月区分 */
    private int thisMonthKbn__;
    /** 六曜表示(css) */
    private String rokuyouClass__;

    /** スケジュール情報リスト(in SimpleScheduleModel) */
    private ArrayList < Sch200SimpleModel > sch200DataList__ = null;


    /**
     * @return dspDay を戻します。
     */
    public String getDspDay() {
        return dspDay__;
    }
    /**
     * @param dspDay 設定する dspDay。
     */
    public void setDspDay(String dspDay) {
        dspDay__ = dspDay;
    }
    /**
     * @return holidayKbn を戻します。
     */
    public int getHolidayKbn() {
        return holidayKbn__;
    }
    /**
     * @param holidayKbn 設定する holidayKbn。
     */
    public void setHolidayKbn(int holidayKbn) {
        holidayKbn__ = holidayKbn;
    }
    /**
     * @return thisMonthKbn を戻します。
     */
    public int getThisMonthKbn() {
        return thisMonthKbn__;
    }
    /**
     * @param thisMonthKbn 設定する thisMonthKbn。
     */
    public void setThisMonthKbn(int thisMonthKbn) {
        thisMonthKbn__ = thisMonthKbn;
    }
    /**
     * <p>rokuyouClass を取得します。
     * @return rokuyouClass
     * @see jp.groupsession.v2.sch.sch200.Sch200DayOfModel#rokuyouClass
     */
    public String getRokuyouClass() {
        return rokuyouClass__;
    }
    /**
     * <p>rokuyouClass をセットします。
     * @param rokuyouClass rokuyouClass
     * @see jp.groupsession.v2.sch.sch200.Sch200DayOfModel#rokuyouClass
     */
    public void setRokuyouClass(String rokuyouClass) {
        rokuyouClass__ = rokuyouClass;
    }
    /**
     * <p>sch200DataList を取得します。
     * @return sch200DataList
     */
    public ArrayList<Sch200SimpleModel> getSch200DataList() {
        return sch200DataList__;
    }
    /**
     * <p>sch200DataList をセットします。
     * @param sch200DataList sch200DataList
     */
    public void setSch200DataList(ArrayList<Sch200SimpleModel> sch200DataList) {
        sch200DataList__ = sch200DataList;
    }
}
