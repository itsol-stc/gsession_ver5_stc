package jp.groupsession.v2.api.ntp.nippou.comment.delete;

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
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpCommentDao;
import jp.groupsession.v2.ntp.model.NtpCommentModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * コメント削除APIform
 * <br>[機  能] WEBAPI 日報 コメント削除フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouCommentDelete",
plugin = "nippou", name = "日報コメント削除",
url = "/api/ntp/nippouCommentDelete.do", reqtype = "DELETE")
public class ApiNippouCommentDeleteForm extends AbstractApiForm {
    /** コメントSID*/
    @ApiParam(name = "npcSid", viewName = "コメントSID")
    private String npcSid__;

    /**
     * <p>npcSid を取得します。
     * @return npcSid
     */
    public String getNpcSid() {
        return npcSid__;
    }

    /**
     * <p>npcSid をセットします。
     * @param npcSid npcSid
     */
    public void setNpcSid(String npcSid) {
        npcSid__ = npcSid;
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
        if (StringUtil.isNullZeroString(npcSid__)) {
            msg = new ActionMessage("error.input.required.text", "コメントSid");
            StrutsUtil.addMessage(errors, msg, "npcSid");
            return errors;
        }
        if (!GSValidateUtil.isNumberHaifun(npcSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", "コメントSid");
            StrutsUtil.addMessage(errors, msg, "npcSid");
            return errors;

        }
        //編集権減チェック
        //コメント取得
        NtpCommentDao comDao = new NtpCommentDao(con);
        NtpCommentModel comModel = comDao.select(Integer.parseInt(npcSid__));
        if (comModel == null) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            //日報
            String textNippou = gsMsg.getMessage("cmn.comment");
            //閲覧
            String edit = gsMsg.getMessage("cmn.delete");

            msg = new ActionMessage(
                    "error.none.edit.data", textNippou, edit);
            StrutsUtil.addMessage(errors, msg, "admFlg");
            return errors;
        }
        //日報閲覧権限チェック
        ApiNippouEditBiz editBiz = new ApiNippouEditBiz(con, reqMdl);
        int ecode = editBiz.validateNippouAccsess(comModel.getNipSid(), true);
        if (ecode != 0) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String edit = gsMsg.getMessage("cmn.delete");
            msg = new ActionMessage(
                    "error.edit.power.user", edit, edit);
            StrutsUtil.addMessage(errors, msg, "admFlg");
            return errors;
        }

        //コメント編集権限チェック
        CommonBiz commonBiz = new CommonBiz();
        if (comModel.getNpcAuid() != reqMdl.getSmodel().getUsrsid()
              && !commonBiz.isPluginAdmin(con, reqMdl.getSmodel(),
                      GSConstNippou.PLUGIN_ID_NIPPOU)) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String edit = gsMsg.getMessage("cmn.delete");
            msg = new ActionMessage(
                    "error.edit.power.user", edit, edit);
            StrutsUtil.addMessage(errors, msg, "admFlg");
            return errors;
        }


        return errors;
    }

}
