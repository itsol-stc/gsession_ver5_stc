package jp.groupsession.v2.sch;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.sch.sch010.Sch010DayOfModel;
import jp.groupsession.v2.sch.sch010.Sch010UsrModel;
import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;
import jp.groupsession.v2.sch.sch040.Sch040DailyLineModel;
import jp.groupsession.v2.sch.sch040.Sch040DailyValueModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>Tag that retrieves the specified property of the specified bean, converts
 * <br>it to a String representation (if necessary), and writes it to the current
 * <br>output stream, optionally filtering characters that are sensitive in HTML.
 * @author JTS
 * @version $Revision: 1.1 $ $Date: 2016/11/04 08:45:42 $
 */
public class DailyScheduleReadOnlyTag extends TagSupport {
    /** ロギングクラス */
    public static Log log__ = LogFactory.getLog(DailyScheduleReadOnlyTag.class);

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
     * 表示終了時刻
     */
    protected String to = null;

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
     * <br>上段のスケジュールかを設定します
     * <br>0:下段 1:上段
     * <br>上段の場合、全てが公開状態になります
     */
    protected String top = null;

    /**
     * @return mysch を戻します。
     */
    public String getTop() {
        return top;
    }

    /**
     * @param string 設定する top
     */
    public void setTop(String string) {
        this.top = string;
    }

    /**
     * <br>管理者権限有無を設定します
     */
    protected String admin = null;

    /**
     * @return admin を戻します。
     */
    public String getAdmin() {
        return admin;
    }

    /**
     * @param string 設定する admin。
     */
    public void setAdmin(String string) {
        this.admin = string;
    }

    /**
     * <br>１時間あたりの区切り数を設定します
     */
    protected String memCnt = null;
    /**
     * <p>memCnt を取得します。
     * @return memCnt
     */
    public String getMemCnt() {
        return memCnt;
    }

    /**
     * <p>memCnt をセットします。
     * @param string １時間あたりの区切り数
     */
    public void setMemCnt(String string) {
        this.memCnt = string;
    }

    /**
     * 在席管理が利用可能か状態を保持
     */
//    private int zaisekiUseOk__ = GSConstSchedule.PLUGIN_USE;
    /**
     * ショートメールが利用可能か状態を保持
     */
//    private static int smailUseOk__ = GSConstSchedule.PLUGIN_USE;

    // --------------------------------------------------------- Public Methods


    /**
     * <br>Process the start tag.
     * @return int
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
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
        if (value instanceof Sch010WeekOfModel) {
            //型が違う場合はエラー
            //            return (SKIP_BODY); // Nothing to output
        }
        Sch010WeekOfModel weekMdl = (Sch010WeekOfModel) value;
        //プラグイン利用状況
//        zaisekiUseOk__ = weekMdl.getZaisekiUseOk();
//        smailUseOk__ = weekMdl.getSmailUseOk();

        JspWriter writer = pageContext.getOut();
        //      //再起的にHTMLを吐き出す。
        try {

            __writeTag(writer, weekMdl, req);

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
     * @param req リクエスト
     * @throws Exception 出力エラー
     */
    private void __writeTag(JspWriter writer, Sch010WeekOfModel weekMdl, HttpServletRequest req)
        throws Exception {

        log__.debug("-- __writeTag START --");

        Sch010UsrModel usrMdl = weekMdl.getSch010UsrMdl();
        ArrayList < Sch010DayOfModel > dayList = weekMdl.getSch010SchList();
        Sch010DayOfModel dayMdl = dayList.get(0);
        int intFrom = GSConstSchedule.DAY_START_HOUR;
        int intTo = GSConstSchedule.DAY_END_HOUR;
        if (from != null) {
            intFrom = Integer.parseInt(from);
        }
        if (to != null) {
            intTo = Integer.parseInt(to);
        }
        int intTop = 0;
        if (top != null) {
            intTop = Integer.parseInt(top);
        }
        int intAdmin = 0;
        if (admin != null) {
            intAdmin = Integer.parseInt(admin);
        }
        ArrayList < SimpleScheduleModel > schList = dayMdl.getSchDataList();
        //スケジュール情報を画面表示用に格納します
        ArrayList < Sch040DailyLineModel > dailyList =
            __getDspScheduleList(schList, dayMdl.getSchDate(), intFrom, intTo);

        __writeHtmlString(
            writer, usrMdl, dayMdl, dailyList, intTop, intAdmin, req, weekMdl.getZaisekiUseOk());

        log__.debug("-- __writeTag END --");
    }

