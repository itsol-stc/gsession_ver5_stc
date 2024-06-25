package jp.groupsession.v2.api.schedule.attend;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.api.AbstractApiForm;
import jp.groupsession.v2.api.GSValidateApi;
import jp.groupsession.v2.api.schedule.edit.ApiSchEditBiz;
import jp.groupsession.v2.cmn.annotation.ApiClass;
import jp.groupsession.v2.cmn.annotation.ApiParam;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] スケジュール 出欠確認応答編集 WEBAPI フォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@ApiClass(id = "schedule-changeAttend",
plugin = "schedule", name = "出欠確認応答編集",
url = "/api/schedule/changeAttend.do", reqtype = "POST")
public class ApiSchChangeAttendForm extends AbstractApiForm {
    /**Schsid スケジュールSID*/
    @ApiParam(name = "schSid", viewName = "スケジュールSID")
    private String schSid__;

    /**
     * anser 出欠回答
     */
    @ApiParam(name = "anser", viewName = "出欠回答", required = false, confRequired = true)
    private String anser__;
    /**
     * <p>schSid を取得します。
     * @return schSid
     */
    public String getSchSid() {
        return schSid__;
    }

    /**
     * <p>schSid をセットします。
     * @param schSid schSid
     */
    public void setSchSid(String schSid) {
        schSid__ = schSid;
    }

    /**
     * <p>anser を取得します。
     * @return anser
     */
    public String getAnser() {
        return anser__;
    }

    /**
     * <p>anser をセットします。
     * @param anser anser
     */
    public void setAnser(String anser) {
        anser__ = anser;
    }

    /**
     *
     * <br>[機  能]入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GsMessage
     * @return エラー
     */
    public ActionErrors validateCheck(
            GsMessage gsMsg) {
        ActionErrors errors = new ActionErrors();
        GSValidateApi.validateSid(errors, schSid__, "schSid",
                gsMsg.getMessage("api.sch.sid"), true);
        return errors;
    }
    /**
     * <br>[機  能] 編集権限チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * TODO RelationBetweenScdAndRsvChkBizリファクタリング後 引数reqの除去を検討
     * @param req リクエスト
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validatePowerCheck(
            HttpServletRequest req,
            RequestModel reqMdl,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();
        ApiSchEditBiz eBiz = new ApiSchEditBiz(con, reqMdl, null);
        //同時登録スケジュールの編集権限チェック
        errors = eBiz.validateSchPowerCheck(errors,
                NullDefault.getInt(schSid__, -1),
                0, ApiSchEditBiz.MODE_ANSER);

        return errors;
    }

}
