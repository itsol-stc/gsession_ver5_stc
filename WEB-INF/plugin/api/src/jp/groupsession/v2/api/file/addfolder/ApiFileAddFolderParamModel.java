package jp.groupsession.v2.api.file.addfolder;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
/**
 *
 * <br>[機  能] /file/addfolderのパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileAddFolderParamModel extends AbstractParamModel {
    /** 親ディレクトリSID */
    private String fdrParentSid__ = null;
    /** フォルダ名 */
    private String fdrName__ = null;
    /** 備考 */
    private String fdrNote__ = null;

    /**
     * @return fdrParentSid
     */
    public String getFdrParentSid() {
        return fdrParentSid__;
    }

    /**
     * @param fdrParentSid 設定する fdrParentSid
     */
    public void setFdrParentSid(String fdrParentSid) {
        fdrParentSid__ = fdrParentSid;
    }

    /**
     * <p>fdrName を取得します。
     * @return fdrName
     */
    public String getFdrName() {
        return fdrName__;
    }

    /**
     * <p>fdrName をセットします。
     * @param fdrName fdrName
     */
    public void setFdrName(String fdrName) {
        fdrName__ = fdrName;
    }

    /**
     * <p>fdrNote を取得します。
     * @return fdrNote
     */
    public String getFdrNote() {
        return fdrNote__;
    }

    /**
     * <p>fdrNote をセットします。
     * @param fdrNote fdrNote
     */
    public void setFdrNote(String fdrNote) {
        fdrNote__ = fdrNote;
    }

}
