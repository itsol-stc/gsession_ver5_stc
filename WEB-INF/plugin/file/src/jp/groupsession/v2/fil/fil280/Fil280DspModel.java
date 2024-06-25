package jp.groupsession.v2.fil.fil280;

import jp.groupsession.v2.fil.model.FileCabinetModel;

/**
 * <br>[機  能] 個人キャビネット表示用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Fil280DspModel extends FileCabinetModel {

    /** 個人キャビネット登録者名 */
    private String addUsr__;


    /**
     * <p>addUsrを取得します。
     * @return addUsr
     * */
    public String getAddUsr() {
        return addUsr__;
    }
    /**
     * <p> addUsrをセットします。
     * @param addUsr addUsr
     * */
    public void setAddUsr(String addUsr) {
        addUsr__ = addUsr;
    }
}
