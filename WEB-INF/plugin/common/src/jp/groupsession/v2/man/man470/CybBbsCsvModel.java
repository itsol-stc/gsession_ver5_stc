package jp.groupsession.v2.man.man470;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] サイボウズLive 掲示板インポート用投稿モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CybBbsCsvModel {

    /** 投稿ユーザ名 */
    private String cruser__ = null;

    /** 投稿日時 */
    private UDate crdate__ = null;

    /** 更新ユーザ名 */
    private String upuser__ = null;

    /** 更新日時 */
    private UDate update__ = null;

    /** 本文 */
    private String value__ = null;

    /**
     *  コンストラクタ
     */
    public CybBbsCsvModel() {
        super();
    }

    /**
     * <p>cruser を取得します。
     * @return cruser
     */
    public String getCruser() {
        return cruser__;
    }

    /**
     * <p>cruser をセットします。
     * @param cruser cruser
     */
    public void setCruser(String cruser) {
        cruser__ = cruser;
    }

    /**
     * <p>crdate を取得します。
     * @return crdate
     */
    public UDate getCrdate() {
        return crdate__;
    }

    /**
     * <p>crdate をセットします。
     * @param crdate crdate
     */
    public void setCrdate(UDate crdate) {
        crdate__ = crdate;
    }

    /**
     * <p>upuser を取得します。
     * @return upuser
     */
    public String getUpuser() {
        return upuser__;
    }

    /**
     * <p>upuser をセットします。
     * @param upuser upuser
     */
    public void setUpuser(String upuser) {
        upuser__ = upuser;
    }

    /**
     * <p>update を取得します。
     * @return update
     */
    public UDate getUpdate() {
        return update__;
    }

    /**
     * <p>update をセットします。
     * @param update update
     */
    public void setUpdate(UDate update) {
        update__ = update;
    }

    /**
     * <p>value を取得します。
     * @return value
     */
    public String getValue() {
        return value__;
    }

    /**
     * <p>value をセットします。
     * @param value value
     */
    public void setValue(String value) {
        value__ = value;
    }
}
