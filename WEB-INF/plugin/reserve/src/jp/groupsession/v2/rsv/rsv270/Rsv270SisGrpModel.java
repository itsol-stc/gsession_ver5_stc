package jp.groupsession.v2.rsv.rsv270;

import jp.groupsession.v2.rsv.model.RsvSisDataModel;

/**
 * <br>[機  能] RsvSisDataModelの拡張Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rsv270SisGrpModel extends RsvSisDataModel {

    /** 施設グループID */
    private String rsgId__;
    /** 施設グループ名 */
    private String rsgName__;

    /**
     * <p>rsgId を取得します。
     * @return rsgId
     */
    public String getRsgId() {
        return rsgId__;
    }
    /**
     * <p>rsgId をセットします。
     * @param rsgId rsgId
     */
    public void setRsgId(String rsgId) {
        rsgId__ = rsgId;
    }
    /**
     * <p>rsgName を取得します。
     * @return rsgName
     */
    public String getRsgName() {
        return rsgName__;
    }
    /**
     * <p>rsgName をセットします。
     * @param rsgName rsgName
     */
    public void setRsgName(String rsgName) {
        rsgName__ = rsgName;
    }


}
