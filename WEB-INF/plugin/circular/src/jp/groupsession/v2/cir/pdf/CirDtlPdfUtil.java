package jp.groupsession.v2.cir.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cir.GSConstCircular;
import jp.groupsession.v2.cmn.FileNameComparator;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.struts.msg.GsMessage;

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
 * <br>[機  能] 回覧板詳細PDF出力に関するユーティリティークラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirDtlPdfUtil {

    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public CirDtlPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public CirDtlPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 回覧板詳細Pdf出力に関するUtilクラスです。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl pdfモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream  回覧板詳細Pdfの出力先となるストリーム
     * @throws Exception 実行時例外
     * @throws FileNotFoundException 画像ファイル読み込み時例外
     * @throws DocumentException ドキュメント生成時例外
     * @author JTS
     */
    public void createCirDtlPdf(
            CirDtlPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        Document doc = null;

        //フォントファイルパス
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //バックカラー（回覧先確認状況テーブルヘッダ）
        Color color_memheader = PdfUtil.BG_COLOR_LIGHTGRAY;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ByteArrayOutputStream byteout = null;

        try {

            //フォント タイトル
            Font font_title = PdfUtil.getFont16b(fontPath);
            //フォント ベース
            Font font_base = PdfUtil.getFont10(fontPath);
            //フォント(メイン)
            Font font_main = PdfUtil.getFont12(fontPath);
            //フォント(改行)
            Font font_empty = PdfUtil.getFont10(fontPath);

            doc = new Document(PageSize.A4); //(842F, 595F)
            //アウトプットストリームをPDFWriterに設定します。
            byteout = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, byteout);

            //出力するPDFに説明を付与します。
            doc.addTitle(gsMsg.getMessage("cir.5"));
            doc.addAuthor("GroupSession");
            doc.open();

            //文字入力範囲（横）
            float totalWidth = doc.getPageSize().getWidth();

            //アカウント名
            PdfPTable table_acc = new PdfPTable(1);
            table_acc.setWidthPercentage(100f);
            table_acc.setTotalWidth(totalWidth);
            PdfPCell cell_acc;

            //ヘッダー
            PdfPTable table_header = new PdfPTable(2);
            table_header.setWidthPercentage(100f);
            table_header.setTotalWidth(totalWidth);
            float [] width_header  = {0.13f, 0.87f};
            table_header.setWidths(width_header);
            PdfPCell cell_header;

            //メイン
            PdfPTable table_main = new PdfPTable(1);
            table_main.setWidthPercentage(100f);
            table_main.setTotalWidth(totalWidth);
            float [] width_main  = {1.0f};
            table_main.setWidths(width_main);
            PdfPCell cell_main;

            //タイトル
            cell_acc = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfMdl.getCifTitle()),
                            EMP__), font_title));
            __settingWidth(cell_acc, 0, 0, 0, 1);
            cell_acc.setLeading(1.2f, 1.2f);
            cell_acc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_acc.setPaddingBottom(6f);
            table_acc.addCell(cell_acc);


            //空白
            cell_acc = new PdfPCell(new Phrase(EMP__, font_empty));
            cell_acc.setBorder(0);
            table_acc.addCell(cell_acc);

            //ヘッダー
            //タイトル
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.title"), font_base));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfMdl.getCifTitle()),
                            EMP__), font_base));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            //発信者
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.cirsender"), font_base));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getCacName()),
                    font_base));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            //発信日時
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.senddate"), font_base));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            cell_header = new PdfPCell(new Phrase(pdfMdl.getDspCifAdate(), font_base));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);


            //修正がある場合、修正日時
            if (pdfMdl.getCifEkbn() == GSConstCircular.CIR_EDIT) {
                cell_header = new PdfPCell(new Phrase(
                        gsMsg.getMessage("cmn.pdf.resetdate"), font_base));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);

                cell_header = new PdfPCell(new Phrase(
                        NullDefault.getString(pdfMdl.getDspCifEditDate(), EMP__), font_base));
                cell_header.setBorder(0);
                cell_header.setLeading(1.1f, 1.1f);
                cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
                table_header.addCell(cell_header);
            }

            //添付
            cell_header = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.pdf.cirtemp"), font_base));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            table_header.addCell(cell_header);

            StringBuilder attachFileName = new StringBuilder("");
            List<CmnBinfModel> binfMdlList = pdfMdl.getBinfMdlList();
            if (binfMdlList != null && binfMdlList.size() > 0) {
                attachFileName.append(binfMdlList.get(0).getBinFileName());
                attachFileName.append(binfMdlList.get(0).getBinFileSizeDsp());
                for (int idx = 1; idx < binfMdlList.size(); idx++) {
                    attachFileName.append("\r\n");
                    attachFileName.append(binfMdlList.get(idx).getBinFileName());
                    attachFileName.append(binfMdlList.get(idx).getBinFileSizeDsp());
                }
            }
            cell_header = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(attachFileName.toString()),
                            EMP__), font_base));
            cell_header.setBorder(0);
            cell_header.setLeading(1.1f, 1.1f);
            cell_header.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_header.addCell(cell_header);

            //空白行
            cell_header = new PdfPCell(new Phrase(EMP__, font_empty));
            __settingWidth(cell_header, 0, 0, 0, 1);
            cell_header.setColspan(2);
            table_header.addCell(cell_header);

            //空白行
            cell_header = new PdfPCell(new Phrase(EMP__, font_empty));
            cell_header.setBorder(0);
            cell_header.setColspan(2);
            table_header.addCell(cell_header);

            //内容
            cell_main = new PdfPCell(new Phrase(
                    NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfMdl.getCifValue()),
                            EMP__), font_main));
            cell_main.setBorder(0);
            cell_main.setLeading(1.3f, 1.3f); //行間の設定
            cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_main.addCell(cell_main);

            //空白行
            cell_main = new PdfPCell(new Phrase(EMP__, font_empty));
            cell_main.setBorder(0);
            cell_main.setColspan(2);
            table_main.addCell(cell_main);

            table_acc.setSplitLate(false);
            table_header.setSplitLate(false);
            table_main.setSplitLate(false);

            PdfUtil pdfUtil = new PdfUtil();
            pdfUtil.addCalcPaging(doc, table_acc, 0);
            pdfUtil.addCalcPaging(doc, table_header, 0);
            pdfUtil.addCalcPaging(doc, table_main, 0);

            //回覧先確認状況
            if (pdfMdl.getCirMode().equals(GSConstCircular.MODE_SOUSIN)
                    || pdfMdl.getPrivateLevel() == GSConstCircular.CIR_INIT_SAKI_PUBLIC) {

                //回覧板確認状況のヘッダを用意する
                String headerEmNaPo = gsMsg.getMessage("cmn.pdf.circell1");
                String headerCheMe = gsMsg.getMessage("rng.rng030.04");
                String headerMeTen = gsMsg.getMessage("cmn.pdf.circell2");
                String headerEdate = gsMsg.getMessage("cmn.update.day.hour");

                //回覧先確認状況を取得
                List <CirDtlPdfMemModel> memList = pdfMdl.getPdfMemList();
                //回覧先確認状況
                float [] width_status  = {0.65f, 0.2f, 0.75f, 0.2f};
                PdfPTable table_status =
                        PdfUtil.createTable(4, 100, totalWidth, width_status, Element.ALIGN_CENTER);

                //回覧先情報 ヘッダ
                PdfPCell cell_status;

                //ヘッダ 社員・職員番号/氏名/役職
                cell_status = PdfUtil.setCellRet(
                        headerEmNaPo, 1, Element.ALIGN_CENTER, font_base);
                __settingWidth(cell_status, 1, 1, 1, 1);
                cell_status.setBackgroundColor(color_memheader);
                cell_status.setLeading(1.1f, 1.1f);
                table_status.addCell(cell_status);

                //ヘッダ 確認日時
                cell_status = PdfUtil.setCellRet(
                        headerCheMe, 1, Element.ALIGN_CENTER, font_base);
                __settingWidth(cell_status, 1, 0, 1, 1);
                cell_status.setBackgroundColor(color_memheader);
                cell_status.setLeading(1.1f, 1.1f);
                table_status.addCell(cell_status);

                //ヘッダ メモ/添付
                cell_status = PdfUtil.setCellRet(
                        headerMeTen, 1, Element.ALIGN_CENTER, font_base);
                __settingWidth(cell_status, 1, 0, 1, 1);
                cell_status.setBackgroundColor(color_memheader);
                cell_status.setLeading(1.1f, 1.1f);
                table_status.addCell(cell_status);

                //ヘッダ 更新日時
                cell_status = PdfUtil.setCellRet(
                        headerEdate, 1,
                        Element.ALIGN_CENTER, font_base);
                __settingWidth(cell_status, 1, 0, 1, 1);
                cell_status.setBackgroundColor(color_memheader);
                cell_status.setLeading(1.1f, 1.1f);
                table_status.addCell(cell_status);

                //ヘッダー固定表示設定(回覧先がないグループ選択時はヘッダのみ表示)
                if (memList.size() != 0) {
                    table_status.setHeaderRows(1);
                }
                //確認一覧表示
                for (CirDtlPdfMemModel memDspMdl : memList) {
                    StringBuilder name = new StringBuilder();
                    if (!StringUtil.isNullZeroString(memDspMdl.getSyainNo())) {
                        name.append(memDspMdl.getSyainNo());
                        name.append("\n");
                    }
                    name.append(memDspMdl.getCacName());
                    if (!StringUtil.isNullZeroString(memDspMdl.getPosName())) {
                        name.append("\n");
                        name.append(memDspMdl.getPosName());
                    }

                    //社員・職員番号/氏名/役職
                    cell_status = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(name.toString()), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_base);
                    __settingWidth(cell_status, 0, 1, 1, 1);
                    cell_status.setLeading(1.1f, 1.1f);
                    cell_status.setPaddingBottom(4f);
                    table_status.addCell(cell_status);
                    //確認日時
                    String cvwEdate = memDspMdl.getOpenTime();
                    cell_status = PdfUtil.setCellRet(
                            cvwEdate, 1,
                            Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_base);
                    __settingWidth(cell_status, 0, 0, 1, 1);
                    cell_status.setLeading(1.1f, 1.1f);
                    cell_status.setPaddingBottom(4f);
                    table_status.addCell(cell_status);

                    //メモ/添付ファイル
                    StringBuilder memFileName = new StringBuilder(memDspMdl.getCvwMemo());
                    memFileName.append("\n");
                    List<CmnBinfModel> memBinfMdlList =
                            pdfMdl.getMemFileNameList().get(memDspMdl.getCacSid());
                    if (memBinfMdlList != null && memBinfMdlList.size() > 0) {
                        Collections.sort(memBinfMdlList, new FileNameComparator());
                        memFileName.append(
                                NullDefault.getString(memBinfMdlList.get(0).getBinFileName(), ""));
                        for (int idx = 1; idx < memBinfMdlList.size(); idx++) {
                            memFileName.append("\r\n");
                            memFileName.append(NullDefault.getString(
                                    memBinfMdlList.get(idx).getBinFileName(), ""));
                        }
                    }
                    cell_status = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(memFileName.toString()), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_base);
                    __settingWidth(cell_status, 0, 0, 1, 1);
                    cell_status.setLeading(1.1f, 1.1f);
                    cell_status.setPaddingBottom(4f);
                    table_status.addCell(cell_status);

                    //更新日時
                    String dspCvwEdate = "";
                    if (memDspMdl.getCvwConf() == GSConstCircular.CONF_OPEN
                            && !memDspMdl.getOpenTime().equals(memDspMdl.getDspCvwEdate())) {
                        dspCvwEdate = memDspMdl.getDspCvwEdate();
                    }
                    cell_status = PdfUtil.setCellRet(
                            dspCvwEdate, 1,
                            Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_base);
                    __settingWidth(cell_status, 0, 0, 1, 1);
                    cell_status.setLeading(1.1f, 1.1f);
                    cell_status.setPaddingBottom(4f);
                    table_status.addCell(cell_status);
                }
                table_status.setSplitLate(false);
                pdfUtil.addCalcPaging(doc, table_status, 0);

            }

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
                underContent.moveText(20, 820);
                underContent.showText("GroupSession" + "  " + gsMsg.getMessage("cir.5"));
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

                //アンダーコンテンツを取得する //(595F,842F)
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
            if (reader != null) {
                reader.close();
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
    private PdfPCell __settingWidth(
            PdfPCell cell, float top, float left, float right, float bottom) {

        cell.setBorderWidthTop(top);
        cell.setBorderWidthLeft(left);
        cell.setBorderWidthRight(right);
        cell.setBorderWidthBottom(bottom);
        return cell;
    }

}