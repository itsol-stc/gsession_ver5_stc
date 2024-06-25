package jp.groupsession.v2.fil.ptl010;

import jp.groupsession.v2.fil.fil010.Fil010ParamModel;

/**
 * <br>[機  能] ポータル ファイル管理メインツリー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilPtl010ParamModel extends Fil010ParamModel {

    /** 表示キャビネットSID */
    private int dspFcbSid__ = 0;
    /** ポータルSID */
    private int ptlPortalSid__ = -1;

    /** キャビネット名 */
    private String filPtl010FcbName__ = "";

    /**
     * <p>dspFcbSid を取得します。
     * @return dspFcbSid
     */
    public int getDspFcbSid() {
        return dspFcbSid__;
    }

    /**
     * <p>dspFcbSid をセットします。
     * @param dspFcbSid dspFcbSid
     */
    public void setDspFcbSid(int dspFcbSid) {
        dspFcbSid__ = dspFcbSid;
    }

    /**
     * <p>ptlPortalSid を取得します。
     * @return ptlPortalSid
     */
    public int getPtlPortalSid() {
        return ptlPortalSid__;
    }

    /**
     * <p>ptlPortalSid をセットします。
     * @param ptlPortalSid ptlPortalSid
     */
    public void setPtlPortalSid(int ptlPortalSid) {
        ptlPortalSid__ = ptlPortalSid;
    }

    /**
     * <p>filPtl010FcbName を取得します。
     * @return filPtl010FcbName
     */
    public String getFilPtl010FcbName() {
        return filPtl010FcbName__;
    }

    /**
     * <p>filPtl010FcbName をセットします。
     * @param filPtl010FcbName filPtl010FcbName
     */
    public void setFilPtl010FcbName(String filPtl010FcbName) {
        filPtl010FcbName__ = filPtl010FcbName;
    }



}