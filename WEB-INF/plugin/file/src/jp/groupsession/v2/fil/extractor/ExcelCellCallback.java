package jp.groupsession.v2.fil.extractor;

import jp.co.sjts.util.StringUtil;

/**
 * <br>[機  能] Excel セル コールバッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ExcelCellCallback {

    /** 行結合文字列 */
    private final String line_join = "\n";
    /** 列結合文字列 */
    private final String cell_join = "\t";

    /** テキスト抽出コールバック関数 */
    private IProcessExtractorCallback extractorCallback__;
    /** 最大文字数 */
    private int maxLength__;

    /** 読込み文字列 */
    private StringBuilder text__;
    /** 列結合文字列 */
    private String cellJoin__ = "";

    /**シート名 */
    private String sheetName__;
    /** 1つ前の行インデックス */
    private int beforeRowIndex__;

    /**
     * Default Constructor
     */
    public ExcelCellCallback() {
        text__ = new StringBuilder();
    }

    /**
     * @param extractorCallback セットする extractorCallback
     */
    public void setExtractorCallback(IProcessExtractorCallback extractorCallback) {
        extractorCallback__ = extractorCallback;
    }
    /**
     * @param maxLength セットする maxLength
     */
    public void setMaxLength(int maxLength) {
        maxLength__ = maxLength;
    }

    /**
     * シート読込開始
     * @param name シート名
     */
    public void startSheet(String name) {
        sheetName__ = name;
        beforeRowIndex__ = -1;
    }

     /**
     * シート読込終了
     * @throws Exception 例外
     */
    public void endSheet() throws Exception {
        __callbackResult();
    }

    /**
     * セルテキストの追加
     * @param rowIndex 行インデックス
     * @param value セルテキスト
     * @throws Exception 例外
     */
    public void appendText(int rowIndex, String value) throws Exception {

        if (!StringUtil.isNullZeroStringSpace(value)) {
            // 行結合
            String line = "";
            if (rowIndex != beforeRowIndex__) {
                if (text__.length() > 0) {
                    line = line_join;
                }
                cellJoin__ = "";
            }

            // 桁数を確認
            if (maxLength__ > 0) {
                int length = text__.length() + line.length()
                                + cellJoin__.length() + value.length();

                // 最大文字数を超えている
                if (length > maxLength__) {
                    __callbackResult();
               }
            }

            // セーブ
            text__.append(line + cellJoin__ + value);
            cellJoin__ = cell_join;
        }

        beforeRowIndex__ = rowIndex;
    }

    /**
     * セルテキストの結合文字列をコールバック処理する
     * @throws Exception 例外
     */
    private void __callbackResult() throws Exception {

        // 結果を戻す
        extractorCallback__.onResult(text__.toString(), sheetName__);

        // クリア
        text__.setLength(0);
        cellJoin__ = "";
    }
}
