package jp.groupsession.v2.sch;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.sch010.Sch010DayOfModel;
import jp.groupsession.v2.sch.sch010.Sch010UsrModel;
import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;
import jp.groupsession.v2.sch.sch040.Sch040DailyLineModel;
import jp.groupsession.v2.sch.sch040.Sch040DailyValueModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

/**
 * Tag that retrieves the specified property of the specified bean, converts
 * it to a String representation (if necessary), and writes it to the current
 * output stream, optionally filtering characters that are sensitive in HTML.
 *
 * @author JTS
 * @version $Revision: 1.1 $ $Date: 2016/11/04 08:45:42 $
 */
public class DailyScheduleRowTag extends TagSupport {
    /** ロギングクラス */
    public static Log log__ = LogFactory.getLog(DailyScheduleRowTag.class);

    /**
     * The key to search default format string for java.sql.Timestamp in resources.
     */
    public static final String SQL_TIMESTAMP_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.sql.timestamp";

    /**
     * The key to search default format string for java.sql.Date in resources.
     */
    public static final String SQL_DATE_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.sql.date";

    /**
     * The key to search default format string for java.sql.Time in resources.
     */
    public static final String SQL_TIME_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.sql.time";

    /**
     * The key to search default format string for java.util.Date in resources.
     */
    public static final String DATE_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.date";

    /**
     * The key to search default format string for int (byte, short, etc.) in resources.
     */
    public static final String INT_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.int";

    /**
     * The key to search default format string for float (double, BigDecimal) in
     * resources.
     */
    public static final String FLOAT_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.float";

    /**
     * Should we ignore missing beans and simply output nothing?
     */
    protected boolean ignore = false;

    /**
     * get ignore
     * @return boolean
     */
    public boolean getIgnore() {
        return (this.ignore);
    }

    /**
     * set ignore
     * @param b ignore
     */
    public void setIgnore(boolean b) {
        this.ignore = b;
    }

    /**
     * Name of the bean that contains the data we will be rendering.
     */
    protected String name = null;

    /**
     * get name
     * @return String
     */
    public String getName() {
        return (this.name);
    }

    /**
     * set name
     * @param string name
     */
    public void setName(String string) {
        this.name = string;
    }

    /**
     * Name of the property to be accessed on the specified bean.
     */
    protected String property = null;

    /**
     * get Property
     * @return String
     */
    public String getProperty() {
        return (this.property);
    }

    /**
     * set Property
     * @param string property
     */
    public void setProperty(String string) {
        this.property = string;
    }

    /**
     * The scope to be searched to retrieve the specified bean.
     */
    protected String scope = null;

    /**
     * get Scope
     * @return String
     */
    public String getScope() {
        return (this.scope);
    }

    /**
     * set Scope
     * @param string scope
     */
    public void setScope(String string) {
        this.scope = string;
    }

    /**
     * The servlet context attribute key for our resources.
     */
    protected String bundle = null;

    /**
     * getBundle
     * @return String
     */
    public String getBundle() {
        return (this.bundle);
    }

    /**
     * setBundle
     * @param string bundle
     */
    public void setBundle(String string) {
        this.bundle = string;
    }

    /**
     * 表示する時刻を設定
     */
    protected String hour = null;

    /**
     * getHour
     * @return String
     */
    public String getHour() {
        return (this.hour);
    }

    /**
     * setHour
     * @param string bundle
     */
    public void setHour(String string) {
        this.hour = string;
    }

    // --------------------------------------------------------- Public Methods



