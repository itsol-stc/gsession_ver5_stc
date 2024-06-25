package jp.groupsession.v2.api.zaiseki.edit;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.zsk.GSValidateZaiseki;

/**
 * <br>[機  能] 在席状況を変更するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "zaiseki-edit",
plugin = "zaiseki", name = "在席状況更新",
url = "/api/zaiseki/edit.do", reqtype = "POST")
public class ApiZaisekiStatusEditForm extends AbstractApiForm {
    /** ユーザ */
    private String usid__ = null;
    /** 在席状況 */
    @ApiParam(name = "status", viewName = "在席状況")
    private String status__ = null;
    /** 在席コメント */
    private String comment__ = null;
    /** 在席コメント更新フラグ 0:更新する,1:更新しない */
    private String comeflg__ = null;
    /**
     * <br>[機  能] ｃomment を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return ｃomment
     */
    public String getComment() {
        return comment__;
    }
    /**
     * <br>[機  能] ｃomment を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param comment 設定する comment
     */
    public void setComment(String comment) {
        comment__ = comment;
    }
    /**
     * <br>[機  能] status を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return status
     */
    public String getStatus() {
        return status__;
    }
    /**
     * <br>[機  能] status を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param status 設定する status
     */
    public void setStatus(String status) {
        status__ = status;
    }
    /**
     * <br>[機  能] usid を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return usid
     */
    public String getUsid() {
        return usid__;
    }
    /**
     * <br>[機  能] usid を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param usid 設定する usid
     */
    public void setUsid(String usid) {
        usid__ = usid;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateSchSearch(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage(req);
        String zskStetas = gsMsg.getMessage("zsk.73");
        String userSid = gsMsg.getMessage("cmn.user.sid");

        //ユーザSID ここでは数字チェックを行う。未入力は可
        if (!StringUtil.isNullZeroString(usid__)) {
            //入力がある
            if (!ValidateUtil.isNumber(usid__)) {
                //数字ではない
                msg = new ActionMessage("error.select.required.text", userSid);
                StrutsUtil.addMessage(errors, msg, "status");
            }
        }

        //在席ステーテス
        if (StringUtil.isNullZeroString(status__)) {
            //未入力
            msg = new ActionMessage("error.input.required.text", zskStetas);
            StrutsUtil.addMessage(errors, msg, "status");
        } else {
            GSValidateZaiseki.validateZskStatus(errors, Integer.parseInt(status__), req);
        }
        //コメント
        GSValidateZaiseki.validateZskBiko(errors, comment__, req);

        //コメント更新フラグ
        if (!StringUtil.isNullZeroString(comeflg__)) {
            //
            if ("0".equals(comeflg__.trim())) {
                //更新する
            } else if ("1".equals(comeflg__.trim())) {
                //更新しない
            } else {
                //異常な値の場合 更新するにセット　
                comeflg__ = "0";
            }
        } else {
            //未入力の場合 更新するにセット
            comeflg__ = "0";
        }

        return errors;
    }
    /**
     * <br>[機  能] comeflg を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return comeflg
     */
    public String getComeflg() {
        return comeflg__;
    }
    /**
     * <br>[機  能] comeflg を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param comeflg 設定する comeflg
     */
    public void setComeflg(String comeflg) {
        comeflg__ = comeflg;
    }
}
