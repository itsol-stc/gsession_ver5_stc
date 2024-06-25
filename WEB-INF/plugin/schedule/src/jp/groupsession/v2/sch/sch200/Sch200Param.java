package jp.groupsession.v2.sch.sch200;

import java.util.ArrayList;
import java.util.List;

/**
 * <br>[機  能] スケジュール 個人週間JSONデータ定義を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sch200Param {
    /** 表示形式 */
    private String defaultView__;
    /** 先頭表示時間 */
    private String firstHour__;
    /** 表示開始時間 */
    private String minTime__;
    /** 表示終了時間 */
    private String maxTime__;
    /** 先頭曜日 */
    private int firstDay__;
    /** timeFormat */
    private String timeFormat__;
    /** aspectRatio */
    private double aspectRatio__;
    /** theme */
    private boolean theme__;
    /** selectable */
    private boolean selectable__;
    /** selectHelper */
    private boolean selectHelper__;
    /** editable */
    private boolean editable__;
    /** event */
    @SuppressWarnings("all")
    private List events__;
    /** header */
    private Sch200Header header__;
    /** titleFormat */
    private Sch200TitleFormat titleFormat__;
    /** columnFormat */
    private Sch200ColumnFormat columnFormat__;
    /** dayNamesShort */
    @SuppressWarnings("all")
    private List dayNamesShort__;
    /** select */
    private String select__;
    /** slotMinutes */
    private int slotMinutes__;
    /** dayClick */
    private String dayClick__;
    /** eventClick */
    private String eventClick__;
    /** eventDrop */
    private String eventDrop__;
    /** eventResizeStop */
    private String eventResize__;
    /** 六曜リスト */
    private ArrayList<String> rokuyouList__;

    /**
     * <p>slotMinutes を取得します。
     * @return slotMinutes
     */
    public int getSlotMinutes() {
        return slotMinutes__;
    }

    /**
     * <p>slotMinutes をセットします。
     * @param slotMinutes slotMinutes
     */
    public void setSlotMinutes(int slotMinutes) {
        slotMinutes__ = slotMinutes;
    }

    /**
     * <p>dayNamesShort を取得します。
     * @return dayNamesShort
     */
    @SuppressWarnings("all")
    public List getDayNamesShort() {
        return dayNamesShort__;
    }

    /**
     * <p>dayNamesShort をセットします。
     * @param dayNamesShort dayNamesShort
     */
    @SuppressWarnings("all")
    public void setDayNamesShort(List dayNamesShort) {
        dayNamesShort__ = dayNamesShort;
    }

    /**
     * <p>events を取得します。
     * @return events
     */
    @SuppressWarnings("all")
    public List getEvents() {
        return events__;
    }

    /**
     * <p>events をセットします。
     * @param events events
     */
    @SuppressWarnings("all")
    public void setEvents(List events) {
        events__ = events;
    }

    /**
     * <p>defaultView を取得します。
     * @return defaultView
     */
    public String getDefaultView() {
        return defaultView__;
    }

    /**
     * <p>defaultView をセットします。
     * @param defaultView defaultView
     */
    public void setDefaultView(String defaultView) {
        defaultView__ = defaultView;
    }

    /**
     * <p>header を取得します。
     * @return header
     */
    public Sch200Header getHeader() {
        return header__;
    }

    /**
     * <p>header をセットします。
     * @param header header
     */
    public void setHeader(Sch200Header header) {
        header__ = header;
    }

    /**
     * <p>maxTime を取得します。
     * @return maxTime
     */
    public String getMaxTime() {
        return maxTime__;
    }

    /**
     * <p>maxTime をセットします。
     * @param maxTime maxTime
     */
    public void setMaxTime(String maxTime) {
        maxTime__ = maxTime;
    }

    /**
     * <p>minTime を取得します。
     * @return minTime
     */
    public String getMinTime() {
        return minTime__;
    }

    /**
     * <p>minTime をセットします。
     * @param minTime minTime
     */
    public void setMinTime(String minTime) {
        minTime__ = minTime;
    }

    /**
     * <p>titleFormat を取得します。
     * @return titleFormat
     */
    public Sch200TitleFormat getTitleFormat() {
        return titleFormat__;
    }

    /**
     * <p>titleFormat をセットします。
     * @param titleFormat titleFormat
     */
    public void setTitleFormat(Sch200TitleFormat titleFormat) {
        titleFormat__ = titleFormat;
    }

    /**
     * <p>columnFormat を取得します。
     * @return columnFormat
     */
    public Sch200ColumnFormat getColumnFormat() {
        return columnFormat__;
    }

    /**
     * <p>columnFormat をセットします。
     * @param columnFormat columnFormat
     */
    public void setColumnFormat(Sch200ColumnFormat columnFormat) {
        columnFormat__ = columnFormat;
    }

    /**
     * <p>timeFormat を取得します。
     * @return timeFormat
     */
    public String getTimeFormat() {
        return timeFormat__;
    }

    /**
     * <p>timeFormat をセットします。
     * @param timeFormat timeFormat
     */
    public void setTimeFormat(String timeFormat) {
        timeFormat__ = timeFormat;
    }

    /**
     * <p>aspectRatio を取得します。
     * @return aspectRatio
     */
    public double getAspectRatio() {
        return aspectRatio__;
    }

    /**
     * <p>aspectRatio をセットします。
     * @param aspectRatio aspectRatio
     */
    public void setAspectRatio(double aspectRatio) {
        aspectRatio__ = aspectRatio;
    }

    /**
     * <p>theme を取得します。
     * @return theme
     */
    public boolean isTheme() {
        return theme__;
    }

    /**
     * <p>theme をセットします。
     * @param theme theme
     */
    public void setTheme(boolean theme) {
        theme__ = theme;
    }

    /**
     * <p>selectable を取得します。
     * @return selectable
     */
    public boolean isSelectable() {
        return selectable__;
    }

    /**
     * <p>selectable をセットします。
     * @param selectable selectable
     */
    public void setSelectable(boolean selectable) {
        selectable__ = selectable;
    }

    /**
     * <p>selectHelper を取得します。
     * @return selectHelper
     */
    public boolean isSelectHelper() {
        return selectHelper__;
    }

    /**
     * <p>selectHelper をセットします。
     * @param selectHelper selectHelper
     */
    public void setSelectHelper(boolean selectHelper) {
        selectHelper__ = selectHelper;
    }

    /**
     * <p>editable を取得します。
     * @return editable
     */
    public boolean isEditable() {
        return editable__;
    }

    /**
     * <p>editable をセットします。
     * @param editable editable
     */
    public void setEditable(boolean editable) {
        editable__ = editable;
    }

    /**
     * <p>select を取得します。
     * @return select
     */
    public String getSelect() {
        return select__;
    }

    /**
     * <p>select をセットします。
     * @param select select
     */
    public void setSelect(String select) {
        select__ = select;
    }

    /**
     * <p>dayClick を取得します。
     * @return dayClick
     */
    public String getDayClick() {
        return dayClick__;
    }

    /**
     * <p>dayClick をセットします。
     * @param dayClick dayClick
     */
    public void setDayClick(String dayClick) {
        dayClick__ = dayClick;
    }

    /**
     * <p>eventClick を取得します。
     * @return eventClick
     */
    public String getEventClick() {
        return eventClick__;
    }

    /**
     * <p>eventClick をセットします。
     * @param eventClick eventClick
     */
    public void setEventClick(String eventClick) {
        eventClick__ = eventClick;
    }

    /**
     * <p>eventDrop を取得します。
     * @return eventDrop
     */
    public String getEventDrop() {
        return eventDrop__;
    }

    /**
     * <p>eventDrop をセットします。
     * @param eventDrop eventDrop
     */
    public void setEventDrop(String eventDrop) {
        eventDrop__ = eventDrop;
    }

    /**
     * <p>eventResize を取得します。
     * @return eventResize
     */
    public String getEventResize() {
        return eventResize__;
    }

    /**
     * <p>eventResize をセットします。
     * @param eventResize eventResize
     */
    public void setEventResize(String eventResize) {
        eventResize__ = eventResize;
    }

    /**
     * <p>firstHour を取得します。
     * @return firstHour
     */
    public String getFirstHour() {
        return firstHour__;
    }

    /**
     * <p>firstHour をセットします。
     * @param firstHour firstHour
     */
    public void setFirstHour(String firstHour) {
        firstHour__ = firstHour;
    }

    /**
     * <p>firstDay を取得します。
     * @return firstDay
     */
    public int getFirstDay() {
        return firstDay__;
    }

    /**
     * <p>firstDay をセットします。
     * @param firstDay firstDay
     */
    public void setFirstDay(int firstDay) {
        firstDay__ = firstDay;
    }

    /**
     * <p>rokuyouList を取得します。
     * @return rokuyouList
     * @see jp.groupsession.v2.sch.sch200.Sch200Param#rokuyouList__
     */
    public ArrayList<String> getRokuyouList() {
        return rokuyouList__;
    }

    /**
     * <p>rokuyouList をセットします。
     * @param rokuyouList rokuyouList
     * @see jp.groupsession.v2.sch.sch200.Sch200Param#rokuyouList__
     */
    public void setRokuyouList(ArrayList<String> rokuyouList) {
        rokuyouList__ = rokuyouList;
    }
}
