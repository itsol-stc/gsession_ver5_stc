package jp.groupsession.v2.tcd.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.excel.TimecardLineData;
import jp.groupsession.v2.tcd.excel.WorkReportData;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;

/**
 * <br>[機  能] 勤務表(PDF)出力に関するユーティリティクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TcdPdfUtil {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TcdPdfUtil.class);
    /** 勤務表出力用フォントファイル */
    private static final String FONTFILE = "GenShinGothic-Monospace-Normal.ttf";

    /** グレーフィルター設定値 */
    private static final float GRAY_FILL__ = 0.93f;

    /** 外枠線 */
    private static final float BORDER1__ = 0.9f;
    /** 内枠細線 */
    private static final float BORDER2__ = 0.1f;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public TcdPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * 指定されたストリームに勤務表データを設定する
     * @param appRootPath タイムカードデータディレクトリパス
     * @param oStream 勤務表データの出力先となるストリーム
     * @param workReportData 勤務表データ
     * @throws Exception 例外
     */
    public synchronized void createWorkReport(
            String appRootPath,
            OutputStream oStream,
            WorkReportData workReportData,
            List<TcdHolidayInfModel> holDataList) throws Exception {

        log__.debug("PDFUtil createWorkReport start");

        //出力用のStreamをインスタンス化します。
        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
        //ページサイズを設定します。
        Document doc = new Document(PageSize.A4);

        //明細情報
        List<TimecardLineData> reportDataList = workReportData.getLineDataList();
        //集計項目
        BigDecimal sumDay = BigDecimal.ZERO;
        BigDecimal sumDaikyu = BigDecimal.ZERO.divide(BigDecimal.ONE, 1, BigDecimal.ROUND_HALF_UP);
        BigDecimal sumHurikyu = BigDecimal.ZERO.divide(BigDecimal.ONE, 1, BigDecimal.ROUND_HALF_UP);
        BigDecimal sumYuukyu = BigDecimal.ZERO.divide(BigDecimal.ONE, 1, BigDecimal.ROUND_HALF_UP);
        BigDecimal sumKekkin = BigDecimal.ZERO.divide(BigDecimal.ONE, 1, BigDecimal.ROUND_HALF_UP);

        PdfWriter pdfwriter = null;
        try {
            //アウトプットストリームをPDFWriterに設定します。
            pdfwriter = PdfWriter.getInstance(doc, byteout);
            /* フォント設定部 */
            String fontStr = __getFontFilePath(appRootPath);

            //(ゴシック16pt(太字)
            Font font_16b = new Font(
                    BaseFont.createFont(fontStr, BaseFont.IDENTITY_H,
                            BaseFont.NOT_EMBEDDED), 16, Font.BOLD);
            //(ゴシック14pt(太字イタリック)
            Font font_14bi = new Font(
                    BaseFont.createFont(fontStr, BaseFont.IDENTITY_H,
                            BaseFont.NOT_EMBEDDED), 14, Font.BOLDITALIC);
            //ゴシック10pt
            Font font_10 = new Font(
                    BaseFont.createFont(fontStr, BaseFont.IDENTITY_H,
                            BaseFont.NOT_EMBEDDED), 10);

            //ゴシック6pt
            Font font_6 = new Font(
                    BaseFont.createFont(fontStr, BaseFont.IDENTITY_H,
                            BaseFont.NOT_EMBEDDED), 6);

            //ゴシックbase
            Font font_base = new Font(
                    BaseFont.createFont(fontStr, BaseFont.IDENTITY_H,
                            BaseFont.NOT_EMBEDDED), 11);

            //ゴシックbase
            Font font_title = new Font(
                    BaseFont.createFont(fontStr, BaseFont.IDENTITY_H,
                            BaseFont.NOT_EMBEDDED), 8);

            //空白セル用フォント(非表示)
            Font fontEmpty = new Font(
                    BaseFont.createFont(fontStr, BaseFont.IDENTITY_H,
                            BaseFont.NOT_EMBEDDED), 6);
            fontEmpty.setColor(Color.WHITE);

            GsMessage gsMsg = new GsMessage(reqMdl__);
            String timecard = gsMsg.getMessage("tcd.127");

            //出力するPDFに説明を付与します。
            doc.addAuthor("GroupSession");
            doc.addSubject(timecard);

            //文章の出力を開始します。
            doc.open();
            //ヘッダー部分
            PdfPTable headerTable = new PdfPTable(16);
            headerTable.setWidthPercentage(100);
            float totalWidth = 920;
            headerTable.setTotalWidth(totalWidth);
            float[] tableWidth = {0.03f, 0.03f, 0.05f, 0.08f, 0.08f, 0.08f, 0.06f,
                    0.06f, 0.06f, 0.06f, 0.05f, 0.05f, 0.05f, 0.05f, 0.10f, 0.10f};
            headerTable.setWidths(tableWidth);
            //テーブルのデフォルトの表示位置（横）を設定します。
            headerTable.setHorizontalAlignment(Element.ALIGN_CENTER);

            //承認印table
            PdfPTable ineiTable = new PdfPTable(2);
            float[] ineiWidth = {0.75f, 0.25f};
            ineiTable.setWidths(ineiWidth);

            PdfPTable ineiTable_l = new PdfPTable(2);
            float[] ineiWidth_l = {0.27f, 0.47f};
            ineiTable_l.setWidths(ineiWidth_l);
            PdfPTable ineiTable_r = new PdfPTable(3);
            float[] ineiWidth_r = {0.27f, 0.27f, 0.27f};
            ineiTable_r.setWidths(ineiWidth_r);


            String msgYear = gsMsg.getMessage(
                    "cmn.year",
                    new String[] {String.valueOf(workReportData.getYear())});
            String msgMonth = gsMsg.getMessage("cmn.month");
            String kinmuhyo = gsMsg.getMessage("tcd.128");

            //ヘッダー１行目
            //年月日
            PdfPCell cellH11 = new PdfPCell(
                    new Phrase(msgYear
                            + workReportData.getMonth() + msgMonth, font_14bi));
            cellH11.setBorder(Rectangle.NO_BORDER);
            cellH11.setHorizontalAlignment(Element.ALIGN_LEFT);
            ineiTable_l.addCell(cellH11);
            //勤務表
            PdfPCell cellH12 = new PdfPCell(new Phrase(kinmuhyo, font_16b));
            cellH12.setBorder(Rectangle.NO_BORDER);
            cellH12.setHorizontalAlignment(Element.ALIGN_CENTER);
            ineiTable_l.addCell(cellH12);

            //承認印欄１
            PdfPCell cellH13 = new PdfPCell(new Phrase("empty", fontEmpty));
            cellH13.setBorder(Rectangle.BOX);
            cellH13.setBorderWidth(BORDER1__);
            ineiTable_r.addCell(cellH13);
           //承認印欄２
            PdfPCell cellH14 = new PdfPCell(new Phrase("empty", fontEmpty));
            cellH14.setBorder(Rectangle.BOX);
            cellH14.setBorderWidth(BORDER1__);
            ineiTable_r.addCell(cellH14);
            //承認印欄３
            PdfPCell cellH15 = new PdfPCell(new Phrase("empty", fontEmpty));
            cellH15.setBorder(Rectangle.BOX);
            cellH15.setBorderWidth(BORDER1__);
            ineiTable_r.addCell(cellH15);

            String msg = gsMsg.getMessage("tcd.129");

            //ヘッダー２行目
            //◎翌月７日までに提出してください
            PdfPCell cellH21 = new PdfPCell(new Phrase(msg, font_10));
            cellH21.setBorder(Rectangle.NO_BORDER);
            cellH21.setColspan(2);
            cellH21.setHorizontalAlignment(Element.ALIGN_LEFT);
            ineiTable_l.addCell(cellH21);
            PdfPCell ineiCell_l = new PdfPCell(ineiTable_l);
            ineiCell_l.setBorder(Rectangle.NO_BORDER);
            ineiTable.addCell(ineiCell_l);
            PdfPCell ineiCell_r = new PdfPCell(ineiTable_r);
            ineiCell_r.setBorder(Rectangle.NO_BORDER);
            ineiTable.addCell(ineiCell_r);
            PdfPCell ineiCell = new PdfPCell(ineiTable);
            ineiCell.setBorder(Rectangle.NO_BORDER);
            ineiCell.setColspan(16);
            headerTable.addCell(ineiCell);



            //ヘッダー３行目（空行）
            PdfPCell cellH31 = new PdfPCell(new Phrase("empty", fontEmpty));
            cellH31.setBorder(Rectangle.NO_BORDER);
            cellH31.setColspan(16);
            headerTable.addCell(cellH31);

            //ヘッダー４行目
            //氏名TABLE
            PdfPTable simeiTable = new PdfPTable(3);
            float[] simeiWidth = {0.1f, 0.8f, 0.1f};
            simeiTable.setWidths(simeiWidth);

            String msgName = gsMsg.getMessage("cmn.name");

            //氏名タイトル
            PdfPCell cellH41 = new PdfPCell(new Phrase(msgName, font_10));
            cellH41.setBorder(Rectangle.NO_BORDER);
            cellH41.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellH41.setVerticalAlignment(Element.ALIGN_TOP);
            cellH41.setNoWrap(true);
            simeiTable.addCell(cellH41);
            //氏名
            PdfPCell cellH42 = new PdfPCell(new Phrase(
                    PdfUtil.replaseBackslashToYen(workReportData.getUserName()),
                    font_16b));
            cellH42.setBorder(Rectangle.NO_BORDER);
            cellH42.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellH42.setPaddingBottom(5f);
            simeiTable.addCell(cellH42);

            String msgStamp = gsMsg.getMessage("tcd.130");

            //印
            PdfPCell cellH52 = new PdfPCell(new Phrase(msgStamp, font_10));
            cellH52.setBorder(Rectangle.NO_BORDER);
            cellH52.setVerticalAlignment(Element.ALIGN_BOTTOM);
            simeiTable.addCell(cellH52);
            PdfPCell simeiCell = new PdfPCell(simeiTable);
            simeiCell.setBorder(Rectangle.BOX);
            simeiCell.setColspan(10);
            simeiCell.setBorderWidth(BORDER1__);
            headerTable.addCell(simeiCell);
            //作業場所TABLE
            PdfPTable sagyouTable = new PdfPTable(2);
            float[] sagyouWidth = {0.2f, 0.8f};
            sagyouTable.setWidths(sagyouWidth);

            String msgPlace = gsMsg.getMessage("tcd.131");

            //作業場所タイトル
            PdfPCell cellH44 = new PdfPCell(new Phrase(msgPlace, font_10));
            cellH44.setBorder(Rectangle.NO_BORDER);
            cellH44.setHorizontalAlignment(Element.ALIGN_LEFT);
            cellH44.setVerticalAlignment(Element.ALIGN_TOP);
            cellH44.setNoWrap(true);
            sagyouTable.addCell(cellH44);
            //作業場所
            PdfPCell cellH45 = new PdfPCell(new Phrase(
                    PdfUtil.replaseBackslashToYen(workReportData.getPlace()),
                    font_16b));
            cellH45.setBorder(Rectangle.NO_BORDER);
            cellH45.setHorizontalAlignment(Element.ALIGN_CENTER);
            sagyouTable.addCell(cellH45);
            PdfPCell sagyouCell = new PdfPCell(sagyouTable);
            sagyouCell.setBorder(Rectangle.BOX);
            sagyouCell.setColspan(6);
            sagyouCell.setBorderWidth(BORDER1__);
            headerTable.addCell(sagyouCell);

            String msgDay = gsMsg.getMessage("cmn.day");
            String msgDayOfWeek = gsMsg.getMessage("tcd.132");
            String dakoku = gsMsg.getMessage("tcd.133");
            String syussya = gsMsg.getMessage("tcd.134");
            String taisya = gsMsg.getMessage("tcd.135");
            String kadou = gsMsg.getMessage("tcd.tcd010.16");
            String zangyo = gsMsg.getMessage("tcd.tcd010.09");

            String shinya = gsMsg.getMessage("tcd.tcd010.06");
            String chikoku = gsMsg.getMessage("tcd.18");
            String soutai = gsMsg.getMessage("tcd.22");
            String daikyu = gsMsg.getMessage("tcd.19");
            String hurikyu = gsMsg.getMessage("tcd.23");
            String yukyu = gsMsg.getMessage("tcd.03");
            String kekkin = gsMsg.getMessage("tcd.34");
            for (TcdHolidayInfModel holMdl : holDataList) {
                switch (holMdl.getThiSid()) {
                    case GSConstTimecard.THI_SID_DAIKYU:
                        daikyu = holMdl.getThiName();
                        break;
                    case GSConstTimecard.THI_SID_HURIKYU:
                        hurikyu = holMdl.getThiName();
                        break;
                    case GSConstTimecard.THI_SID_YUUKYU:
                        yukyu = holMdl.getThiName();
                        break;
                    case GSConstTimecard.THI_SID_KEKKIN:
                        kekkin = holMdl.getThiName();
                        break;
                    default:

                }
            }
            String biko = gsMsg.getMessage("cmn.memo");

            //タイトル
            PdfPTable titleTable = new PdfPTable(16);
            titleTable.setWidthPercentage(100);
            titleTable.setWidths(tableWidth);
            PdfPCell cellMt = new PdfPCell(new Phrase(msgDay, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);
            cellMt = new PdfPCell(new Phrase(msgDayOfWeek, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);
            cellMt = new PdfPCell(new Phrase(dakoku, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);
            cellMt = new PdfPCell(new Phrase(syussya, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);
            cellMt = new PdfPCell(new Phrase(taisya, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);
            cellMt = new PdfPCell(new Phrase(kadou, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);
            cellMt = new PdfPCell(new Phrase(zangyo, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);
            cellMt = new PdfPCell(new Phrase(shinya, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);
            cellMt = new PdfPCell(new Phrase(chikoku, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);

            cellMt = new PdfPCell(new Phrase(soutai, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);

            cellMt = new PdfPCell(new Phrase(daikyu, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);

            cellMt = new PdfPCell(new Phrase(hurikyu, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);

            cellMt = new PdfPCell(new Phrase(yukyu, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);

            cellMt = new PdfPCell(new Phrase(kekkin, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            titleTable.addCell(cellMt);

            cellMt = new PdfPCell(new Phrase(biko, font_title));
            cellMt.setBorder(Rectangle.BOX);
            cellMt.setBorderWidth(BORDER2__);
            cellMt.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellMt.setColspan(2);
            titleTable.addCell(cellMt);

            //明細テーブル
            PdfPTable meisaiTable = new PdfPTable(16);
            meisaiTable.setWidthPercentage(100);
            meisaiTable.setTotalWidth(totalWidth);
            meisaiTable.setWidths(tableWidth);
            meisaiTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            //明細
            PdfPCell cell = null;
            for (TimecardLineData reportData : reportDataList) {
                PdfPCell emptycell = new PdfPCell(new Phrase(" ", fontEmpty));
                emptycell.setBorderWidth(BORDER2__);
                PdfPCell emptycell2 = new PdfPCell(new Phrase(" ", fontEmpty));
                emptycell2.setBorderWidth(BORDER2__);
                emptycell2.setColspan(2);

                //休日フラグ 0:平日,1:休日
                boolean holFlg = false;
                if (reportData.getHoliday() == 1) {
                    holFlg = true;
                    emptycell.setGrayFill(GRAY_FILL__);
                    emptycell2.setGrayFill(GRAY_FILL__);
                }

                //日付
                if (reportData.getDate() != null) {
                    //勤務表
                    String value = String.valueOf(reportData.getDate().getIntDay());
                    cell = new PdfPCell(new Phrase(value, font_base));
                    cell.setBorder(Rectangle.BOX);
                    cell.setBorderWidth(BORDER2__);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    if (holFlg) {
                        cell.setGrayFill(GRAY_FILL__);
                    }
                    meisaiTable.addCell(cell);
                } else {
                    meisaiTable.addCell(emptycell);
                }

                //曜日
                if (reportData.getDate() != null) {
                    int wk = reportData.getDate().getWeek();
                    String jwk = UDateUtil.getStrWeek(wk, reqMdl__);
                    cell = new PdfPCell(new Phrase(jwk, font_base));
                    cell.setBorderWidth(BORDER2__);
                    cell.setBorder(Rectangle.BOX);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    if (holFlg) {
                        cell.setGrayFill(GRAY_FILL__);
                    }
                    meisaiTable.addCell(cell);
                } else {
                    meisaiTable.addCell(emptycell);
                }

                //打刻開始時間
                PdfPTable dakokuTable = new PdfPTable(1);
                if (reportData.getStrikeStartTime() != null) {
                    //打刻開始時間
                    String start = reportData.getStrikeStartTime();
                    if (StringUtil.isNullZeroString(start)) {
                        start = " ";
                    }
                    cell = new PdfPCell(new Phrase(start, font_6));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    dakokuTable.addCell(cell);
                } else {
                    emptycell.setBorder(Rectangle.NO_BORDER);
                    dakokuTable.addCell(emptycell);
                }
                if (reportData.getStrikeEndTime() != null) {
                    //打刻終了時間
                    String end = reportData.getStrikeEndTime();
                    if (StringUtil.isNullZeroString(end)) {
                        end = " ";
                    }
                    cell = new PdfPCell(new Phrase(end, font_6));
                    cell.setBorder(Rectangle.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    dakokuTable.addCell(cell);
                } else {
                    emptycell.setBorder(Rectangle.NO_BORDER);
                    dakokuTable.addCell(emptycell);
                }
                PdfPCell dakokuCell = new PdfPCell(dakokuTable);
                dakokuCell.setBorder(Rectangle.BOX);
                dakokuCell.setBorderWidth(BORDER2__);
                dakokuCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                if (holFlg) {
                    dakokuCell.setGrayFill(GRAY_FILL__);
                }
                meisaiTable.addCell(dakokuCell);
                emptycell.setBorder(Rectangle.BOX);

                //出社時刻
                if (reportData.getStartTime() != null) {
                    cell = new PdfPCell(new Phrase(reportData.getStartTime(), font_base));
                    cell.setBorder(Rectangle.BOX);
                    cell.setBorderWidth(BORDER2__);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    if (holFlg) {
                        cell.setGrayFill(GRAY_FILL__);
                    }
                    meisaiTable.addCell(cell);
                } else {
                    meisaiTable.addCell(emptycell);
                }
                //終了時間
                if (reportData.getEndTime() != null) {
                    cell = new PdfPCell(new Phrase(reportData.getEndTime(), font_base));
                    cell.setBorder(Rectangle.BOX);
                    cell.setBorderWidth(BORDER2__);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    if (holFlg) {
                        cell.setGrayFill(GRAY_FILL__);
                    }
                    meisaiTable.addCell(cell);
                } else {
                    meisaiTable.addCell(emptycell);
                }
                //稼動時間
                String utilTime = reportData.getUtilTime();
                if (utilTime != null && utilTime.length() > 0) {
                    double utilTimeVal = Double.parseDouble(utilTime);
                    if (utilTimeVal > 0) {
                        sumDay = sumDay.add(BigDecimal.ONE);
                        cell = new PdfPCell(new Phrase(utilTime, font_base));
                        cell.setBorder(Rectangle.BOX);
                        cell.setBorderWidth(BORDER2__);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        if (holFlg) {
                            cell.setGrayFill(GRAY_FILL__);
                        }
                        meisaiTable.addCell(cell);
                    } else {
                        meisaiTable.addCell(emptycell);
                    }
                } else {
                    meisaiTable.addCell(emptycell);
                }
                //残業
                String overTime = reportData.getOverTime();
                if (overTime != null && overTime.length() > 0) {
                    double overTimeVal = Double.parseDouble(overTime);
                    if (overTimeVal > 0) {
                        cell = new PdfPCell(new Phrase(overTime, font_base));
                        cell.setBorderWidth(BORDER2__);
                        cell.setBorder(Rectangle.BOX);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        if (holFlg) {
                            cell.setGrayFill(GRAY_FILL__);
                        }
                        meisaiTable.addCell(cell);
                    } else {
                        meisaiTable.addCell(emptycell);
                    }
                } else {
                    meisaiTable.addCell(emptycell);
                }
                //深夜
                String nightOverTime = reportData.getNightOverTime();
                if (nightOverTime != null && nightOverTime.length() > 0) {
                    double nightOverTimeVal = Double.parseDouble(nightOverTime);
                    if (nightOverTimeVal > 0) {
                        cell = new PdfPCell(new Phrase(nightOverTime, font_base));
                        cell.setBorder(Rectangle.BOX);
                        cell.setBorderWidth(BORDER2__);
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        if (holFlg) {
                            cell.setGrayFill(GRAY_FILL__);
                        }
                        meisaiTable.addCell(cell);
                    } else {
                        meisaiTable.addCell(emptycell);
                    }
                } else {
                    meisaiTable.addCell(emptycell);
                }

                //遅刻
                __emptyFormatForCell(
                        meisaiTable, reportData.getTikoku(), font_base, emptycell, holFlg);
                //早退
                __emptyFormatForCell(
                        meisaiTable, reportData.getSoutai(), font_base, emptycell, holFlg);

                TimecardBiz tcdBiz = new TimecardBiz();
                //代休
                String daikyuDay = tcdBiz.dispSyukei(NullDefault.getString(
                        reportData.getHolDaysMap().get(GSConstTimecard.THI_SID_DAIKYU), ""));
                __emptyFormatForCell(meisaiTable, daikyuDay, font_title, emptycell, holFlg);

                if (!StringUtil.isNullZeroString(daikyuDay)) {
                    sumDaikyu = sumDaikyu.add(new BigDecimal(daikyuDay));
                }
                //振休
                String hurikyuDay = tcdBiz.dispSyukei(NullDefault.getString(
                        reportData.getHolDaysMap().get(GSConstTimecard.THI_SID_HURIKYU), ""));
                __emptyFormatForCell(meisaiTable, hurikyuDay, font_title, emptycell, holFlg);
                if (!StringUtil.isNullZeroString(hurikyuDay)) {
                    sumHurikyu = sumHurikyu.add(new BigDecimal(hurikyuDay));
                }
                //有休・欠勤
                if (StringUtil.isNullZeroString(daikyuDay)
                        && StringUtil.isNullZeroString(hurikyuDay)) {
                    if (reportData.getHolKbn() == GSConstTimecard.HOL_KBN_YUUKYUU) {
                        for (String val : reportData.getHolDaysMap().values()) {
                            String yukyuuDay = tcdBiz.dispSyukei(NullDefault.getString(val, ""));
                            __emptyFormatForCell(
                                    meisaiTable, yukyuuDay, font_title, emptycell, holFlg);
                            if (!StringUtil.isNullZeroString(yukyuuDay)) {
                                sumYuukyu = sumYuukyu.add(new BigDecimal(yukyuuDay));
                            }
                            break;
                        }
                        __emptyFormatForCell(
                                meisaiTable, "", font_title, emptycell, holFlg);
                    } else if (reportData.getHolKbn() == GSConstTimecard.HOL_KBN_KEKKIN) {
                        __emptyFormatForCell(
                                meisaiTable, "", font_title, emptycell, holFlg);
                        for (String val : reportData.getHolDaysMap().values()) {
                            String kekkinDay = tcdBiz.dispSyukei(NullDefault.getString(val, ""));
                            __emptyFormatForCell(
                                    meisaiTable, kekkinDay, font_title, emptycell, holFlg);
                            if (!StringUtil.isNullZeroString(kekkinDay)) {
                                sumKekkin = sumKekkin.add(new BigDecimal(kekkinDay));
                            }
                            break;
                        }
                    } else {
                        __emptyFormatForCell(
                                meisaiTable, "", font_title, emptycell, holFlg);

                        __emptyFormatForCell(
                                meisaiTable, "", font_title, emptycell, holFlg);
                    }
                } else {
                    __emptyFormatForCell(
                            meisaiTable, "", font_title, emptycell, holFlg);

                    __emptyFormatForCell(
                            meisaiTable, "", font_title, emptycell, holFlg);
                }
                //備 考
                String bikou = reportData.getBikou();
                bikou = PdfUtil.replaseBackslashToYen(bikou);
                if (bikou != null) {
                    cell = new PdfPCell(new Phrase(bikou, font_6));
                    cell.setBorder(Rectangle.BOX);
                    cell.setBorderWidth(BORDER2__);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_TOP);
                    cell.setColspan(2);
                    if (holFlg) {
                        cell.setGrayFill(GRAY_FILL__);
                    }
                    meisaiTable.addCell(cell);
                } else {
                    meisaiTable.addCell(emptycell2);
                }
            }

            //計・フッター部分
            PdfPTable footerTable = new PdfPTable(16);
            footerTable.setWidthPercentage(100);
            footerTable.setTotalWidth(totalWidth);
            footerTable.setWidths(tableWidth);
            footerTable.setHorizontalAlignment(Element.ALIGN_CENTER);

            String kijyunDays = gsMsg.getMessage("tcd.137");

            //基準日数
            PdfPTable footerTable1 = new PdfPTable(2);
            PdfPCell cell1 = new PdfPCell(new Phrase(kijyunDays, font_10));
            cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell1.setBorder(Rectangle.NO_BORDER);
            footerTable1.addCell(cell1);
            PdfPCell cell2 = new PdfPCell(
                    new Phrase(StringUtil.toCommaUnderZeroTrim(workReportData.getBaseDay()),
                            font_base));
            cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell2.setBorder(Rectangle.NO_BORDER);
            footerTable1.addCell(cell2);
            cell = new PdfPCell(footerTable1);
            cell.setBorder(Rectangle.BOX);
            cell.setBorderWidth(BORDER2__);
            cell.setColspan(5);
            footerTable.addCell(cell);

            //稼働日数
            cell = new PdfPCell(new Phrase(StringUtil.toCommaUnderZeroTrim(sumDay.toString()),
                    font_base));
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);

            //空
            cell = new PdfPCell();
            cell.setBorderWidth(BORDER2__);
            footerTable.addCell(cell);
            footerTable.addCell(cell);
            footerTable.addCell(cell);
            footerTable.addCell(cell);
            //代休
            if (sumDaikyu.compareTo(BigDecimal.ZERO) > 0) {
                cell = new PdfPCell(new Phrase(StringUtil.toCommaFormat(sumDaikyu.toString()),
                        font_title));
            } else {
                cell = new PdfPCell(new Phrase(" ", font_base));
            }
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);
            //振休
            if (sumHurikyu.compareTo(BigDecimal.ZERO) > 0) {
                cell = new PdfPCell(new Phrase(StringUtil.toCommaFormat(sumHurikyu.toString()),
                        font_title));
            } else {
                cell = new PdfPCell(new Phrase(" ", font_base));
            }
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);
            //有休
            if (sumYuukyu.compareTo(BigDecimal.ZERO) > 0) {
                cell = new PdfPCell(new Phrase(StringUtil.toCommaFormat(sumYuukyu.toString()),
                        font_title));
            } else {
                cell = new PdfPCell(new Phrase(" ", font_base));
            }
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);
            //欠勤
            if (sumKekkin.compareTo(BigDecimal.ZERO) > 0) {
                cell = new PdfPCell(new Phrase(StringUtil.toCommaFormat(sumKekkin.toString()),
                        font_title));
            } else {
                cell = new PdfPCell(new Phrase(" ", font_base));
            }
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);

            String thismonthSum = gsMsg.getMessage("tcd.138");

            //当月合計タイトル
            cell = new PdfPCell(new Phrase(thismonthSum, font_10));
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setColspan(2);
            footerTable.addCell(cell);

            String kijyunTimes = gsMsg.getMessage("tcd.139");

            //基準時間
            PdfPTable footerTable2 = new PdfPTable(2);
            PdfPCell cell3 = new PdfPCell(new Phrase(kijyunTimes, font_10));
            cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell3.setBorder(Rectangle.NO_BORDER);
            footerTable2.addCell(cell3);

            PdfPCell cell4 = new PdfPCell(
                    new Phrase(workReportData.getSumBaseHour(),
                            font_base));
            cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell4.setBorder(Rectangle.NO_BORDER);
            footerTable2.addCell(cell4);
            cell = new PdfPCell(footerTable2);
            cell.setBorder(Rectangle.BOX);
            cell.setBorderWidth(BORDER2__);
            cell.setColspan(5);
            footerTable.addCell(cell);

            //稼動時間
            cell = new PdfPCell(new Phrase(workReportData.getSumUtil(),
                    font_base));
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);
            //残業時間
            cell = new PdfPCell(new Phrase(workReportData.getSumOver(),
                    font_base));
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);
            //深夜時間
            cell = new PdfPCell(new Phrase(workReportData.getSumNight(),
                    font_base));
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);
            //遅刻時間
            cell = new PdfPCell(new Phrase(workReportData.getSumTikoku(), font_base));
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);
            //早退時間
            cell = new PdfPCell(new Phrase(workReportData.getSumSoutai(), font_base));
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            footerTable.addCell(cell);
            //空
            cell = new PdfPCell();
            cell.setBorderWidth(BORDER2__);
            footerTable.addCell(cell);
            footerTable.addCell(cell);
            footerTable.addCell(cell);
            footerTable.addCell(cell);
            //当月合計
            cell = new PdfPCell(new Phrase(
                    workReportData.getSumUtil() + " h", font_base));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorderWidth(BORDER2__);
            cell.setColspan(2);
            footerTable.addCell(cell);

            cell = new PdfPCell(titleTable);
            cell.setBorderWidth(BORDER1__);
            PdfPTable title = new PdfPTable(1);
            title.setWidthPercentage(100);
            title.addCell(cell);

            cell = new PdfPCell(meisaiTable);
            cell.setBorderWidth(BORDER1__);
            PdfPTable meisai = new PdfPTable(1);
            meisai.setWidthPercentage(100);
            meisai.addCell(cell);

            cell = new PdfPCell(footerTable);
            cell.setBorderWidth(BORDER1__);
            cell.setGrayFill(GRAY_FILL__);
            PdfPTable footer = new PdfPTable(1);
            footer.setWidthPercentage(100);
            footer.addCell(cell);

            headerTable.setSplitLate(false);
            title.setSplitLate(false);
            meisai.setSplitLate(false);
            footer.setSplitLate(false);
            //ドキュメントへ追加
            PdfUtil pdfUtil = new PdfUtil();
            pdfUtil.addCalcPaging(doc, headerTable, 0);
            pdfUtil.addCalcPaging(doc, title, 0);
            pdfUtil.addCalcPaging(doc, meisai, 0);
            pdfUtil.addCalcPaging(doc, footer, 0);

            doc.close();
            //書き出し開始
            oStream.write(byteout.toByteArray());

        } catch (IOException e) {
            log__.error("勤務表PDF出力に失敗しました。", e);
            throw new Exception("WorkReport Set Error", e);
        } finally {
            if (pdfwriter != null) {
                pdfwriter.close();
            }
            if (doc != null && doc.isOpen()) {
                doc.close();
            }
        }

        log__.debug("end");
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから勤務表用のフォントファイルのパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    private static String __getFontFilePath(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceFileSep(appRootPath
                + "WEB-INF/font/" + FONTFILE);
        return ret;
    }

    /**
     * <br>[機  能] 出力対象の空文字処理を行いテーブルへ設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param tabale テーブル
     * @param value フォーマット対象文字列
     * @param font フォント
     * @param emptycell 空セル
     * @param holFlg false:平日　true:休日
     */
    private void __emptyFormatForCell(
            PdfPTable tabale, String value, Font font, PdfPCell emptycell, boolean holFlg) {

        if (StringUtil.isNullZeroString(value) || Double.parseDouble(value) > 0) {
            PdfPCell cell = new PdfPCell(new Phrase(value, font));
            cell.setBorder(Rectangle.BOX);
            cell.setBorderWidth(BORDER2__);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            if (holFlg) {
                cell.setGrayFill(GRAY_FILL__);
            }
            tabale.addCell(cell);
        } else {
            tabale.addCell(emptycell);
        }

    }
}
