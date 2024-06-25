package jp.groupsession.v2.fil.extractor;


/**
 * <br>[機  能] ファイルテキスト抽出経過時に実行されるコールバッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface IProcessExtractorCallback {

    /**
     * <p>ファイルが正常に読み込まれた場合に、その文字列を返します。
     * @param text 読み込まれた文字列
     * @param biko 備考（excel の場合、シート名）
     * @throws Exception 実行例外
     */
    public void onResult(String text, String biko) throws Exception;

    /**
     * <p>ファイルが暗号化されている場合に発生します。
     * @throws Exception 実行例外
     */
    public void onEncryption() throws Exception;

    /**
     * <p>ファイルが暗号化されている場合に発生します。
     * @param e 発生例外
     * @throws Exception 実行例外
     */
    public void onEncryption(Exception e) throws Exception;

    /**
     * <p>ファイルが読込めなかった（読込み対象外）場合に発生します。
     * @throws Exception 実行例外
     */
    public void onError() throws Exception;

}
