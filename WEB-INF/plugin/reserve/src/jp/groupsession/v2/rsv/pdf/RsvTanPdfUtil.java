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

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstReserve;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rsv.biz.RsvCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約単票Pdf出力に関するUtilクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvTanPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvTanPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RsvTanPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public RsvTanPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 施設予約単票Pdf出力に関するUtilクラスです。
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
    public void createRsvTanPdf(
            RsvTanPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        log__.debug("施設予約単票PDFの生成開始");

        Document doc = null;
        PdfUtil pdfUtil = new PdfUtil();
        /* フォントファイルパス */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント アカウント名
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント ヘッダー
        Font font_header = PdfUtil.getFont10b(fontPath);
        //フォント メイン 10pt 黒
        Font font_main = PdfUtil.getFont10(fontPath);

        //バックカラー（ヘッダ）
        Color color_header = PdfUtil.BG_COLOR_LIGHTBLUE;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ByteArrayOutputStream byteout = null;

        try {
            doc = new Document(PageSize.A4); //(A4 H:842F, W:595F)
            //アウトプットストリームをPDFWriterに設定します。
            byteout = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, byteout);

            //出力するPDFに説明を付与します。
            doc.addTitle(gsMsg.getMessage("cmn.reserve"));
            doc.addAuthor("GroupSession");
            doc.addSubject(gsMsg.getMessage("reserve.src.21"));
            doc.open();

            //文字入力範囲（横）
            float totalWidth = doc.getPageSize().getWidth();

            //タイトル
            float [] width_title  = {0.4f, 0.6f};
            PdfPTable table_title =
                    PdfUtil.createTable(2, 100, totalWidth, width_title, Element.ALIGN_CENTER);

            //施設・施設予約情報
            float [] width_main  = {0.22f, 0.78f};
            PdfPTable table_main =
                    PdfUtil.createTable(2, 100, totalWidth, width_main, Element.ALIGN_CENTER);

            //施設予約表示モード
            String topTitle = gsMsg.getMessage("cmn.reserve")
                    + "[ "
                    + gsMsg.getMessage("reserve.src.21")
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

            PdfPCell cell_main;

            //施設グループ
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.facility.group"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvGrpName()), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            table_main.addCell(cell_main);

            //施設区分
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.47"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(pdfMdl.getPdfRsvDspKbn(), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            table_main.addCell(cell_main);

            //施設名
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.facility.name"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvName()), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            table_main.addCell(cell_main);

            //資産管理番号
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.asset.register.num"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvSisNum()), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            table_main.addCell(cell_main);

            //可変項目の設定
            switch (pdfMdl.getPdfRsvKbn()) {
            //部屋
            case GSConstReserve.RSK_KBN_HEYA:
                __makeCellHeya(pdfMdl, table_main, cell_main, font_header, font_main, color_header);
                break;
            //物品
            case GSConstReserve.RSK_KBN_BUPPIN:
                __makeCellBuppin(
                        pdfMdl, table_main, cell_main, font_header, font_main, color_header);
                break;
            //車
            case GSConstReserve.RSK_KBN_CAR:
                __makeCellCar(pdfMdl, table_main, cell_main, font_header, font_main, color_header);
                break;
            //書籍
            case GSConstReserve.RSK_KBN_BOOK:
                __makeCellBook(pdfMdl, table_main, cell_main, font_header, font_main, color_header);
                break;
            default:
                break;
            }

            //重複登録
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.136"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setPaddingBottom(4f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            String over = null;
            if (pdfMdl.getPdfRsvOverRegKbn().equals("1")) {
                over = gsMsg.getMessage("cmn.not");
            } else {
                over = gsMsg.getMessage("cmn.accepted");
            }

            cell_main = PdfUtil.setCellRet(over, 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            table_main.addCell(cell_main);

            //予約可能期限
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.135"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 0);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            String strKikan = null;
            if (pdfMdl.getPdfRsvKikan() != null && pdfMdl.getPdfRsvKikan().length() > 0) {
                strKikan = pdfMdl.getPdfRsvKikan() + gsMsg.getMessage("cmn.days.after");
            }
            cell_main = PdfUtil.setCellRet(NullDefault.getString(strKikan, ""), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 0);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            table_main.addCell(cell_main);

            //備考
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.memo"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 1);
            cell_main.setMinimumHeight(40);
            cell_main.setPaddingBottom(4f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvBiko()), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
            __settingWidth(cell_main, 1, 0.5f, 1, 1);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);

            //空白
            cell_main = PdfUtil.setCellRet(EMP__, 2, Element.ALIGN_LEFT,
                    PdfUtil.getFontEmpty(fontPath));
            cell_main.setFixedHeight(15);
            table_main.addCell(cell_main);

            //登録者 + 登録日付
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.registant"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 1, 1, 0.5f, 1);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);
            //新規登録者、最終更新者
            PdfPCell cell_regist = new PdfPCell();
            __setRegistrant(width_main, cell_main, gsMsg, font_main, pdfMdl, cell_regist);
            table_main.addCell(cell_regist);

            //印刷
            if (RsvCommonBiz.isUsePrintKbn(appRootPath)) {
                if (RsvCommonBiz.isRskKbnRegCheck(pdfMdl.getPdfRsvKbn())) {
                    if (pdfMdl.getPdfRsvKbn() == GSConstReserve.RSK_KBN_CAR) {
                        cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.print"), 1,
                                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                        __settingWidth(cell_main, 0, 1, 0.5f, 1);
                        cell_main.setLeading(1.1f, 1.1f);
                        cell_main.setBackgroundColor(color_header);
                        cell_main.setPaddingBottom(4f);
                        table_main.addCell(cell_main);

                        cell_main = PdfUtil.setCellRet(pdfMdl.getPdfRsvPrintKbn(), 1,
                                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                        __settingWidth(cell_main, 0, 0.5f, 1, 1);
                        cell_main.setPaddingBottom(4f);
                        cell_main.setLeading(1.1f, 1.1f);
                        table_main.addCell(cell_main);
                    }
                }
            }

            //利用目的
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.72"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 0, 1, 0.5f, 1);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvObj()), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 0, 0.5f, 1, 1);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);

            if (RsvCommonBiz.isRskKbnRegCheck(pdfMdl.getPdfRsvKbn())) {
                if (pdfMdl.getPdfRsvKbn() == GSConstReserve.RSK_KBN_HEYA) {
                    //利用区分
                    cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.use.kbn"), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                    __settingWidth(cell_main, 0, 1, 0.5f, 1);
                    cell_main.setLeading(1.1f, 1.1f);
                    cell_main.setBackgroundColor(color_header);
                    cell_main.setPaddingBottom(4f);
                    table_main.addCell(cell_main);

                    cell_main = PdfUtil.setCellRet(
                            pdfMdl.getPdfRsvUseKbn(), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    __settingWidth(cell_main, 0, 0.5f, 1, 1);
                    cell_main.setPaddingBottom(4f);
                    cell_main.setLeading(1.1f, 1.1f);
                    table_main.addCell(cell_main);

                    //連絡先
                    cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.contact"), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                    __settingWidth(cell_main, 0, 1, 0.5f, 1);
                    cell_main.setPaddingBottom(4f);
                    cell_main.setLeading(1.1f, 1.1f);
                    cell_main.setBackgroundColor(color_header);
                    table_main.addCell(cell_main);

                    cell_main = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvContact()), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    __settingWidth(cell_main, 0, 0.5f, 1, 1);
                    cell_main.setLeading(1.1f, 1.1f);
                    cell_main.setPaddingBottom(4f);
                    table_main.addCell(cell_main);

                    //会議名案内
                    cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.guide"), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                    __settingWidth(cell_main, 0, 1, 0.5f, 1);
                    cell_main.setLeading(1.1f, 1.1f);
                    cell_main.setPaddingBottom(4f);
                    cell_main.setBackgroundColor(color_header);
                    table_main.addCell(cell_main);

                    cell_main = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvGuide()), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    __settingWidth(cell_main, 0, 0.5f, 1, 1);
                    cell_main.setLeading(1.1f, 1.1f);
                    cell_main.setPaddingBottom(4f);
                    table_main.addCell(cell_main);

                    //駐車場見込み台数
                    cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.park.num"), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                    __settingWidth(cell_main, 0, 1, 0.5f, 1);
                    cell_main.setPaddingBottom(4f);
                    cell_main.setLeading(1.1f, 1.1f);
                    cell_main.setBackgroundColor(color_header);
                    table_main.addCell(cell_main);

                    cell_main = PdfUtil.setCellRet(pdfMdl.getPdfRsvParkNum(), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    __settingWidth(cell_main, 0, 0.5f, 1, 1);
                    cell_main.setPaddingBottom(4f);
                    cell_main.setLeading(1.1f, 1.1f);
                    table_main.addCell(cell_main);

                } else if (pdfMdl.getPdfRsvKbn() == GSConstReserve.RSK_KBN_CAR) {
                    //連絡先
                    cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.contact"), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                    __settingWidth(cell_main, 0, 1, 0.5f, 1);
                    cell_main.setLeading(1.1f, 1.1f);
                    cell_main.setBackgroundColor(color_header);
                    table_main.addCell(cell_main);

                    cell_main = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvContact()), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    __settingWidth(cell_main, 0, 0.5f, 1, 1);
                    cell_main.setPaddingBottom(4f);
                    cell_main.setLeading(1.1f, 1.1f);
                    table_main.addCell(cell_main);

                    //行き先
                    cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.dest"), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                    __settingWidth(cell_main, 0, 1, 0.5f, 1);
                    cell_main.setPaddingBottom(4f);
                    cell_main.setLeading(1.1f, 1.1f);
                    cell_main.setBackgroundColor(color_header);
                    table_main.addCell(cell_main);

                    cell_main = PdfUtil.setCellRet(
                            PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvDest()), 1,
                            Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                    __settingWidth(cell_main, 0, 0.5f, 1, 1);
                    cell_main.setPaddingBottom(4f);
                    cell_main.setLeading(1.1f, 1.1f);
                    table_main.addCell(cell_main);
                }

            }

            //期間 ヘッダー
            float [] width_kikan  = {0.5f, 0.5f};
            PdfPTable table_kikan =
                  PdfUtil.createTable(2, 100, totalWidth * 0.3f, width_kikan, Element.ALIGN_CENTER);
            PdfPCell cell_kikan;

            cell_kikan = PdfUtil.setCellRet(gsMsg.getMessage("cmn.period"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_kikan, 0, 0, 0.5f, 0);
            cell_kikan.setLeading(1.1f, 1.1f);
            cell_kikan.setRowspan(2);
            cell_kikan.setBackgroundColor(color_header);
            table_kikan.addCell(cell_kikan);

            cell_kikan = PdfUtil.setCellRet(gsMsg.getMessage("cmn.start"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_kikan, 0, 0.5f, 0, 1);
            cell_kikan.setPaddingBottom(4f);
            cell_kikan.setLeading(1.1f, 1.1f);
            cell_kikan.setBackgroundColor(color_header);
            table_kikan.addCell(cell_kikan);

            cell_kikan = PdfUtil.setCellRet(gsMsg.getMessage("cmn.end"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_kikan, 0, 0.5f, 0, 0);
            cell_kikan.setPaddingBottom(4f);
            cell_kikan.setLeading(1.1f, 1.1f);
            cell_kikan.setBackgroundColor(color_header);
            table_kikan.addCell(cell_kikan);

            cell_main = new PdfPCell(table_kikan);
            cell_main.setColspan(1);
            __settingWidth(cell_main, 0, 1, 0.5f, 1);
            cell_main.setRowspan(2);
            table_main.addCell(cell_main);
            //期間 ヘッダー 終わり

            //期間 データ
            //期間 開始
            cell_main = PdfUtil.setCellRet(pdfMdl.getPdfRsvFromDate(), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 0, 0.5f, 1, 1);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            table_main.addCell(cell_main);

            //期間 終了
            cell_main = PdfUtil.setCellRet(pdfMdl.getPdfRsvToDate(), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 0, 0.5f, 1, 1);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            table_main.addCell(cell_main);

            //期間 データ 終わり

            //内容
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.content"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 0, 1, 0.5f, 1);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setMinimumHeight(40);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(
                    PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvSubject()), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_TOP, font_main);
            __settingWidth(cell_main, 0, 0.5f, 1, 1);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);


            String strEditKbn = null;
            switch (pdfMdl.getPdfRsvEditKbn()) {
            case GSConstReserve.EDIT_AUTH_NONE:
                strEditKbn = gsMsg.getMessage("cmn.nolimit");
                break;

            case GSConstReserve.EDIT_AUTH_PER_AND_ADU:
                strEditKbn = gsMsg.getMessage("cmn.only.principal.or.registant");
                break;

            case GSConstReserve.EDIT_AUTH_GRP_AND_ADU:
                strEditKbn = gsMsg.getMessage("cmn.only.affiliation.group.membership");
                break;

            default:
                strEditKbn = "";
                break;
            }

            //編集権限
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.edit.permissions"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 0, 1, 0.5f, 1);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(strEditKbn, 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 0, 0.5f, 1, 1);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);


            String strPublicKbn = null;
            switch (pdfMdl.getPdfRsvPublicKbn()) {
                case GSConstReserve.PUBLIC_KBN_ALL:
                    strPublicKbn = gsMsg.getMessage("cmn.public");
                    break;

                case GSConstReserve.PUBLIC_KBN_PLANS:
                    strPublicKbn = gsMsg.getMessage("reserve.175");
                    break;

                case GSConstReserve.PUBLIC_KBN_GROUP:
                    strPublicKbn = gsMsg.getMessage("reserve.176");
                    break;

                case GSConstReserve.PUBLIC_KBN_USRGRP:
                    strPublicKbn = gsMsg.getMessage("reserve.187");
                    break;

                case GSConstReserve.PUBLIC_KBN_TITLE:
                    strPublicKbn = gsMsg.getMessage("reserve.189");
                    break;

                default:
                    strPublicKbn = "";
                    break;
            }

            //公開区分
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.public.kbn"), 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            __settingWidth(cell_main, 0, 1, 0.5f, 1);
            cell_main.setPaddingBottom(4f);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            cell_main = PdfUtil.setCellRet(strPublicKbn, 1,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
            __settingWidth(cell_main, 0, 0.5f, 1, 1);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setPaddingBottom(4f);
            table_main.addCell(cell_main);

            if (pdfMdl.getPdfRsvPublicKbn() == GSConstReserve.PUBLIC_KBN_USRGRP) {

                //スケジュール登録
                cell_main = PdfUtil.setCellRet(gsMsg.getMessage("reserve.190"), 1,
                        Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                __settingWidth(cell_main, 0, 1, 0.5f, 1);
                cell_main.setPaddingBottom(4f);
                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setBackgroundColor(color_header);
                table_main.addCell(cell_main);

                String strPubTarget = "";
                for (int idx = 0; idx < pdfMdl.getPdfRsvPubUsrGrpList().size(); idx++) {
                    if (idx > 0) {
                        strPubTarget += "\n";
                    }
                    strPubTarget += pdfMdl.getPdfRsvPubUsrGrpList().get(idx).getLabel();
                }

                strPubTarget = PdfUtil.replaseBackslashToYen(strPubTarget);
                cell_main = PdfUtil.setCellRet(strPubTarget, 1,
                        Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                __settingWidth(cell_main, 0, 0.5f, 1, 1);
                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);
            }

            if (RsvCommonBiz.isRskKbnRegCheck(pdfMdl.getPdfRsvKbn())) {
                StringBuilder strHeadTanto = new StringBuilder();
                strHeadTanto.append(gsMsg.getMessage("reserve.busyo"));
                strHeadTanto.append("/");
                String strTanto = "";
                if (pdfMdl.getPdfRsvKbn() == GSConstReserve.RSK_KBN_HEYA) {
                    strTanto = gsMsg.getMessage("reserve.use.name.1");
                } else if (pdfMdl.getPdfRsvKbn() == GSConstReserve.RSK_KBN_CAR) {
                    strTanto = gsMsg.getMessage("reserve.use.name.2");
                }
                strHeadTanto.append(strTanto);
                strHeadTanto.append("/");
                strHeadTanto.append(gsMsg.getMessage("reserve.use.num"));

                cell_main = PdfUtil.setCellRet(strHeadTanto.toString(), 1,
                        Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                __settingWidth(cell_main, 0, 1, 0.5f, 1);
                cell_main.setPaddingBottom(4f);
                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setBackgroundColor(color_header);
                table_main.addCell(cell_main);

                StringBuilder strDataTanto = new StringBuilder();
                strDataTanto.append(gsMsg.getMessage("reserve.busyo"));
                strDataTanto.append("  ");
                strDataTanto.append(pdfMdl.getPdfRsvBusyo());
                strDataTanto.append("\n");
                strDataTanto.append(strTanto);
                strDataTanto.append("  ");
                strDataTanto.append(pdfMdl.getPdfRsvUseName());
                if (!StringUtil.isNullZeroStringSpace(pdfMdl.getPdfRsvUseNum())) {
                    strDataTanto.append("\n");
                    strDataTanto.append("他");
                    strDataTanto.append("  ");
                    strDataTanto.append(pdfMdl.getPdfRsvUseNum());
                    strDataTanto.append("人");
                }
                cell_main = PdfUtil.setCellRet(
                        PdfUtil.replaseBackslashToYen(strDataTanto.toString()), 1,
                        Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                __settingWidth(cell_main, 0, 0.5f, 1, 1);
                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);
            }


            if (pdfMdl.getPdfRsvSchUseFlg() == GSConst.PLUGIN_USE) {

                //スケジュール登録
                cell_main = PdfUtil.setCellRet(gsMsg.getMessage("schedule.3"), 1,
                        Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
                __settingWidth(cell_main, 0, 1, 0.5f, 1);
                cell_main.setPaddingBottom(4f);
                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setBackgroundColor(color_header);
                table_main.addCell(cell_main);

                String strSch = "";
                if (pdfMdl.getPdfRsvSchKbn() == GSConstReserve.RSV_SCHKBN_GROUP) {
                    strSch += "・" + gsMsg.getMessage("cmn.group");
                    strSch += "\n";
                    strSch += pdfMdl.getPdfRsvSchGrpName();

                } else {
                    if (pdfMdl.getPdfRsvSchUserList() != null
                            && pdfMdl.getPdfRsvSchUserList().size() > 0) {
                        strSch += "・" + gsMsg.getMessage("cmn.user");
                        for (String name : pdfMdl.getPdfRsvSchUserList()) {
                            strSch += "\n";
                            strSch += name;
                        }
                    }
                }
                strSch = PdfUtil.replaseBackslashToYen(strSch);
                cell_main = PdfUtil.setCellRet(strSch, 1,
                        Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
                __settingWidth(cell_main, 0, 0.5f, 1, 1);
                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);
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

    /**
     *
     * <br>[機  能]新規登録者名・登録日時、最終更新者名・更新日時を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param width_main セルの幅
     * @param cell_main テーブル内要素
     * @param gsMsg メッセージ
     * @param font_main フォント
     * @param pdfMdl PDF出力用モデル
     * @param cell_regist 新規登録者名・登録日時、最終更新者名・更新日時
     */
    private void __setRegistrant(
            float[] width_main,
            PdfPCell cell_main,
            GsMessage gsMsg,
            Font font_main,
            RsvTanPdfModel pdfMdl,
            PdfPCell cell_regist) throws Exception{

        float[] width_regist = {0.5f, 0.5f};
        //登録者、登録日付表示用テーブル
        PdfPTable table_regist =
                PdfUtil.createTable(
                        2, 100, width_main[1], width_regist, Element.ALIGN_CENTER);
        StringBuilder registrant = new StringBuilder();
        //新規登録者
        registrant.append(gsMsg.getMessage("reserve.178") + " : ");
        registrant.append(pdfMdl.getPdfRsvAddName());
        cell_main = PdfUtil.setCellRet(registrant.toString(), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
        cell_main.setLeading(1.1f, 1.1f);
        cell_main.setPaddingBottom(4f);
        table_regist.addCell(cell_main);
        registrant = new StringBuilder();
        registrant.append(gsMsg.getMessage("schedule.src.84") + " : ");
        registrant.append(pdfMdl.getPdfRsvAddDate());
        registrant.append("\n");
        cell_main = PdfUtil.setCellRet(registrant.toString(), 1,
                Element.ALIGN_RIGHT, Element.ALIGN_MIDDLE, font_main);
        cell_main.setLeading(1.1f, 1.1f);
        cell_main.setPaddingBottom(4f);
        table_regist.addCell(cell_main);
        registrant = new StringBuilder();
        //最終更新者
        registrant.append(gsMsg.getMessage("reserve.179") + " : ");
        registrant.append(pdfMdl.getPdfRsvEditName());
        cell_main = PdfUtil.setCellRet(registrant.toString(), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_main);
        cell_main.setLeading(1.1f, 1.1f);
        cell_main.setPaddingBottom(4f);
        table_regist.addCell(cell_main);
        registrant = new StringBuilder();
        registrant.append(gsMsg.getMessage("schedule.src.85") + " : ");
        registrant.append(pdfMdl.getPdfRsvEditDate());
        cell_main = PdfUtil.setCellRet(registrant.toString(), 1,
                Element.ALIGN_RIGHT, Element.ALIGN_MIDDLE, font_main);
        cell_main.setLeading(1.1f, 1.1f);
        cell_main.setPaddingBottom(4f);
        table_regist.addCell(cell_main);
        //枠線の太さをそろえるため、セルに変換
        cell_regist.addElement(table_regist);
        __settingWidth(cell_regist, 1, 0.5f, 1, 1);
    }


    /**
     * <br>[機  能] 施設情報を生成する（部屋）
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl PDFデータモデル
     * @param tableMain PdfPTable
     * @param cellMain PdfPCell
     * @param fontHeader ヘッダーフォント
     * @param fontMain メインフォント
     * @param colorHeader ヘッダーバックカラー
     * @throws Exception 実行エラー
     */
    private void __makeCellHeya(
            RsvTanPdfModel pdfMdl,
            PdfPTable tableMain,
            PdfPCell cellMain,
            Font fontHeader,
            Font fontMain,
            Color colorHeader) throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //座席数
        cellMain = PdfUtil.setCellRet(gsMsg.getMessage("reserve.128"), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        tableMain.addCell(cellMain);

        cellMain = PdfUtil.setCellRet(pdfMdl.getPdfRsvSeatNum(), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);

        //喫煙
        cellMain = PdfUtil.setCellRet(gsMsg.getMessage("reserve.132"), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        tableMain.addCell(cellMain);

        String smoking = null;
        if (pdfMdl.getPdfRsvSmoking().equals("1")) {
            smoking = gsMsg.getMessage("cmn.not");
        } else {
            smoking = gsMsg.getMessage("cmn.accepted");
        }

        cellMain = PdfUtil.setCellRet(smoking, 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);
    }

    /**
     * <br>[機  能] 施設情報を生成する（物品）
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl PDFデータモデル
     * @param tableMain PdfPTable
     * @param cellMain PdfPCell
     * @param fontHeader ヘッダーフォント
     * @param fontMain メインフォント
     * @param colorHeader ヘッダーバックカラー
     * @throws Exception 実行エラー
     */
    private void __makeCellBuppin(
            RsvTanPdfModel pdfMdl,
            PdfPTable tableMain,
            PdfPCell cellMain,
            Font fontHeader,
            Font fontMain,
            Color colorHeader) throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //個数
        cellMain = PdfUtil.setCellRet(gsMsg.getMessage("reserve.130"), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        tableMain.addCell(cellMain);

        cellMain = PdfUtil.setCellRet(pdfMdl.getPdfRsvNum(), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);

        //社外持出し
        cellMain = PdfUtil.setCellRet(gsMsg.getMessage("reserve.133"), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        tableMain.addCell(cellMain);

        String take = null;
        if (pdfMdl.getPdfRsvTakeOut().equals("1")) {
            take = gsMsg.getMessage("cmn.not");
        } else {
            take = gsMsg.getMessage("cmn.accepted");
        }

        cellMain = PdfUtil.setCellRet(take, 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);
    }

    /**
     * <br>[機  能] 施設情報を生成する（車）
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl PDFデータモデル
     * @param tableMain PdfPTable
     * @param cellMain PdfPCell
     * @param fontHeader ヘッダーフォント
     * @param fontMain メインフォント
     * @param colorHeader ヘッダーバックカラー
     * @throws Exception 実行エラー
     */
    private void __makeCellCar(
            RsvTanPdfModel pdfMdl,
            PdfPTable tableMain,
            PdfPCell cellMain,
            Font fontHeader,
            Font fontMain,
            Color colorHeader) throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //ナンバー
        cellMain = PdfUtil.setCellRet(gsMsg.getMessage("reserve.134"), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        tableMain.addCell(cellMain);

        cellMain = PdfUtil.setCellRet(
                PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvCarNumber()), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);

        //乗員数
        cellMain = PdfUtil.setCellRet(gsMsg.getMessage("reserve.129"), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        tableMain.addCell(cellMain);

        cellMain = PdfUtil.setCellRet(pdfMdl.getPdfRsvCarSeatNum(), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);

        //喫煙
        cellMain = PdfUtil.setCellRet(gsMsg.getMessage("reserve.132"), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        tableMain.addCell(cellMain);

        String smoking = null;
        if (pdfMdl.getPdfRsvSmoking().equals("1")) {
            smoking = gsMsg.getMessage("cmn.not");
        } else {
            smoking = gsMsg.getMessage("cmn.accepted");
        }

        cellMain = PdfUtil.setCellRet(smoking, 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);

    }

    /**
     * <br>[機  能] 施設情報を生成する（書籍）
     * <br>[解  説]
     * <br>[備  考]
     * @param pdfMdl PDFデータモデル
     * @param tableMain PdfPTable
     * @param cellMain PdfPCell
     * @param fontHeader ヘッダーフォント
     * @param fontMain メインフォント
     * @param colorHeader ヘッダーバックカラー
     * @throws Exception 実行エラー
     */
    private void __makeCellBook(
            RsvTanPdfModel pdfMdl,
            PdfPTable tableMain,
            PdfPCell cellMain,
            Font fontHeader,
            Font fontMain,
            Color colorHeader) throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl__);

        //ISBN
        cellMain = PdfUtil.setCellRet(GSConstReserve.RSK_TEXT_ISBN, 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        cellMain.setPaddingBottom(4f);
        tableMain.addCell(cellMain);

        cellMain = PdfUtil.setCellRet(
                PdfUtil.replaseBackslashToYen(pdfMdl.getPdfRsvBookIsbn()), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);

        //冊数
        cellMain = PdfUtil.setCellRet(gsMsg.getMessage("reserve.131"), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        tableMain.addCell(cellMain);

        cellMain = PdfUtil.setCellRet(pdfMdl.getPdfRsvBookNum(), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);

        //社外持出し
        cellMain = PdfUtil.setCellRet(gsMsg.getMessage("reserve.133"), 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontHeader);
        __settingWidth(cellMain, 1, 1, 0.5f, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        cellMain.setBackgroundColor(colorHeader);
        tableMain.addCell(cellMain);

        String take = null;
        if (pdfMdl.getPdfRsvTakeOut().equals("1")) {
            take = gsMsg.getMessage("cmn.not");
        } else {
            take = gsMsg.getMessage("cmn.accepted");
        }

        cellMain = PdfUtil.setCellRet(take, 1,
                Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, fontMain);
        __settingWidth(cellMain, 1, 0.5f, 1, 0);
        cellMain.setPaddingBottom(4f);
        cellMain.setLeading(1.1f, 1.1f);
        tableMain.addCell(cellMain);
    }

}