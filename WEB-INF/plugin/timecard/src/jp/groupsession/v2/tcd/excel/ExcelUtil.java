package jp.groupsession.v2.tcd.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import jp.co.sjts.util.FileNameUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.GSConstTimecard;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.tcd.TimecardBiz;
import jp.groupsession.v2.tcd.model.TcdHolidayInfModel;

/**
 * <br>[機  能] 勤務表Excel出力に関するユーティリティークラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ExcelUtil {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ExcelUtil.class);

    /** BOOK */
    private Workbook workbook__ = null;
    /** Sheet */
    private Sheet sheet__ = null;

    /** 勤務表フォーマットファイル */
    private static final String FORMATFILE = "workReportFormat.xlsx";
    /** 勤務表フォーマットファイル ６０進数版 */
    private static final String FORMATFILE60 = "workReportFormat60.xlsx";

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public ExcelUtil(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public ExcelUtil() {
    }

    /** 画面ID */
    public static final String SCR_ID_010 = "tcd010";
    /** 画面ID */
    public static final String SCR_ID_110 = "tcd110";

    /**
     * <br>[機  能]指定されたストリームに勤務表データを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param outPath テンポラリディレクトリパス
     * @param appRootPath タイムカードデータディレクトリパス
     * @param oStream 勤務表データの出力先となるストリーム
     * @param workReportData 勤務表データ
     * @param admMdl 管理者設定
     * @param holDataList 休日区分リスト
     * @param subId
     * @throws Exception
     */
    public synchronized void createWorkReport(
            Connection con, String outPath,
            String appRootPath, OutputStream oStream,
            WorkReportData workReportData,
            TcdAdmConfModel admMdl,
            List<TcdHolidayInfModel> holDataList,
            String subId
            ) throws Exception {
        log__.debug("start");
        log__.info(" 勤務表ストリームへの設定 開始");

        FileInputStream in = null;

        List<TimecardLineData> reportDataList = workReportData.getLineDataList();
        log__.info(" 勤務表ストリームへの設定 勤務表フォーマットの取得 開始");
        try {
            String path = null;
            //フォーマットファイル情報取得
            if (admMdl.getTacWorkreportKbn() != 0) {
                CommonBiz cmnBiz = new CommonBiz();
                CmnBinfModel binMdl = null;
                binMdl = cmnBiz.getBinInfo(con, admMdl.getTacWorkreportSid(), reqMdl__.getDomain());
                String dateStr = (new UDate()).getDateString();
                cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, outPath, 0);

                File saveFilePath = Cmn110Biz.getSaveFilePath(outPath, dateStr, 0);
                File objFilePath = Cmn110Biz.getObjFilePath(outPath, dateStr, 0);
                File destFile = new File(
                        FileNameUtil.createFilePath(outPath, GSConstTimecard.TCD_TEMP_FORMAT_NAME)
                        );
                IOTools.copyBinFile(saveFilePath, destFile);
                path = IOTools.replaceFileSep(outPath + destFile.getName());
                //一括出力の場合フォーマットファイルを削除
                if (!Objects.equals(subId, SCR_ID_010)) {
                    TimecardBiz tcdCmnBiz = new TimecardBiz();
                    String delFilePath = tcdCmnBiz.getTempDir(reqMdl__, 
                            SCR_ID_110, subId, saveFilePath.getName());
                    String delObjPath = tcdCmnBiz.getTempDir(reqMdl__, 
                            SCR_ID_110, subId, objFilePath.getName());
                    IOTools.deleteFile(delFilePath);
                    IOTools.deleteFile(delObjPath);
                }
            } else {
                path = __getWorkTemplateFilePath(appRootPath, admMdl);
            }
            log__.debug(">>>path :" + path);
            in = new FileInputStream(path);
            workbook__ = WorkbookFactory.create(in);

            sheet__ = workbook__.getSheetAt(1);
            log__.info(" 勤務表ストリームへの設定 勤務表フォーマットの取得 終了　基本データの設定開始");
            //該当年
            Cell clYear = getCell2(1, 1);
            setCellString(clYear, String.valueOf(workReportData.getYear()));
            //該当月
            Cell clMonth = getCell2(1, 3);
            setCellString(clMonth, String.valueOf(workReportData.getMonth()));

            //氏名
            Cell clName = getCell2(1, 5);
            setCellString(clName, workReportData.getUserName());

            //--- 追加 2024/08/08 システム開発Gr 塩見和則
            //社員番号
            Cell clSyainno = getCell2(1, 19);
            setCellString(clSyainno, workReportData.getSyainNo());
            //---

            //基準稼働時間
            Cell clHour = getCell2(1, 9);
            setCellString(clHour, workReportData.getBaseHour());

            //基準稼働日数
            Cell clBday = getCell2(1, 11);
            setCellString(clBday, workReportData.getBaseDay());

            //有休合計
            Cell clYuukyuu = getCell2(1, 13);
            setCellString(clYuukyuu, workReportData.getSumYukyu());
            //欠勤合計
            Cell clKekkin = getCell2(1, 15);
            setCellString(clKekkin, workReportData.getSumKekkin());
            //その他合計
            Cell clSonota = getCell2(1, 17);
            setCellString(clSonota, workReportData.getSumOther());
            
            //休日区分名
            int line = 25;
            
            log__.info(" 勤務表ストリームへの設定 基本データの設定 終了　休日区分データの設定開始");
            for (TcdHolidayInfModel mdl : holDataList) {

                Cell clHolKbn = getCell2(4, line);
                setCellString(clHolKbn, mdl.getThiName());
                line++;
            }
            Iterator<TimecardLineData> it = reportDataList.iterator();
            TimecardLineData reportData = null;
            log__.info(" 勤務表ストリームへの設定 休日区分データの設定 終了　各日データの設定開始");
            for (int i = 5; i < 36; i++) {
                if (it.hasNext()) {
                    reportData = (TimecardLineData) it.next();
                } else {
                    reportData = new TimecardLineData();
                }

                Row row = getRow(i);

                //休日フラグ 0:平日,1:休日
                int holFlg = reportData.getHoliday();

                //休日(土日、祝祭日)
                Cell clHol = getCell(row, 0);
                if (reportData.getDate() != null) {
                    setCellNumeric(clHol, holFlg);
                } else {
                    setCellString(clHol, "");
                }

                //日付
                Cell clDay = getCell(row, 1);
                if (reportData.getDate() != null) {
                    this.setCellNumeric(
                            clDay, Double.parseDouble(reportData.getDate().getStrDay()));
                } else {
                    setCellString(clDay, "");
                }

                //曜日
                Cell clYou = getCell(row, 2);
                if (reportData.getDate() != null) {
                    int wk = reportData.getDate().getWeek();
                    String jwk = UDateUtil.getStrWeek(wk, reqMdl__);
                    setCellString(clYou, jwk);
                } else {
                    setCellString(clYou, " ");
                }

                //打刻開始時間
                Cell strikeStime = getCell(row, 3);
                if (reportData.getStrikeStartTime() != null) {
                    setCellString(strikeStime, reportData.getStrikeStartTime());
                } else {
                    setCellString(strikeStime, "");
                }

                //打刻終了時間
                Cell strikeEtime = getCell(row, 4);
                if (reportData.getStrikeEndTime() != null) {
                    setCellString(strikeEtime, reportData.getStrikeEndTime());
                } else {
                    setCellString(strikeEtime, " ");
                }

                //開始時間
                Cell stime = getCell(row, 5);
                if (reportData.getStartTime() != null) {
                    setCellString(stime, reportData.getStartTime());
                } else {
                    setCellString(stime, "");
                }

                //終了時間
                Cell etime = getCell(row, 6);
                if (reportData.getEndTime() != null) {
                    setCellString(etime, reportData.getEndTime());
                } else {
                    setCellString(etime, " ");
                }

                //稼動時間
                Cell ktime = getCell(row, 7);
                String utilTime = reportData.getUtilTime();
                if (utilTime != null && utilTime.length() > 0) {
                    double utilTimeVal = Double.parseDouble(utilTime);
                    if (utilTimeVal > 0) {
                        setCellNumeric(ktime, utilTimeVal);
                    } else {
                        setCellString(ktime, " ");
                    }
                } else {
                    setCellString(ktime, " ");
                }

                //残業
                Cell clZan = getCell(row, 8);
                String overTime = reportData.getOverTime();
                if (overTime != null && overTime.length() > 0) {
                    double overTimeVal = Double.parseDouble(overTime);
                    if (overTimeVal > 0) {
                        setCellNumeric(clZan, overTimeVal);
                    } else {
                        setCellString(clZan, " ");
                    }
                } else {
                    setCellString(clZan, " ");
                }

                //深夜
                Cell clShin = getCell(row, 9);
                String nightOverTime = reportData.getNightOverTime();
                if (nightOverTime != null && nightOverTime.length() > 0) {
                    double nightOverTimeVal = Double.parseDouble(nightOverTime);
                    if (nightOverTimeVal > 0) {
                        setCellNumeric(clShin, nightOverTimeVal);
                    } else {
                        setCellString(clShin, " ");
                    }
                } else {
                    setCellString(clShin, " ");
                }

                //遅刻
                Cell clChiko = getCell(row, 10);
                __emptyFormatForCell(clChiko, reportData.getTikoku());

                //早退
                Cell clSou = getCell(row, 11);
                __emptyFormatForCell(clSou, reportData.getSoutai());

                //休日情報
                String holName = "";
                String holDays = "";
                Map<Integer, String> holDaysMap = reportData.getHolDaysMap();
                for (Map.Entry<Integer, String> map : holDaysMap.entrySet()) {
                    int idx = 25;
                    for (TcdHolidayInfModel holMdl : holDataList) {
                        if (map.getKey() == holMdl.getThiSid()) {
                            //デフォルト表示情報へ出力
                            __setDefHoliday(i, holDaysMap, reportData.getHolKbn());
                            holName = holMdl.getThiName();
                            holDays = map.getValue();
                            //休日区分ごとの日数表示
                            Cell clEachHolDays = getCell(row, idx);
                            __emptyFormatForCell(clEachHolDays, holDays);
                            break;
                        }
                        idx++;
                    }
                }
                
                //休日名
                Cell clHolName = getCell(row, 12);
                setCellString(clHolName, holName);
                
                //休日日数
                Cell clHolDays = getCell(row, 13);
                __emptyFormatForCell(clHolDays, holDays);

                //休日内容
                Cell clHolValue = getCell(row, 14);
                String holValue = reportData.getHolValue();
                setCellString(clHolValue, holValue);

                //備 考
                Cell clBikou = getCell(row, 15);
                String bikou = reportData.getBikou();
                if (bikou != null) {
                    setCellString(clBikou, bikou);
                } else {
                    setCellString(clBikou, "");
                }
            }
            log__.info(" 勤務表ストリームへの設定 各日データの設定 終了　ワークブックの関数再計算開始");
            //ファイルopen時に数式を実行する
            ((XSSFSheet) workbook__.getSheetAt(1)).setForceFormulaRecalculation(true);
            ((XSSFSheet) workbook__.getSheetAt(0)).setForceFormulaRecalculation(true);
            log__.info(" 勤務表ストリームへの設定 ワークブックの関数再計算 終了　書き出し開始");
            workbook__.write(oStream);

        } catch (IOException e) {
            throw new Exception("WorkReport Set Error", e);
        } finally {
            try {
                in.close();
            } catch (Exception e) { }
        }
        log__.info(" 勤務表ストリームへの設定 終了");
        log__.debug("end");
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから勤務表テンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @param admMdl 基本設定
     * @return テンプレートファイルのパス文字列
     */
    private static String __getWorkTemplateFilePath(String appRootPath, TcdAdmConfModel admMdl) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String filename = FORMATFILE;
        if (admMdl.getTacKansan() == GSConstTimecard.TIMECARD_SINSU[0]) {
            //10進数
            filename = FORMATFILE;
        } else {
            //60進数
            filename = FORMATFILE60;
        }
        String ret = IOTools.replaceFileSep(appRootPath
                + "WEB-INF/plugin/timecard/template/" + filename);
        return ret;
    }

    /**
     * <p>セルにStringをセットする
     * @param cell 対象のセル
     * @param str セットする文字列
     * @return セル
     */
    private Cell setCellString(Cell cell, String str) {
//        cell.setEncoding(Cell.ENCODING_UTF_16);
        cell.setCellType(Cell.CELL_TYPE_STRING);
        cell.setCellValue(str);
        return cell;
    }


    /**
     * <p>セルにStringをセットするNUMERIC
     * @param cell 対象のセル
     * @param i セットする数値
     * @return セル
     */
    private Cell setCellNumeric(Cell cell, double i) {
        cell.setCellType(Cell.CELL_TYPE_NUMERIC);
        cell.setCellValue(i);
        return cell;
    }

    /**
     * <p>行を返す
     * @param index 行数
     * @return 行
     */
    private Row getRow(int index) {
        Row row = sheet__.getRow(index);
        if (row == null) {
            row = sheet__.createRow(index);
        }
        return row;
    }

    /**
     * <p>セルを返す
     * @param row 行
     * @param index 取得するセルのインデックス
     * @return 取得したセル
     */
    private Cell getCell(Row row, int index) {
        Cell cell = row.createCell(index);
        return cell;
    }

    /**
     * <p>セルを返す
     * @param rowIdx 行数
     * @param colIdx 列数
     * @return 行
     */
    private Cell getCell2(int rowIdx, int colIdx) {
        Row row = getRow(rowIdx);
        Cell cell = row.createCell((short) colIdx);
        return cell;
    }

    /**
     * <br>[機  能] Excel出力用の数値項目の空文字対応表現を出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param cell Excelセル
     * @param value フォーマット対象文字列
     */
    private void __emptyFormatForCell(Cell cell, String value) {
        if (StringUtil.isNullZeroString(value) || Double.parseDouble(value) == 0) {
            setCellString(cell, " ");
            return;
        }
        setCellNumeric(cell, Double.parseDouble(value));
    }

    /**
     * <br>[機  能] Excel出力用のCellStyleを設定する(一覧用)
     * <br>[解  説]
     * <br>[備  考]
     * @param cell Excelセル
     * @param value フォーマット対象文字列
     */
    private CellStyle __setListCellStyle(int i) {
        CellStyle style = workbook__.createCellStyle();
        style.setBorderLeft(CellStyle.BORDER_HAIR);
        style.setBorderRight(CellStyle.BORDER_HAIR);
        style.setBorderTop(CellStyle.BORDER_THIN);
        __setBottomCellStyle(i, style);
        return style;
    }

    /**
     * <br>[機  能] Excel出力用のCellStyleを設定する(最下部用)
     * <br>[解  説]
     * <br>[備  考]
     * @param cell Excelセル
     * @param value フォーマット対象文字列
     */
    private CellStyle __setBottomCellStyle(int i, CellStyle style) {
        if (i == 35) {
            style.setBorderBottom(CellStyle.BORDER_MEDIUM);
        } else {
            style.setBorderBottom(CellStyle.BORDER_THIN);
        }
        return style;
    }

    /**
     * <br>[機  能] Excel出力用のCellStyleを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param rowNum 行数
     * @param holDaysMap 休日SID、休日日数
     * @param holKbn 0その他　1欠勤　2有休
     */
    private void __setDefHoliday(int rowNum, Map<Integer,
            String> holDaysMap, int holKbn) {

        for (Map.Entry<Integer, String> map : holDaysMap.entrySet()) {
            CellStyle style = workbook__.createCellStyle();
            Row row = getRow(rowNum);
            if (map.getKey() == GSConstTimecard.THI_SID_HURIKYU) {
                Cell clHurikyu = getCell(row, 22);
                __emptyFormatForCell(clHurikyu, map.getValue());
                style.setBorderLeft(CellStyle.BORDER_HAIR);
                style.setBorderRight(CellStyle.BORDER_HAIR);
                style.setBorderTop(CellStyle.BORDER_THIN);
                style = __setBottomCellStyle(rowNum, style);
                clHurikyu.setCellStyle(style);
            } else if (map.getKey() == GSConstTimecard.THI_SID_DAIKYU) {
                Cell clDaikyu = getCell(row, 21);
                __emptyFormatForCell(clDaikyu, map.getValue());
                style.setBorderLeft(CellStyle.BORDER_MEDIUM);
                style.setBorderRight(CellStyle.BORDER_HAIR);
                style.setBorderTop(CellStyle.BORDER_THIN);
                style = __setBottomCellStyle(rowNum, style);
                clDaikyu.setCellStyle(style);
            } else if (holKbn == GSConstTimecard.HOL_KBN_KEKKIN) {
                Cell clKekkin = getCell(row, 24);
                __emptyFormatForCell(clKekkin, map.getValue());
                style.setBorderLeft(CellStyle.BORDER_HAIR);
                style.setBorderRight(CellStyle.BORDER_MEDIUM);
                style.setBorderTop(CellStyle.BORDER_THIN);
                style = __setBottomCellStyle(rowNum, style);
                clKekkin.setCellStyle(style);
            } else if (holKbn == GSConstTimecard.HOL_KBN_YUUKYUU) {
                Cell clYuukyuu = getCell(row, 23);
                __emptyFormatForCell(clYuukyuu, map.getValue());
                style.setBorderLeft(CellStyle.BORDER_HAIR);
                style.setBorderRight(CellStyle.BORDER_HAIR);
                style.setBorderTop(CellStyle.BORDER_THIN);
                style = __setBottomCellStyle(rowNum, style);
                clYuukyuu.setCellStyle(style);
            }
        }

    }

    /**
     * <br>[機  能]勤務表フォーマットひな形
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param outPath テンポラリディレクトリパス
     * @param appRootPath タイムカードデータディレクトリパス
     * @param oStream 勤務表データの出力先となるストリーム
     * @param admMdl 管理者設定
     * @param holDataList 休日区分リスト
     * @param subId
     * @throws Exception
     */
    public synchronized void createHina(
            Connection con, String outPath,
            String appRootPath, OutputStream oStream,
            TcdAdmConfModel admMdl,
            List<TcdHolidayInfModel> holDataList,
            String subId
            ) throws Exception {
        log__.debug("start");

        FileInputStream in = null;

        try {
            String path = null;
            //フォーマットファイル情報取得
            path = __getWorkTemplateFilePath(appRootPath, admMdl);
            log__.debug(">>>path :" + path);
            in = new FileInputStream(path);
            workbook__ = WorkbookFactory.create(in);

            sheet__ = workbook__.getSheetAt(1);

            //該当年
            CellStyle style = workbook__.createCellStyle();
            style.setBorderLeft(CellStyle.BORDER_THIN);
            style.setBorderRight(CellStyle.BORDER_THIN);
            style.setBorderTop(CellStyle.BORDER_MEDIUM);
            style.setBorderBottom(CellStyle.BORDER_MEDIUM);
            //休日区分名
            int line = 25;
            int maxLine = 25 + holDataList.size() - 1;
            
            //デフォルトの休日区分名の枠よりも多い場合、既にある右太枠の削除
            if (holDataList.size() > 9) {
                Cell clHolDays;
                for (int row = 5; row < 36; row++) {
                    clHolDays = getCell2(row, 33);
                    style = __setListCellStyle(row);
                    clHolDays.setCellStyle(style);
                }
            }
            
            for (TcdHolidayInfModel mdl : holDataList) {

                Cell clHolKbn = getCell2(4, line);
                setCellString(clHolKbn, mdl.getThiName());
                style = workbook__.createCellStyle();
                style.setBorderTop(CellStyle.BORDER_MEDIUM);
                style.setBorderBottom(CellStyle.BORDER_THIN);
                
                if (line == 25) {
                    style.setBorderLeft(CellStyle.BORDER_MEDIUM);
                } else {
                    style.setBorderLeft(CellStyle.BORDER_HAIR);
                }
                if (line == maxLine && line >= 34) {
                    style.setBorderRight(CellStyle.BORDER_MEDIUM);
                } else {
                    style.setBorderRight(CellStyle.BORDER_HAIR);
                }
                clHolKbn.setCellStyle(style);
                
                //workReportFormatに記載されている休日区分名の枠よりも休日区分が多い場合
                if (line >= 34) {
                    Cell clHolDays;
                    for (int row = 5; row < 36; row++) {
                        clHolDays = getCell2(row, line);
                        style = __setListCellStyle(row);
                        if (line == maxLine) {
                            style.setBorderRight(CellStyle.BORDER_MEDIUM);
                        } else {
                            style.setBorderRight(CellStyle.BORDER_HAIR);
                        }
                        clHolDays.setCellStyle(style);
                    }
                }
                line++;
            }
            //ファイルopen時に数式を実行する
            ((XSSFSheet) workbook__.getSheetAt(1)).setForceFormulaRecalculation(true);
            ((XSSFSheet) workbook__.getSheetAt(0)).setForceFormulaRecalculation(true);
            workbook__.write(oStream);

        } catch (IOException e) {
            throw new Exception("WorkReport Set Error", e);
        } finally {
            try {
                in.close();
            } catch (Exception e) { }
        }

        log__.debug("end");
    }
    
}
