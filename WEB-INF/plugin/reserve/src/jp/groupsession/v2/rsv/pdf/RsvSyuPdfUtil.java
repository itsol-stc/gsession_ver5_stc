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
import jp.groupsession.v2.rsv.RsvSisetuModel;
import jp.groupsession.v2.rsv.RsvWeekModel;
import jp.groupsession.v2.rsv.RsvYoyakuDayModel;
import jp.groupsession.v2.rsv.RsvYoyakuModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 週間施設予約Pdf出力に関するUtilクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvSyuPdfUtil {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RsvSyuPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RsvSyuPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public RsvSyuPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 週間施設予約Pdf出力に関するUtilクラスです。
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
            RsvSyuPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        log__.debug("施設予約PDFの生成開始");

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
        //フォント 氏名,日付
        Font font_date = PdfUtil.getFont10b(fontPath);
        //フォント 土曜日用 10pt太
        Font font_sat = PdfUtil.getFont10b(fontPath);
        font_sat.setColor(PdfUtil.FONT_COLOR_BLUE);
        //フォント 日曜日用 10pt太
        Font font_sun = PdfUtil.getFont10b(fontPath);
        font_sun.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント 祝日用 10pt
        Font font_syuku = PdfUtil.getFont10(fontPath);
        font_syuku.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント(六曜)
        Font font_rokuyou = PdfUtil.getFont8b(fontPath);

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
            PdfUtil pdfUtil = new PdfUtil();
            doc = new Document(PageSize.A4.rotate()); //(595F,842F)
            //アウトプットストリームをPDFWriterに設定します。
            byteout = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, byteout);

            String smail = gsMsg.getMessage("sml.167");
            //出力するPDFに説明を付与します。
            doc.addAuthor("GroupSession");
            doc.addSubject(smail);

            doc.open();

            //文字入力範囲（横）
            float totalWidth = 920;
            //施設情報の高さ設定
            float sch_height = 40f;

            //タイトル
            float [] width_title  = {0.2f, 0.8f};
            PdfPTable table_title =
                    PdfUtil.createTable(2, 100, totalWidth, width_title, Element.ALIGN_CENTER);

            //スケジュール
            PdfPTable table_main =
                    PdfUtil.createTable(8, 100, totalWidth, Element.ALIGN_CENTER);

            //施設予約表示モード
            String topTitle = NullDefault.getString(
                    gsMsg.getMessage("cmn.reserve")
                    + "["
                    + gsMsg.getMessage("cmn.weekly")
                    + "]", EMP__);
            PdfPCell cell_title = PdfUtil.setCellRet(
                    topTitle, 0, Element.ALIGN_JUSTIFIED_ALL, font_title);
            settingWidth(cell_title, 0, 0, 0, 1);
            cell_title.setLeading(1.2f, 1.2f); //行間の設定
            cell_title.setPaddingBottom(5f);
            table_title.addCell(cell_title);

            PdfUtil.setCell(table_title, EMP__, 1, Element.ALIGN_LEFT,
                            PdfUtil.getFontEmpty(fontPath));

            //空白
            PdfUtil.setCell(table_title, EMP__, 2, Element.ALIGN_LEFT,
                            PdfUtil.getFontEmpty(fontPath));

            PdfPCell cell_main;

            //スケジュール
            //ヘッダー 年月
            cell_main = new PdfPCell(new Phrase(pdfMdl.getHeadDate(), font_header));
            settingWidth(cell_main, 1, 1, 0, 0);
            cell_main.setColspan(1);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_main.addCell(cell_main);

            //表示グループ
            cell_main = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.show.group")
                    + "：" + NullDefault.getString(
                            PdfUtil.replaseBackslashToYen(pdfMdl.getDispGroup()),
                            EMP__), font_header));
            settingWidth(cell_main, 1, 0, 1, 0);
            cell_main.setColspan(7);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_main.addCell(cell_main);

            //施設名
            cell_main = new PdfPCell(new Phrase(
                    gsMsg.getMessage("cmn.facility.name"), font_header));
            settingWidth(cell_main, 0.5f, 1, 0.25f, 1);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_date);
            cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_main.addCell(cell_main);
            //日にち＆曜日
            float [] width_headDate  = {0.7f, 0.3f};
            for (int i = 0; i < pdfMdl.getCalendarList().size(); i++) {
                Font font = null;
                if ((Integer.valueOf(pdfMdl.getCalendarList().get(i).getHolidayKbn()) == 1)
                || (Integer.valueOf(pdfMdl.getCalendarList().get(i).getWeekKbn()) == 1)) {
                    font = font_sun;
                } else if (Integer.valueOf(pdfMdl.getCalendarList().get(i).getWeekKbn()) == 7) {
                    font = font_sat;
                } else {
                    font = font_date;
                }
                if (StringUtil.isNullZeroString(pdfMdl.getCalendarList().get(i).getRokuyou())) {
                    //日付のみ表示
                    cell_main = new PdfPCell(new Phrase(
                            pdfMdl.getCalendarList().get(i).getDspDayString(), font));
                } else {
                    cell_main = new PdfPCell();
                    //日付 + 六曜を表示
                    PdfPTable table_headDate = new PdfPTable(2);
                    table_headDate.setWidthPercentage(100f);
                    table_headDate.setTotalWidth(totalWidth);
                    table_headDate.setWidths(width_headDate);
                    PdfPCell dateCell = new PdfPCell(
                            new Phrase(pdfMdl.getCalendarList().get(i).getDspDayString(), font));
                    dateCell.setBorder(0);
                    dateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    PdfPCell rokuyouCell = new PdfPCell(
                            new Phrase(pdfMdl.getCalendarList().get(i).getRokuyou(), font_rokuyou));
                    rokuyouCell.setBorder(0);
                    rokuyouCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
                    table_headDate.addCell(dateCell);
                    table_headDate.addCell(rokuyouCell);
                    cell_main.addElement(table_headDate);
                }

                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (i != pdfMdl.getCalendarList().size() - 1) {
                    settingWidth(cell_main, 0.5f, 0.25f, 0.25f, 1);
                } else  {
                    settingWidth(cell_main, 0.5f, 0.25f, 1, 1);
                }
                cell_main.setBackgroundColor(color_date);
                cell_main.setPaddingBottom(4f);
                table_main.addCell(cell_main);
            }

            table_main.setHeaderRows(2);

            PdfPTable dayTable = null;
            if (pdfMdl.getSisetuList() != null) {
                for (RsvSisetuModel sisetuMdl : pdfMdl.getSisetuList()) {
                    dayTable = PdfUtil.createTable(1, 100, 115, Element.ALIGN_CENTER);
                    //施設情報
                    PdfUtil.setCell(dayTable,
                            PdfUtil.replaseBackslashToYen(sisetuMdl.getRsdName()),
                            1, Element.ALIGN_LEFT,
                                    font_header);
                    String sisetuInfo = __createSisetuInfo(pdfMdl, sisetuMdl);
                    sisetuInfo = PdfUtil.replaseBackslashToYen(sisetuInfo);
                    PdfUtil.setCell(dayTable, sisetuInfo, 1, Element.ALIGN_LEFT, font_main_black);

                    cell_main = new PdfPCell(dayTable);
                    cell_main.setMinimumHeight(sch_height);
                    settingWidth(cell_main, 0, 1, 0.25f, 1);
                    cell_main.setPaddingBottom(2f);
                    table_main.addCell(cell_main);

                    int sisetuList_count = 0;
                    for (RsvWeekModel weekMdl : sisetuMdl.getRsvWeekList()) {
                        int weekList_count = 0;
                        for (RsvYoyakuDayModel dayList : weekMdl.getYoyakuDayList()) {
                            dayTable = PdfUtil.createTable(1, 100, 115, Element.ALIGN_LEFT);

                            //祝日表示
                            if (sisetuList_count == 0) {
                                PdfUtil.setCell(dayTable,
                                        NullDefault.getString(dayList.getHolName(), EMP__),
                                        1, Element.ALIGN_RIGHT, font_syuku);
                            }

                            for (RsvYoyakuModel yoyakuList : dayList.getYoyakuList()) {
                                StringBuilder disp = new StringBuilder();
                                disp.append(yoyakuList.getYrkRiyoDateStr() + "\n");
                                Font font_sisetuInfo;
                                if (yoyakuList.getRsyApprStatus()
                                        == GSConstReserve.RSY_APPR_STATUS_NOAPPR) {
                                    disp.append("(" + gsMsg.getMessage("reserve.appr.st1") + ")");
                                    //閲覧できない施設予約は黒文字
                                    if (yoyakuList.getPublic() == GSConstReserve.PUBLIC_KBN_PLANS) {
                                        font_sisetuInfo = font_main_black;
                                    } else {
                                        font_sisetuInfo = font_main_orange;
                                    }
                                } else if  (yoyakuList.getRsyApprKbn()
                                        == GSConstReserve.RSY_APPR_KBN_REJECTION) {
                                    disp.append("(" + gsMsg.getMessage("reserve.appr.st2") + ")");
                                    font_sisetuInfo = font_main_black;
                                //閲覧できない施設予約は黒文字
                                } else if (yoyakuList.getPublic()
                                        == GSConstReserve.PUBLIC_KBN_PLANS) {
                                    font_sisetuInfo = font_main_black;
                                } else {
                                    font_sisetuInfo = font_main_blue;
                                }
                                disp.append(NullDefault.getString(yoyakuList.getRsyMok(), ""));
                                if (!StringUtil.isNullZeroString(yoyakuList.getRsyMok())
                                        && !StringUtil.isNullZeroString(yoyakuList.getYrkName())) {
                                    disp.append(" / ");
                                }
                                disp.append(NullDefault.getString(yoyakuList.getYrkName(), ""));
                                PdfUtil.setCell(dayTable,
                                        PdfUtil.replaseBackslashToYen(disp.toString()),
                                        1, Element.ALIGN_LEFT, font_sisetuInfo);
                            }
                                cell_main = new PdfPCell(dayTable);
                                if (dayList.getYrkYobi() == 1) {
                                    cell_main.setBackgroundColor(color_sunday);
                                } else if (dayList.getYrkYobi() == 7) {
                                    cell_main.setBackgroundColor(color_saturday);
                                }
                                //週の最後の場合だけ右枠線を太くする
                                if (weekList_count == 6) {
                                    settingWidth(cell_main, 0, 0.25f, 1, 1);
                                } else {
                                    settingWidth(cell_main, 0, 0.25f, 0.25f, 1);
                                }
                            cell_main.setPaddingBottom(2f);
                            table_main.addCell(cell_main);
                            weekList_count++;
                        }
                        sisetuList_count++;
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
            PdfContentByte under_page;
            PdfContentByte under_date;
            stamper = new PdfStamper(reader, oStream);

            UDate date = new UDate();
            for (int i = 1; i <= total; i++) {
                //アンダーコンテンツを取得する
                under_page = stamper.getUnderContent(i);
                //ページ番号を追加する、フォント設定、位置設定
                under_page.beginText();
                under_page.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                under_page.moveText(750, 570);
                under_page.showText(
                        i + "/" + total + " " + gsMsg.getMessage("cmn.pdf.page"));
                under_page.endText();

                //アンダーコンテンツを取得する //(595F,842F)
                under_date = stamper.getUnderContent(i);
                under_date.beginText();
                under_date.setFontAndSize(PdfUtil.getBaseFont(fontPath), 12);
                under_date.moveText(750, 10);
                under_date.showText(
                        date.getStrYear() + "/" + date.getStrMonth() + "/" + date.getStrDay());
                under_date.endText();
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
     * @param sisetuList 施設毎の予約情報リスト
     * @return sisetuInfo 施設予約情報
     */
    private String __createSisetuInfo(RsvSyuPdfModel pdfMdl, RsvSisetuModel sisetuList) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //施設名
        String sisetuInfo = "";
        //施設ID
        if (sisetuList.getRsdInfoSisetuIdDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo +=  gsMsg.getMessage("reserve.55")
                    + ":" + sisetuList.getRsdSisetuId();
        }
        //資産管理番号
        if (sisetuList.getRsdInfoSisanKanriDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo +=  gsMsg.getMessage("cmn.asset.register.num")
                    + ":" + sisetuList.getRsdSisanKanri();
        }
        //座席数、個数、乗員数、冊数など
        if (sisetuList.getRsdInfoProp1ValueDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo +=  sisetuList.getRsvPropHeaderName8()
                    + ":" + sisetuList.getRsdProp1Value();
        }
        //喫煙
        if (sisetuList.getRsdInfoProp2ValueDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo +=  pdfMdl.getPropHeaderName2()
                    + ":";
            if (Integer.valueOf(sisetuList.getRsdProp2Value()) == GSConstReserve.PROP_KBN_KA) {
                sisetuInfo += gsMsg.getMessage("cmn.accepted");
            } else if (Integer.valueOf(sisetuList.getRsdProp2Value())
                    == GSConstReserve.PROP_KBN_HUKA) {
                sisetuInfo += gsMsg.getMessage("cmn.not");
            }
        }
        //社外持出し
        if (sisetuList.getRsdInfoProp3ValueDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo += pdfMdl.getPropHeaderName3()
                    + ":";
            if (Integer.valueOf(sisetuList.getRsdProp3Value()) == GSConstReserve.PROP_KBN_KA) {
                sisetuInfo += gsMsg.getMessage("cmn.accepted");
            } else if (Integer.valueOf(sisetuList.getRsdProp3Value())
                    == GSConstReserve.PROP_KBN_HUKA) {
                sisetuInfo += gsMsg.getMessage("cmn.not");
            }
        }
        //車のナンバー
        if (sisetuList.getRsdInfoProp4ValueDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo += pdfMdl.getPropHeaderName4()
                    + ":" + sisetuList.getRsdProp4Value();
        }
        //書籍のSIBN
        if (sisetuList.getRsdInfoProp5ValueDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo += pdfMdl.getPropHeaderName5()
                    + ":" + sisetuList.getRsdProp5Value();
        }
        //予約可能期限
        if (sisetuList.getRsdInfoProp6ValueDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo += pdfMdl.getPropHeaderName6()
                    + ":" + sisetuList.getRsdProp6Value()
                    + gsMsg.getMessage("cmn.days.after");
        }
        //重複登録
        if (sisetuList.getRsdInfoProp7ValueDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo += pdfMdl.getPropHeaderName7()
                    + ":";
            if (Integer.valueOf(sisetuList.getRsdProp7Value()) == GSConstReserve.PROP_KBN_KA) {
                sisetuInfo += gsMsg.getMessage("cmn.accepted");
            } else if (Integer.valueOf(sisetuList.getRsdProp7Value())
                    == GSConstReserve.PROP_KBN_HUKA) {
                sisetuInfo += gsMsg.getMessage("cmn.not");
            }
        }
        //承認
        if (sisetuList.isRsdApprDspFlg()) {
            sisetuInfo += " \n";
            sisetuInfo += gsMsg.getMessage("reserve.appr.set.title")
                    + ":";
            if (sisetuList.isRsdApprKbnFlg()) {
                sisetuInfo += gsMsg.getMessage("reserve.appr.set.kbn1");
            } else {
                sisetuInfo += gsMsg.getMessage("reserve.appr.set.kbn2");
            }
        }
        //場所
        if (sisetuList.getRsdInfoPlaComDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo += gsMsg.getMessage("reserve.location.comments")
                    + ":" + sisetuList.getRsdInfoPlaCom();
        }
        //備考
        if (sisetuList.getRsdInfoBikoDspKbn() == 0) {
            sisetuInfo += " \n";
            sisetuInfo += gsMsg.getMessage("cmn.memo")
                    + ":" + sisetuList.getRsdBiko();
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
}
