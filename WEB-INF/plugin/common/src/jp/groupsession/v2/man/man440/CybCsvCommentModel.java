package jp.groupsession.v2.man.man440;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] サイボウズLive インポート用コメントモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CybCsvCommentModel {
    /** 投稿ユーザ名 */
    private String name__ = null;

    /** 投稿日時 */
    private UDate  date__ = null;

    /** 投稿内容 */
    private String value__ = null;

    /**
     *  コンストラクタ
     */
    public CybCsvCommentModel() {
        super();
    }

    /**
     * @return name を戻します。
     */
    public String getName() {
        return name__;
    }

    /**
     * @param name 設定する name。
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     * @return date を戻します。
     */
    public UDate getDate() {
        return date__;
    }

    /**
     * @param date 設定する date。
     */
    public void setDate(UDate date) {
        date__ = date;
    }

    /**
     * @return value を戻します。
     */
    public String getValue() {
        return value__;
    }

    /**
     * @param value 設定する value。
     */
    public void setValue(String value) {
        value__ = value;
    }
}
