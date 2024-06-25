package jp.groupsession.v2.ntp.ntp040;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040CommentModel;
import jp.groupsession.v2.ntp.ntp040.model.Ntp040DataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
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
 *
 * <br>[機  能] 当日日報出力機能
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp040PDFUtil {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Ntp040PDFUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** グレーフィルター設定値 */
    private static final float GRAY_FILL__ = 0.93f;

    /**
     * コンストラクタ
     * @param req リクエストモデル
     */
    public Ntp040PDFUtil(RequestModel req) {
        reqMdl__ = req;
    }
    /**
     *
     * <br>[機  能] pdfを出力
     * <br>[解  説]
     * <br>[備  考]
     * @param param パラメータモデル
     * @param appRootPath アプリケーションルートパス
     * @param oStream スケジュールデータの出力先となるストリーム
     * @throws Exception 実行例外
     * @author JTS
     */
    public void createPdf(Ntp040ParamModel param,
            String appRootPath,
            OutputStream oStream) throws Exception {
        log__.debug("スケジュール単票PDFの生成開始");

        Document doc = null;

        /* フォントファイルパス */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント アカウント名
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント 日報NO
        Font font_index = PdfUtil.getFont12b(fontPath);
        //フォント ヘッダー
        Font font_header = PdfUtil.getFont10b(fontPath);
        //フォント メイン 10pt 黒
        Font font_main = PdfUtil.getFont10(fontPath);

        Font font_main_uchikeshi = PdfUtil.getFont10(fontPath);
        font_main_uchikeshi.setStyle(Font.STRIKETHRU);

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

        //フォント 会社情報 黒
        Font font_comp_black = PdfUtil.getFont10b(fontPath);
        font_comp_black.setColor(PdfUtil.FONT_COLOR_BLACK);

        //フォント 会社情報 青
        Font font_comp_blue = PdfUtil.getFont10b(fontPath);
        font_comp_blue.setColor(PdfUtil.FONT_COLOR_BLUE);


        GsMessage gsMsg = new GsMessage(reqMdl__);
        ByteArrayOutputStream byteout = null;

        try {
            PdfUtil pdfUtil = new PdfUtil();
            doc = new Document(PageSize.A4); //(A4 H:842F, W:595F)
            //アウトプットストリームをPDFWriterに設定します。
            byteout = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, byteout);

            //出力するPDFに説明を付与します。
            doc.addTitle(gsMsg.getMessage("ntp.1"));
            doc.addAuthor("GroupSession");
            doc.addSubject("単票");
            doc.open();
            //文字入力範囲（横）
            float totalWidth = doc.getPageSize().getWidth();
            //タイトル
            float [] width_title  = {1.0f};
            PdfPTable table_title =
                    PdfUtil.createTable(1, 100, totalWidth, width_title, Element.ALIGN_CENTER);
            PdfPCell cell_acc;

            //タイトル
            String title = gsMsg.getMessage("ntp.1") + " "
                    + PdfUtil.replaseBackslashToYen(param.getNtp040UsrName()) + " "
                    + gsMsg.getMessage("cmn.date4"
                            , new String[] {NullDefault.getString(param.getNtp040InitYear(), "")
                            , NullDefault.getString(param.getNtp040InitMonth(), "")
                            , NullDefault.getString(param.getNtp040InitDay(), "")});

            cell_acc = new PdfPCell(new Phrase(title
                    , font_title));
            PdfUtil.settingBorderWidth(cell_acc, 0, 0, 0, 1);
            cell_acc.setLeading(1.2f, 1.2f); //行間の設定
            cell_acc.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_acc.setPaddingBottom(6f);
            table_title.addCell(cell_acc);

            pdfUtil.addCalcPaging(doc, table_title, 0);

            PdfUtil.setCell(table_title, EMP__, 1, Element.ALIGN_LEFT,
                    PdfUtil.getFontEmpty(fontPath));
            int index = 1;

            for (Ntp040DataModel data : param.getNtp040DataModelList()) {
                //日報NO
                PdfPTable table_header =
                        PdfUtil.createTable(1, 100, totalWidth,
                                new float[] {1.0f}, Element.ALIGN_LEFT);
                PdfUtil.setCell(table_header, "No." + String.valueOf(index), 2, Element.ALIGN_LEFT,
                        font_index);
                pdfUtil.addCalcPaging(doc, table_header, 0);

                //日報
                float [] width_main  = {0.2f, 0.8f};
                PdfPTable table_main =
                        PdfUtil.createTable(2, 100, totalWidth, width_main, Element.ALIGN_CENTER);
                PdfPCell cell;
//                PdfUtil.settingBorderWidth(cell, 0, 0, 0, 1);
//                cell.setColspan(2);
//                table_main.addCell(cell);
//                table_main.setHeaderRows(1);

                //タイトル
                cell = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.title"), font_header));
                PdfUtil.settingBorderWidth(cell, 1, 1, 1, 1);
                cell.setGrayFill(GRAY_FILL__);
                table_main.addCell(cell);
                cell = new PdfPCell(new Phrase(
                        PdfUtil.replaseBackslashToYen(data.getTitle()), font_main));
                PdfUtil.settingBorderWidth(cell, 1, 0, 1, 1);
                cell.setPaddingBottom(4f);
                table_main.addCell(cell);

                //時間
                cell = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.time"), font_header));
                PdfUtil.settingBorderWidth(cell, 0, 1, 1, 1);
                cell.setGrayFill(GRAY_FILL__);
                table_main.addCell(cell);
                StringBuilder time = new StringBuilder();
                time.append(data.getFrHour());
                time.append(":");
                if (data.getFrMin() < 10) {
                    time.append("0");
                }
                time.append(data.getFrMin());
                time.append(" 〜 ");
                time.append(data.getToHour());
                time.append(":");
                if (data.getToMin() < 10) {
                    time.append("0");
                }
                time.append(data.getToMin());

                cell = new PdfPCell(new Phrase(time.toString(), font_main));
                PdfUtil.settingBorderWidth(cell, 0, 0, 1, 1);
                cell.setPaddingBottom(4f);
                table_main.addCell(cell);
                //案件
                if (param.getNtp040AnkenCompanyUse() == GSConstNippou.ITEM_BOTH
                        || param.getNtp040AnkenCompanyUse() == GSConstNippou.ITEM_ANKEN_USE) {
                    cell = new PdfPCell(new Phrase(gsMsg.getMessage("ntp.11"), font_header));
                    PdfUtil.settingBorderWidth(cell, 0, 1, 1, 1);
                    cell.setGrayFill(GRAY_FILL__);
                    table_main.addCell(cell);
                    StringBuilder anken = new StringBuilder();
                    if (!StringUtil.isNullZeroString(data.getAnkenName())) {
                        anken.append(gsMsg.getMessage("ntp.29"));
                        anken.append(":");
                        anken.append(NullDefault.getString(data.getAnkenCode(), ""));
                        anken.append("\n");
                        anken.append(data.getAnkenName());
                    }
                    cell = new PdfPCell(new Phrase(
                            PdfUtil.replaseBackslashToYen(anken.toString()), font_main));
                    PdfUtil.settingBorderWidth(cell, 0, 0, 1, 1);
                    cell.setPaddingBottom(4f);
                    table_main.addCell(cell);
                }
                //企業
                if (param.getNtp040AnkenCompanyUse() == GSConstNippou.ITEM_BOTH
                        || param.getNtp040AnkenCompanyUse() == GSConstNippou.ITEM_COMPANY_USE) {
                    cell = new PdfPCell(new Phrase(gsMsg.getMessage("ntp.15"), font_header));
                    PdfUtil.settingBorderWidth(cell, 0, 1, 1, 1);
                    cell.setGrayFill(GRAY_FILL__);
                    table_main.addCell(cell);
                    StringBuilder company = new StringBuilder();
                    if (!StringUtil.isNullZeroString(data.getCompanyCode())) {
                        company.append(gsMsg.getMessage("address.7"));
                        company.append(":");
                        company.append(data.getCompanyCode());
                        company.append("\n");
                    }
                    if (!StringUtil.isNullZeroString(data.getCompanyName())) {
                        company.append(data.getCompanyName());
                    }
                    if (!StringUtil.isNullZeroString(data.getCompanyBaseName())) {
                        company.append(data.getCompanyBaseName());
                    }
                    cell = new PdfPCell(
                            new Phrase(PdfUtil.replaseBackslashToYen(company.toString()),
                                            font_main));
                    PdfUtil.settingBorderWidth(cell, 0, 0, 1, 1);
                    cell.setPaddingBottom(4f);
                    table_main.addCell(cell);
                }
                //内容
                cell = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.content"), font_header));
                PdfUtil.settingBorderWidth(cell, 0, 1, 1, 1);
                cell.setGrayFill(GRAY_FILL__);
                table_main.addCell(cell);
                cell = new PdfPCell(new Phrase(
                        PdfUtil.replaseBackslashToYen(data.getValueStr()),
                        font_main));
                PdfUtil.settingBorderWidth(cell, 0, 0, 1, 1);
                cell.setPaddingBottom(4f);
                table_main.addCell(cell);
                //活動分類/方法
                if (param.getNtp040KtBriHhuUse() == GSConstNippou.ITEM_USE) {
                    cell = new PdfPCell(new Phrase(gsMsg.getMessage("ntp.3") + "/"
                           + gsMsg.getMessage("ntp.31"), font_header));
                    PdfUtil.settingBorderWidth(cell, 0, 1, 1, 1);
                    cell.setGrayFill(GRAY_FILL__);
                    table_main.addCell(cell);
                    StringBuilder ktBriHhu = new StringBuilder();
                    ktBriHhu.append(data.getNtp040DspKtbunrui());
                    ktBriHhu.append(" ");
                    ktBriHhu.append(data.getNtp040DspKthouhou());
                    cell = new PdfPCell(new Phrase(
                            PdfUtil.replaseBackslashToYen(ktBriHhu.toString()), font_main));
                    PdfUtil.settingBorderWidth(cell, 0, 0, 1, 1);
                    cell.setPaddingBottom(4f);
                    table_main.addCell(cell);
                }
                //見込み度
                if (param.getNtp040MikomidoUse() == GSConstNippou.ITEM_USE) {
                    cell = new PdfPCell(new Phrase(gsMsg.getMessage("ntp.32"), font_header));
                    PdfUtil.settingBorderWidth(cell, 0, 1, 1, 1);
                    cell.setGrayFill(GRAY_FILL__);
                    table_main.addCell(cell);
                    cell = new PdfPCell(new Phrase(data.getNtp040DspMikomido() + "%", font_main));
                    PdfUtil.settingBorderWidth(cell, 0, 0, 1, 1);
                    cell.setPaddingBottom(4f);
                    table_main.addCell(cell);

                }
                //添付
                if (param.getNtp040TmpFileUse() == GSConstNippou.ITEM_USE) {
                    cell = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.attached"), font_header));
                    PdfUtil.settingBorderWidth(cell, 0, 1, 1, 1);
                    cell.setGrayFill(GRAY_FILL__);
                    table_main.addCell(cell);
                    StringBuilder tmpFile = new StringBuilder();
                    for (CmnBinfModel filMdl : data.getNtp040FileList()) {
                        tmpFile.append(filMdl.getBinFileName());
                        tmpFile.append(",");
                    }
                    if (tmpFile.length() > 0) {
                        tmpFile.delete(tmpFile.length() - 1, tmpFile.length());
                    }
                    cell = new PdfPCell(new Phrase(
                            PdfUtil.replaseBackslashToYen(tmpFile.toString()),
                            font_main));
                    PdfUtil.settingBorderWidth(cell, 0, 0, 1, 1);
                    cell.setPaddingBottom(4f);
                    table_main.addCell(cell);

                }
                //次のアクション
                if (param.getNtp040NextActionUse() == GSConstNippou.ITEM_USE) {
                    cell = new PdfPCell(new Phrase(gsMsg.getMessage("ntp.96"), font_header));
                    PdfUtil.settingBorderWidth(cell, 0, 1, 1, 1);
                    cell.setGrayFill(GRAY_FILL__);
                    table_main.addCell(cell);

                    StringBuilder nextAct = new StringBuilder();
                    if (data.getActDateKbn() == 1) {
                        nextAct.append(" ");
                        nextAct.append(gsMsg.getMessage("cmn.date2"));
                        nextAct.append("：");
                        nextAct.append(gsMsg.getMessage("cmn.date4"
                                , new String[] {String.valueOf(data.getActionYear())
                                , String.valueOf(data.getActionMonth())
                                , String.valueOf(data.getActionDay())}));
                        nextAct.append("\n");
                    }
                    if (!StringUtil.isNullZeroString(data.getActionStr())) {
                        nextAct.append(data.getActionStr());
                    }
                    cell = new PdfPCell(new Phrase(
                            PdfUtil.replaseBackslashToYen(nextAct.toString()),
                            font_main));
                    PdfUtil.settingBorderWidth(cell, 0, 0, 1, 1);
                    cell.setPaddingBottom(4f);
                    table_main.addCell(cell);

                }
                //登録者：
                cell = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.registant"), font_header));
                PdfUtil.settingBorderWidth(cell, 0, 1, 1, 1);
                cell.setGrayFill(GRAY_FILL__);
                table_main.addCell(cell);
                Font font_adduser;
                if (data.getNtp040AddUsrJkbn() == 9) {
                    font_adduser = font_main_uchikeshi;
                } else {
                    font_adduser = font_main;
                }
                cell = new PdfPCell(new Phrase(
                        PdfUtil.replaseBackslashToYen(data.getNtp040NtpAddUsrName())
                        , font_adduser));
                PdfUtil.settingBorderWidth(cell, 0, 0, 1, 1);
                cell.setPaddingBottom(4f);
                table_main.addCell(cell);

                cell = PdfUtil.setCellRet(EMP__, 2, Element.ALIGN_LEFT,
                        font_main);
                PdfUtil.settingBorderWidth(cell, 0, 0, 0, 0);
                table_main.addCell(cell);


                pdfUtil.addCalcPaging(doc, table_main, 0);


                if (data.getNtp040CommentList() != null && data.getNtp040CommentList().size() > 0) {

                    //コメント
                    width_main  = new float[] {1.0f};
                    PdfPTable table_comment =
                            PdfUtil.createTable(1, 100, totalWidth
                                    , width_main, Element.ALIGN_CENTER);
                    PdfUtil.setCell(table_comment, gsMsg.getMessage("cmn.comment")
                            , 1, Element.ALIGN_LEFT, font_index);



                    PdfPTable writeComment = PdfUtil.createTable(1, 100
                            , totalWidth - 24, Element.ALIGN_LEFT);

                    for (Ntp040CommentModel cmmt : data.getNtp040CommentList()) {
                        String name = cmmt.getNtp040UsrInfMdl().getUsiSei() + " "
                                + cmmt.getNtp040UsrInfMdl().getUsiMei();
                        name = PdfUtil.replaseBackslashToYen(name);
                        if (cmmt.getNtp040UsrInfMdl().getUsrJkbn() == GSConst.JTKBN_DELETE) {
                            PdfUtil.setCell(writeComment, name, 1, Element.ALIGN_LEFT,
                                    font_main_uchikeshi);
                        } else {
                            PdfUtil.setCell(writeComment, name, 1, Element.ALIGN_LEFT,
                                    font_main);
                        }
                        String cmmtStr = cmmt.getNtp040CommentMdl().getNpcComment();
                        cmmtStr = StringUtilHtml.transBRtoCRLF(cmmtStr);
                        cmmtStr = StringUtilHtml.transToText(cmmtStr);
                        cmmtStr = PdfUtil.replaseBackslashToYen(cmmtStr);
                        PdfPCell cmmtCell = new PdfPCell(
                                new Phrase(cmmtStr, font_main));
                        PdfUtil.settingBorderWidth(cmmtCell, 0, 0, 0, 0);
                        cmmtCell.setIndent(10);
                        writeComment.addCell(cmmtCell);
                        PdfPTable table_space =
                                PdfUtil.createTable(1, 100, totalWidth, new float[] {1.0f}
                                        , Element.ALIGN_CENTER);
                        PdfUtil.setCell(table_space, EMP__, 1, Element.ALIGN_LEFT,
                                font_main);
                        PdfPCell spaceCell = new PdfPCell(table_space);
                        PdfUtil.settingBorderWidth(spaceCell, 0, 0, 0, 1);
                        writeComment.addCell(spaceCell);
                        table_space =
                                PdfUtil.createTable(1, 100, totalWidth, new float[] {1.0f}
                                        , Element.ALIGN_CENTER);
                        PdfUtil.setCell(table_space, EMP__, 1, Element.ALIGN_LEFT,
                                font_main);
                        spaceCell = new PdfPCell(table_space);
                        PdfUtil.settingBorderWidth(spaceCell, 0, 0, 0, 0);
                        writeComment.addCell(spaceCell);
                    }
                    PdfPCell allComment = new PdfPCell(writeComment);
                    PdfUtil.settingBorderWidth(allComment, 0, 0, 0, 0);
                    allComment.setPaddingLeft(24f);
                    table_comment.addCell(allComment);
                    pdfUtil.addCalcPaging(doc, table_comment, 0);
                }
                index++;
            }


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
                underContent.showText("GroupSession" + "  " + gsMsg.getMessage("ntp.1"));
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
}