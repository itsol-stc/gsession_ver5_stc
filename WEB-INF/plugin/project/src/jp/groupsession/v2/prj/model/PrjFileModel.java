package jp.groupsession.v2.prj.model;

import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;

/**
 * <br>[機  能] オブジェクトファイル情報を格納するのModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjFileModel extends Cmn110FileModel {

    /** バイナリーSID */
    private Long binSid__ = new Long(0);

    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }

    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }

}
