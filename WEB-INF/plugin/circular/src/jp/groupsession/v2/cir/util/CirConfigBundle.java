package jp.groupsession.v2.cir.util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;

/**
 * <br>[機  能] WEBメール設定ファイル(circular.conf)の読み込みを行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CirConfigBundle {

    /** 設定ファイル名 */
    private static final String CONF_FILENAME__ = "circular.conf";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CirConfigBundle.class);

    /** 設定値を格納するMap */
    private static Map<String, String> confMap__ = null;

    /** 設定ファイルエラー */
    private static boolean fileErr__ = false;



    static {
        confMap__ = Collections.synchronizedMap(new HashMap<String, String>());
    }

    /**
     *<br> [機 能] コンストラクタ
     *<br> [解 説]
     *<br> [備 考]
     */
    private CirConfigBundle() {
    }

    /**
     *<br> [機 能] 設定ファイルの読み込みを行う
     *<br> [解 説] WEB-INF/conf以下の設定ファイル(*.conf)を読み込む
     *<br> [備 考]
     *
     * @param appRoot ルートパス
     * @throws IOException 設定ファイルの読み込みに失敗
     */
    public static synchronized void readConfig(String appRoot) throws IOException {

        String filePath = IOTools.setEndPathChar(appRoot);
        filePath += "/WEB-INF/conf/";
        filePath += CONF_FILENAME__;
        fileErr__ = true;
        //設定ファイルの読み込みを行う
        StringTokenizer lines = new StringTokenizer(
                                    IOTools.readText(filePath, Encoding.UTF_8),
                                    "\n");

        while (lines.hasMoreTokens()) {
            String line = lines.nextToken();
            line = StringUtil.toDeleteReturnCode(line);
            if (line.startsWith("#") || line.indexOf("=") < 0) {
                //先頭に"#"がついた行はコメントとして扱う
                continue;
            }

            int index = line.indexOf("=");
            String key = line.substring(0, index);
            String value = line.substring(index + 1).trim();
            if (value.length() == 0) {
                value = null;
            }
            log__.debug("<==設定ファイル内容==>");
            log__.debug("key:value==>" + key + ":" + value);
            confMap__.put(key, value);
        }
        fileErr__ = false;

    }

    /**
     * <br>[機  能] 設定値を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param key キー
     * @return 設定値
     */
    public static synchronized String getValue(String key) {
        return confMap__.get(key);
    }
    /**
     *
     * <br>[機  能] ファイル読み込み時のエラー有無を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return ファイル読み込み時のエラー有無 true:エラーあり
     */
    public static boolean isFileErr() {
        return fileErr__;
    }

}
