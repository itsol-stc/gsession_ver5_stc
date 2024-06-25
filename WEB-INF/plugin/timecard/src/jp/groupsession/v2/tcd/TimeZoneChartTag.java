package jp.groupsession.v2.tcd;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.model.TcdTimezoneChartModel;
import jp.groupsession.v2.tcd.model.TcdTimezoneMeiModel;

/**
 * <p>タイムカード時間帯設定部分のタイムチャートを生成する
 * Tag that retrieves the specified property of the specified bean, converts
 * it to a String representation (if necessary), and writes it to the current
 * output stream, optionally filtering characters that are sensitive in HTML.
 *
 * @author JTS
 * @version $Revision: 1.1 $ $Date: 2016/11/04 08:45:47 $
 */
public class TimeZoneChartTag extends TagSupport {
    /** ロギングクラス */
    public static Log log__ = LogFactory.getLog(TimeZoneChartTag.class);

    /** リクエスト */
    public static HttpServletRequest req__ = null;

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
        req__ = (HttpServletRequest) pageContext.getRequest();

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

        if (value instanceof TcdTimezoneChartModel) {
            //型が違う場合はエラー
            //            return (SKIP_BODY); // Nothing to output
        }
        TcdTimezoneChartModel zoneMdl = (TcdTimezoneChartModel) value;
        JspWriter writer = pageContext.getOut();
        //      //再起的にHTMLを吐き出す。
        try {

            __writeTag(writer, zoneMdl);

        } catch (Exception e) {
            throw new JspException("Jsp出力に失敗しました。", e);
        }

