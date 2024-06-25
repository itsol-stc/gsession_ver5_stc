package jp.groupsession.v2.sch.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
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
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.model.SimpleCalenderModel;
import jp.groupsession.v2.sch.sch010.Sch010DayOfModel;
import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 週間スケジュールPDF出力に関するユーティリティクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchSyuPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchSyuPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public SchSyuPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public SchSyuPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 週間スケジュールPdf出力に関するUtilクラスです。
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
    public void createSchSyukanPdf(
            SchSyuPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        log__.info("---------- SchSyuPdfUtilクラス START ----------");

        Document doc = null;

        /* フォント設定部 */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //1pt
        Font font_m1 = new Font(PdfUtil.getBaseFont(fontPath), 1);

        //フォント(アカウント名)
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント(ヘッダー)
        Font font_header = PdfUtil.getFont10b(fontPath);
        //フォント(メイン)
        Font font_main = PdfUtil.getFont10(fontPath);
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
        //フォント(スケジュール内容 予定あり)
        Font font_main_yotei = PdfUtil.getFont10(fontPath);
        font_main_yotei.setColor(PdfUtil.FONT_COLOR_BLACK);

        //フォント(改行)
        Font font_empty = PdfUtil.getFont10(fontPath);
        //フォント(小)
        Font font_little = font_m1;
        //フォント(氏名、日付)
        Font font_date = PdfUtil.getFont10b(fontPath);
        //フォント(六曜)
        Font font_rokuyou = PdfUtil.getFont8b(fontPath);

        //土曜日用フォント10pt太
        Font font_sat = PdfUtil.getFont10b(fontPath);
        font_sat.setColor(PdfUtil.FONT_COLOR_BLUE);
        //日曜日用フォント10pt太
        Font font_sun = PdfUtil.getFont10b(fontPath);
        font_sun.setColor(PdfUtil.FONT_COLOR_RED);
        //祝日用フォント10pt
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

        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
        doc = new Document(PageSize.A4.rotate()); //(595F,842F)
        PdfWriter pdfwriter = null;
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            pdfwriter = PdfWriter.getInstance(doc, byteout);

            GsMessage gsMsg = new GsMessage(reqMdl__);
            String smail = gsMsg.getMessage("sml.167");
            //出力するPDFに説明を付与します。
            doc.addAuthor("GroupSession");
            doc.addSubject(smail);

            doc.open();

            //文字入力範囲（横）
            float totalWidth = 920;
            //スケジュールの高さ設定
            float sch_height = 40f;

            //タイトル
            PdfPTable table_title = new PdfPTable(2);
            table_title.setWidthPercentage(100f);
            table_title.setTotalWidth(totalWidth);
            float [] width_title  = {0.3f, 0.7f};
            table_title.setWidths(width_title);
            PdfPCell cell_title;

            //スケジュールテーブル
            PdfPTable table_body = new PdfPTable(8);
            table_body.setWidthPercentage(100f);
            table_body.setTotalWidth(totalWidth);
            float [] width_body  = {0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f, 0.125f};
            table_body.setWidths(width_body);
            PdfPCell cell_body;

            //スケジュール表示モード
            cell_title = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            gsMsg.getMessage("schedule.108")
                            + "["
                            + gsMsg.getMessage("cmn.weeks")
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

            //スケジュール
            //ヘッダー 年月
            cell_body = new PdfPCell(new Phrase(pdfMdl.getHeadDate(), font_header));
            settingWidth(cell_body, 1, 1, 0, 0);
            cell_body.setColspan(1);
            cell_body.setLeading(1.1f, 1.1f);
            cell_body.setBackgroundColor(color_header);
            cell_body.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_body.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_body.addCell(cell_body);

            //表示グループ
            cell_body = new PdfPCell(new Phrase(
                    PdfUtil.replaseBackslashToYen(
                    gsMsg.getMessage("cmn.show.group")
                    + "：" + pdfMdl.getDispGroup()), font_header));
            settingWidth(cell_body, 1, 0, 1, 0);
            cell_body.setColspan(7);
            cell_body.setLeading(1.1f, 1.1f);
            cell_body.setBackgroundColor(color_header);
            cell_body.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_body.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_body.addCell(cell_body);

            //氏名
            cell_body = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.name"), font_date));
            settingWidth(cell_body, 0.5f, 1, 0.25f, 1);
            cell_body.setLeading(1.1f, 1.1f);
            cell_body.setBackgroundColor(color_date);
            cell_body.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_body.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_body.addCell(cell_body);

            //日にち＆曜日
            int simCalCnt = 0;
            float [] width_headDate  = {0.7f, 0.3f};
            for (SimpleCalenderModel simCalMdl : pdfMdl.getCalendarList()) {
                Font font = null;
                if ((Integer.valueOf(simCalMdl.getHolidayKbn()) == 1)
                || (Integer.valueOf(simCalMdl.getWeekKbn()) == 1)) {
                    font = font_sun;
                } else if (Integer.valueOf(simCalMdl.getWeekKbn()) == 7) {
                    font = font_sat;
                } else {
                    font = font_date;
                }

                if (StringUtil.isNullZeroString(simCalMdl.getRokuyouName())) {
                    //日付のみ表示
                    cell_body = new PdfPCell(new Phrase(simCalMdl.getDspDayString(), font));
                } else {
                    cell_body = new PdfPCell();
                    //日付 + 六曜を表示
                    PdfPTable table_headDate = new PdfPTable(2);
                    table_headDate.setWidthPercentage(100f);
                    table_headDate.setTotalWidth(totalWidth);
                    table_headDate.setWidths(width_headDate);
                    PdfPCell dateCell = new PdfPCell(
                            new Phrase(simCalMdl.getDspDayString(), font));
                    dateCell.setBorder(0);
                    dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    PdfPCell rokuyouCell = new PdfPCell(
                            new Phrase(simCalMdl.getRokuyouName(), font_rokuyou));
                    rokuyouCell.setBorder(0);
                    rokuyouCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
                    table_headDate.addCell(dateCell);
                    table_headDate.addCell(rokuyouCell);
                    cell_body.addElement(table_headDate);
                }

                cell_body.setLeading(1.1f, 1.1f);
                cell_body.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_body.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (simCalCnt != pdfMdl.getCalendarList().size() - 1) {
                    settingWidth(cell_body, 0.5f, 0.25f, 0.25f, 1);
                } else  {
                    settingWidth(cell_body, 0.5f, 0.25f, 1, 1);
                }
                cell_body.setBackgroundColor(color_date);
                cell_body.setPaddingBottom(5f);

                table_body.addCell(cell_body);

                simCalCnt++;
            }

            //改ページ時の表示設定 ここまでのtable_topを上から２行分表示する(グループ行と日付行)
            table_body.setHeaderRows(2);

            int topHolCnt = 0;
            String project = GSConstCommon.PLUGIN_ID_PROJECT;
            String nippou = GSConstCommon.PLUGIN_ID_NIPPOU;
            //グループ、本人
            for (Sch010WeekOfModel wMdl : pdfMdl.getTopList()) {
                //グループまたは、ユーザ行ごとのテーブル
                PdfPTable table_user = new PdfPTable(8);
                table_user.setWidthPercentage(100f);
                table_user.setTotalWidth(totalWidth);
                float [] width_user  = {0.125f, 0.125f, 0.125f, 0.125f,
                                        0.125f, 0.125f, 0.125f, 0.125f};
                table_user.setWidths(width_user);
                PdfPCell cell_user;

                //ユーザ区分
                if (wMdl.getSch010UsrMdl().getUsrKbn() == GSConstSchedule.USER_KBN_GROUP) {

                    //グループ名欄
                    PdfPTable table_group = new PdfPTable(3);
                    table_group.setWidthPercentage(100f);
                    table_group.setTotalWidth(totalWidth);
                    float [] width_group  = {0.05f, 0.1f, 0.85f};
                    table_group.setWidths(width_group);
                    PdfPCell cell_group;

                    //空白
                    cell_group = new PdfPCell(new Phrase(EMP__, font_little));
                    cell_group.setColspan(3);
                    cell_group.setFixedHeight(0.5f);
                    cell_group.setBorder(0);
                    cell_group.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_group.addCell(cell_group);

                    cell_group = new PdfPCell(new Phrase(EMP__, font_little));
                    cell_group.setBorder(0);
                    cell_group.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_group.addCell(cell_group);

                    String imagePath =
                            IOTools.replaceFileSep(appRootPath + "common/images/groupicon.gif");
                    File file = new File(imagePath);
                    //ファイルの有無チェック
                    if (file.exists()) {
                        Image img = Image.getInstance(imagePath);
                        img.scalePercent(60f);
                        cell_group = new PdfPCell(img, false);
                    } else {
                        cell_group = new PdfPCell(new Phrase(EMP__, font_main));
                    }
                    cell_group.setBorder(0);
                    cell_group.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    table_group.addCell(cell_group);

                    cell_group = new PdfPCell(new Phrase(
                            PdfUtil.replaseBackslashToYen(wMdl.getSch010UsrMdl().getUsrName()),
                            font_main));
                    cell_group.setBorder(0);
                    cell_group.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_group.addCell(cell_group);

                    cell_user = new PdfPCell(table_group);
                    settingWidth(cell_user, 0, 1, 0.25f, 0);
                    cell_user.setRowspan(wMdl.getSch010PeriodRow());
                    cell_user.setLeading(1.1f, 1.1f);
                    cell_user.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_user.addCell(cell_user);
                } else {
                    cell_user = new PdfPCell(new Phrase(
                            PdfUtil.replaseBackslashToYen(wMdl.getSch010UsrMdl().getUsrName()),
                            font_main));
                    settingWidth(cell_user, 0, 1, 0.25f, 0);
                    cell_user.setRowspan(wMdl.getSch010PeriodRow());
                    cell_user.setLeading(1.1f, 1.1f);
                    cell_user.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_user.addCell(cell_user);
                }

                //一週間のスケジュール
                int dayCount = 0;
                for (Sch010DayOfModel dMdl : wMdl.getSch010SchList()) {
                    //グループ名欄
                    PdfPTable table_sch = new PdfPTable(1);
                    table_sch.setWidthPercentage(100f);
                    table_sch.setTotalWidth(totalWidth);
                    float [] width_sch  = {1f};
                    table_sch.setWidths(width_sch);
                    PdfPCell cell_sch;

                    //祝日の有無
                    if (dMdl.getHolidayName() != null && topHolCnt == 0) {
                        cell_sch = new PdfPCell(new Phrase(
                                NullDefault.getString(dMdl.getHolidayName(), EMP__), font_syuku));
                        cell_sch.setBorder(0);
                        cell_sch.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table_sch.addCell(cell_sch);
                    }

                    //一日のスケジュール
                    for (SimpleScheduleModel simMdl : dMdl.getSchDataList()) {

                        String schTitle = "";
                        if (wMdl.getSch010UsrMdl().getUsrKbn() == 0
                                && (Integer.valueOf(simMdl.getUserKbn()) == 1)) {
                            schTitle = "[G]";
                        }

                        if (simMdl.getTime() != null) {
                            schTitle += NullDefault.getString(simMdl.getTime(), EMP__) + "\n";
                        }
                        
                        schTitle += NullDefault.getString(simMdl.getTitle(), EMP__);
                        schTitle = PdfUtil.replaseBackslashToYen(schTitle);
                        Font font_top_main = font_main_blue;
                        switch (simMdl.getBgColor()) {
                        case  GSConstSchedule.BGCOLOR_BLUE :
                            font_top_main = font_main_blue;
                            break;
                        case GSConstSchedule.BGCOLOR_RED :
                            font_top_main = font_main_r;
                            break;
                        case GSConstSchedule.BGCOLOR_GREEN :
                            font_top_main = font_main_g;
                            break;
                        case GSConstSchedule.BGCOLOR_ORANGE :
                            font_top_main = font_main_o;
                            break;
                        case GSConstSchedule.BGCOLOR_BLACK :
                            font_top_main = font_main_black;
                            break;
                        case GSConstSchedule.BGCOLOR_NAVY :
                            font_top_main = font_main_navy;
                            break;
                        case GSConstSchedule.BGCOLOR_MAROON :
                            font_top_main = font_main_maroon;
                            break;
                        case GSConstSchedule.BGCOLOR_TEAL :
                            font_top_main = font_main_teal;
                            break;
                        case GSConstSchedule.BGCOLOR_GRAY :
                            font_top_main = font_main_gray;
                            break;
                        case GSConstSchedule.BGCOLOR_LBLUE :
                            font_top_main = font_main_lblue;
                            break;
                        default:
                            font_top_main = font_main_blue;
                            break;
                        }
                        cell_sch = new PdfPCell(new Phrase(schTitle, font_top_main));
                        cell_sch.setHorizontalAlignment(Element.ALIGN_LEFT);
                        cell_sch.setBorder(0);
                        cell_sch.setPaddingBottom(4f);
                        table_sch.addCell(cell_sch);
                    }

                    cell_user = new PdfPCell(table_sch);
                    if (dayCount == wMdl.getSch010SchList().size() - 1) {
                        settingWidth(cell_user, 0, 0.25f, 1f, 0);
                    } else {
                        settingWidth(cell_user, 0, 0.25f, 0.25f, 0);
                    }
                    if (dMdl.getWeekKbn() == 7) {
                        //土曜日
                        cell_user.setBackgroundColor(color_saturday);
                    } else if (dMdl.getWeekKbn() == 1) {
                        //日曜日
                        cell_user.setBackgroundColor(color_sunday);
                    }
                    cell_user.setMinimumHeight(sch_height);
                    cell_user.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_user.addCell(cell_user);

                    dayCount++;
                }

                //期間スケジュール
                if (wMdl.getSch010NoTimeSchList() != null) {
                    int simListCnt = 0;
                    for (ArrayList<SimpleScheduleModel> simMdlList
                            : wMdl.getSch010NoTimeSchList()) {

                        PdfPTable table_kikan = new PdfPTable(7);
                        table_kikan.setWidthPercentage(100f);
                        table_kikan.setTotalWidth(805);
                        float [] width_kikan  = {115, 115, 115, 115, 115, 115, 115};
                        table_kikan.setWidths(width_kikan);
                        PdfPCell cell_kikan;

                        int simCnt = 0;
                        for (SimpleScheduleModel simMdl : simMdlList) {
                            int publicType = simMdl.getPublic();
                            int kjnEdKbn = simMdl.getKjnEdKbn();
                            int grpEdKbn = simMdl.getGrpEdKbn();
                            String schTitle = "";
                            if ((publicType == GSConstSchedule.DSP_PUBLIC)
                                    || (kjnEdKbn == GSConstSchedule.EDIT_CONF_OWN
                                    || grpEdKbn == GSConstSchedule.EDIT_CONF_GROUP)) {

                                if (wMdl.getSch010UsrMdl().getUsrKbn() == 0) {
                                    if (simMdl.getUserKbn() != null) {

                                        if (simMdl.getUserKbn().equals(project)) {
                                            schTitle = "[TODO]";
                                        } else if (simMdl.getUserKbn().equals(nippou)) {
                                            schTitle = "[アクション]";
                                        } else if (simMdl.getUserKbn().equals("1")) {
                                            schTitle = "[G]";
                                        }
                                    }
                                }

                                if (simMdl.getTime() != null) {
                                    schTitle += NullDefault.getString(simMdl.getTime(), EMP__);
                                }
                                schTitle += NullDefault.getString(simMdl.getTitle(), EMP__);
                            } else {
                                if (wMdl.getSch010UsrMdl().getUsrKbn() == 0) {
                                    if (simMdl.getUserKbn() != null) {
                                        if (Integer.valueOf(simMdl.getUserKbn()) == 1) {
                                            schTitle = "[G]";
                                        }
                                    }
                                }

                                if (simMdl.getTime() != null) {
                                    schTitle += NullDefault.getString(simMdl.getTime(), EMP__);
                                }
                                schTitle += NullDefault.getString(simMdl.getTitle(), EMP__);
                            }
                            Font font_top_kikan;
                            switch (simMdl.getBgColor()) {
                            case  GSConstSchedule.BGCOLOR_BLUE :
                                font_top_kikan = font_main_blue;
                                break;
                            case GSConstSchedule.BGCOLOR_RED :
                                font_top_kikan = font_main_r;
                                break;
                            case GSConstSchedule.BGCOLOR_GREEN :
                                font_top_kikan = font_main_g;
                                break;
                            case GSConstSchedule.BGCOLOR_ORANGE :
                                font_top_kikan = font_main_o;
                                break;
                            case GSConstSchedule.BGCOLOR_BLACK :
                                font_top_kikan = font_main_black;
                                break;
                            case GSConstSchedule.BGCOLOR_NAVY :
                                font_top_kikan = font_main_navy;
                                break;
                            case GSConstSchedule.BGCOLOR_MAROON :
                                font_top_kikan = font_main_maroon;
                                break;
                            case GSConstSchedule.BGCOLOR_TEAL :
                                font_top_kikan = font_main_teal;
                                break;
                            case GSConstSchedule.BGCOLOR_GRAY :
                                font_top_kikan = font_main_gray;
                                break;
                            case GSConstSchedule.BGCOLOR_LBLUE :
                                font_top_kikan = font_main_lblue;
                                break;
                            default:
                                font_top_kikan = font_main_blue;
                                break;
                            }
                            schTitle = StringUtilHtml.deleteHtmlTag(schTitle);
                            schTitle = PdfUtil.replaseBackslashToYen(schTitle);
                            cell_kikan = new PdfPCell(new Phrase(
                                    NullDefault.getString(schTitle, EMP__), font_top_kikan));
                            float top = 0;
                            float left = 0;
                            float right = 0;
                            float bottom = 0;
                            int colspan;
                            if (simMdl.getPeriodMdl() == null) {
                                colspan = 1;
                            } else {
                                colspan = simMdl.getPeriodMdl().getSchPeriodCnt();
                                cell_kikan.setBackgroundColor(color_kikan);

                                if (simCnt == 0) {
                                    right = 0.25f;
                                } else if (simCnt == simMdlList.size() - 1) {
                                    left = 0.25f;
                                } else {
                                    right = 0.25f;
                                    left = 0.25f;
                                }

                                if (wMdl.getSch010NoTimeSchList().size() != 1) {
                                    if (simListCnt == 0) {
                                        bottom = 0.25f;
                                    } else if (simListCnt
                                            == wMdl.getSch010NoTimeSchList().size() - 1) {
                                        top = 0.25f;
                                    } else {
                                        top = 0.25f;
                                        bottom = 0.25f;
                                    }
                                }
                            }
                            settingWidth(cell_kikan, top, left, right, bottom);
                            cell_kikan.setColspan(colspan);
                            cell_kikan.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell_kikan.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell_kikan.setPaddingBottom(6f);
                            table_kikan.addCell(cell_kikan);

                            simCnt++;
                        }
                        cell_user = new PdfPCell(table_kikan);

                        if (simListCnt == 0) {
                            settingWidth(cell_user, 0.5f, 0.25f, 1f, 0);
                        } else {
                            settingWidth(cell_user, 0, 0.25f, 1f, 0);
                        }
                        cell_user.setColspan(7);
                        cell_user.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table_user.addCell(cell_user);

                        simListCnt++;
                    }
                }

                cell_body = new PdfPCell(table_user);
                cell_body.setColspan(8);
                settingWidth(cell_body, 0, 0, 0, 1);

                table_body.addCell(cell_body);
                topHolCnt++;
            }

            if (!pdfMdl.getTopList().isEmpty()) {
                cell_body = new PdfPCell(
                                new Phrase(gsMsg.getMessage("schedule.74"), font_header));

                settingWidth(cell_body, 0, 1, 0, 1);
                cell_body.setColspan(1);
                cell_body.setLeading(1.1f, 1.1f);
                cell_body.setBackgroundColor(color_header);
                cell_body.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_body.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table_body.addCell(cell_body);

                //表示グループ
                cell_body = new PdfPCell(new Phrase(EMP__, font_header));
                settingWidth(cell_body, 0, 0, 1, 1);
                cell_body.setColspan(7);
                cell_body.setLeading(1.1f, 1.1f);
                cell_body.setBackgroundColor(color_header);
                cell_body.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_body.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table_body.addCell(cell_body);
            }

            int bottomHolCnt = 0;
            //グループメンバー
            for (Sch010WeekOfModel wMdl : pdfMdl.getBottomList()) {
                //グループメンバーごとのテーブル
                PdfPTable table_user = new PdfPTable(8);
                table_user.setWidthPercentage(100f);
                table_user.setTotalWidth(totalWidth);
                float [] width_user  = {0.125f, 0.125f, 0.125f, 0.125f,
                                        0.125f, 0.125f, 0.125f, 0.125f};
                table_user.setWidths(width_user);
                PdfPCell cell_user;

                //名前をセット
                cell_user = new PdfPCell(
                                new Phrase(
                                PdfUtil.replaseBackslashToYen(wMdl.getSch010UsrMdl().getUsrName()),
                                font_main));
                settingWidth(cell_user, 0, 1, 0.25f, 0);
                cell_user.setRowspan(wMdl.getSch010PeriodRow());
                cell_user.setLeading(1.1f, 1.1f);
                cell_user.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_user.addCell(cell_user);

                //一週間のスケジュール
                int dMdlCnt = 0;
                for (Sch010DayOfModel dMdl : wMdl.getSch010SchList()) {
                    //グループ名欄
                    PdfPTable table_sch = new PdfPTable(1);
                    table_sch.setWidthPercentage(100f);
                    table_sch.setTotalWidth(totalWidth);
                    float [] width_sch  = {1f};
                    table_sch.setWidths(width_sch);
                    PdfPCell cell_sch;

                    //祝日の有無
                    if (dMdl.getHolidayName() != null && bottomHolCnt == 0
                            && pdfMdl.getTopList().size() < 1) {
                        cell_sch = new PdfPCell(new Phrase(
                                NullDefault.getString(dMdl.getHolidayName(), EMP__), font_syuku));
                        cell_sch.setBorder(0);
                        cell_sch.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table_sch.addCell(cell_sch);
                    }
                    //一日のスケジュール
                    for (SimpleScheduleModel simMdl : dMdl.getSchDataList()) {

                        int publicType = simMdl.getPublic();
                        int kjnEdKbn = simMdl.getKjnEdKbn();
                        int grpEdKbn = simMdl.getGrpEdKbn();

                        String schTitle = "";
                        if (wMdl.getSch010UsrMdl().getUsrKbn() == 0) {
                            if (simMdl.getUserKbn() != null) {

                                if (simMdl.getUserKbn().equals(project)) {
                                    schTitle = "[TODO]";
                                } else if (simMdl.getUserKbn().equals(nippou)) {
                                    schTitle = "[アクション]";
                                } else if (simMdl.getUserKbn().equals("1")) {
                                    schTitle = "[G]";
                                }
                            }
                        }
                        if ((publicType == GSConstSchedule.DSP_PUBLIC
                                || publicType == GSConstSchedule.DSP_BELONG_GROUP)
                                || (kjnEdKbn == GSConstSchedule.EDIT_CONF_OWN
                                || grpEdKbn == GSConstSchedule.EDIT_CONF_GROUP)) {
                            if (!ValidateUtil.isEmpty(simMdl.getTime())) {
                                schTitle += NullDefault.getString(simMdl.getTime(), EMP__) + "\n";
                            }
                            if (!ValidateUtil.isEmpty(simMdl.getTitle())) {
                                schTitle += NullDefault.getString(simMdl.getTitle(), EMP__);
                            }
                            Font font_bottom_main;
                            if (publicType == GSConstSchedule.DSP_YOTEIARI
                            || publicType == GSConstSchedule.DSP_TITLE) {
                                font_bottom_main = font_main_yotei;
                            } else {
                                switch (simMdl.getBgColor()) {
                                    case  GSConstSchedule.BGCOLOR_BLUE :
                                        font_bottom_main = font_main_blue;
                                        break;
                                    case GSConstSchedule.BGCOLOR_RED :
                                        font_bottom_main = font_main_r;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GREEN :
                                        font_bottom_main = font_main_g;
                                        break;
                                    case GSConstSchedule.BGCOLOR_ORANGE :
                                        font_bottom_main = font_main_o;
                                        break;
                                    case GSConstSchedule.BGCOLOR_BLACK :
                                        font_bottom_main = font_main_black;
                                        break;
                                    case GSConstSchedule.BGCOLOR_NAVY :
                                        font_bottom_main = font_main_navy;
                                        break;
                                    case GSConstSchedule.BGCOLOR_MAROON :
                                        font_bottom_main = font_main_maroon;
                                        break;
                                    case GSConstSchedule.BGCOLOR_TEAL :
                                        font_bottom_main = font_main_teal;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GRAY :
                                        font_bottom_main = font_main_gray;
                                        break;
                                    case GSConstSchedule.BGCOLOR_LBLUE :
                                        font_bottom_main = font_main_lblue;
                                        break;
                                    default:
                                        font_bottom_main = font_main_blue;
                                        break;
                                }
                            }

                            if (!ValidateUtil.isEmpty(schTitle)) {
                                schTitle = PdfUtil.replaseBackslashToYen(schTitle);
                                cell_sch = new PdfPCell(new Phrase(schTitle, font_bottom_main));
                                cell_sch.setHorizontalAlignment(Element.ALIGN_LEFT);
                                cell_sch.setBorder(0);
                                cell_sch.setPaddingBottom(4f);
                                table_sch.addCell(cell_sch);
                            }
                        } else {
                            if (!ValidateUtil.isEmpty(simMdl.getTime())) {
                                schTitle += NullDefault.getString(simMdl.getTime(), EMP__) + "\n";
                            }
                            if (!ValidateUtil.isEmpty(simMdl.getTitle())) {
                                schTitle += NullDefault.getString(simMdl.getTitle(), EMP__);
                            }
                            Font font_bottom_main;
                            if (simMdl.getPublic() == GSConstSchedule.DSP_YOTEIARI
                            || publicType == GSConstSchedule.DSP_TITLE) {
                                font_bottom_main = font_main_yotei;
                            } else {
                                switch (simMdl.getBgColor()) {
                                    case  GSConstSchedule.BGCOLOR_BLUE :
                                        font_bottom_main = font_main_blue;
                                        break;
                                    case GSConstSchedule.BGCOLOR_RED :
                                        font_bottom_main = font_main_r;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GREEN :
                                        font_bottom_main = font_main_g;
                                        break;
                                    case GSConstSchedule.BGCOLOR_ORANGE :
                                        font_bottom_main = font_main_o;
                                        break;
                                    case GSConstSchedule.BGCOLOR_BLACK :
                                        font_bottom_main = font_main_black;
                                        break;
                                    case GSConstSchedule.BGCOLOR_NAVY :
                                        font_bottom_main = font_main_navy;
                                        break;
                                    case GSConstSchedule.BGCOLOR_MAROON :
                                        font_bottom_main = font_main_maroon;
                                        break;
                                    case GSConstSchedule.BGCOLOR_TEAL :
                                        font_bottom_main = font_main_teal;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GRAY :
                                        font_bottom_main = font_main_gray;
                                        break;
                                    case GSConstSchedule.BGCOLOR_LBLUE :
                                        font_bottom_main = font_main_lblue;
                                        break;
                                    default:
                                        font_bottom_main = font_main_blue;
                                        break;
                                }
                            }
                            if (!ValidateUtil.isEmpty(schTitle)) {
                                schTitle = PdfUtil.replaseBackslashToYen(schTitle);
                                cell_sch = new PdfPCell(new Phrase(schTitle, font_bottom_main));
                                cell_sch.setHorizontalAlignment(Element.ALIGN_LEFT);
                                cell_sch.setBorder(0);
                                cell_sch.setPaddingBottom(4f);
                                table_sch.addCell(cell_sch);
                            }
                        }
                    }

                    cell_user = new PdfPCell(table_sch);
                    if (dMdlCnt == wMdl.getSch010SchList().size() - 1) {
                        settingWidth(cell_user, 0, 0.25f, 1f, 0);
                    } else {
                        settingWidth(cell_user, 0, 0.25f, 0.25f, 0);
                    }

                    if (dMdl.getWeekKbn() == 7) {
                        //土曜日
                        cell_user.setBackgroundColor(color_saturday);
                    } else if (dMdl.getWeekKbn() == 1) {
                        //日曜日
                        cell_user.setBackgroundColor(color_sunday);
                    }
                    cell_user.setMinimumHeight(sch_height);
                    cell_user.setHorizontalAlignment(Element.ALIGN_LEFT);
                    table_user.addCell(cell_user);

                    dMdlCnt++;
                }

                //期間スケジュール
                if (wMdl.getSch010NoTimeSchList() != null) {

                    int noTimeSimMdlListCnt = 0;
                    for (ArrayList<SimpleScheduleModel> noTimeSimMdlList
                            : wMdl.getSch010NoTimeSchList()) {

                        PdfPTable table_kikan = new PdfPTable(7);
                        table_kikan.setWidthPercentage(100f);
                        table_kikan.setTotalWidth(805);
                        float [] width_kikan  = {115, 115, 115, 115, 115, 115, 115};
                        table_kikan.setWidths(width_kikan);
                        PdfPCell cell_kikan;

                        int noTimeSimMdlCnt = 0;
                        for (SimpleScheduleModel simMdl : noTimeSimMdlList) {
                            int publicType = simMdl.getPublic();
                            int kjnEdKbn = simMdl.getKjnEdKbn();
                            int grpEdKbn = simMdl.getGrpEdKbn();

                            String schTitle = "";
                            if ((publicType == GSConstSchedule.DSP_PUBLIC
                                    || publicType == GSConstSchedule.DSP_BELONG_GROUP)
                                    || (kjnEdKbn == GSConstSchedule.EDIT_CONF_OWN
                                    || grpEdKbn == GSConstSchedule.EDIT_CONF_GROUP)) {

                                if (wMdl.getSch010UsrMdl().getUsrKbn() == 0) {
                                    if (simMdl.getUserKbn() != null) {
                                        if (simMdl.getUserKbn().equals(project)) {
                                            schTitle = "[TODO]";
                                        } else if (simMdl.getUserKbn().equals(nippou)) {
                                            schTitle = "[アクション]";
                                        } else if (simMdl.getUserKbn().equals("1")) {
                                            schTitle = "[G]";
                                        }
                                    }
                                }

                                if (simMdl.getTime() != null) {
                                    schTitle += NullDefault.getString(simMdl.getTime(), EMP__);
                                }
                                schTitle += NullDefault.getString(simMdl.getTitle(), EMP__);
                            } else {
                                if (simMdl.getTime() != null) {
                                    schTitle += NullDefault.getString(simMdl.getTime(), EMP__);
                                }
                                schTitle += NullDefault.getString(simMdl.getTitle(), EMP__);
                            }

                            Font font_bottom_kikan;
                            if (simMdl.getPublic() == GSConstSchedule.DSP_YOTEIARI
                            || publicType == GSConstSchedule.DSP_TITLE) {
                                font_bottom_kikan = font_main_yotei;
                            } else {
                                switch (simMdl.getBgColor()) {
                                    case  GSConstSchedule.BGCOLOR_BLUE :
                                        font_bottom_kikan = font_main_blue;
                                        break;
                                    case GSConstSchedule.BGCOLOR_RED :
                                        font_bottom_kikan = font_main_r;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GREEN :
                                        font_bottom_kikan = font_main_g;
                                        break;
                                    case GSConstSchedule.BGCOLOR_ORANGE :
                                        font_bottom_kikan = font_main_o;
                                        break;
                                    case GSConstSchedule.BGCOLOR_BLACK :
                                        font_bottom_kikan = font_main_black;
                                        break;
                                    case GSConstSchedule.BGCOLOR_NAVY :
                                        font_bottom_kikan = font_main_navy;
                                        break;
                                    case GSConstSchedule.BGCOLOR_MAROON :
                                        font_bottom_kikan = font_main_maroon;
                                        break;
                                    case GSConstSchedule.BGCOLOR_TEAL :
                                        font_bottom_kikan = font_main_teal;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GRAY :
                                        font_bottom_kikan = font_main_gray;
                                        break;
                                    case GSConstSchedule.BGCOLOR_LBLUE :
                                        font_bottom_kikan = font_main_lblue;
                                        break;
                                    default:
                                        font_bottom_kikan = font_main_blue;
                                        break;
                                }
                            }
                            schTitle = StringUtilHtml.deleteHtmlTag(schTitle);
                            schTitle = PdfUtil.replaseBackslashToYen(schTitle);
                            cell_kikan = new PdfPCell(new Phrase(
                                    NullDefault.getString(schTitle, EMP__), font_bottom_kikan));
                            int colspan;
                            float top = 0;
                            float left = 0;
                            float right = 0;
                            float bottom = 0;

                            if (simMdl.getPeriodMdl() == null) {
                                colspan = 1;
                            } else {
                                colspan = simMdl.getPeriodMdl().getSchPeriodCnt();
                                cell_kikan.setBackgroundColor(color_kikan);

                                if (noTimeSimMdlCnt == 0) {
                                    right = 0.25f;
                                } else if (noTimeSimMdlCnt == noTimeSimMdlList.size() - 1) {
                                    left = 0.25f;
                                } else {
                                    right = 0.25f;
                                    left = 0.25f;
                                }

                                if (wMdl.getSch010NoTimeSchList().size() != 1) {
                                    if (noTimeSimMdlListCnt == 0) {
                                        bottom = 0.25f;
                                    } else if (noTimeSimMdlListCnt
                                            == wMdl.getSch010NoTimeSchList().size() - 1) {
                                        top = 0.25f;
                                    } else {
                                        top = 0.25f;
                                        bottom = 0.25f;
                                    }
                                }
                            }

                            settingWidth(cell_kikan, top, left, right, bottom);
                            cell_kikan.setColspan(colspan);
                            cell_kikan.setHorizontalAlignment(Element.ALIGN_CENTER);
                            cell_kikan.setVerticalAlignment(Element.ALIGN_MIDDLE);
                            cell_kikan.setPaddingBottom(4f);
                            table_kikan.addCell(cell_kikan);

                            noTimeSimMdlCnt++;
                        }
                        cell_user = new PdfPCell(table_kikan);
                        if (noTimeSimMdlListCnt == 0) {
                            settingWidth(cell_user, 0.5f, 0.25f, 1f, 0);
                        } else {
                            settingWidth(cell_user, 0, 0.25f, 1f, 0);
                        }
                        cell_user.setColspan(7);
                        cell_user.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table_user.addCell(cell_user);

                        noTimeSimMdlListCnt++;
                    }
                }

                cell_body = new PdfPCell(table_user);
                cell_body.setColspan(8);
                settingWidth(cell_body, 0, 0, 0, 1);
                table_body.addCell(cell_body);
                bottomHolCnt++;
            }

            table_title.setSplitLate(false);
            table_body.setSplitLate(false);

            PdfUtil pdfUtil = new PdfUtil();
            pdfUtil.addCalcPaging(doc, table_title, 0);
            pdfUtil.addCalcPaging(doc, table_body, 0);

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

        log__.info("---------- SchSyuPdfUtilクラス END ----------");
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