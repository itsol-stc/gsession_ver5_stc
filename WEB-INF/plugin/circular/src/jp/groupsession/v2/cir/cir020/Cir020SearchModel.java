package jp.groupsession.v2.cir.cir020;

/**
 * <br>[機  能] 回覧板の取得条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cir020SearchModel {

    /** 回覧板SID */
    private int cirSid__;
    /** アカウントSID */
    private int cacSid__;

    /**
     * <p>cirSid を取得します。
     * @return cirSid
     */
    public int getCirSid() {
        return cirSid__;
    }
    /**
     * <p>cirSid をセットします。
     * @param cirSid cirSid
     */
    public void setCirSid(int cirSid) {
        cirSid__ = cirSid;
    }
    /**
     * <p>cacSid を取得します。
     * @return cacSid
     */
    public int getCacSid() {
        return cacSid__;
    }
    /**
     * <p>cacSid をセットします。
     * @param cacSid cacSid
     */
    public void setCacSid(int cacSid) {
        cacSid__ = cacSid;
    }

}
