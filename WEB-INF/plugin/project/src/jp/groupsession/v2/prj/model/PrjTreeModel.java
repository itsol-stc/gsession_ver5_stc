package jp.groupsession.v2.prj.model;

import java.io.Serializable;

/**
 * <br>[機  能] ディレクトリ、ファイルのツリー構成情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjTreeModel implements Serializable {

    /** ディレクトリSID */
    private int pdrSid__;
    /** 親ディレクトリSID */
    private int pdrParentSid__;
    /** ディレクトリ名称 */
    private String pdrName__;
    /** ディレクトリ階層LV */
    private int pdrLevel__;

    /**
     * <p>pdrLevel を取得します。
     * @return pdrLevel
     */
    public int getPdrLevel() {
        return pdrLevel__;
    }
    /**
     * <p>pdrLevel をセットします。
     * @param pdrLevel pdrLevel
     */
    public void setPdrLevel(int pdrLevel) {
        pdrLevel__ = pdrLevel;
    }
    /**
     * <p>pdrName を取得します。
     * @return pdrName
     */
    public String getPdrName() {
        return pdrName__;
    }
    /**
     * <p>pdrName をセットします。
     * @param pdrName pdrName
     */
    public void setPdrName(String pdrName) {
        pdrName__ = pdrName;
    }
    /**
     * <p>pdrParentSid を取得します。
     * @return pdrParentSid
     */
    public int getPdrParentSid() {
        return pdrParentSid__;
    }
    /**
     * <p>pdrParentSid をセットします。
     * @param pdrParentSid pdrParentSid
     */
    public void setPdrParentSid(int pdrParentSid) {
        pdrParentSid__ = pdrParentSid;
    }
    /**
     * <p>pdrSid を取得します。
     * @return pdrSid
     */
    public int getPdrSid() {
        return pdrSid__;
    }
    /**
     * <p>pdrSid をセットします。
     * @param pdrSid pdrSid
     */
    public void setPdrSid(int pdrSid) {
        pdrSid__ = pdrSid;
    }