    /**
     * <br>Sch010DayOfModelからHTML文字列を取得します。
     * <br>１ユーザの日間スケジュール
     * @param writer ライター
     * @param usrMdl ユーザ情報
     * @param dayModel Sch010DayOfModel
     * @param dailyList 画面表示用スケジュール
     * @param topKbn 上段表示スケジュール区分
     * @param adminKbn 管理者区分 1:権限有り 2:権限無し
     * @param req リクエスト
     * @param zaisekiUseOk 在席管理が利用可能か状態を保持
     * @throws Exception IOエラー時にスロー
     */
    private static void __writeHtmlString(
        JspWriter writer,
        Sch010UsrModel usrMdl,
        Sch010DayOfModel dayModel,
        ArrayList < Sch040DailyLineModel > dailyList,
        int topKbn,
        int adminKbn,
        HttpServletRequest req,
        int zaisekiUseOk)
        throws Exception {
        GsMessage gsMsg = new GsMessage();
        //グループ
        String textGroup = gsMsg.getMessage(req, "cmn.group");
        //内容
        String textContent = gsMsg.getMessage(req, "cmn.content");

        int userKbn = dayModel.getUsrKbn();
        int zaisekiKbn = usrMdl.getZaisekiKbn();
        String zaisekiMsg = StringUtilHtml.transToHTmlPlusAmparsant(
                NullDefault.getString(usrMdl.getZaisekiMsg(), ""));
        int rowspan = dailyList.size();

        //ユーザ欄
        String userName = StringUtilHtml.transToHTmlPlusAmparsant(usrMdl.getUsrName());
        if (userKbn == GSConstSchedule.USER_KBN_GROUP) {

            // 新デザイン適用時、在籍管理が使用可能ならグループの表示位置調整
            String originalPadding = "";
            if (zaisekiUseOk == GSConstSchedule.PLUGIN_USE) {
                originalPadding = "cal_colHeader";
            } else {
                originalPadding = "bgC_tableCell";
            }
            writer.println("<td "
                    + " class=\"" + originalPadding + "\""
                    + " colspan=\"2\""
                    + " rowspan=\"" + rowspan + "\">");
            writer.println("  <span class=\"flo_l\">");
            writer.println("  <img class=\"btn_classicImg-display\""
                    + " src=\"../common/images/classic/icon_group.png\""
                    + " alt=\"" + textGroup + "\">");
            writer.println("  <img class=\"btn_originalImg-display\""
                    + " src=\"../common/images/original/icon_group.png\""
                    + " alt=\"" + textGroup + "\">");
            writer.println("  </span>");
            writer.println(" <span class=\"fs_14 flo_l\">" + userName + "</span><br>");

            writer.println("</td>");
        } else {
            String zaisekiCss = "";
            if (zaisekiUseOk == GSConstSchedule.PLUGIN_USE) {
                if (zaisekiKbn == GSConst.UIOSTS_IN) {
                    zaisekiCss = "cal_colHeader-zaiseki";
                } else if (zaisekiKbn == GSConst.UIOSTS_LEAVE) {
                    zaisekiCss = "cal_colHeader-huzai";
                } else if (zaisekiKbn == GSConst.UIOSTS_ETC) {
                    zaisekiCss = "cal_colHeader-sonota";
                }
            } else {
                zaisekiCss = "bgC_tableCell";
            }

            writer.println("<td class=\"wp130 "
                    + zaisekiCss + "\" colspan=\"2\" rowspan=\""
                    + rowspan + "\">");
            if (usrMdl.getSchUkoFlg() == 1) {
                writer.println("  <span class=\"mukoUser\">" + userName + "</span><br>");
            } else {
                writer.println("  <span>" + userName + "</span><br>");
            }
            writer.println("<span class=\"fs_13\">&nbsp;&nbsp;"
            + zaisekiMsg + "</span><br>");
            //
            writer.println("</td>");
        }

        //日間スケジュールのリスト
//        ArrayList < SimpleScheduleModel > schList = dayModel.getSchDataList();

//        //時間指定無しスケジュール出力
//        writer.println("<td class=\"bgC_other_white\" align=\"center\" valign=\"top\""
//        + " rowspan=\"" + rowspan + "\">");
//        for (int i = 0; i < schList.size(); i++) {
//            SimpleScheduleModel schMdl = schList.get(i);
//            if (schMdl.getTimeKbn() == GSConstSchedule.TIME_NOT_EXIST) {
//                // 時間指定なし
//                String title = schMdl.getTitle();
//                writer.println("<a href=\"#\" title=\"" + title + "\""
//                + " onClick=\"editSchedule('edit',"
//                                    + " " + dayModel.getSchDate() + ","
//                                    + " " + schMdl.getSchSid() + ","
//                                    + " " + dayModel.getUsrSid() + ","
//                                    + " " + dayModel.getUsrKbn() + ");\">");
//                writer.println("<span class=\"sc_link_g\">≫◆</span></a><br>");
//            }
//        }
//        writer.println("</td>");


        //タイムチャート部分出力
        Sch040DailyLineModel dailyLineMdl = null;
        ArrayList < Sch040DailyValueModel > valueList = null;
        Sch040DailyValueModel valueMdl = null;

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
                SimpleScheduleModel schMdl = valueMdl.getScheduleMdl();
                if (schMdl != null) {
                    String title = StringUtilHtml.transToHTmlPlusAmparsant(schMdl.getTitle());
                    String color = "bgC_select";
                    writer.println("<td colspan=\"" + cols + "\""
                    + " class=\"txt_c txt_m " + color + "\">");

                    //公開パターン
                    if (topKbn == 1 || schMdl.getPublic() == GSConstSchedule.DSP_PUBLIC) {

                        writer.println("<div>");
//                        //スケジュールAタグ
                        if (schMdl.getValueStr() != null
                                && schMdl.getValueStr().trim().length() > 0) {
                            //内容あり
                            writer.println("<span class=\"js_nlTooltips\">"
                                    + textContent + ":" + schMdl.getValueStr()
                                    + "</span>"
                                    );
                        }

                        switch (schMdl.getBgColor()) {
                        case 1:
                            writer.println("<span class=\"cl_fontSchTitleBlue fs_13\">");
                            break;
                        case 2:
                            writer.println("<span class=\"cl_fontSchTitleRed fs_13\">");
                            break;
                        case 3:
                            writer.println("<span class=\"cl_fontSchTitleGreen fs_13\">");
                            break;
                        case 4:
                            writer.println("<span class=\"cl_fontSchTitleYellow fs_13\">");
                            break;
                        case 5:
                            writer.println("<span class=\"cl_fontSchTitleBlack fs_13\">");
                            break;
                        case 6:
                            writer.println("<span class=\"cl_fontSchTitleNavy fs_13\">");
                            break;
                        case 7:
                            writer.println("<span class=\"cl_fontSchTitleWine fs_13\">");
                            break;
                        case 8:
                            writer.println("<span class=\"cl_fontSchTitleCien fs_13\">");
                            break;
                        case 9:
                            writer.println("<span class=\"cl_fontSchTitleGray fs_13\">");
                            break;
                        case 10:
                            writer.println("<span class=\"cl_fontSchTitleMarine fs_13\">");
                            break;
                        default:
                            writer.println("<span class=\"cl_linkDef\">");
                            break;
                        }

                        if (!StringUtil.isNullZeroStringSpace(schMdl.getTime())) {
                            writer.println("<span class=\"cal_time txt_c\">");
                            writer.println(schMdl.getTime());
                            writer.println("</span>");
                            writer.println("<br>");
                        }
                        if (schMdl.isPublicIconFlg()) {
                            writer.println(
                                    "<img class=\"btn_classicImg-display\""
                                    + "src=\"../common/images/classic/icon_lock_13.png\""
                                    + "alt=\"" + title + "\"/>");
                            writer.println(
                                    "<img class=\"btn_originalImg-display\""
                                    + "src=\"../common/images/original/icon_lock_13.png\""
                                    + "alt=\"" + title + "\"/>");
                        }
                        writer.println("" + title + "");
                        writer.println("</div>");

                    } else {
                        //非公開パターン
                        writer.println("<span class=\"fs_13\">");
                        if (!StringUtil.isNullZeroStringSpace(schMdl.getTime())) {
                            writer.println("<span class=\"cal_time txt_c\">");
                            writer.println(schMdl.getTime());
                            writer.println("</span>");
                            writer.println("<br>");
                        }
                        if (schMdl.isPublicIconFlg()) {
                            writer.println(
                                    "<img class=\"btn_classicImg-display\""
                                    + "src=\"../common/images/classic/icon_lock_13.png\""
                                    + "alt=\"" + title + "\"/>");
                            writer.println(
                                    "<img class=\"btn_originalImg-display\""
                                    + "src=\"../common/images/original/icon_lock_13.png\""
                                    + "alt=\"" + title + "\"/>");
                        }
                        writer.println("" + title + "");
                    }

                    writer.println("</td>");
                } else {
                    writer.println("<td colspan=\"" + cols + "\" class=\"bgC_tableCell\">");
                    writer.println("&nbsp;");
                    writer.println("</td>");
                }

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
     * <br>スケジュール情報を画面表示用に格納します
     * @param schList スケジュールリスト
     * @param dspDate 表示日付
     * @param frHour 表示開始時間
     * @param toHour 表示終了時間
     * @return ArrayList < Sch040DailyLineModel >
     */
    private ArrayList<Sch040DailyLineModel> __getDspScheduleList(
            ArrayList<SimpleScheduleModel> schList,
            String dspDate,
            int frHour,
            int toHour
            ) {

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
            valueMdl.setCols(__getColsTotal(frHour, toHour));
//            valueMdl.setCols(GSConstSchedule.DAILY_COLS_COUNT);
            lineList.add(valueMdl);
            lineMdl.setLineList(lineList);
            dailyList.add(lineMdl);
            return dailyList;
        }

        //出力済情報の格納用
        HashMap<Integer, SimpleScheduleModel> map
        = new HashMap<Integer, SimpleScheduleModel>();

        while (schList.size() != map.size()) {
            //１行分を作成する
            lineMdl = __getDailyLineMdl(schList, map, dspDate, frHour, toHour);
            dailyList.add(lineMdl);
         }

        return dailyList;
    }

