package jp.groupsession.v2.sch.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

/**
 * <br>[機  能] スケジュール一覧Pdf出力に関するUtilクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchListPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchListPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public SchListPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public SchListPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] スケジュール一覧Pdf出力に関するUtilクラスです。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl pdfモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream スケジュール一覧データの出力先となるストリーム
     * @throws Exception 実行例外
     * @throws FileNotFoundException 実行例外
     * @throws DocumentException 実行例外
     * @author JTS
     */
    public void createSchListPdf(
            SchListPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        log__.debug("スケジュール一覧PDFの生成開始");

        Document doc = null;

        /* フォントファイルパス */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント アカウント名
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント ヘッダー
        Font font_header = PdfUtil.getFont10b(fontPath);
        //フォント メイン 10pt 黒
        Font font_main = PdfUtil.getFont10(fontPath);
        //フォント(スケジュール内容 予定あり)
        Font font_main_yotei = PdfUtil.getFont10b(fontPath);

        //バックカラー（ヘッダ）
        Color color_header = PdfUtil.BG_COLOR_DARKBLUE;
        //バックカラー（検索条件 項目タイトル）
        Color color_search = PdfUtil.BG_COLOR_LIGHTBLUE;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ByteArrayOutputStream byteout = null;

        try {
            doc = new Document(PageSize.A4.rotate()); //(842F, 595F)
            //アウトプットストリームをPDFWriterに設定します。
            byteout = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, byteout);

            //出力するPDFに説明を付与します。
            doc.addTitle(gsMsg.getMessage("schedule.108"));
            doc.addAuthor("GroupSession");
            doc.addSubject(gsMsg.getMessage("cmn.list"));
            doc.open();

            //文字入力範囲（横）
            float totalWidth = doc.getPageSize().getWidth();

            //タイトル
            float [] width_title  = {0.25f, 0.75f};
            PdfPTable table_title =
                    PdfUtil.createTable(2, 100, totalWidth, width_title, Element.ALIGN_CENTER);

            //検索条件
            float [] width_search  = {0.12f, 0.38f, 0.12f, 0.38f};
            PdfPTable table_search =
                    PdfUtil.createTable(4, 100, totalWidth, width_search, Element.ALIGN_CENTER);

            //スケジュール一覧
            float [] width_main  = {0.15f, 0.15f, 0.15f, 0.55f};
            PdfPTable table_main =
                    PdfUtil.createTable(4, 100, totalWidth, width_main, Element.ALIGN_CENTER);

            //タイトル
            String topTitle = gsMsg.getMessage("schedule.108")
                    + "[ "
                    + gsMsg.getMessage("cmn.list")
                    + " ]";
            PdfPCell cell_title = PdfUtil.setCellRet(
                    topTitle, 1, Element.ALIGN_JUSTIFIED_ALL, font_title);
            __settingWidth(cell_title, 0, 0, 0, 1);
            cell_title.setLeading(1.2f, 1.2f); //行間の設定
            cell_title.setPaddingBottom(5f);
            table_title.addCell(cell_title);

            PdfUtil.setCell(table_title, EMP__, 1, Element.ALIGN_LEFT,
                            PdfUtil.getFontEmpty(fontPath));

            //空白
            PdfUtil.setCell(table_title, EMP__, 2, Element.ALIGN_LEFT,
                            PdfUtil.getFontEmpty(fontPath));

            PdfPCell cell_search;

            //検索条件 ヘッダー
            cell_search = PdfUtil.setCellRet("  " + gsMsg.getMessage("cmn.search.criteria"), 4,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 1, 1, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_header);
            table_search.addCell(cell_search);

            //検索条件 グループ・ユーザ
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("schedule.sch100.4"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 1, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            cell_search.setPaddingBottom(4f);
            table_search.addCell(cell_search);

            String strGrpUsr = StringUtilHtml.replaceString(pdfMdl.getPdfGroup(), "　", "")
                    + "   " + pdfMdl.getPdfUser();
            strGrpUsr = PdfUtil.replaseBackslashToYen(strGrpUsr);
            cell_search = PdfUtil.setCellRet(strGrpUsr, 3,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_search, 1, 0.5f, 1, 0);
            cell_search.setLeading(1.1f, 1.1f);
            table_search.addCell(cell_search);

            //検索条件 開始日
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("schedule.sch100.10"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 1, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            table_search.addCell(cell_search);

            cell_search = PdfUtil.setCellRet(pdfMdl.getPdfStDate(), 3,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_search, 1, 0.5f, 1, 0);
            cell_search.setLeading(1.1f, 1.1f);
            table_search.addCell(cell_search);

            //検索条件 終了日
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("schedule.sch100.15"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 1, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            table_search.addCell(cell_search);

            cell_search = PdfUtil.setCellRet(pdfMdl.getPdfEdDate(), 3,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_search, 1, 0.5f, 1, 0);
            cell_search.setLeading(1.1f, 1.1f);
            table_search.addCell(cell_search);

            //検索条件 キーワード
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("cmn.keyword"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 1, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            cell_search.setPaddingBottom(4f);
            table_search.addCell(cell_search);

            String keyward = pdfMdl.getPdfKeyWord();
            if (0 < keyward.length()) {
                keyward = PdfUtil.replaseBackslashToYen(keyward);
                if (pdfMdl.getPdfKeyWordKbn() == 0) {
                    keyward += " (AND)";
                } else {
                    keyward += " (OR)";
                }
            }

            cell_search = PdfUtil.setCellRet(keyward, 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_search, 1, 0.5f, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setPaddingBottom(4f);
            table_search.addCell(cell_search);

            //検索条件 検索対象
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("cmn.search2"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 0.5f, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            table_search.addCell(cell_search);

            String strTar = new String();
            if (pdfMdl.isPdfTarKen()) {
                strTar += gsMsg.getMessage("cmn.subject");
            }

            if (pdfMdl.isPdfTarHon()) {
                if (0 < strTar.length()) {
                    strTar += " ・ ";
                }
                strTar += gsMsg.getMessage("cmn.body");
            }

            cell_search = PdfUtil.setCellRet(strTar, 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_search, 1, 0.5f, 1f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setPaddingBottom(4f);
            table_search.addCell(cell_search);

            //検索条件 タイトル色
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("schedule.10"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 1, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            table_search.addCell(cell_search);

            //5色をまとめるテーブル(6番目はインデント調整用)
            float [] width_colors  = {0.1f, 0.1f, 0.1f, 0.1f, 0.1f, 0.5f};
            PdfPTable table_colors = new PdfPTable(6);
            table_colors.setWidthPercentage(100);
            table_colors.setTotalWidth(totalWidth * 0.88f);
            table_colors.setWidths(width_colors);
            table_colors.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cell_colors;

            //選択した色
            for (String colorIndex : pdfMdl.getPdfColor()) {
                PdfPTable table_color = new PdfPTable(2);
                float [] width_color  = {0.1f, 0.5f};
                table_color.setWidthPercentage(100);
                table_color.setWidths(width_color);
                table_color.setHorizontalAlignment(Element.ALIGN_LEFT);

                int index = Integer.valueOf(colorIndex);
                Font fontCol = __getFontColor(index, fontPath);
                PdfUtil.setCell(
                        table_color, "■", 1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontCol);

                String colorTitle = null;
                if (pdfMdl.getPdfColorMsg() != null && pdfMdl.getPdfColorMsg().length > 0) {
                    colorTitle = pdfMdl.getPdfColorMsg()[index - 1];
                }

                PdfUtil.setCell(
                        table_color,
                        NullDefault.getStringZeroLength(colorTitle, EMP__),
                        1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);

                cell_colors = new PdfPCell(table_color);
                cell_colors.setBorder(Rectangle.NO_BORDER);
                cell_colors.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_colors.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell_colors.setBorder(0);
                table_colors.addCell(cell_colors);
            }

            int size = pdfMdl.getPdfColor().length;
            //空白セル
            if (size < 5) {
                for (int i = 0; i < (5 - size); i++) {
                    PdfPTable table_color = new PdfPTable(2);
                    float [] width_color  = {0.1f, 0.5f};
                    table_color.setWidthPercentage(100);
                    table_color.setWidths(width_color);
                    table_color.setHorizontalAlignment(Element.ALIGN_LEFT);

                    PdfUtil.setCell(
                            table_color, EMP__, 2,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE,
                            PdfUtil.getFontEmpty(fontPath));

                    cell_colors = new PdfPCell(table_color);
                    cell_colors.setBorder(Rectangle.NO_BORDER);
                    cell_colors.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell_colors.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell_colors.setBorder(0);
                    table_colors.addCell(cell_colors);
                }
            }

            //インデント調整用テーブル
            PdfPTable table_color = new PdfPTable(1);
            table_color.setWidthPercentage(100);
            table_color.setHorizontalAlignment(Element.ALIGN_LEFT);

            PdfUtil.setCell(
                    table_color, EMP__, 2,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE,
                    PdfUtil.getFontEmpty(fontPath));

            cell_colors = new PdfPCell(table_color);
            cell_colors.setBorder(Rectangle.NO_BORDER);
            cell_colors.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_colors.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_colors.setBorder(0);
            table_colors.addCell(cell_colors);

            cell_search = new PdfPCell(table_colors);
            cell_search.setBorder(Rectangle.NO_BORDER);
            cell_search.setColspan(3);
            cell_search.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_search.setVerticalAlignment(Element.ALIGN_MIDDLE);
            __settingWidth(cell_search, 1, 0.5f, 1, 0);
            table_search.addCell(cell_search);

            //検索条件 ソート順
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("cmn.sort.order"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 1, 0.5f, 1);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            table_search.addCell(cell_search);

            String strSort = gsMsg.getMessage("cmn.first.key")
                    + "：" + pdfMdl.getPdfSortKey1() + " ";

            if (pdfMdl.getPdfOrderKey1() ==  0) {
                strSort += "(" + gsMsg.getMessage("cmn.order.asc") + ")";
            } else {
                strSort += "(" + gsMsg.getMessage("cmn.order.desc") + ")";
            }

            strSort += "          ";
            strSort += gsMsg.getMessage("cmn.second.key") + "：" + pdfMdl.getPdfSortKey2() + " ";

            if (pdfMdl.getPdfOrderKey2() ==  0) {
                strSort += "(" + gsMsg.getMessage("cmn.order.asc") + ")";
            } else {
                strSort += "(" + gsMsg.getMessage("cmn.order.desc") + ")";
            }

            cell_search = PdfUtil.setCellRet(strSort, 3,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_search, 1, 0.5f, 1f, 1);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setPaddingBottom(4f);
            table_search.addCell(cell_search);

            cell_search = PdfUtil.setCellRet(EMP__, 4, Element.ALIGN_LEFT,
                    PdfUtil.getFontEmpty(fontPath));
            cell_search.setFixedHeight(10);
            table_search.addCell(cell_search);

            //検索結果 スケジュール一覧
            PdfPCell cell_main;
            //ヘッダ 名前
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.name4"), 1, Element.ALIGN_CENTER, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 1);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);
            //ヘッダ 開始日時
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("schedule.sch100.11"), 1, Element.ALIGN_CENTER, font_header);
            __settingWidth(cell_main, 1, 0.5f, 0.5f, 1);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);
            //ヘッダ 終了日時
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("schedule.sch100.16"), 1, Element.ALIGN_CENTER, font_header);
            __settingWidth(cell_main, 1, 0.5f, 0.5f, 1);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);
            //ヘッダ タイトル/内容
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("schedule.sch100.7"), 1, Element.ALIGN_CENTER, font_header);
            __settingWidth(cell_main, 1, 0.5f, 0.5f, 1);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);

            //ヘッダー固定表示設定
            table_main.setHeaderRows(1);

            //スケジュールデータ表示
            for (SimpleScheduleModel schMdl : pdfMdl.getPdfScheduleList()) {
                //名前
                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(schMdl.getUserName()), 1,
                        Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                __settingWidth(cell_main, 0, 1, 0.5f, 1);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);

                //開始日時
                cell_main = PdfUtil.setCellRet(
                        schMdl.getFromDateStr(), 1,
                        Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_main);
                __settingWidth(cell_main, 0, 1, 0.5f, 1);
                table_main.addCell(cell_main);

                //終了日時
                cell_main = PdfUtil.setCellRet(
                        schMdl.getToDateStr(), 1,
                        Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_main);
                __settingWidth(cell_main, 0, 1, 0.5f, 1);
                table_main.addCell(cell_main);

                //タイトル・内容
                PdfPTable table_body =
                        PdfUtil.createTable(1, 100, totalWidth * 0.55f, Element.ALIGN_LEFT);
                PdfPCell cell_body;

                Font titleFont;
                if (schMdl.getPublic() == GSConstSchedule.DSP_YOTEIARI) {
                    titleFont = font_main_yotei;
                } else {
                    titleFont = __getFontColor(schMdl.getBgColor(), fontPath);;
                }

                //タイトル
                cell_body = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(schMdl.getTitle()),
                        1, Element.ALIGN_LEFT, Element.ALIGN_TOP, titleFont);

                table_body.addCell(cell_body);

                //内容
                if (schMdl.getPublic() != GSConstSchedule.DSP_YOTEIARI) {
                    String naiyo = StringUtilHtml.transToText(schMdl.getValueStr());
                    naiyo = PdfUtil.replaseBackslashToYen(naiyo);
                    cell_body = PdfUtil.setCellRet(
                    naiyo,
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
                    table_body.addCell(cell_body);
                }
                cell_main = new PdfPCell(table_body);
                cell_main.setColspan(1);
                cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
                __settingWidth(cell_main, 0, 1, 0.5f, 1);
                cell_main.setPaddingBottom(4f);

                table_main.addCell(cell_main);
            }

            table_title.setSplitLate(false);
            table_search.setSplitLate(false);
            table_main.setSplitLate(false);
            PdfUtil pdfUtil = new PdfUtil();
            pdfUtil.addCalcPaging(doc, table_title, 0);
            pdfUtil.addCalcPaging(doc, table_search, 0);
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
            PdfContentByte underContent;
            stamper = new PdfStamper(reader, oStream);

            UDate date = new UDate();
            for (int i = 1; i <= total; i++) {

                //GroupSessioin
                underContent = stamper.getUnderContent(i);
                underContent.beginText();
                underContent.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                underContent.moveText(20, 570);
                underContent.showText("GroupSession");
                underContent.endText();

                //アンダーコンテンツを取得する
                underContent = stamper.getUnderContent(i);
                //ページ番号を追加する、フォント設定、位置設定
                underContent.beginText();
                underContent.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                underContent.moveText(750, 570);
                underContent.showText(
                        i + "/" + total + " " + gsMsg.getMessage("cmn.pdf.page"));
                underContent.endText();

                //アンダーコンテンツを取得する //(595F,842F)
                underContent = stamper.getUnderContent(i);
                underContent.beginText();
                underContent.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                underContent.moveText(750, 10);
                underContent.showText(
                        date.getStrYear() + "/" + date.getStrMonth() + "/" + date.getStrDay());
                underContent.endText();
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

    /**
     * <br>[機  能] 選択した色のカラーフォントを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param colorIndex カラー番号
     * @param fontPath フォントパス
     * @throws Exception 実行例外
     * @return フォント
     */
    private Font __getFontColor(int colorIndex, String fontPath) throws Exception {
        Font ret = null;

        //フォント(スケジュール内容 青)
        Font font_main_blue = PdfUtil.getFont10b(fontPath);
        font_main_blue.setColor(PdfUtil.FONT_COLOR_BLUE);
        //フォント(スケジュール内容 赤)
        Font font_main_red = PdfUtil.getFont10b(fontPath);
        font_main_red.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント(スケジュール内容 緑)
        Font font_main_green = PdfUtil.getFont10b(fontPath);
        font_main_green.setColor(PdfUtil.FONT_COLOR_GREEN);
        //フォント(スケジュール内容 オレンジ)
        Font font_main_orange = PdfUtil.getFont10b(fontPath);
        font_main_orange.setColor(PdfUtil.FONT_COLOR_ORANGE);
        //フォント(スケジュール内容 黒)
        Font font_main_black = PdfUtil.getFont10b(fontPath);
        //フォント(スケジュール内容 紺)
        Font font_main_navy = PdfUtil.getFont10b(fontPath);
        font_main_navy.setColor(PdfUtil.FONT_COLOR_NAVY);
        //フォント(スケジュール内容 茶)
        Font font_main_maroon = PdfUtil.getFont10b(fontPath);
        font_main_maroon.setColor(PdfUtil.FONT_COLOR_MAROON);
        //フォント(スケジュール内容 青緑)
        Font font_main_teal = PdfUtil.getFont10b(fontPath);
        font_main_teal.setColor(PdfUtil.FONT_COLOR_CYAN);
        //フォント(スケジュール内容 灰)
        Font font_main_gray = PdfUtil.getFont10b(fontPath);
        font_main_gray.setColor(PdfUtil.FONT_COLOR_GRAY);
        //フォント(スケジュール内容 水色)
        Font font_main_lblue = PdfUtil.getFont10b(fontPath);
        font_main_lblue.setColor(PdfUtil.FONT_COLOR_AQUA);

        switch (colorIndex) {
        case GSConstSchedule.BGCOLOR_BLUE:
            ret = font_main_blue;
            break;
        case GSConstSchedule.BGCOLOR_RED:
            ret = font_main_red;
            break;
        case GSConstSchedule.BGCOLOR_GREEN:
            ret = font_main_green;
            break;
        case GSConstSchedule.BGCOLOR_ORANGE:
            ret = font_main_orange;
            break;
        case GSConstSchedule.BGCOLOR_BLACK:
            ret = font_main_black;
            break;
        case GSConstSchedule.BGCOLOR_NAVY :
            ret = font_main_navy;
            break;
        case GSConstSchedule.BGCOLOR_MAROON :
            ret = font_main_maroon;
            break;
        case GSConstSchedule.BGCOLOR_TEAL :
            ret = font_main_teal;
            break;
        case GSConstSchedule.BGCOLOR_GRAY :
            ret = font_main_gray;
            break;
        case GSConstSchedule.BGCOLOR_LBLUE :
            ret = font_main_lblue;
            break;
        default:
            ret = font_main_blue;
            break;
        }

        return ret;
    }

}