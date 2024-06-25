package jp.groupsession.v2.rsv.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.RsvWeekModel;
import jp.groupsession.v2.rsv.RsvYoyakuDayModel;
import jp.groupsession.v2.rsv.RsvYoyakuModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約[月間]PDF出力に関するUtilクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvGekPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvGekPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RsvGekPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public RsvGekPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 施設予約[月間]PDF出力に関するUtilクラスです。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl pdfモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream 施設予約データの出力先となるストリーム
     * @throws Exception 実行例外
     * @throws FileNotFoundException 実行例外
     * @throws DocumentException 実行例外
     * @author JTS
     */
    public void createRsvGekkanPdf(
            RsvGekPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        log__.debug("施設予約[月間]PDFの生成開始");

        Document doc = null;

        /* フォントファイルパス */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント アカウント名
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント ヘッダー
        Font font_header = PdfUtil.getFont10b(fontPath);
        //フォント メイン 10pt 黒 施設情報 or 却下
        Font font_main_black = PdfUtil.getFont10(fontPath);
        //フォント メイン 10pt 青
        Font font_main_blue = PdfUtil.getFont10(fontPath);
        font_main_blue.setColor(PdfUtil.FONT_COLOR_BLUE);
        //フォント メイン 承認待ち 10pt オレンジ
        Font font_main_orange = PdfUtil.getFont10(fontPath);
        font_main_orange.setColor(PdfUtil.FONT_COLOR_ORANGE);
        //フォント 日付(今月)
        Font font_date = PdfUtil.getFont10b(fontPath);
        //フォント 日付(先月、来月)
        Font font_date_sub = PdfUtil.getFont10(fontPath);

        //フォント 土曜日用 10pt太
        Font font_sat = PdfUtil.getFont10b(fontPath);
        font_sat.setColor(PdfUtil.FONT_COLOR_BLUE);
        //フォント 日曜日用 10pt太
        Font font_sun = PdfUtil.getFont10b(fontPath);
        font_sun.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント 祝日用 太10pt
        Font font_holiday_bold = PdfUtil.getFont10b(fontPath);
        font_holiday_bold.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント 祝日用 10pt
        Font font_holiday = PdfUtil.getFont10(fontPath);
        font_holiday.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント(六曜)
        Font font_rokuyou = PdfUtil.getFont9(fontPath);

        //バックカラー（土曜日）
        Color color_saturday = PdfUtil.BG_COLOR_LIGHTBLUE;
        //バックカラー（日曜日）
        Color color_sunday = PdfUtil.BG_COLOR_LIGHTRED;
        //バックカラー（ヘッダ）
        Color color_header = PdfUtil.BG_COLOR_DARKBLUE;
        //バックカラー（日にち＆曜日）
        Color color_date = PdfUtil.BG_COLOR_LIGHTGRAY;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ByteArrayOutputStream byteout = null;

        try {
            doc = new Document(PageSize.A4.rotate());
            //アウトプットストリームをPDFWriterに設定します。
            byteout = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, byteout);

            String subject = gsMsg.getMessage("cmn.reserve")
                    + " " + gsMsg.getMessage("cmn.monthly");
            //出力するPDFに説明を付与します。
            doc.addAuthor("GroupSession");
            doc.addSubject(subject);
            doc.open();
            PdfUtil pdfUtil = new PdfUtil();
            //文字入力範囲（横）
            float totalWidth = 920;
            //一日の施設情報の最低の高さ設定
            float day_height = 60f;

            //タイトル表示テーブル
            float [] width_title  = {0.2f, 0.8f};
            PdfPTable table_title =
                    PdfUtil.createTable(2, 100, totalWidth, width_title, Element.ALIGN_CENTER);

            //施設予約一覧表示テーブル
            PdfPTable table_main =
                    PdfUtil.createTable(7, 100, totalWidth, Element.ALIGN_CENTER);

            //ヘッダータイトル
            String topTitle = NullDefault.getString(
                    gsMsg.getMessage("cmn.reserve")
                    + "["
                    + gsMsg.getMessage("cmn.monthly")
                    + "]", EMP__);
            PdfPCell cell_title = PdfUtil.setCellRet(
                    topTitle, 1, Element.ALIGN_JUSTIFIED_ALL, font_title);
            __settingWidth(cell_title, 0, 0, 0, 1);
            cell_title.setLeading(1.2f, 1.2f); //行間の設定
            cell_title.setPaddingBottom(5f);
            table_title.addCell(cell_title);

            PdfUtil.setCell(
                    table_title, EMP__, 1, Element.ALIGN_LEFT, PdfUtil.getFontEmpty(fontPath));

            //空白行
            PdfUtil.setCell(
                    table_title, EMP__, 2, Element.ALIGN_LEFT, PdfUtil.getFontEmpty(fontPath));

            PdfPCell cell_main;

            //ヘッダー 年月
            cell_main = PdfUtil.setCellRet(pdfMdl.getRsvGekHeadDate(),
                    1, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0, 0);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            //ヘッダー 表示グループ&施設
            String headGrpSis = gsMsg.getMessage("cmn.show.group")
                    + "：" + NullDefault.getString(pdfMdl.getRsvGekDspGroup(), EMP__)
                    + "        " + gsMsg.getMessage("cmn.facility")
                    + "：" + NullDefault.getString(pdfMdl.getRsvGekDspSisetsu(), EMP__);
            headGrpSis = PdfUtil.replaseBackslashToYen(headGrpSis);
            cell_main = PdfUtil.setCellRet(headGrpSis, 6,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 0, 1, 0);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(3f);
            table_main.addCell(cell_main);

            //ヘッダー 曜日
            //日曜日
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.sunday"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_sun);
            cell_main.setBackgroundColor(color_date);
            __settingWidth(cell_main, 0.5f, 1, 0.25f, 1);
            table_main.addCell(cell_main);
            //月曜日
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.Monday"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            cell_main.setBackgroundColor(color_date);
            __settingWidth(cell_main, 0.5f, 0.25f, 0.25f, 1);
            table_main.addCell(cell_main);
            //火曜日
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.tuesday"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            cell_main.setBackgroundColor(color_date);
            __settingWidth(cell_main, 0.5f, 0.25f, 0.25f, 1);
            table_main.addCell(cell_main);
            //水曜日
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.wednesday"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            cell_main.setBackgroundColor(color_date);
            __settingWidth(cell_main, 0.5f, 0.25f, 0.25f, 1);
            table_main.addCell(cell_main);
            //木曜日
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.thursday"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            cell_main.setBackgroundColor(color_date);
            __settingWidth(cell_main, 0.5f, 0.25f, 0.25f, 1);
            table_main.addCell(cell_main);
            //金曜日
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.friday"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            cell_main.setBackgroundColor(color_date);
            __settingWidth(cell_main, 0.5f, 0.25f, 0.25f, 1);
            table_main.addCell(cell_main);
            //土曜日
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.saturday"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_sat);
            cell_main.setBackgroundColor(color_date);
            __settingWidth(cell_main, 0.5f, 0.25f, 1, 1);
            table_main.addCell(cell_main);

            //ヘッダー固定表示設定
            table_main.setHeaderRows(2);

            for (RsvWeekModel weekMdl : pdfMdl.getRsvGekMonthList()) {
                //表示行計算
                int days = weekMdl.getYoyakuDayList().size();

                //一ヶ月に表示される週(行数)
                //うるう年かつ２月１日が日曜日：４行   その他：5行 or 6行
                int maxWeek = days / 7;

                //日付カウンタ(日曜～土曜をカウント)
                int dayCnt = 1;
                //週カウンタ
                int weekCnt = 1;

                //表示される日にち分のリスト
                for (RsvYoyakuDayModel dayMdl : weekMdl.getYoyakuDayList()) {
                    //日付のフォント 当月：太字    当月以外：細字
                    Font font_cal_date;
                    float [] width_headDate  = {0.85f, 0.15f};
                    if (dayMdl.getYrkMonthKbn() == GSConstReserve.SAME_MONTH) {
                        //当月 平日
                        if (dayMdl.getHolName() == null) {
                            font_cal_date = font_date;
                        //当月 祝日
                        } else {
                            font_cal_date = font_holiday_bold;
                        }
                    } else {
                        //当月以外 平日
                        if (dayMdl.getHolName() == null) {
                            font_cal_date = font_date_sub;
                        //当月以外 祝日
                        } else {
                            font_cal_date = font_holiday;
                        }
                    }

                    //１日の情報を表示するテーブル
                    PdfPTable dayTable =
                            PdfUtil.createTable(1, 100, totalWidth / 7, Element.ALIGN_CENTER);

                    PdfPCell dayCell;

                    //日付の設定
                    if (StringUtil.isNullZeroString(dayMdl.getYrkRokuyou())) {
                        dayCell = PdfUtil.setCellRet(
                                dayMdl.getYrkDateDay(), 1, Element.ALIGN_RIGHT, font_cal_date);
                    } else {
                        dayCell = new PdfPCell();
                        //日付 + 六曜を表示
                        PdfPTable table_headDate = new PdfPTable(2);
                        table_headDate.setWidthPercentage(100f);
                        table_headDate.setTotalWidth(totalWidth);
                        table_headDate.setWidths(width_headDate);
                        PdfPCell rokuyouCell = new PdfPCell(
                                new Phrase(dayMdl.getYrkRokuyou(), font_rokuyou));
                        rokuyouCell.setBorder(0);
                        rokuyouCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        PdfPCell dateCell = new PdfPCell(
                                new Phrase(dayMdl.getYrkDateDay(), font_cal_date));
                        dateCell.setBorder(0);
                        dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        table_headDate.addCell(rokuyouCell);
                        table_headDate.addCell(dateCell);
                        dayCell.addElement(table_headDate);
                    }
                    dayCell.setBorderWidth(0);
                    dayCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    dayCell.setVerticalAlignment(Element.ALIGN_TOP);
                    dayTable.addCell(dayCell);

                    //休日名称の設定
                    dayCell = PdfUtil.setCellRet(
                            NullDefault.getString(dayMdl.getHolName(), EMP__),
                            1, Element.ALIGN_RIGHT, font_holiday);
                    dayTable.addCell(dayCell);

                    //一日の施設予約情報リスト
                    for (RsvYoyakuModel yykMdl : dayMdl.getYoyakuList()) {
                        //利用時間
                        StringBuilder yykTitle
                        = new StringBuilder(yykMdl.getYrkRiyoDateStr() + "\n");
                        Font yykFont = font_main_blue;

                        //承認待ち
                        if (yykMdl.getRsyApprStatus()
                                == GSConstReserve.RSY_APPR_STATUS_NOAPPR) {
                            yykTitle.append("(" + gsMsg.getMessage("reserve.appr.st1") + ")");
                            yykFont = font_main_orange;

                        //否認
                        } else if (yykMdl.getRsyApprKbn()
                                == GSConstReserve.RSY_APPR_KBN_REJECTION) {
                            yykTitle.append("(" + gsMsg.getMessage("reserve.appr.st2") + ")");
                            yykFont = font_main_black;
                        }

                        //閲覧できない施設予約の目的は黒字
                        if (yykMdl.getPublic() == GSConstReserve.PUBLIC_KBN_PLANS) {
                            yykFont = font_main_black;
                        }

                        //利用目的・登録者
                        yykTitle.append(NullDefault.getString(yykMdl.getRsyMok(), ""));
                        if (!StringUtil.isNullZeroString(yykMdl.getRsyMok())
                                && !StringUtil.isNullZeroString(yykMdl.getYrkName())) {
                            yykTitle.append(" / ");
                        }
                        yykTitle.append(NullDefault.getString(yykMdl.getYrkName(), ""));

                        dayCell = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(yykTitle.toString()),
                                1, Element.ALIGN_LEFT, yykFont);
                        dayCell.setPaddingBottom(3f);
                        dayTable.addCell(dayCell);
                    }

                    cell_main = new PdfPCell(dayTable);
                    //セルの左右の線の太さを設定
                    float left = 0;
                    float right = 0;
                    if (dayCnt == 1) {
                         left = 1;
                         right = 0.25f;
                         cell_main.setBackgroundColor(color_sunday);
                    } else if (dayCnt == 7) {
                        left = 0.25f;
                        right = 1;
                        cell_main.setBackgroundColor(color_saturday);
                    } else {
                        left = 0.25f;
                        right = 0.25f;
                    }

                    //セルの上下の線の太さを設定
                    float top = 0;
                    float bottom = 0;
                    if (weekCnt == 1) {
                        top = 0;
                        bottom = 0.25f;
                    } else if (weekCnt == maxWeek) {
                        top = 0.25f;
                        bottom = 1;
                    } else {
                        top = 0.25f;
                        bottom = 0.25f;
                    }
                    __settingWidth(cell_main, top, left, right, bottom);
                    cell_main.setMinimumHeight(day_height);
                    table_main.addCell(cell_main);

                    dayCnt++;
                    if (dayCnt == 8) {
                        dayCnt = 1;
                        weekCnt++;
                    }
                }
            }

            table_title.setSplitLate(false);
            table_main.setSplitLate(false);
            pdfUtil.addCalcPaging(doc, table_title, 0);
            pdfUtil.addCalcPaging(doc, table_main, 0);

        } finally {
            doc.close();
        }

        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            //ページ番号の追記
            reader = new PdfReader(byteout.toByteArray());
            //生成されたPDFの総ページ数を取得する
            int total = reader.getNumberOfPages();
            PdfContentByte under_content;

            stamper = new PdfStamper(reader, oStream);

            UDate date = new UDate();
            for (int i = 1; i <= total; i++) {
                //GroupSessioin
                under_content = stamper.getUnderContent(i);
                under_content.beginText();
                under_content.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                under_content.moveText(20, 570);
                under_content.showText("GroupSession"
                        + "  " + gsMsg.getMessage("cmn.reserve"));
                under_content.endText();

                //ページ番号を追加する、フォント設定、位置設定
                under_content = stamper.getUnderContent(i);
                under_content.beginText();
                under_content.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                under_content.moveText(750, 570);
                under_content.showText(
                        i + "/" + total + " " + gsMsg.getMessage("cmn.pdf.page"));
                under_content.endText();

                //日付
                under_content = stamper.getUnderContent(i);
                under_content.beginText();
                under_content.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                under_content.moveText(750, 10);
                under_content.showText(
                        date.getStrYear() + "/" + date.getStrMonth() + "/" + date.getStrDay());
                under_content.endText();

            }
        } finally {
            if (stamper != null) {
                stamper.close();
            }
            reader.close();
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
    private PdfPCell __settingWidth(
            PdfPCell cell, float top, float left, float right, float bottom) {

        cell.setBorderWidthTop(top);
        cell.setBorderWidthLeft(left);
        cell.setBorderWidthRight(right);
        cell.setBorderWidthBottom(bottom);
        return cell;
    }
}