package jp.groupsession.v2.rsv.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
import jp.groupsession.v2.rsv.RsvDailyLineModel;
import jp.groupsession.v2.rsv.RsvDailyValueModel;
import jp.groupsession.v2.rsv.RsvSisetuModel;
import jp.groupsession.v2.rsv.RsvWeekModel;
import jp.groupsession.v2.rsv.RsvYoyakuDayModel;
import jp.groupsession.v2.rsv.RsvYoyakuModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 施設予約[日間]PDF出力に関するUtilクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvNikPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvNikPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RsvNikPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public RsvNikPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 日間施設予約Pdf出力に関するUtilクラスです。
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
    public void createRsvSyukanPdf(
            RsvNikPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        log__.debug("施設予約 日間PDFの生成開始");

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

        //バックカラー（ヘッダ）
        Color color_header = PdfUtil.BG_COLOR_DARKBLUE;
        //バックカラー（日にち＆曜日）
        Color color_date = PdfUtil.BG_COLOR_LIGHTGRAY;
        //バックカラー（施設予約）
        Color color_yyk = PdfUtil.BG_COLOR_LIGHTYELLOW;

        GsMessage gsMsg = new GsMessage(reqMdl__);
        ByteArrayOutputStream byteout = null;

        try {
            PdfUtil pdfUtil = new PdfUtil();
            doc = new Document(PageSize.A4.rotate()); //(595F,842F)
            //アウトプットストリームをPDFWriterに設定します。
            byteout = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, byteout);

            String subject = gsMsg.getMessage("cmn.reserve")
                    + " " + gsMsg.getMessage("cmn.days2");
            //出力するPDFに説明を付与します。
            doc.addAuthor("GroupSession");
            doc.addSubject(subject);

            doc.open();

            //文字入力範囲（横）
            float totalWidth = 920;
            //施設情報の高さ設定
            float rsv_height = 40f;

            //タイトル
            float [] width_title  = {0.2f, 0.8f};
            PdfPTable table_title =
                    PdfUtil.createTable(2, 100, totalWidth, width_title, Element.ALIGN_CENTER);

            //施設予約
            float [] width_main  = {0.15f, 0.75f};
            PdfPTable table_main =
                    PdfUtil.createTable(2, 100, totalWidth, width_main, Element.ALIGN_CENTER);


            //施設予約表示モード
            String topTitle = NullDefault.getString(
                    gsMsg.getMessage("cmn.reserve")
                    + "["
                    + gsMsg.getMessage("cmn.days2")
                    + "]", EMP__);
            PdfPCell cell_title = PdfUtil.setCellRet(
                    topTitle, 0, Element.ALIGN_JUSTIFIED_ALL, font_title);
            settingWidth(cell_title, 0, 0, 0, 1);
            cell_title.setLeading(1.2f, 1.2f); //行間の設定
            cell_title.setPaddingBottom(5f);
            table_title.addCell(cell_title);

            PdfUtil.setCell(
                    table_title, EMP__, 1, Element.ALIGN_LEFT, PdfUtil.getFontEmpty(fontPath));

            //空白行
            PdfUtil.setCell(
                    table_title, EMP__, 2, Element.ALIGN_LEFT, PdfUtil.getFontEmpty(fontPath));

            PdfPCell cell_main;

            //施設予約
            //ヘッダー 年月
            cell_main = PdfUtil.setCellRet(pdfMdl.getRsvNikHeadDate()
                    + " "
                    + pdfMdl.getDspRokuyou(), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            settingWidth(cell_main, 1, 1, 0, 0);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            //表示グループ
            String dispGroup = gsMsg.getMessage("cmn.show.group")
                    + "：" + NullDefault.getString(pdfMdl.getRsvNikdspGroup(), EMP__);
            dispGroup = PdfUtil.replaseBackslashToYen(dispGroup);
            cell_main = PdfUtil.setCellRet(dispGroup, 7,
                    Element.ALIGN_LEFT, Element.ALIGN_MIDDLE, font_header);
            settingWidth(cell_main, 1, 0, 1, 0);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            table_main.addCell(cell_main);

            //施設名
            cell_main = PdfUtil.setCellRet(gsMsg.getMessage("cmn.facility.name"), 1,
                    Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, font_header);
            settingWidth(cell_main, 0.5f, 1, 0.25f, 1);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_date);
            table_main.addCell(cell_main);

            //メモリ数
            int cellCnt = 0;
            cellCnt = pdfMdl.getRsvNikMemoriCount() * pdfMdl.getRsvNikTimeChartList().size();
            //１件ごとのスケジュールデータ用テーブル
            PdfPTable table_rsv = new PdfPTable(cellCnt);
            table_rsv.setWidthPercentage(100);
            table_rsv.setHorizontalAlignment(Element.ALIGN_CENTER);

            //ヘッダー 時間
            PdfPCell cell_strHour = null;
            for (String strHour : pdfMdl.getRsvNikTimeChartList()) {
                cell_strHour = PdfUtil.setCellRet(strHour, pdfMdl.getRsvNikMemoriCount(),
                        Element.ALIGN_CENTER, font_header);
                settingWidth(cell_strHour, 0.5f, 0.25f, 0.25f, 0);
                table_rsv.addCell(cell_strHour);
            }

            float leftWidth = 0;
            float rightWidth = 0;
            for (int i = 0; i < cellCnt; i++) {
                //ヘッダー 時間メモリ
                PdfPCell cell_memori =
                        new PdfPCell(new Phrase(" ", PdfUtil.getFontEmpty(fontPath)));
                if (i == 0) {
                    leftWidth = 0;
                    rightWidth = 0.25f;
                } else if (i == cellCnt - 1) {
                    leftWidth = 0.25f;
                    rightWidth = 0;
                } else {
                    leftWidth = 0.25f;
                    rightWidth = 0.25f;
                }
                settingWidth(cell_memori, 0, leftWidth, rightWidth, 0);
                cell_memori.setBackgroundColor(color_date);
                cell_memori.setFixedHeight(2);
                table_rsv.addCell(cell_memori);
            }


            cell_main = new PdfPCell(table_rsv);
            settingWidth(cell_main, 0, 0.25f, 1, 1);
            cell_main.setBackgroundColor(color_date);
            cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_main.addCell(cell_main);

            table_main.setHeaderRows(2);

            //施設＆施設予約の描写
            for (RsvSisetuModel sisetuMdl : pdfMdl.getRsvNikDaylyList()) {
                //予約情報を画面表示用に格納します
                ArrayList<RsvDailyLineModel> dailyList =
                        __getDailyLineList(
                                sisetuMdl, pdfMdl.getRsvNikDspDate(),
                                pdfMdl.getRsvNikFrom(), pdfMdl.getRsvNikTo(),
                                pdfMdl.getRsvNikMemoriCount());

                //施設情報表示部分
                PdfPTable sisetuTable = PdfUtil.createTable(1, 100, 115, Element.ALIGN_CENTER);
                PdfUtil.setCell(
                        sisetuTable, PdfUtil.replaseBackslashToYen(sisetuMdl.getRsdName()),
                        1, Element.ALIGN_LEFT, font_header);
                String sisetuInfo = __createSisetuInfo(pdfMdl, sisetuMdl);
                sisetuInfo = PdfUtil.replaseBackslashToYen(sisetuInfo);
                PdfUtil.setCell(sisetuTable, sisetuInfo, 1, Element.ALIGN_LEFT, font_main_black);

                cell_main = new PdfPCell(sisetuTable);
                cell_main.setMinimumHeight(rsv_height);
                cell_main.setRowspan(dailyList.size());
                settingWidth(cell_main, 0, 1, 0.25f, 1);
                cell_main.setPaddingBottom(1f);
                table_main.addCell(cell_main);

                //施設ごとの予約行数のカウンタ
                int lineCount = 1;

                //施設ごとの施設予約表示
                for (RsvDailyLineModel lineMdl : dailyList) {
                    PdfPTable yoyakuTable = PdfUtil.createTable(
                            cellCnt, 100, totalWidth * 0.75f, Element.ALIGN_CENTER);

                    //１行文のデータ
                    for (RsvDailyValueModel valueMdl : lineMdl.getLineList()) {

                        StringBuilder yykTitle;
                        PdfPCell yoyakuCell = null;

                        //予約情報があった場合
                        if (valueMdl.getYoyakuMdl() != null) {
                            //時間設定
                            yykTitle = new StringBuilder(
                                    valueMdl.getYoyakuMdl().getYrkRiyoDateStr() + "\n");

                            Font yykFont = font_main_blue;
                            //承認状況
                            if (valueMdl.getYoyakuMdl().getRsyApprStatus()
                                    == GSConstReserve.RSY_APPR_STATUS_NOAPPR) {
                                yykTitle.append("(" + gsMsg.getMessage("reserve.appr.st1") + ")");
                                yykFont = font_main_orange;
                            //承認区分
                            } else if (valueMdl.getYoyakuMdl().getRsyApprKbn()
                                    == GSConstReserve.RSY_APPR_KBN_REJECTION) {
                                yykTitle.append("(" + gsMsg.getMessage("reserve.appr.st2") + ")");
                                yykFont = font_main_black;
                            }

                            //閲覧できない施設予約は黒文字
                            if (valueMdl.getYoyakuMdl().getPublic() 
                                    == GSConstReserve.PUBLIC_KBN_PLANS) {
                                yykFont = font_main_black;
                            }

                            //利用目的/名前
                            yykTitle.append(NullDefault.getString(
                                    valueMdl.getYoyakuMdl().getRsyMok(), ""));
                            if (!StringUtil.isNullZeroString(valueMdl.getYoyakuMdl().getRsyMok())
                                    && !StringUtil.isNullZeroString(
                                            valueMdl.getYoyakuMdl().getYrkName())) {
                                yykTitle.append(" / ");
                            }
                            yykTitle.append(NullDefault.getString(
                                    valueMdl.getYoyakuMdl().getYrkName(), ""));

                            yoyakuCell = PdfUtil.setCellRet(
                                    PdfUtil.replaseBackslashToYen(yykTitle.toString()),
                                    valueMdl.getCols(), Element.ALIGN_CENTER,
                                    Element.ALIGN_MIDDLE, yykFont);
                            yoyakuCell.setPaddingBottom(4f);
                            yoyakuCell.setBackgroundColor(color_yyk);

                        //予約情報がない場合の処理
                        } else {
                            yoyakuCell = PdfUtil.setCellRet("",
                                    valueMdl.getCols(), Element.ALIGN_CENTER, font_main_black);
                        }
                        settingWidth(yoyakuCell, 0, 0.25f, 0.25f, 0);
                        yoyakuTable.addCell(yoyakuCell);
                    }

                    //施設に対する施設予約のデータが１件も無かった場合
                    if (yoyakuTable == null) {
                        cell_main = new PdfPCell(new Phrase("", font_main_black));
                    } else {
                        cell_main = new PdfPCell(yoyakuTable);
                    }

                    if ((dailyList.size() == 1) || dailyList.size() == lineCount) {
                        settingWidth(cell_main, 0, 0.25f, 1, 1);
                    } else  {
                        settingWidth(cell_main, 0, 0.25f, 1, 0.5f);
                    }
                    cell_main.setMinimumHeight(rsv_height);
                    table_main.addCell(cell_main);

                    lineCount++;
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

                //ページ
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
     * <br>[機  能] 施設予約情報を作成します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param pdfMdl PDFモデル
     * @param sisetuMdl 施設毎の予約情報リスト
     * @return sisetuInfo 施設予約情報
     */
    private String __createSisetuInfo(RsvNikPdfModel pdfMdl, RsvSisetuModel sisetuMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //施設名
        String sisetuInfo = "";
        //施設ID
        if (sisetuMdl.getRsdInfoSisetuIdDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON
                && !StringUtil.isNullZeroString(sisetuMdl.getRsdSisetuId())) {
            sisetuInfo += " \n";
            sisetuInfo +=  gsMsg.getMessage("reserve.55")
                    + ":" + sisetuMdl.getRsdSisetuId();
        }
        //資産管理番号
        if (sisetuMdl.getRsdInfoSisanKanriDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON
                && !StringUtil.isNullZeroString(sisetuMdl.getRsdSisanKanri())) {
            sisetuInfo += " \n";
            sisetuInfo +=  gsMsg.getMessage("cmn.asset.register.num")
                    + ":" + sisetuMdl.getRsdSisanKanri();
        }
        //座席数、個数、乗員数、冊数など
        if (sisetuMdl.getRsdInfoProp1ValueDspKbn()  == GSConstReserve.SISETU_DATA_DSP_ON
                && !StringUtil.isNullZeroString(sisetuMdl.getRsdProp1Value())) {
            sisetuInfo += " \n";
            sisetuInfo +=  sisetuMdl.getRsvPropHeaderName1()
                    + ":" + sisetuMdl.getRsdProp1Value();
        }
        //喫煙
        if (sisetuMdl.getRsdInfoProp2ValueDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON) {
            sisetuInfo += " \n";
            sisetuInfo +=  sisetuMdl.getRsvPropHeaderName2()
                    + ":";
            if (sisetuMdl.getRsdProp2Value().equals(String.valueOf(GSConstReserve.PROP_KBN_KA))) {
                sisetuInfo += gsMsg.getMessage("cmn.accepted");
            } else {
                sisetuInfo += gsMsg.getMessage("cmn.not");
            }
        }
        //社外持出し
        if (sisetuMdl.getRsdInfoProp3ValueDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON) {
            sisetuInfo += " \n";
            sisetuInfo += sisetuMdl.getRsvPropHeaderName3()
                    + ":";
            if (sisetuMdl.getRsdProp3Value().equals(String.valueOf(GSConstReserve.PROP_KBN_KA))) {
                sisetuInfo += gsMsg.getMessage("cmn.accepted");
            } else {
                sisetuInfo += gsMsg.getMessage("cmn.not");
            }
        }
        //車のナンバー
        if (sisetuMdl.getRsdInfoProp4ValueDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON
                && !StringUtil.isNullZeroString(sisetuMdl.getRsdProp4Value())) {
            sisetuInfo += " \n";
            sisetuInfo += sisetuMdl.getRsvPropHeaderName4()
                    + ":" + sisetuMdl.getRsdProp4Value();
        }
        //書籍のSIBN
        if (sisetuMdl.getRsdInfoProp5ValueDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON
                && !StringUtil.isNullZeroString(sisetuMdl.getRsdProp5Value())) {
            sisetuInfo += " \n";
            sisetuInfo += sisetuMdl.getRsvPropHeaderName5()
                    + ":" + sisetuMdl.getRsdProp5Value();
        }
        //予約可能期限
        if (sisetuMdl.getRsdInfoProp6ValueDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON
                && !StringUtil.isNullZeroString(sisetuMdl.getRsdProp6Value())) {
            sisetuInfo += " \n";
            sisetuInfo += sisetuMdl.getRsvPropHeaderName6()
                    + ":" + sisetuMdl.getRsdProp6Value()
                    + gsMsg.getMessage("cmn.days.after");
        }
        //重複登録
        if (sisetuMdl.getRsdInfoProp7ValueDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON) {
            sisetuInfo += " \n";
            sisetuInfo += sisetuMdl.getRsvPropHeaderName7()
                    + ":";
            if (sisetuMdl.getRsdProp7Value().equals(String.valueOf(GSConstReserve.PROP_KBN_KA))) {
                sisetuInfo += gsMsg.getMessage("cmn.accepted");
            } else {
                sisetuInfo += gsMsg.getMessage("cmn.not");
            }
        }
        //承認
        if (sisetuMdl.isRsdApprDspFlg()) {
            sisetuInfo += " \n";
            sisetuInfo += gsMsg.getMessage("reserve.appr.set.title") + ":";
            if (sisetuMdl.isRsdApprKbnFlg()) {
                sisetuInfo += gsMsg.getMessage("reserve.appr.set.kbn1");
            } else {
                sisetuInfo += gsMsg.getMessage("reserve.appr.set.kbn2");
            }
        }
        //場所
        if (sisetuMdl.getRsdInfoPlaComDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON
                && !StringUtil.isNullZeroString(sisetuMdl.getRsdInfoPlaCom())) {
            sisetuInfo += " \n";
            sisetuInfo += gsMsg.getMessage("reserve.location.comments")
                    + ":" + sisetuMdl.getRsdInfoPlaCom();
        }
        //備考
        if (sisetuMdl.getRsdInfoBikoDspKbn() == GSConstReserve.SISETU_DATA_DSP_ON
                && !StringUtil.isNullZeroString(sisetuMdl.getRsdBiko())) {
            sisetuInfo += " \n";
            sisetuInfo += gsMsg.getMessage("cmn.memo")
                    + ":" + sisetuMdl.getRsdBiko();
        }
        return sisetuInfo;
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


    /**
     * 線の太さを設定する。
     *
     * @param sisetuMdl セル情報
     * @param dspDate セル上部の線の太さ
     * @param intFrom セル左側の線の太さ
     * @param intTo セル右側の線の太さ
     * @param hourDivCnt セル下部の線の太さ
     * @return ret ArrayList<RsvDailyLineModel>
     * */
    private ArrayList<RsvDailyLineModel> __getDailyLineList(RsvSisetuModel sisetuMdl,
            String dspDate, int intFrom, int intTo, int hourDivCnt) {
        ArrayList<RsvDailyLineModel> ret = null;

        ArrayList<RsvWeekModel> weekList = sisetuMdl.getRsvWeekList();
        RsvWeekModel weekMdl = weekList.get(0);
        ArrayList<RsvYoyakuDayModel> dayList = weekMdl.getYoyakuDayList();
        RsvYoyakuDayModel yoyakuDayMdl = dayList.get(0);
        ArrayList<RsvYoyakuModel> yoyakuList = yoyakuDayMdl.getYoyakuList();

        ret = __getDspScheduleList(yoyakuList, dspDate, intFrom, intTo, hourDivCnt);

        return ret;
    }

    /**
     * <br>予約情報を画面表示用に格納します
     * @param yoyakuList 予約リスト
     * @param dspDateStr 表示日付
     * @param frHour 表示開始時間
     * @param toHour 表示終了時間
     * @param hourDivCnt 時間間隔
     * @return ArrayList
     */
    private ArrayList<RsvDailyLineModel> __getDspScheduleList(
            ArrayList<RsvYoyakuModel> yoyakuList,
            String dspDateStr,
            int frHour,
            int toHour,
            int hourDivCnt) {

        ArrayList<RsvDailyLineModel> dailyList = new ArrayList<RsvDailyLineModel>();

        //1行分の予約
        RsvDailyLineModel lineMdl = new RsvDailyLineModel();
        ArrayList<RsvDailyValueModel> lineList = new ArrayList<RsvDailyValueModel>();

        //1予約
        RsvDailyValueModel valueMdl = null;

        //予約情報無し
        if (yoyakuList.isEmpty()) {
            valueMdl = new RsvDailyValueModel();
            valueMdl.setYoyakuMdl(null);
            int defCols = (toHour - frHour + 1) * hourDivCnt;
            valueMdl.setCols(defCols);
            lineList.add(valueMdl);
            lineMdl.setLineList(lineList);
            dailyList.add(lineMdl);
            return dailyList;
        }

        //出力済情報の格納用
        HashMap<String, RsvYoyakuModel> map =
            new HashMap<String, RsvYoyakuModel>();

        while (yoyakuList.size() != map.size()) {
            //1行分を作成する
            lineMdl = __getDailyLineMdl(yoyakuList, map, dspDateStr, frHour, toHour, hourDivCnt);
            dailyList.add(lineMdl);
         }

        return dailyList;
    }

    /**
     * <br>１行分の予約モデルを生成する
     * <br>[備考]出力済みの予約情報はHashMapに格納します
     * @param yoyakuList 予約情報
     * @param map 出力済みの予約情報
     * @param dspDateStr 表示日付
     * @param frHour 出力開始時刻
     * @param toHour 出力終了時刻
     * @param hourDivCnt 時間分割数
     * @return RsvDailyLineModel
     */
    private RsvDailyLineModel __getDailyLineMdl(
            ArrayList<RsvYoyakuModel> yoyakuList,
            HashMap<String, RsvYoyakuModel> map,
            String dspDateStr,
            int frHour,
            int toHour,
            int hourDivCnt) {

        log__.debug("-- __getDailyLineMdl START --");
        RsvDailyLineModel lineMdl = new RsvDailyLineModel();
        ArrayList<RsvDailyValueModel> lineList = new ArrayList<RsvDailyValueModel>();
        RsvDailyValueModel valueMdl = null;

        RsvYoyakuModel yrkMdl = null;
        int rsySid = 0;
        int endIndex = 1;
        int colsMax = (toHour - frHour + 1) * hourDivCnt;

        for (int i = 0; i < yoyakuList.size(); i++) {

            yrkMdl = yoyakuList.get(i);
            rsySid = yrkMdl.getRsySid();

            if (map.containsKey(String.valueOf(rsySid))) {
                //出力済みは除外
                continue;
            }

            //格納可能か判定し可能であれば格納する
            if (__isStorage(yrkMdl, dspDateStr, frHour, endIndex, hourDivCnt)) {

                //空白部分を設定
                valueMdl = __getBlankSchedule(yrkMdl, dspDateStr, frHour, endIndex, hourDivCnt);
                if (valueMdl != null) {
                    lineList.add(valueMdl);
                    endIndex = endIndex + valueMdl.getCols();
                }
                //予約部分を設定
                int cols = __getCols(yrkMdl, dspDateStr, frHour, toHour, endIndex, hourDivCnt);
                valueMdl = new RsvDailyValueModel();
                valueMdl.setCols(cols);
                valueMdl.setYoyakuMdl(yrkMdl);
                lineList.add(valueMdl);
                endIndex = endIndex + cols;
                map.put(String.valueOf(rsySid), yrkMdl);
            }

            //格納先インデックスがMAXの場合breakする
            if (endIndex == colsMax) {
                break;
            }
        }

        //表示終了時刻までの空白を設定
        valueMdl = __getEndBlankSchedule(yrkMdl, dspDateStr, frHour, toHour, endIndex, hourDivCnt);
        if (valueMdl != null) {
            lineList.add(valueMdl);
            endIndex = endIndex + valueMdl.getCols();
        }

        lineMdl.setLineList(lineList);
        log__.debug("-- __getDailyLineMdl END --");
        return lineMdl;
    }

    /**
     * <br>予約情報が格納可能か判定する
     * @param yrkMdl 予約情報
     * @param dspDateStr 表示日付
     * @param frHour 画面表示開始時刻
     * @param endIndex 出力済みポインタ
     * @param hourDivCnt 時間分割
     * @return true:格納可能 false:格納不可
     */
    private boolean __isStorage(
            RsvYoyakuModel yrkMdl,
            String dspDateStr,
            int frHour,
            int endIndex,
            int hourDivCnt) {
        int index = 0;

        //表示開始インデックスを取得
        index = __getIndex(yrkMdl, dspDateStr, frHour, hourDivCnt);
        if (index >= endIndex) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>予約情報と出力済みポインタから空予約を必要に応じて生成する
     * @param yrkMdl 予約情報
     * @param dspDateStr 表示日付
     * @param frHour 表示開始時刻
     * @param endIndex 出力済みポインタ
     * @param hourDivCnt 時間間隔
     * @return Sch040DailyValueModel
     */
    private RsvDailyValueModel __getBlankSchedule(
            RsvYoyakuModel yrkMdl,
            String dspDateStr,
            int frHour,
            int endIndex,
            int hourDivCnt) {

        RsvDailyValueModel valueMdl = null;
        int index = __getIndex(yrkMdl, dspDateStr, frHour, hourDivCnt);
        int cols = index - endIndex;
        if (cols > 0) {
            //空予約を生成する
            valueMdl = new RsvDailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setYoyakuMdl(null);
        }

        return valueMdl;
    }

    /**
     * <br>予約の開始・終了からCOLSを取得する
     * @param yrkMdl 予約情報
     * @param dspDateStr 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param endIndex 出力済みポインタ
     * @param hourDivCnt 時間分割
     * @return 予約の横幅(cols)
     */
    private int __getCols(
            RsvYoyakuModel yrkMdl,
            String dspDateStr,
            int frHour,
            int toHour,
            int endIndex,
            int hourDivCnt) {

        int cols = 0;
        int frIndex = __getIndex(yrkMdl, dspDateStr, frHour, hourDivCnt);
        int toIndex = __getEndIndex(yrkMdl, dspDateStr, frHour, toHour, hourDivCnt);
        cols = toIndex - frIndex + 1;
        return cols;
    }

    /**
     * <br>表示終了時刻と出力済みポインタから空予約を必要に応じて生成する
     * @param yrkMdl 予約情報
     * @param dspDateStr 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param endIndex 出力済みポインタ
     * @param hourDivCnt 時間分割
     * @return Sch040DailyValueModel
     */
    private RsvDailyValueModel __getEndBlankSchedule(
            RsvYoyakuModel yrkMdl,
            String dspDateStr,
            int frHour,
            int toHour,
            int endIndex,
            int hourDivCnt) {

        RsvDailyValueModel valueMdl = null;

        int defCols = (toHour - frHour + 1) * hourDivCnt;
        int cols = defCols - endIndex + 1;
        if (cols > 0) {
            //空予約を生成する
            valueMdl = new RsvDailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setYoyakuMdl(null);
        }

        return valueMdl;
    }

    /**
     * <br>表示開始時刻と予約情報から予約開始時刻のインデックスを取得する
     * @param yrkMdl 予約情報
     * @param strDate 表示日付
     * @param dspHour 表示開始時刻
     * @param hourDivCnt 時間分割数
     * @return int 開始時刻のインデックス
     */
    private int __getIndex(RsvYoyakuModel yrkMdl, String strDate, int dspHour, int hourDivCnt) {
        int index = 1;
        UDate newDspDate = new UDate();
        newDspDate.setDate(strDate);
        newDspDate.setHour(dspHour);
        newDspDate.setMinute(GSConstReserve.DAY_START_MINUTES);
        newDspDate.setSecond(GSConstReserve.DAY_START_SECOND);

        UDate frDate = yrkMdl.getRsyFrDate();
        if (newDspDate.compareDateYMDHM(frDate) != UDate.LARGE) {
            index = 1;
        } else {
            int hour = frDate.getIntHour();
            int min = frDate.getIntMinute();
            int ans1 = index + ((hour - dspHour) * hourDivCnt);
            int hourMemoriCnt = 60 / hourDivCnt;
            int ans2 = min / hourMemoriCnt;
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>表示開始時刻と予約情報から予約終了時刻のインデックスを取得する
     * @param yrkMdl 予約情報
     * @param strDate 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param hourDivCnt 時間分割
     * @return int 開始時刻のインデックス
     */
    private int __getEndIndex(
            RsvYoyakuModel yrkMdl,
            String strDate,
            int frHour, int toHour, int hourDivCnt) {
        int index = 1;
        UDate newDspDate = new UDate();
        newDspDate.setDate(strDate);
        newDspDate.setHour(toHour);
        newDspDate.setMinute(GSConstReserve.DAY_END_MINUTES);
        newDspDate.setSecond(GSConstReserve.DAY_END_SECOND);

        UDate toDate = yrkMdl.getRsyToDate();
        if (newDspDate.compareDateYMDHM(toDate) == UDate.LARGE) {
            index = (toHour - frHour + 1) * hourDivCnt;
        } else {
            int hour = toDate.getIntHour();
            int min = toDate.getIntMinute();
            int ans1 = (hour - frHour) * hourDivCnt;
            int hourMemoriCnt = 60 / hourDivCnt;
            int ans2 = min / hourMemoriCnt;
            index = ans1 + ans2;
        }

        return index;
    }
}