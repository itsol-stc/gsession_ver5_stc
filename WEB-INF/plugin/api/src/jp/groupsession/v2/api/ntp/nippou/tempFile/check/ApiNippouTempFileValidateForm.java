package jp.groupsession.v2.api.ntp.nippou.tempFile.check;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.ntp.nippou.edit.ApiNippouEditBiz;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] WEBAPI 日報 添付ファイルの事前確認フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouTempFileCheck",
plugin = "nippou", name = "添付ファイル保存可能チェック",
url = "/api/ntp/nippouTempFileCheck.do", reqtype = "GET")
public class ApiNippouTempFileValidateForm extends AbstractApiForm {
    /** ファイルネーム*/
    @ApiParam(name = "binFileName", viewName = "ファイル名")
    private String binFileName__;
    /** ファイルサイズ*/
    @ApiParam(name = "binFileSize", viewName = "ファイルサイズ")
    private String binFileSize__;
    /**
     * <p>binFileName を取得します。
     * @return binFileName
     */
    public String getBinFileName() {
        return binFileName__;
    }
    /**
     * <p>binFileName をセットします。
     * @param binFileName binFileName
     */
    public void setBinFileName(String binFileName) {
        binFileName__ = binFileName;
    }
    /**
     * <p>binFileSize を取得します。
     * @return binFileSize
     */
    public String getBinFileSize() {
        return binFileSize__;
    }
    /**
     * <p>binFileSize をセットします。
     * @param binFileSize binFileSize
     */
    public void setBinFileSize(String binFileSize) {
        binFileSize__ = binFileSize;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(
            RequestModel reqMdl, HttpServletRequest req, Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        if (StringUtil.isNullZeroString(binFileName__)) {
            String textFileName = gsMsg.getMessage(req, "cmn.file.name");
            msg = new ActionMessage("error.input.required.text", textFileName);
            StrutsUtil.addMessage(errors, msg, "binFileName");
            return errors;
        }
        if (StringUtil.isNullZeroString(binFileSize__)) {

            msg = new ActionMessage("error.input.required.text", "ファイルサイズ");
            StrutsUtil.addMessage(errors, msg, "binFileSize");
            return errors;
        }
        if (!GSValidateUtil.isNumber(binFileSize__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "ファイルサイズ");
            StrutsUtil.addMessage(errors, msg, "binFileSize");
            return errors;

        }

        ApiNippouEditBiz biz = new ApiNippouEditBiz(con, reqMdl);


        errors = biz.validateTempFile(binFileName__, Integer.parseInt(binFileSize__));
        return errors;
    }
}