    /**
     * Process the start tag.
     * @return int
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {

        // Look up the requested bean (if necessary)
        if (ignore) {
            if (TagUtils.getInstance().lookup(pageContext, name, scope)
                == null) {
                return (SKIP_BODY); // Nothing to output
            }
        }
        // Look up the requested property value
        Object value =
            TagUtils.getInstance().lookup(pageContext, name, property, scope);

        if (value == null) {
            return (SKIP_BODY); // Nothing to output
        }
        //使用する型に置き換える

        if (value instanceof Sch010WeekOfModel) {
            //型が違う場合はエラー
            //            return (SKIP_BODY); // Nothing to output
        }
        Sch010WeekOfModel weekMdl = (Sch010WeekOfModel) value;
        JspWriter writer = pageContext.getOut();
        //      //再起的にHTMLを吐き出す。
        try {

            __writeTag(writer, weekMdl);

        } catch (Exception e) {
            throw new JspException("Jsp出力に失敗しました。", e);
        }

        // Continue processing this page
        return (SKIP_BODY);

    }

    /**
     * <br>HTMLをJspへ出力します。
     * @param writer JspWriter
     * @param weekMdl ユーザ別日間スケジュール情報
     * @throws Exception 出力エラー
     */
    private void __writeTag(JspWriter writer, Sch010WeekOfModel weekMdl)
        throws Exception {

        int intHour = GSConstSchedule.DF_FROM_HOUR;
        if (hour != null) {
            intHour = Integer.parseInt(hour);
        }

        Sch010UsrModel usrMdl = weekMdl.getSch010UsrMdl();
        ArrayList < Sch010DayOfModel > dayList = weekMdl.getSch010SchList();
        Sch010DayOfModel dayMdl = dayList.get(0);

        ArrayList < SimpleScheduleModel > schList = dayMdl.getSchDataList();
        //スケジュール情報を画面表示用に格納します
        ArrayList < Sch040DailyLineModel > dailyList =
            __getDspScheduleList(schList, dayMdl.getSchDate());

        __writeHtmlString(writer, usrMdl, dayMdl, dailyList, intHour);
    }

    /**
     * <br>Sch010DayOfModelからHTML文字列を取得します。
     * <br>１ユーザの日間スケジュール
     * @param writer ライター
     * @param usrMdl ユーザ情報
     * @param dayModel Sch010DayOfModel
     * @param dailyList 画面表示用スケジュール
     * @param intHour 表示時刻
     * @throws Exception IOエラー時にスロー
     */
    private static void __writeHtmlString(
        JspWriter writer,
        Sch010UsrModel usrMdl,
        Sch010DayOfModel dayModel,
        ArrayList < Sch040DailyLineModel > dailyList,
        int intHour)
        throws Exception {

//        int userSid = dayModel.getUsrSid();
//        int userKbn = dayModel.getUsrKbn();
//        int zaisekiKbn = usrMdl.getZaisekiKbn();
//        String schDate = dayModel.getSchDate();
//        int colspan = dailyList.size();


        Sch040DailyLineModel dailyLineMdl = null;
        ArrayList < Sch040DailyValueModel > valueList = null;
        Sch040DailyValueModel valueMdl = null;

        //タイムチャート部分出力
        writer.println("<td height=\"100%\">");
        writer.println("<table class=\"tl0\">");
        //0時～23時ループ
        int dspHour = 0;
        int counter = GSConstSchedule.HOUR_DIVISION_COUNT_10;
        for (int i = 0; i < GSConstSchedule.DAILY_ROWS_COUNT; i++) {
            if (counter == GSConstSchedule.HOUR_DIVISION_COUNT_10) {
                if (dspHour == intHour) {
                    writer.println("<tr id =\"offSetId\" align=\"center\">");
//                    writer.println("<a id =\"offSetId\" name=\"dspHour\"></a>");
                } else {
                    writer.println("<tr align=\"center\">");
                }
                writer.println("<th class=\"time_tl1\" width=\"30\" rowspan=\"4\" nowrap>");
                writer.println("<span class=\"sc_ttl_def\">" + dspHour + "</span></th>");
                writer.println("<td class=\"time_tl2\" width=\"10\">");
                writer.println("<img src=\"../schedule/images/space5.gif\"></td>");

                dspHour++;
                counter = 1;
            } else {
                if (dspHour == intHour) {
                    writer.println("<tr id =\"offSetId\" align=\"center\">");
//                    writer.println("<a id =\"offSetId\" name=\"dspHour\"></a>");
                } else {
                    writer.println("<tr align=\"center\">");
                }
                writer.println("<td class=\"time_tl2\" width=\"10\">");
                writer.println("<img src=\"../schedule/images/space5.gif\"></td>");
                counter++;
            }
            //挿入するスケジュールを検索
            for (int j = 0; j < dailyList.size(); j++) {
                dailyLineMdl = dailyList.get(j);
                valueList = dailyLineMdl.getLineList();
                //1列分処理
                valueMdl = __getTargetModel(valueList, i + 1);
                if (valueMdl != null) {
                    //スケジュールデータ
                    SimpleScheduleModel schMdl = valueMdl.getScheduleMdl();

                    if (schMdl != null) {
                        String time = schMdl.getTime();
                        String title = schMdl.getTitle();
                        String color = __getStyleBgColor(schMdl.getBgColor());
                        writer.println("<td class=\"" + color + "\" "
                                + " title=\"" + time + " " + title + "\""
                                + " rowspan=\"" + valueMdl.getCols() + "\""
                                + " width=\"210\" nowrap"
                                + " onClick=\"editSchedule('schw_edit',"
                                + " " + dayModel.getSchDate() + ","
                                + " " + schMdl.getSchSid() + ","
                                + " " + dayModel.getUsrSid() + ","
                                + " " + dayModel.getUsrKbn() + ");\" style=\"cursor:pointer\">");


                        if (valueMdl.getCols() > GSConstSchedule.MAIN_DSP_HOURS) {
                          writer.println("<a href=\"#\" title=\"" + title + "\""
                          + " onClick=\"editSchedule('schw_edit',"
                                              + " " + dayModel.getSchDate() + ","
                                              + " " + schMdl.getSchSid() + ","
                                              + " " + dayModel.getUsrSid() + ","
                                              + " " + dayModel.getUsrKbn() + ");\">");
                            switch (schMdl.getBgColor()) {
                            case 1:
                                writer.println("<span class=\"sc_link_1\">");
                                break;
                            case 2:
                                writer.println("<span class=\"sc_link_2\">");
                                break;
                            case 3:
                                writer.println("<span class=\"sc_link_3\">");
                                break;
                            case 4:
                                writer.println("<span class=\"sc_link_4\">");
                                break;
                            case 5:
                                writer.println("<span class=\"sc_link_5\">");
                                break;
                            case 6:
                                writer.println("<span class=\"sc_link_6\">");
                                break;
                            case 7:
                                writer.println("<span class=\"sc_link_7\">");
                                break;
                            case 8:
                                writer.println("<span class=\"sc_link_8\">");
                                break;
                            case 9:
                                writer.println("<span class=\"sc_link_9\">");
                                break;
                            case 10:
                                writer.println("<span class=\"sc_link_10\">");
                                break;
                            default:
                                writer.println("<span class=\"sc_link\">");
                                break;
                            }
                            writer.println("<font size=\"-2\">" + time + "</font><br>");
                            writer.println("" + title + "</span>");
                            writer.println("</a>");
                        }


                    } else {
                        writer.println("<td class=\"bgC_other_white\"");
                        writer.println("rowspan=\"" + valueMdl.getCols() + "\" width=\"210\" >");
                    }
                    writer.println("</td>");
                }
            }
            writer.println("</tr>");
        }
        writer.println("</table>");
        writer.println("</td>");

    }

