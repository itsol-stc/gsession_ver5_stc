package jp.groupsession.v2.man.man491kn;

import jp.groupsession.v2.man.man491.Man491Form;
/**
 *
 * <br>[機  能] マイカレンダーインポート確認画面 Form
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man491knForm extends Man491Form {
    /** 取込みファイル名 */
    private String man491knFileName__ = null;
    /** インポート行数*/
    private int man491knImpCount__;

    /**
     * <p>man491knFileName を取得します。
     * @return man491knFileName
     */
    public String getMan491knFileName() {
        return man491knFileName__;
    }
    /**
     * <p>man491knFileName をセットします。
     * @param man491knFileName man491knFileName
     */
    public void setMan491knFileName(String man491knFileName) {
        man491knFileName__ = man491knFileName;
    }

    /**
     * <p>man491knImpCount を取得します。
     * @return man491knImpCount
     * @see jp.groupsession.v2.man.man491kn.Man491knForm#man491knImpCount__
     */
    public int getMan491knImpCount() {
        return man491knImpCount__;
    }

    /**
     * <p>man491knImpCount をセットします。
     * @param man491knImpCount man491knImpCount
     * @see jp.groupsession.v2.man.man491kn.Man491knForm#man491knImpCount__
     */
    public void setMan491knImpCount(int man491knImpCount) {
        man491knImpCount__ = man491knImpCount;
    }

}
