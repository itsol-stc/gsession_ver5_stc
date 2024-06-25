package jp.groupsession.v2.ptl.ptl990;

import jp.groupsession.v2.ptl.model.PtlParamModel;

/**
 * <br>[機  能] ポータル ポートレット画像表示のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl990ParamModel extends PtlParamModel {
    /** ポートレットSID */
    private String ptlPortletSid__ = null;
    /** ポートレット画像SID */
    private String imgId__ = "";

    /**
     * <p>ptlPortletSid を取得します。
     * @return ptlPortletSid
     */
    public String getPtlPortletSid() {
        return ptlPortletSid__;
    }
    /**
     * <p>ptlPortletSid をセットします。
     * @param ptlPortletSid ptlPortletSid
     */
    public void setPtlPortletSid(String ptlPortletSid) {
        ptlPortletSid__ = ptlPortletSid;
    }
    /**
     * <p>imgId を取得します。
     * @return imgId
     */
    public String getImgId() {
        return imgId__;
    }
    /**
     * <p>imgId をセットします。
     * @param imgId imgId
     */
    public void setImgId(String imgId) {
        imgId__ = imgId;
    }
}