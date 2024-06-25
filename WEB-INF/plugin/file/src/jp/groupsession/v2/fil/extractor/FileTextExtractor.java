package jp.groupsession.v2.fil.extractor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.POITextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.hssf.extractor.EventBasedExcelExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.common.POIFSConstants;
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem;
import org.apache.poi.xssf.extractor.XSSFEventBasedExcelExtractor;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;

/**
 * <br>[機  能] ファイルからテキスト文字列を抽出するクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileTextExtractor {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FileTextExtractor.class);

    /** Office file */
    private final int type_Office = 1;
    /** PDF file */
    private final int type_Pdf = 2;

    /** テキスト抽出コールバック関数 */
    private IProcessExtractorCallback callback__;
    /** 最大文字数 */
    private int maxLength__;

    /**
     * 読み込んだ抽出文字を、最大文字数に合わせてコールバックするためのクラスをセットする
     * @param callback セットする callback
     */
    public void setCallback(IProcessExtractorCallback callback) {
        callback__ = callback;
    }

    /**
     * 読込み最大文字数をセットする
     * @param maxLength セットする maxLength
     */
    public void setMaxLength(int maxLength) {
        maxLength__ = maxLength;
    }

    /**
     * ファイルのテキストを読み込む
     * @param filePath 対象ファイルパス
     * @throws Exception 読込み例外
     */
    public void read(String filePath) throws Exception {
        read(new File(filePath));
    }

    /**
     * ファイルのテキストを読み込む
     * @param file 対象ファイルバイナリ
     * @throws Exception 読込み例外
     */
    public void read(File file) throws Exception {

        log__.debug("読込ファイルパス->" + file.getPath());

        if (!file.exists()) {
            log__.warn("存在しないファイルです。");
            callback__.onError();
            return;
        }
        if (!file.isFile()) {
            log__.warn("ファイルではありません。");
            callback__.onError();
            return;
        }

        int type = __getFileType(file);
        if (type == 0) {
            log__.warn("テキスト読込み対象ファイルではありません。");
            callback__.onError();
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);

            // 共有ロック
            // ※ 例外が発生すると、ファイルがロックされ削除できない場合があるため、ロックしておく
            FileChannel fc = fis.getChannel();
            FileLock lock = fc.tryLock(0, fc.size(), true);

            try {
                // 他プロセスロック中
                if (lock == null) {
                    throw new RuntimeException("other process locked.");
                }

                // ファイル内容読込み
                switch (type) {
                    case type_Office:
                        boolean isOOXML = __isOOXML(file);
                        // OOXML ファイルで暗号化されているかどうか検証
                        if (!isOOXML) {
                            if (__isEncryptionOOXML(file)) {
                                log__.info("OOXMLファイル暗号化");
                                callback__.onEncryption();
                                return;
                            }
                        }
                        // 読込み
                        __readOffice(file, true);
                        break;

                    case type_Pdf:
                        __readPdf(fis);
                        break;
                    default:
                }
            } finally {
                try {
                    if (lock != null) {
                        lock.release();
                    }
                } catch (IOException e) {
                }
            }
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
     * ファイル拡張子から読込タイプを取得する
     * @param file 対象ファイル
     * @return 対象ファイルフラグ
     */
    private int __getFileType(File file) {
        String extension = StringUtil.getExtension(file.getName()).toLowerCase();
        // Office
        if (extension.startsWith(".doc")
         || extension.startsWith(".xls")
         || extension.startsWith(".ppt")) {
            return type_Office;
        }
        // PDF
        if (extension.equals(".pdf")) {
           return type_Pdf;
        }
        return 0;
    }

    /**
     * Office ファイルのテキストを読み込む
     * @param inp 対象ファイルストリーム
     * @param isOOXML Office Open XML ファイルの場合、true
     * @throws Exception 実行例外
     */
    private void __readOffice(File file, boolean isOOXML) throws Exception {

        POITextExtractor extractor = null;

        try {
            FileInputStream inp = new FileInputStream(file);
            // ※ Excel は、API イベント使用。
            ExtractorFactory.setAllThreadsPreferEventExtractors(true);
            // テキスト抽出クラス作成
            extractor = ExtractorFactory.createExtractor(inp);

            // テキスト取得
            if (extractor instanceof EventBasedExcelExtractor) {
                // xls  ※ API イベントにて独自に読込む
                // ※ 共有ロックの掛かっている stream を使用したいので、非推奨だが getFileSystem() を使用する
                ExtractorEventHssf.read(file,
                        callback__, maxLength__);

            } else if (extractor instanceof WordExtractor) {
                // doc
                __callbackOfficeDocument((WordExtractor) extractor);

            } else if (extractor instanceof XSSFEventBasedExcelExtractor) {
                // xlsx ※ API イベントにて独自に読込む
                ExtractorEventXssf.read(
                        ((XSSFEventBasedExcelExtractor) extractor).getPackage(),
                        callback__, maxLength__);
            } else {
                __callbackResult(extractor.getText(), null);
            }

        } catch (EncryptedDocumentException e) {
            // OLE2 で暗号化されている場合は、この例外が発生する
            log__.info("OLE2ファイル暗号化");
            callback__.onEncryption(e);
        } catch (Exception e) {
            throw e;
        } finally {
            if (extractor != null) {
                extractor.close();
            }
        }
    }

    /**
     * Office Open XML ファイルかどうかを取得する
     * @param file 対象ファイル
     * @return Office Open XML ファイルの場合、true
     * @throws Exception 例外
     */
    private boolean __isOOXML(File file) throws Exception {

        boolean isOOXML = false;
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            byte[] buf = new byte[4];
            fis.read(buf);
            if (Arrays.equals(buf, POIFSConstants.OOXML_FILE_HEADER)) {
                isOOXML = true;
            }
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
        return isOOXML;
    }

    /**
     * Office Open XML ファイルが暗号化されているかどうかを取得する
     * @param file 対象ファイルバイナリ
     * @return 暗号化されている場合、true
     * @throws IOException IO例外
     */
    private boolean __isEncryptionOOXML(File file) throws IOException {

        boolean hasEncInf = false;
        NPOIFSFileSystem npoifs = null;
        try {
            npoifs = new NPOIFSFileSystem(file);
            hasEncInf = npoifs.getRoot().hasEntry("EncryptionInfo");

        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (npoifs != null) {
                    npoifs.close();
                }
            } catch (IOException e) {
            }
        }

        return hasEncInf;
    }

    /**
     * Office Document のテキストを読込み、文字列をコールバック処理する
     * @param extractor テキスト抽出クラス
     * @throws Exception 例外
     */
    private void __callbackOfficeDocument(WordExtractor extractor) throws Exception {

        // ※ デフォルトでリンク文字が取れる仕様になっているので、回避する
        StringBuilder text = new StringBuilder();
        String[] textlist = extractor.getParagraphText();

        for (String rawText : textlist) {
            String line = WordExtractor.stripFields(rawText);
            if (line.length() == 0) { continue; }

            text.append(line);
        }
        __callbackResult(text.toString(), null);
    }

    /**
     * PDF ファイルのテキストを読み込む
     * @param fis 対象ファイルストリーム
     * @throws Exception 例外
     */
    private void __readPdf(FileInputStream fis) throws Exception {

        PDDocument doc = null;

        try {
            doc = PDDocument.load(fis, true);

            // パスワードがかかっているので、読めない
            if (doc.isEncrypted()) {
                log__.info("PDFファイル暗号化");
                callback__.onEncryption();
                return;
            }

            // ファイル読込み
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(doc);

            // テキストコールバック
            __callbackResult(text, null);

        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (doc != null) {
                    doc.close();
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * 読込みテキスト文字列をコールバック処理する
     * @param text 読込み文字列
     * @param biko 備考
     * @throws Exception 例外
     */
    private void __callbackResult(String text, String biko) throws Exception {

        if (StringUtil.isNullZeroString(text) || maxLength__ <= 0) {
            callback__.onResult(text, null);

        } else {
            // 最大文字数ごとにコールバックする
            String alltext = text;

            while (alltext.length() > 0) {
                String onetext = alltext;

                if (onetext.length() > maxLength__) {
                    // 最大文字数超過
                    String maxtext = onetext.substring(0, maxLength__);

                    // ストップワードを検索する
                    int index = __searchStopWords(maxtext);
                    if (index < 0) {
                        index = maxLength__ - 1;
                    }

                    alltext = onetext.substring(index + 1);
                    onetext = onetext.substring(0, index + 1);

                } else {
                    alltext = "";
                }

                callback__.onResult(onetext, biko);
            }
        }
    }

    /**
     * 末尾からストップワード位置を検索する
     * @param pSource 検索する文字列
     * @return ストップワードが見つかった位置
     */
    private int __searchStopWords(String pSource) {

        // パラメーターの文字列を末尾から1文字ずつ調べる
        for (int i = pSource.length() - 1; i >= 0; i--) {
            if (__isStopWords(pSource, i)) {
                return i;
            }
        }

        return -1;
     }

    /**
     * ストップワードかどうか判別する
     * @param pSource 検索する文字列
     * @param index 検索位置
     * @return ストップワードの場合、true
     */
    private boolean __isStopWords(String pSource, int index) {

        //文字列から1文字取り出す
        Character cSrc = new Character(pSource.substring(index, index + 1).charAt(0));

        // ストップワードか調べる
        if (cSrc.equals('。')
         || cSrc.equals('、')
         || cSrc.equals(' ')
         || cSrc.equals('　')
         || cSrc.equals('\t')) {
            return true;
        }

        // アルファベット + 「.(ドット) or ,(カンマ)」+ (空白 or 改行) の場合、英語ストップワードと判断する
        if (__isStopWordsAlpha(pSource, index, cSrc)) {
            return true;
        }

        return false;
     }

    /**
     * ストップワードかどうか判別する
     * @param pSource 検索する文字列
     * @param index 検索位置
     * @param cSrc 検証文字列
     * @return ストップワードの場合、true
     */
    private boolean __isStopWordsAlpha(String pSource, int index, Character cSrc) {

        // アルファベット + 「.(ドット) or ,(カンマ)」+ (空白 or 改行) の場合、英語ストップワードと判断する
        if (cSrc.equals('.')
         || cSrc.equals(',')) {
            // 前の文字がアルファベットか？
            if (index > 1) {
                Character bfChar =
                        new Character(pSource.substring(index - 1, index).charAt(0));
                if (!ValidateUtil.isAlpha(bfChar.toString())) {
                    return false;
                }
            } else {
                return false;
            }

            // 後ろの文字がスペース、または改行か？
            if (index < pSource.length() - 1) {
                Character afChar =
                        new Character(pSource.substring(index + 1, index + 2).charAt(0));
                if (!ValidateUtil.isSpaceOrKaigyou(afChar.toString())) {
                    return false;
                }
            }
            return true;

        } else {
            return false;
        }
     }
}