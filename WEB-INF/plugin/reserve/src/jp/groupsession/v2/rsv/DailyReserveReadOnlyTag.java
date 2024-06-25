package jp.groupsession.v2.rsv;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>Tag that retrieves the specified property of the specified bean, converts
 * <br>it to a String representation (if necessary), and writes it to the current
 * <br>output stream, optionally filtering characters that are sensitive in HTML.
 * @author JTS
 * @version $Revision: 1.3 $ $Date: 2017/06/16 08:31:04 $
 */
public class DailyReserveReadOnlyTag extends TagSupport {

    /** ロギングクラス */
    public static Log log__ = LogFactory.getLog(DailyReserveReadOnlyTag.class);

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
     * 表示開始時刻
     */
    protected String from = null;

    /**
     * 表示終了時刻
     */
    protected String to = null;

    /**
     * 表示終了時刻
     */
    protected String dspDate = null;

    /**
     * 時間間隔
     */
    protected String count = "12";

    /**
     * 在籍管理使用区分
     */
    protected String zskKbn = String.valueOf(GSConstReserve.PLUGIN_USE);

    /**
     * @return from を戻します。
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param string 設定する from。
     */
    public void setFrom(String string) {
        this.from = string;
    }

    /**
     * @return to を戻します。
     */
    public String getTo() {
        return to;
    }

    /**
     * @param string 設定する to。
     */
    public void setTo(String string) {
        this.to = string;
    }

    /**
     * @return dspDate を戻します。
     */
    public String getDspDate() {
        return dspDate;
    }

    /**
     * @param string 設定する dspDate。
     */
    public void setDspDate(String string) {
        this.dspDate = string;
    }

    /**
     * <p>count を取得します。
     * @return count
     */
    public String getCount() {
        return count;
    }

    /**
     * <p>count をセットします。
     * @param c count
     */
    public void setCount(String c) {
        this.count = c;
    }

    /**
     * @return zskKbn を戻します。
     */
    public String getZskKbn() {
        return zskKbn;
    }

    /**
     * @param int 設定する zskKbn。
     */
    public void setZskKbn(String string) {
        this.zskKbn = string;
    }

    /**
     * <br>Process the start tag.
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
        log__.debug("<==value==>" + value.getClass());
        if (value instanceof RsvSisetuModel) {
            //型が違う場合はエラー
            //            return (SKIP_BODY); // Nothing to output
        }

        RsvSisetuModel sisetuMdl = (RsvSisetuModel) value;
        JspWriter writer = pageContext.getOut();
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        //再起的にHTMLを吐き出す。
        try {

            __writeTag(writer, sisetuMdl, req);

        } catch (Exception e) {
            throw new JspException("Jsp出力に失敗しました。", e);
        }

        // Continue processing this page
        return (SKIP_BODY);

    }

    /**
     * <br>HTMLをJspへ出力します。
     * @param writer JspWriter
     * @param sisetuMdl 施設情報
     * @param req HttpServletRequest
     * @throws Exception 出力エラー
     */
    private void __writeTag(JspWriter writer, RsvSisetuModel sisetuMdl, HttpServletRequest req)
        throws Exception {

        log__.debug("-- __writeTag START --");

        ArrayList<RsvWeekModel> weekList = sisetuMdl.getRsvWeekList();
        RsvWeekModel weekMdl = weekList.get(0);

        ArrayList<RsvYoyakuDayModel> dayList = weekMdl.getYoyakuDayList();
        RsvYoyakuDayModel yoyakuDayMdl = dayList.get(0);
        String key = yoyakuDayMdl.getIkkatuKey();
        boolean checkedFlg = yoyakuDayMdl.isCheckedFlg();
        ArrayList<RsvYoyakuModel> yoyakuList = yoyakuDayMdl.getYoyakuList();

        int intFrom = Integer.parseInt(from);
        int intTo = Integer.parseInt(to);
        int hourDivCnt = Integer.parseInt(count);

        //予約情報を画面表示用に格納します
//        ArrayList<RsvDailyLineModel> dailyList =
//            __getDspScheduleList(yoyakuList, dspDate, intFrom, intTo);
        ArrayList<RsvDailyLineModel> dailyList =
            __getDspScheduleList(yoyakuList, dspDate, intFrom, intTo, hourDivCnt);

        __writeHtmlString(writer, sisetuMdl, dailyList, key, checkedFlg, req);

        log__.debug("-- __writeTag END --");
    }