    /**
     * 開始時間～終了時間のColsを取得します
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param frHour 開始時間(HH)
     * @param toHour 終了時間(HH)
     * @return int cols
     */
    private int __getColsTotal(int frHour, int toHour) {
        int ret = 0;
        int hour = toHour - frHour + 1;
        ret = hour * Integer.parseInt(memCnt);
        return ret;
    }
    /**
     * <br>１行分のスケジュールモデルを生成する
     * <br>[備考]出力済みのスケジュール情報はHashMapに格納します
     * @param schList スケジュール情報
     * @param map 出力済みのスケジュール情報
     * @param dspDate 表示日付
     * @param frHour 出力開始時刻
     * @param toHour 出力終了時刻
     * @return Sch040DailyLineModel
     */
    private Sch040DailyLineModel __getDailyLineMdl(
            ArrayList<SimpleScheduleModel> schList,
            HashMap<Integer, SimpleScheduleModel> map,
            String dspDate,
            int frHour,
            int toHour) {

        log__.debug("-- __getDailyLineMdl START --");
        Sch040DailyLineModel lineMdl = new Sch040DailyLineModel();
        ArrayList<Sch040DailyValueModel> lineList = new ArrayList<Sch040DailyValueModel>();
        Sch040DailyValueModel valueMdl = null;

        SimpleScheduleModel schMdl = null;
        int endIndex = 1;

        for (int i = 0; i < schList.size(); i++) {

            schMdl = schList.get(i);
            if (map.containsKey(i)) {
                //出力済みは除外
                continue;
            }

//            if (schMdl.getTimeKbn() == GSConstSchedule.TIME_EXIST) {

                //格納可能か判定し可能であれば格納する
                if (__isStorage(schMdl, dspDate, frHour, endIndex)) {

                    //空白部分を設定
                    valueMdl = __getBlankSchedule(schMdl, dspDate, frHour, endIndex);
                    if (valueMdl != null) {

                        lineList.add(valueMdl);
                        endIndex = endIndex + valueMdl.getCols();

                    }
                    //スケジュール部分を設定
                    int cols = __getCols(schMdl, dspDate, frHour, toHour, endIndex);
                    valueMdl = new Sch040DailyValueModel();
                    valueMdl.setCols(cols);
                    valueMdl.setScheduleMdl(schMdl);
                    lineList.add(valueMdl);
                    endIndex = endIndex + cols;
                    map.put(i, schMdl);

                }
//            } else if (schMdl.getTimeKbn() == GSConstSchedule.TIME_NOT_EXIST) {
//                //時間指定無しデータを除外
//                map.put(String.valueOf(schSid), schMdl);
//            }

            //格納先インデックスがMAXの場合breakする
//            if (endIndex == GSConstSchedule.DAILY_COLS_COUNT) {
            if (endIndex == __getColsTotal(frHour, toHour)) {
                break;
            }
        }

        //表示終了時刻までの空白を設定
        valueMdl = __getEndBlankSchedule(schMdl, dspDate, frHour, toHour, endIndex);
        if (valueMdl != null) {
            lineList.add(valueMdl);
            endIndex = endIndex + valueMdl.getCols();
        }


        lineMdl.setLineList(lineList);
        log__.debug("-- __getDailyLineMdl END --");
        return lineMdl;
    }

