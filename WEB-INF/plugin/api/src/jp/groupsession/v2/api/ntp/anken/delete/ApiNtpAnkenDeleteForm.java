package jp.groupsession.v2.api.ntp.anken.delete;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.ntp.GSConstNippou;
import jp.groupsession.v2.ntp.dao.NtpAnkenDao;
import jp.groupsession.v2.ntp.model.NtpAnkenModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 * <br>[機  能] 日報 案件削除するWEBAPIフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "nippou-ankenDelete",
plugin = "nippou", name = "案件削除",
url = "/api/ntp/ankenDelete.do", reqtype = "DELETE")
public class ApiNtpAnkenDeleteForm extends AbstractApiForm {
    /** 案件SID */
    @ApiParam(name = "nanSid", viewName = "案件SID")
    private String nanSid__ = null;
    /**
     * <p>nanSid を取得します。
     * @return nanSid
     */
    public String getNanSid() {
        return nanSid__;
    }
    /**
     * <p>nanSid をセットします。
     * @param nanSid nanSid
     */
    public void setNanSid(String nanSid) {
        nanSid__ = nanSid;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @return errors エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, RequestModel reqMdl)
            throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        NtpAnkenDao dao = new NtpAnkenDao(con);

        GSValidateApi.validateSid(errors, nanSid__, "nanSid",
                GSConstNippou.TEXT_ANKEN_SID, true);
        int nanSid = Integer.parseInt(getNanSid());

        //編集時は案件の有無を確認する
        if (nanSid != -1) {
            NtpAnkenModel nanMdl = dao.select(nanSid);
            if (nanMdl == null) {

                String textNippou = gsMsg.getMessage("ntp.11");
                //閲覧
                String edit = gsMsg.getMessage("cmn.edit");

                msg = new ActionMessage(
                        "error.none.edit.data", textNippou, edit);
                StrutsUtil.addMessage(errors, msg, "admFlg");
                return errors;
            }
        }

        return errors;

    }

}