    /**
     * <br>HTML文字列を取得します。
     * <br>日間予約
     * @param writer ライター
     * @param sisetuMdl 施設情報
     * @param dailyList 画面表示用予約
     * @param key 一括登録キー
     * @param checkedFlg チェック済フラグ
     * @param req HttpServletRequest
     * @throws Exception IOエラー時にスロー
     */
    private void __writeHtmlString(
        JspWriter writer,
        RsvSisetuModel sisetuMdl,
        ArrayList<RsvDailyLineModel> dailyList,
        String key,
        boolean checkedFlg,
        HttpServletRequest req)
        throws Exception {

        int rowspan = dailyList.size();

        // 新デザイン適用時、在籍管理が使用可能ならグループの表示位置調整
        String originalPadding = "";
        if (zskKbn.equals(String.valueOf(GSConstReserve.PLUGIN_USE))) {
            originalPadding = "cal_colHeader";
        } else {
            originalPadding = "bgC_tableCell";
        }

        writer.println("<td id=\"" + key + "\""
                + " class=\"" + originalPadding + "\""
                + " colspan=\"2\" rowspan=\""
                + rowspan + "\">");

        writer.println("  <span>"
                + StringUtilHtml.transToHTmlPlusAmparsant(sisetuMdl.getRsdName())
                + "</span>");
        writer.println("</td>");

        //タイムチャート部分出力
        RsvDailyLineModel dailyLineMdl = null;
        ArrayList<RsvDailyValueModel> valueList = null;
        RsvDailyValueModel valueMdl = null;
        RsvCommonBiz rsvCmnBiz = new RsvCommonBiz();

        //内容
        GsMessage gsMsg = new GsMessage(req);
        String textContent = gsMsg.getMessage("cmn.content");

        //縦
        for (int i = 0; i < dailyList.size(); i++) {
            dailyLineMdl = dailyList.get(i);
            valueList = dailyLineMdl.getLineList();

            if (i > 0) {
                writer.println("</tr>");
                writer.println("<tr>");
            }
            //横
            for (int j = 0; j < valueList.size(); j++) {

                valueMdl = valueList.get(j);
                int cols = valueMdl.getCols();
                RsvYoyakuModel yrkMdl = valueMdl.getYoyakuMdl();

                if (yrkMdl != null) {

                    writer.println("<td colspan=\"" + cols + "\""
                        + " class=\"bgC_select txt_c txt_m \">");

                    //対象の施設予約情報が閲覧可能な場合、情報を表示
                    if (yrkMdl.getPublic() != GSConstReserve.PUBLIC_KBN_PLANS) {
                        String[] mokApprStatus = rsvCmnBiz.getMokApprStatus(gsMsg,
                                yrkMdl.getRsyApprStatus(), yrkMdl.getRsyApprKbn());
                        writer.println("<div class=\"" + mokApprStatus[1] + "\">");
                        if (StringUtil.isNullZeroString(yrkMdl.getRsyNaiyo())) {
                        } else {
                            writer.println("<span class=\"js_nlTooltips\">"
                                    + textContent + ":" + yrkMdl.getRsyNaiyo()
                                    + "</span>"
                                    );
                        }


                        if (!StringUtil.isNullZeroStringSpace(yrkMdl.getYrkRiyoDateStr())) {
                            writer.println("<span class=\"txt_c cal_time\">");
                            writer.println(yrkMdl.getYrkRiyoDateStr());
                            writer.println("</span>");
                            writer.println("<br>");

                        }

                        writer.println("<span class=\"" + mokApprStatus[1] + "\">");

                        if (!StringUtil.isNullZeroStringSpace(yrkMdl.getRsyMok())) {
                            writer.println(mokApprStatus[2]
                                         + StringUtilHtml.transToHTmlPlusAmparsant(
                                            yrkMdl.getRsyMok()));
                        }

                        if (!StringUtil.isNullZeroStringSpace(yrkMdl.getRsyMok())
                                && !StringUtil.isNullZeroStringSpace(yrkMdl.getYrkName())) {
                            writer.println("&nbsp;/&nbsp;");
                        }

                        if (!StringUtil.isNullZeroStringSpace(yrkMdl.getYrkName())) {
                            if (yrkMdl.getUsrJKbn() == GSConst.JTKBN_TOROKU) {
                                if (yrkMdl.getUsrUkoFlg() == 1) {
                                    writer.println("<span class=\"mukoUser\"/>");
                                    writer.println(StringUtilHtml.transToHTmlPlusAmparsant(
                                            yrkMdl.getYrkName()));
                                    writer.println("</span>");
                                } else {
                                    writer.println(StringUtilHtml.transToHTmlPlusAmparsant(
                                            yrkMdl.getYrkName()));
                                }
                            } else if (yrkMdl.getUsrJKbn() == GSConst.JTKBN_DELETE) {
                                writer.println("<del>");
                                writer.println(StringUtilHtml.transToHTmlPlusAmparsant(
                                        yrkMdl.getYrkName()));
                                writer.println("</del>");
                            }
                        }


                        writer.println("  </span>");
                        writer.println("</div>");
                    //対象の施設予約情報が閲覧できない場合、「予定あり」と表示
                    } else {
                        writer.println("<span class=\"fs_13\">");
                        if (!StringUtil.isNullZeroStringSpace(yrkMdl.getYrkRiyoDateStr())) {
                            writer.println("<span class=\"txt_c \">");
                            writer.println(yrkMdl.getYrkRiyoDateStr());
                            writer.println("</span>");
                            writer.println("<br>");
                        }
                        if (!StringUtil.isNullZeroStringSpace(yrkMdl.getRsyMok())) {
                            writer.println(
                                    StringUtilHtml.transToHTmlPlusAmparsant(yrkMdl.getRsyMok()));
                        }
                        if (!StringUtil.isNullZeroStringSpace(yrkMdl.getRsyMok())
                                && !StringUtil.isNullZeroStringSpace(yrkMdl.getYrkName())) {
                            writer.println("&nbsp;/&nbsp;");
                        }

                        if (!StringUtil.isNullZeroStringSpace(yrkMdl.getYrkName())) {
                            if (yrkMdl.getUsrJKbn() == GSConst.JTKBN_TOROKU) {
                                if (yrkMdl.getUsrUkoFlg() == 1) {
                                    writer.println("<span class=\"mukoUser\"/>");
                                    writer.println(StringUtilHtml.transToHTmlPlusAmparsant(
                                            yrkMdl.getYrkName()));
                                    writer.println("</span>");
                                } else {
                                    writer.println(StringUtilHtml.transToHTmlPlusAmparsant(
                                            yrkMdl.getYrkName()));
                                }
                            } else if (yrkMdl.getUsrJKbn() == GSConst.JTKBN_DELETE) {
                                writer.println("<del>");
                                writer.println(StringUtilHtml.transToHTmlPlusAmparsant(
                                        yrkMdl.getYrkName()));
                                writer.println("</del>");
                            }
                        }
                        writer.println("</span>");
                    }
                    writer.println("</td>");

                } else {
                    writer.println("<td colspan=\"" + cols + "\"");
                    writer.println(" class=\"bgC_tableCell\" >");
                    writer.println("&nbsp;");
                    writer.println("</td>");
                }
            }

            if (i > 0) {
            }

        }
    }