    /**
     * <br>スケジュールリストに表示先インデックスに該当するスケジュールが有る場合、
     * <br>スケジュール情報(Sch040DailyValueModel)を返します。
     * <br>該当情報が無い場合はNULLを返します
     *
     * @param valueList スケジュールリスト
     * @param index 表示先インデックス
     * @return Sch040DailyValueModel
     */
    private static Sch040DailyValueModel __getTargetModel(
            ArrayList < Sch040DailyValueModel > valueList,
            int index) {
        Sch040DailyValueModel valueMdl = null;
        for (int i = 0; i < valueList.size(); i++) {
            valueMdl = valueList.get(i);
//            int cols = valueMdl.getCols();
            int dataIndex = valueMdl.getIndex();
            //判定
            if (index == dataIndex) {
                return valueMdl;
            }
        }
        return null;
    }

    /**
     * <br>背景色区分から対応するCSSクラスの文字列を取得します
     * @param bgColor 背景色区分
     * @return String CSSクラスの文字列
     */
    private static String __getStyleBgColor(int bgColor) {
        StringBuilder buf = new StringBuilder();
        buf.append("td_type_nikkan_");
        buf.append(bgColor);

        return buf.toString();
    }

    /**
     * <br>Release all allocated resources.
     */
    public void release() {

        super.release();
        ignore = false;
        name = null;
        property = null;
        scope = null;
        bundle = null;

    }

