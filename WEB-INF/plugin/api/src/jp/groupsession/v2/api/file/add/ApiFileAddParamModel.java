package jp.groupsession.v2.api.file.add;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

import org.apache.struts.upload.FormFile;

/**
 * <br>[機  能] /file/addのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ApiFileAddParamModel extends AbstractParamModel {

    /** 親ディレクトリSID */
    private String fdrParentSid__ = null;
    /** ファイル */
    private FormFile uploadFile__ = null;
    /** キャビネットSID */
    private String fcbSid__ = null;
    /** 取引年月日 */
    private String fdrErrlDate__ = null;
    /** 取引先 */
    private String fdrErrlTarget__ = null;
    /** 取引金額 */
    private String fdrErrlMoney__;
    /** 外貨名 */
    private String fdrErrlMoneyType__ = null;

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
     * <p>fcbSid を取得します。
     * @return fcbSid
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fcbSid__
     */
    public String getFcbSid() {
        return fcbSid__;
    }

    /**
     * <p>fcbSid をセットします。
     * @param fcbSid fcbSid
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fcbSid__
     */
    public void setFcbSid(String fcbSid) {
        fcbSid__ = fcbSid;
    }

    /**
     * <p>fdrErrlDate を取得します。
     * @return fdrErrlDate
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fdrErrlDate__
     */
    public String getFdrErrlDate() {
        return fdrErrlDate__;
    }

    /**
     * <p>fdrErrlDate をセットします。
     * @param fdrErrlDate fdrErrlDate
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fdrErrlDate__
     */
    public void setFdrErrlDate(String fdrErrlDate) {
        fdrErrlDate__ = fdrErrlDate;
    }

    /**
     * <p>fdrErrlTarget を取得します。
     * @return fdrErrlTarget
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fdrErrlTarget__
     */
    public String getFdrErrlTarget() {
        return fdrErrlTarget__;
    }

    /**
     * <p>fdrErrlTarget をセットします。
     * @param fdrErrlTarget fdrErrlTarget
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fdrErrlTarget__
     */
    public void setFdrErrlTarget(String fdrErrlTarget) {
        fdrErrlTarget__ = fdrErrlTarget;
    }

    /**
     * <p>fdrErrlMoney を取得します。
     * @return fdrErrlMoney
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fdrErrlMoney__
     */
    public String getFdrErrlMoney() {
        return fdrErrlMoney__;
    }

    /**
     * <p>fdrErrlMoney をセットします。
     * @param fdrErrlMoney fdrErrlMoney
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fdrErrlMoney__
     */
    public void setFdrErrlMoney(String fdrErrlMoney) {
        fdrErrlMoney__ = fdrErrlMoney;
    }

    /**
     * <p>fdrErrlMoneyType を取得します。
     * @return fdrErrlMoneyType
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fdrErrlMoneyType__
     */
    public String getFdrErrlMoneyType() {
        return fdrErrlMoneyType__;
    }

    /**
     * <p>fdrErrlMoneyType をセットします。
     * @param fdrErrlMoneyType fdrErrlMoneyType
     * @see jp.groupsession.v2.api.file.add.ApiFileAddParamModel#fdrErrlMoneyType__
     */
    public void setFdrErrlMoneyType(String fdrErrlMoneyType) {
        fdrErrlMoneyType__ = fdrErrlMoneyType;
    }
}
