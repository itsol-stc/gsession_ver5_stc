package jp.groupsession.v2.fil.extractor;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jp.co.sjts.util.StringUtil;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.oro.text.perl.Perl5Util;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.ExcelStyleDateFormatter;

/**
 * Excel セルの値を書式化する（日本語対応）クラス
 */
public class ExcelCellFormatter {

    /** 日付用デフォルトローケル */
    private static final Locale DATE_DEF_LOCALE = Locale.US;
    /** 日本語ローケル */
    private static final Locale JP_LOCALE = new Locale("ja", "JP", "JP");
    /** ダブルクォーテーション文字列避難用 */
    private static final char DQUOT_STR_SYMBOL = (char) 0x0000;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ExcelCellFormatter.class);

    /** 組み込みフォーマット */
    private static Map<Integer, Format> customBuiltinFormats__;

    /** フォーマットクラス */
    private DataFormatter formatter__;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public ExcelCellFormatter() {
        formatter__ = new DataFormatter();
    }

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param formatter フォーマットクラス
     */
    public ExcelCellFormatter(DataFormatter formatter) {
        formatter__ = formatter;
    }

    /**
     * BuiltinFormats と違う組み込み書式を取得
     */
    static {
        // BuiltinFormats と違うフォーマットを設定（日本対応？）
        customBuiltinFormats__ = new HashMap<Integer, Format>();
        customBuiltinFormats__.put(14, new SimpleDateFormat("yyyy/M/d", DATE_DEF_LOCALE));
        customBuiltinFormats__.put(22, new SimpleDateFormat("yyyy/M/d H:mm", DATE_DEF_LOCALE));
        customBuiltinFormats__.put(30, new SimpleDateFormat("M/d/yy", DATE_DEF_LOCALE));
        customBuiltinFormats__.put(31, new SimpleDateFormat("yyyy年M月d日", DATE_DEF_LOCALE));
        customBuiltinFormats__.put(32, new SimpleDateFormat("H時mm分", DATE_DEF_LOCALE));
        customBuiltinFormats__.put(33, new SimpleDateFormat("H時mm分ss秒", DATE_DEF_LOCALE));
        customBuiltinFormats__.put(55, new SimpleDateFormat("yyyy年M月", DATE_DEF_LOCALE));
        customBuiltinFormats__.put(56, new SimpleDateFormat("M月d日", DATE_DEF_LOCALE));
        customBuiltinFormats__.put(57, new SimpleDateFormat("Gy.M.d", JP_LOCALE));
        customBuiltinFormats__.put(58, new SimpleDateFormat("GGGGyy年M月d日", JP_LOCALE));
    }

    /**
     * 数値セルを文字列化します。
     * @param value 値
     * @param formatIndex 書式インデックス
     * @param formatString 書式文字列
     * @return 書式化された変換文字列
     */
    public String formatRawCellContents(double value, int formatIndex, String formatString) {

        String valueStr = null;
        Date d = __getDateContents(value);

        if (customBuiltinFormats__.containsKey(formatIndex)) {
            // 日本対応組み込み日付を置き換え
            Format f = customBuiltinFormats__.get(formatIndex);
            if (d != null && f instanceof DateFormat) {
                valueStr = f.format(d);
            } else {
                valueStr = f.format(value);
            }

        } else if (formatString == null) {
            // 書式なし
            valueStr = Double.toString(value);

        } else {
            // 日付フォーマット書式クラス作成
            Format dateFormat = null;
            try {
                if (d != null) {
                    dateFormat = __createDateFormat(formatString, d);
                    if (dateFormat instanceof ExcelStyleDateFormatter) {
                        ((ExcelStyleDateFormatter) dateFormat).setDateToBeFormatted(value);
                    }
                }
            } catch (Exception e) {
                log__.error("create format error (value="
                        + d + ";index=" + formatIndex + ";format=" + formatString + ")", e);
            }

            if (dateFormat == null) {
                // 数値
                valueStr = formatter__.formatRawCellContents(value, formatIndex, formatString);
            } else {
                // 日付
                valueStr = dateFormat.format(d);
            }
        }

        log__.debug(formatIndex + "\t" + formatString + "\t" + valueStr);
        return valueStr;
    }

    /**
     * 数値を日付に変換します。
     * @param value 値
     * @return 日付
     */
    private Date __getDateContents(double value) {

        if (!DateUtil.isValidExcelDate(value)) {
            return null;
        }

        // 日付値取得
        Date d = null;
        try {
            d = DateUtil.getJavaDate(value, false);
        } catch (Exception e) {
        }

        return d;
    }

    /**
     * 日付書式フォーマットを作成します。
     * @param formatString 書式文字列
     * @param d 日付値
     * @return 日付書式フォーマット
     */
    private Format __createDateFormat(String formatString, Date d) {
        String formatStr = formatString;
        Perl5Util util = new Perl5Util();

        if (StringUtil.isNullZeroString(formatStr)) {
            return null;
        }

        // エスケープ処理
        StringBuilder vf = new StringBuilder();
        formatStr = __escapeFormat(formatStr, vf);

        // オペレーティングシステム日付（JP書式日付:Full）
        if (vf.indexOf("[$-F800]") >= 0) {
            return DateFormat.getDateInstance(DateFormat.FULL);
        }
        // オペレーティングシステム時刻（時間書式:default）
        if (vf.indexOf("[$-F400]") >= 0) {
            return DateFormat.getTimeInstance();
        }

        // 日付フォーマットかどうか
        if (!__isDateFormatString(vf.toString())) {
            //log__.debug(formatString + "-> not date regex");
            return null;
        }

        // am/pm
        boolean hasAmPm = __isAmPm(vf.toString());

        // ローケル
        Locale locale = __getLocale(vf.toString());

        StringBuilder df = new StringBuilder();
        char[] chars = formatStr.toCharArray();
        int offset = 0;
        boolean isDate = false;
        boolean isGango = false;
        boolean isMonth = true;
        List<Integer> ms = new ArrayList<Integer>();

        while (offset < chars.length) {
            char c = chars[offset];

            if (c == '\"') {
                // ★ ダブルコーテーションで囲まれた文字
                String str = __getElapsedStr(chars, offset, '\"');
                __appendString(df, str);
                offset += str.toCharArray().length + 1;

            }  else if (c == '[') {
                // ★ [] 括弧内
                String str = "[" + __getElapsedStr(chars, offset, ']') + "]";
                if (util.match("/^\\[h+\\]$/i", str)) {
                    // 経過時間 [hh]
                    df.append(str.toUpperCase());
                    isDate = true;
                    isMonth = false;
                } else if (util.match("/^\\[m+\\]$/i", str)
                         || util.match("/^\\[s+\\]$/i", str)) {
                    // 経過分、秒 [mm] [ss]
                    df.append(str.toLowerCase());
                    isDate = true;
                    isMonth = false;
                } else if (!__isDefElapsed(str)) {
                    df.append(str);
                }
                offset += str.toCharArray().length - 1;

            } else if (Character.isLetter(c)) {
                // 連続した文字列を取得
                String repeatStr = __getRepeatStr(chars, offset, c);
                int size = repeatStr.length();
                int bf = df.length();
                char kigo = ' ';
                c = Character.toLowerCase(c);

                if (c == 'r') {
                    if (locale == null || locale == JP_LOCALE) {
                        // 1桁：和暦年 ／ 2桁以上：和暦がん号＋年
                        if (size == 1) {
                            __appendJpformatted(df, "yy", d);
                        } else {
                            __appendJpformatted(df, "GGGGyy", d);
                            isGango = true;
                        }
                    } else {
                        kigo = 'y';
                    }
                } else if (c == 'g') {
                    // ★ がん号
                    if (locale == null || locale == JP_LOCALE) {
                        String gf = StringUtils.leftPad("", size, 'G');
                        if (size >= 2) {
                            gf = "GGGG";    //「平成」
                        }
                        __appendJpformatted(df, gf, d);
                        isGango = true;
                    }
                } else if (c == 'y' || c == 'e') {
                   // ★ 年
                   if (locale == null && (isGango || c == 'e')) {
                       // 和暦年
                       String yf = StringUtils.leftPad("", size, 'y');
                       __appendJpformatted(df, yf, d);
                   } else {
                       kigo = 'y';
                   }
                } else if (c == 'm') {
                    // ★ 月 or 分
                    if (isMonth) {
                        kigo = 'M';
                        for (int i = 0; i < size; i++) {
                            ms.add(Integer.valueOf(bf + i));
                        }
                    } else {
                        kigo = 'm';
                    }
                }  else if (c == 'd') {
                    // ★ 日
                    kigo = 'd';
                }  else if (c == 'a' && size >= 3) {
                    // ★ 週
                    if (locale == null) {
                        // 曜日
                        String wf = StringUtils.leftPad("", size, 'E');
                        __appendJpformatted(df, wf, d);
                    } else {
                        kigo = 'E';
                    }
                }  else if (c == 'a' && hasAmPm && util.match("/^AM?/PM?/i"
                        , new String(chars, offset, chars.length - offset))) {
                    // ★ AM/PM
                    df.append("a");
                    size = util.toString().length();
                }  else if (c == 'h') {
                    // ★ 時
                    if (hasAmPm) {
                        kigo = 'h';
                    } else {
                        kigo = 'H';
                    }
                    isMonth = false;
                }  else if (c == 's') {
                    // ★ 秒
                    kigo = 's';
                    for (int i = 0; i < ms.size(); i++) {
                        int index = ms.get(i).intValue();
                        if (df.charAt(index) == 'M') {
                            df.replace(index, index + 1, "m");
                        }
                    }
                } else {
                    // その他アルファベット
                    __appendString(df, repeatStr);
                    offset += size;
                    continue;
                }

                // 日付記号を結合
                df.append(StringUtils.leftPad("", size, kigo).trim());
                isDate = true;
                if (c != 'm' && c != 'h') {
                    isMonth = true;
                    ms.clear();
                }
                offset += size - 1;

            } else if (c == '\'') {
                df.append("''");
            } else if (c == DQUOT_STR_SYMBOL) {
                __appendString(df, "\"");
            }  else {
                df.append(c);
            }

            offset++;
        }

        if (!isDate) {
            return null;
        }
        formatStr = df.toString();

        // 末尾記号を削除
        formatStr = formatStr.replaceAll(";@$", "");
        formatStr = formatStr.replaceAll(";$", "");

        // 曜日
        formatStr = formatStr.replaceAll("d{4,}", "EEEE");
        formatStr = formatStr.replaceAll("d{3}", "EEE");

        //log__.debug(formatString + "->" + formatStr);
        if (locale == null) {
            locale = DATE_DEF_LOCALE;
        }
        return new ExcelStyleDateFormatter(formatStr, locale);
    }

    /**
     * 書式文字列をエスケープ処理する
     * @param formatString 書式文字列
     * @param validateformat 検証用フォーマット
     * @return エスケープ処理後文字列
     */
    private String __escapeFormat(String formatString, StringBuilder validateformat) {

        StringBuilder f = new StringBuilder();
        char[] chars = formatString.toCharArray();
        int offset = 0;
        boolean isStrBegin = false;

        while (offset < chars.length) {
            char c = chars[offset];

            if (c == '\\' && !isStrBegin) {
                // 続く１字はそのまま表示
                offset++;
                c = chars[offset];
                if (c == '\"') {
                    f.append(DQUOT_STR_SYMBOL);
                } else {
                    f.append("\"" + c + "\"");
                }
            } else if (c == '\"') {
                isStrBegin = !isStrBegin;
                f.append(c);
            } else {
                if (!isStrBegin) {
                    validateformat.append(c);
                }
                f.append(c);
            }

            offset++;
        }

        //log__.debug(formatString + "->" + f.toString());
        return f.toString();
    }

    /**
     * 日付書式文字列が含まれているかどうか
     * @param formatString 書式文字列
     * @return 日付書式文字列ありの場合、true
     */
    private boolean __isDateFormatString(String formatString) {
        Perl5Util util = new Perl5Util();
        //「General」「E+」「E-」throw
        if (util.match("/(General)|(e[+-])/i", formatString)) {
            return false;
        }
        if (util.match("/[gerymdhs]+/i", formatString)
         || util.match("/a{3,}/i", formatString)
         || util.match("/AM?/PM?/i", formatString)) {
           return true;
        } else {
            return false;
        }
    }

    /**
     * AM/PM 表記を含むかどうか
     * @param formatString 書式文字列
     * @return AM/PM 表記ありの場合、true
     */
    private boolean __isAmPm(String formatString) {
        Perl5Util util = new Perl5Util();
        if (util.match("/AM?/PM?/i", formatString)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * ローケールを取得する
     * @param formatString 書式文字列
     * @return ローケル
     */
    private Locale __getLocale(String formatString) {
        Perl5Util util = new Perl5Util();
        // [$-999]
        if (util.match("/\\[\\$\\-[0-9A-F]+\\]/", formatString)) {
            // 日本語ローケル
            if (util.toString().equals("[$-411]")) {
                return JP_LOCALE;
            }
            return DATE_DEF_LOCALE;
        } else {
            return null;
        }
    }

    /**
     * [] に囲まれた定義表記を含むかどうか
     * @param formatString 書式文字列
     * @return [] に囲まれた定義表記の場合、true
     */
    private boolean __isDefElapsed(String formatString) {
        Perl5Util util = new Perl5Util();
        if (util.match("/^\\[\\$\\-[0-9A-F]+\\]$/", formatString)       //locale
         || util.match("/^\\[[<>=]+[0-9]+\\]$/", formatString)      //条件[>=100]
         || util.match("/^\\[DBNum[0-9]+\\]$/", formatString)       //漢数字、全角数字etc
         || util.match("/^\\[(BLACK)|(BLUE)|(CYAN)|(GREEN)|(MAGENTA)|(RED)|" // color
                 + "(WHITE)|(YELLOW)|(COLOR\\s*\\d)|(COLOR\\s*[0-5]\\d)\\]$/i", formatString)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 指定の終端文字が出現するまでの囲まれた文字列を取得する
     * @param  value 全体の文字列
     * @param offset 検索開始位置
     * @param end 終端文字
     * @return 文字数
     */
    private String __getElapsedStr(char[] value, int offset, char end) {
        offset++;
        String str = "";
        while (offset < value.length) {
            char c = value[offset];
            if (c == end) {
                break;
            }
            str += c;
            offset++;
        }
        return str;
    }

    /**
     * 連続する文字列を指定の文字列で変換して取得する
     * @param  value 全体の文字列
     * @param offset 検索開始位置
     * @param search 検索文字列
     * @return 文字数
     */
    private String __getRepeatStr(char[] value, int offset, char search) {
        offset++;
        String repeat = String.valueOf(search);
        while (offset < value.length) {
            char c = value[offset];
            if (c != search) {
                break;
            }
            repeat += String.valueOf(search);
            offset++;
        }
        return repeat;
    }

    /**
     * 日本語日付書式を追加する
     * @param df 結合する文字格納変数
     * @param formatString 書式文字列
     * @param d 日付値
     */
    private void __appendJpformatted(StringBuilder df, String formatString, Date d) {
        DateFormat format = new SimpleDateFormat(formatString, JP_LOCALE);
        __appendString(df, format.format(d));
    }

    /**
     * 書式内の文字列を結合する
     * @param df 結合する文字格納変数
     * @param s 文字列
     */
    private void __appendString(StringBuilder df, String s) {
        // 「'」エスケープ
        if (s.indexOf("'") >= 0) {
            s = s.replaceAll("'", "''");
        }
        Perl5Util util = new Perl5Util();
        if (util.match("/'+$/", df.toString()) && util.toString().length() % 2 != 0) {
            df.deleteCharAt(df.length() - 1);
            df.append(s + "'");
        } else {
            df.append("'" + s + "'");
        }
    }
}
