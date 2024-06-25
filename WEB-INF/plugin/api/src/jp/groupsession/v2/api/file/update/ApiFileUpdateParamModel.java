package jp.groupsession.v2.api.file.update;

import org.apache.struts.upload.FormFile;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
/**
 *
 * <br>[機  能] /file/updateのparamModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileUpdateParamModel extends AbstractParamModel {
    /** ディレクトリSID */
    private String fdrSid__ = null;
    /** ファイル */
    private FormFile uploadFile__ = null;
    /** 取引年月日 */
    private String fdrErrlDate__ = null;
    /** 取引先 */
    private String fdrErrlTarget__ = null;
    /** 取引金額 */
    private String fdrErrlMoney__;
    /** 外貨名 */
    private String fdrErrlMoneyType__ = null;

//    /** キャビネットSID(入力チェック用) */
//    private int fcbSid__ = 0;
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
     * @return uploadFile
     */
    public FormFile getUploadFile() {
        return uploadFile__;
    }

    /**
     * @param uploadFile 設定する uploadFile
     */
    public void setUploadFile(FormFile uploadFile) {
        uploadFile__ = uploadFile;
    }

    /**
     * <p>fdrErrlDate を取得します。
     * @return fdrErrlDate
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateParamModel#fdrErrlDate__
     */
    public String getFdrErrlDate() {
        return fdrErrlDate__;
    }

    /**
     * <p>fdrErrlDate をセットします。
     * @param fdrErrlDate fdrErrlDate
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateParamModel#fdrErrlDate__
     */
    public void setFdrErrlDate(String fdrErrlDate) {
        fdrErrlDate__ = fdrErrlDate;
    }

    /**
     * <p>fdrErrlTarget を取得します。
     * @return fdrErrlTarget
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateParamModel#fdrErrlTarget__
     */
    public String getFdrErrlTarget() {
        return fdrErrlTarget__;
    }

    /**
     * <p>fdrErrlTarget をセットします。
     * @param fdrErrlTarget fdrErrlTarget
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateParamModel#fdrErrlTarget__
     */
    public void setFdrErrlTarget(String fdrErrlTarget) {
        fdrErrlTarget__ = fdrErrlTarget;
    }

    /**
     * <p>fdrErrlMoney を取得します。
     * @return fdrErrlMoney
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateParamModel#fdrErrlMoney__
     */
    public String getFdrErrlMoney() {
        return fdrErrlMoney__;
    }

    /**
     * <p>fdrErrlMoney をセットします。
     * @param fdrErrlMoney fdrErrlMoney
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateParamModel#fdrErrlMoney__
     */
    public void setFdrErrlMoney(String fdrErrlMoney) {
        fdrErrlMoney__ = fdrErrlMoney;
    }

    /**
     * <p>fdrErrlMoneyType を取得します。
     * @return fdrErrlMoneyType
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateParamModel#fdrErrlMoneyType__
     */
    public String getFdrErrlMoneyType() {
        return fdrErrlMoneyType__;
    }

    /**
     * <p>fdrErrlMoneyType をセットします。
     * @param fdrErrlMoneyType fdrErrlMoneyType
     * @see jp.groupsession.v2.api.file.update.ApiFileUpdateParamModel#fdrErrlMoneyType__
     */
    public void setFdrErrlMoneyType(String fdrErrlMoneyType) {
        fdrErrlMoneyType__ = fdrErrlMoneyType;
    }
}
