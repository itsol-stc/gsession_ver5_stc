package jp.groupsession.v2.ntp.ntp250;

import jp.groupsession.v2.ntp.ntp010.Ntp010ParamModel;

/**
 * <br>[機  能] 日報 目標情報ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp250ParamModel extends Ntp010ParamModel {

    /** 目標SID */
    private int ntp250NtgSid__ = -1;
    /** 目標名 */
    private String ntp250TargetName__ = null;
    /** 目標内容 */
    private String ntp250TargetDetail__ = null;
    /** 目標初期値 */
    private long ntp250TargetDef__ = new Long(0);
    /** 目標単位 */
    private String ntp250TargetUnit__ = null;
    /**
     * <p>ntp250NtgSid を取得します。
     * @return ntp250NtgSid
     */
    public int getNtp250NtgSid() {
        return ntp250NtgSid__;
    }
    /**
     * <p>ntp250NtgSid をセットします。
     * @param ntp250NtgSid ntp250NtgSid
     */
    public void setNtp250NtgSid(int ntp250NtgSid) {
        ntp250NtgSid__ = ntp250NtgSid;
    }
    /**
     * <p>ntp250TargetName を取得します。
     * @return ntp250TargetName
     */
    public String getNtp250TargetName() {
        return ntp250TargetName__;
    }
    /**
     * <p>ntp250TargetName をセットします。
     * @param ntp250TargetName ntp250TargetName
     */
    public void setNtp250TargetName(String ntp250TargetName) {
        ntp250TargetName__ = ntp250TargetName;
    }
    /**
     * <p>ntp250TargetDetail を取得します。
     * @return ntp250TargetDetail
     */
    public String getNtp250TargetDetail() {
        return ntp250TargetDetail__;
    }
    /**
     * <p>ntp250TargetDetail をセットします。
     * @param ntp250TargetDetail ntp250TargetDetail
     */
    public void setNtp250TargetDetail(String ntp250TargetDetail) {
        ntp250TargetDetail__ = ntp250TargetDetail;
    }
    /**
     * <p>ntp250TargetDef を取得します。
     * @return ntp250TargetDef
     */
    public long getNtp250TargetDef() {
        return ntp250TargetDef__;
    }
    /**
     * <p>ntp250TargetDef をセットします。
     * @param ntp250TargetDef ntp250TargetDef
     */
    public void setNtp250TargetDef(long ntp250TargetDef) {
        ntp250TargetDef__ = ntp250TargetDef;
    }
    /**
     * <p>ntp250TargetUnit を取得します。
     * @return ntp250TargetUnit
     */
    public String getNtp250TargetUnit() {
        return ntp250TargetUnit__;
    }
    /**
     * <p>ntp250TargetUnit をセットします。
     * @param ntp250TargetUnit ntp250TargetUnit
     */
    public void setNtp250TargetUnit(String ntp250TargetUnit) {
        ntp250TargetUnit__ = ntp250TargetUnit;
    }
}