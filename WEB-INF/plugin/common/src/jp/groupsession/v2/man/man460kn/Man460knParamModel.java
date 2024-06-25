package jp.groupsession.v2.man.man460kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.man.man460.Man460ParamModel;

/**
 * <br>[機  能] サイボウズLiveデータ移行 イベントインポート確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man460knParamModel extends Man460ParamModel {
    /** 取込みファイル名 */
    private String man460knFileName__ = null;
    /** 取込み予定グループ情報 */
    private List<CmnPositionModel> man460knImpList__ = null;

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
     * <p>man460knImpList を取得します。
     * @return man460knImpList
     */
    public List<CmnPositionModel> getMan460knImpList() {
        return man460knImpList__;
    }
    /**
     * <p>man460knImpList をセットします。
     * @param man460knImpList man460knImpList
     */
    public void setMan460knImpList(List<CmnPositionModel> man460knImpList) {
        man460knImpList__ = man460knImpList;
    }
}