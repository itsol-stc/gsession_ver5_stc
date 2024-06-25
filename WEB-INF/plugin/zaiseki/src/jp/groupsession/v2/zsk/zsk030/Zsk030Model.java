package jp.groupsession.v2.zsk.zsk030;

import jp.groupsession.v2.zsk.model.ZaiInfoModel;

/**
 * <br>[機  能] 在席管理 座席表設定画面 表示用情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Zsk030Model extends ZaiInfoModel {

    /** 最終更新日 */
    private String lastUpdateDate__;

    /**
     * <p>lastUpdateDate を取得します。
     * @return lastUpdateDate
     */
    public String getLastUpdateDate() {
        return lastUpdateDate__;
    }

    /**
     * <p>lastUpdateDate をセットします。
     * @param lastUpdateDate lastUpdateDate
     */
    public void setLastUpdateDate(String lastUpdateDate) {
        lastUpdateDate__ = lastUpdateDate;
    }

}
