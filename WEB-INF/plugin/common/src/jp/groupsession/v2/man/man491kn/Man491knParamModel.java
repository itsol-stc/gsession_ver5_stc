package jp.groupsession.v2.man.man491kn;

import jp.groupsession.v2.man.man491.Man491ParamModel;
/**
 *
 * <br>[機  能] マイカレンダーインポート確認画面 param
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man491knParamModel extends Man491ParamModel {
    /** 取込みファイル名 */
    private String man490knFileName__ = null;
    /** インポート行数*/
    private int man491knImpCount__;

    /**
     * <p>man490knFileName を取得します。
     * @return man490knFileName
     */
    public String getMan490knFileName() {
        return man490knFileName__;
    }
    /**
     * <p>man490knFileName をセットします。
     * @param man490knFileName man490knFileName
     */
    public void setMan490knFileName(String man490knFileName) {
        man490knFileName__ = man490knFileName;
    }

    /**
     * <p>man491knImpCount を取得します。
     * @return man491knImpCount
     * @see jp.groupsession.v2.man.man491kn.Man491knParamModel#man491knImpCount__
     */
    public int getMan491knImpCount() {
        return man491knImpCount__;
    }

    /**
     * <p>man491knImpCount をセットします。
     * @param man491knImpCount man491knImpCount
     * @see jp.groupsession.v2.man.man491kn.Man491knParamModel#man491knImpCount__
     */
    public void setMan491knImpCount(int man491knImpCount) {
        man491knImpCount__ = man491knImpCount;
    }

}
