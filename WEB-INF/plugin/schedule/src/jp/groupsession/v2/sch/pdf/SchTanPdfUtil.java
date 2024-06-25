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
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sch.sch040.model.Sch040AddressModel;
import jp.groupsession.v2.sch.sch040.model.Sch040AttendModel;
import jp.groupsession.v2.sch.sch040.model.Sch040CompanyModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] スケジュール単票Pdf出力に関するUtilクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchTanPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchTanPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public SchTanPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public SchTanPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] スケジュール単票Pdf出力に関するUtilクラスです。
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl pdfモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream スケジュールデータの出力先となるストリーム
     * @throws Exception 実行例外
     * @throws FileNotFoundException 実行例外
     * @throws DocumentException 実行例外
     * @author JTS
     */
    public void createSchTanPdf(
            SchTanPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        log__.debug("スケジュール単票PDFの生成開始");

        Document doc = null;

        /* フォントファイルパス */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント アカウント名
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント ヘッダー
        Font font_header = PdfUtil.getFont10b(fontPath);
        //フォント メイン 10pt 黒
        Font font_main = PdfUtil.getFont10(fontPath);

        //フォント(スケジュール内容 青)
        Font font_main_blue = PdfUtil.getFont10(fontPath);
        font_main_blue.setColor(PdfUtil.FONT_COLOR_BLUE);
        //フォント(スケジュール内容 赤)
        Font font_main_red = PdfUtil.getFont10(fontPath);
        font_main_red.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント(スケジュール内容 緑)
        Font font_main_green = PdfUtil.getFont10(fontPath);
        font_main_green.setColor(PdfUtil.FONT_COLOR_GREEN);
        //フォント(スケジュール内容 オレンジ)
        Font font_main_orange = PdfUtil.getFont10(fontPath);
        font_main_orange.setColor(PdfUtil.FONT_COLOR_ORANGE);
        //フォント(スケジュール内容 黒)
        Font font_main_black = PdfUtil.getFont10(fontPath);
        font_main_black.setColor(PdfUtil.FONT_COLOR_BLACK);
        //フォント(スケジュール内容 紺)
        Font font_main_navy = PdfUtil.getFont10(fontPath);
        font_main_navy.setColor(PdfUtil.FONT_COLOR_NAVY);
        //フォント(スケジュール内容 茶)
        Font font_main_maroon = PdfUtil.getFont10(fontPath);
        font_main_maroon.setColor(PdfUtil.FONT_COLOR_MAROON);
        //フォント(スケジュール内容 青緑)
        Font font_main_teal = PdfUtil.getFont10(fontPath);
        font_main_teal.setColor(PdfUtil.FONT_COLOR_CYAN);
        //フォント(スケジュール内容 灰)
        Font font_main_gray = PdfUtil.getFont10(fontPath);
        font_main_gray.setColor(PdfUtil.FONT_COLOR_GRAY);
        //フォント(スケジュール内容 水色)
        Font font_main_lblue = PdfUtil.getFont10(fontPath);
        font_main_lblue.setColor(PdfUtil.FONT_COLOR_AQUA);

        //フォント(出欠確認 出席)
        Font font_ans_yes = PdfUtil.getFont10(fontPath);
        font_ans_yes.setColor(PdfUtil.FONT_COLOR_GREEN);
        //フォント(出欠確認 欠席)
        Font font_ans_no = PdfUtil.getFont10(fontPath);
        font_ans_no.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント(出欠確認 未回答)
        Font font_ans_none = PdfUtil.getFont10(fontPath);
        font_ans_none.setColor(PdfUtil.FONT_COLOR_BLACK);


        //フォント 会社情報 黒
        Font font_comp_black = PdfUtil.getFont10b(fontPath);
        font_comp_black.setColor(PdfUtil.FONT_COLOR_BLACK);

        //フォント 会社情報 青
        Font font_comp_blue = PdfUtil.getFont10b(fontPath);
        font_comp_blue.setColor(PdfUtil.FONT_COLOR_BLUE);

        //バックカラー（ヘッダ）
        Color color_header = PdfUtil.BG_COLOR_LIGHTBLUE;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ByteArrayOutputStream byteout = null;

        int fixedHeight = 20;

        try {
            doc = new Document(PageSize.A4); //(A4 H:842F, W:595F)
            //アウトプットストリームをPDFWriterに設定します。
            byteout = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, byteout);

            //出力するPDFに説明を付与します。
            doc.addTitle(gsMsg.getMessage("schedule.108"));
            doc.addAuthor("GroupSession");
            doc.addSubject("単票");
            doc.open();

            //文字入力範囲（横）
            float totalWidth = doc.getPageSize().getWidth();
            //タイトル
            float [] width_title  = {0.3f, 0.7f};
            PdfPTable table_title =
                    PdfUtil.createTable(2, 100, totalWidth, width_title, Element.ALIGN_CENTER);

            //スケジュール
            float [] width_main  = {0.2f, 0.8f};
            PdfPTable table_main =
                    PdfUtil.createTable(2, 100, totalWidth, width_main, Element.ALIGN_CENTER);

            //トップタイトル
            String topTitle = gsMsg.getMessage("schedule.108");
            PdfPCell cell_title = PdfUtil.setCellRet(
                    topTitle, 0, Element.ALIGN_JUSTIFIED_ALL, font_title);
            __settingWidth(cell_title, 0, 0, 0, 1);
            cell_title.setLeading(1.2f, 1.2f); //行間の設定
            table_title.addCell(cell_title);

            PdfUtil.setCell(table_title, EMP__, 1, Element.ALIGN_LEFT,
                    PdfUtil.getFontEmpty(fontPath));

            //空白
            PdfUtil.setCell(table_title, EMP__, 2, Element.ALIGN_LEFT,
                    PdfUtil.getFontEmpty(fontPath));

            PdfPCell cell_main;

            //スケジュール編集画面 表示モード 通常スケジュール以外
            if (pdfMdl.getPdfEditDspMode() != GSConstSchedule.EDIT_DSP_MODE_NORMAL) {

                //出欠確認
                cell_main = PdfUtil.setCellRet(
                        "出欠確認",
                        1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                __settingWidth(cell_main, 1, 1, 0.5f, 1);
                cell_main.setBackgroundColor(color_header);
                cell_main.setFixedHeight(fixedHeight);
                table_main.addCell(cell_main);

                if (pdfMdl.getPdfAttendKbn() == GSConstSchedule.ATTEND_KBN_YES) {

                    float [] width_attend  = {0.01f, 0.25f, 0.3f, 0.15f, 0.28f, 0.01f};
                    PdfPTable table_attend =
                            PdfUtil.createTable(
                                    6, 100, totalWidth * 0.8f, width_attend, Element.ALIGN_LEFT);
                    PdfPCell cell_attend;

                    //出欠確認・回答区分
                    cell_attend = PdfUtil.setCellRet(
                            EMP__, 1, Element.ALIGN_LEFT,
                            Element.ALIGN_MIDDLE, PdfUtil.getFontEmpty(fontPath));
                    table_attend.addCell(cell_attend);

                    String strAttend;
                    if (pdfMdl.getPdfAttendAuKbn() == GSConstSchedule.ATTEND_REGIST_USER_YES) {
                        strAttend = "確認する";
                    } else {
                        if (pdfMdl.getPdfAttendAnsKbn() == GSConstSchedule.ATTEND_ANS_YES) {
                            strAttend = "出席";
                        } else if (pdfMdl.getPdfAttendAnsKbn() == GSConstSchedule.ATTEND_ANS_NO) {
                            strAttend = "欠席";
                        } else {
                            strAttend = "未回答";
                        }
                    }

                    cell_attend = PdfUtil.setCellRet(
                            strAttend, 5, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    cell_attend.setFixedHeight(fixedHeight);
                    table_attend.addCell(cell_attend);

                    //出欠確認回答リストヘッダー
                    cell_attend = PdfUtil.setCellRet(
                            EMP__, 1, Element.ALIGN_LEFT,
                            Element.ALIGN_MIDDLE, PdfUtil.getFontEmpty(fontPath));
                    table_attend.addCell(cell_attend);

                    cell_attend = PdfUtil.setCellRet(
                            "回答日時", 1, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
                    cell_attend.setFixedHeight(fixedHeight);
                    cell_attend.setBorder(Rectangle.BOX);
                    cell_attend.setBackgroundColor(PdfUtil.BG_COLOR_DARKBLUE);
                    table_attend.addCell(cell_attend);

                    cell_attend = PdfUtil.setCellRet(
                            "氏名", 1, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
                    cell_attend.setBackgroundColor(PdfUtil.BG_COLOR_DARKBLUE);
                    cell_attend.setBorder(Rectangle.BOX);
                    table_attend.addCell(cell_attend);


                    cell_attend = PdfUtil.setCellRet(
                            "回答内容", 1, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
                    cell_attend.setBackgroundColor(PdfUtil.BG_COLOR_DARKBLUE);
                    cell_attend.setBorder(Rectangle.BOX);
                    table_attend.addCell(cell_attend);

                    cell_attend = PdfUtil.setCellRet(
                            "コメント", 1, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
                    cell_attend.setBackgroundColor(PdfUtil.BG_COLOR_DARKBLUE);
                    cell_attend.setBorder(Rectangle.BOX);
                    table_attend.addCell(cell_attend);


                    cell_attend = PdfUtil.setCellRet(
                            EMP__, 1, Element.ALIGN_LEFT,
                            Element.ALIGN_MIDDLE, PdfUtil.getFontEmpty(fontPath));
                    table_attend.addCell(cell_attend);

                    //出欠確認回答リストデータ
                    int index = 0;
                    for (Sch040AttendModel attendMdl : pdfMdl.getPdfAttendAnsList()) {
                        Color attendCol;
                        if (index % 2 == 0) {
                            attendCol = Color.WHITE;
                        } else {
                            attendCol = PdfUtil.BG_COLOR_LIGHTBLUE;
                        }

                        cell_attend = PdfUtil.setCellRet(
                                EMP__, 1, Element.ALIGN_LEFT,
                                Element.ALIGN_MIDDLE, PdfUtil.getFontEmpty(fontPath));
                        table_attend.addCell(cell_attend);

                        String strAnsDate;
                        if (attendMdl.getAttendAnsKbn() == GSConstSchedule.ATTEND_ANS_NONE) {
                            strAnsDate = "－";
                        } else {
                            strAnsDate = attendMdl.getAttendAnsDate();
                        }

                        cell_attend = PdfUtil.setCellRet(
                                strAnsDate, 1,
                                Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_main);
                        cell_attend.setBackgroundColor(attendCol);
                        cell_attend.setBorder(Rectangle.BOX);
                        table_attend.addCell(cell_attend);

                        cell_attend = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(attendMdl.getAttendAnsUsrName()), 1,
                                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                        cell_attend.setBackgroundColor(attendCol);
                        cell_attend.setBorder(Rectangle.BOX);
                        table_attend.addCell(cell_attend);

                        Font fontCalorAns;
                        String attendAnsListAns;
                        if (attendMdl.getAttendAnsKbn() == GSConstSchedule.ATTEND_ANS_YES) {
                            fontCalorAns = font_ans_yes;
                            attendAnsListAns = "出席";
                        } else if (attendMdl.getAttendAnsKbn() == GSConstSchedule.ATTEND_ANS_NO) {
                            fontCalorAns = font_ans_no;
                            attendAnsListAns = "欠席";
                        } else {
                            fontCalorAns = font_ans_none;
                            attendAnsListAns = "未回答";
                        }

                        cell_attend = PdfUtil.setCellRet(
                                attendAnsListAns, 1, Element.ALIGN_CENTER,
                                Element.ALIGN_MIDDLE, fontCalorAns);
                        cell_attend.setBackgroundColor(attendCol);
                        cell_attend.setBorder(Rectangle.BOX);
                        table_attend.addCell(cell_attend);

                        cell_attend = PdfUtil.setCellRet(
                                PdfUtil.replaseBackslashToYen(attendMdl.getAttendAnsComment()), 1,
                                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                        cell_attend.setBackgroundColor(attendCol);
                        cell_attend.setBorder(Rectangle.BOX);
                        table_attend.addCell(cell_attend);

                        cell_attend = PdfUtil.setCellRet(
                                EMP__, 1, Element.ALIGN_LEFT,
                                Element.ALIGN_MIDDLE, PdfUtil.getFontEmpty(fontPath));
                        table_attend.addCell(cell_attend);

                        index++;
                    }

                    cell_attend = PdfUtil.setCellRet(
                            EMP__, 6, Element.ALIGN_LEFT,
                            Element.ALIGN_MIDDLE, PdfUtil.getFontEmpty(fontPath));
                    table_attend.addCell(cell_attend);

                    cell_main = new PdfPCell(table_attend);
                    cell_main.setColspan(1);

                } else {
                    cell_main = PdfUtil.setCellRet(
                            "確認しない", 1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                }
                __settingWidth(cell_main, 1, 0.5f, 1, 1);
                table_main.addCell(cell_main);
            }

            //空白
            PdfUtil.setCell(table_main, EMP__, 2, Element.ALIGN_LEFT,
                    PdfUtil.getFontEmpty(fontPath));

            //名前
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("schedule.4"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(fixedHeight);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfName()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //開始
            String frDateArea = pdfMdl.getPdfFrDate();

            if (pdfMdl.getPdfTimeKbn() != GSConstSchedule.TIME_EXIST) {
                frDateArea += "    ";
                frDateArea += gsMsg.getMessage("schedule.7");
            }

            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("schedule.16"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setFixedHeight(fixedHeight);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    frDateArea, 1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //終了
            StringBuilder toDateArea = new StringBuilder();
            toDateArea.append(pdfMdl.getPdfToDate());
            toDateArea.append("    ");
            toDateArea.append(gsMsg.getMessage("cmn.period"));
            toDateArea.append("：");
            toDateArea.append(pdfMdl.getPdfKikan());
            toDateArea.append("日間");

            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("schedule.27"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setFixedHeight(fixedHeight);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    toDateArea.toString(), 1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //施設予約プラグイン使用可能
            if (pdfMdl.getAddressPluginKbn() == GSConstSchedule.PLUGIN_USE) {
                //会社・担当者
                cell_main = PdfUtil.setCellRet(
                        gsMsg.getMessage("schedule.14"),
                        1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                __settingWidth(cell_main, 1, 1, 0.5f, 0);
                cell_main.setBackgroundColor(color_header);
                cell_main.setFixedHeight(fixedHeight);
                table_main.addCell(cell_main);

                //会社・担当者テーブル
                float [] width_comp  = {0.03f, 0.03f, 0.8f};
                PdfPTable table_comp = new PdfPTable(3);
                table_comp.setWidthPercentage(100);
                table_comp.setTotalWidth(totalWidth * 0.8f);
                table_comp.setWidths(width_comp);
                table_comp.setHorizontalAlignment(Element.ALIGN_LEFT);
                PdfPCell cell_comp;
                if (pdfMdl.getPdfCompanyList() != null && pdfMdl.getPdfCompanyList().size() > 0) {

                    for (Sch040CompanyModel cmpMdl : pdfMdl.getPdfCompanyList()) {
                        //会社画像
                        String compImagePath = appRootPath + "schedule/images/icon_company.gif";
                        File fileComp = new File(compImagePath);
                        //ファイルの有無チェック
                        if (fileComp.exists()) {
                            Image img = Image.getInstance(compImagePath);
                            img.scalePercent(50f);
                            cell_comp = new PdfPCell(img, false);
                        } else {
                            cell_comp = new PdfPCell(
                                    new Phrase(EMP__, PdfUtil.getFontEmpty(fontPath)));
                        }
                        cell_comp.setPaddingLeft(4);
                        cell_comp.setPaddingTop(1);
                        cell_comp.setBorder(0);
                        cell_comp.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell_comp.setHorizontalAlignment(Element.ALIGN_LEFT);
                        table_comp.addCell(cell_comp);

                        //会社情報
                        String cmpName = null;
                        Font font_comp = null;
                        cmpName = cmpMdl.getCompanyName();
                        cmpName = PdfUtil.replaseBackslashToYen(cmpName);
                        if (cmpMdl.getCompanySid() == 0) {
                            font_comp = font_comp_black;
                        } else {
                            font_comp = font_comp_blue;
                        }

                        cell_comp = PdfUtil.setCellRet(
                                cmpName, 2, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_comp);
                        table_comp.addCell(cell_comp);

                        //アドレス情報
                        if (cmpMdl.getAddressDataList() != null
                                && cmpMdl.getAddressDataList().size() > 0) {

                            for (Sch040AddressModel addrMdl : cmpMdl.getAddressDataList()) {
                                //空白セル
                                PdfUtil.setCell(table_comp, EMP__, 1, Element.ALIGN_LEFT,
                                        PdfUtil.getFontEmpty(fontPath));

                                //担当者画像
                                String addrImagePath =
                                        appRootPath + "schedule/images/icon_tantou.gif";
                                File fileAddr = new File(addrImagePath);
                                //ファイルの有無チェック
                                if (fileAddr.exists()) {
                                    Image img = Image.getInstance(addrImagePath);
                                    img.scalePercent(50f);
                                    cell_comp = new PdfPCell(img, false);
                                } else {
                                    cell_comp = new PdfPCell(
                                            new Phrase(EMP__, PdfUtil.getFontEmpty(fontPath)));
                                }
                                cell_comp.setPaddingLeft(4);
                                cell_comp.setPaddingTop(1);
                                cell_comp.setBorder(0);
                                cell_comp.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell_comp.setHorizontalAlignment(Element.ALIGN_LEFT);
                                table_comp.addCell(cell_comp);

                                //担当者名
                                cell_comp = PdfUtil.setCellRet(
                                        PdfUtil.replaseBackslashToYen(addrMdl.getAdrName()), 2,
                                        Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                                table_comp.addCell(cell_comp);
                            }
                        }
                    }

                } else {
                    cell_comp = PdfUtil.setCellRet(EMP__, 3, Element.ALIGN_LEFT, font_main);
                    table_comp.addCell(cell_comp);
                }

                cell_main = new PdfPCell(table_comp);
                cell_main.setBorder(Rectangle.NO_BORDER);
                cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
                __settingWidth(cell_main, 1, 0.5f, 1, 0);
                table_main.addCell(cell_main);

            }

            //タイトル
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.title"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(fixedHeight);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfTitle()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //タイトル色
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("schedule.10"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(fixedHeight);
            table_main.addCell(cell_main);

            float [] width_color  = {0.03f, 0.97f};
            PdfPTable table_color = new PdfPTable(2);
            table_color.setWidthPercentage(100);
            table_color.setTotalWidth(totalWidth * 0.8f);
            table_color.setWidths(width_color);
            table_color.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cell_color;

            Font font_color = font_main_blue;
            switch (pdfMdl.getPdfColor()) {
            case GSConstSchedule.BGCOLOR_BLUE:
                font_color = font_main_blue;
                break;
            case GSConstSchedule.BGCOLOR_RED:
                font_color = font_main_red;
                break;
            case GSConstSchedule.BGCOLOR_GREEN:
                font_color = font_main_green;
                break;
            case GSConstSchedule.BGCOLOR_ORANGE:
                font_color = font_main_orange;
                break;
            case GSConstSchedule.BGCOLOR_BLACK:
                font_color = font_main_black;
                break;
            case GSConstSchedule.BGCOLOR_NAVY :
                font_color = font_main_navy;
                break;
            case GSConstSchedule.BGCOLOR_MAROON :
                font_color = font_main_maroon;
                break;
            case GSConstSchedule.BGCOLOR_TEAL :
                font_color = font_main_teal;
                break;
            case GSConstSchedule.BGCOLOR_GRAY :
                font_color = font_main_gray;
                break;
            case GSConstSchedule.BGCOLOR_LBLUE :
                font_color = font_main_lblue;
                break;
            default :
                font_color = font_main_blue;
                break;
            }
            PdfUtil.setCell(table_color,
                    "■", 1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_color);

            int selectColorSid = pdfMdl.getPdfColor();
            ArrayList<String> colorList = pdfMdl.getPdfColorMsg();
            String colorText = null;
            if (colorList != null && 0 < colorList.size()) {
                colorText = colorList.get(selectColorSid - 1);
            }

            cell_color = PdfUtil.setCellRet(
                    NullDefault.getStringZeroLength(colorText, " "),
                            1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            table_color.addCell(cell_color);

            cell_main = new PdfPCell(table_color);
            cell_main.setBorder(Rectangle.NO_BORDER);
            cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //内容
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.content"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(40);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfValue()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);
            
            //添付
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.attached"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(fixedHeight);
            table_main.addCell(cell_main);
            
            StringBuilder fileNames = new StringBuilder();
            for (CmnBinfModel binMdl : pdfMdl.getPdfBinList()) {
                if (fileNames.length() != 0) {
                    fileNames.append("\n");
                }
                fileNames.append(binMdl.getBinFileName());
            }

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(fileNames.toString()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //備考
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.memo"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(40);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfBiko()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);

            String strEditKbn = null;
            switch (pdfMdl.getPdfEditKbn()) {
                case GSConstSchedule.EDIT_CONF_NONE:
                    strEditKbn = gsMsg.getMessage("cmn.nolimit");
                    break;
                case GSConstSchedule.EDIT_CONF_OWN:
                    strEditKbn = gsMsg.getMessage("cmn.only.principal.or.registant");
                    break;
                case GSConstSchedule.EDIT_CONF_GROUP:
                    strEditKbn = gsMsg.getMessage("cmn.only.affiliation.group.membership");
                    break;
                default:
                    strEditKbn = "";
                    break;
            }

            //編集権限
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.edit.permissions"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(fixedHeight);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    strEditKbn, 1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            String strPublicKbn = "";
            switch (pdfMdl.getPdfPublicKbn()) {
                case GSConstSchedule.DSP_PUBLIC:
                    strPublicKbn = gsMsg.getMessage("cmn.public");
                    break;
                case GSConstSchedule.DSP_NOT_PUBLIC:
                    strPublicKbn = gsMsg.getMessage("cmn.private");
                    break;
                case GSConstSchedule.DSP_YOTEIARI:
                    strPublicKbn = gsMsg.getMessage("schedule.5");
                    break;
                case GSConstSchedule.DSP_BELONG_GROUP:
                    strPublicKbn = gsMsg.getMessage("schedule.28");
                    break;
                case GSConstSchedule.DSP_USRGRP:
                    strPublicKbn = gsMsg.getMessage("schedule.37");
                    break;
                case GSConstSchedule.DSP_TITLE:
                    strPublicKbn = gsMsg.getMessage("schedule.38");
                    break;
                default:
                    strPublicKbn = "";
                    break;
            }

            //公開
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("schedule.21"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(fixedHeight);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    strPublicKbn, 1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            table_main.addCell(cell_main);

            //公開対象
            if (pdfMdl.getPdfPublicKbn() == GSConstSchedule.DSP_USRGRP) {
                cell_main = PdfUtil.setCellRet(
                        gsMsg.getMessage("schedule.36"),
                        1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                __settingWidth(cell_main, 1, 1, 0.5f, 0);
                cell_main.setBackgroundColor(color_header);
                cell_main.setFixedHeight(fixedHeight);
                table_main.addCell(cell_main);

                StringBuilder publicTarget = new StringBuilder();
                if (pdfMdl.getPdfPublicTarget() != null) {
                    for (UsrLabelValueBean pubTargetUser : pdfMdl.getPdfPublicTarget()) {
                        if (publicTarget.length() != 0) {
                            publicTarget.append("\n");
                        }
                        publicTarget.append(pubTargetUser.getLabel());
                    }
                }
                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(publicTarget.toString()),
                        1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                __settingWidth(cell_main, 1, 0.5f, 1, 0);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);
            }


            //スケジュール編集画面 表示モード 通常スケジュール
            if (pdfMdl.getPdfEditDspMode() == GSConstSchedule.EDIT_DSP_MODE_NORMAL) {
                //出欠確認
                cell_main = PdfUtil.setCellRet(
                        "出欠確認",
                        1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                __settingWidth(cell_main, 1, 1, 0.5f, 0);
                cell_main.setBackgroundColor(color_header);
                cell_main.setFixedHeight(fixedHeight);
                table_main.addCell(cell_main);

                cell_main = PdfUtil.setCellRet(
                        "確認しない", 1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                __settingWidth(cell_main, 1, 0.5f, 1, 0);
                table_main.addCell(cell_main);
            }

            //同時登録
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("schedule.117"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(fixedHeight);
            table_main.addCell(cell_main);

            StringBuilder userNames = new StringBuilder();
            if (pdfMdl.getPdfUserList() != null) {
                for (CmnUsrmInfModel usrMdl : pdfMdl.getPdfUserList()) {
                    if (userNames.length() != 0) {
                        userNames.append("\n");
                    }
                    userNames.append(usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
                }
            }
            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(userNames.toString()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);

            //施設予約プラグイン使用可能
            if (pdfMdl.getReservePluginKbn() == GSConstSchedule.RESERVE_PLUGIN_USE) {
                //施設予約
                cell_main = PdfUtil.setCellRet(
                        gsMsg.getMessage("cmn.reserve"),
                        1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                __settingWidth(cell_main, 1, 1, 0.5f, 0);
                cell_main.setPaddingBottom(4f);
                cell_main.setBackgroundColor(color_header);
                cell_main.setFixedHeight(fixedHeight);
                table_main.addCell(cell_main);

                StringBuilder sisName = new StringBuilder();
                if (pdfMdl.getPdfSisList() != null) {
                    for (String sis : pdfMdl.getPdfSisList()) {
                        if (sisName.length() != 0) {
                            sisName.append("\n");
                        }
                        sisName.append(sis);
                    }
                }
                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(sisName.toString()),
                        1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                __settingWidth(cell_main, 1, 0.5f, 1, 0);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);
            }

            //登録者
            cell_main = PdfUtil.setCellRet(
                    gsMsg.getMessage("cmn.registant"),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 1);
            cell_main.setBackgroundColor(color_header);
            cell_main.setFixedHeight(fixedHeight);
            table_main.addCell(cell_main);

            StringBuilder strRegist = new StringBuilder();
            strRegist.append(pdfMdl.getPdfRegistUser());
            strRegist.append("                        ");
            strRegist.append(pdfMdl.getPdfRegistDate());

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(strRegist.toString()),
                    1, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 1);
            table_main.addCell(cell_main);

            table_title.setSplitLate(false);
            table_main.setSplitLate(false);

            PdfUtil pdfUtil = new PdfUtil();
            pdfUtil.addCalcPaging(doc, table_title, 0);
            pdfUtil.addCalcPaging(doc, table_main, 0);
        } catch (Exception e) {
            e.printStackTrace();
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
                underContent.showText("GroupSession");
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