package jp.groupsession.v2.fil.main;

import jp.groupsession.v2.fil.fil010.Fil010Form;

/**
 * <br>[機  能] ファイル管理メインのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FilMainForm extends Fil010Form {

    /** ディレクトリSID */
    private String fil050DirSid__ = null;
    /** ディレクトリSID */
    private String fil070DirSid__ = null;
    /** ファイル管理トップ画面URL */
    private String  filTopUrl__;
    /**
     * @return filTopUrl__ を戻します。
     */
    public String getFilTopUrl() {
        return filTopUrl__;
    }
    /**
     * @param filTopUrl 設定する filTopUrl__。
     */
    public void setFilTopUrl(String filTopUrl) {
        filTopUrl__ = filTopUrl;
    }

    /**
     * <p>fil050DirSid を取得します。
     * @return fil050DirSid
     */
    public String getFil050DirSid() {
        return fil050DirSid__;
    }
    /**
     * <p>fil050DirSid をセットします。
     * @param fil050DirSid fil050DirSid
     */
    public void setFil050DirSid(String fil050DirSid) {
        fil050DirSid__ = fil050DirSid;
    }
    /**
     * <p>fil070DirSid を取得します。
     * @return fil070DirSid
     */
    public String getFil070DirSid() {
        return fil070DirSid__;
    }
    /**
     * <p>fil070DirSid をセットします。
     * @param fil070DirSid fil070DirSid
     */
    public void setFil070DirSid(String fil070DirSid) {
        fil070DirSid__ = fil070DirSid;
    }

}