package jp.groupsession.v2.api.file.editfolder;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] /file/addfolderのparamModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileEditFolderParamModel extends AbstractParamModel {
    /** ディレクトリSID（編集するフォルダ） */
    private String fdrSid__ = null;
    /** フォルダ名 */
    private String fdrName__ = null;
    /** 備考 */
    private String fdrNote__ = null;
    /**
     * @return fdrSid
     */
    public String getFdrSid() {
        return fdrSid__;
    }

    /**
     * @param fdrSid セットする fdrSid
     */
    public void setFdrSid(String fdrSid) {
        fdrSid__ = fdrSid;
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
