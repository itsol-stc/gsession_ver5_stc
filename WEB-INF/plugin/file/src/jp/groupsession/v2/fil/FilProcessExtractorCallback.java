package jp.groupsession.v2.fil;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.fil.dao.FileFileTextDao;
import jp.groupsession.v2.fil.extractor.IProcessExtractorCallback;
import jp.groupsession.v2.fil.model.FileFileTextModel;

/**
 * <br>[機  能] ファイルテキスト抽出経過時に実行されるコールバッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilProcessExtractorCallback implements IProcessExtractorCallback {

    /** DBコネクション */
    private Connection con__;
    /** ファイルテキスト情報MODEL */
    private FileFileTextDao textDao__;
    /** ファイルテキスト情報MODEL */
    private FileFileTextModel textMdl__;

    /** 連番 */
    private int secNo__ = 0;

    /**
     * <p>コンストラクタ
     * @param con DBコネクション
     * @param textMdl ファイルテキスト情報MODEL
     */
    public FilProcessExtractorCallback(Connection con, FileFileTextModel textMdl) {
        con__ = con;
        textDao__ = new FileFileTextDao(con__);
        textMdl__ = textMdl;
        secNo__ = 0;
    }

    /**
     * <p>ファイルが正常に読み込まれた場合に、その文字列を返します。
     * @param text 読み込まれた文字列
     * @param biko 備考（excel の場合、シート名）
     * @throws Exception 実行例外
     */
    public void onResult(String text, String biko) throws Exception {

        // 読込み文字列追加
        secNo__++;
        textMdl__.setFftSecNo(secNo__);
        textMdl__.setFftText(text);
        textMdl__.setFftBiko(biko);
        textMdl__.setFftReadKbn(GSConstFile.READ_KBN_SUCCESSFUL);
        textDao__.insert(textMdl__);
    }

    /**
     * <p>ファイルが暗号化されている場合に発生します。
     * @throws Exception 実行例外
     */
    public void onEncryption() throws Exception {
        __insertErrorFileText(null, GSConstFile.READ_KBN_ERROR_PASSWORD);
    }

    /**
     * <p>ファイルが暗号化されている場合に発生します。
     * @param e 発生例外
     * @throws Exception 実行例外
     */
    public void onEncryption(Exception e) throws Exception {
        __insertErrorFileText(e.toString(), GSConstFile.READ_KBN_ERROR_PASSWORD);
    }

    /**
     * <p>ファイルが読込めなかった場合に発生します。
     * @throws Exception 例外
     */
    public void onError() throws Exception {
        __insertErrorFileText(null, GSConstFile.READ_KBN_ERROR);
    }

    /**
     * ファイル読込みでエラーが発生した場合に、エラー情報を登録します。
     * @param e エラー内容
     * @throws SQLException SQL実行例外
     */
    public void onError(Exception e) throws SQLException {
        // ロールバック
        JDBCUtil.rollback(con__);

        String eStr = e.toString();
        if (eStr.length() > 3000) {
            eStr = eStr.substring(0, 3000);
        }

        textMdl__.setFftSecNo(0);
        textMdl__.setFftText(null);
        textMdl__.setFftBiko(eStr);
        textMdl__.setFftReadKbn(GSConstFile.READ_KBN_ERROR);
        textDao__.insert(textMdl__);

        // コミット
        con__.commit();
    }

    /**
     * ファイルテキスト情報エラーデータ追加
     * @param error 発生例外
     * @param readKbn 読込区分
     * @throws SQLException SQL実行例外
     */
    private void __insertErrorFileText(String error, int readKbn) throws SQLException {

        textMdl__.setFftSecNo(0);
        textMdl__.setFftText(null);
        textMdl__.setFftBiko(error);
        textMdl__.setFftReadKbn(readKbn);
        textDao__.insert(textMdl__);

        // コミット
        con__.commit();
    }
}
