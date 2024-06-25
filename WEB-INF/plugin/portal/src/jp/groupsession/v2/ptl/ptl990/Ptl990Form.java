package jp.groupsession.v2.ptl.ptl990;

import jp.groupsession.v2.ptl.AbstractPtlForm;

/**
 * <br>[機  能] ポータル ポートレット画像表示のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ptl990Form extends AbstractPtlForm {
    /** ポートレットSID */
    private String ptlPortletSid__ = null;
    /** ポートレット画像SID */
    private long imgId__ = 0;

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
    public long getImgId() {
        return imgId__;
    }
    /**
     * <p>imgId をセットします。
     * @param imgId imgId
     */
    public void setImgId(long imgId) {
        imgId__ = imgId;
    }
}