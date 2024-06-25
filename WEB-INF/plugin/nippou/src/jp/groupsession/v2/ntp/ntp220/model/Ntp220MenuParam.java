package jp.groupsession.v2.ntp.ntp220.model;

/**
 * <br>[機  能] メニューに表示する情報を格納するモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Ntp220MenuParam {
    /** contentSid */
    private int contentSid1__;
    /** contentSid */
    private int contentSid2__;
    /** contentName1 */
    private String contentName1__;
    /** contentName2 */
    private String contentName2__;
    /** alreadyFlg */
    private boolean alreadyFlg__ = false;

    /**
     * <p>contentSid1 を取得します。
     * @return contentSid1
     */
    public int getContentSid1() {
        return contentSid1__;
    }
    /**
     * <p>contentSid1 をセットします。
     * @param contentSid1 contentSid1
     */
    public void setContentSid1(int contentSid1) {
        contentSid1__ = contentSid1;
    }
    /**
     * <p>contentSid2 を取得します。
     * @return contentSid2
     */
    public int getContentSid2() {
        return contentSid2__;
    }
    /**
     * <p>contentSid2 をセットします。
     * @param contentSid2 contentSid2
     */
    public void setContentSid2(int contentSid2) {
        contentSid2__ = contentSid2;
    }
    /**
     * <p>contentName1 を取得します。
     * @return contentName1
     */
    public String getContentName1() {
        return contentName1__;
    }
    /**
     * <p>contentName1 をセットします。
     * @param contentName1 contentName1
     */
    public void setContentName1(String contentName1) {
        contentName1__ = contentName1;
    }
    /**
     * <p>contentName2 を取得します。
     * @return contentName2
     */
    public String getContentName2() {
        return contentName2__;
    }
    /**
     * <p>contentName2 をセットします。
     * @param contentName2 contentName2
     */
    public void setContentName2(String contentName2) {
        contentName2__ = contentName2;
    }
    /**
     * <p>alreadyFlg を取得します。
     * @return alreadyFlg
     */
    public boolean isAlreadyFlg() {
        return alreadyFlg__;
    }
    /**
     * <p>alreadyFlg をセットします。
     * @param alreadyFlg alreadyFlg
     */
    public void setAlreadyFlg(boolean alreadyFlg) {
        alreadyFlg__ = alreadyFlg;
    }
}