    /**
     * Release all allocated resources.
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
     * <br>予約情報を画面表示用に格納します
     * @param yoyakuList 予約リスト
     * @param dspDateStr 表示日付
     * @param frHour 表示開始時間
     * @param toHour 表示終了時間
     * @param hourDivCnt 時間間隔
     * @return ArrayList
     */
    private ArrayList<RsvDailyLineModel> __getDspScheduleList(
            ArrayList<RsvYoyakuModel> yoyakuList,
            String dspDateStr,
            int frHour,
            int toHour,
            int hourDivCnt) {

        ArrayList<RsvDailyLineModel> dailyList = new ArrayList<RsvDailyLineModel>();

//        int schSid = -1;
//        UDate svEndDate = null;
//        UDate frDate = null;

        //1行分の予約
        RsvDailyLineModel lineMdl = new RsvDailyLineModel();
        ArrayList<RsvDailyValueModel> lineList = new ArrayList<RsvDailyValueModel>();

        //1予約
        RsvDailyValueModel valueMdl = null;

        //予約情報無し
        if (yoyakuList.isEmpty()) {
            valueMdl = new RsvDailyValueModel();
            valueMdl.setYoyakuMdl(null);
//            int defCols = (toHour - frHour + 1) * GSConstReserve.HOUR_DIVISION_COUNT;
            int defCols = (toHour - frHour + 1) * hourDivCnt;
            valueMdl.setCols(defCols);
            lineList.add(valueMdl);
            lineMdl.setLineList(lineList);
            dailyList.add(lineMdl);
            return dailyList;
        }

        //出力済情報の格納用
        HashMap<String, RsvYoyakuModel> map =
            new HashMap<String, RsvYoyakuModel>();

        while (yoyakuList.size() != map.size()) {
            //1行分を作成する
            lineMdl = __getDailyLineMdl(yoyakuList, map, dspDateStr, frHour, toHour, hourDivCnt);
            dailyList.add(lineMdl);

            if (dailyList.size() >= yoyakuList.size()) {
                break;
            }
         }

        return dailyList;
    }

