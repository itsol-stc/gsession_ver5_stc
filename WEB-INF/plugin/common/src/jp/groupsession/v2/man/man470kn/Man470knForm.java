package jp.groupsession.v2.man.man470kn;

import jp.groupsession.v2.man.man470.Man470Form;

/**
 * <br>[機  能] サイボウズLiveデータ移行 掲示板インポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man470knForm extends Man470Form {

    /** 取込みファイル名 */
    private String man470knFileName__ = null;
    /** 取込み予定グループ情報 */
    private int man470knImpCount__ = 0;

    /**
     * <p>man470knFileName を取得します。
     * @return man470knFileName
     */
    public String getMan470knFileName() {
        return man470knFileName__;
    }
    /**
     * <p>man470knFileName をセットします。
     * @param man470knFileName man470knFileName
     */
    public void setMan470knFileName(String man470knFileName) {
        man470knFileName__ = man470knFileName;
    }
    /**
     * <p>man470knImpCount を取得します。
     * @return man470knImpCount
     */
    public int getMan470knImpCount() {
        return man470knImpCount__;
    }
    /**
     * <p>man470knImpCount をセットします。
     * @param man470knImpCount man470knImpCount
     */
    public void setMan470knImpCount(int man470knImpCount) {
        man470knImpCount__ = man470knImpCount;
    }

}