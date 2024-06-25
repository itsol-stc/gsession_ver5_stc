package jp.groupsession.v2.api.webmail.importmail;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメールのインポートを行うWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "webmail-importmail",
plugin = "webmail", name = "メール情報インポート",
url = "/api/webmail/importmail.do", reqtype = "POST")
public class ApiImportMailForm extends AbstractApiForm {

    /** アカウントSID */
    @ApiParam(name = "accountSid", viewName = "アカウントSID")
    private String accountSid__ = null;
    /** ディレクトリSID */
    @ApiParam(name = "directorySid", viewName = "ディレクトリSID")
    private String directorySid__ = null;
    /** ファイル */
    @ApiParam(name = "importFile", viewName = "ファイル")
    private FormFile importFile__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param userSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     */
    public ActionErrors validate(Connection con,
            RequestModel reqMdl,
            int userSid)
    throws SQLException, IOToolsException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        ApiImportMailBiz biz = new ApiImportMailBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        //アカウントSID
        int wacSid = -1;
        boolean result = __validateSid(errors, accountSid__, "accountSid",
                                        gsMsg.getMessage("wml.102"),
                                        String.valueOf(Integer.MAX_VALUE));
        if (result) {
            wacSid = Integer.parseInt(accountSid__);
            if (!biz.canUseAccount(con, wacSid, userSid)) {
                msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage("wml.102"));
                StrutsUtil.addMessage(errors, msg, "accountSid");
            }
        }

        //ディレクトリSID
        result = __validateSid(errors, directorySid__, "directorySid",
                gsMsg.getMessage("project.src.17"),
                                String.valueOf(Long.MAX_VALUE));
        if (result && wacSid > 0) {
            if (!biz.existDirectory(reqMdl, con, wacSid, directorySid__)) {
                msg = new ActionMessage("search.data.notfound",
                        gsMsg.getMessage("project.src.17"));
                StrutsUtil.addMessage(errors, msg, "directorySid");
            }
        }

        //ファイル
        if (importFile__ == null || importFile__.getFileSize() <= 0) {
            msg = new ActionMessage("error.input.notfound.file");
            StrutsUtil.addMessage(errors, msg, "importFile");

        } else if (!importFile__.getFileName().endsWith(".eml")) {
            msg = new ActionMessage("errors.free.msg", gsMsg.getMessage("wml.233"));
            StrutsUtil.addMessage(errors, msg, "importFile");

        } else if (!GSValidateUtil.isGsJapaneaseStringTextArea(importFile__.getFileName())) {
            //利用不可能な文字を入力した場合
            String elmName = gsMsg.getMessage("cmn.form.temp") + gsMsg.getMessage("cmn.name3");
            String str = GSValidateUtil.getNotGsJapaneaseStringTextArea(importFile__.getFileName());
            msg = new ActionMessage("error.input.njapan.text", elmName, str);
            StrutsUtil.addMessage(errors, msg, "importFile");

        } else {
            //ファイルサイズ
            WmlBiz wmlBiz = new WmlBiz();
            long diskLimitSize = wmlBiz.getDiskLimitSize(con, wacSid);
            if (diskLimitSize >= 0) {
                long useDiskSize = biz.getAccountUseDiskSize(con, wacSid);
                useDiskSize += importFile__.getFileSize();
                if (diskLimitSize < useDiskSize) {
                    msg = new ActionMessage("errors.free.msg", gsMsg.getMessage("wml.234"));
                    StrutsUtil.addMessage(errors, msg, "importFile.limit.");
                }
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] アカウントSID、ディレクトリSIDの共通エラーチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param sid アカウントSID or ディレクトリSID
     * @param paramName パラメータ名
     * @param paramNameJpn パラメータ名(日本語表記)
     * @param maxValue 最大値
     * @return true:エラーなし false:エラーあり
     */
    private boolean __validateSid(ActionErrors errors, String sid,
                                    String paramName, String paramNameJpn,
                                    String maxValue) {

        ActionMessage msg = null;

        //アカウントSID
        if (StringUtil.isNullZeroString(sid)) {
            //未入力
            msg = new ActionMessage("error.input.required.text", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramName);

        } else if (!ValidateUtil.isNumber(accountSid__)) {
            //半角数字チェック
            msg = new ActionMessage(
                    "error.input.number.hankaku", paramNameJpn);
            StrutsUtil.addMessage(errors, msg, paramName);
        } else {
            //範囲チェック
            BigInteger sidNumber = new BigInteger(accountSid__);
            if (sidNumber.compareTo(BigInteger.ZERO) < 0
            || sidNumber.compareTo(new BigInteger(maxValue)) > 0) {
                msg = new ActionMessage("error.input.lenge", paramNameJpn,
                                        "1", String.valueOf(maxValue));
                StrutsUtil.addMessage(errors, msg, paramName);
            }
        }

        return msg == null;
    }

    /**
     * <p>accountSid を取得します。
     * @return accountSid
     */
    public String getAccountSid() {
        return accountSid__;
    }

    /**
     * <p>accountSid をセットします。
     * @param accountSid accountSid
     */
    public void setAccountSid(String accountSid) {
        accountSid__ = accountSid;
    }

    /**
     * <p>directorySid を取得します。
     * @return directorySid
     */
    public String getDirectorySid() {
        return directorySid__;
    }

    /**
     * <p>directorySid をセットします。
     * @param directorySid directorySid
     */
    public void setDirectorySid(String directorySid) {
        directorySid__ = directorySid;
    }

    /**
     * <p>importFile を取得します。
     * @return importFile
     */
    public FormFile getImportFile() {
        return importFile__;
    }

    /**
     * <p>importFile をセットします。
     * @param importFile importFile
     */
    public void setImportFile(FormFile importFile) {
        importFile__ = importFile;
    }

}