    /**
     * <br>１行分の予約モデルを生成する
     * <br>[備考]出力済みの予約情報はHashMapに格納します
     * @param yoyakuList 予約情報
     * @param map 出力済みの予約情報
     * @param dspDateStr 表示日付
     * @param frHour 出力開始時刻
     * @param toHour 出力終了時刻
     * @param hourDivCnt 時間分割数
     * @return RsvDailyLineModel
     */
    private RsvDailyLineModel __getDailyLineMdl(
            ArrayList<RsvYoyakuModel> yoyakuList,
            HashMap<String, RsvYoyakuModel> map,
            String dspDateStr,
            int frHour,
            int toHour,
            int hourDivCnt) {

        log__.debug("-- __getDailyLineMdl START --");
        RsvDailyLineModel lineMdl = new RsvDailyLineModel();
        ArrayList<RsvDailyValueModel> lineList = new ArrayList<RsvDailyValueModel>();
        RsvDailyValueModel valueMdl = null;

        RsvYoyakuModel yrkMdl = null;
        int rsySid = 0;
        int endIndex = 1;
//        int colsMax = (toHour - frHour + 1) * GSConstReserve.HOUR_DIVISION_COUNT;
        int colsMax = (toHour - frHour + 1) * hourDivCnt;

        for (int i = 0; i < yoyakuList.size(); i++) {

            yrkMdl = yoyakuList.get(i);
            rsySid = yrkMdl.getRsySid();

            if (map.containsKey(String.valueOf(rsySid))) {
                //出力済みは除外
                continue;
            }

            //格納可能か判定し可能であれば格納する
            if (__isStorage(yrkMdl, dspDateStr, frHour, endIndex, hourDivCnt)) {

                //空白部分を設定
                valueMdl = __getBlankSchedule(yrkMdl, dspDateStr, frHour, endIndex, hourDivCnt);
                if (valueMdl != null) {
                    lineList.add(valueMdl);
                    endIndex = endIndex + valueMdl.getCols();
                }
                //予約部分を設定
                int cols = __getCols(yrkMdl, dspDateStr, frHour, toHour, endIndex, hourDivCnt);
                valueMdl = new RsvDailyValueModel();
                valueMdl.setCols(cols);
                valueMdl.setYoyakuMdl(yrkMdl);
                lineList.add(valueMdl);
                endIndex = endIndex + cols;
                map.put(String.valueOf(rsySid), yrkMdl);
            }

            //格納先インデックスがMAXの場合breakする
            if (endIndex == colsMax) {
                break;
            }
        }

        //表示終了時刻までの空白を設定
        valueMdl = __getEndBlankSchedule(yrkMdl, dspDateStr, frHour, toHour, endIndex, hourDivCnt);
        if (valueMdl != null) {
            lineList.add(valueMdl);
            endIndex = endIndex + valueMdl.getCols();
        }

        lineMdl.setLineList(lineList);
        log__.debug("-- __getDailyLineMdl END --");
        return lineMdl;
    }

