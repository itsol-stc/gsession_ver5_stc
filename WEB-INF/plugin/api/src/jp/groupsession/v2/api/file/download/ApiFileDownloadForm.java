package jp.groupsession.v2.api.file.download;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/downloadのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-download",
plugin = "file", name = "ファイルダウンロード",
url = "/api/file/download.do",
reqtype = "GET")
public class ApiFileDownloadForm extends AbstractApiForm {

    /** バイナリSID */
    @ApiParam(name = "binSid", viewName = "バイナリSID")
    private String binSid__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param gsMsg GsMessage
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateFileDownload(Connection con, RequestModel reqMdl,
            GsMessage gsMsg)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);

        if (StringUtil.isNullZeroString(binSid__)) {
            //未入力
            String textBinSid = gsMsg.getMessage("cmn.bin.sid");
            msg = new ActionMessage("error.input.required.text", textBinSid);
            StrutsUtil.addMessage(errors, msg, "binSid");

        } else if (!ValidateUtil.isNumber(binSid__)) {
            //数字チェック
            String textBinSid = gsMsg.getMessage("cmn.bin.sid");
            msg = new ActionMessage("error.input.number.hankaku", textBinSid);
                StrutsUtil.addMessage(errors, msg, "binSid");
        } else {

            int binSid = NullDefault.getInt(binSid__, -1);
            if (!fileBiz.isDownloadAuthUser(binSid)) {
                //ディレクトリへの閲覧権限なし
                msg = new ActionMessage("error.edit.power.user",
                        gsMsg.getMessage("cmn.reading"),
                        gsMsg.getMessage("cmn.download"));
                StrutsUtil.addMessage(errors, msg, "fdrSid");
            }
        }

        return errors;
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
}
