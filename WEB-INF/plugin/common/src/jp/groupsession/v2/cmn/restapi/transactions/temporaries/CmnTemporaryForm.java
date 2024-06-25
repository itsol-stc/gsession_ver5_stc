package jp.groupsession.v2.cmn.restapi.transactions.temporaries;

import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
/**
 *
 * <br>[機  能] 認証情報取得API パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ParamModel
public class CmnTemporaryForm extends AbstractForm {
    /** 添付ファイルインデクス */
    @MaxLength(5)
    private Integer fileIndex__;
    /** 添付ファイル */
    private FormFile[] tmpFile__ = null;
    /**
     * <p>fileIndex を取得します。
     * @return fileIndex
     * @see CmnTemporaryForm#fileIndex__
     */
    public Integer getFileIndex() {
        return fileIndex__;
    }

    /**
     * <p>fileIndex をセットします。
     * @param fileIndex fileIndex
     * @see CmnTemporaryForm#fileIndex__
     */
    public void setFileIndex(Integer fileIndex) {
        fileIndex__ = fileIndex;
    }

    /**
     * <p>tmpFile を取得します。
     * @return tmpFile
     * @see CmnTemporaryForm#tmpFile__
     */
    public FormFile[] getTmpFile() {
        return tmpFile__;
    }

    /**
     * <p>tmpFile をセットします。
     * @param tmpFile tmpFile
     * @see CmnTemporaryForm#tmpFile__
     */
    public void setTmpFile(FormFile[] tmpFile) {
        tmpFile__ = tmpFile;
    }


}
