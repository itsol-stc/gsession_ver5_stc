package jp.groupsession.v2.enq.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.enq320.Enq320AnswerModel;
import jp.groupsession.v2.struts.msg.GsMessage;

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

/**
 * <br>[機  能] アンケート結果確認画面(一覧)のPDF用Modelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqListPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqListPdfUtil.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 空文字 */
    private static final String EMP__ = " ";

    /**
     * <br>[機  能] アンケート結果確認Pdf出力に関するUtilクラスです。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl pdfモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream アンケート結果データの出力先となるストリーム
     * @throws Exception 実行時例外
     * @throws FileNotFoundException 画像ファイル読み込み時例外
     * @throws DocumentException ドキュメント生成時例外
     * @author JTS
     */
    public void createEnqListPdf(
            EnqPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        /* フォントファイル */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント 通常
        Font font_base = PdfUtil.getFont8(fontPath);
        //フォント ヘッダー タイトル
        Font font_header = PdfUtil.getFont12b(fontPath);
        //フォント 基本情報 タイトル
        Font font_info_title = PdfUtil.getFont10b(fontPath);
        //フォント 基本情報 ヘッダー
        Font font_info_header = PdfUtil.getFont8b(fontPath);
        //フォント 回答 必須
        Font font_red = PdfUtil.getFont8(fontPath);
        font_red.setColor(PdfUtil.FONT_COLOR_RED);

        //背景色 薄青色
        Color color_light_blue = PdfUtil.BG_COLOR_LIGHTBLUE;
        //背景色 藍色
        Color color_naby = PdfUtil.BG_COLOR_DARKBLUE;

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //出力用のStreamをインスタンス化します。
        ByteArrayOutputStream byteout = new ByteArrayOutputStream();

        //ページサイズを設定します。
        Document doc = new Document(PageSize.A4);
        PdfWriter pdfwriter = null;

        try {
            //アウトプットストリームをPDFWriterに設定します。
            pdfwriter = PdfWriter.getInstance(doc, byteout);

            //文章の出力を開始します。
            doc.open();

            float totalWidth = 1020;
            PdfUtil cmnPdfUtil = new PdfUtil();

            /** ヘッダータイトル ***********************************************************/
            PdfPTable header_title = new PdfPTable(2);
            header_title.setWidthPercentage(100f);
            header_title.setTotalWidth(totalWidth);
            float [] width_header_title  = {0.4f, 0.6f};
            header_title.setWidths(width_header_title);
            PdfPCell cell_header_title;

            //ヘッダータイトル
            cell_header_title = new PdfPCell(new Phrase(
                    gsMsg.getMessage("enq.plugin")
                    + "["
                    + "結果確認(一覧)"
                    + "]", font_header));
            __settingWidth(cell_header_title, 0, 0, 0, 1);
            cell_header_title.setLeading(1.2f, 1.2f);
            cell_header_title.setHorizontalAlignment(Element.ALIGN_JUSTIFIED_ALL);
            header_title.addCell(cell_header_title);

            cell_header_title = new PdfPCell(new Phrase(EMP__, font_header));
            __settingWidth(cell_header_title, 0, 0, 0, 0);
            cell_header_title.setHorizontalAlignment(Element.ALIGN_LEFT);
            header_title.addCell(cell_header_title);

            //空白行
            cell_header_title = new PdfPCell(new Phrase(EMP__, font_base));
            __settingWidth(cell_header_title, 0, 0, 0, 0);
            cell_header_title.setColspan(2);
            header_title.addCell(cell_header_title);

            cmnPdfUtil.addCalcPaging(doc, header_title, 0);
            /** 基本情報 */
            EnqPdfUtil pdfUtil = new EnqPdfUtil();
            PdfPTable table_info =
            pdfUtil.enqBaseInfo(pdfMdl, appRootPath, font_base,
                    font_info_title, font_info_header, color_light_blue, doc, totalWidth);

            cmnPdfUtil.addCalcPaging(doc, table_info, 0);

            /** 対象、検索 */
            PdfPTable table_search = new PdfPTable(4);
            table_search.setWidthPercentage(100f);
            table_search.setTotalWidth(totalWidth);
            float [] width_search  = {0.05f, 0.15f, 0.1f, 0.7f};
            table_search.setWidths(width_search);
            PdfPCell cell_search;

            //対象
            cell_search = new PdfPCell(new Phrase("対象", font_info_header));
            __settingWidth(cell_search, 0, 0, 0, 0);
            cell_search.setLeading(1.2f, 1.2f);
            cell_search.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_search.addCell(cell_search);

            //TODO
            cell_search = new PdfPCell(
                    new Phrase(gsMsg.getMessage("cmn.group") + "：", font_base));
            __settingWidth(cell_search, 0, 0, 0, 0);
            cell_search.setLeading(1.2f, 1.2f);
            cell_search.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_search.addCell(cell_search);

            //TODO
            cell_search = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.user") + "：", font_base));
            __settingWidth(cell_search, 0, 0, 0, 0);
            cell_search.setLeading(1.2f, 1.2f);
            cell_search.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_search.addCell(cell_search);

          cell_search = new PdfPCell(new Phrase(EMP__, font_base));
          __settingWidth(cell_search, 0, 0, 0, 0);
          cell_search.setColspan(5);
          cell_search.setLeading(1.2f, 1.2f);
          cell_search.setHorizontalAlignment(Element.ALIGN_CENTER);
          table_search.addCell(cell_search);

            //状態
            cell_search = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.status"), font_info_header));
            __settingWidth(cell_search, 0, 0, 0, 0);
            cell_search.setLeading(1.2f, 1.2f);
            cell_search.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_search.addCell(cell_search);

            cell_search = new PdfPCell(new Phrase(pdfMdl.getStsAnswer(), font_base));
            __settingWidth(cell_search, 0, 0 , 0, 0);
            cell_search.setColspan(3);
            cell_search.setLeading(1.2f, 1.2f);
            cell_search.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_search.addCell(cell_search);

            cmnPdfUtil.addCalcPaging(doc, table_search, 0);

            /** 一覧 */
            PdfPTable table_list = new PdfPTable(4);
            table_list.setWidthPercentage(100f);
            table_list.setTotalWidth(totalWidth);
            float [] width_list  = {0.25f, 0.25f, 0.25f, 0.25f};
            table_list.setWidths(width_list);
            PdfPCell cell_list;

            //一覧 ヘッダー
            cell_list = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.status"), font_info_header));
            __settingWidth(cell_list, 1, 1, 1, 1);
            cell_list.setBackgroundColor(color_naby);
            cell_list.setLeading(1.2f, 1.2f);
            cell_list.setHorizontalAlignment(Element.ALIGN_CENTER);
            table_list.addCell(cell_list);

            cell_list = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.group"), font_info_header));
            __settingWidth(cell_list, 1, 0, 1, 1);
            cell_list.setBackgroundColor(color_naby);
            cell_list.setLeading(1.2f, 1.2f);
            cell_list.setHorizontalAlignment(Element.ALIGN_CENTER);
            table_list.addCell(cell_list);

            cell_list = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.user"), font_info_header));
            __settingWidth(cell_list, 1, 0, 1, 1);
            cell_list.setBackgroundColor(color_naby);
            cell_list.setLeading(1.2f, 1.2f);
            cell_list.setHorizontalAlignment(Element.ALIGN_CENTER);
            table_list.addCell(cell_list);

            cell_list = new PdfPCell(new Phrase("回答日", font_info_header));
            __settingWidth(cell_list, 1, 0, 1, 1);
            cell_list.setBackgroundColor(color_naby);
            cell_list.setLeading(1.2f, 1.2f);
            cell_list.setHorizontalAlignment(Element.ALIGN_CENTER);
            table_list.addCell(cell_list);

            //一覧 内容
            for (Enq320AnswerModel ansList: pdfMdl.getAnsList()) {

                //状態
                if (ansList.getStatus() == 0) {
                    cell_list = new PdfPCell(
                            new Phrase(gsMsg.getMessage("enq.21"), font_info_header));
                } else if (ansList.getStatus() == 1) {
                    cell_list = new PdfPCell(
                            new Phrase(gsMsg.getMessage("enq.22"), font_info_header));
                }
                __settingWidth(cell_list, 0, 1, 1, 1);
                cell_list.setLeading(1.2f, 1.2f);
                cell_list.setHorizontalAlignment(Element.ALIGN_CENTER);
                table_list.addCell(cell_list);

                //グループ
                cell_list = new PdfPCell(new Phrase(
                        PdfUtil.replaseBackslashToYen(ansList.getGroup()),
                        font_info_header));
                __settingWidth(cell_list, 0, 0, 1, 1);
                cell_list.setLeading(1.2f, 1.2f);
                cell_list.setHorizontalAlignment(Element.ALIGN_CENTER);
                table_list.addCell(cell_list);

                //ユーザ
                cell_list = new PdfPCell(new Phrase(
                        PdfUtil.replaseBackslashToYen(ansList.getUser()),
                        font_info_header));
                __settingWidth(cell_list, 0, 0, 1, 1);
                cell_list.setLeading(1.2f, 1.2f);
                cell_list.setHorizontalAlignment(Element.ALIGN_CENTER);
                table_list.addCell(cell_list);

                //回答日
                cell_list = new PdfPCell(new Phrase(ansList.getAnsDate(), font_info_header));
                __settingWidth(cell_list, 0, 0, 1, 1);
                cell_list.setLeading(1.2f, 1.2f);
                cell_list.setHorizontalAlignment(Element.ALIGN_CENTER);
                table_list.addCell(cell_list);
            }

            cmnPdfUtil.addCalcPaging(doc, table_list, 0);

            //書き出し開始
            doc.close();

        } catch (Exception e) {
            log__.error("PDF出力に失敗しました。");
        } finally {
            if (pdfwriter != null) {
                pdfwriter.close();
            }
            if (doc != null && doc.isOpen()) {
                doc.close();
            }
        }

        log__.debug("end");


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
                underContent.moveText(20, 820);
                underContent.showText("GroupSession"
                        + "  " + gsMsg.getMessage("enq.plugin"));
                underContent.endText();

                //アンダーコンテンツを取得する
                underContent = stamper.getUnderContent(i);
                //ページ番号を追加する、フォント設定、位置設定
                underContent.beginText();
                underContent.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                underContent.moveText(500, 820);
                underContent.showText(
                        i + "/" + total + " " + gsMsg.getMessage("cmn.pdf.page"));
                underContent.endText();

                //アンダーコンテンツを取得する
                underContent = stamper.getUnderContent(i);
                underContent.beginText();
                underContent.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                underContent.moveText(500, 10);
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
