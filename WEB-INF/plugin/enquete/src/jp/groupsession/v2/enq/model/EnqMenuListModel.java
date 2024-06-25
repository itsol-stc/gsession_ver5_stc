package jp.groupsession.v2.enq.model;


/**
 * <br>[機  能] メニュー表示用のモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class EnqMenuListModel {

    /** アンケートSID */
    private long emnSid__;
    /** データ区分 */
    private int emnDataKbn__;
    /** アンケート種類 */
    private int etpSid__;
    /** アンケート種類名 */
    private String etpName__;
    /** タイトル */
    private String emnTitle__;
    /** タイトル(表示用) */
    private String viewEmnTitle__;
    /** 重要度 */
    private int emnPriKbn__;
    /** 説明 */
    private String emnDesc__;
    /** 説明（テキストのみ） */
    private String emnDescPlain__;

    /**
     * <p>emnSid を取得します。
     * @return emnSid
     */
    public long getEmnSid() {
        return emnSid__;
    }
    /**
     * <p>emnSid をセットします。
     * @param emnSid emnSid
     */
    public void setEmnSid(long emnSid) {
        emnSid__ = emnSid;
    }
    /**
     * <p>emnDataKbn を取得します。
     * @return emnDataKbn
     */
    public int getEmnDataKbn() {
        return emnDataKbn__;
    }
    /**
     * <p>emnDataKbn をセットします。
     * @param emnDataKbn emnDataKbn
     */
    public void setEmnDataKbn(int emnDataKbn) {
        emnDataKbn__ = emnDataKbn;
    }
    /**
     * <p>etpSid を取得します。
     * @return etpSid
     */
    public int getEtpSid() {
        return etpSid__;
    }
    /**
     * <p>etpSid をセットします。
     * @param etpSid etpSid
     */
    public void setEtpSid(int etpSid) {
        etpSid__ = etpSid;
    }
    /**
     * <p>etpName を取得します。
     * @return etpName
     */
    public String getEtpName() {
        return etpName__;
    }
    /**
     * <p>etpName をセットします。
     * @param etpName etpName
     */
    public void setEtpName(String etpName) {
        etpName__ = etpName;
    }
    /**
     * <p>emnTitle を取得します。
     * @return emnTitle
     */
    public String getEmnTitle() {
        return emnTitle__;
    }
    /**
     * <p>emnTitle をセットします。
     * @param emnTitle emnTitle
     */
    public void setEmnTitle(String emnTitle) {
        emnTitle__ = emnTitle;
    }
    /**
     * <p>emnPriKbn を取得します。
     * @return emnPriKbn
     */
    public int getEmnPriKbn() {
        return emnPriKbn__;
    }
    /**
     * <p>emnPriKbn をセットします。
     * @param emnPriKbn emnPriKbn
     */
    public void setEmnPriKbn(int emnPriKbn) {
        emnPriKbn__ = emnPriKbn;
    }
    /**
     * <p>emnDesc を取得します。
     * @return emnDesc
     */
    public String getEmnDesc() {
        return emnDesc__;
    }
    /**
     * <p>emnDesc をセットします。
     * @param emnDesc emnDesc
     */
    public void setEmnDesc(String emnDesc) {
        emnDesc__ = emnDesc;
    }
    /**
     * <p>emnDescPlain を取得します。
     * @return emnDescPlain
     */
    public String getEmnDescPlain() {
        return emnDescPlain__;
    }
    /**
     * <p>emnDescPlain をセットします。
     * @param emnDescPlain emnDescPlain
     */
    public void setEmnDescPlain(String emnDescPlain) {
        emnDescPlain__ = emnDescPlain;
    }
    /**
     * <p>viewEmnTitle を取得します。
     * @return viewEmnTitle
     */
    public String getViewEmnTitle() {
        return viewEmnTitle__;
    }
    /**
     * <p>viewEmnTitle をセットします。
     * @param viewEmnTitle viewEmnTitle
     */
    public void setViewEmnTitle(String viewEmnTitle) {
        viewEmnTitle__ = viewEmnTitle;
    }
}
