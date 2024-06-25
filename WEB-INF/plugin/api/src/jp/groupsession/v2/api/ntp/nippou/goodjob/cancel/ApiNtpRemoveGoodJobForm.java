package jp.groupsession.v2.api.ntp.nippou.goodjob.cancel;

import java.sql.Connection;
import java.sql.SQLException;

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
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] WEBAPI 日報いいね！除去フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouRemoveGoodJob",
plugin = "nippou", name = "いいねを除去",
url = "/api/ntp/nippouRemoveGoodJob.do", reqtype = "DELETE")
public class ApiNtpRemoveGoodJobForm extends AbstractApiForm {
    /** 日報SID */
    @ApiParam(name = "nipSid", viewName = "日報SID")
    private String nipSid__;

    /**
     * <p>nipSid を取得します。
     * @return nipSid
     */
    public String getNipSid() {
        return nipSid__;
    }

    /**
     * <p>nipSid をセットします。
     * @param nipSid nipSid
     */
    public void setNipSid(String nipSid) {
        nipSid__ = nipSid;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, RequestModel reqMdl) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        /** NIP_SID mapping */
        String nipSid = nipSid__;
        if (StringUtil.isNullZeroString(nipSid)) {
            msg = new ActionMessage("error.input.required.text", GSConstNippou.TEXT_NIPPOU_SID);
            StrutsUtil.addMessage(errors, msg, "nipSid");
            return errors;
        }
        if (!GSValidateUtil.isNumberHaifun(nipSid)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_NIPPOU_SID);
            StrutsUtil.addMessage(errors, msg, "nipSid");
            return errors;

        }
        ApiNippouEditBiz editBiz = new ApiNippouEditBiz(con, reqMdl);
        int ecode = editBiz.validateNippouAccsess(Integer.parseInt(nipSid__), true);
        if (ecode != 0) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String edit = gsMsg.getMessage("cmn.reading");
            String goodjob = gsMsg.getMessage("ntp.22");
            msg = new ActionMessage(
                    "error.edit.power.user", edit, goodjob);
            StrutsUtil.addMessage(errors, msg, "admFlg");
            return errors;
        }
        return errors;
    }
}
