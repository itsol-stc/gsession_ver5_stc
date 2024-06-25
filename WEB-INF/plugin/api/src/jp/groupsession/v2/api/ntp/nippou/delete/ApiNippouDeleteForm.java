package jp.groupsession.v2.api.ntp.nippou.delete;

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
 *
 * <br>[機  能] WEBAPI 日報削除フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-nippouDelete",
plugin = "nippou", name = "日報削除",
url = "/api/ntp/nippouDelete.do", reqtype = "DELETE")
public class ApiNippouDeleteForm extends AbstractApiForm {
    /** 日報SID */
    @ApiParam(name = "nipSid", viewName = "日報SID")
    private String nipSid__;
    /** ユーザSID */
    @ApiParam(name = "usrSid", viewName = "ユーザSID")
    private String usrSid__;
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
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public String getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(String usrSid) {
        usrSid__ = usrSid;
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


            /** USR_SID mapping */
            String usrSid = usrSid__;
            if (StringUtil.isNullZeroString(usrSid)) {
                msg = new ActionMessage("error.input.required.text", GSConstNippou.TEXT_USER_SID);
                StrutsUtil.addMessage(errors, msg, "usrSid");
                return errors;
            }
            if (!GSValidateUtil.isNumberHaifun(usrSid)) {
                msg = new ActionMessage(
                        "error.input.number.hankaku", GSConstNippou.TEXT_USER_SID);
                StrutsUtil.addMessage(errors, msg, "usrSid");
                return errors;

            }

            //編集権減チェック
            ApiNippouEditBiz editBiz = new ApiNippouEditBiz(con, reqMdl);
            int ecode = editBiz.validateNippouAccsess(Integer.parseInt(nipSid));
            if (ecode == ApiNippouEditBiz.ECODE_NIPPOU_NONE) {
                GsMessage gsMsg = new GsMessage(reqMdl);
                //日報
                String textNippou = gsMsg.getMessage("ntp.1");
                //閲覧
                String edit = gsMsg.getMessage("cmn.delete");

                msg = new ActionMessage(
                        "error.none.edit.data", textNippou, edit);
                StrutsUtil.addMessage(errors, msg, "admFlg");
                return errors;
            }
            if (ecode != 0) {
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