    /**
     * <br>スケジュール情報を画面表示用に格納します
     * @param schList スケジュールリスト
     * @param dspDate 表示日付
     * @return ArrayList < Sch040DailyLineModel >
     */
    private ArrayList<Sch040DailyLineModel> __getDspScheduleList(
            ArrayList<SimpleScheduleModel> schList,
            String dspDate) {

        //ユーザ別スケジュール一式(lineを格納)
        ArrayList<Sch040DailyLineModel> dailyList = new ArrayList<Sch040DailyLineModel>();

//        int schSid = -1;
//        UDate svEndDate = null;
//        UDate frDate = null;

        //1行分のスケジュール
        Sch040DailyLineModel lineMdl = new Sch040DailyLineModel();
        ArrayList<Sch040DailyValueModel> lineList = new ArrayList<Sch040DailyValueModel>();

        //1スケジュール
        Sch040DailyValueModel valueMdl = null;
//        SimpleScheduleModel schMdl = null;

        //スケジュール情報無し
        if (schList.size() == 0) {
            valueMdl = new Sch040DailyValueModel();
            valueMdl.setScheduleMdl(null);
            valueMdl.setCols(GSConstSchedule.DAILY_ROWS_COUNT);
            valueMdl.setIndex(0);
            lineList.add(valueMdl);
            lineMdl.setLineList(lineList);
            dailyList.add(lineMdl);
            return dailyList;
        }

        //出力済情報の格納用
        HashMap<String, SimpleScheduleModel> map
        = new HashMap<String, SimpleScheduleModel>();

        while (schList.size() != map.size()) {
            //１行分を作成する
            lineMdl = __getDailyLineMdl(schList, map, dspDate);
            dailyList.add(lineMdl);
         }

        return dailyList;
    }

    /**
     * <br>１行分のスケジュールモデルを生成する
     * <br>[備考]出力済みのスケジュール情報はHashMapに格納します
     * @param schList スケジュール情報
     * @param map 出力済みのスケジュール情報
     * @param dspDate 表示日付
     * @return Sch040DailyLineModel
     */
    private Sch040DailyLineModel __getDailyLineMdl(
            ArrayList<SimpleScheduleModel> schList,
            HashMap<String, SimpleScheduleModel> map,
            String dspDate) {

        Sch040DailyLineModel lineMdl = new Sch040DailyLineModel();
        ArrayList<Sch040DailyValueModel> lineList = new ArrayList<Sch040DailyValueModel>();
        Sch040DailyValueModel valueMdl = null;

        SimpleScheduleModel schMdl = null;
        int schSid = 0;
        int endIndex = 1;

        for (int i = 0; i < schList.size(); i++) {

            schMdl = schList.get(i);
            schSid = schMdl.getSchSid();
            if (map.containsKey(String.valueOf(schSid))) {
                //出力済みは除外
                continue;
            }

            if (schMdl.getTimeKbn() == GSConstSchedule.TIME_EXIST) {

                //格納可能か判定し可能であれば格納する
                if (__isStorage(schMdl, dspDate, endIndex)) {

                    //空白部分を設定
                    valueMdl = __getBlankSchedule(schMdl, dspDate, endIndex);
                    if (valueMdl != null) {

                        lineList.add(valueMdl);
                        endIndex = endIndex + valueMdl.getCols();

                    }
                    //スケジュール部分を設定
                    int rows = __getRows(schMdl, dspDate, endIndex);
                    valueMdl = new Sch040DailyValueModel();
                    valueMdl.setCols(rows);
                    valueMdl.setScheduleMdl(schMdl);
                    valueMdl.setIndex(endIndex);
                    lineList.add(valueMdl);
                    endIndex = endIndex + rows;
                    map.put(String.valueOf(schSid), schMdl);

                } else {
                    log__.debug("格納不可");
                }

            } else if (schMdl.getTimeKbn() == GSConstSchedule.TIME_NOT_EXIST) {
                //時間指定無しデータを除外
                map.put(String.valueOf(schSid), schMdl);
            }

            //格納先インデックスがMAXの場合breakする
            if (endIndex == GSConstSchedule.DAILY_ROWS_COUNT) {
                break;
            }
        }
        //表示終了時刻までの空白を設定
        valueMdl = __getEndBlankSchedule(schMdl, dspDate, endIndex);
        if (valueMdl != null) {
            lineList.add(valueMdl);
            endIndex = endIndex + valueMdl.getCols();
        }

        lineMdl.setLineList(lineList);
        return lineMdl;
    }

