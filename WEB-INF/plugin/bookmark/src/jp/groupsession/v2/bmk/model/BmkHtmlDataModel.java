package jp.groupsession.v2.bmk.model;

import java.io.Serializable;

/**
 * <p>HTMLから取得した情報を格納するModel
 *
 * @author JTS
 */
public class BmkHtmlDataModel implements Serializable {

    /** タイトル */
    private String title__;
    /** コメント */
    private String description__;

    /**
     * <p>description を取得します。
     * @return description
     */
    public String getDescription() {
        return description__;
    }
    /**
     * <p>description をセットします。
     * @param description description
     */
    public void setDescription(String description) {
        description__ = description;
    }
    /**
     * <p>title を取得します。
     * @return title
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     */
    public void setTitle(String title) {
        title__ = title;
    }
}
