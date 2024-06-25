package jp.groupsession.v2.fil.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.BoolErrRecord;
import org.apache.poi.hssf.record.BoundSheetRecord;
import org.apache.poi.hssf.record.CellRecord;
import org.apache.poi.hssf.record.CellValueRecordInterface;
import org.apache.poi.hssf.record.EOFRecord;
import org.apache.poi.hssf.record.ExtendedFormatRecord;
import org.apache.poi.hssf.record.FormatRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.hssf.record.StringRecord;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;

/**
 * <br>[機  能] Excel (.xls) 読込み API イベント
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ExtractorEventHssf implements HSSFListener {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ExtractorEventHssf.class);

    /** コールバック関数 */
    private ExcelCellCallback cellCallback__;

    /** 現在のシートインデックス */
    private int sheetIndex__ = -1;
    /** 読込みのための行インデックス */
    private int nextRowIndex__;
    /** シート情報BOF */
    private boolean isSheetStart__;
    /** チャート情報BOF */
    private boolean isChartStart__;

    /** シート名 コレクション */
    private List<String> sheetNames__;
    /** 文字列レコード コレクション */
    private SSTRecord sstrec__;
    /** フォーマットクラス */
    private ExcelCellFormatter formatter__;
    /** フォーマットレコード コレクション */
    private Map<Integer, FormatRecord> customFormatRecords__;
    /** フォーマットインデックスレコード コレクション */
    private List<ExtendedFormatRecord> xfRecords__;

    /**
     * excel 内容を読み込みます
     * @param callback 読込み文字列を戻すコールバック関数
     * @param maxLength 読込み文字列を戻すための最大文字数
     */
    public ExtractorEventHssf(IProcessExtractorCallback callback, int maxLength) {
        cellCallback__ = new ExcelCellCallback();
        cellCallback__.setExtractorCallback(callback);
        cellCallback__.setMaxLength(maxLength);

        sheetNames__ = new ArrayList<String>();
        formatter__ = new ExcelCellFormatter(new HSSFDataFormatter());
        customFormatRecords__ = new Hashtable<Integer, FormatRecord>();
        xfRecords__ = new ArrayList<ExtendedFormatRecord>();
    }

    /**
     * Excel 各レコードを順に処理する
     * @param record レコード
     */
    public void processRecord(Record record) {

        switch (record.getSid()) {
            case BOFRecord.sid:
                // start
                BOFRecord bof = (BOFRecord) record;
                if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
                    // 1;start book

                } else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
                    // 3;start sheet
                    __startSheet();
                    isSheetStart__ = true;

                } else if (bof.getType() == BOFRecord.TYPE_CHART) {
                    if (!isSheetStart__) {
                        //グラフシートの場合
                        __startSheet();
                    }
                    isChartStart__ = true;
                }
                break;

            case BoundSheetRecord.sid:
                // 2;sheets
                BoundSheetRecord bsr = (BoundSheetRecord) record;
                sheetNames__.add(bsr.getSheetname());
                break;

            case SSTRecord.sid:
                // 2;Strings
                sstrec__ = (SSTRecord) record;
                break;

            case FormatRecord.sid:
                // 2;formats
                FormatRecord frec = (FormatRecord) record;
                customFormatRecords__.put(Integer.valueOf(frec.getIndexCode()), frec);
                break;

            case ExtendedFormatRecord.sid:
                // 2;format indexs
                ExtendedFormatRecord xrec = (ExtendedFormatRecord) record;
                xfRecords__.add(xrec);
                break;

            case EOFRecord.sid:
                if (isSheetStart__) {
                    if (isChartStart__) {
                        isChartStart__ = false;
                        return;
                    }

                    // シート終了
                    try {
                        cellCallback__.endSheet();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    isSheetStart__ = false;
                } else if (isChartStart__) {
                    // グラフシート終了
                    try {
                        cellCallback__.endSheet();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    isChartStart__ = false;
                }
                break;

            default:
                // セル
                if (isChartStart__) {
                   return;      //chart のデータは無視
                }
                __processCellRecord(record);
        }
    }

    /**
     * シート読込開始処理
     */
    private void __startSheet() {
        sheetIndex__++;
        cellCallback__.startSheet(sheetNames__.get(sheetIndex__));
        log__.debug("sheet start -> " + sheetNames__.get(sheetIndex__));
    }

    /**
     * セルレコードを処理する
     * @param record レコード
     */
    private void __processCellRecord(Record record) {

        String value = "";

        switch (record.getSid()) {
            case FormulaRecord.sid:
                // 計算式セル
                FormulaRecord fmrec = (FormulaRecord) record;
                switch (fmrec.getCachedResultType()) {
                    case Cell.CELL_TYPE_NUMERIC:
                        double num = fmrec.getValue();
                        if (!Double.isNaN(num)) {
                            value = __formatNumberDateCell(fmrec, num);
                        }
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        value = Boolean.toString(fmrec.getCachedBooleanValue());
                        break;
                    case Cell.CELL_TYPE_STRING:
                        nextRowIndex__ = fmrec.getRow();
                        return;
                    case Cell.CELL_TYPE_ERROR:
                        break;
                    default:
                }
                break;

            case NumberRecord.sid:
                // 数値セル
                NumberRecord numrec = (NumberRecord) record;
                value = __formatNumberDateCell(numrec, numrec.getValue());
                break;

            case BoolErrRecord.sid:
                // boolean
                BoolErrRecord boolRec = (BoolErrRecord) record;
                if (!boolRec.isError()) {
                    value = Boolean.toString(boolRec.getBooleanValue());
                }
                break;

            case LabelSSTRecord.sid:
                // 文字列セル
                LabelSSTRecord lrec = (LabelSSTRecord) record;
                value = sstrec__.getString(lrec.getSSTIndex()).toString();
                break;

            case StringRecord.sid:
                // 文字列セル for formula
                if (nextRowIndex__ >= 0) {
                    StringRecord srec = (StringRecord) record;
                    value = srec.getString();
                }
                break;

            default:
                return;
        }

        // 行インデックス取得
        int rowIndex = 0;
        if (record instanceof CellRecord) {
            rowIndex = ((CellRecord) record).getRow();
        } else {
            rowIndex = nextRowIndex__;
            nextRowIndex__ = -1;
        }

        // セルテキスト結合
        try {
            cellCallback__.appendText(rowIndex, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 数値、日付セルのフォーマットを整える
     * @param cell セル
     * @param value 値
     * @return フォーマット化された文字列
     */
    private String __formatNumberDateCell(CellValueRecordInterface cell, double value) {

        short formatIndex = __getFormatIndex(cell);
        String formatString = __getFormatString(formatIndex);

        return formatter__.formatRawCellContents(value, formatIndex, formatString);
    }

    /**
     * 指定セルのフォーマットインデックスを取得する
     * @param cell セル
     * @return フォーマットインデックス
     */
    private short __getFormatIndex(CellValueRecordInterface cell) {

        ExtendedFormatRecord xfr = xfRecords__.get(cell.getXFIndex());
        if (xfr == null) {
            return -1;
        }
        return xfr.getFormatIndex();
    }

    /**
     * 指定セルのフォーマット文字列を取得する
     * @param formatIndex フォーマットインデックス
     * @return フォーマット文字列
     */
    private String __getFormatString(short formatIndex) {

        String format = null;
        if (customFormatRecords__.containsKey(Integer.valueOf(formatIndex))) {
            FormatRecord tfr = customFormatRecords__.get(Integer.valueOf(formatIndex));
            if (tfr != null) {
                format = tfr.getFormatString();
            }
        } else {
            format = BuiltinFormats.getBuiltinFormat(formatIndex);
        }

        return format;
    }

    /**
     * API イベントを使用して、excel ファイルを読み込みます。
     * @param file 対象ファイルバイナリ
     * @param callback 読込み文字列を戻すコールバック関数
     * @param maxLength 読込み文字列を戻すための最大文字数
     * @throws Exception 例外
     */
     public static void read(File file, IProcessExtractorCallback callback,
                                 int maxLength) throws Exception {
         FileInputStream fis = null;
         try {
             fis = new FileInputStream(file);
             POIFSFileSystem poifs = new POIFSFileSystem(fis);

             read(poifs, callback, maxLength);

         } catch (IOException e) {
             throw e;
         } finally {
             try {
                 if (fis != null) {
                     fis.close();
                 }
             } catch (IOException e) {
             }
         }
    }

    /**
     * API イベントを使用して、excel ファイルを読み込みます。
     * @param poifs POIFSFileSystem
     * @param callback 読込み文字列を戻すコールバック関数
     * @param maxLength 読込み文字列を戻すための最大文字数
     * @throws Exception 例外
     */
     public static void read(POIFSFileSystem poifs, IProcessExtractorCallback callback,
                                 int maxLength) throws Exception {
         InputStream din = null;

         try {
             // Workbook stream 取得
             din = poifs.createDocumentInputStream("Workbook");

             // リスナーイベント set
             ExtractorEventHssf event = new ExtractorEventHssf(callback, maxLength);

             // イベント set
             HSSFEventFactory factory = new HSSFEventFactory();
             HSSFRequest req = new HSSFRequest();
             req.addListenerForAllRecords(event);

             factory.processEvents(req, din);

         } catch (IOException e) {
             throw e;
         } finally {
             try {
                 if (din != null) {
                     din.close();
                 }
             } catch (IOException e) {
             }
         }
    }
}
