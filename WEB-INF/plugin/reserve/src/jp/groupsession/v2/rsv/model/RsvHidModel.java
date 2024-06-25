package jp.groupsession.v2.rsv.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 施設予約 選択済施設(非表示) を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RsvHidModel extends AbstractModel {

    /** グループSID */
    private int rsgSid__;
    /** グループ名称 */
    private String rsgName__ = null;
    /** 施設SID */
    private int rsdSid__;
    /** 施設名称 */
    private String rsdName__ = null;

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
     * <p>rsgName__ を取得します。
     * @return rsgName
     */
    public String getRsgName() {
        return rsgName__;
    }
    /**
     * <p>rsgName__ をセットします。
     * @param rsgName rsgName__
     */
    public void setRsgName(String rsgName) {
        rsgName__ = rsgName;
    }
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
     * <p>rsgSid__ を取得します。
     * @return rsgSid
     */
    public int getRsgSid() {
        return rsgSid__;
    }
    /**
     * <p>rsgSid__ をセットします。
     * @param rsgSid rsgSid__
     */
    public void setRsgSid(int rsgSid) {
        rsgSid__ = rsgSid;
    }
}