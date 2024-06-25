package jp.groupsession.v2.sch.pdf;


import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
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
import com.lowagie.text.Image;
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
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.pdf.PdfUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstSchedule;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sch.sch010.Sch010DayOfModel;
import jp.groupsession.v2.sch.sch010.Sch010UsrModel;
import jp.groupsession.v2.sch.sch010.Sch010WeekOfModel;
import jp.groupsession.v2.sch.sch010.SimpleScheduleModel;
import jp.groupsession.v2.sch.sch040.Sch040DailyLineModel;
import jp.groupsession.v2.sch.sch040.Sch040DailyValueModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] スケジュール日間PDF出力に関するユーティリティクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchNikPdfUtil {


    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SchNikPdfUtil.class);
    /** 空文字 */
    private static final String EMP__ = " ";
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>１時間あたりの区切り数を設定します
     */
    private int memCnt;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public SchNikPdfUtil() {
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public SchNikPdfUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }


    /**
     * <br>[機  能] 月間スケジュールPdf出力に関するUtilクラスです。
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
    public void createSchNikkanPdf(
            SchNikPdfModel pdfMdl,
            String appRootPath,
            OutputStream oStream)
                    throws Exception, FileNotFoundException, DocumentException {

        memCnt = pdfMdl.getMemoriCount();
        Document doc = null;

        /* フォントファイルのパス */
        String fontPath = PdfUtil.getFontFilePath(appRootPath);

        //フォント(アカウント名)
        Font font_title = PdfUtil.getFont16b(fontPath);
        //フォント(ヘッダー)
        Font font_header = PdfUtil.getFont10b(fontPath);
        //フォント(スケジュール内容 青)
        Font font_main_blue = PdfUtil.getFont10(fontPath);
        font_main_blue.setColor(PdfUtil.FONT_COLOR_BLUE);
        //フォント(スケジュール内容 赤)
        Font font_main_r = PdfUtil.getFont10(fontPath);
        font_main_r.setColor(PdfUtil.FONT_COLOR_RED);
        //フォント(スケジュール内容 緑)
        Font font_main_g = PdfUtil.getFont10(fontPath);
        font_main_g.setColor(PdfUtil.FONT_COLOR_GREEN);
        //フォント(スケジュール内容 オレンジ)
        Font font_main_o = PdfUtil.getFont10(fontPath);
        font_main_o.setColor(PdfUtil.FONT_COLOR_ORANGE);
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

        //フォント(スケジュール内容 予定あり)
        Font font_main_yotei = PdfUtil.getFont10(fontPath);
        font_main_yotei.setColor(PdfUtil.FONT_COLOR_BLACK);

        //バックカラー（ヘッダ）
        Color color_header = PdfUtil.BG_COLOR_DARKBLUE;
        //バックカラー（日にち＆曜日）
        Color color_date = PdfUtil.BG_COLOR_LIGHTGRAY;
        //バックカラー (スケジュール内容)
        Color color_sch = PdfUtil.BG_COLOR_LIGHTYELLOW;

        ByteArrayOutputStream byteout = new ByteArrayOutputStream();
        //アウトプットストリームをPDFWriterに設定します。
        doc = new Document(PageSize.A4.rotate()); //(595F,842F)
        PdfWriter pdfwriter = null;
        PdfReader reader = null;
        PdfStamper stamper = null;
        try {
            pdfwriter = PdfWriter.getInstance(doc, byteout);

            GsMessage gsMsg = new GsMessage(reqMdl__);
            String smail = gsMsg.getMessage("sml.167");
            //出力するPDFに説明を付与します。
            doc.addAuthor("GroupSession");
            doc.addSubject(smail);

            doc.open();

            //文字入力範囲（横）
            float totalWidth = 920;
            //スケジュールの高さ設定
            float sch_height = 40f;

            //タイトル
            float [] width_title  = {0.3f, 0.7f};
            PdfPTable table_title =
                    PdfUtil.createTable(2, 100, totalWidth, width_title, Element.ALIGN_CENTER);

            //スケジュール
            float [] width_main  = {0.15f, 0.75f};
            PdfPTable table_main =
                    PdfUtil.createTable(2, 100, totalWidth, width_main, Element.ALIGN_CENTER);

            //スケジュール表示モード
            String topTitle = NullDefault.getString(
                    gsMsg.getMessage("schedule.108")
                    + "["
                    + gsMsg.getMessage("cmn.days2")
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
            //ヘッダー 年月
            cell_main = new PdfPCell(
                    new Phrase(
                            pdfMdl.getDspDate()
                            + " "
                            + pdfMdl.getDspRokuyou(),
                            font_header));
            settingWidth(cell_main, 1, 1, 0, 0);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_main.setPaddingBottom(3f);
            table_main.addCell(cell_main);

            //表示グループ
            cell_main = new PdfPCell(new Phrase(
                    PdfUtil.replaseBackslashToYen(
                        gsMsg.getMessage("cmn.show.group")
                        + "：" + pdfMdl.getDspGroup()),
                    font_header));
            settingWidth(cell_main, 1, 0, 1, 0);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_header);
            cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_main.addCell(cell_main);

            //ヘッダー 氏名
            cell_main = new PdfPCell(new Phrase(gsMsg.getMessage("cmn.name"), font_header));
            settingWidth(cell_main, 0.5f, 1, 0.25f, 1);
            cell_main.setLeading(1.1f, 1.1f);
            cell_main.setBackgroundColor(color_date);
            cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_main.addCell(cell_main);

            //メモリ数
            int cellCnt = 0;
            cellCnt = pdfMdl.getMemoriCount() * pdfMdl.getTimeChartList().size();
            //１件ごとのスケジュールデータ用テーブル
            PdfPTable table_sch = new PdfPTable(cellCnt);
            table_sch.setWidthPercentage(100);
            table_sch.setHorizontalAlignment(Element.ALIGN_CENTER);

            //ヘッダー 時間
            for (String strHour : pdfMdl.getTimeChartList()) {
                PdfPCell cell_sch = new PdfPCell(new Phrase(strHour, font_header));
                cell_sch.setColspan(pdfMdl.getMemoriCount());
                settingWidth(cell_sch, 0.5f, 0.25f, 0.25f, 0);
                cell_sch.setHorizontalAlignment(Element.ALIGN_CENTER);
                table_sch.addCell(cell_sch);
            }


            float leftWidth = 0;
            float rightWidth = 0;
            for (int i = 0; i < cellCnt; i++) {
                //ヘッダー メモリ
                PdfPCell cell_sch = new PdfPCell(new Phrase(" ", PdfUtil.getFontEmpty(fontPath)));
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
                settingWidth(cell_sch, 0, leftWidth, rightWidth, 0);
                cell_sch.setBackgroundColor(color_date);
                cell_sch.setFixedHeight(2);
                table_sch.addCell(cell_sch);
            }
            cell_main = new PdfPCell(table_sch);
            settingWidth(cell_main, 0, 0.25f, 1, 1);
            cell_main.setBackgroundColor(color_date);
            cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table_main.addCell(cell_main);

            table_main.setHeaderRows(2);


            //Topスケジュール＋Bottomスケジュール
            int schAllListCount = 0;
            for (ArrayList<Sch010WeekOfModel> schAllList : pdfMdl.getAllList()) {
                //Top or Bottomスケジュール
                for (Sch010WeekOfModel schWeekMdl : schAllList) {
                    Sch010UsrModel usrMdl = schWeekMdl.getSch010UsrMdl();
                    ArrayList < Sch010DayOfModel > dayList = schWeekMdl.getSch010SchList();
                    Sch010DayOfModel dayMdl = dayList.get(0);
                    ArrayList < SimpleScheduleModel > schList = dayMdl.getSchDataList();
                    ArrayList < Sch040DailyLineModel > dailyList =
                            __getDspScheduleList(schList, dayMdl.getSchDate(),
                                    pdfMdl.getIntFrom(), pdfMdl.getIntTo());

                    int userKbn = dayMdl.getUsrKbn();
                    int rowspan = dailyList.size();
                    //グループスケジュール
                    if (userKbn == GSConstSchedule.USER_KBN_GROUP) {
                        float [] width_grp  = {0.05f, 0.1f, 0.85f};
                        //グループスケジュールテーブル
                        PdfPTable table_grp =
                                PdfUtil.createTable(
                                        3, 100, totalWidth * 0.15f, width_grp,
                                        Element.ALIGN_CENTER);
                        PdfPCell cell_grp;

                        cell_grp = PdfUtil.setCellRet(
                                EMP__, 3, Element.ALIGN_CENTER, PdfUtil.getFontEmpty(fontPath));
                        cell_grp.setFixedHeight(0.5f);
                        table_grp.addCell(cell_grp);

                        PdfUtil.setCell(table_grp, EMP__, 1, Element.ALIGN_CENTER, font_header);

                        String imagePath =
                                IOTools.replaceFileSep(appRootPath + "common/images/groupicon.gif");
                        File file = new File(imagePath);
                        //ファイルの有無チェック
                        if (file.exists()) {
                            Image img = Image.getInstance(imagePath);
                            img.scalePercent(60f);
                            cell_grp = new PdfPCell(img, false);
                        } else {
                            cell_grp = new PdfPCell(new Phrase(EMP__, font_header));
                        }
                        cell_grp.setBorder(0);
                        cell_grp.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        table_grp.addCell(cell_grp);

                        PdfUtil.setCell(
                                table_grp, PdfUtil.replaseBackslashToYen(usrMdl.getUsrName()),
                                1, Element.ALIGN_LEFT, font_header);

                        cell_main = new PdfPCell(table_grp);
                        settingWidth(cell_main, 0, 1, 0.25f, 1);
                        cell_main.setRowspan(rowspan);
                        cell_main.setFixedHeight(sch_height);
                        table_main.addCell(cell_main);

                        //ログインユーザスケジュール
                    } else {
                        float [] width_topUsr  = {0.05f, 0.95f};
                        //ログインユーザ用テーブル
                        PdfPTable table_topUsr =
                                PdfUtil.createTable(
                                        2, 100, totalWidth * 0.15f, width_topUsr,
                                        Element.ALIGN_CENTER);
                        PdfPCell cell_topUsr;

                        cell_topUsr = PdfUtil.setCellRet(
                                EMP__, 2, Element.ALIGN_CENTER, PdfUtil.getFontEmpty(fontPath));
                        cell_topUsr.setFixedHeight(0.5f);
                        table_topUsr.addCell(cell_topUsr);

                        PdfUtil.setCell(table_topUsr, EMP__, 1, Element.ALIGN_CENTER, font_header);
                        PdfUtil.setCell(
                                table_topUsr, PdfUtil.replaseBackslashToYen(usrMdl.getUsrName()), 1,
                                Element.ALIGN_LEFT, font_header);

                        cell_main = new PdfPCell(table_topUsr);
                        settingWidth(cell_main, 0, 1, 0.25f, 1);
                        cell_main.setRowspan(rowspan);
                        cell_main.setPaddingBottom(5f);
                        table_main.addCell(cell_main);
                    }

                    //タイムチャート部分出力
                    Sch040DailyLineModel dailyLineMdl = null;
                    ArrayList < Sch040DailyValueModel > valueList = null;
                    Sch040DailyValueModel valueMdl = null;
                    int editConfOwn = 0;
                    int editConfGroup = 0;
                    int schDailyRowCount = 0;
                    //ユーザの一日のスケジュール件数
                    for (Sch040DailyLineModel sch040DailyLinemdl : dailyList) {

                        //スケジュール１行分のテーブル初期化
                        table_sch = new PdfPTable(cellCnt);
                        table_sch.setWidthPercentage(100);
                        table_sch.setHorizontalAlignment(Element.ALIGN_CENTER);

                        dailyLineMdl = sch040DailyLinemdl;
                        valueList = dailyLineMdl.getLineList();

                        //表示スケジュール１行分のスケジュール
                        for (Sch040DailyValueModel sch040DailyValueMdl : valueList) {
                            valueMdl = sch040DailyValueMdl;
                            int cols = valueMdl.getCols();
                            SimpleScheduleModel schMdl = valueMdl.getScheduleMdl();
                            if (schMdl != null) {
                                String title = null;
                                title = schMdl.getTitle();
                                int color = schMdl.getBgColor();

                                editConfOwn = schMdl.getKjnEdKbn();
                                editConfGroup = schMdl.getGrpEdKbn();
                                String dspTitle = "";
                                boolean yoteiFlg = false;
                                if ((schMdl.getPublic() == GSConstSchedule.DSP_PUBLIC
                                        || schMdl.getPublic() == GSConstSchedule.DSP_BELONG_GROUP)
                                        || (editConfOwn == GSConstSchedule.EDIT_CONF_OWN
                                        || editConfGroup == GSConstSchedule.EDIT_CONF_GROUP)) {

                                    //スケジュールタイトル
                                    if (schMdl.getUserKbn() != null) {
                                        if (schMdl.getUserKbn()
                                                .equals(String.valueOf(
                                                        GSConstSchedule.USER_KBN_GROUP))) {
                                            dspTitle = "[G]";
                                        } else if (schMdl.getUserKbn()
                                                .equals(GSConstCommon.PLUGIN_ID_PROJECT)) {
                                            dspTitle = "[TODO]";
                                        } else if (schMdl.getUserKbn()
                                                .equals(GSConstCommon.PLUGIN_ID_NIPPOU)) {
                                            dspTitle = "[アクション]";
                                        }
                                    }
                                    if (!StringUtil.isNullZeroStringSpace(schMdl.getTime())) {
                                        dspTitle += schMdl.getTime() + "\n";
                                    }
                                    dspTitle += title;
                                } else {
                                    //非公開パターン
                                    if (schMdl.getUserKbn() != null) {
                                        if (schMdl.getUserKbn().equals(
                                                String.valueOf(GSConstSchedule.USER_KBN_GROUP))) {
                                            dspTitle = "[G]";
                                        }
                                    }

                                    if (!StringUtil.isNullZeroStringSpace(schMdl.getTime())) {
                                        dspTitle += schMdl.getTime() + "\n";
                                    }
                                    dspTitle += title;
                                    yoteiFlg = true;
                                }
                                Font font_main;
                                if (yoteiFlg || schMdl.getPublic() == GSConstSchedule.DSP_TITLE) {
                                    font_main = font_main_yotei;
                                } else {
                                    switch (color) {
                                    case GSConstSchedule.BGCOLOR_BLUE :
                                        font_main = font_main_blue;
                                        break;
                                    case GSConstSchedule.BGCOLOR_RED :
                                        font_main = font_main_r;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GREEN :
                                        font_main = font_main_g;
                                        break;
                                    case GSConstSchedule.BGCOLOR_ORANGE :
                                        font_main = font_main_o;
                                        break;
                                    case GSConstSchedule.BGCOLOR_BLACK :
                                        font_main = font_main_black;
                                        break;
                                    case GSConstSchedule.BGCOLOR_NAVY :
                                        font_main = font_main_navy;
                                        break;
                                    case GSConstSchedule.BGCOLOR_MAROON :
                                        font_main = font_main_maroon;
                                        break;
                                    case GSConstSchedule.BGCOLOR_TEAL :
                                        font_main = font_main_teal;
                                        break;
                                    case GSConstSchedule.BGCOLOR_GRAY :
                                        font_main = font_main_gray;
                                        break;
                                    case GSConstSchedule.BGCOLOR_LBLUE :
                                        font_main = font_main_lblue;
                                        break;
                                    default:
                                        font_main = font_main_blue;
                                        break;
                                    }
                                }
                                dspTitle = StringUtilHtml.deleteHtmlTag(dspTitle);
                                dspTitle = PdfUtil.replaseBackslashToYen(dspTitle);
                                PdfPCell cell_sch = PdfUtil.setCellRet(
                                        dspTitle, cols, Element.ALIGN_CENTER, font_main);
                                cell_sch.setBackgroundColor(color_sch);
                                settingWidth(cell_sch, 0, 0.25f, 0.25f, 0);
                                cell_sch.setVerticalAlignment(Element.ALIGN_MIDDLE);
                                cell_sch.setPaddingBottom(4f);
                                table_sch.addCell(cell_sch);
                            } else {
                                PdfUtil.setCell(
                                        table_sch, EMP__, cols, Element.ALIGN_CENTER, font_header);
                            }
                        }
                        cell_main = new PdfPCell(table_sch);
                        if (schDailyRowCount == dailyList.size() - 1) {
                            settingWidth(cell_main, 0, 0.25f, 1, 1);
                        } else {
                            settingWidth(cell_main, 0, 0.25f, 1, 0.5f);
                        }
                        table_main.addCell(cell_main);

                        schDailyRowCount++;
                    }
                }

                //最後の表示の場合
                if (schAllListCount == pdfMdl.getAllList().size() - 1) {
                    break;
                }

    //----------- グループ本人 と グループメンバー間のヘッダー出力
                cell_main = new PdfPCell(
                        new Phrase(gsMsg.getMessage("schedule.74"), font_header));

                settingWidth(cell_main, 0, 1, 0, 1);
                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setBackgroundColor(color_header);
                cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table_main.addCell(cell_main);

                //表示グループ
                cell_main = new PdfPCell(new Phrase(EMP__, font_header));
                settingWidth(cell_main, 0, 0, 1, 1);
                cell_main.setLeading(1.1f, 1.1f);
                cell_main.setBackgroundColor(color_header);
                cell_main.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table_main.addCell(cell_main);

//                //ヘッダー 氏名
//                cell_main = new PdfPCell(
//                        new Phrase(gsMsg.getMessage("cmn.name"), font_header));
//                settingWidth(cell_main, 0, 1, 0.25f, 1);
//                cell_main.setLeading(1.1f, 1.1f);
//                cell_main.setBackgroundColor(color_date);
//                cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                table_main.addCell(cell_main);
//
//                //メモリ数
//                table_sch = new PdfPTable(cellCnt);
//                table_sch.setWidthPercentage(100);
//                table_sch.setHorizontalAlignment(Element.ALIGN_CENTER);
//
//                //ヘッダー 時間
//                for (String strHour : pdfMdl.getTimeChartList()) {
//                    PdfPCell cell_sch = new PdfPCell(new Phrase(strHour, font_header));
//                    cell_sch.setColspan(pdfMdl.getMemoriCount());
//                    settingWidth(cell_sch, 0, 0.25f, 0.25f, 0);
//                    cell_sch.setHorizontalAlignment(Element.ALIGN_CENTER);
//                    table_sch.addCell(cell_sch);
//                }
//
//                for (int i = 0; i < cellCnt; i++) {
//                    //ヘッダー メモリ
//                    PdfPCell cell_sch = new PdfPCell(
//                                            new Phrase(" ", PdfUtil.getFontEmpty(fontPath)));
//                    if (i == 0) {
//                        leftWidth = 0;
//                        rightWidth = 0.25f;
//                    } else if (i == cellCnt - 1) {
//                        leftWidth = 0.25f;
//                        rightWidth = 0;
//                    } else {
//                        leftWidth = 0.25f;
//                        rightWidth = 0.25f;
//                    }
//                    settingWidth(cell_sch, 0, leftWidth, rightWidth, 0);
//                    cell_sch.setBackgroundColor(color_date);
//                    cell_sch.setFixedHeight(2);
//                    table_sch.addCell(cell_sch);
//                }
//                cell_main = new PdfPCell(table_sch);
//                settingWidth(cell_main, 0, 0.25f, 1, 1);
//                cell_main.setBackgroundColor(color_date);
//                cell_main.setHorizontalAlignment(Element.ALIGN_CENTER);
//                cell_main.setVerticalAlignment(Element.ALIGN_MIDDLE);
//                table_main.addCell(cell_main);
    //-------------------ここまで

                schAllListCount++;
            }

            table_title.setSplitLate(false);
            table_main.setSplitLate(false);

            PdfUtil pdfUtil = new PdfUtil();
            pdfUtil.addCalcPaging(doc, table_title, 0);
            pdfUtil.addCalcPaging(doc, table_main, 0);

            doc.close();

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
            if (reader != null) {
                reader.close();
            }
            if (pdfwriter != null) {
                pdfwriter.close();
            }
            if (doc != null && doc.isOpen()) {
                doc.close();
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
    private PdfPCell settingWidth(PdfPCell cell, float top, float left, float right, float bottom) {

        cell.setBorderWidthTop(top);
        cell.setBorderWidthLeft(left);
        cell.setBorderWidthRight(right);
        cell.setBorderWidthBottom(bottom);
        return cell;
    }

    /**
     * <br>スケジュール情報を画面表示用に格納します
     * @param schList スケジュールリスト
     * @param dspDate 表示日付
     * @param frHour 表示開始時間
     * @param toHour 表示終了時間
     * @return ArrayList < Sch040DailyLineModel >
     */
    private ArrayList<Sch040DailyLineModel> __getDspScheduleList(
            ArrayList<SimpleScheduleModel> schList,
            String dspDate,
            int frHour,
            int toHour
            ) {

        //ユーザ別スケジュール一式(lineを格納)
        ArrayList<Sch040DailyLineModel> dailyList = new ArrayList<Sch040DailyLineModel>();

        //1行分のスケジュール
        Sch040DailyLineModel lineMdl = new Sch040DailyLineModel();
        ArrayList<Sch040DailyValueModel> lineList = new ArrayList<Sch040DailyValueModel>();

        //1スケジュール
        Sch040DailyValueModel valueMdl = null;

        //スケジュール情報無し
        if (schList.size() == 0) {
            valueMdl = new Sch040DailyValueModel();
            valueMdl.setScheduleMdl(null);
            valueMdl.setCols(__getColsTotal(frHour, toHour));
            lineList.add(valueMdl);
            lineMdl.setLineList(lineList);
            dailyList.add(lineMdl);
            return dailyList;
        }

        //出力済情報の格納用
        HashMap<Integer, SimpleScheduleModel> map
        = new HashMap<Integer, SimpleScheduleModel>();

        while (schList.size() != map.size()) {
            //１行分を作成する
            lineMdl = __getDailyLineMdl(schList, map, dspDate, frHour, toHour);
            dailyList.add(lineMdl);
        }

        return dailyList;
    }

    /**
     * 開始時間～終了時間のColsを取得します
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param frHour 開始時間(HH)
     * @param toHour 終了時間(HH)
     * @return int cols
     */
    private int __getColsTotal(int frHour, int toHour) {
        int ret = 0;
        int hour = toHour - frHour + 1;
        ret = hour * memCnt;
        return ret;
    }

    /**
     * <br>１行分のスケジュールモデルを生成する
     * <br>[備考]出力済みのスケジュール情報はHashMapに格納します
     * @param schList スケジュール情報
     * @param map 出力済みのスケジュール情報
     * @param dspDate 表示日付
     * @param frHour 出力開始時刻
     * @param toHour 出力終了時刻
     * @return Sch040DailyLineModel
     */
    private Sch040DailyLineModel __getDailyLineMdl(
            ArrayList<SimpleScheduleModel> schList,
            HashMap<Integer, SimpleScheduleModel> map,
            String dspDate,
            int frHour,
            int toHour) {

        log__.debug("-- __getDailyLineMdl START --");
        Sch040DailyLineModel lineMdl = new Sch040DailyLineModel();
        ArrayList<Sch040DailyValueModel> lineList = new ArrayList<Sch040DailyValueModel>();
        Sch040DailyValueModel valueMdl = null;

        SimpleScheduleModel schMdl = null;
        int endIndex = 1;

        for (int i = 0; i < schList.size(); i++) {

            schMdl = schList.get(i);
            if (map.containsKey(i)) {
                //出力済みは除外
                continue;
            }

                //格納可能か判定し可能であれば格納する
                if (__isStorage(schMdl, dspDate, frHour, endIndex)) {

                    //空白部分を設定
                    valueMdl = __getBlankSchedule(schMdl, dspDate, frHour, endIndex);
                    if (valueMdl != null) {

                        lineList.add(valueMdl);
                        endIndex = endIndex + valueMdl.getCols();
                    }
                    //スケジュール部分を設定
                    int cols = __getCols(schMdl, dspDate, frHour, toHour, endIndex);
                    valueMdl = new Sch040DailyValueModel();
                    valueMdl.setCols(cols);
                    valueMdl.setScheduleMdl(schMdl);
                    lineList.add(valueMdl);
                    endIndex = endIndex + cols;
                    map.put(i, schMdl);
                }
            if (endIndex == __getColsTotal(frHour, toHour)) {
                break;
            }
        }

        //表示終了時刻までの空白を設定
        valueMdl = __getEndBlankSchedule(schMdl, dspDate, frHour, toHour, endIndex);
        if (valueMdl != null) {
            lineList.add(valueMdl);
            endIndex = endIndex + valueMdl.getCols();
        }

        lineMdl.setLineList(lineList);
        log__.debug("-- __getDailyLineMdl END --");
        return lineMdl;
    }

    /**
     * <br>スケジュール情報が格納可能か判定する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param frHour 画面表示開始時刻
     * @param endIndex 出力済みポインタ
     * @return true:格納可能 false:格納不可
     */
    private boolean __isStorage(
            SimpleScheduleModel schMdl,
            String dspDate,
            int frHour,
            int endIndex) {
        int index = 0;

        //表示開始インデックスを取得
        index = __getIndex(schMdl, dspDate, frHour);
        if (index >= endIndex) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * <br>表示開始時刻とスケジュール情報からスケジュール開始時刻のインデックスを取得する
     * @param schMdl スケジュール情報
     * @param strDate 表示日付
     * @param dspHour 表示開始時刻
     * @return int 開始時刻のインデックス
     */
    private int __getIndex(SimpleScheduleModel schMdl, String strDate, int dspHour) {
        int index = 1;
        UDate dspDate = new UDate();
        dspDate.setDate(strDate);
        dspDate.setHour(dspHour);
        dspDate.setMinute(GSConstSchedule.DAY_START_MINUTES);
        dspDate.setSecond(GSConstSchedule.DAY_START_SECOND);

        UDate frDate = schMdl.getFromDate();
        if (dspDate.compareDateYMDHM(frDate) != UDate.LARGE) {
            index = 1;
        } else {
            int hour = frDate.getIntHour();
            int min = frDate.getIntMinute();
            int ans1 = index + ((hour - dspHour) * memCnt);
            int hourMemCount = 60 / memCnt;
            int ans2 = min / hourMemCount;
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>スケジュール情報と出力済みポインタから空スケジュールを必要に応じて生成する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param frHour 表示開始時刻
     * @param endIndex 出力済みポインタ
     * @return Sch040DailyValueModel
     */
    private Sch040DailyValueModel __getBlankSchedule(
            SimpleScheduleModel schMdl,
            String dspDate,
            int frHour,
            int endIndex) {
        Sch040DailyValueModel valueMdl = null;
        int index = __getIndex(schMdl, dspDate, frHour);
        int cols = index - endIndex;
        if (cols > 0) {
            //空スケジュールを生成する
            valueMdl = new Sch040DailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setScheduleMdl(null);
        }

        return valueMdl;
    }

    /**
     * <br>スケジュールの開始・終了からCOLSを取得する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param endIndex 出力済みポインタ
     * @return スケジュールの横幅(cols)
     */
    private int __getCols(
            SimpleScheduleModel schMdl,
            String dspDate,
            int frHour,
            int toHour,
            int endIndex) {
        int cols = 0;
        int frIndex = __getIndex(schMdl, dspDate, frHour);
        int toIndex = __getEndIndex(schMdl, dspDate, frHour, toHour);
        cols = toIndex - frIndex + 1;

        return cols;
    }

    /**
     * <br>表示開始時刻とスケジュール情報からスケジュール終了時刻のインデックスを取得する
     * @param schMdl スケジュール情報
     * @param strDate 表示日付
     * @param dspHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @return int 開始時刻のインデックス
     */
    private int __getEndIndex(SimpleScheduleModel schMdl, String strDate, int dspHour, int toHour) {
        int index = 1;
        UDate dspDate = new UDate();
        dspDate.setDate(strDate);
        dspDate.setHour(toHour);
        dspDate.setMinute(GSConstSchedule.DAY_END_MINUTES);
        dspDate.setSecond(GSConstSchedule.DAY_END_SECOND);

        UDate toDate = schMdl.getToDate();

        if (dspDate.compareDateYMDHM(toDate) == UDate.LARGE
                || schMdl.getTimeKbn() == GSConstSchedule.TIME_NOT_EXIST) {
            index = __getColsTotal(dspHour, toHour);
        } else {
            int hour = toDate.getIntHour();
            int min = toDate.getIntMinute();
            int ans1 = (hour - dspHour) * memCnt;
            int hourMemCount = 60 / memCnt;
            int ans2 = min / hourMemCount;
            index = ans1 + ans2;
        }

        return index;
    }

    /**
     * <br>表示終了時刻と出力済みポインタから空スケジュールを必要に応じて生成する
     * @param schMdl スケジュール情報
     * @param dspDate 表示日付
     * @param frHour 表示開始時刻
     * @param toHour 表示終了時刻
     * @param endIndex 出力済みポインタ
     * @return Sch040DailyValueModel
     */
    private Sch040DailyValueModel __getEndBlankSchedule(
            SimpleScheduleModel schMdl,
            String dspDate,
            int frHour,
            int toHour,
            int endIndex) {

        Sch040DailyValueModel valueMdl = null;
        int index = __getColsTotal(frHour, toHour) + 1;
        int cols = index - endIndex;
        if (cols > 0) {
            //空スケジュールを生成する
            valueMdl = new Sch040DailyValueModel();
            valueMdl.setCols(cols);
            valueMdl.setScheduleMdl(null);
        }

        return valueMdl;
    }
}