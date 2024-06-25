package jp.groupsession.v2.api.file.rename;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] /file/renameのparamModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileRenameParamModel extends AbstractParamModel {
    /** ディレクトリSID */
    private String fdrSid__ = null;
    /** ファイル名 */
    private String fdrName__ = null;

    /** キャビネットSID(入力チェック用) */
    private int fcbSid__ = 0;
    /**
     * @return fdrSid
     */
    public String getFdrSid() {
        return fdrSid__;
    }

    /**
     * @param fdrSid 設定する fdrSid
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
     * <p>fcbSid を取得します。
     * @return fcbSid
     */
    public int getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>fcbSid をセットします。
     * @param fcbSid fcbSid
     */
    public void setFcbSid(int fcbSid) {
        fcbSid__ = fcbSid;
    }


}
