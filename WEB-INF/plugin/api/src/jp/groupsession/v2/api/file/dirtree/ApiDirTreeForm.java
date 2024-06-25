package jp.groupsession.v2.api.file.dirtree;

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
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.fil.FilCommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] /file/dirtreeのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "file-dirtree",
plugin = "file", name = "ディレクトリツリー情報取得",
url = "/api/file/dirtree.do",
reqtype = "GET")
public class ApiDirTreeForm extends AbstractApiForm {

    /** キャビネットSID */
    @ApiParam(name = "fcbSid", viewName = "キャビネットSID")
    private String fcbSid__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateDirTree(RequestModel reqMdl, Connection con)
    throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        FilCommonBiz fileBiz = new FilCommonBiz(reqMdl, con);

        if (StringUtil.isNullZeroString(fcbSid__)) {
            //未入力
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage(GSConstApi.TEXT_CABINET_SID));
            StrutsUtil.addMessage(errors, msg, "fcbSid");

        } else if (!ValidateUtil.isNumber(fcbSid__)) {
            //数字チェック
            msg = new ActionMessage("error.input.number.hankaku",
                    gsMsg.getMessage(GSConstApi.TEXT_CABINET_SID));
                StrutsUtil.addMessage(errors, msg, "fcbSid");

        } else if (!fileBiz.isAccessAuthUser(Integer.parseInt(fcbSid__))) {
            //キャビネットへのアクセス権限なし
            msg = new ActionMessage("error.not.view.cabinet");
                StrutsUtil.addMessage(errors, msg, "fcbSid");
        }

         return errors;
    }

    /**
     * @return fcbSid
     */
    public String getFcbSid() {
        return fcbSid__;
    }

    /**
     * @param fcbSid 設定する fcbSid
     */
    public void setFcbSid(String fcbSid) {
        this.fcbSid__ = fcbSid;
    }

}
