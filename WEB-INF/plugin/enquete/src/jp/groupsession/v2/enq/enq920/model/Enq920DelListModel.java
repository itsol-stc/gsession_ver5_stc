package jp.groupsession.v2.enq.enq920.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 管理者設定 アンケート種類設定画面の削除情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Enq920DelListModel extends AbstractModel {

    /** アンケート種類SID */
    private long etpSid__ = 0;

    /**
     * <p>etpSid を取得します。
     * @return etpSid
     */
    public long getEtpSid() {
        return etpSid__;
    }

    /**
     * <p>etpSid をセットします。
     * @param etpSid etpSid
     */
    public void setEtpSid(long etpSid) {
        etpSid__ = etpSid;
    }
}

