package jp.groupsession.v2.man.man470kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.man.man470.Man470ParamModel;

/**
 * <br>[機  能] サイボウズLiveデータ移行 掲示板インポート確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man470knParamModel extends Man470ParamModel {
    /** 取込みファイル名 */
    private String man470knFileName__ = null;
    /** 取込み予定グループ情報 */
    private List<CmnPositionModel> man470knImpList__ = null;

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
     * <p>man470knImpList を取得します。
     * @return man470knImpList
     */
    public List<CmnPositionModel> getMan470knImpList() {
        return man470knImpList__;
    }
    /**
     * <p>man470knImpList をセットします。
     * @param man470knImpList man470knImpList
     */
    public void setMan470knImpList(List<CmnPositionModel> man470knImpList) {
        man470knImpList__ = man470knImpList;
    }
}