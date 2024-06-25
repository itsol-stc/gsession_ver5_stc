package jp.groupsession.v2.zsk.zsk050kn;

import jp.groupsession.v2.zsk.zsk050.Zsk050ParamModel;

/**
 * <br>[機  能] 在席管理 座席表編集確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk050knParamModel extends Zsk050ParamModel {
    /** 添付ファイルID */
    private String zsk050knTmpFileId__ = null;

    /**
     * <p>zsk050knTmpFileId を取得します。
     * @return zsk050knTmpFileId
     */
    public String getZsk050knTmpFileId() {
        return zsk050knTmpFileId__;
    }

    /**
     * <p>zsk050knTmpFileId をセットします。
     * @param zsk050knTmpFileId zsk050knTmpFileId
     */
    public void setZsk050knTmpFileId(String zsk050knTmpFileId) {
        zsk050knTmpFileId__ = zsk050knTmpFileId;
    }

}