    /**
     * <br>スケジュール情報が格納可能か判定する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param endIndex 出力済みポインタ
     * @return true:格納可能 false:格納不可
     */
    private boolean __isStorage(
            SimpleScheduleModel schMdl,
            String dspDate,
            int endIndex) {
        int index = 0;

        //表示開始インデックスを取得
        index = __getIndex(schMdl, dspDate);
        if (index >= endIndex) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>表示開始時刻とスケジュール情報からスケジュール開始時刻のインデックスを取得する
     * @param schMdl スケジュール情報
     * @param strDate 表示日付
     * @return int 開始時刻のインデックス
     */
    private int __getIndex(SimpleScheduleModel schMdl, String strDate) {
        int index = 1;
        UDate dspDate = new UDate();
        dspDate.setDate(strDate);
        dspDate.setHour(GSConstSchedule.DAY_START_HOUR);
        dspDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        dspDate.setSecond(GSConstSchedule.DAY_START_SECOND);

        UDate frDate = schMdl.getFromDate();
        if (dspDate.compareDateYMDHM(frDate) != UDate.LARGE) {
            index = 1;
        } else {
            int intHour = frDate.getIntHour();
            int min = frDate.getIntMinute();
            int ans1 = index + (intHour * 4);
            int ans2 = min / 15;
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>表示開始時刻とスケジュール情報からスケジュール終了時刻のインデックスを取得する
     * @param schMdl スケジュール情報
     * @param strDate 表示日付
     * @return int 開始時刻のインデックス
     */
    private int __getEndIndex(SimpleScheduleModel schMdl, String strDate) {
        int index = 1;
        UDate dspDate = new UDate();
        dspDate.setDate(strDate);
        dspDate.setHour(GSConstSchedule.DAY_END_HOUR);
        dspDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        dspDate.setSecond(GSConstSchedule.DAY_END_SECOND);

        UDate toDate = schMdl.getToDate();
        if (dspDate.compareDateYMDHM(toDate) == UDate.LARGE) {
            index = GSConstSchedule.DAILY_ROWS_COUNT;
        } else {
            int intHour = toDate.getIntHour();
            int min = toDate.getIntMinute();
            int ans1 = intHour * 4;
            int ans2 = min / 15;
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>スケジュール情報と出力済みポインタから空スケジュールを必要に応じて生成する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param endIndex 出力済みポインタ
     * @return Sch040DailyValueModel
     */
    private Sch040DailyValueModel __getBlankSchedule(
            SimpleScheduleModel schMdl,
            String dspDate,
            int endIndex) {
        Sch040DailyValueModel valueMdl = null;
        int index = __getIndex(schMdl, dspDate);
        int cols = index - endIndex;
        if (cols > 0) {
            //空スケジュールを生成する
            valueMdl = new Sch040DailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setScheduleMdl(null);
            valueMdl.setIndex(endIndex);

        }

        return valueMdl;
    }

    /**
     * <br>表示終了時刻と出力済みポインタから空スケジュールを必要に応じて生成する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param endIndex 出力済みポインタ
     * @return Sch040DailyValueModel
     */
    private Sch040DailyValueModel __getEndBlankSchedule(
            SimpleScheduleModel schMdl,
            String dspDate,
            int endIndex) {
        Sch040DailyValueModel valueMdl = null;
        int index = GSConstSchedule.DAILY_ROWS_COUNT + 1;
        int cols = index - endIndex;
        if (cols > 0) {
            //空スケジュールを生成する
            valueMdl = new Sch040DailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setScheduleMdl(null);
            valueMdl.setIndex(endIndex);

        }

        return valueMdl;
    }

    /**
     * <br>スケジュールの開始・終了からROWSを取得する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param endIndex 出力済みポインタ
     * @return スケジュールの横幅(cols)
     */
    private int __getRows(
            SimpleScheduleModel schMdl,
            String dspDate,
            int endIndex) {
        int cols = 0;
        int frIndex = __getIndex(schMdl, dspDate);
        int toIndex = __getEndIndex(schMdl, dspDate);
        cols = toIndex - frIndex + 1;

        return cols;
    }
}
