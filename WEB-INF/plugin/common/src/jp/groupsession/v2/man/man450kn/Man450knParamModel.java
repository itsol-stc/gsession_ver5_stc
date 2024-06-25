package jp.groupsession.v2.man.man450kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.man.man450.Man450ParamModel;

/**
 * <br>[機  能] メイン 管理者設定 役職インポート確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man450knParamModel extends Man450ParamModel {
    /** 取込みファイル名 */
    private String man450knFileName__ = null;
    /** 取込み予定グループ情報 */
    private List<CmnPositionModel> man450knImpList__ = null;

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
     * <p>man450knImpList を取得します。
     * @return man450knImpList
     */
    public List<CmnPositionModel> getMan450knImpList() {
        return man450knImpList__;
    }
    /**
     * <p>man450knImpList をセットします。
     * @param man450knImpList man450knImpList
     */
    public void setMan450knImpList(List<CmnPositionModel> man450knImpList) {
        man450knImpList__ = man450knImpList;
    }
}