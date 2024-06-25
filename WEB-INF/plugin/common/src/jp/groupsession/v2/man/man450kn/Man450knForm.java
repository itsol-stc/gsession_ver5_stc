package jp.groupsession.v2.man.man450kn;

import jp.groupsession.v2.man.man450.Man450Form;

/**
 * <br>[機  能] メイン 管理者設定 役職インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man450knForm extends Man450Form {

    /** 取込みファイル名 */
    private String man450knFileName__ = null;
    /** 取込み予定グループ情報 */
    private int man450knImpCount__ = 0;

    /**
     * <p>man450knFileName を取得します。
     * @return man450knFileName
     */
    public String getMan450knFileName() {
        return man450knFileName__;
    }
    /**
     * <p>man450knFileName をセットします。
     * @param man450knFileName man450knFileName
     */
    public void setMan450knFileName(String man450knFileName) {
        man450knFileName__ = man450knFileName;
    }
    /**
     * <p>man450knImpCount を取得します。
     * @return man450knImpCount
     */
    public int getMan450knImpCount() {
        return man450knImpCount__;
    }
    /**
     * <p>man450knImpCount をセットします。
     * @param man450knImpCount man450knImpCount
     */
    public void setMan450knImpCount(int man450knImpCount) {
        man450knImpCount__ = man450knImpCount;
    }

}