package jp.groupsession.v2.api.smail.filedownload;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.GSConstApi;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ファイルのダウンロードを行うWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "smail-filedownload",
plugin = "smail", name = "ショートメール添付ファイルダウンロード",
url = "/api/smail/filedownload.do", reqtype = "GET")
public class ApiSmlFileDownloadForm extends AbstractApiForm {

    /** バイナリSID */
    @ApiParam(name = "binSid", viewName = "バイナリSID")
    private String binSid__ = null;
    /** ショートメールSID */
    @ApiParam(name = "smlSid", viewName = "ショートメールSID")
    private String smlSid__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GsMessage
     * @param usrSid ユーザSID
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCmnDownload(Connection con, GsMessage gsMsg, int usrSid)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        if (StringUtil.isNullZeroString(binSid__)) {
            //未入力
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(GSConstApi.TEXT_BIN_SID));
            StrutsUtil.addMessage(errors, msg, "binSid");

        } else if (!ValidateUtil.isNumber(binSid__)) {
            //数字チェック
            msg = new ActionMessage("error.input.number.hankaku",
                    gsMsg.getMessage(GSConstApi.TEXT_BIN_SID));
                StrutsUtil.addMessage(errors, msg, "binSid");
        }

        if (StringUtil.isNullZeroString(smlSid__)) {
            //未入力
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(GSConstApi.TEXT_SML_SID));
            StrutsUtil.addMessage(errors, msg, "smlSid");

        } else if (!ValidateUtil.isNumber(smlSid__)) {
            //数字チェック
            msg = new ActionMessage("error.input.number.hankaku",
                    gsMsg.getMessage(GSConstApi.TEXT_SML_SID));
                StrutsUtil.addMessage(errors, msg, "smlSid");
        }
        if (errors.size() < 1) {
            //ショートメール添付ファイルチェック
            if (!__isFileOk(usrSid, con)) {
                msg = new ActionMessage("search.notfound.tdfkcode",
                        gsMsg.getMessage(GSConstApi.TEXT_TEMP_FILE));
                    StrutsUtil.addMessage(errors, msg, "smlSid");
            }
        }

        return errors;
    }

    /**
     * ファイルの存在チェックを行う。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid 送信先ユーザSID
     * @param con コネクション
     * @return boolean true:存在する false:存在しない
     * @throws SQLException SQL実行時例外
     */
    private boolean __isFileOk(
            int usrSid,
            Connection con) throws SQLException {

        SmlBinDao dao = new SmlBinDao(con);
        int count = dao.getBinFileCnt(
                Integer.parseInt(smlSid__), usrSid, Long.parseLong(binSid__));
        if (count > 0) {

            return true;
        }
        return false;
    }

    /**
     * @return binSid
     */
    public String getBinSid() {
        return binSid__;
    }

    /**
     * @param binSid 設定する binSid
     */
    public void setBinSid(String binSid) {
        binSid__ = binSid;
    }

    /**
     * <p>smlSid を取得します。
     * @return smlSid
     */
    public String getSmlSid() {
        return smlSid__;
    }

    /**
     * <p>smlSid をセットします。
     * @param smlSid smlSid
     */
    public void setSmlSid(String smlSid) {
        smlSid__ = smlSid;
    }
}