    /**
     * <br>スケジュール情報が格納可能か判定する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param frHour 画面表示開始時刻
     * @param endIndex 出力済みポインタ
     * @return true:格納可能 false:格納不可
     */
    private boolean __isStorage(
            SimpleScheduleModel schMdl,
            String dspDate,
            int frHour,
            int endIndex) {
        int index = 0;

        //表示開始インデックスを取得
        index = __getIndex(schMdl, dspDate, frHour);
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
     * @param dspHour 表示開始時刻
     * @return int 開始時刻のインデックス
     */
    private int __getIndex(SimpleScheduleModel schMdl, String strDate, int dspHour) {
        int index = 1;
        UDate dspDate = new UDate();
        dspDate.setDate(strDate);
        dspDate.setHour(dspHour);
        dspDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        dspDate.setSecond(GSConstSchedule.DAY_START_SECOND);

        UDate frDate = schMdl.getFromDate();
        if (dspDate.compareDateYMDHM(frDate) != UDate.LARGE) {
            index = 1;
        } else {
            int hour = frDate.getIntHour();
            int min = frDate.getIntMinute();
            int ans1 = index + ((hour - dspHour) * Integer.parseInt(memCnt));
            int hourMemCount = 60 / Integer.parseInt(memCnt);
            int ans2 = min / hourMemCount;
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>表示開始時刻とスケジュール情報からスケジュール終了時刻のインデックスを取得する
     * @param schMdl スケジュール情報
     * @param strDate 表示日付
     * @param dspHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @return int 開始時刻のインデックス
     */
    private int __getEndIndex(SimpleScheduleModel schMdl, String strDate, int dspHour, int toHour) {
        int index = 1;
        UDate dspDate = new UDate();
        dspDate.setDate(strDate);
        dspDate.setHour(toHour);
        dspDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        dspDate.setSecond(GSConstSchedule.DAY_END_SECOND);

        UDate toDate = schMdl.getToDate();

        if (dspDate.compareDateYMDHM(toDate) == UDate.LARGE
                || schMdl.getTimeKbn() == GSConstSchedule.TIME_NOT_EXIST) {
            index = __getColsTotal(dspHour, toHour);
        } else {
            int hour = toDate.getIntHour();
            int min = toDate.getIntMinute();
            int ans1 = (hour - dspHour) * Integer.parseInt(memCnt);
            int hourMemCount = 60 / Integer.parseInt(memCnt);
            int ans2 = min / hourMemCount;
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>スケジュール情報と出力済みポインタから空スケジュールを必要に応じて生成する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param frHour 表示開始時刻
     * @param endIndex 出力済みポインタ
     * @return Sch040DailyValueModel
     */
    private Sch040DailyValueModel __getBlankSchedule(
            SimpleScheduleModel schMdl,
            String dspDate,
            int frHour,
            int endIndex) {
        Sch040DailyValueModel valueMdl = null;
        int index = __getIndex(schMdl, dspDate, frHour);
        int cols = index - endIndex;
        if (cols > 0) {
            //空スケジュールを生成する
            valueMdl = new Sch040DailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setScheduleMdl(null);

        }

        return valueMdl;
    }

    /**
     * <br>表示終了時刻と出力済みポインタから空スケジュールを必要に応じて生成する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param endIndex 出力済みポインタ
     * @return Sch040DailyValueModel
     */
    private Sch040DailyValueModel __getEndBlankSchedule(
            SimpleScheduleModel schMdl,
            String dspDate,
            int frHour,
            int toHour,
            int endIndex) {

        Sch040DailyValueModel valueMdl = null;
        int index = __getColsTotal(frHour, toHour) + 1;
        int cols = index - endIndex;
        if (cols > 0) {
            //空スケジュールを生成する
            valueMdl = new Sch040DailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setScheduleMdl(null);

        }

        return valueMdl;
    }

    /**
     * <br>スケジュールの開始・終了からCOLSを取得する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param endIndex 出力済みポインタ
     * @return スケジュールの横幅(cols)
     */
    private int __getCols(
            SimpleScheduleModel schMdl,
            String dspDate,
            int frHour,
            int toHour,
            int endIndex) {
        int cols = 0;
        int frIndex = __getIndex(schMdl, dspDate, frHour);
        int toIndex = __getEndIndex(schMdl, dspDate, frHour, toHour);
        cols = toIndex - frIndex + 1;

        return cols;
    }
}