    /**
     * <br>予約情報が格納可能か判定する
     * @param yrkMdl 予約情報
     * @param dspDateStr 表示日付
     * @param frHour 画面表示開始時刻
     * @param endIndex 出力済みポインタ
     * @param hourDivCnt 時間分割
     * @return true:格納可能 false:格納不可
     */
    private boolean __isStorage(
            RsvYoyakuModel yrkMdl,
            String dspDateStr,
            int frHour,
            int endIndex,
            int hourDivCnt) {
        int index = 0;

        //表示開始インデックスを取得
        index = __getIndex(yrkMdl, dspDateStr, frHour, hourDivCnt);
        if (index >= endIndex) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>表示開始時刻と予約情報から予約開始時刻のインデックスを取得する
     * @param yrkMdl 予約情報
     * @param strDate 表示日付
     * @param dspHour 表示開始時刻
     * @param hourDivCnt 時間分割数
     * @return int 開始時刻のインデックス
     */
    private int __getIndex(RsvYoyakuModel yrkMdl, String strDate, int dspHour, int hourDivCnt) {
        int index = 1;
        UDate newDspDate = new UDate();
        newDspDate.setDate(strDate);
        newDspDate.setHour(dspHour);
        newDspDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        newDspDate.setSecond(GSConstReserve.DAY_START_SECOND);

        UDate frDate = yrkMdl.getRsyFrDate();
        if (newDspDate.compareDateYMDHM(frDate) != UDate.LARGE) {
            index = 1;
        } else {
            int hour = frDate.getIntHour();
            int min = frDate.getIntMinute();
//            int ans1 = index + ((hour - dspHour) * GSConstReserve.HOUR_DIVISION_COUNT);
//            int ans2 = min / GSConstReserve.HOUR_MEMORI_COUNT;
            int ans1 = index + ((hour - dspHour) * hourDivCnt);
            int hourMemoriCnt = 60 / hourDivCnt;
            int ans2 = min / hourMemoriCnt;
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>表示開始時刻と予約情報から予約終了時刻のインデックスを取得する
     * @param yrkMdl 予約情報
     * @param strDate 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param hourDivCnt 時間分割
     * @return int 開始時刻のインデックス
     */
    private int __getEndIndex(RsvYoyakuModel yrkMdl, String strDate,
                            int frHour, int toHour, int hourDivCnt) {
        int index = 1;
        UDate newDspDate = new UDate();
        newDspDate.setDate(strDate);
        newDspDate.setHour(toHour);
        newDspDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        newDspDate.setSecond(GSConstReserve.DAY_END_SECOND);

        UDate toDate = yrkMdl.getRsyToDate();
        if (newDspDate.compareDateYMDHM(toDate) == UDate.LARGE) {
//            index = (toHour - frHour + 1) * GSConstReserve.HOUR_DIVISION_COUNT;
            index = (toHour - frHour + 1) * hourDivCnt;
        } else {
            int hour = toDate.getIntHour();
            int min = toDate.getIntMinute();
//            int ans1 = (hour - frHour) * GSConstReserve.HOUR_DIVISION_COUNT;
//            int ans2 = min / GSConstReserve.HOUR_MEMORI_COUNT;
            int ans1 = (hour - frHour) * hourDivCnt;
            int hourMemoriCnt = 60 / hourDivCnt;
            int ans2 = min / hourMemoriCnt;
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>予約情報と出力済みポインタから空予約を必要に応じて生成する
     * @param yrkMdl 予約情報
     * @param dspDateStr 表示日付
     * @param frHour 表示開始時刻
     * @param endIndex 出力済みポインタ
     * @param hourDivCnt 時間間隔
     * @return Sch040DailyValueModel
     */
    private RsvDailyValueModel __getBlankSchedule(
            RsvYoyakuModel yrkMdl,
            String dspDateStr,
            int frHour,
            int endIndex,
            int hourDivCnt) {

        RsvDailyValueModel valueMdl = null;
        int index = __getIndex(yrkMdl, dspDateStr, frHour, hourDivCnt);
        int cols = index - endIndex;
        if (cols > 0) {
            //空予約を生成する
            valueMdl = new RsvDailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setYoyakuMdl(null);
        }

        return valueMdl;
    }

    /**
     * <br>表示終了時刻と出力済みポインタから空予約を必要に応じて生成する
     * @param yrkMdl 予約情報
     * @param dspDateStr 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param endIndex 出力済みポインタ
     * @param hourDivCnt 時間分割
     * @return Sch040DailyValueModel
     */
    private RsvDailyValueModel __getEndBlankSchedule(
            RsvYoyakuModel yrkMdl,
            String dspDateStr,
            int frHour,
            int toHour,
            int endIndex,
            int hourDivCnt) {

        RsvDailyValueModel valueMdl = null;

//        int defCols = (toHour - frHour + 1) * GSConstReserve.HOUR_DIVISION_COUNT;
        int defCols = (toHour - frHour + 1) * hourDivCnt;
        int cols = defCols - endIndex + 1;
        if (cols > 0) {
            //空予約を生成する
            valueMdl = new RsvDailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setYoyakuMdl(null);
        }

        return valueMdl;
    }

    /**
     * <br>予約の開始・終了からCOLSを取得する
     * @param yrkMdl 予約情報
     * @param dspDateStr 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param endIndex 出力済みポインタ
     * @param hourDivCnt 時間分割
     * @return 予約の横幅(cols)
     */
    private int __getCols(
            RsvYoyakuModel yrkMdl,
            String dspDateStr,
            int frHour,
            int toHour,
            int endIndex,
            int hourDivCnt) {

        int cols = 0;
        int frIndex = __getIndex(yrkMdl, dspDateStr, frHour, hourDivCnt);
        int toIndex = __getEndIndex(yrkMdl, dspDateStr, frHour, toHour, hourDivCnt);
        cols = toIndex - frIndex + 1;
        return cols;
    }
}