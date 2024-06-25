package jp.groupsession.v2.api.ntp.nippou.comment.edit;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.ntp.nippou.edit.ApiNippouEditBiz;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] WEBAPI 日報コメント投稿フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nipponippouCommentAddu-",
plugin = "nippou", name = "日報コメント追加",
url = "/api/ntp/nippouCommentAdd.do", reqtype = "POST")
public class ApiNippouCommentAddForm extends AbstractApiForm {
    /** 日報SID*/
    @ApiParam(name = "nipSid", viewName = "日報SID")
    private String nipSid__;
    /** コメント*/
    @ApiParam(name = "comment", viewName = "コメント")
    private String comment__;
    /**
     * <p>comment を取得します。
     * @return comment
     */
    public String getComment() {
        return comment__;
    }
    /**
     * <p>comment をセットします。
     * @param comment comment
     */
    public void setComment(String comment) {
        comment__ = comment;
    }
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
        if (StringUtil.isNullZeroString(nipSid__)) {
            msg = new ActionMessage("error.input.required.text", GSConstNippou.TEXT_NIPPOU_SID);
            StrutsUtil.addMessage(errors, msg, "nipSid");
            return errors;
        }
        if (!GSValidateUtil.isNumberHaifun(nipSid__)) {
            msg = new ActionMessage(
                    "error.input.number.hankaku", GSConstNippou.TEXT_NIPPOU_SID);
            StrutsUtil.addMessage(errors, msg, "nipSid");
            return errors;

        }
        /** NIP_DETAIL mapping */
        comment__ = NullDefault.getString(comment__, "");
        GSValidateCommon.validateTextAreaField(errors, comment__,
                "comment", "コメント",
                GSConstNippou.MAX_LENGTH_NAIYO, true);
        if (!errors.isEmpty()) {
            return errors;
        }
        //編集権減チェック
        ApiNippouEditBiz editBiz = new ApiNippouEditBiz(con, reqMdl);
        int ecode = editBiz.validateNippouAccsess(Integer.parseInt(nipSid__), true);
        if (ecode != 0) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            String nippou = gsMsg.getMessage("cmn.reading");
            String edit = gsMsg.getMessage("cmn.comment");
            msg = new ActionMessage(
                    "error.edit.power.user", nippou, edit);
            StrutsUtil.addMessage(errors, msg, "admFlg");
            return errors;
        }


        return errors;
    }

}