        // Continue processing this page
        return (SKIP_BODY);

    }

    /**
     * <br>HTMLをJspへ出力します。
     * @param writer JspWriter
     * @param zoneMdl 時間帯設定+タイムカード基本設定情報
     * @throws Exception 出力エラー
     */
    private void __writeTag(JspWriter writer, TcdTimezoneChartModel zoneMdl)
        throws Exception {

        ArrayList <TcdTimezoneMeiModel> chList = zoneMdl.getChartList();
        TcdAdmConfModel admConf = zoneMdl.getTcAdmConf();
        //時間帯設定情報を画面表示用に格納します
        ArrayList <TcdTimezoneMeiModel> dspList = __getDspTimeZoneList(chList, admConf);
        //Htmlタグ出力
        __writeHtmlString(writer, admConf, dspList);
    }

    /**
     * <br>TcdTimezoneMeiModelからHTML文字列を取得します。
     * <br>１ユーザの日間タイムカード情報
     * @param writer ライター
     * @param admConf 基本設定情報
     * @param valueList 画面表示用時間帯設定
     * @throws Exception IOエラー時にスロー
     */
    private static void __writeHtmlString(
        JspWriter writer,
        TcdAdmConfModel admConf,
        ArrayList <TcdTimezoneMeiModel> valueList)
        throws Exception {

        TcdTimezoneMeiModel valueMdl = null;

        GsMessage gsMsg = new GsMessage();
        String timeBelt = gsMsg.getMessage(req__, "tcd.tcd070.01");

        //タイムチャート部分出力
        writer.println("  <table class=\"table-left w80\">");
        writer.println("  <tr>");
        writer.println("  <td class=\"bgC_lightGray\" scope=\"col\">&nbsp;</td>");
        writer.println("  <td class=\"bgC_lightGray wp10\" scope=\"col\"></td>");
        writer.println("  <td class=\"bgC_lightGray w100 txt_c fw_b\" scope=\"col\">"
                          + timeBelt);
        writer.println("  </td>");
        writer.println("  </tr>");
        //0時～23時ループ
        int dspHour = 0;
        int counter = __getHourRowsCount(admConf.getTacInterval());
        int hourRows = __getHourRowsCount(admConf.getTacInterval());
        int maxRows = __getMaxRowsCount(admConf.getTacInterval());

        for (int i = 0; i < maxRows; i++) {

            if (counter == hourRows) {
                writer.println("<tr>");
                writer.println("<td class=\"wp30 bgC_lightGray txt_c fw_b\""
                        + " rowspan=\"" + hourRows + "\" nowrap>");

                writer.println("" + dspHour + "</td>");
                writer.println("<td class=\"wp10 bgC_lightGray\"></td>");

                dspHour++;
                counter = 1;
            } else {
                writer.println("<tr class=\"txt_c\">");
                writer.println("<td class=\"wp10 bgC_lightGray txt_c\"></td>");
                counter++;
            }
            //挿入する時間帯を検索
            valueMdl = __getTargetModel(valueList, i + 1);
            if (valueMdl != null) {
                //時間帯情報セット

                if (valueMdl.getTimeZoneSID() != GSConstTimecard.BLANK_ZONE_SID) {
                    String time = valueMdl.getTimeZoneStr();
                    String title = __getTitleString(
                            Integer.parseInt(valueMdl.getTimeZoneKbn()));
                    String color = __getStyleBgColor(Integer.parseInt(valueMdl.getTimeZoneKbn()));
                    writer.println("<td class=\"" + color + " txt_c\" "
                            + " title=\"" + time + " " + title + "\""
                            + " rowspan=\"" + valueMdl.getRows() + "\">");

                    if (valueMdl.getRows() > GSConstTimecard.MAIN_DSP_HOURS) {
                        writer.println("" + time + "<br>");
                        writer.println("" + title + "");
                    }

                } else {
                    writer.println("<td class=\"bgC_other_white\"");
                    writer.println("rowspan=\"" + valueMdl.getRows() + "\" >");
                }
                writer.println("</td>");

            }
            writer.println("</tr>");
        }
        writer.println("</table>");

    }

    /**
     * <br>時間帯リストに表示先インデックスに該当するタイムカード情報が有る場合、
     * <br>時間帯情報(TcdTimezoneMeiModel)を返します。
     * <br>該当情報が無い場合はNULLを返します
     *
     * @param valueList タイムカード情報リスト
     * @param index 表示先インデックス
     * @return TcdTimezoneMeiModel
     */
    private static TcdTimezoneMeiModel __getTargetModel(
            ArrayList <TcdTimezoneMeiModel> valueList,
            int index) {
        TcdTimezoneMeiModel valueMdl = null;
        for (int i = 0; i < valueList.size(); i++) {
            valueMdl = valueList.get(i);
            int dataIndex = valueMdl.getIndex();
            //判定
            if (index == dataIndex) {
                return valueMdl;
            }
        }
        return null;
    }

    /**
     * <br>時間帯区分からタイトルを取得する
     * @param kbn 時間帯区分
     * @return String タイトル文字列
     */
    private static String __getTitleString(int kbn) {
        GsMessage gsMsg = new GsMessage();
        String msg = "";

        String ret = "";
        switch (kbn) {
        case GSConstTimecard.TIMEZONE_KBN_NORMAL:
            msg = gsMsg.getMessage(req__, "tcd.103");
            ret = msg;
            break;
        case GSConstTimecard.TIMEZONE_KBN_ZANGYO:
            msg = gsMsg.getMessage(req__, "tcd.tcd010.09");
            ret = msg;
            break;
        case GSConstTimecard.TIMEZONE_KBN_SINYA:
            msg = gsMsg.getMessage(req__, "tcd.tcd010.06");
            ret = msg;
            break;
        case GSConstTimecard.TIMEZONE_KBN_KYUKEI:
            msg = gsMsg.getMessage(req__, "tcd.100");
            ret = msg;
            break;
        default:
            msg = gsMsg.getMessage(req__, "tcd.103");
            ret = msg;
            break;
        }
        return ret;
    }
    /**
     * <br>時間帯区分から対応するCSSクラスの文字列を取得します
     * @param kbn 時間帯区分
     * @return String CSSクラスの文字列
     */
    private static String __getStyleBgColor(int kbn) {
        StringBuilder buf = new StringBuilder();
        switch (kbn) {
        case GSConstTimecard.TIMEZONE_KBN_NORMAL:
            buf.append("bgc_timecard-normal");
            break;
        case GSConstTimecard.TIMEZONE_KBN_ZANGYO:
            buf.append("bgc_timecard-zangyo");
            break;
        case GSConstTimecard.TIMEZONE_KBN_SINYA:
            buf.append("bgc_timecard-sinya");
            break;
        case GSConstTimecard.TIMEZONE_KBN_KYUKEI:
            buf.append("bgc_timecard-kyuukei borderBlock-pink");
            break;
        default:
            buf.append("bgc_timecard-normal");
            break;
        }

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
     * <br>時間帯情報を画面表示用に格納します
     * @param chList チャートモデルリスト
     * @param admConf タイムカード基本設定モデル
     * @return ArrayList < TcdTimezoneMeiModel >
     */
    private ArrayList<TcdTimezoneMeiModel> __getDspTimeZoneList(
            ArrayList<TcdTimezoneMeiModel> chList,
            TcdAdmConfModel admConf) {

        ArrayList<TcdTimezoneMeiModel> dspList = new ArrayList<TcdTimezoneMeiModel>();

        TcdTimezoneMeiModel valueMdl = null;
        //時間帯情報無し
        log__.debug("時間帯情報件数==>" + chList.size());
        if (chList.size() == 0) {
            valueMdl = new TcdTimezoneMeiModel();
            valueMdl.setTimeZoneSID(GSConstTimecard.BLANK_ZONE_SID);
            valueMdl.setRows(__getMaxRowsCount(admConf.getTacInterval()));
            valueMdl.setIndex(1);
            chList.add(valueMdl);
            return chList;
        }

        //出力済情報の格納用
        HashMap<String, TcdTimezoneMeiModel> map
        = new HashMap<String, TcdTimezoneMeiModel>();

        while (chList.size() != map.size()) {
            //１行分を作成する
            dspList = __getDailyLineMdl(chList, map, admConf, dspList);
         }

        return dspList;
    }

    /**
     * タイムチャートの最大行数を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param between 間隔
     * @return int 最大数
     */
    private static int __getMaxRowsCount(int between) {
        int ret = 0;
        switch (between) {
        case 1:
            ret = GSConstTimecard.ZONE_ROWS_COUNT_SIX;
            break;
        case 10:
            ret = GSConstTimecard.ZONE_ROWS_COUNT_SIX;
            break;
        case 15:
            ret = GSConstTimecard.ZONE_ROWS_COUNT_FORU;
            break;
        case 30:
            ret = GSConstTimecard.ZONE_ROWS_COUNT_FORU;
            break;
        case 60:
            ret = GSConstTimecard.ZONE_ROWS_COUNT_FORU;
            break;
        default:
            ret = GSConstTimecard.ZONE_ROWS_COUNT_SIX;
            break;
        }
        return ret;
    }
    /**
     * タイムチャートの１時間あたりの行数を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param between 間隔
     * @return int 最大数
     */
    private static int __getHourRowsCount(int between) {
        int ret = 0;
        switch (between) {
        case 1:
            ret = GSConstTimecard.HOUR_ROWS_COUNT_SIX;
            break;
        case 10:
            ret = GSConstTimecard.HOUR_ROWS_COUNT_SIX;
            break;
        case 15:
            ret = GSConstTimecard.HOUR_ROWS_COUNT_FORU;
            break;
        case 30:
            ret = GSConstTimecard.HOUR_ROWS_COUNT_FORU;
            break;
        case 60:
            ret = GSConstTimecard.HOUR_ROWS_COUNT_FORU;
            break;
        default:
            ret = GSConstTimecard.HOUR_ROWS_COUNT_SIX;
            break;
        }
        return ret;
    }

    /**
     * タイムチャートの分単位に対する行数を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param bet 間隔
     * @param min 分
     * @return int 最大数
     */
    private static int __getMinRowsCount(int bet, int min) {
        int row = 0;
        if (min == 0) {
            return row;
        }
        Double minute = ((Integer) min).doubleValue();

        switch (bet) {
        case 1:
            row = GSConstTimecard.HOUR_ROWS_COUNT_SIX;
            break;
        case 10:
            row = GSConstTimecard.HOUR_ROWS_COUNT_SIX;
            break;
        case 15:
            row = GSConstTimecard.HOUR_ROWS_COUNT_FORU;
            break;
        case 30:
            row = GSConstTimecard.HOUR_ROWS_COUNT_FORU;
            break;
        case 60:
            row = GSConstTimecard.HOUR_ROWS_COUNT_FORU;
            break;
        default:
            row = GSConstTimecard.HOUR_ROWS_COUNT_SIX;
            break;
        }

//        Double rowCount = ((Integer) row).doubleValue();
//        Double ret = minute * rowCount / new Double("60");

        BigDecimal rowCount = new BigDecimal(row);
        rowCount = rowCount.multiply(new BigDecimal(minute));
        BigDecimal ret = rowCount.divide(new BigDecimal("60"), 1, RoundingMode.DOWN);

        int rowNum = ret.intValue();

        return rowNum;
    }

    /**
     * <br>１行分の時間帯モデルを生成する
     * <br>[備考]出力済みの時間帯情報はHashMapに格納します
     * @param tzhList 時間帯情報
     * @param map 出力済みの時間帯情報情報
     * @param admConf タイムカード基本設定
     * @param dspList 表示情報リスト
     * @return ArrayList
     */
    private ArrayList<TcdTimezoneMeiModel> __getDailyLineMdl(
            ArrayList<TcdTimezoneMeiModel> tzhList,
            HashMap<String, TcdTimezoneMeiModel> map,
            TcdAdmConfModel admConf,
            ArrayList<TcdTimezoneMeiModel> dspList
            ) {

        TcdTimezoneMeiModel valueMdl = null;
        TcdTimezoneMeiModel tzMdl = null;

        int tzSid = 0;
        int maxRows = __getMaxRowsCount(admConf.getTacInterval());
        int endIndex = 1;

        for (int i = 0; i < tzhList.size(); i++) {

            tzMdl = tzhList.get(i);
            tzSid = tzMdl.getTimeZoneSID();
            if (map.containsKey(String.valueOf(tzSid))) {
                //出力済みは除外
                continue;
            }

            //空白部分を設定
            valueMdl = __getBlankTimeZone(tzMdl, admConf, endIndex);
            if (valueMdl != null) {
                dspList.add(valueMdl);
                endIndex = endIndex + valueMdl.getRows();
                log__.debug("空白部分==>" + valueMdl.getRows());
            }
            //時間帯部分を設定
            int rows = __getRows(tzMdl, admConf, endIndex);
            log__.debug("時間帯部分row==>" + rows);
            valueMdl = new TcdTimezoneMeiModel();
            valueMdl.setRows(rows);
            valueMdl.setTimeZoneSID(tzMdl.getTimeZoneSID());
            valueMdl.setTimeZoneKbn(tzMdl.getTimeZoneKbn());
            valueMdl.setTimeZoneStr(tzMdl.getTimeZoneStr());
            valueMdl.setIndex(endIndex);
            dspList.add(valueMdl);
            endIndex = endIndex + rows;
            map.put(String.valueOf(tzSid), tzMdl);

            //格納先インデックスがMAXの場合breakする
            if (endIndex == maxRows) {
                break;
            }
        }
        //表示終了時刻までの空白を設定
        valueMdl = __getEndBlankTimeZone(tzMdl, admConf, endIndex);
        if (valueMdl != null) {
            dspList.add(valueMdl);
            endIndex = endIndex + valueMdl.getRows();
        }

        return dspList;
    }

    /**
     * <br>表示開始時刻と時間帯情報から時間帯開始時刻のインデックスを取得する
     * @param tzMdl 時間帯情報
     * @param admConf 基本設定情報
     * @return int 開始時刻のインデックス
     */
    private int __getIndex(TcdTimezoneMeiModel tzMdl, TcdAdmConfModel admConf) {
        int index = 1;
        UDate dspDate = new UDate();
        dspDate.setZeroHhMmSs();

        Time frTime = tzMdl.getFrTime();
        UDate frDate = dspDate.cloneUDate();
        frDate.setHour(TimecardUtil.getTimeHour(frTime));
        frDate.setMinute(TimecardUtil.getTimeMin(frTime));

        if (dspDate.compareDateYMDHM(frDate) != UDate.LARGE) {
            index = 1;
        } else {
            int intHour = frDate.getIntHour();
            int min = frDate.getIntMinute();
            int ans1 = index + (intHour * __getHourRowsCount(admConf.getTacInterval()));
            int ans2 = __getMinRowsCount(admConf.getTacInterval(), min);
            index = ans1 + ans2;
        }
        return index;
    }

    /**
     * <br>表示開始時刻と時間帯情報から時間帯終了時刻のインデックスを取得する
     * @param tzMdl 時間帯情報
     * @param admConf 基本設定
     * @return int 開始時刻のインデックス
     */
    private int __getEndIndex(TcdTimezoneMeiModel tzMdl, TcdAdmConfModel admConf) {
        int index = 1;
        UDate dspDate = new UDate();
        dspDate.setZeroHhMmSs();
        Time toTime = tzMdl.getToTime();
        UDate toDate = dspDate.cloneUDate();
        toDate.setHour(TimecardUtil.getTimeHour(toTime));
        toDate.setMinute(TimecardUtil.getTimeMin(toTime));

        if (dspDate.compareDateYMDHM(toDate) == UDate.EQUAL) {
            index = __getMaxRowsCount(admConf.getTacInterval());
        } else {
            int intHour = toDate.getIntHour();
            int min = toDate.getIntMinute();
            int ans1 = intHour * __getHourRowsCount(admConf.getTacInterval());
            int ans2 = __getMinRowsCount(admConf.getTacInterval(), min);
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>時間帯情報と出力済みポインタから空時間帯を必要に応じて生成する
     * @param tzMdl 時間帯情報
     * @param admConf 基本設定
     * @param endIndex 出力済みポインタ
     * @return TcdTimezoneMeiModel
     */
    private TcdTimezoneMeiModel __getBlankTimeZone(
            TcdTimezoneMeiModel tzMdl,
            TcdAdmConfModel admConf,
            int endIndex) {

        TcdTimezoneMeiModel valueMdl = null;
        int index = __getIndex(tzMdl, admConf);
        int rows = index - endIndex;
        log__.debug("__getBlankTimeZone.index.endIndex.rows==>"
                + index + "." + endIndex + "." + rows);

        if (rows > 0) {
            //空時間帯を生成する
            valueMdl = new TcdTimezoneMeiModel();
            valueMdl.setRows(rows);
            valueMdl.setTimeZoneSID(GSConstTimecard.BLANK_ZONE_SID);
            valueMdl.setIndex(endIndex);
        }
        return valueMdl;
    }

    /**
     * <br>表示終了時刻と出力済みポインタから空時間帯を必要に応じて生成する
     * @param schMdl 時間帯情報
     * @param admConf タイムカード基本設定
     * @param endIndex 出力済みポインタ
     * @return TcdTimezoneMeiModel
     */
    private TcdTimezoneMeiModel __getEndBlankTimeZone(
            TcdTimezoneMeiModel schMdl,
            TcdAdmConfModel admConf,
            int endIndex) {
        TcdTimezoneMeiModel valueMdl = null;
        int index = __getMaxRowsCount(admConf.getTacInterval()) + 1;
        int rows = index - endIndex;
        if (rows > 0) {
            //空の時間帯を生成する
            valueMdl = new TcdTimezoneMeiModel();
            valueMdl.setRows(rows);
            valueMdl.setTimeZoneSID(GSConstTimecard.BLANK_ZONE_SID);
            valueMdl.setIndex(endIndex);
        }

        return valueMdl;
    }

    /**
     * <br>時間帯の開始・終了からROWSを取得する
     * @param tzMdl 時間帯情報
     * @param admConf 基本設定
     * @param endIndex 出力済みポインタ
     * @return 時間帯の縦幅(rows)
     */
    private int __getRows(
            TcdTimezoneMeiModel tzMdl,
            TcdAdmConfModel admConf,
            int endIndex) {
        int rows = 0;
        int frIndex = __getIndex(tzMdl, admConf);
        int toIndex = __getEndIndex(tzMdl, admConf);
        rows = toIndex - frIndex + 1;
        log__.info("__getRows:frIndex.toIndex.rows==>" + frIndex + "." + toIndex + "." + rows);
        return rows;
    }
}
