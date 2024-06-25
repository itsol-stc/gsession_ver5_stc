package jp.groupsession.v2.fil.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import jp.co.sjts.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * <br>[機  能] Excel (.xlsx) 読込み API イベント
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ExtractorEventXssf {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ExtractorEventXssf.class);

    /**
     * 全シートを読み込みます
     * @param pkg OPCPackage
     * @param callback 読込み文字列を戻すコールバック関数
     * @param maxLength 読込み文字列を戻すための最大文字数
     * @throws Exception 例外
     */
    public void processAllSheets(OPCPackage pkg, IProcessExtractorCallback callback,
                            int maxLength) throws Exception {

        try {
            // ファイルを読み込む
            XSSFReader r = new XSSFReader(pkg);

            StylesTable styles = r.getStylesTable();
            SharedStringsTable strings = r.getSharedStringsTable();

            // 全シート取得
            XSSFReader.SheetIterator sheets = (XSSFReader.SheetIterator) r.getSheetsData();

            while (sheets.hasNext()) {
                InputStream sheet = sheets.next();
                log__.debug("Processing new sheet ->" + sheets.getSheetName());

                InputSource sheetSource = new InputSource(sheet);

                XMLReader parser =
                    XMLReaderFactory.createXMLReader(
                            "org.apache.xerces.parsers.SAXParser"
                    );
                ContentHandler handler = new SheetHandler(sheets.getSheetName(),
                                                callback, maxLength, styles, strings);
                parser.setContentHandler(handler);
                parser.parse(sheetSource);

                sheet.close();
            }

        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * シート内容読込クラス
     */
    private static class SheetHandler extends DefaultHandler {

        /** コールバック関数 */
        private ExcelCellCallback cellCallback__;

        /** ワークブックスタイル */
        private StylesTable styles__;
        /** ワークブック文字列 */
        private SharedStringsTable strings__;

        /** 現在行 */
        private int currentRow__ = 0;

        /** セルタイプ */
        private String cellType__;
        /** フォーマットインデックス */
        private Short formatIndex__;
        /** フォーマット文字列 */
        private String formatString__;
        /** セルテキスト */
        private String lastContents__;

        /** フォーマットクラス */
        private ExcelCellFormatter formatter__;

        /**
         * excel 内容を読み込みます
         * @param sheetName シート名
         * @param callback 読込み文字列を戻すコールバック関数
         * @param maxLength 読込み文字列を戻すための最大文字数
         * @param styles スタイル
         * @param strings 文字列
         */
        private SheetHandler(String sheetName, IProcessExtractorCallback callback, int maxLength,
                                StylesTable styles, SharedStringsTable strings) {

            cellCallback__ = new ExcelCellCallback();
            cellCallback__.setExtractorCallback(callback);
            cellCallback__.setMaxLength(maxLength);
            cellCallback__.startSheet(sheetName);

            styles__ = styles;
            strings__ = strings;

            formatter__ = new ExcelCellFormatter();
        }

        /**
         * コンテンツ開始
         * @param uri アドレス
         * @param localName ローカル名
         * @param name コンテンツ名
         * @param attributes 属性
         * @throws SAXException SAX 例外
         */
        public void startElement(String uri, String localName, String name,
                Attributes attributes) throws SAXException {

            if (name.equals("row")) {
                //row
                currentRow__ = Integer.parseInt(attributes.getValue("r"));

            } else if (name.equals("c")) {
                // cell
                cellType__ = attributes.getValue("t");
                formatIndex__ = -1;
                formatString__ = null;
                String cellStyleStr = attributes.getValue("s");

                // 数値（フォーマット）
                if (cellType__ == null && cellStyleStr != null) {
                    int styleIndex = Integer.parseInt(cellStyleStr);
                    XSSFCellStyle style = styles__.getStyleAt(styleIndex);
                    formatIndex__ = style.getDataFormat();
                    formatString__ = style.getDataFormatString();
                }
            }

            // Clear contents cache
            lastContents__ = "";
        }

        /**
         * コンテンツ終了
         * @param uri アドレス
         * @param localName ローカル名
         * @param name コンテンツ名
         * @throws SAXException SAX 例外
         */
        public void endElement(String uri, String localName, String name)
                throws SAXException {

            // v => contents of a cell
            if ("v".equals(name)) {
                if (cellType__ == null) {
                    // numeric（フォーマットあり）
                    lastContents__ = formatter__.formatRawCellContents(
                            Double.parseDouble(lastContents__), formatIndex__, formatString__);

                } else if (cellType__.equals("b")) {
                    // boolean
                    lastContents__ = Boolean.toString(lastContents__.charAt(0) != '0');

                } else if (cellType__.equals("s")) {
                    // String
                    int idx = Integer.parseInt(lastContents__);
                    lastContents__ = new XSSFRichTextString(strings__.getEntryAt(idx)).toString();

                } else if (cellType__.equals("e")) {
                    // error
                    lastContents__ = "";
                }

                try {
                    cellCallback__.appendText(currentRow__, lastContents__);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else if ("worksheet".equals(name)) {
                // end sheet
                log__.debug("worksheet end");
                try {
                    cellCallback__.endSheet();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        /**
         * 文字列取得
         * @param ch 文字列
         * @param start スタート位置
         * @param length 桁数
         * @throws SAXException SAX 例外
         */
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            lastContents__ += new String(ch, start, length);
        }
    }

    /**
     * 数値セルを文字列化します。
     * @param value 値
     * @param formatIndex 書式インデックス
     * @param formatString 書式文字列
     * @param formatter 書式変換クラス
     * @return 書式化された変換文字列
     */
    public static String getNumericOrDateCellValue(double value,
            int formatIndex, String formatString, DataFormatter formatter) {

        // カスタム日付フォーマットの判定が不足しているため、再判定する
        String d = __getCustomDateCellValue(value, formatIndex, formatString);
        if (d != null) {
            return d;
        }

        return formatter.formatRawCellContents(value, formatIndex, formatString);
    }

    /**
     * カスタム日付書式のセル値を取得します。
     * @param value 値
     * @param formatIndex 書式インデックス
     * @param formatString 書式文字列
     * @return フォーマット化された文字列
     */
    private static String __getCustomDateCellValue(double value,
                int formatIndex, String formatString) {

        if (StringUtil.isNullZeroString(formatString)) {
            return null;
        }
        if (DateUtil.isADateFormat(formatIndex, formatString)) {
            return null;
        }

        // カスタム日付フォーマットの判定が不足しているため、再判定する
        if (formatString.indexOf("g") < 0
         && formatString.indexOf("e") < 0
         && formatString.indexOf("y") < 0
         && formatString.indexOf("m") < 0
         && formatString.indexOf("d") < 0
         && formatString.indexOf("a") < 0
         && formatString.indexOf("h") < 0
         && formatString.indexOf("s") < 0) {
            return null;
        }

        // 日付値取得
        Date d = null;
        try {
            d = DateUtil.getJavaDate(value, false);
        } catch (Exception e) {
            return null;
        }
        if (d == null) {
            return null;
        }

        // 書式文字列
        for (int i = 0; i < formatString.length(); i++) {
            char c = formatString.charAt(i);
            if (i < formatString.length() - 1) {
                char nc = formatString.charAt(i + 1);
                if (c == '\\') {
                    //「\-」「\,」「\.」「\ 」「\\」skip
                    switch (nc) {
                        case '-':
                        case ',':
                        case '.':
                        case ' ':
                        case '\\':
                            continue;
                        default:
                            break;
                    }
                } else if (c == ';' && nc == '@') {
                    //「;@」skip
                    i++;
                    continue;
                }
            }
            //sb.append(c);
        }

        return "";
    }

    /**
     * API イベントを使用して、excel ファイルを読み込みます。
     * @param pkg OPCPackage
     * @param callback 読込み文字列を戻すコールバック関数
     * @param maxLength 読込み文字列を戻すための最大文字数
     * @throws Exception 例外
     */
     public static void read(OPCPackage pkg, IProcessExtractorCallback callback,
                                 int maxLength) throws Exception {

         try {
             //全シート読込
             ExtractorEventXssf event = new ExtractorEventXssf();
             event.processAllSheets(pkg, callback, maxLength);
         } catch (Exception e) {
             throw e;
         }
    }
}
