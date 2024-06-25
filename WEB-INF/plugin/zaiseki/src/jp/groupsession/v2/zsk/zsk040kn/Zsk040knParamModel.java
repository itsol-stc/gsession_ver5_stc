package jp.groupsession.v2.zsk.zsk040kn;

import jp.groupsession.v2.zsk.zsk040.Zsk040ParamModel;

/**
 * <br>[機  能] 在席管理 座席表登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk040knParamModel extends Zsk040ParamModel {
    /** 添付ファイルID */
    private String zsk040knTmpFileId__ = null;

    /**
     * <p>zsk040knTmpFileId を取得します。
     * @return zsk040knTmpFileId
     */
    public String getZsk040knTmpFileId() {
        return zsk040knTmpFileId__;
    }

    /**
     * <p>zsk040knTmpFileId をセットします。
     * @param zsk040knTmpFileId zsk040knTmpFileId
     */
    public void setZsk040knTmpFileId(String zsk040knTmpFileId) {
        zsk040knTmpFileId__ = zsk040knTmpFileId;
    }

}
