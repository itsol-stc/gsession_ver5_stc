package jp.groupsession.v2.sch.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;
import jp.groupsession.v2.sch.sch020.Sch020DayOfModel;
import jp.groupsession.v2.sch.sch020.Sch020MonthOfModel;
import jp.groupsession.v2.sch.sch020.Sch020WeekOfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 月間スケジュールPdf出力に関するユーティリティクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchGekPdfUtil {

    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public SchGekPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public SchGekPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 月間スケジュールPdf出力に関するUtilクラスです。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl pdfモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream 勤務表データの出力先となるストリーム
     * @throws Exception 実行例外
     * @throws FileNotFoundException 実行例外
     * @throws DocumentException 実行例外
     * @author JTS
     */
    public void createSchGekkanPdf(
            SchGekPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        Document doc = null;

        /* フォント設定部 */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント(アカウント名)
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント(ヘッダー)
        Font font_header = PdfUtil.getFont10b(fontPath);
        //フォント(スケジュール内容 青)
        Font font_main_blue = PdfUtil.getFont10(fontPath);
        font_main_blue.setColor(PdfUtil.FONT_COLOR_BLUE);
        //フォント(スケジュール内容 赤)
        Font font_main_r = PdfUtil.getFont10(fontPath);
        font_main_r.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント(スケジュール内容 緑)
        Font font_main_g = PdfUtil.getFont10(fontPath);
        font_main_g.setColor(PdfUtil.FONT_COLOR_GREEN);
        //フォント(スケジュール内容 オレンジ)
        Font font_main_o = PdfUtil.getFont10(fontPath);
        font_main_o.setColor(PdfUtil.FONT_COLOR_ORANGE);
        //フォント(スケジュール内容 黒)
        Font font_main_black = PdfUtil.getFont10(fontPath);
        font_main_black.setColor(PdfUtil.FONT_COLOR_BLACK);
        //フォント(スケジュール内容 紺)
        Font font_main_navy = PdfUtil.getFont10(fontPath);
        font_main_navy.setColor(PdfUtil.FONT_COLOR_NAVY);
        //フォント(スケジュール内容 茶)
        Font font_main_maroon = PdfUtil.getFont10(fontPath);
        font_main_maroon.setColor(PdfUtil.FONT_COLOR_MAROON);
        //フォント(スケジュール内容 シアン)
        Font font_main_teal = PdfUtil.getFont10(fontPath);
        font_main_teal.setColor(PdfUtil.FONT_COLOR_CYAN);
        //フォント(スケジュール内容 グレー)
        Font font_main_gray = PdfUtil.getFont10(fontPath);
        font_main_gray.setColor(PdfUtil.FONT_COLOR_GRAY);
        //フォント(スケジュール内容 水色)
        Font font_main_lblue = PdfUtil.getFont10(fontPath);
        font_main_lblue.setColor(PdfUtil.FONT_COLOR_AQUA);
        //フォント(スケジュール内容 予定有)
        Font font_main_yotei = PdfUtil.getFont10(fontPath);
        font_main_yotei.setColor(PdfUtil.FONT_COLOR_BLACK);

        //フォント(改行)
        Font font_empty = PdfUtil.getFont10(fontPath);
        //フォント(氏名、日付)当月
        Font font_date_b = PdfUtil.getFont10b(fontPath);
        //フォント(六曜)
        Font font_rokuyou = PdfUtil.getFont9(fontPath);
        //フォント 土曜 当月 10pt太
        Font font_sat_b = PdfUtil.getFont10b(fontPath);
        font_sat_b.setColor(PdfUtil.FONT_COLOR_BLUE);
        //フォント 日曜 当月 10pt太
        Font font_sun_b = PdfUtil.getFont10b(fontPath);
        font_sun_b.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント(氏名、日付)当月以外
        Font font_date = PdfUtil.getFont10(fontPath);
        //フォント 日曜 当月以外 10pt
        Font font_sun = PdfUtil.getFont10(fontPath);
        font_sun.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント 祝日 10pt
        Font font_syuku = PdfUtil.getFont10(fontPath);
        font_syuku.setColor(PdfUtil.FONT_COLOR_RED);

        //バックカラー（土曜日）
        Color color_saturday = PdfUtil.BG_COLOR_LIGHTBLUE;
        //バックカラー（日曜日）
        Color color_sunday = PdfUtil.BG_COLOR_LIGHTRED;
        //バックカラー（期間）
        Color color_kikan = PdfUtil.BG_COLOR_LIGHTGREEN;
        //バックカラー（ヘッダ）
        Color color_header = PdfUtil.BG_COLOR_DARKBLUE;
        //バックカラー（日にち＆曜日）
        Color color_date = PdfUtil.BG_COLOR_LIGHTGRAY;

        PdfWriter pdfwriter = null;
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            doc = new Document(PageSize.A4.rotate()); //(595F,842F)
            //アウトプットストリームをPDFWriterに設定します。
            ByteArrayOutputStream byteout = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, byteout);

            GsMessage gsMsg = new GsMessage(reqMdl__);
            String smail = gsMsg.getMessage("sml.167");
            //出力するPDFに説明を付与します。
            doc.addAuthor("GroupSession");
            doc.addSubject(smail);

            doc.open();

            //文字入力範囲（横）
            float totalWidth = 920;

            //タイトル
            PdfPTable table_title = new PdfPTable(2);
            table_title.setWidthPercentage(100f);
            table_title.setTotalWidth(totalWidth);
            float [] width_title  = {0.3f, 0.7f};
            table_title.setWidths(width_title);
            PdfPCell cell_title;


            //スケジュール
            PdfPTable table_main = new PdfPTable(7);
            table_main.setWidthPercentage(100f);
            table_main.setTotalWidth(totalWidth);
            float [] width_main  = {0.142f, 0.142f, 0.142f, 0.142f, 0.142f, 0.142f, 0.142f};
            table_main.setWidths(width_main);
            PdfPCell cell_main;

            //スケジュール表示モード
            cell_title = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            gsMsg.getMessage("schedule.108")
                            + "["
                            + gsMsg.getMessage("cmn.monthly")
                            + "]", EMP__), font_title));
            settingWidth(cell_title, 0, 0, 0, 1);
            cell_title.setLeading(1.2f, 1.2f); //行間の設定
            cell_title.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
            cell_title.setPaddingBottom(5f);
            table_title.addCell(cell_title);

            cell_title = new PdfPCell(new Phrase(EMP__, font_title));
            settingWidth(cell_title, 0, 0, 0, 0);
            cell_title.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_title.addCell(cell_title);

            //空白
            cell_title = new PdfPCell(new Phrase(EMP__, font_empty));
            settingWidth(cell_title, 0, 0, 0, 0);
            cell_title.setColspan(2);
            table_title.addCell(cell_title);

            //ヘッダー 年月
            cell_main = new PdfPCell(new Phrase(pdfMdl.getDspDate(), font_header));
            settingWidth(cell_main, 1, 1, 0, 0);
            cell_main.setColspan(1);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_main.addCell(cell_main);

            //表示グループ、氏名
            cell_main = new PdfPCell(new Phrase(
                    PdfUtil.replaseBackslashToYen(
                        gsMsg.getMessage("cmn.show.group")
                        + "：" + pdfMdl.getDspGroup()
                        + "   " + NullDefault.getString(pdfMdl.getDspUser(), EMP__)
                     ), font_header));
            settingWidth(cell_main, 1, 0, 1, 0);
            cell_main.setColspan(6);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_main.addCell(cell_main);

            //曜日
            int weekCnt = 0;
            for (LabelValueBean bean : pdfMdl.getWeekList()) {
                Font font = null;
                if (Integer.valueOf(bean.getValue()) == 1) {
                    font = font_sun_b;
                } else if (Integer.valueOf(bean.getValue()) == 7) {
                    font = font_sat_b;
                } else {
                    font = font_date_b;
                }
                cell_main = new PdfPCell(new Phrase(bean.getLabel(), font));
                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (weekCnt == 0) {
                    settingWidth(cell_main, 0.5f, 1f, 0.25f, 1);
                } else if (weekCnt == pdfMdl.getWeekList().size() - 1) {
                    settingWidth(cell_main, 0.5f, 0.25f, 1f, 1);
                } else {
                    settingWidth(cell_main, 0.5f, 0.25f, 0.25f, 1);
                }
                cell_main.setBackgroundColor(color_date);
                cell_main.setPaddingBottom(3f);
                table_main.addCell(cell_main);

                weekCnt++;
            }

            table_main.setHeaderRows(2);

            //表示する月数
            for (Sch020MonthOfModel mMdl : pdfMdl.getScheduleList()) {
                //通常スケジュールのカウンタ
                int count = 0;
                //週間スケジュールのカウンタ
                int rowCnt = 0;
                //表示する日数
                for (Sch020DayOfModel dMdl : mMdl.getSch020SchList()) {
                    //１DAYスケジュール
                    PdfPTable table_day = new PdfPTable(1);
                    float [] width_day  = {1};
                    float [] width_headDate  = {0.85f, 0.15f};
                    table_day.setWidths(width_day);
                    PdfPCell cell_day;

                    //日にち
                    Font fontDay;
                    if (dMdl.getHolidayKbn() == 1) {
                        if (dMdl.getThisMonthKbn() == 1) {
                            fontDay = font_sun_b;
                        } else {
                            fontDay = font_sun;
                        }
                    } else {
                        if (dMdl.getThisMonthKbn() == 1) {
                            fontDay = font_date_b;
                        } else {
                            fontDay = font_date;
                        }
                    }
                    if (StringUtil.isNullZeroString(dMdl.getSchRokuyou())) {
                        //日付のみ表示
                        cell_day = new PdfPCell(new Phrase(dMdl.getDspDay(), fontDay));
                    } else {
                        cell_day = new PdfPCell();
                        //日付 + 六曜を表示
                        PdfPTable table_headDate = new PdfPTable(2);
                        table_headDate.setWidthPercentage(100f);
                        table_headDate.setTotalWidth(totalWidth);
                        table_headDate.setWidths(width_headDate);
                        PdfPCell rokuyouCell = new PdfPCell(
                                new Phrase(dMdl.getSchRokuyou(), font_rokuyou));
                        rokuyouCell.setBorder(0);
                        rokuyouCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        PdfPCell dateCell = new PdfPCell(
                                new Phrase(dMdl.getDspDay(), fontDay));
                        dateCell.setBorder(0);
                        dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table_headDate.addCell(rokuyouCell);
                        table_headDate.addCell(dateCell);
                        cell_day.addElement(table_headDate);
                    }
                    cell_day.setBorderWidth(0);
                    cell_day.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell_day.setVerticalAlignment(Element.ALIGN_TOP);
                    table_day.addCell(cell_day);

                    //祝日名
                    cell_day = new PdfPCell(new Phrase(
                            NullDefault.getString(dMdl.getHolidayName(), EMP__), font_syuku));
                    cell_day.setBorderWidth(0);
                    cell_day.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table_day.addCell(cell_day);
                    //表示する一日のスケジュール
                    for (SimpleScheduleModel simMdl : dMdl.getSchDataList()) {
                        if (simMdl.getTimeKbn() != 1) {
                            int publicType = simMdl.getPublic();
                            int grpEdKbn = simMdl.getKjnEdKbn();
                            int kjnEdKbn = simMdl.getGrpEdKbn();

                            String sch = "";
                            boolean yoteiFlg = false;
                            if ((publicType == GSConstSchedule.DSP_PUBLIC
                                    || publicType == GSConstSchedule.DSP_BELONG_GROUP)
                                    || (kjnEdKbn == GSConstSchedule.EDIT_CONF_OWN
                                    || grpEdKbn == GSConstSchedule.EDIT_CONF_GROUP)) {

                                if (simMdl.getUserKbn() != null) {
                                    if (Integer.valueOf(simMdl.getUserKbn()) == 1) {
                                        sch += "[G]";
                                    }
                                }

                                if (simMdl.getUserName() != null) {
                                    sch = "[" + simMdl.getUserName() + "]\n";

                                }
                                if (!ValidateUtil.isEmpty(simMdl.getTime())) {
                                    sch += simMdl.getTime() + "\n";
                                }

                                if (!ValidateUtil.isEmpty(simMdl.getTitle())) {
                                    sch += NullDefault.getString(simMdl.getTitle(), EMP__);
                                }
                            } else {
                                if (simMdl.getUserKbn() != null) {
                                    if (Integer.valueOf(simMdl.getUserKbn()) == 1) {
                                        sch += "[G]";
                                    }
                                }

                                if (simMdl.getUserName() != null) {
                                    sch = "[" + simMdl.getUserName() + "]\n";
                                }

                                if (!ValidateUtil.isEmpty(simMdl.getTime())) {
                                    sch += simMdl.getTime() + "\n";
                                }
                                if (!ValidateUtil.isEmpty(simMdl.getTitle())) {
                                    sch += NullDefault.getString(simMdl.getTitle(), EMP__);
                                }

                                if (publicType == GSConstSchedule.DSP_YOTEIARI) {
                                    yoteiFlg = true;
                                }
                            }
                            Font font_main = null;
                            if (yoteiFlg) {
                                font_main = font_main_yotei;
                            } else {
                                switch (simMdl.getBgColor()) {
                                    case  GSConstSchedule.BGCOLOR_BLUE :
                                        font_main = font_main_blue;
                                        break;
                                    case GSConstSchedule.BGCOLOR_RED :
                                        font_main = font_main_r;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GREEN :
                                        font_main = font_main_g;
                                        break;
                                    case GSConstSchedule.BGCOLOR_ORANGE :
                                        font_main = font_main_o;
                                        break;
                                    case GSConstSchedule.BGCOLOR_BLACK :
                                        font_main = font_main_black;
                                        break;
                                    case GSConstSchedule.BGCOLOR_NAVY :
                                        font_main = font_main_navy;
                                        break;
                                    case GSConstSchedule.BGCOLOR_MAROON :
                                        font_main = font_main_maroon;
                                        break;
                                    case GSConstSchedule.BGCOLOR_TEAL :
                                        font_main = font_main_teal;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GRAY :
                                        font_main = font_main_gray;
                                        break;
                                    case GSConstSchedule.BGCOLOR_LBLUE :
                                        font_main = font_main_lblue;
                                        break;
                                    default:
                                        font_main = font_main_blue;
                                        break;
                                }
                            }

                            if (!ValidateUtil.isEmpty(sch)) {
                                sch = PdfUtil.replaseBackslashToYen(sch);
                                cell_day = new PdfPCell(new Phrase(sch, font_main));
                                cell_day.setHorizontalAlignment(Element.ALIGN_LEFT);
                                cell_day.setBorderWidth(0);
                                table_day.addCell(cell_day);
                            }
                        }
                    }
                    cell_main = new PdfPCell(table_day);
                    if (dMdl.getWeekKbn() == 1) {
                        cell_main.setBackgroundColor(color_sunday);
                    } else if (dMdl.getWeekKbn() == 7) {
                        cell_main.setBackgroundColor(color_saturday);
                    }
                    if (mMdl.getSch020PeriodSchList() != null) {
                        if (count == 0) {
                            settingWidth(cell_main, 0, 1f, 0.25f, 0.5f);
                        } else if (count == 6) {
                            settingWidth(cell_main, 0, 0.25f, 1f, 0.5f);
                        } else {
                            settingWidth(cell_main, 0, 0.25f, 0.25f, 0.5f);
                        }
                    } else {
                        if (count == 0) {
                            settingWidth(cell_main, 0, 1f, 0.25f, 1);
                        } else if (count == 6) {
                            settingWidth(cell_main, 0, 0.25f, 1f, 1);
                        } else {
                            settingWidth(cell_main, 0, 0.25f, 0.25f, 1);
                        }
                    }

                    cell_main.setPaddingBottom(2f);
                    table_main.addCell(cell_main);

                    count++;

                    if (count >= 7) {
                        //期間スケジュール
                        if (mMdl.getSch020PeriodSchList() != null) {
                            //１週間ごとの期間スケジュール
                            int week_num = 0;
                            for (Sch020WeekOfModel wMdl : mMdl.getSch020PeriodSchList()) {
                                if (week_num == rowCnt) {
                                    if (wMdl.getSch020NoTimeSchList() != null) {
                                        //行数
                                        int row_num = 0;
                                        for (ArrayList<SimpleScheduleModel> simMdlList
                                                : wMdl.getSch020NoTimeSchList()) {
                                            //列数
                                            int col_num = 0;
                                            for (SimpleScheduleModel simpleMdl : simMdlList) {
                                                if (simpleMdl.getPeriodMdl() != null) {
                                                    int colspan
                                                    = simpleMdl.getPeriodMdl().getSchPeriodCnt();
                                                    int p_pblicType = simpleMdl.getPublic();
                                                    int p_grpEdKbn = simpleMdl.getGrpEdKbn();
                                                    int p_kjnEdKbn = simpleMdl.getKjnEdKbn();
                                                    String sch = "";
                                                    if ((p_pblicType == GSConstSchedule.DSP_PUBLIC
                                                    || p_pblicType
                                                        == GSConstSchedule.DSP_BELONG_GROUP)
                                                    || (p_kjnEdKbn == GSConstSchedule.EDIT_CONF_OWN
                                                    || p_grpEdKbn
                                                        == GSConstSchedule.EDIT_CONF_GROUP)) {

                                                        if (simpleMdl.getUserKbn() != null) {

                                                            if (simpleMdl.getUserKbn()
                                                               == GSConstCommon.PLUGIN_ID_PROJECT) {
                                                                sch = "[TODO]";
                                                            } else if (simpleMdl.getUserKbn()
                                                                == GSConstCommon.PLUGIN_ID_NIPPOU) {
                                                                sch = "[アクション]";
                                                            } else if (simpleMdl.getUserKbn()
                                                                    .equals("1")) {
                                                                sch = "[G]";
                                                            }
                                                        }
                                                        if (simpleMdl.getUserName() != null) {
                                                           sch += "[" + simpleMdl.getUserName()
                                                                   + "]\n";
                                                        }
                                                        if (simpleMdl.getTime() != null) {
                                                            sch += simpleMdl.getTime();
                                                        }
                                                        sch += simpleMdl.getTitle();
                                                    } else {
                                                        if (simpleMdl.getUserName() != null) {
                                                           sch += "[" + simpleMdl.getUserName()
                                                                   + "]\n";
                                                        }
                                                        if (simpleMdl.getTime() != null) {
                                                            sch += simpleMdl.getTime();
                                                        }
                                                        sch += simpleMdl.getTitle();
                                                    }
                                                    Font font_main_kikan = null;
                                                    if (p_pblicType
                                                            == GSConstSchedule.DSP_YOTEIARI) {
                                                        font_main_kikan = font_main_yotei;
                                                    } else {
                                                        switch (simpleMdl.getBgColor()) {
                                                        case  GSConstSchedule.BGCOLOR_BLUE :
                                                            font_main_kikan = font_main_blue;
                                                            break;
                                                        case GSConstSchedule.BGCOLOR_RED :
                                                            font_main_kikan = font_main_r;
                                                            break;
                                                        case GSConstSchedule.BGCOLOR_GREEN :
                                                            font_main_kikan = font_main_g;
                                                            break;
                                                        case GSConstSchedule.BGCOLOR_ORANGE :
                                                            font_main_kikan = font_main_o;
                                                            break;
                                                        case GSConstSchedule.BGCOLOR_BLACK :
                                                            font_main_kikan = font_main_black;
                                                            break;
                                                        case GSConstSchedule.BGCOLOR_NAVY :
                                                            font_main_kikan = font_main_navy;
                                                            break;
                                                        case GSConstSchedule.BGCOLOR_MAROON :
                                                            font_main_kikan = font_main_maroon;
                                                            break;
                                                        case GSConstSchedule.BGCOLOR_TEAL :
                                                            font_main_kikan = font_main_teal;
                                                            break;
                                                        case GSConstSchedule.BGCOLOR_GRAY :
                                                            font_main_kikan = font_main_gray;
                                                            break;
                                                        case GSConstSchedule.BGCOLOR_LBLUE :
                                                            font_main_kikan = font_main_lblue;
                                                            break;
                                                        default:
                                                            font_main_kikan = font_main_blue;
                                                            break;
                                                        }
                                                    }
                                                    sch = StringUtilHtml.deleteHtmlTag(sch);
                                                    sch = PdfUtil.replaseBackslashToYen(sch);
                                                    cell_main = new PdfPCell(
                                                            new Phrase(sch, font_main_kikan));
                                                    cell_main.setColspan(colspan);
                                                    cell_main.setHorizontalAlignment(
                                                            Element.ALIGN_CENTER);
                                                    cell_main.setVerticalAlignment(
                                                            Element.ALIGN_MIDDLE);

                                                    float top = 0;
                                                    float left = 0;
                                                    float right = 0;
                                                    float bottom = 0;
                                                    if (wMdl.getSch020NoTimeSchList().size() == 1) {
                                                        top = 0;
                                                        bottom = 0.5f;
                                                    } else  if (row_num == 0) {
                                                        top = 0;
                                                        bottom = 0.25f;
                                                    } else if (
                                                        row_num
                                                            == wMdl.getSch020NoTimeSchList().size()
                                                                  - 1) {
                                                        top = 0.25f;
                                                        bottom = 0.5f;
                                                    } else {
                                                        top = 0.25f;
                                                        bottom = 0.25f;
                                                    }

                                                    if ((col_num == 0)
                                                            && (col_num == simMdlList.size() - 1)) {
                                                        left = 1f;
                                                        right = 1f;
                                                    } else  if (col_num == 0) {
                                                        left = 1f;
                                                        right = 0.25f;
                                                    } else if (col_num == simMdlList.size() - 1) {
                                                        left = 0.25f;
                                                        right = 1f;
                                                    } else {
                                                        left = 0.25f;
                                                        right = 0.25f;
                                                    }

                                                    settingWidth(cell_main, top,
                                                                left, right, bottom);
                                                    cell_main.setBackgroundColor(color_kikan);
                                                    cell_main.setPaddingBottom(3f);
                                                    table_main.addCell(cell_main);
                                                } else {
                                                    cell_main = new PdfPCell(
                                                            new Phrase(EMP__, font_empty));
                                                    cell_main.setColspan(1);
                                                    float top = 0;
                                                    float left = 0;
                                                    float right = 0;
                                                    float bottom = 0;

                                                    if (wMdl.getSch020NoTimeSchList().size() == 1) {
                                                        top = 0;
                                                        bottom = 0.5f;
                                                    } else  if (row_num == 0) {
                                                        top = 0;
                                                        bottom = 0;
                                                    } else if (row_num
                                                          == wMdl.getSch020NoTimeSchList().size()
                                                              - 1) {
                                                        top = 0.25f;
                                                        bottom = 0.5f;
                                                    } else {
                                                        top = 0.25f;
                                                        bottom = 0.25f;
                                                    }
                                                    if ((wMdl.getSch020NoTimeSchList().size() == 1)
                                                        || (wMdl.getSch020NoTimeSchList().size() - 1
                                                                    == row_num)) {
                                                        top = 0;
                                                        bottom = 0.5f;
                                                    } else {
                                                        top = 0f;
                                                        bottom = 0f;
                                                    }

                                                    if (col_num == 0) {
                                                        left = 1f;
                                                        right = 0;
                                                    } else if (col_num == simMdlList.size() - 1) {
                                                        left = 0;
                                                        right = 1f;
                                                    } else {
                                                        left = 0;
                                                        right = 0;
                                                    }
                                                    settingWidth(cell_main, top,
                                                                left, right, bottom);
                                                    table_main.addCell(cell_main);
                                                }
                                                col_num++;
                                            }
                                            row_num++;
                                        }
                                    }
                                }
                                week_num++;
                            }
                            rowCnt++;
                        }
                        count = 0;
                    }
                }
            }

            //最後の下線
            cell_main = new PdfPCell(new Phrase(EMP__, font_empty));
            cell_main.setColspan(7);
            settingWidth(cell_main, 0.5f, 0, 0, 0);
            table_main.addCell(cell_main);

            table_title.setSplitLate(false);
            table_main.setSplitLate(false);

            PdfUtil pdfUtil = new PdfUtil();
            pdfUtil.addCalcPaging(doc, table_title, 0);
            pdfUtil.addCalcPaging(doc, table_main, 0);
            doc.close();

            //ページ番号の追記
            reader = new PdfReader(byteout.toByteArray());
            //生成されたPDFの総ページ数を取得する
            int total = reader.getNumberOfPages();
            PdfContentByte under_page;
            PdfContentByte under_date;
            stamper = new PdfStamper(reader, oStream);

            UDate date = new UDate();
            for (int i = 1; i <= total; i++) {
                //アンダーコンテンツを取得する
                under_page = stamper.getUnderContent(i);
                //ページ番号を追加する、フォント設定、位置設定
                under_page.beginText();
                under_page.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                under_page.moveText(750, 570);
                under_page.showText(
                        i + "/" + total + " " + gsMsg.getMessage("cmn.pdf.page"));
                under_page.endText();

                //アンダーコンテンツを取得する //(595F,842F)
                under_date = stamper.getUnderContent(i);
                under_date.beginText();
                under_date.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                under_date.moveText(750, 10);
                under_date.showText(
                        date.getStrYear() + "/" + date.getStrMonth() + "/" + date.getStrDay());
                under_date.endText();
            }
        } finally {
            if (stamper != null) {
                stamper.close();
            }
            if (reader != null) {
                reader.close();
            }
            if (pdfwriter != null) {
                pdfwriter.close();
            }
            if (doc != null && doc.isOpen()) {
                doc.close();
            }
        }

    }

    /**
     * 線の太さを設定する。
     *
     * @param cell セル情報
     * @param top セル上部の線の太さ
     * @param left セル左側の線の太さ
     * @param right セル右側の線の太さ
     * @param bottom セル下部の線の太さ
     * @return cell セル情報
     * */
    private PdfPCell settingWidth(PdfPCell cell, float top, float left, float right, float bottom) {

        cell.setBorderWidthTop(top);
        cell.setBorderWidthLeft(left);
        cell.setBorderWidthRight(right);
        cell.setBorderWidthBottom(bottom);
        return cell;
    }

}