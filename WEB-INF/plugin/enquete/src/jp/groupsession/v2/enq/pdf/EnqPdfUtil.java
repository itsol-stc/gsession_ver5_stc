package jp.groupsession.v2.enq.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.enq.GSConstEnquete;
import jp.groupsession.v2.enq.enq310.Enq310QuestionModel;
import jp.groupsession.v2.enq.enq310.Enq310QuestionSubModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.BadElementException;
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
 * <br>[機  能] アンケート結果確認画面のPDF用Utilクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(EnqPdfUtil.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 空文字 */
    private static final String EMP__ = " ";

    /**
     * <br>[機  能] アンケート結果確認画面のPDFを作成します。
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
    public void createEnqPdf(
            EnqPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        /* フォントファイル */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント 通常
        Font font_base = PdfUtil.getFont8(fontPath);
        //フォント アンケートタイトル
        Font font_enq_title = PdfUtil.getFont16b(fontPath);
        //フォント ヘッダー タイトル
        Font font_header = PdfUtil.getFont12b(fontPath);
        //フォント 基本情報 タイトル
        Font font_info_title = PdfUtil.getFont10b(fontPath);
        //フォント 基本情報 ヘッダー
        Font font_info_header = PdfUtil.getFont8b(fontPath);

        //背景色 薄灰色
        Color color_light_gray = PdfUtil.BG_COLOR_LIGHTGRAY;

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //出力用のStreamをインスタンス化します。
        ByteArrayOutputStream byteout = new ByteArrayOutputStream();

        //ページサイズを設定します。
        Document doc = new Document(PageSize.A4);
        PdfWriter pdfwriter = null;
        PdfUtil pdfUtil = new PdfUtil();
        try {
            //アウトプットストリームをPDFWriterに設定します。
            pdfwriter = PdfWriter.getInstance(doc, byteout);

            //文章の出力を開始します。
            doc.open();

            float totalWidth = 1020;

            /** タイトル */
            PdfPTable header_title = new PdfPTable(1);
            header_title.setWidthPercentage(100f);
            header_title.setTotalWidth(totalWidth);
            float [] width_header_title  = {1};
            header_title.setWidths(width_header_title);
            PdfPCell cell_header_title;

            cell_header_title = new PdfPCell(
                    new Phrase(
                            PdfUtil.replaseBackslashToYen(pdfMdl.getEnq310enqTitle()),
                            font_enq_title));
            __settingWidth(cell_header_title, 0, 0, 0, 0);
            cell_header_title.setLeading(1.2f, 1.2f);
            header_title.addCell(cell_header_title);

            //空白行
            cell_header_title = new PdfPCell(new Phrase(EMP__, font_base));
            __settingWidth(cell_header_title, 0, 0, 0, 0);
            cell_header_title.setColspan(1);
            header_title.addCell(cell_header_title);

            pdfUtil.addCalcPaging(doc, header_title, 0);

            /** 基本情報 */
            PdfPTable table_info =
            enqBaseInfo(pdfMdl, appRootPath, font_base, font_info_title, font_info_header,
                    color_light_gray, doc, totalWidth);

            pdfUtil.addCalcPaging(doc, table_info, 0);

            /** 設問情報 */
            PdfPTable table_result_title = new PdfPTable(1);
            table_result_title.setWidthPercentage(100f);
            table_result_title.setTotalWidth(totalWidth);
            float [] width_result_title  = {1};
            table_result_title.setWidths(width_result_title);
            PdfPCell cell_result_title;

            cell_result_title = new PdfPCell(new Phrase(EMP__, font_header));
            __settingWidth(cell_result_title, 0, 0, 0, 0);
            cell_result_title.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_result_title.addCell(cell_result_title);

            cell_result_title = new PdfPCell(new Phrase(
                    gsMsg.getMessage("enq.46"), font_info_title));
            __settingWidth(cell_result_title, 0, 0, 0, 0);
            cell_result_title.setHorizontalAlignment(Element.ALIGN_LEFT);
            table_result_title.addCell(cell_result_title);

            pdfUtil.addCalcPaging(doc, table_result_title, 0);

            PdfPTable table_result = new PdfPTable(2);
            table_result.setWidthPercentage(100f);
            table_result.setTotalWidth(totalWidth);
            float [] width_header  = {0.76f, 0.24f};
            table_result.setWidths(width_header);
            PdfPCell cell_result;

            //ヘッダー 設問内容
            cell_result = new PdfPCell(new Phrase(gsMsg.getMessage("enq.12")
                    + gsMsg.getMessage("cmn.content"), font_info_header));
            __settingWidth(cell_result, 1, 1, 0, 1);
            cell_result.setBackgroundColor(color_light_gray);
            cell_result.setHorizontalAlignment(Element.ALIGN_CENTER);
            table_result.addCell(cell_result);

            // ヘッダー 回答結果
            cell_result = new PdfPCell(new Phrase(gsMsg.getMessage("enq.22")
                    + gsMsg.getMessage("cmn.results"), font_info_header));
            __settingWidth(cell_result, 1, 1, 1, 1);
            cell_result.setBackgroundColor(color_light_gray);
            cell_result.setHorizontalAlignment(Element.ALIGN_CENTER);
            table_result.addCell(cell_result);

            //設問情報 コンテンツ
            for (Enq310QuestionModel list :pdfMdl.getQueList()) {
                if (list.getQueKbn() != GSConstEnquete.SYURUI_COMMENT) {

                    /** 設問内容 */
                    PdfPTable question = new PdfPTable(5);
                    question.setWidthPercentage(100f);
                    question.setTotalWidth(totalWidth);
                    float [] width_question  = {0.25f, 0.2f, 0.15f, 0.1f, 0.3f};
                    question.setWidths(width_question);
                    PdfPCell question_cell;

                    //設問内容 設問番号
                    question_cell = new PdfPCell(new Phrase(
                            gsMsg.getMessage("enq.12") + list.getNo(), font_base));
                    __settingWidth(question_cell, 0, 0, 0, 0);
                    question_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    question.addCell(question_cell);

                    //設問内容 内容
                    question_cell = new PdfPCell(new Phrase(
                            PdfUtil.replaseBackslashToYen(list.getQuestion()),
                            font_base));
                    question_cell.setColspan(4);
                    __settingWidth(question_cell, 0, 0, 0, 0);
                    question_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    question.addCell(question_cell);

                    //設問内容 種類
                    String[] argsQueKbnName = {list.getQueKbnName()};
                    question_cell = new PdfPCell(new Phrase(
                            gsMsg.getMessage("enq.ans.selectval", argsQueKbnName), font_base));
                    __settingWidth(question_cell, 0, 0, 0, 0);
                    question_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    question.addCell(question_cell);

                    question_cell = new PdfPCell(new Phrase(EMP__, font_base));
                    question_cell.setColspan(4);
                    __settingWidth(question_cell, 0, 0, 0, 0);
                    question_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    question.addCell(question_cell);

                    //設問内容 必須
                    if (list.getRequire() == 0) {
                        question_cell = new PdfPCell(new Phrase(EMP__, font_base));
                    } else if (list.getRequire() == 1) {
                        question_cell = new PdfPCell(new Phrase(
                                gsMsg.getMessage("enq.28"), font_base));
                    } else {
                        question_cell = new PdfPCell(new Phrase(EMP__, font_base));
                    }
                    __settingWidth(question_cell, 0, 0, 0, 0);
                    question_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    question.addCell(question_cell);

                    //選択肢
                    if (list.getSubList() != null) {
                        question_cell = new PdfPCell(new Phrase(EMP__, font_base));
                        question_cell.setColspan(5);
                        __settingWidth(question_cell, 0, 0, 0, 0);
                        question_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        question.addCell(question_cell);
                    }

                    int idx = 0;
                    for (Enq310QuestionSubModel dspName: list.getSubList()) {
                        question_cell = new PdfPCell(new Phrase(EMP__, font_base));
                        __settingWidth(question_cell, 0, 0, 0, 0);
                        question_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        question.addCell(question_cell);

                        question_cell = new PdfPCell(
                                new Phrase(PdfUtil.replaseBackslashToYen(dspName.getDspName()),
                                        font_info_header));
                        __settingWidth(question_cell, 0, 0, 0, 0);
                        question_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        question.addCell(question_cell);

                        if (dspName.getDspName() != null) {
                            String[] argsAns = {dspName.getAnswer()};
                            question_cell = new PdfPCell(
                                    new Phrase(gsMsg.getMessage("bmk.23", argsAns), font_base));
                        } else {
                            question_cell = new PdfPCell(new Phrase(EMP__, font_base));
                        }
                        __settingWidth(question_cell, 0, 0, 0, 0);
                        question_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        question.addCell(question_cell);

                        if (dspName.getDspName() != null) {
                            question_cell = new PdfPCell(
                                    new Phrase(dspName.getAnswerAllPer() + "%", font_base));
                        } else {
                            question_cell = new PdfPCell(new Phrase(EMP__, font_base));
                        }
                        __settingWidth(question_cell, 0, 0, 0, 0);
                        question_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        question.addCell(question_cell);

                        question_cell = new PdfPCell(new Phrase(EMP__, font_base));
                        __settingWidth(question_cell, 0, 0, 0, 0);
                        question_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        question.addCell(question_cell);

                        idx++;
                    }

                    if (idx != 0 && idx == list.getSubList().size()) {
                        question_cell = new PdfPCell(new Phrase(EMP__, font_base));
                        question_cell.setColspan(5);
                        __settingWidth(question_cell, 0, 0, 0, 0);
                        question_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                        question.addCell(question_cell);
                    }

                    cell_result = new PdfPCell(question);
                    __settingWidth(cell_result, 0, 1, 0, 1);
                    cell_result.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table_result.addCell(cell_result);

                    /** 回答結果 */
                    PdfPTable answer = new PdfPTable(4);
                    answer.setWidthPercentage(100f);
                    answer.setTotalWidth(totalWidth);
                    float [] width_answer  = {0.45f, 0.35f, 0.22f, 0.03f};
                    answer.setWidths(width_answer);
                    PdfPCell answer_cell;

                    //回答結果 回答人数
                    answer_cell = new PdfPCell(new Phrase(gsMsg.getMessage("enq.22")
                            + gsMsg.getMessage("reserve.use.num"), font_base));
                    __settingWidth(answer_cell, 0, 0, 0, 0);
                    answer_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    answer.addCell(answer_cell);

                    String[] argsAnsCountAr = {list.getAnswerCountAr()};
                    answer_cell = new PdfPCell(new Phrase(
                            gsMsg.getMessage("bmk.23", argsAnsCountAr), font_base));
                    __settingWidth(answer_cell, 0, 0, 0, 0);
                    answer_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    answer.addCell(answer_cell);

                    answer_cell = new PdfPCell(
                            new Phrase(list.getAnswerCountArPer() + "%", font_base));
                    __settingWidth(answer_cell, 0, 0, 0, 0);
                    answer_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    answer.addCell(answer_cell);

                    answer_cell = new PdfPCell(new Phrase(EMP__, font_base));
                    __settingWidth(answer_cell, 0, 0, 0, 0);
                    answer_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    answer.addCell(answer_cell);

                    //回答結果 未回答人数
                    answer_cell = new PdfPCell(new Phrase(gsMsg.getMessage("enq.21")
                            + gsMsg.getMessage("reserve.use.num"), font_base));
                    __settingWidth(answer_cell, 0, 0, 0, 0);
                    answer_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    answer.addCell(answer_cell);

                    String[] argsAnsCountUn = {list.getAnswerCountUn()};
                    answer_cell = new PdfPCell(new Phrase(
                            gsMsg.getMessage("bmk.23", argsAnsCountUn), font_base));
                    __settingWidth(answer_cell, 0, 0, 0, 0);
                    answer_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    answer.addCell(answer_cell);

                    answer_cell = new PdfPCell(
                            new Phrase(list.getAnswerCountUnPer() + "%", font_base));
                    __settingWidth(answer_cell, 0, 0, 0, 0);
                    answer_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    answer.addCell(answer_cell);

                    answer_cell = new PdfPCell(new Phrase(EMP__, font_base));
                    __settingWidth(answer_cell, 0, 0, 0, 0);
                    answer_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    answer.addCell(answer_cell);

                    cell_result = new PdfPCell(answer);
                    __settingWidth(cell_result, 0, 1, 1, 1);
                    cell_result.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table_result.addCell(cell_result);

                }
            }

            table_result.setSplitLate(false);

            pdfUtil.addCalcPaging(doc, table_result, 0);

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
     * <br>[機  能] アンケート基本情報項目をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl PDFモデル
     * @param appRootPath アプリケーションルートパス
     * @param fontBase 基本フォント
     * @param fontInfoTitle タイトル用フォント
     * @param fontInfoHeader ヘッダー用フォント
     * @param colorLightBlue 背景色 薄青色
     * @param doc ドキュメント
     * @param totalWidth 全横幅
     * @throws DocumentException 実行時例外
     * @throws BadElementException 画像ファイル読み込み時例外
     * @throws MalformedURLException ドキュメント生成時例外
     * @throws IOException IOException
     * @return PdfpTable
     * @author JTS
     */
    public PdfPTable enqBaseInfo(
            EnqPdfModel pdfMdl,
            String appRootPath,
            Font fontBase,
            Font fontInfoTitle,
            Font fontInfoHeader,
            Color colorLightBlue,
            Document doc,
            float totalWidth) throws DocumentException, BadElementException,
            MalformedURLException, IOException {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        /** 基本情報 */
        PdfPTable table_info = new PdfPTable(4);
        table_info.setWidthPercentage(100f);
        table_info.setTotalWidth(totalWidth);
        float [] width_info  = {0.15f, 0.35f, 0.1f, 0.4f};
        table_info.setWidths(width_info);
        PdfPCell cell_info;

        //重要度
        cell_info = new PdfPCell(new Phrase(gsMsg.getMessage("enq.24"), fontInfoHeader));
        __settingWidth(cell_info, 1, 1, 1, 0);
        cell_info.setBackgroundColor(colorLightBlue);
        cell_info.setLeading(1.2f, 1.2f);
        cell_info.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        int priorityFlg = pdfMdl.getEnq310priority();

        if (priorityFlg == 0) {
                cell_info = new PdfPCell(new Phrase(gsMsg.getMessage("enq.33"), fontBase));

        } else if (priorityFlg == 1) {
                cell_info = new PdfPCell(new Phrase(gsMsg.getMessage("enq.34"), fontBase));

        } else if (priorityFlg == 2) {
                cell_info = new PdfPCell(new Phrase(gsMsg.getMessage("enq.35"), fontBase));
        } else {
            cell_info = new PdfPCell(new Phrase(EMP__, fontBase));
        }
        __settingWidth(cell_info, 1, 0, 1, 0);
        cell_info.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        //発信者
        cell_info = new PdfPCell(new Phrase(gsMsg.getMessage("enq.25"), fontInfoHeader));
        __settingWidth(cell_info, 1, 0, 1, 0);
        cell_info.setBackgroundColor(colorLightBlue);
        cell_info.setLeading(1.2f, 1.2f);
        cell_info.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);
        String senderName = PdfUtil.replaseBackslashToYen(pdfMdl.getEnq310sender());
        if (pdfMdl.isEnq310senderDelFlg()) {
            Font fontDelSender
                = new Font(fontBase.getBaseFont(), fontBase.getSize(), Font.STRIKETHRU);
            cell_info = new PdfPCell(new Phrase(senderName, fontDelSender));
        } else {
            cell_info = new PdfPCell(new Phrase(senderName, fontBase));
        }
        __settingWidth(cell_info, 1, 0, 1, 0);
        cell_info.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        //内容
        cell_info = new PdfPCell(new Phrase(gsMsg.getMessage("enq.26"), fontInfoHeader));
        __settingWidth(cell_info, 1, 1, 1, 0);
        cell_info.setBackgroundColor(colorLightBlue);
        cell_info.setLeading(1.2f, 1.2f);
        cell_info.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        cell_info = new PdfPCell(new Phrase(
                PdfUtil.replaseBackslashToYen(pdfMdl.getEnq310enqContent()), fontBase));
        cell_info.setColspan(3);
        __settingWidth(cell_info, 1, 0, 1, 0);
        cell_info.setLeading(1.2f, 1.2f);
        cell_info.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell_info.setPaddingBottom(4f);
        table_info.addCell(cell_info);

        //回答期限
        cell_info = new PdfPCell(new Phrase(gsMsg.getMessage("enq.19"), fontInfoHeader));
        __settingWidth(cell_info, 1, 1, 1, 0);
        cell_info.setBackgroundColor(colorLightBlue);
        cell_info.setLeading(1.2f, 1.2f);
        cell_info.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        cell_info = new PdfPCell(new Phrase(
                pdfMdl.getEnq310ansLimitDate() ,
                fontBase));
        __settingWidth(cell_info, 1, 0, 1, 0);
        cell_info.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        //公開期限
        cell_info = new PdfPCell(new Phrase(gsMsg.getMessage("enq.enq210.11"), fontInfoHeader));
        __settingWidth(cell_info, 1, 0, 1, 0);
        cell_info.setBackgroundColor(colorLightBlue);
        cell_info.setLeading(1.2f, 1.2f);
        cell_info.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);
        String ansOpenDate = "";
        if (pdfMdl.getEnq310ansOpen() == GSConstEnquete.EMN_ANS_OPEN_PUBLIC) {
            ansOpenDate =
                    pdfMdl.getEnq310ansPubFrDate()
                    + " ～ ";
            if (pdfMdl.getEnq310pubLimitDate() != null) {
                ansOpenDate += pdfMdl.getEnq310pubLimitDate();
            } else {
                ansOpenDate += gsMsg.getMessage("main.man200.9");
            }
        } else {
            ansOpenDate = gsMsg.getMessage("cmn.private");
        }

        cell_info = new PdfPCell(new Phrase(ansOpenDate, fontBase));
        __settingWidth(cell_info, 1, 0, 1, 0);
        cell_info.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        //４行目
        //対象人数
        cell_info = new PdfPCell(new Phrase(
                gsMsg.getMessage("cmn.number.of.candidates"), fontInfoHeader));
        __settingWidth(cell_info, 1, 1, 1, 1);
        cell_info.setBackgroundColor(colorLightBlue);
        cell_info.setLeading(1.2f, 1.2f);
        cell_info.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        /** 対象人数 */
        PdfPTable target = new PdfPTable(4);
        target.setWidthPercentage(100f);
        target.setTotalWidth(totalWidth);
        float [] width_target  = {0.3f, 0.2f, 0.2f, 0.3f};
        target.setWidths(width_target);
        PdfPCell target_cell;

        //全体
        target_cell = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.whole"), fontInfoHeader));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        target.addCell(target_cell);

        String[] argsAntCountAll = {pdfMdl.getEnq310answerCountAll()};
        target_cell = new PdfPCell(new Phrase(
                gsMsg.getMessage("bmk.23", argsAntCountAll), fontBase));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        target.addCell(target_cell);

        target_cell = new PdfPCell(new Phrase(EMP__, fontBase));
        target_cell.setColspan(2);
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        target.addCell(target_cell);

        //回答人数
        target_cell = new PdfPCell(new Phrase(gsMsg.getMessage("enq.44"), fontInfoHeader));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        target.addCell(target_cell);

        String[] argsAnsCountAr = {pdfMdl.getEnq310answerCountAr()};
        target_cell = new PdfPCell(new Phrase(
                gsMsg.getMessage("bmk.23", argsAnsCountAr), fontBase));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        target.addCell(target_cell);

        String[] argsAnsCountArPer = {pdfMdl.getEnq310answerCountArPer() + "%"};
        target_cell = new PdfPCell(new Phrase(
                gsMsg.getMessage("enq.ans.selectval", argsAnsCountArPer), fontBase));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        target.addCell(target_cell);

        target_cell = new PdfPCell(new Phrase(EMP__, fontBase));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        target.addCell(target_cell);

        //未回答人数
        target_cell = new PdfPCell(new Phrase(gsMsg.getMessage("enq.45"), fontInfoHeader));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        target.addCell(target_cell);


        String[] argsAntCountUn = {pdfMdl.getEnq310answerCountUn()};
        target_cell = new PdfPCell(new Phrase(
                gsMsg.getMessage("bmk.23", argsAntCountUn) , fontBase));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        target.addCell(target_cell);

        String[] argsAnsCountPer = {pdfMdl.getEnq310answerCountUnPer() + "%"};
        target_cell = new PdfPCell(new Phrase(
                gsMsg.getMessage(
                        "enq.ans.selectval", argsAnsCountPer) , fontBase));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        target_cell.setPaddingBottom(3f);
        target.addCell(target_cell);

        target_cell = new PdfPCell(new Phrase(EMP__, fontBase));
        __settingWidth(target_cell, 0, 0, 0, 0);
        target_cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        target.addCell(target_cell);

        cell_info = new PdfPCell(target);
        __settingWidth(cell_info, 1, 0, 1, 1);
        cell_info.setHorizontalAlignment(Element.ALIGN_LEFT);
        table_info.addCell(cell_info);

        //注意事項
        cell_info = new PdfPCell(new Phrase(gsMsg.getMessage("enq.27"), fontInfoHeader));
        cell_info.setBackgroundColor(colorLightBlue);
        __settingWidth(cell_info, 1, 0, 1, 1);
        cell_info.setLeading(1.2f, 1.2f);
        cell_info.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        cell_info = new PdfPCell(new Phrase(pdfMdl.getEnq310notes(), fontBase));
        __settingWidth(cell_info, 1, 0, 1, 1);
        cell_info.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell_info.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table_info.addCell(cell_info);

        cell_info = new PdfPCell(new Phrase(EMP__, fontBase));
        __settingWidth(cell_info, 0, 0, 0, 0);
        cell_info.setColspan(4);
        cell_info.setHorizontalAlignment(Element.ALIGN_LEFT);
        table_info.addCell(cell_info);

        return table_info;

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
