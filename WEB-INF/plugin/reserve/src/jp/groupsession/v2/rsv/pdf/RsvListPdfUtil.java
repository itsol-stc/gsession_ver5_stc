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
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.rsv100.Rsv100SisYrkModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設利用状況照会Pdf出力に関するUtilクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvListPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvListPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RsvListPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public RsvListPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 施設利用状況照会Pdf出力に関するUtilクラスです。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl pdfモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream 施設利用状況照会データの出力先となるストリーム
     * @throws Exception 実行例外
     * @throws FileNotFoundException 実行例外
     * @throws DocumentException 実行例外
     * @author JTS
     */
    public void createRsvListPdf(
            RsvListPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        log__.debug("施設利用状況照会PDFの生成開始");

        Document doc = null;

        /* フォントファイルパス */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント アカウント名
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント ヘッダー
        Font font_header = PdfUtil.getFont10b(fontPath);
        //フォント メイン 10pt 黒
        Font font_main = PdfUtil.getFont10(fontPath);
        //フォント 利用目的 10pt 黒 施設情報 or 却下
        Font font_cont_black = PdfUtil.getFont10b(fontPath);
        //フォント 利用目的 10pt 青
        Font font_cont_blue = PdfUtil.getFont10b(fontPath);
        font_cont_blue.setColor(PdfUtil.FONT_COLOR_BLUE);
        //フォント 利用目的 承認待ち 10pt オレンジ
        Font font_cont_orange = PdfUtil.getFont10b(fontPath);
        font_cont_orange.setColor(PdfUtil.FONT_COLOR_ORANGE);

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
            PdfUtil pdfUtil = new PdfUtil();
            //出力するPDFに説明を付与します。
            doc.addTitle(gsMsg.getMessage("cmn.reserve"));
            doc.addAuthor("GroupSession");
            doc.addSubject(gsMsg.getMessage("reserve.rsv100.1"));
            doc.open();

            //文字入力範囲（横）
            float totalWidth = 920;

            //タイトル
            float [] width_title  = {0.3f, 0.7f};
            PdfPTable table_title =
                    PdfUtil.createTable(2, 100, totalWidth, width_title, Element.ALIGN_CENTER);

            //検索条件
            float [] width_search  = {0.12f, 0.38f, 0.12f, 0.38f};
            PdfPTable table_search =
                    PdfUtil.createTable(4, 100, totalWidth, width_search, Element.ALIGN_CENTER);

            //施設予約一覧
            float [] width_main  = {0.1f, 0.2f, 0.15f, 0.15f, 0.4f};
            PdfPTable table_main =
                    PdfUtil.createTable(5, 100, totalWidth, width_main, Element.ALIGN_CENTER);

            //施設予約表示モード
            String topTitle = gsMsg.getMessage("cmn.reserve")
                    + "[ "
                    + gsMsg.getMessage("reserve.rsv100.1")
                    + " ]";
            PdfPCell cell_title = PdfUtil.setCellRet(
                    topTitle, 0, Element.ALIGN_JUSTIFIED_ALL, font_title);
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

            //検索条件 期間
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("cmn.period"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 1, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            table_search.addCell(cell_search);

            int dateDspKbn = pdfMdl.getPdfDateKbn();
            String strDate;
            if (dateDspKbn == 1) {
                strDate = gsMsg.getMessage("cmn.without.specifying");
            } else {
                strDate = pdfMdl.getPdfFromDate() + " ～ " + pdfMdl.getPdfToDate();
            }

            cell_search = PdfUtil.setCellRet(strDate, 3,
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
            table_search.addCell(cell_search);

            String keyward = pdfMdl.getPdfKeyWord();
            if (0 < keyward.length()) {
                if (pdfMdl.getPdfKeyWordKbn() == 0) {
                    keyward += " (AND)";
                } else {
                    keyward += " (OR)";
                }
                keyward = PdfUtil.replaseBackslashToYen(keyward);
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
            if (pdfMdl.getPdfSearchTarget1() == 1) {
                strTar += gsMsg.getMessage("reserve.72");
            }

            if (pdfMdl.getPdfSearchTarget2() == 1) {
                if (0 < strTar.length()) {
                    strTar += " ・ ";
                }
                strTar += gsMsg.getMessage("cmn.content");
            }

            cell_search = PdfUtil.setCellRet(strTar, 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_search, 1, 0.5f, 1f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            table_search.addCell(cell_search);

            //検索条件 グループ
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("cmn.group"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 1, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            table_search.addCell(cell_search);

            String strGrp = gsMsg.getMessage("cmn.group") + "：" + pdfMdl.getPdfSelectGrp() + "\n"
                    + gsMsg.getMessage("cmn.facility") + "　　" + "：" + pdfMdl.getPdfSelectSisetsu();
            strGrp = PdfUtil.replaseBackslashToYen(strGrp);
            cell_search = PdfUtil.setCellRet(strGrp, 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_search, 1, 0.5f, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setPaddingBottom(4f);
            table_search.addCell(cell_search);

            //検索条件 承認状況
            cell_search = PdfUtil.setCellRet(gsMsg.getMessage("cmn.appr.status"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_search, 1, 0.5f, 0.5f, 0);
            cell_search.setLeading(1.1f, 1.1f);
            cell_search.setBackgroundColor(color_search);
            table_search.addCell(cell_search);

            String strJokyo = new String();
            switch (pdfMdl.getPdfSyoninKbn()) {
            case GSConstReserve.SRH_APPRSTATUS_ALL :
                strJokyo = gsMsg.getMessage("reserve.rsv100.appr.status1");
                break;
            case GSConstReserve.SRH_APPRSTATUS_NORMAL :
                strJokyo = gsMsg.getMessage("reserve.rsv100.appr.status2");
                break;
            case GSConstReserve.SRH_APPRSTATUS_NOAPPR :
                strJokyo = gsMsg.getMessage("reserve.rsv100.appr.status3");
                break;
            case GSConstReserve.SRH_APPRSTATUS_APPRONLY :
                strJokyo = gsMsg.getMessage("reserve.rsv100.appr.status4");
                break;
            default :
                strJokyo = "";
            }

            cell_search = PdfUtil.setCellRet(strJokyo, 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_search, 1, 0.5f, 1f, 0);
            cell_search.setLeading(1.1f, 1.1f);
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
            cell_search.setPaddingBottom(3f);
            table_search.addCell(cell_search);

            cell_search = PdfUtil.setCellRet(EMP__, 4, Element.ALIGN_LEFT,
                    PdfUtil.getFontEmpty(fontPath));
            cell_search.setPaddingBottom(4f);
            table_search.addCell(cell_search);

            //検索結果 施設予約一覧
            PdfPCell cell_main;
            //ヘッダ
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("reserve.73"), 1, Element.ALIGN_CENTER, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 1);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.facility"), 1, Element.ALIGN_CENTER, font_header);
            __settingWidth(cell_main, 1, 0.5f, 0.5f, 1);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("reserve.rsv100.14"), 1, Element.ALIGN_CENTER, font_header);
            __settingWidth(cell_main, 1, 0.5f, 0.5f, 1);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("reserve.rsv100.15"), 1, Element.ALIGN_CENTER, font_header);
            __settingWidth(cell_main, 1, 0.5f, 0.5f, 1);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("reserve.72"), 1, Element.ALIGN_CENTER, font_header);
            __settingWidth(cell_main, 1, 0.5f, 1, 1);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            //ヘッダー固定表示設定
            table_main.setHeaderRows(1);

            //施設予約データ
            for (Rsv100SisYrkModel sisMdl : pdfMdl.getRsvDataList()) {

                //名前
                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(sisMdl.getRsySeiMei()),
                        1, Element.ALIGN_CENTER, font_main);
                __settingWidth(cell_main, 0, 1, 0.5f, 1);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);

                //施設名
                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(sisMdl.getRsySisetu()),
                        1, Element.ALIGN_LEFT, font_main);
                __settingWidth(cell_main, 0, 0.5f, 0.5f, 1);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);

                String strFrDate = sisMdl.getRsyFrom() + " " + sisMdl.getRsyFromTime();
                cell_main = PdfUtil.setCellRet(strFrDate, 1, Element.ALIGN_CENTER, font_main);
                __settingWidth(cell_main, 0, 0.5f, 0.5f, 1);
                table_main.addCell(cell_main);

                String strToDate = sisMdl.getRsyTo() + " " + sisMdl.getRsyToTime();
                cell_main = PdfUtil.setCellRet(strToDate, 1, Element.ALIGN_CENTER, font_main);
                __settingWidth(cell_main, 0, 0.5f, 0.5f, 1);
                table_main.addCell(cell_main);

                String strCont;
                Font contColor = font_cont_blue;

                //承認待ち
                if (sisMdl.getRsyApprStatus() == GSConstReserve.RSY_APPR_STATUS_NOAPPR) {
                    strCont = "(" + gsMsg.getMessage("reserve.appr.st1") + ")"
                            + sisMdl.getRsyContent();
                    strCont = PdfUtil.replaseBackslashToYen(strCont);
                  //閲覧できない施設予約の目的は黒字
                    if (sisMdl.getPublic() == GSConstReserve.PUBLIC_KBN_PLANS) {
                        contColor = font_cont_black;
                    } else {
                        contColor = font_cont_orange;
                    }
                    cell_main = PdfUtil.setCellRet(strCont, 1, Element.ALIGN_LEFT, contColor);
                    __settingWidth(cell_main, 0, 0.5f, 1, 1);
                    cell_main.setPaddingBottom(4f);
                    table_main.addCell(cell_main);

                //否認
                } else if (sisMdl.getRsyApprKbn() == GSConstReserve.RSY_APPR_KBN_REJECTION) {
                    strCont = "(" + gsMsg.getMessage("reserve.appr.st2") + ")"
                            + sisMdl.getRsyContent();
                    strCont = PdfUtil.replaseBackslashToYen(strCont);
                    cell_main = PdfUtil.setCellRet(strCont, 1, Element.ALIGN_LEFT, font_cont_black);
                    __settingWidth(cell_main, 0, 0.5f, 1, 1);
                    cell_main.setPaddingBottom(4f);
                    table_main.addCell(cell_main);

                } else {
                    strCont = sisMdl.getRsyContent();
                    strCont = PdfUtil.replaseBackslashToYen(strCont);
                  //閲覧できない施設予約の目的は黒字
                    if (sisMdl.getPublic() == GSConstReserve.PUBLIC_KBN_PLANS) {
                        contColor = font_cont_black;
                    } else {
                        contColor = font_cont_blue;
                    }
                    cell_main = PdfUtil.setCellRet(strCont, 1, Element.ALIGN_LEFT, contColor);
                    __settingWidth(cell_main, 0, 0.5f, 1, 1);
                    cell_main.setPaddingBottom(4f);
                    table_main.addCell(cell_main);
                }
            }

            table_title.setSplitLate(false);
            table_search.setSplitLate(false);
            table_main.setSplitLate(false);

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
}
