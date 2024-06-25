package jp.groupsession.v2.man.man460kn;

import jp.groupsession.v2.man.man460.Man460Form;

/**
 * <br>[機  能] サイボウズLiveデータ移行 イベントインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man460knForm extends Man460Form {

    /** 取込みファイル名 */
    private String man460knFileName__ = null;
    /** 取込み予定グループ情報 */
    private int man460knImpCount__ = 0;

    /**
     * <p>man460knFileName を取得します。
     * @return man460knFileName
     */
    public String getMan460knFileName() {
        return man460knFileName__;
    }
    /**
     * <p>man460knFileName をセットします。
     * @param man460knFileName man460knFileName
     */
    public void setMan460knFileName(String man460knFileName) {
        man460knFileName__ = man460knFileName;
    }
    /**
     * <p>man460knImpCount を取得します。
     * @return man460knImpCount
     */
    public int getMan460knImpCount() {
        return man460knImpCount__;
    }
    /**
     * <p>man460knImpCount をセットします。
     * @param man460knImpCount man460knImpCount
     */
    public void setMan460knImpCount(int man460knImpCount) {
        man460knImpCount__ = man460knImpCount;
    }

}