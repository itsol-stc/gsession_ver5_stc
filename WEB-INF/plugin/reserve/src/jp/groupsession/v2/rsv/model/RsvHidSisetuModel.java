package jp.groupsession.v2.rsv.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 選択済施設(非表示) 施設 を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvHidSisetuModel extends AbstractModel {

    /** 施設SID */
    private int rsdSid__;
    /** 施設名称 */
    private String rsdName__ = null;
    /** 一括登録用キー */
    private String rsvIkkatuTorokuKey__ = null;

    /**
     * <p>rsdSid__ を取得します。
     * @return rsdSid
     */
    public int getRsdSid() {
        return rsdSid__;
    }
    /**
     * <p>rsdSid__ をセットします。
     * @param rsdSid rsdSid__
     */
    public void setRsdSid(int rsdSid) {
        rsdSid__ = rsdSid;
    }
    /**
     * <p>rsdName__ を取得します。
     * @return rsdName
     */
    public String getRsdName() {
        return rsdName__;
    }
    /**
     * <p>rsdName__ をセットします。
     * @param rsdName rsdName__
     */
    public void setRsdName(String rsdName) {
        rsdName__ = rsdName;
    }
    /**
     * <p>rsvIkkatuTorokuKey__ を取得します。
     * @return rsvIkkatuTorokuKey
     */
    public String getRsvIkkatuTorokuKey() {
        return rsvIkkatuTorokuKey__;
    }
    /**
     * <p>rsvIkkatuTorokuKey__ をセットします。
     * @param rsvIkkatuTorokuKey rsvIkkatuTorokuKey__
     */
    public void setRsvIkkatuTorokuKey(String rsvIkkatuTorokuKey) {
        rsvIkkatuTorokuKey__ = rsvIkkatuTorokuKey;
    }
}