//    /** ディレクトリSID LV1 */
//    private int lv1PdrSid__;
//    /** 親ディレクトリSID LV1 */
//    private int lv1PdrParentSid__;
//    /** ディレクトリ名称 LV1 */
//    private String lv1PdrName__;
//    /** ディレクトリSID LV2 */
//    private int lv2PdrSid__;
//    /** 親ディレクトリSID LV2 */
//    private int lv2PdrParentSid__;
//    /** ディレクトリ名称 LV2 */
//    private String lv2PdrName__;
//    /** ディレクトリSID LV3 */
//    private int lv3PdrSid__;
//    /** 親ディレクトリSID LV3 */
//    private int lv3PdrParentSid__;
//    /** ディレクトリ名称 LV3 */
//    private String lv3PdrName__;
//    /** ディレクトリSID LV4 */
//    private int lv4PdrSid__;
//    /** 親ディレクトリSID LV4 */
//    private int lv4PdrParentSid__;
//    /** ディレクトリ名称 LV4 */
//    private String lv4PdrName__;
//    /** ディレクトリSID LV5 */
//    private int lv5PdrSid__;
//    /** 親ディレクトリSID LV5 */
//    private int lv5PdrParentSid__;
//    /** ディレクトリ名称 LV5 */
//    private String lv5PdrName__;
//
//    /**
//     * <p>lv1PdrName を取得します。
//     * @return lv1PdrName
//     */
//    public String getLv1PdrName() {
//        return lv1PdrName__;
//    }
//    /**
//     * <p>lv1PdrName をセットします。
//     * @param lv1PdrName lv1PdrName
//     */
//    public void setLv1PdrName(String lv1PdrName) {
//        lv1PdrName__ = lv1PdrName;
//    }
//    /**
//     * <p>lv1PdrParentSid を取得します。
//     * @return lv1PdrParentSid
//     */
//    public int getLv1PdrParentSid() {
//        return lv1PdrParentSid__;
//    }
//    /**
//     * <p>lv1PdrParentSid をセットします。
//     * @param lv1PdrParentSid lv1PdrParentSid
//     */
//    public void setLv1PdrParentSid(int lv1PdrParentSid) {
//        lv1PdrParentSid__ = lv1PdrParentSid;
//    }
//    /**
//     * <p>lv1PdrSid を取得します。
//     * @return lv1PdrSid
//     */
//    public int getLv1PdrSid() {
//        return lv1PdrSid__;
//    }
//    /**
//     * <p>lv1PdrSid をセットします。
//     * @param lv1PdrSid lv1PdrSid
//     */
//    public void setLv1PdrSid(int lv1PdrSid) {
//        lv1PdrSid__ = lv1PdrSid;
//    }
//    /**
//     * <p>lv2PdrName を取得します。
//     * @return lv2PdrName
//     */
//    public String getLv2PdrName() {
//        return lv2PdrName__;
//    }
//    /**
//     * <p>lv2PdrName をセットします。
//     * @param lv2PdrName lv2PdrName
//     */
//    public void setLv2PdrName(String lv2PdrName) {
//        lv2PdrName__ = lv2PdrName;
//    }
//    /**
//     * <p>lv2PdrParentSid を取得します。
//     * @return lv2PdrParentSid
//     */
//    public int getLv2PdrParentSid() {
//        return lv2PdrParentSid__;
//    }
//    /**
//     * <p>lv2PdrParentSid をセットします。
//     * @param lv2PdrParentSid lv2PdrParentSid
//     */
//    public void setLv2PdrParentSid(int lv2PdrParentSid) {
//        lv2PdrParentSid__ = lv2PdrParentSid;
//    }
//    /**
//     * <p>lv2PdrSid を取得します。
//     * @return lv2PdrSid
//     */
//    public int getLv2PdrSid() {
//        return lv2PdrSid__;
//    }
//    /**
//     * <p>lv2PdrSid をセットします。
//     * @param lv2PdrSid lv2PdrSid
//     */
//    public void setLv2PdrSid(int lv2PdrSid) {
//        lv2PdrSid__ = lv2PdrSid;
//    }
//    /**
//     * <p>lv3PdrName を取得します。
//     * @return lv3PdrName
//     */
//    public String getLv3PdrName() {
//        return lv3PdrName__;
//    }
//    /**
//     * <p>lv3PdrName をセットします。
//     * @param lv3PdrName lv3PdrName
//     */
//    public void setLv3PdrName(String lv3PdrName) {
//        lv3PdrName__ = lv3PdrName;
//    }
//    /**
//     * <p>lv3PdrParentSid を取得します。
//     * @return lv3PdrParentSid
//     */
//    public int getLv3PdrParentSid() {
//        return lv3PdrParentSid__;
//    }
//    /**
//     * <p>lv3PdrParentSid をセットします。
//     * @param lv3PdrParentSid lv3PdrParentSid
//     */
//    public void setLv3PdrParentSid(int lv3PdrParentSid) {
//        lv3PdrParentSid__ = lv3PdrParentSid;
//    }
//    /**
//     * <p>lv3PdrSid を取得します。
//     * @return lv3PdrSid
//     */
//    public int getLv3PdrSid() {
//        return lv3PdrSid__;
//    }
//    /**
//     * <p>lv3PdrSid をセットします。
//     * @param lv3PdrSid lv3PdrSid
//     */
//    public void setLv3PdrSid(int lv3PdrSid) {
//        lv3PdrSid__ = lv3PdrSid;
//    }
//    /**
//     * <p>lv4PdrName を取得します。
//     * @return lv4PdrName
//     */
//    public String getLv4PdrName() {
//        return lv4PdrName__;
//    }
//    /**
//     * <p>lv4PdrName をセットします。
//     * @param lv4PdrName lv4PdrName
//     */
//    public void setLv4PdrName(String lv4PdrName) {
//        lv4PdrName__ = lv4PdrName;
//    }
//    /**
//     * <p>lv4PdrParentSid を取得します。
//     * @return lv4PdrParentSid
//     */
//    public int getLv4PdrParentSid() {
//        return lv4PdrParentSid__;
//    }
//    /**
//     * <p>lv4PdrParentSid をセットします。
//     * @param lv4PdrParentSid lv4PdrParentSid
//     */
//    public void setLv4PdrParentSid(int lv4PdrParentSid) {
//        lv4PdrParentSid__ = lv4PdrParentSid;
//    }
//    /**
//     * <p>lv4PdrSid を取得します。
//     * @return lv4PdrSid
//     */
//    public int getLv4PdrSid() {
//        return lv4PdrSid__;
//    }
//    /**
//     * <p>lv4PdrSid をセットします。
//     * @param lv4PdrSid lv4PdrSid
//     */
//    public void setLv4PdrSid(int lv4PdrSid) {
//        lv4PdrSid__ = lv4PdrSid;
//    }
//    /**
//     * <p>lv5PdrName を取得します。
//     * @return lv5PdrName
//     */
//    public String getLv5PdrName() {
//        return lv5PdrName__;
//    }
//    /**
//     * <p>lv5PdrName をセットします。
//     * @param lv5PdrName lv5PdrName
//     */
//    public void setLv5PdrName(String lv5PdrName) {
//        lv5PdrName__ = lv5PdrName;
//    }
//    /**
//     * <p>lv5PdrParentSid を取得します。
//     * @return lv5PdrParentSid
//     */
//    public int getLv5PdrParentSid() {
//        return lv5PdrParentSid__;
//    }
//    /**
//     * <p>lv5PdrParentSid をセットします。
//     * @param lv5PdrParentSid lv5PdrParentSid
//     */
//    public void setLv5PdrParentSid(int lv5PdrParentSid) {
//        lv5PdrParentSid__ = lv5PdrParentSid;
//    }
//    /**
//     * <p>lv5PdrSid を取得します。
//     * @return lv5PdrSid
//     */
//    public int getLv5PdrSid() {
//        return lv5PdrSid__;
//    }
//    /**
//     * <p>lv5PdrSid をセットします。
//     * @param lv5PdrSid lv5PdrSid
//     */
//    public void setLv5PdrSid(int lv5PdrSid) {
//        lv5PdrSid__ = lv5PdrSid;
//    }
}